package com.yiyiaddon.e.i.e;

import com.yiyiaddon.e.i.d.k;
import com.yiyiaddon.e.i.f.i;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class b implements i {
   private static final float aT = 1.0F;
   private static final int fG = 8;
   private static final int fH = 9;
   private static final int fI = 35;
   private static final int fJ = 0;
   private int fK = 0;
   private int fL = 0;
   private BlockPos l;
   private boolean bK;

   @Override
   public boolean a(k var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s331dulg8qwqux","1u+tdCi3Q2XF4QT1irxXBxpbMe8ulyJrsqSPVk7EscU=",6164985267616472748,6998598289297535446,-2504951460226345794,1499128195924229390>()) {
            case 787965756:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var3 = this.a(var1.e());
         BlockState var4 = var2.level.getBlockState(var3);
         if (var4.isAir()) {
            switch ((int)com.yiyiaddon.m.b.a<"s31f0h84jd3hlr","BShYuj7662J3YTVSd1UySR8FBQzSqLAQN+7TDjKTJAw=",4916539637320824834,-8798169059372505538,-5769146885173405160,7982563191624395185>()) {
               case -308746914:
                  return false;
               default:
                  throw null;
            }
         } else if (var4.is(Blocks.LECTERN)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3mbmrp77k49wi","GhXOI8Og9IrVEqJ/WnIOO079MivomVCVSuBikO4rJEA=",-7113348527887897201,9015460790525870025,-5371284808447288723,1924763194470165500>()) {
               case -1962217449:
                  return false;
               default:
                  throw null;
            }
         } else {
            return true;
         }
      }
   }

   private BlockPos a(Minecraft var1, k var2) {
      BlockPos var3 = this.a(var2.e());
      BlockState var4 = var1.level.getBlockState(var3);
      if (var4.isAir()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ndq95xs2cmj","9wBNOR/1Vmdj5dUtLe7FhOHG/dDCxBJGE4Bsl9xA8p0=",6249758727160507714,-7619642152213139661,1579530114714364064,2282072480411806716>()) {
            case 1613749657:
               return null;
            default:
               throw null;
         }
      } else if (var4.is(Blocks.LECTERN)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n8mpvui1mgfs","Eni0ZcDjpTsNdCejdDCMauM4PIcgt/V7Du8PtVSfZ0c=",5298249755018570193,-6684423426107353645,-2397853175142139520,3215322691177033057>()) {
            case -553558221:
               return null;
            default:
               throw null;
         }
      } else {
         return var3;
      }
   }

   @Override
   public com.yiyiaddon.e.i.f.a a(k var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ayv62rrzo2yp","GMLPjpV0+l7ARoZbtwUPS9+XMW9sRQAuDl3aAu4/B9k=",-5106270848524065275,-9162110827179185979,4435078074458700104,-9140731012554662306>()) {
            case -97518797:
               if (var2.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1b77vw059or2y","9ebLRyhk8WPb6aLD6T9ZGByuiSJxqn5/VRoyKtlbFIg=",2151157929040141273,3975400340996330498,703086639669911751,218397537409737265>()) {
                     case 513492381:
                        if (var2.gameMode != null) {
                           BlockPos var3 = this.a(var2, var1);
                           if (var3 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxl3shv6z8ylt","TlYRcTUmSLp5i8COd6DtbaD+2r1eIE/n5qDiGbD4nJc=",-2343977882316128131,2123048522879595002,3625222007698626893,-9140288356819172942>()) {
                                 case 1599222068:
                                    this.d(var2);
                                    return com.yiyiaddon.e.i.f.a.b();
                                 default:
                                    throw null;
                              }
                           }

                           BlockState var4;
                           label125: {
                              var4 = var2.level.getBlockState(var3);
                              if (this.l != null) {
                                 label111:
                                 switch ((int)com.yiyiaddon.m.b.a<"s16hbloll7hn4d","c77OagFkJeahOfzNuwCesp9XSAi19Tki4PINIc3LrLI=",4180839271049209008,-8178449491422455781,-2752312263541522066,-995894047040941923>()) {
                                    case -27877128:
                                       if (this.l.equals(var3)) {
                                          break label125;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s1s3439vntkj51","XEqJMOrSa6mb/3X8nfK+aR9QfaNkJ4XSba7dD0BH13s=",-1787591459643120359,-4968314938983007490,-6828080999550294977,-7936020533299394094>()) {
                                          case -1408719294:
                                             break label111;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              this.d(var2);
                              this.l = var3.immutable();
                              switch ((int)com.yiyiaddon.m.b.a<"s3e4qbhozjb08q","F3kqFf6hSLI8WJQ738XajgapD/Z+4aREwLZpbA8JwBM=",8884195776594276438,5835405696523539109,8321460445746386297,-7566619601742837833>()) {
                                 case -392796919:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.fK == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s20cpxtcteyfvx","G55Nu9DHKM30EaIZKx2H/0dlrZcRwgl7fRiHdRUNR44=",4406586446276685815,5341548072444719850,-8844716909699849510,-3187363963749412589>()) {
                                 case 543297546:
                                    int var7 = this.a(var2, var4);
                                    if (var7 >= 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3v63kdl38t9kx","4uRiEMWolP6fsXbOkVwJvOSZY+Q02BOCOesSqMxLo3I=",-197250489915143312,-7948741453224017999,8173290686470686603,-4866920283600870233>()) {
                                          case -1912674644:
                                             var2.player.getInventory().setSelectedSlot(var7);
                                             this.fK = 2;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2o65qjfjvmk8i","K7S2smyWcVFb0amKt008Ts5C5oVZ+xUOPBYvsPDSfJE=",-1658149344613889432,8867571424849597297,8062531046773737792,7779917377643737358>()) {
                                                case 1880617284:
                                                   return com.yiyiaddon.e.i.f.a.c();
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       int var6 = this.b(var2, var4);
                                       if (var6 >= 0) {
                                          label77:
                                          switch ((int)com.yiyiaddon.m.b.a<"s11r57m097cnqo","13XDAZzTbc2gKY8NW2MnvjvnTeXkrOv8i9t/5TeadkE=",1591503415227674916,986578604446226827,5541519211145449665,679529177132863415>()) {
                                             case -1005651192:
                                                this.a(var2, var6, 0);
                                                switch ((int)com.yiyiaddon.m.b.a<"sal1lfy3ysxm","BPqb8kVbIASsEFTUHA1WmoLehAQNAH9dkw7c5B7Vz8I=",4842135894904819022,5638130481690309887,8412073628809272379,-1156466783615864861>()) {
                                                   case 402508791:
                                                      break label77;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          var2.player.getInventory().setSelectedSlot(0);
                                          switch ((int)com.yiyiaddon.m.b.a<"s29fi9hc7dvgh0","vcs8sPKjq8OhrmrQa7d2+8dPUqJCtbLNZ2VcfufCJmw=",-6422175494055338040,-1824025056858347613,-1768520716150929488,2601773650224356342>()) {
                                             case -1483405086:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.fK = 1;
                                       this.fL = 0;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ne1y5ty03nc9","cOsctuQXSRe9H6VwXpmMlu/e8UPzaZcELMrjNVTagbo=",5194147282109452028,-6003999412049847865,-6869761287405318711,4223731252480369909>()) {
                                          case 1031239569:
                                             return com.yiyiaddon.e.i.f.a.c();
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.fK == 1) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3t9fecemwnlbu","d3AUqPHUSRQCb8+TbKC5m61gcafSzokLe7Lt2GFQtJw=",-295984550269366532,8754271400321387745,-3961390137581157329,3598323351377059466>()) {
                                 case 349531029:
                                    this.fL++;
                                    if (this.fL >= 2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s171atj368gzpn","lY0O20Gol2TaOJqVNFebMV92FIoPvnwwYLulcXMKl/c=",-4570648117872897385,8312123082063802361,-6137199180024969354,-2293458012729137868>()) {
                                          case 1231958649:
                                             var2.player.getInventory().setSelectedSlot(0);
                                             this.fK = 2;
                                             switch ((int)com.yiyiaddon.m.b.a<"s4kk2zceka0hn","HeIYSbdTS0cZF7gIzT9M0TgMxQi6lzfCbwMGz+FlvBg=",-1815178846766726588,6881244771717205740,-7109446268690780271,-8482738048603873908>()) {
                                                case -1683359119:
                                                   return com.yiyiaddon.e.i.f.a.c();
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return com.yiyiaddon.e.i.f.a.c();
                                 default:
                                    throw null;
                              }
                           }

                           if (!var2.level.getBlockState(var3).equals(var4)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2pobcavqd9cw2","CPPjD15FQIXHm1yBfcgvPT6Pf/QDuDUSzW8y02lv2lo=",6639525197328486093,-6964824734494013624,-3550269312028435123,-413015237667510073>()) {
                                 case -914299992:
                                    this.d(var2);
                                    return com.yiyiaddon.e.i.f.a.c();
                                 default:
                                    throw null;
                              }
                           }

                           Direction var5 = this.a(var1.e());
                           if (!this.bK) {
                              switch ((int)com.yiyiaddon.m.b.a<"slt5i4eu34vlj","5VRSzoknLNHXUBvc4KVCqxfBVIopTltCyNVYPPZnByE=",-4839721400599573212,-4056528232775891881,-6730881454642710074,-3313581347922265130>()) {
                                 case -355284980:
                                    var2.gameMode.startDestroyBlock(var3, var5);
                                    this.bK = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1sh99ytg3os7c","jPPKUGToXnbN7TUto3SrBhPRj3knJYjcpmFyvEekt+8=",3345904975157790963,-421197237088278198,6825416000559925623,3528686444647294527>()) {
                                       case 407481910:
                                          return com.yiyiaddon.e.i.f.a.c();
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              if (!var2.gameMode.continueDestroyBlock(var3, var5)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1p1jl187s88xp","a//swr3jR31bJr1PiQGZi722tIL3COe79mM9p33GBm4=",-4806661743018611891,-4102076983112934620,-5836153470853494874,-2925028740883445588>()) {
                                    case 870405656:
                                       var2.gameMode.startDestroyBlock(var3, var5);
                                       switch ((int)com.yiyiaddon.m.b.a<"sfto0jv23b23x","FV5nJj2IMuRel5Hn8Jy0duyuWgXF8tJCOqMdcrDEw+k=",-4859806790913238429,4108892455903921596,-3817656075858978690,-8382969214909527474>()) {
                                          case 981105472:
                                             return com.yiyiaddon.e.i.f.a.c();
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return com.yiyiaddon.e.i.f.a.c();
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s22vpx0515q7x6","KpP3rZhvB1vpAnMwlkQuqtdN16k2yFxyp7UhCZID+74=",-4199265399721092409,6648649148476575622,2970422601560686846,-4094791923017635148>()) {
                           case -423204629:
                              return com.yiyiaddon.e.i.f.a.a(
                                 (String)com.yiyiaddon.m.b.a<"sl6o4x7505e2d","FU8l9Doooj48elRQEpnarYcp3nVzXjWdp10l5QU92m8fM5MA5bI=",499851496172809482,6051941297929476593,-8664299904705587798,6104164390491449482>()
                              );
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

      return com.yiyiaddon.e.i.f.a.a(
         (String)com.yiyiaddon.m.b.a<"sl6o4x7505e2d","FU8l9Doooj48elRQEpnarYcp3nVzXjWdp10l5QU92m8fM5MA5bI=",499851496172809482,6051941297929476593,-8664299904705587798,6104164390491449482>()
      );
   }

   @Override
   public com.yiyiaddon.e.i.f.a b(k var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snb3bt56uz2ru","viRgu16Xrtn5bPXbglwdJdki6EDPuRo93hADyPqBLr4=",3008751831451052087,1076548972137170487,-6488298032712613935,6197693423010849213>()) {
            case 1677453163:
               if (var2.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sy7vjnfqkpk8f","D8mrggJxjLToyac88r00VQ0r8DTBLfJu3uv+oKh+ZqI=",2477900767070557624,-4509710930364194634,5827285987456164097,-1560052321008131721>()) {
                     case -243516884:
                        if (var2.gameMode != null) {
                           BlockPos var3 = this.a(var1.e());
                           if (var2.level.getBlockState(var3).is(Blocks.LECTERN)) {
                              switch ((int)com.yiyiaddon.m.b.a<"svrg1sm6y0tts","CgjqoEVe3TheJSFWJnxdFwj1hIUZv7ecj15s1Xw/Tx4=",8159614812515489707,3010018115664851212,6953719887876694188,8368501152024892240>()) {
                                 case 304384425:
                                    return com.yiyiaddon.e.i.f.a.b();
                                 default:
                                    throw null;
                              }
                           }

                           if (!var2.level.getBlockState(var3).isAir()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s391glulj5ibok","wANCpQfYdEotaDJQbSYJPwHaLn+LqS/fWo8DP5NsdyU=",-4363819185231838010,7380199879265097339,859756956177853540,-8644255133709259678>()) {
                                 case -1109053351:
                                    return com.yiyiaddon.e.i.f.a.b(a(var2.level.getBlockState(var3).getBlock()) + "");
                                 default:
                                    throw null;
                              }
                           }

                           if (!var2.level.getBlockState(var3.below()).is(Blocks.MAGMA_BLOCK)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2lmhh1f88zf2q","xCBehOX/E7jjEOfW5SD4j7gkeEfd1s5NjHn+3P1Gafs=",-2356237875038085159,-4847408815392834409,-2624844662468320954,2435295726732502793>()) {
                                 case -375796450:
                                    return com.yiyiaddon.e.i.f.a.b(a(var2.level.getBlockState(var3.below()).getBlock()) + "");
                                 default:
                                    throw null;
                              }
                           }

                           int var4 = this.a(var2, Items.LECTERN);
                           if (var4 < 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1pjc1lqt7kf5z","caYcFtsohPrGJ76y/tZkqGpkzYyGReAEaUVJbhXcrmM=",-8342027376136275702,-1469965720332644805,-8519375750086745866,5465639187049086505>()) {
                                 case 1291120036:
                                    int var5 = this.b(var2, Items.LECTERN);
                                    if (var5 < 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3pem4oux8amsw","ucugkUyV0rFy+drZziXkNtOZNvAD4aziXNHeCaaH9yg=",1523411337414346777,-7744437229862894091,1553421888345321421,1351521338658629453>()) {
                                          case -894755138:
                                             return com.yiyiaddon.e.i.f.a.b(
                                                (String)com.yiyiaddon.m.b.a<"s1pdzwpgjhykpd","F8Gr8TMoKrVhRQJ+SfQ548flqPHKSFAfvl1ie7/oRi5PfKKwkh0+ROjNG9I=",1301652645509373145,7773287435093377846,4057847691229214423,9074323130339371562>()
                                             );
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.a(var2, var5, 0);
                                    var4 = this.a(var2, Items.LECTERN);
                                    if (var4 < 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3nwce0dsosiw5","jltFrPLtWUS2AeBimb8hAW7Bc9VjbPjZKOpixy/As68=",4539048385453043415,-741445110964619201,-2043892970956005946,-3700390088169446518>()) {
                                          case -1404706801:
                                             return com.yiyiaddon.e.i.f.a.a(
                                                (String)com.yiyiaddon.m.b.a<"s31cf8vps79dko","7HvbfI/zzNrH5y9jxDfFajm6EiQqXGQxcEQ6NdeOJLNsxWkVuZPvwAX4aot1e3LOFyirRjBgniG3Ng==",8191217514185947293,1430865814437319158,-6500640112459339845,-6553058788333791107>()
                                             );
                                          default:
                                             throw null;
                                       }
                                    }
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           int var8 = var2.player.getInventory().getSelectedSlot();
                           this.a(var2, var4);
                           this.a(var2, var1.e());
                           BlockHitResult var6 = new BlockHitResult(Vec3.atBottomCenterOf(var3), Direction.UP, var3.below(), false);
                           InteractionResult var7 = var2.gameMode.useItemOn(var2.player, InteractionHand.MAIN_HAND, var6);
                           this.a(var2, var8);
                           if (var7 == InteractionResult.FAIL) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3b3c5677z1dxe","izbiQIfSdQj3t9cw9GnrrE5BnX2U+9PW6eJhXH7yGiQ=",-2310450737259781506,7148493724393488176,2585757017706536839,-7249898117292813077>()) {
                                 case 2022628870:
                                    com.yiyiaddon.e.i.f.a var10000 = com.yiyiaddon.e.i.f.a.a(
                                       (String)com.yiyiaddon.m.b.a<"se2sgxq3grm7x","b/M9GxFuuzAyJ2u06fFO2pRl3OLOreJOppqdTMXQFtL9Wbulpw6tkKDHvav371tH",8154185145368678101,-5235538026467478123,1670224179851407537,6174490011182350054>()
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"sz6541535bbse","iBSQPf5hRsxoE4k0yJKY8ogJnqJ1jk9tYZrE9RMxUK8=",-5165635742077907579,1235904115372025981,2255538741951400022,-4531334125566391634>()) {
                                       case -324885104:
                                          return var10000;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              com.yiyiaddon.e.i.f.a var9 = com.yiyiaddon.e.i.f.a.c();
                              switch ((int)com.yiyiaddon.m.b.a<"s2rjftjblkfonq","8VS84bEXS8zsjZqdBBcgR3mI0NStybGtVpf3OXKS06o=",-1884383142652959957,-561424526178197063,1862953356451218714,-8038670959924607623>()) {
                                 case 1745489183:
                                    return var9;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1jyvgmz17p9bs","JhGWJAjrVL6d5stS5c3XL5STngnT5jEcO8wT4icXxZc=",-6014915550312982111,-385541534168192909,5669520373406553449,-8651024131704959954>()) {
                           case -561266261:
                              return com.yiyiaddon.e.i.f.a.a(
                                 (String)com.yiyiaddon.m.b.a<"s36sd7s1arklr2","s4ciI1L1Sqtl0fOVsCdwBPHkdOE6Mt1zzgG9nEi4Jg1JRmZ2zmu/EaGqBExrsw==",4153267944883258349,144495740256063111,-4763471428802295149,5165958037037731374>()
                              );
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

      return com.yiyiaddon.e.i.f.a.a(
         (String)com.yiyiaddon.m.b.a<"s36sd7s1arklr2","s4ciI1L1Sqtl0fOVsCdwBPHkdOE6Mt1zzgG9nEi4Jg1JRmZ2zmu/EaGqBExrsw==",4153267944883258349,144495740256063111,-4763471428802295149,5165958037037731374>()
      );
   }

   @Override
   public com.yiyiaddon.e.i.f.a c(k var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jhije69abvdf","3Mi50avyvySCHCWU9SrUv6K9uH0jCD6rke4p1VpYf8k=",6548298765150871628,-8484784510354089385,477115808544653765,-3248067699527416747>()) {
            case -237963587:
               if (var2.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sytjdz7wnla5f","PIshVOuAaCmYI/pKHbqzLXEtPQ5qZNaGQgEo9OkHnSY=",2724096687754448769,-6948388258791943619,-2162633449646004531,2752509091044771062>()) {
                     case 1650884219:
                        if (var2.gameMode != null) {
                           BlockPos var3 = this.a(var1.e());
                           if (var2.level.getBlockState(var3).isAir()) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxyjulgy5l0mx","CjMXtUfBiYEZg9Z9o1J0iRF3iEoWnMxu75/dI27AlYM=",-6744546733979905679,4427992412306229509,6055822949563800652,7540985129218499614>()) {
                                 case 443408830:
                                    this.d(var2);
                                    return com.yiyiaddon.e.i.f.a.b();
                                 default:
                                    throw null;
                              }
                           }

                           if (!var2.level.getBlockState(var3).is(Blocks.LECTERN)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1i4ipo4ps7zvz","aDGmSf/gNLj9t4zy3k0uFCBHGkavqBvjqUEgP87rbZM=",2059429566282332050,-4337853527327748477,8546198335505547773,3942896871502510511>()) {
                                 case 1133180723:
                                    return com.yiyiaddon.e.i.f.a.b(
                                       (String)com.yiyiaddon.m.b.a<"s61dy2l1nxont","0E9jkfi+YcAYUsWSdos9sBj8SMLuS9l6eVDFO3YQrhWEtfJ9eqj5NGf/i/86YLqiCzRpXE5D8bha+vrvI4BvbzpT",8102481362461755539,7419720478506080357,8775699473876011459,5550369270823722275>()
                                    );
                                 default:
                                    throw null;
                              }
                           }

                           BlockState var4;
                           label123: {
                              var4 = var2.level.getBlockState(var3);
                              if (this.l != null) {
                                 label109:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1cdp24z62tw7m","lUMNUFSqhRHQr3XQxOVSJljq6b4QiS7Df5uVSQKeg0c=",-6487011392087010822,3089188326729427492,1569512031818449784,6049353062786912138>()) {
                                    case 243663212:
                                       if (this.l.equals(var3)) {
                                          break label123;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2yx61vcwu347s","YMnypFIXIXsBr1PPz7EbloY2C7kuEZKLYK9wc3uLvTw=",8628274951926552154,1937265954953029579,-711435292525054111,8629232038613850835>()) {
                                          case 228352763:
                                             break label109;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              this.d(var2);
                              this.l = var3.immutable();
                              switch ((int)com.yiyiaddon.m.b.a<"svg109sdkwe07","rHr5KShCqUGKlByosQfK2nwpi93nUrv9C8T2KNHwgs4=",-598485608216901846,-6437338847199083335,-5671436513517872261,5843407353531276478>()) {
                                 case -1343042013:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.fK == 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"sqm8e3guo40ei","/4IzRHJ1uE4n4gL3CZ/MQzXydYS9xqOWYriG5l00fHc=",3006544656152055027,5367902661936129810,-5676016475440219327,1657071334662347595>()) {
                                 case -248925728:
                                    int var7 = this.a(var2, var4);
                                    if (var7 >= 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3fp1ge6x5bidt","sMl96FdSm1+NULubdeNJ2/4lYCF8yNsroiil3BF9thk=",-7808037832963440259,8736150511072336802,8202539067482316785,279144859532857542>()) {
                                          case -149806911:
                                             var2.player.getInventory().setSelectedSlot(var7);
                                             this.fK = 2;
                                             switch ((int)com.yiyiaddon.m.b.a<"sgbnt3niwhhs","7MHZHESmPc0VVS3mm5n710yHhxQizFztNbeg6OZNP8Q=",-3746754754511642150,4290386832563079640,-1986638095453148211,1032481220709879413>()) {
                                                case -1080112482:
                                                   return com.yiyiaddon.e.i.f.a.c();
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       int var6 = this.b(var2, var4);
                                       if (var6 >= 0) {
                                          label78:
                                          switch ((int)com.yiyiaddon.m.b.a<"s29g4s4hcgmna1","quo1/akYg5TdfAzvUy4sa2JGEDTvvOA0m8f4ecOceE4=",3054391942740279212,-4503545269522584894,7471899267418543378,-2800708977882650110>()) {
                                             case -1979699649:
                                                this.a(var2, var6, 0);
                                                switch ((int)com.yiyiaddon.m.b.a<"sm1162xjfhvr6","zKYsa2RqoKDWHy3TpktKdIGgSRffzhv8nMioVNabKVw=",4874707306420550413,-3909385678924276724,-5751541973799555374,6931938103258113625>()) {
                                                   case 795756902:
                                                      break label78;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          var2.player.getInventory().setSelectedSlot(0);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3kw6z1039wgqa","Naf81N1I9Rjxs8bCTGCZDTOXabqnvBeA2ik878N4jVs=",7814241427104260197,950625026029525798,-3813485462235198596,-3094667924978311258>()) {
                                             case -285625923:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.fK = 1;
                                       this.fL = 0;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1nqvby32s6h1k","r/5k/Ikntv34fR2HiHaAmXCE4phFwbVHGj2Us2502no=",7271971529533448691,-3550852566519813500,3805475024584703662,5310645417692972748>()) {
                                          case -1560190556:
                                             return com.yiyiaddon.e.i.f.a.c();
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.fK == 1) {
                              switch ((int)com.yiyiaddon.m.b.a<"s41nwunj5reyp","pU74DEvMai1r74Qw5evuR7lvL7A9EJ+GB6dNTFIG1SU=",-7601687737203009127,-705385269950420395,-1598996754845947774,-8245231553524329253>()) {
                                 case 792317595:
                                    this.fL++;
                                    if (this.fL >= 2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"swzs1yqee22ce","6ueb5eaK98qjq8cD+wOTAY2g2Gdx4RjOVMl1bPZm8y0=",-1392465569427730378,-5256414739431900667,608486459958325820,1338470441207117784>()) {
                                          case -1065766997:
                                             var2.player.getInventory().setSelectedSlot(0);
                                             this.fK = 2;
                                             switch ((int)com.yiyiaddon.m.b.a<"shtx5q0udxcbj","Yo4gmKweA5lL7LW4nhyA1Z8fPNXZ6EEgJQzU0DM/LZ4=",-8972717012973535014,-8908643909857784311,8746281003147463376,352642227138818846>()) {
                                                case -1004694851:
                                                   return com.yiyiaddon.e.i.f.a.c();
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return com.yiyiaddon.e.i.f.a.c();
                                 default:
                                    throw null;
                              }
                           }

                           Direction var5 = this.a(var1.e());
                           if (!this.bK) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2hcnk7szjv4wl","9XJ5q/CB/mQTwXMZF4LV7u/FwGgDGDSdmYAXeSQQVUA=",-6859920505111194805,5271261622673374167,-1968042266379678533,1865459802969954362>()) {
                                 case 1920441941:
                                    var2.gameMode.startDestroyBlock(var3, var5);
                                    this.bK = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2jf6o40yxhcd9","kgryOjZXYqxgkO2DKA5Lqr1Bh3s5zAF5vTMz8GWA1o0=",-5888093201999218961,675514907533162913,-1054410488553706789,3960838754274866477>()) {
                                       case 866254577:
                                          return com.yiyiaddon.e.i.f.a.c();
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              if (!var2.gameMode.continueDestroyBlock(var3, var5)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"shzyon9r9uldz","en8Uxb36iGHMYeJ12B+l3w1ibdmDkt6+v+Ivxqe/Og0=",-8137136102643894336,-2851847165816794423,3990897231482661596,7107104381609370803>()) {
                                    case -1558205000:
                                       var2.gameMode.startDestroyBlock(var3, var5);
                                       switch ((int)com.yiyiaddon.m.b.a<"s328wos2p85pwa","6gnwhOZQWIudMzMCvgj1j8cJRbdXOXUhUjb/vPfcNkE=",3097033742793636107,7385296790979514128,4618409123727520184,-8221711413120709905>()) {
                                          case 206469656:
                                             return com.yiyiaddon.e.i.f.a.c();
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return com.yiyiaddon.e.i.f.a.c();
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s7dbnop6vgxoz","gmZtbGKCqxZ7hrfW4ze1OiNZL9/Wk5M5U+8iHpDWY38=",3469095417704336065,8661929269044023024,4661434076378116530,8994901978270337080>()) {
                           case 256303358:
                              return com.yiyiaddon.e.i.f.a.a(
                                 (String)com.yiyiaddon.m.b.a<"s36sd7s1arklr2","s4ciI1L1Sqtl0fOVsCdwBPHkdOE6Mt1zzgG9nEi4Jg1JRmZ2zmu/EaGqBExrsw==",4153267944883258349,144495740256063111,-4763471428802295149,5165958037037731374>()
                              );
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

      return com.yiyiaddon.e.i.f.a.a(
         (String)com.yiyiaddon.m.b.a<"s36sd7s1arklr2","s4ciI1L1Sqtl0fOVsCdwBPHkdOE6Mt1zzgG9nEi4Jg1JRmZ2zmu/EaGqBExrsw==",4153267944883258349,144495740256063111,-4763471428802295149,5165958037037731374>()
      );
   }

   @Override
   public boolean b(k var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aavibrylyf9p","T0IqB1xdteVCfRngbgRb0iPo74fFSrTQ+g+qkaZff84=",1691230913684038614,7762017683879145139,840525245047658779,-2084056556897341794>()) {
            case 302090033:
               return false;
            default:
               throw null;
         }
      } else {
         return var2.level.getBlockState(this.a(var1.e())).is(Blocks.LECTERN);
      }
   }

   @Override
   public boolean c(k var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s239ev8ph5dasc","DpJ0tZeHc39Q4LvpkVmkzKW51pKi6m4RodmlzNPNxu8=",-7820473853266374646,-768078496853488989,-262718601860998279,1241759431144420047>()) {
            case 342321628:
               if (var2.level.getBlockState(this.a(var1.e())).isAir()) {
                  switch ((int)com.yiyiaddon.m.b.a<"szqihal74n9v0","FfuMpaO183vCtZkEZjmNsgoh+EJc1yoVIVLZEftbsSY=",-3074687781178101912,-319194997960351171,-6977781988791397671,-8531814924877489468>()) {
                     case -1305558810:
                        switch ((int)com.yiyiaddon.m.b.a<"s2181ip23jyxgh","7x3JSZyqotfNmnW3dHYFW4xoVdLOhOLSX/xPBKtXkdE=",-3363386855966234290,-2079156342546547366,1066186140703173761,3034907862969632616>()) {
                           case 1274530516:
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

      switch ((int)com.yiyiaddon.m.b.a<"s37cijbnbrc0uw","IdMEhDPFm7LwxNnyd95nL3IUZAmSN4svab3OE6z5pFc=",-7572954800365337138,5755780539913805862,-7437503519775763835,5799582658448008073>()) {
         case 1156160501:
            return false;
         default:
            throw null;
      }
   }

   private int a(Minecraft var1, Item var2) {
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j756b9afso4a","+PRH9VtHtAauLWKbh3on1o6g8QUE/7TQjiIxS/TP+6E=",4069504774332694021,-8998779729920274245,-7216607814350094879,3566801482273240251>()) {
            case 1524617840:
               return -1;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"slkpz19n0ye1","s90qtCZ4usnbughBx9aJ2NjaU3GfN3XLbX+4H/FHp6k=",6122650445826620763,5550891791561539637,-1474121705482787130,-2907871503082308972>()) {
            case -764891885:
               while (var3 <= 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1udxggzqh2md1","HtnktFLilnTAqwVfMMEzB6Hd2uEQTQTewAbsG5HV328=",7504393551800354330,4899687899884017679,-764236537758603587,-7352762753483492262>()) {
                     case 1624801694:
                        if (var1.player.getInventory().getItem(var3).is(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1liz39k21ou4g","hBVNo/Mh8Jq3kCNl5CpjpAhIB0R3AfFNTbB9ihZwOj4=",-4980136848607345954,-3878302830757906528,275935296896932858,424394252610630571>()) {
                              case 1180651983:
                                 return var3;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s22vazaa7sh41o","qwqYtXcr85cb0vFRoWvJ6g6LVceye6n5wH2XJPjGuXA=",7290898185719434460,2666532823811703237,-592607457900163317,1395334717394058765>()) {
                           case 18990110:
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

   private int b(Minecraft var1, Item var2) {
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s169yagox1gvvs","jiE/Av37nAWikd2r1elJSHhiUMAmSY31bLfEPr4M/Gk=",7014225575470309341,-8752317192818916024,6018802000732836307,7511633773224501853>()) {
            case 426394589:
               return -1;
            default:
               throw null;
         }
      } else {
         int var3 = 9;
         switch ((int)com.yiyiaddon.m.b.a<"suh3heuv1v05q","rmGxe0yo6Yu9VdoTp4s9DApzklfToC6OvMvEAENFx9k=",-2170726713636307756,8246911304793186992,-194134056889956361,6075455958235151913>()) {
            case 541970303:
               while (var3 <= 35) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3keore3vgrr7t","3WKsoP6vVVvlEK6SEO/Qdiw4yuhAFSd0pBs3zw58jHM=",2364322095702697921,-2356797149349478369,5461024489783142043,4719970787103985062>()) {
                     case -1567425715:
                        if (var1.player.getInventory().getItem(var3).is(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"swzbqbjlxnjr3","CU5YVN+4MVHOwQg1AYm0tNhebj1g5h3FqJptV7o5Kjk=",-8920754423757320062,6830011275923092167,7768213557438307378,8913955232859607868>()) {
                              case 658092490:
                                 return var3;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1etqjb3euhgo2","aMrEqOcHfgsC8vwLP6Hlydx+CG+Nzwq9WsGSt95xaxI=",4789543598214787042,-6494835617970967180,-5756596085471326517,4910375196503823743>()) {
                           case 1444004071:
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

   private void a(Minecraft var1, int var2, int var3) {
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bpmlrcrewnxp","OyqmiAj/iaIYlRzyNPAxPzjWme6Lbiujd2XQWeQ/lb4=",7048326761171764851,-1427823334467597296,-4223111957926733757,-3733928105596253087>()) {
            case -278071766:
               if (var1.gameMode != null) {
                  var1.gameMode.handleContainerInput(var1.player.inventoryMenu.containerId, var2, var3, ContainerInput.SWAP, var1.player);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s30qu9d5is2hbu","uaFPgvWHAkwq+k3oE+kATQ3CdryoBWucfsI8ulsmgNg=",-6063411644188000376,-8107097375573992724,8348918894929874788,-2138000729082278011>()) {
                     case 2114244106:
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

   private void a(Minecraft var1, int var2) {
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s231dwj1hsjxjj","PtG+wExYU7Q2nPPpWm4roQl6w3iQkM0YsVzjK03ArY0=",-5156590139330032172,1341488313151180282,2107835763636855095,7453992802570419780>()) {
            case -2074964982:
               if (var2 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"sufw3hlji7262","m7YBDEdqp0VwsKO3gGjO3+ZWDXNsskOrMV6FX7kt4ho=",-269995978814371720,714217669571947350,5342840104408060427,-1733213965186392699>()) {
                     case 100620211:
                        if (var2 <= 8) {
                           var1.player.getInventory().setSelectedSlot(var2);
                           if (var1.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2r19zhpvt2fgk","ci/DCQAzukB+hRPJPTyABCQWfQ/lx6bMrMilK32pHd4=",-3423193394720843090,7790553492874824449,7174737308314136837,-2121234948793085127>()) {
                                 case 967320994:
                                    var1.getConnection().send(new ServerboundSetCarriedItemPacket(var2));
                                    switch ((int)com.yiyiaddon.m.b.a<"ssx201pmjc7at","7jvM7aqL8RsYo3QVbMCgmcNnwvNULQBmh3O1DkTnZiA=",5652294380429472606,-2391345177549658295,5491218436320177936,5131409655851500657>()) {
                                       case -868426940:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s2zlohcb5s54wa","WT9RRJhHqoza1TsLVJqrvAdlnuNjinkoaD7AEeGdnMk=",-6389716949139522195,4483708266155687587,4818813459537726071,2308252697985803557>()) {
                           case -1954894724:
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

   private int a(Minecraft var1, BlockState var2) {
      int var3 = -1;
      float var4 = 1.0F;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s22weamzqgwv8a","z9m6NOkMtIu5XN9vC6uYHO071icYxCESos+05N4KV/4=",8009940447972397138,-4346712389850086195,-7758384052868460410,1271643737740746095>()) {
         case -1067442025:
            while (var5 <= 8) {
               switch ((int)com.yiyiaddon.m.b.a<"sm5zf40s4yuw2","Wc2yJOpyrVsktC2qD/KLo76+kEfMcY/D51hL8CTZxoE=",2428841932900295219,-2033011799571898883,1879204498231571026,-7818739312000865036>()) {
                  case -879258332:
                     float var6 = var1.player.getInventory().getItem(var5).getDestroySpeed(var2);
                     if (var6 > var4) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s2408ehyta952z","gWBEKUzsmCp9Xip4gHqZJwEFOMIqHihujfhmRiltsTw=",3297458670624396639,-601986263203345189,7640527389674249956,-613522331982953998>()) {
                           case -1124207974:
                              var4 = var6;
                              var3 = var5;
                              switch ((int)com.yiyiaddon.m.b.a<"s25iapuiez8fo2","5MVfbglf4TAv8clKimlMq2k+Khk93/s0NYpQzbBzUQE=",419614876628118579,6718823364572163553,8208456626826126691,8825301232961301028>()) {
                                 case -589236500:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1lrhv27rj4k05","dthgPbDjU+JG6o9u1HkyWjIgvcDbgdpAMW7yPt0kfF4=",6747299133533781743,8447477104869298759,7265817981511406398,-1128841912306629730>()) {
                        case 78681729:
                           continue;
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
   }

   private int b(Minecraft var1, BlockState var2) {
      int var3 = -1;
      float var4 = 1.0F;
      int var5 = 9;
      switch ((int)com.yiyiaddon.m.b.a<"siqhshnbb3v39","LbSd1lWlBZa871lJq3f8NtFV4XAh12PH4jdYi/syB/A=",-5382875094384772535,-5639537099488499242,6836909119106470293,-2216087306420655147>()) {
         case 1658808750:
            while (var5 <= 35) {
               switch ((int)com.yiyiaddon.m.b.a<"s1igbdnd3zg25j","ztzyJHj1vK9n4H3/lWwnX/Qd0miIK910BflJndHOt5U=",-556410066162306312,-4371061870480632449,2880197740421558336,5637719159546159656>()) {
                  case 1808605170:
                     float var6 = var1.player.getInventory().getItem(var5).getDestroySpeed(var2);
                     if (var6 > var4) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s2748k1hzure8p","vKfoCTRUFTq0Dn9a2oz1VTuxk2YpYL06b5oeNAo5GrQ=",861570809288706314,854169493719386226,8372890648383853012,-8463157059365072427>()) {
                           case 1865235020:
                              var4 = var6;
                              var3 = var5;
                              switch ((int)com.yiyiaddon.m.b.a<"s2ozmyy1bbt1ac","w1ZQIOIsF66dA74OMR2EfkzDF+mBfXamxfXlggz4ZDA=",7570228056566259805,-7417411853122049548,-3847814409264190580,3174743267924510599>()) {
                                 case 2114300999:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2a04ot4inw9mf","7UyUzfwyT77nWi4QbXc0DyvBaBmIkmiM6rNgUE8IwTQ=",3661210401127582093,1350280109375105006,-2623756785403689379,-6187460983496252184>()) {
                        case 1912376646:
                           continue;
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
   }

   private void a(Minecraft var1, com.yiyiaddon.e.i.d.c var2) {
      float var10000;
      Direction var3 = this.a(var2);
      label37:
      switch (var3) {
         case SOUTH:
            var10000 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s1i2na3ag77bp6","hcDmZNEL6/0twSr7skozEM8xADSnbpmhN9cH12ks8gs=",2998405565602421798,2939169556285750340,-439534421468690065,-3005576741142931316>()) {
               case -942862951:
                  break label37;
               default:
                  throw null;
            }
         case WEST:
            var10000 = 90.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s2jkc9b3nu25ww","11kTEwAHbEwc5Cq/LcDdd1jKg6h9OaShIehJxoboJBk=",678041482328307683,-5734333183439277741,1979304752055752303,-2600152432051301055>()) {
               case 117325990:
                  break label37;
               default:
                  throw null;
            }
         case NORTH:
            var10000 = 180.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s3iy32mlnohlg0","gzdVSCHZgh8cffLi61pGaXPG9Xb9q1PQ+52DQy1Tjxk=",-5761447638834511544,-8356497866420973763,-4769857699717936668,-8173516050977288048>()) {
               case -252015277:
                  break label37;
               default:
                  throw null;
            }
         case EAST:
            var10000 = -90.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s67ct54n97yon","lXIAT1/nC0TGPTJ0dyDoOC6PyS2MVADr5R91tkOt+Yw=",2484899848805413134,-8478213017187487303,-7960996633542568655,7392204963427623043>()) {
               case -886736716:
                  break label37;
               default:
                  throw null;
            }
         default:
            var10000 = var1.player.getYRot();
            switch ((int)com.yiyiaddon.m.b.a<"s2pwl2rjv0r8v","7Q72Xz+SdeuiJC7oBRwyEr7Nig4BawVRgLiOH/W8zrA=",4523910150603975960,-5869935884540769666,1183881606528765772,-4336432106740848598>()) {
               case -843874403:
                  break;
               default:
                  throw null;
            }
      }

      float var4 = var10000;
      if (var1.getConnection() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sq4wgfio27tt8","ZRuJdxHfs2Fm9RU+0G2z4R6nnfLxgDbm6XCPAL/K40c=",3154013202865155165,-3245027920581297401,8000664589483179864,-1042827174293303581>()) {
            case -1485993011:
               var1.getConnection().send(new Rot(var4, var1.player.getXRot(), var1.player.onGround(), var1.player.horizontalCollision));
               switch ((int)com.yiyiaddon.m.b.a<"s1aehvzb8xejux","mYp8BpWSYnwBoJYD5Tg+3IDvQXnpxP0pWbrBz78hi/c=",-5407765143653422757,-2398646855828219672,-315295598051525662,5591351825649562900>()) {
                  case 739281141:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private Direction a(com.yiyiaddon.e.i.d.c var1) {
      switch (var1) {
         case NORTH:
            Direction var4 = Direction.NORTH;
            switch ((int)com.yiyiaddon.m.b.a<"s2up3b3l9xj8gf","ayEvFWlAmcunrX25mAiMdwmR8i02CS23HlN0I3gJlno=",-4441428536800820635,3601029946261194049,5899837406793845241,-1914178722417258559>()) {
               case 157795015:
                  return var4;
               default:
                  throw null;
            }
         case SOUTH:
            Direction var3 = Direction.SOUTH;
            switch ((int)com.yiyiaddon.m.b.a<"sdqfmsvrl9ucc","VGpYK4DRriQt7wvGjO1Qp67DyfSgOZirBFQaE7UO6fk=",-60878357823047533,2734142145701375631,7599383023569207760,5077438693441354261>()) {
               case 1903695573:
                  return var3;
               default:
                  throw null;
            }
         case EAST:
            Direction var2 = Direction.EAST;
            switch ((int)com.yiyiaddon.m.b.a<"saadpla3dzhtc","iEW4NOJsxurW8TaCDpWd7nMwwOwgCyRcuxMP0NTVVJY=",4695386396942007082,8075950968705829412,1002718241978104411,-3668946354491516459>()) {
               case -151441351:
                  return var2;
               default:
                  throw null;
            }
         case WEST:
            Direction var10000 = Direction.WEST;
            switch ((int)com.yiyiaddon.m.b.a<"snr1330vcztod","6+VjTFTQ4lNzPy3qkMvav23nBhi2QzUAmZx6G3gT/Xg=",8759474708941714353,896853077557519410,1697579648311112625,3105559757237985885>()) {
               case 1896263987:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private BlockPos a(com.yiyiaddon.e.i.d.a var1) {
      return new BlockPos(var1.aj(), var1.ak(), var1.al());
   }

   private static String a(Block var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19vwb4nj1rtij","kEYmpUcavhun22lnPHCMiaCn0VEjm3/c/jZxXTQHvB0=",8560484114757858205,4481700957260423534,-5742407674264800322,-7196000330613704725>()) {
            case 196343847:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s356g15oq9ayri","191yfAySeo125EmnQYe7v78smdpdmxdoVcY7khgXWh3Rhou/",2975032692333263154,5677034478480160248,-3917822481065420853,-1029985949475266201>();
               switch ((int)com.yiyiaddon.m.b.a<"shq5cpv8edi8s","8JulJANZUtrhgQdMxSLjCtx6Q3ix3XZKgs06acL0SGw=",-7396960860378864665,-122487540336851260,-6234736189585814344,205576223971794169>()) {
                  case 1315341364:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.getName().getString();
         switch ((int)com.yiyiaddon.m.b.a<"s15e4yor762wvm","UI6VVSrjEcSZuGd+g/pkkwkeZ+1BhGbMJ7FB40yiiIo=",6603539945606552222,-9155517803281395385,2021345429821963339,-2907107257186106884>()) {
            case 810976012:
               return var1;
            default:
               throw null;
         }
      }
   }

   private void d(Minecraft var1) {
      if (var1.gameMode != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kxqsbmrih2rt","5RDD0H1Pv8Itjxv6DWhH53av3HjUqpQYOzBw5QC9MAQ=",-1361831470046944862,941167424803580809,8317847416566005886,8525178543074700385>()) {
            case -1389959459:
               if (this.bK) {
                  label16:
                  switch ((int)com.yiyiaddon.m.b.a<"s3fl7e75qzg32w","BQbrQ8uuVmamkGkif7/uyZ+//YFze5eqfWqzw18dxjc=",-8296891716278131082,-3524227557318341766,-841016827108370336,-6415813753307577420>()) {
                     case 1064260675:
                        var1.gameMode.stopDestroyBlock();
                        switch ((int)com.yiyiaddon.m.b.a<"s23vljd5i2xnzq","T83iQX4ld9r7y6zl3CXlkkJJQJXhAY5zgufcySFTsCk=",-1164604363679714376,5064259668888577396,4164999709366303030,-1954778953870010723>()) {
                           case -2079230512:
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

      this.fK = 0;
      this.fL = 0;
      this.l = null;
      this.bK = false;
   }
}
