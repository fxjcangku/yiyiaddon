package com.yiyiaddon.l.g.a;

import com.google.gson.JsonObject;
import java.awt.Color;

public final class d {
   private static final String Gb = (String)com.yiyiaddon.m.b.a<"s2k2bqsn218yuo","qMsGd5ho8WDI8jUq6iJytPUoVI63qMwb3PDFOs4yzJjOew==",-5959022278477772156,8506792885802084962,4612339259464842823,-7163026879355362421>();
   private static final String Gc = (String)com.yiyiaddon.m.b.a<"s2c0qi0rqppbf6","877rW2lUwPbtl4GkPDdVQ11I5AJ8E9BHz9N2GImR/r/A/wb2sZs=",1031156218878707114,-3071628267476196588,-5095510612304826146,-1567168121504417183>();
   private static final String Gd = (String)com.yiyiaddon.m.b.a<"s2a2u45si85g7f","0EQmA6BnAiqtegDG3NiLyp6VfiD65OD/O3V8Y8wfk0sJBFjmkSWO3n/w",-6243105236109344899,6354078357925121389,-5416488345906123509,1023252614906904425>();
   private static final String Ge = (String)com.yiyiaddon.m.b.a<"s1g59lqk00wstb","KSb9Jo7muLL/KQiK8ZBmOmuq8VsTGf7K7GGZ42YGHIsTC8Jj8dB2IHScUw8/kOYw33Z+DQ==",6771697783113151593,-8979008025009627986,-5246269385512599434,-4929564847963437580>();
   private static final String Gf = (String)com.yiyiaddon.m.b.a<"s2v9l8ejcrcbuq","4hd20O2O+pqwglD+JuwxJtcvqvZi1XLSa7DL51ipJTFri/KZksY+DPdYCpUhAms5rsP8EcCk",-5225643210869871981,-7429220190906389205,-8214032271773160391,8653178380867076087>();
   private int ul = b.c(0, 255) & 16777215;
   private int um = 255;
   private boolean gc;
   private double bB = 0.125;
   private double bC;

   public d() {
   }

   public d(int var1, int var2) {
      this.ul = var1 & 16777215;
      this.um = var2 & 0xFF;
   }

   public static d a(int var0, int var1) {
      return new d(b.c(var0, 255), var1);
   }

   public int eh() {
      return this.ul;
   }

   public d b(int var1) {
      this.ul = var1 & 16777215;
      return this;
   }

   public int ei() {
      return this.um;
   }

   public d c(int var1) {
      this.um = var1 & 0xFF;
      return this;
   }

   public boolean gj() {
      return this.gc;
   }

   public d a(boolean var1) {
      this.gc = var1;
      return this;
   }

   public double l() {
      return this.bB;
   }

   public d a(double var1) {
      this.bB = g.b(var1);
      return this;
   }

   public double m() {
      return this.bC;
   }

   public d b(double var1) {
      this.bC = var1;
      return this;
   }

   public int ej() {
      if (this.gc) {
         switch ((int)com.yiyiaddon.m.b.a<"seou3b0zjp9iw","vUEYKtsDemKWNzPPUUogl9lKwXllyRQl98Bc6W2QSVE=",-8703584440478816023,6206633715530575636,5615103304810389671,1444763998979859038>()) {
            case -869317298:
               return g.a(this.bB, this.bC, this.um);
            default:
               throw null;
         }
      } else {
         return (this.um & 0xFF) << 24 | this.ul;
      }
   }

   public int ek() {
      return this.ej() & 16777215;
   }

   public float W() {
      int var10000;
      if (this.gc) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1vd5xzxzszyap","LcfZN7py3I3ZnCBpOT6UxWvDMZIEkiDKQ/yi1OQR8f8=",-3672301012271380540,-2512362735755665125,897739224472163002,-3491223852353906672>()) {
            case -655547415:
               var10000 = this.ek();
               switch ((int)com.yiyiaddon.m.b.a<"s3p1n6tirkbuli","f99JYzpV5Ml6bB8LbS1KzLtIuG4U6gh1lK8cjzrKGis=",-1998399488300316021,-2848673683101781442,480702003543864560,8415988294722623>()) {
                  case -1703559168:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.ul;
         switch ((int)com.yiyiaddon.m.b.a<"s1mp4b4g0dcry5","ZvGEZIcmRN5pegIzyDdVTFTc4mMVT8LQ5ecnR2ctycc=",2707008408928742737,1362780516685369806,2746146331807729375,7856400108831672265>()) {
            case 46958282:
               break;
            default:
               throw null;
         }
      }

      int var1 = var10000;
      float[] var2 = new float[3];
      Color.RGBtoHSB(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, var2);
      return var2[0];
   }

