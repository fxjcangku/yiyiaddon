package com.yiyiaddon.e.b;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.g.a.f;
import com.yiyiaddon.g.a.g;
import com.yiyiaddon.i.g.c;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public final class a extends com.yiyiaddon.d.b.a implements com.yiyiaddon.e.b.c.a.a {
   public static final String bd = "autochest";
   public static final String be = "自动箱子";
   private static final String bf = (String)b.a<"s3ql6kylx687rz","QkmIpN3uDUrxD7UMVOhjXH6zXSSrUIJJ74jd2ap8",1688509244385873273,-5021164675534537480,-8697272593758683152,5008712085871399876>();
   private static final float q = 15.0F;
   private static final double c = 6.0;
   private static final float r = 25.0F;
   private static final int G = 36;
   private final Minecraft c = Minecraft.getInstance();
   private final com.yiyiaddon.e.b.b.a a = new com.yiyiaddon.e.b.b.a();
   private final com.yiyiaddon.e.b.e.a a;
   private final com.yiyiaddon.j.a.b a;
   private final com.yiyiaddon.j.a.a a;
   private final com.yiyiaddon.e.b.f.a a;
   private final com.yiyiaddon.e.b.f.b a;
   private final com.yiyiaddon.e.b.c.a a;
   private final com.yiyiaddon.e.b.d.a a;
   private final d d = new d();
   private final d e = new d();
   private final d f = new d();
   private int H;
   private final Set<BlockPos> f = new HashSet<>();
   private String bg;

   public a() {
      super(
         (String)b.a<"s36d4db1pqyoyo","1RjGG+amrvSH4JLpCMYzIK8h4on4EBYMIJ0+Rsw/+5vM3AqPfOdgaiRoY/1DVA==",-1533630909376516030,7471459616057647342,-3434365430869916613,-7780246858648979785>(),
         (String)b.a<"svd6ixrgmnuwn","s8BhfUNSyCMa/F9Wke69oGlylPMamnNGvDhf6A/huINv7rJh",-5280055420675785173,5393819138295210828,-1276531942360010485,1775838923741379210>(),
         (String)b.a<"s1f9vtxnw5995v","w4rUCzeMmoKIMygilu4tbjwViLeNIjlibxw3v0euks8R4Hb5XZJYoA==",-8597786812602514300,8316967583533655301,4169342552096667499,8398267705661903507>(),
         (String)b.a<"srq4311i6aeld","th0Jaa9OihMfHCK0uuyVvJYZMEkJUKHbDfY+0AlbJB1C5ojT7Mz+8+wdpZzxCiv/hQV7JoME8w0n03lnbClUEPeBjLiSlsMouo0mjPIRRNNONxmXWAjI5VIEyehbuY/3qBYB7Q==",3318036562771008451,4963681909533178013,-4100421779001556029,-7837058034428121581>()
      );
      this.a = new com.yiyiaddon.e.b.e.a(this.c, this.a::j);
      this.a = new com.yiyiaddon.j.a.b(this.c);
      this.a = new com.yiyiaddon.j.a.a(this.c);
      this.a = new com.yiyiaddon.e.b.f.a(this.a);
      this.a = new com.yiyiaddon.e.b.f.b(this.c);
      this.a = new com.yiyiaddon.e.b.c.a(this.a, this.a, this.a, this.a, this.a, this.a, this);
      this.a = new com.yiyiaddon.e.b.d.a(this);
      this.S();
      com.yiyiaddon.k.b.a.a().k(this::M);
   }

   @Override
   public String a() {
      return (String)b.a<"s3oqmpwwrv6e3c","kIokQjC8DrWUcjJAzAjzV7ZjMF/BfUJVEJXuYeerFxmATxphTzyPq/nk0wFtBw==",-5981592468395648462,-7848542292523971757,-7571625406899085331,-5918758300742966782>();
   }

   @Override
   public String w() {
      return (String)b.a<"s3ql6kylx687rz","QkmIpN3uDUrxD7UMVOhjXH6zXSSrUIJJ74jd2ap8",1688509244385873273,-5021164675534537480,-8697272593758683152,5008712085871399876>();
   }

   @Override
   public int i() {
      return 30;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
      this.S();
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      com.yiyiaddon.d.b.e.d(this);
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.b.g.b(this);
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.b.a.a());
   }

   @Override
   public List<String> f() {
      if (this.c.player != null) {
         switch ((int)b.a<"s1wb0vtyib5ftc","rmFWLx72Fni94KAJdLyNFVhHaEVv/I4Tf/OD/GNlMBg=",4418338661442868322,6133696530780631551,-8032849238223544474,7392781553252355995>()) {
            case 1783949312:
               if (this.c.level != null) {
                  switch ((int)b.a<"s1j7i8y4wmw0qu","5U5kuRln876t2XPC6Qz8vhydw4lQxA8kHLDXCljtUvI=",-2021793750332664715,-4536002693246375095,3538450083308487565,-295854651859622205>()) {
                     case 705973777:
                        if (this.c.gameMode != null) {
                           this.V();
                           ArrayList var1 = new ArrayList();
                           if (this.a.j().isEmpty()) {
                              label63:
                              switch ((int)b.a<"s3u1hjheb4pvr0","2RSjunwWYnl7LNwQ8pDczRo9oeungyVRy0TnmwT8+94=",4847387900727286032,-795464941631152027,-7119794800646900484,-2130238102222430107>()) {
                                 case -601525448:
                                    var1.add(
                                       (String)b.a<"s3lw4xq9rmfqx7","G1P42F0w9l2RDZT5Zb7CfKraiOHbxpM7D5uFJ4wBm9jvYXAnZyvLEBmnvno0egEcbPNReGZ1eHRV0AWCmgf0Gm3sHl4=",-3020675408772893507,-131319505264970289,6163273660094929059,-5556873550064185528>()
                                    );
                                    switch ((int)b.a<"s2hq5obb021q8b","HsYbFSRUhvUTXH22yGkswBPC8gPZRjZz4EXA3bufzFM=",-5226638824286619224,6414181992736914100,-1262718984528316750,480430427236332309>()) {
                                       case 71603415:
                                          break label63;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a.a != g.TAKE_ALL) {
                              switch ((int)b.a<"sebmlrycyab5g","efKSTuM/IIoG/fHDy1DhJhDCUm7UcaCfxAkRa56WjCY=",1207184859771034612,-7967846674092525085,-1726085749575954528,-7248968603169134877>()) {
                                 case 1564126295:
                                    if (com.yiyiaddon.c.a.a.a(com.yiyiaddon.k.b.a.a()).isEmpty()) {
                                       label56:
                                       switch ((int)b.a<"s7a674e0a3spn","MKjYO7rG16d0ZnVXM+OIKsXu9IMTK8I4NpZrdTJUkgE=",-9049240419105739352,6475968443354303768,-7952671189605689815,7000097603735018118>()) {
                                          case -1639512787:
                                             var1.add(
                                                (String)b.a<"s20oj0v6z86zeo","Ntqw5mDaF0uOzmhiPauXYtLLDBiEhvgKzVKmk9+0mME9wO3bci+8eWmIV2QSRsjMOGXi3qfy852+ikceqM6nSjAatWHKMNzQrQ/qtjjWrEdz+Xxj9b5cUQ==",7396788045799662179,-3739224898422439852,-8719616380577119580,2150453895922029061>()
                                             );
                                             switch ((int)b.a<"s12v34s11cxd0u","CmiIkPldWgToGnwzJsIDe9gy3SfF5vuFSjk53iqOMsE=",2828310422848296050,3601467582325796655,-5808958281884656628,2701035669990517716>()) {
                                                case 743263331:
                                                   break label56;
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

                           if (this.a.a == com.yiyiaddon.g.a.f.MARKER) {
                              switch ((int)b.a<"s2g7ou30o32sfb","ViLMatmnZ8b0yAATeDlwVfKh5Ktf2pldzhfY5W0Dc/Y=",-7212294694888011918,-1541879794795116146,-4288726490760631975,-367556396572588230>()) {
                                 case -93442165:
                                    if (!this.a.fw()) {
                                       label49:
                                       switch ((int)b.a<"s2377z6y7zi9kr","u+9MNjw2Hj95CoV1c2epIge3vvtC2YCga4N2NzD8Le0=",2145918574087695867,-6585101481741017676,1501291041551500359,-7709699382339755410>()) {
                                          case 835380458:
                                             var1.add(
                                                (String)b.a<"s2kgsfdawp6tnv","4TMMul3T8JTRu1wJqwQ9N4GZ4FwLDfzf2DSxXWSdKfXTVx4hop5ILtXnv9xzQrgx1bb9tZ25BJa2xT2BlPo31LAOgxv7djC8LnlfntOUl61W7qc76+SRCSRfIKOGC0SgRvS+5lolg5GQ7A==",4092355832817641907,5859320118925075354,1929784611025828776,-847814252626966604>()
                                             );
                                             switch ((int)b.a<"s525oilbdkg89","Qf8oCxy30pEho0Te7zN0GT8M3cmbE8vUBkxtqba8whY=",2926402579476314792,2922470633241219697,2161932734442339120,1479268430320213335>()) {
                                                case -2108463363:
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

                           if (!this.q()) {
                              switch ((int)b.a<"s32p4hzprt6jg8","8VAFzt1HozrVmSfff8Nq4W5waVG8Fd14kmB2AY55s5w=",-1272647891515733114,7612137733489428403,5373132419337123112,-2279797162493016601>()) {
                                 case 2130774965:
                                    var1.add(
                                       (String)b.a<"s9ql8kcbn4dy5","cAGdZT6NnKpx2zaQY+o+R8BoGzlBZ/6VaC21SGJOMx1aqj7Bg1XwZxSZWPg8raewXIwuKTqqJyMy9MCr+Y0PM2cCaUk3PWn1VBe3mzUvFeXg41itZxTj6wqrF6JIluz8dV8fGg==",-5242477304421020312,8408519875502897429,-8529409484416331069,2242882784704012335>()
                                    );
                                    switch ((int)b.a<"srqlypaqhea3z","T183VgXETbAzYLECDOyrKsvH61Rx61pO3VL5QKoDwm0=",-5630533060664256221,-6513354223637968467,-7231083823735614698,-7980681058854107480>()) {
                                       case 1352398100:
                                          return var1;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return var1;
                        }

                        switch ((int)b.a<"s3v6dg998jms2n","0i+vt6o1gomEmVpRARiKKi/0xgzGqCEzXmgJdEGDJv4=",3712356942503421256,3055618842029025994,-6050387643237488887,-9140552225407490550>()) {
                           case -580691430:
                              return List.of();
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

      return List.of();
   }

   @Override
   protected void m() {
      if (this.c.player != null) {
         switch ((int)b.a<"s3fdojjfuzssdo","rRzY7cK9hlAbp00bn83tmFctm5v+96/tHm1presHh1I=",-4438936845777272513,7744042868610133152,5326693646418778940,-5806772859139328142>()) {
            case -532090967:
               if (this.c.level != null) {
                  label20:
                  switch ((int)b.a<"sv3tv5tzoypxm","nyo2PyrXXZEO1X6M93ZdRuRQYrDW6PCJrYQvo4KlOOA=",-2625405301779725126,-5408065939715160416,901534914468500965,-652733211642666182>()) {
                     case -797014091:
                        if (this.c.gameMode != null) {
                           this.U();
                           this.a.a(this.a.J);
                           this.f.clear();
                           this.a.f();
                           this.a.f();
                           this.H = 0;
                           l.a(
                              (String)b.a<"s36d4db1pqyoyo","1RjGG+amrvSH4JLpCMYzIK8h4on4EBYMIJ0+Rsw/+5vM3AqPfOdgaiRoY/1DVA==",-1533630909376516030,7471459616057647342,-3434365430869916613,-7780246858648979785>(),
                              this.a::render
                           );
                           return;
                        }

                        switch ((int)b.a<"s3swmq6auk408n","k41OQI3ZHsVzXD56KIJ9ulPY9dbih74c7UnfYrnTew8=",-8408436915060384163,-795719176597534503,4165365383001476328,6160938285462678154>()) {
                           case 425715747:
                              break label20;
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

      this.o(
         (String)b.a<"s30qkwki948vr7","AiZ981nrmkgmLqUb5ZtwbR+BXDwr5pZfgDGNw8yrGa7OR8tL3DkRV/A4Pk7C9bkfiH2s1hthQXwvyA==",5803987377704718491,-7587750724862672686,-4887125390871178247,-5734292063977116776>()
      );
      this.c
         .execute(
            () -> com.yiyiaddon.d.b.e.a(
               (String)b.a<"s36d4db1pqyoyo","1RjGG+amrvSH4JLpCMYzIK8h4on4EBYMIJ0+Rsw/+5vM3AqPfOdgaiRoY/1DVA==",-1533630909376516030,7471459616057647342,-3434365430869916613,-7780246858648979785>(),
               false
            )
         );
   }

   @Override
   protected void n() {
      this.a.ag();
      this.a.close();
      this.a.f();
      this.f.clear();
      l.l(
         (String)b.a<"s36d4db1pqyoyo","1RjGG+amrvSH4JLpCMYzIK8h4on4EBYMIJ0+Rsw/+5vM3AqPfOdgaiRoY/1DVA==",-1533630909376516030,7471459616057647342,-3434365430869916613,-7780246858648979785>()
      );
   }

   @Override
   public void b(Minecraft var1) {
      if (var1.player != null) {
         switch ((int)b.a<"s1wtajzjwpybf0","fRLKam/kLepbFxEnu3KUDE+UKuhnPVRg2Fun0sMgYP4=",-1832744680384159274,2704132300483601908,-6408998551393634803,7335465886367648590>()) {
            case 191029295:
               if (var1.level != null) {
                  this.a.ae();
                  this.N();
                  BlockPos var2 = var1.player.blockPosition();
                  if (++this.H >= this.a.K) {
                     label30:
                     switch ((int)b.a<"s2zajn7et2nrxo","MLj+EA3iMhXQto7vZ6uwBLA+KJM0K5W2RG4U5pII3sU=",7856818566717536995,934532191258456744,-2054952682740446577,7248944964432115978>()) {
                        case 219892069:
                           this.H = 0;
                           this.a.b(var2);
                           switch ((int)b.a<"s2whijnhka0jyz","fTQv91V0RNsldmX4+Yt5GpuuedeFxgSX6qszF1nVaqE=",6550328351500168590,7724640709258532369,-1340551724412262559,-5109804622333754478>()) {
                              case 1614597281:
                                 break label30;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.a.f(var2)) {
                     label25:
                     switch ((int)b.a<"s1pc74lrvj7d1t","8UBnTgsCSzu6lflF1kSwfy+I1KO7aRn2H0lHqxX2fro=",1083017047601110854,-3041146666637145077,-8151356284913571986,6564564237657947117>()) {
                        case 2061482601:
                           this.a(var2);
                           switch ((int)b.a<"s2mexg3epv4ekh","zGHmhrFs7yuq99ycRGvgd0n9MQn7yRcMdfEbT7SGemg=",-3950978600337660128,-1366178576227443613,5961307051846391344,-7036217883775521806>()) {
                              case 1181707634:
                                 break label25;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a.ae();
                  return;
               } else {
                  switch ((int)b.a<"s3svuzibihj3f7","9SdiJhKAg0iKK40kzFsakBPJXEM4xdvzhKgXod38mbg=",-7133361710457514115,7500674442234049709,-6848612768297202672,-8548431030530672753>()) {
                     case -832984647:
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

   private void a(BlockPos var1) {
      Set var2 = this.a.f();
      String var3 = com.yiyiaddon.i.g.c.bU();
      int var4 = this.a.J;
      Iterator var5 = this.f.iterator();
      switch ((int)b.a<"s1ut12ve8lch6j","Q3jdN6bLO9F5EGHeN1ql4i/AOb69THi0Huep57mnXuA=",-2805836795390590113,-268779404840686977,-8158901873840240754,-6779130091287742438>()) {
         case -1211185291:
            while (var5.hasNext()) {
               switch ((int)b.a<"s35sm6pyuv3ob6","iNJQYWpHNfsfyQ2fb4lOB2bdN9ILannrR8NtspvOn94=",564061357899622155,-7079832695184086030,5197116673891933849,2532856347283771073>()) {
                  case -821607995:
                     BlockPos var6 = (BlockPos)var5.next();
                     if (var2.contains(var6)) {
                        switch ((int)b.a<"s2kkwspc9ztw8y","0biy3JH+JyBZRdUY1A/rnPdSRVd6Sl3Zta4uEnnM+NY=",1399649990150339679,1313786002794792409,-5176053315115580476,-7169981013212493825>()) {
                           case -7300320:
                              switch ((int)b.a<"s3g6xaaik5ejx9","NGqRzItILxWn5GkZiUiOQsDdWh22ps2qlgje1otEqxY=",1354665948806296586,2006975961999312258,-3265141348271638409,-7067054285602427948>()) {
                                 case 659106460:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (a(var6, var1, var4)) {
                           label28:
                           switch ((int)b.a<"s3em02k9itfgdu","F0lfUmvoLcp1v0lVdlp3FUe76YeY8G/LHAc1/L/Hoho=",5304673239236805384,-6807580634302603686,-3010389842801676471,-421745364704656032>()) {
                              case -1521281720:
                                 this.a.d(var6, var3);
                                 switch ((int)b.a<"s17sdjra9hvnsq","IneDgLOptjjxjFJF7HwQgvwWtvxL1vf5hoVHdCGwuzw=",3944770639551046618,-8690770208833937095,2208596751610339858,7263185317494855650>()) {
                                    case 386105201:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s3obdm2plprvj","W5Hp4A/R5QVSPyykrUI/J5Ge5U8VOsFTshG7iApZZqo=",1061840266628791039,-4008608208938105695,7253326553057585467,403416517292406981>()) {
                           case 2093562797:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            this.f.clear();
            this.f.addAll(var2);
            return;
         default:
            throw null;
      }
   }

   private static boolean a(BlockPos var0, BlockPos var1, int var2) {
      if (Math.abs(var0.getX() - var1.getX()) <= var2) {
         switch ((int)b.a<"sjkstjsv9mfb1","VtwSflulxvs44LJiUtOKAPaHP1qp7501ym382TwV8fc=",-7207737109616173918,-4716958857353820434,-7802231128358387010,-2711495228837723238>()) {
            case -1438333950:
               if (Math.abs(var0.getY() - var1.getY()) <= var2) {
                  switch ((int)b.a<"s1d672co2p75wq","p85Ay8UdDqOdOToKWt64la1W3DvTM/acb2Y2wCy3GuY=",4730458753240579768,-6316584260571628356,5908832774393815807,-8846562921975618651>()) {
                     case 1128845760:
                        if (Math.abs(var0.getZ() - var1.getZ()) <= var2) {
                           switch ((int)b.a<"sbkqxpmdn8v4f","kVNw71qhBldSTMkVKxL+N+lBDPwaNQgo0dUB6DzHaww=",8714077071748673103,-5115030495343026070,5221235686689368744,-2024424009532031360>()) {
                              case -32758052:
                                 switch ((int)b.a<"s34ylf6wfw42js","VdEgmBRCErATgrHXGdVLYxqjG5bqWtn7arpPquxjy44=",7743234040672285521,318706160665268350,6737724754402132646,4440812935493429872>()) {
                                    case 1226546694:
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

      switch ((int)b.a<"s1m5awboqqw2o3","S0qKtfKMIGyOH92wEHtqynNaVP4cFEFsToR95WEOcwE=",1333717406625594856,6131977287760197271,8023587831831114871,-7920160690135755436>()) {
         case 1997356502:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public void m(String var1) {
      com.yiyiaddon.d.c.a(
         (String)b.a<"svd6ixrgmnuwn","s8BhfUNSyCMa/F9Wke69oGlylPMamnNGvDhf6A/huINv7rJh",-5280055420675785173,5393819138295210828,-1276531942360010485,1775838923741379210>(),
         var1
      );
   }

   @Override
   public void n(String var1) {
      this.m(var1 + "");
      this.c
         .execute(
            () -> com.yiyiaddon.d.b.e.a(
               (String)b.a<"s36d4db1pqyoyo","1RjGG+amrvSH4JLpCMYzIK8h4on4EBYMIJ0+Rsw/+5vM3AqPfOdgaiRoY/1DVA==",-1533630909376516030,7471459616057647342,-3434365430869916613,-7780246858648979785>(),
               false
            )
         );
   }

   private void o(String var1) {
      com.yiyiaddon.d.c.a(
         (String)b.a<"svd6ixrgmnuwn","s8BhfUNSyCMa/F9Wke69oGlylPMamnNGvDhf6A/huINv7rJh",-5280055420675785173,5393819138295210828,-1276531942360010485,1775838923741379210>(),
         var1 + ""
      );
   }

   private void M() {
      int var1 = com.yiyiaddon.c.a.a.a(com.yiyiaddon.k.b.a.a());
      if (var1 > 0) {
         switch ((int)b.a<"s2yhinat691u75","CfVn4UUcsmj1YiQxjI3jz9JMnY4T/vc3NIVCZwycAH4=",-5810031073421293117,-6847972680496857144,-4848445686806117641,-188776453292357790>()) {
            case -622144960:
               this.o(var1 + "");
               switch ((int)b.a<"s26s7a718qph2y","8a+48iOwdKYQFFWqfLLIK7sU+kbHtwiR0Xt0puwD27I=",7014612043048449983,-2469340631772293474,2388573254625733540,-8451042310717860090>()) {
                  case 646757076:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void N() {
      if (!this.p()) {
         switch ((int)b.a<"s1v7a74awtfv6","oJlMT7QPqdw9Z3PvygMMi4Gyfc24ce38bO06GJYLaBI=",4417529419687520011,3514117835815299682,-3007921114832566246,6089255223137872027>()) {
            case 381171484:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.a.a var1 = this.a.a();
         LocalPlayer var2 = this.c.player;
         if (var1 != null) {
            switch ((int)b.a<"s1uzts6l6ka49b","FSV20xvD2Edod0ePzX6GAh5t7sHdNMnq2FHSquB3ywE=",3853927245418334269,2748328722862661856,-6435987599803819748,-8472691845681828835>()) {
               case -1967393662:
                  if (var2 != null) {
                     BlockPos var3 = var1.a();
                     if (a(var2, var3) > 6.0) {
                        switch ((int)b.a<"s2elknticvz8dz","xtGA5aEaSvOsOKHVl2b77s/NQFRZbjwnAtJc26mwtZw=",-6521153576866338246,-6553187011824881012,7823640837523625053,176508159621695567>()) {
                           case -217814463:
                              return;
                           default:
                              throw null;
                        }
                     }

                     var2.setYRot(a(var2.getYRot(), a(var2, var3), 15.0F));
                     var2.setXRot(a(var2.getXRot(), b(var2, var3), 15.0F));
                     return;
                  } else {
                     switch ((int)b.a<"s38jiecvlmt08t","dvH78VPAPK7ee84fUqEUiziPv64AP/IQTyugU1CZRRc=",3702054273425211034,273484094872969688,3934882998425483972,5524829936131250947>()) {
                        case 573400906:
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

   @Override
   public boolean b(BlockPos var1) {
      LocalPlayer var2 = this.c.player;
      if (var2 == null) {
         switch ((int)b.a<"sf9emkfny3o5n","C5R0pB6pi/1rpweivEtfH9R2BP5/yCJncipunwVbZp8=",-1580837219053899585,-610338928504839152,4454956463716659526,-6368622609002329009>()) {
            case 1434776391:
               return true;
            default:
               throw null;
         }
      } else if (Math.abs(Mth.wrapDegrees(a(var2, var1) - var2.getYRot())) <= 25.0F) {
         switch ((int)b.a<"s1693zkgpmt9ym","acMoSYMPXidlEpam04CjNwH9JQycNh0U8njJBQZ+xiM=",-4577835588866429943,-1468703953554423372,-8698593373872029295,395048196209262069>()) {
            case -1663424596:
               switch ((int)b.a<"s2fsi09v82si45","Z0PAbKvnDqNA3hhVH19qjcGN9sTy7bqDTaJq8zC6hEw=",2165570059144625225,5737597983818104752,-3769033163010961487,-3260649458720410456>()) {
                  case 1383735990:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s22vf40fbw5c3e","nvXFFpjkXa5j6cbD/36FICxPT4cw7nItCiDph8uWlwQ=",-6543807485842977826,-5667148122313099357,2308320782645438611,-6057058131602624175>()) {
            case 413616590:
               return false;
            default:
               throw null;
         }
      }
   }

   private boolean p() {
      switch (this.a.a()) {
         case ARRIVED:
         case OPENING:
         case READING_SLOTS:
         case MATCHING:
         case TAKING:
         case VERIFYING:
         case CLOSING:
            switch ((int)b.a<"s2izfvfznzvjm2","VHNELq+4ghGPOcNU2Ucb6vXsKKYsI1s2McF89P0PEYo=",9004228691729572476,-1144798830227654585,-3423203758289258198,3791678830307435258>()) {
               case -129093501:
                  return true;
               default:
                  throw null;
            }
         default:
            switch ((int)b.a<"s3oimnwvi0bkwu","SAIB0QKi/DJHlh1h6BBix3KP201WNFfFObMFYMs63ws=",5013269340895767631,2935554617040534297,3886403215241385613,3998481106882188247>()) {
               case 288899558:
                  return false;
               default:
                  throw null;
            }
      }
   }

   private static double a(LocalPlayer var0, BlockPos var1) {
      double var2 = var1.getX() + 0.5 - var0.getX();
      double var4 = var1.getZ() + 0.5 - var0.getZ();
      return Math.sqrt(var2 * var2 + var4 * var4);
   }

   private static float a(LocalPlayer var0, BlockPos var1) {
      double var2 = var1.getX() + 0.5 - var0.getX();
      double var4 = var1.getZ() + 0.5 - var0.getZ();
      return (float)Math.toDegrees(Math.atan2(-var2, var4));
   }

   private static float b(LocalPlayer var0, BlockPos var1) {
      double var2 = var1.getY() + 0.5 - var0.getEyeY();
      double var4 = var1.getX() + 0.5 - var0.getX();
      double var6 = var1.getZ() + 0.5 - var0.getZ();
      return (float)Math.toDegrees(-Math.atan2(var2, Math.hypot(var4, var6)));
   }

   private static float a(float var0, float var1, float var2) {
      return var0 + Mth.clamp(Mth.wrapDegrees(var1 - var0), -var2, var2);
   }

   private BlockPos b() {
      if (this.c.hitResult != null) {
         switch ((int)b.a<"s1jjaxpzb7sqvt","JhVxVDMOCyr74LKvAd7chQ1aOT/npBx5LyPz1nJLpz4=",-8110295778079436607,8930216198624931472,7510166536448638158,597993236541397954>()) {
            case 1387189918:
               if (this.c.hitResult.getType() == Type.BLOCK) {
                  HitResult var2 = this.c.hitResult;
                  if (var2 instanceof BlockHitResult) {
                     switch ((int)b.a<"s33ys6qinp9q0","hSPB8ZNBOdsPFqzPPAgJGH9c4TYDd/yDQiSRNnLJtBQ=",3416945082291867262,1032982990730102212,-1910884301871241917,-4522028728944794073>()) {
                        case -1186995490:
                           BlockHitResult var1 = (BlockHitResult)var2;
                           switch ((int)b.a<"s13i4qrywm42gj","J2yU8phVbK75jpZd2TzBcqOcdwyKgDsc5zwCjB8j+5c=",3019234120477550157,438963881483622814,-7035161188614803019,8467442581710312015>()) {
                              case -2089782277:
                                 return var1.getBlockPos().immutable();
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)b.a<"sb64ocmjh8who","ypE3+aFoasRUm7fPn+kiqSwn2mRcvwJfiO0N/vZIYCk=",-1574184711362050677,7432168552512011300,-1138718276149142703,3650554306080398865>()) {
                     case 172624572:
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

   private com.yiyiaddon.g.a.c a(BlockPos var1) {
      if (this.c.level == null) {
         switch ((int)b.a<"sj1bexvce24y","vhxys79eul0kDcgLjU1vxG4gnQR/jOY02IAuaYaE7rs=",-7199802766201672891,4767580739055467265,-5003936759193411730,-7052651089401606216>()) {
            case 853216392:
               return null;
            default:
               throw null;
         }
      } else {
         Block var2 = this.c.level.getBlockState(var1).getBlock();
         return com.yiyiaddon.g.a.d.a(var2, this.a.j());
      }
   }

   public void O() {
      if (this.c.player != null) {
         label45:
         switch ((int)b.a<"s33inzjgh5kmmw","hY2aWOpnFZLpGdzHieE3lwBTxFxAC7/SDKREAmexXy4=",2614075698098009747,7557513561530821403,4858061843978186946,-5726716134190212300>()) {
            case -1782585766:
               if (this.c.level != null) {
                  BlockPos var1 = this.b();
                  if (var1 == null) {
                     switch ((int)b.a<"saxbvibvczx2g","2Hi2i0oLrvazG+Wj0lySNzER1XcUQOSscdjgshD02SE=",-4439807018301525578,1332914114750732357,4647426086395582957,855237085898693351>()) {
                        case 2101625333:
                           this.o(
                              (String)b.a<"syp7gieuiga83","yLVJXGa27vyxhWGPdmuA4kv/XtBzhQmRIcDenz62JNHkgL2Erpm4/qmAs1p0QA==",-5510749405868911675,520510838728320713,6308976857472481501,7911018994760303567>()
                           );
                           return;
                        default:
                           throw null;
                     }
                  }

                  com.yiyiaddon.g.a.c var2 = this.a(var1);
                  if (var2 == null) {
                     switch ((int)b.a<"stnol3id6vo6p","hkH7XSElyRjg0DhVPmRUiAWtXu+tWnUWaoiCpoFLJVg=",-9181477265954318903,-5033695351356979904,-8369838006509890015,-8269623495461726256>()) {
                        case 116805798:
                           this.o(
                              (String)b.a<"s2pafwkhkyhbom","ESNvD5EY98Ep8jWbUP7/Tjq0tCHaigWfD4fbrcbt1lpoJ0PsNXmD7qSRmi0jLG8CfE4=",-3960937392644329191,5003731616788873458,-6230428991839680196,-8352174130866907053>()
                           );
                           return;
                        default:
                           throw null;
                     }
                  }

                  String var3 = com.yiyiaddon.i.g.c.bU();
                  if (this.a.a(var1, var3, var2.s())) {
                     switch ((int)b.a<"s2w37pyhip3owj","fwvjjq8TBFIBhe+nIiR9nD7RL1NpwaGU/aiAPWKqHzc=",6710240540118347900,-4981895754062439668,-1976951051964958283,-701423726503357613>()) {
                        case 244107225:
                           this.m(a(var1.getX(), var1.getY(), var1.getZ()) + com.yiyiaddon.i.g.c.bU(var3) + var2.m());
                           switch ((int)b.a<"s3k0xilwi6v54f","hNnRCLmH7mq0yS1BJx6lYC7OqvEUsaidiprcUcozs/w=",1251704531909921564,1957198301355736434,137501941463030762,-1961780529231107164>()) {
                              case 1098120609:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.o(
                        (String)b.a<"s3p8b489dk9xub","t34env7MSYgXQIEBQgwB812xCQMOm8+oveYwVHCqTFAOZ4lT+PtuCQ==",-6802563092524679218,-8630486066908431271,8868792201785214557,8116307371447976454>()
                     );
                     switch ((int)b.a<"s1z5t6g7navkhx","vARZjNO/DmmecBno+t14tru0HRArbuWKKoNp1HaidLs=",-4172320114667412392,-3161889676536198278,-662297237892659404,1360589429816192816>()) {
                        case -879631849:
                           return;
                        default:
                           throw null;
                     }
                  }
               }

               switch ((int)b.a<"s2n241jmxuty10","P4XF9KshFOtiOoO/VaolSoDiVc7MIvwzeU4QeiWI8ps=",5462586797643476501,1190210113126081813,595687288967240863,-5868830132170807554>()) {
                  case 1718356001:
                     break label45;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.o(
         (String)b.a<"smw7370r5g9u9","/+EPx/AAKmF2SmqKv6jZhVbkQbZRTr/dPeZFaIjHBnk0w73u8G0=",4462317666338441770,-4538247730753293488,-8766736658076026032,6695213425358051006>()
      );
   }

   public void a(com.yiyiaddon.g.a.a var1) {
      String var2 = com.yiyiaddon.i.g.c.bU(var1.bU());
      if (this.a.c(var1.a(), var1.bU())) {
         switch ((int)b.a<"s2clkwzdgy4cxd","usp6HYfBHWNKDeaPiSXNNdKSjTxSq7Heykty4pe57dw=",-451448039907039381,4940936804812778828,1326696437866463325,8095237234417238467>()) {
            case -1487368744:
               this.m(a(var1.a().getX(), var1.a().getY(), var1.a().getZ()) + var2);
               switch ((int)b.a<"s3aeai61j34ojt","GWxLP1LVP+ch2MmiX597TsgKN5Kpbf8tbukcEHrBbhs=",2593720776354675013,-6707087180097354485,-6634934633850161601,-3179029169171625609>()) {
                  case -196862634:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.o(
            (String)b.a<"s1kq3hw8qhmrfx","Gx/oseKyBw81XkcfJGUiyHtzCZHS5QQtFeemoMFvL5GtT51hK5CiESN4h4mpLIX15sh0vg==",2520591167095246956,-2004085912577430025,7233565077803873913,8889333162629397359>()
         );
         switch ((int)b.a<"sze4h3fni4zm5","Hdc/zF+1pwiiDoFBtFmgaGM56ojvnF2dNRBjtSnkKDM=",62964618396688928,1753525115254601385,-7202433995803580099,5273861492355958389>()) {
            case -267259507:
               return;
            default:
               throw null;
         }
      }
   }

   public void P() {
      int var1 = this.a.a();
      this.a.dC();
      this.m(var1 + "");
   }

   public void Q() {
      String var1 = com.yiyiaddon.i.g.c.bU();
      int var2 = this.a.s(var1);
      this.m(var2 + "");
   }

   public void R() {
      int var1 = this.a.dD();
      this.m(var1 + "");
   }

   public static String a(int var0, int var1, int var2) {
      return "" + var0 + var1 + var2;
   }

   public d a() {
      return this.d;
   }

   public d b() {
      return this.e;
   }

   public d c() {
      return this.f;
   }

   private void S() {
      a(this.d, this.a.Q);
      a(this.e, this.a.R);
      a(this.f, this.a.S);
   }

   public void T() {
      int var1 = a(this.d);
      int var2 = a(this.e);
      int var3 = a(this.f);
      if (var1 == this.a.Q) {
         switch ((int)b.a<"s37lznqbv4mr97","JP0XErrUl2EpVWGf4uYyT16UDS2L/CCfrphTTHDhcXU=",8272259849534478646,-4245051673430771232,-2019743561524107272,-4592761637685731258>()) {
            case 1823828114:
               if (var2 == this.a.R) {
                  switch ((int)b.a<"s1iwk1cyi0q76k","qbAAKqMYnssY5r98maIbXATCdA9VIYbJYmgaYDHUDi8=",8461289666141976526,-7334007874539013141,-2671732475812007881,8960974834882653424>()) {
                     case 405961954:
                        if (var3 == this.a.S) {
                           switch ((int)b.a<"s769by6rpglx7","BcvRzmSdzqA9s9urWOrzPNQ39iS55/Bcllwl3GYqY2M=",-3888178040348447911,-9124690404579743862,-6284569315866645788,2927264853411893992>()) {
                              case -361999103:
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

      this.a.Q = var1;
      this.a.R = var2;
      this.a.S = var3;
      this.L();
   }

   private static void a(d var0, int var1) {
      var0.b(var1 & 16777215);
      var0.c(var1 >>> 24 & 0xFF);
   }

   private static int a(d var0) {
      return (var0.ei() & 0xFF) << 24 | var0.eh() & 16777215;
   }

   public com.yiyiaddon.e.b.b.a a() {
      return this.a;
   }

   public com.yiyiaddon.e.b.e.a a() {
      return this.a;
   }

   public com.yiyiaddon.j.a.a a() {
      return this.a;
   }

   public com.yiyiaddon.j.a.b a() {
      return this.a;
   }

   public com.yiyiaddon.e.b.c.a a() {
      return this.a;
   }

   private void U() {
      com.yiyiaddon.k.b.a.a().C();
      this.a.C();
      this.a.C();
      this.bg = S();
   }

   private void V() {
      if (S().equals(this.bg)) {
         switch ((int)b.a<"s3n6zz7otjnw4u","QGcwkgfY5H+vVuD9C/1YU2+tBRUoKNSJ7aj0wDGGgok=",7559421811353824280,5532600291676797644,1379139132994304590,-3589115278590372991>()) {
            case -706031872:
               return;
            default:
               throw null;
         }
      } else {
         this.U();
      }
   }

   private static String S() {
      return com.yiyiaddon.i.g.c.fG() + com.yiyiaddon.i.g.c.bU();
   }

   private boolean q() {
      if (this.c.player == null) {
         switch ((int)b.a<"s2bznn37xza57u","96SAE+IUWV/rH5xVuZdgpJAJXEYXgd1qwgFTgX0IEvg=",826647188687844893,6232582020362118913,-2866814047198118509,215434977616926701>()) {
            case -1819297353:
               return true;
            default:
               throw null;
         }
      } else {
         Inventory var1 = this.c.player.getInventory();
         int var2 = 0;
         switch ((int)b.a<"s1ez1peu7xi41o","sp9uJyaQmb6GNd0KoB9lOUkjoh65sMldW5p4DP/YxNY=",-5832643476863692156,7214787257264643615,-3042373942893659962,-1537112348814477017>()) {
            case 1186003658:
               while (var2 < 36) {
                  switch ((int)b.a<"s3gw5try20tdla","94k3lccrRoQmdFQNdDxi5fhOboGG7XOEDhKmlCbxIm8=",-2598340146413365846,149957898170899688,-4761403131475564732,4897764399436907665>()) {
                     case 1701517819:
                        ItemStack var3 = var1.getItem(var2);
                        if (var3.isEmpty()) {
                           switch ((int)b.a<"szd5fqgzidgsm","et16lYM8vK7X3UaRZfpeTYdZVTud8aKIJt4n5bxr+cM=",5295084464408740624,-5297785466309098808,7600428223447758607,-5651554447449574019>()) {
                              case -1350058403:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        if (var3.isStackable()) {
                           switch ((int)b.a<"s3j2o0io5khakv","K6Pg/qOWnn6vhlSlrAMyDZNncONMqljuocz+9YFcLHM=",-6048086224386469797,7104675714365081073,-5461078869290222208,-2407354189769479808>()) {
                              case 912257964:
                                 if (var3.getCount() < var3.getMaxStackSize()) {
                                    switch ((int)b.a<"s3rlcwx768rmsl","SYZUp2IBVgin5DnyAgq8W2VCKFlra15bONqgYm8mAf4=",2573252954080242930,2155660267035662636,-7764926272290726397,281944447688998992>()) {
                                       case -1020205740:
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

                        var2++;
                        switch ((int)b.a<"s2og9tqqilhztr","XNEne0AQoGks96IVQVxN47Cq2oBHkBI9BEoEAOG2EQU=",-7358129478199323531,-8915988578633661735,7254163910460012892,-6768769841201347193>()) {
                           case 2036775786:
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
}
