package com.yiyiaddon.e.c.c;

import com.yiyiaddon.e.c.i.j;
import com.yiyiaddon.e.c.i.k;
import com.yiyiaddon.e.c.i.n;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class e {
   private final com.yiyiaddon.e.c.h.a c;
   private final com.yiyiaddon.e.c.g.a c;
   private final f c;
   private final h c;
   private final com.yiyiaddon.k.a.a d;
   private int aI;
   private int aJ;
   private double g;
   private com.yiyiaddon.e.c.d.e b = com.yiyiaddon.e.c.d.e.SINGLE;
   private com.yiyiaddon.e.c.d.f a = com.yiyiaddon.e.c.d.f.SEQUENTIAL;
   private int aO = 0;
   private boolean I = true;
   private final Set<com.yiyiaddon.e.c.d.a> g = EnumSet.noneOf(com.yiyiaddon.e.c.d.a.class);
   private static final int aP = 4;

   public e(com.yiyiaddon.e.c.h.a var1, com.yiyiaddon.e.c.g.a var2, f var3, h var4, com.yiyiaddon.k.a.a var5, int var6, int var7, double var8) {
      this.c = var1;
      this.c = var2;
      this.c = var3;
      this.c = var4;
      this.d = var5;
      this.aI = var6;
      this.aJ = var7;
      this.g = var8;
   }

   public void a(int var1, int var2, double var3) {
      this.aI = var1;
      this.aJ = var2;
      this.g = var3;
   }

   public void b(com.yiyiaddon.e.c.d.e var1) {
      this.b = var1;
   }

   public void a(com.yiyiaddon.e.c.d.f var1) {
      this.a = var1;
   }

   public void c(boolean var1) {
      this.I = var1;
   }

   public double b() {
      return this.g;
   }

   public com.yiyiaddon.e.c.i.e a(Map<com.yiyiaddon.e.c.d.g, com.yiyiaddon.e.c.d.b> var1) {
      if (this.c.v() >= this.aI) {
         switch ((int)com.yiyiaddon.m.b.a<"s3t5m9vqx3unow","3eCp3TMRyVw11kxjqMItY6Bj84c/aQtJs2qo3ER1V5g=",-2000629961256942206,-5625657580057868819,4221387386838730652,223072759037240501>()) {
            case -1698646941:
               com.yiyiaddon.e.c.d.b var2 = this.a(var1, com.yiyiaddon.e.c.d.g.POISON_STORAGE);
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"shb64jaorqtdu","LG4nHrOCHqdieS74rkG3QzKuhC04sp1vkdVDXtdU9u4=",-1492870552275843743,-3449359970539606566,7097126128394344406,-8935095719381112426>()) {
                     case 1793085870:
                        return new j(var2.a(), this.d, this.g, this.aJ, this.c::a);
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      Iterator var6 = this.c.j().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s15aeonl7buym","/6JCb+uNqROBbqv+FPVCqHzjrW6rpJpocLw7NU2vK9Y=",-7639697361254445291,-279042588811183211,-6910694907779930317,6791714488527898298>()) {
         case -1321771748:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1sftbghayz12x","iWBE6GW1Pjor28Toa/NwSj4D1NAGepEhurVt7jSx1U8=",4198552464828713188,7680602621615502261,2466907368444181113,-6187481931250538278>()) {
                  case 1613285305:
                     com.yiyiaddon.e.c.d.a var3 = (com.yiyiaddon.e.c.d.a)var6.next();
                     if (!this.c.f(var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s16bwhzp29kn7s","BWlHpL9klcsTZzsc2ej6dhsiMOgRg3lcM2Cxk2Hpzto=",8222959135028881644,-8428171319896780413,-193409856515289483,-4493067042695573057>()) {
                           case 827996667:
                              this.g.remove(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"s1uqwv40xtpgpi","oRpMYEDtMneLXkrWrLM0JBWi3GcVTaNi50DT/ZjViDo=",-6925678444911504662,-7195864073682197967,936071028371680753,-7148711462673850892>()) {
                                 case 1302350839:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (this.g.contains(var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2eyl5aw0rt3gs","mX6Ob9uvUur4qq1bFGpbqj3x7hM8RQxpyZn38Zn6T6g=",-4369415674685006389,-5058038034788389970,-7358107391332236520,3537425787295869394>()) {
                           case -368884595:
                              switch ((int)com.yiyiaddon.m.b.a<"s19j0qyzje6e30","fQaKoNyqPThUqyRU3e5pH3ni5n1jWGweWKhU8/KqoWk=",7762146556077655348,3938521372048559610,-4667230915074929099,-2881709525334502380>()) {
                                 case -1272787810:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        com.yiyiaddon.e.c.d.g var10000;
                        if (var3.a() != var3.b()) {
                           label104:
                           switch ((int)com.yiyiaddon.m.b.a<"s1o8czjezvyqo9","kpxn2nhvH55N+I56Cy4QivPQUSnRfloickVVaGhCMsc=",8838612898067871662,9051030666652146148,-835191057078631943,-7626871532806265144>()) {
                              case 825797107:
                                 var10000 = com.yiyiaddon.e.c.d.g.SEED_STORAGE;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jalj00qbl38h","r0i8mLdy6ssYd5nJ6GgWNFUfBJ4kW5/FHsXeKEpG3hU=",3669965204201235796,3570606936930219162,6838299917911969186,345382604266595197>()) {
                                    case 1411931293:
                                       break label104;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = com.yiyiaddon.e.c.d.g.SINGLE_STORAGE;
                           switch ((int)com.yiyiaddon.m.b.a<"s2c3sn4r2uxazr","R02etg8zqY4yZwCrt6JIB9WzfARKlo4bGU957sj/0bY=",-8889575164145025921,6384839527624723357,446084182585997888,2713606292313719941>()) {
                              case -325645788:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.e.c.d.g var4 = var10000;
                        com.yiyiaddon.e.c.d.b var5 = this.a(var1, var4);
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2qhagrmqe63j1","ei9JGqEIbsLJB0u392Gfc6estmf2yNzBXXj62VTqvS4=",6733408740653656909,3837801055513269958,-8689074148689986068,6788265241604387663>()) {
                              case 1455955804:
                                 return new k(var5.a(), this.d, this.g, this.c, var3);
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1sc5xf2kn1792","WTwonT/KkIVkMQcSkiQr3txAwZRdUHduWw1lPQyTja4=",-8681496352012480489,-8152763356642774853,-8293195436904709410,-7216047472924069068>()) {
                           case -2068936855:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            if (!this.c.J()) {
               switch ((int)com.yiyiaddon.m.b.a<"s216zyf3z836nc","hC3pJp1woSIFaWTRinbHR5H0wQ6i2Itc7U+iOqP7etM=",-5806098300660449741,8607259133579585728,-1406863007672965825,-7646375939910275846>()) {
                  case -1843397086:
                     if (!this.c.K()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1f9igou0t1cj0","yo1d9vuV4MMR0gN05X9JbzBBZ3It9Uqs7gIy86SOocg=",7423126216750489755,-1121870848555129694,-1035579812491881354,-4341395713453122002>()) {
                           case 1841003428:
                              if (!this.c.L()) {
                                 label92:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2v751zpp7kiui","vv92MDyLiT05aVsZDPvsdxKNIPbUzm2/nJ69rSBtAPE=",-8508447081399854691,-8396038207777013793,8391136672769784697,2371330824341152102>()) {
                                    case 655734153:
                                       if (this.c.u() > 2) {
                                          return null;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"so0t83ff7954z","Z/hHT1qb/mmBhovk+qpReP1VXhjzlgLd+2hzNToqovI=",8674419720586133713,8675290888561751662,-5097835923334569235,-4210981391421637246>()) {
                                          case 387599034:
                                             break label92;
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

            if (this.c.J()) {
               switch ((int)com.yiyiaddon.m.b.a<"s29ix7rjapoyoa","ear+KLcafgyLZ90uK5BPpLM6+kL/9WkMK9ktlM96lKs=",6033747750264449098,8864865446843053337,-3263069880783332852,5047040814084662060>()) {
                  case -114968536:
                     com.yiyiaddon.e.c.d.b var7 = this.a(var1, com.yiyiaddon.e.c.d.g.SEED_STORAGE);
                     if (var7 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s5mkaiil1novt","ath58q/wiIYDVnFG4mz8fGJ3lPgOMO37C4XNUd6VLHA=",3007167503954411390,-8441294719457438025,7966246681475380799,4282346241927335091>()) {
                           case 1792467794:
                              return new n(var7.a(), this.d, this.g, this.aJ, this.c::e);
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            if (this.c.K()) {
               switch ((int)com.yiyiaddon.m.b.a<"s36he433a0h0nb","cXdkKBCd4c7nkoqFLAAAEn7l6BQEyyDmN4h6CGrgUBU=",-6332284272227053363,-742689240162908840,7853111908414783661,1366436663381618204>()) {
                  case -252621357:
                     com.yiyiaddon.e.c.d.b var8 = this.a(var1, com.yiyiaddon.e.c.d.g.SINGLE_STORAGE);
                     if (var8 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1l3oxj8yga3ew","Bul5E3HotdEBjA2fv7BRRLw5so+G5Z0JItG5N44lwlU=",-8900527855596841266,6854940725973806719,8518696125236676223,1116657732212289619>()) {
                           case -1981570714:
                              return new n(var8.a(), this.d, this.g, this.aJ, this.c::f);
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            if (this.c.L()) {
               switch ((int)com.yiyiaddon.m.b.a<"s35ing3qz170tz","VdjB+JgRdPOyGvqDq7BeklD8TSQ5tZRK/d+cmAanx+c=",1585683687509041933,3675025435142620326,-9004400370633544193,-344175029760619911>()) {
                  case -412622234:
                     com.yiyiaddon.e.c.d.b var9 = this.a(var1, com.yiyiaddon.e.c.d.g.MULTI_STORAGE);
                     if (var9 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s20ogf51zch624","QHsB4uNRs9L7YOmq/gkrJxG5+8B9G75ZLDMND0pAsNw=",300245624836681092,-1498894123714749195,6673763297938090992,-7149346083078555903>()) {
                           case 174355992:
                              return new n(var9.a(), this.d, this.g, this.aJ, this.c::g);
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }

   public void a(com.yiyiaddon.e.c.d.a var1) {
      this.g.add(var1);
   }

   public List<com.yiyiaddon.e.c.d.d> o() {
      int var10000;
      if (this.b == com.yiyiaddon.e.c.d.e.BATCH) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2548c0gboxz0c","jsp/QNFCbgG8wJmz/kGoEPzTnq7gh+3/bFXkqzpHnxY=",519687949568310427,-1684650597158924646,2267098147329379938,-3543492721834148822>()) {
            case 775949866:
               var10000 = Integer.MAX_VALUE;
               switch ((int)com.yiyiaddon.m.b.a<"swdbzgiti4rot","SXAKN5C6vAIGBMOtz5y/QnEvFRFQcTKNWHLMGlh5Kp8=",4718590352634835452,1511984279807411082,5372647426979472258,-5577949421528863615>()) {
                  case 1734034617:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = 1;
         switch ((int)com.yiyiaddon.m.b.a<"s2muavyl25rjqc","rk/C+2YEC1zgKvWAQdvi9siHrBMfdbykYShGTv65Jzg=",829326440744049672,-2950813402651115746,5456216773985922000,-6945044702232779428>()) {
            case 148722775:
               break;
            default:
               throw null;
         }
      }

      int var1 = var10000;
      return this.c.a(var1);
   }

   public List<com.yiyiaddon.e.c.d.d> p() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.c.b(Integer.MAX_VALUE).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"se8w3zgrkbk6s","6L55+0n1B0DSePudZPtwPIEsmM6W6MmWWcVIdiKomBQ=",7758461668588020986,-5695524244810786725,-2641746432991609412,-8437580259101623518>()) {
         case -525952258:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"szv2tlukrkzn7","ClGLM4lBAhxCWGPFO48jKR5yFqyC+l/7UzvJmoiPRn8=",-1819279135343813015,-353133645439871119,-5485798534755254182,6026758536267928839>()) {
                  case 818635553:
                     BlockPos var3 = (BlockPos)var2.next();
                     com.yiyiaddon.e.c.d.d var4 = this.a(var3);
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3i2kz051cms6d","QuEI9lUs1rHHoiXvwPJ+2hqpKuliZZKx+3KXOz4Zwzc=",7209005659226485526,-6787601578480821415,-5867109875138154701,-4188595623908904146>()) {
                           case 2075663259:
                              if (this.c.a(var4.a().a()) > 0) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"sjal7gd30juwn","j0PMKJctcvucyTqxK4PUCaZUCI5SYy6LMzSBfHbJOVo=",8242003781867356694,5145444725565945964,2222288381274832679,-5359305642318289627>()) {
                                    case -1257256256:
                                       var1.add(var4);
                                       switch ((int)com.yiyiaddon.m.b.a<"suux0bxbaaaof","beWZe4XrzEPfaB1U5RYLVwK5gWi4weHprl1dkliarAw=",2167429524793146505,-2008076572398734186,-6672173925914682596,-4342354953126776694>()) {
                                          case 1356544422:
                                             break label28;
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

                     switch ((int)com.yiyiaddon.m.b.a<"s1xji86lftdojx","bcEYHI5shsBZWJwgef+N3zCTcBoDxVmbgvPzqM1Ahlk=",-6804163533104909579,5758832379209631918,1771888808004481835,-6507618107916565281>()) {
                        case -545471696:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   public List<com.yiyiaddon.e.c.d.d> q() {
      ArrayList var1 = new ArrayList();
      if (this.I) {
         switch ((int)com.yiyiaddon.m.b.a<"s3jafv1co0j2ic","G73ckjq8yDOeAhGhG7IbYyO+Sx/IDGu1DO2UHQOGNJw=",-4675319436778746645,8485567769001866954,768068768032254766,4853762069969034702>()) {
            case -1390047784:
               if (this.B()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s9fv1o0zmx1co","at2KlIewFGj/TH5BUOcYBKqvXYCu1OlpmuqiJUzgJ0Y=",-5809910651181793065,-5195187933076796389,6324314611878651153,-4851129524055664048>()) {
                     case -602814255:
                        if (this.A()) {
                           Iterator var2 = this.c.c(Integer.MAX_VALUE).iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s1zy3y9weyagz8","NccmE8iDYgGlXNQZN+704VQZbPD4MxAXf3cjeE1Behs=",2041444151884655492,3672495364718909151,7358249502433264520,-4491942672229245547>()) {
                              case -225012409:
                                 while (var2.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"swu2lxpthhdbw","Jv2bIb+CMXt73sqv5JOMEPaaZl0oVABam0WuvNRxha8=",3095708812301833666,-6518275849139379834,-3518526668488007145,2994541771785334876>()) {
                                       case -732860480:
                                          BlockPos var3 = (BlockPos)var2.next();
                                          var1.add(com.yiyiaddon.e.c.d.d.e(var3));
                                          switch ((int)com.yiyiaddon.m.b.a<"sqrsrixkxcf5o","7RYQnaGGa+pPVbiaoZOgly0bUwCvblteL9sKjnj+EmU=",-6563362706233132453,4757982921088033240,1686716431026966692,5568609725875266105>()) {
                                             case 1701964012:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return var1;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s37zeuvf9xvk5y","J6oM3/Tp8HA2r8AXRacCM+Js5/B5CTkimY4gK1ejw1o=",5813334546613903667,-7671902822324311282,2594868504988625282,3890495249512498840>()) {
                           case 630293241:
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

   public boolean z() {
      if (this.I) {
         switch ((int)com.yiyiaddon.m.b.a<"s22q9r3ip46k6f","HvqPDltKe2dCgDskcqFJnUHL5FbdtB/WF9nd27iSYs8=",-6795926747954798210,-8556791220780977486,-8951269253971006812,5914609265737970468>()) {
            case -1952624154:
               if (this.B()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1q35eyv3hffya","9ZetmUCwvpcT0WpuJ1hwC10B3bP3VSDzD6av1nR6lVE=",8650025735528707234,-4210950803804542073,-7948763174033021384,-2431562003944052471>()) {
                     case -1203610372:
                        if (this.c.N()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1woxkmzqzb251","eJysmNwSaYSnLt1ScNhL/mZMGj109lvt6ceCQUhqn4g=",-6144528592661220387,-5051024363950994312,-5552574936832849497,-3121683540699072644>()) {
                              case -1691176900:
                                 switch ((int)com.yiyiaddon.m.b.a<"sku192m3wwz4v","BcqvCj1hVBJTPQZ0g/3PQoN5MDfS4l3gEmXpmeB1y94=",-7079703814200323422,9006473705601072071,8386846270803218441,5862758756366645549>()) {
                                    case 1172361832:
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

      switch ((int)com.yiyiaddon.m.b.a<"s42546hii4nuu","CpB874RqCcAa41Q/Kf1bk2x+hmeoED1+NvFxB+DlA8Y=",-364488780292427244,-5482394307367938177,-4872577534931093035,-7754830555350062617>()) {
         case -1078842102:
            return false;
         default:
            throw null;
      }
   }

   public boolean A() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mtyrwucxr6ui","rH8QlS/Q64QfJmkRrnRjjDj40K1DCwxVhIAHws/4fvM=",-1261548639275833970,-3931022445973665748,-4737424301524013912,5913178077352246980>()) {
            case 1901521491:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = var1.player.getInventory().getContainerSize();
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2y75vsw4cv6bk","fEAYmuBUEM5aKpbEWhEutolF/bMx8L3/zAqcvt0/xLc=",1085891416454542395,-400540989772053466,4775803030797268160,-721186424693656617>()) {
            case 1126176393:
               while (var3 < var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1tfmud8mcz9sr","jN5fV4QcqlNaA22Ek71T4Uq6PkhzbsozS8BUjRVg2s0=",-7302923716343420653,-2863514182279751594,-1860243294093930152,3425101936845582778>()) {
                     case -1529094140:
                        if (var1.player.getInventory().getItem(var3).getItem() instanceof HoeItem) {
                           switch ((int)com.yiyiaddon.m.b.a<"s21izvl97dckno","jvpYC6/AUL7rswocq/jJlf7mKpOzfS7BsIXNFoDcmw8=",5751087971138003066,-1217870174578316545,-9145669634434152687,-4366374993351003142>()) {
                              case 1477370678:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1jowxt7ksfr3r","UWqs6IJIjjuw7UThL69LnnTsG1wKTBtIF9Z1gVssUn0=",616105087511369819,2855025483600490928,264968266877825443,-5601686008172818478>()) {
                           case 199638667:
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

   private com.yiyiaddon.e.c.d.d a(BlockPos var1) {
      switch (this.a) {
         case SEQUENTIAL:
            com.yiyiaddon.e.c.d.d var3 = this.b(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s12l8k2xnpd2z2","IGD/uBgMf09Zr4BdVfOfb6RBVcZBb9UEPK0U9ah3GWY=",3870132371137000839,-1974892011252159614,-3919349908011669880,20099233655272989>()) {
               case -1788168635:
                  return var3;
               default:
                  throw null;
            }
         case ROTATE:
            com.yiyiaddon.e.c.d.d var2 = this.c(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2wnhvm4k4az7a","FmhHEIG2SL9VGBXbGYDiJO7/fIWSRE5KogjitaqsaTQ=",-5330292521228451890,-3176558599159423578,-2985208611867319945,-8210415291527647839>()) {
               case 610341760:
                  return var2;
               default:
                  throw null;
            }
         case FOLLOW_NEARBY:
            com.yiyiaddon.e.c.d.d var10000 = this.d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2pzfvdgz9mp6b","9LKuPsR1Fxihyibv4wmMYDiL1KO7rBYKMiGMB5prnRk=",-723663363061423231,-7452168985060411724,4344161603865600516,-3304135190793045149>()) {
               case 155990519:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private com.yiyiaddon.e.c.d.d b(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      Iterator var3 = this.c.j().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"seub8f4yc19f9","+7ORNfLOerZoY5uhIFFyZ0uGrLRkhEbVJYIvoB4lKZk=",-4859347046025338101,8122859477196724176,2375517116044702537,-7354057134581260086>()) {
         case 778519777:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2mzps30tj6q1a","RskArABXqNngni89tmAQ5Ig07HVVdfvRwrQd/UMetjk=",1419730086976124155,-6280175947209001910,-682997098654788015,6396026161905045959>()) {
                  case -1072159799:
                     com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                     if (var4.a(var2.level, var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"svtccypvhry09","nkDjiTCJ4XqUT3G5HbIPPDOyq0O7+JcxtFuojony68o=",-796628655343273414,-5319040135978444933,-1210898213053857358,-4629302088256478612>()) {
                           case -1984570730:
                              return com.yiyiaddon.e.c.d.d.b(var4, var1);
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1ekg0p1sfszqv","CXrdWAuuKePzNfVwBscrD3uuIGselHQySm10WMMPRyo=",-8990695708151232494,9110260704527383714,-1309964189683176113,-9036500478782868667>()) {
                        case 1209296312:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }

   private com.yiyiaddon.e.c.d.d c(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      ArrayList var3 = new ArrayList<>(this.c.j());
      if (var3.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2caf1kmrlj6zm","4GWYMc7xv22cvETioVxwWql82hysQx84TZbVT8pVmJw=",-2225097276942743395,-2812449978742818609,2951697557669430457,1106192534832553636>()) {
            case 1978735436:
               return null;
            default:
               throw null;
         }
      } else {
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s39wubbsqu0qgr","34JKHzF1sp+9aTyCSmBnw9+ujJUc/rlQX2v9zlgqiHA=",-2739166760450380624,7995402788205039122,1891651662394871506,1796594064813760826>()) {
            case 693909493:
               while (var4 < var3.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sinm1zwl0ac3q","zwBC2VeIjOtU8khgje4g28IIEGIQEzrcWIBLbfxOLhg=",-769783336979599708,-2235508037272802794,1777133174184102051,2252292474643236908>()) {
                     case 620497878:
                        int var5 = (this.aO + var4) % var3.size();
                        com.yiyiaddon.e.c.d.a var6 = (com.yiyiaddon.e.c.d.a)var3.get(var5);
                        if (var6.a(var2.level, var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2hijmjiekda4a","pNrN72N3y3w4hPOPWzzXQvvb9klus17wN005aMuR9cc=",-767060221100045297,-2896234633786606022,-2072380921259171407,5970439578208888443>()) {
                              case -700971506:
                                 this.aO = (var5 + 1) % var3.size();
                                 return com.yiyiaddon.e.c.d.d.b(var6, var1);
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"sbx0x6kxbhvpv","tbrr5rWshys2RQIwp0iv2qJLD/CjVwBaSoqYBKT9GCU=",6691925795077575264,-1011608416400771856,7018447571895275486,5518075662357720946>()) {
                           case 2036421743:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return null;
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.e.c.d.d d(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      Set var3 = this.c.j();
      EnumMap var4 = new EnumMap<>(com.yiyiaddon.e.c.d.a.class);
      int var5 = -4;
      switch ((int)com.yiyiaddon.m.b.a<"szs17cb5ww1og","gO84H74b6YU4fo9Gn8h+boEkn0CNOek+B1fB6teX/rk=",496488486811003751,6755764173912223167,-5648942054764845740,2915304475335723481>()) {
         case 1245815276:
            while (var5 <= 4) {
               switch ((int)com.yiyiaddon.m.b.a<"s21ogl0dwhw2iy","+IE8chX/6ZMy9zweBBMDnoy68wC336iiHp3lsrAKBeI=",-8244707992100805268,-4825510997010563501,-3185558025674270766,3666213663425964236>()) {
                  case -1671222987:
                     int var6 = -4;
                     switch ((int)com.yiyiaddon.m.b.a<"s19r66mqnffwet","Uy+9Ky7ZakVmfISlPhPDYxabMPy9c9nU7O8TkOISZNM=",8433163894449918788,-1711194557684001813,2094661620066610484,2299238357421196042>()) {
                        case -914226265:
                           while (var6 <= 4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1wplcmertjqrs","/A5uXi1hzDwPOO+ViK9UGNnTpa3fArMBuQw0Bxz1eac=",5410932851718289966,1710803064064270263,-7820742387416914276,-549785868990204318>()) {
                                 case 1175499378:
                                    label118: {
                                       if (var5 == 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2dcypmhu8byez","y6/1AZ8pYdFxfGKeYKugrgwXW8Knlnz/RXJ93JcwIxw=",28405763056068780,7219806348556949138,-8810160028233682771,-429447945074483665>()) {
                                             case 1085430084:
                                                if (var6 == 0) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2dxzqzkw0xs69","RvdK6FKP4/p34DcRXLPGI4PG+iqN5oJhT0/hJOKi468=",2492339233982788707,5786795937485865838,-9102197338648541911,4832437379799362311>()) {
                                                      case -1004595606:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1ae98t4cuvzky","evwA61q1/P43vB8IQt7Cd4FNTbIScUQtVum58coOy/g=",-6724657344558582216,1579115508670955490,5327375306989794204,-5629575248313422024>()) {
                                                            case -483559386:
                                                               break label118;
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

                                       Block var7 = var2.level.getBlockState(var1.offset(var5, 0, var6).above()).getBlock();
                                       com.yiyiaddon.e.c.d.a var8 = com.yiyiaddon.e.c.d.a.a(var7);
                                       if (var8 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2qmhexsj35g2l","dKNt9k1jeoJoRymSkGSvsq4LWAou/FeW4d+n4HVm3YA=",-4079320202245129125,6662154601025107881,-4897599134417548988,-7156699457933925486>()) {
                                             case -1829999461:
                                                if (var8.F()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2mtl1wvjzuqw","sjIJgxFvmqYtWjifiVtTSU5VD658SuJxfG0GDRGVl/M=",-6960346171991655753,4365454771227181895,-3971464735379196843,-9165947344818782190>()) {
                                                      case -404322658:
                                                         if (var3.contains(var8)) {
                                                            label85:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1z5spxrat88la","eS7Xz7Q3HllQA7FNNVCDXk4+0fy3/ZW/nSRYXVuxAEM=",2585887276455308963,-5061675094762568390,-2357884310401031949,2789664450199168294>()) {
                                                               case -743462473:
                                                                  var4.merge(var8, 1, Integer::sum);
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2jzhw5cyu7blj","BDw9A6FsyMYUWlt3/ZpHz8mveiObOzXHV6Oad9HO4N4=",3643968703328619831,4966570520153107267,3320078915696499118,-1819361107301521774>()) {
                                                                     case -673595573:
                                                                        break label85;
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

                                    var6++;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3e2avnlwpe5zq","dJaXPnP85umWA65ePhvycauadMtFJnYPbYdfvlRVswY=",-1585847126682507841,-3979853793968919091,5957744597554471977,-7516361870697621016>()) {
                                       case 785918465:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var5++;
                           switch ((int)com.yiyiaddon.m.b.a<"s1q0b4rrmsgxz5","YELuSt+OSivyHyGWlGR+xydxRF0XtlvHTDnCb09iRz0=",4734531140410019007,6578627406607681228,9113408485747767693,4607020515486585493>()) {
                              case 1398695126:
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

            if (!var4.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"syp827u9w0kff","1T/nykbLS1fiJ4fIRU5i+eyAvUMGMROpJWiekSD3yDs=",-8426525031130011733,3715233620790245015,1904743890098251843,-9066593401728583106>()) {
                  case -1543903156:
                     com.yiyiaddon.e.c.d.a var9 = null;
                     int var10 = -1;
                     Iterator var11 = var4.entrySet().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s2iz9k1jkwcpjb","IA+5qXohIVYYSi82iS6ERonBVRgI/xKdRLfkcR6Udl4=",-4080563051828784728,-5793584584813760287,3998623106387993879,6139636437048560262>()) {
                        case 871720774:
                           while (var11.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2q8fj8omwhiy7","okDv/HNB9sZec1OI/mPjTPlqtIxqDmwMiGeLDlikzXQ=",5299323911117776383,2655495999559493227,1193741869577949330,-2352753536137698430>()) {
                                 case 1265148864:
                                    Entry var12 = (Entry)var11.next();
                                    if ((Integer)var12.getValue() > var10) {
                                       label72:
                                       switch ((int)com.yiyiaddon.m.b.a<"str57dmbb189s","6QAuo8veZTI8njZRyeJHvgr65RTPtLX0ZbuztGQY+AU=",-6608962809093291693,-8531295042351135672,-6661256251098104375,-6082131980094683501>()) {
                                          case -1492106610:
                                             var10 = (Integer)var12.getValue();
                                             var9 = (com.yiyiaddon.e.c.d.a)var12.getKey();
                                             switch ((int)com.yiyiaddon.m.b.a<"s1jok1k2ut0puu","/hYedal5F9ZO9XUdHVm/HhnRMONlmeIPHLTophkhfk0=",-156323829617119964,-1429172823706148891,4857752661388610842,-3506901087253000737>()) {
                                                case -1251773791:
                                                   break label72;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1vvtvn2aixatv","10LzK1c99UQ+bVBsMxB/At6+JatVfzDIVHAVw0w33Wo=",-3033795433916606931,-6130893670145279960,-1959210677465152684,-2719783976025677251>()) {
                                       case 815098030:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var9 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3ez3ytps19g4a","zNyPbx++z3IJ0VrYyyjL6Wkd4a9626hO+7cBppTV6RA=",-6277011455714818987,2475282731295402351,-8467842882672082428,-6144250083927566109>()) {
                                 case 339283067:
                                    if (var9.a(var2.level, var1)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2opa9rzu6zk53","zOwF1VuOooj6yI8Ti8xLqkrvezB7ZUF+sWNl7AtU1AA=",9067179449143559314,1318051865046257144,7862278577009250363,-8682913325371054978>()) {
                                          case 927034720:
                                             return com.yiyiaddon.e.c.d.d.b(var9, var1);
                                          default:
                                             throw null;
                                       }
                                    }

                                    return this.b(var1);
                                 default:
                                    throw null;
                              }
                           }

                           return this.b(var1);
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return this.b(var1);
         default:
            throw null;
      }
   }

   private com.yiyiaddon.e.c.d.b a(Map<com.yiyiaddon.e.c.d.g, com.yiyiaddon.e.c.d.b> var1, com.yiyiaddon.e.c.d.g var2) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hulx0o16rv5v","ckHoQ8Pz2NXG3svMe1cw8Xa7wfgPunOW3Iu78rtLuU0=",2169786373171430435,-1356365462561341513,251425971778196983,8099364893002074539>()) {
            case 1661279943:
               return null;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.c.d.b var3 = (com.yiyiaddon.e.c.d.b)var1.get(var2);
         if (var3 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2mj88a93zri42","zFuasKxW3y4hOqFuUQUFP3h504HzblGjkJMT1JYuoko=",-2290811902613051260,4672664092802487549,-5077236097419172073,6862763196538090060>()) {
               case -9707876:
                  if (var3.G()) {
                     if (var2.H()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2gj1cmymsn3w0","jXVtMcuGUuehXKKRS7iWIXOJL6iudS0NrcFkE2oAMyw=",-9134711217946912963,-5592627063610182458,4699267286189817405,3451977861302926783>()) {
                           case -415051880:
                              Minecraft var4 = Minecraft.getInstance();
                              if (var4.level == null) {
                                 return null;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1g2ct7d26ctt3","YA+Pbu0H1LUg8JpB6yGwxjUZo0PdLe298Cc5sCnznqw=",7914497983120864719,2488088100399807167,3927206606940619188,-6560060174674680771>()) {
                                 case 693050312:
                                    if (!(var4.level.getBlockEntity(var3.a()) instanceof Container)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ijuzgmvwgare","YZJxDOQsiaYNqWvod23QWOWg7TxOJFBN3hpZdeajZDU=",-6270385910545156102,5763545463045686183,3585136566198105869,-4127490377273972991>()) {
                                          case -1449146389:
                                             return null;
                                          default:
                                             throw null;
                                       }
                                    }

                                    return var3;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return var3;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sujijrz6thix","CBXyBd7PYh+zVveV5MzLNqoUSfx3QZ5s7no7pB/O6Ys=",-1470860759969069680,3956828335077950006,-2525189058080162237,2351876119504435815>()) {
                        case 1307000698:
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
   }

   private boolean B() {
      Iterator var1 = this.c.j().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1egzqumeoyqaf","e04PpSmaqdloocmh7F1e/xBawgHnza2aJ00P4KdNCks=",-5233477447256639521,-6008941228005571717,-5299054524990660640,8255903306578482825>()) {
         case 592812947:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s5l6mcjy3atd5","I1z82UPVXZ+7TfNq5Zu8Ak7WooHjQiutpEfO1m8zctA=",-8649899225791435221,4423153531439057183,5281812650691877224,4008815203874898845>()) {
                  case 672584827:
                     com.yiyiaddon.e.c.d.a var2 = (com.yiyiaddon.e.c.d.a)var1.next();
                     if (var2.b() == Blocks.FARMLAND) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3n9vy5rjdv3pu","32LfSzSCkkLg/U/RBf4lAwdHzIjVdvwwXey077qFrVo=",2061156127070191357,8054419669256948007,7913631199557371155,3070884018339083876>()) {
                           case -1481589891:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3cvkrdi4frq3d","6foik+BTeW4nCFaKrqqalrhzG2A1gGeIt3zbH1kKD7k=",-9142006617767448335,2178639211843995490,5110446487635198783,5180141202511656666>()) {
                        case 1165772154:
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
