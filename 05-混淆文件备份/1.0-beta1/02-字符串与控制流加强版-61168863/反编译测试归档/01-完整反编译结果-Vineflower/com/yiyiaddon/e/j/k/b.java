package com.yiyiaddon.e.j.k;

import com.yiyiaddon.e.j.k.a.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class b extends f implements com.yiyiaddon.l.c.b {
   private static final String nj = (String)com.yiyiaddon.m.b.a<"s3l01w3bbtn7lg","WSjl9bh11e0xMt+vOUUIhez2SuOmfN3iLZWbhCN5ugZtDyh2yzBfZJGxtP0P+qM1rT76YboId6maaIUQu48oSDdP4mZufA==",-9156021104457122015,3879777624509435035,-43375338942344652,6519236800003813602>();
   private static final int jW = 20;
   private static final float bm = 6.0F;
   private static final float bn = 11.0F;
   private static final float bo = 10.0F;
   private static final float bp = 12.0F;
   private static final float bq = 6.0F;
   private static final float br = 6.0F;
   private static final float bs = 320.0F;
   private final com.yiyiaddon.e.j.a k;
   private final com.yiyiaddon.e.j.k.b.a a = new com.yiyiaddon.e.j.k.b.a();
   private com.yiyiaddon.e.j.k.b.d a = com.yiyiaddon.e.j.k.b.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.j.k.b.b a = com.yiyiaddon.e.j.k.b.b.a();
   private final Set<String> F = new HashSet<>(
      Set.of(
         (String)com.yiyiaddon.m.b.a<"s1cpxb8ujnfoqm","FmLmMbRfJiHN9GDb1RCF0kD1tvAPNGp1SX/Ear4Y4HxZJ4GJksFAyWSs754Ej1fiNyze+/BGIRFnpT1M",-6075542890798811412,-2368538851519039098,-5300820388585274379,-3733718920410269071>(),
         (String)com.yiyiaddon.m.b.a<"smvsqf8m72fr5","L58dOsaiJ1MJDIWu3zBIYGtAb94ISdTNez5KRTmQ3sFctZZuUGmKYmz27cf3UN6yiPhA5/v4rHhP2g==",3587992697900423004,2294242201077490335,-6054316113575078207,4277223644553049986>()
      )
   );

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sry8si5f54fsc","aRTFMi7L9WUCF26gfAdKyzqDw3uxgpI+n7tOt+1icTs=",224066429464345706,-1067870096299826215,-2419446761555057365,-262666609525549023>()) {
            case 1496351865:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2q3mdcvwdp7wo","n4mdK6OlYxNlgMJcrqV+QAUpmHWyZZjBxVwvnZbmytg=",-5210255475392062377,5175226368716815858,-6491638342921220868,5744985762943719664>()) {
                     case -862560239:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"suht9c8a63hy1","EbhRptW2zoL4yUfja9+uHJqje49GEZatdGTL3SETmB4=",2475016528561770827,7850862506612208533,302521741199344992,599171935191318325>()) {
                           case -802502082:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1v36e9xde0286","Hw5W+2m4YKKfMWgjQ8hDTBYJBhAqD4anoyuXtIDI+lk=",3147049026712537633,7829355397175811259,947312600513450799,8527703787321728289>()) {
         case 1249877821:
            return 16777215;
         default:
            throw null;
      }
   }

   public b(Screen var1, com.yiyiaddon.e.j.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s1lofiukg3ty8k","whi1ucWI1qILnsE1eltey9k0H8SSxwWTF9/U7S8iT+ivu8vErUZA5ABP",-1905373013713354485,-8637865773492255582,3278478416058744146,-2196187210770478210>(),
         var1
      );
      this.k = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s3l01w3bbtn7lg","WSjl9bh11e0xMt+vOUUIhez2SuOmfN3iLZWbhCN5ugZtDyh2yzBfZJGxtP0P+qM1rT76YboId6maaIUQu48oSDdP4mZufA==",-9156021104457122015,3879777624509435035,-43375338942344652,6519236800003813602>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33jf7dkn4s7gv","1ivfU9Z3j7BxUoFW5Q0rtWLxQUJXvB7SxUbk5LktKCM=",4060752311606185326,-2571055524082322780,5126393297411358710,-621742948997616835>()) {
            case 663732470:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2pcos97e8c1n","YBd/tV8+M/6HgK90zH0J0re2Xxg0PyzUnip39eBld3Q=",2740217774315419224,4173612782237219238,-147527065810357325,-7287278900762472652>()) {
                  case -933477574:
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
         (String)com.yiyiaddon.m.b.a<"s3l01w3bbtn7lg","WSjl9bh11e0xMt+vOUUIhez2SuOmfN3iLZWbhCN5ugZtDyh2yzBfZJGxtP0P+qM1rT76YboId6maaIUQu48oSDdP4mZufA==",-9156021104457122015,3879777624509435035,-43375338942344652,6519236800003813602>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s3isu682749qr4","frkU9Ywm6gJwuUeJXKvAm2TpKErkw467/68pObAs28U=",2562116738602845216,5365699989852442548,5031992033290294154,-5787467838433546701>()) {
            case 314497900:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1o7cb12brv7wj","D8uOYpQ0InE/rTds6sipmt6xC94B884Cpam7uensc40=",4147226228023184449,1183150802573438819,-3656521345120890028,5796229546719186953>()) {
               case 1162980297:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3qtqsb5xphmpj","P9qNKGsTVNJ65Nq/KKSivGhdOEYgH3ZrPU3EMulEkFo=",-6662058823976454750,7692290948479027656,-3491246237492860177,-4751275053438781038>()) {
                           case 206127274:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.j.k.b.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2kddb6u3ibvrc","UDn17gDELfMMAo3fVK14WU89s3tOCSj7zvU64yb0HMU=",461005022052262563,-2471150446664904281,4545955361497012384,-1908842094747198845>()) {
                                       case -1453983188:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.j.k.b.b.a(this.k);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2behveeiy27ld","8SEz4x+ro+0UOZFluSC1K/2OtQWJS/oz3rGJaHjA7LM=",8051251911955322158,2174788554728860276,5349014499145802627,-1839171296477702046>()) {
                                 case -20550544:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s16wl6np1e44x5","D8dmXvYSus1fOXYaKw9LQvHjgxW3Bt59/HmxTZdWmQo=",-1730399604253227919,2121962794047830657,6510981159560283274,6909201482035455174>()) {
                        case -178656888:
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
         switch ((int)com.yiyiaddon.m.b.a<"s38481664xhof8","O+jdBDKGapkUsnbxEhgjlaLttOWrnKD3S0xZz4x22mw=",1448116430818612287,6625869332055350440,-7840832091006656496,-1099224903436812521>()) {
            case -1266193313:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2wizhuxj96vc9","pOOBJ1zkSxKue1rsiUASpu7dJYk4SRTtOKgZ3NvKyEg=",4614818871758721681,8883337412139224103,-7965092825497332795,-1212254103196962424>()) {
                        case -836906019:
                           switch ((int)com.yiyiaddon.m.b.a<"sf3xbn1ven6ah","JbnaQ0hCKXwH8pxOBLrIYBumtEprjfvz95LM1dzqHmo=",2979725931814203876,6030493489926110204,3514897195791007978,-6803884125940183159>()) {
                              case 1940635765:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s5jni44bcuzx8","GxvDOx5cHBbn2phj5nbmXPWZ63ncfRkoVAhgvnSjFVE=",7974785463500243905,-6326811693323097131,8877765548957776574,7688672059596153095>()) {
                        case 1631628813:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3nhpwi3tptkd","5f2TENDASJVDTArAwP7BTP6uSqKc5m5kB1V28HkefeU=",-1931975431062054386,5125157594012107739,-3713990111854211347,7244087706045994100>()) {
                     case -402175316:
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
      m.lb();
      this.a = com.yiyiaddon.e.j.k.b.b.a(this.k);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.j.k.b.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lz3bw6xfqr3y","F006QaseaBpVbfDG+A/kIJGIQSef/Y/Xi9wru7ePqL0=",-5062877114688127241,-5841957765485269118,-4419426580005869420,6735894739283713901>()) {
            case 111410193:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2uqyc13u4rjt1","ojUej2XGohUlvCn5+G1nSLU8s0Pxqfk629Yo4VJxY5o=",-5165910931450618289,3102034819794752602,-7075578475614064909,-111060074387718598>()) {
                  case 1413796873:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s17o1yzei0n1wo","kjb0azVEZ0zAGpDu1MdKq8E0a2qBYKTPAQzYOTG2ERA=",2624862221648219175,19762756148773423,1758949634576858418,-7499193793845911203>()) {
            case -507901335:
               return;
            default:
               throw null;
         }
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   public Set<String> k() {
      return this.F;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.k));
      var1.a(new com.yiyiaddon.e.j.k.b.c());
      this.b(var1);
      label31:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.j.k.a.c(this, this.k).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3d13lis099sy7","ZGZRwb+UDA/I2j8e9T67PkeNjCq9MUVJOSNCY+SoePc=",6359118034430659649,8785061531549004973,9019203155394884658,-4716766404263216930>()) {
               case 578881543:
                  break label31;
               default:
                  throw null;
            }
         case POINTS:
            new com.yiyiaddon.e.j.k.a.d(this, this.k).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3t84dgaufkikn","X6yaXhpYm3kKrYbNcJsvTbDfiAbLtOmE2O6rVFsEAGA=",1936314796328411698,-3149086384913761911,2032003783342530535,4635268489177191296>()) {
               case 1528311946:
                  break label31;
               default:
                  throw null;
            }
         case TARGET:
            new com.yiyiaddon.e.j.k.a.e(this, this.k).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"seifrml6tgri8","ZZV9X9DD+UHsFG/g9a9loh/D+wE2NevSKY84xsx5T44=",-6876461424816502244,4982823784470267425,6016445948336149229,-1643175085903935068>()) {
               case -1304284742:
                  break label31;
               default:
                  throw null;
            }
         case TELEPORT:
            new com.yiyiaddon.e.j.k.a.f(this, this.k).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3sxyblza3doaf","nTKTzRpcS22sD7rGizZ29lsRqZpePId6dzOzSIjbR6Y=",7221236678073793985,-6444738629468182046,8855642661795803330,6426584289728207094>()) {
               case -984720868:
                  break label31;
               default:
                  throw null;
            }
         case THRESHOLD:
            new g(this, this.k).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2166ntt23b2fx","o3Nm7V37JvS+DGiBVYePDuNG7KNXwDwCcKV/GKZpFqQ=",6752691567042707018,2833096056259241496,3601626889905922164,7535785325464992723>()) {
               case -1512371846:
                  break label31;
               default:
                  throw null;
            }
         case BARITONE:
            new com.yiyiaddon.e.j.k.a.a(this, this.k).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s30i9fjfvldzs5","ceOKb0WJCWUcApntMnMiOiHqd9yOtPbXm9tBF1V2CoY=",-5839928359019237785,-4477265741779183312,8855456888303311026,5862072117384147309>()) {
               case -98626399:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.j.k.b.d var6 : com.yiyiaddon.e.j.k.b.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"sj0m0lc17vwui","xRMnvnjdxFt44CxxDYe/S5KB8vwEnaTus/H7gN/3KJE=",-2879891187475615793,-6466795514060139142,-2304102636936877215,-4127917223508218635>()) {
                        case -62210089:
                           switch ((int)com.yiyiaddon.m.b.a<"sxfepmqa4j3f","xGfw7Sr2vKAT49Bm66zzHwnuHiUvCQRxagNUuzStqaE=",8485715661528538607,3865860481065623601,-400101754889352821,-725918178918124913>()) {
                              case 1012295040:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s2953orps7d7w5","VVqYguVApx1APhD0prT5DHbeKcva8XmSmdSLFRBzSqM=",-5233238675176164791,974497628829343621,-5866123065620954781,5012533244102484657>()) {
                        case 1128494857:
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
                     (String)com.yiyiaddon.m.b.a<"slyquma9v9qur","A8L9qcTk9tkfRmW04dghnaQzUOV7E5685OPBJWny/+r0pIe/",-1651892528916627019,3114394605694375464,8503969932145552336,6180286035274202749>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s34a5aa17lawc9","t7V/6PbzuQcVvoLfqD0z7WYAWIL5GEjEyFt9b3VByvQoycAHGhmOxciHcgjuAAACe74d4cx05arUjA+bM36yAI8zTypT0BbnlOthoUQBkun7pzGSkZ4PzcULw3PzHrtOdH6JEw==",-1396701859871148667,-5232907985418194393,-6954920823228423345,-5420845186752377715>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.k, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s2u9ngg3h510wh","MqO5LOtupg9pLfy91FsS+nQMSPZLFEA07jOncG8oAyG8NFgs",4826445495493643402,-4488106594757790672,-7038029380894313954,-2648172801909632939>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sxf2r64r16sr8","s+tlJfTB4JlKy02WRg4G6PEriE+Ne02eaa4kkO0bB/4=",-4666559943813842272,-4166464463518468207,5889433557428880916,-7183791613882126822>()) {
                              case -695575830:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s6kjdz9q2esmh","Hm8TSN95frcfWQw+0gzyTBmAyMh3BHMMs+r9MTgF+aI=",-7331275766141430712,2716332879200590753,5242461271462363743,6057228193048554801>()) {
                                    case 1243442541:
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
         (String)com.yiyiaddon.m.b.a<"s3r9wfs1nccgke","u4bZidBnrwZzsKEtd82ni6+1ZPS896ZxDF4CwGA5",6074929454895659757,3413746379072780898,5280957911006788231,432106235051335983>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3lr6gv76np9y4","uvnnavMdc3BCXWijCRX4w2ag5mWahamIcXhkd0O4Zhs=",4188294752500479366,-867091312413030949,5912289943380987143,-7766958455634718030>()) {
         case -852881754:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"shj1os4ngs7vf","AssMoi7eXDiuEnEDjTqMigYU/zJ4pi/thm4jmrbRzxQ=",-8492408353288266378,-6400875821712930158,-3573124153483876207,2209761298010279948>()) {
                  case 1203061111:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s2jbcamghgmgp3","6bh6waQjPhVGsXsnAMMQ+odhS8LDW0zs00sWT7jU/vw=",-3735882417817338256,3270030778174577457,6199976609380552434,1884771609631829893>()) {
                           case 291708311:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s7tucvvy9ir8j","IGvh9KBJxIhA40H6dhLAIlxW2gyguN8o3h0ZOg==",-3570937629681968435,-1332390454465946430,-6990788018125214047,7023272054073571336>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s1pwi4iisu60oz","f+rcTEiVWAs60kf182p/tJnnJkOdWmA2ou8RKu3uRoQ=",8499564286132423816,-2870335609780483850,2699500614590844862,-3739639679248916803>()) {
                                 case 1629734678:
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
                        switch ((int)com.yiyiaddon.m.b.a<"s1mvk0eu3hjpof","IHXjRk50cEkK+a3rlqAaXxUCf8cOrCHjg3+DvIX9XTY=",-5941936655712706041,2625708933736000613,9212801542326407809,-7413206517608142533>()) {
                           case -527865924:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"soj62hanm4hdt","Xr240r6682I0RSMwgySaSR591i7ZSxU2Lz3oiKBa6a8=",6287445145962117834,-2160002853584434382,4358274242798362209,-3273663848005345685>()) {
                                    case 1738452270:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s302i1jizunl8n","YWlXoyD/5l3zQXu+REnau66enetfeykfyifpO689D/o=",2515835565854745987,-6583153306139840818,4863845625561056442,6131480617233729732>()) {
                                             case 399855904:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2gy71sj7ender","dFxZWc5tIOWOT69S+3ROJQPsiiW+scH9W25HAHiX59Q=",-8028329170706914111,-6592747926118765898,700568603442536639,-4442423422521551330>()) {
                                                      case -1914622754:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1afx23pgxz3wa","sEJl5fBaVljDtOr8BUdSM/sliRV4AexiSuNJCgE5ZzQ=",-767662234024377738,-7895600421978025806,4229469412477235096,-5767047283282965715>()) {
                                                            case 1606938130:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"ssq8yzhjwahir","HNUmRBCHDVjROntiy6eeFeOKJ/MJ8fMkm5nkYiyZZ5k=",-2863016977135249504,-3077459189409881535,7315920832884752017,7216989973409924104>()) {
                                             case -387005426:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"sekyrb350h5ol","xmc8pBepP3m2xOzeSQTvtMVnUHAJS1k3guWi6id3+4M=",-8407791481805935994,-2246262878484455109,-5243884542827231917,-5168701989332442995>()) {
                                                      case 350396218:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"skovcwqtuhs2","UivlZF6G4rBJkm1XrZ15a+KZK3w5vUH250KLhEPv+fA=",-4196135278296151283,-7840283744400897169,-3613803907569107019,2398650538631599555>()) {
                                                            case -1815897068:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"s2yosk4hz8zdow","FNzWqUWdArIAEYNcgfUZ1CHFLHDWQKsNHrlcKz0D/2A=",3628202268344917939,-5447285947052910623,3732223943553643108,1335660320769827904>()) {
                                          case 841676421:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s15ycwio6esehx","c2BsRVvws5ndZ8+t0pvOe1BO6BNhfFaFMFDNOLXakeo=",-9156605074209878159,3324522827812600557,1918575518917792376,8841485110399633801>()) {
                                 case -1390549065:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"swf2zgwicduiy","JvvmA3AGwbh2T2JfTWyKlxnIHwP8LKO7zZpmw85Cbac=",5282390014414136594,7112561687618876140,-7196529218412559922,7961401675011957580>()) {
                        case 680787002:
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

   private final class a implements com.yiyiaddon.l.b.g {
      private i a = new i(6.0F);
      private String aW;
      private float m;
      private float n;

      private void u() {
         this.aW = null;
         i var1 = new i(6.0F);
         b.this.a(var1);
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
            switch ((int)com.yiyiaddon.m.b.a<"s1qz3utl784t39","MeEvvtbcerh2hiBLJr/qvnqOBMSbHhOhZLE+94Og0p4=",145828835869517547,-6094980357505149306,1120494296938789869,-6897955784203608088>()) {
               case -127794413:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ry8aoth4q6lj","vZ+kwVHaC2XOBdVN/2hy6GRun0etP0uLvmbc1mlgqYo=",-7456340888107555495,3421167490219192545,4949736069396355002,-905804923092715033>()) {
                        case 1023517369:
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
            switch ((int)com.yiyiaddon.m.b.a<"s2zf8oai4icf4l","8DMnsgSW4XYtrQSwdoDoTnSwVXArr759+Cut/7Qfpes=",7310641557595125523,-5317164614395898024,-5544379684824579126,2445820396702747048>()) {
               case -735747902:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.j.k.b.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"svr682fmx3la6","xC3zDl4EVFoQ98ZrqbrHI1Zxf9z7VhV0hvmktjGvJcs=",935700691332351999,8660616285974971544,1007280753039902267,-1543660762426105115>()) {
                        case -937048843:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1wf5trxrpfwoj","O+32z4Nh8/K+XM9exu5CZd38dzGMyVHT204i0k5rpwU=",547725025824768845,6121608469515676206,-399489242896028935,-5123325179372742719>()) {
                                 case 1928974549:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s1kn0199j5oqlx","aBef4GYIJ7COlRBanWp2zvvpSM7hxd6bk6f+OOPxKIg=",7746781101354587040,-1040563301335086150,2402489806448579933,4597739522003562040>()) {
                                       case 2011271560:
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
                           int var13 = com.yiyiaddon.e.j.k.b.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3ieto88xgymbe","RrP0F/LJWn7V8SKefSdi9uawnrcOHpZck8gw0VJ0hu4=",-3863777852783209642,3725949604349350904,2794323345514114006,-3923124021900306804>()) {
                              case -1310843820:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2qatk2r0yt9u8","j42/zDtvVj0hh7h6nHb9c3J0ee8y4j8KFg/5yg9WZc8=",8674006330948548875,-3346945342228389668,6833481139150094869,-2932994898726181019>()) {
                                       case -1704950074:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1qmubg59a99p4","il5qt6txks7W54ZlFl3D5LiawkB1TC172TwPGC4bXGk=",2987554006200943371,-975257787003524676,-7503059886533794519,-5162762321389885800>()) {
                                             case -598517837:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s32r7kaqvsnkex","DUyhIKmCrDD+GhSbpWxHxTBmrfn9TGI+iWxSGfBwrD4=",-4589964946894578578,3016413067241207069,1972966567748405632,4402035768913178888>()) {
                        case -1539166746:
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
      private final String[] J;

      private b(String[] var1) {
         this.J = var1;
      }

      private static com.yiyiaddon.e.j.k.b.b a() {
         return new com.yiyiaddon.e.j.k.b.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>(),
               (String)com.yiyiaddon.m.b.a<"s3clikwspq8ffa","S/j5b6pYUCPijEiOZfQ1Jg7A3ajrP6tamsoOcA==",6400381392702254126,5248447197844135607,-8167748386479968332,7413771251464986950>()
            }
         );
      }

      private static com.yiyiaddon.e.j.k.b.b a(com.yiyiaddon.e.j.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.j.b.a var1 = var0.a();
         List var2 = var0.f();
         return new com.yiyiaddon.e.j.k.b.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s39tyzxnm5gulj","fuxwO7fOSLnk0i6IfoG7rre9uGx5ffv2S0QptHrdFey571WK6RU=",2299155581504209595,1836722252626892937,8872129765120770975,-5024035774039536063>()
                        : (String)com.yiyiaddon.m.b.a<"s34toc6hwtks8q","mcdk8DPTMqGTNxClicJaj6usPB8Y69D1zc7+8HxZ0s1feJPCuZM=",-4131451983590068748,1842718445695105188,-8724717851397253204,5968551437957188574>()
                  )
                  + "",
               var0.a().a().af() + "",
               com.yiyiaddon.i.g.c.bU(com.yiyiaddon.i.g.c.bU()) + "",
               a(var1) + "",
               a(var0, com.yiyiaddon.e.j.e.d.MINERAL),
               a(var0, com.yiyiaddon.e.j.e.d.FOOD),
               a(var0, com.yiyiaddon.e.j.e.d.AFK),
               var2.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"s3vd9ze0km8cl","j2o8Y7/QRSHW1rHXHf4Dr6JMEYaVpuHuboJVHg4mgzwwnDWryMBXyw==",2847426137639138401,6964149277814097879,-2687545473612287864,-1055548791500212396>()
                  : var2.size() + ""
            }
         );
      }

      private static String a(com.yiyiaddon.e.j.a var0, com.yiyiaddon.e.j.e.d var1) {
         String var10000;
         if (var0.a().a(var1)) {
            label15:
            switch ((int)com.yiyiaddon.m.b.a<"s59w4b4aqxbfr","ith6v7gNOH/3cupNR6eiiriz6awvHvwW6CUgrIGdJHI=",-2009261615941819244,-4214777013946765559,-3018710952096259166,-2664463046650446653>()) {
               case 1368734053:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s280zss4ftna2r","11fdeykeZzbZ4HXBH9MlHlRgH2GGR/vS0EoL74MsD5yHtA==",-5654117339641607312,-5755593507144108764,5392907015676808883,5722545720711621773>();
                  switch ((int)com.yiyiaddon.m.b.a<"s286fvvdfj5pvu","fUdX27L1+RQ+huGzmDzmGrhKhhbkp487VcveTbjevfI=",7650501264862832628,-921446052066554814,2485489327507501450,7403700670084011785>()) {
                     case 168257008:
                        break label15;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = (String)com.yiyiaddon.m.b.a<"s2zi5rt9g4qkdj","YxLvoy/7SvHwgTrXLjbCSgcsraadzHPpJvqbP1YiM+RN9Q==",-8351223345656382902,4358665063511214965,8757023642073655136,-815092122340305846>();
            switch ((int)com.yiyiaddon.m.b.a<"s1dv31cd1ef800","qs3egF238z3/qqZeOqj/gtyWSqGmqt8H4BRwMWp+Zeg=",8645282511906151431,-218041975470229077,-3251562116825783804,4226916731778853245>()) {
               case 960533542:
                  break;
               default:
                  throw null;
            }
         }

         String var2 = var10000;
         return var1.m() + var2;
      }

      private static String a(com.yiyiaddon.e.j.b.a var0) {
         if (!var0.mF.isBlank()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2au5jtvltlr1h","GVsr79m84NlMDUbxkQDeE9Hk7KfxxIpVP5YmtFCco28=",5321673539138590391,-5149972419909620653,8955332301770294334,1763308442299822270>()) {
               case 1282729016:
                  return com.yiyiaddon.e.j.k.a.e.aq(var0.mF);
               default:
                  throw null;
            }
         } else if (!var0.mG.isBlank()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3204k44hzo1vs","pWlEz6271O6yYnKIeA9GqUMbyunb0KwfObk5pUmXc8s=",-4082818732659804616,6601295704278054033,2357183006139135994,1829147105154277522>()) {
               case -317006531:
                  return com.yiyiaddon.e.j.k.a.e.aq(var0.mG);
               default:
                  throw null;
            }
         } else if (!var0.mH.isBlank()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2cho4stz3z82x","wXTlglbGpzDqZ2frTVOa0T/FfXRcVx0czW/7EqtRRvM=",-2658693649380825599,-4580282638591914750,-2175946920278280915,5819060521492043289>()) {
               case 850524722:
                  return com.yiyiaddon.e.j.k.a.e.ar(var0.mH);
               default:
                  throw null;
            }
         } else {
            return (String)com.yiyiaddon.m.b.a<"s3ff1su18rsxq1","IFa0/hcHh6F1QwU2PIT77mPewZ2Rto1mLKt5bIigvbtbYCgwcB8=",-3325045527364471840,6569325229237770619,24732064515028855,-8884941932173466901>();
         }
      }

      private String c(int var1) {
         return this.J[var1];
      }
   }

   private final class c implements com.yiyiaddon.l.b.g {
      private static final float bt = 18.0F;
      private static final float bu = 6.0F;
      private static final int jX = 4;
      private static final float bv = 8.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.j.k.b.b var8 = b.this.a;
         float var9 = Math.max(0.0F, var4 - 12.0F);
         float var10 = Math.max(0.0F, var9 / 4.0F - 8.0F);
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s26x5kr945pfxp","8XDMW4a7OPWuOcH2b/uPWg47HgZnBQbYu/16DGa6zlg=",79031728496825492,-5901584576252012987,2655525392727560073,-6503171087278174536>()) {
            case -902729927:
               while (var11 < 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2jqts7vznjrxh","ulFf/k0krAH8QWWWDSP3x4yCxlcaqUjV2WhgqO2H9IE=",-5616418497416536721,-2645852326190257313,8115743380281184390,9027239864914482733>()) {
                     case -2095994898:
                        int var12 = var11 / 4;
                        int var13 = var11 % 4;
                        float var14 = var2 + 6.0F + var9 * var13 / 4.0F;
                        this.a(var1, var8.c(var11), var14, var3 + 18.0F * var12, var10, var5);
                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"skdnhzuk3namc","N0JqD1Ch1x07n5irZM7fMqcUblnvcxf4iUDi9C2EKRQ=",1522376798374007864,-5919276988534929925,3207811906339716185,-5587757693600748809>()) {
                           case 1794774406:
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
         (String)com.yiyiaddon.m.b.a<"s2ttb4jxstezoy","UxRtaIppG8x2uAvdY4/ZXuLTUSDyoWYfvAeY9Z0h9JA=",7331885285891708214,-6595854543288099407,1157884876075151942,2506418115991949295>()
      ),
      POINTS(
         (String)com.yiyiaddon.m.b.a<"s3jyrz5od2niaq","ChldQsXxsL0HOse0RufAZdWe0CbNYIu0lY7QblBD1J0=",-154233923310378170,-1865296485490246093,2427492967388056596,-7672304988899992507>()
      ),
      TARGET(
         (String)com.yiyiaddon.m.b.a<"sk3mnh55y5acm","hZ1n7u2yVBS/5ZphZ8kIU70FxwqlFgtVAEd2HXIVFW9wuH7u",-4880059036424329643,2903413320731234039,3724245028837748588,5775572344621536212>()
      ),
      TELEPORT(
         (String)com.yiyiaddon.m.b.a<"sletdl4f0m3jl","D3k4iwt4Gp8GA7z8wLSqA0M/t2DudgJpk8LnL8wmtlXABaS/",2949915082895361123,4058072742805026570,2949368407168968129,-2919553723818231938>()
      ),
      THRESHOLD(
         (String)com.yiyiaddon.m.b.a<"s1mpreqlfrnpon","N4z1MFwZ6hZsZcJiIF6omzaTugRRs6pCyYQUtJjovbNCC2Fj",806636356483270914,4401753738632098972,3178147713504650694,6082304829115852946>()
      ),
      BARITONE(
         (String)com.yiyiaddon.m.b.a<"s2tw3egbrikxoz","Nx5jtU1264aubOnxbcs1gYH5eCen90ZRQa093h7WLTjIBWXvvzb/1Ymr/c6tiY/l",3609252845967528280,-5904894088254299051,-5188321668907294606,7803688223562705993>()
      );

      private final String nk;

      d(String var3) {
         this.nk = var3;
      }

      private String D() {
         return this.nk;
      }
   }
}
