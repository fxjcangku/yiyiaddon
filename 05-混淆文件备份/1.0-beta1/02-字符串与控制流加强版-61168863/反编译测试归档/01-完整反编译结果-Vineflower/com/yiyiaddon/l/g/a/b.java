package com.yiyiaddon.l.g.a;

public final class b {
   private static final String[] am = new String[]{
      (String)com.yiyiaddon.m.b.a<"s327gkka0ajmnf","9d3CPBeWmz96w00O2v/292lejW1fJb8z0jbMFO/7",2709013467428848075,-2046877803766417571,-7440684836699116438,5785100074534579641>(),
      (String)com.yiyiaddon.m.b.a<"skj7pgek5d2f8","rp7Z3Yk+JdE6vc3GVM68k5FdAe+OVrfIrdBZ6RXp",-6415241309764785358,9069679798719351650,1579191517555062828,-5357754325593881188>(),
      (String)com.yiyiaddon.m.b.a<"sgpqi9mpjgroj","NlyouXqu+68OfPrIKlzXNZJ/LFZCG4XwITe1Kvz3",1377057083786974424,-5107083220417632221,4627665536640930701,-2883061364374847110>(),
      (String)com.yiyiaddon.m.b.a<"s2w78t85ixx3mp","YyUSiTnTRTyP6Ws5jW3ZVdWrH8eBTaSX7w57i+1Y",-2792389820601378010,5946956685286587165,2174041451792051193,-6561583777141069626>(),
      (String)com.yiyiaddon.m.b.a<"s1svgfanvphhas","bTogUl4w5bWPS6tM96r0ldZqyX5UoWUl0yRaFBol",-2280053494414288317,5924083250429068486,-1845037613969772613,-4571705968995352262>(),
      (String)com.yiyiaddon.m.b.a<"s3q19q4r2x4p4m","MlvgDvqWUTdDnEXQwKX3p8GfxKPGS/Pyd3uif7cd",-1567418843529990036,-5480354621769509001,-1294852557420766330,-2138131563809312258>(),
      (String)com.yiyiaddon.m.b.a<"s2ja8csuwt2gl","yiM+5bQVtgPOX4l+cFavq2Ng9iET7y5kpSqiYD0H",-4029964252552686710,2300158589739036879,-316195360153646673,-51378075831889358>(),
      (String)com.yiyiaddon.m.b.a<"s33i8jxhjm5r57","nZp6VjTvLeTG2bWFXnte88Zgkpf+3UHt1h676W+b",-9032951841083345842,-2956678627973183368,7003457488206379954,-6288441041318794477>(),
      (String)com.yiyiaddon.m.b.a<"s1y4rizlu3k3gh","XpA/+WRcuclDAHc2vKHdSmedVr7zrxCEmRVM+xDg",-8768208223498287458,9169030588903459993,9088007691079961407,-3307031164559326804>()
   };
   private static final int[] bv = new int[]{16731212, 5046092, 5016831, 5046271, 16777036, 12602623, 16777215, 16751692, 1710618};
   public static final int ud = 48;

   private b() {
   }

   public static String[] f() {
      return (String[])am.clone();
   }

   public static int b() {
      return bv.length;
   }

   public static int q(int var0) {
      return c(var0, 255);
   }

   public static int c(int var0, int var1) {
      int var2 = bv[r(var0)];
      return (var1 & 0xFF) << 24 | var2;
   }

   private static int r(int var0) {
      if (var0 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3w4nvmq56ywei","/VHFl97B1FT7o2+qwf3Vukikfnz85twCMoDyx/2qSv8=",-2713561768952128147,-6139801801466514211,-3075234766843971167,7178517528845006321>()) {
            case -1917178063:
               if (var0 < bv.length) {
                  return var0;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sgozdmav4l1yt","9DxYDSQNCWEimiwvfTs0yGMko9JNn1JPA0cils0W+iU=",5119637348991033338,-4435911164183902235,1195257182371216856,-8486038204894766620>()) {
                     case -1434466332:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }
}
