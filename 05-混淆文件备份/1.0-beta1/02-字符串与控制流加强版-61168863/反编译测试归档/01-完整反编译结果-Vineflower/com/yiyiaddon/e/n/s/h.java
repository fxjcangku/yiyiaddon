package com.yiyiaddon.e.n.s;

import com.yiyiaddon.e.n.i.s;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.b.t;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.world.item.ItemStack;

public final class h extends com.yiyiaddon.l.h.f {
   private static final String uY = (String)com.yiyiaddon.m.b.a<"s3d41zq699dbge","19YKoRktAZi+NoM+gDVE3hzCveLp/09r4lNq91FYHoJuG8WDxeC+YUksbQlIf0pp7IY3I7vs//+cQ8iu9F8=",486361579345648444,-7863335854604521758,-8438377758009877788,641309655088231124>();
   private static final String uZ = (String)com.yiyiaddon.m.b.a<"s1igyaps0rs18f","0rph0XSQdWSWHUAmY+uDT9KoiOt/iXk3ScVxxLxTqf0xRUfM9jWJB8gMn2qkhA==",5536736995752489314,-5257349066823949600,5865142360268173494,-2424533830296550956>();
   private static final String va = (String)com.yiyiaddon.m.b.a<"szpum9trwcbx4","XnqFeCLg0DB4eEtHwhNHH5vEOU44bt80ad3C9Yi2jKbZaZXRFAFGkB8rDnLYsw==",8843922567245541003,-1408210164521880872,-5857505951039852991,4122268923146915511>();
   private static final String vb = (String)com.yiyiaddon.m.b.a<"s10d1p8uqtqec3","ych5OZJznVEXNtVhwmC9xOYePsqZzyhlaVzIS9Bm9iVCo2xWbNwyVP7Yww2J2rU4yADZi/A6wru7+KOMhpGdkt8fVvKLjY14AzjE+Xh19axypvbCyMHD/9FIv4exgRTfQHDI12XdBGZWWBN3mrnuNnJualC0wmRbQ18=",8763971220664527027,-9078719166769636652,-8442732299530475571,-8936861630334869711>();
   private static final String vc = (String)com.yiyiaddon.m.b.a<"s3bdpaqi804fv7","7+Bz6EX8fpEeQMBptZGbAChNDk/uyGfH/GdeqNstLOxvTr5d5VXIeZaVUB5oJbXmQrOF5yXKM4soafdXkUdA9jlBsDmL74lBVbvw/n5HCxG2aPqMP5gK0QWk8dn+mQ/Dkm6FwuCH9DQt1DBgY+PlPL7VXDYGBwfsvVD6pNRt17mH4J3jI/W0duVnJjtkxC9pMlQvLDAeQHrIFhRc89WUs3cihtmGEd5WVbjE3USVT+fRkF7fHFN0NqafFSntaWRhBmrOmHxhJF4Vvrv0K/8k2oafPLKPhzChSTObdJ4E9ADBqwOCfxrabEtzIpI+la8KYbUdjYDwNwHVU1hIHc5laa+hyr3aWbYuKsl+UB/UxmpohHqgYMXkSaSwXWDzBSXM41q7Ve8e0izt3CzVEw5FIiDlosMmL78Ym0EBvEEwOT2YSRj1WcQgnyk8TVvGW0FzH4QNpVIhDQ3+c7I6xKkByUTWZpULU4uwP0jy6WKZhgyHq2I0/5HNBNitIE/nzUaMKRNzuL+DPoqvSkeMXRbx7G1qjUJsj2u72GRtDpXL1tuLCwRXfERM1dqS/jKsiAwiGUMB8vmMX+8=",4724950090682665601,5588562747387197363,-8862782466949693689,-4836359808285781415>();
   private static final int oj = 64;
   private static final String vd = (String)com.yiyiaddon.m.b.a<"s2tp40atl0re5","27kbpAMuw0beX5XezbTORHNlGGrGetaNGfsyLWON",-1551188587956929701,3507118424146076369,1928234150497160650,235134900611307155>();
   private static final String ve = (String)com.yiyiaddon.m.b.a<"s2a0m9ab5hywxf","L63eAY78827pwxmYH1fEFHxPUnj7jaDYeSt9iWtt",-2602254066705321722,988025016304271707,5194450058732904348,8468860394769231953>();
   private static final float cK = 36.0F;
   private static final float cL = 6.0F;
   private static final float cM = 12.0F;
   private static final float cN = 48.0F;
   private static final float cO = 12.0F;
   private static final float cP = 24.0F;
   private static final float cQ = 10.0F;
   private static final float cR = 10.0F;
   private static final float cS = 11.0F;
   private static final float cT = 10.0F;
   private static final float cU = 24.0F;
   private static final float cV = 22.0F;
   private static final float cW = 11.0F;
   private static final float cX = 20.0F;
   private static final float cY = 10.0F;
   private static final float cZ = 10.0F;
   private static final float da = 12.0F;
   private static final float db = 56.0F;
   private static final float dc = 48.0F;
   private static final float dd = 10.0F;
   private static final float de = 12.0F;
   private static final float df = 6.0F;
   private static final float dg = 6.0F;
   private static final float dh = 14.0F;
   private static final float di = 16.0F;
   private static final int ok = 11184810;
   private final com.yiyiaddon.e.n.b j;
   private final com.yiyiaddon.e.n.o.d d;
   private final Consumer<com.yiyiaddon.e.n.i.a> h;
   private final String vf;
   private final h.a a = new h.a();
   private String vg = (String)com.yiyiaddon.m.b.a<"s2pu9mfuqkx53z","o80kx5gOVuOx2Xni4OaKU96Z6YvWBTlpUzHsFA==",-4191159845501993808,301711609442473933,1668006291685605222,-2665688748170945876>();
   private float dj;
   private float dk;
   private String vh;
   private float m;
   private float n;

