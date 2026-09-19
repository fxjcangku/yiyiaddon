package com.yiyiaddon.e.g.j.a;

import com.yiyiaddon.e.g.d.j;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.j.l;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

final class h extends com.yiyiaddon.l.h.f implements com.yiyiaddon.l.c.b {
   private static final String kp = (String)com.yiyiaddon.m.b.a<"s3dkleqzyvp6iq","2psDPBWdpEMo3XJsLX/9ZO2SzypD9C9H4uajkfyCRuiGyonv5SvY7uzSxhbH4jxXrpPZx+U3KSok8rMg",3742260772400056234,9112732506433028719,8457289183405808243,-3558766837282959493>();
   private static final int eE = 10;
   private static final String kq = (String)com.yiyiaddon.m.b.a<"s2u22fsuhn00ti","S4zAUPlHwiXAABrHz3pbnLOKWyOsoQbNkQeA9g5fTSRJSSTg5JIm4w==",-1041792859841137642,4258919144170935997,-5590364718391707516,502891101134840631>();
   private static final String kr = (String)com.yiyiaddon.m.b.a<"s2edv90hh0d9sl","G/295AZGjANxgTJHyoMDhXIRKzrD4VeLAVQdGRqBiD0=",-6358061096407286423,-240571057662619430,4109269080914724400,-3979372227909774759>();
   private static final String ks = (String)com.yiyiaddon.m.b.a<"s4qy9sq3pa1z8","EicknC65PDWow3GfRZ6gW0/0pvOlxVNjiSjOuqiVuaqA/O/6fYm8chuMLBY=",-714473392012891341,-7450028016563700619,-8651354840128295169,-5537573554312890175>();
   private final com.yiyiaddon.e.g.j.a i;
   private final com.yiyiaddon.e.g.a m;
   private final Set<String> u = new HashSet<>();

