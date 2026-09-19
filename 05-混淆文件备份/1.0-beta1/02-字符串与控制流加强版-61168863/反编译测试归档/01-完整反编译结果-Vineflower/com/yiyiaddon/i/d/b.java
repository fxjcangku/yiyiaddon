package com.yiyiaddon.i.d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;

public final class b {
   private b() {
   }

   public static boolean fu() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sd92t9geayqf","uRLXaLthnff+Sd34WO7aHS/dntDYD7eRKqttiwbiesM=",6020026580584821965,3681985000461019651,8199366013675093903,6233018977132691987>()) {
            case 1339793783:
               if (var0.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sxg99fdcjzik2","/1wf6jC+FOBbBwcqQ3nlooncw/NUIfScdErBarA+Kuk=",6794504576586643923,6523506206588296070,6406673035118612603,4585872411885247331>()) {
                     case 1101161739:
                        if (var0.player.connection != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2zntj2uzyrp3z","TstH0P2Dl0xXarH8G9/AORjHrHrZ7Gns5p0clYo2N7Q=",-8752072883033935035,2149859183055156010,675997085672468250,-4801629996555962478>()) {
                              case 769887792:
                                 switch ((int)com.yiyiaddon.m.b.a<"ssnbidn5iyih","PyROhzIyp6MYOBPn3g6Fa8m9+xC29M7t2kVSJxi3VN4=",6021356449053104274,8518478424624721681,6281295874877419356,4608433961444984099>()) {
                                    case -1227019187:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2fni4exi4jqb8","7oAq41YCMLy+vsl9m3Yka+eKWlkUYe7HIS8N6aCOh38=",-5026236057153647491,6184542835464435504,3778015655413853040,-7038893194457558526>()) {
         case 1132613182:
            return false;
         default:
            throw null;
      }
   }

   public static boolean c(Component var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26svbspfjfyzc","YESBUmxX6kax2227jjocVMWoroo37L9ovQp5jgL+iIA=",1951708550754085009,5371755280485697652,7774606249931609462,4598070966142168242>()) {
            case 1466567742:
               return false;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2y01w7lglbx8c","RhTMCxtZSMZNiEITwL3HPc/WsDioSJuKXZEq4nPvNxY=",-8275063162812587645,-3310964356231163661,-1345024015116907251,5563346078381559795>()) {
               case 1305357442:
                  if (var1.player != null) {
                     ClientPacketListener var2 = var1.player.connection;
                     if (var2 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2mjyo541ulq6m","DIGv9Tht4pVbaZDNQkwyfrad9xHe/SGyUjdtViYIWcM=",8972315222458752811,-3344861147067677091,-2234389944680643416,5642588350511510503>()) {
                           case 1848707256:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     var2.handleDisconnect(new ClientboundDisconnectPacket(var0));
                     return true;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s13h7lr8pvkbsx","wdHTNMXbc3SuYjFihS+J6WHZCfqIINfqvWSXuI/mQbI=",-2452818976477640118,-6007915270106338456,124655541844650768,-7019698636482792431>()) {
                        case 1614471745:
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
   }
}
