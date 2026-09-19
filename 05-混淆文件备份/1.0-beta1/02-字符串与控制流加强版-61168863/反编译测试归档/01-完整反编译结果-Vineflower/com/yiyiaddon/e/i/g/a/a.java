package com.yiyiaddon.e.i.g.a;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.g.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String mf = (String)com.yiyiaddon.m.b.a<"solt1pi4vvkb0","ysBg0fek1OC+2Oo6Tho0OijV3xYJQjnNZY4EucYIbXeNj0LpzQF2iwMkf5BcYHKkAsoq1Hh8F1Ek/JstBuaz4QIeKzv+iqtWdcV2/g==",7972984105144502581,6571069008910064522,1598299354416911841,4688183844062021013>();
   private static final int fY = 20;
   private static final String mg = (String)com.yiyiaddon.m.b.a<"s15wrg4n3m16gv","+ytTtm0BTCiTnbeI/glI7+f8yclgPdhQG22lmmYFGdSzSg6xBfAubA6PDvkB5cCF",-6351877121082113110,6950813259500040582,-6498959759724044252,-9201682847637390581>();
   private static final float aU = 6.0F;
   private static final float aV = 11.0F;
   private final com.yiyiaddon.e.i.a d;
   private final com.yiyiaddon.e.i.g.a.a.a a = new com.yiyiaddon.e.i.g.a.a.a();
   private com.yiyiaddon.e.i.g.a.a.d a = com.yiyiaddon.e.i.g.a.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.i.g.a.a.b a = com.yiyiaddon.e.i.g.a.a.b.a();
   private final Set<String> B = new HashSet<>();

   public a(com.yiyiaddon.e.i.a var1) {
      this(a(), var1);
   }

   public a(Screen var1, com.yiyiaddon.e.i.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s15wrg4n3m16gv","+ytTtm0BTCiTnbeI/glI7+f8yclgPdhQG22lmmYFGdSzSg6xBfAubA6PDvkB5cCF",-6351877121082113110,6950813259500040582,-6498959759724044252,-9201682847637390581>(),
         var1
      );
      this.d = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   private static Screen a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"spnylxxhhgko0","Lpdr5fSDiV8hSPFnal1g/1VxoBjpQxFvRI83lSiy74w=",-1964682606705401216,6427872053007000213,7483268497474004927,-3328226146187560807>()) {
            case -43789827:
               switch ((int)com.yiyiaddon.m.b.a<"s1uc5ip2efdnu6","MpUKnnZIs4sGsfJA9YFyuahS0NU10vbtieYS5pYgLrI=",-7703074873469493654,832022782646649964,5892630246536132987,1393648114427113630>()) {
                  case 1523954690:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Screen var10000 = var0.screen;
         switch ((int)com.yiyiaddon.m.b.a<"s3h6q43v83dk35","2dAicdGhQSdaaBRBu5Q6/6O4eVxVpRHGV2d07xNC/GQ=",7334876267423804974,7059698587496489583,8725634451489500944,8188722714940022095>()) {
            case -199628413:
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
         (String)com.yiyiaddon.m.b.a<"solt1pi4vvkb0","ysBg0fek1OC+2Oo6Tho0OijV3xYJQjnNZY4EucYIbXeNj0LpzQF2iwMkf5BcYHKkAsoq1Hh8F1Ek/JstBuaz4QIeKzv+iqtWdcV2/g==",7972984105144502581,6571069008910064522,1598299354416911841,4688183844062021013>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3bwshpjqcx869","hOWFrp+c6pQ/aelYqsQnF1GVQ1OMW9Avr1nRLKAG1d8=",5816373824628081802,-893824681886568163,-6834027398026148171,-6527231307184171026>()) {
            case 1237185837:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s3ve3kizx6rrbc","sq26CjPBC/2VI7BMk2yfV2J6Sfgyfv21Ay9eIEDEKOw=",-3915626087456824481,3350592397673140253,-5755169690241076031,-3720407037909581305>()) {
                  case -1304448760:
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
         (String)com.yiyiaddon.m.b.a<"solt1pi4vvkb0","ysBg0fek1OC+2Oo6Tho0OijV3xYJQjnNZY4EucYIbXeNj0LpzQF2iwMkf5BcYHKkAsoq1Hh8F1Ek/JstBuaz4QIeKzv+iqtWdcV2/g==",7972984105144502581,6571069008910064522,1598299354416911841,4688183844062021013>()
      );
      super.removed();
   }

   @Override
   public boolean keyPressed(KeyEvent var1) {
      if (com.yiyiaddon.l.j.f.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s11npmgqs3ynnd","6QSNhBC/QlzlnxKmGO5O2VHa8xDzkWVZNOlZwESrQm0=",4277295372176988086,2932911505546218276,750044711460791626,-7554814911124406838>()) {
            case 958756219:
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
         switch ((int)com.yiyiaddon.m.b.a<"s3mrgf8bvj2o2y","NRPPIz2/Pi4Wy2fidMddR2IHDHgaKR3tB23geaky/xw=",-755548332836001372,8542656244796440789,-7213950388724544207,8474178570373415891>()) {
            case 221905024:
               if (com.yiyiaddon.l.j.f.c(var1.button())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2c29p1myboyim","Hmo+FLt6zRWpcXaxF5Qbn2/JxFdrr94OP0LY+ANxjZk=",3575315781855635490,-7835526898594056068,-7778224941505375629,-8550070374958044521>()) {
                     case 1397120410:
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
         switch ((int)com.yiyiaddon.m.b.a<"s3ezbisnwwudax","mcYra/Qem0GBG1Unzc8URW4hyTWZKE6mZBAZWFhvmV4=",-2279458856591428279,2769488148064557137,-4185724543501971430,7184356881177578992>()) {
            case 1290800935:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3dcha19ae2fug","lXwmI9NuRk8xzgqKVrYZcy0/FNH/qBNsgZvZoliqzy4=",3399244369737331265,4701426222185934809,3574643645175666393,-1753266435348957786>()) {
               case 501116485:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2e90twwg0ip7x","aTpk6oNjQHgRFZuvUVJ94rRnt+IyL6eTSSNY5uVWf8o=",4109239741189279112,-3529095980111800086,6769524503812744714,-4615470243699608934>()) {
                           case -1116202860:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.i.g.a.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3uvmgevdmalbx","R4pASDO0w/Yc+D30o/FGjonFnPVz96nNvKOI/3ZqFpc=",3398798147043636369,-1362527415733455251,-7316700861517316352,-2086046260078116081>()) {
                                       case 674494290:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.i.g.a.a.b.a(this.d);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sriluheobt4ta","B/C0RjIcv30UGCuiNlBD11NZ9rbX/fDwZIYOVAmkO+Y=",5156899481081880812,8565874361600293158,1026570384374022220,-8629908799972641636>()) {
                                 case -1575279513:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s102wbsprpz0z5","cQzqyw/497rMaQk4X0wkjM1+54OqukBXpqUhmdUU+Mk=",-2669446801279289265,8715164732424396147,2812722602352390811,-3885318427094316920>()) {
                        case -1315708754:
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
         switch ((int)com.yiyiaddon.m.b.a<"s7t176aa8xjjr","dmL4eoaI5wH1uwz3cVQ1Ai085XX2e/hglZ1yRWlgXfU=",230586135607216211,-7672865464892075292,5048377949226209911,535466680161331045>()) {
            case 1844999970:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s17houbw3j63ms","ko1uHIxkMNv7F3DjBQs3TMQtv1SIhw2Ee9cvUJB7uLw=",-6886159538530275554,-2580837185325185817,9113673142863600417,-4397039870250691808>()) {
                        case 310662129:
                           switch ((int)com.yiyiaddon.m.b.a<"snfo4s5yt17ur","OCRlS1Tjcoq1Rm0CV6moUBGzhaz5/IMANroazfy4NnU=",-3663924922282141232,-4585809750431301920,-9019446733732791426,4980597872674107679>()) {
                              case 1768490166:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2l34vqirrgo4y","67c47ZH4DaA4fGvbi8rZwtZZ5eRhJ6s1gNkDYNeJPT4=",-783935624798977359,6998738109406138228,-5033967208715392080,8896194786329223823>()) {
                        case -1679398150:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1upqg2kulo02w","80Iy0rtr08ntX0/CBdfwemQA/2p4A2phrgryd+Lnv9s=",5852503865326326448,-5452455831480026261,-5523090439676260376,-8462864737930030636>()) {
                     case -777164645:
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
      this.a = com.yiyiaddon.e.i.g.a.a.b.a(this.d);
      this.a.u();
   }

   public void eq() {
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"svtterowe7vi8","Rw76NxA06f11F9AHOlcbVfE/1yTMoWtzh0SgEofopIE=",3295323224452505654,-148157744503403838,5564095468122273682,-304849204844441420>()) {
            case 1761398207:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s12hg0xi3afurw","n5Ayo+DyP7cMGCYA3RLg/rKAjUryuWF2IGT7ihoW6jQ=",3986740319126447535,563029844082934193,8041390487969516059,800611864405172737>()) {
                  case -1197417285:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s2quwmy3a9ri73","ulSIHue6fWmvUQ/oc7hsWMVlSydYkFLE8mbDLivmHRU=",-136101123372215373,2095980593653534422,-7231049114822682497,142324116231888536>()) {
            case 2101947997:
               return;
            default:
               throw null;
         }
      }
   }

   private void a(com.yiyiaddon.e.i.g.a.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s4lngroul9uwe","0hO+Gkq44sWZDrKHpzrIggv3i7C6nVBv4MwsG0MWLb4=",-6761910782288165769,-3476058763177446743,8347863496976539446,-686298039906222065>()) {
            case -1314533907:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1jyi0i7gurx5g","kqkwx4STvz/LUCXjaUKemW/Z+ombVESdTkQa2xEKbO8=",-766819187714516308,-2919465305000898541,-7545835486173086329,-6643758160701893832>()) {
                  case -185321881:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"stzpw9rsreqba","enipV1m4XdPmf/YmlJF0tnbVyAFerxeorMx5au3H1Jo=",-5108824132469356592,-1137105449147157365,-5791228945544906048,8146971374755770780>()) {
            case -978129713:
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
      return this.B;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      j.c(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.d));
      var1.a(new com.yiyiaddon.e.i.g.a.a.c());
      this.b(var1);
      label19:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.i.g.a.c(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2ehpem1f96rm7","0FQkFAg/lttxsDQ+GsBKNMfYGwhAu9AQrj3cwu/BosY=",-7063983703358972886,6403062698822164490,-6553810806999319022,689500930493455142>()) {
               case 1770573101:
                  break label19;
               default:
                  throw null;
            }
         case SETTINGS:
            new com.yiyiaddon.e.i.g.a.d(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s23yzr402attkj","sj6vKPgkI1OKjkD5lcj8D00tyoInkxPp7GUdeeVHGhs=",-1131627971335443971,-4375303520437553917,7900575345953106633,-658493296323050832>()) {
               case -946883924:
                  break label19;
               default:
                  throw null;
            }
         case LOGS:
            new com.yiyiaddon.e.i.g.a.b(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2oeizjqzwzqgd","22YAvrBODlXApch8LgjzqZjWPARsXqYGosoCmodjZJ8=",8158229104717719626,-210178501554159868,6585659592764044792,-6991813763065692355>()) {
               case 1869022221:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.i.g.a.a.d var6 : com.yiyiaddon.e.i.g.a.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1t23mj3y67uks","0QZ8CM8xm/2SS11PnTrorrP9koULxKteZwIbj4pKjRU=",-4691218757680742580,-5873509658474687552,4342791012284854524,-4079094860828540589>()) {
                        case -681192436:
                           switch ((int)com.yiyiaddon.m.b.a<"s2f9qm5zktp98d","NpzSX+HFES75ZuKcZcdNttk6U1ooBVhz0o9a7cIk+Xw=",1487739064967906490,8646429295981200654,-8520543259480944353,7899103911912899836>()) {
                              case 965271816:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"sc6aa7u1uzy9b","RZcxFytYPuJOMwa+a0yxLs4UQ/1UC6dkU5M2REj47nQ=",2092271602391783315,-8154207797926829630,3922139097576869821,-6877528043013755544>()) {
                        case -1863649416:
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
                     (String)com.yiyiaddon.m.b.a<"s4yxxo38a9km7","4BrErSLoAnbPlwTBGL1khd7QT7Mp31g33Sy8AC/J2r0elzp3",-5254083199608029453,-9219786484117862510,6886406131834477491,7826801545733922865>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s3gcil8n8uof9y","NSU+6HGUyClaBIPfG4/0WoxizoxslzyJLJZxJQfSF0F0hYalr/tmVMCuQigE/bf1Csnq7UPqFpsHs3V0cgIju5hlLwmRtaxbE/s68xDDC0xHF5s5qaLoINFBo7Viatl+9iGEoQ==",68372893187980228,247692096195320966,-4260470062436545750,-959552155965159777>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.d, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"srggp92hjxpfa","jmhBQuui0tiDMUz3NgYwyNzvalx/TGvOTf4Bzmr5JiMC9MfQ",7477938973316277362,-3958988035788247205,-405094377871956657,5885954840580515281>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3u5dkf43gx86e","x3oHb058SSj35S+I06QywmucNLBwbYsES3Ih/cZBDd4=",7781790648847723195,-3648191257062553699,-3479914831700520804,2074341996085086153>()) {
                              case 1030355467:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3igm4xph8go03","jraJwugRIWRaeq3M4ZWsZOhPAoCLZz00vSpTZhc+CVw=",7586532679806266370,-1025239419990418507,7380674669592839799,175194031810723449>()) {
                                    case -354083614:
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
      private final String[] H;

      private b(String[] var1) {
         this.H = var1;
      }

      private static com.yiyiaddon.e.i.g.a.a.b a() {
         return new com.yiyiaddon.e.i.g.a.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>(),
               (String)com.yiyiaddon.m.b.a<"s30um5bazrbmzn","abAipctGmagUM1Lv63dBQhoHeoyDmUWofaIzVg==",7122266807181084361,2245978579545770486,-1481904720831054358,9054832287914951901>()
            }
         );
      }

      private static com.yiyiaddon.e.i.g.a.a.b a(com.yiyiaddon.e.i.a var0) {
         if (var0 == null) {
            return a();
         }

         List var1 = var0.f();
         com.yiyiaddon.e.i.b.a var2 = var0.a();
         return new com.yiyiaddon.e.i.g.a.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s2py4dmrhc8fw7","3U7Rp/oFSnXSgwEKPtjg2Leme1X4pmhszvyvIfFQH0jgN/Q5p3Y=",7717649633377516322,8853842401425182418,1963635275585219768,2121912392643385512>()
                        : (String)com.yiyiaddon.m.b.a<"s280ancbyoqa2l","ro8RKNlToUWBFpD280zX9+SvDhZ3QjOPGYrASYei8FSi06Zrr+o=",3764812075102743159,-4100889722831108075,9105312123296329155,-8158238222325754409>()
                  )
                  + "",
               (
                     var0.g() && var2 != null
                        ? var2.m() + ""
                        : (String)com.yiyiaddon.m.b.a<"srk9therx02n1","hes08OLcrivY1yvLGoyXhYBAh5IOHZPDCoS2CjdbpiirYlpBMkRdsQ==",613588013944886439,186790872631307680,7420265547032659366,-8245022687942560116>()
                  )
                  + "",
               var0.a().af().size() + "",
               var0.a().fj + "",
               var0.a().eS + "",
               (
                     var0.a().bC
                        ? (String)com.yiyiaddon.m.b.a<"s1ovbtn6dmrdrf","h+nv7VB1YnOz6m4N4VjxAP0ZVba5rqPhY2wmzHkX+hjcNPtwjmA=",137578103042094271,4350704337175415524,-5727490185267799564,-7190809917796991592>()
                        : (String)com.yiyiaddon.m.b.a<"s29w5zybtzy84o","oChKmetIwuY6JO2yCdlNx1f+Lf3PsmDqL8gbDukIaCRNaW1LUms=",-4318955609143477371,-4976811134770982083,7197320627625222,-8543838481931746491>()
                  )
                  + "",
               "" + var0.a(Items.LECTERN) + var0.a(Items.BOOK) + var0.a(Items.EMERALD),
               var1.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"s3ckalhn0nxvx6","mAfheX7P1bnRrOGSH6ExupnCIDvoNWU+nd6xDg5qwOCeNONAkKMSeA==",965953072361498782,8639996880831098296,-8044379978498840393,-783673313593471874>()
                  : var1.size() + ""
            }
         );
      }

      private String c(int var1) {
         return this.H[var1];
      }
   }

   private final class c implements g {
      private static final float aW = 18.0F;
      private static final float aX = 6.0F;
      private static final int fZ = 4;
      private static final float aY = 8.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.i.g.a.a.b var8 = a.this.a;
         float var9 = Math.max(0.0F, var4 - 12.0F);
         float var10 = Math.max(0.0F, var9 / 4.0F - 8.0F);
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3qd7ad8hg3bky","FcEIuWVF0Hf8i+Yi54+KtkGAJ/v5mOlIjE4rjvU6asU=",-3584822878443049564,3820806047345840983,-3633880618854037835,-8073499185530573226>()) {
            case 266135112:
               while (var11 < 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xjp8b6jmubqz","X8aPu+zFZPi1bT1/g/dYTSSNDSyljkutnASAkJUTdw0=",7873229063345370984,4467687829123441153,7992573121356482311,2688768878107315691>()) {
                     case -312151680:
                        int var12 = var11 / 4;
                        int var13 = var11 % 4;
                        float var14 = var2 + 6.0F + var9 * var13 / 4.0F;
                        this.a(var1, var8.c(var11), var14, var3 + 18.0F * var12, var10, var5);
                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1ew0cu23rn6we","6SL6vVnR3n4XdukfqtMpXyAkY5+Z6HIsrPYXahOLhFM=",5607775680463062594,-8380883744597401290,-6531172644109352166,-42112717748158887>()) {
                           case 814020500:
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
         (String)com.yiyiaddon.m.b.a<"s2ts6e4jb2d2r4","HCjKC/7dVS+wNpEBoBH4q5CMfsprWv1/r3CzTOZPgIo=",-8063164064677252500,7480554446132664399,9209768520719716624,447898834761075353>()
      ),
      SETTINGS(
         (String)com.yiyiaddon.m.b.a<"s340impf5mz99b","zanYO3f4duec0wK0H4VEYcy9P8gS8zdvBbPIjyDF1oU=",-2464276194079644989,1917374287795006112,-1170957238478929844,-5163646767522498945>()
      ),
      LOGS(
         (String)com.yiyiaddon.m.b.a<"s1il5mt725c1xp","xiE4bYLv2JQj/CkaB3oeHRylFZQ9Ap1LBB/KtncjOVU=",-230914547637925082,-5423997679917758951,8986602087683463185,3167145015597800110>()
      );

      private final String mh;

      d(String var3) {
         this.mh = var3;
      }

      private String D() {
         return this.mh;
      }
   }
}
