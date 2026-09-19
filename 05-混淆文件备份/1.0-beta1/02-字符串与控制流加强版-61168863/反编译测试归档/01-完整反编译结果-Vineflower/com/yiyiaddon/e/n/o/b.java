package com.yiyiaddon.e.n.o;

import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.s;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class b {
   private final p e;
   private final b.a a;
   private final b.a b;
   private final b.a c;
   private final b.a d;
   private final b.a e;
   private final b.a f;
   private final b.a g;
   private String mS;

   public b(p var1, com.yiyiaddon.e.n.c.a var2) {
      this.e = var1;
      this.a = new b.a(com.yiyiaddon.e.n.o.d.CROP, var1, var2.by);
      this.b = new b.a(com.yiyiaddon.e.n.o.d.POT, var1, var2.bz, true);
      this.c = new b.a(com.yiyiaddon.e.n.o.d.FERTILIZER, var1, var2.bA);
      this.d = new b.a(com.yiyiaddon.e.n.o.d.POTION, var1, var2.bB);
      this.e = new b.a(com.yiyiaddon.e.n.o.d.WATERING_CAN, var1, var2.bC);
      this.f = new b.a(com.yiyiaddon.e.n.o.d.SPRINKLER, var1, var2.bD);
      this.g = new b.a(com.yiyiaddon.e.n.o.d.SHELTER, var1, var2.bE);
   }

   public b.a a() {
      return this.a;
   }

   public b.a b() {
      return this.b;
   }

   public b.a c() {
      return this.c;
   }

   public b.a d() {
      return this.d;
   }

   public b.a e() {
      return this.e;
   }

   public b.a f() {
      return this.f;
   }

   public b.a g() {
      return this.g;
   }

   public b.a a(d var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fdjuy3lune9i","cgYerjRtDUtMf3WdVQy/8e6FKuNhNWyAdZGiwzOBVMM=",-1122146197379049300,3472103627934433275,4418743801558867952,-4728658370672811953>()) {
            case 188622595:
               return this.a;
            default:
               throw null;
         }
      } else {
         switch (var1) {
            case CROP:
               b.a var7 = this.a;
               switch ((int)com.yiyiaddon.m.b.a<"s10wrnuhfvcjhd","kwZmjb9X+D9PUV/UT3mGfDAFZN1G+9fjOqSgAQnlKcE=",-4611619005025057043,-3069047984004778572,1591461744590324373,-773344896152885576>()) {
                  case 888579468:
                     return var7;
                  default:
                     throw null;
               }
            case POT:
               b.a var6 = this.b;
               switch ((int)com.yiyiaddon.m.b.a<"s2imij7bgph52c","9JnO3ZqZs0BQUWDepbbXosLZJot+/1je4oCa/QYX0bI=",5769422925229677750,284966023739210930,1192183157376241841,-7464727685349047949>()) {
                  case 68771283:
                     return var6;
                  default:
                     throw null;
               }
            case FERTILIZER:
               b.a var5 = this.c;
               switch ((int)com.yiyiaddon.m.b.a<"svqrrz1f67qkz","M5ZBFsTbtfjmngRtR0zoFEz5CB78pnfcbuCmbP5Nlk0=",5385185039713573941,-1026908855409158288,6723896586345518919,-261183498353734241>()) {
                  case -2070838291:
                     return var5;
                  default:
                     throw null;
               }
            case POTION:
               b.a var4 = this.d;
               switch ((int)com.yiyiaddon.m.b.a<"s63sx08xtavrq","klxuDQNCgbUgeO1FautLdy6bFAbiD3mgGzM8TjnxRV4=",-6933696417112856259,2613778003033928624,-3602614674250889055,3395230125354171338>()) {
                  case -1250246901:
                     return var4;
                  default:
                     throw null;
               }
            case WATERING_CAN:
               b.a var3 = this.e;
               switch ((int)com.yiyiaddon.m.b.a<"s1x6ngz67fq4mf","seu7vWEwNcOcJp/+BJ7pZ3+AgGYNCuroK0+jVG/NjGA=",-93924902066747887,-2357862579777804267,-3694537990849943805,4903108903602787978>()) {
                  case 1926764159:
                     return var3;
                  default:
                     throw null;
               }
            case SPRINKLER:
               b.a var2 = this.f;
               switch ((int)com.yiyiaddon.m.b.a<"scn53fqw2e8o2","WXKm0xtBlMVurB4oEg1S5bTl7zryRPmxqR7yEwlof1s=",1161090196646883286,-3424663718734412266,1969827190267590370,-5043930206987978915>()) {
                  case -1721294100:
                     return var2;
                  default:
                     throw null;
               }
            case SHELTER:
               b.a var10000 = this.g;
               switch ((int)com.yiyiaddon.m.b.a<"s30dmxa6nep5qh","mhP32Wmx+LiQ46LB75oL+ArTUSxvb7ymN/g0KRElFcY=",6562276513871351986,-3660690336617419801,7914674702277544068,4436165593033220057>()) {
                  case -1998894260:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }

   public void n(String var1, String var2) {
      this.mS = var1;
      this.a.k(var1, var2);
      this.b.k(var1, var2);
      this.c.k(var1, var2);
      this.d.k(var1, var2);
      this.e.k(var1, var2);
      this.f.k(var1, var2);
      this.g.k(var1, var2);
   }

   public boolean am(String var1) {
      return this.b.k(this.mS, var1);
   }

   public void gP() {
      if (this.e.a()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h1xf3ts3sbi9","pkaF1RpQ9vVQE6zFlWESoFV9wLQW1oAkZWcNtp9JdSw=",-6761189075295123696,2032157708896183936,-5743681164406760985,-4738363842365774140>()) {
            case 1533238543:
               return;
            default:
               throw null;
         }
      } else {
         this.a.cB();
         this.b.cB();
         this.c.cB();
         this.d.cB();
         this.e.cB();
         this.f.cB();
         this.g.cB();
         this.a.I();
         this.b.I();
         this.c.I();
         this.d.I();
         this.e.I();
         this.f.I();
         this.g.I();
      }
   }

   public static final class a {
      private final d c;
      private final p f;
      private final List<String> bS;
      private final boolean dN;
      private String uh;
      private String mV;
      private String ui;

      private a(d var1, p var2, List<String> var3) {
         this(var1, var2, var3, false);
      }

      private a(d var1, p var2, List<String> var3, boolean var4) {
         this.c = var1;
         this.f = var2;
         this.bS = var3;
         this.dN = var4;
      }

      private List<String> M() {
         return this.bS;
      }

      private void e(List<String> var1) {
         this.bS.clear();
         this.bS.addAll(var1);
      }

      private String aU(String var1) {
         if (this.dN) {
            switch ((int)com.yiyiaddon.m.b.a<"s3jit1d2xp4880","5lnT+loOwO2D/caFpgCKyl5s67SHX56Z+FFRyPLx0Jg=",1560910665933786824,-1853688802042377364,6176807475319074724,5196590635527810737>()) {
               case -1910074962:
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1kx1k3b103sfk","EfWJfAepXMCw+i5ZcZbtQuhoIsZlY8MUraSx7eiIPbM=",344672619684894732,-3122837616057405276,-7794096617037976242,5618528099975131306>()) {
                        case -2111505593:
                           if (!var1.isBlank()) {
                              return this.c.name() + var1;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3aisw0h33avh6","ivS0c/sqIonjDX+npyZgXl1Jei8MxlsAfzKMeuyOXME=",993817076764397948,1642548603469889145,6211513404374164296,3553186081898130968>()) {
                              case 2107898360:
                                 return this.c.name();
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

         return this.c.name();
      }

      private boolean k(String var1, String var2) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s23qo3am4du8o","6KAckMEd4avVjOGiN//vAsS/AJ9jWN0mHQGDnflX05Y=",6817594018562779,5727546921086746801,71144305742026241,1415155625314938055>()) {
               case -356782310:
                  if (!var1.isBlank()) {
                     String var10000;
                     if (this.dN) {
                        label63:
                        switch ((int)com.yiyiaddon.m.b.a<"sod654v3j9dq1","BO+tIGieioTbzz3uh+2c5MG/jr0RQ2WhtIJf0GuRNyM=",-6288761123856772805,781989553979455782,-504006548898298219,-803601258765459750>()) {
                           case -552831661:
                              var10000 = var2;
                              switch ((int)com.yiyiaddon.m.b.a<"s1b3ho8decl3ze","wBgTIJeo7TIoeERbMMTDv6Lim8wj71BOEWPBxHCwIW8=",2422446981362502904,5058385405879111871,-8618438618601869066,-9184250573213178233>()) {
                                 case 514693663:
                                    break label63;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = null;
                        switch ((int)com.yiyiaddon.m.b.a<"s1ehg1fenagi33","2UB+S1vkwLafcSDHLGX0gYDCx3BoC2a3JkONTJVNiYg=",884806249949978687,1159402859585790982,-9188288953721821404,144671222598873139>()) {
                           case -1941150335:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var3 = var10000;
                     String var4 = this.aU(var3);
                     if (var1.equals(this.uh)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s179rzt7bh65ec","nkf/ZtPKWSuYHhAFdtTx3PhkqN00xL5wijBMMDm+7R4=",-570588285156120317,8196725026894662517,6848593155820017242,-1050880185043893737>()) {
                           case 1404963131:
                              if (var4.equals(this.ui)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sih8aka665q4r","TRycXwj1SUKzjw0yKIJR3zc9eVHlCP/gpGslA6Gn1N0=",-8043109976846274273,407369502046954889,8514248845359179958,2569697062557713946>()) {
                                    case 1030727378:
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

                     this.I();
                     this.mV = var3;
                     this.uh = var1;
                     this.ui = var4;
                     Map var5 = com.yiyiaddon.e.n.o.c.d(var1);
                     List var6 = (List)var5.get(var4);
                     if (var6 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3brh1p8tm9iyb","V3Rt0eYtRfHjsusYCpE5ZK52YAkuQ4rnyFsDNV34jfc=",-8995130114630745544,-1334107838630130639,-917383918351871415,1994022287337159974>()) {
                           case 163139663:
                              if (!var4.equals(this.c.name())) {
                                 label52:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2huvn5uuu1lsu","C4zxVrqJ1920UTdkOfrr5/rj6C++641CqqGqv3W/xM0=",-3236398016621862609,1680656050513824709,-6613766174719765774,-5143912023966462341>()) {
                                    case 962242651:
                                       var6 = (List)var5.get(this.c.name());
                                       switch ((int)com.yiyiaddon.m.b.a<"sjyyph952x5qn","dUWpU56r2gsLZUVzYqzgqQ/jpQWcTJJelViFEhAupfM=",4244556124942408565,6333345114099865915,-4199659061509935219,2757916877794905322>()) {
                                          case 79862457:
                                             break label52;
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

                     List var10001;
                     if (var6 == null) {
                        label45:
                        switch ((int)com.yiyiaddon.m.b.a<"s28ytn3pz2ugsr","RDc5u/PhqSwMRLKqh9SNVeXVq8lopEVs076NyLCPZRI=",4369181031761012470,7907493418054165493,-3482866461973184215,7404972012322116401>()) {
                           case -997117627:
                              var10001 = List.of();
                              switch ((int)com.yiyiaddon.m.b.a<"s1rsgk0e52r3xz","Myljnc1WHrTdjZSLnWt38bIa4WQ7SL3QkrBkgZwhy6M=",-7271081693142872107,-224429860899447818,7238373386734672994,-7437239333846497925>()) {
                                 case 1550143854:
                                    break label45;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = var6;
                        switch ((int)com.yiyiaddon.m.b.a<"s26hn6br5tgq93","n3rGFuiTCvVElZd9OqPwRpDYoCiY5CQSoT9v7FrtNQA=",1864867314195253053,663289892229615217,2228916417848926205,4387178665825259383>()) {
                           case -95873875:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.e(var10001);
                     return true;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2yu3glmbjx8zb","Kr5WslAUZqVC/TcC9cW4RuyOrgOOnIABajFYI9y61yI=",-8597095666604537194,-1764048959185871613,-4728653009307480018,-3874535620954760318>()) {
                        case 151127578:
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

      public void I() {
         if (this.uh == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sfr73ezx6ct7d","rHXdZ+xiSSyyZyvcEbf2BCVEznbJ6xGzUw4zW+EFxq4=",-5730520009750522469,5621215591098219510,2232352403244762232,6543591686960069708>()) {
               case 1284816814:
                  return;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.n.o.c.a(this.uh, this.aU(this.mV), this.M());
         }
      }

      private int cB() {
         ArrayList var1 = new ArrayList<>(this.M());
         ArrayList var2 = new ArrayList();
         Iterator var3 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s19do24senpayz","oa7qnpNnolK3F3sB5skuxZd7CSAJ1Qcg61brv71jsQg=",-7689027751302233514,3533876535078352776,1770087834478864495,9047422039725853237>()) {
            case -1957523533:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sobh1w21hqlo8","iCpBSBaiMHcJGP9WlrWo9F7KtjPrCNIYzezsOjrLVqg=",-4908442946428430323,5190616371892298484,-5633666636160076815,6802315925198963917>()) {
                     case 745596429:
                        String var4 = (String)var3.next();
                        if (this.an(var4)) {
                           label33:
                           switch ((int)com.yiyiaddon.m.b.a<"s3nuiu2udbpld7","AHqTKhD5PBvUMMq3X8k5Yi2VVc0p7HtoGSjSRApKaqk=",2112529341162724162,7338329179420590071,3236838338645906881,-5706943058413497298>()) {
                              case -1115844704:
                                 var2.add(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kcbegwqv38s5","UUhx7jI93PCWqgGrknd1974WudUGZUdik5m1LaZmOzM=",5365420513197822251,-119656371798455799,347431885000072361,-7865491403970662855>()) {
                                    case -1884148607:
                                       break label33;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1i6kogvhwuttz","iaeVKVtdIsB7wga2TE56R6wxXj4vKVFq7y7y/q1bi+Q=",-3915617551393935580,4551328123020116966,-6835453291497433576,2256545911759467107>()) {
                           case -826963000:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               int var5 = var1.size() - var2.size();
               if (var5 > 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31lerwdxj2lb","VpZHFU87FXi7YqacYgVSUIqi8VTr5E5nLoWYt6uJP/A=",-2413244690229429742,-5341429552704638666,-3749692832053485116,255384508310965297>()) {
                     case 1286527120:
                        this.e(var2);
                        switch ((int)com.yiyiaddon.m.b.a<"sk7ct4qic391v","RBkRueK1Sv4Fsw+qlu4Np+BhneZj7gkuoPcU5c394uA=",-6252367127561870360,-1395862996094026560,2921971980249687336,968399738241813821>()) {
                           case -910584066:
                              return var5;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var5;
            default:
               throw null;
         }
      }

      private boolean an(String var1) {
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3d909pv10tqog","7t7E9Y3mIK+TTYBIfbJ/uD0DRbi2otqceWxqPNJ7wfU=",949646933971642941,-6093614728373154742,-5188397582107418492,2049435800041946715>()) {
               case -670641498:
                  return false;
               default:
                  throw null;
            }
         } else if (this.c == com.yiyiaddon.e.n.o.d.CROP) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ofs0cimdx18h","d7nz8G5k2p89FutcC5eqoMmPyOlojvMbxgy8Aiq1KVc=",5007150408962681928,-3311478086577234079,-3636737091876345570,4324193580151728623>()) {
               case -1353215802:
                  if (this.f.a(var1) != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s11q8movdec4jw","3mO7/b96sPR4I4X56cn44/DRQRKAWQQa6pONlD8eXxc=",7379565785926452835,6607382285239969597,6626552759053199028,7952979884853782766>()) {
                        case -1026605516:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ox2hipafh7jv","Rc4l2/to+PB9PruNyFH3bp0StXB+nIasnQeXWvsP1Zk=",-8309389789836454462,7510335367644357293,4045191685219683273,1158649669039714366>()) {
                              case -949374653:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"swv5o2myek2nq","/ajvi4VmrmPJDL+wJ5YGz+8QyQKgCZnBMqk3ZqMbzAY=",-1144480775056346902,8935341294357927161,984448675473912136,6300855501563983143>()) {
                        case 31972245:
                           return false;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            s var2 = this.f.a(var1);
            if (var2 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3qwl7enb8xbdc","z8H8KJjNERJoi12TRbx7JXJdDD0kYrYHWQAxHXtFCpw=",-908381938102246354,380709432291393808,-3739687942525160172,306964821983528479>()) {
                  case 496508955:
                     if (var2.a() == this.c) {
                        switch ((int)com.yiyiaddon.m.b.a<"snkf93eauy8e6","4pV+wFhi3mf4q1dWyiOiodpG61L5/il1nBRyvLtjNuE=",-8638012765759574866,-4375639507437148307,178685991850713994,7979578460182909220>()) {
                           case -1246453906:
                              switch ((int)com.yiyiaddon.m.b.a<"s2viqe3q3oirfq","k7raiT87E6rbbFEOQcQQLLVwEdq9T+v1h4WyF43hLUI=",-8297347709712231487,6699447699177577077,3588617646236825042,6697800408419578461>()) {
                                 case -1822835683:
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

            switch ((int)com.yiyiaddon.m.b.a<"sag9razib72sc","RGERn0BssCr6eIlScvjyAYaIBRF8uaAfEctNi4WBSNI=",-718837597884197097,3572797812343987540,134799733426247535,-1121652604084744208>()) {
               case -1298615629:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public List<String> bc() {
         return List.copyOf(this.M());
      }

      public List<String> aG() {
         ArrayList var1 = new ArrayList();
         Iterator var2 = this.M().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2bnhevyp13209","N+smc8yK/rmwLCDsWcnT4UddBhKqGKSFKfQDMcCZvlc=",7487234120209803345,-3017964495887748066,-909790458014896458,-1934683650761430076>()) {
            case 316090295:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1zx6hf4e1a0hm","JJcxinAdmy7cyGnzaqh93uFbBSJJMYg9iR8E604AQgk=",-8129385697225938248,6405681692664692239,2678456637853817191,-5185402244966288414>()) {
                     case -1898042618:
                        String var3 = (String)var2.next();
                        if (this.f.a(var3) != null) {
                           label23:
                           switch ((int)com.yiyiaddon.m.b.a<"s34esu02znj38m","dxh/ZqCTKlgozyr4uq9954m1n/0VbQgNnCbvtmU9JoU=",4421031446895410850,1835337102057667451,7378126099481734323,8693163447834524636>()) {
                              case 832005752:
                                 var1.add(var3);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2m5wd5n4x3p76","9uDsIq5gu3FmH5AMirrqgkXitwQlKhwCtwAT9gVB/M8=",7981364382914862230,3095904343847868431,6975690966584140316,7101859217183266059>()) {
                                    case 1570451405:
                                       break label23;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"ssya3n7m2w867","XV3rjBb4+MK/xkJIPvETJoUAW80SL798SkibJ/aijz8=",-6197130051797485025,8146594300979642283,8813134631418840128,-7757543284691602165>()) {
                           case -484599170:
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
}
