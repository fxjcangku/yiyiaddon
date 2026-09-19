package com.yiyiaddon.e.n.r;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

final class e {
   private static final int od = 40;
   private final Set<String> aj = new HashSet<>();
   private final Map<String, Integer> av = new HashMap<>();
   private final b g;

   e(b var1) {
      this.g = var1;
   }

   void hs() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s15c1c4d3kajqv","zwjxwdB8BGCswY1vq5DByV9BM1SEF2B4+EYCKNaPa/I=",-6891502267966850862,3514352531207942358,6025476005299759366,4630113125259662206>()) {
            case -2135761528:
               return;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         if (com.yiyiaddon.i.a.a.b() != null) {
            label70:
            switch ((int)com.yiyiaddon.m.b.a<"su9fpn4yqqrld","kkAc1/83Q7BKbwALbA7eoVU2lETB52Q229J1/Ld98UI=",-3715198722618851482,-6256010991957496453,6028406682256203323,5288125423842120933>()) {
               case 1538931726:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s289oj7dgnmirt","tH8ItFF+bGNbXVqI50mVWdxeyzr4O5ZsEQ7wx3+fZfc=",4193058975265209484,31642060603558391,3642442338533843872,-3639616460070225844>()) {
                     case 71491872:
                        break label70;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s93oz0qkx06jv","mbYgY9SW0iTOx2YGbrthu1mc7KUH+RBGb2OgFcLUqKw=",2628908676580150680,8614872518561208578,4831397078768170086,8832849881651362947>()) {
               case -1641688891:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2 = var10000;
         com.yiyiaddon.e.n.h.d[] var3 = com.yiyiaddon.e.n.h.d.values();
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2zah3gltdwhqf","hnQhfnwxi/JmAjpkKnAFQpcwtElnUvh13uSeE/a33EE=",-4829448994338408232,-4286948817548167418,1976753162713304303,-3808492403082464066>()) {
            case -1726933078:
               while (var5 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s26n6whf9jq7j3","wNICptR4MEr3vIpo32FBaa/mf/Pn/PQZfiwr2ZV9tUg=",-6813185172424162277,-4325309413192052544,5522390668067757967,-7143067751900959285>()) {
                     case -415494565:
                        com.yiyiaddon.e.n.h.d var6 = var3[var5];
                        if (var6 != com.yiyiaddon.e.n.h.d.SPRINKLER) {
                           com.yiyiaddon.e.n.h.c.a var9 = this.g.d.a(var6);
                           if (var9 != null) {
                              label47:
                              switch ((int)com.yiyiaddon.m.b.a<"sjct2npb4d9dr","DFjSE2K7IRuXy7S3LeBjETUZ8L1TFmk8yGFiAkAX0gk=",-484112611471306868,8343602982406520849,-533838546266654187,-193075626352150695>()) {
                                 case 1555617099:
                                    this.a(var1, var6, var9, var2);
                                    switch ((int)com.yiyiaddon.m.b.a<"s2notvyy2axjak","Aw/K5daH6APYidTJHPYNMGUlJn3+OlTfmMpONP42itg=",8519349950608405721,2738213975450144941,-3185438829053847904,6995502904224318139>()) {
                                       case -313949707:
                                          break label47;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        } else {
                           label51:
                           switch ((int)com.yiyiaddon.m.b.a<"s2pi9cu5neczxj","SGukppk/pvsdhoGxN+Ctc6SSM/TIwn0gdYz9pf0O188=",6498087060565833556,-4666959203231754679,-451414585233423493,-5502342396682409007>()) {
                              case 11039869:
                                 Iterator var7 = this.g.d.b(var6).iterator();
                                 switch ((int)com.yiyiaddon.m.b.a<"sklivcbgr8wyv","saWitC1lX5bjvLv+RTjsukWT7lJOaowLOHOadNZ71pk=",4096383542244916082,-3256900542451755069,8584860487862099662,3963694618262063311>()) {
                                    case 117814403:
                                       while (var7.hasNext()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1aknr8h3kcbk5","f0Fhmvza+KkpYzablwuqZguwrRZVbL7b3k5qQg0FN44=",-7145319672390896650,-7119086210604406449,-1063067417109985048,1123457309608435291>()) {
                                             case -1892339831:
                                                com.yiyiaddon.e.n.h.c.a var8 = (com.yiyiaddon.e.n.h.c.a)var7.next();
                                                this.a(var1, var6, var8, var2);
                                                switch ((int)com.yiyiaddon.m.b.a<"s35xizdsca9emf","1bgnCIeewI3ktyEdXTH0k20uv7KGAErLnCrb4Hx84zE=",2306816798012200668,1348260913381075893,-1218104366684711617,-6231483364577440871>()) {
                                                   case -815158946:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s1e9q7m65sqeoy","nLG8eZPZgYsP42p9gBPFi0RwGfP83exOPeCK03Fm54k=",-1374218563569348364,8447640330704247846,5918808181362665914,-3417011357243765196>()) {
                                          case -460608524:
                                             break label51;
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

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"sjepk40gfi382","wrAVqeYdlu7WDSMYteymXDQfevj+GdZefluNBrna2nk=",7968803334673242032,5822417599225368987,3808399358244144651,-7650771044329689057>()) {
                           case -1086923471:
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
   }

   void ht() {
      this.aj.clear();
      this.av.clear();
   }

   private void a(Minecraft var1, com.yiyiaddon.e.n.h.d var2, com.yiyiaddon.e.n.h.c.a var3, boolean var4) {
      String var5 = "" + var2 + var3.a();
      if (!var4) {
         switch ((int)com.yiyiaddon.m.b.a<"s34egw5cyn70en","OBIOBWVx45eBvCk6Zu9d4L8NwojQpsMjIr3u7Ue68oU=",6640453209319954238,3350237273325684156,4945706994432488245,-3166156795105642587>()) {
            case 1052588262:
               if (var3.G()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2byu439opaboz","x9yFYLz7FDs+yvTDO5gOFdFYSNw+jb8z+yIHbt8bYgA=",373919728570369567,-2834132926032813045,-1995944057363571957,2666637550278646882>()) {
                     case -1182068746:
                        if (var1.level != null) {
                           label76:
                           switch ((int)com.yiyiaddon.m.b.a<"s2hrlli0devgg9","VdoA5rOwBE87J57QIuev+ajzF7jaBw8nVidYdkca8Z0=",5024455040012502865,8452359908381478651,5865036144559302307,2559247677443423073>()) {
                              case -277778908:
                                 if (var1.level.isLoaded(var3.a())) {
                                    BlockState var6;
                                    boolean var7;
                                    boolean var10000;
                                    label88: {
                                       var6 = var1.level.getBlockState(var3.a());
                                       var7 = var6.isAir();
                                       if (!var7) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s88a7j55pn7qs","+vFNKbyfIywGqbbGbtaMr5YacFuzffZ5/+Gc8TqY/WY=",-6051596753207986004,2770229872507978706,-9079077776157218736,-7672466487689085042>()) {
                                             case -1871024977:
                                                if (var2.H()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sl6d5e4iiu9bs","gnLo4J8zRsJLCe61I5EE5JvcKrxgXdMU7kgtHu3E534=",-7742817300087705271,-7408005669871500739,-162251156949884676,-1439014037036955178>()) {
                                                      case 1497639703:
                                                         if (!(var1.level.getBlockEntity(var3.a()) instanceof Container)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"svhokue4wta5","8FEY5Zsy5VcChcGtipEzT5LK/txpGx48X/dQZIIjpuo=",6207971481969680579,-2460271994813352391,1155952576957265404,3121574392994160311>()) {
                                                               case -1442672109:
                                                                  var10000 = true;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2j7927nl7mv76","6X6lKioF5C3YYtsMLyZOb5TsiuwEGxNR3o60Dx2lm0o=",-2174407026356984620,2962118045163346117,-7527123150494384696,1541940877969409596>()) {
                                                                     case 1548598797:
                                                                        break label88;
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

                                       var10000 = false;
                                       switch ((int)com.yiyiaddon.m.b.a<"s27laaxkmnv7nt","A6e10NtYVTb3w0B+OmnfPyftuNDP+XWRUtbQhM9O7sY=",-7418047015093303508,-2678381679828556390,8321642072437591999,438384076725515106>()) {
                                          case -386757421:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    boolean var8 = var10000;
                                    if (!var7) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sm3pwmbaiyl1n","OyXGwvMq0IHloJWwUO6tivBSVAO55OW3d5oPaYGF638=",4720162662571937919,-2048077999518574684,395009497397897029,7270838461509755675>()) {
                                          case 1117131367:
                                             if (!var8) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s6w7kmcidxw5u","jD1s7fhchaVIafvlvtyuIr/COBWJz55DeyFozTLshL4=",1416165354340036409,-6536656768581402931,8424726659192972803,560364311287758763>()) {
                                                   case 1376107134:
                                                      this.ay(var5);
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

                                    if (this.av.merge(var5, 1, Integer::sum) < 40) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2jguk5p4sfzhc","6kBy+ydZl6OAfcuJjiOAO4jipPwkmEvMiUYIYPdYwGw=",8304840135606534710,5435696241815452626,2174557339996058464,5241541744324933502>()) {
                                          case -619064685:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (!this.aj.add(var5)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"ssepcxmow0ce3","aAdfnfrjHt+2cUdE9J1j+UIjuvrGcLNexDtjDy+LGDU=",9010556316974545403,-3119173435836803770,8355620456576299078,6867686337096231400>()) {
                                          case -893663454:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    String var9 = "" + var3.aj() + var3.ak() + var3.al();
                                    if (var7) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sbkiu3ukzsyfi","R2FjOWWnBnE4ycImhEheJunfSUK1be1AJPWYWMFHNSk=",-8734524429462228093,4126666407633890886,-8736263158379036662,-1463097862385535390>()) {
                                          case 393785855:
                                             this.g.c.d(var5, var2.D() + "", var9 + "");
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.g.c.c(var5, var2.D() + "", var9 + a(var6));
                                    return;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s21o37d96xvl0q","8HdVFDPjF2A6xiiQNnrTNx+ysuXrveiyIlfGYUebb4s=",3253958616084400505,1916304081365891513,6243822202326946294,-5740818056527127615>()) {
                                    case -1221431528:
                                       break label76;
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

      this.ay(var5);
   }

   private void ay(String var1) {
      this.av.remove(var1);
      this.aj.remove(var1);
   }

   private static String a(BlockState var0) {
      Identifier var1 = BuiltInRegistries.BLOCK.getKey(var0.getBlock());
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s18r28w6rsm2mp","vuoxMMkzypfTI69Y6GQnF28h8KgeRnODcPcfWptLddk=",3129216086187881270,3537864578689543739,3682196258179412291,8551138798758362791>()) {
            case 1986063710:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s3sgh90gqhn7ts","7cWc7tfxus5LI9L//DNpD4f7yGqZBNCMfHjhm51hiRV/sTTt",6342796585264375512,-1711439901133587781,-3215644508941103676,4396052651608441494>();
               switch ((int)com.yiyiaddon.m.b.a<"s13slm0bo264hi","ggPmDP7i08zdBvTujpgMZTg4w25ejU3QjlwqPfaDQvI=",-2718276573742369836,5537006592803460833,-230757840165284009,4485474983887564933>()) {
                  case -2089656060:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = var1.toString();
         switch ((int)com.yiyiaddon.m.b.a<"s2zk7zof5lmif6","BChcy3+UbG2PynVAMspEU/jxVTJ1fV/d+tI755H4hOs=",-7273686852406578263,1503183796748733149,2112309746577808726,9022489900344377069>()) {
            case -649594634:
               return var2;
            default:
               throw null;
         }
      }
   }

   void az(String var1) {
      this.g.dX = false;
      this.hu();
   }

   void hu() {
      com.yiyiaddon.e.n.i.a var1 = this.a();
      String var10000;
      if (var1 == null) {
         label131:
         switch ((int)com.yiyiaddon.m.b.a<"si0gnq2ua0zat","G+Igl9LwiTvmggFFTPrt53YLR2MSO9yVq43A1Xpi9aw=",345273781202011188,3376892197101522480,-5853494752691762101,-7116429545242296657>()) {
            case 2138252987:
               var10000 = (String)com.yiyiaddon.m.b.a<"s10jgtqsusoiwj","m2WDrJtvdqYm6BUcIcAZEs8G2K04Xy7Cv2qksQ==",-2776792585590732125,1953463191459470986,9209091523292976007,4686190153320774920>();
               switch ((int)com.yiyiaddon.m.b.a<"s3v2i7qstvc0ys","BUxrmFOjN9OuBZRivvXVrTz9IdWd8X/axssu1l4DBmk=",1164079766068365742,1235607671048868167,2675319888136318233,1136392613956622668>()) {
                  case 318303317:
                     break label131;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1.dA();
         switch ((int)com.yiyiaddon.m.b.a<"s2gvjbnceygcgh","3bH8TMrqBESb11e6U7oIeT3+4CJFwcpgAorG/VVqspI=",7030484543048112551,-3007902580430452138,712069560026490750,-7530622581778040195>()) {
            case -1168354028:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      label126:
      switch (this.g.a) {
         case WATER:
            com.yiyiaddon.e.n.j.e var6 = this.g.a.b();
            if (var6.h() != null) {
               label76:
               switch ((int)com.yiyiaddon.m.b.a<"s1m216dm4z9pw5","m2XO0HB5lIGyQ+nAaL4CEkvf+5gO6tbYz9ler+upPao=",-5709192030805969628,-4961720340659287728,-6213066288903468271,-2006167118084658375>()) {
                  case -1428608797:
                     this.g
                        .c
                        .c(
                           (String)com.yiyiaddon.m.b.a<"s2awc82bfrt1ef","afuLjL6hoQz2tb/N8HJtBGgAM8GlK1VOPcP1M/RZkl/wNF/5TCI=",-8484615433720626768,8395235053627626732,-5665064918510905967,-8504804908175640287>(),
                           var6.eb() + "",
                           this.cJ() + ""
                        );
                     switch ((int)com.yiyiaddon.m.b.a<"s2tw8bfgfbzgtv","ySsFm8IpaS5kgbMi34/Cz2Fssz8spPnaxoVKrdB0M7M=",2259110150506718592,82739532997383080,-6129993327446347806,-3175712972300785390>()) {
                        case 1774991776:
                           break label76;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.g
                  .c
                  .c(
                     (String)com.yiyiaddon.m.b.a<"s2awc82bfrt1ef","afuLjL6hoQz2tb/N8HJtBGgAM8GlK1VOPcP1M/RZkl/wNF/5TCI=",-8484615433720626768,8395235053627626732,-5665064918510905967,-8504804908175640287>(),
                     (String)com.yiyiaddon.m.b.a<"s3o4xqi39brlgt","USH8vd/1LfirukVVDlgImUnZV25jizhUhrPirep72zes8sCh",335393607765655864,-9186707993234040480,-2150165951219662940,-2271332955763646212>(),
                     this.cJ() + ""
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s280z8h7a2fg6b","zdgLUsFqBYPTZm1XdeIme6RjCEQhDacKyB7BLvoR5Ls=",1167014637120696361,1074788668085328350,-764652756373186665,8284317715459502986>()) {
                  case -1530927276:
                     break;
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s1qgy6rbis7wv2","IreA4c74KpLPhycGfTKdEoJm7PqGUMNYTPQipUk0TY4=",5775729826919360971,5911511847182838177,3143462996133168123,3591475627444747812>()) {
               case 741422671:
                  break label126;
               default:
                  throw null;
            }
         case REFILL:
            com.yiyiaddon.e.n.j.e var5 = this.g.a.b();
            if (var5.h() != null) {
               label111:
               switch ((int)com.yiyiaddon.m.b.a<"s21dbo83pf3hb","VGz6z3nrDoykF/kvyJEfzCSOPtFtpPHPayzSCCYwUC8=",3688085855430019854,9065274044449712591,-1149078399968378697,865374681399116091>()) {
                  case -553224824:
                     if (var5 == com.yiyiaddon.e.n.j.e.NETHER) {
                        label106:
                        switch ((int)com.yiyiaddon.m.b.a<"s3l5od8fvdslnc","DpfDUnWUZlnYtBrIyH0bot8FVywZTjKdHqffth6ipNA=",7717777268183575903,-8743773892490852641,4415888280426417811,-3172818633585282152>()) {
                           case -738865323:
                              var10000 = (String)com.yiyiaddon.m.b.a<"s267e35vx9oeyn","AuAlcCUna4vO3+F1NuedhE54fqEmc01oF97f9KkMRdmbvw==",7627312133165615137,-7250347573490869978,-2662416908637329192,6697476821815533186>();
                              switch ((int)com.yiyiaddon.m.b.a<"s31raa5qkbot3n","OkF6SG/e44XqK+RF6QCkvbQ+auD3Q375A1RW1eGc2fE=",-6330279601120364674,-7276567719768107487,9171463015340619143,8759054667376613007>()) {
                                 case 41092406:
                                    break label106;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = (String)com.yiyiaddon.m.b.a<"s23cwl2ujgznwn","RNnZ/UZVptUYSRW3ocS46MV8eULuUBtHGF2o09kHymWkxg==",-3388824099394638844,-7097597509133977770,4722649281317292536,-5312952063838649749>();
                        switch ((int)com.yiyiaddon.m.b.a<"s3qo4h4ok1musu","BQPiOqWqIRIGEbj9zU+e22WtwHg4xP75GIRhFGa7szc=",-5984434078261548947,-6337828679300685104,-8326232499430952042,5267235303108134369>()) {
                           case 1031863965:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var4 = var10000;
                     this.g
                        .c
                        .c(
                           (String)com.yiyiaddon.m.b.a<"s2o7xgrfscm9cn","6NEM9I/jm+d+lshYRvMRS1GbSqFTne1z5ypiDR9ERZ6bUY5hBr4anA==",9039118636237842914,2579731140275767355,-2999129007635891387,-2737514592434466336>(),
                           var5.eb() + "",
                           var4 + ""
                        );
                     switch ((int)com.yiyiaddon.m.b.a<"s2vmodd0d152fx","PgeFtExp6Gflrh0qbc45/wk1w+5Vuh2DFKpfCo3CWbo=",5581772867128066029,6175483160751485637,1892378961575541498,-3735379159629842712>()) {
                        case 2128011937:
                           break label111;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.g
                  .c
                  .c(
                     (String)com.yiyiaddon.m.b.a<"s2o7xgrfscm9cn","6NEM9I/jm+d+lshYRvMRS1GbSqFTne1z5ypiDR9ERZ6bUY5hBr4anA==",9039118636237842914,2579731140275767355,-2999129007635891387,-2737514592434466336>(),
                     (String)com.yiyiaddon.m.b.a<"s37nm9as2h8y0s","WLOTKNZ7EUsId9AtOOlwJOVAeoECJV2ble8ix3Nvr1WK2PhO",360133564625531720,4759713775288372736,1784014024099620613,1488993394536576612>(),
                     (String)com.yiyiaddon.m.b.a<"s2j0lwx4nzkno","WBmp71nPXo/4YCTkGRphsyV5t4Pd6p96vPP/CT2E8lXgW/GqhJDpvw==",-2761405628117425410,2740249481156884689,7086656095273694513,2105671628460559493>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s2w22g659mupfo","YT6O7xl2UU0JA9rAtlY524SHf2p3yKxm+Q8rdzWWClQ=",1600924160451168263,4083330994430806412,8834606624689086,3952316449755526169>()) {
                  case -771771031:
                     break;
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"sepdoofawb52","Z1GFL3r+R9FNeab24dYb6F5bpKbA2OE8MtwpZ/O4hEY=",7513005674974817856,-7083942280792914026,-6557354123130569416,4877158716951262311>()) {
               case -1926678280:
                  break label126;
               default:
                  throw null;
            }
         case PLANT:
            this.g
               .c
               .c(
                  var2 + "",
                  (String)com.yiyiaddon.m.b.a<"s1aybvzuudzzlq","j+7pSnnpv3/NTHDydXdCYjy97h2QrCFFENmexvo+qonKH/Xz",6728937473115545122,-3730321687624141667,3479483052672137135,8434208310042419525>(),
                  var2
               );
            switch ((int)com.yiyiaddon.m.b.a<"sbmc7sduxqu53","nPPrRFP7h7bPxumYT4haEuYHKTVVbAEfyNNmVm2cHVU=",-8935416264218016865,-4832773625068056486,1209030254239696306,2530773606080455101>()) {
               case 1634859690:
                  break label126;
               default:
                  throw null;
            }
         case RESTOCK:
            com.yiyiaddon.e.n.q.b var7 = this.g.c;
            String var10001 = var2 + "";
            String var10002 = (String)com.yiyiaddon.m.b.a<"s1wg9vwsmlm4d8","lkzvlNRuN2Rdq6szw/ijn90OujS1WD4KGxY54qtK9j5kcuNg",-6537503954236742759,8048137834381372897,6038489222576035113,5835356442023250968>();
            String var10003;
            if (var1 == null) {
               label85:
               switch ((int)com.yiyiaddon.m.b.a<"s1q5t1oc6ikzgi","5tctacSoIPStleMmOQiRNGdaAtA9DHiACy9hVR840Kc=",4355116729258463643,4832894628929423884,437183142805190015,-139525058589765654>()) {
                  case -114670227:
                     var10003 = (String)com.yiyiaddon.m.b.a<"s6nvj73cjy2o0","bfn/xYXvUhBLu4TdzKQwXjK7HNeiGBqKYNwIZYiRFGEzWsbl",-7246551058411077357,3072157059815599055,-1266836404444833469,3277512244982840609>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2dpieprv71r6s","I4rASuCAoBY4lsojIY7lepQLG7vofpoHaZE94MSSXw8=",-4300139359271744769,7461414566115672731,-5904106754001058361,607129233684890920>()) {
                        case 358105728:
                           break label85;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10003 = var1.dz();
               switch ((int)com.yiyiaddon.m.b.a<"s23coyinnn4p1e","aDw5b665QczBd+QOxemkX175Aafh8OS5RewFCuSEDsI=",1150185227525432404,-8165523907981154015,4210829932985829210,-7652668554289612477>()) {
                  case -1933027027:
                     break;
                  default:
                     throw null;
               }
            }

            var7.c(var10001, var10002, var10003);
            switch ((int)com.yiyiaddon.m.b.a<"s3akc1xl5d12j6","bS5mSUKtxpwiYeBRkjM/wJ+kp5t7uvRnLsmxb6+aPLo=",-474635878901430068,-3396207906684838811,5578032135750577105,-7758327370653289862>()) {
               case -2062782629:
                  break label126;
               default:
                  throw null;
            }
         case HARVEST:
         case LEARN_HARVEST:
            this.g
               .c
               .c(
                  var2 + "",
                  (String)com.yiyiaddon.m.b.a<"s1xmi1t1laadqe","UuYsv6fj6C0scpVlqytCSYVcQ2rFPTx6LpfBVDMC/OsDKaXr",-2324437641746856852,-4954192536921323109,-2596205852699774813,-7479780817838092>(),
                  var2
               );
            switch ((int)com.yiyiaddon.m.b.a<"s2sdf423yra0x0","HABIQWM4f0fUkORsQ4p5Eq8STF2QwwV8Wes4zxOcgvw=",2051789411501082686,-6793219834358450953,-1522006078038074886,6779478627090510274>()) {
               case 1789914094:
                  break label126;
               default:
                  throw null;
            }
         case COLLECT:
            this.g
               .c
               .c(
                  (String)com.yiyiaddon.m.b.a<"stkzvqw2xlxdt","Flhe7zFqozDrpZyQ4ytgZgEhCDmCYMAW+VmYwx6TZPshjMx+PJi9JofN",-7271947388460224424,2543753298903532070,3430540484693447943,-2700731393271981436>(),
                  (String)com.yiyiaddon.m.b.a<"s2hl67g8qxkwtr","SaI9/C6CDyRviYE03jr+k2t0/fJnKlN9Ukpz6no55qzi+M0W",6293334633456585399,-8348000268880387204,-5024442789045109985,-973218571170279900>(),
                  (String)com.yiyiaddon.m.b.a<"s1qxqli9veksgc","IWX65RMK/JuLtg3ivLchFESeDZk1zT6r8YKAnEoPPqhn/rN+gFc=",-4443646474197201187,-8880797712622379337,-1043393112855251234,7797473280793139833>()
               );
            switch ((int)com.yiyiaddon.m.b.a<"s333a5rw1499t3","kO+Xk+rVHcNuBuzuPHs7WybRxLMf/BOaWiMLTDVpBvI=",-709899450913225298,2013771537005331311,-3817418666599341122,-988598034145312171>()) {
               case 342895921:
                  break label126;
               default:
                  throw null;
            }
         case UNLOAD:
            this.g
               .c
               .c(
                  var2 + "",
                  (String)com.yiyiaddon.m.b.a<"s4bojx1h8696z","Cpm8Ac4Rz045iRvw6nVf3ltvqUdSLcDMlH5sxOZayxFAPGnt",8951412478660764020,200799642933926037,-8328829582238498184,-6728558996186569759>(),
                  this.bc(var2)
               );
            switch ((int)com.yiyiaddon.m.b.a<"s1soyq5f1br30w","a8BAm/Mu8NcHTCAvVO5uzmkkJ+JZxaq9mRp/rYgdOG8=",8405271630016398208,-8795412639332922237,-6841978221149263666,1963529999965130492>()) {
               case -231318193:
                  break label126;
               default:
                  throw null;
            }
         case CLEAR_DEAD:
            this.g
               .c
               .c(
                  (String)com.yiyiaddon.m.b.a<"seqvo70qnaq2c","xQNi1439HBjtYNc7xykrrp4Jo+v+Om0Y/muzmmUuxGgUHDW51dUeCJ15e2Reg79D",3063741927726539034,2905845507331726278,9051275704739422410,-474951525486499348>(),
                  (String)com.yiyiaddon.m.b.a<"s1yua3666hs2nn","f1GJVdxYEbbRZlaIHLgT5RrTPMapMOps2CowmKIwZ0ivayoV8T00MA==",-5187154188271915955,-1176447155378489639,3846370468301537001,1773795419770751957>(),
                  (String)com.yiyiaddon.m.b.a<"s6zyytbf7nb86","QUsZcSOsVSn1OEo1KAXfy45UovtsW0eE2tOw1VN4txExG9Ms",105064269711615707,8406694404896777675,6720790564919632646,-629140088834523954>()
               );
            switch ((int)com.yiyiaddon.m.b.a<"s16a3y787w5pzt","8dZlM6kn6K/V/7lQR3ekthOln4d+N+nb+Zf4OjaQ4Io=",5019371825120461061,-2952457935981533180,-6876923513158992947,1778729197265578530>()) {
               case -1882067376:
                  break label126;
               default:
                  throw null;
            }
         case CLEAR_MISMATCH:
            this.g
               .c
               .c(
                  (String)com.yiyiaddon.m.b.a<"s12lfpv75b1da3","txztl7PmNjJDzT0n8JPsn8zipVeQS2DrugrWbBTZxSDk8GT+FiMAr93tAs7tAZrG+nlQwCgrvsQ=",-7415073231328704997,5833470760479289706,-6257065028502954003,-4625501664241739054>(),
                  (String)com.yiyiaddon.m.b.a<"s1kj7k30vsy2hq","3jQFIjJhJDfDP46aTvVrOFJOzCu/Q7rT9MHkKmBA4rl9CKxZC3znbQ==",-1841691578367670331,726977307481855125,2466178182404334879,9098332749594163628>(),
                  (String)com.yiyiaddon.m.b.a<"s6zyytbf7nb86","QUsZcSOsVSn1OEo1KAXfy45UovtsW0eE2tOw1VN4txExG9Ms",105064269711615707,8406694404896777675,6720790564919632646,-629140088834523954>()
               );
            switch ((int)com.yiyiaddon.m.b.a<"s3bou0i7cclrwc","37ElQ8/Hcq2XIVMu5PlvGLwiGvzYORopVSWcHAQjG/g=",2214852401896177728,-5909318531451995126,-4575596783640514885,-5547684623013401773>()) {
               case -1829015249:
                  break label126;
               default:
                  throw null;
            }
         case CLEAR_JUNK:
            this.g
               .c
               .b(
                  (String)com.yiyiaddon.m.b.a<"s39t7a1y8civgd","Pp59q1tsfXqkYSB2LSIAc7fJfX60l5okRJ4bA2VZfMME1mCAMhjjYMrU/+kgKllH",4249195143750549429,917478681763068371,-2066354265554496178,-5283863365513230971>(),
                  (String)com.yiyiaddon.m.b.a<"sl2a0s2ff7cgx","NAfFNd+SB/9F6hIJNVgVeINpew+GCN6sOsDNSH62MiBLKR9OBiLhLw==",-2327673304503354416,-1123827173110112076,1838582596821651914,3117913110097002305>(),
                  (String)com.yiyiaddon.m.b.a<"s6zyytbf7nb86","QUsZcSOsVSn1OEo1KAXfy45UovtsW0eE2tOw1VN4txExG9Ms",105064269711615707,8406694404896777675,6720790564919632646,-629140088834523954>(),
                  (String)com.yiyiaddon.m.b.a<"s10jgtqsusoiwj","m2WDrJtvdqYm6BUcIcAZEs8G2K04Xy7Cv2qksQ==",-2776792585590732125,1953463191459470986,9209091523292976007,4686190153320774920>()
               );
            switch ((int)com.yiyiaddon.m.b.a<"s1ucp5tez5uzti","No1dasQbjUup/pMApWs+YERyek4Dd+20rsWUw8fWhmw=",-8770036270328008280,1660172440792918442,1954279519528750447,3138087800303222804>()) {
               case -1260892987:
                  break label126;
               default:
                  throw null;
            }
         case RETURN_CENTER:
            this.g
               .c
               .c(
                  (String)com.yiyiaddon.m.b.a<"s16sh2msvbu2dx","tUqp01uyYClZ1BZAwmZvF3lan6oi38N9S7oTmLDF6oyEXxxEru3TUA==",276180057896694165,7616326845731552244,5664419118652436389,-2438606095142238780>(),
                  (String)com.yiyiaddon.m.b.a<"s37hldvd8bvfsn","6Xn6R0Hf8/rhHevvXe3cqVnkX/p663di1t3ASZIFJveKapMi",-4218287376408163660,-4516057549292253985,2363216615499358344,-6047559199812803021>(),
                  (String)com.yiyiaddon.m.b.a<"s2hj14erylvcav","OErrGxPFsqJwpLL45YfBKB9vcFbsQXi6m3cxO3UBPd2Aw8jK9ydcZcV9ydo=",-1476625382286669375,2136020816272578727,1199531356116367648,8231060529298327080>()
               );
            switch ((int)com.yiyiaddon.m.b.a<"sjwkdebk7tt2b","sa5SD1PkOgZCSVskr7i5gojOijcw0C4PAhfvOn2Epfw=",-4441480812276168267,2507313544280550797,1894618274405076002,6927289509502823772>()) {
               case -1703819006:
                  break label126;
               default:
                  throw null;
            }
         case FERTILIZE:
            this.g
               .c
               .c(
                  (String)com.yiyiaddon.m.b.a<"s38gh9455078kk","ytRDmOXtRwR40q9yLFga7lFC4BvCq9qlIHOnVfOC/drYRdxQmT2GWmuzHFO/4w==",-4788303507745387313,-4069731592127556476,-3074560665642527377,-841050905493619394>(),
                  (String)com.yiyiaddon.m.b.a<"s2a2zvjrdp0d2b","jR2cqW6UeZ2It1jQFwVUx8BQTllEf9PL3Ei6SAhIuh2F6KNy",-6767488645127877341,2151594289403100929,-1320680301783362369,8032587909684225835>(),
                  var2
               );
            switch ((int)com.yiyiaddon.m.b.a<"s3qbvzoti0ksib","X239WtQ0blBL6DObqnRz7GKqsbMwPalt1ljUe6SGbD4=",-2495816660483917601,6098150434169248982,8185778275678717653,2582603132806523184>()) {
               case -3227016:
                  break label126;
               default:
                  throw null;
            }
         case POTION:
            this.g
               .c
               .c(
                  (String)com.yiyiaddon.m.b.a<"s2w0v7wunmqqgs","zC5BwfmPTXUVCDQ+GnSbfW/sOnlFIJJoazDD9knHtCfLkzRJFrZoWw==",2148161242942223641,3759548606848250446,-8648439427857075756,7631430577965910259>(),
                  (String)com.yiyiaddon.m.b.a<"s3akorxgrl1j72","IXMGtweFDl7KmeFK6MI0BzhQfxY3qieac3c2eHvLrGPFZjb3hPSMT98soy8=",-5446378850759175828,-1969460542117689317,6558007915556375409,5449612116290807717>(),
                  var2
               );
            switch ((int)com.yiyiaddon.m.b.a<"s2m6ocz1wjneyl","jySR6q5TZiyB4zJcknP16ohJ8JPzplmrzlYu2+rzsfg=",1174374503362945155,-787112259180751403,7139405099487153024,3431622441734574265>()) {
               case 1265899784:
                  break label126;
               default:
                  throw null;
            }
         case SPRINKLER_CHECK:
         case SPRINKLER_REFILL:
            BlockPos var3 = this.g.a.y();
            this.g
               .c
               .c(
                  var3 + "",
                  (String)com.yiyiaddon.m.b.a<"s3o12cjny6a2gr","bKcJZOsObOF+g4EJA9KmmWSJF7ajtmj3FHhwgPA7I2gyQrcjQ7MHvXru",2611228417461032216,-3321216958485428329,-6362999122902698628,-711333740826610926>(),
                  this.i(var3)
               );
            switch ((int)com.yiyiaddon.m.b.a<"sjsx1c1g7z09x","Pi+A51IplseUXKQFqbIe/jprNPp1uaIS5VUNEJ4FCeA=",8178775842334650476,5584226323277764047,7890854569513667327,-5055675943698260284>()) {
               case -2119499084:
                  break;
               default:
                  throw null;
            }
      }

      this.hv();
   }

   private String bc(String var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = this.g.a.bk().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1csvood2nqhx","lXmXlGE6BT2Vfbuzb059TI4GhgbSb5y8tMR8a9D8iWU=",-646899393839727004,-6481921618220241742,8962060563576759537,7218301564923321497>()) {
         case 1684758839:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2q3xldgbodmdc","/3STQXYAYzX/QUJg8RYst/xkX1y0ic4CYKTBc2/iQjI=",8393058709427884752,-3508859730885783326,2570787796096468969,-9114484272713256782>()) {
                  case -236105280:
                     com.yiyiaddon.e.n.i.a var4 = (com.yiyiaddon.e.n.i.a)var3.next();
                     if (this.g.a != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1vzilnwm7zr89","IdJefRFUKXjnV3sytRVfNBZxrstoN7PSASfuiVNkAhU=",3555562259356891579,-6337913030478894621,-1890584628338663932,-2152562193143712645>()) {
                           case -249810036:
                              if (this.g.a.b(var4) <= 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s7ubk1s4dggcl","mlskLHp54oghiMvVCYU5F6kKwddvPJiT3aA5Pt2+574=",-2695689044506977560,8403462753264285374,-5705293036218023755,-6323066045938460923>()) {
                                    case -417964491:
                                       switch ((int)com.yiyiaddon.m.b.a<"s32o7js08yjc6x","b9Ae8gcUhhckKJgif44sr9IdSDHVP5Z8oW/rC5FKTP8=",-6856183543628187744,7109060478694939434,-4226274864369984159,-2647193291851050528>()) {
                                          case 2103622385:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (!var2.isEmpty()) {
                                    label47:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3n0crammju4xc","X4o67LdPhClOXusSpukV+2KqIu0kGsgG/AZBxYOvddg=",-2721194172437299950,1604195706842327031,1541968593917500566,7249053865484719498>()) {
                                       case 219506789:
                                          var2.append(
                                             (String)com.yiyiaddon.m.b.a<"su4zz9dka7mf0","/nojQ6/mqRWNTyN00Idgw1OFoN+vOzFmcum5ICB1",5269104777649395953,7391151829424821750,7210787564236404213,-2219229767982872791>()
                                          );
                                          switch ((int)com.yiyiaddon.m.b.a<"s3fevhs2jntv3k","+OWzlkngulcz9LckvBl2OAkuhHz/SP9T4j+Ijjoalt4=",-3569204473277566922,-8069627834012598857,1157397297797205837,5163992848315432756>()) {
                                             case -449400666:
                                                break label47;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var2.append(var4.dA());
                                 switch ((int)com.yiyiaddon.m.b.a<"sk2lsrkymawhp","AJewvMxQuEtM8/mdaQa8OXFLEWEuuowMp/WynNmTXSM=",-4340172501101375800,-3641081882526024391,-2145340783793633273,2117225251779835844>()) {
                                    case -567630965:
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

            if (var2.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"sgjeotfnshxb7","Trjo/pIYT0uuP++hSBbjOf3nwE0Zy6XDAe0ZflBack8=",4429698453419293701,-3465499936618824604,-8125675295696342126,-2152020797951315280>()) {
                  case 470241011:
                     switch ((int)com.yiyiaddon.m.b.a<"sha1ptjfoss7i","b+hxlyw/IaqjxZcuCPJZhfE+kAjW3tv4Sqq65ilCsJw=",-820592867753058403,-1384761898031803059,1378855974400790903,-5587434453317881270>()) {
                        case -1856256982:
                           return var1;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               String var10000 = var2.toString();
               switch ((int)com.yiyiaddon.m.b.a<"s3ql4kp77knhx9","0l+5Cb+6YICXuNbDAB3oUfe+uLTVABCaE4esB5sGNpc=",-13525464519817471,-7056058678873225708,2310027248345650697,-8769627813033119678>()) {
                  case 1273716371:
                     return var10000;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   private String i(BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s279st259gmbz5","8QtGZvaw74TShfD5eSmOKAlPeO5qxR2FCZ2SBn8OYqs=",5359866825376481772,1073333364908165825,1145209693885224238,184760211901391631>()) {
            case 883494703:
               return (String)com.yiyiaddon.m.b.a<"s10jgtqsusoiwj","m2WDrJtvdqYm6BUcIcAZEs8G2K04Xy7Cv2qksQ==",-2776792585590732125,1953463191459470986,9209091523292976007,4686190153320774920>();
            default:
               throw null;
         }
      } else {
         String var2 = "" + var1.getX() + var1.getY() + var1.getZ();
         Iterator var3 = this.g.d.b(com.yiyiaddon.e.n.h.d.SPRINKLER).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2x8y1evin9b9z","vkSQSgNj+Abi++CkjmEjEWABSlOHWxopz3KmBxGGlPs=",-1175765281653481597,7110898674611106311,3898220242623188827,-2977959811253738916>()) {
            case -1519687563:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s33j0p8r0ffghb","bVlcDsW6P/05KT4fRpV6DzIDpzzTZ2ChpFXJ94KleOo=",5049251889613830183,704290483819910921,-8934269093553480216,4777132387852662686>()) {
                     case -449449098:
                        com.yiyiaddon.e.n.h.c.a var4 = (com.yiyiaddon.e.n.h.c.a)var3.next();
                        if (var4.G()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1tzmn8orm4t66","o6aPJk/S/+2rZydOI61XkAq8lLLNJj/ko5EzRmd+EQQ=",6537188318547394490,-8294785013715579241,-724118792694026896,-7991231034003398659>()) {
                              case -1760113796:
                                 if (var4.a().equals(var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3iosktbmtn531","fOAPT+Nds46mEAutK/DEZMjk80oqL8HEZWBpmq2Adpc=",6575138832682970703,3921016262165799606,4570959784009789781,-5279782626320037470>()) {
                                       case -1723277306:
                                          if (var4.dw() == null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2ysda3eqakyft","tKMzoWJ5Crk9yy3ZjJIfgDY/lx6jg/atV3e3tvJR3ig=",629544598868410825,5315708345469235506,5224690398334884900,-4902008064047607658>()) {
                                                case 1241059359:
                                                   String var10000 = (String)com.yiyiaddon.m.b.a<"s15tupsd0wfxk2","gB81Th4Ly82lHxO11IvSdCHSLFY0nFBlv/d0a+PUUsQz/w==",5541289529599812813,9081091725202551805,-6835030031901933258,4458394692093841287>();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1tg33ubhwgfn9","R8KuaY5UCKJKiKqTNZvUko4/aBFqZJNfWs9RbZkyr3o=",-4005415266570114520,3916893748466372312,3646936868760272149,-3562679894585112532>()) {
                                                      case 2127963063:
                                                         return var10000 + var2;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var5 = var4.dw();
                                             switch ((int)com.yiyiaddon.m.b.a<"s1zne091pjg40l","xhtweOQAdaZx69yPxspo0+578tF/lVyf2uFIrGTvw0w=",8348592432688102868,8005943513471766118,2110876343113876703,8293612028365815149>()) {
                                                case 1725839444:
                                                   return var5 + var2;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s2vup7kk6vgdsy","j6DSfktuQhQYNyqd+3mTjxqR1QuSsQXspgnP8wTSE/U=",4478456325250442992,-8647705010250731434,-5170060475319549388,1101641077771035844>()) {
                           case 682716677:
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
   }

   void a(BlockPos var1, boolean var2) {
      com.yiyiaddon.e.n.q.b var10000 = this.g.c;
      String var10001 = var1 + "";
      String var10002;
      if (var2) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"swl7idq4dcm1h","fthUZXvlBQ0iU71LuGqqdQeukicv9zC9xOk2mkQKapA=",-7160873568459317869,8234994842673009597,6373458175272392784,-655211716682689200>()) {
            case 185560226:
               var10002 = (String)com.yiyiaddon.m.b.a<"s2kk8rclmm1q3e","d3VWs0Dz1tuAp2Q4eE54keMFWgc7zbrLiTgiINW2+0w5Z8T0j2BjWA==",-2053270804783912326,1523733836823542583,5436011541367517001,-5406036227498539460>();
               switch ((int)com.yiyiaddon.m.b.a<"s2e8kjjbl8wbtf","NUepff+Q8c6//OD/Pq1DzVRPOES2mgJDykgxJjhJjjI=",-3575790992547168112,-697452786836634437,-5674046696734933219,-5476679141757211471>()) {
                  case 305636458:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = (String)com.yiyiaddon.m.b.a<"s155lf8z3g4iar","WNwqkxDdIo9xTw6U3TNJCoaXcBExYUzXKxk+XY9bxvgfs68OzwivD8xl",-3068618215343991742,-9090865354172591058,-8410953496432926468,-3708205357126404760>();
         switch ((int)com.yiyiaddon.m.b.a<"s3cy1809pp8dfj","FWue4qNzuZFhddnM3fbW3NSzEPMDN84SAwnojKxbam8=",-1670827579598821430,4396419613989966577,6204730968036705291,8220615015243206429>()) {
            case 353669105:
               break;
            default:
               throw null;
         }
      }

      var10000.c(var10001, var10002, this.i(var1));
   }

   void D(int var1) {
      this.g
         .c
         .c(
            (String)com.yiyiaddon.m.b.a<"s1k544siydf7xq","3h6QS+9ENQWndCHk9nUEaLVRzjXY1d2kfRLA5u2wl17i6L7tMh/mJ/DV6G+hLgxDpcWUggY0PYVI2A==",-8734902364987251223,-3538060727550027155,6901857875357101508,-408781784194332809>(),
            (String)com.yiyiaddon.m.b.a<"s155lf8z3g4iar","WNwqkxDdIo9xTw6U3TNJCoaXcBExYUzXKxk+XY9bxvgfs68OzwivD8xl",-3068618215343991742,-9090865354172591058,-8410953496432926468,-3708205357126404760>(),
            var1 + ""
         );
   }

   void x(BlockPos var1) {
      this.g
         .c
         .b(
            var1 + "",
            (String)com.yiyiaddon.m.b.a<"s3khhya0m3l3af","HijKV6AvJVmK9CjnAcm9JzQYWtopZ2bxJNUTJp5MispMg63p2KTM+GEV1WsGrm4R",-4183865967656910124,1699091681143152292,-885504937889867366,859764242984043507>(),
            this.i(var1),
            (String)com.yiyiaddon.m.b.a<"s10jgtqsusoiwj","m2WDrJtvdqYm6BUcIcAZEs8G2K04Xy7Cv2qksQ==",-2776792585590732125,1953463191459470986,9209091523292976007,4686190153320774920>()
         );
   }

   void E(int var1) {
      this.g
         .c
         .c(
            (String)com.yiyiaddon.m.b.a<"s2hf6tczazdcs3","WG6+fbK1NEucz0g3d4SeOdBp4x5Nay7r3fMvB3VIgekZkSb1tJJP30EUtnoQMVZCt4b1VER2PYiSaI12294WkLEu7Is=",-1506167827404186278,7006927124091577009,2415776473981193032,8668433913761247703>(),
            (String)com.yiyiaddon.m.b.a<"s1qf7f9zgpw5jr","pbLtJ+2CPg6RcqREgM/24TciBrlvQuon7GCqN253Gg7/1UqkRxebWRyKpMf4D945J8PCnQ==",-6815609076404119059,7430041252373583820,-6061021658646185492,8628250926154169537>(),
            var1 + ""
         );
   }

   private com.yiyiaddon.e.n.i.a a() {
      if (this.g.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nxn37zq8ddhn","L16QiciZ2/2hrm7tAIgpHikdJ43SD9EP3jkjthswmiw=",7253138995428296622,-6971814758049753627,359921998605641300,-801138853751716282>()) {
            case -753371458:
               return this.g.a;
            default:
               throw null;
         }
      } else {
         if (this.g.a != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3s1iqjxv8t8f2","NStBVcVNTU0nG0ZjiIyotGcShb4uttxjwfCBb7Q2sBo=",5122578336678558051,8858292072247383811,-575370112410290329,-1789227753980327328>()) {
               case 1012543847:
                  if (this.g.a.c().dk() != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3uzpkgovsdsgz","1b5kC6EEeoC0if/ej5nnVUDpdHQN9pDlKXuF7YviZlI=",3157950436128755425,4631444440450905926,6037080052530607229,383256167624882550>()) {
                        case -1076882039:
                           this.g.a = this.g.a.a(this.g.a.c().dk());
                           switch ((int)com.yiyiaddon.m.b.a<"s3qps3x1c2o86b","RK6bwfHkETQFdeaFIy2u4CW2GU18OJBXCjkrlOHcVks=",-7508596896020035212,6148969515003461706,-2624945410180885240,2777864790036507100>()) {
                              case 263529923:
                                 return this.g.a;
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

         if (this.g.J != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s283vjs6eg9jh6","g1JAPOaHt5JXnRF/4IPawV9ekG1yUS5auGKFhERvGH8=",8000511169118065509,-2173086660779139039,-9093418053702438388,5973052755121295524>()) {
               case -366400577:
                  this.g.a = this.g.a.a(this.g.J);
                  switch ((int)com.yiyiaddon.m.b.a<"s3b8rpxf29w5of","E/uf9A43CNMhP/xElN9FweB/vtU0DZoSCa5ydyrq67Y=",2634485707313616025,-1487878830757428442,-7346330118548604008,-1056721841943370437>()) {
                     case 1334956207:
                        return this.g.a;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return this.g.a;
         }
      }
   }

   private int cJ() {
      int var1 = 0;
      Iterator var2 = this.g.bV.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2q7s3jx48mu1f","U2LGy+D4WzXUSqiROQTZRyUgujZDhqoFL2n36Tc1vC8=",7950161321753619688,1823324918175987895,-9163917538640399327,8196068857902110168>()) {
         case -1983698452:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s7wmz12x6t71d","pe4dOC0GTmyhDTMPfp5P7dT+62Qb8jLgIJvIGtWohSU=",-6396651476283946130,135354900756454929,-8219769832564941407,-371997960275080716>()) {
                  case 478180168:
                     com.yiyiaddon.e.n.m.a.a var3 = (com.yiyiaddon.e.n.m.a.a)var2.next();
                     if (var3.b() == com.yiyiaddon.e.n.j.f.DRY) {
                        switch ((int)com.yiyiaddon.m.b.a<"s4thp447ef961","bXmFWgfk7Fr6SFh3cic9cwf8tX4CTIqJNdOQTOJN/rA=",9210209352128601122,5331968814918334027,7406602645300498328,-9054831161489969588>()) {
                           case -1122032311:
                              if (this.g.a.j(var3)) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1o5kdehqx4cvr","9TO0ffemBRyagLIAq/rXztpdUb+gFz6Yjkd6bW2OVNU=",6732560357031591867,3372460907312254884,-1454498704000163554,6018061143712148721>()) {
                                    case 1508633442:
                                       var1++;
                                       switch ((int)com.yiyiaddon.m.b.a<"slvh86crauc3c","o/SdihrXZFleWWKgNm+YhYtOQte79NLr9SUg8MqiVik=",4936085693458430550,-5982987985150316065,-1567480703390308652,-1295998242458109546>()) {
                                          case 569802401:
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

                     switch ((int)com.yiyiaddon.m.b.a<"sdxvaqvbqus2y","6e5XlxwNL5NGnFAaYBfS7kjeYdBkoxxpJw7dxEyBfmY=",-9215122898961195612,-5509141667468424851,5636855979171374466,-6514082431074148179>()) {
                        case 677387089:
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

   void hv() {
      com.yiyiaddon.e.n.i.a var1;
      b var10000;
      int var10001;
      label52: {
         var1 = this.a();
         this.g.nW = this.cK();
         var10000 = this.g;
         if (var1 != null) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s95lqox9zjecz","XT8JWYnEMwOqXc+UvNYWrLJFS3eHKG4oGvyWpbMjNH8=",-6634836382742935349,864642424728226668,-4201415186758779548,-4010808403335168870>()) {
               case -1134360205:
                  if (this.g.a != null) {
                     var10001 = this.g.a.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s1pa4ywtdzeth3","W3kjC5RhcA+GQYGBUQWvClSDfoYusB0DfdHtq8MLK2M=",-5913456957392577010,1296989298574134961,3613213874088956859,-312589757336460133>()) {
                        case 214485117:
                           break label52;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sr46ww6kdx3gh","38cDX9SOggmt0hAYc4Xwcu8coN40vk7HpdrbSU+BSKA=",7345802882879781159,4042318524055330108,4620021623844172301,3321306349345448530>()) {
                     case -1414326750:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10001 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s4vf7fibu41l8","H1rUOsdQQcJJmnDw1vnVLm6IIulE6fQ+iTOht7EABGA=",491942864261509410,-3428539648570503163,-1803492381448768246,-3309620030051230124>()) {
            case 861161460:
               break;
            default:
               throw null;
         }
      }

      label45: {
         var10000.nX = var10001;
         this.g.nY = this.cL();
         var10000 = this.g;
         if (this.g.a == h.UNLOAD) {
            switch ((int)com.yiyiaddon.m.b.a<"s3tfdw4z6b5e4p","kqSqIcHIOmXv+txXVmYgrPaC9ruVSz5DxbnqAfBLCPM=",8876043542887800162,-828654417092215930,3404132430589266221,2522757687499170139>()) {
               case 1806859745:
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1t5dt1y3pjnw2","IBmoW4lK9cInwZRTfDNnGA9CwqHFnhVP5UKdjPiBijI=",-5221447008961390772,2579934080489991764,1435164938143784383,1740497128575233051>()) {
                        case -914845271:
                           var3 = this.u();
                           switch ((int)com.yiyiaddon.m.b.a<"s1yiqfhca9t1kr","kz3Jb+TeWICgHNxZPzfcPPp+Ge56dt5JeJqEvV6NKsE=",7674468536779260257,-5770067984404038151,8885180692249987061,7475144802975291723>()) {
                              case -331438651:
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

         var3 = Map.of();
         switch ((int)com.yiyiaddon.m.b.a<"st0abw75ymuz3","ZH4Qa4SpniWKk84wBh5f7zjvJdG8Lttv0CpHSQDnzzM=",-5659470348737052272,-3323219772156564718,861226978460755200,-1082570987057537085>()) {
            case -1220036537:
               break;
            default:
               throw null;
         }
      }

      var10000.au = var3;
   }

   int cK() {
      int var1 = 0;
      Iterator var2 = this.g.a.bk().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s6r7c2728wptj","JcVh4La5XNp/nYITWkk7fSH0yZWLPsyCrkYeswQZqBw=",67212278633139290,4055060549285602257,8493466069128732739,-8215811725835652119>()) {
         case -314093126:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sznwitr2gekc1","UqJpMGrqep0KWQXQKGEqMDlYTlu5vt5mU7dhhkJxU54=",7610186306330562142,3854911783066766911,7325967972608106795,7622286583694378543>()) {
                  case 1918143588:
                     com.yiyiaddon.e.n.i.a var3 = (com.yiyiaddon.e.n.i.a)var2.next();
                     var1 += this.g.a.a(var3);
                     switch ((int)com.yiyiaddon.m.b.a<"sr7y7v6puaaxk","B3ABfo3sETyhg5Ot9M/oOp2sojEaJW8tXcbqJ898pOY=",5094792933078534367,6336187577198736311,-3603811117528265137,7187306074516341864>()) {
                        case 80125320:
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

   int cL() {
      int var1 = 0;
      Iterator var2 = this.g.a.bk().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1ak2ecxbsx6hs","yv6nuLD6dcRfXYha9y+5IFy7bDbpAtk1q9tkDk8KgRs=",-1747130601114432781,9177089579980786101,4265900520139757036,4417950426861075531>()) {
         case -2139564148:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sqcz5yjkye2ss","97mZoSyKtOO0sjpo2W2d0aSoyEuuxczV8S5ht2Td9oI=",-2848686505590584029,-8596443751900583206,-4044109290798353173,7919543905967709388>()) {
                  case -1485708690:
                     com.yiyiaddon.e.n.i.a var3 = (com.yiyiaddon.e.n.i.a)var2.next();
                     var1 += this.g.a.b(var3);
                     switch ((int)com.yiyiaddon.m.b.a<"s2nxg69xiqfe14","SFy60i8kPsnpz70ZjunPOaECPgPHxh/DQds94fGVP2k=",457508587495714831,6036833419092525678,-6836395227543180033,7479980552815098403>()) {
                        case 313265532:
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

   static String a(Map<String, Integer> var0, Map<String, Integer> var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = var0.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3vwz77pwlqcm5","bmBtfFMXW5Y3w8UjZf5SJm4qR2hKhGYeDUYP6sjUnik=",498729898006514362,-1499584161443401266,8153035396704902347,-2800950558723036295>()) {
         case 497214326:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s39ho0xuif7il3","P+F09er47zQ3WW7dFcfrQXx0qBdoi95zxCfBOUDxkn0=",-3175469568483207851,8372585272335355380,2256841824034872978,8873646860600531640>()) {
                  case -226400034:
                     Entry var4 = (Entry)var3.next();
                     int var5 = Math.max(0, (Integer)var4.getValue() - var1.getOrDefault(var4.getKey(), 0));
                     if (var5 <= 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2yb480e51gbl5","jDu6fxcptxJpC5CzvewTRvCcDp9Bhu0zwHhXD5qhwwE=",-1376050979042665161,-6684325569502911683,-7635443428175527554,123563541106493630>()) {
                           case -971873574:
                              switch ((int)com.yiyiaddon.m.b.a<"slnk3wa78irqm","RjNHoDepNpUmnsVIwSyUaYsNXK1de4ev1XkbDacjV+0=",-7571607969445787248,1505065910146841057,-6593887053302932338,8830983991006957861>()) {
                                 case -620590220:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (var2.length() > 0) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s2wbel4fv813nt","WxO+ccFY1q8DQZClEGq8AFayLXdrVSL0Yse23oz8Ik0=",-5837738924067434217,-4987534511108493504,4434533004275019605,-123797623199337926>()) {
                              case -836921870:
                                 var2.append(
                                    (String)com.yiyiaddon.m.b.a<"s211kinvh2uxwk","pTjiH+6r90Ct9W3t5pfNcqhLoWbxgvbNAqngzMTk",5330288537135693144,-2394455940515011175,8456766960420075148,-1119018881950140225>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1az3ug6k7azhn","e2x4VIlKXCcyMIa6WKkjgjj5nQfk70khXIbGPAuHtRE=",8986583373268265873,6290498402571905160,-6463164712390251797,9197647793103660152>()) {
                                    case -1685646809:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var2.append((String)var4.getKey())
                           .append(
                              (String)com.yiyiaddon.m.b.a<"s2qvxqv6pl7u01","UltLbK1lfhScopd0bWRbr5uFQu4ol88tBLmR8feY3Rk=",-5490712504387125751,8122549049551734561,-303338638724299043,5512307533239771485>()
                           )
                           .append(var5);
                        switch ((int)com.yiyiaddon.m.b.a<"s2cfiawp0u7pa8","rY8UdWOZd05xgMUzJAgDgYHIJE+EUUl1giqvoGnT8ak=",-5268743616932873394,-189505550753123922,-8167569505624228916,2722934650361000074>()) {
                           case -72725859:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return var2.toString();
         default:
            throw null;
      }
   }

   Map<String, Integer> u() {
      LinkedHashMap var1 = new LinkedHashMap();
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ofjlgoc92tib","U6MGC0H6HXvF5I4xEU+tXUHzjRQvtIMWbdB0Wug0Zu8=",-885072173270302820,-469259282022539482,443630168495244677,-4746980424539891670>()) {
            case 1914990373:
               return var1;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s16xffwpftudmv","Nqap+FY0h+iKckQCgtXMAXcPCuRSr+nhUHGRZVXYOo8=",1166870971876215413,6949188299717917326,5383932148661278826,3630149488517312629>()) {
            case 58443743:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dbzcupdijidl","Ic3xp3YNdENe7rmK0fcVD03zuIDenPy3cf0HHDVbgC4=",-4304312864328545863,4577205397664255018,-7046808469949112526,-2508418298299651463>()) {
                     case 2079740150:
                        ItemStack var4 = var2.player.getInventory().getItem(var3);
                        if (var4.isEmpty()) {
                           label68:
                           switch ((int)com.yiyiaddon.m.b.a<"s34n30fvoindgj","fierKefddvkiRTM95SfLOMj0cKS4H7LCleYs2MRQFsA=",-5204779674204738287,3843329396345344731,928765143332743528,4098756128385418713>()) {
                              case -1945115882:
                                 switch ((int)com.yiyiaddon.m.b.a<"sxhkit5ztn0y6","MeSGA9DfTbUMBVXdvKOn+rFIqJCq2aFydNLXJ8Bx8Tk=",1148822679007901345,6043651812664052066,-2881877981001520766,839771010143462326>()) {
                                    case 237715907:
                                       break label68;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           boolean var5 = false;
                           Iterator var6 = this.g.a.bk().iterator();
                           label48:
                           switch ((int)com.yiyiaddon.m.b.a<"smfsu7unqw7ck","6i5uVkC3mxTe1I9QPTaAPd3KBXBFV8+SsJ2KCP1NbzA=",2309581615837393621,-900639150959366016,-886091531659052374,6636325085504977943>()) {
                              case 1877654762:
                                 label94:
                                 while (var6.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"swgtcityptavi","YvSY/sjIEN796xzXFbtsk6Im//ixvuPPVCLP26xaAJo=",1341791547572814632,4411864142759085935,285430981136608079,1410405138631412506>()) {
                                       case -266717710:
                                          com.yiyiaddon.e.n.i.a var7 = (com.yiyiaddon.e.n.i.a)var6.next();
                                          com.yiyiaddon.e.n.p.b var8 = this.g.a.a(var4, var7);
                                          if (var8 == com.yiyiaddon.e.n.p.b.SEED) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s22166tb104op9","eCm5pNsQykgxzm9GTB+xg/8bl9lKgKOob8Gqwv28vDs=",-6747592590287708629,4816434392555734158,8453151599391427494,-6604522675220130998>()) {
                                                case 962055096:
                                                   var5 = false;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s13wksb1n3245q","Vv28WTaoDBgoi9jiUzlO7NgVFXU8zKW0SA1azWOKZSA=",-9188975869636632225,-1005377898926696260,-4552467820914464792,5070395782818971649>()) {
                                                      case -1020832209:
                                                         break label94;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var8.dA()) {
                                             label60:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3htkb0rc05cwo","YuktYpGOdP+mYcrqGUNH0TMRpDxUSHeIOe6p6/eyelM=",5327480873549454873,1994364208951692293,3070207383875570864,-8499579716495350149>()) {
                                                case -1432995945:
                                                   var5 = true;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1kyr2qa2ybd9h","0Tu5v6H/Q78y/Aaq3F5NIA1vr2t5Z9EAKzzkLgjbnsQ=",250882880724286372,1955033465378224261,719882797211797600,8582109720763841262>()) {
                                                      case -771467997:
                                                         break label60;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2ing3yrm2u90i","lfQIZilNAY0ig2mzMx4jBX7O33Dkbt0b9YZhckPnTlY=",-6345455170754795507,2818052392743502569,4352923320048889088,4818260811060289045>()) {
                                             case 1580529712:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var5) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s13nb5phzzw1jg","e8sJCmLa9IFG8JA2OwRp7DsLDDxmQXlTY14/8zhxmWY=",-5655051765642109601,-137649780263711547,-436603867150190615,-2034842957808136531>()) {
                                       case -1842088654:
                                          var1.merge(var4.getHoverName().getString(), var4.getCount(), Integer::sum);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1urt2f1gwjzrd","n0ogUw+Olkiy71KvhYtqQPXauctDWWlsRVJrmrDGVhk=",-5617710698134611666,4852611833379698702,2784420465791592461,-3711686998478441263>()) {
                                             case 612995216:
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

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1de4zogkk57be","uEv++wxN62H2hoQK4lj8VCO3NCF6pQu1AVwMsAAnRJM=",-6281233517450590612,5120195517767704139,-4347684541221990554,-7542002292896539385>()) {
                           case 484155331:
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
   }

   String eC() {
      Iterator var1 = this.g.b.a().u().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2e2qqclrj91rl","JymWbcaAEVbooIg/leqW3n8w/BBhm3m123p+apns0BQ=",-2590151251890526364,-2427481467301450292,-1939033957962405822,8444994040189863728>()) {
         case -313847638:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"ska5sytc5ize8","4HcGydEwIQk0uQmhMdjpNs0Qo0kQmY/IdQYQYE4w1UA=",6443269974085397446,335033611327437003,-6215745328522576390,9073533770232734508>()) {
                  case 113341756:
                     com.yiyiaddon.e.n.n.f.f var2 = (com.yiyiaddon.e.n.n.f.f)var1.next();
                     if (var2.a() != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
                        switch ((int)com.yiyiaddon.m.b.a<"syyl5usbteu70","FONWHlgAk77yrDDZ1lJXsb04U67innPbASKodMJotn0=",-1216364443875267483,-8012403618780159072,6425840852542572240,8505797241681163789>()) {
                           case 571662395:
                              return var2.a().m() + "";
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2xptviz5ezjqx","GJ15xTWOYJ7W7cA1rzhhFe6LSF0GHsJLKN1u/tktgek=",-7420993606165900029,7723201605791860268,6303497781826457144,2200919503331856844>()) {
                        case 85353534:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return (String)com.yiyiaddon.m.b.a<"s3hf2y0wft9jsa","5tm7OzNRHiAUFWUHjLKSpQ8w+1YRmTdWulm/Y7FMG1b9JFqQ",8461815753880418391,-5361756369378848103,2708564886473008665,994810331512098697>();
         default:
            throw null;
      }
   }

   boolean em() {
      if (this.g.X.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dso3i0g05eps","biFfjC9JjbaN1+V5ORJuz1ja74Z2DnVxa99J5VnoyWY=",6061829434449760891,-3432393324550020202,-7182349894180070181,9097285004880245073>()) {
            case -985159278:
               return false;
            default:
               throw null;
         }
      } else {
         String var1 = this.eC();
         boolean var2 = false;
         Iterator var3 = new ArrayList<>(this.g.X).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3gexwjha649sj","Y4Vq9SDWgBjUtS9HXN+6DeObhjvJjFDfqp3myLk2sxw=",-1021485768700710364,2711456529332082816,-8959803661593389605,4391156243435909961>()) {
            case -723232458:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1pcs9chl72pql","LTel+WrqOxBzQwoS+0T1Dg/KYNbe0BmJJRVRjKmkntA=",9094727418377884666,4442245713004069428,254512924323623037,-5846107578824316004>()) {
                     case -476995689:
                        String var4 = (String)var3.next();
                        com.yiyiaddon.e.n.i.a var10000;
                        if (this.g.a == null) {
                           label65:
                           switch ((int)com.yiyiaddon.m.b.a<"snhfd4j5kyfcq","+cxxkKnLqcDnET43uQUWOMwG9su55glZ8CRQxZjEz4E=",-1192800205809066364,4756050044058466163,-5304274834122469814,-2656875762379567580>()) {
                              case 574539608:
                                 var10000 = null;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1lpcs8jzkzb4t","NCka3f5CB7HdwqK/R65Mg++jHHmWz+3P1zD62WkqHz0=",-8277706017997691323,-2535992575787263696,5201147756450712126,3935433270794959167>()) {
                                    case -172423980:
                                       break label65;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = this.g.a.a(var4);
                           switch ((int)com.yiyiaddon.m.b.a<"s12ni9ai31lb8y","1Ind7vaoxtmP0k+gfsZZ14tJF4d+U4KE6g509yWZ39k=",2057491358091419605,8886206254174346493,8680507948163864770,7468933640801311919>()) {
                              case -457368958:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.e.n.i.a var5 = var10000;
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1vom15y0c6fue","PWT4jbyLVkSbbYvOf6+q0TY0oPZAiYX0zRNIHeUq/9Q=",-5529306873303853993,701025511956758899,-4823702821317925851,8324695407978777635>()) {
                              case 1881124868:
                                 if (this.g.b.a(var5, this.g.a.a(var5)) == com.yiyiaddon.e.n.n.f.c.DISALLOWED) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s30usmg9o8ntj1","o77YyRq2eWFnMu6nRvJmmO3fyGwlMgeVm5YGKztNWno=",-5334300272616501265,2720516003469427741,8147311938564391709,-451798103452255689>()) {
                                       case -245957796:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3jblxhqrzehy3","mHLDBvmimUXfAK5zCHxAs2B94E8Oe4AWaW2c40oLm1A=",-5017587071888914592,6934057138662022182,4420910120369071660,6459321311261531705>()) {
                                             case 303392793:
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

                        this.g.X.remove(var4);
                        this.g.Y.removeIf(var1x -> var1x.startsWith(var4 + ""));
                        var2 = true;
                        if (var5 != null) {
                           label55:
                           switch ((int)com.yiyiaddon.m.b.a<"s22gb7pmtwyude","HfByUw294Uah344YtOyxGJ5+3nfbtiIKfZzw2/YTadQ=",2896340853078592300,6460698986353147433,7540074153345817235,-3126105411554225299>()) {
                              case 1285967546:
                                 this.g
                                    .c
                                    .f(
                                       this.d(
                                          (String)com.yiyiaddon.m.b.a<"s2helefl3ruu30","EXOMJtR/GZEaCKoPx6qVwZfO1WceY6S4pi+ucWs6Ryo=",-2202141650992768866,8293854979887255563,-5754384496724427004,-3508145594379265206>(),
                                          var4,
                                          var1
                                       ),
                                       var5.dA(),
                                       var1
                                    );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1d6qegfozrzbv","TPgOhbWj56vlbIojxwxcu9MwSVhKtoVKWRDryOrKKcI=",493897681802582195,146302792785228940,3152932104166007247,-5750106255501958434>()) {
                                    case -583778230:
                                       break label55;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s142sfizrcowg6","I7mN1yW2N9QHrwTgCEBeuByKKH6Z0/2ZKgYaCbto81U=",-4446561522436821145,4592649003353049662,-1789045544809977116,-5659922190988909949>()) {
                           case -1125349076:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.g.X.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2yuqu9hpuorkc","yIyCcrr09sPXzLqhfIlGFZyE/2QkH4dCq5hTM90hIr8=",-7773454554010035585,-4189607818094913389,-5343736305707829233,1740591567471884959>()) {
                     case -628417915:
                        this.g.us = null;
                        switch ((int)com.yiyiaddon.m.b.a<"s3571kkq30szw5","gw2T+MG5qR/nxQrZeHtfjvmMAbesIYMj+0N5skKr5LU=",5693660561777946513,8223120416671589607,1371453613593018401,-5761720611543866032>()) {
                           case -1781057081:
                              return var2;
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

   String d(String var1, String var2, String var3) {
      return var1 + this.g.mS + var2 + var3;
   }
}
