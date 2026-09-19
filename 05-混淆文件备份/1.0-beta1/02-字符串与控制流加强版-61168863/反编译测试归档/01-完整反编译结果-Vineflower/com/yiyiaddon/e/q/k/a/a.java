package com.yiyiaddon.e.q.k.a;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.g.j;
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
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String Ag = (String)com.yiyiaddon.m.b.a<"s2k1whgzqefrcv","73B5ALoJ30do6OEziCeY5C7Y5qyszSGdFIocfU1e1XDGaa5byrvaZHK8YRypoQ071vHHq5Q5Y+fB8OaL11sc6IDfZtLJPNhJTW8=",-7519867928548224875,3315481940715413579,6495607622429183938,-4784346850815641264>();
   private static final int rh = 20;
   private static final String Ah = (String)com.yiyiaddon.m.b.a<"sj4dxtcu7b8u0","LAux+EpO+doLLpCH3yLzz6hmAA7E1QR/CKJhTOhkWMTncErAt/zwdI2yI8nFKA==",-7034792236879418137,8594851747811128746,-7949574156197677079,-2546666474431862143>();
   private static final float ef = 6.0F;
   private static final float eg = 11.0F;
   private final com.yiyiaddon.e.q.a b;
   private final com.yiyiaddon.e.q.k.a.a.a a = new com.yiyiaddon.e.q.k.a.a.a();
   private com.yiyiaddon.e.q.k.a.a.d a = com.yiyiaddon.e.q.k.a.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.q.k.a.a.b a = com.yiyiaddon.e.q.k.a.a.b.a();
   private final Set<String> av = new HashSet<>();
   private final Set<String> aw = new HashSet<>();

   public a(com.yiyiaddon.e.q.a var1) {
      this(a(), var1);
   }

   public a(Screen var1, com.yiyiaddon.e.q.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"sj4dxtcu7b8u0","LAux+EpO+doLLpCH3yLzz6hmAA7E1QR/CKJhTOhkWMTncErAt/zwdI2yI8nFKA==",-7034792236879418137,8594851747811128746,-7949574156197677079,-2546666474431862143>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   private static Screen a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26359bfzlwf8","TJcog8alRqNX7OeABTA75F94qdsqGeSEhgOyyE38LTk=",-8819829114968647744,-8506263578144379632,7340407916506400431,1785534915584017467>()) {
            case 1034729162:
               switch ((int)com.yiyiaddon.m.b.a<"s3ctaayzs00z04","k7TmhSFbZ38WJn1pHZM5IWf/15RxCwPG47Q7AFs9Im8=",4780074671420344329,-4611302923095105270,3733257373119897374,1117235611338139304>()) {
                  case 1086199117:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Screen var10000 = var0.screen;
         switch ((int)com.yiyiaddon.m.b.a<"s3s0al0388ohl2","hs6KHrHGDXkgdLFlvXeNqLqapaG5Mw+DvLxUmoRVt2E=",2891817544708758262,583067582197360505,5019152796963384323,-3534772309190475331>()) {
            case -773439573:
               return var10000;
            default:
               throw null;
         }
      }
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s2k1whgzqefrcv","73B5ALoJ30do6OEziCeY5C7Y5qyszSGdFIocfU1e1XDGaa5byrvaZHK8YRypoQ071vHHq5Q5Y+fB8OaL11sc6IDfZtLJPNhJTW8=",-7519867928548224875,3315481940715413579,6495607622429183938,-4784346850815641264>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3c1w1cc2q65kp","tCd7ClRk4zX41eU81osYYaj9Gc8Dzpsf7yDIXif4Z/s=",4006607988352072551,3904161394856738017,8879804157880512631,-1455840494531988533>()) {
            case -1494399001:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2k6c7giyyitw6","yDlXY7w1+ZUCL2xwmaNOMGhO6HkY4qLt7/+LME+x4n8=",-7131516702173001753,-6931208068158883542,8447918892620981345,4740228201137429169>()) {
                  case -1072803924:
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
         (String)com.yiyiaddon.m.b.a<"s2k1whgzqefrcv","73B5ALoJ30do6OEziCeY5C7Y5qyszSGdFIocfU1e1XDGaa5byrvaZHK8YRypoQ071vHHq5Q5Y+fB8OaL11sc6IDfZtLJPNhJTW8=",-7519867928548224875,3315481940715413579,6495607622429183938,-4784346850815641264>()
      );
      super.removed();
   }

   @Override
   public boolean keyPressed(KeyEvent var1) {
      if (com.yiyiaddon.l.j.f.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"slwoxx8z21t1p","8AXGNWhm+aOU/NSZEUvRR6m6MOqxRJfNMudt8ihiAUs=",7415707747645347193,4038404372439460010,6082372559370182739,-4128052701191607967>()) {
            case -905821956:
               return true;
            default:
               throw null;
         }
      } else {
         return super.keyPressed(var1);
      }
   }

   @Override
   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12gpryan2add5","jn84NHGnHNNgmB6HEDwUUTw704Fs77qUSxRgAL7JBAI=",8093190444842528750,1836181186224418387,174808121435927722,2272387614042795203>()) {
            case 756178043:
               if (com.yiyiaddon.l.j.f.c(var1.button())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ujxw4rhs84z2","6ABGg2+HmKwDSfSIOE+yxVZg5B5zhxenMU+jGQprU4s=",-8431955648801586,6289028840584266561,7262379874944673508,4654447550641742614>()) {
                     case -713064661:
                        return true;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return super.mouseClicked(var1, var2);
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vscwkx2duhbd","8a9G8EJRBEbrtR6Kc/u+PLQTspohJm/5OYwqaLOhoeM=",-5882852386654965596,-110858957197837354,8276955426630970177,-4437642683798177625>()) {
            case -391616298:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s7a3jsetli77y","/9aiKse9KhH+QP/6/QpeJzI2gHP1rO9LmRQdIzDoj3A=",-943392877215890965,-152795159389916552,3470697658456354304,-7576671410277060940>()) {
               case -1255606874:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s19uk709yhl2n2","G8V3Rv5Cs75xVvpYVDXDPK0GiRkKFeQm1awMzo0aQZ0=",-7056037318683743181,-7333515607426683184,363329873310028173,1086071116606452460>()) {
                           case 161128512:
                              if (!c(1)) {
                                 if (this.a != com.yiyiaddon.e.q.k.a.a.d.OVERVIEW) {
                                    label36:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3n8wg4bwtho6u","BRsb97wAEQ5GJQecBavKMLJqNf08WxcUaiutUmVqo8o=",964340959256577717,-7903370811692837442,-2717032397479557428,3957750551491191892>()) {
                                       case -1994908852:
                                          if (this.a != com.yiyiaddon.e.q.k.a.a.d.POINTS) {
                                             this.a = com.yiyiaddon.e.q.k.a.a.b.a(this.b);
                                             return;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s26eymza300lcd","GJ8k++XbDfVfqi6Q76TrfD7M9dnH9tG+wQvLq6ug3gc=",2007358414427204415,5169640885054561724,5777269867797562346,5949401805949597994>()) {
                                             case 472130798:
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

                              switch ((int)com.yiyiaddon.m.b.a<"s1bjksgzsh962f","0lY4YH4LpseXT3mRUvF8LmzlZgcPlIGavS7T8p4TE+o=",-7599717311832006002,8134579793240260327,-5910695377060444647,-7160820601924795228>()) {
                                 case -1929149289:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s3e6xn0iu43m1c","MciQVyg62T1b53XNIJ1qK1DqognwKROGAs0QuXUW5E0=",-4465230825733405916,8831688589589767553,9168822910654548148,-4635737249812057326>()) {
                        case -1450842323:
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
         switch ((int)com.yiyiaddon.m.b.a<"syj1x8jll643x","A7LsXhgUPgqp6XOzNlwbWLMwzRYv6JbW6i9q2XZo5D4=",4125803321720115911,-2904002813270310491,-5244242333715740701,4039353912207668594>()) {
            case 1278820879:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"speaoqnfkec4b","q5ra6LdoJ0wLdlJlYMfAFmT8o9Z67fcxQAbJeN9YEls=",-3105112061185350700,-9001415726597398710,4813223502859945563,3260390979154789356>()) {
                        case -863442882:
                           switch ((int)com.yiyiaddon.m.b.a<"s2widcr3pbp688","iIEqLPjjv2O5Z7Hw5TqCr2B9gD1o9NI2xNxERy8dOHI=",-8945728229805535126,-7302576992988146452,-4516779151691351545,-8406170232785432633>()) {
                              case 2077521741:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3lasah4zycpjn","r/0EYcgSdSwHSZgSWjvFb8dcUq5KD0BI/61XphHwju0=",6824614557184062345,-8213313912997047539,-26363106104340518,-8332015793287375782>()) {
                        case -725278421:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s5t8nwge44dou","LX/J7xDQuH1B3n16TAhoja4GGyoMSdyIXELMeMeRUSU=",-3664771776700606991,433717748434393214,1939059271195390090,-436512792533503715>()) {
                     case 1487983079:
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
      com.yiyiaddon.l.j.f.kY();
      this.a = com.yiyiaddon.e.q.k.a.a.b.a(this.b);
      this.a.u();
   }

   public void eq() {
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sm5akjds4s5zj","40MIjoylEz0nwCEYJAiedjvwdEP7rw6AFT/VOkbmJeI=",-3417687411037444887,-5961320883865134679,7454241138811878557,6971895030305546132>()) {
            case 1669572924:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s7cyuafsoakrj","1V+m+RGaB8jnEennXLzkdc/oxjkaBRWR/fbLAFPbhuM=",8708173082838201186,5832305086280632120,-4008278602207817885,-4176554267427508351>()) {
                  case 1272438690:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1ryizedmrwy69","xLwbxOR9oLLQa7D0TPkSok6xfj1l5ICxI2AHDP9LUIA=",-897163427673990227,-5582932351540651694,30447781074475786,2256008064885119365>()) {
            case 1653559940:
               return;
            default:
               throw null;
         }
      }
   }

   private void a(com.yiyiaddon.e.q.k.a.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s45zcwg8kkuab","O068M0oA7BKk5f3MKN+Tx9geGXBbnkGoj/Hf7ASDLgM=",2149711421568560621,-4703052641895702499,3984232341457110210,7920033646839086058>()) {
            case -1982001050:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2bfw22zsq3wf5","BKt+Y5Y1x3dtUX9eJMAzdMYiyVm6W7cYfotyU5yOoMI=",1754489296991023642,423803115113811707,8306892235893027701,3701970710334372479>()) {
                  case 1097780893:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s18fgl57l4u7uc","fPNMTC2CvLLQE3tekghY0pWVAACvI8y/lIIut4ejfSs=",-605287678935444340,5296242843219100481,-2208804801932823194,-3948954624509102808>()) {
            case -114859696:
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
      return this.av;
   }

   public void ba(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1807qcjo86yna","lutK2xxPyq00t+xrvBgCZ+v3RI1AIzKDw20pwoZ6TwU=",1111866254954738754,4049341580372640354,1658263951473777055,-1091019169940284280>()) {
            case -310463494:
               return;
            default:
               throw null;
         }
      } else if (this.aw.add(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fb8gxolx2prn","PENcL09FJPy94F4jteZstmZ0aCLDOthWt7b9AA+mhLc=",-638367735268537407,2140980091745866849,4877247026962892101,7324053495526105473>()) {
            case 720964781:
               this.av.add(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2tomihxxfochb","eGFqlSUo94oHr0n3Ds2fIutGFkFmc9CViI+sRxfuKC0=",1486916915393736842,3316857285524524541,-9210421697740096283,2335809315969131122>()) {
                  case 353073487:
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
   public void a(String var1, float var2, float var3) {
      j.c(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.q.k.a.a.c());
      this.b(var1);
      label23:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.q.k.a.c(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1a6tdktn4wvyz","pzFpGwHM0zKdEnLqO+SomcQR1Z0aB9sW3KyrYODL520=",-7603505931627036556,8043374258565944375,4325644571382653807,308666453434851781>()) {
               case 333345820:
                  break label23;
               default:
                  throw null;
            }
         case SETTINGS:
            new e(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s12dw33f4yem7q","/oXqaDZdVVUU/q1OZUKwS/ypNx3s6iChjQ1n2jRQDuc=",-6464842424842338638,1686370295876129663,-746697899972377715,7789712020568172987>()) {
               case 562999432:
                  break label23;
               default:
                  throw null;
            }
         case POINTS:
            new com.yiyiaddon.e.q.k.a.d(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2daka4hdkmsn9","QTNSKAeSmwARG9dDhHou2Y4/H7QvZ1qqXE5oqIctqiA=",-8943639774258480449,7184250969467611418,7155327474045789483,4276186599294224483>()) {
               case 2029615540:
                  break label23;
               default:
                  throw null;
            }
         case LOGS:
            new com.yiyiaddon.e.q.k.a.b(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2iztkyh25gl4r","1w0vofWreHXKemNXh08gtktjtL4cwbu/+2x+fhE9kyU=",1513925983043349478,3829809668035994899,-4696402972476812263,2396019735837631761>()) {
               case -1827658575:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.q.k.a.a.d var6 : com.yiyiaddon.e.q.k.a.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s132ljzadv217q","deMKaQsVGY7m4BNt9kiuanNMZ+2NOhQbRqaUaSKbZh0=",3284665081669711870,6676477390229190468,-1160629341562798934,8346695834550972629>()) {
                        case 1070110392:
                           switch ((int)com.yiyiaddon.m.b.a<"svpje5gty9une","6TpLfnAmvA4y71M+Zn5UYMs9fAqG29D4U1biMCR9+1E=",-2707778671524642433,-2635212823732578314,-4738220449513570429,-1166197225736202301>()) {
                              case 1795026428:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"szox24sv08wsu","v6rO6fXmhm0GUrJnARu6vR4BaIVp93pgkYsI+Erwxrg=",-310801573134546207,-1146128509266923613,6397901142060381106,-5056898239474764043>()) {
                        case -175671100:
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
                     (String)com.yiyiaddon.m.b.a<"s1974sj5ck49bs","Z6fiE477vYSp/ZJo7inqODU1wTK3f1Fdw6ABltCD38vbjKIe",-7875233951263399325,-171844304276832446,6636116841736742394,-8792993565892089719>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s9egk9o8ymuga","xqlDw0Y7jlgkfJWWBG2FJfkQZfT227+HD1tfDYkEF1H6HuivWKbAhmVH4o0cyVp3WHMVf5fbjU1tPmdvlTUEJ4QwJ+AhZ5r8VHeyySAXVTQ1WmyF4E0dgDeNU4p6PCVxrSG28dbZ+g/y3A==",1829460209308366258,2313766574034370632,-672157347407134836,3455592274927806090>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3fn8b6p46lmx9","wDV5nFn59UADMne9k06fudZRgAhFcpmxdJPt81IKK/I+7Lz3",8622242235047307894,6705634840118674418,7471679162611088324,-3669828537602588924>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3rtgtcsgw67y9","it+EszY1g+AKL2rzKYdLED4smGSZLMchjjLw2nAoMXs=",4320766717198274236,2226099329728394975,-7122947763998075541,-3819327902351403609>()) {
                              case 159039743:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jwdgqp6fhsja","HfeIrVx1H7DRpD0OuAMh/bIDk4Ar121N9vr8a8uOl0o=",279515651237652240,-1805130987408328995,-5238185899176506889,-4583101676597219731>()) {
                                    case 1189739757:
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

   private final class a implements g {
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
      private final String[] ag;

      private b(String[] var1) {
         this.ag = var1;
      }

      private static com.yiyiaddon.e.q.k.a.a.b a() {
         return new com.yiyiaddon.e.q.k.a.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>(),
               (String)com.yiyiaddon.m.b.a<"s3veueylxmxue3","usuzcRDzalyjeEx8HZlis1YUW0eZaQqhSH6DRA==",-7374548685051407498,-6099417866545553175,-3182294652629397930,436673618525633440>()
            }
         );
      }

      private static com.yiyiaddon.e.q.k.a.a.b a(com.yiyiaddon.e.q.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.q.b.a var1 = var0.a();
         List var2 = var0.f();
         return new com.yiyiaddon.e.q.k.a.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s2m3k2w2xtfwzv","TenIGEfQu00ZzKA39RmEN8+p3OEUtt310LHRyKqfEejlZ6QB4hw=",8487597576945672607,-39318206566918866,-4147908554868159107,-4183825113547919556>()
                        : (String)com.yiyiaddon.m.b.a<"szgewwj426fp5","TS/YV9XdqGVCW4Xb+/IMqHTuiTbBUQUkMmsagNQXeNoXFz6bnKk=",7613059633119175783,-6554490950826582182,-6011864390022882089,-5749342115786371406>()
                  )
                  + "",
               var0.a().a().af() + "",
               var1.a + "",
               b(var0) + "",
               c(var0) + "",
               d(com.yiyiaddon.e.q.i.a.D() != null) + "",
               d(com.yiyiaddon.e.q.i.a.E() != null) + "",
               var2.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"s3c1tcn1pz7t6s","NOzQHW1RLeYnjCPQo/xAAX0fqvnrxqYkOGoCzAm+z8JM+yHmapQZlw==",-2759287670958843550,7675247354978193311,3661839738202325714,-7247720155723447844>()
                  : var2.size() + ""
            }
         );
      }

      private static String b(com.yiyiaddon.e.q.a var0) {
         List var1 = com.yiyiaddon.e.q.k.a.e.b(var0);
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3a7wgzs4bzfwm","lV5/iCS6RWzdSLiuYHJiUzjXOsBH8RLswmXBeJUJBDc=",259900673172599892,683825668265564931,-3079067778452471254,6486712108137522229>()) {
               case -163484258:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s3uq906g9hgw96","8obMbgpE6O6ItQ6zgj/NOFP2XG2aRuCBWfigVnq9YF7TkxYWJyk=",-2327010066988850114,-7342895814046034162,56453422627217436,648244765436424767>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2bsa0dxmzgtwb","HMwxsedPYzYoNbieXBOfEDR9ZvRhy/+puDajxo2REpg=",5366588949133446997,-5721038317341981166,5620807856490838023,5781961618747089307>()) {
                     case 773280577:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var2 = String.join(
               (String)com.yiyiaddon.m.b.a<"s1r1islm9zmy4z","6eUU25oYFCG5uN8l4aGbuObLvY2qmgS3kaH+kP9D",8247712045670354417,1924726227548653318,2811710684038593057,1091456901486275880>(),
               var1
            );
            switch ((int)com.yiyiaddon.m.b.a<"ss4qvkwb38y81","FYUvIzR9SQQ1Qn3wKYNmYvb8ZbE2EgsboGczE0pHJns=",931582188289962312,7181552860528819346,-6059537378016947165,3350605595158606502>()) {
               case -1624372381:
                  return var2;
               default:
                  throw null;
            }
         }
      }

      private static String c(com.yiyiaddon.e.q.a var0) {
         com.yiyiaddon.e.q.b.a var1 = var0.a();
         int var2 = 0;
         Iterator var3 = com.yiyiaddon.e.q.k.a.e.b(var0).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3vd3gx4mepzlf","JhR+KvCvlAftHVHBgY5efeBmK4vTgYySKJl6ALcV5Kg=",309167022746259894,6885843611609044231,-166143302311431780,2655543834587061914>()) {
            case -140250852:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2t0rsab79r0q6","qGa1KNXbS9PoxhDPfb0BQBOgGOuEM3uCql25On+R2vY=",-6111705742078901281,-4145604219022158970,1561764384283020704,8785561584063727821>()) {
                     case -812985380:
                        String var4 = (String)var3.next();
                        var2 += var1.i(var4).size();
                        switch ((int)com.yiyiaddon.m.b.a<"s2mce2f384qm7n","F7qlingvnfC4LxxZTO7eDElnMm4u6slaqVMh9Y+ItUU=",5501797254374750876,4580314596785774360,-8860745437437653401,-4504087847200757282>()) {
                           case -10180866:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var2 == 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3n1qfirbyweq0","lsg/ZsqX9STLCOxivFMGUMAgolX0fbgE2Zg3zIolX1U=",-5037624693294771973,-4711398257547858133,-6342985663014640062,7696196833084906976>()) {
                     case 871916870:
                        String var10000 = (String)com.yiyiaddon.m.b.a<"s1ubpnbxg0yosz","qAKyXiuUtXqPALyfSVCXVVU/1sdGqUfg8UuDuXivl61MGwqIOOg=",1074224892189335111,-2666405110030462088,5496710704420545074,5527997111450413417>();
                        switch ((int)com.yiyiaddon.m.b.a<"s1ydop1orovy84","kQ1sAgbh5SYDOwEIUVpaDjtpuerxBa6I9YxgVLHI7H8=",4211580015135555039,-6198795767728738089,526070991472086753,-1370266098841217873>()) {
                           case 1676992111:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var5 = var2 + "";
                  switch ((int)com.yiyiaddon.m.b.a<"s27gd9m0j63pcd","hr5WI6AL27Wda13lRmTXLa/GSWxKEF2iZPQTG386q1o=",-7280673157100350667,5456313605100870149,-3932493373900966152,-3991947534111367177>()) {
                     case 856472238:
                        return var5;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }

      private static String d(boolean var0) {
         if (var0) {
            switch ((int)com.yiyiaddon.m.b.a<"s120cmfj2p1x12","LVV7qTKKTUDjGCr1RXkgkztXIQzi6lVFUPvks8dfedA=",1171167635608179995,8767535245710770659,5743250354950576229,-8828412983433309270>()) {
               case 465090634:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s2swkqli7rb40","VTXuhqtFG+NY0l2v9SR7n+MNmIxLjnILfDCey3HLVASdX8fFOcY=",-6251326784558022611,3926076872758500079,-5580709295674496226,691706987098773439>();
                  switch ((int)com.yiyiaddon.m.b.a<"swwbz449crdnz","9D1F7ec+HXT/TsJOYDQm2UGT2XxCkkPkR/Xf2w/SPYw=",-3677182914595440278,-9021322779451828318,-4337963321033870118,6953913914234304466>()) {
                     case 1028710308:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s3tplu8q8cgvc0","CuGg1TRB8VbPaQmNTwbVcLd4ExrYTKX9vMdiU73EcFKxVkm7psw=",-3561598153694866007,1964146903158115437,54503703474669103,3955470732466059229>();
            switch ((int)com.yiyiaddon.m.b.a<"so9kjv1y9uzhx","8CCFiXw1+WOv2BDs9q6BTpjOcYQGN+m7LxkUE3ZrhWQ=",3205515879453058375,-36644436113219876,6970811707120068038,7299972626393841281>()) {
               case -1558439047:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      private String c(int var1) {
         return this.ag[var1];
      }
   }

   private final class c implements g {
      private static final float eh = 18.0F;
      private static final float ei = 6.0F;
      private static final int ri = 4;
      private static final float ej = 8.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.q.k.a.a.b var8 = a.this.a;
         float var9 = Math.max(0.0F, var4 - 12.0F);
         float var10 = Math.max(0.0F, var9 / 4.0F - 8.0F);
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s216t19o4je1i4","o9EKIeJ+p/FssXfxXrS9HwjU15JfasXvO4LHLmhVA5E=",-7863220692557396190,7461335465267527351,-4906472723588198628,731804358481337146>()) {
            case 376046451:
               while (var11 < 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2tcdppkuuwnm1","7PJPXXGl/5QXgZQzYSNcbAQl1tbhM25VS2Co6JxPOQs=",-458452221675827077,-7412688495649075221,4375961910167737960,1705276959289447004>()) {
                     case -2012796186:
                        int var12 = var11 / 4;
                        int var13 = var11 % 4;
                        float var14 = var2 + 6.0F + var9 * var13 / 4.0F;
                        this.a(var1, var8.c(var11), var14, var3 + 18.0F * var12, var10, var5);
                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"s15gvhjkq70ccu","7I7HghOo4Ga6WP6fqq15QcXL003q/8HNVrOu0Ug0SUk=",8981039810996052705,2025736482156991153,-6136400488577793464,6061906058864252123>()) {
                           case 375252255:
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
         (String)com.yiyiaddon.m.b.a<"shidqsoznt8qm","FfqYAAQUsvqn8NVovBG/F4qeiEQ52c6LQYIPnOvOC70=",7538724682681987144,-305343045070192459,3882565880234604790,5423368226479742505>()
      ),
      SETTINGS(
         (String)com.yiyiaddon.m.b.a<"s2arzatmiiu848","COk/IzQ4mb1tfpvHxUY7YDxYRirxX1qRRWVk3FcIF0s=",5229088098250303254,-6164481192210726981,4170677520703421950,-2045166494806569179>()
      ),
      POINTS(
         (String)com.yiyiaddon.m.b.a<"sl0olb1r9tmum","xld2jmTrRbINqn+E6ncAh+YDxrxNVgMkK/WDppElzKw=",-3851517983654112541,8390035809346777417,-2647368620554560572,-3605708389538942174>()
      ),
      LOGS(
         (String)com.yiyiaddon.m.b.a<"s1oy62m5o7hs0r","cdRzMP2YcicyPV7g55lHvvAyEV4a1ec2JQL0NJ/Qd3g=",-1822033247756396067,7914850025497720995,-6506473242300624047,223145357176458473>()
      );

      private final String Ai;

      d(String var3) {
         this.Ai = var3;
      }

      private String D() {
         return this.Ai;
      }
   }
}
