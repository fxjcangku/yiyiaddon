package com.yiyiaddon.e.a.d;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.Entity;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String aV = (String)com.yiyiaddon.m.b.a<"s3nqa4yofgqk2e","t8Maf/nTOp9h4zwJQwZwSn7a4Cfb+I/b7k45lHjDUKQ6ZvpMqrcX7NFAvjyfzcixokM3w9q66k9XsoEVxxfejghsGWwkcNU0KXuVk8g2Oqo=",-5095518850638310147,4672519909145975262,-4809047813846395150,2686593253928086561>();
   private static final int D = 20;
   private static final float f = 6.0F;
   private static final float g = 11.0F;
   private static final float h = 10.0F;
   private static final float i = 12.0F;
   private static final float j = 6.0F;
   private static final float k = 6.0F;
   private static final float l = 320.0F;
   private final com.yiyiaddon.e.a.a b;
   private final com.yiyiaddon.e.a.d.a.a a = new com.yiyiaddon.e.a.d.a.a();
   private com.yiyiaddon.e.a.d.a.d a = com.yiyiaddon.e.a.d.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.a.d.a.b a = com.yiyiaddon.e.a.d.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2e3urua4lopvf","A4EM9kr740aq1kyuWTIH17w4FCdSRlGsnEStgYbd4fE=",8780828661306604843,6511015142772127722,256306011701894333,5971281569373666289>()) {
            case -1827602994:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2k51kx83nifrq","sfzxpFxC07jX3pv1imiM2N6x+k5E+mCyxboBqjlSZHU=",975758212347876569,-5941936659649252971,-1901443856251814692,-1279911211498591737>()) {
                     case 963490994:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s2208qmy1584uc","9qyYpCxW8JI0Gc6nkEWCK6BA4dxq28exE5iWdnSwAQk=",-8654506915384373536,2351794919061223294,-619059140490098556,6793347616588159878>()) {
                           case -879750222:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2c5m2848v3n4p","Ym4UVKSLl1+Iy6qLIdlLxdq1Qcsxs2ah1aHrCMyDlKo=",-2342159784260246759,1437452507749458520,-9135084851822097969,-3935695745112489198>()) {
         case -1922111890:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.a.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s9ja29r6dkvv7","6WryJ0Lq6DBvXMCBOGpDCWwJcdWp5A9dSP+tvJenjl57ZzgktC7FXk+3DCM=",6913359252277007078,5881225712674589232,-2285239005545922416,-2699854532814937461>(),
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
         (String)com.yiyiaddon.m.b.a<"s3nqa4yofgqk2e","t8Maf/nTOp9h4zwJQwZwSn7a4Cfb+I/b7k45lHjDUKQ6ZvpMqrcX7NFAvjyfzcixokM3w9q66k9XsoEVxxfejghsGWwkcNU0KXuVk8g2Oqo=",-5095518850638310147,4672519909145975262,-4809047813846395150,2686593253928086561>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1t31u25oz2lky","OOZzPX6uh0TxDyvtsoKhDGdvESvKQopMU2AxGF/RQ+E=",-2222200184403005515,-4612616523402419194,-5625265473001385708,-8229533198353881407>()) {
            case 1005530118:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s11xnhk199sfll","Lr8rw0FF5se9RfNNgcxMU6taVtaikPmg2pPO0JoU8yM=",5501400710220410464,7267221037605396369,1157853044838743707,1466742437910745884>()) {
                  case 585413496:
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
         (String)com.yiyiaddon.m.b.a<"s3nqa4yofgqk2e","t8Maf/nTOp9h4zwJQwZwSn7a4Cfb+I/b7k45lHjDUKQ6ZvpMqrcX7NFAvjyfzcixokM3w9q66k9XsoEVxxfejghsGWwkcNU0KXuVk8g2Oqo=",-5095518850638310147,4672519909145975262,-4809047813846395150,2686593253928086561>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"srtiaqi1fqeva","/DrIJDzUE4f9fDM4z3E7/JnBxUTQ140XY/ea/L7tHpg=",3936382067334518120,-1432918193865396788,7700217996101828996,-1841821674781780249>()) {
            case -1819092463:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3jtfrm5ltjvfp","ZRayxZ19nJe0KzTeuPgllpNgZIW8FrKCBD4ObaGpspk=",-5142874669536977410,8590393554269712486,7900043181607108939,-1724825091568711322>()) {
               case -1635137289:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"szv98mubb0vhz","fPSLpl1Z+Dhrbloy/oZNFS8YZeIShRvl3dgy4SENK0c=",-5822472281294309112,-2754139575833837192,7611191302688136510,-4848267034509369411>()) {
                           case 647333819:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.a.d.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s14p2n4d3qj6ei","x1V7Yl+YBXwirCkXITPpMUE43vGryLAjL6EN+YrnZFc=",-6288461104056422030,-2462191813551496806,4112727604782200439,-4250007555511281750>()) {
                                       case -1618780601:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.a.d.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3lphyli2jxonf","eXd+hLFyRvNG1p90FdIKE64i0zBHEQqQ0JPnRtuIbDM=",-3043883305526315786,-8899109107733702947,-7177861134127663044,5398243146327300971>()) {
                                 case 463387537:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s3sqbrovzl1kd8","7+/lqmc9KQ1LvjDsszCabZ3y+zdHd+Q3j77ZUEa4EOA=",-4469607128779820043,-7392788411715696716,6524041075238365037,-8845584887571082229>()) {
                        case 1376880378:
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
         switch ((int)com.yiyiaddon.m.b.a<"sk7ilh55onbpl","H5RRSkv+ktI5OfplbJZcsCmoNQP3PxIV9ZJyKFgr0xU=",-3520761212385173003,2891814072319520269,-8364914887607871863,-6558332780501247930>()) {
            case 921259926:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"sqadqcy73ifho","ZU5CHdusDK8T+EEEU0eM2l60pYrXhBwc088YDsxNE7s=",5657460569857215553,-6797325342490627059,873196516243713395,-8796238720271826506>()) {
                        case 380675925:
                           switch ((int)com.yiyiaddon.m.b.a<"s37gs0v51wlyak","JRNVAslZTcT04CRDQ2cIjsEwWNheRZYISazU3oqPPTU=",7402627197884341517,5030923275128244185,-2010572300502445277,-1430445167395703748>()) {
                              case 1650913696:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2upgzdztug3ep","vTsbxz1CdmjcyihEicQI0746OHCrykJ4jNHvaVDsz7w=",4364117670025432509,3330034780022547502,-6176771035030986050,6133455730428775457>()) {
                        case 1859145508:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fpns3obqwpy7","qmEGeHV87AOvORKFcN8WuzP51nQDibfOAP4+UVL/mQU=",-8575492166217514019,-6058284314065352453,-112719409506104922,1189130768560884119>()) {
                     case 1078850479:
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
      this.a = com.yiyiaddon.e.a.d.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.a.d.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10emvn9p3n91e","ra3YdkdqRMQK1ypv/v7jNMG+89HDApHyTlJoWnaw9Bg=",-4882292554538032390,2082011608745447574,-4114458085295413119,-6377625098590274449>()) {
            case 2021878821:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s155yzm77royav","E+6TfMR9jl+xDhGGMNrIlkIxxFthZ4dhrp/pO2D9Xao=",4987232341996474370,-8109968140619113572,-6662104270729050465,5984647426535685506>()) {
                  case -867516380:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"sadklc6v5n5fp","q81YIF9TuxgCcMeypwnqzFh78qq+FP0F5cqJ1bhGdUY=",4678232408333502887,-7503865178696091029,8022414238099609242,6970570550476045545>()) {
            case -46290034:
               return;
            default:
               throw null;
         }
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   public void D() {
      this.C();
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.a.d.a.c());
      this.b(var1);
      label23:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.a.d.a.c(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1wckefwfc03wt","UPp+oiFnzHXOtbLvMpw/9BY3Ra/cFcdgBO8mp+J6PgE=",4343599405796071005,7343541323736145425,-2055366230944265114,1924164921212756095>()) {
               case -1208357321:
                  break label23;
               default:
                  throw null;
            }
         case DETECT:
            new com.yiyiaddon.e.a.d.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3i4q20vp2cu9h","QygKZKW43TO878CFO57Bt3ADW3nZ4waIQG1h3Y2j/ms=",-920034479975304152,-8494550560047709584,-7509399575004354651,2507584693508592443>()) {
               case 1143866828:
                  break label23;
               default:
                  throw null;
            }
         case LIST:
            new com.yiyiaddon.e.a.d.a.b(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sci58xvwaog05","d8XzWxEYEFZobZDXe0zs/UlJulVCZfdUktW1zu/nCfc=",1106295198432601742,5230094314107380287,-5568763430444264669,3887262622362906070>()) {
               case 1852341858:
                  break label23;
               default:
                  throw null;
            }
         case RENDER:
            new com.yiyiaddon.e.a.d.a.d(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sc5m43hflg1bh","UR1AyjDLWw2/BtIy1ieFYJZ1ZLK5H48LwxZoyBj8uVY=",-3718081689216375979,8019150098795348022,7630199708712996273,-7978108788137484865>()) {
               case -683044356:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.a.d.a.d var6 : com.yiyiaddon.e.a.d.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s288gxl3r6vmuh","/9+b1dwKZjolaY6XAdept2v/C8gQd66fXY0tcYHfn40=",4842846079312081686,-4956924415407080104,6740372233476351916,4272814889544183018>()) {
                        case -1653198494:
                           switch ((int)com.yiyiaddon.m.b.a<"s3osodb1cxc9x2","hS4WlGzd4Y55CsJmeyszLSogReuSHdEOCn3tVOur3ew=",-5721551800918898068,8629469619397175547,-356607636240181342,-3748184176966056629>()) {
                              case -517127875:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"sqr2dx5q2l45f","9z/z/rfl9rPaiHodr4qemJB12McfQ12T9l/fGdooiDw=",1803607692288520730,-6553944726069427205,-2385393045314958039,2094222585705182994>()) {
                        case -677734895:
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
                     (String)com.yiyiaddon.m.b.a<"s3nq7r5o493sux","ujpahr5Osgrdk7erj8jjVockqyVQcX75kL8Unzey0jlST+rR",1448588491197960178,5044225432694186016,125894060586949607,2553359136807882931>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s1xcndbnrdypw","l8wrrJ4aDjcrTorkal05YdIQvJdU1Kexcf73RM48eLeXCK/OZRdMRAddRqhcp/pGR0Prh+tJlsrpEzeV8KDCaNhoq9RaDnhN8agYFLxGcMXvm7f7gsQFfVb8g7MxkW9ARmN6yA==",2952063060356140352,-5953815548035108945,3220103874485105849,-9211837737143025818>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s39l2lem9j00f","NGyMyu8c2w4k0rZFGY76597GVlWBFC/PJSpecRpJuzrwj3LG",5054817540997287645,3038900352795832226,-70029942871802249,4712950899619389066>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s14o041fak5nvj","2keruCxcQAza33vFh6DlgzVowzVZRX/F2mSSewYcjoA=",-522089055115771867,5440597943072471307,-920995405752362022,-814762923971954522>()) {
                              case 1643395369:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3263ozqwoa49i","cvIS5tHFfQ6O7Ei8rlqxL9FoaicDqi6TgcaEOmMLdeM=",-7098797969884936318,-894831524460289878,-6936414226442375302,-5189952670080879242>()) {
                                    case -1908113096:
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
         (String)com.yiyiaddon.m.b.a<"s1wg2qhtcg6rbb","G8g7S8+Z7+6DzmD/DEPOhH2XScuEYDSLoJbpzn8E",1486613257939672170,1159784583923113295,-3904075851072154953,2318008722689222867>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1csk6lbwz2xuc","/9IOgvKYdBJaZKPu81AdpqZhaytoCURRL3o7f0vXb6I=",-483347836193574926,2278053104162364648,-4373755680916955732,-2117256743327344533>()) {
         case -916210079:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s2hwxmi90m4giy","6yP5ngMlRTFUGEVDNhovVMApluRKuxSFv9SvnNQoihU=",-4999042046224873447,-1491026124994303915,371873921842797919,7291780502137680783>()) {
                  case 1381556967:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"stmytis6zqgyi","qUa4NgWRmHZof4B2TkpcMhZuTJD+6hQH0TE+qyLbdeI=",-4833782630639206314,-8199550261830936990,4005891567078132244,-6521026935082688828>()) {
                           case -613323272:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s2ivue1hi9s98l","aF65z2g0N6ljJs730eotw74fhVKQcPjB67dLQw==",-2901401972093516214,-3847183283267576545,-3849388366776872000,-5333166421368336967>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"suyngivjh0exq","YzHEYSJgO6CJWOtdNPT+gLa4mtG7Klv5/PywazRD4Po=",-7905355194813848463,6390119543108422062,3208523417866939253,2069943435752933623>()) {
                                 case 1684729986:
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
                        switch ((int)com.yiyiaddon.m.b.a<"s1y9rerdpuyzok","6RDfzqhQ0LGt9LUzQdlB4hEU7T1FX/LjUL4ncXBD+Es=",5045619145613000185,-6400865142763675480,-4902565110965148830,6956102185645376044>()) {
                           case -1571555587:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jkm4pl44zs63","0wUI38Fc483m1Yqt7iDhz3Zc/rJuLfWDcORlNFVUkRk=",-2781002747088870267,262170081244357557,-6157061608451145468,-2862061792383392566>()) {
                                    case 1655941491:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1qjaezgjh4eq2","1+dlIbzg8RvfYXEX2kx6wtaxmy70zd8mD7CzWZf6+fY=",264824514391047915,-6922089050525386102,1777041506649341952,7107742766435396052>()) {
                                             case -219950337:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2a2966gqxyljc","+m+KkisgB2hCJoU2GXWLaNF4MF9WUR9YQkrQ6AXIx98=",-4381408378293297367,6497805999977224567,3594869528063382341,-1635812481024607301>()) {
                                                      case 623463035:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s21wx8x0j47tvy","crR+JGOBYEiW/VX6+kGNj/JZWLN25Z/UxrlyJMo3D8c=",-8843497134019120811,-488940692974494815,-4260480650257071078,1882067089517383989>()) {
                                                            case 1130438094:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s3bmtv92148mqx","PcrE5kdKZgvFMecri6WlfhQprSzEiBUF4xwLuvhHr5A=",-4428483348138110405,8524795349609231107,8575966294709075853,-3027968405230268187>()) {
                                             case -896348743:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2z1rdxoe2cnxs","oI8Lvdkx4KqwStQgFDoDJYbgg6fjs/+Z6YxbMNUSWPc=",-3633098637138368523,4278790324757584159,8907143405183758168,-5982480992628239701>()) {
                                                      case -897333299:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2ril8q48k2879","0dGZ8xxPTaplGaGnNUPZONol6H4Cd5wUaKGzfmFCH3I=",-2973008194329136993,-6526108596873972930,5960807852604275416,-8686475730475291247>()) {
                                                            case 1923636335:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"s3h7rpor960ecq","59vPhZ9l24L8shE6I7aIIJ81sEePZpDbJIYx49fNkks=",3011311729067655073,-2768099885642103733,3421703150412644703,5060523068593447829>()) {
                                          case -1104362499:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2jg3sezlbmblk","elJzGaQ3/qrsOkwFwt5N8V4dvtpn9iBXdAt0LjCaCy0=",-7419047650435052363,-6371042707748254931,-2182437997515805511,-5155472163422734801>()) {
                                 case 1868195283:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3059jcj3l1ayo","qG56KrFOKDNc3ZaP54S6ar/UyH0C8DQXMj71LTR4otU=",-965590183753061901,-2175750108824211503,4678821040922125760,-1330638825054143205>()) {
                        case -875525148:
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
            switch ((int)com.yiyiaddon.m.b.a<"svcopw5sdfl9j","2KXXDBfkDwTLhS1MqnTm5Xeb4Lca7QLjaosKOhP0ySw=",-3168922711126869906,3517178014261497082,-5509890438015147543,-7990833931023374546>()) {
               case -795189947:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s18rexy350eopt","loml1j4OwbUSKdLGMruhY6pZR6HvDYf9WykPEoe3p2M=",8853614463404208989,5866384115841831018,-6952437403075260745,7521979018768747555>()) {
                        case 2021741121:
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
            switch ((int)com.yiyiaddon.m.b.a<"s1uu54o0tryygi","rLNVBC+BvmERT+qxZMGy3tqGiTnWSG3gh43Mf/58qZE=",-2883381851975046139,-264177757368679019,6348378974663432151,-1978863602487606264>()) {
               case 1062045287:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.a.d.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s2r9tclqifl2s7","faEKJmTFSKAjN8e69kwRAXyNO932AobGJ51CxrC6WDE=",-2032220279180051463,1980460195296089170,-3397121499668454120,1259638637638880564>()) {
                        case -525454513:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"shciaqx13fh04","WRbAU9bBctSqk2MwzvuwXMmLmlTGgLDJ47xkO+UTvuI=",-1650557438766457199,-5592237179123296032,819635757156249697,7407715830249333394>()) {
                                 case 1404956626:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s6nvf0wsid8f9","Jzgb/xZ5QFdZGkwJihSGiTSztOF69daOEIr4pAswJgw=",-7914003323675088100,-7100845522904296103,-1737916966328979823,-5733530741388392778>()) {
                                       case 269474561:
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
                           com.yiyiaddon.l.b.j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                           com.yiyiaddon.l.b.j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                           com.yiyiaddon.l.b.j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                           float var12 = var11 + 6.0F;
                           int var13 = com.yiyiaddon.e.a.d.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"smbn1ed37go1e","U4fkALKnFOClNFlC7St4+nkt1yC8jRFpfzpt3C+mEns=",4708073205749254018,3842071242679673151,-3795192889339155714,-843755708860501649>()) {
                              case -294289047:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3be3ybtdnd47t","b49CvQeya7hh7PtCEY83r3qkFmVLyGwragb181gwyaI=",7127800819496354776,7246973705600298001,-4685804314801468058,-1827431830711492660>()) {
                                       case 517561898:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s197vxqd4uqcz3","4E+my2NjNL60yCW7fG86gq2YHa9PJzGI4PT+2p4TL5E=",-5262744049050852597,3414151930810063874,-7567061951362329261,5366660487350810847>()) {
                                             case -14352788:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s39w8bpuh0441l","S14tdriXMy+ADoxoUFcn45BVVRCp+Xu9P8AHpam+nxY=",-27082876569054555,-4547837582514915791,1158453557427196077,-2136880070100647682>()) {
                        case 1988844459:
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
      private final String[] b;

      private b(String[] var1) {
         this.b = var1;
      }

      private static com.yiyiaddon.e.a.d.a.b a() {
         return new com.yiyiaddon.e.a.d.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s1o9q364wwukkl","MVz5fuhzO+AsviJxn1HFkZOYlzb6KuZqO9BQmQ==",5409003197839092793,-4275567170219987886,-469488927927758639,-4784429932096208483>(),
               (String)com.yiyiaddon.m.b.a<"s1o9q364wwukkl","MVz5fuhzO+AsviJxn1HFkZOYlzb6KuZqO9BQmQ==",5409003197839092793,-4275567170219987886,-469488927927758639,-4784429932096208483>(),
               (String)com.yiyiaddon.m.b.a<"s1o9q364wwukkl","MVz5fuhzO+AsviJxn1HFkZOYlzb6KuZqO9BQmQ==",5409003197839092793,-4275567170219987886,-469488927927758639,-4784429932096208483>(),
               (String)com.yiyiaddon.m.b.a<"s1o9q364wwukkl","MVz5fuhzO+AsviJxn1HFkZOYlzb6KuZqO9BQmQ==",5409003197839092793,-4275567170219987886,-469488927927758639,-4784429932096208483>(),
               (String)com.yiyiaddon.m.b.a<"s1o9q364wwukkl","MVz5fuhzO+AsviJxn1HFkZOYlzb6KuZqO9BQmQ==",5409003197839092793,-4275567170219987886,-469488927927758639,-4784429932096208483>(),
               (String)com.yiyiaddon.m.b.a<"s1o9q364wwukkl","MVz5fuhzO+AsviJxn1HFkZOYlzb6KuZqO9BQmQ==",5409003197839092793,-4275567170219987886,-469488927927758639,-4784429932096208483>()
            }
         );
      }

      private static com.yiyiaddon.e.a.d.a.b a(com.yiyiaddon.e.a.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.a.a.a var1 = var0.a();
         return new com.yiyiaddon.e.a.d.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s1m26r28d19ilf","5rYSQHXb6gPfI2hfB2XCo4MLLmZ4faTWblWiNGZMdlAS1vsq2o8=",8957284120597084397,87216837807627603,-3925683030419371927,4856136903101448937>()
                        : (String)com.yiyiaddon.m.b.a<"s39f7exibz4zzp","xkhFDyYUHZlVmEOYzhCdSkUQ0au1Mbq+M15nRmqGzf0LmLOEIDY=",7594831694465963871,-3558688854830156721,-5545271025981761411,-8739364214278741414>()
                  )
                  + "",
               var0.i().size() + "",
               a(var0) + "",
               var1.B + "",
               "" + var1.d.size() + var1.e.size(),
               var0.o()
                  ? (String)com.yiyiaddon.m.b.a<"s1som99bmw8hj2","hakTZe2EuLTKblxOhTReiuJA3lhthMR0Lho/oxcBOiZY3Yh7+5kZXvFQy3brDUf3",4406271269686151003,-820401174362594699,3495744069168405205,1332502076373119126>()
                  : (String)com.yiyiaddon.m.b.a<"sdvi2jtaxa83p","P0rkfv+8TRrEUkDEvgoLB3pNgy8zyAyWHolnRK3fPGK0nMdKPE6SxCpGDVlSNg==",8768726875434219091,453113732281044027,3258677378326095030,6257466076829947480>()
            }
         );
      }

      private static String a(com.yiyiaddon.e.a.a var0) {
         Iterator var1 = var0.i().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s38hub4p8pq2uw","ZFGFunIBouApjtAQ3Yts2s5lFrkv532tOqlKRb5KErQ=",-1401451386215934391,-4597988533397789450,-9037988380891625029,-4277795044416254149>()) {
            case 1155716695:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1owd9q9dveyc1","r+z9mSHvdSIBH8uOcjgDK0g4Um+sm+Cvhf4Y1U8GPuE=",6067397577936487270,-8936951075338452624,1659667786419474265,5820560597244989923>()) {
                     case 438937665:
                        Entity var2 = (Entity)var1.next();
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1lbflgv8zgifg","5mIk0HlPTL6biya0PQBrj2/5Z1G6b5/WC4eVx7sOh80=",1804245303746209931,5152887168220658542,1082878284100983537,5202786496875743653>()) {
                              case 1306934309:
                                 return var2.getName().getString();
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2fgq6n5cvqcrb","RDekqvd9T5uAAxJ+eCcFnTr4VLPKxLDb6fnsSUmWkfM=",-5705525491500423468,-6373132534404515836,6284893243070292924,2023135781710750603>()) {
                           case 1219298681:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return (String)com.yiyiaddon.m.b.a<"s1wlnvac4czwxk","SdPXTf8fFSP9K/jQlM8WS8RcQtbc3rqxmFCPc3YyMhYGxA==",-1593828220458309319,-2491429746353487860,-188603343585436678,6082271539651805226>();
            default:
               throw null;
         }
      }

      private String c(int var1) {
         return this.b[var1];
      }
   }

   private final class c implements g {
      private static final float o = 18.0F;
      private static final float p = 6.0F;
      private static final int F = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.a.d.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1f8rzdagiati4","Ijgp6wX5GCdoG8gMob3wOH/gsMNgRX7dnTNhpCyG8O8=",1822852110760854881,-2711647252198047844,1845819469883476836,7263561496265369473>()) {
            case -1219852715:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"segkx5v8032kj","IwElMIvtKKAnOIlNXhP1Q2JWivCI/GwBg1ZaeB63uvo=",2705884337249339824,1091890589337375564,-1326767958798373430,-4666086254664838355>()) {
                     case 2088085826:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"sjk7z46cwadsn","hS0tOpk3dT0JFzaFOLPHnxtQ/WfoywV5q+vSA7M235w=",-8465476664049439906,6109673999409779629,7248089347593845172,3762248995575741752>()) {
                           case 1363304055:
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
         (String)com.yiyiaddon.m.b.a<"s2kzbenc5m3sai","9oyR8rMBhzg49JBKYv80BqbM4Vk6jYimYfkcjvTAzj8=",-2419628099373638143,-8698294643717625665,7318165791100161771,-5780575561773258081>()
      ),
      DETECT(
         (String)com.yiyiaddon.m.b.a<"seu54bygcb3fu","0mgB1ZKDKYeC81mEeQbomSIONxc/M5abKBCF3s+jjlQ=",-2969300777018942927,1116530043553221897,8938879671025138760,1467002454259066741>()
      ),
      LIST(
         (String)com.yiyiaddon.m.b.a<"s2pdt5rm92y5z3","WT91LNn5ifdFVGhAaKfYgGdcMop6e1TuIi/BvC44JDQ=",6623129310570001495,-5733301418279735009,-4530969904158653637,-4927613329592720601>()
      ),
      RENDER(
         (String)com.yiyiaddon.m.b.a<"suio8hbqt4vlx","y7tLSq5J1Nf4crcNCPJIm0Pbjka/KaCMUlKkS7cVXO7gIcMB5R8=",-7505513122383533553,-1304836299729137070,-2966938089260789229,4524695201833580684>()
      );

      private final String aX;

      d(String var3) {
         this.aX = var3;
      }

      private String D() {
         return this.aX;
      }
   }
}
