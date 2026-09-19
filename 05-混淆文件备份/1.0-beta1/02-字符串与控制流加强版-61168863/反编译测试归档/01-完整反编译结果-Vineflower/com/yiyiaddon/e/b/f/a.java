package com.yiyiaddon.e.b.f;

import com.yiyiaddon.g.a.g;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class a {
   private static final int av = 5;
   private static final double e = 4.5;
   private final Minecraft h = Minecraft.getInstance();
   private final com.yiyiaddon.e.b.b.a c;
   private final com.yiyiaddon.k.a.a a = new com.yiyiaddon.k.a.a();
   private int aw = 0;
   private int ax = 0;
   private int ay = 0;
   private com.yiyiaddon.e.b.f.a.b a;

   public a(com.yiyiaddon.e.b.b.a var1) {
      this.c = var1;
   }

   public void ae() {
      this.a.ae();
      if (this.aw > 0) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s6bnwvdcinxe3","JbE4vot5FDwmR12tQRau9t/ENNeGpQ0d3uhSJSL2cUA=",-2993515931396098590,264953779366364008,-5059477598864056992,722675116009703699>()) {
            case 1444394608:
               this.aw--;
               switch ((int)com.yiyiaddon.m.b.a<"s1lsvsvmirl3bn","rELAJlfspwuX4A7ZRMke2a2Ro/QexcvomWuBMIJvuCc=",-1159715436876892733,-3221118124203069766,8555275496829031748,-864933860028713840>()) {
                  case 1457477286:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.ay > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sfjmu0djvuy6z","ZMlnKG5iQN1EG4sV8p9sPvS18TuR0Oy1ZbCNiYk64bg=",6346747908746737580,-6646551108859421957,1484274501789058101,-8844452907475652939>()) {
            case -1150718849:
               this.ay--;
               switch ((int)com.yiyiaddon.m.b.a<"s30k0jmf49f4iz","la556FHwR2IeCTCRAG8W2qyNAk9L3IRhfG8WaETd9t4=",2852323615263725882,-7588659532671038086,-3769932278364352089,-4535818163156425947>()) {
                  case 1722303569:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public boolean g(BlockPos var1) {
      LocalPlayer var2 = this.h.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q1ial9ztrcbz","rvGWV0MGarbjBqB0GqmqSYRk6uOUIzb0eFJZMQxcBs4=",1294161125092096345,-9146275202677884936,-242608911704984378,-9023651366124744203>()) {
            case 632373388:
               if (this.h.level != null) {
                  double var3 = var2.position().distanceToSqr(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);
                  if (var3 > 20.25) {
                     switch ((int)com.yiyiaddon.m.b.a<"s31xa6xb3ot9lq","HRVl71Aj63Y4Gl0oOmzvvyy7QA7fFrA9z4KZO2wFNFg=",-4821773554369971299,2248828346534494048,3254855046912667489,-8672027582574657610>()) {
                        case -1997466800:
                           return false;
                        default:
                           throw null;
                     }
                  } else if (var2.blockPosition().equals(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2xx3kfg9ieurh","Gdk0FdT5wanp2OhevaftjFHUqAw1rWpgjBW4voIpebc=",5931482843708755652,-1280220246393207692,8109454237071494702,-4503243172542233426>()) {
                        case 1817785958:
                           return false;
                        default:
                           throw null;
                     }
                  } else if (this.ay > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2s9yn8jpg9tap","a8yM8zfwEJg+PLnRg59DxlS018LHrswqjV3hzgu0uAs=",-6531610859629184695,3449209317373568679,-7331153144719407507,2800533332246541819>()) {
                        case 820365160:
                           return false;
                        default:
                           throw null;
                     }
                  } else if (com.yiyiaddon.i.a.a.b() != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2oyieufm3v1ra","YSPL7N1DaxONsMorAojhQebbtL5NUpQBvVtvrg8Mp04=",1559272087016203085,-5715062981530347108,3165464671732729370,-8168424558863827951>()) {
                        case 1992832735:
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     BlockEntity var5 = this.h.level.getBlockEntity(var1);
                     if (!(var5 instanceof Container)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sy5d36o6j7rnq","+e8XEZUv8qxvqAfwdVYpaPyK1yW01ie+wPD8npGLrNE=",4532500899427723315,3271554641529628829,1034511798926368875,-3656753169563661214>()) {
                           case 1468786609:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        this.ax++;
                        if (this.ax > 5) {
                           switch ((int)com.yiyiaddon.m.b.a<"sadvbv5r0p0o2","QiCe/wiTPNCbFYEQZrWScQrxflGV5hF08gAz/apaYZ4=",-4899390830603795108,4341339455340666799,6194723787912271192,-683491895568506847>()) {
                              case -165959062:
                                 this.ay = 30;
                                 this.ax = 0;
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        this.ay = 5;
                        return com.yiyiaddon.i.d.a.a(InteractionHand.MAIN_HAND, var1, Direction.UP);
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3tahmeh7048qv","FVQsf/vAvNT4faCJQuKb0+Y0SHe8heop/sYwBrOMFcE=",-1751065450977388372,-2900750814317960775,-6132292762410526418,-1121321130108961237>()) {
                     case 1451919411:
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

   public boolean u() {
      if (com.yiyiaddon.i.a.a.b() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sux9ea0e3mtte","CQt573soZVAYkyPFfL0a8Qd7kHuGynbuBumMucWyU8k=",3175306543282148026,3079733606949183588,-8438650966786812516,8702341872460669497>()) {
            case -1259373800:
               switch ((int)com.yiyiaddon.m.b.a<"s6xlhuksd3jmz","5790jwRVWJpZ4/N63C1rBkd0MvUU7DzrcXF1ofz8+gs=",-7673089376884692198,-2587283536578701543,4463444535660841259,4014085198847996899>()) {
                  case -1418551925:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2tkg9zh7pjr8s","A1LEzhU7inZWLgd3dw6pPmPvjUyvcDSAHadXEfmRVpw=",7570207952432015067,-4082068596751998072,2934976513289769045,3853390745192726522>()) {
            case -1384097705:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean v() {
      return this.a.fr();
   }

   public void close() {
      com.yiyiaddon.i.a.a.cD();
      this.f();
   }

   public void f() {
      this.a.f();
      this.ax = 0;
      this.aw = 0;
      this.a = null;
   }

   private static com.yiyiaddon.e.b.f.a.d a(Slot var0, Inventory var1) {
      if (var0.container != var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s25kutb0b8xn0y","wMkYpklwW+pNdJ3tRc3REfRajVMQ7LLVdfnrpfrBGAE=",4507170808496520493,1498551518303987496,-3255901006151942345,-2165422996485023491>()) {
            case 1141998291:
               return com.yiyiaddon.e.b.f.a.d.CONTAINER;
            default:
               throw null;
         }
      } else if (Inventory.isHotbarSlot(var0.getContainerSlot())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lwoeakt23zqf","AhE4iJVF2cyCVKBWbuQYeqC2syd7o80WNxgVjonuCaA=",7015131560535268556,-6831148617942693520,6789558292056300351,6002705701621766976>()) {
            case -155631233:
               com.yiyiaddon.e.b.f.a.d var10000 = com.yiyiaddon.e.b.f.a.d.HOTBAR;
               switch ((int)com.yiyiaddon.m.b.a<"s3hrvl74lxb5ss","KxtDveNzk7oLrHRMwx0jx7cEoSnXZf/k9fGue/eNcEw=",-165283225356851906,5905650964972234652,-215474754608851270,4408967907997546024>()) {
                  case -1677578065:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.b.f.a.d var2 = com.yiyiaddon.e.b.f.a.d.INVENTORY;
         switch ((int)com.yiyiaddon.m.b.a<"s1wg6azt58tcfv","502bvI/+46IRSDAyikkq3ORg05kBub9ifXgiOYsDXYo=",-5101652424381597108,1016220947391377112,8804821915440586192,1528786967918602983>()) {
            case 734850606:
               return var2;
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.e.b.f.a.c a(g var1) {
      LocalPlayer var2 = this.h.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s31udsus2ozu5v","so2ikbKeWbu5oGPFmvbKLfLFBgVHl3kMIa1Td4nav2Y=",-2321469947240281215,5923182437849744532,2212484272673059545,-3818040148638106606>()) {
            case 2024236543:
               if (this.h.gameMode != null) {
                  AbstractContainerMenu var3 = com.yiyiaddon.i.a.a.b();
                  if (var3 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s6akgx6214fo6","4NmDypmsy2CDOVfBi1X0UMpilDdZhgBvYnwMwqoO7TU=",-2356822895518998343,5764632305022320404,4041942505567850678,-6945942725417045099>()) {
                        case -873040538:
                           return com.yiyiaddon.e.b.f.a.c.FINISHED;
                        default:
                           throw null;
                     }
                  } else {
                     Inventory var4 = var2.getInventory();
                     if (this.a != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1ytfmybwguhjz","lCctLAlv8rlcubJmUUF07kXYHPn0ZrEYRaEG4CJTOnc=",5753606851571787319,3591736939852437077,-4779031187507968508,-1169371040451712213>()) {
                           case 1294484714:
                              return this.a(var3, var4);
                           default:
                              throw null;
                        }
                     } else if (!this.v()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2uikot97zwkyt","zhLXooP1f/Zf3FBotaaKdNpgyVp1iJt93ZV1JBNXB5c=",-1859286524064530718,4470784410094592789,1210907450140354184,-3637775887428927761>()) {
                           case 1029140616:
                              return com.yiyiaddon.e.b.f.a.c.WITHDREW;
                           default:
                              throw null;
                        }
                     } else if (this.aw > 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3trcssgh9ruqn","kkDhalzDajwdXuO3ZltsUUkZjpSziET1g3wgCQ2pkv8=",864554119946800776,7158893823372110691,7081011038463962907,8406203234004922986>()) {
                           case -618856335:
                              return com.yiyiaddon.e.b.f.a.c.WITHDREW;
                           default:
                              throw null;
                        }
                     } else {
                        boolean[] var5 = new boolean[]{false};
                        com.yiyiaddon.e.b.f.a.e var6 = this.a(var1, var3, var4, var5);
                        if (var6 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2n7zhtci3kxpx","VownC0P6zYqAbCqHKOpWajHlBCF2kyqc+qZxav+N/Go=",-7153966433852258789,1555552734529654850,-3220127386281420439,5399409966608802991>()) {
                              case -1707984275:
                                 if (var5[0]) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s4h0y9k49i1xe","gTZppdmXgX4ZLXTPMdjLGLngH7oqxMdVUGyIG8K2Zhw=",2871130226321596730,-7653948687440047624,4830809256943104326,-6484174564144615793>()) {
                                       case 1104714505:
                                          com.yiyiaddon.e.b.f.a.c var10000 = com.yiyiaddon.e.b.f.a.c.INVENTORY_FULL;
                                          switch ((int)com.yiyiaddon.m.b.a<"s9ahytcg9jir9","0Vd1pKOvG2zAOs9HslgCr+H/FGZway2pm5OpPGeZ9oM=",-5856613167104612739,-7283459240580676300,-593704003987541383,-9068307338552205387>()) {
                                             case -1956582876:
                                                return var10000;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    com.yiyiaddon.e.b.f.a.c var7 = com.yiyiaddon.e.b.f.a.c.FINISHED;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3id69b15e5w9r","XZVYFl0AKho75AWdJuOhMPevJzTt0cT6r+I4I/Q4zTM=",-4419125046812988324,964453693460438795,8584992105158600135,995763923754166930>()) {
                                       case 823353690:
                                          return var7;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var6.b() >= var6.a().getItem().getCount()) {
                              label61:
                              switch ((int)com.yiyiaddon.m.b.a<"s63p0v8beet8h","UELEZxJRNTpVV6fhSYylxeWI60xyDBc9TP1XJIRvzuA=",2579129479461121554,-8748667648299975126,3190999147943840776,-4998470751587215181>()) {
                                 case 2021723676:
                                    this.a(var3, var6.a());
                                    switch ((int)com.yiyiaddon.m.b.a<"s2e21dy4z2juiw","Teb4cSo/9BLv3PVba07zYk+lrAwBI174R+fBjXVPR8c=",7121015615557087008,-373227856788851207,-3779045282470933800,949063472959525496>()) {
                                       case -1776386016:
                                          break label61;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.a(var3, var6.a(), var6.b());
                              switch ((int)com.yiyiaddon.m.b.a<"s2zhghu6stasqt","KzL8+jbgiSOYftqHa2QCEyqik3Gd0f/7DapwAUJCpD4=",-8356966084274578579,-9100652647341363045,6250003182431230915,-7084821830896378724>()) {
                                 case -157399979:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           this.aw = this.c.P;
                           return com.yiyiaddon.e.b.f.a.c.WITHDREW;
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2te1s6pcsh1kq","7fqlUKhGV5sQWJM+rGUaH7mBu6hypm/poPryQHzXK6o=",-2676462676723181747,8874366528835861004,-8278392255775159423,-6238184343695105616>()) {
                     case -184307213:
                        return com.yiyiaddon.e.b.f.a.c.FINISHED;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.b.f.a.c.FINISHED;
      }
   }

   public com.yiyiaddon.e.b.f.a.a a(g var1) {
      LocalPlayer var2 = this.h.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s289h0lkemu11u","V+Q1JzFTEGcng1TTJGQ7dybfHaNDHZDvLgJa1X/Espw=",-2121202771358462179,6323653501021327732,-4624503435853550281,4388132764157365215>()) {
            case 223295195:
               if (this.h.gameMode != null) {
                  if (this.a != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2zeh6a3a55nb2","YPurXj+H5z9hA/NA1vnVkfPh8rvDnMkxzuDsKNheB48=",-7667237639071667870,4250224580975051971,497516184484614041,-9192783999402161851>()) {
                        case -1574629385:
                           return com.yiyiaddon.e.b.f.a.a.TAKABLE;
                        default:
                           throw null;
                     }
                  } else {
                     AbstractContainerMenu var3 = com.yiyiaddon.i.a.a.b();
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s20d4ll8uyglbn","G04eB7phUZRCrVDipb+HmCBiJ23VuXMhAcu2Pj34Evo=",8317213786926517323,5539016792734591088,-8941319715447004221,2527873255585936192>()) {
                           case -1365603697:
                              return com.yiyiaddon.e.b.f.a.a.DONE;
                           default:
                              throw null;
                        }
                     } else if (!this.v()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1aphycv4a32mx","4b38fWlTBYzINk2vR200XxNtU9CU7EyKPAlbG30d5c8=",-4969965349252214543,-5741073041341760486,517848564509209495,-6522080395033027867>()) {
                           case -1168581896:
                              return com.yiyiaddon.e.b.f.a.a.TAKABLE;
                           default:
                              throw null;
                        }
                     } else {
                        boolean[] var4 = new boolean[]{false};
                        com.yiyiaddon.e.b.f.a.e var5 = this.a(var1, var3, var2.getInventory(), var4);
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2wzopv3jb1axd","hLXj2vqn79GMwT53hY0rltdZqynS99TxMOF558Ph870=",7350002813344220517,5186442915712642805,5785677025137756891,-4367188458151074971>()) {
                              case 225103890:
                                 return com.yiyiaddon.e.b.f.a.a.TAKABLE;
                              default:
                                 throw null;
                           }
                        } else if (var4[0]) {
                           switch ((int)com.yiyiaddon.m.b.a<"s75c9ivatwcoy","f3JjoGIdEIEZmTk9ln2/rzcmlgMkECMmRWitCs5XE6Q=",3364988220897743747,-5344800506182667157,5285064023929933930,654309767190272989>()) {
                              case -310110339:
                                 com.yiyiaddon.e.b.f.a.a var10000 = com.yiyiaddon.e.b.f.a.a.INVENTORY_FULL;
                                 switch ((int)com.yiyiaddon.m.b.a<"sffewwsvsz25e","p9g1rVoshnVYYkWKZtaX+VK40hXLlHVxECoYTC1YLcU=",-7913087855437718832,-1687438911959666687,-4316202355504414357,-4248805231155776915>()) {
                                    case -194266524:
                                       return var10000;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           com.yiyiaddon.e.b.f.a.a var6 = com.yiyiaddon.e.b.f.a.a.DONE;
                           switch ((int)com.yiyiaddon.m.b.a<"s3hg3er2fn4d88","8zu0Md0kEj5J2RS+DJThHxmggYbDVZ+A91P7U62841k=",7584342465411507554,290092984941950240,-6150703881959725683,-4656827083681539062>()) {
                              case 1584386255:
                                 return var6;
                              default:
                                 throw null;
                           }
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1mbw8ijez77y6","+iP3Wftb+a9iFoD/Nz1TXlhIg6ja7HzhJ6K6GDUPbmY=",-138689199681232634,3505752676426894099,-4048834606184560050,-3492058244305896385>()) {
                     case 1752833211:
                        return com.yiyiaddon.e.b.f.a.a.DONE;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.b.f.a.a.DONE;
      }
   }

   private com.yiyiaddon.e.b.f.a.e a(g var1, AbstractContainerMenu var2, Inventory var3, boolean[] var4) {
      List var10000;
      if (var1 == g.TAKE_ALL) {
         label108:
         switch ((int)com.yiyiaddon.m.b.a<"s1kj7f7z3my2r7","OHh5InRkSr7THtqqg7oQI6OcOBIV63RsCxAnnT3kdGk=",940594997050522370,6434339711069206755,-3309927015062804740,-8393366859455838730>()) {
            case -2144901912:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s30j9xdqbyjl62","Pf1d067IoQbYEGdFuCZjA1FfA2sPBuw6IGfSRnl3eng=",-6037839433195301987,-9058283343573560452,-8941691730764130285,638151850189057864>()) {
                  case 1994660339:
                     break label108;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = com.yiyiaddon.c.a.a.a(com.yiyiaddon.k.b.a.a());
         switch ((int)com.yiyiaddon.m.b.a<"s2vfb3hga2miy","O/triSEw/7Mqm0X4dtRm+QjFxnheulmFFaL0Q9mb7l8=",-6823934495880073409,-4966267662273770976,-3378612273133602409,2405305446951383197>()) {
            case 1962580023:
               break;
            default:
               throw null;
         }
      }

      List var5 = var10000;
      Iterator var6 = var2.slots.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sag7qsymave6q","Zm24KomwsGB6QW0RTi/WIgJKMzFGpnUjT/ZaespxbJA=",3305721880785089668,605730126162970234,7586594085159761194,-888936118835891050>()) {
         case -1889386925:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sqtduk5dd2wjs","5YWR+kaYZYt6f8OzV0HPQBKRyLNVJZhRcHWv/lW9N2E=",-2967995760102368818,7845199216427359196,5337395954481975079,-12569157019090042>()) {
                  case 1574078881:
                     Slot var7 = (Slot)var6.next();
                     if (a(var7, var3) != com.yiyiaddon.e.b.f.a.d.CONTAINER) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1cbr70ja8afu5","z8E0X3DClWSH2/OvgOQjGrnIsfJoCMlDjohQw7gxzvM=",4367826965350464607,-8196998068438374125,1472903406753524322,6066768791119826413>()) {
                           case -153964335:
                              switch ((int)com.yiyiaddon.m.b.a<"s1rn1keisfsks8","8oKm3mczjFGi0hmixIBhaQjIKY8hWh9Bq9W6zxSffX0=",-971358545317841685,7659580254583423489,-4107720380209973132,-3767836979623177813>()) {
                                 case 1966184520:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        ItemStack var8 = var7.getItem();
                        if (var8.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s8yggazwt5g93","y5WxxRa/70vLVhzI9NiAGrWbmGGhdNWBoWd8l8TL5rQ=",-672633687458261262,3235145359527205132,6417050657658625797,2779605480123031355>()) {
                              case -1922690687:
                                 switch ((int)com.yiyiaddon.m.b.a<"s30muibllmpwq2","XC9BCSB/a5EAQl1XiqeWs0CUa/lu5uuHyMexNiYZOnc=",649362955375315311,323719796640342647,-8711811536342591639,5127449467772881893>()) {
                                    case -474923820:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           com.yiyiaddon.g.c.d var13;
                           if (var1 == g.TAKE_ALL) {
                              label83:
                              switch ((int)com.yiyiaddon.m.b.a<"sq50gspxqgdqw","xDabIbzEBwYws9s3/445SGnA/MXeK8YWPcXZIgBS9b8=",-5218635065618296353,-24611057154475049,-7525711001145974743,6798024345032220916>()) {
                                 case 156470996:
                                    var13 = null;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2u5m8hj860ggg","rOOWbvRanpSaD8c9t6+e6uVEIReiWd9Ti/s6pEfFSqQ=",6875386457411530047,-7220597484135101950,1725638532008873890,-6956665098786223508>()) {
                                       case -1899812395:
                                          break label83;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var13 = com.yiyiaddon.i.b.d.a(var8, var5);
                              switch ((int)com.yiyiaddon.m.b.a<"s1iyl2uv0wa77j","3LrCgB3e1KrM2nWqTSz6Q4xpvibR4QJTA01/FDVo3JY=",-6012059995894634524,-8810005279913103427,9189711024061088002,-9143897895304104192>()) {
                                 case 1316578527:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           com.yiyiaddon.g.c.d var9 = var13;
                           if (var1 != g.TAKE_ALL) {
                              switch ((int)com.yiyiaddon.m.b.a<"s229n3sxnoc0gl","xl+1ulJBXdo+6OlBh6p/gwwYS/vasxzKGAcf9PkuznA=",3208224159987764606,6215198982691593618,8542323334176821174,3861859899082229747>()) {
                                 case 1722699241:
                                    if (var9 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3alkmjw56oo4n","lEOC0uNSdtSq0nNdjuRlrEz312KvFv835uQAQPavtBw=",-306826125365976301,-2172535459484233659,-7573558047818296220,3786707083007549290>()) {
                                          case 2026834180:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1iasy4jq1y105","3jC9UKG+SnGh/OB+FTLDD87SFg7ZCyn3/Hyc5o8HHPQ=",5904723368551443247,5252938274958893358,4724432041111038453,-3352912451318067430>()) {
                                                case -913797338:
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

                           int var10 = var8.getCount();
                           if (var1 == g.TARGET_COUNT) {
                              label69:
                              switch ((int)com.yiyiaddon.m.b.a<"s2qzsje9drp1jm","xvyXemE9luVCiaFgfQ8Rxp4N9gOsnGh3PKteQL/0K8M=",4071986711741146037,-322455316517180506,1957117730762550466,200390833721903925>()) {
                                 case 2036417847:
                                    int var11 = this.c.b(var9.dF());
                                    int var12 = var11 - this.a(var3, var9);
                                    if (var12 <= 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1eybzgv869kpg","MzlrLx0HgdYbjafkUxXuoVG6bOkmZUS4nt7HuzW0vdc=",5205695947552204590,-1507739566932768168,-1196827517183257460,-8846508744270977551>()) {
                                          case -1185511256:
                                             switch ((int)com.yiyiaddon.m.b.a<"s38wem8aokmicz","H6ezTzQ+b913zQ4mnMEF2c5J9yrrDttH2CRhXWcKdMw=",2400667016391164289,477209435932265506,5435550615961239174,2623511000477997834>()) {
                                                case -198926057:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var10 = var12;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1d588g5iayfsc","m/2oUkf5VMFn0CwcVwcG6xxipCgFfR40dVgD8n7/h9g=",6674603081495750172,-4428632689605210525,-845684792029896234,285332434862137534>()) {
                                       case 1972545640:
                                          break label69;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a(var2, var3, var8, var10)) {
                              return new com.yiyiaddon.e.b.f.a.e(var7, var9, var10);
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1ibvrjk6c0w67","gPZxP1Kh/wovNo0g9VaCv/l4eycY3tVOhRJIxhBqDPM=",-2822949251335765709,-747295130913977684,-8096388191407473478,-1728831982863204490>()) {
                              case 278499993:
                                 var4[0] = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3cv9omkiibl16","lXO83aiPPfPcBDGyRVNNZpuIWHnB0Ocz3OxltLmGm4c=",-3395396356916940900,3587506921924906562,4981150438208060120,8247197933535218539>()) {
                                    case -1264457528:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }

   private boolean a(AbstractContainerMenu var1, Inventory var2, ItemStack var3, int var4) {
      int var5 = var3.getMaxStackSize();
      int var6 = 0;
      Iterator var7 = var1.slots.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1wyd0x8b9iuo5","j1kzNgOHaEbdGBefmQrf18nORyYClAwmO/rIbxcTRzc=",-2561300550485315370,-1316316464986406711,4478445042287680611,6517194197899115071>()) {
         case -1740913945:
            while (var7.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3bajy6s3je5du","DaY7VZ46/Mvg5cdEhcmKTYsIqUYJB4qfo75ZTLQPShk=",3003329235401860850,-5544081132520679149,-4646262085655365863,5748216776522983359>()) {
                  case 1578071203:
                     Slot var8 = (Slot)var7.next();
                     if (a(var8, var2) == com.yiyiaddon.e.b.f.a.d.CONTAINER) {
                        switch ((int)com.yiyiaddon.m.b.a<"s25nzxfn6qv6h2","k+2Nl9KfuCdLuCBiobm0YU3yLxcKJAr0C6fAh720UZg=",-6033238075624834276,-756680219238961505,-8093298798061041289,-3696621420225749870>()) {
                           case -1541669170:
                              switch ((int)com.yiyiaddon.m.b.a<"s3vtfjqceucr8i","YAXdQQ5oLH+ETdt00s80mjO6rLg0CCOVadwmQpQILWY=",-1267035005801490213,-5869214207319212499,-5750697492789198136,-6184923868325683319>()) {
                                 case -298197625:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        ItemStack var9 = var8.getItem();
                        if (var9.isEmpty()) {
                           label57:
                           switch ((int)com.yiyiaddon.m.b.a<"s2z3wzl4ce83sa","zjduUXAcR+yfCVE5h8hJdW/Lok4ZD626sipnsZgjATs=",-8216878550307079066,-251836528986098358,-2054516410257623213,-1721091501209657808>()) {
                              case -1466734179:
                                 var6 += var5;
                                 switch ((int)com.yiyiaddon.m.b.a<"s38t5eakr7e8pz","GqVGCinZTk4pVGj47AvLUQOAn3NIgKCymH+E26bi23o=",-8351679308192820754,-8709051879460456038,-6405532433818470209,-5771752042229785115>()) {
                                    case 652924295:
                                       break label57;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (ItemStack.isSameItemSameComponents(var9, var3)) {
                           label53:
                           switch ((int)com.yiyiaddon.m.b.a<"s178fo3dyx7qhe","gSoN/eVSciAeMI4rNmkxLa9ouwjHpxzcz12/rjUHDF0=",8037359362155636836,-5007219946774006318,-2595020025500977112,-9042108851480325411>()) {
                              case -426311460:
                                 var6 += Math.max(0, Math.min(var5, var9.getMaxStackSize()) - var9.getCount());
                                 switch ((int)com.yiyiaddon.m.b.a<"s1o190yc0xyfn9","oIcU6NdMD3Q9VrplulnZATeP4A0KiZTcUnOrpJUg+6k=",-4838461947875795099,-4432816966075915176,-9074434586051252108,-9029226326689908729>()) {
                                    case 2025409443:
                                       break label53;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var6 >= var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2aocqsa2sgj8s","UXdYB9i97EwoZWl+uPbKc9UekatMB7nblFoWNHqTk3w=",3932615767146301307,7160281432677553790,-6285810823142626583,2110461088716703110>()) {
                              case 1463582090:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s5c5w6na31e3d","kJ6zDwxVGnvLFg112SfMaKuLNyXTT8A+5p+30xPKBG8=",3184017223572675861,-4348535278497754511,-285956120038853135,4940975756271846137>()) {
                           case 839140829:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            if (var6 >= var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s3iokcblloidlq","UQOgFcgAnSvH/sRTCP/t/HhjpJhDYKuM9Aif3yJCG6g=",-7596508157597534017,4107526453315163795,7848718468346839107,7383018695789975152>()) {
                  case 1001228512:
                     switch ((int)com.yiyiaddon.m.b.a<"s2oiq59yoo5645","ZUJmmfs/tLF9WWSFZ70+UogIzBIhJTnxouWr91d5Awo=",-5529675619233164151,4653322040650389881,-5555365054568964507,-6069013451866160346>()) {
                        case 23330996:
                           return true;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s2v3mcqxpdnyro","fKo9FISwS9FLWA5pUt4hVb5ytSkp8rUfGUj7nGsl4G4=",-2524805433434897339,-5855381838531224933,-4912397464492910820,7875720611309056175>()) {
                  case -685449101:
                     return false;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   private int a(Inventory var1, com.yiyiaddon.g.c.d var2) {
      int var3 = 0;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"slm4w6xzxq5ui","anGe7NylkUuWcHe5IQ4ZffCzHz7CPmN4DlCjDCNw1rw=",-9213622143129121038,-6364090829374758349,-8386313265534902594,1688835720408448918>()) {
         case 1826268820:
            while (var4 < var1.getContainerSize()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2g9nvzrsm38yq","bXet7XkGWLruEUuCb5+6ENnSP5Ijl++3OgeoDmUk+NM=",-1399676037743405985,825978709062554875,-306829491741083040,490261532042308221>()) {
                  case 40857102:
                     ItemStack var5 = var1.getItem(var4);
                     if (var5.isEmpty()) {
                        label47:
                        switch ((int)com.yiyiaddon.m.b.a<"szvaauko16mvg","yQX02gQKn3i+lCH3gJ+YBHYhqfqWLVYjc7+AzztSNzg=",5816074373102468179,-182567716653781985,-2338749424496470105,-380127009068428435>()) {
                           case -1105555671:
                              switch ((int)com.yiyiaddon.m.b.a<"s3ldhow0i0e5gy","K/PgKbaTnxQbZpP+2Rpa0C5ABHkPwHfCQ+YDXb0Mq/s=",-953470908461461381,-7283609073938933336,277772741880779061,-6558395804637253316>()) {
                                 case -1394147813:
                                    break label47;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (com.yiyiaddon.i.b.d.a(var5, var2)) {
                        label43:
                        switch ((int)com.yiyiaddon.m.b.a<"s1khbxbus3vs5m","RbHgtgjJfOYY9e4YCRQBNTmG2FzEpMqR2B1P9nZQo28=",-8842672567230497281,-8875351261822235947,4894752139535526911,-6756120693478106670>()) {
                           case 2030049344:
                              var3 += var5.getCount();
                              switch ((int)com.yiyiaddon.m.b.a<"s266t1bqh1ymbh","SkOqA5rH5sSHPPydgqIEp+chyZqQxyFktP22WTzf94Y=",7104269948980077876,-3268657899843968785,415283763834468312,-5566367503017637184>()) {
                                 case 342114110:
                                    break label43;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1omcaoxsai6zk","JjqG1lObaUnuayv+bOAhQoM5uaR/wp+Sy/MP+E9CqiU=",4447011903041968166,-6222627393799409206,3761434660666719517,-6635039482632261211>()) {
                        case -389566714:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            ItemStack var6 = this.h.player.getOffhandItem();
            if (!var6.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2dmluw9tgd5ls","fA2R9CBt254qzXg5vYIDKjJz9t7CxfDJbvo4MhlDq/k=",-1035936927448977762,3061542532282379300,5983816011346544996,6932039869725551958>()) {
                  case 685446892:
                     if (com.yiyiaddon.i.b.d.a(var6, var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"scoa4d3gxcn20","RMOkTY1UsCATBZYLB/ygZmPsL+ZOFwZSYC+/7vgDKy4=",-5505192694903257341,-7347667742184186783,-5334848796952756976,-3068366898915597366>()) {
                           case -1931669648:
                              var3 += var6.getCount();
                              switch ((int)com.yiyiaddon.m.b.a<"sbndimowsc7zb","Prrkjbamh9zF2IRlkBsucof18Bvr2C2b78i91ee43pE=",-879761098540821818,-6512456030196937335,7763513153835097336,2522025465693745142>()) {
                                 case -1848921282:
                                    return var3;
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

            return var3;
         default:
            throw null;
      }
   }

   private void a(AbstractContainerMenu var1, Slot var2) {
      com.yiyiaddon.i.a.a.a(var1, var2.index);
   }

   private void a(AbstractContainerMenu var1, Slot var2, int var3) {
      com.yiyiaddon.e.b.f.a.b var4 = new com.yiyiaddon.e.b.f.a.b();
      var4.az = var2.index;
      var4.aA = var3;
      var4.aB = 0;
      var4.a = var2.getItem().copy();
      this.a = var4;
      this.h.gameMode.handleContainerInput(var1.containerId, var2.index, 0, ContainerInput.PICKUP, this.h.player);
      this.aw = this.c.P;
   }

   private com.yiyiaddon.e.b.f.a.c a(AbstractContainerMenu var1, Inventory var2) {
      if (!this.v()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2i9yx7ex3nrcc","HNKCLNDmZpxLAc7aGjyFK97ZKXCRhNpVtc7DLy7fZqQ=",-7573996389850825041,-2403763063768395932,-3042226107673796342,-9015222439801892492>()) {
            case 688616631:
               return com.yiyiaddon.e.b.f.a.c.WITHDREW;
            default:
               throw null;
         }
      } else if (this.aw > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s13hjupctf613u","uypKKnubIpis0vyJayBaU2Q2FLBMeGIjTNPZPXif2fE=",9094304755142944735,-6737782839080481693,8603347193202050919,9195431932349670709>()) {
            case -730937307:
               return com.yiyiaddon.e.b.f.a.c.WITHDREW;
            default:
               throw null;
         }
      } else {
         switch (this.a.a) {
            case PLACE:
               if (this.a.aB >= this.a.aA) {
                  switch ((int)com.yiyiaddon.m.b.a<"s10jshanawbxl7","ro42j+2zzKpkbVyq9PhER5XeSq64bZ2oJpfMRE15YzU=",-2664174593792313453,4859669665171718004,-6595333863953959511,2446085739615027762>()) {
                     case 1601311907:
                        this.a.a = com.yiyiaddon.e.b.f.a.b.a.RETURN;
                        return com.yiyiaddon.e.b.f.a.c.WITHDREW;
                     default:
                        throw null;
                  }
               } else {
                  Slot var3 = this.a(var1, var2, this.a.a);
                  if (var3 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1v2maflxyphr0","eZir57YdXN/kbzbjTR2dKUPwjIsuDiAaRrsRLFcUn4A=",-1173396935032884253,6756312131224200296,92669586148843235,-4095263950644068574>()) {
                        case 911943393:
                           this.a(var1);
                           return com.yiyiaddon.e.b.f.a.c.INVENTORY_FULL;
                        default:
                           throw null;
                     }
                  }

                  this.h.gameMode.handleContainerInput(var1.containerId, var3.index, 1, ContainerInput.PICKUP, this.h.player);
                  this.a.aB++;
                  this.aw = this.c.P;
                  return com.yiyiaddon.e.b.f.a.c.WITHDREW;
               }
            case RETURN:
               this.a(var1);
               return com.yiyiaddon.e.b.f.a.c.WITHDREW;
            default:
               return com.yiyiaddon.e.b.f.a.c.WITHDREW;
         }
      }
   }

   private void a(AbstractContainerMenu var1) {
      this.h.gameMode.handleContainerInput(var1.containerId, this.a.az, 0, ContainerInput.PICKUP, this.h.player);
      this.a = null;
      this.aw = this.c.P;
   }

   private Slot a(AbstractContainerMenu var1, Inventory var2, ItemStack var3) {
      Slot var4 = null;
      Iterator var5 = var1.slots.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sxy70hzkkemla","Fw01se85JAcinq9Fp+QxxFYZaHtW1g5jffmtMyKyb0Q=",8934705274868539927,3259613825306753245,4229924812628525041,2335744382666174873>()) {
         case -1429673470:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2u09s7p6u9zlv","TBjdIVBPjLeuZOH/pJVb4jAf1GOEwWQcDbNse7z8cb0=",2265307004813286865,2617680989302878758,7259275071700115550,-3308250945586878451>()) {
                  case -1106994185:
                     Slot var6 = (Slot)var5.next();
                     if (a(var6, var2) == com.yiyiaddon.e.b.f.a.d.CONTAINER) {
                        switch ((int)com.yiyiaddon.m.b.a<"sfwhq61ncvc3c","Fb2jfLS2eQ1r66JhSRnzBVC27zImpAZrCMgaylwYUwA=",3333941991745202075,-6641345006978106377,5811557237613988308,-4288199921407744470>()) {
                           case 470321242:
                              switch ((int)com.yiyiaddon.m.b.a<"se99m7x9kp3i6","xiZZud8ed8/lbwy4HO7wgDRD+XuTPrvOytKZG8oGFW8=",-5335999483336715696,1327935304373305962,-5188307221679944174,3740476677674306595>()) {
                                 case -806940327:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        ItemStack var7 = var6.getItem();
                        if (var7.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s32qz2dllrmh9o","UkukoyerLaQdrOEuZjgRVTpFRfVLUFs4vGPbaUjwifA=",-4532735040647914084,-8579975611125245252,-4910392984517598200,-5660230042870158855>()) {
                              case -288728674:
                                 if (var4 == null) {
                                    label44:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2lf5635s2ss8o","hSWGH+Kad7zXGTcJmfFlfaPC3UlBUrTHqhZO0l9Zi/I=",1240119476658697092,-598346153007842022,-3541381327401423169,2671565488696892156>()) {
                                       case -16002138:
                                          var4 = var6;
                                          switch ((int)com.yiyiaddon.m.b.a<"s24qfhtz5vyok7","gYYyuKJyP67tA82F6gJUvZNh+vdMiGMk4wZEBHx2Q8Q=",-7447234999767997352,-7583876917976617911,1287207326467141226,3756232699722216124>()) {
                                             case -1360754085:
                                                break label44;
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
                        } else if (ItemStack.isSameItemSameComponents(var7, var3)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s5js1woxb75tj","v1EYU5xXt0e3CP323hOD+IIUKrjhYbIwL8w2xZAmpZM=",-6933453391572783605,3316493703095026024,-7487879924748219992,6639254558989762369>()) {
                              case 249726919:
                                 if (var7.getCount() < var7.getMaxStackSize()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1otfy1dqahw26","hv3ajTsacbX1biBrlWOsAItuNMB0a09YXRCYbLsgbrs=",-4991103654782921565,-3479279548707576362,-3742658991736628108,-8269773380477019000>()) {
                                       case -725969760:
                                          return var6;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sntflgko3m3b2","8wASNlBikyO2DYb8QL3HK+o5ZFcq6ti0YS9uLvSZoCo=",-6915002110547396171,5597319046565108803,-634055389757526271,-9183636580786325979>()) {
                           case -2062742352:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return var4;
         default:
            throw null;
      }
   }

   public enum a {
      TAKABLE,
      DONE,
      INVENTORY_FULL;
   }

   private static final class b {
      int az;
      int aA;
      int aB;
      ItemStack a;
      com.yiyiaddon.e.b.f.a.b.a a = com.yiyiaddon.e.b.f.a.b.a.PLACE;

      enum a {
         PLACE,
         RETURN;
      }
   }

   public enum c {
      WITHDREW,
      FINISHED,
      INVENTORY_FULL;
   }

   private enum d {
      CONTAINER,
      HOTBAR,
      INVENTORY;
   }

   private record e(Slot a, com.yiyiaddon.g.c.d a, int aC) {
      public int b() {
         return this.aC;
      }
   }
}
