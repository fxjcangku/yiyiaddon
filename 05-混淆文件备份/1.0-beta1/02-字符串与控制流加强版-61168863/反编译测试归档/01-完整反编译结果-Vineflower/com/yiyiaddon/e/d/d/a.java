package com.yiyiaddon.e.d.d;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String fr = (String)com.yiyiaddon.m.b.a<"s1dykrkogbto7p","LhaiVzTEaKVosHTJaU0nCDebLHiQ1o3wYfxe54msUGOm3loP/NLF+l3dehm7M/lsEvJcbqwUHV90xuZpfd1G8knHoRhpdKrxUKPrdw==",3404499338871138318,5557216019461982147,-4208476417474884620,1096786629847852275>();
   private static final int cR = 20;
   private static final float S = 6.0F;
   private static final float T = 11.0F;
   private static final float U = 10.0F;
   private static final float V = 12.0F;
   private static final float W = 6.0F;
   private static final float X = 6.0F;
   private static final float Y = 320.0F;
   private final com.yiyiaddon.e.d.a c;
   private final Set<String> p = new HashSet<>();
   private final Map<String, String> v = new HashMap<>();
   private final com.yiyiaddon.e.d.d.a.a a = new com.yiyiaddon.e.d.d.a.a();
   private com.yiyiaddon.e.d.d.a.d a = com.yiyiaddon.e.d.d.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.d.d.a.b a = com.yiyiaddon.e.d.d.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1iooo6wqggnqd","ZtzYdsZAJKZVUYx6b/swpBnq4awIjTuqdFtfd82DpcM=",-352252984475081863,-74954967353932148,8003285119874008014,-5635542006346532468>()) {
            case -458224362:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2hb15c5j42xne","9/hztD6/5cgSK64nLPszSQxIz0MYqTbHJ+NwZhsmgKU=",5518555209909637840,-6831579192419967002,-107470675501229431,-3792327637867078702>()) {
                     case 1249244575:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s9xk8nay76ha9","Y4KuVUvZNMcUancr/W7u3SoZxk3/pzdkPYc/WWhYwB0=",164080891084350429,3042083032067712557,7169187464493454559,-903866981199274189>()) {
                           case -235016921:
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

      switch ((int)com.yiyiaddon.m.b.a<"s23xbz9sgngtlq","dvdAiXUvsl2Xq71DN46r8HoO9drREqpKJiuQ1dR+dtY=",-5745432800049983863,2256843132352367981,7486810906018017819,7872872484735085846>()) {
         case -1288985333:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.d.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"scxtqa58yoks6","Ko5CLQn7X2J2DhLvcdK08lnsmJQ7cBV3nSv7vr6012BnyG6p3YWbmrQH",-2324958291445530695,5940342780536343756,-3461326908440240084,1220181697028859305>(),
         var1
      );
      this.c = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.p;
   }

   public String D(String var1) {
      return this.v
         .getOrDefault(
            var1,
            (String)com.yiyiaddon.m.b.a<"s21qz6hvcsrml8","Qt5db/iW9nt8RDM853sgT4gztcS+o8cUDXzU8g==",8218401113585918905,5087156838198335854,-3159410537718281828,-2471813292299810368>()
         );
   }

   public void d(String var1, String var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sif3kgrnrplic","s+iPg3N1wDyvaxc7dHVA4P21cuaLceTBixbr1/XJtZc=",6473830732795081160,-3584028051377041624,-5268630403340040634,-6940409908855150118>()) {
            case -1758946609:
               return;
            default:
               throw null;
         }
      } else {
         Map var10000 = this.v;
         String var10002;
         if (var2 == null) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"s1no1rjvpxaz8z","Z4BK2fImkVZYKEIECuCE9KUX5/bAibatUs+MWpy6kck=",-8795079202069319618,-2716236708798477890,7163958896591076860,-5456656378484030122>()) {
               case 743321498:
                  var10002 = (String)com.yiyiaddon.m.b.a<"s21qz6hvcsrml8","Qt5db/iW9nt8RDM853sgT4gztcS+o8cUDXzU8g==",8218401113585918905,5087156838198335854,-3159410537718281828,-2471813292299810368>();
                  switch ((int)com.yiyiaddon.m.b.a<"s36aqumk1ny89a","uqHHjKV0OUUKm4hDF7mllyNuynoapAzK3b78Q8l5Zoo=",-1394718304677128862,3332499949771217492,-837748174964387860,4072237817521218114>()) {
                     case 1947789318:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = var2;
            switch ((int)com.yiyiaddon.m.b.a<"s11y4qwf077dx9","7coCU16u0PmOjl/QM98Vohc0BbWjy7LAoC+xOaMijiw=",-2028279971446875347,8162995700464240645,-267389637986667012,-6052519809692934855>()) {
               case 228739251:
                  break;
               default:
                  throw null;
            }
         }

         var10000.put(var1, var10002);
      }
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s1dykrkogbto7p","LhaiVzTEaKVosHTJaU0nCDebLHiQ1o3wYfxe54msUGOm3loP/NLF+l3dehm7M/lsEvJcbqwUHV90xuZpfd1G8knHoRhpdKrxUKPrdw==",3404499338871138318,5557216019461982147,-4208476417474884620,1096786629847852275>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s20o1q99o1vkyi","56BAivpTVF2f4o/iSK6TFRLVbZD6c6PDC+xePlRiaTw=",-7415265113501346434,-9172730972886438355,8056414780266279236,-6626510905784603796>()) {
            case 1576240768:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"sqw5o6zs9owp4","oHYq9Ucg/YLI6vKTFKA16KIeT8R/UZGYwwume3ruBJE=",8318322112683183956,-6743689110379010491,-7163560684101056692,5560678655471113459>()) {
                  case 1287189655:
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
         (String)com.yiyiaddon.m.b.a<"s1dykrkogbto7p","LhaiVzTEaKVosHTJaU0nCDebLHiQ1o3wYfxe54msUGOm3loP/NLF+l3dehm7M/lsEvJcbqwUHV90xuZpfd1G8knHoRhpdKrxUKPrdw==",3404499338871138318,5557216019461982147,-4208476417474884620,1096786629847852275>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s6z4xfy51tdub","ieT2IMVacyEUcpUTaY1Xf7THgVZaXzuJVDM6yRKHHqA=",-3034683996300852378,2381400258099731331,1770589003389432700,-3475434711400332654>()) {
            case 393141004:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"soo220eaa3wy1","RpbWKPy7VDP7zaPTEPjLUUa0g4L9JiqvPo1rZoUDlmE=",7553380605277167020,2873699546984235514,5389694323158256715,289191568293350630>()) {
               case -786609237:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3cigbinj85d5l","r6qDiybHNrF46Mxlnpyz2iOl91DqZ6SfgUXySJ0pEgg=",4606869156356320262,490200126000343395,-3602499981355888980,-759367342579348986>()) {
                           case 2040857195:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.d.d.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sxavi9k0sehvx","2HHVIdvtbH6ingSxIi+FKiCUUVTlV0iRfLrFFNfJglc=",7016138980325980732,-1683639211652854975,-8279621654036065865,3409347850378692038>()) {
                                       case 1829687141:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.d.d.a.b.a(this.c);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2rzxog3mnira","4trD9LGIBCugDz8FOvn/CghQdaQlVnDpExOsmlB5VZQ=",-4474238320669167004,-4581307902878860581,-587637113764450049,7989247171198928125>()) {
                                 case 1290528768:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s17stbx3r65bxk","/vuuMnhJRx4ybWAitLnBWizAlb2ipZXoFZ+rb2S5a3Y=",-5086520179886668070,7765924438572285090,5875585864694989017,2690655757144929034>()) {
                        case 1591112721:
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
         switch ((int)com.yiyiaddon.m.b.a<"s246smq4icll4i","pA2kGljdrAyNVloPZ2v43fJp3TOHXdWyIl2qxNPUocA=",1176927630607944245,577133652594366666,2418055133860047309,-7282105232294457538>()) {
            case 599383736:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1pcc0cuyjotuf","65HTeryEMeGTToT/npW1SF90WFYx8hhoGaNtAjikzy0=",-7861910887658512686,9056026301717902552,4927878353609706360,7038917896592799393>()) {
                        case -459528285:
                           switch ((int)com.yiyiaddon.m.b.a<"s1xe7lf6u3oxme","9L56MIMMc7ht+8/l3eeuy7bRz05Jm1I3kAq0C+Wl3qY=",-1434772148748104520,-8995258576764449664,1528553940344849595,-9186463652042474165>()) {
                              case 1343560780:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"srqcmp26su3zw","inUHKPFDkLbWwuLuBfsyI+SbBh+Ph1uV2GM8W/zuMzM=",-4832686117221369875,749862690852231589,-7722389614863495080,-6542963010391186568>()) {
                        case -608761546:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sbj60iwfaiq9s","DRV1MHDmjD37IDsZY/JjWpDnd8EokE1St9iENdJlJRQ=",-5308267413134164965,7685558427065493060,-2918293872489532294,2747952610341939180>()) {
                     case -960077152:
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
      m.lb();
      this.a = com.yiyiaddon.e.d.d.a.b.a(this.c);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.d.d.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"skxkxyfcnl1x4","1YxPCW9YwDYf9ehvYaVRbNDL8P8iUzXE5cGCqKmxafo=",6906030295560422070,6718885311063884103,-778179645597643241,9015061950842546149>()) {
            case -1737423183:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s20aggifgr0v0g","S3irmdFSqZBqLdGP218fNq/cmjKw9hGJMDrFTb7hW/M=",-4873868092692820803,275965323234062535,-8635764124132042040,-2711775761633382459>()) {
                  case 1225187503:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s2tjy75q8nti2x","MYwmRe378W7Mt0LTkC5tp8+DJrmakiTVDG5brEooXWA=",9007424882312176771,-5607808319839273309,-1682415300602811500,-4606433479422188119>()) {
            case 724555221:
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
      var1.a(new com.yiyiaddon.l.c.a(this.c));
      var1.a(new com.yiyiaddon.e.d.d.a.c());
      this.b(var1);
      label35:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.d.d.a.a(this, this.c).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3ejxbh1u9allz","DdAsq6yNW++x5OmVwJJflNx3Tm0zmIdwV2UTc2VNp4Y=",8876150435124977293,5904053588937320432,-6690088584162580586,-1097652904981789527>()) {
               case -556563540:
                  break label35;
               default:
                  throw null;
            }
         case AUTH:
            new com.yiyiaddon.e.d.d.a.b(this, this.c).i(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3gc3fq0q5qw04","o2JmNWekQhyzQ1t7ToIKbtHpCClvZAUACc7MDBUMsBU=",-570543547695866577,-2162709443485383002,-5941644079191691473,1856860271968256415>()) {
               case 750407750:
                  break label35;
               default:
                  throw null;
            }
         case RECONNECT:
            new com.yiyiaddon.e.d.d.a.b(this, this.c).j(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3sjf1huah0koj","pY/qjWDhXzQmuuc9VXQRRVvfmzKjpSZwfBC7gxV/Zc0=",-3973183807810875679,6168306500979589744,8985533174397230315,7865595942896237619>()) {
               case 1120587868:
                  break label35;
               default:
                  throw null;
            }
         case COMMANDS:
            new com.yiyiaddon.e.d.d.a.b(this, this.c).k(var1);
            switch ((int)com.yiyiaddon.m.b.a<"su99e4l57dgaa","DYvhGffXei3U+lak1z8+RxP9IwC+3HT/pnk28En060M=",-6480616106267868692,-6823431823140139019,-8755915512926936846,7342557266856998579>()) {
               case -905050279:
                  break label35;
               default:
                  throw null;
            }
         case ROUTE:
            new com.yiyiaddon.e.d.d.a.b(this, this.c).l(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3juws6anua9se","2sYknCoelYJW3+Jg7ZshicwxDXFbBbB3MqRoVxYc8u8=",8510589747309538065,708480272982284659,1923617796360700051,-3561413843605156023>()) {
               case -1881459168:
                  break label35;
               default:
                  throw null;
            }
         case LEYUAN:
            new com.yiyiaddon.e.d.d.a.b(this, this.c).m(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sub3b4st81ywm","FcvpzY6ujSVb72qKJOkGEnXqB2QxMJYTUjaUyA/LKlg=",3142724480664035884,8125250844168850676,-8399704130920176019,-4754821509980596117>()) {
               case 1246286071:
                  break label35;
               default:
                  throw null;
            }
         case ACCOUNT:
            new com.yiyiaddon.e.d.d.a.b(this, this.c).n(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s27dfmnnygt34u","xrT82n+aJTAwkAXGnYjeNJll8cd/YJQPn0e+yGyF6Zk=",8330247139420064613,2582791680936061235,5498789986392830419,-4812948960617238754>()) {
               case -1134547284:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.d.d.a.d var6 : com.yiyiaddon.e.d.d.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1sv3fhka5o1jm","qPemJ5vBdPMQ6KfUcUriGyANWfvjEgCSMNF5sZ1kLdo=",7701759923240697468,8761409569375819647,7325461236912432576,-8386352344197541414>()) {
                        case 1993852155:
                           switch ((int)com.yiyiaddon.m.b.a<"s218wkzp19lhg8","YgRIaZ2utf0jMoQ0B3SFVB9U0Pv1CVqRAfrgh+59iT4=",-3109610682481886157,981421287259824516,6458967420099471697,633753587309405709>()) {
                              case -102058114:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s3be2yxm1c76sr","8iloeuew8gMOeCE/K1h5JWR/30Dznd2KfaeVt4bQReA=",5328593390317189640,4995788669295846617,-94878106174762076,2686818276416946097>()) {
                        case -1100622353:
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
                     (String)com.yiyiaddon.m.b.a<"sa0unsvv0h49t","RpFThr6n09tJLZsWSOf+ALZSXVhi7dn+eff1onEQ8MZJCD1K",4096834389112511167,9015343328084353480,7053061842627856775,312875469473867716>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s3d8mcev3eufs4","EjBeXV5FcawNhiJG0NaNghxjXiP7B6VSfeY8ZqmWWUEPm4ucioW4glGeaJ+xn3Ei4ZhOPPOQXAeKX5WFmTQoRYVQgBBN3ru0gvz5bpQhpsduEt6NkgdSPhJoi6eefvZFHnKmUQ==",-9197634651794881817,917707839591971824,-6257595428349335545,-4665414230415960021>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.c, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3h3up3hw82ysz","aYmlc5X3IJjyr4UtWELpnQvXhdqt/2aDljBFhgXv76ar2Wzp",98145312888780809,2273227440508686616,4936317157474998360,1197742641240971855>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s6jaihttc5lb8","aCc7JTGrQ5UdBAKNvv/0wmOliR2606eqVbhZJi7IdkA=",-2463674821716784351,-3069594097618530660,2030667225296278862,-4686762939435287928>()) {
                              case -2020101595:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"smch6w0bhzm2r","WxuGWuc2FJN4+53aNEXywfuQ3CZPxNRTL19EPaOHrNA=",1632444115662853145,5687918937138096492,-6098885089099952226,7703379593509073922>()) {
                                    case 643619610:
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
         (String)com.yiyiaddon.m.b.a<"s1zzjrku3w654u","Tkowy8m50k/hXyDqx9eHnBMBkTcDeVEwMzC2a2Xa",-2091729180432156994,-7105711803858680388,6312745145141573245,355025331055565920>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"syxwollcm5l5q","d/F/5+++PxsbU0U/IIGujzsuq1S28XGNQvNBsI3TlRk=",6634036524928107165,-9056301094874833656,-6469461443977994287,-5139086936967328645>()) {
         case 1481743400:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s2cg6jxogh3o5m","RKbCazc53zuClypQgnaX1BQP8Q77OsR+xoMaheLsOps=",-1608448955926805589,7313891119807875449,5028157546856161143,4970064468033639038>()) {
                  case -1054628710:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s1i0n0bzal7q2f","PMd9iPXqED/sTs5YS4TQeoLZL6nusGKZxWJWXHcu9tQ=",-1862771983161289681,-7750013759111937397,-8694833208184294659,-1100352272018123242>()) {
                           case -444609954:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s21qz6hvcsrml8","Qt5db/iW9nt8RDM853sgT4gztcS+o8cUDXzU8g==",8218401113585918905,5087156838198335854,-3159410537718281828,-2471813292299810368>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s2a2l7dwy2dalk","O1ZfonvKZzUuCF8CmkV3rUfDsgcMNSgsgi77uJ5s+bM=",-8016354747756927922,-5592212792181225336,-66887926030248626,5499035997484841553>()) {
                                 case -1156119490:
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
                        switch ((int)com.yiyiaddon.m.b.a<"s2g0m3jq166qzc","CLp7541GfQYMbdpg+gNB7j1SOUzi0p/nCf62cVoK/+8=",4872438770900241210,7922054825763742544,-8036568361997721110,-4941414936176532669>()) {
                           case 796159657:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sitf77kfm9zzf","GxhDaDgHn1yzVk7Ucmni0jJ/5gwe4HmCilq2PAeUu68=",5690641128610868770,-1870061479589175466,-1115240225569293679,-7734437443760034938>()) {
                                    case -146599324:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3rr7mi9caplfx","otuHiIadG6krza5dekdU/ii8YRnhxR0QJLv9Bqh5QQA=",5115276886355056937,2881825713312879076,-5247054391496578438,7652072742388963927>()) {
                                             case -1497804510:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3c7lx1efzuk8q","1I0zC1eovrrZNrrDwJDHg/x1nec8VVfgFzUYU0fTpco=",7243931965144734123,-4476361277096334159,4476841822943390676,7083644175184584069>()) {
                                                      case 687794559:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3bz49ya0jwgih","lMPEYGzHj266bdEHHsbgWJ6QlRKwJBSwzwLQYesmdjc=",-1932095663930972603,-3593080273560963612,-3918428132203025228,9024808124348738990>()) {
                                                            case 1386153436:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s1qj57vh9jk7ju","29Q4viH7GXo/oXcaMLO3YC++w/5qCLXq0ZlWu42UExw=",5935840339633625116,-3798474480912407040,-2720012784639289633,-920488471393448526>()) {
                                             case 1881075693:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1a6ta3x2l6qql","68cih4biKyDUbjy7jdWyQnBNWBkvYQPrlpMcESQciQg=",-1992427360911911315,307234516476314761,-1982572505618667167,2978351577560391410>()) {
                                                      case 508953488:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2pi9qazmsxa6j","H6IeMxMT+IDiMeLRvwyHkHKou9kfxsXta0y2bv4MKjY=",-3489716420444059746,4298337018732807163,8389246470510244244,4125221499930572343>()) {
                                                            case 763845520:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"s3d1s7pj8gsl6i","GQQBouK1uVXYfd+Ckxs+5ZbmEwNhe7W25L1AYjdxGBg=",-5402646609705323575,-6477207963668540810,310853587200429508,4113164579177034367>()) {
                                          case -61242120:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s4b06ik5z5z9","Z9Y9Nipb/A6+XFS+bs/BwHjlbY3TdwSqPs9p0vqKp60=",-6699176761977838267,-1814765996309251775,-3658736669371384815,8844317608230027280>()) {
                                 case 481409228:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2h2sihmdhkjh4","b+4gZnzPMDG8nW5n4j5TMEoELE+pBf8LqCdk6/bEzeE=",2431851460539413187,-7761453729291773369,-828635783153013650,-8934029621492478530>()) {
                        case 224738973:
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
         a.this.a(var1);
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
            switch ((int)com.yiyiaddon.m.b.a<"s1tbz2ah3sno76","eHsbPtLDpHCVmHDtIoomhr24npJfjthrOnTNz+AJ4I0=",-4329894840891898198,-4175514364022045402,-609772653280601035,6962915631499295514>()) {
               case 1941753479:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ugsprjvfrgmu","t7fVJ7PxQVoiEgGxdVUSNjuDelqhDUXlh2MJbWkQOO0=",5399536433405933976,354750586177983059,8100454087880752863,1431880078347054935>()) {
                        case 664222457:
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
            switch ((int)com.yiyiaddon.m.b.a<"s3hglrre90gy8s","Tkab6cLSi6WsNboYIyQvqnXRUGRgA7HTKyLtpXrdygg=",-5853414455580011550,8422034400783989277,211940606882125684,-8804331096995237440>()) {
               case -1855771287:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.d.d.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s18zsd0mv7aamo","xIhTYOdieew/D/HYQEEEoOZKvgnQmvi0BGNWRpHFcUE=",-3782175002085148753,3290368356653649427,-1540731681520890918,3285998859895689544>()) {
                        case -665135091:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3il59cbqkpcfm","jXcngVYwteX3jYvJT45lLeSlf02WjPlryt8NlPKqv74=",-7136752200384210949,713400642885842514,-9163847320575313543,2158664161630467506>()) {
                                 case -348165006:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s2cd8sj98q6cd0","2D/ueF7nRFeK7TapiW08tJNfl8pI5wl8oJZF8MV0JvA=",-5674828262000428402,-5078161306397980415,-145991188290660529,5301255769184796935>()) {
                                       case 731808146:
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
                           int var13 = com.yiyiaddon.e.d.d.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3e7c4spurumrk","qmhF9v0BwnR0R1AWJd0QBgLA9RbKAAZ8akIq042Wp9o=",3091161571080665306,-919283367408227786,-387910163971084310,555602834659262150>()) {
                              case -801492586:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2f3nygjr6tgxs","w9Xz4Fxv+8PX09zEmgoA/NkCqlNOEU/yTmzWYO3Ok9c=",2430654393198317771,-3667989491013579774,-1519730337723447365,-6824116486334952699>()) {
                                       case 328798711:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ay5vvd7aeu9d","mZOhwZXSMBjClCGrofblPfE7coEQJCJVJIHGpAf77k0=",-4890587557118662557,5274748656889055989,-8287658822021424887,6722248209138557588>()) {
                                             case 158802387:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s3mqspa7sztsvq","4v91n2M5V+wL/bR5KY1wtNp3lM59j3R4je5lY8pLwxs=",-5408791181250603541,2814858365985231477,-5533963015717583301,-8319178380845958270>()) {
                        case 2078215412:
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
      private final String[] s;

      private b(String[] var1) {
         this.s = var1;
      }

      private static com.yiyiaddon.e.d.d.a.b a() {
         return new com.yiyiaddon.e.d.d.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s3s1e81remqy5y","GATYmDsrVG5C2r8g1svSVo6SV8XZeUIlartM1g==",-5507399382439395314,3003192001265933700,2537623246043885902,7359864434144684612>(),
               (String)com.yiyiaddon.m.b.a<"s3s1e81remqy5y","GATYmDsrVG5C2r8g1svSVo6SV8XZeUIlartM1g==",-5507399382439395314,3003192001265933700,2537623246043885902,7359864434144684612>(),
               (String)com.yiyiaddon.m.b.a<"s3s1e81remqy5y","GATYmDsrVG5C2r8g1svSVo6SV8XZeUIlartM1g==",-5507399382439395314,3003192001265933700,2537623246043885902,7359864434144684612>(),
               (String)com.yiyiaddon.m.b.a<"s3s1e81remqy5y","GATYmDsrVG5C2r8g1svSVo6SV8XZeUIlartM1g==",-5507399382439395314,3003192001265933700,2537623246043885902,7359864434144684612>(),
               (String)com.yiyiaddon.m.b.a<"s3s1e81remqy5y","GATYmDsrVG5C2r8g1svSVo6SV8XZeUIlartM1g==",-5507399382439395314,3003192001265933700,2537623246043885902,7359864434144684612>(),
               (String)com.yiyiaddon.m.b.a<"s3s1e81remqy5y","GATYmDsrVG5C2r8g1svSVo6SV8XZeUIlartM1g==",-5507399382439395314,3003192001265933700,2537623246043885902,7359864434144684612>()
            }
         );
      }

      private static com.yiyiaddon.e.d.d.a.b a(com.yiyiaddon.e.d.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.d.a.a var1 = var0.a();
         com.yiyiaddon.e.d.c.i var2 = var0.a();
         String var3 = var0.am();
         return new com.yiyiaddon.e.d.d.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"se5ybznnisp0i","IJyMuuZzs0+XGEEmLAT4R7IUZs2f10ezOrjoxNTTjCVkynW2NDw=",-8714636637843795643,3983224171030393718,6783824505728606369,-8723184424807458235>()
                        : (String)com.yiyiaddon.m.b.a<"s28jckxyycxo2h","BN4meLOTP2yBlHo/UwnFpRn5dPbW7UGQX1Weqw2xYBiTx57kwwE=",874249091545657869,-3498964848055578717,-7285432593250471071,3889176749351041006>()
                  )
                  + "",
               (
                     var3 == null
                        ? (String)com.yiyiaddon.m.b.a<"s247b9zsradtud","CuAi5HOMFjI8N4FQZ7dTOMKJlAD04LpfjZZL+ejdx7Wlxg==",-3741764425436482639,3455368011280956085,3824711699143093574,4326805057885327771>()
                        : var3
                  )
                  + "",
               (
                     var2.aq()
                        ? var2.C() / 20 + ""
                        : (String)com.yiyiaddon.m.b.a<"s247b9zsradtud","CuAi5HOMFjI8N4FQZ7dTOMKJlAD04LpfjZZL+ejdx7Wlxg==",-3741764425436482639,3455368011280956085,3824711699143093574,4326805057885327771>()
                  )
                  + var2.B(),
               a(var0) + "",
               a(var1.ag) + a(var1.ai),
               a(var1.af) + a(var1.as)
            }
         );
      }

      private static String a(com.yiyiaddon.e.d.a var0) {
         if (var0.a().ab()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3otnxo7a7083s","QRLEZXYYptPmFhppESMoNuK9dUpKwey/B1Em+LQjxyQ=",-8980876278299510217,-3736374278896904114,5496373296449874537,745438149004878932>()) {
               case -1934464129:
                  return var0.a().ao() + "";
               default:
                  throw null;
            }
         } else if (var0.a().ab()) {
            switch ((int)com.yiyiaddon.m.b.a<"skiqc4hajus60","qQPHAwPGWjyk6hW77CjrG/CJYr+zDrfxLab76RgUeu0=",-2112423615180151617,-221306731083816473,-4592760107032717965,-2617830360962619783>()) {
               case -73564723:
                  return (String)com.yiyiaddon.m.b.a<"s358hdygrq21ke","8AXh6uW5JCmanDHq69nCIOfWeT1QfzrqWRZYklUSZIQmMbIyrKwALv9Cn4k=",-1934499029245575128,4261259632989594245,1122455483312222734,8358740018122717279>();
               default:
                  throw null;
            }
         } else {
            return (String)com.yiyiaddon.m.b.a<"s247b9zsradtud","CuAi5HOMFjI8N4FQZ7dTOMKJlAD04LpfjZZL+ejdx7Wlxg==",-3741764425436482639,3455368011280956085,3824711699143093574,4326805057885327771>();
         }
      }

      private static String a(boolean var0) {
         if (var0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1zmd2o6cl7z4e","VZbf2JzHu21j/Q61hR2+nJ2rvwpom/YZcWQWNrdHfAo=",-1500625773666078565,-4768094962238288787,1722031419328760010,-2669693534319206075>()) {
               case 2106049783:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s1iyw1cioq4mgi","JW/myl7X3FtDW5YBj7WvLsylVaH0u6314TQ7nBk3yMxMzA==",8391851400525477579,7796525353028993090,5087760866989236933,5687937375669376102>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1tp59pe5sfmdk","LD4smCyK4JPKwOg+pTDwNIxVcQRzdAzfa/EeY06+BIw=",7282238645140058351,7134752661926313732,-4082397553115010140,5469397086845730687>()) {
                     case -953313574:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s3gj6saale0snx","Z9mrZ/FbRM5NuRGPwdJNDEm1fwkulXcfkf2ULzy1e21cSg==",1900196522665780329,5520963099588225303,-8608106572255330585,6659621505128507246>();
            switch ((int)com.yiyiaddon.m.b.a<"s3ic3vufbqqr2g","YzN7PVCZqJlsG7q1IhMVp6nL4KhgUKuW+gzm7AAZMsA=",-742671910484149889,-1010243005773940310,-8319031021115729726,2131505854064780846>()) {
               case 1245534233:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      private String c(int var1) {
         return this.s[var1];
      }
   }

   private final class c implements g {
      private static final float Z = 18.0F;
      private static final float aa = 6.0F;
      private static final int cS = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.d.d.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sr3t7ohccun85","mHDb8d/YkyT2YdzqDYd08qc87CaDvy/wWQ+HI5LqnYI=",1520804971578286172,3121263914752004751,-3217504290095638135,6754756000032475198>()) {
            case -1052763610:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3oyo29pexc4yt","ZRaEjN/kU0J5X/QpXxTpiVXqYlcbYHGqC8DstxsJirA=",6970660541294505481,1930996549342245117,-5933039451304357658,5041189600736783745>()) {
                     case 1442085465:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1c3h6ksztns2y","GIU2+8QxQmprCnW7jBikWq4TBYsnyd9/T2DaP9fKWgw=",5631568372897248233,-6771527644696453200,-3024120332997984501,-1518494109945391956>()) {
                           case -1610739133:
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
         (String)com.yiyiaddon.m.b.a<"s3s3trehteb23x","NH4z7CbbZFevBZHX799kbRH9diBMA2TeZ90hI6pDzuA=",-5123224796071332407,-1184512449270438724,-2486307554898217538,-3942728773324462458>()
      ),
      AUTH(
         (String)com.yiyiaddon.m.b.a<"s4qotf6a7o3t2","GBHmuCLL3cPzAqJHo8GTk0apiDaloZYKU5DNaBnx31qDS1E7",-7724017095057335035,3867980946121542043,-3929540431292702456,-3497633431038860781>()
      ),
      RECONNECT(
         (String)com.yiyiaddon.m.b.a<"s18ie2sjkzun7q","pVd1fWMkAWqP2qnzqLcaTbQUVLtDdBXGfZyWDP5Vqohx0Ftm",328507682111860016,1322259500607177856,-6777854561968621849,9103389806706620844>()
      ),
      COMMANDS(
         (String)com.yiyiaddon.m.b.a<"s3mf21u2ka9g5w","jJToLRzvCz6TO+y8cEYlZparzlCoRasen0zlBSZKLlRCxr0dSuNlWw==",-671402658444334341,4079089543534386203,7967388503297067018,-1707581953291174726>()
      ),
      ROUTE(
         (String)com.yiyiaddon.m.b.a<"s3lj1zrin1wgwx","JTCIghOHVk6lkPZ1Te5BEc76Sy90RwtRT0c42hKkgX4/UfCw",-8315145171301603476,-8365755910028897194,-4396799273983438437,-7375145280518564646>()
      ),
      LEYUAN(
         (String)com.yiyiaddon.m.b.a<"s3e5my1ky3atle","ETWKpvaymz4V/RBarJ+vCoMhG1CbR2mUgWMSkLRHoOwcWk1S",1765064359124464628,-7371432809627103419,-5743442104639651264,-1717432225633308175>()
      ),
      ACCOUNT(
         (String)com.yiyiaddon.m.b.a<"s2a5cu6wu9tbq3","bCkjX89Xy5cDpnuYQuDjcMVyrySrFLNM7QI5EmdA6bVKxl24GoI=",-6631904788167260600,8118292134950170953,7968600468687405920,701404481476050221>()
      );

      private final String fs;

      d(String var3) {
         this.fs = var3;
      }

      private String D() {
         return this.fs;
      }
   }
}
