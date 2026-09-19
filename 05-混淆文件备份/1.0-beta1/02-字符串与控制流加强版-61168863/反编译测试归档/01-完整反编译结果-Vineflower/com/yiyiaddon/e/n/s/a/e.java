package com.yiyiaddon.e.n.s.a;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class e {
   private static final com.yiyiaddon.e.n.c.a g = new com.yiyiaddon.e.n.c.a();
   private final com.yiyiaddon.e.n.s.b g;
   private final com.yiyiaddon.e.n.b n;

   public e(com.yiyiaddon.e.n.s.b var1, com.yiyiaddon.e.n.b var2) {
      this.g = var1;
      this.n = var2;
   }

   public void d(i var1) {
      var1.a(
         new com.yiyiaddon.l.c.f.e(
            this.g,
            (String)com.yiyiaddon.m.b.a<"s2ikdcxdqvlzu7","ZmFhdXPcvjOi2Se62tBNO4XBynVThkXxvTLH55IwDsrGWOI3JrAz1h8EOGq0N8g0UFO+K6Xw5nS5YhhsKMRE4mN+MoQwfgrfo6dbhkM0",-926064562136421795,4534566423459153379,-7989714134271921764,-8055487597829290269>(),
            null,
            24.0F,
            12.0F
         )
      );
      var1.a(new e.a());
      var1.a(
         new com.yiyiaddon.l.c.f.a(
            this.g,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3m7ok33z3vx9a","4lC0eoY5FatJ2UMB8jn4iBGh2fcAPg1gLghYQUKfHXcV5+gNV/hmJgJYYDg=",1170891126163217443,5863104360707693103,-957493730352679731,-3100846625708950185>(),
                     this::fE
                  ),
                  (String)com.yiyiaddon.m.b.a<"sev2gwt7o2aj3","oRy1qht7j3pCmtGyYepcOjkO4VsM3d9oN1ZsQiY9r0z2fOzFwcY+320lcFL5O9AttMC4joCrvnDA7zq4QbGZJzxNu6DVuIJNpzEAYFWNj3vjwg==",7928446862402968557,6432167318739717556,7762445137893056263,-7176974701182109475>()
               )
            ),
            24.0F
         )
      );
      var1.a(
         new com.yiyiaddon.l.c.f.e(
            this.g,
            (String)com.yiyiaddon.m.b.a<"s15ycl5onjrmbj","dC6HyRS6/zDVZXc3/858PmomxYEgc5yxCpwHR3TlncYJqlWyhjS3CixAzTEQsA==",7445026506517010143,2487266746870895770,-1078116020610109471,3990927460004225839>(),
            null,
            24.0F,
            12.0F
         )
      );
      Iterator var2 = this.n.a().aL().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1jyd19hzpwmcw","ENWgyH9etizhJp7tPqHCdXFkQCIjADATAWCzQHjEFqY=",-8803232041998068023,-1630112394134926928,6045719854425286130,1489387488894880152>()) {
         case -1419669920:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sskqxqlbpkqrl","PCp4E+3ZEnr6yuFnw4y2pS8b99E4JbZf2MTLPbmhHcc=",4258750222229899172,3029901728656608348,-5535522895969471812,4712828250045967963>()) {
                  case -466482995:
                     com.yiyiaddon.e.n.c.a.a var3 = (com.yiyiaddon.e.n.c.a.a)var2.next();
                     var1.a(this.a(var3));
                     switch ((int)com.yiyiaddon.m.b.a<"s1gbutkjmfh108","wK/QMrYsr1Eb1Mv+ohPJbELixeBr3kI+1jCo18ZB2d8=",-2884245960203076439,-5156578192602349443,1118688421592508655,-2260642923749783300>()) {
                        case 560979543:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.a(this.c());
            var1.a(
               new com.yiyiaddon.l.c.f.e(
                  this.g,
                  (String)com.yiyiaddon.m.b.a<"s1xdd02vs2ohiu","HdsUilvPfwRb9SBe+O/B7yCIMEq1hX81ym7VuW4omFGUwKcVyjQw4OD/f/LGB5Zk2w3bDFobb9JY/RIMyJrPs0tkB7rax4PMmM1XnRlzvUFewt19P3XJ3KDozATda+kQMao68BxDovZT16kwAq+S3TqyREw=",3602318367503688555,3783602822026892590,1614460568951627728,-5164280991440200282>()
               )
            );
            return;
         default:
            throw null;
      }
   }

   private g c() {
      com.yiyiaddon.e.n.c.a var1 = this.n.a();
      com.yiyiaddon.l.j.i var2 = new com.yiyiaddon.l.j.i(
         6.0,
         32.0,
         1.0,
         (String)com.yiyiaddon.m.b.a<"s1hex9t2mhmkfn","+8dcY/i3+Q4WIcktOmLoxIZ586OlHb+ra69ck0roqf3p60MJ",6693670969451926642,7961615645926964674,-3613680878039336302,-150504851590694543>(),
         () -> (double)var1.lF,
         var2x -> {
            var1.lF = (int)Math.round(var2x);
            this.n.L();
         }
      );
      return new com.yiyiaddon.l.c.f.b(
         this.g,
         () -> (String)com.yiyiaddon.m.b.a<"s28w5vc6nnwga3","o38IUKJomK2IWd4Ab2rx4/LP9U/QnIkQO8L+0BgjMRTyL7In",-2807665786678592129,-6973518540195491100,6392836118090869945,7074438511670747175>(),
         (String)com.yiyiaddon.m.b.a<"s2iral1cvppb0m","pkg0FjB+fwRMve7GA2aibF25IuzjdzwxoN5fsAYULjpBU5k9XP0Uth5IFrFWSvkO9Yhv9I79Ll57CbkbCkP+UI9ymJ/xvhm8stI2oDmzh3Z011zLBCHW6QQdXbUtrUlC1GZXLfEfTbIBtJvd9NJSF95ICDXbCKMz",-141929826191478393,1713276648728387299,7327553932822895542,1083346362966967438>(),
         null,
         List.of(
            new com.yiyiaddon.l.c.f.c(var2),
            com.yiyiaddon.l.c.f.b(
               () -> {
                  var1.lF = g.lF;
                  this.n.L();
                  this.g.C();
               },
               (String)com.yiyiaddon.m.b.a<"s28w5vc6nnwga3","o38IUKJomK2IWd4Ab2rx4/LP9U/QnIkQO8L+0BgjMRTyL7In",-2807665786678592129,-6973518540195491100,6392836118090869945,7074438511670747175>()
            )
         )
      );
   }

   private g a(com.yiyiaddon.e.n.c.a.a var1) {
      n var2 = new n(() -> var1.dD, var2x -> {
         var1.dD = var2x;
         this.n.L();
      });
      com.yiyiaddon.l.j.a var3 = new com.yiyiaddon.l.j.a(
         (String)com.yiyiaddon.m.b.a<"s21c8c4jkuzxrr","w/mi+BDfFpEnfnxZ7M+ox5utjIqTtjlWd4wGwUhc/Ks=",-1939713897364091370,-8604787736901081019,684179303854125821,3418695108608629262>(),
         () -> {
            if (this.g.a() != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1mqaafpk7gkde","ufxX+RxVlTuhoITaxi/aiEJAxgvplESqPXt4naLrpUU=",7758076223843623431,-4605050080735533290,-6644607956782728938,-7285251426196738871>()) {
                  case 1441616511:
                     this.g.a().setScreen(new com.yiyiaddon.e.n.s.e(this.g.a().screen, this.n, var1));
                     switch ((int)com.yiyiaddon.m.b.a<"s1xwanmn7ha23b","nSB0SqvLUKF6LSrZyuxWpuRxoK5Sv70njWuz2Vb8RPg=",-2735393977786879224,-5253723945796951636,-3829671597417639273,4419326520598461869>()) {
                        case -1750040868:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
         }
      );
      return new com.yiyiaddon.l.c.f.b(
         this.g,
         () -> var1.a(),
         var1.c(),
         null,
         List.of(
            new com.yiyiaddon.l.c.f.c(var2, var1.a() + ""),
            new com.yiyiaddon.l.c.f.c(var3, var1.cZ() ? var1.a() + "" : var1.a() + ""),
            com.yiyiaddon.l.c.f.b(() -> {
               var1.dD = b(var1).dD;
               this.n.L();
               this.g.C();
            }, var1.a())
         )
      );
   }

   private static com.yiyiaddon.e.n.c.a.a b(com.yiyiaddon.e.n.c.a.a var0) {
      Iterator var1 = g.aL().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1qz10lfsldb80","30s33r3i1o3Ay4GKhjEkbiie9UX9u7V6riVgCiQLqLU=",-1171865088875994537,2529391735856847001,-5762462068092890027,8432340985665431602>()) {
         case 2096037161:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s8rmfsg003mau","k1uFRjylXitnhONQREBzrSJtp7XB493SazSpY85O3w8=",8649505374024404733,-6319886428997162885,6351339055259853707,-5861173321674096152>()) {
                  case 686735837:
                     com.yiyiaddon.e.n.c.a.a var2 = (com.yiyiaddon.e.n.c.a.a)var1.next();
                     if (var2.a().equals(var0.a())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1h7eqis0bz7ue","Yge8Bamku6OnumOtG4cFpn6uliBSM89A01Iqa9Jrgh0=",-6398767278951463251,3936411192821292831,-2104895191507749952,-7860418912558413346>()) {
                           case 2049470162:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3bkkxt7uqgktc","Jrzfra8r/xdOEr3NXPZhLXHg0u7/1LfJh+vWlXmGb5M=",-1057322641387139282,5191202002538273802,6506781529991091001,-8070461439868554001>()) {
                        case -1278167156:
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

   private void fE() {
      if (this.g.a() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11ej4577at440","+q5bj/MwdAM2RGzwcgXBvXXSqe4jGQW9qyw/P6fXqWg=",-5792135356078342801,-9021146799673013859,4191398296694018511,7040570032952934300>()) {
            case 960993409:
               return;
            default:
               throw null;
         }
      } else {
         this.g
            .a()
            .setScreen(
               new com.yiyiaddon.l.h.c(
                  (String)com.yiyiaddon.m.b.a<"s3lo1uqu7u7bxh","eYiyxPTE7RtSy8cVCvLI8FKrJjFczYpfqgdwea2hoCCyGzNE52Px9Q==",-1041468620809930515,6697609374915892058,4915550601611363583,8544665366171225600>(),
                  List.of(
                     (String)com.yiyiaddon.m.b.a<"s8p9b7vk5wx1w","sRoP6DNWKy+n0NppLrJdVKzeN0rWcx9jdqpcCUxY0IZdEKvsZa/q7E3WSJk69Ne+hyPrCrEZNlm5dyZDjZzwmQ==",-495272401837568375,-3037299108731768635,-8197822362910292057,3327275201106303962>(),
                     (String)com.yiyiaddon.m.b.a<"s2yq2r2aitjebq","oxEcnekuDRxkuzL5KFjNgU0eQJA4bgR4YaXLkw4WjY3z/NwkU7AKD3PZUaztU7pp3kon0Yjl9z1BoO+DgzPndhcEsdRUXmG9sTNDBm6qzxyEcSjj4aJvzG90+iZjng2ipk0vJzeRHfbrs64l4F0=",-1495000794125500281,7308645177469396896,5787919036020077201,-5881284642753920241>(),
                     (String)com.yiyiaddon.m.b.a<"s25lkaed79hy7c","jSn/UfbQsfE7C3yCZf0maiPO4NWHq6A0+hkLdg==",-6292719797818674263,5682518123117497020,-9126430348755222720,-3745605277696930241>(),
                     (String)com.yiyiaddon.m.b.a<"s3hu7vml7n6j9b","Fk7wk6IC+pNnAffXZ1DQmrOMPFuLndn6wu46joFmDc5OZXoMgBrQqRhgGI+uQd1y",1835631827600712231,302660568609009415,8486410631158963647,155119722524307794>()
                  ),
                  (String)com.yiyiaddon.m.b.a<"s17sbjku846j4v","fd+gDEOi7Dnvy9/zsgL/ZolEpaPgNge7WET7OBVELRO8YIh5xOAAaw==",5253336462547153811,-7411227711594115508,-4302714854092061681,222415056470731621>(),
                  this.n::P,
                  this.g.a().screen
               )
            );
      }
   }

   private final class a implements g {
      private static final int ol = 2;
      private static final float do = 10.0F;
      private static final float dp = 6.0F;
      private final com.yiyiaddon.l.c.g.b a;
      private final List<com.yiyiaddon.l.c.g.b> cb = new ArrayList<>();
      private final List<com.yiyiaddon.l.c.f.e> cc = List.of(
         new com.yiyiaddon.l.c.f.e(
            e.this.g,
            (String)com.yiyiaddon.m.b.a<"s13eeqqfsrkt3z","ha2zfHj7BeSaIEsB5uRKVRQ+DLASsOrUoYbo2827jSn+OGf77YPwTBr8HbI/Nyn2lZYPZHn7DbvF5FxqOVOwEkEQScIcSinRqGCDM1rM4z6TJwxDlTvJULbyzzU6NRqH2WJ52PBTaLs5cFyGBXjQQABeOPCbT5qHmk4=",-1195998979784709904,5480798086393912463,-7249157246651511363,-4664365987774070886>()
         ),
         new com.yiyiaddon.l.c.f.e(
            e.this.g,
            (String)com.yiyiaddon.m.b.a<"stlj8xm97dc6r","rPcDr16cZ0XPwyR9Vu+z+kKYgKu+tX/drX6/yiutEm5UteaQQ4IgphLW1qe8U04Kcs8RzHaLTBYUOxo51wFQWLZwPgfa7z1jvSHSjG0w7NTky0V5hLrO3QP6uLnG+Q==",-6068255577466727576,977912511979687349,5914379087577422871,2097475791116096681>()
         )
      );

      private a() {
         this.a = this.b();
         this.cb
            .add(
               this.a(
                  com.yiyiaddon.e.n.h.d.SEED_BOX,
                  (String)com.yiyiaddon.m.b.a<"s2b76vdov1471f","s6CnQWS8oVOBC2limiXxBjSjqzCR5Oq1EMw/uSpTqCI=",-3808387817560465075,-5741625523226470284,509549353864022525,-641502135206539835>(),
                  (String)com.yiyiaddon.m.b.a<"stmkqv7ft9xpt","pDbTzv9hK21h2Yy6CBbu4129vVDvTrgtnwMIu2HA+l45Bw==",6380753454080708162,-3499835735091238042,-1223059278496738077,1231649613204518183>()
               )
            );
         this.cb
            .add(
               this.a(
                  com.yiyiaddon.e.n.h.d.OUTPUT_BOX,
                  (String)com.yiyiaddon.m.b.a<"s3p26fey0oiish","0SillJgg1jhVRVD4Xge87FFsqDKtzZZoPlDlRH7bWYM=",3466278463340453014,-3847594753766256445,2316500322236193082,-3464840252425851304>(),
                  (String)com.yiyiaddon.m.b.a<"s39bz3kyxwh631","AdFiMHK8jDlyZhG4/2tozG83OeTuMM5AqVpj91mW8/6lkg==",-3210556446537757390,8655027509320896908,105456790656134258,-8768253821203241492>()
               )
            );
         this.cb
            .add(
               this.a(
                  com.yiyiaddon.e.n.h.d.WATER_SOURCE,
                  (String)com.yiyiaddon.m.b.a<"s2u2fraarganbk","CoU4OyjuOM4taMHnd7bLjU2EPHGyzLuXYYEHoHQ/QH4=",1591697311668617110,2932045148941423189,-4121316806258703357,-1816614735358969441>(),
                  (String)com.yiyiaddon.m.b.a<"s2kiwskfdj51l7","zVTIAcpDrIqEjWEYcIcJXjgHCf/9gXZR/oJlCY5VRWKkww==",266829437730485917,8883950173456642739,-8126729631511713514,-2898835422577146782>()
               )
            );
         this.cb
            .add(
               this.a(
                  com.yiyiaddon.e.n.h.d.LAVA_BOX,
                  (String)com.yiyiaddon.m.b.a<"s2qtwfbax9q7q7","BCuo/K+uncphAslSJmrs1bRKv1daLMbdKC9N3HhzJic=",-4061494067114579519,-7358304123008701888,-186788814839090419,8348321496706077199>(),
                  (String)com.yiyiaddon.m.b.a<"sapy1nebnvb33","GjFF3bRVrBfDN2P92Zec/I832rd0dWnkOHAUpqVDArZdAA==",-7131939384149026570,-9050407046645895273,-3661396752638186908,9138875404482727332>()
               )
            );
         this.cb
            .add(
               this.a(
                  com.yiyiaddon.e.n.h.d.BREATH_BOX,
                  (String)com.yiyiaddon.m.b.a<"s3k6cmiq28m7g2","pGYUE7CDuxs6VScXz9tAxCG6Jjzi1wfrJZuCh+8P88w=",-4120990017512350462,-5740725164461779653,7163338842052114058,-1620414051487440875>(),
                  (String)com.yiyiaddon.m.b.a<"sd26k7shwdr6v","wMSGKOubZRoulxTtUjyPE8Q1cb3uxVcAjWZY/+bVCXBPBA==",-8051869734200844269,-8901169779290231803,-5316184941929188188,1322707571498050216>()
               )
            );
         this.cb.add(this.a());
      }

      private float m() {
         float var1 = 0.0F;
         Iterator var2 = this.cc.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2uzo12qsd5h60","36oxqIzTvm/KNmitiF4t1UhMjAf2vV46Caqi7XoI+LE=",4716915400976747785,4223253308214861576,-8344555921332076506,-3777934050854482063>()) {
            case -1858243363:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3mqs0uwwubeza","R6DF05KSA0ztaTyO72cMcLTgCS4ciARsW8k1rPkk0Ss=",-6810554062536368692,-581761386206661387,-2264354290880903832,392432130328162537>()) {
                     case -398162561:
                        com.yiyiaddon.l.c.f.e var3 = (com.yiyiaddon.l.c.f.e)var2.next();
                        var1 += var3.b();
                        switch ((int)com.yiyiaddon.m.b.a<"s1u7ycdbjwht0c","70LfL4TASIPZHdDcc+XQU5bAGhBDJ7tcx3ldXJkRWdY=",-402983087902236732,3428358542187284122,6614511091598297952,-3969378378739352623>()) {
                           case 241752555:
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

      private com.yiyiaddon.l.c.g.b a(com.yiyiaddon.e.n.h.d var1, String var2, String var3) {
         com.yiyiaddon.e.n.h.c.a var4 = e.this.n.a().a(var1);
         String var5;
         String var6;
         if (var4 != null) {
            var5 = String.format(
               (String)com.yiyiaddon.m.b.a<"s1p4s73fbqhb56","Mn3kns4v5nXp+/FuvvbqTt6YQ8IaZnNp4pcq9fdoaFU0Scgalu8mBTUieoiDwlbVVqOS3EFjjaFdEDuJuXIirQphQ28kbpALt8g=",-2014620076905051952,7644738508451901700,1559462443339335496,4448097293483553426>(),
               var4.aj(),
               var4.ak(),
               var4.al()
            );
            String var7 = com.yiyiaddon.i.g.b.bV(var4.bU());
            var6 = var7 != null
               ? var7 + ""
               : (String)com.yiyiaddon.m.b.a<"s1tyf1ih4yix2p","/3MU7IofxWL9yH4Vs9gOgxdxb8rC1QPgtFur2V8bq6k+8Q==",8891102940396531690,6847629952174712079,-3433736699079843940,-16423178227244760>();
         } else {
            var5 = e.this.n.a().c(var1)
               ? (String)com.yiyiaddon.m.b.a<"s1s7atwvygkf8q","gYWk5TAwG1Hlgzvm01IDourQxNZZwZpghG4BED7Ph+akQXNUzH6dp7ohDNDmhnVE1eLrKMLlgCY+AUVss0Jok/Qc",718621266448427961,8749661063973455068,-1689979909353044539,1584321616911797290>()
               : (String)com.yiyiaddon.m.b.a<"s3is1lkvrkw5ub","Y2/w0a84KK5OUWj3XRkh22i7uiKu7J8AKzPXlXQ20qFxGHzTXU2iZA==",2933045498349361964,-6681661040047371013,-792612887886399394,-7374150824367578503>();
            var6 = (String)com.yiyiaddon.m.b.a<"s1tyf1ih4yix2p","/3MU7IofxWL9yH4Vs9gOgxdxb8rC1QPgtFur2V8bq6k+8Q==",8891102940396531690,6847629952174712079,-3433736699079843940,-16423178227244760>();
         }

         return new com.yiyiaddon.l.c.g.b(
               var2 + var3,
               var5,
               var6,
               List.of(
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        (
                              var4 != null
                                 ? (String)com.yiyiaddon.m.b.a<"s3so7815ogqd4p","DJka9yU5SVtUIJod2whcRSaAefmtZUqiUL/kBE9GDqE=",-1165047433521220022,4108391826054424993,2747828368724478025,-2214968859558027812>()
                                 : (String)com.yiyiaddon.m.b.a<"s81slmi5moeyo","94vfArV6K+8gRmdk9ckH6+lRrR0Td7vVm0SRn5/ihYc=",-7455059718943421193,-4546727505393575016,6839042517811603567,-5506251893135280240>()
                           )
                           + "",
                        () -> {
                           if (e.this.n.a(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s25bop2m1b2mxx","Nv23CbuxjlouBdvYVqOq1tGeMAkfJnec9X50V7XzV28=",4061335619702644332,-4432984541712332683,-2828534188452103291,-5022099064868671495>()) {
                                 case -412437284:
                                    e.this.g.ah();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1rln34d23vm8r","cJmUKP4yKZD2J9vTzPxSZaahXgaWMEzwsn4VQI1Hfwc=",7783450568587602063,5841412263572372710,6386901600413951069,3166654395645005963>()) {
                                       case -408364505:
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
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"s3pi4ibtnhf2tj","rQUnnq76VzS0H+ben/fRT5D3NMmYivsq6+5/aLxn0PYspyMI",-3748166404786093502,-348617462500399990,4163554924639622391,-1499319676229699220>(),
                        () -> {
                           if (e.this.n.b(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3hry5wfmur3v3","lj+uiEtE8JdeGA5eCdVNC6QjR2h8xqpb1V6DCmDi3Eo=",-5754113304778659387,6271915309856681315,-1112769750574297790,-2406212175605927641>()) {
                                 case -1617246030:
                                    e.this.g.ah();
                                    switch ((int)com.yiyiaddon.m.b.a<"s36ra83ub17vjh","pJzKZQDvs2K/qoJtfs7t1Ovsq3ZRwnLd1/QYNSwD0DA=",8450559559604123083,-1070161004137743912,1661792666314541370,7732170131395867691>()) {
                                       case 1975713725:
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
               )
            )
            .a(() -> this.a(var1));
      }

      private ItemStack a(com.yiyiaddon.e.n.h.d var1) {
         switch (var1) {
            case SEED_BOX:
               ItemStack var6 = new ItemStack(Items.WHEAT_SEEDS);
               switch ((int)com.yiyiaddon.m.b.a<"s3kap6wjyicdne","wfpsog3ymk7t14NmbI4zJygEBWI7j9kHzykv//6d8FQ=",5244497391287433255,-4104435967361835934,-4059449010639046144,-5354110720114843731>()) {
                  case 1675269354:
                     return var6;
                  default:
                     throw null;
               }
            case OUTPUT_BOX:
               ItemStack var5 = new ItemStack(Items.WHEAT);
               switch ((int)com.yiyiaddon.m.b.a<"s2faedwl7a6fn9","jSjtSuFv/l3vOGhyjr2J+eGBCFfhZ3XN+Yt1WZrURog=",-1124600208661748859,-7973908934692855826,3807320213000599053,-8489615637218717867>()) {
                  case -866650545:
                     return var5;
                  default:
                     throw null;
               }
            case WATER_SOURCE:
               ItemStack var4 = new ItemStack(Items.WATER_BUCKET);
               switch ((int)com.yiyiaddon.m.b.a<"seb4wydureysd","c2dZ19iOha89Az+xsy3htfy6BignfY6lqLgjWH4puGE=",6386417327348043687,-3293599194428054314,7227279582362553447,8859843177258747458>()) {
                  case 15950647:
                     return var4;
                  default:
                     throw null;
               }
            case LAVA_BOX:
               ItemStack var3 = new ItemStack(Items.LAVA_BUCKET);
               switch ((int)com.yiyiaddon.m.b.a<"su4nrobl6ctwy","1TB7wMMc6Q+Ah9NSCONUH2o5kpnvNi3oLeLJ0fUaEZI=",-3848764125589955451,9133951083228906251,-9048460395732762005,2801258940054676171>()) {
                  case -2016590837:
                     return var3;
                  default:
                     throw null;
               }
            case BREATH_BOX:
               ItemStack var2 = new ItemStack(Items.DRAGON_BREATH);
               switch ((int)com.yiyiaddon.m.b.a<"s3mlu5i8ugnoh6","RueCAGrjEa+hz4pOWuU8ub767JchIAcdGNEffqoxPLo=",4620409790779483070,3809654423025314247,1893021881501052324,-3190839390324532492>()) {
                  case 1939197374:
                     return var2;
                  default:
                     throw null;
               }
            case SPRINKLER:
               ItemStack var10000 = new ItemStack(Items.SPLASH_POTION);
               switch ((int)com.yiyiaddon.m.b.a<"s18iaf98vixtfh","lQJDptrmUFM2UMqs/gbZ5aatBwlRoe7Xc3HWZa60T1E=",-1179430406776628183,3733738088763739693,2681809347779994298,-7153737677817380094>()) {
                  case 451500799:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }

      private com.yiyiaddon.l.c.g.b a() {
         int var1 = e.this.n.a().a(com.yiyiaddon.e.n.h.d.SPRINKLER);
         return new com.yiyiaddon.l.c.g.b(
               (String)com.yiyiaddon.m.b.a<"s27lfzl917p7rv","Sf6Nu5ne2BBWXnX/liDl4q6UjBmGhtzvyn77t8mmf2XOH1bnxkk=",8006067968916732093,-2846402032511458316,-943564870720698825,-3220397595704173307>(),
               var1 > 0
                  ? var1 + ""
                  : (String)com.yiyiaddon.m.b.a<"s3is1lkvrkw5ub","Y2/w0a84KK5OUWj3XRkh22i7uiKu7J8AKzPXlXQ20qFxGHzTXU2iZA==",2933045498349361964,-6681661040047371013,-792612887886399394,-7374150824367578503>(),
               (String)com.yiyiaddon.m.b.a<"s2mhk9nddh99g9","HQpBuAPX1cHaT8e0HHlqRacPPgkwMANkOGkEMPQR35RF+2jRUn7Umw==",-6086247908215516718,613295584192682436,-8083846889172928587,1057077181566096188>(),
               List.of(
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"s35ejbz64gq5t","c91lsyE9Jidq2F0n3ok0CnnOAoVKjD+DyvSRxvpE6I+2Pj6d",6060879598337900464,2561130337557583673,4584857270209507134,7943612276144796407>(),
                        () -> {
                           if (e.this.n.cL()) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxvmm28k6o3kp","JS3n6JOLo/qOovjdGzavRKw2c1a2o7wXKwP/SynUqu8=",-6844709385438429997,6275501979000432241,-5264700921642646871,-2213935608152194900>()) {
                                 case 1937453103:
                                    e.this.g.ah();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1iq8cn7yl2kyh","XFWEqTD4nq0FG3KJAKeldYuLuE2Fyd0uV4s93aj8Nug=",8049695268502634752,6705447274541836584,-8787417687639874447,-9049389951900236596>()) {
                                       case -530126250:
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
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"s1ntw5oy8jqqno","in1qtjwXvvyKnLxhwGEB3gtl+vVGkmqK03yABxYx0rpxUIBA",7965562711489126022,8720964305617667485,-4926955341934897147,-7675580135559571111>(),
                        () -> {
                           if (e.this.g.a() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1jbmulykx5vnv","YXaF/9WSbcDKoD6D3r2gwNdOPfRor7u+jPZry0J4UeA=",8924819291135019110,-1933779010740764344,-18698502577486175,-6181939307077364557>()) {
                                 case 2081664439:
                                    e.this.g.a().setScreen(new com.yiyiaddon.e.n.s.g(e.this.g.a().screen, e.this.n));
                                    switch ((int)com.yiyiaddon.m.b.a<"s1j7lak5pudwif","N9bHDgW7MzcyRJ555ZxNQOWsje9038PSXNRMqP2YZYw=",5778930551247285004,5337941911351431394,8189542953495254390,-7695495979685394757>()) {
                                       case 1832622053:
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
               )
            )
            .a(() -> this.a(com.yiyiaddon.e.n.h.d.SPRINKLER));
      }

      private com.yiyiaddon.l.c.g.b b() {
         return new com.yiyiaddon.l.c.g.b(
               (String)com.yiyiaddon.m.b.a<"s25uqcknxjbp71","Qe5VZfYk0CuYNkHtvuZc2mqzG/IlOE/1ePzWfDUpnXvVPdD+",-6723054940862685645,6199022758593541749,5737763777439618292,-1150393550570311426>(),
               () -> {
                  int var1 = e.this.n.aC().size();
                  String var2 = com.yiyiaddon.i.g.b.gm();
                  if (var1 > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"ssgl7kb0tzp6j","qsJDWSjaWqS7+CDbHbLNqL8dCcGLsoJdsiwbw/+AUkw=",8737978202495940542,-1133415878264686460,-6178469401072058022,-27914088609568229>()) {
                        case 67316260:
                           String var10000 = var2 + var1;
                           switch ((int)com.yiyiaddon.m.b.a<"s3e8gbr9ttzfaj","79tW1kEZUI50uza+GWVY24SzHtXhsruN9/XmEKzdelc=",-6991808173885588553,8842871524849421328,7730808734425606440,-2884348999717849264>()) {
                              case 1474043318:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var3 = var2 + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s2vsp8e1rlbwp5","WMAutnXhf0IVWtaVHdSexvFnBSArwfgQjEQ4peuBQaE=",3124200020723563149,-3373345074784898202,-8344004421328497207,-7143279009913579180>()) {
                        case -1454084757:
                           return var3;
                        default:
                           throw null;
                     }
                  }
               },
               () -> {
                  if (e.this.n.cR()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s21su113pkhwsu","57iSbQvWYPrs+INjNeBvQ7oMNcEzB4A01sZNX/LB5zk=",620116954794197355,1908150581134924701,3225918041136968519,7244863869243046731>()) {
                        case 2014663036:
                           String var10000 = (String)com.yiyiaddon.m.b.a<"s1q2jmaqvhndzn","7lxnEjZYwkC87tiCfWFyrURX2hO5hv1KY4tvRM17LvI6y8+Q2kKq9pukq2YJljENcdWe3V2L/EFBqwg3",-41330633474139086,-4341076974651708984,-4041377724569578974,-6336094446434102888>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1zayjus67dih3","NmrVsQJBIutV8Q3I2LkoepUlMhh/t2YUqrlEhRvxwWw=",-6873069239612081028,-3033573265701582897,-9109550366682570348,-3425734131551318837>()) {
                              case -1142772760:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var1 = (String)com.yiyiaddon.m.b.a<"s9bx030hxlk4x","kF55SQEinOX84kz6boia/7NLHxTeq0Nb3r3vmsrZHrM3za9iYRZnPnsLMP3fNkR8V4i2FvcpNcdrCKMBHsJiYw==",-3477559726563993686,1626157497240246466,8997910359166757408,6765339720616313429>();
                     switch ((int)com.yiyiaddon.m.b.a<"sj0106ma7bryo","cniqljtVfmFu5FvMJHH+ibRH2GtAFrhTo85A4giphZo=",190055944946681833,5331288319423365066,275219205016937211,2759308894358453037>()) {
                        case 794044650:
                           return var1;
                        default:
                           throw null;
                     }
                  }
               },
               List.of(
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        () -> {
                           if (e.this.n.cR()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1ksnqxkfdosg5","OagdK5B89qNi8rId3BV9VBqQgNRfiDIOKsmAp/QxjlE=",5456311992949214094,7732945590789029181,57181369417458073,6558887885158495888>()) {
                                 case -2101223618:
                                    String var10000 = (String)com.yiyiaddon.m.b.a<"s1ufh8a01pd5xh","Qj8q5xdEWvSanmKBijnYkzZL74LkhinE77VPwWPQWU5jvEUjKGvxxXXN",8580744651691037478,4861854370626153413,-3778765776307363627,-4267768148574174154>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s139pt1fnhxksv","vdt3mCWx7+TvxdVHGxcqTjHjQ2RY8bwHJhejfh+Q1lE=",-8545412134260042710,-1796364232345551164,-1292647524584068920,-2729552422935488298>()) {
                                       case 2109411806:
                                          return var10000;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              String var1 = (String)com.yiyiaddon.m.b.a<"s26as3pie312le","WBVWQm5G7f7nWIi/Pc/JCMnSM8yHBbpvzo1iBHggl1z//7fr9hf8L+Wb",-3690154415228156311,-1671113709323300740,-7160709687997238918,-7813551629835604315>();
                              switch ((int)com.yiyiaddon.m.b.a<"s3cf9nchj950z","JVJVXTzvDVEPAfr4o0CJq3Cw8pwYM5+AHOMuyHGtb4s=",-8278700413949320585,4505318937699667822,-6413446356763916301,-661164681454209935>()) {
                                 case 1450365805:
                                    return var1;
                                 default:
                                    throw null;
                              }
                           }
                        },
                        e.this.n::gn
                     )
                  ),
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"slgpd7pqop0mb","4wVRA4+YPYO4YgGPHCQxWalWT1swyAB3VKN5wTndUKuN4sTM",-4629123061023579342,3790317318158996379,-611910816803049906,6593936660543004717>(),
                        () -> {
                           boolean var10000;
                           if (e.this.n.cR()) {
                              label25:
                              switch ((int)com.yiyiaddon.m.b.a<"s2u2pcf4bc2usk","aCyEyu0nEHrN6k7JLJcF42+yxqdRkQUhsGq8r8rix0Q=",-3540545838875675994,-1473804436858689431,-2869890770131569364,-2074478947806281754>()) {
                                 case -1032777458:
                                    var10000 = e.this.n.cQ();
                                    switch ((int)com.yiyiaddon.m.b.a<"skpmtue7vnv01","P2hupfU/KWKiQu42VV7yMd7VrOw0T2CvIQNK/zzjgao=",-2205149713716658296,-5204900306323168761,-7688102557549055187,2073240035171926743>()) {
                                       case 1272629689:
                                          break label25;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = e.this.n.cS();
                              switch ((int)com.yiyiaddon.m.b.a<"s3joaisk7b9z48","evKTezyIBB52Gm7MlgniYEKXwFD39p34TD2RQB7dErc=",5973457200253834143,-7229989467695064258,-8779340106360254666,7950213956084404129>()) {
                                 case 1215431122:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var1 = var10000;
                           if (var1) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2l7rmn9fx2faq","i/obH2LBEcp/hS2nFK1BlIO2Je0FtldKaRM6ZNla+88=",7030489269937770081,-5480675489551214653,9218305694361555598,5906990303139344492>()) {
                                 case 1831692125:
                                    e.this.g.ah();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2i3nulk2k9pnl","hpO2XsvAmQjfZoB+RZvx6m0I4m6kao7OEJ+AAK/qRGw=",-6925154006537060785,-7855786853523375495,-6056352233382152718,-8296195985316954325>()) {
                                       case -2129359364:
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
                  List.of(
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"s1ntw5oy8jqqno","in1qtjwXvvyKnLxhwGEB3gtl+vVGkmqK03yABxYx0rpxUIBA",7965562711489126022,8720964305617667485,-4926955341934897147,-7675580135559571111>(),
                        () -> {
                           if (e.this.g.a() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s21z7622rifj93","vTzB6OirhSZFSGdHZ4CPedv5ZRnTdQV0SARpAMM3a9k=",-5856843741893456437,-7780375209595186254,-6313172076098498107,1484211762148809114>()) {
                                 case 62585027:
                                    e.this.g.a().setScreen(new com.yiyiaddon.e.n.s.d(e.this.g.a().screen, e.this.n));
                                    switch ((int)com.yiyiaddon.m.b.a<"s1jc6hqjhh2cog","cdUGFCStWl/z7IsY+Xw0FPbOAXMUNFNxVAlAfJ3j7Lo=",-1359502628739747084,-5420335354845728985,-4347911635112094762,5210736454691172291>()) {
                                       case 338259869:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        }
                     ),
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"s10l0njjiegm2n","6mzdY+OtujnTci5iH3h1QHj9oYHiFJu3ZjgBHmRIc+j8JmIF",1589220706951516504,4979690106187861519,4750698127286358244,1144157846765084028>(),
                        this::hG
                     )
                  )
               )
            )
            .a(() -> new ItemStack(Items.FARMLAND));
      }

      private void hG() {
         if (e.this.g.a() == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s29rul2oz4lfl9","A7B0fjxsx5GmCXqz6K0AqLbvlYt85P8c/O0/wmwzdFA=",-3672560993986501359,2917423864674941103,5527157805912224724,-4025692607427394980>()) {
               case 2109810391:
                  return;
               default:
                  throw null;
            }
         } else {
            e.this.g
               .a()
               .setScreen(
                  new com.yiyiaddon.l.h.c(
                     (String)com.yiyiaddon.m.b.a<"susnhbarr79g9","2lrUPS8AibS7lnpC4BiNeah1bVZB6ipoeBu4J+b3nOgSIvNVEjfpf7PWQ0s=",-8407194880357359626,-8536290349513953726,6181098964834890876,-3396941028575213817>(),
                     List.of(
                        (String)com.yiyiaddon.m.b.a<"s2v2wi8kgnchie","PcywSd0wlCBQ0oxDlCy/+uevwPmBVfYzY4D0XrRQRBgG69L+6xDirjObV6EDhngItoRamYHRFun3vubnOTc/veuDLWM=",-3154559188489200324,-4755481563469715501,-524885891843784378,-2001046345391090102>(),
                        (String)com.yiyiaddon.m.b.a<"s2leq3fzajbsbz","u8AKBrbNfksGzBOh/YKzBwVYwi5+qe/ZPLv/ECn0sMPLhCoYVjQAEQhorjBT7C/VZLIDv33bdmMR92ZMwEBghHKePgWY6TPs+f4=",2876274622121256323,2523218906794261335,6693608219115645471,-4523360695920557363>(),
                        (String)com.yiyiaddon.m.b.a<"s12j6kk4otlywa","dxocxiIoOzZzZlyviCHfBBghsLApXOzYiQ3Oiw==",-5797915379143627237,4138996454183182320,2354639138369653779,-3420140701589066834>(),
                        (String)com.yiyiaddon.m.b.a<"sv5xzcwunrr26","ptc8SNQWWj9a1T3dlXeFrqUfhtGvg7/7Yn1CP0QQ+rl8iTB0JVm8+hyTLUHRyfB1",6319461390641283884,-4885338909327822213,4873161523498024851,-5857004216204552230>()
                     ),
                     (String)com.yiyiaddon.m.b.a<"s11f5ksbz2czd","s02KSju7NPE44LLv8pTm6u6y7YP9Npdf/iufcrzHHehRHyKUPrXNzQ==",1525155272615616202,-4806003960748042935,-8034613413200045007,-488300108298861270>(),
                     e.this.n::bM,
                     e.this.g.a().screen
                  )
               );
         }
      }

      @Override
      public float b() {
         return this.a.b() + 6.0F + this.m() + 10.0F + com.yiyiaddon.l.b.d.a(this.cb.size(), this.cb.get(0).b(), 2);
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
         Iterator var2 = this.cb.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1w6pbk1lvwtcm","71G8Do3ANt91TXFQd117AW5OxOLK1xFbp7vcKldfX0c=",7910095167289294473,3454648427727361424,-1164751605211685880,4295478158450965441>()) {
            case 830478255:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s21sqstmsmg8wm","C+FDQ6DxNM2Zqd/0g433Fgs84XkC8NdgJtLbevIHqTA=",-6622031978682088525,-7098492664384261360,1337359119886203551,-6415351258818392839>()) {
                     case -2126214836:
                        com.yiyiaddon.l.c.g.b var3 = (com.yiyiaddon.l.c.g.b)var2.next();
                        var3.a(var1);
                        switch ((int)com.yiyiaddon.m.b.a<"s34e854atzgi2w","2p4sW2BpANTBC9sFEb207izBH/4zfG1nVD3LN6q9rUw=",-9095420277969413630,2568169134569788270,6843718060874942089,-7253801774103701189>()) {
                           case 1239282360:
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
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.a.a(var1, var2, var3, var4, var5, var6, var7);
         float var8 = var3 + this.a.b() + 6.0F;
         Iterator var9 = this.cc.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"scvn0dbpnd76u","zAXfIbOHJWfC186WwG+9JeF8v+WRXimbeuV/ioN2LNM=",-6010140726374100480,-576344436987746984,2852189421835529582,-5319257436214566199>()) {
            case 1317692234:
               while (var9.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1uq3e33cdwcxz","ZJbiyunz/sBRIBMFpxyo8dSijpAJXIMWuVxXuVuQGeg=",-5927098838688049271,-5616017239712181462,-695145360316229622,-6747047156622341461>()) {
                     case -1674458878:
                        com.yiyiaddon.l.c.f.e var10 = (com.yiyiaddon.l.c.f.e)var9.next();
                        var10.a(var1, var2, var8, var4, var5, var6, var7);
                        var8 += var10.b();
                        switch ((int)com.yiyiaddon.m.b.a<"s1tdiwugi2ih53","JjupgTdtKGDfkPdU8Z/F2V/oBpQ4TeNsRi0ONvKGz/o=",2461766352909035908,-2795790177913100154,-6887065342883091251,1217162102588143476>()) {
                           case 1567681589:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               float var13 = var8 + 10.0F;
               float var14 = com.yiyiaddon.l.b.d.a(var4, 2);
               float var11 = this.cb.get(0).b();
               int var12 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"sqei8aj9b3rk8","gNdL581SdbZvZtMsRvg9FUwxWuOI1OKN66g3Mh+hLqE=",-7185160509766780468,-7477928074880450657,-544708095267234610,-6527572102690269394>()) {
                  case 1989022934:
                     while (var12 < this.cb.size()) {
                        switch ((int)com.yiyiaddon.m.b.a<"slwqzqmsw034q","ibpRBHXT4ewDfCzdsI5apN+iNC84tXJmVfsYEB+jmzA=",7934805217758896615,-6517210780531017714,5542568049981025471,2987269065801015627>()) {
                           case -149752692:
                              this.cb
                                 .get(var12)
                                 .a(var1, com.yiyiaddon.l.b.d.a(var2, var4, 2, var12), com.yiyiaddon.l.b.d.b(var13, var11, 2, var12), var14, var5, var6, var7);
                              var12++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2zpc0gdipebxn","Tb2iuhfY1EY195U6NPIXcdsiKts736ggS18nZhyyTWE=",7308569569895511525,-324792113817251165,313075985506921599,329834985088908029>()) {
                                 case -1271540822:
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
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (this.a.a(var1, var2, var3, var4, var5, var6)) {
            switch ((int)com.yiyiaddon.m.b.a<"s1xzrhils9z7ly","aXCyh0vxdsakz12yBExndaIg26LPTFvDYCTzV5eE2Es=",-5098420011065050675,1547167633006626284,4791602619378307258,1848845384880999008>()) {
               case 1328540596:
                  return true;
               default:
                  throw null;
            }
         } else {
            float var7 = var4 + this.a.b() + 6.0F + this.m() + 10.0F;
            float var8 = com.yiyiaddon.l.b.d.a(var5, 2);
            float var9 = this.cb.get(0).b();
            int var10 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s1at0rrm558ewl","tlHQ2wY1I2AX+FmCCAAJKImx4yCvFA6MCN9Ylz7jHf4=",2655348759524835972,-4402966074574527575,-8355187506345392970,-649370211894391908>()) {
               case -795929440:
                  while (var10 < this.cb.size()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s287vefcq2dazg","kFiZsS/HxcUynn+oTwDtYsZEQ0yHyVNQSOj8gAmD++U=",-5595230498127133658,-4553619360799761688,379935110462414109,-1829163929966234700>()) {
                        case -1125796191:
                           if (this.cb
                              .get(var10)
                              .a(var1, var2, com.yiyiaddon.l.b.d.a(var3, var5, 2, var10), com.yiyiaddon.l.b.d.b(var7, var9, 2, var10), var8, var6)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1lm7wwsl8w6r9","7K7Ni7tXnjhWPrbGUmZ96f3hxElcqzPKrr5XlokyhSY=",-4985282868504059088,-5371539219594884271,-1088120446106005162,3908988315797670795>()) {
                                 case -379312723:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           var10++;
                           switch ((int)com.yiyiaddon.m.b.a<"s3lmkkzacslqx7","+sOngQcPrPfoCI5QnjlvCyrM7FZbWpDvIwWgxzqJR1M=",-4894018737841954886,-2286138950718954157,3823314485320272136,-5083677173026787441>()) {
                              case -1830429643:
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

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }
}