   public float X() {
      int var10000;
      if (this.gc) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2zg4oz4ie38mf","bAPk6mpLmgJDy0XENl5XuoZGg8rt1AyRhjIJojHkjGU=",5470588698098971243,-7284065304972869431,2485963505199083304,1511637205616104369>()) {
            case 472000468:
               var10000 = this.ek();
               switch ((int)com.yiyiaddon.m.b.a<"s8noan7g6ttt8","xuXzi4X0C5wvYK744TPq95EV6vjaLG8ZKZmR2vnUKhQ=",-7547272027610345645,-1593433210350312500,7684493391805192277,-8405786970301048647>()) {
                  case -131547446:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.ul;
         switch ((int)com.yiyiaddon.m.b.a<"s32tn1ku5fc7vs","l8RIzOC1JMq5Sg2UM6u7LkMFB7MO00S1SX+X8ld8ldQ=",7751358070802480223,2725892354749199342,-5313700787079632813,-3231672517247629289>()) {
            case 383327499:
               break;
            default:
               throw null;
         }
      }

      int var1 = var10000;
      float[] var2 = new float[3];
      Color.RGBtoHSB(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, var2);
      return var2[1];
   }

   public float Y() {
      int var10000;
      if (this.gc) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3r6nzw65the1c","aBrc85MlQ1pkFiOwKSlPbihtXAEAJHp/AipA/X6Vh2E=",6046750896003278620,628918228514899174,8692271301080361929,8471292149684197562>()) {
            case -26522705:
               var10000 = this.ek();
               switch ((int)com.yiyiaddon.m.b.a<"s1ghukd9qbg329","nk0LClTb+s2qo42CfKskOWHRAOocELPhzR1PLF1EzG8=",-1789248471863972379,4263567542652739406,3477557286507519909,-886433608053949588>()) {
                  case 1907094382:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.ul;
         switch ((int)com.yiyiaddon.m.b.a<"s9u91kujt6dpb","3JJiuJWGPINHJLx0Cfnxc2LoioCMPCSHg/KTJ+esvZ4=",-79740387088989775,-179499196789399283,-6637503271756770419,-4125605705044839318>()) {
            case -1885421731:
               break;
            default:
               throw null;
         }
      }

      int var1 = var10000;
      float[] var2 = new float[3];
      Color.RGBtoHSB(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, var2);
      return var2[2];
   }

   public void a(float var1, float var2, float var3, boolean var4) {
      int var5 = Color.HSBtoRGB(d(var1), d(var2), d(var3)) & 16777215;
      this.ul = var5;
      if (!var4) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rpiv0kqi0qxf","xNWpv70t5HGKt8sckJNmAQYX1ozWWhnVabPGs6TREBs=",-1788920234572392160,-5328395928428056133,4578482168476524175,7482450180159312757>()) {
            case 1463435027:
               this.gc = false;
               switch ((int)com.yiyiaddon.m.b.a<"s1ocbp1y2i8yi2","huHZJNnf45MwqNt1eoO3q8sif8U0Jxyg2DRVkOwZhHw=",-3389851232686404367,5939295000267650335,-3899007131353905667,686230009360270653>()) {
                  case -168156206:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static float d(float var0) {
      if (var0 < 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jxwgszpgs7a8","JQApgeBTX/IxR5kCOZpA0TQi5YosJ2FhjD3KHV3UzhU=",-6418861455319803775,1302943062789988401,1557068155550026413,4437220343505453212>()) {
            case -1780299589:
               return 0.0F;
            default:
               throw null;
         }
      } else if (var0 > 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2iw3kro3zayzo","7CGETJbOGbpryuvs8IEXAoEsCH15ZSq6ng1jYaFip/Y=",8960211676672658506,3443074831644800540,2459381669556792968,694076619439214447>()) {
            case 1975015846:
               return 1.0F;
            default:
               throw null;
         }
      } else {
         return var0;
      }
   }

