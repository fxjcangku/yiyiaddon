package com.yiyiaddon.e.i.c;

import com.yiyiaddon.e.i.f.c;
import com.yiyiaddon.e.i.f.n;
import com.yiyiaddon.e.i.f.o;
import com.yiyiaddon.e.i.f.p;
import com.yiyiaddon.m.b;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class a implements n {
   private static final int fm = 30;
   private static final long t = 50L;
   private final c a;
   private BlockPos k;
   private int fn;
   private boolean bF;
   private long u;
   private p a = p.IDLE;

   public a(c var1) {
      this.a = var1;
   }

   @Override
   public o a(com.yiyiaddon.e.i.d.a var1, int var2) {
      Minecraft var3 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)b.a<"s34ywbj4vdlpnw","lKwV2p7Hxhp/kNLv64Wf1b4nhuvIbJMND1NYni9qLQE=",-7478983740171387884,537709981636338767,-6933440766560779041,3565600408698799758>()) {
            case 1859162479:
               if (var2 >= 0) {
                  if (var3.player != null) {
                     label50:
                     switch ((int)b.a<"s233twn5rkas7z","yJ3p07JEzUXURjEvLY+7xxXI6ftbihhohwcX3APplCc=",-7557663243685787190,-2275218747678518104,6234488717459920928,5783218028887411733>()) {
                        case -396263440:
                           if (var3.level != null) {
                              if (!com.yiyiaddon.i.c.a.ft()) {
                                 switch ((int)b.a<"s9b3qhz9jwhgx","PgRgv0wlDb/0HBBNxJpzlExw/YEMIJio7R4LaWrjBcM=",-523180993313369896,-4650659133637003760,-1507432647222151058,-6020838162617362483>()) {
                                    case 562386180:
                                       this.a = p.UNAVAILABLE;
                                       this.a
                                          .b(
                                             (String)b.a<"s37sa9o4x6mkz2","t6iHZ31xAByOPxdg1z/SwXmmEKZR5Paxa0h4B03ITI6Yjn5/afgn/XAzJNS82bj7JSEKpLpIswCcrxjcnXxRkY+Y35p4zbuTcyoN9WYgg4aMNMUNBnquwClqWt0lMooa4RURFw==",-2392711974470063585,1338451650030945404,1180349108154936062,6342977586645270929>()
                                          );
                                       return o.UNAVAILABLE;
                                    default:
                                       throw null;
                                 }
                              }

                              BlockPos var4 = new BlockPos(var1.aj(), var1.ak(), var1.al());
                              if (var4.equals(this.k)) {
                                 switch ((int)b.a<"s2yrru8ryvy1i9","pOUwZr5dM8VoyjTS6gulBPl/2TneMmqCD0LlFqsaDhQ=",-6883987802562993864,3935221252480779117,-5941486697736248237,-8747795259631431341>()) {
                                    case 428703511:
                                       if (this.bp()) {
                                          switch ((int)b.a<"skcr2n9844i36","5J64YfIChwMZYtIpDM8mbBhw7h7hj7b58c3HYOoCBac=",-5275158052385968511,4166483785793644698,569393872403225240,-1073989003043618172>()) {
                                             case -616569122:
                                                return o.ALREADY_RUNNING;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              this.k = var4;
                              this.fn = var2;
                              this.bF = true;
                              this.u = System.currentTimeMillis();
                              this.a = p.STARTING;
                              boolean var5 = com.yiyiaddon.i.c.a.a(var4, var2, false);
                              if (!var5) {
                                 switch ((int)b.a<"s2afb8wjql22r3","8IiM7f/S1yFcS2pkz2toAZzKCs2gq0ikgBC4/u4imVs=",-1276374920180800746,-2041459105603020457,-7650615450192116142,-8591970658987982743>()) {
                                    case -2109354171:
                                       this.a = p.FAILED;
                                       this.a
                                          .b(
                                             (String)b.a<"s3d6wclqgzcuku","H4q1xj+LSVP6D0sxY87s+qOw9cVanYvzidjkNryKWLPQTygrIrbh05D8BJSh1p7ciuCXh6xW1N/8Kija",6636452356826923326,-4610356856011358823,-325347170979192700,-4097196183680178646>()
                                          );
                                       return o.FAILED;
                                    default:
                                       throw null;
                                 }
                              }

                              this.a = p.PATHING;
                              return o.STARTED;
                           }

                           switch ((int)b.a<"s3lj6uo87hpxsi","okZukbsRZjXWZZzzg+7uIqTlLJEI36NX+I+gDmO+/cU=",-5822953176761632048,7893374279964738023,9134119118121066128,-7075737825133958522>()) {
                              case 248869716:
                                 break label50;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a = p.UNAVAILABLE;
                  this.a
                     .b(
                        (String)b.a<"s219jg3tsy4seb","Dg4gEsYnmxJzSmzBrSHxRRkuLRhUNY1rW8V0e0qZGcE9X81Bkv2YkvjMvSOk++E3EBzw90GiaXywpzQMDsG+IhDGOFvc8+O9DkvqsQSl",-7813085872956218129,-1834466827641053090,2069543143399696595,2461129674534479962>()
                     );
                  return o.UNAVAILABLE;
               } else {
                  switch ((int)b.a<"s3limr7mjxsht9","vayhbiY0H7nLlLnM2szicmjeRjHmoGMrrocbX/EGPAU=",4449348087805046949,4630874349370196509,-209265263136578334,740014494427276581>()) {
                     case 50490569:
                        return o.REJECTED;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return o.REJECTED;
      }
   }

   @Override
   public boolean bn() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)b.a<"s1t98eyy4n9gu7","MsLvqPdaA/Gj5YndOFFgGJSVQpIJcZ0chnGyYSWgMrI=",4472080655241979658,-5666271514881871068,3265408375603277920,5132127608977210869>()) {
            case 170441786:
               if (this.k != null) {
                  boolean var10000;
                  if (var1.player.blockPosition().distSqr(this.k) <= (double)this.fn * this.fn) {
                     label32:
                     switch ((int)b.a<"sdabzl09tizp4","n5+ALYRan6640e9LC8wHjG3osqHBaHfM05E7MYH9rtY=",6948796626643742940,1759188258360486308,-1257802596454852276,-8109615003635425402>()) {
                        case 193177531:
                           var10000 = true;
                           switch ((int)b.a<"sjyjqqeexhlw3","HN2kVuGXdo06cFk/ic+QZz/vg7b+aU0Vd5brVKdGX7U=",618007269344325811,-6659362928904441733,-5269248742126176943,-2870295475759750798>()) {
                              case -1772988428:
                                 break label32;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = false;
                     switch ((int)b.a<"s37vvf3puza8cs","LJomKpikjfDGlIVWftFsxmYuKcTbeBTLL3TsBh9Mej4=",7529702069332288552,-8247682736738175541,-1948928419031285063,5193501706672264142>()) {
                        case -770021377:
                           break;
                        default:
                           throw null;
                     }
                  }

                  boolean var2 = var10000;
                  if (var2) {
                     switch ((int)b.a<"s109uydh9cqrgg","OkVcQoge2GIsTlpQ7rYxdtOA9XxG1r9brmVmu9tANzQ=",-3680625509966907310,6533130865092169799,-1819238475289201858,-5416642996091034984>()) {
                        case -178739174:
                           this.a = p.ARRIVED;
                           switch ((int)b.a<"s3n81kx8bd5eto","At52YLNmVi9fM2UOjMMzVbIbJUmDaU9Xwvs9ROVke7M=",-6603049465064872262,8476573969745530592,499996589494570309,5166488563495607762>()) {
                              case 1994216077:
                                 return var2;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return var2;
               } else {
                  switch ((int)b.a<"s9hewj0j06m9a","FqqTSlaoXr2YD9b34MUOoDXRoov7yJQnWk3KwQNIo0Q=",973092058921199918,7395682178308768245,-6440165981303683772,-3245863706279742900>()) {
                     case -1734478884:
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

   @Override
   public boolean w() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)b.a<"s21mln6sozocch","bmldwHGI7NC4BCnJd0Xvpt0AfetALGxrFsKzDGq26tk=",4973112032309498567,3583729144441965616,-3295575704001093562,-4642414648569616371>()) {
            case 572844124:
               return false;
            default:
               throw null;
         }
      } else {
         return this.bp();
      }
   }

   @Override
   public p a() {
      if (this.bn()) {
         switch ((int)b.a<"sv4i11cfth5hq","Syy/tnQXktttgrflqCD63G9udqjakBqeInxWicPZFEc=",-402918040919514318,-7112385729605325797,-6482753666474824154,7067167855661803307>()) {
            case 711864801:
               return p.ARRIVED;
            default:
               throw null;
         }
      } else {
         if (this.bF) {
            switch ((int)b.a<"s3pnb2slgd3k2k","3642O4IWt4NPeHKs8+P9boTBmzsQzKdXYeu4wfxGwQQ=",-1983608923168675958,6626317986889533443,7954865368531371566,-8788774015413237145>()) {
               case 208984225:
                  if (this.a == p.PATHING) {
                     switch ((int)b.a<"s1b37uleeq6549","WOXpzi3POca53cx5V7tbdEGncz94XW3qKBgJGfv4j0c=",3629311662123714590,-3130351054205141461,-7186666211770386966,-2953028598989605145>()) {
                        case -270965503:
                           if (!this.bp()) {
                              switch ((int)b.a<"sdslvg3ksbzl2","/nUZQqKr6PHRhnsUfvxWObhZu/NHKE1zZpach++omvY=",-8392931079045275364,6511029817095732165,-6988982955092184764,5313302410473147972>()) {
                                 case -178720364:
                                    long var1 = System.currentTimeMillis() - this.u;
                                    if (var1 > 1500L) {
                                       switch ((int)b.a<"s260vmu41832k5","mgICgKrCmp5p1vhBYEpwe/3htlSA4KG5pqg8z7D1U1k=",6950155180977070135,-3997366473786353959,1042702941115231527,-4558526449148600716>()) {
                                          case 399246388:
                                             this.a = p.FAILED;
                                             switch ((int)b.a<"s3f94dvkeqytie","RJYO6sHOA15HZhfe+Nj56JCTRct3KNIKTLZ/z2c6tYY=",-4116223755568731486,5664690331191903252,2468536709050558961,-5883099934317382740>()) {
                                                case -1799494597:
                                                   return this.a;
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

         return this.a;
      }
   }

   @Override
   public void ag() {
      if (Minecraft.getInstance().player != null) {
         label13:
         switch ((int)b.a<"sil3l1yd5mew9","YnwuUO5bH05tEjX9y0E/Wm16uZ9ua8hRi+FiTIVGoB8=",-5317528543082027833,3701958933083773048,-55621291880032303,-7517522905395066696>()) {
            case 1728078073:
               com.yiyiaddon.i.c.a.i();
               switch ((int)b.a<"s8hmp6v6ckj2x","nZCw6umlp39Q43H1Epaj0kTbYCCelUTPK3/3rTCq82E=",8984247916048579443,-2788960502571055090,5976308594708774071,2476235538577247787>()) {
                  case 808218303:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.k = null;
      this.bF = false;
      this.a = p.CANCELED;
   }

   @Override
   public boolean bo() {
      if (Minecraft.getInstance().player != null) {
         switch ((int)b.a<"s22k5ovpg4kqwt","G2+12Qjck1hoqiupG4mUIeR9IkzsDXsRw5kuA63eLUQ=",-5136988602228977971,2228071104485946108,-4205482950523060598,-2272217288845010469>()) {
            case -1108280237:
               if (com.yiyiaddon.i.c.a.ft()) {
                  switch ((int)b.a<"s2g3wes4iphz0q","Jd6zf4ID/Xwd5gmU7I7UJhYAKg/Q3u+LuyLYGlsdAtM=",-4500845113173767689,-5178215248665975545,-5421997163247436794,2693305745608184206>()) {
                     case 2114818796:
                        switch ((int)b.a<"s10gxnjsm0wic","uORz56bX/omazadm2pVC0RhkOkZergRgBcIp7Aaojio=",-3064005440523900069,-2436850794764751632,-4267044060850235635,7308654976277842313>()) {
                           case -1675823414:
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

      switch ((int)b.a<"s8fal7h3tnfxi","ZikQUkoFjd85uEePsbwWIWPxxkp4VJseJYV5tSOUylU=",-193812745513897968,-4007504308091659529,2429328832733114413,-5832988382655026480>()) {
         case 2118313374:
            return false;
         default:
            throw null;
      }
   }

   private boolean bp() {
      return com.yiyiaddon.i.c.a.cX();
   }
}
