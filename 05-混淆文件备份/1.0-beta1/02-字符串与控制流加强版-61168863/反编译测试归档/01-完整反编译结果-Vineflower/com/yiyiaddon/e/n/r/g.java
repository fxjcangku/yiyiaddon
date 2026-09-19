package com.yiyiaddon.e.n.r;

import com.yiyiaddon.e.n.i.l;
import java.util.Iterator;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class g {
   private static final Logger q = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s3e6jbyyn1q6xs","tTBqgz3pYXbFektKMWjchde1DX7qo1eNiI1ugEpiGrJRrsZCis7AwtEdSMjdSQpxVU4nTDUJo/vyJkZbPNY=",603108603040957985,1950478485963061776,8875152901291350836,-2819546251693874384>()
   );
   private final b i;

   g(b var1) {
      this.i = var1;
   }

   boolean ey() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sh70kfztz09pf","1mlN8vAlnMcxA5jzinroIinVpf3Zs0cDICXky4y1OYc=",117752972017407607,4824217996139425185,-5746794614886043877,2498302392359173360>()) {
            case -1627533249:
               return true;
            default:
               throw null;
         }
      } else {
         switch (this.i.a) {
            case HARVEST:
               if (this.i.J == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2oq46x7vwvs0v","wQivkVrjZ5PqhoierGdKHOQR954wH/Gb6jGIMC621IU=",-9075358678443268923,-27419253397362842,6852048894748493229,5079439811501673920>()) {
                     case -1329587123:
                        switch ((int)com.yiyiaddon.m.b.a<"s11915knkkpii","U8DAfaBRdbU0W7ZORxwYV2oMTNxcd4oszv7xMLKO9R4=",-2119426998689944779,-5192334337997134861,23981869423022453,-2422110851733421781>()) {
                           case 1591846271:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  com.yiyiaddon.e.n.j.b.a var10 = com.yiyiaddon.e.n.j.b.a(this.i.J, this.i.a);
                  if (this.i.a != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s13vmaklxtmime","79qfjbrKlp1kxBat4It5TVuHsWR4eMN9rj2AkC3TFcU=",-4564996040568034712,379373871850304699,704839771556808704,7050810228660375601>()) {
                        case 1909343302:
                           if (this.i.a.c().a() == com.yiyiaddon.e.n.j.d.SPECIAL) {
                              switch ((int)com.yiyiaddon.m.b.a<"swq90r7kfh7hu","svzSxVKgEIpnYzqJtRFvLIBu/bLid3J3Fg7KeS62UwA=",8704105783185622514,-4462382428864554105,27589696198709669,5234071764594631125>()) {
                                 case -192689354:
                                    if (var10.a() != com.yiyiaddon.e.n.j.d.SPECIAL) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2fjze2insrrlg","NgVtCU8Ze7BSnV7J0D5qm6HBnxIztyxoimdbeYPNfkA=",-3329893499031342941,1818651843635283766,-4814127874093253085,2338843332952690802>()) {
                                          case 259344120:
                                             if (this.C(this.i.J)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s26jt7gefwywu7","IYTjsMMl7TjUqGTC13Ft3LNt5XqulyZzPMKjJkPTC3U=",1142601721655888147,-7339028697232135401,-8047981694969637382,4115286070607547501>()) {
                                                   case -1130900735:
                                                      switch ((int)com.yiyiaddon.m.b.a<"sovyiegurmqdk","dh111tCrvL0A1Tvr7280SurFcAvVmS0YI30xkhaMcr0=",7869035154189020459,-8092521767197482978,2383358241532061974,-8943681680138758297>()) {
                                                         case -1516318153:
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

                                    switch ((int)com.yiyiaddon.m.b.a<"s1oqpv8c50tt06","XVqd5iHe/+NRcel0fa9BMciTHDfDViXKHI9dhqaoaYk=",-8793287682960741291,3625323660989931463,-6598693844597366951,-4786021028177351731>()) {
                                       case 641082627:
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

                  l var16;
                  if (this.i.a == null) {
                     label365:
                     switch ((int)com.yiyiaddon.m.b.a<"s1gvzed5lpue0a","V35y602eqdGrH7x//2MZhRW0UXr3CQK8fq8hVcsu18I=",8979328954658019067,-777448596402782765,-8446515096329728663,3108490085307912981>()) {
                        case -317837098:
                           var16 = null;
                           switch ((int)com.yiyiaddon.m.b.a<"s19y66jstqn4e6","qP+2dKOSPDHTzCeR/EpjBViEfVlKf0OkVx26Yp2i0uw=",-2275821105319236139,6590792038709853013,-6766814322601655714,1464918637624148163>()) {
                              case 1542737238:
                                 break label365;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var16 = this.i.c.apply(this.i.a.c().dk());
                     switch ((int)com.yiyiaddon.m.b.a<"s2xplye8599g29","u5WJk/LMGcWkxpl0bcpPfXmNPbkTFL7PGw2eImcZd9E=",1349934799870084552,114255882818121693,-2530226804778241101,-2221089967396082532>()) {
                        case 698025037:
                           break;
                        default:
                           throw null;
                     }
                  }

                  l var11 = var16;
                  if (var11 != null) {
                     label361:
                     switch ((int)com.yiyiaddon.m.b.a<"s2vp67on1ad9ub","TreBkev+2fBNg1K3ADDcR9FoKeZYJjaKF5EJcwU0H50=",4239475366753234826,-3814942214007041245,5269927796527121551,-9160429158871363088>()) {
                        case 970644430:
                           if (var11.dh()) {
                              String var12 = this.i.a.c().dk();
                              if (var11.a() == com.yiyiaddon.e.n.i.g.ONE_SHOT) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sxqomoexc3b8n","ca2avC0hcTUvzGUou1NSFrnE88QaGdG11ffXN8zkmIc=",2923125619992954911,2225603211736441623,-6327695221459482147,325089474132073028>()) {
                                    case 1055226270:
                                       label393: {
                                          if (var12 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1a7khiw7vyffl","eHkhY+LA/dQBpBFtv7g8lMkEGqW3qzCuLqMP8LxNaeA=",8296001787589194890,2844160560228279949,6498673681466334923,6465423083841776333>()) {
                                                case -2043488471:
                                                   if (var12.equals(var10.dk())) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s25bdxfvgc6uxf","OTWr4bC601S8qT51zv9QB4mJrOzNxf/odno81WSycDs=",3741979793210291513,-1199804946218461808,6086196666572076328,6011853755706800095>()) {
                                                         case 886437793:
                                                            var17 = true;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1socgecwhzmvw","OpHxFuOxHRFh3LJZ39j99kHpovY46zFec2gSpYPdE3o=",5192766103256742469,960766300635778042,-763083304461880623,-1566134144387290858>()) {
                                                               case -42199043:
                                                                  break label393;
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

                                          var17 = false;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3sdp6tmrq3ocn","nsr2f06AyrE5t5kwRokf0tCX5tsh7dXI103hGhJZonM=",-4301184387681768484,-8238994141795536328,6839577932956909618,-90021495113680485>()) {
                                             case 1036049368:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       boolean var13 = var17;
                                       if (!var13) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1kwfj641i9oxk","DCkt2xlJXx6q4gYVNAz652g6Qr5qMv0CaiZ8n45Cmvc=",-3565175989644045237,-7181038833069936475,-8393024790129467817,6577399951467612584>()) {
                                             case -236865255:
                                                if (this.C(this.i.J)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sv3itk5i2p52n","cakZozaOt6/y0kAuN2AYsNqEX0K1/KXyIKj8v6Vjbbs=",7616430206811199828,192385476897474119,-230721661410503273,1104793832505516120>()) {
                                                      case -1667293305:
                                                         switch ((int)com.yiyiaddon.m.b.a<"srvfpxauv0os0","X+BrpemmEvIay/9bCIcdQ/VNj+7Z2nUvspLJGia4WxM=",3769262824879964906,-8302613997455989472,223684922883854532,407118161649049378>()) {
                                                            case 952902602:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s21f4a9qzy7lk7","PJ6sS8zVYYFqQYKEXYY83bgX4k4rDU6FcvBX/2WCwbk=",8155692915691680810,3496860967503214231,4878511401967763434,-635838787947576344>()) {
                                          case -16592522:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (var10.a() == com.yiyiaddon.e.n.j.d.GROWING) {
                                    switch ((int)com.yiyiaddon.m.b.a<"ss2bleaqar1wa","gYB5ooUvlpkv23sPH/unfkfWJX6FtZ0tK4fIgPuTRRQ=",8371074095272116012,1857717410431110274,-8051207197810936789,-3513859671628784642>()) {
                                       case 1261348500:
                                          if (var12 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3otlo9p1oo89d","uxnXaHCKDr7r+mgh8T0EQHJPlZCfH3w/3QW2ibLPn6g=",8276396747633958469,8047774304795485701,-4867858204297180480,-1422929479858471877>()) {
                                                case -260862940:
                                                   if (var12.equals(var10.dk())) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1kmz5egu99u55","vwWeVgg9sCxZU9UGBLMWNY3QCdEdW//wzvXFeIDyYhc=",-9102277942788108619,620081728068970304,-1651746238179848080,6429391197187342634>()) {
                                                         case 1302110007:
                                                            if (var11.dL() != null) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s36eneiba61767","jnuuAFHx3VUkFAUPmOUVCNa6MikkM/FhCFRbUSjh23E=",-8894459411525508824,-2772207013563949039,-4746641580507627757,-566570951448084489>()) {
                                                                  case 464563579:
                                                                     if (var11.dL().equals(var10.dl())) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2c9w3znj4dlvt","r+MSwZ7zkNXjEDfkp0WJ5fu78Ab/D2W8L0KMcFh+SRA=",-5292885859534571885,4089236896028246699,-655754716496386590,8702555870720447480>()) {
                                                                           case 1195959395:
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s2iu6h9gegdorb","l/TE9Kdn5QwnIAYVTO+hNHC73a/j4NUIKmpYfHv8+qc=",7346674887828392852,1789421334648270035,276791393643217933,869469107355788843>()) {
                                                                                 case -2035635390:
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

                                 switch ((int)com.yiyiaddon.m.b.a<"syeh388e059pn","hDLYlRfAJ/EIYrIUqcFd7oZdt60TNsBwfIZUAYK8T8U=",-4593643921700053437,-706822950471317513,-3823837458588862882,-6187717370673974154>()) {
                                    case 1285896991:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2lia5dquxgkqo","cTfV4IafPi6KsGaFNOV0pvf9wvOd4juasN4gUb/AA9c=",9094631116931809627,430130032789038098,899630269342994312,-5709563509808322270>()) {
                              case 1851016646:
                                 break label361;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s25058ljjuyeg2","cGdAnSLYeMTX1Plcd8kCE9mEo7dBQbJ0HibT+lgnZe4=",-4496115826274272505,-4614894687743335978,-5392800769736465647,-4154627109689827495>()) {
                     case 960777408:
                        return false;
                     default:
                        throw null;
                  }
               }
            case LEARN_HARVEST:
               switch ((int)com.yiyiaddon.m.b.a<"s342qadk7n13cl","UNwDMGwtkAXzKrnhzfL/7yhwYKtcwwssgPm+urUo+AE=",3446102319104795954,-284630732384918492,-518793290557736753,5157191535411846901>()) {
                  case -631429538:
                     return false;
                  default:
                     throw null;
               }
            case CLEAR_DEAD:
            case CLEAR_MISMATCH:
            case CLEAR_JUNK:
               if (this.i.J == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s36p8dah0alt6w","O0+PwPMZhxRE0Lrq9D+D3zeTJaDZ/OkjK55ONZ82RIo=",2345347238134489342,-1594863619592069770,-8735088096627211610,-85923960822957321>()) {
                     case -419951250:
                        switch ((int)com.yiyiaddon.m.b.a<"s25dm2gol7wb9f","GXvn+RWYl+dmBlEbNhdi6UEEMN9OuQK3UrfALCseFno=",8835321687795933612,-1969106210361476878,1625841831727095046,-3306758876451556312>()) {
                           case 1137732809:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  com.yiyiaddon.e.n.j.b.a var9 = com.yiyiaddon.e.n.j.b.a(var1.level.getBlockState(this.i.J.above()), this.i.a);
                  if (var9.a() == com.yiyiaddon.e.n.j.d.EMPTY) {
                     switch ((int)com.yiyiaddon.m.b.a<"smqwsvdyf0hqa","0b1KyxxjmZPY3W1fuL6jG8TTT85jSrV7QxvvdslRwWE=",-4664083520109139007,6341084204360524278,-8063342794379715908,5616232659038221528>()) {
                        case 424922943:
                           if (this.C(this.i.J)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3c83l9ocmd3we","s2Qu4gZ8FotWdueN9S5fNMGRnQirpP60ZqgssfE2iig=",1516872866369479336,-5635462642817018985,-795751167502292130,-8571745127619569866>()) {
                                 case 1416187082:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3gsbjmi2agiwv","dCMEPEUMNj6wDeGmFdFAOG8w1iYpIUQuBd+UFWBQaAw=",-4616831605895542105,-7506799242543273202,-1621051771061936360,-7810005513770421423>()) {
                                       case 2091091855:
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

                  switch ((int)com.yiyiaddon.m.b.a<"s1qcdowb2jh26q","nPDCShOu1rp8qEFwXtdZU71LkScpl2KYClCNAzsrUi8=",8344407118184060925,-8538482655798656903,-5501116603963010398,8302742147590211886>()) {
                     case -1503682117:
                        return false;
                     default:
                        throw null;
                  }
               }
            case WATER:
               if (this.i.J == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s25sn0e7gfi1wj","rzS2SrnpAjkU0qB1gIuHIUddH+svXg2PjZabWBjVlEo=",7915353638753122771,8440647669920164621,6827958455513841514,-3071837608432983293>()) {
                     case 1037302880:
                        switch ((int)com.yiyiaddon.m.b.a<"s1hj81nepc65mo","Gb63ccE13sx7A/pfHwpYZxOtLmpziX2ZZctr+aH5leA=",6914367618376766363,-4657671045556639239,-9130516413317522481,-1542365094820176699>()) {
                           case -333689080:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else if (com.yiyiaddon.e.n.j.b.a(var1.level.getBlockState(this.i.J)) == com.yiyiaddon.e.n.j.f.WET) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2q23tq1euiekv","Cq3aWN2/zW0ThUmYAqiB/bF8QibSOrSqoHVWrEAK8DI=",2668095754340259637,36976705950860770,-2186107400589206945,2166647743028885929>()) {
                     case -1716805624:
                        switch ((int)com.yiyiaddon.m.b.a<"s2plqarpngicrp","mt2iMOiPTkplpKwiN38EYlJRLqGHSzJksu4uBL7OE3c=",6999879730987075839,2441145910947498372,1278552250881053959,-2983783815363085181>()) {
                           case -1855169548:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"seuoum16cfnix","zJYW8U9lF3F2rlav13jF1gIaPASWq7AUa/PSlrFPuIg=",-1206716275647743653,7329335943718610948,-4452748418992076632,2166424984213365983>()) {
                     case 2134663265:
                        return false;
                     default:
                        throw null;
                  }
               }
            case PLANT:
               if (this.i.J == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1fbf4rr1t4ykp","fuqwSQlzvaK4dJe0YSAdmneicGLOxUeoiLx/0cubo5w=",-6719493780559086158,-1958186840289677199,-8815691468857480080,-5968120912406244967>()) {
                     case -1885478471:
                        switch ((int)com.yiyiaddon.m.b.a<"s3uspy5qumzz4t","6rVPD7q68BkkL5CO4sFr9RHdakfUkFshUD0KtLdCzrw=",-2064570144300143727,-781849141732559595,-2068149714969640456,8396919548037296932>()) {
                           case 1894791490:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  com.yiyiaddon.e.n.j.b.a var8 = com.yiyiaddon.e.n.j.b.a(var1.level.getBlockState(this.i.J.above()), this.i.a);
                  if (var8.a() != com.yiyiaddon.e.n.j.d.EMPTY) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1pukel1zd1l23","4ubGSHSDAR+/uibZnXQ8kku/AqiIhH+3GAOFWHuC5o4=",632682414433700090,7239686901138217974,1977083730770755750,5021180405253473971>()) {
                        case 1355212190:
                           if (var8.a() != com.yiyiaddon.e.n.j.d.UNKNOWN) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3pxfhrpliooit","dZxxqwfgN6HspmcKGxexIWvq7UxouGpiEIncfQuV6BU=",3974890825503197353,-592833174262063872,-7255872617393491096,-1253686672620556274>()) {
                                 case 728848924:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1v63onvlxuyu6","zWIv3H+3fl/IP+1+1JxLBR+7wNbXYoe7JSNGsCBug3U=",4129442881003128020,-416095354303104905,-4602543630392139397,-1213739737190030933>()) {
                                       case -1657676466:
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

                  switch ((int)com.yiyiaddon.m.b.a<"s2amzc4caffkn","G4Us48mKnOtzRE1JiJv/uCmp3EO8lkqOWkJUYyNqMHM=",8695990713100556835,-6421266856751374060,3045039537640206708,258816938316682375>()) {
                     case -107925842:
                        return false;
                     default:
                        throw null;
                  }
               }
            case FERTILIZE:
               switch ((int)com.yiyiaddon.m.b.a<"s11x7o2u154xv","VrdNhdvuN8QcK2eRgLcerrQNSbtKfi7n50pgqkCmQFU=",-3633564311026539260,-4219406690636828167,-346968705235552967,2789562924237236103>()) {
                  case -666471616:
                     return true;
                  default:
                     throw null;
               }
            case POTION:
               switch ((int)com.yiyiaddon.m.b.a<"s3e1ll5knr7693","XQFstfDLEm02GG3L/uE3pzp8BTh5gFu64C494OWLcso=",5456451356614051666,601451883337821726,-8405995908013721898,-5409746491837711472>()) {
                  case -1115332886:
                     return true;
                  default:
                     throw null;
               }
            case COLLECT:
               if (!this.i.a.dU()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s615oe3g79emf","zOm/Ubi9d4kYj4OH7I31X2rVMfnwZH88gLxMRKHgdCg=",3450834122419528522,-2449710139873961563,4942810881012576241,6538231858267430389>()) {
                     case -426901204:
                        switch ((int)com.yiyiaddon.m.b.a<"s1dmtb4j0tiguf","wRWeuf3pRT8LVdcdSk2Gh2hWcV4LxhEx/OkIeKYabek=",-8416612179463451220,9197469437836283002,-6033343290114829822,-4424339752342896785>()) {
                           case 2079692221:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2qxwch16nchxs","FjuPgn0Rd2cH52r2hMMljJJeosJtQmEa/EbAnzaYtXE=",7703047442210663510,7186655153380737812,6159932769310542434,-7734870369146542139>()) {
                     case 858610032:
                        return false;
                     default:
                        throw null;
                  }
               }
            case REFILL:
               switch ((int)com.yiyiaddon.m.b.a<"s3h1m26vzvhcwo","8Xj984Nr0sKP9qM+0zCVWsf8qCL/fd7PGqH/dyq1qVk=",-4670495677308834927,-3899197692514862859,3627904764297327819,6634093119891626188>()) {
                  case 1051173117:
                     return false;
                  default:
                     throw null;
               }
            case SPRINKLER_CHECK:
               Integer var3;
               Integer var7;
               boolean var14;
               label450: {
                  var7 = this.i.b;
                  var3 = this.i.a.D();
                  if (var7 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s38ri59ztmwbqc","9mjwayi70Ms89yEnqAF3ljk8ltgxrVDQgLWZ9ibSq74=",-1546070840359056223,1720026054920651176,-1415875920031784858,-5753558512168307996>()) {
                        case 338147344:
                           if (var3 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2ap6txavyudlx","nBZCzwNqv3aWynOFnO4A4kbUBc+oJTW19PZ3WQV5u9k=",5898780640444658923,-7380254943659027461,82620785093423837,1108265223014032646>()) {
                                 case -1646928533:
                                    var14 = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ph99u3s8zeax","5ebKOLHn4lI+jsiJL9UNuy6y2/NpAgxH95hBnzfrYSo=",-6401781017898422785,-4384159031324668615,-6565316279980236912,-479206946154024951>()) {
                                       case 1738059377:
                                          break label450;
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

                  var14 = false;
                  switch ((int)com.yiyiaddon.m.b.a<"s3dl2vtvvhuobi","5Iyw9Rxc7Jnant1swzvlgof3+K3QpGbRJT9YgdpJTFc=",715809471237104778,-4606083792763163419,-7866017494468941022,-992372111127221595>()) {
                     case -195645324:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var4 = var14;
               if (var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"sarwk5wxxn0jo","TBBC7k/RtYc1PRqXvRfJJmXYJh0jiy8Wt6WQWcJta3k=",-6514278712506154409,6314747545301504903,2978140826385022188,8445817869687071270>()) {
                     case -601926017:
                        if (var3 < var7) {
                           switch ((int)com.yiyiaddon.m.b.a<"s9bk6r81s8bj0","CmiRN2ENfTuxaq3t437TQeO1ylm+0BfQDV8ourSa8bE=",-8483644943349786740,407651610106795545,-185501074088024238,4159702577425640119>()) {
                              case 1005813359:
                                 if (this.i.nH < 3) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3e6dno923208y","C1IJ9+LO2I81wB7GRM9Brh5hHG6eL2k++MVTrvh/Ts0=",899451806983775182,1712071259685446299,-7967082030450974540,-2572094627820409338>()) {
                                       case 1730626206:
                                          this.i.v(this.i.a.y());
                                          switch ((int)com.yiyiaddon.m.b.a<"ss1n6tgb0ge0i","jkRBFgi2hwTDa7bIN8aBUeDz+ETJFhY8MSQ/9OV19ZA=",789257028126880655,7502442038897059750,-2054213451991496737,-6528534451730780728>()) {
                                             case -979281207:
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
                        break;
                     default:
                        throw null;
                  }
               }

               BlockPos var5;
               label437: {
                  var5 = this.i.a.y();
                  if (var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1hdtagh28qvw0","V39BQoQA5hld6T0nVJf5RDNBXg1nRjicqFRY6EeBAzE=",-2832819881198205490,5899911806733722592,5001771873348034113,-8088183937581559921>()) {
                        case 1579404497:
                           if (var3 >= var7) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxy1ao6zw7y1f","U1DpazrZR9W5/np8GCxURyA66X+EXvkjTgxjSWmby6I=",6470849817264560086,648666489836839382,-2439743623240080088,-5233489527230938857>()) {
                                 case -1002940105:
                                    var14 = true;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ktshnc5uuzby","AIIeE5XRa0GiP5leEpi0fR6Iw6ku0z/sVDtsNSrf81o=",5815143335478930649,-7408552509645760557,-6932936429513256250,-6325661376395259469>()) {
                                       case 936996948:
                                          break label437;
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

                  var14 = false;
                  switch ((int)com.yiyiaddon.m.b.a<"s280zympipjwh5","b/bi0suxLjjDbUEYZWcbktDze5Fk56cLVFuW5eLSlr0=",-6871622254411512629,5572005588821694972,3483059807743989360,437130341901632625>()) {
                     case 1683120957:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var6 = var14;
               this.i.a.a(var5, var6);
               if (var6) {
                  label245:
                  switch ((int)com.yiyiaddon.m.b.a<"s2arpz2snounr2","j6eR6O9S60xXSmsgOL9ZSyhcPdguJYurYii1Usu9Ufo=",-4608207749244274054,2159429906415634059,7492549708015348894,-4024524050421327857>()) {
                     case -1843275610:
                        this.i.u(var5);
                        switch ((int)com.yiyiaddon.m.b.a<"s2bz7db41sh7bk","TJcmyOqOaqX7V/kGbofNmFGp+7VMeSFDnP6XKJtlFro=",4682621893125363774,7179161415675110434,-1961892729161963445,8234531365859118233>()) {
                           case 716859226:
                              break label245;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.i.nQ++;
               if (this.i.nQ >= this.i.a.bj().size()) {
                  label240:
                  switch ((int)com.yiyiaddon.m.b.a<"s3hblvggdiii8f","HoG5hObRK8hiEdz3IVXbJKKxDSBwL8TFS1ugVnJST+U=",-7552318683234522431,-1840468689412720573,2619540371734774103,-2453484174268343282>()) {
                     case 277574786:
                        this.i.nQ = 0;
                        this.i.P = System.currentTimeMillis() + this.i.lE * 50L;
                        this.i.K = null;
                        this.i.a.D(this.i.a.bj().size());
                        switch ((int)com.yiyiaddon.m.b.a<"s2cywonzobl65m","JfZRyUjki+UbtaM0vLiHu+0CPT2CKpkaBKwihvYOKLc=",-5818226036019812022,5148622986436307478,5639933945015260518,6027698624827432687>()) {
                           case 975871701:
                              break label240;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2af966z6lt4sh","alt8D3UQ6HRa+DXIxHjyVRg4XZq1f/2+ybYiirClmRY=",-5727300275784093656,9212919288087368722,6905616372176850212,-3757782718038694125>()) {
                  case 252364983:
                     return true;
                  default:
                     throw null;
               }
            case SPRINKLER_REFILL:
               switch ((int)com.yiyiaddon.m.b.a<"s1udjb9qiuylsf","SMRR5WLoUMS8LNKJH+aBKG+2TZSmwrn2CmMcgpzaEX8=",-4711946645062330744,8550010369570872293,-1058880822933000375,4903933924493516211>()) {
                  case 1500789731:
                     return true;
                  default:
                     throw null;
               }
            case RESTOCK:
               switch ((int)com.yiyiaddon.m.b.a<"spfgfmduubcou","c96J3dvdP1Ij3hS+sUaaZbuuBe91ohqaYLdTrithdy4=",4133394102334855229,6833457589883746631,311157131515275154,4589642027408621893>()) {
                  case -203843252:
                     return true;
                  default:
                     throw null;
               }
            case SEED_RETURN:
               switch ((int)com.yiyiaddon.m.b.a<"sdwqys0d8cbsx","ZFu6rdfGBmIitMT91Osf3KrD8DOn9FgqlI83Gg5HyRI=",2684142599442122429,7281024740828751706,-7742160512752783577,7889818752518913351>()) {
                  case -1855220630:
                     return true;
                  default:
                     throw null;
               }
            case UNLOAD:
               if (!this.i.dV) {
                  label295:
                  switch ((int)com.yiyiaddon.m.b.a<"s26i966jyx2iv","AxrbFHaw6iKEFNmDs4vH0DIYCAxB2erhAsvmAWZMqA4=",-9135458375072515184,503563163374429901,-4882547420397104495,2211742453587068326>()) {
                     case 622470732:
                        if (!this.i.a.dO()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3j4plyesg6zfd","EJXAB+a4p9VkA0w7JQ7h+/Ar5SRzkVui7JS7YlGTlKk=",-2656439698916003606,2176455560851332136,-6188938626843237029,-4349253329771898848>()) {
                              case 1285405779:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s32s1foxy1cxxc","2khfZ3qH78fjwzdvTBZHti82CLM2peCKxPCkwdblgeM=",3051792155098442469,7130314567647581035,5791266304885706380,8066292478301568063>()) {
                           case -1046090133:
                              break label295;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"sn1xajnhqaqe4","d8WzRKP/LplpI2KER3d5JPhHzhLBAUnfPCxxHSF579w=",-1660319300455562001,5855170203156737543,-1443692936895380039,-6937949536200966619>()) {
                  case -1831854374:
                     return true;
                  default:
                     throw null;
               }
            case RETURN_CENTER:
               BlockPos var2 = this.i.a.z();
               if (var2 != null) {
                  label319:
                  switch ((int)com.yiyiaddon.m.b.a<"s1i1kfs0fg9xff","NN9mhBUlFoMvdgBgAdh4Uc8DwrSv9HfO73PGhWnaI9o=",-4776253318292910398,-9016156257644548276,-875439502087208406,1727496816647147395>()) {
                     case 110124262:
                        if (!this.i.b.a(var2, 0.85)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1edwr67mhg6bd","eKHikZwrOL6kkrNDrKDJq54TepW8s0czdvMlbF3lfZ0=",8965244865206502706,5471311470990295964,2306295204259527361,4178893047254555154>()) {
                              case 536132879:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1qfny0oxldosg","9WjEiTumVwYBCyJvC9umwkpnUgaxccoloc/JCpIVHUE=",4479467894667973907,-257107910967191539,-5495121422933731613,-100445573210552653>()) {
                           case 2013779887:
                              break label319;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1jwp69foezbcv","naCSNiYOy5aEQPGDUk0mFK2322+POvGTGKB/VEzF/Hs=",-6066674888626636333,3501987869319730106,8479065335870114086,7243836651700547878>()) {
                  case -1123892864:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }

   int cO() {
      switch (this.i.a) {
         case LEARN_HARVEST:
            switch ((int)com.yiyiaddon.m.b.a<"s19du4lbsxmwwx","DmhDWvIe1P+dcNhihU7dba4gAw+ZWcFv3IZhxhySryk=",4927702648971308792,8924066646294720493,4427728091243546781,2439922244270616449>()) {
               case 515760079:
                  return 20;
               default:
                  throw null;
            }
         case CLEAR_DEAD:
         case CLEAR_MISMATCH:
         case CLEAR_JUNK:
         case FERTILIZE:
         case POTION:
         default:
            switch ((int)com.yiyiaddon.m.b.a<"s63ul9u0gqwxo","Dj08Akf56DfBGP6LwmUDW/XkqmYnLcRCs5jpY9N4NuM=",-1563031605949119150,-2155843964574534676,5597829067038444816,1711478650522763674>()) {
               case -1585504953:
                  return 8;
               default:
                  throw null;
            }
         case WATER:
            switch ((int)com.yiyiaddon.m.b.a<"s2a500h34iq3ea","0Wi0XEDLSRdS+/u+LKnjkvWKD/I375limStHmyQYv5Y=",6941951021940687782,6076896741172266931,-4121332924202480891,2239050253899650363>()) {
               case -187386523:
                  return 12;
               default:
                  throw null;
            }
         case PLANT:
            switch ((int)com.yiyiaddon.m.b.a<"s16ms8rcpqsi5e","CBH/JUmDnHkAiSVUNHySsHNeykZIFGHjV6ciJLvPbMk=",2201423270948874098,-234117975399680606,-7503898406591987471,-5461233479896920293>()) {
               case -2020648043:
                  return 4;
               default:
                  throw null;
            }
         case COLLECT:
            switch ((int)com.yiyiaddon.m.b.a<"s2gbqe1ebcdrqs","EgiPwWMqMthIzQ3TMlIgJUUSumRPM6RfqrGi+i5lGcE=",-3824271899905341972,3870642252053431529,8379767604220761661,2761955505380038121>()) {
               case 1906869371:
                  return 4;
               default:
                  throw null;
            }
         case REFILL:
            switch ((int)com.yiyiaddon.m.b.a<"s3cx5nz2f1wc2q","H7+SZb7HQoX2DikCyirWcHkvwU/2lGjcLn1rxDGKUkQ=",2046909879576606098,5702844527269729424,-2526094755938312242,4174841372146547167>()) {
               case -2087477655:
                  return 4;
               default:
                  throw null;
            }
      }
   }

   void hz() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1c5dec242b0mc","69oiCPbV2+y/EaF5yLhRGQBd+nnpv+SCDo3Zis7d3Hs=",-1612610159821307798,4491753632971448522,320281507483892208,6451923063864293845>()) {
            case -353200271:
               if (this.i.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3vy2qfr9lthne","Ndd6JoJ8nDmW189Esx1F8m5dAgZvb0eDACfNqhQVj+g=",-3101778492394004110,-3398796520192813809,-192838007820004624,-4941180262833807042>()) {
                     case -1335794698:
                        if (this.i.J != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s38txyxw8d1mgl","52bVbxAWuNaHUxMd80T1z7D5lwS1XnbN8Poljsmd5mI=",-3209480311056875576,7527877238050980343,-8795335373681798532,6598455893914390478>()) {
                              case 287780424:
                                 if (this.i.uv != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1m397pls57z2q","mJYcYVPAeOMJCHJZObG6+36UYhy2hM94RTBQDeQs2xQ=",528533474440540169,-5449047336648383290,-5954011923310544731,-5985839491582104364>()) {
                                       case -789028773:
                                          if (this.i.uw != null) {
                                             com.yiyiaddon.e.n.j.b.a var2;
                                             boolean var3;
                                             int var4;
                                             int var5;
                                             boolean var10000;
                                             label231: {
                                                com.yiyiaddon.e.n.j.g.s(this.i.J);
                                                var2 = com.yiyiaddon.e.n.j.b.a(this.i.J, this.i.a);
                                                var3 = this.C(this.i.J);
                                                var4 = this.i.a.a(this.i.a) + this.i.a.b(this.i.a);
                                                var5 = this.a(this.i.a, this.i.J.above());
                                                if (var4 <= this.i.nS) {
                                                   label199:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2wyut0n125y8l","kodxWcmWTR3yfY2Z1TmN5Nie86WRtINU/mxhrINVBAg=",2275869037294579984,-3474170179982615422,-934503504465355098,8896062338872700088>()) {
                                                      case 2113425846:
                                                         if (var5 <= this.i.nT) {
                                                            var10000 = false;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s22jsgpvyd0oke","PSkhC6ZPW06Iy9AO/Zci3scd7p8V4E3BgTMuxi934Kw=",128668775246929878,6694433496158287935,-2116870683597838730,9215881441261264681>()) {
                                                               case -766786669:
                                                                  break label231;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s3j0ha1jvlxug5","ETACR50a8PqTFHUa6CsZcwg1aZ1TUqVl7DGKu1ZGFFQ=",-6441534620587605378,-9020117081668729605,5039636452747463369,-1568063020774223853>()) {
                                                            case -362119377:
                                                               break label199;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var10000 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s6nnaoijictqv","lPLXUQ9/uadc7i7hmwPY+uVxAIj8PKNnvV/gb5Rx8rE=",-4997094990623132131,9063332821914581326,-2749849081158605507,-7031113819195160946>()) {
                                                   case -799827836:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             boolean var6;
                                             com.yiyiaddon.e.n.i.g var8;
                                             String var9;
                                             boolean var10;
                                             label224: {
                                                var6 = var10000;
                                                boolean var7 = this.i.uv.equals(var2.dk());
                                                var8 = com.yiyiaddon.e.n.i.g.UNKNOWN;
                                                var9 = null;
                                                var10 = false;
                                                if (var2.a() != com.yiyiaddon.e.n.j.d.EMPTY) {
                                                   label191:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1g3winak0u2hf","Vi3JyymBD8VL/6KXWI/gW/ym4F11w4pnO3ZBeSA5y9w=",999350743913043700,-6042426088712989499,-1019218648240204072,-6245479632805280558>()) {
                                                      case -757845675:
                                                         if (var7) {
                                                            if (var2.dl() != null) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3snnvk54vac6m","/bMWwUyQ7/mKEM+UvCDzW4ALLit9P3Tg0bNzcyNT8nk=",-2255060070431857233,-2524110575133383889,-5087392753918114115,7886099022008883540>()) {
                                                                  case 348007865:
                                                                     if (!var2.dl().equals(this.i.uw)) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s186vb1o0ft30b","4jVTWCwyrf8uQElcBW9eWrqaV5W3RjPDt0C9BN5xC+E=",2228742534224541797,5906150130587270961,-4460749586623340182,6790695066300148937>()) {
                                                                           case -765135378:
                                                                              if (!Objects.equals(var2.dT(), this.i.ux)) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s1nyrdkdwb24hr","tFyOxXtbmHa69C66Xh/3zBghp93eSMBeH8d6HQt8bY8=",7140487141683321064,-3210933136668042105,-1584241422472997145,-2437353578053780472>()) {
                                                                                    case -651441122:
                                                                                       var8 = com.yiyiaddon.e.n.i.g.REGROW;
                                                                                       var9 = var2.dl();
                                                                                       var10 = true;
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s12103bn13zuhy","pWV3KpAO632XZdbRYzdwZklbYFzPoHnBxRYYD4msBgk=",-9048457893063870781,6373888484609192128,-1787214821899955375,-3570391497290960094>()) {
                                                                                          case 2042686052:
                                                                                             break label224;
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              }
                                                                              break label224;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }
                                                                     break label224;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break label224;
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s3s2rz8f05wh3y","JcUAN2oAAPcNyQ+qj0Pp551JjfbtmpHvL1uNh8WQZ08=",7827626506101131341,-6262676965556029388,-8177459404727316172,-1974884094411190219>()) {
                                                            case -577141048:
                                                               break label191;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var8 = com.yiyiaddon.e.n.i.g.ONE_SHOT;
                                                var10 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2ji60jyvm0yqj","Y0wh/ZobcSiiTMcHcr5KBf8GKvfBfekdLu6h+nQUiL8=",492457840895993820,1889980812907099600,2827625261031534306,1307737330214063492>()) {
                                                   case -690533461:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             label217: {
                                                if (var10) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s20ib0iq1x10y1","4VhQy8yfmtvhpXaOpWDGd/UGG1hBIK0/7q6pxxiC2cY=",8698205619510977348,5992763702798055736,8134890966064257928,-705108158197104529>()) {
                                                      case 657548160:
                                                         if (var3) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3vb4w6qf68op5","dJbp7OTYaasHvuLfCSZeGZO2gCRh+wVqxU4xdRnWYeM=",1802013655009980469,2788083891148351315,-62764806578369639,-2324927713873119861>()) {
                                                               case 1969799804:
                                                                  if (var6) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2ktnphdhmy3fd","aB5TQEBuX7TxVnlZKsnwfYFdHc1ohfgFliL65n3C5SE=",8814035182292379951,8255398389481915538,774171314919726589,8829422518448969508>()) {
                                                                        case 727630239:
                                                                           var10000 = true;
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s15267g41edky7","c1TruDMmCnfK7RHCR1o8tXoXbDwm6TnfjC4z9a6lpxM=",899495388055342354,930953477312558378,-8617928947445259307,8702463749362371961>()) {
                                                                              case -450636203:
                                                                                 break label217;
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

                                                var10000 = false;
                                                switch ((int)com.yiyiaddon.m.b.a<"su077gdnoif36","lpD5GFSZU3c03a25Bw7O4ugLsusUcK7g0lhIlCdKLaY=",-8730117560601558223,-5223273957547931995,-4635817008553322990,8381772649030439564>()) {
                                                   case 1367928258:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             boolean var11 = var10000;
                                             Logger var15 = q;
                                             String var10001 = (String)com.yiyiaddon.m.b.a<"s3d0s6nu1jtqmk","06Yo05Ans4p+Z8IduMPz3G9NDLdxRr1eEe4O+hH0qcgsj4Knt9H2Hcs9Tn7A6dW1j2y4VzXgMKLTirGG83me32OjcWTAjzUGSN3/yR7/h6V6VwsVZVVAMGSeo9VDjMB7AI6G0HCF9eeUJSNOLTQ+F3Qh3uBguPfCvklQ65vTWtDlunEa8zxH+OLV5lg+RkHdvY1Ln1T8K5ksgUrYWOl7jEa018hHZoPSJt9GpCpsl+T8yK4HXvJxuydqxt8BwRBLBLfRO0jB7CPYIWU1agE315eGfNS0OvrYnZVdhXhpheZ13ntsYeOB57VLbHo8v5EoSoRBIqE5CDC752GGl9x1kmVUYS0=",7742207566848706758,7160070668659965452,9084442797031877651,-7521011188318180702>();
                                             Object[] var10002 = new Object[]{
                                                this.i.uv,
                                                this.i.uw,
                                                this.i.ea,
                                                this.i.ux,
                                                this.i.nS,
                                                this.i.nT,
                                                var2.a(),
                                                var2.dk(),
                                                var2.dl(),
                                                var2.dT(),
                                                var3,
                                                var5,
                                                var4,
                                                null
                                             };
                                             String var10005;
                                             if (var11) {
                                                label150:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3i20i6j79y6tc","KiJx3QJSP1jsuPh4zv51mFD6F2/hP6JLwZlGZVALN8M=",-1341830803644166672,5685297202010154889,-4175163129177393983,4696016168355800363>()) {
                                                   case -1496182380:
                                                      if (var8 == com.yiyiaddon.e.n.i.g.ONE_SHOT) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3buwq9qf9j61p","eOYAzftLGGeZbz7bXp8zJ643acHAXB5hQMP8Q12QOfw=",310199500460257430,8493355939231844293,-7057269900145554796,-4455179811401115617>()) {
                                                            case 893192329:
                                                               var10005 = (String)com.yiyiaddon.m.b.a<"s1pqeo0dcypqzu","kAKaMzUbjcKgEF9VUoJTUGPaGaaEVSUtXSvnMrEZCVOuToY2g8G+53D4pwQ=",-757294782060192022,7587388437833762635,-8385439152194127180,1214524464426111492>();
                                                               switch ((int)com.yiyiaddon.m.b.a<"skt8zfqrjyc6g","RrJ7Aw/ALr0/6OAJO9ifM69KAxlci5zZvHJ5Bhd36Uk=",-6674211240275015361,4849434325029204466,3579953169607424445,-7460223661149548370>()) {
                                                                  case -1224331612:
                                                                     break label150;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         var10005 = (String)com.yiyiaddon.m.b.a<"s1mmjfg4d7ez17","CsP4h+BYczmzLZmSR1s8VhtVZwPff+dGKw9aL4V1hRpwEHk4KgrRvrTv",4611686829655425170,-3415725865432222226,6320519453790605612,3772866654093264645>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1by6i5wssqa7t","+szQF2p4dT6/SsLHpLch1pHOzENnrVderKEDVPfSmtE=",2692255744336111786,4626399916703044348,6255544216602729094,1760158609571626231>()) {
                                                            case -1901651757:
                                                               break label150;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                if (!this.i.ea) {
                                                   label161:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s162k4660onua7","vsQTpABdg+bMY1WcG0Gmjr55XjJSB41T32h3GGVpMks=",8302595510686486051,-111644945397812761,6296774834887801028,-6994148783008718564>()) {
                                                      case -102900467:
                                                         var10005 = (String)com.yiyiaddon.m.b.a<"shfvnvw3qeqzm","6dgfRB3N4ajZZE/CWe7/6ZBg0FVUvX/DaRKpUrZp5OM5V5GrFj1wwA==",1990709832552447605,9053978105152037400,2880272755298706246,574403457783335482>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s15b5pqlc8pr1j","Z33PNcKrmJ5XzMMJ4genYwaI31Nl4CepqjhyGPsgGa0=",-3596101623878968010,-1695147117772401617,-4844313236626305168,-3689821061091804205>()) {
                                                            case -393960984:
                                                               break label161;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else if (!var3) {
                                                   label158:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s6kn6qthzltk9","1jGa1w071NscjntXjkcS/UUEpTRwSNYLxbslA/RU9mI=",8036326829204801689,-2298990737295388679,-3457724735219282371,-1572902814413406373>()) {
                                                      case 1378068319:
                                                         var10005 = (String)com.yiyiaddon.m.b.a<"s147xesqp9hv7x","S9Azcs0dbd8jo/PjZEJJQsHMCeeJzXsfBcn146k7b+Zr9GWw",-139615045669030078,-3019778598453256195,1977360801413200516,724795347163244394>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3ojfdhqbremuf","fnlbGfTgNgCEvr65GNik3krstoWWMMyd5dOOgU9LsuQ=",6079090246867808230,8136628310145411203,3311571269899966554,3316032054892505105>()) {
                                                            case -531132341:
                                                               break label158;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else if (!var10) {
                                                   label155:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2xd72l9gp7dri","gHhPD9LhHz8ZC26dAElY8o4QCrWgDOYn0lr0QBPaeno=",-743313996206054018,-1632244188603155671,3901143214076334447,6841369718588336710>()) {
                                                      case -1631402120:
                                                         var10005 = (String)com.yiyiaddon.m.b.a<"scjjbfxkh7w49","jmADsWCHZkPKlGgbDzw3V5EGMt/WnSjyaTy5oabhU2agcMHcshTC0CL+",-4455347577419468484,6794621273582783549,6394374486717573302,269328029020309798>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2ma7u4rc6rsme","aHOQ6hf78AiwKQJwy8TbC4VJYxvWqaMuks2IOIcAPg0=",1074881892317522721,-547012169570811580,4640946607649042895,-3046369493852846548>()) {
                                                            case -1981950133:
                                                               break label155;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var10005 = (String)com.yiyiaddon.m.b.a<"s3qxon81kekqv5","c5AzfkFNMDIJwYSzBGEA6zYwN83lqTRuqWLAoFIqiawOk35692tDT7Vzywc=",-6087238904655578392,1232347418912175603,-7994053306467469988,-6746873300132535013>();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1bfp5rtci64or","HyGIrmXrUk93+YfU6lQS0W13Z8xqcKOW8ZTVG5WysBg=",3161871384937063949,2585919903107001865,7173588778609814396,2918791601733812746>()) {
                                                      case 140412915:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var10005 = var10005 + "";
                                                switch ((int)com.yiyiaddon.m.b.a<"s170ga0xnbbo6h","mpgEcMMPvtn4+J3vhVa0JMDVarl4xhLarAY3kgdQGIY=",-3601289183137529114,-3236015073085280325,4714227959682147037,5952891630421294130>()) {
                                                   case -1522356035:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var10002[13] = var10005;
                                             var15.info(var10001, var10002);
                                             if (var11) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2yjt1xfer8u5i","0waorZHhTkZIAnkPtB5KEgnH2/ipjsEFnE0ftiVAEJ8=",6625109615077791648,2498368284219628175,-8835928700509366308,981386709727931207>()) {
                                                   case -1806920966:
                                                      this.i.f.accept(new b.b(this.i.uv, this.i.uw, var8, var9));
                                                      this.i.ai.remove(b.p(this.i.uv, this.i.uw));
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2o5ee3nzkhi53","3G8pDV7PVX0Vum5hDdgtWfAB7d8GOPQ2Tnmgsbcz6WI=",-6996714142205148870,-4318466561542878790,7617038909038471101,-6736624817887084662>()) {
                                                         case 2028003146:
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                String var12;
                                                if (!this.i.ea) {
                                                   label129:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2oja1xi676hx","1HOeJuSXXA2D42bAVnesOIxWss2ZbZisaHO9/x0O9FU=",5555485603125997859,3169888009817036602,-2155152547140768825,-7551456275569635195>()) {
                                                      case 930633097:
                                                         var12 = (String)com.yiyiaddon.m.b.a<"s1hm0x6whxm1xt","RZwEnBLvAPn/pw2IdEvPw37a9X4R1fceqI9vHrA4RXwpk1oIkIm5iZl29umJIlp2",-6000521579284221397,-6358453509041900029,-1909596556029155425,5169680926462450739>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s219op87znwuml","PdoLOuQmaVnuUFipcUTsowDlZH5BWndQHxvhDuaB4Ck=",-3722628817576270132,1090384533255255516,7260851815189918354,8288109644062721089>()) {
                                                            case -860020442:
                                                               break label129;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else if (!var3) {
                                                   label126:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1j29t335i566n","zml/6XXGva8CmORfPcUrbzDIpUE9MjHpdtS9X9wVwYo=",1127220269410258368,3236761195163049979,-5740833476069398878,-6837114042508758430>()) {
                                                      case 323354510:
                                                         var12 = (String)com.yiyiaddon.m.b.a<"s3py3uvvoxwom","g3WqcHeFsOvBfs3PQFJCde6JFn0tFcfrpEIhseIFp//xyNcit5CgcLa8u4v5RdS5ZfIhn3Tx6CXdwhDfYBe6Cdh+Lh8=",4483149693676861274,-8285209815677904852,-7153443216424103305,2868877521933485523>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1wl9chtjmisgb","RjQcvQyIAwRQIqCIAfn1TOwI8FJTQJVbGXj6tF88T98=",819936512038438779,2916517328166283704,-865938134577559032,-6924845068741190408>()) {
                                                            case 13354282:
                                                               break label126;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else if (!var10) {
                                                   label132:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3jlew0wpmq0tq","vdv4R+tqc9X6wAeRcfqfyInpddsU9mFqepg4cBd/sic=",4178321956741769328,-2255562124536549127,-4723585838668341871,-3116262530348886226>()) {
                                                      case 1207506591:
                                                         var12 = (String)com.yiyiaddon.m.b.a<"s1rj3u8hzo60am","okxb2y5qmy1xvGJyNesmSe+VUHlQAiC8jPK/V4xw7i9wTtsCOI/F2HBYu0E3eIve6kVY6K5+gXk=",6690910104918364909,-7221277188575847973,2647369000847009332,-4410504084644755237>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2qdl3gjpc4yzc","PnPWfdMZ28bq5dv45zPKVmfTNbL3Gm7VDNqovRaih/k=",6250710642241386088,824456943531945290,8388083480104489271,-1536413571534702827>()) {
                                                            case -1106999312:
                                                               break label132;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var12 = (String)com.yiyiaddon.m.b.a<"sghf9qkovfy36","qiGeJZ82c3NJJBh6MrsmGkt3GwnCk6z8wSrBgR3W2Az41ppguq1uNSJDe4napHjKMbXN+Gp9i+I=",7664167000091478052,-1228334592620892082,-2660136828495905210,3417894346224865390>();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s32erwaa7ordis","nDgkExLdp9ao9tLK6RzwdKEFd+fM/JQQC2ptu7jqBfA=",-938863384799425374,1296164858853757453,-6029673784703310790,2615429487295907720>()) {
                                                      case 1460956456:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                String var13 = b.p(this.i.uv, this.i.uw);
                                                if (this.i.ai.add(var13)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sg5f3fma0rlq9","BqPNFFps6V8CE55HAfWH5C/g6lELrUiZIF2GyAxf28s=",-8041332716729416151,-2220698794609227993,-1354018164517851076,8652859921606952826>()) {
                                                      case 552806505:
                                                         this.i
                                                            .c
                                                            .c(
                                                               var13 + "",
                                                               (String)com.yiyiaddon.m.b.a<"s1yviuqbjb896y","9WYgGGDOLi7GAj7YzNWqL2CxAYXiOi7vfo+3l5fQOfxPAkqeliru9uaA",-671359933573768802,-1166126051145639460,-4781848009144529890,-6514884624802290504>(),
                                                               this.i.a.dA() + var12
                                                            );
                                                         switch ((int)com.yiyiaddon.m.b.a<"s232tsj5j3qw32","rQ7gTTwxR7dt+OBFfADAUCm6zYv4FExhENL/wNq0Qlg=",-4967421763216951463,-327162317044041930,-8106181311042124207,7347101080970240313>()) {
                                                            case -814876412:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                return;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"svpbtt9emr7wh","/Wl1nK21og3VlpZLpyA4xJBhk4YkByb4g5ZPW3fAm2M=",-2095534721724367438,4989675214215685062,-1669108071468410343,-1913469400699020797>()) {
                                             case 1414404286:
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
               break;
            default:
               throw null;
         }
      }
   }

   private boolean C(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hn8vb2ryscy4","oF8E4eBzh/tNCmiiemt3WGqQn2uTJ3r1KjnJP7WES4k=",-6004345443461551428,5617564498106913491,2776837269738009161,-721768121122660841>()) {
            case -2010439856:
               if (var1 != null) {
                  com.yiyiaddon.e.n.j.f var3 = com.yiyiaddon.e.n.j.b.a(var2.level.getBlockState(var1));
                  if (var3 != com.yiyiaddon.e.n.j.f.DRY) {
                     label29:
                     switch ((int)com.yiyiaddon.m.b.a<"s2y0gtpngx33ty","7Cn3Vj85X2AqY/Ogfsb/v8RGmhI1rVwzrB7IMVrOJGY=",5565264393964204649,-2234315346715460574,-7919518237538660666,4081343607803112756>()) {
                        case -135035910:
                           if (var3 != com.yiyiaddon.e.n.j.f.WET) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2equ1ab6bpv08","nVp3oask6Be1nv8JBr0nTJU49MDh7wqra1YuEg5v4xU=",-4867536700855193912,6206148864547407667,2193261770624111388,-5927301341546582907>()) {
                                 case -328521981:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s351k2bn2cpgd","bjGrUwcN1482V15rw9jxUbCuPTNfbv4vQ+YPKMTchfw=",-1811546355167987023,7237834924269812739,865865423255197008,-8814721479950922965>()) {
                              case 1077643197:
                                 break label29;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2nohbscpy2n4u","vSb6dk7A61vpQXG6NDPD0Pz9SAFWSBTUb3oSWOjscGk=",1242469517187737111,5683910337115441929,-5353190633096085134,-1515651737297131459>()) {
                     case 899562773:
                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2my7lbrk20yw2","hW1bqKKJi8++q9XahpY175+yk1cSX4FJeBrlZqCBoXs=",8401812083358087455,-2839489032810149848,8157571579733622486,-1121844411152414291>()) {
                     case 841627577:
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

   void hA() {
      this.aA(null);
   }

   void aA(String var1) {
      if (this.i.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1w5zqbrgdihyi","mgqiolFRcX7WpqqysEJhv6DRFD1zmcqaTg+zbfkYCkE=",-590144684570882519,5319777770976069099,-6487333834652136393,-8498382531266417894>()) {
            case -510511953:
               return;
            default:
               throw null;
         }
      } else {
         this.i.dV = true;
         if (this.i.a == h.RESTOCK) {
            switch ((int)com.yiyiaddon.m.b.a<"s3pxo79vv1d5pz","Rak5R4uvXMV0dd2f33n1A3DgC7xIvPJQGuYX6LhASy8=",8161799580350805399,-8369000609367054315,5596398500028603316,7986122323683918126>()) {
               case 1708738017:
                  this.i.ap.put(this.i.a.dk(), new b.e(System.currentTimeMillis() + 900000L, this.i.a.a(this.i.a), this.i.L));
                  this.i.aq.put(this.i.a.dk(), this.i.a.a(this.i.a));
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ny5of0ev3p14","QWUt4STUE5rxSFbRp4OgjcBlTe5CJg5brtKiHwcpw0I=",3815558280934898695,-6110572844315013313,-980310692077923622,-1974994006737249862>()) {
                        case 1208574382:
                           this.i
                              .c
                              .c(
                                 this.i.a.dk() + "",
                                 this.i.a.dz() + "",
                                 (String)com.yiyiaddon.m.b.a<"s90od6m3v5o4x","7oGHYbdfdvRD6ndg4yjTO+20o/MpCPxoo9sAW6w5/SyaY72NjMbg4YVueCWF6lenyvv/LbuHjPULGg==",-7338506575560594590,2962430248868630440,7143950980369735231,426403093841528689>()
                              );
                           switch ((int)com.yiyiaddon.m.b.a<"s138b23m4aap1g","oR3AqogqvZ5Ay/lP2eR+Qz5+9/n3Nr/6IsavzCD9C/A=",7341372123353930200,-1698654444642795599,6547860167246389455,7212734433553901363>()) {
                              case -1492681978:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.i
                        .c
                        .d(
                           this.i.a.dk() + var1,
                           (String)com.yiyiaddon.m.b.a<"s1kzyy14lu7n8b","k0qNt2Ul9Zrw7PFZGK26uyXE7kPWmG2XaL+woW7+E36g6hYBSAYRnA==",-2611240616306272975,3314242202404203918,-8684573760743579353,2481229135963009248>(),
                           var1 + ""
                        );
                     switch ((int)com.yiyiaddon.m.b.a<"s1wly1u09xpa38","jE0eW1IQRYuW+SG9/tmYUsPGNK93HPAvn8whEjNG4HA=",8908324916409803343,8173751296288252063,6040753993230321199,-3401552600611148176>()) {
                        case 1333833369:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            long var2 = System.currentTimeMillis() + 60000L;
            if (this.i.a == h.SEED_RETURN) {
               switch ((int)com.yiyiaddon.m.b.a<"s3rzax560ww2b0","cQ5V0U3qtd43VwlWN5oXIoZT0tCa5YSKpTbTblDGQhg=",4439910532724074058,-8711610783701953107,3961523270740364841,5998819089917977975>()) {
                  case 607077802:
                     this.i.as.put(this.i.a.dk(), var2);
                     if (var1 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s36o0vm6y1aya8","Z6XsUnJqP3MIPKKWoSVgUP3JkzxQT/DO5Rg1sdnAG7s=",1844523207149417135,-9089103345894608488,-6300739650889251609,-9181360077790180151>()) {
                           case 870966390:
                              this.i
                                 .c
                                 .c(
                                    this.i.a.dk() + "",
                                    (String)com.yiyiaddon.m.b.a<"simbwi2ey9tfu","Ve1ZrqSihKdJIsRppymtgLPYoQ71fsER7g8IboFZnWdTQB6Lo4o=",-6273250602325620671,-6367932973322214378,-1430656114116028205,-3092824125176328873>(),
                                    this.i.a.dz() + ""
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"s4y32n3bmgza8","adtDCTOkyk8xvtcpvM4mqQhU0dUQd80io14rqFcEUJc=",5400534221269414394,2811195140338066492,7756097585468170450,-2158317382726760090>()) {
                                 case 277724560:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        this.i
                           .c
                           .d(
                              this.i.a.dk() + var1,
                              (String)com.yiyiaddon.m.b.a<"s2odf8l6kpnfdz","tydl/lmbZQrnrewHC9LYlVCG+YjImKZylND1NALdGu7dgAYdt5KGcg==",-1490088163453077110,6853019193982391070,4027246519517753432,-171972478893594446>(),
                              var1 + ""
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s3uuwmrkl3md2l","IjPIRKCUXMrb9pmFJoF1LxY7ksXHZruqWeKvKHqBztw=",6749125024219611454,-1032342240468869484,3836764328207015150,-4496085746434523213>()) {
                           case -303648155:
                              return;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            } else {
               this.i.ar.put(this.i.a.dk(), var2);
               if (var1 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fraupau14qgp","JDuomzOrxQDJKxEzILW/qyz3yDJp/w5HJP9kFfkgm6M=",-6332330752875680780,5681658473258978833,2741562299482622850,8963455449880557944>()) {
                     case -52443776:
                        this.i
                           .c
                           .c(
                              this.i.a.dk() + "",
                              (String)com.yiyiaddon.m.b.a<"s8xak22d0ktrb","RNNoj7sUaME/5TKCXEfN994h4GeDd1AzAe8aqroTY60u6bN6X5ErL+mlD1RxuQ==",-3762533424918868220,-1020725444792641491,7952784615543710919,3247911749033931945>(),
                              (String)com.yiyiaddon.m.b.a<"s7ofdvka9uf5v","/BnTl2cMhl+vuwOXwXQTFgippIBNIS0NKwAODH+yqrWkVEurJiAWp64BQIWFYalJTo7E8QY+9B0=",5482100920858414615,6547937686431232846,-6267926497658808373,-9097525842915709180>()
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s25xcidfhpdlku","2+Z6ruluRf3Khog+u9hno5vvYP6OuWeP+8X6KEsBNh4=",5022586179762142159,3046474789791919227,-3530442950232029747,-7410319567862552047>()) {
                           case 199656102:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  this.i
                     .c
                     .d(
                        this.i.a.dk() + var1,
                        (String)com.yiyiaddon.m.b.a<"s35qwwgx5fhrut","YutMCwvhIANZpoDkWBQqqAiIm4OlAfrUR1DXOMBYidDxMH6NrJ6itQ==",7657233961379606028,-3632065325723308770,8240596869811491098,-6888479527782869271>(),
                        var1 + ""
                     );
                  switch ((int)com.yiyiaddon.m.b.a<"sh5jhs6ct51hu","YqO52Gz+fVAM00TuZ5aWv+f3bvsew0WS/9VFJvDFooM=",1530834286739662093,-2300590167221166085,-1631765322434590767,-6350471478280487086>()) {
                     case 1581961261:
                        return;
                     default:
                        throw null;
                  }
               }
            }
         }
      }
   }

   int a(com.yiyiaddon.e.n.i.a var1, BlockPos var2) {
      Minecraft var3 = Minecraft.getInstance();
      if (var3.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ovmmfgx8fpse","45TddPhEUmm66j4uchHhr4Ajd0CHHL4DFtRlHNNXaIg=",-8983800185925205547,5076929659031851752,1552305505381743563,3700731223378549185>()) {
            case -761327489:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1yyy9j4oq2lrd","60HxapPfNUrbXSbcLcNLxbLf6d65uSQUybbdmHLetT0=",831710280594172041,4069772739241538430,4571840281924549232,5174051586543597590>()) {
                     case 746137398:
                        if (var2 != null) {
                           int var4 = 0;
                           Iterator var5 = var3.level.entitiesForRendering().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s4h776wzybyrr","msSy7suXobuJ2ORNLVHAzOaLZJMvjWHmL6SMTUmtNoI=",7496034322358887683,1865049605970139241,-5947032908176692274,6357052515289353943>()) {
                              case 1282373198:
                                 while (var5.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s19889w3n2bmzs","TMa+ZVx2cufxKjLhqcug0Wy33/uVqG4as/JA14JF+Fk=",-9188107517244419690,-8045221352079363494,-1388301373178068643,-5631332871480806228>()) {
                                       case -2086005107:
                                          Entity var6 = (Entity)var5.next();
                                          if (var6 instanceof ItemEntity) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sppvix1j8fn3r","6S++KeMTcbE0OxhAPCLEe6ElMg0YADCHX7M9NsGzhc4=",6733502625340194721,-6596433249737345073,-4273391406891398021,8367019974055609487>()) {
                                                case -777996336:
                                                   ItemEntity var7 = (ItemEntity)var6;
                                                   if (var7.distanceToSqr(var2.getX() + 0.5, var2.getY() + 0.5, var2.getZ() + 0.5) > 36.0) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1upvywa3f89wg","DyLBoRjMJ86yvVMPyCRTdAZrK4lEmEd7/8RqBbpmQYA=",7140546725994662660,-3278606328847677264,-4976797415717426064,4232498296111749706>()) {
                                                         case 210420960:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s377rg7a6ae7yq","mRMfYRGOnGTv5i4gNhnP/UMdfVFZSChHOaPH5wzd80Q=",-8125072038122853887,-9197781595215005234,-3063400533679543700,3337682619028992149>()) {
                                                               case -563645294:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      label73: {
                                                         ItemStack var8 = var7.getItem();
                                                         if (!this.i.a.a(var8, var1)) {
                                                            label48:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2vnk4xg3bmisi","IrxCNqOdDJ5Z6AsZRyka2atr8fh8bLdTGdnxyl2M9fk=",-3786112238763094847,-3295832567262402186,-588040965521216764,6118957303807326197>()) {
                                                               case 2111030224:
                                                                  if (!this.i.a.b(var8, var1)) {
                                                                     break label73;
                                                                  }

                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2k8wzchb0l3q7","Wu4dZhJAftM6lG+Gnan4k3WsL4bt+32FmdW9wXPhvoc=",5959914198960975952,-8685619405494316718,-8951605754440337535,-1454038192217939177>()) {
                                                                     case 431157886:
                                                                        break label48;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         var4 += var8.getCount();
                                                         switch ((int)com.yiyiaddon.m.b.a<"s21f21zvy15awq","03Rw0h1vrAqXwWAtXW07RExaJjIbXbF/zq7xi8d8vVk=",8696668847761055589,-2361206007288909981,-4754968068628562413,-1209616678308055947>()) {
                                                            case 1223550931:
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"skadc8tffhph","fdgraQ154hAvTmUh3ciUMqrnvdaNFhfd4QFRPUXqMzg=",-7973753362738972936,7838271665115344674,517787051949511351,8585624437939952449>()) {
                                                         case 632321997:
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

                                 return var4;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s32xa8grx8bh8c","OYxgO0s7sEY5Xfk5udtJI9CkKPAPc93smhmti2FpFEE=",4001014620416353496,-5840793099431624220,-8652885437207168162,1250367706275443991>()) {
                           case -330294329:
                              return 0;
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

      return 0;
   }

   void hB() {
      this.i.uv = null;
      this.i.uw = null;
      this.i.ux = null;
      this.i.nS = 0;
      this.i.nT = 0;
      this.i.ea = false;
   }

   void hC() {
      if (this.i.dV) {
         label204:
         switch ((int)com.yiyiaddon.m.b.a<"s19iinrfu13zbx","tamJ9dKp/gg/O7NIXcUUhCn6jv6ZdEcg5JhlSzzbHbU=",3993811064442283601,3642847036370987955,2994174035987919281,-383348211745445423>()) {
            case -333393946:
               if (this.i.a == h.RESTOCK) {
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"sn0cw0022pzr","uCnCZY/tCvS5CgfX4H5iYfPGJ5QK91H5ttffikRodOg=",5950151546530590741,5959398108567373714,-3020894905549025111,6519252589155759771>()) {
                  case 1573086087:
                     if (this.i.a == h.UNLOAD) {
                        switch ((int)com.yiyiaddon.m.b.a<"s11hr9v7jcm06d","j1lZ7uy9mWjsy/eaTvt8Vicu2iZaLIecie/PcfC5SrY=",-6339080027814285455,-555476194380947003,-3308548787441397169,-2531086151349792827>()) {
                           case 15694122:
                              return;
                           default:
                              throw null;
                        }
                     }
                     break label204;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.i.a == h.REFILL) {
         switch ((int)com.yiyiaddon.m.b.a<"sqjthfxlqy0m0","t2LzQHTDnHYlY7EakcqDZbxNrXukFDKjQIY0xkrNq1I=",-2339991347656066350,-8878944823074570607,6965772602287315856,-5633559926737624044>()) {
            case -539576246:
               Integer var1 = this.i.a.D();
               com.yiyiaddon.e.n.q.b var10000 = this.i.c;
               String var10001 = var1 + "";
               String var10002 = (String)com.yiyiaddon.m.b.a<"s2u4nwcz5kfefa","Q4Hpxo7Fh9ciCVnD4+Jc3Kqo4wcRsEmo9Is4qtFj+sOmfibmV6s=",-9118184754330097661,-5019368950187553527,-7869040223587739164,4966428499900571717>();
               String var10003;
               if (var1 == null) {
                  label150:
                  switch ((int)com.yiyiaddon.m.b.a<"s1fo25oexyp02s","jGJmdJAkDH4/TQWs9hMhn10OOGW61KE4s4YpnkYrTFw=",1103410376821436658,-7316808084361419515,-8051760755443800650,-1869885214618514792>()) {
                     case -1221611607:
                        var10003 = (String)com.yiyiaddon.m.b.a<"s1nenetyfwb443","dIaTRlNQX+VjW8FnL2a6sqJs+D7Qyfmy2qaaXM7uhHXAh3XL",-657264566583684136,4466858584012379562,-8791587654375545583,5860162468990780972>();
                        switch ((int)com.yiyiaddon.m.b.a<"syyedpug8wrme","8fQtAp4z7ZLhrAgybV1KCYL714FTweXYncap5sAmIGo=",-4630211393475524053,-4006795458152933308,-6403998099925946798,328345571276937669>()) {
                           case -1449017809:
                              break label150;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10003 = var1 + "";
                  switch ((int)com.yiyiaddon.m.b.a<"s3695852nl2sfn","rDpwlcTOryDCQ3euVRiloaf1Z+9gcJOJrbmSYJC7LiI=",8002737226330521452,3103905277273430017,2837071081775322552,5168319443711227805>()) {
                     case -295004148:
                        break;
                     default:
                        throw null;
                  }
               }

               var10000.c(var10001, var10002, var10003);
               switch ((int)com.yiyiaddon.m.b.a<"s2l85invtnxwjf","gECWdGmi110OXrwwp+rcQh9ZrYg4O9DD1CK+fBLW2Ec=",-2705629375913512727,3502037906634412052,2389200799307722098,-7078810835941907344>()) {
                  case -882034695:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (this.i.a == h.CLEAR_DEAD) {
         switch ((int)com.yiyiaddon.m.b.a<"s2gex60t5u9nrv","g1EknIx11uc6VdtAiAwNw6Dvu7BMttXbVw2NPrM6IQ0=",-6439805508375918149,-4025204872841354050,2370413085006117091,6748863764708150173>()) {
            case 2108700984:
               this.i
                  .c
                  .c(
                     this.i.J + "",
                     (String)com.yiyiaddon.m.b.a<"s37xexlhv6c49k","Z9NRzQiYYb44xwtiakX/D+Z1fZwbIeqFNfe5Mlf0zfBcnsDpMMACyxf6",598138754804535367,7223298581351253169,-1542969241321033813,3441055166850200908>(),
                     (String)com.yiyiaddon.m.b.a<"s2z2saumcdi6yh","mgRiLg1MZKwlENnfaMvuk0oN2tJXVOPakE1x8ac3IcUAbQ7Zpz72Qq7J",-1425895756106277824,-8559658072487733715,-3665870579793401907,-5821023097631453996>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s306l0qe0slwnd","5w4DHcpIihTyAbQpPS/AYWtjQQ7UFguhyNgEpmcaoP0=",3257198147263452478,6792328638617437252,-6574352616950344958,9215722343933238563>()) {
                  case -1083345196:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (this.i.a == h.CLEAR_MISMATCH) {
         switch ((int)com.yiyiaddon.m.b.a<"s24o6jozs4zxd8","4zUUdXY5Fd/WMXFZ3XMumQ81XaqgXbmtxxZzPicpjiU=",-6573805631803851046,-8246070907653062439,-5556165375694639966,5127658950705199270>()) {
            case -1639248123:
               this.i
                  .c
                  .c(
                     this.i.J + "",
                     (String)com.yiyiaddon.m.b.a<"s27bsp6u5mv2o3","wBQp85d27gQ7rwrApTtAl+zeLHU1JVKWe3+gUJNqBNyB5oNwlKZi8wsU",-3573162142398113222,-422478871719711582,-5608099631893010269,8739083731598323877>(),
                     (String)com.yiyiaddon.m.b.a<"s26f0ku5vfwg72","e/6BlljM4eSm8GwNzTUmMQ4uHuVei6T35nxhR8OkZx3m/ti905XKu6GmkKIpif38gfTa2Q==",-3798905013445555091,-1610335886363842392,91646582403303225,2698661790445938704>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"sjmlncejudrcd","HE2M50tObGVSMU8/3/OswoK3aSDKYZOrIeaGK8+DCf0=",7118851316897801798,-8389936094704105092,-50436498808799009,4979760385803909545>()) {
                  case 252174516:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (this.i.a == h.CLEAR_JUNK) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rvresyjr584t","2vm1YMy2iOl0k26JncBXfa9BO6e3E+vQPRNeOLHb0AA=",-49888332171892080,4575255094167343799,8864488445636493650,-5406319171041371225>()) {
            case -1153648555:
               this.i
                  .c
                  .b(
                     this.i.J + "",
                     (String)com.yiyiaddon.m.b.a<"s1wqq21n9nxf05","y04FJ+Jpf49myLcRun+A7nG/0L7CIEJI4oWvkxEsDC84g8pKeQVVOUkb",-3715540111879041834,5455537127125755030,722573188356855399,5607807573059280023>(),
                     (String)com.yiyiaddon.m.b.a<"suupx5mz62xl1","wIY6eV+yNeIDxa/D+MFuBhqwfAVMezySrBFqm5j8sysQPGJaoehX5LYMf4A=",-4451875120646735238,5317903168865698951,5364992368373686114,-147037127728347387>(),
                     (String)com.yiyiaddon.m.b.a<"s1k1waepmcftj0","EE3OuiUXv1tskOKeYp2oWRme7QkfhW+AtyzppQ==",-6997008676618418998,-4436087147709301395,-4693049827734275412,-1201204921205713973>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s2rmny5j480lji","cf6I+2ukEa+9HdY/hCnytRofslD7QFcefimvuIqUJh4=",8729352417425523012,6401419504044286612,5121069755716741517,399292123951655918>()) {
                  case -380497259:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         if (this.i.a == h.PLANT) {
            switch ((int)com.yiyiaddon.m.b.a<"s31774pjo3q355","WZpJA+OMxB4j7svZ0bzpwFAcNQM3zvzZWljN6n7Ecsc=",-3743165566225949040,7966492120526888600,-6336109499100554505,-4817428071489830507>()) {
               case -1461689392:
                  if (this.i.a != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sgk27ndva51y7","C3JBgepkqJ3ByFcSI7ffqoeEzxI/TLoV1freyLFGuRU=",-2520330776169341703,2900690843276095692,-3666898135455084440,842284548395882638>()) {
                        case -327560243:
                           this.i
                              .c
                              .b(
                                 this.i.a.dk() + "",
                                 (String)com.yiyiaddon.m.b.a<"s36j4mn9czm57x","tjmh+IXUgvMMQb+FFQ+mV14Mb+p1/qNSQts5WUPrmdJPBvjp",-2604294197978603162,-560926522931792449,1241181649765982992,8866288085898062418>(),
                                 this.i.a.dA() + "",
                                 (String)com.yiyiaddon.m.b.a<"s1k1waepmcftj0","EE3OuiUXv1tskOKeYp2oWRme7QkfhW+AtyzppQ==",-6997008676618418998,-4436087147709301395,-4693049827734275412,-1201204921205713973>()
                              );
                           switch ((int)com.yiyiaddon.m.b.a<"s1pxd5kxq48927","yVfvaxnbrlOOn2/H0Qo8Zj3g1cg3u8JnQgVPccLBDz0=",-1246297412975550421,-1593497627030164135,1659370589651235768,7699331460693951939>()) {
                              case -1531706073:
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

         if (this.i.a == h.HARVEST) {
            switch ((int)com.yiyiaddon.m.b.a<"s26bul6hodlry6","cVHdx0P60OeVAcjQjbT4ospwloDyfmUQHlvlTmbii70=",-3560308538673546874,-6695704153581854438,7589953863732775620,8346291549296360671>()) {
               case -3092038:
                  if (this.i.a != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2udu82nb5oybk","CaHAOCpTeuqskaBqUJGCBofp+8pQCRZ1xxT0FsI0ibE=",6376101924727367907,5931653307539825682,1760377152029293386,7357611097464891482>()) {
                        case -2086746950:
                           this.i
                              .c
                              .b(
                                 this.i.a.dk() + "",
                                 (String)com.yiyiaddon.m.b.a<"s1rtojlmmuvxwk","yrkkyVRMcz3ZKuZ9bYBWYJ745n3lY3EcTiIDadyDchVTMBNz",5944504720835750954,-8285923036179734754,201432215189723727,3295648383653688517>(),
                                 this.i.a.dA(),
                                 (String)com.yiyiaddon.m.b.a<"s1k1waepmcftj0","EE3OuiUXv1tskOKeYp2oWRme7QkfhW+AtyzppQ==",-6997008676618418998,-4436087147709301395,-4693049827734275412,-1201204921205713973>()
                              );
                           switch ((int)com.yiyiaddon.m.b.a<"syt7bdkrgei7i","y61ezbCeunZCuJEzXycTH7td3qYVAbv/AZuKs4qUbZE=",-7550370685232313333,-5527658272462918736,-6237962850125123476,-1356231032813414624>()) {
                              case -827440131:
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

         if (this.i.a == h.COLLECT) {
            switch ((int)com.yiyiaddon.m.b.a<"s24rrd9emh8six","isR4SLMmUNqcA0PSdCSsrmH3j2iYpnM2rETqi/E8zfA=",-4801448938758154260,5469787218178629497,4624821179138902921,9017570314549507577>()) {
               case -1757723517:
                  int var7 = this.i.a.cK();
                  int var2 = this.i.a.cL();
                  this.i
                     .c
                     .a(
                        "" + var7 + var2,
                        (String)com.yiyiaddon.m.b.a<"s34v7hwkr0a6v","1sdWUtpylYB1yEqhvbulAthTrKfbwAnEeCkpng8MdpevxQoj",-2615417912104764655,326019111675788573,1909539386736210894,-2117307752432862086>(),
                        Math.max(0, var2 - this.i.nY) + "",
                        Math.max(0, var7 - this.i.nW) + ""
                     );
                  switch ((int)com.yiyiaddon.m.b.a<"s1hxwmgo45906e","gSKyw8a7TnCcz3Qjnh6zSPDuBbgDRukNt9uoz/9Aks4=",-6922290576433001044,-2769453077350350184,6063590510485738539,-2088409900348417775>()) {
                     case -1381514776:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            if (this.i.a == h.RESTOCK) {
               switch ((int)com.yiyiaddon.m.b.a<"s3schhipq6nhgj","cshtAdwo4n9dAapbcrRnCSzdLLDWNPFVRrOJShG8U/o=",4514522107761092170,2088718498001180703,-7914110354653912684,-4011466194999452994>()) {
                  case 616396539:
                     if (this.i.a != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s35sxf9vsmz9yy","4XuGGUeXUn/yUX7bfPd17Hn11Ut3Z3YI+29gIkNhGKM=",-7017648448940639255,-127427466780772586,2225635691086168345,-735499259422519618>()) {
                           case 1612603775:
                              int var10 = Math.max(0, this.i.a.a(this.i.a) - this.i.nX);
                              this.i
                                 .c
                                 .c(
                                    this.i.a.dk() + var10,
                                    (String)com.yiyiaddon.m.b.a<"s3jw4w5q4rju4f","klJ1E2KMPwjQJc6m0amk/I0LRayRetJ3cL0BUgWSb7eSh5c4",7939699034519831774,2953026168877767964,3283418437301801114,7880990511955873457>(),
                                    this.i.a.dz() + var10
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"s38x6s45x3cb4c","JiUfES1hCvrnmLqNFULyAjhkAZ1EIa9j1Sd/KhHTvM0=",6356587901193727050,-6369059110945459227,6685472591242097380,7142943916551248654>()) {
                                 case 1496023839:
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

            if (this.i.a == h.UNLOAD) {
               switch ((int)com.yiyiaddon.m.b.a<"s15bdeq28shkr4","M037cKIE5GEEY7vhGcTme0YsLrTjV/C1PPgNRVNq334=",2495873527712165526,-5980613712955415211,4257549163257847172,-6629102000155140118>()) {
                  case 511712882:
                     if (this.i.a != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1iba3kxckuhhv","WzFFrqRaGQevm2Xuuo3A8zu46fN3mx42pnjPpaHDaE8=",5323054237271106572,7386881775692054600,5643597431693890396,4466760305928537779>()) {
                           case 1317739667:
                              String var9 = e.a(this.i.au, this.i.a.u());
                              int var12 = this.i.a.cK();
                              String var15;
                              if (var12 > 0) {
                                 label142:
                                 switch ((int)com.yiyiaddon.m.b.a<"s4x07p2qgp72n","nJgMMy/DNYkLH7CTpSdLeJLXo7aGCEBsWEsp/5KcB94=",435047137177057079,5781286154287872326,6810277498020178468,-775535496549760822>()) {
                                    case -1578737147:
                                       var15 = var12 + "";
                                       switch ((int)com.yiyiaddon.m.b.a<"s5n36nt7o1lvv","ILz4cRNfk9r9e4EQ1y40097AIiUNgO8WmE7OI9Fi8xE=",-5814584930275186964,3308346244873887619,-3564898398269737344,1832179562337549762>()) {
                                          case -1877919955:
                                             break label142;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var15 = (String)com.yiyiaddon.m.b.a<"s1k1waepmcftj0","EE3OuiUXv1tskOKeYp2oWRme7QkfhW+AtyzppQ==",-6997008676618418998,-4436087147709301395,-4693049827734275412,-1201204921205713973>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jrumrwokqhon","nnDrJ6Izf5Piv24qW+aaRMCZhoVoujeDpuiGNc2F1co=",-7294200453446527364,-5965472025028195280,316180898062723932,4969919535948717167>()) {
                                    case -456065029:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              String var13 = var15;
                              com.yiyiaddon.e.n.q.b var16 = this.i.c;
                              String var17 = this.i.a.dk() + var9 + var12;
                              String var18 = (String)com.yiyiaddon.m.b.a<"s3gd7m8x1kljdj","oSAy6RGRVScUqBaekhm62fB8A4iVW8nsJzCo6jXBaN3eyCal",-1814085054476997836,4834816201670401384,-5689344535741635492,764259573386009034>();
                              String var19;
                              if (var9.isBlank()) {
                                 label135:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2sq40dhsjiz5r","mNhyQ6jocIH4Yzi2Ca006ifckyMYXwawSVACFBOatNM=",3139730073692404724,4444159535393172852,8266234763345841397,-6957358699234412588>()) {
                                    case 1683675320:
                                       var19 = (String)com.yiyiaddon.m.b.a<"s3nagb0jpp8qt0","yziE2tkfV31YTueHZXdR0s9eE21LAXHMHyRx5F7IeSrRaZ50I3Y=",-2448278600656826378,-3229749351383736262,-5686998512255103793,6834110781199682715>();
                                       switch ((int)com.yiyiaddon.m.b.a<"s5oj7tjgg7uax","uRtPQHeBmdQbax5N0arzCkZ35ZMGsnArvQdAFvIgNn0=",6277389868303118757,2991381392597844547,-4709715169797175070,3859325640056141362>()) {
                                          case -1779871651:
                                             break label135;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var19 = var9;
                                 switch ((int)com.yiyiaddon.m.b.a<"s387irlxgpcozp","sRZT4jzXW22DYzvfj+9BZFmB4N1DrmU3TBR80BcfZ04=",-892307387086602574,6978662323386187265,969139547938242579,-5728491500016938775>()) {
                                    case 2019432554:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              var16.a(var17, var18, var19, var13);
                              switch ((int)com.yiyiaddon.m.b.a<"sp7u37wibbbt9","qOVYn97/2jfxQSyKnOnNvInu8DsvLabMso0waBPLLwk=",5872562404904485458,687223527617551206,-2996462221705639641,-1497299122132219088>()) {
                                 case 1759219767:
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

            if (this.i.a == h.SEED_RETURN) {
               switch ((int)com.yiyiaddon.m.b.a<"svkr5u7vu75x3","hfTWQRO0FZ9C1neiLwOyUE4ryBBlCaJuk3n/IHYRKMw=",-8882695737851917774,3103448179691202393,6728389956542503429,-7924319883843574587>()) {
                  case 1602863036:
                     if (this.i.a != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s31wmisb5xlvpm","J5d+aLS1saoEvj1r3g17003Dfcz/UohR2mXfNu86IMY=",3322706924789206181,-644708402886675252,-3000780735504355275,-8636152844593727604>()) {
                           case 441336030:
                              int var8 = this.i.a.c(this.i.a);
                              int var11 = this.i.a.a(this.i.a);
                              int var3 = Math.max(0, this.i.nX - var11);
                              String var14;
                              if (var8 > 0) {
                                 label167:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3mo5mcdsu09t3","ibe8qa8wBnSqh967VxHhGNEedoQk4LbX8pUca5CXPKY=",6114402298041332746,-5616851881323943018,-882636300611937459,1529488150598972952>()) {
                                    case 1653467276:
                                       var14 = var11 + "";
                                       switch ((int)com.yiyiaddon.m.b.a<"s24usskmxbbl8u","1gmJcwkhrscAdbquJL9Jls/RRNFYbyAXzlkeOsd+8JY=",-5168526148926369868,1704612614211701098,2611157707390384294,-2640165678186597518>()) {
                                          case -1625750522:
                                             break label167;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var14 = (String)com.yiyiaddon.m.b.a<"s1k1waepmcftj0","EE3OuiUXv1tskOKeYp2oWRme7QkfhW+AtyzppQ==",-6997008676618418998,-4436087147709301395,-4693049827734275412,-1201204921205713973>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1nw3qteb2wszb","g7H7RAxA59t7LlJzbaHcmPT6JqfPd2lqWdFnWX8zT5g=",-3318782224842399449,-3369479942117100127,-1535970694218691495,-6919193439408935005>()) {
                                    case -41697815:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              String var4 = var14;
                              String var5 = (String)com.yiyiaddon.m.b.a<"s2lpvo3779v9j5","SoUtEKxGBuu1WE+L5yclIL40zj6m87ecFGUjkbr3R7oM3bKDjz0+TQ==",5579163176915172141,-4920870387383344947,5722547929483009480,7489821335646334141>();
                              String var6 = this.i.a.dz() + var3;
                              if (this.i.ad.add(this.i.a.dk())) {
                                 label160:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2oq0ibq6j8jyk","bukzJsFHzhF+D4+RsnTUkVhZPMAgm4Dnsf3QgBI0wXI=",-5550346295925047937,3094325428160725020,5082870695129708609,-6580715790291618672>()) {
                                    case -402686873:
                                       this.i.c.a(this.i.a.dk() + var3, var5, var6, var4);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ligz7s2ibjnj","Ss8U3jCTo5FxKAM1tn3Sjl+4n6YA4V5i4aOr6DjPO58=",8950188420958620513,-1401177011444662641,-8749551661313329051,-5462460979793994506>()) {
                                          case 705773528:
                                             break label160;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 this.i.c.b(this.i.a.dk() + var3, var5, var6, var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2uzzpnqry1lwk","gVwNZScW1lqsqhdhbBC0THLbFRrUNAqhC/tg1vO6efg=",-6673688538958573237,-6434330825808899979,-5277139826840614519,7151723577898610088>()) {
                                    case 396019200:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1sc6612zrzbp6","PAZZzT6H177TujbTyPiv+LAEG/zyQSxVAfn5joPXB/E=",-6986290625363662631,-4106765423476850016,-4198809344102641526,-2705372217534220293>()) {
                                 case 204898609:
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

            if (this.i.a == h.RETURN_CENTER) {
               switch ((int)com.yiyiaddon.m.b.a<"sprpv4iwxk4le","fe94FyGuRHIRkRiF6j+M6jJqUXmpQSx0+z5L0qVvysU=",-6115506732244247558,-7105738883803059569,-5631680103435856130,-2491966358082158933>()) {
                  case -1432065073:
                     this.i
                        .c
                        .c(
                           (String)com.yiyiaddon.m.b.a<"s352kncxoqtza4","reOIPtIKK99gs5rGyMGfyP/nvP6rOOe21SwBLdiQk+mj5Yp5BVi+wxl3sJXkZ6He1P4=",-4384571380038145471,-1344818220564250931,-439737832820161411,4675118751734542238>(),
                           (String)com.yiyiaddon.m.b.a<"s3lc2rx47iuwyh","HEcfNydTof1hGxvMcRDV+lQ+PoOmCCmLjc8IAy5BzqC3bmisD0H9Vw==",2311643527443692764,7953699929885234115,3126325266851282739,-2420503720118188032>(),
                           (String)com.yiyiaddon.m.b.a<"s2ql17hd3e9z3n","c0z9N155xes88I6DM21wfAL98DHGQI4sX0vOICWCiNkJV6tvcLE=",-1890015492085497346,1661292655953753040,7036907869567604176,-4494722216344962414>()
                        );
                     switch ((int)com.yiyiaddon.m.b.a<"sllfdc2f1ai4v","kWpe16jaLIb+gTRrAS6DjFhLi7dVwbJMdLGzn9aQm2k=",-5371082396727823599,4856686337200357888,-3848222925078782019,6165310450829548375>()) {
                        case -1749848576:
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