   public void b(JsonObject var1, String var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33cxvi54242ks","8WbEWF8oIGjb2nYe40Zz1Wz+eiSnx1uGQLLR2zIMLK8=",-1124224892949049716,2832211414698665695,5150960212457917604,6505072322043981312>()) {
            case 98975772:
               if (var2 != null) {
                  if (var1.has(var2 + "")) {
                     label60:
                     switch ((int)com.yiyiaddon.m.b.a<"s3qyuvfm7fwhk2","aMqWO4tdHdJn4Ux4tO/P8BLth/SlrPfahWF9FOR+y+c=",5191286438560968637,-2715888920383927021,3271444355091758863,-1926808347597914970>()) {
                        case -1072714027:
                           this.ul = var1.get(var2 + "").getAsInt() & 16777215;
                           switch ((int)com.yiyiaddon.m.b.a<"s1rt12kgde8yn7","O3RznpWfLiEt40zhLgTT/2BMkp23V7SFbsgCtidgpZs=",1242707658223387126,6635402523970235605,8159746727637013226,149443986535604927>()) {
                              case 776679586:
                                 break label60;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.has(var2 + "")) {
                     label55:
                     switch ((int)com.yiyiaddon.m.b.a<"s1iz49azprqe58","sMNOkqFkn6OuvCuQnBZl2P2qLjOSz7M4mWc9P5hgOKs=",-5053802758801395470,-5606168032191498413,-588529992129698524,8370942023580428341>()) {
                        case -447113316:
                           this.um = var1.get(var2 + "").getAsInt() & 0xFF;
                           switch ((int)com.yiyiaddon.m.b.a<"s1kqk90mj92up7","CnTH7SEEz8TDjpv8X+QeVkpi0BxeFGOWn7rfWMrS6VE=",-8916459303400040454,-2395675959334289707,-6096131477468798296,-3506693454841240461>()) {
                              case -1493395079:
                                 break label55;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.has(var2 + "")) {
                     label50:
                     switch ((int)com.yiyiaddon.m.b.a<"s26i1tf45xtrgx","WY/IRjahLPnY3UVrB0zlVMQFXU6rn2J8KGV5iFreByw=",-6069123743189831009,-1941520175832062173,5832663457282657582,-1374184007402859289>()) {
                        case -1276697940:
                           this.gc = var1.get(var2 + "").getAsBoolean();
                           switch ((int)com.yiyiaddon.m.b.a<"s12xhcwwv5qh60","Pb2e7LoaZPJUTnGCR8sH3esL4YA02ft4K6GnyXeBeqc=",4466738515825681916,-1004382370847701688,-8386952808878829822,2616442110893247319>()) {
                              case -601894671:
                                 break label50;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.has(var2 + "")) {
                     label45:
                     switch ((int)com.yiyiaddon.m.b.a<"s1hyr3d1nbybra","6ZcY0GEsyisASp0Au0NLaClVgVIvZUKxcznRvlfaKDc=",5434878593785632951,2099661825899631211,8529776459763036596,3302342689077408837>()) {
                        case 1049822902:
                           this.bB = g.b(var1.get(var2 + "").getAsDouble());
                           switch ((int)com.yiyiaddon.m.b.a<"s77ruokcz33ik","liTlS9PCqT6wh3zXTwO1q/gBhm92ncxn82578PrpEOk=",-435058140413741758,-8148277636233059274,2323428572468214499,-8088895884017956225>()) {
                              case 745344025:
                                 break label45;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.has(var2 + "")) {
                     switch ((int)com.yiyiaddon.m.b.a<"si61qy9y8hyld","v4gsJwQpJ/8B989J7HvUP3freuV0G3LZU/+gnK9BePI=",4731287789518222819,7011924541653297275,1593273125472538960,-4885985611160373743>()) {
                        case 1204827139:
                           this.bC = var1.get(var2 + "").getAsDouble();
                           switch ((int)com.yiyiaddon.m.b.a<"s1sxae2ntr793p","eyyc1bYrWqP2pLqScnL77NpMA39B0jQsS9OpuPw3i64=",-849626803048961178,-6766141452506694016,1928482679031653500,-8635555740057262315>()) {
                              case 1802888088:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s15mek4gomp4dh","Jp7+AZou9AGrzW/QloE9f89XMbcBmWTgW6vRo8aSJXI=",6023455521611648243,663368556065054067,-7887907410232454067,-3378906973461121340>()) {
                     case 434727860:
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

   public void c(JsonObject var1, String var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lzwriy1l31s1","ytdD6XX/VdZAc8xxH4t3wdeaA9zM4k9ARe/dvTherIc=",617121578551596438,-5765025312760168856,-3683327895214709465,7539895872385827957>()) {
            case 1516766180:
               if (var2 != null) {
                  var1.addProperty(var2 + "", this.ul);
                  var1.addProperty(var2 + "", this.um);
                  var1.addProperty(var2 + "", this.gc);
                  var1.addProperty(var2 + "", this.bB);
                  var1.addProperty(var2 + "", this.bC);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s6nnjeqfcti6s","LtXMy2P5H2WTb8dsu0zQMP3IXiBZJSjrfxrAu6xOpWs=",8118178125216279343,508526957068911108,-8203895690658975106,-1262059309239636172>()) {
                     case -2066099584:
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
}
