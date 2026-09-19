package com.yiyiaddon.d.c;

final class c {
   private static final ThreadLocal<int[]> a = ThreadLocal.withInitial(() -> new int[1]);

   private c() {
   }

   static boolean k() {
      if (a.get()[0] > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pt6vgjody4lb","BnLnU5NqjVHfE1ydUENLzL5DNj1sqvIXBBeMBrqDhmg=",1630156674742012626,2127392283781766313,-2646651908302432516,4610531917961295046>()) {
            case 1056730284:
               switch ((int)com.yiyiaddon.m.b.a<"s2x093ihdjusa","eOl7ZJ37yrPzDa2hlxwxnZQ5klm8iG87PFppYxQ2who=",-3230125734815607806,-4315784363498876911,5498929708907888583,5443341523679676705>()) {
                  case -1856349571:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2r8t7lc6m0y43","+PegZP3Ii2o+3xa66/7XbRsjP+mBJ8BcsmmkcQ+vKh0=",-2908024655292684503,-6884359156955818967,-4041531779279819071,-8989700922652192418>()) {
            case -653008328:
               return false;
            default:
               throw null;
         }
      }
   }

   static void b(Runnable var0) {
      int[] var1 = a.get();
      var1[0]++;

      try {
         var0.run();
      } finally {
         var1[0]--;
         if (var1[0] <= 0) {
            a.remove();
         }
      }
   }
}
