package com.yiyiaddon.e.o;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.d.c.d;
import com.yiyiaddon.d.c.f;
import com.yiyiaddon.d.c.i;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.phys.Vec3;

public final class a extends com.yiyiaddon.d.b.a implements com.yiyiaddon.e.o.b.c.a {
   public static final String vI = "antikick";
   public static final String vJ = "发包防踢";
   private static final String vK = (String)com.yiyiaddon.m.b.a<"s2o8lsupj7rkh2","KKKx89U7qCeyJEkB0wpz96eJMDvOjt9D0MxgoJ6Bx2tTIzdSO94D0XCB",-1909651437884733353,-872511372667178979,-8040182852382965189,400561715712257102>();
   private static final String vL = (String)com.yiyiaddon.m.b.a<"st8z07rnoqxtz","2XLraTwST14gQw3MZFJThRx2uJJOp2wwg5ai3OJJgHkpsp+0tbJ0uyJiWWylOitdOPLXWdFYgAj/VQ==",-3443456118393430492,-656014908706119104,-3975199600871554306,-8629306373933863188>();
   private static final String vM = (String)com.yiyiaddon.m.b.a<"s34zzvxz5ws8kg","0bUCewRwJqV8PXZIHGcLw5xkwzjiFlEqwLNdq2JWzJoBQ8ibUVOX5kEY+4mw5rvoNP61j9OscOff9od4ymnQsw==",-3290499390551025901,-413747558228019476,7785649403198996426,2298938123660684844>();
   private static final String vN = (String)com.yiyiaddon.m.b.a<"sqmjnrb461ijj","NQMM+nPGqWA2FVQ0Cheto9tWYXDv4kGb1Eft+r1t9rqm1armUwscviSeW79zKjFOouALtN+1ExolC+gIhZmzrju+gfQ=",-5086500938712428242,-2631541597749667416,8655326808197093388,-2077478003396069452>();
   private static final long X = 1000L;
   private static final int om = 2;
   private static final double ay = 0.16;
   private static final int on = 100;
   private static final long Y = 5000L;
   private static final long Z = 60000L;
   private static final double az = -0.08;
   private static final double aA = 0.3;
   private static final double aB = 0.5;
   private static final double aC = 0.4;
   private static final double aD = 0.4;
   private static final double aE = 0.5;
   private static final double aF = 0.01;
   private static final int oo = 3;
   private static final int op = 6;
   private static final float dq = 90.0F;
   private final Minecraft ag = Minecraft.getInstance();
   private final com.yiyiaddon.e.o.a.a a = new com.yiyiaddon.e.o.a.a();
   private volatile double aG;
   private volatile boolean ej;
   private volatile boolean ek;
   private volatile boolean el;
   private final AtomicInteger b = new AtomicInteger();
   private final AtomicInteger c = new AtomicInteger();
   private final AtomicInteger d = new AtomicInteger();
   private final AtomicInteger e = new AtomicInteger();
   private final Set<BlockPos> al = ConcurrentHashMap.newKeySet();
   private final Queue<String> a = new LinkedList<>();
   private long aa;
   private int oq;
   private long ab = System.currentTimeMillis();
   private volatile double aH = 1.0;
   private long ac;
   private long ad;
   private boolean em;
   private final List<com.yiyiaddon.e.o.a.a> cd = new ArrayList<>();
   private volatile boolean en;
   private volatile boolean eo;
   private int or;
   private int os = 5;
   private Vec3 c = Vec3.ZERO;
   private final Random a = new Random();
   private static final String vO = (String)com.yiyiaddon.m.b.a<"s3fqq90ur9h91q","xCvg9EJV/Gt3GvQdN9bGDsz60TQFAQAbgjlf+U1/",-1618974424516697749,-3367517307711550256,-3594753859509197755,-9004668148558425617>();

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s1gcdm3v73ori7","Yi1buhsr5hZLwOFqHJ9axQTACEWWlstx9eJp4HTDGicPU2CksbvG3jy4x4k=",-769966200661889691,-5749175750733783372,-2214577470717607024,8206450789569345403>(),
         (String)com.yiyiaddon.m.b.a<"s1cf0x0s3eqp2w","84P34TU0fOCI5IgJOEEiJz0RXJHHTffYznx1WwdrkROV/JB3",-5408014209708381826,-8513762789296226755,-1549994082537097059,-4825256776926227876>(),
         (String)com.yiyiaddon.m.b.a<"s1y1skfvm9s8gl","Ci4HwGkBD1431E42EFsg4rvtBlS8iO4eMqeXGnYdwSieQwxuP/WARg==",4977037410912261062,8309519785388001961,-6299495283010136872,-8653443670720004089>(),
         (String)com.yiyiaddon.m.b.a<"s346y3hsxjdxiq","R+GRLZ3IZsiZ7hHqnXiLc/ypHCZvr5cD/c9Tc9EJmT4KqlYuy0L97APJm2Uf/I4EPPdS4o0G/WDK4rdtlGyNJHNRS3r7YMbMz3RwmSpfP0E83vJmmRCa4jXaKoq/kjkC",-1202998861206656072,-1075829958970176639,-9177141698128114428,-886522753433221011>()
      );
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s3fqq90ur9h91q","xCvg9EJV/Gt3GvQdN9bGDsz60TQFAQAbgjlf+U1/",-1618974424516697749,-3367517307711550256,-3594753859509197755,-9004668148558425617>();
   }

   @Override
   public int i() {
      return 30;
   }

   @Override
   public boolean h() {
      return true;
   }

   public com.yiyiaddon.e.o.a.a a() {
      return this.a;
   }

   public double e() {
      return this.aH;
   }

   public int cT() {
      return this.a.size();
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   @Override
   protected void m() {
      if (this.ag.hasSingleplayerServer()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kf4w43aek8hy","wiTJ9S2u+sH/dl7RW/yFgaaqkZNTBCcOzXc2aKGwtGU=",4326551223613988149,8437742376012815071,-941366317191820764,8427531458321801987>()) {
            case 1835091442:
               com.yiyiaddon.d.b.e.b(
                  (String)com.yiyiaddon.m.b.a<"s1gcdm3v73ori7","Yi1buhsr5hZLwOFqHJ9axQTACEWWlstx9eJp4HTDGicPU2CksbvG3jy4x4k=",-769966200661889691,-5749175750733783372,-2214577470717607024,8206450789569345403>(),
                  false
               );
               this.ad(
                  (String)com.yiyiaddon.m.b.a<"s2ux0ef7y439d6","TOMLdeGoFXPlTTkthXK9Dj1cBL9jPBsHjYyJEHLuMS7Me46flUwbBfoLpNDJVck+",-5584927472051317600,5762373340688036394,7470242139678829144,5106754747563040887>()
               );
               return;
            default:
               throw null;
         }
      } else {
         this.hN();
         double var10001;
         if (com.yiyiaddon.e.o.b.c.eF()) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"s3w1mf7ou37eyt","ZTPG5Nbw1Z6SSLDP3Vqt6G3YuxcgRZr6tzZDy8wblvk=",-1206671137082858983,-2592608918819089467,-6528366480905837918,1491390213114707027>()) {
               case -886364890:
                  var10001 = 0.5;
                  switch ((int)com.yiyiaddon.m.b.a<"s1t2zx5dvg0vg9","coemPGgi2+G3rvYHbwkRMp7WECekhZ9Q0MkqbNkJ3to=",1797475360089707568,-8959507870335114352,-8621269462324681568,-7678889458544955054>()) {
                     case -1465359577:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = 1.0;
            switch ((int)com.yiyiaddon.m.b.a<"s10gqiamscdteo","8TOkpRcK0GY0+Icopqgeq011gNbvUSuR+zHmRGoM+9Y=",-833929865076517936,-6802492523082206299,231194653606096898,4601533284121173676>()) {
               case 791199709:
                  break;
               default:
                  throw null;
            }
         }

         this.aH = var10001;
         this.em = this.a.eF;
         f.a(
            (String)com.yiyiaddon.m.b.a<"s1gcdm3v73ori7","Yi1buhsr5hZLwOFqHJ9axQTACEWWlstx9eJp4HTDGicPU2CksbvG3jy4x4k=",-769966200661889691,-5749175750733783372,-2214577470717607024,8206450789569345403>(),
            this::b
         );
         com.yiyiaddon.e.o.b.c.a(this);
      }
   }

   @Override
   protected void n() {
      f.l(
         (String)com.yiyiaddon.m.b.a<"s1gcdm3v73ori7","Yi1buhsr5hZLwOFqHJ9axQTACEWWlstx9eJp4HTDGicPU2CksbvG3jy4x4k=",-769966200661889691,-5749175750733783372,-2214577470717607024,8206450789569345403>()
      );
      com.yiyiaddon.e.o.b.c.b(this);
      com.yiyiaddon.d.c.e.b();
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.JOIN_SERVER, com.yiyiaddon.d.a.c.DISCONNECT, com.yiyiaddon.d.a.c.CLIENT_CHAT);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s296lma9ggmy83","HNsRwwcKAffmTPASig9+ZEoooAus5p76D03XDWJKaRc=",-341669335939147459,-2036143029066424264,-2470982482555723834,-3835960473659507646>()) {
            case 1447151800:
               if (this.g()) {
                  switch (var1.a()) {
                     case JOIN_SERVER:
                        this.hN();
                        this.aF();
                        switch ((int)com.yiyiaddon.m.b.a<"s2vkveqh32ygmu","OAUUkHgsD4rzUsXFa/ERd+nF6Nom3IkzHWfsMpjEqBA=",-2370680336900600109,6859732910413304124,260669882966379022,-5524693837456447396>()) {
                           case 932184288:
                              return;
                           default:
                              throw null;
                        }
                     case DISCONNECT:
                        this.hN();
                        this.fP();
                        switch ((int)com.yiyiaddon.m.b.a<"s1itwv5jngy436","R4i5CLwPNC+5KzlAI9U1W4U5bIMbTxcIOEnTViczbrM=",-1608202583548571442,8774070861365381415,-1359972298639453590,-7328839925208699639>()) {
                           case -42241178:
                              return;
                           default:
                              throw null;
                        }
                     case CLIENT_CHAT:
                        this.aH(var1.l());
                        switch ((int)com.yiyiaddon.m.b.a<"s2ptovcho3r1c6","E46Vt7u2d432/yIK5dTlInKKUGWaKFVbE0HxotCHjbI=",7209183902406951903,-1033388026429723668,-1086029235091020683,5427690395824802851>()) {
                           case 2142538774:
                              break;
                           default:
                              throw null;
                        }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1x0f5di4ijwaz","GcHmTnW6f+F3c8Gc4wYhMPDwJNy3f9iTwbtxS9vJ5P4=",4879244536058226114,5141051113877461653,2292214144445979317,-4361005287573439792>()) {
                     case -237322935:
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

   private void aF() {
      this.ad = System.currentTimeMillis();
      this.d.set(0);
      this.e.set(0);
      this.hM();
   }

   private void hM() {
      String var10001;
      if (this.a.ev) {
         label57:
         switch ((int)com.yiyiaddon.m.b.a<"s2p49lll53ds63","0x3E77xcD8I/3iKbDnnHFy0gtCl0X4CI6aJIZYfOPF4=",-6752557845508839375,966907022976011868,5074406764720164880,-63329260542771345>()) {
            case -818939820:
               var10001 = i(
                  (String)com.yiyiaddon.m.b.a<"s2o8lsupj7rkh2","KKKx89U7qCeyJEkB0wpz96eJMDvOjt9D0MxgoJ6Bx2tTIzdSO94D0XCB",-1909651437884733353,-872511372667178979,-8040182852382965189,400561715712257102>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2840siqvx4r2b","AfjBE3HRspID6OLWkfx15MbBOGx+5UUZ19S3YQ2WqtE=",-1703419961225974044,-1444081362568858158,-426416270933767172,2508501068067720319>()) {
                  case -1073679624:
                     break label57;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s2utxjrrnuidzz","8FCaQuEx85+TtlZ24vewie2LGLKWRGgL3wb2MsxtbhxVS7I/edA=",4812825575723659346,8343283148685870692,5766315343012322782,7454391488875373643>();
         switch ((int)com.yiyiaddon.m.b.a<"s1vyqv8zrnpae1","DpUWJxb0M0xOleACWHWhkqj5ZlHBW/iysTJCeuc8qYU=",3376578526325362384,-136952858554230802,-1690660377010163534,-2057744352912264579>()) {
            case -621350162:
               break;
            default:
               throw null;
         }
      }

      String var10002;
      if (this.a.ew) {
         label50:
         switch ((int)com.yiyiaddon.m.b.a<"s3u2081yw84o27","iFLGt79LZCuda7WmMTyoHIQt1iQ+E4mudLMV70CmGKM=",-1474375923261170243,3574905351582568673,7188428118339845122,5588418631784614074>()) {
            case 1714853793:
               var10002 = i(
                  (String)com.yiyiaddon.m.b.a<"s3gh4a504qkvv","Tr25+VG9GR/8yhVgEG+bjD92MZrugoLmme8QwqdYbQr89Q==",-3969783821860413286,-5667348447155135993,8355330038293919132,-5622798780798213997>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s29av3jc5hlojr","adXTH8LQrZJy+AeDdnv79DigIyHPFwCx740YJnsnO3E=",-1701336048408987233,8273129143752572253,-6124730040354412642,-5212572435105592063>()) {
                  case 1888486681:
                     break label50;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = (String)com.yiyiaddon.m.b.a<"scszxlcay2816","IuX4i1y6KCrrMfsC131LVEaAvhsVGukCd/ikmYvp1jjCLLjgLgo=",1417042678423912577,-5177593433696145460,90379813003529081,3011347978103322937>();
         switch ((int)com.yiyiaddon.m.b.a<"szsexmc4xwkyb","SetNbUa5prThPcZnbh3n+p2Pc0guTuJJVUisiJNcUPA=",-5777571702527346260,-137387512783736366,483449976731971088,-4409341135262556919>()) {
            case -1122390366:
               break;
            default:
               throw null;
         }
      }

      String var10003;
      if (this.a.ex) {
         label43:
         switch ((int)com.yiyiaddon.m.b.a<"s2ygqeygvos957","r4LOFNdbuveDLQuqpFMtQBKahj4xtP7YtnN2b37DahY=",-4093826662379407531,-6114810561407816360,5645459391541486551,8634302847596549193>()) {
            case -509925696:
               var10003 = i(
                  (String)com.yiyiaddon.m.b.a<"s3gh4a504qkvv","Tr25+VG9GR/8yhVgEG+bjD92MZrugoLmme8QwqdYbQr89Q==",-3969783821860413286,-5667348447155135993,8355330038293919132,-5622798780798213997>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1p8sgt7qxk96z","uXhjgEaJ540MUgBEOrZ/B7mC5C/bogXFjYdEYuyXcgY=",-9034126669888361073,-7006285125521172423,27443010004595766,-151299975720159837>()) {
                  case 1662384973:
                     break label43;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = (String)com.yiyiaddon.m.b.a<"scszxlcay2816","IuX4i1y6KCrrMfsC131LVEaAvhsVGukCd/ikmYvp1jjCLLjgLgo=",1417042678423912577,-5177593433696145460,90379813003529081,3011347978103322937>();
         switch ((int)com.yiyiaddon.m.b.a<"s7cn4e79bfmf1","j8Wq6VI8/YeKkfykSW+vzj8mRYnR75lieRhKu9WWNmA=",3193974060741190833,8341538058956078281,4662934926332637134,6615204089948951608>()) {
            case 1171310324:
               break;
            default:
               throw null;
         }
      }

      String var10004;
      if (this.a.ey) {
         label36:
         switch ((int)com.yiyiaddon.m.b.a<"s3ia551aeebyi6","noKlFUsfOL5ppQp1j+WNziMaamnkPuQxuErnTDdM12Q=",-423664807208823012,-8352738793242038260,-2096653008055669634,-3881084362288874171>()) {
            case -440933087:
               var10004 = i(
                  (String)com.yiyiaddon.m.b.a<"s3gh4a504qkvv","Tr25+VG9GR/8yhVgEG+bjD92MZrugoLmme8QwqdYbQr89Q==",-3969783821860413286,-5667348447155135993,8355330038293919132,-5622798780798213997>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3hkfz0n741s6x","25hldgXyciSPTL7BqpwTqbfX1PogROhsDZhfpTS3cUk=",1724800687069160845,-4302841327860165383,-6675822348697170343,8950666969213756432>()) {
                  case -253984507:
                     break label36;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10004 = (String)com.yiyiaddon.m.b.a<"scszxlcay2816","IuX4i1y6KCrrMfsC131LVEaAvhsVGukCd/ikmYvp1jjCLLjgLgo=",1417042678423912577,-5177593433696145460,90379813003529081,3011347978103322937>();
         switch ((int)com.yiyiaddon.m.b.a<"s26nnzwtcbbq1b","zpjOYAOGIQj9dbBHGkZc10u+zjp3HsEGBoIN09kutNg=",-4231783712773525303,2502051238138317016,7384619573932878679,-202739930639086355>()) {
            case 373617560:
               break;
            default:
               throw null;
         }
      }

      this.u(var10001 + var10002 + var10003 + var10004);
   }

   private void fP() {
      long var1 = System.currentTimeMillis() - this.ad;
      if (this.ad > 0L) {
         switch ((int)com.yiyiaddon.m.b.a<"shkbp8y8kcgmh","ru0pRbTdNrPhPX8+Gb4OIGTvRLSwEE5sztX9qzn10eQ=",-597783163782291073,-2464496185946258138,-417896032103534409,6636728677690366356>()) {
            case 1223120821:
               if (var1 < 60000L) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2yy150x2syex","H8Nsj1SNbAt2BMbh4XjG3RC0rD4UprCh8MER6ZjcD/k=",-3355597749510368363,-1990383094466629469,8760219780022587314,2786585432579308997>()) {
                     case 932651070:
                        if (this.a.eD) {
                           label33:
                           switch ((int)com.yiyiaddon.m.b.a<"s15xzahruoffiu","Ty4IC3YoyJdZbj9UT7/3l5bZ3NpfKmyrFlSfDRx4EXE=",-2921945165224187636,7874579157454152019,-4135750022875800734,-213072179788709620>()) {
                              case 1870067236:
                                 this.o(
                                    (String)com.yiyiaddon.m.b.a<"s1todclq88thng","r/xYsZZfpbC3gkLAAXYqB5Ljq3kWFIg2CEI3kMDpIVgpDm6VUBe/qFhLJguMLIUGEJaRGyQBTK+Q0N5hsWQ=",6556243627643519352,-2575456153621612837,5128802295553357901,1273064048669993870>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1p09oq8r6y8tv","srYjfYyg1jeqjZ7EkmnJUsOC5JsWiwRuKDMB6RoEyUs=",-495118563210463929,-6810069872856408471,-7277214303565238726,-1460672627567743494>()) {
                                    case 381788292:
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
               break;
            default:
               throw null;
         }
      }

      label44: {
         if (this.d.get() <= 0) {
            label29:
            switch ((int)com.yiyiaddon.m.b.a<"s2du02dnqeloll","h6Rjt0AUkz1OB5mbu6s1XPv2zEqBcKW/UTTEZ5na1cQ=",-4110039831527516054,-249486332238702950,-6156366723208675702,-8440187976402779490>()) {
               case -1608909607:
                  if (this.e.get() <= 0) {
                     break label44;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"su4wjhky873em","iogWt/OhBFvgVIDoUllhrZ5hMHiv/Q9ZcVaOd3Mpe4Y=",6626043349795163664,4399902556086297081,6714582978821511617,4765512853152709879>()) {
                     case 2138704599:
                        break label29;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.u(N(String.valueOf(this.d.get())) + N(String.valueOf(this.e.get())));
         switch ((int)com.yiyiaddon.m.b.a<"s2o6hh96hm0dx1","UYHZbqX+1+BbpxspBn6jo6hkn9RFaoWReOiEUBikzSM=",1965164525865676620,3478514607681287631,-4013603239142719789,3184871531269245933>()) {
            case -551363008:
               break;
            default:
               throw null;
         }
      }

      this.ad = 0L;
      this.d.set(0);
      this.e.set(0);
   }

   private void hN() {
      this.a.clear();
      this.aa = 0L;
      this.oq = 0;
      this.b.set(0);
      this.c.set(0);
      this.al.clear();
      this.ab = System.currentTimeMillis();
      this.aH = 1.0;
      this.ac = 0L;
      this.cd.clear();
      this.en = false;
      this.eo = false;
      this.d.set(0);
      this.e.set(0);
      this.el = false;
      this.or = 0;
      this.os = 3 + this.a.nextInt(6);
      Vec3 var10001;
      if (this.ag.player != null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s330xonkxhy8bz","L4Yz96nKfBIBRvJgHduaOzivS/GYL6gwaeHQ/UBKY/4=",-6665930798690148287,-6695614487459498414,-2431222352593568699,-8222239707773884848>()) {
            case 438420241:
               var10001 = this.ag.player.position();
               switch ((int)com.yiyiaddon.m.b.a<"s3kcmdb2oylfm7","049KZuFtIBU46kInqBMqQinmPQx4I9iU1L8VHNUbC10=",6133712618028777873,9014165410775066387,-6453654896498831722,-3630260556366288764>()) {
                  case -837408728:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = Vec3.ZERO;
         switch ((int)com.yiyiaddon.m.b.a<"s1uzxdahjifzqp","haiovSlvz2QxI72NOyXkjnGOxR60u53hKt2Qn0vfOJg=",2745048932149468412,319901398831489480,4005188072816659834,-2073871684724469698>()) {
            case 1200139890:
               break;
            default:
               throw null;
         }
      }

      this.c = var10001;
      com.yiyiaddon.d.c.e.b();
   }

   @Override
   public void b(Minecraft var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2yt69trcu2ell","+bZSrLCMejMaSNIrdHRMBKuF9U5V8ogw5d2bFVUwngY=",6631145888486585143,4078907138606317084,4711387671132206356,-6346002796253204748>()) {
            case -438863814:
               return;
            default:
               throw null;
         }
      } else {
         this.hP();
         this.hQ();
         if (var1.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2bqk4lre15ryh","y8C2Z+wXafME2+oyQmijdo7cD+oF1aY/ePHbi0Yq2hU=",4297712538212036370,-407988027973660625,-5738529768290280335,5743912828114197460>()) {
               case -612697179:
                  if (var1.level != null) {
                     this.f(var1);
                     this.hO();
                     this.hR();
                     this.g(var1);
                     if (this.a.eE) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1ry9tok5yzqtd","utvmuF0CRaLbIpMuAoxg2GomaV5t1vZqfPHM59GbUBI=",-5372836158005588281,-2489352537326101763,7520592133622991541,298649903727054756>()) {
                           case 722751329:
                              this.h(var1);
                              switch ((int)com.yiyiaddon.m.b.a<"s2ki7c607o4anv","2qaK3LW+T2Dat5uzeoMdsWAJ97Ae90+EtA1uUcREpTY=",3078525982112385843,3922352522857269107,9042382912744358227,-1473586216997678164>()) {
                                 case 275955124:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1lqyudrosw2g2","hkrQokgJmVZFs2XHWDEEu16qyzNve+Rc3PZGGVWGPLU=",3858320600975844446,-2011701347240069145,-1559861312604543771,-2722304575873924341>()) {
                        case -1675410087:
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

   private void f(Minecraft var1) {
      this.aG = var1.player.getDeltaMovement().horizontalDistance();
      this.ej = var1.level.getBlockState(var1.player.blockPosition().below()).getBlock() instanceof IceBlock;
      this.ek = var1.player.isSprinting();
   }

   private void hO() {
      long var1 = System.currentTimeMillis();
      if (var1 - this.ab < 1000L) {
         switch ((int)com.yiyiaddon.m.b.a<"s3iznyjaw3uhxd","TdSG8USPIOEuAyg1uJ1T6J9tmjN14Gt1JFUEEP1PXfY=",6950916454245249023,-396860109298131743,5416028661100599754,4089293185334266466>()) {
            case 1414283914:
               return;
            default:
               throw null;
         }
      } else {
         this.b.set(0);
         this.c.set(0);
         this.ab = var1;
      }
   }

   private void hP() {
      boolean var1 = this.a.eF;
      if (var1 == this.em) {
         switch ((int)com.yiyiaddon.m.b.a<"s34mkgd9g92c9y","viWKt2htinv2NmLljih7iXadRKc/4OHkJtW0cDU6hrU=",7573638578992440153,-7347965360269832615,8296113321383844075,8117877403576541560>()) {
            case 643686754:
               return;
            default:
               throw null;
         }
      } else {
         this.em = var1;
         if (!var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s278q6ha4oqwlc","hYUhjBAP9zkuPh71htS9m77uBJ6jL7UZMq84HoteQT8=",2563049965737291163,-2315555845943868920,3404372058090448946,-897553755554095146>()) {
               case -1093901414:
                  com.yiyiaddon.d.c.e.b();
                  switch ((int)com.yiyiaddon.m.b.a<"s1drxkwapy3l7d","xLGFZVDtiJ9iAJDtwFBw9EkhrPAAKus1BztW2CNzwbk=",-8861284382810177545,-2602183542107279595,3901865922027190235,6306265419247129435>()) {
                     case -2057246074:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private void hQ() {
      if (!this.el) {
         switch ((int)com.yiyiaddon.m.b.a<"s35xrbt9orltnz","8KFPonq8/Xq7VU9oeZpMuPgknAmHpnY3k4rdf2FNGaQ=",-5204859423774431697,5554145425726561709,-312434498867885579,1025919053437475061>()) {
            case -274484528:
               return;
            default:
               throw null;
         }
      } else {
         this.el = false;
         com.yiyiaddon.d.c.b.k(
            (String)com.yiyiaddon.m.b.a<"s2o8lsupj7rkh2","KKKx89U7qCeyJEkB0wpz96eJMDvOjt9D0MxgoJ6Bx2tTIzdSO94D0XCB",-1909651437884733353,-872511372667178979,-8040182852382965189,400561715712257102>()
         );
      }
   }

   private d b(i var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ws0btjcfygjb","ZVcqOYSEJaPxoFuWUIWAx3Lw4w2lR2nC1Q9UcqO1WWI=",8117490818430715253,-1865765181620463684,9055963049663575580,-5921667275372736213>()) {
            case 1410742696:
               return com.yiyiaddon.d.c.d.a();
            default:
               throw null;
         }
      } else {
         if (var1.a() == i.a.CUSTOM_PAYLOAD) {
            switch ((int)com.yiyiaddon.m.b.a<"s17vf2ue5v0si1","8pz6MUNiw5LxFPj9kPBuceCBZXvqKLG1GRglujn5kpY=",-1294529255576962623,4990619220537389860,9146904108709305226,-6007715515032106834>()) {
               case -464670506:
                  d var2 = this.a(var1.B());
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s32yszbut2958t","AKhmYtFQgb43SLMsZDOBovdmjzVIeRH0fEd/5Jn04X0=",-5661054468227651175,-5006018133019542044,-800451323080628834,7023891748397778331>()) {
                        case 1341059940:
                           return var2;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var1.a() == i.a.PLAYER_INPUT) {
            switch ((int)com.yiyiaddon.m.b.a<"s2umdw5c4z0ebp","f4T00zR9TndE4SrYaACgpspqzrp4AzZWZix91XcUh78=",-7169731286264914097,1724176722610391855,8620834108429899935,-5629893139109611354>()) {
               case 1166022451:
                  d var3 = this.c(var1);
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s36gwo6djwf5y2","DU+kZIlWFqSCkavCaTNOCfFJfe+0no0jMTlPiwTJF7A=",-7963619696190661895,-2295286263788424522,4005142220668565718,-6017295121260059509>()) {
                        case -1402053347:
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

         if (var1.a() == i.a.CHAT) {
            switch ((int)com.yiyiaddon.m.b.a<"s29al93sfpprqx","tHRY3FHjih/rRyuv0zm5Jute7H1aFdUa8b7rJMBTUt4=",-2086207128516256092,-8535306834072038797,-1818033531697388592,1617063533177560212>()) {
               case -1883408460:
                  if (this.a.ez) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2fszikzzhagk0","R+CPhikiM9l9DIPObusseZd2VZ0kjyaALhPdM/IAGiQ=",6532717413959236122,7635874339557978888,-4532617216405552351,799553371441283574>()) {
                        case -1660001944:
                           return com.yiyiaddon.d.c.d.b();
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         d var4 = this.d(var1);
         if (var4 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1hdslb7djwu1f","ymD5Gfl+Yh7n4AlgJV3tK9NfsPeNYpzycjft2EREISw=",8518736639468488287,8008350922387891811,1951293185500635294,-7440357396973687564>()) {
               case 1200267936:
                  return var4;
               default:
                  throw null;
            }
         } else {
            if (this.a.eF) {
               switch ((int)com.yiyiaddon.m.b.a<"sy99itoxvhzx5","2+/h0mqS/shmCdYIWfF87afC8uUJhtr9fMMNco12GY8=",1984299836110224667,-5730629559033350919,2498021969119161186,1498280435081951728>()) {
                  case 1674298720:
                     if (a(var1.a())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3jsywp76lnaf7","NwOzGRVDEEgBXvKoDGNVjj89+LiGWU4ux8cz+m9BdCE=",7961786643415291201,1243105756719933591,3421545217750730270,3462210686227522312>()) {
                           case -1745559619:
                              return com.yiyiaddon.d.c.d.a(this.q());
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            return com.yiyiaddon.d.c.d.a();
         }
      }
   }

   private d a(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2exla9ebc0e8o","mIvEwVToravZvI2ig9W4m0ZDMwnH960bw5QfYjN619o=",-9131848789660965137,2095664857608976942,3558584420013614340,3361573439774477573>()) {
            case -1444562660:
               if (!var1.isEmpty()) {
                  if (this.a.ev) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2sxbuqd3543ab","HZ0Wj8tekCzmWa8Wnj5YUc3fM5/MJ4dVyKXrbAZ0BGI=",-2963997818383121434,-6661323310399817574,-7976393131519316171,-5214375078557033649>()) {
                        case 2009702576:
                           if ((String)com.yiyiaddon.m.b.a<"st8z07rnoqxtz","2XLraTwST14gQw3MZFJThRx2uJJOp2wwg5ai3OJJgHkpsp+0tbJ0uyJiWWylOitdOPLXWdFYgAj/VQ==",-3443456118393430492,-656014908706119104,-3975199600871554306,-8629306373933863188>()
                              .equals(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1jy3m0p0fah1g","z8h9tMsrqh9GJhcG55YA+bc6fXc6d7ytOVsOYpuZVaI=",-1278488450581695543,-8594706261159300665,5596088216150701113,-2565979678292906733>()) {
                                 case 787598136:
                                    this.d.incrementAndGet();
                                    this.el = true;
                                    return com.yiyiaddon.d.c.d.b();
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (this.a.ew) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2qk1ka57ni0qn","Kfo9/RHuMewwGqpxWYRTeSrRyi79e4I8mpfZI4fV6b8=",-8221945397100809210,-6865441624024931224,888287789009201984,-802325557782886940>()) {
                        case 437885803:
                           if (av(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1famzrh3vatne","raPlKK73x6Wxqh0Ub8Cc//mvUcz1w24uoN18AqbHfls=",1073713092427550368,6988063037508130028,1565016781893827533,7755157231008152813>()) {
                                 case -976801604:
                                    this.e.incrementAndGet();
                                    return com.yiyiaddon.d.c.d.b();
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
                  switch ((int)com.yiyiaddon.m.b.a<"sqlzmi9vommxx","SHe15TnO1TKrQ8S5imkSRR7yqitPljfGXqLR3Ku6Xnc=",2740894753014258874,7627850475302505205,2698458843861384428,-7464939634466332453>()) {
                     case -2105797252:
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

   private static boolean av(String var0) {
      int var1 = var0.indexOf(58);
      String var10000;
      if (var1 < 0) {
         label38:
         switch ((int)com.yiyiaddon.m.b.a<"sbdgmj52h0wl7","OuZl1NZXyxNdvwR5unqVcNHRiNHbHwZoJ+bXBczOAYQ=",-3302143495159741543,832582940249250682,23782582406365736,8150119996961289341>()) {
            case -715656908:
               var10000 = (String)com.yiyiaddon.m.b.a<"sh5nl3qng0z2a","p7utLrstL0y8/gugWdbBRP81SETYRPt14PYzFuhMFcW8pA6h9Rt96DvF2Qnnaw==",653190513847562868,1275374885871131121,-6615407908413816173,-4570607250336576154>();
               switch ((int)com.yiyiaddon.m.b.a<"s1l4fmcyvyyvgd","i4CMcVxUjARNS2Hu57viuwElbWGwaUfkafj0vzjwuGU=",-6294067985432863127,-4855449253921855497,5592407014700892102,3725570834414760531>()) {
                  case 1562824763:
                     break label38;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.substring(0, var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1you5cgmg94o6","IcnFGs3u00LProDHvAoOs4WPEfjq091BlvBPs6xSLTU=",-7107956130859136977,8556982409221097257,7005828442779492748,-5557894828625315940>()) {
            case -2134668696:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      if ((String)com.yiyiaddon.m.b.a<"sh5nl3qng0z2a","p7utLrstL0y8/gugWdbBRP81SETYRPt14PYzFuhMFcW8pA6h9Rt96DvF2Qnnaw==",653190513847562868,1275374885871131121,-6615407908413816173,-4570607250336576154>()
         .equals(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"se7ka6bwi6972","7xQ2BGJ3utktFHtO1CBU8Sdt8K8FCZZ64vzmXGGhqrc=",-2217932478262422059,8336419081991586373,7114320647144225330,7061418194307929074>()) {
            case -1885237448:
               if (!(String)com.yiyiaddon.m.b.a<"s34zzvxz5ws8kg","0bUCewRwJqV8PXZIHGcLw5xkwzjiFlEqwLNdq2JWzJoBQ8ibUVOX5kEY+4mw5rvoNP61j9OscOff9od4ymnQsw==",-3290499390551025901,-413747558228019476,7785649403198996426,2298938123660684844>()
                  .equals(var0)) {
                  label32:
                  switch ((int)com.yiyiaddon.m.b.a<"s26pln222dxhq2","S7lLTtipzCkCeaq0lG9BtTqfpHSUFs9PrfLzY/Z4w/Q=",1981777996497492417,8499801744919876755,-7649663555263018731,3318035785003286055>()) {
                     case -737319013:
                        if (!(String)com.yiyiaddon.m.b.a<"sqmjnrb461ijj","NQMM+nPGqWA2FVQ0Cheto9tWYXDv4kGb1Eft+r1t9rqm1armUwscviSeW79zKjFOouALtN+1ExolC+gIhZmzrju+gfQ=",-5086500938712428242,-2631541597749667416,8655326808197093388,-2077478003396069452>()
                           .equals(var0)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2r96txyr1bckj","90Chg3E9R01stTFBrxO6qaKhCTxeN6RjAIPTIKBXT04=",-4896980803602074360,-32672357510336907,-1052248982022189176,-7926957467579700128>()) {
                              case 1574331639:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1w1idktga8xua","D3greQj6LxEPaIgao1Z872XIKWGFn+beY1yMu+Ca7So=",-1969613147321876197,4628528693164064600,-3217302757396314095,6429966161667802690>()) {
                           case 1498956512:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3vt63kptuldc8","seAm/W+Wm88ycYp4QKxr5Jk1IFfRAmBOObXNYF9ceqo=",-303094820685379522,5816037288421809896,-6348841314315358019,-7570218720158889620>()) {
         case 469930706:
            return true;
         default:
            throw null;
      }
   }

   private d c(i var1) {
      int var2 = var1.l();
      int var3 = var2;
      if (this.a.ex) {
         switch ((int)com.yiyiaddon.m.b.a<"s27m88uh5cpvns","XoWlVsta7w5MhoV8RCp2qPEQNYjwIVmaJAE5Pq2haDA=",7614936373088859359,516533465195075204,2413465984313305890,4852966432703474969>()) {
            case -1277044346:
               if (var1.b(32)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2k311mai36u25","yKZR4U+2iXNtyA/YGssqfbuQ0Hdxtj3+JkziG2WPm1I=",-4794537625405482813,-4319120391891303401,3384456768177099577,8677560710119332290>()) {
                     case -1429419555:
                        if (this.aG > 0.16) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3vuwuad24788k","aIV3MarfiZPDF0P1LYiNoteXxTKk+8yWuEecbt10foc=",-1976652758063773858,-8090786471826452678,-1968679530017521527,-4283620944668730729>()) {
                              case 968598465:
                                 if (!this.ej) {
                                    switch ((int)com.yiyiaddon.m.b.a<"snxeokllywawl","AehhhZVFDhjk16O00MFffJeoy/LYVIm6j/jEBYBCidI=",-5037134387446131569,-303973727550669869,-7290881651984684179,-4212330285823854433>()) {
                                       case 392833159:
                                          if (!this.ek) {
                                             label63:
                                             switch ((int)com.yiyiaddon.m.b.a<"sp5497tbzhjwh","hj5DCb4mLk7pqYagb3cq48pbyOATQUCJQJXV4LTTYxE=",-4707932996570608169,5398345388660844412,-5146884280550512090,-724871206191003381>()) {
                                                case -493111648:
                                                   var3 &= -33;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3t82qaen5iass","1/huEYP/jBXc/LpIkpNRueA0+cNw4ZIZkd95shrLt5E=",-1613080104318426188,-911050280775217771,8589694126581320836,8580691615416159474>()) {
                                                      case 892195099:
                                                         break label63;
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

      if (this.a.ey) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qwbrsxacx3dp","K6+glE0ZWRilv2lchMNM/ftyNIEnZRSES4CK1ycOIww=",-8303955148682908886,3597273796678873927,5703963211828270067,-7363428637578090530>()) {
            case -995211995:
               if (var1.b(64)) {
                  label57:
                  switch ((int)com.yiyiaddon.m.b.a<"s2mjnuuuzzsd67","b3Q7ISejIS2U8ckxt7I7ZoF40Ed/KqkgB8QGnnotDEY=",-984667885913028546,5972788980494540349,2789502150119170663,-4498471818117396496>()) {
                     case -1463469065:
                        if (var1.b(1)) {
                           label55:
                           switch ((int)com.yiyiaddon.m.b.a<"s1wolemc4zm390","SRCoEDhigOynD5dRnh2W0gIqRGwnU2G5pr8XWmsbXxQ=",7106683838304140796,-3966569477180917188,6182452383819885494,2283787675886229446>()) {
                              case -689030432:
                                 if (!var1.b(2)) {
                                    break label57;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1jqy0bzfgy2y6","G8uaB1g0TI3dmbd/RrBE8vEKkVuT1pX51Gdi8OBawSU=",7550570638710016542,-201966180026859759,-539316754612579364,8749954818727584942>()) {
                                    case 948521260:
                                       break label55;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var3 &= -65;
                        switch ((int)com.yiyiaddon.m.b.a<"s1qz15eejgee91","NWnc3elnd4uRXG/jfmctZfs+RlcVJmbHcXuWKQbFvkU=",-8426589807074135797,-1086364134133733714,-6870309566632944980,8114934089578914860>()) {
                           case 1384529452:
                              break label57;
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

      if (var3 == var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s2s0rmwl6xgajp","nPQZlkCGblay3GWi3U4R+lyZYzc0o7jzag4+/G+Z5oQ=",-2398494899248808720,-1172336068192625519,-8815421812342496486,-1349067204372742122>()) {
            case -424938129:
               switch ((int)com.yiyiaddon.m.b.a<"s9f9210m4copm","0WyvrHKQUQvRS2m3f4a1zF0z2ArETLdQffMheFaV9X4=",-2429708743666833637,-5168068006063724913,3345315167004508358,-4614644306767109729>()) {
                  case 749249206:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         d var10000 = com.yiyiaddon.d.c.d.a(var3);
         switch ((int)com.yiyiaddon.m.b.a<"s1gsslar9knp2o","r3g6h5arRnNMm9HPOzLM888btAvXm3Vrsv7/q7tV17I=",-443942639130401015,-3393094485442744010,-6791403604396004748,-5325913131724002520>()) {
            case -111688811:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private d d(i var1) {
      boolean var10000;
      label161: {
         if (!com.yiyiaddon.e.o.b.c.eI()) {
            label126:
            switch ((int)com.yiyiaddon.m.b.a<"s25nq3ghe2lxhi","fe8u29FJdKbYfy5RzgP/7QBbeEUpqOWRWdJqsm9ujOo=",-3499654755134865482,-8099727194195818674,-3325622704424626656,-689275806383953598>()) {
               case -1313622850:
                  if (!com.yiyiaddon.e.o.b.c.eH()) {
                     var10000 = false;
                     switch ((int)com.yiyiaddon.m.b.a<"s1uds8wt7t2amx","JHTBRNUyr58infvxezeI/eR50UvYuzUqGxxfXMSiV50=",3586888631847715241,8121995479626094933,5650734681578913887,1938281458921600423>()) {
                        case -2025480212:
                           break label161;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s9inqygacecjv","mkWvIwacbanY5RNkUtSkujjclQPpWBzye558ZOzTBVQ=",-8371988792716579813,207176608711751991,-9189014661227308271,5548394801737714878>()) {
                     case 1877877147:
                        break label126;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = true;
         switch ((int)com.yiyiaddon.m.b.a<"s77mey5wu0oja","kqQLe9ECVuRdrjj798cXZ9N++J3t3fileVGHLaIwQuM=",-3091282099386385555,-1072874417119783990,3909671708427304156,6316447135411042147>()) {
            case 1668579487:
               break;
            default:
               throw null;
         }
      }

      boolean var2 = var10000;
      if (this.a.eB) {
         switch ((int)com.yiyiaddon.m.b.a<"sbfwxng1vky26","0ykh8Nzs0Q542sHNm3B9Dy2TvcwZQ4J3yHE+ZtW4R7Q=",8430994841429061439,-465037379205815371,2330879526605705823,-4178358632985141181>()) {
            case -2099294013:
               if (var1.a() == i.a.PLAYER_ACTION) {
                  label151:
                  switch ((int)com.yiyiaddon.m.b.a<"spo0bl8a1bdc1","oKaQ4TF0sLrmD5I9uIHvLVv/SufoCvciIsSG4JeyGDM=",4648291262096781570,28117388256449900,7374952526796069919,2506459950119378367>()) {
                     case 895649162:
                        String var3 = var1.B();
                        BlockPos var4 = var1.a();
                        if ((String)com.yiyiaddon.m.b.a<"sveuj7kjahfv8","h8sPi1oidm308BIq++cXc77XA7VbbQ2Wyx7aGcGJyuZpTyfc/aLym4gEoGdYihJuCM8GLrB4DbSQSeGpQ7Crl26j",-7765532160932546499,-5928006600633113959,5342549456968329388,5944810543977871558>()
                           .equals(var3)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s11t0cfp7ej20d","S3umjGtN/ZGrf7/v/IiDvpQx27AeaPiuBfJUQXGHo7s=",3793330659479625155,9190802336989404420,-1922258275128159274,4327036473688664413>()) {
                              case -27430900:
                                 this.en = true;
                                 if (var4 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3nurnarpalnp8","BdA0MwJETfReT2M7HKBpPB7YSg60btevZAV4nF6/UT0=",6463009210389467216,-1579072735396522269,7637669569718410823,6232011684170686038>()) {
                                       case -1133523830:
                                          if (this.al.contains(var4)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sgk49tfit4jww","DUWy0OMNuiHYJY3Pgd0rJFFry7dppM9yvn43THGXqKA=",-3273925545981027967,4889213048952934832,1254125703953729602,541267659649687000>()) {
                                                case 758643547:
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

                                 if (!var2) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3vqrk6ck99gkg","ArTZ0JbUifJ2dO//JUSC/UMFPPL4NhMYXAAEuyVTi+E=",2971430124473252490,6635374592387928905,-7559636029580596426,-8623803422738017198>()) {
                                       case -1466812980:
                                          if (this.b.get() < this.l(this.a.oS)) {
                                             if (var4 != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s22zmga1lhgd2m","9oKQjYIDrSB2y/yFz8oM7KaQeUwzJRvXBTtbkLMlsoc=",-350274960328847094,5743567061852596234,-6937743711482504764,-79104144276717426>()) {
                                                   case 1684702916:
                                                      this.al.add(var4.immutable());
                                                      this.b.incrementAndGet();
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3s5x6luxe7qot","SHNUJl8/4ufl0AnrPBDaFTv7SQWAKw0Bvp8Nm2uO2vo=",-8253387045824905745,-3342961158537729037,6874125355246692060,-6292846196012848775>()) {
                                                         case 1539388251:
                                                            return null;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             return null;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"sk7btvve2h5dk","QLwvtzEC2eso5fJjSgUIeqHahd899RPpCh/O8TP92v8=",3468884222793300318,-735335810066208726,2066861488706880683,-8517603421457561349>()) {
                                             case 697289474:
                                                return com.yiyiaddon.d.c.d.b();
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return com.yiyiaddon.d.c.d.b();
                              default:
                                 throw null;
                           }
                        }

                        if (!(String)com.yiyiaddon.m.b.a<"s2oh7h467ssdx","pYdNlBizsTLWxR0GguxjWMX/ziBFBdGOtNlcE39jAdtpoVpi3/GnlR8QCHn8G3heRI+vecXOtIEaNRIC+6nPqQ==",6369100357461840933,2015457460553367685,-1226584606344694575,6885608386888623722>()
                           .equals(var3)) {
                           label115:
                           switch ((int)com.yiyiaddon.m.b.a<"sjppqp9wfoevt","58p/Y0GPWS3vJduXQ5h4gq0CHZ5033mkPbC2Og9tc/s=",-5597831254881568505,6733040125223248250,-3865060841322954039,-8273925626641808796>()) {
                              case -985014173:
                                 if (!(String)com.yiyiaddon.m.b.a<"s2wm5qat93fudm","m8c3z630KNvCJsbLDS2N8Gm54lCiWmmaM/iARo5kp66FJWmOfDwdOebjnOg4NsxzpM98Vq3ewxCSUM8Zj6ia4pCP",6030354320607926699,-3513146794504581540,-2275373391621678144,-5256604856058057959>()
                                    .equals(var3)) {
                                    break label151;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2jrbqcc5ugs7v","5fBohiYohHGa7CLh7/agLY8GOHMbjZ6zVJlRAwNDkb0=",1759894739398280309,-4858118843840171696,1719331994570305475,-6131483592374919403>()) {
                                    case -216744212:
                                       break label115;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var4 != null) {
                           label110:
                           switch ((int)com.yiyiaddon.m.b.a<"s27k68usrgemct","CbsNbnDEXlO4tmIF/QOOqLgKR9kBaAEQdMzZF/SzMts=",-792994282910277687,4671148879347958450,-3149048388230108168,6813508008189250509>()) {
                              case -1680568351:
                                 this.al.remove(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s22pfw3l0ruouo","bpH9zN71zByeRsLz9WGXfTjtXNllm5uSLU6n6DM5Vj4=",-8564456709427717245,-7788324522555178802,6204742374153883401,3103440916766379056>()) {
                                    case -331852642:
                                       break label110;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.en = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s1izt98tl2wv44","7uk6T+AnJxP2u/d/2DNHVSV2dVUyd4HAn7tgJQVtv7c=",7687121012166911318,6106059693805241847,-1294695248398581750,2925974874998271837>()) {
                           case -1111084561:
                              break label151;
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

      if (this.a.eC) {
         switch ((int)com.yiyiaddon.m.b.a<"snmkjw0x9jlw0","JpzUKhYsaZj9mdaoFJgyEoP52NC4h36cooY4/RhRhig=",-5632001534178063616,1753567055448965666,-1414005143769218758,-6084484349943031656>()) {
            case 1958016329:
               if (var1.a() != i.a.USE_ITEM_ON) {
                  label102:
                  switch ((int)com.yiyiaddon.m.b.a<"s1d2i6n6017tq8","vFIiLMf8jOM8Xm/IbXcJdrW/e0k9Yb+sZMdsQ/PBjb4=",1712826220830183393,-373114616825134644,2744326719238741511,2649297791972066623>()) {
                     case 1367847512:
                        if (var1.a() != i.a.USE_ITEM) {
                           return null;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3e4zwisl0nruu","vgFN/2FYVHBkcR3/eWU/YefkpSXWk+gMS5+hm6zVkUI=",-688040977165668361,4458804204476532870,3650708233023294872,-1539436809515966445>()) {
                           case -1629031728:
                              break label102;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.eo = true;
               if (!var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3vdf3akw8jrud","PjLeA+JX8Gae3/4+VGa5GrAUWdQVeNIFAcEdC9j67xI=",2596143278824094009,3480226160806711978,-1965155728772841725,-6447671149612245742>()) {
                     case -676568662:
                        if (this.c.get() < this.l(this.a.oT)) {
                           this.c.incrementAndGet();
                           switch ((int)com.yiyiaddon.m.b.a<"s2bs5gre5j8tcp","qRK2vAaHXiqPBXpyP+WSGxiYZGF+sIFcEw5yz6FyhYc=",-3661525520824374086,1517433321771300864,-1246013308307821666,2366436572737991756>()) {
                              case -1165546145:
                                 return null;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2oo9sqoctfhex","O13G18ya3XxdGdmJi8ANR8Ztp9Sp7B0jIz1cD5q6hSk=",9133563062235773439,6715496593329583561,5004831247449047295,8297272812880381073>()) {
                           case -1199515632:
                              return com.yiyiaddon.d.c.d.b();
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return com.yiyiaddon.d.c.d.b();
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private int l(int var1) {
      return (int)Math.max(2.0, var1 * this.aH);
   }

   private static boolean a(i.a var0) {
      switch (var0) {
         case KEEP_ALIVE:
         case ACCEPT_TELEPORT:
         case MOVE_PLAYER:
         case PLAYER_ACTION:
            switch ((int)com.yiyiaddon.m.b.a<"s3kqk2q697ndhq","J8SgJGwMqNV1eiJ+IaP+UmmzrJRttpZS7Pf9gl1B//k=",-4580352306358027479,2350754372090655828,8530565047818922750,-6153833563805272681>()) {
               case 707672367:
                  return false;
               default:
                  throw null;
            }
         default:
            switch ((int)com.yiyiaddon.m.b.a<"s287g1gvlwe05k","3WVhG9k1L3dwXGdVJ19YKGZCYqdAX683CiTFxsyqIUg=",9115744194913307839,7110301302865915910,-2265131749142653569,126923982361461705>()) {
               case -1692089050:
                  return true;
               default:
                  throw null;
            }
      }
   }

   private long q() {
      int var1 = Math.min(this.a.oV, this.a.oW);
      int var2 = Math.max(this.a.oV, this.a.oW);
      return var1 + this.a.nextInt(var2 - var1 + 1);
   }

   private void aH(String var1) {
      if (this.a.ez) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kfk047jpun27","UaabNgQ7Xb1begO9TwsJ8WXeNvK9e3XTIgYJK0wyVRo=",3586106055736445999,-599873047905018445,-5175010806539525269,6616651549366801391>()) {
            case -1532749030:
               if (var1 != null) {
                  this.a.offer(var1);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"syb7ljkjbxuaf","p5aot3e66X2Yx3Y9Kn4QbaQYEAretvnFfVtvCC3Bsq0=",-4612643973627476519,9077169759567158993,890101177077559165,-3199576355436194361>()) {
                     case 1265612042:
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

   private void hR() {
      if (this.a.ez) {
         switch ((int)com.yiyiaddon.m.b.a<"s2agtlrye9tr72","sfX+xOuxxWdk5hxFkM0ctI6KFKu24OL+EfWN5Em9tqE=",6117404399971341655,-4452813712942563940,-7127358903674887249,-2109193357795249641>()) {
            case -2049547884:
               if (!this.a.isEmpty()) {
                  long var1 = System.currentTimeMillis();
                  if (var1 - this.aa < this.a.oR) {
                     switch ((int)com.yiyiaddon.m.b.a<"s27ou2yc10r5b","UbrFcTvCFZXpyMCPCdcbWyxB2AGtsGTMBUUQ0CfHJaM=",-3267600577490134628,-8045568173270861330,-563363479970057317,1468939738004073425>()) {
                        case -1948959931:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     String var3 = this.a.poll();
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2uhpi46cav4bp","auCxdDAtJ18DEn76iXSJerX+KdBGlXTxTR6t0+gcAAk=",3635601185537941254,8192587930389175801,-4402787350681698881,-843713907571994860>()) {
                           case -1058146665:
                              return;
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.d.c.b.j(var3);
                     this.aa = var1;
                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ldznsahmlm3g","cBEtXu3KImtuVnnQUtC7OmWGP81AFulYbZVVkbA4nfI=",-9130190272946200912,-5866225754236637263,-5927237856162816858,-185412402180785809>()) {
                     case 1062237696:
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

   private void g(Minecraft var1) {
      if (!this.a.eA) {
         switch ((int)com.yiyiaddon.m.b.a<"sfjq6cw33yh8g","N/MvGYYTs1x23C9ZnW2fsD5YQuhskuNCVAVCi6X7Wlk=",6569183418591679135,-4557976676356657405,-3216603421373400573,-3778150383352977505>()) {
            case -742210773:
               return;
            default:
               throw null;
         }
      } else if (++this.oq < 100) {
         switch ((int)com.yiyiaddon.m.b.a<"s191tv4baigqwg","bja1AjBBNH6RCS2e395uLRA1OsdHxOZ7F0sTAkiqnIg=",8006137576268445584,4717222816939823441,742938025370323412,6699001807304663814>()) {
            case -919922917:
               return;
            default:
               throw null;
         }
      } else {
         this.oq = 0;
         float var2 = var1.player.getYRot() + (this.a.nextFloat() - 0.5F);
         com.yiyiaddon.d.c.b.a(var2, var1.player.getXRot(), var1.player.onGround(), var1.player.horizontalCollision);
      }
   }

   private void h(Minecraft var1) {
      Vec3 var2 = var1.player.position();
      boolean var10000;
      if (var2.distanceTo(this.c) > 0.01) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s1bh8385lf7jyx","6stjOkvmBeKNy200uDOfD4Iuw9EXhHkecnUCWJ/fEog=",3608900078942402527,-2658335442969979235,3187190132518021460,7827437570326146243>()) {
            case -650990189:
               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s3cb0s23lfdqi4","3AY5gpvDrFs7qVIIiozCdPQ+8JCg8RJ8ZKaeEUGIdF0=",-4477004897257484809,-4585731896356041956,-6272157464065920783,-8705911814111380902>()) {
                  case -1171296626:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s1punw1es343dd","Yn1iqu7enEBIxaYgPk4lanENc3fs01JlloV97Qyimf0=",-8847897814301745561,6976169715952376049,-283057651330674943,713553687775266547>()) {
            case -1009221258:
               break;
            default:
               throw null;
         }
      }

      boolean var3 = var10000;
      this.c = var2;
      if (!var3) {
         switch ((int)com.yiyiaddon.m.b.a<"sgbizjnjd5yjd","7X53HUAZxuMvIRdgkRxsKao3kRTOD5UWcUbqQ61z9ZI=",-9013729773354382098,-8244759393277228245,3313014799835622068,1099864556241439980>()) {
            case -1260529429:
               return;
            default:
               throw null;
         }
      } else if (++this.or < this.os) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pa4w1y8zp1kd","y2tdNRD2l//26nPyEoafKccNp5KOLJ/Ovumkm9C7PC0=",5551752248602467002,3139104422187664456,-3235203195527880222,-7428696477121373165>()) {
            case 1340379003:
               return;
            default:
               throw null;
         }
      } else {
         this.or = 0;
         this.os = 3 + this.a.nextInt(6);
         float var4 = (float)this.a.aP;
         float var5 = (this.a.nextFloat() - 0.5F) * 2.0F * var4;
         float var6 = (this.a.nextFloat() - 0.5F) * 2.0F * var4;
         float var7 = var1.player.getYRot() + var5;
         float var8 = Math.max(-90.0F, Math.min(90.0F, var1.player.getXRot() + var6));
         com.yiyiaddon.d.c.b.a(var7, var8, var1.player.onGround(), var1.player.horizontalCollision);
         var1.player.setYRot(var7);
         var1.player.setXRot(var8);
      }
   }

   @Override
   public void hS() {
      if (this.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vdp0ib8i4s84","QMt1ouTq5xZ0mfU00GmDpqvuVFyM907ASofMoF4bwL0=",4872094964591898766,-7262806781773750489,5683166022594224105,6227392912881717653>()) {
            case -327311144:
               if (this.ag.player != null) {
                  if (this.a.eD) {
                     label52:
                     switch ((int)com.yiyiaddon.m.b.a<"s3tgc2lq5twypi","LrQvzyKn/SxiFP5CwqMbugcwuEQ/W3Cf6nGSRKBqLzc=",5882500439829683811,-6088140320582159893,-3640707335134868080,213834821882988699>()) {
                        case -1580860827:
                           boolean var10000;
                           label62: {
                              if (!this.ag.player.onGround()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s39b2grjar6ujj","XwwO6ICTpKBkQ7QyAsTup8BXozBkTvFpijctY8YSZak=",8156665693497183578,-5870574142567702099,-999578898556802549,7570472753181073591>()) {
                                    case -1717520347:
                                       if (this.ag.player.getDeltaMovement().y > -0.08) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2c7kmb24mg2lz","jTfb9ocyef33juLgvGulwK6Ut3FQoy6xDTXJ9uxHrQI=",-2911287020011816742,-3735820165361206587,-7578426815415972527,7100543004320862792>()) {
                                             case -178257971:
                                                var10000 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3mb489kx83gok","tPRMBXDELZxnZXnS1PUoCJv9F3r7ipENUaqnpPx/4wI=",-2135906999653905133,1767486868948864951,3119373087930255399,-3971598109999469168>()) {
                                                   case -1792984964:
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

                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s23k7fr6ljlmrd","3eo0pw5dZqKzIlPe/7POATCuazZ67RSI6cbHU2nTwog=",4262990588778508121,-4163940024874425656,-7488048586760054637,-1619881294284162667>()) {
                                 case -1017886707:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var1 = var10000;
                           double var2 = this.ag.player.getDeltaMovement().horizontalDistance();
                           this.cd.add(new com.yiyiaddon.e.o.a.a(var1, this.en, this.eo, var2));
                           this.en = false;
                           this.eo = false;
                           if (this.cd.size() >= this.a.oU) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2fluxoc8rszbo","GZxG+oI0DbfHYYOajSMsbA6OhERTtLJMvZh3F8iXGaE=",-8813828417396966174,6218210230224796593,8025573933581191541,1888897826633906872>()) {
                                 case 1217703717:
                                    this.hT();
                                    this.cd.clear();
                                    switch ((int)com.yiyiaddon.m.b.a<"s33stkl1ecj2pw","4YoSw5s6n7YyDG+oHVRww4Evc9I66I1jorisyUrHEGg=",-5645474252650402036,1104172706493397964,-7951292402863083960,8995142894491325005>()) {
                                       case 1343585760:
                                          break label52;
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

                  long var4 = System.currentTimeMillis();
                  if (var4 - this.ac >= 5000L) {
                     switch ((int)com.yiyiaddon.m.b.a<"sptc20ytn1m91","mINB8c2FkI5/Y+L8nvXFeI35cDz/zOm9f69gIow/8KY=",6373281020706654435,-7487527012900344255,8270225427075558405,-3656625963039663971>()) {
                        case 877702157:
                           this.ac = var4;
                           this.u(
                              (String)com.yiyiaddon.m.b.a<"s26mo2vho5tebp","tKZC+R27FvWM1DDGpinqrfEKnjM1qeLmo26JYVbVqhVwnc7aNq3z9F+aq+V0LlMHPSfE5qbU+Wy/5vsZwFgcAKKdaNwpoQr7NHVLjw==",4698848660575823521,-6658555906204036421,-5356106848011727645,-3107291902376346879>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s36itk4ucgtc6s","2DVKPz5t7gbEueQVuzDwhAgPi/yNhnxNKfiiqPKLvxo=",-2868736080459189699,1628721526508581795,-6283497195701636625,896742426727279230>()) {
                              case 1848359252:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s3fmkhadqgsbgw","oPWCx/YiwsdzmTUrs6zbcfuh7JSjDMKcTgP1X50xN5o=",-7094223515322765423,-7972115425159932020,-4284624700524855888,-8900360822609414044>()) {
                     case -50859928:
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

   @Override
   public void F(String var1) {
      if (this.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1aqtlfzz3ep6f","gihIb869MKtQfEu1KwxOtwV0yPr7xP0Lx8doF2tMDSM=",3024543621394298130,-1350929392742563296,-1873423149720026481,1420843970852574342>()) {
            case 144215331:
               if (com.yiyiaddon.e.o.b.c.eF()) {
                  if (this.aH > 0.5) {
                     switch ((int)com.yiyiaddon.m.b.a<"ssfzhuh0nag0l","jun/iGkmzONkHcTaNZf9mSs1OXi5BbTs9DKYh0aCW74=",6010178635086176394,-5447725046754449155,-4783185998642096945,4001916547527949086>()) {
                        case -462669180:
                           this.aH = 0.5;
                           this.u(var1 + "");
                           switch ((int)com.yiyiaddon.m.b.a<"sy5gbi9de24cr","0EnQXV2SR/0mwgKdmoHeK/a59OhK45dyy4crSTj1BUs=",2060507503788557457,2153481415847282428,-5684817809987095242,7191157911583367867>()) {
                              case 199269039:
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
                  switch ((int)com.yiyiaddon.m.b.a<"supl2t2nc32j7","BDV9v9B1VF5qxZfmxlujOaDiXR9Hradyg1Tk1oNb9Mw=",6465776586348310911,-270981844992684719,-5044562121806187376,5683988288159504261>()) {
                     case 816282413:
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

   private void hT() {
      int var1 = this.cd.size();
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      Iterator var6 = this.cd.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3uhr4frnen860","GTCwiy6u0QFsWrcqR/WYvCpMkRlVUWxtHrV/gZ0zFBw=",2848783048330377721,3799230483873145773,-7008399102484380064,7300299205166624373>()) {
         case 1094872376:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sip8ql8bxqw6s","eqw33ZrU3gE4CuAifFAc9cMjtid8fjHW003HvjlV/b4=",-2495584588483103941,4746492954198518174,-7855787012365391420,-8521928838096003406>()) {
                  case -348051392:
                     com.yiyiaddon.e.o.a.a var7 = (com.yiyiaddon.e.o.a.a)var6.next();
                     if (var7.eA()) {
                        label78:
                        switch ((int)com.yiyiaddon.m.b.a<"s37xv1quojbeg0","9jO5yy1zHPhjsZ0xAgndGifbHEDCGPaqk7D/1piQtU0=",7360853561670351475,3189056758506888177,-2466822390300934470,-2947577512468598572>()) {
                           case 198331495:
                              var2++;
                              switch ((int)com.yiyiaddon.m.b.a<"s74qlndqmx6kt","lTCpeHzRXa5fnOVuWsMIulTaaCiyxW7wd01+RNR5xWo=",-692204250905226015,-1880639285147295533,-4700589789026576753,-2401496833732903115>()) {
                                 case -1647280283:
                                    break label78;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (var7.eB()) {
                        label74:
                        switch ((int)com.yiyiaddon.m.b.a<"s2j9l1qg1xof1c","nepbl/EQhg2zQTgLITrgbXOwCHGRTz1SzQboEiwe5Q8=",2472180583126522176,-298452575445643617,1881482271678669995,5293784915954765778>()) {
                           case -496988606:
                              var3++;
                              switch ((int)com.yiyiaddon.m.b.a<"s1wtpegjbs4kwk","jX09H3my/HSK3/r7s4XeGo4M1ZFr+f99ZuyuJ0hPVWs=",-4295692435912729029,-8305913394151643091,3931371945316702594,-1488091309245531184>()) {
                                 case -2016908579:
                                    break label74;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (var7.eC()) {
                        label70:
                        switch ((int)com.yiyiaddon.m.b.a<"s1to17ax74d1sa","FYknfxloHKtTjjjtVJINcfrqvES5DO11mAY5FKqvloM=",6587060682935433235,-3604704077007753270,-3575265452196332130,9111773047702539572>()) {
                           case 1673783496:
                              var4++;
                              switch ((int)com.yiyiaddon.m.b.a<"sgvyppzu8uj33","P04uvMoePzZ7MGkCveRRPOS7eP/scCFpRaisX7Heoc4=",3051677988236280943,-4940142162721219668,4656734403437106604,475650422359773004>()) {
                                 case -540946212:
                                    break label70;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (var7.f() > 0.3) {
                        label66:
                        switch ((int)com.yiyiaddon.m.b.a<"s1n73ijxb65zse","KjDlI+YUYTe0nGS01vZe1Rt2s/pbDQ2+mFZMo6VsJ/w=",8374538018556735554,6237751198758831779,-4220306559421084905,1427297237789020256>()) {
                           case 278910210:
                              var5++;
                              switch ((int)com.yiyiaddon.m.b.a<"s3ee0jrrn8ig4v","YO+MM/9yS/dI5OeELad1qCx1SjubUTKxtqs27bYuGS8=",-8407652765215780293,1434089505780918541,-6623342651838887157,1097567001921874321>()) {
                                 case -481640471:
                                    break label66;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s21hzpj3kafqae","4IqvHCWaKo+NSllregFNuEykqtuEIsX7mZPoysp1+Yc=",5916770899755979908,1281205130684141445,1630855598044705374,4908682102737312179>()) {
                        case 991676416:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            StringBuilder var8 = new StringBuilder(
                  (String)com.yiyiaddon.m.b.a<"sn2tr4j3xh05l","NfxRkKFNm3Dcwg8URp9ZxMsW0HmAQ/26l5KLmgRImEmzKh6O8S5j8nnSFeQrCrZ/M3P/xDYU5IU=",876460463988993517,5651209848226542675,5483713661616899622,8134512207059567145>()
               )
               .append(var1)
               .append(
                  (String)com.yiyiaddon.m.b.a<"s1lqqwrak6st2n","vlOC6a0aM/Xj9DtCV6v6exgrtYk9hHSiuovyUEOWgclsULB3elq39w==",2965467251566713298,-7870716842794592364,3245072539464449048,-6145906475180172668>()
               );
            var8.append(
                  (String)com.yiyiaddon.m.b.a<"s189w3xwj67a9t","Z4wA+cijZV0TDqHgOuUZsy01LIctVQl40vqOkvihiVykTR1qsufdwWme7cCVKchN/P/uIt85ybQGIAJpoT8=",8357072033349327810,-8018983374726434156,-1315348684270257833,-1498642581530842253>()
               )
               .append(N(String.valueOf(var2)))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2uo650ritlbny","Uq/JoaNVXSKrrN5+BV0ZW8xOPRgz6anjGPXlwZTD1bW/WYfZeUuZ6Q==",-7893705862376766416,-5926614044381193636,279750338000521504,4348665937712434674>()
               )
               .append(a(var2, var1))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s3pze4bev7fdhf","YwaTfH+LiILi6/3JXGNRubUjNJ6Q2ycA3IrCwQVvnGQ3Bg==",6944657278549340985,6569919493445487755,4058505857543255473,-4175428839821007478>()
               );
            var8.append(
                  (String)com.yiyiaddon.m.b.a<"s2gu3jl81l8a4p","YYi+2pEptBGbxiALcN8rm+WLSJTI/R508X05y/vBWvy68Du+LX75pSGfPQ/ksuF+COSIV1FolpsbQX8/OwM=",6073579153110037940,2895745584884656482,8455028569251428920,-2476320285090422661>()
               )
               .append(N(String.valueOf(var3)))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2uo650ritlbny","Uq/JoaNVXSKrrN5+BV0ZW8xOPRgz6anjGPXlwZTD1bW/WYfZeUuZ6Q==",-7893705862376766416,-5926614044381193636,279750338000521504,4348665937712434674>()
               )
               .append(a(var3, var1))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s3pze4bev7fdhf","YwaTfH+LiILi6/3JXGNRubUjNJ6Q2ycA3IrCwQVvnGQ3Bg==",6944657278549340985,6569919493445487755,4058505857543255473,-4175428839821007478>()
               );
            var8.append(
                  (String)com.yiyiaddon.m.b.a<"s2f1hpapy4in5r","+6QgU6y/M0Md1or6RQBM19GufQih2nLvWVKm/SQ9qxcCR8Q1czSQiGviY+cuVm+e5qZHz33NEJfpOpNiq9o=",79020745874961681,8313086055923676947,3162817325186201804,-987239942425219770>()
               )
               .append(N(String.valueOf(var4)))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2uo650ritlbny","Uq/JoaNVXSKrrN5+BV0ZW8xOPRgz6anjGPXlwZTD1bW/WYfZeUuZ6Q==",-7893705862376766416,-5926614044381193636,279750338000521504,4348665937712434674>()
               )
               .append(a(var4, var1))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s3pze4bev7fdhf","YwaTfH+LiILi6/3JXGNRubUjNJ6Q2ycA3IrCwQVvnGQ3Bg==",6944657278549340985,6569919493445487755,4058505857543255473,-4175428839821007478>()
               );
            var8.append(
                  (String)com.yiyiaddon.m.b.a<"sz0fli49wimxc","fNHQvUFfhm2qjGwK1B8TFdLFpM9m28Qc6H8zOqFTlq0Ci9+gdFtEApdmzvjatxtJcFsaSMOvqDjQKuZpwlU=",-6180663342908267368,-2957628792051346879,3254834523635350336,-6037002930757355842>()
               )
               .append(N(String.valueOf(var5)))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2uo650ritlbny","Uq/JoaNVXSKrrN5+BV0ZW8xOPRgz6anjGPXlwZTD1bW/WYfZeUuZ6Q==",-7893705862376766416,-5926614044381193636,279750338000521504,4348665937712434674>()
               )
               .append(a(var5, var1))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s3finv1kkkl9vn","BDBRsP+WQsfVmhr6ugVsq4iHDtO0Npa9RNW2a/wgm2s=",-6334993165396807474,-3806335743147246100,4883089618022927364,-6250102319858657688>()
               );
            if (var2 > var1 * 0.5) {
               label56:
               switch ((int)com.yiyiaddon.m.b.a<"s3sb2kganc38pk","uvYAsfAmZmhf0dp4phBuKzrewaZjdijlLu3o22SnrhE=",-8318309775786123766,1300804879021571469,-3939081783474238175,2744405898800647453>()) {
                  case 998487310:
                     var8.append(
                        (String)com.yiyiaddon.m.b.a<"s3cfvaioioiuof","g7JXhP1fCegqyCsXTP4Lb7IOJ8PxJyUVLnH0J6NreVnPbV+Dpq8rEjdd3Tc5DRtE4r2PIfbdQ1ozA/6qqrugvaVKZQuegCG4hS9/22PIWplOt08drW2HKm9BOX0HrA==",5724885770858384584,2251583704085895751,-1710989995091785391,-1161794121656263947>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s17ksb4uhjgrph","S8clvMw2Aza1bfq292IQe3VFblwKRCzKh8tmZZP5K9k=",-6046771580867288209,-7747850807198422176,1036453581906412168,-8664936148088858039>()) {
                        case -923870784:
                           break label56;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (var3 > var1 * 0.4) {
               label50:
               switch ((int)com.yiyiaddon.m.b.a<"so5gydvi2zlfe","tBIQ8HZrljEmPvzi8PgMTRjFBgkI/+1NmpHC19DqbfE=",-4241215595666207340,7391653418374386033,197053637276619030,1765443828879906956>()) {
                  case 1547745566:
                     var8.append(
                        (String)com.yiyiaddon.m.b.a<"s1qmqsiv0vxhej","tgEmVKai2Uxm7CWM5Sa5lohZkL3VcX/xuhquK+grr9T2uho94quLgZS/YIZK4K4A53e5UshcgxfyjWdD50zEw//ueXffZuwFjXWQuXfYe8bPcQ==",5803325605080507078,7165609244018172329,-8614331778533275394,-5473521515117603685>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s3utuiaublvpd4","Vvzhx541aUEHr56wi/+qg6tpb2fSUZVcU8jPDM8CZLY=",6118453034669414506,289870906699749527,5748646449876048108,-1433049915116073279>()) {
                        case -2125478486:
                           break label50;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (var4 > var1 * 0.4) {
               label53:
               switch ((int)com.yiyiaddon.m.b.a<"s2ptjr7qbv7bgb","MJMIW8OPzfNRvwvo037+8CmVOf2Xg98cU6VfpAhvHVE=",7657392303510949147,1460349898572484007,1422766711404001713,2597782336682768209>()) {
                  case 155055133:
                     var8.append(
                        (String)com.yiyiaddon.m.b.a<"swr6vh32ttr4a","0fxEUvlXAJcvugujkEDc7JUOa1ZtLCYPhLz73N5PQtGkidJuTE9heH2fCDg2YfuBMXNI9XF23cf61BJkq3psswGSWM+mqnDMCXexKtNz+rTCaA==",6022900399935414881,8872240313497882693,8525386350732357360,7107619976771822905>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s7jfarsny1mes","HmhnzDPYDCb+oi3Ym1+CR/+ftmg7736eA9Vxn33Ltcg=",693168975034575594,3899874247647245935,-2367012013712889855,-2683848752815496143>()) {
                        case 690992051:
                           break label53;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.u(var8.toString());
            return;
         default:
            throw null;
      }
   }

   private static int a(int var0, int var1) {
      if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1riq5pbdbz66s","XLYYzS3U7bekquKZM7yWV9lGoi+ZRk+Jb0HAq3gtbDE=",9056746002906324420,7240651747593873676,-2428816444770606194,-1087420961034226641>()) {
            case 979807257:
               switch ((int)com.yiyiaddon.m.b.a<"s3ubqb0vbr5e54","037xfeRCuoOjGdTYZQ59qdbPTioI5ikTMIVELFQPRRQ=",6048605823685126943,-902625953326167388,-8617231797644513497,3948269656459710436>()) {
                  case 625129135:
                     return 0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = (int)((double)var0 / var1 * 100.0);
         switch ((int)com.yiyiaddon.m.b.a<"s30s40piehc3ur","A4vI7wtHSBuin/ZB9ekPraFS8R6orGLcZeo/HjbBRwo=",-1782363823329532661,-7504295889587975166,8054682776810477558,5456045733332591736>()) {
            case 1295711300:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s1cf0x0s3eqp2w","84P34TU0fOCI5IgJOEEiJz0RXJHHTffYznx1WwdrkROV/JB3",-5408014209708381826,-8513762789296226755,-1549994082537097059,-4825256776926227876>(),
         var1 + ""
      );
   }

   private void ad(String var1) {
      this.u(var1 + "");
   }

   private void o(String var1) {
      this.u(var1 + "");
   }

   private static String i(String var0) {
      return var0 + "";
   }

   private static String N(String var0) {
      return var0 + "";
   }

   @Override
   public com.yiyiaddon.l.f.i a() {
      return new com.yiyiaddon.e.o.c.b(this);
   }

   private record a(boolean ep, boolean eq, boolean er, double aI) {
      public boolean eA() {
         return this.ep;
      }

      public boolean eB() {
         return this.eq;
      }

      public boolean eC() {
         return this.er;
      }

      public double f() {
         return this.aI;
      }
   }
}
