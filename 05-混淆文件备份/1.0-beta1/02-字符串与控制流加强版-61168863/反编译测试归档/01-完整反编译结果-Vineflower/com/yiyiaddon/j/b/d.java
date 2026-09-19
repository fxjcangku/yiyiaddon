package com.yiyiaddon.j.b;

import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class d {
   private final Set<com.yiyiaddon.g.c.d> aI = new LinkedHashSet<>();

   public Path k() {
      return com.yiyiaddon.i.f.a.f();
   }

   public void C() {
      this.aI.clear();
      Iterator var1 = com.yiyiaddon.j.a.b(this.k()).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1kh7kv8z5dcn5","jMW9BFNdEMqf3I8Xvp/jtIKiXEcOOc1qE0WPGmk0IW4=",8986520695987556412,1224601428994383078,-5847005651972686870,-3610178579376447586>()) {
         case -76858643:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2boy4fzd2vrbx","a+54sV3JRU1RzVCHXLlI0QRSZBsLB8qOKAStbGn9BA0=",2399334608608691553,-6972668355046770049,6160595729582707085,5712202304837271196>()) {
                  case 1826018661:
                     JsonObject var2 = (JsonObject)var1.next();
                     com.yiyiaddon.g.c.d var3 = com.yiyiaddon.g.c.d.a(var2);
                     if (var3 != null) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s2ralqioz5zcql","9d90e6fcwmjZzUS7pQ88+Yj1KA1xMgP5p+feHdElV+8=",6619454184450692662,2756160125658210918,7772686798877480098,-770761128707517942>()) {
                           case 297944188:
                              this.aI.add(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"s1rp4bdnvna22x","Z/oS3EFGBXP/L5pY3zCb+J/1f2hXAJZh29I2v1TDWi8=",-6362021948964919935,904038668992173811,-448453902149251749,2032110288653324763>()) {
                                 case -248934567:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1wcd8ubqxpxan","QVJ6a2/Q4iG/XIjcLVdhwL2oRtIcm7yt4vwjjmsc5eM=",-353178145308970514,-4606817052696236492,7204259750261465017,6264575609324689732>()) {
                        case 1805918351:
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
   }

   public String d(com.yiyiaddon.g.c.d var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29b0ojk3xljid","oj+dZeVxfbvHovTPk6z/6EDbfx1GSzmPZXgAnbXHICo=",-6682395790815800574,552861709947340088,1370173140377555957,2914406217606098829>()) {
            case -790006414:
               return null;
            default:
               throw null;
         }
      } else if (this.aI.contains(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lnmd3pdhk04f","UGqdNH5vV1FwRT+omYx0h/nZTDZM/tpUUOBz9mK4EvY=",-1448031356054747970,5407619116930559499,2872611690830016699,-3265723788409972662>()) {
            case -1727497302:
               return null;
            default:
               throw null;
         }
      } else {
         Path var2 = com.yiyiaddon.j.a.a(this.k(), this.e(var1));
         if (!com.yiyiaddon.j.a.a(var2, var1.f())) {
            switch ((int)com.yiyiaddon.m.b.a<"s2epxab0pg1act","1roCDyZOJQ3FPAOKsu059I0qAsZEXJp5KtKXN+mzbUM=",-6963584535420498961,-8059808450146446070,2131308242560573765,8153824376076041660>()) {
               case 340392403:
                  return null;
               default:
                  throw null;
            }
         } else {
            this.aI.add(var1);
            return var2.getFileName().toString();
         }
      }
   }

   public boolean a(com.yiyiaddon.g.c.d var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ztsdscg077s8","oyNZo6nHSqiQl34mlwjlr1jsneE6/e0CvjdQgYik9RM=",668213154856326433,-885140883201181019,7161581868004675264,6878991441799764230>()) {
            case 142869358:
               return false;
            default:
               throw null;
         }
      } else if (!this.aI.remove(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jdjm3766mol9","NxMnHWcJmPmQHVrArGgX2ZQ8i0Wht7MsxrACrS43Ks8=",2025822269456473719,6148681965963799088,6775294398705018773,3976031170950125074>()) {
            case -1068384294:
               return false;
            default:
               throw null;
         }
      } else if (!this.fy()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kujht22kjcns","aXUUFulZv0R+MKshXdB1A1sr3TOg80jdQLmT4oAvzIk=",-6731496775458539947,-3904008640855592885,-6194068440062746756,5783822877422485197>()) {
            case -1516598414:
               this.aI.add(var1);
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   public boolean fx() {
      LinkedHashSet var1 = new LinkedHashSet<>(this.aI);
      this.aI.clear();
      if (this.fy()) {
         switch ((int)com.yiyiaddon.m.b.a<"s7xfsls5q0g19","uOLiRU9Zp+gyHTopfLLYkcCi1IGIn6bXqwRElHFJov8=",1858589003428779677,4017916944307869128,4900205364438226548,3694272843768084181>()) {
            case 1254211512:
               return true;
            default:
               throw null;
         }
      } else {
         this.aI.addAll(var1);
         return false;
      }
   }

   public Set<com.yiyiaddon.g.c.d> A() {
      return new LinkedHashSet<>(this.aI);
   }

   public com.yiyiaddon.g.c.d b(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s120sggmbcmal3","PU0MmR/DL1U8YRTvG0qoxbowrd2N714g1tc2e9Zjab8=",6278069092215024700,-6283542906016808249,-439720313718605706,6021418316094861384>()) {
            case -1107876233:
               return null;
            default:
               throw null;
         }
      } else {
         Iterator var2 = this.aI.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s37pp3berke5u","tkQAaSY4i5LxLpnPuwDaeg6xL7+rKqFbBcDAk8orwic=",-1557849625724067823,-1492130913649258335,5749592737156942184,6881543181828149885>()) {
            case 104799611:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2txj5iz825uqe","paVV7tybW9FSi26A4TVYMdma+DC1gapuO0Xzidpn894=",4138608353860206241,-6801661185411376685,-755988298020507374,1481447652480891608>()) {
                     case -1555371375:
                        com.yiyiaddon.g.c.d var3 = (com.yiyiaddon.g.c.d)var2.next();
                        if (var3.dF().equals(var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2dpjl0mycfiq4","bTaw1f7ZdRNGjeBaQokL4/Zs34XQivKjhuRDaPa/8eI=",7848431652878574345,-5248359314512888578,8920602323822808345,-6033886501404971573>()) {
                              case 1649246160:
                                 return var3;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1qsrxqxplkfy5","yXRE2gEJ2RMNzoEV6xkB4pX6f27qKq+TpwT+ZQKU0N8=",7171164883518411361,8742727903539434585,-1387227101654185087,-5926914477306965566>()) {
                           case 423731423:
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

   public boolean dt() {
      if (!this.aI.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zocc16gr3mti","tonH90aHUnFCDJGur5+ZC3jH3O3QeqEQErqwXgGhhmA=",6203084018553193752,1235494541980188214,827956031146441112,5194761111552031633>()) {
            case 135948417:
               switch ((int)com.yiyiaddon.m.b.a<"s1tyvi616lijwi","xEUcEuBftjDJowIUOqafSao13Z0IoMRugcWmu/97Xn8=",9180142343158977099,-1386626387726055955,-3955490650182140391,4856754051613820430>()) {
                  case -525527469:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3lqes1x28rzo1","6PqBlmyV7ZcQzcV21BFp9nIeHSMluNGPemVRegf/R8M=",-3572550987940574546,-8120814869345968452,-4236589788970144714,7603629551664021788>()) {
            case 362943921:
               return false;
            default:
               throw null;
         }
      }
   }

   public int a() {
      return this.aI.size();
   }

   private boolean fy() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.aI.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1872gfjj2b19p","fdu1P/p5g0w2xhCqL4o9r9XW3kZ9csppwqc9S0U4sc0=",-274021410452385267,-8572304816982859151,6841702607329599850,6655678896529360453>()) {
         case -678535429:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3gzqmdfj2pbko","b41eIcJI2T6UFuxBzqfcpDsttvxeKB8/Q/3429oAda0=",-5360283703881773998,-2896686146463372840,6792143776285843326,-7958940485279134>()) {
                  case -267547749:
                     com.yiyiaddon.g.c.d var3 = (com.yiyiaddon.g.c.d)var2.next();
                     var1.add(new com.yiyiaddon.j.a.a(this.e(var3), var3.f()));
                     switch ((int)com.yiyiaddon.m.b.a<"syg3lh84don0o","gccAZFoYl8WB8DwzDon4OwNvckEkcxm/SoTCJKWKRnY=",491633354926134314,5203365452072594149,-7302852891675805082,-3103010512620094749>()) {
                        case -1603919502:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return com.yiyiaddon.j.a.a(
               this.k(),
               var1,
               (String)com.yiyiaddon.m.b.a<"sul47k857hycr","DgzW14tmr1moZz4ymgMhu1OC2/Ql91K4fXiuQn1c0pVRjWIqYnw=",-6460251945954668194,-3829326835633104911,-4341365397474099097,-1936600774574056856>()
            );
         default:
            throw null;
      }
   }

   private String e(com.yiyiaddon.g.c.d var1) {
      return com.yiyiaddon.m.a.B(var1.m(), var1.be());
   }
}
