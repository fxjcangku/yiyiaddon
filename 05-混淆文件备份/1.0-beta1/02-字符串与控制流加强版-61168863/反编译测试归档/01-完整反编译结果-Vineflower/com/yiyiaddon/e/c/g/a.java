package com.yiyiaddon.e.c.g;

import com.yiyiaddon.m.b;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class a {
   public static final int aR = 8;
   public static final int aS = 3;
   private final Set<com.yiyiaddon.e.c.d.a> i = EnumSet.noneOf(com.yiyiaddon.e.c.d.a.class);
   private Map<com.yiyiaddon.e.c.d.a, Integer> t = Map.of();
   private Map<com.yiyiaddon.e.c.d.a, Integer> u = Map.of();

   public void a(Set<com.yiyiaddon.e.c.d.a> var1, Map<com.yiyiaddon.e.c.d.a, Integer> var2, Map<com.yiyiaddon.e.c.d.a, Integer> var3) {
      this.i.clear();
      this.i.addAll(var1);
      this.t = var2;
      this.u = var3;
   }

   public int a(com.yiyiaddon.e.c.d.a var1) {
      return this.t.getOrDefault(var1, 8);
   }

   public int b(com.yiyiaddon.e.c.d.a var1) {
      return this.u.getOrDefault(var1, 3);
   }

   public int c(com.yiyiaddon.e.c.d.a var1) {
      return this.b(var1) * 64;
   }

   public int d(com.yiyiaddon.e.c.d.a var1) {
      return this.a(var1) * 64;
   }

   private int e(com.yiyiaddon.e.c.d.a var1) {
      if (var1.a() == var1.b()) {
         switch ((int)b.a<"s1mcv04kgukv05","yK3bNyy1mCqq6ZVOejTc07DjhigMzjIFR/l3NUrjCLo=",7411448642267414230,3480753883745771821,4467358436903159953,2488936427868181087>()) {
            case 822963319:
               return Math.min(this.c(var1), this.d(var1));
            default:
               throw null;
         }
      } else {
         return this.c(var1);
      }
   }

   public int a(Item var1) {
      if (var1 == null) {
         switch ((int)b.a<"s1onzcnjyfotm9","tiTMv7gIBqgFn7LfCbszsm9k3QLg25+Z56otNM4UHTQ=",351253942682920172,3129611726253099977,2090324347531571429,8088154247256783986>()) {
            case 268263001:
               return 0;
            default:
               throw null;
         }
      } else {
         Minecraft var2 = Minecraft.getInstance();
         if (var2.player == null) {
            switch ((int)b.a<"s2ds6r63f3luar","NCuQDTQPCnB53CVZ8ntkOMfKCNrmaLHlYb7dT+BSHg8=",6877768906551408709,5424635791074703208,-4816470058097263568,-7640380491523441607>()) {
               case -1551964572:
                  return 0;
               default:
                  throw null;
            }
         } else {
            int var3 = 0;
            Iterator var4 = var2.player.getInventory().getNonEquipmentItems().iterator();
            switch ((int)b.a<"s13c5pgvdn2ic7","JhdJFTMPF0haWcD9tb3h12IOP3ZJ+b1LpuIBUMUMje4=",521999043343067980,1331448813967581678,-5609506335632767311,4231387361835220769>()) {
               case -2024246791:
                  while (var4.hasNext()) {
                     switch ((int)b.a<"s2op0qgobl5wzd","qO6fTSVe3gt0cy0RBvsMM2aUqgjO6tO8039gb5i6nBA=",-5921971146246874383,-1601972750186013594,2922860674869624203,6330477273091758060>()) {
                        case -1193640223:
                           ItemStack var5 = (ItemStack)var4.next();
                           if (var5.is(var1)) {
                              label43:
                              switch ((int)b.a<"s2cs7x4d03k39d","u+k/gOM0GKrBhzIBH4Yc/dL8U/Ma6qV5SaeMmRD2ZdQ=",2942032039500226361,-9091005364541030810,-4731278991156650130,-2004890667655856235>()) {
                                 case 1085664021:
                                    var3 += var5.getCount();
                                    switch ((int)b.a<"sw7xss2zuwwso","BVYYndADLbp7USDlDBivSIqSsiVac0m1iA5BGRU1vh8=",-8198440691387982258,-5450058109781552010,6931701836885060623,-7835812347672914488>()) {
                                       case 1682283120:
                                          break label43;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)b.a<"s2alfvhj2zv2gl","akP54520kBnjh3GwoYF+uWwzcyr6dCM9XVopGvACLVY=",-9094255257849129079,-9208312731969346970,-7327122881016572107,-6538173949505153628>()) {
                              case -1741268161:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var2.player.getOffhandItem().is(var1)) {
                     switch ((int)b.a<"stx2pd4udbk9s","vWQvoyrfURQg7eVWxW1362iHKTHzr2jsyVZkA3B8/mQ=",4799687833915179080,-8040743011402660996,-4481162321519261275,2826644431792199225>()) {
                        case 67940990:
                           var3 += var2.player.getOffhandItem().getCount();
                           switch ((int)b.a<"s1pfgd2atifebv","cIXMdlEPz0cJLB3kmp0s7bx4YJx9kgZmCyg3F9PaQIA=",5567857770225339477,3622140276766414424,-3481671649720945862,-5254435232903611763>()) {
                              case -2069575752:
                                 return var3;
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
      }
   }

   public boolean f(com.yiyiaddon.e.c.d.a var1) {
      if (var1.F()) {
         switch ((int)b.a<"s1hx5t941zr05m","hSKLmNJ6SWO2LXWts4RqsVCjMuUxznCN/2EcyrmxwT8=",1120292181140878561,1086470139981959182,-259747313855027712,2844951340286930388>()) {
            case -1631689745:
               if (this.a(var1.a()) < this.e(var1)) {
                  switch ((int)b.a<"st3eehfgtc4pw","GepSs158jDNISgDy+l3ur842qUMexuVe4umrPIUHalE=",-1693767944860889423,-3337562380341802329,8375965443692700328,-6150622216735336695>()) {
                     case -1027722906:
                        switch ((int)b.a<"s2rb1fq0om0dq6","+EcqG6+c1J0TkWfGOGS5ATcJQTPiaJgsIvoPBWcOjpY=",5625993201398018619,-5717604907606322297,-5454451655784578878,-1941836961949092049>()) {
                           case -858994188:
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

      switch ((int)b.a<"s1cerfphe7e78r","TEVrVAa2urLmcGdHGQLCZ9mvMI1bbWXhJwVCricukMo=",5339892282462673658,-2250785576269122579,887227651407841774,1655975541969847086>()) {
         case -1668121694:
            return false;
         default:
            throw null;
      }
   }

   public boolean I() {
      Iterator var1 = this.i.iterator();
      switch ((int)b.a<"sg54klj0c7219","4A5C+7sG7vJa3RTEtyPhGsSytSi+GJ0GjWS4dmjkm8g=",9061209639014588155,1813284507720953377,-1535833651220909525,-9146579740027287138>()) {
         case 986313278:
            while (var1.hasNext()) {
               switch ((int)b.a<"s3cdealiiyd63w","gDJCsmlL7cq0KLc69AhzYx+oqYqzWvPVPjvQKX7Hanw=",5777797139909237244,6744283196295681409,3685903452105588062,2031069168276741694>()) {
                  case -1327570983:
                     com.yiyiaddon.e.c.d.a var2 = (com.yiyiaddon.e.c.d.a)var1.next();
                     if (this.f(var2)) {
                        switch ((int)b.a<"s28rpeboeuumkt","p70eeYZmb/wuXrLEUaiAjUQgtX9ns/87O+JrVMpdXmA=",7454580197796110765,3019273428485804584,-3187165992535115057,3285821894957267387>()) {
                           case -428506236:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s180kg8sdrzcf1","EUnXx28ekoOgMq0uVTjIfmtPXwAsMJkVRRUXdmD24oQ=",1356347517470762470,-3235006156460702642,-2278558546251808704,-7900987039389330722>()) {
                        case -1583667423:
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

   public com.yiyiaddon.e.c.d.a b() {
      Iterator var1 = this.i.iterator();
      switch ((int)b.a<"s7d9gk14ppkg7","13aeKKpBxHjlYGhCENMiX9Boz726bmRw6l1tbVcZE8c=",7803567945282213896,-1313276436562603718,3737620257063468474,4893977622481292076>()) {
         case -1663532300:
            while (var1.hasNext()) {
               switch ((int)b.a<"sn2789hxmfsgw","KuU3BX/HBiN5mflCtymgFn2MD+jSs0ARYy2mYG9Auj4=",-3706967953220218565,-6690885956085877401,-2404121896965681841,8848757692097225533>()) {
                  case -1283856405:
                     com.yiyiaddon.e.c.d.a var2 = (com.yiyiaddon.e.c.d.a)var1.next();
                     if (this.f(var2)) {
                        switch ((int)b.a<"s1p5wb1uh7ikkf","wZXgS/Oruu4VybezjOXBvuNk1MEjnDCqqtZgjT9SWbg=",-6559388847865894905,-4139911395442854012,-2255722306430953797,4935884152181941404>()) {
                           case -1199494068:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s2oyacbgml8xh4","6qb3KJAJmb8GIy1EGv878Qd7hhoBL/07EfsyQH0mDzo=",1164273382720462771,-6900750261699039787,7361728565723208038,-5846019211926926061>()) {
                        case -765280451:
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

   public boolean a(Item var1) {
      if (var1 == Items.POISONOUS_POTATO) {
         switch ((int)b.a<"s3aadke63wfdyn","nvkKqs1vzXXXv+PIIMfzEEwm8/Kw6zg0LHt8ApYaA+I=",-2083271899639457502,-3399813907468261628,-76079946576585778,4357418206026163571>()) {
            case 2135592714:
               return true;
            default:
               throw null;
         }
      } else {
         Iterator var2 = this.i.iterator();
         switch ((int)b.a<"s28ko3bcirqr2h","SGdPg1Ro1Cp8Esiw0RlSsS4lI1EuBnHOOlq6wOjjgjA=",-2237674894880798274,7151244611646958048,-755822442085161884,-876349995758577324>()) {
            case 1418387537:
               while (var2.hasNext()) {
                  switch ((int)b.a<"siy2jntvj7dn2","HCmWmeQHhrKJJ4XQnm8vq3YUFtokLdhc/bVcpczO6uY=",7755269860570157973,-5325967034524027979,5846090539591393067,7150633958202169084>()) {
                     case -1578433119:
                        com.yiyiaddon.e.c.d.a var3 = (com.yiyiaddon.e.c.d.a)var2.next();
                        if (var3.E()) {
                           switch ((int)b.a<"s3cbcaz26tr608","JnXhAqLbHWGXyng0tlPpsu5u9Ca6you9M6IiwinI5bo=",-3298759126540191651,-9089356854433722671,-3140546784667388573,5599634339420682876>()) {
                              case -577205071:
                                 if (var1 == var3.b()) {
                                    switch ((int)b.a<"s2t3ezc8l8l2lf","V4dCd4bNNCNa1LZ/NDv8bqHngvYNZI6N4DesXpIJK5g=",-2808018838914785714,3625735541390440823,4386674167353630091,-2213501144066284139>()) {
                                       case 1227529584:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s2n1rogc3ugmk2","4OZ7XQbluVrEkuh8HAxun0gf7vJPcgiyqOBeL7E8SqA=",-1413847689777336447,-1410357848060962338,5302252499649374743,2741635743904386647>()) {
                           case 2016701787:
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

   public int v() {
      int var1 = this.a(Items.POISONOUS_POTATO);
      Iterator var2 = this.i.iterator();
      switch ((int)b.a<"s1pppbav2l4f62","rLKZcVNXriRJGIIl8qlRSPPxgM7RYOEkgtAaIXM3D0Y=",8451919724970842592,9143607669142197222,-3391416658992923912,7047361151709795316>()) {
         case 1636404672:
            while (var2.hasNext()) {
               switch ((int)b.a<"s9ay0c7xft5i3","rgavKpvrztsXjL+WrMTpZ8BCfJvb1RTKFNVobHbiPGs=",-5560896922084974117,3299394586056264021,3145674547432885240,-4510785945567013022>()) {
                  case 1375958161:
                     com.yiyiaddon.e.c.d.a var3 = (com.yiyiaddon.e.c.d.a)var2.next();
                     if (var3.E()) {
                        label23:
                        switch ((int)b.a<"ss0l5f1ga4q1s","UyOIFdZ2Jc3c49RdY5ij3T7NkE3zLTpTSV9jU8ouH2Y=",-6300901787011033889,-8169918103928438715,6414495468267569236,-6328288693186712633>()) {
                           case -1160064971:
                              var1 += this.a(var3.b());
                              switch ((int)b.a<"s3aza1f95s0ljy","8zz+TKaApBJ1YsXTr5NcUtkhXEnWmAlqLyOo76DHZBY=",-5944331242691186701,5925895645328893298,-6008799160750890965,753368428469495739>()) {
                                 case -560524811:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s2rx40ez5gxsyc","GWIVIyxD/rcMr1P4vb9BQEXeCpaHGR1QK7SRv5+dYIk=",2158719987660741159,469498724716608319,-8920473433740663679,6559148392599579981>()) {
                        case -1277397356:
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

   public boolean a(ItemStack var1) {
      return this.a(var1.getItem());
   }

   public boolean b(ItemStack var1) {
      Item var2 = var1.getItem();
      Iterator var3 = this.i.iterator();
      switch ((int)b.a<"s38itbgb2xslt","h2W+MrUt117KSN+6iO0Ecq348aeVCu6zNg0xYoDYO5k=",8776595309513803332,-2978426264216849966,-5949818255786555942,-4285326091280842857>()) {
         case -938286388:
            while (var3.hasNext()) {
               switch ((int)b.a<"s1o9d86df4c4k7","JGJlp5z+PjUXWWvRJtf7DIx9GjFMr3EGdYS03cmMj28=",8113203932386637616,5238148490213742154,7782957819194302035,-997943987842870716>()) {
                  case 2024420848:
                     com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                     if (var4.F()) {
                        switch ((int)b.a<"sqhi6y9ophzk1","jPe/VsDUyABHlHZ/9yRTanQZwfcW35JC3mv3JrjGRhU=",3321736076474410822,-2137143109790405743,-3123514465458983821,-4527653797106513801>()) {
                           case -1658879917:
                              if (var2 == var4.a()) {
                                 switch ((int)b.a<"s28ym3gtvvbwg1","eMXYAoLHNYTekLcTe0Rr0rSTyCaoGYSsO2/tWjlhtfI=",-1563636492998989809,-1998729356386382268,419051892043114597,880221322426141940>()) {
                                    case -653962613:
                                       if (var4.a() != var4.b()) {
                                          switch ((int)b.a<"s95rvikoffkki","uhAsUg1FfmmrRonxbmHuX6BEWNqGd1lRHk0+A4I65zo=",5407192287733044799,636461266656028177,5154236264749523108,1026754461435347658>()) {
                                             case -733340523:
                                                if (this.a(var2) > this.c(var4)) {
                                                   switch ((int)b.a<"sdn2jisdi7oo8","0rt/O++PrdwtslAL6YuQMLjl55/PimcZ6wWiu724hLw=",-8371344176593215906,8066443765693292382,6056287429744226853,7040124884500183403>()) {
                                                      case -311854354:
                                                         return true;
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

                     switch ((int)b.a<"s2utrfoovr829e","KEJNBgTLSVMDfVbOeA0I52TdM0Qj9+0jkttULrH6Yco=",4913049662932333986,2407433452804414749,2791931398596301293,-2656033253693126684>()) {
                        case -1955766986:
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

   public boolean c(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 == Items.POISONOUS_POTATO) {
         switch ((int)b.a<"s204t8vgh65lmv","DEaAtZ4i0J0FPJTgrs5RlGiE1aNHjJ4q21vnv8rHMMI=",926570841794078553,5720619962246073602,-105684614153620235,1463373590007847732>()) {
            case -1788377599:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = this.i.iterator();
         switch ((int)b.a<"s2l4obv8u4d4ik","PcceAGHTbL1eEQzC5+pOjspROK+s/+haYKZp4qNo9AM=",-4868927748161301731,-848473897174829540,-6811482560809545671,2843140832807044357>()) {
            case -61224369:
               while (var3.hasNext()) {
                  switch ((int)b.a<"s148spnog64q8y","f2u0uEB7ik3jqN1wPE5Ct4RkjxkHr9Mb/M99C5t3PX8=",3007902059142413427,195170709416404001,4131275403587298343,9154692959449130271>()) {
                     case 702322769:
                        com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                        if (var4.E()) {
                           switch ((int)b.a<"sey8oljv6fo0e","2vI9i3y248nFFscrzy+dtwq8eEen7QrcQ0b1g+gN9rE=",-7825341249424212884,-2246936410093892410,2785586586795322970,909667784305783814>()) {
                              case 1016968589:
                                 switch ((int)b.a<"s37e41d30737wb","qg1C6CaTELU7dRGlvtqho88InQ4IAuu9hfZa+rDT9Lw=",-5182010388244991883,-4309518137657225478,-5246435480089188730,4657149570858281776>()) {
                                    case -805764306:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (var2 != var4.b()) {
                           switch ((int)b.a<"s2qje29tc6i0g5","o8jtl1uAyCtJit5RqrEhO6MGWPs226pMgP52i0phP4k=",4444186796757457932,23263172913858009,-4935507332449483255,600458909484570660>()) {
                              case 1648347307:
                                 switch ((int)b.a<"s3to2zh2aynwsu","1/X5ki3Xq3xiAEmLT34WUFDlZpzUaOi7NbXmA7zmoHY=",-1119441217559514924,-2235006764015930137,3857076758955082524,-1120575965354398238>()) {
                                    case -1804993817:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (!var4.F()) {
                              switch ((int)b.a<"s1a7b2628jqo3f","21ZGJQ3bmo7bgtxwkiaJGlsWkqRtrD4cP2VWLxkG960=",816582117391331089,9190754164793489933,-5071294311555629241,7568390815638964348>()) {
                                 case -635783906:
                                    if (this.a(var2) > this.d(var4)) {
                                       switch ((int)b.a<"s2s2pw0k3okcfa","o1XJdAECDUO7xym3/zT+ITLhiOdeFMkkiyFT4wnVBI4=",-2471030674289555444,-511373967046751319,-8098109310319379472,-3577647708046881727>()) {
                                          case 1398145604:
                                             switch ((int)b.a<"s3kzxzzrplhilj","bH+NwNwTxfkFW+IZbzKjTHHC45Moewyu9qmQ774HKQg=",4909842009905721479,2298602140379764666,8538366037516295364,5687855350514638047>()) {
                                                case -507801941:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)b.a<"smyubmmn6ctul","+XfzK06BeLjlYadQ37TTCpxHQGkgSjKnjpPiuHAoBzc=",8548445039808825121,-249220256894972363,-1631515901189604611,-1784089377687325459>()) {
                                          case 1657871576:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var4.a() == var4.b()) {
                              switch ((int)b.a<"s1hb7rajpkd1d3","kni6mn+x5YDCJnA8WtFNnvc/U7ZMHkDrPpuvPpqBjWI=",5838663366869186598,-5646835196196779988,-8204900842415669524,8171964427207136328>()) {
                                 case -116756068:
                                    if (this.a(var2) > this.d(var4)) {
                                       switch ((int)b.a<"s358ify04r1bfn","1uQOPDlDozDVp0d1vt0k9krZo+A88PyEDN1AhE/bo0U=",1156524224054411127,-3996581732984522990,-6280189276650073752,1942432953130246952>()) {
                                          case -1470544187:
                                             switch ((int)b.a<"s25iml1ycud8sb","VOy8l8/uUBxtVpJw/A449ydFLHRGMSw4uzTKAZZzJFc=",-4598769117956188276,-6667066028931085316,4790219781710142893,5561906742840190075>()) {
                                                case 953470654:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)b.a<"s11qp18yddt2j9","GRuWVMTqZpYm21dySw85M/6X7UtHd+B/yTudKfJe3qs=",-6671754857190364565,-7005679178879290553,-1333835050329576708,-8704883287294504157>()) {
                                          case -457073244:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)b.a<"s1tq9rwimbea8a","N/Y7h+AcNXx0Q2CWF50yLV8BsH03hc+tedKvoG5W0Z0=",-1170284600594910642,1213017564907874086,2345161832943329788,-2484503619758212355>()) {
                              case -2111896108:
                                 continue;
                              default:
                                 throw null;
                           }
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

   public boolean d(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 == Items.POISONOUS_POTATO) {
         switch ((int)b.a<"s18p4vuxozmqvg","VvnJ8Ujdx6YAxnP7FW6Zf5MmdRMfUppWlxrJPDq3Lq4=",7342877360134690447,-7270852208636875892,3708493674372781183,-2693186675231297027>()) {
            case 1596418923:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = this.i.iterator();
         switch ((int)b.a<"s27uyfn8m6a021","gGxJcgn6xAvkV9N0eka5r48pa6q0CxViVzwY8E+I278=",-7297534896556457398,-7503183882857009685,-1420137129045300520,4293230651257777395>()) {
            case 344868999:
               while (var3.hasNext()) {
                  switch ((int)b.a<"sz07r4g327g2e","8uFk2bmZ6Ho9Ua7xzLQKG/Z1Oh12B19B72i2vVYnRB4=",-2691059714722867708,1090835299694719842,1090923800277955541,-2809233981324928510>()) {
                     case -1175151851:
                        com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                        if (var4.E()) {
                           switch ((int)b.a<"s1edl23rzri6rf","UExZzF1j5M2O72qkckTgJh/LLhGTKLX6yyT7bpujH1Y=",-3958211234681866848,-5158956062643641794,5054968375033589569,5747250008289911683>()) {
                              case 417018223:
                                 switch ((int)b.a<"s20d84vtm1yyoy","oqe10fA8j+Mx+gMM4egqoncRhVOUxz/gOE045vL5Rmw=",-163264055284993436,-525249014674365179,1873577612892057202,2584621895704016583>()) {
                                    case 1118654146:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var4.F()) {
                              switch ((int)b.a<"s2djmuha5hsazd","qtf3MXSs9XKtlDIBgHBmILldwCyYnt8hzd3MgNsuWPI=",3303603065648807123,2813089467977940546,-3178790390140002400,-3921581815069974510>()) {
                                 case 1067195069:
                                    if (var2 == var4.b()) {
                                       switch ((int)b.a<"s14xguorsjqguk","cR/qnRXUGzCmEX4pMvuZKXlyhahWnO0DxrtWxBaUtX0=",8494494629237281482,5037406096288521943,-8837741121934302187,3907880953352502907>()) {
                                          case 492454146:
                                             if (var4.a() != var4.b()) {
                                                switch ((int)b.a<"s76rsout39hxf","14BiFVUypmRHYhPkn9XjebBjcTvLpemDaBCQ6DLKzSg=",373723054822997876,-5667975088009432130,4792351175657511605,-5562805253056245611>()) {
                                                   case -606734189:
                                                      if (this.a(var2) > this.d(var4)) {
                                                         switch ((int)b.a<"s60qz34ql8ohc","WZppoCr2iU8/K096O0irbO6pyidBwe2C+CpBkL4U9ds=",7825038433949941974,3991900712174664849,-2782089709332860773,-3058930226400049555>()) {
                                                            case -154193708:
                                                               return true;
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

                           switch ((int)b.a<"s1mfq82tmeukdq","5IlHfPq/DASkDW3SNraQQBv6JZazGsPHJQ+RAHzsCyU=",-6712862109840008126,4586234326540647896,-8097678233337157482,-1288952539359585053>()) {
                              case -2085973369:
                                 continue;
                              default:
                                 throw null;
                           }
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

   public boolean e(ItemStack var1) {
      Item var2 = var1.getItem();
      Iterator var3 = this.i.iterator();
      switch ((int)b.a<"sllkqsx26cq5a","DL/GsY4Tb3dfWWJ3DoFYGgO6h2QwyNMMOVPu9CS8lXw=",4584627292268305185,5916920448679273269,-2260987079221824292,-1960445019248340614>()) {
         case -42222218:
            while (var3.hasNext()) {
               switch ((int)b.a<"s2vh3te4zq8tbk","0n9e9ZEpI7Te88I33OAjZV+xZP1lcaF1Wzu8va48pOI=",-866627047790948905,-3228326396656305810,-324785731297424315,7911251797025746338>()) {
                  case -1116295076:
                     com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                     if (var4.F()) {
                        switch ((int)b.a<"s37ybvvj8isi1p","/b7rDC32i4Owygb2u1JKRhc0A9ESELfW0yuMV2ypk9M=",1920823868484060851,-4133576559604755540,5338343819722610411,926685219250677103>()) {
                           case 2101734023:
                              if (var2 == var4.a()) {
                                 switch ((int)b.a<"s2hlceoj1qpntc","Zjzngd2e81b78xg17hPNx/lKwHo83EIT4N9cVAj+tPQ=",5926325042271731696,-7327705130053279375,2975093971155454173,-9166920504498003566>()) {
                                    case -715119163:
                                       if (var4.a() != var4.b()) {
                                          switch ((int)b.a<"s317pmfnek79tj","8yVbe0qB9CPItbfOVApRyElKJaNobEedITbirYAyjaw=",-6781057068838132667,990152781470972547,-5710913168486387203,8263735140203200961>()) {
                                             case 77494927:
                                                if (this.a(var2) > this.c(var4)) {
                                                   switch ((int)b.a<"s1ozsstpur5746","IDjpraj3NE1HOxC0KBwBnQ9HSfDhFPJWN7GSMY+EvHQ=",7957463380366513629,-1176037731111598718,1166854110047430076,7882529506958767196>()) {
                                                      case 1153988155:
                                                         return true;
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

                     switch ((int)b.a<"sjor30bvf6odl","VttvVxOgcqovUCSiQZkcM/uThsvNTEh3Nr8f4PD7A5g=",8804730266377870449,3122677512047629737,5473777560964769695,-3816415645908408722>()) {
                        case -643602811:
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

   public boolean f(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 == Items.POISONOUS_POTATO) {
         switch ((int)b.a<"s2neyg8448geh","bJBMOyXzEskf5FQ8YaUs549jTrrF2jJF5SliLIe3A0g=",8917247335958771125,-4428936533104519438,2367021590808571561,-5375530014103468052>()) {
            case -900428228:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = this.i.iterator();
         switch ((int)b.a<"s2frsgvmn20inc","lV8pumyvDJ7CHnDRODEcV0MpXYplZKVyhPEiC2UMJgo=",-4370569669555691287,-6349004523562186131,-1690344623375818593,-4786715150476397773>()) {
            case -658953966:
               while (var3.hasNext()) {
                  switch ((int)b.a<"swaruvypu92aa","0tximRj/by39n7hVmqw93xvHAO8gDGUoNS1JF5JdbeQ=",4862932628558027629,7576031571958137930,1354048678952254982,-9025560201109898829>()) {
                     case 263501084:
                        com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                        if (var4.E()) {
                           switch ((int)b.a<"s3hpeo4qcdq3tz","7oCuI19iGKz7oi5NtqNxacf9nUD7KozqR7Ll3EkKpEk=",487252616544168968,-4247161768286810684,-6047608886639863077,-3432940035178025039>()) {
                              case -1792290584:
                                 switch ((int)b.a<"s13gz79xhfmbmc","4mJSX7O7bjMjQFP3wUHHU2ACLdbzIORNKYAsoEH4ebc=",7219316758605275674,5272400200383440311,8487892684287681930,-3012060608271298840>()) {
                                    case -1902211762:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (var2 != var4.b()) {
                           switch ((int)b.a<"s3flq2s11iwywb","aSjxKwVSrBywhJX8cK4DVuGifxOd5xy3oGTcKMvMgCA=",1149170736558421653,-1262917688716505454,-6231172215821281940,7621050938825894315>()) {
                              case 828675176:
                                 switch ((int)b.a<"s1j0u5yyqiy6mn","2VO0KefdHAAqDZdFycEhhEjUlweSxBfpfBsLwHTz+fs=",76271426459174444,-3618908123693161232,-2117752127090546129,8367717161471541961>()) {
                                    case 1694230744:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (!var4.F()) {
                              switch ((int)b.a<"s9kdjkth6ds8r","givtDV0wVMETAPxrk/eDXBFzVuw9/liWjZSrt6Z6MLc=",-8002468103701698027,-8268282253115829972,6626136551622966519,842019260644354431>()) {
                                 case -223869258:
                                    if (this.a(var2) > 0) {
                                       switch ((int)b.a<"s2sq7c0ve0772k","/WGrEmQM37fMXyhJ0+TKIP7vBs+n6NjdGbXQu3qky4Q=",7864648093981278115,7527791121861806077,7123186704159454297,7409734078352598882>()) {
                                          case -507198915:
                                             switch ((int)b.a<"s2nq1hf4mwo2px","DFEhGvbfJG9GesPWdf6Y3fEMPXVF3YSYKmtQlrREiBQ=",5595103086947628991,8850683378643785698,3138958996278986617,-4632271604603611342>()) {
                                                case 375093123:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)b.a<"s1bmwnfmybhyft","Zab7ztIoRE6LF5Qqr5JbdOPNSGEvn3tJLuePWxEzuyI=",5607468446917794133,-8086958752013816758,3626243530903356142,160839894277575039>()) {
                                          case 333417512:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var4.a() == var4.b()) {
                              switch ((int)b.a<"s26dn8l0ozv67x","d7lXPeGZgEBvl92DSUD2vBUQZUBc8tZvgJUmtfjsDyI=",1945428842152934268,-2857714535878568748,1135795971324075387,4686949789211029253>()) {
                                 case 1172766703:
                                    if (this.a(var2) > this.c(var4)) {
                                       switch ((int)b.a<"s292307j6ji5sv","cga/lzVPsUMQD6aLt92lyYRJCZBRvc2XlNGwJiEe+1E=",7638852081954825089,5181986942948297774,-1700591421332118368,4628084361802305188>()) {
                                          case 1555868554:
                                             switch ((int)b.a<"s3sqezkwkaa3c2","VIfhRrc5uezhWr/AT3Uhqd9ACcwM+ErpUPLUg/3/Rjk=",6453286003687303975,-8640415365475009288,-7672091843584183757,-7490361518304482588>()) {
                                                case 362585511:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)b.a<"s37sr1l1a9ijtn","AP2szlK1lMTLLm2il3UrZ6yUx+3FNzaRTqA9LLOI3uk=",-6639673192970632620,-1358372064860590439,344677301398956208,-2594332848663486048>()) {
                                          case 123278967:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)b.a<"s1hayqrycf8cnc","mA6y2zHleg3m8J5Vlfp2uoMW0m+keIEhtFTKCkMmX8Y=",-5232679757957266103,5559474123412757881,3548664875023984201,4571437168598069164>()) {
                              case 1971648039:
                                 continue;
                              default:
                                 throw null;
                           }
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

   public boolean g(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 == Items.POISONOUS_POTATO) {
         switch ((int)b.a<"s1ypsb7p4ef3n9","Y9ys+mJczE+kqppn8uuqhubuICP9O2aSDZmxbPO8x0s=",755328965913619763,-4291641408752920038,283031761539956815,5169439248726628277>()) {
            case 1440547981:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = this.i.iterator();
         switch ((int)b.a<"s3rxpvzsqdsoab","re5mGQfqIj8dWdtn3wE3OkBqnoEVFMO8O9zCRBca7EY=",-1004632103174420234,-17900489447275196,-3711923843641271628,-4304125467192463466>()) {
            case -947938069:
               while (var3.hasNext()) {
                  switch ((int)b.a<"s16uris6rrgeyn","fTZGu48qPq3sVt18LswWewkMkaRs/zKuazVp6+RRZ2U=",3711304566790334310,6895913013106829612,9125510437412932204,4526150786086165401>()) {
                     case 861556135:
                        com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                        if (var4.E()) {
                           switch ((int)b.a<"sc7cvmnr7in2a","s2cRc57AinsSH0wU8gdX7O85dOMgr3bRZWp2oKUYFxE=",107481684082707733,7244237028014039995,4925794987210875661,5554239642564555583>()) {
                              case 669879826:
                                 switch ((int)b.a<"s1skd6pmbwx9s2","LqV6Y9EXxSPRfyiwZ1Ks8AaUXWD2OqywiAKa2sggw6U=",-5133003870820413607,3488239999499736080,-6401318716340927111,-408329218343308612>()) {
                                    case 2095721014:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var4.F()) {
                              switch ((int)b.a<"s2geolts9mqwio","4WQfZMieTUfkXUafBrHoyrBzfpCr5qpIx/w75Cvjyk8=",-6541442807327665120,5193234974101427636,937555665922742222,544218115415608849>()) {
                                 case -1070294705:
                                    if (var2 == var4.b()) {
                                       switch ((int)b.a<"sq4elgibr9ka7","nPS5mXPIzSPx5QQmJLNrOIFc7sB/Z+Vrv2ql43WbD5A=",-3410078839075720521,6363331479595163383,-5450219766193748060,696607713360222737>()) {
                                          case 1692694026:
                                             if (var4.a() != var4.b()) {
                                                switch ((int)b.a<"s3r8prj6qld1pg","Rxa7skx7oKJY+3ASW4WETrl8UZlbTXEPp98k058ai5M=",-4436272743337650392,-6591183992210428589,7665925864652039010,-5152030613319006842>()) {
                                                   case -1029647299:
                                                      if (this.a(var2) > 0) {
                                                         switch ((int)b.a<"s193794lnmcu67","VjSZENU6uCliasUJJBweKtNPNRRn386xMHEUSA/m/SU=",-3595687965373308408,-3958688635929467120,-7224285218006519620,3129016117966958363>()) {
                                                            case 697564717:
                                                               switch ((int)b.a<"s2uic7id2jul4a","9xfBfCLDIuTWSKPjLzo8NpAr4mA151/ApuDoqEXO6Bk=",-8375154599605623564,-5999383671880618500,-4459815142152289916,-8813013474809722727>()) {
                                                                  case -1390880685:
                                                                     return true;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         switch ((int)b.a<"s349ac9zk9lww1","uE0dMrav2ZEtA7D2H4ygPni5mx5g0BK4mA9HFrgjIeQ=",8547207604140190073,-1044022690003688213,-6390423955385264683,4166949029914279443>()) {
                                                            case -1969325529:
                                                               return false;
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

                           switch ((int)b.a<"s2qhrugcm5xitd","mhIM/hDWPXM3pVYZcA8Z5kdFXb4+geiAIalSxDNo7nM=",-1078488814281390098,-8045010246620804765,4715249390003392066,-318881677400932917>()) {
                              case -1655549431:
                                 continue;
                              default:
                                 throw null;
                           }
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

   public boolean J() {
      return this.a(this::b);
   }

   public boolean K() {
      return this.a(this::c);
   }

   public boolean L() {
      return this.a(this::d);
   }

   private boolean a(Predicate<ItemStack> var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)b.a<"sq5znhx1ut5l5","DShst2Ak/B/F630knTi7vx6JgDbWf/lmth59YRljRn4=",-5382639169540729610,2580195462301122221,-5224219861769947385,-7931689031253379060>()) {
            case 673440562:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var2.player.getInventory().getNonEquipmentItems().iterator();
         switch ((int)b.a<"s35on0on6ilog2","FR2iSmzEBsj8kZ6OXv8PqpfRZBHjEjRYYDbSlKF8yp8=",594529855098529465,-7359902643841965594,-8179051697963853100,-8659995380112957798>()) {
            case 871847798:
               while (var3.hasNext()) {
                  switch ((int)b.a<"s1thuvv7ieks31","gTRiMefK/lhxLuf3dkT7sWarC/CkpqMML2fPcDz8UiA=",-7006423955887218256,-4343223873007070591,-2756812948122764290,-5431611848729921345>()) {
                     case 34506615:
                        ItemStack var4 = (ItemStack)var3.next();
                        if (!var4.isEmpty()) {
                           switch ((int)b.a<"s1l3dv1llqxeyx","VHg8VHVDQOaqNfuUqjJXQ81xulNtLSg25BUoejYYlvw=",4543428685522337656,-6528138746431519709,-1804398928696620590,9150968647341927655>()) {
                              case -1364433788:
                                 if (var1.test(var4)) {
                                    switch ((int)b.a<"sqkyt2ih8ttej","m8F/DEyVD8M0wEfKoWqLfLbI7aTnQ8HyFYvjGBBXWFI=",686937450492030655,3086233277001899106,-512119772752863617,1293488217257993625>()) {
                                       case -1421935793:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s1mlmzhnyr7dr","r7OdqHSU0Smc8SGb+2nSp2HsFoQDExNIfWQ9aThwf9w=",-7031063846218449218,3419497725290584767,1969962262508441886,-7429133410597080356>()) {
                           case 374341417:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               ItemStack var5 = var2.player.getOffhandItem();
               if (!var5.isEmpty()) {
                  switch ((int)b.a<"sa5ebbqbv0omc","EGGEePY0YDhztctyrts86iu3AbIbcz9sRWmMQYM6/Yc=",-3078547170604048685,7312241766779752463,6904332757873802489,-8885021021970227781>()) {
                     case -1775195787:
                        if (var1.test(var5)) {
                           switch ((int)b.a<"s31nz0pi4j19kb","FKh/XF7N+rkFFQSZQcu2X2hLD/HzUgDVjCFJEVlpZaM=",-5369564413847453745,6950928201517026222,667504299615320508,-8911487262159807561>()) {
                              case -1196665285:
                                 switch ((int)b.a<"s3575p6shvq6x5","Lq7I1uDLCFrEbtXtPyun69aU5btttS40pMI4dtAip7o=",7422871812526261105,270119999386701964,-692547756038844010,-2494059328500198780>()) {
                                    case 584254597:
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

               switch ((int)b.a<"s1tvu7f3s2h8j0","qmYv40q0Y72CuovpVHBLEGxA+SntpvSppNCk1hDbTkw=",149787533097946333,-6750821994510981781,-6937045204520440214,5723799246733027906>()) {
                  case 626296214:
                     return false;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }
}
