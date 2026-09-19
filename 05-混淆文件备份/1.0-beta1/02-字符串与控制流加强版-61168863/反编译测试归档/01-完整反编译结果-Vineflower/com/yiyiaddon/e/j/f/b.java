package com.yiyiaddon.e.j.f;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalTwoBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class b {
   private final com.yiyiaddon.e.j.a d;
   private final Minecraft I;
   private BlockPos t = BlockPos.ZERO;
   private int bi = 0;
   private int iY = 0;
   private static final int iZ = 3600;
   private static final int ja = 5;
   private Consumer<Component> d;
   private long B;
   private static final long C = 3000L;
   private int jb;
   private static final int jc = 40;
   private boolean cL;
   private boolean cM;
   private double U;
   private double V;
   private boolean cN;

   public b(com.yiyiaddon.e.j.a var1) {
      this.d = var1;
      this.I = Minecraft.getInstance();
   }

   public void fd() {
      Settings var1 = BaritoneAPI.getSettings();
      if (this.d != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s358bn915hafz3","ObyCL1Q2SSbiGVgYvdfaRgc4NpTsy1cvjXMiUtDF7mM=",2035219096776915920,7097202731173130915,5869447945331686852,3272539355223347357>()) {
            case -1367189337:
               return;
            default:
               throw null;
         }
      } else {
         this.d = var1.logger.value;
         var1.logger.value = this::b;
      }
   }

   public void fe() {
      if (this.d == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3khxbh6avjblm","qT9la3uaNMzKp9VHubv0ZMjLrFs3xd6/G+QIIhrwlsw=",-6105773653109092798,2138951048097206307,-1949712116734539402,4558750294571561711>()) {
            case 397001198:
               return;
            default:
               throw null;
         }
      } else {
         BaritoneAPI.getSettings().logger.value = this.d;
         this.d = null;
      }
   }

   private void b(Component var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sa0bh2np0ro8f","sR5Mb+N333CPlmDRtJSQ2lMRouhZizm1PRqRf/pD8Zc=",-3902443340505665198,-5461083825050312854,-6562951963013159526,6486027149407593903>()) {
            case -1302272623:
               return;
            default:
               throw null;
         }
      } else {
         String var2 = var1.getString();
         if (var2.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1ftxfym1xfy2h","CRR33vmSRToKeOAVqF2Agx+nG/6wquxJ+QF8yYmbyZQ=",1685498700350451621,-229781672143349695,-276658465695941024,7422160751341131940>()) {
               case -1598119061:
                  return;
               default:
                  throw null;
            }
         } else {
            String var3;
            label77: {
               if (var2.contains(
                  (String)com.yiyiaddon.m.b.a<"s14e2jeggtdyc2","2fIbifS2pyM/the5SA0hzS9//tp26QMhyITsRF55lt/ZaI1w",4113654024735118859,2793601707340462785,-2378289516114898335,2487353283350844321>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qc432dw7kw8p","3ojH0FDQZk5U0pGIVy0veTD+CvU5ZMAHNAgMNqRlEug=",-3996532542970830654,6881278122948113380,4370366475713348778,-5808468523577476299>()) {
                     case -90703714:
                        if (var2.contains(
                           (String)com.yiyiaddon.m.b.a<"s1tv9uuwjh2na","KvPZIcyqhLFY2J7wz9MDM4sE21nfIhFMo4zV4U2lMkI=",4435566709023322061,-6058169391336824493,-2459101065708715533,7200562121235864238>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3kfrt6fkbf6uj","nJVcQqB1IqQC1WUr0Ge/1YVCUblj7gMW23usmyCjBso=",7906481184391236065,-2910664765490431098,-2153165970660228914,5486784044277325942>()) {
                              case 1990686668:
                                 var3 = (String)com.yiyiaddon.m.b.a<"s3o7qrtkr7t4x0","xp9DruJ0J6Ip+RdJSGLExKnjvrOMOePNCc8ROkjvr5NF/pAahcuW+sjyma5Lfp6BjaBGhvxKKmuVuyeaqrr43tYMYEiC8+ubI5c4T78On14HTh0i1n9tCa2m",-4852303540180290693,-7858937830466044345,7458431314396653294,3292795416877504102>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s2q5pzbsjf6n2m","K/watcZ17J2lpdhhQmtmMZFqACcCUKZhfmPVRncHUZI=",8492187818612985211,4161509752159652351,8259068159094593193,-8749082545297954633>()) {
                                    case 615796855:
                                       break label77;
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

               if (!var2.contains(
                  (String)com.yiyiaddon.m.b.a<"s1ecqpjvbxlmwe","IRhHQaffEthf431hMmBKrDCaDDMJWQxRkEpd1yTO6XiCzw==",-2276813102185071243,-1627793704021917932,-6698666291571106930,-139918395772096615>()
               )) {
                  return;
               }

               label61:
               switch ((int)com.yiyiaddon.m.b.a<"s5uz5c1dbqefa","gG+bhsYHMvYG0U67jxp58OvmEfGaHdUg3mqW478HiPE=",-2450709874105720079,-3725338100182440423,857625385115263161,3629232510800410651>()) {
                  case -677222220:
                     var3 = (String)com.yiyiaddon.m.b.a<"s35teq9wfjepm6","b7JdOeAx1SZvFb02n9oOF0djjFtrJugfBQTRcp4eKZMQb8Q13VDRDblXlIpAHZy/CqqEQz3YX1Eo6g6+ayZS+Nj1hrTwIrTF1v5Ph4vm",-4157089901128262666,-4544680718931540194,-2959215218985285878,1132564256223827304>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2pi0gm0idurm7","orR254z8hNej3cOqnW5NeNH4ZyU6A2BAZj9akQwdRGc=",4160701090010175336,7556670267398857365,2073577722365659270,2419207952740353002>()) {
                        case -374181330:
                           break label61;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (!this.cl()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1dm0x5ohlk1ov","n0BPV2IfKABI8JmKVYbylOsLhTtvDV20g6RUJH7oaj8=",4379555158201453793,5374270293669884367,8592497151114708076,-8185381616247274885>()) {
                  case -1817197044:
                     return;
                  default:
                     throw null;
               }
            } else {
               if (this.I.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2oqw72atw041g","IOFmhYO6+gJrXHgC6li00+WqVfCJq0cX0PLDbBmQlVU=",-8776670585473415997,7646222000085936037,2390019095163368070,5465060936920882865>()) {
                     case -164506902:
                        if (this.I.player.tickCount < this.jb) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1a7pn0x21pwzk","E3071L7mSPn+jVkho8ASjt36sOwvJqZhViX3ihbEE1c=",6511311523500753718,5205354097068867422,-2314540263086935609,7805294890713465527>()) {
                              case 1013519963:
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

               long var4 = System.currentTimeMillis();
               if (var4 - this.B < 3000L) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2qyn0f60yy6vc","HcqhKEOO7kWktyCvHSnfeRZEmU57yFpjeasU3cb7RYI=",-4646332818565904741,7449281398206919196,-1750515228506024431,-9219764203442803290>()) {
                     case 1829887801:
                        return;
                     default:
                        throw null;
                  }
               } else {
                  this.B = var4;
                  this.d.K(var3);
               }
            }
         }
      }
   }

   private boolean cl() {
      try {
         com.yiyiaddon.e.j.d.a var1 = this.d.a().a();
         return var1 == com.yiyiaddon.e.j.d.a.MINING || var1 == com.yiyiaddon.e.j.d.a.GO_WILD;
      } catch (Throwable var2) {
         return false;
      }
   }

   private void ff() {
      if (this.I.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vo90rtlggikm","QDzJieIbFn1FBRkm74XcSv2GRzzqhQ8B73Yt5ObvaG0=",-4694280149010542875,-2343585743647358112,-7686649213156590992,6065862496695390527>()) {
            case 1350382201:
               return;
            default:
               throw null;
         }
      } else {
         this.jb = this.I.player.tickCount + 40;
      }
   }

   public void j(List<Block> var1) {
      this.a(var1, true);
   }

   public void a(List<Block> var1, boolean var2) {
      if (var1 != null && !var1.isEmpty()) {
         try {
            IBaritone var3 = this.b();
            if (var3 == null) {
               this.d
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s3juvmklt5has5","Q7D6nn874xs26br4qRMcX1Ip+icQgCT1CP65z6uDdKPqGJ8yXA0Np6pI5gaOxqpvbJAu0g==",-641914207583803652,2597022752145080260,-8013092458652039122,-7711585348181539794>()
                  );
               return;
            }

            StringBuilder var4 = new StringBuilder(
               (String)com.yiyiaddon.m.b.a<"s23ndn3p9lcsc3","B7cH3AjB9MJu0Sfg/W6b6noVhFY/FaD3DTFP4BkS33cD453N",-882442349379134970,-7559652611043844996,-4585878948053687240,292235438108236458>()
            );

            for (Block var6 : var1) {
               var4.append(
                     (String)com.yiyiaddon.m.b.a<"s2abgwl4q5lpk","9ydNQUKjEQ6AuZXmTSLJ4drgfyaAU7lKQRcrPUJy",-3699944305461026750,3746290684526608702,-7075363912014619217,-279622286241979434>()
                  )
                  .append(BuiltInRegistries.BLOCK.getKey(var6));
            }

            var3.getCommandManager().execute(var4.toString());
            this.ff();
            if (!var2) {
               this.fg();
               return;
            }

            String var9 = BuiltInRegistries.BLOCK.getKey((Block)var1.get(0)).toString();
            String var10 = com.yiyiaddon.i.b.a.bQ(var9);
            if (var10 == null) {
               var10 = var9;
            }

            String var7 = var1.size() > 1 ? var10 + var1.size() : var10;
            this.d.K(var7 + "");
            this.fg();
         } catch (Throwable var8) {
            this.d.b(var8.getMessage() + "");
         }
      } else {
         this.d
            .b(
               (String)com.yiyiaddon.m.b.a<"s3hutuvo8gytsi","mbOsS0tHSEMUwfU7642vlm5gF2olAPot7GGOI8F93ggaG6TKQk9gSEpUcT7fVhRRtEy1+HwIHJA=",4879514940791457319,2539214312999516641,1396718471176778494,220235976471884773>()
            );
      }
   }

   private void fg() {
      if (this.I.player != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1md8yq6fz7osp","DuhvL30/LFFQklcMOv97I4bM1rrSp7cWJRrzi1Kw9DM=",-8067271123832664790,-5850915443736827161,-1452868528172218588,-2734757311995497588>()) {
            case 1478868406:
               this.t = this.I.player.blockPosition();
               switch ((int)com.yiyiaddon.m.b.a<"s12mbjcs2yrqaq","sKSWGYxQmrXSXeLNfrdkcJ5/QqSEUUm7bq+BMwN44kE=",797931892409021303,-8158907181085813169,-809988843224165821,-3831384738750791789>()) {
                  case -1753191802:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.bi = 0;
      this.iY = 0;
   }

   public boolean q(BlockPos var1) {
      try {
         IBaritone var2 = this.b();
         if (var2 == null) {
            return false;
         }

         var2.getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(var1));
         return true;
      } catch (Throwable var3) {
         return false;
      }
   }

   public boolean cm() {
      try {
         IBaritone var1 = this.b();
         return var1 == null ? false : var1.getMineProcess().isActive() || var1.getCustomGoalProcess().isActive();
      } catch (Throwable var2) {
         return false;
      }
   }

   public void ag() {
      try {
         IBaritone var1 = this.b();
         if (var1 == null) {
            return;
         }

         this.ff();
         var1.getCommandManager()
            .execute(
               (String)com.yiyiaddon.m.b.a<"s1plqeccvz5f9c","mVvnJ59gu+bq8d0B3QznhdQBnGGUk4z5NEuxj14Ik/4pSCmk",4536412174229924301,-1381556390881128526,-1167330584867864064,782527130665691631>()
            );
         var1.getPathingBehavior().cancelEverything();
      } catch (Throwable var2) {
      }
   }

   public void a(
      boolean var1,
      boolean var2,
      int var3,
      boolean var4,
      boolean var5,
      int var6,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      boolean var12,
      boolean var13,
      boolean var14,
      boolean var15,
      int var16,
      int var17,
      int var18,
      int var19,
      boolean var20,
      boolean var21,
      int var22,
      boolean var23
   ) {
      try {
         Settings var24 = BaritoneAPI.getSettings();
         ArrayList var25 = new ArrayList<>(var24.blocksToAvoid.value);
         if (var1 && !var25.contains(Blocks.LAVA)) {
            var25.add(Blocks.LAVA);
         }

         if (!var1) {
            var25.removeIf(
               var0 -> {
                  if (var0 == Blocks.LAVA) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1hhls24otzyg1","CM7rQMOlpoxvDP0wFtHABwjqyWf61LS23dGbS02NN7g=",4860391701111802032,7026837858801760666,1737835312656279165,-6480542075042168402>()) {
                        case 764834897:
                           switch ((int)com.yiyiaddon.m.b.a<"s1l2z56j771wlu","UDQVWs6zHnltYwHpj+DWLMk6uXHv8pWZRWnSxa/uCa8=",-3846230947295712643,5029823583699032333,4326012386655270754,8651108851620126548>()) {
                              case -1722043417:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s14wsl1zlxhobw","3SNnP+NkaRiAt7itC1O/E2Ja9xg4Hn5S4ZS9fUkMZ78=",2648244774096049806,-8353514398849468791,-1036001164369047106,6626641057807578507>()) {
                        case 574495229:
                           return false;
                        default:
                           throw null;
                     }
                  }
               }
            );
         }

         var24.blocksToAvoid.value = var25;
         var24.allowSprint.value = true;
         var24.allowBreak.value = var4;
         var24.allowPlace.value = var5;
         var24.avoidance.value = var2;
         var24.mobAvoidanceRadius.value = var3;
         var24.maxFallHeightNoWater.value = var6;
         var24.pauseMiningForFallingBlocks.value = var7;
         var24.allowInventory.value = var8;
         var24.autoTool.value = var9;
         var24.assumeExternalAutoTool.value = false;
         var24.sprintAscends.value = var10;
         var24.allowParkour.value = var11;
         var24.allowParkourPlace.value = var12;
         var24.allowDiagonalAscend.value = var13;
         var24.allowDiagonalDescend.value = var14;
         var24.allowOnlyExposedOres.value = var15;
         var24.allowOnlyExposedOresDistance.value = var16;
         var24.minYLevelWhileMining.value = var17;
         var24.maxYLevelWhileMining.value = var18;
         var24.mineMaxOreLocationsCount.value = var19;
         var24.blacklistClosestOnFailure.value = var20;
         var24.legitMine.value = var21;
         var24.legitMineYLevel.value = var22;
         var24.legitMineIncludeDiagonals.value = var23;
         var24.mineGoalUpdateInterval.value = this.d.br();
         var24.blockReachDistance.value = 4.5F;
         this.fh();
      } catch (Throwable var26) {
      }
   }

   public void fh() {
      try {
         Settings var1 = BaritoneAPI.getSettings();
         if (!this.cL) {
            this.cM = var1.freeLook.value;
            this.U = var1.randomLooking.value;
            this.V = var1.randomLooking113.value;
            this.cL = true;
         }

         boolean var2 = this.d.a().ck && !this.cN;
         var1.freeLook.value = !var2;
         var1.randomLooking.value = var2 ? 0.0 : this.U;
         var1.randomLooking113.value = var2 ? 0.0 : this.V;
      } catch (Throwable var3) {
      }
   }

   public void m(boolean var1) {
      if (this.cN == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1osbbpi068u1l","ii8xXatcdkSlItS4ivrcsD4ZaHbv9/FhHZBZGw44BQU=",376324920894761208,-4169383832310869130,-1760590479648718060,5334712489598319488>()) {
            case -1326263342:
               return;
            default:
               throw null;
         }
      } else {
         this.cN = var1;
         this.fh();
      }
   }

   public void fi() {
      if (this.cL) {
         try {
            Settings var1 = BaritoneAPI.getSettings();
            var1.freeLook.value = this.cM;
            var1.randomLooking.value = this.U;
            var1.randomLooking113.value = this.V;
         } catch (Throwable var5) {
         } finally {
            this.cL = false;
            this.cN = false;
         }
      }
   }

   public void a(String var1, Object var2) {
      try {
         Settings var3 = BaritoneAPI.getSettings();
         if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s34u7ezfr4v8bb","V1QVOVQ7URFjXRdKknYv0tfQf8boqlIwCJLhIcb+7bY7SjFgBsJNdGgLF57JRyFt",2371397517773739522,-5043730840296093480,-7258033563122949766,-362606513874456277>()
         )) {
            var3.allowBreak.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s2ete6xcpbyl8b","kzhW7kMWK9X3Hg3SQzpQWhmPfK+QsepFYKYjhLjaKMi3ZYo456aFnK9tK3pIR5kG",923868387556453203,-7415397314897592070,8776877480914005252,5542635880807171579>()
         )) {
            var3.allowPlace.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s14jhnf12jnfo7","XqSX1Qt6kob8ZwLR0yE88x+VW0IC1XM/7Syv5b16UvEuUe8oIrYi+fedpW4YSA==",5845666505837311992,4325170070771088323,-1581149210980812599,2714746101827569514>()
         )) {
            ArrayList var4 = new ArrayList<>(var3.blocksToAvoid.value);
            if ((Boolean)var2 && !var4.contains(Blocks.LAVA)) {
               var4.add(Blocks.LAVA);
            }

            if (!(Boolean)var2) {
               var4.removeIf(
                  var0 -> {
                     if (var0 == Blocks.LAVA) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1itzqkavlqg30","g9m8KodJJeQBVF9t+julJ0AtwZE1Ub8o724bHfCGeG8=",-4012689157383487883,-3968465666039873647,9070065436661227133,3734850836972618387>()) {
                           case 389623850:
                              switch ((int)com.yiyiaddon.m.b.a<"s23jc1gzehdx5i","PGDe0HeSErks0S9UxXm8yIFesBs8Gc06LcHUOOLpp5I=",-5543651107297807357,-8461974380156129691,-1345410308530062070,1229885638140579945>()) {
                                 case 1239026224:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s1nidmixfwaa0h","SO3uaxnh5aNsD38tRwHTJFn22FV6dtrsw3pRh0jsjkM=",8276310387732057442,-7878063793436432829,2629229134905984487,8000393735269554245>()) {
                           case -2146856279:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               );
            }

            var3.blocksToAvoid.value = var4;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s2fk2kssh7yuko","KTIvTPRWTxFiCgvCKMz2OdkwxYmZcct7LNaFiaQVz8GtDm9NZaubqrv9Ih/InQ==",-6482710631171223032,5697836918716683141,6578595026291983894,-934546865352785671>()
         )) {
            var3.avoidance.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s3lx7g6vmws5pz","bRr9fvDQBlAyjqJM1eqmLol6yKIzs3cw7+By6gbtG9VKJU+SBoE1JwabOYGpQ6EX7LKoHU3nO5nH2eInmHyqvQ==",7227159323187439124,710630079720587999,4998765181044567420,1818416281978570709>()
         )) {
            var3.mobAvoidanceRadius.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s21gvlpbjukl1y","eI/0HZk+Vs9pqio5DrGWMVVRZ/mQkeniz3VMP3jzD9XCU8UKHRS0IGF1mcX2kvxSu5zxaRtjVMqZP5WU2+gCHUa9NvQ=",-546493737024488793,1112489419704894640,7336490590493410716,4990168198010503387>()
         )) {
            var3.maxFallHeightNoWater.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s30wiwwc4l79bx","fc+H8SNEnhseQs+XtpGGoT9j+u+Y2OT/Ck49A2HdxtZQGKYcD2xkGE8wM88dy1BIUHAtSUz0wzdyl/qgsqsDCcRv8tsdAJmDYczaoePCnSNkxg==",949479844712775884,2288527350768117006,-2522305006496700271,7808119185898314614>()
         )) {
            var3.pauseMiningForFallingBlocks.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s2yhepekncbjp9","sQdvU2jXeHwLccJ3jsHRpaO6/tMtgNqru24cf+otfKkvJXDVmJX/PfzHvkLnEixff6q8Wi6BbQc=",-1710065609041410123,-7116391850126682153,-7463896743413364315,5332006965605367505>()
         )) {
            var3.allowInventory.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s3ph6qdepyex3e","AgR8whefIpEXVE93vtGmC/1NKnbrAEsfTYnqHGBMl/vj7J0iRVL74MczJOE=",1116662910692703614,-8476973919149983195,-7573595072017254608,-3514540658467584158>()
         )) {
            var3.autoTool.value = (Boolean)var2;
            var3.assumeExternalAutoTool.value = false;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"sn9h8pdavsavc","kZY2Qei2yYWgNBQ4tqSDAHRO+6cbdbEYJjv2OPaOj5MIer8yfm8l/fnaCdIeLbEYVkI9TDFg",-4790133594209892692,-7634861440043230574,1476742104030760921,-5028533991657630130>()
         )) {
            var3.sprintAscends.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"sqg8olox0rsdu","oEfMo27MjeccCnHR8jjKN8Q7FrFM8FDeTcZS0SIFUbKQUex6EMDsjP/PHFqglUUKvGquBw==",8501162526463032434,7301462245662316960,-1389401034213255954,-3958116883746797514>()
         )) {
            var3.allowParkour.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s3a03tfollgejt","sLcTQv8cFSUQD0V0c4G/jVIKjGmyGGbksoODE1KAOAdfEhiiaMBX2x5zxLM3WSYaI1cJTxdbNFp+MvpcZE8=",-422724701827963409,-7637085955140893606,8071192495489062487,6194400511859301674>()
         )) {
            var3.allowParkourPlace.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s3vg5p7e5f81kf","pGjz8qcHuVYxHg5dL/t5lISAWZM01aQ44AZcV/abYz+q/tg2hs1LARWJLixBPIXVGyrzl82fWaLQQ/2ZMJ1BL3Tt",-8369802064878790635,-3142855822067786,1877339824786007583,517093917343107263>()
         )) {
            var3.allowDiagonalAscend.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s20vxiwzljkf59","ia6e/lu7f9Yz1SVq470DyCEcyUrqghpVIkGXefnfBKLfkPoLTp+Pj/Jqf7jHm5nMdD1pkjlLV1fJhuS0CvrM0JpXANc=",-5403528800286752597,8555514055907947196,2153303790082888655,-8704336024808123156>()
         )) {
            var3.allowDiagonalDescend.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s26x823lhluevf","RYt9Do92IpS8jhP9FX6SKKK0spUJaRZ96HX6wTyHcyBoA/Cnl0/OQZJPcotK8r7jCMNaMeOyAz/2gNfiLJhw3ElVt3g=",8478671782946965850,3641741677386544345,1123984735045335056,-2081315411613232752>()
         )) {
            var3.allowOnlyExposedOres.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s2n0ld85c0ls7t","lcV4n9Xt+X3r3Y8SC4I49VUZKF3VO+mXdHckiDGx0Rospn8vitSL+Bt99TSdFs0tG+/nVsvrgUffCEbPEcblSiodKAEdEViT/WeYbd1ocf8filop",-3432969805363883537,4171949723912717767,7538156308143322769,-8036173379766632058>()
         )) {
            var3.allowOnlyExposedOresDistance.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s1mamm9ug2tpc2","NoSf4GjMRO+WOCYi8CxbFodm3UNMySjHZJ6pwtSql6/GVKpxla1fWqk2NQUfkmoon5sPeTw7r6WR26pq9lu2dmwcKdE=",-7631516108459033557,2900654597258474352,-4417457051509659286,-8350638416442468659>()
         )) {
            var3.minYLevelWhileMining.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"sx62e8qgzwyaj","G/MAay0ola9J9Y1AlVHyKMXqQ9HtA+XpmBqQYDQpcDW0Rvp5sDyPMDKMeyZDggwqKCzjP3x+Z6WwWVKJmDWe50uFYR8=",-4051025645543387247,2658227235271197415,-2065868969108592259,7053898980750659787>()
         )) {
            var3.maxYLevelWhileMining.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s3rvisnefwf781","0pSnETregfsAoG5LYzcmw/iS4LwYMms8kvdfkm1Av+SGauqleKRH7KzacWgv7Gyu+AbS8kTqVFgVeqp7vEh2TVQimtUtcXUOB/NGMw==",-4252690328880163262,1999809147603321799,-3227593033509613803,-5255774922937649304>()
         )) {
            var3.mineMaxOreLocationsCount.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s1ozwcgrxigmcb","DSWDrHMsOXwzxfjzDK1EfKJCKPKjzTVGWfK8y4Zb9KkvSDkAsqCVTjxl8ug336YTvDsW6mK8+FTYeXjvr8nO+G0tx46CGSZBo6dDmdEu",6876477892056637113,-481582063529775717,-8336426638780972928,6051544074930433134>()
         )) {
            var3.blacklistClosestOnFailure.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"suzvzyvpjjnlz","KwXu4PLSWJDP+PqH2aNY88LWJ+IwyKxqpa4J4uyTgX2nEUCXdWoGXGgH7jjMQg==",3702800434213596309,5971569212412987443,10131811747719297,771857520121358676>()
         )) {
            var3.legitMine.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s1vnuyjawjbtwb","ofczv0/0N/aBpJ3NETcerLUxtOCWZTheuzXwHn/erAREWu77KSikoLNrfgt3q/Mk3c2mL2NKCIfBcQ==",1996401459835327405,-619852635710035222,1827827649252621720,229431392831607900>()
         )) {
            var3.legitMineYLevel.value = (Integer)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s1cayammmzlqi3","sXa5txc4IoQHr5H9BIpNHB9qcl8WOsD3PetFpr71YecleJ/J7oqdMZ2VLIykPFjoWfzbW/KPB9khEJa+nZcRe50mmuqCeS2R3NdczErh",2069032040252832096,-6093791684138239575,-6153219833598054865,4275584143971000863>()
         )) {
            var3.legitMineIncludeDiagonals.value = (Boolean)var2;
         } else if (var1.equals(
            (String)com.yiyiaddon.m.b.a<"s1oomml1yxqhwe","WXdehCoF3hiQAItTebE5BCxge/S8+0AbaLL8l4FCjVud6PtpyXyvv+o1ZA5guqNB7sA5pSwyu042AODu2rKSqcSMfqvsbmPI",-7252538135387988727,-8484173439963208404,4697615277908419459,4351434104174485441>()
         )) {
            var3.mineGoalUpdateInterval.value = (Integer)var2;
         }
      } catch (Throwable var5) {
      }
   }

   public void k(List<Block> var1) {
      try {
         Settings var2 = BaritoneAPI.getSettings();
         var2.acceptableThrowawayItems.value = new ArrayList<>(
            var1.stream()
               .map(Block::asItem)
               .filter(
                  var0 -> {
                     if (var0 != Items.AIR) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2xn05fdnv80h9","A5aTOA0Nr2xnFUJWv1DstHggEBLHGpIE9jfPbS3iDyc=",8817021257221607399,-8113111585620556170,-261748623872916033,-3476745947399342642>()) {
                           case 493647247:
                              switch ((int)com.yiyiaddon.m.b.a<"s32tt3ja1t6v3t","U6+6bQh0zmsJOMz+xvqEALwCGCjGyCn68TGvyk4LeoU=",-7110808251858850081,-3877438957642621359,8954570659885118277,2321256393690530005>()) {
                                 case -248686095:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s3cc4tvm0x96su","QCDFtS+s06bs3eMl+G+qmd3zXwxHjZYCQZVCOwl8hwo=",6052614057471222000,4113900173404354604,261306445249584645,-1838642699149923702>()) {
                           case -864626921:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               )
               .toList()
         );
      } catch (Throwable var3) {
      }
   }

   public boolean cn() {
      if (this.I.player == null) {
         return false;
      }

      try {
         IBaritone var1 = this.b();
         if (var1 == null) {
            return false;
         }

         if (!var1.getPathingBehavior().isPathing()) {
            return false;
         }

         this.bi++;
         if (this.bi - this.iY < 3600) {
            return false;
         }

         BlockPos var2 = this.I.player.blockPosition();
         double var3 = Math.sqrt(var2.distSqr(this.t));
         this.iY = this.bi;
         this.t = var2;
         return var3 < 5.0;
      } catch (Throwable var5) {
         return false;
      }
   }

   public boolean w() {
      try {
         IBaritone var1 = this.b();
         return var1 == null ? false : var1.getPathingBehavior().isPathing();
      } catch (Throwable var2) {
         return false;
      }
   }

   public boolean co() {
      try {
         IBaritone var1 = this.b();
         return var1 == null ? false : var1.getCustomGoalProcess().isActive();
      } catch (Throwable var2) {
         return false;
      }
   }

   public IBaritone c() {
      try {
         return BaritoneAPI.getProvider().getPrimaryBaritone();
      } catch (Throwable var2) {
         return null;
      }
   }

   private IBaritone b() {
      try {
         return BaritoneAPI.getProvider().getPrimaryBaritone();
      } catch (Throwable var2) {
         return null;
      }
   }
}
