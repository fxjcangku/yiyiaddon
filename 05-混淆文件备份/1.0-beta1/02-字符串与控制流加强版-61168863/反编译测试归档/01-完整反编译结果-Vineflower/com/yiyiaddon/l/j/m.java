package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.Rect;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.PreeditEvent;
import org.lwjgl.glfw.GLFW;

public class m extends p {
   protected static m d;
   private static final float oe = 0.15F;
   private final Supplier<String> N;
   private final Consumer<String> x;
   private final int vQ;
   private final Paint P = new Paint().setAntiAlias(true);
   private final Paint Q = new Paint().setAntiAlias(true);
   private final Paint R = new Paint().setAntiAlias(true);
   private final Paint S = new Paint().setAntiAlias(true);
   private float gk = 150.0F;
   private float of;
   private float og;
   private float oh;
   private int aL;
   private int vR = -1;
   private float oi;
   private float oj;

   public m(Supplier<String> var1, Consumer<String> var2, int var3) {
      this.N = var1;
      this.x = var2;
      this.vQ = Math.max(1, var3);
   }

   public m a(float var1) {
      this.gk = Math.max(1.0F, var1);
      return this;
   }

   @Override
   public float c() {
      return this.gk;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      boolean var10000;
      if (d == this) {
         label106:
         switch ((int)com.yiyiaddon.m.b.a<"s11d443hqidsck","mCQ6XzeG/+XDRwP38GlSuM8OBcTZrqV+3ny6S0k44/w=",8299280054326637178,-138838924655253787,2336370810503931283,687554709400671686>()) {
            case -2081383015:
               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s180g33yj382o3","ms6e8majrU5Bmn2kEEFVIPLP4NhuDaf5fr+eLoedanQ=",-7987799382195982433,2126010221999807327,3210095413237721014,4759597781994090568>()) {
                  case -1406168153:
                     break label106;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s3rb6fq0k6cvfb","/B+Bt7pjA+rM9spWj8SC0oLHpTEyqaCaokfjdzfGwZE=",5541354016495268773,-2272072831515650914,-4433494346107738202,6021229351432255441>()) {
            case 1318233260:
               break;
            default:
               throw null;
         }
      }

      boolean var5 = var10000;
      this.oi = var2;
      this.oj = var3;
      if (var5) {
         label101:
         switch ((int)com.yiyiaddon.m.b.a<"s19hh0s6fhssh9","TqvOePY0OlzyKApyEvW3TTNFFJCasFUI41Yu3JFPxkk=",5895348553464806762,7909129939846358854,5940207999986143800,-1214796084173653389>()) {
            case 178057696:
               com.yiyiaddon.l.g.b.a(var2, var3, this.c(), this.d());
               switch ((int)com.yiyiaddon.m.b.a<"ssyft9sqp0i38","je6Vy2zrO3KTQPgUNAUYtUCZef27pD0LnRVR5R/qIE4=",-1906285050969393744,-1519803160686454528,3297104983888209248,-8322850436012093481>()) {
                  case 1578467724:
                     break label101;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
      float var7 = com.yiyiaddon.l.b.j.h(this.d());
      com.yiyiaddon.l.b.j.a(var1, var2, var3, this.c(), this.d(), var7, var6, this.of, var4);
      String var8 = this.gV();
      this.bD(var8);
      boolean var9 = var8.isEmpty();
      String var23;
      if (var9) {
         label94:
         switch ((int)com.yiyiaddon.m.b.a<"s3lrn8aa4cye7c","ZSdUE/E3zGGlnTTrH28JWulqC4BvAAVBjQwTTQcU62M=",-5228719162539146706,-1553478355884826183,-3422732671812348048,3932542962451269432>()) {
            case -265866103:
               var23 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"sq39h7g77wi1p","pnCnr7H1v3veU0/ycFap4tRnVv4hKT9s/tsaD0ulZzaYje1B",-1077265529133515659,-8194566280782571915,-8727033204212209047,-159914815923987609>(),
                  (String)com.yiyiaddon.m.b.a<"s31aod0uvb8fs2","PtwzGjpBADn5ZyVb4erkuJlgZySlvhwinoQBSbC5mkR2R0nNSpQIhXCSAyyWI63iEukFcqcd",4979641751177254782,-2241252559918293673,6612445492474735423,5209558442157852446>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"sssztl8c09lh9","c5WWqjmFEVt84gJF5HrHrFv742KhBTawSqgQeHCyNUg=",3203127168596304235,4289805860628902032,5092597688884135729,9014321346451966178>()) {
                  case -130821165:
                     break label94;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var23 = var8;
         switch ((int)com.yiyiaddon.m.b.a<"s30ng7sry4zv6e","1Dt9ObDFXMG+J8qgDDqNTtMqSkg5fyjXYvq7/JUXWk8=",816055107026538079,-4772980559507428872,-1389816468345165868,-3209893331487213802>()) {
            case -244770749:
               break;
            default:
               throw null;
         }
      }

      String var10 = var23;
      float var11 = var2 + 9.0F;
      float var12 = this.c() - 18.0F;
      float var13 = com.yiyiaddon.l.g.a.b(var8, 10.0F);
      float var14 = com.yiyiaddon.l.g.a.b(var8.substring(0, this.aL), 10.0F);
      float var24;
      if (var5) {
         label87:
         switch ((int)com.yiyiaddon.m.b.a<"sl0giger1h6ub","ArdIDPiBq9IQYjqU6uyxrsjKKY5naIfHacgufVZ0MBk=",-5631535478895542367,4508306748298382657,-1475252833851754787,6236370293782475055>()) {
            case 1004279950:
               var24 = Math.max(0.0F, var14 - var12 + 3.0F);
               switch ((int)com.yiyiaddon.m.b.a<"snlwfl6zv2g7z","NZbjPKSk5UE2lfC1LsMrGixbI2EK9VYHMQzkAiFEYgY=",-3008384882861408997,3552407029074610386,3683341329132683856,-7231255910118188934>()) {
                  case -739013975:
                     break label87;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var24 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s21fcwkxcz9x55","SXYw62NWbz2pNydaFP5+qa2XZAG/QoJ9+CGW+x/WAPg=",-5620665632342061325,8205129073942233418,5123972203349487053,-2601349725180034369>()) {
            case 542871196:
               break;
            default:
               throw null;
         }
      }

      float var15 = var24;
      this.og = this.og + (var15 - this.og) * 0.22F;
      var1.save();
      var1.clipRect(Rect.makeXYWH(var11, var3 + 2.0F, var12, this.d() - 4.0F));
      if (var5) {
         switch ((int)com.yiyiaddon.m.b.a<"s3b3ojv5lfpoe5","tl7cd3EBqhj3jpR4zMR42XuSBtsRHZ0TxW6wbvH+yG0=",8227099892463465816,-8999891005435717096,-8226537349231881051,8539962931842276580>()) {
            case -1343895768:
               int var16 = this.eG();
               int var17 = this.eH();
               if (var16 != var17) {
                  label80:
                  switch ((int)com.yiyiaddon.m.b.a<"s1458eq4a270ez","ap4eCXMd6uV3XQynXjPYdzwr/b8g4KhcuvalVbOxqXc=",3892388419835907024,-6142196345462826273,5838431132870273630,-2548987333706402689>()) {
                     case -411676711:
                        float var18 = var11 + com.yiyiaddon.l.g.a.b(var8.substring(0, var16), 10.0F) - this.og;
                        float var19 = com.yiyiaddon.l.g.a.b(var8.substring(var16, var17), 10.0F);
                        this.Q.setColor(a(var6.uS, var4 * 0.72F));
                        var1.drawRect(Rect.makeXYWH(var18, var3 + 4.0F, var19, 16.0F), this.Q);
                        switch ((int)com.yiyiaddon.m.b.a<"sl1zhi4jwx5pc","jb2JoQB36xj3Ac8pF6K+ZdmDSqQGJ99W4HqOKljuaB4=",-9134767295390062512,5914689980111485336,-6449010292596374056,5339555802499726010>()) {
                           case 85966789:
                              break label80;
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

      float var10003;
      if (var9) {
         label73:
         switch ((int)com.yiyiaddon.m.b.a<"s5ag0ze2i13hp","MG6mkB5E6j9YNWTeTNtOrU1zMWVNPBHroKErdYsBDxM=",-1734567814686359465,6030212256404192510,-4931577260111811578,8701708376718060247>()) {
            case 1431078361:
               var10003 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"smkxrdcrnxj62","ItzawCcRuLtfc/Xdsz1u4qWq2YiHuB2V1NAf9dnOIbQ=",-1172318042373895647,9109780124937826872,-794740243099310958,8826973587336982570>()) {
                  case -571312642:
                     break label73;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = this.og;
         switch ((int)com.yiyiaddon.m.b.a<"s2gpk2s25ytakn","TCP/EVvsPRV+hplMN1Ojtq2a1MTQFRPR5zC2IjuRBmY=",7225771419251752976,1873396226205494500,2902601121793868413,2199937547987396855>()) {
            case -893613120:
               break;
            default:
               throw null;
         }
      }

      float var10002 = var11 - var10003;
      var10003 = var3 + 15.5F;
      int var10005;
      if (var9) {
         label66:
         switch ((int)com.yiyiaddon.m.b.a<"s2dhlphvcko4pf","lHnlDtLd8grspd2GJyfJ3JdNmQvv0Hu6xf5OUnCXYxo=",4324453389180390873,-6112470786015970546,-6932472612069839298,2497149907110924670>()) {
            case -952800504:
               var10005 = var6.vr;
               switch ((int)com.yiyiaddon.m.b.a<"s1nv3ni9wu1r15","WFkn2Al2U9OvyE1voUJOMeuNuMndf7Vj7wXUyIkppm4=",8807208722702185888,-1309005557191082456,-6277026223752456832,-814059161670619052>()) {
                  case -240034993:
                     break label66;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10005 = var6.vq;
         switch ((int)com.yiyiaddon.m.b.a<"s2ggxl29swuqfk","O3rRXMbuc/esDQM04oAmln+3lJNbgj4l1JiI3knfs+Q=",6056166266860548809,1143663842175526027,-9128355455004699624,-3244602792549970050>()) {
            case -1848363067:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.a.b(var1, var10, var10002, var10003, 10.0F, a(var10005, var4));
      if (var5) {
         label61:
         switch ((int)com.yiyiaddon.m.b.a<"s2xvvg51y3t4di","eote2vfpGmiX1BNk88DYA8ZWAbsTXdVu5QW20NAeLzc=",2759062228882352012,5553759732200961660,-9042217946799420687,6486788692352080860>()) {
            case 1594766831:
               float var20 = 0.35F + 0.65F * (0.5F + 0.5F * (float)Math.sin(this.oh * 6.0F));
               float var22 = var11 + Math.min(var12 - 1.0F, Math.max(0.0F, var14 - this.og));
               this.R.setColor(a(var6.vp, var4 * var20));
               var1.drawRect(Rect.makeXYWH(var22, var3 + 5.0F, 1.0F, 14.0F), this.R);
               this.d(var1, var11, var3, var12, var22, var4, var6);
               switch ((int)com.yiyiaddon.m.b.a<"s1tnedictycmql","VfkgnzG7Qcc3ZGfHr/BhzxfJGKzWuoF6MFMlSJsfHcI=",7919674612929308777,3962349659377566546,-4774450799310388810,-4262256790140049019>()) {
                  case -404518560:
                     break label61;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.restore();
      float var21 = 0.3F + 0.7F * (0.5F + 0.5F * (float)Math.sin(this.oh * 6.0F));
      this.P.setColor(a(var6.vp, var4 * this.of * var21));
      var1.drawRect(Rect.makeXYWH(var2 + 8.0F, var3 + this.d() - 2.0F, this.c() - 16.0F, 1.0F), this.P);
      com.yiyiaddon.l.b.j.b(var1, var2, var3, this.c(), this.d(), var7, var6.uS, var4 * this.of);
   }

   private void d(Canvas var1, float var2, float var3, float var4, float var5, float var6, com.yiyiaddon.l.i.c var7) {
      String var8 = com.yiyiaddon.l.g.b.gP();
      if (var8 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1oe1qwg963gwk","gZHaunA1z3STE0ZJwWU8GYuht65eqW7GUYTDL2Qk2nM=",1034234185944292740,-7351964257571531406,9032690630531282189,3929349093432099357>()) {
            case -1326178269:
               return;
            default:
               throw null;
         }
      } else {
         float var9 = var2 + var4 - var5;
         if (var9 <= 1.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s2i88ye5hzsd3y","hmnvqj15mAOZK0DNSwL7YqytudF//+LecPJjp5Ri9ck=",-8805169299609414115,-6784613819080893147,6614935441537984176,2665584568981229457>()) {
               case 1569605268:
                  return;
               default:
                  throw null;
            }
         } else {
            float var10 = 10.0F;
            var1.save();
            var1.clipRect(Rect.makeXYWH(var5, var3 + 2.0F, var9, this.d() - 4.0F));
            com.yiyiaddon.l.g.a.b(var1, var8, var5, var3 + 15.5F, var10, a(var7.vq, var6));
            float var11 = Math.min(var9, com.yiyiaddon.l.g.a.b(var8, var10));
            this.S.setColor(a(var7.uS, var6 * 0.85F));
            var1.drawRect(Rect.makeXYWH(var5, var3 + this.d() - 5.0F, var11, 1.0F), this.S);
            var1.restore();
         }
      }
   }

   @Override
   public boolean cB() {
      if (d != this) {
         switch ((int)com.yiyiaddon.m.b.a<"spy138hmtp2tl","BuncaFCCe+H2SvaYme8Pu2x/UR6m8GkdO/AKpHpiZiM=",3726019067530067725,-7862224270249736616,7122045813129612263,4915126808647128662>()) {
            case -452230685:
               if (!(this.of > 0.01F)) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s3h7a3cruv27uy","rS+C21WoMdXnZ7WyypZXBnf+Qr0BIU0LQPRtdWxcdJ8=",-1120213323267560954,7816631581422796647,-420249667652660975,-3153412126206130114>()) {
                     case 1326633068:
                        if (!(Math.abs(this.og) > 0.01F)) {
                           switch ((int)com.yiyiaddon.m.b.a<"sa8iethac2yvq","rleiJ3eIK8j3qdR68OU0FYXbA0bfqkHf93Wx/sTbQiE=",-9108120983326339821,8027675738380628887,-5993491533273657062,-8885488149464361334>()) {
                              case 1272324831:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2db1967idoeww","DzrIjZiEFosoT3qWsry6ffKoTHqoXgJMfJ3EY5p5KBw=",5694606905476093027,7661546877448329081,-8525777429273881123,1990872094076483121>()) {
                           case -355855011:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"szcxqjmkwpt36","4wO4tGdgaPZI291bktN3VPwbJX++XBcTp4kdSZpO6cM=",727786709680987306,-4382162478703057435,1754736032029667504,-1059541630819435389>()) {
         case 564764174:
            return true;
         default:
            throw null;
      }
   }

   @Override
   public void a(float var1) {
      this.oh += var1;
      float var10001 = this.of;
      float var10002;
      if (d == this) {
         label25:
         switch ((int)com.yiyiaddon.m.b.a<"s1887u1tqd0rf5","IkLu59tf9b2TPg6Z38bJSz+N9T7mrnuKiFed50HbE1Y=",4122962611366858816,8321316987446864837,-8902094709204406108,6015290469932919228>()) {
            case -1909008837:
               var10002 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s37z2h90ymuwfe","lptbaGDJvxlHcgZA+75Aw4S0HaULyCgzA/V3xLFBnnA=",7466712156324960394,9096632047631900891,6885141255931288738,-3203355400739323819>()) {
                  case -1811704588:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s9txvrwqf9w1s","rqsVcC9HSbVVNWXRco+zIuPnV7jZYwbm5wob5wLs4lo=",3373115916375996863,6174295846417997114,8112132179524508513,-3469510613599132117>()) {
            case -1709472343:
               break;
            default:
               throw null;
         }
      }

      this.of = var10001 + (var10002 - this.of) * Math.min(1.0F, var1 / 0.15F);
      if (this.of < 0.001F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ovv7uxti18k8","rMC3dd02Z28JBh2s5eqXnPK5eOWOO6MjRnqEvhED4I8=",9162139265825327734,-569494862609365905,4741834967856299569,7494754320036906336>()) {
            case 1350840043:
               this.of = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s3609ta5js7lhh","VVEupPoBz6Xf0EXFNbpAM3vqrd5/0R7XQInTddBfBOA=",-6370689757025798151,7343106935480220451,-278918344291532340,-4838320300500476553>()) {
                  case -1550698628:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s128thwbxdqzi","eWdxGeezUG/WDYs7TkQqG/lW1aY3Cz1GC4CmA+WU3qw=",-3621691345664839022,3740231062922404292,-8438783335304076382,-4539001277367909275>()) {
            case 137928379:
               return false;
            default:
               throw null;
         }
      } else {
         if (!(var1 < var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"s24wo2uoezbgts","Cr4FYgNUrioQnUWTHAjXBuyEq/zuogJpWSLsfa7EhRg=",-1389095607962241956,-7624897865309462091,-3655473723118076470,6587878265057988680>()) {
               case 678815696:
                  if (!(var1 > var3 + this.c())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s26opmppwkv3p6","aj6hYmmCKJwU14zKuOOuvdids7ofODdit1/ThW1c71U=",-6929960172407400329,-454396157171778003,-450321651598548338,1641001567984850031>()) {
                        case -1068552510:
                           if (!(var2 < var4)) {
                              label38:
                              switch ((int)com.yiyiaddon.m.b.a<"s3h64hhafyrxio","XChUomdtlkujK1DLBytphUMeSuAH9IQrBgxkSKlJ7/Y=",-2376143946937733919,6818984608010712328,-957348838380172769,2226320229164821897>()) {
                                 case 727667166:
                                    if (!(var2 > var4 + this.d())) {
                                       a(this);
                                       this.c(this.a(this.gU(), var1 - (var3 + 9.0F) + this.og), false);
                                       return true;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2p3621of4g00j","ikzytH0YTZacsqB1AWtjonFovxObBvfzWhN3hBNmuYM=",-8709556109227690299,8882026395555019042,-1277861117355528285,5346063492723744302>()) {
                                       case 1621109244:
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

         if (d == this) {
            switch ((int)com.yiyiaddon.m.b.a<"s18wdombcgqora","R0+BR6fIad421ZIJU2VEbVrsoqvvSEBV2xOpiSoctJs=",-2602752896017102617,-4062760899019453968,3102070114445136570,-3132061595191427156>()) {
               case 1174874284:
                  lb();
                  switch ((int)com.yiyiaddon.m.b.a<"souutknrfrh7c","KgV/fLHWgQNXHPFp1S3CWrcny6+spHSM5BUDFnH1i4I=",-7715393395724331803,-7782551175380065930,-4291230992993995151,-7263000506284965967>()) {
                     case -1863892061:
                        return false;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4) {
      if (d != this) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vpifexntlqy5","2mdl3KuoVQrCNt7qkwNVkP7emUPCqE4/6As6o64ghIY=",2031958999051626587,-8292394810854546627,2030549783599420968,2256961920100004574>()) {
            case 1627703724:
               return false;
            default:
               throw null;
         }
      } else {
         this.c(this.a(this.gU(), var1 - (var3 + 9.0F) + this.og), true);
         return true;
      }
   }

   public static boolean keyPressed(KeyEvent var0) {
      if (d == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fjtwkqqmgeua","sStTFdmnyl4KFWyb4u4KuxZygViOVcwB15WNJG8PbIA=",-6809195588394442948,-2768774482292928359,2725618562365714927,-2641503967077146279>()) {
            case -772808765:
               return false;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         label117: {
            if (!E(341)) {
               label102:
               switch ((int)com.yiyiaddon.m.b.a<"su8fjyoggqa8n","obAOfnouZ5mK0T82PtHVlMQYKudZhlizZPgFBCmwZP4=",5335608670714614874,4449007188658176654,442911836054434915,1655472511360256170>()) {
                  case 1387888997:
                     if (!E(345)) {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s1de5bzcquh3rg","xX75ySoM0zJGlH3ukqmw7AVygrnpKuIWDJo7Oqfgj0g=",3377550002391837668,6909266696372791062,-3325574345144331553,8402243075841320790>()) {
                           case -1889232746:
                              break label117;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2h47m8uq5n6ka","hvtUcTroe0cFALdX0pxzFaKld4ugRLomv0NIHmpiLpA=",7596086845196021490,6409492144068397950,-3067399593806448996,7497216867922588370>()) {
                        case -1487701057:
                           break label102;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)com.yiyiaddon.m.b.a<"syx51yn1qq786","/LwvwNSIepiGXBVj/Pf31zMGvAzLrsERbmoZq+hxUc8=",2097423551675486369,7866176691993070905,4505051805124588770,-6006036831658224325>()) {
               case 702994092:
                  break;
               default:
                  throw null;
            }
         }

         boolean var1;
         label110: {
            var1 = var10000;
            if (!E(340)) {
               label94:
               switch ((int)com.yiyiaddon.m.b.a<"s4hih5xxaeh1v","FWXEcJEOd0p+pjAAK6AgJNCX5jT5ROQIkuPrXGXM5Qg=",2864849050622956570,5273070047725359857,126795987873878840,-3629166922692032875>()) {
                  case 660108962:
                     if (!E(344)) {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s1n8l1z9zxp7y7","B8hFgx2ewzm2Q/QTNPpNLaGMZn2SH6IFZcTCl2cSaGY=",6026415541648930267,1903166329176498921,4133958049753768741,5920731072646222099>()) {
                           case 1632926387:
                              break label110;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1a32z7u0hv40a","wDDphLwXOXErM1jbN2y05BZkpKx/CcJvSx56IK8E/r8=",-7156186376176434157,1475586921730378740,-8953695648336918394,-6979747659390226339>()) {
                        case 537520383:
                           break label94;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)com.yiyiaddon.m.b.a<"s2q029vrrtwy9p","jikgBFAJpgrWBEgsj8T0OpvimrDwz4reEj8zuoF85+U=",5605565316473006345,-5152316992344264527,2354280936546314195,-2904462708043581219>()) {
               case -1948773181:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2 = var10000;
         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"sekytfr7h2n5x","2STDN0C5PVMwtFPs65uACz2cTAYpAHv0zcnBpey/kPs=",-5837028210917804694,-6941837684357214439,-6563934468595030713,-5247287295982480141>()) {
               case 477153796:
                  if (var0.key() == 86) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1i5g6y0lycknq","OcB+B06G4dNM4urrEQ7u5dbSlcrj2HulFGsxMmpN7hs=",-1804952003676552175,-4023662181990762355,2285427151022996376,7814911928873813882>()) {
                        case -337108657:
                           d.bC(Minecraft.getInstance().keyboardHandler.getClipboard());
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

         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1xe6itwfx81l3","DrYWAQl3srgNkQddDLcGbegkaEhghGQvM2SKIaWtOm8=",-4756060465024578907,-2307455605102655783,83904822105487127,1652780193941823309>()) {
               case -1298075220:
                  if (var0.key() == 65) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3d9b6ael4yngc","jr3C1AfeJaen2s5YOs+Y5L7er8BGdIuRlqyA71fa2ic=",-7525760953555836326,2396938273471426722,1793177867470610085,7643739604206871847>()) {
                        case -557810875:
                           d.lf();
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

         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qkebtz9uqqty","H5iQdP+uJ7J4c5rJkDGw6koNhO6yAk21hs3BDepUuUo=",-8248097485484952453,-4572455706355884510,2571649266309635585,-673525722086791320>()) {
               case -49074076:
                  if (var0.key() == 67) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3kdj9884j4yei","69LW7AGT8KxBGn+2p2AQBAIYfy9wwMBkhJuqlVoRqNU=",4566977347742038180,-120595145778044504,4692638738873469631,5106449969460394562>()) {
                        case 1828983756:
                           d.le();
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

         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s23d4ony6t3k6w","aJwI2Me8L5EDPBNJ2fgmXi34Nbia+8qP9eQbB7ljSiY=",-1528261950272160344,2408296551137125104,-273262245326256081,4729545707401824086>()) {
               case -1331499706:
                  if (var0.key() == 88) {
                     switch ((int)com.yiyiaddon.m.b.a<"s4jb4pcvcw85i","WhPErYBuaFfKYoAA7GnlxnNNaVLuvkccAaYLDER18nQ=",5653563961769518595,-7111457086249470071,-5734487806091887998,5459166709320029285>()) {
                        case 974723833:
                           d.le();
                           d.gP();
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

         switch (var0.key()) {
            case 256:
            case 257:
            case 335:
               lb();
               return true;
            case 259:
               d.lc();
               return true;
            case 261:
               d.ld();
               return true;
            case 262:
               d.b(1, var2);
               return true;
            case 263:
               d.b(-1, var2);
               return true;
            case 268:
               d.c(0, var2);
               return true;
            case 269:
               d.c(d.gU().length(), var2);
               return true;
            default:
               return true;
         }
      }
   }

   public static boolean charTyped(CharacterEvent var0) {
      if (d == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s31w1bxo1wdkq8","PthXktXeZ81bbFY+cro1h9QSdCPUvaKEO1dhKmOpybU=",-4249639060751351500,6746753357500994898,-3927349401447841491,4887345031395842532>()) {
            case 553181485:
               return false;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.l.g.b.kf();
         d.bC(var0.codepointAsString());
         return true;
      }
   }

   public static void a(PreeditEvent var0) {
      String var10000;
      if (var0 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2a4kharaafcli","iJuv2s8lVZbZNfuIzrVSigPSqM2gSwWufeLch0rxP0k=",-8666299242244507556,-988408186973439962,-8498700639464163537,867515788719909574>()) {
            case -622937004:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s13fbeo6xxtgwg","Ee9qyk5/H9NiyxpevJl//9pPcgb5UBGFIn5GCQiAxOc=",6156408157016623084,-7555770390804227987,4731594020816805883,1301984674398986227>()) {
                  case -250659069:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.fullText();
         switch ((int)com.yiyiaddon.m.b.a<"s1dkvvfwa7rs18","WWl0uVueVJEgtsoNZKqZeGg3XXXcE4qKkTbVAIULkMo=",-518090609821085487,-5813647521984608709,-6080098164738242996,292512026181802057>()) {
            case 1524190581:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.b.bv(var10000);
   }

   public static void lb() {
      d = null;
      com.yiyiaddon.l.g.b.kg();
   }

   public static boolean gO() {
      if (d != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3p55xk65apdz9","HXpPRJjQSrJQ6Q6AsYiCV3nXArKX6M28JkGNP9k4vIQ=",7274576655984445665,-4075270653316036785,7485447911649723348,-1925193656064433360>()) {
            case 310944888:
               switch ((int)com.yiyiaddon.m.b.a<"s2xqko9g6zhvb1","gHJOJUEeDIJZzS+oRe4dXUsq0A+Mlif4zOCXsc7Ym/o=",-9215361541738555287,4932940601017671032,2052075865279890623,2138991791326812076>()) {
                  case 26271858:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s14xnu1axrgyit","bGIZ02j2mkOOQvqRlhP1APCwHYio60RssQm2XF1yeg0=",-1525982817549779583,-7118489787287504178,6926605285834379316,-2537612901017217256>()) {
            case 1681082787:
               return false;
            default:
               throw null;
         }
      }
   }

   private static void a(m var0) {
      d = var0;
      var0.oh = 0.0F;
      com.yiyiaddon.l.g.b.kf();
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12dhm9tv2j7s1","e7tGMr1I7WYVkmYC6/9b7BuTatw93VKLjF9ZH9jRgR4=",911274173729704601,333409779605660946,4634167382292667774,564475085457717578>()) {
            case 1132300526:
               com.yiyiaddon.l.g.b.a(var1.screen, var0.oi, var0.oj, var0.c(), var0.d());
               switch ((int)com.yiyiaddon.m.b.a<"s3cnre5o3sv3yv","hlDriEHr4mIpfAlqm9TnAL8JFziL7WtHPqy2u65UrHE=",5583435173065427161,7099452881543513358,4187012357769352674,-3300616983061316009>()) {
                  case -790419289:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void lc() {
      if (this.gP()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3s2x7xwslll8r","wW7gDQJQdUXSq6D9Uz1gCx64J0gN1sVrYXo9nMvoHt0=",8723533235759249908,-2447665258780492210,3018100709891232258,-622922778626555311>()) {
            case -644485708:
               return;
            default:
               throw null;
         }
      } else {
         String var1 = this.gU();
         if (this.aL <= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"shel76i5446bc","SVhv+YBPWr++kVvwaSY/Eo9n0pxLB0sUFM9VshfNi1Y=",764638070094823917,-8308895277088190844,3062870220419525412,2077417900448157593>()) {
               case 1043506696:
                  return;
               default:
                  throw null;
            }
         } else {
            int var2 = var1.offsetByCodePoints(this.aL, -1);
            this.bB(var1.substring(0, var2) + var1.substring(this.aL));
            this.aL = var2;
         }
      }
   }

   private void ld() {
      if (this.gP()) {
         switch ((int)com.yiyiaddon.m.b.a<"sihn5nariblju","r22O8GuvUy/EVsrwsXSxupYfugXawZj2b0YQYSyrFRs=",6906047791309240941,560327729863879995,-8560244101529329966,-5587587255548564449>()) {
            case 250354170:
               return;
            default:
               throw null;
         }
      } else {
         String var1 = this.gU();
         if (this.aL >= var1.length()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2vie6jnuxukxk","la8mR5R12PprFSqhmaloRP9Icb0uXTSvNnfph9xVc3Y=",-4796483693418382455,-1913986511612032708,7859715226380111362,4995294888416851401>()) {
               case 584787439:
                  return;
               default:
                  throw null;
            }
         } else {
            int var2 = var1.offsetByCodePoints(this.aL, 1);
            this.bB(var1.substring(0, this.aL) + var1.substring(var2));
         }
      }
   }

   private void bC(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dkn1uuocwneh","FgFYDCoq2bcl5WTr/+2iNevPaCT5CgS2DN6ItJSrIds=",4016921764213421792,-3485104209527645488,8127247381682966350,-7732871522223904657>()) {
            case -1535675906:
               if (!var1.isEmpty()) {
                  StringBuilder var2 = new StringBuilder();
                  var1.codePoints()
                     .forEach(
                        var1x -> {
                           if (D(var1x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3p7swi32fhgyi","/1NU7e2BK7VZSxzvhkBB9RmqdtMXmLLimaddyXJ6AWc=",8229624993944260190,-1810120879694739763,1214782538527374245,7085961861926900903>()) {
                                 case 344258546:
                                    var2.appendCodePoint(var1x);
                                    switch ((int)com.yiyiaddon.m.b.a<"s8qcc882y9lhg","Mc0iuMEQVgABpwi6jmcpR7J1kY7RclJrmCcfU1zgyO4=",5393466288315073333,-1477087089392346173,1947403571098540227,-3928974589758058543>()) {
                                       case 457685302:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        }
                     );
                  if (var2.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s9gkb3hzns9i1","uysIzSz9WtiXaANuCFWnczGmEG3Ry6Xp9peACCenisY=",-1998780982938203918,2625923512831308525,-321541015152490943,-5320769653444079249>()) {
                        case 1700256520:
                           return;
                        default:
                           throw null;
                     }
                  }

                  String var3 = this.gU();
                  int var4 = this.eG();
                  int var5 = this.eH();
                  this.bB(var3.substring(0, var4) + var2 + var3.substring(var5));
                  this.aL = Math.min(var4 + var2.length(), this.gU().length());
                  this.vR = -1;
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1h5tvitl7orby","9KZzp7Yp+J0N6FJ1N3ritw9kYh1lL9nmGD6GAl7UzLk=",298233330014626720,7914148114165411547,5544865824709218356,-3715159124980858581>()) {
                     case -1935328186:
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

   protected String gU() {
      String var1 = this.N.get();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rinyh9vi3lse","rw9wd1GzO2N6VabzLf3cm/oT6mql/vJoUswzYVvSxG4=",-1547793472047115572,3318515414955225221,8830265701867208051,5566216100229788826>()) {
            case -582999929:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s32qps1c7kw95i","9bkeXTc3FLt7FcdPIpBojRmMs9csK3OQ6XpvDg==",-6843989114261645724,5992287386990895943,-5534133070352505637,-2369033642154931130>();
               switch ((int)com.yiyiaddon.m.b.a<"s1xwwc1u1e8afv","v3eKLmZLlSsMMWFtz2HVTS6sASqbONbZcIBOYK6tI5M=",1835321441402657911,7569195792553018157,-5009399545642961754,578898375941581830>()) {
                  case 1389187958:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2ialh75iwy1qu","IqFpI519mH6f+VFqL3YYyLfdJFRTOG33kfRmR4LzfTc=",4356279926711049343,-4838582318343637738,-7686712801745040031,5830294151180729403>()) {
            case 1995806244:
               return var1;
            default:
               throw null;
         }
      }
   }

   protected String gV() {
      return this.gU();
   }

   protected void bB(String var1) {
      Consumer var10000 = this.x;
      String var10002;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s190rphjf6y3ky","QWVppBZnwy65jcnoJwQevWUzea4gBMOE58KoIiYLcf0=",5800376820822320694,6071340321871557612,7087339052646788996,-8917107810008472067>()) {
            case -856269450:
               var10002 = (String)com.yiyiaddon.m.b.a<"s32qps1c7kw95i","9bkeXTc3FLt7FcdPIpBojRmMs9csK3OQ6XpvDg==",-6843989114261645724,5992287386990895943,-5534133070352505637,-2369033642154931130>();
               switch ((int)com.yiyiaddon.m.b.a<"s8dt6b7k94urd","TPiDSPhV7LNWTsHIDT9F8Xh9UztnyqgRLjFZ1JT0Oa8=",-4111220616260332921,-5182972008306968058,-4752727126296883222,7991015710373980744>()) {
                  case 1237556157:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s1u0tapulbpx56","95rL9AWWbm1GkRiVaW5RDFdi5w+CAYYr5nSCY21CKy8=",5756588395302698634,-8936891875835921539,-7642099157647169788,-6119193667197589036>()) {
            case -2003446922:
               break;
            default:
               throw null;
         }
      }

      var10000.accept(this.cp(var10002));
   }

   private boolean gP() {
      int var1 = this.eG();
      int var2 = this.eH();
      if (var1 == var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o7mhv1e6c5tl","vIXCwgqm6UNT55GA49bHiF3TTkwB5HIs3PF0EAWmEp8=",-3842340593804514100,827572033977045218,-3192459290252597154,-6143316407728500154>()) {
            case 763950471:
               this.vR = -1;
               return false;
            default:
               throw null;
         }
      } else {
         String var3 = this.gU();
         this.bB(var3.substring(0, var1) + var3.substring(var2));
         this.aL = var1;
         this.vR = -1;
         return true;
      }
   }

   private void le() {
      int var1 = this.eG();
      int var2 = this.eH();
      if (var1 != var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s2e0g2obkv3u3","GaR3PAqmgIrkLuEyrGTnUau/5sYPuQySe8FDiPCJgZI=",4406694289713614708,8811902813374089918,-6328477723362677890,5029211813767374250>()) {
            case 2079341683:
               Minecraft.getInstance().keyboardHandler.setClipboard(this.gU().substring(var1, var2));
               switch ((int)com.yiyiaddon.m.b.a<"s2w1hu7zbq5g93","QW5t2i9a8STRmuJyyG7ShzdG6543R00Tw/SpG+HyF3s=",-2386133371329570534,7756421611158399985,-4456845731961848227,7209559142643105343>()) {
                  case 1930513441:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void b(int var1, boolean var2) {
      String var3 = this.gU();
      if (!var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qrm1khu14gwm","WIszschJ1QWRvA+K4GagmBS43D3e1ljUP5fdgZmbkgs=",8317960959897128806,-5684579181003351277,-6570989014891378059,1736698355547491148>()) {
            case 946349909:
               if (this.vR >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3sv6wxpgfcs5n","3OpRtABLXBJT4sUw266MogntgOjKPEk+9cBfLNLWcs4=",-5970659127462248692,5081007624946499063,-2649651705325631032,6480524469116815129>()) {
                     case -239445341:
                        int var10001;
                        if (var1 < 0) {
                           label38:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ajz5974rehfn","+5px7BoBLuRQfgDd9qs5CwGu5WEV1i178zbDBCsFq4k=",1025476961318801491,-366391950192470858,-8902010615449105010,-1037686313497568956>()) {
                              case -1858197402:
                                 var10001 = this.eG();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3kxsgmf6ew1pb","Q0h99J6gGOwu1eM5JiB9FvYQLyQApmsSnjgNIFuzGRc=",749534580245556970,8521402765287879534,7031759838807280784,733442336943632423>()) {
                                    case -845813437:
                                       break label38;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = this.eH();
                           switch ((int)com.yiyiaddon.m.b.a<"s1s4gr83kh24tp","O/ZdCRHSDLOo4VcxRy4Ev7n0VC+ILMAfv1rVYqr4db4=",3940368378503764077,9169951904172899372,2880718317991117726,-5043401811573701321>()) {
                              case 752120304:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        this.c(var10001, false);
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

      int var4;
      label62: {
         var4 = this.aL;
         if (var1 < 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s2o9ab7fhm4bau","IfwC4wA/Snk31Wq/9FwvRylVl9SAMn7M1G1awrGiaKQ=",-456712161104306682,-2882206988084281028,5906133000715014315,7305573784247640006>()) {
               case 1424312305:
                  if (this.aL > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vodo58xcxyju","h/Hntvmc9ZAMCUSOHQ5a2GDfYWcj8QtJf6ElDQJtBKs=",-3844722215970017812,4735470908319528067,7316714370487094528,8364993431852472667>()) {
                        case 2011616987:
                           var4 = var3.offsetByCodePoints(this.aL, -1);
                           switch ((int)com.yiyiaddon.m.b.a<"sa2rh8a7pfwtv","uYOgD+swdKQxAHe8+Vx5erPo5X+fLQgJlk707DToh3k=",5467904144317582471,-2105849456286579945,-9090849546341452727,-6941162448026231909>()) {
                              case 62335738:
                                 break label62;
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

         if (var1 > 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s38f1o48hicj40","XmjFFPTkqvvWfwBe8bT3xrNOdJ2s0NziF4r2EJECaTs=",-195628066175663031,-9068679572730616810,-7327764043429393060,3520072354157411542>()) {
               case 1927728005:
                  if (this.aL < var3.length()) {
                     label49:
                     switch ((int)com.yiyiaddon.m.b.a<"s2lce3hzt30k9a","6y7K1NHIJxEnBl87M/0MYrqzBBmCWGuC0ZDPwNM3ymQ=",-8615310995258215456,-5765512880655578115,-565540273791349660,3742361375217066308>()) {
                        case 519621169:
                           var4 = var3.offsetByCodePoints(this.aL, 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s4mll8ky7m1kt","1n+n/BjbRJPx+qQwbIxXgZBm4QMKdujn7muR4Xf1Z3Q=",-5126820496461644472,4535385958986337949,-5013817406332482327,-3248837004247523761>()) {
                              case 637483713:
                                 break label49;
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
      }

      this.c(var4, var2);
   }

   private void c(int var1, boolean var2) {
      label32: {
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s12c2011k99r59","nlN7Fn5Owg1plMCS0O1gnqoS02CdlM9etHk9W2Oh8c8=",-3203745913481102628,-7414182384120175217,-828657370433953576,1537852930759395964>()) {
               case 1093981330:
                  if (this.vR < 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1b2hut41gxa4d","oIb+tONoA3mQxXWsR4YpxEUqZDvkfuK/GK7Xgxwn5lY=",1303494841987093046,8364741689732089143,-5550396594350717500,-5719530364040143193>()) {
                        case -90769153:
                           this.vR = this.aL;
                           switch ((int)com.yiyiaddon.m.b.a<"sea6yo0lvzi82","A1uxZVIqTm7KKU/mPSFZthFwK3+qy2niaCVCo+twW08=",9199622488786242873,-2080809437194919540,217416575640427949,-7531678204771302039>()) {
                              case -1739016923:
                                 break label32;
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

         if (!var2) {
            label21:
            switch ((int)com.yiyiaddon.m.b.a<"s2dme9yjxuogpd","nspQ0jqCB+QLrFOuIBrKw56KNU/Qw6YmqT0OszfcJN4=",-3742891133004625201,6036253419152778062,8429192610283497674,-3253140036975609527>()) {
               case -1961152083:
                  this.vR = -1;
                  switch ((int)com.yiyiaddon.m.b.a<"sgyvtsj8vfle5","oKijMFbaJKII2BRJoqNcoVlKcHWsfpCD814mtmH3msc=",-8013414145644944822,4119988404450661607,-1971927545192962084,6706275333656334285>()) {
                     case -1379963312:
                        break label21;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }

      this.aL = Math.max(0, Math.min(var1, this.gU().length()));
   }

   private void lf() {
      this.vR = 0;
      this.aL = this.gU().length();
   }

   private int eG() {
      if (this.vR < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2stkdymshedz7","vIImzSDKeFBhvpcw5FBK1NKUIqOzVAEcl2p7QMTFzJA=",-2465018921934909180,5673838559480131693,-435808202286759357,3081531854898414262>()) {
            case 829988194:
               int var10000 = this.aL;
               switch ((int)com.yiyiaddon.m.b.a<"s3u1dcnz12imh6","W8okmJ3ra00Aa3hO7fWsE+3GtrKJxjHDf7xTTaR9YeU=",4268058147565060439,8999822705210605357,-5027488341777389083,-9041814884388691939>()) {
                  case 501274948:
                     return Math.min(var10000, this.aL);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var1 = this.vR;
         switch ((int)com.yiyiaddon.m.b.a<"s14eyt80brqu8o","jQk/nsDX2UZjjLirhtixWQzZMNbPpuftWEn1su56vTs=",-4529134383767136364,8604132931005203997,5453447314453554031,4749018873523100505>()) {
            case -409269933:
               return Math.min(var1, this.aL);
            default:
               throw null;
         }
      }
   }

   private int eH() {
      if (this.vR < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qzz5zxuprvk2","k3Z/89qeGOsvIKqHODyOmcDa1wPY8f4c5+K+vsVS8kM=",8742130902925024955,-9208330282667994332,-4379416810272218528,-2609400838307179336>()) {
            case -582768921:
               int var10000 = this.aL;
               switch ((int)com.yiyiaddon.m.b.a<"skqgqmdzfjzx3","GYozWgWiDlLjs7qQpzSnpWO9tCVp9wwMyt80Fr/X20Q=",2101972584500018419,-6479132326901473769,-7669787551240351619,-3963958018673281056>()) {
                  case 2140144870:
                     return Math.max(var10000, this.aL);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var1 = this.vR;
         switch ((int)com.yiyiaddon.m.b.a<"s4n29ol9mrv1q","xGBx6V/Pb6sq1DlCnZO3QCUu+0zlkPCDe3ybbsicytM=",-3867684845191311964,4545178483621625717,-787202188592871571,7754972937857470569>()) {
            case 1723843711:
               return Math.max(var1, this.aL);
            default:
               throw null;
         }
      }
   }

   private int a(String var1, float var2) {
      if (!(var2 <= 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s28hcuxm8pli3r","4ATeh/+xKt1RC4cRguMeskpGVibeVRlz8kvpagWLHEI=",-3957567699091549101,3788422748030511044,3173895000563420335,-1547356562539628811>()) {
            case 1306347465:
               if (!var1.isEmpty()) {
                  int var3 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s35coxaudd5p66","mfeSb3CfLWH/LsB8fHfFz3xvAdQUGIOhXrV/mfNgXEc=",-39191362922936731,-5217444426966944857,-6855012238597265505,-8803810012474182554>()) {
                     case -1477918123:
                        while (var3 < var1.length()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3syeeosmj6g1t","crH5ElLbR8Lv3LnOBH6SPp7U18xgXX5s0F5ZeF+umf4=",-712252890424650,4151953929938396271,-3887703069420585604,2633247864496933119>()) {
                              case -1327476525:
                                 int var4 = var1.offsetByCodePoints(var3, 1);
                                 float var5 = com.yiyiaddon.l.g.a.b(var1.substring(0, var3), 10.0F);
                                 float var6 = com.yiyiaddon.l.g.a.b(var1.substring(0, var4), 10.0F);
                                 if (var2 < (var5 + var6) * 0.5F) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2i7cg1xlef1rv","PV9yCk/hZu1eKXW2P7T+fq7muI/VfdFgGBqDaN32ORc=",8285782982543224703,-8402447299818661151,-3065308494780145236,3723191546725250362>()) {
                                       case -451761395:
                                          return var3;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3 = var4;
                                 switch ((int)com.yiyiaddon.m.b.a<"sdnyko1k9k0wo","N4R4cjN2U3MdMjrZRgB9Vlao9S8wwQR5hHdXxh3MLuI=",5619351067821364780,4476420468459164907,-1107894691552147159,233659182192373720>()) {
                                    case -576922369:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var1.length();
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"swmjl022vrpxp","bslp8FLU5wQLMBTcy4tAlUqPnbEoIYGsLoPiKLvgLLw=",6340765433829180108,1031616889625725951,7639952305607302012,-8433575671432120775>()) {
                     case -1947578041:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   private void bD(String var1) {
      this.aL = Math.max(0, Math.min(this.aL, var1.length()));
      if (this.vR > var1.length()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ecbo5s9svepg","yKsLkyB/VFTNP6xmfDCnZ5s1jPoYOuGWKQ+mgCWp/CI=",2874025966253115605,5890168503948469556,-8718353087391768783,-6034885540564340148>()) {
            case -1811169749:
               this.vR = var1.length();
               switch ((int)com.yiyiaddon.m.b.a<"s385sqvyr9zruf","V0Br975E7EfXXTPiTCOD8/AKSUDNcZ4R0dR2FcXZwbM=",-6578257887379136256,-883801666865328233,-1443924099210330537,-5617917782746545132>()) {
                  case 1606709628:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   protected String cp(String var1) {
      if (var1.codePointCount(0, var1.length()) <= this.vQ) {
         switch ((int)com.yiyiaddon.m.b.a<"shgbmrkdx6ex6","S8cMfjbDsBX4IiRN5RvrVNgno8V6qAo7r95wGh+rkl8=",-4041237527130481271,-6433656462272550893,-1773356364176819667,7712481533831913635>()) {
            case 945998116:
               return var1;
            default:
               throw null;
         }
      } else {
         int var2 = var1.offsetByCodePoints(0, this.vQ);
         return var1.substring(0, var2);
      }
   }

   private static boolean D(int var0) {
      if (var0 >= 32) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vh7mqq8anxuz","N6UxBDH9gmYanYSfR72LZcFWAUvlP3EZIdl9T9OH9Qw=",247132581290494873,1265559325766113903,5461531017164547908,5146034016620804531>()) {
            case -1092805392:
               if (var0 != 127) {
                  switch ((int)com.yiyiaddon.m.b.a<"s30e11jgtvbnkm","HxmETvKFokKocboueY/JPYVwqTfbWcncqwwCyY/0+DU=",-6975313390726497633,-5814434513066355942,-687654485213843609,8003523413808510201>()) {
                     case 1805240516:
                        if (var0 != 10) {
                           switch ((int)com.yiyiaddon.m.b.a<"senlxow59ky5m","aDTzSjwrpkc3DpuQb1ZPk+htJtWRhBWub3YDMiqi/+g=",-332920125125639699,7834506569526513231,3255403494764754700,6519195256832568439>()) {
                              case -72231584:
                                 if (var0 != 13) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s13qhj6ukttr8n","PnbH5QXGbjC0kOPUYVtH4EjzGZqx/x44f3pXor6c7B0=",560690451417800377,-9009051778930660045,-3909745838083545967,1774644762699557802>()) {
                                       case 295991231:
                                          if (var0 != 9) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3c07nvrjm65q4","u+rZwVXyElPAgVjhDq5nBTcPCYBVRuNEIZeZy8Ciklg=",8182961747827454207,-1857730638948437591,-4139676053335901645,-2953530453645566911>()) {
                                                case -1318967964:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2vci5hxofsd6h","3VdiibVSTVWVmcHlWwQMAPkrJHfvJCyw7Gb7GCdQ3oE=",9072614151975231041,-1176561260674513546,-7038242066169957325,1508913847735228652>()) {
                                                      case -922294330:
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1digx501942iw","rma0b6iEiZKKOitsi8hdiIsC/Z5LLrhxMPd/tRz7wNY=",2960581602871140994,7571124007733369297,1343702132952916002,1257090644330793558>()) {
         case -422157876:
            return false;
         default:
            throw null;
      }
   }

   private static boolean E(int var0) {
      long var1 = Minecraft.getInstance().getWindow().handle();
      if (GLFW.glfwGetKey(var1, var0) == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qocpd7iymvl9","2TDzOEsU9pbluYWSgxbJ99deu7XuuZ9v8+i3uGIkgxg=",-6962163593881542593,-3143276886835334880,65876690315882356,-3654043401588398761>()) {
            case -1889038743:
               switch ((int)com.yiyiaddon.m.b.a<"s1ffkyxdvtnzc2","pALNefGixQCJzrLNbQTxvEk7UGWmn5xyrvFF+5V5KWA=",-7417432877258686792,401473341718816653,7774703125461705442,5535029893224813569>()) {
                  case 1234923393:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"snlxvub2mq03c","GShOWWfRuaqPZ8Bb5Ni9tWjsDYB2R4VX2WdfC7PP6t8=",9137918379140187131,-3423137673835810287,7513861028604067231,5492580401615131519>()) {
            case 63801523:
               return false;
            default:
               throw null;
         }
      }
   }
}
