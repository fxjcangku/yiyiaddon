package com.yiyiaddon.e.n.h;

import com.yiyiaddon.e.n.j.e;

public enum d {
   SEED_BOX(
      (String)com.yiyiaddon.m.b.a<"s22vs8h2u05ydx","X45z1MP5Rngj0AX7uLuFvMKpAoFRszRNkmUCjKdPBM+mWA==",-8182979955337933458,7833892924056309788,1403211773855954545,-3209951976587703568>(),
      (String)com.yiyiaddon.m.b.a<"s1t66hjzbk8rlr","hb36yUMcSIM9QKy2D7RQlTOWtwWqWh1KsxbJdhxwnMNhoUTT9+7dGu7X",799166676738650564,-8472940105650962154,5509411793409042567,3929603343772793348>(),
      true
   ),
   OUTPUT_BOX(
      (String)com.yiyiaddon.m.b.a<"s3vfioi9q9d39m","TjYeP6unw4f+UZO9mexyeiYhEmQKqhW18iNJtwdy7OLHkA==",3575296479842936256,-2151621956710324007,5980272151751742605,1379623378614243370>(),
      (String)com.yiyiaddon.m.b.a<"s4gawsficv1jk","ikOpjiQp+5gK+rxTWxQJjPhq0gwHNG4HrRr4tbSPYaDhYZzXh6A45A==",5988828986478955277,7977637967227477151,-6262615644713888034,-4445545213684375301>(),
      true
   ),
   WATER_SOURCE(
      (String)com.yiyiaddon.m.b.a<"s21u7ge60s8iny","FF7nHp5mDRupofVPE1Ep6jMjEelf1Gj8lCCOrfdK9u9p/Q==",1948687982528806017,-6172319980881215521,5652991938325840101,-7555422021672471402>(),
      (String)com.yiyiaddon.m.b.a<"s1kjh2d0613gfr","xsgSHk+Hu/9eXGwL/Fzl9ydMTEqYU6aIUCGfPnA0DTFWsHKvJls=",-602757768561264229,-4513616837242357638,487706747528887734,-9221864151013242007>(),
      false
   ),
   SPRINKLER(
      (String)com.yiyiaddon.m.b.a<"s2exnoagugbxk4","lbiBb1P43NJMtUPP7JpukRW1bigcOCXYv4fwc617W2gQfw==",-5875020580200049433,-9198502426387746126,-2974601802590242735,-8231316839434193306>(),
      (String)com.yiyiaddon.m.b.a<"s3cfg9gfoq16t6","2HQFT99gn/+tRMFDPaxVKM1LcXNxsAybwkqYDmftCncFm8k2ze6iATHMkVOjcA==",-1161210189481600004,3245703990009648373,-6578823482165570069,-7306176151770247191>(),
      false
   ),
   LAVA_BOX(
      (String)com.yiyiaddon.m.b.a<"s1oyp7vimuazn6","CNxNEbGZJLTszTRRlWf+wHRylFa13ZgV/LQz9OlgG9OO7w==",-6374575509556326991,6762723766557761532,7878194024442457077,6438977318738019077>(),
      (String)com.yiyiaddon.m.b.a<"s1wbd4ev21iixn","prreQqYq+rwHLItSTPMSjFYzZorDpEUpySsBIZqWVa+AHGV8Laie2Pes",-3618616462771085613,8962468220902530339,3374797666623753154,-3440396836674750897>(),
      true
   ),
   BREATH_BOX(
      (String)com.yiyiaddon.m.b.a<"s2q9uj3x4dclbt","pArMo/WRMyLierjSG90p1nCIB3mBnNjgKBiXSagBmsBHyQ==",-5488573064308472683,-9012996304233253796,8687130773367010057,8622216093237514588>(),
      (String)com.yiyiaddon.m.b.a<"s2yimqmbu78wyd","pRLD/tig9mbxCpjG0QzoryZEKYqWRwEHmD5B//xfqRNmlzBh2LR/JywxlNPL2w==",8547629020578764408,8541916584884468987,877014138755327966,-5095902826134709525>(),
      true
   );

   private final String rX;
   private final String rY;
   private final boolean dG;

   d(String var3, String var4, boolean var5) {
      this.rX = var3;
      this.rY = var4;
      this.dG = var5;
   }

