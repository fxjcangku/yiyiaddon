package com.yiyiaddon.l.a;

public final class a {
   private static final float eJ = 1.70158F;
   private static final float eK = 2.70158F;

   private a() {
   }

   public static float d(float var0) {
      if (var0 < 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s21bucecsb3jof","hfuf3VdnQvwEMikA41mbSdHIIC7bXlqtJIYeZa/vWk8=",9041625058601301792,7078790431837377072,-3875723030305967803,7937705994038722101>()) {
            case 1527126670:
               return 0.0F;
            default:
               throw null;
         }
      } else if (var0 > 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"shx69l6nzobd2","P3M4tk59ldG0zEDlb/oSgqeM5FaVmxms2dBYU41Iigs=",-9111307345399917374,7432120441680315135,-551411322739413241,-6502406769857746125>()) {
            case 1892263161:
               switch ((int)com.yiyiaddon.m.b.a<"sjah0fztsk5pz","kBotASSNXr5q3s/2gbZQiMqWLSzWBpTsIa9Pzuxinuk=",-8519848370011101484,-1350186874283363102,-3429369670489239262,-3108884130358722035>()) {
                  case -1880411941:
                     return 1.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1kmxxxebfykql","XGDOqyfpurwywUi//m1CazjtMtAh8cKDJlQ6ApPcs00=",-9039141158397652824,-5500535346595842974,1225916570508077542,3518477219961077218>()) {
            case -489553118:
               return var0;
            default:
               throw null;
         }
      }
   }

   public static float e(float var0) {
      float var1 = 1.0F - d(var0);
      return 1.0F - var1 * var1 * var1;
   }

   public static float f(float var0) {
      float var1 = d(var0);
      float var2 = var1 - 1.0F;
      return 1.0F + 2.70158F * var2 * var2 * var2 + 1.70158F * var2 * var2;
   }
}
