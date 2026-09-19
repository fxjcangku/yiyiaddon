package com.yiyiaddon.e.g.g;

import com.yiyiaddon.e.g.e.b;
import com.yiyiaddon.l.g.a.f;
import net.minecraft.client.Minecraft;

public final class a {
   private static final float ay = 2.0F;
   private static final double x = 1.5;
   private static final b[] b = new b[]{
      com.yiyiaddon.e.g.e.b.BOOK_STORAGE,
      com.yiyiaddon.e.g.e.b.LAPIS_STORAGE,
      com.yiyiaddon.e.g.e.b.OUTPUT_STORAGE,
      com.yiyiaddon.e.g.e.b.ENCHANTING_TABLE,
      com.yiyiaddon.e.g.e.b.GRINDSTONE,
      com.yiyiaddon.e.g.e.b.AFK
   };
   private final Minecraft z = Minecraft.getInstance();
   private final com.yiyiaddon.e.g.a b;

   public a(com.yiyiaddon.e.g.a var1) {
      this.b = var1;
   }

   public void render(f var1) {
      if (this.z.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hhoool0986vm","okKEntodx04IFs3CZN8TKU5LvEwGNgJ3Xhfdl0e192s=",-3765142979885035694,-472911535399274046,5505978544165328799,-4994767172632675421>()) {
            case 50693794:
               if (this.z.level != null) {
                  if (this.b.a().bf) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2gqgmmsp1s9kj","4dGreLRewrGqzOg9Ezb+meDjCaolGmJRcrAQbggOmoQ=",6545432907469590690,-2771009043225846846,8501587755672908688,-5919533353927570076>()) {
                        case 2096645025:
                           if (this.b.a().aS()) {
                              b[] var2 = b;
                              int var3 = var2.length;
                              int var4 = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"s2b8hvq6fvosht","nnr1J4xK8o5OI0zAYLbUBeAGjIfk/37PT4AmHxola0Q=",8780741073015470087,6005907210225042176,-4649353568946553879,2978538460931824298>()) {
                                 case -656175742:
                                    while (var4 < var3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2lfh8og7htdkh","r1HmB0g7e9Ya5o+D02/2Dtpyp+mr7aib1eIoqIzfjv4=",7693427237093965902,9100988141707277883,290976117367818765,-435152415411313065>()) {
                                          case -1447627054:
                                             b var5 = var2[var4];
                                             this.a(var1, var5);
                                             var4++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s3vmcpkjisyb0e","EQDMwwMyzQmFxmDgUsDXFLF5Be028vsj3kqachE5jOE=",-1822022923558941305,-280557384596549023,4626048273736247818,-2359601710390788847>()) {
                                                case 1157036081:
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

                           switch ((int)com.yiyiaddon.m.b.a<"s2q2ag4fyhl54t","9N0BrDXZeV6zdCr2umeKjBLD9hGTl2Mm1H48nIUZec0=",8083813606796231335,-8964610181396414330,8976757025670414959,-4769536820434485803>()) {
                              case -285779618:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2f5ki8up6z9b2","Cbr6hjyqrMBvrNcJKATSbBuCTD1HLAg2GE24ZgwRsj4=",6287483019350293400,1508654839028792613,6504234490492684777,-4604608443213242222>()) {
                     case 1870464352:
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

   private void a(f var1, b var2) {
      String var3 = var2.aO();
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3io1yu514fjz8","3dBr0les18zMZh+OZwtz1jQMBnjYihtwY6yMQna3920=",-2286816987218191279,1431011650349739268,3148713436491680968,8124230866499169878>()) {
            case 922347599:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.g.e.a var4 = this.b.a().a(var2);
         if (var4 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"shc7b2eepk64r","qtU5NRfUAVauxHP0fBp0hP7E3e8Sw9epEOIMpNH3kzA=",-9148740864821801417,757964978355914703,-2854220451519832711,2916717570360046064>()) {
               case -410852038:
                  return;
               default:
                  throw null;
            }
         } else {
            var1.a(var3, var4.aj() + 0.5, var4.ak() + 1.5, var4.al() + 0.5, 2.0F, var2.am(), 1.0F, true);
         }
      }
   }
}
