package com.yiyiaddon.e.i.e;

import com.yiyiaddon.e.i.d.i;
import com.yiyiaddon.e.i.d.l;
import com.yiyiaddon.e.i.f.q;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class c implements q {
   private static final int fM = 2;
   private static final int fN = 64;
   private int fO = -1;
   private boolean bL;

   @Override
   public com.yiyiaddon.e.i.f.a a(l var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (this.bA()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fsrkvus1udch","HDMjJNTQnodV8lIZuPG0+haOa1abItw/dlBJ407xPhM=",2566086424012816303,2902086538028049552,987455428440501893,-4864201756353996806>()) {
            case -849427377:
               this.bL = true;
               return com.yiyiaddon.e.i.f.a.b();
            default:
               throw null;
         }
      } else {
         if (var2.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ohop6f96zrd7","BJJLF4PnJfxe8oKOdQk0PhpxkyalmmkDXJRgXnemYvA=",8741871264220066472,1948679982333122143,-5003012572439249529,-9036208438937122615>()) {
               case 872865389:
                  if (var2.level != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s21x13cd43ol2o","i/qaNVR7YN4f+CKM2FYwLeT1uhYnZu9cPsmmTgVTrlE=",-2700184012197829895,8458024015657531770,-2454473009429476090,4585017541035861398>()) {
                        case 1025889547:
                           if (var2.gameMode != null) {
                              Villager var3 = this.a(var2, var1);
                              if (var3 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sbdhpuwst4cgr","idWAf+g4vft03D1aIaDTKstnX+VJF1lHUG2QVopqdmc=",4726787173792174704,1289056381398914058,5235826549439431030,7550880102880417423>()) {
                                    case 1014272521:
                                       return com.yiyiaddon.e.i.f.a.b(
                                          (String)com.yiyiaddon.m.b.a<"s2spujxmzqixnn","A8QtAiV7aDmbX6sQ7SbC1lDtVX3rqFtzP97t3Qa36uj7hwUh50UgEZlNDoKcas0ivsG3kFMGifcu6ZKxKBwVvG3AEZuMvITk9Rzg2MLo1kebpZcp4efT7n2PQ8UlfA==",-5183701607177993993,-2304132425469403584,-3513016350179032547,5117702041035287538>()
                                       );
                                    default:
                                       throw null;
                                 }
                              }

                              this.a(var2, var3.getEyePosition());
                              var2.gameMode.interact(var2.player, var3, new EntityHitResult(var3), InteractionHand.MAIN_HAND);
                              this.bL = true;
                              return com.yiyiaddon.e.i.f.a.c();
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1i7np8ci2w7kd","SbnCPDjTAOlkeGNQ9+QDOKKKJ9F01WT+L42N+cLJyBQ=",-9121188439776894426,5258695738741469915,-1978644187194106127,5803603463527956169>()) {
                              case -310276780:
                                 return com.yiyiaddon.e.i.f.a.a(
                                    (String)com.yiyiaddon.m.b.a<"s3forqjdfqi71k","lI0actPWmDrvuDiO+dI/f+viRBonEr0ambY2wDJY0a55TIWHB9WsX4CUUryiPQ==",6875390106112876552,-7900011049417414877,9135507222272454664,-4302147757808873157>()
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
            (String)com.yiyiaddon.m.b.a<"s3forqjdfqi71k","lI0actPWmDrvuDiO+dI/f+viRBonEr0ambY2wDJY0a55TIWHB9WsX4CUUryiPQ==",6875390106112876552,-7900011049417414877,9135507222272454664,-4302147757808873157>()
         );
      }
   }

   @Override
   public boolean bA() {
      return com.yiyiaddon.i.a.c.fr();
   }

   @Override
   public Optional<i> j() {
      return this.ah().stream().findFirst();
   }

   @Override
   public List<i> ah() {
      MerchantMenu var1 = com.yiyiaddon.i.a.c.a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12wglydbmmpha","4U137LYi9cTQcdiTluo4EF2c9ypBB1YEcZnkSsoWAKY=",-1856233265729007002,6548894387484256900,6785558400169840298,-3413088056638139416>()) {
            case -1495558962:
               return List.of();
            default:
               throw null;
         }
      } else {
         ArrayList var2 = new ArrayList();
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2ba1y87b3dxdj","ao1Lvy+OF0fs/lz6w5WTT58bFWwN4uipQZBBHqUP/Vc=",4042926028456039755,-8170375523437551456,5587177911783544084,3462960866324789539>()) {
            case -1328216207:
               while (var3 < var1.getOffers().size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wlr90yfwl4km","gehgWXr2R584Au2dvriG5Qdm2jyqwPLlgsO9zOV38Ao=",7240605303018631347,3647858212441280832,3646222715565557856,-5411123235429447282>()) {
                     case -158950734:
                        MerchantOffer var4 = var1.getOffers().get(var3);
                        this.a(var3, var4, var1.containerId).ifPresent(var2::add);
                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"spib04vugnznj","LkbRM310bJ+ZkDo2uVGjfeSSD4IoXsTGkGuV8rfmtIU=",-1199810526486615319,-4459385910120152037,2175423034670923740,-3656975168536255043>()) {
                           case -185872479:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return List.copyOf(var2);
            default:
               throw null;
         }
      }
   }

   @Override
   public com.yiyiaddon.e.i.f.a a(i var1) {
      Minecraft var2 = Minecraft.getInstance();
      MerchantMenu var3 = com.yiyiaddon.i.a.c.a();
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"svw6lvxz1qphk","Z9H9xiWV5S3vF3Om6nxc8GrLGLOidmdf5bHIIV/qJEI=",-1989977340595530564,-2554264972914789292,-7539415984788221848,-8743657251758799946>()) {
            case -2090923364:
               if (var2.getConnection() != null) {
                  if (!this.a(var3, var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2fnerlt9yctnh","9ZOAcg3Og7n6qmq0CtZWO2QLM1ij8uXK9BFDfCHFUUg=",-7940901127857733282,-881234284733194510,7802180388195837909,1406166634984404171>()) {
                        case -1637156458:
                           return com.yiyiaddon.e.i.f.a.b(
                              (String)com.yiyiaddon.m.b.a<"s3usflxpfnzg0s","moSzTRLfn4ALbTZXfhDWLg0/8ZcKZSkzf6UXFY+3MYHe6sQjWuZbxdpqltZ84mRv",-3138929587820344741,8483414561927250864,-7774176263176998007,4995020427742874454>()
                           );
                        default:
                           throw null;
                     }
                  }

                  com.yiyiaddon.i.a.c.M(var1.bf());
                  this.fO = var1.bf();
                  return com.yiyiaddon.e.i.f.a.b();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1j0bdbwdesxys","9j3BDu0RM6IHt2yW5u8PogTkzUsGMevn0OQJnlEERrc=",4589059735843665602,-9209516310505498723,2357229778787714341,-5204731861892576862>()) {
                     case 1352756255:
                        return com.yiyiaddon.e.i.f.a.a(
                           (String)com.yiyiaddon.m.b.a<"svu44qj41dyle","gYj5Ueu6SEc6yWyjwjW6QOnnwAsHCf4QTOHMct4wmWfpeu8fMIdxW33IvUU8Xg==",6844737390057215852,4860133993852445215,4578082115617109309,1273561784936397988>()
                        );
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.i.f.a.a(
            (String)com.yiyiaddon.m.b.a<"svu44qj41dyle","gYj5Ueu6SEc6yWyjwjW6QOnnwAsHCf4QTOHMct4wmWfpeu8fMIdxW33IvUU8Xg==",6844737390057215852,4860133993852445215,4578082115617109309,1273561784936397988>()
         );
      }
   }

   @Override
   public boolean b(i var1) {
      MerchantMenu var2 = com.yiyiaddon.i.a.c.a();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3evfm5qoqgl0b","sqlEDFua0eKDX85lZlPvqIX33Cbqyk7NoPhA/iviTeI=",-5773491058434622722,-9084717809503526631,4700821960362745764,-515051980782014052>()) {
            case 1650892058:
               if (this.fO == var1.bf()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sy20rrj1gwwwm","aOMmzTU0ldhH7EMriwhmHoWqUpqsUDPd6lWkb6IitWk=",8530959073146887058,6932981722750276617,677706785609377784,-6125556061301806961>()) {
                     case 1273432287:
                        if (this.a(var2, var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2z5mr603lsg4b","ZJDgHXKiqDkYXsAxInRQnX2XxVjU8JbOLfC8O7+9fP0=",1241689444696203916,-4178764717114807208,-7986987360384491433,-1802365036466398644>()) {
                              case -83386300:
                                 if (!var2.getSlot(2).getItem().isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1cadf04pp38kt","ROE0XVqnfit/zI89BfDHVFK2wwM0N7a+xrZFYvGMtHs=",-46050160511800744,5252440314708887274,7083962631045737614,7366221824199636475>()) {
                                       case -623551539:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3l4kptcj7xkc5","relp/GLFkwjvC0ECBRuTaxd7YBXH1mkZ/XQsSxIv9W8=",868480447279678910,6892969993589682191,1689994528019954697,-6875493884469723742>()) {
                                             case 803846337:
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

      switch ((int)com.yiyiaddon.m.b.a<"s33ed5g1j5zuef","aexotmNDDJRoR4PFvUnwLGONC/OklDcUSUazm4vif64=",-2623255452136471332,2317371937345754006,4673914405897627666,-2122699084813860293>()) {
         case 892336961:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public com.yiyiaddon.e.i.f.a a() {
      Minecraft var1 = Minecraft.getInstance();
      MerchantMenu var2 = com.yiyiaddon.i.a.c.a();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ahx91i3jwe5","4P9BsZb3/o0diEDi4tgCld40z2AYpJKRbcfsCWpW0P8=",8342833996921994911,201380207341760242,-55230015453520468,-3192337757217654294>()) {
            case -631857570:
               if (var1.gameMode != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2rpsclz7lru8d","TQmWcuwhahTl4fxgIMGYpsaSzBmAU5h9NFNmLWjrXZ8=",3019371760766236769,-6558949000688599610,-8712016410061395034,3545286598929600268>()) {
                     case -2055221491:
                        if (var2 != null) {
                           if (var2.getSlot(2).getItem().isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s28z6sms6ux5uq","aj1A9eOKFbcZKTTOI84+o606Mou7kzFiKXHO/IbF5e4=",-6704584469160928847,5415465071927861901,-5321052569837999284,3521710260403897621>()) {
                                 case -1726905013:
                                    return com.yiyiaddon.e.i.f.a.c();
                                 default:
                                    throw null;
                              }
                           }

                           com.yiyiaddon.i.a.c.d(var2);
                           return com.yiyiaddon.e.i.f.a.b();
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s97tv3p1gg29f","L1gUMsrXKGBYkMYbuVkKVLTDLxejz0j8/gc6EJ3QLYs=",-5318520307256404484,-4240142150942477152,-3215180995107333870,621766200962415729>()) {
                           case -1439504296:
                              return com.yiyiaddon.e.i.f.a.a(
                                 (String)com.yiyiaddon.m.b.a<"sv4g18lw1s8e6","a4ZjReiq80w75GshVy9iUNI0l7D1LCtt/RKZmjPZQkp+J0oRoK+ChH5qQsM=",-3440641544745818378,7423516971238380561,4369813100455853682,-3228367891293520173>()
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
         (String)com.yiyiaddon.m.b.a<"sv4g18lw1s8e6","a4ZjReiq80w75GshVy9iUNI0l7D1LCtt/RKZmjPZQkp+J0oRoK+ChH5qQsM=",-3440641544745818378,7423516971238380561,4369813100455853682,-3228367891293520173>()
      );
   }

   @Override
   public void close() {
      Minecraft var1 = Minecraft.getInstance();
      this.fO = -1;
      if (this.bL) {
         switch ((int)com.yiyiaddon.m.b.a<"siabzavlswa1n","bSe19VAd8ptPJ/hMnOvYURIUqGbbZokuEvVxL7k7rnI=",1746009669865521090,6100100074860544672,-1351862761546464858,-1549807857135815055>()) {
            case 802054844:
               if (var1.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3u3k496cffar3","6uEYVVITDJWUZIWO1v6kZihQ4kcufEuJunXDl2MtX6E=",6390439289763644552,-6829223739574893992,-2570752377946749734,-2300037226335377792>()) {
                     case 1114457691:
                        if (this.bA()) {
                           label19:
                           switch ((int)com.yiyiaddon.m.b.a<"s3j0givgppkmxw","C/R+A00/B7sg4FUQ/rFIddJuQkSTZl4/DcRxBG88VjU=",1782930393390973098,-2712192121333303190,-4168650029018421303,-8087966457343446176>()) {
                              case 868741443:
                                 com.yiyiaddon.i.a.c.close();
                                 switch ((int)com.yiyiaddon.m.b.a<"s25mq0kmnbbmnn","lxnO60zi7AQc4ffF+TZhjNJQqZrU6cUnKBue5EVqFnw=",-4818115342088160991,-3945851701896908109,4299810564486056224,-3884157316111855570>()) {
                                    case 1320125028:
                                       break label19;
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

      this.bL = false;
   }

   private Villager a(Minecraft var1, l var2) {
      Entity var4 = var1.level.getEntity(var2.bk());
      if (var4 instanceof Villager) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hlvs2eikowic","iaVnqqqdtAP/N817indMJilx15BHR+fIRf0Zo/tp/ps=",-4895281972132195922,-8353159150843141368,3077832535313511703,-2151449199684586959>()) {
            case -1285460759:
               Villager var3 = (Villager)var4;
               if (var2.c().equals(var3.getUUID())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ocx2e1mxc640","8p+O0lOCI5mY57z4dszdy9fAWB73dMQHYGsozlo9XJU=",3989414413681338293,-263081704101574952,-5804357338829976123,-4318108779018825857>()) {
                     case -1301540768:
                        return var3;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17213id16i5dw","RvvRgaHi4t/Mo37++dOqoVaATKVL1DHsH+/4cgnntRc=",8257052635087343363,-4636663217306622828,4783198505042827331,2175005871033370225>()) {
            case 2080618788:
               return null;
            default:
               throw null;
         }
      } else {
         AABB var5 = var1.player.getBoundingBox().inflate(64.0);
         return var1.level.getEntitiesOfClass(Villager.class, var5, var1x -> var2.c().equals(var1x.getUUID())).stream().findFirst().orElse(null);
      }
   }

   private Optional<i> a(int var1, MerchantOffer var2, int var3) {
      ItemStack var4 = var2.getResult();
      if (!var4.is(Items.ENCHANTED_BOOK)) {
         return Optional.empty();
      }

      ItemEnchantments var5 = var4.get(DataComponents.STORED_ENCHANTMENTS);
      if (var5 == null) {
         return Optional.empty();
      }

      for (Entry var7 : var5.entrySet()) {
         Holder var8 = (Holder)var7.getKey();
         String var9 = var8.unwrapKey().map(var0 -> var0.identifier().toString()).orElse(null);
         if (var9 != null) {
            int var10 = var7.getIntValue();
            int var11 = ((Enchantment)var8.value()).getMaxLevel();
            ItemStack var12 = var2.getBaseCostA();
            ItemStack var13 = var2.getCostB();
            return Optional.of(
               new i(
                  var1,
                  Integer.toString(var3),
                  (String)com.yiyiaddon.m.b.a<"s2mx7sg8zj8t2r","XxWOpCXSjUENKLP2ebGcHpmFIG5YlJ13aFPGu8WrcY/qcjRRLF7aQjHwI7AfDiw3PcHXt0io0ZzTypQtSr9Mw1DHIXaj60Fl5BTXWg==",-6170275402570357418,-6306865067513893848,6880971753940051903,-1138150534759807964>(),
                  var4.getCount(),
                  var9,
                  var10,
                  var11,
                  var12.getCount(),
                  var13.isEmpty() ? null : BuiltInRegistries.ITEM.getKey(var13.getItem()).toString(),
                  var13.isEmpty() ? 0 : var13.getCount(),
                  !var2.isOutOfStock(),
                  false
               )
            );
         }
      }

      return Optional.empty();
   }

   private boolean a(MerchantMenu var1, i var2) {
      if (!Integer.toString(var1.containerId).equals(var2.bw())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mu5tl4bqh2uo","+FtyClPTsQrJ6c2w7ZKPNQukexssDBKpAHYqM5B9QQs=",-8160869638915688565,-3483653135658694468,6435873548714273418,5986499612154883796>()) {
            case -518502966:
               return false;
            default:
               throw null;
         }
      } else if (var2.bf() >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"ssi3m4d4ex1at","wk7RbQaQST01WBk2UduH1usE5CTpCUf/1juC3pPIZrQ=",-6316031297250448851,-2041736640545944889,2451725796659857769,2688532940200265460>()) {
            case 2032834571:
               if (var2.bf() < var1.getOffers().size()) {
                  return this.a(var2.bf(), var1.getOffers().get(var2.bf()), var1.containerId).map(var2::equals).orElse(false);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qgc50tr4svd","esMqfHq/GccjSah6sPYS9J3n4asFJyoZvr1/Qt/YIOQ=",-3490448102463307479,-4361328644298513600,-9158129312386759078,7276556794447829480>()) {
                     case 987350674:
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

   private void a(Minecraft var1, Vec3 var2) {
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hopr7ojo8ece","MXIr8mIoBnwIxCtklkA5a41SLD+bOejIzW3OW/P7wAo=",-4681060936911532941,-5604173952454613826,1618777783562099450,-6257307330628577520>()) {
            case 1802339905:
               if (var1.getConnection() != null) {
                  Vec3 var3 = var1.player.getEyePosition();
                  double var4 = var2.x - var3.x;
                  double var6 = var2.y - var3.y;
                  double var8 = var2.z - var3.z;
                  double var10 = Math.sqrt(var4 * var4 + var8 * var8);
                  float var12 = (float)(Math.toDegrees(Math.atan2(var8, var4)) - 90.0);
                  float var13 = (float)(-Math.toDegrees(Math.atan2(var6, var10)));
                  var1.player.setYRot(var12);
                  var1.player.setXRot(var13);
                  var1.getConnection().send(new Rot(var12, var13, var1.player.onGround(), var1.player.horizontalCollision));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3bowzlcfzglba","3YcUazvpxg/w1orERnQVgwxApZdFtJSEmD9G5rdHWnQ=",-624704534400821157,-40373361315501067,7335525055090483589,3320826988138553597>()) {
                     case 1753845757:
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
