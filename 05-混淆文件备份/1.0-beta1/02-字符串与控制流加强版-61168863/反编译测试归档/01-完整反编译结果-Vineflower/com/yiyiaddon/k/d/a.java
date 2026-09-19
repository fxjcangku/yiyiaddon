package com.yiyiaddon.k.d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerData.Type;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public final class a {
   private final Minecraft az;
   private final com.yiyiaddon.k.d.a.a a;
   private final Runnable i;
   private ServerAddress a;
   private ServerData a;
   private int sO;
   private int sP = -1;

   public a(Minecraft var1, com.yiyiaddon.k.d.a.a var2, Runnable var3) {
      this.az = var1;
      this.a = var2;
      this.i = var3 == null ? () -> {} : var3;
   }

   public void ae() {
      if (this.sP < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bozpw7lcw5pf","KYf/miLLjt8iTlFtdEj9W85PJd692V4L3NZzlx2PMF0=",8469543361000573576,-637110142813757377,-3106922267192275150,-3712287398843203837>()) {
            case -980833453:
               return;
            default:
               throw null;
         }
      } else if (this.sP == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s19p4fbenbjwyu","AJfC6Kt4HNWXVmlsPCFdpMVDO4OZGjNDYVdfA6HJ1n0=",5888257757462690249,-5579190203179185467,-5551163281594006456,-8255982603689276981>()) {
            case 1695588834:
               this.sP = -1;
               this.jr();
               return;
            default:
               throw null;
         }
      } else {
         this.sP--;
      }
   }

   public void a(ServerAddress var1, ServerData var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sufjuqwrhrc0d","INjyuYhDi/sldu/X+1PH43vVXTsKyn9wq+B1tmzG2t8=",-797549494747901472,-3990073000957301308,-5920693761834957575,-150829587525979927>()) {
            case -858918288:
               return;
            default:
               throw null;
         }
      } else {
         this.a = var1;
         ServerData var10001;
         if (var2 != null) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"s3ng9yijaax49f","cufs96itN0sLXKJHe9WzQyFCfUyEYDxElctJFT4EGpA=",-1767279628234745937,-5384351094417776939,5999954878256039349,-1436347959753582579>()) {
               case 1148752661:
                  var10001 = var2;
                  switch ((int)com.yiyiaddon.m.b.a<"sm0jm97o4z6d6","LnHwMJufDfR8EcXydIJMGHMOJyppEgic2UokfqZEWzs=",4047284276409407543,8150635831716040920,1340366614791043479,-312682678153925574>()) {
                     case -574842812:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = new ServerData(
               (String)com.yiyiaddon.m.b.a<"s3kny0kot0lgb6","2UyGJbkP/3nSYdQyEA8ci8cK23koPPsaG2zWa5k47wdMxE6z",4478869743478706729,8510322282004392636,5895466565637188676,7762867140796762709>(),
               var1.getHost(),
               Type.OTHER
            );
            switch ((int)com.yiyiaddon.m.b.a<"s1tv22tsctxcsk","MaGyinjTW93pdGClq2LhKn/ZcR1udGyA82usNGp5BOQ=",-5230652148013496910,6963377216387063489,-9182029417740950833,4158344093303625801>()) {
               case 739654958:
                  break;
               default:
                  throw null;
            }
         }

         this.a = var10001;
      }
   }

   public boolean ap() {
      return this.a(this.a.D(), this.a.E());
   }

   public boolean a(int var1, int var2) {
      if (!this.a.ar()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qp0343rcd4vz","aZul3d1T7qih3k9u+v7x2URRm1Xjsl3akmE6jm1Ct6w=",7748189233621489091,-8101810871268978677,-4090508427369772794,3829670036743808818>()) {
            case 129800099:
               return false;
            default:
               throw null;
         }
      } else if (!this.fH()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1u6j3mk5hbg2e","0Of4TeM/QpuwtZW6dcf8/aci84RuM/jUnkUoL82pmuQ=",-2403744799500957192,-5810490724293121586,1299693125659186611,8601245599358738011>()) {
            case -188628577:
               return false;
            default:
               throw null;
         }
      } else {
         if (!this.a.as()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2prinbna5nirs","CUAFMg1cpJ3ML/QwNT/jIu05F1SghtsdayUb+GV19Ao=",-3788204569117589560,5251810561720884198,1249835618772374649,3095037143129538648>()) {
               case -679568653:
                  if (this.sO >= var2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s375ro1242fd76","U7/fPoDU9XlOnsu7NoNTGi0CA+965uCQFXoxyXA6rDY=",7977064159751524093,5750946412316158023,-8980224449541609832,-1325327238379806432>()) {
                        case -2017963877:
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

         this.sO++;
         this.sP = Math.max(0, var1);
         this.i.run();
         return true;
      }
   }

   public void f() {
      this.sP = -1;
      this.a = null;
      this.a = null;
      this.sO = 0;
   }

   public void bi() {
      this.sO = 0;
   }

   public int B() {
      return this.sO;
   }

   public boolean aq() {
      if (this.sP >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s9ka0z6ukyvlp","cAeZ3ho6j+O4jd6fBMtevGhAUGG4l1rJoR/PnkCnSvk=",-5128033176714919874,4853202379341807766,4045172832032418616,2940966735545965856>()) {
            case 1520684819:
               switch ((int)com.yiyiaddon.m.b.a<"s20ug32n600bxv","7iLt6ZdmnwWNRyZwjoaQbHeTrkVW2tjkFGtqpQ+Qxck=",1968106363850172580,-7986660305019341544,-4739314237186338307,-4492703719086683759>()) {
                  case 1967273147:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s9uny8cxr6rib","AXzgvnpnznZYAaYUIVEbUG181smu63hkVyPJnkREYBQ=",5363030640671617246,4835391225679312060,4160658153862480216,3089445861532250742>()) {
            case 1632923197:
               return false;
            default:
               throw null;
         }
      }
   }

   public int C() {
      return this.sP;
   }

   public boolean fH() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3bt1bnv2mpetu","HkJeEIG1puPd0BuEM3pY3JRfnBxpV+czPNwk42SqED8=",13467054836032288,833244785884799878,8609365302897457840,5437772830273984794>()) {
            case -698832772:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1kn6ifa0c5npx","uBqTmK74IpyUQru8fX/UCwPb5njQTTy7vrieRYA4y9M=",-4432462608220433782,3100881958966892269,-3321003059036335255,-6806310706520791449>()) {
                     case -2101918172:
                        switch ((int)com.yiyiaddon.m.b.a<"s1wklivr9kns0q","mDLwyscCMkbu9t+Xfpm+DWeD8Mhm6iIr4shLMBlIoxY=",-4448188935853959687,-2497972968285060971,8981903012259974343,399417796017571240>()) {
                           case -492581146:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1odsxawtwtvpc","QqnFIAqM6uWS/lhTl9XOFXmwTTA+6C12pQMFVH6wZvI=",-7116396618286774014,-5474610006431487274,-3911070051419544527,-2663394816576381471>()) {
         case -67301823:
            return false;
         default:
            throw null;
      }
   }

   public String gq() {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12hhlgaoxtlww","mgAWq/IRCmt0Lor34SSfQZ0hbzBdnUm/oVDloNy2+2Y=",4403571103607594430,-2006297407871697843,5437323852905276469,8665850418541528191>()) {
            case 2127419318:
               return (String)com.yiyiaddon.m.b.a<"s2regd3iej6vtz","ESPNhLqHlQ2XkaBQ3O3Z8Iz/FB7h49av58Ey1A==",6351634265266791975,-2830151923663388244,7281063717308177231,2882951159964435260>();
            default:
               throw null;
         }
      } else {
         String var1 = this.a.getHost();
         if (this.a.getPort() == 25565) {
            switch ((int)com.yiyiaddon.m.b.a<"sjgb2d3xh4y6m","4FXxwBpZf56YELHeyjtIaohQSl+4W5Rt+pG3BNRv2D4=",4050800241060829478,-5657864340331970749,9073889131161319489,246511932839729362>()) {
               case 1113413536:
                  switch ((int)com.yiyiaddon.m.b.a<"s15zs9cvbfag87","vTZZwNP3iy3G5kGIA6ttD67ptUXaOZT2cSEinvPq1yY=",-1639449814375698342,1857112565305645839,-5274560105903082753,-6423513315756731874>()) {
                     case 1837974260:
                        return var1;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var1 + this.a.getPort();
            switch ((int)com.yiyiaddon.m.b.a<"s2vl5fznqwiq1a","YDmq4ztfEcIA1hcCNZl2JLvjKF82ofscWcQG2B4nPKc=",-4309433173669114798,-7651737822406775050,-2932400099359633596,-5890164916851953118>()) {
               case 2082124790:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   public void aA() {
      if (this.sP < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ibl5kkb6qpoo","9fU8J8BmnuvhlMkZPkguMB2mrZ4YdWQCb19uxkblFHw=",6289942446398810277,547303615619168828,-606306012388577893,4832816388419959932>()) {
            case 1028311287:
               return;
            default:
               throw null;
         }
      } else {
         this.sP = -1;
         this.jr();
      }
   }

   public void bj() {
      this.sP = -1;
   }

   private void jr() {
      if (!this.fH()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ctq55rrrilzs","PV4Cvw3FfhANOMqPg20aRzj7po5oQ43Noue5jJeuqQ0=",6440478708855201708,-7389093418791647908,311804695150826133,-6195104670402168024>()) {
            case -1400735161:
               return;
            default:
               throw null;
         }
      } else {
         ConnectScreen.startConnecting(new JoinMultiplayerScreen(new TitleScreen()), this.az, this.a, this.a, false, null);
      }
   }

   public interface a {
      boolean ar();

      int D();

      int E();

      boolean as();
   }
}
