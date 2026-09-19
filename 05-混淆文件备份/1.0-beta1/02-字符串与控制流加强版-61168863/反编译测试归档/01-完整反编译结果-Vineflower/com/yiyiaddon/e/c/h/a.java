package com.yiyiaddon.e.c.h;

import com.yiyiaddon.e.c.d.d;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class a {
   private static final int aT = 512;
   private BlockPos g = BlockPos.ZERO;
   private BlockPos h = BlockPos.ZERO;
   private boolean M;
   private int as;
   private int at;
   private int au;
   private final Set<BlockPos> j = new HashSet<>();
   private final Set<BlockPos> k = new HashSet<>();
   private final Set<BlockPos> l = new HashSet<>();
   private final Set<com.yiyiaddon.e.c.d.a> m = EnumSet.noneOf(com.yiyiaddon.e.c.d.a.class);

   public void a(BlockPos var1, BlockPos var2) {
      this.g = new BlockPos(Math.min(var1.getX(), var2.getX()), Math.min(var1.getY(), var2.getY()), Math.min(var1.getZ(), var2.getZ()));
      this.h = new BlockPos(Math.max(var1.getX(), var2.getX()), Math.max(var1.getY(), var2.getY()), Math.max(var1.getZ(), var2.getZ()));
      this.g = new BlockPos(this.g.getX(), this.g.getY() - 1, this.g.getZ());
      this.h = new BlockPos(this.h.getX(), this.h.getY() + 3, this.h.getZ());
      this.M = true;
      this.at();
   }

   public void a(Set<com.yiyiaddon.e.c.d.a> var1) {
      if (this.m.equals(var1)) {
         switch ((int)b.a<"s1l6uybm9zyu2u","ZFmtT8OGM3oRg8aIDl+54v3qiw9nSzGbSZ91dUdnShQ=",-4407428143710404429,1867672316790957479,5846740512461077908,-5740792409943973800>()) {
            case -1948217732:
               return;
            default:
               throw null;
         }
      } else {
         this.m.clear();
         this.m.addAll(var1);
         this.at();
      }
   }

   public Set<com.yiyiaddon.e.c.d.a> j() {
      return Collections.unmodifiableSet(this.m);
   }

   public boolean M() {
      return this.M;
   }

   public BlockPos f() {
      return this.g;
   }

   public BlockPos g() {
      return this.h;
   }

   public long h() {
      if (!this.M) {
         switch ((int)b.a<"s29e8qk2euqp01","HfalxLDBEbIY17jSKes6uBGmB1qXfQOz7Wtja+v5wjc=",-2829998058197398765,-5058019121757032894,4565389317772246443,7568137652410247915>()) {
            case -568082226:
               return 0L;
            default:
               throw null;
         }
      } else {
         long var1 = (long)this.h.getX() - this.g.getX() + 1L;
         long var3 = (long)this.h.getY() - this.g.getY() + 1L;
         long var5 = (long)this.h.getZ() - this.g.getZ() + 1L;
         return var1 * var3 * var5;
      }
   }

   public List<d> a(int var1) {
      ArrayList var2 = new ArrayList();
      if (var1 > 0) {
         switch ((int)b.a<"s1rfcohf2clfbb","JD2EA3sC4iP61DOvI6qtDqRiOs6ydTrRDtYwOYKE05w=",-5913464910401558983,-7959036626357125517,749256869258086337,5528943456918510901>()) {
            case 2034340850:
               if (!this.j.isEmpty()) {
                  Minecraft var3 = Minecraft.getInstance();
                  if (var3.player != null) {
                     switch ((int)b.a<"s2z4tvjg86e74d","ADbmQqZAbrjZZjkCqGhJHe2cj8IDOAOAyoY2WwjuEzM=",7598743500472048838,-8007390814266409454,7436175075855811384,6607445867677250378>()) {
                        case 1244923833:
                           if (var3.level != null) {
                              ArrayList var4 = new ArrayList<>(this.j);
                              var4.sort(Comparator.comparingDouble(this::a));
                              Iterator var5 = var4.iterator();
                              switch ((int)b.a<"s2dn80ul96xkkf","2OOf/vsYi3+vS5xT9xm4TjECdVlFTTc0/mFaW8rnjAE=",5906902006108062692,1508099363685074610,-4946310717389336182,6613130350539145254>()) {
                                 case 1282960537:
                                    while (var5.hasNext()) {
                                       switch ((int)b.a<"s2bc5bdowkq1iv","2pnSeIYkmcAh7w/aYwVWYwYtuZCWCYptjFqx6d414Ko=",2982175991224594475,-7045918599157281673,-5794682936420029656,6114325862799890105>()) {
                                          case 1860001892:
                                             BlockPos var6 = (BlockPos)var5.next();
                                             if (var2.size() >= var1) {
                                                switch ((int)b.a<"s2qlr5k04qm7ij","Bh3FKaNxhmPDLyAAJJgldXPdMhp6kS4fgxwqnQfkzGk=",961752263781450406,6473197941448958532,-233365109688924091,2918045349276687407>()) {
                                                   case 813500542:
                                                      switch ((int)b.a<"s3t48by4vn60qv","Wa6o3AkylKmzjulomPpCmY4jk5GLuqAeSQdN97StC6A=",4042688673091837421,-6954039294942465174,-3258627502357095879,1865949165998938087>()) {
                                                         case -1392841485:
                                                            return var2;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             com.yiyiaddon.e.c.d.a var7 = com.yiyiaddon.e.c.d.a.a(var3.level.getBlockState(var6).getBlock());
                                             if (var7 == null) {
                                                switch ((int)b.a<"sjkyj9q09fnj3","Nxq3a14WM3Q9jXGb7rmPU5ycll6zs34sWe9F2pKNi0g=",5814920684628207919,-971552114922235091,-8129502263223518800,123064371498639170>()) {
                                                   case -884942315:
                                                      switch ((int)b.a<"s2ujkkk8upbvqu","dJZp2ofSZlP3d/nt+4TZp9mNqZ2UceL4W2iOl7Id+EA=",4931618424341970122,-284501944592012138,2840600665645230489,3945118875311910593>()) {
                                                         case 362248402:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                var2.add(d.a(var7, var6));
                                                switch ((int)b.a<"s23ljsjcqkklml","bhXwWidIXNlW4IK995VIy08s9JD2ZuN9mFXbiFymy7c=",-237348717155116065,-3553152581961987284,-6150097967532080531,4993773283263585572>()) {
                                                   case -1928401235:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
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

                           switch ((int)b.a<"s37kbfxh6c7khb","LylfLtFu5z8e7WwzXi1/87PBrRGqQ5UKE4+t+2j6MOU=",5460532669040497156,-453632491168088654,-8878045637914064035,-6525810454119058995>()) {
                              case -797286241:
                                 return var2;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return var2;
               } else {
                  switch ((int)b.a<"s2vwmcx8ya4n6d","uN11jCJfL5NYDDD7oRxC2MjYc6cwFOrcCQonQQO1gi4=",875366992769981677,6000005599756062039,-6382784887632551827,-4864459150750151680>()) {
                     case -1636760347:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public List<BlockPos> b(int var1) {
      ArrayList var2 = new ArrayList();
      if (var1 > 0) {
         switch ((int)b.a<"s2dm4ns1molifm","A5WliiZYqyqMRKA1eblJAT4QdjUPt2rNId2fJ2sPHgc=",-546082601569017240,-3021838756257764791,-3252886491427504677,-4970374346746983422>()) {
            case 1911400578:
               if (!this.k.isEmpty()) {
                  Minecraft var3 = Minecraft.getInstance();
                  if (var3.player == null) {
                     switch ((int)b.a<"sadzrq4cbd3aa","iMcoQtIjlFNIjT+QIUVAH9OudSG/7gS47EsJ05+Hung=",-6793803092814878974,-6948337689919819897,9084061729741100118,-2402975132861269954>()) {
                        case -747518851:
                           return var2;
                        default:
                           throw null;
                     }
                  } else {
                     ArrayList var4 = new ArrayList<>(this.k);
                     var4.sort(Comparator.comparingDouble(this::a));
                     Iterator var5 = var4.iterator();
                     switch ((int)b.a<"s2e1d2ds4xeoeo","WH0vZ0z2/6dQtERxBOu/h053F24qmBxJbaVLyvgSh6o=",-4355484791964847816,5629119060141622829,605572287395781896,4733652803505522267>()) {
                        case -324223366:
                           while (var5.hasNext()) {
                              switch ((int)b.a<"sg7zotdyeyw1u","o+CGN5tJWfruib9/wbXCTRIrsflKBRhVCOBdaCVFfqw=",-3633864693897809838,7157606680562968105,5613215924918694778,-2941380511736618238>()) {
                                 case -399986612:
                                    BlockPos var6 = (BlockPos)var5.next();
                                    if (var2.size() >= var1) {
                                       switch ((int)b.a<"s2ojfmie3s7o4h","YuJfA7OkTcf42MZYzDGkQSmqX/ADZxYM7cUPaY7FWJU=",7334360225963783290,-5953748318903855824,6325339973401130995,1455084450682932086>()) {
                                          case -951416492:
                                             switch ((int)b.a<"s36kew4a2gfunp","ZMnqBJnz79EDg3RLosjf1cx5N8QOAXVfNeb8N9MVCZI=",-35336088134134111,-5120051978556288187,1220150491978176069,-7007477428022434033>()) {
                                                case -976735321:
                                                   return var2;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var2.add(var6);
                                    switch ((int)b.a<"s1w4v9w2uhw7bw","oD9RGFOTJoIobKDibHCs5tPXlYi0sSo4hjiEdaE/FVs=",397287103565550980,6326834916166665170,641069887690330407,-1244313872021704893>()) {
                                       case 1685905593:
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
               } else {
                  switch ((int)b.a<"s2zi3qrhz2oa2w","4BP4h0eLICPPPk95PfM8z2Wgi5cKnvaZT5/Br3dDGfA=",-4204783761254355497,3326510276565438566,3157318851438017599,-1539433487685396720>()) {
                     case 1837260254:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public List<BlockPos> c(int var1) {
      ArrayList var2 = new ArrayList();
      if (var1 > 0) {
         switch ((int)b.a<"s2qhu6pbp8p7pv","boo+6olXnvOItgCKgNaEVUzSGl8nH79BFKPkvnp/fcs=",-4070511903665964837,-1201625296712632458,4240086912921627757,-4387829359466460680>()) {
            case -2036660735:
               if (!this.l.isEmpty()) {
                  Minecraft var3 = Minecraft.getInstance();
                  if (var3.player == null) {
                     switch ((int)b.a<"s3221bp1cfxzjx","u9ywsz5L/mfTjpEAVOkSkcc5BlZoheMObbNKi3Jjc4Y=",7045278941519250701,2974220436549546734,-4084292686700778887,6990555135904473376>()) {
                        case -1949404327:
                           return var2;
                        default:
                           throw null;
                     }
                  } else {
                     ArrayList var4 = new ArrayList<>(this.l);
                     var4.sort(Comparator.comparingDouble(this::a));
                     Iterator var5 = var4.iterator();
                     switch ((int)b.a<"s2qus9jqumt9jz","SAzr5DiJO8vzCT2LFjtsggC/OPcW1/imXbNHeQxuWQ0=",2369487794547342839,-7515039410605252364,-8060368197451870111,-6362874820384919115>()) {
                        case -1080896631:
                           while (var5.hasNext()) {
                              switch ((int)b.a<"s1i0elpi76aew9","7WJOWJJ4IHvTK0fj7uNTMdEhRs0U/Eto3DDYILRH3+k=",5433965878099487774,3936540563219340205,5244984054975584273,551345117226924111>()) {
                                 case -1824829744:
                                    BlockPos var6 = (BlockPos)var5.next();
                                    if (var2.size() >= var1) {
                                       switch ((int)b.a<"s1nxngaj43r4q1","TjUbhLrDN0QtddZMQXR8Q9QYrZNtrFwUzsBLDlQHLAI=",-4810134052319269595,3013120541982639452,-8593289308946016261,-3982624036430002246>()) {
                                          case 637569731:
                                             switch ((int)b.a<"s12ihkglf9kb99","DhOGthl4VMMFZsFTxkYjsIWaeH9sYsIzvzhmUl06Wa8=",-665079607008779062,4974311536860837766,-3166751217363047007,-1665739858467271829>()) {
                                                case 471185444:
                                                   return var2;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var2.add(var6);
                                    switch ((int)b.a<"s1y2l75s4ioks9","PneMffsHBwtmfUxtNd/wevPQMUmloEv9xpGCqulDaHw=",-4149711071957627152,868088237316361459,-7023989726716966582,-7598695087680958716>()) {
                                       case 634323703:
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
               } else {
                  switch ((int)b.a<"snukqs9rb8x2h","g4t2lgGA8dgin25F0cVU7tOcjLcovDXunnzCybFfZAQ=",3566005317235420348,2194323279457749822,-5448426274068634688,5150079751367601744>()) {
                     case 42708352:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean N() {
      if (!this.l.isEmpty()) {
         switch ((int)b.a<"s1iznndxe5g13x","V3Nk/He8TxyvkOCzE6PKaRm591Q+pFpXez3dZF1UaM0=",-2134931628420860423,5785982017472845287,-3566394317051727484,-6715455682303136754>()) {
            case 1273323278:
               switch ((int)b.a<"sy8ii0uvw0en1","39Ra37BHphF8ZpiLdvwb4k29nGQDbgQmQ5C4JOX+3uA=",1084081458055559038,6564018411532363750,-5878340548817792050,2652829043714501602>()) {
                  case 23387817:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s3q2uuz7i6sc7v","RRH9QosYgBUYKrE9pq/wXeKy6X4Zuy6MnyFk5MfFnVg=",-4072392248367547389,7872553033865957105,688201652416570253,6218660494378120857>()) {
            case -1230657008:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean k(BlockPos var1) {
      if (!this.M) {
         switch ((int)b.a<"s2dul6yj0jkw6m","pkFoKTn0z0u7/xlMdwiiFtRUB9O9bIoBQTvddRiFFI8=",-3518195340175510740,-8562128265347495003,-7364890791850355140,-1998480890067601773>()) {
            case -812451030:
               return false;
            default:
               throw null;
         }
      } else {
         if (var1.getX() >= this.g.getX()) {
            switch ((int)b.a<"sqf00qti6xc05","vSn2Jusnje+35YJa+mhX+Y+MW4WjBF4ReWYfug//7M0=",4235044150992871321,-3430213180630240876,8264191976374923165,-2452626285022527184>()) {
               case 458221566:
                  if (var1.getX() <= this.h.getX()) {
                     switch ((int)b.a<"s4sukm9yguvwb","tyhyO+lo9sZBZkzfUAB56wmZpWqTkXvwQLPpzEpPF30=",3347942040820058551,6923617171177333826,1110372077641862972,-12993352420503680>()) {
                        case -1313999836:
                           if (var1.getY() >= this.g.getY()) {
                              switch ((int)b.a<"s59e28iv6i77j","6mhQ1T6pKviod9/gYenJNjyp+HknUKvHLSF6A8Nus98=",-6317091686149720102,994152509683569597,5735456254206317459,-7583747409384252432>()) {
                                 case -431111166:
                                    if (var1.getY() <= this.h.getY()) {
                                       switch ((int)b.a<"s3gui13iugeg0q","HU7prZUMfmHgBHpl1NeG/DYpMPUatdjHuQJKbFBlDg8=",-7533455804117052482,5258270227244506383,3134920534088551674,5130105672809393166>()) {
                                          case -1570249061:
                                             if (var1.getZ() >= this.g.getZ()) {
                                                switch ((int)b.a<"s1aoqcmn7ce8ov","C8b8ZoP+NTtcjegchajVGCkIBnD3JF6RyROZqeE7+M8=",-7070427543924334903,4040789240972673617,4436370095263202781,-7911933286344681851>()) {
                                                   case 1961838437:
                                                      if (var1.getZ() <= this.h.getZ()) {
                                                         switch ((int)b.a<"snkuzw8e3pem3","iOO4eLOf4o/DLkwDnzS8yUL5rduuzygT4z1vUrtC09Y=",-5636188230172301710,1927558235524588609,3941235324989465146,-2529556998930902323>()) {
                                                            case 343709853:
                                                               switch ((int)b.a<"ssg3izcwkn7x5","S8biUbikrk3qfr4+fQQ7/L8gGeGU26IZ2o31PNklLxs=",1712277730553210845,7939476243013950562,6435466699750461952,-1493688591542563734>()) {
                                                                  case -1683441177:
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

         switch ((int)b.a<"s2stsgne51sf18","ejlABT5p6Rzb5G2VapuLLewFGhbM86i3lK4Otl1dRkY=",-4566557338202213373,-609904446847778405,-5498057205380459956,3415526379172917986>()) {
            case -1140807651:
               return false;
            default:
               throw null;
         }
      }
   }

   public void g(BlockPos var1) {
      this.j.remove(var1);
      this.k.remove(var1);
      this.l.remove(var1);
   }

   public void f() {
      this.M = false;
      this.j.clear();
      this.k.clear();
      this.l.clear();
      this.at();
   }

   private void at() {
      this.as = this.g.getX();
      this.at = this.g.getY();
      this.au = this.g.getZ();
   }

   public void ae() {
      if (this.M) {
         switch ((int)b.a<"s2slicxuguwcq9","y18YcFQiyRuALNs/KGmLyDWjoZtYssi+rM/VKNeLcec=",-2522991147874500782,-7728365324649519260,-7295798283597555424,-8779668450941505986>()) {
            case -987031099:
               if (!this.m.isEmpty()) {
                  Minecraft var1 = Minecraft.getInstance();
                  ClientLevel var2 = var1.level;
                  if (var2 == null) {
                     switch ((int)b.a<"s1l5us85d6y80g","Mh1UY/YY0N51Lwuu1kUakEAws+UPa7yYHMJA6IClGos=",-2882499488353581044,4585349383003899232,-2962566264497308138,-4721052831086020140>()) {
                        case 1701772190:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     MutableBlockPos var3 = new MutableBlockPos();
                     int var4 = 0;
                     switch ((int)b.a<"skjpil9yivhf1","ZRyYhiFAc71mq6JTwQq/7JUvpl6nEyRFqhDspbQWekU=",38866440330556828,8473290040186017030,7707296843892148574,3847909751878566428>()) {
                        case -2016716918:
                           while (var4 < 512) {
                              switch ((int)b.a<"sw1zr9fj6yp5f","E5KlfyoDQycXDFwrjSlcZIywQInuCjDtXiv7i7VQKoM=",7344469846326547801,-7297096555502092080,4891820280234937809,4589366308961973531>()) {
                                 case 130825381:
                                    var3.set(this.as, this.at, this.au);
                                    this.a(var2, var3);
                                    this.av();
                                    var4++;
                                    switch ((int)b.a<"s2lzbdj4ej99bm","T8685vumkjhJVou98qmiYxWisoqfsjwmnwvv+44mKFE=",8528179265081587432,5900511324496601090,-2423646564213664190,8927060185662261576>()) {
                                       case 838386578:
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
               } else {
                  switch ((int)b.a<"s1pwwtfpztlkfz","y1XTAjKCWRF7WGxd0wyc1uIrCM9bTD1o5Ea6CL9rYaY=",-1270581752079857907,-66893295505518883,-3943950219941517377,2229192496001781540>()) {
                     case 1190890099:
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

   public void au() {
      if (this.M) {
         switch ((int)b.a<"smae01cw3vvh0","wBDAzDoIcARUUySanUsTqXt4atIhqQjig6ePqkjraEU=",-1883306100939955824,2051739152804372950,-4137920528270701495,7036484526238087089>()) {
            case 45184032:
               if (!this.m.isEmpty()) {
                  Minecraft var1 = Minecraft.getInstance();
                  ClientLevel var2 = var1.level;
                  if (var2 == null) {
                     switch ((int)b.a<"s135gsu6ealrvi","1R8iuDssVE3FBoXja+8g0SggaBiFHAS2u2OSAvCxXCo=",-3808962715031526682,2740173703602885827,-969239510263059454,-990907203273984470>()) {
                        case -1089022313:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     MutableBlockPos var3 = new MutableBlockPos();
                     int var4 = this.g.getX();
                     switch ((int)b.a<"s1r2neft3whkkv","mql6iw9s/BT1zy9STxMTku3fXaQ4YILDwh5veYYjZ0Q=",-8533087249109971163,49068311155564529,-7139847021566973267,-1251750464686423816>()) {
                        case -1851059463:
                           while (var4 <= this.h.getX()) {
                              switch ((int)b.a<"s3d4lpo6n7ynpy","MT1pzUdFhWJLr8ArG8l80lC2JIeMqmKL9JnwP45H0rI=",-1319574096562006452,4438498412474176780,9001603201691483938,5224310443789657802>()) {
                                 case -851388144:
                                    int var5 = this.g.getZ();
                                    switch ((int)b.a<"s288aly9kpc0v7","tyYi245cCYZqusJifNmVtDMqW67Z1Bj8N3Eaza82vUg=",-6309372249115208487,5764122454691097973,3542065986305541534,6547612883506047244>()) {
                                       case 1701126568:
                                          while (var5 <= this.h.getZ()) {
                                             switch ((int)b.a<"s3ivab01rqzqh8","7PfHIIgWuiJO8Oiy/bucLyT6XbJWBMYzPoCRxpQ4OlM=",-6831077538858358814,5102772824120639113,3171451208422922825,9151672220593097365>()) {
                                                case -1342672130:
                                                   int var6 = this.g.getY();
                                                   switch ((int)b.a<"s1oi5ytqzofwi5","UDjPrZ7Qyz7ag/LebuYa7GT1wMdgbWgsod7jTypfbss=",-6327037404505728690,6674234429166609500,3066778590871806926,-2907247301248674012>()) {
                                                      case 535838150:
                                                         while (var6 <= this.h.getY()) {
                                                            switch ((int)b.a<"s19i74zd07005r","er/P//DZoBwHDJpymF4wM9dQqjkvwyVymuF560eChVk=",105645818991307606,1090803525424721565,6464632329085493529,-6214522312309247383>()) {
                                                               case 879033463:
                                                                  var3.set(var4, var6, var5);
                                                                  this.a(var2, var3);
                                                                  var6++;
                                                                  switch ((int)b.a<"s3f3srbrsiwxi2","J0pT1Rb7aYlaviEFtU3/NNigN0lF1W6KYNXIRNcW1QE=",1224293804289303941,2253052744314774930,6372076636402609633,-7075822023602680635>()) {
                                                                     case 1417699693:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         var5++;
                                                         switch ((int)b.a<"s1eur4luegdulc","BzexJoEtHONxH/8ghZ80Vubb3YDiz4YUZmjTjPmpEQQ=",1666576696648397844,3702164194299854494,-851705688329247788,-7271529179317941942>()) {
                                                            case -939027038:
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

                                          var4++;
                                          switch ((int)b.a<"s217y3yoc76vnv","3bVt2hYCBBG+RlpqNKeuHh/4PWksnQk/kcfK41wzCPs=",-5631312751086540599,9084554546507311042,-8654059687300080497,3751401716017975742>()) {
                                             case 551382468:
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

                           this.at();
                           return;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)b.a<"s2xs8o0xrmz6j4","Yq13jAV1UuJnmB6/64fmlyiBBUfjUz/Cvblb7670KEw=",5255591136445326949,-6196020091543970341,-8052673561631964689,8384598602988384752>()) {
                     case -2046095493:
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

   private void av() {
      this.as++;
      if (this.as <= this.h.getX()) {
         switch ((int)b.a<"s197mmjzmcwpkr","TQVP6keDufKqXB+naEhDWHyJiHPkniG62qMW7DKJPR0=",8465736428447049412,8513758593932608959,-143618436689721589,-4832012442292192742>()) {
            case -1973247560:
               return;
            default:
               throw null;
         }
      } else {
         this.as = this.g.getX();
         this.au++;
         if (this.au <= this.h.getZ()) {
            switch ((int)b.a<"srk99uqcihcgh","sZWCFBralmLHmu1thNGZmLG0ou6MgzkBHZtXl9WHHek=",-9072707906649141647,-955795060233357655,-4754941691504639609,5220070628434240265>()) {
               case 65056834:
                  return;
               default:
                  throw null;
            }
         } else {
            this.au = this.g.getZ();
            this.at++;
            if (this.at <= this.h.getY()) {
               switch ((int)b.a<"s1ffb3vldvd887","+qv9swQUHzI73J4snn0B7pxelQmMTLIDsxakZndU1Ek=",628776998770213242,-8277750273764288302,80041065521538059,8288890748519188161>()) {
                  case 1894481503:
                     return;
                  default:
                     throw null;
               }
            } else {
               this.as = this.g.getX();
               this.at = this.g.getY();
               this.au = this.g.getZ();
            }
         }
      }
   }

   private void a(ClientLevel var1, BlockPos var2) {
      BlockState var3 = var1.getBlockState(var2);
      if (var3.isAir()) {
         switch ((int)b.a<"s335tcih24k3td","m+j/EPa5CjNvEMn0lVu26rLXv/iuonaTAXTfMagnVoo=",1829336706376643865,-8842756760135754291,-6528977035884314866,5493558150333199398>()) {
            case -1956756909:
               this.j.remove(var2);
               this.k.remove(var2);
               this.l.remove(var2);
               return;
            default:
               throw null;
         }
      } else {
         label98: {
            com.yiyiaddon.e.c.d.a var4 = com.yiyiaddon.e.c.d.a.a(var3.getBlock());
            if (var4 != null) {
               switch ((int)b.a<"s1i9nud4nhd92g","IvnooPmpIflBmgSKAKFWnkIoRklxpSKcIRrAw9TRDYM=",6314085145938978862,-7263934268809065287,1977787327129624246,-5623496595391120107>()) {
                  case -1248590417:
                     if (this.m.contains(var4)) {
                        switch ((int)b.a<"srpqdjlfewohn","JoYZdRFf6U+zex9G3NWo02FmXYR7bLS7/svtGuyNi48=",-7986444149845897586,-3824533746817134572,1319994297710365789,-4720910327217150347>()) {
                           case -789689655:
                              if (var4.a(var3, var1, var2)) {
                                 switch ((int)b.a<"s1xxvwg2bu52zm","TQN6q7lX3BoetW8vWXEUzIZlBSz2I4CB1+UJN5BYz08=",2404778831933602204,-1885982418297941842,-5999970097906281965,182602900228110902>()) {
                                    case -1197878169:
                                       this.j.add(var2.immutable());
                                       switch ((int)b.a<"s2l83u6nd3vn6","l4352BcXpWRZ4N0zfdAW7ePG4fwueqwmD23UCOzd9J0=",3710659852214960759,-8289646806327926646,4007793181410350879,5280688507857272095>()) {
                                          case 2086732679:
                                             break label98;
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

            this.j.remove(var2);
            switch ((int)b.a<"s2fswauvf1p4sv","NZzFQsnxOMk9ZZdV47XEqh4FVsrzlIwEMbAABwhr9BY=",-824940700370526988,-2897106307485694752,4762722514592994463,-7415613106592670274>()) {
               case 1096001552:
                  break;
               default:
                  throw null;
            }
         }

         boolean var5 = false;
         Iterator var6 = this.m.iterator();
         switch ((int)b.a<"s3ucckwttwzl88","cSJJ4NXWQbW6WPoHVj3YN3EOgzsPIQXfoGlKfIE9ZtI=",-6195587687372579981,-6386686990259204593,8141233539295474450,8436248464000030769>()) {
            case -1530531369:
               label89:
               while (var6.hasNext()) {
                  switch ((int)b.a<"s12imuca2h2rz5","FR07yP1VzMp+PJ36rI2HPsiDGtugAu9wbO2rBJTXbPg=",-8523155715358572059,-4120219746954202323,-3579555202241991872,-2835880393276214533>()) {
                     case -831769429:
                        com.yiyiaddon.e.c.d.a var7 = (com.yiyiaddon.e.c.d.a)var6.next();
                        if (var7.a(var1, var2)) {
                           switch ((int)b.a<"sct34zd5orkn7","BANDSt+u8Ed9HaGJdm+X6QUNdJsxvgpP2UEA8OZ1hCQ=",-5139151724781446905,-5765501288236180125,8954434287366914767,8400204540123385951>()) {
                              case -1484018809:
                                 var5 = true;
                                 switch ((int)b.a<"s1kbrcugqmi2h6","ZAdWGSitm/usRWGbLkLpOYxtTjrPA4XzmMWogCsDzyo=",-7481787979903305329,-5175274599971832042,5522312863806766145,-8386521537637214198>()) {
                                    case 2023022246:
                                       break label89;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s3g8auy7s7u99h","oQ6GOamFEfb7p5Q4wSzloigpP0DJuNI97e4PD2Rf+qo=",-3142029208327273727,748924151544416531,4067800508596192904,1308458599966433980>()) {
                           case -1075101966:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var5) {
                  label59:
                  switch ((int)b.a<"s3vpf1e125icjx","+pRcw5+Y61VZxC9yjl1fuuzOseSpCp0Yf3yWexXBgQQ=",1720536027401966907,-7237807249941227608,-6642954484244624325,6959016321881418502>()) {
                     case 362245115:
                        this.k.add(var2.immutable());
                        switch ((int)b.a<"sqmfypw2w5qkh","GENFUj5wJMSB8aem3kB/GXMJCV+JWOwav0FVoIJ5vrU=",-4233732828524956024,-8650967850852326485,-3502748333920135493,-3483545231035039462>()) {
                           case 683363765:
                              break label59;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.k.remove(var2);
                  switch ((int)b.a<"seo880qc104bn","GKc+KN+Tfs0sgqk+USsjHvS7j/HebBygGxu6sGprbl4=",5057710424665741891,7279172137642591085,4777644507140744043,7041444659667655321>()) {
                     case -1549150839:
                        break;
                     default:
                        throw null;
                  }
               }

               if (b(var1, var2)) {
                  switch ((int)b.a<"s1wd0xu1s0ldci","VawCAf2UDsneOUEj6BSJFM0THLVBXkKdeE6tOKffU/U=",7062743184124172663,6775324741935263030,-1863840427216629636,527827885963048218>()) {
                     case -279524260:
                        this.l.add(var2.immutable());
                        switch ((int)b.a<"s1q4f3qvvkxh22","qq6nYn9MgKXm591s/cWUYcxQjcS8s6W00wvUua+Sqhc=",991693365557611385,2327714722416790038,2753695531620242097,-7584840096617867696>()) {
                           case -1635568664:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.l.remove(var2);
                  switch ((int)b.a<"syowcbacp5avm","FyAGugm2kFKuLnmU7Knstkq2bouIdWFO3yA6VoVlacY=",-2527315235403139252,913783506534061931,-7342910730130355370,1748655963330625439>()) {
                     case -1815378141:
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

   public static boolean b(BlockGetter var0, BlockPos var1) {
      BlockState var2 = var0.getBlockState(var1);
      if (!var2.is(Blocks.GRASS_BLOCK)) {
         switch ((int)b.a<"s1uyiwcs6sbm3c","CKjEgvAlm562aL74EDqJ6JmUbaFSEB7h3cjuzf3IwoI=",-385153375219483332,7893517511057171318,-6858760893430669709,-8039346995263260816>()) {
            case 1502989958:
               if (!var2.is(Blocks.DIRT)) {
                  switch ((int)b.a<"s2eyaejjzp9ilj","AudbBmEOdLNyOdyM1VDVm1SLKkc+DTtZ7CVntdib6sw=",3191737463706384312,-8978406687233217387,-7081044998439898494,8015457343752170549>()) {
                     case -1741203264:
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

      return var0.getBlockState(var1.above()).isAir();
   }

   private double a(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      double var3 = var1.getX() + 0.5 - var2.player.getX();
      double var5 = var1.getY() + 0.5 - var2.player.getEyeY();
      double var7 = var1.getZ() + 0.5 - var2.player.getZ();
      return var3 * var3 + var5 * var5 + var7 * var7;
   }
}
