package com.yiyiaddon.e.n.l;

import com.yiyiaddon.e.n.h.c;
import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.s;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class b {
   private static final float cc = 1.5F;
   public static final int mQ = 32;
   private static final d C = new d(65380, 75);
   private static final d D = new d(65380, 30);
   private static final d E = new d(65380, 200);
   private static final d F = new d(16762880, 35);
   private static final d G = new d(16762880, 200);
   private static final d H = new d(16762880, 255);
   private static final d I = new d(16723245, 60);
   private static final d J = new d(16723245, 220);
   private final Minecraft ab = Minecraft.getInstance();
   private final com.yiyiaddon.e.n.c.a d;
   private final c c;
   private final p d;
   private final com.yiyiaddon.e.n.r.b c;
   private final com.yiyiaddon.e.n.k.a b;
   private final Supplier<List<com.yiyiaddon.e.n.h.b.a>> b;
   private final b.a a;
   private final b.a b;
   private final b.a c;
   private final b.a d;
   private final b.a e;
   private final b.a f;
   private final b.a g;
   private final b.a h;
   private final b.a i;
   private final b.a j;

   public b(com.yiyiaddon.e.n.c.a var1, c var2, p var3, com.yiyiaddon.e.n.r.b var4, com.yiyiaddon.e.n.k.a var5, Supplier<List<com.yiyiaddon.e.n.h.b.a>> var6) {
      this.d = var1;
      this.c = var2;
      this.d = var3;
      this.c = var4;
      this.b = var5;
      this.b = var6;
      this.a = a(var1.a);
      this.b = a(var1.b);
      this.c = a(var1.c);
      this.i = a(var1.d);
      this.d = a(var1.e);
      this.e = a(var1.f);
      this.f = a(var1.g);
      this.g = a(var1.h);
      this.h = a(var1.i);
      this.j = a(var1.j);
   }

   private static b.a a(com.yiyiaddon.e.n.c.a.a var0) {
      return new b.a(var0);
   }

   public void render(f var1) {
      this.a(var1, false);
   }

   public void c(f var1) {
      this.a(var1, true);
   }

   private void a(f var1, boolean var2) {
      if (!com.yiyiaddon.l.g.a.e.a().a(e.a.STARDEW)) {
         switch ((int)com.yiyiaddon.m.b.a<"s12v8egpbxkr10","7EvUouNNkhDzuXK2OzH/lRxk3wwsbbIS/s3M2203hn4=",-854879915990867974,1517324513659179897,8024397462874671569,3458990892385343333>()) {
            case -1058018783:
               return;
            default:
               throw null;
         }
      } else {
         List var10000;
         if (this.b == null) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"s2196yy5b2oa5u","+Uz3J6A3GPW8CFbSm+ZMONVr08/iprPXUoYLuH/qnSE=",-4838542611812912242,-810722141488838862,6503153455903132962,-1276573158100597278>()) {
               case 55206076:
                  var10000 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s2qkdstpicr7lg","FtuYVXWaFNHuXnEIdyU0cmyj4KX7i4jMIgPppllgYkc=",5260576914370640341,-2511162375305026140,3079909783816348401,-2479833006166570600>()) {
                     case 289893626:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = this.b.get();
            switch ((int)com.yiyiaddon.m.b.a<"sr1t7yifnx35g","lHULxS+l1zz90POgAGVsbl7BOYKhHHxh9ChB5k05hEA=",3489365468443015874,889776532045912152,-8368134382660123356,-6732811132922409558>()) {
               case -1942022931:
                  break;
               default:
                  throw null;
            }
         }

         List var3 = var10000;
         this.a(var1, var3, var2);
         this.b(var1, var3, var2);
      }
   }

   private void a(f var1, List<com.yiyiaddon.e.n.h.b.a> var2, boolean var3) {
      if (this.ab.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aiimcvttum34","ne7qRl91x18dneMkNqaoRPwvBCP5D4w7zSi5tAZys6Q=",4453776340415887901,-8140229282726260544,-6807766818134315575,-6213877126514480108>()) {
            case -237617008:
               if (this.ab.level != null) {
                  if (this.a.dy()) {
                     label110:
                     switch ((int)com.yiyiaddon.m.b.a<"s3ckq98x4m333y","gA5FOkG9b6J2VMLV8K1FOjxrn8h4NKewcOyyNQUu0Jc=",434383431971980772,5608326073819943146,-6262605748933892007,-5171329609031544316>()) {
                        case 142485490:
                           Iterator var4 = this.aZ().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s19rzhytmy4taj","5jF8jdEmUKwSMzle7XfRhHujmdLLf6aO437YpmJxQ/A=",-7389897874580426170,-5452655499388807768,2656616987261228272,354343142614600794>()) {
                              case -1075487409:
                                 while (var4.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1qol2s26pk9jn","7a9ppxr9t+RW8gQ/UHQ76oDP02HeodzwqT9Fz7yA1eM=",-648339394474864427,-8930316043924505085,-8634919507484567390,7316642456960786472>()) {
                                       case 2144585740:
                                          com.yiyiaddon.e.n.k.a.b var5 = (com.yiyiaddon.e.n.k.a.b)var4.next();
                                          if (!this.a(var3, var5)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3mr5dh00kpyny","HjrCHjH54AWHPwy9tYe1l1siEWRnRWSjpqPyAVtN6KQ=",-1762772125734479724,-6218717635778566740,5930864868876993695,-2709708247123238739>()) {
                                                case -2109483779:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2pre1si6m6fwp","Y7JTYFSXNM7R2x3bB8OTo8wGLt5CTKonp7QckjEZt3I=",-7187382950343868382,6912704052981322766,-4546265030065603830,4648228774442020967>()) {
                                                      case -578849997:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             int var6 = this.b(var5);
                                             BlockPos var7 = new BlockPos(var5.ci(), var6, var5.cm());
                                             BlockPos var8 = new BlockPos(var5.cj(), var6, var5.cn());
                                             a(var1, var7, var8, this.a.i(), this.a.a());
                                             switch ((int)com.yiyiaddon.m.b.a<"sn5er9l36ztzo","v7Hm5pXqZo5t1EMDJdSJNbPJEC9xptV5A3n1ohu8Wlw=",-6153459838096343331,3440555586108361405,4941183892025961555,7438558947778703565>()) {
                                                case -1651393830:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break label110;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  Iterator var9 = this.c.w().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1rq0oje442jh2","rzgP+kSsgnqhC7xp3Rtub03BsWNcHab5lPczZzVDRIM=",-8936776840984672213,8261650291259811803,-1016394575998315464,-4499124519001818061>()) {
                     case -2084386721:
                        while (var9.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3tb9zwkkglz2r","zqkEbRnwak4srTybcWx+2hddmCDm1n1aYO4O2UDKFyQ=",4778398614752550722,-943841577229751857,-5853133136074810525,-6336948943473643523>()) {
                              case 1490108526:
                                 BlockPos var11 = (BlockPos)var9.next();
                                 var1.a(var11.getX(), var11.getY(), var11.getZ(), I.ej(), J.ej(), com.yiyiaddon.l.g.a.j.Lines, 1.5F);
                                 var1.a(var11.getX(), var11.getY() + 1, var11.getZ(), I.ej(), J.ej(), com.yiyiaddon.l.g.a.j.Lines, 1.5F);
                                 switch ((int)com.yiyiaddon.m.b.a<"s27rn06yw7tk10","KApuELWeW0UR0vrF0/Sfx0r7qyxCHJYLvUjf7wPVOso=",99309267663948425,3643796768431193997,-6525041452887121850,9049479179659307855>()) {
                                    case 1421838748:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.a(var1, com.yiyiaddon.e.n.h.d.SEED_BOX, this.d, var3);
                        this.a(var1, com.yiyiaddon.e.n.h.d.OUTPUT_BOX, this.e, var3);
                        this.a(var1, com.yiyiaddon.e.n.h.d.WATER_SOURCE, this.f, var3);
                        this.a(var1, com.yiyiaddon.e.n.h.d.LAVA_BOX, this.g, var3);
                        this.a(var1, com.yiyiaddon.e.n.h.d.BREATH_BOX, this.h, var3);
                        List var10 = this.d(var3);
                        if (!var10.isEmpty()) {
                           label93:
                           switch ((int)com.yiyiaddon.m.b.a<"s5q680mn2990m","Ey/gpe9+dikpikL2LxPY7S3eejXgdrrW4xyPtsG3im8=",-2222637177455189962,5981132831670422292,3262849153287118915,6624519925013821311>()) {
                              case 975429234:
                                 if (this.b.dy()) {
                                    label90:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2hcd0h6x4w5h6","hpcT2fFXvhfA82kpzcpfZCi2JWVBks8wOgwlh+b3+9E=",-9135090977124801961,-7194043093211341962,8333927022694467219,8589957944287921497>()) {
                                       case -703917302:
                                          d var12 = this.b.i();
                                          com.yiyiaddon.e.n.l.a.a(var1, var10, var12.ej(), var12.ej(), this.b.a());
                                          switch ((int)com.yiyiaddon.m.b.a<"s2sywrfzj6m49m","xKGpJVXxoawtEaGLbCGQb1xaqHdrw8V3UMxbXonrg8w=",-2940305321641538218,6377533122866579579,-3828589921150515517,-7803863777941623223>()) {
                                             case 330080576:
                                                break label90;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (this.c.dy()) {
                                    label85:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3v225oxsruj04","2gSxgfdk950ocqP1irCLOawsfgM2ScWJO1cwC+Tsi1U=",164838714266652170,1240803179015126767,1125341513027134262,-2840331737657009002>()) {
                                       case -886489425:
                                          d var13 = this.c.i();
                                          com.yiyiaddon.e.n.l.a.a(var1, var10, this::a, var13.ej(), var13.ej(), this.c.a());
                                          switch ((int)com.yiyiaddon.m.b.a<"s223s4x9lfry6p","mmsQQBHF01CpMLjQddNFuUPBkNaELa+m7Hda/uR27o0=",8025753801494186067,-4259989497544247342,8285049056154854626,4961381104393626870>()) {
                                             case -945567693:
                                                break label85;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (this.i.dy()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2j9dhrheejlhu","83sCsuyowb7IegqLFa+/lTZlp/kXhvyT7hzzehFn1xs=",5647430114157739527,-8424334565898334747,5624709237153585928,3870999257332848357>()) {
                                       case 71651944:
                                          d var14 = this.i.i();
                                          com.yiyiaddon.e.n.l.a.b(var1, var10, var14.ej(), var14.ej(), this.i.a());
                                          switch ((int)com.yiyiaddon.m.b.a<"sf6xfag3hnr2z","C4UiXapgyV63qCgYj7lNo1XLNyelLh71EZ1Y9ViTzvI=",-9198662205901647711,-2824442816050577998,2188438209327066950,-5463734191640094394>()) {
                                             case 1614648532:
                                                break label93;
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

                        Iterator var15 = var2.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s1owztotxeskdi","9sEVqSNgwVCKGWCTK12jSIfXusLmNFKvVrjldC/4v5U=",-1807741635838450208,-4076008167976095373,-3229119399856063868,-1924535440757925315>()) {
                           case 1617059346:
                              while (var15.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sow5gxon3brii","dykNRHZnmMO3Gxu6eq/m7aygkBA1jagxwL/nQqdrmlM=",6902097852798248903,8102305170427026449,-2604749807730039174,7631735480491427978>()) {
                                    case 1793411542:
                                       com.yiyiaddon.e.n.h.b.a var17 = (com.yiyiaddon.e.n.h.b.a)var15.next();
                                       this.a(var1, var17);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1osk0luayw8b6","/TRbDL6uF5PxVl3EzbTm9Xblwo3uaS2nWNueIaPDK/U=",1426124418019959127,-8071434601371115363,-5997019538611191824,1241511260754837648>()) {
                                          case -1631426993:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              BlockPos var16 = this.c.t();
                              if (var16 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3bsd5cfq9rv9m","fVKvVCU5a5HsqPMFg4p3QUi51TQdCpVE/SAJgFhQCmE=",1863342059001205457,-4026850860687433298,1494259767928349472,9096700058331914175>()) {
                                    case 1982154822:
                                       a(var1, var16, C, D, com.yiyiaddon.l.g.a.j.Lines);
                                       this.a(var1, var16, E);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ac6f9soasmb7","P4Ul9el8LTcjs1IpNNfDNWgBLKAgny1O64uABzdLAB4=",842871243801639159,-7561961547446682603,-7212007426638136533,-7344510687740667074>()) {
                                          case -1061059410:
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
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1lrnyarhf7j3","FUKQSbJcYoSMtd9Wd1GJV5T8vyLirqhNyC5Sv3to3W0=",-2468783725898836057,-5124193757848590950,412045657072392798,-6907413438203382452>()) {
                     case 299935894:
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

   private void b(f var1, List<com.yiyiaddon.e.n.h.b.a> var2, boolean var3) {
      Iterator var4 = var2.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s379r3wgo1kz5u","rZTyk5aJgOeaJ/NHu3jEtHDzHCV8PAXQAzZ2kmLo8Y0=",1974677871447096321,-5722896938731774795,-5910519413198277954,2188810866574496051>()) {
         case 67799673:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s115u7wgqn86du","YSqKnZvkggr1le15w3rxnke7rZwhufbDFZcJXd/dmmw=",-8269635262603104373,4268918952700370589,3709888007319554174,7479962580646411556>()) {
                  case -1460686188:
                     com.yiyiaddon.e.n.h.b.a var5 = (com.yiyiaddon.e.n.h.b.a)var4.next();
                     if (this.y(var5.a())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ax8yro0km6w4","iDnBBqjCfeOQdw3pJgFTNUwTXpOO2jG9aZ32On1eCqI=",-6418009562741815116,3445667341611690678,2404971246510024288,-5823259922118556807>()) {
                           case 177876117:
                              switch ((int)com.yiyiaddon.m.b.a<"s3bgskgrosn5gr","61rOwQIJ4fSgwkZbEF1/M26QopVbrHz9jjxEx+6TqNo=",7927498141803091968,-2059913763729025425,-8375297575146967909,-5497815157557181285>()) {
                                 case -145159665:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        int var6 = k(var5.a().bY()) * 2 + 1;
                        BlockPos var7 = var5.a();
                        var1.a(var5.a().m() + var6 + var6, var7.getX() + 0.5, var7.getY() + 1.6, var7.getZ() + 0.5, this.d.lF, G, G.ei() / 255.0F, true);
                        switch ((int)com.yiyiaddon.m.b.a<"s1psnrh07i6mtd","/y1mPfNSl/lwH0r1RgWG5vaY+Az0P52xaZTw8kuyCAA=",-239318111906784396,-5510822101513736579,-4268007231700680726,-5810477140088485570>()) {
                           case 294734280:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            var4 = this.c.w().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s2kyhubqjgdaj6","Y0VHE8hkZ2pE5NGVs9b/gk1YvMIRxSfvKef2f3uaYig=",3580942782842038578,3120337222471695805,-2683586368552970915,-3248700366819200294>()) {
               case -1115888197:
                  while (var4.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vwgvyaib17ry","Gqt5ZO+NtcZrVCjRZxnVwBpf/DbJ+Sw1v2W7ewPsr0A=",-7487609344584215522,-170927601926703570,8518587574258471849,8299955081952333284>()) {
                        case -1392876210:
                           BlockPos var10 = (BlockPos)var4.next();
                           com.yiyiaddon.e.n.k.a.b var12 = this.b.a(var10, com.yiyiaddon.e.n.a.bU());
                           String var10000;
                           if (var12 == null) {
                              label73:
                              switch ((int)com.yiyiaddon.m.b.a<"s2k49xwtepkw2s","1VxUwasEoCba6JZuzkJRCgqMqgr9KEgEr6ltMRBwn6E=",-1565265268913975183,-6540305721621218232,5918199381238845039,-2633701378649129493>()) {
                                 case 132699841:
                                    var10000 = (String)com.yiyiaddon.m.b.a<"sl2jxmphu8fha","8xiFYIJW3FNqxQfVJCBsUPAdJwxaiBzaeitbYPZVdvm6IJUn6ls=",9060485536103045769,-7029491448457450199,4454539105603766920,1735037796952478989>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2x2so3cn74lkz","8Vq81SSlKBrXfisxEdw7F+Eb5bwG9RsKeTH1ESWWRBs=",4897699756739069777,2141810040058831982,-2112261677821235655,-356688502812868511>()) {
                                       case -300881043:
                                          break label73;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = var12.ef();
                              switch ((int)com.yiyiaddon.m.b.a<"s10f48h4ecejsp","tE6iMTA3C49I9erw9CGS23Og1SChPtfmI9yr0Ogg7GA=",1521766149769638365,-3343678639490588068,-4938378340738792406,521612314395537927>()) {
                                 case 1449648810:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var13 = var10000;
                           var1.a(var13 + "", var10.getX() + 0.5, var10.getY() + 1.6, var10.getZ() + 0.5, this.d.lF, J, 1.0F, true);
                           switch ((int)com.yiyiaddon.m.b.a<"s2tjhlve6gyypy","GTFAe38Gm86ENQflC5sebTtH5biYUi1parYFfKCKfCA=",-5959941113912278001,-9076181109544960632,-6773972691766780052,-228582375750400378>()) {
                              case 1986029170:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (!this.j.dy()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s199w2b8yu9h03","cc8N3MFlzCAUDK3c1y3NUN6Dn4RW/TGuV/wVfBSAlfI=",8074960424690892823,-7485931744800762311,4619132345749071402,5251372378335555481>()) {
                        case 356832206:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     this.a(
                        var1,
                        com.yiyiaddon.e.n.h.d.SEED_BOX,
                        (String)com.yiyiaddon.m.b.a<"s35a2qh9r5fp54","FSAOF3onR/xNoo7Jwm/w/ux6UFzRF/dpEA046sORogV01w==",-6698212766709724422,-2525727434738683958,-4751952920397177614,5041440525384922019>(),
                        this.d.i(),
                        var3
                     );
                     this.a(
                        var1,
                        com.yiyiaddon.e.n.h.d.OUTPUT_BOX,
                        (String)com.yiyiaddon.m.b.a<"sdxo97kp02v94","xPi47E4aMUv3C2RfLBvLu1jAmCar5fjgzQtmw+WYbyq/Og==",4147714696235596852,-8372229669993431594,-1356734043536275856,-6852065858493188538>(),
                        this.e.i(),
                        var3
                     );
                     this.a(
                        var1,
                        com.yiyiaddon.e.n.h.d.WATER_SOURCE,
                        (String)com.yiyiaddon.m.b.a<"s14n677rucll3c","Qi+e53N9Bvh2d94RHH5LW14B8k8M9qy07Q0oHi5NG4gvnw==",-8442653456126210667,564533314086333641,7190533869749270419,5054798661894270134>(),
                        this.f.i(),
                        var3
                     );
                     this.a(
                        var1,
                        com.yiyiaddon.e.n.h.d.LAVA_BOX,
                        (String)com.yiyiaddon.m.b.a<"s2or7j2diyw4b2","bH3ZXh5CJwpQ6DYAweLjy0FuC4kOQ30oRLEco42Rg30ONw==",-3848547209608779720,4063744864249803260,897502842771900666,8769323797721724301>(),
                        this.g.i(),
                        var3
                     );
                     this.a(
                        var1,
                        com.yiyiaddon.e.n.h.d.BREATH_BOX,
                        (String)com.yiyiaddon.m.b.a<"s1xwj24xtbuib5","7lT2h3a5vAiRIrz34gjL4GhHHdRqm/c74QdZbXXQU0FJyA==",2143858036717726866,726651996471017571,3699583954156586736,-8553233515037704895>(),
                        this.h.i(),
                        var3
                     );
                     var4 = this.aZ().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s10zyi1b81a3bt","JB3qxHV7HGbIWoZcefw9F5K87MumhgCPxsgVdnEW/BE=",-335592865521569742,-2623653299322033385,-6541171710466862640,-5380001250104735831>()) {
                        case -646278503:
                           while (var4.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s13pf05a08ar5u","7z2vbIwFElDWzqfY+0Vm2uBAIUKjMrtHqSVxxV7i3w4=",-961203308779890153,2967850323189756855,6945371789403038010,-7903943170645743336>()) {
                                 case -1173200049:
                                    com.yiyiaddon.e.n.k.a.b var11 = (com.yiyiaddon.e.n.k.a.b)var4.next();
                                    if (!this.a(var3, var11)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1d3dqmal7k28g","BPXm0Ea4oOAoc2s5X9xeqM6hVX7Kt6qeCO0lwNqROc0=",-4600782626149750903,-3663300765011461701,-8513918548461726556,6793559977678933335>()) {
                                          case 258162117:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1kcur3yxi28ld","5oEz2h0biVqqqMcm4GfFB1kisYSYNJSA4EB3AKWNZ0Y=",-8962032708933696179,-2141494941313795559,2967368139293096095,-2083259562564349142>()) {
                                                case -2061670800:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       this.a(var1, var11);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3hi90wghzscy9","LCg0ZDoXjI2MiKh2Up/SEN1zWnxfgPlxvWFc1q56Y7U=",2403723131421398511,7993537977284478676,-5650507673862562242,6198394223907911987>()) {
                                          case -1076352221:
                                             continue;
                                          default:
                                             throw null;
                                       }
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
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private boolean a(boolean var1, double var2, double var4, double var6) {
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"sng6fv7jggzjl","ed9H/Lg1azEDpidEOOspMfrE6+Ib4ua2tNt5FcJoWvk=",-5731957216606106102,5916590540206454563,8044785342599467884,-7948030568441045683>()) {
            case -467457158:
               if (this.ab.player != null) {
                  if (this.ab.player.distanceToSqr(var2, var4, var6) <= 1024.0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1vr5j0u9878dj","uBALb376GEtMdujkMTjTxhmu/XO60nDNWT6yWONAnSg=",-6323537603477657288,4906763062237985795,-1033013143798943907,336022851508365411>()) {
                        case -321159745:
                           switch ((int)com.yiyiaddon.m.b.a<"smw6wnzb9t9g9","daKeSLTBru8IZa4bg2SRZPdKJ/Vu2bHMQLHopZPeH3Q=",-4108255271714284572,-6739696928483415367,5643099985494792924,5913045385394848915>()) {
                              case 1170571227:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1is4omykmbi4o","xtfolzZVnApsJVhPO1soBTKi3O+dOns7mU/W2hb6hYU=",8043749138992663827,4186793448730262507,4571040021774677889,8709358782362302603>()) {
                        case 114329258:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s198ym2ai165b9","p5h3vsCXPMHJLxBJ/9D4SYPitN6qxNG5fXseoAv8Xt8=",7050659074902148655,-295587557234647382,-3174666074228427909,1456246075886107529>()) {
                     case -2025576513:
                        return true;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   private boolean a(boolean var1, com.yiyiaddon.e.n.k.a.b var2) {
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qgtr5yucg8tl","1fKqgpVMmJie7ZjTaPLX5umR19Pksmu3taaQ3qEr++M=",6658933113220510199,-7575234127506856974,-1396707250234965670,-8897981789049426677>()) {
            case -971213504:
               if (this.ab.player != null) {
                  double var3 = a(this.ab.player.getX(), var2.ci(), var2.cj() + 1.0);
                  double var5 = a(this.ab.player.getZ(), var2.cm(), var2.cn() + 1.0);
                  double var7 = this.ab.player.getX() - var3;
                  double var9 = this.ab.player.getZ() - var5;
                  if (var7 * var7 + var9 * var9 <= 1024.0) {
                     switch ((int)com.yiyiaddon.m.b.a<"sxjmef9pqgdry","yJOSd79aE6gX0n7byu1/2n9lmEnCIZMdfZOXa8ela7Q=",-3783769817759300858,8981961217002989426,4050561948538065979,4925697261551011424>()) {
                        case -784931240:
                           switch ((int)com.yiyiaddon.m.b.a<"s23wikjsn3licz","Q7gdYEC+zlLQXtv2S+AIGjTprdzKiyFnzjWGafcko40=",-4819906455128043774,-2849491059814885298,-2222069818920694111,-7503383963170216233>()) {
                              case -377050052:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sr3ogjlc6jm0l","mdXNfrT8jiNJJXkvpyms05K8qILApI4twlV6rS0iI5w=",-5572699563104308778,5863033704339430228,-707729322844133468,2658857094479449783>()) {
                        case -1573182451:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2gc6vm3u8k2va","Nnc2xF6j6lpX/VucRfpZqguC/ZGGLOes0E1pAZO2b54=",8457239299172793420,568384564019425204,375268275662899553,-2600324834722068181>()) {
                     case 1445924305:
                        return true;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   private static double a(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }

   private List<com.yiyiaddon.e.n.k.a.b> aZ() {
      String var1 = com.yiyiaddon.e.n.a.bU();
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.b.c().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2tvjajdanwqhk","DVlW1isRirLslpNcnxZgjOaJdJD3Af7+nectAClOIMI=",-8561862256666622126,-6623743004887298616,1999270049245120179,-3013891348032507063>()) {
         case -1173360758:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s30zbq8tozqau3","IF7HKnxyqh+3o1yrMvJ36ysR2Lol48MmU8fvPKVtAlc=",-1383427755881880999,2383922023898396842,5760777704433939016,-3274955991472055319>()) {
                  case 1682156221:
                     label41: {
                        com.yiyiaddon.e.n.k.a.b var4 = (com.yiyiaddon.e.n.k.a.b)var3.next();
                        if (var4.bU() != null) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s2brcqvxjq9hfa","DeGra/+j3ANkBstE4r6SGwfo51WD6xu7L9ByXpthcpg=",808100720691924909,6776675734031129684,4299980135399610195,-4721991838038263500>()) {
                              case -771666115:
                                 if (!var4.bU().equals(var1)) {
                                    break label41;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"shw3u1k7vvgpt","cMSrWtwgiT2rUAVUsY/XR/Rn0mxO0Y6sEfHD5vMPGxM=",-9078926244331877645,8942913198465136073,8689994849433800031,-3663063714402300398>()) {
                                    case 935027248:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var2.add(var4);
                        switch ((int)com.yiyiaddon.m.b.a<"s3aq0n9qv6xpnh","BkcJUTBKHOSDdoRJGiwfrX9mtRLj2MB3E11DW6Nkx/0=",2007071531194058969,-7535279112083480939,-8683746445693704616,-4172454007475426477>()) {
                           case -499496833:
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1utrb7jhb9j4v","l/7VphCIw8EOYRFZVYReG3NUZ52Xr7HrMQs39DqiPhc=",-5229614154012862470,939890220147316779,-7772427310220971147,5902379696049588348>()) {
                        case 1676904567:
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

   private void a(f var1, com.yiyiaddon.e.n.k.a.b var2) {
      d var3 = this.a.i();
      double var4 = (var2.ci() + var2.cj()) / 2.0 + 0.5;
      double var6 = (var2.cm() + var2.cn()) / 2.0 + 0.5;
      var1.a(var2.cr() + var2.ef() + com.yiyiaddon.i.g.c.bU(var2.bU()), var4, this.b(var2) + 2.6, var6, this.d.lF, var3, 1.0F, true);
   }

   private int b(com.yiyiaddon.e.n.k.a.b var1) {
      return Math.min(
         com.yiyiaddon.e.n.m.a.d(new BlockPos(var1.cs(), var1.ct(), var1.cu())).getY(),
         com.yiyiaddon.e.n.m.a.d(new BlockPos(var1.cv(), var1.cw(), var1.cx())).getY()
      );
   }

   private void a(f var1, com.yiyiaddon.e.n.h.d var2, String var3, d var4, boolean var5) {
      c.a var6 = this.c.a(var2);
      if (var6 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s15pruefk2iuy0","ofxL99WnQ7ZYiQ7alcp4pjgJqUWX0bMQ9mIqK0wDj/8=",355921386176996624,5501184257743204860,6342704223303238527,-3992433983979998742>()) {
            case 664789016:
               if (var6.G()) {
                  if (!this.a(var5, var6.a().getX() + 0.5, var6.a().getY() + 0.5, var6.a().getZ() + 0.5)) {
                     switch ((int)com.yiyiaddon.m.b.a<"syxvyr5egzt0x","lnaS47AUgb1G9iVBp3Y0dzpTHQ8lFjHIlDMiiBqRYTY=",-7435606741158678739,3544036275838734187,-1050405589073391378,-5351854810788828770>()) {
                        case 534979338:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     double var7 = var6.a().getX() + 0.5;
                     double var9 = var6.a().getZ() + 0.5;
                     BlockPos var11 = this.a(var6.a(), var2);
                     if (var11 != null) {
                        label25:
                        switch ((int)com.yiyiaddon.m.b.a<"s1abhra4ro3uxs","8WdtEcUpIzx4gxhZnNNpv4CIoQ6zu3fLd6YvXSKdztQ=",173549483612341293,-3006838282570213113,-3192068434863866784,2278519149839834359>()) {
                           case 171308136:
                              AABB var12 = b(var6.a(), var11);
                              var7 = (var12.minX + var12.maxX) * 0.5;
                              var9 = (var12.minZ + var12.maxZ) * 0.5;
                              switch ((int)com.yiyiaddon.m.b.a<"s24rgqtrd5kysz","CYRsUxVw97xsNx1x1S759BxEx4iJAr9Tq4txxkgeB4M=",7540030502364492496,4037807582535814364,-3996748975167442170,-4270341080140839078>()) {
                                 case -1417420842:
                                    break label25;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var1.a(var3, var7, var6.a().getY() + 1.4, var9, this.d.lF, var4, var4.ei() / 255.0F, true);
                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sx9tuagmiepzl","2/lunJJ039eRKHU2wqK3U3meyV8sG+MdsEJIWeoSCSI=",-7032599547379177163,-386352556880534665,2410514381004816780,7676624344237478860>()) {
                     case 1977299472:
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

   private List<BlockPos> d(boolean var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.c.b(com.yiyiaddon.e.n.h.d.SPRINKLER).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"svte08rmitjvb","35SNHIJTQ4wZXfll2+2hd4znqEUNGWPyG/T4k2b++1U=",2244734582394706834,-159515218171497090,2199313844245977732,8816560567282653954>()) {
         case 714197536:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1v3hwl6f5l2eq","W72W6sZjPInTfeIJ/IAfcvMosavcRozwA+7GEFC4HgQ=",-2068938101314139948,-6756287972138260034,1974411971115410607,1919229818476833852>()) {
                  case 2038640707:
                     c.a var4 = (c.a)var3.next();
                     if (!var4.G()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s95qrnj87nbaw","aBZnUIN4xVqopN+2/yVjH86qAaqaboIt5VWxPtAySU8=",-6271940351755400394,-7061473838084860300,6593650950936121621,-3209075368936189513>()) {
                           case -561857769:
                              switch ((int)com.yiyiaddon.m.b.a<"s3b0n85ji7py4y","oNGd8mdSrtQ5JIRrecNFjS5yy4qGl0UgzgOpdv5LcxQ=",2076218996388319047,-1308583226717382898,-8500412398947130833,5411553620468859916>()) {
                                 case 1947489166:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (!this.a(var1, var4.a().getX() + 0.5, var4.a().getY() + 0.5, var4.a().getZ() + 0.5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"snyc77sevbpw9","JvOVijNnH/e1qDRKkUy/mIDnJPRGymCevOYHpaP3cVc=",-3221606243815254742,-786797773220979594,8054450713759131112,-5244636353767953019>()) {
                           case 1476822733:
                              switch ((int)com.yiyiaddon.m.b.a<"s2ndoo6ew8udwm","CCwLMFAd5v86MljRV9TkzvrjpDIn22qhE0oJJm+CC6w=",7160369386733693384,-6982966624120300960,5019376794319173848,-4302176160206894349>()) {
                                 case -2009342168:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var2.add(var4.a());
                        switch ((int)com.yiyiaddon.m.b.a<"s1ozd3rotx8bsm","DLJmGUl+ARxBp986yTzk5IwXaZ/l9JJo+ZQL+CGufcg=",8062762853802259580,-4524998562608191177,8638321112591552356,8683822929686697454>()) {
                           case -1218372222:
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

   private int a(BlockPos var1) {
      c.a var2 = com.yiyiaddon.e.n.h.b.a(this.c, var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"smmj217wxz087","VzoRgWbokJxEYlKWXdJjisBv6gTOJN9GvW+isqFOSNc=",2309840019130968160,-1866669348761588198,-3766188975108666978,2012269968845375666>()) {
            case -441194871:
               if (var2.dv() != null) {
                  s var4 = this.d.a(var2.dv());
                  if (var4 instanceof com.yiyiaddon.e.n.i.f) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2khly0srnvmp8","fHJKR24j8PmG/dxpDMdWf+SQQncXdrzbcgCWg8mz6AA=",-3512339957434403137,3025088907131347300,-6550447344261786645,-8990942064968136249>()) {
                        case 1617056467:
                           com.yiyiaddon.e.n.i.f var3 = (com.yiyiaddon.e.n.i.f)var4;
                           int var10000 = k(var3.bY());
                           switch ((int)com.yiyiaddon.m.b.a<"s1dss1v5qiubwp","ekGfPY0r87g5cgSYqRd3C0O8XOfRRCBV5WMSmey3V1s=",-4803134866009306960,-4717977570389546248,6017877402263946653,-3382982536444391958>()) {
                              case 1181383230:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s22d2xrxxztwe2","G59fhwkTzMM/qLjfJ0ICG5GvlHpvEz7buZCx29c2UTc=",-2013462851072459635,-5143868343224279936,1819830396958564082,-7833421204642823571>()) {
                        case 656603551:
                           return 1;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sewvphdk1vlve","dzhO/18PMzEJwWWkUdvs9f9qlfiIc5ztT3fBasvjdS0=",-5148094521588211862,5003627039037226618,-2399785017504025120,-7961718371724237965>()) {
                     case -1989933989:
                        return 1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 1;
      }
   }

   public static int k(int var0) {
      switch (var0) {
         case 3:
            switch ((int)com.yiyiaddon.m.b.a<"s365sr06gp7y1x","/JKSSGYzm4dxZjt1ISN4CjleHhe5H8914OUMeC50AIw=",-1667122732425513523,-3783935208818463075,8184066971746384250,1300606508394461874>()) {
               case -1096311401:
                  return 2;
               default:
                  throw null;
            }
         case 4:
            switch ((int)com.yiyiaddon.m.b.a<"sobw9lo4osres","Pw99OpmmkGX7MPX980ci5k65E87G1JyebDHrC0LphQQ=",-7232220002414878677,1033928761564488331,-2256198947545143667,4500172286781980772>()) {
               case 1884560836:
                  return 3;
               default:
                  throw null;
            }
         default:
            switch ((int)com.yiyiaddon.m.b.a<"s15bgo7p11hmxx","HVDzIRye/RrtL+tB7WGSAnZIdn4H/hnAB68qa+S3vK4=",-1598896690980229443,7578757048822102668,4542339341026014006,-787923300091749771>()) {
               case 375559478:
                  return 1;
               default:
                  throw null;
            }
      }
   }

   private void a(f var1, com.yiyiaddon.e.n.h.b.a var2) {
      if (this.y(var2.a())) {
         switch ((int)com.yiyiaddon.m.b.a<"sylo7qvy8ifxo","O+p+avd95Ff5COu9atGKz6L8ru308Yjy4hAij49oUk4=",-8163783439918303215,8461466054138919975,-3170668277364949853,8193595441843937984>()) {
            case -306162997:
               return;
            default:
               throw null;
         }
      } else {
         int var3 = k(var2.a().bY());
         BlockPos var4 = var2.a();
         AABB var5 = new AABB(var4.getX() - var3, var4.getY(), var4.getZ() - var3, var4.getX() + var3 + 1.0, var4.getY() + 1.0, var4.getZ() + var3 + 1.0);
         var1.a(var5, F, G, com.yiyiaddon.l.g.a.j.Lines, 1.5F);
         var1.a(var4.getX(), var4.getY(), var4.getZ(), H, H, com.yiyiaddon.l.g.a.j.Lines, 1.5F);
      }
   }

   private boolean y(BlockPos var1) {
      if (com.yiyiaddon.e.n.h.b.a(this.c, var1) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rfsz66i7w3yl","CgijK1ChTZN/ran+ipsYb+ch0M+o1QI5SSLTPhWiNug=",-4044331785730731223,-319335357208789556,8888589931348223191,-3041967238193769415>()) {
            case 403862252:
               switch ((int)com.yiyiaddon.m.b.a<"s1995qcmcv0rmv","hF+aJvVrcEatJiFHBwx+WNKnzIXKT/AzpSEX/XJQnNo=",1898858075350817847,-481961056413650873,-6247818174390376391,3927619846987516283>()) {
                  case -781671795:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1q2z4clo5c42t","hXSYTwMV2YneGkRky1I8z+bEwPIHb43Rt78ggry7BGE=",-1873540522944264473,-1325694786640033445,-2195222019207232104,322377528581657980>()) {
            case 2024035017:
               return false;
            default:
               throw null;
         }
      }
   }

   private void a(f var1, com.yiyiaddon.e.n.h.d var2, b.a var3, boolean var4) {
      if (!var3.dy()) {
         switch ((int)com.yiyiaddon.m.b.a<"smub5v98m4is0","yRTGRS+VekXaXcF5rXa29xqjr0A2yT7tfRGtbuq7Kmc=",-9147498029135572862,-7547153562716725778,3289688349477024263,-187736996990990166>()) {
            case 1190837937:
               return;
            default:
               throw null;
         }
      } else {
         c.a var5 = this.c.a(var2);
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s11ycy2cne08ac","hB+UuETjgYyaFnN2lVu6qYVxdfY1eRDRkaq2xjRpUFI=",-3172205801068964480,-4684957086759121328,-3080624213949506284,262873431212405194>()) {
               case 2046294211:
                  if (var5.G()) {
                     if (!this.a(var4, var5.a().getX() + 0.5, var5.a().getY() + 0.5, var5.a().getZ() + 0.5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3rumoslxoh2yn","1cyGX6iRCmGbq0ZSv5cc2KdulS6alnp+I96ZiDZSaVA=",-8185845937614548821,-5866194036028885811,6857251873529087316,-3709932969455883815>()) {
                           case 21247142:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        d var6 = var3.i();
                        BlockPos var7 = this.a(var5.a(), var2);
                        if (var7 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1tffo8mm9k9ah","yYzuAtOBwrHslLveVbtkd3dnAGoLCm/GjdVdlAkXDBE=",2469645841299454792,-6989074678876394303,664293677673326139,-840653159572240987>()) {
                              case 93137538:
                                 var1.a(var5.a().getX(), var5.a().getY(), var5.a().getZ(), var6.ej(), var6.ej(), var3.a(), 1.5F);
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        var1.a(b(var5.a(), var7), var6.ej(), var6.ej(), var3.a(), 1.5F);
                        return;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s28opeyapsxhrl","tUCmCkKcB87foCTVbdHBe+EE7/hfrxt6UnqW83uCZpw=",8514525135371843813,-22710343860986031,-1274109013579341249,-1716906262819681157>()) {
                        case -763093032:
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
   }

   private BlockPos a(BlockPos var1, com.yiyiaddon.e.n.h.d var2) {
      if (var2 != com.yiyiaddon.e.n.h.d.SEED_BOX) {
         switch ((int)com.yiyiaddon.m.b.a<"s3f7skf256q8n4","ZBrHM+ORHH6sl3umtrd6kifzA01b56MSKiQKcoHhCx4=",-3299750413372058172,-3483419488807012092,4907622366311532389,-5930999865483121453>()) {
            case 1509539958:
               if (var2 != com.yiyiaddon.e.n.h.d.OUTPUT_BOX) {
                  switch ((int)com.yiyiaddon.m.b.a<"s140274ebb5bf6","Q2dSLQPLX0VOIYQ0rk2m9gw8Dug9QSH62JF2obo/kBk=",-6480207299266371128,-5875356275338169068,9130452950329980173,3482233236106638786>()) {
                     case 2117529683:
                        if (var2 != com.yiyiaddon.e.n.h.d.LAVA_BOX) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2kt18xmh83sa3","AK6Q6HNxhOUYMRr2GBar1tztmllcWnxlTzke44IoERM=",1424186377979185524,-3774797473800156565,-5283724382605082915,2134993969259708345>()) {
                              case -1207544652:
                                 if (var2 != com.yiyiaddon.e.n.h.d.BREATH_BOX) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1agsjd85c7kus","stKJkhKv2KuOLKU1zr5m8Igga5UgvqIdjRrxoQDbDLM=",-5683411958171796830,2106734838600148687,7825643882862696458,-8283853205821867856>()) {
                                       case -1322574206:
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

      BlockState var3 = this.ab.level.getBlockState(var1);
      if (var3.getBlock() instanceof ChestBlock) {
         switch ((int)com.yiyiaddon.m.b.a<"s1p8m4tjnd83gw","IVzkO0euKM3JJ8BhJWlECjXxhuWWX45caLHI/YwnRmU=",-1466387097750778755,2257492917816553943,3204577117923280986,-6617830399664676785>()) {
            case 948464374:
               if (var3.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
                  BlockPos var4 = var1.relative(ChestBlock.getConnectedDirection(var3));
                  if (this.ab.level.getBlockState(var4).getBlock() instanceof ChestBlock) {
                     switch ((int)com.yiyiaddon.m.b.a<"s23h22zayhki3u","ttXLKYLSA8w0VJCBYhmNN/au8seR+olTtYwg+5dZ3us=",-3824889684657882911,3658245896359985248,-351301303956240104,5636252531890508952>()) {
                        case -160800850:
                           switch ((int)com.yiyiaddon.m.b.a<"s8trhb7zkcw6n","pjdC33dpXAUcMY4rH8sB4cITSPfFvSzxbUNSlUZCnyI=",4295644409935089174,6758973359375043578,1337467611721883822,7939718063749278986>()) {
                              case 1961083520:
                                 return var4;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vgtnhalhza2g","d9TLkFpuf1zLurRw468ZMa9J3xdlJMpY0rhhGAoNaJ4=",-2689767977451592147,-5093348230471138228,5411039727960417533,-2981132475512823730>()) {
                        case 1277133229:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"se5looroa7f0u","fVG7/y/D54QgJvNJ1wBRllLYNivMvEkx15roNpjq9DA=",8060152701685501456,-5810519179942747137,-2429135233636240702,8331450814064730051>()) {
                     case 1040873989:
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

   private static AABB b(BlockPos var0, BlockPos var1) {
      return new AABB(
         Math.min(var0.getX(), var1.getX()),
         Math.min(var0.getY(), var1.getY()),
         Math.min(var0.getZ(), var1.getZ()),
         Math.max(var0.getX(), var1.getX()) + 1.0,
         Math.max(var0.getY(), var1.getY()) + 1.0,
         Math.max(var0.getZ(), var1.getZ()) + 1.0
      );
   }

   private static void a(f var0, BlockPos var1, BlockPos var2, d var3, j var4) {
      AABB var5 = new AABB(var1.getX(), var1.getY(), var1.getZ(), var2.getX() + 1.0, var2.getY() + 1.0, var2.getZ() + 1.0);
      var0.a(var5, var3.ej(), var3.ej(), var4, 1.5F);
   }

   private static void a(f var0, BlockPos var1, d var2, d var3, j var4) {
      var0.a(var1.getX(), var1.getY(), var1.getZ(), var3.ej(), var2.ej(), var4, 1.5F);
   }

   private void a(f var1, BlockPos var2, d var3) {
      Vec3 var4 = this.ab.player.getEyePosition();
      var1.a(var4.x, var4.y, var4.z, var2.getX() + 0.5, var2.getY() + 1.0, var2.getZ() + 0.5, var3.ej(), 1.5F);
   }

   private static final class a {
      private final com.yiyiaddon.e.n.c.a.a k;

      private a(com.yiyiaddon.e.n.c.a.a var1) {
         this.k = var1;
      }

      private boolean dy() {
         return this.k.dD;
      }

      private d i() {
         return this.k.y;
      }

      private j a() {
         return this.k.c;
      }
   }
}
