package com.yiyiaddon.e.p.i;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
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

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String yT = (String)com.yiyiaddon.m.b.a<"sgiuvh3brtyo7","ZKBCibo73x3P4oyjZeU4EcPY6SYoxUP4Fjav4HGYGXrPmdXKOA4cQIm8kSOkJf+zyemrAJqS9Y3MzaP3gsfEnNc1RqOpTftRlG8=",4682535223103083604,8038890018535313785,8406364555556212015,-5954579732192419243>();
   private static final int qd = 20;
   private static final float dT = 6.0F;
   private static final float dU = 11.0F;
   private static final float dV = 10.0F;
   private static final float dW = 12.0F;
   private static final float dX = 6.0F;
   private static final float dY = 6.0F;
   private static final float dZ = 320.0F;
   private final com.yiyiaddon.e.p.a b;
   private final Set<String> aq = new HashSet<>();
   private final com.yiyiaddon.e.p.i.a.a a = new com.yiyiaddon.e.p.i.a.a();
   private com.yiyiaddon.e.p.i.a.d a = com.yiyiaddon.e.p.i.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.p.i.a.b a = com.yiyiaddon.e.p.i.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2r24l6yj2laia","Ttw41cK9jCTydKfuTXdueYV8kqzeWPXoaLHgK09dWdg=",-1808440893850129687,-7024133307893817973,-1687149000005873138,-5459639329069115720>()) {
            case 1370185869:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3twfjgt2vweix","M9YnIW/mnk4xGyhLQpf/x2SwobHj5PQ7VCg17j5fKHQ=",561775589948056122,9201394048591778534,3672146677330265780,1633316055940227151>()) {
                     case 1198826715:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s30wb1l95pdjeo","CWDeltKExWSFWv37lFrvBflxMj8DTd7xlBY6MFvMmAA=",1296731110124140427,-272993179559002510,-4074643530996645832,-8050957246221649876>()) {
                           case -85616942:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1y73m6lgbivus","LcknzKeIOF0RAa0p1u0Na9dAQ/K/+zAINQUR2DUm4e8=",4560348193169510822,-6922492969631944840,4540309912928900906,569470587826544434>()) {
         case 510818470:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.p.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s1pdkocdzn102y","Qae7nr9pvQAJS2KQ5IC6M3tSem+yLprwP7qH8g6vIdWbo6oF2AY=",3827952711561411474,4717855561802427247,5810041410949483563,4698313832962393533>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.aq;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"sgiuvh3brtyo7","ZKBCibo73x3P4oyjZeU4EcPY6SYoxUP4Fjav4HGYGXrPmdXKOA4cQIm8kSOkJf+zyemrAJqS9Y3MzaP3gsfEnNc1RqOpTftRlG8=",4682535223103083604,8038890018535313785,8406364555556212015,-5954579732192419243>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"syq9t15wo8mp5","IgLio0NuhnBzwpLVZm42gydNOGWlJUY6OY4OMRjM8KI=",-8742061067174320983,6433400195512202874,981231238148788018,-9093282737225290810>()) {
            case -427377563:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1oh0qc838165u","QIoX9Lg+F9d95amAULiqjrOguW+qOQmgAIpoqF0i4wY=",8777104654174691626,5208869173582413659,4512934875026281481,-6643779607015490183>()) {
                  case -1388557531:
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
         (String)com.yiyiaddon.m.b.a<"sgiuvh3brtyo7","ZKBCibo73x3P4oyjZeU4EcPY6SYoxUP4Fjav4HGYGXrPmdXKOA4cQIm8kSOkJf+zyemrAJqS9Y3MzaP3gsfEnNc1RqOpTftRlG8=",4682535223103083604,8038890018535313785,8406364555556212015,-5954579732192419243>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s26qwq163u5bbk","EAXI8Hpkw8ACvgXMarXaPd6BeQzh3yElTGsz+28yITQ=",9066170736655079929,-2970927906079921781,2239229951890733758,-7468761640877867045>()) {
            case 1141117440:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2hr2esx0ptby7","wDqyYfF1JGtOYLBMufZJ4GXplktoPDQ/+TYKBB3jymQ=",5680619111861302637,-5899397779203978650,1308460212274871916,8091524796090594559>()) {
               case 1675437518:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1lvtn6ivao23","erQBlumgiuJzOLaY1dNww1qoLFN86y9iknfdzT1N8TQ=",375725348833015522,-1299948305949985870,-5491050959039573377,-6032682995958674417>()) {
                           case 1901802239:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.p.i.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s30ku2ovm3antw","9BLYK9KVryDP5PeF+rbBweUxhwTOOGZGc8BNhqpMGMA=",4632638091895127069,1806481817638820018,3787074959404269586,5431156358425294078>()) {
                                       case 1589520115:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.p.i.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1saqrmncnhxy7","+dI5z9L/h0Dp+sxcHNigaQ+s+DiLDCEhrAjUlcE9fsk=",-5675198760756704993,-8815670291881309826,-4218931653812429950,3117908641358222959>()) {
                                 case 108389660:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s334c9mmfjwufp","VviPLYsj8GTOzTGL7c5iRWf2IBXJd5E0k89JsQZ6Sng=",-1898870125450055683,-3330384778788783821,-6783599349706450985,-7842995559222175732>()) {
                        case -914335779:
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
         switch ((int)com.yiyiaddon.m.b.a<"s1a6l9sasprb2x","wGAxVcxrRZxypFyUEaFr/D0u90fPk90iK5tPfFDMj0A=",-715299031520503079,7523915814477324421,1724756758355290254,1623277103884155463>()) {
            case 300653393:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s16ybxnq1jdr69","9rQW0DfD3cwTlvKr3jAPYq7BlCX3Ikm88FXzHh76IoU=",-6790817949908247004,1773802877805340018,6595505360528984214,1639801561469287645>()) {
                        case 641174504:
                           switch ((int)com.yiyiaddon.m.b.a<"s1w08e3zhmlzi","ma/ln8uhaUTQ1wRti17yfsBp0+sBfPN95hvObAJxfbE=",3588212272087460626,395502344595834906,6650669804704758056,-7306590248017793135>()) {
                              case -1789974002:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s17u4dvq40295s","qjqE8VwGrKT0u4uMTiqaHSPHvPQbrX3frUJdW8EZFGY=",7857166426683427534,-504634498063449230,285761294594441726,-5802010620497950755>()) {
                        case -329677859:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2byx78qda8sio","jIBkkzzQpTnedQHlSkjnOrfHnl8q6s2fHoH4tnEU99A=",2859861915289820297,-4602053917934067195,-6582760538893163676,7816612804647299659>()) {
                     case -1484099640:
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
      com.yiyiaddon.l.j.f.kY();
      this.a = com.yiyiaddon.e.p.i.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.p.i.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s81l5xlddzq8l","JpvDVp33/RsSJ3auq3ICFeDdVfEITP7CNZhp6/YlSjg=",1608605439837196523,5357965930753271959,-760546375365663169,6214604964923550284>()) {
            case -703801970:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s3n41lulziuxuf","QhKp0jRm3zApTm5ZuZiDrkB89FATzS8M/i+RKmxs+0A=",8838610672214176492,-78573543662399045,8127601170230749941,-8116557698419917711>()) {
                  case -1280319514:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s521upiqabx6y","yi76qinBQ/JwoooTOAVsbNO20cvv0n3I8JkXcP3J+50=",2913237102761281842,4003914006463748654,-1199130674051033778,2918694629799235230>()) {
            case -398120007:
               return;
            default:
               throw null;
         }
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.p.i.a.c());
      this.b(var1);
      label31:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.p.i.a.b(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3pk5ubq6ux0fa","LFCLlQt5kwnIZAdjKE/L3RSq02Wxf4vBgLfEu1xpe8I=",5590833839468207354,9088903195233350485,-8288795466530801466,-3327386647113752705>()) {
               case -908693195:
                  break label31;
               default:
                  throw null;
            }
         case KEYS:
            new com.yiyiaddon.e.p.i.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3rt3s68dmzu9b","bga9p20oY/cnDkndnaFdoYjgSwOPldBvhR2zFRSD35E=",4690279299640045984,-1241940462929676068,1561253073213334339,3131104688777412025>()) {
               case -145981263:
                  break label31;
               default:
                  throw null;
            }
         case GROUND:
            new com.yiyiaddon.e.p.i.a.c(this, this.b).F(var1);
            switch ((int)com.yiyiaddon.m.b.a<"skkmbpnb2h3p3","xFGFveOpwibMecx1F/VeLMpjtDc7tdq9PFR8aek8Duc=",5359957061634910541,5616844368077266332,-8093457459931442123,-5158074601604045760>()) {
               case -627810850:
                  break label31;
               default:
                  throw null;
            }
         case WALL:
            new com.yiyiaddon.e.p.i.a.c(this, this.b).G(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2k3r9je5twj1g","narvUrSQcfcgsGoIkvhgguyjQcP1+fyEJXimCIve5yI=",-6254687433352350142,-3075959685757179272,-3821065399704838748,7841192018949102472>()) {
               case 1145811475:
                  break label31;
               default:
                  throw null;
            }
         case COORD:
            new com.yiyiaddon.e.p.i.a.c(this, this.b).H(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s31z8kf5hie8r9","QS7QzGod8SLkvsEu7f4eCoqxEbWzGDlm6Sq2gksdl2w=",3765885950293777700,7547411106472680503,7495972354980976923,-1857125798684263298>()) {
               case 1599797529:
                  break label31;
               default:
                  throw null;
            }
         case VERIFY:
            new com.yiyiaddon.e.p.i.a.c(this, this.b).I(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s5k47gxpb6r3o","cQOEYr3cYCw3ufkE8w9lKAY40EuXQgC9xxr6mN4Cdi8=",-2808102650213345760,-6425437925887321039,3537445733423266493,-7417470083757443658>()) {
               case 1300031015:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.p.i.a.d var6 : com.yiyiaddon.e.p.i.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s15jnfy6g7hoe2","O0+5uhBaKvNIsDFJCyo6iRaT7q3rMXj2XPdFFXNlGYM=",3798620411986800063,-329638597998107991,-558073637719323591,5265912911492150313>()) {
                        case -1848652615:
                           switch ((int)com.yiyiaddon.m.b.a<"s2ml3vm2zxzw39","r5yUbDjJQvd+BWvuTUQBs9mbWxZzr1eWCfWErtJvPNA=",6457796370672143534,-859162981241730448,-7066580917284779305,-3599601077363078375>()) {
                              case 889041283:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"spmuf9mqbt1qd","JMFqMWhuqAwjBSbj36IikEdRNli+riGHX8w0qX7r1Rg=",-4561277524977776415,-8533391566972371068,-6614525966598992163,2107488206865073038>()) {
                        case 725924818:
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
                     (String)com.yiyiaddon.m.b.a<"slsdvgiifn3q1","dBSh7UpNNBCdAYAb67ALuE+6vkbVO5c45iVaVVz80TVATSAH",-2159952312732456979,-4463357484865638014,-5094753737279709425,-3637842698536037373>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s1sl0rkw2c8mz9","GqoaF5T8sd/CPKBi2UDTahAmo62ix4VBzplJPNhCacdn/fqtmuFRnWJDcO6XycBkpMHd7/ZAm1rx/NSzMEsyBNSDEcJMOja8Pi3qzw55Pzrdi7MovXnIQrcloHf6vgadqeUghA==",3210639955068139417,8315695625324418239,-4374880664662592793,2944202950537015365>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s2khb4njy1vmf5","p+O6eFpl1UJIh6KIlsfUnOM4GBCvuYUS6YhZv01xEI9vL82A",561674889186661252,-2455470986249766932,7625951634332002578,-3282957140423244338>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2n07edg7wktxp","o1UFGnKMWPVaO9NRUUN/3k2FduaQjKX2V++EHwbwHp8=",2477299008911197573,-2623723058276077549,-4954148959391611021,6136780465339178940>()) {
                              case -1896171030:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"sey57qsmewfbc","D/Fzg5PCbxW/8sFjTzGVY/uQOS4to1H4Gbl8vjZ4Qcg=",1125420542408536191,7369748703321177120,-4102981334542764309,-2952302834477550171>()) {
                                    case -1722855121:
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
         (String)com.yiyiaddon.m.b.a<"s3qz9ie4updr5","jG6AtE8uCnpkOny4UhMfPuqq3soAnlQ18FdA1RQC",4242806087908128155,-7915695381722675855,-4388759160694064104,-541452202994736109>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1e3yaryiekxxk","uGcKGBHCRIPBfpSFed2gkRvpFO3RsZMKw6ApN61Qj8A=",-4253318661391846817,8333718784631223006,6002110200940138049,-1925245693231089220>()) {
         case -632639709:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s2shh65jakg3dq","VykbWlaF+M0E8NG/g3Np9JdsQVlVGaZCMSsmLFuCWAI=",-6929199418910151832,-3333542768064663339,640744581856462028,553712539732115484>()) {
                  case -1247966986:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s2w5ravec546m2","xUWzjiuoXX/FocEgiDkz+Bab9dSElCQVBWREMm0ZmBk=",7731576419362679559,-8981585523702180558,3966521326932442022,7903208290140741560>()) {
                           case -1272494513:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s30vsqix4gzl78","iicfC3WYnhpXAEDVxPmfVuC1FHTZ4Q23a3CxiA==",1673560723128740849,-1274893177638757285,2957935193742734142,8195459541363859510>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s185tdndc27plk","B13Pr95dl4dIknnygJaEFnNgD1FHx6+mK5COX35ptXA=",6251296077693904907,2627038059222289168,-1717928507245015876,-4477652238823026747>()) {
                                 case 853326977:
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
                        switch ((int)com.yiyiaddon.m.b.a<"sx9xqnvsflgr3","b7BBrdHn2wZbWx5bzdfUY8GMaW8uLEnv0OR34V04++A=",1080811452477628819,7892596937591711457,5489607920170635925,-5662035058157574018>()) {
                           case 2103025275:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s32jzdr2l4fwet","+GPBBQWS5PQgBcMG+GuGEKmyLY06twLKIk3LXmyMfyo=",-3540423975771195503,8669022452569547356,5379138993133828745,7762350469284033100>()) {
                                    case -2015035394:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2jmflcudomhi3","SxhCVCpbBrPqEUvLifnVh4OTZIGCNzLxIx/GkdBQwuY=",1638764578960195867,2540105790656122181,-5283831182787621075,622702096673980495>()) {
                                             case 1770644566:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3uzwmt1xatnpk","Mv1oInBolaaUc6n6nfaydMabOHJ4wp6hcy5J5Q00pWk=",-5990169572429976454,3641579958595145824,-7495084505427400278,-2119001447094873081>()) {
                                                      case -1545996585:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"smy2btm0qqnty","1DDrn419QU+xJtD69W/i9lCD1ceRTAqGWKdy2qDRbXs=",7346870424027940707,-4407864719709913589,5085576783442159350,-4509718639023485931>()) {
                                                            case -411964063:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s169vta0p62d54","9APVQxshOznFC1fl+caF0pSNjkmuOwkh9rk/eW4fHx0=",-6835201687637666758,827702108010452807,-8747910241278060475,2173377509063586435>()) {
                                             case 1857679696:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2bbojoytmwi1w","/9QySLQczdFysOAV70Y0ONJc8uG0Sil6seyKkpVNr8w=",2907094345694269648,5691449306812561429,-8684455106348911574,7388597946864869271>()) {
                                                      case 83140224:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"sutxa2q38d4vq","SOVD7D7YvfcIC9g66F1MkFxqIsqUfdFa3nuFwJl+6zM=",8141482493555007145,1122468231073152865,-7809214905987132193,4038223684591256474>()) {
                                                            case 449997365:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"s2xqgwstfbhhqc","d5T+82HmJfq5l5LiZQ7L6ML0T0Hzo7iEh6Li3VFIW5I=",-1583750194543808644,96366852298554860,5185063547824921996,-5702099736796172574>()) {
                                          case 1423026869:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2dy5onlasqsow","htWR9uzVHdalYStDl+Z1R3wIjFzomp7tQqy3U/MNl1o=",4440092510336443893,8609477761502413856,8210364608130477725,-3490101353088213297>()) {
                                 case 949860937:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3mbi4c65yj03b","boWsx2RoLk5VMYO2LtoZu6jLC15CgXQpL3wOZC8DF5g=",-2821757439581796580,2998031847631324410,-5329828100367103820,9122459761705699088>()) {
                        case 1240145585:
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
            switch ((int)com.yiyiaddon.m.b.a<"s27qb7pdwlcsow","oJmluqLtr5O7LVs135nEiEjclA4doRnXLaKu9YXjvU8=",-504532559456085593,2442393207858171150,-2213969347144347255,-7317389602076787558>()) {
               case 985917586:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s15vqne2iqk3eq","N9EFxBfxK4M7ZUviGubWyCRh4jWNDtpDdlTjWunEF6g=",-591511172277685738,4231001786064144861,5349028757777165231,107913915538258344>()) {
                        case -151415724:
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
            switch ((int)com.yiyiaddon.m.b.a<"srquls1s5wy2h","CZ2ght/jwWP/vWJKk09R2S1Mvf4K6yR7MouFkk4IxHE=",3237570738160216634,-8284373178399079531,-7574275661790821404,7241990922949707967>()) {
               case 1189037213:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.p.i.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s2j6qkirjlzcoe","uSBDxAUVsy80R+Y4p0JJIUdyBYks6J8UuA1Ml7CH3Sk=",-2497799586122688759,4337928430910011919,-5457481287140008322,233589377017847386>()) {
                        case 1773319812:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1qrom03672vm5","LVBLMYfm5y83ovtSXdeYyRsKXNCKiDU421PbrUXxcnk=",-7787483242033283827,-2278439827547568941,-5100276802131490165,3473954908516501665>()) {
                                 case 1719645680:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s38sne39yg2epv","Cp5msXbIFwRwUWbDR+NNQtvNMZmAEowQ8RcsVDACZMY=",3671292224166160648,-8519325915621195428,-805682179884064913,2130025262517588175>()) {
                                       case 1007490767:
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
                           int var13 = com.yiyiaddon.e.p.i.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s10eo5af0th5xv","NVuCT9K/RPIgO3DMUku70xMoB5uqbcgvNckNxt+OkVU=",3136826740273189165,3063902221250854550,-4490444873524037001,-5505702681920310506>()) {
                              case 1441724654:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s352704ixnci3p","XLTZm/Es1iLNDEBL769pwcPLwwGvUUQY/m8B1QVIVds=",2744001875003748742,2032346086266394811,-4682021970031857445,-6728659257155113968>()) {
                                       case 1077501832:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"spomtsu1xnr39","212LZEAblfGd1Mg/FauJJh4DNUOYopWf/qsyNf6YuY0=",-7174731094469391835,-9203889921732478307,1714012060224820977,-8394917671460956281>()) {
                                             case 532988713:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s3mz9bk4f2d936","JgklbfuaCrBXtFwhSJuIZKP1cQkVJoHpfvuR0XJbBv8=",-6808036232808188714,4625064503810316190,-6031130745841460850,964977743599851698>()) {
                        case 2039322189:
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
      private final String[] ad;

      private b(String[] var1) {
         this.ad = var1;
      }

      private static com.yiyiaddon.e.p.i.a.b a() {
         return new com.yiyiaddon.e.p.i.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s2th3d0dioat5s","EDyiKhgsBVw6TSEwP4ISVseATAkgxpwmcU4x8A==",-8056284607832473074,-3194106975021661190,-3822693003588244337,2395300917108129301>(),
               (String)com.yiyiaddon.m.b.a<"s2th3d0dioat5s","EDyiKhgsBVw6TSEwP4ISVseATAkgxpwmcU4x8A==",-8056284607832473074,-3194106975021661190,-3822693003588244337,2395300917108129301>(),
               (String)com.yiyiaddon.m.b.a<"s2th3d0dioat5s","EDyiKhgsBVw6TSEwP4ISVseATAkgxpwmcU4x8A==",-8056284607832473074,-3194106975021661190,-3822693003588244337,2395300917108129301>(),
               (String)com.yiyiaddon.m.b.a<"s2th3d0dioat5s","EDyiKhgsBVw6TSEwP4ISVseATAkgxpwmcU4x8A==",-8056284607832473074,-3194106975021661190,-3822693003588244337,2395300917108129301>(),
               (String)com.yiyiaddon.m.b.a<"s2th3d0dioat5s","EDyiKhgsBVw6TSEwP4ISVseATAkgxpwmcU4x8A==",-8056284607832473074,-3194106975021661190,-3822693003588244337,2395300917108129301>(),
               (String)com.yiyiaddon.m.b.a<"s2th3d0dioat5s","EDyiKhgsBVw6TSEwP4ISVseATAkgxpwmcU4x8A==",-8056284607832473074,-3194106975021661190,-3822693003588244337,2395300917108129301>()
            }
         );
      }

      private static com.yiyiaddon.e.p.i.a.b a(com.yiyiaddon.e.p.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.p.b.a var1 = var0.a();
         com.yiyiaddon.e.p.e.a var2 = var0.a().a();
         String var3 = var2 != null && var2.a != null
            ? var2.a + ""
            : (String)com.yiyiaddon.m.b.a<"sr920zkq1rmdi","9G+Bbq8T6PebVft0T2St2xPzagNMDmJtrEGvfC9VzE6wEg==",3784798922923519044,7595493022828212364,3536096304012032874,2967150956331056981>();
         String var4 = var2 != null && var2.a != null
            ? var2.a.eZ() + ""
            : (String)com.yiyiaddon.m.b.a<"sr920zkq1rmdi","9G+Bbq8T6PebVft0T2St2xPzagNMDmJtrEGvfC9VzE6wEg==",3784798922923519044,7595493022828212364,3536096304012032874,2967150956331056981>();
         String var5 = var2 != null && !(var2.bh < 0.0)
            ? String.format(
                  Locale.ROOT,
                  (String)com.yiyiaddon.m.b.a<"s2cknfj506h854","xK22eDHz6q9LWramy7/5FCF/9v4vckWgbvdr/M/tQaYf5xN/",-2479950507184913060,-9146951650000871884,8362611904182773299,-7716050518465495601>(),
                  var2.bh
               )
               + ""
            : (String)com.yiyiaddon.m.b.a<"sr920zkq1rmdi","9G+Bbq8T6PebVft0T2St2xPzagNMDmJtrEGvfC9VzE6wEg==",3784798922923519044,7595493022828212364,3536096304012032874,2967150956331056981>();
         return new com.yiyiaddon.e.p.i.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s1nmyp6tj84xma","dWCPJhU6lXyiKkU0Tq2GZvv+oDgfD5+HqW7AJndp+vER7bmO5vk=",-669998233817842042,7264308740589410985,-1619961407873370756,-1911052491857537450>()
                        : (String)com.yiyiaddon.m.b.a<"s3av4gi9ezkacq","+/pdscGcG1qPZQyg/fT9zIy3KYKG/4ok4E3i/AQVkDcq5qAgG0o=",9105175207865827292,8246771363185608513,1017789467611577862,-4714097356436662445>()
                  )
                  + "",
               (
                     var0.a().dP()
                        ? (String)com.yiyiaddon.m.b.a<"s3qhi3nrbx22y5","UKPly3UQNIJ+MhCrX6ol1h3Xh58lPHXE+L9dpw9/SbPvJnSTEBg=",-8794961592557151773,-2131389149280655779,-2859851673326537016,3589475659543383587>()
                        : (String)com.yiyiaddon.m.b.a<"s2r7sxzozkexiq","yuEMFTUC8bXZm19ag9MhwEM9+dJmppDF9+P58j2Y0oVBPdVc",96029631618167278,387502002221989197,4376000767953595086,2225844664112816545>()
                  )
                  + "",
               var3 + "",
               var4 + "",
               var5 + "",
               (
                     var1.eO
                        ? (String)com.yiyiaddon.m.b.a<"sjnrjhgavnalg","079X5YbP81K+0oC9fh1wKVs0U7jmPmKxvka42SO787ieJA==",-666156184828204030,7851239827306191291,-6545926077332859439,-671986532114789179>()
                        : (String)com.yiyiaddon.m.b.a<"s3olt68f373opr","NDXyTVFJOE/IXwkejbR28LtY2ltovtyo5tTH1jhnEC6f1w==",7274570584707519336,2380118422783891542,-2242526419463456867,-9137553805614772227>()
                  )
                  + ""
            }
         );
      }

      private String c(int var1) {
         return this.ad[var1];
      }
   }

   private final class c implements g {
      private static final float ea = 18.0F;
      private static final float eb = 6.0F;
      private static final int qe = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.p.i.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3ler1bjc79zl7","JuaKD7XBS2B0lD7taEc6UCjEFXayFE3r0JEWvfemRLo=",6897706300053490971,-5116491722315962229,3564559901180389389,669021148043738772>()) {
            case -1455173462:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"sfnwpxpfbj4cf","dJZ9yCptkr0W1wqtB0/1KEpyDu8bqO0fYfjlpPufP0c=",3041446720214813267,-1693814664713701842,-2249677862781981855,3838973255971050711>()) {
                     case -588380777:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3qznzyink9i87","eZg61NVQpOFhzRdX/7dy2RfBHIXFMcSA7Q78oDurYCI=",1583692498663591952,1827806283245930928,7466852799277112048,4496682609421898008>()) {
                           case -359723865:
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
         (String)com.yiyiaddon.m.b.a<"smji52shirtfy","dzHR6nB5ozYK3I+OsdtK/uCjvhGni7oOCpDEg/+LTQU=",5194154489700844038,-1611945420280184146,114578053542223191,3157450724912657822>()
      ),
      KEYS(
         (String)com.yiyiaddon.m.b.a<"s25alpob9mrdfg","vnb/xVgMTv85qQFFM0VbhMgjq62hEeoHR/0K15I64TNPtI26",4774117279015326124,-2677654462090678496,-7498167098184745775,8872134108909344007>()
      ),
      GROUND(
         (String)com.yiyiaddon.m.b.a<"s3n6kk3u6h7gzf","jZksqtd2FBMWBHbRVmB1bZRu0SZusjEzibA3ttPqW0sYXmcR",1525009039702078381,8422990944050983926,7526032592756047318,-6834933518166771875>()
      ),
      WALL(
         (String)com.yiyiaddon.m.b.a<"s2mcwb3061a5sr","NU/kvZhjvIJNwC79ZhVRNAE9cD8wCGgbUYX5shMPc5ckc+mb",347008851308995006,6749241918933317587,-2296427428806227735,-3850042298353310987>()
      ),
      COORD(
         (String)com.yiyiaddon.m.b.a<"s10q8xtab46w46","WzrKFU8KeK0mTQd8RS2EeJsNVttA0Rna1QBCjzz+XNI1K7CG",-6333098480142450608,-5281315474909224511,1060591357849505796,-5955222483160917087>()
      ),
      VERIFY(
         (String)com.yiyiaddon.m.b.a<"s2b2y4clx4ctc3","ZsGvPZhNFlifEk0EYc9Q5p3fz6pLFdCKRo0UYnq0A1OdxheWoVU=",-3866133730131039403,8485678719005838836,-1821844401816855022,-2798849078392526447>()
      );

      private final String yU;

      d(String var3) {
         this.yU = var3;
      }

      private String D() {
         return this.yU;
      }
   }
}
