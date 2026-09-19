package com.yiyiaddon.build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

/** 变换前后同一组边界值与随机输入对照，测试对象和测试程序都不进入发布包。 */
public final class HardeningSelfTest {
    public static void main(String[] args) throws Exception {
        String name = HardeningFixture.class.getName();
        byte[] original;
        try (var input = HardeningFixture.class.getResourceAsStream("HardeningFixture.class")) {
            original = input.readAllBytes();
        }
        int assertions = 0;
        // 重排是随机的，覆盖多个排列；不是只测一次幸运的输出。
        for (int round = 0; round < 8; round++) {
            byte[] modified = new HardeningTool().transformClass(original, HardeningSelfTest.class.getClassLoader());
            Class<?> protectedType = new ClassLoader(HardeningSelfTest.class.getClassLoader()) {
                Class<?> define() { return defineClass(name, modified, 0, modified.length); }
            }.define();
            if (!Boolean.TRUE.equals(protectedType.getMethod("identity").invoke(null))) {
                throw new AssertionError("字符串驻留身份改变");
            }
            if (!HardeningFixture.PUBLIC_TEXT.equals(protectedType.getField("PUBLIC_TEXT").get(null))) {
                throw new AssertionError("公开常量字段契约改变");
            }
            for (String method : Arrays.asList("text", "branches", "arrays", "floating", "exception", "lambda", "monitor", "allocation")) {
                Method before = HardeningFixture.class.getMethod(method, int.class);
                Method after = protectedType.getMethod(method, int.class);
                Random random = new Random(731L + round);
                int[] edgeCases = {0, 1, -1, 2, 3, Integer.MIN_VALUE, Integer.MAX_VALUE};
                for (int i = 0; i < 1_007; i++) {
                    int value = i < edgeCases.length ? edgeCases[i] : random.nextInt();
                    Object expected = invoke(before, value), actual = invoke(after, value);
                    if (!expected.equals(actual)) throw new AssertionError("行为改变：" + method + " 输入=" + value);
                    assertions++;
                }
            }
        }
        System.out.println("变换行为对照通过：" + assertions + " 项，8 组随机布局，含 Unicode、引用身份、异常、循环、switch、lambda 和浮点边界。");
    }

    /** 异常的类型与消息也是结果，不能只比较正常返回路径。 */
    private static Object invoke(Method method, int value) throws Exception {
        try { return method.invoke(null, value); }
        catch (InvocationTargetException error) {
            Throwable cause = error.getCause();
            return cause.getClass().getName() + ":" + cause.getMessage();
        }
    }
}
