package com.yiyiaddon.e.c.i;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.HoeItem;

public final class m implements e {
   private static final int bx = 2;
   private static final int by = 2;
   private final com.yiyiaddon.e.c.d.d c;
   private final com.yiyiaddon.e.c.c.h h;
   private final double o;
   private InteractionHand a;
   private boolean N;
   private boolean Q;
   private int bm = -1;
   private int aX;
   private int aY;
   private boolean R;
   private int bz;

   public m(com.yiyiaddon.e.c.d.d var1, com.yiyiaddon.e.c.c.h var2, double var3) {
      this.c = var1;
      this.h = var2;
      this.o = var3;
   }

   public com.yiyiaddon.e.c.d.d c() {
      return this.c;
   }

   @Override
   public l a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3at4yvrhbf4cx","8STS7n4N3iZ0n61CYD3ULo6SUqXpl2EDjVne6k7zySw=",-7198574600109802629,197516626851074411,-2893065941453892714,5924579358254840884>()) {
            case 1000258867:
               return l.TARGET_INVALID;
            default:
               throw null;
         }
      } else {
         if (!this.N) {
            switch ((int)com.yiyiaddon.m.b.a<"s123j643isonj6","Xzsb5nkYcSCqXsQYZoW1uUN03bHhoNN2nsI53YY5Y24=",-7645207628642275347,-8773886955682163882,-6408045971118418859,-3086892061709841244>()) {
               case 331607899:
                  if (!this.h.d(this.c)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sonjlb7a4hr42","k9791Um8CQ+3Z8WndAMv83CihOcBBhXqmfWtFHWf/C8=",-8179811563228098148,957508965545222987,-6470839241687049779,-8645068616359056417>()) {
                        case 1643874733:
                           return l.TARGET_INVALID;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (!this.R()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2vwwmxexhca5n","015CwMVqmL70YBzvEWUmDWZ9SGi4rWmqnzBwKQWitrk=",-2783421163010166908,5627529212253794248,-8038686525819156419,6677273529920855084>()) {
               case 1990402513:
                  if (!com.yiyiaddon.i.c.a.ft()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s261pigig2s64i","DXWDmAQhtntra3OsBZ6gmNawCdvm/a7ugRUsHayYNXE=",2864376337294817611,-4213455783219831887,3883240499090806633,2079196295714958466>()) {
                        case -114675378:
                           return l.NAVIGATION_FAILED;
                        default:
                           throw null;
                     }
                  } else {
                     if (!com.yiyiaddon.i.c.a.cX()) {
                        switch ((int)com.yiyiaddon.m.b.a<"smef8i953z6sz","smGMOxKgv3yPVbtWadK6t88IgRtjGO5j07QLA5eH10Q=",-6670653499984247587,-4597878627654155693,3794559969204239156,123063042942522245>()) {
                           case -85497160:
                              com.yiyiaddon.i.c.a.b(this.c.a(), 1);
                              switch ((int)com.yiyiaddon.m.b.a<"s1kxpvwehu2gdq","juq52hdqD1rTN51GdtXH1/ir62Br8TO8DieVLQnDkW4=",7167666932164819133,-7985707038047037473,-8638570682185626149,6894354519949946023>()) {
                                 case -2099288020:
                                    return l.IN_PROGRESS;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return l.IN_PROGRESS;
                  }
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.i.c.a.i();
            if (this.a == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3d7hwbyz6py7j","hogFWsvnPrcGw/IKBWvLdclvUYNrVpgQ2do/G54ykSo=",826245810585513046,-4595420366468330551,6888729081377796108,4043177113369795777>()) {
                  case -1651913864:
                     this.a = this.a();
                     if (this.a == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2pcvk27o1dpv9","737GcKAi1CRKRRND5gIPQ7aCKzojzziPruJIxkznJJY=",623842335600222491,-5266179685969295377,-5206086236915884467,401708312351643241>()) {
                           case -762068176:
                              if (this.R) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3mlfoajy4as7j","vT9ruihjNSIGa1NS1CJpRzcbcgTLABkrijxbbR9a5J8=",1662278391568860867,-6719925646266020593,8842389483401556517,-4445322941846853719>()) {
                                    case 1266946182:
                                       l var10000 = l.IN_PROGRESS;
                                       switch ((int)com.yiyiaddon.m.b.a<"sd12i6w3q2xw9","t5dmjWiwoaba9ztYvXjeAlvLEFEUaUZm495nZ/t6WTE=",-6902952245638075436,-6447554829062436510,-5257547836807106292,8115798163141473394>()) {
                                          case 1800007789:
                                             return var10000;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 l var2 = l.RESOURCE_INSUFFICIENT;
                                 switch ((int)com.yiyiaddon.m.b.a<"s245qxs2rcxi7b","WZ9ipawhjGqfMn1pilDkhtsIORpfCs4V9PmXYdIeJsc=",1343708609413292891,3418782537617442591,-7218069125029664114,-8971379830529748218>()) {
                                    case -201172754:
                                       return var2;
                                    default:
                                       throw null;
                                 }
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

            if (!this.N) {
               switch ((int)com.yiyiaddon.m.b.a<"stnv3pkfdcesx","w2VVv1L+rCqDZ0ucjODyyrK6ShKsJIl/IH6T3GfzuHY=",2104899594536059767,7232419809128211935,-2067695236565510339,2186045127655968448>()) {
                  case 2105828015:
                     com.yiyiaddon.i.d.a.b(this.a, this.c.a());
                     this.N = true;
                     this.aX = 0;
                     return l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else if (this.aX < 2) {
               switch ((int)com.yiyiaddon.m.b.a<"s3gpmwhytz7but","4L/mkEjByY9nLDNILcPjod4/L5PTkh8Ty1G4vOexIh0=",713117102782831167,2197333162821543444,-1192657493574689915,-4342283075001317471>()) {
                  case -66809793:
                     this.aX++;
                     return l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else if (this.h.c(this.c)) {
               switch ((int)com.yiyiaddon.m.b.a<"s11amljjga0dt","BQvRQKWLxsuc9aVXIpoyD7BbdFNKdTaVn/1dg1kk9YE=",-4663535217676933467,-6384807210178479746,2785328012005063182,3950780963445093272>()) {
                  case 1455158479:
                     return l.SUCCESS;
                  default:
                     throw null;
               }
            } else if (this.aY < 2) {
               switch ((int)com.yiyiaddon.m.b.a<"svgxp7xoj6qe2","ZfgLEivtcSRVAD3K8ztY5r3BiiqqKsbQAZJuDN9RVC4=",-8407495538862417617,3761194789822140393,-7983094645793698032,488387469744184391>()) {
                  case 1535455176:
                     this.aY++;
                     this.N = false;
                     this.aX = 0;
                     return l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else {
               return l.PLANT_FAILED;
            }
         }
      }
   }

   @Override
   public boolean O() {
      return false;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
      if (this.Q) {
         switch ((int)com.yiyiaddon.m.b.a<"suhzl7fdyhcpv","T5X3N+PSJCYtYFk3g98OlJSnYYJCcOGED+pwcTKkIW0=",-1998938466560643427,-3703448343277117352,-194017951850299804,-6957663889797083709>()) {
            case 1660235940:
               com.yiyiaddon.e.c.i.h.e(this.bm);
               this.Q = false;
               switch ((int)com.yiyiaddon.m.b.a<"s3fuv4sfzz7224","V6iWHrJ9p7IQ5oEbl2X9yj2e8BE2Hag4sXhxHlNRYNk=",-6100296248050971394,-5777147370985867971,-6935880050795333599,-6296288292864837788>()) {
                  case -419023040:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private InteractionHand a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2egph01m0dny7","ol4+4iTSwgYgO6g9OrQjRxyYualzx+1kRHDF3/gFrGs=",8966754433105316921,5107515575945642327,-3036840403227513875,4203532686740794038>()) {
            case 1797986709:
               return null;
            default:
               throw null;
         }
      } else if (var1.player.getMainHandItem().getItem() instanceof HoeItem) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ggfdzns2l0sc","RzLYctLqaACKBmDoINnPChDcLInP2grIJvFkHVw9SIc=",-7880129849864230579,2360307301603916328,-3584152183615627525,2502439034721935593>()) {
            case 250783424:
               this.R = false;
               this.bz = 0;
               return InteractionHand.MAIN_HAND;
            default:
               throw null;
         }
      } else {
         int var2 = com.yiyiaddon.e.c.i.h.a(var0 -> var0.getItem() instanceof HoeItem);
         if (var2 != -1) {
            switch ((int)com.yiyiaddon.m.b.a<"sz4zglidm0xqj","OTlOBsQ0FqNv1+ZHhGW49AojsPEWj7I1CD1jKJjNNdI=",-1672322767521475810,-6500753210319093255,4690466919369994424,-246817488751883748>()) {
               case 509301351:
                  this.bm = var1.player.getInventory().getSelectedSlot();
                  com.yiyiaddon.e.c.i.h.e(var2);
                  this.Q = true;
                  this.R = false;
                  this.bz = 0;
                  return InteractionHand.MAIN_HAND;
               default:
                  throw null;
            }
         } else {
            int var3 = com.yiyiaddon.e.c.i.h.b(var0 -> var0.getItem() instanceof HoeItem);
            if (var3 != -1) {
               switch ((int)com.yiyiaddon.m.b.a<"s1a6uw2byvx0k9","ce2CAxEzOZGNEujzUnyy8gwXMZn+P4PQqKcWu/cJsj4=",-8205290757298233146,-6427557565754400304,-3490401919389771761,6218775662622881847>()) {
                  case 28166799:
                     this.bz++;
                     if (this.R) {
                        label37:
                        switch ((int)com.yiyiaddon.m.b.a<"s291ieqkbpwwq0","8KogC4mG/iBmT04aM7dAfokImEvImZAT81GkmuAnIbI=",7673965046737566982,1014319067656358526,-4780178535584651089,901029552227437427>()) {
                           case -355250512:
                              if (this.bz <= 8) {
                                 return null;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1xp6szjnh0l7e","hhQmIg1uSVZOwHm+aBWEjax9jALY1vkFOYnb5W4eIiQ=",3352776250973720256,8454209157439333855,1874680250356616747,5271642567964259336>()) {
                                 case 423714085:
                                    break label37;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.e.c.i.h.d(var3);
                     this.R = true;
                     this.bz = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s2w2yyi1t37hwa","hIGLLEXQsPZM5eDN1WUHmYdt8mvpmszUJKlUmwtve9E=",8058813023470641184,9115469921418952980,1610810227824290625,3295102690907551852>()) {
                        case -1814364977:
                           return null;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.R = false;
               this.bz = 0;
               return null;
            }
         }
      }
   }

   private boolean R() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s333yp2aj7zujx","EXNmasPy6+gGBqR0Mu8ZdmC+OtWpJWj4gtYRppXms5U=",3663757021506913703,-8816693284420978259,-4448272883326686378,899699171105874679>()) {
            case -732137616:
               return false;
            default:
               throw null;
         }
      } else {
         double var2 = this.c.a().getX() + 0.5 - var1.player.getX();
         double var4 = this.c.a().getY() + 0.5 - var1.player.getEyeY();
         double var6 = this.c.a().getZ() + 0.5 - var1.player.getZ();
         if (var2 * var2 + var4 * var4 + var6 * var6 <= this.o * this.o) {
            switch ((int)com.yiyiaddon.m.b.a<"s2i0n3d8uxtgja","r29pvUkHcg0XofZF0GXeDQUJNwYlIPb8l3BUCtLasf8=",-2237392738687130717,566718442080076060,-1220054199355376119,-5816185729726604695>()) {
               case -49373521:
                  switch ((int)com.yiyiaddon.m.b.a<"s1r6f3wyfdydww","THkU0MEx8/HI4uI18phlEbvvk0oVRd1rvZj2a1vWFQQ=",2326125107816276263,-8100285454350591232,8807503819045833294,-6025791450901711463>()) {
                     case 1759326504:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"scepwpvgzv1ff","sjG+ynSk5EeFXP+gmSXaE+mr3xrdzUb1E5YQnPqwv3U=",4498051726286204398,-6272201392631508773,-8516548417528597177,8142052234723289513>()) {
               case 1917697362:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }
}
