package com.yiyiaddon.e.r.c;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public final class b {
   public static final int ry = 512;
   private List<Entity> cw = List.of();
   private Set<String> ax = Set.of();
   private List<String> cv = List.of();

   public List<Entity> bt() {
      return this.cw;
   }

   public void b() {
      this.cw = List.of();
   }

   public void a(ClientLevel var1, Player var2, int var3, List<String> var4) {
      if (!var4.equals(this.cv)) {
         label121:
         switch ((int)com.yiyiaddon.m.b.a<"su35hvpu7tnib","So7yjzsnUKaR2cbOcAuUIB1Z4Gvq/TUhB/1S3PznXt4=",2876200548074978217,5810214339183907470,6452825273508154206,2190586689536608993>()) {
            case -1516598429:
               this.cv = List.copyOf(var4);
               this.ax = new HashSet<>(var4);
               switch ((int)com.yiyiaddon.m.b.a<"splhml6r1t4o8","bGs/tLrrAviNqXODkKAmvcb6m/m30Nr0/d0/eJIDfBA=",-1527302285770701121,5357106944732429979,-7171954761812714713,3228157833725466934>()) {
                  case -1863862826:
                     break label121;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.ax.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v7ox8te87tey","CUrilUodp7m+UxqqQXIHIuM7ESOdgpeVT0l4l5igQpw=",8198081456334403834,1810672860648203391,-673946973114255627,-6783373468625453701>()) {
            case 114601517:
               if (!this.cw.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sygf8kc8xo41a","JOMjrvzgNx4GWmWdPLWQ6iFP2GfsWEpsmBOcIWm5A0M=",-6755289276687423057,7678614728081288318,8982091415137799540,-5957969098840678519>()) {
                     case -1829610647:
                        this.cw = List.of();
                        switch ((int)com.yiyiaddon.m.b.a<"s2k5vgg4ngmj8k","HA9fCuKXjN7+AVMmAcNvspiR23k6cp+julH4r+VpHI0=",6569539905198256361,-30890678350386295,-3590534061726843757,-2669749173052778815>()) {
                           case 2085748094:
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
         double var5 = var2.getX();
         double var7 = var2.getZ();
         int var9 = var3 * var3;
         ArrayList var10 = new ArrayList();
         Iterator var11 = var1.entitiesForRendering().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sy1zyzaqz3m5g","QKQB56IU1MvHHB1uGVxhJIuB2arTOgmzg8iqInSlE7c=",-7870340423413167581,3517511631737148013,5030825050733224146,-2331592948309954981>()) {
            case 747351013:
               while (var11.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ixdfdzr5dr5r","+YXqwc3KP9FGuKbGuJy3uMFt87Mx9zN3n9l6N+FetjA=",-6350121282510252225,7467829045508090188,-5013971616958342836,-1121434408289039957>()) {
                     case -528700045:
                        Entity var12 = (Entity)var11.next();
                        if (var12 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sk8hhqrnn1j0h","bO6H8jWn9H2Givctn+Wm5xH5J7iBp2HRzaCNlcNCfds=",4633000803769111649,-402759672619120724,5468755875047771303,7369517830218496799>()) {
                              case -2029033558:
                                 if (var12 != var2) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2vonvju1d5teo","jL1eDaGIRm7Hyfv7rvES+N0BX3wmPywFmOpMiVboRFQ=",-7084780709352543221,-342835815986285274,14521854468193851,-3505869226660685999>()) {
                                       case -1800304247:
                                          if (var12.isRemoved()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sgq0gdamzb77n","GCgua+HW3BWiquBX+aWM79J9rbhZF8AYqRJOWgxjCsY=",-2347753483496479663,2444185158707428021,-3110296567829693448,-8536317730347930367>()) {
                                                case -1606331604:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2ksjyjitqcopl","a5ePLDqk4Ed8yN/p1w68GRCjKFq/GOJapx8WCSUcvO4=",8332422278428065993,-1807862610156424449,986924752025931258,4858455658037793660>()) {
                                                      case 51991270:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else if (var12.level() != var1) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1a856c8luxts9","m1w4kaAjkvA7H4uGjuL7pfYT1/JdazIe04jlu/F09yA=",704067684563116119,6552741251339343264,-4455712358457563411,655297134157759026>()) {
                                                case -147999597:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3kvu7eztprx9g","HaRBcKdrpvv2z4B1D+6B798ODHHtaaKaaCfeCKjpcyM=",-8499803045034800488,-8213554483803737938,-3371325223789834972,7107572813517698828>()) {
                                                      case -944657184:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var13 = b(var12);
                                             if (var13 != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3uxn35excmw0n","dRjdGcdKdKc0VJ6nl+7Lq42BV+vpugnpXEMwJhksGH8=",-3109968128495951985,-4474797528681165734,-8704431715246739999,8820989001005695036>()) {
                                                   case -2046478471:
                                                      if (!this.ax.contains(var13)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s13xndumgx7vbd","0+GdlDsWJdWhvcwIN4wIm9A1f0T1J01jGf4uYMgZ4bQ=",4086652555293642862,-5909489023490148939,3047692143070537900,1163459292441352883>()) {
                                                            case -764810702:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3ghdepwas71wc","fvqNMMeKnX8ME9JQ3pnfI2XEZZ8xFrDvE99vdml1tpc=",1516650701430016250,8399297561170504077,2926157007496277455,3685286166782792709>()) {
                                                                  case -908230905:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         double var14 = var12.getX() - var5;
                                                         double var16 = var12.getZ() - var7;
                                                         if (var14 * var14 + var16 * var16 > var9) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3nek7bj9bopiu","WpUri7kJYwyKP/L182NpRiM7Ar8N3JcQLFpET/tljRY=",-4308252101499751620,-3916220569810375744,7208248359977539416,-8620518287143289453>()) {
                                                               case 992286346:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sjab0d4v99q77","+xHyiUGYBx8utStQSaz2yRbnTx+PaH9di8HMVbYAZmw=",-1812232985353979248,-2637569131296982333,-1415576146520979053,513486751383017835>()) {
                                                                     case -439238146:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            var10.add(var12);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s187ju0f2vz92u","7IfLK0wEHa6/4iRr0Uw8xqKncHWlst3xno6D6aMIDEQ=",4817602299346893622,6633631932737294800,-2695188240530276789,365090860879896427>()) {
                                                               case 1791721952:
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
                                             break;
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

               if (var10.size() > 512) {
                  label69:
                  switch ((int)com.yiyiaddon.m.b.a<"s1d5dqfjqjh2jf","FPMJeo86QRySRGPJep0ZH6eTmKovy5OL8nG/5cmswiA=",-5894084651978279498,-3581850778151936275,1762316179243380295,-3521542980274585981>()) {
                     case -1926082380:
                        var10.sort(Comparator.comparingDouble(var4x -> var4x.distanceToSqr(var5, var4x.getY(), var7)));
                        var10 = new ArrayList(var10.subList(0, 512));
                        switch ((int)com.yiyiaddon.m.b.a<"s3fgihedwmg7a9","hICUI8DSHk9FnH9SxzDAEU0ZnYUwXyqAnSw1Y8FT66M=",8814591856190409922,2804842646115413305,5775542502461731224,-617473018150444133>()) {
                           case -444349546:
                              break label69;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.cw = List.copyOf(var10);
               return;
            default:
               throw null;
         }
      }
   }

   private static String b(Entity var0) {
      Identifier var1 = BuiltInRegistries.ENTITY_TYPE.getKey(var0.getType());
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dil2xi62qtoe","h55B5zhlhQmCkMxMhp25Yod2F35fDP/YlhHmtWfnwAg=",-7795877707580206894,7154861304827752793,-2633257763145055286,-7427866913231957899>()) {
            case -1764445723:
               switch ((int)com.yiyiaddon.m.b.a<"s2u0vb2mvwpcxr","DZ6+GzTDFiSjcPGPfr/WIocfgNHodgBPcHhuChpM2JA=",-644893692148717327,-3964225286797363462,8141189004520259777,-5866216707333754501>()) {
                  case 154416108:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var1.toString();
         switch ((int)com.yiyiaddon.m.b.a<"s1hi4hp5hqmmc6","NJodCifbcTydq0OOUmbvw5QbU+UK2ZiE3ezkNMnv6V4=",1499087269493437964,-479820755672026650,-1627798466366663540,-2373499205151288632>()) {
            case 1518878279:
               return var10000;
            default:
               throw null;
         }
      }
   }
}
