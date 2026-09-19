package com.yiyiaddon.e.o;

import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.o.c.f;
import com.yiyiaddon.l.f.i;
import java.awt.Desktop;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket.Action;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;

public final class c extends com.yiyiaddon.d.b.a {
   public static final String vS = "serverdetect";
   public static final String vT = "服务器检测";
   private static final int oA = 64;
   private static final long ai = 100L;
   private static final long aj = 5000L;
   private static final int oB = 3;
   private static final int oC = 3;
   private static final int oD = 3;
   private static final long ak = 1000L;
   private static final String vU = (String)com.yiyiaddon.m.b.a<"s3spyfz62sqtw3","NxVpUW+pPjnFg05wrAuS4TGm4JMzRH88dDrlEU814ZY96Q==",-6819353367432574501,-916783808145267767,8587337193097720434,-7053112392172806576>();
   private static final String vV = (String)com.yiyiaddon.m.b.a<"s2qnin4mrnzni6","/VTwzBlNnuy9S12gZSTBS7Zh3Jp4MbVw20g7DWRNRYw=",-4988727056449292435,-863982423561413764,-1139147327534276524,-733385614384714671>();
   private static final String vW = (String)com.yiyiaddon.m.b.a<"s2yy5gq6wux1cg","Hl9lihibffyGDPVcERFGvxdrXB34Fkuz1GlxGJduP2MMXA==",8782015747074890794,-3568707177845304284,-5675948909167915815,-5324838301066979488>();
   private final Minecraft ai = Minecraft.getInstance();
   private final com.yiyiaddon.e.o.a.c a = new com.yiyiaddon.e.o.a.c();
   private final Set<String> am = ConcurrentHashMap.newKeySet();
   private long al = -1L;
   private long am = Long.MAX_VALUE;
   private int oE;
   private String vX;
   private String vY;
   private final com.yiyiaddon.e.o.b.c.a a = new com.yiyiaddon.e.o.b.c.a() {
      @Override
      public void hS() {
         if (c.this.g()) {
            switch ((int)com.yiyiaddon.m.b.a<"sib42rox3v7s8","QPuD/5LfcdYzyOYJmAA2Lpd868urQsgWhxao+rMD4II=",-2457555995550606513,3377971618885831433,-5177743883482414160,-4411969289609236715>()) {
               case 732994220:
                  if (com.yiyiaddon.e.o.b.c.cU() >= 3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2oihqab48d5i5","n0PVBGVCSkiGnLQrTAeigFmfsV10Of49V47jgXrp2fI=",864264168832566462,492957057449928823,1342458347560592158,-6426111959127127109>()) {
                        case 123238420:
                           c.this.a(0L, 1);
                           switch ((int)com.yiyiaddon.m.b.a<"st89h2mh1vnij","tdlSt/E2YfNnz3fDdV1svn3UJyOHuufq2CMNWdJWdlY=",4251993803709640593,-1721485442664186678,-8899389488503574788,3964603418031934846>()) {
                              case 971528137:
                                 return;
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
      }
   };
   private static final String vZ = (String)com.yiyiaddon.m.b.a<"s32vc5ngneo9yd","at2PBgMInAK8GbtN/Rb9jBMYOHf8dUYjmw4P6wzo",-2409512092424884421,-1345756301888485158,-116285476880243524,7092732424153887385>();

   public c() {
      super(
         (String)com.yiyiaddon.m.b.a<"s3h6t5sldb3gwm","6P0tUCgX4AqEYB80/uqjQB4zeptabR5DuXyOIHmYhnvIiKIygU7wK6I4fUGoxMwG6juksA==",-2446075697357761416,-3030069300897817867,3942850081727537515,7770082303257679336>(),
         (String)com.yiyiaddon.m.b.a<"s3bp75h9u9zcie","y6RyakSsQJs1amcIA1coDfA326pgtMhIMDA1c5pry8DuCw/kEog=",1829637047924055550,5556079786283873998,4234653470670100962,597569534513159555>(),
         (String)com.yiyiaddon.m.b.a<"s2an33cgrrzsqi","mcNpA4mb83BemJjlCp0OR3asfBYtclSZVtkAbEHKbSAZygcRV+AzRP8U",5766740316293766732,6937860197172821025,-4995319778209771845,-3138480091225273178>(),
         (String)com.yiyiaddon.m.b.a<"s3vfla7ka5sbt9","FUCQ+qyt8QjR3i9LdgPkRMGS5+tk3UHV/61sZFSxuCeWxAmy1siTiGe7USKcru0WBLF4/p2MVIfut9f6ehwwRJoQPPDxZA==",-5163509117447034984,5996897489069217429,-7777548166294273013,701178071946546751>()
      );
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s32vc5ngneo9yd","at2PBgMInAK8GbtN/Rb9jBMYOHf8dUYjmw4P6wzo",-2409512092424884421,-1345756301888485158,-116285476880243524,7092732424153887385>();
   }

   @Override
   public int i() {
      return 20;
   }

   @Override
   public boolean h() {
      return true;
   }

   public com.yiyiaddon.e.o.a.c a() {
      return this.a;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   @Override
   protected void m() {
      com.yiyiaddon.e.o.b.c.a(this.a);
      if (this.ai.hasSingleplayerServer()) {
         switch ((int)com.yiyiaddon.m.b.a<"s22giblu5s70zt","y7/xWQNHZrQNzudsCjlN8dBWFEjlr9DgizUliylKd78=",8736472282931666028,-6071070948603222997,-5458925986996046764,8534304755510598561>()) {
            case -2134917866:
               e.b(
                  (String)com.yiyiaddon.m.b.a<"s3h6t5sldb3gwm","6P0tUCgX4AqEYB80/uqjQB4zeptabR5DuXyOIHmYhnvIiKIygU7wK6I4fUGoxMwG6juksA==",-2446075697357761416,-3030069300897817867,3942850081727537515,7770082303257679336>(),
                  false
               );
               this.ad(
                  (String)com.yiyiaddon.m.b.a<"sbn2erg2rk3tf","LXDEyqz0F3u2oS2KpEc3rsw7Wx2b9O1eRPdVw/gWn8bn/V4awBok5mRtoVbYDNKG",7859675430276445351,-8971567134437451604,-3214297078731810502,8901607844035781497>()
               );
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.d.a.a(
            (String)com.yiyiaddon.m.b.a<"s3h6t5sldb3gwm","6P0tUCgX4AqEYB80/uqjQB4zeptabR5DuXyOIHmYhnvIiKIygU7wK6I4fUGoxMwG6juksA==",-2446075697357761416,-3030069300897817867,3942850081727537515,7770082303257679336>(),
            this::a
         );
         com.yiyiaddon.k.e.e.b();
         if (this.ai.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"su4dtowbne69y","FV2QyV3hfskpzHDWZY/z7eOSn/W/J2UbWbdyMlswv4g=",-1248536187319671510,1721467247726990918,7875409102968778521,3688288554853475843>()) {
               case -427412063:
                  if (this.ai.level != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s175y1yidrf791","H4RHHzJS/LOdkVuPlVf8iMkqmcUkCN6Z/f+lQLe7kQY=",-6758306315614856385,3800687028891869049,7933617099220961745,-772124538809423593>()) {
                        case -2091337706:
                           if (!this.ai.hasSingleplayerServer()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3gaww0dbvocr2","dQs6Y4TXEL2uVSFss+ertn5Uq/Xf1Z/5Iuxj4d/F71U=",1521743893836128150,6293349326748358292,-5670947418344848875,-4339287726173854129>()) {
                                 case -1285655888:
                                    this.am.clear();
                                    this.a(this.a.pp * 1000L, 3);
                                    this.u(this.a.pp + "");
                                    switch ((int)com.yiyiaddon.m.b.a<"sxbpy5nui6cch","+3oezqRoQ4ogbA+/DMtSYGhVkUsGNz/8/tgsWNRx7zs=",7380371792993397391,-2055736543916855838,3540501800046167337,3959717253837057513>()) {
                                       case 1102951612:
                                          return;
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
      }
   }

   @Override
   protected void n() {
      com.yiyiaddon.e.o.b.c.b(this.a);
      com.yiyiaddon.d.d.a.l(
         (String)com.yiyiaddon.m.b.a<"s3h6t5sldb3gwm","6P0tUCgX4AqEYB80/uqjQB4zeptabR5DuXyOIHmYhnvIiKIygU7wK6I4fUGoxMwG6juksA==",-2446075697357761416,-3030069300897817867,3942850081727537515,7770082303257679336>()
      );
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.JOIN_SERVER, com.yiyiaddon.d.a.c.DISCONNECT, com.yiyiaddon.d.a.c.PACKET_RECEIVE, com.yiyiaddon.d.a.c.SERVER_CHANNEL);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snhyyc3vho7pr","Wp9LHakp3ZOO9MU9NDYRiCKgB90hu0Ri610mO/8ukbY=",8735900246250433954,-2221433309520169695,4977002521399610672,-1004129784600526826>()) {
            case -1154155550:
               if (this.g()) {
                  switch (var1.a()) {
                     case JOIN_SERVER:
                        this.aF();
                        switch ((int)com.yiyiaddon.m.b.a<"skzzp5m3pmaa3","zsGYdTnAP7g5kPFzGi0qwZBMyq+B8NxkrPiBo1p8/TA=",-4491458635336557648,-4584553611040682947,6364702115302610291,6840785168404036237>()) {
                           case -2005372750:
                              return;
                           default:
                              throw null;
                        }
                     case DISCONNECT:
                        this.fP();
                        switch ((int)com.yiyiaddon.m.b.a<"s3mggrwb5i5r8x","glCrvELABIVNZL4+38wC8CqBPUWl9qLgsatc0Y9jrcQ=",616148183168720095,7746699745737833165,5366866322214139536,4268734336757425603>()) {
                           case -480013931:
                              return;
                           default:
                              throw null;
                        }
                     case SERVER_CHANNEL:
                        this.aI(var1.l());
                        switch ((int)com.yiyiaddon.m.b.a<"s3dbreu286rob1","rW9UGusAtrAIA/cc/Rq45ESg4INjhD63VT30c8RHaHI=",-7258758237203411578,-801579513938218621,-503718444159220367,-5846980906620669822>()) {
                           case -1524019212:
                              return;
                           default:
                              throw null;
                        }
                     case PACKET_RECEIVE:
                        this.aJ(var1.l());
                        switch ((int)com.yiyiaddon.m.b.a<"sxlacn2n6m1rp","EoKXY7c0F+dqgUGdsWohoFO9kjIfyy1I93L8lQ5lqNE=",-596215859605376525,5797703172879293853,-3812795646097280464,-1264200356419792717>()) {
                           case -60599539:
                              break;
                           default:
                              throw null;
                        }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s27xzzat0g0etc","DZEwFUnlT8G2eetZPVSvkeKmWx91PgMi+TFMBCoEqzw=",-6842278949406755201,8150503178622920934,-6436388304433503473,-5760347834869523859>()) {
                     case 4845389:
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

   @Override
   public void b(Minecraft var1) {
      this.id();
   }

   private void aF() {
      this.am.clear();
      this.vX = null;
      this.vY = null;
      this.ai.execute(() -> this.a(this.a.pp * 1000L, 3));
   }

   private void fP() {
      this.am.clear();
      this.al = -1L;
      this.am = Long.MAX_VALUE;
      this.oE = 0;
      this.vX = null;
      this.vY = null;
   }

   private void aI(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1h96tflhnwa5t","wCCBrVwrjzqz/WyT+4s5Y7HwxkAPKVIhIDrP3cRSUzE=",7864153342953612664,7159917470907906538,5753856276982110043,439166017385900690>()) {
            case -527190897:
               if (!var1.isEmpty()) {
                  if (!(String)com.yiyiaddon.m.b.a<"s1wf5swngp08sf","6qSglHa+jPKTV5ZnPolbLRSIQEP4uhGxNg4xpTdK7Nt2IadjJ/BmQba5G8cqyem/7xnBFhTkbVC9XA==",1008740360871798851,6974278796252872388,8189281683089374764,-6940959125624215307>()
                     .equals(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sz1p5y6m2wk0x","m7Es3I1vW2f+CmgHRPeISVfvd4f6T1NzlFic8O3F+YU=",-6438277927103602662,8484932438246537311,-4148501840645208045,-1897202956275761574>()) {
                        case -1737436875:
                           if (!(String)com.yiyiaddon.m.b.a<"s16ux0cfhy149m","FHxNQBKnAu6s2xkDMz29yHV7TIjEAVnmsm7yQTWfJuPGqIs7KyGKw6TqQ9NdAQkrlddzyEaGhGZhvWRNO7ZNcw==",-903815148178679144,7913781254681164445,-9086493853910769467,-6100457099598611935>()
                              .equals(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3nuf9alznmcy1","s2tO3/vrSGT3MJD/4WiCgCymz+6u0VKTOZuIUmLZLfM=",9054165274003347438,-8484097562302119320,3494679352650394030,-493913111424254989>()) {
                                 case 1274876205:
                                    if (!(String)com.yiyiaddon.m.b.a<"s3yel4hgcor7g","DQ4D0bfmeHizCMlBKFTx9k+saORG0JgsFHPkmJog1MmE1gihYx9Cjn/BUcBMPBa6PCekeSwTKZyUbqjnUzOePp5C+Mc=",-5431556701774769274,3304104201171204584,-8538003858814137296,5120607818928409633>()
                                       .equals(var1)) {
                                       if (this.am.size() < 64) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sgm9g9vrffx5r","5Iz3Ak0SyQB7q4ZNVaQl+OxV6BwDlrCusifLYK0Cpz4=",1123242074833071561,352974644039017735,1346700061679831340,9038257933422699100>()) {
                                             case 1727425586:
                                                if (this.am.add(var1)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3b56fsg88mkbo","w4D5+snxLHo2pqzpEnExk6KoTmv8tydzRWQqLDWbbr8=",-7763795636921244184,4640239551159412975,4671787322742551182,-379941026145139797>()) {
                                                      case -1361211969:
                                                         this.ai.execute(() -> this.a(100L, 1));
                                                         switch ((int)com.yiyiaddon.m.b.a<"s311lsakk9ltg4","mOLgQWrIgAou7rXmG9VdnT0LkGxKVRFMtbtYj7cE0fM=",-4611693079552691816,5419757782074752226,2876658740650629095,4827608059576831779>()) {
                                                            case -399225421:
                                                               return;
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

                                       return;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3bwnmsrncyldx","SEkstQMf1UhbufHQRbdPT/y/dq6oOYMtm4H1IJ7qJzU=",2966301968640669733,-337122666319567452,6637769553772869149,2908284670366641616>()) {
                                       case -479962685:
                                          return;
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

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dtzgz7xdc5i5","mgLr0yylr9t+qCcBvXDYfCWpOd31jsfBR0TX7wSKfXE=",5960073764617241778,-6006864813515428328,-666334054293849149,8957974599030571354>()) {
                     case -2117451012:
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

   private void aJ(String var1) {
      if (!ClientboundCommandsPacket.class.getName().equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s20hyx8ls7juuv","1PhLcs44X6XbYsPJVLuErh9LW74UGB02+Is+g4wR8zU=",1243703454470286926,-8754118142508178014,1176922388850085829,2963561519922402788>()) {
            case -1441912766:
               return;
            default:
               throw null;
         }
      } else {
         this.ai.execute(() -> this.a(100L, 1));
      }
   }

   private void a(long var1) {
      if (this.ai.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"savoo7cnvzd49","WF5C6qs0jO5oL6bojjWJdtvcQrYid3Abzo6pi6Tvddc=",5306592220526328950,3448265960875789799,-1956443294192362044,-1358992353242468427>()) {
            case 429540132:
               if (this.ai.getConnection() != null) {
                  String var10000;
                  if (this.a.eH) {
                     label68:
                     switch ((int)com.yiyiaddon.m.b.a<"scbs5lcsczkob","zCWVe7qTcV2/XZjVsJJb0Y25rfvT0COlVQ295KFC2y8=",5841123617755065659,3940497161984446584,1612064344784866630,6367103824610240040>()) {
                        case -500657689:
                           var10000 = this.eR();
                           switch ((int)com.yiyiaddon.m.b.a<"s1894wtmfwu0ff","fnhSrNdv8w334rcsCad13Y3rZWuTzjS+ocrxpoFoS6w=",1982479273724119890,6111936294598802078,1318279045514465029,2546648697600499314>()) {
                              case 1231621988:
                                 break label68;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = (String)com.yiyiaddon.m.b.a<"s3spyfz62sqtw3","NxVpUW+pPjnFg05wrAuS4TGm4JMzRH88dDrlEU814ZY96Q==",-6819353367432574501,-916783808145267767,8587337193097720434,-7053112392172806576>();
                     switch ((int)com.yiyiaddon.m.b.a<"s3jgya96l9y8s3","qmB8mNdWqDVb54dyhPAoO7YC+F8R1MxiwIuhf1b0/Mw=",-5913569320783821189,-908127724039213031,3898097357169459184,-969882919335014704>()) {
                        case -702829207:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var3 = var10000;
                  if (this.a.eI) {
                     label62:
                     switch ((int)com.yiyiaddon.m.b.a<"s2ho698lek6cq2","U+hlD3YDI05ZoKyP8LyamqPG3yrkP6DS8FFGVKCRPVg=",-8350491489469393783,-8801051408534409929,6943421988821712052,-7203037138427558803>()) {
                        case -2026909660:
                           var10000 = this.eS();
                           switch ((int)com.yiyiaddon.m.b.a<"sbz8fjfy8feo8","UwCmyBYAb6SdsjfmPs/NOIiUSUnRci2akJwmDw9MzJ8=",-7250066440284795109,-3934169834758018706,7747481701972595120,8291109702529337185>()) {
                              case 765074783:
                                 break label62;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = (String)com.yiyiaddon.m.b.a<"s3spyfz62sqtw3","NxVpUW+pPjnFg05wrAuS4TGm4JMzRH88dDrlEU814ZY96Q==",-6819353367432574501,-916783808145267767,8587337193097720434,-7053112392172806576>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2t3ken67cwwxv","GvPTKzODDc7wf9ZuI6mCyBRHiRv36Y1k+q574uhuYpE=",1352523858574254967,289203861484573485,8375409656772969496,-8464405944908950353>()) {
                        case -1727337518:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var4;
                  label82: {
                     var4 = var10000;
                     com.yiyiaddon.e.o.b.c.a(var1, var3, var4);
                     if (Objects.equals(var3, this.vX)) {
                        label58:
                        switch ((int)com.yiyiaddon.m.b.a<"s5ulr2kphxtsd","ctJPw81+7ZamfaYjaSb9QTfa4VktlwVobc3rtunoDBU=",4881306739085737319,5033283304162370345,6415469113596492658,2744643442973704916>()) {
                           case -1909097577:
                              if (Objects.equals(var4, this.vY)) {
                                 var7 = false;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1svmqm42ztw7m","qJ505wNZ2Z78cNXQGuUyqzfqD+Q2pBT7QD8N2O3KiKE=",5486140196906201353,-4093590565026513434,-579380995622826151,2874816863406869444>()) {
                                    case 1383874805:
                                       break label82;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1gd3dsxu3e0me","Pw5KRrfpNHzrRbF/zROTz2JrXC8mjlECdrupbZIw8fY=",6632607080155318678,6409448823117201064,2020598446704572545,8869372644388875052>()) {
                                 case 1819883403:
                                    break label58;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var7 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s1c9r89ch54tuu","SCp3KGGzV455xfqGBaAl6Xlezh8j0kHjYomA6Evjr/4=",-6803844645867060965,2014581718108645017,932504793761059539,4161978054188750592>()) {
                        case 1740952034:
                           break;
                        default:
                           throw null;
                     }
                  }

                  boolean var5 = var7;
                  if (this.a.eJ) {
                     switch ((int)com.yiyiaddon.m.b.a<"s48vdpki3mrz4","tu4CaDuPibj9uUHN1SIsu5HV1mXkLtIh/gorHfBUQzc=",1729885117716949603,-4024514398031609684,2381416038159307834,801519837375800487>()) {
                        case -2012943049:
                           if (var5) {
                              label47:
                              switch ((int)com.yiyiaddon.m.b.a<"s2u8rtht07gk99","b0tmhVSmd9aArKRVVIFbB5tbvM2rWmOjIfZ+0tFgsuY=",-1864489495710295100,5289855079035032350,-1386996229151548974,5539765831635665478>()) {
                                 case 1029502073:
                                    this.u(this.r(var3, var4));
                                    switch ((int)com.yiyiaddon.m.b.a<"sxmxxrxlzcfbi","BZxL6cSpvmqv3+FrLJ7nwhrF0dNKc5hPIMLtzXULDqw=",-5641433184132499500,7054725249388099544,-2242587171630445827,4416440933772167772>()) {
                                       case 874537397:
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

                  this.vX = var3;
                  this.vY = var4;
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"suypq2blxt9tk","0gBpRpmq4oMe79O1RO9MPYrRC2o8/Z/EeVuR8cXgal0=",8431684939822937256,-7560833941097933324,-3658220988266686133,4241419691042568554>()) {
                     case -1867958427:
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

   private void a(long var1, int var3) {
      this.al = com.yiyiaddon.e.o.b.c.r();
      this.am = Math.min(this.am, System.currentTimeMillis() + Math.max(0L, var1));
      this.oE = Math.max(this.oE, var3);
   }

   private void id() {
      if (this.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nrtni8no5w8h","Tr20R4FCa70xOdJmttWZ1DO3bPFbeqea3GK+6K3IMtc=",7210499442411549984,-6843060495288565456,-4413357539289517720,-8540525639986137734>()) {
            case -1057307702:
               if (this.oE > 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3nzbj5kc1ffpg","rO1nPuGktjhd/Ff+c7HCnALi759GPNoyOmsS6YgFbWM=",-6397032326670522805,-5654562434390816989,-4003410735117394542,-9082694740319519938>()) {
                     case -738158685:
                        if (this.ai.player != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s8n01u0it9j1i","s3DtZqXlbOvxDeXiuz6FcC3XAq2J7RvW7qRByIjBrBM=",4941045395439508642,5342638747253575245,-971405755743723359,7702381351270487242>()) {
                              case -424068912:
                                 if (this.ai.getConnection() != null) {
                                    if (this.al != com.yiyiaddon.e.o.b.c.r()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2emqngkyrlxwg","GIz1V0y2I1E084Nv71ktBOT6M08nhEjP9ZzvclrIywI=",8068706757239662895,-9015250211967934982,-5031823074625139322,-773859289688181774>()) {
                                          case -2019471890:
                                             this.oE = 0;
                                             this.am = Long.MAX_VALUE;
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    long var1 = System.currentTimeMillis();
                                    if (var1 < this.am) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s27ai0j1pmpcrl","p5l8s/VK1Z4NV9edVXYBWBfssm3TcBX1Akb4VtZvAmw=",-8164616250196487996,4313038458161309486,-6048098209305314692,1188516423338880490>()) {
                                          case -86846881:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.a(this.al);
                                    this.oE--;
                                    long var10001;
                                    if (this.oE > 0) {
                                       label38:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1g3yhqpme3lry","b4qaP4VVA9ynoQesDLLXgPgVF16jt15TOqQmk0kT2Vw=",-5871732867046637251,4534369167744747390,-667768358696159083,-3716505196876900187>()) {
                                          case -1869181257:
                                             var10001 = var1 + 5000L;
                                             switch ((int)com.yiyiaddon.m.b.a<"sqrbx3gfhezvj","2E8fD6fJBMsgpOWKDPQaP65Q67ZTbyl3Fd6A6nLSr4I=",8683769397072016068,-178101027242776713,5742765567456245830,2451837383828578007>()) {
                                                case -1929788672:
                                                   break label38;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10001 = Long.MAX_VALUE;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1iwoe62l2gdgd","Y+LIoB8YIBm8dXeBWTVJ5HAG2tPbzrRhL/HgRpXP+wY=",-7519797416931545498,1278676447009062752,-7040835439202275500,6185144086339116343>()) {
                                          case 808645046:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.am = var10001;
                                    return;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"sfv4q7h093773","QiTfiPuDmipvPOy0ow50Xd/xPJ0M6AhRqapgsD1lrSk=",7342642756256667828,-7609739657434725910,-494144706921757304,3563265004532175645>()) {
                                    case 823267081:
                                       return;
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
   }

   private String r(String var1, String var2) {
      StringBuilder var3;
      boolean var10000;
      label90: {
         var3 = new StringBuilder();
         var3.append(
            (String)com.yiyiaddon.m.b.a<"s3og0q9z33o35i","lt4yWK6RW8JehgLuJ3qP8O4LNCOVOAihfRAw8lFB/P8x0vq/B1A6FyeBikZMMPnHxWODnr8g+VjR85BPSYWso93ZqaA=",4357767185152130299,123088771091265743,-464474657469305592,-8058806757181870707>()
         );
         if (!(String)com.yiyiaddon.m.b.a<"s2qnin4mrnzni6","/VTwzBlNnuy9S12gZSTBS7Zh3Jp4MbVw20g7DWRNRYw=",-4988727056449292435,-863982423561413764,-1139147327534276524,-733385614384714671>()
            .equals(var1)) {
            label83:
            switch ((int)com.yiyiaddon.m.b.a<"s237irt37w9uuv","RwhY/+bYmdtXh5MEfaRPbzxf+di4TLU+gfa4SJmI93w=",6200213375936750835,-65182859759033601,-1764782084078274959,4842176231614212808>()) {
               case 1565234068:
                  if (!(String)com.yiyiaddon.m.b.a<"s3spyfz62sqtw3","NxVpUW+pPjnFg05wrAuS4TGm4JMzRH88dDrlEU814ZY96Q==",-6819353367432574501,-916783808145267767,8587337193097720434,-7053112392172806576>()
                     .equals(var1)) {
                     var10000 = false;
                     switch ((int)com.yiyiaddon.m.b.a<"s2wdbf9964b00w","4j9PVuJNEfqfbozW/aaACH1jLkz9QMPyAABGD7Exm3U=",7700983093916657182,8894561762495949521,3227456179147710003,-43686446387410785>()) {
                        case -1004481372:
                           break label90;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sq61rnyaelly6","9LUX9XNwDumolSH8Kf4d3ICk+qkHaZWZ6dJbA9tnzag=",-5689900099026392597,-7862608469827245371,1513228319023392569,-2598784002996295984>()) {
                     case -1744426925:
                        break label83;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = true;
         switch ((int)com.yiyiaddon.m.b.a<"s20ddlb9lb8gt0","uTV4XpWCQ6KpeBuPsGktiS6YmjExo5t63689vLka6dw=",3482973703706019264,5364360396675162083,7429987187935673727,-3862011022932323781>()) {
            case -1812509908:
               break;
            default:
               throw null;
         }
      }

      boolean var4 = var10000;
      StringBuilder var5 = var3.append(
         (String)com.yiyiaddon.m.b.a<"s26eeeeuxud4oq","7Cp5yESJZn0dQOCHQjGM6LGt5W0bMVdiaf62gWBqrnIpUeOlo73F6ZSOlhqcHHIe/NLAmw==",-5323486020010668937,-5919070868453917911,-7489510695657325288,341324304074997483>()
      );
      String var10001;
      if (var4) {
         label72:
         switch ((int)com.yiyiaddon.m.b.a<"s3h8jckxjwsl2f","//1vIfEllIeBh19iQot6bOa9l2Dbx19CbyxjXwRsqXU=",8833545406922429323,3087315092575686346,-4758285024130929014,-7536655259705542973>()) {
            case 2002092509:
               var10001 = var1 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s2ok8oyx9p8xna","tT0N+eYGSOUJDNspH8jt4fpVfUkPEXZONwJYFmiNNCI=",-941444417231170989,7451381476131899936,-7110859873733866359,-5504245211259516854>()) {
                  case -26672968:
                     break label72;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = bk(var1);
         switch ((int)com.yiyiaddon.m.b.a<"saiy5iowfej9n","n20syjuvkplAmMOxSbFef9Q2ZuhJje/JrSSvHsHLnMg=",8410475756902539567,7222988972627125326,3410450833182027571,2396176666046992365>()) {
            case 615321273:
               break;
            default:
               throw null;
         }
      }

      var5.append(var10001)
         .append(
            (String)com.yiyiaddon.m.b.a<"sckodgqy5ipxy","Sht4SBv5zVmeTJXjRw1bTqvIH2+IbbXee0TE3583",6403084848218470340,6459492526778796746,3121571645626761369,6363603186690771754>()
         );
      if ((String)com.yiyiaddon.m.b.a<"s3spyfz62sqtw3","NxVpUW+pPjnFg05wrAuS4TGm4JMzRH88dDrlEU814ZY96Q==",-6819353367432574501,-916783808145267767,8587337193097720434,-7053112392172806576>()
         .equals(var2)) {
         label61:
         switch ((int)com.yiyiaddon.m.b.a<"s61k9l48zsin8","X7NTeSp2j2owKrsdd0aNsK/DsBYt4Pm5serukYB8H5g=",7498353155107606757,198689079930598995,7958785298701951380,-7406020037064135394>()) {
            case 1354607455:
               var3.append(
                  (String)com.yiyiaddon.m.b.a<"sswl2ksxn650h","adP0rYo9TnOBVmdNZkGeMCWi1NX3mxlfGYsxbGGtRBd56p8K3359cjrTQ8GVIchcDiynHM8Rg6HfxPlVnTk=",1889920167642152347,-7700627017336844117,-7173014565353262187,-8948690693726230519>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"skvbh575pwv3v","PNjUYq9HCdg4vhUTCFKSbuyeQY40kALD7mCnlvUZvMU=",-5115919766580381614,421895652885975711,7717301294801643719,7235756589922201810>()) {
                  case -1359610054:
                     break label61;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if ((String)com.yiyiaddon.m.b.a<"s2yy5gq6wux1cg","Hl9lihibffyGDPVcERFGvxdrXB34Fkuz1GlxGJduP2MMXA==",8782015747074890794,-3568707177845304284,-5675948909167915815,-5324838301066979488>()
         .equals(var2)) {
         label64:
         switch ((int)com.yiyiaddon.m.b.a<"s34q5be0k1meiq","aD5Ytj0SIBHzRfjAFI2gcn286axOWB68UTI/y9k7tyE=",-6021476743961444515,6645185210923528787,4865599912901698940,-8958624296688796116>()) {
            case -1507291004:
               var3.append(
                     (String)com.yiyiaddon.m.b.a<"s2zdu04ctl116c","nQRbLzEtHiRQh6wuqlgQM90k4ajX50A6zluDUzahemXxUzKOu87Dwv5Esr5Q8xh6J1T0qw==",-824579793662028300,-9158766378588821517,-8339874761016104470,1390360969979668152>()
                  )
                  .append(
                     i(
                        (String)com.yiyiaddon.m.b.a<"s1b3hpl3qvvp6z","165UlAwRXrBAiXx3P/mtvoXmgUa7LJKb7lVZND4cd785BR7tXEI=",-6040193218956322460,2543283900140393890,-3388636147724483870,486466090050819035>()
                     )
                  )
                  .append(
                     (String)com.yiyiaddon.m.b.a<"s2jby6lnxltiwn","F3lS06x8qKRrjrA3JKZzzjvbjX/dbdF+c9q8q7vnNibxB7U9EBGvlueG79JAEoS1",5483671580910224776,-6697195415391919411,1207459417512001938,-4411610016150928033>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s1la4vhuj2j8gh","IHQ5Atw+L8sza0TAz8d+zrbqHcPMVd+PxxZ/oiQofOI=",4807474599485505927,-1336458960822068748,-2991917971413417672,6152972722086201604>()) {
                  case -379053400:
                     break label64;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var3.append(
               (String)com.yiyiaddon.m.b.a<"s269fkyyjrdeh7","5wKxRpp2PKzwdnNwKbT6TlIqz9PFKAmB3oDIt/8V+jTkb14avmiyLo+kyFYCTjGYwBn1WPgkeBnb4QDN",2004470319450166229,1273448911013134540,-817633378218388331,-7233337067397705480>()
            )
            .append(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s3uv635njo67kl","pkLCj+sxUEMz6OI3/CNwqVr/nG6TuoEbj2sOeLPwEIc=",-8745506706697844703,-7084997279692883890,5241691692706672112,-6406878514997262827>()) {
            case 117349085:
               break;
            default:
               throw null;
         }
      }

      if (!(String)com.yiyiaddon.m.b.a<"s3spyfz62sqtw3","NxVpUW+pPjnFg05wrAuS4TGm4JMzRH88dDrlEU814ZY96Q==",-6819353367432574501,-916783808145267767,8587337193097720434,-7053112392172806576>()
         .equals(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3mndlbz4ultzo","Qtl2p2aIhRjsOTctQ7GTF/gQprO9UEfKCew7m6Zm99U=",8802331107254575920,9124070052099467344,-3390003001439142849,611684348132725872>()) {
            case -1847437136:
               if (!(String)com.yiyiaddon.m.b.a<"s2yy5gq6wux1cg","Hl9lihibffyGDPVcERFGvxdrXB34Fkuz1GlxGJduP2MMXA==",8782015747074890794,-3568707177845304284,-5675948909167915815,-5324838301066979488>()
                  .equals(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s310m6jgjict0z","/bNWmzIFkUZXedTbmIrkYcWhqI1TPZfgoqngL+JVOlI=",1306323517303431258,-6884363723566152885,-4311251241780015256,7457828336626190363>()) {
                     case -1489413170:
                        var3.append(
                           (String)com.yiyiaddon.m.b.a<"sx4l2h9getxjh","16i7QimOqDqEClhb6LMCl6VIwkAB7sMAgjwRGFjPDKYuRa48dFwX8BaRJUJmGIuA1JLAqw==",4505714919165178249,5972813260061839137,-6357349744314004148,7229041710328374552>()
                        );
                        if (com.yiyiaddon.e.o.b.b.aw(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"su4s7w0cxo7de","Fo4uROGLag+VTeaGa7SVz0j+Z6lMFPKsG+M4AoI7K7A=",-976411936218255455,7936672911753601815,-6146894742926729576,8594801344057080199>()) {
                              case 527802505:
                                 var3.append(
                                    (String)com.yiyiaddon.m.b.a<"swvambm8p2ceb","po9iZ7DK8rObSbQD/8Hh5nameVcT4QWY9kOwfMRbeoxy648hUJOJrKjOtCLYIn513qR/gZRy5XZXSwDtsfoc03Gzk4KEX9kLYcjUsg==",6416817856909832004,-2041401945423514665,-1906340267011679252,-8426836267454116232>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s20sbhymnyq1jz","EjwIZLsUiFMwCAr9T4UHQxELZuWHC5CHqbrLI6WhcMY=",7290081866490515690,-1058796769313053343,7345028920090508234,6212091335287707938>()) {
                                    case 1107795659:
                                       return var3.toString();
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var3.append(
                              (String)com.yiyiaddon.m.b.a<"s2s1bith5gvutw","FU13yWTvtpwv2bBbJj5ds6YZyr+IuBNFEJgHxhH7AnKR+umXMLJJjRsL0B7msISq",3341437112386173324,-7155540377330947542,4498617013270846105,5495303591911799495>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"sndg1002jpw30","Bwewe2Miy3fb8PhwQGAX8XAFDZ98zn6fco3VIYpI1+k=",-4708972761718399363,-8691431786104166831,-1745678058503447535,2135640270407043952>()) {
                              case -1166536354:
                                 return var3.toString();
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

      return var3.toString();
   }

   private String eR() {
      ClientPacketListener var1 = this.ai.getConnection();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2srxaswror147","jX7VosRpbjug+/MnVnRDTGK2bynfpLdYczsvqaUY7Lo=",4598829814620083051,-883134867313402845,6401703692329434180,4638370678107146532>()) {
            case 195404775:
               return (String)com.yiyiaddon.m.b.a<"s2qnin4mrnzni6","/VTwzBlNnuy9S12gZSTBS7Zh3Jp4MbVw20g7DWRNRYw=",-4988727056449292435,-863982423561413764,-1139147327534276524,-733385614384714671>();
            default:
               throw null;
         }
      } else {
         String var2 = this.d(com.yiyiaddon.e.o.b.b.ax);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1sipusyhvoivk","hFKOX3E8viw1DXZnHRO2IyoRjmHKtV/Tx77OimbKia4=",-2571279569753311619,3416495933636687833,192483500137029767,-4630793017481664313>()) {
               case -370195576:
                  if (!var2.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s46wsgawlccsv","zhsuCv2Acjh/z2Tsm2rQT6iIE5rTUY5jCnQJoRTOle0=",-7763331825317372213,2542140543332700656,3367490699644066978,-4206689523433711443>()) {
                        case -1837650648:
                           return var2 + "";
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         String var3 = var1.serverBrand();
         String var4 = this.a(var3, com.yiyiaddon.e.o.b.b.aw);
         if (var4 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sw2civgha9voj","D6+JtiFuTYAv2EyGikcMDdbnggVFojYi4kTxu6PMW68=",7049030372291816574,-6328087264802596533,-8986588963018038764,-3302627416566645406>()) {
               case 546942768:
                  return var4;
               default:
                  throw null;
            }
         } else {
            ServerData var5 = var1.getServerData();
            if (var5 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"skotaogqgxqyn","uwRmveTu/K1zpU9ai0IYY1j4NhWgd/PbJJUEyCYCBC8=",1371501258079923368,5988602682743882533,-689317487449494618,-5022366665926196672>()) {
                  case 160084237:
                     if (var5.version != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1wltr32o1byxz","G7M0kGkGE4yBasHfcnZIQf6Ea2EIRYONSdNrG+BIzDg=",-7446823821409904361,-6709161983047898406,5403344537101147331,-731956666317580780>()) {
                           case -686525931:
                              String var6 = this.a(var5.version.getString(), com.yiyiaddon.e.o.b.b.aw);
                              if (var6 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s72ktu794lk0e","OemlOLcGmdgEi3JqLu4tefgFlruM/luQLtIrzMIYaOM=",-1338066459070347074,-1136516686244330150,6854826852054391269,-3546971726372154833>()) {
                                    case -552248796:
                                       return var6;
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

            if ((String)com.yiyiaddon.m.b.a<"s372lkl52geg78","MFBR0yeXbJSBXg6hpMHnwRsLmbkCW4+WYlxvXXbQee2Cu8lHhn1lFpNm",8090769796663553470,-4071919827612996763,7300914011611961088,4121684346153770514>()
               .equals(var3)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1i0rrdqpxfbcs","JumntBoaTmSW+RuvPb/w/gPF/8f/30Vg5bEEltmyLBc=",-4286516699499073795,7891495685043282988,-1916759116131784870,738768610208578552>()) {
                  case 1353030649:
                     return (String)com.yiyiaddon.m.b.a<"s3ahy7ckrnh6x8","GKLlTNoJ/7fdGzjuV/3UCyxGUHEhX/NisgRez2T96iA=",-4737263253591750165,-4194048803554707769,-6931860074843130905,-3829034373038413945>();
                  default:
                     throw null;
               }
            } else {
               if (var3 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2k1wtgfrs1844","o6sOzy1ip30bp77Zerv30y6n0EQMwyV/lQizW6TMU00=",-3801828605045536075,3666386292938925310,-7373615114521456212,2648131797633554580>()) {
                     case 1159337149:
                        if (var3.toLowerCase(Locale.ROOT)
                           .contains(
                              (String)com.yiyiaddon.m.b.a<"s372lkl52geg78","MFBR0yeXbJSBXg6hpMHnwRsLmbkCW4+WYlxvXXbQee2Cu8lHhn1lFpNm",8090769796663553470,-4071919827612996763,7300914011611961088,4121684346153770514>()
                           )) {
                           switch ((int)com.yiyiaddon.m.b.a<"svxfw4a9bbatb","YMobeAv+1ougdUFHSvXrXLLy6RJ4KqpQoxjuitoGbjs=",-7027326543830612342,-6103972291487684633,-8092935943514886447,-5155211941633054823>()) {
                              case 1042496822:
                                 return (String)com.yiyiaddon.m.b.a<"s3r4w5jrejl4uj","L0GKHXCkSPJJzgEYPBvfzAm6VU9VMkGpd6Rc6VLvGUU1g0EjfHrUcu9HnqmKJR+THn0i1w==",1483520546670831497,-568659120048971763,-7264966028077927480,4877174157684067355>();
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }

               return (String)com.yiyiaddon.m.b.a<"s2qnin4mrnzni6","/VTwzBlNnuy9S12gZSTBS7Zh3Jp4MbVw20g7DWRNRYw=",-4988727056449292435,-863982423561413764,-1139147327534276524,-733385614384714671>();
            }
         }
      }
   }

   private String eS() {
      LinkedHashSet var1 = new LinkedHashSet();
      Iterator var2 = this.am.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3vzsi5rjk5bym","hGxL8W/wsyJm1rW6xSey7QalNmmR3h+RgPxgBJY/PDM=",-3130016283996591265,999954584474414138,-9080965121749880615,3913738228365198077>()) {
         case -1624552537:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3evfn6s5c4bkt","NCAVdPlJrfaBCBIb5ggVRGpIL42V7sSWa9sxLpqW3W8=",8826325796852036459,-4436659129826114234,-917070022337115770,1985810500699587744>()) {
                  case -1338861891:
                     String var3 = (String)var2.next();
                     Iterator var4 = com.yiyiaddon.e.o.b.b.az.entrySet().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s2f613r9ujshdi","TllhGLh/L35fqC4Q2jumOAk4g93gT8kX6GMiHG3BgRM=",-5202667417960609350,-5461297377468021180,-5029190426508749285,-3791264605851648719>()) {
                        case -2087486535:
                           while (var4.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s22ibb73rtdb51","efjJkiowsXYuox2g/2/TVnLkpIKtrRez05FoQPwuIjQ=",-3074404031021948796,3554615058730509378,3602499944094112014,-3967521663795397782>()) {
                                 case -690858471:
                                    Entry var5 = (Entry)var4.next();
                                    if (var3.contains((CharSequence)var5.getKey())) {
                                       label57:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3o8qaoi4bg65b","tsV4Ncaskopvi6uFayYcLWd5SPpjbatLO5UDXdB2V8w=",8157299092483149341,-7278225174211843652,2466977625995304421,-2778532252746876943>()) {
                                          case 2101872217:
                                             var1.add((String)var5.getValue());
                                             switch ((int)com.yiyiaddon.m.b.a<"s1n05nqj0lecis","T9/hzNJpH/cnuCvKPMSEimrPhIZI5WFp9D5Kn0/vbHo=",6697478988864245180,-7550280792951639506,4978629435897121548,-2289301091496624890>()) {
                                                case 415883105:
                                                   break label57;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"syk3mxbqm1jtl","U8t8pGmwMZ/b8wuRsph0yxaEMs0QS30r4Pm9fFD3bm4=",6869187636736151125,-3414444986077523730,-4685903948625258761,-951310602548841609>()) {
                                       case -1151266654:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sx6ra08wsc9xl","+jCIoTgf+mMHUkva/a8jNw5GhO74CJn1SUYtYiRYzOc=",-9217871340218805059,-6825999811195729263,-6436678205606371438,6915012224407705176>()) {
                              case 1285231320:
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

            if (!var1.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s104dznta5qym6","FfXmt4mgC3hMDsp6RV0lBHI6MrSa3TeS3iRJWMQGiXI=",-9078752903027053108,-7391144164640676401,2095775504615939841,-8130591925478764892>()) {
                  case -1649787651:
                     return String.join(
                           (String)com.yiyiaddon.m.b.a<"s33w47tpzlxckc","1CtNXI/4pky12Q6J6BIdoiMQpu2nQ/Zw7dnpsbk43nA7qQ==",-5214147383716643976,4674339398012080511,6956264014040538,3175728956412700441>(),
                           var1
                        )
                        + "";
                  default:
                     throw null;
               }
            } else {
               var1.addAll(this.a(com.yiyiaddon.e.o.b.b.ay));
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2sq69dveb4npb","5jhY1kGCNQSaOj9Z1XFgMN0NhEFYxEOFVBc4vQenPjI=",6724189042212583382,4218497591012876210,8709496226629390892,-688088034464022777>()) {
                     case -1741897222:
                        return String.join(
                              (String)com.yiyiaddon.m.b.a<"s33w47tpzlxckc","1CtNXI/4pky12Q6J6BIdoiMQpu2nQ/Zw7dnpsbk43nA7qQ==",-5214147383716643976,4674339398012080511,6956264014040538,3175728956412700441>(),
                              var1
                           )
                           + "";
                     default:
                        throw null;
                  }
               } else if (com.yiyiaddon.e.o.b.c.cU() >= 3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3nlz7peqc8m3i","OaUswUo2SYsUAogDTkjRiLq1uylv/nZaJZj+XWdTuc8=",8884943184852187655,4025931506036410564,-7259510069014473628,-2378983459173300684>()) {
                     case 57645343:
                        return (String)com.yiyiaddon.m.b.a<"s1e27i8vc8c43j","wDGM9vUInTT32d61smtEbY6fRlfqU3HuJ/1WcJcdbw+ibqwpzPk0kHS8mtrd7ddyOBiCi9ozxAvydnUDHMZs3LfCJbF7MA==",-2245573656588384870,5299735948836606329,-3878543898470139373,7918067310197076656>();
                     default:
                        throw null;
                  }
               } else {
                  return (String)com.yiyiaddon.m.b.a<"s2yy5gq6wux1cg","Hl9lihibffyGDPVcERFGvxdrXB34Fkuz1GlxGJduP2MMXA==",8782015747074890794,-3568707177845304284,-5675948909167915815,-5324838301066979488>();
               }
            }
         default:
            throw null;
      }
   }

   private String d(Map<String, String> var1) {
      Set var2 = this.x();
      Iterator var3 = var1.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2tf4fcbts37uw","ShYkPzybFZI/E2y/jB9bgtYy9qeq9D6sUJ2Yb6Upo+0=",5445167994971984976,-8952648604982084663,-8639339261470513186,-5276815754027493704>()) {
         case 1083911280:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ta93b8tymyj0","KK5bJTZb6LNmDW7xYWZDpKe2E22x13kJ48GrKZataVE=",3891974540103161612,-4938347560953874576,6823235095220963167,8956200484574611394>()) {
                  case 548625360:
                     Entry var4 = (Entry)var3.next();
                     if (!((String)var4.getValue()).isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1dw1x1oz4b79b","sAVdB6pqS12+RXxscdZm020s610xHx7n8GcZv8INQMc=",3482755885075561374,-2905078498058276066,-5320516906529709606,-2326893971344258349>()) {
                           case 1680050390:
                              if (var2.contains(var4.getKey())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sgzrbpt1j4gms","mc7JHSvFvxVhaO+T2JYHB9QjSsyBhfm2Ziu3V0RZoEU=",1300464017861238381,-7970502168801711785,-3779597706601883088,-7659216471127691437>()) {
                                    case 886359474:
                                       return (String)var4.getValue();
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3ctyeibrxnatx","DQSEZSWYGqfgGHrbyxz2Rpr4edXFUzLL/rMFS1yQeRo=",-2636823915268735100,2377793368787512657,-9077850445351821301,6263420789197436078>()) {
                        case -1984731168:
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

   private Set<String> a(Map<String, String> var1) {
      Set var2 = this.x();
      LinkedHashSet var3 = new LinkedHashSet();
      Iterator var4 = var1.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2j8hac0moctgh","3wVue4WAF0D/ZOM5X/PuutTY83XX57T2sV9y5XSpHxw=",3192523751222081764,-891034228447936844,-937554018072207986,-6166536343369707339>()) {
         case -1092214741:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3vix7istf5s1b","GDVJvbGDrM5UpU1OkKBgplTK9rnt90R8ECzVXskMQrc=",2331774762951107922,6144853090239585033,-7817088897261369313,-7151951039375358727>()) {
                  case 1080022657:
                     Entry var5 = (Entry)var4.next();
                     if (!((String)var5.getValue()).isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"syaolp7eg73lf","wOarxtLp5lSKiQ4p/TMr1UkfPVpbuz1GooupyY34RfQ=",807314552836982377,-982640893268963897,-5856196474470104612,8590681770792095877>()) {
                           case 1492192073:
                              if (var2.contains(var5.getKey())) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"seayjvlgphm4y","YkyVOi/tMW0ftYsyl7d+SGH7MmmxtxG/ekJ6fYf9w8k=",4413094746000670629,2667241449628495563,129233379093297853,3832106829432991748>()) {
                                    case -2136063917:
                                       var3.add((String)var5.getValue());
                                       switch ((int)com.yiyiaddon.m.b.a<"s1jd6oi5qjtqg8","rBl6nwe/hhI7q/+v96LH3Rj9cDKHjxfkq2rv1KJuQU0=",-5828892474076246086,279083816397736350,3081430580184814062,8869091560929767562>()) {
                                          case 230861551:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s1agcpy4szc14e","xgC83+dU+oz29CDSVxveIs2p8lVhbMD616XnZ6b70zM=",7973795319814256330,767332743508261102,-5031622421152729506,8819858380675358376>()) {
                        case -1207948361:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var3;
         default:
            throw null;
      }
   }

   private Set<String> x() {
      ClientPacketListener var1 = this.ai.getConnection();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"szmephlsslwuj","tI1/3oP7wPUo4lf2IrXHANlY87gUD4zg0RflChC0IFw=",-4955855985202878552,7995905837152097132,8145327898333857385,-5305069961674223581>()) {
            case 1329869770:
               return Set.of();
            default:
               throw null;
         }
      } else {
         CommandDispatcher var2 = var1.getCommands();
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2y4ijdewovlrd","/MU6F6BEsUHlqWpOW55vbPt4baABZOY4w705J8CAVFA=",1541969624380503776,-445933809774897663,5479451365339030658,3171388372099543663>()) {
               case -867533762:
                  return Set.of();
               default:
                  throw null;
            }
         } else {
            LinkedHashSet var3 = new LinkedHashSet();
            Iterator var4 = var2.getRoot().getChildren().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s2gx70bdatru7m","qXAJ0SKvPlHmzRKqeH9pCeB5+fiSo3n8Oo0BNy43N1c=",577100640755725482,6779646626021741625,2837142783045222636,-7839961461539479983>()) {
               case -1476965791:
                  while (var4.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1d743k1njarjv","8cosDfQocoibju11jhcH8eFVSyrC9fm3QEAOsm/KDys=",-5782125758711316302,1675188345080694737,-9007839987885201302,-6395320753561912260>()) {
                        case -67275989:
                           CommandNode var5 = (CommandNode)var4.next();
                           String var6 = var5.getName();
                           if (var6 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1ukub1jtkwc8e","qMqkrczZm+xPOPngmjyQCGa6qKybcNAetRQTqRxT1mo=",4604359071482215135,-672182719583077794,5767642865907010991,-43130045634895430>()) {
                                 case -1639633823:
                                    if (var6.isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s92xxy63ykcqn","sdAJFLyxuxpRbmXuq886M1OwiZesuFT6Ohjar98852I=",-596838485178474249,5792282363834162191,177100906919130875,286305328274194575>()) {
                                          case -940126752:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1d8o7p0b6pguf","bBGqMHoi0dFpXBP/E/yeVQ6+Lunx2/YRBkCKvH6K3/U=",-4433414943965357202,4349245961437716131,693507738359781835,4118493128015178818>()) {
                                                case -510319167:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       String var7 = var6.toLowerCase(Locale.ROOT);
                                       var3.add(var7);
                                       int var8 = var7.indexOf(58);
                                       if (var8 >= 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2de042om3upja","zKTheOmWe/NeYHvsixv5m3+CIgQLaqYQ9VguhK8MvPg=",-5285760449695134322,-1149168199311384160,-67932964515765962,-5329839623621125450>()) {
                                             case -886987187:
                                                var3.add(var7.substring(0, var8));
                                                if (var8 + 1 < var7.length()) {
                                                   label48:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1c7nqs5r00bvd","eH78yTR9RPWJa9cvd7xQOvhh9+BhcKP/bFo4CeNn2VA=",3243618796834163956,5001844485557611324,-7428514167662167622,4776905904428057616>()) {
                                                      case 73638172:
                                                         var3.add(var7.substring(var8 + 1));
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2muncyjjcq1bi","fS4iT32w25cyiIr6pkD6Yn6PojCZjlBTxtuh9sAQhGw=",6635222573916232772,-2988242058304911023,-9128010127407511295,2692962008415757132>()) {
                                                            case -151333740:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s2fimafkm11pi6","83Gfw+kyQXLdWn1fH7sQq58X4ctXmQPeLymAIXIboDA=",-4331600607779702394,-807466097196135645,-6489296530606701432,2853114598734652422>()) {
                                          case 581255254:
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

                  return var3;
               default:
                  throw null;
            }
         }
      }
   }

   private String a(String var1, Map<String, String> var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5skev55foq5l","I9VXOkGgZk3XHY65cwm0BVGScfW8b1yYSBnDEJxe+Ew=",130460872837021874,-3075511915572593338,-1897943642560620129,4833205935524349711>()) {
            case 1469069175:
               if (!var1.isEmpty()) {
                  String var3 = var1.toLowerCase(Locale.ROOT);
                  Iterator var4 = var2.entrySet().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1ilf2qbdf8jrz","fuPfA6uDDXTdX+iD9w87gVDuFtYtBVjVKaEifJpOADE=",-9013818369155531588,562296968394005445,5048539638233451956,-765985531150554619>()) {
                     case 1540947767:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sjw5rffgymiyg","8WRBpSpz9kC+QGhK6HQ6Kq2ttr6eNjAPZarUckWIZX4=",-8206722705116356832,-627567607381131467,3445071614696464868,1206928304583746235>()) {
                              case 472922641:
                                 Entry var5 = (Entry)var4.next();
                                 if (var3.contains((CharSequence)var5.getKey())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s217apfo02aila","b/LLxYbKyBbTedpW421D+JZR3wmQZeH7UkOjEiNyqeU=",-1081677269528474866,6167523940369617473,-979151259971596677,161848324692433985>()) {
                                       case 300916368:
                                          return (String)var5.getValue();
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s291lmthg61fpo","+dvv/nt2rcwG5KWbO+7giXcFOqvv98FLHpGruU2JC9Y=",709959541970984886,4370737201015312200,-2051717289703826260,2257086670734488792>()) {
                                    case -1413356914:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2lokc31uw5rhk","WfSifdyCQEaPWgZD5y77lT60S+eHps9tKjgSbnPCNHg=",4583349101055233080,-6839410084336147527,-341205381478411603,-4934002107512997957>()) {
                     case -2147278035:
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

   public boolean a(UUID var1, String var2, String var3) {
      if (!this.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"sdm47us40ii2g","Bf4V2ATAiwBFfL+GvVrntYacNJ2hLSoAN0wYabRUFLE=",-936023256080169245,-4180358434824542931,9101494291463317133,-3613447255819725587>()) {
            case 785500002:
               return false;
            default:
               throw null;
         }
      } else {
         c.a var4 = this.a.a;
         if (var4 == c.a.BYPASS) {
            switch ((int)com.yiyiaddon.m.b.a<"s81dfctwr3ja5","6tnkOgYtcmC29PARuQAcIVW6wULfOtAm759ckfnHJpc=",-6963493486493196668,106369411041606843,345524740409725150,4953858184363092348>()) {
               case 2091953126:
                  com.yiyiaddon.d.c.b.a(var1, Action.ACCEPTED);
                  com.yiyiaddon.d.c.b.a(var1, Action.SUCCESSFULLY_LOADED);
                  com.yiyiaddon.k.e.e.br(
                     (String)com.yiyiaddon.m.b.a<"sgsm5q8k02w63","eIhUDmZrfualjnJBT+00LotQ/OT8JeGf6BGhznVfrUnBgI7PieV9f4rNaiijOfcysU5Cm/BxcE1UpDqogbaEaKbE",2596885782866742058,4120792516983176414,2974785767644491302,6580195243242814886>()
                  );
                  return true;
               default:
                  throw null;
            }
         } else if (var4 == c.a.AUTO_DOWNLOAD) {
            switch ((int)com.yiyiaddon.m.b.a<"s2niehmzyjgsr1","hh6t+JZ8syZ6LIGSHpxOrN7EkIRlWlvx0ylUCsjTKRU=",-8980894098305022383,3417298750784967634,-7444114029617887263,-2943545277320706756>()) {
               case -358328802:
                  com.yiyiaddon.k.e.e.a(var1, var2, var3, this.a.pq, this.a.pr * 1000, this.a.eK);
                  return false;
               default:
                  throw null;
            }
         } else {
            return false;
         }
      }
   }

   public void ie() {
      File var1 = com.yiyiaddon.k.e.e.b();
      Thread var2 = new Thread(
         () -> {
            try {
               if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) {
                  Desktop.getDesktop().open(var1);
               } else {
                  new ProcessBuilder(
                        (String)com.yiyiaddon.m.b.a<"s3o6f1e2qwc9nd","weh2YCOKPKyQnREPg51Rgg1pOwKgtWKEL7RP+/1Xx6zji1MAP+/R1fT+gzcFNv/YdCcbiA==",-1462347065560699320,112777077681900239,6569304387264370442,3389317844136872164>(),
                        var1.getAbsolutePath() + ""
                     )
                     .start();
               }
            } catch (Exception var3) {
               this.ai.execute(() -> this.o(var3.getMessage() + ""));
            }
         },
         (String)com.yiyiaddon.m.b.a<"s3jfjp6om9ub5j","v4LkXbe9iAibTl7PhI1PssJYrteMgxt9geRpp0a99NE9AFJF9Qsh5gbEWxVFvSj3rMXMjgXoHvARdMppi3c=",1228703323616806759,-946077854646498765,-6113350067318293161,-2088028120848421699>()
      );
      var2.setDaemon(true);
      var2.start();
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s3bp75h9u9zcie","y6RyakSsQJs1amcIA1coDfA326pgtMhIMDA1c5pry8DuCw/kEog=",1829637047924055550,5556079786283873998,4234653470670100962,597569534513159555>(),
         var1 + ""
      );
   }

   private void ad(String var1) {
      this.u(var1 + "");
   }

   private void o(String var1) {
      this.u(var1 + "");
   }

   private static String bk(String var0) {
      return var0 + "";
   }

   private static String i(String var0) {
      return var0 + "";
   }

   @Override
   public i a() {
      return new f(this);
   }

   public enum a {
      BYPASS(
         (String)com.yiyiaddon.m.b.a<"shiczm9zf5frd","/pCqh71P4ZeYDN+DYFgaR31LGsPA/iBemuNAN0PJSPj6Egzc",5782965463727394192,5982856350536610796,4116081125645714469,276692810015789375>()
      ),
      AUTO_DOWNLOAD(
         (String)com.yiyiaddon.m.b.a<"s1l1tbafz38q5w","OEDLpDo7eoPeLifmEONnpCOXim9ZvKKc0u6AfMabXgzQS44b",2248634483722706687,6705903958182395165,5661907443025200530,4583884684810789941>()
      ),
      VANILLA(
         (String)com.yiyiaddon.m.b.a<"s20g783izydyds","VgWLipQzJ2gidJ2g+QLVPjSdB4MJnE18qHx0+FzLu5jQ+gCA",-3933168587839300779,3084520757074647262,-4688517407758589366,-302291236556075354>()
      );

      public final String wa;

      a(String var3) {
         this.wa = var3;
      }

      @Override
      public String toString() {
         return this.wa;
      }
   }
}
