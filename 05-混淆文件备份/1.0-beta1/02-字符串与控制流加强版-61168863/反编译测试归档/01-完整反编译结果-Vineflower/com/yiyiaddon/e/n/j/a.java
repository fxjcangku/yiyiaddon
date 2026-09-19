package com.yiyiaddon.e.n.j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class a {
   private static final Map<String, e> af = new ConcurrentHashMap<>();
   private static final Map<String, e> ag = new ConcurrentHashMap<>();
   private static final e a = e.NORMAL;

   private a() {
   }

   public static e c(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mt261hq04ttg","zXa53ekAo4vx+EoxpCWDkGmJtu1w1mHFRkvaCYwrQS0=",8426949719095577414,6105161086212352575,6179542171285964987,8593399952176570406>()) {
            case 1285350092:
               if (!var0.isBlank()) {
                  e var1 = af.get(var0);
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s30zvn7x8jia4g","xmFXlcCkMpIr+M7+n6oV/kx3oFHBxZD8Xw+U5RFl3E8=",-3392405544120569925,2280742171877508798,1099630099723192610,-7359117350212627556>()) {
                        case -1524563450:
                           return var1;
                        default:
                           throw null;
                     }
                  }

                  return ag.getOrDefault(var0, a);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s9vbubiwjjd41","3/V8u5KTrKl0WlPqwsUcsSzh14nt7dcEChCXcr8Q5Ec=",2910962795997375276,-7839160779653276734,-8623840056243687948,430924074228296045>()) {
                     case -819394340:
                        return a;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return a;
      }
   }

   public static boolean ah(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3r2k7z8fhzgp2","x+SbfB4IUbwdp7yHptRx2VLCuBzrElZ1E58oOmUAkJk=",-6324408930312515741,4975176917507855528,4977505906401065182,-8164258855527830920>()) {
            case 1486310654:
               if (!af.containsKey(var0)) {
                  return ag.containsKey(var0);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1el6v9w8czgz8","H1G4WFfAVkUCJaVGm949vIS3MDT1VMIXiY3vqY5PAzs=",-1030168297562769748,-8231871036910382735,-2110537016235918556,3623769560642212395>()) {
                     case 1349475793:
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

   public static void a(Map<String, e> var0) {
      af.clear();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1v5rzwnd8ase2","rrgZfCP2JttUrsA0L7kY/TuPKSHI+0YcrB0YVZIKAuE=",-4305581076852970296,-5729883550030561224,4290446079781425669,-5394009487564241541>()) {
            case -621224677:
               af.putAll(var0);
               switch ((int)com.yiyiaddon.m.b.a<"s2reaf4yefk9ei","5FS7FhYHgEwtB3FD5Jn878koDz4jYV3kmoaaHhzaFAQ=",1430109835721099047,2400178283259971993,-1691707183877691935,-3988797339867420548>()) {
                  case 339861537:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static boolean a(String var0, e var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s317txb2pjygut","1hxDRsnZjSkLJ3hFl9NMRnzLGVqGy3MQ91DUOpkRYi4=",6328347193558270335,-89864625616326968,1191073260466864288,-4208193122535036398>()) {
            case 1685400073:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1oifsem6e0a1g","+d+1jWP6aruuIm3zAUwAftEkj0YOjeNFOLUrflF3wcg=",5112418935782995636,-7331162119123949388,2667498485674283301,-6444464014106484117>()) {
                     case -1211583658:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1eeob258fgxlw","Q5LlKCb4L8LiSsYSQKwdue31ZUNogfHCyAiTIkWmhN4=",-1620950297782526000,-3814840433643750149,-8389430893509556696,-5763372377879617058>()) {
                              case 357721805:
                                 if (var1 != a) {
                                    if (af.containsKey(var0)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s5xnkq491541c","OweuNc1k+tWZAf3nd6kHrVb+r95wmDLCwdB1P0Ws56c=",-4081681706222315214,-3175513173056191763,8880450263796467249,-9045541135474743482>()) {
                                          case -822483580:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    e var2 = ag.put(var0, var1);
                                    if (var2 != var1) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s22bg6agtcpm41","sJAMaa6SBLs9Tr4G6XYBzB7LxfX3Hz1BxVv9eBh+WHk=",-6889084539648442851,-8299900265778450376,-7939924702429250941,-4858141301473423499>()) {
                                          case 1301082173:
                                             switch ((int)com.yiyiaddon.m.b.a<"svrmfhnb7a89t","UMf4LakycriMmZtlo2fXSkyKRIgeZv8Ga0cOj7LwcCM=",-3688299810018977345,-7576518817013675820,-1765180004225141657,1619650858096452557>()) {
                                                case 1474953399:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       switch ((int)com.yiyiaddon.m.b.a<"s17pgm5dfdbd99","r3L3hgc/66PppZFPLLpl4lpL2UxA55LsTCC5L8E1Fc0=",-1101296729231237421,-6526429712312781766,-5259712670068832091,6904267471335237338>()) {
                                          case 172447134:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"spdpid70s9bzd","illVokXSTHKxa87XBMGVPqiVGJg3t82X9yQm72mhAMI=",3448513810804818078,-8764776117288473289,-2547289934593017914,-5560002439641699678>()) {
                                    case -1578919710:
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

      return false;
   }

   public static void gH() {
      ag.clear();
   }
}
