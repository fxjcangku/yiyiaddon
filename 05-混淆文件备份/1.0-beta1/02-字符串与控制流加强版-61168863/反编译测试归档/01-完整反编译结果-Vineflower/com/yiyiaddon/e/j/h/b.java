package com.yiyiaddon.e.j.h;

import com.yiyiaddon.e.j.e.c;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class b {
   private static final double Y = 128.0;
   private static final float bk = 3.0F;
   private static final double Z = 1.5;
   private static final int ji = 10;
   private static final d r = new d(16724480, 55);
   private static final d s = new d(3597055, 245);
   private static final float bl = 4.5F;
   private final Minecraft L = Minecraft.getInstance();
   private final com.yiyiaddon.e.j.a f;
   private int jj = Integer.MIN_VALUE;
   private Set<BlockPos> E = Set.of();

   public b(com.yiyiaddon.e.j.a var1) {
      this.f = var1;
   }

   public void render(f var1) {
      if (!e.a().a(e.a.MINING)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sgc1m5vvmyb8","n+bNDBpVNJceZk4qvHgtdfh0JBfuqJzYrvAIkAcdFj0=",-2969521780947500722,-3045355047541780286,-2238121534389944121,4142475802595030451>()) {
            case 71795264:
               return;
            default:
               throw null;
         }
      } else if (this.L.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1psj1tigrpyts","GfPcfvnc6V0Dpi37Ab0MntekKWfO29BZbTlATZXpSc8=",-2966690583351005273,-4346464542006161353,-6230122907569876347,691478579143802075>()) {
            case 1456390043:
               if (this.L.level != null) {
                  this.a(
                     var1,
                     com.yiyiaddon.e.j.e.d.MINERAL,
                     (String)com.yiyiaddon.m.b.a<"s136its2vo4el9","9aaQGdaM5fneSFq111HKRTWma4Cx2vFVWgx+/Bxmlste0v5USqB/a8Jm",3662435643040716955,-2125301973253069164,2174892330800213372,-8594028781580108502>(),
                     this.f.f()
                  );
                  this.a(
                     var1,
                     com.yiyiaddon.e.j.e.d.FOOD,
                     (String)com.yiyiaddon.m.b.a<"s32oscoa78qnj8","7CHUiSVvk5OIAyfF/cSG9PLTs3qAggAeMkU0TxNLkhoZ3Jul6kx/LZ2w",-1813274361412347819,2934479531413533674,-8310549650422555604,1555747381265155545>(),
                     this.f.g()
                  );
                  this.a(
                     var1,
                     com.yiyiaddon.e.j.e.d.AFK,
                     (String)com.yiyiaddon.m.b.a<"s2awni6r964dfh","QUDQabg0czO6Lj0T/N+boew/fVAMZNLpGLt7kecVLqcHVHQ87gMaRTJTdACyPg==",-8444266255799047929,8194921731775893288,-2065241390658483405,-4594879298201583567>(),
                     this.f.h()
                  );
                  this.b(var1);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2drgdwk78ygf7","jsdpm9H0f8k6tItIzIbH0n8BCc54mpsYNhHDCGO/WoM=",5057321702984187507,-8291199345688995714,-6907875438630215743,-8698721825735759264>()) {
                     case -2003686722:
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

   private void a(f var1, com.yiyiaddon.e.j.e.d var2, String var3, d var4) {
      c var5 = this.f.a().a(var2);
      if (var5 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s312y7q88ywiy7","NLgkWUuHAtod1tXdAMOB+UmYgRFsgzqsZhnyEkY780o=",-1799065342060969027,7448408301456290269,-1635612347335915695,-1646954523587724976>()) {
            case -1672057988:
               if (var5.G()) {
                  AABB var6 = this.a(var5);
                  Vec3 var7 = var6.getCenter();
                  Vec3 var8 = new Vec3(var7.x, var5.ak() + 1.5, var7.z);
                  double var9 = this.L.player.position().distanceTo(var8);
                  if (var9 > 128.0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s125wbtatty1eh","G4eAF2UCsQkESYjG3c9v5E8SK+F6huLGbsA/Pil/GPI=",7276183171522426008,-6951054326857070351,1991098917887741449,2023849981636647413>()) {
                        case 1523064212:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     var1.a(var6, var4, var4, j.Lines, 3.0F);
                     com.yiyiaddon.e.j.b.a var11 = this.f.a();
                     boolean var10000;
                     if (var2 != com.yiyiaddon.e.j.e.d.AFK) {
                        label56:
                        switch ((int)com.yiyiaddon.m.b.a<"s2polkq1b9jjmg","LI3tNhrqUcLSzfdC+gMzYDHwxF5MZEots6JnB+wane8=",2055625629905653515,-1975097463512317636,6510181088471519411,-2751202162571983619>()) {
                           case -520942861:
                              var10000 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s2qwnjjgbs02pw","abbjM9AlUogQPMvfXGZb2jhBvigvS4qOr1EWhRCvDiU=",-4880311471357400985,3486353554137340968,-856460940760454072,2641929003088145221>()) {
                                 case -1882338854:
                                    break label56;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s2pg1s8m87a28z","brtM+GZTPFezRwcYi8X1etRcLpzVlAlbAJSNp/9lNuo=",3648150500781257876,8261182770648409815,-4791958801128106676,5581744793946429090>()) {
                           case -1091509843:
                              break;
                           default:
                              throw null;
                        }
                     }

                     boolean var12 = var10000;
                     double var16 = var11.z;
                     double var10001;
                     if (var12) {
                        label49:
                        switch ((int)com.yiyiaddon.m.b.a<"slnj56zr07xn9","6T+PSEuctREqoZw0+U8IGPptIQUW4Xx3d0CDVBPsVkk=",3835942260046685045,2136816702011654548,-4080566760137796729,3623482039225621086>()) {
                           case 382701609:
                              var10001 = var11.A;
                              switch ((int)com.yiyiaddon.m.b.a<"s165qprf1nhoax","injsClbXmzMdn98HLSsyAcTp44cRLopgGlFigSq72WY=",6550393127328481726,-4147102498954986147,-223722458278219340,-8147017662496532532>()) {
                                 case -197041898:
                                    break label49;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = 1.0;
                        switch ((int)com.yiyiaddon.m.b.a<"s184i41gosd9p9","CyAASiwLBZLNjod4UxCjc/A3gn8ImzWQJ3Bu/cTTh4E=",-1535994067611745442,-5386632756329495243,-4677981576920129394,-2008940618775280931>()) {
                           case -1597456357:
                              break;
                           default:
                              throw null;
                        }
                     }

                     float var13 = (float)(var16 * var10001);
                     int var14 = var4.ej();
                     String var15 = var3;
                     if (var12) {
                        switch ((int)com.yiyiaddon.m.b.a<"sfu8w9i467456","ONXbUFGk8bo/VUB2wCXPMrT30Ruop98lz3kPVKmp5H8=",-3947455311902758740,5005229398903435444,8501330611436754823,-6526947016098281174>()) {
                           case -2018975739:
                              if (var11.gE != 0) {
                                 label42:
                                 switch ((int)com.yiyiaddon.m.b.a<"s309cq86uy3wt","aTYYmXeoooFP51XftTle/YI85e/Tyr1BN6Cak44gXbo=",7545697307457091099,9126551859862629014,-236664494036488522,-2620848940731158511>()) {
                                    case 497165954:
                                       var14 = var11.gE;
                                       var15 = am(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s39um95c1v4in7","tbyjmfOKSBhT2ooDEZIN6DRZBnUDYId5KEvd5aZWa9U=",4306729948486090775,-3820770772369278604,5229526716202451327,-6593245462783632048>()) {
                                          case -470802665:
                                             break label42;
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

                     var1.a(this.a(var15, var5, var9), var8.x, var8.y, var8.z, var13, var14, 1.0F, true);
                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2yof1ms0m8io7","kYl4UUPBokGszj26nlA/O15iPPyxoHqC8Zx1lBpLWL8=",-2665718412496316641,-4385441870179095984,5089765879227989868,-509086322540493101>()) {
                     case 876407398:
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

   private AABB a(c var1) {
      BlockPos var2 = var1.a();
      BlockPos var3 = this.c(var2);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19u2bnvj4zz1t","lwMR3CZlS69IcCE/7riWiDLrcFiP4Wtx+9N+GRY4KCo=",964317741771417451,-6792264034551933655,-2931145558927050107,-924338483153078422>()) {
            case -774397154:
               AABB var10000 = new AABB(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s2ui3zeccpx8al","PhrkptVTIzi7au+5pC2m5g+klWItRpaszhJLLjz37yM=",-5002325024470372082,-2125035736003765463,2186142941270827830,2591856470534617600>()) {
                  case 192533543:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         AABB var4 = b(var2, var3);
         switch ((int)com.yiyiaddon.m.b.a<"swqqjq2f3aaxk","XbKvxb3q4epdTUWlnsFRbj96mEevcRIwMVxKnfm4eUs=",-6798733580228252662,7688944929277718830,-2632212423984820900,3883033898195397953>()) {
            case 1998569107:
               return var4;
            default:
               throw null;
         }
      }
   }

   private BlockPos c(BlockPos var1) {
      if (this.L.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o1qrjm7gg74n","Q80s8TtrNFqgbd+ednerK7XoBEAGTYqyxLFA0sLseCA=",-1322624641520651829,479916750085663860,7823418740247074724,-8338507826571144970>()) {
            case -284270328:
               return null;
            default:
               throw null;
         }
      } else {
         BlockState var2 = this.L.level.getBlockState(var1);
         if (var2.getBlock() instanceof ChestBlock) {
            switch ((int)com.yiyiaddon.m.b.a<"s1met1qln4bwyb","89RTbHsSeQmdIj7Js8k8N5+9rpInolWGd2UUBH1+3bo=",1043587955552389451,-105486827607695341,-2390397766933370984,-5520588897759350631>()) {
               case -107288028:
                  if (var2.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
                     BlockPos var3 = var1.relative(ChestBlock.getConnectedDirection(var2));
                     if (this.L.level.getBlockState(var3).getBlock() instanceof ChestBlock) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3n37qgw42g6ey","qZ8haZGd1JXcQqzkz+OLQQvfUlLRULSwB7oD7urTPPQ=",8881614228373059804,2981665815755942692,-2069612414236450666,-5998600007751684575>()) {
                           case -1782632065:
                              switch ((int)com.yiyiaddon.m.b.a<"s2bjt5yzsio03c","7zjKNENYP7XelGL9Docd7GwUrAfZZGPLO34Q4//X85A=",-364267992054911780,8919706721893590806,3026785389717776836,-1244958611219754019>()) {
                                 case -516792399:
                                    return var3;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s34z77u48jdee6","q+ccHeiskZNipkvvPPr7uBv8jAkqNM0YEeRpM7O2tqA=",-6145335202344314116,6786185555261711332,5615976424968939468,-2908716823454888727>()) {
                           case 1042939235:
                              return null;
                           default:
                              throw null;
                        }
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2hrr6cd2n1nt9","PKgtL2uZTZey9aJVvQmKth9bE3mUtyG7fubonHiv/5E=",4182410298500570424,5526504087633391144,4857240817833868248,1711679429803057262>()) {
                        case -1102743756:
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
   }

   private static AABB b(BlockPos var0, BlockPos var1) {
      return new AABB(
         Math.min(var0.getX(), var1.getX()),
         Math.min(var0.getY(), var1.getY()),
         Math.min(var0.getZ(), var1.getZ()),
         Math.max(var0.getX(), var1.getX()) + 1.0,
         Math.max(var0.getY(), var1.getY()) + 1.0,
         Math.max(var0.getZ(), var1.getZ()) + 1.0
      );
   }

   private static String am(String var0) {
      if (var0.length() >= 2) {
         switch ((int)com.yiyiaddon.m.b.a<"s29odudak600th","ojetKR7+1vviMzPDoAIyH5y6hXdb9TLll4NDtrm5gvI=",-7413393064930026687,-1675371616381217916,947665726230538771,7237074740070624702>()) {
            case -1293389017:
               if (var0.charAt(0) == 167) {
                  switch ((int)com.yiyiaddon.m.b.a<"st2mbpzhsxk38","mKfyuGMQ/xvZPZi9/FhDnp1hstBsx5sjOv4rdu7YT8U=",-2346643619412414604,-1604707571327323291,1027725302120383546,4212006320567940477>()) {
                     case 130567737:
                        String var10000 = var0.substring(2);
                        switch ((int)com.yiyiaddon.m.b.a<"s3v3gevi94lcix","rAQDK6Z/gF5ViHb2Pgfx8d00MY+9tBUirLS6zCQosus=",1444377803753885201,8378918489165297728,2495159107731731870,-1739558898987757233>()) {
                           case 29961051:
                              return var10000;
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

      switch ((int)com.yiyiaddon.m.b.a<"sdho4hydkas66","cyWyIzzEyZSH4w5xIZBRCUj69RTDvduCnnu3i2asV34=",3109789085861744030,8137736523033966238,3371445529882034319,-4190896544404188165>()) {
         case 1346240952:
            return var0;
         default:
            throw null;
      }
   }

   private String a(String var1, c var2, double var3) {
      return var1
         + an(var2.bU())
         + an(this.bV())
         + String.format(
            (String)com.yiyiaddon.m.b.a<"svinvyocramve","20idxIL3lDkdBxXKVZ0hx0iX56IjFwGkJr1P2rZ8T6LmCGsMLsujXBhbeKNpaw==",9068647688552765678,120865583235643801,-1750286385365440114,-3216670291782684350>(),
            var3
         );
   }

   private String bV() {
      if (this.L.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"szvh6vz9h83hb","5GnSqr14S1Kq7TIU9Lt8WN5ri6CmxcrLz7sVhYiQBbY=",-7834462330468024664,-1801075605740325873,-3353233183714850289,7212288466813450745>()) {
            case 1032039491:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sj5wojr8e8syw","zm0kT+yDMcfcy7VNIo3Zv7rV8FzSAmheofNj6w==",-4366179347466449922,-3831801318612275582,-4692094061970104682,8335792659655719539>();
               switch ((int)com.yiyiaddon.m.b.a<"s2yvmq10rxeruh","nFrTWI8OXkwVCMpk7hpqylmyDRhFq8b0/YiPe42zZRA=",4819639980033386696,-7219036328076398325,-8967957487930402977,6105652524093468032>()) {
                  case -913148692:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = this.L.level.dimension().identifier().toString();
         switch ((int)com.yiyiaddon.m.b.a<"s3fljqe257vazs","2dyCPARfwOGUGws8fhC2/3qdV8XKVzGd7ICigowJ1ro=",388620328667946300,7846068795649866965,227225135517218799,6264423848010305889>()) {
            case 1843492194:
               return var1;
            default:
               throw null;
         }
      }
   }

   private static String an(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kwckg5d30999","lbGGx0JZ1Hp9cmdawt0uJtBJhTvzctLcRuX44/DHNMo=",239100961015651482,2925303878698638237,3270059891978331304,-5896073595365718923>()) {
            case 2081203888:
               return (String)com.yiyiaddon.m.b.a<"s1ivyqun31ctpc","DGSx5HiV1WUnRy+MXuD0te0EPxV6dwoP5zNzOTfy940=",-1279831700677664636,-5132081441973156981,-3207657907306974082,936582221269489272>();
            default:
               throw null;
         }
      } else if (var0.contains(
         (String)com.yiyiaddon.m.b.a<"s272pja8jja6g3","zBC/rFcNwYvt5Wn0g16sggrv3ztkjxv4HFAVydIj8cgcQbtSe3H7GlxZTpIABw==",5306985145707214480,-1010712008889939539,6637421360700688920,1160988044807058456>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"shzvgj0umoio7","eFhlH9LDWLYhcQk99A6F//WMSoWXfjI6tW7osVLeIyQ=",-6893928016418936970,4935035084210710240,-546974843254941264,-1969759496603580518>()) {
            case 464160678:
               return (String)com.yiyiaddon.m.b.a<"s1giso8319tca8","djymAkKjiF8uQHSVwnZHk6BA26ElZevWQBVkjKSJ56gKsw==",3030816705124186034,-2752283467282688109,-1206829667186736675,-7516262966315497083>();
            default:
               throw null;
         }
      } else if (var0.contains(
         (String)com.yiyiaddon.m.b.a<"s1uhbib8guelii","jodMKcr1jjws7+sHOJa7aKMi82xi3wyvPuLwu76KkLU/vXukLIWpOw==",-6036730521601812847,-9130785852456868458,-1938852967727368320,8261365776725719429>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s1psgevgxrgizu","cz3PZP+lcpkMFyK856opVKOOW9AwO8LrYpSErbpR8HM=",-1129866296486523774,-9160995075225320742,-2408639056879338442,-4915573475473742847>()) {
            case 804031889:
               return (String)com.yiyiaddon.m.b.a<"s3bb3b3xop0odh","M7LwmHajxsPWDBIFN8ysr6bgPXfoW7Q0B8L2BA4q1Lc=",-6344120858835207972,-1468640764754679778,-7130632037772364485,-3101855239193491174>();
            default:
               throw null;
         }
      } else if (var0.contains(
         (String)com.yiyiaddon.m.b.a<"s3dqdkluyexp5k","q4/yiyLJ9VoEpJsU+v8DH/hvLCqipu4LbkKghuMhueOo1Q==",-6840278403696582944,7782447890137509746,5443820061638457462,6907422830956096924>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tbbcyy0i3alt","To0bxrDoSJ63Bp/ow5Ouvdwd3dGRPvEWh8g4H0LHNB4=",3262257157467359403,8839766970016800551,2045642239424993198,-5830411760571263733>()) {
            case 620106518:
               return (String)com.yiyiaddon.m.b.a<"s1q90spypw1neq","BgSnGdge9QVEIEFFZfUW3+N8CSRO546kPuEsxW8YH4Q=",-110142468220139977,-6929921570040656615,-5695531766771266926,-4998734747036864405>();
            default:
               throw null;
         }
      } else {
         int var1 = var0.lastIndexOf(58);
         if (var1 < 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s15y55og78nhi","fPO4I4KSTBXTjPPtN6ia6TlSRZt5Y+OywNZuYCh4QOo=",3188035148837553190,-9209116930573802490,-8999146614296250737,809548439333413562>()) {
               case -798477002:
                  switch ((int)com.yiyiaddon.m.b.a<"s3ncl3s77i6ouz","7dSfFh40j4G7J7gJ8XInYY+mwwjiC/WRkN/yIr3Vvd8=",5488062862631321349,3958239619900036703,2761132839213513012,-6652192397937457563>()) {
                     case -88647739:
                        return var0;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var0.substring(var1 + 1);
            switch ((int)com.yiyiaddon.m.b.a<"snj4xnodoi9bk","1rymLLKHVEszpzy2wIAsoeEyt8UAqIH7alr/8c0z3H8=",9114646001043442177,-1432846342688670264,-8340092359134791183,5993793113828280868>()) {
               case 213341119:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   private void b(f var1) {
      if (!this.f.a().cc) {
         switch ((int)com.yiyiaddon.m.b.a<"s3keejopmwumyu","SwW4FJi0fV1yJz74+KT4rswDdNxFNfjQ0EqlxScmQJI=",707081878759025209,2025993160209388165,-6192998835814066853,3544957571753854076>()) {
            case 474493678:
               return;
            default:
               throw null;
         }
      } else {
         label65: {
            int var2 = this.L.player.tickCount;
            if (this.jj != Integer.MIN_VALUE) {
               label57:
               switch ((int)com.yiyiaddon.m.b.a<"s2ih12402v2cen","HStTe5jZHzMHWacQdWCeyIsTihr7Fj6KBIL7ACq1IC4=",-7948097195077018420,-3142239982242001744,-7647244520924554731,2575620492015076174>()) {
                  case -587832639:
                     if (var2 - this.jj < 10) {
                        break label65;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s18pgn8gr2mfal","YrLkxbNpXQb0yqxdI1GOjoHhMhvL+xhYF4WecuQG3sQ=",9078312276382814176,3983977960328492801,-4133874522601796246,997306421627863517>()) {
                        case 1105971334:
                           break label57;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.jj = var2;
            this.E = this.a(this.f.a().gp);
            switch ((int)com.yiyiaddon.m.b.a<"s1ia5nd826ijkb","f7+dskFzR4s+yiZQPVYwSIKcMTJjekusZe2ivDPxlIU=",-7083272745940041093,366748691097181512,-5209238542455461195,7576229825926904815>()) {
               case 1911172504:
                  break;
               default:
                  throw null;
            }
         }

         if (this.E.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2d7a2bsovp5o8","V8l+t8Ivq6/dRS8qU7oCYrfLSnx7X62+0T1zLuE7g8E=",-6630714983334710607,-5016338725729912896,8182267229577805330,-7054202187591397218>()) {
               case -1685704607:
                  return;
               default:
                  throw null;
            }
         } else {
            BlockPos var3 = this.L.player.blockPosition();
            Iterator var4 = this.E.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s2vlznhgq3bdjy","acD5vG8FARwLZZgsCx+FR5Thl1lA9sjcUnpgMkl8FW8=",-1272483254822564281,5735355984092546919,3181458399623881539,-7351318560091717181>()) {
               case -1211340155:
                  while (var4.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s12jgqnz9us2mt","IbVUVosFogm9yW5Xm5ajtUdhFCuQwAY7rLzIz7hpZJ4=",-6246350359381104722,6920717516757274804,904762783502347944,5727470594451302169>()) {
                        case 682794088:
                           BlockPos var5 = (BlockPos)var4.next();
                           if (var3.distSqr(var5) > 16384.0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2dfhssbnn7t3p","36H0G6fsyAihZ2YWJDoqeBivnWzx4p6Bu1Rd23Z7RYg=",-421024125527311287,6696259295733212535,-341172328842676994,-4336260102972718006>()) {
                                 case -410780860:
                                    switch ((int)com.yiyiaddon.m.b.a<"s34rva7ki2kcgk","M96HltkCuNBh8BQMb4CHDnxJYvMGNgcKYxmeCfoiWnA=",6341691666297423589,-3854612285636454303,-6107757175373899413,-1466025680034136573>()) {
                                       case 874259139:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var1.a(var5.getX(), var5.getY(), var5.getZ(), r, s, j.Both, 4.5F);
                              switch ((int)com.yiyiaddon.m.b.a<"s1fwnxp62buhuu","XlbXotF5MMcoNTNooWZP9K3X1WrjI39hKRNE+4i1VRQ=",7915035936183303220,-86020055662426245,-8421366481007163057,2523655346529574207>()) {
                                 case -157110886:
                                    continue;
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
      }
   }

   private Set<BlockPos> a(int var1) {
      HashSet var2 = new HashSet();
      if (this.L.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32395x2od829a","qGM8HLdd8Foo9xiyBbBDqlU+3qPqU+qq4Ays/Fbk3qQ=",3154737086923701902,6156637985579100216,-8329763001671977373,-7492302820099063170>()) {
            case -308634631:
               return var2;
            default:
               throw null;
         }
      } else {
         BlockPos var3 = this.L.player.blockPosition();
         int var4 = -var1;
         switch ((int)com.yiyiaddon.m.b.a<"s1x54tn6u3kw6v","8OA249e1ib8eO7/kqhIvJ6rvNJfvuTxn+tVlgguVYok=",-603548426197431810,-2058027654064631931,-619344737983979761,-5511138923756159172>()) {
            case -1206327011:
               while (var4 <= var1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3eymfld6cuvxg","nrGAKpnnNn9EKw9SJVHTgER1vVVG07ckP1W6tacb6dM=",-5251086967344588588,-3074648364315754377,8044079922150227833,4178616735374719095>()) {
                     case -1259583445:
                        int var5 = -var1;
                        switch ((int)com.yiyiaddon.m.b.a<"s3ebb75n2z2leg","fwJy2PfogfbLPgVHrE5NfZw2OnBP5vP5Xo8i8LN1JL4=",4442059851810002024,-6273745523259019343,-6658179974783893573,9039342070320692974>()) {
                           case -1082979618:
                              while (var5 <= var1) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3fim3bbk0rcix","VU+v2SEc7/xDlFV31/0xvFUwZrSQcidHWrG/CINzn9g=",-1114837539374933376,-6723458318370394479,4510428103226997412,-6600895846606386922>()) {
                                    case -1658602256:
                                       int var6 = -var1;
                                       switch ((int)com.yiyiaddon.m.b.a<"sbp6syqatknb2","UHEXKFurjuOM51ga5VHQrOJiX8FdtspiyJt6xhh+U4I=",-2418016016389610631,867018835635342206,9029745893846457870,2289127675401832516>()) {
                                          case 209676641:
                                             while (var6 <= var1) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sst4lxf7to77g","mMr54qxPD79fYRY+xuHTryqDEoXqhwzhNsRK3PbamPQ=",7862017781199524831,-6401001576419778142,-6909723165472793238,-2454775789611935175>()) {
                                                   case -556992743:
                                                      BlockPos var7 = var3.offset(var4, var5, var6);
                                                      if (this.L.level.getBlockState(var7).getBlock() == Blocks.LAVA) {
                                                         label54:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1fs3tx9rxnzpk","DF3p/sm0dLrKvReqDk3CYFJxGC7OXSqLjxBNMoej0Fk=",-7135354462444188589,6805046694741565215,8113194993386869266,-1000355067838119113>()) {
                                                            case -693357314:
                                                               var2.add(var7);
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3mfhxtgp5rc4b","z7bwOw30q/tZnbY6iPWFaebl9yI04qhzxnZrmZlR/j4=",5160914974761806339,-8580238016476928556,2217449494384907828,38512077629498147>()) {
                                                                  case 432612411:
                                                                     break label54;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var6++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3ipcnrram0nox","Dk2kvOpANaZTdAdXV2xvQra9nKOrxygpCgD4mqjkkvg=",-4138469730856560311,9069988648478684974,3125146954177042465,-5863292887635479814>()) {
                                                         case -477575805:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var5++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s3m8fnk1gmdxpz","U/hcDMyh8hP6TA19ylItJHXatLvf/kGbOIUXfx6IbaE=",1962808663882792548,-5085145936884241946,5656741412239196907,-6696216602731716013>()) {
                                                case 1067043427:
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

                              var4++;
                              switch ((int)com.yiyiaddon.m.b.a<"s1vzo7peictof1","g5m72TfbIp5ZhDXWIUcNceGSpDWfhoGmwEtvQ+yJd2c=",8482911560072035384,1152793136526733844,2757278722492707133,411886724604041447>()) {
                                 case -134591616:
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

               return var2;
            default:
               throw null;
         }
      }
   }
}
