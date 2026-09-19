package com.yiyiaddon.e.f.b;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class d {
   private final Minecraft w = Minecraft.getInstance();
   private final com.yiyiaddon.e.f.a.a b;

   public d(com.yiyiaddon.e.f.a.a var1) {
      this.b = var1;
   }

   public void a(List<Entity> var1, c var2, int var3) {
      var1.clear();
      if (this.w.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19iv0am9p0rf5","atveK12erlNHHbubjgRjhaxbi8VF+gR+8u+GdJwpqis=",3688225817079031383,-7320193195838164947,7215908318175584505,7652256254802197778>()) {
            case -1275988648:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var4 = this.w.level.entitiesForRendering().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s12yzmahj57rxr","Xcr/34+7Zj3g5Owaq52xdCwbsrPoNokjxPTK15sDiac=",6265955467536981093,4325381027264848497,-2136795423277033838,2741908212940032078>()) {
            case 819543364:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2rz3suvvs962w","IXotelsluhCm2xDOfS99J1kGwZrbmpLea3xCtUUnchg=",2815497825146611523,-275319384783579006,-8389482954766687254,3829198892034229110>()) {
                     case 1896738188:
                        Entity var5 = (Entity)var4.next();
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2jq1shy0u1jxj","cpntaQpyqasZr7lMs1nhbOibOmUhrDWFRYqwgpIHURA=",4766688300208126553,6548360654198477100,-9012078533288574920,-2210808744642985692>()) {
                              case -64542564:
                                 if (this.a(var5)) {
                                    label43:
                                    switch ((int)com.yiyiaddon.m.b.a<"shfpsxax090wg","E+xACMSDdjlTyXMp3xYjLfv+ycffy6mZJ1d+gcQJFo8=",5612443207775718876,2163148062191111792,9001893259662772039,-1789090651977804777>()) {
                                       case -1654448814:
                                          var1.add(var5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1a0bzfbp2xty","sc3r/4S/wN7t9uXqCwAk1OYSBddhZfyMnlhEKMMSeAc=",-4661756195939246288,3977093587174339736,-4392850233458724680,5880094957086838767>()) {
                                             case 363888641:
                                                break label43;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s1z43kucntbud2","sHDTB8qP8R9Yy8TngX+TyYIw40ADkemm/Apt+kIUlt4=",-3638234524979540495,8754734345962134221,-6367118557655007915,-2641324991386483386>()) {
                           case -1300685367:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var1.sort(var2);
               if (var1.size() > var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s186p8b1fysicr","RLy550/PlwNo+RLg3T7Kcx3GBie3KC//P0NVEiL46n0=",-6388867628259924839,-4459029922695157942,-5079108285755504087,5473808906142187344>()) {
                     case -51733654:
                        var1.subList(var3, var1.size()).clear();
                        switch ((int)com.yiyiaddon.m.b.a<"sqyjesegjz0z2","utF0R/NCanIN79hPh4PNOdqZmNKse6JUTemQJQ2xEFY=",938775178856647185,-5752384181951785632,5296016905450548352,6389307875934934659>()) {
                           case 1293803272:
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
   }

   public boolean a(Entity var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7fwbjjcq55jf","ZZ+Ggxp4Ksn6mxsGQT9+WzXOoxjaALKzMhV2hVuEBgE=",-7497040433939240279,-7293799483937842630,-695881476925430998,-7181483587881677732>()) {
            case -1894744951:
               if (this.w.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"skz25d77y0x9a","k6UEuTKArhU8q7dEw1CJf2CuDrAPAEbY5HNSHf+m7P4=",-1411852129807496718,2883589052281138999,-4336495189129639180,-7631510517914592928>()) {
                     case -2014404211:
                        if (this.w.level != null) {
                           if (!var1.equals(this.w.player)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1pqvb8dqinkyf","HOlb/zub8CAruKBBS5xQ926PM//f3u/5c6N24UXRo78=",-7010661522123640341,-6499959847146566329,6211411100093719201,-4715470712050186072>()) {
                                 case 288861742:
                                    if (!var1.equals(this.w.getCameraEntity())) {
                                       if (var1 instanceof LivingEntity) {
                                          label240:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1fzk9e2yklsg1","QNIov59hFTYhclKAoccwrlJZMe1LpYC0XmqmtKTqHNg=",2372889207111279365,6334144812352916064,4094584660226827557,2123431174446640591>()) {
                                             case -299758091:
                                                LivingEntity var2 = (LivingEntity)var1;
                                                if (var2.isDeadOrDying()) {
                                                   return false;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s22b7lbobt4vp3","frorFoeydoA1aPiuO01dqZNSvNpHwU/lwhaaWmXyQ44=",8574531836696468419,-4466346179114338856,2204031468833174658,-5480933760838178715>()) {
                                                   case -1882163219:
                                                      break label240;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (var1.isAlive()) {
                                          AABB var4 = var1.getBoundingBox();
                                          if (!this.a(
                                             Mth.clamp(this.w.player.getX(), var4.minX, var4.maxX),
                                             Mth.clamp(this.w.player.getY(), var4.minY, var4.maxY),
                                             Mth.clamp(this.w.player.getZ(), var4.minZ, var4.maxZ),
                                             this.b.t
                                          )) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1zwo00jc4rzz0","wbwR7lAbvcAiHuR37aETUKPWj+YQ91kkeDERrWROUZQ=",-523004340705140753,5907495977004842905,-4526461789346927868,1675163231485102237>()) {
                                                case 1619816973:
                                                   return false;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (!this.b.v(BuiltInRegistries.ENTITY_TYPE.getKey(var1.getType()).toString())) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2a3kg9u9ngyah","FkTdKPTPlDOiD9yI6vb3afOG2l7mlrj1afCBiNI4FZs=",-1259021194182203604,-95037756071781809,5002831310887791389,6077217404414251048>()) {
                                                case 355236711:
                                                   return false;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.b.aX) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2dkz6poltx4zy","nJ7OpMlDOkEAqdX9EnsH8MqjERTVydFS0IzdmYJpzbg=",-1476021442850255316,1444939764989568592,-867041253779420051,5757365860550802679>()) {
                                                case 1734798692:
                                                   if (var1.hasCustomName()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3a6jua2vi3qel","k6Jqkwz0LPxtrqajEtgmGryWcsB640PU+rpljaqPdAs=",8843263263755299488,-6746076995816430912,-6490792670115379135,-4877541917341461188>()) {
                                                         case -1221329982:
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

                                          if (!this.b(var1)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"so6awi7kdf1xy","brRHBB6azuQTYrzP3ox1VfP78p1xzrjH+cA153JQ6tc=",-2730012544511347162,-3161498645671764672,4665395265689203547,-5055819541217895028>()) {
                                                case 431455394:
                                                   if (!this.a(var1, this.b.u)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3s7i0pnzvqwat","AMPIi5JhJI6RoQJC7yCNqpWS58WGKjZncZfckc+R8vo=",-121258856846564639,8321495142368751642,4896276345665243258,-1360190962571240331>()) {
                                                         case 1310940689:
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

                                          if (this.b.aZ) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sp4eh1zo1m6yy","lhQTb031NzNP3GoQJX+ECDevpFnQ22GC1A2ou1wjCGU=",-1194729134441390191,7633615350490602090,6768405905732942897,7594011376641012812>()) {
                                                case -427626203:
                                                   if (var1 instanceof OwnableEntity) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sbfvr3hy0fhms","j8iAce+/fhhqBkZ/h/B/WsS5Fu6kpGXAG7ust5fBmsY=",3340029654972967634,653798594777321704,3019081430478477445,-6246139557001394327>()) {
                                                         case 663946666:
                                                            OwnableEntity var3 = (OwnableEntity)var1;
                                                            if (var3.getOwner() != null) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2ssfn4npctgdf","9p3cr1lYYnZcmMnAlhnjZolKkFzkOtxg0dhL/+6GH9E=",-8327641827978810139,-4577248157542724088,2087227762828593973,2195669731873333053>()) {
                                                                  case -1630016629:
                                                                     if (var3.getOwner().equals(this.w.player)) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2rh8j3f1mgzle","USHe1fN6IeFS0hxFdNOGsamLwgFEaAaKLBVKZvWN3Vg=",-3789159823929980555,-1532504158094462847,1073516889709168759,7726933442963579464>()) {
                                                                           case -2015467050:
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

                                          if (this.b.aY) {
                                             label277:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1uv6om20ermi3","4bykEBakpTHcC6ePg8BgYeRjJ5JN4VaLk28rLt38Yrc=",2608594032724995282,9117389587287722689,-6076272357670130084,8650404205414837395>()) {
                                                case -1740444474:
                                                   if (var1 instanceof EnderMan) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1i4wkw99hwqmw","znpv1E9ggMTKO2fo8dCkHvmzm+Ox7NoTSq9Xt7jtAtc=",6637575318210601888,2313413823955592812,-422672411081475899,264237482716436167>()) {
                                                         case 2015620911:
                                                            EnderMan var5 = (EnderMan)var1;
                                                            if (!var5.isCreepy()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1omi07urodsz5","WrTksTgjX1hpOpGvibgk+tU0uVoJV8kcMs+TwGBlKEo=",-8072654770045441837,-4919502490983552764,-8013439014954564710,8990313235072043760>()) {
                                                                  case -1361805579:
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

                                                   if (!(var1 instanceof Piglin)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2mjaxlmutz8ci","DHB5CGv0dGoQvLtkj8E/V72BOQWAuwhZY0fl9E1VTPY=",-765672962424656306,-5509342200813310090,8340375080207373500,4652547188447453405>()) {
                                                         case 704132410:
                                                            if (!(var1 instanceof ZombifiedPiglin)) {
                                                               label218:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2xyzsv6na9j5v","q2JsPi1ZIbiLFLQgRp8bPxJCa9D+HCzW1J2ULdXtL+g=",1253491922351661363,6270687164543050113,1218792657268135032,4147677685581052666>()) {
                                                                  case -2128694856:
                                                                     if (!(var1 instanceof Wolf)) {
                                                                        break label277;
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"suwgre45mmfgg","XfcuSH2a6Vh6+Yx7tPyZwS/WqnTkWFvbdvn7Y1hhFtY=",-165803176126354211,2826936302890896310,-8382747509560870508,5665623575804767468>()) {
                                                                        case 149755798:
                                                                           break label218;
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

                                                   if (!((Mob)var1).isAggressive()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1rdznfi1vyejy","LjWRQRce7iPbFy7iqcMRU8LJ7Idt/dIwUTT0NJ3U3HQ=",-1520986567663330598,6503585712433975614,-485701555818854844,-3456960238235464713>()) {
                                                         case 367680478:
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

                                          if (var1 instanceof Player) {
                                             label213:
                                             switch ((int)com.yiyiaddon.m.b.a<"sfme8ettmkq7r","y5e3cfvFgn4HHDuNqQhhM7my8A+k9KTHjvv8qt4b2+s=",5696119112218351759,306997537085736577,2315833681139577823,-2610020680656185945>()) {
                                                case -818980846:
                                                   Player var6 = (Player)var1;
                                                   if (var6.isCreative()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sww3g0dlissgl","IfekzydwxBDNjGrZZ27D3PrIu0sVIUSAJgcmvPAGf/o=",-6674285649767667518,-497200779142626284,-6978810170936602416,-2551770985198903178>()) {
                                                         case -753324727:
                                                            return false;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   if (this.b.a == com.yiyiaddon.e.f.a.a.d.IGNORE) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1dgsr1dfu0rye","xXw2vitygxhZt9UhkZE2LcZht2kn1yWtqDj+cXxZ0z8=",-6013684479019527525,-6566437607401839712,-4145636992568343686,-6315485906180510901>()) {
                                                         case -1896669866:
                                                            if (var6.isBlocking()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2mex7opx502ty","1IWuKXiiOsBXwxFqqPKM6ikarPD2fvgLfSi/i+bO+hY=",-394206922090262878,-825883888782427712,3079986362890564760,-966180747970809114>()) {
                                                                  case 1334271763:
                                                                     return false;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break label213;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var1 instanceof LivingEntity) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1lisabbajjhxl","WdMsdt3ylsAuj3Yq5mZ4bMKyhxyQxRNLSCAjluAgltk=",7557779000322343909,5819167178613711045,945397304506887115,-4198429559156158513>()) {
                                                case -877257409:
                                                   LivingEntity var7 = (LivingEntity)var1;
                                                   if (!(var1 instanceof Zombie)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s20xo133244gyf","cq90667tu+3xVnEQuI6i4N+4o1wQUcZWXvhZWUNggR0=",1756142383033943491,246972282763998192,-9114975400502537741,-2944543718843644635>()) {
                                                         case -1007660116:
                                                            if (!(var1 instanceof Piglin)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3bet6lab95zpk","Z1NLWZtuJ1olQOD2Jxb6Xk4YTjGJB9R4ewg3/jV1io4=",-5583436743137629004,-6757801502751247469,7478660502730993521,-7719743599936071598>()) {
                                                                  case 1286080295:
                                                                     if (!(var1 instanceof Hoglin)) {
                                                                        label200:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s1qdh4yqfzvb2p","kgyvJ5yr+txH5cpJGbakVVC/v9hkMfiqaPujqHuA1xY=",-6338849302392021432,8175884831267897583,627391667379855483,6434721549347520787>()) {
                                                                           case -1095831091:
                                                                              if (!(var1 instanceof Zoglin)) {
                                                                                 if (var1 instanceof AgeableMob) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s3m61pncgiefik","e6wE9SxwlTGMVSynFN80H0EhBl6xIGBgRm/tvBqKvJg=",-2890750269613113775,4776553393107033364,-814708205062523768,-8281179831090776591>()) {
                                                                                       case 1332461559:
                                                                                          if (!(var1 instanceof Frog)) {
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s1q1yxsdbf9y2c","EAjTMyi3OSuEgsC/KxXHwzo4mnqZV9Ng7QDCKR39U74=",-8912128673088138181,-1760015882093387493,-5235826055237084400,-7789349272018137276>()) {
                                                                                                case 1100429669:
                                                                                                   if (!(var1 instanceof Parrot)) {
                                                                                                      switch ((int)com.yiyiaddon.m.b.a<"s32zneqnugeqe9","Yi2ftTsGtaAFZFZWahAxlSTPkAfo8piQIlIOS33u6XU=",-3741579728291891223,7199274830548439165,71498579070877935,7115794060043786360>()) {
                                                                                                         case -1651447201:
                                                                                                            switch (this.b.a) {
                                                                                                               case BABY:
                                                                                                                  boolean var8 = var7.isBaby();
                                                                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2xiu2axmtb0ti","3iW+7/Mcl90KmKruF6svm9KlykWfY1d5JvMUhknui5I=",-4974948410980332345,8842727987486160171,-5380301357484632186,5461354503701462281>()) {
                                                                                                                     case -163043309:
                                                                                                                        return var8;
                                                                                                                     default:
                                                                                                                        throw null;
                                                                                                                  }
                                                                                                               case ADULT:
                                                                                                                  if (!var7.isBaby()) {
                                                                                                                     switch ((int)com.yiyiaddon.m.b.a<"s9o61klw7bo5p","y3CB0bY4usDoV+RuR+03Cial0vY494ZwPdWtBqZZF1Y=",-5290518609555278782,-5646091532557874046,-932521637374656507,-5058317786414977595>()) {
                                                                                                                        case 907895066:
                                                                                                                           switch ((int)com.yiyiaddon.m.b.a<"s7bc73foqwwkp","OgEKnN/JyN5sjeCI5jB8pJ1phUcrniZkZmHqmBxlAWE=",-3220302831087293800,-5018712281746372864,-2687205000858417077,1632943216524048007>()) {
                                                                                                                              case 1908238985:
                                                                                                                                 return true;
                                                                                                                              default:
                                                                                                                                 throw null;
                                                                                                                           }
                                                                                                                        default:
                                                                                                                           throw null;
                                                                                                                     }
                                                                                                                  } else {
                                                                                                                     switch ((int)com.yiyiaddon.m.b.a<"s22908xu75ufzc","KV0mysFGOaFJ3QDrN8o1BkXVLcN4KY9wsc9UqReLQKI=",-855255218158162467,4105971710560502883,8698480597698699141,1268348775078380436>()) {
                                                                                                                        case -177995905:
                                                                                                                           return false;
                                                                                                                        default:
                                                                                                                           throw null;
                                                                                                                     }
                                                                                                                  }
                                                                                                               case BOTH:
                                                                                                                  switch ((int)com.yiyiaddon.m.b.a<"s256cilojg7plg","UyBQ1JaSW4P1zmIT/SHxxkeJi/xCugzf6h8SyctFE0Y=",-7472394395268942920,-1386311330097975954,1535577358102247357,2627073234304302467>()) {
                                                                                                                     case -1656451952:
                                                                                                                        return true;
                                                                                                                     default:
                                                                                                                        throw null;
                                                                                                                  }
                                                                                                               default:
                                                                                                                  throw new MatchException(null, null);
                                                                                                            }
                                                                                                         default:
                                                                                                            throw null;
                                                                                                      }
                                                                                                   }

                                                                                                   return true;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }

                                                                                          return true;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }

                                                                                 return true;
                                                                              }

                                                                              switch ((int)com.yiyiaddon.m.b.a<"s2l566vicjdpkp","9TpamBqZ32VjiPYJEK9y5ye3T47xQCcaI041nYRAWnU=",2279284291583664545,8864616346188432712,4861024103033238723,7411149576531529128>()) {
                                                                                 case -362329268:
                                                                                    break label200;
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

                                                   switch (this.b.b) {
                                                      case BABY:
                                                         boolean var10000 = var7.isBaby();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3iki8jdbu4ua2","3ow+sfl2Y330IgP0JPjnjSNBp15qCDIQCSQK3E+V0/M=",848879985088067902,6009191897558986967,-6738366693911327026,2238458653451883411>()) {
                                                            case -672639552:
                                                               return var10000;
                                                            default:
                                                               throw null;
                                                         }
                                                      case ADULT:
                                                         if (!var7.isBaby()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1hyxm1s5e7dok","/1qhgns8nBCDx3FXOlXopb9S028LN0APDTnQNaDvHxw=",8851767600729049538,-3368221022575792751,463658500536588701,1436640742603539245>()) {
                                                               case -178465383:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1sq1xn59nko54","XLeOcnHziVj6YvEmERVVS6rITKEOs5HdMIrQaN/b6Jo=",-6009292897946761544,5458569068641674420,-3302851995609508459,3396044331301391382>()) {
                                                                     case 233676810:
                                                                        return true;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2cn834qqduqb7","oQyGLTX854Cq5kVyFSAuZ34h+h4EdC/veWzZLI8VqK8=",4270183839351626783,86557596449213567,310793678557342527,-6405870325527082770>()) {
                                                               case -1356164818:
                                                                  return false;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      case BOTH:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s21krpj5ljfcq9","cLMyFXJAGbSWOquOV3ivv+veVATzfu40aXFgj3qlujg=",-6152830624065691139,8923304897653002599,-5483836402087031389,-5482311608197438109>()) {
                                                            case -208052045:
                                                               return true;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw new MatchException(null, null);
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return true;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s252y2aiaa1gtj","K3vUJVX89B8CVW0AK/6A8cExHwxv+cp9kabp3xk9IeI=",-7696076147563770204,-2937987078350129423,2039027227116495994,-3433951118020329309>()) {
                                          case -663783266:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s36ochxyw30fra","874HoKY6ROs2sWxctvQOjWWh+jnYC63Dppl501H75xs=",-9137047993461258382,5648203240774808631,-4880054545996290490,1171177716478632693>()) {
                                       case 519012460:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2nkbdbco3e55k","BZgKj4HcUkqeuVR1VT/QscijXQJsT9snIZCbt+cYW2c=",3092137638813364722,7040868828681713404,-4142915206504117285,4629164466668012423>()) {
                           case -422484560:
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

   private boolean a(double var1, double var3, double var5, double var7) {
      double var9 = this.w.player.getX() - var1;
      double var11 = this.w.player.getY() - var3;
      double var13 = this.w.player.getZ() - var5;
      if (var9 * var9 + var11 * var11 + var13 * var13 <= var7 * var7) {
         switch ((int)com.yiyiaddon.m.b.a<"s37wmjncz011pd","r/+i1uxcrmdwZW564pfLwQSy+fmdkef0mlJVzsN+CX4=",1532719650244933953,-3462457791874018750,-1484678311072321474,-2626107283730574595>()) {
            case 1682409260:
               switch ((int)com.yiyiaddon.m.b.a<"s2jkqz7ftic60n","Z8AXFVqGusYM63M/92n3YhuO8QFddycX2exJRPo/z/Q=",-7579222693586892567,1818759274657718282,7594856398580501463,-6797586391767059539>()) {
                  case 1117845339:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s12j47pf25cgdk","HuMG/bRcd7bfNUNfYf/9nUaYZdZpmPqEaLzpG5BUmwk=",-7937505903067695116,-2717867772250826487,8966871218574376694,-587313802518918784>()) {
            case -1322495260:
               return false;
            default:
               throw null;
         }
      }
   }

   private boolean a(Entity var1, double var2) {
      return this.a(var1.getX(), var1.getY(), var1.getZ(), var2);
   }

   private boolean b(Entity var1) {
      if (this.w.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"swgzt9bpx28nf","l1vhE4RGMiwKUL3PjH3rCjjTbn/lrG63ryGm5295UyA=",753228184702217267,-8004265666303068645,8859264882899656510,-8805499021650860536>()) {
            case 1217315184:
               if (this.w.player != null) {
                  double var2 = this.w.player.getX();
                  double var4 = this.w.player.getY() + this.w.player.getEyeHeight();
                  double var6 = this.w.player.getZ();
                  boolean var8 = this.a(var2, var4, var6, var1.getX(), var1.getY(), var1.getZ());
                  boolean var9 = this.a(var2, var4, var6, var1.getX(), var1.getY() + var1.getEyeHeight(), var1.getZ());
                  if (!var8) {
                     label29:
                     switch ((int)com.yiyiaddon.m.b.a<"s10w4ecnrob6sq","a9EjGi1aIBbNFd1re5Utzg1Q+42182Pea2LeIaFLl/U=",-5515125452836670576,-7032873420816110478,3317783605117682432,9110359655949140805>()) {
                        case -1534660762:
                           if (!var9) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1wdod8jotuliw","/E44jFEZTIhFa93jIlXMCdVCGfN/NZ096nScPPYA/Tk=",2414804989141441245,-7067622324422771996,8951149124314396217,7019560051811365013>()) {
                                 case -2101322411:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s74i8ff9pr9hq","XjDrIYTRx5pmnAYn3ni9jXQ8y9zFmf3JGGzH6evTcRo=",6719890772331273350,-5634141353068218307,-1499848176962167095,-5853210717607552442>()) {
                              case 1064010371:
                                 break label29;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3beeczoc54s1","Zg0jZ4WtSqQaN/p0Q7H6Fsk2zfmkAtNfn8cWU5AtFyI=",-8771315683012403402,6540673481337280480,-7011208472426732969,-3153969104242587547>()) {
                     case -1781371259:
                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1m33rdshcu69e","mMow+QFI/MLLBdAmBzMK8f8gAZqZjzQBZuFfp9q9h2c=",5747923765812081326,-5147704164306838392,-6981766407285505600,-3487799981056794559>()) {
                     case 665903323:
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

   private boolean a(double var1, double var3, double var5, double var7, double var9, double var11) {
      if (this.w.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mdco22mpimzr","CH0MynIJ2S9batpprhfXvFva+10mtFgWGkJ65XL7IXE=",2980686130868200990,-764970461155164472,7007446800324782584,-2859477287104285162>()) {
            case 1501402142:
               return true;
            default:
               throw null;
         }
      } else if (this.w
            .level
            .clip(new ClipContext(new Vec3(var1, var3, var5), new Vec3(var7, var9, var11), Block.COLLIDER, Fluid.NONE, this.w.player))
            .getType()
         == Type.MISS) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gocc7k9u7vfr","aVXHjTcS/aEyP1lsgmPpCqGJCnq/E/TV9EVszEjBI5U=",3026660742042310815,-8101227602708340219,-8664588782432190786,8282401502273215549>()) {
            case -44437640:
               switch ((int)com.yiyiaddon.m.b.a<"s2y5szsuy1jzk6","fm33OcDJF7LKZ8hmoTPTAOkB/6jvQss1dBooAtnONww=",-7306722529842765828,4601444444296279513,8261838495311033609,745074659189579352>()) {
                  case -1373590248:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2bsajba91n97y","X8UAcI7YAXZEeAYgkgNvMDtQsBJ6/jNC1s9lN9/m7R0=",-894814397606722325,2789676052292889586,-5859602434512195013,-5184586878082316344>()) {
            case 647200618:
               return false;
            default:
               throw null;
         }
      }
   }
}
