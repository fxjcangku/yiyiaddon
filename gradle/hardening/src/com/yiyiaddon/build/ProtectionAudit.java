package com.yiyiaddon.build;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * 对最终发布包而非中间包验收：逐个解密动态常量、对照原始字符串集合及跳转键，
 * 同时让 JVM 校验所有类的成员。它不执行任何游戏初始化或向外发送数据。
 */
public final class ProtectionAudit {
    public static void main(String[] args) throws Exception {
        Set<String> originalStrings = new HashSet<>();
        try (JarFile jar = new JarFile(args[0])) {
            for (JarEntry entry : Collections.list(jar.entries())) {
                if (!entry.getName().endsWith(".class")) continue;
                ClassReader reader;
                try (var input = jar.getInputStream(entry)) { reader = new ClassReader(input); }
                char[] buffer = new char[reader.getMaxStringLength()];
                for (int index = 1; index < reader.getItemCount(); index++) {
                    int offset = reader.getItem(index);
                    if (offset != 0 && reader.readByte(offset - 1) == 8) originalStrings.add((String) reader.readConst(index, buffer));
                }
            }
        }
        List<URL> urls = new ArrayList<>();
        urls.add(Path.of(args[1]).toUri().toURL());
        for (String line : Files.readAllLines(Path.of(args[2]))) {
            if (!line.isBlank()) urls.add(Path.of(line).toUri().toURL());
        }
        int strings = 0, integers = 0, dispatches = 0, classes = 0;
        boolean tamperRejected = false;
        try (URLClassLoader loader = new URLClassLoader(urls.toArray(URL[]::new), ClassLoader.getPlatformClassLoader());
             JarFile jar = new JarFile(args[1])) {
            for (JarEntry entry : Collections.list(jar.entries())) {
                if (!entry.getName().endsWith(".class")) continue;
                ClassReader reader;
                try (var input = jar.getInputStream(entry)) { reader = new ClassReader(input); }
                Class<?> type = Class.forName(reader.getClassName().replace('/', '.'), false, loader);
                type.getDeclaredMethods();
                type.getDeclaredFields();
                type.getDeclaredConstructors();
                classes++;
                char[] buffer = new char[reader.getMaxStringLength()];
                Map<ConstantDynamic, Object> resolved = new HashMap<>();
                for (int index = 1; index < reader.getItemCount(); index++) {
                    int offset = reader.getItem(index);
                    if (offset == 0 || reader.readByte(offset - 1) != 17) continue;
                    ConstantDynamic constant = (ConstantDynamic) reader.readConst(index, buffer);
                    if (constant.getBootstrapMethodArgumentCount() != 5) continue;
                    Object value = resolve(loader, constant, false);
                    resolved.put(constant, value);
                    if (value instanceof String text) {
                        if (!originalStrings.contains(text)) throw new AssertionError("解密结果与原始字符串集合不符：" + entry.getName());
                        if (text != text.intern()) throw new AssertionError("解密字符串未驻留");
                        strings++;
                    } else if (value instanceof Integer) integers++;
                    else throw new AssertionError("加固常量返回类型不符");
                    if (!tamperRejected) {
                        try {
                            resolve(loader, constant, true);
                            throw new AssertionError("篡改密文未被拒绝");
                        } catch (InvocationTargetException rejected) {
                            if (!(rejected.getCause() instanceof javax.crypto.AEADBadTagException)) throw rejected;
                            tamperRejected = true;
                        }
                    }
                }
                ClassNode node = new ClassNode();
                reader.accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                for (MethodNode method : node.methods) {
                    for (AbstractInsnNode instruction : method.instructions) {
                        if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof ConstantDynamic dynamic
                                && dynamic.getDescriptor().equals("I")) {
                            AbstractInsnNode next = instruction.getNext();
                            while (next != null && next.getOpcode() < 0) next = next.getNext();
                            if (!(next instanceof LookupSwitchInsnNode branch) || branch.keys.size() != 1
                                    || !branch.keys.getFirst().equals(resolved.get(dynamic))) {
                                throw new AssertionError("加密分派键与实际跳转表不符：" + entry.getName());
                            }
                            dispatches++;
                        }
                    }
                }
            }
        }
        if (strings == 0 || dispatches == 0 || !tamperRejected) throw new AssertionError("加固覆盖或认证检查缺失");
        String result = "最终发布包强校验通过\n类加载及成员校验=" + classes + "\n解密字符串常量=" + strings
                + "\n解密整数常量=" + integers + "\n分派键逐项匹配=" + dispatches
                + "\n密文篡改认证拒绝=通过\n客户端实际功能=未在本工具中验证\n";
        Files.writeString(Path.of(args[3]), result, StandardCharsets.UTF_8);
        System.out.print(result);
    }

    /** 使用最终包中已混淆的引导方法真实解密，避免只验证构建端的算法副本。 */
    private static Object resolve(ClassLoader loader, ConstantDynamic constant, boolean tamper) throws Exception {
        Handle handle = constant.getBootstrapMethod();
        Class<?> owner = loader.loadClass(handle.getOwner().replace('/', '.'));
        var method = owner.getMethod(handle.getName(), MethodHandles.Lookup.class, String.class, Class.class,
                String.class, long.class, long.class, long.class, long.class);
        String ciphertext = (String) constant.getBootstrapMethodArgument(0);
        if (tamper) {
            byte[] changed = Base64.getDecoder().decode(ciphertext);
            changed[changed.length - 1] ^= 1;
            ciphertext = Base64.getEncoder().encodeToString(changed);
        }
        return method.invoke(null, MethodHandles.lookup(), constant.getName(),
                constant.getDescriptor().equals("I") ? int.class : String.class, ciphertext,
                constant.getBootstrapMethodArgument(1), constant.getBootstrapMethodArgument(2),
                constant.getBootstrapMethodArgument(3), constant.getBootstrapMethodArgument(4));
    }
}
