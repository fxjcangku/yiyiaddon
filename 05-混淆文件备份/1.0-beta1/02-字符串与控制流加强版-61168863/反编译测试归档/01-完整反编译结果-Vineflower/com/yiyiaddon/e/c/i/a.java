package com.yiyiaddon.e.c.i;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class a implements e {
   private static final int aU = 2;
   private static final int aV = 2;
   private static final int aW = 32;
   private final com.yiyiaddon.e.c.c.h d;
   private final double h;
   private final List<com.yiyiaddon.e.c.d.d> p = new ArrayList<>();
   private final List<com.yiyiaddon.e.c.d.d> q = new ArrayList<>();
   private final List<com.yiyiaddon.e.c.d.d> r = new ArrayList<>();
   private boolean N;
   private int aX;
   private int aY;
   private final f a = new f();
   private boolean O;

   public a(List<com.yiyiaddon.e.c.d.d> var1, com.yiyiaddon.e.c.c.h var2, double var3) {
      this.d = var2;
      this.h = var3;
      this.p.addAll(var1);
   }

   public List<com.yiyiaddon.e.c.d.d> r() {
      ArrayList var1 = new ArrayList<>(this.q);
      this.q.clear();
      return var1;
   }

   @Override
   public l a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3n83u6wufrirp","UjKSt86K5A+PjbgsEEFmCql3jsaXIgvpxCHZk56syHw=",1882564775965337404,-1586500657063959833,-7822290467679417589,3675115403267978809>()) {
            case 904248757:
               if (var1.level != null) {
                  if (this.N) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2170txzzxqu71","XZzI8s1qyq47R+5SvhAKKwdMtdE0v8LhU8yC6zi5q5g=",7141449558748965581,7273579777143590746,4611967429239273740,1424221841175954506>()) {
                        case -311164861:
                           if (this.aX < 2) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3cczpig5e5sxn","WCibIO83BSyqC342Rdt5olYui1Iv67peoIScmLnmTiU=",-3548107697054357248,2092287012766130961,8663620174518957516,-1701532834494023933>()) {
                                 case -1709785118:
                                    this.aX++;
                                    return l.IN_PROGRESS;
                                 default:
                                    throw null;
                              }
                           }

                           l var2 = this.b();
                           if (var2.S()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s11eoe067u9r3j","RylyjzDGAlxgTPJR9H+5w+VUBRdhTNwsQ7EsYxW+2kk=",-8321336251522275978,8708871090179950524,-6907339337468372940,4472788065121086677>()) {
                                 case -844373153:
                                    return var2;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.p
                     .removeIf(
                        var1x -> {
                           if (!this.d.d(var1x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3mvjdns36pew","xo13wmBVk353OUCEwfMA4b6lPzEWiqtFcAaBk5UBdS0=",-397069473175481994,8723184882622809246,5900769981747537540,1800920742712910184>()) {
                                 case -2094590917:
                                    switch ((int)com.yiyiaddon.m.b.a<"scq357fotmd8c","el7VuIkNu6Dfr7qW2C7OVGmj9/4qv43YZrP1JiYpNC0=",8516407121639657900,-7687080643427132794,-41333617304680889,711329896830861270>()) {
                                       case -1728779452:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s7slns5nniql5","qzSkhFtk1BkkqbSnPUuK6DBWsTeHam9lbg/J+a9AIN0=",4060539712641555022,-2560067037414521831,-4929384106416741746,8209517983788832918>()) {
                                 case 1674254462:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     );
                  if (this.p.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s4i1zqz4fd538","WLf3r4fOJ7R0A2AcsW0Lmh2aRPk9n3hBhSeRIzAY5iQ=",1894052439250943596,4756761422780783119,-3308151254743116492,-4136520054940575682>()) {
                        case 1367592304:
                           if (this.r.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3780664q7vtek","zbFsD/S3zRuo5dsl3N4HKMo4LcNPWizPisVajb0IA6U=",-5792809157620634667,1436176065122137838,-4541477867603380502,189328258714936276>()) {
                                 case 551373822:
                                    l var14 = l.SUCCESS;
                                    switch ((int)com.yiyiaddon.m.b.a<"spl4ynspju8lz","b3ZQPFpqnN01Y1K4Y+Piu8zJ1O79Vjx9v8rzyrOS0h0=",-6890689992656189026,-9139770927610299832,-310456379503300649,7831184877742321588>()) {
                                       case 1801282770:
                                          return var14;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              l var15 = this.b();
                              switch ((int)com.yiyiaddon.m.b.a<"s3s2ncg547k2lo","fOr9UaRMgeaVOX63aNgKSaCvHOexa/uELRPb3L9th3A=",4629483275363647388,7528868141745175724,-8683582803792147334,9091982111932362043>()) {
                                 case -1283896927:
                                    return var15;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     ArrayList var10 = new ArrayList();
                     com.yiyiaddon.e.c.d.d var3 = null;
                     double var4 = Double.MAX_VALUE;
                     Iterator var6 = this.p.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3vcu3bslcsbbj","1bBqgDoLZpHRy5DA1wIF0cMerWorVJUqpx8QtXs6NTI=",6239646837858535848,-2197705439725269623,-735939465322138884,-7510318349281487037>()) {
                        case 1743788984:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s354oz6f8z3lqd","JyWSnG8YDv7PL4djSy9AZMKTUsyCb88sQopk2CGXnqM=",-2296412844302024292,-6051699052011472216,3054503075766453874,-2087924316380661226>()) {
                                 case 1393822793:
                                    com.yiyiaddon.e.c.d.d var7 = (com.yiyiaddon.e.c.d.d)var6.next();
                                    double var8 = this.b(var7.a());
                                    if (var8 < var4) {
                                       label133:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3febt0nd9hdyy","k8NMDTkOUZvElgC/K2X+i7OANvqodrJyhad03+tZHZg=",4045440259288313509,-5032238973267699243,4153160543388962978,5442338259749051296>()) {
                                          case -1301769535:
                                             var4 = var8;
                                             var3 = var7;
                                             switch ((int)com.yiyiaddon.m.b.a<"s165qq37nbl9pd","LHAEwXIaA/K6xleYN6eGffFN2vl7T3OQTnr356ch2kY=",5078173233979848415,-2873570863858317168,-8549035267104673796,921093832321945094>()) {
                                                case 2057537009:
                                                   break label133;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var8 <= this.h * this.h) {
                                       label128:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ab0q92wcvpa0","0j5ITbaoqB+OeaF4O6fRA9/Zhhyioc0WPuXP1YltF/I=",6895440298981303669,-7684110086352559939,618970232288630433,-6064470235761957072>()) {
                                          case -96406327:
                                             var10.add(var7);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1fjp066nql9xp","CZclFZB4RGXW2viMhtpNQ2TkjR55DL1IHlI7nFP12kg=",-6423352537259631128,3733175851180822416,-3815785573772120526,-5812756905490845618>()) {
                                                case -404712597:
                                                   break label128;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sn0pgsi6igxc7","3r7rGr8YmxKXXgOg2GataNCazPy1a61wgpkUbciNBpQ=",2899700764956463614,-3480917415895956195,5716216593447408257,375314927370915985>()) {
                                       case -121394606:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var10.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2vn65hxu90sj9","1tX0BfZQzXCn2RMWFeiBm+RvNEq0PAcfiClYf5Xq0kg=",7855674084794679087,7613716400149120538,-9221456155550048651,-1620597827530450504>()) {
                                 case -718141697:
                                    if (!com.yiyiaddon.i.c.a.ft()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ca6jbjuy2sg9","Y0i7lv7/cm6s45HG+CmkxmXWa1bfbwlP5c4d2Ik6j1Y=",987350057156358076,6643000503826116458,-8850943994902733513,4703626171969624753>()) {
                                          case -1509002014:
                                             return l.NAVIGATION_FAILED;
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (!com.yiyiaddon.i.c.a.cX()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3vccwyr7h6tww","G6iisBD1K6dVpaM06ggZwqMeefuGChGJACRtWPsjM/Y=",1204330083743576303,-7827710534781741791,-2313522309771576896,7474050553698698720>()) {
                                             case 1940349975:
                                                if (var3 != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3pgrdxduvfu8h","MgGKAf/7omXdMFBMUTbyk0+f3VLg65EpPIiR66u3jgA=",8579326766456713050,-1046470290580986503,8426834291278845138,3761931245919081431>()) {
                                                      case 1998164371:
                                                         com.yiyiaddon.i.c.a.b(var3.a(), 1);
                                                         switch ((int)com.yiyiaddon.m.b.a<"s29e1x3meder53","FT3QwkllIZdLa63aC5JnFcL6YuUkolf2NXCqHDtYkLg=",-8914675418743448238,-17648369779929200,-8986523749658720044,-8323788647541458670>()) {
                                                            case 285827992:
                                                               return l.IN_PROGRESS;
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

                                       return l.IN_PROGRESS;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              com.yiyiaddon.i.c.a.i();
                              if (!this.O) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kp2q86p6k1pe","0txhVs5ZxugvUfda2c9Y2Nhi2Nzpy1DbdPX6M3nVH48=",-3173912167858058211,-5212848094154735300,-4589680532904954161,2942251784583998355>()) {
                                    case 1962510702:
                                       this.O = this.a.Q();
                                       if (!this.O) {
                                          switch ((int)com.yiyiaddon.m.b.a<"st471xe8mvyad","uX2u8Og3skTXjXNczLgtrrQ5CJQ4H/N3lmukDbDZmYY=",-9033766900618226535,-2036006876208718701,8950753851468314081,-2386669362513854420>()) {
                                             case 1099837917:
                                                return l.IN_PROGRESS;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              int var11 = 32;
                              Iterator var12 = var10.iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s3elkvsp0rwfi0","PieOHJ/j76OJ/7x39ssxr8YPdEvYL8cAbjYqp+lSNeE=",2324407679153228418,-1992797297182992533,5774554846129329636,-3927657684514059113>()) {
                                 case 1765152500:
                                    label157:
                                    while (var12.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s150zd0059mdzl","Pos7isanKEsI3OuwyNCDbWPQDONmjQ7bUap9xz4mXTc=",6535183023209247828,4852358671703404970,4396522585653962149,-2081699982474619653>()) {
                                          case -702572649:
                                             com.yiyiaddon.e.c.d.d var13 = (com.yiyiaddon.e.c.d.d)var12.next();
                                             if (var11-- <= 0) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s8l72s1y0n2u4","Gifo9oXAchlRdIGAWmuwP27lhJtrHLxMPibS/5JEd+M=",8048579756745540428,3121112117933380109,-4648863507575349042,300095675669002695>()) {
                                                   case -722418316:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3nrxt10xf84g9","T6kdQvQ6EP0T56zfNfjGtO0tnTO45OcoGPMAn0/iN3U=",-8867457271197834339,1392740968936890399,5830118004030364756,-3916613343880951992>()) {
                                                         case -1155583746:
                                                            break label157;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             com.yiyiaddon.i.d.a.d(var13.a(), Direction.UP);
                                             this.p.remove(var13);
                                             this.r.add(var13);
                                             switch ((int)com.yiyiaddon.m.b.a<"s3tlucwv3dq9fs","RwOs6Uj0dFtT9jhQugC6x9LFwLLyTfVjQNMl/qcEjtc=",-2467411382757755516,-6452190416706119103,264020054697952105,-908594826001030554>()) {
                                                case -1510978065:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.N = true;
                                    this.aX = 0;
                                    return l.IN_PROGRESS;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"se5q69ikybdv","GQAgiOb03u2V2HuI2pRgEx42sfb0VZDE7iyYGE7XTW8=",-5652386635990131534,-5334191269158741276,-2229082486242621484,-630322637335418849>()) {
                     case -315220139:
                        return l.IN_PROGRESS;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return l.IN_PROGRESS;
      }
   }

   private l b() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.r.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sgpy558rmu8r8","vY53CWwWP4IMnmhG4ZOQ1S/ZtrFFL6VkgtZdjYW7y+o=",-1641502726232371367,8226000041566925739,-1791847623498530741,518214223711030604>()) {
         case -1656083614:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s31ho8fotpo1d8","o4P/YGbH84GRPLrMXoxkdXtS7ZjCukMFWmUb55IBqtE=",8100291223221186585,-2809011048608231773,2681510993802157747,-8792265415651402673>()) {
                  case -967055053:
                     com.yiyiaddon.e.c.d.d var3 = (com.yiyiaddon.e.c.d.d)var2.next();
                     if (this.d.a(var3)) {
                        label49:
                        switch ((int)com.yiyiaddon.m.b.a<"s16vfgau5hssly","7mVe4QYVNNe2wdA+2C3eu22S22LC/LrXEn7abCANmso=",1837317566250512903,1901625899200349283,6009649687763004627,-4520982058094012952>()) {
                           case 1791761924:
                              this.q.add(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"s3th8rg29qh3mg","saLrHQ4gWb/JBBV5xVfJS9VfxbQIS2k0Xrii1co0REc=",6790803619213167321,8770062410914610718,4849717751637918994,-1885941977342482399>()) {
                                 case -1115070638:
                                    break label49;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var1.add(var3);
                        switch ((int)com.yiyiaddon.m.b.a<"s7aw7qlap5oka","q9y+0fMLhGzZlL1znHO9D+Tf2t76+7WrO6C1LMpI42M=",-2437512563006958526,-8001842861503233674,-452329289898595815,-537189604435578725>()) {
                           case 1265666719:
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s24spzzvbbtbw4","WGNQV7Zv+ZKoYMWeZmgo4Tj8dcqPzAEWDI2/JOCHFLQ=",6506134619864988082,648964148824848103,-987310342826364477,-5335507297460231242>()) {
                        case -983715772:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.r.clear();
            this.N = false;
            this.aX = 0;
            if (!var1.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3j0tz74u2eelt","G7z6Npc8HJx4vDI/T+VVcUwRM5UB6bKQ8l4XjRUEqi8=",8887804337593622377,2659905544389083773,-8135442734300219340,-6218375649790081949>()) {
                  case 1059540960:
                     if (this.aY < 2) {
                        label37:
                        switch ((int)com.yiyiaddon.m.b.a<"s1mxh6mqsuhz0j","iDl8kVeFAAetQjp2o6MFAW5xEPOlE/TXHUH2Bluv97g=",-5347343990118973711,-1618532210634269834,1231499106996974763,-3325778560860310747>()) {
                           case -1052108401:
                              this.aY++;
                              this.p.addAll(var1);
                              switch ((int)com.yiyiaddon.m.b.a<"s3p7oh5l8i8qu9","dvuEEn6dRM5EsqjLTJbhsG3eqkhcnyg/F/3hNJY5UvQ=",-7448110718263946789,2606951526080385480,-1212706506795160281,8305603430895844540>()) {
                                 case 976151001:
                                    break label37;
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

            if (this.p.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2g31opxsgdgnn","hXF+Hwh5VrFtM5JVOEUQWtkcxjnxPLJP9nMbIvREONU=",-2599049390708680918,-3073195371465730786,3535614617280251841,5998498882713672178>()) {
                  case -1971446883:
                     this.aY = 0;
                     return l.SUCCESS;
                  default:
                     throw null;
               }
            }

            return l.WAVE_DONE;
         default:
            throw null;
      }
   }

   @Override
   public boolean O() {
      return false;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
      this.a.ax();
   }

   private double b(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      double var3 = var1.getX() + 0.5 - var2.player.getX();
      double var5 = var1.getY() + 0.5 - var2.player.getEyeY();
      double var7 = var1.getZ() + 0.5 - var2.player.getZ();
      return var3 * var3 + var5 * var5 + var7 * var7;
   }
}
