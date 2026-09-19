package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Vec3i;

public final class o extends p {
   private static final float oq = 9.0F;
   private static final float or = 58.0F;
   private static final float os = 10.0F;
   private static final float ot = 24.0F;
   private static final int vS = 12;
   private static final String[] ar = new String[]{
      (String)com.yiyiaddon.m.b.a<"s3vn3lw8tk1l2k","P3qs6xOppZ47XY1t423JfXzxXWOQy71sS9PGI88k",-9082759478579379287,-6761202310906699511,6318679722377072161,-3554541808303874790>(),
      (String)com.yiyiaddon.m.b.a<"s2m9ouoh2bfj30","3m9TJ+d9hMzMG9/ncEolOJkcSyLG37P3a6XvgPgi",-3743004802955278288,-5962523592688640286,8702382096264403228,1142091809558225424>(),
      (String)com.yiyiaddon.m.b.a<"s21qubneq4mmwm","hzUblmgorCPhOmnsvRwSai86vT5L+WRgSl6wCOxC",364692003273119606,840429238172933803,-474727934458649594,7732054777330030122>()
   };
   private final Supplier<Vec3i> P;
   private final Consumer<Vec3i> z;
   private final o.a[] a = new o.a[ar.length];

   public o(Supplier<Vec3i> var1, Consumer<Vec3i> var2) {
      this.P = var1;
      this.z = var2;

      for (int var3 = 0; var3 < ar.length; var3++) {
         this.a[var3] = new o.a(var3);
      }
   }

