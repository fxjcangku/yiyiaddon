package com.yiyiaddon.e.o.c;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends com.yiyiaddon.l.h.f implements com.yiyiaddon.l.c.b {
   private static final String wL = (String)com.yiyiaddon.m.b.a<"s1c7wu68okn6d1","sod8KN5/kxjkATcEpM1aFxcrt+7oIEPeVnt1cvQcyVCF4pNqpYqtJwgJPMaDfkgDKmyURZhfPKC6o7VlCKFWf5E+s+3t7txdFz8=",-310438732078875664,-2365065673468699333,-3275066538220410134,-2605951687111877852>();
   private static final int px = 20;
   private static final float dr = 6.0F;
   private static final float ds = 11.0F;
   private static final float dt = 10.0F;
   private static final float du = 12.0F;
   private static final float dv = 6.0F;
   private static final float dw = 6.0F;
   private static final float dx = 320.0F;
   private final com.yiyiaddon.e.o.a a;
   private final Set<String> an = new HashSet<>();
   private final com.yiyiaddon.e.o.c.a a = new com.yiyiaddon.e.o.c.a();
   private com.yiyiaddon.e.o.c.a a = com.yiyiaddon.e.o.c.a.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.o.c.a a = com.yiyiaddon.e.o.c.a.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"suiwzsi9du0dr","WNjEXorASsMfoI0VswS4Xxm76em32tOwy6JdLxDgnug=",-6511619112320107613,-8240884367925394482,-6225029445119425141,-2592828275267574205>()) {
            case 1910368442:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s38mgapsv2hoor","Sb48ubLkpU1pT0984RVxoht9fmzKHVepfY2z+F2tALM=",5961616903707177173,2437420342338437414,-8209087134263070085,624016527704638470>()) {
                     case -2085236220:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s7y3l5sg4kqfs","N4u6YEruURe2xnT1Xc62PwCIKQ0OhSXFxLZ+RLOdsWI=",5415148351000797824,6060962759866761737,-3706261469999444190,-135492353728133061>()) {
                           case 38371545:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3utz4dmjznyaz","ZAUjiTQE85wKBBlyFq+Yu0sxpCEfrE2Msg7MQU89kYI=",-8633864333247356545,8050357376220646494,6642094527366897358,8268292380503124050>()) {
         case 199571356:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.o.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s3r5v1fg4prn28","msXpMWwmkZtOyCenDB44czfLkOLCewXs4QJwau6owgchgFFii5A9r1zs",8704669503018709432,3136702504534784295,-7660583489960707794,-2123133650171253662>(),
         var1
      );
      this.a = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.an;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s1c7wu68okn6d1","sod8KN5/kxjkATcEpM1aFxcrt+7oIEPeVnt1cvQcyVCF4pNqpYqtJwgJPMaDfkgDKmyURZhfPKC6o7VlCKFWf5E+s+3t7txdFz8=",-310438732078875664,-2365065673468699333,-3275066538220410134,-2605951687111877852>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lilkir1s86nk","90ztm/K1n+A2uP8aqAx2ByFCtI2Sg5dA+gf3Wm+Qe4k=",-3137219939389309563,4612828324264993242,-3280254771097619744,-7188106132767501653>()) {
            case 2036656332:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s3sspqhpd2ep4g","BxAnm6wAL9daVItoGNWR/4Kl3M9lnd2DZp9TVL9vA3w=",-8318782588565840288,7679149154173630504,-4966434479317160244,3791271148406162153>()) {
                  case -477552028:
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
   public void removed() {
      com.yiyiaddon.d.a.b.h(
         (String)com.yiyiaddon.m.b.a<"s1c7wu68okn6d1","sod8KN5/kxjkATcEpM1aFxcrt+7oIEPeVnt1cvQcyVCF4pNqpYqtJwgJPMaDfkgDKmyURZhfPKC6o7VlCKFWf5E+s+3t7txdFz8=",-310438732078875664,-2365065673468699333,-3275066538220410134,-2605951687111877852>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lffyvml4vcpd","4TrI+wMiKawemgmj+MZRFyY62j7rOWG/dtb9n+pvclE=",702839328706374376,-4934824731769522414,5867122417149425955,7962884551455417850>()) {
            case -265943337:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1o1lnese76r7q","LMRt6MV/yJ+ql+fVadvlPBz782vVCyK2TxfdOgqzITo=",-287811119988846887,-909125342810944523,4010065151871045388,-3145319619984371259>()) {
               case 1186160246:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3hvksa1zrmfip","NqgJrcJY4oWHuwyN4fYdGi6IWewwEhqQtL2i6RfL6Ag=",2071766249313305624,-3275194875524335388,5050372834856815141,2931822438422739713>()) {
                           case 1997500731:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.o.c.a.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s32ens1r7lacet","LTIC7P950hDjDvUrLFIYsCVaC2JETRx5ekLyILXuXp0=",-8016188747151037570,5788602094153230260,5836711244257159837,8615529148133665421>()) {
                                       case 1316847549:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.o.c.a.a(this.a);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sg9l2mh5gdlpz","tqydeJBpbbBczETho3wGE6HUH63mfyaf0VtYHpvLgmQ=",1351543982660671061,-2444947773217323786,7200784095807235034,-2509198859307187971>()) {
                                 case 737497870:
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
                     switch ((int)com.yiyiaddon.m.b.a<"swajobwi9zd9y","IDKCC3HOIcmqp1X/Bz/oZBtYeOqvp0g4lOB7TyezSGg=",-3876159746176964110,-7241899873012718768,5777388075287679028,-3921232628694651546>()) {
                        case -634511784:
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

   private static boolean c(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13oi79ckvpjy2","cl9plXNCmfDj/1uXaxTMkeMOxKMEwzdNpptDaTqb85o=",-2355658319489453837,-3652693535278034916,-4864103410536166233,-5323151655207765970>()) {
            case 1640467800:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"sh67we7nzo6lf","eo5z2XPs7iI4gwAmdlAoBSyEploEWrHj+J+Jk4VRYlg=",-638739766280079353,6204479669929632393,-6006051038330277182,-2394976356041069256>()) {
                        case -849866064:
                           switch ((int)com.yiyiaddon.m.b.a<"s1dm5hy1dw0fhe","CwvEqsw2kNBrxb60udO9i7zTlrmddRV5IAgBP8pRulY=",-4605310560112758058,-1027678852568601115,-3686544601831381466,760184867907650780>()) {
                              case -774675075:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sn1x7ubw0fvmd","tGl8A7ZqBij+P8njWLj5XEq8+mh44VrJGCdbcPyzCcM=",2997450815569802284,6226730027132732660,1895268190659475318,-197956313982568421>()) {
                        case -418568235:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s11wer1qmd7mek","vDO5ARasDG6Mo81N0wIcDrYztyVZaKdObyxt3YKXGGM=",2554299925865849679,-6989113636257900319,7354115927153076643,-2233048389784797099>()) {
                     case 779303080:
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

   public void C() {
      this.a = com.yiyiaddon.e.o.c.a.a(this.a);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.o.c.a var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s4fjbxymqzv7p","p+7itJ+y0m0R7seRBSBCREBnkR40YasMazEkcNBC/tE=",5834210376599990688,-6536027950780251830,-6605087930834508289,-3444312141719452186>()) {
            case -627906747:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"sthir6mz05oah","Np1sKvEJFHi6WSfewxLeX/9S9UGdjYRe9A3ni3mhxeM=",3018982620443567766,7097963214153415168,3162383392855054930,-8506525881671814574>()) {
                  case 395659776:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"slvyt803dpzi2","mC9ULwObsOfuIrpikUAIeYmY9KLMdNDBFfMD55magkY=",3359581514016963751,383952710808592671,-7407685079420143563,-2201221508277892018>()) {
            case 69868861:
               return;
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.a));
      var1.a(new com.yiyiaddon.e.o.c.a());
      this.b(var1);
      label35:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.o.c.a.a(this, this.a).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3ka23pm1y0e72","QvSyOI/tI4a/CP/GPmAPwwRXg2sgZ8klGlJElmEAKvw=",-5113026345945347416,191856853265786010,1252415420260315808,-6818753576772267989>()) {
               case -1935922384:
                  break label35;
               default:
                  throw null;
            }
         case DISGUISE:
            new com.yiyiaddon.e.o.c.a.b(this, this.a).v(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2lkjzwzkrcg14","oASpjMYv/fZEf1hoBitluTFhqZvqOIxYpX0nsuYymnY=",8916358831182773661,-5702837598138700172,-7999612810485763851,9097985368283404996>()) {
               case -912944253:
                  break label35;
               default:
                  throw null;
            }
         case CHAT:
            new com.yiyiaddon.e.o.c.a.b(this, this.a).w(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1xbn9194o0vln","rt9TsNYMeFn0DKVqArbAq3vBiu3k/8ryidHP4BRngVY=",6873672628821314151,1607652833318125040,-5522070183857907658,2804049692577318137>()) {
               case -710880906:
                  break label35;
               default:
                  throw null;
            }
         case ANTI_AFK:
            new com.yiyiaddon.e.o.c.a.b(this, this.a).x(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sv4fnl8rehmxk","n9p9IeCH5KG7DQhwRkUbDX2OdOLlr3GgdJnmSSazuOo=",-3755957751493606067,-7431319402879394692,-5559647112155020264,-277313684229101651>()) {
               case -142227306:
                  break label35;
               default:
                  throw null;
            }
         case THROTTLE:
            new com.yiyiaddon.e.o.c.a.b(this, this.a).y(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s19l912qq8g8bi","PZktvHVf51mbrTugPo8YHKyDrTnSX32NHagHzg2M04g=",-3194298086992737608,711505702618693899,1709721696977379361,7365534265556813954>()) {
               case -1481548982:
                  break label35;
               default:
                  throw null;
            }
         case ANALYSIS:
            new com.yiyiaddon.e.o.c.a.b(this, this.a).z(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s188c1hq459us5","jaTuWJ6zuaFOBOhfo7DgfffTRuMiFQuDq94hI9zvsho=",4368142784769839056,1563980793227044952,-888588088484711099,-133417176885207888>()) {
               case 2032345392:
                  break label35;
               default:
                  throw null;
            }
         case HUMAN:
            new com.yiyiaddon.e.o.c.a.b(this, this.a).A(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s56rpytw8j97s","/VzPjH00qDS/HeX9TXU5bPLirdPrAN28AjAa0A/lMOo=",6141900985639996868,3197140929462046916,9177538374856819198,8935921416019507092>()) {
               case -52478066:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.o.c.a var6 : com.yiyiaddon.e.o.c.a.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1r7i0zwlsk5g1","+D4ucgrUI4zoDc+ame1At1sTfmy5dRtgUzx7RhKtolI=",4137358969285955489,-6704654986783598424,-6594147787948128317,-1742070504755362455>()) {
                        case 756129145:
                           switch ((int)com.yiyiaddon.m.b.a<"s2r93jnmxhaox7","UKoSykAuHeZwisX+GEx7E44R3wXOr/Tr1iOSNiVj5LM=",4603513370098076985,-8609116025205269222,-4690252295914917617,-2064099953684250091>()) {
                              case 582080508:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"sowsrm21b61c9","b9SIeZJkqt8LN0rleal5Qvy5p+GBMe1lzQ4yHYt+7zg=",-6477147392730042595,-7340796315453233956,-715246037683383755,-5944731411630240800>()) {
                        case -9179280:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               }
            )
         );
      }

      var1.a(new com.yiyiaddon.l.c.f.a(this, var2, 24.0F));
   }

   private void c(i var1) {
      var1.a(
         new com.yiyiaddon.l.c.f.a(
            this,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s158q05f3fycsv","TKQCkU3/5PvoyIR5m6QHkLTIrOxqC5VWIYdIuPZ2SBQ7iW49",-8304484228442871498,1959044574829344896,3745789734503642497,-3447697272544567445>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s3iftq53gx0ie4","09eO4oYgLdvA72Oidx90dh0hJyGVSTl0AaZFPnOvgalRu/swtY3NSyCIfxy3ad+AmzDYwTCKx5XadfKzVE5/Yhr0IBsSH0+mQOG/X+tnl+paO5dtQy00smAPTR9GrqjS/55MNQ==",-5752057968461640234,-830584608243055604,-6052001716345975592,4977923167811278596>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.a, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s2txy1s89foj4o","0IwtwuFzsTQeM5roZMS9OZU9zxIwnvuCSfb9VOiyAm5xTuyP",3971015917681515328,821074404284397680,2178968816540281060,9123279462455835338>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1qpp2r0jmyrzt","evssXVkG7SqdZJdncQBtfb5iAjp6woLMnjg50aGG4RA=",9172962164348969168,-356945235683439049,-13611074237571240,-1337080727280089315>()) {
                              case -1134875250:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"sed57a4vim3tx","OwvTkXOfnHojx9hto+twwAbxO1vFXzUTWNMdY1V6POk=",5941260976164734376,-5539060610035487469,-2510317166542677820,6737985039095646913>()) {
                                    case 1658949303:
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
            ),
            24.0F
         )
      );
   }

   private static List<String> a(String var0, float var1) {
      ArrayList var2 = new ArrayList();
      String[] var3 = var0.split(
         (String)com.yiyiaddon.m.b.a<"s3kakzsypc93j","piOGrPYB+dYIk7vDIF1Dmx6qYWmFmABByfdKOb8u",2516577776198704958,3042459980516181896,-7089859568240650573,-3463816698155738372>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s465m550zjin6","Am6uW5/FIk5Qtk25mKf+UlFkamIqzf8snojHhlPmv4U=",-353786332320671515,393547846555594705,1280052083862094596,6815406160290821913>()) {
         case -697309380:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s37fi0ek825fdb","/croC3nWvgqs8Jkni5Hwxi/lC6e/Z3L/F9r+EGNZ0pE=",7839742646203411799,2502462645436109892,-3459329626423670969,8579536643048933862>()) {
                  case -25719269:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s3fvwl5e7dbu4f","jgeufWoxLM4kvgxx9Blx2GF5hg96Fkv8NE6v1lgUVTg=",2894412819094113930,-621941921925023990,6950776239766116198,-7079310579835979428>()) {
                           case -1040877437:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s2lw98xmdllvyg","ftiqEt3dV0xB0uVdkl/Vo/APGijwXoxRFBK4kg==",5085242218599442731,-7413473062552803025,-3997426149370966976,7314890876059396943>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s2nt44cciw5o3f","oJcfXXJj+fg5YiiopwCTAbJ1KjTuao4jf7YWy6N5egs=",6206595837809676967,6953390090515343269,-2247077725137467239,-4090772735851500079>()) {
                                 case 1866074681:
                                    break label68;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        StringBuilder var7 = new StringBuilder();
                        float var8 = 0.0F;
                        int var9 = 0;
                        label46:
                        switch ((int)com.yiyiaddon.m.b.a<"s37vkcvmzmvyi1","xz0IIm35MAa+7hn2HPqPApq/8flqZZXqR31gC3FyoNU=",4758412626470563477,4595618567617067551,5065844347429327688,-584660813100151314>()) {
                           case 703503064:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s13he0kbv8yymw","Jc9HJQcdDSeT5SMZobti7gS3rckLzDInTuW/NtSA2bY=",-1156245954815164563,6144415944681529858,-3388749865961262326,630349858240603899>()) {
                                    case 748248196:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s25l51j31pb57f","jc9to1A2C0AL2d9ws7Pcg8iiEkxzHHpAU62jozHTKNs=",-7677768542022812922,1508733044686891042,4503758493917514696,8041608490311122219>()) {
                                             case -186749239:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s15efsegopd3v","FqHaMosaV/ev3iynbNkHwCqYdK45eVDoF8C1d3wqut8=",336247171561733048,-6067272325571229132,-8425752775925780690,1397750266052568375>()) {
                                                      case -1504076549:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1tj9ka2bsfqzn","mihX+rjlU/aXmrUG7KmYKUCXvupPxp3fmjeqYeotbzs=",8938060718449338043,-2695964286989780644,6372509718947856689,-2603734927946527869>()) {
                                                            case 569278678:
                                                               break label64;
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

                                       float var13 = com.yiyiaddon.l.g.d.a(var12, 10.0F, false);
                                       if (var8 + var13 > var1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1zfxkjyq8zqhi","fXo6y6DcurhSMrGFbbR1GIf5dpHiJtJWEVHOvZYgTTw=",-7886897454697423222,-6830662945481122124,-8545366791681847969,-3294692521834888930>()) {
                                             case -2057280051:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2e8sergugkbhj","BsL53CK8lskKmus7k5crCKAk6Il5rpGkhRl75rXi9yE=",7394026661799031326,3277489624811724000,-1378348964469173634,-3892867789785234151>()) {
                                                      case 1871626689:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"swmyoumuw1uby","Z9gsZ5QSoJL+ItB7VkdnmTXN7hd5/OFQP0om0TjkEtA=",-6832891158284826689,8126807509038538338,-2130694319293678142,8394337814496613159>()) {
                                                            case -564694337:
                                                               break label58;
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

                                       var7.append(var12);
                                       var8 += var13;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3kdsg17l8db6o","MCEe7qHyT8X4OHXAAdv+YnDTvAQC2E/Jjd3hciI1nNg=",2174277332031726973,8133246087344951287,-3673359943792893132,7674998049087694026>()) {
                                          case 874460259:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s3kig8pkp8qvgy","618MrDOpGBWB+hf/O2tWhd7Cf3/BCpe6iywgJWBtZ+E=",-5930816433237957895,2967793208865569561,-4372902106229210335,162925579673144469>()) {
                                 case -1250246896:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s28zpycx54246c","M8wTXETNtusuNdi7udZbCBdI/XbTTyUmrEm8tagP+4k=",-5720667089770986858,9033262939113896308,-121075684569709162,5313447119960634786>()) {
                        case 36042415:
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

   private final class a implements g {
      private i a = new i(6.0F);
      private String aW;
      private float m;
      private float n;

      private void u() {
         this.aW = null;
         i var1 = new i(6.0F);
         a.this.a(var1);
         this.a = var1;
      }

      @Override
      public float b() {
         return this.a.b();
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.a.a(var1, var2, var3, var4, var5, var3, var3 + this.a.b(), var6, var7);
         this.a(var1, var2, var4, var5);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE, var6);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE);
      }

      @Override
      public void F() {
         this.a.F();
      }

      private void a(String var1, float var2, float var3) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2f8jwfc0h66fu","oMB081V3A9xtH6cXNta8fmNQE/C82ZBVmClSS8vSKIc=",-6067238996686560597,-2798743298711888088,-5701411757957433420,-76347063537573109>()) {
               case -2064545062:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3630du11mrb4k","f3s3BLl/BYwFbqmMwg32innprMsAqd5B1ENQrXUA3Cs=",-2392770041543546005,-7832303115624015196,-42329035774666045,-7386152818580479276>()) {
                        case -599361017:
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

      private void a(Canvas var1, float var2, float var3, float var4) {
         String var5 = this.aW;
         this.aW = null;
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3vo1yn4pv0esg","82Ay97023lICZO44kLObeMh+uJnLAV1zyBKYIYeomeM=",-8584376541563931311,-5967500950832745613,1439729551257641692,-4424067883514884695>()) {
               case 1052797436:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.o.c.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3r4gyz4jnm157","MRx60yVHaGO7bowaZne0of49faoYansglEo4UudTWuo=",1449559514579098787,385740361984756098,-4811721469340044197,8258087620745949361>()) {
                        case -1800405200:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s13k5hqvxw8ug5","xQiij5vTy79bylAFcQo7ya2jIz0TI5DS1xvZNoYt02w=",-2770933835816886967,-67412969734920159,-1468274243302642697,-3799268351617122857>()) {
                                 case 1817961599:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s1kvnkybzgz7zf","7T8Ti6nQGxCTcC4r+HY35CQF5n/MeP+zG01YntYF7Q4=",7782277845831698374,5493617147949452772,4999378952882927065,-4465096605522156142>()) {
                                       case -1616002287:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var8 += 12.0F;
                           float var17 = var7.size() * 12.0F + 12.0F;
                           float var18 = Math.max(var2, Math.min(this.m, var2 + var3 - var8));
                           float var11 = Math.max(0.0F, this.n - var17);
                           j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                           j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                           j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                           float var12 = var11 + 6.0F;
                           int var13 = com.yiyiaddon.e.o.c.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s1rv41a5lmsima","fRO439wl4OS816TKIw2AZzSEWRAQ1bDHKpoF3W5ORz8=",7303501132324440916,3917918422994000752,-4100457144776879905,7769424780412557105>()) {
                              case 1878815313:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s348tz037xvv55","ay798ZINxJgR6xK+YxEl3DLgT1+25Ong2Or/3pRs8yU=",5495509520347824927,8827074344821419038,935610050251921715,-8372394004571351736>()) {
                                       case -675088219:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3aspqd14skic1","4s03jY2erqFx3qW7iX+LvJ5vrkQ4KG2iQAH75jAYoo4=",-2944510612130467783,2725802974391361723,-7598914089246460810,4604861089395454604>()) {
                                             case -8427637:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s19zqa6lgo8xfw","JnDKJ2+c1VpFK/mrBI5rtZ6yPTl9L0mGLn53LbVIkqk=",8366446684425714494,4388219726125437755,-5765114169380740479,6241091441357407106>()) {
                        case -969788297:
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

   private static final class b {
      private final String[] X;

      private b(String[] var1) {
         this.X = var1;
      }

      private static com.yiyiaddon.e.o.c.a a() {
         return new com.yiyiaddon.e.o.c.a(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s28z1q7zc3vtsv","gXtt0/nN0mhPAdHyUpklu4nJ6hp5L0mmLaBO4Q==",2447814220833095236,-488291669038122639,3149495911930784791,-4722622754185328408>(),
               (String)com.yiyiaddon.m.b.a<"s28z1q7zc3vtsv","gXtt0/nN0mhPAdHyUpklu4nJ6hp5L0mmLaBO4Q==",2447814220833095236,-488291669038122639,3149495911930784791,-4722622754185328408>(),
               (String)com.yiyiaddon.m.b.a<"s28z1q7zc3vtsv","gXtt0/nN0mhPAdHyUpklu4nJ6hp5L0mmLaBO4Q==",2447814220833095236,-488291669038122639,3149495911930784791,-4722622754185328408>(),
               (String)com.yiyiaddon.m.b.a<"s28z1q7zc3vtsv","gXtt0/nN0mhPAdHyUpklu4nJ6hp5L0mmLaBO4Q==",2447814220833095236,-488291669038122639,3149495911930784791,-4722622754185328408>(),
               (String)com.yiyiaddon.m.b.a<"s28z1q7zc3vtsv","gXtt0/nN0mhPAdHyUpklu4nJ6hp5L0mmLaBO4Q==",2447814220833095236,-488291669038122639,3149495911930784791,-4722622754185328408>(),
               (String)com.yiyiaddon.m.b.a<"s28z1q7zc3vtsv","gXtt0/nN0mhPAdHyUpklu4nJ6hp5L0mmLaBO4Q==",2447814220833095236,-488291669038122639,3149495911930784791,-4722622754185328408>()
            }
         );
      }

      private static com.yiyiaddon.e.o.c.a a(com.yiyiaddon.e.o.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.o.a.a var1 = var0.a();
         return new com.yiyiaddon.e.o.c.a(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s3s1mmt7yih1at","6roL0RYt9oVAfe/e5aHbYAZGMq9tClg4ys29+w6N/57zJmccV38=",6483375500642509188,-5633433834744788664,3096225499016563111,4079087455476966251>()
                        : (String)com.yiyiaddon.m.b.a<"s2k1d86oed4vij","qaVUa7gh34rrwZs3EK3QCtUwvViHMvB9p3Zhis2K/n5uF6JGA9o=",6441305535631311263,8731527616263024923,2151364230929162596,984050859993062393>()
                  )
                  + "",
               a(var0) + "",
               (
                     var1.ez
                        ? "" + var1.oR + var0.cT()
                        : (String)com.yiyiaddon.m.b.a<"s1u7m54xdfrfx3","L25J1MtsvAwO3lN4ZGfoZnhBYbmkGHI4KgdoJVd/ygyQGZIh",9035874746916327211,-7728887060831036912,4142378698468960942,-3218044730472380617>()
                  )
                  + "",
               (
                     var1.eF
                        ? "" + Math.min(var1.oV, var1.oW) + Math.max(var1.oV, var1.oW)
                        : (String)com.yiyiaddon.m.b.a<"s1u7m54xdfrfx3","L25J1MtsvAwO3lN4ZGfoZnhBYbmkGHI4KgdoJVd/ygyQGZIh",9035874746916327211,-7728887060831036912,4142378698468960942,-3218044730472380617>()
                  )
                  + "",
               (
                     com.yiyiaddon.e.o.b.c.eI()
                        ? (String)com.yiyiaddon.m.b.a<"s18fhvx5anxa8g","unD28O+oRiWw7PlK0ie6wI0Y3k59cpLt1vrE1uJ1axL4aR1W",2892364633620538136,-4912408938206312027,-3497235026825348870,-7830745709154718898>()
                        : (String)com.yiyiaddon.m.b.a<"s1jvlfnt271324","uOjzyL9HrZ6u5chpk/V6HyefuyMCEiFPN75HhR1uzV80VPWv",5709726914984741190,5624378279331017112,6915370669727069332,-4481793706838555871>()
                  )
                  + String.format(
                     Locale.ROOT,
                     (String)com.yiyiaddon.m.b.a<"silyq5tf4ecar","kjOdDeQZlDBdp+pqvUgIuD72gmEIiwtK9OEsNsx8Q/OrZ72D",8856263475579997966,-1460231259508940477,-6962017254570446433,-2192787365975180237>(),
                     com.yiyiaddon.e.o.b.c.g()
                  ),
               as() + ""
            }
         );
      }

      private static String a(com.yiyiaddon.e.o.a var0) {
         int var1 = (int)Math.round(var0.e() * 100.0);
         if (var1 >= 100) {
            switch ((int)com.yiyiaddon.m.b.a<"s3td9ry2htw93t","2AziiNd1mPDHdWV++WLI+csqWMy9B0s1aoUpWoUcz4Q=",7125925579972096418,-6314320224625399430,5567739519937379043,-628414370853346271>()) {
               case -1803364598:
                  String var10000 = var1 + "";
                  switch ((int)com.yiyiaddon.m.b.a<"svu2qrfy1uh5r","wXxhyG1jFuMS92sa0tzQe6OwrU+w+5x9WzvO+c91wLY=",-5349452130635180097,-601852559432448644,-3644226172324927551,5633533567299985577>()) {
                     case -491133276:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var2 = var1 + "";
            switch ((int)com.yiyiaddon.m.b.a<"s1oph689iraesv","OGS7axoE2JbtqTCyE1B4zZPJLv5Wqs0/uoxr7ul7g1o=",1878921238225152660,3740751458584134467,-101688891578704018,6411179942352573594>()) {
               case -943111429:
                  return var2;
               default:
                  throw null;
            }
         }
      }

      private static String as() {
         String var0 = com.yiyiaddon.e.o.b.c.eU();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sxlmhymk7rm2v","HV2qAoEHHJZ0H27UthSa7uAPXbmO+s09OdVEe5BuGQM=",-3851193496641525536,1031044292089637754,-2029720552662052080,1560622312997133701>()) {
               case 517535222:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"svtiasty34uc1","6UWemBKRIaAtDy0u/sMBQMQPsTGYPGKeB6nfHGOKD2Q=",-8534074318216678665,-6149881805116095039,1524144462703730187,1466012535715928138>()) {
                        case -214637557:
                           if (!(String)com.yiyiaddon.m.b.a<"s2iv4lks5iqruq","WrXMqDDyzPd3bZYwmMHdOdYM5n0F7ULRHK/PdYvAolw=",-9216930486560564252,-3888545786960679060,-3889514421178995178,-7274573257014920205>()
                              .equals(var0)) {
                              if (!(String)com.yiyiaddon.m.b.a<"s2gdiabljy412c","5tt6r2YcqImogLlDSyLsQuSVoeQzx6gX4WQHZhgx2tuotg==",-8033576683084418859,-8532266504156314403,2664307205408230457,3745393742311423377>()
                                 .equals(var0)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ic8f6nhhrmoc","hv1F4rDXxR4H305FDDAHAaS+uHFq4K7YSnt4fp2v41c=",-8161913211004100750,294214372013587896,193918459104725482,-7520887163025358925>()) {
                                    case -1075959506:
                                       if (!(String)com.yiyiaddon.m.b.a<"sqfbpvykg1zq2","JVASxRaOtQNcWaSVSnsumLgHw/ww6j21sgzTh2+ZKaY44g==",8960594823319483115,5532063555080930744,3421967466274373696,4139867932146398087>()
                                          .equals(var0)) {
                                          if (com.yiyiaddon.e.o.b.c.eF()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3rv9rp9psjxn2","0jR6BtqIWjRhzFqrAhCv1HoG4xVxEF5Spv0pWEAKp0Q=",1389277709814581750,-8332821180540353336,-6771462696056313435,-66432229369310893>()) {
                                                case 555227970:
                                                   String var10000 = var0 + "";
                                                   switch ((int)com.yiyiaddon.m.b.a<"s38huj7infal9c","cZjxvgMkt8m/iGKvvZs+aFi/NyCV24EqwhI6YqGgTio=",5721795176273981506,8931468660572550096,-701263253527893003,1274007730898616994>()) {
                                                      case -58033016:
                                                         return var10000;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var1 = var0 + "";
                                             switch ((int)com.yiyiaddon.m.b.a<"s1brt69hjzd0kr","RUYOirug1CIJVQMoacQ4gQoM207zWxbrUsKL6WLggfI=",-5262312041314379334,7427786325636444144,4515974519973396865,-2677247925777914817>()) {
                                                case -638323990:
                                                   return var1;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"sn4apw3l72ebo","pZa+JoBCbnMzmBFdzSqEo+X70jKvW22WfBdR2+CJasE=",6931559090611036047,6658752245094965272,764032823595155476,3596798718675274065>()) {
                                          case 400037975:
                                             return var0 + "";
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return var0 + "";
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s35tkb8ycs99g4","a9zpRlryGzia6443eFp64XCqUmW0JGhdHL2XaKSNARw=",-2580705246687190004,7276868183678510711,6422180652888240604,1927921842639301199>()) {
                              case -1948632442:
                                 return (String)com.yiyiaddon.m.b.a<"szhd21o9r8t3x","U/blMyQ5q2wEJ0xPVEtfkeotC0iouQRk8o3znYo1CwsQnpvd",-1899325839208403206,1842285878576371562,3568347447347420808,-2117269761085275818>();
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

         return (String)com.yiyiaddon.m.b.a<"szhd21o9r8t3x","U/blMyQ5q2wEJ0xPVEtfkeotC0iouQRk8o3znYo1CwsQnpvd",-1899325839208403206,1842285878576371562,3568347447347420808,-2117269761085275818>();
      }

      private String c(int var1) {
         return this.X[var1];
      }
   }

   private final class c implements g {
      private static final float dy = 18.0F;
      private static final float dz = 6.0F;
      private static final int py = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.o.c.a var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"scy8ywnfn3wat","anohCMxOzFzQpyIUyJW/2bgQ12n7Fcw9NYoiYA3+L2E=",-4382709912587594527,4576414506135882897,101944508414401747,6334721885844040262>()) {
            case 1390593805:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ij03yu1ymyum","ShjETbNRQsA4KusYih/NIeBMQ3h/XC8Vk7L5IXs6+Lk=",7021023819979769961,637849521675018188,4510197154433011234,-1057187848601248645>()) {
                     case -1375623119:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3t9k0yfprh2c0","DO2jCfbz8xYIInasiGIqXX3PCaaDByJ7ltu7VfIxpWo=",3616116036933373555,4166869170619706686,2977063959848151656,-3146669472620899604>()) {
                           case 1351652782:
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

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6) {
         com.yiyiaddon.l.g.d.a(
            var1, com.yiyiaddon.l.g.d.b(var2, 11.0F, var5), var3, com.yiyiaddon.l.b.d.c(var4 + 9.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var6
         );
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

   private enum d {
      OVERVIEW(
         (String)com.yiyiaddon.m.b.a<"s26dzhyebgghxk","/FhiE2rbWmsKgGwCsjSyUnnTdb3URinnvVQPI3XQQrQ=",7806729779251475417,-7342055667721474437,8531852008558266312,-4964817594874051712>()
      ),
      DISGUISE(
         (String)com.yiyiaddon.m.b.a<"s25g71xz3r5avu","3Bx8C7sQoaVDIG8oUN5TRv4Z9sZl1D/D/Qfyeo2ERVjOh5A37I0=",4209470350176547486,-2107981122360643690,-3885065718001662552,-1420610228549119216>()
      ),
      CHAT(
         (String)com.yiyiaddon.m.b.a<"s26im4sla4y4hj","KoL4X9iS6Ntpk97fDgMYYrbaJjLZqyRCB9WlO3gK7/i5uffV",1240754492488066504,-6793312974596539648,666658881462255421,5072714145040487275>()
      ),
      ANTI_AFK(
         (String)com.yiyiaddon.m.b.a<"s2o6e4mtfv19hc","K+QHSlb9jiKv637yXtSWvH2rDU4sQqgn05CuY90Ucg7Gkg==",8263012576255787658,7635737036805682247,4377406561499412062,-7889356383309135468>()
      ),
      THROTTLE(
         (String)com.yiyiaddon.m.b.a<"s2kcrdft0idee7","DeyJjiHtAitDeekLV9UE082Rmo3t4DO8jnvx5asAipd6Fnxc",-241221955528662459,-192117510087949873,-8248589919531260343,-9135831953655015222>()
      ),
      ANALYSIS(
         (String)com.yiyiaddon.m.b.a<"synnvda6svffb","9gZZp9IgUvtoanZ/1IMJcFU06Firt4TU3xwQe2AHs2dwplmc",317354112540679885,-3966360926717981044,-5844548489316823561,1412064164398580128>()
      ),
      HUMAN(
         (String)com.yiyiaddon.m.b.a<"s1gjtwnzwo7gzd","nks1Wx43P9P62FVNzutI82Ej2W+IVJFeZTTo8BwM5TI03WT4",2341213794901162016,-4101379923261806952,2026394389150668297,-6259476657146560728>()
      );

      private final String wM;

      d(String var3) {
         this.wM = var3;
      }

      private String D() {
         return this.wM;
      }
   }
}
