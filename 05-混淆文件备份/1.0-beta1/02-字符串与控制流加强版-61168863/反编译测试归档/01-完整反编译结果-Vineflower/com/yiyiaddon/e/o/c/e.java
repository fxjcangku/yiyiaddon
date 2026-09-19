package com.yiyiaddon.e.o.c;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class e extends com.yiyiaddon.l.h.f implements com.yiyiaddon.l.c.b {
   private static final String wT = (String)com.yiyiaddon.m.b.a<"s1yai0m92xvy7z","4ALNd6EIMpALA65BOT74TgzJfGXeoQmMO/2JnM4Ee9GTjsq1Bb70/RUSToN4xXIS8ig/CzAA2cDiiAnwwFFDfFiw+99Oi79JBmGCjqJLrEoC4g==",9017341301258541522,-1348943436514707495,6659947127116025135,659889160225334937>();
   private static final int pB = 20;
   private static final float dJ = 6.0F;
   private static final float dK = 11.0F;
   private static final float dL = 10.0F;
   private static final float dM = 12.0F;
   private static final float dN = 6.0F;
   private static final float dO = 6.0F;
   private static final float dP = 320.0F;
   private final com.yiyiaddon.e.o.c b;
   private final Set<String> ap = new HashSet<>();
   private final e.a a = new e.a();
   private e.d a = e.d.OVERVIEW;
   private int E;
   private e.b a = e.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"skk706ou9lqrx","Iy6ydhI5255HMglNq1QPi9DsGM5yUM1+yUaGlVW4IV4=",-3000683318594308861,-3440684520697456336,-5254760223505148241,8956179820133827540>()) {
            case 988377482:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3h5aqx95xbxik","cXsd8bi8gZFCnxG4o2NlxwXmvZznfaIGBnLGwq+CNEs=",8695561151518107462,5475400394258970397,3348709850310321278,2326287658372344990>()) {
                     case 1639267688:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s3tl3qomb37era","ih+/zb/Ae02R0fL6So1QBvizwepTF4TAAGrzEa9j/Fw=",-6812242398097851054,2063197277578810023,830032297252537396,-6737245652168862240>()) {
                           case -1642783179:
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

      switch ((int)com.yiyiaddon.m.b.a<"sehk8awhohljn","tpRnoAy/0G8LBnSNXtg08jbzCn7IDh6+11MHeKo5DW0=",5112467311173020940,-7084301677641815838,-245367957329518828,3066881989761994430>()) {
         case 259350178:
            return 16777215;
         default:
            throw null;
      }
   }

   public e(Screen var1, com.yiyiaddon.e.o.c var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s3p60cc9xl6nv0","AlpJ3T6eeuJSU1nLGPfPi8ZiW7x4nw/uvHrXLOCeSGgzaXkN0tt3coM9xPM=",4336877243590348099,6201329739217219322,-2461281398849210097,6456422303252123590>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.ap;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s1yai0m92xvy7z","4ALNd6EIMpALA65BOT74TgzJfGXeoQmMO/2JnM4Ee9GTjsq1Bb70/RUSToN4xXIS8ig/CzAA2cDiiAnwwFFDfFiw+99Oi79JBmGCjqJLrEoC4g==",9017341301258541522,-1348943436514707495,6659947127116025135,659889160225334937>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jzmko6b22atj","aQH0a5/+ir2f5Dy65knFC/tmjxRbvlIWebJpV4RYKKQ=",2818641829804484885,-3099783982579410462,-3678922449105292481,2561337077008177216>()) {
            case -421299899:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1i24ko2x0pg8n","FT3snn7KC0Shg1Zjz9Z+Ttp/by8EPCWD0DR+wY+n0vs=",719771567746134615,5728266496476096410,5518663903557485739,-4625682138035083569>()) {
                  case -1081403058:
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
   public void removed() {
      com.yiyiaddon.d.a.b.h(
         (String)com.yiyiaddon.m.b.a<"s1yai0m92xvy7z","4ALNd6EIMpALA65BOT74TgzJfGXeoQmMO/2JnM4Ee9GTjsq1Bb70/RUSToN4xXIS8ig/CzAA2cDiiAnwwFFDfFiw+99Oi79JBmGCjqJLrEoC4g==",9017341301258541522,-1348943436514707495,6659947127116025135,659889160225334937>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"skvrtmx1bfddc","gn4FzRyQ9J9SrzbdMT8WzXir52Syi7ccssmMFt0tFg4=",-264637041390629901,-55815226725547927,3022755785469616417,732034807773922610>()) {
            case 241912389:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1weitarabzpqm","iXX2/nC0wIf0vjWDQKhjat6Mb6Gc4LetuIpNCeGDWpU=",-7160648074571446794,1476582549619170169,-3568587686071843298,666518173106765536>()) {
               case -1265562111:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"svaav035tnkl9","r2w7PF/IsfMwEdy8EGEvSO6j4qa+u38BRtedmbg963k=",1081481616456703485,-4431292085046169012,2110697017151703976,-758271311505706235>()) {
                           case -1723221580:
                              if (!c(1)) {
                                 if (this.a == e.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2dxeogpffardk","CIp6Y6Zake9ZvghzQuRwvNnc9pCAqFZlnZg3y5SYUEU=",-5142786191798874278,-2815120134231141596,7372054759749405621,8454879123795955385>()) {
                                       case 708888577:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = e.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3rk3iyfoubovy","iqHdiI3hKxS78JSmH4Lc5QXHXzoula0xqNiyDUvKxOQ=",-372254779386535696,-2355410710910306075,614973864281674617,-4472000447812127056>()) {
                                 case 2061061332:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1pktuf84ppr07","nkBmQ5G4n/Tn7mRmJWZO3903/6RefZ/D/OASGQh+72U=",5659486526674012219,-2566165385767827346,-3032082488498229851,-3411979728744847750>()) {
                        case -719701555:
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

   private static boolean c(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sr637lk4hmh0h","Hce5VM9JoEvdk/WtM8wNJGhSBq6YKfva+47hJMxYk9o=",-7467026491997790789,-2189112026495023567,-36992174151685598,-1484936869048539152>()) {
            case 1740371487:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2r4q14imd0b0w","lsALNum8eIXQFHn1e1+mCwLuyGDTtlUDokcroM2WMsg=",8138174564484213322,-6009689551545089771,-4874766923142788734,-8426590492701174315>()) {
                        case 1882231290:
                           switch ((int)com.yiyiaddon.m.b.a<"sp9yw5r51tew8","D4rU69XPUV8TPna4ol6OF0DNbFexmt6fY1EhNftgUDc=",-1369155958438597390,-469835384942431451,2114143078026441998,6777907431073754397>()) {
                              case 820457680:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s296kjdbx375ng","zTjIAqrkjHyq+Qb4Glml7pzR4aBi3vtkFdogcLd7iwQ=",-2223103837537647163,5319615541208414138,-5500841118503585066,2653376111988714577>()) {
                        case 1415153766:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"svr9gm32ljyae","n0T8b9wbKts4FzdxyI203aw2dVs27mYqtJrckjB0600=",-4991295638997730641,8621742309905023816,1040829990986107943,-1372339016538984267>()) {
                     case -1798761447:
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

   public void C() {
      this.a = e.b.a(this.b);
      this.a.u();
   }

   private void a(e.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uzdantxekga7","J9DFlUyNr6Qai/l/GUDEWobBRMVImhUJXtrFcqyyZH0=",-1257578228385383538,4490221790445909409,1803029798779006661,436575440098427863>()) {
            case -438697753:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1xixjdr6yqeg4","QAjYJBvrilWJGN70jE55VKPWK71UUQpLqL82F2eYWZs=",4871375506433842501,-3927069552073107016,-6541543417267380103,4014651006674309125>()) {
                  case 768486518:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s96jajxzqa1ih","3AH9YmpYgfvJzQg1aKIgdodyGKgN7WkcjjR5tgL+fsQ=",-1990890694616918866,8697133500332818979,-3821315153500488115,3973163252865110734>()) {
            case 1835063757:
               return;
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new e.c());
      this.b(var1);
      label19:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.o.c.a.e(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2osakiw0zc54h","LyN5gic5A9su4kBon4HQyuTkAw30cBsLLKmIKPPcaJw=",1207208564311217168,3319549837049849036,5105846098138406525,-4154134762737067977>()) {
               case 721659538:
                  break label19;
               default:
                  throw null;
            }
         case DETECTION:
            new com.yiyiaddon.e.o.c.a.f(this, this.b).D(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sr4sjljhwtbnp","7OyOEzjhsYUXgz50NezpmakvCt+aUuBoOcpB45GYkkc=",-3873664234379571634,-9160145492306066225,7872725166236619314,3878336225403393836>()) {
               case -2099597632:
                  break label19;
               default:
                  throw null;
            }
         case RESOURCE_PACK:
            new com.yiyiaddon.e.o.c.a.f(this, this.b).E(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sqsrkdumcvtn5","5z7b/WdO4+zEs8oooSLfA3c4l0xJhwAWq/0S7Vqhibs=",3457033136333860047,-3330325499859546477,52049204692131263,-5310196366255684960>()) {
               case 228193974:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (e.d var6 : e.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s54fiuu7kqdek","OJp4Z4srDA2d1GhQEP8GDAZYmdSl/i8icPmvY8pGsME=",-1433398700784051114,-6307472614117338436,-4978638384771582318,-1106037957523826176>()) {
                        case -1892922313:
                           switch ((int)com.yiyiaddon.m.b.a<"s6otq76hxxkek","1Szpekdzs6SkVJdW5J7fOM4AvrxMiBvtSaNQnCafMJA=",-1339294796879409192,-8676911680776541279,-112151029441174245,280945205121562440>()) {
                              case -379489344:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s3ae6e1nu25xyq","0shO83M/2D8r86gf7cmXvVm9Qhf7CvkJhkcY0pDx7CA=",-2832216888538231450,528833529659697924,620306869066216914,-987583650697119743>()) {
                        case -1736779997:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               }
            )
         );
      }

      var1.a(new com.yiyiaddon.l.c.f.a(this, var2, 24.0F));
   }

   private void c(i var1) {
      var1.a(
         new com.yiyiaddon.l.c.f.a(
            this,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"snabe0sw7dn8m","FG3thfcxQnU9XPm1Iv3Rqqn3p6Vtbb6/PRq+Ua0jqve6HvOB",3348710527221729503,8150203325725450467,8899626918362783250,772600827727243499>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s2yh96czdrhk83","9K9l39G4bb7WfvR1eoDWRJ7wvLP9lCJxFF/8ZBaenlsxcNI+Bn9LWIjx6HSJYS7itvJSvua0Ed3EPh7oXZcVwDswbIPFwZhjvwQQlaMI90icMRuIkUjvsEfF0QEPyJptlJxxSw==",-7004849601216801632,-1569756150245426694,4915855808755544762,-3869826160263717464>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s1hhb44ksgx857","KyLUWKYFwgQG/3qkC5Ybpu4fQyVayAK2m1xw1NC4cGctm9UI",5195424013537487987,7289320464896032425,-5733798081680047891,6360738859375499254>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s14iut3xy0vwcu","/w+/t+7dpN8NIoAer9Asp3yZwVNdwpP+aUNhz0z2r6I=",4936539816954931666,-6622500883061795605,6280716658156891341,5974285447455889253>()) {
                              case -278349625:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s9wo9a3chud61","N8vfsOCbMR89p/X+D51Bfqe3N6ViQS8aDPDTqKWogUY=",7328895851625534395,2439461245489935665,-8800810414665516109,2620451061652567320>()) {
                                    case -546185505:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }
                  )
               )
            ),
            24.0F
         )
      );
   }

   private static List<String> a(String var0, float var1) {
      ArrayList var2 = new ArrayList();
      String[] var3 = var0.split(
         (String)com.yiyiaddon.m.b.a<"s1bjtiihprvya5","14zmtCvx38gFONbnWRiy8U9FtvcJReUV/7s7PrM3",1030133923872157957,8649279331674624149,8438570585419481357,8946210493366541805>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3pnbar2couxr2","DnC3KXhkHiTCGJzEDwq2YsBY290pitTE4hhIjgV50Wk=",-1075279495532541473,7668099828235579549,4046142882006083156,8532291778846285617>()) {
         case 2067182313:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s28nxyo98tvj9o","50kkUMqK/ysze1d87c/fvBL+CE2TcPnTURzS49aMz7I=",-3381650662233612494,4242850866135278149,-6329494526345593869,475428179801163922>()) {
                  case -60763556:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s34matgt72dvzc","fs1iCwQzogVREzZlOgNrdbN4eGfdSttIPXUV/vgYpgQ=",531723197447088447,6928351482713935118,-4570172726472020557,-5410411775055637107>()) {
                           case -21506298:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s3jeqyzem9alez","qvuk4YyU19vgg0VUtQJuA2b/Zki45XZFtSjJQA==",-3542697445019822155,5096869264585077958,-5566591425769226048,8684383430985628344>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"sqk0no6mm4y1l","x4KlTMjeQ9Y7Wd+dSv1IUvm9KRN59CSCMNu3P8oMOuY=",6801200468742252154,956911445872367552,-2959451535307451067,3602555260552866934>()) {
                                 case -1147690740:
                                    break label68;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        StringBuilder var7 = new StringBuilder();
                        float var8 = 0.0F;
                        int var9 = 0;
                        label46:
                        switch ((int)com.yiyiaddon.m.b.a<"s1qs1swnkjlc9g","7M+AcmsNA3SXqua2L5P2vi9GZ5CkUOm5yTQ3i91ueKo=",-9109437287904724155,6239361319144421010,-18229617894146532,1264900491200592321>()) {
                           case -1493368504:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3017rmaog0w4g","QdKya7kLepi9KsiqIw7vFTynFDSVy8QM+CvDhubF2Fo=",1387535844890672415,-2181044821034315045,-4380191590030133908,-2966481243284212520>()) {
                                    case -29613108:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sm238tnmmk1qf","T2STgnplis0SX/iYbiODUSAtdwke+bKr9nhaU7OoZKY=",-2844340399930281607,4184746543300268055,4530954435620949766,-7544575820680461908>()) {
                                             case -722390164:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3o6isx2ew654y","C2hzxf9uhBkOyrBdFNiM/plagkh1luzYCZIdCNLvEMM=",3736984870232936763,-5431229481345388280,456362322882876516,-4588732818802802894>()) {
                                                      case 1190483019:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s13fky9enx7isv","u0aKs7eudcBYFRoeuzN1dl7NMozqZRjdrcHq2zuTlTE=",8767760167501564599,7677104813382430209,-2625977861130026127,-19364638930258718>()) {
                                                            case -230251333:
                                                               break label64;
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

                                       float var13 = com.yiyiaddon.l.g.d.a(var12, 10.0F, false);
                                       if (var8 + var13 > var1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s13cr1x0b8nhov","Ymgs27Ln45f4rhTFivAF74AoYj3AHWs0nODA3xYdm1A=",7234591045085498629,-5924384685099556173,8019352488261708664,3815390270647201678>()) {
                                             case -56527058:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2t1pt2k6fpys0","uP/6YTMD23l+L5XfEJPKCx20iKS5rztGJ+hATR2m80o=",-8352234597469879857,-1689265343501877434,3983484398016577092,4235999165794700535>()) {
                                                      case -154789791:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3l1gd3b76thnp","cFsMJY0moyT6nRCsIETCdycj2MoG62IAxTK+dXPg3Ko=",-5701081099333140662,2421174174584740725,-1713550921284234367,-6264065861101490606>()) {
                                                            case 1517057410:
                                                               break label58;
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

                                       var7.append(var12);
                                       var8 += var13;
                                       switch ((int)com.yiyiaddon.m.b.a<"sj4u69u3e9mx0","An1rC1RIizHpLJbFCLS6UzOVUjsmxbsZkbj9FcDQNMU=",299613255523352938,3373583266617131212,2948927268004357811,2153515586074089610>()) {
                                          case -1987988182:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"snwh5rlbt9ped","4esJ05qdKmpgBu2GIHkPBHz9POmRDmsWiB7s0j5AMjU=",-8505610794353933317,-8117958271781208867,-5404737174792489526,-7709451426323203750>()) {
                                 case -185114690:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1pc7w26r4h0uk","hIA5uMAKXJGrIqzphP1PI3oRsUlITqTMbbCGvLInchI=",-7741000652092732241,6863085019879943772,5326527332062507251,5163315805932289770>()) {
                        case -1593288326:
                           continue;
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

   private final class a implements g {
      private i a = new i(6.0F);
      private String aW;
      private float m;
      private float n;

      private void u() {
         this.aW = null;
         i var1 = new i(6.0F);
         e.this.a(var1);
         this.a = var1;
      }

      @Override
      public float b() {
         return this.a.b();
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.a.a(var1, var2, var3, var4, var5, var3, var3 + this.a.b(), var6, var7);
         this.a(var1, var2, var4, var5);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE, var6);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE);
      }

      @Override
      public void F() {
         this.a.F();
      }

      private void a(String var1, float var2, float var3) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s4sejnxyl9zvy","shRjF4ptnK62vzQ/sMnEFF7CHX8mAeBGJXWwVVBv+1c=",-7422935954382759023,3234926799726400194,529675883711159974,-5162536855348105616>()) {
               case -2094890376:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s36n6pkrk0c3gy","EbUEbLIfOjwkmnJZMWJQU/3IUz2M5kn5g3eA9z1C01M=",-6958202752621359932,6513021367453348152,-6508947152715306220,-3907311855908140608>()) {
                        case 333635476:
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

      private void a(Canvas var1, float var2, float var3, float var4) {
         String var5 = this.aW;
         this.aW = null;
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3so5lxin6he9l","dSRrcJGlFPlL9tT1cYghbDCEMNPvBOgqafmobMpf8Qo=",6078782490672637452,-6070441315604640395,-7311611366614513187,-7432137936006882655>()) {
               case -1256148800:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.o.c.e.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s32kbtrvr9wk6p","Vx/t7MGiU7pMVS1jK83ehLHWEMbpgIHsW9SGGOt8CBg=",-1165997356416031174,-6201948405220242135,6512399942336836362,6487229135233276261>()) {
                        case 151045138:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s156rkfvl8xdji","/uwmwqlqjeYvy1jHp0KkBSZE5GoXrI/aaafLlaBAPhY=",3427458704783911931,951791391011935087,4310210359164842480,6694615029666357348>()) {
                                 case 1119274667:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s3j9doy95hkk0g","vsEbyMW7J3sj13rms4rzwFuajaOv12rNqlcw1hGpf2g=",-4522349048287638172,5159555350531434530,9153212339708344076,4846209637480228787>()) {
                                       case 2141559515:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var8 += 12.0F;
                           float var17 = var7.size() * 12.0F + 12.0F;
                           float var18 = Math.max(var2, Math.min(this.m, var2 + var3 - var8));
                           float var11 = Math.max(0.0F, this.n - var17);
                           j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                           j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                           j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                           float var12 = var11 + 6.0F;
                           int var13 = com.yiyiaddon.e.o.c.e.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s1tdhq8gxhj0oe","IGV7HOaUzUY6fhTYLoa6gbr4fe3atDgCqzV5PH5qVhU=",8384691222035715041,112662909216475756,-2803988706069995777,-5984704816479413649>()) {
                              case 1738173852:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"svglmtoobgb6g","rw01CtQBfHrbYb1Ww7VMwf6ZT/MhlHGDWfLLK/AiAVQ=",7422095515292836886,-6559441506054339515,7965174446656186052,1123720914928261138>()) {
                                       case -640599517:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"svr470w27tkp8","3NImFXO3HHTFP1nIF09BXcJEu3ft5BaEnWbZw4SgDxo=",-1336749865210625870,-4932508097907691276,-8158255252654177900,-3593147182990459207>()) {
                                             case 1208271574:
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
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s7fcotnrypmgv","/rVPgKARfvMvbmiHy9XF/7Vgn2WqB53e3/rFHnyugpM=",5158168864990502956,-7661478630351083782,-2560204945971609984,-5758879394386782325>()) {
                        case -2009650863:
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

   private static final class b {
      private final String[] ab;

      private b(String[] var1) {
         this.ab = var1;
      }

      private static e.b a() {
         return new e.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s2ngyjg6o1dgp5","zKwP5ML4o3+ukeHKSe+HJvUH9xuPboc5dg2lsQ==",6684677775568362902,-3989309259904989581,-3331783138073698696,-7708981081621124049>(),
               (String)com.yiyiaddon.m.b.a<"s2ngyjg6o1dgp5","zKwP5ML4o3+ukeHKSe+HJvUH9xuPboc5dg2lsQ==",6684677775568362902,-3989309259904989581,-3331783138073698696,-7708981081621124049>(),
               (String)com.yiyiaddon.m.b.a<"s2ngyjg6o1dgp5","zKwP5ML4o3+ukeHKSe+HJvUH9xuPboc5dg2lsQ==",6684677775568362902,-3989309259904989581,-3331783138073698696,-7708981081621124049>(),
               (String)com.yiyiaddon.m.b.a<"s2ngyjg6o1dgp5","zKwP5ML4o3+ukeHKSe+HJvUH9xuPboc5dg2lsQ==",6684677775568362902,-3989309259904989581,-3331783138073698696,-7708981081621124049>(),
               (String)com.yiyiaddon.m.b.a<"s2ngyjg6o1dgp5","zKwP5ML4o3+ukeHKSe+HJvUH9xuPboc5dg2lsQ==",6684677775568362902,-3989309259904989581,-3331783138073698696,-7708981081621124049>(),
               (String)com.yiyiaddon.m.b.a<"s2ngyjg6o1dgp5","zKwP5ML4o3+ukeHKSe+HJvUH9xuPboc5dg2lsQ==",6684677775568362902,-3989309259904989581,-3331783138073698696,-7708981081621124049>()
            }
         );
      }

      private static e.b a(com.yiyiaddon.e.o.c var0) {
         return var0 == null
            ? a()
            : new e.b(
               new String[]{
                  (
                        var0.g()
                           ? (String)com.yiyiaddon.m.b.a<"s1nk5pndaa7vsd","XH9/k5LTdlpO9npVlY8ndkimvXEDw6I56KXXDYwNcoInWxNF2Vs=",-1741473839994747081,-3923612808945050058,1636535601806400086,-905698590646629407>()
                           : (String)com.yiyiaddon.m.b.a<"s12kdsokyt3nrl","cmRSQz7wz4JqmqYletirlxNxC6CiCDaDN1CEjRrjuK+MhJzfMgo=",-875567772457379506,7463274694668975225,-4928851539382902824,-2353059653857178074>()
                     )
                     + "",
                  eV() + "",
                  as() + "",
                  com.yiyiaddon.e.o.b.c.cU() + "",
                  (
                        com.yiyiaddon.e.o.b.c.eH()
                           ? (String)com.yiyiaddon.m.b.a<"s2xbwr0hyc6xpk","t85T3cIN/GqRygVc51wFWZTo/m/QulmBHaQBKWYaGiedrSzaOadajq1iwZpkgQ==",440853471847095665,-9066531754582446838,418814480110501885,-467855066991326632>()
                           : (String)com.yiyiaddon.m.b.a<"s34sftl69bwvey","XPbiPI0wy5ZGzvIqibyQPfb7WkWO6dfQJs6qrTcwx0zM3w==",-652091780414387565,7065005455758033859,8435693114203384221,-7425741818192419060>()
                     )
                     + "",
                  (
                        com.yiyiaddon.e.o.b.c.eI()
                           ? (String)com.yiyiaddon.m.b.a<"s86mwli6mz9ly","c91pVMDBqYQY769YUwvrkCCmUvSoBNoWZbzXCnvs91S4gFJy",-3326439282635229442,-8038296556131083083,1494307343375455590,-3833537287895735298>()
                           : (String)com.yiyiaddon.m.b.a<"s1a1r0zz7zchoa","uqp7/npb0bDCb4C9IKiXCRNYpEt3OUna5U6klq9cjj9xpU4t",4866829007551595529,-6787145214425155226,7394319352856131185,-3407598827880797299>()
                     )
                     + String.format(
                        Locale.ROOT,
                        (String)com.yiyiaddon.m.b.a<"s1wwetycec5pgc","sV3bhQvALqq4FyY4TRb5VJZCkojeeewS4ErmAR7TT7/dQUGK",-501127179842593232,8124924904273675912,-4597890641669355615,-8187649381925641689>(),
                        com.yiyiaddon.e.o.b.c.g()
                     )
               }
            );
      }

      private static String eV() {
         String var0 = com.yiyiaddon.e.o.b.c.eT();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2rupc5u8zxidq","28OlcI9nA4mG3Jjp3sKNJ8dPcDr98196aIXs/HX5TVk=",-2055294286903121084,-2383454046355172406,5145668348205178909,-1264347204889612006>()) {
               case -2094401181:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2cm7is8ryyb3x","8+oT8J9qq48lguvz2Ne0eNwhUTA87wX5pQF0Y7WZFxo=",9160749259340163570,-4086852912632005376,7093131302232018544,1486944577954326902>()) {
                        case 691596139:
                           if (!(String)com.yiyiaddon.m.b.a<"s1b4d6ea5jvgn0","PUHbAZokZzx8wXRGr/DEaSwk+FqKu1WC+V+kkSW+EKE=",5749137854303935138,5453614979746458018,-5775056324231560218,-7647091094143648778>()
                              .equals(var0)) {
                              label41:
                              switch ((int)com.yiyiaddon.m.b.a<"sn1ubeaa7mriv","gV+IUOVA6QxLKKcfqbDVIAHhutLzh08kCjUxlRcC7MY=",5892484799127758957,-3230311971020907342,1480380958689831332,154635794699569163>()) {
                                 case -2036856559:
                                    if (!(String)com.yiyiaddon.m.b.a<"s3b309xwxj0qry","7G2l8flxnTs126vFWfyxG1V0H7f1f3CZP6pX2RhoJT3nCg==",8179763515033101051,8116185084899282452,3463262084210518766,-4592362555705436021>()
                                       .equals(var0)) {
                                       return var0 + "";
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s19ux1cxooh5cj","avro1DTbYdIIAVJO59qXF7TCy8nC65DQ5gqJ2Pi/8jg=",7844979937439191719,8763185312131712381,322800382650830914,-3957270697264542007>()) {
                                       case -2130720797:
                                          break label41;
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

         if (var0 != null) {
            label35:
            switch ((int)com.yiyiaddon.m.b.a<"samnhax2emk7g","n2Ru0XeAPTJ41E4y2s6PuZ7hYHKXCbc5wOyHgHLnBws=",-3435714456807177505,5264034944422826861,7085705130090404164,8980932319500659967>()) {
               case 1871782970:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ahcagcx8l0qg","g0bJxLfEAIjc1cA/yx1aG/aIWzSkqsdD5DgfBQ6v7is=",8351907879892514079,4406661364630822825,7103363334853647869,-946855102677227043>()) {
                        case -358819385:
                           return var0 + "";
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"siq845m4ubmpw","PujAblbQ/tmZICaEs6FG+DHwWnWnVyhUPLtcyygbEXg=",3506537045057350440,-7575783611546733217,2436905265223663886,-2056268946460449570>()) {
                     case 147565596:
                        break label35;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String var10000 = (String)com.yiyiaddon.m.b.a<"s1b4d6ea5jvgn0","PUHbAZokZzx8wXRGr/DEaSwk+FqKu1WC+V+kkSW+EKE=",5749137854303935138,5453614979746458018,-5775056324231560218,-7647091094143648778>();
         switch ((int)com.yiyiaddon.m.b.a<"s32u5f6fbl3sqd","jRMgU1Fwey0eeTuqGcas4OdEKCmR3V/gRMfHXn+OP/E=",-4206912250318581569,6652281756115720526,5891303870970622470,-2382209356759614869>()) {
            case 1668678736:
               return var10000 + "";
            default:
               throw null;
         }
      }

      private static String as() {
         String var0 = com.yiyiaddon.e.o.b.c.eU();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1w4l737jpkgom","IJv76BA/JJgt68QUOuungGGMQik3fqBhkrFiI/BYr/w=",338537882426818104,9154069959379886582,-4677959432836019900,-2865535224264262358>()) {
               case 1900788325:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s89x3px4n3gen","BoJFMTEqEPnsva0q2Gmgq3rdjmBlHynrrJ8D9vh9hMA=",4701211220963230221,4487133544180073600,-2408007045715520697,-2807477170202185390>()) {
                        case -1306566506:
                           if (!(String)com.yiyiaddon.m.b.a<"s1b4d6ea5jvgn0","PUHbAZokZzx8wXRGr/DEaSwk+FqKu1WC+V+kkSW+EKE=",5749137854303935138,5453614979746458018,-5775056324231560218,-7647091094143648778>()
                              .equals(var0)) {
                              if (!(String)com.yiyiaddon.m.b.a<"s3b309xwxj0qry","7G2l8flxnTs126vFWfyxG1V0H7f1f3CZP6pX2RhoJT3nCg==",8179763515033101051,8116185084899282452,3463262084210518766,-4592362555705436021>()
                                 .equals(var0)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s189cldx5zra6m","yhMpCx7wKsKqDIGPWagpMI0EajeUhTLpiyyIxuHBfmk=",-6080924120801405997,5944330576065651815,5662813594189501531,245842537595472617>()) {
                                    case 1152720112:
                                       if (!(String)com.yiyiaddon.m.b.a<"s3undak923du5k","LWCkncYihUh92dap68fjYjogBUWzybSVo2VAlpnIZDDjOQ==",1381078980965907585,-3623736379593224224,8116915648498680489,-7782419483454074049>()
                                          .equals(var0)) {
                                          if (com.yiyiaddon.e.o.b.c.eF()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2qcozk9h5hph9","T7mwgsMeudwmD7KAv93SKSk8NQG2erHV2Xkco8x/bfM=",-837990702922919081,4841779552457662628,7056900541951709398,-4362409498499892263>()) {
                                                case 1571664167:
                                                   String var10000 = var0 + "";
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2xvtscknfeevh","MFwjGdxQOy1F8VcVPcmhZCMJ+omGHhLgmiQb2sUjV2U=",4462928183693185858,-8102896396902890166,-8713828425663702785,729951240984192710>()) {
                                                      case 2026173561:
                                                         return var10000;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var1 = var0 + "";
                                             switch ((int)com.yiyiaddon.m.b.a<"s3vg6y9bpndkmp","QFT+wQ1Nc1p2almXuhl0CTSR6CW589J1aLcs2EA9OsY=",-4348431032382295136,1555621836561619162,-2618214350014045201,9218916613644891571>()) {
                                                case -103090996:
                                                   return var1;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"sxmcfjkzcartv","OT6p6IC6jcqeLlqR8L4dWDu7uKgBRjBXuoddm9chXCg=",102918682439354814,6283677719041453860,-8579415875743494564,8540212316141085955>()) {
                                          case -1018132785:
                                             return var0 + "";
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return var0 + "";
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sncb0h9q34g1s","0AXeAT84/SagdaKYiC4jm7FCPQcMN/9uagBve/yBv28=",2355260716125014878,-6109823360941482470,3553737437587252548,-6901869800758205864>()) {
                              case -1408222564:
                                 return (String)com.yiyiaddon.m.b.a<"s2dyy456l42436","x3Cl6q9jpDk+UbWllEfZR8V9lAPmjAIlCBoykcsvVRaXh95v",-6918390893734546327,-2237583678366297648,-4960806657587113751,7105865829639306834>();
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

         return (String)com.yiyiaddon.m.b.a<"s2dyy456l42436","x3Cl6q9jpDk+UbWllEfZR8V9lAPmjAIlCBoykcsvVRaXh95v",-6918390893734546327,-2237583678366297648,-4960806657587113751,7105865829639306834>();
      }

      private String c(int var1) {
         return this.ab[var1];
      }
   }

   private final class c implements g {
      private static final float dQ = 18.0F;
      private static final float dR = 6.0F;
      private static final int pC = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         e.b var8 = e.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sdwjdeioxpjxb","CkEzrJXXZKwPWywOmraIIsaadsVyc7ltxTzc/6zDp18=",8871139565539025640,-6680714998969751723,-730191314675117788,-8285855368977235925>()) {
            case -1028406999:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s37lzy3bxwooih","nX6K9Kv30e7ZfT34bgfv9Vy0jnhiQvsj4/FF3URRjsI=",-4871657802764320948,-8754351771403534518,-1881328047790789857,3668049059530467700>()) {
                     case 1311530112:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s68n1977j4f33","q2shgr0GHynUleYOV/NMUC7ZVxQux+FGs4yDkveipXk=",335344681712966113,-703656826901018614,8140907328967665554,4276414398021084547>()) {
                           case 116075241:
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

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6) {
         com.yiyiaddon.l.g.d.a(
            var1, com.yiyiaddon.l.g.d.b(var2, 11.0F, var5), var3, com.yiyiaddon.l.b.d.c(var4 + 9.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var6
         );
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return false;
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }

   private enum d {
      OVERVIEW(
         (String)com.yiyiaddon.m.b.a<"sgyop6e2v2s98","73CzzaR7znJ5v/yhS8qL7LWpP+XDF9qVeUy4KxG87uI=",6413786043002432967,549148301186607363,-433480150602914693,5426988705784433454>()
      ),
      DETECTION(
         (String)com.yiyiaddon.m.b.a<"s2nyp89bi6r48t","H1T/d25bljtTYNyTjkr9ZrCK2ne2BSoFhICus5ZKm67BMoZC",-411698992518764863,-2904236804264025934,-3178747106628887155,9054563895601988755>()
      ),
      RESOURCE_PACK(
         (String)com.yiyiaddon.m.b.a<"sjr9v1uoxaarx","fQzhnTlB1c43/9IklsDXHKBtrjS90zMt5EinPz3xXFOdbJkhViw=",-2372140168599284439,-1086137982614252640,9157378917239290625,8912052992672808585>()
      );

      private final String wU;

      d(String var3) {
         this.wU = var3;
      }

      private String D() {
         return this.wU;
      }
   }
}
