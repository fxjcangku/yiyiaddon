package com.yiyiaddon.e.q.j;

import com.yiyiaddon.e.q.f.e;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Iterator;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class a {
   public static boolean a(ItemStack var0, e var1) {
      if (!var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1n5vj2t4fk26l","MnRwJdwS7j33a5B2H+OuCjyYU6I4+Bp4E9ZWcDzA250=",-271343988722540659,-6799535812515684392,-3428394022918328223,2489585556292221196>()) {
            case -2067869988:
               if (var0.getItem() == Items.ENCHANTED_BOOK) {
                  if (!var1.eO()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2azp36orff0yj","42HMRNnIbgpmEOCMX3gXKMCvV6z0gXsxHViVnU/PvEg=",1023386258216615625,-5469836214839450552,-1870983814281535874,-2827721399817484757>()) {
                        case -1447445368:
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     String var2 = var1.ff();
                     if (var2 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1bnsonif9p984","1hWV8il3IVQKDbQC6tntFjYwrO7YVBrYP2MMCahCUSo=",-2874293190870749041,-1081471974607983064,3824820014633369292,9142280320082209806>()) {
                           case 96401241:
                              return true;
                           default:
                              throw null;
                        }
                     } else {
                        ItemEnchantments var3 = var0.get(DataComponents.STORED_ENCHANTMENTS);
                        if (var3 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"syjbs3w2ygqx1","ExRh7H9uxcHVUDeY79atfnuZOWCANUyGlUOQTDS7KrE=",-6373710668632006299,3363161128283745585,5095263341230686129,172782061967687773>()) {
                              case -1986293407:
                                 if (!var3.isEmpty()) {
                                    Iterator var4 = var3.entrySet().iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2zzqxo2bgqld0","Fq+GniagXA2wNOxwrgCdnG5qD7vsunSh6yE81JAs60k=",-3274368183902257081,-2924268514400036580,300965981655225578,1313909957069736327>()) {
                                       case -1777007305:
                                          while (var4.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1wh5bqvn5qk","4eY/LkOObW8GI9n4nz8otdk6HQ+pY+mh6sQUGm3HNRU=",7017646269850434042,3942227175681790243,4515364954142981628,2179681661399133704>()) {
                                                case -301692325:
                                                   Entry var5 = (Entry)var4.next();
                                                   Holder var6 = (Holder)var5.getKey();
                                                   String var7 = var6.unwrapKey()
                                                      .map(var0x -> var0x.identifier().toString())
                                                      .orElse(
                                                         (String)com.yiyiaddon.m.b.a<"s4s21c91luvt6","5aPpJuWti/679Ml2luNrA5XQuQh8DONdRVV6SQ==",-1363635341092256436,-7924254039041225643,4416648099872415792,4087543972013486163>()
                                                      );
                                                   if (var7.equals(var2)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sjxfvfgpx6y2i","iO5cHK0AbCggYU7PK+z5+DNUOEXIWYLJGA3WGE1QN9w=",3935053271252463451,-39494988051166040,-3191118999893838298,1299023243198833512>()) {
                                                         case -1199219507:
                                                            return true;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s2y055cuxuawpb","CuNfgJPA09SckilZDt4ZaV8lAOAFKMpk1zRxMKODWWA=",-8383891794847003828,-5196918987097404006,-4476530730670158528,63286604937395598>()) {
                                                      case 1781660662:
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s2wt2ohx0w42t5","+4ZBV0L1W9/+yZVGaW9J07hRgBw7lMfihb9V5NUWeeQ=",-7072042558275502149,4702250794917534086,5665896760843303650,1010136868463161491>()) {
                                    case 1565945244:
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
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3407g64syx713","cyL2YGTZNWUdzcue36uTGr6WDfqyvGj61PNlmdE1k44=",-799816520389837624,4834963008958051278,4075582917845909266,-948272980946917823>()) {
                     case 1879503985:
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

   public static String f(ItemStack var0) {
      if (!var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ln3lv4z69e4w","/3W8ZbED1xbqN3AGtCwhX8dwJJMa/Jz0xz9HiWpURkM=",-4733513628934161425,7264487197944093984,-7069190144905317126,8054980452008953344>()) {
            case 1741509902:
               if (var0.getItem() == Items.ENCHANTED_BOOK) {
                  ItemEnchantments var1 = var0.get(DataComponents.STORED_ENCHANTMENTS);
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s19n9tbcqd4w5","ciJA1zUJmc2DxEfIqf0TrTlKv1h4etTzG824PP+cnR0=",-1578458551488680014,-719063498262875065,-7772280732496257478,-7149417306322613334>()) {
                        case -200140519:
                           if (!var1.isEmpty()) {
                              StringBuilder var2 = new StringBuilder();
                              Iterator var3 = var1.entrySet().iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s2i38ntkp94ugm","xLV1sZlJXtHcstTMwY7XJyTdTPptL+GeWLWtncWe+Ik=",8353700290043004646,-4717027024640712181,6156678051978240054,9112476019812152919>()) {
                                 case 579615467:
                                    while (var3.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"soh577btte7xr","GMD+kKbq2JK7KPzDhflPck6W712wpNx2/SRNhTvaTrM=",5951327057038353560,-2553392630343706660,4305721458977306773,1029258623816807577>()) {
                                          case 2044415474:
                                             Entry var4 = (Entry)var3.next();
                                             if (var2.length() > 0) {
                                                label37:
                                                switch ((int)com.yiyiaddon.m.b.a<"s9cwgjb7jfk52","HsIjzKC8hZCD5r+0KWgOK37OSNQvXY1CUux5Ht7GI2Y=",4516414501101295434,399332093806346953,2718790381690313386,-1027763465413319358>()) {
                                                   case -1676453967:
                                                      var2.append(
                                                         (String)com.yiyiaddon.m.b.a<"s2v6q0wkciji37","hgn9o8pps9kw0PsgbeLKfA+W4F2t5nQJTgbiTf/g4qw=",-3899665102874238462,2686201384080891249,-7337070453961095215,-8361209566782622083>()
                                                      );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2n8qh3luhx7aw","UKDGJR5/wzncJLXRhrgmQIgOM60ChPl1CcShuhFnkJY=",-5863708376041574042,5062710207489256687,-1752988908474947443,-3300835351169950474>()) {
                                                         case -747700775:
                                                            break label37;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             Holder var5 = (Holder)var4.getKey();
                                             int var6 = var4.getIntValue();
                                             String var7 = var5.unwrapKey()
                                                .map(var0x -> var0x.identifier().toString())
                                                .orElse(
                                                   (String)com.yiyiaddon.m.b.a<"saz6w4r24fybz","p4EIsOBlTRddO+eZJ9Dke4TdU2sL+5ENU61tIbJtmI4KpId50c2MCgnp",-823544367348692640,-4795734775804953300,-1556915457655334532,7230656652886876147>()
                                                );
                                             var2.append(var7)
                                                .append(
                                                   (String)com.yiyiaddon.m.b.a<"s3i8tugltfmmem","sNNP5neEmYo9KKAKuBFoocf/y19eafLI4QeHotds5MTraQEM",6533828964592837134,3064889784808854461,8973853975333410544,1409712362569391307>()
                                                )
                                                .append(var6);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1lv2dbza3wbme","e6oFg/H/jd0brBJ9hjEzyh6d0ABCE76MOlv1vIUbYjQ=",6491260608308764068,3665851094745989526,7018607511274939342,2096703097926757051>()) {
                                                case -2126714486:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return var2.toString();
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sllfv1hd490ci","9CpmJ1uy51FYvd86GcHk0yJpnjcuDm7hz/HBSxeKPZg=",-436245326763798581,-985093902127588023,4499975753364127186,-72953210294792422>()) {
                              case -1830934434:
                                 return (String)com.yiyiaddon.m.b.a<"s3a8yrr9xnvadw","nzoxl0Iicc5llWuP41kl+XRDiwM1EQUDDAU225AUY/RwYcf+aDM=",-5523095922289463453,-6045033962989266296,-6654137538747829936,-4489693742680168464>();
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return (String)com.yiyiaddon.m.b.a<"s3a8yrr9xnvadw","nzoxl0Iicc5llWuP41kl+XRDiwM1EQUDDAU225AUY/RwYcf+aDM=",-5523095922289463453,-6045033962989266296,-6654137538747829936,-4489693742680168464>();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ky76x86wxhpq","u3OVAeX/hvB+FpJgyXzWl9BxVwD/hB0Nq4PfW8ve5Vc=",5231667001953238062,7741153843199064090,1821864393495467687,-7032301778666634962>()) {
                     case 1628173920:
                        return (String)com.yiyiaddon.m.b.a<"s3ub9354o9qmq3","AJ6nzAxhl+2WpuMPA3qLBb+icvhfZ4JOTJxWJQrYGqaEi2xS",-5589554981368577452,-3928250090916304944,-8632135115361827211,-2728844511889961527>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s3ub9354o9qmq3","AJ6nzAxhl+2WpuMPA3qLBb+icvhfZ4JOTJxWJQrYGqaEi2xS",-5589554981368577452,-3928250090916304944,-8632135115361827211,-2728844511889961527>();
      }
   }

   private a() {
   }
}
