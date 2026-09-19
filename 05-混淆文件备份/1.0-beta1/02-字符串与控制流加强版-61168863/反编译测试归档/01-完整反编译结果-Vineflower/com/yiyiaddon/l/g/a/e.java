package com.yiyiaddon.l.g.a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import net.fabricmc.loader.api.FabricLoader;

public final class e {
   private static final Gson g = new GsonBuilder().setPrettyPrinting().create();
   private static final String Gg = (String)com.yiyiaddon.m.b.a<"s1ae9k84x1cfge","/iGJ9srNEnYnocNeNe8AGOn3LtiLbSYJRRAjt2kLwgV5gdwhvBAgA3N3F1nv+WwW6jb/3znPJfSKCQ==",-4220408896180656124,-4704270870194365610,2092730543253637803,358247865555871137>();
   private static final String Gh = (String)com.yiyiaddon.m.b.a<"s1mytjlg9lzsx2","3rjnEzyUPVtcUflKM0xSOAo0EGvCdQyTzebvrpgZ1dlxZBUONTJlpAbHMoF84Q==",-2371359273504795487,-8779977020214055859,-7757644469077937920,-181402804252028056>();
   public static final double bD = 0.5;
   public static final double bE = 3.0;
   public static final double bF = 0.1;
   public static final double bG = 1.0;
   public static final int un = 0;
   public static final int uo = 256;
   public static final double bH = 0.5;
   public static final double bI = 2.5;
   public static final int up = 0;
   public static final int uq = 4096;
   private static volatile e b;
   private boolean m = true;
   private float kA = 1.0F;
   private e.b a = e.b.FOLLOW;
   private e.c a = e.c.FOLLOW;
   private float kB = 1.0F;
   private int ur = 0;
   private boolean gd;
   private int us = 24;
   private float kC = 1.0F;
   private boolean ge = true;
   private int ut = 0;
   private boolean gf = true;
   private boolean gg;
   private boolean gh = true;
   private int uu = 16777215;
   private final boolean[] a = a();
   private boolean gi;
   private boolean gj;

   private static boolean[] a() {
      boolean[] var0 = new boolean[e.a.values().length];
      Arrays.fill(var0, true);
      return var0;
   }

   private e() {
   }

   public static e a() {
      e var0 = b;
      if (var0 != null) {
         return var0;
      }

      synchronized (e.class) {
         if (b == null) {
            e var2 = new e();
            var2.kw();
            b = var2;
         }

         return b;
      }
   }

   public boolean ar() {
      return this.m;
   }

   public float s(float var1) {
      return var1 * this.kA;
   }

