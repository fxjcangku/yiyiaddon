/*
 * Decompiled with CFR 0.152.
 */
package com.yiyiaddon.l.a;

import com.yiyiaddon.m.b;
import java.lang.invoke.MethodHandles;

/*
 * Uses jvm11+ dynamic constants - pseudocode provided - see https://www.benf.org/other/cfr/dynamic-constants.html
 */
public final class a {
    final static private float eJ = 1.70158f;
    final static private float eK = 2.70158f;

    private a() {
    }

    /*
     * Unable to fully structure code
     */
    public static float d(float var0) {
        block15: {
            if (!(var0 < 0.0f)) break block15;
            switch ( /* dynamic constant */ (Object)b.a("s21bucecsb3jof", MethodHandles.lookup(), "a", a.class, "hfuf3VdnQvwEMikA41mbSdHIIC7bXlqtJIYeZa/vWk8=", 9041625058601301792L, 7078790431837377072L, -3875723030305967803L, 7937705994038722101L)) {
                default: {
                    throw null;
                }
                case 1527126670: {
                    return 0.0f;
                }
            }
lbl7:
            // 2 sources

            return v0;
lbl9:
            // 1 sources

            while (true) {
                v0 = var0;
                switch ( /* dynamic constant */ (Object)b.a("s1kmxxxebfykql", MethodHandles.lookup(), "a", a.class, "XGDOqyfpurwywUi//m1CazjtMtAh8cKDJlQ6ApPcs00=", -9039141158397652824L, -5500535346595842974L, 1225916570508077542L, 3518477219961077218L)) {
                    case -489553118: {
                        ** GOTO lbl7
                    }
                }
                throw null;
            }
lbl15:
            // 1 sources

            while (true) {
                v0 = 1.0f;
                switch ( /* dynamic constant */ (Object)b.a("sjah0fztsk5pz", MethodHandles.lookup(), "a", a.class, "kBotASSNXr5q3s/2gbZQiMqWLSzWBpTsIa9Pzuxinuk=", -8519848370011101484L, -1350186874283363102L, -3429369670489239262L, -3108884130358722035L)) {
                    case -1880411941: {
                        ** continue;
                    }
                }
                throw null;
            }
        }
        ** while (!(var0 > 1.0f))
lbl23:
        // 1 sources

        switch ( /* dynamic constant */ (Object)b.a("shx69l6nzobd2", MethodHandles.lookup(), "a", a.class, "P3M4tk59ldG0zEDlb/oSgqeM5FaVmxms2dBYU41Iigs=", -9111307345399917374L, 7432120441680315135L, -551411322739413241L, -6502406769857746125L)) {
            case 1892263161: {
                ** continue;
            }
        }
        throw null;
    }

    public static float e(float f) {
        float f2 = 1.0f - a.d(f);
        return 1.0f - f2 * f2 * f2;
    }

    public static float f(float f) {
        float f2 = a.d(f);
        float f3 = f2 - 1.0f;
        return 1.0f + 2.70158f * f3 * f3 * f3 + 1.70158f * f3 * f3;
    }
}
