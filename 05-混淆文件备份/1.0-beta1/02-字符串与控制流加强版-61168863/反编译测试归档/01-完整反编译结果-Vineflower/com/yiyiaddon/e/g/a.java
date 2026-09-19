package com.yiyiaddon.e.g;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.g.d.i;
import com.yiyiaddon.e.g.d.o;
import com.yiyiaddon.e.g.e.c;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String jh = "enchant";
   public static final String ji = "自动附魔";
   private static final String jj = (String)b.a<"sp1zp5mjg33fg","1d7JNucaS9fWIscHT8da9uiShAU26uO0ZC81+Y3y",-7206579428225292219,70926526349891509,-7377596072190562810,8717326705013378339>();
   private final com.yiyiaddon.e.g.b.a a = new com.yiyiaddon.e.g.b.a();
   private final com.yiyiaddon.e.g.h.a a = new com.yiyiaddon.e.g.h.a();
   private final com.yiyiaddon.e.g.f.a a = new com.yiyiaddon.e.g.f.a();
   private final com.yiyiaddon.e.g.i.b a = new com.yiyiaddon.e.g.i.b(this);
   private final com.yiyiaddon.e.g.g.a a = new com.yiyiaddon.e.g.g.a(this);
   private final com.yiyiaddon.e.g.c.b a = new com.yiyiaddon.e.g.c.b(this, this.a, this.a);
   private final com.yiyiaddon.e.g.i.a a = new com.yiyiaddon.e.g.i.a(this);

   @Override
   public String w() {
      return (String)b.a<"sp1zp5mjg33fg","1d7JNucaS9fWIscHT8da9uiShAU26uO0ZC81+Y3y",-7206579428225292219,70926526349891509,-7377596072190562810,8717326705013378339>();
   }

   public a() {
      super(
         (String)b.a<"s3n3xmyrajd3of","RGykPG1Guua+Nv6B0PyGZ1wfKpH0fc9FdlrCAJ+hJJhbavlgg4i19R79",1721091339488444778,6864499461975366247,6352263783774448627,-750694614919668099>(),
         (String)b.a<"s286w9wyh31dbz","gffHpPquZ6C3eJnhaUu0Yra9qUdC9C4ZcRgOt0kOblFApv6N",-987190600865837770,4154577408674425011,-733250797583506733,-8449162305364057128>(),
         (String)b.a<"s1t5n4znwf7jpo","yRSktDEYzFyJPrh5/spjJZK9GTb4w3jM7m6cJe/7jp9ooFXtoIww2OJAPP6jzz6+",7794048773391165852,5386613719634663262,8854360276163931491,-8104312048522462443>(),
         (String)b.a<"seomvi3j6k67s","LLQCK5xmqmuF1v9Ge0ibP0hW8mNQ9r/LBE2vdOUsgMFschZD/euLY0grp1KuBtoVdihCzl1+LaBN9tZK8MtZ/9Wft6MZQPBAbtblWtQRMkIGllB2UOjGc/bnqP69Cfnla3qYig==",-1095436618208076505,-455935295144141116,-1644489961210125878,-4281590510115223302>()
      );
   }

   public com.yiyiaddon.e.g.b.a a() {
      return this.a;
   }

   public com.yiyiaddon.e.g.h.a a() {
      return this.a;
   }

   @Override
   public int i() {
      return 50;
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

   public c a() {
      switch (this.a.a) {
         case GEAR:
            c var2 = this.a.a;
            switch ((int)b.a<"s32q25ygrmjk7q","ZA3ZRZoxcjJ5Ul34UJ1oNJBPiEd2Ke8XPa1BK7GUCwc=",-18531763113479501,2164473408757668086,3267269435142487963,-6800559179108374450>()) {
               case 1232252180:
                  return var2;
               default:
                  throw null;
            }
         case BOOK:
            c var1 = this.a.b;
            switch ((int)b.a<"s1npntd7ff2ded","oCDxtzjgFfwORaIJ4jUxsdQ4k7N7TBVDPxOdoNh5GkU=",-4323219393391567400,-7326473077220197794,829916380535208905,484434039560917984>()) {
               case 135031141:
                  return var1;
               default:
                  throw null;
            }
         case CUSTOM:
            c var10000 = this.a.c;
            switch ((int)b.a<"s2kkig5wof8p6x","fvORrMaDTtFYZKmTah22vAUtiPeiwS7DawkxvEMki4Y=",-249072124871463571,-1043530556944203168,-7161104405740196522,4571816435734007556>()) {
               case 251529999:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   public o a() {
      return new i(this.a.aw).b();
   }

   @Override
   protected void m() {
      this.a.C();
      l.a(
         (String)b.a<"s3n3xmyrajd3of","RGykPG1Guua+Nv6B0PyGZ1wfKpH0fc9FdlrCAJ+hJJhbavlgg4i19R79",1721091339488444778,6864499461975366247,6352263783774448627,-750694614919668099>(),
         this.a::render
      );
      this.a.bB();
   }

   @Override
   protected void n() {
      this.a.bE();
      l.l(
         (String)b.a<"s3n3xmyrajd3of","RGykPG1Guua+Nv6B0PyGZ1wfKpH0fc9FdlrCAJ+hJJhbavlgg4i19R79",1721091339488444778,6864499461975366247,6352263783774448627,-750694614919668099>()
      );
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.SCREEN_OPEN);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 != null) {
         switch ((int)b.a<"s2we3vvdkez9c0","cO3o/HY5R7AeEPLWFBy9+HVLBcixPRUKGEm56j5dvWk=",-3317908770647648783,1740070733003986387,1173997492502447833,1182252948961166803>()) {
            case 35498013:
               if (var1.a() == com.yiyiaddon.d.a.c.SCREEN_OPEN) {
                  if (this.a.y(var1.l())) {
                     switch ((int)b.a<"sbkc5cdlkbyu1","WHUHpQ87vo8Ys4S3Sb+SpE+uaTanP3oRaYcr0tIc6X0=",5861571891808029373,8223698132927470486,-5021047209975829462,728713997444365306>()) {
                        case -706780562:
                           var1.i();
                           switch ((int)b.a<"s52n3zltjp0n","/EN7DeC/Np5lJM2EGBZAOgNjzrZPnFoRS+TTjIXGg+w=",8715248984555542050,591237778500025565,-6971253985669502448,1632513792313811392>()) {
                              case 1080225962:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)b.a<"s8qfssuebpa23","ZY3TAt9WREPysap/py+BaQ6F2PXExg8pJLkxJBcU36U=",3839824033337191762,-2134669779193279077,-1430435662116348954,-4040450194059925278>()) {
                     case 753945777:
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

   @Override
   public void b(Minecraft var1) {
      if (var1.player != null) {
         switch ((int)b.a<"szm58zcoiq0dk","Prw8gqtZK1O9am4o7CXJVOK+cx7iSj59JHp2JaBUMRs=",-4677678669496198314,3082483533671914752,2019028827512532766,4094047971327696322>()) {
            case -1569150859:
               if (var1.level != null) {
                  this.a.ae();
                  return;
               } else {
                  switch ((int)b.a<"smfu9qg54rccu","Oi/8UDFJFNXGlZLrlkDcDXDDJyKLUUUao85ywfkw/2A=",3834432295932855755,6939941483531115401,3740882767688098934,-923989952918807655>()) {
                     case 1706654978:
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

   @Override
   public List<String> f() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)b.a<"s16hgat0czahay","s0YqeuoWnxb9lVXYLhuwYeje9u1AoXN5i28erHaWG5U=",6835300390918712223,-735812150088360531,9028617676932473725,-4967403973424595367>()) {
            case 1098180186:
               if (var1.level != null) {
                  this.a.C();
                  ArrayList var2 = new ArrayList();
                  com.yiyiaddon.e.g.e.e var3 = this.a.a;
                  c var4 = this.a();
                  Iterator var5 = com.yiyiaddon.e.g.e.b.a(var3).iterator();
                  switch ((int)b.a<"s2azixnxadcnk7","y4mJz5wc4Lo9j8lIwfhr3mFyrUuPlXCXy84Bc93HAq4=",3865850685932773811,7572543891035157780,5100095710799562671,-5071842119817793519>()) {
                     case 2074955915:
                        while (var5.hasNext()) {
                           switch ((int)b.a<"sorup3kiymypa","8ZMueWP1tZyVCC9xtdbp0erAtbWWtHr96Djuasvq9ko=",7990432741421585194,-4291006205697640141,-2074360792355020569,795608653187846401>()) {
                              case 2072209012:
                                 com.yiyiaddon.e.g.e.b var6 = (com.yiyiaddon.e.g.e.b)var5.next();
                                 if (var6 == com.yiyiaddon.e.g.e.b.AFK) {
                                    switch ((int)b.a<"s3fs7g21ujguub","bSz1aP9zqmyP1jUEVD2ngJSsqTAWezXUYF4cQIeeclE=",7714482154450937261,9060752460536529324,-451947847799579876,7307765864587628525>()) {
                                       case 387691253:
                                          if (var4 == c.DRAIN) {
                                             switch ((int)b.a<"sew719ns3q68x","KRiWLhWhuZ0bJDbcbFEos9C8sIDJ9JrDS2Dl3Rnz3DI=",-7374303715194799040,-7708861815400599250,2721803409477218896,7835197868889656503>()) {
                                                case 576585839:
                                                   switch ((int)b.a<"s1jfd2ig1eg3on","32ndqIKgi7jDwfb8Rg/+xI6iBXrulf4Y6JZZdTTbwbw=",6334510379522193748,-849808746980057528,7851537989093990287,-4953607521942637411>()) {
                                                      case -230105379:
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

                                 if (!this.a.a(var6)) {
                                    label38:
                                    switch ((int)b.a<"sx9979sv6fkyq","n7/mG3Jz1BIPWhVfxmDgIhWAaUPP0/QDFm7jLiYOOqY=",-1782779682237141200,5738060481073652231,980752679757793691,5863265155696601706>()) {
                                       case 1426572540:
                                          var2.add(var6.D() + "");
                                          switch ((int)b.a<"s29vic8g9fnxq0","9wPIO3Or5LcMUFlld8t/hF68wfoZG1x+DvKiknAb+FU=",1913704241782339592,-5074619865970016506,5032212709616736613,9016042473778994881>()) {
                                             case 296485075:
                                                break label38;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"sk9m2y6omblns","Piw5VrdTkUjIdTIp2EmA7C+0Qh/XLnK2he/zARNwvfo=",2436745078860287883,-690662313527536378,-5760079563217266631,5775823131169409387>()) {
                                    case -1571001919:
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
               } else {
                  switch ((int)b.a<"s254zav0chet8w","JpLhVMDgqNG56ojNpuFyeyWqEQJsaRp7iE66HPxRH1Y=",8023914530089013546,-9154746131391044437,-4449104093045208526,4855533100759456358>()) {
                     case 2070066883:
                        return List.of();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return List.of();
      }
   }

   public void K(String var1) {
      com.yiyiaddon.d.c.a(
         (String)b.a<"s286w9wyh31dbz","gffHpPquZ6C3eJnhaUu0Yra9qUdC9C4ZcRgOt0kOblFApv6N",-987190600865837770,4154577408674425011,-733250797583506733,-8449162305364057128>(),
         var1
      );
   }

   public void b(String var1) {
      com.yiyiaddon.d.c.a(
         (String)b.a<"s286w9wyh31dbz","gffHpPquZ6C3eJnhaUu0Yra9qUdC9C4ZcRgOt0kOblFApv6N",-987190600865837770,4154577408674425011,-733250797583506733,-8449162305364057128>(),
         var1 + ""
      );
   }

   @Override
   public com.yiyiaddon.l.f.i a() {
      return new com.yiyiaddon.e.g.j.b(this);
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.g.a.a());
   }

   public com.yiyiaddon.e.g.i.a a() {
      return this.a;
   }

   public com.yiyiaddon.e.g.c.b a() {
      return this.a;
   }
}
