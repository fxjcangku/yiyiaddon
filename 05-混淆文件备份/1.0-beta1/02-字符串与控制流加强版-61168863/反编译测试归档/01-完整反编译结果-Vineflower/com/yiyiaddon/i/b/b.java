package com.yiyiaddon.i.b;

import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public final class b {
   private b() {
   }

   public static String c(Entity var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3skjax3fvv70","krZmmYMNr6Z3envJvr98y9CN8qGvSokDSn/TjaNiEr8=",-2923824722066440329,1847160426672524598,5282712180405448697,7404804455990182498>()) {
            case -167629002:
               return null;
            default:
               throw null;
         }
      } else {
         return BuiltInRegistries.ENTITY_TYPE.getKey(var0.getType()).toString();
      }
   }

   public static boolean E(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1l7feme0g6kr5","VP36JrILsx59iF8kkGFMSml6yZaezlrfYfZbjVPq47U=",7903063935565314987,5268469061458975948,-759621259832976008,4790260182659913782>()) {
            case 1542231367:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1bju01yj936ac","Ocd3PVE+QdVVttSnMQU3Wx0VNpDvZC6n4MwrFq7H5IQ=",8126028221320279144,-316072721494016031,-5856778395059389697,-5208599491166230998>()) {
                        case 1410183146:
                           return false;
                        default:
                           throw null;
                     }
                  } else if (BuiltInRegistries.ENTITY_TYPE.getValue(var1) != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s12vuhhgh4rs0y","GS8MEY3+SHooQr00BByLxFFFwQF3sHPed1NS/kS/ACs=",-3913448964955800846,-6738189188725122807,8034927483819396071,8263974558601203302>()) {
                        case -1788033227:
                           switch ((int)com.yiyiaddon.m.b.a<"s2s8eaelvy4x4u","espOcbm/os0OHAw5pS5VwiJaDqOhAvy1y72wz//Am2Y=",-9005080131944916246,5075203999361823280,7936188035496387660,5427197115295705658>()) {
                              case 1836840766:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s245b3p6blia4j","QSr1eFucX1ZJW7pUtnJveX47q0dg4WnKacMyVCj4KDo=",-8982049629597968197,8500596295503709823,-1566978034079338418,7739935693248150332>()) {
                        case 1799272220:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3mui3lgh1dk40","RbcciR+/8lzgBJERgHb7Yornvxz2R1QLiDVC2dTR6vQ=",8279963700212790034,3211196986146610193,6154846284606770817,3737475070239308569>()) {
                     case -928051810:
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

   public static com.yiyiaddon.g.c.b a(Entity var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3d7b9d1hwfsc9","x1/r4vS0Vy0bFKW9JNn/plOTwjpF2q/EDb5VpayYWrY=",9002851458813133859,-5283944284208341771,8640458582360331267,-4347716826911865001>()) {
            case -1659077054:
               return null;
            default:
               throw null;
         }
      } else {
         String var1;
         String var2;
         String var10000;
         label47: {
            var1 = BuiltInRegistries.ENTITY_TYPE.getKey(var0.getType()).toString();
            var2 = aj(var0.getType().getDescription().getString());
            Component var3 = var0.getCustomName();
            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3lwkniylxtd94","ihq6HBRjGJ3fWCGMxylIGTsYon1Z9roOvCmJorug+e4=",-3008162364743743834,4302234988370703092,-5323856770439563064,-8006420674570159298>()) {
                  case 1825423520:
                     if (!var3.getString().isBlank()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1wessl8e69zzl","rFb42Qa9Z1u54C70Tony7XBiGFWQoF4w6h+LXSJWAsU=",-8812069434801426248,-3424862250440759719,2062578016082529106,-7736258880029465154>()) {
                           case 1581755372:
                              var10000 = aj(var3.getString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2ppn741rg30jo","8EUpIMNu4hQcJUEEMTWSy/8Ey7St7iQE8EtvLXEC57k=",3346553752461931620,-4216969733697402517,-7690298077876830007,-2061715258160159185>()) {
                                 case 668685962:
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

            var10000 = null;
            switch ((int)com.yiyiaddon.m.b.a<"sb349tyrh2pkz","WMoV1WPl4LjeowAmRSsPAvz8CAqxsVtvgtBw3aAmzg4=",-7328715364768839453,-6329185317192001995,6245769954696092337,-3717474524009898773>()) {
               case 1001967853:
                  break;
               default:
                  throw null;
            }
         }

         String var4 = var10000;
         if (var4 != null) {
            label30:
            switch ((int)com.yiyiaddon.m.b.a<"s2gzcpytmjrq51","kbWROn8Kh/PHgMNFvJh/KBzxfgL9RUdYGDbRmusxvFg=",5107682033726431317,-6404576306239724419,-4705152645119787597,4250072736798756481>()) {
               case -955395297:
                  var10000 = var4;
                  switch ((int)com.yiyiaddon.m.b.a<"sgfidq4jdylai","59C7SvZrDF4nTrmdouTZp29xOQ7gXlnrVSeKinKhplI=",2301063731079657455,1581192965216183592,5783623162271095457,-203762958729986013>()) {
                     case -1952457097:
                        break label30;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var2;
            switch ((int)com.yiyiaddon.m.b.a<"s1iq2r1i84xdqt","1kE1N1oiS4VUPsujkOwA5TrJiLMaPkxZnIDF2M93HKE=",-3968224348363519939,7782227068495450005,2471147088331815357,1626329512451446399>()) {
               case -1430194930:
                  break;
               default:
                  throw null;
            }
         }

         String var5 = var10000;
         String var6 = var0.getUUID().toString();
         BlockPos var7 = var0.blockPosition();
         return new com.yiyiaddon.g.c.b(var1, var5, var2, var4, dz(), var6, var7.getX(), var7.getY(), var7.getZ());
      }
   }

   private static String aj(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2i7ofqvf3qq8s","KAihKR3XJv1XDjAvic1V5VrQWRjlGDQCH6d2fxWBDbM=",-6886719215303903888,-6400170952421301560,5883230199190186615,-578203129962361950>()) {
            case 170105027:
               return (String)com.yiyiaddon.m.b.a<"s1g56kgthh8w6s","oasjG59O+fD7hlpVYNhBOG3dFlScEEuZoq3oEA==",8976357483873154456,467807072323040947,-5673105185186325443,6427151486746935583>();
            default:
               throw null;
         }
      } else {
         return var0.replaceAll(
               (String)com.yiyiaddon.m.b.a<"swv5zfiftm14y","oostRux0zNx1RN0EPoPUBNTO5F0rt6z5Q6Tjz+IzP0yCT0pRL1K6FTCY8i2ABA0zIHqJVghfj5urknhPUL6k1MT2IOSIOw==",-3151684311139705687,-2083344575771908110,-350446385140935682,-2079785099522630011>(),
               (String)com.yiyiaddon.m.b.a<"s1g56kgthh8w6s","oasjG59O+fD7hlpVYNhBOG3dFlScEEuZoq3oEA==",8976357483873154456,467807072323040947,-5673105185186325443,6427151486746935583>()
            )
            .trim();
      }
   }

   private static int dz() {
      try {
         return SharedConstants.getCurrentVersion().dataVersion().version();
      } catch (Exception var1) {
         return 0;
      }
   }
}
