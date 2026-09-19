package com.yiyiaddon.k;

import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class k {
   private static final Duration l = Duration.ofSeconds(8L);
   private static final long aV = 300000L;
   private static final int sL = 4000;
   private static final double bx = 50.0;
   private static final double by = 200.0;
   private static final Map<String, Long> aY = new ConcurrentHashMap<>();
   private static volatile boolean ft;
   private static double bz;
   private static double ab;
   private static double bA;
   private static long aW;
   private static boolean fx;

   private k() {
   }

   public static void aR() {
      if (ft) {
         switch ((int)com.yiyiaddon.m.b.a<"s2w5zx9azk37r0","pZx2sjJYpPsb5hetqnXEmiigHSEN6NwDDgSfR2NxVsk=",-1585223108490933483,3807506219219653559,-1920433144549325998,-7902525034392111262>()) {
            case -577172206:
               return;
            default:
               throw null;
         }
      } else {
         ft = true;
         jp();
         com.yiyiaddon.d.b.a(
            (String)com.yiyiaddon.m.b.a<"s3mupyud97s7j4","vwCrojrO82IkMenRrovdnhcHsyukwB/gU7b51EdgtxFFbTYNX9abdrb3aQoSldBfndkIcwxMp6uUmKhMArZ9L0Mv",-9046332597522019872,-6235930967470786936,3001087207606120327,-258423305759886022>(),
            1L,
            TimeUnit.SECONDS,
            k::h
         );
      }
   }

   private static void jp() {
      UncaughtExceptionHandler var0 = Thread.getDefaultUncaughtExceptionHandler();
      Thread.setDefaultUncaughtExceptionHandler(
         (var1, var2) -> {
            try {
               String var3 = var1 == null ? null : var1.getName();
               if (var3 == null
                  || !var3.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s2564dc7yn6pv4","mUbw1TcQPDYl5HujW7ZIjGZc86imlcIYTXV8M83VqdCoZcoZXD58s/4hLR3cOg==",-1470377053241822206,3704762332630732846,-6181388545830064159,-8122609895477810221>()
                  )) {
                  a(var2);
               }
            } catch (Exception var4) {
            }

            if (var0 != null) {
               var0.uncaughtException(var1, var2);
            }
         }
      );
   }

   public static void a(Throwable var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"suu091ukx15gp","5VL4eNJlNqMhTovHQnY5vdNTj8fFGmCSLdOCcPVarMU=",-237317186239204643,482582047458615759,-5239615099594164308,5744524561149178015>()) {
            case 1340124956:
               return;
            default:
               throw null;
         }
      } else {
         JsonObject var1 = new JsonObject();
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3794ckaxb2cqj","n18HfyE6TUE5U/hh2TT6bHF2OHLLyXE6GjeUVbvTJiOy+nXxOcf//8oZ",5310962720648942142,5456923011650441273,-6257343343442757572,3722007670997660641>(),
            var0.getClass().getName() + var0.getMessage()
         );
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s48y07o48gfhq","hcGshcMLuWH3QPF0jvl4dDA4ry4joGfzy4NvrfIp3W4Xl7Kzb+pIVwZBpLbZBu5JESc=",1077257336654041613,-2145005406139381113,-3613148557943622526,102512809664792562>(),
            c(var0)
         );
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s293bqrc6sk2v8","PMJ2X2ySuCQVsypt6i4OOLxiEOLadi/pNRfhxgzMF+D7FSdRyF9eNrgP",-5503541289926944671,1515312745787261507,4597499068884511858,4132029020733122222>(),
            com.yiyiaddon.i.b.x()
         );
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s2snfnh90dwv2d","TWdiIfkMXayWrU4NTiKasXvJRqQTFHH+9rac8BSJgrcvQvGGZWjqxZapYgsxupVSuhVossnFoqg5uKESl8Q=",408449541942930014,3901976295369602058,1588358521653165900,8513522477085800133>(),
            com.yiyiaddon.i.b.bc()
         );
         com.yiyiaddon.d.b.a(
            (String)com.yiyiaddon.m.b.a<"s11daasn37y36g","UN10JnbXrLMLA2pbirCp/G7dRxYF7HvGvT5g4PQH6ME9tkHdiPhll0+ZNPW/NR6dyYughKNhBthJWqtpgvmRGkHjI/NEFOgs",-2965817644254977126,-785290259731310186,1930346245217335305,-6213295800524867939>(),
            () -> com.yiyiaddon.d.e.a(
               (String)com.yiyiaddon.m.b.a<"s2e62vmpwlh67s","VeG1ciCAEktDezcNEmKnc3UNIbM+5nVpXG2eQ9wAsE3GSVBRVnADqxI7JO5MRQ52Y2BpGFY9rktHsEKNTB8=",6787685115187756080,7193916314263091891,-3215222656431242524,3813631909696608190>(),
               var1,
               l
            )
         );
      }
   }

   public static void c(String var0, String var1, String var2, String var3) {
      long var4 = System.currentTimeMillis();
      Long var6 = aY.get(var0);
      if (var6 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"stzjtdbx97vl5","bl9TP9lNNUPp0R/ET6W0F418wCRIMvvgoMLd03AeWdM=",-8398943396495922860,-2027061907400479474,3802645967231290990,5937002224360117791>()) {
            case -1304200271:
               if (var4 - var6 < 300000L) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jbsvtcwscoad","kvmRFZikzI8o1W/mRt5XDwkeBdXRa7tZtpNi9nXIyYI=",6117670129409421937,3790038478422993696,-5262862765385885949,2967852306360677670>()) {
                     case 1263571028:
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

      aY.put(var0, var4);
      JsonObject var7 = new JsonObject();
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"s792u1u0geqfk","urBnixNNmFwCJPJ5p+BqP15GQs27MKq3oCzsoiwFtXWOOamV",-6933098599343381318,8025698788460591925,-3207972955527583417,922005253491714009>(),
         var0
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"sox3cruv5neh6","JO5JTlNhB7COWhZz0Jl6UECcu4DkV+1RFdoIseF7H40QZb+7FBnNYbGOYR8=",-3275848372727827966,169252061036334222,1400816041914183407,-4626423707552407849>(),
         var1
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3794ckaxb2cqj","n18HfyE6TUE5U/hh2TT6bHF2OHLLyXE6GjeUVbvTJiOy+nXxOcf//8oZ",5310962720648942142,5456923011650441273,-6257343343442757572,3722007670997660641>(),
         var2
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"sqm6pnzv9caqz","PL+z8wUh1yoifbtJj1f9Kqd03lPkXi+yiXfKADyG8GVgNqtS",-8017597283038838548,2381474442773541166,2563821439603410826,-7127404062780009644>(),
         var3
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1lecdvgukpxf3","j2Q6ZvsXgPzU/yIhjgPcwXI2KSvDCbFFgp39l0pAI/7BpvRg",2227006743684025906,3139879921266608393,-1739711628952970433,2385079001816352033>(),
         com.yiyiaddon.i.b.gg()
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1qod86aal6ok0","CwUuZVi0KvOWqMp3GGyhD7cvglRDFnxm2Ro9+fEmLlOaE02N",-5699666089757666216,3836631929393613473,-6712036266466956399,-9129752606497273338>(),
         com.yiyiaddon.i.b.a()
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"s293bqrc6sk2v8","PMJ2X2ySuCQVsypt6i4OOLxiEOLadi/pNRfhxgzMF+D7FSdRyF9eNrgP",-5503541289926944671,1515312745787261507,4597499068884511858,4132029020733122222>(),
         com.yiyiaddon.i.b.x()
      );
      var7.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2snfnh90dwv2d","TWdiIfkMXayWrU4NTiKasXvJRqQTFHH+9rac8BSJgrcvQvGGZWjqxZapYgsxupVSuhVossnFoqg5uKESl8Q=",408449541942930014,3901976295369602058,1588358521653165900,8513522477085800133>(),
         com.yiyiaddon.i.b.bc()
      );
      com.yiyiaddon.d.b.a(
         (String)com.yiyiaddon.m.b.a<"su8ot8154lvzl","k0UKH3GY3c/YAWW1ORKEeMK6ax2w/nG8EDK0qJZKud7cLUweY5GLAejsET7nNmRv3XhJUFfVUKrErSyBj+Ht6Pvg2R6TK8AAB3eO/g==",8423964431199378904,4495212892044419457,-2138872600485886457,3736572442235515137>(),
         () -> com.yiyiaddon.d.e.a(
            (String)com.yiyiaddon.m.b.a<"s1ure0izuqjr9u","kSTxL5wvmik18JwrT0PR8+bE9vFC92o5KrEFKmUKVYW/dkQhWxLt3MaZOKu/RzxRhgAZ6JXsvzJQ8yN/ViQeWygS",-7153960102085524605,4664413643315135121,3448094077208782893,4761163670224262115>(),
            var7,
            l
         )
      );
   }

   private static void h() {
      Minecraft var0 = Minecraft.getInstance();
      LocalPlayer var1 = var0.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lwwp4eqqwqhb","t8evhVNaUny0rXflXDJKY37d7Del/E0hReSca+FSjWg=",6921451763371819514,123581898105197958,-7363170364034473698,-1192920481035529263>()) {
            case 240403666:
               fx = false;
               aW = 0L;
               return;
            default:
               throw null;
         }
      } else {
         long var2 = System.currentTimeMillis();
         double var4 = var1.getX();
         double var6 = var1.getY();
         double var8 = var1.getZ();
         if (!fx) {
            switch ((int)com.yiyiaddon.m.b.a<"s2yjbcposyn616","ZIKrZLmGuE8jEXCRm31g8FODjFBcVttAycF2zyqQbXc=",-9118090175565685100,-8611240890170992825,-7720365169152739543,-4984782153764260622>()) {
               case -1890221285:
                  bz = var4;
                  ab = var6;
                  bA = var8;
                  aW = var2;
                  fx = true;
                  return;
               default:
                  throw null;
            }
         } else {
            double var10 = Math.max(0.05, (var2 - aW) / 1000.0);
            double var12 = var4 - bz;
            double var14 = var6 - ab;
            double var16 = var8 - bA;
            double var18 = Math.sqrt(var12 * var12 + var16 * var16);
            double var20 = var18 / var10;
            if (var20 > 50.0) {
               label33:
               switch ((int)com.yiyiaddon.m.b.a<"s2rh7u5jbcoo92","BVzh61fB5JMYFZkxEtMcDvOsoT84oSZQ6aV8Sg01lck=",8771424652374250990,-446559472371339996,5176980413572003407,-3439703631213552433>()) {
                  case -1684351163:
                     c(
                        (String)com.yiyiaddon.m.b.a<"s3ptqwiw6h3d5n","/7EFm0cuLLJEy2VRVnUERlmdI/LhI+Nvxn6o7l/XJNKuAIbUXjDBC7+3zJDi1o97",-6143486239044665716,3382716794595196786,-3731547180573657419,7775968939954884954>(),
                        (String)com.yiyiaddon.m.b.a<"s2g4327bwdyea1","ANVv6H84au5GT112sDcHbvEP/jycq8krBIpBDEitRgKV/krq",4448597690559797512,3843756144790599871,-7339335703175954193,2558117223958629690>(),
                        String.format(
                           Locale.ROOT,
                           (String)com.yiyiaddon.m.b.a<"s2ql34h4kspa15","f444H0vqhKVv/l+WHipjGRnGoftCRK94UtTwEQ/skuf3sgK/vBzy8ElchlG5/IacOLY4cMKF",8649578318501128671,3367769378343990691,1094855872497405527,5373421304890988661>(),
                           var20
                        ),
                        String.format(
                           Locale.ROOT,
                           (String)com.yiyiaddon.m.b.a<"ses2ibkgdbi7m","xlJqNHPeaOfgY1aELpNCw33l1e/eP0LnQMy8daUJysp6n4jcuTiF8cy51/lmi2Y33AdV4q2Hh1BPY/SZfZ5XBK9k0aVFuo9zVttVAQ==",-3716152716811730536,-96328621236446081,5050357566567257520,7869620460614159581>(),
                           var18,
                           var10
                        )
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s2v086ap1a5biz","qBNFaxToCdhfB0nvnHQcqkD6EIa7uGqsbqEY3LgPIf0=",7289073114293364520,2269459305082402215,4260656947379677846,861088072744362797>()) {
                        case -834727504:
                           break label33;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            double var22 = Math.sqrt(var12 * var12 + var14 * var14 + var16 * var16);
            if (var22 > 200.0) {
               label28:
               switch ((int)com.yiyiaddon.m.b.a<"s1ysbmsb7zvnt","ZSYQrg9ATrI8TjSiYtHgYjIkZFUaX6hmIzvcxSGaRxk=",3717443539624035692,6148595009992111073,3334659375048433499,8759352755746475586>()) {
                  case -2032050224:
                     c(
                        (String)com.yiyiaddon.m.b.a<"s3t1bc8vbpht9r","ZjmCidBTH39+30c3MFmScTDWi3qIZzIOlcSVaIlQmm7suZXKDijQ18GMtKg=",-6372831465631769170,3375119887134688970,-3900023078927736827,5929926515858354471>(),
                        (String)com.yiyiaddon.m.b.a<"s13z0yvxj82vnm","uKLQbx4jDVt344fR9p4Rz4D0U8CIp2B+VUcJuy/NYQYayqCwa+D5vg==",2832101999177668845,-5089586426138453082,9171593739828148912,-4121656857223909476>(),
                        String.format(
                           Locale.ROOT,
                           (String)com.yiyiaddon.m.b.a<"s2fvuybo7o4dnf","APrExDLsRKDO8+ox6X5mHB21ZZ6KTRerJTvldJAY9VyiEah78Jl2LTwCT8QB4/wGVLo=",-6489221816857799989,-432440622945230194,-3163498948518689610,-3134617422345365179>(),
                           var22
                        ),
                        String.format(
                           Locale.ROOT,
                           (String)com.yiyiaddon.m.b.a<"s2p96zmfdufaal","qFXTI4CIx0juL7f7ez0RlCgR/aILcOYE8H/eIOeMkqVSJDQsdpcnZTm+CibkyFR18grhTe66ptShKPC9r9xeHRyT3ipmBz2sFownOth+",6963473970690474315,5808459000697236328,-7423770592467690715,5524546928088712943>(),
                           var12,
                           var14,
                           var16
                        )
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s20qvawb1sf71e","iLdlhuL8sQ11PMW2CDGjt5PTz853Q6mmnwKbnDkbfjE=",-6858872204577600709,-1325831924620372040,-8777521975099580238,-1112347775393303776>()) {
                        case -401192199:
                           break label28;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            bz = var4;
            ab = var6;
            bA = var8;
            aW = var2;
         }
      }
   }

   private static String c(Throwable var0) {
      StringWriter var1 = new StringWriter();
      var0.printStackTrace(new PrintWriter(var1));
      String var2 = var1.toString();
      if (var2.length() > 4000) {
         switch ((int)com.yiyiaddon.m.b.a<"skl823xwn5lbj","DO7LT2bmr9x6fsernXEO2NwXT0+uRyhXs7aw+gDHqtg=",6457608449586028114,-6910761519105606484,-4966957618318890475,8307291822560231933>()) {
            case -1600307815:
               String var10000 = var2.substring(0, 4000);
               switch ((int)com.yiyiaddon.m.b.a<"s915dtgj7vr68","BqP6hv01lEPRegva51k4zL83rjCl1psvYnhI3AdW3Bw=",6346433921930893045,-1119427732854798195,1392701584205814448,6947137862021631677>()) {
                  case 1427422700:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s26zl20gau9h3m","64/x1NMmf1O5m8oOTIiw2gLmdIn22xmswk6xfBeMAf8=",1208326381932353863,-8011001648877433692,1295982336424506002,-157770437358051607>()) {
            case -470717757:
               return var2;
            default:
               throw null;
         }
      }
   }
}
