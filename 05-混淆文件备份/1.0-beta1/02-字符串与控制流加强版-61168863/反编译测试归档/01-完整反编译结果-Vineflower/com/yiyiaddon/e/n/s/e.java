package com.yiyiaddon.e.n.s;

import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.l.j.k;
import com.yiyiaddon.l.j.n;
import com.yiyiaddon.l.j.p;
import io.github.humbleui.skija.Canvas;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;

public final class e extends com.yiyiaddon.l.h.f {
   private static final String uS = (String)com.yiyiaddon.m.b.a<"s1xim2a0jtxfup","LX1KIkYO9gZsLSeqs2iu1rcN8SwnVHxk8bI+UOPpldyPhzvLmzqFgcgS",4434703857616481182,4762819403833415716,-8547135541759585603,-9000791827667529650>();
   private static final String uT = (String)com.yiyiaddon.m.b.a<"s1ajv4nehcfzt","39J3diHtAvtLi8upFMTI9QbHVIIxcnjTn40dpUcXfs+9LdCCBcurMYm0vbwNCQ72IHlV95+cPqG29+wOrVlCVMDhW6dvmUCHdbUzxKLkMD9oHA==",9101878101933913536,-3674709118190149786,-2560057436902830827,-2864105148581719899>();
   private static final com.yiyiaddon.e.n.c.a e = new com.yiyiaddon.e.n.c.a();
   private final com.yiyiaddon.e.n.b g;
   private final com.yiyiaddon.e.n.c.a.a l;

   public e(Screen var1, com.yiyiaddon.e.n.b var2, com.yiyiaddon.e.n.c.a.a var3) {
      super(var3.a() + "", var1);
      this.g = var2;
      this.l = var3;
      this.G();
   }