   public j a(j var1) {
      switch (this.a) {
         case FOLLOW:
            switch ((int)com.yiyiaddon.m.b.a<"s33bdfsvguuo14","q59Ulv/iaAndoo7DW+lfN1lipJk25et7uzk3/1xpDaw=",-4302235786160928031,-1853308222156944168,-8039882975733906978,-8286520450981551477>()) {
               case 1332793473:
                  return var1;
               default:
                  throw null;
            }
         case LINES:
            j var3 = j.Lines;
            switch ((int)com.yiyiaddon.m.b.a<"smig5er1uw2oh","7dvS3rNI6hfSfW0aesm9G/EvOuaWtk+yLmOAm5VNcvU=",3194011495512016492,-2226042981935332435,-2715356474464954790,-4677742547838580781>()) {
               case -2002152436:
                  return var3;
               default:
                  throw null;
            }
         case SIDES:
            j var2 = j.Sides;
            switch ((int)com.yiyiaddon.m.b.a<"s35oi2jiv7y0f1","nGKFFtZL4TKp9jPc9H9gNl0y+KTaLNrnm0ELudLYbhA=",1543141668467510003,207837628573930365,5270743014595034510,-5717702116091504151>()) {
               case -1037000291:
                  return var2;
               default:
                  throw null;
            }
         case BOTH:
            j var10000 = j.Both;
            switch ((int)com.yiyiaddon.m.b.a<"s29higbx76vqhl","+enF1nKcgCYJdJYqBrHAvjxXh3ezvlfGgsTgBOmkHRo=",-2513720987092275350,-6975082514436960891,-7585991141127743219,1791176393516380071>()) {
               case -1476290742:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   public boolean d(boolean var1) {
      switch (this.a) {
         case FOLLOW:
            switch ((int)com.yiyiaddon.m.b.a<"souls2duovnyl","GQzBlgOuZD+FtFN2CqF1XPixauEbogXmik+Cp50FHlU=",-8899468663300738557,-7503723646191915456,-2940179696953849666,5514498334099444744>()) {
               case -267229808:
                  return var1;
               default:
                  throw null;
            }
         case XRAY:
            switch ((int)com.yiyiaddon.m.b.a<"s1r6llnml12jef","glgcQAJBWNa/qq+LzwjA6KQfrBRdIYzUXUjeIxcYzfA=",1043270749008337687,1049550768235499476,5979050621822058725,552144481749285026>()) {
               case -461977010:
                  return false;
               default:
                  throw null;
            }
         case OCCLUDE:
            switch ((int)com.yiyiaddon.m.b.a<"s1hvgl2plq9kqs","sCf9gAqxPT4LUAK+bs0DJyFvowC+m8VYNryZVUNj4jU=",-1093915580434010270,6808889198230396887,1160823912045245077,5196606751714432921>()) {
               case 971636068:
                  return true;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   public float Z() {
      return this.kB;
   }

   public int el() {
      return this.ur;
   }

   public boolean gk() {
      return this.gd;
   }

   public int em() {
      return this.us;
   }

   public float aa() {
      return this.kC;
   }

   public boolean gl() {
      return this.ge;
   }

   public int en() {
      return this.ut;
   }

   public boolean e(boolean var1) {
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3eh8ql4i2qs1s","aczqjkam9ZEOlIe9/snsbXbSVDiLdiOCjXLUGZiEO9c=",6245495937355264711,-8392271974804236355,-6412965285391080077,7074805850879901164>()) {
            case 1489171786:
               boolean var10000 = this.gf;
               switch ((int)com.yiyiaddon.m.b.a<"s3tgd1ek6kbx8v","cGMbErZ6jN1L8ySF+0Ckk+uIPiFegYkJ987dJ4OdDJk=",3457642262860120469,-2049896305465977871,-2528826155780220676,-3950384119735106186>()) {
                  case 1425183217:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         boolean var2 = this.gg;
         switch ((int)com.yiyiaddon.m.b.a<"sf4bjpz3g6g06","eaJ+OFNmQblLwPt93k78YDM9fv9XdwVcXVgStqkmtmQ=",-2488887934489667406,8596033048973457964,-6053865649662951157,-7063627739083082669>()) {
            case -267245657:
               return var2;
            default:
               throw null;
         }
      }
   }

   public boolean gm() {
      return this.gh;
   }

   public boolean gn() {
      return this.gi;
   }

   public void z(boolean var1) {
      this.gi = var1;
   }

   public boolean go() {
      return this.gj;
   }

   public void A(boolean var1) {
      this.gj = var1;
   }

   public boolean gp() {
      if (!this.gi) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s2g6z8cqhts69z","ujA+Gt7es4GQsuupkPW80Dfk+MqnpjW8GntW8H4lMk4=",8860789210624288521,4100804201933362941,-8892383283680189226,-5540307140514038223>()) {
            case -1959374772:
               if (!this.gj) {
                  switch ((int)com.yiyiaddon.m.b.a<"s16aidip8dv6nm","At7Qu9ah4Ax0cukNhh3tW2WqommcgjwgEsl1uR8EA/o=",4562680514445408240,8564629949343875396,-277134795044736138,1844961105683810621>()) {
                     case -36991247:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2kg1nuenqqodi","fGw7al+QHN7wya+JboYlm18WPrloyv2sKm3VlFyDAqE=",-602309663094436040,7375317741799620298,6646041552400345078,3351120263867352212>()) {
                  case 391555123:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"srxtywlugxpvm","+BpNEcSgTMx37xVoeRJep+LNafYGzrLzKmhmO20FgeM=",-1641557825340568456,1517098184236989118,-8117705768095450745,-674981883190159760>()) {
         case -1370118648:
            return true;
         default:
            throw null;
      }
   }

   public int eo() {
      return this.uu;
   }

   public void n(boolean var1) {
      this.m = var1;
      this.e();
   }

   public void l(double var1) {
      this.kA = (float)a(var1, 0.5, 3.0);
      this.e();
   }

   public void a(e.b var1) {
      e.b var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3ail2m2srangj","fF6PqWQWVQJOQB6yav1i+w6VR7Rj+rG104ZU1GM3b0w=",-6089293403295916513,6629445030236969475,-6009638761379906998,3460875484872251447>()) {
            case -999002969:
               var10001 = e.b.FOLLOW;
               switch ((int)com.yiyiaddon.m.b.a<"s1e5hwv43grj97","KKyqKye9R1k0FQYBYy0JrtRSA7bpRSn0J1Q+Rz8k/+c=",140012785851115080,-2181793834965268982,3380423168162752871,-3109686662783030282>()) {
                  case -1023416315:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"sfip5ua3ii68g","nWdTlyI9l3DB8Apg1ErD4bVslsro3sbxH7LEN1wYGbI=",-3823546587568722022,-3015987114559446927,-1247017849372477593,3635939242348561192>()) {
            case -2054526047:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
      this.e();
   }

   public void a(e.c var1) {
      e.c var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s24vdvtnve2pr6","roLvQBJ5Jc6QTrfhplnRaoDiQ8/OUas2K7YaeCChNhE=",342665732317575821,-2986672571602700668,8477591332817350496,-3918719315582932113>()) {
            case 502061669:
               var10001 = e.c.FOLLOW;
               switch ((int)com.yiyiaddon.m.b.a<"spsnvio2le7gd","EFqwwDkLTosmbj2fYjZReIcJHRWXZRKLi699IWgnzGQ=",2655712295436127353,6932803755713985314,3936848327740757373,-1166853936193025637>()) {
                  case -407250660:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s17vaixwmm8h66","C2jyyuPVL1pcfCOMOnoPF1KCOtRUS8ZJtoKu+3TKovQ=",-44767022578566858,500391707052624444,-6358555219711970767,-4209633998117167114>()) {
            case 409683691:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
      this.e();
   }

   public void m(double var1) {
      this.kB = (float)a(var1, 0.1, 1.0);
      this.e();
   }

   public void n(double var1) {
      this.ur = (int)Math.round(a(var1, 0.0, 256.0));
      this.e();
   }

   public void B(boolean var1) {
      this.gd = var1;
      this.e();
   }

   public void o(double var1) {
      this.us = (int)Math.round(a(var1, 0.0, 256.0));
      this.e();
   }

   public void p(double var1) {
      this.kC = (float)a(var1, 0.5, 2.5);
      this.e();
   }

   public void C(boolean var1) {
      this.ge = var1;
      this.e();
   }

   public void q(double var1) {
      this.ut = (int)Math.round(a(var1, 0.0, 4096.0));
      this.e();
   }

   public void D(boolean var1) {
      this.gf = var1;
      this.e();
   }

   public void E(boolean var1) {
      this.gg = var1;
      this.e();
   }

   public void F(boolean var1) {
      this.gh = var1;
      this.e();
   }

   public void Q(int var1) {
      this.uu = var1 & 16777215;
      this.e();
   }

   public double n() {
      return this.kA;
   }

   public e.b a() {
      return this.a;
   }

   public e.c a() {
      return this.a;
   }

   public boolean gq() {
      return this.gf;
   }

   public boolean gr() {
      return this.gg;
   }

   public boolean a(e.a var1) {
      return this.a[var1.ordinal()];
   }

   public void a(e.a var1, boolean var2) {
      this.a[var1.ordinal()] = var2;
      this.e();
   }

   private static String b(e.a var0) {
      return var0.name().toLowerCase(Locale.ROOT) + "";
   }

   private static Path a() {
      return FabricLoader.getInstance()
         .getConfigDir()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s1mytjlg9lzsx2","3rjnEzyUPVtcUflKM0xSOAo0EGvCdQyTzebvrpgZ1dlxZBUONTJlpAbHMoF84Q==",-2371359273504795487,-8779977020214055859,-7757644469077937920,-181402804252028056>()
         )
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s1ae9k84x1cfge","/iGJ9srNEnYnocNeNe8AGOn3LtiLbSYJRRAjt2kLwgV5gdwhvBAgA3N3F1nv+WwW6jb/3znPJfSKCQ==",-4220408896180656124,-4704270870194365610,2092730543253637803,358247865555871137>()
         );
   }

   private static double a(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }

   private void kw() {
      Path var1 = a();
      if (Files.isRegularFile(var1)) {
         try {
            JsonElement var2 = JsonParser.parseString(Files.readString(var1, StandardCharsets.UTF_8));
            if (!var2.isJsonObject()) {
               return;
            }

            JsonObject var3 = var2.getAsJsonObject();
            this.m = b(
               var3,
               (String)com.yiyiaddon.m.b.a<"s17418d3dpg405","89c5CfXkh0lT9h22aNpT+pe1d66+XkQnmzgYIeClwupx79kmwwYgsqX1",-7477814927005281084,-1803794257343596845,-1955441836189406215,7613027707064179491>(),
               this.m
            );
            this.kA = (float)a(
               b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"ssgtx0xsb7zhc","lR9oG8houOgH3b2EnYv4fdvL8F/no9qIRAWstvj3yIn+1IEKLUTj/Vwr8+ggamCNi4hf84zO8X4=",845705615331495692,-74065904074114800,6502212695499420312,-5730663919816045900>(),
                  this.kA
               ),
               0.5,
               3.0
            );
            this.a = a(
               var3,
               (String)com.yiyiaddon.m.b.a<"svf46l499gkta","5T6RAejRz7kiI67Ajg2MeRomfRhZIRTbjWsawDpSpaZgmpdhnfN1KthTtqw4UPKiIet3bQ==",3129529734682265977,6737047038078701558,6854373935482989430,5309982072341985631>(),
               this.a
            );
            this.a = a(
               var3,
               (String)com.yiyiaddon.m.b.a<"s1tai9xvkgbndy","woU4gEoZtI3+wkOd5Z1Zu27ncyhvCJniYJX9hIj10umoXiU+CaJuAmBIeFiqxJmXzR0wWJDwMGJrbVo2rNc=",9155854349323569380,-8131469593515204263,-160618553193499368,2309673062789001987>(),
               this.a
            );
            this.kB = (float)a(
               b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"sjxhdd8jm78ai","4gycTl9bAhPAYYtk/eBmbDsENT8wEcaDMZEioQcdW0OSrd3t09AYI7uvjfDN/4uc",-9159955753581428981,-609777335739007951,-2388618104620228708,1823495668845942126>(),
                  this.kB
               ),
               0.1,
               1.0
            );
            this.ur = (int)Math.round(
               a(
                  b(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"sstyy3jho1wcb","Q1m/HDo5FvHKFgOox8xNSgwu/zPIpn01QCJYqTT+6H5BdTEJn0PTTr0RZ/9c/tkZyBA=",7572911934650160783,5775828940270660347,1598317035561750236,3341705599959038696>(),
                     this.ur
                  ),
                  0.0,
                  256.0
               )
            );
            this.gd = b(
               var3,
               (String)com.yiyiaddon.m.b.a<"s2i43dqx5l0lg8","j/nnU7hZaSHrMmtdHz8wRbt+PIc72SsGmwf8MLcgy2XZlc7u",4432388228415383166,-3786740697504848907,-4252470545752831795,-6890085722497888653>(),
               this.gd
            );
            this.us = (int)Math.round(
               a(
                  b(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"snei4adis8ewj","Dqqx98Q6FZWoaLYGOGr9A3FzG3kJC/br0W/PGtrzQvLALcN0g7E6/d1MKYFELQ==",5523809350990590065,-4475854975216845654,9135409770429494530,-542413978186369278>(),
                     this.us
                  ),
                  0.0,
                  256.0
               )
            );
            this.kC = (float)a(
               b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s1ij20virn8sli","RqCGHrjUgZP28SW72dFGW7VgpxOb6xDSTmptS4wqie6nEZw528eWo4EKa+7zbA==",7902615351467086980,-3085839418815698839,-9006337021988517649,3209406484572020370>(),
                  this.kC
               ),
               0.5,
               2.5
            );
            this.ge = b(
               var3,
               (String)com.yiyiaddon.m.b.a<"sv5vn4zdk2e4a","5zr+z9zKTQHOvTTySGaOtjE+i1MXYYDXJoTgTK0BBYXy+eMv3xJMEPkTX9flBw==",8934687783404156355,-4970773983633365152,-2869226229150506475,7233007906921971523>(),
               this.ge
            );
            this.ut = (int)Math.round(
               a(
                  b(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"s3iv3qs4ehtzt0","LQt/NS8oYcvbjWuEa+NfFGNFY0mN34I87PrDBTehfoAHE8j/0BqQ3GxmiiG5b/LsJFzMIo7g3p1SMA==",4176426400252884853,7186354751451014289,-5086849862061361949,4547623505309443427>(),
                     this.ut
                  ),
                  0.0,
                  4096.0
               )
            );
            this.gf = b(
               var3,
               (String)com.yiyiaddon.m.b.a<"s3lry595esd1kb","fon0AEgKVn7R7Yg6o/mk34T5bjPeBNT2zo09tWY+wEryDAp1va4nvD5Ori5oyvqQ95QTdyY7QiKymqV0XY9pMMJ1",581403931612409334,-2058173681221126617,9019852386922504123,-6478966770750941626>(),
               this.gf
            );
            this.gg = b(
               var3,
               (String)com.yiyiaddon.m.b.a<"s1zq2q2uhx58vj","I+a8bzkPB88L3cftiBNZ9QiSxsZt6v9AIC8rAuNnyNtNBpgToyafHA3XAaQ3WqxWWB58B3HMBfa2LZIhDm+x+hQ0",-3374096508007837643,-632576105306380887,-587473949013683520,2881235319307186941>(),
               this.gg
            );
            this.gh = b(
               var3,
               (String)com.yiyiaddon.m.b.a<"ssduqbyh70rlv","bxisDu3oYA6dBCkt51Anuy4ahn5IHjNq/2lMF73mPQpUzfyxtLOtMdj8zK1mCIHakNxNhw==",-5067674738264818350,8098554441597015735,-3868576324929177245,-6096102238670844983>(),
               this.gh
            );
            this.uu = (int)b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"sl41i9ecdq80m","MYYiZAscNfvGJzUl9E7Cukwm45CLpVnaSVY8yBDLtYhKAXh+YCeDJRMIQJ+eGwZLvJfS9obMZ3/Fr5rJv/0=",4267730125521916691,946737350753041140,-6168967769181038157,-5285326690865931213>(),
                  this.uu
               )
               & 16777215;

            for (e.a var7 : e.a.values()) {
               this.a[var7.ordinal()] = b(var3, b(var7), this.a[var7.ordinal()]);
            }
         } catch (Exception var8) {
         }
      }
   }

   private void e() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s17418d3dpg405","89c5CfXkh0lT9h22aNpT+pe1d66+XkQnmzgYIeClwupx79kmwwYgsqX1",-7477814927005281084,-1803794257343596845,-1955441836189406215,7613027707064179491>(),
         this.m
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"ssgtx0xsb7zhc","lR9oG8houOgH3b2EnYv4fdvL8F/no9qIRAWstvj3yIn+1IEKLUTj/Vwr8+ggamCNi4hf84zO8X4=",845705615331495692,-74065904074114800,6502212695499420312,-5730663919816045900>(),
         this.kA
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"svf46l499gkta","5T6RAejRz7kiI67Ajg2MeRomfRhZIRTbjWsawDpSpaZgmpdhnfN1KthTtqw4UPKiIet3bQ==",3129529734682265977,6737047038078701558,6854373935482989430,5309982072341985631>(),
         this.a.name()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1tai9xvkgbndy","woU4gEoZtI3+wkOd5Z1Zu27ncyhvCJniYJX9hIj10umoXiU+CaJuAmBIeFiqxJmXzR0wWJDwMGJrbVo2rNc=",9155854349323569380,-8131469593515204263,-160618553193499368,2309673062789001987>(),
         this.a.name()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sjxhdd8jm78ai","4gycTl9bAhPAYYtk/eBmbDsENT8wEcaDMZEioQcdW0OSrd3t09AYI7uvjfDN/4uc",-9159955753581428981,-609777335739007951,-2388618104620228708,1823495668845942126>(),
         this.kB
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sstyy3jho1wcb","Q1m/HDo5FvHKFgOox8xNSgwu/zPIpn01QCJYqTT+6H5BdTEJn0PTTr0RZ/9c/tkZyBA=",7572911934650160783,5775828940270660347,1598317035561750236,3341705599959038696>(),
         this.ur
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2i43dqx5l0lg8","j/nnU7hZaSHrMmtdHz8wRbt+PIc72SsGmwf8MLcgy2XZlc7u",4432388228415383166,-3786740697504848907,-4252470545752831795,-6890085722497888653>(),
         this.gd
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"snei4adis8ewj","Dqqx98Q6FZWoaLYGOGr9A3FzG3kJC/br0W/PGtrzQvLALcN0g7E6/d1MKYFELQ==",5523809350990590065,-4475854975216845654,9135409770429494530,-542413978186369278>(),
         this.us
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1ij20virn8sli","RqCGHrjUgZP28SW72dFGW7VgpxOb6xDSTmptS4wqie6nEZw528eWo4EKa+7zbA==",7902615351467086980,-3085839418815698839,-9006337021988517649,3209406484572020370>(),
         this.kC
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sv5vn4zdk2e4a","5zr+z9zKTQHOvTTySGaOtjE+i1MXYYDXJoTgTK0BBYXy+eMv3xJMEPkTX9flBw==",8934687783404156355,-4970773983633365152,-2869226229150506475,7233007906921971523>(),
         this.ge
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3iv3qs4ehtzt0","LQt/NS8oYcvbjWuEa+NfFGNFY0mN34I87PrDBTehfoAHE8j/0BqQ3GxmiiG5b/LsJFzMIo7g3p1SMA==",4176426400252884853,7186354751451014289,-5086849862061361949,4547623505309443427>(),
         this.ut
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3lry595esd1kb","fon0AEgKVn7R7Yg6o/mk34T5bjPeBNT2zo09tWY+wEryDAp1va4nvD5Ori5oyvqQ95QTdyY7QiKymqV0XY9pMMJ1",581403931612409334,-2058173681221126617,9019852386922504123,-6478966770750941626>(),
         this.gf
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1zq2q2uhx58vj","I+a8bzkPB88L3cftiBNZ9QiSxsZt6v9AIC8rAuNnyNtNBpgToyafHA3XAaQ3WqxWWB58B3HMBfa2LZIhDm+x+hQ0",-3374096508007837643,-632576105306380887,-587473949013683520,2881235319307186941>(),
         this.gg
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"ssduqbyh70rlv","bxisDu3oYA6dBCkt51Anuy4ahn5IHjNq/2lMF73mPQpUzfyxtLOtMdj8zK1mCIHakNxNhw==",-5067674738264818350,8098554441597015735,-3868576324929177245,-6096102238670844983>(),
         this.gh
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sl41i9ecdq80m","MYYiZAscNfvGJzUl9E7Cukwm45CLpVnaSVY8yBDLtYhKAXh+YCeDJRMIQJ+eGwZLvJfS9obMZ3/Fr5rJv/0=",4267730125521916691,946737350753041140,-6168967769181038157,-5285326690865931213>(),
         this.uu
      );

      for (e.a var5 : e.a.values()) {
         var1.addProperty(b(var5), this.a[var5.ordinal()]);
      }

      try {
         Path var7 = a();
         Files.createDirectories(var7.getParent());
         Files.writeString(var7, g.toJson(var1), StandardCharsets.UTF_8);
      } catch (IOException var6) {
      }
   }

   private static boolean b(JsonObject var0, String var1, boolean var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sa0604p8o52tq","oCmAE2ThiGJPDYO2yehIJAPzZJfrlN/yI/pWoDx9Uds=",-4359708692932101006,7385613820918597898,-8353931033835032166,-6594421361813241907>()) {
            case -117391394:
               if (var3.isJsonPrimitive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"ssyz2b1imfl1c","8kdWt8+3qDfH/T8Kg15HrgAjGfgC8U2nSOIdMdFMTmo=",-5088139562013174632,178953504257999089,-9214577795145373024,7091278841238438019>()) {
                     case 299121092:
                        if (var3.getAsJsonPrimitive().isBoolean()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s28uk9g2fwpo4r","vnSU108/TCuYSBSwdFbR0FWvG70+JPojk4/3nvfLDY4=",-6808948366920518317,6153423360794104111,7554014555607552954,-3413615093437221256>()) {
                              case 1967349881:
                                 boolean var10000 = var3.getAsBoolean();
                                 switch ((int)com.yiyiaddon.m.b.a<"s14an6zuyocsg0","K4xZQ7C11hu8z4j8ICgH1ozn98qoHQhcwYTsR9KEBes=",6587099236214717329,498376353866213555,-2082594144289515174,-7661257963074040167>()) {
                                    case -1886852263:
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s103dr2qsklux3","XcJu8wKTAddfnVb/xUkATYegCiTrC6spsSfm47mJr7Q=",3005779099630798590,5733899735075341289,-8132969867587571052,4147943502630440402>()) {
         case 191900962:
            return var2;
         default:
            throw null;
      }
   }

   private static double b(JsonObject var0, String var1, double var2) {
      JsonElement var4 = var0.get(var1);
      if (var4 != null && var4.isJsonPrimitive() && var4.getAsJsonPrimitive().isNumber()) {
         try {
            return var4.getAsDouble();
         } catch (NumberFormatException var6) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static e.b a(JsonObject var0, String var1, e.b var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive()) {
         try {
            return e.b.valueOf(var3.getAsString());
         } catch (IllegalArgumentException var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static e.c a(JsonObject var0, String var1, e.c var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive()) {
         try {
            return e.c.valueOf(var3.getAsString());
         } catch (IllegalArgumentException var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public enum a {
      MINING(
         (String)com.yiyiaddon.m.b.a<"s2j0lwr12f8qt8","cBxTYYQyrHb+Ga7HNwLWrtHmEmvKlp4NThDwJyAKxEM=",2836367708806022164,-4713383947898610118,-5678302888649761839,-3956485671695235603>()
      ),
      STARDEW(
         (String)com.yiyiaddon.m.b.a<"s31gq92bq2rliz","adsJqlcta4M0GF8KnpxxSBOHcOZuM5gLhwfl0jJgLyhwEQ==",-3879178314107904644,-4877836514198220453,4768292267741987160,-8362212030514683122>()
      ),
      AUTO_CHEST(
         (String)com.yiyiaddon.m.b.a<"s3j49shg6p4gwy","rwSI/5qTM/AcUi3d0n/gwkpEODnVLf5ZBPUK50qxGXyjqSkK",3456127706939737504,2286274691707648948,4934987033096184522,-4502477899723485771>()
      ),
      VILLAGER(
         (String)com.yiyiaddon.m.b.a<"s2hkuia6r20759","tHJW87WFNpLluLRrl57GM74J9HGNO+R41317C0AxShszgNNJ",828206022728806856,8605822654871188531,-6819059869043594180,-3161405430279984952>()
      ),
      ADMIN(
         (String)com.yiyiaddon.m.b.a<"s10ffpnm182cck","1g8ZMr5/WnJU2GmrpLgQPoULpYpfVd9kzDffrppWxju63CWwlYY=",5618227166999889443,8902454092094945916,-4917121473582067888,-854254440718099245>()
      ),
      VISION(
         (String)com.yiyiaddon.m.b.a<"s33qwizfblajm2","45fUg39HZhqKC1/KENl40s5cKbzzNOtanDNlGRYlSzo=",804973898816587256,8202593535307324526,-4774142329811288232,-727617631094907369>()
      ),
      TELEPORT(
         (String)com.yiyiaddon.m.b.a<"sff2zid8wgrfj","kZaebiuAUHG9EWC7jCaMITnwLIzIbDd/KpKJIV8HqBY=",2824985166538916635,-146296032157972364,7050866624271419152,3257216938609735344>()
      ),
      INSTANT_BREAK(
         (String)com.yiyiaddon.m.b.a<"sx0n34x9y01eg","X8zE19lA/BUlV2yJL7HIo5DZnZrqkxGng1bgMMBeUPW9tVf+",-7049128200087898541,-5221759276609891244,-1466671335102864905,-5173490467670012038>()
      ),
      BONE_MEAL(
         (String)com.yiyiaddon.m.b.a<"s1nufa0w2d6l1v","LIbUOJzswUAl2YCEs+Kqqxoan1jjAz8fJ6zvH5oas+jOSaV0",-5966863116766655185,-877531525935756161,8604218171798177169,-8235447045928848409>()
      );

      private final String Gi;

      a(String var3) {
         this.Gi = var3;
      }

      public String h() {
         return this.Gi;
      }
   }

   public enum b {
      FOLLOW(
         (String)com.yiyiaddon.m.b.a<"sikkuiyy4wyz9","Mty1baCRJDOSgiXQhJjTNbcIsn3t5ZW0HElzNpaP3+FRpxnkx1g=",-5990625487411101371,-6937387574804049989,-2233239325367091448,6924917979196436088>()
      ),
      LINES(
         (String)com.yiyiaddon.m.b.a<"sog61ainb3qhz","UGfxdwrmvJXjDAuJXDkwEbefNV2R1z5Gzx2oLu7WnIGcTaEE",4936520295894260885,4921485452636001005,3368338725718511914,7116663666226794096>()
      ),
      SIDES(
         (String)com.yiyiaddon.m.b.a<"s3tq67zg6ud2g7","cbBxsnaQUV7442iwVbTEbVLf6GBh+ncHyBJ0Hn7a1rjBZw==",2098439554046078362,-8501756301887184743,4584536585821225988,-6072230290068579171>()
      ),
      BOTH(
         (String)com.yiyiaddon.m.b.a<"s192k0guem03tc","RK2nBWXpGGWs7Zb/Mxqa1CXOFo3YHOpp7AbGdPhpdMk2UfU2",-1992192732783542302,-2832417255799977947,1454872442461352089,7895010627754594060>()
      );

      private final String Gj;

      b(String var3) {
         this.Gj = var3;
      }

      public String h() {
         return this.Gj;
      }

      public static String[] b() {
         e.b[] var0 = values();
         String[] var1 = new String[var0.length];
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s18k581gc281zn","+09JdZcwvU5pF5255N4fUz8+B1KS+/KVixjFLpelXTM=",3781139981558351747,6651345965786089592,4634186423706230183,3603777675658187537>()) {
            case -68234656:
               while (var2 < var0.length) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1bz2g8mvhj4me","anqQAud0x7zlkZICVl2tU9JbfhoyOA41Rd7MiXqkeHE=",66269902301169476,4171761317949937761,-9146428730123219169,5325192586358040673>()) {
                     case 451688096:
                        var1[var2] = var0[var2].Gj;
                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1uj4xs20zjx2r","y8rQ1qPIefvR3AYkc+nB9eKlhXWfwscDibo0I+yGR0w=",1694309372146853177,1306784386983899373,-339309573760392825,-368795963269213150>()) {
                           case -1903789502:
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
   }

   public enum c {
      FOLLOW(
         (String)com.yiyiaddon.m.b.a<"s2qtn3meh857n0","xjM4MGbBenZOkO4CG9c/ke3cJF+Io9LD4lhqBfqCxRK2sNm4j1E=",6211155693774391155,7620426905688569170,-2692243879211236862,1370108381041399908>()
      ),
      XRAY(
         (String)com.yiyiaddon.m.b.a<"s1qzl8wknu6frq","KYsy2/tt452hiPlORkSJwGCgriw4+8wcCWVQpNWTjqOqgYaw",-8857628225046929759,7132626861753034761,4781524354957221120,-752498183570032528>()
      ),
      OCCLUDE(
         (String)com.yiyiaddon.m.b.a<"s1oko3aasqox6u","Vg8N1sgtZh+whqw+anoKGR+MbVYXcgy01lqgd0BZN9OVDaeQ",-7394153461683333241,3454304330187823994,4745932272282110974,-8450597339622226822>()
      );

      private final String Gk;

      c(String var3) {
         this.Gk = var3;
      }

      public String h() {
         return this.Gk;
      }

      public static String[] b() {
         e.c[] var0 = values();
         String[] var1 = new String[var0.length];
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"seaqc7jpc65hf","x7p8CIZ9+3Mn5NPNaFflFmFtJWXXBjBPSVwBHlTgh4c=",-7472886000515302180,1773224931264546839,-8963383474818367919,2910792561115582820>()) {
            case -1736840021:
               while (var2 < var0.length) {
                  switch ((int)com.yiyiaddon.m.b.a<"s39dn55zmmisxf","fEsw9zWlbqk/ZpuxmzP/cIhO+hkoJ2zzwH9Vb9+2ohs=",7070989781739431034,5017080041408452755,-8909380345044009337,897872267655799495>()) {
                     case 548675351:
                        var1[var2] = var0[var2].Gk;
                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"ssg72lxg8sznp","NULBMXdQJ0H8OcCYvvid8aYK8MLAvhH9pYGdQO8c4eY=",1557650281761414688,2903775303338261151,-5901479586454440562,-6146924651592435829>()) {
                           case 1287809092:
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
   }
}
