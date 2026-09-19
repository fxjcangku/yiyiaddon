package com.yiyiaddon.e.o;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.o.c.d;
import com.yiyiaddon.l.f.i;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class b extends com.yiyiaddon.d.b.a {
   public static final String vP = "flightbypass";
   public static final String vQ = "飞行绕过";
   private static final long ae = 5000L;
   private static final double aJ = 0.28;
   private static final int ot = 10;
   private static final int ou = 5;
   private static final int ov = 5;
   private static final int ow = 40;
   private static final int ox = 36;
   private static final double aK = 100.0;
   private static final double aL = 3.0;
   private static final double aM = 0.5;
   private final Minecraft ah = Minecraft.getInstance();
   private final com.yiyiaddon.e.o.a.b a = new com.yiyiaddon.e.o.a.b();
   private int cV;
   private int oy;
   private int oz;
   private BlockPos Q;
   private long af;
   private boolean es;
   private boolean et;
   private long ag;
   private long ah;
   private final Random b = new Random();
   private com.yiyiaddon.e.o.b.a.c a;
   private com.yiyiaddon.e.o.b.a.b a;
   private boolean eu;
   private static final String vR = (String)com.yiyiaddon.m.b.a<"sh4oq3jrartpo","uPyC4wV6f2tYPyCy90/gNEPorjsecWJbnaFFfGT0",3203664826345531723,-5280718812113170867,-8474142148296369947,6697728978137559874>();

   public b() {
      super(
         (String)com.yiyiaddon.m.b.a<"s2l212q917326p","pmYrVQQJkh3xFz9pZRPNeE5zJN45L27u38auEfG3U49V9mEIov0NG62DFNzuwAQ33EZRRg==",-4595944237634528704,2066651896611793132,-3524702414747701170,278707603538512755>(),
         (String)com.yiyiaddon.m.b.a<"s2guh2mevb7my1","D6TMfIB7Ag41ozhF8e4sO3v6gge4CMK4RNvuCll2NcHO4j6R",8655844576731871251,5889052831157191306,6333072454874071774,727182076252853683>(),
         (String)com.yiyiaddon.m.b.a<"s27jncnyuj471j","HVyLIrGW+mpOGvqX7BV6VJXXfdX2HR+qFNKfZHPTklT8Z92RMH6tOg==",-1880858741071498767,7579433527309074725,-3121554488827790716,6230972593370535252>(),
         (String)com.yiyiaddon.m.b.a<"s1oxzmcfg1iv53","Y9xQpApRYQv9DlZ8r761h4PIdu2E/dv+eSo8O3uvRyiyffiA2/evSsA0FR27iWRDuUIR8z5rv+W36Fk0IHIoeikO1sFDFdSNhCQ6b9neJEWBZaK14ISJCAEybrA1AQ==",-4064664323378168968,6677674536303103356,23866890508480302,-5593313057331562795>()
      );
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"sh4oq3jrartpo","uPyC4wV6f2tYPyCy90/gNEPorjsecWJbnaFFfGT0",3203664826345531723,-5280718812113170867,-8474142148296369947,6697728978137559874>();
   }

   @Override
   public int i() {
      return 20;
   }

   public com.yiyiaddon.e.o.a.b a() {
      return this.a;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   @Override
   protected void m() {
      if (this.ah.hasSingleplayerServer()) {
         switch ((int)com.yiyiaddon.m.b.a<"sxvhoq71ipugc","KexyhTnye5P+U4clJSQLzEGr5YU3O9AAA8L09USh/nc=",6996347203387890236,-755047398502615421,-3579168270757170472,1358432321592242025>()) {
            case 186683144:
               e.b(
                  (String)com.yiyiaddon.m.b.a<"s2l212q917326p","pmYrVQQJkh3xFz9pZRPNeE5zJN45L27u38auEfG3U49V9mEIov0NG62DFNzuwAQ33EZRRg==",-4595944237634528704,2066651896611793132,-3524702414747701170,278707603538512755>(),
                  false
               );
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s2guh2mevb7my1","D6TMfIB7Ag41ozhF8e4sO3v6gge4CMK4RNvuCll2NcHO4j6R",8655844576731871251,5889052831157191306,6333072454874071774,727182076252853683>(),
                  (String)com.yiyiaddon.m.b.a<"s1v1v6bui6e31x","96F9d9yKrfzFx3wd8RnzrTLeUdMW6zbHma8cfk42RiuxMBnY1l4UkFdzCxcoDQXFr4H1dw==",1726424682855198436,-8215646942103740824,5916685832286449664,-1433146707077735781>()
               );
               return;
            default:
               throw null;
         }
      } else {
         this.hU();
      }
   }

   @Override
   protected void n() {
      if (this.Q != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"sunyzuduo1qoc","bVV2CVmzUUK+aYny380OpQgwSF9AtSfITH44a0XitIA=",-5830937985795587452,8419528776770794304,6520351076647617928,-8341081328789312745>()) {
            case 1772180460:
               this.z(this.Q);
               this.Q = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1g1800mtr0xos","T7FOUjt5shssJLq/Q7Mj2oW9cJv2Z15PhoQ0aYiaG+c=",4274254642176091271,3037424136436229554,21882191316567432,-4122057826192213575>()) {
                  case 408507128:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.hV();
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.JOIN_SERVER, com.yiyiaddon.d.a.c.DISCONNECT);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rk4zy2qumtc0","HMkv3ZW4tUYsJN8aJh2vzuH9gRnNf3J9RjGx1lJzUvQ=",2466537896903050191,-3567168377974608010,4680983781299536584,4245360825274730110>()) {
            case -294753139:
               return;
            default:
               throw null;
         }
      } else {
         if (var1.a() != com.yiyiaddon.d.a.c.JOIN_SERVER) {
            label23:
            switch ((int)com.yiyiaddon.m.b.a<"s20xwwbl0htisz","Po93171F5Ag7M4yZVL+BaYu5Ivgr0nnwFNJkVYgL1LE=",5007356137738451271,2708026367560890835,-7354571190663240073,5312915430414271464>()) {
               case 850680429:
                  if (var1.a() != com.yiyiaddon.d.a.c.DISCONNECT) {
                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2dowdfhj8b0sx","s7/y0joQeGuHECfY/pwmqU8p7pEwT86jgTk7TeZwwbo=",2699952260270040131,-5730773975541551351,-7325249257982720699,-1909487534052411788>()) {
                     case -1435912813:
                        break label23;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.hU();
         switch ((int)com.yiyiaddon.m.b.a<"s2p92weoongewn","idOI4Hgzq0Mx5yx3wt3fP1B7uviBdSoyvAD8y4etVS0=",2785223872813702251,-2153274102584473625,-5818945964652677758,6551807191415208231>()) {
            case 1971679459:
               return;
            default:
               throw null;
         }
      }
   }

   private void hU() {
      this.cV = 0;
      this.oy = 0;
      this.oz = 0;
      this.Q = null;
      this.af = 0L;
      this.es = false;
      this.et = false;
      this.ag = 0L;
      this.ah = 0L;
      this.a = null;
      this.a = null;
      this.eu = false;
   }

   private void hV() {
      if (this.et) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lz9u47arsam3","LY7PC8wXG2tafkRahVpSGpfmx+fOc8C3HxLeNUY0rC8=",1569266807623688451,2893989699883702593,7203560900441704218,-6926946806203261579>()) {
            case 1044178366:
               if (this.ah.player != null) {
                  this.et = false;
                  Abilities var1 = this.ah.player.getAbilities();
                  if (var1.flying) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3dvup2kn25to2","KxhPBjmZwSC/U9uU2akRwrPU097y+MEeg4Od+qiIFGQ=",-3423802417588597108,-1088203497601243184,2678234988437057259,-6176446557884220368>()) {
                        case -2022438709:
                           if (!var1.instabuild) {
                              switch ((int)com.yiyiaddon.m.b.a<"s9ga9tfa3od7y","HuHUW4rHmKKsPanhKdAkH1x7oHL5GdQEMOyLLeNJLV8=",5846642471503252997,3227090859951061615,-1202712955856553241,6635432758355507772>()) {
                                 case -286146629:
                                    if (!this.ah.player.isSpectator()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1a4b6y01onlpf","maNH88OOYHI6UtJZGkn9XU227DXZrRjjwPk9thH2Gks=",-243127024476258724,-2965971795943243423,763231109964127775,2757069592216642700>()) {
                                          case 2141658439:
                                             com.yiyiaddon.d.c.b.a(false);
                                             switch ((int)com.yiyiaddon.m.b.a<"s34ooe1byrsvv","QFeV0sOolpqO2xM7Xmnf8zAGo7gDtNEIQNsfDCKAYVY=",-6453234310860226489,8569713352670960217,7219074920619940503,-3465736498692061370>()) {
                                                case -562239006:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"scz5k25lup7i2","k9mWP1s/JGsRBedkyWKmNpnemBDDZAaJAc2VxzTF5mk=",9051519346732307385,-2764284968788376140,3852956356974093253,9080357552054140145>()) {
                     case 1301338600:
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

   @Override
   public void b(Minecraft var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sdt90y6vtqaxc","YvxFCrQqvZgG3gaLjBXrP5TVSFozat02YeogtDgNOW0=",4217067586801769310,-4323408265024094047,6247806859062311652,7005895580245150155>()) {
            case -1917600226:
               if (var1.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dc1k8cn8jetv","LdZOTiTbjRHzwhy5++vyuR5Fd815Nzixu6/TXshUGuU=",-3629661578893959344,8568530769970400942,-7589400161907321874,-619393207454173494>()) {
                     case 444651292:
                        if (var1.level != null) {
                           this.cV++;
                           if (this.Q != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2d4iwm5cw9zzy","2h3/Z+pKmUSlzDtEdhJhBlIt9M3zb2kBr5/DmQO1BKQ=",-3553449879563179180,6069593803900082378,7965012328328942287,-3800338540215786040>()) {
                                 case 1843856309:
                                    if (System.currentTimeMillis() >= this.af) {
                                       label67:
                                       switch ((int)com.yiyiaddon.m.b.a<"s22lzqqpu1zvvh","yxDtnTmTIiT7HXeKnOjguaABz1q8Gk0HXp+BLT7T0E0=",-6700546881158456810,-9023427716083864194,-1963015787920908514,-5339582089762547784>()) {
                                          case 1792648932:
                                             this.z(this.Q);
                                             this.Q = null;
                                             switch ((int)com.yiyiaddon.m.b.a<"s4ess2f1d1ozn","hoL/PtmCtHove4e0BAp46PflDZLx7/UuF160/Mav5qY=",-4472027052970693830,-4667598320080521833,6695886853846284577,-8724259235631129590>()) {
                                                case -765681690:
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

                           com.yiyiaddon.e.o.b.a.a var2 = com.yiyiaddon.e.o.b.c.a(this.a.b, this.a.eG, this.a.pi);
                           this.a(var2);
                           if (!var2.eE()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1ia1734318369","pqa2SAI5joNdv8snIuq1Iu6ERkISseYPn1ulXS4LZLA=",7064432491242635688,-8623987964228143148,2038801600375045183,2442817863147642913>()) {
                                 case -1625885731:
                                    this.hV();
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (var2.a() != com.yiyiaddon.e.o.b.a.b.PACKET_FLY) {
                              label60:
                              switch ((int)com.yiyiaddon.m.b.a<"s2fgh3cak2ib50","b729XZp8M4OqtIZUkFZxMr0TdqEdQNbfLtpqqZdqzbU=",-2339743425562948480,5525357224850405526,-3310380348755350365,6571972240613228446>()) {
                                 case -451347912:
                                    this.hV();
                                    switch ((int)com.yiyiaddon.m.b.a<"s3s24o19cj5orl","1dByQ2go/tMp73gHOx3eJGEQckdHuDBKHGsyAoaN9Rw=",-5266019950827162671,7509137749305519475,-3511884839933063020,5768386891505202480>()) {
                                       case -1000577832:
                                          break label60;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch (var2.a()) {
                              case PACKET_FLY:
                                 this.hW();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3usui3e60d3m2","tFNVxr3bohq4g1JXN0xyBBRotb9KzNP8zz2j9jPmGMw=",3594367137302733054,151330491672608167,5787763259301772752,-3694790119785090764>()) {
                                    case -349686543:
                                       return;
                                    default:
                                       throw null;
                                 }
                              case VANILLA_MIMIC:
                                 this.hX();
                                 switch ((int)com.yiyiaddon.m.b.a<"s45vthvnzra7s","tgoFvWqG3zmBT5c52BwU/ZlSVHhlNBgUY+pbaLvruFY=",464877746957080511,-853116756530625737,8465382820706505083,290244490609167451>()) {
                                    case 606222378:
                                       return;
                                    default:
                                       throw null;
                                 }
                              case SAFE_GLIDE:
                                 this.hY();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ym3ff6id7yp1","6ZgLd24+VK73X/t7Jv/WiBdBSSgzqC4gB3dNcciox2c=",-6587651310186979144,1795721366100277846,-2594171220989154454,-2476745089845343321>()) {
                                    case -1603628050:
                                       return;
                                    default:
                                       throw null;
                                 }
                              case FIREWORK_BOOST:
                                 this.hZ();
                                 switch ((int)com.yiyiaddon.m.b.a<"s2y151lnbh0olt","S2xLY/v0jW3hOzdn38LngbxTMzZSKYXsQ/i7Ka1V+ws=",7790339448088148820,5401335253959536769,5525478459285972549,-3677103719873019506>()) {
                                    case 2100618230:
                                       return;
                                    default:
                                       throw null;
                                 }
                              case SEQUENCE_SCAFFOLD:
                                 this.ic();
                                 switch ((int)com.yiyiaddon.m.b.a<"sfi3hxntpftnh","W1o1B9A/i+je+R8ooaRN9myMM0nhcmF20maoeut9kQ0=",8580161468921632189,-3232852055440478642,4395698956955568179,8150982018368965658>()) {
                                    case 433360844:
                                       break;
                                    default:
                                       throw null;
                                 }
                           }

                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sk3gllolremp","+KO/mXzOg8jTg0euwLmQKvL3mM7BOaBKPT6icrE57ns=",3132555717639796897,8031449960320755468,1107865869625278375,-399597290480462752>()) {
                           case 1545694449:
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

   private void a(com.yiyiaddon.e.o.b.a.a var1) {
      if (var1.a() == this.a) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hfthv0e8xfm3","Yk9/Ry0mY//4g1o5CUgS4cU5R6D2CI4A3oVic3ehUAc=",8295118447628994079,3562934912925231396,7783599460387695884,3869982481564579209>()) {
            case -175679861:
               if (var1.a() == this.a) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2u8iffi0ostes","CQKkrFA5U880PrNZigCrp4WdZCXT2CVyI1K4zdRxPgA=",8883727952047784001,-5490620616828271532,-3507573042044906789,8282101519269141571>()) {
                     case -1618038057:
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

      this.a = var1.a();
      this.a = var1.a();
      switch (var1.a()) {
         case DEGRADED:
            this.eu = true;
            this.u(j(var1.a().wH) + "");
            switch ((int)com.yiyiaddon.m.b.a<"s1mviefvjucp52","Jc1IZ+v5s4I59i6Qprxf3w8vnrqHnlXwNpA3Lmp0US0=",-6517958924331021794,-3683803597783546468,-5674488896709705821,3342292881669585597>()) {
               case -1347992603:
                  return;
               default:
                  throw null;
            }
         case NO_FLY_ABILITY:
            this.eu = true;
            String var10000;
            if (var1.a() == com.yiyiaddon.e.o.b.a.b.VANILLA_MIMIC) {
               label43:
               switch ((int)com.yiyiaddon.m.b.a<"swbuzx85u8gt2","grH91sfOyQmOBeZk1k5EaToD6DPYUbh1Gsjbim8fCjw=",3479186909927483724,-1978485843593165808,-3449336261287916280,-6629930848607930635>()) {
                  case 1203033857:
                     var10000 = (String)com.yiyiaddon.m.b.a<"s3re96i3r2t4ur","YnaJMpNNSWaexOld+XhyPGBXkuzlUNBKai04huNDWE8yXkILw7WFX0BVJL+8O18A5ZsGjtMSn2oHpd/x",-3737155296339451616,-7352222180288198736,7591241238642271,-8990916647958509214>();
                     switch ((int)com.yiyiaddon.m.b.a<"sw57v18mp5ywb","ObSAXFePKXJ/TFwuf8Z6Y9DgwOfzmGMQ5dikm/SBM1o=",-2475101487440223988,-2007865181015151706,6837056747375924658,-3664553793394534852>()) {
                        case 1786825166:
                           break label43;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = j(var1.a().wH) + "";
               switch ((int)com.yiyiaddon.m.b.a<"s3fue2p1xwbejs","0SNpMt2c9wKKqG9BIV/N+AtfN61W4e5OdXc2Vx7qrmQ=",4236250824496561260,-3856463624486516639,5328584954198374435,7854469784865550108>()) {
                  case 1206262576:
                     break;
                  default:
                     throw null;
               }
            }

            String var2 = var10000;
            this.u(
               j(
                     (String)com.yiyiaddon.m.b.a<"slcb535tuf014","Xi/SnWTwpv4LRPuTVZUcqDCW1uRiWmssLICXQ45ef7oKiHZW",6402950224427239808,-7586437308987164460,-4949677045325757359,-3213440664761244800>()
                  )
                  + var2
            );
            switch ((int)com.yiyiaddon.m.b.a<"s3pif8ywq5ahud","49p4Li8QywqX9LAjWzAi+s+nJgZHqd1likcyTFfbytQ=",-2663013276919615290,2323683900850548022,-4036390286110149065,-6215983170316315481>()) {
               case 1907026302:
                  return;
               default:
                  throw null;
            }
         case HIGH_RISK_AC:
            this.eu = true;
            this.u(
               j(
                     (String)com.yiyiaddon.m.b.a<"slcb535tuf014","Xi/SnWTwpv4LRPuTVZUcqDCW1uRiWmssLICXQ45ef7oKiHZW",6402950224427239808,-7586437308987164460,-4949677045325757359,-3213440664761244800>()
                  )
                  + j(var1.a().wH)
            );
            switch ((int)com.yiyiaddon.m.b.a<"s3cy1go40ip0x9","TL93jEYJU5eDlsvDqO49LddzMuRja56uPkacSWXOE70=",-636533035537655711,3294798117885743051,3006234406233502670,-8739847007295528992>()) {
               case 978014553:
                  return;
               default:
                  throw null;
            }
         case GRANTED:
            if (this.eu) {
               switch ((int)com.yiyiaddon.m.b.a<"s2eyyfiknkk6u5","MxZzyds4YbLJhglMOfyQNgpFgsTh7J+IPDrf3bMwFcA=",4804062059645842537,312005639185177350,-1732840838253692120,-6730839760232090576>()) {
                  case 334724557:
                     this.eu = false;
                     this.u(j(var1.a().wH) + "");
                     switch ((int)com.yiyiaddon.m.b.a<"s10je66zb63e5i","F5Ye42VjAcssRV7JGU0cav/o9oKxJyiCas6Z8u3pQBs=",5805844182720524273,1515627473804302590,6122576337403412781,-339020787909903500>()) {
                        case 1818069959:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
         case COOLDOWN:
      }
   }

   private void hW() {
      Abilities var1 = this.ah.player.getAbilities();
      if (!var1.flying) {
         switch ((int)com.yiyiaddon.m.b.a<"s1i3w79h30uavp","pEfLQ+tgeqqXxXiYrSpv+FfDgplbeu2EslaRmhl5DVQ=",4341120428315414748,-7230639436151832138,-736245944737875247,-1405864313932592807>()) {
            case -498058053:
               this.et = true;
               com.yiyiaddon.d.c.b.a(true);
               switch ((int)com.yiyiaddon.m.b.a<"s15nknrdz6jq26","59PL6NvINAxgdpZn8vVJiT5QuRE8WZ0SUeasr1KCYBc=",6045512159579185005,7967103731277267468,-1746400489864394636,5824066394813803826>()) {
                  case 1729208845:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void hX() {
      if (!this.ah.player.onGround()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ttn2l2xgza0a","SV9yGD80VgDlSnOCLeaaOhsSG6WhruKdsujtJSEBnxc=",913987015719375637,-1098278528727206207,-691845674761456682,7265687584523698894>()) {
            case 328970411:
               return;
            default:
               throw null;
         }
      } else if (this.cV - this.oy < Math.max(1, this.a.pg - 1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s10ss0z5xvpvhc","0I0hn0/bWItOVBtMOtgtB0I0lDW4CgutNQNqgVGc3AU=",-5760511886298797105,-3232836719126746104,819145671789116618,3462306227304919462>()) {
            case 778752539:
               return;
            default:
               throw null;
         }
      } else {
         if (!this.ah.player.isSprinting()) {
            label23:
            switch ((int)com.yiyiaddon.m.b.a<"s1na338qf7r78b","6Mnzmyu6QjIuNdspMM8Fm8q73vs3lzbyuw8OzvlS9A0=",2212944628766496032,-1467938340878549369,-1501498073375795200,-2379726514980380581>()) {
               case 236571430:
                  this.ah.player.setSprinting(true);
                  switch ((int)com.yiyiaddon.m.b.a<"s8znub1n4ev9y","ZmtDnwcSnJbrklrMr81qLLVhUjsKRo9eS9xfg1v8CjU=",2034308483390766797,-8098962687542902075,5095484477547838223,1695167341380498278>()) {
                     case -861135025:
                        break label23;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.ah.player.jumpFromGround();
         float var1 = this.ah.player.getYRot() * (float) (Math.PI / 180.0);
         Vec3 var2 = this.ah.player.getDeltaMovement();
         this.ah.player.setDeltaMovement(var2.x - Mth.sin(var1) * 0.28, var2.y, var2.z + Mth.cos(var1) * 0.28);
         this.oy = this.cV;
      }
   }

   private void hY() {
      if (!this.eD()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1oyxd1naezrjl","okHEAxehQ4mZ7CHezX7zU3TvAnoyVBPEC21mC9TRaCY=",7037409713494023768,3631939521686222148,-3083884350416537585,-5801217217774355737>()) {
            case -1112261791:
               return;
            default:
               throw null;
         }
      } else {
         double var1 = this.a.pf / 100.0;
         double var3;
         if (this.ah.options.keyJump.isDown()) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s1kig2xnpf8cey","OBjr3ZfmqIYIpKojh5Vjnz/VWGrHxkLERNlLUYN23ns=",-9004907386809869137,7404780743846224346,-117508077485520052,2859781164465009410>()) {
               case -1087151110:
                  var3 = var1;
                  switch ((int)com.yiyiaddon.m.b.a<"scvhknuaj0cyv","8iVXz3LqvdzROI63ySwOHwLL8FLfS+em/xHmXaZyBtc=",7961643330760974976,3965820390180484400,-7249759233892899748,6720904391543604842>()) {
                     case -51678116:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (this.ah.options.keyShift.isDown()) {
            label28:
            switch ((int)com.yiyiaddon.m.b.a<"s2g2nwrxts0se5","k6tVGV67fNAkBJKVdeT5ife9FPs1VcqrcYALjk8ucwQ=",-17903250341875845,643416432362288901,-4226052927609351973,6050371895691091951>()) {
               case -1806103108:
                  var3 = -var1 * 3.0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2z47r9gfrsu7","kao8leZCOHDxqWdPO+T442CeXI+1akJWG3DEiqLzqw8=",1510324404996105380,-5948162104478634663,-6096235788332067452,-7318609730950362732>()) {
                     case 232770460:
                        break label28;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var3 = -var1 * 0.5;
            switch ((int)com.yiyiaddon.m.b.a<"s3geeds1iwg4q6","FJoDhXnIqa/lbu0JgDrq+YvRxR6ekDRIBC5LZXLvPqM=",-1414784799381335561,3444972612320178058,-5020623326117080289,6711937429379130577>()) {
               case 1223246952:
                  break;
               default:
                  throw null;
            }
         }

         Vec3 var5 = this.ah.player.getDeltaMovement();
         this.ah.player.setDeltaMovement(var5.x, var3, var5.z);
      }
   }

   private void hZ() {
      if (!this.eD()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g0ypl0r2oqfj","LEAM165bGgFAYN1vqsv+Amm/PE0bnexv2fmtbShYvjk=",-8032933723480729089,858850843216417225,3801966397044773764,9016591388757039232>()) {
            case -868032157:
               return;
            default:
               throw null;
         }
      } else if (this.cV % 10 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jtbg00d6tma5","3K4q0H+bw4hH99s0bU8oshi0x+4xhY2ja+SC+GyNzAo=",8132150416273548479,-791905798482252588,-1986273868147033563,64211945804714607>()) {
            case -1071912486:
               return;
            default:
               throw null;
         }
      } else {
         label102: {
            boolean var2 = false;
            int var3 = -1;
            InteractionHand var1;
            if (this.ah.player.getOffhandItem().getItem() == Items.FIREWORK_ROCKET) {
               label71:
               switch ((int)com.yiyiaddon.m.b.a<"s27ucwz3gny9n7","RDlTHPsAzN9FF2N+4SrfwDHY5vu4NPX+9CgV4sXnTCE=",-2650492317579667534,8082791868514565739,5624288426740288517,-4227709453055107237>()) {
                  case -1155131307:
                     var1 = InteractionHand.OFF_HAND;
                     switch ((int)com.yiyiaddon.m.b.a<"s2vkw6fcqbawwm","ekCdy+rdvD8MYRhtCx8hFgP3XEvnR+q1/0qa5eXAWGs=",4838331949639570280,6539623643965238764,3031800302876195232,2099938151599197645>()) {
                        case -1028145958:
                           break label71;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (this.ah.player.getMainHandItem().getItem() == Items.FIREWORK_ROCKET) {
               label74:
               switch ((int)com.yiyiaddon.m.b.a<"s1snqc1gwsqhyu","USuN9Ap6GNO2iv7NAVWI0NjtWHOTC+sEAX9JY+Jcvrs=",-4158030813955322541,1110169189187432766,6827992745803197151,3448727852851553029>()) {
                  case -199966372:
                     var1 = InteractionHand.MAIN_HAND;
                     switch ((int)com.yiyiaddon.m.b.a<"s3o8c8xddvljwi","CXwVpPsuEtp+Uhz4h5DJBywCDijdqK+Tyysf1riJqYQ=",8204639425735410085,3375822635690549564,-5876900374917869782,3317920172589130778>()) {
                        case -1267917167:
                           break label74;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               int var4 = com.yiyiaddon.i.a.b.a(
                  var0 -> {
                     if (var0.getItem() == Items.FIREWORK_ROCKET) {
                        switch ((int)com.yiyiaddon.m.b.a<"s25vm9lpfoy8kl","dYLLCxnrHIEXAopvcVQruMisIVpiLtBfIm2uXp0ZH2I=",-8625258328115285702,-2251647185292998216,-5627850759870893700,-3648841665204532780>()) {
                           case 134128135:
                              switch ((int)com.yiyiaddon.m.b.a<"s1oj1qyrviz2m1","CJkswtjzWvrIpULPKK9oc8vcK6e8/n0TUf+DWKoprWQ=",6294322538272136240,8236667562725706708,6737029524761655781,5145735548818356780>()) {
                                 case -1105839414:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"sy959qsj6nbl8","duknugPqQbjdrCFYXyw5AseV1UWOvTukazc5zbPQ7qQ=",-4780362003547225560,2387817999528005489,-6814669251036445926,7648681827202364562>()) {
                           case 1507894991:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               );
               if (var4 < 0) {
                  break label102;
               }

               label81:
               switch ((int)com.yiyiaddon.m.b.a<"s1os3q03ngpeop","8PmBv94ewOR/x37xb8aGAtz5/Mt40ske63R+d2uX7AQ=",-4857949328072074317,-1413988320384903472,8895368839867614333,-8956826177506234596>()) {
                  case -1202862277:
                     if ((var3 = com.yiyiaddon.i.a.b.dy()) < 0) {
                        break label102;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1f1ki368nuna9","daZssYByXrHBCfNSSkLo/hUCWS9EpeBs7U73c8RCIzs=",3717015772373262718,-3048191956822442380,5126106378940871767,7507869494662569172>()) {
                        case -448958060:
                           if (!com.yiyiaddon.i.a.b.q(var4)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1aubui9dvfkn5","AYOc4o4hcjf9acOI06vlLIUaWlWR8ofBwS4QZ0h3n9Y=",4957517142067605186,5412963625760442837,-6549596782086630733,6114687268441470672>()) {
                                 case 1427800750:
                                    break label102;
                                 default:
                                    throw null;
                              }
                           }

                           var1 = InteractionHand.MAIN_HAND;
                           var2 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s3aayipxsmty50","oiX1gcAR2xLxvJ1FIdMx0JYqlYWDbiffd+r2Y1osxzI=",2272678541068824461,-5506967246061040659,-4991866787042610136,1965518261511270960>()) {
                              case 1646948382:
                                 break label81;
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

            ItemStack var6 = this.ah.player.getItemBySlot(EquipmentSlot.CHEST);
            if (!var6.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s133pbfbqrn6y","dbQQwsSYdAEh0j+k10bIsrjFK4x/pGqI3nQ/01VU7Yc=",-7032484959809489164,-5119342538775765354,-4381418406052519502,5478297791309422903>()) {
                  case 1353631146:
                     if (var6.getDamageValue() < var6.getMaxDamage()) {
                        int var5 = com.yiyiaddon.d.c.b.k();
                        if (var5 < 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2m9h2lr16qaaq","ysxnEOBKvaCQbdtZyvuIqEPEQkaRjheTiTSe/5ywbZM=",-273099804659169260,-2150712520662768554,-7688722762611523507,-235639812049618289>()) {
                              case 1709201851:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.d.c.b.a(var1, var5, this.ah.player.getYRot(), this.ah.player.getXRot());
                        if (var2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3h32gp4wpw364","UBcQxf2i7OE989OiDhN4JNopCidiyNqmtS7kWXo3AVI=",-8884273336323045162,6059932890244152027,-4828945709991720273,7893766590062311557>()) {
                              case 1642021648:
                                 com.yiyiaddon.i.a.b.q(var3);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1s7q9m56ecbch","LJRGxinoHFymMkuutqBKJ0RWGqcFdU58ev272PdPTVY=",-4254690093327370665,4728160563737737942,8915670665871563275,7493012147093079869>()) {
                                    case 706993193:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s2aixelormkinv","zKEFdBVddJ5pMQySeXYMZkj0P+xB+RcRBfq1XPIdO54=",-5248347282664355152,-412651062674245222,-4734212247169522943,-5429365357520425268>()) {
                        case 1437099022:
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

         this.ib();
      }
   }

   private boolean eD() {
      ItemStack var1 = this.ah.player.getItemBySlot(EquipmentSlot.CHEST);
      if (var1.getItem() == Items.ELYTRA) {
         label41:
         switch ((int)com.yiyiaddon.m.b.a<"s1a8b56pqghzo5","zNElM/vJsgPZnMVCm57LJLdOs4zXKU7Dh0z3/3LmX9A=",6093581946668174757,7278555597083331485,-5078890452345207999,6844184327783089508>()) {
            case -712664387:
               if (!var1.nextDamageWillBreak()) {
                  if (this.ah.player.onGround()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3t66tlgwdf7ja","V8XRYm4oqi431Aa3ocJ+D8USDrwSdLE4eNAksYZGIUA=",2384594369204931774,-1833970667760858611,583408982105097673,690095339623972732>()) {
                        case 215362383:
                           this.ah.player.jumpFromGround();
                           this.es = false;
                           return false;
                        default:
                           throw null;
                     }
                  }

                  if (!this.ah.player.isFallFlying()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s5n4wt8qg3ror","hg+D7c8PJOQz/UOrLMrESuFetCIgVpEzJ7+dGVn34Dk=",3113994288481913443,-5379460113610853734,1454876360246994558,176814255147756206>()) {
                        case 1089139622:
                           if (!this.es) {
                              switch ((int)com.yiyiaddon.m.b.a<"s37iddx7tkv1ug","3/ce/lVYlK1kYmAKFK2KZe3kguM1yUSexX/aZ5l8CSg=",5886584140041843603,376267921988431987,4433529265800192426,5455056895306685324>()) {
                                 case 1707808958:
                                    com.yiyiaddon.d.c.b.j();
                                    this.es = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"sa3ojpdrked15","j8KlyIuS8QiJPJXKF2XMnZtUMuAZs4dXJOtb3N3C+eA=",-322784722947495816,4590986306878018781,3733075209202639547,2659602952549798137>()) {
                                       case -1112945612:
                                          return false;
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

                  this.es = false;
                  return true;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s156gidz2sgmuu","1ykOjg2qU4vMdOuMjKil+g8UOkSMDnYLbokh75Kqkck=",-2564326948286246298,7685340028646968881,6733643738664019317,3176458777820074240>()) {
                  case -79870651:
                     break label41;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.ia();
      return false;
   }

   private void ia() {
      int var1 = com.yiyiaddon.i.a.b.a(
         var0 -> {
            if (var0.getItem() == Items.ELYTRA) {
               switch ((int)com.yiyiaddon.m.b.a<"s2dt0n4v5ad7nu","bUGMFOVndqn+N+ZqUT0fKS5IqF0rxCkvc5b+XKZ45+8=",-6193203235534954177,990492190230445038,3398952739370854466,-4620435224484096942>()) {
                  case 1884990627:
                     if (!var0.nextDamageWillBreak()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2dzf5c6wvkz3e","0adnkBNLkVrzGrrdDZRmqzDG55WteWKql1r4SrS0Zas=",5551872396999203117,4094050121397748055,-3764464559229938344,1642006925317657638>()) {
                           case 1711273653:
                              switch ((int)com.yiyiaddon.m.b.a<"snozhb9z508fc","lnDE4XYNt6RHqt3JO6r6Dwn0ys0SSlhx9pFf5jEqRaU=",1037492043458183033,-2154555938564534433,3223969959873451076,-4221071553934072155>()) {
                                 case -796323311:
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

            switch ((int)com.yiyiaddon.m.b.a<"s1691mztybac7z","RnhHU/hEy32TjQ9h/6yy9dBxfON8QGjQcnOU8n61eDo=",8079271223164744510,-2928369537619745543,-8812049795480878125,-6541274059526475541>()) {
               case 334184515:
                  return false;
               default:
                  throw null;
            }
         },
         36
      );
      if (var1 == -1) {
         switch ((int)com.yiyiaddon.m.b.a<"sinqknu03xa7i","NGWKRYbQ2YYn3QznOMDZ2Yg5LhIfI3LJaJdLu5Hzb6c=",-825935177966430188,-204201369085573609,2810337915457652117,-6977345299600141169>()) {
            case -639080381:
               long var2 = System.currentTimeMillis();
               if (var2 - this.ag >= 5000L) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2d8i554xp30ze","YScePIZ+pk4GslPk8wvHGCMDb40WXoyK50zSSmP2pzk=",-4371412512512297959,-5486951946676733241,4046400035170284167,-8072729229927327301>()) {
                     case 1836878584:
                        this.ag = var2;
                        this.u(
                           i(
                                 (String)com.yiyiaddon.m.b.a<"s2t7cnz60ildky","EozCHLuYbYFmzaxY/CfLBz66PjovG1aPNmBiUR0LsH8=",-5410825793450300488,-5672932129199336495,6870351045774677796,-8052540355838241570>()
                              )
                              + ""
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s3e674dtnzrluc","IgDiLV+YvYf9DL3vdaVIrAKck66wsdvrS9UfNE0VeAo=",-2082136412332150213,-5778485961713904354,-7242447245336813578,-450063063969661578>()) {
                           case -1139134128:
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
         com.yiyiaddon.i.a.b.r(var1);
      }
   }

   private void ib() {
      long var1 = System.currentTimeMillis();
      if (var1 - this.ah >= 5000L) {
         switch ((int)com.yiyiaddon.m.b.a<"s140231f96ld6e","Tt7oUy6tAxGkwr+ffPsjWBpei9AbHHspGfzr91Bh218=",2787702699145136791,-344908772242664475,4917150536602153831,-5859306760493031721>()) {
            case 1534102549:
               this.ah = var1;
               this.u(
                  (String)com.yiyiaddon.m.b.a<"s2mrjndrvovldb","ihw0UhgPmsbc1ITepHRjl3XZpZcAXThJheq/Ly5KywOEYpHA/+UdyDgXV8wlPeLqiZPo4Igz/4GZGlS5GA/fUJs0eqKeiLab0QWA8CC32Z8=",4374422851284775124,-7262005916346242713,6714951272453728696,6835247005764098771>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"snghksmqhz9gd","JH/+ez4OFzg3CqZy/vSAVKVf3RDF/vuEo1h2EY1do/M=",-2481710023333731093,2260717491873551854,-7343795176180791545,-8242370993245591486>()) {
                  case -447950504:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void ic() {
      if (this.cV % 5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sfj0c2640sqfn","VxfSc20FqYDL0QTKh9W8J+9+Iv9h81eacbTYwkrR88Q=",3973190841036557072,-3444501706059646637,3167116800364452251,3887004015977358606>()) {
            case 2044620469:
               return;
            default:
               throw null;
         }
      } else {
         ItemStack var1 = this.ah.player.getMainHandItem();
         if (!(var1.getItem() instanceof BlockItem)) {
            switch ((int)com.yiyiaddon.m.b.a<"s23nmace64l6f5","+tKj+q1xkM+w5LeF17MqmGYrRBJSZBLhY+4x+SqFvo4=",-370232997135470277,1585040548198522226,5456741371097624682,3554687898096344647>()) {
               case 1034005846:
                  return;
               default:
                  throw null;
            }
         } else {
            ClientLevel var2 = this.ah.level;
            BlockPos var3 = this.ah.player.blockPosition().below();
            BlockState var4 = var2.getBlockState(var3);
            if (var4.isAir()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3qdoio5g2w05k","z2tqyJL0u/ZVJDp419RFSpWGnZdcotbvvaszZPQM1Xw=",1705119246205394621,2993248254841309300,-3512855523195575326,3783182504150452061>()) {
                  case 1540323885:
                     if (var4.getFluidState().isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s27w2qutm0fpsh","EGQcExDQJD67M2inVQpKmsS+wjMZ2NOc2PD5NeoMFbE=",727242143956936751,6392597996086050642,3633350077454541776,656957431457106527>()) {
                           case -973655327:
                              if (var4.getCollisionShape(var2, var3).isEmpty()) {
                                 BlockPos var5 = var3.below();
                                 BlockState var6 = var2.getBlockState(var5);
                                 if (!var6.isAir()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3frv626spuyzb","4aeXfyJyGz9jsj4ka2LkwMT1FpYX/9PqtgQjJcc+Vfc=",8677813814746851319,1168513650223060436,8539089284187639664,-6629918402557625492>()) {
                                       case 2070347235:
                                          if (!var6.getCollisionShape(var2, var5).isEmpty()) {
                                             BlockHitResult var7 = new BlockHitResult(
                                                new Vec3(var5.getX() + 0.5, var5.getY() + 1.0, var5.getZ() + 0.5), Direction.UP, var5, false
                                             );
                                             if (!this.a(var3, var7)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1u2j9k5iwu31l","N9nK3QxR5jSAkiYT7+QeV0QayJP+8xkWlOzAI0if4x4=",2106375920644460201,5844315471697711741,-2869474337002284740,-4984446113779935225>()) {
                                                   case -1859425816:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.oz++;
                                             if (this.oz % 5 == 0) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3s57957m7ryhg","GyvvEyBglKgi2GWwgOyXG7RDE44lG/Bd2mZttHuCfaM=",7459240745787750192,6222374003898794565,-6575528023408935448,-7156369943013578987>()) {
                                                   case -1178838935:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.Q = var3;
                                             this.af = System.currentTimeMillis() + this.a.ph + this.b.nextInt(40);
                                             return;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2nkarpxn9gota","FCkNMzHTxx855TEZoXdziyI2VodUwNOGeVYwHDC4qe8=",7016319181758293882,-1824066101434588055,-4090318826641672131,-3559896079828586191>()) {
                                             case -336993299:
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

                              switch ((int)com.yiyiaddon.m.b.a<"s22wh4v5kcgfnk","xUihe9iSwv/y2SsIplzSvNjm9t7LOYspTr47ZAQkWpk=",-3624069656482163102,221997425324503876,7667795806367051891,8164639567798023761>()) {
                                 case 101809334:
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

   private boolean a(BlockPos var1, BlockHitResult var2) {
      boolean var3 = com.yiyiaddon.d.c.b.a(InteractionHand.MAIN_HAND, var2, var1);
      if (var3) {
         switch ((int)com.yiyiaddon.m.b.a<"s32lwb8kewfhv0","q4dauHGNBglNEVpUFMcDQJ0XTkwqKh7m5uJcFYEvkDQ=",-4705532365521660165,2915393617470668528,1286280560131119218,7740480098706894699>()) {
            case 1921498501:
               if (this.ah.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ypt4zmbf0fd3","WGPegy0VDem54SABTCvWmSlrbZ6f4CCgCs1BbdkmAvk=",2168380074266331049,-1774762843587616156,2130689443700761864,4434147581646392093>()) {
                     case 1842328430:
                        this.ah.player.swing(InteractionHand.MAIN_HAND);
                        switch ((int)com.yiyiaddon.m.b.a<"s1sjjbeaeemwyd","pn4gw5afEeCci7nviJ8CXeUULzYhRWsHVDQe1iJVl5c=",6532245695784624793,9188506049895833698,-7370097468156602738,4571046744829778551>()) {
                           case 731911200:
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
   }

   private void z(BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dbmrneix9f0l","5HY27BCx01jdQs7OYIfjDMxPiw18kwR3E9wNCEKA9+Q=",-5170089254237348492,-570243473459366869,-5125203520638704970,6354373117516571988>()) {
            case -933991023:
               return;
            default:
               throw null;
         }
      } else {
         ClientLevel var2 = this.ah.level;
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2yq24j6v8m2t8","YCTlXDmUDhunkOR+poMDCyi59+WZ/Hx/ykmrFtCnM/A=",-5620859704153377930,-7667810653196687488,-1628018445510850442,-8311198552719566568>()) {
               case 3718402:
                  return;
               default:
                  throw null;
            }
         } else {
            BlockState var3 = var2.getBlockState(var1);
            if (var3.isAir()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ni31jvf6akwa","VwOgf/3cP+xngJcJeVjFGBVFixZm79E8ta7kYgwSeCw=",-5004609370016017540,-3002974062209150446,-9132122409979798407,555581954218659910>()) {
                  case 1420490417:
                     return;
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.d.c.b.a(var1, Direction.UP, var3);
               com.yiyiaddon.d.c.b.a(var1, Direction.UP);
               if (this.ah.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1y4zgghg4gni4","4wMqL6zeOdQ/ipLvKjolSKMPhhGuONNxlvrSEfA/Klc=",1683611817076839169,-6154634879082717849,-1040219944263968473,6628414655415130716>()) {
                     case 787709127:
                        this.ah.player.swing(InteractionHand.MAIN_HAND);
                        switch ((int)com.yiyiaddon.m.b.a<"sr1n21rd7mz79","q2VTFxjv8vwDl5TuhMRm+F07yKTwUJMm9WO0fc8Q4lg=",2471409394266295500,-8041759620063311196,-7283805376057210409,-3649753566130329973>()) {
                           case 581253273:
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
      }
   }

   private static String i(String var0) {
      return var0 + "";
   }

   private static String j(String var0) {
      return var0 + "";
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s2guh2mevb7my1","D6TMfIB7Ag41ozhF8e4sO3v6gge4CMK4RNvuCll2NcHO4j6R",8655844576731871251,5889052831157191306,6333072454874071774,727182076252853683>(),
         var1
      );
   }

   @Override
   public i a() {
      return new d(this);
   }
}
