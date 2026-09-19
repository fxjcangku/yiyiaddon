package com.yiyiaddon.e.d.c;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;

public final class e {
   private static final int cv = 6;
   private static final String[] r = new String[]{
      (String)com.yiyiaddon.m.b.a<"skzm79cb7jmxy","JoDPPSdycVMdQ2FmB1FXe/l69P8U9RHztB9AzQ/5k+s=",8246142979313364670,-3898381951436381186,-7391173621985444438,-7734764469249818533>(),
      (String)com.yiyiaddon.m.b.a<"s2cbetk9nno25m","x93NGjSbz0Rcy9EIjmOuuCaJrB/XPS1+zVcynWvqLVo=",-1340632581526644777,3424284113494081033,5088263869784953434,2716916664742005093>(),
      (String)com.yiyiaddon.m.b.a<"s342e9i602ytqf","cfZVFbQ7/zMTOaf/abrPrjQJ+yHet0BK2B1u4489QBI=",-8423745175170532495,3341620473988427387,2985599103410530610,2174289284494717546>(),
      (String)com.yiyiaddon.m.b.a<"s2bellvwqadx6c","nL/fRR0N0Z0LTt/p3i27zr5+6XCIVMl+ISDgCBM8Co4=",7680758013969116154,9169353178144251384,3011482964089174520,-4191989692606546941>(),
      (String)com.yiyiaddon.m.b.a<"s251v8sex8t58m","ouwnzPEUvz0/ocX45CLXCTG5VvS4QJn290TGp+il83U=",-6255660622603489060,249299293326258496,-888873888737429580,-8932596651292237964>(),
      (String)com.yiyiaddon.m.b.a<"snemzhm0glokv","+wDnvYD1T1B6BN9HxFL+dn1/rmw809/wfkHfb5212Trf/MuNF/g=",-7813338244051723282,-1827760063587599616,-4520400065953024946,-8497649047190962690>(),
      (String)com.yiyiaddon.m.b.a<"s2oij0r6r3vbhw","edEXcE31iSdq6TJDpwhfpJBTOSfjxa5IuSf+66mA+fYA9zknfDwcU5Nm",-3537812609711151020,-8273253526051375729,6742428033337353011,-3498951815024837673>(),
      (String)com.yiyiaddon.m.b.a<"stm610ykkmyag","7Sp/5BiA5uO+1RnQ/gkDOL8CZ4TvPDgcDsQNTKNJ/sY97vgaHjY=",-4004460356472337904,1743112832609169314,7347614039070256736,8153341957523839795>(),
      (String)com.yiyiaddon.m.b.a<"s2cp1hh0l6lppb","SXBvZN5B5NZR+4mEBUPGRGa9WSrhXmLmS3CoiDAcvC0=",442559369897548120,-594652576103038020,2252440020919897310,-2727388074054263212>()
   };
   private Screen a;
   private int cw;
   private boolean at;

