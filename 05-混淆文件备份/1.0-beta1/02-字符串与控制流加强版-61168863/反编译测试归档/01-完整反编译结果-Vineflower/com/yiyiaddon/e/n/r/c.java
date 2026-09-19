package com.yiyiaddon.e.n.r;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

final class c {
   private final b e;

   c(b var1) {
      this.e = var1;
   }

   boolean b(ItemEntity var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1k8kmstuc3s5b","wRPImk2IpK5hS7d4y77AIKdz8/KCmv6Vvi1Ycs4dJGk=",5946268900162723287,9135990085542110513,-4092827390235950746,1051350732985575488>()) {
            case 2112523162:
               if (var2.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bjuiv6wfzrnk","f52yexlW3A39t/MLMs01Fi/X3IvFkUFBBNXF3OT/na0=",-7815767137103800995,6155156158736505248,5272690060325891654,-6289297437666128726>()) {
                     case -1415097177:
                        if (var2.player.distanceToSqr(var1) <= 1.0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3lhehdy06wehy","CsbAMaAipmLeM6fdTh+SRDlY2ROnZV+2zA40DwfiFCg=",-4246157464443490374,-877209922941009448,8455262641222823117,-6616595188898842263>()) {
                              case 348212927:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1clga7du766zu","C8zHcsXpYQqi/vw6xDdiieY05YkMDVtvP+djmHt+PAg=",8239489052364954594,4093957239967104544,2407476027773236566,-7158157017394263383>()) {
                                    case 1191198350:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2j4ismxwokem6","Pfsh5Xy+nxtDqJiGTkzW2VZ3mgKNV1bUhWyG7dcrF6w=",-2100104408982047268,541993984514663190,-3183856067952515779,-5700891147641666683>()) {
         case 515434741:
            return false;
         default:
            throw null;
      }
   }

   void w(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      String var3 = "" + var1.getX() + var1.getY() + var1.getZ();
      if (!this.e.ae.add(var3)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2x71txcv3wzy6","HAeNlerEe6nPG/xoDxK4Nvio6I3PgEv+uowLqEX1anU=",8629555532115663359,4512691983284596405,-5589530222312072390,-8993626076615884737>()) {
            case 2136692635:
               return;
            default:
               throw null;
         }
      } else {
         ItemEntity var4 = this.a();
         String var10000;
         if (var4 == null) {
            label51:
            switch ((int)com.yiyiaddon.m.b.a<"s29s8ecnmfetj1","ZX/rcp5fZckaio7LOM+T5Bqgk7n7aCX1kTgQSJrWeAw=",-1046421536611597710,4740619180377299276,7417849867494191530,-6153393386911505946>()) {
               case -1782807560:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s3tl4hy9yd2rvz","YZKVMi0LVFKIy4560EfieYvxzMk44/35qpAyOYgsPX26cw==",5490848586448176037,-534229663554007843,3701697614946040796,-8331214636451934061>();
                  switch ((int)com.yiyiaddon.m.b.a<"sb15e9ghcozyx","5gJirotD92J/LE1NxOyt/SkIyrWcrk5q+ej9m1PzIps=",-7604406192963783237,8688418483099872447,4076414768616863587,-2583207800888282063>()) {
                     case 1986627743:
                        break label51;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = String.format(
               (String)com.yiyiaddon.m.b.a<"s7rec3g8ocm4v","WKI9RUXLXX0VJL7hBpdopHVnZBN5O/piHmRGT4CUKJ6pURn8ZIBlxgk+1cwyBzcCzJX6+jnCsIs9bvumaEtzxg==",-8062306309242526952,-7625861130529739882,7355090929274484145,4700658252637484507>(),
               var4.getX(),
               var4.getY(),
               var4.getZ()
            );
            switch ((int)com.yiyiaddon.m.b.a<"s36ueb15lt4oqh","5TX47sF1rq+aneIIRgWGwiVvR1+h56lJxnDTfDT+iwE=",4077280887473780822,-4431519315155240218,7589936234090872210,-2330170942933677155>()) {
               case 1258468206:
                  break;
               default:
                  throw null;
            }
         }

         String var5 = var10000;
         if (var2.player == null) {
            label45:
            switch ((int)com.yiyiaddon.m.b.a<"s1epnu95gd3804","hZMiIwonpRMQzyQoi7lua4YhZv/MY16escapb3h/juE=",-7310674451745243215,-6212997133235487470,-5272368646375334127,6372961472314184623>()) {
               case 2073570513:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s1y0b3d3g6celt","1qqlCVuImlfFxz3S5eLtBlscdlVLeh2aSwLAGdtOgQeG+A==",-3596379659104099380,1404408121791224330,3658873989144427363,5449073434157192909>();
                  switch ((int)com.yiyiaddon.m.b.a<"sdsi17qetgb1d","ZzIzD3L3R7ENcF2GCT0fhqIncWt6K73ol3bGMOnW9YM=",5867601221760416915,83248705253989844,-877703870528353363,-4554151883910306890>()) {
                     case -1196838096:
                        break label45;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = String.format(
               (String)com.yiyiaddon.m.b.a<"s7rec3g8ocm4v","WKI9RUXLXX0VJL7hBpdopHVnZBN5O/piHmRGT4CUKJ6pURn8ZIBlxgk+1cwyBzcCzJX6+jnCsIs9bvumaEtzxg==",-8062306309242526952,-7625861130529739882,7355090929274484145,4700658252637484507>(),
               var2.player.getX(),
               var2.player.getY(),
               var2.player.getZ()
            );
            switch ((int)com.yiyiaddon.m.b.a<"s3mfyles2074mv","Bw+DaRyu8xUBOYXR5NzR8ed24Rd0AAoF+mp3s6sPCPQ=",-8656951829002533162,7313487770316807238,3571752650512234422,-3725549859302514980>()) {
               case -1770759915:
                  break;
               default:
                  throw null;
            }
         }

         String var6;
         label61: {
            var6 = var10000;
            if (var4 != null) {
               label41:
               switch ((int)com.yiyiaddon.m.b.a<"suteso5sp8kn9","JxMqSLP6HgTXfzOZHv0f1VguANtQYcXFCsux+FSnp7Y=",-8930613946913647059,-363606212562806694,-5984757239966269400,-2040538639328872400>()) {
                  case -2117901535:
                     if (var2.player != null) {
                        var10000 = String.format(
                           (String)com.yiyiaddon.m.b.a<"s2i8p9kw6qos8x","N1AggZRfFDStqtGA185mpMLDjhpsA2zNrGx5H6+DFig/uKvVewOqELLjXzs=",-2102843101271635183,-4624085750039433753,5463185922334359065,-3857716922617148016>(),
                           Math.sqrt(var2.player.distanceToSqr(var4))
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"shhihdvu5fayj","AYKdO8u9MaJkJRv6jQMN7Peqzc6UG/aPfj9IuXUHazs=",-4050386769251984166,-8813878664747232706,-1388905984791996946,3340911319316040743>()) {
                           case 1184409096:
                              break label61;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"seqe8og71uitj","PGo2TdHc/Fz9s++fglC88cyobUptj0kiur7YVdwe/ig=",1755531424464126651,3327751776999585477,8930629945691747631,-7773469524908969603>()) {
                        case 213973328:
                           break label41;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = (String)com.yiyiaddon.m.b.a<"s12teuznbz4m1e","3X/5t37L9L74YE1zGIVJcT0l7ZKpL6a7443ijf/X88A=",-8037402516630044278,-2672835032687263674,1431905991049347334,3169813541313780399>();
            switch ((int)com.yiyiaddon.m.b.a<"s1t2zvysoryx5g","DDKAZ00txv5c95/2I4OIMiy53WFy+LQSExzcIewm9gg=",2630672913756700927,6520052793022561249,-4746461934585541822,7913825710278452771>()) {
               case 2140554299:
                  break;
               default:
                  throw null;
            }
         }

         String var7 = var10000;
         this.e
            .c
            .d(
               var3 + "",
               (String)com.yiyiaddon.m.b.a<"s3es3aegkj71t3","exS3BiaQmThr4KCbCpBQsMB6ewLu46yazkoVxY9ZD4SJ09gAt1/6iu8t",6347809787790416778,-5324022897550515483,7281363515628779125,2926032020316645015>(),
               var5 + var6 + var7
            );
      }
   }

   boolean c(ItemEntity var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         label41:
         switch ((int)com.yiyiaddon.m.b.a<"s290r1b909k12h","MtabwNUKU5WmRppQdykZNpIe6q79MKRU+u+fVf8+ZJA=",-1931424974494127193,2814573127540073582,7265178670525424137,3035283467822583598>()) {
            case -539882150:
               if (var1 != null) {
                  if (this.e.nR++ >= 20) {
                     switch ((int)com.yiyiaddon.m.b.a<"s7km3kr9ak2ko","IjTyDY8LThmCIyfpaIaSM/tN7BgJ4TCI9cevjuT5BIE=",6948685633348335920,-4869117407617947116,8462094106845289488,3289376602532544704>()) {
                        case 1577325097:
                           this.hi();
                           return false;
                        default:
                           throw null;
                     }
                  }

                  double var3 = var1.getX() - var2.player.getX();
                  double var5 = var1.getZ() - var2.player.getZ();
                  if (var3 * var3 + var5 * var5 < 1.0E-4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3axu1rx1hw2rh","KUxlNzmtBZnl6Xsj4dM6iEygslAVngH0S3tF8yS7bs4=",3210918110989583221,1078598638486916970,8199524877982791374,-2154831727611885794>()) {
                        case 1287830554:
                           this.hi();
                           return false;
                        default:
                           throw null;
                     }
                  }

                  float var7 = (float)(Math.toDegrees(Math.atan2(var5, var3)) - 90.0);
                  var2.player.setYRot(var7);
                  var2.player.setYHeadRot(var7);
                  var2.options.keyUp.setDown(true);
                  this.e.dY = true;
                  if (var1.getY() - var2.player.getY() > 0.5) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2dkv89npd2mtr","PXzze8zt+Pev6ITcWGsWoJPhXp4lhDLeeO8ncD60SQc=",-5249507698990360969,-3321855381809721040,-6516834761688921336,-98587579186777470>()) {
                        case -1889948569:
                           var2.options.keyJump.setDown(true);
                           this.e.dZ = true;
                           switch ((int)com.yiyiaddon.m.b.a<"sgs9gmr8ac88p","kEr+WaaEcFCpk0teFvjOpp9SodjeR82xEHZ5NrJkR6E=",-2448683183206191961,8001261648357460314,1912948971079227833,-7285171010122839846>()) {
                              case -556586280:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return true;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s20b1kukp9sj7b","LR0vmIV/L5qYH7bXeo2Y+oJf7BNvNaeoBlqKSXL16ms=",7091635615428607263,-7099535163540915967,1440436516967190276,2080019224625571749>()) {
                  case -612463094:
                     break label41;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.hi();
      return false;
   }

   void hi() {
      this.e.nR = 0;
      Minecraft var1 = Minecraft.getInstance();
      if (this.e.dY) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s2fh8b5yk2ubqe","ef6Sm+8M+arXmAlDyIWDXz196axWW3B8oNF+4mL+w48=",-2169805023172177492,-856667516991152463,-8743549060871700678,-4065362910079207442>()) {
            case 994531962:
               var1.options.keyUp.setDown(false);
               this.e.dY = false;
               switch ((int)com.yiyiaddon.m.b.a<"s19hh6kljg2rbq","f41zBU7TVvtYYU29IQOqIgIU6UIKPxs2XRpj/wqe220=",6884839071810885580,2257241963996080585,7518974667907213423,-6850459579476797421>()) {
                  case 1914151138:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.e.dZ) {
         switch ((int)com.yiyiaddon.m.b.a<"s27aim8utnsbia","lq2fMHtjED8nwDhvaZyOQfNFR7U1jVnMBmRWdnGZcUU=",4266145504983136586,2040095772253502537,4360956811097310527,-2297951789791771764>()) {
            case -1569528613:
               var1.options.keyJump.setDown(false);
               this.e.dZ = false;
               switch ((int)com.yiyiaddon.m.b.a<"s3c4kuithkw5rr","HIr910x0eCF742MeLiyd/LEw+vEvxGVDyIXvLPAgqAM=",5714649862567716005,7010307798511685709,8555309922409234095,4258331201871925000>()) {
                  case 1687063709:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   boolean dU() {
      if (this.a() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sfc5wsa2o7x84","I8ct59rL8arqFQnPfD/Q2K16e1TNJPv2IlSFU1HFcbo=",6243546198942695101,-2491975133365794386,-4843027065001598679,8256737199337249755>()) {
            case -796797412:
               switch ((int)com.yiyiaddon.m.b.a<"s3rwemi0k7x945","AisAE9zWtlaETN3I6w9F1g5g6RSnHOCLsnAuQVV3uhw=",668779434147832106,-6338874460518804022,-2514379659654300137,-2492986467154193956>()) {
                  case -2032535189:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1smqqbfay7nz5","vzR5WZDKn8DPDG5gB6sABHlt2O4ukRBSFVS1V0xV5aA=",-2772904221516379897,-4567331229201179444,-8438786437093284020,4523807162293334420>()) {
            case -1126533185:
               return false;
            default:
               throw null;
         }
      }
   }

   BlockPos v() {
      ItemEntity var1 = this.a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sprxef938pnpw","O8CBEPoXg4HLQT45Fhu0tFTpXHoVdwGROK2sa76A1t8=",7884811818886251952,-7760701474954810675,-5385566920456624206,-8450287735108741439>()) {
            case -1804176495:
               switch ((int)com.yiyiaddon.m.b.a<"s3kfibs5xhgfu5","nQG3k46VnLEGADekFfxP3Sql4o9vnbGKQqQf9VU+u1I=",8032659817955794502,-1522734228571003435,343185041238709739,1540470043215951897>()) {
                  case -794164375:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         BlockPos var10000 = var1.blockPosition();
         switch ((int)com.yiyiaddon.m.b.a<"s1f542lm8hnxla","FzRniExXP2JF+zPZDm3DAIZE2f4cbPxanxaIhFdi4Ak=",2344601397790356825,-6179929219368806813,-1701191809097635356,3516087941596710021>()) {
            case 1656674743:
               return var10000;
            default:
               throw null;
         }
      }
   }

   ItemEntity a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qj8w7uqsekcm","XlBqwfmWWggDJyLMxcXWti9wnafW+zLaj7mKpBBIxeI=",-5655557439633392762,3707062010348122707,-690879673259775107,45957007210746350>()) {
            case 2092892524:
               if (var1.level != null) {
                  BlockPos var2 = this.e.a.A();
                  BlockPos var3 = this.e.a.B();
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2z4f2vqpr6sfa","2riUqadaf6kyAX6jOCHSPUfxCr1/wDHiM4hsdXMkppY=",-2685818420811584001,-9059260931695908601,-7043413167848037708,-879565154101942176>()) {
                        case 250867709:
                           if (var3 != null) {
                              double var4 = var1.player.getX();
                              double var6 = var1.player.getY();
                              double var8 = var1.player.getZ();
                              ItemEntity var10 = null;
                              double var11 = Double.MAX_VALUE;
                              Iterator var13 = var1.level.entitiesForRendering().iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s1ynrj1c6gpw74","QOiLoZfz5IuXpzdiSf/J8sXm9i/SD5sXIfDGIjYF7JA=",9095830927082209693,4203590090583174557,5730904398467006048,-2272177473945089743>()) {
                                 case -1277493005:
                                    while (var13.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1cyga278vcjsj","ajZ7adUBsAuX64deq5wM7kqY9z/vjdfR/kFO7a0aiac=",-8938043695737444488,7136628406443389675,-4060770633538951631,-8172664850565378521>()) {
                                          case -1304203709:
                                             Entity var14 = (Entity)var13.next();
                                             if (var14 instanceof ItemEntity) {
                                                label70:
                                                switch ((int)com.yiyiaddon.m.b.a<"s121w0aqqpa4fo","3lQTdt0tS4NjUbspC4FYcfBJDdXoertqhc8x1jZjqnQ=",8948239698329248874,-1555465046213566824,-3359907562680350174,-2007041520845147207>()) {
                                                   case 559991135:
                                                      ItemEntity var15 = (ItemEntity)var14;
                                                      if (!this.B(var15.getItem())) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2x3b4smf60yis","9ECKfoSyFbldmxGlHDluZdIkaifvykCb48pA09IAWHI=",-7667854728916315299,5555151105688867455,-6774683703728518241,-5465044240649542230>()) {
                                                            case 1804731398:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3qh0os770m06v","qreNTpNS/7qW0HMTpeuh/nFDb+iJ+m1I8gu3rf9vZDQ=",4565876818416999994,-1837327550275631744,3625953616830619512,3357652027374202808>()) {
                                                                  case -121270168:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      BlockPos var16 = var15.blockPosition();
                                                      if (this.e.a.a(h.COLLECT, var16)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2vc02ruak3vw3","sVU9ZDZjRrZ0661yH528zJwRSN96kBeBiXj1Tog5k1I=",9125401305087636162,-53200979551590963,3913610317922022381,-5119710178018850608>()) {
                                                            case 29047909:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2hnztlmd61vt0","DffwKrZvwE59sikDLq+FHjT8MuBzVPzblW+2kwXt4Vk=",-1292795448705486516,5310959339756228743,7448705699035744225,7194086811410632716>()) {
                                                                  case -2053737655:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (var16.getX() < var2.getX() - 1) {
                                                         continue;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s7cn6v69d8v3d","O910VWmjNUa49+bsjVTbKkQphK7H5JSGiMqql4pHE/U=",2513990883923394932,-1889844167414929657,-3505760920516633501,-7980458261065629768>()) {
                                                         case 931066582:
                                                            if (var16.getX() > var3.getX() + 1) {
                                                               continue;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"sqf0gay4hc4nj","5OAGAFwmbMtOWeNqVDkLoJMs+LDkVQfmb4sJvel0OXI=",-4079020648714436283,-5847305364858560647,6706672089040736984,6246276873550434753>()) {
                                                               case -1820012036:
                                                                  if (var16.getY() < var2.getY() - 2) {
                                                                     continue;
                                                                  }

                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2k6rni3uw95f6","3Q3vCDquQ/Uc2zESjp922Avy7/NGCfJe6e97IGnG1zk=",-906654112672690873,6696170091142533318,-5877959746673382448,-435342336282207323>()) {
                                                                     case 203619334:
                                                                        if (var16.getY() > var3.getY() + 4) {
                                                                           continue;
                                                                        }

                                                                        switch ((int)com.yiyiaddon.m.b.a<"s118lmqsf8ht8h","8vNVOf46dDjttsNa04jYOb/os1tBroyWHTFge4X2S2c=",-4672872412744145761,-7475607038028146697,-6755196095461502027,-7690783140954511995>()) {
                                                                           case 867317925:
                                                                              if (var16.getZ() < var2.getZ() - 1) {
                                                                                 continue;
                                                                              }

                                                                              switch ((int)com.yiyiaddon.m.b.a<"sx2wgj6aglrys","yh0t+ZW6FDgHDmYZG77OXDj6G0KoM8cJ2o8aujtU3Oo=",-8781374918511749887,5506662126838122568,-9136228634657050113,-5118605850621126583>()) {
                                                                                 case 15974147:
                                                                                    if (var16.getZ() > var3.getZ() + 1) {
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s100d6hbcl5p14","4mcU7qUeZNA4JcMpEUvpG57tR18FLelQLTjktx0XWvE=",3537230605044419036,-2137934485417147748,8430994661795461813,4471239159574381972>()) {
                                                                                          case 2020661731:
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s3m26wzl5tpqz6","x4yZCknltFgrrbaAk593AkJQ+JuFx6l6E9phRJVExhQ=",-9011569268848591876,8497967600165922585,-5688990352316802090,-2548948694987482065>()) {
                                                                                                case 1836392090:
                                                                                                   continue;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    }

                                                                                    double var17 = var15.distanceToSqr(var4, var6, var8);
                                                                                    if (var17 < var11) {
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s2kapd2tuqy39h","xKA14r7n53pEvKETzAch/yDaZ/Vot6NlZthRTiWdyws=",-7872272795591710976,6186096106884904061,-8651969404065513622,1229828842259177753>()) {
                                                                                          case -396077151:
                                                                                             var11 = var17;
                                                                                             var10 = var15;
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s3k96wgruz6ipc","Ijq+Nmn6A4E8KXgo6tKDCSB8jXA2c95ao1gxJmmbZU0=",7406032059406338081,-4114147330430310093,4674774641322335355,-162944304338654264>()) {
                                                                                                case 1675226289:
                                                                                                   break label70;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    }
                                                                                    break label70;
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

                                             switch ((int)com.yiyiaddon.m.b.a<"s22dmms4uxkvtz","4mBI5Cw2MnzyfY8a9EfSoBhf+bMSoTGqFiLu5PMaC2c=",-4342474225006382641,5555438657562403505,-5252550237074396441,2558679084222179446>()) {
                                                case -92284720:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return var10;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3eqms7g5rvje1","kg3JRn6sLuxbM0DwQ/5G0PUMpIFC1ZIUjpluC5C/4wM=",-865287289079341878,8953370635006087794,-2897757518318032217,2035574386063880705>()) {
                              case 534853510:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"swvh2wjdeei1w","VZCK6LhZtw3naHvWM4gJi7d2fuC+4SMsBMzh3LIYFtQ=",-3448628722664429446,8066441119487762799,-5066522537519602750,-130668226065289477>()) {
                     case 1596488895:
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

   private boolean B(ItemStack var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3va0a59j92nnf","Tb1/nWyeLMEHh46MwuDnA23jYx2Hwg04zOj7ef2bj5A=",-1188657962734267682,4337668209021945203,-3043814084240755555,2203290754646966956>()) {
            case -197931806:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2u3yop5suyno1","9/NoZ5y4EZPX/omy265p6O4aQoYK1S121IVQ2HHwAPo=",-4398125645473094712,6596073385558663579,5253218894651052527,-3289532799626494417>()) {
                     case 219249530:
                        if (this.e.a != null) {
                           Iterator var2 = this.e.by.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s29xosjh2qqqfw","xpy19Gii/SZUKvJnNOMdXCURIjUb24ELM5ftdaC1XBU=",8342180393763843199,-8374291098992285842,-4525401050238631089,-7274867663134534839>()) {
                              case -449625223:
                                 while (var2.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"se333yqesxca1","UVDDZ84RHzZ1o0f4iKaCG6Ue/m9hMhitOMQrX2OWpnE=",-7033286254717882847,4512648725537765834,7585451654783644180,4919153049088688452>()) {
                                       case -1130513046:
                                          String var3 = (String)var2.next();
                                          com.yiyiaddon.e.n.i.a var4 = this.e.a.a(var3);
                                          if (var4 == null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3bwzy7esxppif","VkNasZDRk08NxMTtA1QcTO03nWiQF8fsDJy+7AZQ2wg=",4078354077385148224,6553775726951859643,-8706348203505749,1971341161175701565>()) {
                                                case -728165930:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2pc0r6yj9xjf4","RAVJCcwDDttnLK2ASrn2rHksG+H4Q9lDZHiV2wz64yc=",-1338381237576109170,5287319717049405019,-8391910148378784482,1734575474859443995>()) {
                                                      case 966327775:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             if (this.e.a.a(var1, var4)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s16gt7h0uwff2p","10QfS7vI5ozfYLazTSyZCtNemYsEKkh2DFySgbVdNlM=",-2765056035606556030,3484390888434147622,-4724423611537924876,-7071678225980551212>()) {
                                                   case -1811690542:
                                                      return true;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.e.a.b(var1, var4)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"stl3soq2bsyaf","ogc5S9C2SzxH+ch9keJrXErUXuShtxsMflKmjEIqRF0=",-2181435918945132617,4404309326529399081,864113684504755121,4559364748164747127>()) {
                                                   case 692825654:
                                                      return true;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"sqtausdvmx1j0","atCeFlVUFjhmzdPtPB1uh9sAnqQ/6TwfhkskC3eWXWY=",7315954570689189176,-3014907196675768773,-7035640435639698732,-22965409447504445>()) {
                                                case -1915064470:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
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

                        switch ((int)com.yiyiaddon.m.b.a<"szaah5wbe7htp","WiMb+4e9gWJIkSo8kRDIbXuYzuMv9/Y1cCAqRX5/wCo=",8855660098256919224,-2506691677149313926,-3778165112147746800,-7321166392943590213>()) {
                           case -427443005:
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
}
