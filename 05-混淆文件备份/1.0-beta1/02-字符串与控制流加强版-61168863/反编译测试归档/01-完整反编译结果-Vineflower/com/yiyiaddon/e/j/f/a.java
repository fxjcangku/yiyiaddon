package com.yiyiaddon.e.j.f;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class a {
   private a() {
   }

   public static boolean a(Minecraft var0, LocalPlayer var1, List<String> var2, BlockPos var3) {
      if (var0.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kst2z6dd2172","Jx8UVCrOwgJF7q9xhsQF7zIRPZlPQXDEqtCUG38XvXA=",-2155355835150350606,-1102226078380772365,4699091903379651434,-4950654725014602788>()) {
            case -1520720271:
               if (var0.gameMode != null) {
                  BlockPos var4 = null;
                  Direction var5 = null;
                  Direction[] var6 = Direction.values();
                  int var7 = var6.length;
                  int var8 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"sdlcfip7jih5d","A87CIb56RB8QVqJNqR5ArrZzqm6pJXGApodnOfg/8x4=",-3099820146828411922,-7001353197080091660,1728129394005126522,-2363228487856523496>()) {
                     case -1607124994:
                        label140:
                        while (var8 < var7) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2br9u0qzzihts","HEEcObpWxjbDSc1hJpXUXWGr1cmnIDFo+bgd++z/GgA=",3373907540651961623,-549621550611652031,-8539182409318308387,-5630771964245584699>()) {
                              case -792796173:
                                 Direction var9 = var6[var8];
                                 BlockPos var10 = var3.relative(var9);
                                 if (!var0.level.getBlockState(var10).isFaceSturdy(var0.level, var10, var9.getOpposite())) {
                                    label114:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3c7xux4528kt1","sncf/luKq0ZjzqoE0uBXoaLjRtd7RiDOnERopZ8UZX8=",9028801579446985482,5412296476421828948,-2965323468363799193,-6922255405744399854>()) {
                                       case -1041378090:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1cd3hjia8lf92","uCo4IEF3Y/gCCce7VthuLye3sx0/SkDVR+73TDdbVxo=",2250785136272807019,-4282388919711895560,-7361839368829985783,-4518571330387466463>()) {
                                             case -1456801455:
                                                break label114;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    Direction var11 = var9.getOpposite();
                                    if (a(var0, var1, var10, var11)) {
                                       var4 = var10;
                                       var5 = var11;
                                       switch ((int)com.yiyiaddon.m.b.a<"s28ut0eytu5egr","MCeUmQKNZS3k8aOExZrjSperPwFErOx2BhJBeJvTXGQ=",3927210878835453058,3673783447968441877,-2494877835125014990,-2452134652848798320>()) {
                                          case 2062621025:
                                             break label140;
                                          default:
                                             throw null;
                                       }
                                    }

                                    label111:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1s7sphraxzp7x","DY9wZPyVPi6g8FDKqiiI8Aq4vTxjtJm21GtnUr4uybA=",4705251782260834355,1435364540062770193,-6098690937391293167,4536860545974789721>()) {
                                       case 1016045549:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3rv69jk5tjbqq","neQMa22seuN26LPjE2JOM/H29cmdMBe1lSX4Fo85nQk=",-3189057635155372704,183802091703029072,-2549043577907751493,5293935590128211404>()) {
                                             case -2131657036:
                                                break label111;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var8++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s12lf1h18m644t","kf+LeiU3yo/I3eMqSi3hQE8XHLLO/mi/zDG4+xmRLU8=",5242336439753604125,3216788537813530722,-8499117597819369289,3721487646032800397>()) {
                                    case 35983992:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var4 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2g01545dxa7bz","SUrD2hVxr/F9tSJp8vISNlZhkcdsENsG6ydFLQ2YVtw=",-74534137090157384,-259018415482096858,-3143134877160621482,1905603699331754022>()) {
                              case 935855894:
                                 return false;
                              default:
                                 throw null;
                           }
                        } else {
                           InteractionHand var12 = null;
                           var7 = -1;
                           var8 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s2879vd82bjkrt","HO+hbPOOU0Qxy7v0U+1E+sM1UKWcNlnbJ7Dl0IvOn9E=",-3252313838419964573,620110074292882102,5552222014274619073,200836459764733164>()) {
                              case -1645106422:
                                 label129:
                                 while (var8 < 9) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1gvidbqig35be","VJEAT/eNNfMYK0y+rpAqRqzWA6NnkKkRbLnbnccDgy0=",1084349944380521172,-1720820245957914752,-7277194762187795036,1879733344055977998>()) {
                                       case 212384845:
                                          if (b(var1.getInventory().getItem(var8), var2)) {
                                             var12 = InteractionHand.MAIN_HAND;
                                             if (var1.getInventory().getSelectedSlot() != var8) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1yqptig4aqra0","E+8MHm3hECMSQ1H3gL5flhCXCj5Sa2efIPuprGON3pw=",-3988074352301212638,-3123202993687989524,476015154919299493,926202750732234283>()) {
                                                   case 2108038034:
                                                      var7 = var1.getInventory().getSelectedSlot();
                                                      var1.getInventory().setSelectedSlot(var8);
                                                      if (var0.getConnection() != null) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"sosuxb04hp9ww","9uvi5oSuB4i+WDhCCXxcn735Uh6P5gVKXlTYMwvuji0=",-8361586146699611231,-4041610776263139222,-777247982837887141,7034306984488232997>()) {
                                                            case -1605840956:
                                                               var0.getConnection().send(new ServerboundSetCarriedItemPacket(var8));
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1ewizx5j3ux6j","OWCP+6hqc7QRhgJXFC/VxGZ7oPrBUmTUXeojtzt/96Q=",-4840048055469544938,8404561952515127855,-8549997133263790375,-8229514714267598830>()) {
                                                                  case 1794312795:
                                                                     break label129;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                      break label129;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break label129;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s1cv5nwn6csrxw","ztt5oiuYcCrDZxdoq94R9MuDSasrwx5vQO17JgtR4q0=",2016776115082577226,187510214478241625,5856704870354344793,2952912184567681493>()) {
                                             case -2002043699:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3fc8bzfl2c4dz","d3kNFgq4rvwbbWnzFBLGkcib/Nf2K+CfWdVtqUCz5Ic=",-7844113461330509988,7210826866177489642,7116548441028125372,-5543193437142840912>()) {
                                                   case 501208543:
                                                      var8++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2xsaqozk6iq0","l/KHn0joATaI4m/4p+nDuFHK4Itc/jjfePJ5AUYCjWY=",5579284565733962284,-832714529058982665,846690547941622577,-3268427282729617650>()) {
                                                         case -246888385:
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

                                 if (var12 == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2tzsxkfq1sq6l","mrHRek49i4ThY2/TW1jkPhGHTxE55jWRvUW3cbLlHiQ=",-39723151524873977,6830143369291417678,8989469940093006921,-3253916542565164453>()) {
                                       case 1025256710:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    BlockHitResult var15 = new BlockHitResult(Vec3.atCenterOf(var4), var5, var4, false);
                                    InteractionResult var16 = var0.gameMode.useItemOn(var1, var12, var15);
                                    boolean var17 = var16.consumesAction();
                                    if (var17) {
                                       label84:
                                       switch ((int)com.yiyiaddon.m.b.a<"s365d64r1icx0r","1+jyW0TClPJKJjutnj0V8ey00LEZLkL8mMJaNiGHzMw=",7882454489185643242,-2321587425587194643,-6820962512938516073,6069024581209528708>()) {
                                          case 893900199:
                                             var1.swing(var12);
                                             switch ((int)com.yiyiaddon.m.b.a<"s3jlpy62fli7ph","lpzEvBDeONrzmFR6R3uG9KnSOawm1N5AURfsuCLX7n4=",7314232329217555720,2746097344918481513,3695082701417224834,-2361252858804652631>()) {
                                                case 1547675515:
                                                   break label84;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var7 >= 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2xp00gz230kxn","bAhMq5lOkQLb08c2iAPTvqe0JfMgHxj4G6bQj1EOqaM=",-3144994866695572868,-8504445397076638370,3831615591028246523,-1190797587035575970>()) {
                                          case -8076372:
                                             var1.getInventory().setSelectedSlot(var7);
                                             if (var0.getConnection() != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3h4gc0qg739q9","fKzHMCZsCzC7M8UepepgJV9dp1wS1iTxT+hB93J/Rvo=",-1143737844668176208,-897267867698741235,-3853801423499943075,-146066143101967377>()) {
                                                   case 593933185:
                                                      var0.getConnection().send(new ServerboundSetCarriedItemPacket(var7));
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2dey453wpmr3v","3JwpP5uirllvID9DJsGZEA9NMu6yHZgSg9EsGdt24ZY=",5454645471844619286,-6945882641491531435,5279841669183186567,-8421749186646483493>()) {
                                                         case 1831684519:
                                                            return var17;
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

                                    return var17;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s50y4a5ui3ae6","1pWfEhSja+ItuI4VZhF7qEZ7XdGmWiJEyVmWUgm4xx0=",-6448680249441624830,-971995648330709675,-3347099805407337692,424745752738905257>()) {
                     case 709726118:
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

   public static boolean a(LocalPlayer var0, List<String> var1) {
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3183v4yr6oxd9","s1oOn5ZW50xtFurhYzAErOloVOJZeOxQCqsHeuhR4n8=",-8706994689181605965,3432082435690619171,8098091755849497553,4269948532376420881>()) {
         case 400759489:
            while (var2 < 9) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ri55411oqa07","DcfTeNOC7zuSa3e2Ff9rFL7OA9zz3OBrYmmMUUtDceE=",-2470614339554523744,712468312308698532,-8901852591480686795,-8963317465931711530>()) {
                  case -1995398763:
                     if (b(var0.getInventory().getItem(var2), var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1go8e1x0j9bal","vcxQTYhqD9jEL4xQHtdxqoMXwxX+zXpIK1POW9j0HgI=",-6502674351294596195,-8059904197676944297,3125457547972424383,4721768555391538479>()) {
                           case -1931708440:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2yfwn7vc9yu9","A8W1FcCGzBVwjO33pl878C2zdGtgSxNmaZpC9iKV5Z4=",2357022983378845419,-5788431996537432437,-8505875410975903512,8292569480437820265>()) {
                        case 463345138:
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

   public static boolean b(ItemStack var0, List<String> var1) {
      if (!var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2gemody9q2bp4","PkREKrfTB84ewtjLGTxkoe35ZlY+Xkvq2RsTYTMQhr8=",-8474964619902543136,7064129614779785250,-6262571225641487897,7092825962684527269>()) {
            case -1263458466:
               if (!var1.isEmpty()) {
                  Block var2 = Block.byItem(var0.getItem());
                  if (var2 == Blocks.AIR) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1ahr2fdq9uc4d","rMXeCb0EP/RPbsJpRNPfFd0+qW49QiH1lDQqCnRO6DA=",1126969089137619549,-8446494204955974048,-1130797887942576031,-5739880318419996870>()) {
                        case 952008425:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  return var1.contains(BuiltInRegistries.BLOCK.getKey(var2).toString());
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sh9dxazwu1f4n","nv72AoSk7Y671lNtN4fuMOLH2402Y8rhaZeS5OXJOYc=",5888126008765724804,173674095038518415,6614724491593996894,-2217748324577122101>()) {
                     case 1964189363:
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

   private static boolean a(Minecraft var0, LocalPlayer var1, BlockPos var2, Direction var3) {
      if (!var1.isWithinBlockInteractionRange(var2, 1.0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s10fro1y555zz","xJOHE4Jg1PFhM7vxM8xVy3EDGfTNEz3oYI+/spdMOfI=",-7459293515649116874,6903381498640074429,6433899580644884068,-5237801125396097138>()) {
            case 2062755075:
               return false;
            default:
               throw null;
         }
      } else {
         Vec3 var4 = var1.getEyePosition();
         Vec3 var5 = Vec3.atCenterOf(var2).add(var3.getStepX() * 0.45, var3.getStepY() * 0.45, var3.getStepZ() * 0.45);
         BlockHitResult var6 = var0.level.clip(new ClipContext(var4, var5, net.minecraft.world.level.ClipContext.Block.OUTLINE, Fluid.NONE, var1));
         if (var6.getType() == Type.BLOCK) {
            switch ((int)com.yiyiaddon.m.b.a<"s1h2b833ckrt5e","W3251raCy+E80K7I7VwvUEWMUZYTARteNKTjmB4F11o=",-3709186842011874594,728120300431234811,3677526527055221221,-391402725969201313>()) {
               case 349017634:
                  if (var6.getBlockPos().equals(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s315fqi2xt97hc","uGI+QKP6pNjQwqHcyogLAZbd4cxIdP6wiB7prN+EjIk=",-1754460580346667926,-7905003552635438263,-5453377252217731453,-1402451297063264621>()) {
                        case 1392382473:
                           if (var6.getDirection() == var3) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxjf75lx9j98j","maO9tQcxTfS7e+2HVxbXMzkXPp9i9CDsOQca7SAUlq4=",7725976021246020968,-5986523998462089404,-2151378972186252002,-9067459066532883604>()) {
                                 case 990564715:
                                    switch ((int)com.yiyiaddon.m.b.a<"s26eudg6fcbib9","3vSp8I48GRXgg6n6AXHWR1iS/YfC23HRbiqqkjbTbuY=",6440815400206711291,-8079683736451885234,4184336232808837325,-6932754064455585019>()) {
                                       case -404349681:
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

         switch ((int)com.yiyiaddon.m.b.a<"s12tvoqmudez82","fwxktLfE62HOn0Kz/WO+ycl+/U8/E4Bmnr807jPguRg=",3636170018939068038,-7843263075879347801,-3845895023207166978,5007969361510944854>()) {
            case -350936027:
               return false;
            default:
               throw null;
         }
      }
   }
}