   private void G() {
      com.yiyiaddon.e.n.c.a.a var1 = a(this.l);
      this.d()
         .a(
            new e.a(
               (String)com.yiyiaddon.m.b.a<"s1a8h0qynln7qa","BJl2Vky7g45GHDejAoldUSAWMaacFNGoefNXDKxfulb0RtwqkcrGcEnArK4=",4874895420024332671,2993222277241032830,705427242533492205,6303933189075677057>(),
               null,
               new n(() -> this.l.dD, var1x -> {
                  this.l.dD = var1x;
                  this.g.L();
               }),
               com.yiyiaddon.l.c.f.b(
                  () -> {
                     this.l.dD = var1.dD;
                     this.g.L();
                  },
                  (String)com.yiyiaddon.m.b.a<"s8fcu3nfm7gq7","gxz4XSAH+CeSCMPQSlU4pnuQDZ4scp7/0Q0ddmCYpjA=",-9152583898167997210,2732790191625399894,-5493137247590442301,-4179368888933943162>()
               )
            )
         );
      if (this.l.cZ()) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s3k3wmzidex72v","zXkLOqu7XPvgPr264Gavv+I5S05n4cuGMwond+alYA0=",3015554414184789393,-8408400518590740948,-219701613021468366,1545460028244898134>()) {
            case -1647541632:
               this.d()
                  .a(
                     new e.a(
                        (String)com.yiyiaddon.m.b.a<"s2303zkauy95ug","d3E30DejL8nm15Ye0WKGfi4hFOtCEK1vylSY2F9Ab8CrqJA/vBHeA6vJe5U=",3071127213032759558,-3370867993676578224,4951694363813668593,5601814369064197442>(),
                        () -> (String)com.yiyiaddon.m.b.a<"s1xim2a0jtxfup","LX1KIkYO9gZsLSeqs2iu1rcN8SwnVHxk8bI+UOPpldyPhzvLmzqFgcgS",4434703857616481182,4762819403833415716,-8547135541759585603,-9000791827667529650>(),
                        new com.yiyiaddon.l.j.c(this.l.a() + "", this.l.y, this.g::L),
                        com.yiyiaddon.l.c.f.b(
                           () -> {
                              a(this.l.y, var1.y);
                              this.g.L();
                           },
                           (String)com.yiyiaddon.m.b.a<"s324dbvcvs9dqk","0wZS9X7oCtGvdfX23kDs3DA58fy8cvCeTNFI6KVLjXU=",5134764893778908388,5833232992557756291,4818278247268849347,2817535488836482027>()
                        )
                     )
                  );
               this.d()
                  .a(
                     new e.a(
                        (String)com.yiyiaddon.m.b.a<"s3qh54re1s3yk9","cek0KizIFSvxOOy7kKTAzGXAwJ4WJtasQJ6+2/N/RgQlywTF44FDBOZBQHg=",6062950332563099527,-2106925575227160811,-6120057436069529328,2074359324098683601>(),
                        () -> (String)com.yiyiaddon.m.b.a<"s1ajv4nehcfzt","39J3diHtAvtLi8upFMTI9QbHVIIxcnjTn40dpUcXfs+9LdCCBcurMYm0vbwNCQ72IHlV95+cPqG29+wOrVlCVMDhW6dvmUCHdbUzxKLkMD9oHA==",9101878101933913536,-3674709118190149786,-2560057436902830827,-2864105148581719899>(),
                        new n(() -> this.l.y.gj(), var1x -> {
                           this.l.y.a(var1x);
                           this.g.L();
                        }),
                        com.yiyiaddon.l.c.f.b(
                           () -> {
                              this.l.y.a(var1.y.gj());
                              this.g.L();
                           },
                           (String)com.yiyiaddon.m.b.a<"s2dehct4rvw6vn","eHWfmYa/yhSlr0Jt1/93eRIFl8w6/yfKQQY9wYmc3Zk=",-3364407374760033833,-3059210728623853087,-6539216529441768640,-5021785235571011684>()
                        )
                     )
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s2ihq29sgnyvaj","gjIUmXXMJOPAdiTeenkWTYjQuf4dN4k8aCfNIKnp1MM=",4531149850063806571,-6038961576640796780,-7135476054226010177,1953349354781880675>()) {
                  case 1953204469:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.l.cY()) {
         label18:
         switch ((int)com.yiyiaddon.m.b.a<"sbd00ypasvo76","2kG21CQ4DPgZdxjIZ+4GO4UkJHn8pYNqdoBjxGf8rpU=",7291694045634332722,-1415748826312224067,1485478401322704570,8217286049154912208>()) {
            case -1598837119:
               this.d()
                  .a(
                     new e.a(
                        (String)com.yiyiaddon.m.b.a<"sc0djbexco0ez","JXz6QtAl67fC/aEOPcYByWP0aJEFvMvVFfoVU1/Kjsvp/NyMRb1uzyE2JrAHY3Ym",4589986876128721299,-1253164860938643729,8348181072204290943,5120346413078249136>(),
                        null,
                        new k(List.of(j.b()), () -> this.l.c.cr(), var1x -> {
                           this.l.c = j.a(var1x);
                           this.g.L();
                        }),
                        com.yiyiaddon.l.c.f.b(
                           () -> {
                              this.l.c = var1.c;
                              this.g.L();
                           },
                           (String)com.yiyiaddon.m.b.a<"s3okhv44so9x2q","7b7jirAUBt4gIOI9bPWiXzGjfKcYYY+G72vLRWNCcSHFfTsP",6892709935839591351,4592987243248211214,-5409693334483975493,-1322831427541714626>()
                        )
                     )
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s32ewd5dhqak19","yhhAUkgRlFLcjwoGwYfa+czMpcGFQe8p71ra5GPD1cY=",-5264020878265124809,-4842165365030361876,-655458295008665628,4217701562447185344>()) {
                  case 502409316:
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.d().a(new w(this.l.a() + ""));
   }

   private static com.yiyiaddon.e.n.c.a.a a(com.yiyiaddon.e.n.c.a.a var0) {
      Iterator var1 = e.aL().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"ssvijol6ihqvr","CxkwMAgneLsTQ+iZoreHc3UroJovoKHDKofu1ULbSO8=",-7720934351646860206,3043772631212358466,730640405502658432,-4254131447927842453>()) {
         case -756596892:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"svpckbixltupz","iizi0/iN221ON47A/sYHyYUf6/sX41/n1q6p55MuJO0=",7251148583301054751,6250657240010197504,100281531119050218,5596799352711944940>()) {
                  case 867716400:
                     com.yiyiaddon.e.n.c.a.a var2 = (com.yiyiaddon.e.n.c.a.a)var1.next();
                     if (var2.a().equals(var0.a())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s7zshe0vx6eq9","q1P48llqywlb5wBUI38l3Bf6goF1eoAHeZK1Um5xKXI=",-2512778547635100636,5315882657292492992,-6327457395971754107,-8591989241826724025>()) {
                           case -1692843795:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3cdphw21yd0yx","Oo9F+FkhQpe1Kq68OZqYM3U7u6+QCa+xBWHL+/FAhdU=",421923862517929325,-7664392924041543496,-8375701200581016475,-3838399566634882191>()) {
                        case -1583688054:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var0;
         default:
            throw null;
      }
   }

   private static void a(com.yiyiaddon.l.g.a.d var0, com.yiyiaddon.l.g.a.d var1) {
      var0.b(var1.eh()).c(var1.ei()).a(var1.gj()).a(var1.l()).b(var1.m());
   }

   private static final class a implements com.yiyiaddon.l.b.g {
      private static final float cv = 36.0F;
      private static final float cw = 14.0F;
      private static final float cx = 13.0F;
      private static final float cy = 10.0F;
      private static final float cz = 12.0F;
      private static final float cA = 28.0F;
      private static final float cB = 12.0F;
      private static final float cC = 8.0F;
      private final String uU;
      private final Supplier<String> c;
      private final List<com.yiyiaddon.l.c.f.c> bZ;
      private boolean eh;
      private float cD;

      private a(String var1, Supplier<String> var2, p var3, com.yiyiaddon.l.c.f.c var4) {
         this.uU = var1 == null
            ? (String)com.yiyiaddon.m.b.a<"s1mcekdbp7td2o","5YPeZS6DpRyOjXRgD0enQDdcpX+iEHSF3fWPAA==",5957255401578981770,-4434391038142249956,8006778451068839696,-504310696184893183>()
            : var1;
         this.c = var2;
         this.bZ = var4 == null ? List.of(new com.yiyiaddon.l.c.f.c(var3)) : List.of(new com.yiyiaddon.l.c.f.c(var3), var4);
      }

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
         Iterator var2 = this.bZ.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s25pvh0cjofmh3","lARQXQc+TChlxMtQRsrXhbMhqTpaSTO0H32yJ3KjeME=",-8247204830594566372,-4713944601514271631,1459306355420792530,2971384530289317360>()) {
            case 200036263:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3544797fhyglt","of/CyxuV1WslfVuJIyKC5P03y4rXIwBt+BUwWGt+rPU=",7910181561936417054,-3277374129804961313,551741147633414614,-8615926197298025691>()) {
                     case 158073310:
                        com.yiyiaddon.l.c.f.c var3 = (com.yiyiaddon.l.c.f.c)var2.next();
                        var3.a().a(var1);
                        switch ((int)com.yiyiaddon.m.b.a<"sjoro3j46sl0n","ZsRUaztY/kVSQJA/abqpSe/xJkC3j/E3S9FwY/1px2Q=",5522383572917054305,1825463068744602972,-2698648896766414219,2254838112396072716>()) {
                           case -515015616:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               float var10001 = this.cD;
               float var10002;
               if (this.eh) {
                  label22:
                  switch ((int)com.yiyiaddon.m.b.a<"sqmzx1fxgdh4r","cmj/L/1M9mCXvKAK5s56JQjVGwLynXqJLx0lg1iJ+HQ=",2208345664905798147,6378717178101978341,-5664802670245757410,-25901129523542292>()) {
                     case -1966322057:
                        var10002 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2d37q7bdcepqf","YJBTKCBUX16QRuxYS7TB8Skt+oqjb7cl2dXO/egzNSo=",-5504186062937465527,5049681403258988456,1071026034397371190,7789421291673044577>()) {
                           case 954915117:
                              break label22;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s36zcl37shcqnu","0z+2hX/yWYaPmwLp/YxBrfmwdT4sRAW33JDdNYgnrqg=",5410520200702255676,5913001798792770703,8375860988240177302,5129912148052203680>()) {
                     case 462986213:
                        break;
                     default:
                        throw null;
                  }
               }

               this.cD = var10001 + (var10002 - this.cD) * Math.min(1.0F, Math.max(0.0F, var1) * 12.0F);
               return;
            default:
               throw null;
         }
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.i.c var8;
         float var9;
         float var10;
         boolean var10001;
         label67: {
            var8 = com.yiyiaddon.l.i.c.a();
            var9 = com.yiyiaddon.l.b.j.h(36.0F);
            var10 = com.yiyiaddon.l.i.c.y(var5);
            com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 36.0F, var9, var8.uQ, 0.7F, var10);
            com.yiyiaddon.l.b.j.c(var1, var2, var3, var4, 36.0F, var9, var8.uX, var5, 0.1F);
            if (var6 >= var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ub75hjasju6c","M8KjCWLuzdLs0znaUR9yP0KSps6IBsBIyvn/Rg0N4Lo=",-8198909103624086980,-1886513863418693172,574630671708740797,-1995415111402215016>()) {
                  case 1810196259:
                     if (var6 <= var2 + var4) {
                        switch ((int)com.yiyiaddon.m.b.a<"sngo6fj19fkz3","zy1yFdqQ0Pirg7XqMkrNdq8G4eslAGL4KPn/ohxVqdM=",419469261859613391,-1073651376324875347,141776315551939204,6350550479468084797>()) {
                           case -474366229:
                              if (var7 >= var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s17oeqj6zuwnu5","wz+Vl+7OYo76BJwMY9GW/q8A6+j+78wlly5amxM/gdc=",5657491388050712410,4009824787932263937,8506414704216754198,-8521011254697341385>()) {
                                    case -1110436160:
                                       if (var7 <= var3 + 36.0F) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3n31n8im6pi3y","SCyAhkIl+K9zLuFu3QXgulvnQ7eFL3wv5g1uHn81LtI=",-3188497226630567104,-264289635047086314,-3243873804931192137,2420269020701019824>()) {
                                             case 1698693278:
                                                var10001 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"snznng40bplz8","r0ZjfNifjS9YodMr0KxrAmW4vDj14l7YRTjPIVJDQUo=",231033760483957972,-2144630631091924828,-2451824527592569589,4964122671136684003>()) {
                                                   case 1091059264:
                                                      break label67;
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

            var10001 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s1b58nibp2aw5s","E59R2BZMXrykxyzlrem6ORQaCsli4yc3ktdRyz3EFR0=",-5961029249332811535,-2149253308005117655,3923038583011974642,8220776058389288347>()) {
               case 785462273:
                  break;
               default:
                  throw null;
            }
         }

         this.eh = var10001;
         if (this.cD > 0.01F) {
            label45:
            switch ((int)com.yiyiaddon.m.b.a<"s20t43nrqbs5d7","xIbLD2rjFSF/3l237WUtnz/eXJ9VgZgJsh1kNccHiZg=",297964634138230227,-2914857634094938605,-3303566104527055797,6895065913983681075>()) {
               case -620335709:
                  com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 36.0F, var9, var8.vc, var10 * this.cD);
                  switch ((int)com.yiyiaddon.m.b.a<"s2konso9sp2w2j","0tv+qnGsXKvLKqP7BG/TK3uYQiqIl8H8B58LqRC7DLk=",-4388201238283008256,7458688009084288492,-1821670376947603954,-8446896970458986079>()) {
                     case -2104628066:
                        break label45;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         float var11 = var3 + 18.0F;
         com.yiyiaddon.l.g.d.a(var1, this.uU, var2 + 14.0F, com.yiyiaddon.l.b.d.c(var11, 13.0F), 13.0F, var8.uT, var5);
         this.a(var1, var2, var3, var4, var5, var8, this.a(var6, var7, var2, var3, var4));
         float var12 = this.b(var2, var4);
         Iterator var13 = this.bZ.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3co0ic8tsrqf2","nLWZE9jc/V/6uLSdKfiGTxKhpxDp28UEmg1NZn5BXhc=",-8542825419692395695,-8506638521140336582,-7852567004177681250,8957736354614569685>()) {
            case 536628388:
               while (var13.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s6khy9v4ft6uj","6pYbyhkuyaWOAUJshkB7E6N5peJcOdCOcjTv+4XoOGc=",1769459934681461100,-8817384206420108749,6216264603046141580,-8331658795640848980>()) {
                     case 498236684:
                        com.yiyiaddon.l.c.f.c var14 = (com.yiyiaddon.l.c.f.c)var13.next();
                        p var15 = var14.a();
                        float var16 = a(var3, var15);
                        var15.a(var6, var7, var12, var16, var15.c());
                        var15.b(var1, var12, var16, var5);
                        var12 += var15.c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s1od5ki85is1oj","nh47W2VMy1mFvm2oq3oPH8Wj8lkv3NUj+dk0f3IqDac=",-5502837767459515713,-3894084153253866032,8709349954523224331,-7586782012827568768>()) {
                           case 400003395:
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
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (var6 != 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s3zu4ds1uol92","qvV0SeAkd2mO+rbmSPGMfd5rF7nFtBdV62TEXdppQeQ=",-1838451843433521935,6147768431863722414,-5071555369090375009,-8826321802495362155>()) {
               case -806016424:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (!(var1 < var3)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1rnedxgt4r0f3","HwSCTWc7N5LFL32rsbqpgwPP2AqEKoQdYCOXIZM/uiQ=",-371686813817223230,1960035371990623622,-3854814170749658920,-110629878338630535>()) {
                  case -1881000798:
                     if (!(var1 > var3 + var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2pke39qwxjkrd","sIxRnTCumESKtLU50CtGtFVUK6zurfa5tQOOiaR/Vqw=",-688557120005823176,-6335893785405384460,1017611086758096253,-4336455558981956347>()) {
                           case 808713981:
                              if (!(var2 < var4)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s29my44fgb5dl4","g+daocS5nSP+rOkExnjOo/L4ww/xR4zCwZ4vETEL8es=",5708395648275985469,5018727008266256723,4395650856565478455,-8593603884302455598>()) {
                                    case -1565627293:
                                       if (!(var2 > var4 + 36.0F)) {
                                          float var7 = this.b(var3, var5);
                                          Iterator var8 = this.bZ.iterator();
                                          switch ((int)com.yiyiaddon.m.b.a<"st7anlaqqezgh","KQovVIgvHucoZfXydRUT7v0z8+KeV0ncs/zdVqdNocY=",4464039715064672637,-5294706444885825320,-3516065572119309026,-6403153183602430300>()) {
                                             case 1084800122:
                                                while (var8.hasNext()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s6au4mxfbw0it","Szt+85MuuaVf5phROn/hE0/9pjKysg9UUX2F4fvWCbQ=",184466492054631029,2768429535695581614,7151097565959955518,-1575868820875725070>()) {
                                                      case 1121871550:
                                                         com.yiyiaddon.l.c.f.c var9 = (com.yiyiaddon.l.c.f.c)var8.next();
                                                         p var10 = var9.a();
                                                         float var11 = a(var4, var10);
                                                         if (var1 >= var7) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s9kufh5buk7z0","2PZjk0rh18C3O5oaxiI/8cfUsoG9qZJyQKlO9VA46RQ=",-3278374425341565913,-1587704455845706731,37491285817827018,7692782496182563877>()) {
                                                               case 2115256814:
                                                                  if (var1 <= var7 + var10.c()) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1tsz12cswurat","IJKIucwafQPsGwzha2e92AALWJAW4S8XmDmvQTGkvpg=",-5764957637522422250,3478281170621775335,-234738854142972830,-8688318847764881257>()) {
                                                                        case -1520032734:
                                                                           if (var2 >= var11) {
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s3oxfduuvu0yh1","fD/Os3hE07XQctKlA6OhRcn7+EgFO/fkXJRPQY5MEts=",5337396177066734098,6373235558239811944,2022799343684209909,443021534423171508>()) {
                                                                                 case 686506387:
                                                                                    if (var2 <= var11 + var10.d()) {
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s3dlvzzs43u7zc","hnUyvDK7oCM8zM46Ktt2YZ5DFjx4SbIGqtAMFvNVqeM=",-6341567802555803436,8144610065990479201,335719473629165966,8064080288922941218>()) {
                                                                                          case 1158164674:
                                                                                             if (var10.a(var1, var2, var7, var11, var6)) {
                                                                                                switch ((int)com.yiyiaddon.m.b.a<"so3ad477ut21t","M2GLiS8M/x+jNgV+Tzk68lQPUusT9j0adpWh42hhnPU=",-1851436364169679306,3870800187272460842,7750742134110493003,7841552045542190320>()) {
                                                                                                   case -1113546140:
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

                                                         var7 += var10.c() + 8.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2a78gayzjfwyh","bM2mYMVbnTSUkhTJiAaxd9j3K5jaGUq/4DUThPgKxJk=",7418862635782667284,4731783366282593339,4251159064052173885,-692427225151378554>()) {
                                                            case -216564571:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s2n8e1ymkx8q7n","W1Xhh27ZZ8aHt0IG5mFRsAu7CcwCxFlioINO1NSXw2Q=",1935363286350030674,4333401781922047844,4259938030146689122,-5850636300896171473>()) {
                                          case -1451809807:
                                             return false;
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

            return false;
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }

      private float b(float var1, float var2) {
         float var3 = 0.0F;
         Iterator var4 = this.bZ.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3h753dqwtacqb","O5MOXKXUbzICGGuJ6uwnPZCRrNvvj/jEqJcVk5m4z2s=",-4633927176533859667,-3304916815811171728,-8213037160287659394,-6505988798546957824>()) {
            case 1569693475:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s32vpog1fr452b","7zihJ6BY0oqon6a7PWKRFaeILnC0srLGvndZ4gyGUZg=",-1946970411686399310,2176877170696639183,2436738740218125506,-7321573739835852803>()) {
                     case -310712058:
                        com.yiyiaddon.l.c.f.c var5 = (com.yiyiaddon.l.c.f.c)var4.next();
                        var3 += var5.a().c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s1oo079ethjh18","QRxQ7gime4J0q/4quW1B1hq9oyZZ1zxFh6tSILSjZJE=",6295125418322624282,-8122454431701506349,-2843950703203087447,8758440461033314514>()) {
                           case -1432054928:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var3 > 0.0F) {
                  switch ((int)com.yiyiaddon.m.b.a<"sbkpqjyg4oqb8","0q0OO49pkVZEc+e2kG6Pc6E1VjbmGHkDUUfd1HYSq7k=",-7306101798429499142,-3977869270347814961,-2914173433683467163,-6616012752108281064>()) {
                     case -638910293:
                        var3 -= 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s1f77x2cuyhuwk","udgtSRUfg5Ku5xia6RB2z0hiPGMdFbDDPJv6VRXfS4s=",1765912143166925939,-7294086379174253906,416258086685521813,-5456401207889638287>()) {
                           case -787379462:
                              return var1 + var2 - 14.0F - var3;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1 + var2 - 14.0F - var3;
            default:
               throw null;
         }
      }

      private static float a(float var0, p var1) {
         return var0 + (36.0F - var1.d()) / 2.0F;
      }

      private String a(float var1, float var2, float var3, float var4, float var5) {
         float var6 = this.b(var3, var5);
         Iterator var7 = this.bZ.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sm3lusyqbko12","iTzp5puMQEE4dQ8G2ERUfkvLL1duxb0UubZTYnMwd/8=",2842595285989415482,-1190705206346082046,-7603846740619675083,-154469390926397205>()) {
            case 2082597561:
               while (var7.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zlwd6d8ojdin","LKJk4RUgZY3fThtLq+fmdW3NhVTUeIEoJC0RGOI6x2s=",-7639397082524082948,-7306737077892020579,6280417427064062758,7712496053907492577>()) {
                     case 1705791669:
                        com.yiyiaddon.l.c.f.c var8 = (com.yiyiaddon.l.c.f.c)var7.next();
                        p var9 = var8.a();
                        float var10 = a(var4, var9);
                        if (var1 >= var6) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3d74fy0fjbgtc","cX22RAnJTCw230aX5mUo9+2zBMOFqGDrdVZA2bIVWig=",2009651717749801579,-8435617395344760000,80707402928586799,-6363085999962764194>()) {
                              case 2061497142:
                                 if (var1 <= var6 + var9.c()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s29vipg81c2cpt","delqPapdIYk+mguOp2ix4U9uyz33qmM7SjX482gN+Ko=",7599758927288073262,-3049472057136482273,-8266296128144125473,5305567333475860999>()) {
                                       case 848696131:
                                          if (var2 >= var10) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3999ptrhedz4u","s3tzREeC8YlB6o5SfH+D2QTD1BolGHUh+uK3Rk0/6jA=",-5618195131803965765,1590603485429723304,-1280861066183836380,668883745482113799>()) {
                                                case 738437187:
                                                   if (var2 <= var10 + var9.d()) {
                                                      label65:
                                                      switch ((int)com.yiyiaddon.m.b.a<"ss0zj422pyk05","LhR56Zl6lO/UQVlrk9jmRzSAp7x0OnFOfVoVhDl8hmA=",-1942471332505869249,-8813495250190355405,6664139127068957531,3526923194398423348>()) {
                                                         case -448626670:
                                                            String var10000;
                                                            if (var8.c() == null) {
                                                               label72:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2e14jmtm8pp3k","DqqUWmjDq40LmjBeH8e21jVr6EkOvDP0DR85EjHMhB4=",-3454173481722273399,-8759294470175443447,-5335005957529749833,925938612813378137>()) {
                                                                  case 130493485:
                                                                     var10000 = null;
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s4ghdarnqotgp","ONIFrnHQLVulT3UjNx3ivLYkRWmh6Lvct7bMrHczqg4=",3881517309386226104,-7609278153145395852,-8186908176346920555,-3703634559884856859>()) {
                                                                        case -143235543:
                                                                           break label72;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               var10000 = var8.c().get();
                                                               switch ((int)com.yiyiaddon.m.b.a<"sntfeil4wrybc","Lm2rQAo+Yc3MfSZvmmkMGWcV9eur9oyAhhqeLawtls4=",-6813975252485294714,7970349455068422528,-1191765101337456366,-8204961427959762986>()) {
                                                                  case -491881749:
                                                                     break;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            String var11 = var10000;
                                                            if (var11 != null) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3m6ow5thy01x5","1lHK5HgsN/JjdIwxeL6eGW0IYsi/RnZ7kuh4wPHOJzc=",-3759460047607744873,-3442865897502464193,1275922519094925967,7306231087231475180>()) {
                                                                  case 1659977275:
                                                                     if (!var11.isBlank()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"si6rwm1r2v1ai","/4hEWj+S6TXGI2RkUn8cOIp2U+ld4UhsHqwvdK9KdIw=",-7746549705294255461,4106820176631200381,-8838560466944794334,6735546095676396090>()) {
                                                                           case -916467456:
                                                                              return var11;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }
                                                                     break label65;
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

                        var6 += var9.c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"sztj9gc4uy1vi","8+3+HYahyAyQI3HeSSDHX2bUPQY4GK+6UZVtpjUpvRA=",4109372955128200485,-2508182463869321844,3580046803706799628,6834936027701110020>()) {
                           case 1927674419:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.c == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1rcohcwbtwfeu","VqCX/xeLvn3RDfwHFtveCkeXbgcU3WdelcBMuA19UpE=",2956422267328525730,-2872682228832771213,-5222831064428588216,859900204779547981>()) {
                     case 852483167:
                        switch ((int)com.yiyiaddon.m.b.a<"s27ivhxug15ab0","pJxN44QPbg2EzVRMHrEduFhIK4L8Gi9YIBuWlJaOVmE=",2949544109503961830,7289219530156174765,-824690403410822942,-5203585310407236300>()) {
                           case 1628105770:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var12 = this.c.get();
                  switch ((int)com.yiyiaddon.m.b.a<"s1yh372pnos082","K9/Y/PXNIN7PvmuM1PuaSNuFWiNppWx2a0vXj1/cbfE=",3458859918517991265,-3548973647456095521,-4169688379721940051,-7096566407105039672>()) {
                     case 1936016980:
                        return var12;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }

      private void a(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, String var7) {
         if (var7 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"scqas120eh3ur","v3bZd/2tNKrwuAC/agR2CsSeqrGBp/O9yt3E2LYXBfI=",-1457037042864845462,-5667472816773189139,-4318411509756469074,-6558986647905329999>()) {
               case -272654458:
                  if (!(this.cD < 0.02F)) {
                     if (var7.isBlank()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ubsl9dgqi2n4","TmoshhotKQt669lglGPZLx4Emqyyggn6rgk36DyZjYM=",7887556299341494547,4361868937965252033,8514953459828817785,2208517898336491981>()) {
                           case -2044895343:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        float var8 = var2 + 14.0F + com.yiyiaddon.l.g.d.a(this.uU, 13.0F, false) + 12.0F;
                        float var9 = this.b(var2, var4) - 12.0F;
                        float var10 = var9 - var8;
                        if (var10 < 28.0F) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1mbt955ij669y","5uo3VbF7YuRpmRKFepB1XLTAQnAzFJhI08/W+TRU5+Q=",-3240497180687565279,3038033465625410666,6578858751717609882,-5789034847750590053>()) {
                              case -13723641:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.l.g.a.b(
                           var1,
                           com.yiyiaddon.l.b.d.a(com.yiyiaddon.l.g.d.cn(var7), var10, 10.0F),
                           var8,
                           com.yiyiaddon.l.b.d.c(var3 + 18.0F, 10.0F),
                           10.0F,
                           com.yiyiaddon.l.b.j.a(var6.va, var5 * this.cD)
                        );
                        return;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1p7e1zf9k1q5e","hASuG2nVPHf5afjoJBoiuYh5DfnzERhZjJPzXZFE75Q=",-2311387018295214742,3790980655855634796,1528743279697691406,-7508069696157817967>()) {
                        case 724827470:
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
}
