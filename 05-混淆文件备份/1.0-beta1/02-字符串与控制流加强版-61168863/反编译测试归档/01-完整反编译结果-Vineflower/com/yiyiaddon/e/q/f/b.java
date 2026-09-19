package com.yiyiaddon.e.q.f;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class b {
   public static final String zB = "minecraft:overworld";
   public static final String zC = "minecraft:the_nether";
   public static final String zD = "minecraft:the_end";
   private final BlockPos T;
   private final String zE;

   public b(BlockPos var1, String var2) {
      this.T = var1;
      this.zE = var2 == null
         ? (String)com.yiyiaddon.m.b.a<"s3hnkk2fcdzu8","BEhgG7yD4cWPhk/FF24XUPO5l5+9B8GKtB4XTKpCmoYhQeuVuAO0z8iKZSCRe+N4e1fhs0zHLFvkQYR/CXMdkN2+",7013502890268546043,8980101718985323604,-286480854004751618,-1117249086683360916>()
         : bs(var2);
   }

   public BlockPos a() {
      return this.T;
   }

   public String ec() {
      return this.zE;
   }

   public ResourceKey<Level> b() {
      String var1 = this.zE;
      byte var2 = -1;
      switch (var1.hashCode()) {
         case -1526768685:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s2ao7jjxsn7vjb","EY5wHFCpM1qP7Cye9JP7dcET4Vs9CvYTyErd4Dq/wWpHxdGXqIrzJZ5Fsg/SQGNiA6F4gXRaET4PwSj8w/zbKOqY1YA=",4941990871892047095,-3757771447079776379,1861269708163686924,4471057647386031683>()
            )) {
               label34:
               switch ((int)com.yiyiaddon.m.b.a<"so6aaqxu9ryje","LRRTEiLSC4lZyqswTKXgYBrZDSMpDp5K8nK2kd07W84=",1013817668461925228,-7862095744864813929,-3623710901348909314,6454647799047713171>()) {
                  case 741544389:
                     var2 = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s10rfky0qh1v5a","QROAtm2IqsQz/lQV7PrJiIB6GtINYRwmbGAc5IzBw1g=",-4949255689748929711,-7663242237901300816,2720327811484853037,6749595738671137141>()) {
                        case -107922982:
                           break label34;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1731133248:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s2y1ktjmii336s","cUfO7Zpu/1gBc6Yze2/IWfZQ/zn4G215rKy3/v8n4QfYZnejUC4hRy36Do0c6j78ufU77NZ4QbrznD2fCr4=",6750791197308645050,8928051068043547229,8209736236514751718,-8245552917841809087>()
            )) {
               label38:
               switch ((int)com.yiyiaddon.m.b.a<"s35hti2zwfheiq","mqgC/JcFiP8arI5rs1a5ARL26ghv5dT3X5S+2CVCDoA=",-8800052461158058576,3398064889859678229,-622090800064372254,3834372555125070562>()) {
                  case -853445360:
                     var2 = 1;
                     switch ((int)com.yiyiaddon.m.b.a<"s2d54v48q3u8xc","G/t3g19uDIIfiQAaj+0AIGXGkeMguCCoc4iHVxstlQ4=",-2179683913067746137,2299382180520561592,-6750728979785912636,1776564361617630869>()) {
                        case -1377359395:
                           break label38;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
      }

      switch (var2) {
         case 0:
            ResourceKey var4 = Level.NETHER;
            switch ((int)com.yiyiaddon.m.b.a<"s3ieyurkmj4f0f","ykBHUlQQ6697cfxqWbme+K7po3Wyjd179E/G3ks6BIA=",2578117384373105313,-2975717664742875881,5602332506737405101,-2749212507070598991>()) {
               case 1845114134:
                  return var4;
               default:
                  throw null;
            }
         case 1:
            ResourceKey var3 = Level.END;
            switch ((int)com.yiyiaddon.m.b.a<"s20yb4eyn4foac","4UrBJDhbP0Ds6b17/8Jur/jZKxH3/3cIHUBTagZWQeM=",2253287819315835486,-5773845460557515631,5418214588917144924,1742365277074279127>()) {
               case 1920606732:
                  return var3;
               default:
                  throw null;
            }
         default:
            ResourceKey var10000 = Level.OVERWORLD;
            switch ((int)com.yiyiaddon.m.b.a<"s28c11am9ey3pc","cSldc0DAo6+3siUnEA9XxZxz/hASxDioj6fx3N8mSPg=",4219583911218845118,-147939267546120665,7752029990710345221,8309254490115310568>()) {
               case 2006333188:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   public static String f(ResourceKey<Level> var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"shafmx3d4kk9f","abaSFYfg6Y0Thci+R/HTVzuQeVwlukLEbTOQuTJ6IrI=",5260986490827004790,3673291580502799418,1168034736527634175,-3163236889844389824>()) {
            case -378766851:
               return (String)com.yiyiaddon.m.b.a<"s3hnkk2fcdzu8","BEhgG7yD4cWPhk/FF24XUPO5l5+9B8GKtB4XTKpCmoYhQeuVuAO0z8iKZSCRe+N4e1fhs0zHLFvkQYR/CXMdkN2+",7013502890268546043,8980101718985323604,-286480854004751618,-1117249086683360916>();
            default:
               throw null;
         }
      } else {
         return bs(var0.toString());
      }
   }

   public static String bs(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"svci4023r1p3j","TTqqcTMsHouznWlplCiGfi1Fi/R016TQDHujwWMnLO0=",7128118062969175726,-7811772152690094448,-2392383771492468141,-3809107474156659031>()) {
            case -1320855026:
               return (String)com.yiyiaddon.m.b.a<"s3hnkk2fcdzu8","BEhgG7yD4cWPhk/FF24XUPO5l5+9B8GKtB4XTKpCmoYhQeuVuAO0z8iKZSCRe+N4e1fhs0zHLFvkQYR/CXMdkN2+",7013502890268546043,8980101718985323604,-286480854004751618,-1117249086683360916>();
            default:
               throw null;
         }
      } else if (var0.contains(
         (String)com.yiyiaddon.m.b.a<"s2ao7jjxsn7vjb","EY5wHFCpM1qP7Cye9JP7dcET4Vs9CvYTyErd4Dq/wWpHxdGXqIrzJZ5Fsg/SQGNiA6F4gXRaET4PwSj8w/zbKOqY1YA=",4941990871892047095,-3757771447079776379,1861269708163686924,4471057647386031683>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s3461czbk0h4g7","VZJEABpSC+7YpwLSNN2QWcN5IkocWL5M6QMNBKRJArI=",1024073508102071028,-3287034890931537396,1784336394976594175,-4626954111886223516>()) {
            case -717148799:
               return (String)com.yiyiaddon.m.b.a<"s2ao7jjxsn7vjb","EY5wHFCpM1qP7Cye9JP7dcET4Vs9CvYTyErd4Dq/wWpHxdGXqIrzJZ5Fsg/SQGNiA6F4gXRaET4PwSj8w/zbKOqY1YA=",4941990871892047095,-3757771447079776379,1861269708163686924,4471057647386031683>();
            default:
               throw null;
         }
      } else if (var0.contains(
         (String)com.yiyiaddon.m.b.a<"s2y1ktjmii336s","cUfO7Zpu/1gBc6Yze2/IWfZQ/zn4G215rKy3/v8n4QfYZnejUC4hRy36Do0c6j78ufU77NZ4QbrznD2fCr4=",6750791197308645050,8928051068043547229,8209736236514751718,-8245552917841809087>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s3v7jqgk9x07oc","QPheV+y9J9nY8G6dB77v1Gopc7B9lta6f5loM88nQLs=",601982265754267007,-953351256618410640,-4458258865593023954,-5253943887468252510>()) {
            case -756218156:
               return (String)com.yiyiaddon.m.b.a<"s2y1ktjmii336s","cUfO7Zpu/1gBc6Yze2/IWfZQ/zn4G215rKy3/v8n4QfYZnejUC4hRy36Do0c6j78ufU77NZ4QbrznD2fCr4=",6750791197308645050,8928051068043547229,8209736236514751718,-8245552917841809087>();
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s3hnkk2fcdzu8","BEhgG7yD4cWPhk/FF24XUPO5l5+9B8GKtB4XTKpCmoYhQeuVuAO0z8iKZSCRe+N4e1fhs0zHLFvkQYR/CXMdkN2+",7013502890268546043,8980101718985323604,-286480854004751618,-1117249086683360916>();
      }
   }
}
