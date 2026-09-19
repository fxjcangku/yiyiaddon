package com.yiyiaddon.e.n.h;

import com.yiyiaddon.e.n.i.f;
import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.s;
import com.yiyiaddon.e.n.i.t;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public final class b {
   private final Minecraft Y = Minecraft.getInstance();
   private final c b;
   private final p c;
   private final com.yiyiaddon.e.n.r.b b;
   private final com.yiyiaddon.e.n.p.a b;
   private final com.yiyiaddon.e.n.c.a c;
   private Function<BlockPos, String> a = var0 -> null;

   public b(c var1, p var2, com.yiyiaddon.e.n.r.b var3, com.yiyiaddon.e.n.p.a var4, com.yiyiaddon.e.n.c.a var5) {
      this.b = var1;
      this.c = var2;
      this.b = var3;
      this.b = var4;
      this.c = var5;
   }

   public void a(Function<BlockPos, String> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s16k38kjupxkp6","pP7CXsQ1jlG/HnVfF490CUrSoFzGTCUZggmsLegrJYo=",5997750032607015470,-7720548460757544600,948407030687138178,267689877453998472>()) {
            case -1415635850:
               this.a = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s199hoez0fn46j","MqNeNhKzlcCsY31hDTuQFNNhzcU6CR0yrPWG2EXNGSc=",-1785256458080401216,5063926477999271594,753616534274945365,5087433807541880173>()) {
                  case 1770093431:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public boolean a(d var1) {
      if (!this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1meqd0arhit9y","iOJjweQOUhh4cCIz8oUpVwdVBVV0C/BkGT7AXiijYwo=",-5792508522775191982,7398946962837371547,920917765985856114,-5712566619463458904>()) {
            case 284644170:
               return false;
            default:
               throw null;
         }
      } else {
         String var2 = var1.a(this.c.a(this.c.bz));
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2yfhd4j52l4a7","8Py/u00vSrE+QG6x7voH0ks0JHHu57D8giDCJih38iA=",7037768077645905916,9196899291489192104,2262588145592630716,5611976073588524478>()) {
               case -551995873:
                  this.k(var1.D(), var2);
                  return false;
               default:
                  throw null;
            }
         } else {
            c.a var3 = this.b.a(var1);
            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1y3eubseleock","XBROTborHL1PFQT0qyElb5eeIqzF7USdUgQ1l18J33A=",7695189837462367975,-7814421136864016273,-5634893604003075109,-6439023318053912447>()) {
                  case 1793355195:
                     this.k(var1.D(), "" + var3.aj() + var3.ak() + var3.al());
                     return false;
                  default:
                     throw null;
               }
            } else {
               BlockPos var10000;
               if (var1 == d.WATER_SOURCE) {
                  label66:
                  switch ((int)com.yiyiaddon.m.b.a<"szltcc1yve0te","5kDtmseoeNM3zwlWsm5f1c7uiHKHUgIq98YDanmpipo=",5634606535837890292,-1186539139082320673,-7710772420357386781,6180658374738830105>()) {
                     case -1046824797:
                        var10000 = this.p();
                        switch ((int)com.yiyiaddon.m.b.a<"suu7a6u8ljm3g","w/4HxEW1GI5JGAgb0ONsjRuoHnVFcm8tzyVCUQ6VBVk=",-3045145958178562468,5789038146679542540,-3589507366274111287,6460390205363080611>()) {
                           case -1198811330:
                              break label66;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = this.b();
                  switch ((int)com.yiyiaddon.m.b.a<"s1ziqn7quw53wo","9VI32oQ09x3ZoY1V6/5QAeef35cXE33dFQU+LemoUSg=",-7308365806417193047,3821486582158574820,-2101739187343642648,536862488095748667>()) {
                     case 2095096107:
                        break;
                     default:
                        throw null;
                  }
               }

               BlockPos var4 = var10000;
               if (var4 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"swr83y3lbzsdt","l75rwaC/EbjKYQHbXTpWXMa3Fk1ty8/tr8dNeTbObRA=",-4174347925853501995,-5698863506233498343,-7615029008398566704,4185625782025948259>()) {
                     case -1457910851:
                        this.k(
                           var1.D(),
                           (String)com.yiyiaddon.m.b.a<"silxpvo7v4d32","9/u4j+neGUJoZQzXuzOlHOrtKY46MYLCuvumNOaleMCb8oBXOo67/Y2Kulk+WO7Y",-1976089702653444463,-8490614284197925400,2114849365795514182,8514640559495897321>()
                        );
                        return false;
                     default:
                        throw null;
                  }
               } else {
                  if (var1.H()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2wwxkmj1qppkg","uHlvoMiGHWmf5xXD/smlA/HQWvKBSdYNN7eySWZxgwg=",-6825813697261973017,-5822169778538960426,2659926208925650423,-8315768226989321871>()) {
                        case -743761083:
                           if (!(this.Y.level.getBlockEntity(var4) instanceof Container)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s8nsmmw8zjtyq","a8zIKbCMpgzsT9mb0MLwLogCdSW8g3Jl9ZZusu5LtCE=",-2391675705098093493,-7774635721691516305,-6191387596675228993,8913495454377400149>()) {
                                 case -1379459730:
                                    this.k(
                                       var1.D(),
                                       (String)com.yiyiaddon.m.b.a<"s3696id8jqyigr","oqVHh+B9Lu4VfHr/vyIyuW+s1yuFIKRv3/dB5SQwuaCpv/EHcX0ZTJsF7aGlw/NTSM2aqIoXP/Gqv00CTs32sOtT2hLOpY9Tafg=",972682322900539759,7066641895769570437,-7047572922892472623,1155206739999895231>()
                                    );
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var5 = null;
                  if (var1 == d.WATER_SOURCE) {
                     label55:
                     switch ((int)com.yiyiaddon.m.b.a<"s25lbrefg1we3c","ANf/LLvc0n84iZVefZX8+GU2xXTsY1SGuWLtCo8pjl4=",-4684143214717049322,3653292563515004959,-4204201837034513288,2774942548079238669>()) {
                        case 541023134:
                           String var6 = this.f(var4);
                           if (var6 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3izc0nsxfk8vo","3p0ky7y1a3V19c/8QnAmJhrTpY6aTFmhh7gDjuys5PA=",8236671498057161968,2213463005304160172,6567522760312160559,-5182768166187351480>()) {
                                 case 1716109944:
                                    this.k(var1.D(), var6);
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           var5 = (String)com.yiyiaddon.m.b.a<"s2aq8a0zpb2h50","l2FbrDfOHMGjvDpGRHrqDcN7NXWPp9ip9MdNHP16faagRYcR",-1058932009400436271,2919020891889858342,-7051119258272094417,1287161179228025178>();
                           switch ((int)com.yiyiaddon.m.b.a<"s20m41w1r1nez7","DIypDC6Xn/LbGeWcfEwiKmlz22YG41wltctruZehWIM=",3445639777115035465,7352953846938400856,6242774325795798650,5034053974089176789>()) {
                              case -694901156:
                                 break label55;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a(var1, new c.a(var4.getX(), var4.getY(), var4.getZ(), com.yiyiaddon.e.n.a.bU(), var5, null, var1.D(), null));
                  this.a(var1.D(), var4, var5, null, null);
                  return true;
               }
            }
         }
      }
   }

   public boolean cL() {
      return this.R(null);
   }

   public boolean R(String var1) {
      if (!this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gb8celbix2d4","p054KT66OxsJ/MgccdLxN1Q/GP7vKZ6rWJXhCEvXsAc=",-940287097268551794,-6276818997809445251,-3934531236800080032,-2732741017789683834>()) {
            case 2099989240:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var2 = this.o();
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s257u0eyyu4ndk","+EQZqzR7pHHL+EO2ILuOkWNp19wXekss/aJM1+Kphls=",8082942262233109159,5283364474239513027,-8181825536875977626,-6474681202271924424>()) {
               case -1389375426:
                  this.k(d.SPRINKLER.D(), this.dt() + "");
                  return false;
               default:
                  throw null;
            }
         } else {
            String var3 = this.a.apply(var2);
            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s34gyx8qpo3pi8","c8iSCNSzQ0UAMOXuetQeRkk6HNIxvGhxelE4NcAHAqo=",-8275032157753664557,-2741465498563387423,-4667259404579821631,842728694719050477>()) {
                  case 1514772208:
                     this.k(d.SPRINKLER.D(), var3);
                     return false;
                  default:
                     throw null;
               }
            } else {
               List var4 = this.aN();
               String var10000;
               if (var1 == null) {
                  label133:
                  switch ((int)com.yiyiaddon.m.b.a<"s2u87v7q1ci4hr","tWnffab8dr7ZRdxlrl1boEo+IWhGdo6Au2x8pKB1vsk=",-5306326334804330121,145337584806720407,8771762300135789553,-363135501203482456>()) {
                     case 2129398746:
                        var10000 = null;
                        switch ((int)com.yiyiaddon.m.b.a<"s3lfzseoxpghkr","3VZmTMGychQ70KqNrORSSH/SvwGQUfQI8KmPjTw48zk=",-1780609903328807936,587558881832483908,-2882832469039918571,6351470747135484109>()) {
                           case -2021356081:
                              break label133;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = var1.trim();
                  switch ((int)com.yiyiaddon.m.b.a<"sldq05xyvjkxn","XVCG0+iBwXJJwHTxh8fX8qEjl6u2leW7i6oyCV6qe/A=",-7575897229261124370,-2476788109800218650,-4357326397758171762,-7978037129471131271>()) {
                     case -385232592:
                        break;
                     default:
                        throw null;
                  }
               }

               String var7;
               f var10;
               label152: {
                  String var5 = var10000;
                  Object var6 = null;
                  var7 = null;
                  if (var5 != null) {
                     label129:
                     switch ((int)com.yiyiaddon.m.b.a<"s1ogpbldmq4rz1","GVTPpd843F9eybX92O2rD/mYpEU+3xev9DosFDYu+hw=",-2478571669355111327,1837508842190322392,-6515445609257644702,-6135794233954977010>()) {
                        case -1276424125:
                           if (!var5.isEmpty()) {
                              var10 = this.b(var5);
                              if (var10 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1qzsur8hol8mo","JrKeUtBk9kkx1RN7XvwekTT+xMntCjOuck8SoP31tDk=",6021950631427005650,-2196879118161406739,-164614999380870307,-1190119433788210606>()) {
                                    case 2041769318:
                                       this.k(
                                          d.SPRINKLER.D(),
                                          var5
                                             + String.join(
                                                (String)com.yiyiaddon.m.b.a<"sxjvqrzc66baz","g6VBUdRt1I+6h49JnnehbqNbdHRj07jZdBXChH6x",7944906320533623161,2841519038670150621,3220255128440379301,234665083295199340>(),
                                                this.aO()
                                             )
                                       );
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              var7 = (String)com.yiyiaddon.m.b.a<"s2srx4o8hghe4o","tz4o/kwQdBzHVpXiXzoYbUVfhrfPCXYekmsBDoMdTzcqhp+1/8caUMjNZZI=",4923947339750006510,-5738124266766342322,8721738253113177036,-6796756820547337856>();
                              switch ((int)com.yiyiaddon.m.b.a<"s3kiyzwfwpxyhx","zBfuDhd+lkYduFdUrgsxaKq0A9xpUrd4O5ItovlcurM=",-6345372669186095095,-6602641135640654037,2775867637513345928,-7416629062543511524>()) {
                                 case 908024672:
                                    break label152;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"slk183rq8glzx","10eETKjnLRfTITsZz54J5or7a1GTal1DDsYyA5mKToU=",-5569734225145659992,5559602092659831071,-7475722281574657896,-861359322547455652>()) {
                              case -1097410805:
                                 break label129;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var10 = this.c(var2);
                  if (var10 != null) {
                     label124:
                     switch ((int)com.yiyiaddon.m.b.a<"sx1n4rbkqehpv","h26A3yW1qBe5kMuptEEgzzIaZDKfbLmgutzVVc2IM7o=",-4786185721510980968,-1973653906517740567,4432102322486179479,-7060221977000941908>()) {
                        case -2135283273:
                           var7 = (String)com.yiyiaddon.m.b.a<"smufoeuc1mtlq","FeXxjDLlIAt/anD3aarVaX/FCrknYN2efCNQVw4yIq0jTB52yUuyJ3VL",7334512770931152545,-117886581923313037,-5540125549503057907,-3723611675888948345>();
                           switch ((int)com.yiyiaddon.m.b.a<"s2ylmx8hiavcoe","i7DfWAEpzrMijNQp2EONuPgiKsz1EU7mR4OcwUhsyU4=",179935766421631951,5580514731615709816,1734985454323085305,6131887632941596133>()) {
                              case 1645186575:
                                 break label124;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var10 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vmurupa2850b","45czJ5p1YboB7u3IW9EMAioq7fFk3eX7vHXzdksvUxc=",4143987041330891864,5692316915155873892,2424799795904323200,-5298995177745827675>()) {
                        case -552119369:
                           var10 = this.b(var2);
                           if (var10 != null) {
                              label117:
                              switch ((int)com.yiyiaddon.m.b.a<"s2040vsgr1sglw","IbOy3W4q6YIdkQsYlMfBx5rMh854IYPEsrZb05A+GmE=",-5310149008730232379,8690553111659074346,-7913983990195122164,8770363689833326282>()) {
                                 case -1050667537:
                                    var7 = (String)com.yiyiaddon.m.b.a<"s1j6viee2b96v0","1x6dWcRUJ/wm0Hq8tSAoD8eSGSUtOk8uCupB1iPEOtlF8/UQ9GLKRJ1Z",8486092831030858597,-302962105041122027,-5400655033156748341,8430225078400848362>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2qdg86ltm802m","vLmQCsBY74uOw2RLtK6gCK8JlyTDVDOFNW08LEhWhjk=",-2654311646086211948,8661938659366878829,-344661651077615093,-3045062512669771062>()) {
                                       case 1523028304:
                                          break label117;
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

                  if (var10 == null) {
                     label112:
                     switch ((int)com.yiyiaddon.m.b.a<"s3j2vajd2sgqsf","v9TsDV7U00XK1sQL715dbe8AU8DcTrW/WxqalAgiM5E=",-4315839281968346840,2427589868816025321,76393496781279803,1838393076659090788>()) {
                        case -943384359:
                           var10 = com.yiyiaddon.e.n.h.c.a(var2, this.c, this.c.bD);
                           switch ((int)com.yiyiaddon.m.b.a<"spbfe2ej3qi8l","Y9EsKm7wTIHIX2dtk168TkrW8KrsQhc61t+LjNSbmZM=",736149962522042756,-6543172914890737778,6445248348510518766,6826597488265938736>()) {
                              case -169537359:
                                 break label112;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var10 == null) {
                     label107:
                     switch ((int)com.yiyiaddon.m.b.a<"s3s53lt6q5gytg","c8XMDVQHgZ31vvYmhHxtDeVER4jbHX8dWgSzb0oxocw=",3057661570644089062,1154236848797178069,-5690306412950275218,6047783093821279134>()) {
                        case 1367405839:
                           if (var4.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1vahsg0c8unk","ZdMwFC5Xa2uTPWa7/weKP9E08U6GFTsQKPyCVjucgEk=",-5764078046939476272,-1021700954896908067,-5094571705792588232,5845069639364678619>()) {
                                 case -1351814857:
                                    this.k(
                                       d.SPRINKLER.D(),
                                       (String)com.yiyiaddon.m.b.a<"sap2qx4bzwvve","wtrPVxy6uuHDIHHwLlm6yjD9iGtr1ofiikLoEIk3MQtJAiSscbVfRLdHrHZ/+BYJfibmNHLiC6+FJeVyZNkXqwiFijKX7vp0rfnYp95JtQYYs0eZmgG27fYWK70ZHA==",3386566091467439407,6110665761018516652,-4343480138437068998,8350894476447486787>()
                                    );
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           if (var4.size() != 1) {
                              this.k(
                                 d.SPRINKLER.D(),
                                 ((f)var4.get(0)).m()
                                    + String.join(
                                       (String)com.yiyiaddon.m.b.a<"sxjvqrzc66baz","g6VBUdRt1I+6h49JnnehbqNbdHRj07jZdBXChH6x",7944906320533623161,2841519038670150621,3220255128440379301,234665083295199340>(),
                                       this.aO()
                                    )
                              );
                              return false;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"slwhswumk4c53","7VEoiM2qsRDx4eUff/l3G41Yol/0FlyhOln8A4vSVvw=",149146606155228960,-7232527222530655364,6399503200470516247,4726585911829728428>()) {
                              case 665748608:
                                 var10 = (f)var4.get(0);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1x4r566t075hv","BQGRKOju1qIhzRUARJRA05/4W9bhb3oOJLmBw04FXC4=",3816847889456007633,-5731966363870713353,1936192778717923919,-8948244192121534199>()) {
                                    case 965505376:
                                       break label107;
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

                  if (var7 == null) {
                     label98:
                     switch ((int)com.yiyiaddon.m.b.a<"s3o6l0dkoyhk95","IPXTpvH0tIMo5ScFhsKNE16qZ6QWHMl49IensK4NHc8=",5619153922939887705,-4212562363189587934,7262363409891893467,5592798206848415304>()) {
                        case 1892007986:
                           var7 = (String)com.yiyiaddon.m.b.a<"s2srx4o8hghe4o","tz4o/kwQdBzHVpXiXzoYbUVfhrfPCXYekmsBDoMdTzcqhp+1/8caUMjNZZI=",4923947339750006510,-5738124266766342322,8721738253113177036,-6796756820547337856>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1ofpayqgz8at6","6uxJk25fMoORuvi24/aPlXDGlCgHv+7lYqsJkFok8AU=",-5363342045140598834,-8032100286271902001,1215771735615842285,3303303321588401302>()) {
                              case -1155045709:
                                 break label98;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
               }

               com.yiyiaddon.e.n.h.a var8 = com.yiyiaddon.e.n.h.a.a(var2, var10.L());
               if (var8 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s23xe3k79dqzmk","INj6iXXiotqd4vLlbpTvbdhEYVINlgVF09xOrqJYJ1k=",-7857568635787568262,8167306599643189422,6605542380546585820,-4960206137735608073>()) {
                     case 904283628:
                        this.k(
                           d.SPRINKLER.D(),
                           (String)com.yiyiaddon.m.b.a<"s1o0i24p6ybmxp","E8c35fBbCxx5JUhn/0RFzFrl48A47yCc7u64kJHoNaLlAUZGIzLfXwu0y5WqxRvrj2w264HsE5JHHQKEXFE=",1479377062798762772,5359718541908198209,-8742251762421061340,1174587014171747980>()
                        );
                        return false;
                     default:
                        throw null;
                  }
               } else {
                  String var9 = this.a(var2, var10);
                  if (var9 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1ylh7lv7f2gty","W0cVSxMFtMUWDM5o7mgy06fyc+dG2rMqzqYKuZN8sP8=",379435355068536387,-7724424959724438226,-787687973168062107,-6009046526800036912>()) {
                        case -594871631:
                           this.k(d.SPRINKLER.D(), var9);
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     this.a(d.SPRINKLER, new c.a(var2.getX(), var2.getY(), var2.getZ(), com.yiyiaddon.e.n.a.bU(), var7, var10.L(), var10.m(), var10.dS(), var8));
                     this.a(
                        (String)com.yiyiaddon.m.b.a<"s1nbg1kfjgq5c9","k+Ka6HtG1Yxf3hcYMv+iuCIICyrnkI0EkTA/9xG8uKDW5SoZgwo=",5075793255307014392,-1422695141701510808,4515334394306475648,-4039216347346341406>(),
                        var2,
                        var7,
                        var10.m(),
                        var10.dS()
                     );
                     return true;
                  }
               }
            }
         }
      }
   }

   public List<b.a> a(AABB var1) {
      if (this.Y.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dy4ajntnbpic","roydC/R2hOftF0JCYta+xcOs6754nbYmy8EL38rDt58=",-6061673337074162272,-3158901679774080555,5711632059843546753,-2908115843056846224>()) {
            case -214375694:
               if (var1 != null) {
                  ArrayList var2 = new ArrayList();
                  HashSet var3 = new HashSet();
                  Iterator var4 = this.Y.level.getEntities((Entity)null, var1, var0 -> var0 instanceof ItemDisplay).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s28d9gayor3wwz","5r5R7mP4A7Pt2aM6x+r+QUUea18Up0PNiwAwoPSwVo4=",-4428878745776438239,-3136782227940750115,7654589693197713183,84987211323188620>()) {
                     case -286052229:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1mn3ho18stimz","9eiTyxMn/5oHt+1GEGuH/SF94LVFofG9oMYWi4vvGn0=",7847149899778061069,-2679818532777534655,-4259480940823651610,-8730446996214648575>()) {
                              case -2054458952:
                                 Entity var5 = (Entity)var4.next();
                                 BlockPos var6 = var5.blockPosition();
                                 if (!var3.add(var6)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1nif1pq5wnunm","U8nbzBmNWyv03ZCmJeM5Yl1eZfxQ2DovlfhtoTUfQgA=",-5961624435488649783,-6854055399526073772,-3003052667164083502,-229207652882775763>()) {
                                       case -1168444827:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3pdgjzr9nqcas","mc4hnjUNWo2403CFzVXC3vm2USZDqb2Olp19nL6b+OM=",-5811702065166450391,-6096279496238678218,-3667375120655175187,-196799481351148555>()) {
                                             case 1321658674:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    f var7 = this.a(var6);
                                    if (var7 != null) {
                                       label35:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2bknz2mnru917","wGND7fE13oPJWHbxHMn7w4bn+QZBfpvEMTzJHqIhOwo=",-5779108739559113911,-4569872801420036339,5418158694690878677,-3093733700105907407>()) {
                                          case 1398286703:
                                             var2.add(new b.a(var6.immutable(), var7));
                                             switch ((int)com.yiyiaddon.m.b.a<"st8bpxfduc14o","+YG8eUMRYlYMX9uRsB36BWKDAhB80kIwh49AKvR2IpQ=",8865520237851547516,-6036875569761708018,-7928111832534381702,-1852180617947524635>()) {
                                                case -871321777:
                                                   break label35;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1akygui50td4o","EFrAUc4UH0gduf1vUy60iYJR2+oQ9jSMSutzKeAhiJs=",1522307958328772691,-6623519557763444684,-3222755942485740785,505886172376052070>()) {
                                       case -1196595791:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1brvunopir3hx","zMmLju3IotZEpmw+nJef0myQDZOOpw0osE1qfEpc9p4=",8757285958120711643,4353402140269747903,-5349934856022523535,6644787650989777424>()) {
                     case 823520058:
                        return List.of();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return List.of();
      }
   }

   public f a(BlockPos var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13y97rz65u8k","GgGqT35BJp09m43w0ElTTLs0W4arh65PrqxMpsU6TOA=",-3365898396147972217,2489352491440368689,-1248826196188764406,3130291810652327418>()) {
            case 621116389:
               if (this.Y.level != null) {
                  f var2 = this.c(var1);
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2uq5upd2j5wus","GBbDgNueSVv5aOlzz6jmxfG6MIwozXzc6ZZVw/ZkEbs=",926264039334025284,1960065358201485240,-2144407318297066684,-7328174511583867377>()) {
                        case 871332367:
                           switch ((int)com.yiyiaddon.m.b.a<"s2yxq4b1odajmm","2j3WSf198nwY0bmT9jaY4yZh0D8vApjhc/esKe7JiS4=",8050540193587708766,-2105968524783122500,8642007683828433514,-5195366524965208749>()) {
                              case -1522830052:
                                 return var2;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     f var10000 = this.b(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s2q98ox0ujundw","PkCtjREDDuntb43a4QPj+Rn4o19Z4fP/M25JEtWqXNA=",1043836205832637442,-3240532664066229789,-6045380811417633044,8355303396500807557>()) {
                        case 248643031:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sw8rfrvjip6hx","szBmNG4vZp9ldFxG8ZoEP5lpv5wI53pbEb5PTPcXmdI=",-8267204262450130677,-481345849584359107,-6438447689246820947,-1601657935102805732>()) {
                     case -848273139:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private f b(BlockPos var1) {
      if (this.Y.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26ugyhkix0857","dkJvDoZRLi9JVpVImOisFx0wQuuDG3BCu21haS/AxaI=",-1961939848524463523,-519135900889768735,-6646717694377987148,-756116994893788673>()) {
            case -1188984235:
               if (this.Y.level.isLoaded(var1)) {
                  AABB var2 = new AABB(var1).inflate(1.0);
                  f var3 = null;
                  Iterator var4 = this.Y.level.getEntities((Entity)null, var2, var0 -> var0 instanceof ItemDisplay).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s27i746vo1arvv","Djj+Y8kPDeQJka5nKlZFec8kDI1ALjGI8x18duVPOrY=",4667711082215758495,3468856344857670174,4222818495926760590,784967055631040986>()) {
                     case 1130903935:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s990grikk4xm9","RqVhlf424ZWlUZBGtnApwKehs8nMcdY02CnV6b2Q2hQ=",-12059038739730329,3336499148102216465,8237120443056901784,671341277779318559>()) {
                              case -912803796:
                                 Entity var5 = (Entity)var4.next();
                                 if (!var5.blockPosition().equals(var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"szxq4k5ur1rig","KPzqvolHpO3Wi2MUeH0M8K4vXwwh+o+dYHo6b4CIsGA=",3504211546587601970,-8489484266871807114,1427832341834393966,6641092154131645922>()) {
                                       case 43059283:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3517fhfa36cs2","e7TXKLy8qFEP5HwGyXdqlPuA4MzbDN7P1/DY4glZVMQ=",4495899918399820936,4033478629514475867,709901909254832523,-1855705562050372502>()) {
                                             case -315022869:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    SlotAccess var6 = var5.getSlot(0);
                                    ItemStack var10000;
                                    if (var6 == null) {
                                       label77:
                                       switch ((int)com.yiyiaddon.m.b.a<"s20g5etvld6n7k","EBHp5DD3vuY/0BruZxxTOMKnzC//fSBuvM/r9TRauvs=",2886617014118018656,-5988892138009369734,2503246305517180708,-1535329685658373884>()) {
                                          case -687090419:
                                             var10000 = ItemStack.EMPTY;
                                             switch ((int)com.yiyiaddon.m.b.a<"s6id3vaqc8811","sepGpXHQH1Mu0JlC8HNV6byBfD1R9b4+Se+sjfLcVkA=",350472333604532458,-5919673215582687763,5261058427918633066,-182387637158776238>()) {
                                                case 1151782415:
                                                   break label77;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10000 = var6.get();
                                       switch ((int)com.yiyiaddon.m.b.a<"sn1kqg2wv7hgr","M1Vw8L37oD5VNrn9ZQkRMs9zrCgns1XMnyxaty1onlM=",5893163341214451369,-5142119187764239547,-922832542400988151,4835706565569240713>()) {
                                          case -678561183:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    ItemStack var7 = var10000;
                                    if (var7.isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sx5rq3k3t7pii","6ZJnM8GnE286McVHq0RAx7idsJBnC/uPb45tXDde644=",2540001640497877997,-5585230635167392493,5934171639037993454,5009211993399721835>()) {
                                          case -707860973:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3f6q1tln45zkl","+h9InKXMEZGOxoDPHpW4H8JlspaLREypPJOlX0Tcasg=",8795092283517780929,5742748839115051440,-4054026653911261922,-65817306063545088>()) {
                                                case 731591538:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       Identifier var8 = var7.get(DataComponents.ITEM_MODEL);
                                       if (var8 == null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s12xbznb1b3m9m","Aez+ibr2emoQ8Wpac9u6IDVnbMTZLpfnXSx+fyRJ9Bo=",7685417182433099684,-997942007777619446,-7076583955808969946,8478229754978836075>()) {
                                             case 931817367:
                                                switch ((int)com.yiyiaddon.m.b.a<"s16n8xzp5fdvv9","8Zbn6UODUUfD9ojOZGuhATAD6iQNR8nkIEsyYbPGYlE=",3423172374696472083,-2081877568035156752,3306777714016257256,6056489173419740524>()) {
                                                   case -2078189212:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          f var9 = this.a(var8.toString());
                                          if (var9 == null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s279p0tiqf6p17","U67ThQvnH72N2QjjRtd9NavAhDRKl7oKcuVaRazD/Bc=",4075792426946920380,2412281786693362990,7153629688301648998,-2775842526224311672>()) {
                                                case 960832960:
                                                   switch ((int)com.yiyiaddon.m.b.a<"sa84cv5fydxve","ST9PIGxcZANxqzJ/FTseoP99/JbewPuayJy550cLTiY=",4039747185742639513,6485997831115306680,-3833957439474289744,-8100349667671275236>()) {
                                                      case 1992282949:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             if (var3 != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sa4vj0qlfclvz","xCyIMBKKJyMvDLlcETYTzDP/cyQ6ZMaRkd+RMK1Aq0Y=",5722973514874342436,1665903757490313159,6821182439732888213,8011398748792104224>()) {
                                                   case -1844575439:
                                                      if (!var3.L().equals(var9.L())) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3erpam3gdoghp","VOdpEW5zuvTVapYJuVkQ8hZEW9dj0qI+bGeE5DSTG9Y=",8846912363207325110,7207431709634591987,-2501918441628484797,4566360478808265486>()) {
                                                            case -1379273053:
                                                               return null;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var3 = var9;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1lwt6pj6gtdvj","rGxv/76Bqce2ATjZ+tp0SQC9VHkStiQw9HgWhezMbFA=",8480752745036222240,-4678657297716091961,2354458390443417362,-3393000046860335454>()) {
                                                case 1822619661:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s8qnn3zghdgpk","QLoDxDsRj5oRnci6xRChn9d0ydIoCvyTWqvG+wnkMhU=",7255223812230361979,-1588616634522033602,7694289426952624097,3564827823688614894>()) {
                     case 1380157525:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private f a(String var1) {
      Iterator var2 = this.c.a(com.yiyiaddon.e.n.o.d.SPRINKLER).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"swvlnfwqjapji","KT9sAjHIsQcmGATzVBpb3P6PeWUCF1eqQqG5l/PsXg0=",9182736103134170260,7112695474436757010,204690655776712382,2401794338289629442>()) {
         case -725901968:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1t7i7i71bazoo","cRGzlO/dcDd1Vkf7Ue6I1PemKCtS84NWnJsz8yBjFok=",6068451331833439340,1040249388910660306,5527975796236820546,3047524992326831055>()) {
                  case 1035778196:
                     s var3 = (s)var2.next();
                     if (var3 instanceof f) {
                        switch ((int)com.yiyiaddon.m.b.a<"sgzyavtvifedt","lL9BZdQudOGpZTn0KlpVO9Wf/gBcHb1E9toE6tlOWT8=",651796833028751849,-8559040254957860081,5474702597924531213,-4971729063679419519>()) {
                           case 1335343491:
                              f var4 = (f)var3;
                              if (var1.equals(var4.dE())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3qc5aju8641ua","+EWXB+R6QL4NBIb4u0WISkdn80Cd916DoKAsRGg7+/8=",7236403175123463214,6349731352094043872,-5104708312941011983,-6194948302530313563>()) {
                                    case 634523913:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s1tw7ux4g3jrgn","XDQoEAmFOfNPJi2UtB+JiO56lKJZnDqXSsp4SFNtVzU=",6967280679064535110,-1064113467086815937,-6698162344950858333,-6613849504055177414>()) {
                        case -1125062789:
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

   private List<f> aN() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.c.bD.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3u4en6tmdvva4","cAjdfZKruEVFXSZzr06U5K8HpABL4oJnRqIWgbAXQwY=",2065583317946835780,3670257379691647969,-8501856842949037980,-2513451537475776061>()) {
         case -1081340799:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1d6c6i3uyezq5","YYlAAO6IMwzweSck6oADqvc9ecSVnRNHPgIeYmbK+Ng=",-7569362077824739001,3281894952087863631,2644506859233150821,1040910981592795497>()) {
                  case -1465064080:
                     String var3 = (String)var2.next();
                     s var5 = this.c.a(var3);
                     if (var5 instanceof f) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s1uv8rz2g5jnqm","eIvAODLmJQshd9jtYFZcw1nVMDm/RWNqSfZM1JrEkdo=",84775908980460143,-481587250900707272,-4623626086381707509,4602143795858022101>()) {
                           case -1977251880:
                              f var4 = (f)var5;
                              var1.add(var4);
                              switch ((int)com.yiyiaddon.m.b.a<"s1hrkmsbqwz9dg","NhGCOKomFPMj3SaMrMLnNfF+c9rbl+84Y1MEAvAp3gw=",-4856395149925553028,3556378083560905118,1957077861766852277,7112273714943817809>()) {
                                 case -1894309596:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2frfzour51n0w","cNMoYKeIXfZKs7xJghUY4JIARzDd1YVogYfHFOw5Sa8=",-4408606316152664789,337450831891651368,-4540949847808800942,9051672499358700083>()) {
                        case 1759447411:
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

   public List<String> aO() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.aN().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s31hnm60rwjhus","almZXnJ1vrN+Q5LmAp+lj/tkEmzx8iAaoBlShl3qgV0=",8739026051727508170,-6291426662775498194,-1232781309879279569,829852072721618995>()) {
         case 393139904:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3uhcc6dzsjyb7","iGtIwCxziDLXcF01e3uSGoRZwGwnG0pk6Ex/EbtUR7s=",7992606401966049177,-7598864416935075166,-467007912279800750,-3112282951254989358>()) {
                  case -952153538:
                     f var3 = (f)var2.next();
                     var1.add(var3.m());
                     switch ((int)com.yiyiaddon.m.b.a<"s3o80woy92wlpm","URwhLXbfsvmSxuKs5va+vTeoBkmu6UyKwiGXFXI0zRk=",-8464904362116592240,539864135618292155,8390383274916361001,-2854853496047705950>()) {
                        case -125034919:
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

   private f b(String var1) {
      Iterator var2 = this.aN().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1r1073k5czh0d","PhIac2qj2ISyCE4sbMZWXeDJ23KwftHrdpRfABjMsOU=",6266364293376835019,-3574592576762410983,-7993382538255963730,-6790442356310704346>()) {
         case 2110725748:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2trz6acw4nkfi","WhXFKK6TrkNDy0Boz4ylEBXGdtCoc6PWhZPN+vr++PA=",-6883208305899781184,5730655774245457398,1278712926472077018,-58940351139134673>()) {
                  case -1350904244:
                     f var3 = (f)var2.next();
                     if (var3.m() != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1zgyzxs1ezw7c","iSJT7a1W2rvEDocPEBNBmv5iCrLDiEYXVRJkCi9el9c=",-3473702830247910144,1471747880829109857,891183578930374616,4735465062384548351>()) {
                           case -2033808749:
                              if (var3.m().equals(var1)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s108z0dfwn80o2","Zm/Z0YO2YaWCA8jjzaEQcEfH67nfvTu+Hzd6pYmmxME=",-7716018118356873249,-4627418437153754467,-673103951358234136,6656021833771713399>()) {
                                    case 2031466530:
                                       return var3;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (var3.L().equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3p1x3sh458u6t","SchTSXbJFlEbTUw/4RsSdvZkaFYQESd2fzl00/VEu2o=",-562333286585314146,-814488377828005348,-93988677917653840,-6862319589332049531>()) {
                           case 1193242399:
                              return var3;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1lpy9so08kxct","ErN7E5mYMhzwg6E4tbPvyq8cC774Pw8gCiEDsB5b7Yg=",4244200405485318416,1862567084230264021,-2048303632972285568,1802614023143718873>()) {
                        case 2101340120:
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

   private String a(BlockPos var1, f var2) {
      if (this.Y.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s217gprmw0hfmj","dmZd+2u3CkJrSUi/QjJuCpr0AARyNNrpcy0SDHKdM2s=",-2985269216752098478,-5681349144279489568,5830675267129298394,-7664582510511559646>()) {
            case 156481205:
               if (this.Y.level.isLoaded(var1)) {
                  com.yiyiaddon.g.d.a var3 = com.yiyiaddon.i.e.a.b(this.Y.level.getBlockState(var1));
                  if (!var3.fc()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s358jo8hd3z6ie","q3SrBUo7JNJo5uEl7PrlPBLepOPHmExq4m9p7M0x9+o=",1959114295185482627,-3933002777708946154,1025340340676611140,7715512143597716049>()) {
                        case -1194019992:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     if (var2.dF() != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1bpq60lyn4hfr","i7lUhWGxGdJAI+fccMFX5KGpV5O4/uKUqGe588u2KUY=",7492471829743612436,-1994525240333248551,2399407265776840872,7062471527460387632>()) {
                           case -1888889411:
                              if (var2.dF().equals(var3.dv())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3me2v8y39w8su","zH54N1P8Ik4IDHruTv3ZPGhKLORDO2aA+8PqHJG/HWM=",9073210576373783258,6977902528771148927,-7162483074227476841,7105160554539842106>()) {
                                    case 344371678:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (var2.dJ() != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sith3tfwlmeei","+Ulule7cYXZjtJTF+3ipPgU9F94GD64o2UTu6TNOT04=",-3161847814519291165,-6453933020351169306,-1342195297241880812,-658376276920380222>()) {
                           case 1430147764:
                              if (var2.dJ().equals(var3.ea())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3fbemobn8t3fg","ZaSkjpQ9KBJd+oNgnAHPYvu9DdZ8hcLAR9FryPon96E=",-575300375817185456,-8288520823778953798,-2546233750350685002,7851310948187153437>()) {
                                    case 1835128903:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var10000;
                     if (var3.a() != null) {
                        label48:
                        switch ((int)com.yiyiaddon.m.b.a<"s10tp9lhc9dteo","FzYY+fkkNwfE1L94yBrnjPaPhzJ7uXHy9rpZ6E0BKpQ=",3901583226613640323,257617373076041017,-3368663995853997991,-5974305701637620662>()) {
                           case -1313979647:
                              var10000 = var3.a();
                              switch ((int)com.yiyiaddon.m.b.a<"sij0jz1eeavjp","UGGDt02MZ5vmcNVrQMAfDzYhg9fmvZN9NbuFfThcuUQ=",-7404475628634609622,1009716567344811301,-4415610500578567876,-284534361744157419>()) {
                                 case 312581054:
                                    break label48;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var3.dv() != null) {
                        label51:
                        switch ((int)com.yiyiaddon.m.b.a<"s2awj4z2xdggl","5PICHrwK1Xx1bkdVCVEA9Hr8z/G84o/sQUUt7dljy7A=",4909205768587642433,-6594989988623648719,-7776243635886660463,3038079034061363874>()) {
                           case -103450126:
                              var10000 = var3.dv();
                              switch ((int)com.yiyiaddon.m.b.a<"s1w0cjtg84nmzl","nT0ZGbmINIRDFfE9QEvQL2/Zp37Ckk9IjG4I6T5MJSc=",3449802383445803872,3225513433833479168,6964932305079265932,-321673058777781086>()) {
                                 case -1213185167:
                                    break label51;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = var3.ea();
                        switch ((int)com.yiyiaddon.m.b.a<"s3cy9p4hkqwvms","NwiyMvPb0ZmhVmPcqHnqdhulp2c6UmhvyxqMNRSLxNM=",5907078971336534712,-7578505806007802353,-7667496887460742337,4104254730045244976>()) {
                           case -549080196:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var4 = var10000;
                     return var4 + "";
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1svol8qvdexro","RQ3Z+R3mg3+Z+YPi6S6gg4qJwx/gPSxWDVGmE0Z+Wjo=",-8921114033959463752,-7413448225916764298,-6742535626231322660,1599946101849923656>()) {
                     case 1294855883:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public boolean cM() {
      if (!this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3py7pl7z8kbv8","JNF8X/onkRejBZUTYpLMz+L2p5NAgJgwKt9pCoyje30=",253847963107920314,8459610341565426248,2902116293287594528,3513516160654095386>()) {
            case -1467673190:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.o();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"stxtxsr9upqqa","4h9xZpBYbjbOoVuJTYa6nLVPeFZbAsCqt0XqKQzt27k=",2024090436180831526,-701868429117467328,-2434853375734327466,5936159918930708241>()) {
               case 1147699229:
                  this.k(d.SPRINKLER.D(), this.dt() + "");
                  return false;
               default:
                  throw null;
            }
         } else {
            c.a var2 = a(this.b, var1);
            if (var2 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s141srqshg7b46","+n6dpYAg/FEbguEKkoY4AIzKrSwvyxMubyYszxnoRPo=",-2722753975245277577,-8321537297808931519,-5952698771129279567,-7301554201068518703>()) {
                  case -651651108:
                     this.k(
                        d.SPRINKLER.D(),
                        (String)com.yiyiaddon.m.b.a<"s29hdu7fxetc46","p82U7dJb82PaJIZSQeXOgq6qZ2Jw6TCZo+K+g0m/E6zpbowDJsyZHBB2mn+bWFcT",-1021235708603960218,8718764699566631835,-114452191843148030,7332839971888426891>()
                     );
                     return false;
                  default:
                     throw null;
               }
            } else {
               this.b.au(com.yiyiaddon.e.n.a.bT());
               this.b.q(var1);
               this.b.V(com.yiyiaddon.e.n.a.bT());
               this.b.f();
               com.yiyiaddon.d.d var10000 = com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
                     (String)com.yiyiaddon.m.b.a<"sb6dpda9k2fe0","iR81qWku0gG2lR92fjguVVxeG8v+LfROUKPRr7prOPYq8G+V3Ma3h/8fEzQ=",7458680832369298198,-858179539178607650,-4592797181775295203,1105981363373412212>()
                  )
                  .a()
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s1m63u7mmeb7z5","1qipBZMlIGzfxjsJ3Y7KXKU+A5s5xvpOXxAfHabWohI=",-4796434496521277179,6346384628165048024,831722308225384870,1764985194774064668>(),
                     com.yiyiaddon.e.n.a.bU()
                  )
                  .a(var1.getX(), var1.getY(), var1.getZ());
               String var10001 = (String)com.yiyiaddon.m.b.a<"s34zl0adnez6zh","m9WYMWw3redgVU3jugR8+PSbNQbkdHHhdLOBEvKKhVI=",-4028269910816452258,-1897261469929973547,-5089037522587695253,-1836128753549609095>();
               String var10002;
               if (var2.dw() == null) {
                  label30:
                  switch ((int)com.yiyiaddon.m.b.a<"sz27qrml7ndnn","eoebca3e9cUf+5Xy4vpAldLzlD1fUdW8Ed/+9ql1KIs=",5883828915497627101,6142014816869660343,4267944450759760140,-1122058131716115168>()) {
                     case -431619713:
                        var10002 = (String)com.yiyiaddon.m.b.a<"s3fy3kvumwlg5p","bc895z7LhwYI8IbrqEVekLBYhcG/tkWRAQZ7c0bph4gbgg==",7136754961002750833,301876576224782575,-4284190496041634821,5602795079468483023>();
                        switch ((int)com.yiyiaddon.m.b.a<"s34h8b1m1ffd7","T0jEV8V8zs6AXdCXdwTjLrF/6Zf0m2i+Zt87BuTjYPk=",-4717041408023094445,-3618937884476006128,-816825401956126743,6673069216571988917>()) {
                           case -1409578776:
                              break label30;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = var2.dw();
                  switch ((int)com.yiyiaddon.m.b.a<"s2zq3g1vts4ehh","AaXofzDFT12wpTlQlB3DnCfyfMpsfG+te9RHPyESySQ=",-8851680366175061243,-7685050454673452367,-7107123980149696608,-6197090104068969752>()) {
                     case 1019416871:
                        break;
                     default:
                        throw null;
                  }
               }

               var10000.b(var10001, var10002)
                  .a(
                     com.yiyiaddon.d.d.a.SUCCESS,
                     (String)com.yiyiaddon.m.b.a<"sdrj8m2xiqizk","stp7gitD+4i3FvgI5Ts1k/9L3C5Zq24uLtQcDUIcddgEtw==",1259953256211695203,7914422256258996596,-5959070699726298934,-8543403138868369105>()
                  )
                  .g();
               return true;
            }
         }
      }
   }

   public boolean a(c.a var1) {
      if (this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3iu4vd4ij4coz","mZak2ht69VyapITtg3+uUQObw9QcTPEEwgFZf59SyQg=",7200458771327431470,5638023993544696807,-1214499122404631233,-4030345412567146779>()) {
            case -1367507798:
               if (var1 != null) {
                  this.b.au(com.yiyiaddon.e.n.a.bT());
                  this.b.b(var1);
                  this.b.V(com.yiyiaddon.e.n.a.bT());
                  this.b.f();
                  com.yiyiaddon.d.d var10000 = com.yiyiaddon.d.d.a(
                        (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
                        (String)com.yiyiaddon.m.b.a<"sb6dpda9k2fe0","iR81qWku0gG2lR92fjguVVxeG8v+LfROUKPRr7prOPYq8G+V3Ma3h/8fEzQ=",7458680832369298198,-858179539178607650,-4592797181775295203,1105981363373412212>()
                     )
                     .a()
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s1m63u7mmeb7z5","1qipBZMlIGzfxjsJ3Y7KXKU+A5s5xvpOXxAfHabWohI=",-4796434496521277179,6346384628165048024,831722308225384870,1764985194774064668>(),
                        var1.bU()
                     )
                     .a(var1.aj(), var1.ak(), var1.al());
                  String var10001 = (String)com.yiyiaddon.m.b.a<"s34zl0adnez6zh","m9WYMWw3redgVU3jugR8+PSbNQbkdHHhdLOBEvKKhVI=",-4028269910816452258,-1897261469929973547,-5089037522587695253,-1836128753549609095>();
                  String var10002;
                  if (var1.dw() == null) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"s1welc0n375995","eQEaKehoEYPNIi4ZygKuwEB7FYAlzrRZ/CgTBrfNMAc=",-324899263809822223,-3090343645927268219,-8945857461137405294,2106054403603017216>()) {
                        case -1328994468:
                           var10002 = (String)com.yiyiaddon.m.b.a<"s3fy3kvumwlg5p","bc895z7LhwYI8IbrqEVekLBYhcG/tkWRAQZ7c0bph4gbgg==",7136754961002750833,301876576224782575,-4284190496041634821,5602795079468483023>();
                           switch ((int)com.yiyiaddon.m.b.a<"s3iai05ctlw62y","VbfrXd2ELZNTMpVCNQqBagDFLZEMO5Mn0Wgjo5p/uGY=",9086429076441893977,7999696693617881541,9134503066001589562,1751003095635354678>()) {
                              case -1447274675:
                                 break label22;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10002 = var1.dw();
                     switch ((int)com.yiyiaddon.m.b.a<"s1vxaxua7mdsni","u+/GA9YpWnE8mnpC3VNZnQ3Ei9adRhRdzka/PGGZ3Pk=",-2671725125420618550,4882842947490616658,6049374222922511608,-2839400079088617906>()) {
                        case -491646978:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var10000.b(var10001, var10002)
                     .a(
                        com.yiyiaddon.d.d.a.SUCCESS,
                        (String)com.yiyiaddon.m.b.a<"sdrj8m2xiqizk","stp7gitD+4i3FvgI5Ts1k/9L3C5Zq24uLtQcDUIcddgEtw==",1259953256211695203,7914422256258996596,-5959070699726298934,-8543403138868369105>()
                     )
                     .g();
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3strk56pakjwy","DixOuoc/5fp+gA2UXqrmFhiyTwdZejG1pLhtMkxM6Ac=",6905344657551413695,4515346036646782277,-188594075321003660,31776601942738573>()) {
                     case -1831313782:
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

   public void gk() {
      if (!this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nbzab7023bzm","J1DutgC9EhbUloVIpxIChRwnLU17jeh76GI0Mm9NzZs=",-7525065317443636231,-5039233144239570372,-4952873745105711422,-5576255088916634617>()) {
            case -1725296366:
               return;
            default:
               throw null;
         }
      } else {
         this.b.au(com.yiyiaddon.e.n.a.bT());
         int var1 = this.b.a(d.SPRINKLER);
         this.b.c(d.SPRINKLER);
         this.b.V(com.yiyiaddon.e.n.a.bT());
         this.b.f();
         com.yiyiaddon.d.d.a(
               (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
               (String)com.yiyiaddon.m.b.a<"s1auxs6sr3hdlg","tqIUoFMaz977QCo7UBtEaVJSc56xpX5YicY7/l7UBigbo88apRu7c1lPcjqJ3Sd0",-2715399737688160223,3505381820204715410,-3108670185444784430,-8381469123059017437>()
            )
            .a()
            .b(
               (String)com.yiyiaddon.m.b.a<"s30bladrcg83vf","iGS1quPLvOP3rignzDfhJqyPSoREj4AarBOG5psyavI=",1551572527169892446,7759036149022389952,1113424321432015392,-4983311005644398627>(),
               var1 + ""
            )
            .a(
               com.yiyiaddon.d.d.a.SUCCESS,
               (String)com.yiyiaddon.m.b.a<"s3rk5onpzibi50","+v8KrO4J6CQFhRps8fK5o/fn/xJ+EYCpOwj0HXBoyadQgA==",5392881880148116035,-7103394418890399408,1264663521401938037,-4233859027351205340>()
            )
            .g();
      }
   }

   public boolean b(d var1) {
      if (!this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yfm0wu404xl2","kys2zxpcb8u22tyeykrzQc33RP1nw5TaGcJljF0TqBQ=",3830551387544749765,-4457252589226294195,-1823358104495568645,-636569614691288403>()) {
            case -2051232675:
               return false;
            default:
               throw null;
         }
      } else {
         this.b.au(com.yiyiaddon.e.n.a.bT());
         c.a var2 = this.b.a(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2sd9ezs1fqn4l","3Wt+D0xioBovLv27gGXlKjjJBgcbfdBpqL7puPC0pmU=",6656694571666666010,-8408508153646133392,4299845120505678106,-7147894842602733387>()) {
               case -1865259717:
                  this.k(
                     var1.D(),
                     (String)com.yiyiaddon.m.b.a<"s2rehmsfv5ii1m","vL/ZDGxFtREw4FjmO2LPTzDy+IJJ1qK+w8TPT7+3DtQZCD6Y/ZQ5KrXy",4590948659596034645,2528124694590008549,-8452406619715665142,1653491732067592210>()
                  );
                  return false;
               default:
                  throw null;
            }
         } else {
            this.b.c(var1);
            this.b.V(com.yiyiaddon.e.n.a.bT());
            this.b.f();
            com.yiyiaddon.d.d var10000 = com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
                  var1.D() + ""
               )
               .a()
               .b(
                  (String)com.yiyiaddon.m.b.a<"s1m63u7mmeb7z5","1qipBZMlIGzfxjsJ3Y7KXKU+A5s5xvpOXxAfHabWohI=",-4796434496521277179,6346384628165048024,831722308225384870,1764985194774064668>(),
                  var2.bU()
               )
               .a(var2.aj(), var2.ak(), var2.al());
            String var10001 = (String)com.yiyiaddon.m.b.a<"s34zl0adnez6zh","m9WYMWw3redgVU3jugR8+PSbNQbkdHHhdLOBEvKKhVI=",-4028269910816452258,-1897261469929973547,-5089037522587695253,-1836128753549609095>();
            String var10002;
            if (var2.dw() == null) {
               label25:
               switch ((int)com.yiyiaddon.m.b.a<"siln2jymm48gb","JdK3FEg4EnxlpXsWMJ++vpsjEar05dE2PoI9OSYrfQI=",5621651642795633558,-2738148813184608948,-8955588763317914462,5258230845733938294>()) {
                  case 1696005229:
                     var10002 = var1.D();
                     switch ((int)com.yiyiaddon.m.b.a<"s3ufgj2sx6sz05","BIUsLUX8e3k50UybRbXnkzNjDh0YTZU79vKb8Ut/P70=",-2679308285811089590,1441439197034527191,9170995695294458621,5350892595528082815>()) {
                        case 1783411451:
                           break label25;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10002 = var2.dw();
               switch ((int)com.yiyiaddon.m.b.a<"s2lngj5eld0b4e","2zwGbdlJubEhzg6PXAU7jfmTz9rYbc6xn8lzNm60sP8=",-3545638001058205578,-2780023023417335036,-3361701696467525226,-3315373897956812315>()) {
                  case -704506291:
                     break;
                  default:
                     throw null;
               }
            }

            var10000.b(var10001, var10002)
               .a(
                  com.yiyiaddon.d.d.a.SUCCESS,
                  (String)com.yiyiaddon.m.b.a<"sdrj8m2xiqizk","stp7gitD+4i3FvgI5Ts1k/9L3C5Zq24uLtQcDUIcddgEtw==",1259953256211695203,7914422256258996596,-5959070699726298934,-8543403138868369105>()
               )
               .g();
            return true;
         }
      }
   }

   public void P() {
      if (!this.db()) {
         switch ((int)com.yiyiaddon.m.b.a<"s33i4p2hxrsr7p","3wSJq7zA5irRleeVM/MmUB5ykCRkJSRKH8L0Ud+k/oM=",8791767589069905168,-5872522907651459723,517441539379673139,6519686152354130405>()) {
            case 96942816:
               return;
            default:
               throw null;
         }
      } else {
         this.b.au(com.yiyiaddon.e.n.a.bT());
         int var1 = 0;
         d[] var2 = d.values();
         int var3 = var2.length;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1ptfwdk1tg571","A7Lbcf0JTG+44xHfpq7MYz+ik8XZmmRSB5/jU4czoUM=",1122217833875430392,-1187492064011902540,-5302655632767231918,-5248340401952283616>()) {
            case -925825245:
               while (var4 < var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2z72dxrwgxvvp","VIiLg56KPElla6Ee8ECGxcsn83w8zHMB49bROIR8tQU=",-2323034609808043565,6938323024908048063,2395601527323118699,1496367687519601029>()) {
                     case -594560604:
                        d var5 = var2[var4];
                        var1 += this.b.a(var5);
                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2gkz4lxp141vv","WXHGxc35vTEC07TClt/4tC4yAHhgCSb5HXBqpzujd+o=",-4706171689240394415,8584240584811584247,592780364713597660,3926413469645219167>()) {
                           case 738991009:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.b.t();
               this.b.V(com.yiyiaddon.e.n.a.bT());
               this.b.f();
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
                     (String)com.yiyiaddon.m.b.a<"sog5nnr097kfh","3iDFMRur6BF+OYWiBvvqiWAzN4YAgpfcQ89FNlkc3DezZwsnsqg3m6MA",5318158767565333612,7925167622605009675,4002932833442689094,-541341139861037452>()
                  )
                  .a()
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s30bladrcg83vf","iGS1quPLvOP3rignzDfhJqyPSoREj4AarBOG5psyavI=",1551572527169892446,7759036149022389952,1113424321432015392,-4983311005644398627>(),
                     var1 + ""
                  )
                  .a(
                     com.yiyiaddon.d.d.a.SUCCESS,
                     (String)com.yiyiaddon.m.b.a<"s3rk5onpzibi50","+v8KrO4J6CQFhRps8fK5o/fn/xJ+EYCpOwj0HXBoyadQgA==",5392881880148116035,-7103394418890399408,1264663521401938037,-4233859027351205340>()
                  )
                  .g();
               return;
            default:
               throw null;
         }
      }
   }

   private boolean db() {
      if (com.yiyiaddon.i.c.fp()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2p6yvmsr3jyxf","/EljT/izduG7N0DhRBEtVpJwsHJKl7DBAGS5zG/dyVQ=",-6982831352894451162,-610983905622162928,3889796580841610101,6965202149723950000>()) {
            case -989262550:
               if (com.yiyiaddon.k.e.c.fr()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3w3gzp9owq0d3","S5ogai1yWATId0r2IYSkRuJS80E8xk/GZTENbH/0hnE=",-4514229524282266419,-825064876026659304,3324529268360641950,3262924723868670490>()) {
                     case 338787905:
                        this.b.au(com.yiyiaddon.e.n.a.bT());
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

      this.k(
         (String)com.yiyiaddon.m.b.a<"s1y8b7a48voin","jWinH4J1BEWWi3KPmCNsnBgFYoqTY9UT/mVXB8jAGFc=",-1837901146715712942,-8673052215977086077,5787081160392542011,-9101879866981888361>(),
         (String)com.yiyiaddon.m.b.a<"sewgh0a4jqhh2","A6TvAqfmHahpEe9nWftoDh9QZ2FTDNCS740U/ZkP5XKjlIL0RlLSvacBkfyVa1Cgz2hCPhTzRvE=",4763222647243899396,175582283258616270,-2211840036570296342,3134969924751305574>()
      );
      return false;
   }

   private BlockPos b() {
      if (this.Y.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbedzs7jtmvge","LptqdtgIlEJYyPquP+TBjqdYIjJKsPco6pAfctW+VnI=",7022525908297001593,-2654781178266621854,5767852329345688101,2703753526527900428>()) {
            case 925494373:
               if (this.Y.level != null) {
                  HitResult var1 = this.Y.hitResult;
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1lttintc65w46","36cCaVeljCOrWr1YTspMjaT+qJyH+oA7mgZFPEOnoH0=",-5334399069607564519,-2216562741708325562,316408053694763203,6364646462721352282>()) {
                        case -133493956:
                           if (var1.getType() == Type.BLOCK) {
                              if (var1 instanceof BlockHitResult) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2euv8wbrmi9tp","Mnf458ZpUjacTbqz53ornYD8OW2ZSF6Aq6Cu2zrfNow=",-3166662990945712360,2133300023797365231,8976219658226367940,7074513594759747745>()) {
                                    case 1512788208:
                                       BlockHitResult var2 = (BlockHitResult)var1;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3hrv10lqntwbz","4zZ6csnnSqXBmGRcIUxeV9jMrX3qJsAc49pLomd0Xoc=",-2286652905594030452,4085122134526607313,-8581562977190571849,-981723963721430401>()) {
                                          case 169867554:
                                             return var2.getBlockPos().immutable();
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return null;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3de63frdya5n7","vZVWnBRIc74nTwuhxtzrWzIyZgYqBXpOOtK+rWXq6Jg=",-2973725103851819714,3980589225630939254,-3889134502952655546,4924047512433624292>()) {
                              case 455774623:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1x57g5vc11zmn","KvA61Id1cC0X/ZRVcg0pk3CcA0eMOcVznI03G85Yg3c=",1268337344338748739,-5840172679045306086,-4790220681981603593,5780463877485609132>()) {
                     case -397999924:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private BlockPos o() {
      if (this.Y.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s335rq5nxybltr","Q/r3pLiMeXwrsx8JqE4CVLm5pDZg1kZrTQ6/vKFw3pc=",8393382016055905385,-3091840179815717608,8229252124949610700,5350751874981509050>()) {
            case -529563927:
               if (this.Y.level != null) {
                  HitResult var1 = this.Y.hitResult;
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1tofaqo172qyl","pOxtdrBa/TTeB2UPdMG+v6r8mtXOgLvchdOpCyUJ+vc=",2351290133595905243,6286262261057468568,1880035539337838577,9188673895524238403>()) {
                        case 1223486911:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     if (var1.getType() == Type.BLOCK) {
                        switch ((int)com.yiyiaddon.m.b.a<"s8qu36bypszbh","TASCDau0A2RXAtsn5Ofbl3SPpHdQehXZ+NGVGKhsSIQ=",-286391575183714999,-6195963908873044320,383896066303734762,-1688632305239655392>()) {
                           case 621811086:
                              if (var1 instanceof BlockHitResult) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s35ooabetme3ch","A0QCqTAHHP+2MdXCfzWrPPEZcEkcLzD/lfkYS8epLDQ=",2606746110522124137,8114036557708187956,5152711283240173783,-6618956993889576353>()) {
                                    case 118637810:
                                       BlockHitResult var3 = (BlockHitResult)var1;
                                       return var3.getBlockPos().immutable();
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (var1.getType() == Type.ENTITY) {
                        switch ((int)com.yiyiaddon.m.b.a<"srj512japkaz3","3qhbMA57ZUe/tLntAjmUXnqdddeEppKuEtQKj1G1KE4=",-4008419566505906663,-2222434349352054984,-917128999647658078,-9216676990631710168>()) {
                           case 2075684889:
                              if (var1 instanceof EntityHitResult) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3omxunjfwruij","vYg2Bf5NPno/LyB7wevO/PxAwbBVkV84q2p/xv7dLsQ=",5601713282754797894,4679733946823177517,1654302965617350121,-4813200508387031539>()) {
                                    case 1001930782:
                                       EntityHitResult var2 = (EntityHitResult)var1;
                                       if (var2.getEntity() != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2a4r3zwuq3zoo","Tmq4ZA5qkf3x/IhnTj5YlP9J7lu0TlMydtMcvYzLgXc=",4040664364048058210,-3161864890378291166,-259451600464968573,-3815423480358402364>()) {
                                             case 1913464448:
                                                if (var2.getEntity() instanceof LivingEntity) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s31ozvk174w5dj","1Y2v0DHY919UX73DbxqO8qehtsvWaTTeT5E/6v1Xm+Y=",-6001973965774204363,-5720872559263497773,6733545096529055117,-699357389330725335>()) {
                                                      case -1942594116:
                                                         return null;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                return var2.getEntity().blockPosition().immutable();
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

                     return null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"smgrw1jiknr6x","PNhVJ437jzsuU/fxlD1xl4Z27REEuyUN+ewVQrGn+PM=",-97343481570286111,6884098463159628073,7055307259556815089,5993016573348121871>()) {
                     case -856794549:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private String dt() {
      HitResult var1 = this.Y.hitResult;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"symerx8615jya","lfUJ0i0JzC0b8QsHrXIWf9DEiny0f3HZOz1kxMnjYTU=",6903986595019321404,-4961942534233844798,-8175570666646256908,2048562795560824838>()) {
            case -2083720416:
               if (var1.getType() != Type.MISS) {
                  if (var1 instanceof BlockHitResult) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2fo4nak87h17v","Y0P8Soz3DSsCHAoS8mA5zPdWrnk8Vff6EDSvfmSsCB0=",-6781387283837517147,-6769201449221412851,6476961223844232860,8019672301450394091>()) {
                        case -48399763:
                           BlockHitResult var4 = (BlockHitResult)var1;
                           DefaultedRegistry var6 = BuiltInRegistries.BLOCK;
                           Block var10001;
                           if (this.Y.level == null) {
                              label54:
                              switch ((int)com.yiyiaddon.m.b.a<"s1pwwqdvxb3ibe","g/Qp39ainqz1Lz+ZGSaIDR05MrC2P5MOKIU4h0X2H7g=",-6773410310461679026,-5257917382895694947,-8499722786609587073,2200228673793941752>()) {
                                 case 1796817531:
                                    var10001 = Blocks.AIR;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1q2vab0esqffv","lsLccvxw/O4hSCdhXl+conM7a2Q5WYJznMPlnwOJYOM=",-6460340186299753442,4377263788228074458,-2699087718185272207,4002673038358000753>()) {
                                       case -675949329:
                                          break label54;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10001 = this.Y.level.getBlockState(var4.getBlockPos()).getBlock();
                              switch ((int)com.yiyiaddon.m.b.a<"s4kpxxk9bxty5","O4IR4ctonmUDtZCCKrpvX9LckfL3KAx6VQfdnBhJ8O4=",7662294474889059319,8743422957338766963,-2266791405892156120,-5488882381938555150>()) {
                                 case 14036427:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           Comparable var5 = var6.getKey(var10001);
                           if (var5 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3hk405kvo82ae","+hzg5HMREHqlxUgEXkIiVIBosAIulFMwNR7glMrWoCo=",-5219878862701337686,3836217911997499467,-7274805805870746411,-5228858165188330698>()) {
                                 case 1636804942:
                                    Comparable var7 = (String)com.yiyiaddon.m.b.a<"ssy2vgs0bz863","JoyaDhtTBQnHaJn7NLCGIp65BS1Pk/TjZ4yV/Gf29eE=",595764008048151747,214843688309901247,-7030139098009927485,-2906919540121416461>();
                                    switch ((int)com.yiyiaddon.m.b.a<"snqfka4grg3n9","V6Zr64m9xGfYnsG5qRtbR31nhf/khYk/ZkcJi8umRGU=",4049996551569482082,9037967807827976995,6986449151845145994,6531570464507483443>()) {
                                       case -1729942525:
                                          return var7 + "";
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s1p7sepgfsyo4y","wk5nJLARsC02o0SRJkGo/ddWbeU4NO6pC/VlxC//GGQ=",4426742079876633925,969732949667000178,-1448714018854344843,3407051080885020404>()) {
                                 case 1113658919:
                                    return var5 + "";
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     if (var1 instanceof EntityHitResult) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3d8k3skg1a98g","3S4OmbSY/oBxdjwg286hrI/scazTBLg48S3d+zmq1Ok=",-1307794765401785893,-2498578637658674572,632080797365102931,967795936572377835>()) {
                           case 2034102707:
                              EntityHitResult var2 = (EntityHitResult)var1;
                              if (var2.getEntity() != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2m0j0rfzqcvyh","VRYIVULRfDV2dTbCkpsRS5qXkFc8CMNkwNMwVH5RtT4=",-2943023629452797132,4093163032986918762,-9118714436968105178,-8575434852573058878>()) {
                                    case -1110653014:
                                       Comparable var3 = BuiltInRegistries.ENTITY_TYPE.getKey(var2.getEntity().getType());
                                       if (var3 == null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2pzhhxn3k6cbb","TK89EkVGH+l8Q3/yQwyiDYhL24ixDCUz1l4sc91A93M=",605713174822786839,-6835737149201016867,748290346412388454,6477588427837304044>()) {
                                             case -279481478:
                                                Comparable var10000 = (String)com.yiyiaddon.m.b.a<"ssy2vgs0bz863","JoyaDhtTBQnHaJn7NLCGIp65BS1Pk/TjZ4yV/Gf29eE=",595764008048151747,214843688309901247,-7030139098009927485,-2906919540121416461>();
                                                switch ((int)com.yiyiaddon.m.b.a<"sppu0my4wx9og","iBGhnj3w4aRsklXVL8+x1r7Eg1wJOjHGFwEddwMWLP8=",326795439273553969,8824822721245704518,8852329369073221461,-2544165931906367470>()) {
                                                   case -279783987:
                                                      return var10000 + var2.getEntity().blockPosition().toShortString();
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3r899xu39yuol","attSFCsoW5sldlZvEp6fRJ/KugarECySLI7YmMKJSDU=",4278288521175088211,360917946645481216,-2283227519775676737,8659609743302306675>()) {
                                             case 1685081972:
                                                return var3 + var2.getEntity().blockPosition().toShortString();
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

                     return (String)com.yiyiaddon.m.b.a<"ssy2vgs0bz863","JoyaDhtTBQnHaJn7NLCGIp65BS1Pk/TjZ4yV/Gf29eE=",595764008048151747,214843688309901247,-7030139098009927485,-2906919540121416461>();
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3alqzs4qfdw49","F0uoCwCR9TJPOraP57Ld1kGvBIRmwmu6CIL0faMpT7E=",8698351778660208806,-9204676209318856860,3874645570492701709,81236159131132667>()) {
                     case -900854485:
                        return (String)com.yiyiaddon.m.b.a<"s2l6b9dye5d7h3","jlljbHtYu/wUo+MXucPRunm6gVAq65G9hEplDr6B",6765935365492305640,-8295276974136362571,1514178124126005482,4923027308005939752>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2l6b9dye5d7h3","jlljbHtYu/wUo+MXucPRunm6gVAq65G9hEplDr6B",6765935365492305640,-8295276974136362571,1514178124126005482,4923027308005939752>();
      }
   }

   private BlockPos p() {
      if (this.Y.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"scyfetc8ahp4h","k9nYhulJB2fVgLCGMX8tvm7q9WCnB5ixhhxVqXN8QF8=",7411462454880329829,-3752601385942571666,5105059246580345041,-4448684031398672629>()) {
            case -197599502:
               if (this.Y.level != null) {
                  HitResult var1 = this.Y.player.pick(this.c.lA, 1.0F, true);
                  if (var1 instanceof BlockHitResult) {
                     switch ((int)com.yiyiaddon.m.b.a<"s31m7i5vevrtsl","Z3pkDlkbEGmpvUEcss8Uj4MCMNL3iFYsQq1cZuj9yIQ=",-5767395429251391062,3557664197792085543,-3759012897615257058,9216887066342800160>()) {
                        case -1585119329:
                           BlockHitResult var2 = (BlockHitResult)var1;
                           if (var1.getType() == Type.BLOCK) {
                              return var2.getBlockPos().immutable();
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s18ma6u9e6ncsv","jK6y4WqzKlymnSx7Lil8i/3YEmxiGzyCCUmW+kLJteM=",-4191919812278599433,-5975161734749493493,6255154400984460036,2996486777593245164>()) {
                              case -795450691:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bbpbkn8d4tas","Fzch6rAqHFyunjtZc8UyqO4oMTAIdP3HuUm6FtobtEc=",876042351365864567,9079288671587977363,-8488580030562551049,-5040626949890473702>()) {
                     case -955445028:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public t a() {
      t var1 = null;
      Iterator var2 = this.c.bC.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2sjn8bxohtcua","gHblzT64GM/kYVpDtCCIfqR780+YbNOf6ut/dvt4XpY=",4025294382107858868,-1502362901202874048,5661167356265588412,-3287549344311680767>()) {
         case -41873382:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s36dj1ap97sipr","7nJXRyjbCfkx345Gc57D2Y+PzAMgsLTp3pEEWA8PohE=",-6896707813212661454,-7637395556805179110,-4121066028209704604,-3300189846888886862>()) {
                  case -1243950660:
                     String var3 = (String)var2.next();
                     s var4 = this.c.a(var3);
                     if (var4 instanceof t) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3fbea3zxmy1jk","doHGDj692GBF6/CBvJHfQq2EERWxXmWI3yuD0vDgp/Q=",9216628648001865553,-4984675193392718011,-6320392475588677404,5143278873751215518>()) {
                           case 359193693:
                              t var5 = (t)var4;
                              if (this.b.a(var5, false) < 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s8rml6iewbdpe","B6ZA9I3ActXxcndpVw9b4+AAxNw1bXwO6XDmsyU99z4=",8684798653397291067,4757181665996706309,-4381857906451429490,-7517456324734090818>()) {
                                    case -1544040412:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1pcxchvpr5u19","udeFDOghBzj6Bg3pgTwZmrWss7rfHWfU6nXkCMS1Awo=",-4008376359115123943,-4013665219142894453,3843368741500447475,5482672197955737636>()) {
                                          case -459189189:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 label57: {
                                    if (var1 != null) {
                                       label38:
                                       switch ((int)com.yiyiaddon.m.b.a<"s29qawizpre0jp","CwhYgfnb32P6T2XXY67MEhq+U5moIW2D6H/Q1HMLYRE=",-5566617139000962716,-8708182524558194964,-1628122138744526003,7829220036484252170>()) {
                                          case -670238001:
                                             if (var5.cf() <= var1.cf()) {
                                                break label57;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1adxigdeufhoc","X/QI0CF+iSRFt+Md1u3EqcfV/Kjx77eXTKcvCw856K4=",-7539531930977686892,4646013127596373372,1453260598256696943,7283193749526533749>()) {
                                                case -1293332948:
                                                   break label38;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var1 = var5;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1xr6z0bpmlnv9","zGc9xEk0mUFW1zgMcC/5oKqtykGaTLYuWoWUD1eYXLs=",7642689695164618741,-840504598680250757,4712804556847599501,42171291575258034>()) {
                                       case 321723384:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3eptv6mx90o3y","Mux/44It/VpuED/87sLiS2Ve6Wa5HBB9oLBDs3KA2cs=",-4149059741797197627,-6587140394510758558,4941224962397537198,1259480290187931658>()) {
                                    case 182413357:
                                       continue;
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

            return var1;
         default:
            throw null;
      }
   }

   private String f(BlockPos var1) {
      return com.yiyiaddon.e.n.h.c.f(var1);
   }

   private f c(BlockPos var1) {
      return com.yiyiaddon.e.n.h.c.a(var1, this.c);
   }

   private static String aD(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqp0q2es6frdt","SpGrkWGg0Qyh3tcKy5qgJdh460HQjY56DKKM2N7gl5k=",-5596681896986349918,-8154690826213413390,-7484218613752779548,-2767105204006497231>()) {
            case 1822126780:
               if (!var0.isBlank()) {
                  int var1 = var0.lastIndexOf(47);
                  String var10000;
                  if (var1 >= 0) {
                     label36:
                     switch ((int)com.yiyiaddon.m.b.a<"s2s139jg8hsku9","t5m3PflpADZ+usE/Tp64HWktufvWMsOvjS9hbuDo6Wg=",1379057154198536133,-8475967398229639795,-6289804577296370071,-2993780492063592965>()) {
                        case 2002568750:
                           var10000 = var0.substring(var1 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s3f55h6647s8i3","sFcKYAXBa7eq9CalrqmKi3QFHMmsGp5+723zWTItB0s=",5393117574482036758,-549142665867851674,-1277375954142297765,6444901323682640773>()) {
                              case 648257819:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var0;
                     switch ((int)com.yiyiaddon.m.b.a<"s1zr6i9ozqf1f2","1mhGWZBhMoCv1tlkJrJKa4VkeE+RuAHKqW4s2llF9t4=",-4109516304398587609,-6917312859223276438,6295062620748835236,8113318465595590782>()) {
                        case -91590186:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var2 = var10000;
                  int var3 = var2.lastIndexOf(58);
                  if (var3 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"sbfdafsqk182l","oUutb594e94jyaQfTA9yqerqsYISaSu9OyAAPBWw6SE=",-1292640800980545197,8624987110021406859,7384548772372449501,2012745059155277789>()) {
                        case 1556527197:
                           var10000 = var2.substring(var3 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s1t9e3svtdhtba","Yp72ORF+m59meBZLzyGYEtkziP/HIRioC1hwup6qgTo=",-4697400556246730698,-3416150552430004003,1900249341623316671,7485284983130734412>()) {
                              case -1435576088:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pjjt41ps3qtv","sWi32tSCi4xYwmZdjR66ldeugLTUlT9ayO1uw1rqWCo=",-6379259577957006704,-6271003602818539710,5682404719636291699,-975530718082599481>()) {
                        case 1739777474:
                           return var2;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3sknljgsa03t9","PDumcpglUfMBG3MiYts2lMLBmGkQ4/K91DulHsYwCYI=",3845174857064930785,6614519416345387424,-999503089053029985,1621599875382324945>()) {
                     case 141360026:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static c.a a(c var0, BlockPos var1) {
      Iterator var2 = var0.b(d.SPRINKLER).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s13fijb8nridwz","B9S7o16CuzLaFb0l36QWBJXbBn6xuZ6m1hecGIdkMLE=",4558327535167388349,5393577412270586047,6725427930850251604,5070548505995498315>()) {
         case 156723271:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s32xq4zwlpbgkd","gEYm9cqQIx8FU7bNen93uwJ9QuqnlBgKVK+y1ziNQJQ=",4210613573791691815,-6165064446003850117,-1793784588891237205,-7990103395476218998>()) {
                  case 1405716847:
                     c.a var3 = (c.a)var2.next();
                     if (var3.G()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3qln8m97kz7n8","gVDoIKaZMUeisd0cpOU8/2pZhRzew+d8cuCGTIEflfw=",-2835943481227887119,-4457424159902607014,2089775213769971700,-5066980328170198868>()) {
                           case 1569496270:
                              if (var3.a().equals(var1)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sbpu0rrds8zcg","FBO995KqZKQMUw11BfOmZPbEsqVtqNyfL6b5rSnYgwg=",6265634626795495001,-6807246774040722199,-5027229394978723189,2324007750804347311>()) {
                                    case 1082321886:
                                       return var3;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1xwh9g1picxr9","Dp6uWMxyOYYau2paNOdlaPkFpGXYXHHrE/m/Rd02n4A=",6639956799902422583,7817310002328219497,5370143421177605692,8982096688508873411>()) {
                        case -1877315475:
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

   private void a(d var1, c.a var2) {
      this.b.au(com.yiyiaddon.e.n.a.bT());
      if (var1 == d.SPRINKLER) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"sr2wsdkqrjrdw","8MsGF5LEqdY7lYVMdBFck2qObj4FC8l6cZWG6iAJVZI=",2859537850493124631,55185257142152087,-3484491142700534258,4001939360503218769>()) {
            case -1041185355:
               this.b.a(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s2jkq9hbo8sswd","v3RXwyfulKpicTJX4OejhfAWGckW23PcRTbADUvdMJg=",981037587257337204,-4724558228742010979,-7764662240197879216,-5913218455895546629>()) {
                  case -1063418718:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.b.b(var1, var2);
         switch ((int)com.yiyiaddon.m.b.a<"s283ftzy1f4491","zZBaQcReK119Cw1/18Foy0o4DMmuB72HXNmncHi1dRA=",4157984492683457400,6843480270376503817,4069528691451313084,4720538643135683696>()) {
            case 1407703789:
               break;
            default:
               throw null;
         }
      }

      this.b.V(com.yiyiaddon.e.n.a.bT());
      this.b.f();
   }

   private void k(String var1, String var2) {
      com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
            var1 + ""
         )
         .a()
         .b(
            (String)com.yiyiaddon.m.b.a<"s1m63u7mmeb7z5","1qipBZMlIGzfxjsJ3Y7KXKU+A5s5xvpOXxAfHabWohI=",-4796434496521277179,6346384628165048024,831722308225384870,1764985194774064668>(),
            com.yiyiaddon.e.n.a.bU()
         )
         .b(
            (String)com.yiyiaddon.m.b.a<"s3f4aetzc8ayec","VJs9BVoJwIUy7+tYyXX1LB8XDrOxEYocbctz8hTWavI=",9179180799164348450,-3592025706204810377,-2800841098982917406,6695858664839671053>(),
            var2
         )
         .a(
            com.yiyiaddon.d.d.a.FAILURE,
            (String)com.yiyiaddon.m.b.a<"s3n95sk2hx624w","GXOWiWuDnwgw24ndvUI/HZWaGLsNoz0SXruv4vQsxQ7xBQ==",9157821713152293997,3435900314397375412,-8187243051438268254,6988458776707654121>()
         )
         .g();
   }

   private void a(String var1, BlockPos var2, String var3, String var4, String var5) {
      com.yiyiaddon.d.d var6 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sj1it8tia9j20","kRb3LAG922IZ9vJoANJ+ct4lU1TeCxH0qGDeLYGVoQr9waM+cwg=",-1636478135534022061,7173123077078661042,4788932230194228714,1819509718835052172>(),
            var1 + ""
         )
         .a()
         .b(
            (String)com.yiyiaddon.m.b.a<"s1m63u7mmeb7z5","1qipBZMlIGzfxjsJ3Y7KXKU+A5s5xvpOXxAfHabWohI=",-4796434496521277179,6346384628165048024,831722308225384870,1764985194774064668>(),
            com.yiyiaddon.e.n.a.bU()
         )
         .a(var2.getX(), var2.getY(), var2.getZ());
      if (var3 != null) {
         label41:
         switch ((int)com.yiyiaddon.m.b.a<"s1oix7vzyx2erj","xQCCX7a4+E3FSB22FHvZ8692GkjqIutkn4J1G206R8E=",3801385461442335073,9140320181621921344,-3299551280918210105,-362310045185502742>()) {
            case 338111412:
               var6.b(
                  (String)com.yiyiaddon.m.b.a<"s22ly9haryusd4","ihboLyPEocaqkiOHNwoFYUwPAG/1kTpSi8xQQTNkdPM=",-936224494097801371,-8192760493943905787,-2159062319144935041,-413584376575965634>(),
                  var3
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3n2ajvwwrrn53","PAgSUzd75UILgCiiHFrbJluiwIhz1ozCmuymvP72A2A=",-4847066430807836365,774128305089234317,-4813746739157126303,151311885087008508>()) {
                  case -1922855397:
                     break label41;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10001 = (String)com.yiyiaddon.m.b.a<"s34zl0adnez6zh","m9WYMWw3redgVU3jugR8+PSbNQbkdHHhdLOBEvKKhVI=",-4028269910816452258,-1897261469929973547,-5089037522587695253,-1836128753549609095>();
      String var10002;
      if (var4 == null) {
         label34:
         switch ((int)com.yiyiaddon.m.b.a<"s1gykg274rf7b3","xU7+oe/AcrwQpy3LzPEZ5EgNdMBof+IcNRmg/jSNmiY=",-2768935901837620210,4784467713706843636,4517331003772838385,-6061693193590116761>()) {
            case 424758965:
               var10002 = var1;
               switch ((int)com.yiyiaddon.m.b.a<"siatmk4yafrzt","QwXbQHhBJw6AUtCAAERvcH3Y4/dnv5tai07fhHonZw4=",-1979118517340409341,-5225233059491369146,-1527474900034886245,-753243307065926357>()) {
                  case 653207405:
                     break label34;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var4;
         switch ((int)com.yiyiaddon.m.b.a<"s3otku2bcy2fhx","cKBICqfnkSRHvCXNXXq5RxYthS6UTUN1OpMGa7dESnY=",5866071714098132120,-6196237388747872032,-4771857529091095195,3167514650975739685>()) {
            case -1873480636:
               break;
            default:
               throw null;
         }
      }

      var6.b(var10001, var10002);
      com.yiyiaddon.d.d.a var7 = com.yiyiaddon.d.d.a.SUCCESS;
      if (var5 == null) {
         label27:
         switch ((int)com.yiyiaddon.m.b.a<"sncs198fojvso","wJzHqO9SB/Bt5Cz/w6nXqDDzi5zSV39ZmBwo7Zmbi60=",-1279962106180720104,-723657277654222284,-7844931457357525089,-5655427681219865609>()) {
            case -1327756547:
               var10002 = (String)com.yiyiaddon.m.b.a<"s2956mszi68r84","r7WDtuiQCdO4Hf8WICrIZ8CHmuFTGwvYp8lbLoJciws=",-7832808576503849576,-8634526406961776303,64115289046436527,1830705432293395764>();
               switch ((int)com.yiyiaddon.m.b.a<"s16bagk7bcy210","eI8AC5K4PfOikH5uyKiPikjI/L57YhLmCP8KBvVII9A=",2347285636668724889,-5643123712824649223,7047992318894083636,-8006167040039283538>()) {
                  case -2045320986:
                     break label27;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = (String)com.yiyiaddon.m.b.a<"s2zyigzpjwzsde","8HRdh7ECq6qVS0Kvus5/cncHTX35Es79Lsi5xfLJKu1R2w==",-2315155167776500346,-7301831923505750154,3859513497858524795,4370446226798587034>();
         switch ((int)com.yiyiaddon.m.b.a<"s1nn4kr5wiooc6","aUFM23z3b/866qimhdMEQ9Kb0QKKyvPrOGjL3ejWank=",4917549787051039522,-1082871782749296096,-3830478762178306157,-2408234303181872769>()) {
            case -996754433:
               break;
            default:
               throw null;
         }
      }

      var6.a(var7, var10002).g();
   }

   public record a(BlockPos E, f a) {
      public BlockPos a() {
         return this.E;
      }
   }
}
