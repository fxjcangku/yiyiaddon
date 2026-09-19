package com.yiyiaddon.e.c.i;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;

public final class b implements e {
   private static final int aZ = 2;
   private static final int ba = 2;
   private final com.yiyiaddon.e.c.c.h e;
   private final double i;
   private final List<com.yiyiaddon.e.c.d.d> s = new ArrayList<>();
   private final List<com.yiyiaddon.e.c.d.d> t = new ArrayList<>();
   private final List<com.yiyiaddon.e.c.d.d> u = new ArrayList<>();
   private boolean N;
   private int aX;
   private int aY;
   private boolean P;
   private int bb;

   public b(List<com.yiyiaddon.e.c.d.d> var1, com.yiyiaddon.e.c.c.h var2, double var3) {
      this.e = var2;
      this.i = var3;
      this.s.addAll(var1);
   }

   public List<com.yiyiaddon.e.c.d.d> s() {
      return this.t;
   }

   @Override
   public l a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tkj6a96ujjxo","mFMo8D15govHEjmDFft06dSUIXGVUEEIDXFAFdKz+Kk=",-6249866025418545922,263990232536332042,-3275436370296992613,-3905783393850680250>()) {
            case 779148992:
               if (var1.level != null) {
                  if (this.N) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1ud22g7b7g86k","D7OHRcP5dENjCaHupGOcWGlj5lD+rorFLJRFSsnv098=",-7858858510733842433,-4482387535063173684,6169678090859000683,-1914245271001981138>()) {
                        case 1108145639:
                           if (this.aX < 2) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3lcc5jmcbswpf","vEd/HDpR3D0w8y0AE5JdCnEDRw3aEZVsmDLqLsiPeFs=",-1267112275007907903,-1008453564700281449,1083829842688415174,-8325495424422901041>()) {
                                 case -889636058:
                                    this.aX++;
                                    return l.IN_PROGRESS;
                                 default:
                                    throw null;
                              }
                           }

                           l var2 = this.b();
                           if (var2.S()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1scd8rv2e00jk","6ruSNoqg/+3su8Oi7eLeBQ04FOAOTK5OzPepPGAK8e8=",804927501989510462,1224098194075069808,5749018619204289596,-3733724124859105214>()) {
                                 case 1750305698:
                                    return var2;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.s
                     .removeIf(
                        var1x -> {
                           if (!this.e.d(var1x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3gg672bj588pm","W73qz8GVk3u2scGx7w4umV0cNhPM/ynth+idayNhdRo=",-5737188187797280084,1864951550622383800,-8341288775273710776,7069659186948433745>()) {
                                 case 2093644178:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2vedhowzpdz5l","gmWxmRcnC36tk2PBMMIvtW0BFw6KSfvMF+uEesyACWk=",-1423485075537236096,4228562943404715312,6281610503163325423,-7619515055994618242>()) {
                                       case 1574750600:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"sd39zlj63ix12","7lW5PSzTCctqjlLo8xQBo177jSZIDSCj2Y8rnc+hL0g=",5295770098078214807,-271630549084264982,-4619434174583323714,-6308402848833682189>()) {
                                 case -1822304449:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     );
                  if (this.s.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"stx85nkqq43wj","PAItW3W5ZB8UJJJqYwijfsTDWTxRwhaFhvyP3AOBfDE=",184973724707095255,-7168238367848357422,7069161325939029880,-8020553138589912985>()) {
                        case -1530582223:
                           if (this.u.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"sqttye1nlco2d","rN5/K8wZKPRD9GcQQ5p/fz1nKvm3i/QfqvQc77qdPmY=",1095168202665603219,-9026231369806626276,6642085536179242514,-201552091977634871>()) {
                                 case 810262977:
                                    l var10000 = l.SUCCESS;
                                    switch ((int)com.yiyiaddon.m.b.a<"sy0rb1sm65ez0","/rdIlN8+1doqxajH277GkrkDjO0/TMUseAVN5OyjpgI=",4478964122691349761,-5871887432045629255,751872349135787934,3469193213706128372>()) {
                                       case -919915056:
                                          return var10000;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              l var10 = this.b();
                              switch ((int)com.yiyiaddon.m.b.a<"s2v8u134i2rqx8","bb8zJdrJdeEOIYQcCuvu6KJRrdHMXIgpQns56Pob11g=",3356600359130516714,1129433718435870310,-9204165836275436151,-1276500449276439403>()) {
                                 case 1449846477:
                                    return var10;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     com.yiyiaddon.e.c.d.d var7 = this.b();
                     Item var3 = var7.a().a();
                     ArrayList var4 = new ArrayList();
                     Iterator var5 = this.s.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"sbkjqo7xzsd4p","qbkg/06GUKMXDwwsy98gl4WJ5FLFybKqs9o+aleGQCo=",-6777930209996526613,-2226378069573116227,4375196654730120211,4167028333624528195>()) {
                        case 1378779889:
                           while (var5.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s231mgarruu20m","7ck6lkuXlOWHRC8ZAMESc8jQ8Qc4ylsvzoNEWCSX8GI=",-1471692024636677290,6654194128870790489,2223235688825071965,-7530546067891040656>()) {
                                 case 1416375935:
                                    com.yiyiaddon.e.c.d.d var6 = (com.yiyiaddon.e.c.d.d)var5.next();
                                    if (var6.a().a() == var3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"so491leqw0gge","Uofs3tWDzEOuwv0pfVMVABynRI354gRj0Bzit8FvQso=",-5187584466305466294,-435659408611754591,-4360758812046664409,1076480966294676503>()) {
                                          case 227833866:
                                             if (this.b(var6.a()) <= this.i * this.i) {
                                                label114:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1njykv3c0ov0f","gnsyro9HG9Jovq4VThDcnO5Bt9uWQhYABZRATSuVFOk=",-5177487034380877038,-7099046866151212982,3733407466699243548,-7578423606387665839>()) {
                                                   case -650598915:
                                                      var4.add(var6);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3sppsumzbfj3s","eyptyq2jwN8E5dow/gZFXIQq4B0XRS/dzQBkFuQm5MA=",2225286163103766804,-4014576335582023738,4392178235801700380,2585661071725024361>()) {
                                                         case -187204542:
                                                            break label114;
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

                                    switch ((int)com.yiyiaddon.m.b.a<"s1i2z5ccuzcy3f","VoIHcZBZx0URvgIy5wodCac4pY12yWX2DHUL+2JEHd0=",-6516063196010039824,-2144373387868350810,-8961579432902927443,8008059904835481695>()) {
                                       case 1597162873:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var4.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s20z5f25j7rp8w","pEdr4Yvsmj3z66Ps9Gg2mEe5HslyBHMlX7EM5Qx3RBc=",-3831130068056597139,-7478030777958352684,103396923400922515,-1989452314939471353>()) {
                                 case -2094897127:
                                    if (!com.yiyiaddon.i.c.a.ft()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s29ckrz20lgcm2","nGUyzZB5yklPbjJhop/GbatQr57o+DN9rjw9x6G41FU=",5448201326718178769,2933301169029489096,-1524658739567627166,-255694332080252041>()) {
                                          case -2018027736:
                                             return l.NAVIGATION_FAILED;
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (!com.yiyiaddon.i.c.a.cX()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3inmj9s5y9nq","qsRt2+WPxDdqFhKfa0pxBMopx+vwa8SEzCo7oc5D+0s=",-4663150835569422162,1888640655635067959,3274187530784930317,1723751310591091046>()) {
                                             case -1559492258:
                                                com.yiyiaddon.i.c.a.b(var7.a(), 1);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2qlojcyseunjz","bLeinXeaZ6L40LvLBbhjfR74aEfTEP1lKeXdWcRVFdA=",-8331764454434685617,6582194600792738343,5697311433184131965,-4214105477841819602>()) {
                                                   case -170286231:
                                                      return l.IN_PROGRESS;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       return l.IN_PROGRESS;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              com.yiyiaddon.i.c.a.i();
                              if (!this.b(var3)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"spbxcn87iwmsy","qDHaQH1rlqe5tXqHRmfEZpUhdpAk80kIU3EJrmJ0czg=",-7422359285987341898,-5148214026005946307,4537816878060893035,-4968326788617158642>()) {
                                    case 1168233003:
                                       return l.IN_PROGRESS;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var5 = var4.iterator();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3u87025u7ntmg","wCSMN9B27APq/2OHsz6w1iazFbAcToPUxqPNXJeN4qM=",-2831010530257555611,252624588061400739,-4465562469631425869,1095446766115909356>()) {
                                    case 687994054:
                                       while (var5.hasNext()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sck7oyzh0yzk6","cIIA/hVB0nWNdZao03Zt2EyRgkYaCOJhSRDBt1n6JWY=",-7973089720744831912,3400610534985929387,-4445447176134701537,866115876584732986>()) {
                                             case 1273053731:
                                                com.yiyiaddon.e.c.d.d var9 = (com.yiyiaddon.e.c.d.d)var5.next();
                                                com.yiyiaddon.i.d.a.a(InteractionHand.OFF_HAND, var9.a());
                                                this.s.remove(var9);
                                                this.u.add(var9);
                                                switch ((int)com.yiyiaddon.m.b.a<"s8tqwqg55rhk0","aLDf4TP3fYFO4zOoAplpgDN/i70LmSVgyXswyE3o060=",-5939660827929464166,1355445141395375448,-5347194987589794773,4540575833061672509>()) {
                                                   case 627565456:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.N = true;
                                       this.aX = 0;
                                       return l.IN_PROGRESS;
                                    default:
                                       throw null;
                                 }
                              }
                           }
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s190uwmq39j3ne","T2vNzjzVkEBln2cIWniHSXdDRS2XgflnIfedC4RURag=",2780921369450928453,-5600645151541537608,-4527045337628207596,-5863178537843037759>()) {
                     case 1995826071:
                        return l.IN_PROGRESS;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return l.IN_PROGRESS;
      }
   }

   private l b() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.u.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s21fyu5kls33kw","orW+/CDieRHnElfRKLQki1MPoOfseW+hyp8VXhbkti0=",8117839606409331418,-6739503734529005882,-5710486799083639589,-3026957832893607440>()) {
         case 2130974334:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s32hmp22p83zt9","kc/ajDIMoVikRlTTxe2NNp3vMiAm2G5xTJEtmu7pYcA=",3195606745977363456,-5029838555906708483,-8999974876903452057,7787545498537391883>()) {
                  case 687420844:
                     com.yiyiaddon.e.c.d.d var3 = (com.yiyiaddon.e.c.d.d)var2.next();
                     if (this.e.b(var3)) {
                        label49:
                        switch ((int)com.yiyiaddon.m.b.a<"s11qghaz4fxhdd","YOW14IFT38eFcK4mj6xkBZ1WcjajqHsGyQMMoohhPYU=",8792726926661285830,-3575216919248324061,-4879681839026546235,5124743677932069340>()) {
                           case 1353153085:
                              this.t.add(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"s32w3jstovdg7s","akmWY6HjKX/f0q1tSGdnRBiG6w7oK6rfhnxtsxTDh1o=",-1440096460803087715,7536269136038047807,-927990622370922381,-7182752142597629083>()) {
                                 case -1090751491:
                                    break label49;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var1.add(var3);
                        switch ((int)com.yiyiaddon.m.b.a<"sh2uyj6i37ki2","zgJiyrLNNIZWPH+CyGGWfN7oAVRA/yTxQnaMHfLGjfk=",5341007142100847314,6338477599131162066,-6683579976168241985,4821446519824405734>()) {
                           case 2090623877:
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1lsmj0hx3vrda","i9T8ZbiFRPwQ+oKjls7Qd97Lqn+6beNlJ1GzGSKhOco=",-4052032276937141569,-8001034275684496981,-2540516334975387615,1610804298884853484>()) {
                        case -1095321607:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.u.clear();
            this.N = false;
            this.aX = 0;
            if (!var1.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3r90v5f3cxaww","0U7y6CpM76Pgu7cIFqXCjsaTc15DGFZkM4CpcbyNpdQ=",6056868092634743242,4224292580116452178,-3351234340612480905,-1805843188254316759>()) {
                  case -1002565826:
                     if (this.aY < 2) {
                        label37:
                        switch ((int)com.yiyiaddon.m.b.a<"s208xhbzbxuqhw","pTwGqEzi819Fp5lri6sgz0W8mTvGYmFvoSmBTXHNDkY=",8857596115779852874,2699627050315501485,-3859787970547403725,6732048763727948043>()) {
                           case -1677344584:
                              this.aY++;
                              this.s.addAll(var1);
                              switch ((int)com.yiyiaddon.m.b.a<"sdplwc8tt3vwg","3n6LINrawHTxv7+uS+oOvpFvSpZ+d6789kEm2/0jA7Y=",6817141684720958898,-2953479924842569575,7366507416062682919,-5265225184600386394>()) {
                                 case 1281535217:
                                    break label37;
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

            if (this.s.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"sqz4o3j5dqq8a","PVrzfuRM9hB7b9JUXmJ35Zbr/bp4ERtnYX4HPjd3Ae0=",7531634062649820186,4921211605092347499,-6785316856126934550,5521623270383054221>()) {
                  case -1314079230:
                     this.aY = 0;
                     return l.SUCCESS;
                  default:
                     throw null;
               }
            }

            return l.IN_PROGRESS;
         default:
            throw null;
      }
   }

   private boolean b(Item var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player.getOffhandItem().is(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s4m0yd93lav7l","me3cg9DxyvW16dJNIeF/QfJnshUovXBKPId7jxre9as=",-8228764359614207398,-4394626291134890263,-4276237741035032885,2598997794878820647>()) {
            case -1512035425:
               this.P = false;
               this.bb = 0;
               return true;
            default:
               throw null;
         }
      } else {
         int var3 = h.a(var1x -> var1x.is(var1));
         if (var3 != -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2vbve8ensbijx","71JPSOCKGoxmwEWOZeRR6vyGdIOxyh6W3At1mj31oH0=",-6754891001014866387,474116232381717886,2918503530464329772,3776375385019857350>()) {
               case -223039076:
                  h.c(var3);
                  this.P = false;
                  this.bb = 0;
                  return true;
               default:
                  throw null;
            }
         } else {
            int var4 = h.b(var1x -> var1x.is(var1));
            if (var4 != -1) {
               switch ((int)com.yiyiaddon.m.b.a<"s3bl95vkdv5dq6","IdmnqgljIoIPE+dJnSB6PXi3LnpJvnskRMj2Nz01HmU=",3265546266089032653,3382728315639741995,8220313406239041754,6987102864536200188>()) {
                  case 674295451:
                     this.bb++;
                     if (this.P) {
                        label32:
                        switch ((int)com.yiyiaddon.m.b.a<"s3a4ejmjnctxc4","dv3UwDJqY/5SVTcqSPbAIXl1mLYTYdZepeRQMKC9TE4=",6183342263354880176,5586658168045878667,-984713202599603527,-8977084397791351165>()) {
                           case -192944670:
                              if (this.bb <= 8) {
                                 return false;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3i3iu58mvofjg","IwxBRfMK1YprINMbpoin7cEyXLAUpQyZgk6ZbXIZZCs=",5567455316643330902,7047703530148588212,-3998214094261856615,3045528264334244884>()) {
                                 case -897825854:
                                    break label32;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     h.c(var4);
                     this.P = true;
                     this.bb = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s26qky52drfi9u","ayzHF+5uYZvtb6Vw06w6gjG1VXjpi6zgfsUH7REGCLI=",-6619169941259032586,8242795992588955458,-3045378834978474098,-2026633147493494892>()) {
                        case -2050941358:
                           return false;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.P = false;
               this.bb = 0;
               return true;
            }
         }
      }
   }

   @Override
   public boolean O() {
      return false;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
   }

   private com.yiyiaddon.e.c.d.d b() {
      com.yiyiaddon.e.c.d.d var1 = null;
      double var2 = Double.MAX_VALUE;
      Iterator var4 = this.s.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2s3zgb1srie5n","GqNNG4zRqULrhR387Q5iy70R56BtToy4ltqa/v6Bkds=",-3590303397691928648,7607037204717234055,3238801691872811086,8534408443442487134>()) {
         case -1395445397:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sus1om3zhi3e4","3/TbGL6Qy4dY9mvDGeyVE5aqZGjqRCO1m+w9kYb2iho=",3500228011505063825,-4753086513698088356,-4863871474926345939,4331471995315955204>()) {
                  case 1077656512:
                     com.yiyiaddon.e.c.d.d var5 = (com.yiyiaddon.e.c.d.d)var4.next();
                     double var6 = this.b(var5.a());
                     if (var6 < var2) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s34b9h9j4qxhoa","3O2hrFzB7GnmhdAbZzPVuaMzYdDwSwgciWeGKbnR0r4=",2720253399292277492,5711679502908477939,-605725493490313636,-516485370827077585>()) {
                           case -502472180:
                              var2 = var6;
                              var1 = var5;
                              switch ((int)com.yiyiaddon.m.b.a<"s2kyjc68fc1kw2","Dly5kzVJpenQo5chb9lTGBKk/8Qr4h+Gmy7ftLAn0uU=",-5300480557122465255,3396250249142639791,182624824999507556,-6336278726540368651>()) {
                                 case -248729030:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"szyb5iby5p3j9","6ZfJpAirWQ1uBlWQxu5v+ZK01maLBDcovYTyK7JTwAM=",-3530206211032142748,8682852814339148303,982874277970842296,5198158743148580414>()) {
                        case -821035677:
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

   private double b(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      double var3 = var1.getX() + 0.5 - var2.player.getX();
      double var5 = var1.getY() + 0.5 - var2.player.getEyeY();
      double var7 = var1.getZ() + 0.5 - var2.player.getZ();
      return var3 * var3 + var5 * var5 + var7 * var7;
   }
}
