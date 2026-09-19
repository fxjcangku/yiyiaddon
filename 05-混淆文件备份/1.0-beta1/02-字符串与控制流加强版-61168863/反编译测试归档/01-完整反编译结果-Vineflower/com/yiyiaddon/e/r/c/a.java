package com.yiyiaddon.e.r.c;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;

public final class a {
   public static final int rr = 4;
   public static final int rs = 64;
   public static final int rt = 2048;
   private static final int ru = 5;
   private final Map<Long, List<BlockPos>> aI = new HashMap<>();
   private Set<Block> ax = Set.of();
   private List<String> cv = List.of();
   private long[] a = new long[0];
   private int aL;
   private long az = Long.MIN_VALUE;
   private int rv = -1;
   private List<BlockPos> cw = List.of();
   private int rw;
   private int rx;
   private double bo;
   private double bp;

   public List<BlockPos> bt() {
      return this.cw;
   }

   public int di() {
      return this.rw;
   }

   public boolean eR() {
      if (this.aL < this.a.length) {
         switch ((int)com.yiyiaddon.m.b.a<"s3w1zuemkqsp57","EAH08PIVamwZZ6kY2EuAnN4aPU77sUbUabJ4I/HrxkE=",-1856469199300028599,-2444426671606100082,6285175898882370527,-779402993209592039>()) {
            case -1859716564:
               switch ((int)com.yiyiaddon.m.b.a<"s1fsaahwrwcx6m","udKzK7hQP05NZj/kgk+MzheZ6SEsDeAFQaE4XKTzY14=",-8593309192739854323,-8751865411549631898,9179162536790352928,963172112336155054>()) {
                  case -69073841:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2p4z4oelqdn54","YCdl+xg84HqMthB9m3L/zEPUpSoveCxd3sYrYCb8aXs=",4911832839171841208,8195704418653465879,-5293852835223360489,5479878664481765198>()) {
            case 918267603:
               return false;
            default:
               throw null;
         }
      }
   }

   public float f() {
      if (this.a.length == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1guu3of6njpz","vkYVBABulDZ81tJYXCNa2un0qBVlWU2biWUr4xQ7ams=",6503910268683172707,-1346058023737886407,-5790734724860416235,6062444808904003589>()) {
            case -1093199452:
               return 1.0F;
            default:
               throw null;
         }
      } else {
         return Math.min(1.0F, (float)this.aL / this.a.length);
      }
   }

   public void b() {
      this.aI.clear();
      this.cw = List.of();
      this.rw = 0;
      this.a = new long[0];
      this.aL = 0;
      this.az = Long.MIN_VALUE;
      this.rv = -1;
   }

