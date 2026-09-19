package com.yiyiaddon.e.c;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.d.a.c;
import com.yiyiaddon.e.c.c.d;
import com.yiyiaddon.e.c.c.e;
import com.yiyiaddon.e.c.c.f;
import com.yiyiaddon.e.c.c.g;
import com.yiyiaddon.e.c.c.h;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String bO = "autofarm";
   public static final String bP = "自动农场";
   private static final String bQ = (String)com.yiyiaddon.m.b.a<"s27l4c3mdb8kf6","0cD93w8CkShAt93dpbz2eaZrtRUuskpKthgsvZAd",-8217551673360944393,3284887502452255572,-6282470613909895350,-308052298598740292>();
   private static final int aF = 80;
   private final com.yiyiaddon.e.c.h.a a = new com.yiyiaddon.e.c.h.a();
   private final com.yiyiaddon.k.a.a b = new com.yiyiaddon.k.a.a();
   private final com.yiyiaddon.e.c.g.a a = new com.yiyiaddon.e.c.g.a();
   private final f a = new f();
   private final h a = new h();
   private final g a = new g();
   private final e a;
   private final d a;
   private final com.yiyiaddon.e.c.b.a a = new com.yiyiaddon.e.c.b.a();
   private final com.yiyiaddon.e.c.f.a a = new com.yiyiaddon.e.c.f.a(this);
   private final Map<com.yiyiaddon.e.c.d.g, String> l = new HashMap<>();
   private String bR = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
   private Boolean a = null;
   private final Deque<String> a = new ArrayDeque<>(80);
   private final Minecraft j = Minecraft.getInstance();
   private final com.yiyiaddon.e.c.e.a a = new com.yiyiaddon.e.c.e.a(this);

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s15l5vel5cjucp","Y6hats5jhvOdaKsi18OISH9nsywPGwpPSOwrgxg/mNV2P0tHclZzrP7GO4g=",5270896215829710264,-5495009229515108823,271062044985252578,2442450476031508562>(),
         (String)com.yiyiaddon.m.b.a<"shhymj6dqyhba","vt7cTZW+t/XryKWEUwVTGYmCiqM6vgB2fBrtUNOg+f5Dg2aK",5522978535738040898,-1847537027349733158,-433027615928203401,-2358902848112082257>(),
         (String)com.yiyiaddon.m.b.a<"s120788y6gy69x","0LYMdh+B1DY2HnU9C5xlMIFaslX+Y4LLqUyV9xOYryhXUL5xamJHHZE/1CF2+9Zg",-2774945420311560500,1503069885704559368,4479888493461411822,646427025848725566>(),
         (String)com.yiyiaddon.m.b.a<"s179kh4r1xfnz6","XEWCb4OeRTYmZnP/vIZC+Z0XJtXsFlYtUz0f6RBGjbgpFvfxmPMFEg+NmYXjdivGP151WLYo5uVvHLVKk1b2Oplb4joLQtcjckhdRScWMRdHe3BL/nhWx+3YVXw+kW7MQR5ChxUqbX7EyWr6QnVO8IhgsU1mENU+gmkvwg==",-5362209427053700587,1851226710213914380,8277784245379263131,-8878102200861187341>()
      );
      this.a = new e(this.a, this.a, this.a, this.a, this.b, this.a.aI, this.a.aJ, this.a.aK);
      this.a = new d(this.a, this.a, this.a, this.a, this.b, this.a);
      this.a.a(this::u);
   }

   @Override
   public String a() {
      return (String)com.yiyiaddon.m.b.a<"s2n6xkarnsxreg","SiyGGhMZwweNqtAGNQSgCBirF3R/P45rtI/YvlxmUm+ap0B0AhyHN2YQMCE=",-217436930258097159,-669684651442828360,7983813518654356495,-7062317628521461224>();
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s27l4c3mdb8kf6","0cD93w8CkShAt93dpbz2eaZrtRUuskpKthgsvZAd",-8217551673360944393,3284887502452255572,-6282470613909895350,-308052298598740292>();
   }

   public com.yiyiaddon.e.c.e.a a() {
      return this.a;
   }

   @Override
   public int i() {
      return 20;
   }

   public com.yiyiaddon.e.c.b.a a() {
      return this.a;
   }

   public d a() {
      return this.a;
   }

   public com.yiyiaddon.e.c.h.a a() {
      return this.a;
   }

   public f a() {
      return this.a;
   }

   public synchronized List<String> m() {
      return new ArrayList<>(this.a);
   }

   public synchronized void an() {
      this.a.clear();
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
      com.yiyiaddon.e.c.d.g[] var2 = com.yiyiaddon.e.c.d.g.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3dtqzffzg9tpj","UZLR7vXAP1l2om5Bkf1z7F+OfEkD9nRBzQsd0liTLcw=",-2667720049627537865,-5477035661484513816,3174986382355125929,-6218476798200954809>()) {
         case 1296336802:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s3d2e4avajg2ue","F9nokaKhgZ58vHdFUP01w+VJvspL+xrsYvLinR+0kvE=",4244679802475712475,-3567284181904545524,-2008255062998040805,2599118998990648783>()) {
                  case 1364480537:
                     com.yiyiaddon.e.c.d.g var5;
                     Map var10000;
                     String var10002;
                     label45: {
                        var5 = var2[var4];
                        JsonElement var6 = var1.get(a(var5));
                        var10000 = this.l;
                        if (var6 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s20vdzz9hzau6l","UNWZZgMgdKbkJiHISjwyJm+Ixu3aOgb04T32bGYmS7o=",-6148406138156517161,-6914658617229690912,2370342233621395698,-5960947765465165598>()) {
                              case 1648224898:
                                 if (var6.isJsonPrimitive()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2bfab8jv7pbdu","i2E2u2KYOjgnYZ6Scl31J2Rz5v8QgZ2cb4UEHav5uEM=",-7500971116319909960,385390208962258743,-6259364093707373380,4420228650454321307>()) {
                                       case -932228175:
                                          var10002 = var6.getAsString();
                                          switch ((int)com.yiyiaddon.m.b.a<"s2wptm2rofotpl","ivxElUFq16i1GFAWpBHDFFKqZSMmZEcvhTKG3/GuF6c=",-898775882166539620,3255963836825402266,4101228753641761750,8402817704864220881>()) {
                                             case 704176027:
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
                        }

                        var10002 = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
                        switch ((int)com.yiyiaddon.m.b.a<"s16n75p27jazh5","+Z2fVbMcKT5OaX2AgIAO1+6KEoqkXnK8xCzsZ0sZJgU=",-1156342731468429250,4752272568781027997,6965243570187520577,-3772399373508053107>()) {
                           case 1525846461:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.put(var5, var10002);
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1lcwell763twv","Je8ZKYfyrRYVWRwrl6JvgpmZGQBOBRuv6AHC82c+3UI=",4587251211654685593,8179565524027506721,-2073657348322103576,6804387056865108803>()) {
                        case -1054592407:
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
   public void b(JsonObject var1) {
      this.a.c(var1);
      com.yiyiaddon.e.c.d.g[] var2 = com.yiyiaddon.e.c.d.g.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s7vm0sx6qiodt","5u0lZEVHfs9xCIAAMuCwfwfi2327agoqfd6c3euAipk=",-9013120531365669335,-5037657336494467994,797529024712109631,-1685394158032280676>()) {
         case 687227001:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s214kb3e0lim4n","zCZHcPkYNJpDF7xbo2m1auhk9pgf1znsHE2o04+Gx0c=",5019916419127424077,8127338063137915079,-2315098216787201077,-4228812702599801246>()) {
                  case -1934149062:
                     com.yiyiaddon.e.c.d.g var5 = var2[var4];
                     var1.addProperty(
                        a(var5),
                        this.l
                           .getOrDefault(
                              var5,
                              (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>()
                           )
                     );
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s232kpu6ogy2nq","XygIGXiKbf+hCZgVqIOEWkfRX7Kh7Z/GohqZT3ijr/k=",-932081516377674078,439054016209190360,2023484123698819132,4112600216127288593>()) {
                        case -1437511006:
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

   public void L() {
      com.yiyiaddon.d.b.e.d(this);
   }

   private static String a(com.yiyiaddon.e.c.d.g var0) {
      switch (var0) {
         case START:
            String var5 = (String)com.yiyiaddon.m.b.a<"s2xo4k2ndwax2p","mRuP9GGBmqazLCSzJTWb08WDKCItHeJ+n4zNYcvBkQA2uS6EzeVe3z4R3mr6K466CZunTM+f",-3595779395219311174,329334812496702346,-353233009279708585,-6294817312479170850>();
            switch ((int)com.yiyiaddon.m.b.a<"s2eirgot4ym4wy","m5dy2F4pKnzQT22NwFFzlmSRRVM7XygLPl6efOPIqZc=",3070881863467971790,-5250519782134666795,-5523039766911263000,2834140823625540441>()) {
               case -701906607:
                  return var5;
               default:
                  throw null;
            }
         case END:
            String var4 = (String)com.yiyiaddon.m.b.a<"s1au2jegufalfg","QJGoW4HzHZBY717ochiQ7zzHnKvhoaimxqeLOBExsZnDVU2bUmYMSBOxhlzmnF+Zow8=",-3128481008034147770,3778530121690951288,5014789299450023218,-5551366716854932341>();
            switch ((int)com.yiyiaddon.m.b.a<"s16szo1hco3dwh","52sFz0YBj7oPMYc3rUcrXUCZJCK+r0MndD71eL4Y0+U=",3541408662151710412,-2771783897985533523,3150818151885585173,5166785341635382998>()) {
               case -643118065:
                  return var4;
               default:
                  throw null;
            }
         case SINGLE_STORAGE:
            String var3 = (String)com.yiyiaddon.m.b.a<"sw4m9ktzash1t","zGtvYPXQSEEpP6LH9/CRyMyTSX+K8FvAwT5E7tti+bL4+Yhnz1CujdMRBMhay/ohQEpZurYlt3A=",-623459124885492924,-1050814916133580755,-6814161220945901892,4241050483720101779>();
            switch ((int)com.yiyiaddon.m.b.a<"s38e8zao27ni7i","kXRo44d3I+zWTkhinxT3O1A3oQQTiWOSWAYdTOH2LPk=",48647907940442067,535745043357086593,3999143093551648999,-3413220568686014715>()) {
               case 175326501:
                  return var3;
               default:
                  throw null;
            }
         case MULTI_STORAGE:
            String var2 = (String)com.yiyiaddon.m.b.a<"s3kh60k6556g8r","pZY2eGwmuemODqdqVxIeE0UukMOrZZxTKWPPgB3DIN6/4B73vjK9MKdSnWVpQ0W0kWsUM93F",7451359304078039127,-3094375598208852688,-1027811624229115100,-2409904362879589991>();
            switch ((int)com.yiyiaddon.m.b.a<"s2k5b8lm1zqj4u","B/xzKdd2AEhkbpo5P8QXlDvQ8qm7IQ2AxZ391dFSkWY=",8150303452222900143,-3111975490466966777,2219129229458550063,3409505393320241007>()) {
               case -765946508:
                  return var2;
               default:
                  throw null;
            }
         case SEED_STORAGE:
            String var1 = (String)com.yiyiaddon.m.b.a<"s1hon0g3o3utdk","RohzawziYw565jwFdSCtOnPP/ugc+ZFWhQJsFAXpjeY5lOlM38VUbnJUA+hJA3jtN4VhCw==",7464677163857373331,-6341204606197486358,4655654328758741592,-3641993778950637252>();
            switch ((int)com.yiyiaddon.m.b.a<"s1k81o82acrp9e","M5Pe0HIfab/LBxx/OVZ6btK+oMG3ZXxtLPgpZb1QpJU=",-144266047172459729,4496534299952145341,769409717967882370,1753082748479338895>()) {
               case -528738262:
                  return var1;
               default:
                  throw null;
            }
         case POISON_STORAGE:
            String var10000 = (String)com.yiyiaddon.m.b.a<"s23p3818e4ubnr","AyIVx8/Ztxr9OpDvEAMFmrSpL8KvW7SrrwtYsY1JYvFswYPwhXVlEhWUTVCNtduqEHDGJGp62DY=",-5575555574881079511,-2093712901630372526,335327404076104133,-1977964511504093440>();
            switch ((int)com.yiyiaddon.m.b.a<"s1bops0a94qnzd","JRpXhO5i3DE2NQ5AlXtZ2x/5ls2Ljaf727/njpyueg0=",-6349948726989506754,7472513599334146416,9039163201295525562,-302535669848135918>()) {
               case -670507623:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.c.j.b(this);
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.c.a.a());
   }

   @Override
   public List<String> f() {
      if (this.j.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1k4ih1bnn402t","ZWTHww4GgpRYblEXSsI/tTodhaKrzmTXa3DDih1YDCE=",3037057810290740039,-5952771372588597314,-1784945606085363606,-2831133087312379466>()) {
            case -1694603291:
               if (this.j.level != null) {
                  return this.a.a(this.g(), this.c());
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ychiryiddq15","/xVj6Bwj+fjpXzvAycjdcEkefAeXvuaipQAm3i2voKw=",-6679926704545228972,763639017845385308,-6049073875081497944,-3712071774820579344>()) {
                     case -1142682937:
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

   @Override
   protected void m() {
      if (this.j.player != null) {
         label42:
         switch ((int)com.yiyiaddon.m.b.a<"s1t70elavmu8jj","inHP8vFgKxq27/UOO+Nef2jZXgj4noPfxZrxdm9HjIE=",-8690513619335631941,8239822293060364780,-390129050131127937,-5883686234123854480>()) {
            case -532269295:
               if (this.j.level != null) {
                  this.aq();
                  if (this.a == null) {
                     label35:
                     switch ((int)com.yiyiaddon.m.b.a<"s1oqrdyhj1qvve","Re4terTQXpjAqd69fOD5kXFln6SiPKBARhJA0gUIkpc=",6400486214614761692,4607963684861472215,27465088550540993,-1588461380121512429>()) {
                        case -180520260:
                           this.a = this.j.options.pauseOnLostFocus;
                           switch ((int)com.yiyiaddon.m.b.a<"s1u368slj8cxfe","PZDsXeFjnEipqdHu5KsnVzn5Ojc8nlvB6opEM7SuNfg=",8147350886947939298,-1345062776286339285,-4643053126236728704,-5041858839362445566>()) {
                              case -856214319:
                                 break label35;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.j.options.pauseOnLostFocus = false;
                  this.a.a(this.g());
                  com.yiyiaddon.e.c.d.b var1 = this.a(com.yiyiaddon.e.c.d.g.START);
                  com.yiyiaddon.e.c.d.b var2 = this.a(com.yiyiaddon.e.c.d.g.END);
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sc7ohxg26cxo5","KT7BxB20hafhou1fB51q1Kf6IbDk/MqB0Lpu4iVL/gc=",-1284642470705198094,-2055577008238451505,-2191122427232089911,-4454304846475603103>()) {
                        case -91412840:
                           if (var2 != null) {
                              label28:
                              switch ((int)com.yiyiaddon.m.b.a<"s17nyx6dywswxa","eg7P1xhkmlsO3w8/IK5DWiW4KtNwfWo24RXz0sdDquo=",5659083886222309194,953373636409853748,-7695621863757974644,4015070266436015033>()) {
                                 case -1960268109:
                                    this.a.a(var1.a(), var2.a());
                                    switch ((int)com.yiyiaddon.m.b.a<"s3b9vobrzo4tz4","L1ddWTi+CyAktTIGvkyUimcHPNFYQRHA2YjBQaq0HX8=",-2769566455527565114,7350350449366285070,4416358634567661843,-837530633958457316>()) {
                                       case -1564599424:
                                          break label28;
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

                  this.a.au();
                  this.a.a(this.g(), this.a(), this.b());
                  this.bR = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
                  this.a.a(this.c(), this.a.aK);
                  this.a.a(this.a.aI, this.a.aJ, this.a.aK);
                  this.a.b(this.a.a);
                  this.a.a(this.a.a);
                  this.a.c(this.a.B);
                  com.yiyiaddon.l.g.a.l.a(
                     (String)com.yiyiaddon.m.b.a<"s15l5vel5cjucp","Y6hats5jhvOdaKsi18OISH9nsywPGwpPSOwrgxg/mNV2P0tHclZzrP7GO4g=",5270896215829710264,-5495009229515108823,271062044985252578,2442450476031508562>(),
                     this.a::a
                  );
                  this.ao();
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"ssec2onynwxql","h4B2pvC/Ud/8hs9tkIwDPFsGHZXiwi8MPCzTkhV02BQ=",-6790581867624609117,-3394871767437806858,7976227266497580638,-8396559968265046853>()) {
                  case -586734230:
                     break label42;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.b(
         (String)com.yiyiaddon.m.b.a<"s1kr84ll8uh87t","xaRyBZrXhwLY442vziWTI3qf5O3vRPPxdZB7XhPSaAEzHOC0Q/8uQI7iT8/qrvUVPNGPOmX5pdheig==",1942316710341236719,-7997509325673874980,-4192119622469298929,6894785086033498024>()
      );
      this.j
         .execute(
            () -> com.yiyiaddon.d.b.e.a(
               (String)com.yiyiaddon.m.b.a<"s15l5vel5cjucp","Y6hats5jhvOdaKsi18OISH9nsywPGwpPSOwrgxg/mNV2P0tHclZzrP7GO4g=",5270896215829710264,-5495009229515108823,271062044985252578,2442450476031508562>(),
               false
            )
         );
   }

   @Override
   protected void n() {
      com.yiyiaddon.e.c.e.a.e(true);
      this.a.f();
      com.yiyiaddon.i.a.a.cD();
      this.b.f();
      com.yiyiaddon.l.g.a.l.l(
         (String)com.yiyiaddon.m.b.a<"s15l5vel5cjucp","Y6hats5jhvOdaKsi18OISH9nsywPGwpPSOwrgxg/mNV2P0tHclZzrP7GO4g=",5270896215829710264,-5495009229515108823,271062044985252578,2442450476031508562>()
      );
      this.bR = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1y00aqs61r6np","TTj5yfzb66HlO1VetW9v3YOUD4TWw/sddCWlRaODAAI=",-5485240995505354075,-7898288958375766884,-3611590257454743134,89161448558893565>()) {
            case 1958890782:
               this.j.options.pauseOnLostFocus = this.a;
               this.a = null;
               switch ((int)com.yiyiaddon.m.b.a<"sjm4jcnk41qba","rXR7WlBmWLWcdfEfeWTZKw0ht5Xb0THTZYDNCARFeBw=",-94684966140256183,-8596979185830196607,7759773827007202223,761110754816591724>()) {
                  case 731010549:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void a(com.yiyiaddon.e.c.d.e var1) {
      if (var1 == com.yiyiaddon.e.c.d.e.BATCH) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ci7epwkp6yc2","CIka8xOUI9KUPsBU6WcEwHvMcAmZsKQftwFnAzgmSe8=",184553732858582100,6177689418400915746,-6429993574754510656,-7110385749117185001>()) {
            case 642733365:
               this.u(
                  (String)com.yiyiaddon.m.b.a<"sk0hc7ox97l2f","vYdqrozAL/kgT6nqNEZW340iafNJdSV6ZfqU8edkKdoxOwOWzT+umZLDMk69xZGfru4yopX4zFcecXAn89bi1fDm4kpOx/F/trpNqRE31qtdhQFQ",-2460221096070765715,-4543578828078327207,6436570701039535285,7780192186405820901>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3mjbstr0zicya","vRSfk456NraISWfpNI5cKcmFVOsr4jFg1mfqZZNLH3s=",-8703842365645408291,8531315643903128799,7332023228053134403,1535675452247778490>()) {
                  case 915765153:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.u(
            (String)com.yiyiaddon.m.b.a<"s2nz5ewqf3ip3r","dYOZTfWW9EzebogbibJJ1MYMUsrL51twPBcQwsXoWcH2uiyJmQHDBgymNuaaA+z8LxgzDMupUaM1+Dm3dMADHGbs/6tdiqQFIC7QnHSAggM=",819470944721691237,-260840615873665035,-6328957403363826612,6191291606851692004>()
         );
         switch ((int)com.yiyiaddon.m.b.a<"s34sfbkrs37ncy","An1vTxK2FQScNr6yjNzId8XSTt2soRFMmRSkFgaGyw8=",-7221537509230076178,4099988902980988667,-3952082813390007566,-2167633683793838929>()) {
            case -792544641:
               return;
            default:
               throw null;
         }
      }
   }

   @Override
   public Set<c> c() {
      return Set.of(c.TICK, c.DISCONNECT, c.SCREEN_OPEN);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjlskr6rq8txx","xysAYqShxYjkMvI1NKkdl3KWnzuGkg6qsdaSBecVPIE=",-5336479358714902852,-8699656280169026429,-1248247850408174517,6408097446927889303>()) {
            case 1663987140:
               return;
            default:
               throw null;
         }
      } else {
         switch (var1.a()) {
            case DISCONNECT:
               if (this.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3mjuzcp7wpq5l","cg3yAdMXLE2eFI+rBFy4YRJlfqqKPXq3HP5a3pEqX/0=",7023258579007283522,-1922443427741554835,55943117384305079,4411403322863426831>()) {
                     case -958779317:
                        com.yiyiaddon.d.b.e.a(
                           (String)com.yiyiaddon.m.b.a<"s15l5vel5cjucp","Y6hats5jhvOdaKsi18OISH9nsywPGwpPSOwrgxg/mNV2P0tHclZzrP7GO4g=",5270896215829710264,-5495009229515108823,271062044985252578,2442450476031508562>(),
                           false
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s14oxx1hzha1bt","Nz4pvb6+psMcCVM9Z4zNkxZ1YslIlFgWiH293xMxgYo=",6864014965557359209,2898402843017784536,-2692650479701797016,-2878975475874856260>()) {
                           case 880038828:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case SCREEN_OPEN:
               this.i(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s34v4sj6d2nz81","ptfVStbbA2OdoU+DbMlIZi2TNXOqtn70EjWfJp2ruLY=",-6134462756152267970,4364617918986054988,-144669282328680094,4559591831308136528>()) {
                  case 373456205:
                     break;
                  default:
                     throw null;
               }
         }
      }
   }

   private void i(com.yiyiaddon.d.a.a var1) {
      if (this.j.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3395kmnxq74ql","j1B1JE9BzBtEkCvNKhE1F6lc8q8r8xmnXlxrLLFkgOM=",2811744041790937860,6304923749751128972,-477616242235625953,1486761644634738364>()) {
            case 908975449:
               if (this.g()) {
                  String var2 = var1.l();
                  if (InventoryScreen.class.getName().equals(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2lbdk3ueqbyqj","GZglBTUy2LhSMA5WmAPBJVBOOhIefo1W0S5XvZ252WA=",-1378478825152291755,5528597724205017575,-1975931598350048143,47648535945511675>()) {
                        case -1780349348:
                           return;
                        default:
                           throw null;
                     }
                  } else if (!m(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3n2ki1u2ecau2","W8rk1qIQYiRi0mqdaV86h42hxFw0E+1HcGtimd5FSYc=",3593657180464106908,8612930831155461375,-3793780336490552797,-4016580566375437279>()) {
                        case -295044160:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     com.yiyiaddon.e.c.i.e var3 = this.a.a();
                     if (var3 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2k9tf6hlxvz6r","jNgd4et6LGCSSM0Qf8J8uWFrJQYQjcGP1fpkgJZjGu8=",2246658600393412237,-5589022155018211355,-959100389721603778,657798822080105973>()) {
                           case 1684651942:
                              if (var3.O()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"simgelylbbcrx","CVzkMHzxZ9HF9fxeCw/r1kCpxQ1llNOrg2+GC5YnAnI=",-1965657828150789649,4669067635138613150,8920528816000601284,-8724690622051439720>()) {
                                    case 1240633644:
                                       var1.i();
                                       switch ((int)com.yiyiaddon.m.b.a<"sb04r9l9n6kfn","uiKDEyJnrf3AtDHg5UeWFb0XT2MTN/jlWqFJpp5qGKU=",7056398224643207285,6130052544053352864,-5077864322783246335,4427751815035421425>()) {
                                          case 149935104:
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

                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3oayeml63lpad","m2uuHnqSQYSOUB4eNu/NiKuAWjN5fxzdrULKI08mtgM=",2314846246291112893,8408654770823984245,-5902534211474285928,3847947228101100152>()) {
                     case 844397419:
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

   private static boolean m(String var0) {
      if (var0 != null && !var0.isBlank()) {
         try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(var0));
         } catch (Throwable var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void b(Minecraft var1) {
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32rqozbjwxxsv","zYBlKnt+HaYBpuHt9VJHXQ4kLGmk3t9YWN8OwDANMic=",6804478829818620843,-4340942294079507393,-1489346784074773537,1471838205128499787>()) {
            case 1033950109:
               if (var1.level != null) {
                  if (this.a.A) {
                     switch ((int)com.yiyiaddon.m.b.a<"spey629qz1nug","3jXx1HSwNIH8/UpucM1PbmJ9mLaS3dch3bdhdChNjbM=",4849824849146917888,7884229428339378614,-3957374508601418822,-2670456057332276651>()) {
                        case -644759053:
                           if (this.a.k(var1.player.blockPosition())) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29vxbgayv5vxt","Qz+W6JtD27+kc6DGurPxLB6Qj4hD+F9xLR1F3UeuFhk=",7588033953598649219,1789402344843712094,-2116233616955840210,-6715378328044879041>()) {
                                 case -739698255:
                                    if (var1.options.keyJump.isDown()) {
                                       label26:
                                       switch ((int)com.yiyiaddon.m.b.a<"sqclqefnsklqm","fqjXJKAIRChuKm5XQJsy0uQh6+W5dvLFBEN5nP7sbhM=",6732328212033223213,-4032518982256107193,8798231001123628275,3625354634899206620>()) {
                                          case -1153377125:
                                             var1.options.keyJump.setDown(false);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1l2047w88a7qe","ZgTlrfSdNsomoJnVeQWh6JIIlse8bM2KlA9oVh3CqOw=",-8884735635937251787,-1016253537656424230,-784318295213829305,6370473814052264075>()) {
                                                case -1627008003:
                                                   break label26;
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

                  this.a.a(this.a.aI, this.a.aJ, this.a.aK);
                  this.a.b(this.a.a);
                  this.a.a(this.a.a);
                  this.a.c(this.a.B);
                  this.a.a(this.g(), this.a(), this.b());
                  this.a.a(this.c(), this.a.aK);
                  this.a.ae();
                  this.a(this.a.a());
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3tevanphbkc9h","3tr0os9Z9TDL0z/WybWr8C3+c8wwElM+1HNU0zdmjkE=",3705587764993148189,8661885759253685610,4957904463071618589,-842335396127297028>()) {
                     case -1039147980:
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

   private void a(com.yiyiaddon.e.c.d.c var1) {
      if (this.a(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gs3kdqozy821","CY/qhioIj61UgW0P+/H9BU3eHa1zUId9mHcYMQ6LMAg=",3645937475507904921,-1693159279346664729,-4684139241438739971,6018621048242215533>()) {
            case 1875610572:
               if (!var1.af().equals(this.bR)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sg4an31gdk4et","upNj8iSWBUv04SJ/+jB1royHUnJLQeufy4LS9XTeRew=",2499607739393869441,5598405060042777499,346588007952053895,-2625471202967438969>()) {
                     case -193472366:
                        this.bR = var1.af();
                        this.u(var1.af() + "");
                        switch ((int)com.yiyiaddon.m.b.a<"s1kjtllusw10kz","ytzyVjTQUi+EmGu7K/VMkxT75PF8sHKvhLUjWHCVPpo=",-519241051126758525,4603678273605889570,3300564164503201339,-7315514154622798188>()) {
                           case 542042243:
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
      } else if (var1 == com.yiyiaddon.e.c.d.c.OBSERVE) {
         switch ((int)com.yiyiaddon.m.b.a<"shkgrqet4vz1y","JgZHKJHyR8jJx//GwLS5FtU7j6Wkylrd3Ehs2VId9QE=",-5957303562378026309,4755412575609230128,-3244525027930853475,-5097763678683619998>()) {
            case 1155634543:
               this.bR = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
               switch ((int)com.yiyiaddon.m.b.a<"s3ezlltkiyjiz5","Mka6NLInnqjdDMBi0RPzcs1/dqS99DPFRMJTl3H1+p4=",3056517796773109038,7456376624218918983,-3359209954721719163,-3588289728701631127>()) {
                  case -656636160:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private boolean a(com.yiyiaddon.e.c.d.c var1) {
      if (var1 != com.yiyiaddon.e.c.d.c.UNLOAD) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bwbzy4wzhrfz","umAUzmkHORNH9KVpypbhyE+6qc7u8qFLEVynKiV7lqc=",-6913014987533331657,2233246286012098029,-3517008220433084910,-6923320334095797952>()) {
            case 1056638825:
               if (var1 != com.yiyiaddon.e.c.d.c.RESTOCK) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s86lgneawgoft","IMDSbtTE7qwae16pvha1Y8Ud7QfVzXmdJWekSxRI5C8=",-5739108159207397326,-3826566872013004479,-4640370843096827762,5886517713472701749>()) {
                     case 1421605804:
                        if (var1 != com.yiyiaddon.e.c.d.c.POISON_DUMP) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2vlbvx4oiljk7","FFz/fQx/csnZ6RUwUYC2LNx6rAosivt8pgJ6I8yo6HM=",5865176723561162382,-743583368973948614,3726118301588783753,-5111624871834733368>()) {
                              case -131824449:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2149pk8jofl0k","m4MgMf9ge9W16TmMRB/nK8ik2+O01JeJLRKmaX17mxs=",7843413986777448461,8054312230279375988,-624397827000510579,-184337233327726636>()) {
                           case 1422245958:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"s3sa7qu7u7m10n","1yezVBkejzFBNnvvU/njxhwGJpq61Yh3Al3o2vsid+E=",-9199156222905446485,-396178763654614908,6302400512857322761,3957648276982031703>()) {
         case 458283166:
            return true;
         default:
            throw null;
      }
   }

   public void u(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sbzkk3jdkips","d+ePcd8yNxb9inv0pThu2UKZ7N1/AjCbZ2ITXW/Rgyo=",7797818429279412706,-5886143189098797899,-5949641332140680421,-496141142557267602>()) {
            case -2035738859:
               if (!var1.isBlank()) {
                  com.yiyiaddon.d.c.a(
                     (String)com.yiyiaddon.m.b.a<"shhymj6dqyhba","vt7cTZW+t/XryKWEUwVTGYmCiqM6vgB2fBrtUNOg+f5Dg2aK",5522978535738040898,-1847537027349733158,-433027615928203401,-2358902848112082257>(),
                     var1
                  );
                  this.v(var1);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3egtjizj57c9z","Uy9ySwJ39bu6KEO0jIrAmXF803phArHA4YKG5JH2DR4=",6203065763876587704,4235872952431801461,3817379360703966374,-4614138428156819174>()) {
                     case -1533480265:
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

   public void b(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"shhymj6dqyhba","vt7cTZW+t/XryKWEUwVTGYmCiqM6vgB2fBrtUNOg+f5Dg2aK",5522978535738040898,-1847537027349733158,-433027615928203401,-2358902848112082257>(),
         var1 + ""
      );
      this.v(var1);
   }

   private void v(String var1) {
      String var2 = var1.split(
         (String)com.yiyiaddon.m.b.a<"skbgylv5hh084","60Lg3+tD5SgjZMW+bZJB0GwvfWzZAc1a3BTpU38e",8329235857249349673,8601332199366802910,-826600407899644838,-2270785564014112880>(),
         2
      )[0];
      String var3 = var2.replace(
            com.yiyiaddon.d.c.e(
               (String)com.yiyiaddon.m.b.a<"shhymj6dqyhba","vt7cTZW+t/XryKWEUwVTGYmCiqM6vgB2fBrtUNOg+f5Dg2aK",5522978535738040898,-1847537027349733158,-433027615928203401,-2358902848112082257>()
            ),
            (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>()
         )
         .strip();
      synchronized (this.a) {
         while (this.a.size() >= 80) {
            this.a.pollFirst();
         }

         this.a.addFirst(var3);
      }
   }

   private void ao() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
         (String)com.yiyiaddon.m.b.a<"s2pgs65lsd1kpz","43Gccpau4nshGGSS4I5qtZsTsi7shTpEyH7wgxGkWsJGfZaApNpTtH0hG6ajk6QR/Ojm5aSSyWkGsLpkS60=",-5120481474492530570,3043365055288854072,-7888085048341093441,-8447876877405534217>()
      );
      Set var2 = this.g();
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;
      Iterator var5 = var2.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s17pignkv0eem6","7R+6oInMl/PgPXnejPawGuj7Ab8WiQQLWt/RTGcCtVA=",3838309868725312156,-2324959379031498806,4963042776318474608,-4201855330408974437>()) {
         case 880105194:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2aodp6gyzmbmk","BkV8LS32Me/jtBpmxR0ruWIvU0NhemLLNqFLwToVpww=",-8784828811325789139,-7159354272106649616,8798879555856858464,-8118465034358953001>()) {
                  case -332100182:
                     com.yiyiaddon.e.c.d.a var6 = (com.yiyiaddon.e.c.d.a)var5.next();
                     if (var4 > 0) {
                        label145:
                        switch ((int)com.yiyiaddon.m.b.a<"s394zofm7tz83e","owrKW+6enUg1ib0eucsJo/NZX9dszNy2VckZspEo7Wo=",8055063476828890443,455308465402559483,2584784871538923125,-2298565637035443535>()) {
                           case 932418975:
                              var3.append(
                                 (String)com.yiyiaddon.m.b.a<"s1bjn9srh8nar6","vRLR26+rpj78s/uyTqTprz0ro10Hwl77l9+jev4PmfjILw==",-1847578674145691467,1589137823400915978,-7548996253040779909,-6589590658908644164>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s1lpdfqfwz7zb6","oAeefpMGv6Z61zWu60dvHdFRE8T7z2eGAzO9kJc6JgU=",925262450107488693,6541825753725940227,5574938425991249941,-2871397611032556953>()) {
                                 case -860426995:
                                    break label145;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var3.append(i(var6.m()));
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1t95e3g8yiwpr","NyaCzMMIT9VeCncyONYi/iiaTacYMS8S3fw7Yu8dRpo=",7462799577029374558,4316699897480781692,1006582348564654063,5714927445789285364>()) {
                        case 1577690787:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.append(
                  (String)com.yiyiaddon.m.b.a<"st230b7e5qy6q","mkscl+pTsDnDSpMZSks9ficvvbEoKwMw0QvEWCi1va2CsF5/gZ9S7CqGDLMUR3AxML9xsw==",-1028476288384196069,6004410678210777760,204568251954216159,5216523737046213125>()
               )
               .append(var3)
               .append(
                  (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
               );
            boolean var13 = var2.stream()
               .anyMatch(
                  var0 -> {
                     if (var0.F()) {
                        label22:
                        switch ((int)com.yiyiaddon.m.b.a<"sl45ak7upjky0","c8k+zOcKR210oy7K9W6XF5wx2wZIZL2NetK1+YA4xFc=",-1698651735220375473,2682729378684411520,8687887053325160040,-5883887937210546300>()) {
                           case 1827688686:
                              if (var0.a() != var0.b()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s24c93i60yx14b","gu43wIttDm0awBtMuNd70HoaFWRsvdJzUm1DxA120uk=",-6519589149654626916,-3269748900565455153,-8465009148031221905,3407425461158239441>()) {
                                    case 750399837:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3tun7b4tjr6pi","4Dm6UqOOwnmje4UTCFZxRKgkFPw8mTmq7jtXn00PofE=",5018120384418784369,648540119258189191,-3181146719226856652,7342187283817891033>()) {
                                 case 1876004617:
                                    break label22;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s15u5174mv8rt0","3LJsfnzO4Y2tda3SPIG5pvYVS7Iy//sq30Ctq0kpRCs=",-2752488261584874690,-7496931726264877615,1699043173522775117,-8299606616131153868>()) {
                        case 1081883110:
                           return true;
                        default:
                           throw null;
                     }
                  }
               );
            boolean var14 = var2.stream()
               .anyMatch(
                  var0 -> {
                     if (var0.F()) {
                        switch ((int)com.yiyiaddon.m.b.a<"sqyja7l5p2dkk","D0MxRr3gjLSpZ1wLEOE7yB9wD6czkPKJDhpzdpP0rX4=",-4646806009917289081,-3985058054307726620,5970507184329043656,4560530056831681224>()) {
                           case 790861458:
                              if (var0.a() != var0.b()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ifzpsjvd3wdn","6nLzxe/CfCJCwviJZ5oKBxRsMxofP6jCz2qafNaoook=",7570609547971665425,3194591554475973298,2933660100093775898,-7099393403942560422>()) {
                                    case -7271105:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1gi83x41pnk47","HELlOEaoMKkGzjPtyw133ABlHfYcBOV1tB4n2jKIVw4=",7409600344975357972,4641803945379765032,4962590591697717421,3747800030090381680>()) {
                                          case 936579619:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s373tz89dh6rbv","7mEBMX1O9UqQlnIR/1/VLpDdhDs5q5GIVCmfg+4P3y4=",881302509614574188,-8319295098254192780,6126170268020447972,-6857353601606020600>()) {
                        case -907556633:
                           return false;
                        default:
                           throw null;
                     }
                  }
               );
            StringBuilder var7 = new StringBuilder();
            if (var13) {
               label139:
               switch ((int)com.yiyiaddon.m.b.a<"s2clwtovd9e1n7","CdtmZeDONElTWK4gojhF3MxsCKqZKllgPcS+2Uv7Lug=",7379377029086270912,-3819145906697681999,-2425675454200817640,6809363294285135542>()) {
                  case 487469002:
                     var7.append(
                        (String)com.yiyiaddon.m.b.a<"s35h6owcofl104","rZ7yOdO0yCG+vhI5f3Q5/gS7+hR9ePwUvQaI2lav5P/R8Q54UcY=",-7528994205482158212,2729421386979695040,-3128520566160423934,-4216505740781030611>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s2kz7amrewdzap","A1mORqyVyfGHXWW6ric4TRrFk/ACiUDYCIkKe2+iO7Q=",-8365181813048028118,-8410368545999941694,-2777349032226946735,3316603566122142474>()) {
                        case -755553367:
                           break label139;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var14) {
               label136:
               switch ((int)com.yiyiaddon.m.b.a<"spt0kdu36d17q","lXGMbd4iUWqd/OtpaKTQF0+OP3whovlic1cyKcDbMc0=",9073546231378233521,5390895438150931129,-8062110146927052929,-723930872038078675>()) {
                  case 1723279658:
                     var7.append(
                        (String)com.yiyiaddon.m.b.a<"s1de98pwthi2yo","vV5YSRhN6zjfqAItVKgji6SQSEnA14cL9lLZA0uuMSkJbKTRNBiP6A==",1204560252017014674,7969311002543217185,7040795189238035224,8537898167671994766>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"syjig4oi0bg5f","xwM+3MjFi/ky4tL8iSH+PtEpDPHkmJGdGEAcYvyN5lw=",-6349997046909962928,4761418077257610112,6356569648342755910,-499290757438265001>()) {
                        case 1152191440:
                           break label136;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var14) {
               label132:
               switch ((int)com.yiyiaddon.m.b.a<"s2uvfww61p2s9h","xqW9Bj/cjE8R6fkLbRLfujDUEbk+lEhctbE0abWNYqY=",7036926903766912043,-4232180076936633110,-5167402762042617298,-3816157741297131103>()) {
                  case 134748573:
                     var7.append(
                        (String)com.yiyiaddon.m.b.a<"s1fww71boknzu9","111/eLNf5paD/RrvfrrRfa0dUniSU8TfJxcsd2CzZtJCcRG6s1I=",-5676139217598926187,6735465398748660096,-3903117432997286489,2768265484377364638>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s6fywlaxmeaxv","IiL7OcPJgU5ZXdSVuM8wnu1Euh/tQddqthOP9k000ZQ=",-3105985450781825860,5946149674224372928,6822897899771688725,6356500454776304478>()) {
                        case -1362224206:
                           break label132;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            String var8;
            label155: {
               var1.append(
                     (String)com.yiyiaddon.m.b.a<"s1xisj789b4s0r","0/ooR0NR5BKKHWP8Rts1CJK0+BaldNUwrJW4LoXKUJR2IkHyJSIJgpTVZ4lqW5QoDNE8cg==",-6659276387988328055,-4681763353259070099,7924108811803994673,9053060212933318533>()
                  )
                  .append(i(var7.toString().trim()))
                  .append(
                     (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
                  );
               if (var14) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2cc9rajzrhntl","DTKRpU+0D7oJ3J4zwijhuOgMa3Fjl5WZhTzsNI69SVc=",-1325757646249844616,-6509313508887767585,-4455775386565405622,-8879135504440209095>()) {
                     case 358745118:
                        if (var13) {
                           switch ((int)com.yiyiaddon.m.b.a<"sohn99erf3q8a","xQB/GqncfpYqrBeAkwjjmDKu6WHePa6f3gHhCeA+jTM=",3588755256036208226,-4247998449726646506,1120614574071939930,-6535711543125162044>()) {
                              case 1324808254:
                                 var8 = (String)com.yiyiaddon.m.b.a<"s2fbvhdejqqw3w","hSUrz2LkiXm/gz2JLt2yXo32eNgxsRbaLv+BvU9Khq9rQMqPmkMAXskC9F0djRzINODH6yKs",-7315104834784880536,-4714518547359172874,-8498389768301274206,573837612106289834>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3i49da7l6pmuy","pcgTn/b65UN1wZk+dyoULI1/cwJJ3DD3CmoN08mQmWc=",-7568683759161227708,-1115591354861088973,1263027212561935361,-8090871814824141970>()) {
                                    case -450542670:
                                       break label155;
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

               if (var14) {
                  label121:
                  switch ((int)com.yiyiaddon.m.b.a<"s2ue16q9fkufmt","dVDN6je/kOa5wVqKLqFY40AF4dI/WXip4bsJ2bhhz+I=",5309992303677192681,-5530527392133240401,-2801083805450139565,6545560921399058067>()) {
                     case -7002599:
                        var8 = (String)com.yiyiaddon.m.b.a<"s2gipz4gcji6jo","RSiefVV6Ns5McmzHB94HwC+ZVuj2ocAucg5cVualOOshczRckjwwVq/Unu4RrUheLEZqQ8wibBjr2Q==",-2972439271739811738,-3997558732213574434,6682044316225545234,-4015433412505978546>();
                        switch ((int)com.yiyiaddon.m.b.a<"s11ctd4qkbku7m","NqJZ7iEAk2xxbhg6qTMRWRvQ+Yu/8ftsawvtkk1eMsU=",100469121900031071,-1507633932341843763,8822111459460667798,-5019690688211502370>()) {
                           case 334480246:
                              break label121;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var8 = (String)com.yiyiaddon.m.b.a<"s3ei3vmmf11px3","E4f5vIKpprNwhaH/cfp1F/7SAZF6hwRBFaN6/+gEia24h0zO847ElXWjs4rH76d/",-3302244592689049529,1965195597917866891,2053247302176414990,7599643729098816209>();
                  switch ((int)com.yiyiaddon.m.b.a<"ssmfzn2un4em5","BiJ+xDlZAamMcA9KeGD0MhdV1n2DTUXx4eIednPbm/4=",-8943482976041463498,-4645545489200777802,-5778437095881002952,-7718964586546029025>()) {
                     case -176909521:
                        break;
                     default:
                        throw null;
                  }
               }
            }

            var1.append(
                  (String)com.yiyiaddon.m.b.a<"s2aj1kl2vfeibn","8D73ztOPf37bVyh3VjCXGcZMWmkoIZX4UbfrpDj8l2u7emPZkRao2yxCSOIxAuiHfH1VsQ==",-8894967105414172090,7194400299451613617,-1838566394966906933,2972138879165309873>()
               )
               .append(i(var8))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
               );
            com.yiyiaddon.e.c.d.b var9 = this.a(com.yiyiaddon.e.c.d.g.START);
            com.yiyiaddon.e.c.d.b var10 = this.a(com.yiyiaddon.e.c.d.g.END);
            if (var9 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s216y12xk1f0fc","k96JZeZWgj+gGo/oqPcM2WiW9hMzXzqjZJrO1bLkp/w=",-4185639181557741273,-2533467327240900438,6366443149536874333,-7705575382774288486>()) {
                  case 2000820003:
                     if (var10 != null) {
                        label114:
                        switch ((int)com.yiyiaddon.m.b.a<"s3smph9v0lhso5","k/XgwrpZ0lEBkeqq3/hGlByYIBbpHLDwUU2xBLigDMs=",-7998910755776956087,4342151184325452561,5694179623507576106,1584558641298970854>()) {
                           case 1165032692:
                              int var11 = Math.abs(var10.a().getX() - var9.a().getX()) + 1;
                              int var12 = Math.abs(var10.a().getZ() - var9.a().getZ()) + 1;
                              var1.append(
                                    (String)com.yiyiaddon.m.b.a<"s1uzxk7cglq6fx","Qz/L82EOS/RHyY/wUtx1iPdHNIyMHbWMZylViz+LIywP4jIClscIgjhV0g6pCAmWtGIQyQ==",8952054144641490195,-3949514951156971472,7328542309831160275,-4057507872046752086>()
                                 )
                                 .append(i("" + var11 + var12))
                                 .append(
                                    (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"s3rduxf6za28j9","MvrU4K0VvzdQkdGsu8UGxJyQNfBpt3z0w/EGMS9eBcs=",6506822240772389531,-4432743648436249453,4733473664600801520,-3387093421882522513>()) {
                                 case 1669255542:
                                    break label114;
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

            StringBuilder var10000 = var1.append(
               (String)com.yiyiaddon.m.b.a<"s28o290f51d50e","ZfkMVSe8qgE3UlUSy/6cm/o+WzS74OtcjZqIz/yK2zxgop23PTY37O3yjMxQMocCk5XTlg==",1427208397333849375,-3098712487549181819,-5294919691491646372,-5086731838719585230>()
            );
            String var10001;
            if (this.a.B) {
               label107:
               switch ((int)com.yiyiaddon.m.b.a<"s3k2wo627lstsj","0w98PMXA5zMA31uiJvAYX08HE5f62XbulV/XCtCHZOk=",5117899234485027527,7794607539902942841,8210140969286294162,-8787368191068342261>()) {
                  case -1693733724:
                     var10001 = (String)com.yiyiaddon.m.b.a<"sdw5iaic67y3a","0pwgk9A8MnliskPUHDEn/9tMG+/DJ3bLgIw+9fxqYm3Tzw==",-6461731858763218309,9164796013826798509,2667457317706248942,4285920377601230218>();
                     switch ((int)com.yiyiaddon.m.b.a<"su0w2wrdsqtrv","RkWQnqld/XjDL8j4IYtXsZgsVaqZAb/F2z9e2FaN1oY=",-4714104187344013575,-7651542694261019840,4555461679009763777,-2231345407764100298>()) {
                        case -1156382295:
                           break label107;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10001 = (String)com.yiyiaddon.m.b.a<"s3j0drudp5z6z5","afAUSeyDKtpR3m/O9xBYLRxbpcNursPIRmfjfI3X2QAp/A==",-2411843769834463508,4535523385842845365,6699210046950634019,-5650970188179966574>();
               switch ((int)com.yiyiaddon.m.b.a<"s1xi9hsw73csm5","3eqJKOdHLMuvGzCsVQEsVTHK0tOv2b520POIYPDe3zM=",2848311953602435106,-2463931816545458359,-6223478835870874714,1322166683484236977>()) {
                  case 1206134523:
                     break;
                  default:
                     throw null;
               }
            }

            var10000.append(var10001)
               .append(
                  (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
               );
            Iterator var15 = var2.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s35z8gkqc86e3s","dS5XDOtxBb6IQlANb7fooLSFiv8IssGy+BIUEj0ERK4=",7829985119596919081,2252316656973320070,-8453520815757372877,-1473408142831836822>()) {
               case -1945504832:
                  while (var15.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ck2a8abf9vrg","cCgopu856hPWGz2oKNgWiso46nO5foVXzuw3OyRxfTM=",-7115627480669332998,-7910730002349543802,9012022697505441342,-7970150939850769520>()) {
                        case 2066370459:
                           com.yiyiaddon.e.c.d.a var17 = (com.yiyiaddon.e.c.d.a)var15.next();
                           var1.append(
                                 (String)com.yiyiaddon.m.b.a<"s3awfnklv6tqh0","Ki6ap4ih7PsqS5WQLEbLkvWS5iRb1E0DnquLheHu0uLwTQ==",-7196281800237909091,-2937528985741161916,-3025246134266740741,5811821722870570845>()
                              )
                              .append(i(var17.m()))
                              .append(
                                 (String)com.yiyiaddon.m.b.a<"s21bvient5wo5g","3hWEfJt7W8RlLeQTgPF8plnIyyKbD61ditvCH0LpCNrjQoij9zV4hu928nMpTw==",6554383216833774073,-7313505791335185520,8748691082665767068,-2335013249450326287>()
                              )
                              .append(i(this.a.a(var17) + ""));
                           if (var17.F()) {
                              label94:
                              switch ((int)com.yiyiaddon.m.b.a<"s2cpjp01n59avz","VuEgfRS9FRQiJZLXf9bqTjlFoPb4PwY6zSQJhrL9H6s=",-6883450716296063290,-2608613466565490111,-7739732278398224320,7720502925493260151>()) {
                                 case -1714551045:
                                    var1.append(
                                          (String)com.yiyiaddon.m.b.a<"s10unn39tvokhj","P3AHsKNWEwH/KnhUyl5iU2clopP83/ySsfU/1O8z+wWEAdfh/TXwQIYY",6275125628271519840,2018770667565632426,1161505773633871590,1284248983849656399>()
                                       )
                                       .append(i(this.a.b(var17) + ""));
                                    switch ((int)com.yiyiaddon.m.b.a<"s2gqltnqf60a0s","ZeNwRPds7NKujP/s6LgJaic2neNQLdKVVVNvEGcUsT8=",4383975977326504299,8314113417523389806,6353183004423467555,-7544296220459831223>()) {
                                       case -2040762029:
                                          break label94;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var1.append(
                              (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s24jhr4ddiqdyo","Ln5Z24HxnomicxgyJvbkxiNzSia/LHA8mlq/OERhhkM=",-2754157469037183961,7900804394444640198,3055611091339297692,1618335399387810659>()) {
                              case 75185890:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  String var18;
                  if (this.a.a == com.yiyiaddon.e.c.d.e.BATCH) {
                     label84:
                     switch ((int)com.yiyiaddon.m.b.a<"s2k2xycbrmgtw5","cICMQmu3z7P9HHgRFcuNnFsTQ3g7F6urirFqZJ4tCzY=",-4820836503977256242,6488251694826000107,5955882430141665398,-1306922065362275106>()) {
                        case 1097551386:
                           var18 = (String)com.yiyiaddon.m.b.a<"s1s639jzn5hbh7","gk0p3nBN20nkFAikvtfrpj6dnHM7wZds5y4yd0yN91Q8GMEqSF5XsCT9wNOh76GS",-7369733416579817703,-5692164345118527930,8551987160907214072,-8656380778408355643>();
                           switch ((int)com.yiyiaddon.m.b.a<"s38ro072mrv642","VgtR1oolcG9M/uB0Nn0R7CFZBwi1N1NOoHnW9OH9sSw=",-644645571362259215,-454295135768725526,-987813679257700000,-1322881327020830841>()) {
                              case -1111242199:
                                 break label84;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var18 = (String)com.yiyiaddon.m.b.a<"s352t52haastv3","glZov6M0tztmiRmRXfjCjlQaan/Ky7mbnRUwVwFIf8oqinww",3154698419705261560,-1428238690390867271,6327390020447692192,5684753309127083396>();
                     switch ((int)com.yiyiaddon.m.b.a<"s1qqg65bxpnqf1","CkK08mGs+4ewRL5dDXIkzKj/OYQh1bvG5suwryPNZSc=",3576559026757221368,-8341590900839384265,-3876215519778734301,5896369621830785963>()) {
                        case 159753563:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var16 = var18;
                  var1.append(
                        (String)com.yiyiaddon.m.b.a<"s1x20je5j05hem","31l62Whz1Kac9j+/Hdc+UXdnbHjFSaFDk2L//91h1xygOd1Iog9vO5JL2pOA+gRLcmRZDA==",1728525637415098667,-3205257137601214558,-2356437561988202338,-4007889543467933486>()
                     )
                     .append(i(var16))
                     .append(
                        (String)com.yiyiaddon.m.b.a<"s271psx6ouwj92","6Df2FenBkIoLEkQq8WzCi38UZHUDYlTZdfiDkDxaBF8=",2136852102196664403,4249004270026538930,-4262401099575225740,4146263653460799159>()
                     );
                  this.u(var1.toString());
                  return;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private static String i(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3cv3xcu3r224k","7FVb34Y+ZD2cNVBWY9vpXoQaKEX50AAaNJcUB1qNsAA=",-5158861975522672716,-9036893852179892357,3278360528435582451,3310701484012238508>()) {
            case -293730038:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
               switch ((int)com.yiyiaddon.m.b.a<"sl191l08avrs","wozoPKhEq9aOtmlfzDKprT0/m3gtEK9gBrIQcHEK7Uo=",-6505019873464773412,-5773889126756001059,46938879711679540,5365388231538584738>()) {
                  case 492213940:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3e83tg6qlbt8w","zAokR/gQkNAqa5oZWkCdMIzS42fsY5DQweOlcYamYOI=",-4924303610557576770,4859127276545865166,-4259107157946793696,-7834775909524940960>()) {
            case 593332793:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   private static String j(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1svf150z99uin","gOfG+1hG198sVJcAV16cjnfmHqG17y5Q239ucicLfLE=",-4056008906988761359,1292793808009439966,-7935392933220806769,5820853480893063306>()) {
            case -416687834:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>();
               switch ((int)com.yiyiaddon.m.b.a<"s3kmc98t0l66s","9G7AWzMS2oYKsXkAw3zUy2T83PlsMriGov0TdtBpOHk=",-9135816461539125728,6531012236971518571,8948553568387119618,9200884844104648185>()) {
                  case 1418302444:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1bqgeo90sggwp","OtT73ApMFcyuWDHbvpqKQecvOo9V/YyCvdjc1AjAScE=",-5277916464248341400,-7960275056431749418,5027149017831486840,8288772613863810711>()) {
            case -1815805291:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.e.c.d.b a(com.yiyiaddon.e.c.d.g var1) {
      return com.yiyiaddon.e.c.d.b.a(
         this.l
            .getOrDefault(
               var1,
               (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>()
            )
      );
   }

   public void a(com.yiyiaddon.e.c.d.g var1, com.yiyiaddon.e.c.d.b var2) {
      label35: {
         this.l.put(var1, var2.ag());
         if (var1 != com.yiyiaddon.e.c.d.g.START) {
            label28:
            switch ((int)com.yiyiaddon.m.b.a<"s3b4h86os0z4bq","aesKhqLRLCIKQKxyHB1IroyrbRGxXzva+l//5rt5LTg=",-5222244273685300595,902912735055876706,5646853196959997090,12499845398955856>()) {
               case 631004668:
                  if (var1 != com.yiyiaddon.e.c.d.g.END) {
                     break label35;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2cgnuiz9rqw3r","ITfHDWD7lr2Jth900uoYekPEw+ICnZH4OeRhgpsHj0c=",-5535482125298368548,6827828342464194684,-3924992514940620693,1596659282659920650>()) {
                     case -330668993:
                        break label28;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         com.yiyiaddon.e.c.d.b var3 = this.a(com.yiyiaddon.e.c.d.g.START);
         com.yiyiaddon.e.c.d.b var4 = this.a(com.yiyiaddon.e.c.d.g.END);
         if (var3 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2i928ubk3ilau","+9phdy3RwxGkyJGDOI0LLH0bmARxHFrVuELLgFmHgxM=",4202656694761896914,-8814629159881407379,7331357976844984803,1764562964374917265>()) {
               case -1682547333:
                  if (var4 != null) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"s3lcesi7svwhjk","UzbmrknBIDCRvgAMNeo9BEuOQJ4zT3O2SzbwbCQEuW0=",-2573533727373851856,-1220357160126178660,-7333168572458981720,4257126296473128236>()) {
                        case 1550453711:
                           this.a.a(var3.a(), var4.a());
                           switch ((int)com.yiyiaddon.m.b.a<"s22lid2rw3vc92","m8XBgIfPKcvFZXftgi+chGmsOE0/w28Ne6wA77ZcKDQ=",6331727219061208358,-3310122125058778188,-6720128737986107490,6370284811739943402>()) {
                              case -1557987398:
                                 break label22;
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

      this.L();
   }

   public void a(com.yiyiaddon.e.c.d.g var1) {
      this.l
         .put(
            var1,
            (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>()
         );
      this.L();
   }

   public void ap() {
      com.yiyiaddon.e.c.d.g[] var1 = com.yiyiaddon.e.c.d.g.values();
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3tm6u0je8oytc","ustutlhOW+zGeuK55XueOkwIfGSeARnpcT5iLXXEpRU=",2660164568017214329,-7775785024680838795,2260614636475827586,6851177771769390499>()) {
         case 1257236684:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"sr923q31pg253","1oBgknPWuBjoaqCHSHCDJ3YZkHRyDnaCzFHZf5Rmis8=",9158302043910881804,6641825312743456132,-8221381210527386758,338074664269416436>()) {
                  case 1987320849:
                     com.yiyiaddon.e.c.d.g var4 = var1[var3];
                     this.l
                        .put(
                           var4,
                           (String)com.yiyiaddon.m.b.a<"sogkxt1vphjv2","r7jJ1nOQoiw0HHtcIjwunIP1IZPH6jId5UP8Hg==",-7765715700213609563,4432681815184712457,423169822906090361,-1211423352828783631>()
                        );
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1w2sprh8qbni9","o99m45mn++ScIBcXu/sHE+ZlWWG0z4byvwiA0/zrDAk=",-3844763788399052610,6790498386851916851,-3645703208597351822,-4797741897905242984>()) {
                        case -388346312:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.a.f();
            this.L();
            return;
         default:
            throw null;
      }
   }

   public Set<com.yiyiaddon.e.c.d.a> g() {
      EnumSet var1 = EnumSet.noneOf(com.yiyiaddon.e.c.d.a.class);
      this.a(var1, this.a.m.keySet(), this::a);
      this.a(var1, this.a.n.keySet(), this::b);
      this.a(var1, this.a.o.keySet(), this::c);
      this.a(var1, this.a.p.keySet(), this::d);
      if (var1.contains(com.yiyiaddon.e.c.d.a.CACTUS)) {
         switch ((int)com.yiyiaddon.m.b.a<"s25t9a3kbatlbu","xKq+9AYyZCmvf1o9K1t7N0NfiGw1FJQsR+9yLbo8ABU=",6819310132152843643,-3946293325686084510,8697444062624263713,-4692250058306657140>()) {
            case 589893591:
               var1.add(com.yiyiaddon.e.c.d.a.CACTUS_FLOWER);
               switch ((int)com.yiyiaddon.m.b.a<"sv9qznv44s4zy","G3L15pT3M4Cb3+/I8Vp0wUCz2b3JQQBz/G+CjD6saWI=",-3375373390056599680,785487177301664375,-4272139259932289627,-8552835005087532528>()) {
                  case -1223507294:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   private void a(Set<com.yiyiaddon.e.c.d.a> var1, Set<String> var2, Predicate<Block> var3) {
      Iterator var4 = var2.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s69sxkazzsz89","FvolK+k3r9VUrbflXaePzm9VSgqONtFmhx0bPEAeF/E=",-6347925833804412734,-4957556021101324122,-1488980875210866071,908583296319937975>()) {
         case -1760106637:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s34b149fmx44ow","P3vlivPt1IL5DHTmlyTamYyJKPgV7Fl9OOFu7JXWO0U=",2411920516737356909,-2603573119884074429,5999301335175888946,1351487929237817847>()) {
                  case 2083286027:
                     String var5 = (String)var4.next();
                     Block var6 = a(var5);
                     if (var6 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sjw8mpur4thk8","oJi+T6N7eHKWIBs4D4w8tLk7eCtpSnL2aC+LGoLMAFA=",3914057364535236795,-7156733239379467869,-96837985251794608,-6179760425564891421>()) {
                           case -1427385034:
                              if (!var3.test(var6)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1wxl14q97ypxd","9DGmf+tKzXT3ixe8LuSsSghgLAjglECBqVgZOYLKmMM=",8443320799898639738,8647232528264117323,-8748089566849006939,4553389556861601797>()) {
                                    case 1174666332:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1o20si57unul4","V7j/gsne5G9K6PY73WHdOK3eazW/qqPoF9k3GP+lmDU=",-925667677636794372,2656229723745268893,-7064312842851058309,6106018988852468974>()) {
                                          case -141857080:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 com.yiyiaddon.e.c.d.a var7 = com.yiyiaddon.e.c.d.a.a(var6);
                                 if (var7 != null) {
                                    label33:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3bb1d0u2bh4hj","y8yPm5PfSCDlsR7oIeB1maTDPYVwKR2519uODUyWt3g=",4675222004790337953,-4047668337204947835,-7306866897113874797,-6600662215176439194>()) {
                                       case -95596709:
                                          var1.add(var7);
                                          switch ((int)com.yiyiaddon.m.b.a<"s32i52sj6k76w5","bOFuAv6XoLhJyBI4yQwk1xXy6UlLskOunzD4PLyFxzM=",8638931085440189161,9141404518315996544,6723131557982672491,-7982195110196141630>()) {
                                             case 249584761:
                                                break label33;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s22es0xch9lm0z","oCMAv/EFQIKCRHhl2X3RT5YaSpr3kNwK9Q77xTKwCxY=",-7530911266594176507,-1462988751439385708,7327890937281210958,-1154669909479246240>()) {
                                    case -1123805314:
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

            return;
         default:
            throw null;
      }
   }

   public static Block a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qvkoa0yyvs7i","QnbiZJnnep2aIISoV0BdFYAVJgza6i0N9nMMo8egBbI=",4903419982427312516,-8189666309534361306,-4682013322084557587,8745277226164577560>()) {
            case 1197901090:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2sewhsr6zg3b8","64mEqmQNxgEq+QQHftuHEToSZpifYv1cICQvwXaPKro=",4135417576795213778,-3513950057785207843,5865529992405929340,-7061213631367935516>()) {
                        case 1331347369:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Block var2 = BuiltInRegistries.BLOCK.getValue(var1);
                     if (var2 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3o2xapht5m7as","UeIgDkEimXgRWpYKguvY8CnqduTq7I9m0uWG/3xxJOw=",-7955949533370395467,-7693738708693641661,242018713164933260,1889488333137322563>()) {
                           case -782247118:
                              switch ((int)com.yiyiaddon.m.b.a<"s21pnr6oc0vm4t","zLbBkqubGOZb7zCGxz3rUHzoPpu+aRARx0Wm7N2c7fA=",-8651851735588245848,-8819840653365903690,-1732322186724152717,-8582338159388628644>()) {
                                 case -895865144:
                                    return null;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s1so78sbjbhso3","vxfQik4dmbVSp/InsG0OWgjjvUS2C8Jape5183dr/ds=",-5727692545401891008,3700515536536653319,-2538281047086536627,-1035724312653950480>()) {
                           case -959108840:
                              return var2;
                           default:
                              throw null;
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s30egfop9r2npd","f11vAEdwWXLfaS6CkRKQWkIq0HkqwDPpgLmOBZ47AGU=",-935598156870690935,-1680021201394884179,5274433050180357759,-724704409238881524>()) {
                     case 306625907:
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

   public boolean a(Block var1) {
      com.yiyiaddon.e.c.d.a var2 = com.yiyiaddon.e.c.d.a.a(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1iwweaarzuoqa","I0YV7zF9n2oQ/5/cCnULJ5RtQmIFV8AGiAJK6TEuNTk=",1376786185293979643,-2397769494616386043,-4612211778564481858,5222964416067049058>()) {
            case 1122866811:
               if (var2.a() == com.yiyiaddon.e.c.d.a.a.CROP) {
                  switch ((int)com.yiyiaddon.m.b.a<"stmq96xtrznky","RVQss/STWtBANmuTBh/yNx3Jv3TPrl+QkmZ6doSo3XE=",-8742085749330227129,3526247933667691703,8471489419092216734,-2974091355331156719>()) {
                     case -620860284:
                        if (var2.a() != var2.b()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3fngrhow6fktm","c08pslsa2eLVgHRtAp6nXE4ycONTWCEPrMzkIZKDiZY=",3071505127924491678,-7402564702708380896,-6737274823785985889,7445994262340619729>()) {
                              case 149289409:
                                 switch ((int)com.yiyiaddon.m.b.a<"s224ksnpvcdlg4","DvYIr+5EGIzSUCuzeljDRcSDzLccIiNFiH0nxcy6X00=",-4161451996841469488,9089657147839232684,6347652773629679469,-7748131886236448798>()) {
                                    case -2116320179:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2d15lvw6llkep","hXWWl3Mr4Aq3XK2fJqjBGnKJWY9tHwp01CUBTnf4Hkg=",5728341671904187380,-8925869497266634488,-6891631291568739335,5112302205633132660>()) {
         case 66750133:
            return false;
         default:
            throw null;
      }
   }

   public boolean b(Block var1) {
      com.yiyiaddon.e.c.d.a var2 = com.yiyiaddon.e.c.d.a.a(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a9knv47ph3jo","qKFMPDTzIhJVvpDwrMGRMHqxbjaqTaaMIXGEqzLPsFQ=",1409865792141096741,2406938545393562402,-4255366664495676744,5260272416261541078>()) {
            case -427976074:
               if (var2.a() == com.yiyiaddon.e.c.d.a.a.CROP) {
                  switch ((int)com.yiyiaddon.m.b.a<"s226im99ny9uvk","Oc4ME357TzIQlwXiYyNzAt2dIfqo53rllQmxvKm5tIw=",5136644244901700985,-8897408870668228826,5759094440489431253,-6790565287292887668>()) {
                     case 1684348874:
                        if (var2.a() == var2.b()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s399kkpsvxvr4d","5QstbqPZa50FU+SXB4bKncxwh1mc9RR9JBw4US8HqMg=",-6445251958197304188,-4138476404825668953,-6750479677986678360,-7905740404764106791>()) {
                              case -787863292:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3old0zz3148ld","Q9QiGlreLsqrSXU+T/TDHCrSagi+3UaP8tdn5wylE68=",-8569086750134518657,-1921466003010742882,1691057677995201869,2803300336242895772>()) {
                                    case 799733547:
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

      switch ((int)com.yiyiaddon.m.b.a<"swo2xjj3vgg5k","szs3nFcCoW0fsraMR1Jw+5jLg3uwvTA+nlMVzLZo+YU=",-1649453138035561261,-6591460042743357581,2260003583054113032,757338113889201255>()) {
         case -1654058221:
            return false;
         default:
            throw null;
      }
   }

   public boolean c(Block var1) {
      com.yiyiaddon.e.c.d.a var2 = com.yiyiaddon.e.c.d.a.a(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sm9bxw48fval8","30it+oAXi5zJGmobjahu43BS35y6m97fU8M9kKNfbDE=",-6069122538667588976,1273265391423635515,-8517484216756575387,-5593916829126897245>()) {
            case 1451641789:
               if (var2.a() == com.yiyiaddon.e.c.d.a.a.PILLAR) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2svi241erlilq","XwHjbN5kVMEQdaJZCeq3rpSct46eoYUwSsKXSefesxE=",-7128609217159178393,650605266723622606,-7569936217947211858,6344854819003988497>()) {
                     case 1111940108:
                        switch ((int)com.yiyiaddon.m.b.a<"s2x7q9q8xitog7","AL2Bty7ayB3kIbIIvB1Q9YDL6osHamr4wD8solcNshs=",-5761066903408004561,7720435043226134284,9120884141237495056,4938613318013015492>()) {
                           case 2075177508:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3ixpujrwqxquj","CI9ub+EhzYyLl/Z2UlybWs03TIAbuHqU1g1mTGolUTc=",-4725638153474299791,742358184223021930,-7386713192828059042,-8600321969626697651>()) {
         case -229441153:
            return false;
         default:
            throw null;
      }
   }

   public boolean d(Block var1) {
      com.yiyiaddon.e.c.d.a var2 = com.yiyiaddon.e.c.d.a.a(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tac8sk29o9nu","pzX+b3dRP5k+0bhfS4snf5KCjWbXUxWC0WbCVha49fo=",2693883983664092071,-7162677585426367217,-1072202023023067336,-7155020649529885027>()) {
            case -1969398161:
               if (var2.a() == com.yiyiaddon.e.c.d.a.a.FRUIT) {
                  switch ((int)com.yiyiaddon.m.b.a<"sjutw4fat838","dzHIYviQd6wQGvWT4XKOVs0FqRa7VmVyoEYDxJY1J2s=",-8196684011224959559,-368355035003432511,-2815652919874072020,1293572605532571874>()) {
                     case 1825653810:
                        if (!var2.E()) {
                           switch ((int)com.yiyiaddon.m.b.a<"srmt64iazqqbn","FSO86ei4NfOAXjJIq65poSgQf1Z9bNpwGGIQy4DC9mo=",2211809387581087306,-6129244131371762293,-9020325616336637159,147635082786646538>()) {
                              case 186902312:
                                 switch ((int)com.yiyiaddon.m.b.a<"sldfldm2pimb","UoUFtJlRmMJ/MVzph3Rs9PS7zWLkSZrzGOkGKe2jZTM=",-5834129060362600203,6565188202327506604,2943020682895490372,529713208217683867>()) {
                                    case -384263790:
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

      switch ((int)com.yiyiaddon.m.b.a<"s62wzxh90wbgu","++brJdubLrNc/gTE0S9Nyr5s6ZJBh1ypU3LvibsFjUo=",7896597712468156220,2019566452726245313,-1538605164478015184,-3790780754280043200>()) {
         case 895676819:
            return false;
         default:
            throw null;
      }
   }

   public void aq() {
      this.a(this.a.m, this::a);
      this.a(this.a.n, this::b);
      this.a(this.a.o, this::c);
      this.a(this.a.p, this::d);
      this.L();
   }

   private void a(Map<String, Boolean> var1, Predicate<Block> var2) {
      var1.keySet()
         .removeIf(
            var1x -> {
               Block var2x = a(var1x);
               if (var2x != null) {
                  label22:
                  switch ((int)com.yiyiaddon.m.b.a<"sla5z2295cah8","8C5eWQ63c/7fesDkg95vLbQWd/gYXU3CT3tHWb3ubdg=",2791609805085257046,-4026135433905892463,684623375874350724,1753668753829079330>()) {
                     case 1142355865:
                        if (var2.test(var2x)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3hx7x2bxuapyf","0zM/pqKukgiAyJbPJ4RV+TeV+9SB+ekyBgCnrAShH5o=",673238542102101649,5322922703869382585,-2333609253872106968,1118438459447451971>()) {
                              case 1537409918:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2wd3yavmlwz5f","yj2ZHj74VHNClxM/1Cr5p/uuE3PFEgqJyw9D2anOKYM=",-3053357436378981058,204566744219649026,2134896165378767641,4383359999872972918>()) {
                           case -110064540:
                              break label22;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s13qixr3sgsc54","lOuXY9HLCTEv4K5qo9gcpOEMOpT3EOORCAwZYyohrUs=",5492774588514168382,-3920266710795487547,-2938354324671336293,-2563083847020583885>()) {
                  case 1392671526:
                     return true;
                  default:
                     throw null;
               }
            }
         );
   }

   private Map<com.yiyiaddon.e.c.d.a, Integer> a() {
      HashMap var1 = new HashMap();

      for (Entry var3 : this.a.q.entrySet()) {
         try {
            var1.put(com.yiyiaddon.e.c.d.a.valueOf((String)var3.getKey()), (Integer)var3.getValue());
         } catch (IllegalArgumentException var5) {
         }
      }

      return var1;
   }

   private Map<com.yiyiaddon.e.c.d.a, Integer> b() {
      HashMap var1 = new HashMap();

      for (Entry var3 : this.a.r.entrySet()) {
         try {
            var1.put(com.yiyiaddon.e.c.d.a.valueOf((String)var3.getKey()), (Integer)var3.getValue());
         } catch (IllegalArgumentException var5) {
         }
      }

      return var1;
   }

   public Map<com.yiyiaddon.e.c.d.g, com.yiyiaddon.e.c.d.b> c() {
      HashMap var1 = new HashMap();
      com.yiyiaddon.e.c.d.g[] var2 = com.yiyiaddon.e.c.d.g.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"szhnvvzlknaxn","YofoFXZbGihAxkzS3lbbURvlXVZqsiuBJtHkWgWeIjg=",-6662694855595695848,9087533614524499260,7336112282879578150,-5330612211490095647>()) {
         case -359217378:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"su37ata6eu8h3","2oBgGY0mrijU6eFQKDVeR3u8g48CB7qDJyEWWjuGwsU=",-4299183667227261927,-1238468875658426808,-7621874687854415570,-8802367133314142537>()) {
                  case -1076382898:
                     com.yiyiaddon.e.c.d.g var5 = var2[var4];
                     com.yiyiaddon.e.c.d.b var6 = this.a(var5);
                     if (var6 != null) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s98rj7xl8scx3","4XJ/470gLZcF0yHmwgWG2gm9MELzSxhMClGBOMWlLcU=",-2310154834683158518,2826929449118970291,-5913064828316699092,-4583395575316653216>()) {
                           case 1266730395:
                              var1.put(var5, var6);
                              switch ((int)com.yiyiaddon.m.b.a<"s2mimy3rw70svm","BjBkO/mdDHcPH88zPTov55gHKyb9v58XxDGOKs2FVOA=",7421885052638759787,-8169477184112564923,-5717010521359155428,-7173539953391173635>()) {
                                 case 836119130:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1etwkjqks4pc1","y+zpeWo3ExV1njUNbg2R+qdTQRfzt0xGocjIxvQ0E8I=",-770249505642765149,9026691734923624751,112842794290501672,-1193791930112497888>()) {
                        case 1948331821:
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

   public String ae() {
      Set var1 = this.g();
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;
      Iterator var4 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"swho7no9tm82d","BrYoBOjhmyoOpGyG5PK5X224rjm2iL9p65AYKE9338M=",-4828719604331899054,2776414310571783808,1395675423799796967,-4259258848196973515>()) {
         case -139982404:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3fy65i3br25m3","k1YY5diVGwaN9Rp9loi1jv/S3e3KcCjInLcVqH/cWmU=",7167466320641371753,9139232654784772675,-2807138804616686111,-7122870894124285659>()) {
                  case -905575596:
                     com.yiyiaddon.e.c.d.a var5 = (com.yiyiaddon.e.c.d.a)var4.next();
                     if (var3++ > 0) {
                        label65:
                        switch ((int)com.yiyiaddon.m.b.a<"s1u4r69rf8s39h","QYar81ipQ2ArpDRTsseqAHlGGQUKSjvmhW4MwVIR0YU=",3798318911513424950,-792677821637265268,114725441381313413,-8108809329047879442>()) {
                           case -760041298:
                              var2.append(
                                 (String)com.yiyiaddon.m.b.a<"s1bjn9srh8nar6","vRLR26+rpj78s/uyTqTprz0ro10Hwl77l9+jev4PmfjILw==",-1847578674145691467,1589137823400915978,-7548996253040779909,-6589590658908644164>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"st63j0wmj0yv","azItUmQzOn217oNhAePx5j9MlA6+caa2LA28qtmSz/U=",-239260735699121176,-5988134588576128211,-3614466577286734751,3466823361978782164>()) {
                                 case 1247494079:
                                    break label65;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var2.append(i(var5.m()));
                     switch ((int)com.yiyiaddon.m.b.a<"skfe7tmdh3oo7","ob694odt1mMqk+cpKsTUUTRXNrrU1D/xaTivwjpp2Ug=",2237182184522206164,-8524208384305669009,3060697600081710382,-5089602596621705387>()) {
                        case -1930230120:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            String var8;
            if (this.a.B) {
               label55:
               switch ((int)com.yiyiaddon.m.b.a<"s1xcvjqxw2m2v8","2wc0jwzqlUWfyVIqrPOHIVRVUDZTdbRRIy/1YpEpPks=",6207195529548024876,202274360455685641,-582334051262450557,-2678602180750425898>()) {
                  case -2063203552:
                     var8 = (String)com.yiyiaddon.m.b.a<"sdw5iaic67y3a","0pwgk9A8MnliskPUHDEn/9tMG+/DJ3bLgIw+9fxqYm3Tzw==",-6461731858763218309,9164796013826798509,2667457317706248942,4285920377601230218>();
                     switch ((int)com.yiyiaddon.m.b.a<"s391nyrtb8varw","R0IzpAJNc4bIz6V3lIJngdBQ6mY5vLvfTrCcyl547Ig=",-3825555996502892440,-1908776510294348208,-2889604377481681506,7810194633470090893>()) {
                        case -313051141:
                           break label55;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var8 = (String)com.yiyiaddon.m.b.a<"s3j0drudp5z6z5","afAUSeyDKtpR3m/O9xBYLRxbpcNursPIRmfjfI3X2QAp/A==",-2411843769834463508,4535523385842845365,6699210046950634019,-5650970188179966574>();
               switch ((int)com.yiyiaddon.m.b.a<"swveu2hskv1j5","aeR4dNP78/oMNM/NF4dHRogOPqme6M63nmC/ZP7/x/Y=",-127822412539737673,9078968869409247315,-7218874207682199429,6601959216379048294>()) {
                  case 1634701460:
                     break;
                  default:
                     throw null;
               }
            }

            String var6 = var8;
            if (this.a.a == com.yiyiaddon.e.c.d.e.BATCH) {
               label48:
               switch ((int)com.yiyiaddon.m.b.a<"swajfocro4izp","lvLMor1dhyvvofqkkJBdWSc43VLisWpiwuMfuxEi8/M=",7096221202258263991,-1987532667973152133,-383854043286499971,-270278993972474096>()) {
                  case -149173574:
                     var8 = j(
                        (String)com.yiyiaddon.m.b.a<"s39x67vcr67k8o","yhtfkfBBtIFsxRWYDYMpB00eLKpTzzgmM1rgU3/COMA=",6634774362144107456,5402888991416442769,-4102292845718439111,7884293674944773837>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s3fz1bd70ibizh","2sClettF7uV2zMJQ8i1gP/23AVSOM8IAqDFrcoOE29I=",-7872324819375793058,5986809996324256511,5387242764885019001,89291879018273967>()) {
                        case -1213528069:
                           break label48;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var8 = j(
                  (String)com.yiyiaddon.m.b.a<"s2cif7vql16ayc","FeI9O+dyahHbmxOxATb4zYNhKYojvv+GTukaybaneQ4=",-1164081128394364138,8418202969450439431,-3701680562060553428,3521716768492681816>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s26c5zjzoqsvex","dQf7f3RrF19Z4KstB68y7R44PaEMM1wUdVGiM4fAMfE=",-6616751059149551488,2990859472556210142,1111691728755404972,-1757311602040665179>()) {
                  case 1607213304:
                     break;
                  default:
                     throw null;
               }
            }

            String var7 = var8;
            if (var1.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s22fpnfxhfqjwa","BNwciZvvmyKludxN2P/T50APQHp6+B+2ks2cSVpRWIU=",251761923318734347,831257662029485787,-1159984001404133947,8531061352911455137>()) {
                  case 1155268251:
                     var8 = (String)com.yiyiaddon.m.b.a<"soz332dyfl1bp","mtiqdjN3AI0oOGTWGZ/66DsPhr86uKYUSLjHztoAA/TJWpnVFjo=",-5710424054118469363,-821858289232143007,-99959059582953306,5678330062146114490>();
                     switch ((int)com.yiyiaddon.m.b.a<"sgpd0wzp6g5iq","fZ90otCkmHVqFg+0Iqh4oCu0tqiTl2VYZzuSXqUC6l4=",377624808078249052,-8331388053617831894,1046846040613794206,3275846282504400733>()) {
                        case 1083903542:
                           return var8 + var6 + var7;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var8 = var2.toString();
               switch ((int)com.yiyiaddon.m.b.a<"s2j2kafkml5aim","cdgMwdtmaJTBEMDMGfVOg7dG5ZCZq25gopEl05iv6d8=",-7402031154914927895,-70066882555783517,6779123241000245708,-6829190256856522863>()) {
                  case -1622776912:
                     return var8 + var6 + var7;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   public static String a(ResourceKey<Level> var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"srlge386qd6aa","c7X/NVvrfrfM3EZkaKMFEMkwCgrtPtCKBFKEmsvSwQU=",8426267050362439635,-7421118213228940318,7564206809529747611,-8346296385320254962>()) {
            case 1907630708:
               switch ((int)com.yiyiaddon.m.b.a<"s3erhvbu6rbu3i","I3wuybsKm7RUTDXKg+yXWrFRWK1FfmKI8mgGkdo6Imw=",-7264311402801974903,6685637599785749762,5302708006335727922,-7489628151912495279>()) {
                  case -1490754885:
                     return com.yiyiaddon.i.g.b.bV(null);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.identifier().toString();
         switch ((int)com.yiyiaddon.m.b.a<"s8m2hhtbg1f3k","58fKouLsVycxsahqO8l4DajnaAtGtN6TRDJRIBfNSII=",2066680994972133759,7077600327624877024,7682327034974971742,6632743980801299995>()) {
            case -955158355:
               return com.yiyiaddon.i.g.b.bV(var10000);
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.l.g.a.d d() {
      return this.a.g;
   }

   public com.yiyiaddon.l.g.a.d e() {
      return this.a.h;
   }
}