   public String D() {
      return this.rX;
   }

   public String aM() {
      return this.rY;
   }

   public boolean H() {
      return this.dG;
   }

   public static d a(e var0) {
      if (var0 == e.NETHER) {
         switch ((int)com.yiyiaddon.m.b.a<"s1avfu7c9zm5fo","f4MCZUcfDoRa521pHBy2M7JRH8wlLEUL2KsoxC5sxXs=",4575272697551452365,6846337544465538870,3411903274768457681,1694902881529625315>()) {
            case -14526084:
               return LAVA_BOX;
            default:
               throw null;
         }
      } else if (var0 == e.END) {
         switch ((int)com.yiyiaddon.m.b.a<"scoma088uubkc","U3yhYgz8knA07N79KKKoI81JkV50yVZJPdGPlDop8/0=",8366828615796905504,2520004537634223079,8568502540399999599,-612841245270524359>()) {
            case -484470110:
               return BREATH_BOX;
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public String a(e var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vu24yf7bba7m","/pE82NYg7N7RRIk6AUrVS6HkfOTCVeoQQ73ApZty+FM=",5995431349458469777,2336829242618861354,3421736395881008751,-4932254254902789124>()) {
            case 428194808:
               return null;
            default:
               throw null;
         }
      } else {
         d var2 = a(var1);
         switch (this) {
            case SEED_BOX:
            case OUTPUT_BOX:
               switch ((int)com.yiyiaddon.m.b.a<"s35ie4lishbunv","DhMKRiQZ4UA0dbLaFP6YQkpctC2EyIYKK0EF/bHilF8=",6016480840918878908,1105505980033887197,7748463363446888066,-1830188519072659038>()) {
                  case -584896797:
                     return null;
                  default:
                     throw null;
               }
            case WATER_SOURCE:
               if (var1 == e.NORMAL) {
                  switch ((int)com.yiyiaddon.m.b.a<"sjbwzog1rilxr","7UVCxbUI4m4RNYb7NAnx+FYj6XpIypIHw3Y/FmpQSbk=",7122039923099346710,-8628566891969859553,-4514335381058901124,3021612900742841683>()) {
                     case -363644716:
                        switch ((int)com.yiyiaddon.m.b.a<"spcb6sybolbth","fwQIS33+Zl/nFgLrthhuD+fY4iWeSESv3cJRSaKgte0=",-1183842062774240114,-6956069666231201591,-8504048986811465656,8069011832144939908>()) {
                           case 1583435773:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var7 = var1.m() + var1.eb();
                  switch ((int)com.yiyiaddon.m.b.a<"s183pug79zlqj9","wOpcAmrR9so98hqLIeHcH465jDcq4YYxRlvciQIzobM=",-4485856524631902605,4862865107516996873,-1711248202722998495,-3025141771512530449>()) {
                     case 721678588:
                        return var7;
                     default:
                        throw null;
                  }
               }
            case SPRINKLER:
               if (var1 == e.NORMAL) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1nm8khbesj4wy","2WmsdtuCmUYxsOHSzBMDZjedxNgMi6sm1Zbm0mWv+V8=",-4569827449017580295,6587093926383866137,-2425834967242182365,324856466030074808>()) {
                     case -93901161:
                        switch ((int)com.yiyiaddon.m.b.a<"s1j2x2us25laqx","tWupxEHlZy1UpAPyRWqVPgC+wnKl/JOneDD193dcluk=",6951561989786121636,1053948309708027045,3504705076626888332,4008732581304932204>()) {
                           case -1233616789:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var6 = var1.m() + "";
                  switch ((int)com.yiyiaddon.m.b.a<"srygi82ca2jcz","nUhef7pM1UmC2LolbYEdT2AgPQlPFpF/GghDcwMAimw=",5822102017046828595,-2531548521912941545,-6398972111969659,-6654907625807286273>()) {
                     case 1587632692:
                        return var6;
                     default:
                        throw null;
                  }
               }
            case LAVA_BOX:
            case BREATH_BOX:
               if (this == var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3o16ugp1bla22","TSkxCm43AhnmDTL0ISM0uR3fkoMXAzbk8hLY//z/iDs=",8317632053150052870,5388596360057504420,-7244439164335383262,-8927915718604972972>()) {
                     case -1822649053:
                        switch ((int)com.yiyiaddon.m.b.a<"s2u7dvlh31n0za","nr5nv/44vKJoeysc3teWTjGBg4F59QMTdmVbrv5Mh3g=",-982934275680894728,4409744432105418823,2570276754398860530,8335235255558500306>()) {
                           case 1522415729:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else if (var2 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"smhm15qtzofll","GoJ+OBOg+CvLjI32vz2knR4yVk6OPo84gu5/X7Zo+WQ=",1190671240055932236,3050387586418373657,913068282837735538,7082921923759886795>()) {
                     case 1493013157:
                        String var10000 = this.rX + "";
                        switch ((int)com.yiyiaddon.m.b.a<"s2adsu54ql2c8n","12xHGPjmWb9pX3XHEBNz5EcJTM2jSN4a8TGV6zTDc3k=",1767702045038158681,-7638872967861405897,-658936640198504536,3257763951224382581>()) {
                           case 989746068:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var4;
                  if (this == LAVA_BOX) {
                     label69:
                     switch ((int)com.yiyiaddon.m.b.a<"sfyp1yzhnqd20","PPZ2njbjRHI51rZIqIycuaFKli9+0+yTP9x0UTL7UG8=",-1922801520001708167,-2386350463077691918,5105202125420875288,-2013328808363826792>()) {
                        case -23321584:
                           var4 = (String)com.yiyiaddon.m.b.a<"s1n85hewd3wnvn","jX2yYc2lMKU0EkNGJ/a887WzHMeSMRSghh5FUhkLDem0HqWaQ/M=",475281588093400316,3438383381121959556,-2019360210381237571,2504981065228338389>();
                           switch ((int)com.yiyiaddon.m.b.a<"s18x3oocei88o5","RQvf5EcIYIgDvcm6aNpcDfjFYNfIzYT72yvar9XQHE0=",7635344005987970241,-1585968579247110810,5744899664552094931,-4548926315885103090>()) {
                              case 998991527:
                                 break label69;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var4 = (String)com.yiyiaddon.m.b.a<"s2w2wcm6botb76","tsdGaKUgiT405QJOW06Y6/kp48UX3zcSA7ZFcRC272kBsZXgqb4=",8798233073539033098,3832064485225093787,4095409886617859382,-1512627629522102979>();
                     switch ((int)com.yiyiaddon.m.b.a<"s1tzgmyw76gqw5","6kV3hwNto/CnvoQa7KtSSimr8Qbrz9Nv6vXpJVK8yKs=",4208192702468846166,7033472964661821326,5852595277762870496,-3499465716816578477>()) {
                        case -778947945:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var3 = var4;
                  var4 = this.rX + var3 + var1.m() + var2.D();
                  switch ((int)com.yiyiaddon.m.b.a<"s3jfdhwuodpl2d","f4yWvhabdlCdqWQNlQBYPrDjfp9B7/b2Q6D59vfDauc=",26537901648198644,908020726192599672,-3041696763469304732,-7963318237724979053>()) {
                     case -249402140:
                        return var4;
                     default:
                        throw null;
                  }
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }

   @Override
   public String toString() {
      return this.rX;
   }

   public static d b(String var0) {
      d[] var1 = values();
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1o3ln8gx80dyp","KG7rIRhneSAuIoNlqIrW6N3lvEGqsZXQzeXjIhOOxhw=",3736164862622526995,-5512612876789481916,501248571243116114,-4677613274511036909>()) {
         case 981903533:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s2dsdvp935k6jn","szujcVPJu+9KA1h6AJsGSr7Fdbipp4lBzHj6IfjESds=",4790201616499317558,-7910851271646735706,4880744508562783773,-3397784255663747241>()) {
                  case 195658220:
                     d var4 = var1[var3];
                     if (var4.rY.equals(var0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sh30f0vg8w6ly","kwHawP9o0/nHJRLwJYpm+cagV3WLYeLdzoMX4iAKIHo=",1320790026090301162,-2820840366433025951,-4995721188318610899,-1698878424961419629>()) {
                           case -1425651829:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2kl5lg27v2l93","fQha/C9MTShRayJEPvvQNO7C7qBaPwZRHrod2obuNCo=",-5443019987144873443,-2903573275114965750,8634744754411703220,7092112676622731405>()) {
                        case 1303798466:
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
}