   public void a(ClientLevel var1, BlockPos var2, int var3, List<String> var4) {
      this.bo = var2.getX() + 0.5;
      this.bp = var2.getZ() + 0.5;
      if (!var4.equals(this.cv)) {
         label71:
         switch ((int)com.yiyiaddon.m.b.a<"s1wk43o0x82b2k","CtrUrLHDw7dGj02n6CHfY1qIq9sfbd/Pr4sXisUIHRw=",-1886079366627996877,-150229452700736688,5250580632279182486,622531541660654753>()) {
            case 608646825:
               this.cv = List.copyOf(var4);
               this.ax = a(var4);
               this.b();
               this.bo = var2.getX() + 0.5;
               this.bp = var2.getZ() + 0.5;
               switch ((int)com.yiyiaddon.m.b.a<"s3j6ynvpzqltx0","xyRaxI3qlw3NJM8c50A7CozV9S9GGVVewvOXK0DdxjM=",-3175645289296639523,259481109752917207,7905849037648714265,4246492566522017664>()) {
                  case -1806076336:
                     break label71;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.ax.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s162fsg89csp11","cHDa5vx0Unh+k0p1S6ogz0ccFXUUa+iY1rCNobmKTfU=",-5151702836572062397,7909352439221500252,6141055607767393733,444861901510999948>()) {
            case -1928124185:
               if (!this.cw.isEmpty()) {
                  label50:
                  switch ((int)com.yiyiaddon.m.b.a<"s3pyuv1e9wanf6","jCP36nd4KVRS1up18GgzuvTL91hQzGnyAwey/Bt9aO0=",879307898435379236,7130553693848207465,7406429478744427848,8251091383707549959>()) {
                     case -1643610374:
                        this.cw = List.of();
                        switch ((int)com.yiyiaddon.m.b.a<"s1z6nllytnn4jk","65IJdCMVScatIrkQmW+BUnOYc2pq09wziCA+5sU0jpw=",-6161840425229893939,-5313444544832743863,5816480865795946731,-2843881911523755574>()) {
                           case -1301366028:
                              break label50;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.rw != 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2wfh2lqtp6ydt","LEFEvkSVGcd65bANChvcShlnLvC3dc/drFVAHzq3ozw=",-202560250360354102,9144472753213449805,-6411266865675338694,789928514802073226>()) {
                     case 828540484:
                        this.rw = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s1fzndo1uzxzme","htg2MAJ7B9AQ3KoLS0S9GZy0xzLWLT2T2YrgBvx44Zk=",-6072992831654071858,-5462815977080395878,1558396364940450308,-8071378708729724899>()) {
                           case -16849167:
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
      } else {
         this.b(var2, var3);
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3mpmx36shlx8q","rNy/72M+OCua9ILiIh1YaBO/FrwCpY0jIMuHEey5CNY=",-4152931783913984837,1989298004274077134,-7645162920115253660,-7982047267850970129>()) {
            case -1717839972:
               label87:
               while (var5 < 4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ndws10grz7uu","Rn3E+APxP53kc26HKw9VGtG0qh96u5wAORgDqfKxC54=",8968545080450130805,-8878902701274687080,6864582783782240357,-3610187363106150184>()) {
                     case -502045746:
                        if (this.aL >= this.a.length) {
                           break label87;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sqekw1eqz9ten","UTx6jd4PYNap0ZXXXz4bvpFTCZtlbHz1OjsF1iG2bxM=",-1958909394719279457,-439011462443886516,-2395305583081565304,5622103153946952532>()) {
                           case 270839753:
                              long var6 = this.a[this.aL];
                              this.a(var1, a(var6), b(var6), var2, var3);
                              var5++;
                              this.aL++;
                              switch ((int)com.yiyiaddon.m.b.a<"s3lernfmb9eym","tpN7BQJoeJv3usxXQ+FX0tL+FyXPYjYPTU9THh03ZkM=",-8661119307971762326,2763429026372997221,-6670581421545059176,7280637556450400983>()) {
                                 case -994427782:
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

               if (++this.rx < 5) {
                  label58:
                  switch ((int)com.yiyiaddon.m.b.a<"s1yzq8l86dr5kg","dB/bM9zE+cDIlJyrMwT1fnPMBHsABLd8lYB3JE4+CMc=",1177219640608954435,4455522841126132457,9119521382216515151,6718913244127629613>()) {
                     case -1739908040:
                        if (this.aL < this.a.length) {
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1tcml0fsee2h2","ACx87TV3nH+KxrgZ3b6tuDskliJs1afBBOHYcmm0eII=",8206500420021935326,-5470837720802506587,7305827412915948235,2705748644618602369>()) {
                           case 172169973:
                              break label58;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.rx = 0;
               this.ja();
               switch ((int)com.yiyiaddon.m.b.a<"s1wjb1muwl6fel","uEuTpiACEGHNt2ubN0J/8TofE7h3IWhIVdo84eyZc90=",8598044210591949991,9073257298237487885,1455393435196305909,512313357608509511>()) {
                  case 497714095:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void b(BlockPos var1, int var2) {
      int var3 = var1.getX() >> 4;
      int var4 = var1.getZ() >> 4;
      long var5 = a(var3, var4);
      if (var5 == this.az) {
         switch ((int)com.yiyiaddon.m.b.a<"s1podxfbxlmbjb","bWikORAizhqU4rbA4ymlYqRXn0VZe5fKxff7xu8N0e8=",-2957258390915897374,1456470725627417160,-1126283614337846415,-5881789572327169142>()) {
            case 1833868834:
               if (var2 == this.rv) {
                  switch ((int)com.yiyiaddon.m.b.a<"s14ea48idgjndo","nyiApY8ObruSlfxmPpEE2e7W9AX2xN9Yi3gaAwQ5rXI=",4175133059221145046,5455745890396394614,5259038074317815729,8886904763727523584>()) {
                     case 13795152:
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.az = var5;
      this.rv = var2;
      int var7 = (var2 >> 4) + 1;
      long[] var8 = new long[(var7 * 2 + 1) * (var7 * 2 + 1)];
      int var9 = 0;
      int var10 = -var7;
      switch ((int)com.yiyiaddon.m.b.a<"s351dwzif9yx1t","+m3rPKyuSUzxXnG0qBzlEadu4VJUgZWjgXbCFGZn9ug=",-1791491683552867544,-9145605027479651961,-615416384140129551,2084821348818379206>()) {
         case -795362744:
            while (var10 <= var7) {
               switch ((int)com.yiyiaddon.m.b.a<"s39j4w5cphz53b","Pb2gHIb94AFWTdQFhU6+18jcChj3FdlgjJb6J+fPhrA=",-6845926528072549533,4668550254874939198,8765791355221576779,-5950769390791349751>()) {
                  case -273539906:
                     int var11 = -var7;
                     switch ((int)com.yiyiaddon.m.b.a<"ss5s29soroc46","kVQTgHx7o7jlzT3kgpAKk10YU4tD3MYMsCTybaNdAqA=",2479192444274043998,-2488894897480983580,-7529529441863130513,-8490688721592221016>()) {
                        case -1263068599:
                           while (var11 <= var7) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3fn7fkgfotbdb","BX3dLJQNmPAABV1z944Walsb80i7EuAeJrGwhYFYpfI=",313511615618693634,719845405952676370,-6559590451466638044,7912498239771243357>()) {
                                 case 518248662:
                                    var8[var9++] = a(var3 + var10, var4 + var11);
                                    var11++;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2edlh3540xv2m","M9kAS21S7TS62HOYxH3mlR6t2nkqvwLgDg289yugdcA=",9015191170235243296,7065871119156580909,-6320085370117653158,-6523675143412437565>()) {
                                       case 285888571:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var10++;
                           switch ((int)com.yiyiaddon.m.b.a<"sqhgvkv6dehei","2xt2g8j++aNc4Kas5Tlc/gIGo7VraHHqvdRHdgsaKUs=",1904260491256049046,7850680119276148969,-856672026014734353,-4606086481107825386>()) {
                              case 688617813:
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

            this.a = var8;
            this.aL = 0;
            var10 = var2 * var2;
            int var13 = var1.getY();
            this.aI
               .values()
               .forEach(
                  var3x -> var3x.removeIf(
                     var3xx -> {
                        if (!(this.e(var3xx) > var10)) {
                           label22:
                           switch ((int)com.yiyiaddon.m.b.a<"s27kvr6pdixdr5","tyTKrCnbkLWNi99oIB9NBzVh0wlt9M7zlLetq8pK6eo=",-1506792764657589064,4126712581438180402,7748128662448630992,-4224082308064919375>()) {
                              case 1420587260:
                                 if (Math.abs(var3xx.getY() - var13) <= 64) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2jzqnh9w23c7o","OGVqpa9+fyGQJxOyTI68abS0b0bqQ9WW1HuqaTiF/Gg=",-648471580200106494,2037452359805954366,6203489369332349986,8642529237223062619>()) {
                                       case -1519811542:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"sphokvyj5mq4z","yvpD29JwfGqtenql5aB3V3w5IjfgXhdqTxdcV1MYVQs=",1849907550641798566,3916571644093496274,2709967429016017235,-2442042483362210983>()) {
                                    case -433103754:
                                       break label22;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s30ura7vdwfozw","wvVLaLuJyMVY+Mh2g646Vm+RXzmVRTNJ02oyhaNt4qU=",6082257180158768594,6620038565882182665,-6301652233217548157,5575832801107596380>()) {
                           case -315894878:
                              return true;
                           default:
                              throw null;
                        }
                     }
                  )
               );
            this.aI.entrySet().removeIf(var0 -> var0.getValue().isEmpty());
            this.ja();
            return;
         default:
            throw null;
      }
   }

   private void a(ClientLevel var1, int var2, int var3, BlockPos var4, int var5) {
      long var6 = a(var2, var3);
      LevelChunk var8 = var1.getChunkSource().getChunk(var2, var3, ChunkStatus.FULL, false);
      if (var8 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a83zhbj6u5k9","9Ei8c16ViW7Ah4g2GtkNZe1iW6ygNr5qBv+6jt8sZ1o=",708332116501923305,-6584203004656251011,1532935797184138595,570899718230326794>()) {
            case 471777333:
               this.aI.remove(var6);
               return;
            default:
               throw null;
         }
      } else {
         ArrayList var9 = new ArrayList();
         LevelChunkSection[] var10 = var8.getSections();
         int var11 = var1.getMinSectionY();
         int var12 = var4.getY();
         int var13 = var5 * var5;
         int var14 = var2 << 4;
         int var15 = var3 << 4;
         int var16 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s33r9pj039sy25","ysc+EghcuH+SmMKiDscUFvLTu+11XISGMQFB+3KgNyA=",-2200308050482044405,1480436189165725382,-7080499468017723104,542001727988325417>()) {
            case 1248358321:
               while (var16 < var10.length) {
                  switch ((int)com.yiyiaddon.m.b.a<"s21ry6y9cawysv","7LpsYE1fAuCw0BdY5xwQ3yfVPqeQsBcgR0Y3H/QYBi0=",7471932851255601396,5705156038657541238,1234265374135058473,-9113557606641591209>()) {
                     case 2015552207:
                        LevelChunkSection var17 = var10[var16];
                        if (var17 != null) {
                           label93:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ggwfhq7krdaw","ybW5hUrEaM0Hr1gS+v5+G/ErwdIHqMn655Ubxhc256w=",-4678970423141990629,-3376573985034122466,-304243937177818259,8914533900164132015>()) {
                              case -768695377:
                                 if (var17.hasOnlyAir()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2icjlpw4b7d8d","LkXocTLWueiwfJPgDvY38Ff67ukt1Dy90hWgVMwQVY8=",7377618898176866111,-6276452664601039689,5461359766430337037,-4879799145640296819>()) {
                                       case 417806682:
                                          switch ((int)com.yiyiaddon.m.b.a<"s30t4mxvzwc4g8","rBsmznt0qUWLwxNRjgCdOoEfERh19qUfp6flDnV5VSA=",-4837185853709897454,-4800417207693096556,-2622360544773104480,5607018201098767944>()) {
                                             case 957656967:
                                                break label93;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    int var18 = var11 + var16 << 4;
                                    if (Math.abs(var18 + 8 - var12) > 72) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3c4tynegw74wi","A30PK1JAr7AcIfknR/1kiqlFfFiuOzTlM1yUHH3jnTU=",-7973922597669411862,-7480134428174364641,-8285261940751755832,-5742720317633740197>()) {
                                          case 431571512:
                                             switch ((int)com.yiyiaddon.m.b.a<"s32qm8zjec0eon","fKywSrWx2iBAenO+lmsH8B3wECf+apvFVMDmlnt/LGk=",-1587091325582715368,-5970345816171003163,-1991629335069491537,2947598431824929625>()) {
                                                case 1424863954:
                                                   break label93;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else if (!var17.maybeHas(var1x -> this.ax.contains(var1x.getBlock()))) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2r9mi45roi4xi","57NzGmvePGVd+qPKyv/0cFToMk/iujzKpDlEDCHBJ4U=",7219285920907136867,7497163978174989879,-841744687964660230,3139671271953692122>()) {
                                          case -50394111:
                                             switch ((int)com.yiyiaddon.m.b.a<"ssz4310abirun","vndp6exT+5GoNh3NedntihvUvu/56esSUViP4A0ebhY=",8900495896709635769,-7916621654992160771,-3510719097708693233,3949440096308675294>()) {
                                                case -1085472516:
                                                   break label93;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       int var19 = 0;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1s8w2gs0lp4p9","IVHwVB01RrZgJfxDFP3WwyXv64nttuzVMY0L2ct577Q=",6688063801963167920,1827846553192891623,-5519691115940621821,1894982147786380734>()) {
                                          case -126280930:
                                             while (var19 < 16) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s20ulu9e6c13fc","YIuMlIFhWy7wYb/3eAs9o9ziQLc86so6sFIb94kOcY0=",-7035793905952687218,-2247571924285309252,-7115707392080433411,-8176279197237468011>()) {
                                                   case 2023254434:
                                                      int var20 = var18 + var19;
                                                      if (Math.abs(var20 - var12) > 64) {
                                                         label123:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1lx8z1vx65odx","fDHxxwqhy+R1C70xjZg8j/L/D1j3ZdnPexxosugVSeY=",-5702187691927428419,-2322733771937435576,669245482581939631,6091318134534918747>()) {
                                                            case -926746642:
                                                               switch ((int)com.yiyiaddon.m.b.a<"sgm2o5om0vv6t","wxRH+xuHzEwkmDE8dp31iMJFLg/DKQCFkbejavsRYSg=",-5702566143197998807,7255681870237572539,120282638142904199,2311633549595731980>()) {
                                                                  case -318309805:
                                                                     break label123;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         int var21 = 0;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1inxn8br7xvg1","HquWBde94gqCmgTQbb6GpOOVcWcjido8sJ3ku5fckmU=",5956731702768226907,-2905288467744513358,-7675056133336052210,-974531338662141575>()) {
                                                            case 102235676:
                                                               while (var21 < 16) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2twm6dw5a7u7s","e4B4K2taBFq7KTm/sd3+9pKiH/cFp+ZFZ6jcn+hXJkU=",-975699313120528855,2803932208094625989,7532349804337885788,-1825616639038951743>()) {
                                                                     case -491409653:
                                                                        int var22 = 0;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"stfx7m6bnijse","I86h/lGCzMi0unfOYfDGgmgLPlJc7Vvc42sPG71X8X4=",1716603489393100172,1129383473436174484,6450890145738444617,890396484721378995>()) {
                                                                           case -1290053267:
                                                                              while (var22 < 16) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s3euwotzn3mm1x","mpEaC/6bqCnBB/aylzKHGfuN5DaMGuX69JmqpPpz4Co=",-2602962466340863271,4968577455781132149,-1281303558329362504,-2830748505611105773>()) {
                                                                                    case 363255839:
                                                                                       BlockState var23 = var17.getBlockState(var22, var19, var21);
                                                                                       if (!this.ax.contains(var23.getBlock())) {
                                                                                          label119:
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"s1uu32hqp2v8xu","dtUmZ8i1SudeDx7blevLLd0nWmI6bK94rR8rOLkpf3A=",8221630530656049688,6411833518303408117,-28984154213330193,-7022456744535101560>()) {
                                                                                             case 726166580:
                                                                                                switch ((int)com.yiyiaddon.m.b.a<"s3o5tycnt92aea","NDyMGwY2RAaoCcLtJ8dnadII0DwYYTrn84xWCcZ/pHs=",-553972809419988721,2189547916362011591,5428226852059530999,2058720020076552234>()) {
                                                                                                   case 1950343413:
                                                                                                      break label119;
                                                                                                   default:
                                                                                                      throw null;
                                                                                                }
                                                                                             default:
                                                                                                throw null;
                                                                                          }
                                                                                       } else {
                                                                                          double var24 = var14 + var22 + 0.5 - this.bo;
                                                                                          double var26 = var15 + var21 + 0.5 - this.bp;
                                                                                          if (var24 * var24 + var26 * var26 > var13) {
                                                                                             label115:
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s1blh4c7m6j42a","KosIj1GmTvBruIFdwOInSQK+lfOJi00/ylX3XZNpKEs=",3208594640014744777,6188592867714459707,-2920031183882931671,-3169580863578076005>()) {
                                                                                                case 514546187:
                                                                                                   switch ((int)com.yiyiaddon.m.b.a<"sz9zemp9crfej","P8OPx3nFCqx0YS6yfvNFw5sNee6tdmkI+NHDdlsKsJI=",-3655608417515105978,-1991492733710195651,2283599348053901882,-7156127054024743016>()) {
                                                                                                      case 1546729372:
                                                                                                         break label115;
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          } else {
                                                                                             var9.add(new BlockPos(var14 + var22, var20, var15 + var21));
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s2gjdxsdnxmcs3","p46x4bfXgKibipgmFVpm7VxeSE2C/OK+3+wtyDpy9Es=",-1924425066667338109,121404855599597972,5445520333142337562,1766223302339774688>()) {
                                                                                                case -310630500:
                                                                                                   break;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }
                                                                                       }

                                                                                       var22++;
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"sjodw43q0tw9p","RSi9t79qqfQor/q/KHQ6Ovxcr+LpjKw4d5DYR0mjBNc=",8257521060289072617,-1871253272735070813,-52461374494372146,5815912806850841013>()) {
                                                                                          case 9636898:
                                                                                             continue;
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              }

                                                                              var21++;
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s17ugalbzismwm","a7bHkCVDoFoCvBacIDejTtdPsp+byvdrZt+1eXSWdq4=",2525220673554626023,566017964254930901,5528400384926980214,-5504357811174100745>()) {
                                                                                 case -1187064136:
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
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var19++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3rhiaq26u8z8v","xB3DPfcZv0IZn3mudGlgBlPfe5gtLTaLCypOLMRhZdU=",-8858401458178733467,3477783453696810653,700112850016067562,-6563683137698633977>()) {
                                                         case -1844205147:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break label93;
                                          default:
                                             throw null;
                                       }
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var16++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3kc4209uclbgv","ddUklMPILhNhWtUDEYWzMu03c1ORX7rxgEm1Ev+zXvM=",4505229075668103906,-4308907077093754018,-4626453731821935342,-8057246608615115542>()) {
                           case -551778781:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var9.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2uktvdtx1i4hp","xukBqXDygUQwf1YSp72X16JO2OWeP+FN4i5Z3iIy2JI=",-1046848349949717739,5884605196325104836,-1872790541541929672,366241391031843822>()) {
                     case -1329336122:
                        this.aI.remove(var6);
                        switch ((int)com.yiyiaddon.m.b.a<"s1x44a71tpeleb","RscYJ7r7LKLOeMsweoFIWlR2XW/c30F9QZTXvxOxUUM=",3543152358637922067,-346368215695923039,-1517744540902890093,-5577177858202366495>()) {
                           case 1157541995:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.aI.put(var6, var9);
                  switch ((int)com.yiyiaddon.m.b.a<"s3p9ch9m1douck","BNYg0Q/zqdDlU3pV381i2HfOHHfILr4W5E+Mek9Du3o=",-1300066023103515932,-8906547917241709539,8831365456492406803,-6276255575162143340>()) {
                     case -1914986887:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private void ja() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.aI.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s27qy9tygem5pq","MFV67+Hz6/buXKJD1NW7EG5RCu9+BQETISDtLr0gVXA=",6723772872152462403,-4128127426173869103,7078088411503255785,-4749723345602835270>()) {
         case -2120454129:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ckl8dcp83gut","gjF+vQtMWFUFUpPZXdyijPSrkzS5ZjYo0cwP/1iXjjI=",-1616382177785052884,-2937619179911984768,-3934758874985775743,8246171889733731560>()) {
                  case 1613987832:
                     List var3 = (List)var2.next();
                     var1.addAll(var3);
                     switch ((int)com.yiyiaddon.m.b.a<"s14104gnete6et","xKR9+gdaw5T7tHoKh8IWe3KJZ+QqqHDYYyFSW4IxxaA=",5987840338803301190,-1882432051919264711,-2434020412023210827,-8969056725647361949>()) {
                        case -1228841094:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.rw = var1.size();
            if (var1.size() > 2048) {
               label20:
               switch ((int)com.yiyiaddon.m.b.a<"s3vx7vzzlia7rn","zFHoBQnRKp1VMYTRe2Cyz/h5WqyckjAlBn83Mzx8cjk=",-2656623115573239889,-1687550653611633410,-5535511524291839014,-4611221683238309727>()) {
                  case 45127060:
                     var1.sort(Comparator.comparingDouble(this::e));
                     var1 = new ArrayList(var1.subList(0, 2048));
                     switch ((int)com.yiyiaddon.m.b.a<"s3d97kp6dq54kg","SSc+R/3Z9cGZiaAdC+oRY/2lSmIMMozEj/qOHkPWIqE=",829598066076865102,-3414734920242456345,7909716670883624245,-660373471059328560>()) {
                        case 929071325:
                           break label20;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.cw = List.copyOf(var1);
            return;
         default:
            throw null;
      }
   }

   private double e(BlockPos var1) {
      double var2 = var1.getX() + 0.5 - this.bo;
      double var4 = var1.getZ() + 0.5 - this.bp;
      return var2 * var2 + var4 * var4;
   }

   private static Set<Block> a(List<String> var0) {
      HashSet var1 = new HashSet();
      Iterator var2 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1nnym7w5qt4v","GvyUl6hJMKC2zGmVrLiEdFHp7a/7Sf79Gh2tipNK1aY=",-2857454933433213180,7550611539060565912,-771015136999416017,6285032579082965587>()) {
         case 1153451598:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3nke6cgwv84tt","XMvxtPnRJ35G9aTt6XKVPoZ9WRM+2yrn7QfYIIvRO10=",-1883474263958349689,1344663839155416955,3497655211221032446,3840906892538238884>()) {
                  case -285389409:
                     String var3 = (String)var2.next();
                     Identifier var4 = Identifier.tryParse(var3);
                     if (var4 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"su0uwq2kcmsi3","FAhYvnPm5tIDgCRs7hfqoILTLzJE7DCQk0eXMtOkZx8=",3786740849428660537,7349800587518877143,8309234763372717260,-8268246096092653305>()) {
                           case 1570220982:
                              switch ((int)com.yiyiaddon.m.b.a<"s1pqvf9od97zo1","5pDFToY8gyvE4SVti8gI+yKyg6ntJ0yi2tdeQm/TEQs=",3504805653165068748,4388868812461589510,3451934280828615847,2917176331591181724>()) {
                                 case -1547826396:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        Block var5 = BuiltInRegistries.BLOCK.getValue(var4);
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s275a2bjacrr4r","lS99W0eMN0vDXAHrIMlI3e0lFvsFsjExYbkyy25yGaw=",-911634871371146885,6300526362142481436,2973628715465050020,8769912046595999367>()) {
                              case -628476844:
                                 if (var5 != Blocks.AIR) {
                                    label33:
                                    switch ((int)com.yiyiaddon.m.b.a<"s4hbd1fjtgl4g","+zRvzSbY+V8n87l1C9tL9DNWwI3AK3oyQsZ6ICBdcL0=",786093469061176243,3569365520834044854,-1722909263128278426,186533530265132990>()) {
                                       case 1917780914:
                                          var1.add(var5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ih6tqmhfx5zb","VIXQzDtqBCEf35FvLvpIr191MM5nyH4wkAX9hlyqZVw=",4980875461908514475,72497185822671364,-6952543265923081817,3281754351157023699>()) {
                                             case -1343311016:
                                                break label33;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s2jem5dz9t7tgi","uWwptbxtGeXHZaJYbhy0jCf9/wJC7mWp1rDFnlZgsXE=",-8172659611600406885,-5432497951965315576,4882151225319713491,5460267968104077156>()) {
                           case -1245132017:
                              continue;
                           default:
                              throw null;
                        }
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

   private static long a(int var0, int var1) {
      return (long)var0 << 32 ^ var1 & 4294967295L;
   }

   private static int a(long var0) {
      return (int)(var0 >> 32);
   }

   private static int b(long var0) {
      return (int)var0;
   }
}