   public boolean a(Screen var1, String var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3p8ty5vve8uyx","SkuWvQjZGBkRUi5KzYsOGYtSTLDEGpeaga/IDS7Nqps=",6586179070018699208,-3854983693853293550,-952080448059546092,7870716932576016263>()) {
            case -1713675558:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s11g3ybop5pmz","n2fDD9fYomzhbBEizbqIocODi48P4DXoi8qOHl7k+dk=",2496110797283399288,-4949705799350912347,-4441265022461487591,9177346109799436664>()) {
                     case -1507111497:
                        if (!var2.isEmpty()) {
                           if (var1 != this.a) {
                              label75:
                              switch ((int)com.yiyiaddon.m.b.a<"s1f1dl2u3y488y","VaQ9+bPDkVW6wEIDRaX+Ei/yQVtm/SzSEdC4+sI4TMo=",7021020099328968199,7304540575705190275,-5638340439304473144,-1444437470776954367>()) {
                                 case 16101471:
                                    this.a = var1;
                                    this.cw = 0;
                                    this.at = false;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3scqe7r73bf0q","3ZsqA/yzWfm8H97iFdLXY+ZWvrUFrjK7gkkjoGITnJA=",8400047994570607336,-6233413488458860494,6038650944024284512,8310101577136295717>()) {
                                       case -1552040685:
                                          break label75;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.at) {
                              switch ((int)com.yiyiaddon.m.b.a<"spvdrwjch0bl7","LuMtKng7K1MlZKa6D0mvYj/ynI73zYGlL0/7GatRJlI=",434519713840284244,-1645746316825171338,-5542799727728068995,-5459702517862521066>()) {
                                 case 1091913294:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           List var3 = this.a(var1);
                           EditBox var4 = this.a(var3);
                           if (var4 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2m7zhc8iz9v6x","M9nX2Yhv+tvHj7ZkNbYHN/D1u8++qnY88PoAzOdm2sM=",-8123826096259999002,3815183821250771058,5017400401167317336,8766330850197020008>()) {
                                 case -209115652:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           Button var5 = this.a(var3);
                           if (var5 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3geci9zuhvpjv","im6JBgzLDnZjaPApAVfAlgHNU87/qMz/5I4ZiypRYfA=",-8362525364733135681,622445771188825599,8543678849746035452,855454270408060990>()) {
                                 case 311767984:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           if (!var2.equals(var4.getValue())) {
                              label64:
                              switch ((int)com.yiyiaddon.m.b.a<"s2o88rosqs85et","qJ+rlhQ/wtu6NfhuXxk93CUpmg9exf0yZ9FbIOIXbaA=",-6562317705392626013,5963727695269402213,-8391836796080803861,5716844597178921780>()) {
                                 case 781551549:
                                    var4.setValue(var2);
                                    switch ((int)com.yiyiaddon.m.b.a<"sgf1xdc8f43e8","N+PCls4fQW41qHw5c42zQWNn0Nj8hgXqHvzp72WuRls=",678932027518201286,-4710066127848765450,-7871574434357957613,4580824214339869781>()) {
                                       case 144604312:
                                          break label64;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.cw++;
                           if (this.cw < 6) {
                              switch ((int)com.yiyiaddon.m.b.a<"sclhwrbabmox9","Qu7Aj74pjzP12Lxi+O4pUlzJPTYo6K0MkcqEbgd6MB4=",4120955951507711432,-9008946613202776679,-362784990966621189,-741456216670900028>()) {
                                 case -250197783:
                                    if (this.cw == 1) {
                                       switch ((int)com.yiyiaddon.m.b.a<"somuu4ndyhvr3","7tASen2+skG1OqMGqXBvqLCf1grHQ6fDuwyKDQKzCZU=",4717279909122713494,-3319377961753211216,-547658946074855661,5914462165131914923>()) {
                                          case 2012155928:
                                             switch ((int)com.yiyiaddon.m.b.a<"s21ezcpafilzvs","8SHMHmIeAMEGGBjHeNrJvHWlj83L605l5MD1GqA/oks=",3655609028333936820,-697693206379630089,-4044954746483512676,-7250227303305032917>()) {
                                                case -1742776009:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1fgtwypwqrc40","2VtRPqtW5NXMvb32ZCs0D6UhPgCvxXOZzyIYm5IG6M4=",-7540635428989878705,-4842894197303195751,-5430419538821389479,2008429879008223116>()) {
                                          case -1379250346:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.at = true;
                           var5.onPress(new KeyEvent(257, 0, 0));
                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1c5n3wqi7o001","jEpQUOVvxbSwtKqBDbpquCDGXllZ91yzRf/rPs8lh3k=",-228059359293292066,-6147390508806326323,-5241624275360843426,-7838403084419938939>()) {
                           case -1172431197:
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

   private List<AbstractWidget> a(Screen var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.children().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"scwq5fnlk6vjt","r5/B7oohfDUFknbycoULfygIddVK3LL6H3OJxuq4LK4=",5046718536659410568,7223081018388626205,3575124773165253717,6814583714479807777>()) {
         case -784530877:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s10429mvkp8zdc","ZsB0axSQ+9l++mI0mTzadVeUxg46ko1UcqjZ8tc0J3c=",1429087221077931091,2364493725914585801,771812614233935289,-6792265619128718910>()) {
                  case -1013204974:
                     GuiEventListener var4 = (GuiEventListener)var3.next();
                     if (var4 instanceof LayoutElement) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s1j5rtechr6j4b","eV/cnvqRbfAEjLx2nNmgHq2fzPq5QtM5FoBBPVnQXCg=",-2357615401640307599,4436587142172775137,3202527672844098467,7804774882214569615>()) {
                           case -973893766:
                              LayoutElement var5 = (LayoutElement)var4;
                              var5.visitWidgets(var2::add);
                              switch ((int)com.yiyiaddon.m.b.a<"s2110t8vp8r12a","sfpr9Ga8SltJhzk9Pt5lt80EtwTeKaj/vzk+Sd/reZA=",-781637469579728478,-9099529979304279347,1540925063099430949,4374764812847077485>()) {
                                 case -970114273:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s19bruj4209hce","8Tio+aBGFQdE7zQ5j4O025QO7zUd90mQJOcOwT0ruXs=",-261246397270953417,-5556228942353716013,-3202776989997100504,1388270355528806252>()) {
                        case -1049910155:
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

   private EditBox a(List<AbstractWidget> var1) {
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3ptn2d3d2kxio","UMFNh9K5N5z1PdcdoQ8ttP3KcR7jRAkUrTRrNj4tZ8w=",6105506064992585795,2516586568138363398,-2988188380467990993,735460036485363951>()) {
         case -129763728:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s8styqdt5zb0i","4ceo2BWlfV7lvBCCmGqXI58uvN2E6WdJc7M/+Gj+3v4=",-953578328525708413,3370176627289042894,-7685175975209775891,-1208642161889512424>()) {
                  case 2046645298:
                     AbstractWidget var3 = (AbstractWidget)var2.next();
                     if (var3 instanceof EditBox) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ipioqd4qdzdl","jBCq4V/19XJJne4aixMCT9Kl3h9ddFdj4Utat3s9u24=",382925591335910651,-5433178696995650729,-3627078109317935394,3797308065095005360>()) {
                           case -1838532982:
                              EditBox var4 = (EditBox)var3;
                              if (var4.visible) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3mxc6uhfl9hn","n/n+xHtM3C7uGhsJ3daO2oz1UJzso8IBBryvb0fgq9o=",-9023180789456467847,-4913969366282875096,-1447192261874507840,6761184710599470533>()) {
                                    case 215837055:
                                       return var4;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sj8sdmspihi5f","lQPxA9HkKS+rk3V1wndi4KZgVUwD0xcMyxaMvkX3yw8=",5332343171702713781,4519654583592528106,-4629570877841133585,2544024348146008058>()) {
                        case -1690124565:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }

   private Button a(List<AbstractWidget> var1) {
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1agmtawobi64p","t2gvbK4U/0qnaig6/9G1wK3FKRwIv/M83ZQnrubFRPs=",9080328352679864831,-3875829717651361393,4068585740041460564,5010176190069097230>()) {
         case -887402586:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1xc8dnqej0mxz","qeinIjYoDAOgphlmABdHZzlT6JPq3cKaRhn9+vm9p0w=",-1847501419133810247,8238210671663805404,-5373170428160709974,4101960282446580580>()) {
                  case -1050991305:
                     AbstractWidget var3 = (AbstractWidget)var2.next();
                     if (var3 instanceof Button) {
                        switch ((int)com.yiyiaddon.m.b.a<"sgtpyjvcr8zv5","IhpyjceGOO+e+0MJuadf1g08WU86EStSXVaDfRN9NPA=",-4762713626006394453,4326972112312107113,-81251867012107888,8671496870167850588>()) {
                           case 1284276400:
                              Button var4 = (Button)var3;
                              if (var4.visible) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sb1moqi3btuiz","AYCHAPypq2yPt0Vu3SoWUruXvkJVj8FS4OmOpBuYrng=",-33528621258306672,1132141067279991434,-5593471183158309647,-7794628501363157418>()) {
                                    case -323653088:
                                       if (!var4.active) {
                                          switch ((int)com.yiyiaddon.m.b.a<"ssnzt40grt8ml","k1nAG/OvDhslYEBJ46JWCDyQjW2Cn5RWviD9h6Uq0Qo=",-7958402019193560581,8886928967039894625,-4123997086272252407,-5986731786834103381>()) {
                                             case -1165997969:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3qx3kry1hlwdm","5nCOmMoMreZPDfs62N9G2iL/89kswebeQyO1yhlLYLE=",5170834570822057107,-813840551191812685,8373224868052507423,1021483406243347759>()) {
                                                   case 1279045740:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          String var5 = var4.getMessage().getString();
                                          if (var5 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sva4husla97j1","WkGUJfOvk1W2D7C9fPrxuiyusBL6hNsBIpeLQXzWQZA=",-3823179981646096744,1271182631925641623,3928036243496119010,-6792141514066022556>()) {
                                                case 763094915:
                                                   if (var5.isEmpty()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s21kz3b9hl837e","kHou6ire4xFfdXeGr9BI48p8lnEC/1GAezjMxwJpf0s=",4731336792623306942,8404921097007662784,3373329705005676191,-6767910857492729729>()) {
                                                         case -1334311130:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1bg60strbw1ud","Kt2YFq6vbsuiPxcuYFQZnL6vleaXXiifjM134/E/Bok=",-2294973374343671305,8347196698532025216,4758142768149820379,5324869104501729807>()) {
                                                               case -2083483163:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      String var6 = var5.toLowerCase(Locale.ROOT)
                                                         .replaceAll(
                                                            (String)com.yiyiaddon.m.b.a<"s3ju7ixfzg1pu3","ZpMDs5qGq7VA1+Xv3eKJ1UYuYiJDdOPzp/4supOHFtbrjE1z5eUvyaUT5VfqCQCHL3eCZhoa",8012936603884157785,-7220905805870915069,8937826143501755280,7567112300289014071>(),
                                                            (String)com.yiyiaddon.m.b.a<"s3nj3rs3rtbfy1","hYEkyzVhpZ+ZkBIJXRB2IzX07XaW0SG2r2Ubgw==",-5798458531217636201,-4490131212671091435,-3641259981636570330,8571191645887714247>()
                                                         )
                                                         .trim();
                                                      String[] var7 = r;
                                                      int var8 = var7.length;
                                                      int var9 = 0;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3zeaypwhftv1","P57PeLXICKaYW+7YHa+8hfGVwA8srhY84+nECzkdCUc=",-2954876266102149350,-8759998414473245775,7387598337882255404,-7634632401933215746>()) {
                                                         case 903984756:
                                                            while (var9 < var8) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"sozrpbrwzajwx","LptfMtv2GFDk6YiLtptMEAepxgpVA5sX32q+TOxGHUw=",2559602619405593768,-6283431921525321156,-8256681398924119548,1387655511327472918>()) {
                                                                  case -339792076:
                                                                     String var10 = var7[var9];
                                                                     if (var6.contains(var10)) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sd2hu1m49e5yx","7zoKHgq4aNMxZTe2E1eUzX2qnmNoYzIF8/7n5rpUaQM=",3005048777443124353,-2322476212544889041,382073782337241276,-4954690958213638955>()) {
                                                                           case 2028656150:
                                                                              return var4;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     var9++;
                                                                     switch ((int)com.yiyiaddon.m.b.a<"se0writ5q1kf6","9SL+1q+Lbm/SK7OdaBXYNc88F7Sse2RFidgoE+e+3Nw=",8411340163258013772,-4931959827514061797,946637075375059689,-5657008670271355523>()) {
                                                                        case 805335457:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s25n1dhw4uz74s","tXjxT8maYIxQV2kBPHI7Ry9tKWeyK5QxHrRhNJO4Rsc=",1412062682382440815,3798404015808226292,-1970727424921363638,297135183661989580>()) {
                                                               case -452307018:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
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

            return null;
         default:
            throw null;
      }
   }

   public void f() {
      this.a = null;
      this.cw = 0;
      this.at = false;
   }
}
