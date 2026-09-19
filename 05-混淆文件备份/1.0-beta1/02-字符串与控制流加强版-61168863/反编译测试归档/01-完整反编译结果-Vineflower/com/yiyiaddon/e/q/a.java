package com.yiyiaddon.e.q;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.a.c;
import com.yiyiaddon.e.q.d.e;
import com.yiyiaddon.e.q.f.d;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String zq = "villager";
   public static final String zr = "自动村民交易";
   private static final String zs = (String)b.a<"s1hy5550jjkzgo","zZlKqpRHibwZ22frEoSl2e1ghM3d1+yUjHh3vUd1",-784028524938058734,-6416302743299260663,5421876057725567640,-3512660285693527351>();
   private static final int qi = 80;
   private final Minecraft al = Minecraft.getInstance();
   private final com.yiyiaddon.e.q.b.a a = new com.yiyiaddon.e.q.b.a();
   private final e a;
   private final com.yiyiaddon.e.q.h.a a;
   private boolean eV;
   private final Deque<String> h = new ArrayDeque<>(80);

   public a() {
      super(
         (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
         (String)b.a<"s31c7z6rfuf4q5","R6OEjwBYMPF55o9iG091pIPBNqQccmjxnK1vjEwl/qpmsOWyhPIYig==",-1768344565869983054,9170179964200634718,740196728275576213,-4400669068104229730>(),
         (String)b.a<"symdtt5xgjzmv","cGwaoQKhN/jiowaEOXfrK7xwhVMHI7JiStY3F2siV7/HxIMlp1duKAjV2i8z9cG8",2146212488740148953,-2057769853645225879,-7327518998652246675,1299247396089041192>(),
         (String)b.a<"s101tdcswrfqqg","apc/Q+HSpX7ynGdXPUXT3yUCz7FlrMJmU3W8kAoEegHmA43MPH29/Vyy479KCiNLzDKQZgfd2hL48eusJ9dZHZpw6lqFGve0oxq0Lgr8Mn+gMg==",1359798222970457505,-9031994361338249136,-3114703619596314074,-6402893357520083808>()
      );
      this.a = new e();
      this.a.a(this::K);
      this.a = new com.yiyiaddon.e.q.h.a();
   }

   @Override
   public String a() {
      return (String)b.a<"s20wbxk8fdmj17","GUdUAGEuKdkVsfCMd1N74hP6DuM9XesFL3q3Y7HXAI9YlP6HR3ixo4PW9YRTppGLk+suuCIYEOm4mTfDz9U=",-8006934103960092656,-5545004249413136603,-6506938279351191949,6432182700497029690>();
   }

   @Override
   public int i() {
      return 70;
   }

   @Override
   public String w() {
      return (String)b.a<"s1hy5550jjkzgo","zZlKqpRHibwZ22frEoSl2e1ghM3d1+yUjHh3vUd1",-784028524938058734,-6416302743299260663,5421876057725567640,-3512660285693527351>();
   }

   public com.yiyiaddon.e.q.b.a a() {
      return this.a;
   }

   public e a() {
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
      com.yiyiaddon.d.b.e.d(this);
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.q.k.b(this);
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.q.a.a());
   }

   public synchronized List<String> ac() {
      return new ArrayList<>(this.h);
   }

   public synchronized void dC() {
      this.h.clear();
   }

   @Override
   protected void m() {
      this.eV = this.a.e.fV();
      VillagerProfession var1 = this.a();
      List var2 = this.bn();
      List var10000;
      if (this.a.a == d.PIPELINE) {
         label44:
         switch ((int)b.a<"s2ky8oquri08yg","GLc+vHVXfMIc/YZxAN6arKuKgjyCLOnVJlwXPerwCew=",3027270463427485731,5302133053379443198,-6057612232717572803,1761273222808131667>()) {
            case 1604228175:
               var10000 = this.bo();
               switch ((int)b.a<"s1d3mkxwmavo19","AZZ8626pXzo2yGwjCUg2kStZ7lbj7eTgDsvCN/ldnt8=",6118866040505764113,-934552033410646365,-8691537655409598092,4247589350235450099>()) {
                  case 517235339:
                     break label44;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = null;
         switch ((int)b.a<"s39ibpw3lx6tud","bFdDLOPyd/eRLJo/JNx0iWcxYeASFdhSH0atH2NOnuM=",-4080190954749089996,-2774303940130368520,-506539150183692977,-4117023607721038979>()) {
            case 1820864941:
               break;
            default:
               throw null;
         }
      }

      boolean var7;
      List var3 = var10000;
      String var4 = this.a.a.name();
      int var5 = this.a.o(var4);
      byte var6 = 64;
      this.a.d(var1x -> this.ir());
      this.a.e(var1x -> this.ir());
      this.a.a(var1, var2, var5, 32, var6);
      this.a.F(this.a.qn);
      this.a.G(this.a.qo);
      this.a.u(this.a.eW);
      this.a.H(this.a.qp * 20);
      l.a(
         (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
         this::a
      );
      this.a(var3, var6);
      label40:
      switch (this.a.a) {
         case LOCAL:
            var7 = this.a.a(d.LOCAL);
            switch ((int)b.a<"s29sqmd102kw2o","PFx+hZb87OsA4lr1Gb1iKWCjbDuQ4+ctuL4r7OjZOrU=",-5093064700204357380,9107562708300882005,46136550728509248,4287392674858302869>()) {
               case 1781424029:
                  break label40;
               default:
                  throw null;
            }
         case SINGLE_PATH:
            var7 = this.a.a(d.SINGLE_PATH);
            switch ((int)b.a<"s3u544earjaad1","va6JWvkbmoPpriTHJo3nHcqvWEatJr6SXvUhWyEaWKM=",7680377235882606382,4886796475799119720,-5560661923232708670,762855040760475047>()) {
               case 1049356025:
                  break label40;
               default:
                  throw null;
            }
         case PIPELINE:
            var7 = this.a.c(var3);
            switch ((int)b.a<"s2l0wow8dv3i0f","y+8mxU0ruGwkIL7DOVAnPwE1g45tRiwcbgJxNP2SmSY=",4179361827473038255,8623739598411992036,-1664081116930650788,6144398655432268431>()) {
               case -781912429:
                  break label40;
               default:
                  throw null;
            }
         default:
            var7 = false;
            switch ((int)b.a<"s2a6ki1r4uiq6c","rElvcEkRSpX5VIF8NphjzbyGfHnuWDY9C+ed1iMa2Ow=",234932847000740252,-2102443976713287529,-4071778354114555237,-466917908572773376>()) {
               case 47377836:
                  break;
               default:
                  throw null;
            }
      }

      if (!var7) {
         switch ((int)b.a<"s2mfxe7iwc27hy","ZiEvTfC1ejwJ1svLZr43MWL+I/6yoe6iyrEzUrcJbNY=",-6299076405932677743,-2096823971182511744,6568300995533748307,-3103785453318944752>()) {
            case -894659406:
               this.b(
                  (String)b.a<"s1ll9r1t8mmpur","jk4GdNYU9BaTWe3ycGVZ/hX7vHBzTyWld1RzvX5z4aILd+3kB3QlYpMK6U/a2rXjDZtHEA==",-9093834469222692485,4935640097997468842,6291304229634458295,-8707114208638417178>()
               );
               this.al
                  .execute(
                     () -> com.yiyiaddon.d.b.e.b(
                        (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
                        false
                     )
                  );
               switch ((int)b.a<"s5nnij5hvmkv1","8g4ieJLP9Wh1LMaWSq+t3iXMrfJMDBNvLVu8k8NsSJ4=",8261421741868442846,656223029184230308,-5961542042033417182,7010678212896191534>()) {
                  case 63344542:
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
      l.l(
         (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>()
      );
      this.a.ag();
      this.eV = false;
   }

   private void ir() {
      this.al
         .execute(
            () -> {
               if (this.g()) {
                  switch ((int)b.a<"s2utd42iensbxb","0G1Uo+Lb4DepwQp/BLvOEKO5aGoYa/zV9S8kJ+7gAnY=",6777877176183728854,7666355604699144002,-8261297306069639615,7359703320618417490>()) {
                     case 810920862:
                        com.yiyiaddon.d.b.e.b(
                           (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
                           false
                        );
                        switch ((int)b.a<"s24h15iikyxiug","4ynZnCXHy8L1boKdCFCjfnzPb344TT0sr6RqKlTKVQ0=",-1582317590638000846,-5569483948290223706,6557049960439057295,-2716860732069719539>()) {
                           case 108684811:
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

   @Override
   public Set<c> c() {
      return Set.of(c.TICK, c.SCREEN_OPEN);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)b.a<"s3or2767u5f43u","NR6gfXNDca0cANhq8LLerS5mAM2lMM+1mTvBIipRytU=",-652735437917696262,4416443455161306247,1267432128148746000,8824859662789394932>()) {
            case 647889930:
               return;
            default:
               throw null;
         }
      } else {
         switch (var1.a()) {
            case SCREEN_OPEN:
               this.i(var1);
               switch ((int)b.a<"s3hqd030qiscxb","igQQKqF7IG9c1WGhsH3bXEcOjEZhGjRfINRMSAq5J94=",2004266550758193912,7674164920724734911,8080085882819928409,-4244518185671433877>()) {
                  case 1607472895:
                     break;
                  default:
                     throw null;
               }
         }
      }
   }

   private void i(com.yiyiaddon.d.a.a var1) {
      if (this.al.player == null) {
         switch ((int)b.a<"s34l0of6slws76","yCj2S//Fbrm1PaiqWEz20v2r8xBPg9aRFWey6LlFaWo=",2292762784506720474,-6444985359882285857,-2100113459609263773,680628656031750814>()) {
            case 496161881:
               return;
            default:
               throw null;
         }
      } else {
         String var2 = var1.l();
         if (!this.g()) {
            switch ((int)b.a<"s22xyv0s0ignyh","TjeCmfAsjF4lyBXNsYXp30o6GWrHe1dxVA3U9OZfodE=",5128457296854893193,-1933299254038101126,-7383353083786537557,8029708557603382630>()) {
               case 720272750:
                  return;
               default:
                  throw null;
            }
         } else if (InventoryScreen.class.getName().equals(var2)) {
            switch ((int)b.a<"s3e9rf29uv4byk","Kfgyj7mi4fa65T1x2kZTwaCQvQ6sBJmpT6zzjWdsvYQ=",8294754432384006522,5105207900078971394,-1944840457766143868,4286412550810785333>()) {
               case 1280206369:
                  return;
               default:
                  throw null;
            }
         } else if (CreativeModeInventoryScreen.class.getName().equals(var2)) {
            switch ((int)b.a<"s22w2hmy0lns90","b0HKETeOsmt+bJB1u1ryAsDp9xF2Pjsn68EIiZ0fEVI=",4455919566343029692,2105980278920500306,-8663570115497280322,-8556070461975364308>()) {
               case 79878280:
                  return;
               default:
                  throw null;
            }
         } else if (m(var2)) {
            switch ((int)b.a<"s5zhqc5ok8ql0","DdgzXf18VT+QcCGIgwl922VAq2q4p/hjJU1qqg4TZCg=",1684851605967991175,-2372532938903103578,-310437130485967370,-3747931538300281674>()) {
               case -1948274292:
                  var1.i();
                  switch ((int)b.a<"s278yq6e1es7og","CeqGKd+whhGVnqNZR0qR2e8Xf6o8RRCoC77N6JwYLG4=",-7953734382502312605,-6810973159233379309,2727001919138584182,-8659269751911005006>()) {
                     case -893472799:
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
      boolean var2;
      boolean var10000;
      label48: {
         var2 = this.a.e.fV();
         if (var2) {
            switch ((int)b.a<"s2qxwc05dcgmxm","KB3vSv7RNGScZbaIyyPPnIWnktkBbaP6rDXnGBO6YGY=",-5563482677267789272,-5944811576796437994,-1548022009651525797,5704181279908111553>()) {
               case -2107507166:
                  if (!this.eV) {
                     switch ((int)b.a<"s1v51uswit44xi","YSdnzzpBV8xJxgpBBUzRN1GIJBkl1wqewmAS1F/jqX4=",-7092294516096998939,-8748842971046157055,-8233272066267924017,6188404590910506066>()) {
                        case -1154899992:
                           var10000 = true;
                           switch ((int)b.a<"s302c3dnza5lqe","xcpjk8n+Ncvilg4XYGAqAS0sbVVcP9Y6S04VsUs3l0k=",7836653859749454397,-379750340950428342,-1287831493779814190,7318006234752792584>()) {
                              case -1716223251:
                                 break label48;
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
         switch ((int)b.a<"s112wqac93779u","DfMGdqY11Z1LnepbL6LT15LSyIGu5Ln4td8geHF9U98=",8973265602459441406,1460053221113234969,4204896980755822484,-2158957827798315474>()) {
            case -1735062028:
               break;
            default:
               throw null;
         }
      }

      boolean var3 = var10000;
      this.eV = var2;
      if (var3) {
         switch ((int)b.a<"s14tfx31jd3oa2","69vcgBDvjXoWSFIdza7ASNMtPJWIXdo1BqhlzmYUO6o=",-5633759209124813776,-7785327710714806536,-7420027592697781561,-7995178849137298979>()) {
            case -1657827206:
               this.K(
                  (String)b.a<"s2cvo1f6hs4lvx","Tk4WuuUIMg/fg8VPbqdE2aUD26+r6c1UIidJ4nbO4N8RBUmDK0TP9cmnbxa+voyyyXkLQTXeIMQTfg==",-7139644260191764258,5142770896054088077,7694275308219034517,-3764045236764497703>()
               );
               com.yiyiaddon.d.b.e.a(
                  (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
                  false
               );
               return;
            default:
               throw null;
         }
      } else if (var1.player == null) {
         switch ((int)b.a<"s1ja2p0ui0t2sc","BV3Tm01gqilvHJkskxdScTr9q1uLfXc9+xySi4OlLfY=",667442331959363986,663737393001230613,-703006729991296723,4362270734767550992>()) {
            case -1914087738:
               this.a
                  .aV(
                     (String)b.a<"s2o4mvfpccjtga","OSU2H2VcNPZFNXZlZFYIHMEliCJAhr7W/jJ0GUzHmv6OIqgh",2122502151184660702,4291662429429253846,2579909072881612948,-5728191195164827210>()
                  );
               com.yiyiaddon.d.b.e.a(
                  (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
                  false
               );
               return;
            default:
               throw null;
         }
      } else if (var1.level == null) {
         switch ((int)b.a<"s2ryy5t5gql0vl","ticqvfmFTR0KCWGnXxs146T5ubp4il28oEQbiwGO8tI=",7286141454597597665,-7912035948893697209,-8926343651119775112,-6178743811633824172>()) {
            case -606051404:
               this.a
                  .aV(
                     (String)b.a<"s19llnojnjf6l9","21vn2rEiA1FZ95td4irObPtM4VTblgaLslvMm+CpDWBNxDNk",7602626775642141188,5930697624026548488,8488782534690755799,-1593762633941869851>()
                  );
               com.yiyiaddon.d.b.e.a(
                  (String)b.a<"s14eugbgtl4qxu","PUuySHn2vjTvhYVPxp61Y7OVv2FBu3la14T6i64ydZ+f0vRWD9M185zS6oc=",-7713211211636359983,8656500327438700310,8814134341909470848,-1409085928920067469>(),
                  false
               );
               return;
            default:
               throw null;
         }
      } else {
         this.a.ae();
      }
   }

   private void a(f var1) {
      this.a.render(var1);
   }

   public void K(String var1) {
      if (var1 != null) {
         switch ((int)b.a<"sx8kz60f2qlk3","R0cLwCkjDXPCdovGaiS25IKC8fD7zzC34tEGMyVEWC8=",8747828171852965087,3091567264315757545,5248632023461764852,6173908772844783919>()) {
            case 698894791:
               if (!var1.isBlank()) {
                  com.yiyiaddon.d.c.a(
                     (String)b.a<"s31c7z6rfuf4q5","R6OEjwBYMPF55o9iG091pIPBNqQccmjxnK1vjEwl/qpmsOWyhPIYig==",-1768344565869983054,9170179964200634718,740196728275576213,-4400669068104229730>(),
                     var1
                  );
                  this.v(var1);
                  return;
               } else {
                  switch ((int)b.a<"s1s6qlh1ic05d3","hwfcHsI5RI2ppBzDDnzeOLeeSkoiM7s4YkPXKHBJZBA=",7600020761261822005,1682250588415089855,-1973259909804031719,-3248276758305015959>()) {
                     case 1177254595:
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
         (String)b.a<"s31c7z6rfuf4q5","R6OEjwBYMPF55o9iG091pIPBNqQccmjxnK1vjEwl/qpmsOWyhPIYig==",-1768344565869983054,9170179964200634718,740196728275576213,-4400669068104229730>(),
         var1 + ""
      );
      this.v(var1);
   }

   private void v(String var1) {
      String var2 = var1.split(
         (String)b.a<"sjqqs4xhlkiky","U4vbTNCysM5dqUFr9IY3DRZdQAsFVKTXdE/z+Y+m",-2694251255922629718,-8086860772172275165,4681913208950854130,-2513115658061605731>(),
         2
      )[0];
      String var3 = var2.replace(
            com.yiyiaddon.d.c.e(
               (String)b.a<"s31c7z6rfuf4q5","R6OEjwBYMPF55o9iG091pIPBNqQccmjxnK1vjEwl/qpmsOWyhPIYig==",-1768344565869983054,9170179964200634718,740196728275576213,-4400669068104229730>()
            ),
            (String)b.a<"s2iv546kcxkw9i","uNXaOKEnCvcD7m1Lucv2ipvhgON2sWY8RI0jgQ==",4037995778971957520,-382345264589530665,-6489639403989905441,8425895464078122475>()
         )
         .strip();
      synchronized (this.h) {
         while (this.h.size() >= 80) {
            this.h.pollFirst();
         }

         this.h.addFirst(var3);
      }
   }

   private void a(List<com.yiyiaddon.e.q.f.a> var1, int var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(
         (String)b.a<"s2b33l8w24s3j9","fods1flguxykMhswNefqsCOybHLYKtb0nzNLTSukER1AloZSNGqKdy+7Jm82f9a8BuVyp0JAjS/f3DlFwyucVXgg",-5762268600430310817,2716244867828450396,-1813565206122535081,-3407083514434590833>()
      );
      var3.append(
            (String)b.a<"s1fum2mliierk2","4dwSYJaVpqJitpipsV+g+qZDhtSUMcNuiegARTCqPfvDQujV/IJLC/WF2/JjCXAAegWjkQ==",-582055711182108728,326606636229681519,8935483638985310651,-1469910526025051624>()
         )
         .append(
            i(
               (String)b.a<"s2nyfqkwwaml2l","MuB5IZXZijtMWsCgq5YkYts6Z9F1DgVIdtp4dt9aIT8d+fIH",2487484307127270105,-3682005451811595391,7226353906867608113,-2430859945243589750>()
            )
         )
         .append(
            (String)b.a<"s3hzzm9ducqvy4","QAcM42YKiw/MbfV9Kwf74rXZVj/LWIcIb8V9sfGN0/zyczA4Eo3dWK+ihXzA5MUtgWlm17T32B6/0htFrM2Yje8KMPoO1Q==",-6779336902796153336,-6371681135355490652,-8856589123390607314,1474397689855586515>()
         );
      if (this.a.a == d.PIPELINE) {
         label57:
         switch ((int)b.a<"s56jn6lmtufnb","zDB44pueOekrhBKz2eoE7uM9S3yZipouYj5PZ86LMR4=",-8830098716558400709,5083021761004249301,5493543502709620663,-3511085008309609844>()) {
            case 461341208:
               var3.append(
                     (String)b.a<"s6a6oyjq67fbb","6CFSrzRliiiuh65QFJZkAlO2q9o79zQ6r2IkywzvNhMXENqhEcDcFqlznjI2b0EAlVpXIw==",5753032492081103797,-3246443381374519302,7692035867751197855,9050129432507874743>()
                  )
                  .append(i(this.a.a.toString()))
                  .append(
                     (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                  )
                  .append(
                     (String)b.a<"s1txr0lld9sip3","rQyvwO8Z2Z/fumWW3CExCNWaxtGwh7p1ZVIBdQSbrTGM/RvqBgI=",-4085768935638820997,2379857538362933776,989250341739127319,5717350313257762869>()
                  )
                  .append(i(String.valueOf(var1.size())))
                  .append(
                     (String)b.a<"s3bonfxe3zddpw","3/GroeZ1U4A/49ayEkT9T3oHUiLB3SI4NZ+pLyTfweU33leuMLFjxp22yMya/h0Dv9sMx9keOJ3Kdg==",6382504982896350252,4015930465060719662,8279826691169110760,8844884371135793328>()
                  );
               int var4 = 0;
               switch ((int)b.a<"s2kwqrquvd1y8s","5Jze2FUHn1ct9xh7ygIKgwnkmCOmfk1+n6SEOBtOfIA=",-6391113830243162649,8014106899059226940,-6024174966851784788,4260867654161304533>()) {
                  case 1300971705:
                     while (var4 < var1.size()) {
                        switch ((int)b.a<"s38hvxcfmlat2v","KzKTtXAif3wg3qOAyh1wNYM7yoSVmw8zdMRA+6adDRg=",5276932185591779593,-1589202708944304814,3265038149970003262,5292660083638652472>()) {
                           case -1012651617:
                              com.yiyiaddon.e.q.f.a var5 = (com.yiyiaddon.e.q.f.a)var1.get(var4);
                              ArrayList var6 = new ArrayList();
                              Iterator var7 = var5.bs().iterator();
                              switch ((int)b.a<"s1g7zi5de7lo2j","47H02WRncv7wenk3zQhLABb+PKR+Ep9ZmPL3lu+YANU=",2690115058183765067,7460369399771081458,-6198183605526462721,956851742259944219>()) {
                                 case 1944169957:
                                    while (var7.hasNext()) {
                                       switch ((int)b.a<"s3fojq2j9sfgt5","R8rTI0vg4bscQ4c15SrmH0x9vVl1D5MOFa1UwhmnUSo=",-525010267680948748,-4618350828976298037,3065037218300891411,3375244558671245473>()) {
                                          case -226601454:
                                             com.yiyiaddon.e.q.f.e var8 = (com.yiyiaddon.e.q.f.e)var7.next();
                                             var6.add(var8.fe());
                                             switch ((int)b.a<"s2ewbn68eirz28","hfe/w7j61lZbVR1EnIXbZSOuA3wAo8YVOaB4reJlSDY=",-6478894324257649301,-6803248966773173860,3130841728652292453,-5105113983327310122>()) {
                                                case -393488521:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var3.append(
                                          (String)b.a<"s1j5wp2p8udduw","T+d1NZGXan7wxeFuz35yhiPyERsJ5uP7Y/2kVJh0e/SwKWp/XB4kPA==",8297170056845385092,-5256010573012436600,2114569599921139908,-1491812220254044443>()
                                       )
                                       .append(String.valueOf(var4 + 1))
                                       .append(
                                          (String)b.a<"spwtla34pluhj","m+zVsgrLf0S8QKpTkcQT+P46FV2pb5BpgKcrOMDcfwUCEL4T6S/dyyahV4ROFMjl",5250116604635696335,1936929039443734467,4053267783726039676,-6504773576786939775>()
                                       )
                                       .append(i(com.yiyiaddon.e.q.c.a.a(var5.b())))
                                       .append(
                                          (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                                       )
                                       .append(
                                          (String)b.a<"s353hulckl75iv","VdGq0vEEJidN6Rk35l8y+ECf7ARO7nSmyTJZWliZ6nc1e5GAQqvDVR5jXB0=",-7581510025200557992,6601846506678268228,2552283299805622107,-5507398519083956621>()
                                       )
                                       .append(
                                          i(
                                             String.join(
                                                (String)b.a<"s2ksfh4m0wbdus","On4a2t8bNZg90WdDoUe/ByDp3eYAgmxtrjcLDjye",8813862909893514712,-4268131667101405459,-6502632506034897758,1832835079739690019>(),
                                                var6
                                             )
                                          )
                                       )
                                       .append(
                                          (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                                       )
                                       .append(
                                          (String)b.a<"skg8ywdiarpsr","HBfSf/fJHic//MMCdkB5MG0qu5vhpnzToxRFgI3EOmhVLyFwmnhtmKuR/gI=",163095180228514458,-7166066871575092548,1735178911917454224,8006397170994476993>()
                                       )
                                       .append(i(String.valueOf(var5.de())))
                                       .append(
                                          (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                                       )
                                       .append(
                                          (String)b.a<"s1zsskvzxay2a4","PaE2ZE7uU7mu9uThqvSXGIlfG8zaI7dviqzpGB0s+c9KfH0V8yvo+Ohge84=",7799080734449067777,5297843151189451969,2583875450797252835,-4747381271673359236>()
                                       )
                                       .append(
                                          i(
                                             (String)b.a<"s17rb72ui0u8z4","NrojYXDtiCZj5pzmAHnuOHKEeBanfatkpALv7LRtv+Fd0DX2ogGHSg==",4464819614876629549,-2314701983886505006,-5117865359943719575,6749172568488654236>()
                                          )
                                       )
                                       .append(
                                          (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                                       );
                                    var4++;
                                    switch ((int)b.a<"s2yeyggzsyrmjp","z6H70pMkurDFpxnTqlhSmh+ezJD5vib2N0NFPw3sW80=",7333701252800947782,5482509688551923722,-1995183903604317457,6629755904813240121>()) {
                                       case -1761753471:
                                          continue;
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

                     switch ((int)b.a<"sa85cbmz3adl2","PLF8phVEbeJ2O420gOIFas25AbsStADT1zlG4JMTOLE=",-6110236792433019644,1969528864499846374,-697671136212100816,2717909640923748124>()) {
                        case -2013965492:
                           break label57;
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
         ArrayList var9 = new ArrayList();
         Iterator var10 = this.bn().iterator();
         label72:
         switch ((int)b.a<"sjl9lhqn4iljv","VflCE4wHB2bpNsLE2QKVDxwxFns9xYjGKN81Z7nEvLk=",1238075179429518629,1354769442082422804,8117351292731827860,152419500414184549>()) {
            case -1170317595:
               while (var10.hasNext()) {
                  switch ((int)b.a<"s1q2siug2on8jm","ncFmXmuKNuTzdbDwA2GxiHDliJ/4LH+4okHf89wkXV8=",-481626479532907096,-1684766376236820423,-7784775891849183904,2450789542413471670>()) {
                     case 905172690:
                        com.yiyiaddon.e.q.f.e var11 = (com.yiyiaddon.e.q.f.e)var10.next();
                        var9.add(var11.fe());
                        switch ((int)b.a<"s39eec2rwxt8qm","fIbDV5sOfS/jP9f/B5Ly1q8jS3sjVOXIbUxzTV1Brg4=",-7606808875347505638,7513104556815875298,5942582803011974574,-6299734383888635408>()) {
                           case 2132420517:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var3.append(
                     (String)b.a<"s6a6oyjq67fbb","6CFSrzRliiiuh65QFJZkAlO2q9o79zQ6r2IkywzvNhMXENqhEcDcFqlznjI2b0EAlVpXIw==",5753032492081103797,-3246443381374519302,7692035867751197855,9050129432507874743>()
                  )
                  .append(i(this.a.a.toString()))
                  .append(
                     (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                  );
               var3.append(
                     (String)b.a<"s2zkywaox44ohr","45niBwYDELnMjW2PgAR25f/lxyOwhPf+QbLjQ7L/BJrz2GHvH8/Uyh5z5tT8RukkcASm0A==",6776441375881095534,4207005086294948282,7309533407093980488,-2212819738133102347>()
                  )
                  .append(i(this.a.a.name()))
                  .append(
                     (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                  );
               var3.append(
                     (String)b.a<"s30ox4v3he7llf","KkrvPky+EEBiiHNwBF2ektxjj7/MururEeTvtbfEv0D52BwftlB/w78Refq2laEGNgSylw==",7059281966643590600,4632492392594056450,2803308218449940876,8809182354851409703>()
                  )
                  .append(
                     i(
                        String.join(
                           (String)b.a<"s2ksfh4m0wbdus","On4a2t8bNZg90WdDoUe/ByDp3eYAgmxtrjcLDjye",8813862909893514712,-4268131667101405459,-6502632506034897758,1832835079739690019>(),
                           var9
                        )
                     )
                  )
                  .append(
                     (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                  );
               var3.append(
                     (String)b.a<"sodjq8iy3v23m","Y8SkKiceRDD+qHWY5BOR/QnZhQ4aTxlPu+KjyF9NUeLZX5iHYs5JeJFuTViAKefaxYBLSQ==",-7147400380978524423,544328257389222456,8134385387856259787,8586429267639186555>()
                  )
                  .append(i(String.valueOf(this.a.o(this.a.a.name()))))
                  .append(
                     (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                  );
               var3.append(
                     (String)b.a<"s188gb6fa5ylmn","KZh4piEGgRjwr2ZcP0J3azhaAnK98oJfon+Ey4DGLNBbCiIHDfyvGTHmfTup1LqyO+8fPw==",7913043270171275465,-5389764181243448137,8959233597536295993,3246251562655149859>()
                  )
                  .append(
                     i(
                        (String)b.a<"s17rb72ui0u8z4","NrojYXDtiCZj5pzmAHnuOHKEeBanfatkpALv7LRtv+Fd0DX2ogGHSg==",4464819614876629549,-2314701983886505006,-5117865359943719575,6749172568488654236>()
                     )
                  )
                  .append(
                     (String)b.a<"s3mt1jij1wz73k","UjaUPhBjbXGESL/uw7JANxXGf16bYm5yB4fkQ7xNa4M=",8747647725853290125,-1297346164586609925,6466961597798131021,8042126146138371333>()
                  );
               if (this.a.a == d.LOCAL) {
                  label66:
                  switch ((int)b.a<"s1zef0ppu7udts","Q9tz8sHslbhV3meJ+1pWV2VNz82gMK/9GvhUllEa1m8=",1379101575609830335,4112152192767352497,-4527335011157937397,-5469388191292865100>()) {
                     case -566077607:
                        var3.append(
                              (String)b.a<"snpj1fh3274vs","B4EdIz3/HMBp62izZC20dTdRcNi+SvtNUJwu7dw1kqkFbzlxAL+mwOfTihGa34MH24wKgLcyUNGgnan6KhM=",8333151033822880212,4851489837735271567,-2845799770490714406,-1399836805864247562>()
                           )
                           .append(i(this.a.a.name()))
                           .append(
                              (String)b.a<"s1ubtcgc2yr3so","nyFDCoKzkSiC/s5VZVCrvOi1Mh8jwibgyEeKz6e34/o2Gv/TzwJORHajL16iE81htpOEYplhIwU=",-587089901356060950,-5435870237576123644,5721378406541629089,7955266310771521791>()
                           )
                           .append(
                              (String)b.a<"szlkrcdl1dnru","hnR7oRLaM55y/qVUStBEtNtwXU8qrPs/YMsNydMYF5b6Tn408qzlQozRY0fhP5E4sS0KQ0jqRTv33FN0fYD6Ka1WIMEa+QEI+xI4wlvwIyq82XGv3n2dvElZ8W0=",-5574399157894956580,7114244021982059890,-5200858063508612970,-4511251776100863515>()
                           );
                        switch ((int)b.a<"s35005jvzht8ol","ZaCKAUddWZ4ilApskqGQwPxHjVk+7sNknOgfA2QQ8O0=",-2480904497506636989,-8960724035989745467,-744186756097381506,-1370620598335451893>()) {
                           case -752335391:
                              break label66;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.a.eW) {
                  switch ((int)b.a<"swxnpn0o8952e","p38S1LGTiFDwkhIX7Bb5K0LEiRE/Ygb5a5V/FdHvEJo=",-2957559121742706919,750755229948949295,2763759737302619576,-7918037356975139410>()) {
                     case -1821137092:
                        if (this.a.a != d.LOCAL) {
                           switch ((int)b.a<"s3dw93wmnpv3dg","f/68QjNoijG9pi0vaDHLwgdSkhi4yg2HUn/c9bCzfQc=",-9176680049447563095,-2678539827007994078,4023240212344488167,-602972427719288278>()) {
                              case -92914884:
                                 var3.append(
                                       (String)b.a<"s1gqxby77nmjwj","CN3Vot1BIKajNFyFd72DP07l1oWULY0PMneS+z2FxDdCdSsdCWfG2OPx4NtNwPjuFlA5SlTUzTHupIU1XT5e7TQxqtOFasOP",-4026621632871874649,1609250222143053246,-4143270463619026531,6615288385292091416>()
                                    )
                                    .append(i(this.a.qp + ""))
                                    .append(
                                       (String)b.a<"s3p4wpqike5j0a","JyvdA7W46eRHO1irBnvfY5uy/2INfE/6bkSaAMWjclD674nriLveQFxQnid0Mg==",7182281578237653287,-4246464034616376664,920199408068816564,7874157770352319555>()
                                    );
                                 switch ((int)b.a<"s1x9yhh767r8to","c5g2SezUA9vTxeGlVNIQ/mnir+MwBFGAAi9a6T9+bWc=",5067926186679793277,-6259377974500083430,-3582029600999548155,-3072724067844047315>()) {
                                    case 1050295655:
                                       break label72;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label72;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.K(var3.toString());
   }

   private static String i(String var0) {
      if (var0 == null) {
         switch ((int)b.a<"s242gu2m3dm47","WvmdihI85/P7j9pS5P/MDdV53c+um9xzvC1aka+kWO8=",788509629169295384,4324271502515920627,-6191576863087194184,2713956078753698431>()) {
            case -1447453725:
               String var10000 = (String)b.a<"s2iv546kcxkw9i","uNXaOKEnCvcD7m1Lucv2ipvhgON2sWY8RI0jgQ==",4037995778971957520,-382345264589530665,-6489639403989905441,8425895464078122475>();
               switch ((int)b.a<"svvmrd25asey0","chSd/QrSkKompn5beRRYGldys58k3CkJ5sL/OR7jiXc=",9020429114243509059,-7855889520997873877,-6179051787552780823,8353991219407496975>()) {
                  case -2064839723:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s1ogqu7b7qa1um","Lxkz/p/v1RXEyF//SasAzMWSGPMU5PfZFMFAYUcSc0U=",-3248576685192255946,6268052655575917262,-4765275692361117777,3098995865963994100>()) {
            case 737726855:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   private static String j(String var0) {
      if (var0 == null) {
         switch ((int)b.a<"shqershiyy0w8","u6D40NUak8U4P8GQXsdgcUhQpadZKBxfcVx4LoUTOK4=",-5507821399788167763,-2986365270192151479,-1064154294699403581,7509592100557402590>()) {
            case 590817220:
               String var10000 = (String)b.a<"s2iv546kcxkw9i","uNXaOKEnCvcD7m1Lucv2ipvhgON2sWY8RI0jgQ==",4037995778971957520,-382345264589530665,-6489639403989905441,8425895464078122475>();
               switch ((int)b.a<"si9yy2w3efdvh","fdU2sH6qjikQa845aGtcxpgewFnZFn7fm/BPGk3UdmM=",-159313569143983503,-1602315114971869666,-3484593924807458501,-1699623851980812475>()) {
                  case 392405372:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s2zm2jlif0wetm","90ab6Jqby9tAHSuJyBP/KHQXgpxFvQIQJR+UidtirbA=",-6042986044443263056,8347630191413464520,290769078393390749,-8641070912255163559>()) {
            case 40244368:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   @Override
   public List<String> f() {
      if (this.al.player != null) {
         switch ((int)b.a<"sbp3p6ktqfjwh","aGhC9YkoDp9g5S2mcbU3OgQM17I9Jxb3FGWfx8Bz29g=",5084716376258029421,-6556800647275396010,5663000646683605948,-9036306877742256887>()) {
            case -333304529:
               if (this.al.level != null) {
                  ArrayList var1 = new ArrayList();
                  VillagerProfession var2 = this.a();
                  if (var2 == null) {
                     label206:
                     switch ((int)b.a<"s8cyqct1bg82q","F+I7Ec4bBA2GoOcwTEDN6ezz8MON60EmY7pzsqz/v+c=",8023377484085633975,3526947443077404967,-4075049866683272540,2805029265841515586>()) {
                        case 1467333049:
                           var1.add(
                              (String)b.a<"s1y5guemdzd4pp","OO46842HnxNLMUpkj2MEmoqPjYp0pF0o5pPOYe1qt41CFVz3tn6GV9Vf0O1BcEvTIyPHGQ==",-4399398632960691874,-1046002572162311987,-1338483854974956299,-6566026496912135122>()
                           );
                           switch ((int)b.a<"s1qnlex12bqd65","pbyieMJlkhvtJl8VbiRG/S6N1Veh2xoexjrYJJ3GXtk=",-2287956907026917830,4282981680935834266,-3593041118236936729,-2190676555542377084>()) {
                              case -616263462:
                                 break label206;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  List var10000;
                  if (this.a.a == d.PIPELINE) {
                     label200:
                     switch ((int)b.a<"s1o70oabnfdb9k","xY5+7k/aVQ0N+gf1i6voVGmHqulFu5HiMz+P2meb6b0=",-3143756075228563432,6046292283495359536,8288729245874518588,-7123310280783658829>()) {
                        case -1301363973:
                           var10000 = this.bo();
                           switch ((int)b.a<"s3gmptp8elgnr0","sTdzLxA4qsrwU3EUs1ZRbeTE7HElZrUJD2c0ZXZhSFk=",-5118891078514647016,-7563141032265271583,7540581754599126237,1376461968499407139>()) {
                              case 769723909:
                                 break label200;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = null;
                     switch ((int)b.a<"s264pmw94lvr38","hB/EBtO07APyF0qH2DCU3+FVeW+4C2UvrCD3fmnWH44=",-2570302452247767641,-8791680242562825290,966175142934929817,-6419498862354926381>()) {
                        case 1144515120:
                           break;
                        default:
                           throw null;
                     }
                  }

                  List var3 = var10000;
                  if (this.a.a == d.PIPELINE) {
                     label196:
                     switch ((int)b.a<"srnvgrqvcicbf","TIk8QxFvwBQw8IRUoi1AqV3zVZsbENIwcwflG7bZu0o=",-510386309322439935,4632565383345872005,8185323774959063314,2350931413281976875>()) {
                        case 594278122:
                           if (var3 != null) {
                              label194:
                              switch ((int)b.a<"s2o7s2pncidot0","lYCKMOr8iZAzzlN1q9bNaGxmzzJlxEAg3TPmD9b7m5I=",-9111286109146893699,628303887938614174,5845478322089979219,8378093553854852006>()) {
                                 case 2138337107:
                                    if (!var3.isEmpty()) {
                                       break label196;
                                    }

                                    switch ((int)b.a<"s1a1e1rgymo0au","oTRiGpS2qGrCFg8ZrVrgQtqdsC8al0l1ZfTKZkFkLdU=",-4720379671025044454,-3302652196088067116,2596501502282701123,-6451726855496888545>()) {
                                       case -1421203933:
                                          break label194;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var1.add(
                              (String)b.a<"s3i9ui60u5ur6s","kcdFMPrg6CBiXcjnJ9xrHeTICQBoW4rY2NaDTGsKJJkNI3opA4wbRE3Fp9zsOn7hJeZxvUaiiWpi6rECC+MYRXF8uM995F9fGUEZXv25EldW6shTFUV+frfVc/7n9m1A",-1265604533060324053,-630421969955921788,-4090890537743597167,7787645248204342140>()
                           );
                           switch ((int)b.a<"s2mdkjh1x2gmi3","lmT9xbNJqrYVLUeGL0bHvIi4sunc2pqFAzBUj+DC5sk=",-8813556775341833715,7033596883178139443,3406488021376687970,-7354989955738771311>()) {
                              case -1335054271:
                                 break label196;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else if (this.bn().isEmpty()) {
                     label188:
                     switch ((int)b.a<"s28a1frqkeljol","q3xB/C9DNg2d9yF3jVNNFurFKMDlbBIH8SNJOHUYTuE=",-2689511291376144954,5870152205014522207,-3002774855890792583,-7378905054173301205>()) {
                        case -1723346826:
                           var1.add(
                              (String)b.a<"syryt5d5fczlc","MsV60cmArl9hYhmBL2QBtFkP/qtK4SUuVaCz/g4p8C4LVlYGooZuOr0FYFCd6AMD7tIjLg==",-4118277649639103369,3115781158331653131,-2849771505124605098,-1090096835166837673>()
                           );
                           switch ((int)b.a<"s1gb3makf36jam","estu7lDvLYD/uM1jK4NnganY3AFBstH10haFPbrpA/8=",4110660090800270894,4163910375393361427,-4729108345539012403,-8718466147715220365>()) {
                              case -1786308424:
                                 break label188;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (com.yiyiaddon.e.q.i.a.D() == null) {
                     label185:
                     switch ((int)b.a<"s1sx98oxhfuhpa","J4mjKnP6FOLKGg42yW/4JmJ/I2m4rDP5RIgzT7+5Q7k=",-2787453238992450325,954799464242560350,-7987312703337965526,-7039501384210995370>()) {
                        case -1052278:
                           var1.add(
                              (String)b.a<"s29mha9v9dhj6m","HkRv1YjUsymafsRj3mEUXAYIJ4ufBSVVsPy+CbEfQfFeRMynha8U5B39fvtQRNcIbetxKw==",-4249770157510421492,-6137493383107841387,3847220510018745571,-4739289096347260811>()
                           );
                           switch ((int)b.a<"s1fsbf7ubsnblc","T4hHinYRSAXDpvhX4eX+8wiPAa9vBdb4kkSGI6dSlCI=",133398455351157254,-1514123086274112846,3093966379382055240,4028594004082748914>()) {
                              case -1429786359:
                                 break label185;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (com.yiyiaddon.e.q.i.a.E() == null) {
                     label181:
                     switch ((int)b.a<"s3eka5hmk29e6j","c5dagWPTs39scjl4g34+dEP6pij6jQUUlvJafOuj59U=",4336675105291306940,2125654127379247426,7440287135408828866,9080636183652393126>()) {
                        case -1988474091:
                           var1.add(
                              (String)b.a<"siq78ww7s9708","IO2xlebf4qSY8niveRMEjgGQYJxqd9dcj28R0YK7R4138oi0wt9N6cBBS4ZQv2399jLBSCVr",323383821282360025,9214805200482524373,8362178283757848954,6041990915776068194>()
                           );
                           switch ((int)b.a<"s27cpwce70gu07","6+8FVItdA8HyCgQa1KWT5Xr8GbBYp1ex+2hTMiSboQo=",7006133116830348645,-5601922554359181149,4070749544707683371,-2033315698741670874>()) {
                              case -465367404:
                                 break label181;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  ArrayList var4;
                  label221: {
                     var4 = new ArrayList();
                     if (this.a.a == d.PIPELINE) {
                        switch ((int)b.a<"s31qz5n4yr5mry","gxNWuMI3A87Bcihfh1XkJzvP1CpsPbKfFr0ovxDXbKY=",-6012357263247998509,6665360520085202547,2608404258971993064,7687776866185604255>()) {
                           case 1324091310:
                              if (var3 != null) {
                                 switch ((int)b.a<"s26s5n7hynwssp","1jp5GKd/81W2Q9PEt+0+/nN4PR+HSWvoCrrSDoo5fAA=",5062472254109930780,-6567384807110244922,-3758168905736337790,-4578581541824808623>()) {
                                    case 903795860:
                                       Iterator var5 = var3.iterator();
                                       switch ((int)b.a<"s1dv849rka1xek","dsUx8/vusKDU4ypmUDxwTrTG6+LAvHyE+wBa65L/+Vc=",-4825240847897338880,-6509714794333472831,-9220529720864962617,-2019047854923913038>()) {
                                          case -964256857:
                                             while (var5.hasNext()) {
                                                switch ((int)b.a<"suubseapioik","pnP/5PoA7yhclgGv/G5ZgcMkA7MYW4rlW30z0fW8jzg=",-3311642735834776678,3050417547362758783,-1419224469608328868,-1203974190523689379>()) {
                                                   case -1419188450:
                                                      com.yiyiaddon.e.q.f.a var6 = (com.yiyiaddon.e.q.f.a)var5.next();
                                                      if (!var4.contains(var6.b())) {
                                                         label170:
                                                         switch ((int)b.a<"s17qe25drcwqcq","xaMQLX8JosM71EdYz8ZqNGAs83mla+k6M/5Zw8ynmKE=",4911121379765574840,-3396472146137633618,7553809010310266626,9091402677239979307>()) {
                                                            case -437143908:
                                                               var4.add(var6.b());
                                                               switch ((int)b.a<"s22uf38do91w9h","o/vgwBczM/g6I6oYikepJWYTyrfjErCwkeOGVMGNtpg=",5428748538223790621,2499898141210266801,-2379115560707175864,6705475869821033508>()) {
                                                                  case 1300423241:
                                                                     break label170;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      switch ((int)b.a<"s2qvgut1973tqn","1w9wah45s+ByUtP5FKPWeBtznCYnqkDC3BlqiGERncs=",6214762363703682968,-4139077902868604147,4927681892638140350,7105372736472503164>()) {
                                                         case 1359257409:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)b.a<"skg2kh9ltuvue","MhK/Aysa3NPx1gg5A82w9CIIrukaEHw16UGNJloLq30=",3115421321466582954,3709527376093035859,5149704891827579764,-7171287294138631292>()) {
                                                case -329237368:
                                                   break label221;
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
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (var2 != null) {
                        label162:
                        switch ((int)b.a<"ss51ffk5o0eu4","HB2hr2sCf6fCMpMYwL3r8hR0IOif06J9EU2cQjaelac=",3486741466321632395,1038821959461182382,-7487715024778906563,689699406466307842>()) {
                           case 83834743:
                              var4.add(var2);
                              switch ((int)b.a<"si2rxcepn6cbs","m/AfDEC84s7vpCTRB/i9QTZJ2DbrSbi09acDHMLz5pg=",8840395933614681410,-4658989722920477424,-6572588985660999390,5984413676303639927>()) {
                                 case 1550262758:
                                    break label162;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                  }

                  double var11;
                  if (this.a.a == d.LOCAL) {
                     label155:
                     switch ((int)b.a<"s3cqaeyaez9bnp","nHjpmnymy1AColjuTvv/udGk8qYhiW3VsGbV7dYfDpo=",2026064353802022237,-2503414840042585472,-4478777995290158213,7299572273253559505>()) {
                        case 858562106:
                           var11 = 3.2;
                           switch ((int)b.a<"s3csxvsung5y2b","M/4NwFY0GEx+rW2XRZYqQSkAA66F7jcMRejIcPkv0WU=",-4536501261467356175,7606778209236694428,-1968338938541877507,8737097994708821131>()) {
                              case 1841188495:
                                 break label155;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var11 = this.a.qo;
                     switch ((int)b.a<"s23b43s50sym04","mlYdDjCvkEo9YF1obsN+AjJP+Fqjh5lW1QYfOpE7OfI=",1967930142386100073,-6255454005997155247,-4202140907824398379,-2839303021511629913>()) {
                        case -827745877:
                           break;
                        default:
                           throw null;
                     }
                  }

                  double var10 = var11;
                  Iterator var7 = var4.iterator();
                  switch ((int)b.a<"s2lngdayeos0ko","ZMBk8YAyAlQVXKXba9yKMRqdvo6OC1QVgfeYOmteyP8=",8115639506583738400,-2768060135670512408,6367341924088531179,6670591749810419263>()) {
                     case -188399737:
                        while (var7.hasNext()) {
                           switch ((int)b.a<"s3ou6evjf6kxiq","DEL6bd5p6YmHKBQuzQ1aOb3+/Rfqe3xLHALhQ7wKGxg=",6816721569003931297,983526580212886760,-590360907614617939,-9077361300606985849>()) {
                              case -1519433817:
                                 VillagerProfession var8 = (VillagerProfession)var7.next();
                                 boolean var12;
                                 if (!this.al
                                    .level
                                    .getEntitiesOfClass(Villager.class, this.al.player.getBoundingBox().inflate(var10), var1x -> a(var1x, var8))
                                    .isEmpty()) {
                                    label136:
                                    switch ((int)b.a<"s2920boz0elujf","A8QoHw0Z7mwFDmeEx8ETiq+HzHsK+oZ8lXHW81Nzv+g=",1504888846309923210,9191842190268517602,5377398221775835901,-3735956190499207070>()) {
                                       case 1730787914:
                                          var12 = true;
                                          switch ((int)b.a<"s2bdiy9x3nd88","IqKqoMGz+aIWze/bkkX5uO3bNquJE+8v4lPZmnMXPmI=",3970176944507678493,3077517617965998188,-4243316471775917296,-4629909075389424390>()) {
                                             case 317426493:
                                                break label136;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var12 = false;
                                    switch ((int)b.a<"s3bzzds1dvvzj6","RzpgOv5tIZ6RDVGzJ2wfSc4J+cxQ15zAKsv4seshRTU=",5658800438800248102,3144827739579859377,6022807825370292461,-2943461739202030254>()) {
                                       case -465725958:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 boolean var9 = var12;
                                 if (!var9) {
                                    label126:
                                    switch ((int)b.a<"szetn3hfzyhmg","ynPKWLGYyByZTVLSWkBpZufu7+qsgDapgb2FVV7VTkg=",4185725190652964329,1759267169769785088,1968694067988915797,5995406074819925126>()) {
                                       case -1899917012:
                                          String var10001 = (String)b.a<"s229hqgta1t4ao","viH5VeG5F69OIRstBlCbng8HHSP0K84uNwC8ohwuXPTkmij+8IFZGp6bnBAq/kd2wtgmk0/ERIMIN+3LINpjcxuESaXPhw==",8951725144819778261,3074187959521218259,-7816468301240032679,1238449204674618673>();
                                          Object[] var10002 = new Object[]{com.yiyiaddon.e.q.c.a.a(var8), var10, null};
                                          String var10005;
                                          if (this.a.a == d.LOCAL) {
                                             label132:
                                             switch ((int)b.a<"s1g48ez5e7l655","HzNRQEIa3qNNXdl/lPDdda0wOlZgX5V5AZa9/0prIxM=",7834148378097992286,5478899326623909407,5665221117515360030,-6246953764058530408>()) {
                                                case 1315257504:
                                                   var10005 = (String)b.a<"s25oxg4cc6ly96","BMVHpfqwEtlqabECTgXHhaYiLJTyb9BGf6Ml5bwNpdjni5Y9E7rXDTJLIGTfpEHpphVx0+ZdzfwwzEMOg9jrYKKrRZhzoKqMz7I=",4515694706151249426,5828655992952545169,8371145909837891504,-2132224830910333416>();
                                                   switch ((int)b.a<"s3ny33n8t8qxr6","zzm8RGQQfOPcW1gZMOuatuqt17CtJNAWY1ga0r9aKs0=",8019427705913677427,-2695085561616531157,856842263166585555,4208516701228594280>()) {
                                                      case -60839350:
                                                         break label132;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var10005 = (String)b.a<"s2iv546kcxkw9i","uNXaOKEnCvcD7m1Lucv2ipvhgON2sWY8RI0jgQ==",4037995778971957520,-382345264589530665,-6489639403989905441,8425895464078122475>();
                                             switch ((int)b.a<"sx5o50kwo4rzu","QbpdBdLG57Fu21oxw/GhXC8x8fyiBv1iGRImN0MFW/k=",-5506374718290838469,-8445934893719530779,2551707556724778774,2753601698773720280>()) {
                                                case 906640213:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var10002[2] = var10005;
                                          var1.add(String.format(var10001, var10002));
                                          switch ((int)b.a<"s35jx58yhhoy86","QTnaj4rUVnDt1nfQS7y92QOrPvkGHb9K9Vinex/IGbs=",6647841014614423999,3327484824522615774,8755507104294436468,-8769901244023011886>()) {
                                             case -89936198:
                                                break label126;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"s1czn3wvv1k3mf","2gSM10zOqA+DKmXcUvQC8U1T/WZNB5nQODtuF2alIlk=",1802546240027499855,-3237031747322351709,-797759683767367932,-3607604986587058541>()) {
                                    case 856191827:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (!com.yiyiaddon.i.c.a.ft()) {
                           switch ((int)b.a<"s2daz3hlr8ciaw","dzRBGtwIhtgqJRxY5FleX6/IvmdmTiC4rvesFRFHWUE=",-2237945317421878058,-1035777638659652024,-5825077474172119107,-537195669225575174>()) {
                              case -1988978097:
                                 var1.add(
                                    (String)b.a<"s39eftzzk3hkcg","KdjGWcppGPcCOq9hK2bOtO+nJERTaTATyZzobHfPUTAVszAKN9QGU80mlAB3HpbVyGV5JJZKiqYyGqD0f3JqhLYOZgs=",2691984670216982909,-7022376789359164510,2385287764181094553,7230601718477422798>()
                                 );
                                 switch ((int)b.a<"s38vsglltvb8ii","JGltihRzQ3gb9KwQxBHaYz3WTwxby8tQcDMTRE0dA8U=",-5144058736952791230,-1356579494141681299,-7042177929575013778,8963438534825717128>()) {
                                    case -226341314:
                                       return var1;
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
               } else {
                  switch ((int)b.a<"s1q50w8icvip4k","RVbXJr+ox8cOkcqRkiHHEcqhYI3xj6erfHGlJ1PYnnM=",8074755848734131330,-4002582312020897463,-1128554811013924383,-38482681366117566>()) {
                     case 156159088:
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

   private static boolean a(Villager var0, VillagerProfession var1) {
      if (var0 != null && var0.isAlive() && var1 != null) {
         try {
            Identifier var2 = BuiltInRegistries.VILLAGER_PROFESSION.getKey(var1);
            return var2 != null && var0.getVillagerData().profession().is(var2);
         } catch (Exception var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   private VillagerProfession a() {
      com.yiyiaddon.e.q.f.c var1 = this.a.a;
      return com.yiyiaddon.e.q.c.a.a(var1.name());
   }

   private List<com.yiyiaddon.e.q.f.e> bn() {
      return this.a(this.a.a);
   }

   private List<com.yiyiaddon.e.q.f.e> a(com.yiyiaddon.e.q.f.c var1) {
      ArrayList var2 = new ArrayList();
      VillagerProfession var3 = com.yiyiaddon.e.q.c.a.a(var1.name());
      if (var3 == null) {
         switch ((int)b.a<"s1lqq3j2loxs9f","Hts9S45/iQgh9u9ZgRK/PlMir1n9TRPCF50o1mUfm0A=",2447614620715102901,3844981127782951792,7095080170920142923,-4982600600019942702>()) {
            case -530306763:
               return var2;
            default:
               throw null;
         }
      } else {
         String var4 = var1.name();
         boolean var5 = var4.equals(
            (String)b.a<"s1z3col69ulmqm","HVNttG/vxSXKnW1W2zvpvbYL/u0ZtpUG9DhtSHunZ2DJ1t75H5U=",-5237548930458254739,3110953568635277440,-7455409861231178673,-8309990934365067449>()
         );
         Iterator var6 = this.a.i(var4).iterator();
         switch ((int)b.a<"s36ae4pr3xu88l","QSVtnDBlhjtQf6cyeAYaCbNYixBOJ+akxdZVJLVwcOk=",-1918572116822168629,-5869633507477847501,-1529116314973507998,5011715501289897482>()) {
            case -2008140486:
               while (var6.hasNext()) {
                  switch ((int)b.a<"s1o1klm3capat1","eCKh8TJ5Ro3mWn3i8qJCVRzr+0KAbApAoriV5vULDe0=",1070871321411543045,-4820017549270736108,-109450895252477152,4019295222700116353>()) {
                     case -566957426:
                        String var7 = (String)var6.next();
                        Item var8 = b(var7);
                        if (var8 == null) {
                           switch ((int)b.a<"swuryfplu05n2","e2zbizpEPdEEfeKswiMQCaDV5CB+ys0LRqcXYUhqmUg=",2217516066792355732,-2667632610834782045,1149436890571197398,-8613273867196281424>()) {
                              case -391556166:
                                 switch ((int)b.a<"szdtua8k7apko","Bauonnarlts5VSQlogi2fca9OgMS2MIqf1uSyprR6IQ=",4725550243501944169,-6566136261338190366,-3079241909715998891,5631818673639438043>()) {
                                    case 295742386:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           String var9 = var8.getDefaultInstance().getHoverName().getString();
                           var2.add(new com.yiyiaddon.e.q.f.e(var8, var9));
                           switch ((int)b.a<"s3uaw3apjns3i3","MAnSaZH0DY7LagNFvZeRSZy1Q0ybRqxA7pWOBDu7A5Y=",2052766484923364943,-6205995131303500839,-3122075113739632303,7408615331663908637>()) {
                              case 771705910:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               }

               if (var5) {
                  label72:
                  switch ((int)b.a<"s3onhcgluiihbb","Zwe3WIALjxu7KKiUUAFxYJwQLjfTyixD8UD1W/MY8gc=",-8418299838320817491,3239682360660623565,5525590808593776117,1075731522257863226>()) {
                     case 1940323986:
                        Registry var10000;
                        if (this.al.level != null) {
                           label67:
                           switch ((int)b.a<"sc0pe7nik4n4f","RKYFYtwZ4xuFkzHF/aPZtdf+pm3QOo9APeSpnFRxkYg=",7097882326991119189,295774585697592699,-1807766261516332167,-3824558136973502744>()) {
                              case -354068523:
                                 var10000 = this.al.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                                 switch ((int)b.a<"s1l4temfk3plfa","LOkaPhq1Z/dfgZlGvOdsZ++DBQ3i8ymg4mgT2L26nQw=",4559438475109122927,-7290791906169660801,-2258651361469080130,-7815584443112790308>()) {
                                    case 530810379:
                                       break label67;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = null;
                           switch ((int)b.a<"sajxcm4mqa8uo","qiaDnTc5qeBgwml+evRrJ5H94eAVP/WyKLtISU6ofbQ=",-460216473726099383,-7389938160256346707,-1215927627845497499,4352658749398176938>()) {
                              case 1747743477:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        Registry var13 = var10000;
                        Iterator var14 = this.a.bp().iterator();
                        switch ((int)b.a<"szl42mx5f62bz","xW/peWGmDVyebD/hb9BnFlMAM4+Q0E62TGH03SI9sPg=",3355971207349312362,-4915187887537686240,2101359628604722057,9100645681841400850>()) {
                           case -248776499:
                              while (var14.hasNext()) {
                                 switch ((int)b.a<"s2y5qnj1hu7tix","8p0dLhFE+QYYvKHKBtgJQwc7uUrYJozywGhIQuFOOF8=",-450617364790279253,-342143726257128952,-7806846038755511093,1758303637861234367>()) {
                                    case -1492014123:
                                       String var15 = (String)var14.next();
                                       Identifier var16 = Identifier.tryParse(var15);
                                       if (var16 == null) {
                                          switch ((int)b.a<"s1e2r0o5bfv8lb","uarQuGzQKVCsmaYQ/QmOCNrH4BynA6lslX0NhS2KjTM=",-2205481370704502218,8586109880434678940,-3235460973154409686,-7964248903312773473>()) {
                                             case 823209546:
                                                switch ((int)b.a<"s3idhetrfy8fzm","BDSBNSbhopFEu5e4Z7/z7GlmLfPhWo8qtUoWSiAyBow=",-8681105264073575743,7771862386323907742,5597080774078335517,-5312333185499092409>()) {
                                                   case -1639581686:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else if (!com.yiyiaddon.e.q.c.a.y().contains(var16.getPath())) {
                                          switch ((int)b.a<"s2h3hmjcjkilck","cKmG4bSL38pB060c3fqzC+80QsE33Z+DFxEiE5v+6yg=",-2143344910580378147,-1205607884119603131,588234924322896610,-6485342330769712125>()) {
                                             case -2046065341:
                                                switch ((int)b.a<"s2rwna9wh6uk7i","Z4c2TH/pMnCYkXerQ+Fw41XGV94LfM4sU66Qo8+DDkM=",-5733532448244588334,6764648832430235029,2942323766744509179,-3563271447979134315>()) {
                                                   case 2132106223:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          ResourceKey var10 = ResourceKey.create(Registries.ENCHANTMENT, var16);
                                          String var17;
                                          if (var13 != null) {
                                             label86:
                                             switch ((int)b.a<"syja31084m9u1","vwDjots/L5RDn0QzyGp2j5YOiIKkssrPtAkuMKbE6E0=",-1855988832149515003,4602051741013226199,-314905269244860087,7422247648702304642>()) {
                                                case -2119978419:
                                                   var17 = var13.get(var10)
                                                      .map(var0 -> ((Enchantment)var0.value()).description().getString())
                                                      .orElse(var16.getPath());
                                                   switch ((int)b.a<"s2qcqr7a7qong8","WLDGCDzvWuyDTrf23BiaVX71w6YmPOi50F4GjXVb6sU=",-469657046592579875,486362492292635485,-4707941803374017998,3802786937103230586>()) {
                                                      case -764936102:
                                                         break label86;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var17 = var16.getPath();
                                             switch ((int)b.a<"s2mt0sit1xw2nc","Z3eNY7bHCJEfg1tsJRUsQbnPEylzHPHhWRH4AoqpD28=",5589918123125062022,-6924041550776740224,2852240015154457463,-6850017471787427836>()) {
                                                case -535224073:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          String var11 = var17;
                                          com.yiyiaddon.e.q.f.e var12 = new com.yiyiaddon.e.q.f.e(Items.ENCHANTED_BOOK, var11 + "");
                                          var12.aX(var15);
                                          var2.add(var12);
                                          switch ((int)b.a<"s239edizlzwp83","EUxIXwN0jVW6OsOd4Q/I/XR3AFpJbTUbyzuNZC/fOSg=",-1724302450509411912,-3828523593626364485,-1582306625933505394,-7272028787275104707>()) {
                                             case -2097958578:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                              break label72;
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
   }

   private List<com.yiyiaddon.e.q.f.a> bo() {
      ArrayList var1 = new ArrayList();
      com.yiyiaddon.e.q.f.c[] var2 = com.yiyiaddon.e.q.f.c.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)b.a<"s384mlpcoo0k9f","8c/PEN48LXgNhZNR8haiy6IkSNduzc8492HSEj9xrsI=",8950128746471226433,-2440470587146109033,-7497879614350487596,-440909422703107443>()) {
         case -396678970:
            while (var4 < var3) {
               switch ((int)b.a<"s2e4gazdevqbvl","Pr1mPYmJWm4NIbMsoV41d5yGOkzVV1PmlTam8UWoils=",-6389540520903416406,4718627645571447602,3414200714359234112,-3261353246442563804>()) {
                  case -970808244:
                     com.yiyiaddon.e.q.f.c var5 = var2[var4];
                     String var6 = var5.name();
                     if (!this.a.ay(var6)) {
                        label45:
                        switch ((int)b.a<"s21my9u9c2a7o1","do2Jn0cCgM9rjSmdBtBp7/6iu2iaFaxt9h2WRiQp8/o=",7830933492524107766,6181276253972111533,-4984206261594538991,-1037312045903196043>()) {
                           case 1582656997:
                              switch ((int)b.a<"s2t8pumew8aje","MtL7ThMmvgPhPp4RpvJoc4/3+q6BgX5PBY4ekNiaBZU=",3450622560901023120,-8563823017059413403,7288330188087648226,-6186145268062563080>()) {
                                 case 1299995124:
                                    break label45;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        List var7 = this.a(var5);
                        if (var7.isEmpty()) {
                           label41:
                           switch ((int)b.a<"s372edm4tvdjnn","4/T7tg4p1hgbJxadHPcBj4eGu4I++YlXkEehr6f5oi0=",7370002088408391684,5830814147406534169,6802835432436649082,-288058087876734857>()) {
                              case 1631556821:
                                 switch ((int)b.a<"s8si46sdkp2iq","Y/22iJNU5ouePHZpqJvb6r3gTkwCYwGlbZDkIQlLmIc=",7386572371527637,-6618152487884833201,-8786085396762904625,-3539594652535976375>()) {
                                    case -291061332:
                                       break label41;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           VillagerProfession var8 = com.yiyiaddon.e.q.c.a.a(var6);
                           if (var8 == null) {
                              label37:
                              switch ((int)b.a<"s259nhrk0zum9b","0JAusLpdS0ECnaQOJ72wTkhH/6QIBHHKWlbw9BL6u70=",2938805461284179511,-9202186214985392525,2570120144746445643,2770097970695222502>()) {
                                 case -433836794:
                                    switch ((int)b.a<"slk0xtxhza5k7","8igl714DvX6Z76JoA3NKaBTptzwJtIUNxrUFLm74/k4=",3292586720395047299,-1395848535165685231,5475787191517141283,2374656191472880670>()) {
                                       case -785691069:
                                          break label37;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              int var9 = this.a.o(var6);
                              byte var10 = 64;
                              var1.add(new com.yiyiaddon.e.q.f.a(var8, var7, var9, var10));
                              switch ((int)b.a<"s33eutisdnzll1","2skG19YOHf6SeJ6kckz/PlBqW1YNahvP3VvxVprg0b8=",-1623135763016318314,8918714633438743376,-9015715737974597607,5617102971429731499>()) {
                                 case 1015000184:
                                    break;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     }

                     var4++;
                     switch ((int)b.a<"s79y3q3xfq00o","sowHf5n12Rmways/3fclN40mhm4S11L/ATPw7lIpidA=",753064824386835491,703787973119447952,1581604928430005438,-503221940793875693>()) {
                        case -1477885985:
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

   private static Item b(String var0) {
      if (var0 != null) {
         switch ((int)b.a<"s2qz8eqhj9u4l7","dcVNBkPYS+9Z4zuQe2k8lhZKEZ5IYSU5OP7NDVoXNSQ=",-2124683131830234695,-3832920080585823732,727402013309787020,-4287789700919605903>()) {
            case -320215029:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)b.a<"s5ufv0b4rp54m","B/FCCnK/iPYIW6h0mK2tmfTp0z7RgXa05roZaqJoC3o=",-844893638697921251,3753133946297653132,-3200762887885492908,-7185439614753121181>()) {
                        case -118926302:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Item var2 = BuiltInRegistries.ITEM.getValue(var1);
                     if (var2 != null) {
                        label34:
                        switch ((int)b.a<"s1v7xqgwd3pqg1","k2vtJDkneixrubbdTSh5OiSQVY9CkXrTavM2pBQpktc=",1211579215261864579,-7311373633931698873,1066741031914259021,-7803434870543292589>()) {
                           case -1562535040:
                              if (var2 != Items.AIR) {
                                 switch ((int)b.a<"s2d60y8p3b7n2","e6hUwRJbG5jnEW+NS3vrTQ6ijwnra0z5zfTE8XVXXlk=",-3789103566711138104,9145616702537983889,6548452194725521936,1954401559326796267>()) {
                                    case 1956566841:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)b.a<"s2uzah8kqyq2ht","y1CQ4nYZJoUR9AXCt9JXtHiOPbEhf/UFfVSTI4sdYpQ=",3867395545436011024,1620045237700407057,518972626509857585,2315606292079657210>()) {
                                 case -349530151:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s12v3u88qtihrh","uWxlLIDdZbP3S79aug+7LhYNNTwR1MWaoJCJFxp4TE4=",7296348620188646916,-4246729912864514572,7766146154510856107,-8515106188048733964>()) {
                        case 1034691732:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)b.a<"s1ocw6sia6igvt","UdKfEg4O+RL+EOtjHNKAxA0zZE4Ji1uzrA3AR0gtfJc=",-7691134943782383999,5067737854980795118,-8563987922984196376,-2463775612767829064>()) {
                     case 1748560944:
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
}
