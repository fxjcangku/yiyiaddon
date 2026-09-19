package com.yiyiaddon.e.c.d;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public enum a {
   WHEAT(
      (String)com.yiyiaddon.m.b.a<"s13erhh91wdl1n","wnHsVou+EU8oBJEQ+MYALZU2N2xfbtHIx+UxxXnMw8k=",937859941869107268,7359847744913948396,4305535345472449094,-2977425139600359635>(),
      com.yiyiaddon.e.c.d.a.a.CROP,
      Blocks.WHEAT,
      Items.WHEAT_SEEDS,
      Items.WHEAT,
      Blocks.FARMLAND
   ),
   CARROT(
      (String)com.yiyiaddon.m.b.a<"s1hqd6bp1knw8k","6oJMGlXUk7m5o8ZwakxpXETxAZAqC1mWrZCy0AvrJ2DdZA==",-7114772571834072629,-3732200501840795183,-2140949241292800057,7619950810332962634>(),
      com.yiyiaddon.e.c.d.a.a.CROP,
      Blocks.CARROTS,
      Items.CARROT,
      Items.CARROT,
      Blocks.FARMLAND
   ),
   POTATO(
      (String)com.yiyiaddon.m.b.a<"s2pag0amueyi24","WjclBpJac0HM0lFSQ9n/Ojaast0w+IMCptjDHDUhmy0vUQ==",-1061897540209145958,1414205135186502623,207842533749949538,5571687365226606852>(),
      com.yiyiaddon.e.c.d.a.a.CROP,
      Blocks.POTATOES,
      Items.POTATO,
      Items.POTATO,
      Blocks.FARMLAND,
      Set.of(Items.POISONOUS_POTATO)
   ),
   BEETROOT(
      (String)com.yiyiaddon.m.b.a<"s3smendpwk2396","vRJeCSUTuBEqCq0Xi7LjCYl0ffrFo/yGKbv30HPvwoxXAA==",-3662075727050445276,3940542893296462548,3384040623689586234,-7703886916106521977>(),
      com.yiyiaddon.e.c.d.a.a.CROP,
      Blocks.BEETROOTS,
      Items.BEETROOT_SEEDS,
      Items.BEETROOT,
      Blocks.FARMLAND
   ),
   NETHER_WART(
      (String)com.yiyiaddon.m.b.a<"s2g3yifexhbmyl","gcgn6KyxaZxHvs745MX275NCQm3ZG+cD7nq044PQG4uyNg==",-8984496698886664344,-5643489429146467584,-7424256557143165834,-6768548677440419432>(),
      com.yiyiaddon.e.c.d.a.a.CROP,
      Blocks.NETHER_WART,
      Items.NETHER_WART,
      Items.NETHER_WART,
      Blocks.SOUL_SAND
   ),
   BAMBOO(
      (String)com.yiyiaddon.m.b.a<"s36nbcaoztwaec","klYJxL+oFU16vzJvl8tWJdOo5/CP7CXEDTm65fH6jjA=",-1863142965767673912,2796005577532137021,5090721873270259991,-1562265697907860277>(),
      com.yiyiaddon.e.c.d.a.a.PILLAR,
      Blocks.BAMBOO,
      null,
      Items.BAMBOO,
      null
   ),
   SUGAR_CANE(
      (String)com.yiyiaddon.m.b.a<"s19n3wuecwys3a","J/uxDf4RR1HqSF41/8esm86Z9M6eH8iLMX6ji+wJw0I=",-7039067978292169721,4617885234838284606,7718751766394500539,-8061759349366332242>(),
      com.yiyiaddon.e.c.d.a.a.PILLAR,
      Blocks.SUGAR_CANE,
      null,
      Items.SUGAR_CANE,
      null
   ),
   CACTUS(
      (String)com.yiyiaddon.m.b.a<"saahvfhcficx4","RCcAKbS3iKie4QwOonEjw3zDKd63+1y5kH0LPu15fSaAkg==",2949006701530576077,7392034497558997392,-5011191703602733176,-2136331467879048728>(),
      com.yiyiaddon.e.c.d.a.a.PILLAR,
      Blocks.CACTUS,
      null,
      Items.CACTUS,
      null
   ),
   CACTUS_FLOWER(
      (String)com.yiyiaddon.m.b.a<"s2g3oo1jm8i8ep","63HYbTatr84jA5aXj7xNzjQD1xzdcb+dENLi6WfAkywXBIiQ",-2062838872000651530,593147323345621928,258591424007294115,-1832638431458328309>(),
      com.yiyiaddon.e.c.d.a.a.FRUIT,
      Blocks.CACTUS_FLOWER,
      null,
      Items.CACTUS_FLOWER,
      null
   ),
   PUMPKIN(
      (String)com.yiyiaddon.m.b.a<"s177ssdcjlr3n","BHkiyY+EYLa7sTW8vysFcTuHGRWhF5vZRLNwHD/UAX4=",3728090373535443662,-475596640831029271,4121453882609212786,2724929237026778514>(),
      com.yiyiaddon.e.c.d.a.a.FRUIT,
      Blocks.PUMPKIN,
      null,
      Items.PUMPKIN,
      null
   ),
   MELON(
      (String)com.yiyiaddon.m.b.a<"s9ytji2ki84fz","jRaip1flRGo7vKe8v/w+R9uVJcfmQbyOjT5Wr5rxMGA=",-422649449396640479,-79936047677072529,6965412335191403830,1847012805759376583>(),
      com.yiyiaddon.e.c.d.a.a.FRUIT,
      Blocks.MELON,
      null,
      Items.MELON_SLICE,
      null
   );

   private final String bV;
   private final com.yiyiaddon.e.c.d.a.a a;
   private final Block a;
   private final Item a;
   private final Item b;
   private final Block b;
   private final Set<Item> h;

   a(String var3, com.yiyiaddon.e.c.d.a.a var4, Block var5, Item var6, Item var7, Block var8) {
      this(var3, var4, var5, var6, var7, var8, Set.of());
   }

   a(String var3, com.yiyiaddon.e.c.d.a.a var4, Block var5, Item var6, Item var7, Block var8, Set<Item> var9) {
      this.bV = var3;
      this.a = var4;
      this.a = var5;
      this.a = var6;
      this.b = var7;
      this.b = var8;
      this.h = var9;
   }

   public String m() {
      return this.bV;
   }

   public com.yiyiaddon.e.c.d.a.a a() {
      return this.a;
   }

   public Block a() {
      return this.a;
   }

   public Item a() {
      return this.a;
   }

   public Item b() {
      return this.b;
   }

   public Block b() {
      return this.b;
   }

   public Set<Item> h() {
      return this.h;
   }

   public boolean E() {
      if (this == CACTUS_FLOWER) {
         switch ((int)com.yiyiaddon.m.b.a<"s7ooapvpp8t5f","7qYYGuNcAg/ZDlQ0mK8WN3TlugUBw4BDXekSPYjbDEk=",-2696918744286531292,-2554619336968401853,322561416661895905,-8927957599459159581>()) {
            case -2121411193:
               switch ((int)com.yiyiaddon.m.b.a<"s7eji5rtkaw7u","HHWjsx714RdypbhkjzkkZlCJvdDBLxKFjTUpXrPzW1Y=",1762336145610495896,-6826099833979620768,8941507226168365075,531670131745217696>()) {
                  case -49274757:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1bu8dkptuvaxk","9PiqH09J/oay7zqXgmgt3YtZXmlbaDrlr4Q7hGf7Jto=",-4811636518978733824,5590651285113072622,-2014380214834871207,-7448252237370388763>()) {
            case -547168016:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   public String toString() {
      return this.bV;
   }

   public boolean F() {
      if (this.a == com.yiyiaddon.e.c.d.a.a.CROP) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cvnkx6bw3cgl","5GiHbrgu3b1r2tI0QVMNiGnrQ/ZXoi4hg7fSIw/9ocI=",4273053553448117963,-1508264236259818779,-8330750706466004786,-9013004127698622193>()) {
            case 1031664075:
               switch ((int)com.yiyiaddon.m.b.a<"sll31h9tv63h6","pgVUO2s/eRNtTsVkRpKe01pWF7O/boxrdy45GGOAicg=",-5306503978617632535,-1237640937593186458,7908086960186874892,5332368808486560251>()) {
                  case 359089189:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"sfs74m0xjz7x9","AUNo7D7eZaxQKXfe8bM9ITzb/QYkVK62AJxn9YH4mLs=",6672082481044474395,-1808287012156373639,8804390272807309934,-8500178270085907667>()) {
            case -2131926131:
               return false;
            default:
               throw null;
         }
      }
   }

   public Item c() {
      if (this.F()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tdcnsjyxzc3r","FMvZwUD19TK9GDnM82CRNCOo5zTTIBRMRKcRgD8H3/s=",4331176040049913217,7108014126373669239,6770249033527857082,-5274304963033241773>()) {
            case 1797171277:
               Item var10000 = this.a;
               switch ((int)com.yiyiaddon.m.b.a<"s1nrf6mm37ro5j","fBCa7gSjAnzd5cHUtvQgpscrT2rdf09VyaeS8DSySnc=",-510359007154060636,4590434116496936873,-4023556729270827362,-4410738993704300096>()) {
                  case -1781168699:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2zyrkpno4sdmm","yfiaBE5G5isliGXXxrpv1fJWLQiUiMCorCXvPl6Ay30=",-4276897972836388259,-842996896596349188,-8484679471600559120,9110486303794050230>()) {
            case -1319063294:
               return null;
            default:
               throw null;
         }
      }
   }

   public Set<Item> i() {
      HashSet var1 = new HashSet();
      var1.add(this.b);
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2rbaotfnmzvqv","/Ulf5yfHn4AlAl+A9NxNHYPmHUhU4DiDs3XhsRlsF+U=",-3967641568058569798,-90215096533243154,2921773758884052624,1272643135180687218>()) {
            case 1814241366:
               var1.add(this.a);
               switch ((int)com.yiyiaddon.m.b.a<"sdfrnr2dxsi64","LWF9ilEk9ZYu1k96T+q6BdxxJSwLH1G72dF9mDdEYWk=",3867693837371751294,4265382948458858284,-3554701348507636383,-422348928212207759>()) {
                  case 319199494:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.addAll(this.h);
      return Collections.unmodifiableSet(var1);
   }

   public boolean a(BlockState var1, BlockGetter var2, BlockPos var3) {
      if (!var1.is(this.a)) {
         switch ((int)com.yiyiaddon.m.b.a<"s10hv5xd8dyzab","sJVOv5Izis+C+TptDSMsSk+cvIJz2mnd0wr7ozjKZ3s=",4428865372189536088,7842093239536681582,1874293400608454604,4534532162660105512>()) {
            case 280100840:
               return false;
            default:
               throw null;
         }
      } else {
         switch (this.a) {
            case CROP:
               boolean var10000 = a(var1);
               switch ((int)com.yiyiaddon.m.b.a<"sd9idchm17dn0","m152b2dFWCivhqSVIGVnQKc1V+UGyN8Rni2WUbokEsE=",6152872875176759988,3761959617389906360,-1499578095274948890,2485990156240124232>()) {
                  case 56500463:
                     return var10000;
                  default:
                     throw null;
               }
            case PILLAR:
               BlockPos var4;
               boolean var7;
               label67: {
                  var4 = var3.below();
                  BlockState var5 = var2.getBlockState(var4);
                  if (!var5.is(this.a)) {
                     label55:
                     switch ((int)com.yiyiaddon.m.b.a<"s345685l1dfbl7","F64T71a6rcRfyNZtf5qb+dmRckRwhhjRae09fB+uiRs=",3553742439855638714,1930250969897803781,-5837476706425348418,545605508616782974>()) {
                        case 315662285:
                           if (!b(var5)) {
                              var7 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s3rc07ksf1gj6g","z7GmO3gLYdLOIYi9Ky9GlPEiWTz8mM5JCWGYMBsGJEk=",-5601707259684319442,-647830626105354753,8741405184594263661,-3905641509108941838>()) {
                                 case -1846306823:
                                    break label67;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3nqk3wgk3f83v","hJnbbL3TE7BkZgrrlEEbSSWVY8R33PJTcc2P+US4r0Q=",3218162564074652322,-3495755051437668957,2979837477565325444,-3366267640481925020>()) {
                              case -158728362:
                                 break label55;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var7 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s2utmnxxb0mtfo","QEmcwcxUjt7yub8gIiCnef2hZkS0xmlHETbZID7Oex4=",-4160589588104719200,-6262260283619502853,-2406233351853835788,-7929934174993177273>()) {
                     case 437368750:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var6 = var7;
               if (!var6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1lqybwqcgxogn","HCp4zLTxoNvEykzCPTmwrBg1oPpQRguu6hqEJ0SNT64=",2744643875696031644,5352259797541677328,5142618080730474186,8192130618971225183>()) {
                     case 1539163199:
                        switch ((int)com.yiyiaddon.m.b.a<"s4nn7nm2gu7fe","Bj1CzPScrtvlOXcnJYT8FF5KVQha/hgUDJy7dEDJ61I=",2004105076331261116,939330207086142829,3500604854390272344,9031027994558823867>()) {
                           case 321095021:
                              return false;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else if (!var2.getBlockState(var4.below()).is(this.a)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sap2fj9qp8cqc","9s1dY/hsbT/IVWqpchS8YiIOQHfSLvGbQ1cD/t4STu8=",-7145137953893313709,-7423270688208413292,3510940065429539611,-8038417432304848011>()) {
                     case -721209006:
                        switch ((int)com.yiyiaddon.m.b.a<"slm544v5d0flz","F9Oat6fk4qzpV8YkXWY5gSiwdIVMIiOSn/jRfBvBFWA=",-9116937956623955360,-1282651224935331401,2696465614594991397,3510516798696122767>()) {
                           case -1430608984:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s4g62fwk2pjtr","kWB2c3YhkuCoceKZnxYs7KZrR26vGfKUVsUWXyxD6d0=",8564702071964970579,-3631867918400547344,1765155893544091189,-7667191046587351522>()) {
                     case 1164158959:
                        return false;
                     default:
                        throw null;
                  }
               }
            case FRUIT:
               switch ((int)com.yiyiaddon.m.b.a<"s3ptu7vqulnpdb","RW1l9iLjY2TjTdS0oH+sN62aib/g/R3BX1mn6xzlWrc=",-8787227599061388195,-8755733248291834853,4372334102271367794,-6547734926429465333>()) {
                  case 2077498904:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }

   public boolean a(BlockGetter var1, BlockPos var2) {
      if (this.F()) {
         switch ((int)com.yiyiaddon.m.b.a<"s33ro1dh79oqw7","N40juz8dH00KgzTnxhO52zHo+uuy/1IbR1HqYcTMhIY=",-597569218425315823,6857804095010760082,-7184713791194989050,-8232955673340726673>()) {
            case -1728806405:
               if (this.b != null) {
                  if (!var1.getBlockState(var2).is(this.b)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1e3omspl99yi","y0Rup03YldVL1mbWhPU4QEgr1Ix6J7zf/qDYsIdQ/KQ=",1520421946609459077,-4089299374904832840,-8242615264716894792,5426229248793708763>()) {
                        case -2074478074:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  return var1.getBlockState(var2.above()).isAir();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bcf1wq4589tf","zCZBnVKJZ1Ml1P/L8rYvRuehDbyN7P9EuIvVKERxuro=",2495152937508408494,1166622872844043862,-4407784084049014540,4662889687367629581>()) {
                     case 1617385683:
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

   public static boolean a(BlockState var0) {
      IntegerProperty var1 = a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s256xob9e9he0t","gm8EtKmpWd7FIfPxvKMtsdr0Db2bYltxsa5e86kzBX0=",-6394794052278364485,7386543397624057914,-8761200405479840524,425127455353177914>()) {
            case 1149189484:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = Integer.MIN_VALUE;
         Iterator var3 = var1.getPossibleValues().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s9t06p1jt86x0","/rZ78sI6Jbi6d0PLwZKRsb2akpbXDqeNvxpeNS2FDrw=",379155391500523291,3142187636070682390,-1432090300475886512,-4226373995990162131>()) {
            case -1973262070:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1c30kazcgi95c","uuS1OZYKIyjwBhXUjLrGd0QAadDsiG6Ag5txRkNQ8S4=",-861318073246605414,-4933773608523509238,6808895108098799458,-9036602403284507143>()) {
                     case -1377825130:
                        int var4 = (Integer)var3.next();
                        if (var4 > var2) {
                           label44:
                           switch ((int)com.yiyiaddon.m.b.a<"syue4aqq8nraw","DE0fVbiMYaRABViMRYibhcxLlq0vtzY8CA+Q1tKMcNg=",-1488239341623306140,-9171411131052863450,-6391298394094566687,2941308647266356603>()) {
                              case 516495083:
                                 var2 = var4;
                                 switch ((int)com.yiyiaddon.m.b.a<"s106ow1glkr1v2","CdwOcpnDR+ySNduJl4DrZ7dKm9sJS6fe03fyihe7h8g=",-6400788975267986806,336130442986144230,4890654929731913034,3997027002285258871>()) {
                                    case -473538967:
                                       break label44;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sf2neil0tpqnm","hev49Bc1Z5cdf9VgUul2NEPLPNF4ZzAidq1NLy7G+y8=",-7013856117375281998,5151848817431706059,-5953382413318944635,2442406658792580181>()) {
                           case 1336321750:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var2 != Integer.MIN_VALUE) {
                  switch ((int)com.yiyiaddon.m.b.a<"sfbizpnnozn4u","buxP5kF9qlJroz/7GUXDqTqjpa2oZyRLxADFVLrh4bc=",8964797391768350922,-7243518730174241171,-5435560906851776968,-539217589808670209>()) {
                     case -1507318823:
                        if (var0.getValue(var1) >= var2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ha941r35zpdj","YsnFFNpz73QOjM6hTwwjDvgYstbn2iMFsNexvUx+G3I=",1862349894107090051,5114100076957347700,9103898213210444779,-837058587806494272>()) {
                              case 710814499:
                                 switch ((int)com.yiyiaddon.m.b.a<"sho7hn3t4yqyx","hTgIulsgnPTrOuHXFBjf+y389akSlwZ8c/1Y168RnPY=",1868439045180504773,-737166186753874770,6573631902021802803,-3165794984840942145>()) {
                                    case 96637942:
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

               switch ((int)com.yiyiaddon.m.b.a<"s31ls3inqg7i7b","XEhQ/Xsex7aJQ1feG6uaLmAqrcVNp9rPZ1nXm6EQBFs=",-8199994047276634486,5282087434313966535,-2158369990065387431,-7205497066629768679>()) {
                  case -409458642:
                     return false;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static IntegerProperty a(BlockState var0) {
      Collection var1 = var0.getProperties();
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1flepbahqst46","XTbPTdPE8v/At0g7hGdiPvcbzmDVH0MvKWeiDEKC1CU=",-9053104338001768368,6088413755584639346,6454850826078107732,-624429907843536679>()) {
         case 1246302819:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s39fn9fnrrk1ne","oJcqRFcOVFUip9YX0PzV1a5oa8qXpaTUF/+e5H7klX4=",662494420311232829,6168999336942830554,6971362766994807556,-3563191543657802551>()) {
                  case -766502111:
                     Property var3 = (Property)var2.next();
                     if (var3 instanceof IntegerProperty) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ce9ftna9fkh9","BtNIGHubFQRc0XfX0lJDGHx8RbC1rBrTkiH6bxIOPEA=",3135588064741081335,5646307595766712579,7000558442450898843,675448785491833131>()) {
                           case -961744498:
                              IntegerProperty var4 = (IntegerProperty)var3;
                              if ((String)com.yiyiaddon.m.b.a<"sp5qdzk4rr724","+8LROiA1/4jzZInS0gfSyqfcuda8vAAvYR42dehDCIGYYw==",986702189649990757,-6860872098928061823,5927188737183333730,6731875308441898352>()
                                 .equals(var3.getName())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sn54q5nlbni9c","G1MOAxHh5vXcczpc7GFeGgxRLzpBXs7FGl/aMaTBDhU=",4327151602767734589,6183857146410480063,-5703142160404689235,1687933081380053718>()) {
                                    case 188548282:
                                       return var4;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s245od2njpl7ad","XvKNQpcy+N/MVnjkM2Xe10CT78+pMl9kEt/3nF0kUxM=",-2016877425848816438,-2810495585818963600,6567193124891734465,7630326305095056798>()) {
                        case 10565276:
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

   public static com.yiyiaddon.e.c.d.a a(Block var0) {
      com.yiyiaddon.e.c.d.a[] var1 = values();
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"svp37zj9vl6id","Ilph2DEF11/e9Os59za3ld70qMAh8let82rPrcj5K4o=",7693721308840803062,-1175623290644326619,-8143773328028131704,-4105659174345913033>()) {
         case 339476368:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s36xw2nh7un9w5","Ilc2ueAAj47jAOVUaLpeYDWJs9ZJLQtXS2fgMSVYM3k=",-3173029751426402764,8320081604576278588,4164652110056439712,-4222433990063516676>()) {
                  case -1518622833:
                     com.yiyiaddon.e.c.d.a var4 = var1[var3];
                     if (var4.a == var0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s169gou9gde4lv","GA52+BiQNlmENif1gV7BoFWCgBsaiFrQhjHkZxRGQCk=",420503596413441665,-284187931160513665,6006680726341371304,-9111130199921569093>()) {
                           case -438125943:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2i74hec9r79vn","7fjm51afWC4Gdk4uP9mExUuTae0nGFUs849SexGaikY=",5280083781729472682,5681745687213152301,6872070745785941052,871528333464118782>()) {
                        case 2003605975:
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

   public static boolean b(BlockState var0) {
      return var0.is(Blocks.BAMBOO_SAPLING);
   }

   public enum a {
      CROP,
      PILLAR,
      FRUIT;
   }
}
