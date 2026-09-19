package com.yiyiaddon.e.p.h;

import java.util.Iterator;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class a {
   private static final double bk = 0.29;
   private static final int qa = 25;

   private a() {
   }

   public static String a(ClientLevel var0, EntityDimensions var1, double var2, double var4, double var6) {
      if (!a(var0, var2, var6)) {
         switch ((int)com.yiyiaddon.m.b.a<"se3o4ptricg65","hjZgIoBP+fBrUt/pScKDI1y1uCcNUfCGXbOu8p0uMuo=",-6039523770309544824,-3437686069460814863,2873689892555832362,-5678962555517093707>()) {
            case 1043719577:
               return (String)com.yiyiaddon.m.b.a<"s29x306o0jp41c","5W/YNNZ0bDkJCyBf9ZeKHNX1oQt+z39GmLg1RMG6BNmnc27qwGSs6vzX5WxpWC8AxRU=",362656230188215328,-3405222765581816715,8274123281090888560,-5793476295713526650>();
            default:
               throw null;
         }
      } else if (!a(var0, var1, var2, var4, var6)) {
         switch ((int)com.yiyiaddon.m.b.a<"s35hca18zbs29y","2RpiB6kDjP7pt+EdKeMvu7Gsr8R7zRAAenRQqAknmsM=",-7074866968749826259,1338657083989843462,-8405668984301384688,2647504683076912746>()) {
            case -146296951:
               return (String)com.yiyiaddon.m.b.a<"s3q4ock69tq9il","X3+oAeTST2UREQOkUSNTuFtYWR6Pn6TEIYYJo755H8K8GPu8/hPmW1Ul8G4=",3949898162736011873,-8898403423398674822,-4305638384250875983,7001114408103145295>();
            default:
               throw null;
         }
      } else if (!b(var0, var2, var4, var6)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sjkzxjpnhz27","W5ZPKDJSj/xaJonrQxMNKB+00/nBxj5qmygJinZDOfA=",-5246728092090429619,-3375854344055516809,2461525800302794501,3764795306848530055>()) {
            case -923138744:
               return (String)com.yiyiaddon.m.b.a<"s6s5t143v8omc","M0+YCPrLPz4kPI9CDX65JD/4+N3192OaAlsvtSRP7Ne3iuQFtQeCRs2ak+I=",-1024603813246325130,2116366207671503559,7170743055368029355,1236132431057456650>();
            default:
               throw null;
         }
      } else {
         String var8 = b(var0, var1, var2, var4, var6);
         if (var8 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sje3mlx2woj9j","lyh/nNCwaUuVYCun820pKPaTU0sSx1SOXc5RL7vVa+A=",7915954138426732522,9004723645730636431,1671415909891874427,2502789611285796041>()) {
               case -446206040:
                  return var8;
               default:
                  throw null;
            }
         } else {
            return b(var0, var2, var4, var6);
         }
      }
   }

   public static boolean a(ClientLevel var0, double var1, double var3) {
      int var5 = (int)Math.floor(var1) >> 4;
      int var6 = (int)Math.floor(var3) >> 4;
      return var0.hasChunk(var5, var6);
   }

   public static boolean a(ClientLevel var0, EntityDimensions var1, double var2, double var4, double var6) {
      AABB var8 = var1.makeBoundingBox(var2, var4, var6);
      Iterator var9 = var0.getBlockCollisions(null, var8).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s28tnczom6oyo8","r2Gs9GrZaMg6HeReFM+VOorlZc23Wqhn0xMacwmX/aU=",-3281183839291938947,-8179270520093906311,-6194862271879349103,-4232086201194890075>()) {
         case -980473420:
            while (var9.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s13qdcvtfmudkz","52F/zKintEB2hRz97oGqOCe9edpPBVC2QnLzYTFv0kY=",4355841929326734717,-2697570716517724921,5262960329238831555,3420748272357215569>()) {
                  case -76943688:
                     VoxelShape var10 = (VoxelShape)var9.next();
                     if (var10 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3hndl3rmwl341","gOE+X3UWsejMVVtSmA87r9XigV1BsA4coGlFc1/6MVg=",-8079737716848970702,1118310175573191191,3433892648704363811,-2229186195677623469>()) {
                           case 323827869:
                              if (!var10.isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sal5zoekcdv2v","75BH1PWJkJn+sBL7ivDCb0FgV8D+G4ewRxfwIO4y8lI=",7416102283350861447,-2582741104721942172,6544844511595225707,7748158157601902373>()) {
                                    case 466539687:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s2x5jkkh6l5fkx","BSbf9mxhMfCVbr+EU/XNx9tm9OqJ3F4aPeJ9khiaK48=",1837731035826323934,933507067010768182,880931333705689878,7582087133860647183>()) {
                        case -1722982828:
                           continue;
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

   public static boolean b(ClientLevel var0, double var1, double var3, double var5) {
      AABB var7 = new AABB(var1 - 0.29, var3 - 1.0, var5 - 0.29, var1 + 0.29, var3 - 0.001, var5 + 0.29);
      Iterator var8 = var0.getBlockCollisions(null, var7).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3rqf5pfa7xf4","8HIKAJfdwUH4u4L+YiC21PtAkKj4iMpmmkHKHrqZ138=",-8975924032045961998,7303814501005980801,4379797410049420228,-5696919324289641549>()) {
         case -789996941:
            while (var8.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sq5obc3hi4lyt","MA5Nbf3LIm+WRPC/CxiYofL8GH7zmsC+/ihR0t5cAHw=",6280092939976960065,-5786073733754333255,3159663807409440475,5909727886874435248>()) {
                  case -968032610:
                     VoxelShape var9 = (VoxelShape)var8.next();
                     if (var9 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s44f5wo3ggoo1","kbrgEQheVHPdjfmzM+b+Tnzo1hucgBhb58EhrEOxLHI=",-4987847975030602332,-6567965943588424329,6725279661932998937,-4612439034794911887>()) {
                           case -95821581:
                              if (!var9.isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"soqo6sr21n6d","flE4PpNBedQAqt6hSt+EmZA3s0H/tEaVrTh1V4oawa4=",-8359777107410163714,-8512640394385123970,3931398313164297337,3785517221518012604>()) {
                                    case 1171562043:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3usjtdv5ltyg9","WBs8ikgBxazvlMf7cUSzMKSPLzpc2Gpt4wxygJcYAvI=",5932251344578481661,-1754244069623710223,-242079877979288850,1418758615229309504>()) {
                        case -122039491:
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

   public static String b(ClientLevel var0, EntityDimensions var1, double var2, double var4, double var6) {
      int var8 = (int)Math.floor(var4);
      int var9 = (int)Math.floor(var4 + var1.height() - 0.01);
      int var10 = var8;
      switch ((int)com.yiyiaddon.m.b.a<"s2lpprcxlkhhuf","jmy0ejS+27/EQh8BnomDqOPwwVXYE2nQfhqxPUFKLrQ=",-3146128715727197230,2813418276522217958,-1871691424417787415,-5105053771265301833>()) {
         case -1407580634:
            while (var10 <= var9) {
               switch ((int)com.yiyiaddon.m.b.a<"s347qvdofsn8kg","VYAmYsqK0RzrzFjczfEMEIOXuso74ObJUEIgNdOQwQ8=",-6477805878281051769,1553713092255312861,-369864781516484413,-6333951412379468516>()) {
                  case 116028874:
                     BlockPos var11 = new BlockPos((int)Math.floor(var2), var10, (int)Math.floor(var6));
                     if (var0.getFluidState(var11).is(FluidTags.LAVA)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3knedf2kd9pra","8OUBbSnFsge8tXoz1ebBvKDxpotTiIOyK5A1ROLfsZY=",-4612302879424355498,8145026133542314442,-5460900987492003807,-4578015158110554109>()) {
                           case 1669770497:
                              return (String)com.yiyiaddon.m.b.a<"s8qayjok1a2wh","7AmIkbq4iZm2eHvIKVpAKJJqoDashoBKBhR+LXkzZ9t9bu2mRqU6JWtRUrk=",408790715462342934,-7417604684114220699,1362532298078365663,-8284922726478323548>();
                           default:
                              throw null;
                        }
                     }

                     if (var0.getBlockState(var11).is(Blocks.FIRE)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s23ukaacy43qoo","EoQEVVISyK9cyeR5tKO6nt7OQbzkgdb8YdHal3rqKzw=",7265865051351172619,8951362383834445304,3585054545112670154,1146360797749694983>()) {
                           case 420154438:
                              return (String)com.yiyiaddon.m.b.a<"slc6hycmrwhd2","xQSfQCftIQcM6Qz2eU0u17xXy9q8YYmwPnNe6J1i4esPkNJ9UHIL6w==",-1984917555598377602,-4876316578912029480,-1404653295532458858,1378533321723697128>();
                           default:
                              throw null;
                        }
                     }

                     var10++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1o8k6sytdxwin","qzhmdki+jF/wiZokJWOGoBdnaG5zaiNAi66/8xzDABg=",1455532379704527392,-5587991637300584576,-6133475368895497077,6251453068098559738>()) {
                        case -1970231882:
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

   public static String b(ClientLevel var0, double var1, double var3, double var5) {
      int var7 = (int)Math.floor(var1);
      int var8 = (int)Math.floor(var5);
      int var9 = (int)Math.floor(var3) - 1;
      int var10 = 0;
      int var11 = 1;
      switch ((int)com.yiyiaddon.m.b.a<"s2vpbz2vd9aist","Slg/A3vIVtFKk6Y2ApsLIp7HptXZZpF7Dv1sXra1udI=",5427821623771707388,3737830501181594468,-291562744295730798,9061109884281292973>()) {
         case -1156408406:
            while (var11 <= 25) {
               switch ((int)com.yiyiaddon.m.b.a<"s2t1h0kgfhh4h7","3l0B80MnnaSgO26u1AvcrIwb6trTRQzQ+8RrSNt6pF8=",-2567633639891237393,-4747176625067883338,4178576315819999315,8754244299522493593>()) {
                  case -1649244790:
                     int var12 = var9 - var11;
                     if (!var0.hasChunk(var7 >> 4, var8 >> 4)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2yy6x9jloz8gt","s4HMm/rftw1LiAq+JC4G48Na+g3G1jBd7V4Yvn5RKBE=",8773228467723315075,-603344206847900369,-5003155273188649022,8031613707352933504>()) {
                           case 244414803:
                              return (String)com.yiyiaddon.m.b.a<"s29x306o0jp41c","5W/YNNZ0bDkJCyBf9ZeKHNX1oQt+z39GmLg1RMG6BNmnc27qwGSs6vzX5WxpWC8AxRU=",362656230188215328,-3405222765581816715,8274123281090888560,-5793476295713526650>();
                           default:
                              throw null;
                        }
                     }

                     if (a(var0, var7, var12, var8)) {
                        label34:
                        switch ((int)com.yiyiaddon.m.b.a<"s25nyj2xy7q2uy","7nh6ZbrfPv030rwdj8wKHP01/vei6BUYdAjzWSI/rHw=",1223804956927451491,4011342522281413626,8759640088625277741,-8948517775186274652>()) {
                           case 129744596:
                              var10 = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"sucv2jnp84dm0","WmHF1VKSiO1hinFwzTUxV8bdKUrFYFTF3euOMmkLO+o=",-8920783665009601678,-2227865599246246900,-4099893369767305357,-6259126966744481643>()) {
                                 case -410945213:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (++var10 >= 25) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1hry5zx1uqvhl","RQfxIhEwGP2rBU7oOWys72qLo3pV5jwUMyegtFd91sk=",905557220423357570,2668886206486481700,8363949905406962240,-1675805533076824335>()) {
                           case 1281345552:
                              return (String)com.yiyiaddon.m.b.a<"s1mo3kf4zcg5tk","9BcQtH2jpN91qTW4rH8/fHpJg1bEdPYsIOuXbbP5jpxvunZ2OImlATVsbA9dO1tUUYCtjnTl",3885200424507407776,3108597230680432627,1476080389067831110,-8471950575225240715>();
                           default:
                              throw null;
                        }
                     }

                     var11++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1l00wxumrx8qt","zgo3hxra41R/tLOablnel6VQY4AX7Z/o1RK9tuDVn9c=",-2423736327221827379,6972131463797219855,1336306996000362236,8140328175122001067>()) {
                        case 497675097:
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

   public static boolean a(ClientLevel var0, int var1, int var2, int var3) {
      AABB var4 = new AABB(var1, var2, var3, var1 + 1, var2 + 1, var3 + 1);
      Iterator var5 = var0.getBlockCollisions(null, var4).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"se31ei3031tjt","s369STYuxslU6STaBLbquvPvJvhHMcrYi3bcepZwzts=",-2060444339658279478,-5223663142341987976,4838281112729690616,-3383045306220585671>()) {
         case 1204128474:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ttx8do29u4pc","H9AH3Lcxgcmlg716ljsMfi/8FPPkxJUBvwQeXKfiOYI=",-7132762448346545576,6608335779065514706,-2921397210679394807,-327793191437508856>()) {
                  case -1901348691:
                     VoxelShape var6 = (VoxelShape)var5.next();
                     if (var6 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3doqg3ywb61rd","y8wJWnxBHeMikOQzfaPsulluR02HZG5X4mND/b3VLS8=",-6526160838163098577,7434516830950584244,-1445574153773628955,-6322586603784244649>()) {
                           case -1275396326:
                              if (!var6.isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sgm9qa820ggrn","KN/f3d8GVzVLloOG0Ufdi3Fe1EljYPDFqnYgJZDM/gc=",-7014024665562464430,9120744992607921866,3810569477322911768,107215283828668359>()) {
                                    case 1574508102:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3vrwcev6pt71e","0FWB6DNlEoor155vX4tUaI2SYaiFxa1vnNpJvCin908=",-2203121274188288795,2908726932022123738,-7167201457761172562,3136117670901391273>()) {
                        case 1498374561:
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
