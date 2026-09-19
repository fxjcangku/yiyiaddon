package com.yiyiaddon.e.g.c;

import com.yiyiaddon.e.g.d.g;
import com.yiyiaddon.e.g.d.k;
import com.yiyiaddon.e.g.d.l;
import com.yiyiaddon.e.g.d.m;
import com.yiyiaddon.e.g.d.n;
import com.yiyiaddon.e.g.d.o;
import com.yiyiaddon.e.g.d.p;
import com.yiyiaddon.e.g.d.q;
import com.yiyiaddon.e.g.e.c;
import com.yiyiaddon.e.g.e.e;
import com.yiyiaddon.e.g.k.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class b {
   private final com.yiyiaddon.e.g.a a;
   private final com.yiyiaddon.e.g.i.b b;
   private final com.yiyiaddon.e.g.f.a b;
   private final Minecraft x = Minecraft.getInstance();
   private static final int dF = 20;
   private static final int dG = 40;
   private static final int dH = 3;
   private static final int dI = 3;
   private static final int dJ = 36;
   private a a = com.yiyiaddon.e.g.c.a.IDLE;
   private boolean L;
   private String bR = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
   private boolean bi = false;
   private int dK = 0;
   private int dL = 0;
   private int dM = 0;
   private String jn = null;
   private int dN = -1;
   private boolean bj;
   private int dO = 0;
   private final List<String> az = new ArrayList<>();
   private m a;
   private k a;
   private o a;
   private Item e;
   private int dP = -1;
   private int dQ = 30;
   private a b = com.yiyiaddon.e.g.c.a.GEAR_IDLE;
   private com.yiyiaddon.e.g.d.b a;
   private int dR = 0;
   private int dS = 0;
   private int dT = 0;
   private int dU = 0;
   private boolean bk = false;
   private int dV = -1;
   private boolean bl = false;
   private float aw = 0.0F;
   private float ax = 0.0F;
   private String jo = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
   private String jp = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
   private int dW = 0;
   private String jq = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
   private int dX = 0;
   private int dY = 0;
   private int dZ = 0;
   private int ea = 0;
   private int eb = 0;
   private int ec = 0;
   private int ed = 0;
   private boolean bm = false;

   public b(com.yiyiaddon.e.g.a var1, com.yiyiaddon.e.g.i.b var2, com.yiyiaddon.e.g.f.a var3) {
      this.a = var1;
      this.b = var2;
      this.b = var3;
   }

   public a a() {
      return this.a;
   }

   public void bB() {
      if (this.x.player != null) {
         label24:
         switch ((int)com.yiyiaddon.m.b.a<"sbl0jb1o1obip","zNi676oOymKDUfFzvrZRdrY+rKstvFcIslHbXg7LuWA=",8050738096577992323,-454858947030589030,1203744659576361641,1393882334450958182>()) {
            case -1828781629:
               if (this.x.level != null) {
                  if (this.a.a().a == com.yiyiaddon.e.g.e.e.GEAR) {
                     switch ((int)com.yiyiaddon.m.b.a<"sijlx0p5ftdhb","L5w1dLCtsliKJdZq18EfQ8TDoKnNsjOO5Hu8MY8iYXE=",-7765541020479126748,-1952621915167080680,8172187700336030308,1773115337805685026>()) {
                        case 184463604:
                           this.bC();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.bD();
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"syt29vndyt5u7","NjTpb1HVS8S0d2CNsXM1kTyavyOwnxnuRwijX2V/ey0=",5069213505427425530,-6809585363918381759,-5756183645493118655,-633501798305821226>()) {
                  case -559202618:
                     break label24;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a
         .K(
            (String)com.yiyiaddon.m.b.a<"s19bl4g1wepfx1","cMBZU78/9JrZcfFT+K/DdniGrX8UOT6nElQsYtKYF3ie+FTmf/B0vtuR1FSaFZ/RFBbLJ6ZFPID+StPS",4924363238742147166,-5067684254189649806,7144085846505009084,3317814529336813533>()
         );
      this.bF();
   }

   private void bC() {
      if (this.a.a().aR() != null) {
         label52:
         switch ((int)com.yiyiaddon.m.b.a<"s3gaj36j1h99r6","8ImUW1S7MZ12lxgOUUOuVLnOa5QOv05u6EgWm41EeNM=",-1040642366998857150,558344692934845120,8510804507655326033,1064030220044346246>()) {
            case -522643663:
               if (this.a.a().aS() != null) {
                  if (!this.a.a().aS()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ju86srorkz1l","eSPUIsb450oRJ1lyw8c1rWIGQiuA1wpeeTcR1ENXGXU=",4527849018996020290,-6739080723828453573,-1587652572086135443,120290566139985175>()) {
                        case 821267492:
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s18l64dqsrf7ef","1pvsluE+NL+P42CBRr9XRE+VZnCNqCUcwwAc7NRm8HUsGz9/TZR/IlBGdexOPVwbiiDRTCM+EgfRpIGkXXNvXOir7WFz9uOFMjPmdHWK7D6KNH+ZWzCzXtdJLHrTJQOiCuY23GXa/joK4teyPDRibaeVKvZoI4Yj",-5052884617246643350,3933550687971309058,391568746862466115,-5021367313331806453>()
                              );
                           this.bF();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.a = this.a.a();
                  if (this.a != null) {
                     label43:
                     switch ((int)com.yiyiaddon.m.b.a<"s2nq5eg7lzwwmf","rVY2XaR97/EKPgVDGen1pE1hEQiUNa1Jhu8RpCSSSrk=",-2780212437305905277,-9206025058750449046,1998566517534729392,7148092513522830664>()) {
                        case 1864947115:
                           if (!this.a.a()) {
                              List var1 = d.a(this.a);
                              if (!var1.isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kgb7mkg94hnq","pPkkFiwOP3BYTK1NMDNqgvr0Siw8xPfW1QRLNUfnAFM=",-640413041483517105,7733433938837754108,-6641718152453304162,1176129901056477180>()) {
                                    case 230404995:
                                       this.a.b((String)var1.get(0) + "");
                                       this.bF();
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              this.e = this.c(this.a.aF());
                              if (this.e == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sdsgwyaeqmea9","kDeZhhRP1JqqvwM8zouXoaApG/5oUJ9PknR/gTnYgCo=",-2409662597227899765,5498622661055166580,-8882288854785708481,6767193545991827149>()) {
                                    case -436047818:
                                       this.a.b(this.a.aJ() + this.a.aF());
                                       this.bF();
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              this.a = null;
                              this.a = null;
                              this.dP = -1;
                              this.a = null;
                              this.dR = 0;
                              this.dX = this.dY = this.dZ = 0;
                              this.ea = this.eb = this.ec = this.ed = 0;
                              this.a = com.yiyiaddon.e.g.c.a.GEAR_IDLE;
                              this.bR = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
                              this.dL = 0;
                              this.dM = 0;
                              this.bm = false;
                              this.bG();
                              this.L = true;
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1vo4hyiyqmklz","uWQyGGSLdnCxHtEeT7jbHgXC+QR17OdHqYAsJpvR5s4=",3517133387060651877,7678606135006472422,5299191347681094095,-4596421375350875055>()) {
                              case 1911522403:
                                 break label43;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s399x4oh5mzz0e","9ep1jH0vY99pEBBj/sHVzXWTY+4WHHiYGcqoXz96gpvJ8FTjbzWaqs3pYmbJr6i6lx3mbzaMvNBB4AlGPatNOEoxi5phj+c46z4=",-6872847557249812017,-4070756940177925636,5204025258693808145,-916741736908558681>()
                     );
                  this.bF();
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2y8f6pd7v59ek","0sGWc+BLKnAEvk+He7Xw3wOi0XYHZBDcTG8cDO1B3YQ=",253065799703734798,-7859483869463408640,-8099619491494756136,1417579196786195978>()) {
                  case 1306917193:
                     break label52;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a
         .b(
            (String)com.yiyiaddon.m.b.a<"s2z552llhjnhxf","hCv2k59uxMo5cFlGkca6OayBZDJsu/sk2BG7849gX6/cYBzK438AudsRrZ1jC0vBHMh2Ryo1LjZh0Ks4+I+VMmbJH2EQVsIuDuqoIq+sgKYs6jv+M+/uYAjvHF9WEwhL",-3659478031575664522,-9057845214763702572,958061147620668214,5929277166518382782>()
         );
      this.bF();
   }

   private void bD() {
      if (this.a.a().aR() != null) {
         label46:
         switch ((int)com.yiyiaddon.m.b.a<"s25xqbirvfsm18","brHRjq7GonEKpycss4dC2bvx6u/mho3dtWl9xHzrjps=",-2852411305507858577,8471326254882198945,-5994064807989089982,-5390362039581210116>()) {
            case 1178591944:
               if (this.a.a().aS() != null) {
                  if (this.a.a() == c.DRAIN) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2194z76ewolo6","2RCuf+NX18mvofzzRgAY8yxwib398iRvY+J9qwRSlsQ=",-529687040059672669,-866412175860443602,6286072130286505211,-2118291484097615142>()) {
                        case -1556669546:
                           if (this.a(com.yiyiaddon.e.g.e.b.AFK) != null) {
                              label37:
                              switch ((int)com.yiyiaddon.m.b.a<"siymwafwfqdx5","1Bl5mgVc4gt+QOJ9etxQ8P4ExlT0vk20jgLwgmlpwkU=",-3172971195398951269,1588471135980020227,5491284728233107888,-5063397850373032697>()) {
                                 case 1645631859:
                                    this.a
                                       .K(
                                          (String)com.yiyiaddon.m.b.a<"s13rd72b3o08yn","KjYGD3m3/52YTPtroE56EtT92PnkEDdH9swENARSzspfAtmApwfT0Zv2qC93l4TDOrBj3KajplIoH3mW4Emb2muljI5m7pXqVgLLFb+M",2763522320569453048,2442005053672512490,8620591668642025103,-6773929200749926056>()
                                       );
                                    switch ((int)com.yiyiaddon.m.b.a<"s1xfhhi5f89zyq","8tLXnOaCO7ne685gJVmVUGIrZZuOAjjzV4jiZ7SbYXs=",-1768456933654199929,-7377876069489733209,-4768736249522178176,3298249351548607978>()) {
                                       case 816669449:
                                          break label37;
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

                  if (!this.a.a().aS()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1bsi7p6wbjslh","E76k8S9s8yKyCtZUwlqxQlKERUPDri9x2mxITlNdiAc=",-7759536474200402101,5842794333782282395,-6143467154722851416,2232670948033810465>()) {
                        case -1524475582:
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s18l64dqsrf7ef","1pvsluE+NL+P42CBRr9XRE+VZnCNqCUcwwAc7NRm8HUsGz9/TZR/IlBGdexOPVwbiiDRTCM+EgfRpIGkXXNvXOir7WFz9uOFMjPmdHWK7D6KNH+ZWzCzXtdJLHrTJQOiCuY23GXa/joK4teyPDRibaeVKvZoI4Yj",-5052884617246643350,3933550687971309058,391568746862466115,-5021367313331806453>()
                              );
                           this.bF();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.cq();
                  if (this.az.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ofu6adtke482","lQqZ3VO/ba3580MztgQN0t4P6LfPoHCnxkQUPU8EWX8=",3350439624892912217,-1498249907057676931,9207388838438059948,-7539658508204163693>()) {
                        case -1641888114:
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s1yge6hcjmi2pw","yC4hBqrBY/OuM8s8xgFAnpvlvnjVAFGiQ/QM9DoHxVRDdZxvto7IIz/xIEuWMQVmtzK31byPLFPn42Blik5djJLz85c=",-8392940651768597570,2639137399425510888,-3115107219435909379,5073341349822046734>()
                              );
                           this.bF();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.dK = this.a.a().dy;
                  this.a = com.yiyiaddon.e.g.c.a.IDLE;
                  this.bR = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
                  this.bi = false;
                  this.dL = 0;
                  this.dM = 0;
                  this.bj = false;
                  this.b.cC();
                  this.dO = 0;
                  this.bm = false;
                  int var1 = this.c(
                     List.of(
                        com.yiyiaddon.e.g.b.a.am,
                        com.yiyiaddon.e.g.b.a.an,
                        com.yiyiaddon.e.g.b.a.ao,
                        com.yiyiaddon.e.g.b.a.ap,
                        com.yiyiaddon.e.g.b.a.aq,
                        com.yiyiaddon.e.g.b.a.ar,
                        com.yiyiaddon.e.g.b.a.as,
                        com.yiyiaddon.e.g.b.a.at
                     )
                  );
                  int var2 = this.az.size() - var1;
                  this.b(this.az.size(), var1, var2);
                  this.L = true;
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s135gr56ktfbzg","Az/X4tN9AitmOg0nssu9x3F/U1wApqq4baK0hKVqws0=",2530061521490019421,6458300159635833784,8841457654738506770,-8337289958697989032>()) {
                  case 269087416:
                     break label46;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a
         .b(
            (String)com.yiyiaddon.m.b.a<"s2z552llhjnhxf","hCv2k59uxMo5cFlGkca6OayBZDJsu/sk2BG7849gX6/cYBzK438AudsRrZ1jC0vBHMh2Ryo1LjZh0Ks4+I+VMmbJH2EQVsIuDuqoIq+sgKYs6jv+M+/uYAjvHF9WEwhL",-3659478031575664522,-9057845214763702572,958061147620668214,5929277166518382782>()
         );
      this.bF();
   }

   public void bE() {
      this.L = false;
      this.cv();
      this.b.ag();
   }

   private void bF() {
      this.x
         .execute(
            () -> com.yiyiaddon.d.b.e.b(
               (String)com.yiyiaddon.m.b.a<"sodwehrgsgyvr","uBdUGYOIh9FJwSVD8eHNBXyyM3MrNhQmcMHj6zGRDeIojevr6swygvHE",1798870565181335054,-8031241338427421872,3830788915787991867,-1855610938421494320>(),
               false
            )
         );
   }

   private void b(int var1, int var2, int var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append(
         (String)com.yiyiaddon.m.b.a<"s11onqvufq3hxj","plSBRQUlSoZTWfrFjbsdFOI3ttElH72k8oUJRq/tzz4rOWLcAA4eXYT2TJw40tX37SMG/aCnnNjDuWexlQw=",1929256611586657352,5250255613690190030,-1165305407085956226,3046219188765086956>()
      );
      var4.append(
            (String)com.yiyiaddon.m.b.a<"s18880kt74ak8","MZQFiYnBiIVVkjoaAguhmC/0fGNZ/yGi7Z9mB3jQ3jZ4Zk+BtaD+5n9I/RTPrgejaWnh8Q==",-7212769189348866233,-6119684207879593889,-1133154899896752072,-3077222682768562272>()
         )
         .append(j(this.a.a().toString()))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var4.append(
            (String)com.yiyiaddon.m.b.a<"s28frw5qm5p7j9","dF/q2eOvoQlfYe6ohbQxRHE4cs5umvLz+c5oC8S4+Tkvg6rmFfQOzNAwSv7rdYFk7YZUxQ==",-5072343641627510305,5638313710964094050,-4018830243318326040,-6449211879375172753>()
         )
         .append(N(var1 + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var4.append(
            (String)com.yiyiaddon.m.b.a<"s2x24uu4d60e8f","WrYkh32dI8GQleZEbmD5qZ+M0jlAnSIcbLOsD0I6blhRXcbkDlsOynnI+DFyV2bziRpg4A==",-7652364339391851301,6916132954263237434,1440535731682795624,-954113455261985450>()
         )
         .append(N(var2 + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var4.append(
            (String)com.yiyiaddon.m.b.a<"s1zppvnosbd3mu","oWryUDH6bnLpy06uSOr/LG1jkAM/T0gfR/e0nPp39oE6Ykdz1Hro027VF+FrQVgUq1kVzg==",-9150744896280007815,8901251815850690858,-7044773064367291213,-4289330583559084367>()
         )
         .append(N(var3 + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      if (this.a.a() == c.EXPERIENCE) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s23db7f7m4iige","O4pT7RxO3wUBD5Iw0qY9n6Ppjkth+EKB1L5Lg3SksAw=",-114688374263098022,6262938870674839895,4428203391362960219,3352071246406256681>()) {
            case 693887640:
               var4.append(
                     (String)com.yiyiaddon.m.b.a<"sph09v51vdqy","DlSoHmxMCgTc++NyJJmmozaQj76Mu0Iglaa/RUYQ8qIzerUCCTbGTUW0xaHSLY4fdoL7BQ==",7009005600994684792,-2273975940047660270,-302081464319838191,-8079009493331095064>()
                  )
                  .append(N(this.a.a().dy + ""))
                  .append(
                     (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s1p9206cmeblcf","wJQgbiXpTc7exDU2Gq6fdW3vkjQCK+t01pX2vo4niSo=",-918077678418189752,4820820296748366779,1698906920549766347,2779348318406342872>()) {
                  case 1523085609:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a.K(var4.toString());
   }

   private void bG() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
         (String)com.yiyiaddon.m.b.a<"s3m2wpxjmsbbi4","QDvL6EtGUHi5Xfelu5ni3b7hZbta3+CeHk/vP1chfFlFjyzp0nAbAbhEBFvOOMw0/X4K9y37Y74U6I6xJjKn0R3Av9Nr4g==",-4596923277403405229,-780224951850374904,-4981931960769881164,-2251972071146028970>()
      );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s3rf549kyij57v","DKYXnINiIwsEznJlQSTVzML9b582r1uWTdQoSmF23kTPT6t9BCL68G9ebFfqYJ3eqtWA0w==",3719879628270803856,4760599931526053804,1958899932915582192,-1906965317647066768>()
         )
         .append(j(this.a.a().toString()))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s2o2ut77aqqzzk","xMcPedzOedUtpymbQKBcwIjJei64XAt8D6IA4NpHS6AuFvy/3rngliy27K2MWpbMUc6bvQ==",385683932893060032,9089372738368668056,2613616996948109549,4069964497717048861>()
         )
         .append(i(this.a.aJ()))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s2xida5hlvqot1","9GY9QIzvxq7POsDf/Xg4v8Bvo8szD7RZ8g777lQT1PIrkclNShtjyZJKo1TS6TDmStjm+Q==",-8624976128746479711,-2760581218540657330,7563934303181195450,-5245145310675638140>()
         )
         .append(j(this.a.aL()))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s27qppt0sp1xx5","7qLNIrrMqISdySW5VezmCURF3oaFVXPpVU8yrDC1f7GB+BN52JzjaHTHnDkZkzT8u/f6XQ==",6816315687223597808,1504801740326784047,-1427035089631285770,6817102870959120526>()
         )
         .append(N(this.a.Q().size() + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s3u1joosgqgzbk","3YSteEgF9xnp2ih1SrPCj9Fo2L9Yd2yrxF2ZYERIGhDhcj4icR2XomAePYo4HjaGF9KRZg==",2061647917732083295,-5653214719545443154,1738534656815282473,-7973097385506472183>()
         )
         .append(N(this.a.a().dE + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      this.a.K(var1.toString());
   }

   private void bH() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
         (String)com.yiyiaddon.m.b.a<"s2rbe586kgm9k2","yZ9YfQzl5xHh10/Tlx6QqiFsa0XIDmjHG0NWWh7EGj5PhmoIRWKPGTCV3WiQ82LFEQjeQ296qzZpBarqad7p2D5q",3529427228524933326,-3050356155914145139,4366243343176721483,3969368444963214129>()
      );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s2bx4x9j0rwsns","kTAxWoYPG7YuxZx7CN6uIxSikd9hupY1u7KzfYB27V6Ize9ze3mtJ2EoFb+6NccocmzU8w==",-294741577175577224,7835668353798168761,5707945909124941098,-2613668937549307251>()
         )
         .append(N(this.dX + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"seei8pyr8xws","2/ed3ZOw/+h3s31JGc1iLCx9/pR2OZ+Cc+gmZl8UL3mgVoPahj+kKPP4SjLCHJMNO0G3AA==",6767796772785922953,-8894509062454641553,1207512733207908212,3325261202993365041>()
         )
         .append(N(this.dY + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s38hzgr43juvdf","kt81tQU8IHNNx6Z0pxXs484F/PdqfRcR1mNxJhlTyNdCDWXe4fVglvczuZggQoZ0KOT1hA==",1971634610552859350,-1556843808551369213,6712522900771780195,-1055318711532764127>()
         )
         .append(N(this.dZ + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s3criqb5p43yq4","THNRo0rwLJieTIvwc8Q7MOV5Csc9nlIchqmwDKcEL34LoO3twocRofQ/wdojErHVYVDNlg==",-2047479033556915745,-4790449183300340752,-7098610167191222774,2726121522018199193>()
         )
         .append(N(this.ea + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"srxwojqkg8olq","RqRYjhIKT9vENGZy1N2KRwqrmIMX0ugdWdQ+oIrWcJT4l/Ak2Zz6UNtrmxDrS/iIyBK/mA==",-5346307564316190130,-2890712854855779246,2327593135533993098,-8940267297224842445>()
         )
         .append(N(this.eb + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"soturnutd3s7u","A4QfwyY/4kmxuz8lbU1JGB2lWivWuPVC1Nee6QsR088SMdnjNZlDbR/6U6LphmoEVridAA==",8814806615466889290,5732481534301750159,2360595181234042727,4440005059092603379>()
         )
         .append(N(this.ec + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s2yke8do5oj2o6","dLKpmu8MetuBLnBUEQTISFRHAU4TDJHJIGhAhvmEjfpuYI4jnsAkc6bqJjb+ERMa03BuIQ==",1771013894266266423,-4153539133947238573,-6011403099814674930,-1874753134584074632>()
         )
         .append(N(this.ed + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s27z1js9g44fb","5PjZxPU9NjLR6z5SDmBh3xlvr4BmJSBOECUP8OHKBdY=",4700813005546295367,3987285318264048744,6694804689376800160,-7227892185098436567>()
         );
      this.a.K(var1.toString());
   }

   public boolean y(String var1) {
      if (this.x.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjrbu3nijb4j1","AzP7Eg6lgyZYec83fRkTIDTMnuBRZfzU+U8/F2488MY=",4642957409227333589,-4741025063670773149,3303193998400578203,756851282154977536>()) {
            case -1382239197:
               return false;
            default:
               throw null;
         }
      } else {
         if (InventoryScreen.class.getName().equals(var1)) {
            label59:
            switch ((int)com.yiyiaddon.m.b.a<"s3j69faw5gbjdx","hAm9xcrA5COiyvCctoelqzYY4WC/PjcIU5y6hxHtbD4=",2289558307885088362,-4733637465254381969,2409348773451111511,-3266555969044393442>()) {
               case 1210503437:
                  if (this.x.player.containerMenu != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3kdv37fxlhy5m","AGz7qaMvEfMxKsxv9kIhMKMjgtP/DQ0ShH510GSpwcY=",6362268257302535322,5078414260176161307,8496345707375920345,-5153207299098127998>()) {
                        case 1798870531:
                           if (this.x.player.containerMenu != this.x.player.inventoryMenu) {
                              label54:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ldml1tn1f2tp","OOEb6WZZ1ZGTFC9MCh/ZU+SdtC9E1coaTW8BKRQgGJg=",738964988115979087,8432396555859508422,4264879280818160337,-2626581644891986701>()) {
                                 case 913506861:
                                    this.x.player.closeContainer();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1oy61lz37ceoa","vttJ9PjX/wtScKahviFOBGZySff0G39JQP6TWbM6uKg=",6746110872405279980,2644541022807471219,3815242810219842711,-7202471821069089811>()) {
                                       case -667671643:
                                          break label54;
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

                  this.dM = 0;
                  this.dL = 0;
                  this.dR = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"sa0x0ifzn5z00","RKuNSXE4mV+36ViToidtSSIf0Zf8iE0ouvt5rdQVwDM=",8858303712889416216,-6552982129288567283,-2118545723801013871,-4231198229647163736>()) {
                     case 127588984:
                        break label59;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (!this.z(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s77d0pq6nczon","nRslToaDgW46cXFUaWXGjXQkZbYnea5Hr1bCdwqVjFQ=",-3758998590844822121,-7906548512340512094,4954164541504082841,8594761511637763975>()) {
               case -1995989537:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (this.a == com.yiyiaddon.e.g.c.a.GEAR_ANVIL) {
               label44:
               switch ((int)com.yiyiaddon.m.b.a<"sgej0u52ioqzn","Em8iGjrqgxXdXXuwOV7lEq/weoF6zcMtPBmuQIgLU38=",1233990713159327323,7692350404669967866,7243601908923120864,9193389760606039456>()) {
                  case -1426173787:
                     this.dR = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"skw3v0w1mihr5","oCwJEMQpTqedanujEugp9UV1XfHP4R0xj/Z02peTzjg=",-6596149078704077681,7901258828971936089,820491592176251742,-73909092105633770>()) {
                        case 230221706:
                           break label44;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (this.a != com.yiyiaddon.e.g.c.a.RESTOCKING) {
               label41:
               switch ((int)com.yiyiaddon.m.b.a<"s3enuz4gol14lx","9AUkrgfAJ5eV/43Y47jRAnSmM5fHLxjkSV7iUVglodc=",7987930255002384097,3827011598572564738,-1494914585748650040,-1259395413632437312>()) {
                  case 1938554180:
                     this.dM = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s3txtafei54l3m","3g9z2LIZUL/6FlU4pRM0WSg3oXCn7F4lgyl3N0Dd9dA=",259193347516808089,-7078744297819577066,1265023824048236715,-8875622392726909400>()) {
                        case 616649286:
                           break label41;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.dL = this.a.a().dz;
            return true;
         }
      }
   }

   private boolean z(String var1) {
      if (this.a.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"smg19fpfj5p1v","gSUHK/VxnsTMkw/S8UFG7cRNiR3d4ud4GCTMQqHDF08=",-4257006930533391182,5587109512015467784,995445160307095261,3490565296028931128>()) {
            case -63329760:
               if (this.L) {
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s160rb59fuq2d7","B0NzWJwdx8i97zTUQmZUpwOZz+rCDo9EuiyKQU04OWw=",-2904040766891855624,-6089832767940567020,-7562845299913262973,1569303686291427239>()) {
                        case -323170569:
                           if (!var1.isBlank()) {
                              if (InventoryScreen.class.getName().equals(var1)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sp3yxz6upupwf","bimeLDJqm6zjG4l25UqT2Az4leaNfU+8lwP6qP7bkWQ=",-2070440440777810042,-9118974371540882917,8510915063711168688,4329376597916553035>()) {
                                    case 1232848581:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              if (CreativeModeInventoryScreen.class.getName().equals(var1)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1qh1zv3r9r0vp","a36Zb87Uvk1vpyRlrjmUMEiXXPJKjWl/IxfQ4qztQlU=",6984675862374681891,-3024250033310444895,-8599985548508851015,6386914384501457767>()) {
                                    case 1876979622:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              if (!m(var1)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2pz9dti60666l","F5rakfL8RGjkZLluqDzfGoFbi7s2OmuT0VMcNCRDCYQ=",769673410038513455,-6294818400974756299,-1252373937969691963,-9042603549652637192>()) {
                                    case 1464791692:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.a != com.yiyiaddon.e.g.c.a.ENCHANTING) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sz4gezrmxdhx6","oEM1p96lHn6wFIle7Hl69v25PjWZWVLFkrND2k2TSFc=",7034838513638235729,-5333530428602649295,4594935482324024195,3858804615885240023>()) {
                                    case 509520298:
                                       if (this.a != com.yiyiaddon.e.g.c.a.GRINDING) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3rz86ow7f5c86","6oCK0Tgm7v554SxZvoLE/elPONwmD7MBA6XgSl8nTZg=",6589375327224973548,-3728449253618668452,-5063060899721087475,9205692415575551452>()) {
                                             case 1953449149:
                                                if (this.a != com.yiyiaddon.e.g.c.a.STORING) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s6ode0ygpijyq","rbX15utpoRrRKgOURTSRhDlarCCrPxYK59ZuwGktnCU=",-8587697692323461555,-2749855582272490886,3218782080229910909,5184559112743340723>()) {
                                                      case 57013970:
                                                         if (this.a != com.yiyiaddon.e.g.c.a.RESTOCKING) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3v27bkvdzbzhp","QPxLPlbEA4/594v/n/eMcrm4Z3sK0VlbS5hvoFSfdZY=",3112769431083101764,7506929559104322471,4978419338178892832,5834007935674194652>()) {
                                                               case -1190469288:
                                                                  if (this.a != com.yiyiaddon.e.g.c.a.GEAR_TAKE_GEAR) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1ooplqewlsswp","b9v+O51Lgopu8q260ugyT7S5Z176nlV51momt74x8uQ=",7153962871615739525,2431831267835114511,-8904771969353527471,1995291512740680874>()) {
                                                                        case 1869323260:
                                                                           if (this.a != com.yiyiaddon.e.g.c.a.GEAR_ENCHANTING) {
                                                                              switch ((int)com.yiyiaddon.m.b.a<"sump8rubk4j90","47TuiP+2IMZbCWjB963GZBszb05+yWONemi64YNTDPA=",-6522871109329240210,-8888655371596581780,1737538883006438320,-96728641300152664>()) {
                                                                                 case -1161731678:
                                                                                    if (this.a != com.yiyiaddon.e.g.c.a.GEAR_RESTOCK_LAPIS) {
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s15ofo3p866cns","V/xrtH62iPw6bECHxlgljzxtoMHuWQnhwCivGSp+TIY=",-8122244937678530510,8064113451789853097,1231966272272045970,4035421195878815770>()) {
                                                                                          case 1674246750:
                                                                                             if (this.a != com.yiyiaddon.e.g.c.a.GEAR_GRINDING) {
                                                                                                switch ((int)com.yiyiaddon.m.b.a<"s1gcthq0y4b9up","WxweV2cA+gJRqFxkTF1T0dCLUgr9CdHfyy7aHbNz0V0=",-7928479338477050807,1783991457611329377,-3563790812171263160,-4183983806709826733>()) {
                                                                                                   case 1420515820:
                                                                                                      if (this.a != com.yiyiaddon.e.g.c.a.GEAR_ANVIL) {
                                                                                                         switch ((int)com.yiyiaddon.m.b.a<"s356xezujzavxr","Q9scgNq6md4W/Nqq8iIw70crvC8g8iM7qRjWNT8o0AE=",398481651159527406,-7134300191250945594,4270171420757828610,-7647880333581872903>()) {
                                                                                                            case 363569610:
                                                                                                               if (this.a
                                                                                                                  != com.yiyiaddon.e.g.c.a.GEAR_TAKE_ANVIL) {
                                                                                                                  label78:
                                                                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1qyhyn3u0cpcu","yZ8P6pqqCNJAC2Ff3uyN8mNM2tr2STrmJykDKBBx/0w=",-3817361483249892269,-7087074523695619795,-728842107981863329,-5225926063791680288>()) {
                                                                                                                     case 19669243:
                                                                                                                        if (this.a
                                                                                                                           != com.yiyiaddon.e.g.c.a.GEAR_STORE_OUTPUT
                                                                                                                           )
                                                                                                                         {
                                                                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3je80vgtsxwg1","sqoNc42CO6irQ23g93JYd6FVET0F0UsS4YuvBarN0Lw=",7326188208319669896,-488688226972786833,7914546092929676970,3456794929303710428>()) {
                                                                                                                              case -863163638:
                                                                                                                                 return false;
                                                                                                                              default:
                                                                                                                                 throw null;
                                                                                                                           }
                                                                                                                        }

                                                                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3dkvf4yy7q837","2WzmX6HGJbIXUzrKBENuOp57iM8MbZ0GjwcFsua81Ts=",-9156850116538169707,1649452715670952754,1485981717026953362,7105504403914143407>()) {
                                                                                                                           case -895950729:
                                                                                                                              break label78;
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

                              switch ((int)com.yiyiaddon.m.b.a<"s235j3vpg4ptbc","tbQmfSLk136yFWY3iEfwCkdddfhqxu0+mLa9JxLWgF8=",-8803172554138673971,-5210824423405079564,8606062182730662679,-743828213802802699>()) {
                                 case -2009096111:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1bgh1lla9viz7","fkKWKMST+9ssKTeL992FzPtZwZW3IZ60QpW55wZAgxg=",-87619454003363075,4814698039509781518,4325646805411986769,1521172022369684797>()) {
                              case -1112469958:
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
                  switch ((int)com.yiyiaddon.m.b.a<"slxhow0czt2x6","XWJ1athHm1ka7zx+GjyZua1bYXEag7/D6brDlgEBt+I=",4220260901600760051,-5672052127255524286,-8787972310174322616,-5316334910584375239>()) {
                     case -1816093667:
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

   private static boolean m(String var0) {
      try {
         return AbstractContainerScreen.class.isAssignableFrom(Class.forName(var0));
      } catch (Throwable var2) {
         return false;
      }
   }

   public void ae() {
      if (!this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sesjx4f8v2v","hPosuh39Vm/N0hHw6dmllDtleXE1evUnB0CXiYbz7tU=",-5230215368364344149,-1540517367140653222,-5025628317890161989,8589680762520784475>()) {
            case -352720960:
               return;
            default:
               throw null;
         }
      } else if (this.x.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dgu1j60v7sa4","dLpKcRKEylumYfzBJr+GwJiqZqEW0s/A2T/RhJ2zSk0=",-4884612803468756462,-8200641583574058819,-3120376787984211228,8137457882598743>()) {
            case -242611057:
               if (this.x.level != null) {
                  if (!this.a.a().aS()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sbuhfyb1eqpar","Hb2LFA4fr7Tp6lqVCRjToL2pQM6JfFN88xXhVv51jCw=",-4686614147510002134,3930933600148822876,4902000778357645868,2785693441083938191>()) {
                        case -837840829:
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"sjvmkvaa8n6fr","p/e5pQTnlqBdd1kgvga1P1D+CtREh5Os0IrFEk0AuTpKEUqA5jzjRhMubSDC/vWMUxW8iO2frCa3M4z2VpjjruPOQ6u/5/vxLmTwjtGp",7210335000676291966,-5519909499071811713,599333870595586816,-101095504931221056>()
                              );
                           this.cv();
                           this.b.ag();
                           this.bF();
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (this.a.a().a == com.yiyiaddon.e.g.e.e.GEAR) {
                        switch ((int)com.yiyiaddon.m.b.a<"sv0az5w1e2x8j","mwFSQgLGy6rG+i+e2geG4adzBoX2nh0n4t3ik9NVTeA=",8576593877078970904,-1043440557884072935,3743876062957853468,-177837967225156716>()) {
                           case 587866500:
                              if (this.e != null) {
                                 label201:
                                 switch ((int)com.yiyiaddon.m.b.a<"sm2gqi377c3a8","3YI4JfnMCZPMXSTSXTfxYm80aR8kceJnlCPNylAEVnk=",-5668798849974055919,1779535288802944322,-4001140796365130082,-8675758397517628602>()) {
                                    case 1933467211:
                                       if (this.a == null) {
                                          label199:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ggq2bw1a7zwr","sluLiRY0uHZTEg1Wmrjy7WcbR1QO9tGwfUS0iDWIri0=",1038674392558045712,2623393600607857770,4934229851483787958,3063062264166639617>()) {
                                             case -851373415:
                                                if (this.dP < 0) {
                                                   break label201;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"soloyk09nsqac","du2RuiFqJYv0l3tBCW8I/zQP9AgzoeWTd3bcKOdP5M0=",-5260616550854631796,5121349041069958595,-5953860757144683743,3626462080647644196>()) {
                                                   case 1090709486:
                                                      break label199;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if ((this.x.player.tickCount & 31) == 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s5uif4ztd8ce","DoyQPfP5frPyfv1pJCeLv7tMLtHBs/BECwvpF3bvfB0=",-5480037160532589949,-2852662603067784309,2511077524515664630,-7764158057627989752>()) {
                                             case -108695384:
                                                if (!this.a(this.a)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3d78tou0pu3vw","CEuXFn6ZixqEaxL0v4ce2X21SI9fGlBqdNSygG6nH2Q=",858770699291661776,4111724156361164211,-7671922494709592110,8371943765158354394>()) {
                                                      case 1401906414:
                                                         if (!n.a(this.x, new ItemStack(this.e))) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3q34srhoqjp78","NmNOPdXDilk2sLhTDmU2ga0SpPlShP0fwe+Lw70xyR8=",8048172466719800673,4580086112524337596,4711946314773601508,-2067062014443241765>()) {
                                                               case -1940595235:
                                                                  this.a
                                                                     .b(
                                                                        (String)com.yiyiaddon.m.b.a<"s3w2nafpwg21rn","CxIZ3WaqTC+paagRtaEmDYu9vwEg4k0EMJRmUfGw5jRCMrBknH3dQN7dlTRuKCREA4i0RCYm7zNkxbn4oLd7EzTlMTa/spBfWmYv4zIk7Q3S2xHRNaBsYQ==",1477959124193255757,-5073636846762263736,6862428114509718563,3587388623520341591>()
                                                                     );
                                                                  this.cv();
                                                                  this.b.ag();
                                                                  this.bF();
                                                                  return;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break label201;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                                break label201;
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

                     if (this.b.w()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s21s8ir86csdvk","Mqy3rQnE5xNWzP+Bb6bmp+UV2WiNefcTZxmAqKIAG0U=",8949915053460176627,4833345035686048523,-3666629388053700712,-1231497615087257999>()) {
                           case 1153047627:
                              if (!this.a
                                 .name()
                                 .startsWith(
                                    (String)com.yiyiaddon.m.b.a<"s428m1ytjsmmy","g3KpBH+dehqf7hE73kSmjLMuUL/3aIpzs1m3SfdMUJYINgv4Txk=",5306796818451038802,8023212192915472711,8265248487440319982,-793143878499234506>()
                                 )) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1k1cztp62uio2","N8EywiQvPK+aO4OCW/iz8fO0XJWVnzU5g6M0hLQwh7w=",-5702700228059805396,-4715202810572022598,8229760312109487272,3961756064950154297>()) {
                                    case 1234753585:
                                       if (!this.a
                                          .name()
                                          .startsWith(
                                             (String)com.yiyiaddon.m.b.a<"s3tjih1qywsje5","sm7kU70SP9LIfbGNzcEUo56/W3hy+v5ZJcm35nN/ECIkSRLQAPElmEMekN7G3EkL",-3456614457353718424,1751050893995654345,4923445364330960990,-3075776928304708744>()
                                          )) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sz1eh78eggidc","ND0mgYyXu8mDdK185sfPSXcMBgNB6VDtaIDZkaSD/J8=",7733246034209061099,-4507964320773677458,7394376987874877035,692929348263786097>()) {
                                             case -863322833:
                                                if (this.a != com.yiyiaddon.e.g.c.a.FARMING) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3a5z5x0zau77o","rkCENUfloL6hwvH9A1njkinDTPW96cGy4FAVFulzL/A=",-4749374508060000165,-3139448119209259175,-6851906921648726737,-667534910288416692>()) {
                                                      case -1753088130:
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

                     switch (this.a) {
                        case IDLE:
                           this.bI();
                           switch ((int)com.yiyiaddon.m.b.a<"s1mkt4ktlkaz8a","igV6JgegjtMqlSo2Kq3Wj+bMnZET94co45vt1SRtxXw=",-2980217797052981529,4537207207043524885,4757032317036401094,2204091908046112516>()) {
                              case -433066966:
                                 return;
                              default:
                                 throw null;
                           }
                        case WALK_TO_FARM:
                           this.bJ();
                           switch ((int)com.yiyiaddon.m.b.a<"s1vui6g13nzluy","hQQSQ2B8Oct6yIX2uF++gXtGbfdXvCEr6Q9vVG2D4Og=",-7103689107123305973,5627619163263051926,7139835327734765346,2411466061411073998>()) {
                              case -2137557292:
                                 return;
                              default:
                                 throw null;
                           }
                        case FARMING:
                           this.bK();
                           switch ((int)com.yiyiaddon.m.b.a<"s3sdbicfk6d2sa","FdvtZofUr61OWqavCrgZjQAZy07FnITEuhTeBQNSBJw=",4586588274426025366,7978450252396410089,-2391359063383061582,5324535819641090290>()) {
                              case 853454806:
                                 return;
                              default:
                                 throw null;
                           }
                        case WALK_TO_ENCHANT:
                           this.bL();
                           switch ((int)com.yiyiaddon.m.b.a<"scxsry5foffgz","5qdqndn7ltmR/7f6cORhKv0Ogctn9biMlSpDE1+GXJM=",-5133360506263783670,-8269497607970835552,-8708881939463273121,3620119059197540283>()) {
                              case 2050432271:
                                 return;
                              default:
                                 throw null;
                           }
                        case ENCHANTING:
                           this.bM();
                           switch ((int)com.yiyiaddon.m.b.a<"s3bbmmfr3tb5o7","XhLB6IfPQJX2lbyDTx4nYGYwNriMa5cvxC7ftOVZbek=",3184145284070065592,5180577595464903789,-6676694796790570520,-7538784298387866375>()) {
                              case 878989698:
                                 return;
                              default:
                                 throw null;
                           }
                        case CHECKING:
                           this.bN();
                           switch ((int)com.yiyiaddon.m.b.a<"s11zxelcauidmi","GsMNs3SKOKmGLu7Qkkggtlnqpf6oyDARYs1WMDz1dfs=",5784321528422252571,4421246601139752393,-7293903546682670542,-979413852900028201>()) {
                              case -1504191186:
                                 return;
                              default:
                                 throw null;
                           }
                        case WALK_TO_GRIND:
                           this.bO();
                           switch ((int)com.yiyiaddon.m.b.a<"sz8n9i0dp5yks","pA62P7qtF6VttiDs3M61M1bSVV3mXjUzW3/DmXytwWk=",704060260282568535,4812044256866134737,250098950402652647,648609078324428243>()) {
                              case 261078172:
                                 return;
                              default:
                                 throw null;
                           }
                        case GRINDING:
                           this.bP();
                           switch ((int)com.yiyiaddon.m.b.a<"s1mzuo7i5bo198","zsjhbcFrG9ofeLmEwgs9jste3tcGw8mlq7XtgajwgTc=",6993167257394464522,-8231872090014209832,8683782380653395109,937004694615067239>()) {
                              case -93691922:
                                 return;
                              default:
                                 throw null;
                           }
                        case WALK_TO_STORE:
                           this.bQ();
                           switch ((int)com.yiyiaddon.m.b.a<"s3jizrjgv149x7","Q/Xu3A8Pgf/hyjZVbzQLOOIcW/dQv+T0qLVwhpyNZxw=",-3265223214168614951,8174168178041068597,8796928297216889911,-6908137637947224210>()) {
                              case 1008017875:
                                 return;
                              default:
                                 throw null;
                           }
                        case STORING:
                           this.bR();
                           switch ((int)com.yiyiaddon.m.b.a<"s2d1slceozsho8","/Op4ZggrRuLE2/9toifP5Dv4p7hCFxRoBj0JNPOXheM=",-5256581402266788044,-7581603224243728342,5010413795627158479,5516320425759586174>()) {
                              case -1548986061:
                                 return;
                              default:
                                 throw null;
                           }
                        case WALK_TO_RESTOCK:
                           this.bS();
                           switch ((int)com.yiyiaddon.m.b.a<"ssqleo09fi2zl","MiE+fbb5XfUAi7Ewcto7KJA8bk2h44dqxXkoUMpYeGk=",-6484346581652742281,-9079534558674861296,-4650147453250400778,7249088704325965585>()) {
                              case -1516519237:
                                 return;
                              default:
                                 throw null;
                           }
                        case RESTOCKING:
                           this.bT();
                           switch ((int)com.yiyiaddon.m.b.a<"seu2o8xh9nfze","w3pibfppH2ITvO2z5wqrbu2Wzji/VfH9o9GT39u0Khg=",3609548812071407520,-8335261927648850955,833803194269385519,-438641616445479867>()) {
                              case 1309555589:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_IDLE:
                           this.bW();
                           switch ((int)com.yiyiaddon.m.b.a<"se2rzlrn4qiok","xqHsIkWXdRwSe/M3eiT5tfIr4wF6NL/6FGFXrYCS85s=",-1945186424744548222,4432916076662360199,5738846091429451649,-2955696600439951106>()) {
                              case 23478522:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_EQUIPMENT:
                           this.bX();
                           switch ((int)com.yiyiaddon.m.b.a<"s2h3bb9wxak17k","CzJZJjKQtPoI9oXEMTcH7dePkq4URccOKWC0uiu9JpE=",-6300276286884392556,-2391925711768915860,-3333975060852953956,-6050369306889463046>()) {
                              case 652993362:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_TAKE_GEAR:
                           this.bY();
                           switch ((int)com.yiyiaddon.m.b.a<"s12e22ub5vg9vq","xmspNa7XxlIaDfNoyKiRjT6eT77oulpHa46z2Tva+sM=",-6413265935178963338,94742651319593369,-816700453954096690,1406823501364827741>()) {
                              case -2095860953:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_ENCHANT:
                           this.bZ();
                           switch ((int)com.yiyiaddon.m.b.a<"s24qx3wp7wb41y","3AC3GIG0ecPKBmKjsGy644pQSGOZ1qXY5Fdx+irXfUg=",-122788449617825758,-8696026829482961995,4381886816507528118,7478153305178549578>()) {
                              case -649789359:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_ENCHANTING:
                           this.ca();
                           switch ((int)com.yiyiaddon.m.b.a<"s1jtn9hgzd3qur","KSAO3Y85sFrkrsWOc5XBk5m3+67BHC8nmbsmf6AzThE=",4529899709250904774,6742559383480520435,8110487974925613119,-1496488131262836144>()) {
                              case 1421657712:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_LAPIS:
                           this.cd();
                           switch ((int)com.yiyiaddon.m.b.a<"s1lgd9u4po0i0","7z6WJgI7UIdUImXuteDwvPGGyXJhZxR+Fg47RSmCzjc=",-7592721787301474273,222813167841853830,-9162893562985050615,-9085924003461301988>()) {
                              case -1207922888:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_RESTOCK_LAPIS:
                           this.ce();
                           switch ((int)com.yiyiaddon.m.b.a<"s3s6gg90865ltj","wjfQYjyZ/9DdeGGWiZwwt9/x0BNaujeZSZh4fSOWSQ4=",5649980031059783898,-804020583068621845,5306707915188847988,3869506860980015892>()) {
                              case 1943289872:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_EVALUATE:
                           this.cf();
                           switch ((int)com.yiyiaddon.m.b.a<"s2etzmwevdnhnx","W8CqVP2UVHR5CGYjIxxRluV5x5wp9DXcWdP4TAERHT0=",4544846591718572718,-6308911920597427111,-1054442053245226767,5145754337485524030>()) {
                              case -2090033219:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_GRIND:
                           this.cg();
                           switch ((int)com.yiyiaddon.m.b.a<"ss52ng4re7ky7","NKKY1Er38chENTDUc9n9HWxEaptZRP32+uemripJ+DQ=",1635287120339953525,-2355116230861275349,5124700883919877693,2543119885761615835>()) {
                              case 677920880:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_GRINDING:
                           this.ch();
                           switch ((int)com.yiyiaddon.m.b.a<"sk2stwnuhx13w","/olgFcRQmIlf+3quTnhlfp7A1Ffuvitqr72Tu8u7/Tw=",-8717922509511503116,-8422301952679470868,6110715299736840553,-4510387613545250553>()) {
                              case -1479284744:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_ANVIL:
                           this.ci();
                           switch ((int)com.yiyiaddon.m.b.a<"s26x9r8lgpr10w","IvcyG6rD9rsspmgk7+r65dn5KxXdyVDXo5yF2ed/w1w=",4100648609143064338,-1417443364513466235,-4800365950132361664,3547142797234161279>()) {
                              case -1887503255:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_ANVIL:
                           this.cn();
                           switch ((int)com.yiyiaddon.m.b.a<"s2mygqv2q9iiqh","6evqyzXXLFWc8Vxwgks7kHtgJadet3uEaRQJ6GDid20=",-8913006045741915679,-2502035466720051198,-916387077679545340,-8803072184773591255>()) {
                              case -376358766:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_ANVIL_BOX:
                           this.cj();
                           switch ((int)com.yiyiaddon.m.b.a<"s28y7vvujlv0g","F1zZw/FPvX7M4VQnB5/FxdX8EEbhkwZ1rRpBZxu9lj0=",-5125842357402305009,3895459282883848162,-1271249030283050136,-1847530922970203097>()) {
                              case -878507014:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_TAKE_ANVIL:
                           this.ck();
                           switch ((int)com.yiyiaddon.m.b.a<"s4f3aw46watcg","f591jwHcWQjfHFLCdB7N7eXkYJoPJeZCLgrFHWZCB8w=",7653380498641052000,5869715031935338699,-9115250551921694240,-5852685295492972658>()) {
                              case 174612606:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_ANVIL_POS:
                           this.cl();
                           switch ((int)com.yiyiaddon.m.b.a<"s2ajv4ekk9zjb9","0hsUMSFpRq+ryuinrPg67bI6CTtN9W1VTKUm0Mx4uVg=",6821836227039722982,-5665497518363201410,2447317968790233905,-1346161128058111600>()) {
                              case 643311245:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_PLACE_ANVIL:
                           this.cm();
                           switch ((int)com.yiyiaddon.m.b.a<"sqqchtwt9f8u1","Ql7mnIbwXUjvmO3evgBc8hGZegFAA31VQ4urbGS3b1s=",175734900696853498,-597467778502658329,-7999779049633285230,5221239373713071654>()) {
                              case -398688211:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_WALK_OUTPUT:
                           this.co();
                           switch ((int)com.yiyiaddon.m.b.a<"s3q1y4490t52od","FFayRGE+JG1notre+VF8qUcl9KdfdTKI8gFtStZjO9Y=",-321195905957772950,-3712391247980546654,-2734040005685782732,5583675119489749654>()) {
                              case 153643726:
                                 return;
                              default:
                                 throw null;
                           }
                        case GEAR_STORE_OUTPUT:
                           this.cp();
                           switch ((int)com.yiyiaddon.m.b.a<"s3o55v5swqwp7i","5hvXYN7qTJr5ds9v4684bOtg3GrkKFCbxqEUh9mLPs4=",1536580664675114547,-4717955797764857973,5333422542311036731,-2926306845723460596>()) {
                              case 502269783:
                                 break;
                              default:
                                 throw null;
                           }
                     }

                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2d6s3ij2ry4mr","FMPXKrXK0bMrLGRdAFUy0yeeEFERNllGLbY4Ni1QDZk=",-2324249551062861001,-6906324982670542480,4616297252011444085,-5572066148034056323>()) {
                     case -1859688690:
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

   private void bI() {
      if (this.aA()) {
         switch ((int)com.yiyiaddon.m.b.a<"sjrw0ibpd47g7","NhPoNzyF08fUiOvyCu+2rTetWq6gR9bt2xs8NdVf1i4=",-5774744971109509847,3794608131992417508,6074667358322947844,-7842740183791723691>()) {
            case -410756197:
               this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
               return;
            default:
               throw null;
         }
      } else {
         int var1 = this.x.player.experienceLevel;
         if (this.a.a() == c.DRAIN) {
            switch ((int)com.yiyiaddon.m.b.a<"s23a6p3anfu92v","iRPoTRTmMTnKANR4rpyyXvQyYt3JAcFr+6Yvywyu1eE=",3573131949183993980,7554345278587371370,8423267054345275894,7119145392131629576>()) {
               case -85593045:
                  if (var1 >= 30) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3eh35ymjulwle","9TLb3luGDjJn1DuvVEtI/cbsJAYYNTa4x97Xz3dWP6Q=",6088400954006340943,8152811790640012937,8809722374261165939,3487843622009535611>()) {
                        case 799172637:
                           this.a(com.yiyiaddon.e.g.c.a.WALK_TO_ENCHANT);
                           switch ((int)com.yiyiaddon.m.b.a<"s2f4wu7ldit8ml","7q9DuVvicXyXW0Y44QU1+kO5ICQeTN03XjsW14VqOe8=",2454028471617072166,-6745262887063765854,-5047919228313169469,-9183173200475661669>()) {
                              case -1126918333:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.a
                        .K(
                           N(
                                 (String)com.yiyiaddon.m.b.a<"s2ptydfrtogkpc","Z6eepE5g28pE34PDRGQG1pFrQyIyt9aIl+gjmgEEgbqzgSe1",7322278536563876232,-5061782391695046327,1666279135377023935,-9127738761511348070>()
                              )
                              + ""
                        );
                     this.bF();
                     switch ((int)com.yiyiaddon.m.b.a<"s3t8nu8rkgly11","lA09IeKBMmGWS2yFnl6sAXotX/qxv+Qm79ZTZnB0z8M=",-6335446697540266443,455051184601030444,8209274814202516319,1275507478251575529>()) {
                        case 122302834:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else if (this.dK <= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s3q920pubhq3mm","ZMFQ/TyjDYOHzWOzph0av+YoQhvbbQMm8e+SZKt9XuY=",-3462703111627558941,-1318699598729158909,8303789917406744926,-2555371023017814449>()) {
               case -289001209:
                  this.dK = this.a.a().dy;
                  this.a(com.yiyiaddon.e.g.c.a.WALK_TO_FARM);
                  return;
               default:
                  throw null;
            }
         } else if (var1 >= 30) {
            switch ((int)com.yiyiaddon.m.b.a<"s10ytgubxsy2ul","xis5tIgr1yCjPxfhL67CS9j47gaOQWgqGgVRzSTq+WI=",3475928338867959530,-6935170780338776944,-1518815194715099593,-7197066924148352216>()) {
               case -971396443:
                  this.a(com.yiyiaddon.e.g.c.a.WALK_TO_ENCHANT);
                  switch ((int)com.yiyiaddon.m.b.a<"sjrzbtcybptxe","8sOsAKBCqzSOfsPH8TiPMm8zrroJrpXOm0/BFMMC5OM=",2911336223733023904,-4824449651640477594,4118830172129475838,-8190293118409037130>()) {
                     case -754003119:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.a(com.yiyiaddon.e.g.c.a.WALK_TO_FARM);
            switch ((int)com.yiyiaddon.m.b.a<"s1jlo20fpxsin9","wBCRyoSn5k0IKG1qESeWBrc6r5UU5HNN4CGvYyO5YR4=",1024087122299677875,2198358648864064135,-5112427445320048507,-3055024968368107289>()) {
               case -1119639543:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private void bJ() {
      if (this.aD()) {
         switch ((int)com.yiyiaddon.m.b.a<"s29sy8mdq1fr7b","OJaJEC91ciUIlebFI4wtvDuu9SW0rzmTjsKNd+7pvhI=",-7187588516736260193,5063555253266483800,-7365745906989401501,-4073131468009847764>()) {
            case -1918156854:
               this.a(com.yiyiaddon.e.g.c.a.FARMING);
               switch ((int)com.yiyiaddon.m.b.a<"s12b826upd0ymk","y8hKOdJoFo4EZsOSjdRAMlGXgCZlbX2+D/JpIXBExG8=",6481728452118152314,2518169738530242741,-6548801638233662052,-6243541441485747433>()) {
                  case 979084739:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.k(this.a(com.yiyiaddon.e.g.e.b.AFK));
         switch ((int)com.yiyiaddon.m.b.a<"s10fnuv0uhncz0","N1k4hspgTCLThgSCKvkIjY+hFYp//MfT3F+gCMUlrFY=",3162669614504106500,2054680979880460787,-696070452260626979,4480647013479753541>()) {
            case -838405442:
               return;
            default:
               throw null;
         }
      }
   }

   private void bK() {
      this.cr();
      this.ct();
      int var10000;
      if (this.a.a().a == com.yiyiaddon.e.g.e.e.GEAR) {
         label34:
         switch ((int)com.yiyiaddon.m.b.a<"s16j19omj4wuq7","ALITDs+Q1uzOK0XhZit/B57KFHwSH044WB63zTWXvRY=",1022421931116144578,1968817559562379815,918111022405877284,1773920159039660575>()) {
            case 1429712330:
               var10000 = this.dQ;
               switch ((int)com.yiyiaddon.m.b.a<"s2bjj6aozuo5em","wEBGDTIITetZhhqRxDJtnIBos21EYU9Fm9KLrQg9aRA=",3650358663364518761,-2079536582784671155,2738658964799047810,3665598790319730777>()) {
                  case 227628804:
                     break label34;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = 30 + 3 * (this.a.a().dy - 1);
         switch ((int)com.yiyiaddon.m.b.a<"s2qrquw3yb5uor","9E/mEe8LOu7c4UdOYHPVqZ8GbugaKuRTK2PtAQzmv9Y=",6085116988937861156,-9015542153246793363,8814492400181719265,-7566015859108285396>()) {
            case 1010076114:
               break;
            default:
               throw null;
         }
      }

      int var1 = var10000;
      if (this.x.player.experienceLevel >= var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2gk6amp1u2uoz","E13axcpYSWMhRXtGV0mSaakDiXvNAMBQReWxa6PTaSA=",6221216453725342421,-197533801613671986,-3638256836724072357,-2203679750132943106>()) {
            case -1219648991:
               this.cv();
               this.dK = this.a.a().dy;
               if (this.a.a().a == com.yiyiaddon.e.g.e.e.GEAR) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jerkiby0vouf","IH3zyxK7sUdsnu58yq2VlxbQbpnsh5niZJY/+5r6ofE=",-5286148968206619251,-1851032554506014521,3968104777739475299,-5361695842928550018>()) {
                     case 604267738:
                        this.ed++;
                        this.a(this.b);
                        switch ((int)com.yiyiaddon.m.b.a<"s3b84oo8n2ujzz","SkD9H3nV2TUXsu+ueIZMheMXBPSEgaTUsAqQ2LjrCEY=",386939068598178977,7586352395018646248,-6066058620927031368,-8488832850371943248>()) {
                           case 880787310:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.a(com.yiyiaddon.e.g.c.a.WALK_TO_ENCHANT);
                  switch ((int)com.yiyiaddon.m.b.a<"s3l2bttvljo8vn","R6y/X0BQyKxmOcWa6W5jW2Bzn1tujShK5+Zjn4bUnm8=",-3622494543809467789,-5915751506520821753,5276514111497424178,7987975414347480804>()) {
                     case 2058641088:
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

   private void bL() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ENCHANTING_TABLE);
      if (!this.a(var1, Blocks.ENCHANTING_TABLE)) {
         switch ((int)com.yiyiaddon.m.b.a<"s4qerkcljwwrw","IMhK5rfgsgsc0z3RAevNIbrHRnQG2/GIpE4lguv9HIY=",-2448118511614744273,-3610655737641811457,7157119613578327357,-4613421298948284949>()) {
            case 2004689917:
               this.b.ag();
               this.a
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s3dfr2yspzi6ga","VxdJ9MlnyOV0SI7kvxJ1eFVVX7DngwStzj7CDfrQ2HkKVNaoyaIndLFOpLk6Mw/fx+ToE22rRj1IU2rwJTl4gcIj1owNNYUZdi03RVXiAl/gA9XbfIlt0w==",-7129500714783478765,3210722531386788332,4807828332839479005,-6407801114639396333>()
                  );
               this.bF();
               return;
            default:
               throw null;
         }
      } else {
         if (this.b.m(var1)) {
            label31:
            switch ((int)com.yiyiaddon.m.b.a<"se1goql5kt0ax","MlGLSIOnTl2AL9jS/2qBSEdbnx8vCGeoRmzykz10ZEw=",-4878891425498039477,-4840987627448177514,6293481060912743306,-2902999278445528776>()) {
               case 1784188106:
                  this.b.ag();
                  if (this.aA()) {
                     break;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1160sxgzzqrbw","fnuqe2O6iRWiS/7ksGN7hhpjrOdGRlanSqOqCa3UvGg=",-8408952132562104025,2607758974160461470,1262237558202379348,-2382547480104151680>()) {
                     case -1469633002:
                        if (this.b.d(Items.LAPIS_LAZULI) < 3) {
                           switch ((int)com.yiyiaddon.m.b.a<"snvumgda8v23k","WpGauRFm0XYYOsFEUjFB+Rwcszczecbk8nYKoQ9qDvo=",8501270156009888067,-4457960915683202687,4566493031109777047,6528506852931618644>()) {
                              case 1086583673:
                                 break label31;
                              default:
                                 throw null;
                           }
                        }

                        this.dL = 0;
                        this.dM = 0;
                        this.a(com.yiyiaddon.e.g.c.a.ENCHANTING);
                        switch ((int)com.yiyiaddon.m.b.a<"smqdcxn71zca8","inQoefDqHgyP6LF5bfc4Ka29Pfhtczzb4fgv95NhvJ0=",-6308783500982841888,-5265269520524210312,3470771496143429337,-3761581732894919853>()) {
                           case -1509958532:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.b.i(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s17sziwygg23mz","+EsgHWh0YoFN7OMlzgdYu07LmmjcJBh3/Da575sSUxs=",-5190191023423214346,8632058293958753596,-5480513170666625398,-3555137738267166503>()) {
               case 1763286830:
                  return;
               default:
                  throw null;
            }
         }

         this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
      }
   }

   private void bM() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2g2oy3quhyzxi","Adr47dHtTM35/+Fm22BDOdRzNPtzQUe2rQlYoKsFuHY=",-8657065683573581493,5349039101783645210,8586520028917463188,-3013060514089947634>()) {
            case -1525218574:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         boolean var1 = this.b.aV();
         BlockPos var2 = this.a(com.yiyiaddon.e.g.e.b.ENCHANTING_TABLE);
         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1tqew6zpjle3b","GB7o2HsFQ63KAxHOv9f7ykNS62Nd4SVF74icpdOLewk=",-5439948321765593990,-8969967420208509873,2221975199415808933,-2575665909375439373>()) {
               case -962201408:
                  if (!this.a(var2, Blocks.ENCHANTING_TABLE)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sx74d6zkflzwk","FGW0z2/w0MCrQUP7qDaBdBCFJSa49h2VoiDoV+03EGo=",-8402048934493566933,-6180805124266184276,1856462069219635286,-7885571363327141732>()) {
                        case -342379789:
                           this.b.ag();
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s3dfr2yspzi6ga","VxdJ9MlnyOV0SI7kvxJ1eFVVX7DngwStzj7CDfrQ2HkKVNaoyaIndLFOpLk6Mw/fx+ToE22rRj1IU2rwJTl4gcIj1owNNYUZdi03RVXiAl/gA9XbfIlt0w==",-7129500714783478765,3210722531386788332,4807828332839479005,-6407801114639396333>()
                              );
                           this.bF();
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

         if (this.b.d(Items.LAPIS_LAZULI) < 3) {
            switch ((int)com.yiyiaddon.m.b.a<"s83gru97i4cv0","Ql7LA12ikUyLhHTfpdOqYQGiNo+UGSJqDZIPfzWldXo=",-5198687257111439587,471673609111105021,-2368877601440198563,-4305926639051193121>()) {
               case -891430214:
                  if (!var1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2in7e3wginawz","44MVuHdK43gmmGzDRvZlmUGH/UXOmhPgw4fZhXdBMYU=",-1969283690372658594,5576958122116158196,-5864171221119540967,5683445232859564335>()) {
                        case 118635143:
                           this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
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

         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1hfdcpx9fqlzp","BWV171/opnFHfDnKbe/pkOhOpGole+iki34rLNPdG4w=",5680783272690325085,-3258349863983758813,7611331656147945162,-1461442335743536663>()) {
               case -861775606:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2di5iim45fu2h","OL7ZrHF81i3/8lH6GcfoRc5Qq2kBLkXgR0qbUf0a9X0=",4898329061028697593,4041075086375844361,-2857260661440466984,5922539442755186267>()) {
                        case -726045280:
                           this.b.l(var2);
                           this.dM = 20;
                           this.dL = 40;
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (this.dM == 20) {
                     switch ((int)com.yiyiaddon.m.b.a<"sx83vn9bysiyx","CizXZ+2E8swnZm00UpPFaeNGCCTDueLZH65yJQbt+S8=",7754689835758371419,-8365777341976672456,5609229577424641686,1892251903493234813>()) {
                        case 1931049834:
                           if (this.dL == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2yj5r1h09c25y","9W25VXss1OHYEAUVAsn4V8SVsBDZuoLylxI52WlITL4=",-4477546195858216957,3294856092122703377,6552228533784692511,-5188599778163977833>()) {
                                 case -1453778231:
                                    this.dM = 0;
                                    this.dL = this.a.a().dz;
                                    switch ((int)com.yiyiaddon.m.b.a<"sj1vtajuz3d31","T7R/rHWv+FJWQ08chhpXyBFKctIhwxoCiSa8ko6Gd9k=",-5638673489044057927,-3508293080794656009,-1366793074060608100,-4173057311896613056>()) {
                                       case 582941964:
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

         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"si613twa1m9ey","ZkXCZpHyrlgUbstGQXCdf3+6UF77ubturuzGQpz3lyE=",618856677319669733,7780487204255375573,7041571885801648608,1395876539292545661>()) {
               case -850507329:
                  return;
               default:
                  throw null;
            }
         } else {
            label167: {
               EnchantmentMenu var3 = (EnchantmentMenu)this.x.player.containerMenu;
               int var4 = var3.containerId;
               switch (this.dM) {
                  case 0:
                     int var7 = this.b.e(Items.BOOK);
                     if (var7 < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2qnmznjnql3y7","Qvr4gE93YwSMPLCTy4+9rX7FN17RKNSKSc685NrN8Cs=",-74178837310303207,-5215501670682281746,-4879289463547501783,-812943994239663900>()) {
                           case 971082328:
                              this.a
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s1vvklrrniyvd7","Y8vBzz7q/INrSGRWjvfgm2rmO7Omf0DIzawNypBy6J/nJ//w8ThnzH/j9ACcdiuenbKxvA==",5450245013076625603,1104776864667434376,-3503995621666676799,-4050619017671382899>()
                                 );
                              this.x.player.closeContainer();
                              this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
                              return;
                           default:
                              throw null;
                        }
                     }

                     int var8 = this.b.a(var3, var7);
                     this.x.gameMode.handleContainerInput(var4, var8, 0, ContainerInput.QUICK_MOVE, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 1;
                     switch ((int)com.yiyiaddon.m.b.a<"s2aokb9da02fwp","OOoHJTTJJS5PuIUZx7gTSWJuiVLpnyCiPL4WZgwMhfA=",-1678445238810651191,-2055584397681875987,-6432030018393817532,2006752984639497703>()) {
                        case -731201862:
                           return;
                        default:
                           throw null;
                     }
                  case 1:
                     if (!var3.getSlot(0).getItem().is(Items.BOOK)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ci3vnbtmkck1","tHDS3mb7loW3WQX6c+cz1q1mu6f2U4V5smrOLTneME8=",7320311563759668356,5346449602098424663,790594468376294690,-1483180807490254983>()) {
                           case 1154812576:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.dL = this.a.a().dz;
                     this.dM = 2;
                     switch ((int)com.yiyiaddon.m.b.a<"s1m98etasg4u5t","uVCSqmMjiii7UXHdB0iem4hLbFQjTZ7x9POXetXACg4=",-691889635073554455,-6331810991014859411,2206058086028560740,-4176599399013985446>()) {
                        case -776222403:
                           return;
                        default:
                           throw null;
                     }
                  case 2:
                     if (!var3.getSlot(0).getItem().is(Items.BOOK)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s37ewene0sxhn1","v6sbol4ft64p2KAyxC3lqtqdi0XNW8i2qnxYZsKqurY=",8318452700612272937,5798233399469476085,-8689574837226800848,5729371212577629834>()) {
                           case -979791660:
                              return;
                           default:
                              throw null;
                        }
                     }

                     int var5 = this.b.a(Items.LAPIS_LAZULI, 3);
                     if (var5 < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3mce8egijncsm","rCLqCYYYbEsSliW8skhL0wogaAQ5+P94lLOZNnlpZFY=",7807975300010637446,2032797000080430861,8187631430130898170,-6425051304820586369>()) {
                           case -1072112496:
                              this.a
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"sv7fsthbl5jwn","IelTYwmf61SQIjE0dOJ2CB2Ra9KK/SH8HERYF0D6yAU5H68lyo3ZpX30n9fxBwi8pcPnLAOon/EeacIwuaR/vfyyta0=",-9061583845629027102,2718288864315919549,5220931196475890475,4669209925382786450>()
                                 );
                              this.x.player.closeContainer();
                              this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
                              return;
                           default:
                              throw null;
                        }
                     }

                     int var6 = this.b.a(var3, var5);
                     this.x.gameMode.handleContainerInput(var4, var6, 0, ContainerInput.PICKUP, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 3;
                     switch ((int)com.yiyiaddon.m.b.a<"slma851r74afg","OnRvfgAKhDQOcUKvysfQ0ZXDg/6Lxx704PXqMDaVw8U=",-6060888492118298680,482850452456969461,-1449538950727475794,4556153537723489194>()) {
                        case 621959487:
                           return;
                        default:
                           throw null;
                     }
                  case 3:
                     if (!this.x.player.containerMenu.getCarried().is(Items.LAPIS_LAZULI)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1lrat6567u8s5","RL65A76lGZa21+V1KsMbA0m+/VbCr393TvMqcxTNG8U=",-7911504369746932149,6729322819891708720,-6519943343401456822,-5099574360951630135>()) {
                           case 1130434968:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.x.gameMode.handleContainerInput(var4, 1, 0, ContainerInput.PICKUP, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 4;
                     switch ((int)com.yiyiaddon.m.b.a<"s3bz2u982e1hyi","d3TL8SjKNxP2WdfRVbhc3LXq4QO4JKeLKdrWoKVH/tE=",-4972007846116022156,4615529254011154824,7743479495737655834,8567298596224390875>()) {
                        case -649213593:
                           return;
                        default:
                           throw null;
                     }
                  case 4:
                     if (!var3.getSlot(1).getItem().is(Items.LAPIS_LAZULI)) {
                        break label167;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2egrpg9x56lv2","58Reb4WdB8dBBPcXcTaUb/Xp4I0wmXuWOPJBzn5DJNI=",-3277129301046816833,-4280970224304087981,7521383307530049896,4500360585423509445>()) {
                        case 2068901508:
                           if (var3.getSlot(1).getItem().getCount() < 3) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1r56ouf6ykb5m","8RAPO3+T1TNsu0o9uNh/2J/3FSB9u8ySVui0fCbjE8Y=",6185099995729351979,8947238471704272158,-9073750296755344591,-7182975700863276044>()) {
                                 case 1700854676:
                                    break label167;
                                 default:
                                    throw null;
                              }
                           }

                           this.x.gameMode.handleInventoryButtonClick(var4, 2);
                           this.dL = this.a.a().dz;
                           this.dM = 5;
                           switch ((int)com.yiyiaddon.m.b.a<"s6ms7v4lpfjvf","TFyhDIRsWdyLc9MflFa0reDvuUrL/rupB5v/d2y54x8=",-1686825790828387538,286462000178603873,8853005798709062384,1369444168749282863>()) {
                              case 361603364:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  case 5:
                     if (!var3.getSlot(0).getItem().is(Items.ENCHANTED_BOOK)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1ta9zm4a4jtmw","Ud86zyGWp1FgX7n0uEtrN8fZULkQgALgu4TvrsJIzus=",-7066377329358285314,8509483187052816082,8214846225636953668,-2160837415431150667>()) {
                           case 1724369061:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.x.gameMode.handleContainerInput(var4, 0, 0, ContainerInput.QUICK_MOVE, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 6;
                     switch ((int)com.yiyiaddon.m.b.a<"s3r0zhkkurj3w4","5bf7343r2RAWIFOxTCuoR6s53v4NE9zwTXDgmPOkre0=",-7651086920443687276,-7114305689308226870,-4196811952877428451,-1356495555780912870>()) {
                        case 316158834:
                           return;
                        default:
                           throw null;
                     }
                  case 6:
                     this.x.player.closeContainer();
                     this.dL = this.a.a().dz;
                     if (this.a.a() == c.EXPERIENCE) {
                        label144:
                        switch ((int)com.yiyiaddon.m.b.a<"s3qdwsv880zutm","ay7l1ig2dycSUyjVTFmHAPdyqaBDSBgqZwAKqcG5bXU=",-6427668786944823218,-7079632900560005230,5031166054064787484,7762559876148746911>()) {
                           case 1435275707:
                              this.dK--;
                              switch ((int)com.yiyiaddon.m.b.a<"s1hgviki8o2o15","4JrFU73cH8Dbluh13i/Fj0XVIpenaoxiwyxjv+LISSc=",-972065755006996624,1345846976041343972,1318834387313181468,-3280053627024493349>()) {
                                 case -1714808853:
                                    break label144;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.a(com.yiyiaddon.e.g.c.a.CHECKING);
                     switch ((int)com.yiyiaddon.m.b.a<"s36yahfpcs7g99","fAkBjdXsUsl/IjkdCAnobxu3zk001rI+YvlOWeaUEgg=",7477417015438148414,-3546377937611093938,-3864593723835434810,-1071985621834677130>()) {
                        case 916337003:
                           break;
                        default:
                           throw null;
                     }
               }

               return;
            }

            this.a
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3nt87pjq0nyq3","PGmD3jIRxkU85/ytas2O6On+GOWYvnxTtjU0QTMcIjG2PdFHgIks5m1wn5igC/DF9qSAlZC98ul3ZUHHTan2pjWJLyp/VJntATFKzQ==",-7928780036193861446,4944956647449437589,-881782071335290267,527666440145069225>()
               );
            this.x.player.closeContainer();
            this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
         }
      }
   }

   private void bN() {
      this.dN = this.b.e(Items.ENCHANTED_BOOK);
      if (this.dN < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s63tvojia11sx","UwCJH0USjASmSPTLhAyLQxg+7fdVKKBoKWKzJ+sAeMk=",-8447611879137214845,-406992823474057098,-593995815579274742,817101004375060662>()) {
            case -1113890202:
               this.a(com.yiyiaddon.e.g.c.a.IDLE);
               return;
            default:
               throw null;
         }
      } else {
         ItemStack var1 = this.x.player.getInventory().getItem(this.dN);
         this.jn = null;
         List var2 = var1.getTooltipLines(TooltipContext.of(this.x.level), this.x.player, TooltipFlag.NORMAL);
         Iterator var3 = var2.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1dseuz5tpg64h","46TaMCKGT7b9mdGB1DswMriCV41Y+ZoFZiZMr7kP4Y0=",-7997929123398320889,-8903465900458851791,-5718854646097518482,-9157347464189100411>()) {
            case 1316434288:
               label82:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"srdkucrz1iv3e","9F2uX1fd6WKB+ZYFlpHPF6yVtrw5R6L/fJyH8BHInxo=",-5181420200515586126,7517408040175354031,-6053242891698934529,-7458422228000607155>()) {
                     case 1172875422:
                        Component var4 = (Component)var3.next();
                        String var5 = var4.getString();
                        Iterator var6 = this.az.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"sudkz0g7g42jo","rElFVSertcao21hl/ZCl9+dnjmeihstC7slV+GtKiIQ=",628062032710529913,1815400106846748991,-3179999432199530297,-3653155003619139038>()) {
                           case 2048122636:
                              label79:
                              while (var6.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1nv13dcu5veim","2EFTSm/wI/mJ6F6e6zQQh27tvWjgRJujRQfDzEHp+8I=",-279211142627023440,6968200798812490106,7151859429382138639,3468933915379291034>()) {
                                    case -1877109782:
                                       String var7 = (String)var6.next();
                                       if (this.b(var5, var7)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2by7hwhf4y3vj","cpv/FOE5ufemnLH6C0SXaX5Rlw27FY9Shg2t069N4Mw=",-8458784889095663482,3840249935138114477,-3970334437142503571,4724617364951863802>()) {
                                             case -1802741450:
                                                this.jn = var7;
                                                switch ((int)com.yiyiaddon.m.b.a<"s1migoacak2lex","CwHT/D2qpyS2oca2LDm7uMfo0B9qyGqH7067xHhX7tw=",5327135489327925382,3103695160421719541,-3823073438768359448,3241054541097927759>()) {
                                                   case 1504034433:
                                                      break label79;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2dblzefsn111c","WAyt+hW4datnAKkTaMcsy653TE9iN3n3jUPnl1CJr0c=",456378727911313728,2176665438174886854,557956968479576517,-7895842727254391378>()) {
                                          case -428685245:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.jn != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1j4rikwh9szrj","CMv2KaO1MfEQLXB1K041Dx47mmISc9yLVFndrpKaawU=",-7485948988228968482,-5611167025644082524,5220446596808398053,-7305119883060246679>()) {
                                    case 1828477156:
                                       switch ((int)com.yiyiaddon.m.b.a<"sxdb4vkq5s3td","qbr/ZaIeJo9g3HVwbFtVJHQ+3gcPTHcmWsZVg9/ONZE=",-336803550702703992,-127744120009304969,-5895610138256573689,-2520267110384843519>()) {
                                          case 511344097:
                                             break label82;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3rdtgwvmfhgzg","ox3CCcTDL9azYAOer1zGIDla7ZkVk6ySJPKz5p82m/Q=",4373201255063546578,1673511854765748791,-4741987389225668775,-2086939486699584528>()) {
                                 case 1891188222:
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

               if (this.jn != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1rmwknl79l98y","2EGpCTv449peRcHr5SQWec3cW8cXGm1V8hmmPW0bVGY=",6271537077532239259,2283619188458998130,829135840471652367,351460549683934655>()) {
                     case 1197460394:
                        this.cs();
                        this.a.K(i(this.jn) + "");
                        this.a(com.yiyiaddon.e.g.c.a.WALK_TO_STORE);
                        switch ((int)com.yiyiaddon.m.b.a<"s1dly79wh3fmai","pkAZbu3BVVFJpaf03rvnBBO60bWeotUvN+4an1BT7UY=",1712997795251923182,-1980017659994564684,-2031161069798028003,-6141447692242822618>()) {
                           case -556474224:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.a(com.yiyiaddon.e.g.c.a.WALK_TO_GRIND);
                  switch ((int)com.yiyiaddon.m.b.a<"stnswczipozj2","D6jVFfMHpGBSvkFP9kYio4qklc49e523KX3WlGeG4X8=",7049848828056989078,6071416747504802913,-90633843534623022,-7122980598947571425>()) {
                     case -237580401:
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

   private boolean b(String var1, String var2) {
      String var3 = S(var1);
      String var4 = S(var2);
      if (var3.equals(var4)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e2ktxzog0ogc","fiJf7VNYLXswKidQYXHsvKXuwuQOv1qi6H3gF3XgykM=",5105299731937057987,6385034281576147206,-6285180367543966248,-6428670786517666903>()) {
            case -1109580559:
               return true;
            default:
               throw null;
         }
      } else {
         String var5;
         String var7;
         String var8;
         label108: {
            var5 = var4.replace(
               (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
               (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
            );
            int var6 = var4.lastIndexOf(32);
            if (var6 > 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s15b6kihc2ip2a","Hbd534SrO1jbBmeUfqQR3MXKT+IROgHD8Y0WTcUoC+c=",8577376155018284057,4824926960457026711,3633721485208148602,-4349960287845648925>()) {
                  case 1274738657:
                     if (var6 < var4.length() - 1) {
                        switch ((int)com.yiyiaddon.m.b.a<"sc7dtwpc6m0gd","C6EaQDIOGQDd6QMZHB3Ta4hoBnYyY2oTyEpwEgUjiug=",-601528347124950317,-1909160605768113230,6683183469137274298,4319062610245595563>()) {
                           case 512494780:
                              var7 = var4.substring(0, var6);
                              var8 = var4.substring(var6 + 1);
                              switch ((int)com.yiyiaddon.m.b.a<"s3h1ab2jlp0il7","fzacNuRCQKovkqisXoxXwMwW16V0QA1ylBwWHi+22/g=",8761063648027422227,-1996170641296653533,8700058192882775788,702888584794835255>()) {
                                 case 2133986040:
                                    break label108;
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

            if (!var5.matches(
               (String)com.yiyiaddon.m.b.a<"s276dzyhlwnvp6","HHo7pPJ1I1Fj811MICbjSJvGjxeKpWsFPxKQdUQHfI4dL1dwTDA=",4456879181481549295,7417723285901530690,5079447129796857548,-4455122836875887948>()
            )) {
               return var3.replace(
                     (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                     (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                  )
                  .contains(var5);
            }

            label85:
            switch ((int)com.yiyiaddon.m.b.a<"s3v4jpmknvimns","L3GQnUzn+2WFlIOzzVWu3Bl/NqhFF5itTtblaUoYYnw=",-4342349382302317588,4374986958878059514,680555116380764749,6336939901554982408>()) {
               case 1475424460:
                  int var9 = var5.length();
                  switch ((int)com.yiyiaddon.m.b.a<"swtlpxt4gzreu","mWe8fEUsm8NbKRvVrgBLHFFbLoP24qIeibIr75dgQZw=",4986153365819033966,2729534344372398740,-2609964571197046401,-5451051572440695433>()) {
                     case -1574058525:
                        label97:
                        while (var9 > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2uslz3x4slcj8","P7ugKAbM2aFlMQ7i3k1Nan9iQkFH4KspEkS2WaP/LUA=",-5651504382381367148,-5269697204940802816,-7721846211802874338,6061414445388236692>()) {
                              case -1051250497:
                                 if (!Character.isDigit(var5.charAt(var9 - 1))) {
                                    break label97;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2n7t01qvo57cq","bTyGrLANCa2Jql1LtVidAuK/PhUcNoPvBGYkpNcspIY=",2273372038185195089,-1039795703804067082,1778215336406403118,3372479581248702536>()) {
                                    case 1199811209:
                                       var9--;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1krumitis52h5","c7DSd02pFKH8Pq5TmqpN/0IrM08vHgp8Ty38JW1vuIM=",-6263973846628598977,4317150711209046160,-4686806018804490792,-7678017973161020456>()) {
                                          case -984985696:
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

                        var7 = var5.substring(0, var9);
                        var8 = var5.substring(var9);
                        switch ((int)com.yiyiaddon.m.b.a<"s1pp2wji8nhsjq","MpfBJT4LHKjJPY6gkfCofpH1cl4fkUwwtuptuf/HjCQ=",-5776481777393048268,-8633579568399210551,71297360549341421,-8124265323838803968>()) {
                           case -1950259202:
                              break label85;
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

         if (var8.matches(
            (String)com.yiyiaddon.m.b.a<"sjmq3jl9t2kyh","Iep7p6s1FxhDSVnGAr16GGd8efA2Fc9k+0G8hViATesWBQ==",-1148324097946437127,-8567554459109617175,-3230814727686996195,-4401179080877099811>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s3480vyyzatkwp","xO9lckIwodoY1ovFc922WKj1dynrEvn62YwqzhVk+nE=",6838011219571888330,2323735508600777861,3441754606395484353,-7263430241308299710>()) {
               case -1751946707:
                  if (!var7.isEmpty()) {
                     int var13 = var3.indexOf(var7);
                     if (var13 < 0) {
                        label63:
                        switch ((int)com.yiyiaddon.m.b.a<"s1xsx7nbvb1eaw","h9Ox/C/lcSDgiuuDjVNHFpj3V6mb8JBSWZ/+hgvQPBc=",8341882865534757799,-3353907587336355642,-786510873421790371,2414986172572139581>()) {
                           case -1926094712:
                              var13 = var3.replace(
                                    (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                                    (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                                 )
                                 .indexOf(
                                    var7.replace(
                                       (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                                       (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                                    )
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"sww9bmj0cu7xw","B6VPKoH4XbOykZQ/0MNCI7HEF1kefIhtryw0W7V02nU=",3883354846822533204,-8370882465906280444,-8627315171492049731,-841671497522384242>()) {
                                 case -60673172:
                                    break label63;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (var13 < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1qpiy5xqjfgud","7VwmEXNY7o1Hwa+Dez5lu1QmdlUboaDHFuI6L33Rz0o=",-6785966818965061089,-8888893198845166893,-8930247005306298050,3160657266314910836>()) {
                           case 1297671950:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        String var10 = var3.replace(
                           (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                           (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                        );
                        int var11 = var10.indexOf(
                           var7.replace(
                              (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                              (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                           )
                        );
                        if (var11 < 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3g8p3648zptox","A5c4w1Z+/TSX3U/FRoun1i6s1lKPf4ocQ9bhBYb3HiU=",705210592661278306,-1645358832097106910,-2161930144271315093,3112598777413778082>()) {
                              case -93131965:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        String var12 = var10.substring(
                              var11
                                 + var7.replace(
                                       (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                                       (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                                    )
                                    .length()
                           )
                           .trim();
                        return var12.matches(var8 + "");
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2oeckl3uuy3uy","x1RWxmBn3kYDX4fg7q3ZLMFJJ1tzcDo2GI3n0X8djJY=",-707217115542603779,-476719622094268742,6247503408178545473,1481835223213672289>()) {
                        case 1387776092:
                           return var3.replace(
                                 (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                                 (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
                              )
                              .contains(var5);
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return var3.replace(
                  (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>(),
                  (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>()
               )
               .contains(var5);
         }
      }
   }

   private static String S(String var0) {
      return var0.replace('　', ' ')
         .replace(
            (String)com.yiyiaddon.m.b.a<"s2avcptriqwsa7","t61DcQ/Q9tsxAoezrkYRD63miDq/tdFQHNwImph+",1345551495621907523,2142042060419754543,3021229240169151497,-7840337160379606723>(),
            (String)com.yiyiaddon.m.b.a<"s1e19y6n7iqk2t","eSkwy9cg5qmZlJ021pR/yWKGFvYE2IGkM9X5+BUtF/Y=",-5609934462290796970,-4572251784651716596,-8422198146012510146,-2894382487473090192>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s8ne33s4pku2h","R1MMH5aok1OZotkTAxO18/2Mu6CjP9NZ3jFvrvxx",-1312229397595431132,687313932350530871,4514306947537780588,-2786631430948557050>(),
            (String)com.yiyiaddon.m.b.a<"s34x2uh7psxk79","prwO2A15mqH6SxcunfimSpFjLpetFBj3ifS/zc4X",6221200539151193368,-2487258075947468052,-3483227920304264962,-3625804337945184798>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s2est971gzjbmv","PSkuVxZb/zg48crtKkd/B1pZBiJ9XaCRAIdMmbLo",2636513596407007048,587652116951390401,-8544987194408452014,-1058745411834440374>(),
            (String)com.yiyiaddon.m.b.a<"sptu1d2jnod3z","JrhqK0gmeaR07HM+GyzRug3MPMKrylqnxT5TymSB",1703709047224901795,8638155032441603869,6000259987906474257,-3038720175321643002>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s2h2pwzhdy5wzl","ZESEmPa7qyfS/sC/BhBjsr2AeaQygcufWUJiUtHO",6653105155925205252,-5882443115326633767,8160773293863650177,-3846745648174454333>(),
            (String)com.yiyiaddon.m.b.a<"s1yjy3evfp525o","WY7DHrON/fhyw5wgpUgniuEFm8WhT0sMXi1jaaWY",-8531406924039130102,308709196377743440,-7835973396891651557,-4981154763459406284>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"se2s8u4udpmwr","z8hAtOPBE/rX1OFs4g6T8Ml5HQI31611kIJZF/hA",2982986760989719427,3185812673403138969,6203525677516373860,-2742296235754920622>(),
            (String)com.yiyiaddon.m.b.a<"s3sqgsuei0c51u","MhnBft0C0q1wBoS0vD0aP/hOklHGWTBSyExFUGVo",885458891466137724,-1133446140890362661,-6832333456994578169,1006142356806080679>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"sh4o6m19njhuw","9oNN9HUcttIt4+67JCFRi+78vNu8WSpcXg0I9FP3",5101450418095512600,7170384588642804099,-6510135002068889114,6241205210132744009>(),
            (String)com.yiyiaddon.m.b.a<"s30rtgfbr0aydr","jG1hiCMPLquUS7aBIle9PZCpMjxlyh4VCPUnxwaG",5940688201184657191,5762753397947594945,-9068066085248640609,-7798798597228756557>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s20nebm8gpmx5","P2LAuWyhUcd4ur3GvEeAOMJDJdLiR9DIaeB/8HUn",233618639445027564,4224922850160221482,156085172796326204,-4715531922227815491>(),
            (String)com.yiyiaddon.m.b.a<"s28opbwis62y07","WrvdXB4BcC82GfJ+1Zjp88vZk45b1Tz8HpG1iCk8",19214139443470036,5895235129853433314,-4130340987045081121,-2644415549945646012>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s1cwqgwodedb7t","8kWx4IjAGnPdblR0PILR1vTYm4LgHvEbewr269d6",5383815770221160719,-3728118334789631038,7886285983055075872,2170839036783560888>(),
            (String)com.yiyiaddon.m.b.a<"s1aejrst4mhc12","vJOzw8tgtRDgtl0d6MmcwZ6OB9OG+lgpKXJ/kKp/",8911524960651327252,3223948436653117990,-9091659483580461920,-8458983631746143642>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s6nxta9c6djey","wNfl66V6YKLaxBm4yTtR9ML8n4q1A30Aq2aBU13p",4768877815616616532,-9201654184667293197,-6627114832570104989,2240101658093933792>(),
            (String)com.yiyiaddon.m.b.a<"s1z929uw9wptsc","tGFmsJt8V89oGaGv7IVt5ainqM8x+6gre2RGvrU8",-5292913721100984186,-5489002159353913291,2524157731188798882,6278126348201482835>()
         )
         .replace(
            (String)com.yiyiaddon.m.b.a<"s2y2n4ayr3ydr8","YJZicL0lSGIZZKTk1GrW8j27e5+iYbDrqNz2P8cH",-7481489427471633175,-926743081129228778,9179420744279897401,5312143962180000646>(),
            (String)com.yiyiaddon.m.b.a<"stclvujkqwv9z","8Zg//7tdRx8cCRKIDEFhvdA9fiIjAIYI1uoqFepl",-8043711657930086873,6144799541006809028,1192965760405055345,-7067303662689247448>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s1eh5wikt3p08p","Wh7TJx5DS2vbVRB+/oprTWgwpBDWKRqyHpaEJ9eaAIBLgHQzBotNyU2NwvPzdDbVqo6rQGYb8i8Z8imjnSyfjD7ww7e34OZ8ES/MYl9nDpUQz2dTeDA=",-2579305789227958636,2494286076344726458,311591428720891712,-8205113773787705869>(),
            (String)com.yiyiaddon.m.b.a<"sptu1d2jnod3z","JrhqK0gmeaR07HM+GyzRug3MPMKrylqnxT5TymSB",1703709047224901795,8638155032441603869,6000259987906474257,-3038720175321643002>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s24vvupqmjwzg6","RtzTvl47tpbvIYYBhAo779G2GYS5ooaVUwgiVdnAM7tsWHpFTEngQu/DT8U89mC4kFMiHVuTFPOoU3LlRV1V8GrE8IZgDl0pLHM6DfuphMEFR4QO",1075839037152163675,-3995430733014135997,3071761044632641529,7219252191537002568>(),
            (String)com.yiyiaddon.m.b.a<"s1yjy3evfp525o","WY7DHrON/fhyw5wgpUgniuEFm8WhT0sMXi1jaaWY",-8531406924039130102,308709196377743440,-7835973396891651557,-4981154763459406284>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s3c6or4zmqvhpl","Y+Ea4hswd+utcmE0f5ly+5kqSFZxXQ8gGcjSybcip0xEA+hftVOGqlJjcD8sl5eNwMNblctxOpBHRB5tIiorER+GucFK/tRtqo72DoJXN/0zcQ==",-1408996412712040558,8773129455268119199,-2523840498737436596,-5183640144583509102>(),
            (String)com.yiyiaddon.m.b.a<"s3sqgsuei0c51u","MhnBft0C0q1wBoS0vD0aP/hOklHGWTBSyExFUGVo",885458891466137724,-1133446140890362661,-6832333456994578169,1006142356806080679>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s2uhvyet11gnlu","U5cuZxghdGxiOQlbrm2X3D81JIIxmNUDjUYlVKAkJ6CLqHHFJU7q9K2k6VqtXONknh1fJVxuLr4jx75eb88II5ZYD8VWmCLntWHK5boKi1+aXQ==",-6679010169753570035,7715998418877611355,-2204985355410609469,-5125542450830608598>(),
            (String)com.yiyiaddon.m.b.a<"s28opbwis62y07","WrvdXB4BcC82GfJ+1Zjp88vZk45b1Tz8HpG1iCk8",19214139443470036,5895235129853433314,-4130340987045081121,-2644415549945646012>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s2rbgjf6sk097u","BZAEnvAebf0iwHubP2Yn+v87h/CXTUc8mmb+DXqaO78qE7+ueK3tZWLmFAhVu/tv5tU3mIRq2X7kmi9jUt2hFvdKZfd+t6CGGmOSTe27Cc+eow==",7777944591277146948,8741328780045868655,-3873313652753853144,9086030026798272428>(),
            (String)com.yiyiaddon.m.b.a<"s34x2uh7psxk79","prwO2A15mqH6SxcunfimSpFjLpetFBj3ifS/zc4X",6221200539151193368,-2487258075947468052,-3483227920304264962,-3625804337945184798>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s1mh5wsge9c8jq","aZa/OnsCMjJS65x8+TAIl1Q+/qwBCVeU8a2cwE3cFJ4mDIND5/g2UrpzekWXfYg3AAkBGR/guFWMocvI8RUXGRDj9VkIhq015Uzy2ktSecc=",-8828702585656779474,-6375822524695983935,8602615892081372328,8523841247804604554>(),
            (String)com.yiyiaddon.m.b.a<"s30rtgfbr0aydr","jG1hiCMPLquUS7aBIle9PZCpMjxlyh4VCPUnxwaG",5940688201184657191,5762753397947594945,-9068066085248640609,-7798798597228756557>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"st3x49h442fqt","rJ4BAIJalrOkZYwmsi6/iGvZdQ5gkT8yxiqfCaReF2NXSSypxxf6mIfBb5nmkofqnIobdDTfXGosqDkYOKL//evSgpmWNerTgxdiDfxNOkfYfWH4",8345539470447259481,3136180171916980239,327963439529221633,6938585300394851840>(),
            (String)com.yiyiaddon.m.b.a<"s1aejrst4mhc12","vJOzw8tgtRDgtl0d6MmcwZ6OB9OG+lgpKXJ/kKp/",8911524960651327252,3223948436653117990,-9091659483580461920,-8458983631746143642>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s1i9348rfak7f7","oNYyd/wwAWFIlYL/UdtK5l5+eDeHNgYggmK0KwuM51HSrVJLBwX8pe52tpJ0Ly2/rue4BvgYThZF9uOk9ThoxzL0tg6m/9Zh7/1WKC409Zh3yQ==",586404412311655357,-52293784664608923,6560097721586008886,2311525938744253508>(),
            (String)com.yiyiaddon.m.b.a<"s1z929uw9wptsc","tGFmsJt8V89oGaGv7IVt5ainqM8x+6gre2RGvrU8",-5292913721100984186,-5489002159353913291,2524157731188798882,6278126348201482835>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s1u369gzv5073z","JhtiFeIwyM/gwNM8MMNgRwXDFrBs0ha1X13iZKcQhTDqSkMutQWsLXTTznxvelKiO5fqPnZ8sw3t/hxrYTKW+UdQPe4q/UF8mEeS7tXvNu4=",-5577661455695746365,2627750604225722754,-5954054847243567475,5016481084399923951>(),
            (String)com.yiyiaddon.m.b.a<"stclvujkqwv9z","8Zg//7tdRx8cCRKIDEFhvdA9fiIjAIYI1uoqFepl",-8043711657930086873,6144799541006809028,1192965760405055345,-7067303662689247448>()
         )
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s1radp2fh3j05w","MWIvogWn1NS0LTNLxAkBTzQOI50YRkjLPxo52FmWhJaYag==",-6426907839545473201,-4754116874134880192,7023937140662187238,-8942392537525068637>(),
            (String)com.yiyiaddon.m.b.a<"s3cmavflds71ni","rRe7Uf9qlUozkViG5ycWhSYdY55LINL05iQ8z/ze",-1453274276147902440,6315627805471290064,-6793303873905302157,4630386897910385347>()
         )
         .trim();
   }

   private void bO() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.GRINDSTONE);
      if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3q5n8c591ef9h","3OlKwUViQlY1Kwep8YlmBPlTrb0LYJuwHKnjl6veJ/8=",-6891729058527292025,7951109885091083695,-2898683857140166397,-1441797637669450498>()) {
            case -1313025314:
               this.b.ag();
               this.dL = 0;
               this.dM = 0;
               this.a(com.yiyiaddon.e.g.c.a.GRINDING);
               switch ((int)com.yiyiaddon.m.b.a<"s3oqdnlkt4ev26","1bHUhrJVopXLqK1rAUt2P7jVhlFzVJBp8mB475Heq9Y=",-1111950315447893249,5536855652492792803,3238814710394757950,8369268263791836379>()) {
                  case 442125656:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1mmhtc2lbogdk","LCS3gEwua4yVUoL8/0a21Ounu8h0qHvqxB0vo/G0SXg=",-8346392080117049610,-4350662304234318574,-7182886237648261473,-2373065953766322749>()) {
            case -757231160:
               return;
            default:
               throw null;
         }
      }
   }

   private void bP() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s15zk66v6k3pj1","VrGAvh45Np7rNv/ZGO6uyx8K58PYFqbV0qDqiGIMlUU=",-8635256774231229707,5522428805717072709,-4238143803355377345,3105570246450878034>()) {
            case 1575644631:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         boolean var1 = this.b.aW();
         BlockPos var2 = this.a(com.yiyiaddon.e.g.e.b.GRINDSTONE);
         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2i0pvca3ntndm","E/lTFSo0OzXQqHILk8v4YpkuIL8OwfM3g5SmKvuh6qY=",7963159743949160969,-115270516983429429,4805873724881219887,-1638794644025561989>()) {
               case -131161704:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s999hvyz0bppg","3Ffb4SGa0r6hv2EZZdABtq0iCwAF7ODimF2ibyTRgQ0=",-5991692974941647408,-6338748538751709050,3111574183850795110,8731185524113050355>()) {
                        case -750721852:
                           this.b.l(var2);
                           this.dM = 20;
                           this.dL = 40;
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (this.dM == 20) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2kj14cydmxywm","OzXOGDz95cOfcm4kRTIM3/4CqXw+VYbwofFcaCxlpss=",-5160232044586789305,2051442385823597712,-2247710406260250882,3461485565014678625>()) {
                        case -111803746:
                           if (this.dL == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1l8gzklv9zq7o","cqcMnIXoBFz/GgogzL8TxqP7VrnqE5UDLbkFLZMSlmo=",-9166173812029169312,8175902581592461455,7192629160137697455,7780540843793223118>()) {
                                 case 1028702580:
                                    this.dM = 0;
                                    this.dL = this.a.a().dz;
                                    switch ((int)com.yiyiaddon.m.b.a<"sg0h5ig1j91qe","bongeVpovVHw+Kfm8EOr+a9UcCFM9aAH6037UTFbbK0=",-7426073781450906067,-2831292955830359135,5074629733595965708,5938987248213036493>()) {
                                       case -1009293352:
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

         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1tfbii6rf863","zjH6fH27SUOI4m7YBecb/bME5wfa3VbS/6nH+4pYmgc=",5791770205262677316,5315651671040539382,-4206378220266499832,-3543136968517400259>()) {
               case 529061336:
                  return;
               default:
                  throw null;
            }
         } else {
            label98: {
               GrindstoneMenu var3 = (GrindstoneMenu)this.x.player.containerMenu;
               int var4 = var3.containerId;
               switch (this.dM) {
                  case 0:
                     if (this.dN < 0) {
                        break label98;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3dxgveib7fz01","5NdlV7ZOwzFSJyN8vuuxBpNwWsrA2WLlF79vB+mZT/g=",-9175846164355700751,185957171811291888,888093000683919271,-1045406186087340690>()) {
                        case 443691359:
                           if (this.x.player.getInventory().getItem(this.dN).getItem() != Items.ENCHANTED_BOOK) {
                              switch ((int)com.yiyiaddon.m.b.a<"shgy5v61j3qq7","BXjOZ/BPn1Jc1TqYUNtrjmsMpFnfwYsPXVx0pleEm4w=",8892098638490359758,3732100852327474628,-5960176412693337644,-7419242950121034160>()) {
                                 case -1402473904:
                                    break label98;
                                 default:
                                    throw null;
                              }
                           }

                           int var6 = this.b.a(var3, this.dN);
                           this.x.gameMode.handleContainerInput(var4, var6, 0, ContainerInput.PICKUP, this.x.player);
                           this.x.gameMode.handleContainerInput(var4, 0, 0, ContainerInput.PICKUP, this.x.player);
                           this.dL = this.a.a().dz;
                           this.dM = 1;
                           switch ((int)com.yiyiaddon.m.b.a<"s1zzrvil4wwowe","vioA8LP+reoKQ/OP/Tm76nS1mR4mZwZI6Pqo8E0LG9o=",885093233013716423,1664217567595534115,-8572105193548720606,4443013517994394472>()) {
                              case -1540397180:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  case 1:
                     ItemStack var5 = var3.getSlot(2).getItem();
                     if (!var5.is(Items.BOOK)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2oirvuvi3yfw2","cIsU8jxw25pK/C3L0RiTwiOJFKyKl6bWIkva0EZjUnM=",6707972974607006758,7811377509740213001,-1990204466078459137,3776559801702053917>()) {
                           case -459722144:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.x.gameMode.handleContainerInput(var4, 2, 0, ContainerInput.QUICK_MOVE, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 2;
                     switch ((int)com.yiyiaddon.m.b.a<"s1d2wsai2axvvn","eX6zsnih3aGoJs7w6/txuUp7FVRJffGsxzR+pIHqeo0=",-842311909723434577,240235308987046671,4303297994161395365,4491909490249534972>()) {
                        case 1312074482:
                           return;
                        default:
                           throw null;
                     }
                  case 2:
                     if (this.b.a(var3, var4)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2gc5abfc100jr","zSqh5UJ6hO6R/QAZi2miCYNnq6XILjcUek6yDB7M66Y=",3744619594747446357,7956648283548660470,18147696847321173,2599587096391533169>()) {
                           case 1443060506:
                              this.dL = this.a.a().dz;
                              switch ((int)com.yiyiaddon.m.b.a<"s3uprsb28ms5v5","6syBTVypQ2SBuNkuopR0EHqQovzjgYEYhAg7prx7EBQ=",-2971728825849332091,9181017921767601760,-5863045546679546951,-6228792918369139178>()) {
                                 case 2038804577:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        this.b.cC();
                        this.dL = this.a.a().dz;
                        this.dM = 3;
                        switch ((int)com.yiyiaddon.m.b.a<"s3pdgmcq1a0v5a","8ddGiUfdHNsKxmSiosZWJgbk8aeQZal7tDv0Qj1m92Y=",8362567360300763070,7557917649715566625,2866101229035795991,-7374399771586933506>()) {
                           case -21754153:
                              return;
                           default:
                              throw null;
                        }
                     }
                  case 3:
                     this.x.player.closeContainer();
                     this.dL = this.a.a().dz;
                     this.a(com.yiyiaddon.e.g.c.a.IDLE);
                     switch ((int)com.yiyiaddon.m.b.a<"segk6k4baniaf","qAGScJ7OxZjW8FthTUIhxk8crEWDAFUmWRZEz6zKLW8=",-5240029920067487196,706565161989003752,330267613952350555,-8279714439687598130>()) {
                        case -1392395725:
                           break;
                        default:
                           throw null;
                     }
               }

               return;
            }

            this.a(com.yiyiaddon.e.g.c.a.IDLE);
         }
      }
   }

   private void bQ() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.OUTPUT_STORAGE);
      if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s90pzgwge3zyy","pZydY/yUn3zC7JS2iCeM9nS5JkCpXPkbAikcJKmsE4o=",-856409258626075734,-3440496640708343339,-1601018014033885345,-7459511445159198527>()) {
            case 811814426:
               this.b.ag();
               this.dL = 0;
               this.dM = 0;
               this.a(com.yiyiaddon.e.g.c.a.STORING);
               switch ((int)com.yiyiaddon.m.b.a<"s1ywsaw0wi95db","9rnX3DCcegH0BPTxKcl8gV7pf4ScxRlcSVX9QYgeFME=",55104715191728456,-383558109695855477,1654488669392954833,8215929679685211257>()) {
                  case 26154958:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s2oyfou53m2lsl","IYbE6n3t668oy24h7xOv5pXmqCO85cXXMjBWToFRRvo=",-5714452846787560286,6580864573826416384,986851630819537390,-1941474524025396779>()) {
            case -1199071524:
               return;
            default:
               throw null;
         }
      }
   }

   private void bR() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qtnxaqbui0un","mjchUVNM98BItr/xmos5gH5cpHScTHKKj8HMnbD9gS8=",5711143965779586575,495855915670948989,2901697227642219702,6768169458532214648>()) {
            case 1069760003:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.OUTPUT_STORAGE);
         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2lxnhscjpcbgr","nVG72NjYy1qjXaenL0AuRoNym3VykunHsuwZZSsEPNI=",3154875011539238989,-3418327124732168436,-3001678700581435215,2251055985786063977>()) {
               case -2048324490:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s32m8ng25pynu5","o9BUIDyjnzGhFcNaxgiF8FF5qz0bM9JAb6Y9/eunmk0=",4919810854659879510,8473034020892728395,2214724658470720240,6833604149444738884>()) {
                        case 826215276:
                           this.b.l(var1);
                           this.dL = this.a.a().dz;
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

         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2nb42qx27k7rz","maRo5Boju8qonS1x47G+Dypog6xwSXmebwc+NnXetlw=",-5311359475233940862,4002058128332918110,-8540508583573401271,5725335729605452604>()) {
               case 28333517:
                  return;
               default:
                  throw null;
            }
         } else {
            label126: {
               ChestMenu var2 = (ChestMenu)this.x.player.containerMenu;
               int var3 = var2.containerId;
               switch (this.dM) {
                  case 0:
                     boolean var4 = false;
                     int var5 = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s1f75brlzbz8sb","9emtn+I/WetNOOjQD/xwELNPSluRXYF3FoHwcUdrkLc=",-1223493294217815250,-8482703288930319202,-652368244719281277,7446076161946006019>()) {
                        case -1022724219:
                           while (true) {
                              if (var5 < var2.getRowCount() * 9) {
                                 label77:
                                 switch ((int)com.yiyiaddon.m.b.a<"s289ncwkvv09dt","CSMra4rFtrWNOlhyphE1M5tYmlxf/DNeQpFXHfZFtko=",535050244713985745,3379054408829611232,-6008267951552601577,-2384595980097044730>()) {
                                    case 470707610:
                                       if (!var2.getSlot(var5).getItem().isEmpty()) {
                                          var5++;
                                          switch ((int)com.yiyiaddon.m.b.a<"so1e4wygbsrjc","hz+6QHAm2OnBPoWUFANF73sN4Yrhyr+O54evvs9wO5A=",-7007307067237475691,7966232382770907828,3039705780579663721,2516884837284606913>()) {
                                             case 932553508:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2pwwnyyr5o7y2","nXHzyDbaN4ix7RYtD1SyvNQu0rpzcWjtgEDZy66KQZE=",2166759118009254602,8545299512458826210,-2762720218929177578,260231912397896937>()) {
                                          case -350106533:
                                             var4 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"s3s9gphwkl8lq3","ZbhBFkA9PtzllH9xQhzxgVSNcrgOtnx4Oox86VZvKmc=",-6654433569246977736,-8464125932781886735,1856373254071001591,5795209766031415761>()) {
                                                case -559134467:
                                                   break label77;
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

                              if (!var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sh12fl40msac","yzB4lrE6Nr6zeT4U+KsSl9vxiw6QVGGvKYa7qx009V0=",3673052128426709961,2115043018951212267,1888143293550231792,4701834681279340287>()) {
                                    case -714577958:
                                       this.x.player.closeContainer();
                                       this.a
                                          .b(
                                             (String)com.yiyiaddon.m.b.a<"s13gcqgv13dx3g","KnjDR0ZcE19vgnAq4nYXRM+7JvjaObQdSvHARfbzAW8/Cw4Gudw3vyEwFucsxqkIKg0=",-8716863081087483859,-1414418176220631666,-2982666693880560017,7271088838218422427>()
                                          );
                                       this.bF();
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.dN < 0) {
                                 break label126;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sqvd2e1wpjldu","hD2P7Wg5LbG3Bq8fKX2pVj3teEspskyVo5y/n6V7ZvE=",-2383474999936514127,2146366415305822391,-1522468866564571976,-7383018724909988547>()) {
                                 case 620153468:
                                    if (this.x.player.getInventory().getItem(this.dN).getItem() != Items.ENCHANTED_BOOK) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3fv6ba06137co","HOA/6v4NK5MZGz7a+IqOnQ4eSLJNVZTyISoWRM2Ug6o=",4240191934024928087,-2243072942495595967,813984687737513809,-7058014707848289765>()) {
                                          case 1343398252:
                                             break label126;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.x.gameMode.handleContainerInput(var3, this.b.a(var2, this.dN), 0, ContainerInput.QUICK_MOVE, this.x.player);
                                    this.dL = this.a.a().dz;
                                    this.dM = 1;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3efgrx4gmdxyv","2HbTPaEmDL3vbFW4HjjzAIirZjX97NLx7fxXgmTTKy8=",-1942228057019914575,558335938621873106,2580583910429588343,2561653896189557580>()) {
                                       case -569589672:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  case 1:
                     this.x.player.closeContainer();
                     if (this.jn != null) {
                        label85:
                        switch ((int)com.yiyiaddon.m.b.a<"s1lme7z3236lmy","x4BwtYOklwT/mXcL1lIqVCzT9qrAmoQVTobn595NWNQ=",1107388338236923882,4061806316468902388,2265585110376778723,3285747658680030819>()) {
                           case -743451774:
                              this.az.remove(this.jn);
                              this.a.K(this.jn + this.az.size());
                              this.jn = null;
                              switch ((int)com.yiyiaddon.m.b.a<"s3n76rezzccvxr","yZqgPMLo/rYkdAYKM6+Ezp/Hel0kOM0+3WNh9Zr7pJ8=",-4965658669014286574,-1205641805772035309,-2366226472315721389,4361127306070831182>()) {
                                 case -1073234673:
                                    break label85;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (this.az.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s21j5atcnqunyx","HPZlPXoXvzwZLjBOeVEA1uOukrhhoUVnT5+p1CgoFao=",7016531822071075057,-6008345914458555536,-6492889444036563842,1185633912610764091>()) {
                           case 1045244774:
                              this.a
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s1au3vlbvj3hxn","yDzZlJYjWBHiFAzcfyP+MJ1gzpJf5CL7Bafkkf02OUTuRETXEPmCZxPSI2N+Ro+Bunez76lMnqgRdg==",8770157656252688368,8156853791905362858,-5944130926406839866,1592309192083796984>()
                                 );
                              this.bF();
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.a(com.yiyiaddon.e.g.c.a.IDLE);
                     switch ((int)com.yiyiaddon.m.b.a<"s1po7qmn5sqlxq","c+RP58qhGE2ysvTEpe8IRXR5MkWsfggQrpwR2wBVsu4=",293960875396260666,7562676229909786792,-1231098210233365296,4077522007041266326>()) {
                        case 1732680173:
                           break;
                        default:
                           throw null;
                     }
               }

               return;
            }

            this.x.player.closeContainer();
            this.a(com.yiyiaddon.e.g.c.a.IDLE);
         }
      }
   }

   private void bS() {
      if (this.aB()) {
         switch ((int)com.yiyiaddon.m.b.a<"s18pfbsrdq9k1b","1lZIthsFlc6ua7NcEwUvqgVSGYP3QGk0sHo0ogPeOMI=",-1155507472108165127,-628575533831469933,1852341069106592106,-5720411129462736273>()) {
            case 405576160:
               BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.BOOK_STORAGE);
               if (this.b.m(var1)) {
                  label46:
                  switch ((int)com.yiyiaddon.m.b.a<"s1hsvs0alvvedt","0bptrVchbhaSD+8FenZnIrH6IEwSIcrXUlhBg5SHW1s=",-5756765162240517834,-4205699996763856844,5499330236835802715,7948058555323728235>()) {
                     case -594852563:
                        this.b.ag();
                        this.dL = 0;
                        this.dM = 0;
                        this.a(com.yiyiaddon.e.g.c.a.RESTOCKING);
                        switch ((int)com.yiyiaddon.m.b.a<"sw34w5ky84kq7","ZYhDoZGYsAKCVk7Q0Rcv2oBN3CeWRSFpCyfo9KvP1Dk=",-7726885799337541345,-2037154905544217983,7746344881076439128,-6820190086741242632>()) {
                           case 849338162:
                              break label46;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.b.i(var1);
                  switch ((int)com.yiyiaddon.m.b.a<"s1uyexcn9mda82","GHH7ir6qH46JnGoPgiDmu1lFJrG7Obin8RA480ras4c=",19463503519428957,-4452761994664284856,463095501497044686,5710941559577168397>()) {
                     case 1851447109:
                        break;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s10xqmm9f2veca","mZ8KKMD0v065NZyDPrM4ybgajYXmvWTMKdqSaAghai8=",3627099047195064900,-2508649061627675567,-9136013636022234770,6741888473393304461>()) {
                  case 2143896248:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (this.aC()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3p6bstnr8wwe9","AipnvRmiMkbtd9ledOiLZMYXHkPETy2P8435pib8yyg=",-7115839606609085771,-2081065458485677010,-1002148721556383223,-7676689419987723716>()) {
            case -1367416608:
               BlockPos var2 = this.a(com.yiyiaddon.e.g.e.b.LAPIS_STORAGE);
               if (this.b.m(var2)) {
                  label36:
                  switch ((int)com.yiyiaddon.m.b.a<"s1j2t6f9nnvbbp","zfKeahHkbCOEwhzOCk7nbcHnAWoablYjelrz1Q7YiUw=",7197844251231966416,-1776031053551638649,7211440634866594619,-7513586112775138825>()) {
                     case -1593240217:
                        this.b.ag();
                        this.a(com.yiyiaddon.e.g.c.a.RESTOCKING);
                        this.dM = 10;
                        switch ((int)com.yiyiaddon.m.b.a<"s2grkw3ddl9puy","0t5hZa6iPqzZcagQiogoyKeljg9qtHaSUQWyJjWbm+U=",3462233278801122767,-2219474620093999277,-7373363947159640490,5284024105794196924>()) {
                           case 931013917:
                              break label36;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.b.i(var2);
                  switch ((int)com.yiyiaddon.m.b.a<"s3m40yxmpd6sxv","80weMxR6mUc7Rccj66YqxrAr6afe8t4Um5QcmFI616M=",8923944944916481304,4181803830071539876,6715011158779885360,3907347209868422436>()) {
                     case 740356328:
                        break;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1o457c2yr5kyu","gpEHLzfVlMV5gv76Fa56N3Yy79mUxWNvw05IhJIRg2w=",396299393540295583,-6402249999289457713,-4162596477355879729,6408697530272199693>()) {
                  case -1998765869:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.a(com.yiyiaddon.e.g.c.a.IDLE);
         switch ((int)com.yiyiaddon.m.b.a<"s1ab46kq419bef","3HRr0lyy8lmezgayKF74k9yFNBAKNUmml3a4FcC6EsU=",-4443734191407515535,7307163457694390717,-4720125415017048766,-1415158900740590471>()) {
            case -10136989:
               return;
            default:
               throw null;
         }
      }
   }

   private void bT() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lbut8smkn481","ewwznWZ+mS2mkEG2HVuoFHLyt/zcpm5pf0GLLDE+Fys=",-801410497164318703,8412646095001876912,-5927680782922093610,6617131014582330600>()) {
            case 969240659:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else if (this.b.aU()) {
         switch ((int)com.yiyiaddon.m.b.a<"s26xk7tm7gcuhb","5dsZscBIr+S0GejJLesHN2g96II4UMKRmxfiVmc8Ipk=",1985711785746265889,-4798193463147314623,810495411850300213,6616459862780143727>()) {
            case -1913292277:
               this.x.player.closeContainer();
               this.a
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s1eoxxi68x3no4","2Ex4l74TVaCkl8MuAN5B+hma46HBqHd/p2RGJSvRdDvG7ibuwZNSemBwWn+EyeoBNoJChQ4ytB59Fb63KgM=",3944758554152112512,3759218900254254863,5788838057172230773,-5464232948265618314>()
                  );
               this.bF();
               return;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         if (this.dM >= 10) {
            label182:
            switch ((int)com.yiyiaddon.m.b.a<"s1qxqc3td82nnv","o+PeENKtGMjR0hck661jxCKV/7FOoepJ+F+GUfHNXvQ=",4926323797962195044,4002193927187653186,5339361269407974913,3240241011009325266>()) {
               case -1644707335:
                  var10000 = 1;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ssi7i55i42e2","cT2pXmacCt/VeS10l1JBrmv8rvAHIetIXMttEfK4bhs=",-4763489111678863534,6624409782125596965,5502754326567123901,-3853240546446440460>()) {
                     case -470920781:
                        break label182;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s1nfswkq92ovr6","e9insE33pLiMY+M6sIBpEhoQyPoJmGll92bl5ygtomI=",-4497176715331840260,6628453476855744714,-7048718240941741060,6283703229817132794>()) {
               case -472500776:
                  break;
               default:
                  throw null;
            }
         }

         boolean var1 = (boolean)var10000;
         BlockPos var14;
         if (var1) {
            label176:
            switch ((int)com.yiyiaddon.m.b.a<"s2e9r2j3zbvxog","mJqdl3E5AJI3RJNHgvctLsQywHvhLY6E7WTvTEXTq/I=",7450283295988992183,-9123614469484351869,1034044790765254174,-752818126093506497>()) {
               case -1190377375:
                  var14 = this.a(com.yiyiaddon.e.g.e.b.LAPIS_STORAGE);
                  switch ((int)com.yiyiaddon.m.b.a<"s285cgppz256ae","QN2QiR3uY60WA8tYYcCmeTpeCjf50n9o9oiFd+0paZ8=",-7918842647570458072,2402566215005244865,-4234907383724190425,5060839604071701405>()) {
                     case -692978499:
                        break label176;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var14 = this.a(com.yiyiaddon.e.g.e.b.BOOK_STORAGE);
            switch ((int)com.yiyiaddon.m.b.a<"s2br8n50qlkg88","07gmd8KtYeu33lOjMEqav/oN8ArmsiPIpwEdwtg7kx4=",442733515515443260,4727198003241347837,8420188523524609429,-4139785229963718599>()) {
               case -513839205:
                  break;
               default:
                  throw null;
            }
         }

         BlockPos var2;
         label224: {
            var2 = var14;
            if (!this.b.aX()) {
               label172:
               switch ((int)com.yiyiaddon.m.b.a<"smodlxjo8bc2q","uaoYtUynx9ujgQLe9tacIR3KalMwnxFr10xJsWkS/to=",4068281333215174330,-7459247242663650137,7194307604754964543,2846271268360320859>()) {
                  case -177303939:
                     if (this.dM == 0) {
                        break label224;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3w4q7qzsj0qor","eHJzWxm/HgGYG90qeY2NLYqumt05/pRzwftsfRxmZ8k=",408948073872600734,6157547880288852576,-1876856987504420744,6121373023838073141>()) {
                        case 655670181:
                           if (this.dM == 10) {
                              switch ((int)com.yiyiaddon.m.b.a<"s37696afp8yhs0","Yjzngwe3yYd8N4Vh1XYUBkzbKkrzSSyk5dK4g8PJUxQ=",538420982850356949,-2302632314233268314,-7500559497192926783,-7832178219266827446>()) {
                                 case 1494750278:
                                    break label224;
                                 default:
                                    throw null;
                              }
                           }
                           break label172;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (!this.b.aX()) {
               switch ((int)com.yiyiaddon.m.b.a<"s334unlr0qowbk","A1c3KQh1aRktJ/wNLYGgeS/g/XzTG8ZNzlF7TJmsa+c=",-5143159277707628448,-944845861897383103,-7784673874755366655,3090378628514078868>()) {
                  case 576194521:
                     return;
                  default:
                     throw null;
               }
            }

            ChestMenu var3 = (ChestMenu)this.x.player.containerMenu;
            int var4 = var3.containerId;
            Item var15;
            if (var1) {
               label165:
               switch ((int)com.yiyiaddon.m.b.a<"s1sd20hcc633n1","EOmQQ+3KbmJNda4zhEq7F2DKdz+9HHcR3MDzws628Xg=",-8850060256920896813,3474151126641571665,-3529063899648395459,-2314250501111661829>()) {
                  case -1293888440:
                     var15 = Items.LAPIS_LAZULI;
                     switch ((int)com.yiyiaddon.m.b.a<"s2selrlyzhv2vw","gQwv4S5nSs8pjRw6E3mykupJBu4nQ3OxZCZ5anwGRZA=",6011030616071945494,-5290232070127333504,-1848576276982304891,-7511842869110475857>()) {
                        case -1127041690:
                           break label165;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var15 = Items.BOOK;
               switch ((int)com.yiyiaddon.m.b.a<"s25uxjcqtfjyv1","flgPNyCNYYeLHmYxOogJpWCO/F2/fpDrMcptDAvUh/o=",-1178618964260996707,-2493866577310165364,2431011308666043775,-3561974471765208163>()) {
                  case 934307525:
                     break;
                  default:
                     throw null;
               }
            }

            Item var5 = var15;
            int var6 = this.b.d(var5);
            if (var1) {
               label159:
               switch ((int)com.yiyiaddon.m.b.a<"s29upzg6ogy8gt","bmZrrWFcbnnOqR2r3pjrekeoHIfUW4B93VUSomiqR/k=",469563806346039446,-8737455785298225384,5764697364190656947,2498558399193888583>()) {
                  case 1923165474:
                     var10000 = this.a.a().dC;
                     switch ((int)com.yiyiaddon.m.b.a<"s2n282pmr6cmhj","QP+gB6Q+SRHN8784154rx4ciekSvpuLCsiv1Dz+Bt3Y=",-2219108044827378527,4629972985013100849,3129677380908272885,-619789817030080021>()) {
                        case 1550645434:
                           break label159;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = this.a.a().dB;
               switch ((int)com.yiyiaddon.m.b.a<"s3u19987ztncdq","sXrC+50+5yrZmJd05WTJKEwcWaTRtTETuBP9v9v7ffQ=",-28438277402804106,-8218907756955655385,973038145892978960,-9153029920965999081>()) {
                  case -990021276:
                     break;
                  default:
                     throw null;
               }
            }

            int var7 = var10000 * 64 - var6;
            if (var7 <= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"sggt3dl4xu934","rSvQ63EGvHkSECHfpI6qrxdjW0Ro5AfGMIoX7PmwYZw=",-2183790392029693454,-6737272039221238316,3285166279599802900,6870766596392162962>()) {
                  case 1580731513:
                     this.x.player.closeContainer();
                     if (!var1) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1x74vuim7op7d","qCymsOuERRUkrJt8GsRk2dmmc8s7UWjJd3ORondAT4E=",1671639988571272116,-2958390506458992329,-6629098266435249332,-8443991073545485352>()) {
                           case -2100838899:
                              if (this.aC()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1hdxwngazuv40","xigfD8P05ajoAJMAZfS8/ifPxdz+hFJumlFrvyCRAsY=",-7143051545835567509,5649670489289544970,9202036114035646973,6584317450382979462>()) {
                                    case -32899250:
                                       this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
                                       this.dM = 10;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3uz1ez6ie3vb7","O+dAI2QSvXJm0CeC6eUwa3bAdARmloXMWLowpoJgZJc=",-8466874720490482400,6511523132500509811,-1298027265122754737,-4635479409331205398>()) {
                                          case -597409300:
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

                     this.a(com.yiyiaddon.e.g.c.a.IDLE);
                     switch ((int)com.yiyiaddon.m.b.a<"s2v1qnc30p1u9e","/QT+Pjk/y4y9bKlP9xD5lYURs2sfvPy9WiMbaeaX+bo=",-8560388649416301460,-4284336254794432582,-7860931818100750483,-1748130020529888474>()) {
                        case 1746484131:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            int var8 = 0;
            int var9 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s27w0m22gl41nr","ITHdG9wY8WAazcpB2gYK3Tw5IZQk1I6E+sa4eMDgsqg=",-1431353514287870905,3137801901392045082,-4969636984272745649,-7796753907825047484>()) {
               case 606518320:
                  label206:
                  while (var9 < var3.getRowCount() * 9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s17t79fgdxoh0f","JDvqqSvOjOWonvWRdXEwng6kbcyn3D2j3Ir/Augd0IY=",770313541541642710,6641150915491507475,2099300720492243777,424305982039009766>()) {
                        case -1510845742:
                           if (var8 >= var7) {
                              break label206;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2xixkvvk1ettv","fhAJTEHryTJg6/M0UIETrmSwLQwt83t3ufxg+3Q1CSo=",5792809599836294321,-479486510034214210,2063101295473128501,-2154911653985513448>()) {
                              case 1787391029:
                                 ItemStack var10 = var3.getSlot(var9).getItem();
                                 if (var10.getItem() == var5) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2dyargxyzt2yz","UaNRigKStQahoW8vxUx20QE8ihVdt020Td/NGK44+EE=",7922882695240548517,-4590005647231271283,-2210412405977531562,-3490270831231144990>()) {
                                       case -897643708:
                                          int var11 = var10.getCount();
                                          this.x.gameMode.handleContainerInput(var4, var9, 0, ContainerInput.QUICK_MOVE, this.x.player);
                                          var8 += var11;
                                          this.dO = 0;
                                          this.dL = this.a.a().dz;
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var9++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ldcvcin5ypka","NF4Nx65GnjgUNSAVKXrrlhmaiU4e6rX7r2RGsvkaFOw=",-228224626335576127,2382688435095451335,1121824532909377941,-6982863309968921696>()) {
                                    case 1574918839:
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

                  String var17;
                  if (var1) {
                     label143:
                     switch ((int)com.yiyiaddon.m.b.a<"s28xnjygi9dmgs","J0ybdv6bztKK1WfDcosTWozdr5SVpfj908ELcGw9KGc=",-4584433918706328434,-6477073155550809806,2824025805540010692,2864004265827714987>()) {
                        case -1069594185:
                           var17 = (String)com.yiyiaddon.m.b.a<"sqejx349ny3kw","9S8SMdIDdBBrL7Vmk6Qe72FMyDo0xdgpyNjza49kNan6dw==",4990887706511858901,-8410596311784600768,186297433535208810,7621880422751823346>();
                           switch ((int)com.yiyiaddon.m.b.a<"s3knrvkh7rgwfo","WZriSCeM68hoC2NpCtbpmH2/xI2yHi61Zk1coGYWHsw=",-8623094978944019394,-8474734742668500080,-4632059873117116795,8262357731657753869>()) {
                              case -832979238:
                                 break label143;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var17 = (String)com.yiyiaddon.m.b.a<"s28i1yjs1m87ky","2uUPYqIV52JPwHiM/f6UiU1yvEBmD8JttBPRUFl0LmZoVQ==",4734441684967245880,-841046730343294724,5498716053774049403,-4483741037915539031>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2wixtp556oc3y","z3Gr7oG/FZkwgBXsqma2C+1pw+r+xyBoh+SAb/gJdrE=",729852334402391394,-7671674202369906648,-4412427834926403196,419837306978309728>()) {
                        case -1963715920:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var13 = var17;
                  this.x.player.closeContainer();
                  if (++this.dO < 3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s23qs0n7779eid","LjCyktJ4vP5/Txl8ziF45nALUfjP2XmW0elmr09WqZM=",8320451968461263363,2291713142822659958,576857411890756059,-1116014960270422236>()) {
                        case 584877976:
                           this.a.K(var13 + this.dO);
                           this.a(com.yiyiaddon.e.g.c.a.WALK_TO_RESTOCK);
                           byte var10001;
                           if (var1) {
                              label133:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ms91a3650oeh","1T4X/IDDIyb7maTN1ipuRO+qNPkAqTLT+Dn5LWeVE3I=",-8343707265834499146,6848540759607239964,-2672053577276469146,3210614774415867017>()) {
                                 case -515673414:
                                    var10001 = 10;
                                    switch ((int)com.yiyiaddon.m.b.a<"s24u0ovfv5sehi","UrFiBDIhsbP/DrS5neOPJsLyDaPekL1lFzPE4Lv4hdg=",-2470879947995975015,3542357952568500033,3353836466112313849,-1112107006981352621>()) {
                                       case 12776609:
                                          break label133;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10001 = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"s32g5p2l5t2xjr","1c66R71kYwhF9fLAyfcqsXNp3SVaD+C3KmE0/KZ2kSY=",2273650608768909726,7856575999568242413,-7578342303510345305,-6770543913146636358>()) {
                                 case -866006909:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           this.dM = var10001;
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.dO = 0;
                  this.a.b(var13 + "");
                  this.bF();
                  return;
               default:
                  throw null;
            }
         }

         this.b.l(var2);
         this.dL = this.a.a().dz;
         byte var18;
         if (var1) {
            label113:
            switch ((int)com.yiyiaddon.m.b.a<"s1o6pclml2esct","vaSQPkugbCzIUlUT76K/FYhyG+AKZpkzGzdduRrO1W8=",6962354050147777177,-3668796200956994071,6464617657785639250,-3964797956916596509>()) {
               case 473856476:
                  var18 = 11;
                  switch ((int)com.yiyiaddon.m.b.a<"s2bekmzzg2g9xp","flqfWw9WMrSj5PZjr5XzWkugYpabQ8LVaI2NnVZEYIY=",-8968681447115118883,8712252339700929083,2542137084193235055,-7046802876418358525>()) {
                     case 1147026781:
                        break label113;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var18 = 1;
            switch ((int)com.yiyiaddon.m.b.a<"s10v8h3edaxr2y","mIzv5z+/pwtVKyY/kKMa8iKVANfNZPjSlLn8eVr7BBo=",-4527786418953725713,-7511628032749417959,176177276724955116,908310682798828781>()) {
               case -652381759:
                  break;
               default:
                  throw null;
            }
         }

         this.dM = var18;
      }
   }

   private Item c(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d7wl7sfyj1q5","Isc4zMlknSqNfHK2XbAE8OO6MDsT25yQXVKAW1OIMls=",-318259423501101656,6884998800830119486,-2004989009311142385,5002912829026854588>()) {
            case 1078368192:
               if (this.x.level != null) {
                  Identifier var2 = Identifier.tryParse(var1);
                  if (var2 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2f8ltp8eff723","Huf7+QlUz2Vau+CurSFsMdDq0CHvdTudJBL+eZ5xw9c=",-3162777890471484366,5094227770205001170,1595174283907864806,-3602766486850958251>()) {
                        case -214439545:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Item var3 = BuiltInRegistries.ITEM.getValue(var2);
                     if (var3 != null) {
                        label34:
                        switch ((int)com.yiyiaddon.m.b.a<"s1hx7ui7w4epvi","QT5g5P2WzqVVWb5vK8Vc21lDUswwXXQQJ7Xuf0KskQE=",-3920386919787114941,6543921716632714585,5299322476889470895,5440822943793085318>()) {
                           case 1333252362:
                              if (var3 != Items.AIR) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1b6979qkr75ba","1tcnJ8VHF37t6ADe4JnDUARkjTNQYHuRO3ZRHAACkiY=",-3179783968370615787,-1299919405892372621,-7941072757516907952,-2059194932850473881>()) {
                                    case 1565334863:
                                       return var3;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3s6fhcahtwyqg","WmbllGK9wF9YXXDpqyP++vtmVogR6WPtn7wmEErUPAU=",387617655194439736,8401703396488276494,2463157660728206910,6002292368438766361>()) {
                                 case -2056760934:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2zly1lv84z3a1","3FgJ/2wW7iiE23TA7Hi8GC0IuXmhb2yXL2QFVPXS0RQ=",5159652084916967673,-5668815633276869232,8019757104785106320,-533452382685904092>()) {
                        case -733554864:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1cd9taf03ymtp","sUEF811XviXmbXs5BGtCale50sOnBv/3UePjx4201aQ=",-739828413640773724,3962306769824203651,4022759302251855761,1936893495169050740>()) {
                     case 1640606649:
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

   private void bU() {
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s3spj1ya9etm41","abV5jDNMvR0YRQdZce2IkqbqNViF34BgY0rr2Cwtnk0=",7318378746991978183,3668474994542403795,-5839755043140111360,-5902898369284379794>()) {
            case 836498828:
               this.a.av();
               switch ((int)com.yiyiaddon.m.b.a<"s1ys4atjg0se2k","2zD6u6p61FGbmeCKDIPEQEx2aA9olzbM5607z189kt8=",-6134665827910947443,-3146781632868538685,-9149285333778356746,-3355707565711564132>()) {
                  case 1804217963:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.dP = -1;
      this.a = null;
      this.dR = 0;
   }

   private void a(p var1) {
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2dpsnc3juiexg","KWyDVt9GinRq3JjcPMMgirZK6Bn4tAbdxl429JuhHMU=",4601651476164666134,-6125719282586861509,7665759824484516302,3431091667984901016>()) {
            case -595627513:
               this.a.b(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s10kcv3e8haztl","EXXO7X/vr/MxyJjmzOrEUqwbLs+YQYdp/shOhWkAq3A=",5818840073232939700,-9030371019312002283,4294403127006406859,-4651341692438666251>()) {
                  case 1533388645:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.dZ++;
      this.a.b(var1 + "");
      this.bU();
      this.a(com.yiyiaddon.e.g.c.a.GEAR_IDLE);
   }

   private boolean a(a var1) {
      if (var1 != com.yiyiaddon.e.g.c.a.GEAR_ENCHANTING) {
         switch ((int)com.yiyiaddon.m.b.a<"slre37856ixdp","Wj634yX4PsOB5bgFH2jiyGDBGSeeCRY5qQ3AH7i/xSo=",3721938131123642772,3060599341583284262,6109089464534220654,-717777798287599204>()) {
            case 1508304121:
               if (var1 != com.yiyiaddon.e.g.c.a.GEAR_GRINDING) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ftjgz7h40r50","waRAgWNIigt2MU1oOPfAVQElI9O1WXddLKwSCPk1x4M=",-1074775449925614421,2850163116519552426,6455712602359668301,4944700113433773119>()) {
                     case -332209384:
                        if (var1 != com.yiyiaddon.e.g.c.a.GEAR_ANVIL) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2x0dr8mzxc1zl","F096D8XERbC8FF3rptacECPDA3UzFl3A/8lhEOK4DrE=",4106114485561607421,6932817813701745035,-1852594270042030092,6182260196392298926>()) {
                              case 1722659820:
                                 if (var1 != com.yiyiaddon.e.g.c.a.GEAR_TAKE_ANVIL) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s27tarz8fea20w","JHbt1LsDPKeyPuersa7utuTc+oQcOvC1jItaAO2Iza8=",9085147319617307276,-9129310405718474335,-3663309628495091865,9180755245980856516>()) {
                                       case -1914471400:
                                          if (var1 != com.yiyiaddon.e.g.c.a.GEAR_TAKE_GEAR) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sa8wglovolx0y","zLW1XVHrtcYjDxbBVPctxVzEEPSe2sXlV1gwlzldhcQ=",5081548296231599908,2518107251714434732,-5178673518506625699,3197968124580232809>()) {
                                                case 1067928401:
                                                   if (var1 != com.yiyiaddon.e.g.c.a.GEAR_RESTOCK_LAPIS) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2ix7pbqta7vtu","UiJDTYOKA5YnTKAFGNAtArrSUX7A8soi2zrEB8TEYQE=",4934274679196643118,8370034047506990941,-113668968478914685,-3283886386712464555>()) {
                                                         case -711567391:
                                                            if (var1 != com.yiyiaddon.e.g.c.a.GEAR_STORE_OUTPUT) {
                                                               label40:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3mpdwvuy6g9wt","5TPwVm8FfQ6d19JPFtLmSetXIXbSVGfV5/dYHfzTkBA=",-782144636675807,6427333517691836843,4659539088727012223,625251997844959420>()) {
                                                                  case 998223419:
                                                                     if (var1 != com.yiyiaddon.e.g.c.a.GEAR_PLACE_ANVIL) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"smhzr03hjtd2p","8iGKhHSSx2HAb/1WNI3FU+IoW5PDGjhA4zcZ/D6ZTH0=",-5828546248808336888,9045717140488147567,-5530170555191462097,7390147579602614773>()) {
                                                                           case 483918807:
                                                                              return false;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1jsqyf3yafk12","mlJB4eTWotLuM+lj/+lxw3GxfoLktBQokV39ke2FXuQ=",2567353327982599259,-7039231334995372900,6287863017394239993,7510723345889145726>()) {
                                                                        case -717149504:
                                                                           break label40;
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1zoajg3m8gyw7","KfUwrE1VitHYREm1OKK5LQV/4/ZvmLNi4F2EC6jhQmQ=",-7717587395143684105,1931740729796875880,6325486286536103880,-5859573193451879042>()) {
         case 1530466194:
            return true;
         default:
            throw null;
      }
   }

   private boolean b(a var1) {
      if (var1 != com.yiyiaddon.e.g.c.a.FARMING) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kdljh2my3aq3","1nkGQdO6GE50CGpW+LPjcAvcxLe6yXPBQKPMD4lkneE=",1324394608259356197,2750015762371507534,-8837416119779846611,-7281273117023135922>()) {
            case -1651218204:
               if (var1 != com.yiyiaddon.e.g.c.a.STORING) {
                  switch ((int)com.yiyiaddon.m.b.a<"spwodv268xwcq","ploF39fVyKZeCISMP4tT8VFWHyCBODr3KPeg+be15hc=",-5721833609488389227,421414809600170302,-5375792678324333749,-4539089065257120087>()) {
                     case -2053791890:
                        if (var1 != com.yiyiaddon.e.g.c.a.RESTOCKING) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1giovizxni9kb","L9iS/v3uKiQ/IarZo1DNLvUl0nlMaycFIrxryv8PP5w=",2552851350002841914,-533702066631475398,-4036722320692057730,-4547273331910630632>()) {
                              case 996470401:
                                 if (var1 != com.yiyiaddon.e.g.c.a.GEAR_TAKE_GEAR) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1o0n673fmmk5y","VjagwM8KbCHDl/12ElRFg6AfC/B2x4tRZAIFU5PdP48=",-7871095596841598596,9212535320576320081,-3775264382133567137,579899565354128651>()) {
                                       case 1492871048:
                                          if (var1 != com.yiyiaddon.e.g.c.a.GEAR_ENCHANTING) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s33bmyw7r8uz9o","EQa9GEYxweMoLYp5ZrstFEAFBLoLzh1jSdWMO3VYF3Q=",-2340285279107092546,6758713827116846876,3787524903534752929,-369190741024891869>()) {
                                                case -1050157056:
                                                   if (var1 != com.yiyiaddon.e.g.c.a.GEAR_RESTOCK_LAPIS) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s28dvpelrqseeg","0YC68837VXCZV9JoZDAo5cs6Qm8ECPTv0NBXITpC5rM=",-6189594278193256966,-2636105200060216368,3397952199607539531,-4209036982046957097>()) {
                                                         case 2108831694:
                                                            if (var1 != com.yiyiaddon.e.g.c.a.GEAR_GRINDING) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s191j3q7tbswo5","lh4ZUVBrCVM1esZl10IWfHx/nUeDt/WLAsbx+MkM014=",6668421668386053948,6105115221638550711,-4486072486748485128,-6337979189137594378>()) {
                                                                  case -358065020:
                                                                     if (var1 != com.yiyiaddon.e.g.c.a.GEAR_ANVIL) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s1dnmhtqgdp6y4","kk+PHOYuCdN007hbcbzg44g/84fvhulPLlunxWggNi8=",-8606273910901558744,3355096197244484047,-6403404332395027949,-4383595895003729248>()) {
                                                                           case 1497670966:
                                                                              if (var1 != com.yiyiaddon.e.g.c.a.GEAR_TAKE_ANVIL) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s2mze20tcvme1j","GGUGZwUH8B/9TdBUrSPIZOvVQ7Na672t+JuWijs0OPw=",7313196966185738742,-6369899540273547181,-7869853144236191079,-6757355028719358807>()) {
                                                                                    case 1812369044:
                                                                                       if (var1 != com.yiyiaddon.e.g.c.a.GEAR_PLACE_ANVIL) {
                                                                                          label49:
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"szg58saz0ljni","3LAB8wxYQDCcvtmqJaQuazrU7Rx+98UhiX7dltScjwM=",-109734216518818396,-4999584645182808556,-8459884031438125274,1280724269085840431>()) {
                                                                                             case 763645037:
                                                                                                if (var1 != com.yiyiaddon.e.g.c.a.GEAR_STORE_OUTPUT) {
                                                                                                   switch ((int)com.yiyiaddon.m.b.a<"s2pmrfokgzy26u","8zgiy3w8hXkbwCqhpxvnGJzOLLYrtZwFs/ONdUd6/jw=",3838893858236402615,-6806036407445411291,-6986407892435107875,2787866909157947051>()) {
                                                                                                      case -996947142:
                                                                                                         return false;
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                }

                                                                                                switch ((int)com.yiyiaddon.m.b.a<"sm8ahmd1v48iz","jybj3BN0NPUX09hgYEu+bqb2qg2jQEXnhC7aRZK9xmk=",687147816724631153,7315458292035078456,-4372666267513121803,-203564741150053327>()) {
                                                                                                   case -289697891:
                                                                                                      break label49;
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

      switch ((int)com.yiyiaddon.m.b.a<"s1ox6os6yf6eec","0/+m3Dc3E/iyTGnIFweRQMx+Sc2RWGUMkpJRwQep/2c=",7900519859886152172,4425666974927760938,-4071272490553109828,-8652048348356565699>()) {
         case -87336378:
            return true;
         default:
            throw null;
      }
   }

   private int L() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1kqaretir616p","0PPntPOhZZAFPm8bXuwkdG9KiFzZhOyXrjhlZLClwTY=",7364293728294528571,1993491971823436957,4611842135529318937,-3484853455446508885>()) {
         case -1917432833:
            while (var1 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"smnx0hkltjnin","qHLHzEQLjE/h9Wpwktiocz/IAiJAiVo5EiQ4OXHNAAQ=",-3718024238726928232,3825483769935991563,-3517363155680126802,8720328350532119015>()) {
                  case 484111472:
                     ItemStack var2 = this.x.player.getInventory().getItem(var1);
                     if (var2.is(this.e)) {
                        switch ((int)com.yiyiaddon.m.b.a<"shb0m86ycjbgy","i5j/HNIzUphE6gTD65lrmeN0TPRJZXcN8YRhp4cllX4=",-9117733018214629729,339683390568059715,-1641707525058347567,543102739479541022>()) {
                           case -521877491:
                              if (!g.a(var2).isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"shrjvmr384n2s","uFJmBC/o8ueuN0GMOePfT1io+bmQJu8Va3YOtZafxTg=",5171143807653591647,7914205600491073967,2587252370103440341,5916788281570218148>()) {
                                    case -764185006:
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
                     switch ((int)com.yiyiaddon.m.b.a<"sszjonbl1eg7f","xZkC0BqUcbk8BaNJI63pgoEXOxAkbmiDqkqs5s77ypc=",-1759883204046843302,-6781997172682544560,-4762146870432519428,8023929811016395905>()) {
                        case 508010647:
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

   private int M() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s14nyaw5t9jjaq","OY7LnaNkbDVkHbf/VMNSzg5mME0vSmQcWG2h1F6olkU=",-5695799625725405838,2926828893104337920,-1305957692739779418,3553643945909757133>()) {
         case 42785724:
            while (var1 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s3j2ofhjd1sxft","0t3yxGN05usSQ/FVWkRqlQW/IPT1qPrcLStokYokuyA=",-7627006673169840387,-7329024561784624423,-3978411765766781239,-2952994360768625994>()) {
                  case -1084695209:
                     ItemStack var2 = this.x.player.getInventory().getItem(var1);
                     if (var2.is(this.e)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1rg7y15zaozfs","Iz7YFmtBaU1aRM2J9aHOzqoDOimDD3CdRO7kvZn4zks=",-2177014802987004264,-4908845833190538452,-6746241621873129542,8571984398641817473>()) {
                           case 2113341824:
                              if (!g.a(var2).isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s23e0sjc4k7o22","2tGU2FGGP5ECC2riSlNXEsYECkZU0ZNHfUR60uZFSqY=",-1869984065129752609,6243920020236224021,-3905938316264458167,1644330134389745582>()) {
                                    case 1760259755:
                                       if (com.yiyiaddon.e.g.k.b.c(var2, this.a)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s24jd1p4kl6lvh","aQ0F98RIJ06pJHohUDZks8vpmRj+xdCCGbbJefvNl5g=",7473123288788598895,-6752447507784066909,-8049740773151045229,4329600576493881854>()) {
                                             case 174431047:
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
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2vuab4j56rns4","d1EjQRXdIYvmck7j2q78H3edwq7TMNAZ2Yim3z0V+eQ=",-7289416392562395930,5316779026230510105,-3027092568560440779,7331984425131820870>()) {
                        case 1841608411:
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

   private int c(Item var1) {
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2lylp804blg02","kk3Y1Eyt895SwmF/laKFN6MM1F6Fxf8sYzS8u79z47w=",-211604796605747992,1243885323083041787,-1447593824027371939,4837388602101268919>()) {
         case -1890297735:
            while (var2 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s1n39q618lgrtd","JP2ad8cX5dRkq+R62/6RMVLkB5nhEPrbHoJGeWbNqi0=",-7314939299274630961,505021138284009327,8609204112452108085,5454657887130159794>()) {
                  case 1613169795:
                     ItemStack var3 = this.x.player.getInventory().getItem(var2);
                     if (var3.is(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3dckgcy7cobsq","od9K+qlt/rAxbgKhDMlPMKsYtPWpMsnzWDEvtLw74f0=",4019030366905074739,-7689854924827677418,-7942180821324031609,1483877376532623934>()) {
                           case -1564658377:
                              if (g.a(var3).isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2wu9fnez4lg5z","Z+/OLZfhdj6Q1XO24piU3cmt0RMa/U9Mn7CZ9l2H0nI=",-1310614902040555984,-9123397648733861831,-2743123601989579835,-6834791990368227378>()) {
                                    case 1560306524:
                                       return var2;
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
                     switch ((int)com.yiyiaddon.m.b.a<"s385s23f5dl25r","EQWj9H5v0dzTPj0YSa3kkMuB7u0ySioEc//R1+2t3Ks=",-2730947945624128336,-2505347723659454037,5987623193208992724,6790275979643500471>()) {
                        case -680218130:
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

   private List<ItemStack> E() {
      ArrayList var1 = new ArrayList();
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s899bcla1kkyt","ZOl1wRcTeA+Swup7y8PHhV5i8lodJ31boW0YmSciZU0=",-5248498306167726290,-38111764327303907,-2001518221344407502,7642903492691954212>()) {
         case 1760215143:
            while (var2 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"sko3adzkvginu","cEFY8hEzhbTGdau1zAQzm8IskLivQHRF2PntIGMtWDM=",1390704375312811078,6288781816490698658,-2076554146323692421,-2289804972033034519>()) {
                  case 743379132:
                     ItemStack var3 = this.x.player.getInventory().getItem(var2);
                     if (var3.is(this.e)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1ly5qpn2ix7qo","MqxiTjEV1d20DoxjR3R22TwdmVsjbV8sdBvMGwg0dQk=",-4684758338853384644,4265035999492257141,9170344713441402648,-598472379557147826>()) {
                           case -1542988981:
                              if (!g.a(var3).isEmpty()) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1o823wwg5j3jj","3hX3toUWvV7dz7uj1jdZ1LrpA9M/x/PL2ZLz2dffog4=",-8195044362559739261,732992791397637189,7495138968310850826,1611197172962430379>()) {
                                    case -1136342440:
                                       var1.add(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1wzlhpbpvtc1z","mVoTusiV+ZT2QQY70GdUznE9SdkdpT4YOL/aRFdLPs8=",-613297519872506508,-5460648426927272007,4081029304587804905,-3308277191756789857>()) {
                                          case -1603884914:
                                             break label28;
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

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1p5unjf81x369","223qP5nWoxsIQhr4dLHJdXtrtMbTrmwEHJ2uBbko9go=",5289323213214497407,-1412480069394128126,-8967853106406869982,-6863183671939121293>()) {
                        case -155820761:
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

   private int N() {
      int var1 = -1;
      int var2 = -1;
      int var3 = Integer.MAX_VALUE;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2h22uwq5dd0p","ucXUpJ22tCH9IhjA5OMsFL9SyWJHXeHpGNoy9O3hA2c=",-1100082610051133681,-2639559761115115495,-6392620047752713752,8960092932201955082>()) {
         case 386608254:
            while (var4 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s2j7lle1ek8ypf","z5wc38Rxnb5kx6cVrMO6AHne9MQMBxFa98bxpYGOrgo=",-1143087831837472635,-8561659731597670885,3884481447640725796,1810036866373025566>()) {
                  case 1037277796:
                     ItemStack var5 = this.x.player.getInventory().getItem(var4);
                     if (!var5.is(this.e)) {
                        label51:
                        switch ((int)com.yiyiaddon.m.b.a<"s37qcpu04nd0tx","L4ZqoCKWtNbsinu+ZrCu8M+y2atut4lBp787ZuMK7P8=",-8684462800944329338,5587628028164621285,8028883952093439573,-7259474191285416146>()) {
                           case 1212697156:
                              switch ((int)com.yiyiaddon.m.b.a<"s25lfc1bhwdult","nw1UpONBRIh8Z8vKwHbuE1ZKIdkLLMeJoHXRFWhIFwo=",-3223714511294244937,-1942762703108700276,6354718798771685964,5390024057570545862>()) {
                                 case -123632230:
                                    break label51;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (com.yiyiaddon.e.g.k.b.b(var5, this.a)) {
                        label47:
                        switch ((int)com.yiyiaddon.m.b.a<"s1mk9twzerqd34","ACICtqOszoUaQPIjqjjipH51Do17P4vLJH3ifTs7vWA=",3638032661625200145,-1980972126245560815,7531154399988448350,5035549161491857556>()) {
                           case -813073:
                              switch ((int)com.yiyiaddon.m.b.a<"s26g2f955xmum2","co1cUgLbFJy4235hOCMCHT0LiLT0h8mKu3b5+BiZLQk=",5865459839489837384,-2790280110518380918,1818299621927481459,4071927370488081586>()) {
                                 case 935409425:
                                    break label47;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        label69: {
                           int var6 = com.yiyiaddon.e.g.d.c.a(var5, this.a);
                           int var7 = com.yiyiaddon.e.g.d.c.c(var5);
                           if (var7 >= var3) {
                              label41:
                              switch ((int)com.yiyiaddon.m.b.a<"s2i7oxdc748f8g","ehrb8u2kqKKmFD3qjSf8nV6DH3pcMXznnpc0ngPhgKE=",997521512368571874,4290529978178880622,5559962475267914257,2308838090381981059>()) {
                                 case -1353013556:
                                    if (var7 != var3) {
                                       break label69;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2fyed1hnk7x4w","w6IYAoIBm+d35Zy1HIki+a4ICn6K4LXgRB9/zi46bOs=",4517953460057857076,-6492635036613415965,-1495693675602594713,-734869764055521390>()) {
                                       case 1875431814:
                                          if (var6 <= var2) {
                                             break label69;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s3o94wyvb9lxa2","ZpyqdaZJ/zAmeCZkMF3myRPbypHSp6bJjL3kUJaE1uQ=",-4908281497842566230,9063694687412463902,5046486672274276258,1822258084452118170>()) {
                                             case 504326433:
                                                break label41;
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

                           var3 = var7;
                           var2 = var6;
                           var1 = var4;
                           switch ((int)com.yiyiaddon.m.b.a<"s1kknf9lhsievq","YwSMGUMeP7cJUka1Pi+4MMVMDd+7URfAlGf4cZDYBWA=",2195244679175976827,-1595113149016351352,2566204949047070889,1074697644366753100>()) {
                              case 1074185003:
                                 break;
                              default:
                                 throw null;
                           }
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1kjslwac754a6","AHRGBqwLiIg7tW8o0gElcMDkQ4kQkmBKvb2HzPRYKZ4=",-1982394705987770168,-7519099481175214092,7953269868017690083,-7814622655784176156>()) {
                        case 1792735658:
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

   private int O() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sojif51og0djg","QptTnCPIxekUGCOtQyFrfnZrytr9EdPEdMxFNZ5B1tM=",-1319016145954297694,7705913557341532331,7210404660396641978,6551398367362663300>()) {
         case 1248482053:
            while (var1 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"sad0bpue4fhx4","vYBvQjiyUkxneRJcOjDFxIh34EmnG8n0dXcmq/Pf+4A=",1533781452790633147,6587931758651057141,-229550303453193443,5097920755289763777>()) {
                  case -1087948338:
                     ItemStack var2 = this.x.player.getInventory().getItem(var1);
                     if (!var2.is(this.e)) {
                        label29:
                        switch ((int)com.yiyiaddon.m.b.a<"s2f5dvstdtb3d0","dMlAOVQ4B3G65vqZMOaHgiaP9dIeYYJ+M1arswiQopU=",8630427665928761505,-7273300133861218939,-5353623900317980435,-3441431162338377334>()) {
                           case -788036429:
                              switch ((int)com.yiyiaddon.m.b.a<"s3n70u9mla1vwa","C13F3aTocvkaQigHcUrdYVUpbvDID5ZubowFvRKdUWA=",9159077049836242332,658662056316413692,6139854395108638768,2485919648842386731>()) {
                                 case 717259464:
                                    break label29;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (com.yiyiaddon.e.g.k.b.d(var2, this.a)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3dvhar16e5hgd","REZKG2653oKmP0a/MBv8sC03QvlLOMeZoSZdNFdvieQ=",7002361191003190034,6260696776311841665,-8944401838448876168,-2879633484233558885>()) {
                           case -1841253658:
                              return var1;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1fj5gy8gwojvs","cNyJl+i0e4x99WfnOqtuM74QvmiCCEwZiuvCRywuS/Y=",8860888305241632128,-6529806379908513607,-8886846831861760523,7387584848894876957>()) {
                        case 458995523:
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

   private int h(int var1) {
      int var2 = -1;
      int var3 = Integer.MAX_VALUE;
      int var4 = -1;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3bwle9uw7todn","PCSGzTXv7rH88tCQ0VYCEiin/JsWuNgoiUmYhlN0w7E=",-6362169571735439497,-6566710084249792655,507040868846714077,6667719635314026238>()) {
         case 97344797:
            while (var5 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ow5znrwq7edv","e7tgsFunttJAhFO1wKR2q50STKuu3ScT4/5XCp9LBII=",5939192033317362876,-2574169036226458694,6052718450871668120,7190750160120908885>()) {
                  case -367629724:
                     if (var5 == var1) {
                        label69:
                        switch ((int)com.yiyiaddon.m.b.a<"s90rvxwr37gqz","Ewatg72xYwEdF5Ee6wBTwIUp1NsPh7/8MRwqbNjwlgw=",-2977912544366916041,-1891490878970818538,-7597481775788209352,853135083590630550>()) {
                           case 444242298:
                              switch ((int)com.yiyiaddon.m.b.a<"s3mhpayq766v36","02XXMX/K7QZagK7pFEUimTq/C3A8wYwc9F2ayS4xmwA=",5780989971917945230,8410739949795652571,6771700192777343311,-7172382899979060414>()) {
                                 case 1149103505:
                                    break label69;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        ItemStack var6 = this.x.player.getInventory().getItem(var5);
                        if (!var6.is(this.e)) {
                           label65:
                           switch ((int)com.yiyiaddon.m.b.a<"s2a7z5ckin8asy","TXEn1avTl6L4it1nVE+9AUQ/3GtUQvDqkObtBxPfcbc=",-1189982539403854364,-7719694253866033712,-7147244508225608270,2100434500815042020>()) {
                              case -1296151135:
                                 switch ((int)com.yiyiaddon.m.b.a<"s385gwlh9d6lys","f8odM2xXFggAioRB9bkbQXhlspjgwFwqn7DWxzEwCyM=",5304512187066942749,8584078188442371256,-2090988880845410584,1142619660818141342>()) {
                                    case -984644355:
                                       break label65;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (com.yiyiaddon.e.g.k.b.b(var6, this.a)) {
                           label61:
                           switch ((int)com.yiyiaddon.m.b.a<"s17z01c6rojxc6","wvqzxhVWYzkwgzduWPf+86ooAnkhOrAcW1Dfalhd9ZA=",8998455065305804905,8486034738818977199,-580533374613920973,-7024168216511711560>()) {
                              case 347135324:
                                 switch ((int)com.yiyiaddon.m.b.a<"scdfdrlmts4k0","4yuPziJFCJ0xkC+KrXtaYQN7ZlnBs8O+2z+nlvWj9cQ=",-8719945715601563742,-3147317338772337619,-1068525679739441586,1297923908724076172>()) {
                                    case 182020027:
                                       break label61;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (!com.yiyiaddon.e.g.d.c.a(var6, this.a)) {
                           label57:
                           switch ((int)com.yiyiaddon.m.b.a<"s4z0t1eizy7z","1wiNFj/P789UydGGd+hWE2pY23RB9c6D/kQ4rKNI8eM=",-394769641040399531,4274788041762018276,-6202755975119494795,-2605136795232487851>()) {
                              case 814912664:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3fcx6bt389hu9","p0te9VpNhXSvqvn2ynPYrTYoey1nmOUsr1yQhhY34gs=",5017928182240645624,-2213812444986233166,4468935026373440233,9138135269051108129>()) {
                                    case 79543316:
                                       break label57;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           label89: {
                              int var7 = com.yiyiaddon.e.g.d.c.c(var6);
                              int var8 = com.yiyiaddon.e.g.d.c.a(var6, this.a);
                              if (var7 >= var3) {
                                 label51:
                                 switch ((int)com.yiyiaddon.m.b.a<"s53uq48k04ill","wbRT4Cq8epG9mfNQhiiSwu0oIlQWejA5Ac7mDEGK/aM=",6865698004262412351,2353020881254589452,-1604034602763987491,-5365394817436155277>()) {
                                    case -691441337:
                                       if (var7 != var3) {
                                          break label89;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s3a4bgz56ll057","JU/Lw2I5cLhLtWlkuFHS8UqtAC1yYw68JxmFcEH6kLo=",7035206674304266599,-7906891590293024426,197795481330342563,-1783716870009967767>()) {
                                          case 1414081862:
                                             if (var8 <= var4) {
                                                break label89;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s7ceebtk231wr","A/BaT08gO6cEKYF2G23LEC4SbthCQ38q85ZW9ohYz8c=",5063241411927866034,-6990320486323197677,-4743805859469853501,-2911104916698045175>()) {
                                                case -534253260:
                                                   break label51;
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

                              var3 = var7;
                              var4 = var8;
                              var2 = var5;
                              switch ((int)com.yiyiaddon.m.b.a<"s1q99g13d8eko5","AIsD1Jl7bBknFTOigEz+SY6IpqOEINnh8KxmBKmB+Vg=",-5653625670738966034,550985300101487254,4027004682925084163,-207673533022903546>()) {
                                 case -648801302:
                                    break;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"sjo0z2ffg1yzn","xJHPt5c8MD1dxey02VB8lkARxvTxLP3b9ray6eI1Wjo=",-1266760605455755344,5374478610658996215,5439080927366747645,2642399363864077753>()) {
                        case 1970263839:
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

   private int b(ItemStack var1) {
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3lqzlqq9dcc21","L1KAi8mluTP84SA+F9JmqYfhz2bTQ4CFBk4zehIcPis=",8582975274347012266,-5347554975132072721,-3879119978864627230,4731503464134760796>()) {
         case 2142440551:
            while (var2 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"senb9pg5o974v","1eM1hagI83HrIY9C6CDVuqxEGTXgWanS9vJwWj9cxgY=",6599681983364808761,-3365561891500697869,-2900526974059139372,-4567272112816623432>()) {
                  case 866764919:
                     if (this.x.player.getInventory().getItem(var2) == var1) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2hinnjk2hjdqa","JRjETKookVbCmJIH4LTHHJdZfnlgryVqhBaXFeU1kMI=",7396078765829534321,-7520420944177126841,8013292615317568927,1314941534524977149>()) {
                           case 975649639:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1xnq81jy3dm83","gPoWYV8sOBsm+FtTmwAJAUuCsSRzt9AZoAUoH6YH9eo=",-1846733773439763070,-1200811828911189229,-2223736598284827289,6901687466953426120>()) {
                        case 644977232:
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

   private int P() {
      int var1 = this.a.a().dE - this.dY;
      if (var1 <= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ru94psef8qy1","h7SKhN5M1GxGScxiYK6FhII+zJ9zNRw+ZMVyYdfOy0I=",-6328047674141956614,4192360070107908843,-2764887074449182596,6975795335186392524>()) {
            case 1355498964:
               return 0;
            default:
               throw null;
         }
      } else {
         return Math.min(this.a.a().dD, var1);
      }
   }

   private void bV() {
      ArrayList var1 = new ArrayList();
      int var2 = 1;
      int var3 = this.P();
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"slkpwqa438y0","/zg3V7MsSmECixatgPfuY5HXKGfXuNg8kuXV7UV9SUc=",5389586888514249256,-492800499789714604,3676853684692121528,7500975655696913162>()) {
         case 1562913774:
            label61:
            while (var4 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s1m2pnpwxdsrfd","jNZPXUo/63EgaSbrYVhKJRNGuvrahgdiNkf0LUMc3+Q=",2175561228514177706,5341145928666117293,-3300251286029843485,6567448959231141825>()) {
                  case -1255855563:
                     ItemStack var5 = this.x.player.getInventory().getItem(var4);
                     if (var5.is(this.e)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2rp8dgxzez50u","/AT9woT9Y0hSoTyohtm4DoRGXaRM02wfuT/peBZIbco=",-449481217097455060,-3896007301738622098,6340399346238537682,-4175210755542527281>()) {
                           case -317252917:
                              var1.add(new k(var2++, var5, this.a));
                              if (var1.size() >= var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2vx885d04dlhm","ix1IBJNvz+1IwJKVSLAph/lkBL3rQbt7K14BxdcsIH0=",1947154181928296842,4392772295718528734,2578005983426410078,3958729705307148857>()) {
                                    case -1013677722:
                                       switch ((int)com.yiyiaddon.m.b.a<"sh829qjr6m506","NEzLf+us1I0fHGoSzlm11Ln+IcXuwUrcgPQ2dkFHhc4=",-6865798257577287366,-3639107534191474077,6651615400629776026,-1223614754416383259>()) {
                                          case -1306496221:
                                             break label61;
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1nat2lub5b4fm","vEbN1qKHOTg70C+xSraWnw/nB/HSxj8N113FCjCznlc=",2837063176596867827,-2042619831141540882,-7178672299098566867,-4982660003959763233>()) {
                        case 1714706221:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var1.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s31tvcuc8cb62s","KxSpZpeQkuRIrlqad/MlJRSnUlYX6bpRGcUgcaSDcNM=",-5219109431352185447,-740717716986992670,-6083003933791106160,-8513939050424017886>()) {
                  case 729046413:
                     this.a = null;
                     com.yiyiaddon.e.g.a var10000 = this.a;
                     String var10001;
                     if (this.a == null) {
                        label34:
                        switch ((int)com.yiyiaddon.m.b.a<"srrogmpvmyjkx","Q8PrHZwkHPSBJudZA5YWeNdw4/dOHfMEKzZgAoW8o7o=",5443504022035437159,-7919323811221082344,-3224522161569840042,8889055597005068229>()) {
                           case 1139646423:
                              var10001 = (String)com.yiyiaddon.m.b.a<"s261vuemuyzkx8","WaEPr1TiLCoBvUielP7UPAkOxzkVbtzt8YQoTDUveQg6d359",5755066621194425460,3336691400428990782,1390312033153842285,-2348280490489962409>();
                              switch ((int)com.yiyiaddon.m.b.a<"s3kdxkldqqmrbr","2a6J4Xw3Pi6w3+qrpfyXPndakwC99Nnje/Hr5LtMKIc=",-9130104074677745103,5222665228590074356,6170948889009828597,-4645258570749498223>()) {
                                 case 1646043086:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = this.a.aJ();
                        switch ((int)com.yiyiaddon.m.b.a<"s7rc0b4oqbdqd","o+DH8R4iUPD9wSkhZ6mFNH4CCfJecWl7oZX9yQHLeYU=",-2036525793845638604,-5221086904670186875,-553366915755920930,-5263918262214583761>()) {
                           case -384853223:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.b(var10001 + "");
                     this.bF();
                     return;
                  default:
                     throw null;
               }
            } else {
               this.a = new m(var1);
               this.dX = this.dX + var1.size();
               return;
            }
         default:
            throw null;
      }
   }

   private void bW() {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s6ehv8ea1bqi1","9/iki3gQZS4TNTDMnY/sNOKhWkEGcn7saS1NQwwRQKo=",1149310074853995630,-5420382829509082608,1291109047261050557,-7924085947247405970>()) {
            case 1165184675:
               if (this.L() >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s26wz7dom06j6k","jSwbHDeh8Q2qe6grMgkQH4kyadWrnCWEAIMV5sCYuGE=",5516277011633475688,8533027914733973554,-448568891487143884,-3960374081858530178>()) {
                     case 2007331867:
                        this.dT = 0;
                        this.a(com.yiyiaddon.e.g.c.a.GEAR_EVALUATE);
                        return;
                     default:
                        throw null;
                  }
               } else {
                  if (this.c(this.e) >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s29h71p8m5eno4","z0BgnhlDMYmDVqVr87xjJcryl+tNqW8QhkmS2BjMQUE=",-8355384099801431155,5426368472739510688,5993753827240425954,1718000916100435237>()) {
                        case 191898102:
                           this.dT = 0;
                           this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT);
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_EQUIPMENT);
                  return;
               }
            default:
               throw null;
         }
      } else if (this.a.y()) {
         switch ((int)com.yiyiaddon.m.b.a<"s5eiyt5jwxy99","T8jZIYnjWr+4Q72+iB1GnN9CvztNc/37zVss6hjQrwY=",569395541563909854,-3454284622296399667,7537154877218905508,-3819141099735029374>()) {
            case -635939195:
               this.a = this.a.a();
               this.dT = 0;
               this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT);
               return;
            default:
               throw null;
         }
      } else if (this.dY >= this.a.a().dE) {
         switch ((int)com.yiyiaddon.m.b.a<"s21uwvqq1h9enn","+IUj4pAqpSl4itVaGEN6lATdhenomumgx4Vn12rL/Ic=",5034647683214624916,5092420443224723195,-63165958228569091,856266663419378200>()) {
            case -2077857669:
               this.bH();
               this.bF();
               switch ((int)com.yiyiaddon.m.b.a<"s32bf1nn654c0l","LSwXbQbwDfxd3M3SK1fjTg5rybe9VCbBEp6U0enPJAA=",-5444424467027094584,7728814494950728162,-1825543418637606071,3592943698942196277>()) {
                  case -2139497272:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.a = null;
         this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_EQUIPMENT);
         switch ((int)com.yiyiaddon.m.b.a<"s3plo49f9gdwo1","u8Q3HJ/71W1ERhVHWhTmZGZdAw2SMb+1OK6Y4Qm0Q44=",-9078433716376359087,1006866443277945134,9041657575992981844,2431468644255949818>()) {
            case 2024055809:
               return;
            default:
               throw null;
         }
      }
   }

   private void bX() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.EQUIPMENT_STORAGE);
      if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"sekgfdjjk4nbg","+yfLh1dbU8iP5Xv+j9WeeAwg+5KoAMOsorDPt14FnKk=",3345859512937370645,-1402750418270619787,-8167734707271482492,-4699993900258876148>()) {
            case 416059258:
               this.b.ag();
               this.dL = 0;
               this.dM = 0;
               this.dS = 0;
               this.a(com.yiyiaddon.e.g.c.a.GEAR_TAKE_GEAR);
               switch ((int)com.yiyiaddon.m.b.a<"s26jd933aati7x","B8DQCaSzu1F/uEhfhlR9/eaOB1tqpL2epOi8onrmv6Y=",-6739774108985648860,-1335239723582689827,780299604751368594,3633716477762926016>()) {
                  case 1351811704:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1yf8dzneq80q2","tGW7iZBIGeNCikORMHBSpGodGUukkg32kdWR73Glg88=",2488821565204791986,-4916977869045121008,-4857228259190908620,7862803827643656405>()) {
            case 213642815:
               return;
            default:
               throw null;
         }
      }
   }

   private void bY() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s30sep37wopu5c","r7ruSVA2zQQ7VT5XgC0QBqjNkaHTE5/M1w6iwOy0jt0=",6576527227577026498,-8526519314824379671,8806536362064045932,-1111328541763624999>()) {
            case 34479508:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else if (this.b.aU()) {
         switch ((int)com.yiyiaddon.m.b.a<"sy429bnyphg15","0q+X+jR0JXpF+8e02A46QPYWhXJFcokkdNAIPqZXdeU=",5187288020108848289,3907650425974681012,-2928425459588969001,7222896495472416100>()) {
            case 1065925749:
               this.x.player.closeContainer();
               this.a
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s23amr4mufcvep","ryxmg1oE5eh8u5MksGl/AzXDwmXgyu4ohz4jQ7l2q4OgqTTK2c7KTnmOVa/aOdBw79kaCrir/ByR+XVgdQdG4Q==",-4514185246079790486,-7659560839763780871,6918902885176178153,-316151380933525453>()
                  );
               this.bF();
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.EQUIPMENT_STORAGE);
         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1m2m7ybcig8an","JIEIXeqyfxMtY1lF8lggh40ft22t8fVVSIMPs9J1f8s=",-6711633406919404163,1214521117750266499,7891944948957187347,-8358319253600631757>()) {
               case 1747474280:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s7s7zgxua1mhh","wPoXJAwZ+/mZ52jerAfs3YFEDyQsfAv8+s84edRIMb4=",600598281547150296,-2110500003874626457,-7458318403706277228,7853006881809314400>()) {
                        case 1538022003:
                           this.b.l(var1);
                           this.dL = this.a.a().dz;
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

         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1k5x05rz6lqcx","gHHTa/0c+YjHaYVeRwJEZ0A5T7eBmFEJKWsX6Z53d58=",7375178729400497330,7398217565497766137,-4380142157130852322,2991938045799098316>()) {
               case 85654867:
                  return;
               default:
                  throw null;
            }
         } else {
            ChestMenu var2 = (ChestMenu)this.x.player.containerMenu;
            int var3 = var2.containerId;
            int var4 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s179fcf7eab3sj","eb4c21DbqWZVh0JmbTogM5lmqaZKvr+kVMwlBZidQ+A=",-6471302455711677964,6482517226730015678,1312068835582729149,-3389709775584303141>()) {
               case -1417302167:
                  while (var4 < var2.getRowCount() * 9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s31fzrqzc5vb48","JwIIpiXbw25xW39ThaZ31zMfjyjVbaEdxztYt1x4qsU=",-7416633476185845941,3159334271813301779,-1210413667250074085,-759998582542762152>()) {
                        case 294863945:
                           ItemStack var5 = var2.getSlot(var4).getItem();
                           if (var5.is(this.e)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s378kt3cxwmx4h","gwjjZlw/9dQ/1t1bOUPC5BwEyiWrmv6ky4Y22vseBxY=",-2409485818197726636,-3836264346800775919,-5047278258712384393,-517289770140981949>()) {
                                 case -1262119572:
                                    this.x.gameMode.handleContainerInput(var3, var4, 0, ContainerInput.QUICK_MOVE, this.x.player);
                                    this.dL = this.a.a().dz;
                                    this.dS++;
                                    if (this.dS >= this.P()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1bxp1s73qnivs","Tc7SqUinGLBKhKCFkAjT0yvfQQ/nBuWw0f3LvWbG3Bw=",3832265192013349142,396683888523497277,8285717119254967915,-3424401691726769032>()) {
                                          case 2063940890:
                                             this.x.player.closeContainer();
                                             this.dL = this.a.a().dz;
                                             this.dS = 0;
                                             this.bV();
                                             this.a(com.yiyiaddon.e.g.c.a.GEAR_IDLE);
                                             switch ((int)com.yiyiaddon.m.b.a<"s2utxpx46jzdms","5Sv9n+lEOWzCVonXalTpVMt2Bkrw0+whCgWD3Cnd9Go=",-5250495201350114170,-413196291456026173,-3361679851142118593,-4372668097042163046>()) {
                                                case 1396673464:
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

                           var4++;
                           switch ((int)com.yiyiaddon.m.b.a<"s2fvh6spgzn9l","iO9XO9hTyoeeYaC8avdqGT//i9S2aqYRWEBbsyd7mxA=",4167645399985748391,6230569299683608010,-8866123791924146637,9069482130702064824>()) {
                              case 1064175793:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.x.player.closeContainer();
                  this.dL = this.a.a().dz;
                  if (this.dS > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"shdwjfn03nees","ljpU6r7GLZ0XfZaTJyIEQ26qDBllzI8kGoJCbI2rvKI=",2037669029272215382,1475021040432194151,-6067930570731685127,341127821665888602>()) {
                        case 156312962:
                           this.dS = 0;
                           this.bV();
                           this.a(com.yiyiaddon.e.g.c.a.GEAR_IDLE);
                           switch ((int)com.yiyiaddon.m.b.a<"s2s2ac2srjwbn2","X2nfE57/yIBmAzNTlXTAjwbrTVKB2SfAMsItrX8uTxI=",6256361017555923297,8302338005884084541,-4251928445584160171,8312570435677539272>()) {
                              case 996817493:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     com.yiyiaddon.e.g.a var10000 = this.a;
                     String var10001;
                     if (this.a == null) {
                        label72:
                        switch ((int)com.yiyiaddon.m.b.a<"s168o58as1zgkx","JgxZdiGUgXOyMyYtBc5TEld5v681OiOLmbbiapX/TTg=",7488788275094908728,7262463271949813191,3554812855058521285,2354624140913457889>()) {
                           case 1771179418:
                              var10001 = (String)com.yiyiaddon.m.b.a<"s261vuemuyzkx8","WaEPr1TiLCoBvUielP7UPAkOxzkVbtzt8YQoTDUveQg6d359",5755066621194425460,3336691400428990782,1390312033153842285,-2348280490489962409>();
                              switch ((int)com.yiyiaddon.m.b.a<"s1ok0t9cipny74","XZwjRhOp82mn6sErfqTDeFnplG8/KCnMAQCAZyBLsV4=",-3402399369012132567,-513460392727466841,-8908415829791214346,4508761625982715698>()) {
                                 case -11848279:
                                    break label72;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = this.a.aJ();
                        switch ((int)com.yiyiaddon.m.b.a<"s1ckkodztqq1im","BTubOtNHH3DU59PE2xy6otbW7xhsrFX2pSGyWQdp+8U=",136960510891603008,-8478000114837480000,265465757152129207,1883625005150461916>()) {
                           case 1484391385:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.b(var10001 + "");
                     this.bF();
                     switch ((int)com.yiyiaddon.m.b.a<"s1zzjv1emrw7j2","h/IcXilncfoGR4HIJVMux79RRoqZN5fJ2nnWOxNxJ8U=",-1170852114888094361,-911819890049203187,8765956576667738061,-1633832230207081815>()) {
                        case -948511275:
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

   private void bZ() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ENCHANTING_TABLE);
      if (!this.a(var1, Blocks.ENCHANTING_TABLE)) {
         switch ((int)com.yiyiaddon.m.b.a<"siwshlwcasnpd","4elOUUraV7QmWWMDrctE+VhN8nfw/iYE+mQZ8omE6eI=",4219207457168340608,4896784916661592210,7319007694221970168,965506524092164048>()) {
            case -752884790:
               this.b.ag();
               this.a
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s17qqnbneto718","ywZ6ADl9uq32Sc51byCuHsHx1aPS02LenUsKRlaLgBgpFckdr6wlAK3uS8invQLeJL727B38sQzkRB/2Hkg=",6325795023546992972,-8715743328713959986,-8252832626219100566,-7504428354463220499>()
                  );
               this.bF();
               return;
            default:
               throw null;
         }
      } else if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g8mfjjenfcco","77aFyCz4aGmgcaan+QjSnvw0ix+Zmj0o8B5o05+sFHY=",5248640213658398651,-4735146685148697940,8562522043800819109,-7134959046985270849>()) {
            case -1769521992:
               this.b.ag();
               if (q.g(this.x.player.experienceLevel)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2yyd3n7ezafo6","m89oXOqX2UyNWuWqLu3Du6xNQJIsuTiyuLFgdauVQ48=",-6098801517421350460,6095220952194660081,-8074566891364011162,133597957185363460>()) {
                     case -1520700380:
                        if (this.a.a() == c.DRAIN) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3up1l039sc430","XLytMr2mBoeAFwF7KpDQAc4GFE+nJhZ2z4kCLgkyQmM=",652067797328218951,5078399114484940634,2038779849876253336,2683116393065652104>()) {
                              case -2060416478:
                                 this.a
                                    .K(
                                       N(
                                             (String)com.yiyiaddon.m.b.a<"s2ptydfrtogkpc","Z6eepE5g28pE34PDRGQG1pFrQyIyt9aIl+gjmgEEgbqzgSe1",7322278536563876232,-5061782391695046327,1666279135377023935,-9127738761511348070>()
                                          )
                                          + ""
                                    );
                                 this.bF();
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        this.dQ = 30;
                        this.b = com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT;
                        this.a(com.yiyiaddon.e.g.c.a.WALK_TO_FARM);
                        return;
                     default:
                        throw null;
                  }
               } else {
                  this.dP = this.c(this.e);
                  if (this.dP < 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2x6kg5ef5aips","chzwZQS4/tegSHH91VM0Yo4Sx3earVFXIcz+VnXWHSs=",5760151457904777864,5909066832851477190,8514539914891552291,5278504709296473256>()) {
                        case -944767492:
                           this.a(com.yiyiaddon.e.g.c.a.GEAR_EVALUATE);
                           return;
                        default:
                           throw null;
                     }
                  } else if (this.b.d(Items.LAPIS_LAZULI) < 3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1e5tk60nfpa4t","J9C6hoqGD0d+DbJCwe1pLJwNXylLppnu4GGy6C4x9yg=",-8097281807150034824,7200508291496319328,-8577870777993486410,8971113874159878378>()) {
                        case 1224575685:
                           this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_LAPIS);
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     this.dL = 0;
                     this.dM = 0;
                     this.a(com.yiyiaddon.e.g.c.a.GEAR_ENCHANTING);
                     switch ((int)com.yiyiaddon.m.b.a<"s361ccydvr2wl6","I9snoIvQbfzskuDz8KZKWPh1xHOVaIAygDv3uWmBAZY=",-1727413394207090732,8770966239049697827,-4860296281629875761,-6147683765213034089>()) {
                        case 80043804:
                           return;
                        default:
                           throw null;
                     }
                  }
               }
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1cd63p5mof1fs","bbPSj9vD3r3J1gL1qjPujpw8/XAhLvp8+Z7x/sj0268=",-5505034125040787291,2405886237031150452,-6816680061842764346,-721699187390891217>()) {
            case 1488423232:
               return;
            default:
               throw null;
         }
      }
   }

   private void ca() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sdovsnaei1sfl","ldzE7US3e8EiObE0v1E/oisBad+b0Gc0/msJQ1sDBi0=",4046027352486351119,-3659494515210973439,-8982464645524846889,6780468716991334599>()) {
            case -1075367243:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         boolean var1 = this.b.aV();
         BlockPos var2 = this.a(com.yiyiaddon.e.g.e.b.ENCHANTING_TABLE);
         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2jdofuz6or2yr","Z6qoHmCgyz7VL+hXUOf7vD01Q3DNfQbyCzrlTGGzJ0E=",-6301839122450174574,2451522421429922991,3831510673057993650,2023771554262990379>()) {
               case -121822628:
                  if (!this.a(var2, Blocks.ENCHANTING_TABLE)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s23a4hyfjelrvl","sDUEAaZ2n6qNZKlK29qiC8TeOIe5JRZNwIoeA1bc3VI=",1253844000463703311,4944651679860143394,7842646609932040862,1927376478261852100>()) {
                        case 1257999501:
                           this.b.ag();
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s17qqnbneto718","ywZ6ADl9uq32Sc51byCuHsHx1aPS02LenUsKRlaLgBgpFckdr6wlAK3uS8invQLeJL727B38sQzkRB/2Hkg=",6325795023546992972,-8715743328713959986,-8252832626219100566,-7504428354463220499>()
                              );
                           this.bF();
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

         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s3dbpshus69um","rRM7awgw4kuZysjsZB55NQWoM35+7EjqzsaaYHrWhA8=",4462811472807823420,6559945403353856166,-356089496797799916,-1318662695906926524>()) {
               case 318324080:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1hofc2auw4rha","cPe4GPQNc3t2QvHORPx5rkkjMvCQYT6XXn8194ThUmk=",-7098945182892234581,1321579915266466502,20364323078541516,2295887219546580223>()) {
                        case 53402380:
                           this.b.l(var2);
                           this.dM = 20;
                           this.dL = 40;
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (this.dM == 20) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1k8zo8efl0bop","P+vDdUbabm07jacVhLuyjkJ1lNGSLtOcfScPpOQN0QM=",8047187806027188557,6934850849323273601,-4032930041828611853,-294650825721707463>()) {
                        case 1897608845:
                           if (this.dL == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2ajynuc971501","r6iwIzZQg7jeeteTSvp0DhMaYU7sNSot/S2vxt35Va4=",-8393962885035959110,5258576155459404970,-708666828336050354,8725919297902852963>()) {
                                 case 347717457:
                                    this.dM = 0;
                                    this.dL = this.a.a().dz;
                                    switch ((int)com.yiyiaddon.m.b.a<"sus3ndgbx1eea","5HRFVuLTOGDwpn2hPldy5MEave4VdAOiiG/5qAMLHf4=",3006789273731412248,557813913451734629,168752602983632361,-7678693790658186438>()) {
                                       case -834511480:
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

         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2yh2c46rnf628","Cy3jDvfUEM0T7J9vcSXBIZ7ApCAx/hdykDgJmoEz11s=",7927654853861577815,2726979605667120829,-4842536801243203187,-120612827884081058>()) {
               case -1098886136:
                  return;
               default:
                  throw null;
            }
         } else {
            label177: {
               EnchantmentMenu var3 = (EnchantmentMenu)this.x.player.containerMenu;
               int var4 = var3.containerId;
               switch (this.dM) {
                  case 0:
                     if (this.dP < 0) {
                        break label177;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1setb3k3sn13h","prmNfYcSBev1AcM55i7sU2DOrkD5IVQOtKgpaYQYlYs=",-9089245733930620923,2789478026545115867,160366616189499996,-1040297279236110221>()) {
                        case -635048561:
                           if (this.x.player.getInventory().getItem(this.dP).isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s216j2v78i7daq","2p7SQk4zsijViSmFcwiEawBQIS2A8ReQAAmGEKHy1FQ=",6564280296418094590,-7356076299223782753,8511857817593343822,2920455049540705422>()) {
                                 case -1209113673:
                                    break label177;
                                 default:
                                    throw null;
                              }
                           }

                           int var8 = this.b.a(var3, this.dP);
                           this.x.gameMode.handleContainerInput(var4, var8, 0, ContainerInput.QUICK_MOVE, this.x.player);
                           this.dL = this.a.a().dz;
                           this.dM = 1;
                           switch ((int)com.yiyiaddon.m.b.a<"s3rgqyf4dwdlj1","SYba9NXftjrS4mocNahyiv5eMHrqS0uRW5HieSNGhQw=",3459800906161129579,1323710667493938399,3402150047672729984,2384068088684476038>()) {
                              case -1689309830:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  case 1:
                     if (!var3.getSlot(0).getItem().is(this.e)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3l626isyjiaro","GOkHfp3sf/ARei2OkBBmJBoHRIVlv6svrpigSgWqqEc=",-8978287925251724165,3170203195107688034,-126676325177407088,7985036441437428415>()) {
                           case 1354559858:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.dL = this.a.a().dz;
                     this.dM = 2;
                     switch ((int)com.yiyiaddon.m.b.a<"s34mgfq5sh31wx","5UBUi5vY9DwetccIb2cGjRqhpg9j2HTh4ZKghjEYt4k=",-156104582528930300,-8250225386663233178,35500884272710015,-6821988867877903337>()) {
                        case -1088247514:
                           return;
                        default:
                           throw null;
                     }
                  case 2:
                     if (!var3.getSlot(0).getItem().is(this.e)) {
                        switch ((int)com.yiyiaddon.m.b.a<"scb8wf2bn7fmq","CkGmmJvx79MttXtvvbOX2GhTsoo/4+GVrgk5GZ67oiI=",9183283166265449916,1973903533577785203,8515792997967641571,6010227765540304711>()) {
                           case -633453909:
                              return;
                           default:
                              throw null;
                        }
                     }

                     int var7 = this.b.a(Items.LAPIS_LAZULI, 3);
                     if (var7 < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3hr3p5aqopddm","OMBw6ThLs9cVyXfXFPy+/B8PYdNHXjNW2xE4k/COL5A=",175657993154166854,-4786935305621486243,-6440837861522007404,-8050648945305927768>()) {
                           case -1126887374:
                              this.x.player.closeContainer();
                              this.a
                                 .b(
                                    (String)com.yiyiaddon.m.b.a<"s1773ucihzv2d","m1QUTSFAZO91CjoohHnCbcMwXaqcMYYGDY8nvSFjEFrFpRwbXY9d1Ni8qF7Dh/K7SZcQ0o29WqKxaedhGksYKnQMaAU=",486305046061293760,3798251877811838286,-3957367204566862417,6198288630339297125>()
                                 );
                              this.bF();
                              return;
                           default:
                              throw null;
                        }
                     }

                     int var6 = this.b.a(var3, var7);
                     this.x.gameMode.handleContainerInput(var4, var6, 0, ContainerInput.PICKUP, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 3;
                     switch ((int)com.yiyiaddon.m.b.a<"s1m4y4c0dsnn8x","UIz5kNIlA1fmICHIOGZieeodREGWp2crwQw1HXCmzh4=",3203750438789174497,3608003934116311996,-8570583518695885216,5106294966746657523>()) {
                        case -667806532:
                           return;
                        default:
                           throw null;
                     }
                  case 3:
                     if (!this.x.player.containerMenu.getCarried().is(Items.LAPIS_LAZULI)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1om4a750caelk","/k9GYTcw9PEDxctNRG2VYGHF5oUVkj+seTU1li+MlLY=",-8502613073559224813,-3406640268812177326,5292588779983060389,6319940736414201908>()) {
                           case -1041625114:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.x.gameMode.handleContainerInput(var4, 1, 0, ContainerInput.PICKUP, this.x.player);
                     this.dL = this.a.a().dz;
                     this.dM = 4;
                     switch ((int)com.yiyiaddon.m.b.a<"s2qtxho4ct0w0c","CcMlDsNDu3WbV9Yk2rseYloVgPRwQX6UlDLg30ubeI0=",5674917910250479250,-3056790854007518987,-4388274923647741174,-6400177737527825447>()) {
                        case -11385716:
                           return;
                        default:
                           throw null;
                     }
                  case 4:
                     if (var3.getSlot(1).getItem().is(Items.LAPIS_LAZULI)) {
                        label137:
                        switch ((int)com.yiyiaddon.m.b.a<"s1utjeu3brkpjd","tThPHOT+iMYRyolV17vLD0BfBE/rD/2+EP1ZoZuC4h0=",-8292043432515603200,5445969834130738839,3893588252177841793,5915006677250493024>()) {
                           case 679943318:
                              if (var3.getSlot(1).getItem().getCount() >= 3) {
                                 this.x.gameMode.handleInventoryButtonClick(var4, 2);
                                 this.dL = this.a.a().dz;
                                 this.dM = 5;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kg0xcm0tomo","2y0Y7lIJV1nOIgkOS1ayAQ6WWUh2XTT021bp83WoSic=",6014271636328787479,6194520455993864006,360579823121682211,-8411345127565609308>()) {
                                    case -837145831:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s16nuj3oc6z0kv","YLmrJP4V0EK1RKEAj9Km2nZoeDJRmeUpjtGDkn3g9GY=",-4616730397134377826,-7872903386983244429,-3604688467209672067,2681189683486308234>()) {
                                 case -894740853:
                                    break label137;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.x.player.closeContainer();
                     this.a
                        .b(
                           (String)com.yiyiaddon.m.b.a<"s2licn5a338udw","rxTv/iQmtNqgdAtKZsgHAFZS4KUB2L4sJUmLir0UCvyLmmDgkn6Pc2HeNFnDmBQOu1n89S05KRMmMcBbsgCRIJipUNAuOiud",6628653752900941058,6826623414925480385,4084014062271664937,9210299074251345654>()
                        );
                     this.bF();
                     return;
                  case 5:
                     ItemStack var5 = var3.getSlot(0).getItem();
                     if (!g.a(var5).isEmpty()) {
                        label132:
                        switch ((int)com.yiyiaddon.m.b.a<"s2owmb0lrkf011","gwXCC/9UaGksePyWm78zjh6TtZhjnNAyHqIrDzhRFkc=",-6681213791351512462,6656988167793061977,-3406832469088213099,-8689577219069323063>()) {
                           case 945250036:
                              this.a(var5);
                              this.x.gameMode.handleContainerInput(var4, 0, 0, ContainerInput.QUICK_MOVE, this.x.player);
                              this.dL = this.a.a().dz;
                              this.dM = 6;
                              switch ((int)com.yiyiaddon.m.b.a<"s9whm6rjqj22r","7tWQsZc/Q2ukiPiWQ0KKrXYVC3j+WxBlP4B/5MXayPg=",2149147389102831928,7172821517993276441,-8872251282579882536,-2400936840253564779>()) {
                                 case -1119275284:
                                    break label132;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2g83lbpaqcl23","ISz6hIROT7CLUvrQmKxOkQAcSTRFigI1KEXDT+L0Vcs=",-3785020699828107873,-3331327227674669096,750161713525200396,5876452792514101344>()) {
                        case 1078050686:
                           return;
                        default:
                           throw null;
                     }
                  case 6:
                     this.x.player.closeContainer();
                     this.dL = this.a.a().dz;
                     this.ea++;
                     this.dT++;
                     if (this.a != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s10ij1o04gm8cl","37DXnGs8PJQAdtz9CB4shI3RhBUvbax8wjT/HItgi3c=",2710905169719221652,-2862462055945563322,-2078190149221885061,749884028377283409>()) {
                           case -545375543:
                              if (this.dT < this.a.a()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3vao9f7gephuk","l7P6E0tkeVonGCEZ1AjC9IIkxlwX+Hmhaf73lmsNZoQ=",7770777332497966855,8565848959193641800,-2200681714395248254,-563707180501107948>()) {
                                    case -1626157373:
                                       this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT);
                                       switch ((int)com.yiyiaddon.m.b.a<"su1brfzj2pes5","Tmvc8hHFxh4F4FkMieCPFA9X2N2hHMB+T3PEJkR6NvY=",-6869346608347342800,3154070470501216441,1395002989453897220,1126273279106396163>()) {
                                          case -1011622263:
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

                     this.a(com.yiyiaddon.e.g.c.a.GEAR_EVALUATE);
                     switch ((int)com.yiyiaddon.m.b.a<"s24yhc81uazn7r","QxlBJkQ3z7VZa/aCgBedj3GUxNKe3HA/HeAq9EIjckI=",5257423152817175582,1472293878057129069,-3241839125925153674,-6803280816723163659>()) {
                        case 1436309982:
                           break;
                        default:
                           throw null;
                     }
               }

               return;
            }

            this.x.player.closeContainer();
            this.a = null;
            this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_EQUIPMENT);
         }
      }
   }

   private void a(ItemStack var1) {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ujx82593ieqn","irIarquNxlev/6aLr29/BkjMldDSQAxZIyFDxkpd9v4=",-684766221276774049,-1114199912598281452,8842782664840356241,970436572457883128>()) {
            case 1692256605:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.g.k.b.a var2 = com.yiyiaddon.e.g.k.b.a(var1, this.a);
         int var3 = var2.l().size();
         int var4 = this.a.Q().size();
         StringBuilder var5 = new StringBuilder();
         Iterator var6 = var2.n().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s32pelyo9os6ua","OGFTZJMjEcG2e0Y43/2THR8yecagNL3QG4HfFjpZVig=",5804793065197351842,-2265482510119598835,-4180537864028903738,-7033856091143472926>()) {
            case 347803172:
               while (var6.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2wz1u4v9hqebg","kbHDbMd3WFRCEFY6De9di2dYKjIp80dyHeSBQuMjtJk=",-1194520106272206719,-6679410125675339423,7533106409573691612,-7192104962339027600>()) {
                     case -842540524:
                        String var7 = (String)var6.next();
                        if (var5.length() > 0) {
                           label51:
                           switch ((int)com.yiyiaddon.m.b.a<"sol5bmql7zxbl","bUZ4WX8PWT1EoPXgkyH7R8EISnjOC3/kneJMyRTjmQs=",7914657948912914487,5188057368034347518,-8370046108016934939,4895490882793738611>()) {
                              case 678456176:
                                 var5.append(
                                    (String)com.yiyiaddon.m.b.a<"s4zbknotvgamg","sDaMv8wQHA+9ygDquUM77E8Nf/xkGnb20h35GapS",164153444873950429,-1075792080654700864,4361872485848814319,1436729156837807003>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s3g3imjtgazlkr","UmYlvfHCsdRjIHvYWkZNO0gNOn5mrslTa0oG1533PS8=",-824188728209057902,6139590526168129069,4677475874505241753,-2736857878603227545>()) {
                                    case 1546763486:
                                       break label51;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.e.g.k.c.f var8 = com.yiyiaddon.e.g.k.c.a().a(var7);
                        String var10001;
                        if (var8 != null) {
                           label47:
                           switch ((int)com.yiyiaddon.m.b.a<"s1gxvaayfkbv95","ihgpJ0Anx3j5pwXmPBon3nAd5L5UGV9xxt1pVg08eHA=",-4298347504469369845,1512711902246359807,-4602289231814035643,-6380291751201838058>()) {
                              case -92808454:
                                 var10001 = var8.a();
                                 switch ((int)com.yiyiaddon.m.b.a<"s18vxerpyvwrhu","plr1+VBLkHhcETYmkB9Bdwi3OetR+Jgnj3z+F4v3OBk=",-8294877059314252969,-8726012042067625962,-4165008723322633951,-4311555549448075423>()) {
                                    case 1829245662:
                                       break label47;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = this.U(var7);
                           switch ((int)com.yiyiaddon.m.b.a<"s20ipp17qxk181","PtM1FERUyPsy1k0+mzAbnmw9poLYHGZD1GohWI1357U=",-3181354627142458622,-5308921569676546417,246832268754954122,4455356601159653307>()) {
                              case 1344787025:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var5.append(var10001);
                        switch ((int)com.yiyiaddon.m.b.a<"s38pkcju5hlevn","x/QIAe9dZdxh1HtoOLzDMy1k0CslD0jM1uQhOL2rSS4=",-7602950329203866132,6663345008479257088,-893970775342080223,1039291299478714110>()) {
                           case -1001242545:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               StringBuilder var9 = new StringBuilder();
               var9.append(
                     (String)com.yiyiaddon.m.b.a<"s3q7ezcvhsw7kg","ZtqfJadpPAkAu5bI+rHEDyR3pmfqKpjKI7wuxtBKVLfXIYP6Ix63hkpAAqXG3XuYSoA1m/ib",2013473170364541489,5461845720119255961,6457730546931908089,2258701189991881460>()
                  )
                  .append(j(this.T(this.a.aK())))
                  .append(
                     (String)com.yiyiaddon.m.b.a<"szar8far07y2l","T7KmZPpmjgA9NPSU4RPDBpn1kP3yQCXQGwMFEgaDo2ylnQ==",-4876270103171100660,-3124442100538581240,3388207804606527803,4738343548433281323>()
                  )
                  .append(i(this.a.aJ()));
               var9.append(
                     (String)com.yiyiaddon.m.b.a<"s3n2h7gqz7t02l","RdSH2fKGPNUYnIk8WeK8i8n62fDVNn4c+ByuME+yyGu6mUaaoOONS529H3S7K6BczVTBmg==",508301197230458030,5293946636802967963,2297868876096864844,4752693873314282906>()
                  )
                  .append(i(this.a(var1)));
               var9.append(
                     (String)com.yiyiaddon.m.b.a<"s2auvfape5z23h","vEyhBnRuONtEFU5+UBJQ4BEpvNQA0jcVfKNPmGSiuWj4BMbHIwSqyiEscVWcMmxtob94nw==",-3623613845592307074,-3934879801858657097,-6380715827926411797,-8471576680051227478>()
                  )
                  .append(N("" + var3 + var4));
               if (var5.length() > 0) {
                  label37:
                  switch ((int)com.yiyiaddon.m.b.a<"s2hmwz9y38a6en","NBwnxVHI8iUI5uGwnhwjsXab/wrpXJWyWWoYCyGtn6o=",-5097941032832900560,293763908525047676,-7135782775745988060,4652264089718915727>()) {
                     case -1518977719:
                        var9.append(
                              (String)com.yiyiaddon.m.b.a<"s753olxoj28z1","aElspZAu7jwHJ7Ulse5BtKpEm69T9qoD/gHbgxP/PuMOpBEJiW8BKy0aIKy7/epcbo7ZNg==",-4886249961834170806,1956836058618004690,-5249763456497375441,8082136935640575628>()
                           )
                           .append(i(var5.toString()));
                        switch ((int)com.yiyiaddon.m.b.a<"s5z9p07t4yqnm","I/r83559wA9Py5mKkOrozqcY/b6fARpBuILesGtO7bM=",78188002933399981,1800162402879291689,-1809483511577548659,-1932041647297951981>()) {
                           case 2117384399:
                              break label37;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.a.K(var9.toString());
               return;
            default:
               throw null;
         }
      }
   }

   private String a(ItemStack var1) {
      Map var2 = g.a(var1);
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s29xjv7fjqi3hs","LLhErJHNp4Yub8JB5Kof7Zt26zio9u1oDpfOnHtfEmA=",-887363385394019053,-2242973453329105948,1132900137370735862,1331621998080748539>()) {
            case 504313077:
               return (String)com.yiyiaddon.m.b.a<"s6n1ftcmwo4px","Ah8CVRL/6h6U3Oha8bulcf+9HJz1LiSkahsIg4Qn",-8196991584315244871,-6278517968305438329,3100036159811237873,1348574200358081224>();
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.g.k.c var3 = com.yiyiaddon.e.g.k.c.a();
         StringBuilder var4 = new StringBuilder();
         Iterator var5 = var2.entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3twyly58uppht","8e0R1kEEOjoztEwj5ARa+9p1I6CTemfRJk12l1StFCM=",1652507259628378735,8700329462926305969,-1661012724410853664,652253456653094858>()) {
            case 38029592:
               while (var5.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27s1gsy79mj20","z7cfXKKpiZJv2aUw9gbTadjNA1oojncLhVWd3jzUE2g=",-231541898917861554,6647152112298505002,2722612451052765633,539669126308342839>()) {
                     case -1133562408:
                        Entry var6 = (Entry)var5.next();
                        if (var4.length() > 0) {
                           label41:
                           switch ((int)com.yiyiaddon.m.b.a<"s1jes3wxjxral2","WPDeMWpCf7OwC7yaEiCg95ou0P/50eSQ/6uAmByJUiI=",1086466155392961570,-1369567559638841978,-6052835276492759565,7220882908374719850>()) {
                              case -1702126579:
                                 var4.append(
                                    (String)com.yiyiaddon.m.b.a<"s4zbknotvgamg","sDaMv8wQHA+9ygDquUM77E8Nf/xkGnb20h35GapS",164153444873950429,-1075792080654700864,4361872485848814319,1436729156837807003>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"sm7bzjl1zh9zm","LgVH7uIzDxlB2owobggHsjvZD1TNZ3uZZmlQYtBT7lc=",4707681837137526244,-6551290680170125153,5694565928884939694,-7403516665396280191>()) {
                                    case 354988723:
                                       break label41;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.e.g.k.c.f var7 = var3.a((String)var6.getKey());
                        String var10001;
                        if (var7 != null) {
                           label37:
                           switch ((int)com.yiyiaddon.m.b.a<"s2qbdcwzf4ssmu","qJJ8WcqQPYG8qfcWDJctOOGjn8P3X2was2MFwz/g2WM=",-8963432914917547077,7946766042511240862,2584558626569877236,1482281273801805613>()) {
                              case -2062969538:
                                 var10001 = var7.a();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1v2dxw4wja5cu","Tnpowx2GxjTG/IqSVZYkvxncwgx7sXqFpuXpWmdT7GU=",6249572287866713207,-2161915120708790439,-7929563308329369571,-3501242980031704463>()) {
                                    case -2046753567:
                                       break label37;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = this.U((String)var6.getKey());
                           switch ((int)com.yiyiaddon.m.b.a<"s1exg3jomou4v7","K7AqVWRUqbwp1rCE+xcJyoddewBeuyYXd4CWcl7b838=",253330754836081967,7065854815015950708,-2384609092238645431,5512759731116196494>()) {
                              case -213910316:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var4.append(var10001).append(var6.getValue());
                        switch ((int)com.yiyiaddon.m.b.a<"s3o7ga0bu4vico","0x8SXrwe5U/6p+lH7/0ixIRB6dAYCXi+DmT7+Q6K6EI=",692165096160998470,-8843448145296722778,-4381483640277521413,-214142996295689089>()) {
                           case -2143582288:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var4.toString();
            default:
               throw null;
         }
      }
   }

   private void cb() {
      ItemStack var10000;
      if (this.dP >= 0) {
         label25:
         switch ((int)com.yiyiaddon.m.b.a<"s1bfbukqur840q","HVhZ/qp5rIt5zpiYs32exjfh3GtyUsHgmxWyydu7TtM=",-5004431848663469986,-4289906800781067430,1311711550686789095,6864892790808187910>()) {
            case -901419388:
               var10000 = this.x.player.getInventory().getItem(this.dP);
               switch ((int)com.yiyiaddon.m.b.a<"s2yy7fph2d5kt0","yp0Qay26Qb/5ZDLIQuNasbJgDSRBPM6/wdYqA7p9esM=",-2354987802917336399,7518459700655893325,-3370066646534113020,-341168031981721399>()) {
                  case -1283620454:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = ItemStack.EMPTY;
         switch ((int)com.yiyiaddon.m.b.a<"s1jowz3hoprhhs","8MEyx6KhhLsdW4iQim5EYw6Vz7Ml+hs2VS8q3owbmlA=",5791117298300970828,5914648043835299924,-416041586571832574,-513317103609913964>()) {
            case -1209850328:
               break;
            default:
               throw null;
         }
      }

      ItemStack var1 = var10000;
      StringBuilder var2 = new StringBuilder();
      var2.append(
            (String)com.yiyiaddon.m.b.a<"s1qutum1cmo6xm","FlpJ/zF4ZbeykD4qrNa/USd2pSVRuGlplknqVLUjBb/v2t+rECR/1qJSCncZs0Pp1qv7oEqd",6616700386371461473,5451616766219953620,337295967505173079,2039678411076627012>()
         )
         .append(i(this.a.aJ()));
      var2.append(
            (String)com.yiyiaddon.m.b.a<"s326dh6rlt0fzn","1p5hicTyQk6rCpBdkFVzX8Td3SPy5x2gAnNTfglk0XXhBCadJgXi1t2Xqyft2HtlKDQ=",-8051962283265319001,7208924182332214674,638875728413579434,-8712527347083381737>()
         )
         .append(i(this.jo));
      var2.append(
            (String)com.yiyiaddon.m.b.a<"s5l6gedpzoo5e","2ek/KQLEH8cwRTLUAJiOE8vNoA0py7w2Ptatxv5eaIktPKFHw0eRZZdtiXo2fD6A",2809073800134954987,8924876415689851475,-1047456585154593850,-5750245596162070521>()
         )
         .append(i(this.jp));
      var2.append(
            (String)com.yiyiaddon.m.b.a<"szqslx5xnvtqw","X2VRpQIFDUlQDpC0ed75hlOP5A5DGm/lhXgYATl2CqBlkOlybM/4Cm2P1jyxcGmp",2389226138028060826,7667860029143371120,3703608736965217559,-44578695116641134>()
         )
         .append(i(this.a(var1)));
      var2.append(
            (String)com.yiyiaddon.m.b.a<"s1qsak8nc22481","5jpCGExNI6EBKcR827LXvNZGRn5rMIVWLdQU9+egOXEuBCCajPC08cG9r0einEE4",8688120920984876506,572448619218216051,3954817791873472608,-9116834205418156956>()
         )
         .append(N(this.dW + ""));
      if (!var1.isEmpty()) {
         label20:
         switch ((int)com.yiyiaddon.m.b.a<"s1ygg6wpa34qfk","opl9YetExNMlmyxVZzJwNUggTkHPo1s9MkuFgAYjnoE=",-2902528909536962512,-3008261888215226133,8221723495815120032,6735681683725703245>()) {
            case -1324694128:
               com.yiyiaddon.e.g.k.b.a var3 = com.yiyiaddon.e.g.k.b.a(var1, this.a);
               var2.append(
                     (String)com.yiyiaddon.m.b.a<"s2wtb3si9iqk3b","lLA95Od77XLudLaaMoguaxhMnGO5wQ/WctC2jOJmtIn2H3kvMamvuQjEDyzQ+T7+",-6556627272532617559,-5994841621948337238,615579321298986,-6883554410470722122>()
                  )
                  .append(N("" + var3.l().size() + this.a.Q().size()));
               switch ((int)com.yiyiaddon.m.b.a<"s5vi0uvphosyj","oPSAN6inz6ehsSidLVtlVPc6F/HKMVLN9cu4zYZBppg=",-119617196900941684,-3379394995577764461,7032652742447781424,-6557916652493514468>()) {
                  case 771713582:
                     break label20;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a.K(var2.toString());
   }

   private void cc() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
            (String)com.yiyiaddon.m.b.a<"shejaz2amd6ml","RXEW/4cSGAMTupee81/serm9bxVzrMlY/Gc6Ymp/4If3TATelE8EFxuhe05Qdh/0kV6BQ9qK",2174664394102267998,-2827975470380754579,1925187086247962759,7403653269188565108>()
         )
         .append(i(this.a.aJ()));
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s1d4kjlbgvyeow","tCfjM/X+REP938UtIrf4GkCncJx6ZxIMMtHlU6OsJK3Wt5bAoFenluuHVS+TTQwKIwUeEw==",-8237807484940495146,4240421329122520670,-8243896447456426955,-1220264637903519649>()
         )
         .append(i(this.jq));
      this.a.K(var1.toString());
   }

   private String T(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gxornc9fltta","AUD/o14qU5ezcLVPflyptKFW6aLVkwoWWXzV1gp8MMI=",3055770021820892724,171194129124985129,-2652149914777209116,-8876717731529146463>()) {
            case 1087196189:
               return (String)com.yiyiaddon.m.b.a<"s2epqb5whafcy7","rpPUzocaujztzo+6cZpEmfWO45qTSXdPx31WYA2ZS1g=",2989126669809101111,2035859477740203473,-5812464827562065307,-3472821637191044841>();
            default:
               throw null;
         }
      } else if (var1.startsWith(
         (String)com.yiyiaddon.m.b.a<"s24g2035m029co","6EwnmVyeYPdJUFiDBaklWf5ItxByhmmZqLTtj0g8nt6ozzvt4MStJQ==",-4239241137524298614,-7435393848763224192,1512269273719423456,8967175685520790272>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"sy7dkttrbab3l","U6ZUyeH/MTg7E1aj5C1BNwVZ8gsBtirHaIbcnT7Q8x0=",-3865827167936444531,-3009194207876005233,7348383567029773538,3402848689034920142>()) {
            case -591710803:
               return (String)com.yiyiaddon.m.b.a<"s17vjg8xbns7gh","Wm6H/5LwsDDIRaUKR6tXRvE025+v1cvXsarDvqVC8p8=",-5836762845854443854,-215257782155133623,5285381568372001369,8330616820769827042>();
            default:
               throw null;
         }
      } else if (var1.startsWith(
         (String)com.yiyiaddon.m.b.a<"s1d3t1ini6ikfa","xZlkkUgUTsnFyzhpmLC53bsyYL5AQcn7+OGaU1SBkcQT5JhgxV0=",1252474141978158644,5463896793654423203,3078693529098285092,3309194426888178303>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kus9qdhw5854","oUzMUvEwQUUbdyEgXF9hDf/tKNAp7y4xgyXtD1U4IAU=",-9075255716834070994,6128649652085781880,-4674326717114293182,4420854496341444951>()) {
            case -1522572846:
               return (String)com.yiyiaddon.m.b.a<"s3p3pgegui1u4u","D7Pys7kPPVwEQQOLmryrhWvw4a73BHNsxLokmmK0PlE=",-8536525194654015257,-7443712635782138054,7334692907677292506,-4823167181310587523>();
            default:
               throw null;
         }
      } else if (var1.startsWith(
         (String)com.yiyiaddon.m.b.a<"s167mrqgzfxove","vq4Xwk0arPpknqDARlBxhPZfKmugcTRGB/OGRBeqP4a9qPNO",9202508826862562175,4072615582113191197,4548122197093733418,2045937623286654687>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s248efuqfy7dqv","KJNoHuprRK9LMtzAjtc/XWPTE1P8rJePHsQrQPoQ+lQ=",-7952255827279277077,-9065993803525823038,8519037675686064257,9210890105234769504>()) {
            case -1158298829:
               return (String)com.yiyiaddon.m.b.a<"s1nhihwaz62ci4","hsk7Hg0KB1dkQP3Chfa+eSMOH20UlC/svw7vlS2ShOQ=",1847127179470206284,-2247678038684990832,-6787403687034961601,6527030620749825308>();
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2epqb5whafcy7","rpPUzocaujztzo+6cZpEmfWO45qTSXdPx31WYA2ZS1g=",2989126669809101111,2035859477740203473,-5812464827562065307,-3472821637191044841>();
      }
   }

   private String U(String var1) {
      if (var1 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"sxkwnysas4txl","YUnS7vWISb8up0PeNyie809gDcMOPcGZePOqAcl6gT4=",1447085756821293756,-1702479900399756657,2307701652382667033,-7766377153588586921>()) {
            case -1662477486:
               if (!var1.isBlank()) {
                  String var2 = var1 + "";
                  switch ((int)com.yiyiaddon.m.b.a<"s26ajmbkklwe5r","XPcKP1ItjMJ2OveYiL2eWMEJbe5QgQobVawEXCvvr7I=",8084787106659735566,-2757366726959908962,936221611609530757,-8495531895314834101>()) {
                     case 381230973:
                        return var2;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2lvzu9tnvxc2r","+LPYclgFiBbTdr8rdxWaDw7T8CPEXCLgoYCtsn8yXyw=",-2888808550636137227,-9133942745102046929,5518710854060406274,8548263676052171536>()) {
                  case -890696249:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s3obzf4s5jy1vu","exBOEQp7r9DwLdzmJlLbacLFkgpcsSjQBs/A+NqiKJvIZmiM",1724445425624965647,-8802227708434073147,-5346770062794180504,3260264540529049668>();
      switch ((int)com.yiyiaddon.m.b.a<"s35m7813g7wqfo","vTisALbXKMdwsY/M/kDe/e2uKGm5SlfvIiblFZuFsHk=",8179389044145412536,3410694963486530565,-6922070522148629060,4448281710904294102>()) {
         case -1500276938:
            return var10000;
         default:
            throw null;
      }
   }

   private void cd() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.LAPIS_STORAGE);
      if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fta9de21d50p","5MJgyiVxw9LvKZP2vyu+Vdt/7mufJjWOzHMSkBXIAKQ=",-428311714659843732,1294087450478890460,1156318973662350605,-5591037276157029191>()) {
            case -2114128538:
               this.b.ag();
               this.dL = 0;
               this.dM = 0;
               this.a(com.yiyiaddon.e.g.c.a.GEAR_RESTOCK_LAPIS);
               switch ((int)com.yiyiaddon.m.b.a<"sm3pyjrgiqr7g","MX2BQL2LX1bk2/5xMSN8EvPOoFShXLpWye+ez4eJX9s=",-5441715372558880452,-4056802108603527059,-1501307814644641904,-7106222485911940973>()) {
                  case -265395052:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s35ckpgeo0o91r","0sS+lTc11vUDMr9XVg4VMMaGu2DAtWDe2aK2Ylwmquc=",-7562999683413963155,6930372669125540473,4198937115960675450,4423558990695592454>()) {
            case 180170024:
               return;
            default:
               throw null;
         }
      }
   }

   private void ce() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"scb56bcebae3r","+XsDD3FftdwNdQ6IgHwMvvbOQ83+Gq6vuqPmSIzsOV8=",7016481549783666987,7083797332851171761,5832472954938300055,-784606690497382367>()) {
            case 1993110903:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else if (this.b.aU()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1beathggnraf1","TOeHX/x6i2bLXULec6KuGtRcPUNxroh7Ra/fe1EfFdI=",8728344718200800888,-30855073956937729,-4501008094791603588,-5306988970787013102>()) {
            case 922582283:
               this.x.player.closeContainer();
               this.a
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s2jbo4g88qenur","Uz+AuVJI6xm+D1UOLGcd8iBc5pc/NQ1XABgDRDZeUt1hRCG1x0rphKihBqHSPkrx0JVBZAN777uKt+RgDgdFl+e7",4653041219164315731,-4313839503318229951,4847313609768719422,1569742466218640842>()
                  );
               this.bF();
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.LAPIS_STORAGE);
         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s13yuuq6sus2ka","btnEKnJDe3w/630ZO+4LvrFaN6NrQ3I13Z+rS+/xSH4=",1439549536818758895,7060748389216655959,-3096018855069873484,8805550949480505220>()) {
               case 1743301261:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1j2k7fx996ywq","a8Kn8XpztsGBR4sy1kmDnlOG18xuLziDBYAZDjXEhuU=",-6685667578765906549,8760418716834444984,280085837145470639,-4234980421074010469>()) {
                        case -1362373963:
                           this.b.l(var1);
                           this.dL = this.a.a().dz;
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

         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s11vdgqu1nvtkw","QD89SCOkfS1WCKf61pZoogNn6orSXdDKlLRfGNmLFEg=",9154369967603911929,-950736789123542654,-8606356582786659388,-5264751628951907211>()) {
               case -293059555:
                  return;
               default:
                  throw null;
            }
         } else {
            ChestMenu var2 = (ChestMenu)this.x.player.containerMenu;
            int var3 = var2.containerId;
            int var4 = this.b.d(Items.LAPIS_LAZULI);
            int var5 = this.a.a().dC * 64 - var4;
            if (var5 <= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s30db606fjh66w","NhKxtj1BpP4/BfFj6y1Y7v5b0nxT8cg7bRSGoYBF5Y4=",1093888136327930595,2963809623687805282,4623972295928907450,-9071303568399310448>()) {
                  case 877314403:
                     this.x.player.closeContainer();
                     this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT);
                     return;
                  default:
                     throw null;
               }
            } else {
               int var6 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"svs1i4ohnb1ec","5tkyXTWSt4f1NcFKQZX2HMEBNWz7v40CUEyX4slPl50=",-5393399952746558230,-4306669303572237104,-8984233574234510599,7623070357603518733>()) {
                  case -155128965:
                     while (var6 < var2.getRowCount() * 9) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2m6pchadgxjul","TwLTbrCu+tPS15+74pjD0bq4icRH/lvp6k4QeRDneNk=",-1303827766734045414,3803036841759837715,3337711558190415638,-1505010876351126637>()) {
                           case -1660975786:
                              if (var2.getSlot(var6).getItem().is(Items.LAPIS_LAZULI)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1cp8sodfxxkpm","qhsq5w1pVt7jRbLwRgKbLLVbhKObukgpnGmUbe+9oIQ=",-3113049016608042884,3310313988393150571,-8625399161752743601,1954994883847999815>()) {
                                    case 1133736432:
                                       this.x.gameMode.handleContainerInput(var3, var6, 0, ContainerInput.QUICK_MOVE, this.x.player);
                                       this.dL = this.a.a().dz;
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              var6++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2plpwy8xs860u","K1jRHRpjePbT+Bai38kynC5Aldz4+dZ3GGj4iqi3RAo=",2710072747145052598,3305228702816670623,6918826274394747574,3077339022184522189>()) {
                                 case -1105869045:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.x.player.closeContainer();
                     this.a
                        .b(
                           (String)com.yiyiaddon.m.b.a<"s2380jhu8qd34a","hrHJXQPGKoPzEtM5c1MEOUOAkcVAz2eoyK/lKLZMYqqhy73uTHw4L0VdhXqNcG7eodrQVhpGIjIiFNIBQ/IxZoUlOOCpXshK8J3L5g==",-5293855451946144988,7593484935357328019,7230087167480135835,-5191422120737257228>()
                        );
                     this.bF();
                     return;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private void cf() {
      List var1 = this.E();
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s27gjks6od6d70","Q1ntn9Mn12xlFHwqxx5WB5Ks/Fl5eOy6X6QAgIrIXvI=",5421700148286703269,-4050777940170719401,-497197829550363837,711098489236847726>()) {
            case -485839088:
               if (this.c(this.e) >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3bn4pwqe1yu8","FUGxKPBeKMnx1dXZzYnvchYQXONHLsPVNjxhg2m+IVI=",2196865344911629924,-5997041465446787139,-691179098743403591,582133717362177526>()) {
                     case -1878585881:
                        this.dT = 0;
                        this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT);
                        switch ((int)com.yiyiaddon.m.b.a<"s13epqk5h0laij","0a61necUdWr0jIyeqz45T21rblVv+IoaB3xhoyaRRJU=",848125844785907761,8677587995449918843,-8202699736084811344,9133191552200496808>()) {
                           case 723336932:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_EQUIPMENT);
                  switch ((int)com.yiyiaddon.m.b.a<"sdq2ww0jnfzsp","sgtQdEqREQ9nMSpv+XwX91hK+rFm6Aj6/E+kotNO0e4=",-6596585650765390531,-6509513338893729776,-8822187250758239448,3825420705641107531>()) {
                     case 1485982787:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.g.k.a.b var2 = com.yiyiaddon.e.g.k.a.a(this.a, var1, this.a.a().a);
         switch (var2.a()) {
            case COMPLETE:
               this.dP = this.b(var2.j());
               this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_OUTPUT);
               switch ((int)com.yiyiaddon.m.b.a<"s1s1gkdzs4ztip","qaiDHw/hqMEHjajaPjVUK8igu58nN13cg6jlUrKNyqE=",4271853061403773384,6717608178131984680,1324872804499024576,-1046691428275939968>()) {
                  case 766336889:
                     return;
                  default:
                     throw null;
               }
            case UNREACHABLE:
               this.a(p.CANNOT_REACH_TARGET);
               switch ((int)com.yiyiaddon.m.b.a<"soeofnyuf90z3","zJ+G9dif6mmc80zz/9jHwYQTtW9V9PBvYCtPgIIE5Z4=",-4897656730932363496,8434763539067148788,8869509257104200026,6060815249968641503>()) {
                  case 1339039673:
                     return;
                  default:
                     throw null;
               }
            case ANVIL:
               this.a = var2.a();
               this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ANVIL);
               switch ((int)com.yiyiaddon.m.b.a<"s16ntzy8rt0vnz","CNRT0+83OaYygVqWXzzTvffI0opW5cQRNHpC10Zwphk=",841223244261485621,4872361844040775889,4181803031522548822,7711788966884908017>()) {
                  case -698676516:
                     return;
                  default:
                     throw null;
               }
            case GRIND:
               this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_GRIND);
               switch ((int)com.yiyiaddon.m.b.a<"s37hgp5pznoxxy","W49hoem1DDiVx6GmLYHjv9AY9RucYorGb4uocEfl7Ck=",-2504055347442470928,-7486257516787050466,8093769222520038775,4880497148464639090>()) {
                  case 448341248:
                     return;
                  default:
                     throw null;
               }
            case CONTINUE:
               this.dT = 0;
               if (this.c(this.e) >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3aytfhijeeykb","s2f/rSmjpHPBCcvvrXRG4hOmYrOjqJINu9LchiBQWPs=",4857524522087747001,-5988088007225107868,-9023130310059258313,8154742728201034295>()) {
                     case -1951964145:
                        this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT);
                        switch ((int)com.yiyiaddon.m.b.a<"s2t1znjredcke9","aC4Lu7JXLobzY3IdZkVenM6Ue32LcFwcsc5FTdQYdYY=",-8287958542451113009,556483955702148902,-8379760065214512207,-3834519029436239576>()) {
                           case -70408995:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_EQUIPMENT);
                  switch ((int)com.yiyiaddon.m.b.a<"s3jpsbisp6djfv","1UgApsBf6ZX0YPyTc78jN0lrZKXwHxhwISP9T+DQGfg=",-6619444584533539928,6379884144466137117,-2264889515048030610,-585231412807503420>()) {
                     case -2107913873:
                        break;
                     default:
                        throw null;
                  }
               }
         }
      }
   }

   private void cg() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.GRINDSTONE);
      if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s6hjou0zu45rw","jHwuvZHChSGHv2Qxlc49xO+KNv+4yIVnwLJn4/CcIlQ=",8648803581519097042,-5212006584650379547,-8269823549469107758,-536503381193415006>()) {
            case 1001055592:
               this.b.ag();
               if (!this.bk) {
                  label47:
                  switch ((int)com.yiyiaddon.m.b.a<"s320f0yf8pr72j","dxlN01hD5DLXk95u+OU+XrY5Bxo1rbq+DV/p59skvwo=",-6932798003116009023,3295126803029610869,6408984931488344168,3243955365261099163>()) {
                     case 43191261:
                        this.dP = this.M();
                        switch ((int)com.yiyiaddon.m.b.a<"s1usfcl7ouqnei","qrlea5hs2dIPq59WxMmDKIKAFxctOcH0BGDpvfKPutI=",1650761084468447614,54739652722218603,3470973384512438586,1793779554708166684>()) {
                           case -648222523:
                              break label47;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.dP >= 0) {
                  label43:
                  switch ((int)com.yiyiaddon.m.b.a<"s1axse2tew1l8k","nea2trM32WaLos+h1gYO9wtZVWE+nD/Hq2Uxt8QSgQI=",-3731547924382113693,-2554120075086566454,2941727403682227628,8133041531085921730>()) {
                     case -1930908919:
                        if (!this.x.player.getInventory().getItem(this.dP).isEmpty()) {
                           this.dL = 0;
                           this.dM = 0;
                           this.a(com.yiyiaddon.e.g.c.a.GEAR_GRINDING);
                           switch ((int)com.yiyiaddon.m.b.a<"sshzkiy1y2v53","T79qD1Own2UC0ySm7PNh6/bi9JunpMzQvEXO4gOhl9E=",-7594975027315509711,883187079810051834,4725313460846665515,-5212470008075448224>()) {
                              case -545708921:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1dj1o2hn9po3u","2/80TrnAXGlxjGs5e26NNzQH2/Ryu1OTpUYdnQcrFVY=",1200843469232309627,15199116833687931,3464430685782398656,-1707004674231801636>()) {
                           case 1957346447:
                              break label43;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.dT = 0;
               this.bk = false;
               a var10001;
               if (this.L() >= 0) {
                  label34:
                  switch ((int)com.yiyiaddon.m.b.a<"s1yphk3q6mmxuf","uUs9mHV0uiSkKs6Xv7SKNpbXY0VanMl1ujsPk9RHRJ4=",4314340978443051318,6640853395405709283,-8126961904246030613,118869334092998292>()) {
                     case 1477282096:
                        var10001 = com.yiyiaddon.e.g.c.a.GEAR_EVALUATE;
                        switch ((int)com.yiyiaddon.m.b.a<"s1pakqdandpbkq","LNimovrSUNyZfLyGNAK3Vs15PQOY8e2U9dxaTMQeDG0=",-4334440770820631322,-5090093547693713849,-5529489397961451993,3810084446527248913>()) {
                           case -770835594:
                              break label34;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT;
                  switch ((int)com.yiyiaddon.m.b.a<"s2l98lpld96abq","F5L8Oa607DNfS2x0+aygH874ltfOqFdpi6lICXsMURI=",494406833977095013,7089808556309547365,4475409738942715731,8675968733987540541>()) {
                     case 1932011944:
                        break;
                     default:
                        throw null;
                  }
               }

               this.a(var10001);
               return;
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s10u190tuteuxc","Zxl4y//sxkPlj6UbVgbMlBNVP5tkx1M0UmdH5Hnll/s=",-4155649743639652469,2183705159168338046,3412133832905329311,-5106283043779868683>()) {
            case -1471438003:
               return;
            default:
               throw null;
         }
      }
   }

   private void ch() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sqivsijp5hhjo","rj16LOwKTDxQCENF4E7mo2tyX/N9hhqt40QZFWk3kS8=",-5822306549405977417,2520325544841938013,5597174337684939884,1893654228757020384>()) {
            case -116218984:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         boolean var1 = this.b.aW();
         BlockPos var2 = this.a(com.yiyiaddon.e.g.e.b.GRINDSTONE);
         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s3sltlx8yzm9we","So9dcEBqhgBicg8C/PqenY0MJqPUppr1QywbxJJhg3U=",-329885077198791257,4871180399518853444,-5198282546001916720,3290581629762837961>()) {
               case 1331088608:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ziucwuzctne3","FQZyvVd5WpKO8r8QG1z5rhSX67yVKtT9K+szB94qvKk=",3433263010325851129,2446157471272518357,5319324559736855727,3583849343283603263>()) {
                        case 745939353:
                           this.b.l(var2);
                           this.dM = 20;
                           this.dL = 40;
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (this.dM == 20) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2nmjixu9l0gok","Ge6Vrqk+d/aBAkNcgyu1Edz/lzD/mWEkUVARCKnjX2A=",-2463240519321514201,7211010050149499783,-5109030552544782874,-6730983037416398002>()) {
                        case 224581926:
                           if (this.dL == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2oueni907vbql","OtOfM/zPDOZ98KDL3mR0sUGlB7KxYZyJhKHAfkGKwek=",-7498611393926617103,3275967206177644631,-276015754138839171,8309688848765599328>()) {
                                 case -1976247752:
                                    this.dM = 0;
                                    this.dL = this.a.a().dz;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2z51jxtcahd56","9NxuEcX0Ml18CiexZZAcErHknUPN9BVOCAlpOctDukM=",-3839120598825932803,-509330466425526167,-4122211046624038642,-5940917183521028562>()) {
                                       case -1589184397:
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

         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1h41mouu1ncye","BbK+LjLuHfrRydv+b6d6rTVgXiPZxASKNS7ADa3VVrA=",-8461432959236364214,-202720214455087299,-4966051349916502307,-4292380414587663539>()) {
               case 1985257730:
                  return;
               default:
                  throw null;
            }
         } else {
            GrindstoneMenu var3 = (GrindstoneMenu)this.x.player.containerMenu;
            int var4 = var3.containerId;
            switch (this.dM) {
               case 0:
                  if (this.dP >= 0) {
                     label89:
                     switch ((int)com.yiyiaddon.m.b.a<"s36a28xmlp64yj","dlh691kQbTjHM4wBVIwV1N1Z4JVBlndc9aL936a/y8s=",-4215029307427568223,153484671141562606,4104133572801430391,7663754891365926844>()) {
                        case -1763036136:
                           if (!this.x.player.getInventory().getItem(this.dP).isEmpty()) {
                              this.jq = this.a(this.x.player.getInventory().getItem(this.dP));
                              int var6 = this.b.a(var3, this.dP);
                              this.x.gameMode.handleContainerInput(var4, var6, 0, ContainerInput.PICKUP, this.x.player);
                              this.x.gameMode.handleContainerInput(var4, 0, 0, ContainerInput.PICKUP, this.x.player);
                              this.dL = this.a.a().dz;
                              this.dM = 1;
                              switch ((int)com.yiyiaddon.m.b.a<"s2gt3sf0oeylk3","JeLzcybGkmDGFFWxWMpXKpUjLmLNwHjSh+ZOy18QSyg=",2489351257873980263,-6009529084574167473,-7396398025518351028,5089594764400152302>()) {
                                 case -455470728:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2cwd0rvu5etfn","R6hiHjSYcSa8QmOMpgamtUhuKmJ4I8NIhK2JgXVQDAg=",-3830477576869078852,-8726834697371541876,3856136197791610239,6943249158380714755>()) {
                              case -2125415909:
                                 break label89;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a(com.yiyiaddon.e.g.c.a.GEAR_IDLE);
                  return;
               case 1:
                  ItemStack var5 = var3.getSlot(2).getItem();
                  if (!var5.is(this.e)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3rna4h9indq6n","pBh+RmQmY7aI9dxCaQlP9LWt32U8GE5hg7Yh5aurSDE=",-3286947650494624575,8442366024027670004,-4284620739203464848,-7736291533858448879>()) {
                        case -630928373:
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.x.gameMode.handleContainerInput(var4, 2, 0, ContainerInput.QUICK_MOVE, this.x.player);
                  this.dL = this.a.a().dz;
                  this.dM = 2;
                  switch ((int)com.yiyiaddon.m.b.a<"s3k1mw8bvvrdg4","xu88Js0yd5oHilydB4nh8f9EYxT6ilA4MX3ZFCTKaj8=",-9022171647776490416,3829325425554204269,-6011015714228513311,-2679621580471557713>()) {
                     case -1175900039:
                        return;
                     default:
                        throw null;
                  }
               case 2:
                  this.x.player.closeContainer();
                  this.dL = this.a.a().dz;
                  this.eb++;
                  this.cc();
                  if (this.bk) {
                     switch ((int)com.yiyiaddon.m.b.a<"s11494iantt25p","VoZbgBPVjMyrMHXFXdJdCYUnRbxQP3fAX+1mLaE+5Uc=",4982614180455856543,-2915707130903357699,8133773183049940709,-5435994567585136576>()) {
                        case 242477811:
                           this.bk = false;
                           this.dP = -1;
                           this.dT = 0;
                           a var10001;
                           if (this.L() >= 0) {
                              label109:
                              switch ((int)com.yiyiaddon.m.b.a<"s1mt179yx9klgn","FN0lFGnNzFjUZAKaaiHtWWE5QElU6uTmuyWGkDcOYKM=",-5743284472582670878,-7792148535098049511,1210193539404712624,620251388416050674>()) {
                                 case -612756280:
                                    var10001 = com.yiyiaddon.e.g.c.a.GEAR_EVALUATE;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1e6sftvyyld23","ZZpSX8BZMtfPdYXXWjDdOQvusi6VZ88F8q3sPGjIFRA=",-5294384091502582092,-4181472245337130422,-1613665074297778949,4321648157401243437>()) {
                                       case -559953898:
                                          break label109;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10001 = com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT;
                              switch ((int)com.yiyiaddon.m.b.a<"sc4ok1ru072d2","rhW+Xku+BLcN019uBhvKuZWFpjYkmd6FYWo05vY6tR4=",-8338792219295993852,-1194082152612737184,7886255108225883216,6773293904451917882>()) {
                                 case 606102494:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           this.a(var10001);
                           switch ((int)com.yiyiaddon.m.b.a<"s279vkl2arh7vg","YBCFbSTkF2HYai2vv29T+MJ5GU0GeXa9PCvRxqtpYHo=",4495160410737966940,859850093143239332,2420269559547421918,2962825964110355713>()) {
                              case -2072309637:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.dP = this.M();
                     if (this.dP >= 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1unp5in1lcgb1","5RrYp50pz0jQHG8qpQVFhUtaJ3kOwDKDq+Lm/zG/Yvc=",4128295091820111588,2057601575031135877,7698737094032311581,-2004956383042401284>()) {
                           case -1812117570:
                              this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_GRIND);
                              switch ((int)com.yiyiaddon.m.b.a<"sa9wqg057ewmp","+8vH2keOHyfU3P+YWXazPnCCFr475+Da/MVNR1hgtag=",-7522242096298470700,-6821297116271637751,-5557016251178735712,1936382974020832422>()) {
                                 case 2087299605:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        this.dT = 0;
                        a var7;
                        if (this.L() >= 0) {
                           label101:
                           switch ((int)com.yiyiaddon.m.b.a<"s2p3vkuz193kjg","hwl10xQ/p/pbCB9bRh/qqGDPSi6O8chPEK/BnZ9t+T0=",-2417262555613824316,7667930743990268057,-5471902990965613706,7822702355149563319>()) {
                              case -975759444:
                                 var7 = com.yiyiaddon.e.g.c.a.GEAR_EVALUATE;
                                 switch ((int)com.yiyiaddon.m.b.a<"sskzvak0k6pch","vvpGxLi9yVCdef5YUl17ayTIT9AUUti4NofxIbnBHi8=",3425350069298373425,-4373013466533775712,-1027021093754447882,-5348178799765064630>()) {
                                    case 1111240731:
                                       break label101;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var7 = com.yiyiaddon.e.g.c.a.GEAR_WALK_ENCHANT;
                           switch ((int)com.yiyiaddon.m.b.a<"s2l3txcep4rjnr","5RELu9POFafM/Cd7or1WhUzNA2c0ncz/HpiSCnd5A1U=",-732282224027784247,-1133477286626628209,-807764510599425305,-3960268001439851361>()) {
                              case 216706565:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        this.a(var7);
                        switch ((int)com.yiyiaddon.m.b.a<"s26ms27mg1h7kw","N/SgQETb/NYJWplqVt0+PeWmcSFJONp8XG7oUmktAjc=",7807814933366004509,3146724812952326876,-1752708170566795960,7779894119309310116>()) {
                           case 257698359:
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

   private void ci() {
      if (!this.l(this.a(com.yiyiaddon.e.g.e.b.ANVIL))) {
         switch ((int)com.yiyiaddon.m.b.a<"s21m36jdjf0x12","Hdft+4vE7fNIBwBoGwSMmFDuJk4oXNfMAqui1/3nNaY=",-4763845207095554713,8455139752299177907,7876756460108644616,-5067633992452723342>()) {
            case -301079232:
               this.b.ag();
               this.a
                  .K(
                     (String)com.yiyiaddon.m.b.a<"s34wj0ji7db952","9ceoMAJ66+FsJMSCs/kfEKW7s0v9dTmyTrgN2xnr/bfuKiDTIjxvXMrr+OQWRdpb3fhqiZBFWV+uzzgSz14EP9rxXdQYo58q",-7177009786427615756,-186693964490424956,1635536090765368176,2005571557308250573>()
                  );
               this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ANVIL_BOX);
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ANVIL);
         if (this.b.m(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"sntveeqp40ihd","mbovcfh6cyOFKHPv1KoiDhCBvYCEMHILB8JF61+eDPw=",2722259942346849946,-5747409717675778013,1296474492781290530,2275985328540266115>()) {
               case -2136080936:
                  this.b.ag();
                  this.dL = 0;
                  this.dM = 0;
                  this.dR = 0;
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_ANVIL);
                  switch ((int)com.yiyiaddon.m.b.a<"s3b7mp5b4gcsdc","HpFG+bfWunrO56ORk2tZA+KPAgGjot3Y+24Y2Kk4xhA=",-2228845814391597518,-2116633438620905200,-1056662201599160768,-7854008645031064814>()) {
                     case 1083491339:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.b.i(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3tqpy4mwxcudx","SGGXKOH5gfQCFhcl+Y69gJI/UYOsp5V1Q8bpL0qTtyQ=",5857844173379850057,-6705719133294733420,6168606379076940393,-6705468159264186468>()) {
               case 1507967883:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean l(BlockPos var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sx4tnfzqqbnk","MwfyHm8WdaNKJllSKTB10omEL9rgmCu0EAqJFA6f+Ww=",-8332164231588270445,-6882802090288908855,-3011672455387480507,-5187044624861874792>()) {
            case 1516537104:
               if (this.x.level != null) {
                  Block var2 = this.x.level.getBlockState(var1).getBlock();
                  if (var2 != Blocks.ANVIL) {
                     switch ((int)com.yiyiaddon.m.b.a<"s21x1zqgwjb487","bFt/fZgvBC+7FjUTqw3CpK7dh4G0XW3W4Vrg+xnK3Aw=",924109240315830056,7532280589952836093,-5017761225144067268,-4955673663560273140>()) {
                        case -967765188:
                           if (var2 != Blocks.CHIPPED_ANVIL) {
                              label32:
                              switch ((int)com.yiyiaddon.m.b.a<"s2427xew31d353","X3iam4ipGaTpn8q/HlEj+n1h5aArxCnsDgdOLhj+r7c=",-9080518153567126799,289501745184742982,6426355098929875944,3396373743779967875>()) {
                                 case -605254941:
                                    if (var2 != Blocks.DAMAGED_ANVIL) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s10awa57b7b2yp","4eW5xc3pHcXOd3PpmJsU7M71ePmVe8/Fkx3ktkqETdk=",-4115099015075846355,8997207572684705243,6484512734031300528,-98013342562290303>()) {
                                          case 1149717544:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1c98dik3ndhbl","Ldqkfk6HN1PQccFNndJ06AFP08sr7lX2tMnXhy0P8HM=",3147484832092428386,6940689003480882402,-7722358449360762633,2207559532947241486>()) {
                                       case -728292840:
                                          break label32;
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

                  switch ((int)com.yiyiaddon.m.b.a<"s4a47aoon7xdq","arkbFeAqufK93IPuRQCqFqXqoeQ6Er5RBnUwAbCDsbo=",6317591919649119255,7775300758908326965,8427990914132407950,-6400861938111770269>()) {
                     case 768858791:
                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2z6v1emybukna","GLjI3ohGdKbj4tWZafGSI/Fpf9v/vQW1XPUIkVWRdnY=",509106124780425960,7811434918294336900,8817405995793495578,1379591209948420937>()) {
                     case 1679266910:
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

   private boolean m(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 != Items.ANVIL) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dkhpof7m71un","kJjfe7XcZb5UrzLvB757GQvaZBqlPj5s6quOlqdsPms=",-2642101147460137378,563458862839779006,4789497828265706759,9095265677684258561>()) {
            case 1415487759:
               if (var2 != Items.CHIPPED_ANVIL) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s1d7fqo14smqnl","3fjR2jM/T73DWW27DKFAtlP4AxY3wTN2qXwZVxPSdkw=",6665482768091432856,-1701976728359290108,-8326863421727580700,-780603807788608638>()) {
                     case 2064243332:
                        if (var2 != Items.DAMAGED_ANVIL) {
                           switch ((int)com.yiyiaddon.m.b.a<"s365llbt6clatr","BLQzjYAjLi7MqUURfIniQlSm5yn9DJhR0Uw9Gv4R+iE=",-3962268543853712784,5282774173606784416,9156534212584353702,-6440814909556818871>()) {
                              case -2070199495:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s28jslvs5zrz57","G/ytcp8M/xFAgQluQ4MaLg2xuUa3wGv3HW9eBRQpfFQ=",5076277052237788171,-8606301681751128318,8675975263004733451,-2277872304026659169>()) {
                           case -418891831:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2tklmy4vnhcbd","2CK1bL19IQde8g50ll7/rP6VInqqkZje4EsnVG2uPTw=",-7094779256896515574,-7283539094323250486,-1088799945059008633,-1013747770061429936>()) {
         case 2081641997:
            return true;
         default:
            throw null;
      }
   }

   private int Q() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2f9cuskz2o544","OOsvPAgu0H0dd3DC/g6ZIFuWv3IdsnQjNfKenrR3Uyc=",-6124395893665051068,7450432273515614666,2737389849207980744,-8002949422864806855>()) {
         case 647700444:
            while (var1 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s1hx709br66okf","p6pdvEg7bl9IwLmVvjLtz0y3hefsvUtEpBxfBmeEY38=",-5560072360347714402,940755338911094677,2665820803457792551,3875845108226861770>()) {
                  case -875860586:
                     if (this.m(this.x.player.getInventory().getItem(var1))) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2zgv8m979g9ke","M4WUuPrz+MVM79Ls1H5KUK011EUHwbdEbyi6G6RJ/dE=",-7822704771349146478,-9162725603116367808,-3498258772327670325,4872556865833564646>()) {
                           case -54494588:
                              return var1;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2giqkufuc5wz","UvsBIiSiiJ1S0GEfrDhKDJi4MBqGyNQ1r2ykGty+Fgk=",464168741241556585,346089251536352093,4689915537638677468,-903913585114443009>()) {
                        case -953058994:
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

   private void cj() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ANVIL_BOX);
      if (this.b.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1szzxj621wpss","xVf05SgFvpbD57KIhUZxtwdA+SrqiQFJuZMphwrwLqw=",-976926361776156143,-2492099603617582505,-7376978491155827451,-6050570056307767618>()) {
            case 69810011:
               this.b.ag();
               this.dL = 0;
               this.dM = 0;
               this.a(com.yiyiaddon.e.g.c.a.GEAR_TAKE_ANVIL);
               switch ((int)com.yiyiaddon.m.b.a<"s2vc5q30y4dmtb","vrKzMiMHxUMBLEt0Gw9UWvnHw3eVvljdl1t2eIWfyTk=",7134970353988852149,-3191004775093515736,1802577243083281464,2611859132926689403>()) {
                  case 204695112:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.i(var1);
         switch ((int)com.yiyiaddon.m.b.a<"sl6aynycqkpud","fl5mYbZpMah0SL7DJPTiYJsC6HzsEspc+wo31pHq+cI=",-7690947792052079302,-110598062119441602,-8532874225629821265,5587228749217852526>()) {
            case 759009624:
               return;
            default:
               throw null;
         }
      }
   }

   private void ck() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s31s2g3l4u8aul","4Wltd7rUq9x+KUHqxjOm/CJJY8SPH+6WYze3yfpKcoo=",-2007112551462939745,1853706382736696968,-827475483857812042,5435820654379975581>()) {
            case -1296591950:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else if (this.b.aU()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qlbmosgjc1j0","ipk3akheUhkQZHsuSV8uKqLvDJNZfU8etnpw1vFfl0A=",3335278780948266831,-6050723369685787626,-9058362448358321350,-4245957293977562705>()) {
            case 992403320:
               this.x.player.closeContainer();
               this.a
                  .b(
                     (String)com.yiyiaddon.m.b.a<"sj2aua2po5h2x","wdfxjr7DeTuhO4UJej6Sg3fuX6ziwq3bjRhPnCnUzG/xZRXpV4zHqmsBk5fcqTdvAim2Qz6SL5kgHSW7KMU3tA==",5570253302778530727,6336210729545472682,-2542021236968170343,1913081502342182090>()
                  );
               this.bF();
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ANVIL_BOX);
         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s299xx3lllxmjg","J7mnBybCEnUds+o1hkk4V59PzRlUVhzHXov0JDHFM4M=",280655222664796728,-3314452282042286168,-2640580144559004986,-3251322897920928430>()) {
               case 1329497035:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1tix6l3tkpm5z","MV9whAYZZSLKhm0IL613GoWD7WRJYJ3QMeym6AYsXE0=",-567148830886524296,-4073162564188625370,-5928670618719428433,5486004828877610077>()) {
                        case 1230171189:
                           this.b.l(var1);
                           this.dL = this.a.a().dz;
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

         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2r6paaz6u18fq","5zHFlaJ+YIyWdbWjXqKYaIMdEZ2tUVT81IkF8YECln0=",-1765414476465408272,3554324368407295154,-1278177655092323208,5390263553222286953>()) {
               case 1815031388:
                  return;
               default:
                  throw null;
            }
         } else {
            ChestMenu var2 = (ChestMenu)this.x.player.containerMenu;
            int var3 = var2.containerId;
            switch (this.dM) {
               case 0:
                  this.dV = -1;
                  int var5 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2yirw5sx1z3i8","PS+iLlOcb2PYx93RdiWr/PNhI7pEBwXuWcgnGQYzEjA=",2120314944364206068,7461907602089188500,-3989653441456655021,-5670154480432124280>()) {
                     case -164902453:
                        label91:
                        while (var5 < var2.getRowCount() * 9) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ny3zssvhgxio","mhg2x/fCYfGW9NdFT/uPMfTSSifhm6+TQdHyeDS2TiE=",6719804745907972246,-7328796505894509525,-4521789462757065177,980327106685708080>()) {
                              case 1329018173:
                                 if (this.m(var2.getSlot(var5).getItem())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sp4y2nkk14yeo","O6QOxq8WFtmevb56M3NTNpGgTlgdpPw+rlSVX5DpFek=",3747652945088049195,1393182938077742114,-1782672610835477918,3789878229838400644>()) {
                                       case -1532528918:
                                          this.dV = var5;
                                          switch ((int)com.yiyiaddon.m.b.a<"s27zyw7o93ae1m","alejKpwx0sQDAygIoQukEx85z1cyzBYyEPbMW+Rx7iE=",8344554485367552960,-234860235641814100,-9060760116995916791,-7921256615363322963>()) {
                                             case 1603613074:
                                                break label91;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var5++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1biq7yspromht","4UNTtOoV850/bwv4Lkas+6OV3EB3BvvP70wpXfx3bIQ=",-5155353499931859799,-8630996108710046899,4732748814522377577,-3720791032335799054>()) {
                                    case -799054236:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (this.dV < 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s35z65fry8tb6g","57/QERC4e9MkdfmySc9Ugq4FElmKR5BGk9MTrIx4ofg=",-8131300884670821043,8052021678571446745,-2590899754457167312,-9002958524562776856>()) {
                              case -1578892420:
                                 this.x.player.closeContainer();
                                 this.a
                                    .b(
                                       (String)com.yiyiaddon.m.b.a<"s7r9ib98hn076","kfh75ZmwazKLxFjkxn516yr3Bx5pJB8fVHpfL1Pi4v3ZQraH9rquhnWGuzFdxj6FkZOx/j0RySYSR3Yil6+GyTjVhMuOFFJDVZMkrlmfncEnZiK+",-7401959634145242950,8906445507315813407,2452750026532731564,8837927551136533334>()
                                    );
                                 this.bF();
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        this.x.gameMode.handleContainerInput(var3, this.dV, 0, ContainerInput.PICKUP, this.x.player);
                        this.dL = this.a.a().dz;
                        this.dM = 1;
                        switch ((int)com.yiyiaddon.m.b.a<"s3i17gglqnnjxq","dnnSw3dCBt1IhixQbxUy3rT++JIAGk1XVbN0Uy9e5qc=",-8759780567072815463,-9016034865458129451,6137316089620464490,7814013117346697653>()) {
                           case 1813424757:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               case 1:
                  int var4 = this.x.player.getInventory().getFreeSlot();
                  if (var4 < 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s12cpipa63a7ti","Rl4isxL0nbnMzhVl0HBjXhbgd5kVYwmNsoyDIz2Hvzw=",4116281186040392272,2041575869458270956,3592952688898334576,7971807307180718186>()) {
                        case -1859004523:
                           this.x.gameMode.handleContainerInput(var3, this.dV, 0, ContainerInput.PICKUP, this.x.player);
                           this.x.player.closeContainer();
                           this.a
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"sj2aua2po5h2x","wdfxjr7DeTuhO4UJej6Sg3fuX6ziwq3bjRhPnCnUzG/xZRXpV4zHqmsBk5fcqTdvAim2Qz6SL5kgHSW7KMU3tA==",5570253302778530727,6336210729545472682,-2542021236968170343,1913081502342182090>()
                              );
                           this.bF();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.x.gameMode.handleContainerInput(var3, this.b.a(var2, var4), 1, ContainerInput.PICKUP, this.x.player);
                  this.dL = this.a.a().dz;
                  this.dM = 2;
                  switch ((int)com.yiyiaddon.m.b.a<"s6nsy7baxfbeu","PkDKjrV5Ubtl0LVUGARU+2VfMuKZ/TZ3JXnzwKXKhAI=",5270178843945545589,3735283152915425725,6145528414701297951,-7470272039949032630>()) {
                     case -414859044:
                        return;
                     default:
                        throw null;
                  }
               case 2:
                  this.x.gameMode.handleContainerInput(var3, this.dV, 0, ContainerInput.PICKUP, this.x.player);
                  this.dL = this.a.a().dz;
                  this.dM = 3;
                  switch ((int)com.yiyiaddon.m.b.a<"s18f63is05e1hg","cgN90AaIQneNh4ZaLhmQ5tWzJFeCg4c3MWyDPIeWdTM=",-5395901657157315301,-8327356932161580320,1940442291759259417,806488378666819564>()) {
                     case 394822304:
                        return;
                     default:
                        throw null;
                  }
               case 3:
                  this.x.player.closeContainer();
                  this.dL = this.a.a().dz;
                  this.dM = 0;
                  this.dV = -1;
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ANVIL_POS);
                  switch ((int)com.yiyiaddon.m.b.a<"s1o2ats4hriay4","qZo8Qm4RSA2j3KYl+HfJBaZiGso9ipYFo45YQG9Pmi0=",-684412879287182391,7102723537255517946,786746599308084301,-5785394353889719006>()) {
                     case -1054298910:
                        break;
                     default:
                        throw null;
                  }
            }
         }
      }
   }

   private void cl() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ANVIL);
      double var2 = this.x.player.blockInteractionRange() + 0.5;
      double var4 = this.x.player.getEyePosition().distanceTo(Vec3.atCenterOf(var1));
      if (var4 <= var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dq46e4u9i8mz","xb4G6KsBoOl8HbGsyE6mfepaXRDWLRIbA+99LCw/WEU=",5439504867782534783,3003471141118920233,-625724238986550695,-4615304681358982379>()) {
            case 1370823069:
               this.b.ag();
               this.a(com.yiyiaddon.e.g.c.a.GEAR_PLACE_ANVIL);
               switch ((int)com.yiyiaddon.m.b.a<"s1855jga345iaf","zvPn+3OsGmoQteWUPolRSD4KiFMLUuIgypOJMb8XmuE=",8381794418494061293,-4286374806849076833,965098067509565971,-6852946260792249494>()) {
                  case -1494340696:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Direction var6 = this.a();
         BlockPos var10000;
         if (var6 != null) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s18tf7aicktdam","9RpcJ/3Bp5MwrnIKD8lWS7u9w1M7IOQ/mohPWXGer0Q=",8828658203882204820,-5891287042795846109,8297059808141698177,9035158462022298923>()) {
               case -744868511:
                  var10000 = var1.relative(var6);
                  switch ((int)com.yiyiaddon.m.b.a<"s3tyhwu36hiq9i","lAZLlHjqeLvpv99Zca4gqbz1RyP/hXHCv5gSoMUzBw0=",-8736802991783648476,5825861624265541878,-661982732551074339,-1910439327545008957>()) {
                     case -1151551819:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var1.above();
            switch ((int)com.yiyiaddon.m.b.a<"sliprzhfyfhly","r11iu7Zw6gcfCP5fQpFoSKucNXZNgD0PNro8rPIREwE=",4314141624680364758,2236102575615296063,7810422401717027482,894752063818217679>()) {
               case 406818593:
                  break;
               default:
                  throw null;
            }
         }

         BlockPos var7 = var10000;
         this.b.j(var7);
         switch ((int)com.yiyiaddon.m.b.a<"s2vt4ntzdneowm","M02wUP08nLs2uk6jsA0mSDjSFG6Y75I0gnkL7BqDHG0=",1937630758688070017,-4202963357809759679,-2973203698508790318,-640361844391399682>()) {
            case -1659244480:
               return;
            default:
               throw null;
         }
      }
   }

   private void cm() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wiphalpxvlib","X1kP7/eXmIpGZNbapDKwPWK77XodlwTUnZlnuPRBsok=",1848642193072094598,7595724790620532478,4779828531207719475,-2862603831033955963>()) {
            case 256420148:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ANVIL);
         if (this.l(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s30z4kl1znx6fa","LGTUkH6ckqH8++Lx94a8hK7gjjPgy4k1nqMsWgzab04=",3018384646200667898,6034922769082724083,-6524337562522907559,4393806037904490232>()) {
               case 945399185:
                  if (!this.x.player.getMainHandItem().isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s30s6hrfg1qdil","6twJBCHYtgcxYmV/DS/Ro1/vkhSDvLXwn3oNSHOzfiM=",-433651826488571685,4845326180763426517,2402862775183805241,4427540962451162040>()) {
                        case -878755270:
                           if (this.m(this.x.player.getMainHandItem())) {
                              label44:
                              switch ((int)com.yiyiaddon.m.b.a<"s5o02c9eylmdv","mPq29aqdGVBwK0Znn3tSk/9P7UEYxz+OYjJTgVZccLI=",8434827971673017646,-1452601752333112578,-4295162729202316049,7822214626840119787>()) {
                                 case 305573663:
                                    this.x.player.getMainHandItem().setCount(0);
                                    switch ((int)com.yiyiaddon.m.b.a<"s278zf6gk9xhew","XF/thu5equknJ+awvLgMlqmQ4RL9nTUas+oWt+nycHI=",-1811378105253318876,-2559784908533991208,-1809016102135545331,-7209369962040830295>()) {
                                       case -932323225:
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
                  }

                  if (this.bl) {
                     label39:
                     switch ((int)com.yiyiaddon.m.b.a<"s16p72vg9lidwt","LVe0BjMQqL5zhn9uYdiLAHqDjr8sX8CJAEHhBmfKGK0=",3175804170771513404,-7238614242116717472,-676505165293081050,198546790413533505>()) {
                        case 750863348:
                           this.x.player.setYRot(this.aw);
                           this.x.player.setXRot(this.ax);
                           this.bl = false;
                           switch ((int)com.yiyiaddon.m.b.a<"sdl3v0rmmlgay","QZgx54WKIh2WDMgqNRuOu/fxv9Pr08gges5WLNcYt18=",-5035361535857341083,-280171184746108352,1931663707303311101,720717246660404531>()) {
                              case -591570535:
                                 break label39;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a
                     .K(
                        (String)com.yiyiaddon.m.b.a<"swz0fa8ie1bl0","6Zdq+Q84KwoDRedQD6KDJjeDH7HaZyVYxOnTSbftPDwEXZRq1R8RU2RxaJJvKuaKcD2NS6iz+yArtSc4l8Jg6A==",-9193531312408484797,-1943515992453901052,-2296913085019550852,7260077290998101542>()
                     );
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ANVIL);
                  return;
               default:
                  throw null;
            }
         } else {
            int var2 = this.Q();
            if (var2 < 0) {
               switch ((int)com.yiyiaddon.m.b.a<"somk64bcm5dmj","q2ftItXgW4ECNNho5Ac/NCDzEg0yathhX/6GbjhTFO4=",-8356354316759378211,-4861731989800108365,-5073314316216067472,601417778009945817>()) {
                  case 1677619547:
                     this.a
                        .b(
                           (String)com.yiyiaddon.m.b.a<"s7r9ib98hn076","kfh75ZmwazKLxFjkxn516yr3Bx5pJB8fVHpfL1Pi4v3ZQraH9rquhnWGuzFdxj6FkZOx/j0RySYSR3Yil6+GyTjVhMuOFFJDVZMkrlmfncEnZiK+",-7401959634145242950,8906445507315813407,2452750026532731564,8837927551136533334>()
                        );
                     this.bF();
                     return;
                  default:
                     throw null;
               }
            } else {
               this.b.p(var2);
               if (!this.bl) {
                  label53:
                  switch ((int)com.yiyiaddon.m.b.a<"s3dmjyx040l18q","X0KV7DfmUqwcrwwHpYEB9MY6jSBs45AtVwwTip9B4ZU=",2321976987527583485,-5726020072729354356,7553707568747657548,1171930579729910904>()) {
                     case 1754086858:
                        this.aw = this.x.player.getYRot();
                        this.ax = this.x.player.getXRot();
                        this.bl = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s309p2mwda67gu","v80J9Kby7NlaDTQxm1QBU02aF9L/QHmNAOOhbfY94aw=",930386742544531681,-2998298853115567542,-2353132463816681274,7459795267437728122>()) {
                           case -1567585857:
                              break label53;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.h(var1);
               Vec3 var3 = new Vec3(var1.getX() + 0.5, var1.getY(), var1.getZ() + 0.5);
               BlockHitResult var4 = new BlockHitResult(var3, Direction.UP, var1.below(), false);
               this.x.gameMode.useItemOn(this.x.player, InteractionHand.MAIN_HAND, var4);
               this.dL = this.a.a().dz;
            }
         }
      }
   }

   private void h(BlockPos var1) {
      Direction var2 = this.a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2agqqgshy9zot","BCpPWZl9WnJR7aueXtq/OfTNpiXmfioIxwVo7nP8xuk=",8952275977649623059,4022781760148152229,-1098487575561659809,-3323423910119294361>()) {
            case 107031208:
               return;
            default:
               throw null;
         }
      } else {
         ItemStack var3 = this.x.player.getMainHandItem();
         Item var6 = var3.getItem();
         if (var6 instanceof BlockItem) {
            switch ((int)com.yiyiaddon.m.b.a<"s30okyqdd2nccv","DdzjeQ3RDx6uv0kW4tPPgyAQBNJ+oXxH8GRhqywfhxg=",-1091595294649995681,-943376251817542707,-2890446085453478636,1040655916007674949>()) {
               case 839222331:
                  BlockItem var4 = (BlockItem)var6;
                  Block var13 = var4.getBlock();
                  if (var13 instanceof AnvilBlock) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3sgpxrvozwdu1","pTkgM5ueeEHV7MvYfbDasJOwXy8PBTU0ekXKobe1RFY=",-583606435749544403,-2904559573749936613,-2502188602749207733,-249968784939589545>()) {
                        case -715482915:
                           AnvilBlock var5 = (AnvilBlock)var13;
                           switch ((int)com.yiyiaddon.m.b.a<"s2ipwf5vcf853i","wstkJugkUOqQBXX6PYZYhgOKMzbmChPpZq0deSsXltk=",8995173086483044817,-7108483363681887029,8746988068889354726,-1733550163620152261>()) {
                              case 2096804395:
                                 Vec3 var14 = new Vec3(var1.getX() + 0.5, var1.getY(), var1.getZ() + 0.5);
                                 BlockHitResult var7 = new BlockHitResult(var14, Direction.UP, var1.below(), false);
                                 Iterator var8 = Plane.HORIZONTAL.iterator();
                                 switch ((int)com.yiyiaddon.m.b.a<"sgc7cj96qh4y3","drfqjhyFcHA/gWbi6nMeu0NfSHCugnj1DzUIxHDVtgs=",8385932766507052075,3724684617182257847,5201078390318037031,-6515453809214909191>()) {
                                    case 726521614:
                                       while (var8.hasNext()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s23iuu8mzw8ucn","vztHWlB/G2Mb0KZPbZISdKFussIwAtujNtYM8rVZAt0=",-2074490564706341253,7389194749535213615,-8910200277090688685,-8667216304253674495>()) {
                                             case 1414538041:
                                                Direction var9 = (Direction)var8.next();
                                                float var10 = var9.toYRot();
                                                this.x.player.setYRot(var10);
                                                this.x.player.setXRot(0.0F);
                                                BlockPlaceContext var11 = new BlockPlaceContext(
                                                   new UseOnContext(this.x.level, this.x.player, InteractionHand.MAIN_HAND, var3, var7) {}
                                                );
                                                BlockState var12 = var5.getStateForPlacement(var11);
                                                if (var12 != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3t6bu0ixmxgmz","K0FdwJBwe+K8NCcZaEApMHOJ/B+kO7OspmRRBLW5SKk=",-258274484693813906,3393612871631096390,3675657032244826002,-8079879220909778399>()) {
                                                      case -2095208724:
                                                         if (var12.getValue(AnvilBlock.FACING) == var2) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"socfhtw8mvk7i","3H50n5Yy6Rj07f57uSii34UP5AhtuY1DcxqBD/rUh5k=",1617900587006674223,-5493405552920365727,6960229625552151824,-1819110751552306343>()) {
                                                               case 826737602:
                                                                  this.x.player.setYRot(var10);
                                                                  this.x.player.setXRot(0.0F);
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

                                                switch ((int)com.yiyiaddon.m.b.a<"s1usqis9qobyv1","svxuCiv6Eq/pcJBZhDuL0X9Gf3jLNdd3e/VMHBTbJDY=",-6788844047304084899,-7216762566176536166,-278966537266494836,-6566732338685196746>()) {
                                                   case -1183548352:
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

   private Direction a() {
      String var1 = this.a.a().aQ();
      if (var1 == null) {
         return null;
      }

      try {
         return Direction.valueOf(var1);
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   private void cn() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3b1q5c9yyic96","zABf4OY1qYVOlv9w64rgNbICI+1m9EMXPilLEgoGG0c=",-8597541394400256905,4722526142504391888,7352074134385887359,-8852832402464315751>()) {
            case -756959134:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.ANVIL);
         if (!this.l(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2i9vtsx7kl8u3","X3RJHcvjHVzNCpE7Kw6O94ceSMb+gU+uhNAc2j+ESGI=",-8927702920855696374,-500145365348382181,-5358686605987543897,-2376597596105931759>()) {
               case 586094608:
                  this.a
                     .K(
                        (String)com.yiyiaddon.m.b.a<"s34wj0ji7db952","9ceoMAJ66+FsJMSCs/kfEKW7s0v9dTmyTrgN2xnr/bfuKiDTIjxvXMrr+OQWRdpb3fhqiZBFWV+uzzgSz14EP9rxXdQYo58q",-7177009786427615756,-186693964490424956,1635536090765368176,2005571557308250573>()
                     );
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_ANVIL_BOX);
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.a != null) {
               label160:
               switch ((int)com.yiyiaddon.m.b.a<"s17d8xcema490j","TfD7LWm3X9XmWLQnfdc6bIqQmBf2sKNMd/Ws4r1x4TY=",-3846673274053403422,-6375145511923341252,4983391501552749920,-4173103650953889901>()) {
                  case 828489850:
                     if (this.a.y()) {
                        boolean var8 = this.b.aY();
                        if (!var8) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2eihzggxchjcn","7cf681ucEw+KItt5B0WZLhU2tXiJLlddM2lDZrYkrm8=",1160472090494877767,-1794631963101479711,3360273037322085904,-2205613859416819115>()) {
                              case -1180875672:
                                 if (this.dR == 0) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s35qmins1tjuov","5UlmRA4nXHhaDe6iqJnVAHsyhQ9KYDC0porbS+9D6NI=",-8066309682199027395,-1912598784397747271,-5689560530962313434,9016685606673079477>()) {
                                       case 2037563528:
                                          this.b.l(var1);
                                          this.dR = 20;
                                          this.dL = 40;
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (this.dR == 20) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2lxuaa0nibx93","vR5/CzLBTpQtbUhsXpkjjTHAnDwTuZlK6rZ9zVpjHOk=",3907738941145421938,-922400615165811241,-3218473078597466842,2107565844353976768>()) {
                                       case 491053234:
                                          if (this.dL == 0) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s19457tw6bpffk","0n0yFhrzJef+EiwqIN4SFaT+HMDrA01Q1ghAxLxw+g4=",7738821048143230262,7171611286449553403,-325782846801740310,-234615088762463444>()) {
                                                case 2081193472:
                                                   this.dR = 0;
                                                   this.dL = this.a.a().dz;
                                                   switch ((int)com.yiyiaddon.m.b.a<"sv3ucgdlbob2g","1UzkQiyEaAeqW42/6OK02WfuZhSdPC8BBaeXr38ecF0=",-321825881935233584,-2314532631987697766,-2774637464914310480,3526213156024931666>()) {
                                                      case -310011981:
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

                        if (!var8) {
                           switch ((int)com.yiyiaddon.m.b.a<"s18omul5qaw5sh","fV0BxSAG+d18OOyd+TflNcHlgpcKP4POJqZl1Fmt0K8=",8996280964958997163,-2305911432352768784,-6448982669466219473,-607678859268946653>()) {
                              case 975423638:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        AnvilMenu var3 = (AnvilMenu)this.x.player.containerMenu;
                        int var4 = var3.containerId;
                        com.yiyiaddon.e.g.d.d var5 = this.a.a();
                        switch (this.dR) {
                           case 0:
                              this.dP = this.N();
                              if (this.dP < 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2m0mfggstbq60","TXo6TafJFzIHqGCoKNtXc9/WB3RxeJHOAyz82fMenjA=",-6493075886574141072,-8100704006811465580,-1870695305004367480,6137128316557640283>()) {
                                    case -906537861:
                                       this.x.player.closeContainer();
                                       this.a(p.GEAR_IDENTITY_INVALID);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              if (!l.o(this.x.player.getInventory().getItem(this.dP))) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s299v16sfmhqjl","eeRW+UdK/rPdDMoxMYTerQHd4r6iNM5yZHaPOPTSxHc=",-8112730952413522806,8851945626459621729,-5265108410094341484,4455174941800048175>()) {
                                    case -98425786:
                                       this.x.player.closeContainer();
                                       this.a(p.DURABILITY_LOW);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              this.jo = this.a(this.x.player.getInventory().getItem(this.dP));
                              int var10 = this.b.a(var3, this.dP);
                              this.x.gameMode.handleContainerInput(var4, var10, 0, ContainerInput.PICKUP, this.x.player);
                              this.x.gameMode.handleContainerInput(var4, 0, 0, ContainerInput.PICKUP, this.x.player);
                              this.dL = this.a.a().dz;
                              this.dR = 1;
                              switch ((int)com.yiyiaddon.m.b.a<"s22e40spjd4mpm","CoTZyzRSWxebR9Emq/O5AZcIRWTJyLqmgKMBrw/qwBc=",2372252820186844660,-8818244503558481794,6736476558868969006,-9070607773472472982>()) {
                                 case 2047635920:
                                    return;
                                 default:
                                    throw null;
                              }
                           case 1:
                              if (!var3.getSlot(0).getItem().is(this.e)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s333blfxlv1fr7","yqLFXbaDnQVzeBtWAxmnyp/Q/zC+MlKTj8kc3QKasGM=",-8264156639362895211,1959111143057964761,997811814164206692,4371100811789539598>()) {
                                    case 261423189:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              int var9 = this.h(this.dP);
                              if (var9 < 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s29cruhg7j0gcs","sU4RuIGFb+OTNJCRRpbC3YabYA8aRaRy+dGQYxg4Tws=",3997672635970938158,6138103341101497626,-5479453769059932639,-6224749176256909714>()) {
                                    case 959515606:
                                       this.x.player.closeContainer();
                                       this.a(p.CANNOT_PLAN);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              this.jp = this.a(this.x.player.getInventory().getItem(var9));
                              int var7 = this.b.a(var3, var9);
                              this.x.gameMode.handleContainerInput(var4, var7, 0, ContainerInput.PICKUP, this.x.player);
                              this.x.gameMode.handleContainerInput(var4, 1, 0, ContainerInput.PICKUP, this.x.player);
                              this.dL = this.a.a().dz;
                              this.dR = 2;
                              switch ((int)com.yiyiaddon.m.b.a<"s2hbwwg9613gti","WtdMIgdRUC33v1KRPiag74HXG3DgMSNYGy1fUXYDDBU=",-7853308099553159384,-1874768259376527943,8204561534865726438,5428605767566425715>()) {
                                 case -334329973:
                                    return;
                                 default:
                                    throw null;
                              }
                           case 2:
                              if (var3.getSlot(1).getItem().isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"soermt76fj4ou","aLbBlBr3AbCv7XG05j8xnF6YkzyyvaCk0G7Tqb9B1J4=",6603303002698928259,1043811788562039272,-2773472430226711704,2113996153939670527>()) {
                                    case 1844257781:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              int var6 = com.yiyiaddon.e.g.d.c.a(var3);
                              var5.c(var6);
                              this.dW = var6;
                              if (com.yiyiaddon.e.g.d.c.f(var6)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sss9dolejhuqa","bQ9YQYbKwyREz9aHO71p8TeJx+40FR9B2fGs1RMsM/I=",7589444920210707643,8655695382263337099,9036386346802496126,-6455933746991256895>()) {
                                    case -855570637:
                                       var5.g(true);
                                       this.x.player.closeContainer();
                                       this.a
                                          .K(
                                             (String)com.yiyiaddon.m.b.a<"s1c4u0a03mxkfb","TwHbusCcIh8+ZEUJxEbi/z/7UPE1DQUFBh+NMQzQqwhEDZKUTpAMO5lvHX2KP9tTapjLhrPeb7urBqUPqfSgouKs1pJPaTWzYxpdF5+0nsa32+BQsvCQSuR6",4258073733561085031,-8987215462006719644,7112275489639625887,-5847950023913582952>()
                                          );
                                       this.bk = true;
                                       this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_GRIND);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.x.player.experienceLevel < var6) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sv1nym91s834o","UkfeB/+kStlh+SdHdIE0V62OMbwr7kOim7wSl1C+Gpg=",-735952947846463815,-1937462744335749314,-3968241429173781249,3567524128257382869>()) {
                                    case 1997594688:
                                       this.x.player.closeContainer();
                                       if (this.a.a() == c.DRAIN) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3clcm2phc57i1","6oEcC+eFBXvld3YFe9XTBNLwJaffU9k82djCRFzsJ9I=",-1864306137917475871,3955028094609139715,2945706180136702329,8577989756491547936>()) {
                                             case 1219378951:
                                                this.a.K(N(var6 + "") + "");
                                                this.bF();
                                                return;
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.dQ = var6;
                                       this.b = com.yiyiaddon.e.g.c.a.GEAR_ANVIL;
                                       this.a(com.yiyiaddon.e.g.c.a.WALK_TO_FARM);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              this.x.gameMode.handleContainerInput(var4, 2, 0, ContainerInput.QUICK_MOVE, this.x.player);
                              this.dL = this.a.a().dz;
                              this.dR = 3;
                              switch ((int)com.yiyiaddon.m.b.a<"s1yr9y6wocy74e","CmvGX+WC80KmdT6KYM5ZfzFE5LL0mGrsk4pSBxYkNQM=",8009632622791784018,-5904358974361060721,-2256362846939775974,-424202585121581755>()) {
                                 case -423395047:
                                    return;
                                 default:
                                    throw null;
                              }
                           case 3:
                              this.x.player.closeContainer();
                              this.dL = this.a.a().dz;
                              var5.h(true);
                              this.a.av();
                              this.dR = 0;
                              this.ec++;
                              this.dP = this.N();
                              this.cb();
                              switch ((int)com.yiyiaddon.m.b.a<"s3bhhd1popi2fu","nRvIfMnrHrx6yLTnvOpiP5SUZOegT0z6zk/Z5auXmC0=",-180198251268987362,509668997597609512,-859352244986946277,7995904255987859494>()) {
                                 case 860202132:
                                    break;
                                 default:
                                    throw null;
                              }
                        }

                        return;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s31zw91z3axkrt","ToTZZ9fqYP3c3ePjWOcdbirAuO5rhG/fC+6kBSjj57E=",-2478982439169039283,-4932904886777347584,-9207084915739451571,-4699596005364197600>()) {
                        case 1995310333:
                           break label160;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.dP = this.N();
            if (this.dP < 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s18il8gchg52fp","w8GYpcBNlCxPX13FHh65ciL3XmjP8i14orGIz/uxgks=",-3494706938375191856,2399407970109482028,3392656584468540029,4559734057982808773>()) {
                  case -1637067735:
                     this.a(p.GEAR_IDENTITY_INVALID);
                     return;
                  default:
                     throw null;
               }
            } else {
               ItemStack var2 = this.x.player.getInventory().getItem(this.dP);
               if (com.yiyiaddon.e.g.k.b.d(var2, this.a)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1y59syxwkqp09","EJbk4vFJBQbu3sUU9mdNtdVAAtVfDINMeOnHrbf9jSg=",2936309426068201726,1968029030865666389,-5613615670717743415,8965926799911305459>()) {
                     case 886158254:
                        this.a(com.yiyiaddon.e.g.c.a.GEAR_WALK_OUTPUT);
                        switch ((int)com.yiyiaddon.m.b.a<"s3coghxvi9ingp","pVawQMjourEhv3VwuVp6upm8jyYFRH1cMRuVt8kFmR0=",-2728308396696371952,3590048743315277024,1421307590836896143,-8328005831151066203>()) {
                           case 1326770547:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.a = null;
                  this.dR = 0;
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_EVALUATE);
                  switch ((int)com.yiyiaddon.m.b.a<"s3d31aerhdeysr","lOx2eGxqRjiDVFFwvnGgujA6eh2AA5izw+tjYtTU2sQ=",838346098985420814,9103590357957630703,-5973245574236855327,5726368454948826668>()) {
                     case -1727421356:
                        return;
                     default:
                        throw null;
                  }
               }
            }
         }
      }
   }

   private void co() {
      label53: {
         if (this.dP >= 0) {
            label46:
            switch ((int)com.yiyiaddon.m.b.a<"s36v31z2vniuu4","SuyxekbkW27fPlhnjJiBnbsKlZJpIqHtlVf80FZtY/E=",2909172487855694615,-2030434114005462687,2055306654339576417,-2352251739960431513>()) {
               case 1104479974:
                  if (!this.x.player.getInventory().getItem(this.dP).isEmpty()) {
                     break label53;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s38i4ohqwesr9t","JB9fC9vNLmFmb8y2psDrXPEBG2sKGpvAcAKo2fBkcrc=",3261176625999939030,-2491996050490151752,3998611446161352096,-6082093873472667009>()) {
                     case 1889830849:
                        break label46;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.dP = this.O();
         switch ((int)com.yiyiaddon.m.b.a<"si772iwrgcjb0","l+5Md18z0EEU/edqwwtumYJMvf/TlDHsjvjipbzlD60=",-6104571280904229958,1100385522105267029,-148154500314212174,-3054349600019864677>()) {
            case -1907120990:
               break;
            default:
               throw null;
         }
      }

      if (this.dP < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dwey3m3xlx3","czCLcsAP69sjlUY+42c4NKReedzRWVq/fH20wdHVso4=",-5329819654471979167,3816867709266565159,2981265764736391428,7832352913467952956>()) {
            case -1574231975:
               this.a = null;
               this.dR = 0;
               this.a(com.yiyiaddon.e.g.c.a.GEAR_EVALUATE);
               return;
            default:
               throw null;
         }
      } else {
         ItemStack var1 = this.x.player.getInventory().getItem(this.dP);
         boolean var2 = com.yiyiaddon.e.g.k.b.d(var1, this.a);
         if (!var2) {
            switch ((int)com.yiyiaddon.m.b.a<"sft7p26c458ur","js5eXl3621mj9LhPNmaiM6khTPi7Vn9pOFgbLxk5e+Y=",-2691129856446742547,-4929392709495131608,2639940099013139349,8596917472553139576>()) {
               case 1590765408:
                  this.a = null;
                  this.dR = 0;
                  this.a(com.yiyiaddon.e.g.c.a.GEAR_EVALUATE);
                  return;
               default:
                  throw null;
            }
         } else {
            BlockPos var3 = this.a(com.yiyiaddon.e.g.e.b.OUTPUT_STORAGE);
            if (this.b.m(var3)) {
               switch ((int)com.yiyiaddon.m.b.a<"s16z64xrrvlc8f","8vcDilxoz+vXsy0by65bCIuyQoK+cCTXP/a6+nb5SLs=",2541193670932632585,-4129154437277885642,-1449251744685859611,8305247955007577718>()) {
                  case -459163303:
                     this.b.ag();
                     this.dL = 0;
                     this.dM = 0;
                     this.dU = 0;
                     this.a(com.yiyiaddon.e.g.c.a.GEAR_STORE_OUTPUT);
                     switch ((int)com.yiyiaddon.m.b.a<"s1z1lqeeylr88c","GZcQvFecCJ2yh7eF9zkZR9icyn6IsyILbcSau/Kir/w=",329046897815306665,2482738994397788800,9156197682302425152,-4007523990861827520>()) {
                        case 1413817778:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.b.i(var3);
               switch ((int)com.yiyiaddon.m.b.a<"s6t7oat3mox1q","xiOl6JtofXmF2P5PMBWrSzzU5JDRCqT6lv2Ht0QYb98=",-1105715850269433907,-8477181364683075892,3707207127296540237,4612289777064176977>()) {
                  case -1250819626:
                     return;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private void cp() {
      if (this.dL > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s67mb5qhf5yyu","50W/rq7qnKqZoo4e36JfIOtXv2IrwPDsiQ2VGRgpzSc=",-4226944474028179412,-7140002412974592581,-2088351793600761025,2055392102854429017>()) {
            case -1729939188:
               this.dL--;
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.OUTPUT_STORAGE);
         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3jaeqbcashn6h","sW/k/ZDakbnOjxkuAqtvMUe5fGfG3bZE+GokkYZLkrA=",2586620482587098271,8046536034846855667,-1759562007339408162,6238726394701845278>()) {
               case 1079392621:
                  if (this.dM == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3qgrlz564wf5f","2wap8n2x1JxSsBrNDPrzLJoqpiBN8Ot54Ex5aSNOFF8=",-8882745557070212776,2858824172323473298,-8566702736370703474,-5574296387788505702>()) {
                        case 198710618:
                           this.b.l(var1);
                           this.dL = this.a.a().dz;
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

         if (!this.b.aX()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3rc06oco5916f","9yeKy80wmExiodZ2wLRKKE0kpUgndlC1G6JxeYYpB34=",-4629601690847471862,5294775651013950158,2538461380788140011,1758473032141511634>()) {
               case -996789216:
                  return;
               default:
                  throw null;
            }
         } else {
            ChestMenu var2 = (ChestMenu)this.x.player.containerMenu;
            int var3 = var2.containerId;
            switch (this.dM) {
               case 0:
                  boolean var4 = false;
                  int var5 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s1tgzktv1j6ei4","p54BoKZlBnhfYAFd55mtheT9bessFn7g6Xxu9RTqfKo=",1245613647126989317,4423071856990579836,7341803012250274074,1722280306153182806>()) {
                     case -1409936959:
                        label127:
                        while (var5 < var2.getRowCount() * 9) {
                           switch ((int)com.yiyiaddon.m.b.a<"s5ybmipkerobe","vR95DwgD/YojwKcvabB7hF40TS7PuxuIRysKwvgYIeg=",-3855443644081300321,4870495410405955915,-7655836923677352125,-7653545113270316211>()) {
                              case -1345664772:
                                 if (var2.getSlot(var5).getItem().isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1i7xicon3yq3d","5yTVQl5pRV2rDOn8O0ioGUih5yeF7TSII2C8Kls2Abw=",-8110631669067681547,2475561659664779437,3209450711642498344,1148622481543876096>()) {
                                       case 856092894:
                                          var4 = true;
                                          switch ((int)com.yiyiaddon.m.b.a<"s129rpdeq8usx9","CBAf+sFsBIdd10QkiQw0FFyT0GpHXHdAXtHDLvCOkwo=",-3699053739164297191,772595784260097187,3344571192744367529,292393245716791450>()) {
                                             case -1575976008:
                                                break label127;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var5++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s37q1qaj6fm3eo","OtbZkOsdzbEIUuMqn83yEfFnlosVl4k26D3QcrwUnkI=",4049876359330401379,9209775830646331881,-8326868892572792041,3364373317964042509>()) {
                                    case -1363040663:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (!var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"so6jjvskv2763","NVx/NdUxR4diYkNfxGJRLFTGFhZsn/SwskYEop2wDoA=",3942950443242949769,7422311962798520547,4838740176352892589,-8153771890709067939>()) {
                              case 224374407:
                                 this.x.player.closeContainer();
                                 this.a
                                    .b(
                                       (String)com.yiyiaddon.m.b.a<"sbde1j0on90ox","fT3XaMjro/ubX4eCKKKFNP2IhBXLh3ocMLIEVsNCZ3BuckSupVAPqKZF3vAduPw6IhLfTdKWN5KsfzpjmrinRgyzBBs=",-7263828815799290995,468843825732494852,-8211202653192011578,-5152439144526562189>()
                                    );
                                 this.bF();
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        if (this.dP >= 0) {
                           label95:
                           switch ((int)com.yiyiaddon.m.b.a<"skpnyl526ixgv","/qik0RF5zWVUPe9henXNYFkgSkTP8a/+dTmr6oVO8dg=",7655390679994846551,5843157483605597770,4892586964557656703,-7600716815893020683>()) {
                              case 138576963:
                                 if (!this.x.player.getInventory().getItem(this.dP).isEmpty()) {
                                    this.x.gameMode.handleContainerInput(var3, this.b.a(var2, this.dP), 0, ContainerInput.QUICK_MOVE, this.x.player);
                                    this.dL = this.a.a().dz;
                                    this.dM = 1;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2kjk9yj3q1wbz","aSVqQHE+7Hi79zheESpNjHmyvU2SRgzAXSRZu1bqTDk=",-1411320112543120106,7818528828760703664,-6912899399297940226,3142377279883513573>()) {
                                       case 530856945:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s8z1gwfo60pwl","RsyQo1RTFuvKSkWa5H6epX3GoarD1bkmcwXQcJ4+Xcg=",4035180423476278604,4437240514270964876,-7256791011214653198,-3269786364043813870>()) {
                                    case -1571858573:
                                       break label95;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.x.player.closeContainer();
                        this.a(com.yiyiaddon.e.g.c.a.GEAR_IDLE);
                        return;
                     default:
                        throw null;
                  }
               case 1:
                  if (this.dP >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s30agujvbtnu72","xqtM+pjLmYVc5W5CwkqxdguWzl+p2mTHuIsBxrd+N3Y=",-8633863274011096239,6169274428332207007,8426423826129350088,4453301413498915700>()) {
                        case -310381055:
                           if (!this.x.player.getInventory().getItem(this.dP).isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1vfdi8ohpzkm3","i8+NVTfZ/flQCNaqVnZqlYU/eb0sz2ShbO8bemIdu1E=",-4517006236583180041,-8168414977994852780,2760441264247401977,-8191653695937421266>()) {
                                 case 458817765:
                                    if (++this.dU < 3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"svc6ulu0yuzf5","4cHmlvgIICCSLuO3lF3mjbtyKBm3vX7aDDDDEEVLvnE=",1957127421739954067,-6209422666799029885,2439439775199349727,3527999700943518539>()) {
                                          case 1512110806:
                                             this.dL = this.a.a().dz;
                                             this.dM = 0;
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.x.player.closeContainer();
                                    this.a(p.REPEATED_FAILURE);
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

                  this.x.player.closeContainer();
                  this.dL = this.a.a().dz;
                  if (this.a != null) {
                     label86:
                     switch ((int)com.yiyiaddon.m.b.a<"szpetutjoshje","B8azE0Zxvtxb9XXzFYSoKB2extjiY6UAfunWmB4vUq8=",-6399851941024392356,-417273397698111951,-3476629605773042771,-3135139769372284470>()) {
                        case -1005910108:
                           this.a.cx();
                           switch ((int)com.yiyiaddon.m.b.a<"s1hz8mbw8tiydy","kXKxpl3puznM4zZyj4NnZZlAqv3sN3hD0Zo4z7mDb2E=",-1829791927073635357,1359495978670600192,6593640355063682930,1292274653457634569>()) {
                              case 1070678806:
                                 break label86;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.dY++;
                  this.a.K(i(this.a.aJ()) + "");
                  this.cs();
                  this.bU();
                  if (this.dY >= this.a.a().dE) {
                     switch ((int)com.yiyiaddon.m.b.a<"s13fahjmw6u4ft","dJj45NYy9UdrhbQk91NNubPK14+3ex4aTDMCEjdKHB4=",-988751767315542927,-7177819144189071151,5455698759196431248,-4566172477700629691>()) {
                        case -139000153:
                           this.a = null;
                           this.bH();
                           this.bF();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.a(com.yiyiaddon.e.g.c.a.GEAR_IDLE);
                  switch ((int)com.yiyiaddon.m.b.a<"s3bwuuj5m8d35x","IzYd6+EMZU72J7aiFcqLN3Lapso+Fhg7ZQ1KNAg2ZKg=",-3731693725871428554,5750368628681723638,-2188293414020698787,7745073567283475051>()) {
                     case 442668926:
                        break;
                     default:
                        throw null;
                  }
            }
         }
      }
   }

   private void a(a var1) {
      this.a = var1;
      this.dL = 0;
      this.dM = 0;
      if (var1 == com.yiyiaddon.e.g.c.a.WALK_TO_FARM) {
         label54:
         switch ((int)com.yiyiaddon.m.b.a<"sgva1mysmefd9","oYubXkJE68eVWvvV/3W+1czJF6MCqlwsnDiaT8fAQng=",-1966552019615188455,-6753731929885723222,2591131548262977931,-6631171404556103245>()) {
            case -1593048131:
               this.bj = false;
               switch ((int)com.yiyiaddon.m.b.a<"s2nxrp4pgjyo53","XSwShQWhkrEyxur6qnh2jeRWeizrLNCGFbCwdpLRmkk=",2956550381594288143,-6550951000001758614,1491335930312735659,1215572663501095617>()) {
                  case 1617859470:
                     break label54;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1 == com.yiyiaddon.e.g.c.a.ENCHANTING) {
         switch ((int)com.yiyiaddon.m.b.a<"syif6gle0pron","Mh3Mj3vkx7pad6reSOt70GpknA986TuB/OpvVGnRVZQ=",-1574174609667276449,-3805769739320873684,-4019842376439895479,3128744572013395037>()) {
            case 1449060422:
               if (!this.bi) {
                  label48:
                  switch ((int)com.yiyiaddon.m.b.a<"saq2b08i84zck","cSB0z9gVAT1sTaPHQQt9zPUZFKlnskv356PvfR+qwP4=",-3433672599101194495,-3286185135047517758,-3288408836031631463,-201427858249810303>()) {
                     case -577443223:
                        this.a
                           .K(
                              (String)com.yiyiaddon.m.b.a<"s1wjx9c4v6r4gj","EeBpVO8nKDLnQDbTIBOu3u/basu2SweUPX6CfE1VmXUnzSO2AQbgSq+npUobhdKgyHYK5x7YHvdk4pLj0XE=",2915351786054553531,4644645680219482442,-2234535565459273493,-233376541076234340>()
                           );
                        this.bi = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s1502ambl42coi","QOLvA9a6c0nexTsV/APt5rNyet4cUwp2iLV6qiGMLOo=",-136413144149344445,7567219763811653556,3261721701234430647,6073728651495135593>()) {
                           case 213610831:
                              break label48;
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

      if (this.b(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"sui7rr7axvvnm","hfguX9bis2x7P6Lpp3B/KYjNTI9MH4lay3f22wXeKo8=",-5445340058398566744,-5309111438164825788,3634316983136139805,-1470097770457151326>()) {
            case 1969706767:
               if (!var1.af().equals(this.bR)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2aqblrlqdfpnc","pH1yBv9bvG9yTJeYLbEsw0wgn2NyTdkaMfM3E4yXIAY=",6963171997162983390,-5419815269197411111,3876093583151280889,-5946221040295071767>()) {
                     case -736015886:
                        this.a.K(var1.af() + "");
                        this.bR = var1.af();
                        switch ((int)com.yiyiaddon.m.b.a<"s1nl7elzh3cpq0","5sqhrJtEBNvIyO7HgY9wQ4DYqxxFNCpG9DUrUYNVWTU=",4984813789172011113,7391471875695067869,6203546736955880078,5694671831559043223>()) {
                           case -1346432553:
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

      if (var1 != com.yiyiaddon.e.g.c.a.IDLE) {
         label42:
         switch ((int)com.yiyiaddon.m.b.a<"s2of61qazwpf0h","9A9QMMlWt0apj6oEQ9jPk0+8PILWHziJljI9VRQnMV4=",8391661090894448743,3284656120728766233,2314470021080957385,3511829325878274551>()) {
            case -1186630127:
               if (var1 != com.yiyiaddon.e.g.c.a.GEAR_IDLE) {
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2s0d5tszy43gw","utL8oNuo+5/EEpWQXU5053080zM4BlRaFniHRRHtU2U=",4111347116138249783,6085620958233596094,-5623530634531936420,3742419476033349105>()) {
                  case -1695437939:
                     break label42;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.bR = (String)com.yiyiaddon.m.b.a<"s25w19skwi9gbb","zWcztAI7vZ3n4phzmIYpyLoYhqGuTddwcXVJQg==",6210461774515830832,-535356984053146014,7797865788047356305,-1065394686884062001>();
      switch ((int)com.yiyiaddon.m.b.a<"s1212zg41q6pq2","/fp7fq7eKJhxSfHKCcBC1dHYUGDwvoIW462D1nG+Mck=",1940695816121688245,-7519173774279494195,8694022269045065944,-1266075225406125541>()) {
         case 1542883233:
            return;
         default:
            throw null;
      }
   }

   private void cq() {
      this.az.clear();
      List var1 = List.of(
         com.yiyiaddon.e.g.b.a.ah,
         com.yiyiaddon.e.g.b.a.ai,
         com.yiyiaddon.e.g.b.a.aj,
         com.yiyiaddon.e.g.b.a.ak,
         com.yiyiaddon.e.g.b.a.al,
         com.yiyiaddon.e.g.b.a.am,
         com.yiyiaddon.e.g.b.a.an,
         com.yiyiaddon.e.g.b.a.ao,
         com.yiyiaddon.e.g.b.a.ap,
         com.yiyiaddon.e.g.b.a.aq,
         com.yiyiaddon.e.g.b.a.ar,
         com.yiyiaddon.e.g.b.a.as,
         com.yiyiaddon.e.g.b.a.at
      );
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2urwaze9pcivw","V2XsSdqvmjq4fOfR38OIZTiNaZI8849HRSSFjSIfAqg=",-1840278050256683415,7125804394725007977,-2221658271080358453,-3589115772826887301>()) {
         case -1392709672:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3j7h9rrip48p9","CZR6x63o/gBzP3Cp4zxB4jpmnhie8z7Yh0kXl+TqdFs=",2435704902548239112,-3152915689155748495,3792953881967413863,4288852273371559634>()) {
                  case -2057156502:
                     List var3 = (List)var2.next();
                     Iterator var4 = var3.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1q80qhrle8t7n","l+2RAxduQwUtDa3OeGuUJbhhMWB0UWNynu5KOPaGq0k=",-4537788765839462847,7977365864994023061,1111966890561226835,-8250070917222533427>()) {
                        case -1142115688:
                           while (var4.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1489tt6ccnw8y","7gcGOeAYeiOtBxj2nInu6QHm0BzOcjclnxCBPA4ukpA=",5926469450002362588,-3497988722842673090,-2225553812540630502,1524347421648406105>()) {
                                 case -1339377726:
                                    String var5 = (String)var4.next();
                                    if (this.a.a().x(var5)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2jiziw23somkl","kpEKYZJyH5Wdh2O5L27jFBXLJA99AmsAAJ+Svxb6Sek=",2477911834190853453,1035155362627416603,-7602510763320839713,6365483751429483675>()) {
                                          case -39465245:
                                             if (!this.az.contains(var5)) {
                                                label76:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3uywemekb846i","jNd2wP2ZFOOkDDSaQJ+mK5OA4KWv0L8r2GL3HKRoiYw=",-3927721641217285933,4409207455010041359,-4343284039574942397,6974144122339787903>()) {
                                                   case 1742055527:
                                                      this.az.add(var5);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1fupac4gy37a3","vBT0eG5JaNW4nT7lVo4CJ4NpUq1X/8rP3bz4HVCmDIQ=",7776816402811701467,-3658036185422654491,248922993033779078,-3664982605365486757>()) {
                                                         case -659467502:
                                                            break label76;
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

                                    switch ((int)com.yiyiaddon.m.b.a<"s16k2wh8i62pth","doEAkowcHBwQg1OtXrDEdk5ipsvkNfhdrxIMl7+0MfM=",-6589894242412401440,5008698099115930685,-4513315291611946545,-6438512326296573692>()) {
                                       case -1234300458:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1ol22ruli5rcd","fcLGWF5+4eOC2Z62DDygNUGxU+8Jm/6KntMNFTKb+FI=",-1940483907132103455,-8488984289207392545,6546543280812370278,-8854583556070191247>()) {
                              case 1963266118:
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

            var2 = this.a.a().ax.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s20axfa7bw15c8","2kghl40n2N/sDNgRP5ZHssR0yRWmetFF6DVTCibrjG4=",-5638351052223267323,668525416191026574,7072032628802314676,-2996053003316336688>()) {
               case 1812682615:
                  while (var2.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2b5u4xmkxzb6u","i0Ywmk4K4yVbdovGDNmzvnfGjnYWVODTBrTkm6TRorg=",6301054564282125887,378373073347950446,5593195806539108231,-4174774077292277135>()) {
                        case -180618263:
                           String var7 = (String)var2.next();
                           String var8 = S(var7);
                           if (!var8.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1cnus328dqhgl","7GbKXwmz0ZYcx+xTORQpG325wnFvEsxA5Wo9NQgsycA=",8325360182623511379,-4879301349584672540,6712996272309627833,-396458144409641268>()) {
                                 case 1735755368:
                                    if (!this.az.contains(var8)) {
                                       label50:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2oxii6rdjslml","MQ3N8Qb7BH2qIIxKkPawDGZuvTwFslOFEF8/EpAkZGc=",2792244494293497639,-4938015785528519705,-3390739838467180931,-8010336360950887161>()) {
                                          case -1473302876:
                                             this.az.add(var8);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1ykh42tflcule","ZPKUSvhsDqAaKr2+40O3ju8vHn/uNcfsqrTz7hgQXOs=",4116740289396701870,-4369704612147813098,533004883608827034,-7567188123238908497>()) {
                                                case -971431364:
                                                   break label50;
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

                           switch ((int)com.yiyiaddon.m.b.a<"s3t95o8rcbtpjo","v+uuL29KI9NnhlvGQlWu/tKhR+0fFLKXHfnwMYGny7A=",-3956263859846010358,7160654399262155792,1057536260605612589,7427982755983004636>()) {
                              case -345414449:
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
   }

   private int c(List<List<String>> var1) {
      int var2 = 0;
      Iterator var3 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1jrmtvzck85sp","pK4TVP1gl4uyaJUIaJoi/nUc6MGUETZa3gcSZC1vuI4=",-2276443659295512748,3031861136756616717,-3016071587222796974,7831391236396857411>()) {
         case 2063632110:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3f0i1dgo05d1q","L5Nc/gia8EfKxHL/vPRrSDhW/vbye1WuLaVv61MAtB8=",3028605565656243756,-3892459282084734380,-4655005590112349973,947792324466067015>()) {
                  case 948041424:
                     List var4 = (List)var3.next();
                     Iterator var5 = var4.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s25eump6infz44","AG9H67dXmcVE7P0obJF+7g72Gz+rJTLDHCGo+mvf5Cc=",4705945773714317560,-2380280133654407603,8640953363314620451,6421579318951143219>()) {
                        case 2109953326:
                           while (var5.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s23spee43n4pba","7yUqbgRzMo7hxwP3PJMEZfgYDWdm4zk0FBn4DPdNBUE=",3561411057366306152,-2599021411450401570,-6237768530613525057,-5428747317798823443>()) {
                                 case -1755453431:
                                    String var6 = (String)var5.next();
                                    if (this.a.a().x(var6)) {
                                       label36:
                                       switch ((int)com.yiyiaddon.m.b.a<"sujnqyvzyhw3c","BV3gjlHeA2ypOHBOXp6tXYc3tepwCrlRHjjHXayRODI=",-573809128904218253,2540765552039259053,174523240222577253,3366513064302768499>()) {
                                          case 278536614:
                                             var2++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1tpyuh298sup2","KzmHTYf28U3kr67gvtO5eNIkcQAVRwVudRO3fyKKpSw=",-506277091425199595,5421786601205826658,5633522390001902679,-2944263864171081731>()) {
                                                case -1411187882:
                                                   break label36;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3o9sf0wi965xy","7Wxl25cvuc/4xx0ur2n9PjeNXC92JH1Xe5W3tIcYWvE=",3241112401309326698,-8000336532525816982,4438827883927062395,8826838567959720564>()) {
                                       case 1640211646:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1wrggo8nubseb","d5w+EJUi8q9Oe23Rawdhk/tAT5ICcHWO6ZKZ3HVkdtY=",-992077149926246219,4652054283107193153,4460212417308036587,-5021323334512455979>()) {
                              case -876218787:
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

            return var2;
         default:
            throw null;
      }
   }

   private boolean a(BlockPos var1, Block var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"spdoygu8pjd42","3BgL5Ez1TD4sIfn1DCJvYnKmDisrCTZveVo3FGcHHZM=",-2847298037009436272,-7583463007156665399,-5262551821306441300,-5927841091384105351>()) {
            case 267471:
               if (this.x.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"shvmtblntiatx","AaEdckg0wvpk+SqdVG53FrrMc9FxQrcRUq/iGu2dBpM=",-2901647207089384066,6488713056324624501,2401594039668074299,5203069799350939651>()) {
                     case -108524173:
                        if (this.x.level.getBlockState(var1).is(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2e0hy24fhbm68","rgcJHW+XedEsyy9vB2Xx6yn2QqrWvnOD4pKZ8K1CGuI=",3000445990249393017,2692211884819760117,2469210624943151718,-8184963252035007815>()) {
                              case 2018029874:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2peiodohzyso1","ZKvwxxzaTr7OyRSWUEcN2lEG8VrCsY6/g34cW76I+l0=",403802609731138838,-5848215169376068515,7522845298311075067,310948307247781701>()) {
                                    case 2089843439:
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

      switch ((int)com.yiyiaddon.m.b.a<"s29kzu9o9argqy","2kwrfsqYCb9Or9+SYqII+lzbxLR/jdYpLkaWSLxstbQ=",2850517421240027790,7859949633280237756,-5811349083696577904,-3359871801840322300>()) {
         case 1262379910:
            return false;
         default:
            throw null;
      }
   }

   private boolean aA() {
      if (!this.aB()) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s1lpu5mrcc491q","dV+xFoZ33vYXWt4p4PloAJw8cIl008u5GfK1qTnAXd0=",4572290839441011205,-5661470445860145924,8017249772014943056,-8435603618184280707>()) {
            case 436103358:
               if (!this.aC()) {
                  switch ((int)com.yiyiaddon.m.b.a<"swulswgcjbwpd","k/W9QYCIovDjoBZsFYCemg3yOQTtJKNfee1O9Z5ovQ4=",2736877419742946747,-2698937798755081084,-3995669829171436603,8550616984185537990>()) {
                     case -97048037:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3kd3nrrwtfopk","pqEuz3Bqsr0QvCWos0udhITjjqDg4MXL8jRhRnpnt0w=",8931472775675324270,3156449904714855882,6407358535090881236,979800176647674254>()) {
                  case -1335328492:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"sg1go6cr3wmmg","sCHQrPfB+qDgKRAFuQscQsY808+E2z2cRgDgm1czS/0=",-7942464756402707597,-1972021788754441416,3501396942811379388,-3679371822375108505>()) {
         case 130448733:
            return true;
         default:
            throw null;
      }
   }

   private boolean aB() {
      if (this.b.d(Items.BOOK) < this.a.a().dB * 64 * 0.2) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wipjrymy9q84","0R7m60DTKmqc3zxo2vtUM5gpz4OSoY3o6rlfWlcBngw=",5040760557051818037,-6692701192488852205,2472589068553109585,-3043359503471208278>()) {
            case 113328185:
               switch ((int)com.yiyiaddon.m.b.a<"s3clbbewpkfql7","+iReHE2Tnoc6PuJwkyrmwl5gy+oGG+HpJ8sNv3Z3cYc=",-4885569149670579564,6379924526725420907,1239201106008879360,-5260405309396163683>()) {
                  case 1819725251:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2ii23lheu9796","/cqSJVvjJbr87WsWomnF93X2jmsZmpSbd+F4WjlKaWM=",-2141933772821580463,8092066417605984142,7213014185764169417,-2303674093925043428>()) {
            case -1477821923:
               return false;
            default:
               throw null;
         }
      }
   }

   private boolean aC() {
      if (this.b.d(Items.LAPIS_LAZULI) < this.a.a().dC * 64 * 0.2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m1eiae717x9u","NdwrCTD5+2tH0zXPx4O/l+7YtIBLldj/FiF6wwT5QjU=",-3825136982331426420,-5203726762939805794,4120674895728132824,2037656931953029136>()) {
            case 993521116:
               switch ((int)com.yiyiaddon.m.b.a<"s1aobqo14wu8du","BFsIB3MexsD13GTZI/mhE5y/IQSO9t58q6w8+0jNTO0=",3228914830064456362,-6925664823654161924,-8578411643721711097,3775106085599404147>()) {
                  case 1738215446:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3e75yw6x5paen","K3Xg3QA/2Fz2/yZPBBnd3P3dPeKMYT3FMMX5OZ9Bfds=",8302266872291306191,-5455111263069234274,6594923872545333740,-6641763245973688837>()) {
            case -1691771884:
               return false;
            default:
               throw null;
         }
      }
   }

   private boolean aD() {
      BlockPos var1 = this.a(com.yiyiaddon.e.g.e.b.AFK);
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ajc5ie7pwrhw","T6Cv5BKlwREl/NXzv2ir8zMVXLwRf4l8eD3X3IFNM14=",6434311308162583964,-7041076017173726223,3973067239734275318,6675755633657687959>()) {
            case 1719754829:
               if (this.x.player.blockPosition().equals(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3aqpbfby166y0","riTwpm95QISCk2zMjrtFGx9tsioLqL57xGkqnuU/xB4=",-6849898305247973555,6551070784493039845,-3377528152429026115,2109768340145090665>()) {
                     case -819512880:
                        switch ((int)com.yiyiaddon.m.b.a<"s3hf67tk06xww3","Y5egpCmw1zG4eA3BU33c3Cyi25N98tqC7iymZc0cHJA=",5274564322694210641,7130767510128391303,6026135740705273605,-9203926444210823307>()) {
                           case 967273203:
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

      switch ((int)com.yiyiaddon.m.b.a<"sswgi6wb4p2hc","eg6/4IAviYsgC0na7XY357MPvIS4VykP2EAYy7YoYgM=",6874164983050173737,-7442729998837975195,4983626061706925785,-4488288516993825916>()) {
         case -1362876807:
            return false;
         default:
            throw null;
      }
   }

   private void cr() {
      if (!this.bj) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x55q3k6gu371","SgbJ1BqJCZAXWQxJqolc76u/6ENY7aWj4llpUgrS1vc=",-1012753787918666135,-6248022574838666546,-7634499439560797202,686154993526319118>()) {
            case 1257700019:
               if (this.a.a().bg) {
                  Float var1 = this.a.a().a();
                  Float var2 = this.a.a().b();
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2mas5cvseylmi","CASIyzEvlcBzIEc0RwclIBFW3pO27XELL0Smg5I+1VA=",3294234223667343037,-5150345239466935549,2640108373419918150,2394523701349876551>()) {
                        case 209162372:
                           if (var2 != null) {
                              this.x.player.setYRot(var1);
                              this.x.player.setYHeadRot(var1);
                              this.x.player.setXRot(var2);
                              this.bj = true;
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2sfiaqxo93m00","Jybb1r5FW8tcVd1V9ZmCkz6s7zAg28cU3Ey8+FGjZDg=",-3629706464258270762,7802125058682793868,-3925567993936924345,5899251965476889419>()) {
                              case -1565823343:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s1awbm7voop626","/U6lhcN/2hGsRjKyuK6klm1G2nlcdFRXcaGNhQUESKs=",-4185213259788944824,-7095514952415487754,-5029827839604778568,4738091445145383667>()) {
                     case -1273990459:
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

   private void cs() {
      if (this.a.a().bh) {
         switch ((int)com.yiyiaddon.m.b.a<"s1stbvhszvhd1n","vJszyVGJFyZCJQITMItV+LnEHJfL264gNPo+KZZu8H4=",3680906273293813074,-1509263648252006406,-6650717210621961087,-6621830698504489561>()) {
            case 2052863610:
               if (this.x.player != null) {
                  SoundEvent var10000;
                  label63:
                  switch (this.a.a().a) {
                     case CHALLENGE_COMPLETE:
                        var10000 = SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
                        switch ((int)com.yiyiaddon.m.b.a<"s1lckf2md1a1bi","R/6ninldxJB3CE6UxVkEnU7vQwenTSggnovjaTzpViw=",8214439710731437855,3490811706356687772,4886571449973986594,-6095680672146197938>()) {
                           case 479350045:
                              break label63;
                           default:
                              throw null;
                        }
                     case LEVEL_UP:
                        var10000 = SoundEvents.PLAYER_LEVELUP;
                        switch ((int)com.yiyiaddon.m.b.a<"s1n51609j0popa","Sig7H6C96wjPKus0FetOIfTOrKMp439lSgoVHWohuXw=",-1855512827409661713,316206586535142357,646691532321768487,-2082197938841554983>()) {
                           case -1178209701:
                              break label63;
                           default:
                              throw null;
                        }
                     case ENCHANTMENT_TABLE:
                        var10000 = SoundEvents.ENCHANTMENT_TABLE_USE;
                        switch ((int)com.yiyiaddon.m.b.a<"sxmbrzn5u82fj","P5tTZseLDZW9CU26vPLIvJJXFuOhAeHbSDF1uKU/wNs=",-366535438635478150,-9182785655408440226,6496077895178612761,5545336472416202150>()) {
                           case 1903954583:
                              break label63;
                           default:
                              throw null;
                        }
                     case NOTE_PLING:
                        var10000 = SoundEvents.NOTE_BLOCK_PLING.value();
                        switch ((int)com.yiyiaddon.m.b.a<"s2iry2dtnm7sdg","viPcoMAsPdMp1/87wo5A8wpK0F5GyOJoNLsD3k6g6/s=",-647112231318627533,5169904644233734527,4977275470304742764,-5866467791086403069>()) {
                           case -102531632:
                              break label63;
                           default:
                              throw null;
                        }
                     case BELL:
                        var10000 = SoundEvents.BELL_BLOCK;
                        switch ((int)com.yiyiaddon.m.b.a<"s134xfcx0du437","3+tQuVd7141TrTietDWveM9vI9ZBd4MhbUHsYuoaVhA=",6679623747904316888,-9187814033997432449,1356083707706606463,6872045336177856709>()) {
                           case 1560013146:
                              break label63;
                           default:
                              throw null;
                        }
                     case FIREWORK:
                        var10000 = SoundEvents.FIREWORK_ROCKET_BLAST;
                        switch ((int)com.yiyiaddon.m.b.a<"sspo5xg64sa3","mgt0qr2sl0BvNJY7QGL4DmuTWM5E7afEssvZY0Hl0uA=",-3180403790690228296,6048286781642145870,-8429913795279012860,1818494696594108220>()) {
                           case 145478207:
                              break label63;
                           default:
                              throw null;
                        }
                     case EXPERIENCE:
                        var10000 = SoundEvents.EXPERIENCE_ORB_PICKUP;
                        switch ((int)com.yiyiaddon.m.b.a<"s15v0d90srdzea","Wn7d0yEX3yrnQxLJm6T2cy4nmBDv3xuARooFXo4ELio=",5670507159429119956,-1145522045568075008,1961284165267271265,-4273196998287019671>()) {
                           case 428001034:
                              break label63;
                           default:
                              throw null;
                        }
                     case VILLAGER:
                        var10000 = SoundEvents.VILLAGER_CELEBRATE;
                        switch ((int)com.yiyiaddon.m.b.a<"s3ncf8y3csk2bn","xOEFFlH4SNprEKtaNOJoCbUFJpmYq7Muvhr4pcul4Po=",5863827762283014622,-9101563817496978059,-6595818955261291904,4932466887905809572>()) {
                           case 1743082053:
                              break label63;
                           default:
                              throw null;
                        }
                     case TRIDENT_THUNDER:
                        var10000 = SoundEvents.TRIDENT_THUNDER.value();
                        switch ((int)com.yiyiaddon.m.b.a<"sga6rlrrgy0ov","mslUaisYfdZp+GzOERpYImk8VmZk6GTptwAdKPkyzv4=",-7809515410341367534,3188918436078306190,4082994716170385446,1609933427033048912>()) {
                           case 517967010:
                              break label63;
                           default:
                              throw null;
                        }
                     case ATTACK_CRIT:
                        var10000 = SoundEvents.PLAYER_ATTACK_CRIT;
                        switch ((int)com.yiyiaddon.m.b.a<"s3s3eracjayp5u","Bt02iYjjhgeDaS92FKr4lTglsUR8Js71xOiKsWhag3s=",1827529942501046965,-5111417524959501948,864390293216283025,-8383718348886728055>()) {
                           case -900203401:
                              break label63;
                           default:
                              throw null;
                        }
                     case CAT:
                        var10000 = SoundEvents.CAT_AMBIENT_BABY.value();
                        switch ((int)com.yiyiaddon.m.b.a<"s1tmlo9xg7fwts","lKzLwDrjXbcKtLt9PwAxNUHYaklj3TK/yX9vABP9T8g=",707391964184341420,-5309131030796747068,-6436482950252015941,-5933211252460106358>()) {
                           case -599331589:
                              break label63;
                           default:
                              throw null;
                        }
                     case THUNDER:
                        var10000 = SoundEvents.LIGHTNING_BOLT_THUNDER;
                        switch ((int)com.yiyiaddon.m.b.a<"s2gogoulu7mllf","Zwp1PDHvnqnwCc7NlgvbkaTEeVyipzDFlSi8aE0x5vQ=",2029617143058346081,8592852950021008805,-5714953022886831007,4553949319295960108>()) {
                           case -373038750:
                              break label63;
                           default:
                              throw null;
                        }
                     default:
                        throw new MatchException(null, null);
                  }

                  SoundEvent var1 = var10000;
                  this.x.player.playSound(var1, 1.0F, 1.0F);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ivqgeg7ugwy7","BjnRDV7DuL7TsmU6szp+HqpD2fw4wa2ZfVnsx6vbCrI=",-8292903349366490614,7760230699690800214,407476874883528035,3212426230623016776>()) {
                     case -1344917727:
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

   private void ct() {
      this.cu();
      if (com.yiyiaddon.d.b.e.g(
         (String)com.yiyiaddon.m.b.a<"smy6lrt9cot35","GRjTxJiPDVoArq7GIcH3WPRQnzZT/3Q0hd42l4oriWBD5uyjDkolAxeZPVs=",-2809441995103403872,-2242933110827058780,803756730565666030,4155200590219657616>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cudwixped129","u6Ho4d+g9hzckfYozHZfxKydZoTZjvgJDuYDOg5Yi9E=",-5119654234082214105,7690296224275592170,-2821974610113833107,3412194167225100575>()) {
            case -727942031:
               return;
            default:
               throw null;
         }
      } else if (com.yiyiaddon.d.b.e.a(
         (String)com.yiyiaddon.m.b.a<"smy6lrt9cot35","GRjTxJiPDVoArq7GIcH3WPRQnzZT/3Q0hd42l4oriWBD5uyjDkolAxeZPVs=",-2809441995103403872,-2242933110827058780,803756730565666030,4155200590219657616>(),
         true
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s1z33e2oxuy2pr","Vfk869jKXXWOTOwX/iZ4sgcT2pPNdqSQdNES9UySraU=",-8842486951932459806,-7053412426527998478,-6083751801222448444,-2422327535603743074>()) {
            case 1242007319:
               this.bm = true;
               switch ((int)com.yiyiaddon.m.b.a<"s3sdu6hnx2kbwi","Yd0hrvbrKG3OQyWIGNcKZiKtSMY92t3gZn61ObfdAgs=",-8552888639113947601,-8659981604537307621,1056922319366210355,2054826656062817014>()) {
                  case 1548774044:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void cu() {
      int var1 = this.R();
      if (var1 < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1thgfgqrz05n7","oJxt58+WzWb/DAfgNtucbJDgnqDe7DEHwXgq3GCW8Cs=",-6381927193487255822,3260889225621461385,-2728903274561300087,-7133187074742857596>()) {
            case -1097578366:
               return;
            default:
               throw null;
         }
      } else {
         this.b.p(var1);
      }
   }

   private int R() {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s27997xexx67bg","7++7ZZ/tMDpYXPChQn7hF9foqSQi7dGj3bRDif6kLFQ=",-6710552751480259141,-7524225601845526988,-4042649516793824656,-8677286811539978259>()) {
         case -1976008475:
            while (var1 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s1j9g99593i690","rj/hkEqUvjPJhzpbanlwxN5wIkRem3PekkH9v3CTt30=",110270519106488604,2550931250823788461,6153634586565416431,-3549849469542689265>()) {
                  case 459324989:
                     ItemStack var2 = this.x.player.getInventory().getItem(var1);
                     if (var2.is(ItemTags.SWORDS)) {
                        label33:
                        switch ((int)com.yiyiaddon.m.b.a<"s2n9s1dfup6zuw","hi18wvweYcsMMwBsiX7AEOlvuIxJ4jJWOeQ5P6hsdVE=",-1744234403210432285,-1820059990773958249,-5029552665857611205,-6170681370302446464>()) {
                           case 180037288:
                              List var3 = var2.getTooltipLines(TooltipContext.of(this.x.level), this.x.player, TooltipFlag.NORMAL);
                              Iterator var4 = var3.iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"sg2s3af38zktq","TnYsaR6Tb+XQJH3kZvl/O+abBMu+S/oQRQzYneiRAbY=",-8262722978069057382,1721822939044737025,7197374459744174949,6051471480943907701>()) {
                                 case -1967438037:
                                    while (var4.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s11c7lb9d85043","0fsxYKHoX629PS1E2fNSG8ORB0ZkJxDTcX2m3q3hjvM=",7164021994483376564,-8230078913544285449,-2378569469118857097,-391756428951948844>()) {
                                          case 2063218801:
                                             Component var5 = (Component)var4.next();
                                             if (var5.getString()
                                                .contains(
                                                   (String)com.yiyiaddon.m.b.a<"s1y5da7oopkp8k","MCi1h7QM1TgCqhKh1c4hCABBruXkbSgNaXHtmdy/8cOeOEtN",6962950211896562678,2251193443692628906,8211674395758763523,-6083771946918081600>()
                                                )) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2ozw9z1bgnepx","ai5l1rkt8501znAhSHunDySaMN77E5QorjcSGRnWgZE=",-5491008290950406913,4264436379045021791,2168331903521721366,-8001192404154280174>()) {
                                                   case -1254598084:
                                                      return var1;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1e6pm94xbjebn","kqRNevO8VIi/n85aAzmbw82w/afdweKM2fexwxlVuX8=",-6879828330947686148,-8474211447123963697,-5709285314317638471,-3767188042686818587>()) {
                                                case -213398039:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }
                                    break label33;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s6k9z15vutynl","Urn8eK/xYNYWRMC7Mw8qFYmFE1JC7YnB2jynkRELv8k=",-3925151968153858781,-8372802309361764772,-1193312053167950593,7278830686558823558>()) {
                        case 688945061:
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

   private void cv() {
      if (this.bm) {
         switch ((int)com.yiyiaddon.m.b.a<"s33wd8g8l7cg1h","wvGllCsXS8kMDHGk7AxPyw8BQiHIC9G/EbPK/rMliWo=",-5534946576366501252,1849841777378659038,836320892017147490,-4303225550751237451>()) {
            case -44778819:
               if (com.yiyiaddon.d.b.e.g(
                  (String)com.yiyiaddon.m.b.a<"smy6lrt9cot35","GRjTxJiPDVoArq7GIcH3WPRQnzZT/3Q0hd42l4oriWBD5uyjDkolAxeZPVs=",-2809441995103403872,-2242933110827058780,803756730565666030,4155200590219657616>()
               )) {
                  label16:
                  switch ((int)com.yiyiaddon.m.b.a<"s23malagd2ytat","0+vA3cK+uoSs2y4cV0v7Lpf5ULhLW1vUhDOxGCVxAvY=",-2678655859779951506,1424019918856118068,-9021429980183029018,-8551545164215453096>()) {
                     case 1459729425:
                        com.yiyiaddon.d.b.e.a(
                           (String)com.yiyiaddon.m.b.a<"smy6lrt9cot35","GRjTxJiPDVoArq7GIcH3WPRQnzZT/3Q0hd42l4oriWBD5uyjDkolAxeZPVs=",-2809441995103403872,-2242933110827058780,803756730565666030,4155200590219657616>(),
                           false
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s1qew7woq4ap2n","GJEVCCbZjTc126O7dH0cSl0izYh2OQ24LJfUUk2V9qs=",-5837064290466435594,8232275497166195919,4887844108714385342,-3288317533428191943>()) {
                           case 2104341159:
                              break label16;
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

      this.bm = false;
   }

   private BlockPos a(com.yiyiaddon.e.g.e.b var1) {
      com.yiyiaddon.e.g.e.a var2 = this.a.a().a(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f9ymyshfjgki","BqMwTY/n7j4pXmnM+MMK1QIBiNJvO4lW1wBM9QLWsXw=",-9054094821796550914,-5911203926160285081,-4583157448522050948,-5282175845583623227>()) {
            case 2128406997:
               switch ((int)com.yiyiaddon.m.b.a<"s2a9rr0acb7ot8","n9hoy+hPgxC5uPelr9DS9ZGIgiUQ8/iHjADtXbOYLnY=",-348465087011145016,627203032811573075,208539021156443470,-2302147886655625184>()) {
                  case -1593562708:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         BlockPos var10000 = new BlockPos(var2.aj(), var2.ak(), var2.al());
         switch ((int)com.yiyiaddon.m.b.a<"s387c7sbeh7l2a","dHSOseCGl89yNepT9pIOzBxVW1dXUKQK/OkA4HuMEuE=",4104513397450979097,-5772531353376606243,-3114653594225271874,5440028894812864199>()) {
            case -1937523208:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static String i(String var0) {
      return var0 + "";
   }

   private static String j(String var0) {
      return var0 + "";
   }

   private static String N(String var0) {
      return var0 + "";
   }
}
