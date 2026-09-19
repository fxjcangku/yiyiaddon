package com.yiyiaddon.e.n.s;

import com.yiyiaddon.l.b.w;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;

public final class g extends com.yiyiaddon.l.h.f {
   private final Screen c;
   private final com.yiyiaddon.e.n.b i;

   public g(Screen var1, com.yiyiaddon.e.n.b var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s1eib8dju9j0f3","iMTmNvU8j7fEY01DI2GcxJfnO9H2LErc1ghJ3MJKzgpbpgH68NE=",-952829189080510058,6469314496288900703,201146477472280541,-5378881148161663226>(),
         var1
      );
      this.c = var1;
      this.i = var2;
      this.G();
   }

   private void G() {
      List var1 = this.i.aA();
      this.bw(var1.size() + "");
      if (var1.isEmpty()) {
         label20:
         switch ((int)com.yiyiaddon.m.b.a<"s20elu0tswbtmi","ZId/UaD9mGWRpH8oud+s1kk7c6OXs+6FN6pX/SxRhyA=",8240019545841333889,-8322592051209105138,8705075590482872121,-5282327971879854450>()) {
            case -568254257:
               this.w(
                  (String)com.yiyiaddon.m.b.a<"s2shyk6v6vtlyh","MTAsJkXSk5hV43hY3oalE76IIyqauVA8zf5wfNGGvbcfeTyF",-2566848800348389123,5453262011297938526,2789701998939591339,8136195178748520500>(),
                  (String)com.yiyiaddon.m.b.a<"s1f6zhyw2w9o17","x81/x5P8JGGX4reLjv19kbeUdoVtb15shktMYKSyAnKaj/RRu20M5g==",-1716810921117367686,5773866438279864752,8587699073056080517,-3381668081270046664>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"st9x6nd99hc9r","zC9JaV1tjPPACjk5GHOPLojyXPYuUg6SV/XMaiMfHG4=",-6448796987486884296,-3899578918176464223,5734301078075411941,977587151835975720>()) {
                  case -600851741:
                     break label20;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Iterator var2 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1yjfyhinyomfk","+nS8U+0AIjujmyHmmKg2tDbq/eb4DaxHBZjnS4T8Z0I=",3961578373935003847,8431179654037327894,-7700851262263381593,-4612467611048604254>()) {
            case 424434018:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3dfwvo01zl1ah","5N+fk5W55Y6uXugF777dZKxD73btupPNVmoAaTVP58I=",8283983344660894560,5956510770745158002,-2729348943269493128,6882811152301443890>()) {
                     case 1501881311:
                        com.yiyiaddon.e.n.h.c.a var3 = (com.yiyiaddon.e.n.h.c.a)var2.next();
                        this.d()
                           .a(
                              new com.yiyiaddon.l.b.h(
                                 a(var3) + "",
                                 () -> b(var3),
                                 new com.yiyiaddon.l.j.a(
                                       (String)com.yiyiaddon.m.b.a<"s22grhdwxcqx5c","rr5qcGF7C2XTrT3voWx0PUsn5KfNVXOTcDaXtrdDRY6tRaUK",4832041997495773848,6150223739267158414,-5362598700993973150,-3413265731595393962>(),
                                       () -> {
                                          this.i.a(var3);
                                          this.u();
                                       }
                                    )
                                    .c()
                                    .e()
                              )
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"sww1b84xcrw18","+PN+bZnQCQTNiR3PG5ZZNTfTxFonHx1jq3ZK9S6JmYY=",7464746412820076619,6649057348130163138,5357399269115540144,8387391249744940764>()) {
                           case 1770360388:
                              continue;
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

      this.kN();
      this.kO();
      com.yiyiaddon.l.j.a[] var10001 = new com.yiyiaddon.l.j.a[]{
         new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"s2hh72ovstb1kb","45Fq42tLQaYi/RESClJE/vv2LjrJl4Q6e2QSh9hNr17+HAuQZXtTcGoeXiRLEg==",864834648460629809,-9188548079346240665,-4317262649067853481,6011490967085843859>(),
               this::hD
            )
            .c(),
         null
      };
      String var10006 = (String)com.yiyiaddon.m.b.a<"stzl31dgqqoaa","y5ADAvKehwB/2aNRKsW2AiZ1mV2T5IEOScxdY4O/Kpo+AaVV",-6015950410173785891,6470557313903768781,-4386826602628651506,-6661033163735366681>();
      g var4 = this;
      var10001[1] = new com.yiyiaddon.l.j.a(var10006, () -> var4.kR());
      this.a(var10001);
      this.d()
         .a(
            new w(
               (String)com.yiyiaddon.m.b.a<"sisogbti3fm32","foBie65z1U0ZN3VklzYmZwMiODcmwYhaGBJLaomnLJ+2qGqHf797e2PJFdOwLPqTI4MHjaK4FzkHrLdN9QWUrlnSjf7frI0HdzDSnb2CwyWEm/V97CG992+0e3oiUNBIdAgZI2+5Ri+QpQ==",6759805747659774349,5669130726115633710,8550798678480221980,-721383680133083703>()
            )
         );
   }

   private void u() {
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2brk942a3x230","3ppn3KEc9PQ02Bkg04hSWHXpj/t5xDK7A+JaFRir8z0=",-8326641381757860732,-1883441221083155339,2811837041361086273,8458230425898450426>()) {
            case 398821265:
               this.minecraft.setScreen(new g(this.c, this.i));
               switch ((int)com.yiyiaddon.m.b.a<"s1ogwaebdklmcv","h9MGII6Ke8E7YUa+6ZtFsGAt7k2lpmxzXhPqsNPpL+c=",-3658501173264232344,8366211493614199424,-6692196006176398004,-4642553046602132363>()) {
                  case -888491028:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static String a(com.yiyiaddon.e.n.h.c.a var0) {
      int var10000 = var0.aj();
      int var10001 = var0.ak();
      int var10002 = var0.al();
      if (var0.dw() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sznfxww1cuonh","1jsNAApmvYi2naD3NKBnj09t3+Y6CfndG39yizLJ30E=",-383312467507139522,8056697624856250667,6835369898288909418,-4006604075534753191>()) {
            case -2042057211:
               String var10003 = (String)com.yiyiaddon.m.b.a<"s12moh5mj4jbd3","kI9vv1FrI2ye0P2HXRUmRJzW3xT1ovVFmgBsjO097Qbx4g==",9109614093642823057,8603838795449130427,-2314162029955485204,-3010172331546226231>();
               switch ((int)com.yiyiaddon.m.b.a<"s2j9tip44cifp6","6MOAkOUO2UVmk8IBbFifFU/l286N4SaE84IhSizf6LI=",-8179561352024995234,-6490442512171595692,-333847810071508934,693509516355653356>()) {
                  case -2055644523:
                     return "" + var10000 + var10001 + var10002 + var10003;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.dw();
         switch ((int)com.yiyiaddon.m.b.a<"s2roxusz6dnl0o","uQTloJq4bZ9W6wMY/lwxNCQYtcWg/Nwnswm/s+1OFvA=",-5422998116976070222,-8055239632043058668,-5160323392479254744,6845884064544042198>()) {
            case -1445742746:
               return "" + var10000 + var10001 + var10002 + var1;
            default:
               throw null;
         }
      }
   }

   private static String b(com.yiyiaddon.e.n.h.c.a var0) {
      String var1 = com.yiyiaddon.i.g.b.bV(var0.bU());
      String var10000;
      if (var1 == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s2ywiauv18xhqe","+nwHA5HrO850rHOOvWrWZW7E2SHRW4VPgfatY2cx3B8=",3950487877129944082,-8792127509242146973,6714666206456712190,2251590478620044220>()) {
            case 2103729167:
               var10000 = (String)com.yiyiaddon.m.b.a<"sacuzllq333b1","trR5J8GmEz0b3TNpDRHqCMMLyD6DCIVp4N+2Nq74hiE=",-8896147464517171872,-8841896178372726395,-400717052814021295,-2740701102896307370>();
               switch ((int)com.yiyiaddon.m.b.a<"s205806vyvyf78","l772TDzc8wekn85fJ6TZA5OvRy2TElTFCZUfZZMRGXI=",-2733139248471715962,-5700642431679279069,-1442948459984140354,-2475051099483197336>()) {
                  case 336002496:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s3gg2wkshza033","QiBZzkNzXLoHUK55Knicu5ATn5Tr64q9wFQhddKpeK4=",6074839640726999424,787223197396730844,4212577881796708595,-3172706461240925098>()) {
            case 1753056571:
               break;
            default:
               throw null;
         }
      }

      if (var0.du() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s36t0tqandgqvw","Lr7kz7hw5ftMhNkyr3Ph5vSh7SLBUDaRqT5TOqBzgiY=",-5508875075833810719,-7305721338710356410,-3992542423564810431,4004818160685824960>()) {
            case -554438815:
               String var10001 = (String)com.yiyiaddon.m.b.a<"s3pmdp7xpglbqk","RtK7a1kzJp+mYxmAjXdoHjlJkFLnSmJCy/xkSvZU4nrqpQ==",8877479467912933695,-5191752979900815102,-1453769289111689578,-970826138602124885>();
               switch ((int)com.yiyiaddon.m.b.a<"s2qd7o9tb08id","JlvQ/asn+U9aTQip4JROg/A+z2HgTlPn27AZalkGWHM=",-5133345398592678841,-7144068695281292305,3530819173799704878,-4985412571675537252>()) {
                  case -368601872:
                     return var10000 + var10001;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = var0.du();
         switch ((int)com.yiyiaddon.m.b.a<"s2zp6on06j0700","9oUCUto07EW8rIOGbjm75pT60F1Qh6VtKclTINBPXTg=",-8109101819986694467,4419880618624125885,-7779545456698564789,1607937223634075422>()) {
            case -1825567042:
               return var10000 + var2;
            default:
               throw null;
         }
      }
   }

   private void hD() {
      if (this.minecraft == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ywutq1aa6gyj","mV6SQF0+yk/5IHfksn1V4PZcNDMtt7UIoc/WdcZHSBY=",-4078428075307502839,-6226153339175326223,-2527673207221271377,5087355239100529490>()) {
            case 140581367:
               return;
            default:
               throw null;
         }
      } else {
         this.minecraft
            .setScreen(
               new com.yiyiaddon.l.h.c(
                  (String)com.yiyiaddon.m.b.a<"s35i27y7c445hd","bQPmgVOBdiWaCdDsRYQNuPk+K3rNc714XRfhdv7DSs3Pjpi75XUvxwD2",-4890560258113627516,-7657540433119774835,-6848612015839412781,-1435124684151969822>(),
                  List.of(
                     (String)com.yiyiaddon.m.b.a<"s2eiys8tnsyus5","SRZ10+ZF5lbEyWtFjm0Wsxr9hlUydtEIjN4UEyUE6BjFJI811o0nsHyUxbPERz7bRRl1Sq21bXGguyQhYkNocdqYz7yCaA==",-571576974106816710,1916232057667533954,5540465915774026228,5563282629142787144>(),
                     (String)com.yiyiaddon.m.b.a<"s3kvjzorlym61t","FagspPeafr+1C36kw3cjgff6swziyMrAcrPNEn4O0velated0757u+E14frHaEpdnPe8/cGtJariY6DSW3iDOZ1E641o3AG0VeEdo8tIKODES6UB",-5164953712332266042,-7917865202049427503,-5388301848209443110,1847285307174916624>(),
                     (String)com.yiyiaddon.m.b.a<"s36yznmcbwowce","Cr/kQoHocIgD56KRFKAYH76CalWw/hIsOjQAdw==",3011467065381238575,6667449750534964320,-6128954848720644182,-3417393852732220829>(),
                     (String)com.yiyiaddon.m.b.a<"s27udl9shrwb47","JIpahKasT6iNrPvzabRYpmizI2ctMVhl5Fd8DDb01izlQ1abR4edXUmkNEcueDvn",-8532774414089378865,-2638747321797008501,4669840190454606116,-710780893496219493>()
                  ),
                  (String)com.yiyiaddon.m.b.a<"s2wb1qr6ovx3rg","Lbtz2nzzZSycH1oWOJJFN9J0VuMA2ia5SmN+8CxMunJmD1ums9Ew0g==",-8460452166540723173,-3786475415754547400,5867260641853414883,-4952144081461981046>(),
                  this.i::gk,
                  this.minecraft.screen
               )
            );
      }
   }
}
