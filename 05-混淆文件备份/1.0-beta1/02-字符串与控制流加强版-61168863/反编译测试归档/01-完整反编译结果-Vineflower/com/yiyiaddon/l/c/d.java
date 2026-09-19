package com.yiyiaddon.l.c;

import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;

public final class d {
   private final n a;

   public d(String var1) {
      this.a = new n(() -> com.yiyiaddon.d.b.e.g(var1), var1x -> com.yiyiaddon.d.b.e.a(var1, var1x));
   }

   public float t() {
      return this.a.c();
   }

   public float b() {
      return this.a.d();
   }

   public void a(float var1) {
      this.a.a(var1);
   }

   public void d(Canvas var1, float var2, float var3, float var4) {
      this.a.b(var1, var2, var3 - this.a.d() / 2.0F, var4);
   }

   public boolean b(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sl3ff61gdenwz","muUIsPzJds8NDfnnRJflGBHNp+jTFJkno+2eWKwELkQ=",-8465784385759477985,-2345698309012143242,3996960713480689239,2909383773270369692>()) {
            case 2105232331:
               return false;
            default:
               throw null;
         }
      } else {
         float var6 = var4 - this.a.d() / 2.0F;
         if (!(var1 < var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"s10w8bmu0sbzvz","Sb/ei8O1HTA8cyriygLZQEZvAlFjCY2S9EQDKm47j1w=",4544882912861675734,5769409792277041732,7816136633631114709,1755356982765505859>()) {
               case 1222957129:
                  if (!(var1 > var3 + this.a.c())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1cu9r9aqyyc5c","d6368GDzkwMgbQ1GgikWTHGvN4lTXb67qE+iIxUBxCo=",5786758027230464683,-125197508883591987,-4069187840791446581,-6755504597098430801>()) {
                        case 778216672:
                           if (!(var2 < var6)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2kn1zanud7c2x","6dhDLI6QlsL+6YkgH5wLPQ6jlfcne5RF16aeW1wplIs=",2800978612828874805,-8682851143170242931,-4709948024789275417,-98834156681559700>()) {
                                 case -1401093406:
                                    if (!(var2 > var6 + this.a.d())) {
                                       return this.a.a(var1, var2, var3, var6, var5);
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1uzw6pad2vf3e","4Wb9g7xMQGzT2R7mazTgwDHXb3P+JEkX+taI496xfbg=",5580552448006223298,995089817461068516,-7365021664728975587,-487350033264038561>()) {
                                       case 1656921238:
                                          return false;
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

         return false;
      }
   }
}
