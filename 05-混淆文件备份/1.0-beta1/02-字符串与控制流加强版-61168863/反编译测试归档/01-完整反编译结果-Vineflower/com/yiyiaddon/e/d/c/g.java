package com.yiyiaddon.e.d.c;

import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class g {
   private static final int[] k = new int[]{0, 8, 9, 17, 18, 26};
   private static final int cx = 7;
   private static final int cy = 5;
   private static final int cz = 2;
   private static final int cA = 40;
   private final Minecraft o;
   private final com.yiyiaddon.e.d.a.a c;
   private final g.a a;
   private com.yiyiaddon.e.d.b.c a = com.yiyiaddon.e.d.b.c.IDLE;
   private int cB;
   private int cC;
   private int cD;
   private int cE;
   private int cF;
   private int cG = -1;
   private int cH;
   private int cI;
   private int cJ;
   private int cK;
   private int cL;
   private float Q;
   private float R;
   private ClientLevel a;
   private String fp = (String)com.yiyiaddon.m.b.a<"s1lzmfw42j8unz","qj0tE6ImmPVq8Lt8tP8E46oG3CkR/UGnzcSGrQ==",2649992785071553760,6297062249610851010,-6236636727716266119,1928817206455334865>();
   private Vec3 b;
   private boolean au;
   private int cM;
   private boolean av;
   private int cN;
   private com.yiyiaddon.e.d.b.c b = com.yiyiaddon.e.d.b.c.IDLE;
   private boolean aw;

   public g(Minecraft var1, com.yiyiaddon.e.d.a.a var2, g.a var3) {
      this.o = var1;
      this.c = var2;
      this.a = var3;
   }

   public boolean ab() {
      if (this.a != com.yiyiaddon.e.d.b.c.IDLE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wdf1295hnqq8","5wTzGRJmcovtaQnUmI/yxXN1j+kFAF4JbdNH+y1vT4c=",6522658431153970008,2447644122870366321,-1811678758355902361,2851286926220948370>()) {
            case 1381102343:
               if (this.a != com.yiyiaddon.e.d.b.c.COMPLETE) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1vevmbywlaucg","bhKumjYkwdf7W7CbAa5vKS6693uZ/tAYhG00OshofWQ=",6850634853814238166,-4852921261980014904,-7602276077919980430,-8957394403973713433>()) {
                     case 721083940:
                        if (this.a != com.yiyiaddon.e.d.b.c.FAILED) {
                           switch ((int)com.yiyiaddon.m.b.a<"sn9em7i110fpv","HeZkaOjXIkwi2Z/ToTNv5p4UG61R+1efi2cTKvqGoeg=",5964963627555442523,-2916140197970013352,7725274489007585000,9057863898358456847>()) {
                              case 1824936875:
                                 switch ((int)com.yiyiaddon.m.b.a<"s25qwz7f3pszg5","eO+kJm0N5sty+YCotnTtregU3jLslE/OSa+xdHlFrkg=",596193746175130511,5137163649475925623,-2857807762291786969,420889232135160057>()) {
                                    case 1808380276:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3m5wb3gxlxei","eGqLQJbEx/kAz8UrL0oQTW+T61nIOz+FsIH6lE8W8aA=",8192223829996959580,-7792892841230470552,-8974065579913424457,-9213225189288757383>()) {
         case -1134748371:
            return false;
         default:
            throw null;
      }
   }

   public com.yiyiaddon.e.d.b.c a() {
      return this.a;
   }

   public String ao() {
      switch (this.a) {
         case OPEN_LOGIN_MENU:
            String var12 = (String)com.yiyiaddon.m.b.a<"s1r857pd9kcw5y","lgCUf05ShnhleXamohX0e62IOFT8Cfh3JyaR2Qtd36jSLq6Embx1xUvJ",-2888372991942156467,4706163073949338661,-4092997943869184698,1484745960106738577>();
            switch ((int)com.yiyiaddon.m.b.a<"s2cg9yeudddav0","cNflAGHwi6+ep6USo6q70V5lXF23xRXzoMkn2kYNYYs=",-8377377745519899656,-8963767674338747259,-3417815976205409550,-4299477114551612784>()) {
               case 1344283829:
                  return var12;
               default:
                  throw null;
            }
         case CLICK_LOGIN_SURVIVAL:
            String var11 = (String)com.yiyiaddon.m.b.a<"s2j9nmlnv5y635","sCRMakDxeM7bghSIPhMPoD25uic+8elBAY/c4ddNb4PIHuVxBYfN0lBOfAo=",23837159864466103,1075156930878443062,-3554801452150129459,-6745746002800287497>();
            switch ((int)com.yiyiaddon.m.b.a<"s1jj6mnds65271","j/dC4w1FNeLVcqxHSFwmUshEeM2ceadgYI22dbfIh1M=",-6399255348064750855,1197760118490104609,752598154254474160,-4922836166789041898>()) {
               case 793133093:
                  return var11;
               default:
                  throw null;
            }
         case WAIT_WELCOME:
         case SCAN_WELCOME:
            String var10 = (String)com.yiyiaddon.m.b.a<"s2or0s7ifgrz6y","V3EpI3rEHnKfy9fzK5mECXKvPIdLdNz0hFgHFrFLTKsD11nGfBM=",-4052426956785575130,7177427228466031899,-1286459664671317653,-4558066199247389067>();
            switch ((int)com.yiyiaddon.m.b.a<"s31gyb477gjg7","s/9joxEXXq08QkzxM3k37h+K4YBL61Mt1JiQBre1O1E=",-4485694123778237500,-5416596696782944723,1699825807934361367,-5129314909528458617>()) {
               case 340189777:
                  return var10;
               default:
                  throw null;
            }
         case WAIT_MAIN_CITY:
         case OPEN_CITY_MENU:
            String var9 = (String)com.yiyiaddon.m.b.a<"s2n3gmqhdpfrfz","7O5jKefhMbhEO9jlSlWMg1yDZuAGtRPSXbGWiudX+Ka17IagzCHeOdaM0PpYQA==",-56944333439864161,8732557601005869103,7591262105555529599,1723890587557077926>();
            switch ((int)com.yiyiaddon.m.b.a<"s2po9p4p6pmpvn","FsEp38WI+aELurxobdGxJUuDQ6TNGfnCw5fxocUmURg=",-4126987241106667245,8183400233902981672,2284970459093544663,1736280387745478502>()) {
               case 1256701923:
                  return var9;
               default:
                  throw null;
            }
         case OPEN_AFK_MENU:
            String var8 = (String)com.yiyiaddon.m.b.a<"s2gyzvym56x0zx","GP6JXuQl56Eme3t8+Z+C9c/dJAoJdSaFGJyDG2/zwqDKBo1Wo9U/OJ37",-6895976977274655125,5139199805046849992,532421911360093896,-5802453442258661693>();
            switch ((int)com.yiyiaddon.m.b.a<"s3oifgjqo353yx","k4j8qNqF0uCjgoD0t0aB9y8GatrS4vAl6tbfuORSTGQ=",-1741435801493176336,-5834501240754224156,4754163589588195823,-5700714055134540747>()) {
               case 510330687:
                  return var8;
               default:
                  throw null;
            }
         case CLICK_RETURN_MAIN_CITY_HALL:
            String var7 = (String)com.yiyiaddon.m.b.a<"s1t1elxb988ziu","1ig/OwUuIyzsQkDM9RixoWNyR5/H7LJ118HCXYLRoCS6ZmXZFrKsQRK25+8=",-7838120233402231914,-2747599835585995667,5412422190671808293,8559708108801006309>();
            switch ((int)com.yiyiaddon.m.b.a<"slwjwsoq4uyce","Codfjiu5X4poWx6LYzJYTBfDf0/LOQHs5FAcaiISr/o=",8856339727096679505,2273586464558704351,-2519175147303826306,-4929738281316927319>()) {
               case -2146447442:
                  return var7;
               default:
                  throw null;
            }
         case WAIT_MAIN_CITY_HALL:
            String var6 = (String)com.yiyiaddon.m.b.a<"s3b4z0o96v1tvn","m5KW3kBdDodHj2ED64Dz0mmypSAObYwwKz3HauTjqcdzz3QayKvAcg==",5949340617235536193,-5518730862859577906,7015663974372685484,6471433551990351142>();
            switch ((int)com.yiyiaddon.m.b.a<"s1qxpx0q2ccgw7","FRre3AJ+cmKMX8y++ldM11I0nNrwwFBfPLIyqZHAeKA=",-17578094675356499,5581440414321556906,501747221260273793,-6586953045628987457>()) {
               case -1574630171:
                  return var6;
               default:
                  throw null;
            }
         case OPEN_MAIN_CITY_HALL_MENU:
            String var5 = (String)com.yiyiaddon.m.b.a<"s3c4jf994keq8g","zXdRlHgfQ0ATL6L8uL5hT0pVh928Yx2BSCd6jDBl57On9tFsnUrFYTZrXOA=",-3542024802314016141,-1102196814126986675,-822313971513652589,-9037782446241769157>();
            switch ((int)com.yiyiaddon.m.b.a<"s2jvt4d16267td","Bjl5x1RPXQyDkD+2FxRi1xDioO4DmEmc7kEbCRLzaBE=",-5247529736011771847,-7020004714555827655,4505171051300558305,-415905266091919893>()) {
               case 1812284667:
                  return var5;
               default:
                  throw null;
            }
         case CLICK_WORLD_TRANSFER:
            String var4 = (String)com.yiyiaddon.m.b.a<"s2tn132bn2dha","e6EXgxZCR3O/eMRoWr8GkljIpbv5ekBWiFrk+vZdQftvAx2tKLNwbA==",3306599182001870177,506982953138186622,-7967535410236652407,8075681717293108020>();
            switch ((int)com.yiyiaddon.m.b.a<"s3qwdmohjrxryn","FeommVHnmS0sZeJb7XdyhEVVj8dXQlkpKSdYy+I2RYo=",9026268140794478710,8058211652183459006,-486363800581376987,3424191341782493631>()) {
               case -739084672:
                  return var4;
               default:
                  throw null;
            }
         case CLICK_SURVIVAL_FIRST:
            String var3 = (String)com.yiyiaddon.m.b.a<"sgc21ea33mdc","WtRo/RnTn1SlYZuHC72bW3DaHYQ+lOVibuiVGtCKmqYn9+x/RWH71SyQtRCGKg==",1606276658460352708,4290440154142968689,-6718110212348711526,8472550705683948279>();
            switch ((int)com.yiyiaddon.m.b.a<"s2zvblws1xcmw2","Gg/eB5BaBTzJJQQ1bmMYs+Y/JtuMpY8aN0RN3/2t71A=",6188433549280290946,-816294763638687194,8766847181628771467,7868017389524342280>()) {
               case -617598002:
                  return var3;
               default:
                  throw null;
            }
         case CLICK_SURVIVAL_SECOND:
            String var2 = (String)com.yiyiaddon.m.b.a<"s1jy85fk2umtrw","RDtIzMkBCF359hP23aN2v6WQ9/wDPI6oI8UXGxzWE4880H03o5F1RlW/t5o8tg==",3228010201008963806,-8732090900345057654,-6918929853942679213,-1503527072531214221>();
            switch ((int)com.yiyiaddon.m.b.a<"s2a8srdftmrz3h","YbsmhoRG6OWC3cWPGWK7cJheP7/oUHCBCNfEdqk/KXk=",-8062829720452593667,-3850542422519809973,-3198480436064503328,5600036935495809094>()) {
               case -865145919:
                  return var2;
               default:
                  throw null;
            }
         case CLICK_TARGET_SERVER:
         case WAIT_TARGET:
            String var1 = (String)com.yiyiaddon.m.b.a<"s2c886n0wt2bmo","87oQ0MpTpk5UwyKA0S8atOjftRr93PPufBgmXwnDoOP2DhnmLats+tWAh7Q2Pw==",-2929603196008838129,8908294099484791011,4846117760246462252,-8934758991873522177>();
            switch ((int)com.yiyiaddon.m.b.a<"s1uv8xt4j1hcaj","/ekn28u8cMPUNR70ajQZ3rd1D8sNmEM4ntUtoR8+LW0=",4948028017319887256,4926352301364460201,-412873443130389991,-4990584663801348164>()) {
               case 1923732326:
                  return var1;
               default:
                  throw null;
            }
         default:
            String var10000 = this.a.toString();
            switch ((int)com.yiyiaddon.m.b.a<"sr1y3pb9wks9f","6HqE+i8YomON6vFKtOMlKRKbzkpaQUqCD1qqtX3eBYg=",-440674584750418236,4856779718901695906,-3331898471012587209,-1263579344524533112>()) {
               case -1109163774:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   public boolean ac() {
      return this.au;
   }

   public boolean ad() {
      return this.av;
   }

   public int y() {
      return this.cN;
   }

   public int z() {
      return this.cB;
   }

   public void aR() {
      if (!this.c.ao) {
         switch ((int)com.yiyiaddon.m.b.a<"s1010c0djrul7h","2k8vvXmMAZehABW0SoWLjTz0RnB1nmRydmKhDu4cLCI=",-4432327704590545508,-5372613335607314167,7201172230341047207,-4675174252538089950>()) {
            case 638970623:
               this.a
                  .u(
                     (String)com.yiyiaddon.m.b.a<"s35pala17l4sal","RCfz5Li7xiPiABOb0Ou9Ws3vvr9WQf2KEZeafjhn81e/Pok3vBxbwLUveR0B/zUsRQyDsBxReZM3kWQlgirnkJzZWC9bipMh",-3022760272172660179,-4702031188120944431,4392263175398089027,816215633707883720>()
                  );
               return;
            default:
               throw null;
         }
      } else {
         this.cD = 0;
         this.cE = 0;
         this.cF = 0;
         this.cG = -1;
         this.cH = 0;
         this.cI = 0;
         this.cJ = 0;
         this.cK = 0;
         this.cL = 0;
         this.bh();
         this.a(com.yiyiaddon.e.d.b.c.OPEN_LOGIN_MENU, this.c.co);
         this.a(
            1,
            (String)com.yiyiaddon.m.b.a<"sg84s1r0fgecg","OKWaubH184pvk8uFLDgiMmgJ0J2xRPwYieYY+lB/bK45E1K4ViYIDMJWwV0TgQf1miZAx/ECAPA=",8363141791023424827,6929818316130745175,5913948937159274574,-8786509997437018323>()
         );
      }
   }

   public void ag() {
      if (!this.ab()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ak37w25ykprz","m3+hBMV9Y7G/vnd6fX2hexFsN4AVU6H63lmStjxB/1Q=",7600569653349155822,-4958859798238222296,8290702516394765484,-7976018206201168468>()) {
            case -787034399:
               if (this.cJ == 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1kl23gi66qu01","SGKd/P5UyxvIkNx8d+ob9ewtPv3cu0jnAlmL9ydCSr0=",-3555913166652415285,-785854786403707031,9083336984315350954,3350282994550403451>()) {
                     case 2011692140:
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

      this.be();
      this.a = com.yiyiaddon.e.d.b.c.IDLE;
      this.cB = 0;
      this.cC = 0;
      this.cH = 0;
      this.cG = -1;
      this.cK = 0;
      this.cL = 0;
   }

   public void f() {
      this.a = com.yiyiaddon.e.d.b.c.IDLE;
      this.cB = 0;
      this.cC = 0;
      this.cD = 0;
      this.cE = 0;
      this.cF = 0;
      this.cG = -1;
      this.cH = 0;
      this.cI = 0;
      this.cJ = 0;
      this.cK = 0;
      this.cL = 0;
      this.a = null;
      this.fp = (String)com.yiyiaddon.m.b.a<"s1lzmfw42j8unz","qj0tE6ImmPVq8Lt8tP8E46oG3CkR/UGnzcSGrQ==",2649992785071553760,6297062249610851010,-6236636727716266119,1928817206455334865>();
      this.b = null;
      this.au = false;
      this.cM = 0;
   }

   public void ae() {
      if (!this.ab()) {
         switch ((int)com.yiyiaddon.m.b.a<"s18wwbqhffmvg1","JJ1M0AhqZlVD1nFR2mves13uDhbUD8jB5r75LTO9Bao=",4552032673934321360,2287449385099692991,-7429038884024079465,8941981959390872489>()) {
            case 406985652:
               return;
            default:
               throw null;
         }
      } else if (!this.am()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2teytd0i09qr2","yUkOGqvA9ptF5tpzIx+YP8T6NJRbvqeItPLE5RoEaQs=",1194388385443694156,1926890212293161854,1766581016737573019,-1820503362349352143>()) {
            case 2125745428:
               this.ag();
               return;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         label327: {
            if (this.a != com.yiyiaddon.e.d.b.c.OPEN_CITY_MENU) {
               switch ((int)com.yiyiaddon.m.b.a<"s2bgg4hyvlh1tz","5SI3sSZZyOVolmUO+hySKeBKIKSBFLl0MHwcLuNAuDU=",-323677888185502192,3751900719569179529,2372077299474363345,-5639096856578442312>()) {
                  case 1980355007:
                     if (this.a != com.yiyiaddon.e.d.b.c.OPEN_AFK_MENU) {
                        label289:
                        switch ((int)com.yiyiaddon.m.b.a<"s2w2vubwjhcg65","8zGDLw7DCiCNcNaa2QepQyg+c9myp7uaH9MgaLl5oSc=",4642213273657256677,8661833385145544046,7444491458547843452,-2908161058198708196>()) {
                           case -12543098:
                              if (this.a != com.yiyiaddon.e.d.b.c.OPEN_MAIN_CITY_HALL_MENU) {
                                 var10000 = false;
                                 switch ((int)com.yiyiaddon.m.b.a<"s20mudizhhyyg1","uC7fzmzbIo0lUVpx8BLiDA+4YZD77heYRQY7CjqG7S4=",4660162058835225224,6830989432453124346,-1304014641932521121,-2790141923745478316>()) {
                                    case 2014685498:
                                       break label327;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s16787kzlok22n","IZymz03VlE3S5LNkj0yoSB+uN+sC6NieicIrb/L1lIc=",5090726750425939070,-1694687986743685039,8128635525974442130,5208115119850247810>()) {
                                 case 346396791:
                                    break label289;
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
            switch ((int)com.yiyiaddon.m.b.a<"s2bp8o9brzw6k2","1U95R/IHyXO0/Pn6GnBRmoOaWq2mh5zOakbpErZeOeM=",2142639893407104294,-3662491124468615210,576482289289623498,8163459061735852567>()) {
               case -152888194:
                  break;
               default:
                  throw null;
            }
         }

         boolean var1 = var10000;
         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qh0s207du725","qSipkS561G5rY/m1CvzQ4S4r0bgHMXu6Hb+/LoVQq5A=",-6137313219723350609,4569692237875575110,856557972432768995,-1871820287018172981>()) {
               case 1917900413:
                  if (this.cK >= 2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2tkcd9b0t228o","mB9z3ILXw0EmOAIKOONGjZVeoX68tBH65NiO1GOa6lE=",-314651838813005577,6393717806555187652,-2482444144476678144,2657191672998783007>()) {
                        case 248085104:
                           if (this.cJ == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s320kroy4p46m0","1BJORjUaqoNQnOIusnf23CBDu4WUV/5UPUu9XM4AoQ0=",-2757433982468389466,-4113018879638444667,2161313705930694695,-1482153915097331490>()) {
                                 case 1839875119:
                                    if (this.cB >= this.cL + 40) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2x9hhayf48pwx","uAeDL9GVygoUiWlkbzkGHrtfo5uhkb1V/WtO3js8rw8=",-2112151198360412211,8549723770739652406,-6612706669417003839,-898741470565137128>()) {
                                          case -470469616:
                                             this.a = com.yiyiaddon.e.d.b.c.FAILED;
                                             this.a
                                                .u(
                                                   (String)com.yiyiaddon.m.b.a<"s9oi1hwfarhgi","PxvSoDX6Kmkj5ONQ0EhlDGt4X/HaT0Y81KL5u1n2iHjqIrwW0+kDdh5b4CkHvE5JTEvRT4L2/cisGhBuFDTOWmHZy29nLpQ/I8lmWt079IHtWyySUPpctgEHMGxytT1hxQTdB1vY9LwdXkJYtjc=",2677128442150853155,1907249490981450824,3873816615484574443,-92961535156154995>()
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

         if (++this.cB >= this.c.cp) {
            switch ((int)com.yiyiaddon.m.b.a<"s1y7h7mn4t1ezl","e/3rmKcKMGsZq0UBHA0so0yinvvDbewWXfTSaOJLdGA=",8957380223633846280,4134653598646462877,-3590461601726844095,2898666271130435457>()) {
               case 565629580:
                  String var3 = this.ao();
                  this.a = com.yiyiaddon.e.d.b.c.FAILED;
                  this.a.u(var3 + "");
                  return;
               default:
                  throw null;
            }
         } else {
            label335: {
               if (!this.ao()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31qyzljny56gy","CRYUqGELpKNBiMbBNUvUQ37Sl5fsrrRqKQRX+GKC3HU=",4189612417775045849,7089484004837117614,-40236880109620705,-7068859969792570448>()) {
                     case -1285156244:
                        if (this.cB > this.c.co + 20) {
                           label272:
                           switch ((int)com.yiyiaddon.m.b.a<"s9bxlfnknok6l","qNqPb4tNHEhDji1X8nUUYE8YBI0xn34Kpd4PeQBJiFs=",5501017291735183689,3084258602589753517,-8178701629468850562,-7322554819472811575>()) {
                              case 604564019:
                                 if (this.a == com.yiyiaddon.e.d.b.c.CLICK_LOGIN_SURVIVAL) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1f65hu9u8y4y5","Pakn3CKHuxhFpz+jgWMOQVX6WWMMpHGHE1Mvu/jhfzs=",6686066723501136570,6266664971784201729,-1355202839579777999,-5806879230875834412>()) {
                                       case 59757104:
                                          this.a(com.yiyiaddon.e.d.b.c.OPEN_LOGIN_MENU, this.c.co);
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (this.a == com.yiyiaddon.e.d.b.c.CLICK_RETURN_MAIN_CITY_HALL) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2n6idi16l8vzg","vJP+R19QTfiWXh2QhoPX+n6HNb6kOxHhC6FEtr4iBGg=",-7504322441381538185,1077586846061074069,-4093536034982013880,-6966712178958538285>()) {
                                       case 250442153:
                                          this.a(com.yiyiaddon.e.d.b.c.OPEN_AFK_MENU, this.c.co);
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (this.a == com.yiyiaddon.e.d.b.c.CLICK_WORLD_TRANSFER) {
                                    break label335;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2ha84y018bghq","3C8cP/6SHADgww/fVTNknA0LEyQIbF+lejQSD3ZfADE=",-5089577093495887940,2519973267863124537,-514462556617486371,-3990931289228693533>()) {
                                    case 367131359:
                                       if (this.a == com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_FIRST) {
                                          break label335;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s13i9m2cmox81k","BWlUkix3oDGFlnvXdgfScUtK5m+VUzpUB7GRfz3b7AA=",-3893855354461143379,-1222230140264747718,-5763936188160896616,3715579067815068940>()) {
                                          case -575384178:
                                             if (this.a == com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_SECOND) {
                                                break label335;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1y2demkdx8397","rnIxaVoAWdVpMMroNmVwz+RMYRsTDsO/Az6k9EGzAw4=",-4864464766413242338,-5520808065149004376,-8175554672097805419,-7298564773442604698>()) {
                                                case 1310120862:
                                                   if (this.a == com.yiyiaddon.e.d.b.c.CLICK_TARGET_SERVER) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"slodqr00z7b89","7VM+t5zoLhISZQXLql3WNIvKOEW2lbUA3/v7Moayn28=",-2589258832531599547,8001983469158448999,5294309783718155032,-3547594542848658386>()) {
                                                         case -1352758443:
                                                            break label335;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break label272;
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

               if (this.cC > 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s81tcyrw7kmno","mZQQ4Uf/eDcsPskHy9LFqnHIVsO9kb6W6v1gFWPJYF0=",-1489335349672117320,8693247601416511837,7797285105130030375,-2224740311280462012>()) {
                     case -1249453357:
                        if (--this.cC > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"skot2e6tmfy76","2mgRaUFaQBGyaaAi598WspYh/gD7q9JqZw8iB3yUC68=",-2763682779421325260,-7225460882110961984,-5956424574161535386,-7929305986003952286>()) {
                              case 1214959854:
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

               switch (this.a) {
                  case OPEN_LOGIN_MENU:
                     if (this.aj()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s36y0s7ll3ople","3UjCm5mcuwbhL/30VWSlO4xi4Or1wGecefkdY7l2Zkw=",-1184981403039131653,-5966179705679472681,-7770873450289606518,20060245514012859>()) {
                           case 1307689729:
                              this.cD = 0;
                              if (this.c.a == com.yiyiaddon.e.d.b.d.BOOK_DIRECT) {
                                 switch ((int)com.yiyiaddon.m.b.a<"svikg84003la8","cRuV3iqnBc9bkGVso8cBnKuHhvmDr/bAhTbXlNK3bic=",5175729746631666438,790267556017025292,6111526739904076467,2861549240548231432>()) {
                                    case -2042920135:
                                       this.a(com.yiyiaddon.e.d.b.c.SCAN_WELCOME, this.c.co);
                                       this.a(
                                          2,
                                          com.yiyiaddon.e.d.c.b.x(
                                                (String)com.yiyiaddon.m.b.a<"s21fatqgsi0h6n","FQiZU9AnZoPp4y/7eh8FW1M/+ac/wSNWIfep2xnFBn94wGXscU57tg==",6539250566439876925,6377891973316735392,-6473562186908625307,-4359508149099787341>()
                                             )
                                             + ""
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"sg4928mq3p5ez","ylUpfDZ0mcpfDphB7tuApCCF/5QRQZ8j2bIM1EMICD8=",-7968418824940411433,7626743353933962860,-417321067990766301,2952690151480269742>()) {
                                          case 859703237:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else if (this.b == com.yiyiaddon.e.d.b.c.CLICK_LOGIN_SURVIVAL) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sztwwn57mhvv9","/GujuB5BybT/ix/ahpVBZMRrBqREXbf9X0Rc++nynJE=",-7605992326933218124,4908339042574852456,-9101178484067502176,-8450405849555597081>()) {
                                    case -1064648933:
                                       this.a(com.yiyiaddon.e.d.b.c.CLICK_LOGIN_SURVIVAL, this.c.co);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2tw63wwu2sgf0","kBpSOiNNN4yGLqhety5XeXHtHV2hiFUc7UJ85FlFUl8=",1756186712751789990,-8327829339205919773,142886652255806902,-6658272361742141458>()) {
                                          case -245372447:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 this.a(com.yiyiaddon.e.d.b.c.CLICK_LOGIN_SURVIVAL, this.c.co);
                                 this.a
                                    .u(
                                       (String)com.yiyiaddon.m.b.a<"s1t62bs1ri4iy0","9GnDo9LCC27exCIKPSetIidHrCRlXgDNm8XPwwn099AC2xomOKAH76vpTwn0o3o19SnYTeF/",5988370941671372312,-7430499915048208116,8600435086027408735,48988019201884668>()
                                    );
                                 switch ((int)com.yiyiaddon.m.b.a<"szsskm16i997c","gYmuZzU5jG4JwuCfg+mc7vRNxoJ1LcrS+7tJbFhyYPs=",1966998751618627127,1561866796554040656,-5372715187974496617,2903103466041291700>()) {
                                    case -1753637426:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case CLICK_LOGIN_SURVIVAL:
                     if (this.c.a == com.yiyiaddon.e.d.b.d.BOOK_DIRECT) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3bfbv1mskhi3p","ykfhg6zctOCs2dz//s4hSfRpnM1tCuDIBQpB0Kt8aqk=",8557505868508590697,8338723309056289704,3611142037046848701,3072174546951717181>()) {
                           case 1241316467:
                              this.cD = 0;
                              this.a(com.yiyiaddon.e.d.b.c.SCAN_WELCOME, 0);
                              switch ((int)com.yiyiaddon.m.b.a<"s1z6jxyjy66ucy","YerqXwKSkQXcwi0MmR4ooeZkAS0fypZMYVhBnvGjfrg=",354487573104457639,2715794309243344831,-4618845052502713879,-442702440767915672>()) {
                                 case 732755081:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (this.q(this.c.cs)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2l43eotp39ubj","ruZrTfw0QvAkb/kSfvNaQ8Em/vnBtPooVIeIr7t2xJw=",-5717338222014649407,-1265153811373501217,9216699183432683895,-6765392817832031222>()) {
                              case 588220368:
                                 this.bh();
                                 this.a(com.yiyiaddon.e.d.b.c.WAIT_WELCOME, 40);
                                 this.a(2, com.yiyiaddon.e.d.c.b.x(this.c.cs) + "");
                                 switch ((int)com.yiyiaddon.m.b.a<"s1p5c7o3zhok36","yxkj47G7wEXlSbr7D65Ke6smInYin1HIQozFCC7PxF8=",-8338045963909921333,-8998287531828401771,-3433667468502040657,-5140384645632614036>()) {
                                    case 1158752059:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     }
                  case WAIT_WELCOME:
                     if (this.c.a == com.yiyiaddon.e.d.b.d.BOOK_DIRECT) {
                        switch ((int)com.yiyiaddon.m.b.a<"s6jmo3r1fg48h","DsWM5iOpijITli/GvdmcUMB5vZXk8fZfA/OCM3ZFPXY=",-3094322633844188666,-3929428774396656704,-5480026534133885716,-7231748580875265847>()) {
                           case 1425438237:
                              this.cD = 0;
                              this.a(com.yiyiaddon.e.d.b.c.SCAN_WELCOME, 0);
                              switch ((int)com.yiyiaddon.m.b.a<"s27t071gj3eiiq","dMEGw7AmZi1G0vPFzgWKlzpodGrMEp0PJSUOomaqrhM=",450369875921806748,1938378749654915337,360697028809945081,1007578087128583907>()) {
                                 case -128545364:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (!this.ao()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2fwahz412iugi","qxY9mbg3QW2Uz0xbUMz+LevYWNdLYKUJX71PWdf3E+A=",-5137550526647608250,-2245008331646395065,728616546952538852,-9063372951361353700>()) {
                              case 1498331916:
                                 this.bh();
                                 this.Q = this.o.player.getYRot();
                                 this.R = this.o.player.getXRot();
                                 this.cE = 33;
                                 this.cF = 0;
                                 this.a(com.yiyiaddon.e.d.b.c.SCAN_WELCOME, 0);
                                 this.a(
                                    3,
                                    com.yiyiaddon.e.d.c.b.x(
                                          (String)com.yiyiaddon.m.b.a<"s3t0v79lfebl2a","zWdpsdwewZ4b2/7sh/h8R7FSOfflMUutfzb6dfA90OgZCg==",-9193359200143962205,4767262780775678092,-6495402946255600588,9137081061980201239>()
                                       )
                                       + ""
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s14xjzbh5u1vq5","5+nEPtW48JaVia/smEuRhuxYPr6O36RSER2nOqH56qk=",6057036160445173448,-8382515553069435820,2969248395599377244,-2487668094905598894>()) {
                                    case -513755978:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     }
                  case SCAN_WELCOME:
                     this.bf();
                     switch ((int)com.yiyiaddon.m.b.a<"sjc0gjczjf753","+81ELcHUP4KUxfca7bg1R5bs6CAWWUYWHuzk5lywlKA=",-5725003326567128180,-7485547046408969706,3185632092270141543,5189159419335436972>()) {
                        case 297063242:
                           return;
                        default:
                           throw null;
                     }
                  case WAIT_MAIN_CITY:
                     if (this.al()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1hbidf8llkelw","90TBMfcFW6pGyV9WwzfMX5QLoicKTa2aBAsAh5Kpf0o=",1504906142512773525,6583749858015245252,-3168820729810648231,1476832766574045685>()) {
                           case 2071392712:
                              this.a(com.yiyiaddon.e.d.b.c.OPEN_CITY_MENU, this.c.co);
                              this.a(
                                 4,
                                 com.yiyiaddon.e.d.c.b.y(
                                       (String)com.yiyiaddon.m.b.a<"s3a18wlgij2jjh","b4KOjLm5Dg9jyszKd4eIqtxAbyklk8rictepIGlTAmyZmLJ8",4600002194203866777,4413327935356660808,772333627139395734,-2303650783918560307>()
                                    )
                                    + ""
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s28b4gx5if9fi2","Tb6y1zSo+yJ7XvnHUaB6IKZLZfcfxyKqxtagYy0OfJI=",6813581852030064402,7281675220408215641,1360374093740932410,-6187924832113737279>()) {
                                 case 356034998:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case OPEN_CITY_MENU:
                     this.bd();
                     if (this.aj()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1laihv7pqplul","TKgGzNIHiwf7i+BfMOn6auFF0bjLSWOGY1TXSu8MOqI=",1145314634469849442,1870691199563268058,6450830533879305867,-1875704294987762206>()) {
                           case -1368426399:
                              this.a(this.b(), this.c.co);
                              this.a(
                                 5,
                                 (String)com.yiyiaddon.m.b.a<"s39m2ncwu5r1f8","LQhHFdZHqqXhdEZL6QCX20zzES8eWrZSRtmsEw6pSEEyrBqery7jmmREUTbXPATL5TuAXj5msrVLjBjw2nU=",-5988120728358197997,-706550563549812335,4532660415432751180,-5316268100305811107>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s3t9nagyp8kn9k","qNlPXRarcG3ccDtkXa7N2r/cslZ+1mFFh5fNXrtnRF4=",-4012579404830458426,-3688840122836192180,-3222073660368924322,-4301333363721631207>()) {
                                 case -1526276155:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case OPEN_AFK_MENU:
                     this.bd();
                     if (this.aj()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ovy4aeed20pv","Qkl4107jiCcmgy6H57fs2XMOKbx/Tviau9EtLJdgUDU=",6023352600725848307,4782916147838314057,8223700503990276036,-4437502990762638683>()) {
                           case 1744498383:
                              this.a(com.yiyiaddon.e.d.b.c.CLICK_RETURN_MAIN_CITY_HALL, this.c.co);
                              this.a(
                                 1,
                                 (String)com.yiyiaddon.m.b.a<"s1o42yljzn366f","qcCQKePH1mHj4uGRCxEeO1y1hrx5km8GvJKjCrkMdt5MxhrzM/DGpv4Vi3XAXqvPEo0ZpBbMB0vFu7wHFH7QAqKkQG8=",8600065671646448407,-7351067039258240277,5235053410383790551,-2153884255598789698>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s2w6brkc5z6fjk","en7gPwIVRgoMmGHiM+DmDizKwa+PZfTwE8S0mPlrbIM=",5481755347785809963,332137528840717591,-5068379673706990410,111680574619599736>()) {
                                 case -758238688:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case CLICK_RETURN_MAIN_CITY_HALL:
                     if (this.a(this.c.B)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2x73mqk1cpg36","r5JAsJ8yBROdex4ln/OCDI44tgWAuPI4SF5gMqxPKN0=",-1225031875824837175,-6053821903312426676,3742494508451618190,134698709602101731>()) {
                           case 1801530055:
                              this.bh();
                              this.a(com.yiyiaddon.e.d.b.c.WAIT_MAIN_CITY_HALL, this.c.co);
                              this.a(
                                 2,
                                 (String)com.yiyiaddon.m.b.a<"sqqph0izm3xc","9SQWHc4BvcBLnrjFWmaaAaiOgB/DRqp7IpLt0g4m7e9xBKCHkcyEDIsbYSSGZpkrNTqdQdvE9q1LlQzJVcs=",-4794907792938745571,1564515142902734598,-3693673662369779041,3260615776020601785>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s9qp6nreuqx48","Tp4WI3fyI3wcr4PfE8Cdd7/oO2uYjENEUWEx+TojGAQ=",5407000833686880487,-1208769897845709789,3718887343102307672,7567224503791927288>()) {
                                 case -1454564037:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case WAIT_MAIN_CITY_HALL:
                     if (this.al()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1jvpi8zpyrrav","mItE9avbByh8ZuJWEVukZASW+NHEpzJXdlZX/QN37r4=",105854429346651747,-5158086273064767014,-3808204046493430298,-3485643459993253561>()) {
                           case 1034620941:
                              this.a(com.yiyiaddon.e.d.b.c.OPEN_MAIN_CITY_HALL_MENU, this.c.co);
                              this.cK = 0;
                              this.cL = 0;
                              this.a(
                                 3,
                                 (String)com.yiyiaddon.m.b.a<"s1s1ri29xj1xdd","OEIC7AeDS56V2NFVknCCuOQ/WD8TBWJb9w8takLZWZfTngaTOO/Pc3mtg4ms/KpzmbMFHLSU10UVfE9oAC0=",8437909368415995161,2369855877758459208,8192759005231703172,4304115855167002391>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s2owcogl4etma4","4bHwnq7BVXlo9zOzGJwduBaZCG4khnZKGr55FxMqJ/c=",8932970410960248819,-7106994407732713510,-232798259113587555,-5619706608622968406>()) {
                                 case -1340897282:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case OPEN_MAIN_CITY_HALL_MENU:
                     this.bd();
                     if (this.aj()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1nrl6j91u78ce","fcEkb8s2rSNnmL0IybWfomcUJgh0muW2VVWGVICpN8k=",-4093886929233744109,-5356046021040480194,2755738594813473820,-7771751287719525199>()) {
                           case -982638409:
                              this.a(this.b(), this.c.co);
                              this.a(
                                 4,
                                 (String)com.yiyiaddon.m.b.a<"s1y47jfjb4txa6","Q69ANtZjQrHVTn3Fo+vIFR85GvOqNXfeB4l1Ry5Z4cAYVUqLUChCwdQRvFDy5MT52PCbBFtCnigsOtcAQ0AI+5Ln",2299754132378183450,1874532067819900524,-8328411321559065035,-7897109703938603695>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s27kr4mq6vidmc","UeQcG/3+tAka5WJwMcXe4xlgvAKrX8zqs08pvd324Mc=",1562031228296080076,5947854061702944820,-1225537584142063550,6898580367749504757>()) {
                                 case -651441123:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case CLICK_WORLD_TRANSFER:
                     this.a(
                        this.c.ct,
                        com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_FIRST,
                        (String)com.yiyiaddon.m.b.a<"s2naeys5d6yo0f","eCft2MPXYlXFT1JFKYvP3pco2Ap8es3fgpKzLubUDevyz2XJ",-8637404569363348090,7743982683834513551,8667593393086487337,-3088759995846034577>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"stv0bd9782k2y","w71vDwa64rQYlRBk58s2Tn4YUimMdT6pWInXoM3owiA=",-5649343028796113007,364695019159810824,-7251828305983010854,6112866872325473233>()) {
                        case -1614921422:
                           return;
                        default:
                           throw null;
                     }
                  case CLICK_SURVIVAL_FIRST:
                     this.a(
                        this.c.cu,
                        com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_SECOND,
                        (String)com.yiyiaddon.m.b.a<"s2jn7h9cz89d66","vLCoiXvuClb+Y6lyC7Y8iP6tuqzi7KZ8oFvDzMQOtuR2wYXbffnAx0I6",603822292767577653,1574438657305844554,-7160334382711686892,-1608886370108779986>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s2h232xaba4uva","XJleiO4zNq4/sHcLk2nu5vdb+FYD1/v9FkToWRoKQgE=",-4781481152815705347,-4372096517792855064,-7723050766703883393,2014252557610960362>()) {
                        case -474303148:
                           return;
                        default:
                           throw null;
                     }
                  case CLICK_SURVIVAL_SECOND:
                     this.a(
                        this.c.cv,
                        com.yiyiaddon.e.d.b.c.CLICK_TARGET_SERVER,
                        (String)com.yiyiaddon.m.b.a<"sqrfh2dwnzdzh","cTP8x+bT1iaUpnd3BjW6TnM1J4fckBfwWkxqAx4JVchD13N2oljj4Ada",1801271720671672570,-7870635721900331540,-7603588629690007111,1584498917080762768>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s6ntra00yvkhz","K8XPg8Mf/Z+87XJOUNPZxaQrNQY/nyIIN/20ft4V2tY=",3581446957524589894,-7452277925765383742,-9158641494013181165,8946392464585125799>()) {
                        case -441219596:
                           return;
                        default:
                           throw null;
                     }
                  case CLICK_TARGET_SERVER:
                     if (this.q(this.c.cw)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3hwddj1wchqdh","FzBdHoJ7jFUjn06dNu48Wgb6VVa/OpoSGDQRiOLDZRI=",-8338521734096383013,3308341341544298971,-1721073809254786359,-760652657445938563>()) {
                           case 1757671056:
                              this.bh();
                              this.a(com.yiyiaddon.e.d.b.c.WAIT_TARGET, this.c.co);
                              this.a(6, com.yiyiaddon.e.d.c.b.u(this.c.cw) + "");
                              switch ((int)com.yiyiaddon.m.b.a<"sdvdaklqlhdo7","QDMdE0aBFnHo0QSNEoynin50DHehZNyiYT381PZu9TA=",707133138734937105,-143716068592173672,-7510971726631122834,-5810950880797026042>()) {
                                 case -1380471868:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break;
                  case WAIT_TARGET:
                     String var2 = this.ap();
                     if (var2 != null) {
                        label221:
                        switch ((int)com.yiyiaddon.m.b.a<"s3pyayz10k9bzm","w2SNIwwXpW7agXYjv/0s1FIfbw45hxGVVs0QvDKmTyI=",-7602903839159305689,-6563173226668231498,4170593676202836755,8683418670244321225>()) {
                           case -1245583614:
                              this.a = com.yiyiaddon.e.d.b.c.COMPLETE;
                              this.a.E(com.yiyiaddon.e.d.c.b.u(var2) + "");
                              switch ((int)com.yiyiaddon.m.b.a<"s6vh61xbdkiy6","FCZF+JJhsVq1V8p8TCRRtridhbwcBihxB4luMBojR3I=",6029320001579586772,8010084542696071524,4776720141266142800,-7837975709163072771>()) {
                                 case 1324980430:
                                    break label221;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sida5yfpqy3ik","ZsSALbLlbLAdpp5W8ogkpUkY98yselBfCOumjcdaWuo=",-1267326168112844842,9169177492860984726,2609421158598992349,-7028489120210170931>()) {
                        case -1078027348:
                           break;
                        default:
                           throw null;
                     }
               }

               return;
            }

            com.yiyiaddon.e.d.b.c var10001;
            if (this.au) {
               label179:
               switch ((int)com.yiyiaddon.m.b.a<"s2ergqvy21xn27","tydwx6XlWAYq4zyO/a6ew4J1GnfSj2N7nhpGKzfGB10=",-7755532101115610779,-1043542648643591066,-7655944892500177116,8741554273417339557>()) {
                  case 86784936:
                     var10001 = com.yiyiaddon.e.d.b.c.OPEN_MAIN_CITY_HALL_MENU;
                     switch ((int)com.yiyiaddon.m.b.a<"sliu3o8q3tw4x","iJdhAnr2q8H/TNBkKL+iJcAODCL+5hxV//t+v9EtDX4=",8985645278833853015,2252053017577962537,4894118858189785456,-902794401402744968>()) {
                        case -1809174256:
                           break label179;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10001 = com.yiyiaddon.e.d.b.c.OPEN_CITY_MENU;
               switch ((int)com.yiyiaddon.m.b.a<"s26cmz0h6t511r","m6Kdu9xGPTQ0nLEmMxWbZrBwn9TKoOtMox6UBqWgjyQ=",2769924122400873514,-1268696326896985707,6700398017160152965,-3725617473949627112>()) {
                  case -162121981:
                     break;
                  default:
                     throw null;
               }
            }

            this.a(var10001, this.c.co);
         }
      }
   }

   public boolean ae() {
      if (!this.ab()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3a9o0un5i8mx9","ENddmP0vGQk0gockVGoO/gxutP2ow8hd3G4+iDnmy/E=",-8836023741431206699,3597591010236233378,1133428989541033382,-2041883166361619754>()) {
            case 1345375719:
               return false;
            default:
               throw null;
         }
      } else {
         this.av = false;
         this.cN = 0;
         if (this.a == com.yiyiaddon.e.d.b.c.CLICK_LOGIN_SURVIVAL) {
            label47:
            switch ((int)com.yiyiaddon.m.b.a<"s2izqiqalw7vj9","14Dmy5XVCb7U1z90gJ2miugUStJplCDebI8h9eO7BT8=",-3701300201399113552,-4675146139710408179,214505910357305169,-125429477899638105>()) {
               case -1024224926:
                  this.a(com.yiyiaddon.e.d.b.c.OPEN_LOGIN_MENU, this.c.co);
                  switch ((int)com.yiyiaddon.m.b.a<"s55ed6of9uq5v","FoLeEFNiHkQbwVsccMpSVkTtVQzrJgmIRUJaJqTbdBg=",4829679901354519178,-868516737110164984,-908587342317792583,-5475746866223530168>()) {
                     case -815243269:
                        break label47;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (this.a == com.yiyiaddon.e.d.b.c.CLICK_RETURN_MAIN_CITY_HALL) {
            label44:
            switch ((int)com.yiyiaddon.m.b.a<"s19sasajmywa93","/bfxjdu4NzVhXNrLJgpsV4DxKoyE8KEq1ssRyb7sV90=",-85026663062089957,-6570537643959056369,9191579930575092709,8922211152351671899>()) {
               case 1868242964:
                  this.a(com.yiyiaddon.e.d.b.c.OPEN_AFK_MENU, this.c.co);
                  switch ((int)com.yiyiaddon.m.b.a<"s3ufdk5vmx6ql8","2VL2xtfwZuGGhjD6+9NVvqYMnWWPc1tYFg0ksV8iaas=",7071185698453157502,-1385669876577342095,-5871689510507421643,-8532168697442689063>()) {
                     case 804844289:
                        break label44;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            label81: {
               if (this.a != com.yiyiaddon.e.d.b.c.CLICK_WORLD_TRANSFER) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31fpny1zpgd1q","Kt3f3pyE5NetYRMK4TEXYKac1dt3vGVJYD5Ce+U8R9o=",8153744303373115666,-7710465943694625274,5395790981140788204,-887688153834816700>()) {
                     case -1182046887:
                        if (this.a != com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_FIRST) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1v5zfx4pagzkc","+j3ziWt3oBM38ydIaJokDbBY/F7/zcLyojQnBq9Ba48=",-1511712073291306454,1433153110413116455,-5168730331524886814,-3067783531479494992>()) {
                              case -1300270631:
                                 if (this.a != com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_SECOND) {
                                    label59:
                                    switch ((int)com.yiyiaddon.m.b.a<"s308nbgh4aj6fy","UeRRaSJVCmMY/Q1R6IP3P8ADJFX0CKntw1VT00ECSyo=",1397426437930110486,2254010232512395473,4725312266283178679,3885037682639797851>()) {
                                       case -1639671853:
                                          if (this.a != com.yiyiaddon.e.d.b.c.CLICK_TARGET_SERVER) {
                                             break label81;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s1jtnr8m7gvj26","RH1fAsiBANz69/CNHWe4F2u/UrVgIagu/phXxr8xVDc=",4494886089615978133,8710646107225105416,-5425426481920151336,-4050389633764960236>()) {
                                             case -1978800281:
                                                break label59;
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

               com.yiyiaddon.e.d.b.c var10001;
               if (this.au) {
                  label52:
                  switch ((int)com.yiyiaddon.m.b.a<"scaz9vxy4j3t","f2J+sAll2sim9li0OkwRBOQS8ch6Bsxl2BiodeAcP/Y=",-4537517450718090270,4939956767224733870,-4743644281163934796,6679906776302710904>()) {
                     case 1236011368:
                        var10001 = com.yiyiaddon.e.d.b.c.OPEN_MAIN_CITY_HALL_MENU;
                        switch ((int)com.yiyiaddon.m.b.a<"sijo1p1vn0vrw","l03vjyJyNJ8rq0WCGyswXZV/9KHfROVRjueOYgz/cl4=",5767689188010199379,3314988546286780883,565896210979822501,-6142774565576488672>()) {
                           case 1781865388:
                              break label52;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = com.yiyiaddon.e.d.b.c.OPEN_CITY_MENU;
                  switch ((int)com.yiyiaddon.m.b.a<"sk6asnd3eefy5","YXxVbDr+5fTtQ0HPFxV5+eF3KALhP/eWu5G4ByFjCH0=",5036100691831863100,6777748601050851490,-6184306331808458152,-8544952218638185888>()) {
                     case -334208384:
                        break;
                     default:
                        throw null;
                  }
               }

               this.a(var10001, this.c.co);
               switch ((int)com.yiyiaddon.m.b.a<"s397io9t1aiapo","tLiUEy7C07GLlFpJl7HIfkbVd3uEFFdH/fqyK9zQa24=",-610355973675407465,-6060812112438326152,-8568522621241655166,-3588805233457816887>()) {
                  case -848614649:
                     break;
                  default:
                     throw null;
               }
            }
         }

         this.cD = 0;
         this.cH = 0;
         this.a.D(this.a + "");
         this.aY();
         return true;
      }
   }

   public void aY() {
      if (this.a == com.yiyiaddon.e.d.b.c.SCAN_WELCOME) {
         switch ((int)com.yiyiaddon.m.b.a<"s22auqt1k5dwuv","yxCyen5VtpPmg7hFKP1Z3rRRBEOSbBICMVxnSQig938=",-8776219891147559741,5002482795862493957,-522208477281991023,8878440963463302848>()) {
            case -711794618:
               this.a(com.yiyiaddon.e.d.b.c.WAIT_MAIN_CITY, this.c.co);
               switch ((int)com.yiyiaddon.m.b.a<"s27puernxuu08k","smPontIrN8GH731XDRepxY9OkXCRnuUP7r3VH0ldmi4=",-8317263618088669016,4171647221528119894,-2364714510859321668,-7622090896917334785>()) {
                  case -1558030735:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public boolean af() {
      if (!this.aw) {
         switch ((int)com.yiyiaddon.m.b.a<"s18696sy3s5sfc","aa56JG8f/Edei0BDO8EQEvp8U4xFfUz2xezxQ3DqdDc=",6985173911468848189,-689589408744756658,-5738184461051164153,-6940005460891857841>()) {
            case 1047399545:
               return false;
            default:
               throw null;
         }
      } else {
         this.aw = false;
         this.aR();
         return true;
      }
   }

   public void aZ() {
      this.aw = this.c.Y();
   }

   public void ba() {
      this.aw = false;
   }

   public boolean ag() {
      boolean var1 = this.ab();
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o57chgtq8i5f","T8JmHcGGi/siE0JRjy3NTcHcTk+yZfGb5Vpqe1TfrvU=",7335145038894353784,-2039578113367467988,-4651532166857811461,3636358081190773261>()) {
            case 688519332:
               this.b = this.a;
               if (!this.av) {
                  switch ((int)com.yiyiaddon.m.b.a<"sbfq6235cuu62","Va2/+QMoZhRXc5yCzjK3mlEwjHN3bT3H0aZDSiR8cok=",2255388775385303927,5148380878774262562,1283634562892344940,-1885194506556303395>()) {
                     case -16272850:
                        this.cN = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s1ee7xz7noco6l","KeDtZswwS7g7owv7zXdAqycU9eNVtnrHBxgMbEm/Xlw=",-9097371610519719218,8656644224676381247,-5110613178619394800,-5853805954480564759>()) {
                           case 793697950:
                              return var1;
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

      return var1;
   }

   public void bb() {
      this.av = true;
      this.cN = 0;
   }

   public void bc() {
      this.av = false;
      this.a = com.yiyiaddon.e.d.b.c.FAILED;
   }

   public int g(int var1) {
      int var2 = Math.max(1, var1);
      int var3 = this.c.cs * 20;
      int var4 = this.c.cr * 60 * 20;
      return Math.min(var4, Math.max(var3, var3 * var2));
   }

   public boolean ah() {
      if (!this.av) {
         switch ((int)com.yiyiaddon.m.b.a<"st2i7gz0l2n5a","lWnEBTtRBn30G82s0qgK5CA/XLg8dsTbL2Ky7Ktsbdk=",1485521650282799125,5542329364693716381,-2856526431178673861,6937167836212923390>()) {
            case -883873179:
               return false;
            default:
               throw null;
         }
      } else if (++this.cN < this.c.cr * 60 * 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v6wmwm4og61s","2zcPAu+3lmZfXx1PMp/fEy6dk+MooGeJHeQ/0r3U4qI=",-7889800952114690923,8613357850761086296,7664707403482211712,1203103638706978127>()) {
            case 1100536147:
               return false;
            default:
               throw null;
         }
      } else {
         this.av = false;
         this.a = com.yiyiaddon.e.d.b.c.FAILED;
         this.a
            .u(
               (String)com.yiyiaddon.m.b.a<"s307no9spywdqr","L7ivOsXNNc/y4W+wZLZNJ+k8LhVNeV0MZzooUseK8y81Dc2ROXbYFHivRucPsbx6KGYcRzL/47Pmjie+7Cg9roiEKbsvhjCcYLXncHhy",2776713465793843555,5218733799064414784,-1205190075033727652,676166557682890948>()
            );
         this.a.D(this.a + "");
         return true;
      }
   }

   public void a(BooleanSupplier var1) {
      if (!this.ai()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3k4wlx7gacnwe","IIl3Y0HZ9dSAb8q2WvKY/GzLCo4RM9m6yYXI6BJHFO0=",4705047191179271405,328736192624851837,6691259761017944005,-7707636015444735704>()) {
            case 197468154:
               return;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         if (com.yiyiaddon.e.d.c.k.a(this.o, this.c.A) != null) {
            label53:
            switch ((int)com.yiyiaddon.m.b.a<"s1nh33rrf0l506","VweW6APZPgNJtDdOX4yD7HBCz7eORrZXcFEH7XIupvo=",-660710353895315863,3344536039021667426,-7223251315536486976,-7150338210160454674>()) {
               case 360591608:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s1r6y2nya6mas3","lpYHXqZRPruRqlXYwGzM/orD8meYl2bFsHKIHYO61/E=",2843400381174338951,7808057900631060996,-5592231009541324884,6180318315639781339>()) {
                     case -1028197316:
                        break label53;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"st9tsv6dff4ri","UCzdJPqv22bBtmc4FM/lyKPFG4rhmcATSCJ4ieoptrY=",-8858227911523455649,-1784578489931217691,6081653822919443794,7445105786339469165>()) {
               case 2028847779:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2 = var10000;
         int var3 = Math.max(0, this.c.cn) * 60 * 20;
         if (!var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s2qrqq1rq9w4g3","Z/uoB3IFFcekjQvrmN/s2je5klihDVAU98uV+GyHbCI=",1244177426962212255,-1863000063534372348,3769200101861324490,543383147993902667>()) {
               case 2122395512:
                  this.cM = 0;
                  this.au = false;
                  return;
               default:
                  throw null;
            }
         } else {
            if (!this.au) {
               switch ((int)com.yiyiaddon.m.b.a<"s1pg0lr2sszsrf","kUgVsj1pFk3ocbBj3/A/k1/4MQoEv6VTLYLMPddWuPs=",-8402364222420821082,3133744137201517041,-978326087582505131,-6092443207576032527>()) {
                  case 1635052875:
                     if (!this.ab()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3jb4w5554gk8y","rbw3xPcedB13gzH+9u8Pz15GazCNKwbg0WQJFg2eUxw=",7176159417673732047,5207931752938341339,5572722671000160303,3623751476483839289>()) {
                           case 1467011875:
                              if (var1.getAsBoolean()) {
                                 if (this.cM < var3) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3dcs6bf8qzfh1","Ek3mlJWqGQmk+fqdD9yvrGjgerXy7W54WyA5B87xTxI=",1272952590735507682,-3879691919012776456,686313760090695897,-5492119588767448114>()) {
                                       case -1960095507:
                                          this.cM++;
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.au = true;
                                 this.cD = 0;
                                 this.cH = 0;
                                 this.cK = 0;
                                 this.cL = 0;
                                 this.cJ = 0;
                                 this.a(com.yiyiaddon.e.d.b.c.OPEN_AFK_MENU, this.c.co);
                                 this.a
                                    .u(
                                       com.yiyiaddon.e.d.c.b.y(
                                             (String)com.yiyiaddon.m.b.a<"s2m0ol1k6vnkjc","q8hy/mYrQsrWZ+54+E4sYUO0XHM8nOUHsn0s+yjH9V5DTA==",7093150611394122563,3004037534206017493,8410212041684031015,4760056984636729711>()
                                          )
                                          + ""
                                    );
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s20h8nxhhk80vm","hba+wpG/5jjnIBBMeMd5OMpVOfU6nMo7mrdoIxu04cs=",2771261708851890277,7140854546626448106,97763044595561868,-8596157265970889507>()) {
                                 case 1977601194:
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

   private boolean ai() {
      if (this.c.Z()) {
         switch ((int)com.yiyiaddon.m.b.a<"s373em2mzrcr4s","MRPL12EEcyVrEKWwAc+6kxy8V4KaGrUz9KTyBF7xfUk=",-1708728755725306235,-5451017466305693105,-1487794101110222929,-242843655405546559>()) {
            case 770228415:
               if (this.c.ap) {
                  switch ((int)com.yiyiaddon.m.b.a<"s15qxtr1nebuag","ScpS7CqvPjXs8sNQPRFA47Hw2BR7nSqfmDWYvSQ4LwQ=",-3316574495777889353,3179986788417663741,995544542526041257,5205426675047711840>()) {
                     case 574631689:
                        switch ((int)com.yiyiaddon.m.b.a<"s1dua6nlxlukn0","lG7ATNh0uqnD+Z0aapMONsuJfdj7AEFBGFpHeqpqwtE=",4510089394646818540,-3208825866786664723,6964294342455747802,-171174006129219972>()) {
                           case 1108360389:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1n46pp0j2u1wv","xOYF9/hAeVKSILwDSx6sJnDWtm6prOvF8JflA3zTEJQ=",8822531714892661052,2903886891344412254,775155756925826983,-5289760759304550443>()) {
         case -1922177615:
            return false;
         default:
            throw null;
      }
   }

   private void a(String var1, com.yiyiaddon.e.d.b.c var2, String var3) {
      if (!this.q(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dgsak7mh9hbl","kWdLsyOa10j9n6FiMF9tBnFbuZy/AykoDdtXY9Qko0I=",8903084676728800420,-7871843769020880369,3725030039684512132,-1015920887203966959>()) {
            case 1445802869:
               return;
            default:
               throw null;
         }
      } else {
         this.a(var2, this.c.co);
         String var10000;
         if (var3.contains(
            (String)com.yiyiaddon.m.b.a<"s10dcj1epu8dfl","YVQhLOfN8yZdoueqFb08yuD+Avu4ZIE7BYfSncWSv28=",4894902410807639103,-5358396538300456646,-4414580695524097221,-1247894310875284828>()
         )) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s3tzvkocb59sui","yWrNvu7Kcfclk91KikOxqjOfBzZeSBjI54ryDUGoVu4=",-56475850263153223,2501847591061981303,2798998301223706971,-497852186111597665>()) {
               case -2065372442:
                  var10000 = com.yiyiaddon.e.d.c.b.y(var1);
                  switch ((int)com.yiyiaddon.m.b.a<"smki4tz51r4ok","LcoGeJDJJ9+C/Y6C3VVOonBpWuVgcSzkCqacIFCaVdw=",2496032806195467321,370327537080728369,7754918360593192513,-2083616526271844633>()) {
                     case -583200539:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (var3.contains(
            (String)com.yiyiaddon.m.b.a<"s35vu87ucu5kks","o7BRmQv/WtFfwmDyuYmNkxWotxmS6QgHZkLdy50OWG8=",8678181031928681022,-4252001454774341216,-523511571244914142,5644038390229601818>()
         )) {
            label28:
            switch ((int)com.yiyiaddon.m.b.a<"sm2kwowbbjwu3","ONeiQMZjvrZ5EUDDz6jRJnwxxQYPs+sonk9VcIOjQQo=",3745232665452870095,-7041484337224262531,8971001297527238972,-400628876771557329>()) {
               case -1229243621:
                  var10000 = com.yiyiaddon.e.d.c.b.x(var1);
                  switch ((int)com.yiyiaddon.m.b.a<"s138fnaf3r4xpr","BTo2sCdnpQgtD7Xoj60Ny7+wi/Kx2cXiKhDl6GrdgsE=",2776127150355148874,8699538325983971182,8395270771654906223,-6817274709169871480>()) {
                     case 1178417365:
                        break label28;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = com.yiyiaddon.e.d.c.b.u(var1);
            switch ((int)com.yiyiaddon.m.b.a<"skgge426kzl8j","5E0Y5KHAv5jcfBP28VGPu3E4Nt8MCQou1WjN545KLPE=",8110242868383363825,-8243104464787587659,1273947785766818110,7969505262166134677>()) {
               case -2130494325:
                  break;
               default:
                  throw null;
            }
         }

         String var4 = var10000;
         this.a.u(var3 + var4);
      }
   }

   private com.yiyiaddon.e.d.b.c b() {
      if (this.b == com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_FIRST) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e9cgyxkmt075","4kQeasxVL3VdJgUG6deUxJKa+q6bnhaRpUIvuCI2R2c=",5049193559208654965,-7603668027299427170,-2920723800066125456,-3497477388405137773>()) {
            case 583608814:
               return com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_FIRST;
            default:
               throw null;
         }
      } else if (this.b == com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_SECOND) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qz09xjqzni72","Ab/h4/korivVLstBXHrAZn2XnkXaNaE0sX6shQ/uem4=",7155703186151730144,5555214511335737081,-9152166507609005534,1558340146369613164>()) {
            case -1315081967:
               return com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_SECOND;
            default:
               throw null;
         }
      } else if (this.b == com.yiyiaddon.e.d.b.c.CLICK_TARGET_SERVER) {
         switch ((int)com.yiyiaddon.m.b.a<"s313jcvztzx594","NtGuLuH6LU71BMYOUPChbFPyKSLWYxaI9X/JGViih8Q=",-3455996193434181885,97112678266501436,-3361679917371342933,5963536177750717415>()) {
            case 1258717044:
               return com.yiyiaddon.e.d.b.c.CLICK_TARGET_SERVER;
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.d.b.c.CLICK_WORLD_TRANSFER;
      }
   }

   private boolean aj() {
      if (!this.an()) {
         switch ((int)com.yiyiaddon.m.b.a<"s21rz4sgsvutwf","DMI2QVjJLJ2uPVdtMMOEcEon1jCIjDBPsFN7gEn/UuM=",9091023721773527356,4027640771916796004,8771174583851570734,3689994709423261625>()) {
            case -1206773073:
               return false;
            default:
               throw null;
         }
      } else if (this.ao()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kb10ug1eul9c","S6h13ERg/8oZGgCG9EKSryV6vANxyy84nCPTtvzReew=",957867968787170053,-4940139457997409179,5476082925730272177,-4131511498230943138>()) {
            case 1438963176:
               this.cH = 0;
               this.cD = 0;
               return true;
            default:
               throw null;
         }
      } else {
         if (this.o.screen == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3jf54gimq9ljx","7En4BxRiLztV8pnCY/3S3dITse4Q4lWEuKdBoW6eTlE=",1695602137486976607,2761957024683544970,-7544344374069815471,2660673308997612185>()) {
               case 1753370931:
                  if (this.o.gameMode != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2zuwbbvjtbtv7","G8RQbXz6umiP+O0x6/9x20Sy7Q1znUGRIcaabyp4niE=",-6335511735757938330,-1596097804610224951,-4573452465661338247,-5122632598372872403>()) {
                        case -1811094469:
                           if (this.o.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2861kzibsvvky","T0GFYJg6lBlO0qbHuCV3aGuJMpizgoG8s7NmLe4bOSI=",-5471085852503706971,8055795772651470416,-2705179120028024504,-3673933899259285035>()) {
                                 case -338673277:
                                    if (this.o.player != null) {
                                       if (this.cH > 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"skshpuk9l6adx","8xs7NxuZNmzfFxgmtmdg+OdfcZu6jQwe7X3RJQV9vKw=",2455116284047436093,-6101156330756548998,-2049225321533468868,5153634114779535266>()) {
                                             case -2046905182:
                                                this.cH--;
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       int var1 = this.A();
                                       if (var1 < 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2vbzonmv8x5ta","DCXUUDZnV5aQf3O0V5A/pWx4DnJ79n4JiAU8thM6a6s=",-6763329333093971556,8962971750935365238,-8755925156944898289,5019561929838405609>()) {
                                             case -1790710505:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.o.player.getInventory().getSelectedSlot() != var1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3qi2ku8ing7ni","3cdiHSZW+f/rKG44alotKbcGfxW0ozuPr92qi39oq3E=",-6879133107534184592,-1715411714262091530,-3903304281144060572,7547324728268402070>()) {
                                             case 773016618:
                                                this.o.player.getInventory().setSelectedSlot(var1);
                                                this.cG = var1;
                                                this.cH = 2;
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       label81: {
                                          if (this.a != com.yiyiaddon.e.d.b.c.OPEN_CITY_MENU) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sqi0dp3i2c4nv","XmBpaEKFbCL8CiNNv4zh549bnYoX8Vl6qylev9m7XQE=",7626725767165019585,4150544466108437169,2741658721367651902,7894497625230848878>()) {
                                                case 300803232:
                                                   if (this.a != com.yiyiaddon.e.d.b.c.OPEN_MAIN_CITY_HALL_MENU) {
                                                      label59:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1rfb4fa3z7icz","h5TuoxlTBbzvsDM8NCb4NPpq5Bfdg+g2diJ3DZKl3Mg=",-4357108475304275254,-4298938263994473569,4410670643767628658,2225651434798748848>()) {
                                                         case 74971465:
                                                            if (this.a != com.yiyiaddon.e.d.b.c.OPEN_AFK_MENU) {
                                                               break label81;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"sn8klgemefm0f","QNH+X/BCa6Pp0zqRp2np8GrULI21VpniDhOJImThqJs=",3177510667582943735,-3230604007337512786,-3210227401826414479,3068310834003285457>()) {
                                                               case -1287354014:
                                                                  break label59;
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

                                          float var2 = this.o.player.getYRot();
                                          float var3 = -75.0F;
                                          this.o.player.setXRot(var3);
                                          com.yiyiaddon.d.c.b.a(var2, var3, this.o.player.onGround(), this.o.player.horizontalCollision);
                                          switch ((int)com.yiyiaddon.m.b.a<"smgdve5bersdu","RGvPhQTK7H7tZjjKFoMnALI/qxhmFyFpEHPDy6cu3A0=",129681084060378835,5595947694887876425,612006868411534259,-1813112430169608915>()) {
                                             case -1638256288:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.o.gameMode.useItem(this.o.player, InteractionHand.MAIN_HAND);
                                       this.cG = -1;
                                       this.cH = 10;
                                       return false;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1fjyvb7mh040e","xyR8wKSfohX004eYATw+hMzvw6hx4JkthEHAoBIFLpM=",4016993630125110174,3055811020790383272,7367196688380116670,-6923056225178416445>()) {
                                       case -639469495:
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
   }

   private void bd() {
      if (!this.an()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ln6xkte9hfic","PV1DGvGRMAfgepVu6ocQplDzXNdbCttXci9fVfzlLlo=",8831858507898964159,5773578698008781697,-4173767694634471993,-8028001848274493542>()) {
            case -596225949:
               this.be();
               return;
            default:
               throw null;
         }
      } else if (this.cJ == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s35bwz6847efij","UIIZ3QiCYYHnJ72aQ5QGgGDyW/YNh/DxdkaQpT5Zvpc=",-1557247324418504836,-8836226793136196987,4545869953969073556,2376346004339932447>()) {
            case -1919695767:
               if (--this.cI > 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1233i10904vj4","7mV0dKwlK/1Filba9Ykw0rSPnNo1yp0n1/SCtkKyZME=",-4172377943101465920,-6255000874687192750,-4083886061782555787,-1956301702438066591>()) {
                     case -1266609821:
                        return;
                     default:
                        throw null;
                  }
               }

               f.aT();
               this.cJ = 2;
               return;
            default:
               throw null;
         }
      } else if (this.cJ == 2) {
         switch ((int)com.yiyiaddon.m.b.a<"sb59iv5jxymtc","PmMP37ijK9OMuOjbIM4oTBAII2SmC7KhaZ2tSm9zgRM=",8999220883850768030,-2642139095684517837,-7856807811483171268,-3391805267528836624>()) {
            case -202931509:
               f.aU();
               this.cJ = 3;
               return;
            default:
               throw null;
         }
      } else if (this.cJ == 3) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vk1e4eonnu2z","bHmSJSQeLh0bCmagkpqOveYI1UWcQKQCIb1glfQNFQo=",-5598920417034449176,6705975983071282522,7080531494808521419,-8237355694157899202>()) {
            case 238196990:
               f.aV();
               this.cJ = 0;
               this.cK++;
               this.cL = this.cB + 40;
               return;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         if (this.A() >= 0) {
            label92:
            switch ((int)com.yiyiaddon.m.b.a<"scvxhm6sq53hr","TI/9H1T6Uq+kI1Bua7ggG6UNsh4WMRxp8/rFmY8MtAY=",5715365082737845632,94087638677161676,3843262695378718752,3108902789382465406>()) {
               case 1075721721:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s3uyqb9v185ifk","Xy9bawnUn5TDkyBk5nIUk+IlZbkveklL19FW+CdT81w=",-4073952160221813343,-6095740265466461030,2403626674040136613,5897557425820742714>()) {
                     case 762196932:
                        break label92;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"sblq4a5umaoiu","EXwAxaTVEhFOa4aM5tDyi+PiMaDk0aQtkQEc6NB2HIw=",3425041220086601927,-5658057297742838775,3823513173878432359,-923218756344990666>()) {
               case 119039334:
                  break;
               default:
                  throw null;
            }
         }

         boolean var1 = var10000;
         if (this.c.aq) {
            switch ((int)com.yiyiaddon.m.b.a<"s28dluomovtrht","Rglioy+gUqTSlyvw205a3Y01w3rIZMc+1VY96g0mOl0=",-1295670746570756947,-1875131353019551615,-7723673697338995513,-7126789229411189304>()) {
               case -689059071:
                  if (!this.ao()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1yrddy6a5d0nb","tXruA1BzPLAxWr/D22BV7pbgG7jEBxZiYrylo6REFIw=",-6937567244294359401,-395321327407169459,-1788883180835381250,8277001649328983893>()) {
                        case 768998902:
                           if (this.cK < 2) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1h0tqod0zzeyk","8027gk1FSh7tqgZnNg2bpWHIUNq5r1jlYg+uQch++GM=",1502022480163352587,-6978839271532507853,2215232871580148699,1567949742376065195>()) {
                                 case -252573427:
                                    if (var1) {
                                       label82:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1lo0oescgwzc8","homLHs6umcK1pTNXowFBedkqjQPSrtK793E8qTQWS/c=",1279580106580047484,-6040644340048238594,1848588788981156700,5420710538468014573>()) {
                                          case -383672764:
                                             if (this.cB < this.c.cq) {
                                                return;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2lwvhdr3z0eqb","r3WMpF0JFkIdUv+R5NAyXJqhIJH9qzlNgKnn/TVYnAg=",3870195180964101830,4953561933423935336,643456282514405912,-1433538187581865298>()) {
                                                case 1088626310:
                                                   break label82;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (this.cK > 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s36mw5po8bbt8","RLsYVlJNkTC6JBvVfi9sDgyRyVlf3X0HIyNAaxOMByM=",-9004141060106828556,-3145507802398435661,-5526562150870239827,995329895115810006>()) {
                                          case -1746373324:
                                             if (this.cB < this.cL) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2skt8fizmpkjf","LCINz0CmsgkrmSPenPAJrx7xZTWebLhrVGuoNQ4ZuUA=",-1954183292750842037,-4671048017380264278,-7013139776563825998,-3882556955776843829>()) {
                                                   case 142710687:
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

                                    f.aS();
                                    this.cJ = 1;
                                    this.cI = 2;
                                    g.a var2 = this.a;
                                    String var10001;
                                    if (var1) {
                                       label71:
                                       switch ((int)com.yiyiaddon.m.b.a<"szwwevpvzd4t8","oNqXvm6h5zmpLOQUfFIH5m33M+NH9pzjdV1auZqM+Cc=",1550522503670410361,8312610436268485492,-3132703952399887244,-8516543303652616219>()) {
                                          case 587013212:
                                             var10001 = this.cK + 1 + "";
                                             switch ((int)com.yiyiaddon.m.b.a<"s1nidd4k4fis1l","F+ZuKZR7gdcnuhOMck66IbsFf7X41ZGmlhd6SIp8xmE=",3987622399342067140,-2328875763788761773,7960178712584763852,-5834480158283137977>()) {
                                                case -1257531065:
                                                   break label71;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10001 = this.cK + 1 + "";
                                       switch ((int)com.yiyiaddon.m.b.a<"s3labxshphqmxd","bVlEldEuoOks+PSfV5bq3DhxGF5OGz2avWakTbLOt1Q=",6578717648529382657,2222424762480680964,-3909848300849583519,-6979239743562563023>()) {
                                          case 1904636966:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    var2.u(var10001);
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
      }
   }

   public void be() {
      f.aW();
      f.aX();
      if (this.cJ != 0) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"suam9hy2725ja","SCq1BVzJ5VKLq4BFgRVDD9UaCtjZ8jH26GPbzWxoJ0s=",-4300550546515085027,306709874250624780,5475320473570305979,-5333705885853339663>()) {
            case -826583419:
               f.aW();
               f.aV();
               switch ((int)com.yiyiaddon.m.b.a<"s1la5hvo39cuo5","kZTZZQf0qGEk8oz15l+w2au/8YOepdYtZKbzaDd5+aM=",-7731432964731165283,1096212599878353432,4553126317122205700,-6054723917831720284>()) {
                  case 1574881030:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.cJ = 0;
      this.cI = 0;
   }

   private int A() {
      if (this.o.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s212d9xpw0z3ve","26Mz4AdtkMXKWB1AEn/A9V6KOeIJ42MwO0N3oeD6hqE=",2998228636259279542,-6461240460339145001,-4086344373947724097,7257932656262361724>()) {
            case 1425239316:
               return -1;
            default:
               throw null;
         }
      } else {
         int var1 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sz4495v65vdhv","7ZKYoFF8GteZWXxHUY4V2uQV+wnFQWPTCLn4T8VVluI=",6860483198339733648,-1985814399283221221,8969716706684316792,2718498185516321470>()) {
            case -2066640640:
               while (var1 < 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"s177s94ga26msf","OGvopGs+rQQr5VgL+LWFi0FRRnJ+9qDCL1opJ2r6Ddw=",926944988610398772,3752233153291152399,-6305602709410373246,-4566059017069361921>()) {
                     case -902387751:
                        ItemStack var2 = this.o.player.getInventory().getItem(var1);
                        if (var2.is(this.c.e())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1znwvupyplks5","ZRRTDNdL6VT0M49Ca+LicCiNW2OmZi9ceuXVaQ96CUY=",4694639827130799944,157120422858409437,-9018126682773658326,-257930257732705721>()) {
                              case 1641468187:
                                 return var1;
                              default:
                                 throw null;
                           }
                        }

                        Iterator var3 = this.c.y.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"sy49qnhj2az3p","ujwBToCiozID2LS2JkMp1AODCTrH1zD34k+5zCJ/bO4=",-5982426482656331478,-6399334433130647014,5267816939056597320,551230798135336579>()) {
                           case -1046229905:
                              while (var3.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s193g1hq3rsp0f","9siYRiyawQmTeaAFTXbxb0DFzoRfUiPBu856q6VlLhs=",6390995211239753046,-4094423044377053179,535148076227699891,3728522915780699636>()) {
                                    case -254840573:
                                       String var4 = (String)var3.next();
                                       if (com.yiyiaddon.e.d.c.k.a(var2, var4)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2tfze9a42ovqu","JWC9V666awSVzqfeFKHaeNfufAqgpnaww0HTBQaR3/Y=",4191454880692361686,4096680414124798940,7658183968437726748,5618852468174969382>()) {
                                             case -703631227:
                                                return var1;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s1t47fn6hk2n0a","9Dmtg1GIBu/QTTfjZ8BqLhbwtdCB/o3iWops2ltiy5s=",4399611654047822750,-4081946160841054480,-239255035859359144,-6118904673029003611>()) {
                                          case 873908221:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var1++;
                              switch ((int)com.yiyiaddon.m.b.a<"s23hgcw11rpc16","FgAA779o9GlFqCNzhkEHMkJ1NqGCVwclt98YN00CxqI=",-4046199292281903575,69847151261463935,-7598921451561833199,9173790295128393235>()) {
                                 case 1530416603:
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

               return -1;
            default:
               throw null;
         }
      }
   }

   private boolean q(String var1) {
      return this.a(List.of(var1));
   }

   private boolean a(List<String> var1) {
      if (!this.an()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3a8zrl3tr0ksj","IElly49IynjexUc/juBAWnkfcKf+P5Ra8EO7LCTur1I=",-6066501658739380003,-1029574481019393761,-8392814214344999385,-8620566447223621489>()) {
            case -657641976:
               return false;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var2 = this.a();
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1lidehitnay9q","cTezhVGvqFAbc5wQFu8J0MA5fIpI0RqOV8JPk8Mzl3g=",-5921092953761610972,-9120493816617775641,8710123359870521696,3213776867798029897>()) {
               case 266297793:
                  return false;
               default:
                  throw null;
            }
         } else {
            int var3 = this.a(var2);
            if (var3 == this.cD) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ee2qc9869jhe","r5pcf1DQOCiHJj+RTaOt7+E7BrnHLeSWqJw3g0WC1fg=",8956341499825144116,-3000572158249568652,-1745925015855711532,-2412285767166675906>()) {
                  case 740701559:
                     return false;
                  default:
                     throw null;
               }
            } else {
               int var4 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s2gs8rxj6upe5i","dtP4HPhsztSat0WYqALBrfOH3qawvd5cDh/quQcB6gw=",-7754507343750533539,2898786782304762029,3322317673873601883,-5907334181870348196>()) {
                  case 1294720302:
                     while (var4 < var2.slots.size()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s22gak1qoaenvp","zySIpej+cbXpbQV4TTNrawECa4KzMYARr7F5Co5CoeI=",-6773811427201736560,3440231973153633865,-1711501994339492442,-1661058900160903102>()) {
                           case 1882885599:
                              Slot var5 = var2.getSlot(var4);
                              if (var5.container != this.o.player.getInventory()) {
                                 label42:
                                 switch ((int)com.yiyiaddon.m.b.a<"s232imwxr5v4cl","rAsbM1nu75/mVyLkWhtb3FeNilOJSYtFVcEZrq4VR8g=",-2397015194714643171,-3053596743872753824,2658453583143741066,-3233751883239015845>()) {
                                    case -1156374122:
                                       if (com.yiyiaddon.e.d.c.k.a(var5.getItem(), var1)) {
                                          this.o.gameMode.handleContainerInput(var2.containerId, var4, 0, ContainerInput.PICKUP, this.o.player);
                                          this.cD = var3;
                                          return true;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s287bhbkvsjdun","lV8rnK1TlSeY3qPBPZJmVSaDK2671nXlyX6Eh3x9HjU=",3433802518804766271,-7304640382250964695,7038899082716038353,3662321743301364196>()) {
                                          case -234215839:
                                             switch ((int)com.yiyiaddon.m.b.a<"s354lclrb35j9u","WcXNMrDwM0qn2fXZs/acYiMQDdzU7nD8pvxaOCFIiIA=",7509789398762559659,-8307414174494487969,6624612233801365049,-7920281692872988892>()) {
                                                case 1772519783:
                                                   break label42;
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

                              var4++;
                              switch ((int)com.yiyiaddon.m.b.a<"s3bzt4jvmmocu1","I1+XXwG08Wyo64xLvTkBLhKoiOeyyAu++PiVwTT8uQ8=",-5522970246246306649,-164613563029616046,7179517557047788199,5460342638658788931>()) {
                                 case -2062516057:
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
      }
   }

   private boolean ak() {
      if (this.c.a != com.yiyiaddon.e.d.b.d.BOOK_DIRECT) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hhkzv3vzpzyw","vsi3aErsOetuPoTsnVHCmTc9Uudd2c23w0qOx/U/gU8=",3614467296686262303,-1590439181976082157,-2128097140858218995,-6505238474047750549>()) {
            case 380069667:
               return false;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var1 = this.a();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1eyxq9opo7da0","MB+U/3jwoZ+8HCWHcblSY98MLRT4XjzleXGZj0LWZSI=",8081722083254047616,-4142470746928964238,5325415257225698182,4361560480674157787>()) {
               case 843771474:
                  return false;
               default:
                  throw null;
            }
         } else {
            int var2 = this.a(var1);
            if (var2 == this.cD) {
               switch ((int)com.yiyiaddon.m.b.a<"s1niy0uw26gy4s","4c9joaOPPPkWj42AupWslXsVk/x7+A0HVVK+6LIjdwE=",467785139105618317,-1245639397757835762,-123109761818684122,7807691161574182149>()) {
                  case 399951588:
                     return false;
                  default:
                     throw null;
               }
            } else {
               int[] var3 = k;
               int var4 = var3.length;
               int var5 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s1h2cxymf780sd","t+TC1RFofl/mvCCMVqLJXVs1+UR9FAiucloM8R1GZHA=",4190936699351617741,-9054128017741271185,-2215945348257744093,-5015000856129942179>()) {
                  case 979535031:
                     while (var5 < var4) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3egiubwozhz1n","3M4gP7rOb89U/6qstQSvqzWWrPgd6zCsHRjEN4w17C4=",-1328625339817083242,-2732648190576574927,-1711214826651258806,-7797114134443031358>()) {
                           case -127854362:
                              int var6 = var3[var5];
                              if (var6 >= var1.slots.size()) {
                                 label64:
                                 switch ((int)com.yiyiaddon.m.b.a<"sjz4nvnj06u0a","HagOVFQYNVQ3OKGrRGxi7vM5IrxC2aMzzogSXX7bo/A=",-3555011346240093966,-7935242093924545855,-2596396896975618281,1901243161355243688>()) {
                                    case -1967457622:
                                       switch ((int)com.yiyiaddon.m.b.a<"s27j2s9voeqsxb","VD0or1zUJuRrb+DNVAiZZYi12y9+N+4HYoLZXpETzL4=",-8773110746169706516,707417918130178885,-6959104249369315403,5537436187355623590>()) {
                                          case -1264984538:
                                             break label64;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 Slot var7 = var1.getSlot(var6);
                                 ItemStack var8 = var7.getItem();
                                 if (var7.container != this.o.player.getInventory()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s30537bm5jfkz6","CTls3WkoABLvpRNkXjF+6Zs8cmkPk3hEm1Qx1/XopVM=",-8401073672904609382,3217396302428878727,-8691100910631640606,-3870859666281455474>()) {
                                       case -479682198:
                                          if (!var8.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1xda79106kmdh","3LJolkgb0VkNGtTNe8XQZD76S7FCe8q0el/gE3nyVK0=",420643287987479243,-5315354969432776792,4207928118303025750,-943467317453360583>()) {
                                                case 972206780:
                                                   if (var8.is(Items.KNOWLEDGE_BOOK)) {
                                                      label57:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3ppdvtpr3w4y2","BpD7EPu+SHC809hk87tUn8JixyDvHdEh9GCRvsWFlXY=",-6718842540089339343,-6566829323337096987,-8119835260924081080,-6102171048786877838>()) {
                                                         case -1028281749:
                                                            if (com.yiyiaddon.e.d.c.k.B(var8.getHoverName().getString()).isEmpty()) {
                                                               this.o
                                                                  .gameMode
                                                                  .handleContainerInput(var1.containerId, var6, 0, ContainerInput.PICKUP, this.o.player);
                                                               this.cD = var2;
                                                               return true;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"sy8ua1n52vimx","IEBg9O/qP860KvG+4f76bCr6uPONyPhVVjyu2X5Ggnc=",3642317416581466512,8431073250391442730,4379956035763307598,1827333772469660525>()) {
                                                               case -13587575:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s373sos4c0zo1b","FMeKVgu3QxYYRnxZq+4qqw5d5NehmHZQ+mg2vZFlGTQ=",8304718007847871155,-1902957398809306436,5823333302584679750,-6803718038758859011>()) {
                                                                     case 1224976807:
                                                                        break label57;
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
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }
                              }

                              var5++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2a1u6nwth3c1d","2CXkOfat52FywwKUE8XhuY0PbR3+4OMWPGh8osYNVOY=",7438469744966116241,-433866536984415905,-2351342819196327554,-1731443706316352452>()) {
                                 case -914569079:
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
      }
   }

   private int a(AbstractContainerMenu var1) {
      int var2 = 31 * var1.containerId;
      Iterator var3 = var1.slots.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3bu88ixaw1uao","u7/K33TwcVpaeb5eL1KZGk0J57wA1p7QTVY80EKXISg=",6929101149121318674,5419096350606597244,4945523770170246303,5169168552977869642>()) {
         case -115393263:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2k1xw2nne24p6","yPab8gOBbjxvTfFkugvNufJeaObfwDcoR8ad7HQBdTM=",5202333596612723757,-6486141494363842311,1603493332243851534,-2583246626215475007>()) {
                  case -752330655:
                     Slot var4 = (Slot)var3.next();
                     if (var4.container == this.o.player.getInventory()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1bxtzsh9dpg0e","uqBJ3Mvu0WjfEeVyBTHfbxVIdnujqF+Vk90u/IxQvIE=",-4402947009691203095,4334565075009623010,7706000800584280247,-7542287521656131002>()) {
                           case 5000072:
                              switch ((int)com.yiyiaddon.m.b.a<"s1onk3vnwb9dh2","3enxaNq2omlAf+2HaNpAgI16iRyhp4wdUhtccny2THY=",8243870872917305969,4540088757036720204,-4294927714665136755,-6367365151414890885>()) {
                                 case -714723653:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var2 = 31 * var2 + com.yiyiaddon.e.d.c.k.B(var4.getItem().getHoverName().getString()).hashCode();
                        var2 = 31 * var2 + var4.getItem().getCount();
                        switch ((int)com.yiyiaddon.m.b.a<"sb9879va3s2c7","eN1cZ+BcQkdwWw8jVErLREXAjw01i2y9P3tiMMZnKJ4=",-2552474446444528390,-7907762643933083654,359420712431773900,9098406335908332546>()) {
                           case -370318077:
                              continue;
                           default:
                              throw null;
                        }
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

   private void bf() {
      if (this.al()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pxq1wb2oj0r7","+hG0845wg92O7PMwGDu+oJBLZjBSyqBVzceWeEAbA8o=",2756149350580133220,-198414235374466377,-2702326208903895916,5918010132977764431>()) {
            case -757034325:
               this.a(com.yiyiaddon.e.d.b.c.OPEN_CITY_MENU, this.c.co);
               this.a
                  .u(
                     com.yiyiaddon.e.d.c.b.y(
                           (String)com.yiyiaddon.m.b.a<"s3a18wlgij2jjh","b4KOjLm5Dg9jyszKd4eIqtxAbyklk8rictepIGlTAmyZmLJ8",4600002194203866777,4413327935356660808,772333627139395734,-2303650783918560307>()
                        )
                        + ""
                  );
               return;
            default:
               throw null;
         }
      } else if (this.c.a == com.yiyiaddon.e.d.b.d.BOOK_DIRECT) {
         switch ((int)com.yiyiaddon.m.b.a<"sumkbtpuk84az","v6zizGKPQRA+U2PmnCZ24JXNXz9AWaRmZT4YZ6TfgWI=",-678663772521098481,-7681855956828063898,-1592169359951658982,3274946398707972129>()) {
            case 1671955698:
               if (this.ak()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s26rvkbg86q40u","Poaq26euoXBk5mb+ScWS1cU9iVxBW2qpFTIDBUAfjjI=",-7402011897645775864,1241121653328547450,1536582106951448896,5952889566317164532>()) {
                     case -1416876209:
                        this.bh();
                        this.a(com.yiyiaddon.e.d.b.c.WAIT_MAIN_CITY, this.c.co);
                        this.a(
                           4,
                           com.yiyiaddon.e.d.c.b.x(
                                 (String)com.yiyiaddon.m.b.a<"s21fatqgsi0h6n","FQiZU9AnZoPp4y/7eh8FW1M/+ac/wSNWIfep2xnFBn94wGXscU57tg==",6539250566439876925,6377891973316735392,-6473562186908625307,-4359508149099787341>()
                              )
                              + com.yiyiaddon.e.d.c.b.y(
                                 (String)com.yiyiaddon.m.b.a<"s3a18wlgij2jjh","b4KOjLm5Dg9jyszKd4eIqtxAbyklk8rictepIGlTAmyZmLJ8",4600002194203866777,4413327935356660808,772333627139395734,-2303650783918560307>()
                              )
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s1cvhftp0wac7d","Lpn+By3ZE25mcMBWSb4Omh0DuDuxRvszmMGbAGNFc2s=",6723831269456599948,2339850818481056036,-57122570694457047,4944651166721784499>()) {
                           case -1217586347:
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
         if (this.o.screen == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1ig881kn3i3dn","9GzYsF4MCgVl9DbwyLnDHNdJxd87PPezV2rber948Tw=",-5222344869904053894,7191272166971642404,7768258819859377993,6304385891209105835>()) {
               case -845783537:
                  if (this.o.getConnection() != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s12koqu4x4eb4b","DMRf19hb7VcXEWYt4bgYg7hZdHdMBITnpfUSNGxTc9c=",7410647556640640876,-5941590670722713535,605525693477512729,4228308377444913592>()) {
                        case 1912338881:
                           if (this.o.player != null) {
                              if (this.cF > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1gzr7meyn0iip","/zUx9praExQqDGQlKW60lcwgapf3rQOPwUy/elqRPMM=",7413644086204984417,-427128402320629914,2229195571548429153,6835849171315056427>()) {
                                    case 1023789053:
                                       this.cF--;
                                       if (this.cF == 1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s13ahqesva1wn9","EDveFMXSOdLjeE69ZYOxnSVOm2/azdC15Mene7LB05I=",6709084195223750410,-4486763296449180553,-4786398792835215739,6958900845748039987>()) {
                                             case 1595298565:
                                                this.bg();
                                                switch ((int)com.yiyiaddon.m.b.a<"s17tq1egu5pn2f","6kNDueDMh+7FbPl/9urHZUfFh1tADv9QCuLHjNAvYw0=",-4453181273306073992,-8986524382793771294,3566532718494848268,-7083573359190878896>()) {
                                                   case -1495284546:
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

                              byte var1 = 35;
                              int var2 = this.cE % var1;
                              int var3 = var2 / 7;
                              int var4 = var2 % 7;
                              int var10000;
                              if (var3 % 2 == 0) {
                                 label60:
                                 switch ((int)com.yiyiaddon.m.b.a<"s200qxnkf9cu1x","JcOUPioDqghisXO0gDCBMMVY7OGt86dQLGjyH8ELN8k=",665165181920442099,5552244634605444003,-5186448411429037028,460527405922759122>()) {
                                    case -1664112024:
                                       var10000 = var4;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1hvewmf2o741y","mrNcHOI/ElaK7bPZI2Q9Gtmeha1AJIMbg/VGqxGfO9A=",-5762380503526183924,2469789962939528871,-3113787055657370938,4135231345497002278>()) {
                                          case 400853287:
                                             break label60;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var10000 = 6 - var4;
                                 switch ((int)com.yiyiaddon.m.b.a<"s14awkv9zkhssw","fyaNcKj8FVFP+pnmQez6CVLhv8jVzsHzH5TPpDh1MFo=",-3895660659298974282,-8209858618730289012,2310851739335805409,2898997517728908256>()) {
                                    case -447947717:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              int var5 = var10000;
                              float var6 = this.Q + (float)(-35.0 + 70.0 * var5 / 6.0);
                              float var7 = Math.max(-90.0F, Math.min(90.0F, this.R + (float)(-22.5 + 45.0 * var3 / 4.0)));
                              this.o.player.setYRot(var6);
                              this.o.player.setYHeadRot(var6);
                              this.o.player.setXRot(var7);
                              com.yiyiaddon.d.c.b.a(var6, var7, this.o.player.onGround(), this.o.player.horizontalCollision);
                              this.cE++;
                              this.cF = 2;
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s29j5vx1ihkf29","EliPUAq8CTXawkA+jg6Mb6/pUNVlqyWiFDLTabm1S0w=",-6471744005830411229,105941643492226361,-7653900666173513657,-5802757985704509319>()) {
                              case -222056298:
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

   private void bg() {
      if (this.o.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2w5tu2byqrl77","UE0igLGJj7aThY6YI0NYd4Lfc0acm1vTH2H24/Ub0kE=",-5544561681332400359,-273725536857274849,-8011970727505811255,5189725462750442596>()) {
            case -2000018823:
               if (this.o.gameMode != null) {
                  HitResult var3 = this.o.hitResult;
                  if (var3 instanceof EntityHitResult) {
                     label35:
                     switch ((int)com.yiyiaddon.m.b.a<"s37kfei5k8wmne","kEwJzdgEXW5y3LOT7Y1l/YBiIPTzM6noOJ+bxW04avM=",5055294412135208958,6550684238600570454,-3847114253973432082,-1824444317741033045>()) {
                        case -1848731724:
                           EntityHitResult var1 = (EntityHitResult)var3;
                           Entity var4 = var1.getEntity();
                           if (var4 != null) {
                              label32:
                              switch ((int)com.yiyiaddon.m.b.a<"s35hyfjgkd00v5","7w7Sj9QHnRYPnK+kIOg16RoiU2fJVxgfYtbhGu3c6jo=",-8925616979559288325,4786912912518202465,2142564845915417686,4686891450538492089>()) {
                                 case -1899372040:
                                    this.o.gameMode.attack(this.o.player, var4);
                                    switch ((int)com.yiyiaddon.m.b.a<"s2aj3022h9nywj","h1p6a61H93gpfAEDvUtigkC4A+Brz6Lwltbz+IbGMJE=",7067304250086444977,-6733297076513877825,933277847898713508,1098394473188780478>()) {
                                       case -133454316:
                                          break label32;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sjd0fz94slh3r","4GEmSeNnym2fTBCS1gC0g4rPsyTYqn3kuvs7t0CVTrQ=",-8317203193953583033,-4277962223299654475,1698018136501998517,6415837396267034777>()) {
                              case -656547739:
                                 break label35;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var3 = this.o.hitResult;
                     if (var3 instanceof BlockHitResult) {
                        label38:
                        switch ((int)com.yiyiaddon.m.b.a<"s1ho8bnzyv2ygv","PgwbvaONLAx6Wiq5/XAKTLOIo6cuB6BwQGVtsus68ug=",-947555447030483274,-7219933880421557667,6245799556360531193,1646473579804078515>()) {
                           case -1684415022:
                              BlockHitResult var2 = (BlockHitResult)var3;
                              this.o.gameMode.startDestroyBlock(var2.getBlockPos(), var2.getDirection());
                              switch ((int)com.yiyiaddon.m.b.a<"s1v17umabo21da","AwcSZ0hseMRr7Nz1Rq3E3piLUAG6y65fo0nrahwwicY=",-781651285979341467,3001280899951541910,152039884149920457,-4316458990991798752>()) {
                                 case -1926390683:
                                    break label38;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                  }

                  this.o.player.swing(InteractionHand.MAIN_HAND);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1zivk8dhrm3y1","nEzcKbGBdMAzr07+s7sXWM/BB5GpmMKRuaWf7BhDabk=",2673542813590510232,251687117761633244,7360268861298148427,-17295827351166277>()) {
                     case 488552751:
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

   private boolean al() {
      if (com.yiyiaddon.e.d.c.k.a(this.o, this.c.z) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1600aijzjn4zu","Z880U4C0IOKBfs7E8i2LRdsZM4rDRS00DCTHVTXA5Ok=",-4083849352076718328,4842839796711330304,-8572568765763523625,-2825673830704440895>()) {
            case 2116657658:
               switch ((int)com.yiyiaddon.m.b.a<"s34ze8dj86jxh5","/O8OWnHAEXZr0IGQZM6OlM+78bpEbx24H7C0Ief59aE=",3081125172011763830,7986897452978441310,-6005943662415769698,2635345508715369252>()) {
                  case -1770024376:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1a4ca51gz737m","Id7pKMyDrrJk42eWwWaoahxxwOvrsLTTHSponTKPpwE=",1280286874723337307,752665224821816236,-7255611505805718837,-8657496526938910131>()) {
            case -1332661326:
               return false;
            default:
               throw null;
         }
      }
   }

   private String ap() {
      String var1 = this.c.cw.trim();
      if (!var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3cz7v0cobp8e1","cIs7Fj6YNReRJAUnv1L6Gsv2qnDK9mBGxiuIl3mB0VQ=",-2626415447931821250,5690065377818281471,-3923362741948446812,3818803420632334093>()) {
            case 124138182:
               if (com.yiyiaddon.e.d.c.k.a(this.o, List.of(var1)) != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sukl47pwkss0o","FDSzKkvr37LiQv0Wg9njJEt3+gmjnNCOq4u5onzexCQ=",4181734801816113464,6892978358641566583,7810446531598282606,-5472837701586308238>()) {
                     case 2096033724:
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

      return com.yiyiaddon.e.d.c.k.a(this.o, this.c.C);
   }

   private boolean am() {
      label37: {
         if (this.c.Z()) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s2km8p0dlwlcxa","ECnzUgVFJvRXSNSTNwksZ58OdnncxbTkYOYjQ5l2CXs=",-527584750517482546,-1208942209427912376,-5081888665518239000,4945999461279347193>()) {
               case -1667956368:
                  if (this.c.ao) {
                     break label37;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s29e0618a7goy6","mMZKLmtPtWMIKFDYcxhs6fb86dFIFFXj3TuGq3K0EHg=",-1635575689643160580,-5403599957124626639,78402777882920376,2942230252986193003>()) {
                     case 1973228040:
                        if (this.c.ap) {
                           switch ((int)com.yiyiaddon.m.b.a<"s27dodi05bu2um","OhuPVp5OPUEBWKswf8FVM5nTO2lJRRNhyp0aYJq1DTc=",3479529567778588943,-9063873977005827596,-3958878003224821563,8687544118022784050>()) {
                              case -23763728:
                                 break label37;
                              default:
                                 throw null;
                           }
                        }
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s1i643y9a75x8k","NABp4E+jE8zZyQ5AyUUbfgcnnb3OH5UIy5vc4FRWiw8=",-4049240040496313243,-5538812459558508234,-44076811501798589,1728382934039145518>()) {
            case -1430900578:
               return false;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s23tbimrx37kr","F+9uRWkVsqpcdmtSxaA1RBtEZmM7c3TJ7LLAMF/MNUw=",-4827755720079819930,8659960459901082091,-5990774054789985511,4426244779467359485>()) {
         case 862589780:
            return true;
         default:
            throw null;
      }
   }

   private boolean an() {
      label93: {
         if (this.c.Z()) {
            label62:
            switch ((int)com.yiyiaddon.m.b.a<"saf6zv8ym2w9l","2G706MWtj6f0G9jjiVfJ0nf5vlSaiw85HIgAgGVWG/Q=",1871355011888165165,4495488197426946818,8126589949809579936,-2887460999385746507>()) {
               case 2042176186:
                  if (this.c.ao) {
                     break label93;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2xcv1t8buifc3","OdOqa5D4hDelAKXwandzdKOFh8apcG4CCmT43ut6SL0=",-2395248731342867333,-8896807102089276008,-1736443619849468780,-7150114897176229967>()) {
                     case 1113835018:
                        if (this.a == com.yiyiaddon.e.d.b.c.OPEN_AFK_MENU) {
                           break label93;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2v55l5wumqdq","Tmy8NNJ2G6UHOLgKNAFP8aZawCLnV+UmO3INuiGCQ0A=",-7498848969639057201,3541745709807927210,9145649260265258180,6794146530052635564>()) {
                           case -438251620:
                              if (this.a == com.yiyiaddon.e.d.b.c.CLICK_RETURN_MAIN_CITY_HALL) {
                                 break label93;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1zmpfhmd9vw29","5CGRVODBgiIABhoXnq+fUBfKJZCDGiJmGDNarwzOHRY=",8705443342151086651,-1142969111216623069,8691637115799530562,-6279743703792757959>()) {
                                 case -990385655:
                                    if (this.a == com.yiyiaddon.e.d.b.c.WAIT_MAIN_CITY_HALL) {
                                       break label93;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1ueg8w00bielt","ZfWZ9Q7uCiM+pAjpNUbiEOASoCAPiUT/0l0P7P2Rd1I=",-1337174550318728519,3130514164443246338,5623857189481297858,-265924813592313680>()) {
                                       case 643078485:
                                          if (this.a == com.yiyiaddon.e.d.b.c.OPEN_MAIN_CITY_HALL_MENU) {
                                             break label93;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s3ifed46q8kgk4","17ZmcLq/tvvj6z299cP2lyjM+7oi7u2SVHge2e9bIeY=",2757992192159678888,8345271109861027093,-3224954479557599680,-1174196844077401831>()) {
                                             case 1118968556:
                                                if (this.a == com.yiyiaddon.e.d.b.c.CLICK_WORLD_TRANSFER) {
                                                   break label93;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s2dn1jm2ypr0m3","K0n0ex/1pqY4SA+//rMaO17kgC/f25uG0Libw/sryXk=",2681633504693517456,5000791088969349968,-40188271453850785,-2365324676062278670>()) {
                                                   case 1146719355:
                                                      if (this.a == com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_FIRST) {
                                                         break label93;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s2y0drpivoeevs","2tUeqF8ylA7X2QEOlxYTszuYBNOkJj1M6WfByf8suPQ=",-1147847570277982985,-6364834356938353298,2232125565265727923,-4779577594136305609>()) {
                                                         case 1544750170:
                                                            if (this.a == com.yiyiaddon.e.d.b.c.CLICK_SURVIVAL_SECOND) {
                                                               break label93;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s154gdez97n9nx","d7lbyLDRyD4jIwuXmDew6wEWNplTt8Bv0a00dxY/m8Q=",6524955215322588445,-1521325358716420029,-2312811536388088732,8269768323693559595>()) {
                                                               case -1492494649:
                                                                  if (this.a == com.yiyiaddon.e.d.b.c.CLICK_TARGET_SERVER) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s25mbyw5t0dmlw","y2zYIdTqHgiDOb2ZYnHG+k0hTXVaoGpRxCsXEyY+p2g=",1323908427682439936,6586966653333856299,2503759104897026452,-2988910459197914109>()) {
                                                                        case 1758326065:
                                                                           break label93;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }
                                                                  break label62;
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

         switch ((int)com.yiyiaddon.m.b.a<"s36mkyrt22wuae","uYow1VWtezoyK8lfqUZjvyecFYRVxnKtEoYK8lwBIgM=",-5541034772601722223,-2687481214692797698,-6767890793875558900,3769594343810576637>()) {
            case -2108014633:
               return false;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"str9pmxfgw7qa","a5A+YdpXe+Z3Hc2KKDiwsCbmcmqg4EDxHSVhFm91k7w=",-3575494303899759087,-2032773271253550091,-7997755407072491361,-6256335623742759897>()) {
         case 1371691426:
            return true;
         default:
            throw null;
      }
   }

   private boolean ao() {
      if (this.o.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2x5lppb5ux9gv","+qRZ6KK2lURl+fflj5fqdq4rxXyRtwtHsr4xp4Gm/+M=",3043097373178107099,-5217872405854273964,-5216922943324122268,2938216234222628936>()) {
            case -468180373:
               if (this.o.player.containerMenu != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s29edzimx2zb6v","5Nzid0BOpR3lxxvOtksq0yERQH8w2bErvBSB+n69Too=",1635682202223890909,-3867701447485275929,-120757970865207956,-4081312849591170163>()) {
                     case -1750455390:
                        if (this.o.player.containerMenu != this.o.player.inventoryMenu) {
                           switch ((int)com.yiyiaddon.m.b.a<"sywn3xq5qpu1m","JZ11uO9AfBuJgnM+kWkHMZ8cuYUZcKjRnclYqEqokh0=",6214114895585056762,8307928431342015780,7527342867327664459,5597830897681967986>()) {
                              case 549781432:
                                 switch ((int)com.yiyiaddon.m.b.a<"s17qqtfptsssij","4wKRwIGCd9VbjjXQ4UdGrlDm55pvrR2lMLly66D//78=",2323188038061328343,3850863617581734349,-7037665378554872212,4948706256204100744>()) {
                                    case 1741505023:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1sv74k5yew354","JefY0QaS/FX8GMu4Q3T69A48VyZ8NmNj1ZfoXB5gu5k=",-7795775423887198134,-5100738549589129652,-204912864347677497,-4707105759068796498>()) {
         case 1974180366:
            return false;
         default:
            throw null;
      }
   }

   private AbstractContainerMenu a() {
      if (this.o.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13cevljqqrbch","9dBpVc3ai6Is3RmQBI/V8LMPyZVLP5SwDmon9ZwcB04=",-8068055273830319881,650484967078040516,1416008007342234056,8836915014991318129>()) {
            case 1369370110:
               if (this.o.gameMode != null) {
                  if (this.o.player.containerMenu != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s204dncx871lqn","6kpLiq+zgkASo0oQnaEbzqs9GbPKSszdpmveR6reB88=",8588329115961623231,-8720227464405385060,1595106064104086063,7500269475635230975>()) {
                        case 741355085:
                           if (this.o.player.containerMenu != this.o.player.inventoryMenu) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1dobew5vtaj8m","IuK/RsYQUu960iER9Qv2q7A2T4V9aT9yZ3HoOXL2ghs=",5092235552504103178,3660785195059763540,-2531694185022994356,785369871645672156>()) {
                                 case 1410877530:
                                    return this.o.player.containerMenu;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  Screen var2 = this.o.screen;
                  if (var2 instanceof AbstractContainerScreen) {
                     switch ((int)com.yiyiaddon.m.b.a<"sxt7glgbclgqw","E0N49zx9UOyzXlvNAAX5uy6Ms9HfiYRRt2MtpHMsdKg=",6139052466382407272,-1481453002860494286,-3911906706570811999,7507968793753093024>()) {
                        case -471505570:
                           AbstractContainerScreen var1 = (AbstractContainerScreen)var2;
                           return var1.getMenu();
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3k5kfli3exr9","ej0ns4t7rEa+w4xr7prRQn5f80i74k+leg6gv+Cr0LQ=",4842004324954606823,-9161594431436769453,3394070543964125731,-524084641220977005>()) {
                     case -1764616532:
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

   private void bh() {
      this.a = this.o.level;
      String var10001;
      if (this.o.level == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s1qjx8scef2dsf","DgmafapQH7BpeBCW9kn5IR/V/XtWuQil+sa9i8JwhhY=",7168650969428717083,5664320155203215052,5008027359059007199,-6864735096341567840>()) {
            case 1240868079:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1lzmfw42j8unz","qj0tE6ImmPVq8Lt8tP8E46oG3CkR/UGnzcSGrQ==",2649992785071553760,6297062249610851010,-6236636727716266119,1928817206455334865>();
               switch ((int)com.yiyiaddon.m.b.a<"s1a1swnqbmrsm0","3WnO0AqMq4yLMS9u5yuQEK2wHJxkotTIOZDjigNWeu8=",4856995370876873135,2267350827885317921,6810683997503856806,6966175566694260734>()) {
                  case 1593714833:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = this.o.level.dimension().identifier().toString();
         switch ((int)com.yiyiaddon.m.b.a<"s2s1fnhf33phbx","0hgxfnU+HeVGPKtTj9HTm5lQ5iPhuHjFjDIH4h+9Mvc=",174232943728067693,3791993347934984001,417903425510484689,4386111908034685295>()) {
            case -2113956820:
               break;
            default:
               throw null;
         }
      }

      this.fp = var10001;
      Vec3 var1;
      if (this.o.player == null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s31n5v4rblzt4x","7Om9uYvptYPHDaf19ZymQRKn0P3ZsYv/zLTeVOm2BoI=",-8595282079881814853,2953929619064657403,-4793199540748886372,2810761031346610746>()) {
            case 1145484987:
               var1 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s31npprdsmdw75","NMFUMxHQotc2r0TqtvBOVN6gTk2y+HRX4U9GxCyPUNU=",207881704435883601,8589425106850541558,-1613229944498052647,4625740799077487399>()) {
                  case -834003256:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var1 = new Vec3(this.o.player.getX(), this.o.player.getY(), this.o.player.getZ());
         switch ((int)com.yiyiaddon.m.b.a<"s12i4hugv5h8c5","R6mJapBKFOIgk2QottdkIuFzQ2P79J52AZu1vPuMWZM=",-8115691379984229527,330877806697269359,-8469209174300317845,-5281727926429483721>()) {
            case -1575730284:
               break;
            default:
               throw null;
         }
      }

      this.b = var1;
   }

   private void a(com.yiyiaddon.e.d.b.c var1, int var2) {
      this.a = var1;
      this.cB = 0;
      this.cC = Math.max(0, var2);
   }

   private void a(int var1, String var2) {
      this.a.u(var1 + var2);
   }

   public interface a {
      void u(String var1);

      void D(String var1);

      void E(String var1);
   }
}
