package com.yiyiaddon.e.l;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.a.c;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.m.b;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String pp = "autoreconnect";
   private static final String pq = (String)com.yiyiaddon.m.b.a<"s3mgw4cvvaa909","eyQzkslyncZnrvRt+UVPrR61Vlv6q/mlpF8f0HDlPUgOihGF",-8784195884276524739,-6925404932798080991,-6264999306569243332,-1496655631800208578>();
   private static final int lj = 10;
   private static final int lk = 20;
   private final Minecraft T = Minecraft.getInstance();
   private final com.yiyiaddon.e.l.a.a a = new com.yiyiaddon.e.l.a.a();
   private final com.yiyiaddon.k.d.a b;
   private com.yiyiaddon.e.l.b.a a = com.yiyiaddon.e.l.b.a.IDLE;
   private boolean dh;
   private int ll;
   private int lm = -1;
   private static final String pr = (String)com.yiyiaddon.m.b.a<"sskedtn4tqq7n","mPNIBFWth959VQZOzcVtWhHoLBxup/xDQKzJp9jH",3727302581251127079,-280479455009631071,8652387233752018411,-1581415701617917118>();

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s2wura9kimsq20","h+8oedTD+qM+yvvaRILAYgFlbWME5FOZhDLbOYROZJk47r2EhI2mv/rzqMtnhOYrHiMnsnH2",-949805326917427194,8164309362325883321,-6406463341500377756,3678252816250536100>(),
         (String)com.yiyiaddon.m.b.a<"s3mgw4cvvaa909","eyQzkslyncZnrvRt+UVPrR61Vlv6q/mlpF8f0HDlPUgOihGF",-8784195884276524739,-6925404932798080991,-6264999306569243332,-1496655631800208578>(),
         (String)com.yiyiaddon.m.b.a<"s1zu0xkj64vzjc","Vmiye/J0VlYpDpVg6ydVdV9HEQ0JeQ90IsxE407rlLbwWnHtr0ijGTvI",4667058384570615580,2552814923286375711,3439802371170745292,7156178399348000070>(),
         (String)com.yiyiaddon.m.b.a<"s1fylxhnbvsbyj","QpYYlhlem915RKvEWbg+6FGEDsYzA0808unF9IUQkwDnyAu4UlE8IvEAfQT7UHJCcOwzvk3v1UA6MRlY17M5JYjvw8kpZadxG5FRLrbFzv2sPLuyTJ8JAB5xhumPZ3f6Gr49+7hv",-2582095005810731502,-4439088457570112353,8410668111761558437,5531949478933030796>()
      );
      this.b = new com.yiyiaddon.k.d.a(this.T, new com.yiyiaddon.k.d.a.a() {
         @Override
         public boolean ar() {
            return a.this.a.m;
         }

         @Override
         public int D() {
            return a.this.a.cu;
         }

         @Override
         public int E() {
            return a.this.a.lr;
         }

         @Override
         public boolean as() {
            return a.this.a.di;
         }
      }, this::aJ);
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"sskedtn4tqq7n","mPNIBFWth959VQZOzcVtWhHoLBxup/xDQKzJp9jH",3727302581251127079,-280479455009631071,8652387233752018411,-1581415701617917118>();
   }

   @Override
   public int i() {
      return 30;
   }

   @Override
   protected boolean i() {
      return true;
   }

   public com.yiyiaddon.e.l.a.a a() {
      return this.a;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      e.d(this);
   }

   public com.yiyiaddon.k.d.a a() {
      return this.b;
   }

   public com.yiyiaddon.e.l.b.a a() {
      return this.a;
   }

   public String aq() {
      if (!this.a.m) {
         switch ((int)com.yiyiaddon.m.b.a<"stbsq61ssydke","Hhf8zedr9XToBWSdn5fa2f5h+NEd81drNv9jwJY08c0=",-6296599468786426862,-3805370270764848566,-5398546943999492449,-4704423827987332670>()) {
            case 110806078:
               return (String)com.yiyiaddon.m.b.a<"s2gy777b56in97","jLB/Yn4DCBj38sA60Yk8huCP/FWSk6vykWDLyCtDYaXu/EmN8wk+wHR/kNDFwoINxKpmEGgc",-1865183307889630485,-4710439934414515768,-7185984491940142045,-2331103584653377632>();
            default:
               throw null;
         }
      } else {
         if (this.a == com.yiyiaddon.e.l.b.a.WAITING) {
            switch ((int)com.yiyiaddon.m.b.a<"s3lmm7uza2r0x4","4prKBtspj7ybke8q4Vnrrlud1Tleqxq3gBxHNQoWP+8=",-3773805652936115227,-5222491418165652453,-5529574027842755438,-940596725574428399>()) {
               case 2044784812:
                  if (this.b.aq()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1fw9or9h4vc6","XdzXp00BRWf0lgsdYSBD3wKAwID2XZF3Id9rw+CiXlk=",-5576438439651633599,-6572165513455051879,1923664262707977265,-1547715388342566071>()) {
                        case -412293989:
                           return this.b.C() / 20 + "";
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return this.a.D();
      }
   }

   public String cT() {
      int var10000 = this.b.B();
      if (this.a.di) {
         switch ((int)com.yiyiaddon.m.b.a<"s253m07p92v2xm","oj0+4Rke9/kwsweXKCA3t7/3QCS55PRazuL7/qSXrH8=",-7599990880005979713,-4486549608500400992,-4642867067333033015,8532252928547561771>()) {
            case 280422012:
               String var10001 = (String)com.yiyiaddon.m.b.a<"s22gkgdow5apxz","7ngCKgqr/P1+fONNMsTugYW2BrIah1hoDPX76F2p/gI0afYX",2861435448307263700,7353111725406730094,8481813392661598437,-7229293123887849583>();
               switch ((int)com.yiyiaddon.m.b.a<"s16gdkw5syf5v2","fPjr0mYxGhxLbBjOrNXWI0vZzzGBYfM7um/o85Sf/uI=",8238091530025711906,-8249968979388678765,1775124958174696899,8073847306585957492>()) {
                  case 1176275524:
                     return var10000 + var10001;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = this.a.lr + "";
         switch ((int)com.yiyiaddon.m.b.a<"s1qp0b5blbd19d","rmh3KeNR6KuexmUqBnPqNtLcKMOa2uXILZnu+J6lhoU=",3514865430529472931,8320863156227823613,4417240804784885925,6791162275124340458>()) {
            case -1909903174:
               return var10000 + var1;
            default:
               throw null;
         }
      }
   }

   public boolean cF() {
      com.yiyiaddon.d.b.a var1 = e.b(
         (String)com.yiyiaddon.m.b.a<"s20fm77y8as972","Rv82RpMjNyS0v1bRs9MM0zJxschQEEXLlkfLsr8oAwQnDs+DqnjWh6S6UNyplQ==",5884780145887465922,5193670358049271909,-6743902277905783547,3416661411905600986>()
      );
      if (var1 instanceof com.yiyiaddon.e.d.a) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ghm0a9otjriq","NaAZ+pDsfUr0+dTTqLi7p4iUT3s0p9rZz9OrULqePWw=",2220026650841200733,-8066149259668975726,4577648617302129023,887587132195977010>()) {
            case -417644934:
               com.yiyiaddon.e.d.a var2 = (com.yiyiaddon.e.d.a)var1;
               if (var2.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1b4k80dxz6usj","Te5Jb78bXadfkW0rrCP7rIl3nmhRysyhuvxMKSIo3tI=",3576391643309288762,2161274693341802022,7663853638234996417,-3986775459976719602>()) {
                     case -477973874:
                        if (var2.a().ai) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2cd0yibbhygwc","0O8A+xjogOD8cs0yPQykRxqcmsOQr6Omij/mrmPi6nQ=",3841432865813914438,7891787433318686630,-230377504329866185,-200689874385227873>()) {
                              case 1337986583:
                                 switch ((int)com.yiyiaddon.m.b.a<"s4nro5d0nrbc7","p6Q3laKBGqB/L+VaC+LMwS8mmrP5R6uJOvuafWroAJI=",4162764430947323069,-5071605514596556807,3826135418357532527,-3640767388514884139>()) {
                                    case 1616914494:
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

      switch ((int)com.yiyiaddon.m.b.a<"s317ug9rzk24b8","USfwpiGXiVGRt+ISOHHmu5uLn0WwKeoBQbDm+qL8KZU=",-560000892242581917,-7667474518282795705,-6299761933002898532,211266577181887415>()) {
         case 1955223336:
            return false;
         default:
            throw null;
      }
   }

   public String cU() {
      if (this.cF()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qqkns4u8lr1b","sY4xTQnaI2YHCT9ZEyTyivWFFK189WiUqz3xE2K6tyo=",-7826912935101018482,6212924281417392416,6326909378754546939,-5750791463646335230>()) {
            case 2091519006:
               String var10000 = (String)com.yiyiaddon.m.b.a<"spjj957cg9scr","E1rGALr2qJOZ0HqhHSFFwaL/NjYLzRSRVnVZQCmDq8JKZ4EtNGoCnfdWJPE=",-5895300684188271027,-5422547544197040353,8367350312320885875,7121362830335965201>();
               switch ((int)com.yiyiaddon.m.b.a<"s1u54c829cdaiz","m6SPhgaVev3J6Tl2iJblsdqR/Zy5jNy5Fp7r5C3fmEc=",3861139786324266457,8625046999754423263,8593729326870967054,-3350289953363674488>()) {
                  case 1667995767:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = (String)com.yiyiaddon.m.b.a<"s3fekpw3s0dh12","x0J1gYASoOz1j+fAwpWprEVGmL5NFxdxG2oeBm6+qUSlLmISA7g=",4021374171159536894,-8363822229035604130,-1532359198815795724,-6601503458150651397>();
         switch ((int)com.yiyiaddon.m.b.a<"s3qci2q9jh3r7t","LnNgcYeibPtVNdFE46NozJuLO9FzYDJdtH2+QWj/e6Q=",-286402843813705974,-437412238804823866,-7524463725494857454,-5531757372088896757>()) {
            case 1851811422:
               return var1;
            default:
               throw null;
         }
      }
   }

   public void aA() {
      if (!this.b.aq()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dojuozto0lg3","JmQj/3208aLF0OQGtDuefwPZNmxpdvncxjtxNsrneZk=",7660335551400502431,-1604645449294951551,-4539037800221173646,1022322457918674130>()) {
            case -989734674:
               return;
            default:
               throw null;
         }
      } else {
         this.b.aA();
         this.a = com.yiyiaddon.e.l.b.a.CONNECTING;
      }
   }

   public void aB() {
      if (!this.b.aq()) {
         switch ((int)com.yiyiaddon.m.b.a<"su553vwj7xy9f","jaBCvVipEX0YSZp9kqnMwMmaQYLquVisfKfJRrOMAX4=",-1629533928587949605,-2516411657471896145,7463863031950767076,-7063039484717148384>()) {
            case 1007643713:
               return;
            default:
               throw null;
         }
      } else {
         this.b.bj();
         this.a = com.yiyiaddon.e.l.b.a.IDLE;
         this.u(
            (String)com.yiyiaddon.m.b.a<"s3quqqrlhfsyec","5Nf2uV0+rKIPFhO8yCnQ0u/clDP7iUoijl5OpEQB3bO0VkvmgJrkODNYimndJq52",-2854157804637212332,4719602331009815726,-4359816678925959458,4777286826551014697>()
         );
      }
   }

   public void fX() {
      if (!com.yiyiaddon.i.d.b.fu()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n9njtv488zmw","RskZoYcDeak6YQPzJg2XAhXwkTyEo75Awd6G5aVgxhc=",-1968640278320555848,7699995212246495085,-3599032371854358875,6857420849840079474>()) {
            case 1705044257:
               this.u(
                  (String)com.yiyiaddon.m.b.a<"snoi8z3cbaif9","4cWGUBPqZmVBvBfHfV2Ght21Pf4RHRRxiOU4JG+TEORLmz3FP+vzMbWKQzbCiUjpqzHfiRcent9u2DS2fbV6PLu7pmI=",3726197642337431604,-6140136498307540074,-5710469473368365108,-203656554397800463>()
               );
               return;
            default:
               throw null;
         }
      } else {
         if (this.cF()) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"s431sgg1zjczr","8k3ktQBh2/pjsH9dLXOADVRZGdAjdt+05X2JbW9mvpM=",3626874838819432853,5826091707081600946,1347793044656772086,-8102132162122667969>()) {
               case -288979298:
                  this.u(
                     (String)com.yiyiaddon.m.b.a<"s3ks8ijvt0g2jf","oHU5vZAQHJHQ5XzpnhCfkG3G/3PcP9ygym7BQnFbW5JHv2SKrsnA4clw35ls4ZYePntp804VJeHis2RDPxD0g7bCu7E6wovoA4/iom60",1875403050619852376,-1356823700175874085,-3072881493634413875,-5398415397035298945>()
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s30y4pwiwga73g","sUzwjn9UZQfhITNoFXAsZEFP9QyMJQmYe/E4wn9cn/o=",5673594543310491956,314645291216169673,-3576596127296564810,-1367682031660298235>()) {
                     case -550337611:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.u(
            (String)com.yiyiaddon.m.b.a<"so9f7n89nvmwn","2lRos18dMDXsHtwSy3m7OFGJvYe1+JnlUDjH6z/OPeVY2gqNDc2b89ozwEUizNNzA+2lV3Mfa7wlx+TGU5W5kGWkqRJBXTYL8DSH0j12wR3/W0oA",7345519500193284394,3762960891629876484,4261208020169146987,-7101268930734277846>()
         );
         this.lm = 10;
      }
   }

   @Override
   protected void m() {
      this.b.f();
      this.dh = false;
      this.ll = 0;
      this.lm = -1;
      this.a = com.yiyiaddon.e.l.b.a.IDLE;
      this.u(
         (String)com.yiyiaddon.m.b.a<"s16y50tcl91srt","/OGGyOzb5xViiS4UP4mnZFFXyg6XGF0dggGmw4Qxo5qxqn19WJM4wZrgGHtW8rfMfB4fDoKtd8Qo8Lm8QRUVbCzhT9QTYqr1R/FmaxsW+/8=",-2997743960887124846,2507326477604721010,-6054933294195772655,-7082808819260638204>()
      );
      if (this.cG()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n5c8hwmx2w1f","9jQM2xP3gydt2mRHufuzd4aLmg87UevDyA4Wa2xIZfs=",6897687982266596691,-7009133705856769190,-4765211019655924425,4711062646859562324>()) {
            case 337623166:
               this.aQ();
               this.a = com.yiyiaddon.e.l.b.a.READY;
               switch ((int)com.yiyiaddon.m.b.a<"s1tacwaqjg7fdd","Rwp1uhWFakaBV3zXrJbrrfyeDImWZgt1f711myPPbdc=",-5129761228342395491,-4685722611744103057,-5878138603779581893,-8198799987704736388>()) {
                  case -378909055:
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
   protected void n() {
      this.b.f();
      this.dh = false;
      this.ll = 0;
      this.lm = -1;
      this.a = com.yiyiaddon.e.l.b.a.IDLE;
   }

   @Override
   public Set<c> c() {
      return Set.of(c.TICK, c.JOIN_SERVER, c.SCREEN_OPEN);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sh0y5nk99zt5m","DZU0aKIWloDHNR7zWl1MfYy1yLPad31Brh40YW4cAtQ=",1751058113532815277,7889727384385792438,3138659350639342491,4117141519516021274>()) {
            case -436611612:
               return;
            default:
               throw null;
         }
      } else {
         switch (var1.a()) {
            case JOIN_SERVER:
               this.aF();
               switch ((int)com.yiyiaddon.m.b.a<"s3jz7wo55x0u6w","p4oTsS1Kla0Z6i8Qqr9Hbdg/lzkM6rsseE9EXnubzvg=",-3697263726935851318,4987077505055210662,1878532036338811213,8188358532058512946>()) {
                  case -750782530:
                     return;
                  default:
                     throw null;
               }
            case SCREEN_OPEN:
               this.i(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2c1s942hlifux","PQI28mcwdkI75qh9fvQ5Hc6S8akETuO12dwXjLrMbzE=",-5889064123899048783,2385069787046250626,-5123301807394922989,678273012740899225>()) {
                  case -615522213:
                     break;
                  default:
                     throw null;
               }
         }
      }
   }

   @Override
   public void b(Minecraft var1) {
      this.fZ();
      this.b.ae();
      if (this.a == com.yiyiaddon.e.l.b.a.WAITING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2o0vqq16b5sfa","sGkhCwNy2efHxEsKE6cL6K1JcRnNCTNN3ZEnj4hjIbM=",-2460896924690775672,5017321340229438572,7061147134659097776,-7092977216085803223>()) {
            case -877998326:
               if (!this.b.aq()) {
                  label53:
                  switch ((int)com.yiyiaddon.m.b.a<"sue0sm0q50txc","i7RtP0dSpyXZzgKNqtuV2alPi36bJ3YAhtRRcNcQk4w=",7848710717853698136,6327363728659002388,8378640608526908205,-7670081096770831977>()) {
                     case -1364509734:
                        this.a = com.yiyiaddon.e.l.b.a.CONNECTING;
                        switch ((int)com.yiyiaddon.m.b.a<"s2g9b745jnaiyt","pkKVMiL5hsMuNuvcodyj4gmkwXWUzeAf5b6lQ5dp+vU=",924095640921174728,-701599140606861191,-990492084153372043,-6769345922529914074>()) {
                           case -588245667:
                              break label53;
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

      if (!this.cG()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j0ygyb68j5cu","Sjsa0qkwbppFCOZ1SKAql1P1rBA6PhaTTo+wBdZmUvo=",1931845542827471058,-5302414727268335918,2933268448509648861,711136217569514811>()) {
            case 686173218:
               if (this.a == com.yiyiaddon.e.l.b.a.READY) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1fg99uwukkjiz","CFDGorMplsPT6W7cIsXRb+YnJ03ph09SpwSmdXVpdkw=",2327185187106494978,-5356876231384669428,-4502442811650408985,6155085807314408030>()) {
                     case 1158513608:
                        this.a = com.yiyiaddon.e.l.b.a.IDLE;
                        switch ((int)com.yiyiaddon.m.b.a<"spynuo758f4mr","kv+IZ+bXvzRu2FJ0LGBjBoTPAv7LW3jzH7NvsmqGJns=",6257702175985786613,3996880870136671384,-3776358366935381668,-5063109880340370410>()) {
                           case 1964110606:
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
         if (!this.b.fH()) {
            label46:
            switch ((int)com.yiyiaddon.m.b.a<"s2o9xoq0ujyfxi","i/XYXJ1TOKlEFm5j25WN3y0x1CXX5H5iD2P6tH7pg+w=",-7364462252660513157,4311812637089944182,6384006985610292616,-553642823026305895>()) {
               case -1333911717:
                  this.aQ();
                  switch ((int)com.yiyiaddon.m.b.a<"s1t4ceswfdg7y3","JBUis8ECqDdHLH28lrVxgvFHaIRIMJOVwuquI6uMxlQ=",3936084886496497670,-8575899494644243134,4057375053639319335,-1714720126002012867>()) {
                     case -384507866:
                        break label46;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.a != com.yiyiaddon.e.l.b.a.WAITING) {
            label41:
            switch ((int)com.yiyiaddon.m.b.a<"s741s6tivdd5","diHGk/v8yMcOVhXqms6XNdXQpkLQNHNcDzj5enQdTxY=",3063681891547276965,-8382058452178896922,-4112997198911408141,3519507205799868789>()) {
               case 234559333:
                  this.a = com.yiyiaddon.e.l.b.a.READY;
                  switch ((int)com.yiyiaddon.m.b.a<"s2ypbxdpfyi7v8","wE3Gt1WcBBn8RXQNevmZH8FcLfGTlqBGy/eTRb0iDaA=",479080209204632838,7156506386575752206,1524181874635447306,4102814671025076038>()) {
                     case 1367919380:
                        break label41;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.fY();
      }
   }

   private void aF() {
      this.aQ();
      if (this.b.B() > 0) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1fqmioli0w3th","vTPPACQBSdlfB9/H/dSSntLt7F2T76OGRoK9W/TGc0Q=",6262245591133133163,-3808717069886450593,-1182596572032293521,-1181078573870766108>()) {
            case 2089326000:
               this.dh = true;
               this.ll = 0;
               this.u(
                  (String)com.yiyiaddon.m.b.a<"s2nxx680xb861","bVvD+B1fxz1A8s4TjDJLbcoXCOPxXwIVqc/nnjVd4SzX6krk9YQJvwiFGvahCIHVeynxAQ==",473863028595879170,-6681116497659205178,5719513096966106638,-3072138594495487646>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s170ije6pk10h","kpILQu/PbqmapObyFwAgGfWUsgOLBqDvuaOtu9jWsMU=",-8521783860803070574,6019767988251278840,7243810272371358368,-5011888963061831852>()) {
                  case 1949531461:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a = com.yiyiaddon.e.l.b.a.READY;
   }

   private void i(com.yiyiaddon.d.a.a var1) {
      String var2 = var1.l();
      if (DisconnectedScreen.class.getName().equals(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"sgx7eh6yzxzd9","IF4RUOPaXpcjG0kO37/8nhsj4tCLaADwTsqeZ0pGmuM=",8050436877491805084,-1108527976321576442,-5056194048923390781,3624455053429863429>()) {
            case -1447080420:
               this.aL();
               return;
            default:
               throw null;
         }
      } else {
         if (this.a.dj) {
            switch ((int)com.yiyiaddon.m.b.a<"s6s7dq4un2lx1","clSPK8QR0B5I2c77jd+Bd5i/BeGL8DPbyiZbLSGCnwY=",-3104005151391433116,-7346376125285834304,5975924885321377316,-2962128392652142973>()) {
               case -1142693265:
                  if (!JoinMultiplayerScreen.class.getName().equals(var2)) {
                     label31:
                     switch ((int)com.yiyiaddon.m.b.a<"sge1xpdb8mcv0","06pkNB2RpeU9y2AFOQS3J/sGnom/o9qcH2P86oyIJSY=",-3806576249577777556,6480285920010692099,-3190079646272586654,520376283027927233>()) {
                        case 804238202:
                           if (!TitleScreen.class.getName().equals(var2)) {
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sow6co2qou3yy","VVBgy/u0nAyL0TjenhmqOL1X9BoNMjzAAZEjoDQbISs=",-2486691853212880902,5121659460002934785,-2196939388737424727,-5939875641515138903>()) {
                              case 1283932575:
                                 break label31;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.b.aq()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3a4qjtm04z6aj","pDAdpa3V4YucCoGaDuUZDwDepXZHYscxHFO4KMeyXZw=",4821474632216954101,4426771261934107315,-4599763053463687427,-1083928588629676423>()) {
                        case -812819700:
                           this.b.bj();
                           this.a = com.yiyiaddon.e.l.b.a.IDLE;
                           this.u(
                              (String)com.yiyiaddon.m.b.a<"s265dch8mlzbhc","79/yosyXcOkjJCAhSpWz5CVGiUeFfzQD73C1VE4UmZSoKdGKV8FV9tJ/bjMXvNXE1RbXQA73iDLwmtsyJkNYOgTcjT2xxfLd",-1351930733071761615,904930765197634663,142710129562375069,7466032116375961359>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"scqcpw43guzo0","r44bB4I0++ukSzcWWYfQJiv1wLaTUFEmq1o+9BASyJM=",8691381881180506673,4884276350882925541,6809410406997364908,-512469977496566544>()) {
                              case 1857086275:
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
   }

   private void aL() {
      if (this.a == com.yiyiaddon.e.l.b.a.WAITING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q703od5eb0e3","2CSERNT8gqD2LhGVHMWQzh/xSWDUUTZM2CTgE2ueqRg=",-536692731488490342,-1513648671712882511,-3218058795698345648,-2147640258248716993>()) {
            case 1073547574:
               if (this.b.aq()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ovu6nypmaufc","fUjwWa7VhTp6eBMpbdSGD7WLTtoW3i2/wiooWs2mDRY=",4086802816762769640,-4667852200672760863,8288413808983671171,1903057795951838622>()) {
                     case -1682183944:
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (com.yiyiaddon.k.d.b.fI()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fcuiiqtyvu8a","VG0ifba9ajd0RYHu+AiZGaLwa24+o2rKy+X/xyCqRHA=",-8057611579148944405,-1399437774636100544,4665611513697220372,6367369373242059756>()) {
            case -1585208715:
               this.a = com.yiyiaddon.e.l.b.a.IDLE;
               this.u(
                  (String)com.yiyiaddon.m.b.a<"s18shi2qihdo5f","93kPKnBzNEl65yD1MoqcvsjVtMzmuY3hSg56ChRoQ4AsmplHwVbkqsAv/efOoTi2pNDJHzZWh/FGrida095XZODhL/E7UIhFqx/FQd8G3HjOwA==",-8362329013498769085,-1725114554216763827,-150852366678392545,-7470046304821857893>()
               );
               return;
            default:
               throw null;
         }
      } else {
         this.dh = false;
         this.ll = 0;
         if (this.cF()) {
            switch ((int)com.yiyiaddon.m.b.a<"szwoh8mvuhsug","CVVNGIuZWNwdAeUxzPn9q6WmGeJ68vq60nG4vogP3+I=",7550210826730173772,949309216015041613,21505986577637228,6416011083494442943>()) {
               case 1069049871:
                  this.a = com.yiyiaddon.e.l.b.a.IDLE;
                  return;
               default:
                  throw null;
            }
         } else if (!this.a.m) {
            switch ((int)com.yiyiaddon.m.b.a<"s371zn21jabnp1","SvuNQTkCvi48hkX2Bee8QZ73T92TyV7BBkRmnrS7WDE=",-8096447297345404750,-5042119511150354341,1492555659585856496,-8685647729410327090>()) {
               case 1962306759:
                  this.a = com.yiyiaddon.e.l.b.a.IDLE;
                  return;
               default:
                  throw null;
            }
         } else if (!this.b.fH()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1nar8n5vb8kp6","OmV+iqMp7HAmSerjKj1uHgxe+yVhQs6MCvDHH01YlwE=",3312327038409173740,6461107493069926605,-377694248419350518,8249943712230415178>()) {
               case 224702366:
                  this.a = com.yiyiaddon.e.l.b.a.IDLE;
                  this.u(
                     (String)com.yiyiaddon.m.b.a<"s92lre4vg0s1n","yh0gQE5WSEL7PzZbmiqDUcfUZ77kDqa2UGXB0TAUmZoLxRgz3bHAAn5Cja43yrtQq3CnggIR5vCtNLfik9c1gat5zQ86Ts06tqllGA==",2805996829779664850,-8213297776827513658,-8414895197879955457,-9129461661119178022>()
                  );
                  return;
               default:
                  throw null;
            }
         } else if (!this.b.ap()) {
            switch ((int)com.yiyiaddon.m.b.a<"sshhjeebcv3m","gRmp9OlFvazTMznTwdfxvZjD+CMLienRqOe6VXLDLpM=",4132137368296689085,8759901010397705158,3846913754767805067,-8293900369907649413>()) {
               case 259151695:
                  this.a = com.yiyiaddon.e.l.b.a.IDLE;
                  this.u(
                     (String)com.yiyiaddon.m.b.a<"s23crcl66674ag","JuvEqFPij5vCFCKR7poplLDxqc57CKCSzY2zwDDVEzp4NJrgs+mat9frct0351lsjKFtV/PURygWVFr6KV4=",-8943459381637015974,-8211736407024644958,3819204739452498869,-354182619491910077>()
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            this.a = com.yiyiaddon.e.l.b.a.WAITING;
            this.u(this.a.cu / 20 + "");
         }
      }
   }

   private void aJ() {
      this.u(this.b.B() + "");
   }

   private void fY() {
      if (!this.dh) {
         switch ((int)com.yiyiaddon.m.b.a<"s1cypmkeb5gusg","XsCzzzDA/mJCrS03jQM9log3l0EtuiYi018CmM6Y7Mk=",6205366425422454758,3152869860428866704,7142914248295444571,-88956108292650438>()) {
            case -1959802219:
               return;
            default:
               throw null;
         }
      } else if (++this.ll < this.a.ll) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bf0f18zh51b7","aNfWsbbmkavRT9VH3cyZ/H34DMBuhWcqw/LKM8dRd4M=",-7646645647811195027,2871117348147367494,-4672426567891671061,-2276408932445928362>()) {
            case 1681792792:
               return;
            default:
               throw null;
         }
      } else {
         this.dh = false;
         this.ll = 0;
         this.b.bi();
      }
   }

   private void fZ() {
      if (this.lm < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sthbnrs1goobt","UVmvyw6kBzzIVjjeKQtQbRUGeNeptMidxO4zBVb5Ae4=",-5701363607700589405,-945221186767894790,2003784032720896283,-5659385465206837973>()) {
            case 129650809:
               return;
            default:
               throw null;
         }
      } else if (--this.lm > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s51vtw12k5yci","r4zEDzkQIgVUVBFCWWs22qytjN6lwb7/eSqjNeWzF2o=",-6702618859694376759,2174844581825412222,-8502635881749708682,1938562089471195061>()) {
            case -1869783388:
               return;
            default:
               throw null;
         }
      } else {
         this.lm = -1;
         this.ga();
      }
   }

   private void ga() {
      MutableComponent var1 = Component.literal(
         com.yiyiaddon.d.c.e(
               (String)com.yiyiaddon.m.b.a<"s3mgw4cvvaa909","eyQzkslyncZnrvRt+UVPrR61Vlv6q/mlpF8f0HDlPUgOihGF",-8784195884276524739,-6925404932798080991,-6264999306569243332,-1496655631800208578>()
            )
            + ""
      );
      com.yiyiaddon.i.d.b.c(var1);
   }

   private void aQ() {
      ServerData var1 = this.T.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1y341qz4y6kdh","B+F4i5N/P3N6wvFZrp376LU5FDinzrtgMkab5Vq7XD4=",-594957594301895508,-3332593175947378528,-1104931128676886660,1081273460516721178>()) {
            case 242063223:
               this.b.a(ServerAddress.parseString(var1.ip), var1);
               return;
            default:
               throw null;
         }
      } else {
         if (this.T.getConnection() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s90pc6evm4adw","QM/J1JQLiLSCHEbGOnd4QYB8lwnDntcDAZIC/e07nes=",4352944819260636467,-9033581569729687180,-6681250519337954077,-6996583585581731154>()) {
               case 1281298859:
                  if (this.T.getConnection().getServerData() != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2gdjepdu0n1az","hXd6ia976m/1+X8Qgq4MRJkfEk9NNlss6OEbxBVcnU8=",3562767587334656674,-7495576930578396819,-1195403362853909374,2631604455775457419>()) {
                        case -757680430:
                           ServerData var2 = this.T.getConnection().getServerData();
                           this.b.a(ServerAddress.parseString(var2.ip), var2);
                           switch ((int)com.yiyiaddon.m.b.a<"s21aqcpy7f1s2e","n9oFoDNA91wlTfZ8dejixaOrX40p88mUhDfLldOpBJg=",-4193043520672219594,3828203619429686030,3593208668602410131,-7293841851107588857>()) {
                              case -102242171:
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
   }

   private boolean cG() {
      if (this.T.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32jzdhs9em06u","O4Z4DFg66+gIJcjq3IzmFvFp+0K9fnk4nwOIMtWjUHk=",8064906399677916733,-1830749610701538863,98803994766042924,-1540071728778161591>()) {
            case 1718249608:
               if (this.T.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s14j5375ee31ni","0M9yzOzOU7u90MkA/sIhsuv7D4WJF6S5pl3X9yWaL3A=",-1289879030207513359,-395171248770773493,-6826833279287986290,4617947747797033895>()) {
                     case -1908802448:
                        if (!this.T.hasSingleplayerServer()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3gmmxpepu6864","A7y8njPxcS1QdcJczmhxZRCPc1NsbhNW+/hs2TOpzpU=",-3307039833347507438,-6620819617355129580,-6626606117668827932,4765355890792579069>()) {
                              case -611298121:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ush6t9zugze7","BhLhxhufqS/Wcd7beu7Sicf4jeZj/nrPlEcVVoEQGlk=",2954109825882381592,9205031155180741992,-7710014232889811062,5729975609392688584>()) {
                                    case 13159441:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3c39eu4rxpz43","7eXnqj33uszGjBfarxQvByoyKP5BGetw3p4HaAA7Abo=",-4615054391320308178,1899734999650266971,6524538393753761672,1033130948538796963>()) {
         case 268196628:
            return false;
         default:
            throw null;
      }
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s3mgw4cvvaa909","eyQzkslyncZnrvRt+UVPrR61Vlv6q/mlpF8f0HDlPUgOihGF",-8784195884276524739,-6925404932798080991,-6264999306569243332,-1496655631800208578>(),
         var1 + ""
      );
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.l.c.b(this);
   }
}
