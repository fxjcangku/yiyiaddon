package com.yiyiaddon.e.f.c.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.c.e;
import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.h.g;
import com.yiyiaddon.l.j.k;
import com.yiyiaddon.l.j.l;
import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class a {
   private static final String iT = (String)com.yiyiaddon.m.b.a<"s2f3icevwqywkd","kC2BaqQuQdWwSdAUSDfjeraOT9NOPi5cCH6c0S3YUFtTQstm",5753860889663799784,7027361434263876544,2951590648922353417,-4364321518064922416>();
   private static final String iU = (String)com.yiyiaddon.m.b.a<"s1pbpqhaz4whcb","vwe4eXV5iOQBw3qmUb+OqKYWSNtjkZrYr+FsbTy/3J7dXTkI2AMaXebSsYWmJ8Z3tTy2Cg==",-227248880047442263,8584775908745377996,-904891516580956189,-4854696635404147615>();
   private static final List<String> X = List.of(com.yiyiaddon.e.f.a.a.a.WEAPONS.m(), com.yiyiaddon.e.f.a.a.a.ALL.m());
   private static final List<String> Y = List.of(com.yiyiaddon.e.f.a.a.c.ALWAYS.m(), com.yiyiaddon.e.f.a.a.c.ON_HIT.m(), com.yiyiaddon.e.f.a.a.c.NONE.m());
   private static final List<String> Z = List.of(com.yiyiaddon.e.f.a.a.d.IGNORE.m(), com.yiyiaddon.e.f.a.a.d.BREAK.m(), com.yiyiaddon.e.f.a.a.d.NONE.m());
   private static List<g.b> aa;
   private static final com.yiyiaddon.e.f.a.a c = new com.yiyiaddon.e.f.a.a();
   private final com.yiyiaddon.e.f.c.a c;
   private final com.yiyiaddon.e.f.a d;
   private final e b = new e(
      (String)com.yiyiaddon.m.b.a<"s1pbpqhaz4whcb","vwe4eXV5iOQBw3qmUb+OqKYWSNtjkZrYr+FsbTy/3J7dXTkI2AMaXebSsYWmJ8Z3tTy2Cg==",-227248880047442263,8584775908745377996,-904891516580956189,-4854696635404147615>()
   );

   public a(com.yiyiaddon.e.f.c.a var1, com.yiyiaddon.e.f.a var2) {
      this.c = var1;
      this.d = var2;
   }

   public void d(i var1) {
      com.yiyiaddon.e.f.a.a var2 = this.d.a();
      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s2ras6cw8h194e","qOVDzwcYuIGDrKyw6yYGXFzVgzM/nmRzcppfSsyzIm7iAQgi",4264113153208504608,1988189264132601255,-3338302426476377049,-6693528507673733898>(),
            (String)com.yiyiaddon.m.b.a<"sz1lzveifwp57","4DV9dMTFQLaVxL5dQn0Oxv6ZHf7+eymrFHmbP+Z8IGSzjQ/9ny2cUEjokgy0ZrcX7bVTxW62Zb0atZo40vU=",3878588681382814933,5544036779630635867,617114961065625504,5137369991650124669>(),
            null,
            List.of(
               new f.c(new k(X, () -> var2.a.ordinal(), this::g)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2ras6cw8h194e","qOVDzwcYuIGDrKyw6yYGXFzVgzM/nmRzcppfSsyzIm7iAQgi",4264113153208504608,1988189264132601255,-3338302426476377049,-6693528507673733898>(),
                  () -> var2.a = c.a
               )
            )
         )
      );
      if (var2.a == com.yiyiaddon.e.f.a.a.a.WEAPONS) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s2011h9rwvb8jf","L0B/awLIwV9KYilGndJ4X1AutqaZ3M2XZfo6el8XcoE=",7635271354178896034,-2957842050645336811,1128851170332186152,-2068362896261835716>()) {
            case 1868618683:
               var1.a(
                  this.a(
                     (String)com.yiyiaddon.m.b.a<"s2s3j5fgga3y58","KGjc1FZTuJ81JhJOjBbvjkWvenhiKGjQOSAoCHgBR12l8gv3M5E=",-2261254751303871741,-7641290442770652688,5501695563158943274,8321103800879313364>(),
                     (String)com.yiyiaddon.m.b.a<"s2dmnux04kf6w1","C6WqDnTaoWGDajQBsJ9qy4ASOM3uFpvf3z2TxzIwvIhvRvr0aSLFlvY6twm50JrXNc+37xgjq8zNAPof0DEEmme1tH8UgI/qaSa21CQsXwN9TQ==",1157902329969945258,9022197828281667007,-100641759063768116,-4572335117805971681>(),
                     this::au,
                     this::bw,
                     this::bx,
                     () -> this.d.a().T.isEmpty()
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s37kqa9hdmbbe7","UlMafrbcFseK4W6Mef6B3jL1zf+HQtgACnOycgQrW/Y=",-6679854008778176332,1109381817580217566,1752797596322377118,2588123254603759001>()) {
                  case 1261620079:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s2q9wswi5brtxd","tPaKk3Z7kYeuJeavvcwiWqrJVJlo58ubOiJp+Y0eZz/e2aQI",-8320421981390641800,-5183886219313736551,4514637533291666645,-6709335028161888343>(),
            (String)com.yiyiaddon.m.b.a<"stbh02fqcj9qw","cefD0JNx0VF3rL3/wII1oShr9BtcKVhf/v3iszmO2QkR53TaJ/5jO27cpG++LA==",-7900781271258741717,1667854200993105324,982222217495690623,4905332900815549935>(),
            null,
            List.of(
               new f.c(new k(Y, () -> var2.c.ordinal(), this::h)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2q9wswi5brtxd","tPaKk3Z7kYeuJeavvcwiWqrJVJlo58ubOiJp+Y0eZz/e2aQI",-8320421981390641800,-5183886219313736551,4514637533291666645,-6709335028161888343>(),
                  () -> var2.c = c.c
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s3n0ut2imm9mym","f5QdPnfPfwY7CEd5L7DBLLclwNoiT4rfpVn+yBTVMJWJBAji+BbhFw==",-3735413069551369534,-8760305028516841435,3614582959593565557,2195369388662915919>(),
            (String)com.yiyiaddon.m.b.a<"s1ki1za93mo9rw","i4iDuCCWyK1NoYpvIOzEWqgFBLT8wPAxfhOHKHS7PzqAgFI4O0LSy75gKgaVbZHQ4esh1V/pqU6Y0LQc",515294133359943854,-4503843268981571222,4194647707998257241,2026197500978592371>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aS, var1x -> var2.aS = var1x, true)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s3n0ut2imm9mym","f5QdPnfPfwY7CEd5L7DBLLclwNoiT4rfpVn+yBTVMJWJBAji+BbhFw==",-3735413069551369534,-8760305028516841435,3614582959593565557,2195369388662915919>(),
                  () -> var2.aS = c.aS
               )
            )
         )
      );
      if (var2.aS) {
         label18:
         switch ((int)com.yiyiaddon.m.b.a<"s27vluw1c345ik","52KuWf7RAfcHa1Zq9AMia6c50cxtvyzrsbJhKkz1kTY=",6006596822879534472,-8057228260431346661,8086909500503765500,-3260951478862902045>()) {
            case -1128128521:
               var1.a(
                  new f.b(
                     this.c,
                     () -> (String)com.yiyiaddon.m.b.a<"s2hqvgb7gzhp89","lvg8EJbvHej9IB+dBc7MKGb4otSk+iKZSUNPlrpCSJ8Trp17wug=",4057036969138169896,-2786816212178187707,-4168135233307602561,7151691341716209727>(),
                     (String)com.yiyiaddon.m.b.a<"s147g82ubvyiey","mQQZSMVoIkoQWLrBqY2w+vlriGoXyYQqBgj35aZkzLEOlfl0JvKvFB/xDoowIN2ZSaGZ5bET6gmKC/jC",3351486034014287663,-7054827078310375368,-1535193135555558449,3170534600777790548>(),
                     null,
                     List.of(
                        new f.c(this.a(() -> var2.aT, var1x -> var2.aT = var1x, false)),
                        this.a(
                           (String)com.yiyiaddon.m.b.a<"s2hqvgb7gzhp89","lvg8EJbvHej9IB+dBc7MKGb4otSk+iKZSUNPlrpCSJ8Trp17wug=",4057036969138169896,-2786816212178187707,-4168135233307602561,7151691341716209727>(),
                           () -> var2.aT = c.aT
                        )
                     )
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2yujqtd9sbrxy","32KFGU5xw51Y+hLhap8qG7J5T2f2fbzbZWnibfLRZ+I=",5812320764831880818,8249300259179899300,1061927687861524906,-7428619134472532910>()) {
                  case -2031847484:
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s2h55e2tqfiqjo","pkEZV86VbPJY+6+dfAUhX9NUs2IR5CZV8SpHyfNxUahWnhz4/6w=",7690323971417940874,4622001206472407953,1895318607470574184,7493716395935681913>(),
            (String)com.yiyiaddon.m.b.a<"svufzmfnfgm97","kV5Koxp/o3LZa572/Fm7mpgtHe+IT6SDFTCXnaZemZ+lLC4rYPL6JJn/NoJCT/0uhL2UU15Ws0IVJZ+w2fGM8RPFYJJtr4Ih93+5I85ZFm1vCAwEwP2IfgfAjafSAbPhIH5m+JfofbQD0wMrcOmfXJHnSa9RELGAZtlV4z63N5/95NuF/reWFhE3",-3259926708214447550,5494636378443317570,8293149996979847817,3233596562603583371>(),
            null,
            List.of(
               new f.c(new k(Z, () -> var2.a.ordinal(), this::i)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2h55e2tqfiqjo","pkEZV86VbPJY+6+dfAUhX9NUs2IR5CZV8SpHyfNxUahWnhz4/6w=",7690323971417940874,4622001206472407953,1895318607470574184,7493716395935681913>(),
                  () -> var2.a = c.a
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s2r0zadjqohkj4","eRl/1ZJz9OCgjeDeEpX/pwrEqpJL6sN0EmzRLJimNsvgDKE5SRCKfTbedWI=",1552853106977500414,6773391914464907764,2527166902038566196,-4391740794769893094>(),
            (String)com.yiyiaddon.m.b.a<"s36osnte0xnr25","LY3Eon/GVxNi9mm6W1is/0L11u6CGjZpgRl5sAffw7Ui1S5g7VQFGl5CQaAZeAl15ZQ=",7712830101587437010,-2518696204195398789,-7706677229974689074,-5707132198961511369>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aU, var1x -> var2.aU = var1x, false)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2r0zadjqohkj4","eRl/1ZJz9OCgjeDeEpX/pwrEqpJL6sN0EmzRLJimNsvgDKE5SRCKfTbedWI=",1552853106977500414,6773391914464907764,2527166902038566196,-4391740794769893094>(),
                  () -> var2.aU = c.aU
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s2msl987wsugxj","Gl5AEVMh1x9bIiFTGm/T5qsmFp0D/FaMO8h4I5BMrsNt1iHF25rCXA==",-2807494388623999764,-2111316951966228314,8710978312081615898,6896140809907697864>(),
            (String)com.yiyiaddon.m.b.a<"s3lkcfbdswr9yx","GQWV9IF6MqQ1+0FppobTK/WNcmHXAWIAQp0g2XXatx2KJD8Zxsu+A0tn2M7KyvkRBBeKY+pm",-3855965690865679837,-6996797551693744180,-24197313298823274,4417578775458114176>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aV, var1x -> var2.aV = var1x, true)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2msl987wsugxj","Gl5AEVMh1x9bIiFTGm/T5qsmFp0D/FaMO8h4I5BMrsNt1iHF25rCXA==",-2807494388623999764,-2111316951966228314,8710978312081615898,6896140809907697864>(),
                  () -> var2.aV = c.aV
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.c,
            () -> (String)com.yiyiaddon.m.b.a<"s1fsmb0918vk7r","kM8ZBQiuK+LBH6mnUlrjKpVs7qTJO+5xRS61b4odYOJdpvH8Y4C/2dbS0sSG/g7c0v3QFI+f",-8356647173995970612,4474690786329723072,1139254338282838979,-2665061359388639004>(),
            (String)com.yiyiaddon.m.b.a<"smc0zqeccoqb5","PaFX/bfu6IpuY6JLFS22qf2HzXLM0agpKloBrbZB6Op03ZzNhZcLN8/fm4OuhTOvdkT5XQbUZnOJPfuDzsrnj6ygbRqnukLnQDQ=",4510332137008632401,1269581329052957041,5101205237326138748,-3506752036425403064>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aW, var1x -> var2.aW = var1x, false)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s1fsmb0918vk7r","kM8ZBQiuK+LBH6mnUlrjKpVs7qTJO+5xRS61b4odYOJdpvH8Y4C/2dbS0sSG/g7c0v3QFI+f",-8356647173995970612,4474690786329723072,1139254338282838979,-2665061359388639004>(),
                  () -> var2.aW = c.aW
               )
            )
         )
      );
   }

   private f.c a(String var1, Runnable var2) {
      return f.b(() -> {
         var2.run();
         this.I();
         this.c.C();
      }, var1);
   }

   private com.yiyiaddon.l.b.g a(String var1, String var2, Supplier<String> var3, Runnable var4, Runnable var5, Supplier<Boolean> var6) {
      return new f.b(
         this.c,
         () -> var1,
         var2,
         null,
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s2f3icevwqywkd","kC2BaqQuQdWwSdAUSDfjeraOT9NOPi5cCH6c0S3YUFtTQstm",5753860889663799784,7027361434263876544,2951590648922353417,-4364321518064922416>(),
                  var4
               )
            ),
            new f.c(new l(var3, () -> this.b.a(var3)).a()),
            new f.c(
               new com.yiyiaddon.l.j.b(
                     (String)com.yiyiaddon.m.b.a<"s23f5oc1xjzs6p","k9TAQp6THWCcpmhpIpiwUMlR5FCpclehcirRK2NI",-5847231815352958781,-6445498723691427480,9018223621413217449,-8281419200266695807>(),
                     var5
                  )
                  .a(var6),
               var1 + ""
            )
         )
      );
   }

   private n a(Supplier<Boolean> var1, Consumer<Boolean> var2, boolean var3) {
      return new n(
         var1,
         var3x -> {
            var2.accept(var3x);
            this.I();
            if (var3) {
               switch ((int)com.yiyiaddon.m.b.a<"sdm4zwpfefc5x","sSNrE2SOI25lUl7+QdYGfUDIHASDzBPqDvBtsnEQWQw=",1873431456943180743,-8311661142806260300,7857849133262942101,5920021138958927873>()) {
                  case -1918240905:
                     this.c.C();
                     switch ((int)com.yiyiaddon.m.b.a<"s3mmpp646i0db6","M54D48605f88cY4IE8f+sXPcuIe4kBrhfvVU1MCtT+I=",-3816831837759557693,3282087573633330718,-8894205553158272170,5366131702368536303>()) {
                        case 1509990149:
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
   }

   private void g(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s33ecrpvqx8648","hU4QwRg4AOsZ/mBv/IEr1fpaDc07/HlK8GqaiJ4PMXQ=",5984305578139215130,-687656938881495093,3683339232656572238,-1425813693932515438>()) {
            case -2089589805:
               if (var1 < com.yiyiaddon.e.f.a.a.a.values().length) {
                  this.d.a().a = com.yiyiaddon.e.f.a.a.a.values()[var1];
                  this.I();
                  this.c.C();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s39vh4m5h4fayj","bSj0ugUrxS5RRJclK0Nl5gs0VWBnHdUYXo3XTLRqe9c=",-2416383228408257740,-1947360109646797211,6388824445359042878,3300833573379502893>()) {
                     case -1007811891:
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

   private void h(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kqbf2pfuzyjf","RGg57j6IieeUbc2R9xMQ49nvmvoRKA+bnqetAt78zH8=",8801055949613157156,6089876427142761255,6717690809315607109,4644651115397528097>()) {
            case -1172944747:
               if (var1 < com.yiyiaddon.e.f.a.a.c.values().length) {
                  this.d.a().c = com.yiyiaddon.e.f.a.a.c.values()[var1];
                  this.I();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2jq8t7gp4ieyf","rVqF98v61E085aOwJ9LLtuU6t51P7wvBHb2VXSxrGwA=",1846068043309190440,9175964299418585947,4201228046204812483,-4248796195858402861>()) {
                     case 1856394475:
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

   private void i(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cvk6gdm45jv9","7k/yteSPwgDcJa21jb64IhVHp93al+4uaeayfqYqt3E=",2968837463465785864,7988318238657171114,-4714465808462268919,44530357104883199>()) {
            case 1148742094:
               if (var1 < com.yiyiaddon.e.f.a.a.d.values().length) {
                  this.d.a().a = com.yiyiaddon.e.f.a.a.d.values()[var1];
                  this.I();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s23e0lv48hb2jh","5UTNo8jSF9NNZYU1pqSkI9QTEdKXpRkGOKLN8xE9Wmc=",1915821298308029397,286843969004044864,4260978396607494449,-8002861410525539418>()) {
                     case 2136536134:
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

   private void I() {
      com.yiyiaddon.d.b.e.d(this.d);
   }

   public static List<g.b> A() {
      if (aa == null) {
         ArrayList var0 = new ArrayList();

         for (String var2 : com.yiyiaddon.e.f.a.a.Q) {
            Item var3 = b(var2);
            if (var3 != null) {
               com.yiyiaddon.e.f.a.b.a var4 = com.yiyiaddon.e.f.a.b.a(var2);
               String var5 = var4 == null ? var3.getDefaultInstance().getHoverName().getString() : var4.h();
               Item var6 = var4 == null ? null : b(var4.at());
               var0.add(new com.yiyiaddon.e.f.c.a.a(var2, var5, var6 == null ? var3 : var6));
            }
         }

         aa = List.copyOf(var0);
      }

      return aa;
   }

   private static Item b(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s8gqrzi9ml3i8","ycqQQ6+tKNYB8K2ewde+n9DGl0Pzq8M6PQyREErBrgM=",4675101311650147879,-1807374050870673186,-267729590454371889,4770528551708146199>()) {
            case -1542754905:
               switch ((int)com.yiyiaddon.m.b.a<"s36axgwb68azd2","vfoMa5kgpaFnuGBrSyRW0WVjpHzW4lX85Lax1rBhNCI=",-3444320376796234385,3185087055476569792,-3943998047090055550,-945049908073073866>()) {
                  case 1853605813:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Item var10000 = BuiltInRegistries.ITEM.getValue(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s11ytimd31q9st","fvDAzng8F2a9oyy+DDIu6hRwcG8myMcx6vSapyFyd3w=",5802073989313263915,-8412146148652418711,7589724378579427603,3674719691180574323>()) {
            case -820117284:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static int J() {
      return com.yiyiaddon.e.f.a.a.Q.size();
   }

   public static String P(String var0) {
      com.yiyiaddon.e.f.a.b.a var1 = com.yiyiaddon.e.f.a.b.a(var0);
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ip3ftcbrv65n","fL07C50KyRsSyHL/v6g//heKihoQvmgBDu8EHJALXLM=",-7420002747936574279,56301963013560454,-8600055106188048796,6767192182328890775>()) {
            case -1026103828:
               return var1.h();
            default:
               throw null;
         }
      } else {
         Item var2 = b(var0);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s7kq2851ow6ch","puEDYzY+3h4ms9fGvbi5YHSTi0sNE66NvJbeFjeyu4k=",7962924226960106333,7476563510355777822,-6381318774636441486,9134078082186602938>()) {
               case 1523718779:
                  switch ((int)com.yiyiaddon.m.b.a<"s16uskw1janrvh","tU31QO7/ZelitL5zgXluCI6ebP4AQLYszVA2S5uZQfQ=",2888998031721529299,-4958220431751872389,8051317595784971957,2791453521791044988>()) {
                     case -1588748037:
                        return var0;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var2.getDefaultInstance().getHoverName().getString();
            switch ((int)com.yiyiaddon.m.b.a<"s2gep3fnieci55","cmSBvea0gJ/W+YwDiDCntvoTb7m3UO7KWUlTIb3v7Gw=",-6435100269944677682,8301260194260625309,1792962039523746891,7991344661059924107>()) {
               case 750210142:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   private String au() {
      List var1 = this.d.a().T;
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yzdfniga3447","tbT6/dJaWNkKhL9QY66wC7Cq1YG5CtJnPsW61WXTQZw=",2640661781972442102,-4389449681242332761,-4079067066153213719,-8348604815359371769>()) {
            case -237678405:
               return J() + "";
            default:
               throw null;
         }
      } else {
         return "" + var1.size() + J();
      }
   }

   private void bw() {
      if (this.c.a() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dardp01s7m1u","m8H5KCNrefpiIJJQd3SkytrPCq7HZKg0cTUIS2WWVWY=",2838636255679904939,-4274005007969662100,7167302453079610895,7372508675862874092>()) {
            case -166169888:
               return;
            default:
               throw null;
         }
      } else {
         List var1 = this.d.a().T;
         this.c
            .a()
            .setScreen(
               new g(
                  (String)com.yiyiaddon.m.b.a<"s2s3j5fgga3y58","KGjc1FZTuJ81JhJOjBbvjkWvenhiKGjQOSAoCHgBR12l8gv3M5E=",-2261254751303871741,-7641290442770652688,5501695563158943274,8321103800879313364>(),
                  this.c.a().screen,
                  A(),
                  () -> new ArrayList<>(var1),
                  var1x -> this.d(var1x, true),
                  var1x -> this.d(var1x, false)
               )
            );
      }
   }

   private void bx() {
      if (this.d.a().T.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dor3kyplcaua","WXFu4OOYQoRCuWLErdLWrsrGW76OL6nC9crajoSygBQ=",-2407599172319589307,4533547424086190586,7670919281803845098,8602612675385973492>()) {
            case -803002746:
               return;
            default:
               throw null;
         }
      } else {
         this.d.a().T.clear();
         this.I();
      }
   }

   private void d(String var1, boolean var2) {
      List var3 = this.d.a().T;
      boolean var10000;
      if (var2) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s3p9scmlox6t6x","WeA0Wb/JZgsDJePEpfVp1ZLN4+UePf6/165Z7NT39lU=",-8479081290968493690,-1026568383724367905,-2463776703250642804,-1591728093032197662>()) {
            case 1025316365:
               if (!var3.contains(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1zjmfe845r05s","1ZtpE1GCQZTJ4PwsvZlteiZoUyCxYcokWf2qNrYB7KQ=",5956611961126754708,2149901193163318652,-341732433368634145,-5466445598841949877>()) {
                     case 965826658:
                        if (var3.add(var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1f0uyh8nl0bje","Ala6KBxWXWesMfI4WWj1GQk9/xtOwV06VCkl1qetlac=",6922155804029317190,7199548230513646583,-4230045875919716541,-5686173126771834632>()) {
                              case 586568650:
                                 var10000 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1tmkmao0s5usk","8kl0hUTnT6EtMDm471I2k20uzLuIX1hNPNBpztW7Jv8=",7261785912994850838,536842967863671969,-5791490533667436771,7425739198179593049>()) {
                                    case -2120043656:
                                       break label35;
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

               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"sbfev4axtt4gy","MV7TVN0V0C1o1oqwbhPx1kkBzBgUTGATOuqOGSccSkA=",-5257665084687687537,-7087520707361872232,-4477648990347912169,-8269231543378783842>()) {
                  case 861220468:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var3.remove(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1uoqbejh3tix4","SRyDyaUcNgnEB4nxwpAtsUBls0vtahtwS1Osa5DXAqY=",6003965567142289316,6756646510434170407,335113750560357021,-2372435129450447315>()) {
            case 1559476818:
               break;
            default:
               throw null;
         }
      }

      boolean var4 = var10000;
      if (!var4) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qmrdn7ybe4im","JII29Eb48K8FfIMxSTKV22Gp3qOmFlKxT87X6zY4DFw=",8128301304496939467,2645572836404266152,5020217830327743724,2575298810664168069>()) {
            case 1788512631:
               return;
            default:
               throw null;
         }
      } else {
         this.I();
      }
   }

   private record a(String iV, String iW, Item c) implements g.b {
      @Override
      public String D() {
         return this.iW;
      }

      @Override
      public String M() {
         return null;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         return com.yiyiaddon.l.g.c.a().a(var1, this.c.getDefaultInstance(), var2, var3, var4);
      }

      @Override
      public ItemStack a() {
         return this.c.getDefaultInstance();
      }

      @Override
      public String L() {
         return this.iV;
      }

      public String a() {
         return this.iW;
      }

      public Item f() {
         return this.c;
      }
   }
}
