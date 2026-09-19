/*
 * Decompiled with CFR 0.152.
 */
package com.yiyiaddon.k.b;

import com.yiyiaddon.j.b.b;
import com.yiyiaddon.j.b.c;
import com.yiyiaddon.j.b.d;
import com.yiyiaddon.j.b.e;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 * Uses jvm11+ dynamic constants - pseudocode provided - see https://www.benf.org/other/cfr/dynamic-constants.html
 */
public final class a {
    static volatile private a e;
    final private d a;
    final private c a;
    final private com.yiyiaddon.j.b.a a;
    final private e a;
    final private b a = new b();
    final private List<Runnable> cV = new ArrayList<Runnable>();
    private boolean f;

    private a() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static a a() {
        a a2 = e;
        if (a2 != null) return a2;
        Class<a> clazz = a.class;
        synchronized (a.class) {
            a2 = e;
            if (a2 != null) return a2;
            e = a2 = new a();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return a2;
        }
    }

    public void d() {
        if (this.f) {
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s2zs4jp8xn7da4", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "/ugJnKkj1bDObqxzEzI57pSgIOeOT8PX6wyvyrIAwW8=", 7656083629977167094L, 3939938725946668436L, -125605892688801276L, -2824243847612571209L)) {
                default: {
                    throw null;
                }
                case 1277160219: {
                    return;
                }
            }
        }
        this.f = true;
        this.a.C();
        this.a.C();
        this.a.C();
        this.a.C();
        this.a.C();
        this.a.A().stream().filter(d2 -> {
            boolean bl;
            if (com.yiyiaddon.i.b.c.E(d2.be())) {
                bl = false;
                switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("soyphoci4kmzj", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "i3e4idSTzfG9L9tDrZu6911nyGs3/BbbpucGPxzX9mQ=", 5911875368690417626L, -7669735292673305749L, 2025667021563898032L, -2092728574157492498L)) {
                    case -1276471628: {
                        return bl;
                    }
                }
                throw null;
            }
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s3678349oh2o1p", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "dYb5OXFo+T1I4ddnI2CjyFUIlVQ5aF7FFyMiufkxdgY=", -4818031973088972117L, -7050583450990838904L, 1413868312284965150L, -4104789500226055672L)) {
                default: {
                    throw null;
                }
                case -343327227: 
            }
            bl = true;
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s2mxersuyj68p4", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "M0d5KIWu1qAxcuhqoe+3sHKHk0yZKI4tHr/YgNWOos4=", 5453901330621352697L, -7724748921957723883L, -8465151407031992301L, -3940866669856054825L)) {
                case -975156261: {
                    return bl;
                }
            }
            throw null;
        }).forEach(this.a::a);
        this.a.d(com.yiyiaddon.i.b.b::E);
        this.a.d(com.yiyiaddon.i.b.a::E);
        this.jq();
    }

    public void C() {
        this.f = false;
        this.d();
    }

    public void k(Runnable runnable) {
        if (runnable != null) {
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s3fdofdvribcrb", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "TIqC9WU0tqfZ8zn2Zn58CtNfjvX0F3HK46TcnldPrlQ=", -8352881165542150150L, 3129694341493740053L, -2116340132912658080L, 1304902616754190932L)) {
                default: {
                    throw null;
                }
                case -2027886240: 
            }
            this.cV.add(runnable);
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s3t0fofpyaesey", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "/rOk+Wl2za6vFzRJ0zsVo1i6zM+pfl4PdoaX5Fjw95c=", -2228422779332681984L, -5480946870278987129L, 5798874119012737363L, -6520270030392587098L)) {
                default: {
                    throw null;
                }
                case 1392136274: 
            }
        }
    }

    public Path m() {
        return this.a.k();
    }

    public Set<com.yiyiaddon.g.c.d> B() {
        return this.a.A();
    }

    public com.yiyiaddon.g.c.d c(String string) {
        return this.a.b(string);
    }

    public int dH() {
        return this.a.a();
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    public List<com.yiyiaddon.g.c.d> a(Set<String> var1_1) {
        block19: {
            var2_2 = new ArrayList<com.yiyiaddon.g.c.d>();
            if (var1_1 == null) break block19;
            var3_3 = new LinkedHashSet<String>(var1_1).iterator();
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("sc6uheohg0cxy", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "ewVdTyR4bLRpEEuh8K1jC3n75y7wCRygD2DrCn3GlK8=", -5365259028738695676L, -7449007151598144730L, 2782877453737782378L, -6763329171187186961L)) {
                case -1760991895: {
                    if (true) ** GOTO lbl-1000
                }
                default: {
                    throw null;
                }
            }
        }
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1w9kecoupz3of", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "nT2/pskNDJoZDgX6+72sm5DLg1rcFNo+h9m+rhjuof4=", 5913821736272245371L, 3512332177352095147L, 3320891775096599396L, 8871064147778494463L)) {
            case -197950616: {
                return var2_2;
            }
        }
        throw null;
        block18: while (true) {
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1nk8ux3djsngx", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "jDX/zsPKnDpxZ3VUQ5WCKou4MI8sjhutYqPGNQ0QnXo=", 3108979286496834846L, -265451551249946524L, -3921446231265358579L, -831755723284161331L)) {
                case -2076981749: lbl-1000:
                // 2 sources

                {
                    if (var3_3.hasNext() == false) return var2_2;
                    switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s3lcpogfbu5hac", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "JvVii7iQWwGAEerwuJsM0acZlF3x8KN+l2fLlvPBZTQ=", 268935066075385858L, -6506962427588531469L, -2976070914887654531L, 5407386184367139297L)) {
                        default: {
                            throw null;
                        }
                        case -604134406: 
                    }
                    var4_4 = (String)var3_3.next();
                    var5_5 = this.a.b(var4_4);
                    if (var5_5 == null) continue block18;
                    switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1q545k2y5yy06", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "GMQy4l17lqmJDvPwMdbKpRtGX6C5Uv/V+7/SJQ7tRD8=", -3780219786430021559L, 35983652566689870L, -4770104408296491981L, 5705117088481006242L)) {
                        default: {
                            throw null;
                        }
                        case 1288233399: 
                    }
                    var2_2.add(var5_5);
                    switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1t37jmf0vys64", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "8OvPr4eeCYNxHlJnl/0oYadZyFAy2QT9L+ujC5hR0sg=", 825736292602762495L, -6232035544650223379L, -3233800684874621120L, -7083002886055938510L)) {
                        case 1058022726: {
                            continue block18;
                        }
                    }
                    throw null;
                }
            }
            break;
        }
        throw null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String f(com.yiyiaddon.g.c.d d2) {
        String string = this.a.d(d2);
        if (string == null) {
            return string;
        }
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1i2w9bb7lzajw", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "Mb+hTRJBprb6Oq0kjW+Auyl9S08f2ODrNRSQtaaMufw=", 3340968748701645183L, -5676763692199289792L, -2927582878469507595L, 3723313197324189292L)) {
            case 1847043156: {
                this.jq();
                switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1jruj6c21chyu", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "RLP5nMM5Gk7xmDeMhWa5ui/A2hIEO4pxxKj5IVSKxVI=", -5208071750592511577L, 580202639167772275L, -2694153208066280335L, -4872743233876388692L)) {
                    default: {
                        throw null;
                    }
                    case 439572310: 
                }
                return string;
            }
        }
        throw null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean b(com.yiyiaddon.g.c.d d2) {
        boolean bl = this.a.a(d2);
        if (!bl) return bl;
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s143y9qadu4uck", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "9I6vuo21vOwqaDASyqekpreSj9paGNKtC3Qx2xfYvZ0=", 353849933368002685L, 4676888049585577595L, -9141777407248959947L, -882590998893849720L)) {
            case 1120527852: {
                this.jq();
                switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("su0pwq38tehyo", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "f7FIARpr416TOqPh3ly+M8PnPXMUj+lfEVO6WMxW9SE=", -5349403369503732022L, 6918321876688041261L, -2382604439290281939L, -7065303272547927L)) {
                    default: {
                        throw null;
                    }
                    case -1551051276: 
                }
                return bl;
            }
        }
        throw null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean fC() {
        boolean bl = this.a.fx();
        if (!bl) return bl;
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s3ra7yiuph04st", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "9jrrR5491V2BmBAwMmCIP49WR1gR6HeyLkPlzDITcfE=", 5010327213744561433L, 3907873098536154067L, -5802819352548843750L, -2670659588801758229L)) {
            case -1710453588: {
                this.jq();
                switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s2mynafdqcipku", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "owwN4cg3lSJScWApEvlZS11c1FR9lX1U78A2XWM5kHQ=", 2431871993896833912L, -7380472611359248640L, -3566826357410090115L, -5899803943074675145L)) {
                    case 5364814: {
                        return bl;
                    }
                }
                throw null;
            }
        }
        throw null;
    }

    public Path n() {
        return this.a.k();
    }

    public Set<com.yiyiaddon.g.c.b> C() {
        return this.a.A();
    }

    public com.yiyiaddon.g.c.b b(String string) {
        return this.a.a(string);
    }

    public int dI() {
        return this.a.a();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String e(com.yiyiaddon.g.c.b b2) {
        String string = this.a.c(b2);
        if (string == null) {
            return string;
        }
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s6uswba4tkl89", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "sN66EwS2+V8XTIREgFQ/PgTFUiC+FUozY1jeFvtf1GY=", -1127478938972573907L, 6895988319725924370L, 1601646686999987966L, -3238399178975118186L)) {
            default: {
                throw null;
            }
            case -205564298: 
        }
        this.jq();
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("se2mltli0isva", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "fAx4m0i5HzYnA/9pnsaIVQaL3BBxeQbN49osQsCo4+A=", -1901945596750783024L, -3102336997411450316L, -6130469004628715054L, -4633229191311045699L)) {
            case -1386110454: {
                return string;
            }
        }
        throw null;
    }

    public boolean b(com.yiyiaddon.g.c.b b2) {
        boolean bl = this.a.a(b2);
        if (bl) {
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s6a9j229h6ta", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "R5R97CUF0dmqxc3ddCmEIM6VAN09/QSW64Kwym6jxM8=", -2475705050837754824L, -2674799479835893149L, -6214388422405386841L, -4400176162442273267L)) {
                default: {
                    throw null;
                }
                case 684764590: 
            }
            this.jq();
            switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s10cl9fd3zw8qs", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "JCjqBhcbRKzeW3djwHFzpx/5U+ocyAspsQpsaTskDr8=", 2828271690205961206L, 8248192535580526247L, 8909699892542938715L, -5837494153138544208L)) {
                default: {
                    throw null;
                }
                case -647934395: 
            }
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean fD() {
        boolean bl = this.a.fx();
        if (!bl) {
            return bl;
        }
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1uyhnc4tqzqus", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "SNmSQSg8Owp/DscZq3u5fTc521hKlwgEIPg2MNhMgfY=", -2568102759440020702L, 8469800177840058700L, 9047814890025434653L, 1619471452593404947L)) {
            default: {
                throw null;
            }
            case -378492712: 
        }
        this.jq();
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s31iaugf02kpgi", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "s41FKVeCgtP1cVXi0aYxcKK1U0Xc6O81JMqyv1brxOQ=", 2224029754961126246L, -7882633312785308545L, -8205097523999107182L, -5522220616259837759L)) {
            case 485467626: {
                return bl;
            }
        }
        throw null;
    }

    public Path o() {
        return this.a.k();
    }

    public Set<com.yiyiaddon.g.c.a> D() {
        return this.a.A();
    }

    public com.yiyiaddon.g.c.a b(String string) {
        return this.a.a(string);
    }

    public int dJ() {
        return this.a.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    public String e(com.yiyiaddon.g.c.a a2) {
        String string = this.a.a(a2);
        if (string == null) {
            return string;
        }
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("scr8t5bclgitc", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "XCgrL3nOP/EU5rnntg/aHhC8rT1tel75WQS6nTw3z7w=", 8130480013445707425L, -1319680397657681541L, -2969723475994783195L, 1410042208012427092L)) {
            case -1969739034: {
                this.jq();
                switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s32qiibwgqdpu6", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "gjPGsTpfipTv7vIu0PZjP+hywHKZnKLFUxX4ItYpRrs=", -5130345441301556427L, -850511546403903993L, 4077192222043997816L, -1952009759375809465L)) {
                    default: {
                        throw null;
                    }
                    case 1059801960: 
                }
                return string;
            }
        }
        throw null;
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Enabled aggressive block sorting
     */
    public boolean d(com.yiyiaddon.g.c.a a2) {
        boolean bl = this.a.b(a2);
        if (!bl) return bl;
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("so1mjxpb6msoh", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "q/nsZnGleCQpd2IKHNyPROoFvzHbKnN4z2oh8Z37fvY=", -3437821175179432704L, -5898437582889550546L, 5410340774309858492L, 3579208267823133701L)) {
            default: {
                throw null;
            }
            case 67574346: 
        }
        this.jq();
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s2c3c59s6997as", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "ZrYkoPXIIiBGSVRc7roRrFuR1NJphnOC8N04k0hVCSI=", 856031168650951240L, -3208851467168930310L, -667229952022544106L, -3612132180027329995L)) {
            case 1544120720: {
                return bl;
            }
        }
        throw null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean fE() {
        boolean bl = this.a.fx();
        if (!bl) return bl;
        switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s527eywmk6kh6", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "JET7vesyGwK8OG9JZuhKLcS6XlC0BGUINRrHWKZKzFg=", -4461476479942393355L, 2685379199412173826L, 3277452431841018985L, -5637697438635497256L)) {
            case -2032655333: {
                this.jq();
                switch ( /* dynamic constant */ (Object)com.yiyiaddon.m.b.a("s1jvd3ivv4fbbw", MethodHandles.lookup(), "a", com.yiyiaddon.k.b.a.class, "XkzSvpWsDi5tAr15Wv+MQ89XCLGXq+ck9IZc71zADY8=", 4797255673709409981L, 6167507220415396571L, 7837564786000552656L, 6907950903770549076L)) {
                    case 1342728024: {
                        return bl;
                    }
                }
                throw null;
            }
        }
        throw null;
    }

    public Path p() {
        return this.a.k();
    }

    public Path q() {
        return this.a.k();
    }

    public String b(com.yiyiaddon.g.c.d d2, boolean bl) {
        return this.a.a(d2, bl);
    }

    public String b(com.yiyiaddon.g.c.a a2, boolean bl) {
        return this.a.a(a2, bl);
    }

    public int dK() {
        return this.a.a();
    }

    public int dL() {
        return this.a.a();
    }

    public boolean fF() {
        return this.a.fx();
    }

    public boolean fG() {
        return this.a.fx();
    }

    private void jq() {
        for (Runnable runnable : this.cV) {
            try {
                runnable.run();
            }
            catch (Exception exception) {}
        }
    }
}
