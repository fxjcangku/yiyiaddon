package com.yiyiaddon.e.k.d;

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
   private static final String pg = (String)com.yiyiaddon.m.b.a<"seur8wknxsawa","zDlbJYlY/Z95J8gzxLc/DXBCYAt78mwyVNrnmlxciP3+pqCASPzd6oUHNOEPo8k5z1g5V4jTX0chWe060psuHKo4G7vgny9p6ubnn6IRGas=",7840809895882130403,1081393946027884859,7097805579464081124,7511979963927788858>();
   private static final int lg = 20;
   private static final float bI = 6.0F;
   private static final float bJ = 11.0F;
   private static final float bK = 10.0F;
   private static final float bL = 12.0F;
   private static final float bM = 6.0F;
   private static final float bN = 6.0F;
   private static final float bO = 320.0F;
   private static final int lh = 16777215;
   private final com.yiyiaddon.e.k.a b;
   private final Set<String> H = new HashSet<>();
   private final com.yiyiaddon.e.k.d.a.a a = new com.yiyiaddon.e.k.d.a.a();
   private com.yiyiaddon.e.k.d.a.d a = com.yiyiaddon.e.k.d.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.k.d.a.b a = com.yiyiaddon.e.k.d.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1cvip0b6hj7l3","kk3HSIF1aSGdgilPkyT1wPU+qMUyYlwkunW9OZU3s8E=",-5215163427896911580,-6356211335235250839,4601410282647601707,1592676900979147303>()) {
            case -1030539615:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ga2ommjhdil8","6mRKwfg6GtuCLOcgSiwPTYcJHrzwJV/c1uXKOGFezNo=",5760013451180274063,-6179033325554578173,-1386696139151937127,-4192874463876298154>()) {
                     case 494497438:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s3jbmt4plizovf","UmzQTLN48sx/Z/hd293j4WmsL1mts8cOpeoxMD3+QLs=",-6869769857627598664,-4373831867402184470,7555364599347860170,-4259492353789243735>()) {
                           case 1488845493:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3lbtcc8i3byju","+0NVMWLPKIyD/IarMqPbOl6KU0BoiJvxsWaPuhfdkw0=",6834386587744486357,-1902750798065192707,-1120056774755393678,4914171355588822392>()) {
         case -2100617772:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.k.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"srsw8m2s88ph4","LzELs4ZYl0+AKBJMPfJCHc6JzGWTwCbwbzmhC/EJKRJUvgrYDSSiapwH",-570496091610136839,4156732659670423664,2700344142586550172,-1504344562450246340>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.H;
   }

   public Minecraft a() {
      return this.minecraft;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"seur8wknxsawa","zDlbJYlY/Z95J8gzxLc/DXBCYAt78mwyVNrnmlxciP3+pqCASPzd6oUHNOEPo8k5z1g5V4jTX0chWe060psuHKo4G7vgny9p6ubnn6IRGas=",7840809895882130403,1081393946027884859,7097805579464081124,7511979963927788858>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s133ei3j1cj200","XibmGo4ub/gDW5gZr359hPyxKFiqLTf0fJ4bmC4SqZE=",-6853326522200009149,-8990849903632326567,9141434905889800032,-5127509170323791998>()) {
            case -1222715406:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"si9z85biotv0q","1s5Bf8zgE5SQF6IhaaN7xk9EESoy8V+SxqtBcmq155w=",-7767992475359799245,2439023480638988258,-7651436055071413214,-6480490114554986536>()) {
                  case -1002247799:
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
         (String)com.yiyiaddon.m.b.a<"seur8wknxsawa","zDlbJYlY/Z95J8gzxLc/DXBCYAt78mwyVNrnmlxciP3+pqCASPzd6oUHNOEPo8k5z1g5V4jTX0chWe060psuHKo4G7vgny9p6ubnn6IRGas=",7840809895882130403,1081393946027884859,7097805579464081124,7511979963927788858>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"srdnt9ytq60s5","aSOiEjpzDD7K2kV3j5Q8OQmxfBcTkJBKZtQiwBl9bzU=",1310755261425856940,-8790391203326594338,3474864467806517368,2010529086475330742>()) {
            case 667369213:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s6nv2onxob9qg","pDnuwO2UxvuHOp7zHJm8f/JwARyMBaJ/NmNjJxc4PjU=",6201301882379133516,4200156961138870844,-255980205534395388,3058305483878485591>()) {
               case 1818817779:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3n4dn0q3ahlf9","XUZjYF97ZtoAhrcvGm/6CL5ty8mLzFmVnqobR6E1b4Y=",6621576760774297610,7164378473460724607,-2669339300032026829,957747920145605457>()) {
                           case -736801258:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.k.d.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sw3nhahopt1ia","J/b+as+QY+UMRrwTbTZ7gp3BEXUJAtBVqWKWWs+KEGY=",1294205200884616061,-8128143542901336899,1723741018445977899,3139704923476322850>()) {
                                       case 772287644:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.k.d.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s12cos0wpggans","2iYn+gKeE1qLLalVfBikBjXbcdr7kGzSdSTwImPeE3E=",-9181594207411207722,-7424324870064772157,8011429289870024290,8182262747209115174>()) {
                                 case -1964385118:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s2a4snw8upugib","LxqcZ8GdluPslQQ27ccXNnlNsfmT9b77trJuC47pDPY=",6145856507411409422,6826130725709530809,-1423661753849795635,-5811640474376479543>()) {
                        case 888184726:
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
         switch ((int)com.yiyiaddon.m.b.a<"s32wzvdxr4jpyf","T0vJOKPbHW1zzjQOq84Thw1VpJ+WRkynPlzFi1bLzkA=",-4310576621017405761,-8015979371400840775,2851406885373874224,1387731655842259472>()) {
            case 1869262757:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s23fzm7bs25z5d","A5pOLrV2PeiNt/GaNnChYQXx+ZSwSGRvsy35xJcdz4Q=",-5015204711121626037,-2087545247766787426,-6636091606197696137,-755996806259034306>()) {
                        case -1712940667:
                           switch ((int)com.yiyiaddon.m.b.a<"sl3or7mi93q7s","s9xaM1TddJcIIX9mwPYKQxAheG5XjEtoGQGxJO+z4c8=",6220890331614386457,-891340056551978989,-299196019304933195,2251274929745595719>()) {
                              case -912474824:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ywgysb843uyl","B47YTHLi3S3DUWr1oMBI/cpF5qwFJkdBmVqaoKEHl3g=",-4199538365761380830,-2981997841303196625,4753953328620132600,8684437026222799571>()) {
                        case -843446949:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"se6wt7w55hh40","/OszBhE/Mey/ZNBOGF915hyErhOw2baMcM0al3lqbd8=",1324519582095641592,505787113900314806,-3564574128252078286,-19336320227738158>()) {
                     case -620970926:
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
      this.a = com.yiyiaddon.e.k.d.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.k.d.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28gyynsgz5q","12Ep5UCZruf4GYYPefySKfueFaf6Ldfx+4anF1ZI2ck=",-670363391275851401,4752847499738257456,4931156610088924415,8933814979601930172>()) {
            case 321455663:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1che104vrgq71","SgLK8p49XzFIMxq2zVO4wON4AFkDQyWgjgcBWda18NE=",-7867256906709989645,906839135611007799,-4903198507730772424,6071362139179152895>()) {
                  case -887151971:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s16vok1ogz48uy","i5pWEegbXTprI6660LKi9iej6nm0XyGsgNyOGGSE6hg=",-4881937059366254244,-6926200136743366371,-1946744661247089944,4506172600908417940>()) {
            case 1844270385:
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
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.k.d.a.c());
      this.b(var1);
      label27:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.k.d.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1oc1e5wdj8qyp","EOxpcOodWtb2gaMB6HwZuFzAf67+cgzW/9r8jNq8gSs=",2530791930777698273,-2345670100202556706,2453326163050543089,5424459564414502072>()) {
               case -982804478:
                  break label27;
               default:
                  throw null;
            }
         case TARGET:
            new com.yiyiaddon.e.k.d.a.b(this, this.b).s(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2kyd1dur7ldkv","sl9nll7eoxz+r6185NZ7+NkcbBzepVfLsS9wYb8kRa4=",6522079040663605006,7781517532985634333,4987318442378764494,-5905691499030338682>()) {
               case -812779616:
                  break label27;
               default:
                  throw null;
            }
         case PACKET:
            new com.yiyiaddon.e.k.d.a.b(this, this.b).t(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sqklai7qxe8dm","Tr2iuXYZT5Ng0qmrHCqM5KOy47CUHO5bO7xAoLCY2S8=",-688656024737055911,-357802201876056569,3087353087687875129,854094164034889193>()) {
               case 651072341:
                  break label27;
               default:
                  throw null;
            }
         case ANTICHEAT:
            new com.yiyiaddon.e.k.d.a.b(this, this.b).u(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2r2ygh0e654m3","WKWj8S5JA+BV3StD0YRtbPh3tilrwcYj91A8PNp9Z08=",7934883719302259683,5844474570681693191,-4694347925693702888,3633365371919617118>()) {
               case 443486092:
                  break label27;
               default:
                  throw null;
            }
         case RENDER:
            new com.yiyiaddon.e.k.d.a.b(this, this.b).h(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1tu8aj7gbmyai","RyLzHEWvRtBMsIMWQEIb9OWjcKYqsxOcPdrGFIgpjBY=",-7642960423599583793,7153651706207207361,3936166540916650018,1242530094100545196>()) {
               case -1816312639:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.k.d.a.d var6 : com.yiyiaddon.e.k.d.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1zsvkb2y00u8l","hVo+BfIgodrULNGyg3Q1DZMo5qmT+55jib9yV7zaGLY=",2836612579024149080,3134691054574259324,6402453553234042865,-254487498049575337>()) {
                        case 1280749605:
                           switch ((int)com.yiyiaddon.m.b.a<"s37pejdqmqkmcr","DUMtD+UkABOhBDi5e8cMnw7gCVPXVkm/GNIYlml+nmU=",-47860704719377376,-7375196963684716741,-7156235966046474621,-7188312750582348589>()) {
                              case 1627459288:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"su8fifzf3o7r6","d6YGSmgfavDH3b2WqYrqVctehbgMwFq9+iA10MxFG+w=",5428339642092315522,3610439002296959182,-9008086271124897966,1518016067274264781>()) {
                        case -1121430944:
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
                     (String)com.yiyiaddon.m.b.a<"s26hw5ny07gyd5","Ou9FygvSZCQxD1hTrCiKizBf30VAJlyhMqr/4E7pghMmWGY+",-1608679702469804511,-6742278106928903072,-6620495348485413230,2676389622402838193>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s1xl0kt0hsdzq6","P7nB3RbMLJGmoqHeA4NDgsyfKLJvUYSCgpv7AocNJ86W+35TtuY1eYefImC5Qxl2tvUU3UWbPmfkDbbwdg8cVYh50A5F5spgDL3pu47NuRnX6fO6GUzizEcBAqx8rXHDE591Aw==",1387552725925305741,-2222145491339827743,7104735185739454653,2537776100002419477>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s1v2f1nv6r7xu4","XBUh8p7p9NHMoljSAqy/yJmfg4OeBVlo6QFHOpgu5UzHocfH",3181717524525134470,1019479097716994307,4663764785146955704,-4697469844423242421>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1eat5pv6xjsac","nvc0xHVmO3f+7rdA8QiYjFLErjYQxKq06TTMsPFzSA0=",8790269237033011217,4605156678015875004,-5392011551442673518,-7457344440555852915>()) {
                              case -1140975622:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2xg7in4h2jhcg","ydO9243eTaYp83XqqHplCkmJS1pHHo8mZaoxeteosvw=",2000759774008578574,357270387585850381,-4970074288266356010,114704877247199952>()) {
                                    case -263454863:
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
         (String)com.yiyiaddon.m.b.a<"s10q71zljdtzdx","aTof+jnx7tTv98PVfS3mnuI4XXosHVZh/bQfa+nH",-3585307333082346611,-573588643334591467,-1857715484484045502,6220822098271787513>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3tmj7z6v3p5mo","2qd10JizrsJzBNstalT0L9/qgCTR1WmwIQaxJMqrGnE=",-4493672316243418894,-3866785791811490861,-8222237002000026197,5065051172295832131>()) {
         case 632819157:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"sht38vl0j53nk","9KAvk7F32gs6caLh8YoOTTPuURs0bgeC40sd0Nz9GTo=",1679162500539548145,-946192181689957521,-2650420725505154345,800235700788007949>()) {
                  case -508588154:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"sxfrdcr6o0hd","wprODuSAfxHyMJCiNRoXkGdR8MozcQIqwuRKG/eNl7Y=",-7284288257512360154,231611513207537903,3452296492577144627,2737053021198523824>()) {
                           case 875801793:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s3vdu2ckefpnbf","vryMamw8G+wsnBA1GSPocTNj6GnfEQZVQisQ9w==",-4909795759308060650,-7779098909571143644,1714199841402122455,-3527486304879823929>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"sfq11k83cyt7","IslusDPUVsWfM/T5HbC2eekhE7uo9ifeh3EEbmcz4FU=",9186389885702423810,7929977547288685096,660244530867527365,37825693285835512>()) {
                                 case 1841886312:
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
                        switch ((int)com.yiyiaddon.m.b.a<"s21hr49imr9yg","Dw8ualCKeah2YAFgX5YawTJgxRPn8uDxzI+uIovYZ8E=",-807367203887617023,2512238719725720497,5708635222205041907,6426670407329883817>()) {
                           case 2139618073:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2p3litdwyqmfh","AKdehG6hP1HBbZrvMRjOxqEh3NWYFRvJWquQZPAmylM=",1330173594022710539,-3632770373314433328,3563497820398196040,745995103317753240>()) {
                                    case 1028123976:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s15m6esb5epgpd","YMC7XRu7JeSQ5KJ9hAdY6yQCkmlUugtsJylx0ZZSxHU=",-1690307353674484626,9197478135835044210,8887823892710184588,-3677226297489023793>()) {
                                             case 1065562223:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"sww76845b3zt2","zZSYCgD6WRSdT2k6h4KKguAk0fJzzrbowhOt8/YGGEg=",-3571654750273089297,3143171682435635143,-4412370757486797272,-4161064375446352312>()) {
                                                      case -632083408:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2nrps8kx4vud9","bt/3tDUDYvGAXbMjanA5HUI25/S2po6P/PDRuatRp8o=",-896653368164792573,-5506807341028835914,6161717962221919582,2672007510305356708>()) {
                                                            case 512902638:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s5tmvd6sexc59","8F2CQQoK1yz3ZTmZZWZfgp3E89Im5bZcwjMCTFv3rQU=",-4063969160352595784,-2441940764593767380,-3052212494209894240,-8989653933476179521>()) {
                                             case 723245880:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3grlskywv0dkw","16cqV8F0/JH69ojbgeS4lOPgqtl073cPvxa337lW5nw=",6387134116343941481,3248194126114855686,681198888600276279,-7724562479840465186>()) {
                                                      case -524736257:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s31ta1hxpods7o","stllyWbEAf0V2qu8SIAwkmHTkPL2eYhSh9WEcOJ1R9M=",-942916215544545367,3378512379809776519,4096876559578689907,-3747390751492603397>()) {
                                                            case -1684966904:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"s2x08sdty2ihpu","rhWIvOPcGeTDSor0Kqd0QkvPFV7s5nqTSUr2F/ueUzU=",-2151342686652250489,2754487917479900848,2932081862566296875,-4759984857187113538>()) {
                                          case -609906843:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2n0355gpzp8tm","P6/S9KZfUu0pj5DatVDkWfUY16NjeUjblNbVLmXwEZI=",-7113958508564602118,-6077779573040060098,-688834868614609382,8410464255363596933>()) {
                                 case 544714263:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3mn5ip1t2ntk9","gUi2WhWrCOCF9qKTyPipPaW6hV9TryGlrUg9ml0Szfw=",-3715679687517878839,13263661469795102,-4265126413666004562,-3940425495617428667>()) {
                        case 671605454:
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
            switch ((int)com.yiyiaddon.m.b.a<"s1tioyd4pphvuv","jTx7r6ESGkzS9zmgTIuu2cyMQ0F/63wuJ+HehhKtq+U=",-9113451426306248813,6870605631268913579,8918388211634562212,-75153612447089625>()) {
               case -2119345486:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3go3d2syshxrp","AmlnpZ0Lkge44PmPR29c+zj2ScFQPosNH0oMRfIwpHY=",7551051843375979792,1502221048814027942,1688740479529312475,-7386604365925276855>()) {
                        case -1651379298:
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
            switch ((int)com.yiyiaddon.m.b.a<"s3v3grwr5ma7vf","ShkhwbshvAztQeiSQp5H/+b8MSES1SeqzmYiiOR0gMk=",-3384911894649769468,-3955129915628032962,5210735355989492404,5490584296093395770>()) {
               case -1108315660:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.k.d.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3up4r554wtt5r","Y0Qe24A2vLDrt7yJ7T2GVGwaaxrWAaj6jBzraCflT5s=",-3824238423323433615,-4531000403761277233,-1599809527134444532,-8381047446130896393>()) {
                        case -1106485196:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29cwalyxxqx13","i80sfUXNspLsCWwLB0OjrNn0qvoqJwax4m6cNMRcosk=",-5606748449777113393,3023495935407743206,3710156717991914088,-7041322863373008646>()) {
                                 case 926866426:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"suiaoeg4ukymv","zHiUv2NjpOwWQIfABMJiwpV6DbI97gnXjpZGE7XFx9s=",8083613824537066544,-7403598784070594955,-3402858322345233330,-9202979908953158253>()) {
                                       case 428319715:
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
                           int var13 = com.yiyiaddon.e.k.d.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"seeypdu6vpaff","XY60OdNW0K/aEvrdGSQOtmzv/dsT87CQNkn9E3bjXSc=",7691137472215873579,4496306764957831685,-3180778847031466999,-7654482999506779566>()) {
                              case -1139728890:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s39czcexli7ft5","TotkmdKZ4sGmZjtjH5IDy4OdjiDs3Sba0xc4giLCK3o=",1790125915511796181,-6859002393121563082,-3064152190977834767,-2068406415888458758>()) {
                                       case -668427566:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1cqb3ktvc7tdv","6RXlpY7xVX0BrsWSgMxLy52njy1htnWWQL75z9B7nK8=",-4756160964328399420,7721532683668066439,3014033705382717495,-4692327902506804119>()) {
                                             case 1770577113:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s2w2rixuqm149","dMfvmU4YGSdSXAXsa/WUjGs9FOyTeSZAMsHkRC+O21Y=",-7754046562672074640,5880028372969383078,-6487131061545205531,-5897361339127314857>()) {
                        case -1266867387:
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
      private final String[] K;

      private b(String[] var1) {
         this.K = var1;
      }

      private static com.yiyiaddon.e.k.d.a.b a() {
         return new com.yiyiaddon.e.k.d.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s2rf1p4wqed4jy","P1p1a98H6D88ewc2ZJ91FPLT/FOTCVDALa+DmQ==",-3163643149409719006,-6710478211406253150,-1270367589518446449,-8184370877515370626>(),
               (String)com.yiyiaddon.m.b.a<"s2rf1p4wqed4jy","P1p1a98H6D88ewc2ZJ91FPLT/FOTCVDALa+DmQ==",-3163643149409719006,-6710478211406253150,-1270367589518446449,-8184370877515370626>(),
               (String)com.yiyiaddon.m.b.a<"s2rf1p4wqed4jy","P1p1a98H6D88ewc2ZJ91FPLT/FOTCVDALa+DmQ==",-3163643149409719006,-6710478211406253150,-1270367589518446449,-8184370877515370626>(),
               (String)com.yiyiaddon.m.b.a<"s2rf1p4wqed4jy","P1p1a98H6D88ewc2ZJ91FPLT/FOTCVDALa+DmQ==",-3163643149409719006,-6710478211406253150,-1270367589518446449,-8184370877515370626>(),
               (String)com.yiyiaddon.m.b.a<"s2rf1p4wqed4jy","P1p1a98H6D88ewc2ZJ91FPLT/FOTCVDALa+DmQ==",-3163643149409719006,-6710478211406253150,-1270367589518446449,-8184370877515370626>(),
               (String)com.yiyiaddon.m.b.a<"s2rf1p4wqed4jy","P1p1a98H6D88ewc2ZJ91FPLT/FOTCVDALa+DmQ==",-3163643149409719006,-6710478211406253150,-1270367589518446449,-8184370877515370626>()
            }
         );
      }

      private static com.yiyiaddon.e.k.d.a.b a(com.yiyiaddon.e.k.a var0) {
         return var0 == null
            ? a()
            : new com.yiyiaddon.e.k.d.a.b(
               new String[]{
                  (
                        var0.g()
                           ? (String)com.yiyiaddon.m.b.a<"s16eom9bzxnxn1","BaWDlbkJHwVndfKawTESKBJLqt2a7ci1rLXId/1e0ztDBGDvKco=",4055884099051882811,6935055364172351564,7368912973583849052,4905664003068297286>()
                           : (String)com.yiyiaddon.m.b.a<"sthfx66etaxvv","nnKj2zscPCurjKcM4xtRzTy43c9WGszBjqB7okiPWHbLDM9view=",-802173464293514443,6428465018264516287,-2294257908539026132,4768449911950623211>()
                     )
                     + "",
                  var0.a().a.pf + "",
                  var0.a().a.pc + "",
                  "" + var0.H() + var0.bH(),
                  a(var0) + "",
                  (
                        com.yiyiaddon.e.o.b.c.eI()
                           ? (String)com.yiyiaddon.m.b.a<"spw7t0ydfuuyd","LB2VtstACRndDEboDtEx6VwCICWeFBHWtbddyYpGKH6R5EHJ",-9043698722410802828,-7193634560829645609,-3325874196185985563,-5422278838565646272>()
                           : (String)com.yiyiaddon.m.b.a<"s2gmg5w68hx9ww","b9lJqcIOos7ksgN92CLUTyuW7hcBzn6IK3w9uqX5D4r7zDOi",-1587094678684705330,8337316808231156236,2271516941402691222,1380213248692204131>()
                     )
                     + String.format(
                        Locale.ROOT,
                        (String)com.yiyiaddon.m.b.a<"s1ok8xnzxf4m4c","AcCfuzFT88/iITu/qFsA6X4jKPIM+abePicort8bWRrprlYF",8771458553572770671,3647707894173141712,4111868504296006295,4077393485119928729>(),
                        com.yiyiaddon.e.o.b.c.g()
                     )
               }
            );
      }

      private static String a(com.yiyiaddon.e.k.a var0) {
         com.yiyiaddon.e.k.b.d var1 = var0.a();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2g56go8rpuenw","RAuLo9HuKcRaQxBgUwFwpXupxukHI3brndgfMWfomCg=",5456864961878929922,1347144865971483020,-8789512644065791141,-2221181407542708663>()) {
               case 1560582723:
                  return (String)com.yiyiaddon.m.b.a<"seu6kzgpimo6h","pX8iP8fFYxdGGVqjBRcMO2xDwNR/g+dTh3HdZoXuoB/Vlg==",6159879276176496191,1884003698357036923,1059649296157413365,-2333277662098307497>();
               default:
                  throw null;
            }
         } else {
            int var2 = (int)Math.round(var0.a(var1) * 100.0);
            return "" + var1.z.getX() + var1.z.getY() + var1.z.getZ() + var2;
         }
      }

      private String c(int var1) {
         return this.K[var1];
      }
   }

   private final class c implements g {
      private static final float bP = 18.0F;
      private static final float bQ = 6.0F;
      private static final int li = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.k.d.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sz6si1r33fto8","+htkvyOfQd3auJbUjGYv/Ilc5m5V/EmCTCdyUfv6vCo=",-5665253486193582825,1516713708901067950,-1331672480196403009,-9163244867246099155>()) {
            case 1697991936:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1o9w6tk7425ci","KYfo1FA5tmBGXcYGa9kIWAieImGplixBUrWWyEqcyts=",989251786608068155,2537223127190623411,6005848832145599936,589931718210830667>()) {
                     case 286239145:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s371wbuj6tfjb4","O51idAfkL0bsNI84okPFZjP6EyJAKwHjYCNIltm6EEM=",1657125468474018194,5268293832482643251,-4072359422156425686,-5272506238036975708>()) {
                           case -1488213549:
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
         (String)com.yiyiaddon.m.b.a<"syrgcx910mvws","gcVzHp1tcjJANcpKrrbJnOWqsZRQuYgBqegrkDZTCcs=",-3337194270363194422,-6031099935852410840,9092682267594322828,-575144802589892202>()
      ),
      TARGET(
         (String)com.yiyiaddon.m.b.a<"s1unklg6axin7e","uVrokEYwTpyfrQQMyTu0lR95JjwYtm3ulNsnfVE0fbNI8D+2",-2229743033469614745,-1629634650384288081,1822643942437851622,-6081366097597080754>()
      ),
      PACKET(
         (String)com.yiyiaddon.m.b.a<"s22zeyn6fwbj2r","040n/P2xSNPQSroQW55lJU8yUbOiJbJIpiaTpeAOaTrZQd1c",2549358036216842004,6642037750825605098,-1338167754768781307,1201560305154192336>()
      ),
      ANTICHEAT(
         (String)com.yiyiaddon.m.b.a<"s1lz17w246811o","MRQ1d7iu4UHNIgn63tklLQwL4aedZIc7z7IueWRYa8tiTxHzf1/2W/nr",8155208942350922608,-3391403326500218977,1718194357041109303,2845862542070760130>()
      ),
      RENDER(
         (String)com.yiyiaddon.m.b.a<"s39el4h93jmevt","penMx23RHdcB2eTq13EPMM2IAeCrZEp3VVHHKnPE5rtHKKUj",2231167352803207548,-1951280062211848128,3701585353286057187,1010364946147386056>()
      );

      private final String ph;

      d(String var3) {
         this.ph = var3;
      }

      private String D() {
         return this.ph;
      }
   }
}
