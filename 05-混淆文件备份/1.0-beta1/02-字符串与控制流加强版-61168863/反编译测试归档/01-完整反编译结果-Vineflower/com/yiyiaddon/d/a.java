package com.yiyiaddon.d;

public final class a {
   private static final long a = 30000L;
   private static volatile long b = -1L;
   private static volatile long c;

   private a() {
   }

   public static long a() {
      long var0 = System.currentTimeMillis();
      if (c != 0L) {
         switch ((int)com.yiyiaddon.m.b.a<"s18hpvo0sk6ho8","HdYvA2OwAE5DEIreqcosACBOtV22p2KsyfqJ/0oZN1g=",-4095394592499658458,1048757595258978122,6890624111367729020,-5984605720471130557>()) {
            case -47350255:
               if (var0 - c < 30000L) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4wbzxbol3nyv","aav/4NSkOjaHe0gQrYa+WgtV/4+0o7Sf9fUIBlD//GA=",-8260518878373675107,-4165875031345565165,8118380200829638074,-4607248890059241799>()) {
                     case -875756600:
                        return b;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      long var2 = e.b();
      if (var2 >= 0L) {
         label21:
         switch ((int)com.yiyiaddon.m.b.a<"s2kow7uslvh5hn","5U9Tqz6tsm0TN6JTsD25unaA1VqZdhs9uypzAwGZOxQ=",-2827651785301197051,-7252451330558368390,-1284514840508280943,6767310748653148090>()) {
            case 144406686:
               b = var2;
               switch ((int)com.yiyiaddon.m.b.a<"sjljd32ghab2v","sl94sQ7ItLQnGuHJof0b/VAkUL7yswPsd+dzYWOCz48=",-6939365638028363196,716878712901995558,-2839410849236595039,-4120264085603357075>()) {
                  case -1375623648:
                     break label21;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      c = var0;
      return b;
   }
}
