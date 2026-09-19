package com.yiyiaddon.build;

import java.util.function.IntUnaryOperator;

/** 只供构建变换测试使用，不打包：覆盖栈、局部变量、异常与字符串身份等语义边界。 */
public final class HardeningFixture {
    private static final String PRIVATE_TEXT = "私有字段-\u0000-\ud800-\udfff";
    public static final String PUBLIC_TEXT = "外部常量契约";

    public static String text(int number) {
        String a = "中文-\u0000-\ud800-\udfff";
        return a + number + PRIVATE_TEXT + PUBLIC_TEXT;
    }

    public static boolean identity() {
        return "相同字符串" == new String("相同字符串").intern();
    }

    public static long branches(int n) {
        long result = 0x7fffffffffffffffL;
        for (int i = 0; i < 12; i++) {
            if ((n & 1) == 0) result ^= (long) n << i;
            else result += (long) n * i;
            n = Integer.rotateRight(n, 3);
            switch (n & 7) {
                case 0: result++; break;
                case 2: result *= 3; break;
                case 5: result -= 19; break;
                default: result ^= n;
            }
        }
        return result;
    }

    public static String arrays(int n) {
        Object[] values = n < 0 ? new String[]{"负数"} : new Integer[]{n};
        Object chosen = n % 2 == 0 ? values[0] : "奇数";
        return chosen.toString();
    }

    public static double floating(int n) {
        double x = n < 0 ? Double.NaN : n * 0.5;
        return n == 0 ? -0.0 : x / (n - 1);
    }

    public static int exception(int n) {
        try { return 300 / n; }
        catch (ArithmeticException error) { return -50; }
        finally { if (n == Integer.MIN_VALUE) throw new IllegalStateException("异常分支"); }
    }

    public static int lambda(int n) {
        IntUnaryOperator op = value -> value < 3 ? value * 3 : value ^ 21;
        return op.applyAsInt(n);
    }

    public static synchronized String monitor(int n) {
        return n > 0 ? "正数" : "非正数";
    }

    public static String allocation(int n) {
        return new StringBuilder(n > 0 ? "正数" : "非正数").append(n).toString();
    }
}
