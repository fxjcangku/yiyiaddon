package com.yiyiaddon.e.g.d;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public final class l {
   public static final double w = 0.2;

   private l() {
   }

   public static boolean o(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nhcy7lb5i73l","ytINAxHeuoSyWieJHOrld8yYmq8REIMtQ4kU8pA3jdQ=",2884572898304796187,2545015294262445229,4674012989986873507,-3162249858861334765>()) {
            case 912541222:
               if (!var0.isEmpty()) {
                  if (!var0.isDamageableItem()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s10psjmskqowi2","iwc1/FjQJvVc92WFHT6/ocSZr2asAhFMOhELvswLNr0=",-7824159650739850779,5355989823439519007,8529656366974471717,-4865445189655658446>()) {
                        case 659088776:
                           return true;
                        default:
                           throw null;
                     }
                  } else if (var0.has(DataComponents.UNBREAKABLE)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3whac8ezzm2e","eJlQYfd1DLVyPrahZFwx2Do/AB5e9ik25xHwnaQQHRg=",6713060567096510840,-7454318339929410036,6761091729032224153,3556008883753381030>()) {
                        case -1595698144:
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     int var1 = var0.getMaxDamage();
                     if (var1 <= 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1591gk0dvi9jz","ZoLaCQPGQYTrA2iQoKjIvTmxTb8Xe46UIQebXKjxIbo=",-530505804716150440,5712238405119601474,3514437311220045976,8271147786910058149>()) {
                           case -1770645658:
                              return true;
                           default:
                              throw null;
                        }
                     } else {
                        int var2 = var1 - var0.getDamageValue();
                        if ((double)var2 / var1 >= 0.2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1cm7wtbisrxua","ZOK2MDZ+aBZh8wqaWI7UUbdtqQT8/+P1k+4tXmB1wX8=",4828512259236442185,-7416837365327146162,140475107494765337,4889715161633240282>()) {
                              case -408925880:
                                 switch ((int)com.yiyiaddon.m.b.a<"sba19323h9w4t","qhWbGJbkM6v19T3Ucz/Ie+hjYbS3VThZzT2p6D02SXw=",2115750593312175357,6992770082712639300,-8242602945277137639,-3747411811957750298>()) {
                                    case -5430436:
                                       return true;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s22hk8oc2xsks9","TyfgvmIVXvVqdjvgm6KdPN4a+pZGGRRavOa5mOfXcAo=",-5878519680730316358,-1800218144020342270,174824108043734101,-2320910999370012392>()) {
                              case -1686600676:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2kq3k136g1r5l","tApIW9ZuqkMaMQd940qeA9Vb/kKHwgNu8EZ6WTfCizI=",1742496240041284257,3910243169893780191,1297419959811616778,-1794728176263143773>()) {
                     case 1908633097:
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

   public static double a(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"stfhe6upp8bfj","tHL9kmc/TC4u1KclOorBx31X3k5WFJg+iNahlXPzmiU=",8983659921573849221,2513262382534756914,891494899704587388,-9127959412658775562>()) {
            case 642595891:
               if (!var0.isEmpty()) {
                  if (var0.isDamageableItem()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ejfa8tslr5nk","+G+xey04pPpR9u7HGpHQh+2VeZa/mPfi7EmF8/QPa7U=",-8969364028844502402,-7907899198554779235,-2149588633997083521,6767449680555069062>()) {
                        case -1541189322:
                           if (!var0.has(DataComponents.UNBREAKABLE)) {
                              int var1 = var0.getMaxDamage();
                              if (var1 <= 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1xh9ux7da7orz","KMGjNliNoiTGLU1Okahbmf15w4ZT/C90cYr02M7wBBs=",-1409119977390145657,2105233227193148093,-7373537923431289532,1612541095555287307>()) {
                                    case -1932696424:
                                       return 1.0;
                                    default:
                                       throw null;
                                 }
                              }

                              return (double)(var1 - var0.getDamageValue()) / var1;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3srve6uwjy9g","uvzDWyJq7U4h6MypyMYqrj05WCraW2O0LKkRbj5szfY=",-5600492593060136939,-113034242762248431,6110201052114665927,1161416278855178421>()) {
                              case -245090227:
                                 return 1.0;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return 1.0;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zu807qnlupbc","YgC5i8l6e+dQKrdr6p+FpncreSLdludvWiMpnfZGtVo=",-4797949364670766604,-3329315522687446772,-3548726930322330681,9005732065536191494>()) {
                     case -1978751347:
                        return 0.0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0.0;
      }
   }

   public static int e(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pzl176le1jof","LENCyiL5EMv2rGok/WJMXXYjAnodUOlYOmKktiQyy0I=",-6931887775344599920,7505712217040350543,-4660267106135700723,-2825020841094783803>()) {
            case 875458719:
               if (!var0.isEmpty()) {
                  if (var0.isDamageableItem()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sevtvtb6rjrw0","lmRXivC/TqzcWDWmnifLfWgvGCisu9s/+OYwJPv4HOY=",-1649730472242833317,7510972568352153480,-1757437802895915895,-4469115407913895071>()) {
                        case 2143369422:
                           if (!var0.has(DataComponents.UNBREAKABLE)) {
                              return var0.getMaxDamage() - var0.getDamageValue();
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1my6y1nu6h52u","HmBHuhdmOcgP7IjfhAeGfIFihQoHVaU908VSkxq+8DI=",707392386534495361,2688181554184368095,-1156403721426951234,-2908459180508882431>()) {
                              case -502016795:
                                 return Integer.MAX_VALUE;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return Integer.MAX_VALUE;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jrjxklg91y8k","9XGFhINmmK76M8K04CkOt8nRmIFtZiBS/GqrIxpawZc=",4392533949871530903,-6526227199197578693,-1990929957788926295,7386162257589790102>()) {
                     case 1910808646:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }
}
