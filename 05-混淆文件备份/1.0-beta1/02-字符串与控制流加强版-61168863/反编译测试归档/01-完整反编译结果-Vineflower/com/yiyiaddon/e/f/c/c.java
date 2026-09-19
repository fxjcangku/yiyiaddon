package com.yiyiaddon.e.f.c;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.h.d;
import net.minecraft.client.Minecraft;

public final class c extends com.yiyiaddon.l.f.c implements i {
   private static final String iR = (String)com.yiyiaddon.m.b.a<"sajqlccs3unzq","OxO8OyrX+C5FInmb5ZQgkVQNm6bPh3n4KoMxDGloiJXCpWTQkuoXbswx",7156914584395275554,7535310395443100709,1909775892718583222,-7328829937248221097>();
   private static final String iS = (String)com.yiyiaddon.m.b.a<"s22mpiyz1zg05j","lf4VOf6GjXux5VU5hkP8SzIat1M+sEH/2vHfWObScmVGOY/mNEDgh0Z17j9ghyTcnQh/KApXEQP1JsqCZhnzraNeU++kJ/X1HDA=",475078610605397142,-6490801787259115161,-4921335001861770084,-6732262439004464438>();
   private static final String[] C = d.a(d.a(com.yiyiaddon.e.f.c.b.e));
   private final com.yiyiaddon.e.f.a c;
   private boolean v;

   public c(com.yiyiaddon.e.f.a var1) {
      this.c = var1;
   }

   @Override
   public com.yiyiaddon.l.f.a a(com.yiyiaddon.h.d var1) {
      if (!this.v) {
         switch ((int)com.yiyiaddon.m.b.a<"s10bt1hyndgaqg","ZrWKayWGSC8C3diQ1zHkahLwh+YB73IL0ZGC8/kCN3I=",2388368245360314544,-8864621972546368436,3832591848672041267,-8076850869008362457>()) {
            case -753495709:
               this.v = true;
               this.G();
               switch ((int)com.yiyiaddon.m.b.a<"s1fs5b8p71chpp","yWO1pdSy6QghESY1fdhopaMzw3+MaEHP5UKeA9UuHRc=",-1612398814750298156,-201536861488311094,-2836726699098624168,5279454098471059454>()) {
                  case -660618089:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   @Override
   public String E() {
      return this.c.t();
   }

   @Override
   public String F() {
      return this.c.v();
   }

   private void G() {
      this.b(
         new h(
               (String)com.yiyiaddon.m.b.a<"sr4ck22431iv","5iBSf3cxbIiHx8jPWtIkr9vU5Mt6hsZdM6zcgQ==",-4882430210281408230,-6416169863126749000,3254746189270810476,1828057146962665817>(),
               () -> (String)com.yiyiaddon.m.b.a<"s22mpiyz1zg05j","lf4VOf6GjXux5VU5hkP8SzIat1M+sEH/2vHfWObScmVGOY/mNEDgh0Z17j9ghyTcnQh/KApXEQP1JsqCZhnzraNeU++kJ/X1HDA=",475078610605397142,-6490801787259115161,-4921335001861770084,-6732262439004464438>(),
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"sajqlccs3unzq","OxO8OyrX+C5FInmb5ZQgkVQNm6bPh3n4KoMxDGloiJXCpWTQkuoXbswx",7156914584395275554,7535310395443100709,1909775892718583222,-7328829937248221097>(),
                  this::H
               )
            )
            .a()
      );
      String[] var1 = C;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s34a6a9udija9q","dxhx4MTbudZYnDlv0of/Zre49xsJOHQZPK4v6D1TSvg=",237266745385559838,5448788392890486972,-3938991924900224772,6887342962806805827>()) {
         case -1262254574:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s18rt49a042j9d","hRJvKlYE6V/HN/htaGxHwaWeZh5bm8L09Z+wQ6hMSu4=",5424231307295429546,8403209962924983224,3616429954157905678,1332067837642863606>()) {
                  case -717982032:
                     String var4 = var1[var3];
                     this.b(new w(var4));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2pw4w4cll1xi9","eJSwqs3FnVxHixlgL3rB1K6kJzn117tdXqnI7CYibOQ=",-5650156829290169285,-771006004596409576,-7471713317639862885,5770271912000332548>()) {
                        case -1965513122:
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

   private void H() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"szw54fjmq6ztk","fBfehDm+rMJThNbmhw66MR6LwuFCxzRdkCGt9sV7hWg=",-8183886518864129706,7911990072433053016,4483381346432510438,6742841087334036926>()) {
            case -812660168:
               return;
            default:
               throw null;
         }
      } else {
         var1.setScreen(new a(var1.screen, this.c));
      }
   }
}
