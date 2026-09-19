package com.yiyiaddon.e.d;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.a.c;
import com.yiyiaddon.e.d.b.b;
import com.yiyiaddon.e.d.c.d;
import com.yiyiaddon.e.d.c.e;
import com.yiyiaddon.e.d.c.g;
import com.yiyiaddon.e.d.c.h;
import com.yiyiaddon.e.d.c.i;
import com.yiyiaddon.e.d.c.j;
import com.yiyiaddon.e.d.c.k;
import com.yiyiaddon.e.d.c.l;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String ch = "autologin";
   public static final String ci = "自动登入";
   private static final int bD = 200;
   private static final int bE = 30;
   private final Minecraft m = Minecraft.getInstance();
   private final com.yiyiaddon.e.d.a.a a = new com.yiyiaddon.e.d.a.a();
   private final j a;
   private final h a;
   private final d a;
   private final i a;
   private final e a;
   private final l a;
   private final g a;
   private b a = com.yiyiaddon.e.d.b.b.IDLE;
   private int bF;
   private int bG;
   private boolean S;
   private boolean T;
   private boolean U;
   private boolean V;
   private boolean W;
   private ClientPacketListener a;
   private String cj;
   private boolean X;
   private boolean Y;
   private boolean Z;
   private ClientPacketListener b;
   private boolean aa;
   private int bH;
   private boolean ab;
   private int bI;
   private int bJ;
   private boolean ac;
   private static final String ck = (String)com.yiyiaddon.m.b.a<"smz69tzma33fr","rGu/c4Ek1B6aEeVUWQ9YqM2Bd08nutbTcOQgKW0w",6077360801636776935,-591508278767315659,822281506994648361,-2588047894725593602>();

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s3cfw5slcne3e7","G+tQrr51TbRphM+dHFLTVcHmNNTBHLtFfi6sa55vBrmdo+CBgdkbR+xPWgIuGA==",-2315213177896893305,-9111628024120963364,3635304172703707079,5396099317706651188>(),
         (String)com.yiyiaddon.m.b.a<"s2zxg6h4nf8edr","GBdkSCpegkiqQvTU8YOkVu0TfamZ/CWRPfX+itvLaGoq8y1D",8177836824299977194,-1357611281647263253,-8010793259308214615,-3713882449301070219>(),
         (String)com.yiyiaddon.m.b.a<"s35gsf7xsgyso2","Fw6jLBB1tIj/JU7DqC0JuV4WsCNXGkuaJ7hVhJJsGMEnEknuqpIUPqMo/Fhpe5J3",-6796669027082660066,-3433892902104297544,6300200614220700844,3593534315958730437>(),
         (String)com.yiyiaddon.m.b.a<"sbla5mfz7v2zj","JbYJRl481GN2mcfYuWybn2ce0w7QCB2BIDCDkEswdsPqYiavPL+aXnJIAG+2aXiXhJ6FlX0vjPKw1D6Cfey505BDegJ62OtJnZaD4dsDgVDG6+5CPdIqosliFdeYcJR2",-1787259688894538222,7625827070011289958,-3637317736473946829,5886870819070267335>()
      );
      this.a = new i(this.m, this.a, this::aJ);
      this.a = new j(this.m, this.a, this::aG);
      this.a = new h(this.m, this.a, this::aH);
      this.a = new d(this.m, this.a, this::aI);
      this.a = new e();
      this.a = new l(this.m, this.a, new l.a() {
         @Override
         public void u(String var1) {
            a.this.u(var1);
         }

         @Override
         public void D(String var1) {
            a.this.C(var1);
         }

         @Override
         public void E(String var1) {
            a.this.B(var1);
         }
      });
      this.a = new g(this.m, this.a, new g.a() {
         @Override
         public void u(String var1) {
            a.this.u(var1);
         }

         @Override
         public void D(String var1) {
            a.this.C(var1);
         }

         @Override
         public void E(String var1) {
            a.this.B(var1);
         }
      });
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"smz69tzma33fr","rGu/c4Ek1B6aEeVUWQ9YqM2Bd08nutbTcOQgKW0w",6077360801636776935,-591508278767315659,822281506994648361,-2588047894725593602>();
   }

   @Override
   public int i() {
      return 80;
   }

   public com.yiyiaddon.e.d.a.a a() {
      return this.a;
   }

   @Override
   protected boolean i() {
      return true;
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
      com.yiyiaddon.d.b.e.d(this);
   }

   public b a() {
      return this.a;
   }

   public String am() {
      if (this.a == com.yiyiaddon.e.d.b.b.RECONNECT_WAIT) {
         switch ((int)com.yiyiaddon.m.b.a<"s3iwiv7f6ls9zb","szL6spftiKqwnZkcgJecOhwEQKY0QZioXIW1kuqN9VA=",8910999352695273452,452023183307723155,1667552231702226082,7269599724006051860>()) {
            case 553674879:
               int var1 = this.a.C();
               if (var1 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"swhrr38287hux","bSurCy7Jzcc/syPxvRg0U5ZU1kleRo3Tlhy/F1ktBVM=",2406481105877734238,-3823648961484550308,-5327298961996675889,2099561709540228756>()) {
                     case -2019512404:
                        return var1 / 20 + "";
                     default:
                        throw null;
                  }
               }

               return (String)com.yiyiaddon.m.b.a<"s37i9fggj9dy3e","ubvO2hZ6EerVFxl2bEgh4g/P7VCc1AdoYH+mkCvGSOGUPhdzjEE=",-263070620097352115,-4803647965070330533,-5436039868270979537,-3153004482166709669>();
            default:
               throw null;
         }
      } else if (this.a == com.yiyiaddon.e.d.b.b.ACTIVE) {
         switch ((int)com.yiyiaddon.m.b.a<"s3noukrqudc9w3","hjJ0kbH45ypFLL8E7g63J4ktFHhdPme5a2ciGM1cT6E=",9202351654253723991,-8981317841462113131,3709684620481839723,490338310363811390>()) {
            case 104780276:
               String var10000 = (String)com.yiyiaddon.m.b.a<"smtruqjqfgagz","ZClr4kqb7jiaUTgVw/c3YmL2pho55+rBJrUTQZ5BkUCGe0xm",-9135698630251661156,9121755516523200069,-1575825466425612563,-7968478441092034467>();
               switch ((int)com.yiyiaddon.m.b.a<"s2ov2qms880ozb","6h+SAsFMAx8qVG4bzVE2YlrRurnczrdo5WZqbT5JAAo=",7196377949188166282,-9179168407851159813,720668635116740620,8900537952613019238>()) {
                  case -361845613:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s10t1dsk323rtm","h+ouDzQj5eIkCRZrE0h4OrFmkG7JyZQVU0VFC7Cribw=",1346883739920559656,-2540774328265392598,1228384256173991666,-6919381354534827461>()) {
            case 1191012096:
               return null;
            default:
               throw null;
         }
      }
   }

   public i a() {
      return this.a;
   }

   public g a() {
      return this.a;
   }

   public l a() {
      return this.a;
   }

   public void aA() {
      if (this.a.aq()) {
         switch ((int)com.yiyiaddon.m.b.a<"s34hgmzbd8f4t1","eVNRUmZVWlXPdeELmxcmra/93yBihvNygUaQA3q2Kco=",1800003452266820252,4267021593729163972,-2562001119963558856,3126047146589050727>()) {
            case 1277083902:
               this.a.aA();
               switch ((int)com.yiyiaddon.m.b.a<"s2v59iw9wex31a","cwG4/tN8QGQxU0FyyzF2xlzVMn+DMB31tPv4CGc7T4w=",6385585063032348401,-801011776132805626,6660975663514420376,-7753586025009872389>()) {
                  case -1342123711:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void aB() {
      this.a.f();
      this.a(com.yiyiaddon.e.d.b.b.IDLE);
      this.u(
         (String)com.yiyiaddon.m.b.a<"s2wtd092v0sv53","esWDD6ZC2E9dvqXd/m8OYQkYbpo73+y6DH4KOAw9zdKZKlCbnBUK1VxLfnVvZoQa",-6813052822506009791,-5050574876774112312,1662855000503164269,-9034569224329200861>()
      );
   }

   public void aC() {
      this.u(com.yiyiaddon.e.d.c.a.a(this.m) + "");
   }

   public void aD() {
      this.u(
         (String)com.yiyiaddon.m.b.a<"si1dyp4x2s3ol","ox4cQdNI5jRT5IyXmcAjQdRvMB8s7H53OaoPyYFf9YkvQisqLADOnwhBPe3ID8dWSLtaKgMXqy9be5FYd10OlTcdJMkiI9j68hJrOIdcaM4vHYmB",3218395596090388517,5912914171399606320,-6083131611998741023,-6837695848746727865>()
      );
   }

   public void aE() {
      this.u(
         (String)com.yiyiaddon.m.b.a<"s2jz4smesydzkg","t0Y3g0xNtoFlTQ8OqH8eT2EMWfXHs2hRgMMdmAroKJXXllKiaSMDEg46Bu59zIAPIJvWrbfs3esw71q8BXowm7e2whocvm0gNP7v5IJZCi731gJx",7957043109354297146,6327609038487762637,6905646583022750156,-261856005431174939>()
      );
   }

   public void f(boolean var1) {
      String var10001;
      if (var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s33tg162tuabgs","Fz8HRdKoIm9tR0vOi4mbOp8iGxXzjDZu/xHX9/0JgVY=",6873318000547980904,3098607733723793386,2361821563702267165,6550299747836280681>()) {
            case -2081887836:
               var10001 = (String)com.yiyiaddon.m.b.a<"s28d5xokvhbd9v","9yCHKn80goRORJtXH21zU5D69xVUh7DCcdqb4OagXkNnz752Zb/r0ANZyqwm9uav9CCVU8Gf/EWL6bl4G/VP1NSPNRoWXU7k1oQu32/ZIdkMnQH0gq85Rjk+",-4942203536400129046,3894889783207729312,-3942198492493006691,2823497550823767579>();
               switch ((int)com.yiyiaddon.m.b.a<"s2wrd75vat9cm2","OoGHtqsap234Y9opX3X4hWDZupmKaEBECi5m9T+vMIo=",-1904913208009283852,-4831530448121942000,2340676150819233731,2362860952057665548>()) {
                  case 491829612:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s1gqc62vne7rdh","XESXI7B2qOKkOMllq15+TIuBZgfcVX5KWoLKJE/YkGbB/TSbCJnZXe4xXdMnMq/yi/8glUBVuaZof9pVzLbMZlrkn8CuaDWzBzHhXwhEVED0kMqkPtVY5P4g4myrJw==",5977855624846970623,-3998141412774943146,3304617068629174168,8488260429607708649>();
         switch ((int)com.yiyiaddon.m.b.a<"s2u6p1snm2dt6i","pKGfIRvhYWl6+WT8ReFDjZ9mqEufhHZE6HEHvAxJAgc=",-6421569749362067066,8094205121789347539,1709659346203898296,3894919798858617743>()) {
            case 2031094825:
               break;
            default:
               throw null;
         }
      }

      this.u(var10001);
   }

   @Override
   protected void m() {
      if (this.U()) {
         switch ((int)com.yiyiaddon.m.b.a<"sy1osctn8hpbf","bvhXOWDjq3ti2Vz9Hv5bzuU//Y7lp8hZj3m2ztlxMaY=",-5703512489510173975,1687521197797695712,7591998546307817712,6668190876793274829>()) {
            case -1770088840:
               this.m
                  .execute(
                     () -> com.yiyiaddon.d.b.e.a(
                        (String)com.yiyiaddon.m.b.a<"s3cfw5slcne3e7","G+tQrr51TbRphM+dHFLTVcHmNNTBHLtFfi6sa55vBrmdo+CBgdkbR+xPWgIuGA==",-2315213177896893305,-9111628024120963364,3635304172703707079,5396099317706651188>(),
                        false
                     )
                  );
               return;
            default:
               throw null;
         }
      } else {
         this.aO();
         this.a = null;
         this.cj = null;
         this.X = false;
         this.Y = false;
         this.Z = false;
         this.b = null;
         this.aa = this.a.ag;
         this.bH = 0;
         this.a.f();
         this.a.f();
         this.a = com.yiyiaddon.e.d.b.b.IDLE;
         this.u(
            (String)com.yiyiaddon.m.b.a<"s16nzzjgohz4t6","L87WUrTs/Seo/rPvwI7WQqMCEXCTjkew4UF1zq1hRAQsnXy8ITpjULMRK5VtmcQlgWg2SjgF8DC95SHG/ExHmvlXCz9r0YSqNHg6bcggSzY=",96168250097739305,-6392392414267419103,-3807830287978440452,4096726441791204909>()
         );
         this.C(
            (String)com.yiyiaddon.m.b.a<"smb08i4n519vk","WuQzol5qSxX8holxMSP+d2C40ihQNauhOVZg3fI/O/nopTGpnmzPrYE3m8+a3++QZtZY8Bzf/xoXDE/Y",-8542923757021568565,-8964818237783589357,-1958679597416379927,-5364976325503832025>()
         );
         if (this.a.ad) {
            switch ((int)com.yiyiaddon.m.b.a<"s1izkuy97trxon","hDvkQ02QoktCP5cOEEDtQFZABLfRlQ5h0so9HiY8i6g=",1631956317678281345,878692109246233456,7114182584274227875,2483241792823368211>()) {
               case 1092133080:
                  if (this.a.ah) {
                     label41:
                     switch ((int)com.yiyiaddon.m.b.a<"s3bi9efe53zj5n","+Khid4fnh5TVaiUm1S+pIG4QnEF8gnv6vUDBGjDwNcI=",172688510722356558,-6058311818583808736,1336274934731703084,-1064926265710149006>()) {
                        case -663333218:
                           this.u(
                              (String)com.yiyiaddon.m.b.a<"s2tvry77yhucj1","nM9uWtEd4ArshYiRtqwv5mY/x2RcMQXfqw/jJ6Kwm/S314ivOjo8zn5+Ybw+Wue3adOrwu0r2LO1ekiO2TsWBwB1FTut1kuLBEQo/XF6P4HEB2zf48lPJZBt1mnDGwXB2c3Xg7Alqw1LogHPyac=",1708861678646386215,-550059361993320189,5045394317973275082,-1432050335775913892>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s3us8kq0fzjebq","yWdNkYWRyTwSZFDL4N3CQLmWOTLrhIMMx8dJXOOVnnE=",-7087544808726259656,-2326406616963406384,-7937264406904647957,4350937118835286333>()) {
                              case 1572557247:
                                 break label41;
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

         if (this.m.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1s9gmb26qskn5","dknJvkw4m02xxCZ3GWTv+3IftTzu7ffaW15lqFEDVVM=",5542645523543180240,1395379497572282319,-7445582012922981426,1733737154854069976>()) {
               case 1921012605:
                  if (this.m.level != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s352f228gv6k1l","0uik8ahiIUJBNfV7q9v4OKiZMS4aeJU+32tE82AjcCk=",4093273022058506257,8297558395106793313,-7292460211517497532,-3704336407126488561>()) {
                        case -1156883124:
                           if (!this.m.hasSingleplayerServer()) {
                              switch ((int)com.yiyiaddon.m.b.a<"sktgg5nwfjotm","2Po+lTrIw9dPR2UHzxs8cTdRDtOUT4HGhAAo7qwNrF0=",4713226012850389629,-5222200069472334821,2280816931360829747,-4452051548356256239>()) {
                                 case 2002358617:
                                    this.a = this.m.getConnection();
                                    this.cj = this.an();
                                    this.aQ();
                                    this.aN();
                                    this.aM();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ffuqii9tep7m","9e+gC4qjS1c5EuRiWCg3Z9Gs5L2vXwJs5ZYCSKvwL3I=",-7481378675325187823,7727316040441619016,-4694774227285148437,-3817946320589418757>()) {
                                       case 1238211848:
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
      }
   }

   @Override
   protected void n() {
      this.aO();
      this.a = null;
      this.cj = null;
      this.X = false;
      this.Y = false;
      this.Z = false;
      this.b = null;
      this.aa = this.a.ag;
      this.bH = 0;
      this.a.f();
      this.a.f();
      this.a.ba();
      this.a = com.yiyiaddon.e.d.b.b.IDLE;
   }

   @Override
   public Set<c> c() {
      return Set.of(c.TICK, c.JOIN_SERVER, c.SCREEN_OPEN, c.SERVER_TEXT);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2c3l6kon24upc","00ZDOVvJplOl5f2W3lr7gfq4/8PK/WrGUqPfldQoUiQ=",625794187563931743,239124424632332554,-944318810221297215,6606694499767510182>()) {
            case 449851156:
               return;
            default:
               throw null;
         }
      } else {
         switch (var1.a()) {
            case JOIN_SERVER:
               this.aF();
               switch ((int)com.yiyiaddon.m.b.a<"s18nbw4s2fdzxd","KO1c8QXrTbQZ5IlSBjJJlkC6qRkxivueMdJMywCJrbU=",7419893338662933610,3263674313512090771,8158143711931871417,4916059319818258058>()) {
                  case -1217949521:
                     return;
                  default:
                     throw null;
               }
            case SCREEN_OPEN:
               this.i(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2fumhfnfed84g","HFEzfqcVO1cnOjr1d3A1tefZv1xEHigDH+iMQyKyQds=",-1554894036272858357,9018065159325129570,2068001154861377672,2194036279353138334>()) {
                  case 1931529301:
                     return;
                  default:
                     throw null;
               }
            case SERVER_TEXT:
               this.j(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s1ew4mo2duvs33","DnAH43cM7DU6Le3BbNrkfPvRaBDteo+ThorgxyfcaEs=",5128762101001154720,7408269878564213896,-3238788664163419912,6541204412489983178>()) {
                  case -764585222:
                     break;
                  default:
                     throw null;
               }
         }
      }
   }

   @Override
   public void b(Minecraft var1) {
      if (this.U()) {
         switch ((int)com.yiyiaddon.m.b.a<"s141h8kpatgdtb","TOufkCJnuGNIKeA3oUUfRhVzX5De8YnEjygOUjtMuOM=",323121149238981213,-2904232413793942608,2706399789620325629,672981400749051389>()) {
            case -1262109843:
               com.yiyiaddon.d.b.e.a(
                  (String)com.yiyiaddon.m.b.a<"s3cfw5slcne3e7","G+tQrr51TbRphM+dHFLTVcHmNNTBHLtFfi6sa55vBrmdo+CBgdkbR+xPWgIuGA==",-2315213177896893305,-9111628024120963364,3635304172703707079,5396099317706651188>(),
                  false
               );
               return;
            default:
               throw null;
         }
      } else {
         this.a.ae();
         if (this.a.ah()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1dae47r3ynvkh","AkW5yl70xPFxsYCbSmYK6ZODaUrfNITKPzJYf8pOQBc=",2739704044073692095,-9221002854743927879,-163641879952816268,-1661370557390505184>()) {
               case 2110127623:
                  this.a.f();
                  this.a(com.yiyiaddon.e.d.b.b.IDLE);
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.a.ad) {
               switch ((int)com.yiyiaddon.m.b.a<"s218qwfocuvtsp","BWnEhIQ5thJ/KywASKSs4ieFl2Ww+Gtwnu7WKVecaUU=",5373349318631943257,-8743548270802601248,-594561540645613380,797601921387995525>()) {
                  case 1495708685:
                     if (this.a.ae) {
                        switch ((int)com.yiyiaddon.m.b.a<"s26wz1esx5y9j8","kwDsMtDTQIVnFn0MMuMsAJHOfTknHs08R0jVXYIzuwg=",7062660795194436783,-231061807507275416,3819087101435678665,-6397642405543520335>()) {
                           case 253232621:
                              if (this.m.screen != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"svddbh3tn11he","A5n8btexR42NirkN9Pm4cPz5naxfyyc1XhyDuFEHKNw=",2864687275761585131,-2523592935093435477,-4801826432403499883,-434213825908080152>()) {
                                    case 369643503:
                                       String var2 = this.a.cm;
                                       if (!var2.isEmpty()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2n11q7c7wl8vq","TZZaZfR+ly3Dnxwwz8uhaCrycoYYXh+J0JNhSZvVi3o=",-6160702784817292629,1581853264839830525,628495167879664870,5646722801059675803>()) {
                                             case 1032515082:
                                                if (this.a.a(this.m.screen, var2)) {
                                                   label269:
                                                   switch ((int)com.yiyiaddon.m.b.a<"swwaxusgkce1z","61uLmu/7n3j+9TfqNiEZdROWt8Xo98DNBW/TLy91ohM=",4851447798405993178,178084838090017223,2165577481711339444,-6529670481295088813>()) {
                                                      case -995781632:
                                                         this.u(
                                                            (String)com.yiyiaddon.m.b.a<"s1qo1grwbt6jmd","Hss0tcZxALnfnQGGBfF8klNSNIIR5iAnnP2fB1MtBwkQ7ujvTPCqI7k9uMFHwcdlVn85ulcy9Ri3V5fi8/u1LkXsdvYnF0B/LXU=",-6756449521975339539,-1474833400354860059,9211576682637187451,-7119638651821402757>()
                                                         );
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3e8lsfy7pbzty","zWL4dPPbi+B5Hj9ivdgTsnrY30IGE0Ivcp5gBN+na7c=",-7349333811434640743,-7853708639264900689,8295334005410969391,-8349303842356640857>()) {
                                                            case 1803453438:
                                                               break label269;
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

            if (this.m.player != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3n0qcpfidxpzr","E28GrIZ+VoXPUmfnypzrcaFg0HoEuupF4mRUSloL6iU=",-6017992033811773987,-6826575698183678642,9076909959684592184,-1024199429609864015>()) {
                  case 2088593624:
                     if (this.m.level != null) {
                        boolean var4 = this.a.ag;
                        if (var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"s18sstgghlj8se","bmbC5xMmyYczXdG/ok3DX4ufvVn/xjpOuKdlkt0/vZY=",5720750542638771273,8902413460190616134,-6685217732488629951,3882037047935346655>()) {
                              case -741032651:
                                 if (!this.aa) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3ihx235120nhp","u0SuWjlWPqCWVAr9+oUcyjjBxe289S53bGiELLJoJrk=",8191636505954822685,-685669151981579470,4534965334129042471,-8473490102728837952>()) {
                                       case -1589155220:
                                          if (!this.m.hasSingleplayerServer()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s35a7hc4s7k3du","PBVVg2DUfTrwFojTeW24DHtDwkY7JWN0bqFmwjiqu4U=",-443300171012192954,-3300860369873130699,-8004844655203967182,388144495196330047>()) {
                                                case 421112805:
                                                   if (this.m.getConnection() != null) {
                                                      label251:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1f6pfphm0njbw","HCGwDItiiCvJfQu/wnZhBDzrI0NUJggjRY8tIiLuSgs=",195507360047478312,-9167409546468171288,2405460734042551524,-7086758874820495055>()) {
                                                         case 231266301:
                                                            this.a = this.m.getConnection();
                                                            this.cj = this.an();
                                                            if (this.X) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"szx46sw4y854z","xE4kN4LTszUJpyJnpK1k/F6pdb9rnupd3v5tZkXpz8w=",-613498693997965013,-2215867395121564660,-4711141698107345957,1412071053612818554>()) {
                                                                  case -1779641419:
                                                                     this.Y = false;
                                                                     this.a(com.yiyiaddon.e.d.b.b.ACTIVE);
                                                                     this.u(
                                                                        (String)com.yiyiaddon.m.b.a<"sl79ymsia7hif","9Lx+54rFiOurjpO9Hb6u3FwoSkM1Cvy/huJZxO6+5NQ4rLpxkwnHucBs/KBup0ukGLFGqd1gCAbil8MHyAW39XJjf/67w5YxUXKxhbX6zWGx62ma",-8344410495687007984,-8012701266576861846,775492038971283035,2780992556658586618>()
                                                                     );
                                                                     this.aP();
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s6jaf6btzs25f","nUlX3ZihDHh69aYiFTrCZjgvuJzxagPlJw/AIxBSmsw=",3853602527220476131,-5741860941084280111,7111408285281799782,-6250701267381804721>()) {
                                                                        case -328669430:
                                                                           break label251;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               this.u(
                                                                  (String)com.yiyiaddon.m.b.a<"s1qe8vnz9lpgge","RMQfZ+VVWDumbl5InVan3ppdP1gHeQX97xRphdL7sSCRlFsTiwMiSBolHdVdiNiqVRYQZpkuW+mlrYhfFOzgoNnpPzvW6OPV0SJZ2Cfy8wQciw==",-8741773662350685295,1759373315733033391,-9062579296980085733,-482119164978254477>()
                                                               );
                                                               this.aM();
                                                               switch ((int)com.yiyiaddon.m.b.a<"snodmbq1iq41m","Fh3UiAsiq5oOQIKj3uzavLEnJA/rKIGde70BhXQBzQk=",325044856733036214,3217862745677243123,2906194406908021268,1555986297160676005>()) {
                                                                  case 1180227607:
                                                                     break label251;
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

                        this.aa = var4;
                        if (!this.a.X()) {
                           label242:
                           switch ((int)com.yiyiaddon.m.b.a<"s18zgs2789knm4","r/GhMN8XpCujKqutR1T+8zcnOKTQgwot50sZoV8Ev+s=",2005796126179593220,-5213547350275746448,4514127679251567316,6503252801493448179>()) {
                              case -1930577120:
                                 this.a.ag();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1gly46d6o36uh","m+0o3l852It/SQzmYg16lZAf6AAz66xsltQMAfWpzMU=",8986708072615849889,8082023358160732946,-4528097030772844023,-276075991450091339>()) {
                                    case 914745804:
                                       break label242;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (!this.T()) {
                           label237:
                           switch ((int)com.yiyiaddon.m.b.a<"s2qogisq1wgd24","81gjkiWo+eqAHFsPJB6j1+K12yTdW/royRWEjvZ4H1g=",3199288437439597363,-4375456078516943440,-8043969644442224492,3426756402447579310>()) {
                              case -1564243680:
                                 this.a.ag();
                                 switch ((int)com.yiyiaddon.m.b.a<"stasi02kh3n49","CsiFo/Jd8BlIeLt9wIF8ExIJPR6X9fA3oFWPLpPXxk4=",1265331544524263026,-3420511145574445316,7997825466731909372,-399618911165131068>()) {
                                    case 1216794679:
                                       break label237;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.a.ae();
                        this.a
                           .a(
                              () -> {
                                 if (this.a == com.yiyiaddon.e.d.b.b.ACTIVE) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2g8b0cmxhz4x9","2Hfc+B/xBkpYOkUdwT3pvmkeeW3F4kys8gdFEM5WyQc=",7751996812667790549,8890072435641635501,-3319488454143240526,3848825590674486423>()) {
                                       case 692904648:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3lk7v7fkzq1bh","qCpa9m+3UiYWJdFlYNPtvEwDF1w0geM9RhPehW1a5JQ=",3356985650186283638,3925890811608708509,-8948418794481991558,5217414027806025137>()) {
                                             case 587483934:
                                                return true;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2fetiizsbhruc","IrU3hm17didBZO8LoRqkYwwLRF1rHhQPh5YL7U0LMuc=",-3993740281090341259,4189804299471010412,-2668481575485523334,6462358678300877221>()) {
                                       case 1678859765:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }
                              }
                           );
                        this.a.ae();
                        this.a.bl();
                        if (this.ab) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2w4qgsdeeoxn","XPTGMOEzHRgA+7SHL/1oV9tKs+lZEdJ7cA+L7jH9F8I=",-8301174765391549937,3944295638161503440,467606723205936274,3659674058781425124>()) {
                              case 1287285628:
                                 if (++this.bI >= this.a.cm) {
                                    label230:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2odc8wdzxucub","m9wJDNnzZvhZ6P/wc/pj1BDyv/PH8PcSv33tFMlSaRo=",6756285215438149869,7158582730500970239,-8141469161775322727,3661863985897773346>()) {
                                       case 21140604:
                                          this.ab = false;
                                          String var3 = this.a.cq.trim();
                                          if (var3.startsWith(
                                             (String)com.yiyiaddon.m.b.a<"s1j1mcqmz7hrwt","Fxb5o2GaiXv/6ym+sPrhVPH7i4YL5hViH8WrxVZs",1084845613298436685,-9202822728657371671,1847032984345415965,8210430075074929248>()
                                          )) {
                                             label227:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1fr4au860odj8","KEZ4+JjCryfghDfJ4rV1P5TwTItT3/3y1wB5x45TwLI=",5175674317640989454,5689227986296941943,119262097735354105,-6326818609665939805>()) {
                                                case 920551843:
                                                   var3 = var3.substring(1);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s11kf37ujfzy3d","Gia929sq/vHdu8zF36E5QVl1FqaKhyumYvrCOMFazMc=",-2795115199808025993,-3418674681027663012,-1154041610213840635,1894109276541369531>()) {
                                                      case -1347436241:
                                                         break label227;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (!var3.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3pxg5jgi085b6","Y3EVTRs7YLPNL6ONrNC4Qdz3mydYesn4JYbdR//oEOk=",-862242721806230993,6191149671554384764,4311546732783511686,-5255588165501771819>()) {
                                                case 1777937428:
                                                   if (this.m.getConnection() != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sx8meux49duw4","9QVnDTzAqvIlFYmajM2ao5NqKcmv0uNFy9vge0GWo3M=",8117303011711273055,6773575722683630328,-3544878156358027292,-7512458183580351343>()) {
                                                         case 773532908:
                                                            this.u(com.yiyiaddon.e.d.c.b.z(var3 + "") + "");
                                                            this.m.getConnection().sendCommand(var3);
                                                            this.u(com.yiyiaddon.e.d.c.b.z(var3 + "") + "");
                                                            switch ((int)com.yiyiaddon.m.b.a<"srwbeatl7ijpt","/oPT9Aq1BXI2GEm97kNwRQfMNT+2Ha8flTtrsae9Y6U=",-8716749066220171417,-31895691932915205,-6265775468765276309,3674488115030181619>()) {
                                                               case 2009411446:
                                                                  break label230;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break label230;
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

                        if (this.bH > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s30pyrdjx5hn7b","vPB74/VYa38sTo7shvIxrZ0IY+AMAjmGGBcFIvBWYS8=",4459989923332562430,1511621232861663528,6130267842732907814,5165406239979195504>()) {
                              case 2035659025:
                                 if (--this.bH == 0) {
                                    label214:
                                    switch ((int)com.yiyiaddon.m.b.a<"sjmt6triox9uh","CwApaEq3eXNRs1elNoVzfqLUij1EEgbP/LppureNQnk=",-9210553812523248187,-544389432878393167,-8617179052110617398,-5383627274908967574>()) {
                                       case 1792488671:
                                          if (this.a.a == com.yiyiaddon.e.d.b.e.LEYUAN_CUSTOM) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2yvhyq3n64zk9","V7kLYPFj5bpbsezj0lvEtDwRw0uvPR/KNOYHkSqFASI=",539561802885971680,-8686171740358028298,4307739538972155466,7486899183595052588>()) {
                                                case 424785644:
                                                   if (this.a.ao) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2wvwmejiu3oy8","1QREe1Bj4uau6jZHDDRwIIG1ClfpT1lCrr2ZJwOdKlw=",-8487460265772470189,4330902685825303630,8523519611479327202,1643717260393379113>()) {
                                                         case 1120509238:
                                                            if (k.a(this.m, this.a.z) != null) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2rxixiw2tt9qp","MXvVVbR8QHCvPrEE4sCHWf3OjTOp7q+dn0fv4cSBwE0=",-3210755102716970313,-8847458654766271382,-2321623516935437053,6139302764614518888>()) {
                                                                  case 320885013:
                                                                     this.u(
                                                                        com.yiyiaddon.e.d.c.b.y(
                                                                              (String)com.yiyiaddon.m.b.a<"s2k9dx284p78z8","HDMSzJgETTJPlv9bE8jwdnYHPhO3auPpgaUabG1RwNsVhRWG",3164145090091850144,2252348567838199695,-8939993628767385979,8263723976643534095>()
                                                                           )
                                                                           + ""
                                                                     );
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
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          String var5 = k.c(this.m);
                                          if (var5 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2azcobordg7oa","tqzs2JWmdNmmZaQoc3eUFU3H12IRrdNR4gaS1zgb6uo=",-2628788581853522399,2134876473092169360,7287737222426496751,7275455879223089936>()) {
                                                case 1306906270:
                                                   this.u(
                                                      com.yiyiaddon.e.d.c.b.u(
                                                            (String)com.yiyiaddon.m.b.a<"s33gjs9zxsazrv","E2S44/mX//HJg4pPJ1OUFlUJd+cK2d5kmOBmIFqtOw93qSx2hVk=",8508414756811203859,-4348605058835557830,-464244466779571086,-295328165421897533>()
                                                         )
                                                         + com.yiyiaddon.e.d.c.b.u(var5)
                                                   );
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1p4hpj53i13vg","uV/fmtJwDjuiVix4wnULv56/lBpu5i7Ef9l8MOHUric=",-3468413508327035740,7649659341652601907,-280918484855033672,-3574506701251448619>()) {
                                                      case 1345047193:
                                                         break label214;
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

                        if (!this.m.hasSingleplayerServer()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3kpo4z0h5q1cj","qZHw+LswJ4LU1irbRydhlF4g7e5F/UdG3aqxgigyFiQ=",-5867461573614435246,-4918867951034474433,-5753326037023936181,4041376780689297003>()) {
                              case -1601802288:
                                 if (this.a.ar) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sbq4wbi8e6zxi","Y5x9ecG35aLmUjM1r272uYnzouWEgP0ure85Nwxu38w=",-1157403393992568913,-4162595710344506897,-1648717629846813239,-4664736675134660925>()) {
                                       case -1721328995:
                                          if (!this.V) {
                                             label197:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3qbsej2jw26be","nOM4mNy8ZrLooVPKkrOge3kRmuXvj2PmcaGbABEfS4I=",-1580312381720204602,6418221453409692373,-4296647302391083407,-5789608423328706018>()) {
                                                case -1140297078:
                                                   this.V = true;
                                                   this.u(com.yiyiaddon.e.d.c.a.a(this.m));
                                                   this.C(com.yiyiaddon.e.d.c.a.b(this.m));
                                                   switch ((int)com.yiyiaddon.m.b.a<"s37s0yxbaqrr0w","rp5IpqeuXmxGmHYMLrdeNO2RiCWJaAdx7bK0Q37r2YM=",-8628486644408261028,1383309810565460622,3215499649735667554,-614404339899153802>()) {
                                                      case 1612674096:
                                                         break label197;
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

                        if (this.ac) {
                           switch ((int)com.yiyiaddon.m.b.a<"sud3hi0dfc0qj","ZbZAOgrcAsowi7SYQ3fvfvMaQMc489P3kAyiaysjltM=",8916530529924511540,-6262431823416502823,-1244194445886655797,-1944983159689083711>()) {
                              case 672790258:
                                 this.bJ++;
                                 if (this.bJ >= 200) {
                                    label190:
                                    switch ((int)com.yiyiaddon.m.b.a<"szvrxls7h6h4f","x7USzJhMtaqlp0L6ToaEUXTtqGqO31q6mJIFfmcSDtM=",7202926800901633940,-3756655239870652784,5394410748758224648,4584254391691012888>()) {
                                       case -26699048:
                                          this.a.bi();
                                          this.bJ = 0;
                                          this.ac = false;
                                          switch ((int)com.yiyiaddon.m.b.a<"sgoj2sfkpsrqi","RsxnZTL2fyYNdXgZrAdPn0LPPolvl0OUrkKYOc8swbc=",3401271477032118832,8372203564449144400,7167133584526164142,6369103276946394233>()) {
                                             case 409072106:
                                                break label190;
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

                        switch (this.a) {
                           case WORLD_LOAD_WAIT:
                              this.bF++;
                              if (this.bF >= this.a.ce) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1kimkcd4aw8cy","U5Beirp+y7lK8OMMy3AzPGAoRL5f0UTlvS9H0OA4qek=",2417401179103864205,3495287174329601133,5850155278573294025,2238084554965665169>()) {
                                    case 1353302332:
                                       if (this.a.af) {
                                          switch ((int)com.yiyiaddon.m.b.a<"swzymmzbfbh0x","uTUgp1cTinvxUKWJ7gWXtOwpXlDkRsf2kiDwFB8QgdI=",-8009089426203043217,-6700759363999692071,-2378684357005798651,-4266957368850181112>()) {
                                             case -1432720465:
                                                if (!this.a.ad) {
                                                   label176:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s23x5wd0kx6md1","IZdH5qybgldltPJcZXlUOR3JL0HieODjQN/k/AaoN+M=",7814437887928594058,-5883351181049614017,-1798833708582146374,2282640079248214390>()) {
                                                      case 1031808759:
                                                         this.a.ad = true;
                                                         this.L();
                                                         this.u(
                                                            (String)com.yiyiaddon.m.b.a<"s8ypowih9y2wz","K9lwkCKsUB18taRm0OIQ8GnazEgfXhPxt6WnAGeY1yQG4X3Ny+mO4rkam/Tb6affB8MASbG5NyjEUId8E6hfurMh78/ecLrErD0=",2521775559857814892,2283480817660939784,4337626924377729243,7330639455362611528>()
                                                         );
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1koflng4uuukr","4XBabQc3JlihpEwUwLus2LRwCUIeD8NvX7Y4XDPE/h4=",4942985621633261407,7485488003979864142,4168924444844171594,-5913578611774496851>()) {
                                                            case 72002365:
                                                               break label176;
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

                                       this.a(com.yiyiaddon.e.d.b.b.DETECTING_AUTH);
                                       switch ((int)com.yiyiaddon.m.b.a<"s17ub29jfl6mlw","DsQ5Q5zhRJ+VqFUQUqCIouBrrF3dTWAkBytYsVm0oRs=",-1340497875411296158,4959125559861635897,-6115843255033116776,1276621434623484479>()) {
                                          case -867864339:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           case DETECTING_AUTH:
                              this.bG++;
                              if (this.bG == 1) {
                                 label169:
                                 switch ((int)com.yiyiaddon.m.b.a<"s367zgz4gw4otm","aw55i+ALExPa5YAjp+6V93TEnVzwMvt+gmPPA3tqvv0=",-5023435746050104149,-9155241793460974973,8241829080025051116,4762379878905269040>()) {
                                    case -1081320174:
                                       this.u(this.a.cf + "");
                                       switch ((int)com.yiyiaddon.m.b.a<"s1wizzhbhlqmcx","CgsbnnVdJVICjgmN5aRvb0tjXnd4njd+vACfSSM9Lms=",-4043536448145493322,-7705361469414691099,-6993002754007999349,-2867065679475007164>()) {
                                          case -976282405:
                                             break label169;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.bG >= this.a.cf) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sqc8hms7ei0i1","tJnu+MqR8EyQgtVyLskwTi6YF4f2PJKUjEkiMBFGjtw=",-7855569605101129921,7316537491797113407,3588293420093089039,7744458470296971718>()) {
                                    case -1476140227:
                                       this.u(
                                          (String)com.yiyiaddon.m.b.a<"sy3qc4u9mygn6","rew6nUOStSGKc//vHPmepBFCTb3ltOKXbUci0cJPm4LLYF+0YjS10zi9B/cHMA0XhWmnNvYBF8/5gYDg",4279193165549106233,8229281675472483204,3423545181979506364,8455178273267979854>()
                                       );
                                       this.aK();
                                       switch ((int)com.yiyiaddon.m.b.a<"s2461zijyfykyz","IJ2Zwvnk6xaqggpNJoShhkn4AjF+ofhvIiauhnh0zGw=",-6244912320855058417,-2182867183394397862,-6575232944194869863,7443185258803751363>()) {
                                          case -922902331:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           case REGISTERING:
                              this.a.ae();
                              switch ((int)com.yiyiaddon.m.b.a<"s2gsfsj9qci443","pJItAGsG95xWHp3ms+rv7KKR2VPbhx9V9QL3pP1Lqd0=",-6185612973769594906,70464113129456004,-3895966643410465577,9155225923767509997>()) {
                                 case 1284654379:
                                    return;
                                 default:
                                    throw null;
                              }
                           case LOGGING_IN:
                              this.a.ae();
                              switch ((int)com.yiyiaddon.m.b.a<"s2kw1hi4hrhz1v","TrKBUOqbyGyC85N5fey8Qy/JvNn+bnuZ7xVyPwc772o=",1346607491982088506,-5136510303939204379,7276759866951661451,-8825782821921181448>()) {
                                 case 1323078626:
                                    return;
                                 default:
                                    throw null;
                              }
                           case EXECUTING_COMMANDS:
                              this.a.ae();
                              switch ((int)com.yiyiaddon.m.b.a<"s3cmeskpxthzt5","3KQNFMPcsacSyY6jWbkUIxSAvf9oq+2YMT1DrRooHuM=",5501342985227872193,8048980420925321600,-4832232664678906296,5340186849972535585>()) {
                                 case 749903774:
                                    break;
                                 default:
                                    throw null;
                              }
                        }

                        return;
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s1urftqqkc9xj","kZhbUR2KoGayY7mv2yoJulTPVqWl10jHsm6K5GPwLf8=",3133884213960168098,383727021674967906,4242709260132693274,421564281841468636>()) {
                           case 1446422539:
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
   }

   private void aF() {
      if (this.U()) {
         switch ((int)com.yiyiaddon.m.b.a<"s8palmvpr7j2m","Lyu/HVbeNlcLmRkbJjmqkxsTDpGD3dfOStSMu6wHZjQ=",-8535875676617066438,-1485386801373172935,4455543820220318483,475979088645807180>()) {
            case 733777944:
               com.yiyiaddon.d.b.e.a(
                  (String)com.yiyiaddon.m.b.a<"s3cfw5slcne3e7","G+tQrr51TbRphM+dHFLTVcHmNNTBHLtFfi6sa55vBrmdo+CBgdkbR+xPWgIuGA==",-2315213177896893305,-9111628024120963364,3635304172703707079,5396099317706651188>(),
                  false
               );
               return;
            default:
               throw null;
         }
      } else {
         ClientPacketListener var1 = this.m.getConnection();
         if (this.a.ae()) {
            switch ((int)com.yiyiaddon.m.b.a<"s35dptd5yaj498","mrouLl7v25iltnhxln6ZTys/niAGhmNlBmAA/d7f6Kg=",-5293296744243326223,5924582837163395366,-253496384961702510,-3049319959925974982>()) {
               case -817795685:
                  this.a = var1;
                  return;
               default:
                  throw null;
            }
         } else if (this.a.af()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2w6d07jzwezpd","XiD6S/TUTFRBy28D9pkhw9OYz/XPp7+Q42caN4zLZwg=",451571783620003790,2493877959884362657,-7309452837968749856,3886816405882473554>()) {
               case 1129271217:
                  return;
               default:
                  throw null;
            }
         } else {
            if (var1 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1uyywx955rbkg","ffg53TCJHRDDCpJE+Rg+7tjUk0sUTCsFpnoJe1G690g=",-2022535597768345451,5340797240928295523,-2388925218924365667,-7089566011818298472>()) {
                  case 1298840116:
                     if (var1 == this.a) {
                        switch ((int)com.yiyiaddon.m.b.a<"s9lsi9npe7vn6","McXSyX27NoIRg2n0YU91Q7Znl5Dfk4frP4pQuBEs1LY=",-8236885527718674067,-8944940818700363242,-956029764661790226,7313409368994142540>()) {
                           case -1641321518:
                              if (this.a.au()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s39969groj13li","RCIWpq/x/qPvWIiraXQaAfzcNOqgNZP+QK+hzxjb1U8=",5249757857981903412,1031916237222699045,-5689209073766164685,6693898306039358204>()) {
                                    case 337549239:
                                       if (this.X) {
                                          switch ((int)com.yiyiaddon.m.b.a<"schkfcni5ajl8","tPZ3PTtz/GDq7PQWnaKzyX9mvySzJH/BMjwIcfw3wdI=",2414394573402417781,-4658820757178879766,6319276808213471378,-6647381391346635526>()) {
                                             case -1204990384:
                                                if (this.a.ag) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1h6a5tp4hn63n","CbFU9Ld1EgqxDkr3Oc+YWHGhXqcZFVBaAwjO/6hqqII=",4336847697250118523,718743620569598297,-8768053360457996231,5959946830839383037>()) {
                                                      case -390692639:
                                                         this.bH = 30;
                                                         this.a.bk();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3f4jaxe7soxms","hRL3ZVQ0axad1C4qHpMqzXwpH5tS85B+CdehssVw+wk=",-3158008960554848099,-8584971365328133857,-6672808743743202663,7414742582783137015>()) {
                                                            case -86229657:
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

                              this.a.bn();
                              switch ((int)com.yiyiaddon.m.b.a<"s72vfrpqof3da","zcvMvpH2zKymo612tg92D/vJCW/ulYJoO4V5dVoLesc=",2408627502946821590,-7649952133023303375,5917936137070309678,-6711630819516377786>()) {
                                 case 381927223:
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

            String var2 = this.an();
            if (this.a.ag) {
               switch ((int)com.yiyiaddon.m.b.a<"s387jgl1qldpcf","QRP9EkRhcGeGTmDJn/txL0OwlxEg3QVyNTWHH0k2h4g=",-4875097191792385049,1159938077841269976,-5161807876502386890,4816938571632181705>()) {
                  case 1161262044:
                     if (this.X) {
                        switch ((int)com.yiyiaddon.m.b.a<"s22rlkyxe0uihp","BpenL8gt+gahPHadP/nMklM+Cl0y8P+4MHeNioPToHM=",-1649044745166720467,1978391753262179309,-1807342342670403978,4251205162127152125>()) {
                           case -1420098508:
                              if (this.a.au()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"savzcfqx4oncz","ZrToiML8fYS6gfiOx3nPHt89ba3SlnW7huz3k+sCWfQ=",4923686921505803516,7530893881493039280,-8950780812294538477,-3355380164628203478>()) {
                                    case -1997383552:
                                       this.a = var1;
                                       this.Y = false;
                                       if (this.a != com.yiyiaddon.e.d.b.b.ACTIVE) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3dm4v5st3melt","VVtTS9Kv106O8HgZZ+tWMVS+Ysbb1tS6i2MMbAQFqbY=",-3176574639192913814,-1862223363299686714,-2462961971386412929,5741856041730115570>()) {
                                             case 267036987:
                                                if (this.a != com.yiyiaddon.e.d.b.b.EXECUTING_COMMANDS) {
                                                   label110:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s33rbojaofc0l7","kf+6aANBOpVud5ljoyLSMJsP+WPrPg2d9QcKT1atdx0=",7756066256473900414,-3718963348430929163,-4509491038484668978,7144426970292479756>()) {
                                                      case 1471453577:
                                                         this.a(com.yiyiaddon.e.d.b.b.ACTIVE);
                                                         switch ((int)com.yiyiaddon.m.b.a<"s168jgtq8vbhie","8WLjkDk9F67JHQF2mSFWE5FXo4sj3ZM5jXfRGOgNQZE=",-2441292476365748061,2815533867830510499,-5045664430691979944,4068218477730447564>()) {
                                                            case -2131770050:
                                                               break label110;
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

                                       if (var1 != this.b) {
                                          label105:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1hp0vfk6cgczm","bSBZjkrR2w/eI0ig5EtxyUAzyR+dhnCrZRyAuPjRSG8=",-4995388477880616195,-4895112164870852864,-8461093495014815459,-8552092493267900075>()) {
                                             case -916832826:
                                                this.b = var1;
                                                this.bH = 30;
                                                this.a.bm();
                                                switch ((int)com.yiyiaddon.m.b.a<"s3aszv3shag3zr","4Pk3VtitFiH5XlGcBoeVSxtMMmIzJhY0+f9ynhh8ik4=",5180778030331107091,9104091104326762426,-3307659418566228800,-1725120623307177933>()) {
                                                   case 956641944:
                                                      break label105;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.a.bk();
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
                     break;
                  default:
                     throw null;
               }
            }

            if (this.a.ag) {
               switch ((int)com.yiyiaddon.m.b.a<"sdr694iyelysp","1JU4DoMOKqFmkP/gI+QawtqJ/p2i0zSut2387iq3qSU=",-2714267798313648786,2574012126456555372,-267216688342006441,3587543095787267549>()) {
                  case -98725827:
                     if (this.cj != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2eb4rsde4zi71","1/jbKQsXiupVEwrlFcWCXnCgxzKCC8VtOWKtzdeHzOo=",2072831957170839433,-8992941900008654411,7169106866643604605,6101922971492020992>()) {
                           case -697261299:
                              if (this.cj.equals(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sd4yidamjtn7l","9BD/hQiX1FTTNv/8LPTLOwKjWbU/aQ/dpyVomImsgJw=",-1969269881604804304,7317720444481366575,-5084148283974729215,-3970528356552014176>()) {
                                    case 1224306789:
                                       this.a = var1;
                                       if (this.a != com.yiyiaddon.e.d.b.b.IDLE) {
                                          label120:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ol7lztrrm458","LpcDqKOizvc9/0pk0O/slO+ffDv6LqJ4xqaxC2K0B1M=",-1070408192845677543,606182534262486046,8333154145325997694,-4916844798460351413>()) {
                                             case 2117108051:
                                                if (this.a != com.yiyiaddon.e.d.b.b.RECONNECT_WAIT) {
                                                   return;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s1oxhha2lg6eip","ocvdxtRT/yCuTOMZGjuSB+ni/jDpZo6heMTcD3nbxgQ=",-5609585750555809570,7770673037298125926,8810449986808535147,-7644480475488349650>()) {
                                                   case -1897915566:
                                                      break label120;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.aM();
                                       switch ((int)com.yiyiaddon.m.b.a<"s21uf2wzi1cacz","5VM/azxe+b2nQ5WvQ0f+GA0pYf69Dsvm1oaes1YfQcI=",-3309955142447713407,5576451824239251757,5732772756213089337,6426630815839927946>()) {
                                          case -1617190578:
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

            this.a = var1;
            this.cj = var2;
            this.X = false;
            this.a.bk();
            if (!this.Z) {
               label131:
               switch ((int)com.yiyiaddon.m.b.a<"sbuzuhnibrhcr","jJ4xY4nhB2vRoEpnCROw6FoAdkI/O9rmLbeePB+WxUU=",2530769301975111928,-2027937693896205822,-356312596630529121,-5998797976998083433>()) {
                  case -1952760473:
                     this.Z = true;
                     this.u(
                        (String)com.yiyiaddon.m.b.a<"s35p9zylyqnoz","XpWY2Q32OpAs4RWXLJi6wYCcawJDA67pCaGXIfH1tG+jzIzIV6uujfL9qsscBFzveikMyohzLBMq59E7GIUXxw==",-6134940552431906572,-829508588923716803,6007245768964979677,6375176185917104317>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"snz7c24o0ys14","k3mcpHuACFQX/DGg1iZri7NAwOUt1WVxHpp1qw8JtkY=",-30195219149600790,-4243765309004280297,-8368450846027695131,-9038372338645020584>()) {
                        case 1167601479:
                           break label131;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.aQ();
            this.aN();
            if (this.a.B() > 0) {
               label126:
               switch ((int)com.yiyiaddon.m.b.a<"s93gi57e0yo42","zPW6ndxIB5nthEaIrrId16ezdjYJ8NqmrqIZEZ9IZ0A=",-886278811085148294,-4596034946083774749,-7893156463624008598,8302385298639458159>()) {
                  case 2060709307:
                     this.ac = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s1ipt8doapvdkl","yu3TrKSmTtioY9gOZ6GRonSDoPxatmy0bdbzcESXjIw=",-2932832652603456254,-7618800001634713315,4277569951041261121,-5915561821231148134>()) {
                        case 751063857:
                           break label126;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.aM();
         }
      }
   }

   private void i(com.yiyiaddon.d.a.a var1) {
      String var2 = var1.l();
      if (DisconnectedScreen.class.getName().equals(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zqx4kc0stj3y","lMMZf+NNJeIEerJg3b7/Uyos+CBLlPWVGyF1YX2CYZU=",1478462056875087257,3004872068997506655,1776961313043218438,-4097299427569022362>()) {
            case -1761631358:
               this.aL();
               return;
            default:
               throw null;
         }
      } else {
         if (this.a.ab()) {
            switch ((int)com.yiyiaddon.m.b.a<"saycetbmfxg34","eNoIj0CLmtPa22FNg0lUFQQz6AfiOfOziG94JQfptUM=",-2762192666388866361,-1014905136765679555,-8959347762390951195,-6934209672685674587>()) {
               case 2458460:
                  if (m(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"socws9hd759xl","5hH05uzor2IMMfs0DTkP65A2+9Q+iXZ8Ww+VVFCLqS4=",8310087369559897230,-8242174961504476290,-5115127815594099696,8653532880982199881>()) {
                        case 1402758440:
                           if (!InventoryScreen.class.getName().equals(var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s20rxptpxf9ii6","ZG+D8ZP4ZGVmj7eaQej2k3vveYxjpPAC39q8kjsCRmU=",2738265952029163032,-3161683916508841917,-3399608364421844583,-3644718718127901357>()) {
                                 case -297197152:
                                    var1.i();
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
                  break;
               default:
                  throw null;
            }
         }

         if (this.a == com.yiyiaddon.e.d.b.b.RECONNECT_WAIT) {
            switch ((int)com.yiyiaddon.m.b.a<"s2z4h6rfme6ljb","b1MulmgaJpIZN70u6NNhAxFrUZs7ScDTlVyn/YeP5+0=",-4463642244351404799,5461425528299353773,-7264061492123905786,-7747123645310482450>()) {
               case 1591385253:
                  if (!JoinMultiplayerScreen.class.getName().equals(var2)) {
                     label37:
                     switch ((int)com.yiyiaddon.m.b.a<"s28o5dxxy4f2oz","QMMe5OaLSZe+v2vCyN1fvoCN1ah3K3PDaOwy1/tGwhs=",-7649269054721768613,5358576418683806713,5024687759203835792,7438568348742153766>()) {
                        case 999538543:
                           if (!TitleScreen.class.getName().equals(var2)) {
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s312ezn44gp2z6","4I7KONtLwjm7FlyZqCCe6BDrI32inA2OE1Y4khqA3lI=",-3483225018096345979,-1573054039268148152,7396680839025854383,3868558246710947518>()) {
                              case 766537421:
                                 break label37;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a.f();
                  this.a(com.yiyiaddon.e.d.b.b.IDLE);
                  this.u(
                     (String)com.yiyiaddon.m.b.a<"s3rheum7k7hw78","LsigvYLpXGj2z0+Fh5UNMUMDDMkTRw5wc4c+SPWZF5kPhyX2455QwYmK3wMhqZ2Xu3+34RJuGE/7XngBVNeQ4Xgo4aaSULpo",-7961834958718317050,-4260843368010755208,-2925772076952393482,-5464515483794875511>()
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s2l3hk3ohwcblh","wu8l43KwkRavKAcmDuSNwJ/UJMgSrJq5jfQISE8wYWs=",-8809923346956839578,4341927466174577197,6704747747022483268,-6341643665227218799>()) {
                     case 2026715188:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private void j(com.yiyiaddon.d.a.a var1) {
      com.yiyiaddon.d.a.h var2 = var1.a();
      if (var2 != null) {
         String var3 = var2.o();
         if ((String)com.yiyiaddon.m.b.a<"s1dpllgo6abdin","UF4EKaroX+vum7FkwM2VAUNz5Q940E8WJOFtfWNjXZpOJqAP",-2333493122602838564,6341331585259958712,4389525881280913575,7810976307162266363>()
               .equals(var3)
            || (String)com.yiyiaddon.m.b.a<"s2nbti1s024i7d","jXNSflJixgyKPfvcm5RpZrdnNVgCXn0SoIIQvxh2RuZ7Ybxt1h3G7H9garHgAwK84zIAzg==",8020086399253931916,-4629917811776565689,7530471854781905291,-2529171101293787179>()
               .equals(var3)) {
            if (!this.W) {
               this.W = true;

               try {
                  String var4 = var2.a() == null
                     ? (String)com.yiyiaddon.m.b.a<"si74vtfabil18","2inzmfrsrTtBekjs7gNJcq2ckNvPqauVbbuYOQ==",-434633935299173087,-9116223467841145431,3990471190562339964,-5164114490435180046>()
                     : var2.a().getString();
                  if (this.a.af
                     && com.yiyiaddon.e.d.c.c.p(var4)
                     && (this.a == com.yiyiaddon.e.d.b.b.WORLD_LOAD_WAIT || this.a == com.yiyiaddon.e.d.b.b.DETECTING_AUTH)) {
                     this.a.ad = false;
                     this.L();
                     this.X = true;
                     this.Y = false;
                     this.u(
                        (String)com.yiyiaddon.m.b.a<"s14nofhf21qzj3","0S6L3peeRFagGjEhNehkutZJQaAZTvxTc2P4kdMXJexY+KivvkNsoVe1Lx3cryUK/geSzbkWOGGwtLuaSMEAUtetO0rq4tNdawE1QorLdVN9I37pEbfQisOFx2M294I1",-8213030813430158924,-396682987617486330,-7169467328736408717,-4325217153276511804>()
                     );
                     this.a(com.yiyiaddon.e.d.b.b.ACTIVE);
                     this.aP();
                     return;
                  }

                  if (this.a != com.yiyiaddon.e.d.b.b.DETECTING_AUTH
                     && this.a != com.yiyiaddon.e.d.b.b.WORLD_LOAD_WAIT
                     && (this.a != com.yiyiaddon.e.d.b.b.ACTIVE || !this.Y)) {
                     return;
                  }

                  com.yiyiaddon.e.d.b.a var5 = com.yiyiaddon.e.d.c.c.a(var4);
                  if (var5 == null) {
                     return;
                  }

                  if (this.a.af && var5.a == com.yiyiaddon.e.d.b.a.a.LOGIN && !this.a.ad) {
                     this.a.af = false;
                     this.a.ad = true;
                     this.L();
                     this.u(
                        (String)com.yiyiaddon.m.b.a<"sdr7ifzhk2nr","jGsuzvRmwI9ftoyZdZEePPLm+ObKuThDUJWREMvys/tsdy5Srk8SXX0w+OyfFV3HK2Iw1w6U6yGRNLLB61WBr78Voyn3bj/eOwKrVlQLqcwQT+LsanYVpNMp4Cw=",6862619471219448126,6562680009254107240,7094936167304926362,-343025417733750502>()
                     );
                  }

                  if (!this.a.ad && !this.a.ah) {
                     return;
                  }

                  if (this.a == com.yiyiaddon.e.d.b.b.WORLD_LOAD_WAIT) {
                     this.C(
                        (String)com.yiyiaddon.m.b.a<"s19h3tf91gr1r0","/FBmNbxaTNUQ5gLkIjJ5mxo5lshSjV8yl7ZSCGK+++Wh3IML+Ta8R5t8jKneepXG5Y8w+LV9O2SzGgR3NDnHq8vh92g=",1640541001572753714,7441238618882163731,-4977864544744701718,-1988886087168483771>()
                     );
                     this.a(com.yiyiaddon.e.d.b.b.DETECTING_AUTH);
                  }

                  this.C(
                     (
                           var5.a == com.yiyiaddon.e.d.b.a.a.REGISTER
                              ? (String)com.yiyiaddon.m.b.a<"s24vcamnkn3ah3","pLWOgP/ESA27bJChNa246VGKVAgZOUPeu1xRiUHxCzk=",-4050699724595935924,3464277299811804086,-2873801369423325536,3227748233745977749>()
                              : (String)com.yiyiaddon.m.b.a<"s2hnuq8kjtan11","m4ztctNo0CI0jFFhTUZPwufHrQkxoFbVQY/fFnR/cL4=",-607306287983026570,-4598256890376230010,6959565829084402516,8162466065276142603>()
                        )
                        + var5.fl
                  );
                  if (var5.a == com.yiyiaddon.e.d.b.a.a.REGISTER && this.a.ah && !this.S) {
                     String var10 = this.a.cn;
                     if (!var10.isEmpty()) {
                        this.S = true;
                        this.u(
                           (String)com.yiyiaddon.m.b.a<"s2ke65posarqlp","wX+fIXov7+xD9YqjKV50TqkG7w7XgfzeLjbZQh8ReANlGiek2ryr++q640HVGA==",-5681349268190616903,-7946668436826554017,2853691666857096841,6008711236280717615>()
                        );
                        this.a(com.yiyiaddon.e.d.b.b.REGISTERING);
                        this.a.a(var5);
                        return;
                     }

                     this.u(
                        (String)com.yiyiaddon.m.b.a<"s3f713lj0cgyc2","L+uqkoTq9fWV6E2k/pXnDA+Wn+3gC5aEOFQ3qi3U6XX1rc7gJC1I7vJgdvZvGUkz0l+9fnm5wKw=",-2329672844064367979,-7531772505143574131,7414981812735025121,1145360154864926755>()
                     );
                     return;
                  }

                  if (var5.a != com.yiyiaddon.e.d.b.a.a.LOGIN || !this.a.ad || this.T) {
                     return;
                  }

                  String var6 = this.a.cm;
                  if (!var6.isEmpty()) {
                     this.T = true;
                     this.Y = false;
                     this.u(
                        (String)com.yiyiaddon.m.b.a<"s1rog6dx9iu2ty","pwli1b6MQaXo1LKnVue9GozWBJCMwctmco10gp+hnlBWIZck39YF615SK8I6cQ==",4068300723116478807,-4036068070413976246,5034144636687241308,-3247815582341488438>()
                     );
                     this.a(com.yiyiaddon.e.d.b.b.LOGGING_IN);
                     this.a.a(var5);
                     return;
                  }

                  this.u(
                     (String)com.yiyiaddon.m.b.a<"s2zp7yqxcjgxgy","dCdKt7cfGa54n/ytE/UTiUTjaLHOK5eNk6uUeeMjA75yQmwz3FyyULlrAoIps91gnyN748AKMdo=",1779023285523617399,-8500772833391359016,6538092981252180234,6575275089563855475>()
                  );
               } finally {
                  this.W = false;
               }
            }
         }
      }
   }

   private void aG() {
      this.a(com.yiyiaddon.e.d.b.b.DETECTING_AUTH);
   }

   private void aH() {
      this.aK();
   }

   private void aI() {
      this.a(com.yiyiaddon.e.d.b.b.ACTIVE);
   }

   private void aJ() {
      this.u(this.a.B() + "");
   }

   private void aK() {
      label57: {
         this.X = true;
         this.Y = this.a.af;
         if (this.a.al) {
            switch ((int)com.yiyiaddon.m.b.a<"s2dh9qwm1uh3gv","niIxrFO9IoGiIhKrm6uGOqqH66eesvpM1XB5CLKki40=",-7702287187323838205,7125870665501287602,8972720904209039736,6465813881017548017>()) {
               case 1701348779:
                  if (this.a.a == com.yiyiaddon.e.d.b.e.DIRECT) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ap83b0jejkqx","Q6dP44lwab7tbEXXaEWSEg3Ci1cu4JP/hiqwtfveLD4=",8864210289016732322,8855588191938199300,2943796704359448412,7319960831812123704>()) {
                        case -1516252466:
                           this.B(
                              (String)com.yiyiaddon.m.b.a<"s3tw5acyy9a0ek","B34QpX2zKZdfk4577P3dkkz2d8TPm3IUKQhkaNMMvjrXC7ienhSlsQ==",6907495516581268836,1630535935124942811,-1869741848548094018,-4582820952017264942>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s3t78nluathkvq","uxZFLCe+xoOCKCX0txddPlpx/Uu4lnixBCmvYlg5AgU=",-3976743713711745737,-2361161567419463088,4273955066519311550,3349064535316781164>()) {
                              case 1977852679:
                                 break label57;
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

         this.aP();
         switch ((int)com.yiyiaddon.m.b.a<"s10drl10vy1j13","IY0/Pb0iCPf7EqMFN3y5dqY3DCS3rA778Xvw0Qq2yWo=",8663917149522350060,-8944412876370086302,-6823248048973722180,4613392345668873543>()) {
            case -916196087:
               break;
            default:
               throw null;
         }
      }

      if (!this.a.al) {
         switch ((int)com.yiyiaddon.m.b.a<"spzksc8bwbwa8","QhokC5W6szXBZCcNKzyLQeFeKWF0TXSXjcgIbutJDpU=",2151131394223303132,-4056848685288296808,-7907990812604647257,-6503330487345583839>()) {
            case 44766526:
               if (this.a.ak) {
                  switch ((int)com.yiyiaddon.m.b.a<"sr3qv29iccnib","bPv5EPD68B2DKvwub2OVrKtXqnCtKr2ev21OGRNBJwY=",192037242591314476,-1227326154286558943,-954398954618684926,4050678430935723039>()) {
                     case 948762015:
                        if (!this.U) {
                           switch ((int)com.yiyiaddon.m.b.a<"s36qpyk0m48q3r","uxgpWjrKM53MRx5bnJ8E/wQzRfSyUHAaCQZXPvBOq64=",5244221582355884751,5940256926984834425,-1037867824766702394,4311583015807725945>()) {
                              case -1030176678:
                                 this.U = true;
                                 String var1 = this.a.co;
                                 this.u(var1 + "");
                                 this.a(com.yiyiaddon.e.d.b.b.EXECUTING_COMMANDS);
                                 this.a.aR();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3lmcfwx4ftr8","W5uQ7UdQAcwP9x5+eBgMMwbLNi3OaebH0A9yAdj0/G4=",-7865173745807114575,7041538197636286035,-4145547714355093977,-3486018203371734859>()) {
                                    case 186216669:
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

      this.a(com.yiyiaddon.e.d.b.b.ACTIVE);
      switch ((int)com.yiyiaddon.m.b.a<"s3bh7mh0n71pdq","mlc77UTgtE6GYd3FzqRXvVmvP2kZCf1b2e+WRhW2Zhg=",2005000567628968901,-4767242570198015468,6568817120529900685,-4868009669837515226>()) {
         case -578457014:
            return;
         default:
            throw null;
      }
   }

   private void aL() {
      if (this.a == com.yiyiaddon.e.d.b.b.RECONNECT_WAIT) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ufgl1eokqfjz","Aakf5q1+pu8dDX42zDMDKPJCZyTPvEVSJlTAjUN1JXg=",1553872132347380450,2436791278998681786,7862230017159277512,5743398075718297988>()) {
            case 872465588:
               if (this.a.aq()) {
                  switch ((int)com.yiyiaddon.m.b.a<"slpanbl4mq2t9","np5DQ6H9iYGfCZoK71G/9ZJDrUQlotMFxaKLOUpm9CA=",-5026156888543081514,-3706102459948716740,8000149421547736111,6466641958127882689>()) {
                     case -1616585688:
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

      boolean var1 = this.a.ag();
      if (!var1) {
         label87:
         switch ((int)com.yiyiaddon.m.b.a<"s23vlbpcaj1hze","BPPU3scANbxFUbME+t3k9Bm5OrFoB0fPhj7VlC9al+w=",4290381889739007316,6099321468043158292,-5553033698873247043,3378823297302690156>()) {
            case -237757336:
               this.a.aZ();
               switch ((int)com.yiyiaddon.m.b.a<"s2amdttoncbkv0","SgVGdhPVvsKu6C5WKRFvzgrGjJXHEod1n+KHVFLbbGs=",-209331078032900330,3986728313114950646,-8924211610434666932,-7758317854468070440>()) {
                  case -743527714:
                     break label87;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.ac = false;
      this.bJ = 0;
      this.aN();
      this.a = null;
      this.cj = null;
      this.X = false;
      this.Y = true;
      this.b = null;
      this.bH = 0;
      if (var1) {
         label80:
         switch ((int)com.yiyiaddon.m.b.a<"s29y52bt32fkd","aJZOvG8gFmQuu5hdDLNdjwGNTEna+PJ0123zNeeS3Ww=",5291362432690564162,4068216161047898986,5920643471624193864,-3785626944579531708>()) {
            case 227792520:
               this.a.ag();
               switch ((int)com.yiyiaddon.m.b.a<"s1hoq0f6bcjzxz","jWrwN6Yv4tV2M5odtqcV2o3SaiXcG0zhykG1KWTQOBw=",-6200044721528986969,-1330970588018430713,1664836773088083141,-7522799572383010669>()) {
                  case -99051145:
                     break label80;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.a.f();
         switch ((int)com.yiyiaddon.m.b.a<"s21hdw0gmxvfvb","SO1/rPg52yX5zGQ0QziaA7npCuat9RvJpZqaCIH2RMk=",4356776204261657475,-6216457665326501943,4374928453460307691,-6989590059813396931>()) {
            case -746541678:
               break;
            default:
               throw null;
         }
      }

      if (this.a.ai) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mk4ter92gl45","7kdYsUFB+kap859ERGUOfDyEBWp0Eul7cgwe2R6IHRc=",-7921144683877818704,-5413596394483619381,5058153469399560225,-2168806605840477190>()) {
            case -2146399615:
               int var2 = this.a.cg;
               int var3 = this.a.ch;
               if (var1) {
                  label70:
                  switch ((int)com.yiyiaddon.m.b.a<"s18gq6cz0ohjal","/vvubfbAv9dAiRco/qMIGWAv7jnpAJ9XeYnE+8mskW4=",-1543185744478543709,1268884760208435243,5309738425130632251,-8857461763430175668>()) {
                     case 806847526:
                        this.a.bb();
                        var2 = this.a.g(this.a.B());
                        var3 = this.a.ct;
                        this.C("" + this.a.a() + var2 / 20);
                        switch ((int)com.yiyiaddon.m.b.a<"s2iwbe551r62t2","TEKmPAIvbfV/RvUQUddczq7NTHLcOp9EUaovctztQKk=",1396294716255385798,-2338557239169487472,4002531720620174728,-667681252407709980>()) {
                           case -1110178657:
                              break label70;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               int var4 = var2 / 20;
               if (this.a.a(var2, var3)) {
                  label56:
                  switch ((int)com.yiyiaddon.m.b.a<"s7ro2glw6377c","UccMt96Hkms71DRLxcfI/ek6IdG1VANmuO0Yn+9bris=",-5053892286838942570,-6530202423641361032,-5102237107260658586,6250497076345091939>()) {
                     case -1972047512:
                        this.u(var4 + "");
                        this.a(com.yiyiaddon.e.d.b.b.RECONNECT_WAIT);
                        switch ((int)com.yiyiaddon.m.b.a<"s1xtt1ropyfbad","QeUpfCCh4MO8rTjYtxMNt6D2EwNqCG3+u3UdezGCpr0=",-6064383681933741478,1536216432010414021,-8186880087075711375,-4822533900616266331>()) {
                           case 2041185465:
                              break label56;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  if (var1) {
                     label61:
                     switch ((int)com.yiyiaddon.m.b.a<"s3fan0d1h9r9sb","8C25zgVrM9l2a5ENNk5Bpww3hmOYxX7MzIQwWMGKDnU=",7493332289807941464,-1369761862410549275,-3878937548684477248,7862947322056682701>()) {
                        case -1352320388:
                           this.a.bc();
                           this.u(
                              (String)com.yiyiaddon.m.b.a<"sjgb73yccxfd3","GAqIigebReEmJo7QHIusgK6lvyVPQy1Y4oOfK6ZAqOLX9IFMTGQTYSKweOvuDSPhPPFWpfj/O1kOScOXSFV2vm0UnAFqNEWnkI4=",8324609571180823400,4494744208544825519,9024888231374475243,-9116949595085150592>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s201i3e142t3kw","8bmYxRH2/0YKkRt9TX/1dB9+g8M4JBL1pqSnWY+RkHM=",4465120568959161771,-87953136939214614,4520529915879118209,-774423988220257244>()) {
                              case 399157644:
                                 break label61;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.u(
                        (String)com.yiyiaddon.m.b.a<"s21yukz9t5v1ao","BTBfpTjN957wSyVv/c5UX128aCRxq0HkOxfHgwGZLtXyjAypzkexJ1gyjYC+Htxx9Er40j6qOWnBQF068p8=",-5439231950664246738,-4708243446695464637,-2672190562602766785,-6118850321458177401>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s3v64e9hohx2hr","OWelGwV/4vHoS3tj9OtuOBWofr7DCOgVG8AkY5KJJhQ=",-2592548151566001049,613154068346849920,4180788369724922966,4330252828253274227>()) {
                        case 1317494282:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.a(com.yiyiaddon.e.d.b.b.IDLE);
                  switch ((int)com.yiyiaddon.m.b.a<"sp27tr4agndhw","8M3+9ekKpseS2DzbJucWuWP6fxg9BFg8WFGIg2hA3oc=",-2439479902385357097,-7037660406331709767,-7801207133343089435,-3343423366002304406>()) {
                     case 138218758:
                        break;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3p8gro0ed2nvu","Q5sVwb5Szt+zsw2/7ha3qivNOwaB43t8zrNeuHzBIng=",-7433976051344461915,-350622353832245977,-3435477514546641920,-940548001913142690>()) {
                  case 515605013:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.a(com.yiyiaddon.e.d.b.b.IDLE);
         switch ((int)com.yiyiaddon.m.b.a<"se253bgvogv3z","J1UP0RLXL2IsW+GPlMW0VGcHq6mvchYd0SF6TCKaeSA=",1466650434097012215,7939513712729819592,2318758227193612846,-1353740218398667483>()) {
            case -1638762828:
               return;
            default:
               throw null;
         }
      }
   }

   private void a(b var1) {
      this.C("" + this.a + var1);
      if (var1 == com.yiyiaddon.e.d.b.b.DETECTING_AUTH) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2vnkec3tvi2qz","8VuPK43Lytg1WUnf8PG0mL9+AAMOGDhGO2gl3KpMh0w=",-5983907329138688950,-2551469618457432816,6347405077318695906,-7559974045743522121>()) {
            case 1063207183:
               this.bG = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s1w398n8qnqq3n","ii/T434riwtmK6VAC0evYYZHK+ndK485s90eaKTxG/s=",5436635330326092377,-2472310025271209959,6628557033405179674,7476101772167039690>()) {
                  case -1317283025:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a = var1;
   }

   private void aM() {
      if (this.a.af) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rbm96xclh5wv","VuKia0dLegfEN7zIxCRGBo+XrXICvtOxyptHXyxmWFI=",950978003514994358,-6472370453052446005,6562567219674857428,-8212178451668016550>()) {
            case -1775058429:
               this.a.ad = false;
               this.L();
               this.u(
                  (String)com.yiyiaddon.m.b.a<"s22e0wru9hrgic","HXldhF+m1h96covsDXk0aKw1z6jyGzDtEX6jxslADEmJIfSF4x2V6qmOXc98QoMoD1Y2yRE6T2895sN0UMaOS1DO2zFfKM7Km4l5H4yFDCCy/R30+Ehz0A==",6045692395932514586,-5654175563381818878,9029478963332617989,-5842638364937256162>()
               );
               this.a(com.yiyiaddon.e.d.b.b.WORLD_LOAD_WAIT);
               this.bF = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s24riyj3zssu03","WA40CF4jdeZNaxWOKFnbwjsqr3FKwFVJVF+FS5mCY2w=",-1816167583418189759,5329503321870331182,-3500964205212037966,-2330441284656872860>()) {
                  case 292010020:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         if (!this.a.ad) {
            label30:
            switch ((int)com.yiyiaddon.m.b.a<"s1ywhj79y24e8i","9stnLx+8jFYjo11ZH2JDS1UT9GLAUdG6gFDLvtGj1bo=",7532364823354951566,5857997811128593399,-3464456949441615402,2398289658282396626>()) {
               case 1696700962:
                  if (!this.a.ah) {
                     this.aK();
                     switch ((int)com.yiyiaddon.m.b.a<"s131i860jx4ely","RQxzg4xb5nCAEOrh+7XNKlF0ftDmUzHGZwVzt/oJrGM=",2984422397179201850,7979688863552277503,-2050254487218590258,-3750853633609174869>()) {
                        case -424038820:
                           return;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2f534t8bvari8","tkdMnEdFtUyG/KPknHm39+/+d+ZHSyYf28Q1mcY7kuM=",-6865809591475709081,6591355348938046912,3295682811904892481,-4882065030841229399>()) {
                     case 1015747141:
                        break label30;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(com.yiyiaddon.e.d.b.b.WORLD_LOAD_WAIT);
         this.bF = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s39uusqbtgw53g","6XGjZenk+zr98pPkpWpmGlvrG4Wptvxeu0UCsHNLg3I=",-3971128274888206325,7859902573596300624,7788046163993495033,-9193289065499591390>()) {
            case 2098621290:
               return;
            default:
               throw null;
         }
      }
   }

   private void aN() {
      this.S = false;
      this.T = false;
      this.U = false;
      this.V = false;
      this.bF = 0;
      this.bG = 0;
      this.bJ = 0;
      this.ac = false;
   }

   private void aO() {
      this.a.f();
      this.a.f();
      this.a.f();
      this.a.f();
      this.a.f();
      this.aN();
   }

   private void aP() {
      if (this.a.al) {
         switch ((int)com.yiyiaddon.m.b.a<"snu4s7kf20j3t","R2g2FhvAJoFdzSiGhvMT+F3MNOojzyk3g1RL8rCE3u8=",6784547350942922323,8539423811501841402,-1236075102673939661,1768349361859007566>()) {
            case -941885247:
               if (this.a.a != com.yiyiaddon.e.d.b.e.DIRECT) {
                  if (this.a.a == com.yiyiaddon.e.d.b.e.LEYUAN_CUSTOM) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ayleqssay3nd","M/eyfjF8KwYUbrVtM6ndyItoJddfkOy7u5/KfgKL/BU=",2816272261061277214,-6128997786542564810,-8254016468618618025,-1965490592674489622>()) {
                        case 263432456:
                           this.a.aR();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.a.aR();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nv0rzt8ez9u4","ceoTLiJgheWfq1FWC2BANMl0oINHTaIi4LNSLe0hN54=",3946229655241513780,1930618126962689242,1244145215559874290,8811467390130189170>()) {
                     case -476100134:
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

   private boolean T() {
      label37: {
         if (this.a.Z()) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s2ofbd92o1st1x","3EtUHJdwLz4evl+wTWeT+BXh/ZUD1rdeVi+BVmpTyTk=",-6424253326763856972,7501413417249132743,-2059844967830159109,-1042873869507718510>()) {
               case 349775637:
                  if (this.a.ao) {
                     break label37;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s31pj1qu70g8nv","JxFsAJ7L1VhkXIJJtoWuQBxKjv+VQjqwvIXA1t0ZT4g=",-2904656830977527090,-4230318544598024145,-3100748350600760175,4995927347679572181>()) {
                     case 303796404:
                        if (this.a.ap) {
                           switch ((int)com.yiyiaddon.m.b.a<"sa25f85oi77qd","qz4qVHbUaTrvPZRGMUUVUeq0DXbQ10/DctodrQgWsB0=",3420394429530320347,-3357082656702977146,6945492893183253361,-8430326024462934079>()) {
                              case 1958129117:
                                 break label37;
                              default:
                                 throw null;
                           }
                        }
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s2sqh755i0rt8s","dGKr8ql60jJEiSgG0+xZ7Luf2PEe9dqqnGqqDgZKj8E=",-8054585417238743758,5588245051788599568,-6082764886742743915,-6118132813283717460>()) {
            case -2126436322:
               return false;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1v3w7me3jn53","bsu7x3GqUvOcfEwb9+x9NdWS1cNjC2IVj4wlN+uXHdg=",-497140663165365566,-1803911129853575209,-6328701202547826796,2199662672895022195>()) {
         case 1629814308:
            return true;
         default:
            throw null;
      }
   }

   private void B(String var1) {
      this.u(var1 + "");
      if (this.a.an) {
         switch ((int)com.yiyiaddon.m.b.a<"sowf5kzhv9foi","7c8Asd7AJlMxardsmH0NtRT3x8H3106j9HXOyennwDU=",-7113561786529232201,133684149793396175,-8293255640021448287,2109047459808126340>()) {
            case -1099783974:
               this.ab = true;
               this.bI = -this.a.cl;
               switch ((int)com.yiyiaddon.m.b.a<"sad42uhbhr563","jYL/Bdq2gwEqSeeEJ6TsqN1hOEn+adSD7rX+3SKAVec=",102894497098815055,2503719569727477282,-3652602340405959892,1207922459855144760>()) {
                  case 938555730:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private boolean U() {
      if (!this.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s25jkz4z790nlg","J0ngzrdKWWfjNn+ddhTnAfAKLgNi/Ny35p+qQkacNm4=",4919820368092606101,8635022958747040040,-6116955589216977696,-2007286667062843272>()) {
            case -1185898866:
               return false;
            default:
               throw null;
         }
      } else {
         if (!this.m.hasSingleplayerServer()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2e7atsfkq44w2","Ye14QTB6QX2BPBhEJEIxTkQTusvuG7yfIbEmRjxuscI=",894217127842116764,-6131653657207037873,951419276544343781,4490192621379587738>()) {
               case 1031661409:
                  if (!this.V()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1b1glthywxmp4","cHQt/XHVFQl1mbPlt9H1IZ6NQSgB3gEszh5VcRZFTw4=",5609167658556779773,-1658653081059313060,-6150457155364610290,2723021231136174788>()) {
                        case -1358513677:
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

         this.u(
            (String)com.yiyiaddon.m.b.a<"sds0vyfun6naf","HK5HuSeEpt0mvxaZYiC4ZhlathrBlHE5LVEUXH5mBhEFQiIim3iQdN7Xm+ye8tZRl271g8HKT9Ys5N/InnZfNSwcVv7Fmru4+75mfD7X+bpyuQD0kXJTMz+U",7274761448613280151,3979346663212829838,-2713184166549232549,-8969131590868172435>()
         );
         return true;
      }
   }

   private boolean V() {
      if (this.m.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s20roegtif78z8","fxjSST/yOksyB3NcFfqRi8t1S/bRxAYUGocpZHyhQTU=",-6752145810420879948,-25949113655906187,-4163323642390614770,-7993120291596160158>()) {
            case -712448026:
               if (this.m.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1p92wrj2rc79h","5Q6lmWHmxau434tXG2fgbZaEFwqCAZkxePAIfp1/Vc0=",2956678969643002024,971143719378334680,-58038994671294602,-420816318761307794>()) {
                     case 1698378752:
                        if (this.m.getConnection() != null) {
                           return this.m.getConnection().getConnection().isEncrypted();
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2o78fhdo7a3lk","CZ/eDbSCwPju7bLSmyZsdvnvtH1aa6xyvLFsqrExEik=",1009965514028416853,-8062141976793726893,7239911482813174853,8090462153761783094>()) {
                           case -1470799270:
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

   private void aQ() {
      ServerData var1 = this.m.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2urco41qnalub","s1q7T/tEvpKRS0bhTK6arHH6lZrhGmNRhrMBVbOYP1c=",3746796336371277142,-8618869347317156336,-3890788053028359716,-8913276870469680281>()) {
            case -1246402800:
               this.a.a(ServerAddress.parseString(var1.ip), var1);
               switch ((int)com.yiyiaddon.m.b.a<"s3pi7uwxzk7g5","4QOfyUH2zdHizuHu7aJIsXufTm7Td+rIpDaSgS8w3Hs=",-2533027741532847101,1444961095581651314,-6223642846745791571,3214280180882353154>()) {
                  case -618008943:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (this.m.getConnection() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3e389q4plk899","2ZPo2dpb1aoCyV8Wz0OkdBelSF1LEpETmV5I/Uu7MHI=",-4516908635412576323,2283040099926109672,10952998750603966,-7099505753782755401>()) {
            case 220948611:
               if (this.m.getConnection().getServerData() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1rf85mhfl7k3r","uam2dQ6QiEEuuyncbySXpvYOnyb5Rj+SGZbAvuycgko=",5688645109189452776,4909459711714698227,1208240833553137231,-7816156351284500860>()) {
                     case -705998050:
                        ServerData var2 = this.m.getConnection().getServerData();
                        this.a.a(ServerAddress.parseString(var2.ip), var2);
                        switch ((int)com.yiyiaddon.m.b.a<"sck42wn6bkak9","NkjMTTt3cxd8A1MjVmmK0kI6ejyp+3KDLg+ul4ROoaU=",1000957565426106313,6029409500591429443,7843721637943731032,-5178477872800020902>()) {
                           case 1750403503:
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

   private String an() {
      ServerData var1 = this.m.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17m737i95nby7","us04a6DC/Jl5ZsQTgJq73LyNwHfgIeJwBCZxP0PPyrE=",721566923388990775,7358158936798059698,-172845920938805092,4798286497772702041>()) {
            case 1858034364:
               return var1.ip;
            default:
               throw null;
         }
      } else {
         if (this.m.getConnection() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3iii5u2h6uyum","AciwRTqJuoq+SllEsOxih0HkXcff9e3FyNOapnwD1eM=",-3148248222592561587,7059011402454794907,10932153599680160,-321698818852458794>()) {
               case 1122762836:
                  if (this.m.getConnection().getServerData() != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ohl3c3og0d7i","rGWislOlLoP8u0XqqCo47/Jb+gxaVcQuMs+3cbxcFw8=",3659965072080928274,6392766891252286072,8840858608480295822,-5699361992047581851>()) {
                        case 1067236926:
                           return this.m.getConnection().getServerData().ip;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return null;
      }
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s2zxg6h4nf8edr","GBdkSCpegkiqQvTU8YOkVu0TfamZ/CWRPfX+itvLaGoq8y1D",8177836824299977194,-1357611281647263253,-8010793259308214615,-3713882449301070219>(),
         var1 + ""
      );
   }

   private void C(String var1) {
      if (this.a.as) {
         switch ((int)com.yiyiaddon.m.b.a<"sptlx9lunn1td","Gcu/uANbWKkRwrK2YHVB7Snp3sKPMzip/8AzKVBM1QU=",-8322451590392451630,3627229592112296747,-2032106044377384055,730746603493229827>()) {
            case 1416533036:
               this.u(var1 + "");
               switch ((int)com.yiyiaddon.m.b.a<"s3su9fy8tkwy5i","gsyQsSnM4HBgNPXI+5f5M+qir5KZTywWDEMWI6JwMA0=",-4044767196776750391,8307085748502538026,9093910695481898123,4701569951466511004>()) {
                  case -1903764228:
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
   public com.yiyiaddon.l.f.i a() {
      return new com.yiyiaddon.e.d.d.b(this);
   }
}
