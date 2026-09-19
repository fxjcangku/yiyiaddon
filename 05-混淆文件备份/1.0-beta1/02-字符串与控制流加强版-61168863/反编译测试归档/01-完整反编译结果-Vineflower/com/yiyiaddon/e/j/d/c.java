package com.yiyiaddon.e.j.d;

import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalNear;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public final class c {
   private final com.yiyiaddon.e.j.a b;
   private final Minecraft G;
   private static final int hm = 40;
   private a a = com.yiyiaddon.e.j.d.a.IDLE;
   private a b = com.yiyiaddon.e.j.d.a.IDLE;
   private int ab = 0;
   private BlockPos o = BlockPos.ZERO;
   private int hn = 0;
   private int ho = 0;
   private int hp = 0;
   private boolean cv = false;
   private static final int hq = 3;
   private int hr = 0;
   private int hs = 0;
   private final b a;
   private a c = com.yiyiaddon.e.j.d.a.MINING;
   private ItemStack i = ItemStack.EMPTY;
   private ItemStack j = ItemStack.EMPTY;
   private int ht = -1;
   private int hu = -1;
   private boolean cw = false;
   private boolean cx = false;
   private int hv = 0;
   private int hw = -1;
   private boolean cy = false;
   private boolean cz = false;
   private double L = Double.MAX_VALUE;
   private int hx;
   private static final int hy = 60;
   private static final double M = 0.5;
   private int hz = 0;
   private int hA = 0;
   private static final int hB = 4;
   private int hC = 0;
   private static final int hD = 600;
   private static final int hE = 100;
   private int hF = 0;
   private static final int hG = 600;
   private boolean cA = false;
   private int hH = 0;
   private boolean cB = false;
   private int hI = 0;
   private int hJ = 0;
   private static final int hK = 40;
   private static final int hL = 10;
   private int hM = 0;
   private static final int hN = 200;
   private Level a;
   private int hO = 0;
   private int hP = 0;
   private static final int hQ = 200;
   private static final int hR = 100;
   private boolean cC = false;
   private int hS = 0;
   private int hT = 0;
   private static final int hU = 40;
   private static final int hV = 10;
   private static final int hW = 200;
   private boolean cD = false;
   private int hX = 0;
   private int hY = 0;
   private static final int hZ = 20;
   private int ia = 0;
   private int ib = 0;
   private boolean cE = false;
   private int ic = 0;
   private int id = 0;
   private BlockPos p = null;
   private final e a;
   private boolean cF = false;
   private BlockPos q = null;
   private static final int ie = 20;
   private static final int if = 60;
   private static final int ig = 200;
   private static final int ih = 3;
   private BlockPos r = BlockPos.ZERO;
   private int ii = 0;
   private int ij = 0;
   private int ik = 0;
   private int il = 0;
   private static final int im = 300;
   private static final int in = 600;
   private static final int io = 100;
   private int ip = 0;
   private static final double N = 0.05;
   private static final int iq = 3600;
   private static final int ir = 3600;
   private static final int is = 2400;
   private BlockPos s = null;
   private static final double O = 1024.0;
   private static final double P = 64.0;
   private boolean cG = false;
   private String mQ = (String)com.yiyiaddon.m.b.a<"s3u5k18p5lb0py","CVGbRYiWyzWnrXzX/19npUP3zw6vODd81kMNHA==",962476188745281713,6569589023899289066,6768228396665697891,1557210378977412126>();
   private int it = -100000;
   private static final int iu = 100;
   private static final double Q = 64.0;
   private final com.yiyiaddon.e.j.j.d a;
   private static final double R = 16.0;
   private ItemEntity a = null;
   private int iv = 0;
   private int iw = 0;
   private static final int ix = 40;
   private static final int iy = 20;
   private static final int iz = 60;
   private static final double S = 8.0;
   private static final double T = 2.25;
   private final Set<BlockPos> C = new HashSet<>();
   private static final int iA = 64;
   private boolean cH = false;
   private int iB = 0;
   private int iC = 0;
   private static final int iD = 100;
   private boolean cI = false;
   private boolean cJ = false;
   private int iE = 0;
   private int iF = 0;
   private int iG = 0;
   private int iH = 0;
   private final Set<BlockPos> D = new HashSet<>();
   private boolean cK = false;
   private static final int iI = 6;
   private static final int iJ = 3;
   private static final int iK = 10;
   private static final int iL = 400;
   private static final int iM = 16;
   private static final int iN = 40;
   private int iO = 0;
   private volatile d a = d.b;

   public a a() {
      return this.a;
   }

   public boolean ca() {
      return this.a.isActive();
   }

   public boolean cb() {
      return this.cJ;
   }

   public boolean cc() {
      if (this.a == com.yiyiaddon.e.j.d.a.COMBAT) {
         switch ((int)com.yiyiaddon.m.b.a<"s1t6de9zq6stpp","jvuJfUjXRktiQjggWUtjOIAZkposbOavfFcHn8ZhUbE=",-8946608139449571355,-5985100162381216889,-4072122703402894533,1805528631681312979>()) {
            case 1386054670:
               switch ((int)com.yiyiaddon.m.b.a<"s1nn7vhylmo6bo","QKAxqmiyaYOFqV8PlRNxGj5irRwkFAKClcaxiGN9aOI=",91214725035868978,-7636774598808284922,-3633343585334853802,-6455385270952450647>()) {
                  case -296037048:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1d5wq0v4247cf","5je+6ZSBfEu83gDxmw/jzd9chEuK0boMBcG7wm9Y2/M=",4725038501698264798,-1242585070791063710,3179192241471714929,6568731347009268351>()) {
            case -456413962:
               return false;
            default:
               throw null;
         }
      }
   }

   public c(com.yiyiaddon.e.j.a var1) {
      this.b = var1;
      this.G = Minecraft.getInstance();
      this.a = new b(var1);
      this.a = new e(var1);
      this.a = new com.yiyiaddon.e.j.j.d();
   }

   public void a(d var1) {
      d var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"spygwtotpb3na","5PuxcU7uTe3Uks5VAXHsTDHKtHizGeft49y+5JRcZFs=",331188425053975013,13128301657794957,-5857332595414967277,104627001659506355>()) {
            case -1236842864:
               var10001 = d.b;
               switch ((int)com.yiyiaddon.m.b.a<"s2c86fdj2wcte5","z6KVmSzfbWJf3vbJb+4s6b0eGEY3RGCxo196Mk5o2TA=",-2968338882692222706,-6418487216999459060,-700470399251947486,-7168349396299376095>()) {
                  case 1151186358:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s2ui6fofhpqn3g","aF4K7vj/mKDu9Nzxn92GzwdQmleTSqPGHVbdjZYSfJI=",-487853927166652447,-1766962609510540318,5148330620635673601,-8332396165054578169>()) {
            case -1826741912:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
   }

   public void f() {
      this.a = com.yiyiaddon.e.j.d.a.IDLE;
      this.ab = 0;
      this.o = BlockPos.ZERO;
      this.cG = false;
      this.hn = 0;
      this.ho = 0;
      this.hp = 0;
      this.hr = 0;
      this.hs = 0;
      this.i = ItemStack.EMPTY;
      this.j = ItemStack.EMPTY;
      this.ht = -1;
      this.hu = -1;
      this.cw = false;
      this.cx = false;
      this.hv = 0;
      this.hw = -1;
      this.cy = false;
      this.cz = false;
      this.hz = 0;
      this.hA = 0;
      this.hC = 0;
      this.hF = 0;
      this.b = com.yiyiaddon.e.j.d.a.IDLE;
      this.cA = false;
      this.hH = 0;
      this.cB = false;
      this.hI = 0;
      this.hJ = 0;
      this.hM = 0;
      this.hO = 0;
      this.hP = 0;
      this.cC = false;
      this.hS = 40;
      this.hT = 0;
      this.cD = false;
      this.hX = 0;
      this.ia = 0;
      this.cE = false;
      this.ic = 0;
      this.r = BlockPos.ZERO;
      this.ii = 0;
      this.ij = 0;
      this.a = null;
      this.iv = 0;
      this.iw = 0;
      this.C.clear();
      this.cH = false;
      this.iB = 0;
      this.ib = 0;
      this.id = 0;
      this.p = null;
      this.q = null;
      this.cF = false;
      this.a.f();
      this.s = null;
      this.iC = 0;
      this.cI = false;
      this.cJ = false;
      this.iE = 0;
      this.iF = 0;
      this.iG = 0;
      this.iH = 0;
      this.iO = 0;
      this.D.clear();
      this.a.f();
      this.a.f();
      this.mQ = (String)com.yiyiaddon.m.b.a<"s3u5k18p5lb0py","CVGbRYiWyzWnrXzX/19npUP3zw6vODd81kMNHA==",962476188745281713,6569589023899289066,6768228396665697891,1557210378977412126>();
      this.it = -100000;
      this.c = com.yiyiaddon.e.j.d.a.MINING;
   }

   public void eK() {
      label32: {
         this.c(this.a);
         this.G.options.keyUse.setDown(false);
         if (this.a != com.yiyiaddon.e.j.d.a.UNLOADING) {
            switch ((int)com.yiyiaddon.m.b.a<"s25inxn0o2sdok","SKgfZEcJCXANpMsMipSfxLEHsUKaroc56K3DZfbZP9s=",8997009340883370495,518713378767933617,-5359568135131781366,-1195893282343833876>()) {
               case 1277502750:
                  if (this.a != com.yiyiaddon.e.j.d.a.SUPPLY) {
                     label21:
                     switch ((int)com.yiyiaddon.m.b.a<"sd61pctm1bnki","sP6f7dI4Fc3XMy+u26GfU8l1k3PGkcM78IGhaeYDaWc=",6370360410119476812,2557469096501635975,9066981937057176194,-2748418977717472755>()) {
                        case -794132655:
                           if (this.a != com.yiyiaddon.e.j.d.a.REPAIR) {
                              break label32;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2onham15b51fh","nsrilxKTqNOLvk6VZhDRnmfGT+OgwjPhXOrz2DX60Tg=",5837530224431263503,7195901430948666621,-2929040253613827345,-6849012242103918400>()) {
                              case -1680872840:
                                 break label21;
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

         this.b
            .a()
            .a(
               (String)com.yiyiaddon.m.b.a<"s15e6hyd1lbzu8","sWntOCT6CMKEBo9MQLz1xljOOYWVEOVGatuIs/HWLudZPT5cblnKEfwwZBT3KzCK",-1556488207012372422,-7282443142352647503,-8053149972278958931,-8296424199670431334>(),
               Boolean.valueOf(this.b.bO())
            );
         this.b
            .a()
            .a(
               (String)com.yiyiaddon.m.b.a<"s2j8vd45jdf685","JIGrReRA8K6VhgZLKX+M56x+Iy5QqjR3yyFF59BtcPpBhGE5kmAtS9W4yQAVo4HZ",6910576740331236279,6523931002908278224,720043912931404511,8732478463887416385>(),
               Boolean.valueOf(this.b.a().bY)
            );
         this.b
            .a()
            .a(
               (String)com.yiyiaddon.m.b.a<"s21irknkwvc136","+NGLyGx2o3yVP3/+eJSmQwVDFiq7Szx7gQoHcz4FmN0w+qlzLl6zgLkQOcz6JL1OJ2JnVg==",-3203331182823233462,4605291720866285762,6624759310708869041,-7775273345698087579>(),
               Boolean.valueOf(this.b.a().cm)
            );
         this.b
            .a()
            .a(
               (String)com.yiyiaddon.m.b.a<"sw8usw6xy6epb","tzf3HXf3Ks/1TdxUYbWsICwm+f4miAdtqCb6w14gk0Z4ptL5HWW7FnH9ptyJli+uk6AKavp17TWFG8/4Vtc=",7396875077669165096,6765293923293291084,-5471731400082783259,8130578168683376221>(),
               Boolean.valueOf(this.b.a().cn)
            );
         switch ((int)com.yiyiaddon.m.b.a<"s1wvwt67ra3eld","ZBz1WkHNeEuQZ9Y6Q86etACW06TLc58qVpCfRlWhqE0=",3460998059333647915,5802842628676465101,-3263173286702393153,-1354904891107257996>()) {
            case -1704872931:
               break;
            default:
               throw null;
         }
      }

      this.f();
   }

   private int bx() {
      return Math.max(40, this.b.bp() * 20);
   }

   private void eL() {
      if (this.G.level != this.a) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ehu7nxak10bx","pSQZkbf0/sLXPWfgfhNGfiI/AcJ+9Q1ADh98Nn+8Dfc=",5752579330202082382,3414450316716766666,-3628044416271908368,2129586238957977658>()) {
            case -450894733:
               this.a = this.G.level;
               this.hO = 200;
               this.hP = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3u3vyt38ewad5","O38M9q/LixUqIQsX20KV63gS9WNxgprFu+l5Mxq74Z0=",-2772462798001227132,8921224568892573280,-7021885784023475155,-303356711110536069>()) {
                  case 1697863420:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (this.hO > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s8qxilc3h9yy8","jBA3IOcxsLZVH1rKD2e3PkJp/tiwI0QaUABbYFU+8IM=",-3917612523745751783,5337737161113187006,6210500211949326478,-4671404594968955564>()) {
            case 486249594:
               this.hO--;
               switch ((int)com.yiyiaddon.m.b.a<"s2wpcmzmolwxfe","Izwbv6Mzzih8Hh1zT023OoHU2w7crz2s5yALkpgI0TY=",-2082061729671787924,5096741799801402857,-7407751370532272117,-7128983460111948130>()) {
                  case -1878349437:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private boolean cd() {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39mekl1kqvsef","JCH0unMCEf+syRy1H6njSXJshOM+6lav9zfv1h/gh5I=",5850415307459968973,7666597671703593823,-8063035955231802884,-6688390912266493412>()) {
            case 877307878:
               if (this.G.level != null) {
                  BlockPos var1 = this.G.player.blockPosition();
                  if (this.G.level.isLoaded(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s36aus3zu34c5c","v1/Hzdsck/FLWg9DnMUgPkAtcd0CREmnMIaFHy8EJqA=",-4573028211883876613,-6158908652733566374,-8745736959195755439,1414837253254484708>()) {
                        case 1579577412:
                           if (this.G.level.isLoaded(var1.offset(16, 0, 0))) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1volo9i2hpg0n","G1e/xhtGCVaE9snQZprGmLCBsbTwJdeLLpCVXffj+zc=",-5144256530096944503,8438515121952579314,4190914855996040015,8833712187625349544>()) {
                                 case -314234658:
                                    if (this.G.level.isLoaded(var1.offset(-16, 0, 0))) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2fe55mqq9vpqi","QQ2tR37gXfgNC/3toDzOWPaKxOwt6vvPjz5RLszqYY8=",8878253320374911908,-7777726240841688589,-5320984548724384251,-2556216031879137517>()) {
                                          case 1356256355:
                                             if (this.G.level.isLoaded(var1.offset(0, 0, 16))) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2j2tefzx5dzq9","taVIUUegwuZ6aCrlhl6H6Fyufmz4Jblowj+PJ0+yyn8=",4243607752728386395,3118958710186032809,4423906368790334298,5918149431096895220>()) {
                                                   case -1684725764:
                                                      if (this.G.level.isLoaded(var1.offset(0, 0, -16))) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1574xbh5h1yt6","DNnFJ0RwtGNFnpccjWcxvwNRfW1xDbVL8H1vceGz68w=",3841870229149385468,-2814557827834026208,-8831365977566122533,3317217310081190485>()) {
                                                            case 1926876016:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1demecnadaa8x","3e4VmLEQZWkruhSfCNSxhG9OJpFDx7ps3aCO4FYtU04=",-8295972943543494143,3602893016861474000,7051674451660923070,-2261714723529952153>()) {
                                                                  case -694314763:
                                                                     return true;
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
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }
                                    break;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"spql7cc6tkojy","h0mz8M+5EWZhiKwzMKOwFJI8UZ5gAz/e5mJCwgTgpGw=",-885859576368560788,7230114605021063038,-1808741285655408942,-4002240606639061836>()) {
                     case -1547355600:
                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"skmth62rcq8rz","4a7eRsy8K+XZ++WoBVj8Zhj3lGpfqtLW3eAq6bBbOGo=",7273229143760504525,2938720379557296338,1760058563717356442,6887088602376230319>()) {
                     case -536682488:
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

   public void ae() {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sylqs15jpupic","hjLQVz32vC8xVZJMrTGL8dsGIcKALFzUb3KhiigLa2A=",-569399689887761626,3521579431830412653,-8191874877010862992,-430036454594233714>()) {
            case -2074704685:
               if (this.G.level != null) {
                  this.eL();
                  com.yiyiaddon.e.j.j.d.a var1 = this.a.a();
                  if (var1 != com.yiyiaddon.e.j.j.d.a.NONE) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1c5m5ieiq5ors","8/tiUm2puW0rRTKJT/CtrTeyTiQ3RvPPIBx1PzCe810=",6363260919654118873,6389900914333347938,8580952926609134679,-111579443518569060>()) {
                        case -1287507900:
                           if (this.a(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2ww5bph4xbvoa","f6NuUw1Qn/T7WiozDJa5fCvr67GXSk80LSm9Am01khY=",5209843767524427529,-3935376517902997993,-6091255538004477970,3215102193816091609>()) {
                                 case -1132542731:
                                    return;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (this.ce()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sgjes2d8kbya3","Odxm9HQNV+E5Bn8yw1X2oF30r8iXBqKa22udx/NSsV0=",-5417842958831560111,-565209821678171928,4643763184062136330,-7749316023841986203>()) {
                        case -594718435:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (this.G.player.isDeadOrDying()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s36einoc77vvyj","oRXpyeKvr1nQsgLtl4QXUuDCTowWE+yT40OuaNSwXIM=",-5965397247747288418,-366166760487700774,-2085983260865061518,7326356603531889829>()) {
                           case -372093788:
                              if (!this.cD) {
                                 switch ((int)com.yiyiaddon.m.b.a<"shsi9tp3fow1y","+NTajRgNOAl6b11a1DOBheoP/a/P/8qWFlEVUQ+KxQ0=",-7750512853267965463,-3731092442707837761,-6830603298745331669,-5612907893707395248>()) {
                                    case -1301614893:
                                       this.cD = true;
                                       this.a(com.yiyiaddon.e.j.d.a.DEATH_HANDLING);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (this.cD) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3fyybmqipk059","FdApzlf85+1h6hU6xuvZ+nh5gRYFAiIhVDKFP97ReJk=",-5967820052970859709,-6935386168835185429,-5173686620418278709,-5189251555027338795>()) {
                           case 1693310115:
                              if (!this.G.player.isDeadOrDying()) {
                                 label135:
                                 switch ((int)com.yiyiaddon.m.b.a<"so77g25ks7fpx","a9y+9SP2Q64oBU7wMM9/jkHMJCT/j1U3F24CS9ypBvU=",-8309637273832638398,6328292442994674141,-7811403476816841812,-935037495388265525>()) {
                                    case -1897286746:
                                       this.cD = false;
                                       this.a(com.yiyiaddon.e.j.d.a.RESPAWN_WAIT);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3gb2xqc14np4a","2R8VAS1alrPbaGJP7ZuenQrzyK0/6o+qi664OJcPvoE=",44740132538880125,-7003281099277362568,288944760600852179,-2087761042450539195>()) {
                                          case -639386602:
                                             break label135;
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

                     label174: {
                        if (this.a != com.yiyiaddon.e.j.d.a.MINING) {
                           label131:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ijup1g1qi6a6","fVrCN1spmF+PV41LDeah8IRQ2qgt54Nin6U/U39JBu0=",-9084846766327409440,-2164026957506227935,3448463005656212787,-2804634638577951619>()) {
                              case 1423024388:
                                 if (this.a != com.yiyiaddon.e.j.d.a.GO_WILD) {
                                    break label174;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2d72rjepks64m","g1kB7WF+RF3RWv2deQp/eZQs/ncVYF7+rT8TMtoligA=",4806854357438009742,7314002449056306056,-4973479923463967133,2410867809600459883>()) {
                                    case -2113328011:
                                       break label131;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        boolean var10000;
                        label152: {
                           if (this.a == com.yiyiaddon.e.j.d.a.MINING) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1kpvjmghuuu8","mt9K0kXy0tTy5jfPc7rp6R28koESZHZT0/KMFtWZAxI=",-4984616675602542363,3101760063824669790,-6099287557990814293,2781567040477203046>()) {
                                 case 1439373035:
                                    if (this.b.bG()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"si6gd3w7swrnj","u5cwoVwqgti5KbYmonPzT21HLkYeLD1wevlO2Drz9xo=",-8802017950264807351,3872895048632567971,4060608950523901139,-1180816508394439943>()) {
                                          case 1773450064:
                                             var10000 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"s3looc282msq25","hR7BJqHxQVtIie4dIsB2nuB6hVE0L4I48BECIOobJws=",5482262762297027275,-4104128175604707782,5089102347205126958,-4308905266146918498>()) {
                                                case -1815068058:
                                                   break label152;
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

                           var10000 = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s25pyyl6yukuqb","Bvx7OL9BT91VWQFyxzVVVQ0z872eVubFW9K6aPkIG8s=",6685771836465164353,-1243974332820393526,3506522236941001680,-1337890820718528359>()) {
                              case -2133327251:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        boolean var2 = var10000;
                        b var3 = this.a;
                        boolean var10001;
                        if (!var2) {
                           label117:
                           switch ((int)com.yiyiaddon.m.b.a<"s1riuqsbnnfajt","7Fdp+tPfP4ZSAu7Uck3qkE252d+LlafBvI8sakVxpN4=",322381635462366654,5477769559214468748,-3456419693426983853,8946387345004535942>()) {
                              case 606120310:
                                 var10001 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3d1r1tl66ro8u","Tq+nGiMBLKP44R16Cen5sOIFztvy+CCMQF0nk5vs40s=",-5213419070455349049,-4832305130350661953,8681478448759853162,6989624241032766509>()) {
                                    case 2012094740:
                                       break label117;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s2yjdigqpbdpvx","Mc6Te6WR3DQhqqOjV6YlCG2EcwuHpq7Xjlj91JZmxEs=",-3071049189276867281,4904878495190516423,-2528737387287786982,7832987752524424880>()) {
                              case -243138136:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        if (var3.b(var10001)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2vjj1n6z8q1je","7xi8vq/fa36Yr1/T6126a3mSrhU0cy0yFtyVXBhEUHs=",-333247273168291345,5500989670510324024,3938396157651536462,3906863963212611234>()) {
                              case 1944012869:
                                 this.c = this.a;
                                 this.a(com.yiyiaddon.e.j.d.a.COMBAT);
                                 return;
                              default:
                                 throw null;
                           }
                        }
                     }

                     this.ab++;
                     switch (this.a) {
                        case IDLE:
                           this.bI();
                           switch ((int)com.yiyiaddon.m.b.a<"s2mcilidfhf00i","gEAzXhtOCY+c7xOklQ6UTqVtGktykmkfpJ6+2MqSsYM=",-6803539490219897666,6239502485410910770,-7357960856194733212,-5947382983704046306>()) {
                              case -3767360:
                                 return;
                              default:
                                 throw null;
                           }
                        case GO_WILD:
                           this.eM();
                           switch ((int)com.yiyiaddon.m.b.a<"s2tdruq1lpclmb","5EhleonA/27OhHIu7fru+XJ00NMAqn207mdtxnA3C5s=",-6826577788481032218,5086143311156593676,3226748940276296317,-5259716135992611789>()) {
                              case -1372701202:
                                 return;
                              default:
                                 throw null;
                           }
                        case MINING:
                           this.eN();
                           switch ((int)com.yiyiaddon.m.b.a<"s1y5lh5724j0gf","JYc+P7HFmohRYPg1Uwu94M6I34xdVghNB1A07qfUn4Q=",-5929876892723998989,-1170996070790511781,-7134757552164514514,-7817270062827810393>()) {
                              case -955110884:
                                 return;
                              default:
                                 throw null;
                           }
                        case UNLOADING:
                           this.eT();
                           switch ((int)com.yiyiaddon.m.b.a<"s475qluu6phi6","oKA5M4rd8HbqpH9o3Td3riHmUOTEE+C29ZPmvxtenFk=",4531587483287647776,1795860366213886916,8981914926698524770,5568502020001072236>()) {
                              case -1319471795:
                                 return;
                              default:
                                 throw null;
                           }
                        case SUPPLY:
                           this.eU();
                           switch ((int)com.yiyiaddon.m.b.a<"s20t1ovr0cmife","wQsq4edeQRstXX9DfcNl8dGAxfbYZkgE0L6FHQWNyJ8=",3844844218184223637,4958616964443313571,532292026542298981,-624881512493791044>()) {
                              case 559096640:
                                 return;
                              default:
                                 throw null;
                           }
                        case EATING:
                           this.eV();
                           switch ((int)com.yiyiaddon.m.b.a<"s31lpcm8xvtmsb","otLR9ktiliSXAh2eOnRtan+J1L2xIbkio4gVZRp0tPI=",-679743750426116468,-9041706594114409960,-3583603972083658812,3256438334982193922>()) {
                              case 1406955261:
                                 return;
                              default:
                                 throw null;
                           }
                        case REPAIR:
                           this.eW();
                           switch ((int)com.yiyiaddon.m.b.a<"s2sfrv9hm6nk4v","DOA5WZz9P27r1zKXZ9nSu/oAnnxVaAcoX8+TPq1bn+c=",-8002070180172879577,-8967641954303240481,5085718022356642598,-2524855648942190062>()) {
                              case -1145580494:
                                 return;
                              default:
                                 throw null;
                           }
                        case DEATH_HANDLING:
                           this.eX();
                           switch ((int)com.yiyiaddon.m.b.a<"s3vnfqi16x8dqd","JhM22NPcc1Yw6CIxEcrpwWCqtFyD74yjxjiv2//fdmE=",342118342040669788,-1077322186173229370,293699894226018356,-7201096048545256588>()) {
                              case -1766742205:
                                 return;
                              default:
                                 throw null;
                           }
                        case RESPAWN_WAIT:
                           this.eY();
                           switch ((int)com.yiyiaddon.m.b.a<"s3qwcf9het0ubj","v7oqdfltfG/Ke7Z9CWDxTwp+61uSzDZ9Qg10HKfpYpI=",-601682715832345001,-714648303556441928,2471936857796240502,-594791413157313597>()) {
                              case 1129342150:
                                 return;
                              default:
                                 throw null;
                           }
                        case COMBAT:
                           this.eO();
                           switch ((int)com.yiyiaddon.m.b.a<"szl656mdz3s82","rNtVP3xYd1dn3uJ+axc1xhoTqnjZf6NjcMd1vzLYc5A=",-5268209007566383820,740687193686774946,3625410560490444484,-983085277747452408>()) {
                              case 1723196829:
                                 break;
                              default:
                                 throw null;
                           }
                     }

                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1hfha1m0ajxwy","yTykPCTgZDPyOeU/FE11jbCLsbtwbYrgLyjZdeB4akY=",5201439108599799751,-3258009205769452302,4610176071245121341,-4269923222151704143>()) {
                     case -1357897971:
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

   private void a(a var1) {
      if (this.a == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2maj57pviakzn","P2U2AzUoq2y4I32giLpCWUZ6GocVSN+kQz3plz5fl/U=",-1780390685462217171,5553217307349958289,-8340222216921927624,6922181892090206819>()) {
            case -1543748960:
               return;
            default:
               throw null;
         }
      } else {
         this.c(this.a);
         a var2 = this.a;
         this.b = var2;
         this.a = var1;
         this.ab = 0;
         this.a(var2, var1);
         this.b(var1);
      }
   }

   private void b(a var1) {
      label234: {
         if (var1 != com.yiyiaddon.e.j.d.a.UNLOADING) {
            switch ((int)com.yiyiaddon.m.b.a<"s1pabwq6sxwelt","QjLXGvMU5j0LTeg/LarOGyi7XoYL+V24ArsMfNd8NhQ=",6390734187207570843,8114340019512796757,-1396942439999598583,-7433408655340563573>()) {
               case -799946345:
                  if (var1 != com.yiyiaddon.e.j.d.a.SUPPLY) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2jves6865xdvp","dJ2O3C+STM1bAEIviENCY5c+7RNfIemOEjkOkKUutRI=",200354690374978592,-9169649751868522991,-8127287394498967734,5434165875759051994>()) {
                        case 117264067:
                           if (var1 != com.yiyiaddon.e.j.d.a.REPAIR) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3uzkxe3bmptd6","EJ5unSX4gZHUjGl3dxjpNxQ9cQISzxuJXEIETbYBCQk=",-5987191932155841951,-65881398261334235,1943908830145124693,5927958553826062282>()) {
                                 case 1692041471:
                                    if (var1 != com.yiyiaddon.e.j.d.a.DEATH_HANDLING) {
                                       label181:
                                       switch ((int)com.yiyiaddon.m.b.a<"s205w9p8ctaclg","E/AQHuQhpLljjDMmnoMIXUTgAG4rcH4Tmjd2yH1st0U=",-8165336737372481252,-3296855175420251737,8049411218963676765,-6030082641802060967>()) {
                                          case -275608706:
                                             if (var1 != com.yiyiaddon.e.j.d.a.COMBAT) {
                                                break label234;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"scc6d40qgaetg","sBgALLa9M5tkpE+3UPMTN9iKS3EU4KcbiWCIhJONTow=",7030100484955872158,8350543609631535717,8696753907278427415,-2808341391670917174>()) {
                                                case -1253438567:
                                                   break label181;
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
                           break;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         this.b.a().cD();
         this.b.a().ag();
         switch ((int)com.yiyiaddon.m.b.a<"s3roe2wsceid1k","WiN4ggJ+bRBtWItNrkvIlBKSn/IcaB3gSWsp9/IZby4=",-1970290415000732512,-9205772314607303037,8187966312192585820,-425959236958477448>()) {
            case 311907668:
               break;
            default:
               throw null;
         }
      }

      boolean var10000;
      label221: {
         if (var1 != com.yiyiaddon.e.j.d.a.UNLOADING) {
            switch ((int)com.yiyiaddon.m.b.a<"sjnj17snlibuv","JSrSwVTjdWse6GLsJNVWJWtIV6ow+UNIpazIJZiut+M=",4869024036845326026,1844792019125043478,5209447384265988239,-3704009863815370467>()) {
               case 1619834794:
                  if (var1 != com.yiyiaddon.e.j.d.a.SUPPLY) {
                     label173:
                     switch ((int)com.yiyiaddon.m.b.a<"s35b6vuhoy02fe","bFHfmiJ6AcaOoW2R0rkYIAQhdSSfNEiTkvPwI0EYWgw=",-3794099425448125523,-2921479700035041181,9021163184442059380,8814664655043656091>()) {
                        case 2073518421:
                           if (var1 != com.yiyiaddon.e.j.d.a.REPAIR) {
                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s1jh32vnxtrytj","21yNHsEhWznUjb2YKp2cgYrUoajf3dljoYvAYXD1Ns0=",4714088411334990216,-8742806027301094782,8048457247529480182,-5053520817286345351>()) {
                                 case 52767910:
                                    break label221;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1w9dnj10i6o77","F726N2clll9/fNk5sZ8udPD9/0MKqaHceYT/Lru56jQ=",-959774930978838633,-1914960323141892937,-1528001747835647649,-5767271138255455721>()) {
                              case 809418182:
                                 break label173;
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

         var10000 = true;
         switch ((int)com.yiyiaddon.m.b.a<"s1aos27niqnnqr","eLcE+aVvg0FPJRlL+ZlNr7O1KSz2CLSi2c+1JLb9unw=",-3073150724270789250,-972653094058557989,515579780041943424,5272386809612453362>()) {
            case 689421428:
               break;
            default:
               throw null;
         }
      }

      boolean var2;
      String var10001;
      Boolean var10002;
      var2 = var10000;
      var4 = this.b.a();
      var10001 = (String)com.yiyiaddon.m.b.a<"s15e6hyd1lbzu8","sWntOCT6CMKEBo9MQLz1xljOOYWVEOVGatuIs/HWLudZPT5cblnKEfwwZBT3KzCK",-1556488207012372422,-7282443142352647503,-8053149972278958931,-8296424199670431334>();
      label166:
      switch (var1) {
         case UNLOADING:
         case SUPPLY:
         case REPAIR:
            var10002 = this.b.bS();
            switch ((int)com.yiyiaddon.m.b.a<"s2vnzkngt9hv71","e4eg+nXGO3qcc9SOl4rSq8tdnEe8VKJ1ILtLbgHtIf0=",-3695951562424595603,3541867711167497836,2658772513053534209,1283972580024362028>()) {
               case 482482566:
                  break label166;
               default:
                  throw null;
            }
         case EATING:
         default:
            var10002 = this.b.bO();
            switch ((int)com.yiyiaddon.m.b.a<"s3hn04b2hhbnnc","Swxgwz8ltDR2s3S2WcHISOvgqPq3sbrn75Hj8V510tk=",3993012224989445996,-695411356956270550,-9221409114008055652,-223233135506671507>()) {
               case -859763547:
                  break;
               default:
                  throw null;
            }
      }

      label211: {
         var4.a(var10001, var10002);
         var5 = this.b.a();
         var10001 = (String)com.yiyiaddon.m.b.a<"s2j8vd45jdf685","JIGrReRA8K6VhgZLKX+M56x+Iy5QqjR3yyFF59BtcPpBhGE5kmAtS9W4yQAVo4HZ",6910576740331236279,6523931002908278224,720043912931404511,8732478463887416385>();
         if (!var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s869k8qs70wpr","JVBLTxxZutRx7qVa638ilpZTeLzDXAvTi14lSwzDgns=",2159324435782711595,-4135326674581182109,-5765636861310363265,189353856477966475>()) {
               case 161977324:
                  if (this.b.a().bY) {
                     switch ((int)com.yiyiaddon.m.b.a<"sjjtavsins0g2","S1RPOL0wMxoNoj6d8V6tXKuN0u7OmcMmH6kdKqIFfUY=",5373006705084597344,8450647012607646881,1027980235134517445,-6273236666479808483>()) {
                        case 1184396819:
                           var11 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"sa4y1u12x9h8r","vF9jIJ1o+TdzH3y4eANEQAHUYAJM4rEL/6TaIi4VLdE=",3541183152862876308,-1757800116064776282,2536041864230037336,-7544059301249849737>()) {
                              case -1170319448:
                                 break label211;
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

         var11 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s1jsbrijttuhob","XkYDfwZKlEX/Mwpl+2hDzkgfqd3pQQAUXyJFG5LWw+w=",-7781716720214446789,-1489316721803477651,2381804951419334311,3441548408815801841>()) {
            case 599569750:
               break;
            default:
               throw null;
         }
      }

      label205: {
         var5.a(var10001, Boolean.valueOf(var11));
         this.b
            .a()
            .a(
               (String)com.yiyiaddon.m.b.a<"s21irknkwvc136","+NGLyGx2o3yVP3/+eJSmQwVDFiq7Szx7gQoHcz4FmN0w+qlzLl6zgLkQOcz6JL1OJ2JnVg==",-3203331182823233462,4605291720866285762,6624759310708869041,-7775273345698087579>(),
               Boolean.valueOf(this.b.a().cm)
            );
         var6 = this.b.a();
         var10001 = (String)com.yiyiaddon.m.b.a<"sw8usw6xy6epb","tzf3HXf3Ks/1TdxUYbWsICwm+f4miAdtqCb6w14gk0Z4ptL5HWW7FnH9ptyJli+uk6AKavp17TWFG8/4Vtc=",7396875077669165096,6765293923293291084,-5471731400082783259,8130578168683376221>();
         if (!var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s3fhhi46biye0r","eaeWqzAWGtDLGhmHCoR1lsdHlBLdIkNuCoTszew1/CU=",7382721035765672764,2950301287971711385,4164677677192323861,-1189355542137611771>()) {
               case 596267160:
                  if (this.b.a().cn) {
                     switch ((int)com.yiyiaddon.m.b.a<"swfrqk9rgl6om","WOBu7AlMQigm4oAq0QRlTxB8V3MSLA+Z7jLQ2t8+ruA=",6053689699004215857,1252368734214086473,5584095905742250539,5305270305488802772>()) {
                        case 506483948:
                           var12 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s1p483771aasi","2DduoqOVNV99KdmGugn9WYrooDvlsh4i4X1rPkx4ZWg=",-5849286378840217119,-8671789173341481466,-5858938360966962102,-8394947565287586911>()) {
                              case 1438411997:
                                 break label205;
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

         var12 = false;
         switch ((int)com.yiyiaddon.m.b.a<"sdx6w1dsv1uw5","xRKIKsaEmsekGL3j3zHYNNwBcBxVYvGWHWT6HeSWb5s=",6270783941243123952,-465094176711925721,-3069624412889818460,-3711231238856636321>()) {
            case -910807515:
               break;
            default:
               throw null;
         }
      }

      var6.a(var10001, Boolean.valueOf(var12));
      if (var1 == com.yiyiaddon.e.j.d.a.GO_WILD) {
         label146:
         switch ((int)com.yiyiaddon.m.b.a<"s3fgojil3glx48","hFtiYAhO/AkBX0KHbbOXOQu62oXiqhyYoJwaA/PoNHA=",-425179426971001950,6441829814707119124,-9194267572169688013,8807054739775389297>()) {
            case 817385136:
               this.ho = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3eegvwx0odckl","21Z4LI/OsPfaYgDaLGaS/y95YMEEkZc20IIVdFrlclw=",-4941311827707139233,1562387216207400,-3211608086584903837,7638567931687450687>()) {
                  case -1722006904:
                     break label146;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.j.d.a.MINING) {
         label197:
         switch ((int)com.yiyiaddon.m.b.a<"sazdcly3dg0bj","Hinc4VoIrb0so8pE/O3WiyYnUFiQNv2FhqmjfWSS5os=",2171440464000857773,1452578842529268814,-2528175414962984426,-4758853423180382550>()) {
            case 1645538378:
               this.hr = 0;
               this.eQ();
               if (this.b == com.yiyiaddon.e.j.d.a.GO_WILD) {
                  label142:
                  switch ((int)com.yiyiaddon.m.b.a<"s2m2vb8xajpe8a","RBk9xp8XOx2CAWbf4xTC6d4cYCmCFLM05ZomLcrH2Hc=",3371240815442318268,5690681902985099020,7311076116109349330,-2680813437455953001>()) {
                     case 1355168149:
                        this.hF = Math.max(this.hF, 100);
                        switch ((int)com.yiyiaddon.m.b.a<"s395qp8xfj9uxm","Om4/8YoJ964XCywoNr3anZMhawxaxhMXVgL1y6Apn6I=",4463291149808749892,-1034521159196568342,-1762692257625711083,1929380402176809775>()) {
                           case 2001529708:
                              break label142;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               label194: {
                  this.hs = 0;
                  this.id = 0;
                  this.cF = false;
                  this.a.f();
                  this.ik = 0;
                  this.ip = this.G.player.tickCount;
                  this.cC = false;
                  if (this.b != com.yiyiaddon.e.j.d.a.COMBAT) {
                     label138:
                     switch ((int)com.yiyiaddon.m.b.a<"s3ro6kb067be1w","n36DtN01i+OaXMTkxaPcmDekL3OAs1ipVYySbJBPj8M=",645482403885932578,3770998612258867466,-6046146888262160267,6061945264586760924>()) {
                        case -892219790:
                           if (this.b != com.yiyiaddon.e.j.d.a.EATING) {
                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s2z22x41es599k","mnbG7GuBX0ZlWxlnXKAM8vkyBNnz3f9OvxHBTeroJZw=",8655880532692675385,359294217770153985,-1647700240428541756,2178056529525268805>()) {
                                 case -196703493:
                                    break label194;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s352vr0uityg1n","Px/T+BIeb9uU1fS8JuEilKczbyUQ/4qnjebjURastWQ=",-7890849459542672570,5092301723239056344,8441769534053757302,7968130404550107762>()) {
                              case 60497295:
                                 break label138;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s3ejyt6gy2ohh7","qPHyPhAiUUTPKzhWuCJ7JOBVIBP3R9wal2Ov17WTw10=",6159403094877140512,-1316257582876868427,-1916994399818980896,-2972822892174699603>()) {
                     case 620483191:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var3 = var10000;
               byte var10;
               if (var3) {
                  label129:
                  switch ((int)com.yiyiaddon.m.b.a<"s25if3kl7nzpmy","rtWjdT367Kkt/q4PVWqLXCsQcxudCaU13M+iZjEkV28=",-4025428240334399982,2027847105399801164,-9161673099015824844,5784844433648920874>()) {
                     case -176821463:
                        var10 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s2rzks3atbs8kl","FSNaHNOSkbiXtW/2DXRsfuVKmyZlOgoijrkiHUj/c2A=",-2335669403301254313,71141068702728790,2691370379489693260,5537633528516528777>()) {
                           case -507990294:
                              break label129;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10 = 40;
                  switch ((int)com.yiyiaddon.m.b.a<"sezuersei6h2h","75aHkq+IvGQgf1J+sVZFseYLXFlDttxsqc46EKxTQ3M=",6790977349404165832,-6421270140006026040,-3378503858297553293,1292446890497521196>()) {
                     case 818602359:
                        break;
                     default:
                        throw null;
                  }
               }

               this.hS = var10;
               this.hT = 0;
               if (this.b.bK()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s717jbj2b4zqx","1B9zBzDsVPcvbdpBs2SQ2b1HFRt6Hlq7lbgtLoF+jSQ=",4069958575748169431,6616348802022958747,-3204325489004023672,6425249031343990047>()) {
                     case 988043238:
                        if (!this.b.bL()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ey9dejcuz8ad","atpJ5tx8pKZcW+QXjlsNcrcBq8a3zGY+zre+5B/P1UU=",-5415655240089234324,-7522257967385080185,-2360719328465048153,4094283063752895551>()) {
                              case 1769137044:
                                 this.b.eA();
                                 this.b
                                    .K(
                                       (String)com.yiyiaddon.m.b.a<"s2ykbsc4iqaqcs","65KHsGj+7VErj09dWsm2w3CQxsTADDJkN5j+TxVOuxp5HGOVIHXzFOYK3AWG01RYjAbMbHMHqD8wGTbzuTXt7zHfU6TAjbmvIxj9c8Ug",-779937069925617093,-6568255079460423886,7459373027096316291,-3325231591864731451>()
                                    );
                                 switch ((int)com.yiyiaddon.m.b.a<"s24vmoyg1xpy","UW7FpBm3CxuhguHV9w+zjheqhdbwsSB9UKtWZdVrqXA=",-5188896374733714219,4678716555614387333,-5858928131848020642,-2406167179134015426>()) {
                                    case 261939299:
                                       break label197;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label197;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.j.d.a.REPAIR) {
         label116:
         switch ((int)com.yiyiaddon.m.b.a<"s1ear3yqi8as31","OGQTDDy4sBsGdeG4VtR/3A+CDcrS8KUdXO5J16My09c=",2922506949077084197,6049499453883710967,-7848978553730240359,-9047129150095673251>()) {
            case -1091672950:
               this.cx = false;
               this.hv = 0;
               this.hw = -1;
               this.cw = false;
               this.ht = -1;
               this.hu = -1;
               switch ((int)com.yiyiaddon.m.b.a<"s3360160xnwdry","Jp01OqH3+CE7G31k5yLbr8hwq6WmHB5pSCN8JfSMwOM=",-8978829471882832674,-4338606411659390572,1833367018296616406,-3361276468298901856>()) {
                  case -1240758578:
                     break label116;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.j.d.a.COMBAT) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wm9v6hp70m15","+g3QB4ViZYT4baBo0UAbwUeu0hacMUtMY9e//vSbO5k=",2522250730936273548,-1959552675555106002,2873918126271089453,-335531192034704985>()) {
            case 2078462393:
               this.b.a().cv();
               this.b
                  .a()
                  .a(
                     (String)com.yiyiaddon.m.b.a<"s62f8oqmxl1b0","7+PZ32yWp9BPrS8rNUgMnPl8DhduJJn8R5vwVHilpOknt6ezXgsXuw0SFvvYfw==",7939852458235329367,1310476688382657900,-1617592746853861973,-218494927959642801>(),
                     Boolean.valueOf(false)
                  );
               this.b.a().m(true);
               switch ((int)com.yiyiaddon.m.b.a<"s1fi53iqfiu8ze","KjIDMfJaMxK8GJ3osQk/sVCdYRU+4209oHMoHuM7cTc=",-4231960606299777744,6249357974601646128,-2576649478139021572,-4885247578286765124>()) {
                  case -797152565:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void c(a var1) {
      if (var1 == com.yiyiaddon.e.j.d.a.MINING) {
         label54:
         switch ((int)com.yiyiaddon.m.b.a<"s33spoa0g2dxg","9ZbsZAIDsxS046m9qyLpmQg4xlTtbcI3Ny1TY0hceOc=",-9198316000932842234,-8711396577706055526,-784143694819862156,-4853313866645090274>()) {
            case 1746727830:
               com.yiyiaddon.e.j.c.a.a().a(this.G, true);
               this.b.a().f();
               this.b.a().ag();
               switch ((int)com.yiyiaddon.m.b.a<"suzafwg946332","HB56EWOK4S8/+bHQs/6Vksk/sRbq6JvBjM9e9NCcGYg=",-3484217451385381558,5459125690987910339,1717572991085221457,-5960293271814744947>()) {
                  case -700381176:
                     break label54;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.j.d.a.REPAIR) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s2iau7qg2rzhw2","yvREQI0fvz+tMBO0UG+CKKjTUnV8DbITWl6C5kdYfcc=",1299557042929745945,-3679737314785971858,7640921673174296888,-8796656024353204222>()) {
            case 207448256:
               this.fa();
               this.eJ();
               switch ((int)com.yiyiaddon.m.b.a<"s5d1pl6e1fr2s","WRyICFyPip9qBeS4w0U8vIt65LyxDk7faIMUHhlaCj8=",-4193696434250014165,8459495282246368798,-5770430977219413456,-7878367736082742012>()) {
                  case 997422019:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.j.d.a.EATING) {
         label47:
         switch ((int)com.yiyiaddon.m.b.a<"s3m7f0pctz88h0","PRKW2IulaxXozQLnT67DdRW6AGcUTDBhpbad7Y5Uobo=",1582604317681872483,7469647685504198762,1604959212488294677,3926424445367970071>()) {
            case 1433056477:
               this.b.a().fv();
               switch ((int)com.yiyiaddon.m.b.a<"s107yfamirk79e","kGH1wH2cVtAuFVO4KOH/fqkSpiJHYshCKiOugH54iMY=",3191911160982904213,6339211104179127395,-5742570149263955395,5664701155270608292>()) {
                  case 1305862753:
                     break label47;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      label61: {
         if (var1 != com.yiyiaddon.e.j.d.a.UNLOADING) {
            label43:
            switch ((int)com.yiyiaddon.m.b.a<"sibhl4lyqez4r","WTdGT1r+azaubWGFe5Qr8polyROtSnVg2h3/8T2UXsA=",4515819622040113659,1246358678257472577,3296508145133069243,-1270123003302535611>()) {
               case 509485905:
                  if (var1 != com.yiyiaddon.e.j.d.a.SUPPLY) {
                     break label61;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1ku0ka1lcprh8","gWtiRznj4qYqxOSkRZIUEGE8rN/bqLGevO/OhgZh4+o=",1355029735093050920,-5874555625875848076,7313648022076078179,6036202212918125078>()) {
                     case 1723434553:
                        break label43;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.b.a().cD();
         switch ((int)com.yiyiaddon.m.b.a<"s3l3kmj09kt5w5","96uBEsJbzqqzk5dymfxdCwNSvR7zDIW46RjdGJIfBH4=",1630127140070510014,570622613898802237,-20549819331013728,6356809676240048943>()) {
            case 1573128769:
               break;
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.j.d.a.COMBAT) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kdf8mh58hrac","vEQr++8OZ0T/mlhjHmlLx3u9w+bmbBo4Xd1n/1fO/bY=",2833513651306294450,-960942829957146184,-7452159771585883208,-1036335787283118496>()) {
            case -60545617:
               this.b
                  .a()
                  .a(
                     (String)com.yiyiaddon.m.b.a<"s62f8oqmxl1b0","7+PZ32yWp9BPrS8rNUgMnPl8DhduJJn8R5vwVHilpOknt6ezXgsXuw0SFvvYfw==",7939852458235329367,1310476688382657900,-1617592746853861973,-218494927959642801>(),
                     Boolean.valueOf(this.b.a().ci)
                  );
               this.b.a().m(false);
               this.a.f();
               switch ((int)com.yiyiaddon.m.b.a<"s3asc3y1fl2tgi","rvShA8oPeVHdA653uTkVmMKLthO098qegrdqDUEYGSA=",3371177947664315916,1378333623435590155,-7547734889490779031,4310068277095283867>()) {
                  case -30301054:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void bI() {
      this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
   }

   private void eM() {
      com.yiyiaddon.e.j.j.c var1 = this.b.a();
      if (this.hp > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gbftf5a89g57","WNVngCLOufr8nqxy3pAI1PgXwJNrPls2oHMMGe1Iwhg=",5010674151080004275,-1974196993216045025,5990087723941568989,8208347186197067670>()) {
            case -2057030594:
               this.hp--;
               if (this.cG) {
                  switch ((int)com.yiyiaddon.m.b.a<"spqwuyq141ycz","MT0zR6JC46ua0Q2UUmRL3yOF1SD4vWh6IXIUVVWvIMo=",7325577795148944979,8803805002664058656,3598988807880371027,8486040250784122023>()) {
                     case -1327747280:
                        this.hp = 0;
                        this.ho = 0;
                        this.cv = false;
                        this.b.a().fo();
                        this.b
                           .K(
                              (String)com.yiyiaddon.m.b.a<"s5o2g760yt9fy","gdth6mLeoIHCVAS69DX16QT0hHOUAvFj6O0jlrthr47KAC00ywGpSYyjzBXYuN1Ke0AhUHCOIm9ZnzPmfFF4rw==",9112961950034857311,1219652456068092498,3656764493160228558,-8702692184561275914>()
                           );
                        this.a(com.yiyiaddon.e.j.d.a.MINING);
                        return;
                     default:
                        throw null;
                  }
               } else {
                  if (this.hp == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ahijsmhe068l","naGrlGt36w7deATjMAlPrq0O12/UTmyUUHKczrThWEg=",-3024193476071945349,995400454461689458,-5571329729089762643,8662211485893864938>()) {
                        case 1526602260:
                           this.ab = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s2xlhmvpa45o34","xwjNOyi8HKGc+CF+AahziP0fdy85FGlEGiC10ayk6pU=",2713633044432564979,4399598422152196634,-7146272809554903256,8067434145771671704>()) {
                              case -1177886604:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               }
            default:
               throw null;
         }
      } else if (this.ab == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k8s4qt7bcwqe","jMOAYb2Quk71E+c1hPh7J7nqafI0Do0TszC6OCMupMQ=",7350777208969305018,-1604640018112893473,-420574805940371785,3322896570101343442>()) {
            case -1595593061:
               this.cv = false;
               this.h(this.b.bL(), true);
               this.hn = this.b.bp() * 20;
               return;
            default:
               throw null;
         }
      } else if (var1.cw()) {
         switch ((int)com.yiyiaddon.m.b.a<"s244t22b1bz3ik","Kgqc1mgvFcrGWNCE5lN9WRNRCyLyq8CnYubGyUJd8Zw=",-1218665967922141955,-6261885392698546331,8601411872573370626,-3928006087326769199>()) {
            case -415779195:
               return;
            default:
               throw null;
         }
      } else if (this.cG) {
         switch ((int)com.yiyiaddon.m.b.a<"s2oy3bnxs70pkz","gEmePSN8VAx60vU2wBSWRWYAsmtLz2SjIY2X7S/2M6E=",3818825727885602588,-948560961212634965,7453550081610950329,-3437053428045599272>()) {
            case 962356392:
               this.ho = 0;
               this.b.a().fo();
               this.a(com.yiyiaddon.e.j.d.a.MINING);
               return;
            default:
               throw null;
         }
      } else {
         if (this.ab > this.hn) {
            switch ((int)com.yiyiaddon.m.b.a<"s2jl9ldivtzw0g","k3dKwAewn5rnrlXDD+dDPz0OHBD5vC67nYai+QH7EsA=",-4176355613306076170,6996682975069035799,188719854617247877,7703453408876188268>()) {
               case 2007313670:
                  if (!this.b.bN()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sri2j9qn7zf0n","Y6NK7FYm6alYCSTdcpctf2fVtT5DOZEADqUH6LwIe7s=",4643923089905474760,-8532692167738563895,-6152095554119822057,1423163336092242367>()) {
                        case 200680593:
                           if (!this.cv) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3absjuoceabqc","wI0Sg+lkJIKmSHqm0aHeex/TtVL9i91Tve95MqkR53A=",898931811251651277,5419396083392708893,3810154479726184719,8857715643597391582>()) {
                                 case 1840904134:
                                    this.cv = true;
                                    this.b
                                       .K(
                                          (String)com.yiyiaddon.m.b.a<"s205y8n3be2sv4","eV7p9q8ZujubFWJ0Jd8LCXObRct/gp1VxzofvnOyqneLX3Hlh+QftUe9ndA2H+L3TqGsyfm353tQDpbe84kO/Z0i78AruQ7vUJXON03gp+JveNU7omsVgg==",-5700243211064511306,6222516553252276978,3543302140785036474,8531621831480115182>()
                                       );
                                    switch ((int)com.yiyiaddon.m.b.a<"s2brbg55pgk696","OG4d4hyKCLvjRpnSEp7ck5iZ8CHW5MsuNsMfOJzjQXE=",4434996071259894745,-2751303818811040570,8284064132841959646,-2736913549882578669>()) {
                                       case 822475089:
                                          return;
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

                  if (this.ho < 3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s31xz0yn913cur","CzS4Ta6JHIP6fLWtWly7MT33ZBzj1Nukk0if1cOb/68=",4505518665459325015,4328774347114867270,3336832828603601728,1862911664683059092>()) {
                        case -424469298:
                           this.ho++;
                           this.hp = this.b.bq() * 20;
                           this.b.b("" + this.b.bq() + this.ho);
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.b
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s1wlhpm7euq6xx","RZ+OMfr1BCFcNdaMf4UBtm7hy2h2wxIhOLp4P/25NheO4SdU4kIM0+WX84zDQSk+VzdSb6FR/dDZdeZGDpCMUa49mqJJweDlEE1m2Sxll6oFqg==",2252750569870436261,-2682302784075143284,-8177080054017485397,7899697624143365225>()
                     );
                  if (this.b.g()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1jk8juecnaq34","2BpI5fjP8tfTOXuJcz6BSprxUVH6ItxIa3hDCuiG/JE=",-3796934299394618819,-8993287868429384398,-6786796772300985880,1677840445965352512>()) {
                        case 1182225695:
                           com.yiyiaddon.d.b.e.a(
                              (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                              false
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s38vfjbjfra548","Na19QRtPpFWy2j+IHqs3dq0hA5jZ5VwbnIu9E1BitP4=",-5582424106233959095,9048977371864821453,5514072990223277938,2500974752840801318>()) {
                              case -1695120006:
                                 return;
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
      }
   }

   private void eN() {
      if (!this.cC) {
         switch ((int)com.yiyiaddon.m.b.a<"s3mz2c1th0pnoc","1TkZYduLSTs1vxFH/sWbmx8LhVeCpISVHO8JWHk35fY=",7955671883815279273,-4096406453088960279,-1200004649542749798,-6235015353600567346>()) {
            case -760163072:
               if (!this.cJ) {
                  switch ((int)com.yiyiaddon.m.b.a<"s320mm0di367rq","baDLahCkFWw4vd5OpZwl4/KcHrAdTeZLrys3lkdVIpk=",3438604505269193048,4887991203754916897,8981415696988280280,-1570930602475653971>()) {
                     case -1250786390:
                        if (this.hS > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"spyete8uwvuzo","D3eGOvSjRqAbLYlUJfuayOgiA4Qoc5tGkJL7iHqANdY=",1721494972469135779,6971986413852456467,6941116101992663421,3600764068981054429>()) {
                              case 997240453:
                                 this.hS--;
                                 this.hT++;
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        if (!this.cd()) {
                           switch ((int)com.yiyiaddon.m.b.a<"spgta6g8p7td3","Vh1EcOUfmCjNU7hvxNvR8JWMw9KFqDIEsQv9ppROV4M=",-7002775274681035816,7457043277762746664,-8254932250179798439,8205360962844032878>()) {
                              case -354095339:
                                 if (this.hT < 200) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s9ru74u0xpcuw","igf+FOPkYUGlav88q4x/qH68hXLOyfojjdq3jyEbGk0=",6081815250007587655,5828336997062606887,-9035790785278391732,1944875203445610623>()) {
                                       case 580685048:
                                          this.hS = 10;
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        if (this.b.a().cv()) {
                           switch ((int)com.yiyiaddon.m.b.a<"shgz0l9ef5yiv","3YUtdzix9DK9skmgfnSxsPZZp/v9gu9kvl+K1716rcw=",6144892454263570139,5352535306737772037,4877987237856596232,-4216287242581745346>()) {
                              case 869760469:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        this.cC = true;
                        this.hX = 0;
                        this.ip = this.G.player.tickCount;
                        this.b.a().j(this.b.ak());
                        this.b.a().fp();
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.eS();
      ItemStack var1 = this.k();
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gizzpnk9bmoy","QShPi5FmPdmhWCM4mVIaWK5JplmH4j+clPDRvU0SB0A=",-4520157110729746488,1606460668347313367,8203796823468862190,-737686517157506911>()) {
            case -1212852974:
               this.hP++;
               if (this.hO <= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s617a9pg80apv","6ws2X9Wr6YOW/oDAYRII+oDBXg7sA/tza1qKVTFh7K8=",6712546489976265593,-7643629851235721221,-8211141180149380531,-2857472461745984348>()) {
                     case 954658168:
                        if (this.hP >= 100) {
                           this.b.a().ag();
                           this.b
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"sxjzlvnsh6lvk","lRuXH2PZI9AUsRDEYEXBGfcslMaSt2Qc/iBzf3+AcTC6QvssHdo0JrlI6rdVYRbJzlJyVW0QA1agAdPvJVMM0AtfYsA=",8513506687639911327,1269564326064661757,3525666851823096578,-1069986043727377450>()
                              );
                           if (this.b.g()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1qyv4x3tski3s","/jLUILZO5NGuxLOBHw5K24LBgAcCQbMss+nylgghmIk=",-3131777300646948282,-8237465271953149330,-7682033360474615745,-5333075551456900693>()) {
                                 case 1822003931:
                                    com.yiyiaddon.d.b.e.a(
                                       (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                       false
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s3q5lp9pm0hao3","8F1ETfWrG3IMU5qU/J6MVicTh205DDkHnhJeB2IxMfM=",-407090393952434362,7554074126138578415,-6045898311841542007,-3723331923842459981>()) {
                                       case 1172189859:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1uuybv0jy2vl7","fXklOdSQRU3Pyxr3dN3xAif+6eqhGcL5H4VLRmfE35s=",5702719392645287848,3403200688126714846,1427556237170011061,6337543126359277945>()) {
                           case -1660076115:
                              return;
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
      } else {
         this.hP = 0;
         if (this.ab % 100 == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qxb6cowuy5y4","ly7zxcWd0huEqlHcqWXJvSdXl5Y5kijYcUySsMpZWBo=",4796453037991591920,-334570121037395000,-9190320558345597322,460391064488914821>()) {
               case 537960716:
                  this.b.a().fv();
                  if (this.b.a().cv()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1loznrc5i9fvq","0zDLajoMv5FO3e2JGYpsa4hPzRBFOTWJ69ir6Lo0XDw=",-6006755560587953136,-1897951071983414052,-5123395887286288859,1998731315203067920>()) {
                        case -837322685:
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.bz() != -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2zxahyip77glt","mgguAMfjmnO+Pt8hqnlaHvveo5VrSN4h2e/HwWmdgx0=",-2344826546334886294,8818312040610747905,-7245635242865571278,8022148349380928305>()) {
               case -1004011263:
                  ItemStack var13 = this.l();
                  if (!this.x(var13)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1zgpd67l8a1w7","LNM3uvjuRBp0ReUhmNSmJRs+cqKObYN43k463Nlvtpg=",-8580838131816004549,-5383629284946634163,-6036959426515675192,-7455807154550494745>()) {
                        case -1649750531:
                           this.b.a().ag();
                           this.b.b(this.b(var13) + "");
                           if (this.b.g()) {
                              switch ((int)com.yiyiaddon.m.b.a<"sjyjdo0kxkkz5","WVuYNn2/3DBkvQ6j7WwKtqh+V3s1BXvQzO36VUvEGx8=",-6380298845506133299,3602358560314515921,-1372927087127673502,-4761458112955626383>()) {
                                 case 1828742440:
                                    com.yiyiaddon.d.b.e.a(
                                       (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                       false
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"swi0lzzh2bfzm","BxCL4xQwX7+Y3m+W5FqSZ6d+qswDsj6idHY8h4Vv+ps=",-3574330259071428669,3833885301868627419,-3668463750527988781,-757074947136407006>()) {
                                       case -1664918715:
                                          return;
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

                  this.b.a().fq();
                  this.a(com.yiyiaddon.e.j.d.a.REPAIR);
                  return;
               default:
                  throw null;
            }
         } else {
            FoodData var2 = this.G.player.getFoodData();
            if (var2.getFoodLevel() < 15) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ticd9513w60k","uvX/KICIrat6lirEGx5GeElg8fdFPI46qSS9KIDsg8M=",4894108103488420285,1418154361279106867,-5005624398965273023,-9035163066541581731>()) {
                  case -1151594358:
                     this.b.a().ag();
                     if (this.ch()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3c77e4mbppnue","+ZxNozwh+HfYkae65BRaty9sp3xCP5XK5SDAM2GW5UY=",-7351162807252789047,4089204111473021426,1334150895306100944,2121647581487743331>()) {
                           case 1218861412:
                              this.a(com.yiyiaddon.e.j.d.a.EATING);
                              switch ((int)com.yiyiaddon.m.b.a<"s2p5ard5vk318s","jyAHaiEhEVy0u6ZSSQCXx3C4LjhgL+42zMleGCmJd7E=",-3827651513599131925,-1434620519745653483,-4662533873308395432,-8520292297211504931>()) {
                                 case 1810547230:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        this.a(com.yiyiaddon.e.j.d.a.SUPPLY);
                        switch ((int)com.yiyiaddon.m.b.a<"s3crpk79z98bkg","akTgXBJa4shtdsCbopAdY0OJcSs0xCP922M/oXac3BU=",-6900880478133785983,-8248533276528500733,-7573680887466556529,-3350518821405632790>()) {
                           case 1740238914:
                              return;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            } else if (this.bA() < this.b.bn()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2agwsswdkwg0p","qFrM+oo/6y+xU+RDblY+lwL7jFm8FiUblv/2wF+QyPY=",1478651431854107433,6953219364421243941,5000397102340607424,7343882280362575593>()) {
                  case 1188891126:
                     this.b.a().fk();
                     this.a(com.yiyiaddon.e.j.d.a.SUPPLY);
                     return;
                  default:
                     throw null;
               }
            } else {
               int var3 = this.bB();
               if (var3 >= this.b.bl()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s24riy7681odku","0bDGJggJp2luhP7Fj5K+r2iS1lhd7+HVCDmAESYOY2I=",-3844913304723858302,7195520671728854235,5614243182221889564,8684316712700272076>()) {
                     case 23264128:
                        this.a(com.yiyiaddon.e.j.d.a.UNLOADING);
                        return;
                     default:
                        throw null;
                  }
               } else {
                  if (!this.b.a().isActive()) {
                     switch ((int)com.yiyiaddon.m.b.a<"slo9sov5ld8w3","4U0w9WtgtyzaAYioYFAFcJQ4VPktgiCep0TwyxMcRxk=",3800035241253854754,2293077728657407463,7343714693226653026,-3747867071252898104>()) {
                        case 617013759:
                           if (this.cg()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2r55jz75qevqk","UA2JBKRhhzFlW7yaGx0TGxILNVkWuqoN/nDo5toFvhM=",1700266536797565628,4008622119218554715,934575751327985218,-8655875915239735311>()) {
                                 case -1664961307:
                                    return;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (this.ab > 120) {
                     label738:
                     switch ((int)com.yiyiaddon.m.b.a<"s2a6cim90mj6h8","k4dk4oPnj8YevPaNof9v4kwnVnXTBI3PkfvE/qCvpQI=",1146874125766348291,131444177740910474,-5364137919724756551,-1463521696628626343>()) {
                        case -879438776:
                           if (this.hs > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1dwppyo52vxgs","1+L+URYVXePu//hDAfOGt4es84KMsqCHwzSxILrqSjc=",-2695199505742795024,-7192211927381024178,110093434130519852,5781248068386453287>()) {
                                 case -163530942:
                                    this.hs--;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3queow5c80ro9","+j6ZYqIrWtzADVLoLtlx0PlcKX2rjFMcza4Xj9rloxg=",4023466410729592305,7623824666662955324,-8519702391753523551,-2026252897553130140>()) {
                                       case 1456092135:
                                          break label738;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              if (!this.b.a().isActive()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3l3gyr5b81pv9","fFWnwF3OOQWR0cWU/Q8DZcEDGEJ/JIGNJqLPLFpxZFY=",3366216175860871344,5119872495727176693,-3528357018614739518,8804123105118742567>()) {
                                    case -407284190:
                                       if (this.cJ) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3crnxd12zv76v","B60jSQbPNF19jxoTtnODn8Ph2Myg7o2Ns30isgRhgQI=",3899400215985892147,8187527281625056957,5867819952218189178,-6076874256746926340>()) {
                                             case 1199653157:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1kxng1ng88oo5","F7Y3tEqMB5luFI5M0W3ou6vEW3lJ+UMtU2Q25p739fg=",4563989656758420116,169220255421211938,1999408931434919705,5151994925882144179>()) {
                                                   case 675862828:
                                                      break label738;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          if (!this.b.a().w()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s343bruktfmniz","zrMOPL4eWkDpB5cRHxwsWwpN4INLZHUkfo0NUrgBJVo=",-2942735440237599623,-1795441779756870067,-1227815527471515032,-1000102706527650698>()) {
                                                case 946644268:
                                                   if (!this.b.a().cm()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s12qlsw6v5wtoy","h6k6ZFrFGbdDuGd8+CTrJ+53w/VAxIko06EljiM52bc=",4996817780273834531,-4860418519455400764,-5982139391887907249,967675835148766690>()) {
                                                         case -600484224:
                                                            if (this.b.al().isEmpty()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3qe7812jv0224","RqWi9kTpnVoi9N1AZJjSuKYTF2qNfpljj8frrPKHz/w=",-7794828524770176422,-1526145777091074869,7452760618754695642,5509950914734054131>()) {
                                                                  case 390041739:
                                                                     this.b
                                                                        .b(
                                                                           (String)com.yiyiaddon.m.b.a<"s23punjlfdg0o5","7iTveg98rgjo9mJOyZRoePEX1kpObc4ANtfwMeKJq1jE99UCbB8bfGI+l2vus2rc4c38EZ+lgxadDPBULwiqdPs5hhEHFSe1Kfpe1zURqu14ZAm8sBjJqA==",8872091904141316671,7944444381699731706,2600368340232216608,-105607103976949158>()
                                                                        );
                                                                     if (this.b.g()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s20ftg7hk42zof","cVdyzTxjIn6QXxA1fxkZWzBXYTkhNeFXU9YuOTZV1sA=",-2925341671647347314,296250095602700781,-8945235320980809711,-7197585250894308259>()) {
                                                                           case 298263612:
                                                                              com.yiyiaddon.d.b.e.a(
                                                                                 (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                                                 false
                                                                              );
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s2avq3wuhln6ln","4QJl2OaxtPQvHQxqhZBpsSWRQke1AB0o0WROksbSsT8=",-5287408146431632174,650195275832846273,-8797651944230022850,-5066056993162880207>()) {
                                                                                 case -1801888520:
                                                                                    return;
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

                                                            this.hr++;
                                                            if (this.hr >= 3) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1184ire3zpjvx","IyKDS7pPzuWMbvGjJ0RWzW3oDK5OWx9YCT0FWHylE5E=",393309123223745406,-2705665043822012314,6526796228930109618,-7946055969555915739>()) {
                                                                  case 140068759:
                                                                     this.b
                                                                        .K(
                                                                           (String)com.yiyiaddon.m.b.a<"shfnq1r3upmiq","oajBNACVsbmVe2BFvTAXKYlgXI5awv3EdHNwmW+MsjMBKg58ecEHO3fZpdfJBIV6Na+SiQqlu9Iu2TJ6vrSqP+KVY4Xc3VxUXgT6es7pF7Q=",-2865353018571630944,6073616316085985987,58896256052837774,82798254243365167>()
                                                                        );
                                                                     this.hr = 0;
                                                                     this.hs = 0;
                                                                     this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                                     return;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            this.b.K(this.hr + "");
                                                            this.cC = false;
                                                            this.hS = 40;
                                                            this.hT = 0;
                                                            this.hs = 100;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1zgrsemtbemeu","ftkR7u5NZaKvPqYgBEkfE8dEQrMO/uEYbeKFixDldr0=",2375957385880244508,581098588917663184,5647759326847002545,-7795542804696833980>()) {
                                                               case -1474378967:
                                                                  break label738;
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

                                          this.hr = 0;
                                          switch ((int)com.yiyiaddon.m.b.a<"s39rnkp8p2epss","h+xyGWSteKVk/L9xjWD7/iv4Hmdq6T84z5E5vMVd3Aw=",2677664254696682690,6940166821581577500,-7474009124822780056,4737800737844074007>()) {
                                             case 1151727103:
                                                break label738;
                                             default:
                                                throw null;
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.G.screen != null) {
                     label703:
                     switch ((int)com.yiyiaddon.m.b.a<"s3rcfudqaj2eqw","MS/p4cOa8XL0DBLH+/h+yPeZzhcdyXSs0gSpzyI2Hys=",403288477077547330,-6542220669418594874,6973572059152058297,863451440024085553>()) {
                        case 634514203:
                           this.hY++;
                           switch ((int)com.yiyiaddon.m.b.a<"s3th1kdtxtg1cx","4AZ3qw1vDhkGl+0PmmO2gyMXmo6fCvjNqVjxy+s6/Ro=",6024345950752055107,-2494278157731426351,5705954340571287294,-1702777730416150288>()) {
                              case 2102935347:
                                 break label703;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else if (this.hY > 0) {
                     label715:
                     switch ((int)com.yiyiaddon.m.b.a<"snuzb3ef1l45n","4ektgfRI9oTpTNIcBI6RC4R67Sk9uQU9WIV4HMOeZmI=",-2438510325305797565,-3134111744361584738,-2384126100304074288,4990428791975511635>()) {
                        case -2044124383:
                           boolean var10000;
                           if (this.hY > 20) {
                              label710:
                              switch ((int)com.yiyiaddon.m.b.a<"s3niqpc7srz8o8","hNUmF+R9LPYj+bjKuxi+qOCHtM8DijV1XRfKcxAQRi4=",1240416025135902758,2787339061925722977,-7485696398490885482,-5337120769372269024>()) {
                                 case -1092067625:
                                    var10000 = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3ebjuejcy39lb","hID3YDaKa1ZprpHeiPZSWqOf45neZ7JLe3d4Zh8bnAY=",-1137312641753230069,-1625163131614635857,-3488711449002448808,-6837409454089281306>()) {
                                       case 225158192:
                                          break label710;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s25mh8shunvh5p","pMoXOuPe+2jKTOKFEyObwLGoBB0nYrNxF4hcfNhj11I=",8927979716590791331,-1443506804023101762,-452548995026281551,590734392830594099>()) {
                                 case -931992140:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var4 = var10000;
                           this.hY = 0;
                           if (var4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s27noobysn1wia","zXyZSJnrB7D0L0QhQFz6edb0RA/gwvLqyWUiFl7cTv0=",374434320624159659,-5242435993595259686,-6549256890362159865,1144558841635899210>()) {
                                 case 1505303748:
                                    this.cC = false;
                                    this.hS = 40;
                                    this.hT = 0;
                                    this.hs = 100;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1qk2d7955xlb","uVXnQYroxmI/3YVj+Uws/0u3d7+DPxSrnXvNF5j/UmY=",-8775619339698790850,5679559158839577496,2140799718281861292,7763140550419605010>()) {
                                       case 328884973:
                                          break label715;
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

                  boolean var19;
                  if (this.G.screen != null) {
                     label697:
                     switch ((int)com.yiyiaddon.m.b.a<"s2pd00k44x5kph","QdlEgc4QytgpQvPuyLMZBQM8i+CzJadSptv0nJcwFIU=",182602343368137851,368724571781006269,-2232198104939591408,6187024887930734452>()) {
                        case -1983845132:
                           var19 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s1o753y4odj9s3","nVgAndJq9XOesdYcLDazn2wlrpXuxDkuCY7QhvauRcc=",-7283557586959666665,9146275822312962635,563274210408586467,1488492938864404023>()) {
                              case -1822957955:
                                 break label697;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var19 = false;
                     switch ((int)com.yiyiaddon.m.b.a<"sd09du4o3w0sn","LWcJPAb1aB3aZIqeBPwRerl3XgEbERFN+fGRc4l6qqg=",2242822222083868338,-7012177312342311214,-5963750846380664216,7806071936497449863>()) {
                        case -1375220639:
                           break;
                        default:
                           throw null;
                     }
                  }

                  double var5;
                  boolean var14;
                  label822: {
                     var14 = var19;
                     var5 = Math.sqrt(
                        this.G.player.getDeltaMovement().x * this.G.player.getDeltaMovement().x
                           + this.G.player.getDeltaMovement().z * this.G.player.getDeltaMovement().z
                     );
                     if (!var14) {
                        switch ((int)com.yiyiaddon.m.b.a<"s183819x5y025f","mQwTNfaWLmtRHdm16mBFOrKeEDzap6wwpcEu6YQH3B4=",-2974023728747127975,1102358989375750912,-4131716078482581127,4820626429689328723>()) {
                           case 586674433:
                              if (var5 < 0.05) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1jcyshtubv2ze","WSnXbZKjqinrPitXHMYrN9XLZmkFV2WPo3rteWNko24=",-6559747654126234390,7022579334141867668,4875177362614816807,3282482236102944387>()) {
                                    case -1773572686:
                                       this.hX++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s30ohpvzu4fyif","oKO0hEDB+QKq5J1Sf68alYSTrxLa7BPP5AYqtc9ih8w=",5344651840049895144,-8603438691685797101,2515336169026922677,3729816435846101242>()) {
                                          case -1737725749:
                                             break label822;
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

                     this.hX = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s1dg4bukw2qhy4","1oHSvXzUaGwvQMygLOH3bXqmMFkEcziVd3ZcMt3+X3Y=",-3758587176740334164,6769833090945574635,4434484325559832888,-9211807182164744167>()) {
                        case -1297234720:
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (this.hX > 3600) {
                     switch ((int)com.yiyiaddon.m.b.a<"s13i3nkrkzycwb","MizhaeKOFHpsyDuYiGoYHd8btI36EqKkMHesO4DqgOQ=",-7838577402291350024,4495869268957222580,6453845489075893683,-6759829778727332508>()) {
                        case 699879322:
                           this.b
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s1zllqbapxcwvr","PULpfdUnOzcV34VHYnoCB1ojZnbHx0cLG1Ax3AMDjyP9HpSkHVPXVkH3WhKLHEiwXtLE6vRKVSszl3jCApuJqHzKbpnV6thlz+n36la1",-2007767307769208492,-8916490258592690044,1484135291288717144,-5500113590332765817>()
                              );
                           this.b.a().fm();
                           this.b.a().ag();
                           this.hX = 0;
                           this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     label815: {
                        int var7 = this.G.player.tickCount - com.yiyiaddon.e.j.c.a.a().bv();
                        if (!var14) {
                           switch ((int)com.yiyiaddon.m.b.a<"sszitf69lc3pn","Ag+9ORBYcvkT/tu678Zx24l3mniX2vcfFw+2FCuhGHU=",-1417858400229520159,-9222782083752279894,3411301198668782302,-6653561366272737390>()) {
                              case -1226577537:
                                 if (this.G.player.tickCount - this.ip > 600) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s14vqpqiopse3e","BO34cUyUREhqKkwJyA4wMlae/wslxwHV/KekYgU9ZQ8=",1079693039350544427,3541830439740459208,933516638359125779,6114343735535299938>()) {
                                       case -520170006:
                                          if (var7 > 100) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s15fe6vji2wsay","ad3ccJxlWbcahUHuyjCajaVPE0pneHyuXUiHqM7AGSQ=",-5177351900319272977,3618995475774691937,404500339385101059,5443505069000973427>()) {
                                                case 400023285:
                                                   if (this.b.a().w()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1z0lec9vjhgy6","ukf+boEnRPGm1/cf/2LzGcWEb4yxdNiiRQerAKWF0UQ=",-5681659313470300349,4867599698401986477,-5162970052347496466,-3624515853115187998>()) {
                                                         case -1366247127:
                                                            if (var5 < 0.05) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1jma79v8buu8y","1RL3Mh9nk7jYba81p3b/L9t8z2iDzGnXnNE/L1TDLks=",-5530222722238920978,1760201020452887316,7435540658923279162,4888389870702387010>()) {
                                                                  case 393146586:
                                                                     if (!com.yiyiaddon.e.j.c.a.a().isActive()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sv6t5pra5wzdz","jcS9CIixLqxHQOtH5TyFzfMy259AV0nQtrhFaM+dlRQ=",-1406882408184461955,1005306822864975805,-5273476226861641416,-5897023456710820902>()) {
                                                                           case -1580367748:
                                                                              var19 = true;
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s3kw73exrc3whe","8a6Ln9PS6l7ZuZN3Q62yNx+spyR/KtK/c0VHokYGAb0=",6959716407104403692,7641268024431077776,2718779045869962244,-3471328144222613218>()) {
                                                                                 case -1650262727:
                                                                                    break label815;
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
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var19 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s38geedxi1klqx","s5u2L1g4vaZlWR0gH94QAqySOFp25zaFrraIQJ3FSPE=",-2240284507634630614,-160309350148727141,-4818810339161452552,-5182860343630072082>()) {
                           case 1457253197:
                              break;
                           default:
                              throw null;
                        }
                     }

                     boolean var8 = var19;
                     int var10001;
                     if (var8) {
                        label668:
                        switch ((int)com.yiyiaddon.m.b.a<"s21c0naf037tyb","oXrf4XEm7htk2WGW6EaRRZlHF/rdKqZZSlCl74LT0zI=",140953641405833039,-195204523590108041,8349346480031440000,8310463008636183591>()) {
                           case -404591627:
                              var10001 = this.ik + 1;
                              switch ((int)com.yiyiaddon.m.b.a<"scec1xmropjws","qRuzrhYG/Bb6DUnqp+/cj88NUbM29NEOEbVwGOY/ntQ=",-7327174008623226011,1038868962029066849,-4356062972993116397,-8076349672929974708>()) {
                                 case -1997089743:
                                    break label668;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s3b4l7eom4oyfh","KffDM2YNRiZyyBuhWOmk++C8/rwoqxXv5blUS3FTQ2A=",-5815254492576683484,-4668761918564271098,-7353461093112037709,4158807635821730574>()) {
                           case 2133813914:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.ik = var10001;
                     if (!var8) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1eykgy1c3a3mj","4y+l3SHoxN915StXTLbsWemBadrz5X+TNKYQo8g394M=",-7145317379459223956,-5820555499298165308,-4882900049851913587,9038731910001369751>()) {
                           case -1459686580:
                              if (var5 >= 0.05) {
                                 label663:
                                 switch ((int)com.yiyiaddon.m.b.a<"s335zwedscviso","4YlBQq3TZtw+BYfvRd++h+pZbC2ZbW788S24m1bWwJs=",708411616957461255,1576269490060858289,-2911451449759357425,-4163023483682427327>()) {
                                    case 491285652:
                                       this.il = 0;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1hhyk3ggfui6e","Z+l8kap68/J3fQWa9iZs/ubeSeTO+apdPMTCM7LNXP4=",-8927628240782171479,3014869323198305173,-4688611878985692150,-1101274483125035497>()) {
                                          case 943911036:
                                             break label663;
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

                     if (this.ik > 300) {
                        switch ((int)com.yiyiaddon.m.b.a<"slilo5y25ei3b","T1fjkQcSKqUospB50EGtH+XHrFmCwa17GnhdYyhnx6g=",-5822188114551607825,5866387261462148362,687296711369645495,-6242054176015193292>()) {
                           case 1802212837:
                              this.ik = 0;
                              this.il++;
                              this.b.a().fm();
                              this.b.a().ag();
                              if (this.il >= 2) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3qa3fpvmicdzp","bcRmwhf0hylmTegiLuXQ0le/R8Sxvmz4StfHVkIhQ1M=",2164695700331554327,-7728743169920308343,-3055616114640618889,-2598073333486036497>()) {
                                    case -1934210466:
                                       this.il = 0;
                                       this.b
                                          .b(
                                             (String)com.yiyiaddon.m.b.a<"s28lgw8m2ttgic","/yALnbOFkwhH1EyiHxwj6GTto0D3p247it9/Uqvyu7Tyg4UXRkOwL459WLDF6va0eNR2Answ0rTqkQM18D1NCTECRHGeIYFhY3yn/puj11JW2g==",-623371865344573591,-1646296659019530157,7216091151510818457,6753343350441127744>()
                                          );
                                       this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                       switch ((int)com.yiyiaddon.m.b.a<"s29go6stuesm8y","YwKcGeAsJKX0aHz/FnXuEcMfrmYUzBUHlQtHQsbk/Uw=",7837315048925285357,-4923577382633200110,-1119333399984871120,5312500931186991079>()) {
                                          case 1552525019:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 this.b.ez();
                                 this.b
                                    .K(
                                       (String)com.yiyiaddon.m.b.a<"s2q4xjep2fzelz","j6qnSbk5F61Bri0yjombY7zG4MeVVcjp36mrnVa41H6ejvEaRgAuasl9DtuQ6wjJEYArtdOSAPEFQJPYOcPxn4m+qZH0mUJLjwFX4rRy",750719128889755144,810420138110695113,5286577459604852536,1422531923366362401>()
                                    );
                                 this.ip = this.G.player.tickCount;
                                 this.b.a().j(this.b.ak());
                                 switch ((int)com.yiyiaddon.m.b.a<"s3o9hk81hf0qaq","7XGwfmBfiE+KcZRJwTi+N0IxNIqqraqzkNSw9CX7nCM=",-1681743422320646912,5214575998519420460,-8007677362159893546,1857018625616423368>()) {
                                    case -397096778:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (!var14) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ln3e4nxcn60l","1JJgNGpd30OUUvCWxyhxQTyZKXBiGf2ooNzMUeBK5vY=",2544888147083552989,-2101311001008394381,-5590263485474355744,-4677793775723463859>()) {
                              case -439727647:
                                 if (this.ab % 200 == 0) {
                                    label656:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2bdot06cddhc0","UKOQVOKR2/NVrSIRHuQFHMJdY1ruzxdHSGoDn5OyQn8=",-5311070663718735147,2112393298500348137,-325695910148541234,-2783340560885326468>()) {
                                       case -72296148:
                                          BlockPos var9;
                                          label792: {
                                             var9 = this.G.player.blockPosition();
                                             if (!this.r.equals(BlockPos.ZERO)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1hxw192ksoohg","n4O4GZmRfzwKKd9lvZEdsMUHKAjvjg4dfA73NXb8/5A=",2620311919653755633,8871190068051237893,5549110910472651701,2191567024615620774>()) {
                                                   case -1729796620:
                                                      if (var9.distSqr(this.r) < 4.0) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s28pbxpl3yk0hj","Da+dk2jOT94DRBVGAjFBEgLPW4PScucHYEz7BAmCDOE=",-1634644596146272636,6585797156801774612,6836070601460412402,-2318906356491938384>()) {
                                                            case -956422897:
                                                               this.ii += 200;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s34h8qpyy24pao","EBhzO7iFZcCSPRjpzY773h6kB4/lQ0gyLBdw5FhB+Jo=",-3288403987550110155,-168063675791092811,-955997201076329506,-1219951339410220582>()) {
                                                                  case -987441808:
                                                                     break label792;
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

                                             this.ii = 0;
                                             this.ij = 0;
                                             switch ((int)com.yiyiaddon.m.b.a<"s210s6vbcp3yka","oN4K2gU1yfp+kctdWyxuJo6uTb6nKeSlRPFG+yVbFZE=",-796544231443943836,6608927068110901427,1681010314514874033,6410677462754663673>()) {
                                                case -619586515:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          this.r = var9;
                                          switch ((int)com.yiyiaddon.m.b.a<"s39k7jb62wq9me","OODN/rIq84Bki38JTTs3k1+h3ItMFdFBBjw8gL5vLB8=",6969302808921378331,-5000101026488229383,830053067346544649,-3700295355455773512>()) {
                                             case 182943640:
                                                break label656;
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

                        this.a(var5);
                        if (this.ii > 3600) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2zeepesd2rt67","P1M6yvme+wuWhG9yDTmoupqd6drkbFYdvXtZxT3kU7M=",-2905270318563204451,-2991305488825420420,6572314976241969912,-7496365721856182873>()) {
                              case -207702171:
                                 this.b.a().fm();
                                 this.b.a().ag();
                                 this.ii = 0;
                                 this.ij++;
                                 if (this.ij >= 2) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sc47zgkqappic","fmc7+LpRxH+f1ceVPwMYRzYYThVO+kO2EwBfRH7W4cw=",7782429341289120425,-6037364893505814385,7497290884337141432,3948925839431595633>()) {
                                       case -1427029511:
                                          this.ij = 0;
                                          this.b
                                             .b(
                                                (String)com.yiyiaddon.m.b.a<"s2urq9yc6iykdt","VwolABtwsUKuGP+uEWOFNgL24c2bvnM+/LDQlXsvjK/VNIWd/N3BYaynmnU0fGIs6F0BP7BRT4Yjoj5gFpDu6X6FiJ9uO6eWwrO37I5n2Ag=",-1329753152823492028,-3223793878263663934,3036931064687027413,-7014795853189231529>()
                                             );
                                          this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3cmlexnckdkv8","styvTYuz4v4jT/hQ7d1ZixFozuS9+VqMyxDCWv+N3Lg=",1033109099055783515,-8892811854531226533,1408538492456849109,-4159407556056725361>()) {
                                             case 1551449883:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    this.b
                                       .K(
                                          (String)com.yiyiaddon.m.b.a<"s3p56ykhnz04ol","RixWuo63aDxdGCJPsrWpQmW5kZ+wGymn1vHmxFT4u/wbBeUHeHtgwu7TYPkwzENFhZR2ijLhWyRvMeY/gbuX65H7e23Gzh3L93I=",-2191855982941932478,8507079320307265196,-4788872788574167240,7868374456868971416>()
                                       );
                                    this.b.a().j(this.b.ak());
                                    switch ((int)com.yiyiaddon.m.b.a<"sa1z35hlf4j98","yaBTkphmJXWIZuzehuriJCHIQVlWvbqToDs91doP75E=",-8038021563959388190,-3502115516702641395,715391019150014534,948892339053835183>()) {
                                       case 1010531291:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (this.G.player.isInWater()) {
                              label638:
                              switch ((int)com.yiyiaddon.m.b.a<"s2we3gqvkvgyjg","Mi3Fe3Ra6BnrKQ6QF/t3B1GwEMHHEEhP769vUnMJNpI=",7842785803734958115,2844678742187703471,-4289422428230134911,7890233251176993879>()) {
                                 case -381614758:
                                    if (this.ib < 60) {
                                       label636:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2munh7c68ad0k","2NofbvSkB8C72kJ/m3AHDWH3vNF65IGFUs3Ers7JNkQ=",5335507497699550464,-6569907047253516552,-6837056082482340978,-5299398414315156321>()) {
                                          case 79353730:
                                             if (this.ia <= 200) {
                                                break label638;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s64vevwnosf09","RjFAYl1OLz5WSO0NF0+JatEyPKIvoLWu//d63zKPcBY=",-101285763698412610,-8154654068667067995,-507276051809514178,6365744007153118364>()) {
                                                case 764900376:
                                                   break label636;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (!this.cE) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sp0vnlmhdjohd","cLLKKgPZ9I/d5EmVVNGlvNhm6vypqnLZu+tjt43Bzgs=",6359207774829488635,-7483636023374914935,-752538715659192890,8841943694735427891>()) {
                                          case -1332603589:
                                             if (!this.a.isActive()) {
                                                if (this.id >= 3) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1o3kuausr397x","lOy0ZfcBDmk/suCRoFoOWOixC+C8F8ijQ6emPCRXqp8=",9104928073412309243,7794606754939078083,-2234870623274173269,7315396716538353931>()) {
                                                      case 926764062:
                                                         this.b.a().fm();
                                                         this.b.a().ag();
                                                         this.ia = 0;
                                                         this.ib = 0;
                                                         this.id = 0;
                                                         this.b
                                                            .b(
                                                               (String)com.yiyiaddon.m.b.a<"s2vix2tfqm4u80","Ig8D4vYmToIfmzhlDL4DxfdX6DffiQr3s8nN45m910yj1tVqSrS0gTZOcUcWbpDRMfekIS16uzId1Jfd6IQ3DSnYcBWyQg1qGkkDkhHC/qrQqkkVlak=",-1491341083320354852,-5729348761052956507,-2158572993994273340,6426588837543001205>()
                                                            );
                                                         this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                if (!this.cF) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1cunmxgmyktfr","d8aBkLs4vMARh6J/nO3VhV6DXoUtW/gU9XPbwnNmz04=",-9200444451874455965,2585567699648579394,-43708373829121828,4339985027183905588>()) {
                                                      case -1389990198:
                                                         this.cF = true;
                                                         this.ia = 0;
                                                         this.ib = 0;
                                                         this.b.a().fm();
                                                         this.b.a().ag();
                                                         if (this.a.ci()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s76wg7ueyn3un","kulOUxhapghXkS9RxQK5wEA5gNlF5atZBLikJrrY5z4=",-3271985029264612919,1448321926817339911,-8106229719661535119,1414451721108859807>()) {
                                                               case 937398344:
                                                                  this.b
                                                                     .K(
                                                                        (String)com.yiyiaddon.m.b.a<"s2vg7sy63krjlz","S5ki0RJYQOfZOhIt03wa2j7V+Un10l1uUtg/CmK+Gqv7P3oTaGmGv0Nt+sGg3rcLBE7n2jXY+MHpdV8C6muXsYhIcl9mmw==",4394671765585983281,1922740223548658358,4453637084686263171,-6540303170283725003>()
                                                                     );
                                                                  return;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         if (!this.cf()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2f59qapdzaiqh","KArWW7Mh7ccmwHvpxzqk3QKYbUSpgMe5AcgydhzKpY8=",-1961040966908272462,4951676947161276120,-4989237724047613741,2794571061420812998>()) {
                                                               case -1751510269:
                                                                  this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sz74jnlapbqed","xIhUey8Xzozzt2v8LJl/sYjPLdZcY5WpAc+Uv/DR0B8=",8764944380536955165,-6974861197445199187,5803533546235472825,3227104344163262470>()) {
                                                                     case -275956203:
                                                                        return;
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

                                                this.ia = 0;
                                                this.ib = 0;
                                                if (!this.cf()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3agfkrf01ofii","vR3T9+J12/gfwxZv0ImVmKjE1BQCNP+eq2dv+lJ+Ht0=",7361919897999503435,609872294020781821,-3334509963021796403,763195791425675507>()) {
                                                      case -483023921:
                                                         this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3esbgt8vm8cnm","ktoTpiDF1URZICZr1ufHr0e+VPadfOjlfDE42rutEIY=",26975794105208461,7277117726079558493,-1271912772241688250,6655233908924207253>()) {
                                                            case 1093920109:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                return;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1d7ar0djl9bxf","x7/I+f0VUkAMAG1sNPs+a3T2TGqIbnAj5IhIoxoMqoc=",3425449571159065358,-1935381006190935048,5105615838597381884,-3997650330945047510>()) {
                                                case -1685676288:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3twoi9fmfzc2u","rJMfy0EjECrO2hoo10thW6gq+SaIK7NRrCi6D4DVSfE=",-9206593946859711167,7470595026952634069,4931700163205797118,1144972176698290377>()) {
                                                      case -110316158:
                                                         break label638;
                                                      default:
                                                         throw null;
                                                   }
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
                           } else {
                              label776: {
                                 this.ia = 0;
                                 this.ib = 0;
                                 this.q = null;
                                 if (!this.cE) {
                                    label644:
                                    switch ((int)com.yiyiaddon.m.b.a<"s27a98xmkblqmx","qn+yz4bLv44HTTWxy6MNwKxD8jDqN/nZHk7h4DkQS4I=",-7459362992590314345,-1056227053644656820,7534719245950138471,6600418381397103983>()) {
                                       case 1498295447:
                                          if (!this.a.isActive()) {
                                             var19 = false;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2vdsccvvcltbm","06tO7zmyQsooD0u7cUdsZEzm4SoSPbcBLzrEKHN5Pz4=",1561544345299433214,4561396028977003736,5196688969980640455,-4377659784603969661>()) {
                                                case 1361328206:
                                                   break label776;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s1a5wdqhvyn4sh","4nh1jH5W4+Far9YoKaJqRNA8QyUIBxUWXGnq9JmTPOY=",-6695115758109886079,-5899539091390203560,-7463072854413121512,6886658244152207398>()) {
                                             case 1083492177:
                                                break label644;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var19 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2rppqudezmwt5","EWv2tOUzdAQlpU4zyX4yW6LThMOUlcbp6AocmYGw3y4=",-3410651675177547398,-2436840404111858135,4141986534842279566,-7322509091163500707>()) {
                                    case -1604117661:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              boolean var15 = var19;
                              this.cE = false;
                              this.a.f();
                              this.cF = false;
                              if (var15) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2z7ngh3xbfbtf","LPpiLFdzemAj7U+Va36a+QzgxOhKnGUHnlvdCZirEBQ=",3765538316125113375,-1883383867795577047,2616684647424941861,9183457656000062197>()) {
                                    case 1240026424:
                                       this.b.a().ag();
                                       this.b
                                          .K(
                                             (String)com.yiyiaddon.m.b.a<"s3t7oe4xr830zk","72CP9LzQJCf7k9RrtfovCL+JPmBOpeQ586gFHwIshuzYMAa+EOdY4ztpX6nwgS+pxj2Nfn4poJUTEIPEuHHT2g==",5523671786443156407,5019924024586675985,8355052874158598723,-4002236800687253874>()
                                          );
                                       this.b.a().j(this.b.ak());
                                       return;
                                    default:
                                       throw null;
                                 }
                              }
                           }

                           if (this.a.isActive()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1yunfvo1fg91g","V4M+cL1n2Ks33uCP23NeQx2xWjLqGqDF1P36icyGltQ=",-9042034886681729203,5266801421087431574,1750310895012017929,8067868102598069611>()) {
                                 case 457502362:
                                    BlockPos var18 = this.a.l();
                                    if (var18 != null) {
                                       label534:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3g81xhhl6leal","7+qkmlzEwCFr9MsMGSQd8kROlKpzLHRflITPSJdLhWg=",-9002139534671407277,3173958745292334846,-1153071089458701888,-8771550273192955275>()) {
                                          case -145345982:
                                             this.o(var18);
                                             switch ((int)com.yiyiaddon.m.b.a<"s4xejh46x7bjl","XFydnGZ+Nz0XHMwRdczIP5Z0O/X6U/DmGHsn/TbCasU=",-3871214406917871188,-6321293681439317,-7098984318687062415,9054359756845847131>()) {
                                                case 1992770017:
                                                   break label534;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (this.a.cj()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1phvzzs2o2bqd","fJNlDz/yca3wM8x7YMgNosfH7VAfeUZLb3lYFWA8ZUQ=",-9022804712086351872,3588346692873671598,3552197797128631695,-8111766388755053544>()) {
                                          case 956374814:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (!this.cf()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3u4mp4qny75tf","o5GAIpRHr5Z/7jNtKPlDLOWDiBH/hJ8Q6AOBfzMNnQg=",-5256072772814389375,-636057500271751285,-8361960288784154192,1921463001454035179>()) {
                                             case 1855437874:
                                                this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2norzgm9hem96","js0gwc3mTqEX9LGStZjJBiSgn1+2Z3/VGeYS+Wv6dEo=",9189377650144542179,-8039490456981515806,1273423134861981722,6759542968159619919>()) {
                                                   case 1055355673:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       return;
                                    }
                                 default:
                                    throw null;
                              }
                           } else if (this.cE) {
                              switch ((int)com.yiyiaddon.m.b.a<"s147h46spqhxq0","1Wex0qHlvbWa3aiGfNPYMYgmG7YnKWdX0InhZZG7sA8=",-4598747758425634890,2617087247545518082,-712502246825369403,-1362644164104198322>()) {
                                 case 492197878:
                                    label754: {
                                       this.ic++;
                                       if (this.p != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sslo6lv28662e","UBVOdBslXvlE1nktONaoPSjfKlmzCHcvhiD0uOXkgjU=",-4956631588477850044,-8280812373942651929,-2745876322197627220,-8373771841865393215>()) {
                                             case -2101043440:
                                                if (a(this.G.player.blockPosition(), this.p) <= 1.0) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s39k4uoeb3rv8v","2A8hQiSlxf2hU3GwhSxPr5fuJg0J7aRltiddJnQwP0Q=",2637259444260084425,-7655374757445904264,7744719723563349702,-3411211275975685471>()) {
                                                      case 413208277:
                                                         var19 = true;
                                                         switch ((int)com.yiyiaddon.m.b.a<"scalcve1ozrmj","zLyLjV4MC4MLQrbmD263blwDL6F45M5VzbBgBBMNROc=",6804684609938788237,8842290858351903114,-6552378474877034473,3070946214462080997>()) {
                                                            case -1733116944:
                                                               break label754;
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

                                       var19 = false;
                                       switch ((int)com.yiyiaddon.m.b.a<"swiegdgnmwteh","smMo/ygI42awwfWb5MNjk22Yqs4dR9ZLedIyjWGw1Ck=",2902920331355271507,4126105067022243041,-3915317933489293427,-30643053357884107>()) {
                                          case -285327633:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    boolean var17 = var19;
                                    if (this.G.player.isInWater()) {
                                       label545:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1lj4qnufauou7","Mdy4g86DwhQ4idMwH5galCdm4k8fhWZ3ECz8clpNtlI=",-736994194569711529,-1444369211162818350,114796944386290446,6949682046823041775>()) {
                                          case 1776753501:
                                             if (!var17) {
                                                if (this.ic > 400) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sbrq027iltozs","Ok44sarJfPVXL+DJZC4ESYdW0axbd+vDsxFQun5dvIg=",4104401505328796784,4221705162481763594,-6396841374769030532,-6090522925191494710>()) {
                                                      case -905425695:
                                                         this.cE = false;
                                                         this.ic = 0;
                                                         this.p = null;
                                                         this.b.a().ag();
                                                         this.b
                                                            .b(
                                                               (String)com.yiyiaddon.m.b.a<"s1fvdekqot51n3","2QTW8ZPpJVRsgv7T3RvW7Ex4Iv1jMGde13NqGQcRQWHnuy5OkuJi0Odm071TIFLU3Q7HxjTvQw1KR51EZjTKdSsG/L4rcg==",-8063618196781636275,8265375581418033451,2156795186680004884,-8888983539037701635>()
                                                            );
                                                         this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                return;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2rooym5n7i8r7","1Y0biPVlOSV0wityDoP9WbQPWaNv1oMEEp2ty85+0+I=",730170278848862746,-1062404021148511378,3994171413316387143,-6968751165424744322>()) {
                                                case -1273801667:
                                                   break label545;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.cE = false;
                                    this.ic = 0;
                                    this.p = null;
                                    this.b.a().ag();
                                    this.b
                                       .K(
                                          (String)com.yiyiaddon.m.b.a<"s3t7oe4xr830zk","72CP9LzQJCf7k9RrtfovCL+JPmBOpeQ586gFHwIshuzYMAa+EOdY4ztpX6nwgS+pxj2Nfn4poJUTEIPEuHHT2g==",5523671786443156407,5019924024586675985,8355052874158598723,-4002236800687253874>()
                                       );
                                    this.b.a().j(this.b.ak());
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              if (this.iC > 0) {
                                 label621:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1l673nfpbaeta","nOyy47ZhLxgRjjh4pNtTeEHYndWwT4V4lhSzhRHGXMQ=",-7524297727288228899,141084843640282398,6503101951680350641,-5806542191116300126>()) {
                                    case 995517075:
                                       this.iC--;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1qyj3i6rnotde","lEqgAiNXXmzg1F5hR1KwvtMIQwRt15VS6ALe5yg5n9M=",-6058330190777098432,-3100034031540563149,-6955454470380283994,1443379477135243310>()) {
                                          case -143372261:
                                             break label621;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.iO > 0) {
                                 label618:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3natq4ypn2d2j","sKor9y9vwxUigDmG4Vg75pWOKvGbM6trvUy+sZ4neus=",7470023141630269741,4066780503672272372,6348415848662755191,4936797464528997227>()) {
                                    case -2068687033:
                                       this.iO--;
                                       switch ((int)com.yiyiaddon.m.b.a<"s4o2e3r325odu","qS1Kvl8G5dh7V6GPaA2y1dsmeCHy5E882DpooWWOuj0=",154059803463183432,-8071545137831746067,2285523631908797808,5181439872366490978>()) {
                                          case 491526126:
                                             break label618;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.cJ) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s34oyz3svufxcv","nFZTgynsmOF9P7tvbSr0ev13rGxcctYKVPHIuPDPAwY=",-8217722995671731601,-8922546068933611395,3776637323870487666,-9129572916808617700>()) {
                                    case -671623130:
                                       this.eP();
                                       return;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 int var16;
                                 label762: {
                                    var16 = Math.max(1, this.b.a().gq);
                                    if (!this.G.player.isInLava()) {
                                       label613:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2a8ozmdqhvngz","yvCuF5zYYjPp26nkqlZ8KQWjEhAyhS0zYEdGzqJFSF4=",-3982880227265068065,5672295236207944044,-3310324043185446648,-7909239878931951098>()) {
                                          case 774396297:
                                             if (this.G.player.getRemainingFireTicks() <= 0) {
                                                var19 = false;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2pm5x2zwdg2go","7ldMevUSDCpUOFWIlewjPkUk6rQE9iBKTG+D0x5bmY8=",-3997195901895730289,6292215444152491810,6385437642542707316,-5724179545184240303>()) {
                                                   case -1871731557:
                                                      break label762;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2ou9d5sfy5s6a","Y14H93R9yBIu38CkT196uj9jgQs1NhmhPU91r0updnQ=",1861837134028014461,5981110064213965750,3918867060771826123,6086950203678421970>()) {
                                                case -1174509151:
                                                   break label613;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var19 = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1o3pvly2buksc","hpT8AbJfzXcZ11J6Chs3BJHrAWA3sa2/9OPi6dA/i7E=",-5795258503381047252,-3146764701423369148,468418929470663544,-4902138244730749681>()) {
                                       case 1462563177:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 boolean var10 = var19;
                                 if (!this.cH) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s24ghh776wykp3","P0iC4osMyzsN1NOwQ00hkE30UYuWaZS/ZSrBfRX9vcM=",-7726645097100318829,-357174956128953427,-1783183801412898916,-7103521499537654492>()) {
                                       case -1213385968:
                                          if (var10) {
                                             label602:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3rtziijmrun9p","qDkD66Rv5LvzKNT5qYwxOD3Vg/gCKdRB+e6t1u8ueaE=",6595951937590890288,-3000714689857759334,3576650471492612395,-2043551992177189994>()) {
                                                case -349317924:
                                                   this.b.a().ag();
                                                   BlockPos var11 = this.a(var16);
                                                   if (var11 == null) {
                                                      this.b
                                                         .b(
                                                            (String)com.yiyiaddon.m.b.a<"s2jdavov4nlqjh","gTOeWg0dhqytB64DYlF0Sbo6A3ocdw2+Ko+YZSCcgwHgr7FEzcGJgLrBK/vhe/jcRiRCK7zmNaoPf1AMkacY1axtljPRFQ==",-4757551199533134430,-2195642229351796190,630191119810092483,8348237707050015821>()
                                                         );
                                                      this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                      return;
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s3rm1dmdav0tzv","2Y0B+dGVt6oE9C+rAwaU8yyHwR9uXTI1kWj7vLccOFM=",3322722930663139682,-4410396310391887632,-1872204724589110585,2127685426297730644>()) {
                                                      case 1711186129:
                                                         IBaritone var12 = this.b.a().c();
                                                         if (var12 == null) {
                                                            this.b
                                                               .b(
                                                                  (String)com.yiyiaddon.m.b.a<"s2khm0p83sl1kg","ogoDGQUBLcUAYS58rHK5KfUZACcrcZFv6yw4qxC5Audb5P6QoLF+r9xBl8DK9ww4/jf60EAu3AwYatk3zkRfm0Fl4MmK6dTpfrwtMWidm+exXQ==",1337438667582358973,5950557367359150619,2705813903569652661,-8826811519695227032>()
                                                               );
                                                            this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                            return;
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"sqy9rht6s8xm8","WAnKOHtxJ2147I67fnlC3MaHI+AmEPiCAHlJSVKfuRM=",-9179754965098188508,6281618549106021789,-4189003260927270757,-4423500690264799092>()) {
                                                            case -1335573931:
                                                               var12.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(var11));
                                                               this.cH = true;
                                                               this.iB = 0;
                                                               this.b
                                                                  .K(
                                                                     (String)com.yiyiaddon.m.b.a<"s328a9z07624vd","p+7bbq1yLX4YUCE4tTA7F2XM5AmAoiygAkGxXG5bndNXvSsCWihrq4z2wPquAsiR72YaYSH2vFXzSKyuez2Qg55g0gDW6qWls6x3Dg==",2022076747690669850,5718041439796081279,7453554974050106666,4287634690303552926>()
                                                                  );
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2fb8sdgc9rnsr","i1l4Tg14frtGWvq5/2yCKa9FHdcVv3Y1Nu8G6a6epf4=",-8977692818432381805,1926850820855977810,1558429669154497048,4939497533736736516>()) {
                                                                  case -832124638:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1mxhk2307vqt6","9CeH2FE2SqbLW5ANF4bV2dBRD4YsFQc8h3X7A5jZ7bk=",-168773111618084978,-8894819691377894347,-1870849624371699014,6279922669836461495>()) {
                                                                        case 1274782822:
                                                                           break label602;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
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

                                 if (this.cH) {
                                    switch ((int)com.yiyiaddon.m.b.a<"su85aah2985i","BVPreo+LtpwR5fN5K/Na55aYqPYciZgAiAzoY3E28Sg=",-6625534735625191517,7563220245696217940,8778860341049513302,5577315983527796716>()) {
                                       case 1742315949:
                                          this.iB++;
                                          if (!this.k(1)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sxeuzb7f6kif","XYf2ff3lcflOR62YJ3WeNAZHlbXv95Vd6DXZB+u0hGg=",-2641962879701557402,-6792356654531408901,4189434286181675723,-2470907544255393683>()) {
                                                case 881681762:
                                                   if (!this.G.player.isInLava()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1edx6smxobojo","qF//umo3Tf5upnk0KQt7qCtkqm0Y1/f+8CdOqJizbtU=",3891102806511159197,-4006926615463069243,9039157151046329702,-2345048677662767838>()) {
                                                         case -1733734649:
                                                            if (this.G.player.getRemainingFireTicks() <= 0) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"sr6ievxrwvobs","HwhB+j+Hxpz3qXbVVzJafj65ov+wdLmoWtWpnkMikug=",-4512550985082414009,-150692390524781291,3136865081631143245,2110274344181507065>()) {
                                                                  case -574984028:
                                                                     this.cH = false;
                                                                     this.iB = 0;
                                                                     this.iC = 100;
                                                                     this.b.a().ag();
                                                                     this.b
                                                                        .K(
                                                                           (String)com.yiyiaddon.m.b.a<"s1vmd5fl28wx9h","bwBsz8pPsdgZTFpEnUfXIGiOo+Ywxu/8OeyVTqBx3rp/R6Gs78tgzaCuI5g8mQkRPbXWXwkek6YK3TuGAj+TpQ==",-4088510115899089205,2862455945997476039,-260233495840147301,-7707022140297702089>()
                                                                        );
                                                                     this.b.a().j(this.b.ak());
                                                                     return;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.iB > 400) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s30u63p9vh1hsy","NzwsydMmuVl4A+CVHrLhrLPAHByI2tVbqt65fda9Hrw=",-8936176357891216230,2165032614839648331,-4286379305042564966,3196698570633425525>()) {
                                                case -1915188540:
                                                   this.cH = false;
                                                   this.iB = 0;
                                                   this.b.a().ag();
                                                   this.b
                                                      .b(
                                                         (String)com.yiyiaddon.m.b.a<"s10c6znyplcp4j","pMPjF2A6Iuja+hkodUHU/GPsHXUGfdyYEh4K1mXPHcQK0Ewr776VlseykMIJmRmgh86txRd8DPKTyIbNzZPCcYKff5lfcw==",-7711826060515487959,-5722629444890572346,-8296581894984395968,-5900999024764056146>()
                                                      );
                                                   this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return;
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    if (!this.cH) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1deklm0qopkfx","8mFxXbWvKMocRh69zxxdnFBbiMqBUkrBVQHm5jKN7JQ=",3051206857581020763,1522098729762478919,-2016138976371565382,9044827587554580779>()) {
                                          case 1296940537:
                                             if (this.iO <= 0) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1csme7dm37wl","DEssWcQDbmKnDo420w6Cx/yCS+xYQhssqvf57pPzz2U=",1068536120403732863,-762422843409395886,-537248615202325563,-4303218089924690059>()) {
                                                   case -1098460254:
                                                      if (this.ab % 10 == 0) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s13rx2mhzf8ecv","9Im9FvGo+AqjvpCx1kc2fJGI/ybYnM+cGdTsKpg01Uo=",5319875102220247930,6668658159379464047,-8257146575509158801,-4149627863244211863>()) {
                                                            case 1064511260:
                                                               if (!this.b.a().isActive()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3oyowlt3g10fn","HFTVrRfdHM6h1vN8Ino9/cN2+2l22ndtVXL/O8BTQGw=",7969617023015014443,2924270947951858904,1753780846524732987,1650150890210049629>()) {
                                                                     case -1730064865:
                                                                        if (!com.yiyiaddon.e.j.c.a.a().isActive()) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s22x5zxj4cz569","ckm7Oefs+himlKcDx1Ne0BoKmSDoe0750cunyO2W6EM=",-2775240522052767308,-6086699625001293444,-3205342452014657378,3089144512906746418>()) {
                                                                              case -699550123:
                                                                                 if (this.k() != null) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s1vxk2li6zct2j","C0n7DspeVV1qMpSBvqY9myaVpr34mHrU+nLmm4IDnOM=",-5129807209327809005,670086932558864005,-6307011033475868688,-6682486469657675892>()) {
                                                                                       case -884628102:
                                                                                          this.b.a().ag();
                                                                                          this.cJ = true;
                                                                                          this.iE = 0;
                                                                                          this.iF = 0;
                                                                                          this.iG = 0;
                                                                                          this.iH = 0;
                                                                                          this.D.clear();
                                                                                          this.b
                                                                                             .K(
                                                                                                (String)com.yiyiaddon.m.b.a<"s1c8n935kxg9uu","3Gj+XN9wR6jbjXOfioQoW1DEpomOzKMp8CC6+YEPgD6JmHFOl4W4vbfNYjpeFTdlRtaD+oTTVgYkkG6/",7991058503479724009,1832603867879285875,-1709504094252582592,-487038486711152412>()
                                                                                             );
                                                                                          return;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }
                                                                                 break;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }
                                                                        break;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (this.ab > 6000) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3ihr3tjdzdmtb","46Sf79SxCDv5O65LfoHtrGocQsRyf/llqT9ZeBVYz2w=",2561566964901567178,8256518587043536833,4112663124966500786,2469534243654229780>()) {
                                          case 1043072963:
                                             if (this.ab % 1200 == 0) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1gyz6qgnup3r3","yteB3fv2Ep/K/VCdA5K9q85ziB/gH+hpj+jZ0MeX8uE=",-5244631102595849029,2572118383917857943,-924792360758961176,-8143179656470901775>()) {
                                                   case -1699601586:
                                                      if (this.b.a().cn()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1379yxy6xsuna","hqANVxxSi7Ny/aL6hWv7Va9kR3089/arO4vnJAvEhfU=",7711880404706474702,-507587194676231358,6466783494691928857,-2513888206422715348>()) {
                                                            case -1947748712:
                                                               this.b.a().ag();
                                                               this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                                               switch ((int)com.yiyiaddon.m.b.a<"smj5g45b6rzpn","8xTSyld6f99ANumHKnKHGZelc18F3oVfF/pwMJs28IE=",-8796264201934566707,3601874590948392109,7663953841458641221,3307223659795625770>()) {
                                                                  case 937296117:
                                                                     return;
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
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void eO() {
      if (this.a.bY()) {
         switch ((int)com.yiyiaddon.m.b.a<"s217wlcfcxdw7v","UQQzABfxbAEfNy6l28G6FW3eK2E9tF8R08GUfGPzKCw=",-1855887678286932476,4658549064427094158,5234290716189255809,8546952567222552600>()) {
            case 1417331081:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.j.a var10000 = this.b;
         String var10001;
         if (this.c == com.yiyiaddon.e.j.d.a.GO_WILD) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"schlomftegwbd","qRZwIJbAOEBmgFC0Inc7ivNbecEGaKP9EyjEWfDNxVI=",6315165456747402998,2417686641056233346,-5912749433827764193,-7895124246301525042>()) {
               case 344005221:
                  var10001 = (String)com.yiyiaddon.m.b.a<"s1c69hv67ig57w","ErJV3sgr/QAQ0866AXNE6wI7/CSFQU1r+56JT+rf2Lm5P2IxKXQwhD748y4XAxh4aWfUwSl97+fh0uoUKTBeMFYV",-6523949772331934924,-815003801282717977,-3611697356110891418,-8384313836381783402>();
                  switch ((int)com.yiyiaddon.m.b.a<"szzag34ml82zn","enNfTBrqxdl3Bl9nXS6B3gjykOZZJqhpf2CrzeR1TR8=",6894335669087279906,6768877156378157730,5556110636491034701,-5222058535102081828>()) {
                     case -602945407:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = (String)com.yiyiaddon.m.b.a<"s1drdsxp029f5p","xostj2JaPA1IEkU1C6UaRtZZUuuf6NoXMJcC6SMOydmByVZXnL749wF+yK4IXjUoFYomFLcJUOr/pDTAU0E=",-2887412530702897850,5305513912034583879,2686635542561817595,-5342500771477091229>();
            switch ((int)com.yiyiaddon.m.b.a<"sq5nzze06bjsu","bcOqfRUzX7Gi3vPM451IrmgZVNACbffuwYGZWB1d560=",-3313696471570851503,-5513132762355803820,-7842964639858627785,8135139119475150326>()) {
               case -1226922606:
                  break;
               default:
                  throw null;
            }
         }

         var10000.K(var10001);
         this.a.f();
         this.a(this.c);
      }
   }

   private void ae(String var1) {
      this.h(var1, false);
   }

   private void h(String var1, boolean var2) {
      this.hC = 600;
      this.hF = 0;
      this.b.a().i(var1, var2);
   }

   private boolean a(com.yiyiaddon.e.j.j.d.a var1) {
      if (this.a != com.yiyiaddon.e.j.d.a.GO_WILD) {
         switch ((int)com.yiyiaddon.m.b.a<"s10dwcaqp95c24","PikxDxK7cSQ1N/Vi8r0hgk7/TdiqL2W718pOOm+aqKA=",5527132218801777680,-4104337671381198836,7960860051176488514,-4352652698743827349>()) {
            case -1488017807:
               if (this.a != com.yiyiaddon.e.j.d.a.DEATH_HANDLING) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ynwo555skplh","DM1xveO85CTX/FLIa2W+ARaRPZb8quoVWgKCZtkUQy0=",-1762855460740388048,-8851119778251369795,-7802541886627962657,-4379564277910806911>()) {
                     case 996072385:
                        if (this.a != com.yiyiaddon.e.j.d.a.RESPAWN_WAIT) {
                           com.yiyiaddon.e.j.a var10000 = this.b;
                           String var10001;
                           if (var1 == com.yiyiaddon.e.j.j.d.a.SUMMONING) {
                              label25:
                              switch ((int)com.yiyiaddon.m.b.a<"s36haxho81hpe8","QpP9TvUw8r4pD7oWjWpztsvvF9O1kL9W9S4flFiH5rk=",4344976275590674660,4058878773556394909,1946819914877795974,-9099645018188334694>()) {
                                 case 680384713:
                                    var10001 = (String)com.yiyiaddon.m.b.a<"s2ifbei7h29ug9","4/RvgocXnTkSk/FB1pYUbUcynAIUU/SickojqFXWR5LTHc95KyGJmIlMTwCJ4S781MUpyIruX+AdXb19Lr6LP3fiP6j6rgsY5W2KQtUW",-3927558664223892676,3203438933938391525,-8995809490393972944,-1097788118431168443>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s7sj98tc63eji","KNmHnhkuE0muehlQoA6RvlCYWsMj9chs6A0sov85JCU=",6666913559287877263,-9157473875304577977,8302500922724797485,2517854994826134010>()) {
                                       case -959426278:
                                          break label25;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10001 = (String)com.yiyiaddon.m.b.a<"s337seles7s803","oJ+Ia+WXCuj3R/RqZsxOqfYQBCjP8yGXo5rwOsSZnqlfVI3r4Ze+oNY1is/ntP/Cw4Vcgl9w2JWXY6rjd5KVlo2qnUccwEy/",-4955915703989225926,-1628494921821600272,-2038641783511155193,643772745324365693>();
                              switch ((int)com.yiyiaddon.m.b.a<"s31o7f0xbf4umg","ZlgK2jHjqrdvf5ZiI/7cooetv0z9Q+8hkSJQJ1APURo=",5374257186043885187,3067628675848182556,5145478100196697167,2992975092111563015>()) {
                                 case -1199843965:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var10000.b(var10001);
                           this.hp = 0;
                           this.b.a().ag();
                           this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2vtxesnglxrbv","kwP+EMwbeHLWsGNeFkmd9DT9ESLsk4yDsD6OCI+66SQ=",7046639908299601133,-7060181224271390932,-6963036199489455951,2484493115210797953>()) {
                           case -978136244:
                              return false;
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

      return false;
   }

   private boolean ce() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mrk3x9oaa5zz","OQ2yrTk/R8xRRXxa7mLNbrv7HRvRQa8eEiDpHvZ5mg8=",7104191780423022702,-2994640962202281341,-2813085895859344742,-6042966343746248914>()) {
            case -1049802821:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.G.player.blockPosition();
         boolean var10000;
         if (this.G.player.tickCount - this.it <= 100) {
            label195:
            switch ((int)com.yiyiaddon.m.b.a<"s3h393mhopdi16","iMmghM5C7Q6daXTSE0CNm7HGkppkAFNvA8RixpIVBFo=",4273641090302761832,-1396260702338434089,-7307744373811127113,1813318870686267408>()) {
               case -1382222122:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s1xopd9wphj3fv","oQOwLd8dEpahAKElFE7a2vIscUAgJDW0GErrrE7CXX4=",-1161420730753383071,9222495858325931953,6661555764517664481,-3608551047270773042>()) {
                     case -2056663808:
                        break label195;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s38zvhuy86kbtc","nyYPFeU9wmqC69lpg25yM1pusWh4MsSNlA0ewYetEkk=",4573761637745655897,-7640325956789159908,-6128551763182010061,378028694433039807>()) {
               case 1782052336:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2;
         label234: {
            var2 = var10000;
            if (this.a != com.yiyiaddon.e.j.d.a.DEATH_HANDLING) {
               label191:
               switch ((int)com.yiyiaddon.m.b.a<"s2j4jqbc8tdbaa","L1Dimf3g0d/NweKRxujUFXzJXbQzuQlt16h1r5IlmNw=",-7091143072770957889,6428001996419653936,-381679974347959165,-1400022406985392340>()) {
                  case -696440826:
                     if (this.a != com.yiyiaddon.e.j.d.a.RESPAWN_WAIT) {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s1lcwx0orjl70z","Kde/v5gGlwLixgVqzY5/wuGwwx4DE4mot76sYjqvqRI=",4644800580232164713,-2460287664650740464,-3353661965752314709,-8615995980341460502>()) {
                           case -973162034:
                              break label234;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s17w1eqxq0a8gn","aoECYM6cWd3lrPcqZicQojA0vfj7sZUvotQJAzhY+Yc=",7302180541446348022,8122578997899150191,3089719236755927758,2343909253328852907>()) {
                        case 1961249575:
                           break label191;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)com.yiyiaddon.m.b.a<"s2nvjyt06nehvt","EIlC/W5xMeTWgADuzshr2hT23eV50oBlMs0vLH0LJrs=",-948122229000728750,6872318797985187000,8371394982138905513,8046927032498684017>()) {
               case 1681167166:
                  break;
               default:
                  throw null;
            }
         }

         boolean var3 = var10000;
         double var11;
         if (this.s == null) {
            label180:
            switch ((int)com.yiyiaddon.m.b.a<"s1dwvzvafvpmxm","7wmHmlkNDEKREFGJdC7pewrY0bqlWq7PZA5PZeeO1Ug=",2917688928247910439,-4204837748383992330,-5529722182480340574,-6558041618700840115>()) {
               case 150371289:
                  var11 = 0.0;
                  switch ((int)com.yiyiaddon.m.b.a<"s13n9k23gbv781","umUhGXMRqPNGs40Npk38w3M4wWwJR3oBhgiwgRtdxPM=",-8841174737787659075,-1286214379616934694,4755297301399596382,4256388045595877915>()) {
                     case 1927869171:
                        break label180;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var11 = var1.distSqr(this.s);
            switch ((int)com.yiyiaddon.m.b.a<"s2dl8cwk7xsuwf","RBd01TOWIbDi0py4spvhZM4mAw6zQvWMYa8cvh5hK6w=",-7418360855187290245,5080736298874734378,-1366384401562449115,-2472963117335356031>()) {
               case -90946180:
                  break;
               default:
                  throw null;
            }
         }

         double var4 = var11;
         double var12;
         if (var2) {
            label174:
            switch ((int)com.yiyiaddon.m.b.a<"s16gx6iwe5x19d","i+8pM7NXNvze6nuVOesHv7ffRhwjBzBwLi/ThcaMs0E=",8456099225856970934,-4412571154051059307,6894482603828804044,6604543130673461853>()) {
               case -367187375:
                  var12 = 64.0;
                  switch ((int)com.yiyiaddon.m.b.a<"sqr1i4eldweas","68p+EQ683htv0BnWErv3IEDCHlk/OzuzxfXFEg/jd7k=",4778201421462508255,3692289256637510177,-4520757136968563777,-407931632055241108>()) {
                     case 842113371:
                        break label174;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var12 = 1024.0;
            switch ((int)com.yiyiaddon.m.b.a<"s1p4837l83446w","3zHzJRCDnglmmKe2SFz0ERHIUoCyAc6W3HbPbfdY3r8=",-1225919184519793719,-6891070362848266726,-2467647866761124688,3099060807709553287>()) {
               case 1954145532:
                  break;
               default:
                  throw null;
            }
         }

         label226: {
            double var6 = var12;
            if (this.s != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s25fc5pwst1pwb","iAcj/uqvTUhro96/d6TWbIlxEmObqfVP8LrJuHlOYP4=",1751102511162787459,-7802750604957715607,4201128254817358730,6531009595213371331>()) {
                  case 817193434:
                     if (!var3) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2yuxfl3hgv5ml","nA/7BawY8xgJDuCijwq1W3YfN3qsN/Crpyal0kR+kng=",2432839918431989285,8663032547558906147,-401953968361784202,-5137774154744490972>()) {
                           case 948983112:
                              if (var4 > var6) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2zjayemyk49ul","imjhney1iPObX7b7GZ8is3P/lw6I7az1jjwXE6YtAaY=",4441263128103470792,-864512586421825370,1085421173648474341,-5245992229782550179>()) {
                                    case -1971448096:
                                       var10000 = true;
                                       switch ((int)com.yiyiaddon.m.b.a<"s38pf5uxi2f0y3","0ZrVf/XrTl0y9EPkofr6kp1Gxi7ItzhbNexqhM4Uitk=",-142857783520973110,-1198947832746779217,6020682158784989056,-690224527866683433>()) {
                                          case -645981138:
                                             break label226;
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
                     break;
                  default:
                     throw null;
               }
            }

            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s1k4fuzzuafjkf","gfG82n9bGV716MHsTbwTcLSP3zkvDNWcv20xRDMRD+U=",4531822176899963499,-3955954120580403594,-6645032210557288890,-7215959134287196914>()) {
               case 1462095094:
                  break;
               default:
                  throw null;
            }
         }

         boolean var8;
         boolean var10001;
         label218: {
            var8 = var10000;
            if (this.s != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2bjp80bxr6c2a","5tcDziy44tjrndOM7B2O+LTQfO385Kh/6RsfgFeERXs=",3288969085078937626,3443775915598408450,769529705608544416,-4028791316171263221>()) {
                  case -1022605825:
                     if (!var3) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1waa9v8a4243h","ag8+UEnZH53zjBel6tIAz7MZ8C5APEsLISmRo1OJwjI=",4645946406424244163,-2691197614661763265,-4739687811848880176,-5918805244310979138>()) {
                           case 1183982525:
                              if (var4 > 64.0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3q9wli61zrvpc","PFh0NxlOliz3ecijVMUVRIOs0hyugKzYhDGarfQs3DE=",-9106969228547922861,-2700417863774318445,-4950694379288796028,-2614234507198969693>()) {
                                    case -764055589:
                                       var10001 = true;
                                       switch ((int)com.yiyiaddon.m.b.a<"s37c2w9sgn5ng6","E9RAYUv5bdmq8i8Q0xvHyK/QzenWR86fx3CMV2msA5o=",-6782002119280163147,-2561812891633977811,1882651103397667857,5320214285815106118>()) {
                                          case -1279614721:
                                             break label218;
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
                     break;
                  default:
                     throw null;
               }
            }

            var10001 = false;
            switch ((int)com.yiyiaddon.m.b.a<"sd3pc58c2tkgv","nTMYtGYuJGp0T+53gpx5v8AEl+EZA5BOCEDPpDEG5+E=",-2083099378082253507,-2662208877298810785,-8585192576868005647,-5086346201455906467>()) {
               case 701903493:
                  break;
               default:
                  throw null;
            }
         }

         label210: {
            label209: {
               this.cG = var10001;
               if (var8) {
                  label152:
                  switch ((int)com.yiyiaddon.m.b.a<"s2rc798wn20y5i","tfs2g8g82ajBd2mKChfUKT7Ktm+8lQOUF/jrBTW+jvc=",-7674427387358527256,8015457031364827805,4453394138362430596,2296909930495307420>()) {
                     case 691621099:
                        if (this.hC > 0) {
                           break label209;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2pcvut27w9l1m","dGEYEJpQQKcGBUQxGqtEGutcedWfmrGyVRjLv4Hu4vk=",2008244534649506223,-2596764712160695842,7843938405010753190,-164685646844233881>()) {
                           case -494442766:
                              if (!var2) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1vi8lyacmxkew","9elenJfd3TzmQu2oZBrP73FztrJi2Q3aiPtEcMXt5SI=",6192177168155312980,-2848190767050474467,-7506364919847438960,-2804415975733345216>()) {
                                    case -1503597656:
                                       if (this.hF > 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1sinueznaic0q","1/iq3tSMIYbYdOOMMSjWkLqV/0vbMooUMOPPz3B4Rdk=",3311792904146047485,6940535223964130112,8182299934943286280,5213225466299634126>()) {
                                             case -2131437068:
                                                break label209;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break label152;
                                    default:
                                       throw null;
                                 }
                              }
                              break label152;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s3tinjh0k7qqkq","mlRZ6THFdt5kHFyB2b0fdjE0g13FIbhWJNI3VeH+uaM=",-2227012086810663235,2664872128953685248,-2006044652122303458,-8768458476232264270>()) {
                  case 398815665:
                     break label210;
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)com.yiyiaddon.m.b.a<"s2id62anywgwpd","8BJUa9EFRP7MooFYwuyOuPcyRFILqBsDx96TSTqe/LY=",-5331461643834264273,1430955186085150632,1679818654860824354,-595378400494903866>()) {
               case -252941776:
                  break;
               default:
                  throw null;
            }
         }

         boolean var9 = var10000;
         if (var9) {
            switch ((int)com.yiyiaddon.m.b.a<"s31qjq13ibe24","3R9rRz/S4q2tcSv7dWcH0NmAmasZgHrebg437/MhfUY=",4724212286573275223,-2688335506191032275,8355872455643234578,-1085338007031081508>()) {
               case -435943579:
                  this.hC = 0;
                  this.hF = 100;
                  this.s = var1;
                  return false;
               default:
                  throw null;
            }
         } else {
            if (this.hC > 0) {
               label137:
               switch ((int)com.yiyiaddon.m.b.a<"s3refytslshbtg","N69XpB4rWDg81SAIuONc2ni+KUUlDeBkBQi6Ctmqdio=",-4644275861152790791,-3059635675997172331,7098057941859752878,5985700351652684844>()) {
                  case 1700004076:
                     this.hC--;
                     switch ((int)com.yiyiaddon.m.b.a<"s1gitvff2qt5bt","WBVTaA5qe7X7aWy30GRfGZhfyYJRGimmY+Dxte53zqQ=",-2414110251443131593,-4862484049177837208,1573599214125640153,4242903376305257321>()) {
                        case -39932153:
                           break label137;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (this.hF > 0) {
               label132:
               switch ((int)com.yiyiaddon.m.b.a<"s3geo0ock6mzwl","HOQCfpv7sbViB9ktvK8k+vQPOPWZ1x/7vG8hC/aV/Zk=",-6036303785612523895,4607007940383394401,-7405135040181026164,4008982930671241620>()) {
                  case 1938532443:
                     this.hF--;
                     switch ((int)com.yiyiaddon.m.b.a<"su7nnfs4xgswi","NkHzL+jOx0Ybs6sJ/+Pi2tglLU3RtA3hyW/vJfmSfHU=",7802471327194449860,-3386457819124041308,-5763802365699103781,3639252828027670184>()) {
                        case -1895910847:
                           break label132;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.s = var1;
            if (!var8) {
               switch ((int)com.yiyiaddon.m.b.a<"s10v47kfhcygr7","eNq43Y7FXu/GxIlIqtRig3+sRAHSvwfgHhY6Mn7QfzE=",5421364829753359530,-1432704264250077398,-9209075837373220888,4529124241332570990>()) {
                  case 1471084589:
                     return false;
                  default:
                     throw null;
               }
            } else {
               this.it = -100000;
               this.b.a().fm();
               this.b.a().ag();
               com.yiyiaddon.e.j.a var15 = this.b;
               String var16;
               if (var2) {
                  label123:
                  switch ((int)com.yiyiaddon.m.b.a<"s1dzgu44zo21id","UEqSw3gRzigLCMBSxvmhR0nsBKDlVFvEoNJqoaDz70A=",-7501134516402409516,5888991352276699852,409694388334914464,8750180216496878685>()) {
                     case 244471434:
                        var16 = this.mQ + "";
                        switch ((int)com.yiyiaddon.m.b.a<"s2faiywbhxlmuu","+RcvNRsc/1p4P1lLamWy+mZvy+g0H/pg00yffJVHedY=",3096665956799903572,4367081221192334036,-6535432834520971418,7350880351379261962>()) {
                           case 237558292:
                              break label123;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var16 = (String)com.yiyiaddon.m.b.a<"s1smlv655zbcf1","mrAzLhkwxP2SrZ0WUgMe5BbaU6Xx9/PiMnKqXViaGNgt1DbkoYBUvL+22cmQPtkZJp9hp+o5L6Sl9p3+KAQfikS4wTswpgOG8z/FRBBRnp+xypvyco3w1w==",3729037120011988594,3894841674035608889,6705427811147424390,9023092125258720580>();
                  switch ((int)com.yiyiaddon.m.b.a<"s51zj7zzkpqf7","Zf4R+mGonFkNmPTUN2umkkw6Oyc+OG4OfK0yFWvEphg=",-6765521964480107574,-6269101412642195939,-2382565743324690028,3127396952726242286>()) {
                     case 1207025512:
                        break;
                     default:
                        throw null;
                  }
               }

               var15.b(var16);
               if (this.b.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1k2p8z9cb4794","LJVOvWa+9JRKd9mAqcssgnze6+f0KhhOmOt/fOis3Ts=",-323707452777582007,-3162110838412563954,-1510718016952078401,7229081929861991014>()) {
                     case 1394593146:
                        com.yiyiaddon.d.b.e.a(
                           (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                           false
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"svprncu323hip","otCoGT6eMSKsu7e3/QrkXdM/zDZJKEJJGHFImZydQWA=",-5350221923016358597,-8562621849791192908,4721081467668432807,-1355206537055845542>()) {
                           case -381999785:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  return true;
               }
            }
         }
      }
   }

   public void af(String var1) {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uic9uy7dvgh5","iNlxu6InkRzEQ9C4wZfOnDqjSc6OjhrT1ibDdta/CvE=",5287401029199177645,6013040584247217406,-9104347787602679016,8850943236147819714>()) {
            case 1227736131:
               return;
            default:
               throw null;
         }
      } else {
         this.mQ = var1;
         this.it = this.G.player.tickCount;
      }
   }

   private void a(double var1) {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s14c2k6ulvna31","6uShJ1nbUVJrig5wUlqdeis2SDG03val80Ue/rx683M=",8177109931238728927,3241608189014276072,6166320825166083224,-7869804999800044362>()) {
            case 1509951123:
               return;
            default:
               throw null;
         }
      } else if (!this.G.player.isInWater()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ntm30was4wy8","u+O1O2SlFsNOyJO6tGVXmCRbLHOb+fVKRopVo5LvHk4=",4216317023218568845,2919454692490777952,2913304012508816578,7384734261828798897>()) {
            case 433997142:
               this.ia = 0;
               this.ib = 0;
               this.q = null;
               return;
            default:
               throw null;
         }
      } else if (this.G.screen != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m1bg3ncdjok1","MeOTQxUlDh9VudKhSc1+al2PttaDyGD30QhOFrhqUDs=",446582046674563536,-7293667008398151113,3708892545189584087,-2416401878319350843>()) {
            case 1092026243:
               this.ia = 0;
               this.ib = 0;
               this.q = null;
               return;
            default:
               throw null;
         }
      } else {
         if (this.ab % 20 == 0) {
            label59:
            switch ((int)com.yiyiaddon.m.b.a<"s2zhf2m6zcu0x4","BEhZJfoQbm6TgfM8QkwsYpux+/ww4UoPHeekjiv2vZE=",-446964578624652677,-9044386844164339414,2111168443150609957,4453264270983459091>()) {
               case -93897206:
                  BlockPos var3;
                  label67: {
                     var3 = this.G.player.blockPosition();
                     if (this.q != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3507rye6bproy","y22XewVRHlYYMoR68W2PDj6MT37vilv/m1yADisEyBs=",3006217032988554639,-4522543624125469066,7383360380597625566,-7437271084468785170>()) {
                           case 1339453071:
                              if (a(var3, this.q) < 1.0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s24iqe9iejksht","lFjiGGKaOJ1s6ZrME+0hJnB03CJfRESTPh64Z+6Ntc4=",-5518728956552580008,-1015950568991971538,7548677178735399272,2203373505267378828>()) {
                                    case -721753408:
                                       this.ib += 20;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3hentuzo0w9nd","PndHY0n9mvUCgm/pBgMs1MVL2s1rxNdytUk6lXTwJsE=",5242099406206787831,-3294611720103377991,4766439241167094646,7475751307886430375>()) {
                                          case 1542739847:
                                             break label67;
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

                     this.ib = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s2wieqrhrfse2w","6qUsTNHiFDI/hC/cbu00fSKji/5I5hKri+XT3E2yTAo=",-2416247741404124024,-2705645962378845764,-8126627553751814997,2961284849029876891>()) {
                        case -608083626:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.q = var3;
                  switch ((int)com.yiyiaddon.m.b.a<"s20u8xlp5i0rcm","ADBo2VyoR1ergY6yezoDsuPt2eNm1qr7D104DmYIndc=",1783943431922774557,-409905262848134315,-2673276942577191873,6915636225958812341>()) {
                     case 2129956196:
                        break label59;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var1 < 0.05) {
            switch ((int)com.yiyiaddon.m.b.a<"s173zwgf8bd68z","DB42anl5U98HyvFwDUxrhT450bh9gc4bjen3TkeYjX4=",-7011510912682171460,-6601243716306997426,8040706680242815929,-5265267895511737386>()) {
               case 722659913:
                  this.ia++;
                  switch ((int)com.yiyiaddon.m.b.a<"s3ictdao5zgnwq","briJ2khGC1O6uycy6hb0oCbRrm7XaLjJtgchgFjm04w=",-7382035764720082423,8597282043106051215,-4966467812015849883,-3936627377429123433>()) {
                     case -1435648974:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.ia = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s2w0eezxfwv4i0","7EmB+ljhM9RyyvimAP7mVMee4kb0A0pgXEe+WKEGWcw=",-3275952698843925756,-8762334518892162538,6342397629876006848,-7643332052009842306>()) {
               case -1138945667:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private static double a(BlockPos var0, BlockPos var1) {
      double var2 = var0.getX() - var1.getX();
      double var4 = var0.getZ() - var1.getZ();
      return var2 * var2 + var4 * var4;
   }

   private boolean cf() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sokbt8v6qyw0v","q7rGiSvEAJpTaBDsuJ7Q4CXUM/u97ov3v343ljkCzjs=",-6136926420962456150,6341627279500820649,-6540937403091266559,-4177039790653097297>()) {
            case -533247785:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.j();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2078y9avxt1w","M4ur0K+n4A5V7dCwjilV4wCJweyh6ocILfK+IQ9iyV4=",-2533095774830178329,9045707980793005376,4826047390624037153,-8094012683424337126>()) {
               case -218154648:
                  this.ia = 0;
                  this.b
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s38xia0b932zc0","lRyVhipqTNjwydVmLfipjbKIzbH2dBGGLduY3IXf+pMsppQ23N0IAia8sCyjkJhxHl6mV5VmKT0+Lk+J2+aEGfLaTgho3249OnQF1fbt",-546684573421172158,2098258454476123541,-1969505626436332393,-4206558977230939726>()
                     );
                  return false;
               default:
                  throw null;
            }
         } else {
            IBaritone var2 = this.b.a().c();
            if (var2 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s26c4r50ylw472","3wv5aK27rwuCAuUjdTjqvwVNb8fylb8JIpcHs7bbSXc=",696244042130646736,437099768035293417,5466354975864636941,-437667458432864572>()) {
                  case 1655083292:
                     this.ia = 0;
                     return false;
                  default:
                     throw null;
               }
            } else {
               this.id++;
               this.p = var1;
               var2.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(var1));
               this.cE = true;
               this.ic = 0;
               this.b.K(this.id + "");
               return true;
            }
         }
      }
   }

   private BlockPos j() {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2l83e5ucxg75i","/vttZkJS5nA3At+JdZQeVTJMxeBGzBUSgX3o/uq0++0=",4189057851707311318,1800652683108267292,4235446947003020306,-4552815823781387424>()) {
            case -1191740760:
               if (this.G.level != null) {
                  BlockPos var1 = this.G.player.blockPosition();
                  int var2 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2oft4ibdc788t","qFT7gVMKQurzGKwJJtpiohfht2j36zrsOb/g9/BhahU=",1940136981559200824,3341050338239710172,-2090406345097078779,8540513969787866045>()) {
                     case 2141808156:
                        while (var2 <= 40) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3l06lo9m9izew","OJTtvJk0JRGEuDrjfKHzqg+X1RzCjs1M2NpgP5wBJhc=",-1657901193052444178,8484869641920840921,167837681747213924,7253855346932064191>()) {
                              case -130695677:
                                 int var3 = -var2;
                                 switch ((int)com.yiyiaddon.m.b.a<"s30u5syhfpnoyu","Y+PypdQ5Cqb+BifmDI8NeQNhacge/P9Ceuc+QOg7nTI=",6659447694809439569,1627047863124795012,-5616950519588512019,4752623456507257600>()) {
                                    case -1330056948:
                                       while (var3 <= var2) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sjw7tvrnxnixa","qXmlwOb9rFtmdmBFUgaF34v8ka2lwNRTQU+yCAXokxI=",906025862360607005,-2916544210286373402,1577666449972131733,4308933856319137753>()) {
                                             case -1090307636:
                                                int var4 = -var2;
                                                switch ((int)com.yiyiaddon.m.b.a<"s1gdfrw9pzsfwb","oHkyHs2sZvBc8WkDG2MUHlDu8+mQC6o4AETDtt+S6s4=",-8469444073216830673,1700552587094049816,6900705133478268443,264441804237587623>()) {
                                                   case 1067953093:
                                                      while (var4 <= var2) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s10pjabbobg0f9","KcLd2d9aBWNtxvBxI08F1dBCMX2baMr1ndZa9bbobu8=",7747588661556078885,-3768299054503023586,8888241223861505009,3618501907319530233>()) {
                                                            case 1245002616:
                                                               if (Math.max(Math.abs(var3), Math.abs(var4)) != var2) {
                                                                  label90:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sagmhgnjgyw35","xkq94mnDFbTxTuj3sNZNFud+sHVoZOfq7w93zyd7oXU=",3102302072333178063,-1420488129482085311,-7129100705295663455,3701152671881442268>()) {
                                                                     case -593809417:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"szh8dfslikqva","3AaU3dl9yvYlMSapd4F3PMl7p6cgGvoI+rGYgc25qoE=",-3465885445580513678,8799275252865575351,7174466623259631340,-8209757286074099729>()) {
                                                                           case 263477990:
                                                                              break label90;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               } else {
                                                                  int var5 = -2;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2q6vwi6lau4lc","ggIVzQEErD0LmPffUeHiODL8+horE3XuIcbR7FBg7LY=",-7179172020573857392,6617800850728730818,3159393578924223009,-5591630380298678850>()) {
                                                                     case -877729426:
                                                                        while (var5 <= 2) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1fpe91ol4ssmy","FUDqfkoHHMCTZmrVG+7taOv+kyrKaqr5tC+/iTrdotw=",6273595365056566111,427205123966074308,848477381473597862,-6102391216911176021>()) {
                                                                              case -1416812862:
                                                                                 BlockPos var6 = var1.offset(var3, var5, var4);
                                                                                 if (this.G.level.getFluidState(var6).isEmpty()) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s1nisn9s8c59n6","qWN7sSd1Ej9Rlb+Ka5S3BlDXFxYWqtDTfEytP0dsFbY=",-5292063848663043108,-2259532972281115658,2409875242658218991,-1278954720561124220>()) {
                                                                                       case -259301787:
                                                                                          if (!this.G.level.getBlockState(var6).isAir()) {
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s2y64bb928cka","q8FzIi8/ofN9zIRZRvGy2rdDkRktK1BQ+PJOl2W12sI=",-7073947879553207391,-2865435851895882161,4164823897498636174,4600035658298629268>()) {
                                                                                                case 42379411:
                                                                                                   if (this.G.level.getBlockState(var6.above()).isAir()) {
                                                                                                      switch ((int)com.yiyiaddon.m.b.a<"s3oxx88twv2wx7","HHnyyWgL0cvZJcuMe56BuOFMkVluwlRQieZIckvOlYc=",3628691420391014706,-3226390557522377932,-2224713925028892970,-5726848743157806562>()) {
                                                                                                         case -202700403:
                                                                                                            if (this.G
                                                                                                               .level
                                                                                                               .getFluidState(var6.above())
                                                                                                               .isEmpty()) {
                                                                                                               switch ((int)com.yiyiaddon.m.b.a<"s2v9vg16vbq650","rKrRM/xTBl5WtDxKHubx6F/vNwA46srS3OyeJq5/bUw=",5889161562190663274,-6766790332918038832,1296004392272114422,5706425543406546934>()) {
                                                                                                                  case -387300602:
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
                                                                                                   break;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }
                                                                                          break;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }

                                                                                 var5++;
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s1zi4a4mtz6ljz","YamPwYovkjnaxz4n0qgPdFN1UW4nYEp80EEeVWc82Vg=",6161357037680515890,-6238814291812018995,2551637547672245490,-672677449843591386>()) {
                                                                                    case -1941189026:
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

                                                               var4++;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2tjsia8tfx4sx","W+PJOk1VcXk5+8igVA6xybwkiNsAHkjyOm16YDA6H9A=",-619272992007883074,8785612554866487305,-3303060939637266895,-49894487696293259>()) {
                                                                  case -1363217120:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var3++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1229eaxdf72ff","VwPy8NNneO5cUN2OxpmyvM685ROdwkANlOHo7WVRoD4=",-2373879008933271209,8226908167590695152,-9081893163403018341,-1580554734626832421>()) {
                                                         case -3271183:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var2++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1mf7ogkt04eoi","vKSCJ4oqUH3Z/y/Zzj42o5X0r1ACzcrvroUiRPwxK8c=",-3117529276475103661,-139312680369799576,8846053412316204117,-3047650892619367942>()) {
                                          case -987121507:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return null;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s18xv10h3xn65i","NMeOG9QTY54yshMYGJYYOtBa+4yWUAH/p+7UFuJkChM=",1637160088601695126,6575164475937379680,6154312158291017639,67792470752694824>()) {
                     case 446580092:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private boolean k(int var1) {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1xtf34cxsya6i","GLHx6A6BSX7HFyweVvQZlOIA0m5Qn0vQyr5Oeq9oh38=",4417580794286200371,-2919603042896592823,8609269088551561695,1793472633176921488>()) {
            case -1829866603:
               if (this.a(this.G.player.blockPosition(), var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2oj0qehctot6z","Ewaq2uOXS8olpQlU8K/hiuRluzXNeX/iyUhppZRdDpo=",-429509240988636756,-6725675169368516680,995676161255982249,-8439125290181260211>()) {
                     case 1890148353:
                        switch ((int)com.yiyiaddon.m.b.a<"s5kkehiu1jmx4","wOnUJ9Wk94iNdPoWRJL19LTygpfxAkIjof09LD0QCdg=",5159456611178732667,-4851203754341540041,7013550459845147987,-5845867230193878424>()) {
                           case -320721140:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2ezdqxjj3m7fg","h1upnkJRMqfXN/XnBPPXT3E2LjpjCWEVFYIvVkyfTzk=",4676757991171582596,-7349587344432032070,-5259849191288552441,-6587810753270014880>()) {
         case -1293489609:
            return false;
         default:
            throw null;
      }
   }

   private boolean a(BlockPos var1, int var2) {
      if (this.G.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3i1cv1zc8iwxg","ClPViLZ+t9Kjpu9bvK50xtrbv9vcxrYzTWgfzhaF5CQ=",4189058789226818883,-2422590949631692766,-1693313549434540459,-5409245887566701130>()) {
            case -557900874:
               return false;
            default:
               throw null;
         }
      } else {
         int var3 = -var2;
         switch ((int)com.yiyiaddon.m.b.a<"s1k8mnfd086r8s","rsKsVav/9FSDccax7sSssx5D2W27pVyjhKNHaanufLM=",-7510781767326131510,-2236396560311393282,1664326185006033137,-2242320950075528550>()) {
            case -1920118955:
               while (var3 <= var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"se8k2uu40vtkk","a98ZzeKOmmanU8rIC9/wOjtc7SuCpDeKPx8t7tidFME=",-9002068198975454866,-2570821078873242182,-6452878237886554168,-6625039375836963290>()) {
                     case -303573504:
                        int var4 = -var2;
                        switch ((int)com.yiyiaddon.m.b.a<"s1hye08k9jxmya","HyK1jQOUa2uFPlF2Mfr3OlhfNqnob8WN4huZ5NccEEI=",6646790515526675988,6345966421995166349,3800699029478989325,5748328819469816841>()) {
                           case -147952634:
                              while (var4 <= var2) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ooilzzxisk7q","rvb7BcoCG2JPIZMygzkXEz/yWhjBXgoGg4RW7LKSBKo=",-3192430641753735848,-681024589444311772,-7779164902432376577,4297956449183723910>()) {
                                    case 491741731:
                                       int var5 = -var2;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1acwc170af663","xnPX2jSUg0EgA3OjHuuzdc4zSEqY3lyEijSu1FYjQ+8=",-6686255575100327047,-6916859927343323499,789203316847092278,743184669145747237>()) {
                                          case 1054535253:
                                             while (var5 <= var2) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1x13el8wmc7jh","newkb3bc1eHFgbNbe1PTZ9Ud5n+vjQDOdYj6hv6H8ho=",217138388019121425,-3957615054119394233,1447440842414607035,-614193433399145256>()) {
                                                   case 663484428:
                                                      if (this.G.level.getBlockState(var1.offset(var3, var4, var5)).getBlock() == Blocks.LAVA) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3j89zxsft1ziy","aTNX3En5QZ5+vNRO/QdDXqTKv9mrhuvo3ti9d0eS4Bo=",7873587038562678549,-4281636944367975193,2575245795284239693,7513732588270424575>()) {
                                                            case 1586497748:
                                                               return true;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var5++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"sum2kwmggbdgw","3BrJHQgLFM8AX0oISCNuum74EOSpu9CN82TdiRhbSB0=",8747834909422675873,-4345442924181181883,-8853391368650726606,8299019288918322383>()) {
                                                         case -392560454:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var4++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1lkf0a7em6n13","1VO/+21+gxKgcaJS/t4INGwTV4nGyKF5ecq17p9t4kk=",-4086240944874564336,-6723174539357228786,-8013253442852931959,3460101375140896778>()) {
                                                case 1243587330:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var3++;
                              switch ((int)com.yiyiaddon.m.b.a<"s3ubn5gv47ncan","1SbS4YF7nMbwPadRfo9O1vnUiXfYVhrfep4A4/kfj9I=",5790816086020176300,5890015980386910257,-1553885307684798081,-5118909397072361424>()) {
                                 case 223086669:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   private BlockPos a(int var1) {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sq66ubceu0uno","NiaaMjed07sQ22dxmeaeh95gcKuv6OkMQ2y/UT/UPjg=",75199381158301262,-2919235908261391863,2138116417347118678,924850301951450517>()) {
            case -890196450:
               if (this.G.level != null) {
                  BlockPos var2 = this.G.player.blockPosition();
                  int var3 = 1;
                  switch ((int)com.yiyiaddon.m.b.a<"suh8k9jy89wrh","cfXS5PtCpcJyvpB9FZISk7+cgotw8N1Pg9sZC1Rw79w=",-7517249542006646665,-7224727928968257961,-4657649434658862040,5153567363096094398>()) {
                     case -2038106135:
                        while (var3 <= 16) {
                           switch ((int)com.yiyiaddon.m.b.a<"sa8cmpuorazy9","62aUrtWW49yi0ZFUO1Stvl65YPNb9FZ0UTrmtmRN/C0=",-2803625064463873622,-361982350658959040,7039574040766471208,-2387717809755245897>()) {
                              case -486554884:
                                 int var4 = -var3;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2fhmxr3pd2agw","NCV/seNmUpByE1GRqbTrNN3d3NHohUAszEhFb0/OKks=",-2946947585777197816,-8545662085758630043,-7786429855251541396,-3318750669144089011>()) {
                                    case -1858507008:
                                       while (var4 <= var3) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3o5y3nwu1qwi9","+6cQPGPiVivxcUppjoonMpMzh6ffIcqVK0U7RQ3gtck=",-5767036488990235926,-1747691661162964825,-4825424556833612666,2231806372756662987>()) {
                                             case -1115086627:
                                                int var5 = -var3;
                                                switch ((int)com.yiyiaddon.m.b.a<"sc525em9z09lj","H9Yu8iNfCBD0ZSFSRt+v+SMjZ0eY/m3GetY8eKv+TNs=",-8951696057307589332,6401207090816189116,-862435193933296344,3646768033633766162>()) {
                                                   case -2145302432:
                                                      while (var5 <= var3) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3e1bk576g342m","oCWrfC2lg08cE2N9NL6aSVXfcu5Q4c1a/VJfYrog/no=",5085374976061772470,2376334872060339633,-6860337355088135748,3095212409080969930>()) {
                                                            case 1719473487:
                                                               if (Math.max(Math.abs(var4), Math.abs(var5)) != var3) {
                                                                  label100:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2z2mtrw6mr5ss","HwUFEizZbjEa02VfU8/A0AOy677YfCKPhhuoz9WY+y4=",-7299680470936721520,1735993049368385636,2085829402443452445,-2943433415629330030>()) {
                                                                     case -2009239108:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s1ahazt2lb5cgh","5QDlHpz7lSUkYhIShBfax/BxMAmdBxhoZdOoArbIuSE=",-4903737434023668257,-8483797624478319074,6732533735343496404,-8127117044768968031>()) {
                                                                           case -1723207935:
                                                                              break label100;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               } else {
                                                                  int var6 = -2;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2d2zbehm2umae","lurst3S0jjRr1fHUIw4Qll456wV++Ycido1FYC9gICo=",2834614386313398426,331746244848095624,6106396820444780358,-944927626895007342>()) {
                                                                     case -1550774393:
                                                                        while (var6 <= 2) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3c4n0yznxwgcb","G6Lsz9f3AXJIRfJjic2kDFUtCbXfp1r5d7+AZ6ebsS4=",-4002756762007256740,-6868128241773454857,3292191997830131008,-330602077500268466>()) {
                                                                              case -490658965:
                                                                                 BlockPos var7 = var2.offset(var4, var6, var5);
                                                                                 if (this.G.level.getBlockState(var7).getBlock() != Blocks.LAVA) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s3age2o062gxn1","REWIQGNZECxl37jNgpscrq1OA0xZgD1ryVBBPsvq9lc=",4848129827401030876,4460232864746241550,8339941438117839928,2041505788876051964>()) {
                                                                                       case 635549629:
                                                                                          if (!this.G.level.getBlockState(var7).isAir()) {
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s2hwxk0hw8mupd","ETeqBlLWdEqW9nhQGW6Ktur++LNP+n4uIPLg4bxXvhI=",-1877200824252379757,6945323801227143259,4708077429670941641,-5120456502492001882>()) {
                                                                                                case -988880134:
                                                                                                   if (this.G.level.getBlockState(var7.above()).isAir()) {
                                                                                                      switch ((int)com.yiyiaddon.m.b.a<"s38x1l1mc65lvp","hCBjBSrLzKJSNCl9KYrmRjmLG8OT50NFNoBoAo7NOJc=",7452607591945951087,-6536094446910525494,7694041810049631911,-7524474757995342290>()) {
                                                                                                         case -159239244:
                                                                                                            if (this.G
                                                                                                                  .level
                                                                                                                  .getBlockState(var7.above())
                                                                                                                  .getBlock()
                                                                                                               != Blocks.LAVA) {
                                                                                                               switch ((int)com.yiyiaddon.m.b.a<"s10gz1iuvabhy","QwjJnjxz6U3+WPBxZNu6pDMbWNffL/zxywv5sWEBmQk=",-3094990301391281150,1290763563623995373,-915111468638237481,8924719465878694468>()) {
                                                                                                                  case 1978346813:
                                                                                                                     if (this.G
                                                                                                                           .level
                                                                                                                           .getBlockState(var7.above(2))
                                                                                                                           .getBlock()
                                                                                                                        != Blocks.LAVA) {
                                                                                                                        switch ((int)com.yiyiaddon.m.b.a<"s21proxqfvytpf","YNDFPMBOoKXRYxHBYVBzoXTZymdmLt2qYAxO19avLrg=",-3035454211574736162,-8856126373186313190,891895017890337550,-2123136372142358816>()) {
                                                                                                                           case -2131566468:
                                                                                                                              if (!this.a(var7.above(), var1)) {
                                                                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s2c3lbdqdis9a7","TKnGK+mI1NA0zfLGxxHOLPj0DWL1q7lOGqbTLhb6sZ8=",9043803741256575146,-3168884861701260964,3525479509030206430,-6454116857845347590>()) {
                                                                                                                                    case 1920151848:
                                                                                                                                       return var7;
                                                                                                                                    default:
                                                                                                                                       throw null;
                                                                                                                                 }
                                                                                                                              }
                                                                                                                              break;
                                                                                                                           default:
                                                                                                                              throw null;
                                                                                                                        }
                                                                                                                     }
                                                                                                                     break;
                                                                                                                  default:
                                                                                                                     throw null;
                                                                                                               }
                                                                                                            }
                                                                                                            break;
                                                                                                         default:
                                                                                                            throw null;
                                                                                                      }
                                                                                                   }
                                                                                                   break;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }
                                                                                          break;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }

                                                                                 var6++;
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s10oazgxdj2rnn","S5b/RbbW8keVrzA5sq5eyrPmB1/GdxCY8P9MYLiEB14=",214277660803140718,6570045002682126012,9163465030038728656,-2175457193872047464>()) {
                                                                                    case -1645641116:
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

                                                               var5++;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1qy3dz80wbn17","3iF1oduzA8JKPPhdaHNQb6vBZSzmWUXh7rARUUkSs7A=",8530906057986706772,-4564111667400462496,1608815227850317575,-2784006889190770775>()) {
                                                                  case 51979613:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var4++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3hsykug21lplk","KBiP42Ju3HLQaJNF3zU3PPOLUYvaSChrlucME1UATyY=",8907643614432314187,1640636816274602311,7364470594790280535,-4512793745671465405>()) {
                                                         case -1776959590:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var3++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s39g0k8e3py8je","9udx0RB6sgMoYvJY3Tw7K80VHDmUUcpX2J2w55QhtHE=",4478461184464464660,7884513438911268681,396006134364866159,6178665299953288394>()) {
                                          case -1942310586:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return null;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qny53u37onyn","l6DCwLsUWaCEh9um0IYCTogl2hSyPYA2SPfnWvaXPKk=",3783191477438107084,-4231345011678069196,-1426229236825187370,-7427301418793625589>()) {
                     case 1303971578:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private void eP() {
      LocalPlayer var1 = this.G.player;
      if (var1 != null) {
         label94:
         switch ((int)com.yiyiaddon.m.b.a<"s86h2ipb67f9n","VPO4AFJ4yE/IU7IMF6wqRHat+V+6ibAyI00JqJyGGg0=",6349739564516230181,-8144436406590521852,-6261236870432496549,-3946449694415911172>()) {
            case 1292225986:
               if (this.G.level != null) {
                  this.iE++;
                  if (this.iE > 400) {
                     switch ((int)com.yiyiaddon.m.b.a<"s240jc5rlcwqw3","wl4kdx8tKAHyUI5UrKExaGa44cE1s6LVOrz0zLC9e+k=",7691213204009643216,-5935338471351050317,-6656717282031682032,-4501035788178538139>()) {
                        case -232301049:
                           this.l(false);
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (!var1.isInLava()) {
                     label85:
                     switch ((int)com.yiyiaddon.m.b.a<"s1vg69ou2afori","hZbovxxjakjJB7xbprKGn4PUpLfR73hODyidaAoa8w4=",-521554095428326285,6143929866366018607,4849438847592364097,2090984284198159456>()) {
                        case -52722845:
                           if (var1.getRemainingFireTicks() <= 0) {
                              if (this.iH > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2g3gzr7zj7bxy","S9ojNzn7ureLKKZV1Ym8n4rtRwQnfyIRAfVDIeBjmD4=",2970925577881582591,-364593659814650974,5977107611094104639,-3179796582199358907>()) {
                                    case -1271710770:
                                       this.iH--;
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              BlockPos var2 = this.k();
                              if (var2 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2n2hezd8qma4f","Zf3V9+8p67GmoPnJFhMqh5Kd52S9n2VN4ZZAGCNDEKE=",1654013827229529706,3920427337945454220,8108247660820903205,-5252829503598468870>()) {
                                    case -2014363357:
                                       this.iF++;
                                       if (this.iF >= 10) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3az1vbs5e6nyn","oKT1idijfConuDLo7R8x6Ym4ryhLfPWT18Ia2DkfSy8=",-1883846900180532758,6955556269754676497,-3409412804705317137,-5282192801554383319>()) {
                                             case -1671754941:
                                                this.l(true);
                                                switch ((int)com.yiyiaddon.m.b.a<"ssl34dl4o6kyg","T2PjJdy18HzuCDg3gzdP6WbVxElMSZejW6w6wJfgMGw=",-2930558666334724913,4680298325635830786,2908968819803856719,-3814737312800587102>()) {
                                                   case 936763226:
                                                      return;
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

                              if (com.yiyiaddon.e.j.f.a.a(this.G, var1, this.b.a().bg, var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s34zx5p7krc951","b9RbTX0TkQ7pQ4SZ0wPeDhRuV8PMa1EWa8+lJmZHPag=",-5555936167937008166,-4059559870956834778,-7064007752537115742,-8422999063972236074>()) {
                                    case -1608418035:
                                       this.iH = 3;
                                       this.iF = 0;
                                       if (++this.iG >= 16) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ouyv47epi4au","eg0OK3axAYaoF6+ZYTsZeZYDOHWltIODo3mLLXB3mp4=",-8374183646467353108,-520937369430795899,6541171383073169724,5456095320426071816>()) {
                                             case 778797871:
                                                this.l(true);
                                                switch ((int)com.yiyiaddon.m.b.a<"sfwlnho0t2fsz","dWF9E/DkOlLkKp73RAgVNpHd72MkOP04JXCC8S+LYBQ=",1817166253120695388,-2380999482968317118,-9143459403640176022,-5451340056711100551>()) {
                                                   case -290430902:
                                                      return;
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

                              this.D.add(var2);
                              if (!this.cK) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sgvo2tbletvk9","4v5qJEuq3OpsjnerryniIcp/MMrZj+eFYyK/4AW8rbo=",-5080262931452176985,-4373350727454102701,-1770116049210170971,-1457737977574561541>()) {
                                    case 1493902761:
                                       if (!com.yiyiaddon.e.j.f.a.a(var1, this.b.a().bg)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1vygeenmgu040","2UC1sS2hQGKkfhB9Svx0/E4Cdu1BsOWPB48wzNLeu68=",983817294358944034,7748659510235428394,1307891408285646961,-2972896878326354797>()) {
                                             case -397483561:
                                                this.cK = true;
                                                this.b
                                                   .ad(
                                                      (String)com.yiyiaddon.m.b.a<"shjgkilmpf1k","fiXV8NYbwuCbwSILS4Z6HtTLWOWLOJGjpr96e24ae1WGUT776Jz12zUiFMmqr8eb7V/85efaMekKgIoiRdLLbkpQWelEl9tjjkxz7YpnopBDK0JFG2jRHI2Zodv8zmismLw3QKHk46o=",-6947671766437251812,-8982926882043340751,3061018323023854202,5057595934817416176>()
                                                   );
                                                switch ((int)com.yiyiaddon.m.b.a<"s3dcoywi60wc5d","zi9Q4+yAbrd6e1lODinqRo5vTo7KwzLR5Yyt1M9VMD0=",-4474226758992274892,3987708292442946250,-3591438979822315295,8483738843243993868>()) {
                                                   case -886268572:
                                                      return;
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

                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1w5vav0ti6adw","2x0dp8BnXgCw3CC+NWq35yLCIAf0tLj83KxYtH8+kVY=",8909972986195962010,-4443056484826263187,6453659185480825181,8039383136056029928>()) {
                              case 715886704:
                                 break label85;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.l(false);
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3vlogcpjgxoae","LxbAm+1NzDYzMSQre/XDL4xU9g04F/VD0I16C9bQQv8=",-4903308768278754667,-2121237798676625178,4014864276935306113,8104472674536129674>()) {
                  case 1238402304:
                     break label94;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.cJ = false;
   }

   private BlockPos k() {
      LocalPlayer var1 = this.G.player;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1etgaiiiidem4","6j7LCCiAnik9P0Ptj/VzqvqNL9PjWwemQocqpWu12FU=",-6242470457477733011,4476709553503262509,-3590886265248937390,8439356224464125040>()) {
            case -2037939318:
               if (this.G.level != null) {
                  BlockPos var2 = var1.blockPosition();
                  BlockPos var3 = null;
                  double var4 = Double.MAX_VALUE;
                  int var6 = -1;
                  switch ((int)com.yiyiaddon.m.b.a<"stp4n3t81wi8a","3PL7zqJm1kdEaOPI9dzC2FUK8czRwYtPdoPqmkJMh/g=",-824755414925691795,-7978375068734672100,-1709082404352812939,2549158137486436258>()) {
                     case 1210373966:
                        while (var6 <= 1) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2y75nuq95jqa7","P2S+1O4SCaUzXRWUgQKZkk3kpTor2kA901hZ7rBuP0U=",-8680722418582275367,3731178388460395359,2192489029700402708,804643394317285127>()) {
                              case -593124341:
                                 int var7 = -6;
                                 switch ((int)com.yiyiaddon.m.b.a<"s17zxun0p13tw9","h2QJoDPSZw51IKLeHaFTJ0jqXjdCkQBJ2kVIIsQS/yU=",7198965544079038862,2494302282679076039,5643587717171180441,-8128211449475174670>()) {
                                    case 1037364972:
                                       while (var7 <= 6) {
                                          switch ((int)com.yiyiaddon.m.b.a<"ss1zoo0x6q2x3","WNG0WPQCbxrSm7Aq9etq26BmDWwHrmlmOFJMWaWQgm0=",4326952980052892033,8156654721116868309,-3419843410695877325,-6280600826756451754>()) {
                                             case -885802232:
                                                int var8 = -6;
                                                switch ((int)com.yiyiaddon.m.b.a<"s21d646zof7odf","kRy6eQ1rhPI0yoY1Xtcvf/UDq0RBnpRBcb7Kl8tCfr8=",2100951352169109808,-4290590078916182300,-1854064212493749173,-5481343052899737900>()) {
                                                   case -1447947702:
                                                      while (var8 <= 6) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2loepbvoo648k","sJ3vfpj6Unb4dwnyPkj3DIgUY5jvqSwB02BNZtDZLgw=",3681513765361336797,5111754668408397128,-4712044151276157594,-6192578723024290552>()) {
                                                            case -91493600:
                                                               BlockPos var9 = var2.offset(var7, var6, var8);
                                                               if (this.n(var9)) {
                                                                  label74:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sztrmkha5ca2h","znWFxCJO83Np16If2dCTcSktm+f28BphKauumLdkDNM=",5956449777681114379,8927378582959305545,-4231213145622081045,4396880402759169361>()) {
                                                                     case 311873442:
                                                                        if (this.D.contains(var9)) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1cotkq7gu7016","DHrItjmrFBiRC2iIGIgAFNi45tGGysOKcuV2t9SrSgc=",5370442505142716167,-4945097492025228518,-2227819916078176962,4507862190987453227>()) {
                                                                              case 958058666:
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s1ljr6l3qrxmrw","DH88KZ6U6HGYsKiz+x1UEhyYanMC7ikAuzZ9Uu2z2fQ=",-880343642941754614,5227758498251564388,-7068597417686790110,7724569780317847648>()) {
                                                                                    case 1402952389:
                                                                                       break label74;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        } else if (!var1.isWithinBlockInteractionRange(var9, 1.0)) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s22u5car9xvrec","nefCgTuVUPQLQks3d9WtzxnBNXgLnIinX84JGOHcymQ=",7600591460367507499,8061798931894858329,5992924908052647400,-3152872196186867163>()) {
                                                                              case 1870489288:
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s1z5l3ir0ahh79","m/BcfDbE98QmNXWQOetbBfnQszQQHGYF64TDbU182w0=",3984556887738179649,-854710316367021380,-4564966836614394895,-2517210048220709785>()) {
                                                                                    case -1659303092:
                                                                                       break label74;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        } else if (!this.b(var9, var2)) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3ktpyo8b4ccyz","ANRw7/9mkAbhC4jH8OmbChMomfhsrUR1ntq6O62JemU=",9068358988598989070,4073056185047309445,5620834516983375153,5652568703452308325>()) {
                                                                              case 2107190904:
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"sjk62afc9cjml","D0nCCCH10NWkOa33ziVGH4wfxI4x1wKaHRBDeNscleU=",3758368831603115314,8132079407568836250,-2008772877855147785,1217422330860187078>()) {
                                                                                    case -283806549:
                                                                                       break label74;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        } else {
                                                                           double var10 = var9.distSqr(var2);
                                                                           if (var10 < var4) {
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s22y1f7ro6ru0g","BFfG34BYfBayjE2v8mGQAfSocwv0AbTMr+BP1P6Rgqs=",8568350494625253696,8956380464574163170,5229255828002868026,-3974672201650615304>()) {
                                                                                 case 1257679255:
                                                                                    var4 = var10;
                                                                                    var3 = var9;
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s2bdne4nthx6fv","tNpmus/xYBxMMG8XCKVL/xouI/5colkgFF4ADL6bO8Y=",-221732188092826474,3964280769219407435,-8615155660388848211,-7330833079375624280>()) {
                                                                                       case 2003743887:
                                                                                          break label74;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           }
                                                                           break;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               var8++;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2j3lwb1zbrgdz","XHE7LXLWmYjlJGUqZfodKKY/8yMCZ6OdK2Xg27hV9iI=",-874806322097445560,9204088667213729156,6921227805520847075,8972716382946228991>()) {
                                                                  case -1915029534:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var7++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1bsyo2yijgulv","zih35VG7Iq+14yYomEtblALVp3BFeogdG888S8Q3cyE=",-5773427886465714674,-1313279881473571572,-836364083453070971,-22498737134194320>()) {
                                                         case 792370085:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var6++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3eeplmxylu9nr","D+YBOHC+YyMrudPf4CfwDG41Ufi50nOoHwVtFosOtzY=",-7903578213865440626,2699228186202923054,-5494370099983125791,-4187788770703118228>()) {
                                          case 965632343:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var3;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2o9m4ww57qmmu","by8P2Q05Y0veHJvDToSfI/iNHnHn1vHvdIMnLfkBOwM=",3517061768673930706,2524720850219960500,-1897001507375294253,4833539815569490749>()) {
                     case -27396034:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private boolean n(BlockPos var1) {
      if (this.G.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snd5lilki4scu","8Ed9KU2aJGQ0DHiXxf/yH9qeM3YdQshaXAVyZQLopRY=",-3266887138093551531,8524108454812456901,2271076298301033538,-6651178145703630941>()) {
            case 601226152:
               if (this.G.level.getBlockState(var1).getBlock() == Blocks.LAVA) {
                  switch ((int)com.yiyiaddon.m.b.a<"sp3dfd8aeiswn","lu0TY1A5+5v1JxcDj6OrVISF9beNEOil6ytl+RZI+Ek=",-4470490763995127498,8390787477332125610,4741230360350803494,8516901174307378868>()) {
                     case -377525933:
                        switch ((int)com.yiyiaddon.m.b.a<"s2siqnug2p5k05","ErYuHO3EAnz39m6b0uWLDc7kIn46YNtjZKdjNgv3exY=",-5397218875959902119,-6703287642586013022,4287110553592060301,6468828829865915325>()) {
                           case -1942251545:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2djzisemzvgj9","7h4ZGGREhZO2y6xehghNThOPdhtmIJoDOvSqHoz8qyk=",8467523400753109268,5425035903192072953,8146614348779525073,815365497146367089>()) {
         case -770416624:
            return false;
         default:
            throw null;
      }
   }

   private boolean o(BlockPos var1) {
      if (this.G.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tgbridpv7n8b","EOefxpQAVKGqFJVIa6YO0f2AQcUcoEPE0NwGAffB7w8=",3238886968252927963,8929575880659736885,-659488273430427234,-1826602837167579629>()) {
            case -198967120:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var2 = this.G.level.getBlockState(var1);
         if (!var2.isAir()) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s1f90iqxgn97a1","Nu9igmOmTYddaF883MMR+mE7TBp/ETgpOgka6t53i94=",1424811178227722942,-5861308594332057001,1218904550668570878,-8628791147156296744>()) {
               case 477237937:
                  if (!var2.canBeReplaced()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sbrlktujvrrfl","RBnuid0d7TqrOKOiVjI6lFa3VQ2F6ilf1OJupEiYTCk=",-5573451900759513465,-9127971110575839991,1983270159681690600,-5653771752183538121>()) {
                        case -1651706251:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2odotkcisidcu","9KirpJoDrOXZkWzElIU/u2bsMOEg0yL+BMyPZlru0uE=",7663492243123191537,4505104551302017322,-8200529224294109569,797678178051754860>()) {
                     case -892580926:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s3amqlt5nxfans","0w0Ok0tUrLV70ZNimqFJ6wbcfpRvgOm4ZgELEUoDeq4=",8354524267459701784,322923373044871356,-5426299396835390181,-3123165510727818749>()) {
            case -590389206:
               return true;
            default:
               throw null;
         }
      }
   }

   private boolean b(BlockPos var1, BlockPos var2) {
      int var3 = var2.getY() + 1;
      int var4 = var1.getY() + 1;
      switch ((int)com.yiyiaddon.m.b.a<"s1c8nofrndz63m","k/LT4KK7wnt9E2ouSiLMBSQ34057tnlm3CleN5UJz+A=",-5851374668717176178,-7556896829675402650,-3187632298227885613,2587398123567284174>()) {
         case 746498477:
            while (var4 <= var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s6u5h7er7ip5i","tByX4DI5l4ZM+QgkcG+JYeeIewa7TKnsUoaCxxtDCsw=",1156408173487162284,188473416461150622,-4323385131625498345,-3622012004845729426>()) {
                  case -370866227:
                     if (!this.o(new BlockPos(var1.getX(), var4, var1.getZ()))) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3isjjei7un2uu","8ThWSjEor0xi1XGA5llyVIXwxtpRyak9vlTsHRjagJg=",-1737867620567902793,-3302816622331314488,-6124960495327119002,4419805349070504407>()) {
                           case 72663498:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1r5ncd8dzcqlc","uedneSfwiLYkHsxPujEtpaWJCgzfqKH/u7AW3f3mw6g=",1869994396927104342,-8739821244766887141,6467356850017725351,13395851156015243>()) {
                        case -890859145:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return true;
         default:
            throw null;
      }
   }

   private void l(boolean var1) {
      boolean var10000;
      label59: {
         label58: {
            LocalPlayer var2 = this.G.player;
            if (var2 != null) {
               label48:
               switch ((int)com.yiyiaddon.m.b.a<"s26mrbyjj8j090","owXOF7ub9btRte05pUCS7tlU4ryIzxkWBYBnqQmXNAw=",202114425411607504,33773454228033657,-2666744203371671313,4039757358913963649>()) {
                  case 2017857886:
                     if (var2.isInLava()) {
                        break label58;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"szajt22omjb7a","fNZCtyEtt9kqdkDLqzUs9TvwHroASDJC0J6DdxC+S3g=",109731247085277908,7336659197470320792,-7591484050839726106,-5312321139836813001>()) {
                        case -1214862834:
                           if (var2.getRemainingFireTicks() > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s6ii3wnow3f7y","Lb1ZIfRjEQH6lf+tIrcUvVuiJRPopw5/mxKOMg4FkzQ=",3212853169239465475,-222703478197680805,2075133425965240375,-8106011908695388032>()) {
                                 case 61283840:
                                    break label58;
                                 default:
                                    throw null;
                              }
                           }
                           break label48;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s30jp4hzruax93","BNhjwIOhIPtKQLM6RSrJq/OfNUalHriA8Huj7MATvnk=",8927155156050963259,-4152630841058786959,765592941481978029,8974295125086836003>()) {
               case -287230614:
                  break label59;
               default:
                  throw null;
            }
         }

         var10000 = true;
         switch ((int)com.yiyiaddon.m.b.a<"s70vkz7g3za5b","QiXsPXCX8Hb5QgxaZj5RoQkHuOQx4MM9SWxLP1KBTVA=",-6658565702931919116,4776657292477623145,-2087139785751056164,2163588630710157063>()) {
            case 2134126505:
               break;
            default:
               throw null;
         }
      }

      boolean var3 = var10000;
      this.cJ = false;
      this.D.clear();
      this.hr = 0;
      if (var3) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x8sl75o6x5ra","jC2f49UjU+gAaifNiNa+wbbwS6i6ZHzb/Wh6tommNpM=",2634964652886776327,-2375807198835109132,160048363822286763,-619511053754804352>()) {
            case 1753069804:
               return;
            default:
               throw null;
         }
      } else {
         this.iO = 40;
         this.eQ();
         com.yiyiaddon.e.j.a var4 = this.b;
         String var10001;
         if (var1) {
            label33:
            switch ((int)com.yiyiaddon.m.b.a<"s3lcyr3zinamt9","q8lrJnC0bWgSATAZLs7fpIed2s8FtgjrpcnaVDiPAjE=",65980085305170326,-7709155828091380387,355487965459992633,8321812378568258455>()) {
               case 1706481900:
                  var10001 = (String)com.yiyiaddon.m.b.a<"s1s7f6cdy8m0fj","++TCUa/w7eH2iF3pa3sK10WzQ0JYXNYmhwLcLUf7bbCfx9FWAOpq9bH578UnPs9833fnkTBSHtV0ZlQsxoA=",6121263122102578252,-3318140228813338880,-4377354009601385859,-4568439439506707022>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1kgm24qadb6bj","MliwBaV1rw6j7biZ6nIGOPz/xPuPMl9mLqXsJGFIWgU=",-2229093365475498539,-696615724859192694,-3005799479109132381,7552745149891687909>()) {
                     case -1034629846:
                        break label33;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = (String)com.yiyiaddon.m.b.a<"s6rllijzgq09z","vRknL4zJi6pxD3EilzjBwzE/wa3OnU9kheABl8Et5f1X76L/uMJuwhgVZtUjbxsLVBno2Qf/I2eagy2WvQ36xvnx",7426458793536108396,-5822609996501592366,-4039380827831206396,3125439904184641687>();
            switch ((int)com.yiyiaddon.m.b.a<"s3eyfslxr729xo","u9dJnTmWpw93Gln92qVWTLGTC27FDy9FxXO/Oza1Qo8=",-1408342262857799621,2568147665003325094,8342569561884120836,2319364750263218114>()) {
               case 1853355977:
                  break;
               default:
                  throw null;
            }
         }

         var4.K(var10001);
         this.cC = true;
         this.b.a().a(this.b.ak(), false);
      }
   }

   private void eQ() {
      LocalPlayer var1 = this.G.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s156bjhpvo630w","dRZO057JoPDi0E41BXJviOBaGwkMOByJTE6FgmsYpUE=",5079344531386509820,-3512616226092464439,5954105077418930994,1964484487910916791>()) {
            case -1763335868:
               return;
            default:
               throw null;
         }
      } else {
         Inventory var2 = var1.getInventory();
         if (!com.yiyiaddon.e.j.f.a.b(var2.getSelectedItem(), this.b.a().bg)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3b4swvh8nzc9h","oMgtj0Ty88pIrRn2frk51EkzU1eH0teiDy4xCgOer7M=",-8596631345839194002,-751307912106529724,4486280165558640240,-2246823809148877633>()) {
               case -646411877:
                  return;
               default:
                  throw null;
            }
         } else {
            int var3 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s32e27bbn042gr","3J3+WgkxUXrFSJgdEHwK0kopwFTGwXkxp7SvrCtro2g=",771452720161727126,-3736508461526228186,-1900633764090255662,-1900972671260901410>()) {
               case 1410233317:
                  while (var3 < 9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2re9hl94e8xtq","VmPI5bDCyAgYuB5nxRdMa1929Y1ugtg4wxyIhwrJts8=",-8925030510040468159,6560263739370833049,7628939296855009811,-5923833316816408873>()) {
                        case -1442556351:
                           if (var2.getItem(var3).is(ItemTags.PICKAXES)) {
                              this.e(var3);
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3h0p2ndjq3zso","TNlGB8FMyp6CWeXiUIbsfu19534vPOL3TX3fu6ualbQ=",-861469392270208758,-3729276768315633117,-1542823831063580297,170401943407800010>()) {
                              case -1157890130:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1dv16khkod3tr","Ntmj6xFpVsHFCTtxJc6v4S3b7QVVF6lwj7Lok0XCgaw=",2633805578905077526,7131002536307774524,-5136171521788189527,9063398191045861477>()) {
                                    case -2060791737:
                                       var3++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1fpqs9uf3lf6t","GlPw/ToutyFvVTAZTPAWK5cIKOKtr7qa4IST7ddOgSY=",-823765806135431853,-1639529148295958886,-32543725834948905,6022293089307008997>()) {
                                          case 1880742619:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
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
      }
   }

   private int a(BlockState var1) {
      int var2 = -1;
      double var3 = 1.0;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s9gpb66xt9zve","wPxIaQ+xddAGBUdkO3+YoKyfQB+UeCOkuaC5fJisOmM=",-5136537353445163382,1846944950809920776,4980108525338668704,-951637344648779296>()) {
         case -998744344:
            while (var5 < 9) {
               switch ((int)com.yiyiaddon.m.b.a<"s23yxtd2yp388y","5t/r5QYrS6j7UsCgm2Qwzz1Or1+EFZYG5Glj0KwN+Cc=",-2706710680786987527,-2176429201375623082,-5938144336773073334,-6951588943278887901>()) {
                  case -628153164:
                     ItemStack var6 = this.G.player.getInventory().getItem(var5);
                     if (var6.isEmpty()) {
                        label32:
                        switch ((int)com.yiyiaddon.m.b.a<"s34rmu5wuw7s8p","8k0gZEdPG3QC+ek9G7YvHFMd2l1Gi5CVkQb/I3kFRMA=",-8846707366234943508,3085969093412984181,-1967124249269607795,-4158043140662844486>()) {
                           case -2087898738:
                              switch ((int)com.yiyiaddon.m.b.a<"s26jf5pmuareod","BjhiXPqCbJPZl3te8ryzdCmn0miH2dw0Xc5VGHIMv7M=",3201263366846134965,-6939639414201483657,9051869334046227648,-2381007204202436994>()) {
                                 case 596254234:
                                    break label32;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        double var7 = var6.getDestroySpeed(var1);
                        if (var7 > var3) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"sf7qvu5zbadvh","POJrUdiXbXDQ6ngP0XUFYx2fEABsltYFb8q7OA0ELj4=",-6310759871094918318,3139953716144830794,-1987104322599643956,7342805013913899399>()) {
                              case -1434096367:
                                 var3 = var7;
                                 var2 = var5;
                                 switch ((int)com.yiyiaddon.m.b.a<"s19infrkbmsljt","ENYn4nKAzcRmIt8LsmNQfIspTqZ2LyyWAMf6YZi7ka8=",6544025288462879046,4046881449410431324,9212903755371586232,-288627391401043633>()) {
                                    case -1094280069:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"snqgzay7j3e4h","7qWsXCiUrogajV24zGMdEivb8CqiFYOHIllHThjC4MI=",-6673838299155711598,-8873597699140355182,-9215576095731789720,-5235777185223697368>()) {
                        case 812925729:
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

   private ItemEntity a(double var1) {
      if (this.G.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ex4to3o0t9dv","6R+beUPGLmFquaaFCgkEa5bYf8JokUdUNzZk91I6ngk=",1778398925763812332,-7949665534485637713,-3023974168427005595,6076619493630703603>()) {
            case 891979190:
               if (this.G.player != null) {
                  Set var10000;
                  if (this.b.bI()) {
                     label105:
                     switch ((int)com.yiyiaddon.m.b.a<"s2nqs33yvoa755","ks8GRhzGreoYxtoq6n6S8siHzElSNvwlHZIaKSbXLKk=",-6828190961022329920,-1216110265050093061,-8340210794254640699,-2965946135416085851>()) {
                        case -695047863:
                           var10000 = this.b.r();
                           switch ((int)com.yiyiaddon.m.b.a<"s3f8tk4361uj11","EWJ6490uKaQyroqfbGBUmKDmL+iOwHFAxYOtUknUkw8=",-635613413640153659,-5482493057816941022,-8678143670649406049,-2718631314775279758>()) {
                              case 1249289796:
                                 break label105;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = Set.of(this.b.bK());
                     switch ((int)com.yiyiaddon.m.b.a<"soswc27u0bcjg","3a5Ni9jaIEXVbXyKNXhifTreCiDivwJ+/g6V8SiRQHE=",7833013937311259095,-3033359388903012586,-1991146465642675682,7482641078345668192>()) {
                        case -1901235016:
                           break;
                        default:
                           throw null;
                     }
                  }

                  Set var3 = var10000;
                  List var4 = this.G.level.getEntitiesOfClass(ItemEntity.class, this.G.player.getBoundingBox().inflate(var1), var0 -> true);
                  ItemEntity var5 = null;
                  double var6 = var1 * var1;
                  Iterator var8 = var4.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1khp1sxx7e95i","v+3yZEfBxEFVYQSGxz6pUKJcPo0tBb6tSH53NpVquGM=",-3592430708495588105,-6797283717304018476,1654286217119989288,-3608766216128996744>()) {
                     case -1204318651:
                        while (var8.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s23mwrdgw4o4iy","ZfryYNtn1qeqxgkLBaEnfLPLP7568RF7IQ+hpbNYjbw=",2011481461431326041,-181679153017466574,6777168517378544258,-6576402536828125515>()) {
                              case 1872195874:
                                 ItemEntity var9 = (ItemEntity)var8.next();
                                 ItemStack var10 = var9.getItem();
                                 if (var10.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1gfaf530ybvnc","R5g/ZUJAf0778jX4P/W9Ur+S5vhueMEooPZoVit0sow=",-1902769265751774501,8465841022218121010,2174328547562182038,858540690143214497>()) {
                                       case 1256511516:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2bupw0j8anxw6","2lQ0ytc+UlGlYhsmOC8pPDpq742m0MaM9lRgkoybKEQ=",-8485232740789693531,-9022656117411756105,-7321702862656299544,-2391124061590217330>()) {
                                             case -1234626398:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else if (this.C.contains(var9.blockPosition())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3sja0rmht9wel","oc7fYt670G3H8v00X2+0P+3k663Qv9YxE+SqtnqtZLw=",-3543744725790698438,3641702934909620986,4556512413705246921,-2592366779798936596>()) {
                                       case 274628610:
                                          switch ((int)com.yiyiaddon.m.b.a<"slodhm83hxy8s","Or+wD3CsvhQIG36RGHFc8tCpMdT312HYtXZWrmXUlmg=",-6846375453190913822,1357253954934519025,6684729546369258405,8702532309927685935>()) {
                                             case 456276379:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else if (var9.tickCount < 40) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s32gzqibiami77","txqk8VY6tsL5yCHoG7VNHLvh1V20Ng0/TAXIThQtI6c=",2030719923276871193,3581783824222509676,3427901045600597939,-5649601382466482361>()) {
                                       case -1878622979:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1xw83s3nych7w","/8pvYVAaCO/oZxTsSPB1+qEj6xcke+HBk9lbOiqDjD8=",-4871858665120755947,2668424278273580961,5287332453712314205,6594468243729174397>()) {
                                             case -584730842:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else if (var9.distanceToSqr(this.G.player) <= 2.25) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s18u87quvoate","b2fXWci9aXuxUiluotbfQ46SKh6uRMbUdClG1vq8NRg=",5563439642419795792,404046603500284709,3302379464366803495,1973054654367749650>()) {
                                       case -1850621308:
                                          switch ((int)com.yiyiaddon.m.b.a<"swyd9x0rzmvji","ns61oOrT7MtNMYO9Bp5mh88YFQPLYQ5pnGn4RsrwpCE=",2424621820753523261,-8568403502717421503,8048446057964747037,7607509276737873865>()) {
                                             case 650765635:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var11 = BuiltInRegistries.ITEM.getKey(var10.getItem()).toString();
                                    if (!var3.contains(var11)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s27z5ktolydpp3","dHhpR+ZgMjvSvGA3DyJwW8K2wuDgCe1Y97XoHzfOL5w=",4086805709660382093,1281942041693631300,-5332558199099869909,1942703585335514912>()) {
                                          case -872117726:
                                             switch ((int)com.yiyiaddon.m.b.a<"s36gp2n28hcff0","ulM9hQB31CI7NNTNql2L5rzmxA3pBgrIqLJPMNcwgYA=",2596505981497657635,-6921105060328210828,1476982751186985422,5573256026015398490>()) {
                                                case -1442651181:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       double var12 = var9.distanceToSqr(this.G.player);
                                       if (var12 < var6) {
                                          label62:
                                          switch ((int)com.yiyiaddon.m.b.a<"s15ocsjz5btwox","VXyUftuaSJSty0WfvfoNRkPUC/5ErUw+JkfU/BpIYQ0=",3156061298666746217,6675227174039779993,7428305515408694965,2407887833070161626>()) {
                                             case 1850606689:
                                                var6 = var12;
                                                var5 = var9;
                                                switch ((int)com.yiyiaddon.m.b.a<"s9yjfxh3up41i","zoYXbM6Ey9Frq1KHqv0LS4qhcc+EvQ/vxsAtdoflG9s=",-4022315102067945536,-5844882185189030108,2039842107984795522,-8724528855247909882>()) {
                                                   case 1391807598:
                                                      break label62;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2a4338nljp2xl","kjavkgZsj5PkWB++7/ZDeHQLcnsxshkkc5n4E+ih73Q=",-6133503453351465205,7264466536592961900,-2976026389007557572,-2543565407511810865>()) {
                                          case -1707087385:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var5;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qdbbwgw5lewa","zW2BpFa5xT5ZgjqnRf6CalX2Ltll/cwqmegOD1BIH9o=",7785882680169227519,-6129921178719478398,3640549063004986829,-1009530463054156634>()) {
                     case 981131397:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private boolean cg() {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rtkw7t8cydh","BVLWgo4sYYZ5vMKXH4JiTTiQWvfqYZMDemgT4Te/uBo=",8748754983895846513,5674991248384908981,-9102238877430121323,1363264554622112024>()) {
            case -233317082:
               if (this.G.level != null) {
                  if (this.bB() >= this.b.bl()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1dbunyawdik31","WFTNsarnsHnJT4mu4XA34rbkxEqjEORciu5lB0JRIfg=",-8057444224962652770,-390817715106655705,8396700678493171674,7583378267980169952>()) {
                        case -93887490:
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     if (this.iw > 0) {
                        label140:
                        switch ((int)com.yiyiaddon.m.b.a<"s1qh9otecwrsgs","VbWaMAN77MC/LeQOq8NMpwbbePgP+Frq/K6ArLMXNFk=",6802509833021871993,790140084180330315,-1348406819988647249,-6193344537281591401>()) {
                           case -2023266776:
                              this.iw--;
                              switch ((int)com.yiyiaddon.m.b.a<"s21vqdtbak3bsw","2EBqTiShxpQURj/Ys4wb8Mwn++PC3uUfEvjryZssDYU=",2899387690236846010,-5629139965494265448,-2980783251311175252,-1235237721916120512>()) {
                                 case -885000994:
                                    break label140;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (this.a == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sbtda6imdnv7b","usFah62ZAQ55Bnki7agOQ2H3o2Q0mLflPoRW43d/goc=",-647042600828318217,1373033180256858834,5773636594371031169,-3557416762267756007>()) {
                           case -829682649:
                              if (this.iw > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1790ysd5irw5d","oQaPlUiRmt82qoPmyuGeHK2qJ+Pu3ceJoRXEMS/4kAo=",-8237862561833073282,-740193536011173882,8250057393237638845,4883192743587120674>()) {
                                    case -634644884:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              } else if (this.ab % 5 != 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s27h4yacrzeb0k","ckTbUGZS18LmHLHwqTm5xk0fVtbnLVC5OKpa6CvgmVg=",7141253031964484599,7986777272985137079,800653784299530749,5540299123111332962>()) {
                                    case 1917099116:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 ItemEntity var5 = this.a(8.0);
                                 if (var5 == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3gs1k86q2d7cg","ws/GzzcZ+Ykx6Q6uVxvwtLlwH4SlDA8FSy/pzBcrk3I=",2216016202916561708,2608504269522091821,423496194880358163,-6571316656688294627>()) {
                                       case 177882071:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a(var5);
                                 return true;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        boolean var10000;
                        label158: {
                           this.iv++;
                           if (!this.a.isRemoved()) {
                              label135:
                              switch ((int)com.yiyiaddon.m.b.a<"s1h4l0qaxjmbfk","kdfOIF6qc944H5vMLpZNOrrEskdXNSzajyBEXcMix+k=",6898980996658558247,585513855903644330,-8153047356937335985,1221029506683979602>()) {
                                 case -956504212:
                                    if (this.a.isAlive()) {
                                       var10000 = false;
                                       switch ((int)com.yiyiaddon.m.b.a<"s21kdohvqrkfcs","8kCD6wZmuk2UpBADa313Pu68ZltroktEpDYZmCLqzrE=",4708248209370238690,3470241822556579363,2237440663707119183,-5095501416325692944>()) {
                                          case 395616638:
                                             break label158;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2emx8g2pw008w","/Nr8NZEezsfZC/U77oHiHhjy5MG5f9yFwh5vb9emScc=",5019170478278549128,-8652305085402225547,-7389440724795228199,653924002847571988>()) {
                                       case -175505082:
                                          break label135;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var10000 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s25glyzvqvzvq5","6VNo+cPQoNW/N84YuDXENBOhva2oouz4KxMmOcUH3T4=",8051827299890520239,5068618206433999158,-7549667861519794602,-6568245851551556939>()) {
                              case -1091881422:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        boolean var1 = var10000;
                        if (var1) {
                           switch ((int)com.yiyiaddon.m.b.a<"s39d79mzh2tb57","fV8CoiEWsYC38paJulCtS71bAXv67W2Xn3JcHYCCc00=",5585885777111816719,2384758848076954590,-4138254738771165931,4961606404098552909>()) {
                              case 1941140822:
                                 ItemEntity var6 = this.a(8.0);
                                 if (var6 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sggjca9x9i52x","urBNk6jNM3bs8NBOCpXL3q8B2lvL2fRTMcUINVea7VI=",-3038877252132314389,-2770953708808221076,-5862097036682805301,-2061365437194793057>()) {
                                       case -519623137:
                                          this.a(var6);
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.eR();
                                 return false;
                              default:
                                 throw null;
                           }
                        } else {
                           label150: {
                              if (this.iv > 20) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3kot412g40z5w","opXDm0OyB8fgPcC5aVgCMV8MpwUU+MO35UvtYoLT4/E=",-7234166679014263008,8178929485108052351,3933505494194708494,7517797548776088854>()) {
                                    case 1188124455:
                                       if (!this.b.a().co()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2o46w4j6gxnet","BBF4InPkW9s9N1/wt9I6SCfSjvNqXsvDruig2NOZfkc=",-7820803234502554538,-3657543703369878141,-8767241879526795613,5338430643601666317>()) {
                                             case 278661454:
                                                var10000 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s83nt1mrcj8bx","StDFG9YW3jQfufny7GCafduBaE5zvql9Ew1YgSywMPU=",354958929488813647,-2939179087892078582,6031988160241263411,6242892354002731988>()) {
                                                   case -1489802224:
                                                      break label150;
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

                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"sf0m9zcqxbzq9","0mj7h/k2o2Ac0msucx6yDyZ19jXW0HnB5I8J4l13/Gs=",-67550155862385438,8951064642193375532,4215647921712160916,1847156962868494802>()) {
                                 case 952443814:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var2 = var10000;
                           if (this.a.distanceTo(this.G.player) > 16.0F) {
                              label116:
                              switch ((int)com.yiyiaddon.m.b.a<"soxptr39vt67v","6t2B0QmW1ldK902qBrxUjR2JAv3fwHlOwTLFlyj4hbc=",616715786057738817,-8824689621839638726,6978690399727215473,-7693175321106283576>()) {
                                 case 405690740:
                                    var10000 = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s17syvgwba86mc","ejunXa4J0EdQjJKKiNhrHPl4HzilDvifWGArlR8ParI=",3425035709533569324,2725262038544162436,2756476795713621152,3530998418398557899>()) {
                                       case 714345247:
                                          break label116;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s1eircoprl73dt","iCDECHijrzwcVng61Qs1neAUHYuZmuzC/UpUNDig9HA=",1983705909560782017,2711253878365789651,-6594438157427351403,5056052758405275144>()) {
                                 case -1196158536:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var3 = var10000;
                           if (!var2) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2zctixcikr2y","kbLv1Raavd8LiHWC8mJuGNQe+f6oUXswutMWyP//MHg=",-8552416314392791459,8185499743902389361,4368522326717199181,2982872945231771226>()) {
                                 case 1020313804:
                                    if (!var3) {
                                       label109:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3p5679g650uot","SkndjXn6vadcfUiyFYH5SLvYVo1YoCz3e80XbYB38G0=",5595061704464576188,-6658425053757246777,6495221247095260092,7535683705952181258>()) {
                                          case -855859219:
                                             if (this.iv <= 300) {
                                                return true;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s12e73m7za69ie","aDNeC9v13ON2GBC75PgqBBf8+8XVNteuZ0VZDsUu81w=",-2675678156104307680,-3831467019722323996,-4168600240528636886,-7708649777713916975>()) {
                                                case -1984966031:
                                                   break label109;
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

                           this.m(this.a.blockPosition());
                           ItemEntity var4 = this.a(8.0);
                           if (var4 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2iet55sgt813e","yrh8Lrub8SyeHVreTfkLFnHrt3sISFNYm+3S2hWYzTg=",5112610328423349975,4785537902942113324,-5097921712067966498,-4045840335844146689>()) {
                                 case 1072623240:
                                    this.a(var4);
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           this.eR();
                           return false;
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s14jfy3jbzyxq4","PovW5W23vigF6thzV4ElySB8JoShG6VtpYwX0Xpufrs=",-6675496292650260769,4674366028856111113,-7650132332005486230,-5205934420829319022>()) {
                     case -691879785:
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

   private void a(ItemEntity var1) {
      this.a = var1;
      this.iv = 0;
      this.b.a().ag();
      IBaritone var2 = this.b.a().c();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s4i3xh05oqx91","4fyYiKKypnVNwlEve9tzzuFnjwa0Z43bUtQaUDeEBlc=",1693380815648018750,8393271350836310916,1054001897622449829,-7055167639435917713>()) {
            case -967568941:
               var2.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(var1.blockPosition()));
               switch ((int)com.yiyiaddon.m.b.a<"s37co410jbtpcn","+QptjGEQmN8LINOEpmi72UuSUeQqAvj+zZDdbvJspjo=",3984963767204164293,8795477472767741906,-529819573216434291,5144108118486699406>()) {
                  case -2041742948:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void eR() {
      this.a = null;
      this.iw = 60;
      this.b.a().ag();
      this.b.a().j(this.b.ak());
   }

   private void m(BlockPos var1) {
      this.C.add(var1);
      if (this.C.size() > 64) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ip4hwtcjnmny","LyHtb+tTbt3JTZR1DdsNATjlQu+2SbfLH4P3lzYBQo4=",-4143323164903500737,4051483989106899084,1096489897666523694,6506141660874841096>()) {
            case -1680190940:
               this.C.clear();
               switch ((int)com.yiyiaddon.m.b.a<"s2k3mxn5lkc25j","Atch32/nBcnbB+f3tyYJfwrLJVsVgEOzb/o0luBICVc=",7229914699644603316,-5636260991363786944,3787394960187485378,-6739918464450084394>()) {
                  case -1099172188:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void eS() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3feovxhg36x5e","c5576JMPkwsV1D6wWXxZFfvGCtWIr3knc6q+nrBMrrw=",-3482657138144691124,-4794195866440745858,-6971682335877959862,2424013868232245598>()) {
            case 310929703:
               return;
            default:
               throw null;
         }
      } else {
         ItemStack var1 = ItemStack.EMPTY;
         int var2 = Integer.MAX_VALUE;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2e8whl912f8bx","buw/gCYdnsK8TSiXpb/kPqn1mKVsCDQIaG0IjgsS0a0=",3818829603403090613,5558026484174696882,-1687308794034066655,-6728465098406568963>()) {
            case 1712599166:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2l7isao3dmhsp","Z14d1aGJyhXghv2URh9p3wApKImMuP4WNYTAnGwmgHE=",-4107704979539743744,7252570111757072209,-6438633338905055893,3739606223144141256>()) {
                     case 1447061404:
                        ItemStack var4 = this.G.player.getInventory().getItem(var3);
                        if (!this.w(var4)) {
                           label103:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ir7ao06quim0","Rmsv3bJcEyG34l7yVNxDOamRPlb1P24cTPp66mCDkHE=",498760311504503390,-1422521084082596445,6175318110186307982,-1869666262727612356>()) {
                              case -1727670443:
                                 switch ((int)com.yiyiaddon.m.b.a<"snll4mlgy5n9b","Uz4plzOKh/e2jMGe+9Iu1QZnVX6oij4s5oA+5jtcNkU=",7913907899600352127,6030658527732416378,-5186890431882483795,3230692838108536223>()) {
                                    case 1959418770:
                                       break label103;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           Integer var5 = var4.get(DataComponents.MAX_DAMAGE);
                           Integer var6 = var4.get(DataComponents.DAMAGE);
                           if (var5 != null) {
                              label93:
                              switch ((int)com.yiyiaddon.m.b.a<"syilkixf5nlwu","DS9hKq5Dsf520by2LD9gPYS5mRxEaquPRyI+MiylAAE=",-2672575642012593889,5390205669045556623,1321405919641416942,8206201102315576493>()) {
                                 case 839281727:
                                    if (var6 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s74apwvk8e8e7","kFLTtiTUEHd4jmL9rXFuU9llufMGHn8TrUPn8U97Kyk=",3136804395792389347,-7489936994501341876,7621766574987045367,-1429942907930772475>()) {
                                          case 427923695:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3l9ujm7jutsqu","Cbb0JMxcso5EVQnrNBeFBaSB0ZaySsPWQUcahD8CVWM=",-6671088206428885703,-7946514956047475609,2497122899511188876,-3683641244082685276>()) {
                                                case -1943737185:
                                                   break label93;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       int var7 = var5 - var6;
                                       if (var7 < var2) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3cl6o4jq77oly","LTLUEArrU1grgylJ1MSbKGx6vqQIhxdQhBiWgIFqJBI=",-2070176049129288394,2616670481469151860,-1536806492548441838,-7686705636037888642>()) {
                                             case -1871926273:
                                                var2 = var7;
                                                var1 = var4;
                                                switch ((int)com.yiyiaddon.m.b.a<"sm0424rzzyn7x","7t1hTfa4iCNINdmrQGsZrTBwv+d+zDOW0M0AiL2dWEw=",2909069425318652251,-1363358312954531382,8029516933759819770,7356078660558230434>()) {
                                                   case -715653540:
                                                      break label93;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"sgenps8gmyxqe","6FQzgdiuMw78L3oDxRKj7hp2/dz8uvCYHHc3PhAo78s=",5287119564786015711,-4616157561823694879,-1398019604598544733,-1581964165073340912>()) {
                           case -836110085:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               ItemStack var8 = this.G.player.getOffhandItem();
               if (this.w(var8)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1r29nprfdexal","vlQSBprIe4cChOpLKcnh4Nwk6qM4Lu4jHiXvZJFLERE=",-5129892639204559875,-1573080719081786624,1531711110884885487,284542797938144275>()) {
                     case 527695603:
                        Integer var9 = var8.get(DataComponents.MAX_DAMAGE);
                        Integer var11 = var8.get(DataComponents.DAMAGE);
                        if (var9 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1yr802nhaimf9","SV9xHxU3mzaLEj/t26F7QbJilhnfSGieerZ9ibCPcbk=",6816474232032583843,-3510586894630576683,4361785271319270969,-1203726307366162221>()) {
                              case -928015424:
                                 if (var11 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s32jt1h1rfyfvy","6rdenhaIfK+hdxBMi1tWOQ7Fb8c9OC40WkdRIFT90Us=",-1512332932923392686,7815349821162395407,4140462008059852968,-9154909079345714757>()) {
                                       case 590216491:
                                          int var12 = var9 - var11;
                                          if (var12 < var2) {
                                             label79:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1ampvwjcjnm3r","m31aYVu14pp51CBx5YM9T29q+ShkkANHtBX74NsYco0=",-2163244043253828509,-5554002145789341068,8890058290915175194,7351781798614723733>()) {
                                                case 2058023140:
                                                   var2 = var12;
                                                   var1 = var8;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s190r2apt4mfsj","EVYU/v3TCGh8IBcy1bhoIdHBwElSzgS+ZzkqjXE27Hk=",4030769532098295651,-1740273801224233716,8877059864190158133,403905388224733276>()) {
                                                      case -1991036032:
                                                         break label79;
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
                                 break;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }

               if (var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"snxdnp59fkshi","2ktHs5qSmBtPbxhk63Ja4Xi4uxDqWaw8PwuJSbr8XNM=",6960811931157194920,-6654770089182772832,3801268788217383745,4651583083137308757>()) {
                     case -1818524584:
                        return;
                     default:
                        throw null;
                  }
               } else {
                  int var10 = this.b.bo();
                  if (var2 <= var10 * 1.5) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1hf21hwg0100w","LdrR/8WIKIa1yH4cZBD6VDyt2EthOA/tYN0lTlBxlXg=",46688212421794132,-8677829648710882165,-5968227840265555010,6193556201809724957>()) {
                        case -1450686465:
                           if (var2 > var10) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3vl26ugqwcbdq","uTpaa5CEqrPBUq2UJ4O62Y/bMPhZL65s42JFxlYoJMw=",8531486475446269671,-629020641442470704,6900126811916637592,-6068521052471127957>()) {
                                 case -553844014:
                                    if (this.ab % 600 == 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3ujhpqgt1kkee","1ZgBDlp/rMVibbazQ0AEtS4P51lUMZ5uC6Dn83NnxFY=",4314343768935829159,2269926089967969158,9172227125771469634,8176048066106768285>()) {
                                          case 637403757:
                                             this.b.K(this.b(var1) + var2);
                                             this.b.a().fq();
                                             switch ((int)com.yiyiaddon.m.b.a<"s1zczr6keo0ku9","Vorzn6QgngOf6V5UUQy0hAKiSquL9bXr0AqBYJvnU84=",1350321140545677558,7531578272011010324,-7101679327173199388,-6905047744309457866>()) {
                                                case 1400139684:
                                                   return;
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  return;
               }
            default:
               throw null;
         }
      }
   }

   private String b(ItemStack var1) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ipagfhso7uem","5LOmDNWUr5tSs6RSMMbIzBn8GAzQIM8gXjs4eobOYfM=",1037299205211902289,-9171280075415484061,-3763949665919341802,-3718133657795612151>()) {
            case 1590539905:
               return (String)com.yiyiaddon.m.b.a<"s36in3d7px8aq8","hYZvyRV2aseANEn+9mYXvAChOMBQQ/+oWJnl8NuxUjg=",3704219148574690695,8406544705266660993,3078945226228247195,-7174668559637119407>();
            default:
               throw null;
         }
      } else {
         String var2 = BuiltInRegistries.ITEM.getKey(var1.getItem()).getPath();
         if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s1egwrud7e9x1k","M3V1Z3LN0wQz98M71LlyexoCF4GxnnX8XowvO+gU1bOy4GxXql7uI25grlY=",-4798133136666810226,3402916450350093171,9193832565369945874,3479632722195610649>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s2suhumft4yoh8","y7iIHkK2oQbLs+W+AsPCEIxHDKL3DwRn3sdDWjjfQ90=",-8339753100060470246,7603867804970552091,7548941674867865044,5031241457557916992>()) {
               case 870163894:
                  return (String)com.yiyiaddon.m.b.a<"s31h2i2e8vb2cq","/1ZZoPVEq7PBiK4ayxDD/n86DlEUwb2zcR+i6HRxle0=",-1244038557055264082,-3300584587155936533,1357074389660452571,-5363409349915408925>();
               default:
                  throw null;
            }
         } else if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s1pcot52pvnzf9","aq6jfmY8PnFq12q1TsO98O3zX6aFvIhNYb+wz68Yqgl5mr2FmB0H/2mT",4777041856529764652,6122033436622900576,4792251822365445244,-7762378468675245595>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"ss5e0mymdcz14","Y1ntroW8rt4C4lXdTz9me3qVzc5zI5aqmJgqZJl4d3o=",-8922890093613465968,1521048677099347062,-7954337399900446641,-5036989292636813964>()) {
               case 480898002:
                  return (String)com.yiyiaddon.m.b.a<"s23l4s7fout72m","j96dyFf1i+eIV94D7j6q98+sLIVSi7EK/i3KAusXun0=",-2126722476594101263,-8669748714190765071,-5951527475976346732,-8005623515344824704>();
               default:
                  throw null;
            }
         } else if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"swzgke7cvlywf","NUA3dsPqd3tFU3UO2/jGSdYrc4yVgJfnzrAtIctV4EYfjAcq",3052580125312492911,9003483517307543433,5944124624409338849,-5337822837877182393>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s3u172tsobw7y2","JZriuWZMQMWA6DmMKfJTDBUPIeIkHNjScsS0boVKkCw=",-1479951809143567206,-4871877408071805074,-8677216301857331878,958898237306406883>()) {
               case 568028996:
                  return (String)com.yiyiaddon.m.b.a<"sfgj6ofdikfir","WDR1cvZ6VRqSc6B2+53piCqpymIATzD2qmBlGgEGSoU=",-2016663426480890887,-1014586440096789036,-2239184711761481107,-3143736703811279229>();
               default:
                  throw null;
            }
         } else if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s2fj31hrzmju9v","b9+CiRcrQ71LUFz2pjH1DB8CL+br/iTeS6TlI5RzcQ8lqHkQ",4941188968491097763,4557235653371501505,-7343985028105800865,-8828452674186514088>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s1l32ksvvtfozl","dDpucFr77btEnjAZNuV5L9+cvofsL/KZrH0obKh8lMo=",-5793420377779727533,-7195648405437324318,-9191979530608550708,-2816779221960080785>()) {
               case 662558955:
                  return (String)com.yiyiaddon.m.b.a<"s3ubn7z2uxzu90","D35xN9EZprpubvaE9/zDLOLKdYb6E6D60ZXxogerQW0=",-5675065408595988368,5527733171684718485,-5624750928405932971,-7934329110317918246>();
               default:
                  throw null;
            }
         } else if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s2px7kuvaazps","o3KKv1C+OFLqI557/tMsDZ62BqAxPb+RsfyqolE8hsU/b9EeH+rxIw==",-2189487092590524475,8812490183552326748,-1374139359943931453,-7610460197100915876>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"sodjyhxyzywa4","FS0pL/Pdb2PRYPk+qhEXgdVCgIWVCoLGh7AUFIpWK6c=",6890967225472638712,-5291059588730990235,-1459031520170926353,2906860208925617015>()) {
               case -934011028:
                  return (String)com.yiyiaddon.m.b.a<"s2tsnsdn3d2oog","CGIGKfX4rVrqzhRqXh8QW9Du4Bkp7nBFItB5c99c",-383722688652690512,-3686070172034553730,8403424711606943434,-7077069425317940743>();
               default:
                  throw null;
            }
         } else {
            return (String)com.yiyiaddon.m.b.a<"s36in3d7px8aq8","hYZvyRV2aseANEn+9mYXvAChOMBQQ/+oWJnl8NuxUjg=",3704219148574690695,8406544705266660993,3078945226228247195,-7174668559637119407>();
         }
      }
   }

   private boolean p(BlockPos var1) {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sejys3z938hq8","s5nD76xxMmU/S1+6Copj+HWYbX7KjMmhCBHpn7hpccg=",-667285088628924860,-323989216973056813,2275589613744349603,6073498585528207692>()) {
            case 2051094228:
               if (this.G.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3g0daf1wpy6lv","++2+IpjwlGFvy77cyceWs1NpV6DA386LdUuUDM6S6+4=",4016119954290290630,407869953001120438,7060624677194523172,870014953084893234>()) {
                     case 1316947630:
                        if (var1 != null) {
                           BlockPos var2 = this.G.player.blockPosition();
                           int var3 = Math.abs(var2.getX() - var1.getX());
                           int var4 = Math.abs(var2.getZ() - var1.getZ());
                           int var5 = var2.getY() - var1.getY();
                           if (var3 <= 2) {
                              switch ((int)com.yiyiaddon.m.b.a<"scb64z0t0mkeo","xdkXt8PsGiRK+jIXAX1yg7J/Q2gNLm6YGYGz9fYVrJQ=",6918631022314467911,67590904986238856,-1245352660766165682,-2975412847391477884>()) {
                                 case 1514707126:
                                    if (var4 <= 2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s390plje0q4y8y","/HUIYntTJRHZ2GuOPncQG0Hks2QTuvfQueBKx/Oa6FY=",-2579497818575811708,2550056440478763206,7630970771434500977,-7408265347111996547>()) {
                                          case -1401381997:
                                             if (var5 <= 2) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s23cjqfa987ko","B/ha6N+w+fatrApwhbyYgI6chcoFczPJ2+0HBE+zQJQ=",-4705804726139690942,-6580865987072892085,946540436339104622,-1614638239398186802>()) {
                                                   case 666685826:
                                                      if (var5 >= -2) {
                                                         if (var3 == 0) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"snn78jmidlpi1","Hhv8YqfvSx6f7PQFdXvjwqIM+7xd8XiG//MkvJ/OFno=",3747191860572235891,1366613836409884736,-1757883851732128779,-174529108958411652>()) {
                                                               case -1413250499:
                                                                  if (var5 == 0) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3dkwcy74iz7dl","evlGPYBPqhHyEvxdIM3e32wEYGVRAq8BtTXVYzAFdrg=",-3487177263529436444,8226111602455372509,3269774403975665048,4181392520375339315>()) {
                                                                        case -406116633:
                                                                           if (var4 == 0) {
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s2cavxws86rga5","cTrrWO855doFySq8w0aMAC7QPDb+dVvLZc9cHGRwuPw=",4054524041921920988,-3140874238901060703,-1956264079368549221,1033126302136215648>()) {
                                                                                 case 2094590900:
                                                                                    return false;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           }
                                                                           break;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }
                                                                  break;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         Vec3 var6 = this.G.player.getEyePosition();
                                                         Vec3 var7 = new Vec3(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);
                                                         if (var6.distanceToSqr(var7) <= 16.0) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s13mapfobgo1mh","rTVpnMxGaD9T7db44Ga3q9Sv6L07VqbnqbnS3HJFc4U=",-1239939528293412418,-301315316651156207,-8362619207311185686,-1715787920926622582>()) {
                                                               case 841945585:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1amipn0sklzcj","ZZOGMVtDouLpxjUQNP5SzeYoWfEMXK56xygoljMw4V8=",3186074066868614886,7991162401002551041,1272926782904876421,798962460696798920>()) {
                                                                     case -644862380:
                                                                        return true;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2pask4abw24ei","oImDfYJegorGQL0cb02OGyS06EWPu13OVwO+dchuwzM=",6554340765669050395,-7534415970786145961,3945947080885756166,2904523229922876893>()) {
                                                               case -1832219549:
                                                                  return false;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s37t5jmqhgl6yf","BIsPW3akHm2Ym76E5svkpslFp4HPrX1gSTQQE03irYk=",5980027948107358180,7576041083041458685,-4572618828738447357,-2249437290273782482>()) {
                                                         case 1735725347:
                                                            return false;
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
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3mvzkwzjj6g4h","iB9++ptcDOKF3SEkTkXfqwFYFR8aGvtb0hr87m7T7dI=",6453631491904126580,9220132916159554615,5234508883699964109,7307354301882608288>()) {
                           case 117532626:
                              return false;
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

      return false;
   }

   private void n(BlockPos var1) {
      IBaritone var2 = this.b.a().c();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3q8twi10t479o","gjVQq+zFG8g5xFhHx3r+A6I2MUW62Hk1FGCTc6tGIjM=",-504370793996078909,-5224695556467964201,-8032458053774110159,-9107331903725494703>()) {
            case 1601114848:
               var2.getCustomGoalProcess().setGoalAndPath(new GoalNear(var1, 2));
               switch ((int)com.yiyiaddon.m.b.a<"sms7b8j9k8nx2","C3rCY76ivCocIvQFQUUjDn1xs03U9Kywd8k/8qzqQEI=",1371160532553285808,6631985531874520557,1843166543922359271,556874242514275301>()) {
                  case -248887101:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private boolean a(BlockPos var1, String var2) {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sm0jn6n4dq0lk","3CQnVMv4HgxKE4gV0wgfTLBDcr2+qNCCE6dz1FXQBGY=",-6157311037454866370,4232879687238082658,-600525507230576705,5713841835324885962>()) {
            case 284324285:
               if (var1 != null) {
                  if (this.ab >= 60) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1wnm7fufeo4fu","Mu/dteXbVSXcX0TeY6KioQk0gbp3ZONIPaN2JlPiQZ4=",-7101913202821606989,-6289150020055286337,-5467163736095117364,-2597222561164399818>()) {
                        case 910193103:
                           if (this.ab % 60 == 0) {
                              double var3 = this.G.player.blockPosition().distSqr(var1);
                              double var5 = this.L;
                              this.L = var3;
                              if (var5 == Double.MAX_VALUE) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1077xt1h9t9oy","OajE91iwVzgY3XdXAN3ar3r/gDg4ATGjzisdpHbOPJg=",-3533954271288585618,-4277202051554345793,-5355945584585841895,-244804101201467551>()) {
                                    case 1918272776:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              if (var5 - var3 >= 0.5) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1bijgjcnzbli8","C+mg2vVCg9iJoYRfEDAJxVzzZgW1YCpc4L5vNTpq2RY=",6089110220395488268,7393285021962784178,-8672160125304940652,8990242851429192112>()) {
                                    case -628328012:
                                       this.hx = 0;
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              this.hx++;
                              if (this.hx < 2) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2s1ro1e5gths5","7i4rxDPZYj0NggrqlM8oRCw6TI49OvZDTlzVjL/uMmw=",3013085705382794935,-2905878076644871091,6476634841870693396,8165452831965829618>()) {
                                    case 111628438:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              switch (this.hx) {
                                 case 2:
                                    this.b.a().ag();
                                    this.n(var1);
                                    this.b.K(var2 + "");
                                    switch ((int)com.yiyiaddon.m.b.a<"s2mr1tr51p7g5s","04uInGF9kjeliiPdOVNQN+YgqZWELVFMcvp/Zzanltw=",-6627537767574975534,7481131884980693770,-1927639020556155640,4054451765859260661>()) {
                                       case -1573334805:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 case 3:
                                    if (!this.b.bS()) {
                                       label50:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3grmmenvt2d49","XpD7nXi1rXOJQyg6jfkmddDQY7qbL5uqQus1GeSvmI0=",6335866194486743335,6097288672841493034,-3127194480489084381,-3967969160971149750>()) {
                                          case -1674216756:
                                             this.b
                                                .a()
                                                .a(
                                                   (String)com.yiyiaddon.m.b.a<"s15e6hyd1lbzu8","sWntOCT6CMKEBo9MQLz1xljOOYWVEOVGatuIs/HWLudZPT5cblnKEfwwZBT3KzCK",-1556488207012372422,-7282443142352647503,-8053149972278958931,-8296424199670431334>(),
                                                   Boolean.valueOf(true)
                                                );
                                             this.b.K(var2 + "");
                                             switch ((int)com.yiyiaddon.m.b.a<"s140crzjgplqna","SxIJXRnQkDIPRACNLuiaIqXx75NvyemkmW34UhTISQQ=",8114313293999202860,3868701059722494656,-6896356959139706769,-7969058234096990196>()) {
                                                case -2025795770:
                                                   break label50;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.b.a().ag();
                                    this.n(var1);
                                    switch ((int)com.yiyiaddon.m.b.a<"s3bz4rvewtj7yq","3C5UQ3dUqXAL/HQ8coa2UZiuAaesB08F181SjE032PM=",-8931679867627017030,477908182483607263,-6167683572938177121,7539914994050725879>()) {
                                       case -1450826065:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    this.b.a().ag();
                                    this.b.b(var2 + "");
                                    return true;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2lr1kfkcwaiq5","RvFkYEW+xO/zKj3jaUwymKmCP6yyzvuKJFnJxpwPxmw=",-1929807357775949794,5363286601550810330,-1970438008195665111,-1554258869944552772>()) {
                              case 2092941939:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qyzzyrulypnw","X0SHhWzt69y6pLLKuYgoR9bcsKwLCXCeqmBq+WQ1QJ0=",6767757433455368126,7116167412672393384,8368760313463804431,-2052203175713254784>()) {
                     case -2116337436:
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

   private String b(BlockPos var1) {
      if (this.G.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s38r98errkmotl","l+sCtMHAB4bG/p8aaHrD1yWNg5ADe4rDc/KpXwojgoU=",6634687832835745369,-7788674570967625991,-1307978608607988324,9184282058879466026>()) {
            case -1419949685:
               if (var1 != null) {
                  Block var2 = this.G.level.getBlockState(var1).getBlock();
                  String var3 = BuiltInRegistries.BLOCK.getKey(var2).getPath();
                  if (var3.equals(
                     (String)com.yiyiaddon.m.b.a<"s3jmc0uu7e27cq","4PhWnWwIf9+gVkt11sPwQmHxCcL8Wb4WRraTfOzEH7qKtFUiI63qSgs1h0gUo3xL6HU=",-8817678568307633202,-2460236460745215323,-8328189965232457689,5644353316365179993>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1o15butjmus0l","eamfZH6B8aFDQ5xHomPHSJI3jvEBbTQ/boU5dFHPgmE=",-1060314685715703892,3603536017330253378,8507190460509600582,4702455350660697977>()) {
                        case 1539581162:
                           return (String)com.yiyiaddon.m.b.a<"sjnyz8x8236hf","8Mans00TIXYR5UumjvI41fhPcnt33Tv6d4OsyaErKe318vAZx3+9CHAg",-5830464343082252323,7582057305427559871,-5722070524323127395,9088627973898827092>();
                        default:
                           throw null;
                     }
                  } else if (!var3.endsWith(
                     (String)com.yiyiaddon.m.b.a<"sx77yz8pl0fsh","GBZi0vuEoBbYJ5zjH4GV6ppf31C/4Cl3f5Qz2Er0SlhdP1JgT900eKG7ab/6c67D3Yk0Og==",-8832939276624529427,9033879735932588709,8988125955129894633,-3600345323831191360>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3l4i59urjwzer","ZfFOENtlphclaE/YtvGrtyVCrpCFI9JANYb2OcFLsUc=",-3319043566299220599,-6521476026525360243,3535673893089530757,1063465333757644226>()) {
                        case 1080994738:
                           return (String)com.yiyiaddon.m.b.a<"s2gdc5q86zobeh","mQ1RODzCR/pYRsMotUHHbWEP3CgsdnsRaphrBpC+b/DxgxqN",88966484981562610,8791164769809219516,-597532667381743160,3289991088973220111>();
                        default:
                           throw null;
                     }
                  } else {
                     String var4 = var3.substring(
                        0,
                        var3.length()
                           - (String)com.yiyiaddon.m.b.a<"sx77yz8pl0fsh","GBZi0vuEoBbYJ5zjH4GV6ppf31C/4Cl3f5Qz2Er0SlhdP1JgT900eKG7ab/6c67D3Yk0Og==",-8832939276624529427,9033879735932588709,8988125955129894633,-3600345323831191360>()
                              .length()
                     );
                     String var6 = var4;
                     byte var7 = -1;
                     switch (var6.hashCode()) {
                        case -1008851410:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s3ok2oibcfv168","DIK9TQB2KVycKLW1L3oyqfb+EnXX4PA642eKOPskY/G16ud/Gq/fQw==",-6267608748816173010,982873350588492907,-7597090391119093788,5482648289942569662>()
                           )) {
                              label221:
                              switch ((int)com.yiyiaddon.m.b.a<"s2iai6zwtte4b5","jrDo/zwiUrTrYp4cPcAr+G02ftUly+2CXDdFcLDUty0=",-3505345458607488524,-5143283854930359054,-3750161709191244221,-1435836769099809107>()) {
                                 case -1974346684:
                                    var7 = 1;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1sy18ihpt7zbb","P7L/bLqfVZ6Pz2mPGcJvCHY5asZuOABICq91PybDzIU=",-5131052056424943862,1924715721699693972,-8118721932753214182,5196399552474679241>()) {
                                       case 1382799279:
                                          break label221;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case -976943172:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s1pzhdmol3lfrt","/2SGbnjvmKMkQ8xFVeKnJlKciKQWAvjTv5Q8oXXmtonWJyLVoWAIEg==",8451469107245964311,5959725658363830518,-1333277711015302690,7863030628573606950>()
                           )) {
                              label232:
                              switch ((int)com.yiyiaddon.m.b.a<"s31o7nrrq2mhqr","lHp3ownjIJMKeQdhbiyA5mewuXvAnrTbBhRhwWC/1m4=",-3672600265618239467,8973479936490765388,1870353209158821059,5598101911368100809>()) {
                                 case -655308739:
                                    var7 = 10;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2mbi56jsnoju4","ufxYGePasocBomyuSgDSFdrbuasZ1b3ZHEzEGHo3tCs=",-3691475740566291004,1957647388303590726,5207883665412180960,-828150342794176352>()) {
                                       case 1610721720:
                                          break label232;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case -734239628:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s2zctyqxht0zdb","DJzD01Bl5NlzBG/LGuQ3/GpYSLtHq8fUEEiEGFOm0vuNCmx6zykjpQ==",-6712392595504485565,-2453426555422083870,-1655148206085804838,1320233999695294639>()
                           )) {
                              label193:
                              switch ((int)com.yiyiaddon.m.b.a<"s2e995xj3syh4a","1VFUkQUBld6JTY5CIlFt3iWsS4pnD8SL6l7CtmTvOek=",6966938129287841817,4517508510112670078,-6424017600450678929,-8112758320410536365>()) {
                                 case -291281963:
                                    var7 = 4;
                                    switch ((int)com.yiyiaddon.m.b.a<"s196dhrck7cvxn","h7vdeS0OOP5MSk88MFSnB2/5m3zdvZRPWM33e2+nPZo=",3082388591304692687,6473290197575368364,-3627786244061934276,1243140592014246257>()) {
                                       case 1874939785:
                                          break label193;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case -209096221:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s37tj4orydqtjo","8UR5i+8fi2Xw1G5RJEKFkarP6Z43mFxC4hkIG7s04uqLCSFUKZ4FmCGMwsVm3AwJ",-3195916029180046649,5342105300267123194,4268583592168925630,-4276626396369260877>()
                           )) {
                              label183:
                              switch ((int)com.yiyiaddon.m.b.a<"s1cixrwl0m42el","+WMm7+UsswpBSt4UCpGHNdd57PSwoxxa72/tpyt3hSo=",2198778756440961194,3369277382895090836,-8821214332497358780,5654752842352571490>()) {
                                 case 1105226086:
                                    var7 = 3;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3by1ytu9kliak","CrlAFrRcDV2PeDk3IoYNe6gt8qEMsmWvpXI39eCjGAI=",-8132093891105233721,-7414222488803829071,223817405747358063,145242632566686447>()) {
                                       case 78354094:
                                          break label183;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case -208942100:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s867rcakzc8sm","Jp7UzNacXU7FrmWNacwuI0Z5bEHMF4A+gjxtE5+yT/U6eOmXR/3CeRp6xzQdL+Qt",-1313349669333707956,-6624780073571357382,-1875859039715130431,8400510732976284222>()
                           )) {
                              label180:
                              switch ((int)com.yiyiaddon.m.b.a<"s2tgbzvprb1wv9","q9HnECwj4Y1NNQG9YwGMQjgYiuyUD3Nqo1OkIT+3t34=",-3250258958988604070,5940478688271998947,1655808690279745055,1591548964695266434>()) {
                                 case 1224284205:
                                    var7 = 8;
                                    switch ((int)com.yiyiaddon.m.b.a<"sp7hqd96wpuk2","36zXKJemWN4U9icPYi0V6Nc6YRZSKM8cmngMIvZgtFM=",8671375609367962319,3106467606329250672,-4437168440315276272,3372819454875095542>()) {
                                       case 2052465209:
                                          break label180;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 112785:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s3nam2xu68r674","7FUjBdDLbSP3MR/K+/8aKYiKbCwzHTgaxx3/Ah5SxTxOVQ==",7802626222070007148,1396819580654461198,542603178776689649,4289906508148009436>()
                           )) {
                              label190:
                              switch ((int)com.yiyiaddon.m.b.a<"s3iugbxinhmg7b","WAQrrEanyRe5d50Gb8RRORJoD+pWHzucXafxrVl6syo=",4284626036422359656,1087463945599762203,2185343840830385291,-6243405914537342444>()) {
                                 case -1326889959:
                                    var7 = 14;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1k0vphxyanavv","45o+nmkhj9ssSqS5+2oSla1jk0+8hBObuLovpKPc1lc=",4395831237655673677,4318127513102669900,2449134024141710216,-3249696034415548579>()) {
                                       case -1940032076:
                                          break label190;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 3027034:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s2ztm57yiqu8xz","JT44qxRgyRqTK+9dr00Ghm3iYvZmt+Xxt6CoBdFF/BpCQs8q",245881612603595489,7273731601524940821,3713818235248746459,3701243834241250720>()
                           )) {
                              label236:
                              switch ((int)com.yiyiaddon.m.b.a<"s2oixb0f6kvknh","pkbt5ViLoIMm6tUsd4j48AO0ms6lwphiosTV0+hPJb0=",4049520462342259681,-8703491768346190427,-33118880818823074,3829234360724733453>()) {
                                 case -1846602957:
                                    var7 = 11;
                                    switch ((int)com.yiyiaddon.m.b.a<"s8976jtbjbc2g","Tb6rq9LQtRaY5/SV9nuEimMKiDHNz6UM5yscTSrvElQ=",7440967808084793738,401995151475180356,-2053442780957384834,-2747403567369564178>()) {
                                       case -1276254211:
                                          break label236;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 3068707:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s19u5pajbtfzj1","M+EOXILTfNJmBmbLp0e+awsrKe5vglNLmMTweb9wwZg3tElR",-571811953600319196,3362333329537394435,1509795294322576075,-8724613028148128706>()
                           )) {
                              label204:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ue30vb83nitw","wTFTW+wQEvQrm+hLtXRhd3gMP6qteje38Dxi5G0edOQ=",1097996868949440165,80662774818640445,8805202877399661832,-7414761694295976389>()) {
                                 case -827356332:
                                    var7 = 9;
                                    switch ((int)com.yiyiaddon.m.b.a<"svzjowgeeiito","jhzztjkOMtmadi6pZSSDUwwLtQlKl65Ij4lVCTGZk0Y=",1547980619028116388,4894400804607100083,6675429681961956357,5349338458255760079>()) {
                                       case -1862241922:
                                          break label204;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 3181155:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"sfwgwzndidey4","tiHUWt/usjQLv1vUBtdFnk8ByrbtMB9JSttHdbLuDkSBiY6v",-619893314283182112,8360997535394111544,-7716716644295925438,3836761828585391647>()
                           )) {
                              label213:
                              switch ((int)com.yiyiaddon.m.b.a<"sc5ynct4zgo5g","gtaGZtP6eWwdoC3hUa9S14iyri+goeP9DhevJumvs2I=",-8038758797973763729,9063511580164867936,-2541717966118971697,6399146371213058207>()) {
                                 case 856275437:
                                    var7 = 7;
                                    switch ((int)com.yiyiaddon.m.b.a<"sbsyphata4cmz","7MXPW0V8Il1C1cAUxlB7ZkABRGSu9HXzKlj0lz13rAk=",-2699854322496344289,2656441365980510170,8445905426878043180,3993664259261074457>()) {
                                       case -1699191164:
                                          break label213;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 3321813:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s2b779yaq9tlk3","oxf0NkD5MNhSHnMQGmWmdR8s4qN+BfKuOqOu6Rfi39gjnnaU",8174049043179135825,6572514146011192106,-3857086392291416342,4764989626443514947>()
                           )) {
                              label225:
                              switch ((int)com.yiyiaddon.m.b.a<"s2tqzsohcef921","MOFfPVI69o7OXtkeGkX0vIQMqvN1yUSHruj2s1KkHKY=",-9092873230440165496,-8042365080433177513,5483654601851652882,-8733122287588658047>()) {
                                 case 821748565:
                                    var7 = 5;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2h51kknrfl18k","oreALdw9X0TArzUsQuJhZX0tn0XBcogqVEdXRUST0wQ=",-73921223657061917,8202726168752745844,-3612845214411259774,7796479385029414619>()) {
                                       case 1595608870:
                                          break label225;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 3441014:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s1813frl8z7vrp","bJCaULJoeFh3OaTkgLYdBjsr9XaVKTVDsjEQlBmRWD9SnDHi",-6865058724589515020,-4958778720215531102,-1698045583844103961,-5888669662065779701>()
                           )) {
                              label187:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ee13f6m1wds4","BZ2yENoLE2facHAY/Bs9aAeMs3MLS4Gti+LkKMA5PYY=",-6983997975269950724,-2455453059493084032,2800045624550633612,-7981659680361869764>()) {
                                 case 447370530:
                                    var7 = 6;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2xw481f43o6tj","zRGg/mAErTIMRSVtSWVWuhIO9B/B5TwypLVWq8q2shs=",-7042308692983291244,-3850549777282668077,-1436103092178051805,3372751693453758765>()) {
                                       case -1057266826:
                                          break label187;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 93818879:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s1ogvfjaz2c8ak","9w4uhWdrxWjPkKd1VZqg3J+ZbtpjQtL12UPoYSjwGsaI8YKWmZY=",-774929159422479439,-2366965964648245243,-743548455218753075,-5281678742351161608>()
                           )) {
                              label196:
                              switch ((int)com.yiyiaddon.m.b.a<"s1zyv2c799vg48","d08k/0bEjEfWy/aL+/EO/pqMeu3vkNdq13uefbKa/nM=",-2063469994118381588,4959756931629553991,8470964827840187445,6821284261871141764>()) {
                                 case -1201467574:
                                    var7 = 15;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2smrkrzblxrx1","9fIkc0nFiDlOCtqRvnIQ8yfTJBQj//O/aE4F8qRAXtA=",7956938868462237280,-1802665260312584178,-850326129058421224,-5096824514600564509>()) {
                                       case 371716510:
                                          break label196;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 94011702:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s39maybyik0z57","sbA6LH9TBUan90zyxjst6Z8tmDQcsoXbiRlujki1g9oQQSiTYW0=",-6294999961160680100,6877714083323792193,-291519459130362085,3620515061595620899>()
                           )) {
                              label201:
                              switch ((int)com.yiyiaddon.m.b.a<"s1gd5ihj6ypuac","c3ZHWu7bfF9/7dk/HINocW1nThJFtErJ+iF82fWGEC4=",-1933097482846833475,-3734597783896210905,-5896316489182945974,-357292605159676881>()) {
                                 case 1078535697:
                                    var7 = 12;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ixfi7mcf7ubz","bmaa8hrG5JKtUupE8osCLZec+qSP8IwpRoSJFRe9gPE=",-8590020171503792191,149037102439894748,-3025421701797842735,8019452186225888944>()) {
                                       case -778744346:
                                          break label201;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 98619139:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s1g4j0witucamk","+OgxJYZYjtvxiEbelOPhMy7Wvm3zfvd0eSUbOuTWeQJvLyZ5BxU=",4565922054296868202,-3125551000454047624,-8446513608914632910,3296721367501926958>()
                           )) {
                              label177:
                              switch ((int)com.yiyiaddon.m.b.a<"s2wtu9qpcgnxxe","z1DW5SfnL6MZcQnHduecjpBXZH5pQXqZFYbqdP5VJPM=",1105920402924757303,-8018425833959232364,-1875804006830085090,-7092109738376973620>()) {
                                 case -1488548333:
                                    var7 = 13;
                                    switch ((int)com.yiyiaddon.m.b.a<"s29xctfa3p3vev","bwZgaZEtmXMSM2bOyqGZQfzIWpy6drHkL6CwHgonDPc=",6372652130151694758,-7069024341431375513,-681535589760483617,6921185078332920511>()) {
                                       case 1048482992:
                                          break label177;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 113101865:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s1bkz474rwpham","N2+QZMevkAAtAoSOdI+/BEKDAzB4PR/+xwK9mbmAedP5npRw5IU=",-973768154221599228,6869328347696226463,6773308141658570575,-661148117649352920>()
                           )) {
                              label218:
                              switch ((int)com.yiyiaddon.m.b.a<"s3hxy33yqmf7i6","ZIwad38Omg3+ENnuL3VcLMm19e69RFonRK0LPT2p8E0=",-6735601045144033973,-7328594212177909244,7778448856698771575,-171830444546438706>()) {
                                 case 738353242:
                                    var7 = 0;
                                    switch ((int)com.yiyiaddon.m.b.a<"sy78he7q83csk","zICzmNbb2Tzfo7HrNVRhd1tlRceGmFbM20r1LvV03lE=",5016574689750189663,-4106714062250593449,6255162331945359570,-2996308120049488969>()) {
                                       case -737790589:
                                          break label218;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        case 828922025:
                           if (var6.equals(
                              (String)com.yiyiaddon.m.b.a<"s1jao3cff8v49m","y2egmOFi4XtfWJwwi+Uu7M3sc7wA7x17rRO/zgqjUgvvrCh+BnHPcbxE",-7282483669891758534,6939612911733818182,5779573062566208751,6965740310334513561>()
                           )) {
                              label209:
                              switch ((int)com.yiyiaddon.m.b.a<"s7doeyoag56jk","Pwnhzz4d+6nPpL0W48jNWZLjkYt4/rkaGm4c7Qdi3rI=",-822251107947465762,-6124883291982709452,1379670706328653348,-123223926338876688>()) {
                                 case 755946242:
                                    var7 = 2;
                                    switch ((int)com.yiyiaddon.m.b.a<"s17x1ly8dfp2zm","Q3FHOTUuvgm5LZBltE9ozUnU0EvlR64//nHHoVkd1RY=",-2634588638775789317,-8975708613843504656,-4377139623982460172,-4256179017352828575>()) {
                                       case -551556257:
                                          break label209;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                     }

                     String var10000;
                     label173:
                     switch (var7) {
                        case 0:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s2js0mhospe4pa","Yx8342bnpSCTtW1GnQ+K2UnDQ6IKJsikGgx+ybytPHk29RyE",4576795859332603448,2620578107669385308,-8093681815258913033,-8831906532562043016>();
                           switch ((int)com.yiyiaddon.m.b.a<"sbl2ll9nahw9m","iVXeqdhI2OLfS3T5qmQj6BeiDUoMkmim6H16c8Dye6Q=",-3944811941780503198,682361106383806127,7144213204880972653,8382519780803570215>()) {
                              case 983444892:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 1:
                           var10000 = (String)com.yiyiaddon.m.b.a<"sjnjerne5uutj","inP54SlYmGwVOravM7Ucx+UyAjOWE6ON2ODczK/h3AzSRIoV",-8808436129788552675,7665202444598430845,1355436529350166751,8729902162469158385>();
                           switch ((int)com.yiyiaddon.m.b.a<"s14mqftlztkajp","jqB2my/dxOxQklqKlCxb+4TSWhQKvYWYbS6sESGO3HU=",6755009759034968123,424737461528703187,-7661495666509261540,-3398415400905630556>()) {
                              case 697728348:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 2:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s2sq8v7fzrzznk","P8+neTkk4gBqbp+JVPQMptAsIly9B2gd6NF3qXvqGc7UXpnE",8328607006524377528,-8904130398055353481,-7324289152124176308,-4908349157426727761>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1jqcg27yb7uwy","4o508QGxA01JHTV019wZ+3uhIeTvqZ9v/mjR/eO0dzg=",-7109351910838741695,6581629834314252345,208374603239742497,-5468905793486807752>()) {
                              case -1515424291:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 3:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s24ejtpfnep5vt","sZb0aREbKpXGj+zECXo4IDN9xuikk0dWg1nTy/QX7ZzYobYl",5522817604407563422,-7717041609181119319,-1808043720643325238,4641096008179287506>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1q3wdd5526puz","FCQUtzAE6cTgZRyUU5hTTQPvWtJR1bNL7RK7qDQ4p6U=",7325377813582103138,-9194542748005659465,6833189885507100392,6321543330886090510>()) {
                              case 216127160:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 4:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s21t636a3v368t","JhePwj7M+fP3QDt9VU1aIrzrbHeUgyU6zuwuoTZcBqdsWTZx",6102817339876410775,-8770104460672011315,8419994632875801685,1102717431106189948>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1lxv7hx8mw4dp","dm+nU+yzR3o0UaZidMiKOOQK0go0LPfXdh8cx0efXrg=",-104176202262442702,-8278971596844992511,6342907219243198093,-2135401313275426036>()) {
                              case 614903217:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 5:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s32fhdkmdpyaov","DjPXUPlqUsqykStzLd1AXkC1MAkeqsA07RgLkIyMOp+T+kkb",3571896022769158285,-3606171715271275552,-2948118060242675323,-3636264256114503336>();
                           switch ((int)com.yiyiaddon.m.b.a<"s2hy73d33q7lpy","WyEUQGU6KavBu7rh38cMy1xTrXa2orIprtlQwdgGYTM=",9015105756629052167,-4757970001838989841,4973626702912150231,-2982613240152181213>()) {
                              case -1358615077:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 6:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s12bsvr084hdxh","EkyFLucsDQl9vEnUxlPc2SPevD3tVAlhfyqgEsAFivcGrGFH",-2069753279311018119,8311568885712300840,4658806414303592454,2040658932264104374>();
                           switch ((int)com.yiyiaddon.m.b.a<"sqg72j80l3iij","DxrHRoldIXjN78qaPDa9Bv2rAv8VgsHMI4IQvlwAAA0=",3281090199716771024,-4051422645314114851,5141437573366841811,942246222984415147>()) {
                              case 1164652595:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 7:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s2jdq0crz1r0ix","ugpPDfrym052NxcBSDAtYU/8V3cBqTyFbJd+SxcOoDuiXDbt",3283227125640160052,7150506068192668371,2718678881179808368,6066413000198070559>();
                           switch ((int)com.yiyiaddon.m.b.a<"sjxoq4nrqu1kf","3kNZZaG1MHYy8Ly5/nXcgW7tHPuqMWmP2gWA03x1c7o=",6920588293762151614,-5248818352124057485,5295460078419051350,-9056755971145234474>()) {
                              case 1249056580:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 8:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s31gsrjx8z28uf","D5LmNh4csG8FbfQtgoihvofs63zhAEY6yVNCecjq9kHMFZII",-2531121827141313964,-8181227426560727379,1948591043277471178,6813199972880265967>();
                           switch ((int)com.yiyiaddon.m.b.a<"sa8lv2crc7tjw","7PqU8K6OjG0KunFEJk2NhnEjuZ+mkvXhQV9RvtQLyOA=",-2531363825291572401,-8595247738698708150,1453265206346840548,3839931487688232720>()) {
                              case 321221391:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 9:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s34fo2hjfres8c","fBCanTJFdBm3QdvZgRouZT9SuGbeKY7ms7ZcX3tw98hY/b8F",2828140058320216939,615274763071135481,-7707415027436089283,220151051540103354>();
                           switch ((int)com.yiyiaddon.m.b.a<"s24e3rqa2skrm0","M5vdc6gExUEsaPxY1H7vWw+jiY/KIeIfjOV4oHi9+4k=",-4838696496681587286,7586539476587469977,1809803954302701551,8226381037477767065>()) {
                              case 1298066895:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 10:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s1158gqgdjrucg","iwRgWwZUQK6QiCREcIaBOfpZuudq/sKM1mk2Lxch2UexGNRg",-3662001810080506770,-4595877585426154562,-6202031251864667790,-2672181913677163302>();
                           switch ((int)com.yiyiaddon.m.b.a<"ssmstdlx5962","mUXdxTnWS7LVwZHsW/fjhYP8eooEHCQL+Mxfo/hbRVU=",8350973990889899044,3539499206406290815,2285447792468754911,-5455460695804483223>()) {
                              case 1517894226:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 11:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s2n8i576ry9fs3","FALRKJhXaIcUlerVnVB9dykCVlSzdZaylgu2vznQVUu+OIGq",-8973010433872296844,4324255897793866059,1128377892412355612,-6735346219591153488>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1byhipr45fbcl","R3JuLbV/f7ax5Rr9AqyqgIRPgLahlaYt3X00OFDEu9o=",-2318888318012358028,-691307550453656924,-3101845858836505299,4370116595427527163>()) {
                              case 1334462932:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 12:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s6muglf6g22ww","79CyZAXF7JmsMv5NEqKHJeLsvLQoUsGmxIaIguK/UlxhrwuF",1640116223804691058,3211514033517548220,-3845302379979783166,6511396722166483286>();
                           switch ((int)com.yiyiaddon.m.b.a<"saewo3g65spul","UdAU1CoEN7U2yBMjQOmexDWGPP5K/zOfheqW5FopnLA=",-6969318541082563594,8996435005688634684,-8929751515261469559,8033430043647163766>()) {
                              case -1089665640:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 13:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s1r70lflk7rexm","iX9cf2dDuYtSrUpTjy4/PctH9KNqO8Bu9/yr1/gaKVLBWLhS",-4897779454979251302,-5582394111633471391,-6765529538394007051,-332629636905585457>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1j7nvqrywxn6y","6Dazl479WWXRyCPgW58DdA7Sl0cQTMhIog1NLug94gc=",4433457335774574250,-5227442946144331912,5380009083041715548,-7433187365489951903>()) {
                              case 2116840678:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 14:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s3dpral17ev80z","Xlw3QnkG84GJsZRc68DJNl3fXbFtHDd3WjNAc2CxFE/4aErj",-7285898719454180099,2646586851569384905,4439676073141335212,-2405628068239747910>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1s6fq7z43ghrp","ksS+Ypiceh1mTSbu4ZH18GlLT+bEmImwl3pBcKwP4ik=",5364928104420972379,-5049124866226282232,6715214278645147606,-1561972622777588299>()) {
                              case -702189770:
                                 break label173;
                              default:
                                 throw null;
                           }
                        case 15:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s3dfb4xh2206mj","vJU60/6rWsGAyeRRux93XZffHWMY9NN6JiXLpGqHD5u0zOL0",-238927465391380087,8700188296086960347,-2459675160344955679,5766262820083980744>();
                           switch ((int)com.yiyiaddon.m.b.a<"s2lw0wank20vdw","/dMcc3SG7VV3Pr/YB7ahOnY+TWR1TOc6BUMkKQPPHZA=",5296680791185080969,-6432265149340101450,-4134211315252184173,-3889087948046239436>()) {
                              case 1789874772:
                                 break label173;
                              default:
                                 throw null;
                           }
                        default:
                           var10000 = var4 + "";
                           switch ((int)com.yiyiaddon.m.b.a<"s6549d1rg25jt","Rna3sZgGcRZyAkH74eHRx0hHsC6mLXbXTlsBGHsLjhI=",-4815069144716325775,1080503175515114827,-1922109769942831503,-6025292235654693549>()) {
                              case -929918963:
                                 break;
                              default:
                                 throw null;
                           }
                     }

                     String var5 = var10000;
                     return var5 + "";
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s250krgs06v7e8","ixheNP/HNQpISXftH0owoVHsC1H3S2I9lotkJzolp30=",-2042400648080725489,4168291729871556408,7061724675632183460,-5037132333701815605>()) {
                     case -1089304111:
                        return (String)com.yiyiaddon.m.b.a<"s29ydyostc7yax","OSE0ertj6Ov/pVk2h1gRrUqBO8E/GBn6wtQcCuwcIiBSwLGXHTA=",-8687592644919396908,8672353750846753675,-3490760842605917496,-586142058134287390>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s29ydyostc7yax","OSE0ertj6Ov/pVk2h1gRrUqBO8E/GBn6wtQcCuwcIiBSwLGXHTA=",-8687592644919396908,8672353750846753675,-3490760842605917496,-586142058134287390>();
      }
   }

   private void eT() {
      com.yiyiaddon.e.j.j.c var1 = this.b.a();
      com.yiyiaddon.e.j.e.c var2 = this.b.a().a(com.yiyiaddon.e.j.e.d.MINERAL);
      if (this.ab == 1) {
         label270:
         switch ((int)com.yiyiaddon.m.b.a<"s1jcqiv7j3ave9","SMgBMb1DQfns+4DzBuaXIZU+qxI14wvf8tdZ7UF3aP0=",53366795599678101,-7753620335455436263,1024687030431531436,3677282058866972695>()) {
            case 1779427429:
               this.cy = false;
               this.hJ = 0;
               this.hM = 0;
               this.L = Double.MAX_VALUE;
               this.hx = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3aeqmexygy8ra","4CkvHYJst+N4gC3A6XdX69raEA9odFIcFPXgbSfT3QM=",-815725490121107333,46832254482366664,2267853616741967343,-8678355880387903971>()) {
                  case 166401887:
                     break label270;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sgle38xodbgc","5pU8g4ZhY0oklQ2JG+sxIQw9d2e9Hw3JLKZGLS4DX60=",-2344465701560766043,9165494541719549027,-7169618711526246236,857227550370232239>()) {
            case 1946545247:
               if (this.b.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3kv4yn1ajzda","RDSNAPgxfG+k7W7wNVX7SBDDDEv2zeDX1LFQ39T31Q0=",-6678764211786608313,-3568600103450633811,2087093757631356182,-805799738544478165>()) {
                     case -650922196:
                        com.yiyiaddon.d.b.e.a(
                           (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                           false
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s2ssmw7jvjinvy","crRwHh+NbH5Yd6ByeGaC4UTWYD6+JnY7I38pacKDU/g=",-1030935390646817092,-8248332897347960021,8870399960997704780,8046199933928110342>()) {
                           case 2100783849:
                              return;
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
      } else {
         if (this.cB) {
            label266:
            switch ((int)com.yiyiaddon.m.b.a<"s3pns4zqtobmjb","oTJemC7Gt2cSRJKjJge61TKckRt16iyDW+lLy2zeWqI=",-1084527749612251431,2451547643335567408,-2620536291125091062,21795372065726720>()) {
               case 1027478842:
                  this.hI++;
                  if (this.hI < 40) {
                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3vx27j5tnfz4d","AKPrdSPsKZdCuY2jY8bF5trb5TPg1gUFvFKT17RqmnE=",5225249002621640975,-2042987440862601787,2035359342622380446,2114588067401296303>()) {
                     case 1081027864:
                        this.cB = false;
                        this.hI = 0;
                        this.b.K(this.b(var2.a()) + "");
                        switch ((int)com.yiyiaddon.m.b.a<"s3rw4ogs9onga0","k/CGw5DAnhjFuhGd8SM/CxlxrSvIOLS7WRI6ogUyrys=",6940121360014591028,-1690349139670624479,1085225391639497119,-2761077467094936407>()) {
                           case 2049781539:
                              break label266;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.ab == 1) {
            switch ((int)com.yiyiaddon.m.b.a<"sj2452uw066tc","AqE9Abmi6Jdlw4rF37W3orXaqthsKDXiUs4xuapzDGc=",8575491825667261468,-3932664763776138107,-7455151042612470851,-2279120788716061918>()) {
               case -1957678609:
                  this.o = this.G.player.blockPosition();
                  this.ae(this.b.bN());
                  return;
               default:
                  throw null;
            }
         } else if (var1.cw()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1lidprl6kjh5u","9Fh9hP5AM0hTluJ65P/TzPhJLlEJM7o+8wdl6wOONl8=",-4484061160583091047,3241400458219495397,-1600531430462002827,3626538436238536583>()) {
               case 1514394630:
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.ab > this.bx()) {
               switch ((int)com.yiyiaddon.m.b.a<"s33tg5axt0942t","20PRe0F3A0Iuph+DB87zwgP++US6KEtuaRDuFOlJ0ZQ=",-6272085271937522817,-6288989641218859310,-2581199573719672721,2260919142743148891>()) {
                  case 1679302820:
                     if (this.G.player.blockPosition().distSqr(this.o) < 4.0) {
                        switch ((int)com.yiyiaddon.m.b.a<"sgd7h5v4elddk","OspKYHpQmWNY1u5kWJZw23XUP5z1UrnpimYzheSoZqc=",-974757126699182609,6467973118461115200,-4276184730501690627,5797006677436801505>()) {
                           case 700399038:
                              this.b
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s1bvd9znxvy9jd","9NsGXjFv36aarg3DJKlQIW3aP48Mbk8USueV3bGaDnQet83yiqc2F8VVtdHJLs5wN+IWcJNgS103houkU+wDpWlGnyEDJNx1W3Ad6YBp",3131118757726492447,-5163619757847755345,-8639403793766267839,-5802296843290500975>()
                                 );
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s10qwc834fuhqz","UU+ezYx6a0nCLqWG1oT+WIBgRy2fDBJVWhNminW9H6M=",6650888984150158442,2295562263983821304,6222993021966283268,1334109478938978398>()) {
                                    case 1505434862:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"s2xa9zgrr9akmc","D8oGMib5PRWl7+v2FVYsXxDcRNGC7fAJjQdutYrU37U=",-8835827799409757559,4431767428847152490,193817212728785882,249290248440886378>()) {
                                          case -1668581915:
                                             return;
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
                     break;
                  default:
                     throw null;
               }
            }

            if (!this.p(var2.a())) {
               switch ((int)com.yiyiaddon.m.b.a<"s1gmb3jj7dhutc","AdO50fyEZWvUc+FNcdnqbCm4pbBZOIs4/AEIm+6FveM=",1059745122537646426,-3463022939334505044,460517411010063015,-7555663726681520881>()) {
                  case -495391440:
                     label295: {
                        if (this.cy) {
                           label199:
                           switch ((int)com.yiyiaddon.m.b.a<"s1bcmmghykjomq","iDFEJIoAcRDB8JoTSuKhFrYvEQx/mPJ1LASEHkGjQoQ=",-5439247403452695192,1188445470617514768,-4926128666292397611,4331273060197570798>()) {
                              case -1303762725:
                                 if (this.b.a().co()) {
                                    break label295;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1qsrbrf8rzm2p","11a68i6PwpXdLyU1eCVI+oFfABSPujTuzWGx5SrGVYU=",3071190407745796281,-1064134706873331228,-8695490096434081709,5232110377790281303>()) {
                                    case -1696781054:
                                       if (this.ab % 10 != 0) {
                                          break label295;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s1zhpq3oold9cz","SWJqja7IwbBcyl+K8UNdEw0j21IULliQjaiA/V/cW2g=",8560569037650905157,7400946870281532011,-3724996410947266473,8442580702582272406>()) {
                                          case 1169983534:
                                             break label199;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.cy = true;
                        IBaritone var3 = this.b.a().c();
                        if (var3 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sdti08ohxg4uy","v+BVOHo3J8/VybDKyDNys+EYXVWMryJxhDpXS+IVYLA=",5474166932931351126,5934161147880282430,1728025144939408583,5574133881120561905>()) {
                              case -809138409:
                                 this.b
                                    .b(
                                       (String)com.yiyiaddon.m.b.a<"sb1qv8rw0s3u5","UEX3B8N+1jN0QcwL6lW6yzfy0FHVvDgc0scgxWZDKf6Xc5zE6hjfMXN1thKbLBSaTI0xBNA9nPeP+6vPUPnWbEMjTMTFjvJln9bc9MRoo2k1mNey",1952204184866267238,8499116001709334441,-6392117649660228960,5933144500878916800>()
                                    );
                                 if (this.b.g()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1vixjivfljw54","ect4aYJghsQv2UdIx2dm5PpcR7/FcD8OHdW2zT278Us=",3223325124806707734,2154896539680794262,-3033044018775036891,-4665975238956152362>()) {
                                       case 707972174:
                                          com.yiyiaddon.d.b.e.a(
                                             (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                             false
                                          );
                                          switch ((int)com.yiyiaddon.m.b.a<"su3gecet0v0qv","HYaJsDdjVhoVqo4zcJuy4/B3Du32CA77EF9LvxVsCl4=",6385883038533085878,2571183803477458243,1906609173365493936,-4686998573941284038>()) {
                                             case 801805297:
                                                return;
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

                        var3.getCustomGoalProcess().setGoalAndPath(new GoalNear(var2.a(), 2));
                        switch ((int)com.yiyiaddon.m.b.a<"s3vfes28kvunuh","usYiK0qP3rylU7bppunDzt+VJRzcqPtrxmPLHJT7inQ=",3846299376851575071,-4267611507113767079,1659286858171713637,578520505965132486>()) {
                           case 491184920:
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (this.a(
                        var2.a(),
                        (String)com.yiyiaddon.m.b.a<"s31z8bl6gedgzx","wuBkh5b69ONHelDHrDZ01lXix6wwPB6209Ww/2U22xU=",6754181451574182687,1066973822604939225,3091659863084237339,4788004349499914699>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"sqvosrmww2y5f","LpXFrXtW2ZXWheuRqGOc5Y6vKORsQRbPDFNyuXYCg7w=",-596692182675554706,-5654397748793441576,6760662040178572973,-3267130138385064170>()) {
                           case 453351585:
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2lg1k2rfikusj","L2MqCVcEMGhdabN8v6BcyMi5P01ubxaeWopirw6lqi4=",-2358482935545819329,-3577319699766348852,-941967511627794984,8645020322386413980>()) {
                                    case -1982106279:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"sfjo2h8v6rgjd","4OiOl4eS1S6Ns8EjbgL+JRfzE6dhUZaI8g6cChDKmfs=",1681818040007527000,-3841235173146395252,-3618220349285636831,8361589903065401032>()) {
                                          case 1987510043:
                                             return;
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
                     } else if (this.ab > 2400) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3m09rb0ydqacl","9JAZSLt76r0Yx2SdA81llEYKXC3whcyKIK0aA487HJI=",4276085639737123559,-6856878334121185244,8158280146746917456,-7327719876025723951>()) {
                           case -1714914122:
                              this.b.a().ag();
                              this.b
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s3am9mz1nlbvun","vqdtLJevTxDUVTAOr/AMX8DEdqfudelOy3vHf187lLoZTEkeFolqbbAnJ3Ta0pDxvJByv15Bn9z2o89ILLV+3ut2/dcDlA==",4307022363031026624,-9190817043673761155,8749504438746475094,3800992923377167083>()
                                 );
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3a3in84s3sxyw","NuxfnqbYmehKosjIUrBKSv2+NPJ4QVEKpYLNN0DOdQ4=",2781943842130302973,452884274222585925,3087003415286409549,8869363447397065281>()) {
                                    case 869868581:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"s1bd8djia1ct97","hOGQnG2+irzLrmUlZEVX8vkMQQk35s+aOA1dDa+ZsHc=",271619723931593474,1464042197673707561,3421519200240836122,-6692948165893263026>()) {
                                          case 1724746453:
                                             return;
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
                     } else {
                        return;
                     }
                  default:
                     throw null;
               }
            } else {
               this.b.a().ag();
               this.o(var2.a());
               if (this.G.screen != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s32ig9cxg2s407","4zOKaFGn6r5a9xkXZRKOi/dF4oQvqEqwWHXJ6fI4xuA=",-1386475822670755713,688005374802751456,-2327277450292723143,-7554318422609722390>()) {
                     case 1575302293:
                        if (!(this.G.screen instanceof AbstractContainerScreen)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1nrce181tozyv","mY004T2kbZNlKkXE68K9ZzWGBZvRO5467Pa1MPxdXXc=",3541306859078056317,7812697934810864914,1539960403547459774,325503181757300601>()) {
                              case 1811284657:
                                 if (this.ab % 20 == 0) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s16o89lkz1tufv","iW0/oa9y8SSAIKsjKs5lUW8Yk97Cm++cPXucssoLKk4=",-2859724938699804517,9040575505221580190,-5605942333958843925,-8971054425355342524>()) {
                                       case 456842559:
                                          this.b.a().cD();
                                          switch ((int)com.yiyiaddon.m.b.a<"s3p2a6p53y9cv8","5aM3H2vOm7+OgT4y4tRVa5QnODdKe+o/LCHhWJsUOy0=",1754430920993905073,-792975669843061075,4375496490828272604,-3706433868387677028>()) {
                                             case -1540173657:
                                                return;
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
                        break;
                     default:
                        throw null;
                  }
               }

               if (!this.b.a().cq()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jsh9rnqm3l43","i60J+obC+Yj/DmNs7OnL55ZpnNHe+XvM1eVS3FxjtNw=",-501494374785524652,2334669231131695897,2995225659565607381,7154497136085644421>()) {
                     case 118309370:
                        if (!this.b.a().c(var2.a())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1mkih6bikq4lh","h7IeXfIWqVd7Egc7q3JwDMZX9D3Ou2Y8oZ55nP3CQ1U=",3464631481180928981,4794037829821776992,-7586289750046277087,581790717409756731>()) {
                              case 618573733:
                                 this.hM++;
                                 if (this.hM > 200) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ctad1bjypci4","oYP+4dah9+gEDJQQzJErwlDnphWqlxQ5utWCP54oLGc=",-7681722318583249355,3619302680395266265,-9079609265187149307,-2997197184440799091>()) {
                                       case -4342185:
                                          this.b
                                             .b(
                                                (String)com.yiyiaddon.m.b.a<"s2x6j74pq1jn51","2/1qVfz8ySakjqtxgdE9kUv1l2DqdTjMf0eyVwPBnqeCHrD0cGUPWFlVvThZpcw9Gi218XQu97zObuSNqRH6c32qWNxRs9EpJPXiVg==",-491710285100441074,9021797327885580438,-848080852810150872,-689946826558448071>()
                                             );
                                          if (this.b.g()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2r32pqnsnhbd3","nNVPboHhITMzd+xJGkGVuATOKFnW5ku5UzI86KyQBAA=",-3302377615773492365,7345482169914206079,9132602533706561371,-8799959258768168970>()) {
                                                case 1114305817:
                                                   com.yiyiaddon.d.b.e.a(
                                                      (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                      false
                                                   );
                                                   switch ((int)com.yiyiaddon.m.b.a<"s244w3ffdzixhv","tVUMG5Ow2j4Fx61BUP3TLDiY5nLDsVgPwkZzzHpP+RI=",-124585952059301653,-7119166847692463004,7704663470328473242,1710975111992128436>()) {
                                                      case -1213975857:
                                                         return;
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
                                 break;
                              default:
                                 throw null;
                           }
                        } else {
                           this.hM = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s2v8p7ifo6fkdz","Zd/BU2cB1ZBlDsS2jGopo6SzOnzyZC5XoLj+vfIAt9M=",-7942796120797186568,-6918163308204321274,8808944126833312405,5580756720713620220>()) {
                              case -555821829:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        this.b.a().p(var2.a());
                        return;
                     default:
                        throw null;
                  }
               } else {
                  this.b.a().cr();
                  if (!this.b.a().ct()) {
                     switch ((int)com.yiyiaddon.m.b.a<"ssdzu35bxohg","FUz7mm4jZm+t4J6UjMArS/FC/97qtodp4L+AM6s19v0=",-4934075978385851968,4381395708876190259,1911256084785895524,1297178172282572911>()) {
                        case 1616864637:
                           this.b.a().cD();
                           this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                           return;
                        default:
                           throw null;
                     }
                  } else if (this.b.a().cs()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s25q927ens2190","0/M7JWfXlkL+RjIMNgvSXV6tp5JkCWL1sxNY7/QpdDk=",-9160758622250306524,-528350195975730258,7748690631083988646,7017442714989754163>()) {
                        case 359523119:
                           if (this.b.bJ()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1m2n410hqclzv","LYAS6Ux2QNooHzSy9RGAVxc3bu5DJKJtNhBaLMBey78=",201562973150659765,-6743070962200208956,-8593008029206879556,-1785002586107346225>()) {
                                 case 1948278013:
                                    this.hJ++;
                                    if (this.hJ > 10) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s22f1c0rmm7fc7","4JY2kTqcTFhQxN5RKg6AU010Kotsq3HiEVkyXfOBpiI=",3338060287792420917,-4373543450962501241,-7567305837591563880,6290432839549170151>()) {
                                          case -1011774674:
                                             this.b
                                                .b(
                                                   (String)com.yiyiaddon.m.b.a<"s28yb5todmlzj0","eUI3qF3+yLwHDyw1LPAjbSCPAGtYGBsA1VZf5uQMlrZBqIgP+6LJyYO0ezt6G6aTfNsLAkBIaSTgM7/BAHczfpMy4owHX6tW5ufkYZqr4Y03nk1jF/A=",-1008808055558823307,-7545641031238949617,-5900249098856082459,7472555763878834524>()
                                                );
                                             this.b.a().cD();
                                             if (this.b.g()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1f0sgi0q83eci","uCmW1hFqEsw8T9IEJZIp9dhbi0KD9MvS/cgRygRSQX0=",6967295640407582779,-6157699833445356807,-7375544776143936045,-2369889485371543019>()) {
                                                   case -1915331243:
                                                      com.yiyiaddon.d.b.e.a(
                                                         (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                         false
                                                      );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s20heszs3xa2z7","Yex2HlCERoyVNooDZc+HvZyfWh5fZOMBoo2NwxroIBQ=",-3652175940415587411,-5765743814323548602,4734160924299780890,-7535767801508461281>()) {
                                                         case 340119548:
                                                            return;
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

                                    this.b.a().cD();
                                    this.cB = true;
                                    this.hI = 0;
                                    this.b.K(this.b(var2.a()) + "");
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              this.b.a().cD();
                              this.b
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"scin0qpa9309g","jLUTlbNLWujCN/NxfBd0XbL3aPrmpu6OiWMGZl0g0Ue+mLC+XDHOhYd5PiXn4bbesJ+QqT+1+k46CZoh79nO6EgwA8iO8RiV0weOY35lVIZKXNXf",8840252349988419388,-8001468214733334743,-8517024473378973882,4057156586323037803>()
                                 );
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1zjb4gktoafr5","QBQqu7YLwSKnZ5BJLqg9pe01PRLlYo+eNPYb+LceNc0=",3216442444648041852,-857663050703745300,7414285263873644838,-5230282299430211176>()) {
                                    case 118085700:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"s3t721sp9izyt0","UhJJ5JVNFJ2rgXiMPr0eB7ayOCXH+p55VzxZkK8+EQQ=",-3412057011429172027,-770815505848339341,9206573331209854991,-4602447134961475173>()) {
                                          case 1824574252:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     if (!this.b.bJ()) {
                        switch ((int)com.yiyiaddon.m.b.a<"si3zu2tfcrpxu","wwuFOjOfDSSKdDC8yNuPkjAB1wIJqFf585mnxzePWhg=",-9218266662562701047,4691770083124325617,8294226672966624530,8178462966648178777>()) {
                           case -1988884424:
                              if (this.ab > 400) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1fxyvnlxfs0nu","kNxrRQAcTI156Z66n58xi86cWyAqrIAZs3q4bb9tmks=",2445929356187502200,979827187724907995,-1854383905170858396,-221119717393254374>()) {
                                    case -1628296813:
                                       this.b.a().cD();
                                       this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3p3cyl40cn561","tSBibdiy214TcPFbG2DeEENuU8uGeXNbbygs5UU2Wt4=",-5293344635001450036,8113504255567459666,2762702844881685802,6910588472568911913>()) {
                                          case -813615581:
                                             return;
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
                  }
               }
            }
         }
      }
   }

   private void eU() {
      com.yiyiaddon.e.j.j.c var1 = this.b.a();
      com.yiyiaddon.e.j.e.c var2 = this.b.a().a(com.yiyiaddon.e.j.e.d.FOOD);
      if (this.ab == 1) {
         label278:
         switch ((int)com.yiyiaddon.m.b.a<"s28blcx9d5ug58","ILb6np9z03fc9/kC/74rSEWiNyuGnws8doW2SAg6dPc=",7725370208667086220,1170319412969064307,-2392103357831405121,-3457089729993054377>()) {
            case -304772915:
               this.cz = false;
               this.hz = this.bA();
               this.hA = 0;
               this.L = Double.MAX_VALUE;
               this.hx = 0;
               switch ((int)com.yiyiaddon.m.b.a<"skhjd6isp625p","/CwBNn7vqEqMUFfEIllw5WBl1BreFsGEBrnpMA5fVh8=",-3735745730279106173,-9133459810343477366,-3598293714798547736,7931378507373985997>()) {
                  case -25423964:
                     break label278;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ch735fx7ejgd","V1dpPhl69M4+G565BnWe+45Y88K2WUziQU9SxTA/zHc=",-7523099162345493141,-4395551492233061798,1296544588944873006,-7723174067433661600>()) {
            case -1692004377:
               if (this.b.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"szuqlbebr0cqc","3AHd436JLTlp+6A4gDPE3nvF7m2YoZzdijFUKmI1Pz8=",-6660834140364603538,-395857345699306933,-920689574293914659,-8495941877778395944>()) {
                     case -228937312:
                        com.yiyiaddon.d.b.e.a(
                           (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                           false
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s16aoyix03438j","p+lOxluC1ud7x3Jb1FXB6M7q73UUetbV/1unvyymN2o=",-5372279200540711280,3130350267807465478,4397092679750392068,8870581698587615049>()) {
                           case 1941629845:
                              return;
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
      } else if (this.ab == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"svj7r9tv031jd","RB2vVRdu4lAcngv5f/CfHUw/IWNw4tcNbBntEzDVN88=",5704992911861694812,7350923455357618117,-8020035743397988787,-5283463132803912623>()) {
            case -1172954142:
               this.o = this.G.player.blockPosition();
               this.ae(this.b.bO());
               return;
            default:
               throw null;
         }
      } else if (var1.cw()) {
         switch ((int)com.yiyiaddon.m.b.a<"s37kl8wfz23ipc","8eQ0VHl3q7eW6gy0m6hTvU1uI1uJzrazxzu3pTZRJSo=",3015304884738041245,-6330075400863781879,-7905518254728807564,1030971458837551570>()) {
            case -1678385824:
               return;
            default:
               throw null;
         }
      } else {
         if (this.ab > this.bx()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1v0u3zrdaaczn","rnStnEjH6ccwsHNaPYxr2AhlBZVnopXcZRJ0aswiFzA=",-3477600460154110911,-2724011734278878224,-7501771280941533750,7463422440948039421>()) {
               case -753762563:
                  if (this.G.player.blockPosition().distSqr(this.o) < 4.0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s156t4er4zj2qe","x27HDNV+r3OMuNyG15d2fDpFaWSzLYnCYZjpAQGHVSQ=",8312516237852542328,-4081480592068291352,-244573369019323846,4626516670355415977>()) {
                        case -183306358:
                           this.b
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s3d7mtb2gjhveh","ZBhlwHf6c32dOsXVGYWExUK8Qu+KJKB00kmB4xAC6ZESR6CezBItA5aztqLZU+gzsUWeO8PInutZlaGeRV4q3WoE/Qe434hl993ml3B0",-4991772982624525801,-45665631619921159,-7890410361960371562,-74626760530684681>()
                              );
                           if (this.b.g()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1yb1gooud2a1t","TlpvpLtbZS3p381QxVmE0k8cONa8IHOPgqluQWnpF/s=",-7948861342404510562,-1222729949396234307,-8635301701040656394,6029491850321383822>()) {
                                 case 612795013:
                                    com.yiyiaddon.d.b.e.a(
                                       (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                       false
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"so3t4pkrivasr","hqkA3TV19Sld9MwTU1yL5emEfVkUbtkRqhb2T2HX3K8=",-129430897512120498,5389177493157512449,-5041652150818413921,1191135583313919038>()) {
                                       case 1496698268:
                                          return;
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
                  break;
               default:
                  throw null;
            }
         }

         if (!this.p(var2.a())) {
            switch ((int)com.yiyiaddon.m.b.a<"s1j39fmjaavnx8","qNN49f+aiKABb0auZ41Vg33+idrZun7xqcXROVgsKdg=",8037870954775070880,2407961619640605943,-2046139476190091819,8523673754712330320>()) {
               case 305436076:
                  label302: {
                     if (this.cz) {
                        label202:
                        switch ((int)com.yiyiaddon.m.b.a<"s18esbwyf7maqq","adwRwC0apcjLOnHtcG0u05UC6uiazi0bMzPaJ7xrgtk=",3341504710013310046,5851897821550852619,-8181348930832411887,1402449087387518722>()) {
                           case 343927584:
                              if (this.b.a().co()) {
                                 break label302;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s26l5uxzimmoab","WAmB9AZuudDt/YfrF9/l+kxL7FSUOQ9b9SZOBvCCUFM=",-6706997801747234756,-7273863861023131113,-933520621952272051,5688455737997566900>()) {
                                 case -781189948:
                                    if (this.ab % 10 != 0) {
                                       break label302;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"scuxyiuk3bgih","Qrph2E8ed9URXwn7BwBwUYuY04428nKBHWZqq9dJzWQ=",-4675906863661732277,3849496341622849294,-755674232639347649,-4762887981059398050>()) {
                                       case -1466636888:
                                          break label202;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.cz = true;
                     IBaritone var8 = this.b.a().c();
                     if (var8 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s9eotjebbf5fp","T0hfFamV6XePfhdwfJoj8+lurj9H/hNLTS2hQBTkebc=",-5449736607255302227,-7821690284158471857,2077881650946883068,5791350401434997090>()) {
                           case -650238070:
                              this.b
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s2j87dmts2006a","74WPDUIHtU8iYuQpE9E7YxJEM21F0oWYdRcMmgW1wNNTNwtSP2GFKQMnI8ChJ/bDG3F9zjNWhQgXlix8vUzYkvrx8fYg0+SU2qLjAAN1wD+WU0uy",4312440271412937707,-8860211287289268871,5899560775221772415,3144531970496105214>()
                                 );
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2dkrniwge7ahg","M0VkqUUwTYNBcEdznj83/2+4DAKtNSK3mRsaX96oBIg=",1565146420515359836,-5716817813285704457,1674804069471171997,5138097969871902590>()) {
                                    case -164987994:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"s7w2y6362qqfh","PJctL/cujUsN7HyqEG+kwJKRkzwerzfFWPff72YKX+I=",835287423598977480,-7254271519338417138,8184874427345420886,-1167087251362595086>()) {
                                          case -56580527:
                                             return;
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

                     var8.getCustomGoalProcess().setGoalAndPath(new GoalNear(var2.a(), 2));
                     switch ((int)com.yiyiaddon.m.b.a<"s19vkutsd45vlb","G3zaphH+ObyoOpLgYurdl1NAk6qaLr7wjgqyCWqNXcM=",2574434670644520039,8844169949320142461,6372824387253968209,9141110869177296154>()) {
                        case 1903852480:
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (this.a(
                     var2.a(),
                     (String)com.yiyiaddon.m.b.a<"s7q2ffubegxli","u4eeKa58yYFfY4+mSbmI+fHpknNUwoKAEfyEhBWFgec=",7496100368108436745,-885017411225269119,8069090353730894609,8047496264001177302>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3uualryyjs16j","HU9uEscU2dq6NAJtQvghvF6AtLtJFDw8AzGkfSi8FWM=",-412000582937973825,3422869008712134115,3127622328950423177,-469579557572786543>()) {
                        case -259789812:
                           if (this.b.g()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s27eosk0pxn2eg","x51Qlu+MEDHA2tovwpCpt+CuwEFrzXb4YzA7/osJc2U=",7074916394108520063,-4249960768962554746,-3502371060437421663,-8534247983441050676>()) {
                                 case 603435626:
                                    com.yiyiaddon.d.b.e.a(
                                       (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                       false
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s2er76cvcpug9b","Cz9LxTo1pHfLGtwUr1upb4t7Qj5ApwLr1ETz774S8QQ=",8942429939843888215,7734961079780863993,-8144833613715715806,-8997132851871407548>()) {
                                       case -1054078462:
                                          return;
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
                  } else {
                     if (this.ab > 1200) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2m9lhmcugi96t","KgZKoE1bk6DNANJG1/gGBdUC9nIdfReIhm1WnZhozso=",-4654837120976950108,-1098898165500265869,-1480155423956460912,-7923991234626122665>()) {
                           case -221076979:
                              this.b.a().ag();
                              this.b
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s24f4a99pkuao0","qPh4fWYxS1VLl7E/oR+c6cObo0cmoZ3Q8T7g2iGfNPHWponNwaWiOdPYWNczL2KdFpGJPGcLVmBcPbwo3Rv9Zo5caoBvOQ==",-2369525164533681124,-7705896318477879530,-5499197399338566838,4522912612111744221>()
                                 );
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3b89od3z8omvb","Xjx1N9RqZH7CcmAy6aZvfMSqZo/T0vnXvQ44dbs2yS0=",2173496575076260512,6055233263795969829,-6899187832677401567,-901501869103644609>()) {
                                    case -2034041334:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"s3d32n94m8q5fx","b5LwYHSD8g/wgX5sxtSKnGaxH+vTh2oV7o/FRytJXYc=",1342613773487456410,-5118832945972294120,-5967149439058455833,-2122867824307992701>()) {
                                          case 238250188:
                                             return;
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

                     return;
                  }
               default:
                  throw null;
            }
         } else {
            this.b.a().ag();
            this.o(var2.a());
            if (!this.b.a().cq()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ynxtlxp51a1s","orLhbFOrxxI2lKYgphakRiEVK+au+Smx44/D3TA7Ovo=",-4358342998804286454,-2382075228558375161,11765024207222685,-2643161421221570312>()) {
                  case -715125123:
                     if (this.G.screen instanceof AbstractContainerScreen) {
                        switch ((int)com.yiyiaddon.m.b.a<"s6ade848vs9qz","kvRfA28zO6mq2wd0WBfIG+e3mniOWIAYlA21sPoa8Ag=",-4845475156139166716,-4785363500865242147,7477391833343953012,-1250663373914093979>()) {
                           case -1839385364:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        if (this.G.screen != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s37zungs3xhnlo","Rg4yczCNTdyeKnRH9/eson+4e9jreQDBk3wg3zLRMqw=",-8567482381451698936,3768813797331364614,-785317496262944947,-3120918438697029337>()) {
                              case -591294450:
                                 if (this.ab % 20 == 0) {
                                    label209:
                                    switch ((int)com.yiyiaddon.m.b.a<"s97dl18yrqqar","6uuao2u1VgGUKhFgcGv3diWadWQQEUW8tH56L8/iEPE=",6105182617766842242,8294964481017152807,3524326217829683485,5602828128812003015>()) {
                                       case -29745701:
                                          this.b.a().cD();
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ecl5dtl1w6em","n4VA1653K3S4LDvdHThZeGHBWlzEvmpdUxlHZRBap1M=",6434170092564628358,5585712291621729769,2118975072609026480,7937676136466232488>()) {
                                             case -1024125195:
                                                break label209;
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

                        this.b.a().p(var2.a());
                        return;
                     }
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.e.j.j.b.a var3 = this.b.a().a();
               boolean var10000;
               if (this.ab > 600) {
                  label265:
                  switch ((int)com.yiyiaddon.m.b.a<"s1wk2kf4jz56n8","DTMjB/VaMlSZFgJDo6bjxc3pCLALs8SKd6L4lH2z3sw=",-9161315109696035683,1001996753742319308,1276203675494255786,6674305969456498603>()) {
                     case 442080431:
                        var10000 = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s2fuz2yim7k20w","W6jX30cZt/tWJIONjvWELzWOdzMM0DWZOq4sI6rz1MY=",7227637258149195048,576884521059810741,-5548140934904821983,7941181310157462676>()) {
                           case 1847030926:
                              break label265;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = false;
                  switch ((int)com.yiyiaddon.m.b.a<"sjg08c3c9rqnz","nMgPz/kKYQXm8uftyJXdE3Z2XY2m3mehJkBy7uQCFVY=",6850667760130027944,7988153944396700734,-189343769028965752,-2708443297990176813>()) {
                     case -437251171:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var4 = var10000;
               if (var3 == com.yiyiaddon.e.j.j.b.a.WORKING) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ynpw603y18am","3eGee+/Ejrh2jRpoDusvx0Yg1WRvkgT5oBur2sLCBXk=",-4269251071984385872,-1360132219324465174,-3516297778850461564,-8113267684621113061>()) {
                     case 917413351:
                        this.hA = 0;
                        if (!var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1we7jj93jbx6f","1GngVk1yWjJaLaXcFC8By5+4mIfuHCVdWCLx2Iaopy8=",7309472156833583228,7810439826198057139,7964574913263569395,382543953656792447>()) {
                              case -994987570:
                                 return;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               } else if (var3 == com.yiyiaddon.e.j.j.b.a.DONE) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jw2karsg5bif","pNYmYhk98fnn40qlSpfB2kol2kaoTzgy20fw4iwgS+w=",1876566239751775886,-8266552713191410557,-5311410403848573620,7253934317733874165>()) {
                     case 1281347327:
                        if (++this.hA < 4) {
                           switch ((int)com.yiyiaddon.m.b.a<"sjbklfxta7kxm","owiR7w+l6RwQICjuihtgEf49xJYAmg8xRO51pwI821s=",7787566782443984391,-4908869496083604331,85135067671151305,-4572008147871339646>()) {
                              case -1259120872:
                                 if (!var4) {
                                    switch ((int)com.yiyiaddon.m.b.a<"se3i09cqs4mok","FhCk5Fy8Q1U9r3UVRMTl4yDwes+4VRlaYjIaK0LfPUQ=",3061466681418371673,-2246754359257105688,-6912750048892025406,-539835593885380172>()) {
                                       case 703233999:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }

               this.b.a().cD();
               int var5 = this.bA();
               int var6 = var5 - this.hz;
               if (var5 >= this.b.bn()) {
                  label249:
                  switch ((int)com.yiyiaddon.m.b.a<"s30boqtksukxr0","2lR5umrzmbUCbqtXorcJA/DaTnRTjEfa39RvcXWtNOM=",7975997498673962911,5999559319180618289,6205640782063841142,436307463951968894>()) {
                     case -751322874:
                        var10000 = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s170fhw2ahvqgi","pPH6aJ6mnRMCXfFaaC65yF57TI5YstGMKObCEO1LS8Q=",5833381679307204685,-9001932460259948179,-6157691858122653031,-1552779635151711942>()) {
                           case 21283841:
                              break label249;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = false;
                  switch ((int)com.yiyiaddon.m.b.a<"s1zgsp516ucg05","OkWHb15zWh07m12bDn5qOgHKNHETyHL3DA7vuKNmUm4=",-6114647806843706532,8981485689196347670,1097089051811174233,-289030248394505894>()) {
                     case -1027854490:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var7 = var10000;
               if (var6 <= 0) {
                  label244:
                  switch ((int)com.yiyiaddon.m.b.a<"s3opoo7sce36wp","VGakNIoWWyIvyZ6FG5BqA7p8Tyt1nbsSWq2ERxnybWc=",-8936026607507757495,-5269119343555295066,5360895364315818752,-6330433946515607204>()) {
                     case -1819836062:
                        if (!var7) {
                           if (var3 == com.yiyiaddon.e.j.j.b.a.INVENTORY_BLOCKED) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2rs6c3ltwq8fl","Z7o2NaHWTwhfc4NQo+Fes77pdFpM/YBemHI1eDMrdEc=",-4638220103279957318,-343194669022821264,-8034164489856680999,-1298300844747796552>()) {
                                 case -1608652415:
                                    if (this.hH++ >= 1) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1gh0vn4p3nimo","6Ung+EreXHYQIRw9AUWpZl5AOp2RO8xTaMjkm+zBeXQ=",7529839996232915428,2812321817205973080,753949524765908128,7749951768769191591>()) {
                                          case -148724368:
                                             this.b
                                                .b(
                                                   (String)com.yiyiaddon.m.b.a<"s18ur4scmd3a69","PtH5v6ZwrEW54Y5eFjpKn5S3fFXEX/kjoMqwIDmw9oGcQppPJrI3Zle78m6nl/hcfejwqJnzt5vhl6KZmlYOXzVvpGHKkSQC/Q9OSk7lCYjDYzDGEBDtXMpm55usQOuVP7Ajt+fRVISPTg==",-9113370014551918107,-8868054604245175689,6360993163240410143,-8314062926978096609>()
                                                );
                                             if (this.b.g()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s11kxevnc71zij","gH79+FoRL2jlHIpGAO5uqUgj44yEJd085tmY/o1uWiQ=",-2645351308290079355,7669314716994388779,5030618661014588657,-4205457243968979307>()) {
                                                   case 624726166:
                                                      com.yiyiaddon.d.b.e.a(
                                                         (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                         false
                                                      );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2ecnn8ixcjm97","PzJDJEU9eFjdBJeV2PuzT5vP3n1JHFb4pjEg+6NjD0c=",-7087604833529898507,-7095990622277460656,-4491744718063428436,7242300063999472871>()) {
                                                         case 991657439:
                                                            return;
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
                                    } else {
                                       this.b
                                          .ad(
                                             (String)com.yiyiaddon.m.b.a<"s3re7q0qzs80jc","ntLKU1scQN8rtwsvFhWmM8/lAXQaXoEdkTfeZW1h7RoikgnnNq8p0PK3OwavIKMe/QrQP0FX1ub4YR1+vqw4NwW0VaRVtXBmBepysrZzCl/3DhrSjHMrN3GXWCLA/w==",1539650329575815303,4121810378013975140,-5016268484151288619,-9183485607578073383>()
                                          );
                                       this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                       switch ((int)com.yiyiaddon.m.b.a<"sqfshygr80h1v","U0fFnZx+GK8jM9p6+6zpQC++QobSrv3yBLg/Sl/hY/w=",-774564332128911194,7526276023841248699,-6168818720132079385,-3893969726505718363>()) {
                                          case 174340440:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.hH++ >= 1) {
                              switch ((int)com.yiyiaddon.m.b.a<"s38nchzd20fwg7","ExCRlSiDDb1Lx6yup8vvEaLHz6fc6gs+9GAuWzPG+qE=",7180077105427209922,-8874478019826644767,2304747664242855494,-1223902777791772434>()) {
                                 case 1900234272:
                                    this.b
                                       .b(
                                          (String)com.yiyiaddon.m.b.a<"s364bd7otvrono","ayZGfcYp3I7NSYmBsA97WKg0UDJLzdkKBQNXa2K1gQxsXMsfhcXHIDqUvSL15ZintFxj026I9/eyyY1oLQ8sLH9zWyw7E2THIl8KgkmKagZssvUGq89HpQgo6lfvOsLqv7/Yjg==",-807041204121709247,-7972329461128976397,8174160749505780344,7724474486774548455>()
                                       );
                                    if (this.b.g()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s19rksvwc78ikm","JxF1OHS48fK+RybPCmqQszOnOI0pA9yK20gKuVaSgok=",-9141428733808194108,-6858819303060125224,-7944684234536413788,-3693348520356675879>()) {
                                          case 1364058894:
                                             com.yiyiaddon.d.b.e.a(
                                                (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                false
                                             );
                                             switch ((int)com.yiyiaddon.m.b.a<"scu0yeiz3a5jc","NunGc53zmRqXM0DWpd8VTUwBN5A4ApbfXxrINAVc0XE=",827233603349861740,-386500104585203035,1657955078909051275,-206774292643948650>()) {
                                                case -236258671:
                                                   return;
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
                           } else {
                              this.b
                                 .ad(
                                    (String)com.yiyiaddon.m.b.a<"s3tnmbdd5cc8xc","yEEMRUNeC5KDWOQlcPxO4OYCP7fMC9FKuulgMeFrk29dW5m77kuPMb9pDt5ioa9ycBxHEFdVj9+IWug7qLu1xtUt7fgAKmhQm/PIf/NSnLAhVB2U/c6flUZ/HEhKZVb+w0DsUD74",-1461411405425817646,4211318806181687031,-6777569483888487792,-6713927000838769877>()
                                 );
                              this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                              switch ((int)com.yiyiaddon.m.b.a<"s18iovnt2r1ic2","laB+Sl/03CCs4XcbUEfZy/fIdtR6YT1AkuqYscGGLOA=",5457426226332992548,2131963976794529195,289639713011318908,5398689836819003805>()) {
                                 case -405410379:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s34comsvs9jbqb","5q+EESL5Q30KSeQq8Ku6fykr/9yga9Viw2Ef/UnQgI0=",6387163758811310500,-3298871066702146653,-2740749191513854096,1070194364524087083>()) {
                           case 2121423540:
                              break label244;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.hH = 0;
               this.b.K("" + Math.max(0, var6) + var5 + this.b.bn());
               this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
            }
         }
      }
   }

   private void eV() {
      FoodData var1 = this.G.player.getFoodData();
      if (var1.getFoodLevel() >= 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vuzeqzei584k","bGA0TWuP9HisrpAcdjnrZQtathVMPu1TD9xDAb01RD0=",-4607035179987791661,-5423460883340243926,6477592058572410395,-4840964277355899494>()) {
            case -1888904955:
               this.G.options.keyUse.setDown(false);
               this.b
                  .K(
                     (String)com.yiyiaddon.m.b.a<"s399210z2ncibz","batYha8wleHkQ5UQE2TZ3BWggGFN4sIl+dFzKVbBEmgtjxdwoOY6qGMCwfUqf5z1F51pgtWjw3Unx4PKfUPjumyW",772298194437268653,2595357310085554,5108989962359215148,-116217867788608319>()
                  );
               this.b.a().fp();
               this.a(com.yiyiaddon.e.j.d.a.MINING);
               return;
            default:
               throw null;
         }
      } else if (!this.ch()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3akvqtfkmfq9w","R09u+1KSEuEJiPwbOnlwhi1nqq6S0Fhwr5F/XVV7Pf4=",-2975052201792190467,-6314237778577946338,4506241981335992522,3193182710079898147>()) {
            case 217983119:
               this.G.options.keyUse.setDown(false);
               this.b
                  .K(
                     (String)com.yiyiaddon.m.b.a<"s2z9znexzhrijg","5es3Zmp0YlYrRc3W5JVI3f3ZnmYrh8S//XPxT2nJEO5fMjqP7hq4H8fC38aavQRPIOdedg2mqE9kS0FCIa9rjtX5",3316678733855718925,-5376876865312444233,5997867792388012305,7576783212152188298>()
                  );
               this.a(com.yiyiaddon.e.j.d.a.SUPPLY);
               return;
            default:
               throw null;
         }
      } else {
         this.b.a().fu();
         if (this.ab % 40 == 0) {
            label33:
            switch ((int)com.yiyiaddon.m.b.a<"su43zbacrl84e","h7MqrVvVUV0YY4j+IUdG5qdKuR/WJSXJwWeR0g+pScs=",-6237825144248167279,494573112999536726,2735348524194275999,-282923974256546916>()) {
               case -206747626:
                  this.b.K(this.b.a().bW() + var1.getFoodLevel());
                  switch ((int)com.yiyiaddon.m.b.a<"s1iuu5dk5vwjx5","8ilhCrJDudgLFGnt4tU8eHBNg6UfTNxBIC/6ZrH1WGo=",478999482482134679,8405109985359941712,-1375854703823343900,7825127398038282912>()) {
                     case 171000825:
                        break label33;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.ab > 2400) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ax7lcvogg6lt","iB2zsg+78Z5waOYdEeeQbaQOIaN/Li1FUPMRtXi4qp8=",7782996267821628149,2736807041759643812,38646660855110432,-3312751716940836692>()) {
               case -1494204912:
                  this.G.options.keyUse.setDown(false);
                  this.b
                     .ad(
                        (String)com.yiyiaddon.m.b.a<"s3nvvdw7tav5ry","6TjLYGd+8Zc/dNRvGVc8i5E4Nh1SqYLGKDN6Z9pDL3TVQ5tLidnP+A24LdWMzXKDGuly6pM6p277/FDhTjM=",4563068347982103076,-4938600982085119814,7307197614276834801,-1205992377911755343>()
                     );
                  this.b.a().fk();
                  this.a(com.yiyiaddon.e.j.d.a.MINING);
                  switch ((int)com.yiyiaddon.m.b.a<"s2q8enryx4jwp3","IwKnfXD/81kRh5S/Y2aWU0gWwfvKHa+8jf9wFEfRTfM=",9064551276129545846,6451239197459299928,2290677911707739985,-1019320410710259946>()) {
                     case -1879637522:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private void eW() {
      com.yiyiaddon.e.j.e.c var1 = this.b.a().a(com.yiyiaddon.e.j.e.d.AFK);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rxlvc5bs1rdh","Yj+fh7P6VxlLiARCfwGFg9g81M4Y0WvokiXGYsb6uK4=",-7230005573627925274,-4832908973388776615,-4786735402668144516,8816704008215282277>()) {
            case 916609557:
               if (this.b.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nbi4dj0xxhaj","SCIuijkA3qEJrbC/P5RS6wY1TfKb9UZAMQkRG7gmyeI=",3691921593652899439,-487829385015497200,2059866098185369210,5397679359504203238>()) {
                     case 1963868310:
                        com.yiyiaddon.d.b.e.a(
                           (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                           false
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s3orijc457hdr3","iQEjGWcsI4qayefeYXwt60rLrp+YQUEmLDhr3SjnVyM=",-2754382618697946473,-5105094019056840088,4203218718360020898,-4671427031093196645>()) {
                           case 1518621063:
                              return;
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
      } else {
         com.yiyiaddon.e.j.j.c var2 = this.b.a();
         if (this.ab == 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s12e70ta5bb7fz","Sh8mS7mEgH1fBzxOBoA/bMISOoLk5ynL+TIFVXqAX04=",-1772103788446731377,7523398659129680467,1959147218284369516,-525865978428366063>()) {
               case -1242110897:
                  this.o = this.G.player.blockPosition();
                  this.ae(this.b.bP());
                  return;
               default:
                  throw null;
            }
         } else if (var2.cw()) {
            switch ((int)com.yiyiaddon.m.b.a<"so4hhuyb8drwi","WGqrTzji8OcGHWmUvpY2AJoKT0gR/SCo7PxX64n08eI=",-1005417481005964159,7530994645590384199,8431481950949309192,5561007862812629352>()) {
               case -924192197:
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.ab > this.bx()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ay122wenkop","fGJADB/PG44+8uWH8nV/evHWztbVbA7a5W+XeoKfA4A=",-4056978608743123347,570413023596056938,621462105030681703,4706659802327990665>()) {
                  case -631531734:
                     if (this.G.player.blockPosition().distSqr(this.o) < 4.0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2v9gnkvojw6k0","KgdYPzJ3iTFtR42MLr1C708yl2xk5+oRA0eN8vd+YkI=",-3919779938123808557,725461944107271538,8505587759422568006,7982704138668123323>()) {
                           case -632789778:
                              this.b
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s2f0wc58snny4q","fY8v3rqrrQJA9ZYmzUiGleC3AdxfGFXMxHxh+EZygUOLku/ZU/hiqRBTbWE4HUTaW7cXdVWjbSC1jfHBUb4R/nuKMLpZ7XJ+cGvGcolKX/pwnA==",-5571744527975159873,-1702192517439217062,-3365835278196346969,4698707801363503204>()
                                 );
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2w6pu4k9njmmx","Jb4mWPe6uAcOeDbDMI7EaVhPXzWyE7KHcedwgyTNnLg=",-127785817750347191,3462647989144568622,-8771041095465345126,7125679794629152786>()) {
                                    case -1051219066:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"sndyaj3mg8ot1","Td/ylta9QXnCIt8KwY56XGBSbFhj6dyo7uhWVyRLPrM=",6811531362092663545,-5543393131758141540,8949011817095043062,463635147687911226>()) {
                                          case -1918333474:
                                             return;
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
                     break;
                  default:
                     throw null;
               }
            }

            if (!this.G.player.blockPosition().closerThan(var1.a(), 3.0)) {
               switch ((int)com.yiyiaddon.m.b.a<"s3okxbvm50reho","AhGUfkCTUVsmsM3mqDwXLqqdfFy7r9XUkrQDFWmq7VY=",-1303003205308552625,-3144529340625248965,-7030917298243892148,-8890821062622737671>()) {
                  case -826015280:
                     label264: {
                        if (this.cx) {
                           label173:
                           switch ((int)com.yiyiaddon.m.b.a<"s3fym2yqadbono","h++ThuKklc/MfhiAgOQUQfX4//p+k6EEyIefxRPPE1I=",-3662851472107552939,7877882241922549723,-5692114797694965890,-299456473050536506>()) {
                              case -1886733702:
                                 if (this.b.a().co()) {
                                    break label264;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1avsz489c90dr","4dcL69d76P+6vxAIaOE5U25/honiSaVSkzRZS/Cu5Go=",9006401195471236897,8450791110826226838,-447221689560855187,1308036383109139289>()) {
                                    case 99274396:
                                       if (this.ab % 10 != 0) {
                                          break label264;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"syvrvowru9f2b","nJMboMPhV6aM2+HkdT8cv1dPpQ8iSyYCJgXB2l2JCXI=",-3382038279049655752,-6857808831503499761,5052619831133633093,2370324324828548970>()) {
                                          case 1641095299:
                                             break label173;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.cx = true;
                        IBaritone var7 = this.b.a().c();
                        if (var7 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s238d3s6ay0qgf","3gZbJA0aoGmM2B9Ikld15KwJNopMNzGUNS+cMY7F184=",2172851567242740730,5930442212038699705,6687554517822723020,4180337007227872915>()) {
                              case 1210615371:
                                 this.b
                                    .b(
                                       (String)com.yiyiaddon.m.b.a<"s2s2ik0hvk27mf","6msl2r4sG2dCSVMjnNuti3o5bv2ISM0+TrbmYLabbMCsTimbgGR5inc0OeHqnKNLsTbZXGjiRzwzgLJntEgiDskxNDwxpYKJBr/AKHl1j02aVEZh",-3743396571186123996,-9164155940537399996,150715614021068040,-6306694361811757055>()
                                    );
                                 if (this.b.g()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2y9nv7t3ja6fr","Ob51UQ1b5XbsKfO+misTCrPX6LBKgOFKYFFW+U331Ac=",53887967602924967,-4309512688620607181,-3811963134542664062,-8534960578689310661>()) {
                                       case -1748463309:
                                          com.yiyiaddon.d.b.e.a(
                                             (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                             false
                                          );
                                          switch ((int)com.yiyiaddon.m.b.a<"s18otrvd9owdx","jZfjZSeeCmc66k3/FrDRyFQz1ZdG2PdbnWetROS4Pkw=",5859703249937797171,2579736737022131504,7949281014257227133,7652012878705204967>()) {
                                             case 465857445:
                                                return;
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

                        var7.getCustomGoalProcess().setGoalAndPath(new GoalNear(var1.a(), 2));
                        switch ((int)com.yiyiaddon.m.b.a<"s256fqz3ynpbqm","Et0eiDR4D09U/X3DYW/xjp0NvAKgQ0uXW0NYk/+/roA=",2122360595465457479,1775487082502548053,-6122624628622687752,-7053644283162451424>()) {
                           case 1034196658:
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (this.ab > 1200) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1u1tqsdwoy7ix","wyYPihuhwbmLx5WV5KqO0V70a24R8XUGiZkBqLlMd2c=",-5118520620734451266,-3135326859824271250,-3393125179406962723,-7767357015416113749>()) {
                           case -1139383256:
                              this.b.a().ag();
                              if (this.b.g()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3kldploq2qrif","/QV7eXPP7xH3xdL1QS7qYqOKvMcVobsIzBlgi4r+22E=",9147925495016517154,-998278769735451098,-8826804524258839015,670148954526618838>()) {
                                    case 1028436511:
                                       com.yiyiaddon.d.b.e.a(
                                          (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                          false
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"s3o7bq9liwbp2z","oq9Mm6lsR1GbsaLgulTp0sK+xYv7do82v9jF3EJw3SU=",3659260115106038141,-4927785734392753801,6433390097223688971,-3501839988213645417>()) {
                                          case 669062338:
                                             return;
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

                     return;
                  default:
                     throw null;
               }
            } else {
               this.b.a().ag();
               if (this.ab < 100) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2wnr3sp29u790","GBYI6gbjqNm4fVvihud1sKG4vbjs8Cv1p4feIgl0qDs=",3347998160257902597,4465912112963974343,-6801356672425858482,8888547176227727468>()) {
                     case -1840141637:
                        if (!this.a(var1.g(), var1.h())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1yr2fud7zo5by","LVyCOMbnFT4xh7aHV2nZwpl0xRXbF8PveYupIi0VrUU=",4061373763349561210,6469548933693216526,-3507304364480332318,8923452040666776870>()) {
                              case 121996346:
                                 this.b(var1.g(), var1.h());
                                 return;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }

               if (!this.cw) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3n81xxqeuxvgz","mE9CT3uK8bBXGbu6YDfF9/aeN7f2gQyT4JRrR13mw+I=",1330547651829620401,-8772328109816623910,-3677646149877186942,8683430420589878048>()) {
                     case -1243509128:
                        if (this.hw == -1) {
                           switch ((int)com.yiyiaddon.m.b.a<"sjkfgwwso5t7x","GjZHyYRei9QXoTPwtL9gO8DLb/6FxQoZENFMUocxBUI=",-6743097980374337388,5308986889503135676,-6050105957989493507,-4688645338384783320>()) {
                              case -132528030:
                                 int var6 = this.bz();
                                 if (var6 == -1) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sn2txmpbye3kw","CvJsyCwqCCICz2gHSl4S6aecQAykCUE0jR7OYpIYfCE=",4706628157144899019,7148001861991003105,-596598674024816187,1910180894277998314>()) {
                                       case -1991931948:
                                          this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                                          return;
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    this.ht = var6;
                                    ItemStack var10001;
                                    if (var6 == -2) {
                                       label191:
                                       switch ((int)com.yiyiaddon.m.b.a<"s28pf0q72oi8kw","gaANsV2rYdaPGfYyLc9xVTM5F/84WsxQy+ThdShyvRg=",3036922179459895966,-5286655701563669405,-1231170626589434005,-8408687090244946214>()) {
                                          case 1746477587:
                                             var10001 = this.G.player.getOffhandItem().copy();
                                             switch ((int)com.yiyiaddon.m.b.a<"s17r8xevdxiyhs","HQw7KZJBKaaIIwoQLxst3H5W55oTzFTQFudKgTcURgQ=",-2408171236573397652,8748190964724545349,4798036886512124498,4646516075267685762>()) {
                                                case 598671075:
                                                   break label191;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10001 = this.G.player.getInventory().getItem(var6).copy();
                                       switch ((int)com.yiyiaddon.m.b.a<"si2cfib3ypb7z","B2QXj7dbGaBDPFJz23COFrvxrV/RbdsodXdq/y/JIXs=",-5306011411675711162,2328665904611121640,-6950410005741211431,-897612604050232392>()) {
                                          case -245608002:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.i = var10001;
                                    this.hu = this.bC();
                                    this.hv++;
                                    this.hw = this.ab;
                                    if (var6 >= 0) {
                                       label186:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2naiq8avcxv4y","RKA+AA/iYcgAd7mEKyJmY78mdntztMLJOWeynfDRP1U=",5924962276940282481,-3598687961529854796,8526079307388113258,4232700061917806242>()) {
                                          case -1059006137:
                                             this.y(var6);
                                             switch ((int)com.yiyiaddon.m.b.a<"sxyzmznspaunm","Xpi3vqBI3PjN5tfepivLmihA/OyT8qL/vf2K31z5ezI=",-4715746960897871561,2758024543566531529,-2420191318036169501,-8521288232570958889>()) {
                                                case -1245579158:
                                                   break label186;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var6 == -2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s16p2bftb0omki","vA9mx8fiQwYxYlCZBh2TuHc8LyvVZeJlmDfXJCjMcoY=",-7292137112925006043,5626312134177655021,-6981992456187460528,-4209390186955749902>()) {
                                          case -1398995842:
                                             this.cw = true;
                                             this.eZ();
                                             switch ((int)com.yiyiaddon.m.b.a<"stpkzpbbs2bsv","G8+utIGvnnFqQoN2Bs30jz2FaBJfptNnP+QjHuDtJOM=",8990708946020315180,5181767359393224057,-4957359403524365832,4603879708460158075>()) {
                                                case -377789444:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           ItemStack var5 = this.G.player.getOffhandItem();
                           boolean var4 = ItemStack.isSameItemSameComponents(this.i, var5);
                           if (var4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1qmemrin17e1d","z5V/f0UkzUdKUXpfnomFRtcEorvTG2ZrogaCRTf2IHI=",5832473773433757538,-3040049752524943207,4793736902167102345,-3512731813756023017>()) {
                                 case -1944222668:
                                    if (this.hu >= 0) {
                                       label201:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2hsccs9o9aofb","fuS1bwfSeKkJFqzmzwrSvzhvjcHXwSWAMSFmZVFB7WI=",2858340846430951726,4277982494047690811,6438180196073956958,-2003217980099162725>()) {
                                          case -227751464:
                                             this.e(this.hu);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1j0zlwe0rrnew","oMjaiK+6fP4F3tVWzO8hie5Y27QukLUT0ggUzRaUP44=",-3193170524986394537,-8131218349060804506,-6603883942173526483,6667184478545350790>()) {
                                                case 185019366:
                                                   break label201;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.cw = true;
                                    this.eZ();
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              if (this.ab - this.hw > 15) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3hd67rr261gff","PWzu90AUz5F3HRpgR9Bo/4FclHzkH8OTrZ2vVW+6O6A=",18672561166348542,-3171007635100177874,4481776460659634279,-506031889762911847>()) {
                                    case -892455357:
                                       this.hw = -1;
                                       if (this.hv >= 3) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2d8ky7oh9sthy","ztJIrseLaaK3zmNoNxxzlSk9cIENeLrcHx/h4/VTYEI=",-7052263611872984164,-2642162641469240267,4116673859418025322,3388086490265172170>()) {
                                             case 170241937:
                                                if (this.b.g()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1t7o52jfvs1ui","bFjD1+mWfdK3zJfrB/wfKl76KGW4UxLq0Ha/qTBLlRY=",-8805319198413914437,2308065388603830049,8458916174679688613,1450479332513196568>()) {
                                                      case 1673264466:
                                                         com.yiyiaddon.d.b.e.a(
                                                            (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                            false
                                                         );
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2e28rs4g4n00g","/QvT3SJKQjgNYQNBVaOqaBhDLsjCK1MPYYgt/XvcBRs=",544870667920346977,5035214482780076863,5053658765018889853,6235627777102164846>()) {
                                                            case -819781656:
                                                               return;
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
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              return;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  ItemStack var3 = this.G.player.getOffhandItem();
                  if (!var3.isEmpty()) {
                     label230:
                     switch ((int)com.yiyiaddon.m.b.a<"s10u0n55kate7q","C8nNyg6SHy5PSN6TaETqv1OZSBHTqsnNdOZKCXkUzj8=",1701551648030963582,-1900195193832040796,-7678385118427638097,-524719472061641790>()) {
                        case -1757753047:
                           if (!this.v(var3)) {
                              if (this.ab > 12000) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3j2lugmx8u718","dxvi66rt8DbflrZddYg5ZxG0T2KboFAJ30goCLll8LQ=",-6813904766092324225,-8824228427254972467,6039925418190300337,-5966299919310315812>()) {
                                    case 1852575033:
                                       this.fa();
                                       if (this.b.g()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s28e54al9d7t1z","EcqZaY6sEk5EIQdUse+O/HqIsJ+PXFMdBe9W+tlsyEs=",-3978591422705496067,-8993081449897449993,-4436752029585500365,1773842059814347561>()) {
                                             case 2011178598:
                                                com.yiyiaddon.d.b.e.a(
                                                   (String)com.yiyiaddon.m.b.a<"s3bctp9hhccdj0","gl1EmnLbKTUjnIwpdtHFgmS3YTTDNv2OEWLM2gNT4iALWB/WZp7uxw==",-6309827877430347943,3558541468411478271,4067321126418335596,-1570158568642334802>(),
                                                   false
                                                );
                                                switch ((int)com.yiyiaddon.m.b.a<"s75st572102p8","Eal+8DftDYN+fXoEjf3ivE47OVDZJtQpQL1+8037mtI=",-8040621373172594006,-5308821615999325796,-3970638630676270091,-3344122269647455685>()) {
                                                   case -1696596476:
                                                      return;
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

                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s17pibinbhul1k","nY7Y/K6DIx9X4tg9EPmbMHZzcqfh9jqF9sakIiHrUgw=",8699599721118355654,-8901176110638030058,-8609863741498045464,4200828441279560697>()) {
                              case 856528870:
                                 break label230;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
               }
            }
         }
      }
   }

   private boolean a(float var1, float var2) {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mxk4fk6vac7a","kZEjNAD4UsNdJco7CZPCL7JcijCOP9BexDMAq6bGYz8=",-8928711780445473098,-1749134783781823820,4875788223643921456,-4873200919613228026>()) {
            case 176578214:
               return false;
            default:
               throw null;
         }
      } else {
         float var3 = this.G.player.getYRot();
         float var4 = this.G.player.getXRot();
         float var5 = Math.abs(var1 - var3);
         float var6 = Math.abs(var2 - var4);
         if (var5 > 180.0F) {
            label35:
            switch ((int)com.yiyiaddon.m.b.a<"s1d4cxztg6s5xm","i94XzD9SaLUUEDBzP0eOgdcGzCkklvyqmcuWdFqc/rU=",-1183053637413383177,-7626398960647238613,-4847614955084211709,-5721405130440889474>()) {
               case -698909022:
                  var5 = 360.0F - var5;
                  switch ((int)com.yiyiaddon.m.b.a<"sg78nbhexmfcr","gLVQmpCLV5HbJHbXrB/+BmOj9TYAcf4lmg+mICPQXAg=",-4490452676660804855,-1324364915900254123,4785546668683639933,-2755178403839098365>()) {
                     case -1229888425:
                        break label35;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var5 < 5.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s3brrdr4cp3rbn","kAwr2tIWWklIUpPK42Yrg1lhkwXb7nhP9smJ9KSXkhs=",1880652129878812141,-2770296805254175252,7348520645365104140,1343961636144500324>()) {
               case -1735492435:
                  if (var6 < 5.0F) {
                     switch ((int)com.yiyiaddon.m.b.a<"sbovfriq9frlh","mO40RLI3Iwk4+BI+lU5fTSnHVi4p6Z9BhpK8TQiP+ZU=",-5808786311996738584,-4412844637493012717,-3919510019841016006,5165281958610648255>()) {
                        case -3814004:
                           switch ((int)com.yiyiaddon.m.b.a<"s2dbmlef5vjqu3","Nv2Lz79G14InXZHwTTjOd/GLnqrdjHpbXv0JUPUQ3q0=",4863752926073036749,-7974877446521074225,-3121248939709186668,-2789352282558579631>()) {
                              case 1390622505:
                                 return true;
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

         switch ((int)com.yiyiaddon.m.b.a<"s319hz4kt08654","+jWpw+709b09yduqZjGaeZrK+Q0i+dw0I8t2UVxl+dA=",6399850331097707903,-4894912397863399241,3354176102280690000,5471690441501418274>()) {
            case -1358575259:
               return false;
            default:
               throw null;
         }
      }
   }

   private void eX() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zorbvkgdwz0","BFHAx/1GOVqhASixa3C8HwkHKQBI8penWxgdPNUJE+A=",5839626790002880651,-5651177204317949799,-3444298746647592363,9200766088736496787>()) {
            case -315561451:
               return;
            default:
               throw null;
         }
      } else {
         if (this.ab == 1) {
            label35:
            switch ((int)com.yiyiaddon.m.b.a<"s12lr5h6pe4ykl","2tE+1aeQ60uOx2IbdOD+z/u4nLxLuO+vGkjWq62qABU=",1239029587775869239,-3342256769989945684,3927723638780124371,975063186592923339>()) {
               case 629759464:
                  this.b.a().fn();
                  this.cI = this.k(1);
                  this.b
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s3d9y8bma7k744","Sqye90W/6ZLNOXxAAzGv1cre6PtuIUT+O+5IVs0SSTaFPkXLhK0xuZ8oeWbHtdW47T5iAZ6O",5174807902993507852,4419041860553910650,7980652245230300367,1835290705583768663>()
                     );
                  switch ((int)com.yiyiaddon.m.b.a<"s2pr5dxqemxkhs","k7Hf4QFRagSbKXA94u27PWONKX+buYr0088tINF97yE=",-4685353140532734682,45623013548406711,6128397998113199426,-7271822052614428116>()) {
                     case -1576091617:
                        break label35;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.G.player.isDeadOrDying()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3crrppq3fyw10","WjzzX5/1QrBO2Y2c1D43NZafvNKrNWrjZ5/2f8BeGZg=",795239274467947843,7979923642108368191,3720019209496805982,3841156119121321052>()) {
               case -612751530:
                  if (this.ab % 20 == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s29fb64r9186by","MlSh9sDDDvDixOqRyeHR04RODypLFx5B0jmR74D0jsc=",3728440185807067878,-6919025257413649339,-3030004192525192354,298596822910855724>()) {
                        case -1281368496:
                           this.G.player.respawn();
                           switch ((int)com.yiyiaddon.m.b.a<"s19chfpsmohhjd","8XFZAzVwofmXFOe9AZ81b7GOBRVO7oR2YbPtGEbiH9I=",-2532603091154796534,6503316011471397877,4046182671198159390,-8987393522870048419>()) {
                              case 1257515760:
                                 return;
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
         } else {
            this.a(com.yiyiaddon.e.j.d.a.RESPAWN_WAIT);
         }
      }
   }

   private void eY() {
      com.yiyiaddon.e.j.j.c var1 = this.b.a();
      if (this.G.player.isDeadOrDying()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1y4ozomppeae0","5Wdx3SAUJQ0H0wQAbLzj+/eInMTXuoHX3ICSbvefAXw=",-1571890809236742755,-6068706283786599066,-7199958798395988612,-4098434083554142671>()) {
            case 501714563:
               return;
            default:
               throw null;
         }
      } else if (this.ab == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q6wltw6upu2e","9aEf7+hG6Q3dkyucxKlYEMDDoDjzk1vUbBl6EXINRNI=",-101780403398709794,-8418233290692060025,-5136942839675657339,938057340541865343>()) {
            case 2036366849:
               if (this.cI) {
                  switch ((int)com.yiyiaddon.m.b.a<"s12eh589d38e3l","BvIlkwParyDvhKE4tBOQL6jFxde7XOvID3KXHF1aalg=",-785878196656201489,-2029515395563061148,-2269880357892856114,5780719834969258727>()) {
                     case -344564387:
                        this.b
                           .K(
                              (String)com.yiyiaddon.m.b.a<"s2rddz7x3o695z","eC+5bri6i9njyb+MvNq7cWHQ3Bb4pxx+9lITb1032vCY+0qgAH+htdooVfKsE7/Bul8uEdnMfkrfddhoJ31NCSxRJdG9Mvv9zyOQOoRHBAKXUutl",4934079618794319728,2466500963641373441,7482260324280184449,1972522470314664835>()
                           );
                        this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
                        return;
                     default:
                        throw null;
                  }
               }

               this.ae(this.b.bQ());
               return;
            default:
               throw null;
         }
      } else if (var1.cw()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hwtyq0e8sv3q","UhFtMR042W7pu8qqHttyP2t+C8hI94CO2ZKFuqSLVug=",-2968884013881304525,-8506545955061361499,2584383243413544856,7178661221491368840>()) {
            case -103849656:
               return;
            default:
               throw null;
         }
      } else {
         this.a(com.yiyiaddon.e.j.d.a.GO_WILD);
      }
   }

   private boolean u(ItemStack var1) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sshlbw7g4joa3","3ok/IRn5jDFVKkL84+LkUNoCKqMkKoTv4WmFaCHJcdI=",-194811032584541752,-5076811610016096472,8722171446193805835,5662056475881619067>()) {
            case -1561363385:
               return false;
            default:
               throw null;
         }
      } else {
         Integer var2 = var1.get(DataComponents.MAX_DAMAGE);
         Integer var3 = var1.get(DataComponents.DAMAGE);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s22njmtedr1qoz","/2QB//a2lDtwa5wRNEx/dj1q0wzA8QPFBfi7ZgAa6oc=",-1199521943434615445,-6177966635607625022,706797005084847149,7420534999792916146>()) {
               case -1461759208:
                  if (var3 != null) {
                     int var4 = var2 - var3;
                     int var5 = Math.min(this.b.bo(), var2);
                     if (var4 < var5) {
                        switch ((int)com.yiyiaddon.m.b.a<"s21l2mml7xvsmr","Ao1sEHwpHDovFaqnM6iLsYsiHoJDHs8FUFaTVOjYFMo=",2835854588523708974,5413208139536319472,6560461544226281358,6966985623025944644>()) {
                           case 645062023:
                              switch ((int)com.yiyiaddon.m.b.a<"s3mw2pjr8oh1rc","JIXjuz6CVltGn/QX2Bl+nviTGIvV18eiPV+DdynoeqU=",5167183483629612608,8247067680277465515,7246395700877757621,5985614182251418276>()) {
                                 case -1007231569:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s2l3h9eq5v6mgv","C5mIAwK8KcNKz67/5casMuhOHFSlCN4vEPyYNEmwpGs=",-1331555661218967550,4762177914146247674,-2843433991173521162,3274369923921433920>()) {
                           case 85383732:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ddlwxmy14fad","/ApXeNjj6FExsiYPvW8GsP0VnVxm5aIDYhMimjjBfpo=",3027938107813589351,6998300146289266789,8699062139846382873,-4020237960711952156>()) {
                        case -1724338869:
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
   }

   private boolean v(ItemStack var1) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mk1irye8un2s","HmgecBBq5Ru3Y1u50dMxPSbNaktroGZ+1J2pfHPgDSE=",-2258403284421625676,8918306431574873818,1451913519703965186,-4532411260666192006>()) {
            case -1250031163:
               return true;
            default:
               throw null;
         }
      } else {
         Integer var2 = var1.get(DataComponents.DAMAGE);
         if (var2 != null) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s19e15ay6kt4jq","6tdH3IpQpHlbFutC1y++yiAupTXUTKttbaMHfjurUvk=",7383232021144370643,119426845753413835,-1243454885617622618,4791467233294460304>()) {
               case 514639626:
                  if (var2 > 5) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2g395cz6xjmnc","xWycYHACudR+zceUotP77nYQo80G8JDgpMgCNbmHp3Y=",-8416922224833302940,-1793433622484601436,4847222012867337942,-3754914504821984777>()) {
                        case -1654289995:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sbp3cejm2066l","FRGAkrmf7f7OP+Gp661hsJHqZ8vp1pxeE4UXzWyHCdI=",-5491268963661316422,1993205987337005413,1632814336738077681,2808449942278450115>()) {
                     case -316902909:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s1x01diki58fc4","QdNNnY1t75XriTVYcLLJbFzn61lImNwTwQpkB+l/YnE=",-342433470183026056,8618472497981406570,5756968494011252481,6429428526282973004>()) {
            case -1640145439:
               return true;
            default:
               throw null;
         }
      }
   }

   private ItemStack k() {
      ItemStack var1 = this.G.player.getMainHandItem();
      if (this.p(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s224pijqodu482","UDE92PCVSRealoC55ss++tdwXwYtjzI/xeE6074WAiE=",-1459049574041622451,1689074935597069467,-2139751198240998928,-6950248707886832990>()) {
            case 229688941:
               return var1;
            default:
               throw null;
         }
      } else {
         ItemStack var2 = this.G.player.getOffhandItem();
         if (this.p(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"s224adb56brl5n","313k4CFtLaPt7p3wzPM7KnQyeXJjMFy6M0e3LNcutYw=",6450154478959553774,7611687417537171009,1306202440813365770,840495241232369364>()) {
               case -512676806:
                  return var2;
               default:
                  throw null;
            }
         } else {
            int var3 = this.by();
            if (var3 < 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s344drree8bril","a0Cj46cpISpAz94/wsV3PdHWBitl/jQdj8SipVXK6Tc=",2191512294499473029,-3956989047481811683,-7746022971572347415,7557774138761038022>()) {
                  case -1068188829:
                     ItemStack var10000 = ItemStack.EMPTY;
                     switch ((int)com.yiyiaddon.m.b.a<"s1jyda1r7jmelr","Nc9LUmxoeKzTKUAs2h8hLv5b3VqHRVc6zC+H2nDHL8k=",3585337526079746469,6269423736575270252,4153227352990671596,-58854205243316717>()) {
                        case -176338373:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               ItemStack var4 = this.G.player.getInventory().getItem(var3);
               switch ((int)com.yiyiaddon.m.b.a<"s3h2asxw9w8w1f","E4izHGPRHdSuJyoLWl0IAfr2ZfaChr7K5yPN6CjVXbI=",4492326206934150898,2597269815714648728,-5582475328305420182,-1570769254020174668>()) {
                  case 978813922:
                     return var4;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private int by() {
      ItemStack var1 = this.G.player.getOffhandItem();
      if (this.p(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2btyf9hjdl7th","GE7NHT0ovjKfQftItVo+ubgo1qvoj6jh7jGnN54SFV4=",3957173178033992862,-638794212173441041,7377900930432083171,-9082489008780570887>()) {
            case -150509908:
               return -2;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sfotj61uxooiv","cY75w7/gQqGsAapDaSHK4JTHVkzSAnzcLOSI4JL4oj0=",-4641588219263481226,-1763231670646687195,-6054692991607029400,5565379185046257418>()) {
            case 1835891019:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s7pvys7kal9s6","KapxF+6lHw2bHYnD9vFznyBKzIJNH4UMPYLP4NXk6yE=",4870958820543396140,-4829876298966186348,5112036571867749469,878823036321018514>()) {
                     case 1627176180:
                        if (this.p(this.G.player.getInventory().getItem(var2))) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3pgqxfux3t8pb","GsF6O0kaZ4CWPgp+DyzoLwzWotj3dFHeSgzuBRxo3ew=",8258098367639620663,-4608549097122424590,5221547705891284648,-4873285817730475774>()) {
                              case -102301197:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3s2350uiq1e7f","Fw5Au2U2HoOQv7pvYMxzOJfsRujJ4IEFxZkh+NxSg/I=",-4443604479493429474,-4873751869472434513,7675414271715699676,8316160252668798539>()) {
                           case -1208656158:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return -1;
            default:
               throw null;
         }
      }
   }

   private boolean p(ItemStack var1) {
      if (!var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s26ycbljldiz6","/q5R1p7UYH7wwLVBL9s82KHk3b6mKkWKstUxnoRlwfU=",6950338477153885826,2341250828435084437,-5748176083439285783,7955309385077099635>()) {
            case 1240179733:
               if (BuiltInRegistries.ITEM
                  .getKey(var1.getItem())
                  .getPath()
                  .endsWith(
                     (String)com.yiyiaddon.m.b.a<"s1egwrud7e9x1k","M3V1Z3LN0wQz98M71LlyexoCF4GxnnX8XowvO+gU1bOy4GxXql7uI25grlY=",-4798133136666810226,3402916450350093171,9193832565369945874,3479632722195610649>()
                  )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s28ve0qlzj56b9","8RjsI2zUGqdQDXS4vNz7J5eIBqkQ3spicH4dyuRcjc8=",-1075155813984888697,-3449929510546197573,-6519365738345771201,-5472620688127159102>()) {
                     case 686680287:
                        switch ((int)com.yiyiaddon.m.b.a<"s6k2dcjdih5fj","TLPH1pZBleTp+5oPir2wxb+y+29523eticJ9BMLP3Ns=",1502236206737588214,6806775584356712039,3058518516611597200,-1650286765477328337>()) {
                           case 868453347:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s3nttxpvuahh7w","OusFByF3d7swIgaeX7b3RmEbaxuCdUqRjyInILskC7Y=",7049887969770437561,8146072170549814027,4056615121705745526,8283461247238435183>()) {
         case 509960221:
            return false;
         default:
            throw null;
      }
   }

   private boolean w(ItemStack var1) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vdtvwwrpsvr6","6dKKjp6mLjlPvTOiBlSqNUG663JVqEHU4Mf1XJegyA4=",-7882704354311781452,6094347306881381648,-6419594171799840821,654858350984209665>()) {
            case -1956976214:
               return false;
            default:
               throw null;
         }
      } else if (var1.get(DataComponents.MAX_DAMAGE) == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dukgktjep9p0","0r8TKBssRY5oIwPh2JwETmwAwNjqaOMUjDk9hgvS94Y=",-2736878968713156182,-7896593334470804356,7463183492756527830,1465723379140281530>()) {
            case 642503376:
               return false;
            default:
               throw null;
         }
      } else {
         String var2 = BuiltInRegistries.ITEM.getKey(var1.getItem()).getPath();
         if (!var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s1egwrud7e9x1k","M3V1Z3LN0wQz98M71LlyexoCF4GxnnX8XowvO+gU1bOy4GxXql7uI25grlY=",-4798133136666810226,3402916450350093171,9193832565369945874,3479632722195610649>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s2q4az20smls0j","bOn6ccKd02LsFLYO/m0ZFqO2MW6psV3+nTE+Q8G2Tuw=",2005358183935747122,3432734623592580118,372797025532634524,-56330862101063899>()) {
               case 758927843:
                  if (!var2.endsWith(
                     (String)com.yiyiaddon.m.b.a<"s1pcot52pvnzf9","aq6jfmY8PnFq12q1TsO98O3zX6aFvIhNYb+wz68Yqgl5mr2FmB0H/2mT",4777041856529764652,6122033436622900576,4792251822365445244,-7762378468675245595>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1iiw60smz1mwt","LUgeqtf/DtQNBaA14baHKErMHuhon+6j/Z54tM2ukv4=",5476551299232335377,306335554931391424,-3160200352546408926,-9082090689361141703>()) {
                        case 473437397:
                           if (!var2.endsWith(
                              (String)com.yiyiaddon.m.b.a<"swzgke7cvlywf","NUA3dsPqd3tFU3UO2/jGSdYrc4yVgJfnzrAtIctV4EYfjAcq",3052580125312492911,9003483517307543433,5944124624409338849,-5337822837877182393>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2apvk5budqgf8","HBxYuIAyDRVhIMkVBxkHdVHhdWRpFXI+DuCztYtR50g=",7794849746829848832,6891827644509200163,1861031988719025314,3983457781879633415>()) {
                                 case -768952064:
                                    if (!var2.endsWith(
                                       (String)com.yiyiaddon.m.b.a<"s2fj31hrzmju9v","b9+CiRcrQ71LUFz2pjH1DB8CL+br/iTeS6TlI5RzcQ8lqHkQ",4941188968491097763,4557235653371501505,-7343985028105800865,-8828452674186514088>()
                                    )) {
                                       label41:
                                       switch ((int)com.yiyiaddon.m.b.a<"s38kveg6clrdbw","aXFjG7vmWMXIkOkpVQ14hjgPkqgNptGfjIGnAoS93nw=",1114478461686891821,-6858137192650441333,5120628440619054303,2742460849959045689>()) {
                                          case -556714272:
                                             if (!var2.endsWith(
                                                (String)com.yiyiaddon.m.b.a<"s2px7kuvaazps","o3KKv1C+OFLqI557/tMsDZ62BqAxPb+RsfyqolE8hsU/b9EeH+rxIw==",-2189487092590524475,8812490183552326748,-1374139359943931453,-7610460197100915876>()
                                             )) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3bfmomcdk2t7e","kqNjCXkbuoSwe1b5oWExnTJCaxIkMKjYBlrEeQ0gwQU=",3296587232750140648,-74332717117882456,4888316677414034507,-1990813809373851609>()) {
                                                   case -977694204:
                                                      return false;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"si3z5tkktardz","FbncL0yW7pP4YNVSSdK8ZFpfXarkvBN4U0XYqkEwnHA=",322907475353676581,2286255422613106593,-6877858747895685497,-6441417165272222737>()) {
                                                case -1378826632:
                                                   break label41;
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
                           break;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s38xnbv2dammpd","Ws5kgfUpoqQ0sA5SWLN5Wsslgp/kBIaFH/V7hkmPIn4=",-4368235706768994860,8527565035917148466,6426324461645834521,8370743927945487328>()) {
            case -104000801:
               return true;
            default:
               throw null;
         }
      }
   }

   private boolean x(ItemStack var1) {
      if (!var1.isEmpty() && this.G.level != null) {
         ItemEnchantments var2 = var1.get(DataComponents.ENCHANTMENTS);
         if (var2 != null && !var2.isEmpty()) {
            try {
               Registry var3 = this.G.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
               Reference var4 = var3.get(Enchantments.MENDING).orElse(null);
               return var4 != null && var2.getLevel(var4) > 0;
            } catch (Exception var5) {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int bz() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dd86fqpuivxh","sV6CZEd1qSucHafU4sjvrbmNT/QTVADUB4xTJl5HZcY=",6513257863813838929,3872221687608627969,-8215900192731458834,505914468113978901>()) {
            case -1867253061:
               return -1;
            default:
               throw null;
         }
      } else {
         if (this.w(this.G.player.getOffhandItem())) {
            switch ((int)com.yiyiaddon.m.b.a<"s1euwcdukexl58","vk+V/UxYIsPpRmAvkgDCZOfeIHcl4lTRjYIZLmlUmaM=",-6229564083499412654,-8071879294281702869,5899602223249752187,8715297780189211939>()) {
               case 336015560:
                  if (this.u(this.G.player.getOffhandItem())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1eimdqq5vfxvy","XdifaWXXwcOj7ZlCgsEigefqsTMtfL0bcijmjsLAmF4=",-7006745976736416896,3666894603318817327,-3618638266337381176,-2353838587075222394>()) {
                        case -1039907866:
                           return -2;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         int var1 = -1;
         int var2 = Integer.MAX_VALUE;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3v7fhsit1nqt5","byQ6k+zjkxevGQt0C5qflGTcvvGBM3KYkVih59TWKCU=",-1624260776090751868,5689478478355584059,1771531993782085831,-5485626214271904134>()) {
            case 1876303871:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2oyet8yfez7xa","g8b6KiveynMZfQZnPkj/5AUTk/Djs4e6SJWhJ6A2kT0=",-1769052714485607953,5355266876518707407,8874127415328155891,6380962292895461758>()) {
                     case -996814588:
                        ItemStack var4 = this.G.player.getInventory().getItem(var3);
                        if (this.w(var4)) {
                           label44:
                           switch ((int)com.yiyiaddon.m.b.a<"s5of35amsq46w","4b1cZUbosaMtSAyNdv37W3duQKAcHIZUX5na8Po2oc8=",6444134516903761907,-3904232633752785558,4417782661971211615,1768660880920252099>()) {
                              case 523917918:
                                 if (!this.u(var4)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2w5nfcxwpc93d","I9FSbQXpgwqPHG5/BMTCcBj8MmqueaHCHfOh66I+77k=",-7669390705884853626,4881175223998501864,-5739901164061060413,620734774621313909>()) {
                                       case -1838915903:
                                          switch ((int)com.yiyiaddon.m.b.a<"s36qhanqm7bhe5","tcjo2kN8Hwc6kuUtZk/X1YIl9+NYdCMTgaU7VmUX5OQ=",6189036724948805205,547988706095093676,-7234113067488653089,1515879475071981504>()) {
                                             case 1776050957:
                                                break label44;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    Integer var5 = var4.get(DataComponents.MAX_DAMAGE);
                                    Integer var6 = var4.get(DataComponents.DAMAGE);
                                    int var7 = var5 - var6;
                                    if (var7 < var2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s351oa0jknudj9","wQYfDcJNFPBQDAYXj0pcCDepHhudnGT9xMujZyrIRIQ=",-7632857774260771093,-1320413807598089667,1511648536046618911,-8568202637307996795>()) {
                                          case -1965849926:
                                             var2 = var7;
                                             var1 = var3;
                                             switch ((int)com.yiyiaddon.m.b.a<"sysouk9va4kiu","Z3SquLC2wbeyqiAcPiDhbuJq7v6zjKSBpLjxZziJXGc=",-6809467464506508755,8104365135209204555,682967281719512795,1329148549577048158>()) {
                                                case 933228291:
                                                   break label44;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }
                                    break;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3l3uegqzs2bn0","PbZyXQJf8tmN8Y5jyXptmAGAng2cuFkMKZ4VjLBT8OQ=",6427519273670801067,3818375994084427862,4932745770196367420,-7783441219956581326>()) {
                           case -1908318198:
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
   }

   private ItemStack l() {
      int var1 = this.bz();
      if (var1 == -2) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ntvq3ywygpg1","Y1jjmRIwIf0BK6t9lJvPa7uybRHe9P1tMBpgbuo3WwA=",-5238578784766957982,5522164851409980928,1355372555937222164,3463854663717620654>()) {
            case 1161423070:
               return this.G.player.getOffhandItem();
            default:
               throw null;
         }
      } else if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wing6s9gkdcw","geiBYZ1NZjrw0zEbHHYuJIvKfiN3gY2o0lvefIlvU9s=",4879758596115888851,8586204953993813772,7746748176028397196,-242252300772144768>()) {
            case 1555643766:
               return this.G.player.getInventory().getItem(var1);
            default:
               throw null;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   private int bA() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3cxc85sq704ur","iy7LVTHvD4My27kRGHqPvofEZwVdGAVN7l2ae+52YRU=",3234959286577230975,2547337021945773568,-8445915501492156527,1570887757061599356>()) {
            case -512604002:
               return 0;
            default:
               throw null;
         }
      } else {
         List var1 = this.b.am();
         int var2 = 0;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3s2rz8ymjvuns","Gxg55xz6LWdgME4P3PF9Q2osHzgkqC+jwNr5sDqHFb0=",8417619080025879560,-8157841745382865500,-2239772509807867446,6035626985838721827>()) {
            case 924482961:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2r1wxknyerx7p","eVvo2DLcfXIuuxZM4nHl9L+f3evYVpsnbTcTmKVi3ok=",-5426759180986813198,-5647936451705227186,8069102802837132568,-4599018257642048541>()) {
                     case -86949314:
                        ItemStack var4 = this.G.player.getInventory().getItem(var3);
                        if (var4.isEmpty()) {
                           label37:
                           switch ((int)com.yiyiaddon.m.b.a<"srfmqhfwuf5uu","FQ6rcS4HHlQu19sple1gojshJnqde2vkbQmfQWUHl6I=",-1570891093450451014,7784760729838178737,2456877261261397871,-6622942205369654524>()) {
                              case -576978351:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3vpxak3gdlg97","AjtZ0vBrOnnaoQB09Idb/E8AIHEAo2F1taeIPStKqwM=",3449528403714778527,9168751935059044777,-6356047166794345371,-8431659224678132536>()) {
                                    case 1536660311:
                                       break label37;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (var1.contains(BuiltInRegistries.ITEM.getKey(var4.getItem()).toString())) {
                           label33:
                           switch ((int)com.yiyiaddon.m.b.a<"s27eqrzr8bt3ma","1Si/kk5Y44RevUcneJ7r+YXFZFkkg2U3VZ4tZliSTF8=",-5085139468353830780,9028244294362224460,-5542233837670967305,1698961193302753763>()) {
                              case 751771539:
                                 var2 += var4.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"s2x1b1249dja99","BaSu6DL6bzlv1YzCJcUNyluGWIe66ca4q6Zv3y65KbE=",-3002640913613801749,-6486021726021313700,-5932377574264728083,-938049961667801062>()) {
                                    case 1759550931:
                                       break label33;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1atlc2jik6yss","mveZjN29drnVL6AKkKcLu+lyI6/oB1zU+jDsdkx+mg4=",-2444746056164523654,-7220958674105496528,8980764046875046302,1597875485453763534>()) {
                           case -613318493:
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
   }

   private boolean ch() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3djjbc07reu6q","kTq7glbvO8lZ2Fm/bxVJTCuIy8VaGsKWa6dZ/mXkyk4=",-7736490781904676665,-2421783095126053734,4514158722471829169,-7706049913595711273>()) {
            case 217687752:
               return false;
            default:
               throw null;
         }
      } else {
         List var1 = this.b.am();
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3aluxwnyvri3v","fGWyYIVwS941klpgbmD80jUveP9ywxwMNr0LoxDZxSU=",-4546043709547227335,-7556652562363301424,3082138204312435665,9016816370042303636>()) {
            case -1603915089:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s21aak2jb4jw4a","QoFXpnmL1oik3V082tRsfL909Zv+AwC/AePMmIMcQ18=",1359309764116832908,-1114679727201614118,-6574828717435433352,-7344130350622258375>()) {
                     case 1929575817:
                        ItemStack var3 = this.G.player.getInventory().getItem(var2);
                        if (!var3.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s30tfw5s51lgj0","xv/H8YsORQmMbHMBkuiCSPiZKzymhgU+aO6lkacgZoA=",-4150499488662469652,-4092865361134770899,1962887761903051804,1957863972134806544>()) {
                              case 454347837:
                                 if (var1.contains(BuiltInRegistries.ITEM.getKey(var3.getItem()).toString())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1gf9qcmjpwcdr","NkQajMkiS/+lxU2ZlFG9s9qo2Y5cqRGJTHZ0qCXywnk=",3877673321936876208,-4246608598992699009,-8972023923772349794,5775538700501998022>()) {
                                       case -24366376:
                                          if (var3.has(DataComponents.FOOD)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3bbo556clc9dv","F2L7UmFRvozNzDHxomaBMJ2yTjZ0TwuK6JxBGLsE4gc=",-8126822742882646369,3041457881523445828,6556781203519823992,8548324712398398710>()) {
                                                case 393011022:
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
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2rjy822bfqqhq","tIl1dRosXcrK84tG9lU2b/fe3nThxV1rftfg75cmTJA=",8888732550431803341,-4354131646143048869,-7533077833338002719,2792542896889665422>()) {
                           case -239727891:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   private int bB() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28dpu3mtd9ke8","5aMewFc/XMatjdFai7SnOIjp8PEtIOXziADMDnUf4ps=",4963065739551262831,-3532306354291109194,-24605191286898350,3210230674137416908>()) {
            case -1078735197:
               return 0;
            default:
               throw null;
         }
      } else {
         Set var10000;
         if (this.b.bI()) {
            label59:
            switch ((int)com.yiyiaddon.m.b.a<"s329z13fockchm","bwBu5zugxsTaY8btSQIFadZ9dMUJICEIuLLO/3e7Z/0=",8869569022820398359,-3522053157918814974,-6635011163693599844,-9057918874279968994>()) {
               case -880048415:
                  var10000 = this.b.r();
                  switch ((int)com.yiyiaddon.m.b.a<"sj6qbn1beh90s","bdO7sypZDYvPi0dvuB6K3X5djTXS2RF10/YVGtFpGsA=",1909735785959633422,3258369002187035333,-1444602151908594877,1403592987951040386>()) {
                     case -1961870078:
                        break label59;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = Set.of(this.b.bK());
            switch ((int)com.yiyiaddon.m.b.a<"s3mwzoluw2pagx","ad666sBcQvwutdn4FGweDzHVQT7wb4FkH6MUBlosK40=",-2680276738286932833,-4458525643940507001,-6477667760987397048,2523379033741598091>()) {
               case 535297389:
                  break;
               default:
                  throw null;
            }
         }

         Set var1 = var10000;
         int var2 = 0;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s15e7vw4wautgw","fjR9RJp+8gfxSw54eU9Z3gvrpFKe053cdgzZ4yQb3NY=",113271003737867195,-3339633238625047070,-242013509919173790,4150056599202547205>()) {
            case 990521380:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"smnihm54zwwow","MGt/8nm7ytHDQHoIWaCPgNf1UfMSQyJa5OKTuxLp5lE=",5550411855815155415,-6150463683096879917,5207488416363991820,-5714712036721601517>()) {
                     case 1613474073:
                        ItemStack var4 = this.G.player.getInventory().getItem(var3);
                        if (var4.isEmpty()) {
                           label44:
                           switch ((int)com.yiyiaddon.m.b.a<"srmt2w7cdg4ve","eeEu73CVimqCd42J9KRXG/Kl8lZ7/VanZSkjOFlN1sc=",-7439250894007892811,-8852701796679834204,374808389284746655,6913465563243198086>()) {
                              case -1864778431:
                                 switch ((int)com.yiyiaddon.m.b.a<"saq42mgb4z9t9","G92s5cPwI7MOLUsOxALWQukpdTFRfrfbIzfOprd8m8c=",-3362693321107340573,6845174592368553552,-3055978026169144181,-6075215563687803634>()) {
                                    case -1793762315:
                                       break label44;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           String var5 = BuiltInRegistries.ITEM.getKey(var4.getItem()).toString();
                           if (var1.contains(var5)) {
                              label40:
                              switch ((int)com.yiyiaddon.m.b.a<"s1c05s3c6uk5f0","bg2+kX5qYaVvlL5QE3uDImDdhWTnBFtnQTWqODqUDWs=",7602043397908456265,4980421420835839748,-3897580770591239883,6527044685531435881>()) {
                                 case -1966671783:
                                    var2 += var4.getCount();
                                    switch ((int)com.yiyiaddon.m.b.a<"sefn3b8zpcgcp","zwIMzOM81DWxi4je9/fuF7VqzM2oCMiQkTDydZZrCi0=",1886095876007570054,-4899364583661780798,-431132925071106260,6578730650983544952>()) {
                                       case 2135245388:
                                          break label40;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"spgn8gclczuh1","Yy2Ok77AUvAwBr+hxiByi6b5wPmLTZY1Z9zAqfMIud4=",-5983233878883237805,649582307440263199,-5700047008867956423,8135891053769816557>()) {
                           case -39362851:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var2 / 64;
            default:
               throw null;
         }
      }
   }

   private ItemStack m() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3gyr2un38ezxl","GjLXR3qZt9FC5aepSDD426I1Sdwy4TKe1u6Ik39oRdg=",-1585802126752423011,4735118041033190388,-5205106377701278972,9204328153159035929>()) {
         case 568962753:
            while (var1 < 9) {
               switch ((int)com.yiyiaddon.m.b.a<"s2z2qcj2fz6gr3","oGh0iMtGOfAL4V59gExysqyC6cTtxUTuCK6ViCkqwIA=",-7017821383964165862,3647119435114690760,3533806676996505753,9068427547060711>()) {
                  case 1396877204:
                     ItemStack var2 = this.G.player.getInventory().getItem(var1);
                     if (var2.isEmpty()) {
                        label29:
                        switch ((int)com.yiyiaddon.m.b.a<"s228uxjdquwb0q","wxVnGvlzviJgxBGFABKLzSCjW7HqltVbpGi1rEVs6l4=",-5059264883874495747,2247055938093799582,2899044998754725318,6885002058520269556>()) {
                           case -163601369:
                              switch ((int)com.yiyiaddon.m.b.a<"s2qor1571vakpm","t/ATG/5YOaF7DsWwKo2eBmEoREv2hyC96j7wBXYCSPY=",3901748555807002781,-530842332688940357,-1968873542211901103,7683663556623223299>()) {
                                 case 908756537:
                                    break label29;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        String var3 = BuiltInRegistries.ITEM.getKey(var2.getItem()).toString();
                        if (var3.endsWith(
                           (String)com.yiyiaddon.m.b.a<"s2px7kuvaazps","o3KKv1C+OFLqI557/tMsDZ62BqAxPb+RsfyqolE8hsU/b9EeH+rxIw==",-2189487092590524475,8812490183552326748,-1374139359943931453,-7610460197100915876>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2shi5m6dxog1y","s131jsSfi5N3v/P/LLHoiDoHQbVrwUUHyGBsgG/oPws=",-2882553329513859725,2816681296805234778,5943922270290729906,-7769788336726343219>()) {
                              case 1239243116:
                                 return var2.copy();
                              default:
                                 throw null;
                           }
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1s55fj0s6hmq4","f82WADfYGHMrBoqyD4bOZKhH13OYjo1BSH5vv8WCe1U=",6537836842886237883,-5850641915804658888,-5802837606563093298,5712065340169211791>()) {
                        case -1556321748:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return ItemStack.EMPTY;
         default:
            throw null;
      }
   }

   private int bC() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2daresl8ukohq","4K1oLLmHb7z5i4sA6X/X1tdB2aX8z+SkUFtBhVRtZOE=",6563805580888609754,8703310731239546371,-5517280502197972864,-1178661021948917572>()) {
         case -2041268582:
            while (var1 < 9) {
               switch ((int)com.yiyiaddon.m.b.a<"s3i5ajnc1ky3gk","XIhdEQOrRjO6ZI6iWpan72ljyWz/z/7rMXTAn+M5XKk=",485770975798800774,1281361479703413051,-7663442033243341816,-5232690514174619048>()) {
                  case 993952564:
                     ItemStack var2 = this.G.player.getInventory().getItem(var1);
                     if (!var2.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1kg7glhn59ft7","9a9iK3x8RRCPTMpjWzkBNHmH/TifM/EYdNjbjDLuFQE=",4486223009438952328,-3970786697064174888,7374525241924127753,1025226547924917927>()) {
                           case 1861407239:
                              if (BuiltInRegistries.ITEM
                                 .getKey(var2.getItem())
                                 .getPath()
                                 .endsWith(
                                    (String)com.yiyiaddon.m.b.a<"s2px7kuvaazps","o3KKv1C+OFLqI557/tMsDZ62BqAxPb+RsfyqolE8hsU/b9EeH+rxIw==",-2189487092590524475,8812490183552326748,-1374139359943931453,-7610460197100915876>()
                                 )) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s12qq45gtxiiz4","VflXedeF9ZgQi5QcMhyms1sbCYs5fO0w8F0Lm9xRSb8=",-5003404422924351959,-1093996449665296686,2445167430926663564,-8263664338472656000>()) {
                                    case -296316372:
                                       return var1;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"srn5l9br2jjzl","qbdkPcbLr/cZ7WNK4C6HIs1FwZ4mDX1+SkhqBdj7Q3U=",-6324336818200321404,8265188213993516011,-6488232832849899974,8316469787432479291>()) {
                        case 1421153871:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return -1;
         default:
            throw null;
      }
   }

   private void a(a var1, a var2) {
      if (var1 == var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jle95z5ybam7","zct2Y2q+A7RLgueD1Q5K+ov8VcaDE+/HrZBKBER3gHk=",-7739116335991598890,-7233411249678677600,8821366721323442646,-797805791490527452>()) {
            case -1180824573:
               return;
            default:
               throw null;
         }
      } else {
         String var10000;
         int var3 = this.bA();
         int var4 = this.b.bn();
         String var5 = this.b.bI();
         String var6 = String.valueOf(this.b.a().a);
         label63:
         switch (var2) {
            case IDLE:
               var10000 = (String)com.yiyiaddon.m.b.a<"s5nro4ejf52fb","uWdUWe79uGYSCDfGORWj4EnWQZdaNzOom7JqZGX4tmja1ojYx/A=",-2423790764078614213,-3611703103083284019,-6227005815273381158,2814767992367029638>();
               switch ((int)com.yiyiaddon.m.b.a<"s2qe42p23vloql","bgeSMkAUESyGSYCbrWoUiAC7PWGc/Y1Yo8hpYw/G/Fw=",-6822406824615380774,4260229160982642406,4407729134600239923,1257365145874402602>()) {
                  case 1225300775:
                     break label63;
                  default:
                     throw null;
               }
            case GO_WILD:
               var10000 = (String)com.yiyiaddon.m.b.a<"s1z9d0e2p9b5r5","6B9gQaduKlLYddvWO8MEQMRixzltT/nCSy8KlORA2wuwouAol4z1ccUsodQ=",3621138308514836400,6538764884427591463,7634629738225095424,-5631926746107752832>();
               switch ((int)com.yiyiaddon.m.b.a<"s11qsg4xblvf5a","pXGwjQ39GU0HFwEH7WjaR2vYa9T87W6rx4gC0uod6ZA=",-4529188535780304137,3985264469256021210,-4790437785152216987,2111007849141577691>()) {
                  case 1349671519:
                     break label63;
                  default:
                     throw null;
               }
            case MINING:
               var10000 = String.format(
                  (String)com.yiyiaddon.m.b.a<"s2zo8pon14sr6i","L6hBtm03ApE3u/9jcsUeZiXtHvTDD3HnkcvISfpnCRUQI21EytuyiSMSNHqQapiRBe9iPH9w402TAkPLV5Y4elHRrVU8vNU7YvUOe3iBgpc=",264954632098889782,4192192769196378173,4974329841514311251,-7557556710382649903>(),
                  var6,
                  var5
               );
               switch ((int)com.yiyiaddon.m.b.a<"s15klsk1q4vayi","UDn8rJyRbHNWwgyh6dwb1NJS426a6Z+7SoV5P/F07O4=",-6473183668355469174,6870547097592252912,4863156163618234075,1538626282064128046>()) {
                  case -1396855436:
                     break label63;
                  default:
                     throw null;
               }
            case UNLOADING:
               var10000 = String.format(
                  (String)com.yiyiaddon.m.b.a<"s1bo6svke9sqwq","VsLuI2na6A8nX1Wk5RZocUarjtkg0IonySF5q7WIrk1zeu7vwDjwn5b/aPCe20Mgrnw/5pxAY/o0w0VPHWnYbXJJokR27PorX3bBxw==",-8037507411436930967,-3575525186049600167,781978481476703769,2848301050704940530>(),
                  var6,
                  var5
               );
               switch ((int)com.yiyiaddon.m.b.a<"s7pw376r86pom","nbLG7ukpS2j9xB/jsEKcHkyKc4NxRjJw/Jhz+XBHY7U=",-6465322821951694064,-8443343493862577786,4100789567277539013,5888126598841870475>()) {
                  case -478654146:
                     break label63;
                  default:
                     throw null;
               }
            case SUPPLY:
               var10000 = String.format(
                  (String)com.yiyiaddon.m.b.a<"shym8fct8u92z","xnaOzQhS8Pjek4t3EkFOUDSEKPV2l8nyDBFJw1rD+id+YAeJFSbUOrsRWzG5mq7mRlJ6QAtIDnqiRKS874y/ZAmrjMTJpQTz0WsTr0OW",5811034889768782554,-7704972896538048763,-777785528947671736,-92955201393451262>(),
                  var3,
                  var4
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1dyoxbj654giz","mOt6JhBFMgO14nox3kq6ms+JID+gkIfm05rOy47eYoU=",6445793494621259108,2442328863412230954,-6377450827875437200,-5896811724190272622>()) {
                  case -624271659:
                     break label63;
                  default:
                     throw null;
               }
            case EATING:
               var10000 = (String)com.yiyiaddon.m.b.a<"s1nqmku03m1h5s","mM897S9VWdfF+gqdKaTsDEv1iGqKeR+raoXEGmZBzstwH8FnCTLqFunF",-5441113145690425957,-461398281631566774,-5043124480650074078,610692252669947824>();
               switch ((int)com.yiyiaddon.m.b.a<"s1mo8q6rdyeqhh","SyP5KRdNnC7fPR4pdPXjdEPRInjMyfRCBGD3O1UwtGI=",-7646282084661671592,-1979018492922720839,3722661259296426836,-3200539645641364813>()) {
                  case -755191349:
                     break label63;
                  default:
                     throw null;
               }
            case REPAIR:
               var10000 = this.b(this.l()) + "";
               switch ((int)com.yiyiaddon.m.b.a<"s2v830vy5qt96u","Jbvk7BLhHLn/d/Fqu86+2da/wBCrhRxmhs5womulzuI=",-6690719003797178685,-516311348516546013,-5698006653993826063,-1615534338963848137>()) {
                  case -1238379572:
                     break label63;
                  default:
                     throw null;
               }
            case DEATH_HANDLING:
               var10000 = (String)com.yiyiaddon.m.b.a<"sc3e371snh98m","pfCqhZCI7q3sOjjdMrNePka0kXPnn7nNCIY5HxX3R6mvZcpPWs9MtSi48H2LmGpoMZrG3Bow7xb0cjNHVrT7A6wu+ZQfDA==",8447205975393946956,5459890099452735221,5583374747889770912,-6019102048685976986>();
               switch ((int)com.yiyiaddon.m.b.a<"s8483kew3z1y","8vJ1k4HzoUAVQ7jrt22odMggVNi9Rv1rqZj3XbIXqjA=",-8651239216369325838,-7006207274484970945,-3289171616438813751,-3679767889894070025>()) {
                  case 22536108:
                     break label63;
                  default:
                     throw null;
               }
            case RESPAWN_WAIT:
               var10000 = (String)com.yiyiaddon.m.b.a<"s2wy88rxscn54v","zIq8PZinp0X7siqPybTHenXfVSK+d7rXoQgrjztRe6C5AdLPSfZV/0uJ0w/5j27egBAJbIXkAcirse8f",6226702937204711020,8164806499832483913,-7863259626387798832,1137699871107527134>();
               switch ((int)com.yiyiaddon.m.b.a<"s2y32f1wpi8m2i","9k6+7+ZZkEhw+qZrAQMzkvFgq1hThbJ88jboEGi2454=",-6089011384126734143,-4170158799148122110,6662234553927541288,6389607904399961149>()) {
                  case 1252954962:
                     break label63;
                  default:
                     throw null;
               }
            case COMBAT:
               var10000 = (String)com.yiyiaddon.m.b.a<"s3u5k18p5lb0py","CVGbRYiWyzWnrXzX/19npUP3zw6vODd81kMNHA==",962476188745281713,6569589023899289066,6768228396665697891,1557210378977412126>();
               switch ((int)com.yiyiaddon.m.b.a<"ssm1h2ffmp1l","WfLLP6t0JI/Yry5k9hqMdHH0PgaALjaeKHISG1z2vIw=",-8370001874918067305,7069786948796127856,2916604397470133950,-1497804537896890943>()) {
                  case 1619411578:
                     break label63;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }

         String var7 = var10000;
         if (!var7.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"ss2l8fpl87cjf","G1javZ/nzhzTy/RqNN1bjUyp3xiSVRZNnLGEn63Z0SA=",-1440703174069662590,8803090363975312474,719795082146105030,8113372546241594346>()) {
               case 1004064817:
                  this.b.K(var7);
                  switch ((int)com.yiyiaddon.m.b.a<"s1fl38g3bo2oy5","BXk9I7neYzB9WhYxJmHBDhF3GAa+EtQSw7y57j3sUBw=",-5063289256829478135,5982326660772768795,-7531448925109101159,-4543874536267169468>()) {
                     case -1932997922:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private void b(float var1, float var2) {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3omnhka11fqv","IrRHHiBO99Knvxv0716kAU1wwx+ad6E+RuwEy7MGWKY=",2826960520093700043,-7405375383410993290,3480181721427246727,8010551704643949008>()) {
            case -15538554:
               return;
            default:
               throw null;
         }
      } else {
         float var3 = this.G.player.getYRot();
         float var4 = this.G.player.getXRot();
         float var5 = var1 - var3;
         float var6 = var2 - var4;
         switch ((int)com.yiyiaddon.m.b.a<"s2iygau50lct8t","dYIcHa91I9OEmtlZf6KJyT3DXwg7n6p21JG/PJdHgg4=",298746970196133814,-7659059579791349261,2042497914445531012,2079986176086563591>()) {
            case -79672939:
               while (var5 > 180.0F) {
                  switch ((int)com.yiyiaddon.m.b.a<"sxag6plb62yg7","tZSt3A1/DGT6GUl6OFDLFirPS1FKMifqzxbdKmSMJUw=",-2253089018416301110,8734365089779269781,-5496951947266480758,7097568713007449687>()) {
                     case 793262183:
                        var5 -= 360.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s3hbplynq4uflq","wfHReziQCm/CP4Y1sFvnRbSiTnFsiQClm2pD4wAJ9+E=",-4024105662444054144,-7628203650298379298,-1703321122144096928,8967069429904878135>()) {
                           case -874061296:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               while (var5 < -180.0F) {
                  switch ((int)com.yiyiaddon.m.b.a<"sw9zyjlmtphz3","3hnmtDuMiIybJIwEkdg7FC5GrPvMtT5qrA008v3NEHE=",-5275982040635944159,8995370478964403929,-507477291618937575,3682381156373573482>()) {
                     case -603069589:
                        var5 += 360.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2bi9h2lke8sxa","ybiIF+WvNjWXeHcUvI9JW8f7oi7whGjrDBdOIupDpF4=",2882181655766048924,8568016615999255284,-671460481120730545,3809638485324294284>()) {
                           case -2037029886:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               float var7 = var3 + var5 * 0.3F;
               float var8 = var4 + var6 * 0.3F;
               this.G.player.setYRot(var7);
               this.G.player.setXRot(var8);
               return;
            default:
               throw null;
         }
      }
   }

   private void o(BlockPos var1) {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qb30xc54jrcg","6fiyfeG0cr8X4Qza+iA266DeXzZMaUekGHgLOaTxikE=",2797165005873309804,-6563748812800492598,9022928541495549641,662764875066355130>()) {
            case -1734688867:
               return;
            default:
               throw null;
         }
      } else {
         Vec3 var2 = this.G.player.getEyePosition();
         Vec3 var3 = new Vec3(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);
         double var4 = var3.x - var2.x;
         double var6 = var3.y - var2.y;
         double var8 = var3.z - var2.z;
         double var10 = Math.sqrt(var4 * var4 + var8 * var8);
         this.G.player.setYRot((float)Math.toDegrees(Math.atan2(-var4, var8)));
         this.G.player.setXRot((float)Math.toDegrees(-Math.atan2(var6, var10)));
      }
   }

   private void eZ() {
      this.a.aR();
      this.cA = true;
   }

   private void fa() {
      if (this.cA) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2flf0h4n27sh8","HVbVR0LDVN5pEH+QRbIMxFnt0HImR98K/mLVrnLf4m0=",898687442443657523,8749231543865382901,-1163014701563792832,-6278207604965007531>()) {
            case 1754611026:
               this.a.ag();
               switch ((int)com.yiyiaddon.m.b.a<"sqhnb00pihwqy","g5/0h/AkjTUfYep8+6sec8k3KD/Zo0aGo2BsnGDSH3w=",8261130839064339195,870972133363064985,-7238087937992577784,6623876672007733428>()) {
                  case 1221909340:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.cA = false;
   }

   private void eJ() {
      if (this.G.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uk3okr882i5z","i+KzlO+Kd53pFy5PQiHee8G8rEUtckFy8sUQNifTCwU=",-2659668683516142555,-2694006130819127847,6472623855544544436,-1549520530749297524>()) {
            case 41572608:
               return;
            default:
               throw null;
         }
      } else {
         if (!this.G.player.getOffhandItem().isEmpty()) {
            label36:
            switch ((int)com.yiyiaddon.m.b.a<"s2wj46k7laol8q","95oAMQpFH0KKAY4dgTtlHC6lI067TEiWGqM08mHQAPg=",3587814347047801806,6862149105124138168,5585007598502542033,-7225009045544932923>()) {
               case 1298585949:
                  if (this.ht >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"ssqwnnlnffkre","2KHmS6D3oQhy5Ya8Lw5QYvg5r2UQo5wjiz1ptQ22FgE=",-4808307405002894726,1018024769937337456,6188972558279955558,481278974769067453>()) {
                        case -1636309997:
                           this.y(this.ht);
                           switch ((int)com.yiyiaddon.m.b.a<"s1vbumpg6gqc7e","VKos4cFAiJNLvf4nBe0qstyGvpmkndQ2JmhPCEjpuxg=",-2175689810878194011,5750196759206898627,-7654633804898661156,7393139816369745729>()) {
                              case 743582148:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else if (this.ht == -2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2gtmwtyfnnzvc","VcnhYKtBg9kILtX49xyi4PIcWZ0qCrJzlH7DnefDkx0=",3695003970746749765,5675536056342947586,-1043339193128256501,-4903651046240950066>()) {
                        case 1763562836:
                           switch ((int)com.yiyiaddon.m.b.a<"s1qgz23f0qr9ge","odXtd70IfuJcLyJ1R7z0iLDKmBjHOPtz5Bs2ITadR58=",-7888748844789985360,-563059478677702125,-3396907492882432203,2136452712162875006>()) {
                              case -1292076145:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.y(0);
                     switch ((int)com.yiyiaddon.m.b.a<"s7gh59ky20qeu","wpQOP1R4C0+XziAWCnbgfhlRhrk5BuHoCOiC/DsU1PE=",-2021925934222538047,6696489521547922263,1731593294657803167,-4097709696234527667>()) {
                        case 971474396:
                           break label36;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }

         this.cw = false;
         this.i = ItemStack.EMPTY;
         this.j = ItemStack.EMPTY;
         this.ht = -1;
         this.hu = -1;
      }
   }

   private void e(int var1) {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5kj7irtg1mci","flBzmXsboYnmPwjaGZZmi2MGjoVnc1qaiGvKkj2v+tM=",-7263981291119544035,-5180506239173693885,-6393670160265912208,-8502372183289101596>()) {
            case -2056633468:
               if (var1 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s236k9p6znwszu","Ob/MIHtGR637qd0j7Ul91WmV2bJXDrnMG6S1VdA8Y0g=",4443368791345005219,4461216777392523810,6709159185778994109,-1745614791024684524>()) {
                     case 334895728:
                        if (var1 <= 8) {
                           this.G.player.getInventory().setSelectedSlot(var1);
                           if (this.G.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sh6c7b0fqhq4r","80ynyIQ6XFaVc/iRfpmhOr5CLtlhCwBSFO2uCMF5jTk=",-2913245069483929414,-1124984593058639838,-6067344059261037668,3569079692090037898>()) {
                                 case 119237438:
                                    this.G.getConnection().send(new ServerboundSetCarriedItemPacket(var1));
                                    switch ((int)com.yiyiaddon.m.b.a<"s3v3tm8datvhnq","c8B8R8wVuaRoqoJJat05cHfFGjLEYTPyw9rQDpdfqFk=",2899419342407226141,6312107579106803861,-5365936177128567477,6361053478231254535>()) {
                                       case -948312100:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3huymneejbr77","QxgvSDjRpg3s0NRI0rawNrVfC6HAssOTusMTuJbaGhk=",-639544880382783310,2065137765201426920,-7654047086556066676,-6912487349155468691>()) {
                           case -922249256:
                              return;
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
   }

   private void y(int var1) {
      if (this.G.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2crwts0csgcl2","4Yyz2XkCAkoNqJ94guyn8J6Q+0+WLfssrjQRSRuGDZ4=",8533146136489083035,-1153370496838951174,7805332092180047743,4494401801573066922>()) {
            case 778193484:
               if (this.G.gameMode != null) {
                  if (var1 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"swpxsucxd2r88","1WOkkjm8/RQ6vk2quDzofNS6uyd++RjC51/ZPM9vt7A=",-1774187905687877750,3768799465236208525,3051908922634662565,2558402287165410340>()) {
                        case 468054961:
                           if (var1 < 36) {
                              int var10000;
                              if (var1 < 9) {
                                 label29:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3rsoxmx6praex","4DFzE/dimm5DnoNElh+3y7DO3qElaateMupAzOFhsJ0=",-4833079272660285644,-2668902905717534844,-4077098504830083898,4883086398729542762>()) {
                                    case -584872327:
                                       var10000 = 36 + var1;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3hj2feimp4bpd","IvPxKqB7GEUiZ6Ri2RGM1GzknxXqJijMsF4EoYRWyS4=",2636131244127762962,-1206886045856960539,-1450882866208819793,6580151440580104415>()) {
                                          case -1675716588:
                                             break label29;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var10000 = var1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3vsx60ijow3jg","Z8Jf+iLYrwmIUd4+5Z4Lf7ictPlMVFF0hpz8//tNlYk=",-6504652933629912669,6448844208728155372,3647992414456809103,-86968091953993095>()) {
                                    case -1822559795:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              int var2 = var10000;
                              this.G.gameMode.handleContainerInput(this.G.player.inventoryMenu.containerId, var2, 40, ContainerInput.SWAP, this.G.player);
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"so3ktbagil4hs","c9y96RNrS2xqrey2J7KSkUUwHeeS8mi6an+MGhDoCV0=",-2521995355589322721,-894454396728120691,6440841264411927466,-439248687698245212>()) {
                              case 1620976966:
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
                  switch ((int)com.yiyiaddon.m.b.a<"sxconfph7dupd","IJWf4n1VRvzjkkDklgaJeSQ4gN6PdqwV1RyoaIET6Xs=",6240610612664067361,8587841505150727607,-4795094227041313746,6844161134678173158>()) {
                     case 1869704556:
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
