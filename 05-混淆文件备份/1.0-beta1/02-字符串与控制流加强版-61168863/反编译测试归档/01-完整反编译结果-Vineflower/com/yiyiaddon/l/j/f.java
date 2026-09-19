package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.input.KeyEvent;

public class f extends p {
   private static final String GN = (String)com.yiyiaddon.m.b.a<"s3txf11g8y3i0i","jrIXBpddbIwow4Nb3Xna/grZpyQaenbtPAD5PGBE0FukiQ==",-2704328953775656025,1630367544011866607,2283137014609499378,4247742012468057422>();
   private static f a;
   private final Supplier<com.yiyiaddon.l.d.a> G;
   private final Consumer<com.yiyiaddon.l.d.a> t;
   private final com.yiyiaddon.l.a.b i = new com.yiyiaddon.l.a.b();
   private final Paint I = new Paint().setAntiAlias(true);
   private float gk = 160.0F;
   private com.yiyiaddon.l.d.a f;
   private boolean gE;
   private String GK = (String)com.yiyiaddon.m.b.a<"s381viacnl9xuu","zO389qzfxZzLalDdpeidGA4yr7XNovmmjWKYhA==",7045546023259372626,-4120950663738795545,2502779418463236132,-6290289090064735890>();
   private float nl;

   public f(Supplier<com.yiyiaddon.l.d.a> var1, Consumer<com.yiyiaddon.l.d.a> var2) {
      this.G = var1;
      this.t = var2;
   }

   public f a(float var1) {
      this.gk = Math.max(1.0F, var1);
      return this;
   }

