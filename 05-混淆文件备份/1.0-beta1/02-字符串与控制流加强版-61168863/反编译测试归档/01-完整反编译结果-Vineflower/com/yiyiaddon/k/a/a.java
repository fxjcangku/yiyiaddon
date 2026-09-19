package com.yiyiaddon.k.a;

import com.yiyiaddon.m.b;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class a {
   private static final int sM = 3;
   private int sN = Integer.MIN_VALUE;
   private int ll;

   public void f() {
      this.sN = Integer.MIN_VALUE;
      this.ll = 0;
   }

   public void ae() {
      AbstractContainerMenu var1 = com.yiyiaddon.i.a.a.b();
      if (var1 == null) {
         switch ((int)b.a<"s326hnkzsnrh9j","PyRYMOAJH/rA59VhStle78eetFqZU0rcjZyh7ESPhVA=",-5842068137227976949,-8294900252799260791,1025813785446031527,-2797880468654978176>()) {
            case 104026986:
               this.f();
               return;
            default:
               throw null;
         }
      } else {
         int var2 = var1.getStateId();
         if (var2 == this.sN) {
            switch ((int)b.a<"s36n3hxl8q358h","tG6CVlCX/3zxuruXqwLi0atRunVlS0Lfx/zwM2dh2iA=",3782979778807856870,-1922122774827511472,-4359072171225319243,212330956377306005>()) {
               case -361250807:
                  this.ll++;
                  switch ((int)b.a<"s2j27hulundl7y","cpRrafWGv8HLaruFyoi1ORIgtOs1bzbbyA1ljC+v/A8=",-7461692454110634629,7293982792061930254,-555172095459818238,-814793373832134185>()) {
                     case 1102255459:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.sN = var2;
            this.ll = 0;
            switch ((int)b.a<"s2vuvtydh0i1p3","1RJhQyMMNYCvIw7PI227XUZlhGzjdTddDdEHlQnpbxY=",-8993108246518941469,-6501942676352509578,525276278250646210,4778107907524482285>()) {
               case 1408206730:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   public boolean fr() {
      if (com.yiyiaddon.i.a.a.fq()) {
         switch ((int)b.a<"s2y33hsq6f0o13","DhIVFD/tVmC7jdQcc8G6Xrz/lQ4Efz6v0OgNdriB4DU=",-6490772327236995599,-1830497619178204967,4844323621325222175,-578069069465697216>()) {
            case -2064652853:
               if (this.ll >= 3) {
                  switch ((int)b.a<"s11kru9gw5w934","S7rZhiZxG1WKytC+EpcCR9cjKiSLopttrfkgryH/uV4=",-8010622785851841555,-1059698495356127574,3412597474659622295,-2952575913122993391>()) {
                     case 1765145099:
                        switch ((int)b.a<"sz09df19b82hq","mSnURL8N5XXn/7nnv9OjNXmqwju1JbajJjrZ0Ee8xuQ=",19461249056725641,8319926435318793307,-1058450377190272013,-9107499720286435290>()) {
                           case 976897669:
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

      switch ((int)b.a<"s3tep97y9z9cwe","HdkHzqt7u2+urqQFGUjfguC+wbfiPKC57XCteh/LQ/U=",-8123929130891702436,-6306512955285389145,-4878213500693282551,-5759022632036797387>()) {
         case 192319292:
            return false;
         default:
            throw null;
      }
   }

   public boolean fB() {
      return com.yiyiaddon.i.a.a.fq();
   }

   public com.yiyiaddon.g.b.a a(Predicate<ItemStack> var1) {
      if (!this.fr()) {
         switch ((int)b.a<"s1m3dwlsuc3i7d","1jxoeYau/Bcs2TQi3OMX8gIiTyh1AekflA5CDmGj7hY=",8810134899138835395,3476672944833698806,2530158715028340245,-1313677431749135928>()) {
            case 867466932:
               return com.yiyiaddon.g.b.a.NOT_READY;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var2 = com.yiyiaddon.i.a.a.b();
         Inventory var3 = com.yiyiaddon.i.a.a.a();
         if (var2 != null) {
            switch ((int)b.a<"s2dynbqs816bv8","neJeYqNpkDHR7gZE/Gl4q+n2mEqBf1lNwlMl/Bq6/EE=",-8063243727274818557,7879713795449199408,-3879477877540547067,8468778356037926740>()) {
               case -851963407:
                  if (var3 != null) {
                     boolean var4 = false;
                     Iterator var5 = var2.slots.iterator();
                     switch ((int)b.a<"spfgne8ut2c3i","HKG06mBKVZQ1Ppu08Tu6Oe16D78PTPexChfYpcbU6AY=",-1514372524577730281,-3967424211674541746,1358445247768162269,-888947802891664176>()) {
                        case -912296506:
                           while (var5.hasNext()) {
                              switch ((int)b.a<"s1wiujwtoz3s5l","U+xGulY0o7nsEw6OKzGzbBmI+d3OKCoEeuChZJYKyCg=",4995791439366652721,-2567008701541220971,-5713701152284834600,-9056763433156303013>()) {
                                 case -1917044502:
                                    Slot var6 = (Slot)var5.next();
                                    if (var6.container != var3) {
                                       switch ((int)b.a<"s361x3wadzsd2o","uaEGCyxnEb8Qiibc9eQXD92zwnPfwGObdRMwwBCfm68=",-1260998058877500495,23579282655551676,7984019691404830420,6740025167026942206>()) {
                                          case 902884817:
                                             switch ((int)b.a<"s39ns09dteejvk","Itl4h5Nn1Zz95ZaxFwtJvpCLl0uKWHMwkMo2pExkTQw=",3862304677280317612,-7623265429366625700,-3214571256956897436,-1771612900876028823>()) {
                                                case -1797846971:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       ItemStack var7 = var6.getItem();
                                       if (!var7.isEmpty()) {
                                          switch ((int)b.a<"s36dkezk4tm50","rpAB+Uhsoectz4z0pt8/pSeY30ODpLsdkx09zfRZBjQ=",-7086434399011970761,1121440443592846069,2877349459060991145,-2814121265597037069>()) {
                                             case -249265985:
                                                if (var1 != null) {
                                                   switch ((int)b.a<"s20uhx8sbshxm6","BKkw8/nhvsoIBCf+jnmvTRcIV7blNpeoGIDYEd+utR4=",-1206703735778325053,1194670203667467211,-1368210404261414877,-6272393225351274800>()) {
                                                      case 1169652567:
                                                         if (!var1.test(var7)) {
                                                            switch ((int)b.a<"s369krsd1zruja","mFTz/xVPr9aGqJHtObTWt5N/6LOerbrLr8ZYENLXmvw=",-275643678665277567,2719700568310060098,-4982358749311552067,3860403122453795586>()) {
                                                               case 367297251:
                                                                  switch ((int)b.a<"s3diu1m0moaopj","RkOllzih4tvylWU/KsR78FgOSwvYk4CzQpKXN7jRQnE=",-3335182339375814382,-1267600496355062901,-7842019735244571737,859062703427900828>()) {
                                                                     case 902160622:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            var4 = true;
                                                            if (com.yiyiaddon.i.a.a.a(var2, var7)) {
                                                               com.yiyiaddon.i.a.a.a(var2, var6.index);
                                                               return com.yiyiaddon.g.b.a.MOVED;
                                                            }

                                                            switch ((int)b.a<"s2fj3uuwjk678s","0Y1GfFZXGpnfBZP0yPetcGCqJZEJPtEdMKkP5u+GqS4=",-1365501293345968308,-7689762924823379938,486529445855654209,2158861738089040658>()) {
                                                               case -1871374041:
                                                                  switch ((int)b.a<"s9vyfqbqlut0z","F7+avQ6SiWo6wFw5uN7uWCqpf/edHoFcoPWZrhydMTA=",-3748163448686874123,-311892083741071166,-919993338666507125,7793237777262672630>()) {
                                                                     case -1541037352:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                                continue;
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

                           if (var4) {
                              switch ((int)b.a<"s1z0w6iazcwg8w","KyBuTfYdrE17cCheuDFd6lruTqL4rbO+q8BstQnQAzY=",7044517969662279161,-284554950370934106,8058762738776230463,5336521080454619924>()) {
                                 case -769963562:
                                    com.yiyiaddon.g.b.a var10000 = com.yiyiaddon.g.b.a.CHEST_FULL;
                                    switch ((int)b.a<"s121rlqdm9wls0","AE+Axz67iLM7+CQXPsyduRoG+wLPJrrhUCtTD5/fqas=",-4294127856660159150,-6218536922243158022,2399108685163088506,8953113639585169937>()) {
                                       case -136954587:
                                          return var10000;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              com.yiyiaddon.g.b.a var8 = com.yiyiaddon.g.b.a.NONE;
                              switch ((int)b.a<"s1xvjtm3qqm8y9","Fz58kgBaQKbbVEhxbIAIJF/wmRotEtLSz3HaKsZNOl0=",1325502168183680722,-2927746316995164119,121238722469301765,-5930967438098420272>()) {
                                 case 649695431:
                                    return var8;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)b.a<"s13zhc9m0m29cg","ezo4p3mZpD4GEVTbCpM8FfYZ//+Yhr3IrKfbPe5SxfY=",-6405583344953319377,4269533461172988439,-3784243334599097044,-6246893545877680840>()) {
                        case 174740754:
                           return com.yiyiaddon.g.b.a.NONE;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.g.b.a.NONE;
         }
      }
   }

   public boolean f(Item var1) {
      if (!this.fr()) {
         switch ((int)b.a<"s12w7l3vmn5p7m","QUZqtOwggn7AEzHlUWU9NYQTJjV/Kvv+Zwa35PNvKhY=",-5939210884679573952,7461519059595487080,-3447818307396001592,-9176226620695405517>()) {
            case -899686787:
               return false;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var2 = com.yiyiaddon.i.a.a.b();
         Inventory var3 = com.yiyiaddon.i.a.a.a();
         if (var2 != null) {
            switch ((int)b.a<"svztxkzv1pfvl","FzHdGybmTMtkQncrEpy2xxjuzXx3KoBvv5OxnnobEjg=",-2881152568264379914,-6716777818726976865,7858183885993464465,-309671386401699805>()) {
               case 826301055:
                  if (var3 != null) {
                     switch ((int)b.a<"s2gu5xrbsmueim","UydO8OAg31IW23wUn9UfZMxpY0kDq7egCgun4/rlXb0=",-2127483296532661216,3900923409958282104,-6677394549203477061,4854678227248496428>()) {
                        case 1043735458:
                           if (var1 != null) {
                              if (var3.getFreeSlot() == -1) {
                                 switch ((int)b.a<"s2fiawo4350tuq","jy3htDrdCx23sh1RT4WrzZCOnxSeZfgjGiZ8aq0LaYg=",7640191976689442633,-3774438446113019820,-4895600965694728497,4766797071820424347>()) {
                                    case 1661975430:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              Iterator var4 = var2.slots.iterator();
                              switch ((int)b.a<"sf7clz4dgngja","PPNh4wU1l5GS7fGwOGqNZkyoBKYnCbWMNB5Qa6j66gY=",-5051385120163621326,246269183435867272,-7390128374323180484,-4496011194405571796>()) {
                                 case -924679901:
                                    while (var4.hasNext()) {
                                       switch ((int)b.a<"s3u1vjee7mvrkv","B3rIXWVs10EdWcr2L7UAiXHGb2liLgqz5CKu3vRcPmk=",-8530818464232921370,-595664197020037934,-2669216678882988103,3305752929065039143>()) {
                                          case 1479230879:
                                             Slot var5 = (Slot)var4.next();
                                             if (var5.container == var3) {
                                                switch ((int)b.a<"s1i7u5jkdq4ex2","oX0bmG4NfLqhENl2k2Xd9scSGULkpQxKsVv2/hr/3Bw=",817577981538517154,2005856793650809178,4032310160864896165,461924252631151663>()) {
                                                   case 1244672969:
                                                      switch ((int)b.a<"s1ae1y10wg6axi","T7VJjyojzAPMgN46XwpG7aOASs+iqDETrvH3sBYLJTw=",5166964504050889659,-41246325803280916,2327232153039375385,-2044686696428623990>()) {
                                                         case -854893257:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                ItemStack var6 = var5.getItem();
                                                if (!var6.isEmpty()) {
                                                   switch ((int)b.a<"s14rjypt8gz3ah","Oba/7rjZwQPFqblgk2rv+1ozldiXKSYr5+SgULM6HO4=",-8403442109472334645,-4109700342339858835,-5434731692495684335,7827165959569280804>()) {
                                                      case 958720198:
                                                         if (var6.is(var1)) {
                                                            com.yiyiaddon.i.a.a.a(var2, var5.index);
                                                            return true;
                                                         }

                                                         switch ((int)b.a<"s14p5qhelmxd8p","dfYtffdp4g51uuee18Bjy70vE0UVfVriS0FzyOY9eb8=",-5914553035674248739,4024134804573463256,-1786877233471591655,-2794876783064557915>()) {
                                                            case -1753830758:
                                                               switch ((int)b.a<"s3ksidjoi0doqg","1xahJMdCwpu//jL0RsLgY2C8lsPhRuaFQa96GE13W+M=",-367085808200169194,-293998859065637652,4772107271717337957,4910024305401685983>()) {
                                                                  case -542261392:
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
                                                break;
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

                           switch ((int)b.a<"s1cjzujwukyb20","t0DZL9eLpVsAiCbIF0tyf3IT+bOMG4FGAGm6I6I3v80=",5150169789392353602,-765309919259114280,3108043722530886645,-5430460668616750859>()) {
                              case -299813202:
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

   public int g(Item var1) {
      return com.yiyiaddon.i.a.a.a(com.yiyiaddon.i.a.a.b(), var1);
   }

   public boolean F(ItemStack var1) {
      return com.yiyiaddon.i.a.a.a(com.yiyiaddon.i.a.a.b(), var1);
   }

   public void close() {
      com.yiyiaddon.i.a.a.cD();
      this.f();
   }
}
