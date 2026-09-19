package com.yiyiaddon.e.n.r;

import com.yiyiaddon.e.n.i.l;
import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.r;
import com.yiyiaddon.e.n.i.s;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public final class b {
   static final int nj = 3;
   static final int nk = 8;
   static final int nl = 20;
   static final int nm = 8;
   static final int nn = 3;
   static final int no = 4;
   static final int np = 200;
   static final long L = 900000L;
   static final long M = 60000L;
   static final long N = 10000L;
   static final long O = 30000L;
   static final int nq = 80;
   static final int nr = 240;
   static final double ao = 0.04;
   static final int ns = 32;
   static final int nt = 8;
   static final int nu = 2;
   static final double ap = 1.0;
   static final int nv = 20;
   static final float cd = 15.0F;
   static final float ce = 25.0F;
   static final int nw = 20;
   static final double aq = 0.85;
   static final double ar = 1.35;
   static final double as = 0.9;
   static final double at = 0.0025;
   static final int nx = 6;
   static final int ny = 16;
   static final int nz = 15;
   static final int nA = 8192;
   static final int nB = 512;
   final com.yiyiaddon.e.n.m.a b;
   final com.yiyiaddon.e.n.a.b b;
   final com.yiyiaddon.e.n.n.f b;
   r a = null;
   p a = null;
   com.yiyiaddon.k.b.a d = null;
   com.yiyiaddon.e.n.e.b a = null;
   com.yiyiaddon.e.n.h.c d = null;
   com.yiyiaddon.e.n.p.a a = null;
   List<String> by = List.of();
   List<String> bz = List.of();
   List<String> bC = List.of();
   List<String> bA = List.of();
   List<String> bB = List.of();
   List<String> bD = List.of();
   List<String> bE = List.of();
   boolean dB = false;
   List<com.yiyiaddon.e.n.k.a.b> bQ = List.of();
   String mS = (String)com.yiyiaddon.m.b.a<"s173b5rkixtcqe","qBEgKgmq2f0nsooBwtYNbaF2SO2PuaKZA708hA==",-8227366057838876949,-4545844672008933477,2652013913585984941,-5798774725690170037>();
   String mV = (String)com.yiyiaddon.m.b.a<"s173b5rkixtcqe","qBEgKgmq2f0nsooBwtYNbaF2SO2PuaKZA708hA==",-8227366057838876949,-4545844672008933477,2652013913585984941,-5798774725690170037>();
   int lA = 4;
   int lB = 16;
   boolean dQ = true;
   boolean dv = true;
   boolean dw = true;
   boolean dx = false;
   boolean dy = false;
   boolean dz = false;
   int lE = 1200;
   int nC = 200;
   int lD = 1;
   int lV = 2;
   int lW = 8;
   int lX = 8;
   int lY = 0;
   Function<String, com.yiyiaddon.e.n.d.a.a> b = var1x -> new com.yiyiaddon.e.n.d.a.a(this.lV, this.lW, this.lX, this.lY);
   Function<String, l> c = var0 -> null;
   Consumer<com.yiyiaddon.e.n.r.b.b> f = var0 -> {};
   com.yiyiaddon.e.n.q.b c = new com.yiyiaddon.e.n.q.b(null);
   Consumer<List<String>> g = null;
   Runnable f = null;
   Runnable g = null;
   private final Set<BlockPos> V = new HashSet<>();
   private boolean dR = false;
   final Set<Integer> W = new HashSet<>();
   boolean dS = false;
   int nD = 0;
   Function<String, com.yiyiaddon.e.n.g.b.a> d = var0 -> com.yiyiaddon.e.n.g.b.a.a;
   com.yiyiaddon.e.n.r.b.c a = com.yiyiaddon.e.n.r.b.c.OBSERVE;
   h a;
   BlockPos J;
   com.yiyiaddon.e.n.m.a.a a;
   int nE;
   int ac;
   int nF;
   int nG;
   Integer b;
   int nH;
   BlockPos K;
   private final Map<BlockPos, Long> ao = new HashMap<>();
   final com.yiyiaddon.k.a.a f = new com.yiyiaddon.k.a.a();
   BlockPos L;
   com.yiyiaddon.e.n.f.a.a a;
   com.yiyiaddon.e.n.i.a a;
   int nI;
   int nJ;
   int nK;
   Integer c = null;
   int nL;
   boolean dT;
   int nM;
   Integer d = null;
   Integer e = null;
   int nN;
   boolean dU;
   boolean dV;
   final Map<String, com.yiyiaddon.e.n.r.b.e> ap = new HashMap<>();
   final Map<String, Integer> aq = new HashMap<>();
   final Map<String, Long> ar = new HashMap<>();
   final Map<String, Long> as = new HashMap<>();
   final Map<String, Long> at = new HashMap<>();
   int nO;
   int nP;
   double au = Double.MAX_VALUE;
   int nQ;
   long P;
   boolean dW;
   double av;
   double aw;
   double ax;
   int jG;
   boolean dX;
   String us;
   long Q = -1L;
   final Set<String> X = new HashSet<>();
   final Set<String> Y = new HashSet<>();
   final Set<String> Z = new HashSet<>();
   final Set<String> aa = new HashSet<>();
   final Set<String> ab = new HashSet<>();
   final Set<String> ac = new HashSet<>();
   final Set<String> ad = new HashSet<>();
   final Set<String> ae = new HashSet<>();
   int nR;
   boolean dY;
   boolean dZ;
   final Set<String> af = new HashSet<>();
   String ut = (String)com.yiyiaddon.m.b.a<"s173b5rkixtcqe","qBEgKgmq2f0nsooBwtYNbaF2SO2PuaKZA708hA==",-8227366057838876949,-4545844672008933477,2652013913585984941,-5798774725690170037>();
   String uu;
   List<String> bU = List.of();
   BlockPos M;
   long R = -1L;
   long S = -1L;
   final Set<String> ag = new HashSet<>();
   final Set<String> ah = new HashSet<>();
   String uv;
   String uw;
   String ux;
   int nS;
   int nT;
   boolean ea;
   final Set<String> ai = new HashSet<>();
   boolean eb;
   int nU = -1;
   int nV = -1;
   final List<com.yiyiaddon.e.n.m.a.a> bV = new ArrayList<>();
   private String uy;
   long T;
   Map<String, Integer> au = Map.of();
   int nW;
   int nX;
   int nY;
   final f a = new f(this);
   final com.yiyiaddon.e.n.r.d a = new com.yiyiaddon.e.n.r.d(this);
   final com.yiyiaddon.e.n.r.a a = new com.yiyiaddon.e.n.r.a(this);
   final g a = new g(this);
   final com.yiyiaddon.e.n.r.e a = new com.yiyiaddon.e.n.r.e(this);
   final com.yiyiaddon.e.n.r.c a = new com.yiyiaddon.e.n.r.c(this);

   public void b(Function<String, com.yiyiaddon.e.n.d.a.a> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kvulp9r4a5cx","Km897ncQ8fAxqQkgqrkF0vAar1D9a0AQKN/DoS4zBxA=",-6923597238930275710,2396846317275229890,7378438977329226532,5386513466022387551>()) {
            case 1584078026:
               this.b = var1;
               switch ((int)com.yiyiaddon.m.b.a<"sj53vxtzjb41o","NRTYzdCRdWJnAUG8tB9l6OnY4sYVVdNk2Sk5EM2NezM=",6867280609177494039,-1754985103865337720,-3412224583618648009,-3098606161002005572>()) {
                  case 1999546558:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void c(Function<String, l> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17jlciy25ttne","8tlkMYHfk3qdlHTRiXsaT6iMtyVg43x5efsbWBstqqs=",6956727238326588767,-6608732720677033305,-6100594018919412732,-2112117575012845341>()) {
            case -1918876635:
               this.c = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s35cz1rjhkg1yu","XigWeuaRL4XOZZgRwIQBoHmZqn1WSE6Ba/E5yXCgcvw=",5652605209404721446,5030124078430524259,-624868867240413190,2356956007612953734>()) {
                  case -2081104406:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void b(Consumer<com.yiyiaddon.e.n.r.b.b> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hjlxeseguifh","JVSxquPLJ72Yrx6R8zTooIdmOhcao0cnwCiXYDE0nyg=",7471966532888107792,6690779027529227345,-3890258562179424647,-3284933541481089586>()) {
            case -1368771348:
               this.f = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s3k6znhkwub48e","miemo8kdPY4psrXCQSryLcPmPRQaE0eYencP1lZRQyA=",4678201140790742738,-1657089725359096213,8247539518179175181,-7637711781922417686>()) {
                  case 322174673:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void c(Consumer<List<String>> var1) {
      this.g = var1;
   }

   public void h(Runnable var1) {
      this.f = var1;
   }

   public void i(Runnable var1) {
      this.g = var1;
   }

   void gi() {
      if (this.g != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xo6hkr629tdj","HawqAO0N6/Ql6ouhSRaQ5NQjKBJ6cjn5qHEpJFnuDQ0=",-4928431345299440641,-3188440202224699191,-4093667810421519871,4899372456165878352>()) {
            case 2112900143:
               this.g.run();
               switch ((int)com.yiyiaddon.m.b.a<"s71d4r9j87wpp","wkaKtnB6SQ/zhWq3xjDukFV6hyL6hOLwcMIFpB0ZsVM=",4100578757884465147,5972495923989359783,-5627814792362295792,-5433283484955883689>()) {
                  case -555272895:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public Set<BlockPos> w() {
      return Set.copyOf(this.V);
   }

   public String ey() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ur168pgsi42v","3Mlr2KPTc7e4Wnw5J53Fe9vK7oQ+ciu56mlyUgwKSIU=",-1351317665729495215,1991113997262934864,6618529804966690557,1918947861517686179>()) {
            case 360732761:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1k3dij4z1yfih","Uv380tB6eqxJTtHmdSYw+sceaS2mjQvnVMZWWbI9lU0=",-1419473120418632064,3260046436116139908,-8097894886799109105,1789312581227094909>()) {
                     case -427652655:
                        if (!this.bQ.isEmpty()) {
                           com.yiyiaddon.e.n.m.a var2 = new com.yiyiaddon.e.n.m.a();
                           ArrayList var3 = new ArrayList();
                           int var4 = 8192;
                           Iterator var5 = this.bQ.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s105uemyvrp7l1","ZtVgxpZeh+wQSP4yHBBxbt4Pc68W3hSB+kBTVQOsPBw=",-5637661225083011164,-3304949985496546370,6680460841750179332,4036464350339003496>()) {
                              case 279097557:
                                 while (var5.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1e9ftmv6cfovj","TcumjIxtFrzqhVIxxbE3Avk8hMRs+ck634iBuONPXf8=",1990110783916943208,6750133249039242646,-3333935501578174431,-8261363478003544605>()) {
                                       case -680026704:
                                          com.yiyiaddon.e.n.k.a.b var6 = (com.yiyiaddon.e.n.k.a.b)var5.next();
                                          if (var6.dv()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2bfchjjwwntmv","dRqi2kpCy+q6P6+Yk0VYUbgwwuAD6uekPpH28oEj6Y0=",-6768938042813672926,-6418386144958760118,3155991914743923808,6198425102042234973>()) {
                                                case 1724865323:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3lepog5hw7bl6","14aqa+GdT1zWGymiLYKlPJFltra4W+Dq8lTlT1ECuC0=",8721017371847526123,7340647633993204270,6843088323287516136,-2745588776932232470>()) {
                                                      case -1530768983:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             int var7 = var6.co() * (var6.cl() - var6.ck() + 1) * var6.cp();
                                             if (var7 > var4) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s28346cd70hb84","xR/S+bsBAJhyPE4mm/7NmbeNK6bKuE4Cn1EkUXVpAFA=",-779326590112217201,-1588103433729727670,7861628588471898918,-1481843201809589051>()) {
                                                   case 1826498450:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s26jjeiwwawqhm","Q5sT9f9y8oFvfX1u5tQuil8qFCKIKE05ohe0uvsiyG8=",3113979564810951933,-4948786488046382943,-8939912857227806612,5421872497274860795>()) {
                                                         case 550473021:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                var4 -= var7;
                                                var2.c(new BlockPos(var6.ci(), var6.ck(), var6.cm()), new BlockPos(var6.cj(), var6.cl(), var6.cn()));
                                                switch ((int)com.yiyiaddon.m.b.a<"s1ffp2a5nvv8h1","/HrtaA5vxqOhUZDudsA96D70Nyyauf2I8tqTmy/oJZY=",2550358898159611401,8138397941645224444,-680987369252675664,-2427314411931911687>()) {
                                                   case 277892053:
                                                      while (!var2.aJ()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2jgwhup2d34do","Nidu1DM4/OQL0f96//J5kuVN++ltqO/zdSM5uQtiRXE=",230181487822605085,3717834321721989307,-2907595785777263628,-8449245408205972977>()) {
                                                            case 306685781:
                                                               Iterator var8 = var2.a(this.a, 512).iterator();
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1xe2cfx1yoy93","7Xg91mP/NeS7+ZByCQpAkf7PwYyCtoRsWBP5eKRE+IQ=",-5637211301957534714,-2952171859105541463,397562824946206077,7218708301670144138>()) {
                                                                  case 1735417175:
                                                                     while (var8.hasNext()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2l00s7dqvu98a","NWifn4u9LCQI+fOo9XspkdsxBSK67opuyIC3Vq77hUw=",3877739773510403002,-3329855924060214839,-6270103115979386003,4255326779829791717>()) {
                                                                           case 1418734727:
                                                                              com.yiyiaddon.e.n.m.a.a var9 = (com.yiyiaddon.e.n.m.a.a)var8.next();
                                                                              String var10 = var9.c().dk();
                                                                              if (var10 == null) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s2yuny79q7lk1u","eFopOISZnBKLQwwhhA+n7ZQ+J2JfxpS0/CtnOIKCgH4=",-8663114203871092330,1453428332331134748,-5394674376491953240,6177301826619101118>()) {
                                                                                    case -849221380:
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s39rxi2znp16w3","POJnuq7cfx04IAopKA/Cw92WbQ7htBdJlyOGjaB+5D0=",-8392665008769772162,6039347790634002400,-8022721440788174443,-2070802231107278860>()) {
                                                                                          case 1723557432:
                                                                                             continue;
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              } else {
                                                                                 com.yiyiaddon.e.n.j.d var11 = var9.c().a();
                                                                                 if (var11 != com.yiyiaddon.e.n.j.d.DEAD) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s8l5i973nia6i","TdtI/n3ba3Kl8iOcWAqh2TRgMQlZ28kBxnaQZYCiVfo=",8625310989104881041,8724573988937665218,1247000678802656243,8172549704055348200>()) {
                                                                                       case 427164163:
                                                                                          if (var11 == com.yiyiaddon.e.n.j.d.UNKNOWN) {
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s172spuswgxn5b","mDU2evEk8tmxnwrs0YzdUltOpZKFU31GZrumlp5rg8g=",-9039971212619827909,6845408328674845248,-5423667239306371032,-1141910328140588439>()) {
                                                                                                case -689740578:
                                                                                                   switch ((int)com.yiyiaddon.m.b.a<"s3sjnv0udep4qq","57g6oiWU3l9uLQ9qaFLHfPuX1hrthvBEoZ2GBMUY4e4=",3786169525510094866,-3711891872651045776,8452260976055986658,-6268096554749170271>()) {
                                                                                                      case 142331436:
                                                                                                         continue;
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          } else {
                                                                                             if (!var10.equals(var6.dk())) {
                                                                                                label91:
                                                                                                switch ((int)com.yiyiaddon.m.b.a<"s1ckzshrrsdijk","CQCKXmZgUDpyRloibSZaGSW6JELE0dRf8qxPm3xSi08=",-1028989732171596673,8269426581465144463,-4304979501323421481,4261995420401864595>()) {
                                                                                                   case 226424214:
                                                                                                      var3.add(new f.a(var9.s(), var6, var10));
                                                                                                      switch ((int)com.yiyiaddon.m.b.a<"s1k1u8pflz8uhr","ncrewAs0AZ+LjCVBCb0fCM/pnLARbSIu4zFns2APtrw=",2955922233051889755,-5521392979725780400,418368874502234891,8225526409606264971>()) {
                                                                                                         case -847075857:
                                                                                                            break label91;
                                                                                                         default:
                                                                                                            throw null;
                                                                                                      }
                                                                                                   default:
                                                                                                      throw null;
                                                                                                }
                                                                                             }

                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s1vt2hie4kwojl","6p0F4O7ua+aMBi5JYlvPuOeRf3ubBp73zIocGw9dpPY=",9098673458570755207,8301935464788375382,111454018597253929,-7288925505946404679>()) {
                                                                                                case -541416088:
                                                                                                   continue;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }
                                                                                 break;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2d56qcwv1osq6","7yXeJ45fRs+me6gDV+fLZkSCn1TLTUHIfa/N9cUa4iI=",8499457708895259526,-6651467047027287797,6191415616518852627,-37289688906224503>()) {
                                                                        case -1515172221:
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

                                                      switch ((int)com.yiyiaddon.m.b.a<"syk9pamtw0z1n","v2qM+C/OIht1MeIoU7hMT/ArWJXUYQSe8949vKy534g=",-679684177611773859,1322202510776542085,9173908945086338881,-6113605606657365940>()) {
                                                         case -1095984414:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var3.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2s9lvax7qdest","BmhvLeqd3bqGjca6dmXI/JXUAT06K2ZD2iTw7foYqQg=",-9035858275538830377,-8246339633575236714,-7454588068242875016,2579780888218996569>()) {
                                       case 1796748990:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 return String.join(
                                       (String)com.yiyiaddon.m.b.a<"s7dv0ytnbfjsc","bTbLBWQ4l6LGW4/lqwqtQLo8bFodJG2mjR7+yznh",16052810439312265,-7349711468901855936,1671670644987741960,-4832477973989639373>(),
                                       this.a.m(var3)
                                    )
                                    + "";
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3aee5imwd7y0d","RSdE7qMzhE7hdMKZF2vw0G4g04/iy00jBrdPbOkpKHU=",-947267106246458445,-3374967090862483682,591653953840615969,-8074163139763513212>()) {
                           case 1290057929:
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

      return null;
   }

   public String ez() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ei9cgvk500ft","fhDzbUikqjGDml4Tko2m7//Fb9HsYdAYO1gZx8T3+0c=",7717286485026280656,-1274647532161930685,-8802510347898718871,-6751856070813431956>()) {
            case -777181188:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3o9vl3weigzmn","MGBvCuFNYu9EVDYpMXcPtsVIdR7rZgS/8VRHh+tvgHQ=",4238462521397456999,536436013743385509,5909056199493654604,3267685991983583294>()) {
                     case -1716713874:
                        if (!this.bQ.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sgpu96ed5ev2b","bGFqWqRAEUricLRFwy7+W4hq01TPD5lBoS7F4qUI22M=",3704012881483532270,-8822522636827474626,-4562996051496687917,7713676330539105909>()) {
                              case 53410368:
                                 if (!this.bz.isEmpty()) {
                                    com.yiyiaddon.e.n.m.a var2 = new com.yiyiaddon.e.n.m.a();
                                    LinkedHashMap var3 = new LinkedHashMap();
                                    int var4 = 0;
                                    int var5 = 8192;
                                    Iterator var6 = this.bQ.iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s3vuodgdgj7tku","LMDbeLHTIWw+QekJc76CuvXog0cvhlt1AYKKDMBtY34=",-4250667513511160577,-6333607887221076275,-7455009552566495244,968519683625189836>()) {
                                       case 635040642:
                                          while (var6.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3mdi7um25cq7g","cDd3tIvqS+1hhQa95oreE4hR6eY2l/0gAGlGv5W7g4A=",-1094676826859248612,340874863893475983,-8756187219680015550,-4899519553311038587>()) {
                                                case -928429571:
                                                   com.yiyiaddon.e.n.k.a.b var7 = (com.yiyiaddon.e.n.k.a.b)var6.next();
                                                   int var8 = var7.co() * (var7.cl() - var7.ck() + 1) * var7.cp();
                                                   if (var8 > var5) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sntk0vpxygb0s","w5tMg+Srbf15TLzLXnGga7V2Zbkm9sf5XBUYw/qju1g=",-526863061757356159,-3559541345214211444,-1383039944985049437,7977650029028220887>()) {
                                                         case -1271354752:
                                                            return null;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   var5 -= var8;
                                                   var2.c(new BlockPos(var7.ci(), var7.ck(), var7.cm()), new BlockPos(var7.cj(), var7.cl(), var7.cn()));
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2c3nvj37hcbig","hkrJVMgh7EebWGnwbGl9rRfvAKcl49ZmPgv5DEw8zSY=",-558891065675544425,2544251944541879291,6874818479273694704,-6676315219413690975>()) {
                                                      case -1718229652:
                                                         while (!var2.aJ()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2llu8ixd5t252","tuqhFX9A5h8fC4bFzLiuLV/clrwWiaFtc7qW7PphQKI=",235783380715392104,-4553171590327773964,-8662161592770692005,-787220713032052024>()) {
                                                               case -1516281507:
                                                                  Iterator var9 = var2.a(this.a, 512).iterator();
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2iicseikqpe4l","AMYtznvMwg4QxRy8rXOG6Et4G3qrGSd07WMf2M1YMos=",7180891602767595620,6813943381217175255,4084386661670029393,2151313032051965729>()) {
                                                                     case 1026093664:
                                                                        while (var9.hasNext()) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"so8cdbwma64q8","zbF50x8rmdxDYFwQWL0cHziKeistzS9dSYGEO8w/xf0=",-2663057614210981544,3888645580583608905,-6807806159544348922,2127298534606450506>()) {
                                                                              case 1144432771:
                                                                                 com.yiyiaddon.e.n.m.a.a var10 = (com.yiyiaddon.e.n.m.a.a)var9.next();
                                                                                 a(var3, var10);
                                                                                 if (this.a.j(var10)) {
                                                                                    label81:
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"se0j8hlx0hiit","4aadV2CAvcJ60D2FHpXxYJ4pIFZBiNOY9HesWLb7I7c=",715901030840226537,-2816309421575699601,-5372603811583362420,-4615183499782282316>()) {
                                                                                       case -905863435:
                                                                                          var4++;
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"s3af4m48ohxxmm","iVpIFAOwFyFEM9zd9CULYfly6trJt09epKHs7G448Ak=",2027749333957821034,-7404421265232366509,-1921614159881405751,6908813183312225511>()) {
                                                                                             case 1195880852:
                                                                                                break label81;
                                                                                             default:
                                                                                                throw null;
                                                                                          }
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }

                                                                                 switch ((int)com.yiyiaddon.m.b.a<"sfosb5ktbzen2","7qYH0NfBN4TqLgijBbxZUnPnJaUTUDXM1hyERwm1+pY=",-7962426539645527849,8175730409193182901,3021742055943724624,4008441053973883485>()) {
                                                                                    case -1309564385:
                                                                                       continue;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }

                                                                        switch ((int)com.yiyiaddon.m.b.a<"s265ucn3q2viqw","i8IgNpMDOQwIjwv4cVBjthUnR7kCnNbPqMMilCO7vMw=",-4201332351218148042,-9136108603520114802,-8210546345613185564,6409302685688730231>()) {
                                                                           case 2069466702:
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

                                                         switch ((int)com.yiyiaddon.m.b.a<"sspsiv1pt3cdm","dCEDdfkn/MJYO3agSVtzxj6o/eslo5CUKKSVCS01hLg=",-4991976900710388238,5799846842474211359,3785492542451930300,6655172059859089885>()) {
                                                            case -1621421287:
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

                                          if (!var3.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ins89r02t0l6","ymVTChjQMDn1xWt9tXaTUMDB+gDgmo+S+rJvY//Rz9c=",-2158788145241960543,7844473187302260691,-2738089857649714180,5557445539614077964>()) {
                                                case -846648203:
                                                   if (var4 <= 0) {
                                                      return c(var3) + this.eA();
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s3kpzc85yz825h","gCwmehykW+BMO+tsdXQAbLLIOX0QAUl12c3wWz6WXYc=",172688320047864370,-2944263172856284719,-2617480737415425407,-6591348279535066272>()) {
                                                      case -1592448907:
                                                         return null;
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s2lqxfcm84hxh8","+avmT3un0xYAXyQbsftGk+VOfPEk0bd+l4/oKolf+7Q=",-31880747050672720,-8465830968387684943,-8975831642383363845,-8930133626303103299>()) {
                                    case 1858172003:
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
   }

   private static void a(Map<String, Integer> var0, com.yiyiaddon.e.n.m.a.a var1) {
      String var10001;
      label29: {
         String var2 = var1.dU();
         if (var2 != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s2whd6cgm336jl","n32f6jJOI++LQ2Br6+MwWlj0yqXemP6PTYxCOCIGvo4=",-1361342110885752001,-9105304049377180685,-6314259350068454472,-5859159362996878260>()) {
               case -1382452934:
                  if (!var2.isBlank()) {
                     var10001 = com.yiyiaddon.e.n.j.e.d(var2).m();
                     switch ((int)com.yiyiaddon.m.b.a<"smz98221sysko","5yJWykc2SPxIRL80WWxYQMUg1USZ5e2KAvOxcM7ohXU=",4548654830088403724,-3284513021694818495,-416539543465654738,-8501512778684030029>()) {
                        case 1025563946:
                           break label29;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2u639pn5um1op","xYrN7sko983QmC02B4Rzba6cVu71V9gPeuNM3euTN6U=",1922570861960453772,1748562598027812130,2471985481388947352,-5205498100102098116>()) {
                     case 1093201224:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10001 = (String)com.yiyiaddon.m.b.a<"s1hiyw8f54tvov","V/nhClgJte5YrXlhutsNLn1u5ZlHORR8fsdhywkow9zq7bE7Deo=",6200327903954314964,6640708151561772077,-7265054724256361247,620923377564846970>();
         switch ((int)com.yiyiaddon.m.b.a<"se1onunfhfoqm","4GErt+4tuFrl4kbIptVJIAT9ECVWOX5T7SDokoPJPxo=",-4858241733142651954,2431143472401599406,-7470273306982044493,7773232057323300279>()) {
            case 800916253:
               break;
            default:
               throw null;
         }
      }

      var0.merge(var10001, 1, Integer::sum);
   }

   private static String c(Map<String, Integer> var0) {
      StringBuilder var1 = new StringBuilder();
      var0.forEach(
         (var1x, var2) -> {
            if (var1.length() > 0) {
               label13:
               switch ((int)com.yiyiaddon.m.b.a<"s2nzaepw1q0kcc","g062njRMm3ag71vu/s8F80ds+YblN2JF0BZIMJ+kJDM=",1972761105946500962,-3410245782406385129,8145504284596259890,8524704300571297849>()) {
                  case -730074673:
                     var1.append('、');
                     switch ((int)com.yiyiaddon.m.b.a<"s17wyswyabacgz","DEAe4+ClP8EYdAPPxkWJay8tRajwHWHYrR06l1bnG4A=",6771511145287174511,-4460232416481876163,-3935861281558283888,3295849538954438077>()) {
                        case 38986594:
                           break label13;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.append(var1x)
               .append(
                  (String)com.yiyiaddon.m.b.a<"s1a6r1jegtq90","qEUy3u/YgNe/NcLImBL6ba5U7HvLGh8+ENWdkoeCgio=",-7637248678223522431,3257277172628833725,5804616579980143307,-3112483104510942913>()
               )
               .append(var2);
         }
      );
      return var1.toString();
   }

   private String eA() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.bz.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1n2g680bectdp","P8Lwm8GA2FkW5PGKdnZSCODY9admGEaX/MDxtuJg/7I=",-8800565593551916552,8034502706043532398,-5994212565742199260,-3414735647056836735>()) {
         case -168954585:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sykoq8r0e0k30","14buP4sTZ2cJ8pFIdHwq1WqEQkFo5+5FNxA9mqmbSqg=",4192324080094362202,-5286495856141924180,8015445717303544425,-3663684433432938260>()) {
                  case -1694004093:
                     String var3 = (String)var2.next();
                     s var10000;
                     if (this.a == null) {
                        label49:
                        switch ((int)com.yiyiaddon.m.b.a<"s1hi89k6hipnja","RpAhYSfGo5+zlYt6kwYssJ2IVSgVzQQLzo2EuOYBtE0=",5096188741079251607,4110838172385686772,2268180000220532258,-6998446659643868778>()) {
                           case 1072033021:
                              var10000 = null;
                              switch ((int)com.yiyiaddon.m.b.a<"s3t75omcdh8cry","Ua+kQORSzX78BVdLEPfFF3abyKuQHjshk33Ftlt/ZgE=",2234625811684468158,-5647342749520345279,8610027197707480668,-1850459265194868049>()) {
                                 case -856849176:
                                    break label49;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = this.a.a(var3);
                        switch ((int)com.yiyiaddon.m.b.a<"s1tucarc77zfj6","X8sf39drvBOrmJC0CtwO1Mj2N+mBuW4dJs8RlLpwvGk=",4499225948706999801,-5187732223747883254,1305753279427683475,-5018968296155547392>()) {
                           case 1046486850:
                              break;
                           default:
                              throw null;
                        }
                     }

                     s var4 = var10000;
                     if (var1.length() > 0) {
                        label45:
                        switch ((int)com.yiyiaddon.m.b.a<"s18171spnfi4cu","+lu/ysY5hReB0mNX2paMzsLyoSP0RnWbss/i5jnxmmE=",2448711106113260376,930615658379865595,-6636569551522778002,-2467616805543131591>()) {
                           case 1076563963:
                              var1.append('、');
                              switch ((int)com.yiyiaddon.m.b.a<"s8rq1whzvtztn","2igydfoGqC3ZdDSrz/uGazto2pecStxyggoQFQONBtc=",-7336104654997044526,-6955833231781572662,2279035064504504052,-961436736594580606>()) {
                                 case 2122551831:
                                    break label45;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     String var10001;
                     if (var4 instanceof com.yiyiaddon.e.n.i.b) {
                        label41:
                        switch ((int)com.yiyiaddon.m.b.a<"s1ez0jbb2st4sn","ufL/xLM5SoDlnYyGQHYALBg9dAzpMQE1vglZ2ThlLmA=",-387812702673459057,7654213846881082278,-493201519408422976,-3425472207976384888>()) {
                           case -842644011:
                              com.yiyiaddon.e.n.i.b var5 = (com.yiyiaddon.e.n.i.b)var4;
                              var10001 = com.yiyiaddon.e.n.j.e.a(var5.bX()).m();
                              switch ((int)com.yiyiaddon.m.b.a<"s2zg705ttzsd96","nnB+tvI3NTR1GsVxumUuK0yY0URxbHhTi23PX4vxH04=",2574250423893779721,-3537792937589689780,-6113050259618285813,-1678345006667761419>()) {
                                 case 1733770969:
                                    break label41;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = var3;
                        switch ((int)com.yiyiaddon.m.b.a<"s1em2bouq2g8dk","omhTnYbSAfPePO0ReXduDelmekoh8LXxo6e0FKWUan0=",-3009171118668499356,3577309743800694512,8475170742905967103,-2850878904587171477>()) {
                           case 1328861962:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1.append(var10001);
                     switch ((int)com.yiyiaddon.m.b.a<"sw2ov98lrzj5t","Nt9S1rILsm+cvf95/UkB0KZ2pGXsqYbciX0jskm4dYs=",-1728154298690483751,4409793963186177938,5496959827500195100,-787382683133346785>()) {
                        case -1362544153:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1.toString();
         default:
            throw null;
      }
   }

   public com.yiyiaddon.e.n.r.b.d a(com.yiyiaddon.e.n.k.a.b var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pa0oek02y7a1","NsRYFc7Im81owRk/lbd2J0X65qc0AKxlETqaZX9vIDU=",-6314981328612656342,-8321400851452217075,-220006274137563980,-6667638773432206155>()) {
            case 122520372:
               if (var2.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sm2p9v657z8ly","bPImtyx35VXIZ0AJeN5/YyWHPaKkXYXp1DXQsd7Qr00=",8265655588379108956,2435161820222137130,-3960577616638685860,8002039618949240814>()) {
                     case 699370383:
                        if (this.a != null) {
                           int var3 = var1.co() * (var1.cl() - var1.ck() + 1) * var1.cp();
                           if (var3 > 8192) {
                              switch ((int)com.yiyiaddon.m.b.a<"s13v5k4vwyiar7","TQmj1kqhQx2w1PcoqxZ8ZVZuNOGjh9NatT20OwuVmns=",2745431317687296756,6291502506121688561,3739014392654390630,2521166218277184164>()) {
                                 case 1189192798:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }

                           com.yiyiaddon.e.n.m.a var4 = new com.yiyiaddon.e.n.m.a();
                           var4.c(new BlockPos(var1.ci(), var1.ck(), var1.cm()), new BlockPos(var1.cj(), var1.cl(), var1.cn()));
                           switch ((int)com.yiyiaddon.m.b.a<"ss9w1kghum0yo","GRvtmZEYO+LBzcQJDGa91xmCEJ/Qmr9GC7w28EmVft0=",-8250115467380862610,8303513333606101385,5215804584257635034,4120155735978991376>()) {
                              case -15977014:
                                 while (!var4.aJ()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ok3ecvtzxqde","YCtc1uapgGMlwci7K8KTc79fvtExroL2hueOMueuNI4=",-5656635676174460304,6783242199421090725,5299558261694180522,-6222878101767769732>()) {
                                       case -816109798:
                                          Iterator var5 = var4.a(this.a, 512).iterator();
                                          switch ((int)com.yiyiaddon.m.b.a<"s19l4cwkw1qx4u","yhlzteRfoR/LLb8GBrxM8UdQGZm1XlUQXJ9fu9RUjdg=",-5832599170789957903,-8708855785242373016,2885629108996774720,4991274186845838541>()) {
                                             case -2145398158:
                                                while (var5.hasNext()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3uxp83djzg4ik","HMPnroCnuJ8ZXdWYozTyS3JxTsnVsCQFrA6bex35HY0=",167030331479897921,-8090609377955608262,1734111542102228197,-8651651028252275639>()) {
                                                      case -827997744:
                                                         com.yiyiaddon.e.n.m.a.a var6 = (com.yiyiaddon.e.n.m.a.a)var5.next();
                                                         if (var6.c().dk() != null) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2yrcnrykumwbl","FIIGGp1heE7WXus2OHE+IS8MJJ6PVUCGVmh9i5+GkTI=",-8301552701570135610,4058911877236183124,7529507669427721252,-4823762843691671645>()) {
                                                               case -727210638:
                                                                  if (a(var6.c().a())) {
                                                                     return new com.yiyiaddon.e.n.r.b.d(var6.c().dk(), var6.c().a(), var6.s());
                                                                  }

                                                                  switch ((int)com.yiyiaddon.m.b.a<"s28vdw43u1vvso","ufAnKoxO6aCua9k+XA0i7lkQRVl06pFLSwNInAoew/E=",-3912741207064864941,-6343324778206117883,-702689867509389764,8714210548370345936>()) {
                                                                     case -42477413:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sir12ui9d13zf","ASmoFg0AUJ4eCTOgrZA/cvew2NELwga/+qZBYq7uSDU=",242016212465194403,-1754918892928874271,7653179618499995094,-4719177988031642173>()) {
                                                                           case 478144928:
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
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s9fj81zmpdw5o","J6SrFxp7mRZ0N8iGOsf49ww2h3e1+L7x5GD0lzYUmaA=",7965063431154408328,-677784822447871427,6087680073889957128,2707704757491540594>()) {
                                                   case -967175761:
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

                                 return null;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2b2pdo7hp2tqa","3KU+vk+92qvk5jyG+1kY00jyUmyGv5Z0VIPJsFGNtLE=",-8539999424327661675,15872929261333201,-2628238005508969562,-57219639586477382>()) {
                           case -407153925:
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

      return null;
   }

   private static boolean a(com.yiyiaddon.e.n.j.d var0) {
      if (var0 != com.yiyiaddon.e.n.j.d.GROWING) {
         switch ((int)com.yiyiaddon.m.b.a<"s1d9npby95bkf7","8QGBuBqcYOhI9038ogJizD9+JRh8z4iWUwFRY/5Iqbc=",8416145940146004887,7131905562927295067,7209984174784525123,-8788409146591855366>()) {
            case -2111274069:
               if (var0 != com.yiyiaddon.e.n.j.d.MATURE) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"scc6gdc1d0t7i","ZT6+yngENFVMKeU37Uy++eVowxF4qPhIUwkxnNqweGg=",3873388629256048130,1574401629039312923,-4009112054673504780,-5068777526328257726>()) {
                     case -1272681423:
                        if (var0 != com.yiyiaddon.e.n.j.d.SPECIAL) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3e9hploy5ulvm","evdCTzszG/jgWo0NLsdaVo787fL+Noz+I7n+R0vym/Y=",740906526920896239,-4759184471328379972,5130953420406319914,-7316632798464987438>()) {
                              case -98678610:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2pltsi162srjh","9ktVCBLKcL2JU8Ic9tK2hml7gaXrlBQaLblmFIFz2Ig=",-7272593591736080348,434450151961626409,-8337516631112943830,6269053891193387686>()) {
                           case -687935549:
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

      switch ((int)com.yiyiaddon.m.b.a<"s8r7q3gq3yggf","8ux4/7zcQmo8y7m7dGluL+dXHs9tr9aXZSQ1IWevqXM=",-3152894796882176327,-2796214879058560489,-2966119599520313756,-948038786557752441>()) {
         case 1894854553:
            return true;
         default:
            throw null;
      }
   }

   com.yiyiaddon.e.n.k.a.b a(BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s57c2h20ugne2","c1Vm4ZVBvUxRS5HxDNpvw47Sq7ZLHNEer0//J44pJps=",3143023061482201457,3242330868893848824,2814140921494524094,8100615940721302914>()) {
            case -1946080759:
               return null;
            default:
               throw null;
         }
      } else {
         Iterator var2 = this.bQ.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s17pk3edyz2rzw","2iy1xNMUf7/SD9IwAsYZdQsviBcFNB9HufPv+szcFX8=",-4965100226815413012,5252801204523519755,2816469343366648256,-6996546690977742188>()) {
            case 793651840:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ap0bl57201ky","MtDFZWpfZ2y/8tQ5sISoQR62LodUqmD4Mg+x0rvl9ps=",-7383757126365903123,5734738358555461282,2680703020005083922,-166725362434857524>()) {
                     case -1760675905:
                        com.yiyiaddon.e.n.k.a.b var3 = (com.yiyiaddon.e.n.k.a.b)var2.next();
                        if (var3.k(var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"snvnfqj0ubw7b","9mrop248o06MwmNDCzh4oyjC/X/8k9bTQnqCx1hxIt0=",1849274427428353023,-4394190742639740260,33484071283483957,2184868225689516611>()) {
                              case -1321889277:
                                 return var3;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2t5z3cozb98c1","AkLsHrBswn8nhNXGn5JHUxmTkjiYL4cYdDoJWC9T7zU=",-7417472235010480639,-352023300418901466,-5377161722730692637,6997436677305990536>()) {
                           case -1729798885:
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
   }

   com.yiyiaddon.e.n.d.a.a b(String var1) {
      com.yiyiaddon.e.n.d.a.a var2 = this.b.apply(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"svfffkfp4nf7p","1PZeWjA5+zv+5DMxTlNvM4x33AyNbDm+tsI7+wjjLZg=",-4881858701292644400,-5792353452260554737,-7047535635949690599,1812519620172664764>()) {
            case 1778013482:
               com.yiyiaddon.e.n.d.a.a var10000 = com.yiyiaddon.e.n.d.a.a.a;
               switch ((int)com.yiyiaddon.m.b.a<"s10r8jcozhx9ao","UeIWtoByYxU0PH+6o0I3o+6/lGvu/mr7rUpNHYh8lj0=",3742041021256038755,-9120972075447251633,3528567244093802992,1877471474401431951>()) {
                  case 463936491:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3vrti7jzmvp7y","3PbcjqSMSCFJldIqfx2Es0wP8Sg3TQz88sFMycCj4gI=",7426809563159773188,-9210867194394212056,-8426282669502151896,7166857636595688122>()) {
            case 1623515129:
               return var2;
            default:
               throw null;
         }
      }
   }

   public void a(com.yiyiaddon.e.n.q.b var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"surxdgbyhc118","TTnje67VzTmJuP72njmTun8D1aQA3y0ke0NBnV6vGmE=",-2276655104319691230,1335043573964461146,-8199790261499514825,-4235278025961068255>()) {
            case 805224870:
               this.c = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s1sqyi1spy7slr","B4WOngJ+yDkGqbUUEmjFzzyHbKp9QCJKe+wgmhyKl3k=",3494578180418921845,508904839433616884,7704786627583746034,363894940316366289>()) {
                  case -1217860138:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void d(Function<String, com.yiyiaddon.e.n.g.b.a> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nliqeqvz8j2d","bh+jnEu9R36Jim42F//AUxMYm8Gokm+o8ZgX9h4JPqg=",1824994620516868188,8481218623417178092,5598840389898971936,8030689446271488232>()) {
            case -231418076:
               this.d = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s1v7dx3q8rpsd5","LdEdQ1uqqRiKygBHS02csha1pSV6mER76qK+8sq0Amk=",622430115512221822,4538899413863474460,-891173016376692065,4857736998365571812>()) {
                  case 584871811:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public b(com.yiyiaddon.e.n.m.a var1, com.yiyiaddon.e.n.a.b var2, com.yiyiaddon.e.n.n.f var3) {
      this.b = var1;
      this.b = var2;
      this.b = var3;
   }

   public void a(
      r var1,
      p var2,
      com.yiyiaddon.k.b.a var3,
      com.yiyiaddon.e.n.e.b var4,
      com.yiyiaddon.e.n.h.c var5,
      com.yiyiaddon.e.n.p.a var6,
      List<String> var7,
      List<String> var8,
      List<String> var9,
      List<String> var10,
      List<String> var11,
      List<String> var12,
      List<String> var13,
      String var14,
      String var15,
      int var16,
      int var17,
      boolean var18,
      boolean var19,
      boolean var20,
      boolean var21,
      boolean var22,
      boolean var23,
      int var24,
      int var25,
      int var26,
      int var27,
      int var28,
      int var29,
      int var30,
      boolean var31,
      List<com.yiyiaddon.e.n.k.a.b> var32
   ) {
      List var10000;
      if (var7 == null) {
         label213:
         switch ((int)com.yiyiaddon.m.b.a<"s1ht1alkxzse5q","rRQrIA+zfrZFQm36QgHgnQoIZkuQBQomYsuVh1RHZHs=",-7596397474401717421,5393444485883305904,3519056781563957968,4972503142624191632>()) {
            case 1457879469:
               var10000 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s1mrdeykfy2rbs","OIFQBNEFNFYw07A5/rpK0rWNMcq2MQsPjM2JluuvENA=",-3245266831402518518,5270296142680709839,3138720933844733063,845250355780261776>()) {
                  case 114576957:
                     break label213;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = List.copyOf(var7);
         switch ((int)com.yiyiaddon.m.b.a<"sxrrsrswc9cnv","SvpPkSKaA1GpbQNwNQDIjEczEqzwH9CG9jZGRAo1Fxc=",-4021906327109021360,-2606103511239714141,1319008049932660124,5196313292534639907>()) {
            case -172966410:
               break;
            default:
               throw null;
         }
      }

      List var33;
      label248: {
         var33 = var10000;
         if (var5 != null) {
            label209:
            switch ((int)com.yiyiaddon.m.b.a<"s34iyaqym6fsks","eajcGP/XeZvbLUhCRvuUh+g9cFwD4CocrXEvu8a4hZ4=",3163920425807140924,-1545711317248729914,3904749682054242562,7870448329537199170>()) {
               case 1819433500:
                  if (var5.a(com.yiyiaddon.e.n.h.d.SEED_BOX) != null) {
                     var40 = var5.a(com.yiyiaddon.e.n.h.d.SEED_BOX).a();
                     switch ((int)com.yiyiaddon.m.b.a<"sr5xsfs61g5xh","Y9MuRO9190Kg2MFIST8Mo9bD946i4XSzmpjCRnPCzOg=",-4066470981777990585,8898931277847436476,9155525600127830443,6302347136737534206>()) {
                        case -2033691590:
                           break label248;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1wo6smzjswjvc","AGXE+tBLZCRKrBQa1Eo5wIWPqjpvCRHuWOb1WHZ2EeM=",-4330513475360428758,-4707348640375723182,2266428921062294304,1944370090265912218>()) {
                     case -2138128945:
                        break label209;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var40 = null;
         switch ((int)com.yiyiaddon.m.b.a<"s2umvr5325mc1y","InJjrcBnjrBhe1YjDo3uJyAqzxiFK7Tq86YAUidVTuQ=",4975468796162009092,-2984525353127487843,8304173059071548455,-7680677972912857363>()) {
            case -1164725132:
               break;
            default:
               throw null;
         }
      }

      BlockPos var34 = var40;
      String var35 = com.yiyiaddon.k.e.c.dn();
      if (!Objects.equals(this.mV, var15)) {
         label201:
         switch ((int)com.yiyiaddon.m.b.a<"s1x25ahytmlvjf","k2HrKgAc50e0aHZLR905wmkSYooa3MUfafRDqpF2p64=",6658514616155506266,4306246341363502659,3177009201295906852,205456905873576074>()) {
            case -1652592931:
               this.aa.clear();
               this.uy = null;
               this.T = 0L;
               switch ((int)com.yiyiaddon.m.b.a<"so0ny7m26r8wk","JXZB2DTJqxFQ2fsEhEpRV42MLXt+6+SPpKWY10vZFsI=",4419906510021932404,-770371368070144019,1311444709985420615,8662784468606469453>()) {
                  case 1811454191:
                     break label201;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      label240: {
         if (Objects.equals(this.ut, var14)) {
            switch ((int)com.yiyiaddon.m.b.a<"sqktotvqit9dr","JbFXgEgNJqPg24oPpQ7GArGDi1JRu1N88LUmjyFMtwQ=",-2734897097857688232,1423504376570167645,-2239720523539264963,3687375511586415616>()) {
               case -2020573659:
                  if (Objects.equals(this.uu, var35)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3tiwkqc19gwvu","jNu7B92xw/idws/CnI83noxYwGav7Rtq7OWfl52mXPs=",8791660060425051610,-6971075717876794833,-5043324991815975617,-624369805668054308>()) {
                        case -1711137580:
                           if (this.bU.equals(var33)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3bl40erzgblyu","OOCRZo2E1r8BT27eJB6biq9CzamCyq87J12xwqXW0/E=",7452390132165534915,3214939967911100224,-3690513960527551757,2510022308916815185>()) {
                                 case 2047796147:
                                    if (Objects.equals(this.M, var34)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1z8okf0gnngga","1RoeQ7657GZZcP846PGUckvQBjilE4jcQmtDEubeS6s=",-7699828163327936895,-7514472641126822070,-7531210601290013257,4690437386860170944>()) {
                                          case -577291469:
                                             if (this.R == com.yiyiaddon.e.n.g.b.o()) {
                                                label189:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3jfomj74mn9jv","WJ/fg0FmWYrvBuy5eKSmvYc1LUWnmx5Htxbq9uvD/cw=",-249307988421713253,-7741048872235795500,1712184494913538296,91712272024771483>()) {
                                                   case -1720881815:
                                                      if (this.S == com.yiyiaddon.e.n.d.a.o()) {
                                                         break label240;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s2cg7wrlaz32ir","O7cSTJ6j9H+kAlrjUFf5k92YnJ1X2rJKZG4AC47QEZw=",876518834733225592,-452097298530277949,-4698864264648896599,6269933638048453101>()) {
                                                         case -1285103275:
                                                            break label189;
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

         this.ap.clear();
         this.aq.clear();
         this.X.clear();
         this.Y.clear();
         this.Z.clear();
         this.ad.clear();
         this.us = null;
         this.af.clear();
         this.ae.clear();
         switch ((int)com.yiyiaddon.m.b.a<"s2a62xmjvklwfy","0+VYIes7yr6C1rtvYlBpakXgtLWfR2mYGVLgPEK9ZHY=",-5496872544331343828,6495041254594988932,8063736202232663660,-8675664112704834759>()) {
            case 1001043374:
               break;
            default:
               throw null;
         }
      }

      this.ut = var14;
      this.uu = var35;
      this.bU = var33;
      this.M = var34;
      this.R = com.yiyiaddon.e.n.g.b.o();
      this.S = com.yiyiaddon.e.n.d.a.o();
      this.a = var1;
      this.a = var2;
      this.d = var3;
      this.a = var4;
      this.d = var5;
      this.a = var6;
      this.by = var33;
      List var10001;
      if (var8 == null) {
         label180:
         switch ((int)com.yiyiaddon.m.b.a<"s26ze3ujaofby2","mthM1QfeQBi2KFWNtG5LVPXbSf5vrXa1IQxOBlG0sWM=",-3819284927000417807,-4342692962299853293,-3993990078998941383,-1918507221609149101>()) {
            case 1033140949:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s1l3ehz80laaza","lMGeyRB2p1mHHZ+/F7LZrxUJfHyB0v/WlX6mdKo13O8=",8438972015266452311,-8047064028202459165,503420018391948501,8711622594983983132>()) {
                  case 449263486:
                     break label180;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var8;
         switch ((int)com.yiyiaddon.m.b.a<"s1b2prh01om1o3","U/jEhZzbINulTIsr+YRmRVY2stb1uNmxA29gUoUJXL8=",4850505741265221991,4254557642558799007,-8347569229549347348,-2163016759034596822>()) {
            case 851290115:
               break;
            default:
               throw null;
         }
      }

      this.bz = var10001;
      if (var9 == null) {
         label173:
         switch ((int)com.yiyiaddon.m.b.a<"sf4ea77g1vtg4","zvFvsPj5ZcIZcHdRHeU6LjMa/j1IVpe9VNOAazju2hc=",-3988501278945881507,-8287741380330840451,8370994804772516601,-2003812330711407355>()) {
            case -1615605790:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s182a8izldcy9h","bskISesrbICS06/oYeCEzKdCDgyqc8u9VP9oovFJSH0=",-1786653342242072007,5651495177907785341,-625936768652055295,-2669190646874237713>()) {
                  case 1566586441:
                     break label173;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var9;
         switch ((int)com.yiyiaddon.m.b.a<"s323dqdr1o6200","yH5+wl2v0f/UcMEZ4SCgluLt2TEoR5LxoC9DmBohnbQ=",5831264438306907576,8295490660747525972,5219327635694406833,-1812925043560146227>()) {
            case -752300182:
               break;
            default:
               throw null;
         }
      }

      this.bC = var10001;
      if (var10 == null) {
         label166:
         switch ((int)com.yiyiaddon.m.b.a<"s3e4v7nwy7s3br","WrFwSg1640KW8AWh0yNr0nT+2f5SDkQLfuVX7RjFwUY=",7620022553332415339,5756911281072537635,8077278244587791540,-2413295578970058894>()) {
            case 174244006:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s17cj5cjuk7y1z","Ll51l6SoZHmwIQwmcqjwO7DeIPX8MgJn1/27hoTlnGY=",1904906050367671741,-8319082179244311008,1288082266428693363,2479668898617968482>()) {
                  case -1686027746:
                     break label166;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var10;
         switch ((int)com.yiyiaddon.m.b.a<"s1dxzw99g1r6sk","TUVcadL3FMCVCuXMcHqXVpjzu31arXbp4HyTlBoag/I=",3336295524527396068,-2454708785908409947,-3875536123429597351,1970918989775014848>()) {
            case -1185534533:
               break;
            default:
               throw null;
         }
      }

      this.bA = var10001;
      if (var11 == null) {
         label159:
         switch ((int)com.yiyiaddon.m.b.a<"s34kpa303hm6pf","1Tzd+WDp0ed+508Hc+eeYVzFaCS+916dgknLc301QYg=",2370115587211997612,7257575882492843049,-6674618069340484471,6487184396241559689>()) {
            case 1683132468:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s3if60uke3n5kh","OaPgKzWJkqK64AHc8RKTT03raQnvyDakp4Ll/9gUXDg=",5471889079124272735,-4424175573978202967,-2228398918307272978,5968312787137013750>()) {
                  case 1988389488:
                     break label159;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var11;
         switch ((int)com.yiyiaddon.m.b.a<"s2aq2gg99iutz4","E3GrKdp1C/91OJyXbJQRkPz+RHKDGrjJ87d1il0Gal8=",-8902136376614209213,35503214866680528,204521108348131009,4325314117027880507>()) {
            case 491956938:
               break;
            default:
               throw null;
         }
      }

      this.bB = var10001;
      if (var12 == null) {
         label152:
         switch ((int)com.yiyiaddon.m.b.a<"s1y04u3surdnvk","zDtDpUqDWf9tqOoecP87g0An3Ct86YO9eiuy7w95zVk=",5013156777920321023,-2330979091443259161,7754326847148677599,-5277561004836580611>()) {
            case -1416385846:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s2phu1tql2uiur","wCDyTBMQCnCZBK+meDLFQBscFtGGDvf0esdSqQ9xen8=",8383698178511354850,2672116487915196258,4633398043150008920,442458509195880598>()) {
                  case 1486633921:
                     break label152;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var12;
         switch ((int)com.yiyiaddon.m.b.a<"s2ydjgpt2ojtrm","Pl9BEo2H2msQFLIBwRC4Aa2yMuxrb2HudceICrnLB0I=",3645321173990743267,-7528321714184526227,-2698278900192391717,-700694369267420153>()) {
            case -1619966746:
               break;
            default:
               throw null;
         }
      }

      this.bD = var10001;
      if (var13 == null) {
         label145:
         switch ((int)com.yiyiaddon.m.b.a<"s1hsef4xzm921m","gEmeazXuNmEKpfqQMFoI1WpHf8i56MwIq6bJm1xWrKk=",1395360234298490214,8819145461657659705,2658976085389320143,-5270652736418612989>()) {
            case 1372098242:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"smmwbwl4kidob","oGEZWdXMawgSBSiCy73U9dk6G1XdBneiq0u2P2hm0cE=",8815921717024025360,7117994215239136188,-9167759376297041497,331783073565679959>()) {
                  case -548959089:
                     break label145;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var13;
         switch ((int)com.yiyiaddon.m.b.a<"s3co4tfaz8ewgk","UKjM2x3tZgRLaiXO3cDMRzgSInbfcDfcb0BEDLJujj0=",-4674080065758802809,6117293554679489250,2375958203733844797,-1415634897062301763>()) {
            case -1422159053:
               break;
            default:
               throw null;
         }
      }

      this.bE = var10001;
      this.mS = var14;
      this.mV = var15;
      this.lA = var16;
      this.lB = var17;
      this.dQ = var18;
      this.dv = var19;
      this.dw = var20;
      this.dx = var21;
      this.dy = var22;
      this.dz = var23;
      this.lE = var24;
      this.lV = var25;
      this.lW = var26;
      this.lX = var27;
      this.lY = var28;
      this.nC = Math.max(1, var29) * 20;
      this.lD = Math.max(1, Math.min(15, var30));
      this.dB = var31;
      if (var32 == null) {
         label139:
         switch ((int)com.yiyiaddon.m.b.a<"s2mnpz0ck076kg","luEDq7TfgGVZx5UzCp7lyCkW/TAt20htKyXEkeSmYY0=",-2954140684157125005,-1491575984586412749,-9006537171036477776,-1590808620221935572>()) {
            case 257903664:
               var10001 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s19fx0e2uosggf","4hWW9f1KLclVS0atqg5dzhAVEL/WVFatRYhIU9lfEp8=",-8231861211097908636,2008711172569187434,-5547656219247290537,-3918810680665693070>()) {
                  case 1897433621:
                     break label139;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = List.copyOf(var32);
         switch ((int)com.yiyiaddon.m.b.a<"s1biivjjglw9vr","nCAumnv1dL7a4QRXH0qJQ3gERvTOK1l7s/WxAkZCr3w=",4043723191769016664,-8039602820319065513,-580802175221319233,-9068825590524098176>()) {
            case 893981530:
               break;
            default:
               throw null;
         }
      }

      this.bQ = var10001;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s22xoz3k3zwzgu","W9N95xZ2TfHDMDJ5OGaJXdYairCSTKATCfDj4+iAr8U=",-6595471356834785846,-4235577064088190512,-3733748992455877871,3396417395333584073>()) {
            case -1329779507:
               if (var6 != null) {
                  label125:
                  switch ((int)com.yiyiaddon.m.b.a<"s3q562qodjeu30","KrX4f6+arVBoyQsgueiq/oZsjj0AzuHSW6dB/iQ0Z2I=",5893277415203307457,-2049064650701411305,7659998644465137570,837408263155418635>()) {
                     case 808228077:
                        Iterator var36 = var33.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s1x7tjoa7dopv8","0hnrz7dlQ2wXgyzF+J571UZf8GvNvTxQIXx798C1GpE=",-5954801770632166307,-3456052491156365959,-1373070692774600937,-8221111615839554062>()) {
                           case 1360720324:
                              while (var36.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s157vpzlndd9g8","BKPS6xcQIzIiwD6JgbREk1bFdev78iEzkXVw3cAt0nc=",-1441861191999735842,-3927578682112900485,-3759361613003294561,2401517465076086057>()) {
                                    case 210311079:
                                       String var37 = (String)var36.next();
                                       com.yiyiaddon.e.n.i.a var38 = var2.a(var37);
                                       if (var38 == null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sm74ou5s3bmow","4gIvby/sVk5o4dJWEZbzQEzMkSLuoj5HOkJ9+q8K9z4=",6490784508991662497,6799560865478721994,-2565348918961306758,-573697712094170478>()) {
                                             case -2043388430:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1aj4vu5ugip72","6TsMN77K4u/sTaMa8yQUhLiSEKHfoKy+M8Uku70qpQs=",8493536991568700790,3084830767078442525,-643935711336382104,-5177891007803791900>()) {
                                                   case -2001376109:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          ItemStack var39 = var6.a(var38);
                                          if (!var39.isEmpty()) {
                                             label130:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2c07m9bgp3rpj","05b6uDKVuv3tZWMD0ozcjjGlkLTaAjohbyFJ6McagPk=",2210939297137703417,6742984635100470456,6744031173207272120,-3906289187847519412>()) {
                                                case -353232386:
                                                   this.b.a(var38, var39);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2n4k6g6zpbyzs","q2pxT82Ud6q6DwbMiY7Fx8ocKT5ZrbyIv7LU57C6eyM=",-6057640491314523551,-7696348074249023852,-6469608109782691632,-7173436205876533770>()) {
                                                      case 378515125:
                                                         break label130;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s440oq1zfifw8","38370Ct1tn89CJV2N8QHa7dItmPCmta/H3MdW5afSyE=",-2464106718067111765,6319789315877859771,1978999795521690812,-3518235680249484532>()) {
                                             case -1555879177:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                              break label125;
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

   public void f() {
      this.a = com.yiyiaddon.e.n.r.b.c.OBSERVE;
      this.a = null;
      this.J = null;
      this.a = null;
      this.nE = 0;
      this.ac = 0;
      this.nF = 0;
      this.nG = 0;
      this.a.ht();
      this.nI = 0;
      this.nJ = 0;
      this.L = null;
      this.a = null;
      this.a = null;
      this.bV.clear();
      this.V.clear();
      this.dR = false;
      this.a.hr();
      this.b.gv();
      this.b.b(Set.of());
      this.b.f();
      com.yiyiaddon.i.a.a.cD();
      this.f.f();
      this.nK = 0;
      this.c = null;
      this.nL = 0;
      this.dT = false;
      this.nM = 0;
      this.d = null;
      this.e = null;
      this.nN = 0;
      this.dU = false;
      this.dV = false;
      this.ap.clear();
      this.aq.clear();
      this.ar.clear();
      this.as.clear();
      this.at.clear();
      this.ao.clear();
      this.a.hw();
      this.dW = false;
      this.jG = 0;
      this.dX = false;
      this.a.hi();
      this.ag.clear();
      this.ah.clear();
      this.ai.clear();
      this.X.clear();
      this.Y.clear();
      this.Z.clear();
      this.aa.clear();
      this.ab.clear();
      this.ac.clear();
      this.ad.clear();
      this.W.clear();
      this.dS = false;
      this.us = null;
      this.af.clear();
      this.ae.clear();
      this.Q = this.b.o();
      this.a.hB();
      this.au = Map.of();
      this.nW = 0;
      this.nX = 0;
      this.nY = 0;
      this.nD = 0;
   }

   public h a() {
      return this.a;
   }

   boolean z(BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q46k89dt3ljm","EU0oE1wHRB17JAKWjT0gFOoXyCFcHxPRgEjf73g1xxs=",-641029542734583776,-5748727377474938096,7470194583733601966,-7063175276250784051>()) {
            case -1826117927:
               return false;
            default:
               throw null;
         }
      } else {
         Long var2 = this.ao.get(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1y7zfe6loa5gx","JKDkD+hgGoXKaYq7QAxigXzUHj4vr1sSLS/8LmefcGo=",4813396556274470535,3291008388698627011,4067137346031746509,-8066705715447508204>()) {
               case -443693621:
                  return false;
               default:
                  throw null;
            }
         } else if (System.currentTimeMillis() < var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s250qp6h0v7t1d","TyUJzoAWdlDI1tWExMmGo8N2ql/5DEz94JBfeEOe8ag=",-1137433336282253280,-4720432257991484909,3339543435410119391,-4161068925359302311>()) {
               case -1435439284:
                  return true;
               default:
                  throw null;
            }
         } else {
            this.ao.remove(var1);
            return false;
         }
      }
   }

   void u(BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1n0ew9ppvegii","MvH7cediAcUNO2MdQfQlCdWHjzHfV2RhYYirCl0J2qM=",4678589080156928140,-366659436907335595,-943693412445086189,-7540764332657639813>()) {
            case -420654179:
               return;
            default:
               throw null;
         }
      } else {
         this.ao.put(var1, System.currentTimeMillis() + this.lE * 50L * 4L);
      }
   }

   void v(BlockPos var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hdq6e6l7jszy","7TQqx7cWaae4RSvoYGpRG0jx/15eYVHe29HgpxtFRSg=",-3263005084824708930,-7342696554223086675,4962774226827247959,648660984951615842>()) {
            case -964414397:
               this.ao.remove(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2caip2m54y4dx","NSvHMBbdgIjY3+AdFFxWwuT9P4yzglupfhPwp1ykwgM=",-6250243339690691515,5956456214203173899,-6406182085128739275,894255592621899897>()) {
                  case 877049128:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public BlockPos t() {
      return this.a.x();
   }

   public boolean dP() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1y4sf0c3djhc5","2toEzSuWieawQUcr3HnuFVTO7RrSqA9n+eTRqc5s1Zo=",-2804445178651512439,-2936940718274296181,1259771416346299131,-8880380841553599409>()) {
            case -1830964453:
               switch ((int)com.yiyiaddon.m.b.a<"s2h3vkc2t4a1uu","JbRWJNetU4LPxfHsVSAnxSWntOl53OTPkK1Sy0DPyF4=",-8044197643927949550,-6663933226835712752,-5978374801868172901,4753214975779289194>()) {
                  case -2136809408:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2bt984h4k4d56","pnzs52YQG8AVNmSBEpR1d+BQHa3BQ/2JMP/7GOc+/LY=",458492423288081650,-8854550247570071945,-9206984640421727559,-1992907032298159667>()) {
            case 813314443:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean dQ() {
      if (this.a != h.RESTOCK) {
         switch ((int)com.yiyiaddon.m.b.a<"s2w35snggyye64","Y+N5JVxzk4u8B6CCUQJIPzFYImXjzG7mcCrgG01wdXw=",2154378855620252122,4152032819414086083,-956815231579637132,4171292519519703599>()) {
            case -1875929733:
               if (this.a != h.UNLOAD) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s3bkmu3bhncntr","8Ef+v9BX+aWD1Nasq7kwI9HvmFNHFNdIHWCMwNI0o5s=",748605476590257581,6499763688013859286,6115619184518600725,7971876051993674068>()) {
                     case -2122528461:
                        if (this.a != h.SEED_RETURN) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3fqxvp6mne1uu","ELsdcuKY01hALobAkHbWa3Ukbw9+UrOOhSj5P88Wjug=",6728637630984266222,3935339168440594163,6268085978948484951,948938757379059243>()) {
                              case 1913318615:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2wiec9hw1pvyz","Ptlw7XrEQju+U+oNhq6VZHeA5oft3wzS1JcuLK5qk88=",-3178322854474594900,3092874296792015877,4216733941522303928,-5653554891917253691>()) {
                           case -132304155:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3onqdig1h26oy","yVKz4yFLbdCTQL+ucVwWGsI/2AQc3e3KQgLgkvC6KPY=",-1767121264473437963,7851857464339492827,8378963078781614530,-7965443024795846994>()) {
         case 1267090093:
            return true;
         default:
            throw null;
      }
   }

   public void gZ() {
      if (!this.dQ()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bmr172uhj25w","kuzvAxWYwRjfIhgtRihUnwVBq7ixP5sRhWiYyof6Mvo=",-5723515847954366581,357136024410210009,6304802282217384308,-2787076738340071733>()) {
            case 412356779:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.i.a.a.cD();
         this.f.f();
         this.b.gv();
         this.b.b(Set.of());
         this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
      }
   }

   public void ae() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjbs0pgkgm5y1","CbfGxU+Rm2jgViUl7EWV11ZBSTdNVpOKPekQXZnXyVc=",-1843019833284415814,-650330669594487256,1056000593548765117,-2953809036342740308>()) {
            case 1445242371:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1mp7vnfg62rkc","ma/tzQZLE7qozrJs7BIwt1rE2rEmKFzOCdv3Du3i8qc=",7364115239558379163,5611524918550301052,6543082159063840408,-3712711496275205787>()) {
                     case 2082788121:
                        if (this.d != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s22vnu6pasfcut","9em/22PqPBSvbN8qR09W9CcnozvX8dHwpU2TJD3CDxI=",-1926332586762138220,8328989687871957554,2560027026410172349,-5236477529906774752>()) {
                              case 241675140:
                                 if (this.a != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3lkqy969nljgs","qaI0UMTcIlG6v373N8cYHuM34wICtcbASamqM6jcSL4=",8864353632924825924,-4558256555792084800,-8005433984382542909,1939777523623367613>()) {
                                       case 1228104436:
                                          if (this.a != null) {
                                             if (this.d == null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"su2ipxicy4pdd","1Xbhg5/abbzqXFNhp49Ckw2nIWb21ysqi51D1nPjzUs=",3899360251728975044,-855308464189806753,2114057458915739730,-5361461668242163728>()) {
                                                   case 317273818:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (++this.nG >= 20) {
                                                label131:
                                                switch ((int)com.yiyiaddon.m.b.a<"sldei5dqvp7hs","5loum5iA3ekwQnUzxbSxaaA3m0UTQDMq0kgX3VojxBY=",-4679559237485125759,-3140750493850967023,-8975005071395635010,-8765063092402898073>()) {
                                                   case -1918642654:
                                                      this.nG = 0;
                                                      this.a.hs();
                                                      switch ((int)com.yiyiaddon.m.b.a<"sar3fbc4ojphy","AX1fJOJJ4iiJ8zA2ZrTZ7+POK4lADPdy2Vlfnkq7BwE=",2103078910122618453,55525578216922018,-8236514175679151255,-4649804618124381941>()) {
                                                         case -280754593:
                                                            break label131;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.nK > 0) {
                                                label127:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2eg0sy3hru024","e7V0PAjwFpHjMY8w2mvvqscggHnWuaY1+7FN+np/X4c=",416523880404816010,-229079483618820820,-9179358882158857903,2792260160191992902>()) {
                                                   case 1929383541:
                                                      this.nK--;
                                                      switch ((int)com.yiyiaddon.m.b.a<"shz1je625q8d3","kcgoYS5J9Q6FhCGEgxLDnh6Ztzxg18uHlPJNH6wg/so=",-5776997503490450996,-242596509404702995,635137692578403557,-5882095136938285653>()) {
                                                         case -688953693:
                                                            break label127;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             long var1 = this.b.o();
                                             if (this.Q != var1) {
                                                label123:
                                                switch ((int)com.yiyiaddon.m.b.a<"s17m0uln6kil5z","bRQt6sof8eJST7YOZ2Wx7SJDRaNBTeXmtUSMeZRQ5Eg=",-7146793310219408040,8913319334569726827,279600955447854486,-307782081894144973>()) {
                                                   case -948298460:
                                                      boolean var3;
                                                      boolean var10000;
                                                      label164: {
                                                         this.Q = var1;
                                                         var3 = this.a.em();
                                                         if (this.a != h.PLANT) {
                                                            label121:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s164hmb52pmj0j","aHO2OY3WQPHe+dGPyNwFRM8E7XGjNnxTsX6kGj78+90=",-2197246823879558062,-576679857586005367,-3475470931982150265,4545091825691732487>()) {
                                                               case 700470002:
                                                                  if (this.a != h.FERTILIZE) {
                                                                     var10000 = false;
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3w3nn13mokpc5","7A0Tvwzi9YCNzmU2mSfclI/+kaMiMoM369DZ4I2uZcw=",-3187488744727726225,5606364350348154036,2194757398976210063,8821235831787786180>()) {
                                                                        case 1457493256:
                                                                           break label164;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  switch ((int)com.yiyiaddon.m.b.a<"s24z9ua6tp0l9h","y/U8XghxY4TW0Da9l2AHn8xb2q6LwaLA6L+tNM6c7Xw=",8296484269620176819,-916339690666402722,-3140788279068661992,8213706134224110667>()) {
                                                                     case -937206729:
                                                                        break label121;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         var10000 = true;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2rfwb6wcbwa7m","k4FHl8GzlacSRngNdxj6dEf3JiOIY5I4BPDIe80c5hs=",-1181173758192654283,3147724528660305405,-3604689718240700734,-5565203870844258638>()) {
                                                            case -294206375:
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      boolean var4;
                                                      label157: {
                                                         var4 = var10000;
                                                         if (this.a != null) {
                                                            label113:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s367wseb5s74sn","TXzVZk65qGilXRanAGH4oVb6B3D3wJZjvT29gKrxPmk=",5296920794677649609,-6579523665674244108,8311385247860709881,3396048279781313576>()) {
                                                               case -1462381761:
                                                                  if (this.a != h.RETURN_CENTER) {
                                                                     var10000 = false;
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1sy59g03u6faz","okpBVzcRSIlTGMxQ3vGSpgt95fffwyjiqgSApttAoV8=",-4476008827001188687,6629927549810350049,447005588045597872,6402615461858606727>()) {
                                                                        case -1559803729:
                                                                           break label157;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  switch ((int)com.yiyiaddon.m.b.a<"s17nsx0g32dths","79v1bIkaBWUZarevmI+wJp1JnItL/NosvSCegvPAIzg=",-5551784338514882722,-1807110858871372693,-8584864560320793366,-7712259123049893396>()) {
                                                                     case -1892948281:
                                                                        break label113;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         var10000 = true;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s35e3ylper0n4y","vbFFE16gjwcjXzMYWP9l8wBq0+i7iSUFbRfmZ82Z5IU=",5236036113357313160,-6526093137749585913,5312219148097075364,-5586569884183264689>()) {
                                                            case -1227129618:
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      boolean var5 = var10000;
                                                      if (!var4) {
                                                         label105:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2uwxnd8tom3xt","Y/y+XkZI1LoYKaeXFhd6OhFfkU5KO1wGJIn5FnkUHTw=",3862290360051302805,-4629976294424536154,-2926950073954724005,-3143984714530386717>()) {
                                                            case -551550955:
                                                               if (!var3) {
                                                                  break label123;
                                                               }

                                                               switch ((int)com.yiyiaddon.m.b.a<"s3lgmzzv6tuord","WF4Tj5TckCli4DfBk4elhynNH3wFiAs9TETPaLIylUM=",-973452467081988668,-7946579736228045362,-2358085125162455996,-8759772648382786833>()) {
                                                                  case 1999048458:
                                                                     if (!var5) {
                                                                        break label123;
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3hoxmw063bacm","E6Is4Ibi8wi2bLFZ752eZ+oh3IQymiYVgms++MnX0e8=",-1768668795905916608,2261855282727555006,-8706394202874022770,-6462362123729123718>()) {
                                                                        case -324260322:
                                                                           break label105;
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

                                                      this.b.gv();
                                                      this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s14ahj2s0d8vv6","bARksQKtrwMN+4f78KZq3FDWG3jJfTytqWza3M7sh/Y=",-6572427848826997100,-1738245930576391989,514158681754024573,-5991631419380509540>()) {
                                                         case 1911512761:
                                                            break label123;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.a.gX();
                                             this.a.hy();
                                             switch (this.a) {
                                                case OBSERVE:
                                                   this.ha();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3rusc4xxtjnmd","3mQaQ0iChRWnjQ0jQUFRSS7gwmajwgAZjKCqsL0J1f8=",-7133187789234842027,-3086123964344510583,-3617276709582202751,7665654728963063152>()) {
                                                      case -1748730827:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                case DECIDE:
                                                   this.hc();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s11vg6so2xzmpw","SEAeIjhIwitUK2jiC5uCiVQbvkOJNEL49OgHpU739tE=",6207380440665820049,5841648662579525934,2821104026446386521,225314319190735369>()) {
                                                      case 241024555:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                case NAVIGATE:
                                                   this.hd();
                                                   switch ((int)com.yiyiaddon.m.b.a<"suzssthi8gvik","giKykmPB4n4FiACjQshWA0QZ2VU48S6diPiO2MD547Q=",-7591068122374954657,4237135053868025431,-6053314897837037057,-8109310926368725231>()) {
                                                      case 284589312:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                case INTERACT:
                                                   this.he();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s6lbyxb8v90ix","W+6AoO3umA6ExE7LNCra5LJ9XyY+obkdvM0vJ+Ru5mA=",-2066996582532307634,-4627710344449161891,-6064174383518456010,2233691967700598288>()) {
                                                      case 1082428692:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                case VERIFY:
                                                   this.hf();
                                                   switch ((int)com.yiyiaddon.m.b.a<"soyvxrupkb9rn","G8MDd845/7K3JiILsnYQDSsohlvqruY/MXBZG/ZzbqA=",322984631558174653,-8442839938936040737,-8845257334137524129,-8168585831540798508>()) {
                                                      case -1120698158:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                case REPLAN:
                                                   this.hg();
                                                   switch ((int)com.yiyiaddon.m.b.a<"sba6ci9vtcoyr","DwX6F4Pp19JCGLJGBjP2WX9j6Chwm1SPMa4dxyv6jFI=",6170112317391453222,-3564210907527607715,-8628025554914872539,7864246975100516819>()) {
                                                      case 1443864096:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                             }

                                             return;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s3hfcd4azjj2uj","9+kyOIT3pVSKXKIFkAhZmewdsfDnZEdiVZSEARLNso8=",4105467972055172647,-4219661063473196743,5730067960534746113,-8070388213387582323>()) {
                                             case 891623861:
                                                return;
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
   }

   private void b(Iterable<com.yiyiaddon.e.n.m.a.a> var1) {
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2l29uqaewiyse","RLbNKV1+bMD4NAu00rmicPuyHxZvAQ+kepSgu3M0w8Y=",2219972162761159619,-3214349644102013697,-4067623278323886018,8233530124500458545>()) {
         case 2033482428:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3cfzgown51vjn","H8ghhqIJbMGAr3J6+ovWnH983VgO1Rn6mnJ4NzT6qlM=",2202402771731323772,3152014578940537826,-4799140743552007646,5696584968017868795>()) {
                  case -891759069:
                     com.yiyiaddon.e.n.m.a.a var3 = (com.yiyiaddon.e.n.m.a.a)var2.next();
                     if (var3 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1rdx2pdohzuuu","Ppt81LYEKfSnIBWOBp9Lj4hDIMvlp2sIrGhjq7tN278=",6804838364168289070,7918258350413714144,-8896677757735549196,-4146368043239655496>()) {
                           case 2016712589:
                              if (var3.c() == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2te6quu1c3phy","dNO25wXnEgR9LFjvCfAXMkKBSuoRClwRQI7HoLDADjU=",7670923903112416222,3191488333313248621,-1596280785723188297,-2885617832619579716>()) {
                                    case 2052766226:
                                       switch ((int)com.yiyiaddon.m.b.a<"s312r0qorzr0g0","Hv65503pxLDHV2BPiUoI/DSqPy8fgLinIyvtIiGa5X4=",-8605651137277747382,1881800091769741109,5506261202258275304,-7595335600659134266>()) {
                                          case 1467409405:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 com.yiyiaddon.e.n.j.a.a(var3.c().dk(), var3.a());
                                 switch ((int)com.yiyiaddon.m.b.a<"sl0v1jmsmydhn","DS61JF1ltoMGgNbFYt1u37LdV91w2nXmKK7vFN4041I=",-7407065148791669344,-4469649154455795966,182963432140513253,7038475344614743251>()) {
                                    case 1144427568:
                                       continue;
                                    default:
                                       throw null;
                                 }
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

            return;
         default:
            throw null;
      }
   }

   private void ha() {
      if (!this.b.M()) {
         label34:
         switch ((int)com.yiyiaddon.m.b.a<"s2zmo7otpz4c18","FrAcEY3FBlN9Q2IR7DZIBdAld+ciIdxH67vwlL5Tg4k=",6077821602343800548,4718853870986671645,-5540061382637382980,6873565350036715822>()) {
            case -941377997:
               BlockPos var1 = this.a.A();
               BlockPos var2 = this.a.B();
               if (var1 == null) {
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3s9qx07ef47qs","bCimlgUaqS6hrcs6JNZ+Pq3iOnQvr3v/Y/GMP3CL9kY=",-7604818022391274191,7681166064153410401,-6420822821309851020,1438163613752443045>()) {
                  case -1400700493:
                     if (var2 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s24teodkfchv3h","gDtWzxS38PMYw9+1fuOY5ayFKtLaC9XpgSXngccY8RA=",-4001662391513459117,34852793604694159,6424612593123270680,-4645395435495572642>()) {
                           case 1160447286:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.b.c(var1, var2);
                     this.bV.clear();
                     switch ((int)com.yiyiaddon.m.b.a<"s2f9949x7t4pld","WGhhwb7wJH81NbAWnsRmMhW96L9nUghoFEs7lH1B3po=",2616214525621717234,-3009492351121383599,-8014278897791293982,-638087358988611757>()) {
                        case -529431323:
                           break label34;
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

      List var3 = this.b.a(this.a, this.lB);
      this.bV.addAll(var3);
      this.b(var3);
      if (this.b.aJ()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uytg8ka1vb6t","UPD1roaBIskY/E4OsdUbdxva5Y4xvgTm6M7HTF23H58=",2488465109051369888,3106902395361251651,2695055536851836124,1034717042011175234>()) {
            case 769225319:
               this.a.hx();
               this.hb();
               this.a = com.yiyiaddon.e.n.r.b.c.DECIDE;
               switch ((int)com.yiyiaddon.m.b.a<"sv9zx3t8f18cl","c1ngnAH3oDmKMPKNqGYohPJyouN1w54/KC1mubcML64=",3498513024762522763,7718330993514819338,-3817085257662034819,6704753348959842529>()) {
                  case 1250875275:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void hb() {
      LinkedHashMap var1 = new LinkedHashMap();
      int var2 = 0;
      int var3 = 0;
      Iterator var4 = this.bV.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sadjvyvsqoux3","uvks7XESdONU/P1yPgU5maBwM9H/+uV30D2DurljcPU=",-8306228849030979480,6763129763345855119,419800771278855427,365850957486630567>()) {
         case -1938859916:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2sa4oa8ev3e3q","/IEPlMqO45eYw/xOxsKlzRi2FL+HnsHBrf4ObImP5Zo=",8719806422250937444,8570082791213549590,3291205573826953534,6904292036685899465>()) {
                  case 1033978180:
                     com.yiyiaddon.e.n.m.a.a var5 = (com.yiyiaddon.e.n.m.a.a)var4.next();
                     var3++;
                     a(var1, var5);
                     if (this.a.j(var5)) {
                        label45:
                        switch ((int)com.yiyiaddon.m.b.a<"s29ds5qg3pzkg2","q/1Dthh5LT8I1sBtpdl35Iu1byeKmThfVgOa+pbfe+g=",-4798101737759733702,6090366712830610805,-2123169912579848739,-4581342323415137076>()) {
                           case -1620633777:
                              var2++;
                              switch ((int)com.yiyiaddon.m.b.a<"s1yoihzsgg4a3i","CxwnBxCxERsVE8tur3TGD/7XMw3WfBJGXf6aWGL4vjg=",-4553359809304541716,-7909730286711300829,8524940261234822172,-2491525282336869115>()) {
                                 case 137479875:
                                    break label45;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2iwn30gfeedgl","BLLTOpKwI/yqHzfghhP5VqXcJeKWKS4J9qsiS4pnnbI=",-4718571583584236342,1767182471256555590,-3272454539468329865,6848753283640848471>()) {
                        case 1918215579:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            String var6 = "" + this.bz + var1 + var2 + var3;
            if (var6.equals(this.uy)) {
               switch ((int)com.yiyiaddon.m.b.a<"sopsxpii91a9d","HtVwgx1YBB7kovZ68YEQNexFAC7CP6Pq++ADMedL+3I=",5761335404014580917,836866716709597583,999248844486712502,246512747818728588>()) {
                  case 417520596:
                     return;
                  default:
                     throw null;
               }
            } else {
               this.uy = var6;
               if (var2 == 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s254wdqo8qgs48","tihjnm4ubLEVXV52p+aIXK98urESVYD8BphtNo2UHrA=",-1993354341924956898,5458720228015169986,-6500362501188141703,-2614589156957957357>()) {
                     case 1555681796:
                        if (var3 > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s39ke5aomapbmw","6pk0wlJRrdrfTZo33pOZek3eNPNRfUyw85Jd7j1R430=",2526034689430702582,-6340450105262306483,1492850760317102853,-3773265440729879540>()) {
                              case 36166763:
                                 this.d(var1);
                                 switch ((int)com.yiyiaddon.m.b.a<"s33374dp5btufb","4ulum5rk5qsMZ3X3Fg8quc3d5ePdxcw4CY3Wl6pgCks=",4266674617048378327,1172572460489416602,5965764055489290563,-3987291506316897216>()) {
                                    case 1834350938:
                                       return;
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

               return;
            }
         default:
            throw null;
      }
   }

   private void d(Map<String, Integer> var1) {
      this.c
         .d(
            (String)com.yiyiaddon.m.b.a<"s3172irtyj9mz","77u02XX2wcbUgDTPOwktF+qr3D3W3NEuN5AR6tzh0wOOVKM+GnchYak3VlAniIkeUWJo3Td0QY40TsI7vBtzAXxR",501868777833938272,7540364029607417588,-4409517750381814329,-5060207236882414126>(),
            (String)com.yiyiaddon.m.b.a<"s1ztqc8njarde1","9XruECR7EinUMUrXqdUs09/JLMScNbskVxB7xCdcHmbqRwZqJVlZO2GtMUSw1cAD0SlfKCJnEnu/gA==",1886178624356867442,6867237373153322915,-7662308669554065078,-7677947948999862578>(),
            c(var1) + this.eA()
         );
   }

   private void hc() {
      boolean var1 = this.bV.stream().anyMatch(this.a::c);
      this.X.clear();
      if (this.g != null) {
         label208:
         switch ((int)com.yiyiaddon.m.b.a<"s3u030j75jhgfr","YVUJPBToV7grau11r9oqOKzLjexaXMiHGwr4hSlQOwI=",-2904490714291477007,-5060568063607213128,5038744911873192471,929940649770461587>()) {
            case 1991390680:
               List var2 = this.a.bh();
               this.V.clear();
               Iterator var3 = var2.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s37gsb6o24ja4z","z8ldrgryk6n6cZgUBhDkNcuQTiLpZkHlRqhqo6Dl1p0=",4578514878851956444,-7360831772950447509,-3934162670012089937,-8187090481620970617>()) {
                  case 436505264:
                     while (var3.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1f0p38oap6met","NCdDUFAmyQ41eGab5hIAZPV/Fz3attaqw+yQy+KdTSU=",1671589100945009906,-8770617793542762277,-1602960116819376365,3892694363014901611>()) {
                           case -1389474679:
                              f.a var4 = (f.a)var3.next();
                              this.V.add(var4.a());
                              switch ((int)com.yiyiaddon.m.b.a<"s1e86w1xfgviec","sFp3pRtkR9kPadh429s6u/KEDgn+6qWJTojiCg/rljc=",1984886617505691400,9159016341539043676,-7436213114694389325,2349256848324705835>()) {
                                 case 885670930:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (this.dB) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1jha676vp8sz6","UAVSzRWmeFYSHdEgUj5nSACAVupL6QZoWwlZYGyO434=",7697260337762500185,-1183883820728100570,8637891149085811846,-3010708712480104496>()) {
                           case 1395199793:
                              if (this.dR) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1nrei4ny0iwn0","wakiLdtpJQJhpp+ZJo4XnsgWgRnFfFmSgerSa/XGEqw=",4787774482431455508,-291626997851137437,6264476473233303091,-8295240941255241296>()) {
                                    case -1123019648:
                                       this.dR = false;
                                       if (this.f != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1axzk5tbxzky8","7eUprzWCY6ClMakOdAhRjq0pwSfUtcbpidiv2iM0css=",-723856172196991054,5441267137201246204,-4339649492030678049,8562515013408034276>()) {
                                             case -1390052195:
                                                this.f.run();
                                                switch ((int)com.yiyiaddon.m.b.a<"sms4yn00u4dvq","Sb8LTpqSMveokiDXhmE4rJ6dY5mk9Gha+Q9xNc0WiLY=",-1955885859572926889,-6150512350720433704,3545313751545670349,-3001977451946657294>()) {
                                                   case 142329011:
                                                      break label208;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break label208;
                                    default:
                                       throw null;
                                 }
                              }
                              break label208;
                           default:
                              throw null;
                        }
                     } else {
                        if (!var2.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2bpu5f8cxwr52","nj7SVyR5lTSBnXuu+SZt8kKNHaCbnu45MUr5fXDwvKo=",7289263000647177978,8005690901822983861,215001325518860080,4478593588312824827>()) {
                              case 355497549:
                                 if (!this.dR) {
                                    label123:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2n7dzo7ywydan","J6bxj5Zm+PLOdGzLWEoqZYPSoIvBSTH5xmI9aJg32DI=",2615540273745950033,2055355369748097600,-4420855068829411481,1632834924528648162>()) {
                                       case 464295291:
                                          this.dR = true;
                                          this.g.accept(this.a.bi());
                                          switch ((int)com.yiyiaddon.m.b.a<"ssgftq3t8mbez","K+/z0hYlKqss3NtIggeYDz428QHuv7C08920bYCwEsk=",-5681703489568047760,-8129891851597985248,4078845115571282327,180285341117918230>()) {
                                             case 229389766:
                                                break label123;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.hh();
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        if (this.dR) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3uq326qpbz3m5","FFvQTvsv7r+L6qImQAL3Ec2TQOeKKgwhaYnpTZB9I98=",-4290755731662929190,7538298689493901739,-8010806711340455264,-8754962453232955516>()) {
                              case 441500265:
                                 this.dR = false;
                                 if (this.f != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s13dad9wv8kxwa","bHcv1md2NXokLVhwNtdhuCg9PapR9HYqKWYY3aGKTbE=",-8315497515432830212,-4856113652921136377,-4145529838639135238,5256234618461141496>()) {
                                       case 723423981:
                                          this.f.run();
                                          switch ((int)com.yiyiaddon.m.b.a<"sd3hc9rd4ml46","qEzxAKI5cStS9reeZKLJzIDGq6dGUn2XVmUhQBhZB28=",-4788872456399950850,218582805633164679,6979930609106180575,-1203863257840071391>()) {
                                             case 1856847634:
                                                break label208;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break label208;
                              default:
                                 throw null;
                           }
                        }
                        break label208;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.bQ.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2822ja6kpwtmh","JAjtAOBj7X+c/W7umMFm9QRhY/QFXcNAdVGqCP89KCU=",-1302511326523330605,-9082839809125074530,-2808185445264039964,3074300543911186824>()) {
            case -786285951:
               if (!this.dS) {
                  label129:
                  switch ((int)com.yiyiaddon.m.b.a<"sigurunxgxs45","E0KXgVMXuQsFtKCKQjcehs6Pm3H+UWjDB2GCTa4QMYA=",-4358751311703137356,2362313389625015167,2170008517231766237,6713546503366594647>()) {
                     case 636438247:
                        this.dS = true;
                        this.c
                           .c(
                              (String)com.yiyiaddon.m.b.a<"s2uxx2orhcbwzz","eC1ZI1o84vpI6OLF7klE3sOHdzpWY/TDfRDUkqv3QEQsc64Txe6vvLxzrHBP0/21LM2b9Q==",-2266597964983382186,1338806576564037152,3067454783976396615,-2726275817065458814>(),
                              (String)com.yiyiaddon.m.b.a<"ssn2fpt024aki","8N58x7VrAKwgfbq3UmcToJrj5LPEtbOEbjkEycSolN8exw5Q6JOj4dEw",7595402996981692883,-8211031216994427504,6141649541637322886,4357630380176934265>(),
                              (String)com.yiyiaddon.m.b.a<"s1th3t2ml256qq","CmFBJcfQbrq4QmIf+5y7Pzag6QzieC79jdX8wrjv1BTJIgYQmJ4cyInZDrSFr6KNLtR8X4oU745/wXSax0OMmMBhB2H66ujAm9dsuanp7hpREg==",3151684851072490329,-1268247306383424015,9001737868854199360,7327751748533029732>()
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s2vurof9svd6u8","mDV6PCeyGCWrr2XO7m+JuGU1X4gMafQaY/BRq+grUxs=",-1958105063030654201,1380513484718428512,-8800324183539321734,-4557545059212135577>()) {
                           case -99751541:
                              break label129;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.hh();
               return;
            default:
               throw null;
         }
      } else if (this.a.es()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d6tne3nxoj71","SfanDVGoALaRUY5R8tvR9qkeqsAYEEYCmfqZZkoKL1A=",-2672146009490565967,5048399340311193521,-6801477109369990275,6517085901441313613>()) {
            case 671358656:
               this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
               return;
            default:
               throw null;
         }
      } else if (this.a.dU()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ilfb8qzn9zt8","KoIRlfM488K6dLDErp/4XhikhdQf7qdn1jGBp5UGbts=",437819377841093273,1807476641547213891,-7713024971952659628,-4709194978562615431>()) {
            case -1592693685:
               this.a = h.COLLECT;
               this.J = null;
               this.a = null;
               this.ac = 0;
               this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
               this.a.az(h.COLLECT.af());
               return;
            default:
               throw null;
         }
      } else {
         if (!this.bV.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"sdo1i8hy36mq9","KCK5IObOhbQtOvzD3HOBoxakOXaNogQNk0EtIXMEtrA=",7774153374597720929,4318327180925618376,-2157644148030947356,-4536180415823444529>()) {
               case -759390650:
                  this.bV.sort(Comparator.comparingInt(this.a::a));
                  if (this.a.en()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s21kh290sd3qel","Cmbds9Zktzg/+65EkLPV0oXKZLvrVpJKU5mpm2/WvwI=",8531546756086578570,7042260717324084296,2191863094947022440,-7557222775075200353>()) {
                        case -585871396:
                           this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (this.a.c(false)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sxzvplvnumsvg","7Vpt0QmynKCnmOkezRNqlCoEHK5Nt6GhMMvWaMavqiY=",9040691541650113096,6044235236937632589,-731150843282867749,4514658733222583437>()) {
                        case 1965308846:
                           this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
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

         if (this.a.c(true)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3att7et1q94fv","uoL5lRYFEOpxub3+tBzx9EzHzYBdSUwSIo3Aw5a7SFg=",-5970660443559903626,-6458050408356517878,8342895714865643862,1967874910661277933>()) {
               case 399786034:
                  this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                  return;
               default:
                  throw null;
            }
         } else if (this.a.eu()) {
            switch ((int)com.yiyiaddon.m.b.a<"s7tnpou2cn5mc","EtzcGSfS2rznhj/cGh8CBqWSP8nGk1Tj0Ix9ot3EvWc=",2566256494466529736,-4546265475911108970,-4754030073600002794,5707949809368991265>()) {
               case 2097460042:
                  this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                  return;
               default:
                  throw null;
            }
         } else {
            this.a = null;
            this.J = null;
            if (this.dz) {
               switch ((int)com.yiyiaddon.m.b.a<"s28xni30s79pgc","cs9CsrFi/91jTzEQdjAwJF1jBlB6yC+DGY2jdiC6q+E=",-455257480689050056,7080759629663155429,1822770709562530113,-8921615003162313383>()) {
                  case -1382092005:
                     if (this.a.ep()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1h5wsgpmtw4do","FnW64+Bh1inQgpGyNC8/rMTOD4QoBgwkfeGVWnhGDZM=",-7897715491182416864,6865308435555626983,-1811769358260831527,1227733708721905338>()) {
                           case -1306437767:
                              if (this.a.eq()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sxievbnru9px1","pitqmgWA5Qhhpq3ZCtmZnvAPeZX2kaklYR3vCadVsVo=",2639612884012882754,2266140118275436580,1025668212637332714,-7062302137955271182>()) {
                                    case 1707063136:
                                       this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
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
                     break;
                  default:
                     throw null;
               }
            }

            if (this.a.et()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3buhudfp1mpjs","ze6kdpjDtyUdWzVW2Xd2kXWyIOifqVSknonTGlCtO+I=",6312661540495699125,-5296018783889459596,4680845005026271513,-8327741554517736797>()) {
                  case 935915937:
                     this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                     return;
                  default:
                     throw null;
               }
            } else if (this.a.ev()) {
               switch ((int)com.yiyiaddon.m.b.a<"shghy0rb8yfx7","ETGvziaNZ5/XbsgG5hoGWRbB8uFt2I1+jvTt0t1FUW4=",-5599856321312823969,-1003269370098723894,-3853690907365758470,548343280348401244>()) {
                  case 1605760746:
                     this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                     return;
                  default:
                     throw null;
               }
            } else {
               if (var1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s20kcvtq0c85kv","5gOfP8M/pIhfVhurPVgy3ax3zhe7VbZbsuLDY17B1pw=",-4824701222997799309,-3570106532201602156,3974617727115968674,780055492234831325>()) {
                     case 525877231:
                        if (!this.dX) {
                           label156:
                           switch ((int)com.yiyiaddon.m.b.a<"sdazq03sh23gl","Ifz/4JE6czDcMw8OHFLPJ0V8IgwORzBUi/i0TJkEZWM=",661245959979360243,-8842299309618842582,-5692743060344050365,-811680276698143460>()) {
                              case 1934933980:
                                 this.dX = true;
                                 this.c
                                    .c(
                                       (String)com.yiyiaddon.m.b.a<"s2bh2dqkaoeqyl","2aNuKXUsZFr/V7YJNIAdrbE6f4+0ofmawaX8rYJMvKCbvlOiBbp0dFxyyzXffBrpNyQ=",-3799116119522830448,-7534272281065193303,-6135715046602909600,2949716544823979963>(),
                                       (String)com.yiyiaddon.m.b.a<"s17i7i9y6o6nk7","RqCSuQT8OlQXTJ5OCfsOOa+/Wv0IqnuRtA2+gGr87AJOWofNmXHziw==",-5166501522788488917,145565511707624763,-8414021863606191512,-449927288420511893>(),
                                       (String)com.yiyiaddon.m.b.a<"s186lk95qbozik","1k+aJqcfIEECpy9JXKSqm19d2FODUpEqqGeiZCaDKCqW/lBHSGuXcTiQbd1xhA==",-3405423638894601608,8967675021567817476,-4115539248252359727,7626305370819254532>()
                                    );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1shvpxlo6zr0u","uQyYz5FLVVFcy7EsDCRbTaoD0FGquYqKrpdxefTt16o=",-1964014896457034992,-8903473594855439868,-4846797260869751079,5399927607835539792>()) {
                                    case 1496573827:
                                       break label156;
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

               if (!this.X.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"se07cmt5ydeoc","1X0Vv1QThyV+kq40uc9MHvBh7Cjzn4vwFRyM4ahZOro=",-2689809408239482930,1945707853034286140,-5354621816927151345,-4586606477030763180>()) {
                     case 325670306:
                        if (this.a.eo()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ucohinadfpla","QNhEWG1+EZRbftMu6wqK091ulVFlTfaFC/B2tQcTEPM=",5844146481484601019,-6123330620194978562,-8586208762500729235,-2944785238019285352>()) {
                              case -694668800:
                                 String var5 = this.a.eC();
                                 if (!var5.equals(this.us)) {
                                    label147:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1h6oxkskt77a8","XE8xXFb1xT0eyjNbDDSX7hOHXxIdUx5rRavODhQAnOo=",-7168803701527929286,21151285939082908,2302016295128105572,8599804893647336617>()) {
                                       case -946573657:
                                          this.us = var5;
                                          this.c.p(this.mS + var5, var5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2etmlbmq09w4m","LbvYol+x8m2DIH5NI2LY6Pm8QfVNeGmIeRjr+37Ku1c=",-8224778803544541261,3832587416966360826,-6790930971500119131,-8083981700574770360>()) {
                                             case 693518081:
                                                break label147;
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

               if (this.a.er()) {
                  switch ((int)com.yiyiaddon.m.b.a<"stc03p3jp2ahf","wkieRKtEcaLva6IUHikt//ovGDqY4fcydyOLBsWKmBM=",1678260508204988060,-8526258741605855490,8372985204675040280,1768414006121506820>()) {
                     case -690313804:
                        this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                        return;
                     default:
                        throw null;
                  }
               } else {
                  this.hh();
               }
            }
         }
      }
   }

   private void hd() {
      BlockPos var1 = this.a.x();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s322871m67qkhb","zFZI3/U91NWkjGlGkJlk/c/0N4x/3RrpqeQxn7SjWYo=",4815533584742715976,3597148120912601843,-1510040180964315238,3646777777742552326>()) {
            case 2133951127:
               this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
               return;
            default:
               throw null;
         }
      } else {
         if (this.a == h.COLLECT) {
            label133:
            switch ((int)com.yiyiaddon.m.b.a<"s1a9hwb4bwyrx9","iKYo4CXH74xXq/qAjVh0l2me5khqJzo3NS+CXTERXAE=",-3956035202336078528,-7178922294317888509,1257424442323503966,-4002266639313705638>()) {
               case 801901399:
                  ItemEntity var2 = this.a.a();
                  if (var2 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1bveom5fhs4e2","0mj/WwM9gCkAxBEO9HTZIe3sRX/iAhcRBY90A2eX+xU=",684505435256828325,8720190913446106012,4659469996324474231,-2339909453891729945>()) {
                        case 1061339524:
                           this.a.hi();
                           this.b.gv();
                           this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
                           return;
                        default:
                           throw null;
                     }
                  }

                  Minecraft var3 = Minecraft.getInstance();
                  if (var3.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2uhinj3s2g431","7S712iHZVF7XS3JaKxWhnwkX5G6w+TWSofIASpq9aiQ=",7639460759588971156,794917433825131417,-1363230483833864275,-4191372343881194195>()) {
                        case 131883050:
                           if (this.a.b(var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1ir0ki1d1bsi4","CzlrhxSr8GgSa8MrCnR1Dwmx8UIhJSqgXRq9Wzi7/TY=",-2560360712606590677,4258588854047922067,9083690947079698830,-4801218175305772494>()) {
                                 case 1340075596:
                                    this.a.hi();
                                    this.b.gv();
                                    this.a.hw();
                                    this.nE = 0;
                                    this.a = com.yiyiaddon.e.n.r.b.c.INTERACT;
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

                  if (var3.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s17kntlhbtxkpp","lHtcudLd8ZHc1bFyWKpk9H0W4hKyhEm6LC3gsPessaQ=",-2095212666484830022,5731553890581532372,7503139807369350910,4147817167544495530>()) {
                        case 1858464798:
                           if (var3.player.distanceToSqr(var2) <= 4.0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2j8wixfbsp7ui","vg8xb9pHxbzNV+OM6vD5YyOOzzKwQGcdLdbDzdaLULc=",1788282834731904895,433160474974746829,6700856082464098445,412473637821049850>()) {
                                 case 1056040829:
                                    if (!this.b.cX()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2enp12l0xok02","1HgdPX1xdoxpxVybz3x1zvanIg8ZHEi43rPo+b/u5pE=",1890935113940001811,8295593453077889182,-8839267824283484770,-1459364091857974418>()) {
                                          case -716242272:
                                             if (this.a.c(var2)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2indht8d27k67","1sifO2+qSFPA6e5zQEFiVuLwocgSWbeq2UA3zwdgUUE=",4329881524420485019,-4409445609808657703,1480306416965877556,-5619520514266470618>()) {
                                                   case 200115409:
                                                      this.a.y(var1);
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

                  switch ((int)com.yiyiaddon.m.b.a<"s3dy25d31gmstn","mN11ApwR9xl+JWJMR3kR12ux6ZYK5tyKH+nlTeffT3k=",5572587267928576535,5259516929525968469,7719741422989249714,-7237745761875869219>()) {
                     case 1792857627:
                        break label133;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (this.b.a(var1, this.a.d())) {
            switch ((int)com.yiyiaddon.m.b.a<"s98o48n8295w8","5uAAnOnrkaZIGbqwSnQ4LsirjhW3y5N+GN8FGS+hrCo=",-2552928421769806239,3258719215372398354,-5820894114195326923,113875574041182277>()) {
               case -1104481306:
                  this.b.gv();
                  this.a.hw();
                  this.nE = 0;
                  this.a = com.yiyiaddon.e.n.r.b.c.INTERACT;
                  return;
               default:
                  throw null;
            }
         }

         this.a.y(var1);
         if (this.nP < 80) {
            label113:
            switch ((int)com.yiyiaddon.m.b.a<"s2r12x0omdr61w","ycDITT8eQvpXk0fuUbAuWVhB7cYGVVlXzX0KmF5c9sM=",-7566477622929877284,4286750883956787324,-7654406658981145102,8126492668682662445>()) {
               case -2037589485:
                  if (this.nO < 240) {
                     if (!this.b.cX()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1e1o4xg4mkxth","U2X7biUeCiz7lc6gyqgf5ZcnQyIoHIHpLYXLu0BMxWk=",-7043527423510800791,5917279064902582235,-3340310683734767942,2397855486007201121>()) {
                           case -171890894:
                              int var4 = this.a.cN();
                              boolean var5 = this.b.b(var1, var4);
                              if (!var5) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s21lnt3uq5n27","PwQMH9KvzdoeeCn54BNHUGsxhm45JCpQVBel/5l0FCI=",-7658831040442232186,3516900662414038089,-9014026145846550168,4684951443042245697>()) {
                                    case 936737518:
                                       if (this.a == h.COLLECT) {
                                          label89:
                                          switch ((int)com.yiyiaddon.m.b.a<"s28z9nx9p54xd4","ep6BkbcMLo4Y+odfjxt3doK173W1z1Bijb1jUG/SRpA=",-4233449398485377868,-4436030970192566907,356142672048211217,8470877362189278174>()) {
                                             case 1181293894:
                                                var5 = this.b.b(var1, 1);
                                                switch ((int)com.yiyiaddon.m.b.a<"s1rjzi7uk4m1s","bry7rJDme5fRj3MGP7CA3rF6V9ID6HR0DEona5EXZ7E=",-3414794868321713946,5998198924213345012,3414169855722646009,-163588788966885792>()) {
                                                   case -490520131:
                                                      break label89;
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

                              if (!var5) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s24nsoww8yfd4y","hNeddP1xaA7F+vsbqAwyxa9IN01VSL2uyV36dZsSSXc=",-7964257579681935022,-5615841579784685788,5814029462195987023,8541399234830863189>()) {
                                    case 1638529491:
                                       this.a.a(this.a, var1);
                                       this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
                                       switch ((int)com.yiyiaddon.m.b.a<"si11qe3gm7vw3","BDj8oNA/ifpS+dfcMXqBzw9hs/SAyzfwexb9WNwOSr4=",-4577057512404538356,-2694919633195031626,8088439900577563408,6267872593927609826>()) {
                                          case 2050762167:
                                             return;
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

                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2j7clu0ht4p1j","vbX2ktKKQlG1ihKVBBseFjKSJx25d15jYrFY24xkcp8=",-1828494302634702272,741971751420798094,2267817433895411597,-5505297826937138709>()) {
                     case 1958669249:
                        break label113;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.b.gv();
         this.ac++;
         this.a.hw();
         if (this.ac >= 3) {
            switch ((int)com.yiyiaddon.m.b.a<"s26xvylh2qpnlx","f4lX+OiaX9FVRPaDD8d9ZTT5TRd+tCu5pWVK2+Zly6c=",-9192109178426955897,3232144089476481514,-4907723762020599766,6502736564268641590>()) {
               case -496444036:
                  this.a.a(this.a, var1);
                  if (this.a == h.COLLECT) {
                     label101:
                     switch ((int)com.yiyiaddon.m.b.a<"s19k57ru7bctit","lI3QNaDYIOhzl8ucpVbh9+VjHJsoTE8hj0PLD7dee94=",-3028062899818400419,-3136132744628663432,-2410774636238480178,-8693758523491299419>()) {
                        case 2047537174:
                           this.a.w(var1);
                           switch ((int)com.yiyiaddon.m.b.a<"s3466afci2cda2","nxcwJnC+f8h5WGNrgGsxuFF4qRvX1MfNrxwOm6vuKgI=",785843450006287391,7717882497318029439,-4261990671898346494,-6588888046152318918>()) {
                              case -96909372:
                                 break label101;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.c
                        .d(
                           "" + this.a + var1,
                           (String)com.yiyiaddon.m.b.a<"s8aikgh6sdjx4","Qj+l4GMmSnU4c5zNYk/9333CZqGUvjUpxHWWpJEzVMLAECxGePkExQ==",-7687533542221804964,7081130144945036000,7612471543107004908,-5509260499653819161>(),
                           (String)com.yiyiaddon.m.b.a<"s27qsqovp9krmj","2OyKrFsKhSz2UULO6ZDD6AV8rIL3MwIlikpJhY2Ir6KA2KUXla1dcmRPX/HbzAc10m23TOJQ16c=",3447736866989006428,-8174297145522159876,-4207169191654746619,121569158528485617>()
                        );
                     switch ((int)com.yiyiaddon.m.b.a<"sai2olfy2tuv7","DBDm8d+axV2O/bUkqd+cdHljKv2WMACldfeFN+cIYfQ=",-46061447602367779,1141582603387573972,-5023457471269404573,2716311621637469402>()) {
                        case -997695485:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
                  switch ((int)com.yiyiaddon.m.b.a<"s2ntgfqmami2qf","nipFvX8X/hUdLUM1CYAeuNl8KS0NQhzWFwSI6AByp7U=",-2116998604049527839,-1260557659761491737,1430169662735267901,722126625812043727>()) {
                     case 969694300:
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

   private void he() {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5mabq84leuhb","G1bjZ4srs2PRrx3W+ZkFf07JVHzPIGHdLkt55e8Bkkw=",-8700852206479770062,-6462363973317265241,-923722069552313208,-8127799680440443595>()) {
            case 501464299:
               this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
               return;
            default:
               throw null;
         }
      } else if (!this.a.dV()) {
         switch ((int)com.yiyiaddon.m.b.a<"s8igqxuot3e9c","FRwOMdL2k87cgFICzlwr/afCJ0gPi4mKdHBneeA6Oi4=",4419671511580756610,-7210664107969322524,-6995493008369257316,-4631810816587418698>()) {
            case -1213928479:
               return;
            default:
               throw null;
         }
      } else {
         if (this.a != h.RESTOCK) {
            switch ((int)com.yiyiaddon.m.b.a<"s31niwulr1g9bh","qBYGMHLS1vcAZVK+fDgv33KJvWveZt7TnOjD3joemnk=",-206721564982914428,7519914948389506785,7795706843245828490,-6736501112796865674>()) {
               case -26542980:
                  if (this.a != h.UNLOAD) {
                     label107:
                     switch ((int)com.yiyiaddon.m.b.a<"smvb5ai7b24wk","p9w8UIYs3mKAjoVcboaaDK6YdfvBnf4dC0xQ9JuadaM=",6870245694216047486,-4967297833282572136,66665645361093311,886124018495497642>()) {
                        case -1014364435:
                           if (this.a != h.SEED_RETURN) {
                              if (this.a == h.COLLECT) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s21c9trngtusyf","YKcOSV0scHS2NacPqLQj1e0qMaFaV3MEPXia0CLqxq0=",7195925228391594021,-1661843749812978597,6710876353377387343,2221416627222395103>()) {
                                    case 1724268456:
                                       this.nE = 0;
                                       this.a = com.yiyiaddon.e.n.r.b.c.VERIFY;
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.a != h.SPRINKLER_CHECK) {
                                 label98:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1bb1a1b8yr4gk","daHlGtWJEiuyO/Sb0dmVhb6BaZ24PuKsu6BA06peI2c=",-8221418959494503106,-6409592413987880024,-2864512701586318381,-2649702366196656436>()) {
                                    case -1451781977:
                                       if (this.a != h.SPRINKLER_REFILL) {
                                          if (this.a == h.REFILL) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3qns6i22clhph","KdDG6Pw32vE3suha5n5SBG8+8eGAQO0WgGXgSMSqqhA=",267178691484934555,-2934263785783332296,-8053754985938626432,-8648003479667284963>()) {
                                                case 1263182345:
                                                   this.a.hl();
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.a == h.RETURN_CENTER) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2abr04gtyihic","3ssi5c9Z+D5DGsrdwQ39XrqW2+sI+BcL+JBDNGFfCg0=",556881132042152114,8774762913052655559,-9206296016685514292,2757463709014749389>()) {
                                                case -300124630:
                                                   this.nE = 0;
                                                   this.a = com.yiyiaddon.e.n.r.b.c.VERIFY;
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          boolean var10000;
                                          label88:
                                          switch (this.a) {
                                             case HARVEST:
                                                var10000 = this.a.dW();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1ainljq2jxdwe","whYMwxzhuX2wT/tMly67JM/E+IuLdt5MvP0X2mIwaDs=",2368148052362204803,-4419201936924295356,1684511348654508032,-1595422942994899925>()) {
                                                   case -1204836489:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             case LEARN_HARVEST:
                                                var10000 = this.a.ea();
                                                switch ((int)com.yiyiaddon.m.b.a<"skxwjf3h0bcyb","qu0nFyMKngE2OZfNFhJgd82zQx3G78AgTldsJaPv9lU=",6051349228573951890,4358844644863918572,1720165470865140845,3816903780328167674>()) {
                                                   case -1395410375:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             case CLEAR_DEAD:
                                             case CLEAR_MISMATCH:
                                             case CLEAR_JUNK:
                                                var10000 = this.a.eb();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2w4t27m5hop79","NMsrgLkyC3PuIY1MXdEZJTuG09GtFYyjTG29jmpzJEY=",-2631703305760976033,3845978112782179455,-3946650413993556617,-6937257751469757776>()) {
                                                   case -1210702810:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             case WATER:
                                                var10000 = this.a.ec();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2kghl7mah60mc","51aPyCpriaFnhc82aZnbO2VHBfuuY0xs6jPWt78xvac=",8139816576202195533,-2670106626471771787,-3146976507171513047,-6270624405158743095>()) {
                                                   case 1552935628:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             case PLANT:
                                                var10000 = this.a.ef();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2ngrtx1i8y4bg","b2dvMVK4zoduhukk/tPiVJTlv5Cw7UWITmvKQ9ldR2w=",3342321180850641076,-8449976680181108631,-3422600455235771009,-4403063670496532933>()) {
                                                   case 1581735477:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             case FERTILIZE:
                                                var10000 = this.a.eg();
                                                switch ((int)com.yiyiaddon.m.b.a<"sprs1eya65xss","w8PCntyTUGbSa631GibMPfn4p4IsWv9ejS7kjkGkRas=",-246338601537354486,5595126028421395554,-944717976481398673,9135748250892558389>()) {
                                                   case -1588665624:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             case POTION:
                                                var10000 = this.a.eh();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2doo5a24t2l43","8MANYyp4XA65SddMhdPJwYxn6MGBHSysxOtCOK0Gml4=",4620805865031334433,-6225393742072319331,4173266555468978017,-8102766537343446482>()) {
                                                   case -910163372:
                                                      break label88;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                var10000 = false;
                                                switch ((int)com.yiyiaddon.m.b.a<"s319wweu9ziosc","axy6xeR38R60/x7osccNeVkO+qJDaXGxMdQVeqRzRrQ=",-2347962892498470379,6528602242826988348,7126232748280464139,-4686422424728728183>()) {
                                                   case -1840775771:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                          }

                                          boolean var1 = var10000;
                                          this.nE = 0;
                                          this.a = com.yiyiaddon.e.n.r.b.c.VERIFY;
                                          if (!var1) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3pyr1flj14m6f","b5GM+q9ACh7w1fI4GVBxZWOk9l06MR3wKUhkQD1kzN4=",-6781061016295842486,-8812102435975006713,-8011756495117606434,-6842764602752417877>()) {
                                                case -741459570:
                                                   this.ac++;
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          this.a.hj();
                                          return;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s1bjn03fa6l12k","sKnFjbwqv1O7FP6+zztIwCqBs3PQNgiZDCMI4/98so8=",-7015294121084647690,5040760340343186518,-4436225437229009208,-2574023396579092017>()) {
                                          case 346342041:
                                             break label98;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              this.a.ho();
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s33fxmjquueuho","TjvrvhgUjO04296Ol26/TsehvjrYS5hVX8hQNcV4in0=",8715676438666023275,-1266464047432547984,3545749003005570713,1629698619941690500>()) {
                              case -475726158:
                                 break label107;
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

         this.a.gW();
      }
   }

   private void hf() {
      this.nE++;
      if (this.nE < this.a.cO()) {
         switch ((int)com.yiyiaddon.m.b.a<"s10j8gruf3eooo","PEEC+wMUZ+iZHL+TnY4esu/xQEmYdydu4YaW8LWi7Q8=",3874718920212401489,1742508387854868806,-6983842393203191739,-8664103873617077641>()) {
            case 379968153:
               return;
            default:
               throw null;
         }
      } else if (this.a == h.REFILL) {
         switch ((int)com.yiyiaddon.m.b.a<"syggxw2eewfj9","HDyFR/+Z21g6IHQx6A9nO+tiiwQCSDOQC8tmaiuvEq8=",3598078399085168585,-1424113818964235522,4162239133743393197,-3799785676136134015>()) {
            case 1468782109:
               this.a.hm();
               return;
            default:
               throw null;
         }
      } else if (this.a == h.LEARN_HARVEST) {
         switch ((int)com.yiyiaddon.m.b.a<"s1nfm3z3vu8bkj","unSW3zRjiRhQAnW0s0MUxpO/EjwIvc00dDArl+OEaGo=",-2739983279650173205,-5785709626429680037,-2035153582823955245,5308440105229230850>()) {
            case -1234866707:
               this.a.hz();
               this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
               return;
            default:
               throw null;
         }
      } else {
         boolean var1 = this.a.ey();
         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s19v8tblig7fnv","7iHY6cdWK/y+DZ1e0VzHiUn9CTjZ3VkIYSFTXLMpx84=",-2623765319538606684,-1518476935701503222,5265224434752764406,-684620728367305795>()) {
               case -1574568980:
                  this.a.hC();
                  this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
                  switch ((int)com.yiyiaddon.m.b.a<"s3c6vfzzc12ftx","d7ddrOZ+RkBwI9w6JeKfnkG9FZex9RA8+AeKahwgJis=",30751179993000288,-4304603870091509088,8921881722874814978,3381428575840062059>()) {
                     case 1006359629:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (this.a == h.SPRINKLER_CHECK) {
            switch ((int)com.yiyiaddon.m.b.a<"s2d8oe1lc5czuh","IcFf/OderTy69KnaDK3Ov2qAAU5dJEF9PMa6AegNl54=",-5974614420031097879,6672092036230799025,-5413246643743219235,-808119352234246919>()) {
               case 573777488:
                  this.nE = 0;
                  this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                  switch ((int)com.yiyiaddon.m.b.a<"smhdyob76dotr","kzVO+FFTUK7n1ZftV5AEK8WI8fQjIu3clKm91Pt/CCQ=",3679296395732584715,-7417931597796380722,-3046840885431175337,111950456482964565>()) {
                     case 1574163722:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            label89: {
               if (this.a == h.WATER) {
                  label77:
                  switch ((int)com.yiyiaddon.m.b.a<"s2il0wencxpq6w","QcFEWFyvNkVhZoDIUe8Ta359Ly1p+bFTg0PjDsyTvwo=",272672355836006475,-7719447892865233883,-5912163918754596836,-2156695236468235411>()) {
                     case 1175025352:
                        if (this.a.ek()) {
                           break label89;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3fa6x9z7mi3cw","qq45EYNRoRjGbiUZhe27BfYhWn0aERBNTjTmaF+hncM=",-8564478700150809134,1298371740556543994,-1609246239704950565,-7280263644086628740>()) {
                           case -478463991:
                              if (this.ac >= 3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ad10xvlex730","fquosiO7ZGCKoSn6WCM06dkZx6pBqPd77JVbf0t4geQ=",-239718083200349721,-3311032199463420162,-4113613851047283153,-7139734166590450194>()) {
                                    case 1373671209:
                                       break label89;
                                    default:
                                       throw null;
                                 }
                              }
                              break label77;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.ac >= 3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3txwzaryhqfns","m2RJepwZvYQyHMcufd34O7kNbfYV14Z+mrXrlIgjfkQ=",-5879812140945058926,-6534828747528705889,-6924007422808838175,-827705833802820269>()) {
                     case -345959125:
                        if (this.a == h.CLEAR_JUNK) {
                           label61:
                           switch ((int)com.yiyiaddon.m.b.a<"s3bdjoezc6uoz2","59BxxuLgX8oFrIT+QBBvrrynZRucsf8rXBd4FV4cOOw=",6389781003821469881,9012010295884393788,-31601686679561266,-3153945310566889529>()) {
                              case 1604021615:
                                 this.a.a(h.CLEAR_JUNK, this.J);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3f0d0rd0sajpj","Jel1eq1W+zwXPpXmgSMamu1dnktg6Yqa5WWFz0zxXps=",5826025807163146349,4233838971742567677,7677389530233079756,-8544151041569626321>()) {
                                    case -1383003986:
                                       break label61;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
                        switch ((int)com.yiyiaddon.m.b.a<"s2m72vymxihx10","bjcSVFrcgJbYz23jE4MKHvM2OB5ZUeE1YGqKAi/Gw20=",-1637371490333946758,3968869140426898515,-5476670552842336999,-3667703889804297446>()) {
                           case 844597296:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.ac++;
                  this.nE = 0;
                  this.a = com.yiyiaddon.e.n.r.b.c.NAVIGATE;
                  switch ((int)com.yiyiaddon.m.b.a<"s38a22l71xbg5y","+e8t+KDIwLnVnNZUuvmPgbu2qgKuZ8MY8K1utNocf4o=",-1113398550218257243,-4117335056481824382,-5092208026950965709,7888172818972365718>()) {
                     case 1356647638:
                        return;
                     default:
                        throw null;
                  }
               }
            }

            this.dU = true;
            this.a = com.yiyiaddon.e.n.r.b.c.REPLAN;
            switch ((int)com.yiyiaddon.m.b.a<"s20twue203os39","lQViL9xKoYQdj79UXy1/3lB1xpVA/ugQ5eCcpHHX8ew=",5276842102132522594,5425519237139840299,895086677836283739,7531950260603099788>()) {
               case -2064502509:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private void hg() {
      boolean var10000;
      label80: {
         if (this.a != h.RESTOCK) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ds8pflz4s24b","/T1QMZoQ+/AaHiL42CosAdAulIVu8PPM/jPGX7TyTvo=",8395838636039161711,-8039151339161971212,-735874748128390485,-6802149672280846181>()) {
               case 1680519254:
                  if (this.a != h.UNLOAD) {
                     label69:
                     switch ((int)com.yiyiaddon.m.b.a<"s13o39ehwdahq1","otahQl3quLmJgLSc1GTS8xBlBi5PaFPIL9TzCIrNvrI=",3403434078529935717,-7238308175971089820,2258210373047651857,-3038135120583695594>()) {
                        case 29521537:
                           if (this.a != h.SEED_RETURN) {
                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s2zncwa6cddnym","CS4gS9KHU99m4JS1e9hTQze3Bx5rwAaLpUokiTqqs0s=",5014769047967222355,-918859907266343100,-2475621000955554367,3736936228330780347>()) {
                                 case -665545169:
                                    break label80;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s6h87j3yg8j1k","ebISp+TIx+Keru+Xiw50FOIEX6IiRx8EfEVsCfXE0Vw=",-2346770355706898544,8463302456162443352,1520730744891160484,-451577161586543934>()) {
                              case -2065506416:
                                 break label69;
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

         var10000 = true;
         switch ((int)com.yiyiaddon.m.b.a<"s1upkx27ph2vez","k2Ltz3/Mhbfy6boJVvEXgVRhgWdoD8iQnHYoX1OoCjE=",-2534952841160895784,-5881208346918596036,-3274850495752539637,-801415193843502374>()) {
            case 1155386094:
               break;
            default:
               throw null;
         }
      }

      boolean var1 = var10000;
      if (this.a == h.RETURN_CENTER) {
         label58:
         switch ((int)com.yiyiaddon.m.b.a<"sj35ie73yeowx","jfLv3a6ZaLkoJWcNZUIMLEM5SiAZ9DHRKz5N5eozMnw=",1299810676289162819,4041698606336870893,3210957958211156416,-6257680687487253447>()) {
            case -1368629563:
               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s1k1r3utha9wp3","ZVeu7boXyr0pB5hVduJh5flC9FW1smYvZgUED5JHhJE=",-7292448075124426123,-2924990522616462266,3283410332578331562,5584420802363955073>()) {
                  case 772254944:
                     break label58;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s1qu7xsra4atjo","N2lD/e2/csYpPG3AmEIp46rDiiyilKl8E5/G2ogaS8U=",1823619708825190857,-4436295954360200132,4743456444145152025,-5616512679617266030>()) {
            case 1521451414:
               break;
            default:
               throw null;
         }
      }

      boolean var2 = var10000;
      this.a.hr();
      this.a.hi();
      this.a = null;
      this.J = null;
      this.a = null;
      this.ac = 0;
      this.nE = 0;
      this.nF = 0;
      this.nI = 0;
      this.nJ = 0;
      this.c = null;
      this.nL = 0;
      this.dT = false;
      this.nM = 0;
      this.d = null;
      this.nN = 0;
      this.dV = false;
      this.a.hw();
      this.a.hB();
      if (!var1) {
         label53:
         switch ((int)com.yiyiaddon.m.b.a<"slm6r5liiu6nu","2KrfzYOOIEuHzBIwPqiJGNIgPg2vslvPX1e2aC1YUHY=",-2240616472475080226,8924919113165160935,-5682494998758745525,284095470404902165>()) {
            case -1736004470:
               this.a = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1genlhj5z294m","cb/al690ZRm9jMJE408xbjvLPPu/iiI+/LqlVd0Z3Eg=",-8093911339282740638,5901914340732088156,-9003647933846901516,-5852613389808642325>()) {
                  case 722871588:
                     break label53;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1) {
         label48:
         switch ((int)com.yiyiaddon.m.b.a<"spa2jj405kkc","cMHldaBrSaZAM8rASA/2RLfoBvWfAzGECCZPWqMdXdA=",-8921851506081303019,-1412007138637902398,2488299646172832912,-1010008825036391263>()) {
            case 1854693554:
               this.nK = 200;
               this.L = null;
               this.a = null;
               this.a = null;
               com.yiyiaddon.i.a.a.cD();
               this.f.f();
               switch ((int)com.yiyiaddon.m.b.a<"s38exyqbr21y0k","ljjkwOB9y51scZysCFrESU4oG/oGkooe0IB5nerzed0=",7113144763435844201,-7324744547833455387,7195746529714378127,9078771433099893138>()) {
                  case -1238006946:
                     break label48;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var2) {
         label43:
         switch ((int)com.yiyiaddon.m.b.a<"s1og1blp6xn4w5","m51bh8OwPhtUeTQLF7nUXrW6Ng6ConPNxo0J6H4FlWs=",4571753851672339840,-800202865466095957,3681363942013463147,-6563011815664221662>()) {
            case 445912051:
               this.jG = 0;
               switch ((int)com.yiyiaddon.m.b.a<"scfg68s4k600","KPay8LgE6/vRYta5ZZbhPSVCnhsCOOCFFeP0sK8rBYQ=",-1219803871727145921,5307275541551960181,-6777598101821460864,-762327081569028270>()) {
                  case 908147227:
                     break label43;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.hh();
   }

   private void hh() {
      BlockPos var1 = this.a.A();
      BlockPos var2 = this.a.B();
      this.bV.clear();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sv3nzhsw6u0ow","WIhBcGFBhYpC2Ge5+fP3ft7YkwU2Vg4nryFXv7qSeN0=",5042385111055838751,-1746359070159947743,7128245307026836633,-5757940404942595862>()) {
            case 1280212368:
               if (var2 != null) {
                  label16:
                  switch ((int)com.yiyiaddon.m.b.a<"svg6ixf0cz5hq","1R5wZv2eA2dQmKObmkk+npngGnU0ImI+Z7jADyZdd2A=",6225635820060775429,-2933188470913677545,-8807250662596243254,-8268767836836028480>()) {
                     case -366511212:
                        this.b.c(var1, var2);
                        switch ((int)com.yiyiaddon.m.b.a<"s2bpl2pmtu9nhi","DRSCg0W6RbVQSy3TTE6AlCHFv9iroh8ITkbHTa3ze2I=",-7433626833680014388,3327707259224484042,7210693539675925482,-892690350531381048>()) {
                           case 1661473921:
                              break label16;
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

      this.a = com.yiyiaddon.e.n.r.b.c.OBSERVE;
   }

   com.yiyiaddon.e.n.r.b.a a(com.yiyiaddon.e.n.i.a var1) {
      int var2 = this.a == null ? 0 : this.a.a(var1);
      int var3 = this.a == null ? 0 : this.a.b(var1);
      com.yiyiaddon.e.n.d.a.a var4 = this.b(var1.dk());
      return new com.yiyiaddon.e.n.r.b.a(var1.dk(), var1.dB(), var1.aP() == null ? List.of() : var1.aP(), var2, var3, this.a.i(var1), var3 >= var4.bT());
   }

   static String p(String var0, String var1) {
      return var0 + var1;
   }

   public List<com.yiyiaddon.e.n.r.b.a> bf() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.a.bk().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s15dm2ksewetd7","hHTPmgvSVRxMTPQkufkr8k5gCWJh6ZgCGw9CotwZHnI=",2077643124103218083,3059182177976049729,2132043093465846512,2155113899494360191>()) {
         case -575504149:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3c9pnbsukzg8s","7ZKR/1gqRcqHmTjLr4YyPUiAasjX8yH3SGVT7teXZLc=",-1003184980177421878,2718568595434638620,8130568100117003378,9106644214427891613>()) {
                  case 1630620934:
                     com.yiyiaddon.e.n.i.a var3 = (com.yiyiaddon.e.n.i.a)var2.next();
                     var1.add(this.a(var3));
                     switch ((int)com.yiyiaddon.m.b.a<"s5sglfctqevf8","e6vtL8g26u+2PgTm8KOgiMNocP88TcToavqNFrLKt9A=",-3983002310670961557,-6432457576295226321,2126183630058751480,-4733380246641636495>()) {
                        case 1974599189:
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

   public boolean dR() {
      label37: {
         if (!this.X.isEmpty()) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"sacuaay6sn5ca","QKrXLP1UYEiJWvWz/1LVRlupuVfIOgyEH0JT7AQKu5Q=",5498231843963051812,4645870860770598415,-8371014779181983176,-8752748212985909135>()) {
               case 1968549298:
                  if (this.a == null) {
                     break label37;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"seg5qlw7wr0at","o9+Cvc0KkioIf1eBcXsnPDPdpZCFNlgOICv5nYBm6TM=",717250535874951992,-4963471161142615238,3602322085498756676,5960708616616013259>()) {
                     case -1695537067:
                        if (this.a == h.RETURN_CENTER) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3a17prq2b31my","hrJZka24OVzKIz+Nfj4YWOJFVNz8dh/nbPOX3NNZ6s4=",-9157157538864699807,-2502033788643421120,-8846588018538408547,4140160444356372606>()) {
                              case 450634883:
                                 break label37;
                              default:
                                 throw null;
                           }
                        }
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s1hcjjukrihar","BIqqsVPCE+upF+wBYFqjub5nuaOiqgRphAR3JuShLCU=",-7157026490049816522,-5378489874520191952,-6491937964596745161,7439956926470363985>()) {
            case -2108894637:
               return false;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s11lnzvfjn7dj4","L0282Zosu78CS3Dh3Yw6v90EbTyiyC5NGfbAmxgciNc=",9117552726206656154,-4091097978412136190,-6703117840831050394,812563466257568086>()) {
         case -2066036499:
            return true;
         default:
            throw null;
      }
   }

   public record a(String uz, String uA, List<String> bW, int nZ, int oa, boolean ec, boolean ed) {
      public String dk() {
         return this.uz;
      }

      public String eB() {
         return this.uA;
      }

      public List<String> bg() {
         return this.bW;
      }

      public int cG() {
         return this.nZ;
      }

      public int cH() {
         return this.oa;
      }

      public boolean dS() {
         return this.ec;
      }

      public boolean dT() {
         return this.ed;
      }
   }

   public record b(String uB, String uC, com.yiyiaddon.e.n.i.g d, String uD) {
      public String dk() {
         return this.uB;
      }

      public String dK() {
         return this.uC;
      }

      public com.yiyiaddon.e.n.i.g a() {
         return this.d;
      }

      public String dL() {
         return this.uD;
      }
   }

   public enum c {
      OBSERVE,
      DECIDE,
      NAVIGATE,
      INTERACT,
      VERIFY,
      REPLAN;
   }

   public record d(String uE, com.yiyiaddon.e.n.j.d b, BlockPos N) {
      public String dk() {
         return this.uE;
      }

      public com.yiyiaddon.e.n.j.d a() {
         return this.b;
      }

      public BlockPos a() {
         return this.N;
      }
   }

   record e(long U, int ob, BlockPos O) {
      public long p() {
         return this.U;
      }

      public int cI() {
         return this.ob;
      }

      public BlockPos u() {
         return this.O;
      }
   }
}
