package com.yiyiaddon.e.g.d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public final class c {
   public static final int eg = 39;
   public static final int eh = 6;

   private c() {
   }

   public static b a(o var0, List<ItemStack> var1, e var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sl4n72rdr7t5h","C8pFVgnRiEC9mpOAjZN2TSQXBq4Cm8gEUKRypuOzekU=",-6845711885405786625,6070267155060080740,7414079609125991741,-7658992429497230153>()) {
            case -628768978:
               if (var1.size() >= 2) {
                  e var10000;
                  if (var2 == null) {
                     label59:
                     switch ((int)com.yiyiaddon.m.b.a<"s1pgvbq71fitcc","m+KVrQQTOirU35Ub3EEfnw+eVMJTbYbAlNbRv+HUc6g=",1283648838456276545,-2408348499499988674,3965090286366723092,443854370456111539>()) {
                        case 559611316:
                           var10000 = e.SIMPLE;
                           switch ((int)com.yiyiaddon.m.b.a<"s21z3wqrw5xlhh","6z94OWPFm7f9FYPPJFA4LrN7MlNXHeDgQlkXOIdYGPY=",-2385075012474438727,-6813350960097853619,-916471706395204301,-8147517951459161576>()) {
                              case 146641216:
                                 break label59;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var2;
                     switch ((int)com.yiyiaddon.m.b.a<"s5wbnrlqncws4","kBKW4QYLJTal14JApQL84xCnRl5OGIRgH55yCuDrMwQ=",2373364762466877173,164721312056025775,-7726120278521898811,-7545442714419880706>()) {
                        case -2100042061:
                           break;
                        default:
                           throw null;
                     }
                  }

                  e var3 = var10000;
                  ItemStack var4 = a(var0, var1, var3);
                  HashMap var5 = new HashMap<>(g.a(var4));
                  ArrayList var6 = new ArrayList();
                  int var7 = 1;
                  Iterator var8 = a(var0, var4, var5, var1, var3).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1pxsid98yeq1i","y9arGWMNeqasvBw0j0xMG41LPXd3K9cFvunth877W70=",8074580229433689501,2857472424961718469,6021130090916325850,4987353590552155388>()) {
                     case 1154876883:
                        while (var8.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s4hz4pm60hvs5","mhMwpNZtIScjWeeOuqgQeIvUvDxGfital9mx+ViZB04=",-3548715679436284548,-5714070121812899583,-2553754317898681110,4146058323832542353>()) {
                              case -26437032:
                                 ItemStack var9 = (ItemStack)var8.next();
                                 Map var10 = g.a(var9);
                                 if (!a(var5, var10, var0)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s4jtrkem91msd","6ndH6facE3Hm0mibcyUs31K5N2Qo1SO6GxJzuUea67Q=",7126989702390885485,7024703085441289210,-8565482777487969405,-5840601998584319758>()) {
                                       case -827550682:
                                          switch ((int)com.yiyiaddon.m.b.a<"s49xrtx74cgxq","knFhFZB5F4RPprQN4hOvENuMQWVOkY5js5HtHnsP98g=",-4345794427075828264,4843015392297987967,-8977048577686399316,4604805999831562611>()) {
                                             case -1202950173:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else if (a(var5, var10)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3ohjc7tq5844u","KOpa1VlUggkEi2hIgwj9BQ/vkNKp/qzKH9g3Zij6EtQ=",-7526633369207713352,-2621883524427854916,-7521308690972524788,-5316980137963781892>()) {
                                       case 1617136430:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3dt128mkgaezr","qsU/iGCHXfZHqF4/e2PHhB/HkxiBHqJzdsVXCxHRCkY=",-2786110484976321917,-4749169998800393204,1224639665718344318,4760146408827933728>()) {
                                             case -1831119051:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var11 = a(var9, var0);
                                    var6.add(new d(var7++, var4.copy(), var9.copy(), var11, a(var0, var11)));
                                    a(var5, var10);
                                    if (a(var5, var0)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3fjk2pmrzvg4l","9uNAxKmchMMCz/XGOHGpnqLoKDa/CspbHZADRqFMNm8=",9005305568010487203,-2402909387718004964,3084811895825517972,7962664409384148157>()) {
                                          case 845675868:
                                             switch ((int)com.yiyiaddon.m.b.a<"s24s3k366gh8td","ZRVU6kN9PmB3rJI7Nq23QMip0FGATADhIK0Nl+e/w7c=",8449116076691987580,7484383172173559232,3656100241843349609,-1893696894371593413>()) {
                                                case 2064419363:
                                                   return new b(var6, 6);
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s31s84ym5ol24b","1E4f1DLMdMbvCfQN52U679VMg7sf0Vew08blAHjpZtw=",-724184850527325415,-9131371633139837337,-2370996911134727903,7433541884817311368>()) {
                                       case -1644026236:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return new b(var6, 6);
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"smsnug3l4ncgz","898Dw80zj40zRW1r8B+DCngSY0k/Nr5+qF44rfIG8+s=",-6518556360099691810,541234892299912299,4016663580872773511,-994288459157986594>()) {
                     case 938179970:
                        return new b(List.of(), 6);
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return new b(List.of(), 6);
      }
   }

   public static ItemStack a(o var0, List<ItemStack> var1, e var2) {
      ItemStack var3 = (ItemStack)var1.get(0);
      int var4 = c(var3);
      int var5 = d(var3);
      int var6 = a(var3, var0);
      Iterator var7 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2044nozweiyxg","u+koygmi7Gb4EmW5rcDQC/AAXAaj4XJC/VejUEfqz/I=",-8736098492190798374,4934984658194716020,2965571590654927064,-3855367523487269413>()) {
         case 1878150198:
            while (var7.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2dcu0rf16rjev","jaPr4c45ni0M4FGqVJuGmxJe1FnbiYo6pxv+jCmRY0o=",-2619476094135200375,-4322914742778244271,-8898685971887122908,7921197846944326891>()) {
                  case 2039308524:
                     ItemStack var8 = (ItemStack)var7.next();
                     int var9 = c(var8);
                     int var10 = d(var8);
                     int var11 = a(var8, var0);
                     if (a(var11, var9, var10, var6, var4, var5, var2)) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s1diht8swr3v0k","OCewW/UOv+0I7buCwEn6aQSndhbw2COuF5sgYvi/sn0=",-4655816921568520160,6371223988160347500,6826666509799170114,-3674873919931508416>()) {
                           case -617815518:
                              var3 = var8;
                              var4 = var9;
                              var5 = var10;
                              var6 = var11;
                              switch ((int)com.yiyiaddon.m.b.a<"savlp5f1eav8a","LApw8fgBjpCsELe5tD8ZvvEWGymdY8bhHGfSW2CEyBo=",-1446708831153268137,6920371540604378052,-3847489383778550955,5447169697687839084>()) {
                                 case 66251438:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s24sd440jj5l6y","l8zrOGpv8xteX4XpTJ11v/i2EbGnpEamXr2kAW9c7Q4=",5274114163489641111,-5430923776507548668,-2454712934709757233,6082922230069025714>()) {
                        case 956381980:
                           continue;
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

   private static boolean a(int var0, int var1, int var2, int var3, int var4, int var5, e var6) {
      e var10000;
      if (var6 == null) {
         label76:
         switch ((int)com.yiyiaddon.m.b.a<"se6qy6g0ko3qt","MtDN7fY8kjJLrDSS2ixRL4lGtlc198xS/feSPucCjng=",5241892103690034755,-7922761859251266651,-7733957277273090138,313276209999547570>()) {
            case -54635834:
               var10000 = e.SIMPLE;
               switch ((int)com.yiyiaddon.m.b.a<"snpiq3ziku91","w/oR7r7LVipDGxQ5E9OcllCMOqV6wPgFKpC5Gu6gXjc=",3046158432740849504,-5072925503769029768,236196099827717519,7661441962220935877>()) {
                  case 285858495:
                     break label76;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var6;
         switch ((int)com.yiyiaddon.m.b.a<"s3jgc128mgnp8d","5Z2+u0DHfRkGstNdtFWidmMCe8QY4cIbjkefA7bx0gM=",2148957233319373792,-5580150592562648916,-1040767667012120970,4410743222012458261>()) {
            case 1616686512:
               break;
            default:
               throw null;
         }
      }

      e var7 = var10000;
      switch (var7) {
         case SIMPLE:
            if (var0 > var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s1gmdoht6pglgf","FCXaTx7w0JlMa3vxWYWqYuO6piuu3E2vmKTheqWK0B8=",-8452030731182821868,2026809994472181365,-1403300080095408946,716095100052571892>()) {
                  case -1111854179:
                     switch ((int)com.yiyiaddon.m.b.a<"s1w6mwotpqrgr3","99CJQJ53CiZtmCp0iICMlqRT9Xy7wlCxazGxtUm9RmY=",-7406843890757457557,6582483089043724647,3583502414575930122,7961754810590156097>()) {
                        case 1967627700:
                           return true;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s2nzwgomo08kqj","Nx8X4edXsN10XqqovtkXkm7Cb5K9dRvgNOeVahkD/yA=",1360910321788743536,-2043567942238161089,467014059429979257,-2337072108361863690>()) {
                  case -485575863:
                     return false;
                  default:
                     throw null;
               }
            }
         case SAVE_XP:
            if (var0 <= var3) {
               label58:
               switch ((int)com.yiyiaddon.m.b.a<"s2zek4o64hranl","lAPjJVm2Yt/g9lDjkUKVGbAwMvdODWbuEYpIuTqQRCg=",-4117754710322395873,6344265689859057322,-3886647744350696251,-5218860567767620359>()) {
                  case -605651639:
                     if (var0 == var3) {
                        switch ((int)com.yiyiaddon.m.b.a<"s18gk82zd3ixnj","gC9JNUZYYz1KwNRT+mg7kE3VxY+oi6FnEMDK6aVSpHU=",-1995471452675659916,-3320507121372787180,8435414668795237093,-6799001931349530004>()) {
                           case -81221211:
                              if (var1 < var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2fh5i7rf3i3hr","GPTQ6c70Yo6jdA7h2sQ5jy5FlGA+K+pDJ0203fVT8c4=",-756369423844915230,-971814863694916726,2202282887325125632,1045223472316953373>()) {
                                    case 1373073488:
                                       break label58;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1eq5zgphdvqtn","earuXa6PdC5fPRLEbDcL1TY9rCfP5TUof9MBbRMZ0Xs=",-6271673011140265508,3894567819408117823,-4656638828563313157,5381735634998796766>()) {
                        case 483095429:
                           return false;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s2fhsdrr8xrtbo","AyNnBosOr4yNtwB6yrQKoyHJNPRoqQ+JrlYUDI5Egbc=",3216905660889812433,-548398892155555680,3565459120304739046,3213661917227966323>()) {
               case -1093218601:
                  return true;
               default:
                  throw null;
            }
         case FAST:
            label89: {
               if (var0 <= var3) {
                  label69:
                  switch ((int)com.yiyiaddon.m.b.a<"s2acwwwq4uo8jl","MxjhDAgisPsncPzMBXu0xhE+G2NdhJf73ruFd3ad/Vc=",-6523700211571949695,-2705448640503660659,-7682275900804711208,-7292895554657608601>()) {
                     case -1975071371:
                        if (var0 != var3) {
                           break label89;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1a0xjf664bt0v","YTGMZkIpGNyC08EwNfxQ2GrIqUax6Qjk7vpzSZF3hpQ=",-1348103323240129706,9155364991712898667,3554863849786223997,1288897875637630660>()) {
                           case 1920119454:
                              if (var2 >= var5) {
                                 break label89;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sie4rom7ckb1w","bLaGWyHnM/X4ipdTkH0nv9zbT+22t5txIyosWKI3rA4=",-1624356883567249274,1933770894529470678,-5055166590680009673,2997185260951858188>()) {
                                 case 97851302:
                                    break label69;
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

               switch ((int)com.yiyiaddon.m.b.a<"s2dl8038xrvpn5","aprOCM853E1IMUq+fpcqTX0tPK/Kn9rlrgGpjUyJkAQ=",-4100777674215640757,3777269369737139571,8079790808155669441,-1206184912704136619>()) {
                  case -2019042016:
                     return true;
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s3e48b3lw2yg3h","Nh3tfL4gq1dj4G8Z8msfklrnvBQsv1CkMrFy/7F0ZNE=",-8005469505707277383,-257944449409589081,-509122006868776971,-1140097490965283466>()) {
               case 1900020137:
                  return false;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   public static int a(ItemStack var0, o var1) {
      Map var2 = g.a(var0);
      int var3 = 0;
      Iterator var4 = var1.Q().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1ixc6w944xd9h","xJHBqjTtCXbc4VC6JLzYRBHCelWiZRfmNUJyGczVwBw=",-1279386269466560181,-7143820636727985350,-1443561918002372149,-6749517707772306404>()) {
         case 1815013167:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1u4311blcfe45","+dDDDZD5P3SshjuPJweYC8CkJbpivN83WvlhJcroX0w=",8969444041371960297,-2155075430855444996,-6170161103165237808,-1137898590830545597>()) {
                  case 1826807747:
                     o.a var5 = (o.a)var4.next();
                     Integer var6 = (Integer)var2.get(var5.s());
                     if (var6 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3mslgf4zurole","Ns9+j9KkRSkRURJwNd1nwsvxsqznul1TUcxpUms6e5Y=",-3740526192866153578,7045074775086913119,-5840313858966260985,-861271359013541820>()) {
                           case 1834170011:
                              switch ((int)com.yiyiaddon.m.b.a<"se0qsfgid4ma7","laaXGw6ILC2/vOJy9EaUF85FaUASGQR1qq81OGOBOpU=",-9077554539974262039,-3627692492203593283,-5444657732641793669,6683683288703768167>()) {
                                 case -300075231:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var3 += 1 + Math.min(var6, var5.ai());
                        switch ((int)com.yiyiaddon.m.b.a<"s3aowxupmu1jn","nqsM2ZAaf7asYchM1Jz+ErvASdpFF+34I+pAAUnHClI=",4886315186505395158,-9037358386727854123,-7897386759324594812,1168313763621376094>()) {
                           case 1890417943:
                              continue;
                           default:
                              throw null;
                        }
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

   public static int c(ItemStack var0) {
      return var0.getOrDefault(DataComponents.REPAIR_COST, 0);
   }

   public static int d(ItemStack var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqb8jzytxatez","n1NNjjoHJGQd2ljkrfSF1+RqwEW6mCKqSLSQmBUvqgA=",6100234843767110149,940680709459495713,-7925662962054968859,8275654831768833152>()) {
            case 593441521:
               return 0;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         Iterator var3 = g.a(var0).entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s22evnvaqe90ry","JYykxXDRZDxB5bhGQd4S5N/E0vF7ZIxg/cdYrkJ8dWo=",-664946009528205736,8070050053151933641,1336482359868186849,-5324447934705311975>()) {
            case -2109854300:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zh8qedmzd9ye","QziXd40m8H3DkrdgA/Rmx61kh/QWF9RU9VgGwVDA+ag=",557368066603575688,8902331190352283600,-6948033997082663136,-9104897619437582962>()) {
                     case -1404844101:
                        Entry var4 = (Entry)var3.next();
                        Holder var5 = a(var1, (String)var4.getKey());
                        if (var5 != null) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s1radokz3wmr07","MNxeno+wqqFiV7C7UMOM7q/z4BARITmtzK4Om2cFUJ4=",3920470218407271199,-8470780105737654099,-8312999137180955309,8717713427183521548>()) {
                              case -227963371:
                                 var2 += ((Enchantment)var5.value()).getAnvilCost() * Math.max(0, (Integer)var4.getValue());
                                 switch ((int)com.yiyiaddon.m.b.a<"s3iu4dsb8a5ppu","pNri0t5KxFAQce5c+hU3OEVid2CyEKaRNuDrYRACA3k=",-8680155129544860465,1714644203916366146,246542168288341676,6430544799415193022>()) {
                                    case 845121962:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1g2gx16gvsua1","Zx5+smRwS9MHj+8BX6Djru56KICsqgR6e/vsG19JaVw=",5776800971288079748,-3501386807291299252,-743624862888605788,2553627303048688515>()) {
                           case 1877560279:
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

   public static String a(ItemStack var0, o var1) {
      Map var2 = g.a(var0);
      Iterator var3 = var1.Q().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3nt9qkjwppz72","hSGGfDWOpScZJ/xrQSOr+2QHvUiM1FzBMZjgQpWqcgs=",7362263197879127369,3343314082828049688,2553561904840163032,-4002646996078962135>()) {
         case -1742887140:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sghxduyjw3gpt","zZTeXZrzPO/U4h2RHd7SM74oH1EIPcI2BpYk8+zGxsI=",515683037999693056,-320723600145204007,34801671166527774,8737856564994203869>()) {
                  case -365986399:
                     o.a var4 = (o.a)var3.next();
                     if (var2.containsKey(var4.s())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s333ay84uvc07r","mxfwoRhSN+SZoBUwD0w9soRZp3HXUTQYwQY1BpHkiuY=",-1673750669472654087,6200642094780111785,-6933978101348986469,710481938700312131>()) {
                           case -1063855438:
                              return var4.s();
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3r832nug34l5x","d7uccdnrpusf5etwhnMz/fRFekJjp39mm8pQjOvNwTA=",-8314591520553683034,-7678632419085099365,-2013981238217649565,-1944276445759709314>()) {
                        case -2058472110:
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

   public static boolean a(ItemStack var0, o var1) {
      if (a(var0, var1) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26hxxzjydtwq1","pfuK+MiwG+14GRCX7+oNmk/FbpPYITIlywGMHScDnB8=",2153507115918552951,6612894840000232019,-4374948388745298595,389523473611500282>()) {
            case 318464668:
               switch ((int)com.yiyiaddon.m.b.a<"s165oe0axgyjnv","bGbae4tIP5hZpGH8FdTjAh9dcxv5yEUpu9Irb1X6+5w=",-7402032477696543645,-365108035387144677,4434554047808798588,1325649236044160610>()) {
                  case -2109010484:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s17gmyzb56q6ho","08FDNol1wnSkXbwrX02Kk0yZlDtVksP9JwhEO28L/hA=",7539755117835914197,-5684537881976420577,-478720558376845834,2634419800850594688>()) {
            case -2066860386:
               return false;
            default:
               throw null;
         }
      }
   }

   private static boolean a(Map<String, Integer> var0, Map<String, Integer> var1, o var2) {
      Iterator var3 = var2.Q().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sw6llgujpuu1b","9bLVMSfkv8V8isd0QWg9W6HISTOhZSkAameYVqIj0NQ=",7446781999621271902,4746580176200985798,-722793837971820625,-7177439048187876695>()) {
         case 916399691:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s17rv2igpc7hhp","AKKHSIElMgnW9cj6+kApWNpkO17kYZbl+BeiDMv1eY4=",-2897030722612572638,-8471243572687652321,-1092543843913646974,7197660856234325460>()) {
                  case 2130798067:
                     o.a var4 = (o.a)var3.next();
                     Integer var5 = (Integer)var1.get(var4.s());
                     if (var5 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1r5a2l79m66qm","c+YP1hbCva6wYkgrB99i1KgVNfLGTR87m+Qs6cjTSlM=",7113415966407979969,4581710797071622568,4599306002440513261,-3911877743818463334>()) {
                           case -1408582131:
                              switch ((int)com.yiyiaddon.m.b.a<"s3huh2m61ei1qp","eh+NK5b4DrksbQJMYyTVw5AgtcxF7NWiFXTBEwMrd6Y=",-1069520413336351827,-7375699122371026752,4235350978000175236,-5592005451453524168>()) {
                                 case 1963075254:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        Integer var6 = (Integer)var0.get(var4.s());
                        if (var6 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s16engubt7qi46","8oFy6smr9VB6lUm0Z/3N7xqk9MV0ZQ0MN3sFLPHiiYU=",-5281389412206324875,-6759878782284146630,7082289798213558541,2660151930613766858>()) {
                              case 452965358:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        if (var6 >= var4.ai()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2m5qa2ar8xaak","csawsAdkUtjiu/2kte5QO5u5b7NWX3Dn2y6W4WTPBdQ=",-9177310045415451224,2231512546494624000,-5466312078850818334,-648938795209070974>()) {
                              case 2146596230:
                                 switch ((int)com.yiyiaddon.m.b.a<"s19e3zigrs4z3r","iWQy7+C1vZaBMee3KNmZ939ldmF3R4Oq2h5/WNzcidM=",-1090152175595047863,-6375073165001284048,9093731125362737406,5530426798611381343>()) {
                                    case 279383582:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var5 >= var6) {
                              switch ((int)com.yiyiaddon.m.b.a<"s83rs49gp70zv","rv24h7y49NZKMutLtgubAS+zV5lsBD+GF+SqlUCetSQ=",3085462975938177711,8296025359081130418,-4105781230737154403,-5561599369765307428>()) {
                                 case 481866445:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s35fhh60pas6bz","3ZC6JuKLc3I4TZGjn6juU8gn9wpQIiFalGBQq05uXAg=",-1099003780382777414,7764549393810932487,8232381811240112042,-1570045036355450028>()) {
                              case -1938286441:
                                 continue;
                              default:
                                 throw null;
                           }
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

   private static boolean a(Map<String, Integer> var0, Map<String, Integer> var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tjx3kk6we6cy","VxkKiVnDGjpMit3t9WPDouaUoZNf3dqA1a9aZQZwnjs=",-1722860665088868705,-4905267087178016689,-6895543700408225538,-1349159236728189932>()) {
            case -2068497198:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var0.keySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1ddyj014i7goa","eLonjiGlmXSTtoIZn8DfFIiTvY6der0cfCGDOVlqOnE=",-8960256655128592052,1390600950383492901,-245945987216360898,6153163173740225863>()) {
            case -896555254:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zmgmg3dv9kh7","Pfyh5+xffhn3+3G8tnVoLGu7S8Pl2V0qPsDGDIojsp8=",6790741333577843713,-1823967232534847409,4191211156069513012,-1668240256697268974>()) {
                     case 1142626505:
                        String var4 = (String)var3.next();
                        Iterator var5 = var1.keySet().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s3dsrkhez0hmm6","R2B5fi4pBM0Llv8i4ZtZj18L7qFg0Fvsl7HXG44gaw8=",8068345847970342690,7655570708485807095,192654051471405593,-2218474559263464352>()) {
                           case -1274488889:
                              while (var5.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1645tvpqe6p0n","zrpDto2iW+P+2SFc/Q+WPKEc2wd98MI0fAKNl7UTCWA=",-6027612088844840779,-5673354749039226043,1692712845337444524,792177942348154832>()) {
                                    case 1928002596:
                                       String var6 = (String)var5.next();
                                       if (var4.equals(var6)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3p9rtinriqri3","d9PLhDmLlJwy/vjNKS5rcOsynigRIepCiKNQGdfoe4g=",257213349743925233,-9199482172888607778,-760930690324479084,-7870587865470354897>()) {
                                             case 1170238134:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1jlgv9c49om7f","+pSgqAfS2ecnQHjDCQMPDA4pdBsdc+6ALuySJ6c+MK0=",8187397458838459762,2331412761284370797,1561062309674627909,-5093551344637495657>()) {
                                                   case 115064643:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          Holder var7 = a(var2, var4);
                                          Holder var8 = a(var2, var6);
                                          if (var7 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s23chp6c5hf9to","a+w4lHGbdrI2PKXs5d/oFld5onKJfJTDqLUBl6IYsJc=",-4540121631687455088,6677650746639417785,-6816683079934585519,-7551359253948518579>()) {
                                                case 52278637:
                                                   if (var8 != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"su0fje0zbsd9p","dElmczSSWrR6stXJqOxN9rNW8IdXU3Ioailo5JtTE58=",3310264076616076565,5647429972094878410,3972254787733611369,-8110902255899737830>()) {
                                                         case 212464931:
                                                            if (!Enchantment.areCompatible(var7, var8)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"svy8k8tbdyyto","hBcfDdNCTmG9m7cTnIMApt2HS45Bg568O2ouWp7zsZQ=",661205298517780342,1709395379751951395,-9153099035625590937,1483751263562620016>()) {
                                                                  case -929308642:
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

                                          switch ((int)com.yiyiaddon.m.b.a<"s2bda3djxlgxit","3bVAv7BrQj4mzTwd/Q+ZCArMt2qdMRXwkw+M63YEHUY=",4774066223308445211,-8182993782333532451,-7409649302272332992,7871543230832185130>()) {
                                             case 965760118:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2m2jp1xwa7q8v","+yrPluWqYHibiawclz/3oXS/ITelGrxutd3Yet7CY2Y=",-1337328994320791931,7202285330502094036,-9110015780661495120,-4118414913799425853>()) {
                                 case -872992679:
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

               return false;
            default:
               throw null;
         }
      }
   }

   private static Holder<Enchantment> a(Minecraft var0, String var1) {
      Identifier var2 = Identifier.tryParse(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2polps2qzc2l8","EDalJ3VPwiforac9HcpZE5lCbCiGIi7u9b7Wk0Xv7aU=",8634993051109463357,-3110104442449148527,-8073319721082376279,6813639800247671015>()) {
            case -738837576:
               return null;
            default:
               throw null;
         }
      } else {
         Optional var3 = var0.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(ResourceKey.create(Registries.ENCHANTMENT, var2));
         return (Holder<Enchantment>)var3.orElse(null);
      }
   }

   private static void a(Map<String, Integer> var0, Map<String, Integer> var1) {
      Iterator var2 = var1.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s29llzxk7dttbl","/mZhcxL5E12NtabccRhOcIvJxMLZo4ZiDFAUGurn8Cg=",3248666577304719922,4094661215731509305,7273387467867760077,2680290032371271970>()) {
         case 1818333119:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s5ocbh2wfrjw5","hBj5uiwfx7b9dN1z1DqZtU/J7SpWFrwiTYB2BTLcLNg=",-8517975663574024774,-7523554127126439906,-3191009446757705451,-4440992394343317028>()) {
                  case 668631206:
                     Entry var3 = (Entry)var2.next();
                     String var4 = (String)var3.getKey();
                     int var5 = (Integer)var3.getValue();
                     Integer var6 = (Integer)var0.get(var4);
                     if (var6 == null) {
                        label41:
                        switch ((int)com.yiyiaddon.m.b.a<"s1eyl0u6nzfcsk","IDslibdjO8llk+XnwjIWvrgFNy4zTo0sX7TrP2ZOcOs=",9118404550511429649,-4103360316119305318,-8221793888200502765,-2978174617343729129>()) {
                           case -614322783:
                              var0.put(var4, var5);
                              switch ((int)com.yiyiaddon.m.b.a<"s20i5mof3crxvf","Ydh/0pyAuQd947Tzvs+fH+NBrW5oclOZeywiNIkAXTY=",-373512592799783514,-4228149202482171290,2482465394861444443,-407272692301425589>()) {
                                 case -983121390:
                                    break label41;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var6.equals(var5)) {
                        label37:
                        switch ((int)com.yiyiaddon.m.b.a<"s3h20yqu2s1lgb","8X1hahxNZUEnp2Sv40ELmwHD1M527MCGECfQlL1zmQk=",-2840649301428700023,-6246726500959739296,-5953014312245687610,1904583276918073477>()) {
                           case -2061680854:
                              var0.put(var4, var6 + 1);
                              switch ((int)com.yiyiaddon.m.b.a<"s3aurx1pacolai","PpL7JaYVNHha+qI+Y7FTIeGYhKOi72TlPCg171Xr1cc=",4285667876124835063,-6567615817699698153,6841039192839629601,8731567540805677981>()) {
                                 case -2077180542:
                                    break label37;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var5 > var6) {
                        label33:
                        switch ((int)com.yiyiaddon.m.b.a<"s2ggaxnrbb6y2e","Rb5vJgy7jgaP67bBc+ri0hUxb3SAwRH/wDYDX42HJus=",7450169737800719185,792752968617942544,-7873870059257595747,-4426773567741237133>()) {
                           case 968176925:
                              var0.put(var4, var5);
                              switch ((int)com.yiyiaddon.m.b.a<"sbsh2q77m4wvc","VEGs85Ymdb0XvyWnXydMIBbDE/41ajjzOVS16ksX4OQ=",1949243622934083338,6788529764786073783,6366855016977801070,-8548749120262184946>()) {
                                 case 77744937:
                                    break label33;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sehcswtc20y5b","vULs7iEAVznibHxmMeSznJaNI5d1I5jIxtxS1NLyCcc=",-4895979624852353626,-502401557642449480,2483562201417656279,4425779882530659661>()) {
                        case 377767875:
                           continue;
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

   private static List<ItemStack> a(o var0, ItemStack var1, Map<String, Integer> var2, List<ItemStack> var3, e var4) {
      ArrayList var5 = new ArrayList();
      Iterator var6 = var3.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sjc3uimhvjxo2","gQqctLBkS0YWQWhrJ0LwrjQhTgRsYoU4qvOcL/JtKqQ=",-5283247167171057845,-2527890673606784541,-3876726529456660056,-6094179166313125496>()) {
         case 951911885:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s33l8skcayamuv","oyfrVMnw0asPDXezM9o/X+CRTyihKrafks6EowjEYlo=",7133184789853890144,-3742685785838038344,-4761507636230120376,-7682606802540008244>()) {
                  case 1300988178:
                     ItemStack var7 = (ItemStack)var6.next();
                     if (var7 != var1) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s2scc2d4w70txw","WaVnaY4QGLz5zhPqMu+Noaefx/kZHEiCOsZ3cWAubH8=",4828040105075678264,-6439834705986427981,-2606113769152759378,2400525310449105227>()) {
                           case -49158583:
                              var5.add(var7);
                              switch ((int)com.yiyiaddon.m.b.a<"s3ajr2zao8bqsp","FYGodJSgOxgHDM9qeHMDWOyW1ei8JY7xJT4MO0i/thA=",-2069026309295960838,4972691883849656144,5999122519360922902,-291495712642212045>()) {
                                 case 980003137:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sseef8tuvqg7d","F4Pm8eBeQMf8FLDf2QjIqpTXwuUE5KQ7oEA7lFtNjT8=",5593821667027908217,1005144492454832361,4194002486025395491,1761770165994291065>()) {
                        case 1267828974:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var5.sort(
               (var3x, var4x) -> {
                  if (var4 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1nnog4lvpfggn","LS3+YLm5QRpUABtOjwWq7NDUfq/5rIVJWD9dZJQWHQY=",-7210602795235308215,-770184233296668306,2972478319444832785,7431744471826473336>()) {
                        case -686270448:
                           e var10004 = e.SIMPLE;
                           switch ((int)com.yiyiaddon.m.b.a<"sftkx9wzf07zo","JrYMlnSw5q/l6OS7Zi0l1q0JemB+Q+XqLhK/dT86kmI=",-4063133469082150099,6984251958814935382,5252770943034357996,391422191118969385>()) {
                              case -1825094854:
                                 return a(var3x, var4x, var2, var0, var10004);
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1q713ma9w0ke4","M+l92NKHFhcF8bf+WbbAZLuQR09f5PfzUm7GrU359aY=",2190287632418325038,-4564313061899909717,-445654524584169011,-48560436813393161>()) {
                        case -1654650822:
                           return a(var3x, var4x, var2, var0, var4);
                        default:
                           throw null;
                     }
                  }
               }
            );
            return var5;
         default:
            throw null;
      }
   }

   private static int a(ItemStack var0, ItemStack var1, Map<String, Integer> var2, o var3, e var4) {
      int var5 = a(var2, g.a(var0), var3);
      int var6 = a(var2, g.a(var1), var3);
      int var7 = c(var0);
      int var8 = c(var1);
      int var9 = d(var0);
      int var10 = d(var1);
      switch (var4) {
         case SIMPLE:
            int var14 = Integer.compare(var6, var5);
            switch ((int)com.yiyiaddon.m.b.a<"s238bi57i81hkp","tXFvnn0I3kSaFx32WkA3rCu1Vg/9or6WgeMJKNVBEGI=",6612683639518571034,385893569800262930,3857027861328057879,5282532514880151903>()) {
               case -701907540:
                  return var14;
               default:
                  throw null;
            }
         case SAVE_XP:
            int var12 = Integer.compare(var6, var5);
            if (var12 != 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s3dygs7medusx7","+6hJRKSqx6eIOCvh4zwJEsTzCF3JoraowpM6talAm6Y=",74837382763584850,-3096741975409798879,-1462399805349960755,3094621113872392679>()) {
                  case -960357294:
                     switch ((int)com.yiyiaddon.m.b.a<"sjvkw2ppjjm2p","uWIbBblwPb4Dx+AXKEtbOs46fzopS0Lac7aUq9EhuWk=",2632400340523235731,-5034951028296870519,7686511975694803773,-410900084851351880>()) {
                        case 1836043046:
                           return var12;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               int var13 = Integer.compare(var7, var8);
               switch ((int)com.yiyiaddon.m.b.a<"s2v55u746mhdzh","JuLIithrlpXxn0ro7RLdE4ITBywlsr2arSKAq3rJ/Zw=",-8787697255453198495,9123768182516348849,8499992207186173959,2131326704090107786>()) {
                  case -159380193:
                     return var13;
                  default:
                     throw null;
               }
            }
         case FAST:
            int var11 = Integer.compare(var6, var5);
            if (var11 != 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s3dutnajqice17","fwxDbMeriXUsY/1x1NVRlEjn78JNMlZJAi9c/wgE0jE=",3574165257567382008,7413869077019094453,-1314737159625576496,1403144975157657028>()) {
                  case -543439950:
                     switch ((int)com.yiyiaddon.m.b.a<"s3u5b4bjt31zcr","CxDR8wqzRjbZx9qRoi/LlwVCQT6IebbTwkyqQPluwZs=",-2319251182900137982,7760475586957130653,-8441195622554348063,753939014744403832>()) {
                        case 1387803815:
                           return var11;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               int var10000 = Integer.compare(var9, var10);
               switch ((int)com.yiyiaddon.m.b.a<"s123tibf4ifot","GvptIErw4zYUhtmo8BaQX70WMT8Jl0rJkt4Om5f6KXE=",875692671820786091,8606962158695445277,-299652937061841204,-7885241076276731285>()) {
                  case 1609148375:
                     return var10000;
                  default:
                     throw null;
               }
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private static int a(Map<String, Integer> var0, Map<String, Integer> var1, o var2) {
      int var3 = 0;
      Iterator var4 = var2.Q().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3kxobemver2ui","fIetZD9tzB4KVFcs1AXO1z70aXuxBd3X23GOeFIRPfs=",-5386999482739255123,3248356706388776121,6244110627102879950,-1702493752327901779>()) {
         case -1029153170:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2vb3jgicesnu1","9iOfFx1ZmuV37lK9Zz87lAvjGBu+2QJx/plYgekWPWQ=",-3522703324126292420,-2023464655998039141,-5383179985904143820,7429757691241572211>()) {
                  case 126997381:
                     o.a var5 = (o.a)var4.next();
                     Integer var6 = (Integer)var1.get(var5.s());
                     if (var6 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sj903gosn57nq","bZq8Uaufe6esYB90u+bFX8CthHW5DCybxK1US7Jq5LU=",93267051819455791,2972382612957981089,1695233153611947929,1277480980490730546>()) {
                           case -983762504:
                              switch ((int)com.yiyiaddon.m.b.a<"s3w1syg3331dma","JRrUjhFZ1wwsR32EQYurihcr3hsWdE4ldI9Z4rW3eYo=",-8367411633261227517,1981507284315541031,-3973017091505280835,9175649942734847094>()) {
                                 case -1288990492:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        Integer var7 = (Integer)var0.get(var5.s());
                        if (var7 == null) {
                           label42:
                           switch ((int)com.yiyiaddon.m.b.a<"s114wrwnura4us","4PWg43M7Dhrbhf69Cw3ExaghwQM5iC+LZIj2bMXAv0k=",-696627848049086816,-5870932977363350614,3265988074496841366,762441382545198331>()) {
                              case 1318350286:
                                 var3 += 2;
                                 switch ((int)com.yiyiaddon.m.b.a<"s29jyuvk1q3a6d","6wz1Z9mhJgR5X7xJsLBSAli8z2fOexYGbSTOzC5zHeU=",-8279077929503420665,1276103759185178602,-4879961277615563361,5572253732327889197>()) {
                                    case -1576730827:
                                       break label42;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (var7 < var5.ai()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s13enxl5r9wve2","vN4S0iBdRTH1spg/3U2am0CFhjcz8P/XGv4hJS6mONs=",-6787417056371010860,-8482774918732131520,-8280115443019126629,6212044441020553488>()) {
                              case -141550477:
                                 if (var6 >= var7) {
                                    label38:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3pc7ko8viljv9","qladwqPVA8HfxcQX/67fs5u6l2jXljAhn4M1nhpdgq8=",-8319189358200453121,7763244328959479203,-1457233744369649463,-9078555795638610680>()) {
                                       case -1527009343:
                                          var3++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3ifepusq1igxc","QUx2ii1QQA+aKdh4UJl3rOkFh7CPkBqD0aC7fGz6iKo=",1925231045895286089,7315498895993246546,-3482678041549268924,1538494066947150845>()) {
                                             case -1664216105:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s14hcb75a33ian","bW/TmSyRVl7LiJORkttjwJqNLzlw1odnmkHQG5E32aI=",8388271300296224476,4217817994057720292,-5303707765410898434,-1528814121723830530>()) {
                           case -465554012:
                              continue;
                           default:
                              throw null;
                        }
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

   private static boolean a(Map<String, Integer> var0, o var1) {
      Iterator var2 = var1.Q().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"scksp8q1p7ugw","bOe5YUxIHqIYdDXlozgCaM7qKwkF4c6hNNpHaCSGK0I=",8853308519781686210,-3046057639756282077,-4680048087128877545,-1177558571611546972>()) {
         case 1595470353:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sqj6uat0e3x6x","AGjHTPneAoiZv7jdEf3X5HJKfH1ZUDzULfDgZnUJg0s=",6998753283179241171,4889166152871581173,-1768268623186008054,3666958758130183146>()) {
                  case 240890655:
                     o.a var3 = (o.a)var2.next();
                     Integer var4 = (Integer)var0.get(var3.s());
                     if (var4 == null) {
                        return false;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s5qvvpjt438g6","IPGvK2usQiGACQFl0mJDjbfoNRBZUgy0Kq08Dvk1VhQ=",5637964730052232327,-7213408410084046087,7393675679310574987,2356321753167610664>()) {
                        case 1541961889:
                           if (var4 < var3.ai()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3ig1oaurhpc50","q9E3Pt8EffKZ/4jLrHahgCYoDS9PLDHnimwgERpHskw=",-3062833559353535028,694850531572264672,8187542366948113113,3469855741219549524>()) {
                                 case -1013624143:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1d3ddfeclr0p4","/YpNFqt/MV1sUX8oIAbn7DU0b1dLw7hIRR/ASv40FaQ=",2311259635741732159,-5738414336838372883,-5207507953168582305,8484236049269559431>()) {
                              case -1214506606:
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

            return true;
         default:
            throw null;
      }
   }

   private static int a(o var0, String var1) {
      Iterator var2 = var0.Q().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s35ov4yowqqup","R9m0cSoWXFCUnAbgdtjV6EC1CQ5TFLnnPq6VyB7adaA=",4694319757341830808,7620283609026376768,7433261110335924440,2431752264052136564>()) {
         case 715738037:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1mzy0kh4g61h4","L7SUvoXmJo/GdYLoZmC69D1EK+7Xl/g9lbvBW2GbkuE=",3042161576750241531,-8655544124977124369,3005923543999125423,7395350056848473903>()) {
                  case -1410514639:
                     o.a var3 = (o.a)var2.next();
                     if (var3.s().equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s146yzcc8g371o","Xdti7QimcoaSoPCPyZ86ahsdUugsrQ6FfTVEBIPCIdw=",7904322794211287196,8007516539417274586,7187199681771321338,5152465453962115141>()) {
                           case 1193829568:
                              return var3.ai();
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3b6jszd6cyqbv","g5dY+nizQO5osSE0ASyKkPgiBA5vc+KkrDYiJllv3zA=",8235266844394007821,-4518995685976330847,-5435774996252223254,580722190055325041>()) {
                        case -1342526878:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return 0;
         default:
            throw null;
      }
   }

   public static int a(AnvilMenu var0) {
      return var0.getCost();
   }

   public static boolean f(int var0) {
      if (var0 > 39) {
         switch ((int)com.yiyiaddon.m.b.a<"s6lofaxzjumtf","BzZL1dr0AGFJolgiXGzBZPZBWyL7bwG0FbALGiCM5E4=",-8977083932640782055,1913862754311002075,4131672346563459979,1700911618570986196>()) {
            case 399421671:
               switch ((int)com.yiyiaddon.m.b.a<"s12ubpisp3ztfd","o1CTcmHRAsaYaVGjIfyyfl3RuSXp0Xyv91qdVkTYrnw=",5303880907813425436,-349165905156422778,2551582885852586408,-6645845816569146001>()) {
                  case -1631840109:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s11plc2jy8mbco","OIx8XguOTtQaNIY//RE+dXahdaWFZml47foq01Zkyv4=",-8890376898419351662,-40500574970935924,-2354883244511659688,-5895200515773811797>()) {
            case -1204423374:
               return false;
            default:
               throw null;
         }
      }
   }
}
