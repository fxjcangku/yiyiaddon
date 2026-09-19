package com.yiyiaddon.e.e.d;

import com.yiyiaddon.e.e.a.c;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class b {
   private final Minecraft u = Minecraft.getInstance();
   private Block c;

   public void a(com.yiyiaddon.e.e.a.a var1, List<BlockPos> var2, Consumer<String> var3) {
      Set var4 = this.a(var1);
      switch (var1.a) {
         case 准星精准指向:
            BlockPos var5 = this.a(var1, var4, var3);
            if (var5 != null) {
               label20:
               switch ((int)com.yiyiaddon.m.b.a<"sh5gzjo6gam0a","rsNfNWV66zfD7rhNIRjs4vlkTi/G01V/1VMSM9s3iFU=",-206393749154301989,5919728844118419975,-8843086630509082866,3280690639125892829>()) {
                  case -1978836247:
                     var2.add(var5);
                     switch ((int)com.yiyiaddon.m.b.a<"s31o421uffmgf0","CrORnFhTGDQbcjc7b0qfrSDloivc/b9JWvQf04wQGRg=",8074002896685975289,-3579565795847675395,-7207561519342979249,-5536174427951576754>()) {
                        case 421713377:
                           break label20;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s1mb77yyyvsspn","dN9nyxqPcPTbKTaL/CUNmJbL00aEtxwQSl8Ukg8rvJI=",3514949459627679715,-7423270718868089958,4376846176193580424,2027503769928935559>()) {
               case 1040289319:
                  return;
               default:
                  throw null;
            }
         case 范围自动扫描:
            this.a(var1, var4, var2);
            switch ((int)com.yiyiaddon.m.b.a<"swaqhtbur5ilz","8Nq9T0zzEmMrALePdEqKZYLoSHpy3lslulEbxzSQMvk=",-7058058478922069148,7464015030044149634,2057403683720832327,-7305865356464178065>()) {
               case -845510469:
                  break;
               default:
                  throw null;
            }
      }
   }

   public void bo() {
      this.c = null;
   }

   private BlockPos a(com.yiyiaddon.e.e.a.a var1, Set<Block> var2, Consumer<String> var3) {
      HitResult var5 = this.u.hitResult;
      if (var5 instanceof BlockHitResult) {
         switch ((int)com.yiyiaddon.m.b.a<"sb6acnf3rnwwr","3/9hyo/DSvjH/DyNtfEm0bwKS0JlFAzbaNCiv9v2nao=",2210410829623379545,3139817461105672761,60706953366384764,-89178563917208252>()) {
            case -1190527326:
               BlockHitResult var4 = (BlockHitResult)var5;
               switch ((int)com.yiyiaddon.m.b.a<"s40x6jxnqvdk7","rcbNcfAynTwVXLr5sTYSezEkYN+A+9g5D6gi4WhkL9A=",-6923492923449653269,4800853930084502491,3022260430832559578,-1478101072373042725>()) {
                  case 235317938:
                     if (var4.getType() != Type.BLOCK) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3u3xazl9rahxy","sQx0/t/n+e0PtoI36GRrMY2aqpwAzg0+bO1D0/pVKdI=",-2026632316754895911,2753730339590746099,1885560944352056981,-1302065401290559768>()) {
                           case 367595530:
                              return null;
                           default:
                              throw null;
                        }
                     } else {
                        BlockPos var9 = var4.getBlockPos();
                        BlockState var6 = this.u.level.getBlockState(var9);
                        Block var7 = var6.getBlock();
                        if (var1.aD) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2bhrppgpqrtix","+zEy5ejKjox9+5nAVuZabV+HPrRWnkc8l3D7/C//Ms0=",-7196028151941042925,5244058531089715232,-1410294336369152260,6678635390203733469>()) {
                              case 1942700346:
                                 if (!this.a(var2, var7)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s32p0mws59dh3g","+dJxoXpIZKbz0D1SEX/Q1dFC14AaHz+4z44bbzkMJ9U=",-4251293059096944564,-1862605777399918053,3631867984532809974,6122053515902870795>()) {
                                       case -1963822996:
                                          if (var7 instanceof BonemealableBlock) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s23zz4koo93d82","MunfZip3Od5LZ0JwspXSunm3mryxoXs1e4KcXqWDyfk=",7486868407066882707,7728493164261509988,-7835046180112155209,-6729755437354141416>()) {
                                                case 236142908:
                                                   if (var7 != this.c) {
                                                      label47:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3irhbrl9f3n6i","GqVQ00wSdUZBTWkR9xZSaUbdYYz5HPrJd7CqACayoXM=",-2344476625438384086,4949207211849176464,-4200476799954914660,4094334324002748806>()) {
                                                         case -226219793:
                                                            this.c = var7;
                                                            String var8 = BuiltInRegistries.BLOCK
                                                               .getKey(var7)
                                                               .getPath()
                                                               .replace(
                                                                  (String)com.yiyiaddon.m.b.a<"sxosn7aqwlmeq","LW4IO0PXb7419SN6eny/nJMdekv4k63e1ErXg24D",8377263941338956438,9202749048668700740,3182746687113294266,-4392944179161047349>(),
                                                                  (String)com.yiyiaddon.m.b.a<"s339zi5iiox0xw","QvTmbQK5OtSldAPz8zuMoqwQ2a2puW+lH7LMlkPz",8598036049755667943,-7088192260238667706,2030098181441836355,-4927741528449115945>()
                                                               );
                                                            var3.accept(var8 + "");
                                                            switch ((int)com.yiyiaddon.m.b.a<"s18pcvqf022h84","vs4lQm7GGMZoZwB+ub7uMUHrARIwvpBUJGIErsxKI0k=",5895845082244019934,8696163016335881560,-8636846441890949329,-9045634982337481868>()) {
                                                               case -1417795159:
                                                                  break label47;
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

                        if (!this.a(var2, var7)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1szr49giolggq","rqod/lkbyhvJ1DiZQQSVKZS9zeoyBVe1SDGZuEg9/FI=",8366666071478747844,-348415783723540068,4247721686892896766,339191473090787861>()) {
                              case -439989032:
                                 return null;
                              default:
                                 throw null;
                           }
                        } else {
                           if (!this.a(var9, var6)) {
                              switch ((int)com.yiyiaddon.m.b.a<"sfof3a9n27rb0","G4EBJtCJkTPgEg9kvn0C+xPYY9e3QyLhQ4uR3Kre0J4=",8468906068478730626,-2863613202173660828,-1369389780658290863,-5559897339408519582>()) {
                                 case -1082742461:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }

                           this.c = null;
                           return var9;
                        }
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private void a(com.yiyiaddon.e.e.a.a var1, Set<Block> var2, List<BlockPos> var3) {
      int var4 = var1.df;
      double var5 = var1.df * var1.df;
      BlockPos var7 = this.u.player.blockPosition();
      int var8 = -var4;
      switch ((int)com.yiyiaddon.m.b.a<"s2uhrg9rywgf48","ygc8h8l8+JraTZTlOW3nA4uIH/VW5Ne5lwNZ0nqyEcU=",-5498331710546866928,-762875224688958521,-6068248170295760838,-8606150983400871436>()) {
         case -1723877293:
            while (var8 <= var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s2gim2acuygxel","TXG88R/Vs1sBBN6r9ecolB+jdiuebqsz4aVgbvBNRmQ=",3148886780887783680,3506014820850364726,-7076448711356318842,640889855003101220>()) {
                  case -1069414001:
                     int var9 = -var4;
                     switch ((int)com.yiyiaddon.m.b.a<"s1lanaccmmuii0","Kxz2AByYHzZmy75Vn3G7VgNyCelmljj7aOKdr15TbHY=",7083569377056434307,5008451350496895338,-15849373583564067,-6650345870687515380>()) {
                        case -1274983273:
                           while (var9 <= var4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1dcdqdikox7r6","/l+qyeVENBzCM74wa7yK60wBJr1aHViWwbvJJx/stTQ=",1462039666090340069,6902821935075125631,-3209321410550383894,3251550669008478186>()) {
                                 case 854590701:
                                    int var10 = -var4;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2r8jtm3yxuw4n","eiByETuWEN/J2N1wlFEmdNwfq/cMSAtP+/NRyB11HkY=",9025173013577636830,-9182479128651951013,-6165554114941218617,83109670008017578>()) {
                                       case -1384015944:
                                          while (var10 <= var4) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s11l8vyacq8kqf","u1YK0IAaeP2ssnlkus2yjtBYysHBnFjgPIGk8Ph7YFU=",1971543066791808010,-6471310516239875528,-1339844542715938613,-1339814684331226264>()) {
                                                case 1088154800:
                                                   BlockPos var11 = var7.offset(var8, var9, var10);
                                                   Vec3 var12 = Vec3.atCenterOf(var11);
                                                   if (this.u.player.getEyePosition().distanceToSqr(var12) > var5) {
                                                      label85:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3jmvfrgbc4fyx","q7OW6+8q+61/CgndbOmkJmYLg+jSaI85fWeQakn+Ug4=",-3248272295547063573,417997313775840833,7153358652623373467,1556428839948910579>()) {
                                                         case -485393940:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s10bpdxs4eq8ol","ebEqvKcY5nTSaT4GJzP4CBZgVD/X+YCJ25DDUtNZT7o=",845001117023539955,-1780983312523190716,3741551792609417794,3869466317177342805>()) {
                                                               case -279317808:
                                                                  break label85;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      BlockState var13 = this.u.level.getBlockState(var11);
                                                      if (!this.a(var2, var13.getBlock())) {
                                                         label81:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s19s8kr4kfpmxw","08OTdctnRdCQ696IzxvatgNVYNMRjRES/sscmR3RnU4=",8526610947203214300,-6203546023762415691,3268208815337194704,3335959090737920842>()) {
                                                            case 1507680242:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s28jmstmb8osml","ZqdSyIcDUNSHlgnBYm/xsDHKwHKbL+5nU49GM/RUWA4=",5787173122184706038,5481104965694794595,7456873475679665374,2264514450312728630>()) {
                                                                  case -309808517:
                                                                     break label81;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else if (!this.a(var11, var13)) {
                                                         label77:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s13b7fkbamrw91","tRf84l5WU2c0pRp6qnb8EOJSZIcop4t3w/D81V+ChJ8=",-5224301798389552062,-6098496077122530982,-5823456660062047794,1814528457656394481>()) {
                                                            case 1558238048:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1wvrzti4ps84b","7zJyth2/MU+wygfOV9a7Zi0dzgVn5bhnYZOb42ZDqRI=",-532039853413189422,5077511068451748055,2447176690098261590,1985861465635021303>()) {
                                                                  case 318837961:
                                                                     break label77;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         label109: {
                                                            if (var1.aE) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"sh1u27gz23udz","9mPZPfZXMPNpf3DbZKCghUDVYFuSGDz59bBbgNzFNrA=",-1527780577910252254,-5594425194139160167,3882538128645668768,1220824107054983913>()) {
                                                                  case -1929704741:
                                                                     if (this.b(var12)) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s5cmv4xjv046x","92uGYheo+yYlzvP8tYJdyTAkQ8pEzmjciXzXpTJpmW4=",-1433781319813591095,-1217956278139505418,6698710977795891960,-4421452460716758862>()) {
                                                                           case -1858714835:
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s3hpakzwjuiv2i","sJkBpFXu7rvNZsEMqjy8q0hbQsIra+8iGI/3VmK8z9g=",8240515495719659685,-7369558048748476615,-21098313887637791,668483365602484868>()) {
                                                                                 case 1597377096:
                                                                                    break label109;
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

                                                            var3.add(var11);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s34tf26i9sva1n","YBPSloknDDi4PnhlvxTB1N+i3aF9mgpzx78/TBPqUfI=",-5785201817345567637,2266326804645142734,-1609738779166624485,349126649845260244>()) {
                                                               case -2129549576:
                                                                  break;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      }
                                                   }

                                                   var10++;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s14f0nnsahutoc","HnEraLe3otmIAxiE7oHWw56HgsCJfyfeJwkz6BdGc/A=",114507669536718388,4174042342866822959,55461311059201023,-5236809001249741975>()) {
                                                      case 873412824:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var9++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1e5qz49cyk7cc","mVMArs48P9KsyRHG/o5dmnyBXbMdFetFwn7SDqcWc8Q=",-2946008362808936517,1938549245153109805,-3317166365454020948,5075978156846278982>()) {
                                             case -1820342082:
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

                           var8++;
                           switch ((int)com.yiyiaddon.m.b.a<"s22wgxdag1jk4z","64jW7ruZjB54RWLDujnPrGfl8zc57QXybWAtGiOpEKQ=",8721038683190808250,-4356570275217745984,-4313293642579249330,3281241565378680823>()) {
                              case 131604762:
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

            return;
         default:
            throw null;
      }
   }

   private boolean a(Set<Block> var1, Block var2) {
      return var1.contains(var2);
   }

   private boolean a(BlockPos var1, BlockState var2) {
      Block var4 = var2.getBlock();
      if (var4 instanceof BonemealableBlock) {
         switch ((int)com.yiyiaddon.m.b.a<"s3c6joot7ax2d2","VyshM0NxVKojSD5u/xWA+p7mnaKnBe2owN+fQD2MYw8=",-801862655118249263,2448760859075330869,-6926047130215926220,2637225171274426735>()) {
            case -917350486:
               BonemealableBlock var3 = (BonemealableBlock)var4;
               switch ((int)com.yiyiaddon.m.b.a<"s2nx3o7r4e5oid","kZI+0lULoKPQwNA1FMiMA2wK+jpGh2rFQtIJ93c7fiU=",3825262808341321020,3921333486173022890,9018925145905001323,4240041047941131729>()) {
                  case 1498345554:
                     return var3.isValidBonemealTarget(this.u.level, var1, var2);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private boolean b(Vec3 var1) {
      Vec3 var2 = this.u.player.getEyePosition();
      BlockHitResult var3 = this.u.level.clip(new ClipContext(var2, var1, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, this.u.player));
      if (var3.getType() == Type.MISS) {
         switch ((int)com.yiyiaddon.m.b.a<"s34ln683egnhw2","D5soqfDfr2a0ol/y539j8krAxFvCHqa90tOuw00cFCk=",-7217737663682632779,2887736033491210030,2643418407334490863,6681882006065175919>()) {
            case 307722091:
               return false;
            default:
               throw null;
         }
      } else if (var3 instanceof BlockHitResult) {
         switch ((int)com.yiyiaddon.m.b.a<"s26tkgbho3sbak","SYc5lWk3BQFp4JgyemopMGo0Fd2Yw2zePBGXmd4d13E=",1028395400800335209,-336290988431466143,-273844083789465860,7696671301337974562>()) {
            case 1522831296:
               BlockHitResult var4 = var3;
               if (var2.distanceToSqr(var4.getLocation()) < var2.distanceToSqr(var1) - 0.1) {
                  switch ((int)com.yiyiaddon.m.b.a<"syjgs74y6v81","h4GX1eLlkrDyJjap1aUAPbjRetSNvdSkZCnfPXuUEd8=",-4388769793355896740,-6168829984507079893,-1948893615504029208,6810732981615114311>()) {
                     case -1796021479:
                        switch ((int)com.yiyiaddon.m.b.a<"s1hk6gjkenuz4l","pvS9CEtwsarXmeA4OU1QOxlVwVaEgE3s97nhYZ0HqSU=",9127789514026267510,5535437822486623139,-5809502622017420646,-9190395719221276476>()) {
                           case -1965546185:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s209ve1vnkbc7z","8U1JEup41os1y+ozH0Um+PgsWKelQUYdxCdcsrE0tX4=",2212737885140290257,1085588939391149252,1048257490279838327,-8000595445064232137>()) {
                     case -322010238:
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

   private Set<Block> a(com.yiyiaddon.e.e.a.a var1) {
      HashSet var2 = new HashSet();
      c[] var3 = com.yiyiaddon.e.e.a.c.values();
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3p9ya0o2nb146","QzJaBgLzxHFFv99O/DYtDmkOZDeUbOnFgOymtmIB2ZE=",-4785347607978107294,-1734844506680441751,8881721851497228412,-361199224022141946>()) {
         case -1669931737:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s3cvocjc0zm8k3","Rwhx70rdUKMa8JjC6p+K/IOZSY7iqPTjWFp2mnN14E8=",1451160042375730872,5920464661368187340,-3800591489177436767,3664167446877904497>()) {
                  case 1976576447:
                     c var6 = var3[var5];
                     Iterator var7 = var6.a(var1).iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s26wss4hdyniow","Yckn1ZyjfaCiaJrVuaystVdGK/OEryOf/OFYwSEZ9hg=",6260116146871099250,-7665074152675972953,2561488361439340196,7333962089866471141>()) {
                        case -2058525072:
                           while (var7.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1fdp1cu3g3vc8","VYJOwVHtLhvifiQ8EuhopOdsGsLeUvR4RYtkw4wBc5Q=",6075803231730307253,5343568557177049508,-982445100768281923,-7484758861578135936>()) {
                                 case 1161277695:
                                    String var8 = (String)var7.next();
                                    Block var9 = a(var8);
                                    if (var9 != null) {
                                       label36:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ou6yp1ynuzzu","uwGZfkNsyBRiOeizJG2R5BtXH3B9dFCx84wE9sISVIE=",-7007909304712500195,3554890513974514337,7174813006191809129,-5422335328791758573>()) {
                                          case -521836695:
                                             var2.add(var9);
                                             switch ((int)com.yiyiaddon.m.b.a<"s375zv8u29al87","T0KEx80qQ4cJZpHilX03ms91oVu5UiGf1Ng2fqnuYSI=",5898412720267859212,107672965389602882,4590587289848467727,7896097007019854046>()) {
                                                case -1723195101:
                                                   break label36;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3pmtuf6k8mi4a","FMvceDAmEcg++LaTnBxvP0NG/fpuIBoNdmYx2atKE+0=",3046897301418431402,6586603288659679376,7592980251971475377,-7098984058422571835>()) {
                                       case 2038975779:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var5++;
                           switch ((int)com.yiyiaddon.m.b.a<"sm46q6x1i7717","L7jTYfNRq+QQo1iRXMqwARxF3R3tRFtdGS85h1rcWUo=",1767262736895540755,-935386567494856595,-6563003501974685792,6268651977921009627>()) {
                              case 460480124:
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

            return var2;
         default:
            throw null;
      }
   }

   private static Block a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s6tfwgxtywp40","VW8yojXNNYo2HZKM1G+BCMesKtLtVajNEBxnpd79j/E=",-6008443677173644290,1876158614990339889,218795046079080798,-6094400990476671546>()) {
            case 1040893783:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2kq3x3h1ywsvf","OGrLrQJXDRD5hv4XLShEloZOWaM+WGoZPcDxhY9TOOQ=",-2048212777777406750,621907620072329858,-5481747565357101989,6438742823143291193>()) {
                        case 572833395:
                           switch ((int)com.yiyiaddon.m.b.a<"s2jfy762cpdh0b","zIqJNkPW0s01ZynBjYzJ+PfBNwTfSer/PkhxRYJP5Mo=",-1290638393962267398,5455983014015785382,-9198116363154376462,827608712686234890>()) {
                              case -1814483536:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     Block var10000 = BuiltInRegistries.BLOCK.getValue(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s359klrmtzau3s","HbbubWrrSedaKRpxhkjTLfXIzSb8CLxHpXxhzVw4GQM=",-1317106580242755554,8396419492976419917,6588095054109945603,5196844783568612731>()) {
                        case 1987327014:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wsx19mijmeh1","jyB4kr9l1ZgYQegRQHtof2+PpeVLWWhE39C5IEO0OVk=",1600742041442596406,287281370441475288,9087634080070666520,4060435449716706996>()) {
                     case -1665536969:
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