   h(com.yiyiaddon.e.g.j.a var1, com.yiyiaddon.e.g.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s1gp3rz6sy1tfr","lxBlTqJ9eslEmVisQ4gKCcCnN6f4EhA/bGKuyi7aFelsXR0hLIZAWtBkr7w=",-3566683896549007482,1467299407091380254,2751874453881203315,-5412347295998997929>(),
         var1
      );
      this.i = var1;
      this.m = var2;
      this.u();
   }

   @Override
   public void a(String var1, float var2, float var3) {
   }

   private void u() {
      i var1 = this.d();
      var1.b();
      com.yiyiaddon.e.g.d.i var2 = new com.yiyiaddon.e.g.d.i(this.m.a().aw);
      String var3 = var2.aF();
      j.a var4 = j.a()
         .a(
            var3 == null
               ? (String)com.yiyiaddon.m.b.a<"s2ttypg4nglu9f","vWUcnmpFnJBSaFnde4R7tqbrqg7PKH0bSsThog==",5874974408340552741,5457521787608571945,4208909639522030013,401945759349361525>()
               : var3
         );
      var1.a(
         new com.yiyiaddon.l.c.f.b(
            this,
            () -> (String)com.yiyiaddon.m.b.a<"s1eyzzzsxt6e46","wDc+T/AwpFHdm92M7VHyqWkrOMglR1cF+tVsFQVWLDA=",5907371977192635448,-2912234009243931389,-7446128724813428454,-9090197626367842539>(),
            (String)com.yiyiaddon.m.b.a<"s3dkleqzyvp6iq","2psDPBWdpEMo3XJsLX/9ZO2SzypD9C9H4uajkfyCRuiGyonv5SvY7uzSxhbH4jxXrpPZx+U3KSok8rMg",3742260772400056234,9112732506433028719,8457289183405808243,-3558766837282959493>(),
            null,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     var4 == null
                        ? (String)com.yiyiaddon.m.b.a<"s391w066ee251c","Hl5JjvNbFFOGKfrbzwxHU+WjCSyqWmuQ93+jB40yV40RFM3r",-652069170651142163,2796000487857355165,4961873597485422155,-5296821280347358183>()
                        : var4.name,
                     this::cI
                  )
               ),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.b(
                        (String)com.yiyiaddon.m.b.a<"s31atg78mo118q","VlYPPf/fa0gOH36WIxQV7DwGG48WRFBjesISUR2b",-7251156208226018979,6313316022498444296,5966185713899675475,5618252285303304214>(),
                        this::cJ
                     )
                     .a(() -> this.m.a().aw.isEmpty()),
                  (String)com.yiyiaddon.m.b.a<"s1jhlnxv2wu92q","GZX/T1AMan/iQUcOdj7f7WxeTTXcpAA9NMUqdeUtLqTov8PhNa0eoCR24xw=",7986135658417397909,-8220148889117648473,-4811108519492097273,3054700433740051477>()
               )
            )
         )
      );
      if (var4 == null) {
         var1.a(
            new com.yiyiaddon.l.c.f.e(
               this,
               (String)com.yiyiaddon.m.b.a<"s3bsw8mrmykopb","jrNfddbi7rTrk5xZh9RlOvbbPQbicAuuaUq6/LTZ7BLrqpXHM/SW+YxgnLu9c5XXlKkVcZZ1HtGaZKtx4sCu+ew8pDzBlUKNiEW1+JsIgKvJUzqKiBZM7qtuA53hFWN4",3566332887628537706,-918083462912997411,-459689740685427769,-2363238450182872536>()
            )
         );
      } else if (var4.profiles.isEmpty()) {
         var1.a(
            new com.yiyiaddon.l.c.f.e(
               this,
               (String)com.yiyiaddon.m.b.a<"s2refcoqurtsij","gFCKuBJr0IxSA1g+EaXzZUIwi/bBoPohLuzJRmGYfze0K8weqQsm7EFVUe2nMD+UltR/cQ3vJElG7+oYC2HPOX7pN43WdcvFVRVWfb6nSfhTJfD5c0k=",465830694295972298,-7725109296886723950,-5556138031031404122,5821685806246854645>()
            )
         );
      } else {
         com.yiyiaddon.l.c.e var5 = new com.yiyiaddon.l.c.e(
            (String)com.yiyiaddon.m.b.a<"s2ttypg4nglu9f","vWUcnmpFnJBSaFnde4R7tqbrqg7PKH0bSsThog==",5874974408340552741,5457521787608571945,4208909639522030013,401945759349361525>()
         );

         for (j.b var7 : var4.profiles) {
            var5.a(() -> var7.name + "");
         }

         ArrayList var11 = new ArrayList();
         if (var4.profiles.size() > 1) {
            var11.add(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s391w066ee251c","Hl5JjvNbFFOGKfrbzwxHU+WjCSyqWmuQ93+jB40yV40RFM3r",-652069170651142163,2796000487857355165,4961873597485422155,-5296821280347358183>(),
                     () -> this.a(var4)
                  )
               )
            );
         }

         var11.add(new com.yiyiaddon.l.c.f.c(new l(() -> this.a(var4), () -> var5.a(() -> this.a(var4))).a()));
         var11.add(
            com.yiyiaddon.l.c.f.b(
               () -> {
                  new com.yiyiaddon.e.g.d.i(this.m.a().aw).L(var4.id);
                  this.m.L();
                  this.i.C();
                  this.u();
               },
               (String)com.yiyiaddon.m.b.a<"s3l53irzwpvqlh","sEyurakVfm2sWiGv4yFrXUrNXwSxdTSs8fpyxfreRgcx8omK",5418261639776124026,1568669033082921651,-1190798817305855815,3759252325142638394>()
            )
         );
         var1.a(
            new com.yiyiaddon.l.c.f.b(
               this,
               () -> (String)com.yiyiaddon.m.b.a<"s3l53irzwpvqlh","sEyurakVfm2sWiGv4yFrXUrNXwSxdTSs8fpyxfreRgcx8omK",5418261639776124026,1568669033082921651,-1190798817305855815,3759252325142638394>(),
               null,
               null,
               var11
            )
         );
         j.b var12 = this.b(var4);
         if (var12 != null) {
            com.yiyiaddon.l.c.f.d var8 = new com.yiyiaddon.l.c.f.d(
               (String)com.yiyiaddon.m.b.a<"s1v0xfov74h2p6","Mh+2rYRHhmBS0/Zfp2Cq7WqsvbRgfCMYWQTBGiIVfTXLJJ5J+TWQ8DTdl00W2QGiR3WCXZoQAwUSvNyUqVYooy5d",-8539676157769353209,-6097550284339288006,-456394383234993288,583757240312785727>(),
               var4.id + "",
               this.u
            );

            for (j.d var10 : var12.targets) {
               var8.c().a(this.a(var2, var10));
            }

            var1.a(var8);
         }
      }
   }

   private String a(j.a var1) {
      j.b var2 = this.b(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dups13az495z","fRQUlVCSK1npkZk9+HrCF9oH1rf4VbrfuGv/MyFSvIw=",-1571953035508577277,-8307697843774739451,2501579334148779051,5688163601325035523>()) {
            case 939368249:
               String var10000 = var1.profiles.get(0).name;
               switch ((int)com.yiyiaddon.m.b.a<"s1phufooo0a4kw","c8tQC+cthfRbA5BzO4vuqKnQ5AcL/ENJcni+6gAUU8o=",-6980581541356791651,8278125943738029979,8247050682311854958,2285402300573029272>()) {
                  case 1616645395:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var3 = var2.name;
         switch ((int)com.yiyiaddon.m.b.a<"s3gyb0groirwb5","3w/MJHyhx1C1yVSwGKM/JIofHzFcyWzASgrzvLVx+dE=",-7008540575813165824,1485629737860210089,3471193275110295364,5816002092015400944>()) {
            case -1927748924:
               return var3 + "";
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.l.b.g a(com.yiyiaddon.e.g.d.i var1, j.d var2) {
      boolean var3 = var1.A(var2.id);
      ArrayList var4 = new ArrayList();
      if (var2.excludable) {
         var4.add(
            new com.yiyiaddon.l.c.f.c(
               new com.yiyiaddon.l.j.a(
                  var3
                     ? (String)com.yiyiaddon.m.b.a<"s1qpse043iqlas","GTPNKzXn/r/ZuM8cVj9CHudDGXZ0fcKQKOnHWzIe0WKnZ0FhdWg=",-4822630535793736918,3532378340087320402,-1294598199056955118,-2636420971733111290>()
                     : (String)com.yiyiaddon.m.b.a<"sg3h913c82r33","VEFJ4rW8xfgUy4L7TEbJrZiAAhm7DJJsEym0Oq/TNPoMkiCd",-1269636254838343693,6366307019867234677,-6692101307575907943,-2163255030058748124>(),
                  () -> {
                     String var10001 = var2.id;
                     boolean var10002;
                     if (!var3) {
                        label15:
                        switch ((int)com.yiyiaddon.m.b.a<"s1yq9qj2ihd0h5","xel9B7TSCLLZMdVtd5udZ0vuuRGRseQgwLkjBARzVhs=",1529314350871854151,469253599201527441,-8461598521942369152,967486582785811302>()) {
                           case 1513248194:
                              var10002 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s1ka0c77xost72","HocEpYQe5N+uyHJyEaS7AufxN39TpuqfNzbPYwAiQWI=",5390509993228824294,-7066553449328252307,835551370341535365,-4990876734416908207>()) {
                                 case -1094588921:
                                    break label15;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10002 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s1bmuw0vyqopc","/BGOSKTVrK5DnhHA3LgDNZgDJrwpfGMAYNU/Pm1hB9U=",3883669130868176570,8468286451470659626,1108751696592080968,4695706613895772694>()) {
                           case 555561582:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1.g(var10001, var10002);
                     this.m.L();
                     this.u();
                  }
               )
            )
         );
      } else {
         var4.add(
            new com.yiyiaddon.l.c.f.c(
               new l(
                  () -> (String)com.yiyiaddon.m.b.a<"s330ppqpzx7j1h","4lQ06qqbHVU+RIJy6IoI+PFjNMwB5jjPuBZoFJ4raSe2v0Zx",-817714673083687893,7712100152813016112,-3291173719677427486,-1386871561372287313>(),
                  40.0F
               )
            )
         );
      }

      var4.add(new com.yiyiaddon.l.c.f.c(this.a(var1, var2)));
      var4.add(com.yiyiaddon.l.c.f.b(() -> {
         var1.d(var2.id, var2.level);
         var1.g(var2.id, false);
         this.m.L();
         this.u();
      }, var2.name));
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> {
            if (var1.A(var2.id)) {
               switch ((int)com.yiyiaddon.m.b.a<"sfbl4e1zmrusx","qsw2z9RgdQOLfR21ar9gRr/wsKbSaTrejZeEzzNXqp8=",-2745006839829344754,-6534880591093006190,-7382591398068616622,3022626680565147086>()) {
                  case 1479064479:
                     String var10000 = (String)com.yiyiaddon.m.b.a<"srfe7pgqkjum6","o67Dx2k6DCBTjXHmknAcsrzUxgcf75zETzmqkNkStiY=",-5636189852063132680,2784949596450103726,-290378968968432104,-6697545025153833934>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2fptfi4i8luea","qkviiuwGkkShTWSFhvsoLCBoLVBgWbbM+q1tqp6Tc1c=",-8953544756439159277,8969684718668653717,-1975329393693730865,5750843866351198059>()) {
                        case 844811611:
                           return var10000 + var2.name + d(a(var1, var2));
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               String var2x = (String)com.yiyiaddon.m.b.a<"s33fcsjydluk03","KFkvCKmE4q4OryVscWiITEAWnRNuXT3Q+LNrZxtsh+g=",-3590985539157770740,-983271598023558758,-8432539013794473130,-3071496232632381895>();
               switch ((int)com.yiyiaddon.m.b.a<"s29hd5ujq9h6om","yB4Tu41R/Pkm5NgUDvtzc41sFUnj1WUxp0sxJvlYsoc=",-3000042809174345550,6856235882822726636,-4973873849361333449,-4201130556097337708>()) {
                  case 1288691187:
                     return var2x + var2.name + d(a(var1, var2));
                  default:
                     throw null;
               }
            }
         },
         null,
         null,
         var4
      );
   }

   private com.yiyiaddon.l.j.i a(com.yiyiaddon.e.g.d.i var1, j.d var2) {
      int var3 = j.a().e(var2.id);
      return new com.yiyiaddon.l.j.i(
         1.0,
         var3 >= 1 ? var3 : 10.0,
         1.0,
         (String)com.yiyiaddon.m.b.a<"s31jyfyjedk135","AYM8qqsu1Av6XTbKpvPRYYfSM8SVkS4mIdx11SXqH3eYnq1t",-4280413617490862013,5888789756526901841,-4861355539516518561,-4075300145126695240>(),
         () -> (double)a(var1, var2),
         var3x -> {
            var1.d(var2.id, (int)Math.round(var3x));
            this.m.L();
         }
      );
   }

   private static int a(com.yiyiaddon.e.g.d.i var0, j.d var1) {
      int var2 = var0.d(var1.id);
      if (var2 < 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rrvm7mjnjas3","YDqGniC+McT4bZAZ5rOEUcQvgcDaMrGV2x1FReVB4BI=",-199147618040150877,8931412221962377979,-7077275775367674062,2488205779587886871>()) {
            case -1091632402:
               int var10000 = var1.level;
               switch ((int)com.yiyiaddon.m.b.a<"s1j2hbq5hya7y6","o2vzkOm5iQnnBcixIv43rkZSYeOQxalLSMjPkR92IPw=",-839784239068546780,8472883321777075293,4675751528144874058,4051706487300019493>()) {
                  case 2113557497:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1v3dvaps2biph","z/mFeDsjZ5bEpj6+EGrF+dNfPrlKhN3D5K5kV+29c4g=",-6535368383790614210,2472713174873126383,-6551866489679811843,6977709061559094160>()) {
            case -253755952:
               return var2;
            default:
               throw null;
         }
      }
   }

   private j.b b(j.a var1) {
      String var2 = new com.yiyiaddon.e.g.d.i(this.m.a().aw).aG();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"syxac8043b9he","FpmUMPrKFhOn/sTfGBhtRwfYCH+VlPtkEqoX76M6pKs=",-3301344462418462503,6068516326608700761,8825919126418269325,-7849649992142961080>()) {
            case -1336435597:
               return null;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var1.profiles.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s34sa4hxwni08u","neZO8oTEh37NGyYKfBdH92HO7Viyk0P8olw6NSMWNkQ=",-5473135957615065038,-4097605565633746987,-2643465116170919025,-1979279535251866929>()) {
            case -39250900:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sw69foc5496f9","SS1Fqn63/8wRTR3pK9KBZcC2mFYQ5IeqEDv6Rv0ZuBY=",3279136825607033417,8214632464588419162,-7567475587371076002,-7746188299612872099>()) {
                     case 1438341742:
                        j.b var4 = (j.b)var3.next();
                        if (var4.id.equals(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2p1n8op2ip8dg","4dVZ0i2L8ck2Y5PU1bJH1Ki1svOK8NqWxl1W/Qz5o1U=",517379506155122766,3480139384683943576,-2565672168146827468,6831491022195974822>()) {
                              case -1611234301:
                                 return var4;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2gr2zpyv8hjx9","m+LzaKwFG1+IrXJpJ7XN17QfnOODMN2vTOWM83UraJs=",-5692707452386272324,-522214948284292488,-8973493379352422618,3158109815263341138>()) {
                           case 1131176270:
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

   private void cI() {
      ArrayList var1 = new ArrayList();
      HashSet var2 = new HashSet();
      Iterator var3 = com.yiyiaddon.e.g.d.h.L().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s23bqwmhlql96o","LOZ0OIKhGf3GJqVnBZe4x/18MtAEveb/ir8MNFeEIdY=",5805422420692559135,-448558428350241877,-5399857162323855651,473412935443105883>()) {
         case 1891986911:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sshvf77xg09s4","XcJcXeyZe/4P1DRKbChNJ5hFCBTGu7rIUHcwf/37tc4=",-8194938564765561324,773104924366939062,-4758686934851321028,8282083141129961438>()) {
                  case -1547400607:
                     com.yiyiaddon.e.g.d.h.a var4 = (com.yiyiaddon.e.g.d.h.a)var3.next();
                     String var5 = var4.D() + "";
                     Iterator var6 = com.yiyiaddon.e.g.d.h.a(var4.L()).iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s39841a0u7f0ea","had7wJE7iI2tNF1TikRwYay4es6eWhe+7aYMZ/Ck+xI=",7386046045493049254,-5648247755397569377,7492213392417310827,-2766833349185174190>()) {
                        case -1136822450:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s30sjclg1tgkbd","27i7BQDx8Z/oCM6Ez1I2hjTEKAbROMgnii/d+cBigpA=",1151815793027274927,-3164440372638730276,-6278040556230520900,7117910785322392749>()) {
                                 case -1029998087:
                                    com.yiyiaddon.e.g.d.h.b var7 = (com.yiyiaddon.e.g.d.h.b)var6.next();
                                    String var8 = var7.D() + "";
                                    Iterator var9 = com.yiyiaddon.e.g.d.h.a(var4.L(), var7.L()).iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1vdrpmvf8uaf6","iloOBFJgwVddxa79KB+AnsB3k/+ekLJsJL9HWKfEAew=",-4638262209991168577,4945558052402621702,-2938676472805284651,8562617419705578281>()) {
                                       case -1536061380:
                                          while (var9.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2niykmnazbyoj","TCTgKTGM9Fndkg4LK83nEIs3dbO/MpLvWGb3pGYikIE=",7216588568359227834,-2254854244099665967,-5567036662878675153,2976476239793769639>()) {
                                                case 739524739:
                                                   com.yiyiaddon.e.g.d.h.c var10 = (com.yiyiaddon.e.g.d.h.c)var9.next();
                                                   com.yiyiaddon.e.g.k.c.g var11 = com.yiyiaddon.e.g.d.h.a(var4.L(), var7.L(), var10.L());
                                                   if (var11 != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"svj04qh47xki3","PXsIIiy2ms/r8Sh9OsLhKmxGcwI5I7CwrmECMAxQbR4=",3225028780438028331,-8934960463263720926,-2065464024010356720,-1250238855965040133>()) {
                                                         case -1710703049:
                                                            if (!var2.add(var11.be())) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s29hx74iralox4","Ixg+w7H52uglkCM3NYBpAu2E1tOMbxAnbRrQRfDRyk0=",4992685806917173843,-5210865906459720081,-8257083688844463509,-7532995212820487358>()) {
                                                                  case -1965868037:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1ea69o6t7bqgx","U4S38xjQHlbR08r+7VsbSLbB01KigET6f29VvBXgF9k=",3847059021232872335,5011729384689725421,-7547793432614060348,-8840149501782334487>()) {
                                                                        case -1725803217:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               var1.add(a(var11).a(var5, var8));
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2re5on33olekx","0xYZL+oUzDQ9cE1hvQc3fH5k1knbyIaimvLcNNCzM1Q=",-5551466968493194005,723664075192826654,1790277976727451901,5847793102171541587>()) {
                                                                  case 225290058:
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

                                          switch ((int)com.yiyiaddon.m.b.a<"symp3g8x3yt1m","ztEyqfgoi/O2vvtaN3JVqM4vUvMASGL0Shr9Jt6jcpE=",8403528022160281114,-4485744277095321199,-4651010310817148342,7523525605046857983>()) {
                                             case -231165322:
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

                           switch ((int)com.yiyiaddon.m.b.a<"s17to45mwkrorn","655y1lgB3i1fI+PBtGvhzPCV4rYM0IKopgZjxUMJCzE=",6287482491391683810,4037648114819323971,-4713738450509660911,-4789036087390847692>()) {
                              case 414456664:
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

            var3 = com.yiyiaddon.e.g.k.c.a().N().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s1stb63gzwhqre","j4OdHCSOj4Oup9+cvXCpQoOBHo51zntzXSWEo/v76RQ=",4805209124996296283,5566392788118974013,-8757308718053557528,-3147114794588411881>()) {
               case -380263453:
                  while (var3.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"st9v3yk1icwos","SvykZ+bf4CmizPd5Wl+kGUiFy/27iF7Lz5djtnr3h8Q=",-7131398251952833612,-7270070843460983820,-2524071409163085524,8779406117502974884>()) {
                        case -312183717:
                           com.yiyiaddon.e.g.k.c.g var13 = (com.yiyiaddon.e.g.k.c.g)var3.next();
                           if (var2.contains(var13.be())) {
                              switch ((int)com.yiyiaddon.m.b.a<"slu16vwbljp51","ioTRvFmbbcw/WnT+XdwUzEBr1jIBTawA6AJcaczJb6c=",-7611685359157706418,8641797343021250522,-4238080185451322077,-6595655981709594460>()) {
                                 case -1959749224:
                                    switch ((int)com.yiyiaddon.m.b.a<"sf049i7jr6voz","uzIbmbifvtyM+7LZnbYw+Vogh9S8vOsIXwwJ2hcNxXw=",3675015503317042004,-4366813877815105696,7944296344657191903,2385432952942668869>()) {
                                       case 1469651235:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var1.add(
                                 a(var13)
                                    .a(
                                       (String)com.yiyiaddon.m.b.a<"s2ttypg4nglu9f","vWUcnmpFnJBSaFnde4R7tqbrqg7PKH0bSsThog==",5874974408340552741,5457521787608571945,4208909639522030013,401945759349361525>(),
                                       (String)com.yiyiaddon.m.b.a<"s4qy9sq3pa1z8","EicknC65PDWow3GfRZ6gW0/0pvOlxVNjiSjOuqiVuaqA/O/6fYm8chuMLBY=",-714473392012891341,-7450028016563700619,-8651354840128295169,-5537573554312890175>()
                                    )
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"sbrfrptvu4wea","hiJIUTqLZywFM5PfcpODkaViV6WBh5hsH/dX1wsoc4g=",6750185512397511940,-488221672100644299,-2712348883377996202,-8730707774443460442>()) {
                                 case -617997524:
                                    continue;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s10s2l43mwff3s","/rFOJwPNtTx2z2Ed6nakS4s+kBDjLQPW6FO51kzEuKQ=",6272878970400291226,5230516023040486049,-1422998093075291102,-3452759732859176758>()) {
                        case 477353027:
                           return;
                        default:
                           throw null;
                     }
                  }

                  c(
                     com.yiyiaddon.l.h.g.a(
                        (String)com.yiyiaddon.m.b.a<"s2y6q4ctd4xx24","J7IJ5Dmpekb9DIlr1Pom2Nb1fladiRtWypLsuVamfVV1oY2v",-6034185741440574714,426863331923100411,-3411732097709667413,3302973542684473863>(),
                        a(),
                        var1,
                        this::L
                     )
                  );
                  return;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private void a(j.a var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.profiles.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sz55l8xbdotg6","gUcb8zF6L/zDmPXNXK3YXPtt2qTX5kR4iSW/P/AlsAQ=",1875797992716685701,68148865120739386,-2596819003026423176,5019518817903877540>()) {
         case 507776580:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1mu4ei6losqva","u6wtr79VDHRJc7JH0Bd5Ru90cCZt7y9JjPZXmMcOvx4=",-7309202612906635476,9103244807646217390,-1985659728513385602,874055543953017927>()) {
                  case -697844640:
                     j.b var4 = (j.b)var3.next();
                     var2.add(new h.b(var4.id, var4.name));
                     switch ((int)com.yiyiaddon.m.b.a<"s2wcf92ykntvzi","BUituXTMrKGFQYqM4FhzQA5wauB4aBGa2IkB9tMGO78=",-1656468593510047702,-8320534889249468482,-6574134657143457922,-6069203324646421388>()) {
                        case 572943489:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            c(
               com.yiyiaddon.l.h.g.a(
                  (String)com.yiyiaddon.m.b.a<"s3l53irzwpvqlh","sEyurakVfm2sWiGv4yFrXUrNXwSxdTSs8fpyxfreRgcx8omK",5418261639776124026,1568669033082921651,-1190798817305855815,3759252325142638394>(),
                  a(),
                  var2,
                  var1x -> {
                     new com.yiyiaddon.e.g.d.i(this.m.a().aw).M(var1x);
                     this.m.L();
                     this.i.C();
                     this.u();
                  }
               )
            );
            return;
         default:
            throw null;
      }
   }

   private void L(String var1) {
      new com.yiyiaddon.e.g.d.i(this.m.a().aw).L(var1);
      this.m.L();
      this.i.C();
      this.u();
   }

   private void cJ() {
      this.m.a().aw.clear();
      this.m.L();
      this.i.C();
      this.u();
   }

   private static h.a a(com.yiyiaddon.e.g.k.c.g var0) {
      String var10000;
      label36: {
         j.a var1 = j.a().a(var0.be());
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1csgjs2po102t","hVzin3huy9AAt/y+bmzQ/OfIffuGZ2u9pKL0AXjSMMQ=",-6759890662103052066,4262501923797065346,-6986584015125668031,-1186972876056587733>()) {
               case -707511303:
                  if (var1.name != null) {
                     label25:
                     switch ((int)com.yiyiaddon.m.b.a<"s12pfcj9oltt7d","gROERB8v0BT4xqE65Yzr1KjsV/oxwnxxMTTH0ooybwk=",-6895158997035746926,-4653482882427399071,-332069269358033928,-6297833099848815459>()) {
                        case -22545579:
                           if (!var1.name.isBlank()) {
                              var10000 = var1.name;
                              switch ((int)com.yiyiaddon.m.b.a<"s2fjtpm0bckeeq","2UDLzYl5bqUvvSQvzcTvEqAnTVp61vqkz2DfWb3KBlE=",-1034068493331678260,-7627939535334757540,-8326630001670172323,4631579925574159339>()) {
                                 case 1216225174:
                                    break label36;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3qkatsj6gefv5","hHVtZcdVWp973YnwvlUhWQB5xTkbzvwtYvww7nPRKmk=",-8128695485039772854,-3271635294520627389,-4378696068548891470,919461842311169505>()) {
                              case 1068400823:
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

         var10000 = var0.be();
         switch ((int)com.yiyiaddon.m.b.a<"siruexug96f31","1ifV95LIOcGMv1+z7XTQG550StJbT0CGEnmvA461t70=",4214588370284668260,-6451410854427526875,-8735083622906577240,-8506194588477176605>()) {
            case -1369687293:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      return new h.a(var0.be(), var2 + "");
   }

   private static String d(int var0) {
      switch (var0) {
         case 1:
            String var5 = (String)com.yiyiaddon.m.b.a<"s2jqrckot665f5","aCBhuRQfz9uCw/Iadu3EL9h1jo5ugFpdQQ6UxQpR",-4330298469980487508,3532371801658828014,-8930953963781976163,6942204654966764047>();
            switch ((int)com.yiyiaddon.m.b.a<"s2wunnlpu3hh0a","Adrs5DXcmgETChLSBWUZ16HpaCcoC5Poe5TTl5SHSXw=",-5163327991190639424,7237816504466996048,-1302943300712662017,557870274851841926>()) {
               case 477907508:
                  return var5;
               default:
                  throw null;
            }
         case 2:
            String var4 = (String)com.yiyiaddon.m.b.a<"s1c7s5fo2hl1eu","Nrgn4VKbgq9dO7rY3dIIPhasd3syTbRMo6svE2/q0Ho=",7320828582256128948,4088079397112172011,1460840799670701131,-7896520094429794478>();
            switch ((int)com.yiyiaddon.m.b.a<"s2g31w5js8rv93","L8r4giqY8ajbdxXdM6MDGElkO3hQ48klxbISOiyrXeA=",5206416969664226201,6156123591528130503,-1749122500283405481,7672150045930649851>()) {
               case -631776170:
                  return var4;
               default:
                  throw null;
            }
         case 3:
            String var3 = (String)com.yiyiaddon.m.b.a<"s2eiqheze8c0t3","lzJFr52evKRrighKZtIa2e90pb+WkJ8TlgHQFZ92StEDMQ==",4554516550531406783,8319548146194647394,5347501541449697284,2630314227482771337>();
            switch ((int)com.yiyiaddon.m.b.a<"s3954lbswn09rz","+etvIrEfHSwCiH/vr5oujqLvxb57pONEyOjgvZ56u0o=",-8629729514305107490,-7949107820215074085,-2645098495553111409,-8407281691864275757>()) {
               case 849619233:
                  return var3;
               default:
                  throw null;
            }
         case 4:
            String var2 = (String)com.yiyiaddon.m.b.a<"si8fgl0h6uns7","Og/cCBZktW/RUpHqpjNqHCwWLjKFjIzFOyTNSj0mIKc=",-2598546022299415549,-6083687933988677338,6401185568830932507,994671642765440500>();
            switch ((int)com.yiyiaddon.m.b.a<"s2x385kpokjj3k","SiyZuCKftQ0MNXox3QlRu3UEzS1Yoa8nmaZCwkSR4H0=",-2846010135597209712,1032306604071498209,-5531025392629136532,4752359038248965944>()) {
               case 365292959:
                  return var2;
               default:
                  throw null;
            }
         case 5:
            String var1 = (String)com.yiyiaddon.m.b.a<"s1vlo1gs70asrr","E/VkW7QMUG/TTFHQhwr3bHoyG4HqL4pIwvOXozGC",-8859769802601343557,4723893234642377940,-6228637214753128007,-9213218982949909307>();
            switch ((int)com.yiyiaddon.m.b.a<"s3trsga8tz2xs4","DP3Q1H7nBI844NiodFc7J2tq+TbUkGcWj0nLpsr+0kk=",712814828278276859,1922747836546998104,5522689892122174822,-1915389918828775271>()) {
               case -1717096265:
                  return var1;
               default:
                  throw null;
            }
         default:
            String var10000 = String.valueOf(var0);
            switch ((int)com.yiyiaddon.m.b.a<"s1923evkrmu382","aJtQxvk5wKwa2Ys/OPIUiKZ/WVU+/uDGlia05nYOXe4=",5446889602880560527,-5399639179314249915,-7519770276487139783,-6286596345256671936>()) {
               case 736263776:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   private static Screen a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2aln1r5zriqjr","qUmk865Taa4doU6wzQvfVhK/l3cfAszjDai8XhjPUvY=",1966873287515935048,-926761156525042993,-3039387892387415422,-2506615410378172206>()) {
            case 2013137007:
               switch ((int)com.yiyiaddon.m.b.a<"s26jl6faz6s5l6","+x8O1oMM/NUEhINn9dll18krp7f3MRibv2xKDELtjNo=",8186986311313793880,-9051905754265274819,-3930919120901392786,4527537689532204289>()) {
                  case 1682345774:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Screen var10000 = var0.screen;
         switch ((int)com.yiyiaddon.m.b.a<"sqquweerzdcye","Hyp+p5vAVPA+UHtX4jIg11Q5s37ohnOzvBXEtsz0gFk=",-6224101490836734224,2820335167799669868,6873966229132183746,-2145110840940824953>()) {
            case 1503012342:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static void c(Screen var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uicnuk52cp26","xkVxWktkfPar6Fl2YJXDAQ4/gEF1HslIZo2j51+rdrc=",-7526816937592850315,7841908970095892846,-8946286595676819759,-403170257786903517>()) {
            case -1728679416:
               if (var0 != null) {
                  var1.setScreen(var0);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s75zbrl7it8jz","v/+pl9HXiNzRfdTH3zxsSN2hXDfVMNNFw7SfHaxYQ3Y=",7794707794537192774,3599818054737618998,-1081256277593034599,2854835884543023917>()) {
                     case 572257050:
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

   private static final class a implements com.yiyiaddon.l.h.g.b {
      private final String kt;
      private final String ku;
      private String jg = (String)com.yiyiaddon.m.b.a<"s2v4wlp50ycxvo","VS+wlwWYI8Ca56JFU4lVDkyR7TrEOvaohQ4KLA==",9021399732685415276,-5978680136909409298,7014374125953824568,-9095116811419386904>();
      private String kv = (String)com.yiyiaddon.m.b.a<"s2v4wlp50ycxvo","VS+wlwWYI8Ca56JFU4lVDkyR7TrEOvaohQ4KLA==",9021399732685415276,-5978680136909409298,7014374125953824568,-9095116811419386904>();

      private a(String var1, String var2) {
         this.kt = var1;
         this.ku = var2;
      }

      private h.a a(String var1, String var2) {
         String var10001;
         if (var1 == null) {
            label29:
            switch ((int)com.yiyiaddon.m.b.a<"sk7g2lesvpeci","61u5gt4ijODg+uSu2CjvIpwNzHIFCrOtENhsdHVn310=",33189392238677291,-1612581108467383705,4250019153518685577,8611585150328849312>()) {
               case -71871983:
                  var10001 = (String)com.yiyiaddon.m.b.a<"s2v4wlp50ycxvo","VS+wlwWYI8Ca56JFU4lVDkyR7TrEOvaohQ4KLA==",9021399732685415276,-5978680136909409298,7014374125953824568,-9095116811419386904>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3rmz5v08vcfzn","BXW1MNijKIP3CTtYHGu8s0C+eMhMfZ1x4uOwvJ2V6X4=",-1836497499613020463,3532512059653917268,-5715092626991934088,2057132607272086521>()) {
                     case 662740578:
                        break label29;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = var1;
            switch ((int)com.yiyiaddon.m.b.a<"s3k3z76qkwftlc","PTBxwntFH27uQIhaSV3OzXlI3zvF+pb4seNwZkicwtg=",-3482376463902215214,-6294683854955114720,5816822997385237659,5707965059370376734>()) {
               case 524430524:
                  break;
               default:
                  throw null;
            }
         }

         this.kv = var10001;
         if (var2 == null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s2b9jcyo8lhl7i","nX+FgmsGCkNp9oMt6wluoElupwmqfRGpPL7NEu09tCo=",3028410903038292530,897495915700376296,-6445254029350590598,-7035054030088159373>()) {
               case 749390093:
                  var10001 = (String)com.yiyiaddon.m.b.a<"s2v4wlp50ycxvo","VS+wlwWYI8Ca56JFU4lVDkyR7TrEOvaohQ4KLA==",9021399732685415276,-5978680136909409298,7014374125953824568,-9095116811419386904>();
                  switch ((int)com.yiyiaddon.m.b.a<"sy87wcptx71fn","Bd5e8pvC/jh7MTBwAJmDyisxMhR5IJCmpyL0pDHYnkM=",8165483442448708160,5942995759508630157,9201303846225631711,-6810140690543604751>()) {
                     case -1998467297:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = var2;
            switch ((int)com.yiyiaddon.m.b.a<"s2qbi4qvy2og8h","fumGcpClEQDL2wYJzCvEgcaK4WgVz+KhvK9xABuejEo=",-4765186219078144719,3012308270723172936,2321473226915537861,4457555825259017356>()) {
               case -438487126:
                  break;
               default:
                  throw null;
            }
         }

         this.jg = var10001;
         return this;
      }

      @Override
      public String L() {
         return this.kt;
      }

      @Override
      public String D() {
         return this.ku;
      }

      @Override
      public String M() {
         return this.jg;
      }

      @Override
      public String bb() {
         return this.kv;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         Identifier var5 = Identifier.tryParse(this.kt);
         if (var5 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2au99e5t45urx","ptfjFT7NLmVdQdK2cAykS6f2OaC103HgGQsWNVqFgIU=",7586477773611033126,9097241767589126040,2521682861222706887,-2605740069165331402>()) {
               case 561938593:
                  return false;
               default:
                  throw null;
            }
         } else {
            Item var6 = BuiltInRegistries.ITEM.getValue(var5);
            if (var6 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s298sizjkt7ko7","3SggMjc4rJ2P870qFYz5CqKgs2B9tu9DTmdji/VhxUw=",604166729912311525,3581079309191439054,4704054097705646274,-8343270927990571854>()) {
                  case -1536989215:
                     if (var6 != Items.AIR) {
                        return com.yiyiaddon.l.g.c.a().a(var1, new ItemStack(var6), var2, var3, var4);
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s2teh64e3zlq6l","4RkkrJPEzB9IfzSqLCa7eRyJYEE7PUXp5V2rMcQWN3E=",-2524525203703093811,-4630694036609107347,-1864254932421971243,5064024414631250006>()) {
                           case 1001679560:
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
      }

      @Override
      public ItemStack a() {
         Identifier var1 = Identifier.tryParse(this.kt);
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s17dw7cxxakl9d","Z65ilrwqxYvYlG5Q11LMGAo7gcoBvw2W/6ce378bmlU=",772685914078685153,-5757191038138746955,-6922196085512149760,6705356444633906656>()) {
               case -2092024902:
                  return null;
               default:
                  throw null;
            }
         } else {
            Item var2 = BuiltInRegistries.ITEM.getValue(var1);
            if (var2 != null) {
               label27:
               switch ((int)com.yiyiaddon.m.b.a<"srg8ui8owbq4t","aeg0WbEHYMFZo0575eLqP9pUkdKw66Wk2DFXPxKJJUs=",2384075225753519425,1969101415082929471,-4330786564357570170,-7790166434832136996>()) {
                  case -1712775476:
                     if (var2 != Items.AIR) {
                        ItemStack var10000 = var2.getDefaultInstance();
                        switch ((int)com.yiyiaddon.m.b.a<"s5hhh3gtne8v7","piO7VMauvfcgeHvW2SslfmxTHz4SS9Nw5BcGSM3OBJU=",2693039557434184742,-3731490668251020664,7474975356608896796,-7624953713143831999>()) {
                           case 2025419733:
                              return var10000;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1wgp3uqgedjxm","lZKL2QL5wgW2anLvmbOBNNa9JCdqTueeSYSfSeMKxto=",1717528057412770840,7538275424642963477,1577788133330046322,780789620793121863>()) {
                        case 726044835:
                           break label27;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s38eu15iu78zs5","dIoq3OJuBDOwAQqGuOfhiKq1+KXb80WVSIQpzIhz3MQ=",6291368617386562483,-8427373099243034047,-572339306768289588,3369135327139456315>()) {
               case -798626037:
                  return null;
               default:
                  throw null;
            }
         }
      }
   }

   private static final class b implements com.yiyiaddon.l.h.g.b {
      private final String kw;
      private final String kx;
      private ItemStack g;

      private b(String var1, String var2) {
         this.kw = var1;
         this.kx = var2;
      }

      @Override
      public String L() {
         return this.kw;
      }

      @Override
      public String D() {
         return this.kx;
      }

      @Override
      public String M() {
         return null;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         if (this.g == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s38oyjlteauuao","ov6vp3wFKeI91u+SWwEixwJuKYQL7OEVlDw90/lMvkM=",-8339713203410602620,-3018083678367197736,-54066977342323002,-2127184318087678522>()) {
               case 398467545:
                  this.g = new ItemStack(Items.ENCHANTED_BOOK);
                  switch ((int)com.yiyiaddon.m.b.a<"s3v2eq0j7ong30","TqdYDDnpb7OcXevkcrZs9SikpVQBRqjSE157RGXkcKQ=",-1595927074610986707,7496220144398613191,2164010875503498832,80188314326781686>()) {
                     case -178628632:
                        return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
         }
      }

      @Override
      public ItemStack a() {
         return new ItemStack(Items.ENCHANTED_BOOK);
      }
   }
}