   @Override
   public float c() {
      return ar.length * 67.0F + (ar.length - 1) * 10.0F;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
      int var6 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"shx2lhzb1l6y0","k8QgpXy03YKa13pQPuOJMdszfkSsqfEwT/23kcBX5PU=",5094167168668408947,6634592657907347180,6457993732963516022,4099233397572904834>()) {
         case 332436366:
            while (var6 < ar.length) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ui9d5azpn1tr","sfah/kXqsYMKogj+rpgijRNRXoKjoVaYr8FsDnyeEpE=",-30783400141013688,7141150360076173517,-6716946590917680564,2598767549774901894>()) {
                  case -54430880:
                     float var7 = var2 + var6 * 77.0F;
                     com.yiyiaddon.l.g.a.b(var1, ar[var6], var7, var3 + 15.5F, 10.0F, a(var5.va, var4));
                     this.a[var6].b(var1, var7 + 9.0F, var3, var4);
                     var6++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3axsjrn5e5jnd","S2WS5ZtOAdTx6Zgnyrcix/wC6hyrsbd+6Hex2a/l/AI=",-5001586173314676488,-9214837779619478300,6663176193292479319,-2225389585669668582>()) {
                        case 1444122238:
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

   @Override
   public void a(float var1) {
      o.a[] var2 = this.a;
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1z9hyvy43tjrt","jil6qrTRTlyACo5KL7zrK9eoO8BepNX2YWUAAmRCi2A=",2328913840845658616,-2906899599316242143,-4246743107707488611,-6910802762358267875>()) {
         case 1430602908:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ts8mgryl4qal","OsYCxHlFlhXMrTN7j8g437qjwbjEjVXwfL1kQ1bwEuw=",5957035931043235816,8205447537186559536,4927718850423555156,2596118430671756777>()) {
                  case -1534409164:
                     o.a var5 = var2[var4];
                     var5.a(var1);
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1vdh12d8lokqt","lxaYiqMmzhl6SMO7HX9oexzx5c0+ThN4fmulWF+AJbc=",-4641660697485965002,2443252304913096973,2383608652142864059,5403814526325084467>()) {
                        case -1508860993:
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

   @Override
   public boolean cB() {
      o.a[] var1 = this.a;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s5fuex9h6kwxn","z1xgDgZfnXkLf3uFvSJqX6a70ddQrp7SaqgSA10ECRQ=",-682028222398135059,5217694771676628753,-6039876573882347093,8223116717790271395>()) {
         case -422776425:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s3sc8lbjb0dudk","X0UsSS169KQbXcUtgMknYyrAi5PwUpU35G49NrahNjc=",7406857278232955752,-8343070798131281515,-4900474701521666729,5290565699107754316>()) {
                  case -540106469:
                     o.a var4 = var1[var3];
                     if (var4.cB()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s7lckbejl32gb","pukn7ytGikBE731GvCPptFmyl0JuY+0C/r709zAYCjU=",2412159038402097174,-7839513175792053010,7537375711726634898,-4619906369163243411>()) {
                           case 1995979318:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"sbrqwu6g9oxyx","NLy0s89UJdIyhIwAXb4/xXYqic8eCdlYjs02iW8GPWw=",2079990501798633988,-5170467788812352840,5830944746279408141,-665181905184862761>()) {
                        case -1304972446:
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

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      int var6 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2x3hus54cpzpk","PxhdTaUOvvwS3YFKwbRc3hANn4Jh5MHW/b/49/WOEik=",-3388782004161897551,-5473691133659589740,2043363939125271624,5950697497786770691>()) {
         case -368311712:
            while (var6 < ar.length) {
               switch ((int)com.yiyiaddon.m.b.a<"s30z8xp0lwdnv8","/XYAue0WgBTbY46uUZ+yfmq5y8gIeb+Zns4kAUnRE+w=",-8199514012483454299,7311949932764471359,6561958550658346560,2634467998397265322>()) {
                  case -866956597:
                     float var7 = var3 + var6 * 77.0F + 9.0F;
                     if (this.a[var6].a(var1, var2, var7, var4, var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s27vi72evjpa30","DdBtIlIhuGdwAMAVoWGO91XlGLNRV6TxdQJE3nSBbbI=",4366531968243093179,6225923012954182949,-5261801583333235234,-1397572386845273777>()) {
                           case -10857034:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var6++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1x6j3m8n35cal","axYr++X+77+eaum+URufoKvj4+ca14f0ZqDVKr9QzZA=",-493230306279068240,3386066317029875991,7278156313384282824,6930909625524522843>()) {
                        case -1612747824:
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

   @Override
   public boolean a(float var1, float var2, float var3, float var4) {
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1zkf8q8nbbcnm","qMadn4sD4QFyE+oJJg9eMWRT4J1xdkcsEIqFJ1B/+wY=",8783715809751649617,-2627733654161900904,-368343853855726078,7250392234924156800>()) {
         case -1288255369:
            while (var5 < ar.length) {
               switch ((int)com.yiyiaddon.m.b.a<"srlfui69xved8","QT2hOoMEI4qIGdHcBfGORmFu3kCJ3lv3ltucRWXF1/Q=",381369143718765370,-6304005733685193168,8219132131854767401,3654286431658877605>()) {
                  case -417148122:
                     float var6 = var3 + var5 * 77.0F + 9.0F;
                     if (this.a[var5].a(var1, var2, var6, var4)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1n5y190stfrx4","ySWjrk+LqLtvkdwY15dTolrdBOYemG9mfodc7QXTJpA=",2732490976787844779,-5040281785436592304,3219225845741388508,-1783847127344104979>()) {
                           case 150654517:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3191wzdga4dz0","oA9UJweRa4y9vjXW3UvA/tQtJOda1McCk+3AfKhYFUg=",2005492378690808787,5500220697070390802,7155590682660451192,1152916993204158179>()) {
                        case 1163889798:
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

   private final class a extends m {
      private final int vT;
      private String Ha;
      private boolean gI;

      private a(int var2) {
         super(
            () -> (String)com.yiyiaddon.m.b.a<"smzr51rut7635","bcXfgdrf5XNxWCTJkmsOTMpgOHFSWiSnkRW3xQ==",-1031738121810536765,5124913536417164035,-5419489004155637374,7564046303187533827>(),
            var0 -> {},
            12
         );
         this.vT = var2;
         this.a(58.0F);
      }

      private int eI() {
         Vec3i var1 = o.this.P.get();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"squ5agk4b6tf4","P8IeLFxzwcXNiuhcoFLlyWBVEdGYn8RXLlh21GfdWpk=",-1934228689670775445,332044951976793680,5900555682923052592,-34413089745099216>()) {
               case -534922648:
                  return 0;
               default:
                  throw null;
            }
         } else {
            switch (this.vT) {
               case 0:
                  int var3 = var1.getX();
                  switch ((int)com.yiyiaddon.m.b.a<"s24p9o37rarshq","gxWJZZ6yAu+nD+n6COR6FwS5q0gg0q9r9/vB2rKlOhc=",7273152755025015987,3373146763008489901,7020553225868531265,-5582124534684681291>()) {
                     case 1123046264:
                        return var3;
                     default:
                        throw null;
                  }
               case 1:
                  int var2 = var1.getY();
                  switch ((int)com.yiyiaddon.m.b.a<"s2uv6r6ffue5pe","t2V8lYU7I4r27K99EI4akByHxTR7j5uIpxaKnWFUQtY=",-4897102938688839016,-1252648609031953356,6317400649410973698,-8778656476805945339>()) {
                     case -885022780:
                        return var2;
                     default:
                        throw null;
                  }
               default:
                  int var10000 = var1.getZ();
                  switch ((int)com.yiyiaddon.m.b.a<"s33n2dwvrbbgag","D+VDO3ka1IiBufGf6uX7vIhK1Ev6lO69n7B7CYI0ouc=",6830435224304478595,3940080565166582011,-7429810823873141515,1515173858767041847>()) {
                     case -385562236:
                        return var10000;
                     default:
                        throw null;
                  }
            }
         }
      }

      private void R(int var1) {
         Vec3i var2 = o.this.P.get();
         if (var2 == null) {
            label28:
            switch ((int)com.yiyiaddon.m.b.a<"s18akkgdzl89mz","hjTAU2v6U5M94lUG90NF6sYibOqKMqUU1gUDv+LHOGw=",3262879322190040958,-5455840680664658845,-6215812804786112299,-3785409510234273621>()) {
               case -932400826:
                  var2 = Vec3i.ZERO;
                  switch ((int)com.yiyiaddon.m.b.a<"s2ys2ki0g85giz","R/6lkqqqsoInFc4qyEu8iOlBRwv4cVa3nMlG3PbWDbo=",162165746405848775,3274329561716268219,3285817715801882005,473969245599501672>()) {
                     case -399074017:
                        break label28;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         Consumer var10000;
         Vec3i var10001;
         var10000 = o.this.z;
         label24:
         switch (this.vT) {
            case 0:
               var10001 = new Vec3i(var1, var2.getY(), var2.getZ());
               switch ((int)com.yiyiaddon.m.b.a<"s7w7l8lmbqdl2","9tASlUXGxVpG3w7+rrySjPZogJ4G3ipw9W4094NM3QY=",-7546598455278898940,-5422545037074239862,-1541818298154343097,-7850561559414542927>()) {
                  case -1149985316:
                     break label24;
                  default:
                     throw null;
               }
            case 1:
               var10001 = new Vec3i(var2.getX(), var1, var2.getZ());
               switch ((int)com.yiyiaddon.m.b.a<"s2rmk64n8anpy9","GsO6T84HWj2nybioOULtmJPJS/z2kEqCJNFThXqp1ds=",-7080064364345732348,4937057858393275760,-3703135436221443695,-8942441463895247155>()) {
                  case 261620794:
                     break label24;
                  default:
                     throw null;
               }
            default:
               var10001 = new Vec3i(var2.getX(), var2.getY(), var1);
               switch ((int)com.yiyiaddon.m.b.a<"smxun9sz32b7q","Gl28JmaCFCavxMThLynoWhTF1mi3IVInIKqBX1gOqPY=",6591874362910727169,3348515699035498859,2909972205884245378,-8327765681409965483>()) {
                  case 1856318915:
                     break;
                  default:
                     throw null;
               }
         }

         var10000.accept(var10001);
      }

      @Override
      protected String gU() {
         if (this.Ha != null) {
            switch ((int)com.yiyiaddon.m.b.a<"slvp56owll3e7","9dMIX1fScrQ2eGrzB+6/EXGcQ0bACX5SEbUJbmCSdms=",-3921725817604422979,-8477793828066852732,1426082947489182936,7828918003306747269>()) {
               case 524548959:
                  String var10000 = this.Ha;
                  switch ((int)com.yiyiaddon.m.b.a<"spz9duhpu7f20","bF1CHJuLizsXwJRAkrQxcRfjUrhwaDarXAN94RsnoBA=",533900818876477306,-1037354641720619904,8663686905129005574,-3137686061796663034>()) {
                     case -1772899227:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = Integer.toString(this.eI());
            switch ((int)com.yiyiaddon.m.b.a<"s29hvjy36o5esc","Jp5m5tYiAiAa3BYEqJPJKMV/4vwgnT072W0WQ4pkzP4=",-2545737314217915382,643657828661181349,6714222156787710653,3909792464988162039>()) {
               case -71452880:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      @Override
      protected void bB(String var1) {
         String var10002;
         if (var1 == null) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s2ujy71a4i0p51","iOlFzgDIwlFt6xB5KKEHS1nEhR6T8CR9mBoi0wxSU4I=",6211820586216234795,669471284880947039,3493320229745210493,8905405085430796932>()) {
               case -1889473109:
                  var10002 = (String)com.yiyiaddon.m.b.a<"smzr51rut7635","bcXfgdrf5XNxWCTJkmsOTMpgOHFSWiSnkRW3xQ==",-1031738121810536765,5124913536417164035,-5419489004155637374,7564046303187533827>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1kswue49iw332","ESJA9JguBAIvQkqYTfdNmDhP7VsK7wX8wMITWyXjM7c=",-3816643590624526450,653578046272175947,-7424444885027804528,346107778596110693>()) {
                     case -1617092096:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = var1;
            switch ((int)com.yiyiaddon.m.b.a<"s3uad0yg2wuvkd","E0Q67JpZuoGQKxXgY12RpPQWGcHwKC5e4Av2mkWklj8=",-2120499538170109454,5255019529752031285,-2330032485041918534,-6890110755164914746>()) {
               case 1696008603:
                  break;
               default:
                  throw null;
            }
         }

         this.Ha = this.cp(var10002);
         Integer var2 = e(this.Ha);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"seqblvtxq7wqr","wGFJexxgf7FhmzLkMhRtVSwc5Mq4/kdynSuIiHb8Yck=",-7405282494962433147,-7540398868053849455,673867838623685919,3738405780660228014>()) {
               case 1515074681:
                  this.R(var2);
                  switch ((int)com.yiyiaddon.m.b.a<"sklnl4lumzzv3","cRtXTJDoU7Uy8sC+8zz1l56HrUO3ijJEz8BArnh3/dA=",3505513275073837047,-8936295362535657908,3098281902429334408,2560986289769154648>()) {
                     case -944958047:
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
      public void a(float var1) {
         super.a(var1);
         boolean var10000;
         if (d == this) {
            label36:
            switch ((int)com.yiyiaddon.m.b.a<"s1cxy1jpis8iin","k+PpHEicYkkXDNCgfEFJ59ggUVm25MjWBp1SU+eL0Ps=",8991734867174734230,6478457879031814369,-7644639852887018138,9216807294394196044>()) {
               case -1730796198:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s3jbvxkt7qqdl5","mlBTx+XN9tD0IvkK8wEUbZVEjzri6LVwRyqwp5jdCL8=",-3718430304505965495,-6773505923144411835,2029347320414401114,-8410106355175811065>()) {
                     case -618223486:
                        break label36;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"srxuadvjttla5","3ftW85mpJLfnCiiiNkRE1u3ouVw3faXIKcpYL+F3MKg=",3123242675702339325,5182957799760816525,2710520984377911528,6706333694059818653>()) {
               case 1145095014:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2 = var10000;
         if (var2 == this.gI) {
            switch ((int)com.yiyiaddon.m.b.a<"s18d3uxrkdj8fy","m6Fj96ShAgf9uyEBsokBFW3+2fsEZYxgEiWzyXll8yQ=",-713344842247370453,-3521634307312552427,-5526028053841012946,7927327957553717312>()) {
               case -1329613988:
                  return;
               default:
                  throw null;
            }
         } else {
            this.gI = var2;
            String var10001;
            if (var2) {
               label27:
               switch ((int)com.yiyiaddon.m.b.a<"s1vpntonb19n8t","zwdwvG/yUgs0sF/Zpby5O3HqqewUisX1ggPwvCtV/Y0=",-9051284549279771081,-8076810974874426824,5521570992313308956,5146233795091583299>()) {
                  case -973128000:
                     var10001 = Integer.toString(this.eI());
                     switch ((int)com.yiyiaddon.m.b.a<"s2f5j3xcuejx2j","YEgpdJ5mmW8E7XiJFOR8RTil44oDx/cmiK/TQ2LT3vg=",3510082555512492239,8246461521610372443,5631621055435663229,-4305226242109428346>()) {
                        case 1519033342:
                           break label27;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10001 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s34bfko8br23qy","A4AVOR2kck8oJXzmVxs9QvKug7UssoqW96jAunMfec4=",100738735848267329,1577323805136996798,964794943265011577,-8881457533687756759>()) {
                  case 160839554:
                     break;
                  default:
                     throw null;
               }
            }

            this.Ha = var10001;
         }
      }

      private static Integer e(String var0) {
         String var1 = var0.strip();
         if (var1.isEmpty()) {
            return null;
         }

         int var2 = 0;
         if (var1.charAt(0) == '-' || var1.charAt(0) == '+') {
            var2 = 1;
         }

         int var3 = var2;

         while (var2 < var1.length() && Character.isDigit(var1.charAt(var2))) {
            var2++;
         }

         if (var2 == var3) {
            return null;
         }

         try {
            return Integer.parseInt(var1.substring(0, var2));
         } catch (NumberFormatException var5) {
            return null;
         }
      }
   }
}
