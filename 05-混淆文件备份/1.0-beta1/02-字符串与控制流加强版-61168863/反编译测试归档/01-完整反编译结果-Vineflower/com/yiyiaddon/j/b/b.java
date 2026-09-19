package com.yiyiaddon.j.b;

import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class b {
   private final Set<String> aF = new LinkedHashSet<>();
   private final Set<String> aG = new LinkedHashSet<>();

   public Path k() {
      return com.yiyiaddon.i.f.a.j();
   }

   public String a(com.yiyiaddon.g.c.a var1, boolean var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28r9927durje1","aYxep7o/UH6cJ84cQxjVhZyPSL8YAgVosfSA/kgca8o=",7151016086929529317,-7366408599667528453,-3060957241154554106,9090605844438766735>()) {
            case 1155873285:
               return null;
            default:
               throw null;
         }
      } else {
         String var3 = var1.fI();
         if (!var2) {
            label46:
            switch ((int)com.yiyiaddon.m.b.a<"s2pz28jp8p7wsq","eYgmk3rwTkfxDYDUsqKlf3YnF7wN5fQ8smbtV0M8hXY=",-8985247625723689165,-480650413193536106,-8703524380530719324,-5527727749886668791>()) {
               case 1333844851:
                  if (this.aF.contains(var3)) {
                     return null;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2wme85iprlt18","NQEHnwnl4ntHXS5G3wMzv2U30v+gqsTjY4QV0izhMl0=",3232638412168763357,-8418494367040567259,-1045124088726950594,1981818274086458130>()) {
                     case -918421288:
                        if (this.c(var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2f1kqciopnmy5","dFGhUstFL0spqsuCoeU9yChSDdkWIf26OGcv1hmUUPk=",3790923326780755178,6735462942270841230,-5064490807258527418,-5331428137328325652>()) {
                              case -2011137896:
                                 return null;
                              default:
                                 throw null;
                           }
                        }
                        break label46;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String var4 = this.d(var1);
         String var5 = "" + var1.aj() + var1.ak() + var1.al();
         String var10000;
         if (var2) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"sc1fatp6ub9yt","u3eim0NnrHmqbJgF7VrXtDZYbr1WEtC3vkDJJhjxvZw=",-896600536351321614,-8775382084580149702,-6549807983444003820,-6320731555040259200>()) {
               case 1605943731:
                  var10000 = var4 + var5 + var3 + System.currentTimeMillis();
                  switch ((int)com.yiyiaddon.m.b.a<"s2b8b1agqolso","3s1o4OMGTYYvqLjoh8udy1/lc9Ddi8grIf1S6ORfxJw=",-4984450757921831251,1094933052382947256,-2902233117201933602,-5719477064487815692>()) {
                     case 67973645:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var4 + var5 + var3;
            switch ((int)com.yiyiaddon.m.b.a<"s1mx0hc1x0upqp","oKsR4ZUY3H9ugtEOB4OeWFgUR5GPO/owJqwqClEUydY=",7510304879929282771,4132582892719148733,-7968014405052290500,1163190629842511502>()) {
               case 179679628:
                  break;
               default:
                  throw null;
            }
         }

         String var6 = var10000;
         Path var7 = com.yiyiaddon.j.a.a(this.k(), var6);
         JsonObject var8 = var1.f();
         var8.addProperty(
            (String)com.yiyiaddon.m.b.a<"s86reagi2p03q","/S7JmSG/bpkLoWY3Mn53p4jjTKtRbd0LoZeq5+gnLPUy9Hkl",-2305202484650106484,7205174448131117690,463087383530071750,-3476271576362803306>(),
            var3
         );
         var8.addProperty(
            (String)com.yiyiaddon.m.b.a<"s166doifd6wxui","gRJAzeKCdb8wIGep2zb3aPsu3q2P/Bax4JKqBRf2Mv75ztpY",6471842225176605997,5880384059349308644,-2355288821837137683,-434094746410856271>(),
            System.currentTimeMillis()
         );
         if (!com.yiyiaddon.j.a.a(var7, var8)) {
            switch ((int)com.yiyiaddon.m.b.a<"s148c0j0d680lh","WKCZ9lLRbyTqhXqCLeqPFIeFsN5oMRGfOIB/7Bh79WQ=",-6179053427890064721,728999712533195459,-8127245216441597050,-1420965999870215215>()) {
               case -1894353620:
                  return null;
               default:
                  throw null;
            }
         } else {
            this.aF.add(var3);
            return var7.getFileName().toString();
         }
      }
   }

   public void C() {
      this.aF.clear();
      this.aG.clear();
      Path var1 = this.k();
      if (!Files.isDirectory(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lxqraehm1a7u","LNysEBgQ2ls4x5nw0Ul/mrmspXP3Sicw6tB0xkaUp64=",-82744493375728955,-5348357718117318660,-4220119634315919888,-5887894890550935978>()) {
            case -1443739575:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var2 = com.yiyiaddon.j.a.b(var1).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1u0t4h4lyumt0","Cf+PIFBMm1GdffpTO/OipNUCalimh0R4bTG9JnNixxU=",671276931891343229,1260426120890058082,-1152516936001889097,-7171133810003143987>()) {
            case 447085986:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sa83mppm4hyej","GNTvWMQJ6ojplO5FaVcbBfTYE8FoEpRnBLJ0xIdnRts=",-7176034948179850279,-655320122896485124,2993937978066110637,5547927045261678154>()) {
                     case -1526606499:
                        JsonObject var3 = (JsonObject)var2.next();
                        if (var3.has(
                           (String)com.yiyiaddon.m.b.a<"s86reagi2p03q","/S7JmSG/bpkLoWY3Mn53p4jjTKtRbd0LoZeq5+gnLPUy9Hkl",-2305202484650106484,7205174448131117690,463087383530071750,-3476271576362803306>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1k8a6tfzo37ki","Mz2tDGGVaiw7JkrwuUzmudVvGWyyoY1oJVp7tdwMWwU=",8387977699331128532,-3143531489497997894,7614734448334265374,-3703886343700708499>()) {
                              case -1618443403:
                                 if (var3.get(
                                       (String)com.yiyiaddon.m.b.a<"s86reagi2p03q","/S7JmSG/bpkLoWY3Mn53p4jjTKtRbd0LoZeq5+gnLPUy9Hkl",-2305202484650106484,7205174448131117690,463087383530071750,-3476271576362803306>()
                                    )
                                    .isJsonNull()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s20i58hy9gckus","dXK+pFRjlgmuNcuJZKEWFCOxm6n+TnmthEsievXkPkI=",1157254067699789707,4947185185145926439,8949090739888717138,134837684190914656>()) {
                                       case -589883171:
                                          switch ((int)com.yiyiaddon.m.b.a<"sdgzov43wu1nd","3lZ3FeL0r5OLDxVRKMJBMgsb7OsodDZLDJzAnm2dN7A=",6905471216052541804,8948040721179923800,6248057626554094837,-927678725471368155>()) {
                                             case 1717253491:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var4 = var3.get(
                                          (String)com.yiyiaddon.m.b.a<"s86reagi2p03q","/S7JmSG/bpkLoWY3Mn53p4jjTKtRbd0LoZeq5+gnLPUy9Hkl",-2305202484650106484,7205174448131117690,463087383530071750,-3476271576362803306>()
                                       )
                                       .getAsString();
                                    com.yiyiaddon.g.c.a var5 = com.yiyiaddon.g.c.a.a(var3);
                                    if (var5 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sz3hqnql6cffj","HkRiz4kFAGwPt9vHYLVGvRAsCm/xK6VDwApYmArPLsM=",-3989695390642779554,-5188540825186108064,4324413746031288551,-1810124786274286509>()) {
                                          case 247266062:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1r5v2mfoixnod","2pJ/utpt4yGKesGNWNaQZTm/3ra/TXWv3cV4IozA3wk=",8679483594409006027,-8844747278062065636,-5064266723621695422,-4618586955768568609>()) {
                                                case -677377976:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (var4.equals(var5.fI())) {
                                          label67:
                                          switch ((int)com.yiyiaddon.m.b.a<"s4au70pdlpr6g","kMMktKAWlaxdpSzXubgCSlEmg3136yG2IChJVJb/FhM=",3865760736164574136,-8864776272631594985,-7583412437960704917,3447931850716147205>()) {
                                             case 231207765:
                                                this.aF.add(var4);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2mzloao5zajdb","In2jHqkw2wDrx0C3KlYayIS3lFoWBP4DtLdNz9jxJHs=",4589263580584960919,-634398182731220124,2575304133369453089,-3522088935820808095>()) {
                                                   case -1678736662:
                                                      break label67;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else if (var4.equals(var5.fJ())) {
                                          switch ((int)com.yiyiaddon.m.b.a<"si9sxfy3e0cer","NLm5zfhkWEtddDltk9SEqWv/ukIzJXV+Xu46ZxwcTJ8=",-7912266648100661566,-6818647670839330713,7546847722170619313,-3348833056813494131>()) {
                                             case -576597834:
                                                if (var5.fG() != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1ajutyjikrxy5","6rzXjPGCpoVftHlMqEzilF1mj11jMNOUDMd2JIThsM8=",7639594279401259855,-3750722537011101724,2868311677779995747,-7795573270580766715>()) {
                                                      case -1667774865:
                                                         if (!var5.fG().isBlank()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1bze8pfiqtd5s","caEJVm150s4aaXS9ncmodAYIaLi6akXNbCEevw8qHzo=",1419643537077826648,81569519600697715,-3738389705579653468,5873624297820468787>()) {
                                                               case -285538792:
                                                                  if (!(String)com.yiyiaddon.m.b.a<"s29rs5p8k2g7m2","n/UUibq4OYZXBqsrnsApSQo1YzooPm9W515MEcn8SYjvRZJMrlfYW/0i",-768834582820269021,7424851966592921328,6389234371663673189,694798281226273972>()
                                                                     .equalsIgnoreCase(var5.fG())) {
                                                                     label63:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2jc561tn9o2it","NcHn+fSyST1yyLX51EAfseIVXC/UZrOKaac9W63nhwA=",1526976280738295532,-5557840551514069813,6089636572212599839,-6343942050258525936>()) {
                                                                        case -1427742162:
                                                                           this.aG.add(var5.fG() + var4);
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1du2npzlmqg34","FOCHmScMmTSRWvVjYsGDiCQaqcMfzrvAewYj3542/ek=",2636566007670068971,-8631330122451299416,8375140284501038475,8020659989873641116>()) {
                                                                              case 425101655:
                                                                                 break label63;
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s3lntc87bpjgr1","daD/bY6w2aqGLsY35whgCpsA7w+7+JO61DX5eH0z4ss=",1507201502075224599,-5628107952906316084,79446365343233186,6176964896792110204>()) {
                                          case 1817218224:
                                             continue;
                                          default:
                                             throw null;
                                       }
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

               return;
            default:
               throw null;
         }
      }
   }

   public boolean fx() {
      boolean var1 = com.yiyiaddon.j.a.b(this.k());
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hnwwr44t1w4r","A9yjqPJB6zdag5GQoT+dsVzK1G5RAW/y6CQtLHkymWM=",-7736552523489332979,-6912535499463026651,-1240080210955300776,-7318087989955019991>()) {
            case -258721964:
               this.aF.clear();
               this.aG.clear();
               switch ((int)com.yiyiaddon.m.b.a<"s23gm7ol2d08sq","yPv02LajWiQhQqi5vM9jMWyVW472JuQB5h/LAFGs7jQ=",-3134927135583985795,-6042760086367034592,8868694468342147830,-5898910687673418214>()) {
                  case 1125779017:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s2r1agdc75xfzm","07nMZr6RsSwjpuY/9EQ3na2A8ywoEDcFYJ6ktR1R8Hw=",5351732380793640130,-4051488488513608853,2530983843029819748,-8550327709891677187>()) {
            case -1716228893:
               return var1;
            default:
               throw null;
         }
      }
   }

   public int a() {
      return this.aF.size();
   }

   public boolean dt() {
      if (!this.aF.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v5zc7kwplb58","0rpplT6V0q7vUHz7hCFE3jfsJU72nhFX/jsVbzG2ulw=",-2225229222376770202,-5373961662229163448,6127990273206004632,-8083508910140430157>()) {
            case -273507038:
               switch ((int)com.yiyiaddon.m.b.a<"s3qkd83ijpehwz","MkCG1uLHQXGUP3Ofoai1BENmowE9aJdb7XKQdHZwRwg=",8123631310288433729,-2647936180354460951,4122709792645313485,7259364257521833963>()) {
                  case 1480348426:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s20nlmdw6xiwvk","IvX9sTSeUU0NZzNsyNBYvy50hgoim8oCVLSEvd+xHd0=",-3358469280631797016,-4272912753803014022,7396324160228891239,-9046251525383963582>()) {
            case -1312361350:
               return false;
            default:
               throw null;
         }
      }
   }

   private boolean c(com.yiyiaddon.g.c.a var1) {
      String var2 = var1.fG();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1601ley807ykr","e/w+YRWyb4ict2AZeyubrEk/SLSQZskn9dGsn9vxs0g=",5610675231800408103,7484944790072293757,-8464114541115752600,-6390319723298562522>()) {
            case -1471244481:
               if (!var2.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3plz8s5byswas","t0m2MrPqRGfHsxwnIqfNDwyhSVVmx2VFsds/y5/zwtI=",-8411635187557805383,-8041285386488734926,7505269812126306017,5548746097103072885>()) {
                     case -979382633:
                        if (!(String)com.yiyiaddon.m.b.a<"s29rs5p8k2g7m2","n/UUibq4OYZXBqsrnsApSQo1YzooPm9W515MEcn8SYjvRZJMrlfYW/0i",-768834582820269021,7424851966592921328,6389234371663673189,694798281226273972>()
                           .equalsIgnoreCase(var2)) {
                           return this.aG.contains(var2 + var1.fJ());
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3pr4vz0lsso43","w19gQpcQKwVIopdJJ/hKlUP/1kQjhmesfO2md023/mY=",5659700346881684029,-7051941753586737172,-7122308686169224753,-9051361484962982077>()) {
                           case 816948289:
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

   private String d(com.yiyiaddon.g.c.a var1) {
      if (var1.fQ() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s31tutszf8jizi","PjTf88T4F+GeBIs9YHODxt+y54YNcFjPZIkG/4ZaS74=",2609464090611332981,8673382638603054509,6701680196392143854,-4842641295230644351>()) {
            case 1483634575:
               if (!var1.fQ().isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s30a5glpvva4wy","bGa0vD/QX1EA2VWOrSZj7pi2nHowb1objewOgrBrKJ8=",925890601873397563,-3589782445979730525,7372013119550029363,-8674289045120437854>()) {
                     case -832041259:
                        return com.yiyiaddon.m.a.B(
                           var1.fQ(),
                           (String)com.yiyiaddon.m.b.a<"s36zar53srhesj","enCf00uCbcOf8GsfkL6tHznyMF6vwK8ljwMtXqLVmeADsaIo/qk=",5469015378608551555,5135263937316163344,-833742734855208242,-4924743999223748056>()
                        );
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var1.dr() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2s3j051i945po","s0402XsltdrSekBDlgrHBWq58BVo1L4qLG1fnC26ELw=",7815674748051296336,6192252083328899373,-3895974246523097612,-6969824534272505925>()) {
            case -605625768:
               if (!var1.dr().isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zcdrvih5te0g","ITbLE72AcqTtxcFW42RxQRR07VSc70VvrJErTh8fGnA=",8356193932986425630,-4098265093405659492,7032501079604738103,2773391218993733295>()) {
                     case -1815018329:
                        return com.yiyiaddon.m.a.B(
                           var1.dr(),
                           (String)com.yiyiaddon.m.b.a<"s36zar53srhesj","enCf00uCbcOf8GsfkL6tHznyMF6vwK8ljwMtXqLVmeADsaIo/qk=",5469015378608551555,5135263937316163344,-833742734855208242,-4924743999223748056>()
                        );
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return com.yiyiaddon.m.a.B(
         var1.fL(),
         (String)com.yiyiaddon.m.b.a<"s36zar53srhesj","enCf00uCbcOf8GsfkL6tHznyMF6vwK8ljwMtXqLVmeADsaIo/qk=",5469015378608551555,5135263937316163344,-833742734855208242,-4924743999223748056>()
      );
   }
}
