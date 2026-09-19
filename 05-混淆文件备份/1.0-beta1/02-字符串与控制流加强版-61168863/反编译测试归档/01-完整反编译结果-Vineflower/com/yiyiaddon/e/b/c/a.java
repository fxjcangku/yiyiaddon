package com.yiyiaddon.e.b.c;

import com.yiyiaddon.g.a.f;
import com.yiyiaddon.i.g.c;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public final class a {
   private static final int V = 60;
   private static final int W = 200;
   private static final int X = 5;
   private static final int Y = 10;
   private static final int Z = 5;
   private static final int aa = 20;
   private final com.yiyiaddon.e.b.b.a b;
   private final com.yiyiaddon.e.b.e.a b;
   private final com.yiyiaddon.e.b.f.a b;
   private final com.yiyiaddon.e.b.f.b b;
   private final com.yiyiaddon.j.a.a b;
   private final com.yiyiaddon.j.a.b b;
   private final com.yiyiaddon.e.b.c.a.a a;
   private final Minecraft e;
   private com.yiyiaddon.e.b.c.a.b a = com.yiyiaddon.e.b.c.a.b.IDLE;
   private int ab = 0;
   private com.yiyiaddon.g.a.a a;
   private int ac = 0;
   private com.yiyiaddon.e.b.c.a.b b = null;
   private int ad = 0;
   private int ae = 0;
   private int af = 0;
   private int ag = 0;
   private int ah = 0;
   private int ai = 0;
   private final Map<String, Long> k = new HashMap<>();
   private f b = null;
   private String bi = null;

   public a(
      com.yiyiaddon.e.b.b.a var1,
      com.yiyiaddon.e.b.e.a var2,
      com.yiyiaddon.e.b.f.a var3,
      com.yiyiaddon.e.b.f.b var4,
      com.yiyiaddon.j.a.a var5,
      com.yiyiaddon.j.a.b var6,
      com.yiyiaddon.e.b.c.a.a var7
   ) {
      this.b = var1;
      this.b = var2;
      this.b = var3;
      this.b = var4;
      this.b = var5;
      this.b = var6;
      this.a = var7;
      this.e = Minecraft.getInstance();
   }

   public com.yiyiaddon.g.a.a a() {
      return this.a;
   }

   public void ae() {
      if (this.e.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s433flu6ig4yv","qHM/WHycp30HQ4LXJGA/AKfgT0lXV/k3rakPWPikzK8=",-6273145838872810163,9019878172504404892,3717877102753540950,-4189487184706669768>()) {
            case -1017424342:
               if (this.e.level != null) {
                  this.ab++;
                  if (this.r()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2103ggjeawe5i","+O+U4cMaA0xzGBc114P296+VF4CbRpnHQmvSc2ceLh4=",-6282790622919965701,7062125119216440205,-8125639476032511388,-8165897755606280213>()) {
                        case 1210614784:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     f var1 = this.b.a;
                     if (this.b != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3czzchexsdod5","p/l/lVY/hJRxYwxI/NBlGgMVsLha/UsGDLzQ4kzUFRI=",-481321455761235957,3810163716693787020,5125293591676370265,7701733071223065680>()) {
                           case 117879245:
                              if (this.b != var1) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"sd7bl6ni7a5li","eSsTpPt5SHsbhk/SJdwlX990xM6KH5UIjCElWAVfaBY=",-4000828965961699502,-1149507856632249303,7221911473454457037,8083680628975561640>()) {
                                    case -1394037238:
                                       this.af();
                                       switch ((int)com.yiyiaddon.m.b.a<"s39d0ahjdq4nnm","dfS+6BDgVcskQNwT9xXMhSU5UseCJbwUH81APMcBC1k=",-3585272465525849360,6374736797144488043,1560180133419793968,-31880691333184079>()) {
                                          case -809900682:
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

                     this.b = var1;
                     this.a(var1);
                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ea6rvjadanu","LQ5keqFH0hV6UMFFaoITk2djfAeIVyIHfV6bloIzFlg=",8311236383208194977,-1940998935516807949,1646106757417533072,614147047603880836>()) {
                     case 1278194436:
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

   private boolean r() {
      if (this.e.player.isDeadOrDying()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gn9950co9x9e","e+U/EYuAqhrFE7Qk73J7hMTguzkOUt8wvs9zBngikh0=",-6611042701518925939,5302074967746351734,1331368595955462471,8510459340263060118>()) {
            case -967129424:
               this.p(
                  (String)com.yiyiaddon.m.b.a<"soashl3o3cag3","YoiUNMfb2uNtEITrLuRcwqqNi1YfJnXjV902BH5VGWkFmWRR",6578347302471498681,-2833983114188497971,2326973509483775839,680019631586565410>()
               );
               return true;
            default:
               throw null;
         }
      } else {
         String var1 = c.bU();
         if (this.bi != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2e79vq2puxwgu","TvJ1mLRSmnkuLs0fP3f44jk6mR5QowN14WfH12yH0ok=",8855874990351376177,1517148693848676971,4740835050894528334,549243469802812138>()) {
               case -861182507:
                  if (!this.bi.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3wmxst5jcd2e","Y+M4YQYzFBf3IEyzzgiybUtA395k9soVu10IVcaAZRw=",-9103980753607322650,6112947866252979545,-2631974872462052779,8549165155215526642>()) {
                        case 2068947306:
                           if (!this.bi.equals(var1)) {
                              label24:
                              switch ((int)com.yiyiaddon.m.b.a<"s30x65ij5z04nc","MIBZnNNnYVv+OxOdlkdEmtc9sTdD2MjvlDC6SmVBIgc=",-1515821730965888230,1625628388427878271,7258654418463863525,-4754546817877711112>()) {
                                 case -1485116114:
                                    this.b.ag();
                                    this.b.close();
                                    this.f();
                                    switch ((int)com.yiyiaddon.m.b.a<"s3azet748vv52w","1kA6VOKw2MOLi0sADKrEV13YWUr4rskFWi30bdzdEJs=",-2774332265073821844,4984844417337623316,4590743615594281621,5582302895459365213>()) {
                                       case 1927046999:
                                          break label24;
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

         this.bi = var1;
         return false;
      }
   }

   private void af() {
      this.b.ag();
      this.b.close();
      this.f();
   }

   private void p(String var1) {
      this.b.ag();
      this.b.close();
      this.a = null;
      this.ac = 0;
      this.a(com.yiyiaddon.e.b.c.a.b.STOPPED);
      this.a.n(var1);
   }

   private boolean s() {
      if (this.a.G()) {
         label36:
         switch ((int)com.yiyiaddon.m.b.a<"stk75e4u8h6jx","7WUBU32H5B+tBWsQidty3TivWLtevCWNRqstZBTRXAg=",9179706574542768352,5226655378934368803,3430759454405605601,-6197511887447497375>()) {
            case 1760825783:
               if (this.c(this.a.a())) {
                  if (this.a(this.a)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sqow9zzrfwxns","NmHgbKWfOLQFBHAaAZ6HJqFmCQyMKTQNSAh6XDRTwts=",2697910506640224487,4702797850703239370,-5511772768982974161,-7655136950371343577>()) {
                        case 1547382657:
                           this.a = null;
                           this.a(com.yiyiaddon.e.b.c.a.b.IDLE);
                           return false;
                        default:
                           throw null;
                     }
                  }

                  if (this.b.w) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3en8wpvu4hvmh","EMW9pnyPUaugk4CR35d7EbSvjUYqpd9TWPamtglQFSQ=",7515484750318659604,-3930733264855253629,-2492540918488625812,4525609789055611865>()) {
                        case -1713488320:
                           if (this.e(this.a.a())) {
                              switch ((int)com.yiyiaddon.m.b.a<"s261m49krdgbqk","/O/Nq+31jPyQuEwn11yw0viNEqYx5J0tCaURbUDraIs=",-3634491102960143821,5945739581076352386,3149930517446709076,-8554547908603637239>()) {
                                 case -965093868:
                                    this.q(
                                       (String)com.yiyiaddon.m.b.a<"s1bntyb7uwb1j1","1slo/aCbpLhQZu2bOjVxGQwyurhFtxxSZ4Y+iIShaWZ9/Lx2Xg/jQT21QcunWPz4FHw=",7766648525134574966,-8280703612458950320,9110623248783827260,2112138809015390635>()
                                    );
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

                  return true;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s12etrgpyfbu4r","nRo7qfqQ1OC0XenlYgkKyVVswNDaDdq+CyDf3RoC5D8=",-1651067129281592351,1598687495013592722,2189536902551729714,-2193580780168497328>()) {
                  case 1351770452:
                     break label36;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.b.d(this.a.a(), this.a.bU());
      this.a = null;
      this.a(com.yiyiaddon.e.b.c.a.b.IDLE);
      return false;
   }

   private com.yiyiaddon.g.a.a a(f var1) {
      BlockPos var2 = this.e.player.blockPosition();
      String var3 = c.bU();
      long var4 = this.b.L * 60000L;
      if (var1 == f.MARKER) {
         switch ((int)com.yiyiaddon.m.b.a<"s24l7d24jbv6tk","qUVldC6xvgnLFgD0qZ+B9afT8wHrnW5s4eYTVxMrxuI=",-5110028906945599934,-7688183269023985580,-4285061837701225864,5388560933360403216>()) {
            case -499478926:
               return com.yiyiaddon.e.b.e.b.a(this.c(this.b.bD()), var2, this.b, var4);
            default:
               throw null;
         }
      } else {
         List var6 = this.c(this.b.k());
         if (var1 == f.PLAYER_CONTROL) {
            switch ((int)com.yiyiaddon.m.b.a<"sm16e8e1sywh7","gYdFsHoxKTKNpiEeNwbCGiVcbe52q+d+h2299AjMbIY=",-5906173130445620824,-5014745464413939411,5665345202390407660,562374505074000029>()) {
               case -1464585946:
                  int var7 = this.b.I;
                  int var8 = var7 * var7;
                  return var6.stream()
                     .filter(
                        var2x -> {
                           if (var2x.f(var2) <= var8) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1yysge8uxbo7n","dTa2X1GHq+X8xxA6pGFglcd2VOz6LTL/cBgD9N5HcjQ=",-3981497675641495936,-1025878119464195686,782172869015438923,5670909654715197843>()) {
                                 case -1760262824:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3tv83vq99rcuv","SEdPKBniNL3NjojVUUL25drsGNy6f+GPDSoqTEGiojo=",3022063960100441186,1937674030320115216,-7237014031572018558,-819260599255066278>()) {
                                       case -1807057841:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"simovb1g0agvi","RlDyT1iqaMCAnVJScfoDg00vE4kZOYGCyN1nVeMBxDc=",-1744616012555263807,-8327273887159713194,-6336043118512574911,-2558640131777801160>()) {
                                 case 191575131:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     )
                     .filter(
                        var4x -> {
                           if (!this.b.a(var4x.a(), var3, var4x.fH(), var4)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2z415bhsqmrux","DCUyXy8XPONWpSkd1jEz8EUDC0sHJ1wNawGpwPWhG9Y=",-1564541312707664998,-6591973531755224945,7699160756050190977,-4752077989534058891>()) {
                                 case -53573286:
                                    switch ((int)com.yiyiaddon.m.b.a<"s33ic4qvpb1xxc","2lEcTRvybepcBCi6kfevVAeN6olgqmvDTyzM1kUPYis=",6487063372816115749,2946200801984451096,-3092239675144993276,1162019105304193626>()) {
                                       case -1586857107:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"stby74xkun9vn","LhZBVfWNmG/lUHYTtE9gVok2nH1vEQHOEVXHuZGFduU=",-4624734772891493223,1437425052316388618,-3966902726703971853,4842745807865477235>()) {
                                 case 1320124021:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     )
                     .min(Comparator.comparingDouble(var1x -> var1x.f(var2)))
                     .orElse(null);
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.e.b.e.b.a(var6, var2, this.b, var4);
         }
      }
   }

   private List<com.yiyiaddon.g.a.a> c(List<com.yiyiaddon.g.a.a> var1) {
      boolean var2 = this.b.w;
      return var1.stream()
         .filter(com.yiyiaddon.g.a.a::G)
         .filter(
            var1x -> {
               if (!this.b(var1x)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xrumgrz1kc05","rhoxE7Ovn+dueU6cAbhHFEyyFwUVrmAxfe1mkPxFTpI=",-516105766918243007,8603232131183438118,534050529164581377,-4616415000632832718>()) {
                     case 191525256:
                        switch ((int)com.yiyiaddon.m.b.a<"s12izdv1jt6ud8","3udPA9Vl/5067YC7m5huYY2f6H9PNGsO6+fCveD3UyY=",6576563532831775462,-8985996817552918041,-3552723092462180943,3306139177709250561>()) {
                           case -1956511099:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ek0d6rlk7rqr","cYzByRhco84cBWMUzj1oumzfiVxEAa0tSIqIfV6tpgo=",2449422346570364067,7892864944042251725,6548567034157822248,-8580688805018003241>()) {
                     case -2010283186:
                        return false;
                     default:
                        throw null;
                  }
               }
            }
         )
         .filter(
            var2x -> {
               if (var2) {
                  label22:
                  switch ((int)com.yiyiaddon.m.b.a<"s1k3sg4yy9rtd4","aoGAf8RWL4bwlze2qmHlDPUWdRvvsg44HL9LghyKnPA=",-8075827320267080334,-1337326045755076764,-4349779332208982745,-2641098691255534478>()) {
                     case 578791799:
                        if (this.e(var2x.a())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s37y5re9uyzcah","nX+YgrK9ttCE/lzR4MJn/zY+Oauw0AUn5IEpx3LvkkU=",4817797043165703096,-5480138734177060646,-7638272963370359405,-3038043385860329688>()) {
                              case 1005891881:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3hmp9kqwpbc4h","nZny3hooiNEhdwb9Gmed3d42nU4wsdVg/jNILdMMy+4=",809342621984052397,-2362458410531703141,-2374415824668756844,-4725046280393196577>()) {
                           case -1639987627:
                              break label22;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2ocvm977r8zlb","dJq9rT1IR4jsigk8DacpMgdLkFU95CRe1kABK+S8D88=",-167993417269638842,856581210895291612,353621305468084306,4809470803052296905>()) {
                  case -2069181290:
                     return true;
                  default:
                     throw null;
               }
            }
         )
         .toList();
   }

   private void a(f var1) {
      switch (this.a) {
         case IDLE:
            if (--this.af <= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s1xobu5d9xkr7q","U73b04Eso5HXYDinV0+gpNdDKtei5FOPHQt6Qcu9d6c=",-2917136653166575343,-6084034693984278339,-829673772149375056,188963576054050855>()) {
                  case -1082563995:
                     this.a(com.yiyiaddon.e.b.c.a.b.SCAN);
                     switch ((int)com.yiyiaddon.m.b.a<"s1pzljxfy3dkmo","P7qzrY4bDaQVAVH+ExVfloNcEHprZ37Hx2f7OaejUS0=",7531306421213536442,-2094969577264344269,-509576891799944675,940032099724949853>()) {
                        case -1584903819:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case SCAN:
            this.a(com.yiyiaddon.e.b.c.a.b.SELECT_TARGET);
            switch ((int)com.yiyiaddon.m.b.a<"s13mfysh1kw3oa","ntCzFBJOUB2IBroChmghhkgexv6Zh89jENnsdfVow+Q=",3275672147789812088,7837916947292091515,-1340639608905958102,-4815374083142537559>()) {
               case -1781794145:
                  return;
               default:
                  throw null;
            }
         case SELECT_TARGET:
            if (this.a != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2okyzykn6zims","+RdC44Uyy28FCz8zppNj+rzAxjPZ1j3SEV9f7ke5vto=",-3540557894303528521,-8772488674168077481,5927605006873257478,-8009110913621835505>()) {
                  case -12905403:
                     this.a(com.yiyiaddon.e.b.c.a.b.LOCK_TARGET);
                     return;
                  default:
                     throw null;
               }
            }

            com.yiyiaddon.g.a.a var7 = this.a(var1);
            if (var7 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"sqcz62k9bt05c","4PVQejR1vee4uDGuTIhkxgtD8j5Wr7F4tS+4opWOGNk=",2970984876948925517,-1347101627801482047,4102915265467410984,7686124253256373863>()) {
                  case -986784064:
                     this.af = 5;
                     this.a(com.yiyiaddon.e.b.c.a.b.IDLE);
                     return;
                  default:
                     throw null;
               }
            }

            this.a = var7;
            this.ac = 0;
            this.a(com.yiyiaddon.e.b.c.a.b.LOCK_TARGET);
            switch ((int)com.yiyiaddon.m.b.a<"s2fayww6y6b5on","LR9ODvIOwR4To3LX3/qDUAVbSntR+yz2HrlcD+g8gtU=",-5901284977819140572,-2051200242180353112,-647758095259768671,-5372894668715290551>()) {
               case 1602757337:
                  return;
               default:
                  throw null;
            }
         case LOCK_TARGET:
            if (!this.s()) {
               switch ((int)com.yiyiaddon.m.b.a<"sr5z698vm5dx7","R+WVyQfzgcJzkwjrWxK+UEynA+Z0NQUAAyFIihkoKAk=",-2204997311297432358,-4723923449773783552,6283292588777222494,-5463666479023186747>()) {
                  case -209956216:
                     return;
                  default:
                     throw null;
               }
            }

            if (var1 == f.PLAYER_CONTROL) {
               switch ((int)com.yiyiaddon.m.b.a<"sj1qfvyei669z","f1ffGs899bWqgAPcDzp3ze8C1D9GGUrSmkWXiuhePiE=",-4071628138590498542,-3258218937524720226,6222799657601524803,-4037480027267623830>()) {
                  case 118333997:
                     this.a(com.yiyiaddon.e.b.c.a.b.WAIT_DISTANCE);
                     switch ((int)com.yiyiaddon.m.b.a<"s3j6ungf0fimv1","SBwvQInU9YSb4YVe1SL/FLsGkuZ5vSqD3gWyB7aFgOg=",7822130793671448878,2752248144475148296,-2851805431170277897,9221258942575889394>()) {
                        case -2125034086:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.a(com.yiyiaddon.e.b.c.a.b.CALCULATE_STAND_POSITION);
               switch ((int)com.yiyiaddon.m.b.a<"s3fq1mf7481dj4","cll15PYe8cftLOffSMJ4/fWvGh7lIW9BwfaqGx9dwSg=",-5587969638903046012,3616579181036390392,2846174695829917715,1341292796702685706>()) {
                  case 73134730:
                     return;
                  default:
                     throw null;
               }
            }
         case WAIT_DISTANCE:
            if (this.a.G()) {
               label234:
               switch ((int)com.yiyiaddon.m.b.a<"s2lkags3xnzzmx","eI1iloDkfD1Qj+F0wLw54jPzMv1YRz7EVIqfmLLaoco=",4771303742380938511,-8447609459344535399,3726421868408252503,7006958699659243098>()) {
                  case 40153887:
                     if (this.c(this.a.a())) {
                        if (this.b.w) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2dsgdylp27daw","GGFc73RY9tMIUcSsMbFxrj0zGD6Qc0Mg2zZpzDh90go=",8518748199813498296,-5316374434413321482,-4355966586175890363,2767312570783485391>()) {
                              case -1232850078:
                                 if (this.e(this.a.a())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3sittsjflhr2b","uYoyuOLZTQCY8Dly4xuCytm3XXAUt6SnjkrEAEKC6/0=",-7508864606697697409,-7798683644000620858,3848935938153647871,-7236834257792334261>()) {
                                       case 917555497:
                                          this.q(
                                             (String)com.yiyiaddon.m.b.a<"s1bntyb7uwb1j1","1slo/aCbpLhQZu2bOjVxGQwyurhFtxxSZ4Y+iIShaWZ9/Lx2Xg/jQT21QcunWPz4FHw=",7766648525134574966,-8280703612458950320,9110623248783827260,2112138809015390635>()
                                          );
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

                        int var6 = this.b.I;
                        if (this.a.f(this.e.player.blockPosition()) <= var6 * var6) {
                           label227:
                           switch ((int)com.yiyiaddon.m.b.a<"s1lc22lv5klg93","19BFpb7pGaIG4Ddbw8RYBwF6JkuAP5DdtrXUPSfJZiY=",-4607316020216928371,-8959713533644010173,3113919970060278996,-1965848788266120652>()) {
                              case 1393901995:
                                 this.a(com.yiyiaddon.e.b.c.a.b.OPENING);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1x2vuec4vzo7o","8LfwRU6x/xSjT4Jz5MqAA7vB4URnl/1hc8kMzU8beQM=",-4665139513690374226,-6656278826227476940,1312240989856429112,-6490508576175750001>()) {
                                    case 1596268428:
                                       break label227;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"svprclnrj5c01","3n4ZQyAbAbX/LxlgWsut6UM5mNYdjbvxzsghSDlyveA=",1853860041093096853,-4682625149998472362,6437238047356828158,-7766027784461450288>()) {
                           case 912304197:
                              return;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1vwe149gik0lt","pySz6jIJekEZQn7L75emv7wPlInmoUu/we7lGgg9qsQ=",5021749949168313788,-4536835348688733933,-6567213406237952082,5992354861966940065>()) {
                        case -1061501750:
                           break label234;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.b.d(this.a.a(), this.a.bU());
            this.a = null;
            this.a(com.yiyiaddon.e.b.c.a.b.IDLE);
            return;
         case CALCULATE_STAND_POSITION:
            if (!this.c(this.a.a())) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ul2vkpph8yw5","CxdeYkSTkVb+qY1KUuayzdjb1hngLeIlaMJcApYS+yo=",5366236972924511568,-5115731699234025472,-8654438605224862152,8713670485115977396>()) {
                  case 2083576371:
                     this.b.d(this.a.a(), this.a.bU());
                     this.a = null;
                     this.a(com.yiyiaddon.e.b.c.a.b.IDLE);
                     return;
                  default:
                     throw null;
               }
            }

            this.b.ag();
            if (!this.b.h(this.a.a())) {
               switch ((int)com.yiyiaddon.m.b.a<"s270iz3lc4q2rn","rGQsr5wHdZvUuV7R0Yk6//bSfA4kk5oBRU7MwycpukQ=",-5931484543929526108,-5588997377381884743,-2956210016044022490,1338674273897958871>()) {
                  case -1276853942:
                     this.q(
                        (String)com.yiyiaddon.m.b.a<"s299yhbbaeg7ml","rzBAxEPFjSpCpT+oK50jUzq1X8lu7JOQPP51f08DU/Q+aWO/PHA=",-4933475163642549194,3186044875146760370,4662883810096364111,-8986077416138475258>()
                     );
                     return;
                  default:
                     throw null;
               }
            }

            this.ah = 0;
            this.a(com.yiyiaddon.e.b.c.a.b.PATHING);
            switch ((int)com.yiyiaddon.m.b.a<"s1ycs7nahv1mrx","QiwNF2QwvLCGrBZAhQD53e6l6KldP+zVHT22xuygUQk=",4519552717988898670,-2777228374208104113,-8546550115363301364,-5419495604844194654>()) {
               case -395178225:
                  return;
               default:
                  throw null;
            }
         case PATHING:
            if (this.d(this.a.a())) {
               switch ((int)com.yiyiaddon.m.b.a<"s3qu71imi0exjt","WVKXgltjLgN5uI+qwpn5MOmqoReNQsn2695KmXoEtXE=",-5084917751667868725,5631191373258457252,-3485129676702357060,-5578794482528545218>()) {
                  case -1997871173:
                     this.b.ag();
                     this.ah = 0;
                     this.a(com.yiyiaddon.e.b.c.a.b.ARRIVED);
                     switch ((int)com.yiyiaddon.m.b.a<"su9gnukf05tud","dYB/EFkwQ8kIxMWrytjKOQhTm+GgVGlSAGFXEprRnu4=",2130409560375739696,5557510552015288998,-5585927433391104217,-8290402586195611667>()) {
                        case -1794219031:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (!this.b.w()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1sesdjo9nr5gc","zTLUVrws4/J1SZ8RR/VM/D0X4XDpPW1S4B6UuWkl/Ts=",8446288407052921682,-8164840722369606016,6439372311769515453,672157766568827506>()) {
                  case -1155410051:
                     if (++this.ah > 200) {
                        switch ((int)com.yiyiaddon.m.b.a<"sbjist59jk4wy","fN0eYrHfHmjRLX5vuGiYxFSOU3Vdr6tWP4vurj+bt1Q=",1976274968316832464,-4513696804331933749,-7794403169901614682,6600984245750525708>()) {
                           case -213994887:
                              this.ah = 0;
                              this.b.ag();
                              this.a(
                                 (String)com.yiyiaddon.m.b.a<"s1htbk99k1z6qy","p6puafhky1P/yytmDekiSWchl04k1/9fCARszt7SpnksAGSJ",316419061131897374,7497780259046437591,2461436471053648803,1302839211883831927>(),
                                 com.yiyiaddon.e.b.c.a.b.CALCULATE_STAND_POSITION
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"sdsiq3r8u2qmy","Ru/4f6PY111310ElflbF/JCGlZfldG5fZ0B//VmrPZs=",2670103829316506867,-4264356863366753827,-4924529038429560763,2528506837004542868>()) {
                                 case -1718354464:
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
               this.ah = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s121mq51ygylxs","KhQKxIfu+xlbUImtwYUhjEW6dH3wbfeW16G35fpcKd0=",1670821085014568603,-7853972775941564814,-116774231570799274,1734006253709304826>()) {
                  case -1936113463:
                     return;
                  default:
                     throw null;
               }
            }
         case ARRIVED:
            if (this.a != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s32wejxxw5kph1","z0fuPE5jc7c6Nogyr99g0YE/KIYCfjFhiEH591YxhGc=",-4108649190256042044,7368182774394849347,6110034990690638158,2544824905513830263>()) {
                  case 1223424417:
                     if (!this.a.b(this.a.a())) {
                        label240:
                        switch ((int)com.yiyiaddon.m.b.a<"s31h6muolnut6v","G0/kjyEp0pikxZqNN/3rgogmeNnQqX1DvVAjKabnZ2w=",6500906605360554213,-156524887336167276,3119836052487200951,-52858141847434266>()) {
                           case -1181744505:
                              if (++this.ai <= 20) {
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s38h75rvwx5h50","J68wSCF4HO6b656Kob9KMyu0vQxof+rMznRgCjOQ7kM=",7934498131331212188,3913283266132804391,-2830382550973812639,-916449407243521161>()) {
                                 case -1926854203:
                                    break label240;
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

            this.ai = 0;
            this.a(com.yiyiaddon.e.b.c.a.b.OPENING);
            switch ((int)com.yiyiaddon.m.b.a<"s3opqtcgflqmxs","KoYRiEpn7ofRzBTe2Pua3aUF+15lYoz3+zMmemZUrWA=",8421388469876355582,3221274257553652877,-4234765801275695935,4485178846322780827>()) {
               case -599758551:
                  return;
               default:
                  throw null;
            }
         case OPENING:
            if (this.b.u()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1iiq42yndnjwt","7XYPoNPRersDr3Ry5DwAfNjMPF17Zx4N+bIRTfu+IhM=",-1862768627416359255,8428676735791923095,4372972040880435588,-9025749127065858033>()) {
                  case -152994445:
                     this.ag = 0;
                     this.a(com.yiyiaddon.e.b.c.a.b.READING_SLOTS);
                     switch ((int)com.yiyiaddon.m.b.a<"s266ch2socr6ko","Ca9gfMPKp2sb8L6GvYa91/pHGMy5RGcGtrC5uPfO8zY=",-497745189134196687,3432757158156232285,6897262753764945898,1201562122814084119>()) {
                        case 803020551:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (this.b.g(this.a.a())) {
               switch ((int)com.yiyiaddon.m.b.a<"s2vcq8mn9c96ae","wigF3oDKMqxoEQnJq9s3PCMhSKHtOdZU79qP8zbxMXA=",7433636213185994176,5837013299621227838,996720486853013792,8519840342043447733>()) {
                  case 1722553924:
                     this.ag = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s1dkjo6d7ydazs","hOs28A1cM4YROStDweDPK1uufp+6xuAztB60KAhH6Xc=",-189740420676357912,2905128215809185293,6286620811690357573,4333095471028711813>()) {
                        case 2038774530:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               if (++this.ag > 60) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2hvabo7lpv81j","g9VVede8PlrcPaCldMXGNQdgBedBqDRJpunh3R+GeJU=",-4265180007938316914,-2875693589746129526,-4349490872500615116,8681418993633193022>()) {
                     case 1434789563:
                        this.ag = 0;
                        this.b.f();
                        this.a(
                           (String)com.yiyiaddon.m.b.a<"s1het0y78ye9u1","UT7zNhV84fPWnRuGTlgXwuV7sS/jyx2TcWrks/f24hUoORoT",4238390413012666903,8956090914149034119,8153845353489395090,-1896487629496504919>(),
                           com.yiyiaddon.e.b.c.a.b.OPENING
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s2ka0z06r2spyk","mTkSlpklx1sWCMjUCwfEp7yZ66tPg9nge4QTf9Zujm8=",1254953195474523338,7072528000429865151,4112468330965589510,9088739389899951786>()) {
                           case 1147144314:
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
         case READING_SLOTS:
            if (!this.b.u()) {
               switch ((int)com.yiyiaddon.m.b.a<"sorrizcxwb37r","BDZo1I+gBWqL9jJlYJLIQUhEKX6CTTqnxv+HeplEMmI=",5857980215137803138,5853635409257940994,6202732660291647744,-1998325575115102596>()) {
                  case 2023426691:
                     this.b.f();
                     this.a(
                        (String)com.yiyiaddon.m.b.a<"s12szr3lot5169","96u7C1Poa6NlQW+He6A7d79X6gDibSBNzbZumU3wY55ZJvKChbCa+w==",4794967983491988805,5492218180846790045,8357770095903612779,293879036125383651>(),
                        com.yiyiaddon.e.b.c.a.b.OPENING
                     );
                     return;
                  default:
                     throw null;
               }
            }

            if (this.b.v()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1p530p3hojm50","hQKHGxc4sLB8TFeyVTySNYMA+d9epY8n4tNDXzQx/U0=",-3178668430079967290,1254062137301648920,-95502682076142030,-8820095511386737086>()) {
                  case 567421168:
                     this.a(com.yiyiaddon.e.b.c.a.b.MATCHING);
                     switch ((int)com.yiyiaddon.m.b.a<"s1c3efpk51xlew","Xxxdz7CSdiop2k4zYHvLqAhOtfEsnXtbXaJqqlv4YY4=",-5417821185058909632,1630026987947802827,6568915323194841471,8337291342273961694>()) {
                        case -654651782:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case MATCHING:
            if (!this.b.u()) {
               switch ((int)com.yiyiaddon.m.b.a<"s148wh1qpavm77","zp/VpLTj4KKpBn/3PheNFP6mCeyq5YiGozApFK0F43k=",8316369987152565399,7778995308715958592,-4034606205740012360,6903175431203817806>()) {
                  case -86851302:
                     this.b.f();
                     this.a(
                        (String)com.yiyiaddon.m.b.a<"s12szr3lot5169","96u7C1Poa6NlQW+He6A7d79X6gDibSBNzbZumU3wY55ZJvKChbCa+w==",4794967983491988805,5492218180846790045,8357770095903612779,293879036125383651>(),
                        com.yiyiaddon.e.b.c.a.b.OPENING
                     );
                     return;
                  default:
                     throw null;
               }
            }

            com.yiyiaddon.e.b.f.a.a var5 = this.b.a(this.b.a);
            label294:
            switch (var5) {
               case TAKABLE:
                  this.a(com.yiyiaddon.e.b.c.a.b.TAKING);
                  switch ((int)com.yiyiaddon.m.b.a<"s3prygd0092jz8","IWdjLujb9ekX24X4Xk+RCK12z98V7Z3q99Qi1t4OKII=",3805173761030407359,-4609541235166165237,155997603053758147,-5995834847796011039>()) {
                     case -1241163053:
                        break label294;
                     default:
                        throw null;
                  }
               case DONE:
                  this.a(com.yiyiaddon.e.b.c.a.b.VERIFYING);
                  switch ((int)com.yiyiaddon.m.b.a<"s2hbaz21exgj38","n8zDmN2rygwPU+dQC9n3sO8RVFUhRJFnKh0zrxwNs9I=",1590299102554642385,1106968309700661372,5048354407782214519,-7712198983145264586>()) {
                     case 1599411512:
                        break label294;
                     default:
                        throw null;
                  }
               case INVENTORY_FULL:
                  this.p(
                     (String)com.yiyiaddon.m.b.a<"s2ql0ukyqh22wt","GARLuI43yr7mR5DSBhFg10KR1kuKb5Bj33sdfLk87uF0KAWObMnevg==",-4806193176324593415,3223180408423604482,-1061152788102224718,-8368720777446261514>()
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s3s48oiovbh8rm","I6gEF7QycsznvwFLzEJ3f1n/lMoGPO96kkWHgByyd0Q=",-6180858760357165306,6110410599716225037,-5372487276973232558,-5632746464519122443>()) {
                     case -1915440442:
                        break;
                     default:
                        throw null;
                  }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s2i9gbg9yxs2a5","+aGeUCQ3i8m2zIWkGLcUIjmiQAXLDwk9XxDlSc0iY/E=",-2067830869072616760,-4617953012147460822,-3527566737012457752,3880226872175695689>()) {
               case 2023812212:
                  return;
               default:
                  throw null;
            }
         case TAKING:
            if (!this.b.u()) {
               switch ((int)com.yiyiaddon.m.b.a<"soebe6b7lyl9","IGNmKkw421j0lU757geWyuS/63gjFUJsotAOCu7jJ5g=",3486707569689487601,-5384714972761444275,6643232492171126212,4317822058920992036>()) {
                  case 332785666:
                     this.b.f();
                     this.a(
                        (String)com.yiyiaddon.m.b.a<"s12szr3lot5169","96u7C1Poa6NlQW+He6A7d79X6gDibSBNzbZumU3wY55ZJvKChbCa+w==",4794967983491988805,5492218180846790045,8357770095903612779,293879036125383651>(),
                        com.yiyiaddon.e.b.c.a.b.OPENING
                     );
                     return;
                  default:
                     throw null;
               }
            }

            com.yiyiaddon.e.b.f.a.c var4 = this.b.a(this.b.a);
            label253:
            switch (var4) {
               case WITHDREW:
                  switch ((int)com.yiyiaddon.m.b.a<"s1b7bh3muenhec","y+KkMEzfN3l51LXZNGpIvSY9QgzdH/FL1mGFBuKpmuQ=",-1028039074547427961,-7633520797655832245,5156333556435757761,8518330514491867983>()) {
                     case 2020411168:
                        break label253;
                     default:
                        throw null;
                  }
               case FINISHED:
                  this.a(com.yiyiaddon.e.b.c.a.b.VERIFYING);
                  switch ((int)com.yiyiaddon.m.b.a<"sl1dxgjh8uz8q","PUIYdPlC3Zu4mJFRexU+JHc8fmPjQjaJdkt8Yn6w6mo=",-6649525923514070197,2761253461574302742,4253429933842064887,-7630810290434539294>()) {
                     case 1043875571:
                        break label253;
                     default:
                        throw null;
                  }
               case INVENTORY_FULL:
                  this.p(
                     (String)com.yiyiaddon.m.b.a<"s2ql0ukyqh22wt","GARLuI43yr7mR5DSBhFg10KR1kuKb5Bj33sdfLk87uF0KAWObMnevg==",-4806193176324593415,3223180408423604482,-1061152788102224718,-8368720777446261514>()
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s1lhn6vlyxw4di","35cvhOtCCWWTrkktCL0qZ9Zu/l2GJFKOcm9htCRKFso=",2882103277424863523,-1589287175296990715,-489865847013535256,3929572110949909618>()) {
                     case -681708692:
                        break;
                     default:
                        throw null;
                  }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s2x3ux95r3r9ez","jJAUkeCt6Y8re/QAwlOq7kliJtVBEhBKiGA+mLsGZ/g=",4036537421994055050,-1768372462578276211,1327262291478831010,-1206119041888781501>()) {
               case 187075477:
                  return;
               default:
                  throw null;
            }
         case VERIFYING:
            if (!this.b.u()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ohaclwg2f2z1","yIEnYdOlmZeK54ehZnDLVfVJWXCbMBZ/ZvYGtyvCyFY=",6500787770230882193,-5986361885207971209,-3441731153770496474,-1132930560224606804>()) {
                  case -897363433:
                     this.a(com.yiyiaddon.e.b.c.a.b.CLOSING);
                     return;
                  default:
                     throw null;
               }
            }

            com.yiyiaddon.e.b.f.a.a var3 = this.b.a(this.b.a);
            label314:
            switch (var3) {
               case TAKABLE:
                  this.a(com.yiyiaddon.e.b.c.a.b.TAKING);
                  switch ((int)com.yiyiaddon.m.b.a<"s1nq9e6qmkn5qw","34bJOCabWMITcG14ZUH8HRCsBXo0otU1DADFNXMO6xc=",-5126839649729619260,-5050784195697032072,-2635361639034382130,-2404679780381674236>()) {
                     case 511807129:
                        break label314;
                     default:
                        throw null;
                  }
               case DONE:
                  this.a(com.yiyiaddon.e.b.c.a.b.CLOSING);
                  switch ((int)com.yiyiaddon.m.b.a<"s30b5k00ax17j4","yc5EupkGaZi0Rvr5UWGeJdVNrmrYrrDfdr1jIjTkULU=",4376217300457820340,3431142195066524594,2658389490917521056,-5151734769159409485>()) {
                     case 446522388:
                        break label314;
                     default:
                        throw null;
                  }
               case INVENTORY_FULL:
                  this.p(
                     (String)com.yiyiaddon.m.b.a<"s2ql0ukyqh22wt","GARLuI43yr7mR5DSBhFg10KR1kuKb5Bj33sdfLk87uF0KAWObMnevg==",-4806193176324593415,3223180408423604482,-1061152788102224718,-8368720777446261514>()
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s2yahj5sinzz6i","PHDQvxVX/uCBzQSnc5OYrnu3B/CeBqbTpX6F8fhEJtM=",1163041873905047488,885233406518904621,-2075100692250531368,7068894585371297991>()) {
                     case -1006418338:
                        break;
                     default:
                        throw null;
                  }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s3siogzd1ksy3","L/raEtJwdFyyhABtEUHXtVV9nhqmgEJwLEZbU4NoYjA=",3114870457058007801,6059464375214777758,-2645842888132800356,2297113272553097116>()) {
               case 879379671:
                  return;
               default:
                  throw null;
            }
         case CLOSING:
            this.b.close();
            this.a(com.yiyiaddon.e.b.c.a.b.COMPLETED);
            switch ((int)com.yiyiaddon.m.b.a<"sc79h69b170ub","bsI83HKJdSDLZTnG8fI9bPovJdsDY8YPkV0fuxB9dxk=",-7140692896352929103,-7592673960143987265,-2265207724957342061,-8610261277309743636>()) {
               case -459744518:
                  return;
               default:
                  throw null;
            }
         case COMPLETED:
            this.b.a(this.a.a(), this.a.bU(), this.a.fH());
            this.a = null;
            this.ac = 0;
            this.ae = 5;
            this.a(com.yiyiaddon.e.b.c.a.b.COOLDOWN);
            switch ((int)com.yiyiaddon.m.b.a<"s2dke180pfbbvr","sNUK9DQKUkruKHRsijXxVHJraHNBojuf2oAJAJbXYt4=",8131445359027325196,-593965028379034021,2476705480759156323,7645634503281559511>()) {
               case -122729088:
                  return;
               default:
                  throw null;
            }
         case FAILED:
            if (--this.ad <= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s2u9zbex35b8lj","uckCTBoQCLjMz7+YzFZhcwq3owLW6zbsofh+v/tyXq0=",4190927527391085662,-6131832863415779727,-1582768677868168046,5071894361267657259>()) {
                  case 426460171:
                     com.yiyiaddon.e.b.c.a.b var10000;
                     if (this.b != null) {
                        label280:
                        switch ((int)com.yiyiaddon.m.b.a<"s12iad059ic2bl","9AqQ1JCI7VuVSOP1303FJMr/wxGO9OLDeQFgqtD89XA=",-1602111384680728017,-4934782815756836402,1363971161945868477,-7423731355329666348>()) {
                           case -1296110778:
                              var10000 = this.b;
                              switch ((int)com.yiyiaddon.m.b.a<"s1bt2186w5ym1u","ezHk1rB3EXu5PdnVIoce09nwwGCQreuj88CDPjSI42g=",7666855730804814452,1093179663491856986,-7240353389275416452,-7070476319205282882>()) {
                                 case -182509198:
                                    break label280;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = com.yiyiaddon.e.b.c.a.b.IDLE;
                        switch ((int)com.yiyiaddon.m.b.a<"s2zmheihcnzkzv","BybfOZPDMYfQOb376Mt2NfDqmUal1p9e4Roi3/wKMJw=",3355657923760217619,-8211023801946350609,4619677031692822438,-1029768749102144155>()) {
                           case 101127743:
                              break;
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.e.b.c.a.b var2 = var10000;
                     this.b = null;
                     this.a(var2);
                     switch ((int)com.yiyiaddon.m.b.a<"s1fqwv38lj5xxi","+LEtMiPFUMW1+UdEej+lFETWYZIvWMjW/CPykD+Pe5M=",3852541566179710881,-2125387490675659660,2769165031250996578,-8799418682277648976>()) {
                        case -300443642:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case COOLDOWN:
            if (--this.ae <= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s6cm7hqingm1i","mACB1eKO5P0RBXdoLQfqMM8JT8AlgDmqoH3c4Kl43SI=",-4717612630316117609,5348280788140897484,-7151590746137548140,-2156274232858411136>()) {
                  case -2077716949:
                     this.a(com.yiyiaddon.e.b.c.a.b.IDLE);
                     switch ((int)com.yiyiaddon.m.b.a<"s2gd8m87saonbt","MUmHD9NRYvxAPhfwi4EG2qTbG9aBOOJIJk4g6h6Q5PQ=",6520252523268840571,4191438756371713846,7668324974547741919,-1499314814542284890>()) {
                        case -850048389:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
         case STOPPED:
      }
   }

   private void a(String var1, com.yiyiaddon.e.b.c.a.b var2) {
      this.ac++;
      int var3 = this.q();
      if (this.ac >= var3) {
         switch ((int)com.yiyiaddon.m.b.a<"s3eryr58vflaoa","+iFH/2+d+qIQwuVSpP1eCWaIYqgncPdFt3kjg5rx5N4=",4326462223465515152,6451519513850502375,7850878912961876674,5106895603372084335>()) {
            case 1556987507:
               this.q(var1);
               return;
            default:
               throw null;
         }
      } else {
         this.a.m(var1 + this.ac + var3);
         this.b = var2;
         this.ad = 10;
         this.a(com.yiyiaddon.e.b.c.a.b.FAILED);
      }
   }

   private void q(String var1) {
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2cgdpy4nn3g7y","rJHMuUwZzSoGEfwz0J+nEb9W98O+lpI717Az9Xi/ieY=",4952666235807543868,3561432695839104516,-3485748053158455724,4867832461462242019>()) {
            case -1999600036:
               this.k.put(this.a(this.a), System.currentTimeMillis() + this.g());
               switch ((int)com.yiyiaddon.m.b.a<"s3kq2vq944q0vt","PtANuaU8oj2Y+9HcSkOZJzNoXEqaawcah9qD/01NVCQ=",-6780202437988421133,6874008045384318263,-6207953420765919532,4716841797543188750>()) {
                  case 1258035696:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.b.f();
      this.b.ag();
      this.a = null;
      this.ac = 0;
      this.ae = 5;
      this.a.m(var1 + "");
      this.a(com.yiyiaddon.e.b.c.a.b.COOLDOWN);
   }

   private boolean a(com.yiyiaddon.g.a.a var1) {
      long var2 = this.b.L * 60000L;
      return this.b.a(var1.a(), var1.bU(), var1.fH(), var2);
   }

   private boolean c(BlockPos var1) {
      if (this.e.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3eujhmyianooq","VQB2XWFekemHhtatPVJ5W5gdXE21HnQjIJpT9Ab+7hw=",4627428637659137789,-83405136251285324,-1603166057970119124,1780920294264953646>()) {
            case -539142535:
               return false;
            default:
               throw null;
         }
      } else {
         return this.e.level.getBlockEntity(var1) instanceof Container;
      }
   }

   private boolean d(BlockPos var1) {
      BlockPos var2 = this.e.player.blockPosition();
      int var3 = Math.abs(var2.getX() - var1.getX());
      int var4 = Math.abs(var2.getY() - var1.getY());
      int var5 = Math.abs(var2.getZ() - var1.getZ());
      if (var3 <= 1) {
         switch ((int)com.yiyiaddon.m.b.a<"se8spx83immzf","+15Nh+BLQq2cgmD1KYcba2mTGSJyL/FAM4ON16MaUCA=",-3513571951825480914,-1865280888068038445,-5277616135877671876,-2192937348771542686>()) {
            case 125970990:
               if (var4 <= 1) {
                  switch ((int)com.yiyiaddon.m.b.a<"spuus8aoyv7lr","dWNBQHRWlwlLzRIKVUKLNOz1XRjDXPl0dL+8R64Xq/U=",-7035380157173181554,7822897602993705051,-2190457275320945025,-8340861732237277448>()) {
                     case 453444088:
                        if (var5 <= 1) {
                           switch ((int)com.yiyiaddon.m.b.a<"s14jnupfpbz8m6","l96cyVuVk1wZNQ5WQPDZl509PjfxCR2e6h5syQGw/cg=",7975975732000295975,7699647712265855234,-4497775048777274377,8973612133179310354>()) {
                              case -269076076:
                                 if ((var3 | var4 | var5) != 0) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1y3y91yf1hevx","yO6lBmhKP9gFDz+2fag2hdGAkXRY8YYSWpHRE/JR8Oo=",4224760052959815969,5983729597061756752,1751889838931545712,-8215634746827019653>()) {
                                       case 1817405145:
                                          switch ((int)com.yiyiaddon.m.b.a<"sczvk7xh8lsy5","cMxPdLmUe8UZuRTeEdwllMVgBUVhsxoaR628mFhZXY8=",2696453681250700697,-8782178607566144906,-3107821533892763436,2095869146794493505>()) {
                                             case -2007847800:
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

      switch ((int)com.yiyiaddon.m.b.a<"s150c74yas7r7a","pcqpv78Yu2yIcfFkMCylNCwPxM3HYq2hnrFj2/js7Ac=",-6047006884252329634,3418692492528612559,-4623054857572425073,-1555210624033615412>()) {
         case 1977938321:
            return false;
         default:
            throw null;
      }
   }

   private boolean e(BlockPos var1) {
      if (this.e.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s38sz7rh0iey40","rmq5bkRgvk8fg4aBU8tyMF/+ve9jk/u2BmMh/nzL6i4=",-2661444143677304506,-2288634446151288222,583186152795474645,58312754365060909>()) {
            case 2023193889:
               return false;
            default:
               throw null;
         }
      } else {
         double var2 = this.b.M;
         double var4 = var2 * var2;
         Iterator var6 = this.e.level.players().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sprg6cbnqtsmu","mBR1ZMgSZ8+DjhIx0jNmsslb6pkbrdf87d0R4n9tAxQ=",5067639095305130098,4210579859928854792,1900440776046717734,5347193166023177104>()) {
            case -848052243:
               while (var6.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s32j33f0pwux3g","lVPGscvrfcHpofoBMnAwVuvuIXZMCLL4sPSFw+UMDR0=",-8556384360177517933,-6369781426053022337,899520538128225728,-6253759498141862106>()) {
                     case 1434748547:
                        Player var7 = (Player)var6.next();
                        if (var7 != this.e.player) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1475mjdqimd2f","5pgcCiGvY1+K02APGR9935jqIH71x64stS0Zb60GCeU=",-3648666110880110350,3871870705879156603,5873139908640985064,2270117457308576020>()) {
                              case -113516873:
                                 if (var7.isSpectator()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2oz8np7ovb0o5","u3lPFKuuSYkO9Q5xKp4+f3zeVpQSE2d3XWW4bO5RFts=",-6365053310486234704,-5358478321792352186,5875753905391219019,6224978470259477962>()) {
                                       case 1551637912:
                                          switch ((int)com.yiyiaddon.m.b.a<"s23tout9dui226","UVOrNJtpoStJ7x3giUX2tvrv2Yi6TAPxgDkfXRomMjU=",4189811399517389973,8083112016930200288,-6567576149131124299,-1631057895930758151>()) {
                                             case 1705315626:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    double var8 = var7.position().distanceToSqr(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);
                                    if (var8 <= var4) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s11brv37kxo4sv","D+jjrLpKn/Z0nrfJKqYtv46vMueZS3HEISmPhEC53F4=",2046974904658851120,1407709359891230162,-455010373882891724,7365502145992180376>()) {
                                          case 1580601657:
                                             return true;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3g2q37wee55o1","oSKhQshU2W141p+5bW4bzi65E2lxV2xxLVff4GNpU8E=",7086861197062011180,4389158701895889699,5790327404003534207,-2002176577307809434>()) {
                                       case 1851626860:
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

               return false;
            default:
               throw null;
         }
      }
   }

   private boolean b(com.yiyiaddon.g.a.a var1) {
      Long var2 = this.k.get(this.a(var1));
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lyyjfzhir1su","cVJEiaJ2e/g4JzdKXBdTHd69SsPbOxzNjkWkDfI/Z4A=",4076309576011820534,-4807286835543242666,-2611406184171684404,-8485633731116379358>()) {
            case -1441040757:
               if (var2 > System.currentTimeMillis()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nadmmfevaij6","ATt8z/AaWnf+XD8vbaA0kJW9rKqUw6dQAcMxF8tZRvA=",-3310352250341420802,-5754959720647923670,565626239212480880,-619818841801665113>()) {
                     case 656691155:
                        switch ((int)com.yiyiaddon.m.b.a<"s3tr3tshpb8cwk","D2idjY9qZWifzz1JkcRfmOlhebfXUSLCq72yIPiQbWA=",5032257633030279022,-6089315063263091993,-2103362858664653225,-6485535705533420181>()) {
                           case -1927904344:
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

      switch ((int)com.yiyiaddon.m.b.a<"s28crvd2mwv84w","ccM4wn+oJ2bEOeaXgusnKUQWKqx4altbiHccfNaUB7E=",3500821069648712115,4657801294338268451,6431920556560584398,-8225757480199063668>()) {
         case 49275796:
            return false;
         default:
            throw null;
      }
   }

   private int q() {
      return Math.max(1, this.b.N);
   }

   private long g() {
      return this.b.O * 50L;
   }

   private String a(com.yiyiaddon.g.a.a var1) {
      return var1.bU() + var1.a().getX() + var1.a().getY() + var1.a().getZ();
   }

   public void f() {
      this.a = com.yiyiaddon.e.b.c.a.b.IDLE;
      this.ab = 0;
      this.a = null;
      this.ac = 0;
      this.b = null;
      this.ad = 0;
      this.ae = 0;
      this.af = 0;
      this.ag = 0;
      this.ah = 0;
      this.ai = 0;
      this.k.clear();
      this.b = null;
      this.bi = null;
   }

   public com.yiyiaddon.e.b.c.a.b a() {
      return this.a;
   }

   public int r() {
      return this.ab;
   }

   public void a(com.yiyiaddon.e.b.c.a.b var1) {
      if (this.a == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"sjaoeh60k0b2t","qR6ULHz4x5Tg07yljkTHfeJY+erCsQ1pv+2SP6lcbQs=",7729767520867314243,-1580061529223489916,7001208966928153114,1807748865878358120>()) {
            case 409868819:
               return;
            default:
               throw null;
         }
      } else {
         this.a = var1;
         this.ab = 0;
         this.b(var1);
      }
   }

   private void b(com.yiyiaddon.e.b.c.a.b var1) {
      if (this.e.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qfyinszz4un9","wv0Tk0LYxI+Iwfc5ZezNG6fsOt3R3wtH9g3ZeAjBYK4=",-5000134046454692806,-7217032002511288498,3837616098051966543,-3669171291101384589>()) {
            case -450021941:
               if (this.a != null) {
                  String var2 = a(this.a.a().getX(), this.a.a().getY(), this.a.a().getZ());
                  switch (var1) {
                     case OPENING:
                        this.a.m(var2 + "");
                        switch ((int)com.yiyiaddon.m.b.a<"s3hbj9fm0mget4","0/WLdYSPmuVspvOopAP88N2pklvVMiaBcz03H2ewmuY=",-8274258033632922382,-8100759318979324754,-3799195753794599843,-5085346706609088267>()) {
                           case -934914156:
                              return;
                           default:
                              throw null;
                        }
                     case TAKING:
                        this.a.m(var2 + "");
                        switch ((int)com.yiyiaddon.m.b.a<"s39a6ob518nkoq","TPVq4ud5gKCff/iS4wGirXv6awWHnd3sBeplBjucgN4=",5218423671470151785,3730476583934350213,2660854969917263996,-3506263986035992684>()) {
                           case 634798412:
                              return;
                           default:
                              throw null;
                        }
                     case COMPLETED:
                        this.a.m(var2 + "");
                        switch ((int)com.yiyiaddon.m.b.a<"s2lqnu4nl21ub3","AO3ohli6wA6Kd9Zps0y5vZFGiSalZ5IBWlaGmcwtMi4=",1988115875305820608,3968807628931611497,9081815765012754092,-4492700431514362384>()) {
                           case 31618291:
                              break;
                           default:
                              throw null;
                        }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"st9txdmehfjxf","nukN2Q9U5Qv6UbBBBzlLzQjXbpc4J7ZlIvOt/l3C+tI=",2421292017754933941,-3287165543852594662,-8214133719177610458,8090322972094367196>()) {
                     case 2080365817:
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

   private static String a(int var0, int var1, int var2) {
      return "" + var0 + var1 + var2;
   }

   public interface a {
      void m(String var1);

      void n(String var1);

      boolean b(BlockPos var1);
   }

   public enum b {
      IDLE(
         (String)com.yiyiaddon.m.b.a<"s3a3nebd05ic1k","NkWesvSK3KXE+OpQ4Y5N9bNUhG5eViN0VOCqCm5rHc0=",-7903623666570783996,-7916777523705367263,-6229968662194474780,-6243164295222379851>()
      ),
      SCAN(
         (String)com.yiyiaddon.m.b.a<"s305lfyjc1rdb0","kBuAtrvHtFv/kadEjudlJiDbvkqrJpPt4TKztcCm3Ao=",-6523792557247826864,2773996167000592957,6826093419265928414,6797372612156922026>()
      ),
      SELECT_TARGET(
         (String)com.yiyiaddon.m.b.a<"s2lmcxvzf6j8db","3itLy7GsV7d17TnXpFkoiUnzYFGB5I1i4CNLRDESOO57Oe7f",-4230471976787016810,4799083052147001244,-8561186346388530090,4454590211705648309>()
      ),
      LOCK_TARGET(
         (String)com.yiyiaddon.m.b.a<"s1dwano8vlhatr","LXxsRZWxIqHfYoJStaP1p1KKAkNjGwspfdIbBHhD02GLjG4H",-5272752553455892355,-7378675986952694345,7002277195098047496,-4350882922705496456>()
      ),
      WAIT_DISTANCE(
         (String)com.yiyiaddon.m.b.a<"s3exwu7rdb7qks","rCqVqHGvq/fR3AZLToi615zBglKF9tjVB//a6yrLShn1CaAW",-3196004841992020847,5237173470097921060,-8875582594751417121,1953156589587617518>()
      ),
      CALCULATE_STAND_POSITION(
         (String)com.yiyiaddon.m.b.a<"s3bjf3fjxmp9j9","B/lr9FCmE5wxpT8KqT9mmFlMU86fswVl8VjtbAVHYpNWTBHQ",3085371486325085510,-4662367108125959586,-3989593872628480120,-7617698922489353906>()
      ),
      PATHING(
         (String)com.yiyiaddon.m.b.a<"s2zfk05iki96h2","24RixMeFu4l39Erevw8z3qYXchfPfQfKiCQIujzHsGM5+Q==",-6739563472676792714,-221239438430402573,-6756035806095230906,2367322885635343769>()
      ),
      ARRIVED(
         (String)com.yiyiaddon.m.b.a<"s1uadsohk25rh0","fNDeiA3+QtbTtgUtM6iXPGtbUz0qVmazy3oNP741mYo8wg==",-9132597846543565451,-1094636159935998685,1302454825730586170,-5586327976948729754>()
      ),
      OPENING(
         (String)com.yiyiaddon.m.b.a<"s2ehdvsosf5ejm","75h6a+P/HOsh4gPeb/+Qe2ELpTWFCJwV2pAzYWMdE9p/QTXJ",1885586951892071528,-1319266849661128636,6742714497254404470,-382819875122024708>()
      ),
      READING_SLOTS(
         (String)com.yiyiaddon.m.b.a<"stkvtkshwqx24","5VviK7GrfFHFcIDKQkex6ZsPzD/mEbVoaF8uggxEml3vuD7p",-7903648860883895924,3839758715097639424,-786262349769069383,-287209318060999655>()
      ),
      MATCHING(
         (String)com.yiyiaddon.m.b.a<"s3d4t4nr4zanqz","W/vUdfsNwFmMKNurmYZI3tCPqbmYOGmmR4VicCJcFJdX5I0/",5480414991010846988,5400100598304798438,3611331627040744869,-347021375424175555>()
      ),
      TAKING(
         (String)com.yiyiaddon.m.b.a<"s1wq8k2lmlstxw","JQXgQyB+qFtpCaYFBYizEtuJyDv341FPeWpudqmo9TOwGQ==",2993306689178477496,4583063215281270919,3782676797853921941,-2226717294296962672>()
      ),
      VERIFYING(
         (String)com.yiyiaddon.m.b.a<"s32qjzavfnpebj","MY72Wqt5G0l4CZQYBGs3wcAnWhON9vnJwemuWBnVBdfsf2jq",-1612548511478790786,7601966863559311420,-6535200378821113342,-4290896831707025410>()
      ),
      CLOSING(
         (String)com.yiyiaddon.m.b.a<"s28xujwdtw2akx","kwQ3ab2VL+GxX6GfuKWhA5D7Ejszv4qTfQJnarpSLQRP3vSw",-536562675037837852,-1102224095819090163,-1316487482587818763,-2140266443953803006>()
      ),
      COMPLETED(
         (String)com.yiyiaddon.m.b.a<"s1zpjuut0xc2fa","pgZXk5jF8Qp0yS1vLTCCt476Vm1hvNtdF1isSF3QfzlbOA==",-1377981063375462298,1854706775437701735,8277582761845560930,7251365622995552656>()
      ),
      FAILED(
         (String)com.yiyiaddon.m.b.a<"s2oegnw58edu9y","Sig4qr1/88RGYEMy7YuyNP2XKlheInN8bLo/xSJgomU=",-8471257434459365346,-6050958489850251741,1017931664380429008,-944523173805944233>()
      ),
      COOLDOWN(
         (String)com.yiyiaddon.m.b.a<"sg2hm7ohoww7z","j+axRFzf1EMujUzqIlPEJaDXT4RrsocsgNasQCvXhFU=",-6095611298863012853,-5267900531883663002,681514198365312310,-7175064115069892789>()
      ),
      STOPPED(
         (String)com.yiyiaddon.m.b.a<"s3gxe44ikvnkq5","uIuI5uMUHtwNrcl/VASJOGR/5N64abBzdBl5uvYQSOXPdw==",8679812651532454019,-2639162277726248772,-1876122000253884941,3584618906130873754>()
      );

      private final String bj;

      b(String var3) {
         this.bj = var3;
      }

      @Override
      public String toString() {
         return this.bj;
      }
   }
}
