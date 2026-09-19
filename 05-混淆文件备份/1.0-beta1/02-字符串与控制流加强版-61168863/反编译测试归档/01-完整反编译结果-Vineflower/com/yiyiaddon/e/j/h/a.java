package com.yiyiaddon.e.j.h;

import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class a {
   private static final double W = 64.0;
   private static final float bi = 4.0F;
   private static final double X = 1.35;
   private static final float bj = 1.5F;
   private static final int jd = 70;
   private static final int je = 90;
   private static final int jf = 255;
   private static final int jg = -1;
   private static final int jh = 12;
   private final Minecraft K = Minecraft.getInstance();
   private final com.yiyiaddon.e.j.a e;

   public a(com.yiyiaddon.e.j.a var1) {
      this.e = var1;
   }

   public void render(f var1) {
      if (!com.yiyiaddon.l.g.a.e.a().a(e.a.MINING)) {
         switch ((int)com.yiyiaddon.m.b.a<"su9y1pmhcnv9h","xZO1aL0c+u0ynY5GDTvYUPjnO898KIek/tXWStfBw18=",7554216675195548311,-4960815433317069796,-3503678770408201750,4179916790912539861>()) {
            case -428938450:
               return;
            default:
               throw null;
         }
      } else if (this.K.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33pmtv2kzwud8","oQXVOk2bYPHj/LmaE3YGZAckPJY0OPGUvBHQWsrEcrY=",4982497413249800240,393675739707474251,-8822171055015473666,8741121107462755224>()) {
            case 2135948796:
               if (this.K.level != null) {
                  com.yiyiaddon.e.j.b.a var2 = this.e.a();
                  if (!var2.cu) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3fp1c7tpzg4an","Q/Fky89EBtuV0hXn0CzTYpAWn2xYG/EtsnqjQGaid30=",3733822702687255427,-5738853254009381814,8390763566903708584,3778520198832351075>()) {
                        case 978136257:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     BlockPos var4;
                     float var5;
                     boolean var6;
                     float var7;
                     label166: {
                        com.yiyiaddon.e.j.c.a var3 = com.yiyiaddon.e.j.c.a.a();
                        var7 = 1.0F;
                        if (var3.isActive()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1jy1hafwgdooe","mR4pKddPwna6JuQ2mnWjYxWPmvqyVpG0KReQbILUQ94=",-703990595087607066,-1008720625736249140,4877252514552300522,-1620216873060631625>()) {
                              case -860525056:
                                 if (var3.h() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sbmni4sd2117v","KdPM1GoIpWESiSXqnbFEq1SohCjq6vSdPusnpDYQ6Ns=",7637737044683862534,1115705610387447812,-7722609297994314957,-2989039599637940382>()) {
                                       case -1891364497:
                                          var4 = var3.h();
                                          var5 = var3.f();
                                          boolean var10000;
                                          if (var5 >= 1.0F) {
                                             label121:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2aliqyks5lrgs","T0TzHsKUjQffCILNT0B2uUqb6wsncrR+BFWgWKdxzVU=",-8178871859621939541,-3917477225591832933,-1578977368472831943,-4124097284740315281>()) {
                                                case -950646184:
                                                   var10000 = true;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3n4qnk9iye3oe","p9EmMRYq6MzrpuwAbevAr3BP9tRpuXuFfPwZv70qolk=",851720513777854568,6020914972281713326,5194135679357028155,1483052106925926013>()) {
                                                      case 1819308245:
                                                         break label121;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var10000 = false;
                                             switch ((int)com.yiyiaddon.m.b.a<"siwlixevus39n","GgBx4bdrEHZDgzh5rIpGXACtbLzKvvFiPQPOiiRnG3Y=",-3443943312807769103,6435151926966447698,-4156721306099106813,5605037314461101675>()) {
                                                case 381678201:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var6 = var10000;
                                          switch ((int)com.yiyiaddon.m.b.a<"sm2shywpeihey","y/+9YXzviblPF7ZGYlp/BOPHd+TVxRLLuBZ6oq2QYh0=",-7778184169875459723,-7678842831528858829,-1452867420520299809,6726570027145904852>()) {
                                             case 578173929:
                                                break label166;
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

                        var4 = var3.i();
                        if (var4 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3byq7k2bx83ff","/0bd79AFq/L7sDL0jtEXwj5WHWpx8oeuw7s+zQAwQ94=",2561306696709092960,-4266557589475772674,-2728509207068231585,1918655845378532252>()) {
                              case -1276423710:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        int var8 = this.K.player.tickCount - var3.bv();
                        if (var8 < 0) {
                           return;
                        }

                        label129:
                        switch ((int)com.yiyiaddon.m.b.a<"s92pduoc90flb","iYNzlnHEtSLMbV97ZnRxxVKMHKWpPd6uvIholSQX8ls=",7261526542309110623,-4424506684336886521,-1606017310651926004,1665229207126807605>()) {
                           case -1711197883:
                              if (var8 > 12) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s25xjk73ng2uv8","/pWHWu1FCCvL5r49zpP1TSbyBKjvbJfGszo2SUUH7Bo=",-49970000097279828,-6467721055319544498,-8587736394869778304,-8546185104648399094>()) {
                                    case -894195623:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              var5 = 1.0F;
                              var6 = true;
                              var7 = 1.0F - var8 / 13.0F;
                              switch ((int)com.yiyiaddon.m.b.a<"s3bimfmao0z0xi","JGqZeWOsrY8CU0AmH+NyYLxLsUrP7wIGp6HIQUMMYdI=",-2831341286840860178,6234889555726453091,462179014296954907,3464705937134553965>()) {
                                 case 1193353636:
                                    break label129;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     double var20 = var4.getX() + 0.5;
                     double var10 = var4.getZ() + 0.5;
                     double var12 = this.K.player.position().distanceTo(new Vec3(var20, var4.getY() + 0.5, var10));
                     if (var12 > 64.0) {
                        switch ((int)com.yiyiaddon.m.b.a<"sjvpuokdeb8j1","PRVr7xX8s4fyW9O4xfClL3dJpGlJQaJdKzI9bFBIMK8=",809835260948312611,-332253345102736687,-4393841711861528131,2490189200080858679>()) {
                           case 1014811574:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        double var21;
                        if (var6) {
                           label108:
                           switch ((int)com.yiyiaddon.m.b.a<"sjyimcl107i4s","4v+bWoVS0Q0eWGtgK8nOjyOk894jFTGXZ7UKYAjzTPg=",-1135880404508392665,6429794136965820175,2605231181173900137,9205227472308594758>()) {
                              case 21658903:
                                 var21 = 0.0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1tiigabt2cq2q","BhtJpBcGJ05ssBaj9dZSOSSTwh2P1KqSo+UwhbnvMzg=",1287509896819371368,1613557142254480701,6891185615430616384,8931373399137605611>()) {
                                    case -350586905:
                                       break label108;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var21 = Math.max(0.0F, Math.min(1.0F, var5)) * 0.5;
                           switch ((int)com.yiyiaddon.m.b.a<"s2i0c9d74xynzd","Rd1IoK+Ag/250LqgF1cD8wjy33tmyYgOIlCx+6E2Z8Y=",-3303950887895350108,8006671388605582414,-50194373741036009,1067906702573814619>()) {
                              case -252921784:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        double var14 = var21;
                        AABB var16 = new AABB(var4.getX(), var4.getY(), var4.getZ(), var4.getX() + 1.0, var4.getY() + 1.0, var4.getZ() + 1.0).deflate(var14);
                        int var22;
                        if (var6) {
                           label101:
                           switch ((int)com.yiyiaddon.m.b.a<"s1vn3emu4h81h4","ckRGPeKAfhdmKDgf6mrd7dWSVSfyPpIZtDsAoV6n8cU=",7928234963028920990,-7860962570701170535,-7804968697139656302,364426750639835805>()) {
                              case 978178493:
                                 var22 = var2.gG;
                                 switch ((int)com.yiyiaddon.m.b.a<"s9655ndbni5lb","2rsG4p9hyu+37l6TG14bg4HH8R2SaI0dP6z3pd0nS8Y=",8279813891159360647,876608285081626548,231662598143211537,-9084790628788057965>()) {
                                    case -1831759321:
                                       break label101;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var22 = var2.gF;
                           switch ((int)com.yiyiaddon.m.b.a<"s36a2csl8jvqve","3S2BDLBA+issMKj6aFernsO7I46+lnXQKEe0f5FHnVw=",3944161321906628389,-2545048999436267772,-6594153446492007787,3966130835108346046>()) {
                              case 864591927:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        int var17 = var22 & 16777215;
                        byte var10004;
                        if (var6) {
                           label94:
                           switch ((int)com.yiyiaddon.m.b.a<"s1i49ifrm9pxxi","ToBJl/oys7Fu42k9jGhydCNHIo5rqRlX3A/YBIM7FBk=",4601366762092816397,-1498087486422826974,-7121033318486846424,1526244979123805573>()) {
                              case 793443089:
                                 var10004 = 90;
                                 switch ((int)com.yiyiaddon.m.b.a<"s15ue0tfrt025","rcNwNYtOs8OZQnaWX7Lqw5YxAar9JVYbw7oMcM+iC10=",-5015172801597029984,-7675140968641213228,-4243488122600981881,-2242539058227017497>()) {
                                    case 1417665018:
                                       break label94;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10004 = 70;
                           switch ((int)com.yiyiaddon.m.b.a<"s3ci8jjkokcte6","Ojj8aaA3f8qwJaG8Q3XQ6FsZnQvzJ61iDpMKCS+Vz1w=",7423543742867733025,-2358318827584643781,-3040076248507271405,6141421414302128386>()) {
                              case 428557512:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var1.a(var16, a(var17, var7, var10004), a(var17, var7, 255), j.Both, 4.0F);
                        int var18 = Math.round(Math.max(0.0F, Math.min(1.0F, var5)) * 100.0F);
                        int var23;
                        if (var6) {
                           label87:
                           switch ((int)com.yiyiaddon.m.b.a<"s3q4r5a08yp5xo","kqw2LreKCo8IfIqGSjfxIljnTlcTLCgvJvDe+dyLz2E=",7147791237378729869,-2404581531981832091,-4969103307826210459,9024643144316956936>()) {
                              case 1778001881:
                                 var23 = 0xFF000000 | var17;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1zg3rv81t4v54","XNbUKxKOlwWY5zlRJVzycO7VYWF1RCLNG6ZPVW+Rdro=",605352529530992909,-1654404417592603942,5057575116477612856,2580718903973444576>()) {
                                    case 817960592:
                                       break label87;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var23 = -1;
                           switch ((int)com.yiyiaddon.m.b.a<"s24q2hol1yrx6y","ogGOAXzjUe4Je7sb0MYrVR8dVRgHaQyOh7JqqptEOFQ=",4653712537597036779,5892753257070780019,8034426455127646441,8156770199330060568>()) {
                              case 486181094:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        int var19 = var23;
                        var1.a(var18 + "", var20, var4.getY() + 1.35, var10, (float)var2.z * 1.5F, var19, var7, true);
                        return;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1yy1x50h06kdb","CdS95K9+P3WKegkVcZ5ea5rW1cEoUBUYHkPos5zL/Ko=",-3186819481284771829,3499675264100811556,-6507238403304033118,1610073931330565025>()) {
                     case 330872444:
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

   private static int a(int var0, float var1, int var2) {
      int var3 = Math.max(0, Math.min(255, Math.round(var2 * var1)));
      return var3 << 24 | var0;
   }
}
