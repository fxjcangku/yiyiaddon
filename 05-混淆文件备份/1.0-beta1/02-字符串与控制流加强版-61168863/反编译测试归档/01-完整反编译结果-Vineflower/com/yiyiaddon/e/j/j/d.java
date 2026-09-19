package com.yiyiaddon.e.j.j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

public final class d {
   public static final int jP = 15;
   private static final int jQ = 7;
   private static final int jR = 1;
   private static final int jS = 9;
   private final Minecraft P = Minecraft.getInstance();
   private final Map<BlockPos, Boolean> N = new HashMap<>();
   private int jT;
   private int jU = Integer.MIN_VALUE;
   private int jV = Integer.MIN_VALUE;

   public d.a a() {
      ClientLevel var1 = this.P.level;
      LocalPlayer var2 = this.P.player;
      if (var1 != null) {
         label38:
         switch ((int)com.yiyiaddon.m.b.a<"s1g6f5bmaalgwg","jn2PD4fRLxqj8+JbhBpqaIe64Z+aVQy/C0F9IdZxJNs=",-3211054489767122928,-4958805868155641694,-1694797599217505418,-2084521763535077312>()) {
            case -1510516803:
               if (var2 != null) {
                  this.a(var1, var2);
                  if (this.a(var1, var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s34tfgtrg2xsfo","LPQrGyugKc0fn3DIuRMeP1CF+JR0ivVwVTHkAfCos5Y=",-3865330259762746668,6105730077086299042,4282591129989041902,4580423872700646517>()) {
                        case 698237453:
                           return d.a.SUMMONING;
                        default:
                           throw null;
                     }
                  }

                  if (this.b(var1, var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2lvpkxlp5xzqx","3uwA63pXlXWTikD1Y0dfEOv0G1UMDceoH9CQw2OWWMo=",6017670778680256151,-5590548589738719040,5818246340723566309,-3836638155670981890>()) {
                        case 1466969266:
                           d.a var10000 = d.a.PRESENT;
                           switch ((int)com.yiyiaddon.m.b.a<"s197wui4bv8lpe","fldiSMWbh4j5uX2+TbwrFWHWoM1mKuqmm2pWBLj8pFk=",2278279191547804097,-5871686876821666704,-478374609265573526,1738024240256990381>()) {
                              case -510892832:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     d.a var3 = d.a.NONE;
                     switch ((int)com.yiyiaddon.m.b.a<"sd5jvuv2nvl31","gw7xpl07KV3sFUJsnEru0hZVa5HPxf4F5obwC2gXrDU=",8203822643279576674,7067455358243686812,-7462929563420872966,-5472963872636390268>()) {
                        case 1343185987:
                           return var3;
                        default:
                           throw null;
                     }
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3n5bdf3822trz","cuTXa5Oz1TTg94zCQXCoEKpglG8b8t/h18Ee71I6gr8=",-322983434850678813,6063087245475807388,9080623166057427436,7282083957710103496>()) {
                  case 242093925:
                     break label38;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.f();
      return d.a.NONE;
   }

   public void f() {
      this.N.clear();
      this.jT = 0;
      this.jU = Integer.MIN_VALUE;
      this.jV = Integer.MIN_VALUE;
   }

   private void a(ClientLevel var1, LocalPlayer var2) {
      BlockPos var3;
      int var4;
      int var5;
      label80: {
         var3 = var2.blockPosition();
         var4 = SectionPos.blockToSectionCoord(var3.getX());
         var5 = SectionPos.blockToSectionCoord(var3.getZ());
         if (var4 == this.jU) {
            label73:
            switch ((int)com.yiyiaddon.m.b.a<"s345kp11jiraej","aCauQ3e4SIIFawUaHrhKKiisjCKmIbyOgz6E6Ef2DPQ=",7077000616563327772,3073976261755192301,-8833014254192674819,3974982608379155766>()) {
               case 937079066:
                  if (var5 == this.jV) {
                     break label80;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2i2hlrl605gjw","PfdLWCIcbQcjqW480R2T7xpqG9rI/5O8e3JKwvRW19Y=",5564624850507582145,-1384048875671990161,-8719194857256805458,-2738722569107445789>()) {
                     case 1903334508:
                        break label73;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.jU = var4;
         this.jV = var5;
         this.jT = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2iwezna8p1392","bj4Z99P7R3v5Z495FHvRtDIoSY2hQoQMv3/VsPb1Sc8=",8016463450469947703,6931680517733504230,-372003743303601609,4872417977677545886>()) {
            case 698299390:
               break;
            default:
               throw null;
         }
      }

      byte var6 = 3;
      if (this.jT >= 9) {
         label66:
         switch ((int)com.yiyiaddon.m.b.a<"s1o4jjv897l0on","vxNsPyCQSmfiHtWDZp3oYX0osReLP3+cMdmC2nP+EA8=",1515156243345385290,-2046783817621774850,-2481033477026386728,5041015736657918381>()) {
            case 823811731:
               this.jT = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s2r6oj8cnbatsf","uDQNpAzryvuiQ/Ix32FQzuU/GJysESF/ybLrVPz1nfs=",-5061796075346443346,2169030764486350061,54130251338458011,-7714470618143805756>()) {
                  case -1790214058:
                     break label66;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      int var7 = var4 - 1 + this.jT % var6;
      int var8 = var5 - 1 + this.jT / var6;
      this.jT++;
      ClientChunkCache var9 = var1.getChunkSource();
      LevelChunk var10 = var9.getChunk(var7, var8, ChunkStatus.FULL, false);
      if (var10 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s88xwzm1qt6as","yyOWrLjCpr6iRa79OFnsElk1de1jE2A2jf0QkuRglsc=",5713891821580926274,-6793198664791541250,-6661313857595795295,-702270017619844080>()) {
            case 939549460:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var11 = var10.getBlockEntities().values().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3cqjh9lhkh4g4","cn4xjsf0wH1bTJ92vWXe+SQ/k/UqC3dbuzfCJEH5xWA=",7315534272360909584,-6475099981605016371,5582203696616013076,2264313211048715623>()) {
            case -555187902:
               while (var11.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1humbpdtz632y","fx7DESHHuGiVP0Oo+gOmdBc4O9ZZQKFwNggn9ITr8VA=",7520573461280073793,7971840007886906093,-8155690764184096272,6129066601335109907>()) {
                     case 2133625444:
                        BlockEntity var12 = (BlockEntity)var11.next();
                        if (!(var12 instanceof SculkShriekerBlockEntity)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s24gal2lkv346o","ZRs5fII2Wgpk07+Vq34fAwnM0gIY0YCGDqdcNbzk8eU=",7963558558701751991,388948809677914928,2360970684922193119,5692815869272841475>()) {
                              case -1920294666:
                                 switch ((int)com.yiyiaddon.m.b.a<"s72hsg75wvrj5","/t3WnFcadWe4dzgj5l7Nwtm+W5zSiD/2IAJ26sCkJYA=",-4472393917130248240,-9154348315106882189,8816211122874398122,-2659138625971582407>()) {
                                    case 1362493446:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           BlockPos var13 = var12.getBlockPos();
                           if (!c(var13, var3)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s34wcyzalnxt3","nwLhGtOTzxJJtSsNyHcBHy/uCSEjTk58Pa8M5M5mbOE=",-5975671502847443970,5428318486321020581,3677238533568757991,8250124761341193386>()) {
                                 case -1180570553:
                                    switch ((int)com.yiyiaddon.m.b.a<"s10ttk95wsy80g","LG1Jg5z0XMYxr8+4m2YbF5bRCUdH5JxenWEqOhmGHWY=",-5833015287758186766,950387793949398895,3287946580525899786,-8997591414251507569>()) {
                                       case -1360162955:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.N.putIfAbsent(var13, Boolean.FALSE);
                              switch ((int)com.yiyiaddon.m.b.a<"s2yt69pykvlxfb","5G7v+F4Y91wKN/QLDne/lQpf0Gfg2eA4k3d75YF25r8=",8511122075254457140,8470441683184177856,-6475105616815823768,-7512252653143284564>()) {
                                 case 542094853:
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

               return;
            default:
               throw null;
         }
      }
   }

   private boolean a(ClientLevel var1, LocalPlayer var2) {
      BlockPos var3 = var2.blockPosition();
      boolean var4 = false;
      Iterator var5 = this.N.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s21igrzci9tlim","I0JrTEXIqOw/lrZfDWMdWeiMjm672mh72cIp7LMVDtM=",-8838338104386926007,8279530802953018861,-5934741076319650692,-3754601268037613114>()) {
         case 273767588:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"szh42q6i6bk9m","ZBc6xnX30foFR18LfEPUnWiEMZQWYamD3s56jG44Jng=",-2743559325150526918,5414305305511708636,-3929587350133113950,-7757626341125077572>()) {
                  case -1830116444:
                     Entry var6 = (Entry)var5.next();
                     BlockPos var7 = (BlockPos)var6.getKey();
                     if (!c(var7, var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2q3da3z8m9wh2","D/pHEs+GVSuOdLZtj0kEBQqiBgV8Jr0hwhyibt++yrI=",-4849933746163253537,981727099654084703,7588899270312409910,2537922379292413602>()) {
                           case 706138334:
                              var5.remove();
                              switch ((int)com.yiyiaddon.m.b.a<"s3992kmjg2v1bj","dNs9+oOs5DfIMEXLRZhUXbPhN5eucj+BIIxSWCoYJv8=",-4852994629740531159,-8341646559985242896,1239856446030664572,1119572318244685816>()) {
                                 case -621707251:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        BlockState var8 = var1.getBlockState(var7);
                        if (var8.getBlock() != Blocks.SCULK_SHRIEKER) {
                           switch ((int)com.yiyiaddon.m.b.a<"szwgzkxpsvde1","C7RYL8DaAL7dfFZZx95J+iATGyzjNQfMSXEHB1ycETA=",8716264755172564153,8628146593122418023,9137471351922025492,5260528996306474112>()) {
                              case 2128047191:
                                 var5.remove();
                                 switch ((int)com.yiyiaddon.m.b.a<"s11px1g333vhwu","Wiw5u2bmW3GWZ794zb4+y8Fus+7rAHVxez1MIkcNFQw=",-6089493778325430588,-7353065959787016222,6288585542683203910,-8717310721085917474>()) {
                                    case -371026649:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (!c(var8)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2bsjoamw1jar5","dJrvogg2XNhGuiqgG8KhEYeAfBYbq8IdzRtXszeAoV0=",-7474561735613848708,-5456980122322810420,3374167429689472873,-8421956623746792798>()) {
                              case -617808854:
                                 var6.setValue(Boolean.FALSE);
                                 switch ((int)com.yiyiaddon.m.b.a<"s10hhle6t3rxbh","tvP7T0GPU9a7lkKkTAheLaFHIm6BY5sP49jsUAzoDQY=",-6085156310626280616,5125257823872226349,-4163740205513050605,-8182657730546673943>()) {
                                    case -652243601:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (!(Boolean)var6.getValue()) {
                              label38:
                              switch ((int)com.yiyiaddon.m.b.a<"s4ef55p0gg7jt","c69sCVQjLKnpEFCKgWdEsjjYCrs0KAM+2Rd/9lpzRqY=",2578935855629876269,9192123689470239696,5538272440919757494,4232766856886336363>()) {
                                 case 92941865:
                                    var6.setValue(Boolean.TRUE);
                                    var4 = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3w38v8linomnr","f71vRbkXiKJQSnDE23kj5pm/i1APdMxw3nlar6ldcXQ=",-1171672555160483248,8195621432574245836,4882973834751396614,-7314278622480696315>()) {
                                       case 1662292361:
                                          break label38;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2pzk8r2t2ls1t","2pCsmhxsSUFpT4lt8oj0ypbVzurwg7oy6JFhTSq0RBs=",-1525104840453625513,4294594016460142392,-5843546287184705039,3034939541887396782>()) {
                              case -1233670684:
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

            return var4;
         default:
            throw null;
      }
   }

   private boolean b(ClientLevel var1, LocalPlayer var2) {
      List var3 = var1.getEntitiesOfClass(
         Warden.class,
         var2.getBoundingBox().inflate(7.0),
         var0 -> {
            if (var0.isAlive()) {
               switch ((int)com.yiyiaddon.m.b.a<"s265ewvzvbhsaa","K5m4HKYD1uq+svjw7P+ePUv/47XgBPI37e5uHcT1750=",4283372001762756637,5763537769633286465,-1890487871593900382,3106212920666123749>()) {
                  case -1708427801:
                     if (!var0.isRemoved()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3dpx4618so0ar","xg4E4y31ULbm9Zd7TE10Gay27V6zUWZMVICrjf29XMM=",5332879274364986765,6850309961094775465,3932409518563455391,-5942833595149319295>()) {
                           case 947796244:
                              switch ((int)com.yiyiaddon.m.b.a<"s1f64b9vkqug8b","Zwqu7ttEQL+2V1e0ssk192FKCjgIlQz6cbF48Xz18AQ=",-8564696653364115425,-5586479357902777631,-6845970209431270702,-366751574652354283>()) {
                                 case 162205012:
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

            switch ((int)com.yiyiaddon.m.b.a<"s2mgwuj4sdklbw","6Hl3OjALm5HQD+84NNA7q+hAPJHN3jt4Pus4Ol4mbzY=",8322645547787764384,1653779128927118748,-5590275183232702080,2962572175272915423>()) {
               case 523654531:
                  return false;
               default:
                  throw null;
            }
         }
      );
      if (!var3.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s32d7exbcl2es4","BUsepTxIoDJAEWz8g2OUHgtyMXA8v9B8H+y8BD7792M=",5310325580410327702,-6407109463484121940,-8180071956319197500,-3503884121627005704>()) {
            case -1794116708:
               switch ((int)com.yiyiaddon.m.b.a<"s1t5j5wlzm0y4s","AMI0PPVlvOULx35aYRFUDS0jWpL6oI6abw5OVkbVeZQ=",-8391781820925640648,6168955071173107265,-1878058552359787578,4295821825832404309>()) {
                  case 1217831570:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"se6pl3ve5s62h","uJG8VH3IEu4lA3Z88AUZ0Bim6UoFSdanPIaSpH1FehQ=",-3936557785269574505,2545384647431658561,-3091146578865958265,6167287648779632366>()) {
            case -1612083798:
               return false;
            default:
               throw null;
         }
      }
   }

   private static boolean c(BlockState var0) {
      if (!var0.getValue(SculkShriekerBlock.CAN_SUMMON)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lsozlk9x68gj","XPaKO0iRHKVJqVgtSimy55Z10yjSOa98KPDyk8MFqzc=",-3445860076348076347,-6595927837306847567,-8567356079632513247,6048345643991712190>()) {
            case -294505966:
               return false;
            default:
               throw null;
         }
      } else {
         return var0.getValue(SculkShriekerBlock.SHRIEKING);
      }
   }

   private static boolean c(BlockPos var0, BlockPos var1) {
      if (Math.abs(var0.getX() - var1.getX()) <= 7) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vzdgxz6bxdwt","vJ/cRFE2EPe0lBiHZckz3S5xBKvbWPjSmrBC++8JkDQ=",4451723976566579177,7595070065980482722,3597060650098582819,8288340319495103934>()) {
            case -386508335:
               if (Math.abs(var0.getY() - var1.getY()) <= 7) {
                  switch ((int)com.yiyiaddon.m.b.a<"s20fk40evicnks","Eai8xd62secWyfB/KeJZ7XJbcbjFnnJCBwOS0dmxohQ=",-7923193278544841840,5419898467263264808,-2318851097865323433,-1492397931871805942>()) {
                     case -818517450:
                        if (Math.abs(var0.getZ() - var1.getZ()) <= 7) {
                           switch ((int)com.yiyiaddon.m.b.a<"si4posbyp3hhp","AJXS4kqEGA5rWWgwbUJiQ45ruVSsTo46nA/vAeuiSzk=",3824724042127760228,1876274657547354395,1555119386270675017,5005787630825781238>()) {
                              case -1270403133:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2nhovjlwd3sv5","PQuoxgauAeMuBJLRG+j4Rn30bXIKjnd1779ne18zlfw=",-1038943003706793621,-2458601227314517447,166571297572278218,3473018233519870273>()) {
                                    case -1650726369:
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

      switch ((int)com.yiyiaddon.m.b.a<"s14p6tsgcw097y","vZledgk4xOmxRCMSi3GbI8VyICzjwj1WbY35/OXw3lU=",-848914708149702943,-3490642049590485335,-979468665180840722,5624313173773178469>()) {
         case -889355907:
            return false;
         default:
            throw null;
      }
   }

   public enum a {
      NONE,
      SUMMONING,
      PRESENT;
   }
}
