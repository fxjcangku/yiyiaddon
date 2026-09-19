package com.yiyiaddon.k.e;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public final class a {
   private static final Set<String> aK = Set.of(
      (String)com.yiyiaddon.m.b.a<"saz3w4p5kh950","sh+c4mCoW9OgEcFfSqOyLLjWj4rFUGTU0yf9pt2ZbCZ9yEE6+WmIVWdPriytEQ==",1463394258799146785,4926931623990696882,1176975951172225189,8301510755816976048>(),
      (String)com.yiyiaddon.m.b.a<"s340ra5upg2235","qbNRMMlqy24SNtCJjVLP65Gn8ZMWNgWYEHLsVRjKr8ltEn7FparcZw==",-1660875815314625888,2545756990936236880,-6417327463356938813,-4389851617530721662>(),
      (String)com.yiyiaddon.m.b.a<"spqhe6x2iwkxd","EojpaQoiIFLm03F9MmkBUzeG5D2pgdj+AzBkan9maXqNRrzRGRL0Lw==",-165802712998085561,5143215100820047031,-7477631869912405198,-5134621923233650540>(),
      (String)com.yiyiaddon.m.b.a<"s167der42pvgso","a7QoZG8gzesNWvPiOFKjgLrteToseP3u95TMzRbRDD+c9l7rdvoXzGxMza8pRg==",900206160245448563,7433929848036018541,2797302614975401117,7652750945219792830>(),
      (String)com.yiyiaddon.m.b.a<"s2k2bqme88w9dz","8/RJU8+UCLdBfHYn2AB2G5Z8h7PaO2+X0pzRPi93/OPilFt8e0n+Yu19IvY=",1472398668382532239,-3933285691727948504,-1981266882951844267,-4613117438199602350>()
   );
   private final Set<String> aL;
   private final Set<String> aM = G();

   public a() {
      LinkedHashSet var1 = new LinkedHashSet<>(aK);
      var1.addAll(this.aM);
      this.aL = Collections.unmodifiableSet(var1);
   }

   public boolean aM(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2831lzok0l2j1","xirFpdEuL50//5lFyeQ703vhMW/StLLJ1EayMp36xto=",-2540820394342303650,191746665187721137,-4725396983927692307,6657094351335327568>()) {
            case -1662263891:
               if (!var1.isBlank()) {
                  if (!this.aL.contains(var1.toLowerCase(Locale.ROOT))) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2e64fidv0z2gf","lfmNgja/tB/SJk0MsobhPaqeZJBmHtokW9ssAH3mRJs=",9183568972928440896,6879419213662070554,4083290574873803695,435683035998061066>()) {
                        case -236759841:
                           switch ((int)com.yiyiaddon.m.b.a<"s2iezpiucbd27l","1dAw5U4AzXwGmh0QmXQOqft9g/WIJl4hHr+Qd+8qIio=",-973072990353075803,-8473487627587340503,-36036581765878850,283138394376114105>()) {
                              case 1736698566:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pmcq6neihtd7","1b/p6vIAVa0lLTOErD+fh/GXUWWfpGVhzFABnc0uG8c=",5225138346404282676,3144111501055932848,8575117537638456728,-880792459135134329>()) {
                        case 1552399493:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"spdsd7blkdfmj","vQgfg8lyD8SsHGOmQTAP/k21dRCl+yOQ4epakFgQCJQ=",8413607367622177678,-6369064195785515300,2250834980080905125,1722086364125634754>()) {
                     case 918032065:
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

   public Set<String> E() {
      return this.aL;
   }

   public Set<String> F() {
      return this.aM;
   }

   public Set<String> a(Iterable<String> var1) {
      TreeSet var2 = new TreeSet();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7fs7hiaed2h","FIhRGOdNLbdPZ8Po7C+4v50JKtMX3fAjHWX3ILthBp8=",2113868314623159371,2101224482132691934,7639002985453513515,-1255354687489846495>()) {
            case 771629558:
               return var2;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1la29gr6ofk29","Il23DaeAwaBK3/KFi74WZwFkRL7gEEQmCXdwwHT7ux0=",-1276601516521145312,7352269816990675294,38289715421593761,-2173690946536666417>()) {
            case -1574190389:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s15fw21i7jz2i6","sctyxSr9ahWCvxLs3HZGSyQB9lX7t8RWOr4roMNJHiE=",-6266474196325519883,-9136990400563640172,2594983322534756582,5715709245528934661>()) {
                     case 286602384:
                        String var4 = (String)var3.next();
                        if (this.aM(var4)) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"stnih8jf9ps5u","YSBQXcwEUcCiN+wBeVwW2cjDDxWhjvOgaQYKUox4j7E=",1565291691377739179,829735109347422542,-8331344281699946047,4074459635262905512>()) {
                              case 1811607494:
                                 var2.add(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"sjgytn6z6yi9a","jiMt7C1ByskqxC7q2NWOTR8L0QU513HtPjYXj/5w+D0=",1647011735472351329,-3733953510162329181,-164753366477444285,3282639733726149439>()) {
                                    case 664597714:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sw0lv51mea6pc","jEjFt/HpfF/WtcnI9QrOn7hoEkEVrf8p3pUWxxOdGNs=",3263330381733635692,-2211718925820868565,114152585057186247,6676986336447915678>()) {
                           case -532664684:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var2;
            default:
               throw null;
         }
      }
   }

   public Set<String> b(Iterable<String> var1) {
      TreeSet var2 = new TreeSet();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sfcndxoxn7xli","hBv3IK8noqu/oEtY5OgHGso4xyCvTj74KWU4AbqLCPk=",-1999807601248564178,3464256285436060226,7705094703522472527,-3095797763493227005>()) {
            case -37850465:
               return var2;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2da2k40ydz01q","qU82HyBfGVYHSF6AZorIeTUiNWWuAYElg6ljTWReKcs=",3635366189098230493,-2729585525128352349,-1278158136930251282,-7097536132873497569>()) {
            case -1156451138:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sdlnnrnqdjj9","hmkuvOe8egpTXWYJ/nLW5Lrf/UZlk0MB/p1R/ulKlxw=",-6109384795914339082,6591089284832715427,8266925026494269999,-6167551258911768608>()) {
                     case -1692680444:
                        String var4 = (String)var3.next();
                        if (var4 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3m1lnu3yu83pp","WmvDcwtHaekr0TFdqs0J98QpIAj0zhMprSjI/zBHzSY=",-7284336437061444583,-4100050472003914555,8568307270093433177,6633684336622473530>()) {
                              case 1287288175:
                                 if (!var4.isBlank()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1v5v7r0xr0z80","zrF0gnGuz2InpQc3X9ukXYi76TUoh/HJvEWq5n+Ld8w=",5338861602391947458,5452394678366440091,5683154063316982481,-6687040762061192446>()) {
                                       case 1720297120:
                                          if (!this.aM(var4)) {
                                             label38:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3g72xeqbwqk21","cDv1bb8PXbjFwNno6kBujR7QkHVIl4Yyr+YIbl8u570=",6858390802964148270,4858728489802016916,8437663724328041,441275892117987819>()) {
                                                case -1747172577:
                                                   var2.add(var4);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s5qmq11fuea99","CkCz6jps/NuGjzlILdhqM+H23wogsZftpr8R6QgLVIM=",3270169474085905082,-1786632426369409019,-1046610772122353520,9105240521594529494>()) {
                                                      case 1248470497:
                                                         break label38;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s180en537y2mhf","r0uhDTrKN39R8M7I7zPXzlbH8C0RQGt6+e+VLc5OlPE=",-4435023676475345243,7479736603617422184,-565209625819500400,-3901561321096349893>()) {
                           case -2088104484:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var2;
            default:
               throw null;
         }
      }
   }

   public static com.yiyiaddon.g.d.c b(String var0) {
      return com.yiyiaddon.g.d.c.a(var0);
   }

   private static Set<String> G() {
      TreeSet var0 = new TreeSet();

      try {
         for (ModContainer var2 : FabricLoader.getInstance().getAllMods()) {
            String var3 = var2.getMetadata().getId();
            if (var3 != null && !var3.isBlank()) {
               String var4 = var3.toLowerCase(Locale.ROOT);
               var0.add(var4);
               var0.add(var4.replace('-', '_'));
            }
         }
      } catch (Throwable var5) {
      }

      return Collections.unmodifiableSet(var0);
   }
}
