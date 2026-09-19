package com.yiyiaddon.e.j.l;

import com.yiyiaddon.m.b;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class a {
   private static final int kg = 0;
   private static final int kh = 512;
   private static final int ki = 2;
   private static final int kj = 8;
   private static final int kk = 20;
   private static final int kl = 3;
   private static final int km = 20;
   private static final int kn = 40;
   private static final int ko = 10;
   private final com.yiyiaddon.e.j.a t;
   private final Minecraft Q = Minecraft.getInstance();
   private final Deque<BlockPos> f = new ArrayDeque<>();
   private final Set<Long> G = new HashSet<>();
   private boolean cS;
   private int iT;
   private int kp;
   private int kq;
   private int kr;
   private int ks;
   private int kt;
   private boolean cT;
   private boolean cU;
   private boolean cV;
   private int ku;
   private BlockPos w;
   private int kv;
   private boolean cW;

   public a(com.yiyiaddon.e.j.a var1) {
      this.t = var1;
   }

   public boolean isActive() {
      if (!this.cS) {
         label22:
         switch ((int)b.a<"soy718geadxm7","o9A4GTGVFCbeVwU07mlJ9waozqBul50zGbE8n6Od/wY=",-423986990093669003,-4810198053801597076,5616671685503681197,436631044515545000>()) {
            case -522406931:
               if (this.f.isEmpty()) {
                  switch ((int)b.a<"s7lx914rqw8qr","wZOH7b17PP1UoYd55ogb/+3dd+iyzsHOjtUVtEh8f8E=",-4573486206365661762,-1453529041703099283,6425254828202860297,5413555241442329341>()) {
                     case 6697338:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)b.a<"s12qj9eg3teosr","d7rVNXbcErG+UpiryGtPOXd7GMMoRsy7eIXXchfTWT4=",8672764337353940152,-3010433854492842611,-8795715838460552154,1155288906800502112>()) {
                  case 778855361:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)b.a<"sk6spotntu6nb","0kEXOIdi1XoSufI89XYPRrX2tirFG14KwNMIT0znAAQ=",8178138641102372871,5181402522242815192,2427881738247663987,-5328109292822238493>()) {
         case -1754242735:
            return true;
         default:
            throw null;
      }
   }

   public int H() {
      return this.f.size();
   }

   public void a(com.yiyiaddon.e.j.a var1, boolean var2) {
      LocalPlayer var3 = this.Q.player;
      if (var3 != null) {
         switch ((int)b.a<"s2edxyatdop8z3","N+43Xy0WVHQ0qjSk+qPLuvjL6bkbO3hlkUXEIHdHyDQ=",-1507970959749764350,-92541976534589063,3576674888273890091,-9144134035684461221>()) {
            case -1766186417:
               if (this.Q.level != null) {
                  switch ((int)b.a<"s100tto7muadvr","4sf8zA0/CfvGq5OR6Ywi+1vNwgw8dLuQPogtlRWvmNs=",-7692880412758384922,245423038027004543,-3093370996364656865,-4410518375556947916>()) {
                     case 1425919254:
                        if (var2) {
                           label199:
                           switch ((int)b.a<"s1wvdtj2qi7cjn","zAobJ+jBSfSeDj+oHSJd2Ly5isQ04k2Aa77MmR3lzNQ=",2926655875443935713,-8556853017207844531,182378689434307793,8307558512466053320>()) {
                              case 1193222942:
                                 if (var1.bT()) {
                                    if (!var1.bQ()) {
                                       switch ((int)b.a<"sd7srn6lmqspz","F1mBc20LsmMXVlJc2ZiCdv3mBVidjgXQ0dUEBIeIuCE=",1463658178006665933,-7074040459245251216,-7563084392207579054,-1652457859306862279>()) {
                                          case 1453095361:
                                             if (!this.cV) {
                                                label114:
                                                switch ((int)b.a<"s1k9y9y7dytd5f","IzgrD8E62ZRmnwjlV1GIrwIMk0GUzgewM3Maw0YDj10=",-6940979683769817451,-8896335032527795778,-7226806934612174572,1243832034910945021>()) {
                                                   case 1277005865:
                                                      this.cV = true;
                                                      var1.ad(
                                                         (String)b.a<"s1z0tz1h6lp3n3","Q8Cbh/V0ey+EeYGTDTKSCP7GvmIo8uPNr377onxOvDSz5kMI6bMK7Bz7t1i+radNsEtsw5fOZearOY/WGgZ3G/czxYyZoBNsgs7dgHTjR7e+5fs0WTAb0MMgbDq/tQ==",-135068011335090410,-7165397391845158539,-110148157305561409,1920803979952475609>()
                                                      );
                                                      switch ((int)b.a<"s2eirytt6wb9n","n/OVabAZgDJAMPVwo87XL3/4aWDs9BYtTWqIxHlp4SA=",4962928066174502657,-3743452052805914852,8689983285979742379,82124227774985249>()) {
                                                         case 1099907640:
                                                            break label114;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.b();
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    com.yiyiaddon.e.j.c.a var4 = com.yiyiaddon.e.j.c.a.a();
                                    if (this.w != null) {
                                       switch ((int)b.a<"s1g7ekn2e4poj7","8+U1q4br4g392+SIhX2zRy3YTvh7v4crvfJF8Bv8KD8=",-2933198426213570217,8135699612705715149,1123341447387646385,-4194758962675088559>()) {
                                          case -1062814818:
                                             this.a(var3, var4);
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    BlockPos var5 = null;
                                    BlockState var6 = null;
                                    HashSet var7 = new HashSet<>(var1.al());
                                    Iterator var8 = this.a(var4).iterator();
                                    switch ((int)b.a<"s2vq2b18cqagx4","pjLp3wFac5Cb0LpEgjPfYokpIeQ+/6JCqofYhFkYr84=",1485593901520916914,5667057699807861020,2466215469599451744,999263287911108397>()) {
                                       case 1443528866:
                                          label218:
                                          while (var8.hasNext()) {
                                             switch ((int)b.a<"s337iw90vazxiu","/vH2BYJy0/nG5xFnmnz6Eq5zskvekXnP8e+4niF5Ym8=",-2919639607000277366,6636906773965918006,6360997541777549064,6561755785317115275>()) {
                                                case -1488808824:
                                                   BlockPos var9 = (BlockPos)var8.next();
                                                   if (this.G.contains(var9.asLong())) {
                                                      switch ((int)b.a<"s25z8mjx5widqf","6RGkbWxxvngR8uMEW23AMM8dFopo+us1QYkLgaJMNDw=",-8450402685691705335,-716607548585837120,-6879431278103674417,7684530960211979901>()) {
                                                         case -1637121331:
                                                            switch ((int)b.a<"sx9womuo0ffi6","knd0Y3nlhaNpWAgd+gdvwgD+I/dooLKzpk4KUTEvUhY=",7430521840922115705,7349545610249473666,-9068967142084531670,1446341452530485891>()) {
                                                               case -735548236:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      BlockState var10 = this.a(var4, var9);
                                                      if (var10 != null) {
                                                         switch ((int)b.a<"s15opuxeeldxzc","7NRtpZbkYqFV8GitaV3IzwrbiALx4faTr/2JfszRWNM=",3583686986183505309,1136382420156134525,-5178868894979638215,7859883775015630825>()) {
                                                            case -1118328560:
                                                               if (b(var10) == 0) {
                                                                  switch ((int)b.a<"s3qz7amman50n2","6QBspZb3wEaiPCV7pO0n4yPysQR9dGJ3tcVmznmAbb8=",-8411665851465385331,-3586545907951750908,5863554167890131303,-1126406726036896154>()) {
                                                                     case 457374416:
                                                                        switch ((int)b.a<"s2q3vepykbsv6j","/AIf4OurT0xWbEhcn/xjTBXHVa/dUbC/yTfHhd/iLuA=",474158051614431350,2292261610316976332,-1045049888386130828,-6522474088638920146>()) {
                                                                           case -1800660978:
                                                                              continue;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               } else {
                                                                  if (a(var10, var7)) {
                                                                     var5 = var9;
                                                                     var6 = var10;
                                                                     switch ((int)b.a<"s2h8oq0km5uxy","rEmLRwFsiEUspkP1nwF7lHtLxji4Tnn2hypnszBbmAw=",3454454755828059993,6567298371923877333,-731804139410967379,-1891385451489415761>()) {
                                                                        case -1434198240:
                                                                           break label218;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  switch ((int)b.a<"s8tjrjsr3ic36","fmJbcyxug+gPdza+Vy9YRLLqah3GFuxckhPEYAjk8dY=",3347601074335462473,1760426356720946597,3214201540628108747,-7997845523118482315>()) {
                                                                     case -737684585:
                                                                        switch ((int)b.a<"s37xjov3lt6an4","oiW8jqEuO+6KtNrkKzOdb12K2MiY0bRezxBkdEVi1lM=",-3592152989696937094,-4042410324203138833,-2567374222506699538,-7149702305272258981>()) {
                                                                           case -1013085366:
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
                                                      break;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var5 != null) {
                                             label173:
                                             switch ((int)b.a<"s23x5kg39c2uf7","pLGK46ZLmjXGXVJchyBghaCbtelHPhjK8QtqOzD3+Ew=",2501630453240838632,-7934694840206066330,4549251165719958523,9207501896078951382>()) {
                                                case 516066681:
                                                   this.a(var3, var5, var6, var7);
                                                   if (this.f.size() >= 2) {
                                                      switch ((int)b.a<"s2258zucr9ul5n","y3MeNwU9BhGJCUGGC3BU7wBCC/QpYPr3qllUl4rLeaM=",7662559526195597560,-1575991549669543915,-7112869577864106539,-3908405047335264542>()) {
                                                         case -147031277:
                                                            this.fM();
                                                            this.fN();
                                                            switch ((int)b.a<"s3qmuvsup1hs6b","TOvw+oG6aWkr6ysbc0mslfsC3sII79+qlJI/OtVioTc=",-7224686717932064563,1071118872187160751,-7789822802223144807,6043702853011402164>()) {
                                                               case -1970205051:
                                                                  break label173;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      this.f.clear();
                                                      switch ((int)b.a<"s3pgtrl633qqvq","usr/tiDmBK6qKGY3GT8a6Sn/s5/FpbaM6pr5B7LFCYQ=",8900730144845326207,2077320358301265102,-2394657794097236113,-6849262444031367719>()) {
                                                         case 1517321364:
                                                            break label173;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.f.isEmpty()) {
                                             switch ((int)b.a<"s1tx25gyblnrpx","90ZNbmXVRRuqACU1gHfPZVBY4mThGMD0em0XA1qs5Gs=",-6199143511749542599,-7210557871440280632,-8315589878082703485,-7768255135050229063>()) {
                                                case 1210167481:
                                                   if (!var4.isActive()) {
                                                      switch ((int)b.a<"s21kxq1ksnh1ol","DoIM9t2PpZ64OIxEFYbpWfHzt8Tg3Ru9F9S7iJBxr0I=",-4913388599177377080,-7491204486709272199,571844922387150645,-1143785102174867770>()) {
                                                         case 73274888:
                                                            if (this.b(var3)) {
                                                               switch ((int)b.a<"s23q1to76437lc","tEtMH7Mo6cGW8wn8x9VG6B35uNN5KIZWiwsSnN7YEJk=",-4715755393695684460,6701160062217225964,-3436445320994761938,-5236518199419077108>()) {
                                                                  case -766243110:
                                                                     this.fM();
                                                                     return;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            this.br();
                                                            this.G.clear();
                                                            switch ((int)b.a<"s2vxsv69965s68","fwulOyupfWWCBsfLB8NS87jQF5Hp30c/GIZS2FKJMtA=",5160497566247760308,-3117819526947090422,-4478817704441008743,-5870412530346690473>()) {
                                                               case -1570180465:
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

                                          if (var4.isActive()) {
                                             switch ((int)b.a<"s3jpge7a2gce8o","WHSxQp+4GRGWumbUx9575YOG4z1GWb7Qus3iu21YqTk=",-8188002798239640181,-1285533004034706110,2524446167676872825,-741921986713712771>()) {
                                                case 1596647666:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          while (!this.f.isEmpty()) {
                                             switch ((int)b.a<"s30x6fa79r6cxw","LHSnKP/gvc698ciCAYJkMfsYJA1aOJmucYSgMk4kKes=",6138224306935151018,6118036713163271428,-3527913400348554043,-133218813138052650>()) {
                                                case 1153697905:
                                                   BlockPos var11 = this.f.peek();
                                                   if (!this.b(var3, var11)) {
                                                      switch ((int)b.a<"s3fmozbtpre9q7","bH2qQ8NJYNu5g8YhqupK3Su2gZXVn7cQXplLG48dtvE=",-429402405891640272,4382435654164172172,5338397236506968619,-5740330762728140458>()) {
                                                         case 557600089:
                                                            if (this.c(var3, var11)) {
                                                               label149:
                                                               switch ((int)b.a<"s2lzg7j0jeeb6y","k4E9V7eQXsLJ2x+1LguFUrZfVmAe+oSBC2hIezDKjcw=",-2697262160602541434,-1493791099561848724,3792208827071010147,-7528056167491300715>()) {
                                                                  case -267537345:
                                                                     this.ks++;
                                                                     switch ((int)b.a<"sk7t2hxrq6qis","Fiu8Vs11rXB3wJekIDVDoye+hwHXamZg4ZCmFgwCqv0=",-2653318758792712469,-5491924923572527812,3318698087146057371,1395573847228760400>()) {
                                                                        case -1927702226:
                                                                           break label149;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            this.f.poll();
                                                            switch ((int)b.a<"s3nyd3qqjbo6c3","ROxxHTPQC1jhWOzZM8UysdukXHtVsVo0sJ8v9qWz0vc=",854195029916120441,1137663526174220614,1478994509815821122,4648751270553790766>()) {
                                                               case 1572737848:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      if (!var4.a(var3.tickCount, var11)) {
                                                         if (!var4.b(this.Q, var1, var11, Direction.UP)) {
                                                            switch ((int)b.a<"sjtp8lnscchjq","dfqsJhp+gNLG1VaQhAzXQYOFg1i4xZKv0OIGO6f3T5o=",-3619145991553754589,-8470050187887079185,3294760005731476582,8555373388438395743>()) {
                                                               case -696142828:
                                                                  return;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         this.f.poll();
                                                         this.iT++;
                                                         if (this.s(var11)) {
                                                            switch ((int)b.a<"s1mouny00cvbon","LcFN73jYwUFmDm6qK2w9Wba0fKV1pZiwoBXZTgsznQM=",1760761032478712475,3927481239600526502,-1301073142138553970,6003680249204240770>()) {
                                                               case -169960926:
                                                                  this.w = var11;
                                                                  this.kv = 0;
                                                                  switch ((int)b.a<"s3pm31vqrfg9s","XGMxOvfW5FeW9KpXHxGJjTFeMHoZEJ8q6gogAji/4S0=",7494775021592735737,-4086270347999030487,3379058949763773643,-21598658203314172>()) {
                                                                     case -912026160:
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

                                                      switch ((int)b.a<"s3u0fzs6mun0t","5uPxErM27nASsOLp9D0MrECebKvjzaheWwSegSwJYcs=",-5385796784422286431,-660698770720725986,4261285292772636453,-4420617941752390448>()) {
                                                         case -180842102:
                                                            this.f.poll();
                                                            this.ks++;
                                                            switch ((int)b.a<"suwditdj5lvfs","TIMwoOfcxJTTz4Uf4LpkyTW6BbjWCDAFHsv4jJIJrks=",-1713007814030157082,-8134668276287680643,8297488171172118993,2510912160289054998>()) {
                                                               case 453737658:
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

                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"s158hoesr6bpgu","yLYaZayEX9hF2fYqh7q7E7att4Fd8r3HNIigqlWbxoo=",-634351780097407435,-9018192619022480581,2954415555861785399,9019370377711314197>()) {
                                    case 1350638370:
                                       break label199;
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

      this.b();
   }

   private List<BlockPos> a(com.yiyiaddon.e.j.c.a var1) {
      ArrayList var2 = new ArrayList(2);
      BlockPos var3 = var1.h();
      if (var3 != null) {
         label48:
         switch ((int)b.a<"s3gex3lgik28g3","ire5tW8gQ0cxtlM/G3FcCDzjOKOA/gP6vAN6YpFO1Us=",-7722168240027328676,7760688621289924414,6846796821016408378,-8231661053762235227>()) {
            case 58345021:
               var2.add(var3);
               switch ((int)b.a<"s3o0mehqp7visr","XvlTngHYvC9X9W4/JRl358FCtosvSyZU7aGk2LmLewE=",2989240066100260937,5274790616406727895,-4810689229641585276,8977647120323643510>()) {
                  case 1008025940:
                     break label48;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      BlockPos var4 = var1.i();
      LocalPlayer var5 = this.Q.player;
      if (var4 != null) {
         switch ((int)b.a<"s3367mzodb367g","QtolGq6NvW/gEb0vROJuIFQ0+MqEF6NboBbJe2fqA0w=",-6644661099358449206,-5613632780007951910,3072315608206056578,2911044951597335119>()) {
            case -1587190391:
               if (var1.d() != null) {
                  switch ((int)b.a<"s2iq2j9nlko66q","EJKYEt24LV2OWUGmS3/FWLPgNsYSOfY3x/WYwyM/qIc=",-1597249315129195593,-9051988403887205591,-467730307908043849,6844183343249476131>()) {
                     case 1465560037:
                        if (var5 != null) {
                           switch ((int)b.a<"s2qvcilvxh0sbq","D6KaQNq5PPDa83cZBvSbs3DhQzStOoHQwd/WFMjuZSs=",4261096472858802625,7963094220516441623,4116088872563293278,6080919498488940265>()) {
                              case -577318816:
                                 int var6 = var5.tickCount - var1.bv();
                                 if (var6 >= 0) {
                                    switch ((int)b.a<"sbgrkxybuhdgz","HObkzbxSedpdSc6ySnshDIavECBk08JO2lt/jPpFEiw=",1846926960504690388,-1902544695195818356,6759897577537474464,-4388327729814420366>()) {
                                       case -973376006:
                                          if (var6 <= 20) {
                                             switch ((int)b.a<"s3n6lmfxho7f4p","M1+eYwPm92VGh6W0zvDaI4xgnVqfZ6TkjhhYyC4X/hM=",7221534990847040978,-3060827442776581722,2377188940653737754,1428820968031111132>()) {
                                                case 1729967662:
                                                   if (!var4.equals(var3)) {
                                                      switch ((int)b.a<"s12g8efsq75cdm","m/zSiL7+8l3+wZgwtpiKX/LlMg2FqgPjZzs0ORlfP6A=",5433731330213468479,-7978996175640976857,-7096178063323431463,4565190712122964985>()) {
                                                         case 283270381:
                                                            var2.add(var4);
                                                            switch ((int)b.a<"s2e8kcq0hj7yxj","lJiinFhPZ0DcaZuRmO68dOjprF1g18FWBOClXm5wQFc=",-1676573936679562185,1129273806129666737,-7774902553184751233,-886847110039484441>()) {
                                                               case -1635947072:
                                                                  return var2;
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

      return var2;
   }

   private BlockState a(com.yiyiaddon.e.j.c.a var1, BlockPos var2) {
      if (this.Q.level == null) {
         switch ((int)b.a<"s1xwgz3m48p3vh","huooByK1ASRovjqyyRbimWsQ6dYaBC76I8lVjlb7CYQ=",5319101378332624231,-6091807839638644112,4004662964107948270,-1729259178707558307>()) {
            case -380852729:
               return null;
            default:
               throw null;
         }
      } else {
         BlockPos var3 = var1.i();
         Block var4 = var1.d();
         if (var4 != null) {
            switch ((int)b.a<"s3vwsyapampsry","EzzUyC4qN2nE+nYOoXjgEbmjYCs3GLM0J9lN0MRt9p8=",-1921170210276126697,7314371150780478988,-3209075512784881250,-6400569345906306361>()) {
               case -434383571:
                  if (var3 != null) {
                     switch ((int)b.a<"s36a8568g9yvo5","xK6aW552bM1ICKbGQ8qsu2qVYSBN5HnZoFeWdUfTJMk=",-1996337426629698760,-1048896041625025388,7555363078680294274,-6151010657281270338>()) {
                        case 567780169:
                           if (var3.equals(var2)) {
                              switch ((int)b.a<"s3virelflsor1e","fLTcSk8huvazC2fhZ7nAutThiXQoHpRL9nqB2Ghgrlg=",358711739290448372,3757178793875103308,1500176557760150491,-6389991227627345288>()) {
                                 case -821335323:
                                    BlockState var5 = this.Q.level.getBlockState(var2);
                                    if (var5.isAir()) {
                                       switch ((int)b.a<"s125dxq9tthigb","e0pl6yvMK0m+9+/WhSYF+RN2jABHAGGrECwd59Uqg6o=",7030330975377065611,-1460984767755529825,7343952869007968497,-6718143922860256345>()) {
                                          case 995983796:
                                             BlockState var10000 = var4.defaultBlockState();
                                             switch ((int)b.a<"s1u8turwlb79dz","J0TlC00dGq9z3C5vG4xKegqearOXlT4Pf2IxJPcka4Y=",-8198778778731803826,-4148340075093663484,2620446879005458885,-2371464860412890735>()) {
                                                case 2010351553:
                                                   return var10000;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)b.a<"sh5b8bsrgpjmd","A1Edi851rn7W8Uy4M6jwQ2geU5R8SAwPUGgOzVIIuM4=",-4548835906563779807,4499765233478875626,-342761168906661374,-8827907018040978917>()) {
                                          case -1773244283:
                                             return var5;
                                          default:
                                             throw null;
                                       }
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

         return this.Q.level.getBlockState(var2);
      }
   }

   public void f() {
      this.f.clear();
      this.G.clear();
      this.cS = false;
      this.iT = 0;
      this.kp = 0;
      this.kt = 0;
      this.kq = 0;
      this.kr = 0;
      this.ks = 0;
      this.cT = false;
      this.cU = false;
      this.ku = 0;
      this.w = null;
      this.kv = 0;
   }

   private void b() {
      if (this.f.isEmpty()) {
         label18:
         switch ((int)b.a<"s5d6loklka4w4","m5rZvwpKDobqOQrS1caGLRIZXeiy6dOPlv+g7PHpvT4=",-6524681074204571500,5901511763358015929,-9082746160478653299,-6067546748976461927>()) {
            case -407073320:
               if (!this.cS) {
                  return;
               }

               switch ((int)b.a<"s24mfmv50k5pgj","3jgPNYQpB3TXSVIlO7CMjI4oOFsHctYYvQ0FUUmtrj4=",-5105282067239557542,-1337638954985163076,-6152953460303457264,590435140241667474>()) {
                  case -1410365988:
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.f();
      switch ((int)b.a<"s281q4izarb8ir","16xq+Y50O0JnJYUdE/LEQ+B+6ev2WLUohMHgBXsM67Y=",6674537180289542541,1197162674872335702,1779464165608373652,8100893061095541884>()) {
         case 374960077:
            return;
         default:
            throw null;
      }
   }

   private boolean b(LocalPlayer var1) {
      if (this.Q.level != null) {
         switch ((int)b.a<"s1fdb5fxubcsqa","Fnigs3GijZMKQX1eJrvy8cZ7onEcG6+uSwYVrhAxcjU=",-4032895045945780897,7936033839127209501,8738326171270185310,-6672918563998347424>()) {
            case 21584305:
               if (this.Q.player != null) {
                  if (this.ku > 0) {
                     switch ((int)b.a<"s3371o1cwgd2a0","aojfQfKkmLOtwcTTs498Q31Pq+SlpIb4WuQZX5OT6/I=",-6170213883845243860,-5265968745230352676,6182794310656870061,-404387047533645216>()) {
                        case -322215248:
                           this.ku--;
                           return false;
                        default:
                           throw null;
                     }
                  } else if (var1.tickCount % 20 != 0) {
                     switch ((int)b.a<"s2eo9k89sa4csa","j0lDr4hgh4eKULIBAjnHdKTFhHyUJyqVxxa8oq4ew8g=",-2243430233145250634,7150331933393540097,2858382568196471551,751390582226032113>()) {
                        case 876395340:
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     HashSet var2 = new HashSet<>(this.t.al());
                     if (var2.isEmpty()) {
                        switch ((int)b.a<"s3b6w3en8zxh2l","KdXxi8mkdcuEYpD1BzDtnEYiI+FmDj/zWkk7Z6+Qmlg=",-2460893989269669694,-910563074214075603,3809622533596943694,-4985778185571111446>()) {
                           case 1742962126:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        BlockPos var3 = var1.blockPosition();
                        int var4 = Math.min(Math.max(1, this.t.bt()), 512);
                        int var5 = -3;
                        switch ((int)b.a<"s3k6140fuqhkjx","M3LDofK/hCJhwp4gCyUIoV5VSvua0Qcbo4okDJ4N6wE=",-6682959655575547026,8527470007563985192,-3336588924079809087,2188712976064038506>()) {
                           case 841057833:
                              while (var5 <= 3) {
                                 switch ((int)b.a<"spd3taje6w1jc","74oVHC6yVkBSUrm/0CBLEc3KoS0B1bH7RgeosUolwKs=",-6876379155516267355,-4086482620589170552,-8157703959490716673,-932518727701353542>()) {
                                    case -1426885779:
                                       int var6 = -3;
                                       switch ((int)b.a<"s1dsw5ou1uhlv","tP10TLE8c2siNhJUkpWhiXMqbh6O9PVRseWR18wsdgo=",1756158006094758672,-2563785311680943914,3287514617150321742,-6932886107184476880>()) {
                                          case -275602120:
                                             while (var6 <= 3) {
                                                switch ((int)b.a<"s3hm6m4e302rob","uYAU1QNqb6N0xNXvC9ejUIR0SgBLKqhQkHrr8+ty2zo=",-2913801616003856872,6038595720053852512,1772723848967347650,2264404461916208960>()) {
                                                   case -114744495:
                                                      int var7 = -3;
                                                      switch ((int)b.a<"slmttvasuhp8q","yPxdr6fVrHrRddounQwbmrwL+K5lLhjfOyJv+abtNxk=",5959291474981447665,4466318826775464455,-2073054713010178939,-3393968398567717635>()) {
                                                         case -1186064381:
                                                            while (var7 <= 3) {
                                                               switch ((int)b.a<"s3hnffadgrroi8","xzLBO0zA4wfvhd51gG1eKpBQ6YEtftD0oLGXaJ6KhYg=",9205058085754629690,6556635801984619921,2826135199614257048,-6565207671922393681>()) {
                                                                  case -1165607018:
                                                                     BlockPos var8 = var3.offset(var5, var6, var7);
                                                                     if (!a(this.Q.level.getBlockState(var8), var2)) {
                                                                        label102:
                                                                        switch ((int)b.a<"sf7sq8yiwfblf","1U8ufdazggK7QzxaQaDIWani3u8Qgcfqz7Sfd6pdK0A=",-220067683853227965,-3248674837359744903,6902201644744192120,289248912341223094>()) {
                                                                           case 1246029136:
                                                                              switch ((int)b.a<"s1kndnpinwdxo0","1568vWObGC8BKRSI4xnZR+KWcNDiIm0LCQ/h8qvImec=",-4747737608017306317,-7324001966629989594,5654768881978286706,-5581791727797827989>()) {
                                                                                 case -763247800:
                                                                                    break label102;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     } else if (!var1.isWithinBlockInteractionRange(var8, 1.0)) {
                                                                        label98:
                                                                        switch ((int)b.a<"s14xvh2ktkv4rk","6vMN/fj8ktHYIhLfbI9pCbx4ey4QMDyu59zD+mduvLk=",-2262296364915544883,4099495060078201009,777028334763994591,5939849980337603109>()) {
                                                                           case 1204181193:
                                                                              switch ((int)b.a<"s1rm7kqi5iczn4","VZXzpHWSHg5T1dc2zN7IZfPHFnrDV61IsTyvGL6941s=",-1995881759405578108,-8971659361604234685,9079946008754400924,-4433220864974922704>()) {
                                                                                 case -297502058:
                                                                                    break label98;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     } else if (this.f.contains(var8)) {
                                                                        label94:
                                                                        switch ((int)b.a<"s2zjkdtfdq300j","5khps3yoNzqXmV5jCx9URmGZ7OrdbuI2pQXW6xc+Tq8=",-1137216748520688661,-914596930316827258,-3878511938272836488,6872790996577823587>()) {
                                                                           case 822392941:
                                                                              switch ((int)b.a<"s2hhwyvre15pcz","EQ+wjXB4Lfyl6VOLkjYMQ2NmytJj52jYZrkxMz8iw/Q=",2780806650314787056,3227675712939151317,-8239005087861325192,-5162833544572563101>()) {
                                                                                 case 1029401853:
                                                                                    break label94;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     } else {
                                                                        this.f.add(var8);
                                                                        if (this.f.size() >= var4) {
                                                                           switch ((int)b.a<"s3gc5yqh8unb27","cLWMcPBol8sBjM61es7Bh+6oKrCMd6V4oFk4J2usdQw=",7196616053302233687,-2926463738800965160,4584020938447015074,-7331702198898812126>()) {
                                                                              case -1670443653:
                                                                                 return true;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }
                                                                     }

                                                                     var7++;
                                                                     switch ((int)b.a<"s3vimtvd2z954x","UvIXZfsjvlpKD6/bqS3X7ESVM7mBWTPAihNC1Lbn+iQ=",-3260610649976883386,2941452001032562487,3948820635771820326,2830606295392227468>()) {
                                                                        case 1900255001:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            var6++;
                                                            switch ((int)b.a<"s2lorawx5ubvxd","dDD96g7kGi4gnyp/Djg9qahegOh1Rvfj05hG+cpfxQI=",-436991311231106825,-5178465808160374081,-1892303937391458909,-3264941750757292016>()) {
                                                               case -548446631:
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

                                             var5++;
                                             switch ((int)b.a<"s325qh28edsoc1","dmCWucYTjNEwRtz1J2rMBBilyawdhF/7j4jKCZa0idk=",-1496191229694726198,-5441365978109841541,627604383666238099,6671235554064501906>()) {
                                                case -439809684:
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

                              if (this.f.isEmpty()) {
                                 switch ((int)b.a<"s1k7yckr5bsqv6","5tD8uZjam3GM62gdlh8VBvH6pe0mzD/67KhG6SJMGEM=",-7472406995549310558,6335324968662189860,-2496322928352122844,-4334648682254548696>()) {
                                    case 1253446868:
                                       this.ku = 40;
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              return true;
                           default:
                              throw null;
                        }
                     }
                  }
               } else {
                  switch ((int)b.a<"s1e3uq3mj1f51s","KSxjA1+sQWtkCmv4hbEUl8ZsCzkSoXCx0V3FEJJMBEg=",171027124427630463,-910341152861161987,7580292676151991907,-6146586973950007624>()) {
                     case 1510839899:
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

   private void a(LocalPlayer var1, com.yiyiaddon.e.j.c.a var2) {
      BlockPos var3 = this.w;
      if (var3 != null) {
         switch ((int)b.a<"s51xt1l0874ez","pb0dnIxGyH0Hmpdt8h58xNZXmTUo3XTO2whaFBvjF/Q=",-6826253285026341396,4182277836250279094,-2506616630284481228,-8949320928848068313>()) {
            case 698772391:
               if (this.Q.level != null) {
                  label34:
                  switch ((int)b.a<"s2dmibxlm70h8s","2rtju7vy4anOqqPY3X0v8ykwDkEJbBmMLJz3up1QjyA=",5590343942583070786,-3836899209356460935,-1343322267433466734,3242966060767229685>()) {
                     case -804755199:
                        if (++this.kv <= 10) {
                           if (var2.isActive()) {
                              switch ((int)b.a<"s1z5vt0y06qmhk","E5ar1UianICT5XVS1wIKv5Wr5LUrhC4VRplu7xxZJqw=",-6605104833524192004,7691274689034220889,-803075709637351256,-5611270018613428889>()) {
                                 case -370037332:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           BlockState var4 = this.Q.level.getBlockState(var3);
                           if (!var4.isAir()) {
                              switch ((int)b.a<"s1nc5uz1pcbizg","Y4sMsJaB8A3pmQQGCP7xZ2j/3dSsex0gPZVQ2z9eJF0=",-424757791882033292,5271968405449948143,565303570333722760,-8110056598105993746>()) {
                                 case -1188694113:
                                    this.w = null;
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           this.a(var1, var3);
                           return;
                        }

                        switch ((int)b.a<"sielm3rvdlh7f","hqAsXE3bjd12PHm1JBzWzWdt3NEQy8ZRWeQ6pdMTq3c=",4606141650702422254,-5268717372554117515,-3812135521820795498,-5698226182201987131>()) {
                           case -1361442587:
                              break label34;
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

      this.w = null;
   }

   private void a(LocalPlayer var1, BlockPos var2) {
      boolean var3 = com.yiyiaddon.e.j.f.a.a(this.Q, var1, this.t.a().bg, var2);
      if (!var3) {
         switch ((int)b.a<"s1w7xnjiezs95c","Hr/+CPGYVe/eHzI7hqtXeI991KHuFatYmMGTgFKwnwM=",5511801431186706531,939340915354624282,-1053338503044350705,-880346694216853834>()) {
            case -711129871:
               if (!this.cW) {
                  switch ((int)b.a<"s3aiduh15756ng","iQXa6g4oN72mBNEcED3Vc8hXLDCnpt0BpYS8HcB9c3M=",315668749766706991,7216374252622619391,-315499717612803743,8177139164735251253>()) {
                     case -1112938605:
                        if (!com.yiyiaddon.e.j.f.a.a(var1, this.t.a().bg)) {
                           label19:
                           switch ((int)b.a<"s3jeeuwckmgfpy","JPnBnvolr2nYv3ImLSsVzyukNTN5gj/oojT8FJQToSI=",6144914816786683158,-3095316574855669100,-3765146814414214768,-6855419212324559120>()) {
                              case -1136668964:
                                 this.cW = true;
                                 this.t
                                    .ad(
                                       (String)b.a<"s11c9l95wmi8b4","OUiOs1wQ9lgnKqSUy69XZZYKA47JjYuTow4XAmafHNoAjy4fF78o4+vKFhu9y3YLr6Uaep1t8MaBgCwWfyoG5RDktWT6xESYxuH3zWj1GouaRRgUW1uy6qX2qOl/OPlZFJuNpXY4UKt9GqlclvUCBA==",295421800529563383,4762678949730348588,2523769789373914411,-6485155932501922661>()
                                    );
                                 switch ((int)b.a<"s1px5hwrndtg7a","ZJexs7yaMDTYt0Msb0QLwEOjlFatMhZjnj5FD4/YxMA=",7466202090160007342,-665346616108996669,2996721654894679592,-3887441402514079526>()) {
                                    case -1874415386:
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

      this.w = null;
   }

   private boolean s(BlockPos var1) {
      if (this.Q.level == null) {
         switch ((int)b.a<"s2v9o8i3ttfy2m","8ip6DXBkqayq5qL/4ycRyGde5VqiAw2j/20BpG+Iyl0=",3674631939963012720,-800588550911643182,6033905800330683292,-8433864591698233167>()) {
            case -1244949460:
               return false;
            default:
               throw null;
         }
      } else {
         Direction[] var2 = Direction.values();
         int var3 = var2.length;
         int var4 = 0;
         switch ((int)b.a<"s23ydm2qhubglv","nILNJ+tirhW3GAvWSKkbQjAtWUjIiy1ruzaU0iHXvqg=",7923782967512091004,5137828036658724152,-2675959261862336963,4038098606898429343>()) {
            case -2047452623:
               while (var4 < var3) {
                  switch ((int)b.a<"s3fa0q9gexyljt","eaIOT54mzg6NWxFzVo0pvGCfs6kcb8BwfQltzthTxmI=",6279445336441378926,6725026651921617768,-8776283040270541934,6673394119342522516>()) {
                     case -1809824559:
                        Direction var5 = var2[var4];
                        if (this.Q.level.getBlockState(var1.relative(var5)).getBlock() == Blocks.LAVA) {
                           switch ((int)b.a<"s65rq36ci8kbr","euVrY1ZgfLDNpsISZk9o5qIVc2J8qLZJ5w5kvpWtlv4=",-6361310316494511947,7451248176121123387,-2958540095475830339,8606082576253700784>()) {
                              case -213030921:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)b.a<"s1y80ykohy77wk","lp9r9BA2viH6vXxtJD+8KavKUjBNBx+aYg79yuKjG5E=",-1534021924814006271,6710764484306828781,-4749254134318529578,-1833435778189393745>()) {
                           case -979265051:
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

   private void a(LocalPlayer var1, BlockPos var2, BlockState var3, Set<Block> var4) {
      this.G.add(var2.asLong());
      this.iT = 0;
      this.kp = 1;
      this.kt = 0;
      this.kq = 0;
      this.kr = 0;
      this.ks = 0;
      this.cT = false;
      this.cU = false;
      int var5 = b(var3);
      int var6 = Math.min(Math.max(1, this.t.bt()), 512);
      double var7 = (double)Math.max(1, this.t.bu()) * Math.max(1, this.t.bu());
      double var9 = 64.0;
      boolean var11 = this.t.bU();
      ArrayDeque var12 = new ArrayDeque();
      var12.add(var2);
      int var13 = this.f.size();
      switch ((int)b.a<"s26td4kbgxy867","KvHBNT0wGAWqPtfVYPzv5cxWsdPXlk/PJjtwSKzHL9I=",5496853162659671993,-6456259064485418264,3598168286868564653,-1571805758259408003>()) {
         case -129518623:
            while (!var12.isEmpty()) {
               switch ((int)b.a<"s29oubw7dvaqav","m/aj+gxeHpKTloIHVJ1OjOBRGK7NiQ4aX8KcOYnIwaU=",-8015352022044512832,6424162616678288492,2755802441824894458,-1162381524144502457>()) {
                  case 2067362360:
                     BlockPos var14 = (BlockPos)var12.poll();
                     Iterator var15 = this.a(var14, var11).iterator();
                     switch ((int)b.a<"sda3v7kcukesd","S7O3j7xfK+lI/Du0L0X0pUH3TB4be0DE4EmnBGTEryw=",4446590989789019004,331314355549682711,-616495633316702098,8386471863128337878>()) {
                        case -1467252169:
                           while (var15.hasNext()) {
                              switch ((int)b.a<"s3h1fpg3aavwqk","AAQYNG/WAAV/S5r7Rzc59FXrF4L7wp08mRLnYVppHWQ=",-1888842006108193966,2371462813724093798,-2046768054602336962,-8808988370085768352>()) {
                                 case -1891572067:
                                    BlockPos var16 = (BlockPos)var15.next();
                                    if (!this.G.add(var16.asLong())) {
                                       switch ((int)b.a<"s2itm88y3r31pu","eKwQam5xY84MIk0Aw3+81sva47awEccPtAgPbcWnOpU=",-7654786420019445790,-4974072888743902905,-9061576652318295737,7202302782486867177>()) {
                                          case -1033267667:
                                             switch ((int)b.a<"skrzrza1ssbf3","iW82Ysj6mImuang0cQfzXaX2eoDXcyahIBsZv25FOB0=",-6134401333901330972,-3097972260064934123,1414735313271194267,-7335098706017127838>()) {
                                                case -268359423:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else if (var2.distSqr(var16) > var9) {
                                       switch ((int)b.a<"s3m1edi1et15kg","2kJbA4CHnDki9TUy/myvP89kgq6FQMfhfKLQrPAbYaU=",5129561189637171097,-4967018191504188435,4938893627728194478,6598648065851300757>()) {
                                          case 1067367459:
                                             switch ((int)b.a<"s10zmtg6ykzn8j","4xT3JaIukCw7w3M9BU1iv0qm3FIZT1RWYLSxwTxkQMo=",-1177480751085175460,8935180395346464525,4293623754332954140,-7038421344374658542>()) {
                                                case 208348083:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       BlockState var17 = this.Q.level.getBlockState(var16);
                                       if (!this.a(var17, var5, var4)) {
                                          switch ((int)b.a<"s18uhhm901rpgk","JC5fqmJLVxbP21pI6DTGOVmPVGltOFH5J+YCWiJL/sc=",3355958544180452649,6307126548105794104,8443660160884258127,626192942889445073>()) {
                                             case 2051161789:
                                                switch ((int)b.a<"s32i24uvv1ffpu","wHdDVRLgI/0DPTnsvGQEFEo05qbnNvfflFzFGE9na84=",2195878877448233431,-3952824078104763902,-1891793927454621535,-8562483169063018188>()) {
                                                   case -1062960898:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          this.kp++;
                                          if (var2.distSqr(var16) > var7) {
                                             switch ((int)b.a<"s19eaeda1y5fzl","8sSWDGS2QWgT5NoqnHB3P99GSL9d4se84Zb+tsgVThM=",6718255970757785091,1684269029406835582,8427677648038722874,2516482990473143632>()) {
                                                case -1127362370:
                                                   this.kr++;
                                                   var12.add(var16);
                                                   switch ((int)b.a<"s9v37xc4wiu99","GoxWI8oZYN+701liIy9RFjB3uX4U5o7bU6jl4CqVgv4=",-6072769548622242973,4991755927689765119,-8720291306744024700,5313957799345838790>()) {
                                                      case -137844878:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else if (!var1.isWithinBlockInteractionRange(var16, 1.0)) {
                                             switch ((int)b.a<"sd9scruhfj8vk","FRXbyq3Z0wJdBZbl46PQsRZEb6Jq6KSXBgLQaS04f3Q=",2446264124968766602,4795675401238640171,7279953119373496138,-5938500982898482771>()) {
                                                case 40669893:
                                                   this.kq++;
                                                   var12.add(var16);
                                                   switch ((int)b.a<"s1hsbngm73kkkr","oIiPt4G/N67fQunb7nV9sOmGpq2cdyX5j/cBHK3dhos=",1348513017037618791,-1631647984111463509,7345313232869219336,-1436439114802219668>()) {
                                                      case -1827855740:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else if (this.f.size() >= var6) {
                                             switch ((int)b.a<"s2ka1inczve4so","tG9yUnboL3yL4lRWr+Fo2VG6//o1aQP4ie+/LICtRbY=",5305976252681319801,-2730755291599852404,5050906131739357095,2557907580393957736>()) {
                                                case -542808365:
                                                   this.cT = true;
                                                   var12.add(var16);
                                                   switch ((int)b.a<"s2tk1x7rai466k","sjAka3nMdU6USlL3g8zK7kTZjMkgCnGeAvGQkWgQAkc=",-3432340121791618393,8941508791792483409,7391358963606527422,-4328909563528953684>()) {
                                                      case 502535956:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             this.f.add(var16);
                                             var12.add(var16);
                                             switch ((int)b.a<"s1fi5odvwmu9a","5HFURHM/WCD1B2ODyFXy+UyuQSFCMDrduss76Hq2wdQ=",6719730264158415160,-2967148822958291666,-4485133711756307624,-2238980392337109750>()) {
                                                case 637895513:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)b.a<"s6jbwkqdjyl7p","EF21Tbc+kEaqg+wbWDsiQu3EYTkfadqVfRn4NIxAuLg=",5127703154832435636,5949602031480257835,-6260123464836517369,-580562950429617123>()) {
                              case 1718686996:
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

            this.kt = this.f.size() - var13;
            return;
         default:
            throw null;
      }
   }

   private Set<BlockPos> a(BlockPos var1, boolean var2) {
      HashSet var3 = new HashSet(var2 ? 26 : 6);

      for (int var4 = -1; var4 <= 1; var4++) {
         for (int var5 = -1; var5 <= 1; var5++) {
            for (int var6 = -1; var6 <= 1; var6++) {
               if ((var4 != 0 || var5 != 0 || var6 != 0) && (var2 || Math.abs(var4) + Math.abs(var5) + Math.abs(var6) == 1)) {
                  var3.add(var1.offset(var4, var5, var6));
               }
            }
         }
      }

      return var3;
   }

   private boolean b(LocalPlayer var1, BlockPos var2) {
      if (this.Q.level == null) {
         switch ((int)b.a<"s1ul270f63htk2","V9lsILGlH23j64o/Pn7GVQQHN0nvhCeiyRMj3VGUSaI=",-6375650095101039056,3553609921815413151,-5368987012012630443,1967333232597603830>()) {
            case 1346384372:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var3 = this.Q.level.getBlockState(var2);
         if (!var3.isAir()) {
            switch ((int)b.a<"sro6q4lzbjit1","D5Fu4HiwTLRlE782FwiQzQJSOPu7JfawmVdOBwDGmas=",8883094200300677857,3963695829616924906,-4685014725993365228,4048256790898046910>()) {
               case -1714937106:
                  if (!(var3.getBlock().defaultDestroyTime() < 0.0F)) {
                     return var1.isWithinBlockInteractionRange(var2, 1.0);
                  } else {
                     switch ((int)b.a<"sheo1dw3gwzew","K83e8bHX9w1ECgRKODvsfLZhCORLxg5bYSVEXpgKutU=",5913218858163903640,1577882148892085215,3881386605336201887,4381353647661730370>()) {
                        case -1542330187:
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
   }

   private boolean c(LocalPlayer var1, BlockPos var2) {
      if (this.Q.level == null) {
         switch ((int)b.a<"s20oj2gis1gbf9","KeueiT5Z9Ez8Cls+5wGkLlp8XRFcYiIpofshMezOvlY=",5569326279011080462,-5112710448460547163,4946068784308433289,3758637964121949736>()) {
            case 1209061504:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var3 = this.Q.level.getBlockState(var2);
         if (!var3.isAir()) {
            switch ((int)b.a<"s1gid0vxv49a8p","gTcK98acSH5XQ+hU/3MawtPHrCrCfw8hSnRbS8/Mae0=",-7577497556098570919,2683198909529102069,-4406639787695145000,6423936327614905090>()) {
               case 268900757:
                  if (!(var3.getBlock().defaultDestroyTime() < 0.0F)) {
                     if (!var1.isWithinBlockInteractionRange(var2, 1.0)) {
                        switch ((int)b.a<"s2khxpn8rdf0uc","gkk2n2kTObLGt1cxgDeHm6OvkIWqsNv9P2ssuYSc2XM=",8907347689923830300,-9090334618755902378,8913914549309739844,2775303454276264758>()) {
                           case 878231750:
                              switch ((int)b.a<"s1vir74nxht1nq","ijyGfvoqGZ/3EZC+eROZ3BdYcK+LNWRL7MTwL0B5HmA=",4619534132158275657,8138588894297909913,-1845685794828165723,3220652055152934066>()) {
                                 case 1389324928:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)b.a<"s350byrxv3m6sx","yQRaBHTU7tIc2iejruUgpKdg0cDZS17ihqitvHRH/I4=",-3925193388691090034,-6128260615337462689,3733632968400381990,-6245603460596857247>()) {
                           case 808533741:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  } else {
                     switch ((int)b.a<"s2sqzvaui99vkk","uppppPwle1JX0XRvQKG6Kmm3I/oWid6ddAYQOnykxAo=",1514027445215878808,-1568390103300536971,-2612315119181297519,9171730161426401316>()) {
                        case 173317682:
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
   }

   private static int b(BlockState var0) {
      if (var0.is(BlockTags.COAL_ORES)) {
         switch ((int)b.a<"s3xd9n1nd26cw","1ScF2qh9ZI1qVFw402LDBASIRtXRBCjhN0Hl6/o3RLs=",-8489955587152604576,1268557946504775470,-1206075876792254442,5404690929902274>()) {
            case -1647013030:
               return 1;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.COPPER_ORES)) {
         switch ((int)b.a<"smubx3zyyz9vp","Qnsu5pjcl6w2Jt2enmU9p71YzS0owc53TjHsAo+Yg1Q=",6411808257442518579,5709555101971609569,3018118901373784347,963958165816232055>()) {
            case -1654725954:
               return 2;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.IRON_ORES)) {
         switch ((int)b.a<"s25dl2tutdry0q","h0gkIJl9HtAvMrStbqW7b3WomX2pOnp0Geicdmr7vrA=",1386372697167655816,-1263834107601210173,5105066991880658139,78332140650523148>()) {
            case -1197564964:
               return 3;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.GOLD_ORES)) {
         switch ((int)b.a<"s26xh235jqb1h4","7dhh27UUO2GgOfR87LejZyPe7YR3o8zGS69ChwNZ7+o=",1119043451025320734,-8801172507114933463,5991780803420861403,-2060504232681126910>()) {
            case 547778432:
               return 4;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.REDSTONE_ORES)) {
         switch ((int)b.a<"s2sb9l2g3xihx2","bE9SDFgRne0RFc2ihk5pCODZS5sHsQPa8QGCBc8tXI8=",8927246253952960603,-8819178453029650762,106703757695625545,4350715687153421193>()) {
            case 1294354518:
               return 5;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.LAPIS_ORES)) {
         switch ((int)b.a<"stzh74mxbawrp","wxv0fYbsghdVaGD3SLnsYkezjYHZVzLoiKgdQ4Tc8B8=",-4032991131435980529,3499418460276501160,530881273490170966,2741539241995725734>()) {
            case -41579864:
               return 6;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.DIAMOND_ORES)) {
         switch ((int)b.a<"s3g4hecad02xo2","XOfB1vnoRNN3/PfX2ivqKqMzOfO7jU64mJ8FXdjGyVk=",7617337506673609055,-9214787226813382850,2237260024603698680,-4492740920669292924>()) {
            case -1039966982:
               return 7;
            default:
               throw null;
         }
      } else if (var0.is(BlockTags.EMERALD_ORES)) {
         switch ((int)b.a<"s33nmtgw42bluy","VbYQuPmzXVw3Pr1ql1FTls7yndmiq0Ngyrq0MNcjcQw=",-7312903427503350840,6989005389649264412,1968654601486195192,7130040377848500589>()) {
            case -2053533631:
               return 8;
            default:
               throw null;
         }
      } else if (var0.is(Blocks.NETHER_QUARTZ_ORE)) {
         switch ((int)b.a<"s3oivk4juw5i25","0VDkWhke+qs69JKmYDWKpJJ9NDKc9ukSZlzp1AReAco=",583530941975113567,7246325690089000730,3566648804384582847,8656073620159573243>()) {
            case -175760887:
               return 9;
            default:
               throw null;
         }
      } else if (var0.is(Blocks.ANCIENT_DEBRIS)) {
         switch ((int)b.a<"s3k0d7onyf5jh7","jErPuyqQJ76l248Lv4GroeYqgff3CDRXOS3bL9HOhG8=",8933497810223973837,4138885760491660242,2892731091623833017,-1305229227686552128>()) {
            case 1108502353:
               return 10;
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   private boolean a(BlockState var1, int var2, Set<Block> var3) {
      if (!a(var1, var3)) {
         switch ((int)b.a<"s1hatlbrhg36yq","n0vRux9bq3yKpMWRDlSUhS//eU/ZS7RaxJ6Xw5H/qoI=",7396722163027604552,-4730635891567667244,-4974204140052000430,-4826132086690236883>()) {
            case -1826819676:
               return false;
            default:
               throw null;
         }
      } else {
         int var4 = b(var1);
         if (var4 == 0) {
            switch ((int)b.a<"sxw76fwom193p","ugtvTcumwnMLKWbn9OZ3yaVHUYKuXIx0FEPhkJwvOEc=",-3617814744904171507,4227900475406358035,3185014895978428146,5076685899016473655>()) {
               case -1527502414:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (this.t.bV()) {
               label32:
               switch ((int)b.a<"s32irb8972jxvu","DRSry8FdZ7kxNxf8X1zmOQprootQziNA87eq3Kxj7tI=",2224878748635923949,6027951328519648739,-3484439348865509496,-522868768238651361>()) {
                  case 559940369:
                     if (var4 != var2) {
                        switch ((int)b.a<"s17gfkexdoanae","IRZxvWRuGzFRBrCbtr0khdt0WFacwzRHiszA9c/V2k8=",1221446777330384067,1012452765190653062,2948563467452620760,-40885181373655320>()) {
                           case -386327239:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"squ77hzk4nsu4","YQw1J4eCqVEEkUUQvSg1LcU0vb8SMcjEqNGBNUDlV5Y=",-3521051334630961902,-5014282649172397158,-6378196457101304311,-3496064310810658725>()) {
                        case 1490862764:
                           break label32;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)b.a<"s3rt0d8rdhwg63","Qa4imbm00eCmt1iLAYdQL+psXwuQEVX1u9TG1VzO8zQ=",-3174467395263047976,2445209543562654297,5415672255665406839,-4686032504280349603>()) {
               case -197198323:
                  return true;
               default:
                  throw null;
            }
         }
      }
   }

   private static boolean a(BlockState var0, Set<Block> var1) {
      return var1.contains(var0.getBlock());
   }

   private void fM() {
      if (this.cS) {
         switch ((int)b.a<"s126s63c6cr2ij","4L/dAMni6IbP+i3G7SVycFxwKRZlJ2/Dsxhvskzt/VE=",1351518735439843937,-3009953620462860810,-6414186082279180265,8346200128559367489>()) {
            case -130525818:
               return;
            default:
               throw null;
         }
      } else {
         this.cS = true;
         this.t.a().ag();
      }
   }

   private void br() {
      if (!this.cS) {
         switch ((int)b.a<"s3nwe9nuii40pq","K5VtKPxSE+cKpxIcUyElqKKdaofqn1UWGTHTlaHX+lU=",7696674039463508391,-3332350543875772540,1085653647733633780,1418598686003007736>()) {
            case 2024142018:
               return;
            default:
               throw null;
         }
      } else {
         this.cS = false;
         if (this.iT > 0) {
            label47:
            switch ((int)b.a<"s3k8j7s7kbdg9q","Voar3OMvNJeWzG5q7o86OLXL7BUUO11lwBSywWHoNto=",-7160180774593691889,-3559359023381560773,6120475186290066646,-7948768046331484882>()) {
               case 171223739:
                  com.yiyiaddon.e.j.a var10000 = this.t;
                  String var10001;
                  if (this.ks > 0) {
                     label44:
                     switch ((int)b.a<"s1jlbtev8wuad1","v2Yz9onMrFWmteh1MdAL0OvS0mdKm3KnsFODDuh8fpI=",-7981715278728903556,7622666414672354929,4421510833830397598,-2130354382401549759>()) {
                        case 146695733:
                           var10001 = "" + this.iT + this.ks;
                           switch ((int)b.a<"sy8crsxkvc8q2","NCCH7E8xT4JkeQ4RO3GsuuDPnmQsg0lIv+xXT1YKaT8=",7015607334832790841,7055240067833077970,-355699545985671641,-8557378693304694962>()) {
                              case 396675700:
                                 break label44;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = this.iT + "";
                     switch ((int)b.a<"s218dcm5qunfff","jYXoyAExgdGrbPbepFMPvI9MsXvxZC7gpiM0/4wZNdo=",8536342885368167406,-8901285621852218390,1057826413500913070,-4467663925983312309>()) {
                        case -910199224:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var10000.K(var10001);
                  switch ((int)b.a<"s39d6fl03rhklb","UVbhbxvG3iF9E9nt+Mwv0n+Zs46XZ9SaoqmSVXuxWU8=",9083901471554152451,1863276393885222263,5692762851436126423,-1160473751386865883>()) {
                     case -923479163:
                        break label47;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.iT = 0;
         this.kp = 0;
         this.kt = 0;
         this.kq = 0;
         this.kr = 0;
         this.ks = 0;
         this.cT = false;
         if (!this.t.al().isEmpty()) {
            switch ((int)b.a<"s2obpg98r48rmk","z9LbUxlTo8Q+trrv5tDZ7iERYZFIo4uaZx7cscyLqlY=",9192987297305376730,172257866025117512,-5198759709298180212,5649518753961523759>()) {
               case 1741226632:
                  if (!this.t.a().cm()) {
                     switch ((int)b.a<"s1sz4vvnpb2www","GJzR/nbC7s5Bs+sWUGpsiNL+ZjuYAgjn2fkzw/BLKJg=",3652291198240024672,5253874228824058535,2151183059725986403,-8416886432841179714>()) {
                        case -803517268:
                           this.t.a().a(this.t.ak(), false);
                           switch ((int)b.a<"s318kx8994yemj","aqTYINsuKQzZL2EqAHea+50n3aUVNJ23/hIhs0YQs9g=",-1033317864463989071,-6963652690987805053,-3637358514677038096,6074289181150688420>()) {
                              case -1903530581:
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
   }

   private void fN() {
      if (this.cU) {
         switch ((int)b.a<"s37y1g31o06jrp","PsRuOR20X6MjnnOOA34P55oQ8qzx3UZnZC92ErxjUaE=",8007425622081387593,2918220150739116306,-5093018681745155095,-340170968995983697>()) {
            case 1417359062:
               return;
            default:
               throw null;
         }
      } else {
         StringBuilder var1;
         boolean var10000;
         label96: {
            this.cU = true;
            var1 = new StringBuilder(
                  (String)b.a<"s2x5g3iu7mnywd","LRaS2FImoK5VqvkG6rej9wGPnwsXkCu9yjrErFe8fS07//AETUJxk5lUm/W3j8SsVgrEQBm3",4882237326264974430,-1313922392485551226,-8270695771160060392,-3276707270209281873>()
               )
               .append(this.kt)
               .append(
                  (String)b.a<"s1c1wi9emzprdf","D5fNT4r6xb5dSmdnWI9esOpKIOhsRm7vghqPT6hJ5fTUU8kqQ3fPAqYsitLU+YcB7GMlLw==",-110592498513097294,5709715998482037612,5873856219894176191,-2013231202655883167>()
               );
            if (this.kq <= 0) {
               label81:
               switch ((int)b.a<"sfhlmzkcg1e1e","dvSsH//HwOiU387d1cUM5KGa1HoMcXrDWyCmd/Bdi+A=",-5519353732959323527,4240745685251146406,6566895059185290824,6126876996204673579>()) {
                  case 860564507:
                     if (this.kr <= 0) {
                        var10000 = false;
                        switch ((int)b.a<"s173zw6fm22w6z","kshSzpVsM5RVezElBRi/PlCMHa1wSqqyF1QpR1mvAy0=",-9070130933827414849,1099855993104059530,6318509644426759786,-5234734892300873125>()) {
                           case 1872180630:
                              break label96;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s3ge06xbvh086z","NSiIh/+aMEWM5PfsbQDLIKwaYEspSrYaOsZfzGSxC0s=",-4578605347134505250,-9009182977866515949,-7176529318640222658,-818266764469611026>()) {
                        case 765320127:
                           break label81;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)b.a<"s1k1htuxn2nre9","uxC7pjv4tRiKCQBx5/EpOYPVf9QFJfch84Hx/H1Jl4s=",-1270635410959671635,3498379662720306562,1151114969333683207,6339654441720923024>()) {
               case 1329589541:
                  break;
               default:
                  throw null;
            }
         }

         label101: {
            boolean var2 = var10000;
            if (!this.cT) {
               label73:
               switch ((int)b.a<"s16hijnxi9uyqk","XKy/pRGDQ3I1fbF+90J6xDes6w8aUNf4HhrkeSfTsUw=",-2358051606716607313,5235234350983533893,6484954822781322969,-5326151463856723014>()) {
                  case 1964755015:
                     if (!var2) {
                        break label101;
                     }

                     switch ((int)b.a<"svotbtlxih9ub","zFrZsXM5tcmCJV33NHHTAnV40tXwohc5X2wV4DdtV7k=",-3950934295078709603,4709068667697694489,961967845199064129,8008252393217644133>()) {
                        case 1097459490:
                           break label73;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.append(
                  (String)b.a<"s24lricslooaka","Bjin5hm74MYr2+2knKzMfwp482CDw/dLVh7j+NXcjrCmZTeywkN+IkxU",6516385508395679685,-1783040149483171525,-1760871001037527259,426885385251029971>()
               )
               .append(this.kp)
               .append(
                  (String)b.a<"si0gllv11ety2","URP1Ia2JBHsBenh1QZ+3BeETAiB9/AfSVpgRUmFgd9K2yg==",-6424734160651237740,3810449251137619077,6899601937998546485,2558999257442663818>()
               );
            if (this.cT) {
               switch ((int)b.a<"s19spayck8uwyh","FibVWdc/KMTwLQv8/4c+NQ31aPxbybmNuGIn4i8Ywjk=",-7177179498739278774,3211530305177627386,8918374883366531735,7268977744538745680>()) {
                  case 862671710:
                     var1.append(
                           (String)b.a<"s3e0he5etzjijg","zhoeDI9jCNzItN/46Buj4pFvlqXTAigKjSDGsZVYrwDIhWSyrHY=",-532676586815636596,-6972715506137180216,2781132042872180571,952819526423984444>()
                        )
                        .append(this.t.bt())
                        .append(
                           (String)b.a<"s1f9lkbsr2x7gn","6oma5F3gZPErmgzUy7QROTB0lZR1iOfUCSJq2Qo/TT0=",585032275309833972,6978983907479829653,8031313493090101825,5964176533523070349>()
                        );
                     if (var2) {
                        label66:
                        switch ((int)b.a<"sb27cnh6fpkja","j7kwd88bbevMW+FCYZDB1XFPcPTzzffA6V7TbHm1iI8=",2171970941913351173,-6184238839611325372,-7783876018706686,-1097970352337700311>()) {
                           case -235928193:
                              var1.append(
                                 (String)b.a<"s2a00q3o4w8g6r","WsN0LxC25VuExJlFjPBF2qdQJl60jHh92Lhd1D1z",7395972706221798495,-2891657262821543593,-7654401081512209424,-4507948648047712238>()
                              );
                              switch ((int)b.a<"s3cht026yc4hsc","AyMes7bdDjuO0Zl0+5NbuMyhbCBNm0ozLcKKU/qZp1o=",-8809775083691438460,8625899010293806461,-7189463983853459599,-7946247782750243170>()) {
                                 case 967051620:
                                    break label66;
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

            if (this.kq > 0) {
               switch ((int)b.a<"s1qliuey9u710k","TLuH7AaGTkt8QMp4XHUms2HGUbcGqexxlBFr7+tXv5s=",-8796816477702976186,-6218795625847851268,-4168344592919942277,-2371981383653630536>()) {
                  case -10432098:
                     var1.append(this.kq)
                        .append(
                           (String)b.a<"s2an02l2ff3dzi","tkq9TnSOcJVj57L9Jbmf3d/5nqpjjG7lC24MiefLjORgz9owrwM=",-5799463849987724030,2788512658589670900,-9118808170606564167,-6668121587944041209>()
                        );
                     if (this.kr > 0) {
                        label59:
                        switch ((int)b.a<"s21q96egizj7t6","NUM1QUtFx4nqZOCKQo8hfjJp13pdDNA8NbquPQS1fkc=",-7359147462224136700,3451754799929120833,8679705303779919844,4454029085280960925>()) {
                           case 1451322094:
                              var1.append(
                                 (String)b.a<"s2a00q3o4w8g6r","WsN0LxC25VuExJlFjPBF2qdQJl60jHh92Lhd1D1z",7395972706221798495,-2891657262821543593,-7654401081512209424,-4507948648047712238>()
                              );
                              switch ((int)b.a<"s34q2dezkhgf9u","N2VBVCFvlZTCgEccLym54ublSX6a5RylNL1DM0CrGxg=",-6306219268193588502,-2071817131280119754,6788717026569885577,-3074387488351254643>()) {
                                 case 1362540129:
                                    break label59;
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

            if (this.kr > 0) {
               label54:
               switch ((int)b.a<"s13xgqnbs6gk7e","zCuqtVjBUcHGZdOddIMh0mz/RBrw9/NSXqwQE1BAc3U=",8651379626134967759,-8281790822775695246,5320679125325254465,1129539700547612365>()) {
                  case 696757524:
                     var1.append(this.kr)
                        .append(
                           (String)b.a<"skdk3md1tx6jr","VY+NBMIOcvS+4d/6r3L1F4h7LKYy7eNBEVNXpMI1DnGsDFLrqlVcfiqAjXs=",-7279047333939974128,7832427448895974917,849783249259022200,-8700552887389091930>()
                        );
                     switch ((int)b.a<"s3btoszcukpraz","VpzM//sMdnV9TpRd2D2+y3yPsNB/7JeleaL1PG3+qr4=",6261275838718685405,2692003930978126800,-2844253898523657902,1266593190493634953>()) {
                        case 633210615:
                           break label54;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.append(
               (String)b.a<"syo88y9de5q0p","XcMzJQNhDvx9UFH/R8O90H2swO/dozodUMNTGB1o1z22bnv7WDjFhoydIz3LAcvdQ+6sAfNdY+2y5lOYDO8+XQ==",3747105520660195690,619441022542828328,1340277982497941488,1853129808577503774>()
            );
            switch ((int)b.a<"s118ovf5qytx4t","DiamGSVbE1JkziM0ZdMfhR9dZS/FVvOafmhZL+PlfPc=",8291831984473595148,469235825427090582,-7244455922825080531,-3769866608347787039>()) {
               case 1761675423:
                  break;
               default:
                  throw null;
            }
         }

         this.t.K(var1.toString());
      }
   }
}
