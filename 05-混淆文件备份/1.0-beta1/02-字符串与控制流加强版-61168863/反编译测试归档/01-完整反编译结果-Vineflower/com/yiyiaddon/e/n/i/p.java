package com.yiyiaddon.e.n.i;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.locale.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class p {
   private static final String sS = (String)com.yiyiaddon.m.b.a<"s733gmi6v5r6r","fGrjNlgK7lMh8j8t6e5MOa0F97bDFug3bmGM4vCVHdevF0r2G8jRHjqyi0qn+QFSirs=",-1974351262508103615,7820440842128237385,7267031620704227494,1346750096444425245>();
   private static final Logger n = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"sig3r8ur2ob75","rM9erpvqfYUjM8npZSARPFxscJ0DUDXU7yfDItQ5MwrPS9XBoGzYdir3P19+PLy2CRNfGXrQyr590pA5OvY=",-8602729802423560133,-2978066440556674447,-2479832074052760869,-193511944311613952>()
   );
   private final com.yiyiaddon.k.b.a b;
   private final List<com.yiyiaddon.e.n.i.a> bN = new ArrayList<>();
   private final Map<String, s> aa = new LinkedHashMap<>();
   private final Map<String, List<String>> ab = new LinkedHashMap<>();
   private volatile String sT;
   private final Map<String, s> ac = new LinkedHashMap<>();

   public p(com.yiyiaddon.k.b.a var1) {
      this.b = var1;
   }

   public void u() {
      this.bN.clear();
      this.aa.clear();
      this.ac.clear();
      this.ab.clear();

      try {
         this.gF();
         this.sT = null;
      } catch (Throwable var4) {
         StackTraceElement[] var2 = var4.getStackTrace();
         String var3 = var2.length == 0
            ? (String)com.yiyiaddon.m.b.a<"s1xj317288yjxs","PVxKww23eIdpH3gHDENXPDwg7cXV5KaMVT/78A==",7415893214764442574,-4510034983957122647,3586531709416583121,3609706977406214742>()
            : var2[0] + "";
         this.sT = var4.getClass().getSimpleName()
            + (
               var4.getMessage() == null
                  ? (String)com.yiyiaddon.m.b.a<"s1xj317288yjxs","PVxKww23eIdpH3gHDENXPDwg7cXV5KaMVT/78A==",7415893214764442574,-4510034983957122647,3586531709416583121,3609706977406214742>()
                  : var4.getMessage() + ""
            )
            + var3
            + this.bN.size();
         n.warn(
            (String)com.yiyiaddon.m.b.a<"slnn1c4hts5nb","9MqCN9XajZoXEAWdjOWASBGddeWg1/XAJX2NZMdPOXyeejn8PVm9RwsfbkcZeP2tjFuX80P3+O+88HIXR7+bq1VxKFVy6KOVc7M=",-6954760085160703563,-6613199313057752702,-6137567012431610838,8019003431368133222>(),
            this.bN.size(),
            var4
         );
      }
   }

   private void gF() {
      List var1 = q.aW();
      ArrayList var2 = new ArrayList();
      LinkedHashMap var3 = new LinkedHashMap();
      Iterator var4 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s34ir71cj3re9","0URWZmOIw61Aem07h15uvgjeklaXE3d3SWu0FQCFW/s=",4718189041688766306,8578588382158422591,-4631479142328637323,2862511768468553039>()) {
         case 1965563821:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2020ro2j8vrmm","74849X51YuqX4FWsluXHKBFKrc4ihCnLw0+iSer9hhg=",-2992137684529308008,6504517999876210630,3682444081133529696,-513064183713213525>()) {
                  case -1854315484:
                     q.b var5 = (q.b)var4.next();
                     if (!(String)com.yiyiaddon.m.b.a<"s733gmi6v5r6r","fGrjNlgK7lMh8j8t6e5MOa0F97bDFug3bmGM4vCVHdevF0r2G8jRHjqyi0qn+QFSirs=",-1974351262508103615,7820440842128237385,7267031620704227494,1346750096444425245>()
                        .equals(aL(var5.dP()))) {
                        switch ((int)com.yiyiaddon.m.b.a<"sz4cw96nxt473","aDAFxE3yyKTPZMLFSuRZzAFpPcrmTjzrijGARt7wX2A=",-1552703365311577285,4538673989383416500,-3686286075717068226,-9050383401145424667>()) {
                           case 1462211436:
                              switch ((int)com.yiyiaddon.m.b.a<"s1cqf9hlpu8ui","JBE2NMKTOEn4/FkCUgsOs80kyQ4+3orP2jG73J0n894=",6898675907461771995,-969433145092660901,-6885041381450634317,-7608542905579095147>()) {
                                 case 31593561:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var2.add(var5);
                        var3.putIfAbsent(var5.dQ(), var5);
                        a(var5, this.ab);
                        switch ((int)com.yiyiaddon.m.b.a<"s2d7z0cbr7ugc8","BD45BwE9IDFvJiZwtGjGEoWYIES+G8cvG+NJD5mdsyk=",-359539359745129781,537089390868576295,-254516669224043019,-7722829067067553824>()) {
                           case -1574471881:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            this.ab.replaceAll((var0, var1x) -> k((List<String>)var1x));
            LinkedHashMap var10 = new LinkedHashMap();
            LinkedHashMap var11 = new LinkedHashMap();
            Iterator var6 = this.b.B().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"swoamcyfqq16s","lmDWUBAw3uLGQALNOYiJgzxJN8+dGhxwENhL1uDQNUs=",-5420483936887511459,7254317396787004486,-5687618985437646404,-8318244004708493905>()) {
               case -1819949639:
                  while (var6.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1kelxrrv07zlh","4llrNmyJQ2xq5Gzuu7Yd5wmt40lwLD+t4OE5OGaNuU0=",3801125384866638586,-5234696980563469564,-3867300212443007082,8988193278324171183>()) {
                        case 18027044:
                           com.yiyiaddon.g.c.d var7;
                           String var10000;
                           label123: {
                              var7 = (com.yiyiaddon.g.c.d)var6.next();
                              if (var7.dE() != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s7i398m866qja","i4DJYW84ln16r6CL1QvUFDqR6P1Z6iMu51mjAgUjO8M=",5858978991957520641,-8340337818241099915,-6120003228113761315,-2517129888492763534>()) {
                                    case 1543856713:
                                       if (!var7.dE().isBlank()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"ss4mohs4zyilu","3dzt18zPx7pgWyPBqy09z6ZmcGzKqocAzZQo9/H3L7A=",405116601089701757,3561966030772364856,-5969981326575023920,6177699360052697342>()) {
                                             case -1077035475:
                                                var10000 = var7.dE();
                                                switch ((int)com.yiyiaddon.m.b.a<"s136hyusq9ls5p","h9F8UmD0R4sNd4ZnMRkXcZNVb9j03ptHmTBMZApMZZU=",-1413504347249059041,-4409579447750161676,-3234527134302630006,-1025571712325372133>()) {
                                                   case 945924984:
                                                      break label123;
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

                              var10000 = var7.fZ();
                              switch ((int)com.yiyiaddon.m.b.a<"swnq1zc4i3waq","hT1RvM+L6nSmf6//a4MVL2Vma/Xgs8u8ukYNx7Oc0W0=",8372076316303608821,1130796679699548730,1516519359168368406,-2282163555623660882>()) {
                                 case 1618606770:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var8 = var10000;
                           if (!(String)com.yiyiaddon.m.b.a<"s733gmi6v5r6r","fGrjNlgK7lMh8j8t6e5MOa0F97bDFug3bmGM4vCVHdevF0r2G8jRHjqyi0qn+QFSirs=",-1974351262508103615,7820440842128237385,7267031620704227494,1346750096444425245>()
                              .equals(aL(var8))) {
                              switch ((int)com.yiyiaddon.m.b.a<"s18uyanb7gpbh5","6IIU65O8BqoKEIURIkajC+ZDFVfzQ85h7AKtxPUlSWw=",8927604826523631409,4183447619434861709,-4786374902152029520,2585714817912574522>()) {
                                 case 1562447447:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2udkbo10nrt3v","t1Xwh8bdHWmMer9PNOELgnDGM2v0SO9KWl0WfBXFfV8=",1797616202939021308,2254863415675986150,8281519635768409021,4166635183037604113>()) {
                                       case 230082149:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              String var9 = aM(var8);
                              if (var9 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sgetyu1me2by1","SORzUtPrik5NvPh+txuCx+dmWlX7+K281msWSdZDplA=",6598573084157997844,7361605870811561841,5134268949819842535,-8363779138415233292>()) {
                                    case 289595506:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2zyk1mr0wl367","oY88gnGdTyjNeaDYoVlXK51hSPNOa8S3wvTsjTA3BA4=",4982347081284450537,3519572940755062813,46644426405249493,2397643009055601049>()) {
                                          case 1750918899:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var10.putIfAbsent(var9, var7.dF());
                                 var11.putIfAbsent(var8, var7);
                                 switch ((int)com.yiyiaddon.m.b.a<"s35a2pml8oux13","wQol1pdv/M+VGtl1bZEq0J4Oy3BoW+sd5hMZt3G+XLM=",-5034543097277253274,-5752073475082153600,-4973052757759984690,-2918136308480639635>()) {
                                    case 802377605:
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

                  this.aa.putAll(a(var2, var11));
                  var6 = this.aa.values().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s10jbj6s1k3oiy","3ymZ6sCvS2Y5M9YROVdh13ufm5t32TtcIudfdcPSoOs=",-502958487642937124,-3888529871264108663,-4281943220527046502,8994431615825651633>()) {
                     case 1332475202:
                        while (var6.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2pbs4f0z35rez","i/2Yxe60gK2Cxozc3l3xslbzeHvX38NWaWKxek1ZFYs=",-6275023865722264661,3241417178734684818,-28667390213011401,5267944518873414088>()) {
                              case 1327709098:
                                 s var13 = (s)var6.next();
                                 if (var13 instanceof b) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s26kn2vgmtzbxa","FI6N5dKoG43jUg/nv6igNY+xA8MYa4xpf+Mcvgy8d4w=",-6372474731693777063,8120274370327111795,-7605769632059986863,5221324527588579330>()) {
                                       case 1574689318:
                                          b var14 = (b)var13;
                                          if (var14.bX() == -1) {
                                             label67:
                                             switch ((int)com.yiyiaddon.m.b.a<"s22yb38ep4sxvq","FabFDgkL3fiIzcAjC+ZRRrQ+03nUvpBwbwpOL9tF1Z8=",1783541777535280157,-8283030933102529384,8535250372390928047,-2325491385455752545>()) {
                                                case -1319094150:
                                                   this.ac
                                                      .put(
                                                         (String)com.yiyiaddon.m.b.a<"s33kj1qcg977tn","iiHZ6gdWQP+03TVeSy0Im4hlpl0jxOYOEhydq7hr0MIlcR4sRZ2m7QHAbvrjshQAXOsovHpiw7Kijiip5uvZZDG4kfMJmA==",6941073129279642771,2729683445177208341,7212327149253012878,7374867994219034685>(),
                                                         var13
                                                      );
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2s8fpuqivozbd","qCa7G6ZAu1kX7jiuLp26T6EDzee9TVmRUenJnT/o7vE=",3483591162861204464,4400367718748154619,1589220077182435896,5366951076277583103>()) {
                                                      case -1041245453:
                                                         break label67;
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s1a6ghg9zusho3","kwX4gLnEmJFmgkBbtFUG3SITvieltZ5RTZj054PSFrg=",8068065051430067449,8340786072155884557,-4904370229334957777,-1075043261772265544>()) {
                                    case -818034589:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.b(var3, var10);
                        this.gG();
                        return;
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

   private void gG() {
      Language var1 = Language.getInstance();
      LinkedHashMap var2 = new LinkedHashMap();
      if (var1 != null) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"s2w53civ5sst65","SklNna6/Wl0GR52JTx7MbWWHUHJav5Ro/kTR7/WaqL4=",385613250052499553,-3745469859352292992,-6704400606730749061,-1427569751892265011>()) {
            case 277106916:
               Iterator var3 = this.bN.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s3se6apcquzslh","KGz9ZomQddCt3tW1QngxYmUG6arjT7zsuzeVZD1kLv4=",247187003585997321,4772112666569722129,5582546494225126800,4421988538388736442>()) {
                  case 2131618528:
                     while (var3.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2x4laa3anw5xm","MehkP92p5G618RvfSS9viYr32vib8Srpi+CbsPDa1/I=",-6235670239818876408,-3023891593623311687,1693172176171167461,2771769997408652925>()) {
                           case -1549459342:
                              com.yiyiaddon.e.n.i.a var4 = (com.yiyiaddon.e.n.i.a)var3.next();
                              com.yiyiaddon.e.n.j.e var5 = a(var1, var4.dk());
                              if (var5 != com.yiyiaddon.e.n.j.e.NORMAL) {
                                 label25:
                                 switch ((int)com.yiyiaddon.m.b.a<"s4q6zc9pjith7","0J/uq653bv2zzEDpTTr5KAgkpVlScBgYzFQe7860V3s=",-5164881128936437405,3096563445433873343,123756134191288914,-3854326127365756073>()) {
                                    case 897760056:
                                       var2.put(var4.dk(), var5);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3e4yuicuclhqy","HpIqBmXde84FLBsc0KOhg9VlMmUOctQnHH86NMc1g4w=",-849826808774200136,-3644732047486541789,5204193706280482799,-376310939209644567>()) {
                                          case -1016991867:
                                             break label25;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3eid2bowc1cud","o7W4RU76H6HhMQW/0BJFi/PHs5AFMa0/6tOWRiJ/MA0=",7302104530175167392,305265630366572979,8108907333438366176,620791111082456984>()) {
                                 case 1407951971:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      com.yiyiaddon.e.n.j.a.a(var2);
   }

   private static com.yiyiaddon.e.n.j.e a(Language var0, String var1) {
      String var2 = var1 + "";
      if (!var0.has(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1agehmdohx10d","PLnSDnLeN/3Oq8eDREjP+h74ix34WOZ6d2lcYRmC+FU=",-5935678271454389596,8506948608655268386,-2806142996695784195,-2614466304131006434>()) {
            case 995973619:
               return com.yiyiaddon.e.n.j.e.NORMAL;
            default:
               throw null;
         }
      } else {
         String var3 = var0.getOrDefault(var2);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1ov60k2nsrtrq","Ua4qtWJ1CrYQsOeJwquJAhYXCAhpoOEC9YMMg5u6tz8=",5885404084869188736,-3930405803973463070,-870191926378887942,4915667790138291060>()) {
               case 1012031532:
                  return com.yiyiaddon.e.n.j.e.NORMAL;
               default:
                  throw null;
            }
         } else {
            return a(var3);
         }
      }
   }

   private static com.yiyiaddon.e.n.j.e a(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      if (!var0.contains(
         (String)com.yiyiaddon.m.b.a<"sw6qrq2wietce","KIIc7MrbEYoExrhddIZ13RjWVxmh+n1dfYPAeLRRUp4=",4626917957358441172,-8608013793863052959,-7790951676281751355,-6257167461919647971>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s30w1go7l2zua2","rN6QjvX7xFYlgS40r7W9ZYf7En59pekRdSid7IEhMEE=",5162417039247158596,-27196703335833318,394793654171420772,2874832835830433258>()) {
            case 746308797:
               if (!var1.contains(
                  (String)com.yiyiaddon.m.b.a<"s2ihiot79csyo4","+1cHLMffPMa7jlV4c4pPFyjZHOekolG/Q9TXprJFeTRGQo0jg/ae7A==",3268152940074375465,2580497756519453202,-8069550357062279141,-4312139444621559846>()
               )) {
                  if (!var0.contains(
                     (String)com.yiyiaddon.m.b.a<"sze57kg1wgcjm","0FvUBLM2ZQU1eXmcHzXAKQ2lLhD0WzxYe2fZbKC88AA=",-9168193600365149050,-8283812246909452716,7178558486921506118,790758313344418260>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3i5jtkvp5mfmv","7e38tYWlV2o8cnG66vjq+rlYAUYQjd95KhkmNlCYchA=",-6959835091677670104,4937183597350731821,8405260896435494322,-9209602502127747480>()) {
                        case -955005949:
                           if (!var1.contains(
                              (String)com.yiyiaddon.m.b.a<"s3a8h4ikrjd7at","VrHUuPCrhsxzSLdTFpcqN1nuKzq0jBBNxdMZMI4nImXLkLnUgjT08dPV",-33074206862474435,4641296661004944291,-4630237255761359223,943606178128297697>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2ltq6c07eq7el","QULn6O3aO2mK98cMcNnDtu5eepoYsx72CoqhXYBGEd8=",-3115103460096597985,8300582756590295703,-6933314927659943316,-5889148371933245346>()) {
                                 case 2029187859:
                                    if (!var1.contains(
                                       (String)com.yiyiaddon.m.b.a<"s2nctr0wqot2l2","XnjxV8pKELZmX5iPDfVROoI/TuIXhHeDS/No/4leW4RUo5y/2cAIeIPvg7tJx9DIEve+45SQ",6936012401792770365,-6009062150872614517,8598869148034077068,786300424705988856>()
                                    )) {
                                       return com.yiyiaddon.e.n.j.e.NORMAL;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1r44f8wsx8j2z","IPezi4bQPDpPSAHMErqD/2o3X6Fpacqia1QN7zECALM=",4742374788842946816,-6672490110720231485,7357662224360341985,5948560622158477608>()) {
                                       case -2053917244:
                                          return com.yiyiaddon.e.n.j.e.END;
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

                  return com.yiyiaddon.e.n.j.e.END;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ue9a1pxy4ve5","OI36/3yi1jFpm+z7t6Kc1EKQGXhOatXZhrxEvUigUvg=",4345468232566365863,-5888650779343216903,4951221575944445083,-5486045498945308133>()) {
                     case 1702179145:
                        return com.yiyiaddon.e.n.j.e.NETHER;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.e.NETHER;
      }
   }

   private static Map<String, s> a(List<q.b> var0, Map<String, com.yiyiaddon.g.c.d> var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      Iterator var3 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s4smzxy6st9sr","6Yaw9RTtZV3uJFBblu23vzdj5irJl4c8oFtVsivV9W0=",4039447545090728305,-3269364504917021625,-2934116047566514560,-1161093054758827743>()) {
         case 30931411:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"siusgm8c9tsfi","hfZVBuBL2BmEhU2MmRtjC0PwoPg6pF8aOyH6x2xZgYI=",7381838769984178919,7093756058825385176,3816023680079743488,4434584943021448294>()) {
                  case -257518808:
                     q.b var4 = (q.b)var3.next();
                     com.yiyiaddon.e.n.o.d var5 = com.yiyiaddon.e.n.o.d.a(aL(var4.dP()), var4.dQ());
                     if (var5 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s13s77giv6wan5","fb2/tvoxzOgFhxUDQQtr07HxdX4ATU1P7h4vUy1hUU4=",-3105254195132831219,-4112253530131080296,7153870320918031636,-6576669651725696469>()) {
                           case -843913799:
                              if (var5 == com.yiyiaddon.e.n.o.d.CROP) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1e8reh272e24r","2yk+IoXBFHIozFJ4qIfBZWJzf1bQUT64WE+vPXjxuQo=",-8527194183448939174,7842568024132500745,1341396527047694221,-8444385596343848235>()) {
                                    case 947742398:
                                       switch ((int)com.yiyiaddon.m.b.a<"sozzu6g9u7cxl","HJ1UJYrk6Q1rDCLHTEBemGpkGsMV33RIZjqKG9XTeKk=",-4334258050211351665,-7783753371471349746,-5065029416376897905,5116776916773373644>()) {
                                          case 705769365:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 p.a var6 = a(var2, var5, var4.dQ());
                                 var6.sW = h(var6.sW, var4.m());
                                 a(var6, var5, var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2xguk94h6gexb","oRbOW/AUtnGM4n7iNIR5tz1OFQlgzwcm6Q5R8xLsQL0=",-5708307715105783257,5355969338092960442,5104189589834661853,6773037118259253647>()) {
                                    case 134275033:
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

            var3 = var1.entrySet().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"sihvr73epi52g","OVgZZrf8VCf/einlbk3veO9rWFABSAcukX/8Emx8QHU=",-201368796510183037,3188904238791226086,7009188902041346211,4872774944826591422>()) {
               case 343055456:
                  while (var3.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3vd5yi3riroy6","7yOmjAQcIJymlspcJDfH+orgXaP7YshZPgtRCa42Qzo=",-9184125537939143677,4941108660987248317,2664788937043147146,4918512525938454157>()) {
                        case -1788800688:
                           com.yiyiaddon.g.c.d var14;
                           String var10000;
                           label117: {
                              Entry var12 = (Entry)var3.next();
                              var14 = (com.yiyiaddon.g.c.d)var12.getValue();
                              if (var14.dE() != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sonz1c7u6risu","W4GE33kCt5ZK5tIeMIkxPiY5ww2FkJIALWRKjtAMvTA=",5928210418736682638,196210563384448696,-4208857343649010755,-8091629335079623964>()) {
                                    case -1829490634:
                                       if (!var14.dE().isBlank()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2rhgi480oakkm","XVx8nzyiBdeq8+qnTZC1MY4Q7xjKqk07zCrUIFJX2zE=",-1591075173635270186,-7238729315087691072,-7269477286722123801,-1629071781442179808>()) {
                                             case 1845186249:
                                                var10000 = var14.dE();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1z0a7c7vj7jak","j1otgClX/LlBjYxfKpYzDiCQdluEsGF851frlm6TFi8=",6815476023399603063,7819504809299430087,-7016779103400030598,700327410618723163>()) {
                                                   case 914194077:
                                                      break label117;
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

                              var10000 = var14.fZ();
                              switch ((int)com.yiyiaddon.m.b.a<"sy1rqf14800sd","/M0wtO+MzXDMOux+4kDQeDQwuLNnp4eqAGCvbs9wdx0=",1268675072036655265,-690179847435295941,-8141194003953304903,8750797286974811281>()) {
                                 case -1323406470:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var16 = var10000;
                           String var7 = aM(var16);
                           com.yiyiaddon.e.n.o.d var8 = com.yiyiaddon.e.n.o.d.a(aL(var16), var7);
                           if (var8 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"swbghwpxhlesp","aRHHBFnF99E+O0FLfdPZHhmIe1M1CTWgj2d6delPoTU=",-8793378870871483432,-8564542326761705304,6049529991518664862,6717993320488233977>()) {
                                 case 1256824109:
                                    if (var8 == com.yiyiaddon.e.n.o.d.CROP) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3ihh9i0c3u1e9","kKalz6LXgCpca+QWOmwXuvbeBmPR2lDDa9eN1NGyyhs=",4459789194712445099,4093648210667652743,-1494317830105066794,5860430361362692056>()) {
                                          case -1350600176:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1j7b5l6i6ltww","FqBbkg58WtaCZhzx29O9CeiiLDhfpaaQxXTQ2lH+pKI=",6476134868200332702,478209071180446428,-6048097253934432365,-2502943280088807708>()) {
                                                case -268143078:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       p.a var9 = a(var2, var8, var7);
                                       a(var9, var8, var14);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2njnqw8awmylo","4Gq/SUBCo7maYCYhp+rrLYy3XZP+g/xDyoF31Zynlrw=",-2670542844836514040,7734382494418569968,1228480194810461455,-4525259102940990696>()) {
                                          case -1456669473:
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

                  LinkedHashMap var11 = new LinkedHashMap();
                  Iterator var13 = var2.values().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3up5a9e0pdquv","jhJr6icM8lO6lYcRXmugFh+iRibrIH9/A1jECEtyeR8=",-4505002986235357898,2163544095146848836,-2917680942061954713,-7505377531259907403>()) {
                     case 1849655377:
                        while (var13.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s36xgn3ih568ju","HK+k2+AvL+q5cpsrO62f/SLwRZUMnvL4fDqR6FCMuSs=",-6522815964694893218,-6774697313344853789,1372620310357563567,-2933092255660170303>()) {
                              case -1616952915:
                                 p.a var15 = (p.a)var13.next();
                                 s var17 = a(var15);
                                 if (var17 != null) {
                                    label63:
                                    switch ((int)com.yiyiaddon.m.b.a<"s31bkjz67rz8jg","eiQ9Ozq8bGhN6ZUJ74bQPTQ2iWyyBtLxFTC+kuaNVT0=",-1495416681447931949,-2104422970969150107,2892343761002434785,-4142516473315414932>()) {
                                       case -1188156969:
                                          var11.put(var17.L(), var17);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3axsuzp1pgk0h","reMACl/hs+osn1hwuc8l37cEDdpvulJdW6bJMNkKyT4=",-1233653578442443635,-3721539371549781386,-2550275677977377112,-3827555776156010018>()) {
                                             case -408212021:
                                                break label63;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2g3cet1bz8lj3","3Tb+p8y/NYw/5twMKbdtTuwZmYKrqC71XivPB14/o54=",8093816214348378994,-3349399494875889602,-8397742178802947928,183233011856015434>()) {
                                    case 1087853795:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var11;
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

   private static p.a a(Map<String, p.a> var0, com.yiyiaddon.e.n.o.d var1, String var2) {
      String var3 = a(var1, var2);
      String var4 = var1.name() + var3;
      return var0.computeIfAbsent(var4, var2x -> new p.a(var1, var3, h(aD(var3))));
   }

   private static void a(p.a var0, com.yiyiaddon.e.n.o.d var1, q.b var2) {
      if (var2.dl()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zbx8vfttf92z","nSGfhMZbu6L0SfrbXoQxLtjI3niFCj7Ctsn+U8DweJg=",1669822522669362137,-4406455262732423701,7101846431160848571,4164935750201045790>()) {
            case 1537028406:
               if (var0.sh == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1bun9n06du4wj","sioI1ZKlWTiCFQn76wmYS/hllE05kn3/pY8rmJgMi7o=",7976364801464643,-9007862325570741410,-3191206069949106028,-6930441799311901857>()) {
                     case 1685340028:
                        var0.sh = var2.dP();
                        switch ((int)com.yiyiaddon.m.b.a<"sxhg61hvku9w0","USEKuooGVQxAUWiQBX9hEWQE8GaWaO6/Yz44mfSando=",-8822388720251172289,-8097854543242872342,-3693087090912377984,8932063080262359919>()) {
                           case 160064462:
                              return;
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
      } else {
         String var3 = aD(var2.dQ());
         if (var2.dk()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ae2pcmg2d80x","jO6vi2LRPmOrM5ZBERhY6Keg0RTbjdmlU5qLnTrSk3Q=",-5416393597630082025,5423371614579532169,-890633551836595305,5822643590449444840>()) {
               case -983630131:
                  switch (var1) {
                     case POT:
                        if (var3.startsWith(
                           (String)com.yiyiaddon.m.b.a<"s2pub7zl3naqx5","uLw2/ixAo+e0caqBRTaG1FjZdyCt+m9MafHkl7F+AFggr7KQrlnXCJTj",274124763585731336,7681875586787874451,7045008438102173033,83785512926808000>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ukxnwha6435h","xJzBgzd81CTbDZoaTmjyrFtKy3GkEVcQiUNUmdeSEos=",3564698174227460748,4104832360771696636,-5704173989280483097,1134466424465818093>()) {
                              case -1753026843:
                                 var0.sk = var2.dP();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1pnhgc5f94vby","OKUdhmEs5PpdN0NTsvfNVV9iBLckdG2tEmfzpnAXAHM=",7899653710204781105,242182024207304294,7141616380916066070,8926349065212626618>()) {
                                    case 276943728:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var3.startsWith(
                              (String)com.yiyiaddon.m.b.a<"s3agp286vr1uwr","TL7+I+hBAxW1i6mb78gHllxr+gLJ4a8ZrOHHliFdwBJORCGi5WIWLjwn",-2656744326439724923,-3267225584887950349,2184070158843606193,864781739765635217>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3oeqv55fgojfl","q0kiIKoxqAoMf3GQ8Ykvo7qvepw0CO5mUeJsRR0a02M=",3823333692709821731,-2472328699763153009,-1085505872716374635,3158706097762112586>()) {
                                 case -233680671:
                                    var0.sl = var2.dP();
                                    switch ((int)com.yiyiaddon.m.b.a<"s314ogjcc2xcex","+i7Q4GcPTL/iQuI4TWuAQVD1ncWwC1yABiC9oc8M7iY=",-7365999488776213249,-460618778852877720,6481617496136269672,-4658802610312458867>()) {
                                       case -1536454301:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        }
                     case SPRINKLER:
                     case SHELTER:
                        var0.ss = var2.dP();
                        switch ((int)com.yiyiaddon.m.b.a<"s3mt3xxp89r9ji","lnjOk8Obk+JRU9xZUgqtaJ357k5+UqKgbDj4VIdCm+U=",7629926356481609362,-2223316681563963442,405584283821531668,346692487818548180>()) {
                           case 1544533702:
                              break;
                           default:
                              throw null;
                        }
                  }

                  return;
               default:
                  throw null;
            }
         } else {
            switch (var1) {
               case POT:
                  if (var3.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s2pub7zl3naqx5","uLw2/ixAo+e0caqBRTaG1FjZdyCt+m9MafHkl7F+AFggr7KQrlnXCJTj",274124763585731336,7681875586787874451,7045008438102173033,83785512926808000>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1cw9grg56fw95","PIu850FLmCUUWwyMp7mMxhHv2eqcEWVp/6l5HPZJbVQ=",-4534999767503967187,5381424117477396335,-8815721152120005062,-6654353278248674473>()) {
                        case 1986977493:
                           if (var0.sk == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2ugvlodqj7e5g","26ycsZv5+IX5SsipYv6T+VCo5EGNxmpgnw2ENlnqau4=",2657298976059154802,2230884713709129940,-6308595850096126520,8502085628177240033>()) {
                                 case 1451260289:
                                    var0.sk = var2.dP();
                                    switch ((int)com.yiyiaddon.m.b.a<"s21od1dhj64n5e","0mY7kfPh+xJhMlySuYNQGj/Ki5c3FcU1GfzQiKvXkuc=",-3947424183229334648,-2710905241411078211,3865031358200040256,-4375035118527708576>()) {
                                       case 649609661:
                                          return;
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
                  } else {
                     if (var3.startsWith(
                        (String)com.yiyiaddon.m.b.a<"s3agp286vr1uwr","TL7+I+hBAxW1i6mb78gHllxr+gLJ4a8ZrOHHliFdwBJORCGi5WIWLjwn",-2656744326439724923,-3267225584887950349,2184070158843606193,864781739765635217>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"sgx2bqcrjpn1z","mExUxXUKtWuLtIAO/DLfrnYP46ZjxHrmJbLdzB8bKYg=",7169800036286560720,-4699442769787252901,-4097939309640948978,7402824623059662880>()) {
                           case 1762885716:
                              if (var0.sl == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s167eij1vqh4nb","IdKVETHrkB9g+kXjxkd2IBbyFcacgOtVOa8theIXkQc=",9009606126544666159,-7282864867349261251,395422328641138692,796313194497274565>()) {
                                    case -944113421:
                                       var0.sl = var2.dP();
                                       switch ((int)com.yiyiaddon.m.b.a<"s2jybxfhgcfv2k","jmJzEICPDMWS5GMsvCk0b390eU0jiPvItPK68rGfyew=",9075953802942961228,8694638510191732492,-4822728560167148466,-5065268176784509828>()) {
                                          case -980469933:
                                             return;
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
                     break;
                  }
               case SPRINKLER:
               case SHELTER:
                  if (var0.ss == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s24si4zbxr9mib","ULyTAW4bhhOGfZqOb5UfuK/VYU/TQ+WfBhhWaB7PuLY=",4259846189015863479,8603951180813164852,6047644574949379123,6575480737487533809>()) {
                        case 507921206:
                           var0.ss = var2.dP();
                           switch ((int)com.yiyiaddon.m.b.a<"s2uz90npsyg4hx","aicHY2CBzekjo7yYhsaLQxGx7Ni591VieXH0HFQQ1kw=",8357994395021587899,-6679852369599456178,-8010194716924644551,-7876061232607614767>()) {
                              case -209544269:
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
      }
   }

   private static void a(p.a var0, com.yiyiaddon.e.n.o.d var1, com.yiyiaddon.g.c.d var2) {
      if (var0.si == null) {
         label93:
         switch ((int)com.yiyiaddon.m.b.a<"s85s17id1c3eh","0ytezKgfJ8fP6ZPiDEvasu3okcQeUc5qhksZ7qA9vdA=",-7937593074111284351,-2335810214777598348,-6137145580029055511,-1747139841874376666>()) {
            case 319121644:
               var0.si = var2.dF();
               switch ((int)com.yiyiaddon.m.b.a<"sg7i9bws135mz","eBmcg6dk96Dd2yPQjcoUSuH7YE1v3j/Y8rf6K3nAAu0=",3270963334402587856,-1362048810230695713,3233528952734405729,8950042462568153635>()) {
                  case -1583037629:
                     break label93;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var0.sV = h(var0.sV, var2.m());
      String var3 = var2.dE();
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bcjmx75gedvk","UAT34Ra+oKAfuorORS4zal9HSauvmojCME12mALv/Z4=",-5596255185198252845,1271670909024913913,-8212365811243295968,-7054661552195896005>()) {
            case -536906636:
               if (!var3.isBlank()) {
                  label87:
                  switch ((int)com.yiyiaddon.m.b.a<"s2udmkiqnlvpdm","Tnl0CKCqA9xTUZbMTI2M0fcI6UfLJ4V44uuQhXIrpb4=",9179866526014940403,1001584713827027934,560743154393727855,8332793894875676243>()) {
                     case 1231921566:
                        if (var3.contains(
                           (String)com.yiyiaddon.m.b.a<"sqd8mdqkdnml3","SyptLoIqas1rT4kSMA5b4xpQKYMP3b86zf8CYkdpHVWKvuhEveRd1gBY",-7448021635595441762,4963024112688001534,108805462458286955,1581219243669749338>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3kn30ad0p8682","lLsC/rBjJCVSURZYGIJwcTJzZyaWyMf9wZ+5dYd1UjM=",-1642212321822483305,5670316082530514616,-3907776334424637402,7487919485870035635>()) {
                              case -144199433:
                                 if (var1 == com.yiyiaddon.e.n.o.d.POT) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sby2vhxqy0uqk","wBwIHw7hYsjdlB2IU25XxivPX6c6kq8HDVWBfRnxs68=",-566296806926699769,-1336807727124669207,5029461600653180142,4788530442141272541>()) {
                                       case -1332361394:
                                          String var4 = aD(var3);
                                          if (var4.startsWith(
                                             (String)com.yiyiaddon.m.b.a<"s2pub7zl3naqx5","uLw2/ixAo+e0caqBRTaG1FjZdyCt+m9MafHkl7F+AFggr7KQrlnXCJTj",274124763585731336,7681875586787874451,7045008438102173033,83785512926808000>()
                                          )) {
                                             label68:
                                             switch ((int)com.yiyiaddon.m.b.a<"s59wl0nsaa2c","jVDRdfl9l99sKVsgh4YaBIhOALgZjeMb4hGUuVC6CqM=",-4322075611061710273,7293169174398882283,479472206407513789,-4448104055573592761>()) {
                                                case -54580974:
                                                   var0.sk = var3;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s92ml906jcd4i","xBkOxsLing5gaNiGLiiLxkFyl+K/5OTeR6eqN0qxBD8=",334424173531240650,-8528221979049586715,1408269842486108683,8203487614498149545>()) {
                                                      case 1186280254:
                                                         break label68;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else if (var4.startsWith(
                                             (String)com.yiyiaddon.m.b.a<"s3agp286vr1uwr","TL7+I+hBAxW1i6mb78gHllxr+gLJ4a8ZrOHHliFdwBJORCGi5WIWLjwn",-2656744326439724923,-3267225584887950349,2184070158843606193,864781739765635217>()
                                          )) {
                                             label71:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2ls7p8vd4f2rs","EDsq1+jQv0qHMclGGhUD3B7/fkKwbU2AdKFTpxx1ymQ=",5911394082568044113,-608810331227151611,-8341177529731838512,-7109011780702584556>()) {
                                                case 1178773998:
                                                   var0.sl = var3;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s15qxug5ugqno4","TNGJEuwfxY6sDljZ2XgmB3XsqGo1zbebrMds3Z3Ip70=",4077694177529514862,-3452304484341513575,-5277876640093770801,-4192589109644604583>()) {
                                                      case 519575175:
                                                         break label71;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"stmc5v3usdbcu","m+h/QrLAeYD/g0PAHNXAnunRl7dS5U7kPqGxnH//Xuc=",-2165760611750660435,-3054079213669577402,3195760264697843973,-7400150364820268620>()) {
                                             case 672365705:
                                                break label87;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    if (var1 != com.yiyiaddon.e.n.o.d.SPRINKLER) {
                                       label83:
                                       switch ((int)com.yiyiaddon.m.b.a<"s6l3zgzuv1l8d","wyJzPxSoFrt3Io2JppCRspz3lHU4rFGCuM8PrFN/E3g=",4372914691179064308,824599700613436991,-928882310577412218,1197124982360701147>()) {
                                          case 1959416663:
                                             if (var1 != com.yiyiaddon.e.n.o.d.SHELTER) {
                                                break label87;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s138jjdfdelege","MUZMsUxWdn4cxyxTNRahDRUvrgk9tb+fsr/XIpFY4mk=",-3093809190889277729,-2293239693117418955,-7805078155107112159,2825557388053165746>()) {
                                                case 1092557625:
                                                   break label83;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var0.ss == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sogqsqvys2trq","69BR3ooShRB0KtVYz+glqjgIZHsq2eTqnOPYrFpy1zQ=",-1706641145561940578,-2187917543711163181,7496578670551416574,-8214086255920751426>()) {
                                          case 2006997517:
                                             var0.ss = var3;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2zjis516qxqik","TKmcmmV6QjHKbo8LovtBiFEWIosvc4SWlAUzioXCY3g=",-768292154091257969,1930967242813150645,1246507288408726195,-8234902491017583079>()) {
                                                case 257077960:
                                                   break label87;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }
                                    break label87;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var0.sh == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2uwyz5qkviz38","d59T65JIoaZNLADA9KC2haMRB8QUNMNyPxl1OA9DWBA=",-5700181942502145769,-7616946448927483667,-5109654905364863293,1775373903256717201>()) {
                                 case -1011142514:
                                    var0.sh = var3;
                                    switch ((int)com.yiyiaddon.m.b.a<"s138kxij79dct2","ww0qQ3wmMoCT20FuqIBpYPtOFeb9b7U88uJW+Y67kZU=",-8097901544867720865,-6586257838713014903,-6466791748261624897,270946828086537838>()) {
                                       case 2028391147:
                                          break label87;
                                       default:
                                          throw null;
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

      var0.c = c.VERIFIED;
      if (!var0.sj
         .contains(
            (String)com.yiyiaddon.m.b.a<"s1trm4qvd4dqsv","SXwqzagjgX9VDZJ8gZwIlNrYP9T2FTnelCfRiFZrxeqJBv4h6z4G5dJ+YfzQvBYYy2MF3UmB",-8030687806911057954,1510431169996317875,-2691092775624653047,-9098199854402544182>()
         )) {
         switch ((int)com.yiyiaddon.m.b.a<"s364kug7xhsxwz","tLirXeeRHJY3DLDRZ2SDn0IhTVHOEC2vKvhTZ1hoUgY=",-4527915994931496818,8008463008573161203,5066938557229113241,6712653817515659783>()) {
            case 899803061:
               var0.sj = var0.sj + "";
               switch ((int)com.yiyiaddon.m.b.a<"s28mbqs5c3tgrx","j/1Fj0vZ1v5fUa1PYvdLXzaWtncswmc2z4/hRW6lMyw=",4320225115623675192,-7418877390900302176,-1574424409485232374,-7133464239956777956>()) {
                  case 984286196:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static s a(p.a var0) {
      com.yiyiaddon.e.n.o.d var1 = var0.b;
      String var2 = var0.sh;
      if (var2 == null) {
         label40:
         switch ((int)com.yiyiaddon.m.b.a<"s1zjwgxv8ujn0v","HxGiTajF6aQYLfn1JVKf7X1bKZOx46pZlVwKgy+6y4w=",6923567226670907243,3902485277072467534,-5847067424712977370,1318231061348417410>()) {
            case 1375349263:
               var2 = a(var1, var0.mA);
               switch ((int)com.yiyiaddon.m.b.a<"s1x89awwybckxf","ccOJQ8glCZXbtw4HCKu8xY9JZJ31mCh8Fm3r9LOeE0w=",7767834241461869803,8737705940524537526,-1367143952607329599,-5337326148282421660>()) {
                  case 1249285519:
                     break label40;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var3 = a(var0);
      String var4 = a(var1, var0.sU, var2);
      switch (var1) {
         case POT:
            b var8 = new b(var4, var3, var2, var0.si, var0.c, var0.sj, var0.mA, var0.sk, var0.sl);
            switch ((int)com.yiyiaddon.m.b.a<"s1trwnkfzrfgmw","BlX2k4GKB0rQjM7z53OZMf8J1dggiWwcdINOFkZZH30=",5235001485598006973,-9115074967659653276,2017559909199425007,-5578699554707906464>()) {
               case -343786702:
                  return var8;
               default:
                  throw null;
            }
         case SPRINKLER:
            f var7 = new f(var4, var3, var2, var0.si, var0.c, var0.sj, var0.mA, var0.ss);
            switch ((int)com.yiyiaddon.m.b.a<"s303q1zozvs0tb","rRuIufWrcYNcf3YhRDroWCOM1OwFos4ayQ/b6NoIyxg=",-1119461433089528890,2746497072159562449,6695821866120799311,-166413082314308923>()) {
               case 1041747076:
                  return var7;
               default:
                  throw null;
            }
         case SHELTER:
            d var6 = new d(var4, var3, var2, var0.si, var0.c, var0.sj, var0.ss);
            switch ((int)com.yiyiaddon.m.b.a<"s2vo9cyhcvte35","bNyHUDKz8QmnN9Zf0NLliWmLzcHTDqcDaVh3OplqeCs=",-5515557470045786890,7791261794198624877,-4165899167265047591,2731023465759191160>()) {
               case 1488588798:
                  return var6;
               default:
                  throw null;
            }
         case WATERING_CAN:
            t var5 = new t(var4, var3, var2, var0.si, var0.c, var0.sj, var0.mA);
            switch ((int)com.yiyiaddon.m.b.a<"swg2nk4emqbj6","qqfFnv9aquzCxlmz/yS8puhaYK/FNxmKk5NzNBohU2o=",-5791823734421761617,653668572633272342,1470904110217609840,5400403791733893973>()) {
               case -1409443807:
                  return var5;
               default:
                  throw null;
            }
         case FERTILIZER:
         case POTION:
            e var10000 = new e(var4, var3, var2, var0.si, var0.c, var0.sj, var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2uehehwcn2gdi","DGCna6yCHAW/CcK9Qge+Cj218fs9AhK4/0EyjGRgWjs=",-4228500275568639323,-6838379580477636748,7864534759397978484,-1409114462015179902>()) {
               case -695867708:
                  return var10000;
               default:
                  throw null;
            }
         default:
            switch ((int)com.yiyiaddon.m.b.a<"s2rkyepx6wal8k","T8ee87FKRIdvWfj+Nj6qy0lUWIonUDZ7Cy5kPU4agh0=",7811252966930899654,-1278209913625223464,6358254653980209078,3769629062126477862>()) {
               case 1346263452:
                  return null;
               default:
                  throw null;
            }
      }
   }

   public static String a(com.yiyiaddon.e.n.o.d var0, String var1) {
      String var2 = aD(var1);
      int var3 = h(var2);
      switch (var0) {
         case POT:
            int var4;
            if (var3 == 1) {
               label28:
               switch ((int)com.yiyiaddon.m.b.a<"s15pvmjjteqpzz","/IJuQ4cWBrA0l/3jmSXYPV+neD6SfxeGJ/qIzGjbNsI=",6341548861591478383,-2773330840888118534,292960114121190621,-4804670508453626608>()) {
                  case -1925614694:
                     var4 = -1;
                     switch ((int)com.yiyiaddon.m.b.a<"s10nqjd9io68l9","FpfW+VxxFwR1dJY0A0RT55f0kT4+AHNMbSXlo2JBG4s=",287283147601632381,-3670388154997661438,8519858689438841395,-4483315402495091547>()) {
                        case 2043271303:
                           break label28;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var4 = var3;
               switch ((int)com.yiyiaddon.m.b.a<"s2smeq0w2t6vxk","nawZA8xWdnE+Fnw1ajtwv4QtiHdhrHMTicatawH8Gt8=",-2087907111138041602,136615918033808802,4708164489985117433,5792767406047089628>()) {
                  case -112837752:
                     break;
                  default:
                     throw null;
               }
            }

            String var6 = var4 + "";
            switch ((int)com.yiyiaddon.m.b.a<"s1lqbp10jahgsm","P7z3Ib9JTtW58mUoicRWW87mMBrXEDgeyYHzYWdI03o=",-1802982869852710462,7721396101833454083,-7287086743977393686,7587895207328797743>()) {
               case -1630922735:
                  return var6;
               default:
                  throw null;
            }
         case SPRINKLER:
            String var5 = var3 + "";
            switch ((int)com.yiyiaddon.m.b.a<"s3nbvxzwt9if9","f/UfzDuBopfTkoW56c1u8GkezDR3fr2wY3MxSDnTooQ=",4060872999561470189,-3751770930359242704,-8722777666549732445,-375074752250057615>()) {
               case -1759384239:
                  return var5;
               default:
                  throw null;
            }
         case SHELTER:
         default:
            switch ((int)com.yiyiaddon.m.b.a<"smqnfkyf6xoc","UtgJOH3ql9zqPdbkE/Mf1850C4JMuNId59h/FxAb0E8=",7079141219825190722,7478360464952398092,5802822413178244726,-6638056433577062090>()) {
               case -441498708:
                  return var2;
               default:
                  throw null;
            }
         case WATERING_CAN:
            String var10000 = var3 + "";
            switch ((int)com.yiyiaddon.m.b.a<"s3pt3eoypmgqa5","d1R4C0JX+cGrgfHlVq7jYZPnfibPmc2b3qZO02QJyH8=",-7180590141836404603,5179356396493897021,1025610528957649750,2851854655768947986>()) {
               case 1593989643:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   private static String a(com.yiyiaddon.e.n.o.d var0, int var1) {
      if (var1 < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3e0ybhen9f536","gz3NY3uIK1DSikGzGM//mrO+AKr9LhFJa9dr8+yYZKE=",3791397689724601758,5698626956386308164,-8665894985443308718,-1793151608697102037>()) {
            case -117403692:
               return null;
            default:
               throw null;
         }
      } else {
         switch (var0) {
            case POT:
               String var3 = var1 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s14u9niyn5m6cx","We5RF4fgbbvQZOYP8/Eoj3dbwf5VZo6Naqsf/sTYeN0=",11165146758513037,-4706943736094355119,-9153532108761451580,9131750576764057824>()) {
                  case 724485245:
                     return var3;
                  default:
                     throw null;
               }
            case SPRINKLER:
               String var2 = var1 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s2jyt89z4oze18","4GEITrUwW3+aujcmdqh7Ky5yRXepE4Fw8s/EjTZi678=",-5140672757462551377,-5871505364016795339,-7544611264095554684,-5672696536009778301>()) {
                  case 1216145910:
                     return var2;
                  default:
                     throw null;
               }
            case SHELTER:
            default:
               switch ((int)com.yiyiaddon.m.b.a<"skbwb19j1re09","2p/TY8SyE9B15pupUJMG3McLPchti3PV7M9QFx7qXOM=",-9048100069961726285,-8280379132538939944,-3451973295215441747,6354363970867940245>()) {
                  case 1926042:
                     return null;
                  default:
                     throw null;
               }
            case WATERING_CAN:
               String var10000 = var1 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s19w8r0ybeuiet","jAIFVCQC76uVkeCRAlMZcxi8voIuszGBnJ6dAwpmScA=",8744614419261269329,9097506766370067867,3440193847444508417,5427013855713351367>()) {
                  case -650561136:
                     return var10000;
                  default:
                     throw null;
               }
         }
      }
   }

   private static String a(com.yiyiaddon.e.n.o.d var0, String var1, String var2) {
      switch (var0) {
         case POT:
         case SPRINKLER:
         case WATERING_CAN:
            String var3 = var1 + "";
            switch ((int)com.yiyiaddon.m.b.a<"s3ldtm1rejcfz6","Xx/MiBXTaBeSQfh5ndNnp1xbTgzkVsSeHtvKgXu4nVw=",-2258252992180837944,-59961901574559833,-8690040431453610948,8978355362826955116>()) {
               case -1529013576:
                  return var3;
               default:
                  throw null;
            }
         case SHELTER:
         default:
            if (var2 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s16ia0psv5h0n7","QRcEAKbp2HR/vRD8keMRBgpzHdb0N+D9jjk3c1o+9Uk=",7060314975570296113,3232956374736645655,4675626555927245698,1429797778545745764>()) {
                  case 1391587273:
                     switch ((int)com.yiyiaddon.m.b.a<"s3hykcnw91k4m4","4ocEtvQzpGMS1pOOS6lyhKUxmNLl8/gpaUE2kaC/+wQ=",-8868218897685279753,-3517681892127156185,-2845809561516806049,-4363640878021685100>()) {
                        case 1466388250:
                           return var2;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               String var10000 = var1 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s37q6k7ksy98nk","secBcinVUhfVWmqkWiDnvxKCd1G75KuZF/RehtGw2Hg=",6116054850497311427,-1797792408884535462,4190684083560636363,-4121720542861040920>()) {
                  case -1120276658:
                     return var10000;
                  default:
                     throw null;
               }
            }
      }
   }

   private static String a(p.a var0) {
      if (h(var0.sV, null) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2g4awj6alsosy","R0/fZ1/cFTGuyJ/pGvmtxAreVw46+ojnRTp00h/J4DY=",4140782095960490866,7882662886608794021,903869690087210667,-1883074440439064632>()) {
            case 1024155572:
               return var0.sV;
            default:
               throw null;
         }
      } else {
         String var1 = h(var0.sW, null);
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sem47zy8om26e","DAnsrhSbI4L5MWHJ347TtkzVhB87gk8DRze3/xk4bV4=",-5384705718718991172,5058169808627210320,-2243018234786876099,-4382086313478186157>()) {
               case -886260369:
                  if (!var1.equals(aD(var0.sU))) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1tgqmpg2pd7l7","YVUoRl6xcUpzf9kcPCW1+R27BCeYVTjBwhUsWS7luWM=",5846476209390769630,1207183162922770241,1080586802623216372,-2685762239513524187>()) {
                        case 519307088:
                           return var1;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         String var2 = b(var0.b, var0.mA);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"svagcwcbynwyy","HDtAb4cdiI2Rh3IJ8RdgIXskTDSr2sej1f7uYQphGLk=",6442880186175259651,-8727103833290997915,4348831658925866325,-6655965991180184677>()) {
               case -2043563666:
                  switch ((int)com.yiyiaddon.m.b.a<"s2zsu5rsm8stc7","tqrdw26fIgnXLMwmU2Eq6I2LPNNZtrn0RWJQzxsEDsk=",3869778061462495196,3972218669677974350,4038572627182941856,-1447148261800876793>()) {
                     case 1621186718:
                        return var2;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var0.sU;
            switch ((int)com.yiyiaddon.m.b.a<"s33izkb2jvfbhf","Bu791LcpBGndZlZirGV+uwNBzRsw0r5ASHEQFBpJoVs=",-3730344172473603207,-8653094821937483777,-5900896640710260,8764883545033777384>()) {
               case 984926821:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   private static String b(com.yiyiaddon.e.n.o.d var0, int var1) {
      switch (var0) {
         case POT:
            switch (var1) {
               case 1:
                  String var12 = (String)com.yiyiaddon.m.b.a<"s2oja96b6gyxae","DAYjqWZbCBTvqV1aiHoZKrVxRH72Ytu/GshBJnCu2Emtr35g5cY=",3526736285193944685,-447205131650307917,6314472007886626488,-6088217645234252116>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3u1ssskwxznc1","khHT9M6H28pZmuLmMs7uHH5sp5ZnVJPoOxRBV20lAMs=",-5016946773354674042,2260520476037682620,-5370651729436056180,4738312275951801022>()) {
                     case 592665362:
                        return var12;
                     default:
                        throw null;
                  }
               case 2:
                  String var11 = (String)com.yiyiaddon.m.b.a<"s3ika7bcrfbl66","Rs2JK/c1lM7lycySfYmTpjESv8pUZtZCLwVyyb+b+4dpW4Z7+/0=",98105861999171155,-6654900700682885481,1967055741132108403,-4295766947015145495>();
                  switch ((int)com.yiyiaddon.m.b.a<"s17rorw0kj44x5","GV4g5zKdPZzLt3hwcHmjHJTRvZo2OCH1cwdOL0j/bP0=",-7426180553014198257,-3596104595013321520,-1266849710441056935,1558013217830029134>()) {
                     case 1884911696:
                        return var11;
                     default:
                        throw null;
                  }
               case 3:
                  String var10 = (String)com.yiyiaddon.m.b.a<"s1n7okb1hp0en1","Noxl7IMjb5o66sAnyIW/rnyHz75IyGL50sL06f6Oy+5QU83zH9s=",122524565405553988,-7452115525480694178,5777906861947397783,-7702759762565147005>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3bjlrmcwydl9f","mDm8s/A8kZmVbBDVu74jDkrJ51cbE5/uNMnyR6N0gRA=",8726152155853888284,-600080666783233045,-1157573237355589861,-7650991230783305058>()) {
                     case -883475711:
                        return var10;
                     default:
                        throw null;
                  }
               default:
                  switch ((int)com.yiyiaddon.m.b.a<"s3g0tjv261karn","Ck/ix0yHugHkhiYuXDvypEaH1L8dZsuI8Nt+Plz88tg=",918716103253260775,-2579008541254596807,-711181962367090031,-6887657989903235883>()) {
                     case -1210600104:
                        return null;
                     default:
                        throw null;
                  }
            }
         case SPRINKLER:
            switch (var1) {
               case 1:
                  String var9 = (String)com.yiyiaddon.m.b.a<"s1q74xuqyzcl09","+LKs+DnChRhfso+viSC/XK8kX+hgBxybtqxCDGBzvvd/SsPWjDw=",-2682260716621780931,-6132917811808072465,3423071521434409937,-4105802295373000458>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2pu1pdd64o287","tV6btYLdmDuKQgqpf2wo4gz6RoG9mMJdiWLHJV2wJ9I=",625156164959388758,-8198913282478485450,-6630031517177420035,-2162741725390376491>()) {
                     case 985312841:
                        return var9;
                     default:
                        throw null;
                  }
               case 2:
                  String var8 = (String)com.yiyiaddon.m.b.a<"sklt58ly3mbcx","Fcg81aOu/N2GzkRdu045m1gBdsvM769PLAq/OkBtRa05DxGu1d0=",-5669347677353964319,-260897335881443307,7031639035044559412,8889315052400398447>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2h4oxntcofmoi","j1T9x+CTAIF0lSlS8D6JShs3t1E1cIFmwc96SPKChw0=",6795430505568784216,5316511901117342759,-6470835086693234216,-6113479259111743786>()) {
                     case 33975512:
                        return var8;
                     default:
                        throw null;
                  }
               case 3:
                  String var7 = (String)com.yiyiaddon.m.b.a<"schwrh6ylsfgu","sd8ipbwTaTP/gLYQxOFQWGvJQ39/2FMDwQKJzAoDpTg0N5tVFU8=",3409608915233340098,-6166572183710320491,3761321070157507533,-6612477762276858815>();
                  switch ((int)com.yiyiaddon.m.b.a<"s4a9o7he3c67n","Ec6VFFJOcgJPBSMhfTmcQ8D+SomMNOUOSDq8mhomE+Q=",4300473159075364559,5245985808213004367,-179174023817375573,-6764744794710937425>()) {
                     case 1013541734:
                        return var7;
                     default:
                        throw null;
                  }
               case 4:
                  String var6 = (String)com.yiyiaddon.m.b.a<"s306htn1zdev1k","zUOeoLxx7LYdLLUflekQan5U3w3glKqZ8QrAWTkPvoCI1jGq084=",8593304129012226640,1429648934899469885,-7047499003898845895,-1053621509626296258>();
                  switch ((int)com.yiyiaddon.m.b.a<"s371q8ik6uja2z","862VAz4r7qYWaIdiuSZIhMG9xN4XFJMzqedDAaEY66I=",4889422492897147786,6143802266168881045,4517035219912679220,5772467707896686524>()) {
                     case -1721728803:
                        return var6;
                     default:
                        throw null;
                  }
               default:
                  switch ((int)com.yiyiaddon.m.b.a<"s3gl7yr9q6vcq5","/ApZro88z30BIRoZ5cVO6R5dGIEZoBHOKhmEdDGTDHc=",5553688439293514734,-2012598186228161040,-2995822303729277846,-5775463868023608376>()) {
                     case -291488278:
                        return null;
                     default:
                        throw null;
                  }
            }
         case SHELTER:
            String var5 = (String)com.yiyiaddon.m.b.a<"s2ejwqq1759sny","c0UkUiPcuM8HiJMTFB2xt1BjSpIdqyMk4nFfOa2uOmYQZ/eA",-185172700149666830,-2650700823829452885,-4435312893675720662,5661040087533668548>();
            switch ((int)com.yiyiaddon.m.b.a<"s1d1jrlur166qt","+X3b+cTwAmFyGJmkiSNGUq7f596PVFQcNZ+lEbyMqt4=",-6943034114759545844,3260489057622099420,5537679001925699725,2741235881603303195>()) {
               case 1175569499:
                  return var5;
               default:
                  throw null;
            }
         case WATERING_CAN:
            switch (var1) {
               case 1:
                  String var4 = (String)com.yiyiaddon.m.b.a<"s2qxk6cfoztrg0","/Iqgw6RZmNnA2utJKkqQ8V1xkKdBhDuJx4TuV2QlusM6o3THCgM=",-5942831064692487728,7927683567940372543,-1462991081734022714,-7916074549266185204>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1guwr1jegnzqf","uCAXzkI5tKzW1TkWJHdxa2T7unsT0i90iuPlajttKko=",-4551386368227417886,-7477542821362842473,-7335412115581194751,952540719630529459>()) {
                     case 1492290233:
                        return var4;
                     default:
                        throw null;
                  }
               case 2:
                  String var3 = (String)com.yiyiaddon.m.b.a<"s2uvlu1dfauo7j","qBoTt8vfRY+xETioueiKfErdsncRmcqXAV30G2wqmcHoyTXumLY=",-4601584464817176607,-3061345196493449820,1442803931805683824,9076058823926151657>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2ohdemue65ib7","S3pcWz4KI6hjKqUWlXPFwOEINuphqOPK8tRBNDwVFOE=",-4697748831638730250,2131631700457904028,-3952424957890867542,230266974004174315>()) {
                     case 1118606992:
                        return var3;
                     default:
                        throw null;
                  }
               case 3:
                  String var2 = (String)com.yiyiaddon.m.b.a<"s16w5e3vd9iydp","SwURAIe7Bu6Ja/8wZ+09sw/MoQaqsBptto9A+GgtBu21ejtWmsc=",991360988744260540,-4796407074432906713,6907298862914436330,7121106975991092310>();
                  switch ((int)com.yiyiaddon.m.b.a<"srbpxmwv5e98u","TkPw8CiCTp6o2BFZIz3VoUYdwheZ2wG30VbQxTVcESk=",3569711594518782610,4173422625900638117,2225539614605911720,5486487099621843630>()) {
                     case 379107989:
                        return var2;
                     default:
                        throw null;
                  }
               case 4:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s3f8kmxqergzej","NPfMjHW83nI8pcSXtxQX4o4exoQmnzo5ibbbs0V23qjl3OmSj2Y=",-6167745615646240920,-3476128977211204996,5383553189281353872,-2790322024625009293>();
                  switch ((int)com.yiyiaddon.m.b.a<"s16f17nwtrt0j4","1FIq42pQuIwCRtYGRUQNeCzPTvspj0+AQ6NiJSNQUjg=",-3448269452212269738,-1380285577243117923,-5523708075719140126,2163649801463423496>()) {
                     case 1827038208:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  switch ((int)com.yiyiaddon.m.b.a<"sw5d7j1ng8tn7","TOI/l5NR7+OGpOXdWHV8GIZSW1nkSm0jInfBXZF/m+8=",-3019596296734653534,5490395730231443613,7135533321722444456,-6368814159684284875>()) {
                     case 647393922:
                        return null;
                     default:
                        throw null;
                  }
            }
         default:
            switch ((int)com.yiyiaddon.m.b.a<"s3gawpdqdtjskt","Iz9x/xRoJ7htpTNXtKJEmkSPW03SappfjNxk6Ft5dMk=",-884883874775291141,-816837042452809115,-5648482986546305892,-1948100507585063474>()) {
               case 34675917:
                  return null;
               default:
                  throw null;
            }
      }
   }

   private void b(Map<String, q.b> var1, Map<String, String> var2) {
      LinkedHashSet var3 = new LinkedHashSet();
      Iterator var4 = var1.keySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3kwmydatms7dm","yyIzU1PTMmu5liIxQrJ0z9674Ovim3kN/2TbIXDJEk4=",8262969654087492611,-4022191496557367976,-7632587294015299193,276083405466837954>()) {
         case 829031971:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1agovf8i57q8l","Ok9X/rxrJvcGeZUIWxqUYCVmvxiHGpnfaEzIPcXejd0=",-3939033589758372960,-8976697827038921282,-6102411026257291141,2244371694399708452>()) {
                  case -806350503:
                     String var5 = (String)var4.next();
                     if (af(var5)) {
                        label109:
                        switch ((int)com.yiyiaddon.m.b.a<"s3w05nn8mooghv","cw+EGpETaVcpE2yyd6P1CSH2L3LBSaVwbk0NKPT9dYE=",944141177912967187,1284230014375536687,-5697890842216157008,-1313196124980023328>()) {
                           case 335512884:
                              var3.add(var5);
                              switch ((int)com.yiyiaddon.m.b.a<"sj2q0s19fkb3a","Ctt5rI2XwMnacWok4dim+FPYG0KPlWCbFiHbkrATsnc=",-5047294006709399726,6358332096082574301,3000449350061494692,-4957305868424889734>()) {
                                 case -1718687086:
                                    break label109;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sm1gu2ajrd6ie","Sbmlat+uc/wOaMoQ6c2cCSvD1x3SRiSEFqMKkxRfDmE=",6337939414683895913,496450586676991248,6015115982359849380,-6909847263745265717>()) {
                        case 982203727:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var4 = var2.keySet().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s14077ou9p3ouj","nxQ/MXqlNlPMEmNeHSxJ6+ppz4ovZn4Nm9oH+X0YsrA=",3721587460007609016,3584694716192134314,7531880469400240409,7571581797277001155>()) {
               case 1100364436:
                  while (var4.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2i697conrkbsi","XQo7VJSjkePzr+fu0a+sbgZbYviWpuRV97CzNlTUsKE=",-5750759607683274349,7518033669633455708,1208947477912215372,174676445650853437>()) {
                        case 1886885229:
                           String var22 = (String)var4.next();
                           if (af(var22)) {
                              label99:
                              switch ((int)com.yiyiaddon.m.b.a<"syvsw60uan2yq","NktX7pcY815QxZXlG3W4UdpF3GMnB5bTa5f+rrBrYV4=",1823867148616958780,1830206852605549994,-6582543587911482501,-4630363469589171334>()) {
                                 case -525358015:
                                    var3.add(var22);
                                    switch ((int)com.yiyiaddon.m.b.a<"s3mnydci37lbvx","XF5PnBw1uAnjJj3r150SFTJJroS40wFk1dwPbcgyy+c=",6780622838073625672,-391853884463070339,-5219578690137262453,463068832322694094>()) {
                                       case 792846430:
                                          break label99;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1b540xkrf4j5r","5Ez7vxSbZkXwuPdu+iiT2egcPAOkW7feALDzYyhjcYs=",7171715739208565115,120729446964143353,-7500890980205047051,-233582059061124630>()) {
                              case -302188727:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var4 = var3.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s5vziih0qypc","h/eCL0JzUaxubiA2ahhIdX83/zeA34ukN035SC8INTo=",-5039508391521575733,-876472863377684062,6590629722128473072,3992706082667647966>()) {
                     case -1648644848:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1qdqswjjkuimo","miy8LeISbOXe6LBgtR5KXRdsRUS3KMNDS0SCtWPyDwo=",3880552629462025497,5430022038897188230,5958487219665478017,313728514117550340>()) {
                              case -890303658:
                                 String var23 = (String)var4.next();
                                 String var6 = var23.substring(
                                    0,
                                    var23.length()
                                       - (String)com.yiyiaddon.m.b.a<"s1riemwu22mxqb","sh3dsz6OtgPb3zKHeazSRwpn2bGOQgQSanlznTKlEdVqFyQwhu8poA==",-7186957493898948715,6556747559717703564,1580527867212433425,-6249695817220242515>()
                                          .length()
                                 );
                                 q.b var7 = (q.b)var1.get(var23);
                                 String var10000;
                                 if (var7 != null) {
                                    label82:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3srggbza3uk12","xkDBuKSc6CbiDv8uoyQSK8EA3LelIcb5vOso3X1OQe0=",6850841562695957623,-8615906275296331547,5815169254830242251,-8758584827819132045>()) {
                                       case 1104403471:
                                          var10000 = var7.dP();
                                          switch ((int)com.yiyiaddon.m.b.a<"s3lf1hzo1abh9z","g24TPd1zFImvB0iy6rjvevw6XRBZpfVU4dyAZubPByo=",-7554552554072762298,4809583546419700788,-883942142678391225,5026678199205631390>()) {
                                             case 362519527:
                                                break label82;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = aN(var23);
                                    switch ((int)com.yiyiaddon.m.b.a<"s1mph4aap1eui2","53CYWBn3rsPzBwI1vgSDXewNev+T57vELnOw4vI8nwI=",8161105568716509647,-1931615061779331233,-4337393616338889481,1128479030131581651>()) {
                                       case 1140671711:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 String var8 = var10000;
                                 if (var7 != null) {
                                    label78:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1nlzjav9in305","hvoW2rK1H3i8ywOQthGVKNTNEdfPtHSY5V4OHTxN/4o=",1311587479742352086,-5452454545547951705,1318268867634481017,2937076264961152833>()) {
                                       case 1181040224:
                                          var10000 = var7.m();
                                          switch ((int)com.yiyiaddon.m.b.a<"s37hg691rk6euv","WF2Y/K2Nzp2wSlr8sDcEEtPYk+FTYY4Ux7PEaw8roCE=",5101797629516871421,4472660693553912996,-8235558962344489407,-977907771597577009>()) {
                                             case -211992304:
                                                break label78;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = var23;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1323cu60ze6ua","nU0KWyQ3Fn2QDD5dgNDq9FLEYUaneUAafCcXFdlCIGw=",-7875989828885646617,-5877806255498140991,2996535868355204377,9181408738087788549>()) {
                                       case 2134627654:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 String var9;
                                 String var10;
                                 ArrayList var11;
                                 ArrayList var12;
                                 ArrayList var13;
                                 ArrayList var14;
                                 ArrayList var15;
                                 ArrayList var16;
                                 String var18;
                                 label121: {
                                    var9 = var10000;
                                    var10 = (String)var2.get(var23);
                                    var11 = new ArrayList();
                                    var12 = new ArrayList();
                                    var13 = new ArrayList();
                                    var14 = new ArrayList();
                                    var15 = new ArrayList();
                                    var16 = new ArrayList();
                                    String var17 = a(var1, var2, var6, var11, var12, var13, false);
                                    a(var1, var2, var6 + "", var11, var12, var13, false);
                                    a(var1, var2, var6 + "", var11, var12, var13, false);
                                    a(var1, var2, var6 + "", var14, var15, var16, true);
                                    a(var1, var2, var6 + "", var14, var15, var16, true);
                                    a(var1, var2, var6 + "", var14, var15, var16, true);
                                    a(var1, var2, var6 + "", var14, var15, var16, true);
                                    var18 = b(var9, var17, var6);
                                    if (var11.isEmpty()) {
                                       label72:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2m551jucorz0c","FZk+IPaOFg1egrahSMxCpIWrOZM+f7kK6D/pTyFb9Po=",2462891387283023330,-378233844410550810,-5917989602899677520,3187575108850665419>()) {
                                          case 2051588077:
                                             if (var13.isEmpty()) {
                                                var10000 = c.CANDIDATE.m();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1gof108zlh7pe","DeLhpDw5OrHx4ZeEngJ4I4l4HAkFk0AtjHJZFz7dWbg=",-8854618677025707543,4103531431437830426,3210201220547839612,-12009777320481966>()) {
                                                   case 600700831:
                                                      break label121;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s3iuqv3gcyp7z4","xPll7a3HHpW4DjGsL4xEScxpKUyu090AxjNv6Cjnmro=",831424578882231064,7006960676642863647,903009719408444977,1366926980795066202>()) {
                                                case 253990643:
                                                   break label72;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var10000 = c.VERIFIED.m();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1kue8p8kq4fjk","Bmk4ZeOijwEXxaKUyudQ/TpAf6aQcriZQieVW30QAkA=",1074012300459152897,5794329282000833836,-754489452468752506,-2566392088734090599>()) {
                                       case 1801924987:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 String var19 = var10000;
                                 this.bN
                                    .add(
                                       new com.yiyiaddon.e.n.i.a(
                                          var6,
                                          var18,
                                          var10,
                                          List.copyOf(var13),
                                          List.copyOf(var16),
                                          var19,
                                          var8,
                                          var9,
                                          List.copyOf(var11),
                                          List.copyOf(var12),
                                          List.copyOf(var14),
                                          List.copyOf(var15)
                                       )
                                    );
                                 switch ((int)com.yiyiaddon.m.b.a<"s28j9ws4v0x6v3","Ms0GhpgN/XhzEFgzbhz0HibdeBpFl5gw2mQe1pYMx8s=",3437920950884656208,-4988921994299612309,-6531179550765691381,4208095439540315753>()) {
                                    case 92859057:
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
         default:
            throw null;
      }
   }

   private static boolean af(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vi11yb1ueo90","YduhwNCcvSOTMHls1TzKosKbSYWuchzy46HdGNBoPpc=",744912774629588445,16010097677443527,-5664780162672711986,-8734796176078041131>()) {
            case -1647157501:
               if (!var0.contains(
                  (String)com.yiyiaddon.m.b.a<"s2dr5bp2jnzmom","1wR21B0iX6Z6p2P7uu9ZUVhdboE+9rdm3VTMcB3Y",-6993869138002307973,7203895005121086896,6895723483483979333,-1255817650551707116>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"sqr3dbkhn90qi","AuxsYcBCzCiI1ps+t7FFxdWKeFCv923Xk/O6zJjTlVw=",8939482494696973122,2583897555556386944,-5903901988630433569,-5165874226385305070>()) {
                     case 128459355:
                        if (var0.endsWith(
                           (String)com.yiyiaddon.m.b.a<"s1riemwu22mxqb","sh3dsz6OtgPb3zKHeazSRwpn2bGOQgQSanlznTKlEdVqFyQwhu8poA==",-7186957493898948715,6556747559717703564,1580527867212433425,-6249695817220242515>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s35lc7t1jwgxs7","ZnWh1pUiTZgoyMW9bsgcwJ8avBcXJR+TaG4GMp2uY/s=",9097255490176180793,-985637182263228700,-1670393093929901381,8556488662128098245>()) {
                              case -512190554:
                                 switch ((int)com.yiyiaddon.m.b.a<"sthduk2gh78ng","JrgsBEg/pUZ1CnTvTN3cvTngXJxVVa3ZI2jqnakUb6g=",4384544454952420406,-3434440585124288965,1575310305909664671,7368218413657187587>()) {
                                    case 350955893:
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

      switch ((int)com.yiyiaddon.m.b.a<"sojz60y8zp53j","YNwn9G5yJLSkmFZRVGf65UysSpxM+n4QBtHpwA/kbX8=",1642928721300909852,5203894875312592668,1090760554743862626,7253563574834562129>()) {
         case -1780626656:
            return false;
         default:
            throw null;
      }
   }

   private static String b(String var0, String var1, String var2) {
      String var3 = f(var0, var2);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26prdkhx7ifzh","jrxC7UXQbN3Mq1sPjkx51F+5UVKwm4YdIQJUgSwjIsE=",8448685100657771027,-9203100669878863723,-7919094822413938922,-8400415051463753917>()) {
            case 895446444:
               return var3;
            default:
               throw null;
         }
      } else {
         String var4 = g(var1, var2);
         if (var4 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sh2q9h9ipbsv5","QcY10gHeIfNoE6cEVMZV6vafVOmuKWiG09UCtrJrxxg=",2886679531664538531,6827786838755599227,8413152791629408280,4424068459957518397>()) {
               case -128214644:
                  return var4;
               default:
                  throw null;
            }
         } else {
            String var5 = h.aE(var2);
            if (var5 != null) {
               label32:
               switch ((int)com.yiyiaddon.m.b.a<"s33u9rrm41j1wg","eichJD6xJiDFBirxAEFB3oI9FAKIN/nMKLtpCCfqaoo=",-236842291468976386,-846769360613022721,363307249142413928,5293022150761387502>()) {
                  case -1775823861:
                     if (!var5.isBlank()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1o16lgj5ubiqo","xoZUT6VUAIWy36UmRgWsGSaCZopxzk1fWeQmZ/IaaI0=",-5999790684479510328,7452329309615728725,-8110189762294054784,5535982505297743364>()) {
                           case 738406497:
                              return var5;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s22qwtnnwg7aq0","f30IredDGpJoxaimx62KLZAe3RfY3FMz0Z+cNigKwnw=",68557381141544939,8919167496357392829,4263750835664782399,-3892363700310647517>()) {
                        case 803957701:
                           break label32;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s1wzmr1o3elo84","BgPYntTU2zo4iQH4wXzJdHThlhKWQB9m8Q3+TzfCAbM=",1948048894481907036,-7889757192645531171,2197420411169071819,5939887739312580613>()) {
               case -500048650:
                  return var2;
               default:
                  throw null;
            }
         }
      }
   }

   private static String f(String var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sc81j8cfs9yo6","hGz+/596BCDOXoZq2jAaTZ0YIGFJU7/Yf+t4MmzuETw=",7870291312629947839,-13197597219950729,-7083907243707085590,-3787662569655609173>()) {
            case 898915345:
               if (!var0.isBlank()) {
                  String var2 = var0.trim();
                  if (var2.endsWith(
                     (String)com.yiyiaddon.m.b.a<"s2ymzdpi4i3xum","dtADjdoFGsBhV5x1s1jtXBk7uUvkIyNrOmXTPmkWRfM=",4340139677399505311,-7541715135481083006,2544493904641791308,-2451971814303099898>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3oa92jk42mwnn","jewgnToFovO/QADj7/MK0ZlWqFgZM4qH5hSYOYps2eM=",1955820682688804117,-6437283224905749188,-2083926172025840586,4426332711166596894>()) {
                        case -473296526:
                           String var3 = var2.substring(0, var2.length() - 2).trim();
                           if (!var3.isBlank()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2aopaafbhxwd3","eo3DXhmqD5vmqDbapiK5ZSzGY0qCo+TJOfvgp9i96fs=",-2945831139809323056,4349477420049727957,7802638617282648576,3634894219857220208>()) {
                                 case 64053067:
                                    return g(var3, var1);
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var4 = var2.toLowerCase(Locale.ROOT);
                  if (var4.endsWith(
                     (String)com.yiyiaddon.m.b.a<"s3cy31zblxyona","x0OngEDZAQ4q3o5/iFzZoa/piACqw4CPz14kJ/VSpp5vZNfDLK6kxw==",994761393164550744,9049440816025383022,1592713165929718350,3092123495973597393>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3bqro5nczjyc","870YL5MxPbbqMbEDXbSXXZZF4O0oNUyIJjd0wAUiP88=",7628228127789457210,8900782923328989209,-9149386339606622440,2724924589131861856>()) {
                        case -94775629:
                           return g(var2.substring(0, var2.length() - 6), var1);
                        default:
                           throw null;
                     }
                  } else if (var4.endsWith(
                     (String)com.yiyiaddon.m.b.a<"skfg95k0lkooj","yXeSwbRjoh9Hc735v2Veuz4mUila7DEXTehTiebbTmr+cuMrnJo=",-2153596063373580404,-7408082681673430622,-2042676477300135105,-3890778709366511387>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3bevnahs30xtq","4NVvllud6nXMvw7ibM3/l8Kigts3NaodaSJ9p02sGyQ=",4925468540559462855,7364625553183609697,6484986228526919998,694842624093806446>()) {
                        case 1319850007:
                           return g(var2.substring(0, var2.length() - 5), var1);
                        default:
                           throw null;
                     }
                  } else {
                     if (var4.endsWith(
                        (String)com.yiyiaddon.m.b.a<"s1riemwu22mxqb","sh3dsz6OtgPb3zKHeazSRwpn2bGOQgQSanlznTKlEdVqFyQwhu8poA==",-7186957493898948715,6556747559717703564,1580527867212433425,-6249695817220242515>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1e918wffkk1xz","P3/QsJXfOuR/stmzFW3a+8IwKZ3FeO+127XEQQ7j4WE=",3694890365464371010,-6526792146770789153,6853015871567907516,-1466104590721172761>()) {
                           case 41408706:
                              return g(var2.substring(0, var2.length() - 6), var1);
                           default:
                              throw null;
                        }
                     }

                     return g(var2, var1);
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ibupv3mqdrfx","rqrsxFedalq1ixZl7KkTfys4+0Wv8twJXIxYtGEAIdk=",8370417244939715021,1917654355463046212,480134322082730200,4716093645595163986>()) {
                     case -1491214422:
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

   private static String g(String var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3iq6gfv5gyjf6","BJfSNpwb2BLEvZplBsWZP+Sf0im/EalQ3Phii4K3TZo=",-2428101339982991888,5735581660269017770,-2813738577810281150,-4754376310258579512>()) {
            case 1106130853:
               if (!var0.isBlank()) {
                  String var2 = var0.trim();
                  if (!var2.equalsIgnoreCase(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s327autb06v882","qFneMP7OmmvzX5R1amOT5M+IGS9HKpLKH1iNEWzKp7s=",-5893119605141401232,-3671880523122161553,-1013657885132296615,8440764310850240662>()) {
                        case 1962819399:
                           if (!var2.equalsIgnoreCase(var1 + "")) {
                              return var2;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sa2j8xdffjmcx","Cs0wK98g+If5UBQhnFTn/q/THpdsgIPLIvBdhwSOu78=",811040948530880897,-8550550704758128435,-51820411678387985,2929198265639433953>()) {
                              case 253398401:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3q4yuffdb81yr","vQBjyWXlywzpGovHXibrECjpb9+Z05vx9K5Oejb3qEE=",-3978462733045409524,4494778278101333281,-2476002851159830588,4551237453855983548>()) {
                     case -451055623:
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

   private static String a(Map<String, q.b> var0, Map<String, String> var1, String var2, List<String> var3, List<String> var4, List<String> var5, boolean var6) {
      q.b var7 = (q.b)var0.get(var2);
      String var8 = (String)var1.get(var2);
      if (var7 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s36i64wrzdx20b","d4BqwN1eAItJXNc+YA3y3VPlkTceQHt3v8SDZNzkjlg=",8316068152054655307,4566621595429026062,7215067782225540350,-6841990968635304470>()) {
            case 460307580:
               if (var8 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s7enpp97n9326","9FQ8DH7imLDOL2cwVymdnIU2p4dcuS23Tx7oHKaDkas=",7415715767401093126,-6023362650270636143,-3289461136122143740,1682055458152218163>()) {
                     case 2122403627:
                        return null;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      String var9 = null;
      if (var7 != null) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"sdw0tlesz3d9z","sjWu8u+IbATKM3JgkKLr64PxkxGhy7XNpGjGicgMENg=",-4957405219925470706,-25198436488413980,2003427598891265836,1524673622697319207>()) {
            case -571068638:
               var3.add(var7.dP());
               var9 = var7.h();
               var4.add(var9);
               switch ((int)com.yiyiaddon.m.b.a<"s3p00035i08oht","8tG6VTD34lkBpG7Yih+tkV9YsCDTVaeGhna+Msqougo=",-1263778586469688123,21828785749509184,-5203332656219194449,6769487542545523640>()) {
                  case -665821362:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var8 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17ewze1eqgmvc","jPzEr6VvqiMvdm4Rw+x2EeS/8JfLS0OO0p6opC1uqCA=",330655072785542220,-1198851991551707164,6009665644902435902,8826100241624946642>()) {
            case 693351198:
               var5.add(var8);
               switch ((int)com.yiyiaddon.m.b.a<"s28ppv2937cyx4","QwVykgn0fNvxP3dc5g/ffDMtTVp7cuQFpSGnHRHYMR8=",7353858700998117289,3631257268034056488,4627711884407291122,-4974276937206865179>()) {
                  case -764327818:
                     return var9;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var9;
      }
   }

   private static void a(q.b var0, Map<String, List<String>> var1) {
      String var10000;
      if (var0.a() == q.a.BLOCK_MODEL) {
         label39:
         switch ((int)com.yiyiaddon.m.b.a<"sh1oohykqt87t","zI6EKQGSBFKlJ1bFW8C1CzfG6Do8dOWKrlrmQGEkEWo=",-710329789575096435,4556240861362218468,-5457602502902537342,5328571743830089742>()) {
            case 1913243608:
               var10000 = (String)com.yiyiaddon.m.b.a<"s23am8oqusva7z","uMKprk930OW03vmmaU0U/ZM89TijUXRtSOS7ENHY4oedPHuIFNL3uw==",-4442298483181952852,1252340647348399160,2254401043739641006,-6871883843384588422>();
               switch ((int)com.yiyiaddon.m.b.a<"s3ytl1xu9xcpf","ccXfREKvcz1VRAejI1jKPclx49gpV/EkSykOCDX1oHg=",8113172586211056557,-326101934687478575,1728402439577298477,3619699829462000878>()) {
                  case -462471859:
                     break label39;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = (String)com.yiyiaddon.m.b.a<"s23nydlitvlevp","hrAqAY/oM6t0IYmznEpDdWqwi1H1jUISnhvkM3cB9SdocFcG8ds=",-2122100605857933107,-9066917199500364862,7873199603005334345,636881508215692912>();
         switch ((int)com.yiyiaddon.m.b.a<"s1ab6qiguz9a2i","2PUzxMm5/+HbNjSq2LF6SRCJQnG3gZGr63+HwPeW5rc=",-4400710204317991618,4141589443419383212,-3343437526522156347,8140092260650735220>()) {
            case -1607348522:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      String var3 = com.yiyiaddon.i.e.a.bS(var2 + var0.dQ());
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sd5fdk3o806p9","0hrBGvXcPWC2FE0ojMYUb3nHqye1RXVlg1NVOqayH5k=",-363083960803817124,-2950002477954590562,-6695358216844100808,-860508432066791346>()) {
            case -1685451315:
               return;
            default:
               throw null;
         }
      } else {
         String var4 = var3.toLowerCase(Locale.ROOT);
         int var5 = var4.indexOf(
            (String)com.yiyiaddon.m.b.a<"s3h5k69twxz1db","ZJVaDGtxbw6JRoQ+TCTIhAgUMrMzjEkJ9Qqs8nvL1Ud+neugmTh8qTgH",-8923662103276369465,-4897031423195504746,-3447805573962803418,9153336269185790387>()
         );
         if (var5 <= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s172wcbpyevoby","Z8/E3YFHFhSjKvkhniBpskORtTBGbWmrNrHWfnEUXi8=",-9151689270553435489,-874604762357292380,8930073709235632954,8025177102124859745>()) {
               case -1935534914:
                  return;
               default:
                  throw null;
            }
         } else {
            String var6 = var4.substring(0, var5);
            String var7 = var4.substring(var5 + 1);
            List var8 = var1.computeIfAbsent(var6, var0x -> new ArrayList());
            if (!var8.contains(var7)) {
               switch ((int)com.yiyiaddon.m.b.a<"s19qgehoct5ab0","XcYO2T0sAknlKoH+6CMmiJc9C1JZxkYbBmxD7SUF8mw=",-1042271524687082868,9211993424306161582,-3470418010849892002,-3512443336712269594>()) {
                  case -1470585594:
                     var8.add(var7);
                     switch ((int)com.yiyiaddon.m.b.a<"s2agx6yvdcrupi","9tE/5FxM9iHdtql8Y4i1FWdqWErJhaIoOQOK+gIrJkQ=",7329212241848104619,-5449950660014121687,-4436378994732631985,-1766290629976643289>()) {
                        case -1228666697:
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
   }

   private static List<String> k(List<String> var0) {
      ArrayList var1 = new ArrayList(var0);
      var1.sort(
         (var0x, var1x) -> {
            int var2 = g(var0x);
            int var3 = g(var1x);
            if (var2 >= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"sem243jw84u6i","iYphoDDl5tbIoLZreYiY6qw1Zy/LJz+rFWiFQve6G2s=",7332509775796381845,-8507778607041382125,6897200974044004149,636508503975486636>()) {
                  case -1518396760:
                     if (var3 >= 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1kz4ex5mghtbo","+Ejm+LpT+M4+U+xuASboLy+d3RiBiAmHQ//ws05FyKk=",4249509235419980672,-7624902697502780929,-7648946021164392411,-1118312378108473076>()) {
                           case 1916963818:
                              return Integer.compare(var2, var3);
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            if (var2 >= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ucho7c3wrqik","KL4awF1vCEkH6OFrqn+qXbG/atcA2w2Xrx7/KuyD9gM=",1354115754242893258,6372073845360793477,7867461354990842233,8214620761064192483>()) {
                  case -1802642214:
                     return -1;
                  default:
                     throw null;
               }
            } else if (var3 >= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"sxzj6gofw25z7","+qrhB6ygTF87A9F3xaOk38+wHFvALwspI7RFG7TQLNc=",-7498780622426566121,2636235271427402771,-9114726622078872899,5919212760592827519>()) {
                  case -1583098651:
                     return 1;
                  default:
                     throw null;
               }
            } else {
               return var0x.compareTo(var1x);
            }
         }
      );
      return List.copyOf(var1);
   }

   private static int g(String var0) {
      int var1 = var0.lastIndexOf(95);
      if (var1 >= 0 && var1 != var0.length() - 1) {
         try {
            return Integer.parseInt(var0.substring(var1 + 1));
         } catch (NumberFormatException var3) {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public static String aL(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s8mdf7awd5lsa","MlNOAQ8szVfu8LbVsPhSvX4T4Y6RWlicWl7cZhnHiUk=",-4911229873330686598,-4902142262495560446,4880858217034793822,7861957008558322270>()) {
            case -575585390:
               if (!var0.isBlank()) {
                  int var1 = var0.indexOf(58);
                  if (var1 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2v4tx8sxkfsil","kWgDJZlwNTtMCNo43jOLHO0lrhpRCbABhUviDMZIwA0=",-4268216364605962812,-8636229641737157515,6820599494188298489,2204775694172911789>()) {
                        case -497714744:
                           String var10000 = var0.substring(0, var1);
                           switch ((int)com.yiyiaddon.m.b.a<"s1hpgc0xdcyns2","Yn6zw6WTO0MLAhX6zZHhUDwXVJOMWTC40o/Z1J4se4I=",3184652226731650652,5216045965583192465,1994243655148527348,7179031459325408112>()) {
                              case -197760131:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2aeuwk67g72q","08WW23OwqShyO/Y+Dk4671bu7GMQlFlXKnsly87u5/E=",2141616695136656845,-2359339254288053558,1982668416605482318,2445337753874048326>()) {
                        case -1371663704:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3i5cij5ftu1rq","4cPGGB0IG33SFbW3x1vdNxqBrvk5bAL7Kqea3qjClCE=",-2430289820805984636,7507681795269604614,2143109464089544185,-3748553487516757944>()) {
                     case -970185544:
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

   private static String aM(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1by1lpoo3pb5c","uYZ9kLZzqIWQpcRGJnW2hkNyQo5SjpYFgw0rrDDq+Vg=",3665790300954866983,-4068442904027180161,8260570070194351656,3882503840028252333>()) {
            case 1116624699:
               if (!var0.isBlank()) {
                  String var1 = var0;
                  int var2 = var0.indexOf(58);
                  if (var2 >= 0) {
                     label44:
                     switch ((int)com.yiyiaddon.m.b.a<"s10r0mpp8mnnmz","yzdxCs+ft6JWeqIWXycMT2PqQ3DX6J2RF2Q2WKEQahM=",-8021917948344147477,8195846400898150179,-159127040424675488,1531157124573923701>()) {
                        case 363737065:
                           var1 = var0.substring(var2 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s2d84vxhbsvhv5","2BXMs9HZtR37c2OpAbxix2DL3U059Deox8W3B0/4Zy8=",5142149482124507063,5807229599342564783,4008750460050950539,-1849465172747624955>()) {
                              case 653870057:
                                 break label44;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var1 = var1.replaceFirst(
                     (String)com.yiyiaddon.m.b.a<"svjhjpjjyi12f","DPM0fjM1uo6NiEIhgcol6jwkik+ZYF3RXWLflhw3D3kDx3VWywG51u1+f6mkWDvKKudobN5sGCpG4rilg/D5CNQS66axLAzRIK5UyK8I6cMkjw==",-9194649198643165507,3059038895094974521,7032539806853722054,7814516602241471208>(),
                     (String)com.yiyiaddon.m.b.a<"s1xj317288yjxs","PVxKww23eIdpH3gHDENXPDwg7cXV5KaMVT/78A==",7415893214764442574,-4510034983957122647,3586531709416583121,3609706977406214742>()
                  );
                  int var3 = var1.lastIndexOf(47);
                  if (var3 >= 0) {
                     label39:
                     switch ((int)com.yiyiaddon.m.b.a<"s10dsi4kwy8qma","JeO95Jc28vADHzwlaqAkkzQqfIpQW7l+MIQosOfIjsc=",-6717519756593135598,-5517328403059599171,-8152166829302749593,-8548503537136608574>()) {
                        case -1059186388:
                           var1 = var1.substring(var3 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"sqwlbvrddhw1","huxaIeNeyZOsbRxkblIljweN0XwExq36Xpg7b56KC/M=",8770701542614916931,-2309024799066663312,-419087566524289356,7632877848748245060>()) {
                              case -921779806:
                                 break label39;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2x3qxkceoqilv","uU5e+vy1bvqWmcuOQIJa4M3Syw+5jKoi9RFsLe8DtTI=",-2433612436004515821,-165372031041701764,-7639874187981624612,6378686893021308280>()) {
                        case -1157719597:
                           switch ((int)com.yiyiaddon.m.b.a<"swx6mktgwq012","lVx+Glkk6cKhfdhkR0naxn9Rb1CVW3Ws2PSlBv7u8SA=",-904771812411634560,-4263549908086743636,4581142040558547224,-1369786087713035086>()) {
                              case -2002748201:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s258t9enwm46o6","GlTNND+q4nhCKXtaxL8HeiuKNUp8/umWiOQg+Ev/zhs=",-6516635632946040812,16849019414404181,3862112434916878961,3257509621217973205>()) {
                        case 1117665386:
                           return var1;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"szga5drs2hao3","+pwJUoueYGGWVQ2c8SIHT9HR0VvTg/XmunegioZk3XE=",200022094484028898,-8533669831804947133,-2960940444822670232,4458520969846841085>()) {
                     case -1663944753:
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

   public static String c(com.yiyiaddon.g.c.d var0) {
      String var1;
      label60: {
         var1 = null;
         if (var0.fZ() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1g3j5ixu9uapd","TvkE7MzXMw5Pr0oiW7neLBKf19OZJTo7Ho6Od9/ZbGg=",2546034193890993987,5480173759874153050,-545236664538537828,3070558880041366695>()) {
               case 1656033799:
                  if (!var0.fZ().isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2o30cocwvlz7r","XLK5jNMuxll7d/plU1fvchJtb9vxzBG49b1Qk3tnOok=",4286229498187110090,-1971805361819701514,8395639245275390443,-8078480101038348705>()) {
                        case 418670296:
                           var1 = var0.fZ();
                           switch ((int)com.yiyiaddon.m.b.a<"s1kf8tmc49c1f7","GEFuojX9Wk/ZEf6V+BBoOGFiMPo+dCd+FfPHwJA7B9A=",-5485618168997121202,2269583457835647867,-2092190760027441110,8251388767142789438>()) {
                              case 1464857516:
                                 break label60;
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

         if (var0.dE() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3voe4tddph95l","29+GvKguhNXXISyeegls9Ekh3prEytW5HRbGXB/Nghw=",2476028583722450920,-7318876054101375292,346468191121054031,-8566249363553052846>()) {
               case 963184869:
                  if (!var0.dE().isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1lqkoq6higrym","UAbsKNk+i//wAgnu7qkBIX9q1WPj/fRkms8H6ZdursA=",-5961287202918382558,-706229135909822840,7837321719904811494,5263377509101466854>()) {
                        case -861680297:
                           var1 = var0.dE();
                           switch ((int)com.yiyiaddon.m.b.a<"s1pnxn57ubt23v","e2wtWW2g6770F15KbunT/L1Kut5SWb0SlM+CKmSJLVI=",-4523586177340066326,-1783419983764702720,1011344494931780881,8605846980577574639>()) {
                              case 552468912:
                                 break label60;
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

         if (!var0.be()
            .startsWith(
               (String)com.yiyiaddon.m.b.a<"sui7dotu94fcf","B5/5E0UtdkCnO7vvdjb7DlXPF3vvCBAQbewL1FbX27wzq30TQGLrF3EzVUoS+YAh",-3735565410241014408,7847201894278615194,5401076608123102284,-7969907279345046849>()
            )) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s2y8vvqis4fpmz","A98lkge7SVsURMa7V8psAl8IXtTOejmqPgtZcQQ+9GA=",8325260077290454027,-7947579785586489719,-7782086774845435996,5759297355206313574>()) {
               case 733539782:
                  var1 = var0.be();
                  switch ((int)com.yiyiaddon.m.b.a<"s1gl8ytce9lhb9","t4O6F6GTKpHv26M2RxOPrU5u6Qljzq9GeldlqeQyUgU=",-2827497188223629570,-8743456840404597149,-2199668708184545775,-6829760163346465266>()) {
                     case 486116255:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }

      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2e61sleiyzmn","eU5DSF2q7RNdixMDt0/CBSTDGzGzO7TvtW0bv4kPLJc=",7559841564653392283,9105492433627483274,5811338649713866099,-7651915996173585119>()) {
            case 198267233:
               return null;
            default:
               throw null;
         }
      } else {
         return aM(var1);
      }
   }

   private static String aD(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qveyvon7yw73","88SLQmnVQMvOwYAE/MUptsvXWA5RiyadWDJpltDFVBo=",518335538767654022,-1201032615976325089,-4634606026205283607,717547474129724506>()) {
            case -2075874443:
               return (String)com.yiyiaddon.m.b.a<"s1xj317288yjxs","PVxKww23eIdpH3gHDENXPDwg7cXV5KaMVT/78A==",7415893214764442574,-4510034983957122647,3586531709416583121,3609706977406214742>();
            default:
               throw null;
         }
      } else {
         int var1 = var0.lastIndexOf(47);
         if (var1 >= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qnllnvlhwa4c","DiNdINIPTPXJjJqTdUYIydc/c97iG7Xv5UBNMYYWs2Y=",9136709800680186351,-558623388625317681,6293818462069798233,-2744456915508219778>()) {
               case 1312777485:
                  String var10000 = var0.substring(var1 + 1);
                  switch ((int)com.yiyiaddon.m.b.a<"s3nack2kmkbzer","c09elj5G8/bbcDJWotyavI5rGMa/5RbvwKaYg/Fhrek=",-5373716213639736793,-750478932677162756,2487682487632699214,4949955828509597459>()) {
                     case 1371036318:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s2yo9783knwof","5BTLjjuwXnEUwnptTrOHa6CW4t6zxHQ+GZ+yrwB2cYM=",3914906983052799835,-8857750936432561652,8798746545045777771,-2159036755749462164>()) {
               case -1573876961:
                  return var0;
               default:
                  throw null;
            }
         }
      }
   }

   private static int h(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         int var1 = var0.length() - 1;

         while (var1 >= 0 && Character.isDigit(var0.charAt(var1))) {
            var1--;
         }

         if (var1 < var0.length() - 1) {
            try {
               return Integer.parseInt(var0.substring(var1 + 1));
            } catch (NumberFormatException var3) {
               return -1;
            }
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private static String h(String var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2y1f3xkkw4582","JqEXzSLzUI22v6RIgh8tul5D7+0RLfa5/gwwSf05TQY=",5618025980081987722,-5163955634781708745,-6018364810581580419,-6776777232218397332>()) {
            case 1527532196:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"si0izbmrjitov","plpcynG09behG6JomSNahJeSwsVemXxAuojrCqoN/fo=",8236231931468164423,-4466565392998440134,-3188683691795022052,-3773528845486881632>()) {
                     case 1647980238:
                        return var0;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s145zzfwideosk","2lRRNk8gti2Fz3QsDg2WONVRduwrufkZitlw1qnItcY=",-9140914720654250484,578229863636271073,-5725504971107646608,3358673707285021473>()) {
            case -1181850635:
               if (!var1.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1160jbg6i9e1c","4dwB0wbsKBa/Au1RVCWjeY08p1uHXtsXdaRv7XThkIw=",9015746220170864181,7724241700061024460,-8161494058963765216,309297991544136756>()) {
                     case -1275337474:
                        return var1;
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
   }

   private static String aN(String var0) {
      return var0 + "";
   }

   public List<com.yiyiaddon.e.n.i.a> aV() {
      return List.copyOf(this.bN);
   }

   public String dO() {
      return this.sT;
   }

   public com.yiyiaddon.e.n.i.a a(String var1) {
      Iterator var2 = this.bN.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sfda02pq07uw1","tjyNER1X26NGtfwI1JuSll18nwowrVSh5p2ki3AvXD8=",7562444270690896309,1149045975073593173,-5950633261028919780,-7587329706640170155>()) {
         case 1866584792:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1dr1txty3uwxj","GRN/OglSLhElE6Tuy+sOPyAbmjSBWoXlB5DmN3KkTpA=",-5973027633536184688,1536081241344147144,-315597407174782301,2411031980910481985>()) {
                  case -405758085:
                     com.yiyiaddon.e.n.i.a var3 = (com.yiyiaddon.e.n.i.a)var2.next();
                     if (var3.dk().equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sir0z87vg9b9w","JfsXUL4t544h+efDq+9EZyjKuIFsVPEKz66D1vkgoe4=",8409616949684468660,155321013805144766,-5593189324405032933,367372040433024005>()) {
                           case -1334393974:
                              return var3;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3u5oouj26mgyg","x/mL5o7inlBh9BJOPcU/LNPap2eV6fcWfDE8SrIXBcE=",-7773749862124318485,-35975649651175056,-6886357920417417671,9141992826668267223>()) {
                        case -772122132:
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

   public List<String> d(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11p80hmutcl1b","rT9acWKQg119UO3sA8lA4K10ek8We5Wk9wsi564B4Cw=",433740646241448745,-5899018701018239148,7905738831025887552,2679713293317478479>()) {
            case -529880549:
               return List.of();
            default:
               throw null;
         }
      } else {
         List var2 = this.ab.get(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3dfqtmnr2tndf","M13sed9KbdbqnaKmaWWnZSwXKdhqlFRsqTOKPgdLlmQ=",-5557509149675223813,-421201129286694068,2774941411403525046,-3896739612145818216>()) {
               case -1275625868:
                  List var10000 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s4t49gvtkhdpn","MapypG7NZeU4Ryx+1/MbyOK7TvORz4xu3jGy8e7jBEE=",-7255721002600591589,5874967597224394413,-2725684507596336947,7430911998547254814>()) {
                     case -97663185:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s2pmlol1ea028z","RXg3TsLEOHvgtrAvi0qCeksUYz1GqrDxWlAR9Z5LC+g=",6232573434582214296,7194261099091473782,-2777427181343490971,7889462008103804287>()) {
               case 1492761052:
                  return var2;
               default:
                  throw null;
            }
         }
      }
   }

   public List<s> a(com.yiyiaddon.e.n.o.d var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o2b0ekl91t9x","hQyUlk8Bbmav3vDOfmdDgsjlz9SzWAJZnES9jpM2nkI=",7726015413110619658,6441432579211311973,450636577817847781,8254181852974804033>()) {
            case 31378249:
               if (var1 != com.yiyiaddon.e.n.o.d.CROP) {
                  ArrayList var2 = new ArrayList();
                  Iterator var3 = this.aa.values().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2sgfp1t4fr8hm","oc4ugOMQ2Zfk3mQGAjP72YVXpcghPz18QsnIMSPICnE=",2756221338909380015,-454014905114162557,-172632784601895750,-5850249583436507468>()) {
                     case 734653255:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s8974hiojih84","ZxQIQmCiQQeIn9or7yuM0+fCHvifUMkUClHwn8aSXc0=",5418839772138747325,-2672025557378244459,2028116629756537118,-2636170782315759039>()) {
                              case 450337384:
                                 s var4 = (s)var3.next();
                                 if (var4.a() == var1) {
                                    label30:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3o02uf9ggefzq","N/bSQezjx+7OXPAvwfUTXJAeqSNMLhpGQZBjUzI9W/o=",925685830508541930,7391383316975601234,7311054429890247153,-2051023504005748981>()) {
                                       case -27745785:
                                          var2.add(var4);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ytsip1ysh9ih","4RN5jNBlCgcSkappaeLn5X1aoMFbO+TdTgN7796Yr1A=",-5134087513712294222,-6488742477828665805,1078512611881522821,-5239009388291858272>()) {
                                             case 1175782130:
                                                break label30;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s377ezceeb6qqb","JaCtmVgH27D5MXCZwGX+5Ptz1liCfLL00alWQPkD78U=",3830386245775212462,886129155230738332,-5978573117549469658,5226188657983072474>()) {
                                    case 1044359046:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sb9dx2ew8wmfk","6p8+LSshdhhsx12m2vWVaC+HP0Xn/Yv5gbBnuxHRy3U=",4098995666353589214,3714553469481005938,3651458697097104760,2543252098837071367>()) {
                     case 1128242844:
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

   public s a(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1otyro3fgolzf","m1zIT66sh0m03icrq7jSXjI38YnvtswZyY2xclcv310=",6603586522228426254,4799693715323298571,171529145249407930,8029888121261306932>()) {
            case -1876610288:
               return null;
            default:
               throw null;
         }
      } else {
         s var2 = this.aa.get(var1);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ezcivadzg3nh","jVTXi3VWC05OfUT+r+A7J6lvKTd3n5/o/d3l3sslLGg=",3803297886743628156,-1719905709447158176,8378032602306861213,-8997637938567135387>()) {
               case 2020874993:
                  switch ((int)com.yiyiaddon.m.b.a<"s2begh1jbl9c0c","9PeiLfDnKrPDzIxCzJ0Ihmlq1mB0ZBKyuh1dCyvM1aA=",9009057200322220954,6819873297568454555,2021067534503902710,9068042363515100333>()) {
                     case 1689271332:
                        return var2;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            s var10000 = this.ac.get(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2gglnavg5sfvx","jXX93RjVPgt/Nu5P4WIlkwjqoLgk5UzaTPGGHECw11s=",3680960204716678135,5315251557264844103,-5971227043446012136,-8341152594490312516>()) {
               case 2112701637:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   public com.yiyiaddon.e.n.j.e b(String var1) {
      s var3 = this.a(var1);
      if (var3 instanceof b) {
         switch ((int)com.yiyiaddon.m.b.a<"s240gc5zvvqb1y","T1y3ZK6gfzapIIQ/4nOQhqjzFpgu3oqb3KZaO6iXLgw=",3607273778667308240,-6880091536100442983,4838127412433255809,1109168865340681040>()) {
            case 1882039412:
               b var2 = (b)var3;
               com.yiyiaddon.e.n.j.e var10000 = com.yiyiaddon.e.n.j.e.a(var2.bX());
               switch ((int)com.yiyiaddon.m.b.a<"s20q1bqwbca7b6","rm2U0TE7SVMLFDgg8TFZFOwNw/mWnkl1LgHcAbxU4VY=",436827444440446946,-927999618027566739,-273680959725325486,8539224908087577874>()) {
                  case -812638530:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.j.e var4 = com.yiyiaddon.e.n.j.e.NORMAL;
         switch ((int)com.yiyiaddon.m.b.a<"sm0suftu7u1tj","J+EcbkbkJhPWy4yMA2BhjaTNtMDFjBAP6fkOzaKoPt4=",-4016617009832599704,-5340060579332947100,8893571195129376563,-4556800540462999916>()) {
            case 1342736105:
               return var4;
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.e.n.j.e a(List<String> var1) {
      if (var1 != null) {
         label28:
         switch ((int)com.yiyiaddon.m.b.a<"s131bnu08vi3pb","x6+yuBCvnUuQ2q9efIM0qFFMhl/gWcKi/1e8SA3gUPk=",-6829878413439338610,3765686587138661105,-1794307611398746023,159566298766578515>()) {
            case -210294017:
               Iterator var2 = var1.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s25vs30ssxbg9c","8HngmUF+a9vkcuSaN7ImVbbAiK+ynI+NQTZkpp5gPqw=",7921624688115416287,-4358445732742592478,6421549710930948694,6839561872967428649>()) {
                  case 1246655230:
                     while (var2.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1ti5zmho4yj9j","sxmqd6ecLIJ6zPxldP41uQrTGtCockZ8Nxd0v4znfp0=",-3000375074195813676,-8819621598729709553,134538262528910459,8583534606472788204>()) {
                           case 136444310:
                              String var3 = (String)var2.next();
                              s var5 = this.a(var3);
                              if (var5 instanceof b) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1iy1vjeen0q47","UbM1HUDzIC7LrCjHdlhRHZJbMjY0wF2Uu+wxPNGw/es=",7610547956223908967,6460838674744765581,-6661621287417192137,-4880447683438367116>()) {
                                    case -1273223102:
                                       b var4 = (b)var5;
                                       return com.yiyiaddon.e.n.j.e.a(var4.bX());
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2lpfdmkhv349i","APHW3JfdOwmULFRbeFB+Hi+C6/UMXit3trRmEf4vtAU=",6196575764117963392,-5608722223565265577,-5521285606941988465,-2869398572430634349>()) {
                                 case 294251453:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break label28;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      return com.yiyiaddon.e.n.j.e.NORMAL;
   }

   public void b() {
      this.bN.clear();
      this.aa.clear();
      this.ac.clear();
      this.ab.clear();
   }

   public boolean a() {
      if (this.bN.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jfhcfrke9xnp","YTERb1sgZmesN2k7VHh0wfFGK3u9mZBGvtn0HJtL6Ng=",-1751079805957307249,1375646718298886844,8468082673033637605,-7024070833744770072>()) {
            case -1420403126:
               if (this.aa.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sdhgxr70czvqr","j9zsthhOJkoqmJaggu3q5jTFYEtXr+jguyv4vl9D2tQ=",-6200689545656961202,-5643066348220683734,1592707627819271923,-2702831776017793049>()) {
                     case -477196214:
                        switch ((int)com.yiyiaddon.m.b.a<"s2yukdq10tb74u","mMtWbkqohB2CeQ539bjbGrvGw4SGq9DkUqLPGGxDAOY=",-1880179705672403377,2881053556736589616,1678410296684705846,-6701397250155434662>()) {
                           case 1101821247:
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

      switch ((int)com.yiyiaddon.m.b.a<"s17yxzzp0g7gwh","wiBSRR+w5Irc5GAEK9CKaP6LPiBrW5p3Gn6WeufuBYc=",7574023639133069275,5474038246905421642,3785768652060178709,6203010450585665265>()) {
         case -941394685:
            return false;
         default:
            throw null;
      }
   }

   public int cd() {
      return this.bN.size();
   }

   private static final class a {
      final com.yiyiaddon.e.n.o.d b;
      final String sU;
      final int mA;
      String sh;
      String ss;
      String sk;
      String sl;
      String si;
      String sV;
      String sW;
      c c = c.CANDIDATE;
      String sj = (String)com.yiyiaddon.m.b.a<"s3mswvfet0y002","4QD47N+Xa2vkHoddfjksLyunUR1CZvbDDUeYM//VNurla9oOofU=",-1818013027833557604,568075366546132037,-1746731439210666590,-8687076892686125494>();

      a(com.yiyiaddon.e.n.o.d var1, String var2, int var3) {
         this.b = var1;
         this.sU = var2;
         this.mA = var3;
      }
   }
}