   @Override
   public float c() {
      return this.gk;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      this.i.a(var1);
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      boolean var10000;
      if (a == this) {
         label95:
         switch ((int)com.yiyiaddon.m.b.a<"s193fipq2tmeph","2pPKPNEFAbemN+J73M206PWquUww++6nbREDADkZ5qc=",5983203966529146973,8362213705834993274,2770105655575136858,-8836242225304629325>()) {
            case 1215389802:
               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s101aba7ijzhg1","wFxKVlSsj3m6QQpJZhWtdhOwwqXULPCiSC6i0346nqc=",-8208826187652787216,6672768238094709248,-1587095709533261525,1546127106715748825>()) {
                  case 1284706892:
                     break label95;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s1hb0u8kw5vcho","B4E+RfyeXRQIk2mNYhCj2Exs9Y95W6sWba59LWPodMw=",6896902020575600924,-2216384056901560144,-7728336481760376779,-5840353145095786318>()) {
            case -382957301:
               break;
            default:
               throw null;
         }
      }

      boolean var5 = var10000;
      com.yiyiaddon.l.d.a var6 = this.G.get();
      if (var6 == null) {
         label91:
         switch ((int)com.yiyiaddon.m.b.a<"s2gh0eapt39bgu","qFgEwCHcs8N4/U5Sc3Gr5hcPbMQfRl03szsegUNhcBs=",-8696632191040656947,8312359023142762771,-2884833231599796019,-4100241615267780148>()) {
            case -1069566662:
               var6 = com.yiyiaddon.l.d.a.i();
               switch ((int)com.yiyiaddon.m.b.a<"si7rvm436l3ou","kYwYMceGgI+iP5PoHwzLZnQVdVIKCyjTRvfNlB+RsYc=",-260453081633977392,-7097299266868524738,-195907282619476857,5913006856121467481>()) {
                  case 1770406492:
                     break label91;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      label112: {
         if (var5 == this.gE) {
            label87:
            switch ((int)com.yiyiaddon.m.b.a<"s25tdadxlgbpsa","blzEvr2/gkjx+u/yok7GyTC0k0U4np9rYZHIEZDl1B8=",6966622733205196823,2563089023633276554,6466176208005528896,2308385491408698997>()) {
               case 1268853111:
                  if (var5) {
                     break label112;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1zpntnntrice7","Req/5WSYgUbGz9te7eclAP3IwJYMLU5jNfA76oJnMyM=",-3925296893281740680,-2318336999509113300,3546579959749358222,-3368303914252859145>()) {
                     case 1779191981:
                        if (var6.equals(this.f)) {
                           break label112;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sdxvak26jle9y","Im+J/bps3pzgSmdbsMOkVYdHIvTc2+G2sF5xNA9Xo/o=",3704599233879962844,4130479665979289438,-1435288625150593380,3046446745299291980>()) {
                           case 418071387:
                              break label87;
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

         this.gE = var5;
         com.yiyiaddon.l.d.a var10001;
         if (var5) {
            label78:
            switch ((int)com.yiyiaddon.m.b.a<"s3e22njjgib1pb","orzd0MfRxPjhPQAcvxyTQd2HThyHoauIm85+me1oIIg=",8974357451366857527,1676715057585473857,3080473476055112519,-3927382300728833243>()) {
               case -723054848:
                  var10001 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s2h0xhqr6iq5v","2SgIOHyWxlklfDaUGQzamixVwY8JzwXme+fKhoZt4co=",-321068928563029004,-8827334959236909844,-2913207714764470715,5933883594045925808>()) {
                     case -1434019464:
                        break label78;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = var6;
            switch ((int)com.yiyiaddon.m.b.a<"s1hfav8cvoz6ns","sHLAEX5bMhpxWVCaAm7+yQgkE9FnA44QeqSDE9aIViE=",-1853808187134496692,-2600110388996788149,7630980540698039693,-2666167165852423734>()) {
               case -1619593185:
                  break;
               default:
                  throw null;
            }
         }

         this.f = var10001;
         String var9;
         if (var5) {
            label71:
            switch ((int)com.yiyiaddon.m.b.a<"s3sesfh9lrvfwo","Epqms4kDE2+70Ch5CGWSgfo+Ms8Sho1cRli0U9nHOjU=",7044247998671762254,5772363651178301364,-5713736358901123857,-481919015357967854>()) {
               case 1615502814:
                  var9 = (String)com.yiyiaddon.m.b.a<"s3txf11g8y3i0i","jrIXBpddbIwow4Nb3Xna/grZpyQaenbtPAD5PGBE0FukiQ==",-2704328953775656025,1630367544011866607,2283137014609499378,4247742012468057422>();
                  switch ((int)com.yiyiaddon.m.b.a<"s8mxea3ewti3r","QOcZBZFjZruK0lzYZyneLW4NxeuA3UjaYR6Wg97G3ZE=",-6187239885735498770,-8657502786511133390,-6871557143299443729,4391863616629675071>()) {
                     case 79420458:
                        break label71;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var9 = var6.m();
            switch ((int)com.yiyiaddon.m.b.a<"s1w4wtxlbpvg3x","g2SBoutaIZlJFFg0dkebzuidzGM7zevZo88jGNFwgQ0=",-3497047084411146738,-4196000336133090259,-40820747207478678,2520150723799020212>()) {
               case 1472364606:
                  break;
               default:
                  throw null;
            }
         }

         this.GK = var9;
         this.nl = com.yiyiaddon.l.g.a.b(this.GK, 12.0F);
         switch ((int)com.yiyiaddon.m.b.a<"sa6lc19t2k3ai","b3/ct/B0mrXeVZITWd81Wc6BMZUHzSq1q/9OoUNm4is=",2703633643241716782,-3451650637023884048,-1885480361616474713,-200593195260615386>()) {
            case -183277887:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.i.c var7 = com.yiyiaddon.l.i.c.a();
      this.I.setColor(a(var7.vd, com.yiyiaddon.l.i.c.y(var4)));
      boolean var8 = this.i.a(var1, var2, var3, this.c(), this.d());
      var1.drawRRect(RRect.makeXYWH(var2, var3, this.c(), this.d(), 6.0F), this.I);
      String var10 = this.GK;
      float var10002 = var2 + (this.c() - this.nl) / 2.0F;
      float var10003 = var3 + 16.0F;
      int var10005;
      if (var5) {
         label62:
         switch ((int)com.yiyiaddon.m.b.a<"s3psvi9hzy2ozq","ib+mb45HJp/AgfaQYLBB4MYWmk7sl39Y0W0M6nlyRUA=",7787088537673800989,7472444122783196167,6534948899391275426,-5908764595905400588>()) {
            case -1055521492:
               var10005 = var7.uS;
               switch ((int)com.yiyiaddon.m.b.a<"s7nki4zg3o2e1","hYjN7GlljfuMDLBIBkFBGohTUn+9ukFAnWVGN3G5OqQ=",5100326780407849965,-564840204202859420,-3064918896640226710,-4671463357108831137>()) {
                  case 701302318:
                     break label62;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10005 = var7.vh;
         switch ((int)com.yiyiaddon.m.b.a<"s2n1bv843mtn7","aXCrkriGpgKWg3GC2RxDQaTgsLpfdD8kW+XfrglpIYw=",1893297145405108865,-8238508233260616748,-9122879604312476751,-2466906799226984657>()) {
            case 1675435477:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.a.b(var1, var10, var10002, var10003, 12.0F, a(var10005, var4));
      if (var8) {
         switch ((int)com.yiyiaddon.m.b.a<"s39klcfkcl4id6","tC+N8vZIppG4le4l1jsy5QygnI+Rcldr6hEBBzkELzI=",-2651847086288395911,6992735534164595319,1638859654315759713,1401776415750731511>()) {
            case 1463294301:
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s3pytkp7950f3r","5vZvXoAdXNOCvHJi+WbKLKsFecwvZc2u7yqvZjpzq24=",-9036742017294911505,3793766621978153123,8586875365893272999,-5982529250948624539>()) {
                  case 1072810492:
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
   public boolean cB() {
      if (!this.i.fP()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2p2yksyiblskk","21yGW4lFkS7Z8QJUdvLBH8qer/0cS/wiQbqMLPoiCcU=",7215735200690317392,3940030779858796250,-6489687470925497398,888721230942204104>()) {
            case 9473297:
               switch ((int)com.yiyiaddon.m.b.a<"s1t5oo2zpjpu1j","w33dgvLm7zPANCnPL6SpY+Ua0qwnKKAnC1gLIeN9t0k=",-7520952360646325953,990507314702614614,2523017416456678684,6020401005530596062>()) {
                  case 2090106768:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3tta9oa0imh4k","VKJAqgwcVtcGtgaPJat4rXijAY43jLyw02H+JkRMDn4=",-4225535576571138722,-6380023408231397047,676184564913562200,-3351248232275727725>()) {
            case -1628206416:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3uewynbbwbtq","ACl3HHQtCtgujta+kfMjX5r8VaWZsqZHFS/mcJ/CFlA=",732557466433987429,-5175175496399052570,4916094596133361757,-2729434727637019644>()) {
            case 1228725735:
               return false;
            default:
               throw null;
         }
      } else {
         this.i.jL();
         a = this;
         return true;
      }
   }

   public static boolean keyPressed(KeyEvent var0) {
      f var1 = a;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26a7w9m27zqfv","OhXK63evpfafbgj5wnvZ1lNSLzCri0LFnxqFXS5XphU=",2159574545128439239,7962257704308229541,4848532506621028743,-3493717111118150313>()) {
            case -736849220:
               if (var0 != null) {
                  int var2 = var0.key();
                  if (var2 == 256) {
                     switch ((int)com.yiyiaddon.m.b.a<"st6mmks3u77w","H9hMPS/OFfkrc/BTjPWKU/nra/9J8A3xHNUAh6/tn4M=",4190878024004104742,8141810144021007503,-2837925676444772021,2469694870400992797>()) {
                        case -949023715:
                           a = null;
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     if (com.yiyiaddon.l.d.a.a(true, var2, var0.modifiers())) {
                        label25:
                        switch ((int)com.yiyiaddon.m.b.a<"suu7fbdxd86qr","AvUdqeTwgINgiGwKJg0Ho5kLKkxNq8fqqMYSYBXnh9o=",-1435699092814159889,-3048124117447797051,6056280330455597658,3103730967404982880>()) {
                           case 543953381:
                              var1.t.accept(com.yiyiaddon.l.d.a.a(var2, var0.modifiers()));
                              switch ((int)com.yiyiaddon.m.b.a<"s368gpkzlznmnf","PgMHIwDUeNWOgq07FmRXyPnwaidzBhNN0Wu4TuvUeDI=",3674651720929802288,6569638516214929611,706301858690882057,-5098230966121962039>()) {
                                 case 1530535265:
                                    break label25;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     a = null;
                     return true;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3bcepkus74pmt","GSll0rP3rwGieAA5RZ/aTchHDnMyN4fNeG64qRzmhqc=",-2835086693737927969,-3609113428120338835,597824116546303745,3286447763252981417>()) {
                     case -2116410099:
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

   public static boolean c(int var0) {
      f var1 = a;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jipiml705ia8","OeZzEJvglTWIPWW8Z+nAxhuCawyVDARGW0LI5KCZqbU=",4202316320752140812,-1463086924866463156,-5964337229869360950,-618753196199752958>()) {
            case -2108906093:
               return false;
            default:
               throw null;
         }
      } else {
         if (com.yiyiaddon.l.d.a.a(false, var0, 0)) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"s16211gqz53rog","01zMDdonZ2lGQBssI3JHiy1zbvjYcSt8F9beqsbZ/3w=",-7608027044774342967,7040753766706288149,4441671452511961896,4909247582005961739>()) {
               case 2008833054:
                  var1.t.accept(com.yiyiaddon.l.d.a.b(var0));
                  switch ((int)com.yiyiaddon.m.b.a<"s10lzaoxpyp1al","/xSxedHMovtFDfa2h9t8xA2TAxWdLUd+7TKHI/L4Eek=",536507833590310530,3074840943081032452,-1759835869253823574,1952188615306487695>()) {
                     case -1961642636:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         a = null;
         return true;
      }
   }

   public static boolean fX() {
      if (a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5sdwt57eqktp","jItf4FS0bOaxlmqIwHBfc2sHojRhHGgahJeTrKwb29U=",-5274473469905625005,-2262403000634756163,-2864034802733803028,-7138777210870964528>()) {
            case 135138247:
               switch ((int)com.yiyiaddon.m.b.a<"s3j785wau7hq0b","MMGkd1sgoDsTDORMkjbYG9oFvhLP6Mn0OazdOv7V/vI=",7151090549369537387,2553824024213724833,5959530132856602656,-6915601055020367709>()) {
                  case 269513993:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2ot5en42ga2g7","lMHhQVHtJ6KM5lGEPlSdCCN6rNr6QhFvtxm7HMdk8EA=",-6388469012975166850,-4667622166622659265,-4141434210138183856,-5724417660218789820>()) {
            case 2020853617:
               return false;
            default:
               throw null;
         }
      }
   }

   public static void kY() {
      a = null;
   }
}