   private static int cS() {
      com.yiyiaddon.l.i.c var0 = com.yiyiaddon.l.i.c.a();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ci4jeb7r7737","KSv3mYnmZ4I0p4swAsovaCx7pq1ENXj3SqS4bgsKCMg=",-8618620512912740756,8925838405702913320,2230693947138706656,2133785479591275846>()) {
            case 1027735921:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s36xt0bzb2qcn4","ZZ/wzVAThk1Qd0AlvLjMyfJLaxKIskKLYdEV/3WSNzs=",-4001156698188219249,1533123693593941219,2681130256164359896,3207260022717382514>()) {
                     case 1747147285:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s2fvluglduymoj","AG84lXQ5j+ARA+0J5pa5i6oO+7h6QTFa7x9+RbHeARo=",2276040405738349208,619010924242413595,-6554260329075681028,6825134645921274943>()) {
                           case 783609683:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2aaepji74d58v","gUK06gKz7N91zcgBTJfvuhxC01JYvX7IswAWMt07JOI=",-4020138931020511905,4049718315561948380,-4542000423816355806,8798844949369723518>()) {
         case -688701040:
            return 16777215;
         default:
            throw null;
      }
   }

   public h(Screen var1, com.yiyiaddon.e.n.b var2, com.yiyiaddon.e.n.o.d var3) {
      super(var3.D() + "", var1);
      this.j = var2;
      this.d = var3;
      this.h = null;
      this.vf = null;
      this.G();
   }

   public h(Screen var1, com.yiyiaddon.e.n.b var2, com.yiyiaddon.e.n.o.d var3, String var4, Consumer<com.yiyiaddon.e.n.i.a> var5) {
      super(var3.D() + "", var1);
      this.j = var2;
      this.d = var3;
      this.h = Objects.requireNonNull(
         var5,
         (String)com.yiyiaddon.m.b.a<"s23dkvtqyith97","Q42YzrkiQjA6v1jyNvait3sTMbrDIkjeA2LyyEU9qAHPOyJZK7X/lg==",4084309907101428295,1851336930427442400,-8770886949895682601,-6582169376676009307>()
      );
      this.vf = var4;
      this.G();
   }

   private void G() {
      com.yiyiaddon.e.n.i.h.gD();
      this.d().a(new t(new m(() -> this.vg, this::aD, 64)));
      if (this.d == com.yiyiaddon.e.n.o.d.WATERING_CAN) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1no7el9dlbko5","RmcfZ4LOd8XQWrI8M27gh8v8oWBsVwV5UI3+g2mRcZA=",-971151516798726985,7270959023409395795,4119904580383892127,-6622041163888978382>()) {
            case -1302466862:
               this.d()
                  .a(
                     new h.b(
                        (String)com.yiyiaddon.m.b.a<"s10d1p8uqtqec3","ych5OZJznVEXNtVhwmC9xOYePsqZzyhlaVzIS9Bm9iVCo2xWbNwyVP7Yww2J2rU4yADZi/A6wru7+KOMhpGdkt8fVvKLjY14AzjE+Xh19axypvbCyMHD/9FIv4exgRTfQHDI12XdBGZWWBN3mrnuNnJualC0wmRbQ18=",8763971220664527027,-9078719166769636652,-8442732299530475571,-8936861630334869711>(),
                        (String)com.yiyiaddon.m.b.a<"s3bdpaqi804fv7","7+Bz6EX8fpEeQMBptZGbAChNDk/uyGfH/GdeqNstLOxvTr5d5VXIeZaVUB5oJbXmQrOF5yXKM4soafdXkUdA9jlBsDmL74lBVbvw/n5HCxG2aPqMP5gK0QWk8dn+mQ/Dkm6FwuCH9DQt1DBgY+PlPL7VXDYGBwfsvVD6pNRt17mH4J3jI/W0duVnJjtkxC9pMlQvLDAeQHrIFhRc89WUs3cihtmGEd5WVbjE3USVT+fRkF7fHFN0NqafFSntaWRhBmrOmHxhJF4Vvrv0K/8k2oafPLKPhzChSTObdJ4E9ADBqwOCfxrabEtzIpI+la8KYbUdjYDwNwHVU1hIHc5laa+hyr3aWbYuKsl+UB/UxmpohHqgYMXkSaSwXWDzBSXM41q7Ve8e0izt3CzVEw5FIiDlosMmL78Ym0EBvEEwOT2YSRj1WcQgnyk8TVvGW0FzH4QNpVIhDQ3+c7I6xKkByUTWZpULU4uwP0jy6WKZhgyHq2I0/5HNBNitIE/nzUaMKRNzuL+DPoqvSkeMXRbx7G1qjUJsj2u72GRtDpXL1tuLCwRXfERM1dqS/jKsiAwiGUMB8vmMX+8=",4724950090682665601,5588562747387197363,-8862782466949693689,-4836359808285781415>()
                     )
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s23tchfzapowiw","KQWeYb5fZgf+cG3SmHhgLwt/dmqb5xrIwonON0sjQCY=",-5655605654803982013,161449642286458658,5394513907296509780,3809831496902546161>()) {
                  case 915374469:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.d().a(this.a);
      this.u();
   }

   private void aD(String var1) {
      String var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1sh5vt48juyus","J9W6Yk1B0LimGpLvvTtwPEGyRMaJ4tUtez9rasg3p3k=",-87391018392770578,1483486294691825050,-8455573816402437990,-5290221662293605213>()) {
            case 1212722701:
               var10001 = (String)com.yiyiaddon.m.b.a<"s2pu9mfuqkx53z","o80kx5gOVuOx2Xni4OaKU96Z6YvWBTlpUzHsFA==",-4191159845501993808,301711609442473933,1668006291685605222,-2665688748170945876>();
               switch ((int)com.yiyiaddon.m.b.a<"s3ft26rrm0ji5p","QgcIQDjzFXL5SHtFINX22nUWGb+P8U61NrYE+okuOek=",7655415354873638511,-8711872268908025860,5001827621772083905,8700451494802637364>()) {
                  case 934755507:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1.trim().toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s3dpvf2vrh21cj","Fj7A1DYYwKVXAGvBZ7V0cyIKzY605lkLf+pNWrimQis=",5606450181051229154,-402560135047776057,-6222248023412365153,6875949074264279712>()) {
            case 985974145:
               break;
            default:
               throw null;
         }
      }

      this.vg = var10001;
      this.u();
   }

   @Override
   public boolean keyPressed(KeyEvent var1) {
      if (com.yiyiaddon.l.j.m.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s18931wv44ef3a","/QXT5S6a3+iij73x4bwg7ruVbJCAnMJRyiNBbKXzz/8=",-9040184218214230786,-8756288723151206245,5410266755255442242,5940329699786930774>()) {
            case -1002074299:
               return true;
            default:
               throw null;
         }
      } else {
         return super.keyPressed(var1);
      }
   }

   @Override
   public boolean charTyped(CharacterEvent var1) {
      if (com.yiyiaddon.l.j.m.charTyped(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s19zy01tpbm3gx","xJxBUNVED800VMvPKLRFAs/AU1+4Idd2GLtqqKsd1tQ=",5062186220196417270,6799118672445756672,-3229544965098640439,-6683062723839807535>()) {
            case 535363816:
               return true;
            default:
               throw null;
         }
      } else {
         return super.charTyped(var1);
      }
   }

   @Override
   public boolean preeditUpdated(PreeditEvent var1) {
      com.yiyiaddon.l.j.m.a(var1);
      return true;
   }

   private void u() {
      this.hF();
      this.a.f();
      i var1 = this.a.a();
      i var2 = this.a.b();
      String var10000;
      if (this.d == com.yiyiaddon.e.n.o.d.POT) {
         label113:
         switch ((int)com.yiyiaddon.m.b.a<"sy99t4gi09dbi","9mElZUlFOf3eKJzASNuEtHYfqYDh7xLJwdYXvsx9LPQ=",4098807985218963401,4999356231475239845,-6496584619975775612,473717248446953858>()) {
            case -819803354:
               var10000 = com.yiyiaddon.i.g.b.gm() + "";
               switch ((int)com.yiyiaddon.m.b.a<"s2vhkswfzx0dug","4FHjt7lnNeARnvVgn0Al5KidZ6W+oo274r8AryfOlIw=",-4564801589597012317,-1165997062897843566,-7172105009457892344,-4876709293513159657>()) {
                  case 1293261923:
                     break label113;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = (String)com.yiyiaddon.m.b.a<"s2pu9mfuqkx53z","o80kx5gOVuOx2Xni4OaKU96Z6YvWBTlpUzHsFA==",-4191159845501993808,301711609442473933,1668006291685605222,-2665688748170945876>();
         switch ((int)com.yiyiaddon.m.b.a<"sqdm2gsttka5f","V2OiQ6iaQxUhMq9w1D6lL+y3O+5kEBZfLy0RZ5BvuhE=",3807295362572494989,4113559594215710823,7700103490352075707,191104191147452123>()) {
            case 981186085:
               break;
            default:
               throw null;
         }
      }

      String var3 = var10000;
      var1.a(new w(this.d.D() + var3).a(22.0F).b(11.0F).a(true));
      var2.a(new w(var3 + "").a(22.0F).b(11.0F).a(true));
      List var4 = this.bc();
      boolean var5 = true;
      boolean var6 = true;
      if (this.d == com.yiyiaddon.e.n.o.d.CROP) {
         label94:
         switch ((int)com.yiyiaddon.m.b.a<"s2a33hkaok7hyr","QbXr2e7KxdHHbswmnUFITKy13jjh5DCwq2kKid2cysE=",-1218008746179024500,-6734533941115981502,-2938382278760383751,-7572500666095480431>()) {
            case -1417829765:
               Iterator var7 = this.bl().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s3i1mjhitxlp92","WIp9w65xMddSFpNEATrE4onjHML8e2xzQOSWSL+67o8=",-5014789653852361251,-3187289303609848072,1396566594233834006,7683678731412253354>()) {
                  case 2062821458:
                     while (var7.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s357nzojeg17yv","U2mBDtzo0/DPT1cW28ArccJg2GxIYaP7NWuvfLWvOaU=",1121174923692289187,1814510012548542666,7761353112451850039,-5278743787897788567>()) {
                           case -1208030164:
                              com.yiyiaddon.e.n.i.a var8 = (com.yiyiaddon.e.n.i.a)var7.next();
                              if (!this.j(var8)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sxxkpi3sbwo4c","PI09aaq/WOzi1L4XlgaqANoJc9w2pt42cg0IexKeY7U=",5672374080074567232,5142860144038210071,4674278420367244836,-2781464279494732189>()) {
                                    case 1709088015:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3o9dtrj3uac92","3IzIKeblcJ13ZoouUrwqa5oe6GL0iAqhisK9DVlbhkw=",-2245163302806044414,8137267874174265137,8764549127812423980,-4017622298607981437>()) {
                                          case 1836063631:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (var4.contains(var8.dk())) {
                                    label102:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1kbk5sx528m99","fdpD07dZ22wPgG6Ep8urUwiBw4+4ocf52HJ+eGYDi0s=",7291285015540313531,-2973172354525611283,-3740630235500460034,-6776948413158428514>()) {
                                       case 1839622329:
                                          var2.a(this.a(var8, true));
                                          var6 = false;
                                          switch ((int)com.yiyiaddon.m.b.a<"s14m5qqsm650zc","n8SdpTiGHYXmk6MaLPSgB3Gwo51PkUNNmHK5R2mbF+c=",2348278195929737746,9203954767368917171,8342613865204638727,-455073272274839340>()) {
                                             case -2143614385:
                                                break label102;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var1.a(this.a(var8, false));
                                    var5 = false;
                                    switch ((int)com.yiyiaddon.m.b.a<"snxwle7nr345e","o+Vfh7rN0018L1NsbLjfAtH8qIaLhn20l6QSWZggNvU=",5766627100528303005,-1137027762464229828,-1823312851495154527,-6430528358646727707>()) {
                                       case -1561249987:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1061t2h0xkeky","5d/pbmdSHI2XtrKwXV6dTpe3sOEvaPe0ObdeP7WOcu8=",1085109309155119071,-7630945668595592568,-5112906086107988072,4412199561453295863>()) {
                                    case -1505221450:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s11mrovlv718d8","fbWPcZRssIG5As7eHxIi/K+oh9bm+XVIvw/Ywrz408E=",-171835461919217047,-5307953213390838712,656556303312369557,2237647826592868557>()) {
                        case 1241653052:
                           break label94;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Iterator var9 = this.j.a().a(this.d).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s21uescyblvwhi","IW+BrKJrtYOLcuoLeYADPGZO6ToA9T+EoORn5jg5B4o=",-9014890503573577831,964688884488334020,7857249138552203652,512245401616055147>()) {
            case 2039514527:
               while (var9.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sjnw62cgeeh6y","DP0Y3UOZRvDUxvEMMjhMklhT8eRYicofU7Z7CfzJqGw=",1729778041955150953,-7239015352342498409,-7223010981753301370,4597874095686183712>()) {
                     case 847843055:
                        s var10 = (s)var9.next();
                        if (!this.b(var10)) {
                           switch ((int)com.yiyiaddon.m.b.a<"sbprfvhslf6zu","EWGrSETCHhScS4cipZ0CMfV0bmHHmsYTicc44Ta4uOs=",-4862474633978580689,-6446538114552897587,2486964710346218793,-4043700513931110484>()) {
                              case 427102947:
                                 switch ((int)com.yiyiaddon.m.b.a<"s238hxpxh8ot6y","PggyU1TZEDPIqGc0mBUuikx5HkwwtIji3tgTJQa0YC8=",-5342048740029170086,4357730407978087087,5816386382878717757,5412004880038121997>()) {
                                    case -1680108381:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var4.contains(var10.L())) {
                              label85:
                              switch ((int)com.yiyiaddon.m.b.a<"s1vim8dwend08v","aACRviYto9TBQ4Hv4yvK/7BcSTuFJsviL+21Hf3TPFo=",2981862529042831662,-6205480440414070374,1348069260450113892,-7379963083790259701>()) {
                                 case 1307865432:
                                    var2.a(this.a(var10, true));
                                    var6 = false;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1fysjgdvdas7p","dBY4NB+AyII12s+niSV9S93I/gIrXDffKkRBneUj1Po=",-9165375121414315125,7496786760864834928,-6038645450297981001,-8782789101859201260>()) {
                                       case -660780330:
                                          break label85;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var1.a(this.a(var10, false));
                              var5 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s18j3mt9s8aaa0","MrYzml2nFPeDegXAZXJ240nls7Mxt/AMGlYBCOmJsNA=",808888487016308211,1704846688325696391,1860341151616949995,-4409357784409763936>()) {
                                 case -1206346299:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s33aua4882xhf9","hEWb2bo/Gw974hasuaroXtDmf86PPw6vv8GxrTqMk5o=",5245165916799953934,5254663640338413371,-6613888704092293461,-3222388802462902114>()) {
                              case 1036983980:
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

      if (var5) {
         label73:
         switch ((int)com.yiyiaddon.m.b.a<"s3ilcu72czwil2","DcXTwLl6GbdAfQqnWHVcuVCK2GG6bDBguQWaXdCd3Oo=",641951664312412801,1812482559966048726,-5927953171198735116,-2399531461288862585>()) {
            case -1066122644:
               var1.a(
                  new w(
                        (String)com.yiyiaddon.m.b.a<"s3d41zq699dbge","19YKoRktAZi+NoM+gDVE3hzCveLp/09r4lNq91FYHoJuG8WDxeC+YUksbQlIf0pp7IY3I7vs//+cQ8iu9F8=",486361579345648444,-7863335854604521758,-8438377758009877788,641309655088231124>()
                     )
                     .a(22.0F)
               );
               switch ((int)com.yiyiaddon.m.b.a<"s35st04ju8sj7p","dm+VvJHvByYaGBOW+Rdck50+6dLcXI3X4FZZUzNWdwA=",-8045931736116859561,1574304664075489427,-352409648904964555,-5688922465604590314>()) {
                  case -1709245789:
                     break label73;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var6) {
         switch ((int)com.yiyiaddon.m.b.a<"sclm2h441r6wj","MxuSwSn/M++r6bmF7E51zXOahWAGBWzvgNzX/qjd94M=",-7340719829306828974,7913370251456716525,2966643690543110784,-8108805747076566391>()) {
            case -974887070:
               var2.a(
                  new w(
                        (String)com.yiyiaddon.m.b.a<"s3d41zq699dbge","19YKoRktAZi+NoM+gDVE3hzCveLp/09r4lNq91FYHoJuG8WDxeC+YUksbQlIf0pp7IY3I7vs//+cQ8iu9F8=",486361579345648444,-7863335854604521758,-8438377758009877788,641309655088231124>()
                     )
                     .a(22.0F)
               );
               switch ((int)com.yiyiaddon.m.b.a<"s10w9q8xyevuf7","IU6fSj9AaIBweo2HStPJVCo/difK1ZZpvua9SUcTie0=",7600335102000751270,764256489941909614,-4424801156921467184,5782473118669208536>()) {
                  case 1405635186:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void hF() {
      float var1 = 0.0F;
      float var2 = 0.0F;
      if (this.d == com.yiyiaddon.e.n.o.d.CROP) {
         label32:
         switch ((int)com.yiyiaddon.m.b.a<"s3m8jee0efu898","As2xUOaNFe2XNGWUsFLCh3nceqwL9Zo11OgdOTepK9Q=",2666761518258791126,-3790196125930824888,8728462231073871965,-7466712152730596546>()) {
            case -2073629823:
               Iterator var3 = this.j.a().aV().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s39tpx78g9zzg3","NTFZC+C3j2qi1WeE/dvwCxI9Z0pP9Pqk0JTmj5AXi7w=",3780752321001286752,-9158397979031787019,-2097451219582385475,-7760071608689506727>()) {
                  case 316954561:
                     while (var3.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1blk5o8h7464g","+9TPz4liXhFSRaaXOQYxkXpmIOipSaBrUQewrwd0zlk=",-5520849891620300729,-4132571061434364030,-1848581651162215966,2422903786586745022>()) {
                           case -1549923977:
                              com.yiyiaddon.e.n.i.a var4 = (com.yiyiaddon.e.n.i.a)var3.next();
                              var1 = Math.max(var1, com.yiyiaddon.l.g.d.a(g(var4.dA()), 11.0F, false));
                              var2 = Math.max(var2, com.yiyiaddon.l.g.d.a(g(var4.dz()), 11.0F, false));
                              switch ((int)com.yiyiaddon.m.b.a<"s2fg1ndw74pl8v","ZtNlQsMASoVK8xAUP7vDUrClaCxGyq5O2JZ4xsF7lT4=",5264891956131233326,2469280121450519267,-40323347047074657,-9126196837140645070>()) {
                                 case 709835210:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3fuz5ojb20slb","9q30LaPcscTeMUyLrGDsDwyliG2ls8tSBD3e/JNTOIU=",4140425457535506329,-7482737956853872026,8693818406223017144,-3452775118925522731>()) {
                        case 1031738472:
                           break label32;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Iterator var5 = this.j.a().a(this.d).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3j9m540ln8g1b","+1SeroO9SXjfuyX7Ycy7SLvcaTcmpT+J6zxDgV4E1gc=",6436211543196285398,-1168595910168891742,-5079537052321158032,7194928154666017267>()) {
            case -1826104759:
               while (var5.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s29x7xsg4wook5","tlNYhuqizhZUaMi8iue+YF4Plt7Fr22AwW8WdcRG6N4=",5893818261764494809,6425453127273117529,-7116660914408050559,-8608348758163106672>()) {
                     case 41750907:
                        s var6 = (s)var5.next();
                        var1 = Math.max(var1, com.yiyiaddon.l.g.d.a(g(var6.m()), 11.0F, false));
                        switch ((int)com.yiyiaddon.m.b.a<"s22koi37w52f53","SuY6ROomkPqm16zrxLqwcj6Lg6IYFlD9el2wtJBpDyQ=",1766826938501369518,6286833559609683636,3707945556971378600,-2682999153896524721>()) {
                           case 1966267242:
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

      this.dj = var1;
      this.dk = var2;
   }

   private List<com.yiyiaddon.e.n.i.a> bl() {
      ArrayList var1 = new ArrayList<>(this.j.a().aV());
      var1.sort(Comparator.comparingInt(var0 -> a(com.yiyiaddon.e.n.j.a.c(var0.dk()))));
      return var1;
   }

   private static int a(com.yiyiaddon.e.n.j.e var0) {
      switch (var0) {
         case NORMAL:
            switch ((int)com.yiyiaddon.m.b.a<"s1smdsyl3ra3kw","3m5b9GgUFrgymSyeH2LAJeMb9PJJCtuMZkt2O+viF/g=",7617466837860053316,7719839552448643755,6701697048216726211,8114635332249292670>()) {
               case 974637755:
                  return 0;
               default:
                  throw null;
            }
         case NETHER:
            switch ((int)com.yiyiaddon.m.b.a<"s1yucwj3zv9hth","Lgtuslb9dQZlmex5T+MPmkohqzwMLnpAz+HvHRZsKvU=",-3234553876153477272,3094433087577574497,1438631015146420103,-3467397921009173615>()) {
               case 1745212703:
                  return 1;
               default:
                  throw null;
            }
         case END:
            switch ((int)com.yiyiaddon.m.b.a<"s3irvsq2rqa3z0","MouAKYjO6eSCwhHgfyNc3cKa6w5JsYwsHliBZ+5ZZMk=",-6738226124237424829,-1670822674267981108,6281645838553470950,-9197106749267118531>()) {
               case -97435427:
                  return 2;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private com.yiyiaddon.l.b.g a(com.yiyiaddon.e.n.i.a var1, boolean var2) {
      String var3 = c(var1);
      return new h.c(var3, this.a(var1), a(var1, var3), var1.dC(), g(var1.dz()), b(var1), var1.dk(), var2);
   }

   private String a(com.yiyiaddon.e.n.i.a var1) {
      String var2 = g(var1.dA());
      com.yiyiaddon.e.n.j.e var3 = com.yiyiaddon.e.n.j.a.c(var1.dk());
      if (var3 == com.yiyiaddon.e.n.j.e.NORMAL) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xrb2z1wsrlua","zlnpUYRHIewzekRq2UbuVcoMxHgni6qoJ6xw0QymFsc=",-4605653646042647577,4048499681591618249,1668944707930588376,1281336142114658692>()) {
            case 2070990147:
               return var2;
            default:
               throw null;
         }
      } else {
         String var10000;
         if (var3 == com.yiyiaddon.e.n.j.e.NETHER) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"s2s4zo6pgwu9uc","YpGVcyWx6UNozwP+Suc56dX/uoyOXp+T0XdTjNitWSU=",-3499515605754295439,-1115758485571518325,1830241794920028625,-7538396446263642120>()) {
               case -257368729:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s6um5m8muyuq","5srPgBW3G9aaXS4fMBR82fHAlkdR4kxDTncxi79Tp2QdZK+5P8JddA==",-1246884037164211670,-1876857637164708874,-1401831947268485937,8490851722474428328>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2e0vikejkndvr","B/gNi9Q+xYUlXVs6TtnvxujwzC0LJbhbs9QBQlU990U=",1986589882686322814,4420682825415670831,4857203901544220644,5233445899609388079>()) {
                     case -1568216663:
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = (String)com.yiyiaddon.m.b.a<"s2ku7v9pv4fkcv","oPdlW3DHCogg4YmuIG5D2F9R5j/OJ8y9vQUxP+//ej6CJTvtrWnQSw==",7011191938025010437,9198239348995396700,-8308155565435598015,5019070001073444620>();
            switch ((int)com.yiyiaddon.m.b.a<"s1k5dxz7s4qoan","Hl8IqdVUQEJGewx63V2EiCNCKSACog0rzwHT/5/nCpg=",1821271647433015758,-5305258886277273832,6740551754465418272,6185545479519012454>()) {
               case 492803351:
                  break;
               default:
                  throw null;
            }
         }

         String var4 = var10000;
         if (com.yiyiaddon.e.n.j.a.ah(var1.dk())) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s11k25bz9qcbt9","TtiDIvjkievpLA/tufwdpGgDSXx4RgEIkWaLrjXxXaI=",6273416773335408132,-4577834992457613631,8270193964241187748,6530133331375884282>()) {
               case 1767516050:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s28avlj8pov7aa","34Q9pP+l9/xVZNTb+HFLbhjHifsPrWWEX5yzaXXklsfO9A==",5425459592045511940,7462300161145903879,-5103928122490151095,2062633457716230626>();
                  switch ((int)com.yiyiaddon.m.b.a<"s8jn9q912pedy","8PtGMEXFinn0AIvh1qoNiJ7tJR106dBs5bt7S+jAE1g=",-202547858809571787,-3961770525155816194,803779039824316324,5914473526946394104>()) {
                     case 420977768:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = (String)com.yiyiaddon.m.b.a<"s2pu9mfuqkx53z","o80kx5gOVuOx2Xni4OaKU96Z6YvWBTlpUzHsFA==",-4191159845501993808,301711609442473933,1668006291685605222,-2665688748170945876>();
            switch ((int)com.yiyiaddon.m.b.a<"s2hkcmqhnx7g7p","//7EQovVc0JL10hRALO5j06ppgZD28zXqhHm75AKu84=",4615010839875045909,-6302255580348435097,-1313538210514579020,-3857810249479028478>()) {
               case 1957931368:
                  break;
               default:
                  throw null;
            }
         }

         String var5 = var10000;
         return var4 + var5 + var2;
      }
   }

   private com.yiyiaddon.l.b.g a(s var1, boolean var2) {
      return new h.c(var1.dE(), g(var1.m()), a(var1), null, null, null, var1.L(), var2);
   }

   private static String a(com.yiyiaddon.e.n.i.a var0, String var1) {
      return g(var0.dA()) + g(var0.dk()) + g(var1) + bg(var0.dx());
   }

   private static String b(com.yiyiaddon.e.n.i.a var0) {
      return g(var0.dz()) + g(var0.dC());
   }

   private static String a(s var0) {
      return g(var0.m()) + g(var0.dE()) + bg(var0.b().m());
   }

   private static String c(com.yiyiaddon.e.n.i.a var0) {
      if (var0.aR() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqqg9yjqbc872","3nIAWbmwPPoN5I1kZbIo8rqubifSUodTDU7XAQRMqy4=",3118172025986569906,-3620777095931612514,-6407413027847616166,1318707864788704519>()) {
            case 1522937329:
               if (!var0.aR().isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qwtn6l3rkqqx","u5TDuoi4/sCC12U2m/SNSmvuOIWWBWxrVIDGUarulsg=",1305044719189079888,-8497627720874916652,361953208076777401,3166459822879843509>()) {
                     case 2137193495:
                        return var0.aR().get(0);
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return var0.dC();
   }

   private static String bg(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3f7s6ba0q2g8z","EfFWQqYVTIlNNJyHRC/Y5KoOa9v+7e2hCwSuc1z5yHI=",-8652420682546360464,780209687961042192,-5403808433551793709,1680705416066283800>()) {
            case 1813564925:
               return (String)com.yiyiaddon.m.b.a<"svcwunrug32k1","I7/q0TuTufnY+KoqKxIoryWM/FswteTYWByi/88Bthg=",-6306821423534038724,611416329787750815,4784325465494769913,-2586461367252321846>();
            default:
               throw null;
         }
      } else if (var0.equals(com.yiyiaddon.e.n.i.c.VERIFIED.m())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bguurihebgan","xy9Zg+gvo5w/7BTgYUMcVct/2mYc/XFF6i/hR8g2GD0=",-9023046198200424305,1977453374788607260,9050417697949990896,-5126092462099421153>()) {
            case -164028064:
               return (String)com.yiyiaddon.m.b.a<"s2tk7pvifwc9n3","EnVAB7jC81ovVCAog+l4wbfHnSECws23AK5zsuL447pVWA==",-4034216730736623326,-5876281974230486203,-2396020861553719952,3568298191750256922>();
            default:
               throw null;
         }
      } else if (var0.equals(com.yiyiaddon.e.n.i.c.DOCUMENTED.m())) {
         switch ((int)com.yiyiaddon.m.b.a<"sa9d3qypzwgff","MiJUYSB7Jtm9SMRULfPZDKdZ3AWB/xMb1JHSRvrFkNk=",-588131762471314090,1165444373704071470,-7316691691047435542,5616381895734277285>()) {
            case -55143502:
               return (String)com.yiyiaddon.m.b.a<"s13q9iftnm9dd","21nobFxKYF7Xp8ff/r9x3Xf6zyevkfAPkHQ+8TVlosY=",-2013377610370547743,2030510117911492778,1162871076377788280,-1500424422690705005>();
            default:
               throw null;
         }
      } else if (var0.equals(com.yiyiaddon.e.n.i.c.CANDIDATE.m())) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pyfs6boi392f","HQs2HQpuvTVEDGJXLLnjxfvjBDkrYkkq+TlVUgK9oZs=",-7340864298397219742,3299788040582525987,2285631788451195606,-7974020234280598480>()) {
            case 637114497:
               return (String)com.yiyiaddon.m.b.a<"s2hf0igl0kxu41","9d5dxiNRoZv4RL4SCwAEeB+c7O/5YQ8UbKVgTvw1+cs=",-1642474974299297300,7130549632645046579,-5365153029471840893,-339176396241837266>();
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"svcwunrug32k1","I7/q0TuTufnY+KoqKxIoryWM/FswteTYWByi/88Bthg=",-6306821423534038724,611416329787750815,4784325465494769913,-2586461367252321846>();
      }
   }

   private static String g(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s5h6uwxrgei4s","tRQgzNDJ+5TKTnCSew8mmrcrIyL74CMdhkPkJUDhudE=",-3189760411586254686,-1748003462776525292,-3667966569352882102,-1474560653389322897>()) {
            case -53506372:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fjabsz7l8kt6","nbalemlZvnRI+yBlCRwCXPiddl+MUij8fD24nh/fKSU=",-6197058293965843253,-3820326695230667197,2191050159838529422,7921760194914045595>()) {
                     case -402108663:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"sxfych78u6pwj","xe0QtO/VnqzSQFPUnf2v1OdrDpj/4S1ghzqgjesM3fg=",286881111098561220,8363722766834216639,-4938648519367388001,-4229008679453982789>()) {
                  case -1455727269:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s1l2l5pkpnl3fq","ZwHIXhTZh02d6uUO9m0rqnuASlQeTN3TpILK5TxZ",4755772517169496721,-6761836451690165784,5462121524445037797,-1758537100212449277>();
      switch ((int)com.yiyiaddon.m.b.a<"s2rd6ij3oif7e4","hXdHG3oVQR6Ju+HhHHy9ThMWiCsHLiqY7Ph0RK+0+9k=",-7225214460717403213,1813792848327675240,2228436563854605349,-4885661805068230925>()) {
         case 381535176:
            return var10000;
         default:
            throw null;
      }
   }

   private static String ag(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sgutlaf70yqky","5itD5rL66lKtuqQNWSmJyboEebqFZ5mw3jFhyBm1tb0=",-8985934417147008075,1446977874221371819,-858141076304056409,-5701838135240011455>()) {
            case 2025517464:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s2pu9mfuqkx53z","o80kx5gOVuOx2Xni4OaKU96Z6YvWBTlpUzHsFA==",-4191159845501993808,301711609442473933,1668006291685605222,-2665688748170945876>();
               switch ((int)com.yiyiaddon.m.b.a<"sjria5e6x3d4m","VxI+8dkvhQGoECB0KXqNHwk5nbBWis+sBo8BwPWh2Kg=",-3918229710947668619,-5472346217428044099,-1288389051754415986,4706305616053916142>()) {
                  case 662763925:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s1mnpip5egc7cd","4SQuB9YMk8cIbTix2/Gd56hx8m2hrPGQTgYqgRxAk1E=",5654704155516332077,4403987954920457351,5614872305445010324,-834934637379337584>()) {
            case -505711271:
               return var1;
            default:
               throw null;
         }
      }
   }

   private boolean j(com.yiyiaddon.e.n.i.a var1) {
      if (this.vg.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sbdrzi0hhq2b4","PQuSZzxjZ7q7maUAyP4UsMAP1TZiJ+3RC+55g7SmjSE=",-5515530055756241149,2530997739943113901,2157836816026973799,-1592683992690410842>()) {
            case -339260249:
               return true;
            default:
               throw null;
         }
      } else if (ag(var1.dk()).contains(this.vg)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2m5xuswzipk1b","CihCgMh/loy60fMIw4kA5OFFZcM4kXU9prjflieikXg=",-6748312213338307,-1343666950823097373,-568003065155907984,-3784817861262285258>()) {
            case -368191374:
               return true;
            default:
               throw null;
         }
      } else if (ag(var1.dA()).contains(this.vg)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nha2aamfr6s3","hyRtVgBxwSxUIqfmrySOLMGWVh43uuNOxE2EuIoOO0E=",1770318970639298781,-5152191754205552791,-3170647179562030117,-7665152470115582343>()) {
            case 632334408:
               return true;
            default:
               throw null;
         }
      } else {
         if (var1.dD() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3gfubsol9cadn","/ifrO2u5bckm2gd1rw6CPPg5679ecuC2C68S5csRiCA=",-8674327646288692354,8701294330883903706,-7816593099732008406,-2969592618876756407>()) {
               case -309124589:
                  if (ag(var1.dD()).contains(this.vg)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pg4x8h0pkha5","5QCSCl1vz+zjm2/RxGfrQudNiGiTkw1+PdSuOp767iE=",-2046134614450020342,-2569258997662455134,-1651173559572055196,-435208314500759420>()) {
                        case -630513612:
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

         Iterator var2 = var1.aS().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2qyfaloxhylb5","ddMNw6iSABRW4P2Qv8L2I6k4k0r8PC6Py2XNbaikRXc=",-9154118307232623567,-1021454321856225359,-1336651744352206683,-5384599919104098632>()) {
            case -296128406:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3eyf9xhapgbb4","iUJP22vD0y//6dMntfKSDJpOe/myI4EgJry4VLxXiPs=",2366201395568419864,-4137754508462223847,-8254307414725822313,-5803593814152983869>()) {
                     case -2128484436:
                        String var3 = (String)var2.next();
                        if (var3 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s29f6z41eupf6x","vnSoHZyEqrxY20cqt2cXeiuv7nY7XMs+iZbWhTv9y1U=",-6361372606617816800,316274493228838846,610456746588241405,-6692095415754459553>()) {
                              case 347199777:
                                 if (ag(var3).contains(this.vg)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1rb51ttbuk76a","lSeMUSVb5dGVrA7KUgcSeAicCzlGrPcBWLLOjECMcKc=",7574747656619316096,-4360449074886815461,4338233037137385565,5760593659603636768>()) {
                                       case 2038186720:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s18c6wzn4imh3k","vrJpgeWgdjWIb9+uQ+/OIxsdFOmZZ5fQm+GU1gviyRw=",-8108098885655743907,6526377500953418860,1835527531194663560,4300652773134659095>()) {
                           case 117438876:
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

   private boolean b(s var1) {
      if (this.vg.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1iafjd3a3i9m5","4nDRZh35i/EVZwvgNZeT8bwSRXkaoYSLnbqaZZfrbMI=",298698202458918384,4140647864733697883,8628403684543437249,-2590080404921495683>()) {
            case 805234522:
               return true;
            default:
               throw null;
         }
      } else {
         if (var1.m() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qxfnvof88sa8","KwDHf+d2mTSeNGZogkicZxhsoe0ULs1Nr8VuNNG3jgo=",1740145252100401068,-3324755616480750901,-7956153890857076699,-5427914500091172511>()) {
               case -576232550:
                  if (ag(var1.m()).contains(this.vg)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s32vdh03fuv8nc","Q4OvdelIUTS6fjkCyjLKZsCnQ0TalfVmzhpXDLt/5Zo=",6975627643437106969,7277306651409322247,-6800468178479342512,1580534930872534315>()) {
                        case 584484504:
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

         if (var1.dE() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sq5jntxuxusry","3o+WVHQuSEzvNzcN3k7t6A4mBHzyySMvfq2BYRxdlNE=",5571766517412266045,1726898345871511451,6409267979183242857,2150022897925955245>()) {
               case 956514487:
                  if (ag(var1.dE()).contains(this.vg)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2o8ybfnh6s4z5","XW1qOgV3Tu4mHJFvlyNUP5hz7I5SJQVC46dTb/6mcbc=",9148079280355499577,482413060250231501,8276002571437010419,-1273790364347672613>()) {
                        case 252846233:
                           switch ((int)com.yiyiaddon.m.b.a<"s1d1bufbtfw8d5","EUCG4QJWY8vHf24jYPr0HjA1tlWaRX2+jgF0e9puy4Q=",5929928907102138709,-1099397105706341786,2908483020129957473,3408663930449348083>()) {
                              case 807684642:
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

         switch ((int)com.yiyiaddon.m.b.a<"swf5ruh4e8dnd","nqX/KdyxKKd8tivvy60JKfi5MABJlELpRMtq3dt+5Z4=",1479970175860672586,4442229233892405787,-8367960050792498875,-3011982279985098643>()) {
            case 1873243088:
               return false;
            default:
               throw null;
         }
      }
   }

   private List<String> bc() {
      if (this.h != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kkxbt31mht8l","MxmYfmefi97BmC5ctISGtFIt9v9lV9e+aZPQ+yNAHOA=",7424372700058496502,3095864486888252450,-2776427989328118401,5005766798644405330>()) {
            case -1782761474:
               if (this.vf == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ifj1eim1okru","QQhHbxThrgJ6MrxYYft0dQtjv2rpJQ5ToRp9v6DulOc=",2245011367465012876,-7444902955300542687,-5005847016025955575,-2373463820841433642>()) {
                     case -50961598:
                        List var1 = List.of();
                        switch ((int)com.yiyiaddon.m.b.a<"s24lcawzrdsrza","jeba01sC7NwlbS/77YXbKF3pyvBfg6gzlsZRoOq6U+k=",-7844002657715695659,-1605496545010570354,-2409620326292320032,407006427817329766>()) {
                           case 1026786855:
                              return var1;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  List var2 = List.of(this.vf);
                  switch ((int)com.yiyiaddon.m.b.a<"s253msegyy2hc0","vkdgVIJJlzZgA1wWG1ZmoazZ1RMoKAmOEShtrqiLTBg=",-7594416946526185852,-5300072659732116018,6974661655102868525,-2367506305543622449>()) {
                     case -316861355:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         switch (this.d) {
            case CROP:
               List var8 = this.j.a().by;
               switch ((int)com.yiyiaddon.m.b.a<"s1griymw6m3je3","oxW47pRhskqi75P4/+Ev+FP6xH/3kPnsoP54qNqHC/s=",-5052197944918799357,-1971289507458306666,-3528202036888869357,-1236641409720837070>()) {
                  case 786487786:
                     return var8;
                  default:
                     throw null;
               }
            case POT:
               List var7 = this.j.a().bz;
               switch ((int)com.yiyiaddon.m.b.a<"s2s9uok8gpyy06","mV66XK4DFXgjMVd4gW3V4TKzYmZ6/SSaD2ObVSCXkI4=",-3638650167420309285,-1845434189575183759,-558922003938608980,-6781687980927098375>()) {
                  case 1559794393:
                     return var7;
                  default:
                     throw null;
               }
            case FERTILIZER:
               List var6 = this.j.a().bA;
               switch ((int)com.yiyiaddon.m.b.a<"sta2u25qw554s","E/nzYaP8SdOSYCLsvW9DzKn8TGpqOCZpP10mD3dVWoo=",172244430451402397,32503558745705830,-4230298188970885338,8812264106663950477>()) {
                  case 652599412:
                     return var6;
                  default:
                     throw null;
               }
            case POTION:
               List var5 = this.j.a().bB;
               switch ((int)com.yiyiaddon.m.b.a<"s1oh0v6ycptzkw","8RfAH+FQewzpQ0633fhKENe3nKgy8pMQhmhiE++fljk=",-3741706519332716400,4524139023103140951,-4958306820096563201,-8792986974161107847>()) {
                  case -329466499:
                     return var5;
                  default:
                     throw null;
               }
            case WATERING_CAN:
               List var4 = this.j.a().bC;
               switch ((int)com.yiyiaddon.m.b.a<"s2kghgcd0uj36y","Ctz9lEQ9LiRCQkJfQf6/1+yZTQD2JsYzb4zbduln4ww=",-472201385176359741,-1698498150822010119,-2779648206292255060,-1546087095505217700>()) {
                  case 1077532483:
                     return var4;
                  default:
                     throw null;
               }
            case SPRINKLER:
               List var3 = this.j.a().bD;
               switch ((int)com.yiyiaddon.m.b.a<"s1w8kvuydelve0","GnhoV4Pw7R4WnPBpjS4mpZbAQW/vfllq+dlgwtK5OXU=",9019475318840534685,8853947442598073631,5375433613721977369,-4760289607030868509>()) {
                  case 1410355753:
                     return var3;
                  default:
                     throw null;
               }
            case SHELTER:
               List var10000 = this.j.a().bE;
               switch ((int)com.yiyiaddon.m.b.a<"smyjvk85jx54r","XwWK0Tl3pMRSYdSucBNTtJgdcJvVNyYN5DtJ0O7U1mM=",-67312695980246153,-8699155347441729362,3992876172669463291,-7642116685595651365>()) {
                  case 1480184966:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }

   private void f(String var1, boolean var2) {
      if (this.h != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27wdvs2kua0zc","f0MF3hzpM2OO+RtkE1HyY3vxKkMzFSTrk2UxGVCHhog=",996888141247394732,-8408289383946835792,9063209708184247153,-4967390915464545989>()) {
            case 199130319:
               com.yiyiaddon.e.n.i.a var4 = this.a(var1);
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s6rnmsv5q1bz8","/FTGu9HCj64e6SzELm0k1Q9bdqHWDrjzrzygnD9NUT4=",-2822171600987972677,1879002410396540836,-8168882286819114050,2571677646280184776>()) {
                     case -1499869536:
                        this.h.accept(var4);
                        switch ((int)com.yiyiaddon.m.b.a<"s2inlwgw0nrbsk","JrnWmLRavvpTlJH8kBVNrM7nHIGUKi/Dtsp1gaiCN8c=",-1017637076678312045,7453647238985178552,-3055661310415885373,-5201253578067966081>()) {
                           case -1538810403:
                              return;
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
      } else {
         List var3 = this.bc();
         if (var2) {
            label45:
            switch ((int)com.yiyiaddon.m.b.a<"s5ny30ztcs7n3","PMItnW+c5e4rFRmEGldXII9GlgNw0pkk34eJj2jXCkI=",7612560482741670734,3360283897339590337,8423777181650802623,-1270221676529225134>()) {
               case 1153273835:
                  if (this.d == com.yiyiaddon.e.n.o.d.POT) {
                     label42:
                     switch ((int)com.yiyiaddon.m.b.a<"s3plg0q0kbxkxz","/KP8GOxyEpSd1RyccaNmHB4AAiDKLcSQSBfjZvf9TRY=",-4559799572739461697,-4455936529806880816,-4696740615514341801,743021528379951013>()) {
                        case 469603654:
                           var3.removeIf(
                              var2x -> {
                                 if (!this.m(var2x, var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sx5qj16wmcjcs","X976b7kycs2w1BKW6F+NV+TScmbfX4b2hcPW45pjhP0=",7222411104331206284,-4162578297512738599,-7939321325826145873,-5707732284336964628>()) {
                                       case -1875442753:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1dss978r92exo","khnhyBa4IIcyDlzJ/hwGr7f1WNf4TxOR4ugVJ40gHvQ=",1798936122072544032,6483827327751007703,-4920779469936712912,7772320656170583204>()) {
                                             case -1306204215:
                                                return true;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    switch ((int)com.yiyiaddon.m.b.a<"s243iqquta9o12","D+7J1i/KC0eDA4ooI6QUBPKhQNoBhNBIbxJgYwySj1Y=",7711978879848714291,-5024873786894902176,1454285716332539326,91700485969855906>()) {
                                       case -404278312:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }
                              }
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s29ad8tjf5gq5d","Du0oHbgeXixXMAT8pWbIqTkkOW3U+mygdfJZvB1PAVE=",8672223533717507928,-427038519082875917,6132145586438266910,1656536501384922008>()) {
                              case 216296974:
                                 break label42;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (!var3.contains(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vf1zxw21aktc","NZu94GifRAuwmFL0eFd0c/hhGGe/mRlgA7JVHqJxdBY=",-5629396747313654412,-7463063376823574542,-7515883780706704233,-7970869289456340377>()) {
                        case -307642528:
                           var3.add(var1);
                           switch ((int)com.yiyiaddon.m.b.a<"s2wt9rouzc5dqy","Drl1YATW08N/vnwlolDD8aMRVLE6SImXvSKrcalraws=",-8886804388919048254,1878372413540443033,-7207228841113781090,-2847681861081147088>()) {
                              case -268570713:
                                 break label45;
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
         } else {
            var3.remove(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sy7s73kfjz1wz","QKEJcUWH7rf6csvL+dY6u2INM9CZShoCu12gyR77XkI=",-26400545789035360,4219702736496474608,-212792215395567879,-2329217245294836512>()) {
               case -297115685:
                  break;
               default:
                  throw null;
            }
         }

         this.I();
         this.u();
      }
   }

   private boolean m(String var1, String var2) {
      if (this.b(var1) == this.b(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"splamr0kf5yb4","jpjQ6LsiGmbJJb2KSis12khJs+ClMZh+3a166chdISU=",1397846593549900855,-2518612174944523021,-29331121425667347,57469559897556750>()) {
            case 858209035:
               switch ((int)com.yiyiaddon.m.b.a<"s7sx20cwwzz15","/tRCegr9qic5RjPmnlBWSrq0wL9KTUVJol2AaTzpohU=",-1188567938752815472,3522397405372755893,-8706605949215202883,3569857183979676510>()) {
                  case -722736261:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1739a7t2wx44f","MmSpOJfG+jUq/aII8xAp7jYYS/Kju5zIS3a/BzM0Hhk=",5803411952903244141,2963431476809767444,3125376062521059037,-3009608618301084869>()) {
            case 2096489359:
               return false;
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.e.n.j.e b(String var1) {
      if (this.j.a() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lb36mzywo2qj","Oel9iXUIa7Za7IaVTN56OnfUQcHkKLZoGMaCEl6iRR4=",-9120901052909698040,-6051210881462571132,1263202413404137398,4302158902773598217>()) {
            case 332417976:
               com.yiyiaddon.e.n.j.e var10000 = com.yiyiaddon.e.n.j.e.NORMAL;
               switch ((int)com.yiyiaddon.m.b.a<"s2ba0p1nxbh9zu","JTbaWNvTqGDq+YiBHtHIFi5vVNrVT/MuyqKICXvSoWs=",-8391339880402356833,-2720384065253548469,-9164527870272184874,-7666743219467737812>()) {
                  case 266271547:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.j.e var2 = this.j.a().b(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s18orkrtmzcs3a","HAjp5EaUkAJe5TyA8Ue2e44NJqdN1C+u1GTYpSOaWTk=",-3233190474873214735,-4233797047843957707,-1019293053590034261,-5642358552840392182>()) {
            case -874753850:
               return var2;
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.e.n.i.a a(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33io83onybg1v","4LqylwMobH0GUsTVfI69m/lj7dwIovQfgNhK2XQ5YLs=",7521075060461787485,2372708795021695272,-5393888765392472748,-5429012199804719569>()) {
            case 357221703:
               if (this.j.a() != null) {
                  Iterator var2 = this.j.a().aV().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1oohh5yvaczjj","TMqnEsZvpusNUrRijSQJFnlVsj8qbPLusbcXpjHNE2s=",7118675527074621064,7613319386927047828,8845293090952366690,-2110467124062949737>()) {
                     case -1677642873:
                        while (var2.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1li08j7alpmf4","DsOIYbEGirQxEFkk6PaFbkl5lAg1qcEvGxStFqkw4HY=",5589523869461986541,-6781138984299578740,-4275010795502715711,3485528039379676447>()) {
                              case -1070113192:
                                 com.yiyiaddon.e.n.i.a var3 = (com.yiyiaddon.e.n.i.a)var2.next();
                                 if (var1.equals(var3.dk())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s26t8lcz29ztxq","EBZ0ugwH4GKsNlXCvYrdyNR9rkQrgRaZpQLsNx2a8DU=",8091800177162983763,6678044178571994902,-6262245424347761426,3351529046926905502>()) {
                                       case -1769454822:
                                          return var3;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1xtft6zqngmcv","jnnFxOlxWyFyFwRtyY49An1a1U93LVPl701Glttyp9I=",2936276646065260244,-462229336154604951,7686117087254050237,-7865694571889244446>()) {
                                    case -747674175:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2c83t8gjq5jgn","WmVK1DcUN92bZIBiKrtW9yFNZ4J9C4sDweUG3Ygb0Nw=",748368649760325090,761143689659624072,2757728658067473767,8005984347049976779>()) {
                     case 1326225344:
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

   private void I() {
      this.j.a(this.d).I();
   }

   private void b(String var1, float var2, float var3) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2u6qvcc4kuauu","KjfwjVXvj8mWU76xtObVYbwFnDpnmvXZp6BES9W950Q=",-1754009304418540571,-1243923615121547371,6189138628795359309,-6633675554728260092>()) {
            case 645342163:
               if (!var1.isEmpty()) {
                  this.vh = var1;
                  this.m = var2;
                  this.n = var3;
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2je20nioms4k2","AR+SRShntKIk99DiJiR6dC8pbHr+Ln7FyT6Fq6Btgww=",-2717389589954423624,3636483831519915185,6475918494028143098,-2244475183732766068>()) {
                     case -515978724:
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

   private void a(Canvas var1, float var2) {
      String var3 = this.vh;
      this.vh = null;
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s36jhciwjm6w4s","+r3AO1xDCfWmmdPZrbawdWz68vsRTMdtgZTd2tsLYsU=",-462536736872381221,3502025847685336229,-6853665731475927531,-582928708986451114>()) {
            case -584333665:
               if (!var3.isEmpty()) {
                  com.yiyiaddon.l.i.c var4 = com.yiyiaddon.l.i.c.a();
                  String[] var5 = var3.split(
                     (String)com.yiyiaddon.m.b.a<"s2jw57ms3n46cc","w3h6JVMC2b0HcmT6i9qbJ5fmNv4oQ/yb6ue2VyBh",-3177973408211303630,-1036398388523514252,-8394092084860810863,-299834806234434768>(),
                     -1
                  );
                  float var6 = 0.0F;
                  String[] var7 = var5;
                  int var8 = var7.length;
                  int var9 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s22erpllgn91ft","G66MgziArtmakDh+PVSuVbMvjUC60XvMTf2TrmndteY=",-54212807124590678,-7093026782962766094,3309058901797026180,2730904448068960637>()) {
                     case 947388169:
                        while (var9 < var8) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3kerip9wu3rdz","nberFdhlGQivQHfzZrTcBEPxWv39FRz45w6SEB8ilmo=",-8439066570488456759,543348312760844339,7450071617371601798,-2886564068442378058>()) {
                              case -1144555622:
                                 String var10 = var7[var9];
                                 var6 = Math.max(var6, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                 var9++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1gkwkc0szr6np","xJoQvvqAqM7x72tEWi9yYLm6Y7yAxOHksdZZBgwjYsI=",-361383230160230466,-4366126735788038059,1017547310868676765,-7811168738575393454>()) {
                                    case -81531458:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var6 += 12.0F;
                        float var15 = var5.length * 12.0F + 12.0F;
                        com.yiyiaddon.l.b.j.d(var1, this.m, this.n, var6, var15, 6.0F, var4.uY, var2, 0.9F);
                        com.yiyiaddon.l.b.j.a(var1, this.m, this.n, var6, var15, 6.0F, var4.uN, 0.94F, var2);
                        com.yiyiaddon.l.b.j.c(var1, this.m, this.n, var6, var15, 6.0F, var4.uX, var2, 0.22F);
                        float var16 = this.n + 6.0F;
                        var9 = cS();
                        String[] var18 = var5;
                        int var11 = var18.length;
                        int var12 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s243e8lewqporp","6I04NesiPz6dBsx0sSrXofKe3xT8sJpp6Np+E7xo1yY=",3638312574694399522,-2722757176645006432,116084253147395008,8764141417921438495>()) {
                           case 1710069810:
                              while (var12 < var11) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3lkubhtk698pv","OQ85KAupOZzttbX5F6C38Fl2qPPk/kH7BC4wvb7NbnM=",-167906726029792417,-1916580087024173573,4373147767524542302,-4205569749355263900>()) {
                                    case -483679309:
                                       String var13 = var18[var12];
                                       com.yiyiaddon.l.g.d.a(var1, var13, this.m + 6.0F, var16 + 10.0F, 10.0F, var9, var2);
                                       var16 += 12.0F;
                                       var12++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1mhwfhwstyaov","WvIMNN+gczFCZv9Iwq7F3Y81KJHihnSy0DW+2oB5y9Y=",-5309875052113260234,-7776056564007553835,159587053311068671,4695601946057126999>()) {
                                          case -1200020090:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s109xdy4hrtb14","jfktbuKofBVDdCY7bzG7XHhvuCZNaK1yXZwlSC1v2Fk=",-2832632388411887332,6383399613119679659,3414894652239044352,737012564828341852>()) {
                     case -142327677:
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

   private final class a implements com.yiyiaddon.l.b.g {
      private i b = new i(6.0F);
      private i c = new i(6.0F);

      private i a() {
         return this.b;
      }

      private i b() {
         return this.c;
      }

      private void f() {
         this.b = new i(6.0F);
         this.c = new i(6.0F);
      }

      @Override
      public float b() {
         return Math.max(48.0F, Math.max(this.b.b(), this.c.b()));
      }

      @Override
      public void a(float var1) {
         this.b.a(var1);
         this.c.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         float var8 = (var4 - 12.0F) * 0.5F;
         if (var8 <= 0.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s37h23h132nxao","MzJ370a/Hx3TNbD7XmBX85kZLOspA/VshEp3fLfqZCQ=",-5098253383368669413,5288838054541484014,-2317101337035686747,-2955770169227226183>()) {
               case -622384859:
                  return;
               default:
                  throw null;
            }
         } else {
            this.b.a(var1, var2, var3, var8, var5, -Float.MAX_VALUE, Float.MAX_VALUE, var6, var7);
            this.c.a(var1, var2 + var8 + 12.0F, var3, var8, var5, -Float.MAX_VALUE, Float.MAX_VALUE, var6, var7);
            h.this.a(var1, var5);
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         float var7 = (var5 - 12.0F) * 0.5F;
         if (var7 <= 0.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s2j35akq8fl4h7","iokWiNB3RHjrm9dmimW9NefBLK0RnxfIhhjawnAcXDA=",8203719738079494620,-70156073020900223,-2535327224615038583,-8072290198642733285>()) {
               case 537253482:
                  return false;
               default:
                  throw null;
            }
         } else if (var1 <= var3 + var7) {
            switch ((int)com.yiyiaddon.m.b.a<"s2o580950hjd89","eQeCHqKAN9hTzuW3onUXeOM3OgX9bZnfL6H1F4RMcDk=",8339423971279739619,-4828826861335261347,4460059589739545919,2583869760764867887>()) {
               case 203715320:
                  return this.b.a(var1, var2, var3, var4, var7, Float.MAX_VALUE, var6);
               default:
                  throw null;
            }
         } else {
            return this.c.a(var1, var2, var3 + var7 + 12.0F, var4, var7, Float.MAX_VALUE, var6);
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }

      @Override
      public void F() {
         this.b.F();
         this.c.F();
      }
   }

   private final class b implements com.yiyiaddon.l.b.g {
      private final String vi;
      private final String vj;

      private b(String var2, String var3) {
         this.vi = var2;
         this.vj = var3;
      }

      @Override
      public float b() {
         return 20.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.g.d.a(var1, this.vi, var2 + 6.0F, com.yiyiaddon.l.b.d.c(var3 + 10.0F, 10.0F), 10.0F, com.yiyiaddon.e.n.s.h.cS(), var5);
         if (var6 >= var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s1s6he8swwf9zb","Ll/QAK7tq6WTm+CU2jXOC4XmBxYzugGhx40NOgulUt4=",-5754087887425127012,8008795538388185195,-2229903605477173555,1775650721535056088>()) {
               case 68050174:
                  if (var6 <= var2 + var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3s2fheygsipqq","31ck0DiivIYWwmarNGpQqXS1fUrTq/F9mGSU6HpgbGA=",5514201096231412499,-5037295635648306891,-4946793632838724815,-2765128219262907610>()) {
                        case 1340537900:
                           if (var7 >= var3) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2812pyd4cfb92","fhX1fDYsSwL+J5zi/mByzF+bnJ+q1TSvm8ipURHT6sI=",-5422038689583846864,-787149832062817801,2174114365993417504,-6784888141795838761>()) {
                                 case -1636181671:
                                    if (var7 <= var3 + 20.0F) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2maun6ymbct6j","Z0EfNJBuf4IaU1I09PVvEU9W5deGgYL79jUM2Ji8MHs=",-2654549246924126341,-401558695581356985,-7411510061548733173,-8895532799380195808>()) {
                                          case 680882426:
                                             h.this.b(this.vj, var6 + 14.0F, var7 + 16.0F);
                                             switch ((int)com.yiyiaddon.m.b.a<"sumpxsfvj6p4a","f54InkgvXoVjaEzQnVqWSTExc9FpJycky6icZ8cX6Yo=",7161925993496725341,2057327746789662889,5257808389860084616,-6732061700501767503>()) {
                                                case 1227553616:
                                                   return;
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

   private final class c implements com.yiyiaddon.l.b.g {
      private final String vk;
      private final String vl;
      private final String vm;
      private final String vn;
      private final String vo;
      private final String vp;
      private final String vq;
      private final boolean ei;
      private final com.yiyiaddon.l.j.b b;
      private boolean eh;
      private float cD;

      private c(String var2, String var3, String var4, String var5, String var6, String var7, String var8, boolean var9) {
         this.vk = var2;
         this.vl = var3;
         this.vm = var4;
         this.vn = var5;
         this.vo = var6;
         this.vp = var7;
         this.vq = var8;
         this.ei = var9;
         this.b = new com.yiyiaddon.l.j.b(
            h.this.h == null && var9
               ? (String)com.yiyiaddon.m.b.a<"sux7xgxjqkkbl","pZTdK3wdBc0LLiOjYq/ohcJmlxgDy30PS1CJ7gwv",2478900912624857007,-7897524081190752507,-8732628755550653628,-1788867963542283129>()
               : (String)com.yiyiaddon.m.b.a<"sj33zf2rdzgl0","OFlmR5IGGKXpydJ1Gba36OmWqD42aJmbtiRUoAfT",-90384482228104831,3716352297472573240,6618197930506181249,-119903957994474820>(),
            () -> {
               boolean var10002;
               if (!var9) {
                  label15:
                  switch ((int)com.yiyiaddon.m.b.a<"s1r7c906j7e6fy","OLUZ+VRCthqq5cIOl6u246eS3ySyOVuFkDxsvZRvMmk=",-6514674901907768590,1299900377348575778,-6065642101473032748,3666671124649765972>()) {
                     case 182031434:
                        var10002 = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s2lbgzieec5gd1","EM6c+orPX2rneogHJeYKJ4fPe8C+tb5ASvMa4AmhwoI=",-8363213666300294350,-400400859888871281,8620843385829598374,-4945585191677732919>()) {
                           case -951692653:
                              break label15;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = false;
                  switch ((int)com.yiyiaddon.m.b.a<"s3d825x794scio","JLIkGAzvjTNrINFgH88q5MrGBv6+4MEceBjUBHlkMTA=",-6341816406288655316,-5005317350171760115,2810062273314633381,-7649508384114229252>()) {
                     case 1104612377:
                        break;
                     default:
                        throw null;
                  }
               }

               h.this.f(var8, var10002);
            }
         );
      }

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
         this.b.a(var1);
         float var10001 = this.cD;
         float var10002;
         if (this.eh) {
            label15:
            switch ((int)com.yiyiaddon.m.b.a<"s2lasw2576u9wn","MQTofjWNko91GAwzQnDADY9pc2IHZdMKzoO7LZz2toE=",4578226577070424204,4694681604151900874,-7262772935376324482,2278779826826648512>()) {
               case -1652390721:
                  var10002 = 1.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s1klhc2c924zxs","/d8u9s5BHbVRamj095jpR+aIleqcyMDmBdbHlHh0aKk=",-3993133065938425770,9067982720521902028,-7072212339845382904,4977265671852329045>()) {
                     case -2105327976:
                        break label15;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"sg7693jsvhmxe","cX5QR1N/I63dNutR0JfGsR6W4qrg8zgywkusaWh3BMI=",7869306104201770189,-3040696062278783647,4892804451609640955,-3924069300165797274>()) {
               case 318924290:
                  break;
               default:
                  throw null;
            }
         }

         this.cD = var10001 + (var10002 - this.cD) * Math.min(1.0F, Math.max(0.0F, var1) * 12.0F);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.i.c var8;
         float var9;
         float var10;
         boolean var10001;
         label95: {
            var8 = com.yiyiaddon.l.i.c.a();
            var9 = com.yiyiaddon.l.b.j.h(36.0F);
            var10 = com.yiyiaddon.l.i.c.y(var5);
            com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 36.0F, var9, var8.uQ, 0.7F, var10);
            com.yiyiaddon.l.b.j.c(var1, var2, var3, var4, 36.0F, var9, var8.uX, var5, 0.1F);
            if (var6 >= var2) {
               switch ((int)com.yiyiaddon.m.b.a<"sep77n9x0dhsi","5D9qM4ciUBayDNyeMTFlQjLAIYJv/FgSc24ubjn6oOA=",-3102444958606512978,-2545174267687376485,-8931741958908498671,-9120919195114145815>()) {
                  case -2088668788:
                     if (var6 <= var2 + var4) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1o80p4s6ox6yd","w851pbmaAwjxGxI74RMjru8ObGANgpkePavFPRamm1I=",-9066243814155597360,4579520046943265433,7235239798900995055,4769835543376611434>()) {
                           case -1940902279:
                              if (var7 >= var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s6czyvt532vx","3t11lk2umMwRZ5L6GNfWu/5KLyeI9/T9U30ndPYvHEc=",-9020585732066020301,4843711165121141673,2944208979523590741,-7809415770047040015>()) {
                                    case -1006918844:
                                       if (var7 <= var3 + 36.0F) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2pfzzg09uzu9k","aQnmS6B1eN8WwuCBqVv9LoeBXPYj1OmQ3YTv+U85nU8=",-3898557705744194029,5988521146159873727,-854902159630627538,-3193032065118488688>()) {
                                             case 1933182176:
                                                var10001 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s1p90psxl9x68v","8Vo2hj7AbUw2+9proL4r86aFOuO/CFjQBL9fmDmxZso=",1155980018434876965,6869430168536210423,8414071300655150720,457793062331259149>()) {
                                                   case 739276623:
                                                      break label95;
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
            switch ((int)com.yiyiaddon.m.b.a<"s3k9fujh0ha4dw","/Ql127MeyBrPj6/rOV7bGnrOnaZxZjnqUJ+1iHgDLPk=",6718909040106760424,2627477909533456926,2987186119142130666,-6200533900646864512>()) {
               case 581547833:
                  break;
               default:
                  throw null;
            }
         }

         this.eh = var10001;
         if (this.cD > 0.01F) {
            label73:
            switch ((int)com.yiyiaddon.m.b.a<"stg0oafu9fl9h","utLzDEnQXWUfUT/aZJmYfhP6TJTJqLBl/SbQ9FdUFTo=",-3801420650686118638,9011009516450570208,7597771777394585157,7430876196565250491>()) {
               case 1964444044:
                  com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 36.0F, var9, var8.vc, var10 * this.cD);
                  switch ((int)com.yiyiaddon.m.b.a<"s1xyc6j4iuked2","vwplxZ+DfW2z+LPxl3pVtIqhOcnf7x8bvCsMCFCGAo4=",7558908859691857473,5916429932807317469,-6293384579044635155,5882863753048802573>()) {
                     case 1153567172:
                        break label73;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         float var11 = var3 + 18.0F;
         float[] var12 = this.a(var2, var4);
         float var13 = var12[0];
         float var14 = var12[1];
         float var15 = var12[2];
         float var16 = var12[3];
         float var17 = var12[4];
         float var18 = var12[5];
         float var19 = var12[6];
         float var20 = var12[7];
         if (this.vk != null) {
            label68:
            switch ((int)com.yiyiaddon.m.b.a<"s3t7v7qgepbn6n","+35IzCANnerhIuFoef4TYB2JeslTPnPr+VTVR/XlX1Y=",-78684547373987274,-2002312159710402396,-1437634025315606200,-7389817357734044074>()) {
               case 1721987351:
                  this.a(var1, this.vk, var13, var11, var5, var6, var7, var3);
                  switch ((int)com.yiyiaddon.m.b.a<"s2xsyev4h1zelq","EMQq2gpcd2iBCJ8Kbpn0Fp7rJNQRVtvEP37CGCJPe7E=",-7315235958976917939,3301765645575503660,-5924999939135466871,5864865969417343876>()) {
                     case -944592209:
                        break label68;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(
            var1,
            this.vl,
            (String)com.yiyiaddon.m.b.a<"s3erjidkcle5ab","oGxNUXQQJztlh6IirztUELPOQNTwjOKD7oPWy66Jozg=",8863090154432740317,-9182193139968414366,8463672107214419940,-5763983351934451320>(),
            com.yiyiaddon.e.n.s.h.cS(),
            var14,
            var15,
            var11,
            var5,
            var6,
            var7,
            var3,
            this.vm
         );
         if (this.vn != null) {
            label63:
            switch ((int)com.yiyiaddon.m.b.a<"s1rloyabbggm5r","nSf9/WOaGaydy/jDx/CR/gwRZECx9H58chSHGXivZQY=",-1966432795269665631,987799603470552311,6450704214510717449,-8396515042137645763>()) {
               case -137474553:
                  this.a(var1, this.vn, var16, var11, var5, var6, var7, var3);
                  switch ((int)com.yiyiaddon.m.b.a<"s1ugrqm5nw5mfc","ybUfOowq9Sxcnh//GooePRTG8clZaw3nQV6csAqTpQ0=",-8489116377687606472,-8208219297682618515,1906141830980875373,-118886763906162367>()) {
                     case 159611624:
                        break label63;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.vo != null) {
            label58:
            switch ((int)com.yiyiaddon.m.b.a<"s1ywav17puq3xl","t3TAbJ0MLr4Ho1tPJzL2P2vB1wbsoYyJqM7g67xGUVk=",2284262623833200485,-4725225016344641336,-7400537217591460052,2182030587475518500>()) {
               case -794327723:
                  this.a(
                     var1,
                     this.vo,
                     (String)com.yiyiaddon.m.b.a<"s38ck5zuz9gko","sBlpEBmNf8V8zbjygd0ZMipIc75jlTmzJ9Re4CrKCK4=",9087111856642994335,3843243360520639955,6321121044311081316,-5033587230173768280>(),
                     11184810,
                     var17,
                     var18,
                     var11,
                     var5,
                     var6,
                     var7,
                     var3,
                     this.vp
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s2potphmbfmnor","m3ODUWa2Ao2mNw7QFxQUcbWwphCxoNXMvr2+JbGD7rE=",3915186060098526175,-5874255289696848354,77963019724241471,6252311138171000167>()) {
                     case 1617032460:
                        break label58;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String var22;
         if (this.ei) {
            label51:
            switch ((int)com.yiyiaddon.m.b.a<"s1q81fgqnk4vg8","UO+ctnL6UpMXGhTeybykHMrO+n2OgY0MdzCFTmQsMYA=",-8974209959657027602,3283343620285241481,-4200460492424840824,-6384839319634603906>()) {
               case 1798315980:
                  var22 = (String)com.yiyiaddon.m.b.a<"s3g4xogt6exu0v","C/wj8ei7jK1xTA4YxzoZ+OiGEEtgtZ3Fep/5po/Sof4TcrAJYJ0=",-7975460964196139455,3809412993470760108,1804142849961779149,-8524737778861546209>();
                  switch ((int)com.yiyiaddon.m.b.a<"sc8ccuqxrtj2v","frVkiL1PwEAwohAsiS7PmSWond4fSl61SIqdZdjRE8g=",-5488446698231044400,1558819107410990095,7595894629880585921,3782008474969908697>()) {
                     case 1967853203:
                        break label51;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var22 = (String)com.yiyiaddon.m.b.a<"s24kx0try5h3i","aK5LkbqoMyqKkuKIhLic3HsFUSveq/cKetMMzUgJZFGWP2EUm2s=",-6618190601544749708,-6722442640655530724,-1315949143562334926,-2146682813174688479>();
            switch ((int)com.yiyiaddon.m.b.a<"s1moiebmqwpz26","5qcw+XZgQ23LQuO1uC5gTuQwpHLPVgH5X49Duw44zXs=",-4405553755314844588,-773166827367016603,3981074605643976787,-2744335438792097026>()) {
               case -805544898:
                  break;
               default:
                  throw null;
            }
         }

         com.yiyiaddon.l.g.d.a(var1, var22, var19, com.yiyiaddon.l.b.d.c(var11, 10.0F), 10.0F, com.yiyiaddon.e.n.s.h.cS(), var5);
         float var21 = var11 - 12.0F;
         this.b.a(var6, var7, var20, var21, 24.0F);
         this.b.b(var1, var20, var21, var5);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (var6 == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1lzfvxscqhwm9","U40/p3XRLIQlhToiorAyfTCxh3HweC0S0ux2bf3tzMo=",-2604800714368308125,1390239973231034093,-7265490628249962246,4562450066963881482>()) {
               case 461840354:
                  if (!(var2 < var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3cqqww8min8j1","EkYhfQh62gvR9WU56BKwh6A+nDH/rCfb3db8GwmjZgQ=",1501989998587776166,-2339637500301093533,2752790503207462757,-6842207711687696986>()) {
                        case 1600025249:
                           if (!(var2 > var4 + 36.0F)) {
                              float[] var7 = this.a(var3, var5);
                              float var8 = var7[7];
                              return this.b.a(var1, var2, var8, var4 + 6.0F, var6);
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s31529pxncrgi9","YTQ9+F+K52Y6EnA4GICxjtqilc1cl2HGXHoyHx/cTGA=",1944411563579292568,1267630733573460648,-2148145494712948508,3617600058451171567>()) {
                              case -1656388677:
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

         return false;
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }

      private float[] a(float var1, float var2) {
         float var3 = Math.max(
               com.yiyiaddon.l.g.d.a(
                  (String)com.yiyiaddon.m.b.a<"s37gjz0a31ew9z","WrMhbWAY4Fjwaj+C3d1fEBweI/ih51gQOd/EWAOAxeEyRQ==",3821147092370508480,1507304510187712738,-8442110870267619883,6806668836740855410>(),
                  10.0F,
                  false
               ),
               com.yiyiaddon.l.g.d.a(
                  (String)com.yiyiaddon.m.b.a<"s3vhah48qtrp4y","ZpIlU8VQ4vDfhnuf7N2xxwDmHgVgcweuksB0VizXjMKopA==",4744864698528441471,5877109149066482099,-6982467423750234048,6008214757643997680>(),
                  10.0F,
                  false
               )
            )
            + 10.0F;
         float var4 = 122.0F + var3 + 24.0F;
         float var5 = Math.max(0.0F, var2 - var4);
         float var6 = Math.max(56.0F, h.this.dj + 12.0F);
         float var7 = Math.max(48.0F, h.this.dk + 12.0F);
         float var8 = var6 + var7;
         if (var8 > var5) {
            switch ((int)com.yiyiaddon.m.b.a<"s1ucpz1f9fmvie","N5np4ghRqNozHCgqQ4cDcEPbiEtxIo4VScbsNYy6XAw=",8020584251908695056,4838349019685712205,-2540116615322689242,-4465662643413313641>()) {
               case -867579947:
                  if (var8 > 0.0F) {
                     label16:
                     switch ((int)com.yiyiaddon.m.b.a<"s11yepwar43rmo","hh6p+u40W+o5V7B/3wMcFpuOGqXWNncBsAiIFDFOzyk=",5956810603855454339,-7566367144241289902,-7953864572677308837,4702233244463156228>()) {
                        case -462189667:
                           float var9 = var5 / var8;
                           var6 *= var9;
                           var7 *= var9;
                           switch ((int)com.yiyiaddon.m.b.a<"s3l4suqxj2p27k","vTd9nfor3fov/ZYkYcReqtGXC9XqSdrpsHHlpEKeiHQ=",-1929980925364970145,4884937301278159415,-5811078079041735775,-1949297573989735347>()) {
                              case -2026251356:
                                 break label16;
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

         float var15 = var1 + 12.0F;
         float var10 = var15 + 24.0F + 10.0F;
         float var11 = var10 + var6 + 10.0F;
         float var12 = var11 + 24.0F + 10.0F;
         float var13 = var12 + var7 + 10.0F;
         float var14 = var1 + var2 - 12.0F - 24.0F;
         return new float[]{var15, var10, var6, var11, var12, var7, var13, var14};
      }

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         ItemStack var9 = com.yiyiaddon.e.n.o.a.c(var2);
         if (var9.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"smhl0lj57g0ik","L1Tkj9rH47E9miKLvApq84/TaTErGsoAPAx510p8luw=",6865069175850051122,8370094475341214877,-1433051343742635638,-3651059844588142889>()) {
               case 395558554:
                  com.yiyiaddon.l.g.d.a(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"s35kklol79idq4","u6JYkE54Q2x/j5y9m3nW4U0XGFnkiT/WdBKdxEz3WWOUlhMqMPE=",6237162867776630091,-4137833506643497657,4707990250458278224,8457465075495203339>(),
                     var3,
                     com.yiyiaddon.l.b.d.c(var4, 10.0F),
                     10.0F,
                     com.yiyiaddon.e.n.s.h.cS(),
                     var5
                  );
                  if (this.b(var6, var7, var3, 24.0F, var8)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s8kj0xlpb95z5","Yo8PBQ12qKd6ODnOs1R+j/evDqKQ5Ss0cLCrjVsdIYo=",6171256893464590800,-6560184426542891432,8603937066393647294,7741053573317306895>()) {
                        case -1802746998:
                           h.this.b(com.yiyiaddon.e.n.o.a.aS(var2) + "", var6 + 14.0F, var7 + 16.0F);
                           switch ((int)com.yiyiaddon.m.b.a<"s2vkp0f82q7hpp","u5LFhatUdQK1eHfDMBTkrJ/Ay+kMCFsYYlNNcsV/3Ps=",3861136553830447259,-8301100896650533858,2367918031288989583,-5934098090768090108>()) {
                              case 284867107:
                                 return;
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
         } else {
            com.yiyiaddon.l.g.c.a().a(var1, var9, var3, var4 - 12.0F, 24.0F);
         }
      }

      private void a(
         Canvas var1, String var2, String var3, int var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, String var12
      ) {
         String var13 = com.yiyiaddon.l.b.d.a(var2, Math.max(0.0F, var6), 11.0F);
         com.yiyiaddon.l.g.d.a(var1, var3 + var13, var5, com.yiyiaddon.l.b.d.c(var7, 11.0F), 11.0F, var4, var8);
         if (var12 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"se7boyblcox37","o0n/kTNhqBrLZEaeTKEIDGaDjYZdbX5m0Tlz3Tn2ULs=",-1322069254034628689,-7170036833420814513,5340869878790482802,-5628119728509707693>()) {
               case 1756987825:
                  if (this.b(var9, var10, var5, var6, var11)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pl21qj8t43az","azsx01wOf8TsbY25a4aZ0J/3MFd5EYpIwA15zDp5GkI=",-6937195833371550348,4537382109655200528,-6479616140774379893,8485769376968665715>()) {
                        case 1389308563:
                           h.this.b(var12, var9 + 14.0F, var10 + 16.0F);
                           switch ((int)com.yiyiaddon.m.b.a<"s1ajbg5sryqpm8","yEzBG2iJ2k/l3UR2G7zL/SjbAfMeuFZboFKoxp+el2U=",7092959741509696778,7575406828020261651,944995733659378305,-8403749419391917282>()) {
                              case -228738676:
                                 return;
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
      }

      private boolean b(float var1, float var2, float var3, float var4, float var5) {
         if (var1 >= var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s22vfziga24r3z","4jsMnI5nK9BrarA1MY18wylD4Mz9793CsFp2ISOVhhA=",-8314599265406099453,2242428047063964314,4459940892500631720,-4713973742528748306>()) {
               case -1659409231:
                  if (var1 <= var3 + var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s216r5irz6v571","+3W2Ofr2tisJaVu3bKa+UoB47ULodJb6RdH2xDhMLaA=",-6947408378870471940,5085902885701307215,-3191428872258260994,-8188742994903691799>()) {
                        case -1577539332:
                           if (var2 >= var5) {
                              switch ((int)com.yiyiaddon.m.b.a<"s12mudy83yogxx","9L5XI9QGPdKaTQcQwvNIhdgrvwjDAOoiy3Q04D6A8f0=",6016902609060436059,-6961413600480751570,-1168611954624912576,-7773780403363065839>()) {
                                 case -1026294519:
                                    if (var2 <= var5 + 36.0F) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3iyo5tltj6eks","OSjF8YloVbYR8ONebZEGIqeOaHu4Edi3V6G652MDSNc=",3180626977213681696,6883101999334095843,1785596887948924427,-6414189215061407796>()) {
                                          case -1886081128:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2n7cfw72438jk","6EbcBMXnc2zIB6qU98F++YSWU+U4PnZn1/D1bny9vmw=",67275200558061330,1747523642520575692,-443955081931565421,8078614607576620569>()) {
                                                case 1786591147:
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
                  break;
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s2f22c8fkyt4bp","+arJSHtPPxHXh2GT0DQOo0ISDVJCelfRK5v2+9jvizo=",-6898810804512857813,-5948830929760861361,-1592766534263594554,7764236111630837717>()) {
            case 550727518:
               return false;
            default:
               throw null;
         }
      }
   }
}
