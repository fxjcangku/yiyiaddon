package com.yiyiaddon.e.h.e;

import com.yiyiaddon.l.b.n;
import com.yiyiaddon.l.b.o;
import com.yiyiaddon.l.b.t;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public final class c extends com.yiyiaddon.l.f.c implements com.yiyiaddon.l.f.i {
   private static final int eL = 20;
   private static final String kY = (String)com.yiyiaddon.m.b.a<"s2cgzijzijm9my","jtDavBEs/843lU23QeKPa15r0N+DPh6/ViXQp6wo",4043030926782023052,-5329456427128203196,8412000850380309830,-8799407974981719191>();
   private static final float aN = 26.0F;
   private static final float aO = 72.0F;
   private static final float aP = 11.0F;
   private static final float aQ = 24.0F;
   private static final float aR = 24.0F;
   private static final float aS = 11.0F;
   private final com.yiyiaddon.e.h.a a;
   private final o b = new o(
      () -> (String)com.yiyiaddon.m.b.a<"s15uzm0huqpx6g","QtLWs5pr63afXB+koedhr7Ahik5zMbkBZG39ZarORkRBp2TplzhJKil0n2oPXAs1dQ4LUxuj3tEVZdRfsMrsjQ==",-3907176071074888726,8533072295868940922,-3034792341085173744,-2845021802201046692>()
   );
   private final List<Object> aW = new ArrayList<>();
   private String kZ = (String)com.yiyiaddon.m.b.a<"s35vgcodt412sw","o1vT0EcetvSn6oWAnsIhfgEtT2l/Tly/ax2dwg==",4725904431893986145,174509337510669958,7541757353502251227,-8778447444102549051>();
   private int eM = 0;
   private int eN = 0;
   private int eO = 0;
   private boolean bx = true;

   public c(com.yiyiaddon.e.h.a var1) {
      this.a = var1;
      this.G();
   }

   @Override
   public com.yiyiaddon.l.f.a a(com.yiyiaddon.h.d var1) {
      return this;
   }

   @Override
   public String E() {
      return this.a.t();
   }

   @Override
   public String F() {
      return this.a.v();
   }

   @Override
   public void a(float var1) {
      if (this.bx) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s28w06w5uir1ex","E7enqEu40/reraytGBblaerxAKddK3uq7K7kYehrJPA=",-6555791553648601702,5652233717993084559,4596794857457903203,3836707793691556864>()) {
            case -1553355749:
               this.bx = false;
               this.dv();
               switch ((int)com.yiyiaddon.m.b.a<"s1f4mlxrqfu9bf","Bag9xtm+pVFO8sj0ZpP7VFwVuEO+DWyGK2Yhm16RHWM=",-7811399127780341764,2345109248083229068,-2867333372130133065,7056754714348966406>()) {
                  case -1859601529:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      super.a(var1);
   }

   private void G() {
      this.b(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"s2rvm6t1rfnz9m","upb4qPqLeqRpplxQDuWH7TVnunNeqSvMaieV2s3UQLBV8gAIcAtogIQQcDieDX6/LaE=",-750641919016976581,8167276671867931893,-3733579125904390090,-4998253732258990845>(),
               this::cP
            )
         )
      );
      this.b(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"s343b79d8y7jiy","+prnIOxvGbf70L7hHPsh8vXn6SvRsqhBjhNlkM7lGXlCTngKb16RwA==",-8476421961212663610,-6704640074674048859,5191228290426666320,-6513386639138087650>(),
               this::cQ
            )
         )
      );
      this.b(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"s57bfjfa2u5nc","fOfuTBPu9Nl84I9onuE0yEPAXbU5IJ7IzkE7SL7udP1BR3Jl82nMEz5aKPM=",-5806522237171746228,-225979669405860349,6615624288012684757,-1847754559342074811>(),
               this::dt
            )
         )
      );
      this.b(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"s1xgf5g0haqw1s","hEDInfWZXHpndWVBsd+WGQK41xWi0M9F8n3oe767p3UOXsUF",2369177987097186897,-5635793337278700827,-644742448423325683,5305638528389167533>(),
               this::du
            )
         )
      );
      this.b(new w(this.a()).a(24.0F));
      this.b(
         new t(
            (String)com.yiyiaddon.m.b.a<"s1osmllsqnn3vj","bIaY8Pnlky/TsKzHpcuqdR6I0sjb1nUZoi8qJOP0esPf89DLDa6w7tuCnkIPHMn8ELY4KJ0KiaZLtW8a4jXVCUEbNeF0iQ==",4918589317163782778,-5243359488699930789,-1678754473635305682,-8338510878435300500>(),
            new m(() -> this.kZ, this::W, 64)
         )
      );
      this.b(
         new com.yiyiaddon.l.b.b(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s368qq4qn9uyca","tmOAj5uBBNjG74kFgEgJdBJ9fT4B16BEptGsqoD6GQU=",2465097076999630636,4289148106429391018,-8696178458399005246,-2351309367985805171>(),
               0
            ),
            this.a(
               (String)com.yiyiaddon.m.b.a<"s34hm1as8qqvmf","3vJGRHjU1cyc3x60WBWFVFEHkqPHhDfeDXrexLmYaqs=",4487118199280063599,3019821556556543906,4941602790764781409,-442274218063990934>(),
               1
            ),
            this.a(
               (String)com.yiyiaddon.m.b.a<"s1di5royzpzx9","5qbH2Ji+R6ipDIeUwuCA7yOKdRIXT1rPTYuFMLtftjw=",-5273455123592255325,-8414671321632779176,4970309635707426692,-1377855836085892375>(),
               2
            ),
            this.a(
               (String)com.yiyiaddon.m.b.a<"s2txexvhzwdnkm","OxTpaaaSHCQsydIR5tiyO1/H6LiZ/nU/TXsHpvcdzmw=",843070128611991566,-8173437292197229375,1693471314277659659,6562990496405098830>(),
               3
            )
         )
      );
      this.b(this.a());
      this.b(new w(this::bn).a(24.0F).b(11.0F).a(true));
      this.b(this.b);
      this.b(this.b());
      String[] var1 = com.yiyiaddon.l.h.d.a(this.a.c());
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1tn6jrn08df4v","9oPPgeH0uy/Kc9qLRuYYLtfIK7RLJ87RerzTtetDhFs=",-7023956861353530086,8483483525423960384,2244069100115444327,-6560287245147554287>()) {
         case -310955875:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s10y0qpaib0mo1","cYc1FSSEIdKXKqA79cXUBlTibS1Whmu5Uzp5ygOlQEw=",-8858373955071278700,-2228834609317580846,4900551124733280367,-4688122726353312128>()) {
                  case 448519691:
                     String var4 = var1[var3];
                     this.b(new w(var4));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"szrt35344uct7","LnpikN0P20FEdYALsryuW32FQ07fBlZ1ZnEl8ZQSDBw=",-2340756498988705253,5044832051135744785,151515598462602279,3953880901284783043>()) {
                        case 1116586405:
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

   private Supplier<String> a() {
      return () -> {
         com.yiyiaddon.k.b.a var0 = com.yiyiaddon.k.b.a.a();
         return "" + var0.dH() + var0.dI() + var0.dJ() + var0.dL();
      };
   }

   private String bn() {
      String var10000;
      label52:
      switch (this.eM) {
         case 1:
            var10000 = (String)com.yiyiaddon.m.b.a<"s34hm1as8qqvmf","3vJGRHjU1cyc3x60WBWFVFEHkqPHhDfeDXrexLmYaqs=",4487118199280063599,3019821556556543906,4941602790764781409,-442274218063990934>();
            switch ((int)com.yiyiaddon.m.b.a<"s1355iwdbxdbdz","BwT5VIXrJPVPRQcRUMQyB4wyON7JWtFcehyovghBzek=",4774029648846578201,-8693742019732174212,-5342077145843024043,3547312103442036634>()) {
               case -1405034219:
                  break label52;
               default:
                  throw null;
            }
         case 2:
            var10000 = (String)com.yiyiaddon.m.b.a<"s1di5royzpzx9","5qbH2Ji+R6ipDIeUwuCA7yOKdRIXT1rPTYuFMLtftjw=",-5273455123592255325,-8414671321632779176,4970309635707426692,-1377855836085892375>();
            switch ((int)com.yiyiaddon.m.b.a<"s24gd8blee46fu","jbQTW/wSsHfDNAUBASZaCWJ4cjUf+KoKbs4s4+HwXD4=",-3200263663018491720,-4616139615262209858,-681279666985637690,3064137336873427523>()) {
               case 846377310:
                  break label52;
               default:
                  throw null;
            }
         case 3:
            var10000 = (String)com.yiyiaddon.m.b.a<"s2txexvhzwdnkm","OxTpaaaSHCQsydIR5tiyO1/H6LiZ/nU/TXsHpvcdzmw=",843070128611991566,-8173437292197229375,1693471314277659659,6562990496405098830>();
            switch ((int)com.yiyiaddon.m.b.a<"s20yke5p0anczv","2TMdq6PjeoFp3fTIOusy/+gZhIFqB6prixYbRg7e2Vo=",3879461080147650489,2462726030389317650,-282092086712805523,-4661903671000467988>()) {
               case 179473316:
                  break label52;
               default:
                  throw null;
            }
         default:
            var10000 = (String)com.yiyiaddon.m.b.a<"s368qq4qn9uyca","tmOAj5uBBNjG74kFgEgJdBJ9fT4B16BEptGsqoD6GQU=",2465097076999630636,4289148106429391018,-8696178458399005246,-2351309367985805171>();
            switch ((int)com.yiyiaddon.m.b.a<"s205wb4frd3183","uoeKn0XPN+mzj6VKZ3UPspzHYbGVE73JdfBVkP68WV0=",1153340663583326729,3115415007093664429,7027404162165468509,6800092572952857264>()) {
               case 954579239:
                  break;
               default:
                  throw null;
            }
      }

      String var1 = var10000;
      if (this.eM == 3) {
         switch ((int)com.yiyiaddon.m.b.a<"s3419bkqzsdhc9","k1QDbpeq8jETtYQLxhHLqkMQnyEdDNYmPzrx/YK+JsA=",-296359747707596073,4434988083572052728,4023517948592389224,-5281157799247164335>()) {
            case -1073528570:
               if (this.eN != 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s9n79sijadtg2","sYZdOj5mMVvMFoC90aUT4Opy+CLK2rMr7B1dojLEwGM=",5811030844788750732,-6812236739295717406,8190399405917698201,6623459302843339221>()) {
                     case 497972880:
                        String var10001;
                        if (this.eN == 1) {
                           label34:
                           switch ((int)com.yiyiaddon.m.b.a<"sbsaku1l3ti0g","0xDIiEDvcZLRy3NLMTBwA+3Mntcb0HwVG/WGmPNBAVw=",-7197126231772428109,7809765989715159799,8691128727664559609,1001434364898121897>()) {
                              case -2041645307:
                                 var10001 = (String)com.yiyiaddon.m.b.a<"s3bvnj26aoyjt4","YvBIwFhX8hUosoppo8nXfIJts61xmqnRmUgZR4fJGpeSFKvF",-6953670950070886381,1588852526201881962,-6989334972254113662,-7400482642693593573>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3dq5dne94l2c","TNSiCcEcKv6V79l93FrrYVLTpY4njVIYhJXNjpCTkFQ=",556232110986228206,-2278195265156135975,7738325191249097694,-9183675812616704707>()) {
                                    case 1753398184:
                                       break label34;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = (String)com.yiyiaddon.m.b.a<"s14jkmu91hsglt","Nc/mB87ufP2ANN5CC4nYSBVW48NJ59jQnqRtxKoeqLJ6Xg==",-6779498744881971804,5468594975993657555,2800670795450145304,-7009885715626420529>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1svtrpgphtddf","Iqkkqhm1+QzKN1wcGkZirysKGKzlfSflyDF1sL5hmwo=",-4603601235159703697,-1135603209589617942,5078863470633777283,4939435683222559990>()) {
                              case -1765743056:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var1 = var1 + var10001;
                        switch ((int)com.yiyiaddon.m.b.a<"srilvgjfh7phf","x7cCwCnUyFHFulW1UoE/7pJkyeoqFfERk17xMsBEH98=",-5708204070349229953,-5884501581382862322,5333326507728171984,-8496342363176240946>()) {
                           case -1519956773:
                              return this.aW.size() + var1 + (this.eO + 1) + this.aM();
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

      return this.aW.size() + var1 + (this.eO + 1) + this.aM();
   }

   private com.yiyiaddon.l.j.a a(String var1, int var2) {
      return new com.yiyiaddon.l.j.a(
         () -> {
            if (this.eM == var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s19saon9re5ewh","r8UnEac5X5F+esj34/ct7+xHpB5/hKAHpL3kbq0jfFE=",1235729892386636938,6207968334085353336,-7477489768707825570,3966923248568559819>()) {
                  case -1664901649:
                     String var10000 = (String)com.yiyiaddon.m.b.a<"s1tqgi6q7mler2","h5sVJzeHyQZ6mezM+cAF1/tLttDOrgC/x8+0x9mCx8pW+Ca+",-9089152856674311897,-8133966743102510024,-7046305964261499674,8308849443314783413>();
                     switch ((int)com.yiyiaddon.m.b.a<"s3fz63uubfuuuc","Ifs3RuDmV1cB9EfzpLtUrW4gTkuBsMhc5yXqLHPVlAw=",1640543358377024657,-7248260284940540477,2025587048183866833,-5184507624252945984>()) {
                        case -1146697831:
                           return var10000 + var1;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               String var3 = (String)com.yiyiaddon.m.b.a<"s2oo14cgxoxxre","8J33DPmW1LZ21TbsUJKABQ/U+t50D+Wz3R5NQ7hqkVE=",2218893187071580849,-7252603363569885269,3394773773058044144,6638501144941666154>();
               switch ((int)com.yiyiaddon.m.b.a<"shryl38keschh","3mTW8Nq5GyVCP59gDj8o52LasnN7VP/GmROWTFQUb2g=",-6018954353683480729,-1962389437870888567,6335746439164823857,7067345465689664408>()) {
                  case 1317889983:
                     return var3 + var1;
                  default:
                     throw null;
               }
            }
         },
         () -> this.s(var2)
      );
   }

   private com.yiyiaddon.l.b.g a() {
      final com.yiyiaddon.l.b.b var1 = new com.yiyiaddon.l.b.b(
         this.b(
            (String)com.yiyiaddon.m.b.a<"s7wlebbvkjo0i","Kt3hM19BvusAJIuq8uRQ7OtfZVsWws9GftbBPBlbaJfARpNDx8U=",-4904519039131269570,9028063995763403667,-4304769698466120051,-2244149671862039114>(),
            0
         ),
         this.b(
            (String)com.yiyiaddon.m.b.a<"s2ibe3e9yugm51","ZxWJSktMd8fuDZdF/Buzt2Rdf05t2W2wmNb4wL4BQPETSVDOJo/hNA==",-6538279742588567633,8491320277636724551,4062670103655216619,-2424174697937990038>(),
            1
         ),
         this.b(
            (String)com.yiyiaddon.m.b.a<"s3mq3en7bcw6ic","8FnBp7piGw1N6JgWUQlOEXYHIFmn7eBws9wldaancOEX5hzcAkE=",-2531366475609653936,-2758081202946962740,4401915536926819896,5154057107093143392>(),
            2
         )
      );
      return new com.yiyiaddon.l.b.g() {
         @Override
         public float b() {
            if (c.this.eM == 3) {
               switch ((int)com.yiyiaddon.m.b.a<"samjcicwxbjpg","S8kZZ56uyZO+/BY3og/4UXlIh3G7zM7pC/mfZoTyMZ4=",1568496660444068255,836514145438918197,-2800072909722583866,-5449301318061793941>()) {
                  case 1270277524:
                     float var10000 = var1.b();
                     switch ((int)com.yiyiaddon.m.b.a<"s2tmfmeh83f8xp","uuNm8PjadXBjt62zgKY+EyoJejb5Hnwz/bUrhRI+y/M=",-233577789789181820,-2189385490597507568,9002844986134022007,8897634626530708784>()) {
                        case -623336343:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s3apv0sv2h493l","O3bViNLc9VzVU/6285J/kYGgVrYxGhoG2aNrAHp/2ds=",-1024894978548122764,-4250164546466132609,8891126228149351086,-3871717782142154331>()) {
                  case -146755647:
                     return 0.0F;
                  default:
                     throw null;
               }
            }
         }

         @Override
         public void a(float var1x) {
            var1.a(var1x);
         }

         @Override
         public void a(Canvas var1x, float var2, float var3, float var4, float var5, float var6, float var7) {
            if (c.this.eM == 3) {
               switch ((int)com.yiyiaddon.m.b.a<"s1fxof6xoaifvh","hv0yd8OIQl1AvaiZFq9K96J7OruBTt5w2b7r5+pdP6w=",2799141203314194621,-6029046613694884645,-5985012208777696082,1615539830510557510>()) {
                  case 1813498436:
                     var1.a(var1x, var2, var3, var4, var5, var6, var7);
                     switch ((int)com.yiyiaddon.m.b.a<"s2bwtfgq9ld4pl","7D1hx1Ddrb7DRDqeGUW+wKlCeX+TXkIbdHwRjdYeOCo=",-5815816420693821885,-1502465616681240896,924406954471815381,6549800324702035622>()) {
                        case -1343118903:
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
         public boolean a(float var1x, float var2, float var3, float var4, float var5, int var6) {
            if (c.this.eM == 3) {
               switch ((int)com.yiyiaddon.m.b.a<"s2hbo3nbph724t","VGUl/dYuK2vi6de6nX90AIRKxMektkWRcglbJkWTags=",-6288678917513561035,2714741425182476784,-458459888860814424,4699210430624746288>()) {
                  case 188907620:
                     if (var1.a(var1x, var2, var3, var4, var5, var6)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s21dfwf4ch4i2j","b8l6hWJXCu3o3SIs/RhO4DwUd1Z25+b4ryuHEZ4oFB8=",3701142466744443683,-2843236618418440927,-7932214070170279713,2610125766743593190>()) {
                           case 311996748:
                              switch ((int)com.yiyiaddon.m.b.a<"s304wjfv9w5esm","NSe4y2lyouUI6He90J48Ya7N67pRiH6IzuCYPQu8LRs=",7296044672149897889,3901022853223776722,-5100417662561827290,-3080058657601521494>()) {
                                 case 575350912:
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

            switch ((int)com.yiyiaddon.m.b.a<"s259cevlkcnp2b","Xb89W1Xxg97dDiB9OymtCbdU3Nc3Fm/qSiWhskgc5OM=",4591871334040499648,2934173070555185138,4757465116852200941,-8981670687457418192>()) {
               case 1671313723:
                  return false;
               default:
                  throw null;
            }
         }

         @Override
         public boolean a(float var1x, float var2, float var3, float var4, float var5) {
            if (c.this.eM == 3) {
               switch ((int)com.yiyiaddon.m.b.a<"s10nw0hew4iua7","ecKlfvzw7pcfhzgKupN24lwwW2diVKmWF6M12JOgO8g=",-2445618942339247399,-5018884984474613446,-1791411788356566374,-6604947762980202381>()) {
                  case -744273849:
                     if (var1.a(var1x, var2, var3, var4, var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s18unva0r1hh6u","BWSJiJQyH3nFnstDRGdajF+g4aOe1cXv/nWbdnaVMlc=",4733031920344664199,2990392250957889543,-7906081514787121709,-1475978549796159340>()) {
                           case -755167907:
                              switch ((int)com.yiyiaddon.m.b.a<"s2mzuhb8i5ev7g","wffrWqtv3jzs5Y3hJMGjCaSrHsn8GW31sbhjuv9JJwo=",-8236553742229505538,-5038255917624018664,7801595731251328748,-5271458408685997182>()) {
                                 case 2002422403:
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

            switch ((int)com.yiyiaddon.m.b.a<"s24z7pg1kav2ft","VZ4EH1NlDkMW3wg926r/Fiumxu7rTaA2TLLBmR15G1c=",1624316540793855643,5501561030964579921,-2850410168194630009,-7567243377385535833>()) {
               case 1520298281:
                  return false;
               default:
                  throw null;
            }
         }
      };
   }

   private com.yiyiaddon.l.b.g b() {
      return new com.yiyiaddon.l.b.g() {
         private final com.yiyiaddon.l.j.a a = new com.yiyiaddon.l.j.a(
            (String)com.yiyiaddon.m.b.a<"s1irr3rxoylj5p","Lao3IyIl5pVOViLqDa5dMAeDhZFxm+JLRuqNfX292wAw1tz2mNk=",-8989488371658255808,-869539040072808198,7462576670605922799,2513095585954731920>(),
            () -> c.this.u(-1)
         );
         private final com.yiyiaddon.l.j.a b = new com.yiyiaddon.l.j.a(
            (String)com.yiyiaddon.m.b.a<"s2ydh8rmpgz6xf","6JPPrJWV6nRCtDxvMPeeTwM0tI88ujXL07HuYtXW38qW3tA6yso=",-7300960934362580451,-7942270510138379429,624900222352457673,-4849058034509769267>(),
            () -> c.this.u(1)
         );

         @Override
         public float b() {
            return 26.0F;
         }

         @Override
         public void a(float var1) {
            this.a.a(var1);
            this.b.a(var1);
         }

         @Override
         public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
            float var8 = this.c(var4);
            float var9 = var3 + (26.0F - this.a.d()) / 2.0F;
            this.a.a(var6, var7, var2, var9, var8);
            this.a.b(var1, var2, var9, var8, var5);
            float var10 = var2 + var4 - var8;
            this.b.a(var6, var7, var10, var9, var8);
            this.b.b(var1, var10, var9, var8, var5);
            String var11 = "" + (c.this.eO + 1) + c.this.aM();
            float var12 = com.yiyiaddon.l.g.d.a(var11, 11.0F, false);
            float var13 = var2 + var8 + (var4 - var8 * 2.0F - var12) / 2.0F;
            com.yiyiaddon.l.g.d.a(var1, var11, var13, var3 + 13.0F + 3.96F, 11.0F, com.yiyiaddon.l.i.c.a().uT, var5);
         }

         @Override
         public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
            if (var6 == 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s19ohmxtgbagz4","lQ0moRzpwfrgIXGOSO3s3YM+lXhwOnu/MFex75Eq8aQ=",-2439263679137868738,6897158195436614787,-7680933496013630182,1709024700019786966>()) {
                  case -463602840:
                     if (!(var2 < var4)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3h6e2q4fvfix7","XJC0XjZuXqkSIt6f8sFZZTkrl/tSRojsI79nccCsJOc=",-4158649558614359061,8121772872769563371,-2536370211628615671,-8092612723908069348>()) {
                           case -484907601:
                              if (!(var2 > var4 + 26.0F)) {
                                 float var7 = this.c(var5);
                                 float var8 = var4 + (26.0F - this.a.d()) / 2.0F;
                                 if (this.a.b(var1, var2, var3, var8, var7, var6)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s33t61vn98mgj8","02maX02vK/KXASj9jpiZrfFu+k0pPLXjuKwk7UOYuKo=",-6679792306293419458,-8198705202881719960,-3917550318979761047,41774000729768252>()) {
                                       case 1207222604:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }

                                 return this.b.b(var1, var2, var3 + var5 - var7, var8, var7, var6);
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2be64lk0bpoef","yQBL3r0ikwg9nhOlXjgAiDC9FEIpminEmNxSvDM7o0o=",-3969943016917718787,3206279128276041206,-4855447412534049091,-2247100479102545746>()) {
                                 case -1692461578:
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

         private float c(float var1) {
            return Math.max(24.0F, (var1 - 72.0F) / 2.0F);
         }
      };
   }

   private com.yiyiaddon.l.j.a b(String var1, int var2) {
      return new com.yiyiaddon.l.j.a(
         () -> {
            if (this.eN == var2) {
               switch ((int)com.yiyiaddon.m.b.a<"sxz6kgqe4lnlv","ZhUIuZj2vioVwi9mgWO7NkrMiijaZGzbKzSpGQm7ToI=",2159301994699026612,617003111159245149,-494385814962251585,-1379982210990574341>()) {
                  case -1217089542:
                     String var10000 = (String)com.yiyiaddon.m.b.a<"s1tqgi6q7mler2","h5sVJzeHyQZ6mezM+cAF1/tLttDOrgC/x8+0x9mCx8pW+Ca+",-9089152856674311897,-8133966743102510024,-7046305964261499674,8308849443314783413>();
                     switch ((int)com.yiyiaddon.m.b.a<"su8n2ovfnvznm","sbmAilEnGr4B+8u4UCTdqJi4tGmhs3rzJnHbEWvsg28=",847729306941983902,-7365489294998684671,3014297541435900142,-2842968908370147083>()) {
                        case -2034674910:
                           return var10000 + var1;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               String var3 = (String)com.yiyiaddon.m.b.a<"s2oo14cgxoxxre","8J33DPmW1LZ21TbsUJKABQ/U+t50D+Wz3R5NQ7hqkVE=",2218893187071580849,-7252603363569885269,3394773773058044144,6638501144941666154>();
               switch ((int)com.yiyiaddon.m.b.a<"sa5hl6k3ody7r","CQ+DaPNUoEsRbdRpTHcngMBxJZK63EJiM2G4YGD2+Qk=",6748718516164561691,-8416451349251230104,-1122181144790706480,-6072761955470868612>()) {
                  case -672763861:
                     return var3 + var1;
                  default:
                     throw null;
               }
            }
         },
         () -> this.t(var2)
      );
   }

   private void s(int var1) {
      if (this.eM == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k58n173ubsij","Rv/1op16/A4rhdR4yKtUb/rLLw4dI4L+XaIMdOBnzVY=",6653046040075175919,2723237910034129420,-6809257693766777125,4098589271469479203>()) {
            case -1667934745:
               return;
            default:
               throw null;
         }
      } else {
         this.eM = var1;
         if (var1 != 3) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"s1uqycsvstd1os","YKTusysvWKBaJ0bCAM3vf/FlY0h7Zbbr2JV/cU7rfP4=",-2321962724948867059,8239384942418077094,-8792517871617774168,-2632342411955484275>()) {
               case -474725842:
                  this.eN = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2g5ncpkcodu2","xnEySoubB9K0Uimgvyu0sZyFs1ityAnkWbyvQMtc/gQ=",-5803503007884306739,-2262200559914967166,-4478743323388925384,-4979392318282061905>()) {
                     case -805374276:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.eO = 0;
         this.bx = true;
      }
   }

   private void t(int var1) {
      if (this.eN == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zommvro2xx43","zE2ITRQ/fome429zKUioexYQN42iAdsa4QtPpuzX5Ic=",9053398168517991146,-6228252771789991455,-2396107317328559233,1577053000971142551>()) {
            case -188908370:
               return;
            default:
               throw null;
         }
      } else {
         this.eN = var1;
         this.eO = 0;
         this.bx = true;
      }
   }

   private void W(String var1) {
      String var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2fsb4rhq4n9tz","QLNwQdvpXFRReiI5MdtROxwRMXkA2QjJv8IRoK/e+iQ=",2052934906277889702,586432215799188138,1488379597993821455,806133258432131672>()) {
            case 686167709:
               var10001 = (String)com.yiyiaddon.m.b.a<"s35vgcodt412sw","o1vT0EcetvSn6oWAnsIhfgEtT2l/Tly/ax2dwg==",4725904431893986145,174509337510669958,7541757353502251227,-8778447444102549051>();
               switch ((int)com.yiyiaddon.m.b.a<"s8vw0y86u02iz","eJ5DqLinkvZYbvjqt4l/e48P5k02GInZmNMH4kv/k8U=",-4272406508719755057,-8562882366184127127,399957709751811944,2674247886056286171>()) {
                  case 1886314179:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1.trim();
         switch ((int)com.yiyiaddon.m.b.a<"s3aqi84zivuhs6","hXTVj5e7ov+kf3BxuCDt80R/eOGjPfp94X9gv7WUCzU=",6825384710163929161,-6214846912932915466,-7471018727174773781,913721580532602338>()) {
            case -2130243899:
               break;
            default:
               throw null;
         }
      }

      this.kZ = var10001;
      this.eO = 0;
      this.bx = true;
   }

   private void u(int var1) {
      int var2 = this.eO + var1;
      if (var2 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vxznpk8ktwry","yK5D6L3LcaOhYUlK7MXDQiBvq5NvWU5Zfe2kcRw9vAQ=",-5079562882061744054,1305591667664218789,6162918044451357730,-939628383639306568>()) {
            case -1917443131:
               if (var2 < this.aM()) {
                  this.eO = var2;
                  this.bx = true;
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3a629mrskn0bl","kgEd8RsQMPuBSCIzlSB9lyTxLdSymPxQcr9SIu0Yhig=",-3177234130649239243,-6566962749687389193,-6524687236727740199,-8110311104038864716>()) {
                     case 453196747:
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

   private void cP() {
      this.a.cP();
      this.bx = true;
   }

   private void cQ() {
      this.a.cQ();
      this.bx = true;
   }

   private void dt() {
      this.a.cO();
      this.eO = 0;
      this.bx = true;
   }

   private void du() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s155mruvituxpc","/JVZqGt7Mf0lMNv7P/5F/V038+V09C4bbSMJHmFJPvY=",3018831469274425110,-2142116300185428287,3414455650113545686,851475119713395431>()) {
            case 499068055:
               return;
            default:
               throw null;
         }
      } else {
         h.a(var1.screen, this.a);
      }
   }

   private int aM() {
      return Math.max(1, (int)Math.ceil(this.aW.size() / 20.0));
   }

   private void dv() {
      String var1;
      com.yiyiaddon.k.b.a var2;
      label209: {
         this.aW.clear();
         var1 = this.kZ.toLowerCase(Locale.ROOT);
         var2 = com.yiyiaddon.k.b.a.a();
         if (this.eM != 0) {
            label170:
            switch ((int)com.yiyiaddon.m.b.a<"s1szun4s8tefkm","ctNMcJyXCJ6C2ZICMSMnmCk1w1J4EsZnOkWVtfqAMyM=",-1642942503460181741,-7604042232283512064,-3780337646598504330,8355218637445855846>()) {
               case 1563683496:
                  if (this.eM != 1) {
                     break label209;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1k0gqrxjaj2qq","llGcx9lkZXhjV90QAFBr4zquc+PDTRKtjOlXpKsjS9Q=",1963144356000189893,-7885040191683914684,6914234924667925445,-9138334029188378855>()) {
                     case -1911193989:
                        break label170;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         Iterator var3 = var2.B().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"siwxue0o1nmnw","KVOQHV9W+llDa8phmVHk44NVUZSHKF4DKBggWWCg3wg=",5011419145806089717,6164020199239778135,-7622602029401580108,6451047184186952418>()) {
            case 101532902:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2at8e82dm54wr","C/OkJEvCER7HucworxjlCLxoBh+FbebXRBvxiDnXozw=",-57280027699017789,5467758724002931277,4756732033269972469,-559321158237013640>()) {
                     case -855968798:
                        com.yiyiaddon.g.c.d var4 = (com.yiyiaddon.g.c.d)var3.next();
                        if (this.a(var4, var1)) {
                           label162:
                           switch ((int)com.yiyiaddon.m.b.a<"s2phv8ppr2m0x1","QLgjs+xQoRYvLy2yNhAfuZec7POb0CxwaTWGxPDunHE=",8836928723197408152,-8137820419359250051,-5377287121361496503,-2774165023164861383>()) {
                              case -1761277972:
                                 this.aW.add(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"sb36dhayqgtcy","TTsmnu9jHziFzdKpRMjrOHKGlVDmDnlgh94tsssPPt8=",-8098100829859174472,-5515798910656476974,-1325189259205431119,6331543335790395233>()) {
                                    case 1225237105:
                                       break label162;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1w9c9e4kjzcot","FMr7YItxDif1vZiFixZDQFs/4dk9FCFF4fVW6jF0Fjs=",-1966158251496016095,-7379963499252308153,-2508976112956784089,3133326409879979479>()) {
                           case -524783238:
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

      label211: {
         if (this.eM != 0) {
            label156:
            switch ((int)com.yiyiaddon.m.b.a<"sq1d0gqf509c4","nGGLR1cIhKFYteQ76G2UiNTfa17FZTpMzZWy3f9xiMw=",6387357039415176124,8500571664914756070,-8419876274090719078,4910868332049835005>()) {
               case -29190245:
                  if (this.eM != 2) {
                     break label211;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1vp3t4uoshqsq","5rvUM8bFlyrsC7ocRxKHWzLcsr/v1mDbys+4ovKTsik=",-6564344001150971055,-8789568609573280765,-5097588085786219087,-4832832549768480825>()) {
                     case 928260926:
                        break label156;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         Iterator var12 = var2.C().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2u99ygfowbmmq","DWeby1k6wNQpoU/EkcmmXVTKgPzGkrWAzlUia4plw4o=",8441690537761064296,2216584499968917279,5937221589596986009,6048271629352880252>()) {
            case 980673394:
               while (var12.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1n3488fzgfxoz","QR6pNrrOfccGkjAwCFs8isDXvuzMIYT53s9J2/uVCYY=",4060292435114983177,-4645303321101339124,8301380161389542010,-1679237374729771142>()) {
                     case 1070673771:
                        com.yiyiaddon.g.c.b var15 = (com.yiyiaddon.g.c.b)var12.next();
                        if (this.a(var15, var1)) {
                           label148:
                           switch ((int)com.yiyiaddon.m.b.a<"s2xm7yx595mobh","fI6t4gToxuzaFWuJ121wwcM0BiBnadvo0oZLOsCV994=",-1665817148787723201,1418891702022147906,-7807319804581019624,-3373316444204541092>()) {
                              case 1919111283:
                                 this.aW.add(var15);
                                 switch ((int)com.yiyiaddon.m.b.a<"s28diq8766k5pr","dsHmzWgrDVb7Z5hrb7Q/GyA9jx1Ek5v9+dvdwg5X51c=",8353968084242707163,-6164968776018921330,3735989996077397712,6728545192095984633>()) {
                                    case -1256016943:
                                       break label148;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s26y9kdm2kyfgp","TeOq1pxtEsZ8jnfYseDLohSZrsDnAH92ewzWackhQo4=",1047124922002635902,2324344650706152646,8257712114957295040,-1035478431796655432>()) {
                           case 2114571129:
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

      label213: {
         if (this.eM != 0) {
            label142:
            switch ((int)com.yiyiaddon.m.b.a<"sk51gpbsyn5ek","cI/s6Fm6e3wRqVwhvSO7dOF+Mt28cZXSTfnmehDZ0tk=",7118813786485605252,-8191162401718483209,-5553184281471358157,3917768058611878349>()) {
               case -1008488768:
                  if (this.eM != 3) {
                     break label213;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1iferq6mjkn8n","OS/Iyp+LH6wseHo4n1G0S8iT1svIMT7XaDBiBr22DwM=",-1402812943669825013,-7128424443364354094,-7188786148203483079,6617582087829186810>()) {
                     case 1370267315:
                        break label142;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         Iterator var13 = var2.D().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s30k18ye8o0404","sOsRv/HPc5qWsGjIBT/kq1NHDmekpnID6GNAz3rT3RA=",-9129442426949675172,4706838112819957421,5771574749827017303,5255105267914146145>()) {
            case 262084168:
               while (var13.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27wc847077esf","Y1y+wW5IZajBJ+dH9Qz2rVcu1NHyJ3e7Y4c076Yngdo=",-6441177308402031977,-3555559673212473660,4015724139141125464,-6718257220345066537>()) {
                     case -1546715584:
                        com.yiyiaddon.g.c.a var16 = (com.yiyiaddon.g.c.a)var13.next();
                        if (this.a(var16, var1)) {
                           label134:
                           switch ((int)com.yiyiaddon.m.b.a<"saxb236yvgmmw","2lq3rzkp+qOBC66sXHn5g/CjRQu1l8Q9FKNCbAmOcMw=",-7342958789897267483,7636314890693982647,3370419212240159068,-6473506335471679003>()) {
                              case 642652356:
                                 this.aW.add(var16);
                                 switch ((int)com.yiyiaddon.m.b.a<"s24itzxaai5p2j","Z7J6S5yRBz3ZjsvNX2MaK6WbPXF+J1kYCoqz+TrzKRM=",-4743855051620825986,-6527877535058749945,-7075148043073863838,-30316749769774385>()) {
                                    case -514144297:
                                       break label134;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s16ps3veus7pg5","beDjrHnLrZGslQ6sJNHx6KhhKXm10cZ1An8KMW0IWDA=",4828748116100958428,8578180268570945293,230450065055417406,2200049711026993477>()) {
                           case 244808287:
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

      int var14 = this.aM();
      if (this.eO >= var14) {
         label127:
         switch ((int)com.yiyiaddon.m.b.a<"s39ieafhs3r0yi","Xxh8idI/pp302QakIMy1WRbqXI1z1em39HYfoA3pKis=",-5852863722412377531,3842478063799355087,-9066461855053869202,-859062943612448521>()) {
            case 2041468587:
               this.eO = var14 - 1;
               switch ((int)com.yiyiaddon.m.b.a<"s2uhcpr7kadzxg","XJEfxj7KBcffJYN+/fbNAa4AYx+ib+v7044zJ2/rbFM=",-3752440401995143432,6087005940708330602,6609991546154547662,839859474920954090>()) {
                  case 1911680953:
                     break label127;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.eO < 0) {
         label122:
         switch ((int)com.yiyiaddon.m.b.a<"sulaupg1rr7o6","XjzWICC7D8YDct3aNTKjrGyx/q8mpnY1ANBLZAu/85U=",-2070726967370938612,7755156948796947378,-9089250768312947575,6146739482365049515>()) {
            case -884460273:
               this.eO = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s1rxjnz3fssmlt","1n2KEJsDryZoAjLymk7Ain/JTcjJhKiGcLAsVndZ1DE=",-6421495358449838697,2863362447570032991,7218852862939554649,5653875862766078885>()) {
                  case -1231653210:
                     break label122;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      ArrayList var17 = new ArrayList();
      int var5 = this.eO * 20;
      int var6 = Math.min(var5 + 20, this.aW.size());
      int var7 = var5;
      switch ((int)com.yiyiaddon.m.b.a<"s1booj1scov3rd","5hGMlL3ywUEQfrtqxcJsj+YbDsFdy/d24Kg0Yo126iE=",8083284531073915984,-508172964640615255,-6384859177423157860,-8122376952319261565>()) {
         case -1954706246:
            while (var7 < var6) {
               switch ((int)com.yiyiaddon.m.b.a<"s237lpwgke1wyl","gY8G4u/BZLSe4yI5r+zww9AzvWA7lMdVnTOT9sl/9o8=",-5347458296969580068,-1567244777799066868,-6483073544451006954,5163553408214870861>()) {
                  case -1157505357:
                     Object var8 = this.aW.get(var7);
                     if (var8 instanceof com.yiyiaddon.g.c.d) {
                        label105:
                        switch ((int)com.yiyiaddon.m.b.a<"s6ne4hsdg7h91","cTirzy5MeRhDMRZrF5IJIBvWkGuhR4yGm/KzQySUlIU=",-5796038232690178718,-5297086107557066846,7315382446645904878,-7511449094246875957>()) {
                           case 2072118342:
                              com.yiyiaddon.g.c.d var9 = (com.yiyiaddon.g.c.d)var8;
                              var17.add(this.a(var9));
                              switch ((int)com.yiyiaddon.m.b.a<"s11601xwhggghf","ya9S/fDnMadjfl97TyUfiXUKuFFFr0PFq9uIG1zw1lU=",-1295114426100339864,-4471854118278245796,-3956014776888128508,7179033761961945673>()) {
                                 case 323916010:
                                    break label105;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var8 instanceof com.yiyiaddon.g.c.b) {
                        label101:
                        switch ((int)com.yiyiaddon.m.b.a<"s257d3hjkq61e","RXA+erFC8+elX8k8SWOQHaAqXUQsSuky1dIgeK9d/zo=",-5244926959409434930,-735121887903692663,5144469344743531199,3695590380241504215>()) {
                           case 166471750:
                              com.yiyiaddon.g.c.b var10 = (com.yiyiaddon.g.c.b)var8;
                              var17.add(this.a(var10));
                              switch ((int)com.yiyiaddon.m.b.a<"s1dem5ek101jkv","RIqNWq9qmc53yAT89NWnkI950hVx3Nf0xJvnn3VmFhI=",3072128973583446825,-3182973939820528590,-289937239279108947,4988320425010609831>()) {
                                 case -1580143079:
                                    break label101;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var8 instanceof com.yiyiaddon.g.c.a) {
                        label97:
                        switch ((int)com.yiyiaddon.m.b.a<"s11ccyl0grbrqi","6DV2pw/JpTJ0oHhCRjoIv9P5lhXnPmWt9yzmb09quko=",7998253332416375584,-9177335283721288827,3428602844055216398,5340177566147196222>()) {
                           case 467423995:
                              com.yiyiaddon.g.c.a var11 = (com.yiyiaddon.g.c.a)var8;
                              var17.add(this.a(var11));
                              switch ((int)com.yiyiaddon.m.b.a<"s2s60kidw5815k","8y1guPzw8IiSehe62kR8IhbITNDVMLlrf42o4M+jMrk=",-1265074493742948279,-4127204022031707430,7850681699247893254,3091370334073406375>()) {
                                 case 162818543:
                                    break label97;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var7++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1ocypn47i2q2u","qb3FNnLA1sOz8qAlIpl9oYV1XCTwFqgnvq6sAuWfnXE=",-9179612314450041426,-4028708059160852537,-1103150082125318149,9096937760387538905>()) {
                        case -1672831800:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.b.s(var17);
            return;
         default:
            throw null;
      }
   }

   private boolean a(com.yiyiaddon.g.c.d var1, String var2) {
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"spligmxeiswt4","XPE5S5GOKwCEi4OvvGrLNMVG7KFv8ChJ4HER6p9pNfQ=",4878895551023934277,4219648275501648617,-899696524860307689,2395708675202764361>()) {
            case -1972194153:
               return true;
            default:
               throw null;
         }
      } else {
         if (!ag(var1.m()).contains(var2)) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s1g7ps7l344ac4","7zE/zO9Yo4LMVbtEa1QEPeLPtpff2W9Zy0dRl8Nlw9E=",1037041463674352545,7488954356887247752,3417397742382833697,3046667482526328571>()) {
               case -2124866570:
                  if (!ag(var1.be()).contains(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1lywxyki3q2pf","3P2BnwN6vtdpEi3FAuYO93uYpgw5/Ezh70x/YTPbQFU=",-1504769521918496987,7304919640957729554,-5808870338003338304,-4042002761076773042>()) {
                        case -657841198:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sncifx8xoergo","AN+m5xiuANzRQvUYAsxyLF+T9TKiHc9xnsHbgPpvzJo=",-8102670341566470537,-5636677601110829257,-7872152226407030602,-1041869871488617696>()) {
                     case -237940744:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s7miejjf4wbtq","7J2aSl6/YCtOVyYGIzYOzRFoV3InMHaLpM5Gen/1UgM=",-4637298805156264378,8887414465889977019,-621843139857948991,-3316560153429759221>()) {
            case -1769698566:
               return true;
            default:
               throw null;
         }
      }
   }

   private boolean a(com.yiyiaddon.g.c.b var1, String var2) {
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s30lr39uogk9kq","T1RImctuNFmTx8wHB9yHs/kToYRUhni5sUuyOvWpfJ0=",8936724350820526588,8453625806030051242,-4694146052891150628,-5151888050883198149>()) {
            case 2145758306:
               return true;
            default:
               throw null;
         }
      } else {
         if (!ag(var1.m()).contains(var2)) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s3b3ke27u2a7by","7rY9GbSBeQd9BswH6K+Odb8lY3GuLtUWUBRJmhyO7IQ=",-3289609684884841317,3104094923655855621,6781333728676668172,-7765863046325900461>()) {
               case 491294506:
                  if (!ag(var1.fU()).contains(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1nj5038u38bzk","FqyBjA63WSNnLKQzFBFZSx6+i452Sds1o2Q7g3MABYU=",-9219316349138618702,6242957591696402437,7927496153204549230,3056433902594053293>()) {
                        case -1481498371:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3mo3o91tqne4v","7+1f+kII9iyFj+8jRKytRKJUtamHQiP02hVjkziPXSs=",-3161109405980789280,-8474252661669092105,3616016164307625763,-4604351669562004764>()) {
                     case 1824176821:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s29ykkyr0mzhvo","cA8Er1/VlVXJ0M3W1+m8VoLbDI7qM+LHZ/IfXxF6c2s=",1887166070290850263,8874038856250147588,-8129054416018857598,-2424845290061730116>()) {
            case -2104367711:
               return true;
            default:
               throw null;
         }
      }
   }

   private boolean a(com.yiyiaddon.g.c.a var1, String var2) {
      if (this.eN == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s21dzbdweuo6cy","6edaAhhuWxuUdXWQmqlPU3fklIyLDzcFQduqxIELwMc=",-1389335653162895213,-4967596186123426848,970104241567158392,5633678987998574529>()) {
            case 675366315:
               if (!var1.fc()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3f0oflmxjj3hr","CoOjfj2XhyvOk5EW5kmplrCu5yjjAEZBhQEUrzq6uV8=",1396808713947440741,8732178598962987369,1514851140872963599,3213950513571343832>()) {
                     case -633608705:
                        return false;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (this.eN == 2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kw33uvvlyrug","ulN4VyJJTE/iNwH9yhm610uy7x/CLMQu6lpA7OmgEnw=",1010430728504146849,-4876118056620013422,751339496940388984,3993322327768225726>()) {
            case -570069588:
               if (var1.fc()) {
                  switch ((int)com.yiyiaddon.m.b.a<"se4w0ldczwhqz","58eiq8/yN/PaMqKGEYDCDvGW2uYhpeHsJBhMyvMmB5k=",-2458838673727003511,3666938901663954793,1404356317473535356,5342221967578625487>()) {
                     case -1538786241:
                        return false;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s39ppfdqeofvs7","Eu35w7l0UksWvMHurnz3uh7e+d5aSTHPxeHCXPWsIkA=",6796127976338022947,-1207820431046585449,-5363598599965062360,6048797783241103528>()) {
            case -684240686:
               return true;
            default:
               throw null;
         }
      } else {
         if (!ag(var1.m()).contains(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"sj3q397ih2m9f","ixkKyJhEcZRCjLX33ruduG2AjtcKBYw1gH9/REVS6og=",2668797552084754927,2590394611545966992,1336514577258096077,4486045615875147402>()) {
               case -1743081603:
                  if (!ag(var1.fL()).contains(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1ij1umj62ixwu","WciQ+8gve7L7JYE4NURF5i9ClsBYIiP7oYY5fOunuUc=",-1862975456365709395,179873990275096137,3615744467302468481,1603366790993226702>()) {
                        case 568698792:
                           if (!ag(var1.ds()).contains(var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3edarstud49nu","Q1Nf/ibed4U5TewPwwta2OiOHpS13EUin+YPrjgORyY=",7293029923566631409,-2767789478599388957,5515657204512446962,8913755306374609314>()) {
                                 case -185468974:
                                    if (!ag(var1.dr()).contains(var2)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s18gff4l7lgcqa","xUFbARxB6TOAXuJL3gBgkYsVytruWZrjyAR547+XJKo=",3889578490682355752,1130020067359440836,-5848260503761465263,9069148975026489789>()) {
                                          case -1499415886:
                                             if (!ag(var1.fQ()).contains(var2)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sylhbap9196xg","FPz7r6uCH/TGfyax8B6OGX4cXZfejVsrdGp6zIeEcmo=",-490076726533608437,-3608924520627753265,-3696802705691000018,212626206554572674>()) {
                                                   case 613350959:
                                                      if (!ag(var1.fR()).contains(var2)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s21wj43bk4f4h4","W2wMxu55iL9vhiCFERGX3sUcOF1tOHaQSkZR9jaVvZw=",-7560489323384795578,2182310187441177958,9169317413438216809,6681574581569176114>()) {
                                                            case -1633227795:
                                                               if (!ag(var1.fT()).contains(var2)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sq85qt7t45zi4","SkQy79xWP93yG3pvKRUSRTtY6ITr9lrjz5ApVX0BgUM=",1841109607832614176,-8428747970185773072,-4442395007958726286,-8340562189083215168>()) {
                                                                     case -1792448802:
                                                                        if (!ag(var1.fN()).contains(var2)) {
                                                                           label64:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s2z45gd2up4kxi","3G3viO7sibPYQVqkGZcbBnSW4n5D22kXehaGdS87xyg=",-4352939885562770495,6537695448468555949,2277322938848216355,4184662783402067523>()) {
                                                                              case -211232940:
                                                                                 if (!("" + var1.aj() + var1.ak() + var1.al()).contains(var2)) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s3al9hzea4xtz0","kb5N7uFA5T367E//IwyS8Td21QJIUnV4O4xEgAEftUo=",2663571656003447137,4421438811655248324,1578783580939047676,7686699204004385281>()) {
                                                                                       case 2034635586:
                                                                                          return false;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }

                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s3qcqo7zqz14mq","R7nSYfVlVsoGXlaU42N/6tpPQ5ciK/SPdHpagyDjpTI=",6147668153781686551,614255740020403128,3704282644434303088,-4673496149673425218>()) {
                                                                                    case 564186401:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2v5hlyay65ck7","hKBWbGIKzt2y/G4Lbgb2WYOx2TnyRcTb7U4wnFMtALk=",-5105987438536283967,1123881436409831605,-8366181721173232195,-7225320034261470593>()) {
            case -1262766103:
               return true;
            default:
               throw null;
         }
      }
   }

   private static String ag(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s8uxxtf0wtqqt","3ESUWVTJEI8tQVZOrl1aRHV/BN09+7kK7/9oAeOsQAI=",-8497069466750728775,6174930408611958690,6881474440567533016,7226531001685251669>()) {
            case -261934384:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s35vgcodt412sw","o1vT0EcetvSn6oWAnsIhfgEtT2l/Tly/ax2dwg==",4725904431893986145,174509337510669958,7541757353502251227,-8778447444102549051>();
               switch ((int)com.yiyiaddon.m.b.a<"s1x0ui47m25xan","cWThqNt1ctqzz6MLXfW8TgpugDlg+RcwrbJDSkgnMzM=",8974181489467932317,-2791409879065046641,6874795470525430878,-6902064652576441228>()) {
                  case 1080565377:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s3hmegwvzqxxyz","xmWpKhxOZPRkpuLMFvZJQ3bmWl9QUG/HirIkzWpY8i4=",7362797533615588359,6597704744785087561,7880943179559201248,-6045545120272777125>()) {
            case -1943912180:
               return var1;
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.l.b.g a(com.yiyiaddon.g.c.d var1) {
      return new n(() -> var1.m() + "").a((var1x, var2, var3, var4) -> a(var1x, var1.be(), var2, var3, var4)).a(() -> var1.be() + "").a(this.a(() -> {
         this.a.a(var1);
         this.bx = true;
      }));
   }

   private com.yiyiaddon.l.b.g a(com.yiyiaddon.g.c.b var1) {
      return new n(() -> var1.m() + "").a((var1x, var2, var3, var4) -> c(var1x, var1.fU(), var2, var3, var4)).a(() -> var1.fU() + "").a(this.a(() -> {
         this.a.a(var1);
         this.bx = true;
      }));
   }

   private com.yiyiaddon.l.b.g a(com.yiyiaddon.g.c.a var1) {
      String var2 = com.yiyiaddon.i.g.c.bU(var1.bU());
      String var3 = "" + var1.aj() + var1.ak() + var1.al();
      String var4 = var1.fL() + var2 + var3;
      Runnable var5 = () -> {
         this.a.a(var1);
         this.bx = true;
      };
      if (var1.fc()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ucrb7826uew6","n84p6aYvcNvaqmCsN+gBiF4tFF0MdFLIa2070zClUtQ=",-8313854454759107848,-2782967282616512521,-7210607084489958544,2248023850395401683>()) {
            case -793601979:
               String var10000;
               label42: {
                  if (var1.dr() != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2m4s0fo1pkcnm","dgMYSPUZTemqxKoTJ8fSaNcx2bZ1DZ/b3XiW1JmBeEQ=",-5027655710785671631,8190365449890130204,5744619543558611728,-7695162456900420265>()) {
                        case 103629346:
                           if (!var1.dr().isBlank()) {
                              label29:
                              switch ((int)com.yiyiaddon.m.b.a<"s5s7dkyn5a80x","drRTxYd4jKzzIgYNWawgLT1+2nAcFHXMDbTwSfLLOyI=",-2177835927045921002,3265031476121256042,9086621753267556950,-980907764470503324>()) {
                                 case -2085800219:
                                    if (!var1.dr().equals(var1.m())) {
                                       var10000 = var1.dr() + "";
                                       switch ((int)com.yiyiaddon.m.b.a<"s3l7i2lr3u3r1p","jR/WbXGDoR8wgQeDITXHpeyhp8XvushOCH2J2usdITs=",-3115440291921005618,8420255692037904729,864121006318847323,1275300210188680267>()) {
                                          case 883071025:
                                             break label42;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2ea1e56xxoitd","6q0xj8goOPoAavd7NY/MWNPWJsZhRWIHbdZXT3DtQ2A=",426007194157718140,-413686962613875015,3205629322221717591,-4195599341068022422>()) {
                                       case -1138721549:
                                          break label29;
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

                  var10000 = (String)com.yiyiaddon.m.b.a<"s35vgcodt412sw","o1vT0EcetvSn6oWAnsIhfgEtT2l/Tly/ax2dwg==",4725904431893986145,174509337510669958,7541757353502251227,-8778447444102549051>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2lks1j9lytzqs","Fx/eS5B4B46kN8E7w22hfhkI2lcUE27a/OBLBNSSBeA=",-646781268526187971,104152005326070653,-2162811985852278080,-3808389984591038103>()) {
                     case -1720933073:
                        break;
                     default:
                        throw null;
                  }
               }

               String var6 = var10000;
               return new n(() -> var1.m() + var6).a((var1x, var2x, var3x, var4x) -> b(var1x, var1.fL(), var2x, var3x, var4x)).a(() -> var4).a(this.a(var5));
            default:
               throw null;
         }
      } else {
         return new n(
               (String)com.yiyiaddon.m.b.a<"s2t6o1og0cks8h","3vquxefWw8Fdzdy6Kh9Rm8RyhgAEcHT1qyRzlLW+Cyft6eV7P/3Vp9RiAWtD3LVo2X++Eq/aOpidrO+xX4Ap+a1qjbWyQ6mX17+AkGnCL4s=",6314154046736679950,9073444860946168931,-5241307638289721188,-8233671168011541157>()
            )
            .a((var1x, var2x, var3x, var4x) -> b(var1x, var1.fL(), var2x, var3x, var4x))
            .a(() -> var4)
            .a(this.a(var5));
      }
   }

   private com.yiyiaddon.l.j.b a(Runnable var1) {
      return new com.yiyiaddon.l.j.b(
            (String)com.yiyiaddon.m.b.a<"s2cgzijzijm9my","jtDavBEs/843lU23QeKPa15r0N+DPh6/ViXQp6wo",4043030926782023052,-5329456427128203196,8412000850380309830,-8799407974981719191>(),
            var1
         )
         .a();
   }

   private static boolean a(Canvas var0, String var1, float var2, float var3, float var4) {
      Item var5 = a(var1);
      if (var5 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snrb55inl3t6e","wmo4YENB3u3AbkwFUWFZ3p8wJwyCgj/c0OlBGdYNtW8=",7205595909339350973,-7045393439530712893,-8714440491904068070,-676589902896135706>()) {
            case 1169414465:
               if (com.yiyiaddon.l.g.c.a().a(var0, var5.getDefaultInstance(), var2, var3, var4)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s7hdmyd3jmdpd","L+hVW+CHqwtfs0gspuGruUDP9F5y7TziujbwnR3RW2U=",8721115072659792142,-406982148654215217,5183666973816242117,-4955900139768733397>()) {
                     case -1875902830:
                        switch ((int)com.yiyiaddon.m.b.a<"s1ljbh41c9k6yw","l3B3PGC+f6BdTL4OY0nMJhYPix8aGEMTyx9kkKxPdj8=",8753151401179995002,6631315017207341288,-8812220272540111317,-7144451450896197609>()) {
                           case 1638443998:
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

      switch ((int)com.yiyiaddon.m.b.a<"s11arvbg8jzhdq","qm+t7BAYNlWl5BwBUzg78wt8S2xgD2n2MJ89Hx45N9w=",-298578440451287427,-3400005739527530039,-329207690290109448,-2788139481117287471>()) {
         case -730182391:
            return false;
         default:
            throw null;
      }
   }

   private static boolean b(Canvas var0, String var1, float var2, float var3, float var4) {
      Identifier var5 = Identifier.tryParse(var1);
      if (var5 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11zn7jfmb8j3e","dE0XjdJhQlaWimSXMp10XtCubhvA4W+2lPePVObzEhQ=",-591729857312142453,6849478998523230935,1271041727684538288,1355635769677331159>()) {
            case 1865144284:
               return false;
            default:
               throw null;
         }
      } else {
         Block var6 = BuiltInRegistries.BLOCK.getValue(var5);
         if (var6 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2pxnjdzlm2s8v","xOh12PjDs9lFJzLZ8GqOzdXthBu0b2uhQDxyzLE/ZUU=",1161185470417497097,-2882461233136595057,-4332114930802935490,-6546442629569074878>()) {
               case 893559274:
                  if (com.yiyiaddon.l.g.c.a().a(var0, var6, var2, var3, var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3fsxvi9n5mslt","nWTtWKMcYwUdoQ4QBZLRpJABBjhjvqixuw+8rcf92Eo=",5301945265357213358,4245295433661243969,-6227750550166347074,-6990243004996245824>()) {
                        case -769953750:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ca6if1293yff","Fa6gIXF5ngHjZmS//hVNW53UtzTRzWl8CAcJSnVxDCE=",-5119558047002331857,-5952056655934249313,-887113430937545548,5113884788911275890>()) {
                              case 83714236:
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

         switch ((int)com.yiyiaddon.m.b.a<"sioy2iin61n8z","jmHXiqQKYZ2vIqDtZ4CQtBVt6ctBRKayetXNdxIQzaI=",-5956999538188344421,-2657335579226611189,5687104003564856945,5006329204332155123>()) {
            case 241652896:
               return false;
            default:
               throw null;
         }
      }
   }

   private static boolean c(Canvas var0, String var1, float var2, float var3, float var4) {
      Identifier var5 = Identifier.tryParse(var1);
      if (var5 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s21wo1j3r5bqwr","Mwxzw1JxU16/j2CPVqsaGssDq7iPhe6ObkkfPI6PxFY=",-8302528729057830160,7194497696949762483,-2832172250327270788,8403386369433928596>()) {
            case 632291488:
               return false;
            default:
               throw null;
         }
      } else {
         EntityType var6 = BuiltInRegistries.ENTITY_TYPE.getValue(var5);
         if (var6 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sz0nbx698nabv","4nindpt2K6abgp2rxu4TLqUbg8pDtTgjF8SQoPJaxBs=",211068902417997507,4283720772720037616,-1808995312750063784,-4875158379588290101>()) {
               case 518194930:
                  if (com.yiyiaddon.l.g.c.a().a(var0, var6, var2, var3, var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1v3eyw5xtvoby","erHKKLdiSDA4feXuBo1/KIwG29HuK5HLyp9lc1ASK24=",-168564651533772184,-529625715327441601,-2055420089811987401,-8412155055399032850>()) {
                        case -1458626870:
                           switch ((int)com.yiyiaddon.m.b.a<"s1u5s55mcu7jud","wbAIT/qDdC66sbjTyUV6M0MqHgZ4a/sSnMOPz7nn3NE=",-7898964989730386478,-6610913234714515756,4611359102983279208,3878456339795381467>()) {
                              case 210082661:
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

         switch ((int)com.yiyiaddon.m.b.a<"sutmtgcf4a6o9","V1WSg++BWK0Bpl6FGuAfmC0TDjwez1tGUAgZaSZEQYg=",-7831845322698472685,-5955756042623477721,-3778878156367982752,-8099335934245326550>()) {
            case -168339975:
               return false;
            default:
               throw null;
         }
      }
   }

   private static Item a(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28x6jm8ycbs9y","sHV02Mr380A+CUFQm+XMHEdW4/QuiUztczoVKeHamzs=",-845736973558263713,-1981844887986732934,-7540962439978420904,-5960285901140315746>()) {
            case -1750458169:
               return null;
            default:
               throw null;
         }
      } else {
         Item var2 = BuiltInRegistries.ITEM.getValue(var1);
         if (var2 != null) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s3od6dn5rdy79j","R5u/Bub6Md579x4LKQvoZafAphETOvZmtR3iJml3Cwo=",8175794306767170552,4461507657200638120,4131482638967842168,921817433488624092>()) {
               case -793855322:
                  if (var2 != Items.AIR) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2797tre75eurl","BRZxMc0nIx54KFzpSajiBwc2iCqdPluJjbtZ2u1CrjM=",-4124302967824125817,-4249088469683308068,-218156066013181903,-295356745039235094>()) {
                        case -1478593387:
                           return var2;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1byft2pzwxi3","4o7OnUCv5xaRWTJhGmIcuqh5a7+GKrdyD54IGwIKWKI=",-7396724398441030712,-5620581349158442166,-3355590244249583705,5724667089428938991>()) {
                     case -408153150:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"sq9a2jy254x4k","RgxjR4Mznf7qhrUhF3i0EYJxVnZLUzcinqUdYD4iKcY=",2109189426810313978,925108375705529365,1984345359334650077,7034463600323663215>()) {
            case -722213726:
               return null;
            default:
               throw null;
         }
      }
   }
}
