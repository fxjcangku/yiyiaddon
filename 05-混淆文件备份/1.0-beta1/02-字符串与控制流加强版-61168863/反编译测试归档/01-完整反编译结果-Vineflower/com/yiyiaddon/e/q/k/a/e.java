package com.yiyiaddon.e.q.k.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.j.n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class e {
   private static final String Al = (String)com.yiyiaddon.m.b.a<"so2dea90j692k","O2Fxm9UEcsaLilzCQrBj2sk+Kdt8xBPNCMO52qPer2MFMbg1gOGlRat0Hhb9e/Rk8sw=",-1377370858381738670,8792987449463255201,3109102911231317007,-7747324642831346835>();
   private static final String Am = (String)com.yiyiaddon.m.b.a<"s1zusc8tp86a3t","/XYLS4C53ID284AuyF4HrCFvBEqdqA2lvD7czPQuooKzbd2WUwcQgTiG6p4=",-8808129439723197045,-453518398708621073,8333508804895557939,-3886090717404827194>();
   private static final String An = com.yiyiaddon.e.q.f.c.图书管理员.name();
   private static final String Ao = (String)com.yiyiaddon.m.b.a<"s3lmrxwgt1cfi","G1HxhOY67WtD+6UREH0y7h1iOlUsMqS/069ykUejMxC5uPnjjMsEPXTU9kAMgA==",4963539891836060499,5463697769653420176,1927123362916294865,1125250475295065799>();
   private static final float ek = 140.0F;
   private static final float el = 72.0F;
   private static final String Ap = (String)com.yiyiaddon.m.b.a<"sujgjw00kv6u4","+xqsZm4aN/Tp60TzUrD0PEedEBwMVYhqEWX8MB3/kk1sjqU5N714OXRDaN4mwOEjc6al6YmaMgyWAHgXcX8d73/sHB6TaqKZbCw=",7969809451346558486,5276622899091452835,2941201244789330711,2436160821361075418>();
   private static final String Aq = (String)com.yiyiaddon.m.b.a<"s2y6i3en9idfdz","fBuxInytD5gL44GmSW83b3ZA2hHMjweQRxJZNR1jeCxDPO8nahdHHMoyOW5ntmlAQcco0cbBAvGWSvkgNkDqp19wzphxGw3PsGHFTsI6swS5zrlo2PMstCQ6",-226497392572786955,8645173840957285440,6618786910308566877,-6437414742661953024>();
   private static final com.yiyiaddon.e.q.b.a c = new com.yiyiaddon.e.q.b.a();
   private final a f;
   private final com.yiyiaddon.e.q.a f;

   public e(a var1, com.yiyiaddon.e.q.a var2) {
      this.f = var1;
      this.f = var2;
   }

   public static List<String> b(com.yiyiaddon.e.q.a var0) {
      com.yiyiaddon.e.q.b.a var1 = var0.a();
      if (var1.a != com.yiyiaddon.e.q.f.d.PIPELINE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2y3wufqacnq88","8LFtX3pxOUGcgKltzQM5sWPfjRWQZggEe7Ef3o4jjHg=",6622953281269280689,5663415587793305896,-5231290222325923413,5244786270607156903>()) {
            case 270863736:
               return List.of(var1.a.name());
            default:
               throw null;
         }
      } else {
         ArrayList var2 = new ArrayList();
         com.yiyiaddon.e.q.f.c[] var3 = com.yiyiaddon.e.q.f.c.values();
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3nqcssiut06rn","u35ofOoPRDzL8gMGAuhc2hkOALXgANYd47JdFkJu3zA=",4622469190532781842,-5994180200386852896,-7796288941941018983,-3286391624911717279>()) {
            case 752459932:
               while (var5 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4krriw6wr9ju","lr4o69ibxiy0v2ugSNiURL7uCSM54hYMkyFkOB/oqnc=",5316723751764422097,-9061637835415937479,-5853366773639619306,-826187170678320244>()) {
                     case 877992974:
                        com.yiyiaddon.e.q.f.c var6 = var3[var5];
                        if (var1.ay(var6.name())) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s15biqjcspsh7d","vKNeFCGatQU0mr098mycWk6SeOUY8+HCHAUkaj5v6x0=",5213774716080270785,-7036247586650983398,1152792401520137282,1980585533214917964>()) {
                              case 1684772997:
                                 var2.add(var6.name());
                                 switch ((int)com.yiyiaddon.m.b.a<"s16f1c3ys1xjae","FMyojzGF5XCg+BC5F7w8/UJeawFYjjB5eNE1jpc+AKY=",-8185141634778599680,-847406542762369934,-2563824887865091561,6937273796281551240>()) {
                                    case -938215508:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"sdbxizcs64rae","+cVl/2D4QTZsOzvJSe1x9/MnBXxCAYuvzM2TojIbQsY=",-4277413705998408480,1764783770226799012,-715910261299910059,5485228927980633083>()) {
                           case 2129633274:
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
   }

   public void d(i var1) {
      this.J(var1);
      if (this.f.a().a == com.yiyiaddon.e.q.f.d.PIPELINE) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"syvvt2u3aa63v","mq9F71t4QfnMWhDKldDRoLKXxgFi+LftOAhVB7dh0O8=",5646844272649753021,8722806050268433947,-6832737796901362690,-755343221786146222>()) {
            case -400002839:
               var1.a(this.e());
               switch ((int)com.yiyiaddon.m.b.a<"s1mlqsu9of7jyp","CJzBwnEdVOrkg03Q/Xnzty1mRuOByIl1DRATrGE6res=",8515409116352892815,-9218706936410169375,-3963740645317892176,-8614126614851155104>()) {
                  case 576376084:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      Iterator var2 = b(this.f).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s11vmawrvmj6u0","UArJ4ZWxn2dsLTfgqTOILFLxtt8XLIRWWiXwuBBCU+U=",-8600620319260873922,6829367235825072981,8715032930511178643,-746996438156510909>()) {
         case -539706667:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s22kyhrbnuf287","CpvVVu400KSRsro2uJ6Y5qKb8IQt0CSHMuV4WvQQRUA=",5241325921902777751,1663433175122696544,7527391230488667000,-5007550334690282870>()) {
                  case 849605451:
                     String var3 = (String)var2.next();
                     var1.a(this.a(var3));
                     switch ((int)com.yiyiaddon.m.b.a<"s4ko46yig0tk7","fc0fgFpQHLoqfP3+tA7V0vsTmXDwuVBs12/xE9OsgSQ=",-8191799720284745610,-7244814037317869775,2466258766046979041,-4218895446013504878>()) {
                        case -1442424935:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.a(this.d());
            return;
         default:
            throw null;
      }
   }

   private void J(i var1) {
      com.yiyiaddon.e.q.b.a var2 = this.f.a();
      var1.a(
         new f.b(
            this.f,
            () -> (String)com.yiyiaddon.m.b.a<"sxjrdgxv7yhfz","yw/VWEtotuRBacjOGzM9eiZWKxWzZudq3zVo/2abTJ2rqmzJ",2927396584365073367,-3542960791953943170,1278841825160865741,-7198849427260898809>(),
            (String)com.yiyiaddon.m.b.a<"s1xplmr0igil7d","wQH+fTvnzrxwHLHCH+B3zQEmBfA7v5jZKrIqcCzrcHRob7CIbqvKyQ==",-1677050182985510702,-6752055684283437470,-68661932030558876,1513313197777016625>(),
            (String)com.yiyiaddon.m.b.a<"s28htnihkzg1zr","YeO4Ww1iB4f+TYtIWISPmwUn6Dk8jH3aYj9iYQKLBjnOYitT2iFwsA==",4268731831782347133,-523076000135442871,6746866816900225496,4398448573180472586>(),
            List.of(
               new f.c(a(com.yiyiaddon.e.q.f.d.values(), () -> var2.a.ordinal(), var2x -> {
                  var2.a = com.yiyiaddon.e.q.f.d.values()[var2x];
                  this.f.L();
                  this.f.eq();
               })),
               com.yiyiaddon.l.c.f.b(
                  () -> {
                     var2.a = c.a;
                     this.f.L();
                     this.f.eq();
                  },
                  (String)com.yiyiaddon.m.b.a<"sxjrdgxv7yhfz","yw/VWEtotuRBacjOGzM9eiZWKxWzZudq3zVo/2abTJ2rqmzJ",2927396584365073367,-3542960791953943170,1278841825160865741,-7198849427260898809>()
               )
            )
         )
      );
      var1.a(
         this.a(
            (String)com.yiyiaddon.m.b.a<"s21vvcnnwp8pfn","J+f7pM6dTlV6Zv4G28mwZTlZwef5ZVS7ElaS3IPsctpk5QRUsOcjXuOiii8KbA==",-7653645363536730165,4937728209626730639,7484577436208629824,-4831828425618013073>(),
            (String)com.yiyiaddon.m.b.a<"s1gjihgf31tvj3","GCuOdlG7qw0pbA79AhepJe6aWcKaWIAL1HdwwsoeXuIRTrgObC0d7ce4xRmQ/sSGeZztyp7Vr5DxREi21jA765as77gAO32DQP+X+r7dmT3qtza/s1if9yx2ynR9w0sTFBPZRmc7",-687732699484659657,-4739289529256386342,-6456416141456710369,-2751387186400720963>(),
            1.0,
            27.0,
            () -> (double)var2.qn,
            var1x -> var2.qn = var1x.intValue(),
            () -> (double)c.qn
         )
      );
      var1.a(
         this.a(
            (String)com.yiyiaddon.m.b.a<"s2q89vz2xd8892","tD7PWeKE8XW5EVfmCUQGKZ5BxyWm4G97rm7hE21J6hbCKTL2LaP3NNNS",4157521224290232374,6206311861171426638,4474436926545421378,663056461832650067>(),
            (String)com.yiyiaddon.m.b.a<"s1vg5odr8nuamm","63ETF8MAzss5bbEz9Xk3CJzOn3E2U3MxIpxtYGNdysxsE8DjC9LwYYHvBRMPF/yUQuwMLtEE6qf0hfovKlLrk3mqso5fhYcTkLDS1ApSTAK7R0DUt2ACHQ==",-247978897869655285,2768885003230901247,-510769756931349112,-7125746093173520046>(),
            8.0,
            256.0,
            () -> (double)var2.qo,
            var1x -> var2.qo = var1x.intValue(),
            () -> (double)c.qo
         )
      );
      var1.a(
         this.a(
            (String)com.yiyiaddon.m.b.a<"s2djydgbpofs2l","NNsRTnH1xNx0mA3P5ewqgDg2vqm86iZZLxo0d5Ha/uQpGmfujqkd6y/ZMu0=",-8197015626996363423,-369057407065702357,-1370618766955922554,5862528875649317548>(),
            (String)com.yiyiaddon.m.b.a<"s215dah1n90d01","5Q2w1F0rJJ9KUEHPthdUxMKbZ8L1mV4rBUThBTA1GGbK7LItoDUVN3OLd66ND4/EMH2+O4scRzfUQyD5IOlzqNu/JioSlCzYED25dVbuZDqyHCP1bSdrCOsd3+qCHzQ0kCVhPHhLBD2I7uthb83AYCEtXqRhhHUnXqI=",-7405133277146791249,-3212210475281093029,8678191739799059476,6456836367251984597>(),
            () -> var2.eW,
            var1x -> var2.eW = var1x,
            () -> c.eW
         )
      );
      var1.a(
         this.a(
            (String)com.yiyiaddon.m.b.a<"s3iyebndb5259a","nmeGlDxN2P6YSfQ2pRdhA1jhbeHlNrL+c5CYJedOk5SVd3D+X7l8wES8",7472804519314089770,-1114164487425586080,-2692215688300694638,218175439259136115>(),
            (String)com.yiyiaddon.m.b.a<"sa79rder0rt2s","KOsIxxdmo38qoNkMcA5qLXmRyVwRe+p7n141kshctfnPdFnuvqwdXOeheCZ1FKcWgU6FolcNakazgEsrP/BZfJ1hQH5dHlLEhFVMSb8ntW5VuskPJ7wfvlgfMmG0mg==",-7818789421237027132,-9160869992125948876,-7093604449947908369,7589304502900210658>(),
            20.0,
            600.0,
            () -> (double)var2.qp,
            var1x -> var2.qp = var1x.intValue(),
            () -> (double)c.qp
         )
      );
      if (var2.a != com.yiyiaddon.e.q.f.d.PIPELINE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mk17itgmtmhm","cfFOczh+NL1MXB/5OZOG2H/6ChzHNuC/WIPSQWs2Cpw=",7836133008889000433,1651590849276543805,1110460528303248515,6893481300595993473>()) {
            case 1025807913:
               var1.a(
                  new f.b(
                     this.f,
                     () -> (String)com.yiyiaddon.m.b.a<"s1c7kuo35jemyc","/JcS+22VG+JDH8lpMmU3hWl54zYm0JZhZzgyuwSpOjdGKgI8",-8017148756534431824,1566799756593959475,9042784493026774799,4254534015196349732>(),
                     (String)com.yiyiaddon.m.b.a<"s28va5b4h180oz","DyXdqeKMOqc3Mftndvz54eZyTYvVgJnGFYx6/QnB242mKGJF/i15KX6Lz6aDrC1MWdZCr545cnorqnArT0tAmAQSHQzIh6ZCIAlSh05bgcy0NNnQfEIp5EOvXXs=",-4280767758019859986,8370008287051277665,5825095717370548196,2816840486531116781>(),
                     (String)com.yiyiaddon.m.b.a<"s28htnihkzg1zr","YeO4Ww1iB4f+TYtIWISPmwUn6Dk8jH3aYj9iYQKLBjnOYitT2iFwsA==",4268731831782347133,-523076000135442871,6746866816900225496,4398448573180472586>(),
                     List.of(
                        new f.c(a(com.yiyiaddon.e.q.f.c.values(), () -> var2.a.ordinal(), var2x -> {
                           var2.a = com.yiyiaddon.e.q.f.c.values()[var2x];
                           this.f.L();
                           this.f.eq();
                        })),
                        com.yiyiaddon.l.c.f.b(
                           () -> {
                              var2.a = c.a;
                              this.f.L();
                              this.f.eq();
                           },
                           (String)com.yiyiaddon.m.b.a<"s1c7kuo35jemyc","/JcS+22VG+JDH8lpMmU3hWl54zYm0JZhZzgyuwSpOjdGKgI8",-8017148756534431824,1566799756593959475,9042784493026774799,4254534015196349732>()
                        )
                     )
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1a4hxd6gelrtk","ZQ+O3C7sumtxrAGdH93DlXrKYGAjoDYfTT1WyV1plzM=",-3202804108456334886,-6973684313670347292,701395594053090349,8592957055510007317>()) {
                  case 719745468:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private f.d e() {
      this.f
         .ba(
            (String)com.yiyiaddon.m.b.a<"s1zusc8tp86a3t","/XYLS4C53ID284AuyF4HrCFvBEqdqA2lvD7czPQuooKzbd2WUwcQgTiG6p4=",-8808129439723197045,-453518398708621073,8333508804895557939,-3886090717404827194>()
         );
      f.d var1 = new f.d(
         (String)com.yiyiaddon.m.b.a<"s3alkuna7usn9m","LpU61VuTV85TsUQw73FG8yP4gmKf+9SMRzALw2bXOeSz8pSiUlI=",-7054247969611333853,-7331437197831992521,-500034015791314422,511657434925867799>(),
         (String)com.yiyiaddon.m.b.a<"s1zusc8tp86a3t","/XYLS4C53ID284AuyF4HrCFvBEqdqA2lvD7czPQuooKzbd2WUwcQgTiG6p4=",-8808129439723197045,-453518398708621073,8333508804895557939,-3886090717404827194>(),
         this.f.k()
      );
      com.yiyiaddon.e.q.f.c[] var2 = com.yiyiaddon.e.q.f.c.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3lkmaltncd88g","3FkzJliTWikiv5De+GcmrEzSfPS4NUnViA8Fyx4ZF4s=",3186179856422705968,-6524531077203044424,-4371355470562309448,1798557572743157725>()) {
         case -784757798:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s22qkzjrglnx3w","wzujXr11BL3LDNMGd//gRVUs0A1MdBjF4QaFtgQBXfs=",-1180154078345346594,3783090716396470632,-4172579163960774610,-5910496649410663101>()) {
                  case -202727183:
                     com.yiyiaddon.e.q.f.c var5 = var2[var4];
                     String var6 = var5.name();
                     var1.c()
                        .a(
                           this.a(
                              var6,
                              (String)com.yiyiaddon.m.b.a<"s2y6i3en9idfdz","fBuxInytD5gL44GmSW83b3ZA2hHMjweQRxJZNR1jeCxDPO8nahdHHMoyOW5ntmlAQcco0cbBAvGWSvkgNkDqp19wzphxGw3PsGHFTsI6swS5zrlo2PMstCQ6",-226497392572786955,8645173840957285440,6618786910308566877,-6437414742661953024>(),
                              () -> this.f.a().ay(var6),
                              var2x -> {
                                 this.f.a().l(var6, var2x);
                                 this.f.L();
                                 this.f.eq();
                              },
                              () -> c.ay(var6),
                              null
                           )
                        );
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3vro2c931avhb","n3KpJQPcM8LHfgHz25w/uZk8f5roJl+NPiS/YxQnrc4=",5194214690267729450,-5169021164430642784,8006778120462629348,-1550830427814520281>()) {
                        case -629977378:
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

   private f.d a(String var1) {
      String var2 = var1 + "";
      this.f.ba(var2);
      f.d var3 = new f.d(var1 + "", var2, this.f.k());
      var3.c().a(com.yiyiaddon.e.q.k.d.a(this.f, this.f, var1));
      var3.c().a(this.a(var1));
      if (An.equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v5hkczu14zq5","hRBT2exWz0cjdrrtb0DZvrcEbECBvOjgV5WSszxjftQ=",3187523523186096568,4538981151558845139,-4085021185754416042,-7085619920485146905>()) {
            case 1282731543:
               var3.c().a(com.yiyiaddon.e.q.k.d.a(this.f, this.f));
               switch ((int)com.yiyiaddon.m.b.a<"s2qbqgg7bkyc53","YQlCQdVJDjodOjYNbK9lCRgl0adoG/Ox7bkO8iQ5dXE=",-1356838086044271967,3679136408397150075,5296243320279620206,2717370141079447907>()) {
                  case -369230742:
                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var3;
      }
   }

   private f.b a(String var1) {
      return this.a(
         var1 + "",
         (String)com.yiyiaddon.m.b.a<"sujgjw00kv6u4","+xqsZm4aN/Tp60TzUrD0PEedEBwMVYhqEWX8MB3/kk1sjqU5N714OXRDaN4mwOEjc6al6YmaMgyWAHgXcX8d73/sHB6TaqKZbCw=",7969809451346558486,5276622899091452835,2941201244789330711,2436160821361075418>(),
         1.0,
         64.0,
         () -> (double)this.f.a().o(var1),
         var2 -> this.f.a().e(var1, var2.intValue()),
         () -> (double)c.o(var1)
      );
   }

   private f.b d() {
      com.yiyiaddon.l.j.f var1 = new com.yiyiaddon.l.j.f(() -> this.f.a().e, var1x -> {
         this.f.a().e = var1x;
         this.f.L();
      }).a(140.0F);
      com.yiyiaddon.l.j.a var2 = new com.yiyiaddon.l.j.a(
            (String)com.yiyiaddon.m.b.a<"s2cnclcoz88udb","knRSTKTBUUSQ1seg21N+KC/uqymxQMh4w7PTWNQA7zBS/DLN",2525704333015004404,-5325715255283330094,-4417752894317949356,4953004313827896504>(),
            this::iY
         )
         .a(72.0F)
         .b(
            () -> {
               if (!this.f.a().e.fT()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2tcnooauvgofz","4/oQ6nvsPRGu3imoFG4RNBPiY+wo3N65njwsAgzFb4k=",-7664103963687471921,-41016366073890527,-1464131998683621408,-7592201917466741314>()) {
                     case 1470222453:
                        switch ((int)com.yiyiaddon.m.b.a<"s24lgqo5jmamei","TVZ0z6Yhqbizuc7rfpXR+FQKyAdBeUFV61h2v5BnQXg=",588508790260660993,9165859347521870803,3456765280670445574,-6476493678770384042>()) {
                           case 1306500863:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jqu5p5z4joan","Am4wEJ3yvXuX/yW/u7xQGBBr6iIS+MtS9s7IeOFjANg=",-4192002901947941481,-6845602773601789609,-4753556893013701239,5386219725002815092>()) {
                     case -1167387526:
                        return false;
                     default:
                        throw null;
                  }
               }
            }
         );
      return new f.b(
         this.f,
         () -> (String)com.yiyiaddon.m.b.a<"s1rdt7q7gbmbl4","7dyjVhN/5+yZmjUwfK9IM89P7Aj8xIeNCSJcvP4kIGU/VLhcLF4=",4934452145874094742,4200935744163852693,-6539827854854761000,-105979473870969278>(),
         (String)com.yiyiaddon.m.b.a<"s3lmrxwgt1cfi","G1HxhOY67WtD+6UREH0y7h1iOlUsMqS/069ykUejMxC5uPnjjMsEPXTU9kAMgA==",4963539891836060499,5463697769653420176,1927123362916294865,1125250475295065799>(),
         (String)com.yiyiaddon.m.b.a<"s39nyqw9l2gnyb","L9orn3vC2gtKHMAtNharrcWSlp1WKe+GyvQFqRdsfGLR2gsGvIRX9LRLo7OntE5Um08=",3681339554698315288,1513474237502421243,3076748966379055755,-4091681350984549127>(),
         List.of(
            new f.c(
               var1,
               (String)com.yiyiaddon.m.b.a<"sqjo0pq5jwszo","AqF9WLnCnX9KC4boT30e4SLLKU3dLVlWmz9AF99sjt+qD0aNbGicrUAKwlsi+lVA2sTFP1uxtCowCR4aAoGHCof0ufg=",-4321924095908800360,-8234282834042443492,1391110064276545098,1130675237424696214>()
            ),
            new f.c(
               var2,
               (String)com.yiyiaddon.m.b.a<"s1j52mqabnqwai","tTJzeEAB0uBO8gFeijw4xSNgeVg9mlICmstnn74pPa2UbB+xZx4lPg6MbyX49ulV2wvl2g==",-1656803210025816445,-3225646932806704325,-1610269606537442157,6614250984482288127>()
            ),
            com.yiyiaddon.l.c.f.b(
               () -> {
                  this.iY();
                  this.f.C();
               },
               (String)com.yiyiaddon.m.b.a<"s1rdt7q7gbmbl4","7dyjVhN/5+yZmjUwfK9IM89P7Aj8xIeNCSJcvP4kIGU/VLhcLF4=",4934452145874094742,4200935744163852693,-6539827854854761000,-105979473870969278>()
            )
         )
      );
   }

   private void iY() {
      this.f.a().e = com.yiyiaddon.l.d.a.i();
      this.f.L();
   }

   private f.b a(String var1, String var2, Supplier<Boolean> var3, Consumer<Boolean> var4, Supplier<Boolean> var5) {
      return this.a(var1, var2, var3, var4, var5, null);
   }

   private f.b a(String var1, String var2, Supplier<Boolean> var3, Consumer<Boolean> var4, Supplier<Boolean> var5, Runnable var6) {
      return new f.b(
         this.f,
         () -> var1,
         var2,
         null,
         List.of(
            new f.c(
               new n(
                  var3,
                  var3x -> {
                     var4.accept(var3x);
                     this.f.L();
                     if (var6 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2hxugl0cgrs5x","r3nvyttqGvGmB4MT38aDd8i5v6syQH4dE4I7/2uuTTA=",3771824795092664664,6977076517997157267,-7698969295735877104,2060446451999363070>()) {
                           case -1110921259:
                              var6.run();
                              switch ((int)com.yiyiaddon.m.b.a<"s31szi7dk5s1ch","btHD/8hN2XlJNdjhIPSEdwosFRDBPCNLBGUJKoFiYHY=",4007316677415427650,8417258534345662278,-5961773816852212255,3168007247640598324>()) {
                                 case -150408990:
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
            ),
            com.yiyiaddon.l.c.f.b(() -> {
               var4.accept((Boolean)var5.get());
               this.f.L();
               this.f.eq();
            }, var1)
         )
      );
   }

   private f.b a(String var1, String var2, double var3, double var5, Supplier<Double> var7, Consumer<Double> var8, Supplier<Double> var9) {
      return new f.b(
         this.f,
         () -> var1,
         var2,
         null,
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.i(
                  var3,
                  var5,
                  1.0,
                  (String)com.yiyiaddon.m.b.a<"smcy4bdqpbd23","KU4Uz4UAdQ58nwATCsm2NjoiDk3LXLtnbYoHeGYX0k3riiBv",7339629156922861832,2097428669010673075,1078581875956123017,-5229985050591986711>(),
                  var7,
                  var2x -> {
                     var8.accept(var2x);
                     this.f.L();
                  }
               )
            ),
            com.yiyiaddon.l.c.f.b(() -> {
               var8.accept((Double)var9.get());
               this.f.L();
               this.f.C();
            }, var1)
         )
      );
   }

   private static com.yiyiaddon.l.j.e a(Enum<?>[] var0, Supplier<Integer> var1, Consumer<Integer> var2) {
      List var3 = Arrays.stream(var0).map(Enum::toString).toList();
      return new com.yiyiaddon.l.j.e(var3, var1, var2);
   }
}
