package com.yiyiaddon.l.g;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontEdging;
import io.github.humbleui.skija.FontHinting;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Typeface;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class a {
   public static final String FR = "harmony";
   public static final String FS = "harmony_bold";
   public static final String FT = "icon";
   public static final String FU = "material_symbols";
   private static final Map<String, Typeface> bj = new HashMap<>();
   private static final FontMgr a = FontMgr.getDefault();
   private static final Map<String, Font> bk = new HashMap<>();
   private static final Map<String, Float> bl = new HashMap<>();
   private static final Paint n = new Paint().setAntiAlias(true);
   private static final Map<String, Boolean> bm = new HashMap<>();
   private static final Map<Integer, Typeface> bn = new HashMap<>();
   private static final Map<Typeface, Map<Integer, Font>> bo = new HashMap<>();

   public static void v(String var0, String var1) {
      try (InputStream var2 = com.yiyiaddon.l.g.a.class.getResourceAsStream(var1)) {
         if (var2 == null) {
            throw new IOException(var1 + "");
         }

         byte[] var3 = var2.readAllBytes();
         bj.put(var0, a.makeFromData(Data.makeFromBytes(var3)));
         bu(var0);
      } catch (IOException var7) {
         throw new RuntimeException(var1 + "", var7);
      }
   }

   public static void a(String var0, Path var1) {
      bj.put(var0, a.makeFromFile(var1.toAbsolutePath().toString()));
      bu(var0);
   }

   private static Font a(String var0, float var1) {
      String var2 = a(var0, var1);
      Font var3 = bk.get(var2);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2su6d38r684uh","msrmMhAbwnxFOmC2WFQyBSDkZtHhbwYj9GDidq35u9I=",5562782290660832115,7413969793370195082,-3822795522637953435,17043671071083740>()) {
            case -1367515478:
               return var3;
            default:
               throw null;
         }
      } else {
         Typeface var4 = bj.getOrDefault(
            var0,
            bj.get(
               (String)com.yiyiaddon.m.b.a<"s16l7379kkw5bx","ghBe4RLkVER0ewVFqa6mcHbogGOgPGlso9vcgWG0mTw5Oh2AxvC2Ip8B",-3971434217140396799,-2115906722850253438,5481466961078022771,-8276175323029350140>()
            )
         );
         var3 = a(new Font(var4, var1));
         bk.put(var2, var3);
         return var3;
      }
   }

   private static Font a(Font var0) {
      var0.setSubpixel(false);
      var0.setHinting(FontHinting.SLIGHT);
      var0.setEdging(FontEdging.ANTI_ALIAS);
      return var0;
   }

   public static void b(Canvas var0, String var1, float var2, float var3, float var4, int var5) {
      a(
         var0,
         var1,
         var2,
         var3,
         var4,
         var5,
         (String)com.yiyiaddon.m.b.a<"s16l7379kkw5bx","ghBe4RLkVER0ewVFqa6mcHbogGOgPGlso9vcgWG0mTw5Oh2AxvC2Ip8B",-3971434217140396799,-2115906722850253438,5481466961078022771,-8276175323029350140>()
      );
   }

   public static void a(Canvas var0, String var1, float var2, float var3, float var4, int var5, String var6) {
      Font var7 = a(var6, var4);
      n.setColor(var5);
      float var8 = a(var0, var2);
      float var9 = b(var0, var3);
      if (!r(var6, var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zek6d4ib6n3p","SjSG/zNgHsxTIxpAXFSsSrYWxamQkjY9ANcGmj3rKCw=",-2238700376996260979,4316253877704267244,3024396627804049353,6313313242874380531>()) {
            case -289319960:
               var0.drawString(var1, var8, var9, var7, n);
               return;
            default:
               throw null;
         }
      } else {
         a(var0, var1, var8, var9, var4, var6, var7);
      }
   }

   private static float[] a(Canvas var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"skorcat2kpb1","Yk/0piFz5xmfv+LHbFLgc+VkiQZN7WnlAklNoPU58wM=",4539375367182153360,-1551932844063714754,8069406006107018726,-8544669273709727553>()) {
            case -870617473:
               return null;
            default:
               throw null;
         }
      } else {
         float[] var1 = var0.getLocalToDeviceAsMatrix33().getMat();
         if (!(Math.abs(var1[1]) > 0.001F)) {
            switch ((int)com.yiyiaddon.m.b.a<"stdf3izhhuch","TogOA2NiCAhlSwi+JaAzuVvC1lp636nR402VSO4qgJM=",2387800064044083673,-7378589447093733358,1129016820180691038,2490347417526831748>()) {
               case 2140873660:
                  if (!(Math.abs(var1[3]) > 0.001F)) {
                     float var2 = var1[0];
                     float var3 = var1[4];
                     if (var2 > 0.0F) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1mvapowii4u6o","cNrTmFomAYRXqP8rPfwyBO4w7R2j22gWD/AgZ569uDo=",8555474042273287182,-9148799280863354410,-4873160978050444631,8766502668119637350>()) {
                           case -1411222024:
                              if (var3 > 0.0F) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ur0kkrpi3svn","349NF0JYPD+Bhcnr9h3K15newrmSbKJY4FpIY8bouEI=",4833295068086394095,-1083165500650873439,1599044474489241093,8070724875600536676>()) {
                                    case -187265873:
                                       if (Float.isFinite(var2)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2co3dt50tw0xp","Nu399vf/MO+I+u6an2MJdmkdy+48oPQoflmxg6GznyA=",-5878572801334324395,2402034086879616910,-1836282745091337512,878876857991114422>()) {
                                             case -681128852:
                                                if (Float.isFinite(var3)) {
                                                   return var1;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s20w3ytloo09sh","6RIV3ZuDXm3PO2x9rE/JqngFTYdau2287cPOIPe6AF0=",-1530185461331789795,-5505980588144810239,-2034275892832558471,-8823480221764568473>()) {
                                                   case -1124405123:
                                                      return null;
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

                     return null;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s10vsh450wrhp","hTPO8aZBRJTDGj7xwZhnTNTZtqtdY+R/CyDqzh4jV/o=",-1855706959763975552,2112822239795619985,-2148683062938524301,-1550148013025613865>()) {
                        case 5704015:
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

   private static float a(Canvas var0, float var1) {
      float[] var2 = a(var0);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gmehbt8ybe6n","FQ79wThJYXZoGnVHPdPQwAwWgyacagxIevBCidnTnQU=",-268386219475715744,6148478023291422921,816199499907092878,-6647504025886039015>()) {
            case 1162744773:
               switch ((int)com.yiyiaddon.m.b.a<"s1vk7ja88cnnno","tj8+dQFsuLuAhnu7N80Ix4H/wOvCXlWnRIEtj0j5sSw=",5896313611350904691,1731039603661060105,-1042435439400080272,2477142555698032336>()) {
                  case 232093380:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var10000 = Math.round(var1 * var2[0]) / var2[0];
         switch ((int)com.yiyiaddon.m.b.a<"syutbaiov8abw","KDLAs/efYxJKHZEmF+rI+ss8QTDZ4+cDWFPHNdi01PU=",-6788765336938266805,-7052574450410724843,3172712623636142214,-8579153381973878557>()) {
            case -1454915041:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static float b(Canvas var0, float var1) {
      float[] var2 = a(var0);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gking5t02a7","kSAd14pcyR/taRbrZyAWHHzh5le6UeZh09UGW0CsAo8=",6872487051778620021,7949259307604067844,9122169803392273489,684688788875760121>()) {
            case 1228344653:
               switch ((int)com.yiyiaddon.m.b.a<"s1l8xl0kt37nuz","4IwRj8lEz30Y7J79OobDHzQKN9plnjTMdglu+4vBdqQ=",765560799875616692,-890745582481442218,-5264009512404474320,-41566146877759881>()) {
                  case -797896076:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var10000 = Math.round(var1 * var2[4]) / var2[4];
         switch ((int)com.yiyiaddon.m.b.a<"s2mctdho3mhsam","/BG4ZeucB8zi5BT80nbXsB9lLQGDq3oJd4XlqI28okg=",-1913742273285728996,-4293236603907018245,1072145693030424172,-6222063489666185395>()) {
            case 1204399829:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static void a(Canvas var0, String var1, float var2, float var3, float var4, String var5, Font var6) {
      float var7 = var2;
      int var8 = 0;
      int var9 = var1.length();
      switch ((int)com.yiyiaddon.m.b.a<"s3fhwe0f88nxtq","6JkiFj3PtPWLQXYwxCNyNEUVJxIpK5m9A18MnAPxkao=",6558536141692991790,-3415196646698544776,-1634801612140374927,-3444922196142625866>()) {
         case 1444472083:
            while (var8 < var9) {
               switch ((int)com.yiyiaddon.m.b.a<"s1zaz1xapprbow","uom44x/6Y6QgBi7imnDte9ZbPrmzdxOcrw2HyUnrzlw=",-3275250973928705911,-4914095440213594825,-2348033047687630516,-4150018469624160008>()) {
                  case 1321465791:
                     Font var10 = a(var1, var8, var4, var5, var6);
                     int var11 = var8;
                     var8 += Character.charCount(var1.codePointAt(var8));
                     switch ((int)com.yiyiaddon.m.b.a<"sg8jrdhhub07t","E44QLudpkzWBuCtTdFaKZcEqAAuVWncYStumJD9Igq8=",2342026908680915794,-4823619360027784274,-1320139081452318614,-6102729931816534586>()) {
                        case -226990553:
                           label47:
                           while (var8 < var9) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2xllsktxo8n2z","0KzEZaiiEdpORw15HN+2lbeTdEmgFHbdqEzwM0PkGlM=",-7005626618007041642,227167352222953481,7334030773677199142,-2687266219113134201>()) {
                                 case 1951726450:
                                    if (a(var1, var8, var4, var5, var6) != var10) {
                                       break label47;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3jl3zhgjxd5er","Jb62Xx+YXkwecAuRYRcHvmfPP5k6bf5CAqeTW3pzNas=",-4555913444799808417,4125731782358114789,-6359893825991412398,5441085997271148812>()) {
                                       case -975575657:
                                          var8 += Character.charCount(var1.codePointAt(var8));
                                          switch ((int)com.yiyiaddon.m.b.a<"sobg9ntgxwp1i","Dag9kuxI2NxcwTOhABKoW4t1nVYtRNctkW9Mf/yiyWk=",-1024593617536744043,-6779947815973012510,3959112166441503781,-5953591682289818484>()) {
                                             case -1757397648:
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

                           String var12 = var1.substring(var11, var8);
                           var0.drawString(var12, a(var0, var7), var3, var10, n);
                           var7 += var10.measureTextWidth(var12);
                           switch ((int)com.yiyiaddon.m.b.a<"s88m2g16hi5jz","1g4P1HGGsrrcW5uzqfAftWrElctVK1bTF6/SRYOeVWE=",-6088435428392564189,-3604005145058521131,-6464435639470491944,-1108678835090113109>()) {
                              case 72368561:
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

            return;
         default:
            throw null;
      }
   }

   private static Font a(String var0, int var1, float var2, String var3, Font var4) {
      int var5 = var0.codePointAt(var1);
      if (var5 > 127) {
         switch ((int)com.yiyiaddon.m.b.a<"s5ho941ad1ien","yo8YtV/cJKCfRAm9JY1ZRMqzODxU2JrxEvhBPHoRvk8=",129407473583097975,-2762729877040918298,-4972471106393184177,7310786471259336465>()) {
            case -1377195399:
               if (!a(var3, var4, var5)) {
                  Font var6 = a(var5, var2);
                  if (var6 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1owwqmcb5zvud","t5Ff1CLQL49ptoYR/+NZVD6vzUYHVfPJ1WWgr5pV5Yg=",3812404429020503461,-1518445823629991281,1319101591533665526,-5719808347103222355>()) {
                        case -58984979:
                           switch ((int)com.yiyiaddon.m.b.a<"s139cwyomybbn6","7cq3pVf3IwT06OAhPpB/PX8Bmtg1NCWzOxQLu8kHzCA=",-3777119842526053254,7730889496969689737,-8589404384373580366,7036987828999733311>()) {
                              case 299044089:
                                 return var4;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s60c87a3h3fnr","phNukytR6WEl4oJ7QtnszWAzDXgY1lEtlyRFh+iHvV4=",-5197551527991906107,8298869123124121958,7106941118225500675,-2475689146540164045>()) {
                        case 56582407:
                           return var6;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1i85gv5788sez","kDPqtOO/1Yo9mV+JwNhUue4BT2kzUawDS1X67EceCrc=",-8259162836198175426,-6948025865237438925,-3381150903960101308,-7230854383948629799>()) {
                     case 1971736755:
                        return var4;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var4;
      }
   }

   private static boolean a(String var0, Font var1, int var2) {
      String var3 = var0 + var2;
      Boolean var4 = bm.get(var3);
      if (var4 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sh6ssa665jopn","IJ+KFJIILoV/8bifr/5u9Ylo8yWPFawRk8luY/edlTc=",1141783994534430929,-1812223005524139692,-788834930889559401,-6286254266110162334>()) {
            case 2142951484:
               return var4;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         if (var1.getUTF32Glyph(var2) != 0) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"s1yi5z97qb7sw7","8PcsRAcJGQF/2moReStLOSgseaLdckbFvd69yhx+/9Y=",6212041589803900660,1382836497497433149,8187723895369979822,-7258792255313989037>()) {
               case 1578746139:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s2ilo99ezecmnp","FNA6RU4YWa27jZhdD5GwD/ozXrs897Q1RnAHzXyaKhI=",7355162964870103372,-5331701387203191811,-4301466496611002909,6282313986565683808>()) {
                     case 534522767:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s2ccfga7hm47no","U7hDl/sLKb5DbUjL3+RMJ4n4UUK59ZqijABrnQvZeUU=",-2885719751376745885,-2716637783356690892,-464060284884249496,4049210446102753918>()) {
               case 634145314:
                  break;
               default:
                  throw null;
            }
         }

         boolean var5 = var10000;
         bm.put(var3, var5);
         return var5;
      }
   }

   private static Font a(int var0, float var1) {
      if (bn.containsKey(var0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3w1yaq7uptnxd","b49NFLloQmugI3GuEKU3pJOVhPwge+xe3vMjwMmaizE=",-6071991266326609060,-1285557759986865686,-4961143726030275944,-2565643338157042516>()) {
            case -1919888502:
               Typeface var3 = bn.get(var0);
               if (var3 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jiamkm79r5g8","EyIrJv6RhF1nW2M9dwp4MkI2u8pTQ/P/pHp1ivyFKns=",8179479194260541543,-1487455691674409132,801004463776927302,2522084887047109339>()) {
                     case 1576688115:
                        switch ((int)com.yiyiaddon.m.b.a<"s2rvtodsgjb17o","jetGx9FrdMDF90kkP2ZtlMsHnQISyjjjdi1JzmJWMLI=",2074490308273435127,2759808574491223261,-2648305194419144111,-6946228638781673216>()) {
                           case 2087025133:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  Font var4 = a(var3, var1);
                  switch ((int)com.yiyiaddon.m.b.a<"s18st5e5xjzi0v","FRdXUauZsvEy80ftTS4CF1huRHl9wJeN6a6z89BfdzI=",-362168679702592704,7574934011437210826,-6594447657830834242,3199169647948961671>()) {
                     case 158438248:
                        return var4;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         Typeface var2 = a.matchFamilyStyleCharacter(null, FontStyle.NORMAL, null, var0);
         bn.put(var0, var2);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1svw9pllbm2w6","oPbsfGrJU98F9MsbHkqbsqzJlQDZbGp05NBfEpsdXc0=",-7829683603089444344,-5930904427841905893,-1463902376553514031,-6386678254906151087>()) {
               case -55712069:
                  switch ((int)com.yiyiaddon.m.b.a<"s1f8hwlhlpm201","YyoszHs58V4VcnNnGZ+i6aJfwBZuciiQILBWKGVpC7I=",6974323800029765023,-4536760682159269538,-5373005002156282620,5754742047162082921>()) {
                     case -1330634725:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            Font var10000 = a(var2, var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1ik6mopidhckf","lNW5TqYSUIILQQ1UwdIu40Iwnk2afE6A6DshrqGPRMc=",-1734127592167269602,-3691026045291038744,1701993823982258456,-173938207536898818>()) {
               case -1028881757:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   private static Font a(Typeface var0, float var1) {
      Integer var2 = Float.floatToIntBits(var1);
      Map var3 = bo.computeIfAbsent(var0, var0x -> new HashMap<>());
      Font var4 = (Font)var3.get(var2);
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ldqnhldaex3z","z/P/smJcXgLnCA/yohD7XRFP7RB6uz9Hgk1QWculto8=",1062824933864641611,-5868366243974572218,7134129313889473115,3172038361127086445>()) {
            case -1400608078:
               var4 = a(new Font(var0, var1));
               var3.put(var2, var4);
               switch ((int)com.yiyiaddon.m.b.a<"srwwsrtxfal3n","VCuNdiIy/QxY8foT/ZmKZZ8O35KyitV+otsEWWQ++Kc=",80113100780676447,-4304553986123503215,6118391448993948112,6913902296481829657>()) {
                  case -1809919430:
                     return var4;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var4;
      }
   }

   private static boolean r(String var0, String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sggy8bxfukni","eHX+qiLL+f3koT8O0PNvTVdiwwq2Qrhr5BbeD/N2t1U=",-2352469180604966181,-95628935974708051,-3432792674464496964,7445687921533742296>()) {
            case 425005601:
               return false;
            default:
               throw null;
         }
      } else {
         Font var2 = a(var0, 1.0F);
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"saz1cmxzx1zi1","xtnarQ2QqB3YGxLM08rccdM+o+/Td24JRBCpaVpDxa0=",1757016995554260848,1085665368197990742,-5651382653611842852,557084634237719970>()) {
            case 412483501:
               while (var3 < var1.length()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wgttl7ghiteb","XiZNgTbWhchmgz80eN9DUaLjVTzuijMOW3U25C4Bor4=",-9162313978592017621,-6258139553282699546,-8117253959793731161,-3508109083700063744>()) {
                     case 1200029902:
                        int var4 = var1.codePointAt(var3);
                        if (var4 > 127) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3sfnrpmufn5ur","7PQUZS87vOCEM0P8IIVyt3X6wsBbB1kk/s8NeHlCK0I=",-7973927229182333863,-8131133885707885612,2012723480103377947,7129956667118327101>()) {
                              case 228955858:
                                 if (!a(var0, var2, var4)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3a7blo7t0d66w","qA2YTK3WD72OczC/gg6luLFv2z0k2ZtBUi7MOzU7fhI=",-4309844518874480906,-8976636385184479737,6018964732554411417,1849747923241155928>()) {
                                       case 227937149:
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

                        var3 += Character.charCount(var4);
                        switch ((int)com.yiyiaddon.m.b.a<"sni3928xoxlv","rKWiV0u+1crvlpclya1tXlLxYZNT8FLcb+rLZ33prPo=",-3971263300158869321,-821389707833505489,7194245086136398720,1222711684909086161>()) {
                           case -128841728:
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

   public static void a(Canvas var0, com.yiyiaddon.l.g.a.a[] var1, float var2, float var3) {
      float var4 = var2;
      com.yiyiaddon.l.g.a.a[] var5 = var1;
      int var6 = var5.length;
      int var7 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1b8zoyd82fshq","186bhP945/z93aVwYwVgrJ/KOHEre/rPg2Xk3AWL2ek=",4246137880347936370,765223033017840963,7321379014444963321,-5274384423282191682>()) {
         case -1806128258:
            while (var7 < var6) {
               switch ((int)com.yiyiaddon.m.b.a<"s1dk5bq7zrezdo","HGMP/Mg03U3FeRKM7ISMhAdRT4ETFsJe6tUhit3tK7Y=",6901740249256618482,5742781214951372298,-8384588120612719621,-2958050249883171756>()) {
                  case 1244943286:
                     com.yiyiaddon.l.g.a.a var8 = var5[var7];
                     a(var0, var8.FV, var4, var3, var8.jU, var8.tJ, var8.FW);
                     var4 += a(var8.FV, var8.jU, var8.FW) + var8.jV;
                     var7++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1ck1yn5cx89tb","TCUF8NasxASy/6vuJVQ4/whPj1ZbHUQMYyRLmNl9cD0=",-3153098561550218824,-6905405595791379050,7958529508877645874,1451727675698842368>()) {
                        case 1515039510:
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

   public static void c(Canvas var0, String var1, float var2, float var3, float var4, int var5) {
      a(
         var0,
         var1,
         var2,
         var3,
         var4,
         var5,
         (String)com.yiyiaddon.m.b.a<"s3qb1w96ifqjmr","PaUbPVLiDxLt1SpZcJjOVvt8aS6gnMPeKNvu8PcYxDRcXZqvv6UtbFWWj+6u1O4CGqDhqg==",-1871580447823000303,-6484023911609091316,-716389596999787851,2221388835214379537>()
      );
   }

   public static float a(String var0, float var1) {
      return a(
         var0,
         var1,
         (String)com.yiyiaddon.m.b.a<"s3qb1w96ifqjmr","PaUbPVLiDxLt1SpZcJjOVvt8aS6gnMPeKNvu8PcYxDRcXZqvv6UtbFWWj+6u1O4CGqDhqg==",-1871580447823000303,-6484023911609091316,-716389596999787851,2221388835214379537>()
      );
   }

   public static float b(String var0, float var1) {
      return a(
         var0,
         var1,
         (String)com.yiyiaddon.m.b.a<"s16l7379kkw5bx","ghBe4RLkVER0ewVFqa6mcHbogGOgPGlso9vcgWG0mTw5Oh2AxvC2Ip8B",-3971434217140396799,-2115906722850253438,5481466961078022771,-8276175323029350140>()
      );
   }

   public static float a(String var0, float var1, String var2) {
      if (var0.length() <= 32) {
         switch ((int)com.yiyiaddon.m.b.a<"sk1ei6u4w1sk3","hJMOWPhEB4IvhySlFyLuviUHTDTy9M1b0bDDWqH1854=",4305729208393380026,-7126175209632838419,-7432931808666022660,4288255018608444707>()) {
            case -231190156:
               String var3 = a(var2, var1, var0);
               Float var4 = bl.get(var3);
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3es9filn02s6j","msvkA2Mokv/IxudpTGtBDoyJgl5JgEfCi5Kkp3WwhFg=",-2621742297523246780,1685670895177594857,5963828894061443635,7188603801126897721>()) {
                     case 1541938776:
                        return var4;
                     default:
                        throw null;
                  }
               }

               float var5 = b(var2, var1, var0);
               bl.put(var3, var5);
               return var5;
            default:
               throw null;
         }
      } else {
         return b(var2, var1, var0);
      }
   }

   private static float b(String var0, float var1, String var2) {
      Font var3 = a(var0, var1);
      if (!r(var0, var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rmx1008u1uqz","9U1OfHXXA8S9iSeR9RhXwD3UwncSsBpEHhvzytHC8xw=",3709581357653383228,-147999284321557350,4829202816402879070,-5504738274259898005>()) {
            case -1416157170:
               return var3.measureTextWidth(var2);
            default:
               throw null;
         }
      } else {
         float var4 = 0.0F;
         int var5 = 0;
         int var6 = var2.length();
         switch ((int)com.yiyiaddon.m.b.a<"s343hodp1suyus","EivrzIZRYQr+/UdIyiSoYtBcVESxWbhxhqdoplY3lVo=",-7509378060967287135,-560118174629374927,1679355161374078375,-3141023018071713805>()) {
            case -1112307609:
               while (var5 < var6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s18ccn3ov9mki8","y0gXIg6aPeG9Yl2HY0XjzF+/wMs+MoavSob6NNRtfnM=",-2249634333156551947,9082110355745450126,7217448915404334368,5774985621672547315>()) {
                     case -357505055:
                        Font var7 = a(var2, var5, var1, var0, var3);
                        int var8 = var5;
                        var5 += Character.charCount(var2.codePointAt(var5));
                        switch ((int)com.yiyiaddon.m.b.a<"se31n6a666q7w","lE+Nclag5QfTNxX4La8TOGTIyJaU+DitBnLFbtJ1vdc=",4536295796431623199,8338881662983125693,8817086861123433848,3588863691906978400>()) {
                           case 1729889704:
                              label54:
                              while (var5 < var6) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3iaodoeb1ko5h","fGXufGLm37J8KGj8medYWR+rKyVOZsIDi5DF5NW7xPw=",-492783800891674756,-6676561064198101394,4879496683740985396,5920205919441966647>()) {
                                    case -1338555918:
                                       if (a(var2, var5, var1, var0, var3) != var7) {
                                          break label54;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2xz6j1m36xg28","tHD/TGp8wT8Wx4Nqyp+rTC6PaOWtIXfO7fzJKVDA38M=",1434477879227432683,-4187911433320607803,2001483796890428481,-2259560128037802334>()) {
                                          case 716475714:
                                             var5 += Character.charCount(var2.codePointAt(var5));
                                             switch ((int)com.yiyiaddon.m.b.a<"s2w8jqg2z4hbe6","pa8S7/TlhyzniUGwTFEawYRelGnEv6RGvrJHI15KkBM=",4763620811523599081,357449318030272190,7712604356168203080,-3024228618391151115>()) {
                                                case -1733601870:
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

                              var4 += var7.measureTextWidth(var2.substring(var8, var5));
                              switch ((int)com.yiyiaddon.m.b.a<"s19me7eaf63hwg","YohzE8wyPKC8mMURGf81fJckf1myL5t2lK/8ibCnGiY=",5590169885036883704,823131460724802591,-6339702135332327480,3039939507747490523>()) {
                                 case -1266596667:
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

               return var4;
            default:
               throw null;
         }
      }
   }

   public static float p(float var0) {
      return b(
         var0,
         (String)com.yiyiaddon.m.b.a<"s16l7379kkw5bx","ghBe4RLkVER0ewVFqa6mcHbogGOgPGlso9vcgWG0mTw5Oh2AxvC2Ip8B",-3971434217140396799,-2115906722850253438,5481466961078022771,-8276175323029350140>()
      );
   }

   public static float q(float var0) {
      return a(
         var0,
         (String)com.yiyiaddon.m.b.a<"s16l7379kkw5bx","ghBe4RLkVER0ewVFqa6mcHbogGOgPGlso9vcgWG0mTw5Oh2AxvC2Ip8B",-3971434217140396799,-2115906722850253438,5481466961078022771,-8276175323029350140>()
      );
   }

   public static float a(float var0, String var1) {
      return -a(var1, var0).getMetrics().getAscent();
   }

   public static float b(float var0, String var1) {
      FontMetrics var2 = a(var1, var0).getMetrics();
      return -var2.getAscent() + var2.getDescent();
   }

   private static String a(String var0, float var1) {
      return var0 + Float.floatToIntBits(var1);
   }

   private static String a(String var0, float var1, String var2) {
      return a(var0, var1) + var2;
   }

   private static void bu(String var0) {
      bk.keySet().removeIf(var1 -> var1.startsWith(var0 + ""));
      bl.keySet().removeIf(var1 -> var1.startsWith(var0 + ""));
   }

   static {
      v(
         (String)com.yiyiaddon.m.b.a<"s16l7379kkw5bx","ghBe4RLkVER0ewVFqa6mcHbogGOgPGlso9vcgWG0mTw5Oh2AxvC2Ip8B",-3971434217140396799,-2115906722850253438,5481466961078022771,-8276175323029350140>(),
         (String)com.yiyiaddon.m.b.a<"s302s8azl56lt4","Mm7ARBNHQvhg7MCDJyZL3v2zd3QNakFYIys5c2DaHhhVlr3gkaMafI0f2FxLT283E8z/t1I/0GO0bG1b6GwbU/IDKn0fR7K+t1IC0w1A5CxQeX2fYGyZbdhix1DGjtc1Xvw=",-387328457721239945,-2536695693366673797,6095695112012832910,2746144029762165352>()
      );
      v(
         (String)com.yiyiaddon.m.b.a<"s3qb1w96ifqjmr","PaUbPVLiDxLt1SpZcJjOVvt8aS6gnMPeKNvu8PcYxDRcXZqvv6UtbFWWj+6u1O4CGqDhqg==",-1871580447823000303,-6484023911609091316,-716389596999787851,2221388835214379537>(),
         (String)com.yiyiaddon.m.b.a<"s1iw4djo9hgh4n","9wUGimaVhOMww/lNyegVQ2LCr5Oth00/gK1Ma4xAu2EfzE/XO5Ect3HOQixXoNBtDj/JOqBOz4ItvJgnewIFLSLvsDa3JY4nnywir6tqOZAwT33l52p5V2FhhN8SbHoCpPYnAqqKSncMZjWg",5081113169684816871,8481249998654808920,-993720292166623336,909950368953326788>()
      );
      v(
         (String)com.yiyiaddon.m.b.a<"s1d5yardi1mbwi","Esch3Smub9Trj3hKsBiEmRKy3M3xSg+Lnjj6H+FCth7NfnLH",6014299772099751264,7486097552532674766,-3893404101385833468,-2576569559874922018>(),
         (String)com.yiyiaddon.m.b.a<"solufnbtn0l77","WtJI93dDQ7CLqv5JFH7fCBULEKrjaQzscO8wPKK9PxDO03AEdAFBaUDnvOCeeqvHjUvHVPVrdZcRPwuiJjsPeh5L4BElGJSMvRig0+iYRCasI0dkCRq2yBC7oLo=",1465604285410389805,3449894123210961959,-2881877003319757650,-6690371507143609634>()
      );
      v(
         (String)com.yiyiaddon.m.b.a<"s3b5vyz1c17wta","9CVC4BpYfp8PHfM99/XSzxva9hWuHZBk7yYax+fHJO/V4D0WcOaL81k0/c77gt2Lw9ypYXWfAs20Jpxw",8447755864594045885,2098065068622377218,3508423342100734101,5562293499657854323>(),
         (String)com.yiyiaddon.m.b.a<"sbvv4scdwkhfd","WFugcyBaYmpDCnstxmXeTsZUKjDCehnpyo285e2z7L/1n+5Bwfq40SKaFtKtmBJXwYTbQySIAxY/U8GDQUhSaNr6OpmEHWpe4Gg/xd2pU1s+AlUadCfT2aWz/26S89IyfU6MWtxjsq6suf7I45Jpw+1S8fjn+01AZq6PGdf1gak=",-7098622047507760013,2196098477499005450,-8316832950262197233,6703941519297198532>()
      );
   }

   public static class a {
      public final String FV;
      public final float jU;
      public final int tJ;
      public final String FW;
      public final float jV;

      public a(String var1, float var2, int var3) {
         this(
            var1,
            var2,
            var3,
            (String)com.yiyiaddon.m.b.a<"s27jpycxxjdr37","ZV50JOnrz69mIn/8zL9cfqmDX7x1IVtqKL498u1lCIvVB3TNr55We7L/",6224482899654444838,-3526803078056462971,7184481374396927651,-2062562667230157815>(),
            0.0F
         );
      }

      public a(String var1, float var2, int var3, String var4) {
         this(var1, var2, var3, var4, 0.0F);
      }

      public a(String var1, float var2, int var3, String var4, float var5) {
         this.FV = var1;
         this.jU = var2;
         this.tJ = var3;
         this.FW = var4;
         this.jV = var5;
      }
   }
}
