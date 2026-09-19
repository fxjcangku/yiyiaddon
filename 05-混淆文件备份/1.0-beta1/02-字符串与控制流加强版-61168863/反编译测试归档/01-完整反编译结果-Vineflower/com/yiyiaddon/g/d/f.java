package com.yiyiaddon.g.d;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public record f(Map<String, Integer> aS, Set<String> aD) {
   public static f b() {
      return new f(Map.of(), Set.of());
   }

   public int di() {
      int var1 = 0;
      Iterator var2 = this.aS.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sqa37jujiwrzc","AGSdOtXDo0jEpT58RedFB3R3Pm/bsfDxDD2MRvi0k7E=",590812248039049612,-6420215441306913126,6921381623191523059,7217190680233920922>()) {
         case 759671693:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ndbco8t6yiji","teR1vibL+4HIe3eNMQh5Gj3xhRNns+S16cQVWHNTdYs=",-4654723381970346793,2854182355029535903,-8354956267939592497,8286640550734712069>()) {
                  case 74486769:
                     int var3 = (Integer)var2.next();
                     var1 += var3;
                     switch ((int)com.yiyiaddon.m.b.a<"stqcpawj2lvl0","SbUK/XB3IttrCePr/W5LUPBK6KdScC+1Ou9fKiGs/Ok=",-342547631579778746,7659266363561500554,-4851131471201616079,-4572839405067292618>()) {
                        case 138169885:
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

   public boolean a() {
      if (this.di() == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s4fapz13rdv6g","ESJy6ojL4StO3gWp4kKpsQxYp5Ir7KTwzoNdkrN6y8M=",4997486972773228206,4288367225073755867,5051282716868812245,-5778459848147396036>()) {
            case 1666519911:
               switch ((int)com.yiyiaddon.m.b.a<"sm28503xvq8ax","wqhakBISY5u4nRnVRXhkmmjHp3gHIDPHHCfiBO3NBH4=",418778052249876923,5122403806417259110,6763887563838536837,3405088827188000868>()) {
                  case -1939480757:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1gnux0ttxiygd","XYjLdfmzvym2nRBx5AGRlBGW22IZktTtDoTWWVuTxEk=",-3948927744544017252,-5294506637363817154,-4484815761170930127,-3966953592115053792>()) {
            case -553548479:
               return false;
            default:
               throw null;
         }
      }
   }

   public Map<String, Integer> x() {
      return this.aS;
   }

   public Set<String> a() {
      return this.aD;
   }
}
