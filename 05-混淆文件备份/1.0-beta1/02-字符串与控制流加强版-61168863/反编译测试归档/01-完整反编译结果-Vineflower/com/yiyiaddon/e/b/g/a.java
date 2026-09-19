package com.yiyiaddon.e.b.g;

import com.yiyiaddon.e.b.g.a.e;
import com.yiyiaddon.e.b.g.a.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String bk = (String)com.yiyiaddon.m.b.a<"s2pvmw99ytvmjx","nPnNRgATVw/y7nXLytz/46ZfGRd7n4TgVTz/vG7OM2Ep+C5oZIBrEJ4n2lO21oZeX0PqjVqdQA3OhzQLHxdjyss8mqseN0GJHMcQvg==",4163587405150401942,-3492300116915740737,-8066944952632766225,-3645247681166510653>();
   private static final int aD = 20;
   private static final float t = 6.0F;
   private static final float u = 11.0F;
   private static final float v = 10.0F;
   private static final float w = 12.0F;
   private static final float x = 6.0F;
   private static final float y = 6.0F;
   private static final float z = 320.0F;
   private final com.yiyiaddon.e.b.a b;
   private final com.yiyiaddon.e.b.g.a.a a = new com.yiyiaddon.e.b.g.a.a();
   private com.yiyiaddon.e.b.g.a.d a = com.yiyiaddon.e.b.g.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.b.g.a.b a = com.yiyiaddon.e.b.g.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2monq9duiq5ic","plXjNefecoQMGmCXnUB1Z9qlXiyYuHmUSIy0KSjVQL8=",5328161112924268954,-5713156516436678983,5886177423129656762,7495378767436767126>()) {
            case 1878407718:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2z6iyeanvu1v4","gMmfGh+kpXtQtB8yNQbV+fOo2y7GO1UHvXGyH/FTNuU=",-2076879770110991926,-1440255126315166509,5461469466138017539,3000472642583698454>()) {
                     case -373839710:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s22it2o0f8xhd0","t1npVkCELZd4FZZV/LMhq19NNleEjZcoU0GVbZ292pQ=",7787486091029466055,-3600606349735463561,5704468313006554949,4455786486253275014>()) {
                           case 1394616703:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1304yp599svl4","2vUZBOTMPV6AJTCKBYM+wDpyCWqlgZaXGZiHzXIyuUU=",8450978573708472457,-5988381074834681189,3965008257303391267,1511664362581745410>()) {
         case 1429008228:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.b.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s23f24kb5opl4t","AIU/bfXs3TGe6X7pUsOGE5WMQH3GMPw6RkZPtxK/Vcgm4SK82Fionupe",-2631741415576227071,6861124685108200792,3243830466466270893,-8369061695830967913>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s2pvmw99ytvmjx","nPnNRgATVw/y7nXLytz/46ZfGRd7n4TgVTz/vG7OM2Ep+C5oZIBrEJ4n2lO21oZeX0PqjVqdQA3OhzQLHxdjyss8mqseN0GJHMcQvg==",4163587405150401942,-3492300116915740737,-8066944952632766225,-3645247681166510653>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s18u1bxjczh8i9","pdcQQM2FCdBwcl6yJT0qRshZXkn1B4DgAD3FdrXPNBQ=",4193617648397348571,5544659349154000068,-9175257724737273959,7774345712551140924>()) {
            case -1839023201:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1n2x9vz5uc8wk","MW7qYkxFPsmnF0bNRVjawjTHrtkB7lI4XFK0sOymiOw=",4946589858212525542,1479146048576373646,980840324083461213,6242290785261862130>()) {
                  case -150112130:
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
         (String)com.yiyiaddon.m.b.a<"s2pvmw99ytvmjx","nPnNRgATVw/y7nXLytz/46ZfGRd7n4TgVTz/vG7OM2Ep+C5oZIBrEJ4n2lO21oZeX0PqjVqdQA3OhzQLHxdjyss8mqseN0GJHMcQvg==",4163587405150401942,-3492300116915740737,-8066944952632766225,-3645247681166510653>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qsrhhbu3dp3f","z0cGmuX8o1YtKl48pWEKhsTdf3NiMD7wJYWIOdBp5OY=",-4664368946575041597,2085144530950137843,-2237697785978933022,-2258620522331120078>()) {
            case -2101890089:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sudzmydk4fqfa","fC0YnkkyC1dsGZpsQmS0wdBiJ36a77svcJWiT+AxeQM=",3181593462158290756,-3132628884738259068,-4780799218128559162,8988526183486368213>()) {
               case -1850709518:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3wspeezan6un","d7Xlul75FBMgq4O0fuRbmUGGtw2wGKN5buefk8Z/TXk=",721165424624231947,-1466430622569495507,-8058284768281046602,-367250860457993867>()) {
                           case -1417767314:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.b.g.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"svihixue1skb4","X3hyEw/RpMnK4KX5kqZqZBZbSXNFTFqFiEN+TEFphrs=",-5234220350518975849,-6474446099323737714,2595678611861500569,7666769983913645699>()) {
                                       case -10159194:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.b.g.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1f11vl7ma35zq","0b7/Gk9NYv/JsIIlFntxm96KffAaxgAGcEjxzDTw8tc=",-399398037818832164,-4387957819847846386,-3559436023933117713,-315412226803489081>()) {
                                 case -703201325:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s14p47kdabz4et","b7DB4nO3Rdvt2IEAIQE4RZgWLgXK4g6F86bOmMmmmj8=",8536331144150198955,2808814884771804566,-7060141099010690536,4594666786440888819>()) {
                        case 580418153:
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
         switch ((int)com.yiyiaddon.m.b.a<"s1to4yptn2fu55","sRan1qobwfP6u8dQwQB4KgPRPtniGhmtnIs0tyoGhxk=",8415435542086032126,-1177682186843462873,-617576359352694231,-2193815664302227188>()) {
            case -40211005:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ponfk7jofr8t","DsxshBdVZV5nb41dZCKcR5+XxKGTOjrbEnXPiW4JH+s=",-7014493034571893572,3420339416661165462,-6548494443526804801,3027717598885976354>()) {
                        case 469148783:
                           switch ((int)com.yiyiaddon.m.b.a<"s22pdbd0q60la1","QixiCLe7PER/9K2UnpFWot8UWam4QfUiMNRad3ETG0Y=",-2576001417629960204,-8254226111535617917,-6616371534362955297,-2052419833662316706>()) {
                              case 1990112526:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s4opcmdvzybt","ZyKSOBiacothtl+RXG+E7OWdtvZ9zQ9SEq3MALs8EoI=",-4781088511816283833,7427805914090056389,7455330832877497333,231516591704609932>()) {
                        case -173469100:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s20rg69y6n83i1","TW9SBUDlGRBx83vCUp5lyymKAbe+iazIYAnR89bjVRg=",-6314493970863097889,-2967780824324435635,7849674254995400759,948492656874293436>()) {
                     case 1622972599:
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
      this.a = com.yiyiaddon.e.b.g.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.b.g.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2u5ni4cmzodt8","RUhJUxcQ1YkRirSJC4AhLieHvateHATuRgkkJzNk84M=",-8756881732747277483,5613339611506137381,7634294036034148303,-3409541265703867187>()) {
            case 1535849108:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1gm1jrve4kiqc","hLQGcyKqY57cEDkbybebmQByBQwMVFKnsTX+GsudHA4=",-657217285419917220,-7184428072781687286,459033970282310862,-5032684296085403479>()) {
                  case -1720207001:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s2e74slabl7669","SIfo7bB0J6d7BXjMIWZZXnQBgbWc4cgdMw/rjv/XSB0=",3257319222272262717,7282206561918753747,-1637732858709475121,5021690690795202922>()) {
            case -2078153809:
               return;
            default:
               throw null;
         }
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.b.g.a.c());
      this.b(var1);
      label35:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.b.g.a.b(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3tja7mo9i8cf8","JDQTX3MVBV9r1NC7A5214EEUOH7GFKYhqqJHSXi0Mrk=",2591851242981319703,-5063633977734617879,8432472966998526183,2071611619739684913>()) {
               case 1549840879:
                  break label35;
               default:
                  throw null;
            }
         case POINTS:
            new com.yiyiaddon.e.b.g.a.c(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s17pvfmmppxufe","FYikzEkaJFzxLJEeMSQMSk1GMqDvW+2IPwgFO8ewwqE=",3309646748743711782,-2543780121844799247,3268610172949497535,1422889993058397397>()) {
               case 1685677666:
                  break label35;
               default:
                  throw null;
            }
         case RUN:
            new com.yiyiaddon.e.b.g.a.f(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"suuxrir476fn5","iAzYQRgHhKb3uGYlVd7L1WI3bHI4U0cVc7thv9YS9gI=",-2735210800194764496,8001808654832960523,1044649445094145757,8656394743677150546>()) {
               case 1295548663:
                  break label35;
               default:
                  throw null;
            }
         case CONTAINER:
            new com.yiyiaddon.e.b.g.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sr6pxkdfqv3rd","AgDQSuzOoxAxmWydEUIiW9Pu12buwiwOuQtzWz9fcWs=",1567348338998236647,-3196704006055972769,-6269871548212579834,1994763321866515225>()) {
               case -1158736476:
                  break label35;
               default:
                  throw null;
            }
         case PROTECT:
            new com.yiyiaddon.e.b.g.a.d(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2kxmda773nd00","G9vYBILv0OMuXD/Ex360/uk/lGRb+hC7mjUoT8OByxw=",8956165039549357481,988638972550099955,-3676568861408880424,3189869592127622671>()) {
               case -250021686:
                  break label35;
               default:
                  throw null;
            }
         case WITHDRAW:
            new g(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1r8nt29muax84","G/Xj46NXML1b+4AlYzdw1U5vHZIinxXMAh9a65PxMB4=",-5415653739499351522,-2456222517391524383,-6955954077963323291,4778681639503029087>()) {
               case -1497231165:
                  break label35;
               default:
                  throw null;
            }
         case RENDER:
            new e(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sp0k3gl7gncov","VvuEFS2pz6DR8SfmVbJukN6qr8jsOap3meH5lKCCq/M=",1239953587578454632,1787820510248823854,-8310167402735477609,-8659904474285662228>()) {
               case -1897464903:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.b.g.a.d var6 : com.yiyiaddon.e.b.g.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3r3ghedf8c31z","AUcaddZkkP/BK52/WEc+PDCLSjEio0hGZ3xjadtLyTw=",3512400605548622506,-6719604773054759506,-3305621969446432224,8536591462739914341>()) {
                        case 760844001:
                           switch ((int)com.yiyiaddon.m.b.a<"s2wjoqyd4me503","l9waNithKUbYcG2jgxBGYq1lYDi186BzbLdN7dHm+10=",4198271646406359269,-2567524278353085129,-9075217937566472817,-2325118186652752978>()) {
                              case 751673252:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s3cna22sqxgosh","qwgQq973mDla4ur/tj7rnEthhKciwNgNibTAB3tn3qs=",6413437815514900683,-3283236459076966273,7459352392467195625,8558162328438668241>()) {
                        case -272802367:
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
                     (String)com.yiyiaddon.m.b.a<"s3w4peho1rhk94","QI7M0d/4HxQIGc920qyMHPNSffooUNLki5X8vAQhR+3D4CBd",4160565796043088744,-9103005991687032487,-6933365668230850764,-772370621856379971>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s2k90ihshodibk","/PQQgahesnrhmDeEkef377JXh29Bo2InC8d5NZAdhVZuFRGY1Lwnhc3LWZJNJt9o2muAGwVH0/qn0b3yw6+zwgMHCt+l7TLI2TGReky1KOYQI8gJard1RNdOZDVsOosIWKWcMg==",1456598993631186263,-5623395758680726991,-8719531698227923426,4352612384737172065>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3p6ohbd6oeb93","kSYZY6dCrEU3DdvFVrFA3OQra0AB02mJ2HV7jND0oSnnei/s",364982736102582006,-5482168157591931934,-2992271953012711574,6509196549842880650>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1oaj84pnglmng","685WVS6X7BW4BmuQtHMJM5kj7aSjrj7aGAdbTv0RcvE=",-8976598615962499776,-7889540351251625694,-6409474581118775616,-3332370828971628359>()) {
                              case -916103348:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s30azyqxsiuyz8","BCChYCW9XQorzchQnVsrPIiNDKxAK6DmaFYNnki99Uw=",711583305446113056,8118488244185191381,6235782886683286187,8349765616760445113>()) {
                                    case -306821358:
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
         (String)com.yiyiaddon.m.b.a<"s5adkf7a927v5","Ja4Ii6rhJCYZE2aw3baulWFAu9+L0RKZDzYdN5Cq",6252102962449953428,7391379991137076378,2759744232494805405,-9164911645034836977>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s32b4musyw1mo5","x86n6h2nqvoJ/srWOTBVxQRhh+nz8qL9pEZnsMmNK8w=",8804727714948826032,-9182819803707940847,8730461027670234923,-7367231916743603675>()) {
         case 1974042719:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s5ai6y16gj11l","GN+wS0zxQLMrcZ7/y1yymYkd90P1+JvqjiqKLFUkAng=",3046852963831449717,-8875645878650101487,6628612664205427627,8947137076827358119>()) {
                  case 924504799:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s2vt7mr1qjeb9d","zj9Xir9zI1MK7EghzTnzlscx8nAAU59eqYKQqJVdV2o=",-6749362862456852522,9005513770192486147,-663593922257092771,-1061187882554033491>()) {
                           case -1437949825:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s2dh3j5v9vq9vc","JIHBupnvI5cuokjEqNal6UVC/w7vVZ3QdyB9cA==",4056792945783273398,4428915099408893157,-905243595938277737,-1669783286091483236>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s6nwusgd3id00","E0cEAmgUV21PYOH15/c6i+KUJSKLskAIhzUCxJ3LD2o=",-2858623267545593741,-3666041527569407,-2378601537711884883,2084941145197279732>()) {
                                 case -1263129660:
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
                        switch ((int)com.yiyiaddon.m.b.a<"smt01s1vqqfwu","plarIGgyZMkSGho+LEYgq5qYpO7tp0Ky4OMLYORS/9o=",-377884342322094224,4583407176170922385,-1616773445363586967,-2759085187106653923>()) {
                           case 441119643:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2z2h76krkd973","T7XkxwoSjRQZlXcYXFuFiBnXfVsOMvJTmK6lewWMHR8=",-1294916189071887660,-9098490279091665112,3489810901932951232,9145042549615334217>()) {
                                    case -65041129:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1irfyvgrqn7j7","z++B662OBRFTlsIaYiSHYfdrLYIw7wjujIjjRwsmfDY=",-7095822356993938783,-9147281090479248817,8868703815161385542,4956688707564762365>()) {
                                             case 131056417:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2t07bjtpqdvl7","nMVC8hRIIw42b9FY2lfCfO1AiQtZzPnSmyaXhcdCJto=",-9143535087181451764,6066785116936945164,6196635919443353684,6161121259291185347>()) {
                                                      case 794346447:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1cmzkqvj4yhfs","DLGwXU/OZRGFVAt53rElSm1C1KiuYGlTNkXUiVP4Uek=",8188072330087040453,4821719918694633233,-9216331344707652187,-129083451364085310>()) {
                                                            case 1571743816:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s3dy3kfuww4f67","vKrP0tWdKL0lcpKLg9+kKSLTu86/jcZpSAwliTjqxpU=",-6550838784558162691,-9058455936158094401,-472076075158339789,4646619661881240613>()) {
                                             case -1361270647:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1tnpxp0j3i6sk","njg9Yozs7OzW9FaizIlMMpPWAIgLIBuDu32TpJrRS/8=",-485737115538945083,-5248374925438518574,6334677935547922954,-5691933765078641470>()) {
                                                      case 1232351477:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s28t34yftb6ucc","WVG6CmtvrI1Hq9ev0TYgn6omc6PFDWnPzd84GqjfijQ=",-4051868744334937659,-8779393985024686641,680578198602979925,2841310624245320882>()) {
                                                            case -218262504:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"sjh8cqq4enuo2","1o57zxZYGuxIjME0hjXHljlt1/jD0067j178XoXYTMs=",7675827796011574378,-7918088747045474052,9118491568630285678,9021630126731512736>()) {
                                          case 353373328:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2byenywnia2mg","jyOoTHjIAc2wdU0Ty2WfWmkCJBEUJVWlLTfT8ANCatc=",-4661262957219616490,-1354865616115306672,-3979674489955020262,2472189564090939877>()) {
                                 case 923909551:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s226x50hd9gnuh","Qv3SYSUX1O4NYlibrYDtEyehZGjCLT/lYu9eVmyXe6E=",3494640465212444597,6488232620582183940,4337223276124107027,7310009200150122728>()) {
                        case -363744544:
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

   private final class a implements com.yiyiaddon.l.b.g {
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
            switch ((int)com.yiyiaddon.m.b.a<"stzcggnxbd3x6","TgbfNlOwUZf/8RQ5T/lOhO+qIC2bKggVQHQrYrR8kbc=",-9159672888256035848,-1192363887246166102,7934183972104195643,-8359458353657759435>()) {
               case 1732269591:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s11mz2mi2c6sml","WL9pZ9w9LEA9zxaWEwnjiYybtvAhOSHms59ClWzsqXk=",8872580582769330370,8517208830857717856,-6652689858541406633,8101437439148435354>()) {
                        case 989380704:
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
            switch ((int)com.yiyiaddon.m.b.a<"s209od3poxopmw","FmMjqd8UOIPQ2sZ3ZAqZa3hyc9TglyTdmE/tI5poDhc=",-6288597995282536052,-2393701865864324651,-9215986416044661942,7931519435208370536>()) {
               case -789396695:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.b.g.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1j0n0bpjr1k5i","0ftuL3IQLeSEsGOVowsB6J6I0mTe8SZkqoW3N75LNXc=",3545751053579114940,350891039090025389,-1524465361112198396,-981130527625531911>()) {
                        case 362762581:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s18b6wln7mipa4","mUGz9xhOVrtUFftBTQ6vfKeaF8KId7lm7XMDme7eoM4=",1283394534526134177,-337874296655656569,-1404677822988804085,5763272377897151310>()) {
                                 case -443696686:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ivw91dc5zaty","hUgx+XHqEDtg7fVsJyHTKX1W9Rc0Ld+JBkCiBbNzXww=",8667684166440246323,8395951643716291746,-9082659949317278082,-8622379690040501434>()) {
                                       case 822129522:
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
                           int var13 = com.yiyiaddon.e.b.g.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3go01bypdqyn4","bTtuHBXL92JzCfENwpGMRVTtMaIdyUWmGHoYEf0v53I=",6630761655522435224,3964386646597677499,-743033967747830676,5160134616074266182>()) {
                              case 1615900633:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s30tfk07ncj8mw","iwXiz82t0dDQ0d6xE5Xz+2KC4/UOic4O3JPAQJ3YQBU=",-3828737369740308787,-5805742478318163599,-3798553940073695081,-3357253722949679349>()) {
                                       case 744748866:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s2mfnrr15i5gsc","RslT0M90+2vd/xf/tdYsEKkt2xYjtwwKnZVzSrdfiek=",6825934769299710062,8508543439971847172,4197678245461540946,2111566711994748466>()) {
                                             case -1671887935:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1mg8235xh25nq","nts+Vq6RbQgE7zskYXK3mR2yiHzqDgSwMAhK6q3E4io=",7266780445602091050,-743620848042897645,7512402180814617490,5228150257280796975>()) {
                        case -795602912:
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
      private final String[] d;

      private b(String[] var1) {
         this.d = var1;
      }

      private static com.yiyiaddon.e.b.g.a.b a() {
         return new com.yiyiaddon.e.b.g.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>(),
               (String)com.yiyiaddon.m.b.a<"s3li7gwm3wf6um","+P9qaZRfgWaE5LVB6CZu20U102WG9dYMQuEtdA==",7688428574706984684,7301785235061283097,-5130424739396957941,8779363128571638755>()
            }
         );
      }

      private static com.yiyiaddon.e.b.g.a.b a(com.yiyiaddon.e.b.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.b.b.a var1 = var0.a();
         List var2 = var0.f();
         return new com.yiyiaddon.e.b.g.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s1np3z8913s5ay","F0BMSFy5QANy8PBbVW0JUmNDq3J8ZJ3hCxkATEvwF2mOIVWtkew=",-1184886009402745116,-1814859454785267654,-6381119307240171491,-2523224525252662108>()
                        : (String)com.yiyiaddon.m.b.a<"s35i4rv6bzcwzc","bi6J8rNm/ScEICKs5YO0vkmyGHDY6UNy0x76W2QpgKIlJxh8GoY=",-1128744979635317925,614729769928603198,-1122556341384835239,6698163208750884199>()
                  )
                  + "",
               var0.a().a() + "",
               com.yiyiaddon.i.g.c.bU(com.yiyiaddon.i.g.c.bU()) + "",
               var1.a.m() + "",
               "" + var1.g.size() + com.yiyiaddon.g.a.d.c().size(),
               var0.a().bD().size() + "",
               var0.a().a() + "",
               var2.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"s1bhip0e5tctdp","4QYNShSXpiEVGK/93ocvSdTyQEEDrwHErmqM9A+KjntbeFDoF/QZlA==",6629592149150094265,-1758687997845098418,5892504413148604878,1041074165877281871>()
                  : var2.size() + ""
            }
         );
      }

      private String c(int var1) {
         return this.d[var1];
      }
   }

   private final class c implements com.yiyiaddon.l.b.g {
      private static final float A = 18.0F;
      private static final float B = 6.0F;
      private static final int aE = 4;
      private static final float C = 8.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.b.g.a.b var8 = a.this.a;
         float var9 = Math.max(0.0F, var4 - 12.0F);
         float var10 = Math.max(0.0F, var9 / 4.0F - 8.0F);
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sz6im63qimn11","2GX/Uvf+T4xtwLmHcdrAtgeuIPBfggJjU/Ao+j5IpNc=",-4645200687416034498,-2175248061661737001,-5916937152582057092,-6561300369817960377>()) {
            case 1376469949:
               while (var11 < 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3smyq20bm2wjq","mhYzql+48HQI1KxKZDHPzTvKy5cN/MKQy1eU56Zuq+I=",-2238915023494165559,4923289826310446505,6720845286639083303,-2713944267600266265>()) {
                     case -759811265:
                        int var12 = var11 / 4;
                        int var13 = var11 % 4;
                        float var14 = var2 + 6.0F + var9 * var13 / 4.0F;
                        this.a(var1, var8.c(var11), var14, var3 + 18.0F * var12, var10, var5);
                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3gi554d0xju7k","Z9+OXFWAvP5oqI1q5m2d32C4YT0VxQVHwkrhvam8lH0=",-4503222387342185845,-9039193888608861717,-7460799123169590781,302618237786609892>()) {
                           case 947827550:
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
         (String)com.yiyiaddon.m.b.a<"s2nesx8ubiszfl","SYozdY4HKvwXdpcQXY6HPWZAxSX0zZe0NjxrU4VCeXU=",-1035848314006587253,3960167943181147036,-8305112712719244119,-4554949217972578505>()
      ),
      POINTS(
         (String)com.yiyiaddon.m.b.a<"s3mc2bcvlczr53","yFfMNihogYA9AtcbUI+Vb89wDKFkfnfZcBiFvlWQJoM=",-7991456590526293271,-4163189281503545223,-5597424861473013109,-7198893081970333215>()
      ),
      RUN(
         (String)com.yiyiaddon.m.b.a<"s7qa7kkr3g04i","Sk35e+YBNndKjNWGeBHu7oHIojNppRz1SCSWIES2VkI+P6FG",-4969782674832755925,5096269524812578525,486039257232185468,3475152884711772796>()
      ),
      CONTAINER(
         (String)com.yiyiaddon.m.b.a<"s1vjeqql76f7sk","3NaZOAMMS3PfJXwpyzBYIZOw6dS9cy76GzmLIKT2E60=",708850263532367523,1226619952400208675,5289134305735559142,-1584552735112815614>()
      ),
      PROTECT(
         (String)com.yiyiaddon.m.b.a<"s38k8t4zxhaknj","dFazOmCyvLkLI6eueh+4qY2piDrcaP5x4MWsQqfC30w=",5333673255276423291,7716647548404638283,-3271444580296538806,3770821328271513758>()
      ),
      WITHDRAW(
         (String)com.yiyiaddon.m.b.a<"s1t4j2ijnk05ph","TSEmqOh6PgRNWJGPiwbquULHvcY5576ue3AZeS1CX98=",6428490523686673105,373377059138299726,3111920379827212057,-954714340456024289>()
      ),
      RENDER(
         (String)com.yiyiaddon.m.b.a<"s2shqjdov8efhr","oKiBjFf0dhdq2qc+vnIaL/O4yC7ULgSkcou+F0/Fesk=",8988148957384989923,-2456227278472209645,5339096853470735710,3727035402143145489>()
      );

      private final String bl;

      d(String var3) {
         this.bl = var3;
      }

      private String D() {
         return this.bl;
      }
   }
}
