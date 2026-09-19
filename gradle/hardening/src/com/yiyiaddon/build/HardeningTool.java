package com.yiyiaddon.build;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.*;
import java.util.jar.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.CodeSizeEvaluator;
import org.objectweb.asm.tree.*;

/** 发布前的独立构建工具；不进入最终模组，任何转换失败都中断构建，不静默降级。 */
public final class HardeningTool implements Opcodes {
    private static final int MAX_STRING_CHARS = 24_000;
    private static final int MAX_FLOW_INSTRUCTIONS = 2_000;
    private final SecureRandom random = new SecureRandom();
    private final ConstantEncryption encryption = new ConstantEncryption(random);
    private final Map<String, Integer> counts = new TreeMap<>();

    public static void main(String[] args) throws Exception {
        if (args.length != 4) throw new IllegalArgumentException("需要输入 JAR、输出 JAR、依赖列表与统计路径");
        List<URL> urls = new ArrayList<>();
        urls.add(Path.of(args[0]).toUri().toURL());
        for (String line : Files.readAllLines(Path.of(args[2]))) {
            if (!line.isBlank()) urls.add(Path.of(line).toUri().toURL());
        }
        try (URLClassLoader loader = new URLClassLoader(urls.toArray(URL[]::new), ClassLoader.getPlatformClassLoader())) {
            HardeningTool tool = new HardeningTool();
            tool.transform(Path.of(args[0]), Path.of(args[1]), loader);
            StringBuilder report = new StringBuilder("{\n");
            tool.counts.forEach((key, value) -> report.append("  \"").append(key).append("\": ").append(value).append(",\n"));
            report.append("  \"formatVersion\": 1\n}\n");
            Files.writeString(Path.of(args[3]), report, StandardCharsets.UTF_8);
            System.out.println("发布字节码加固完成：" + tool.counts);
        }
    }

    /** 只写 class 中间包。先检查原始字节码中的禁用探针，避免加密掩盖发布检查。 */
    void transform(Path input, Path output, ClassLoader loader) throws Exception {
        Files.createDirectories(output.toAbsolutePath().getParent());
        Path temporary = output.resolveSibling(output.getFileName() + ".tmp");
        try (JarFile jar = new JarFile(input.toFile()); JarOutputStream out = new JarOutputStream(Files.newOutputStream(temporary))) {
            for (JarEntry entry : Collections.list(jar.entries()).stream().sorted(Comparator.comparing(JarEntry::getName)).toList()) {
                if (!entry.getName().endsWith(".class")) continue;
                byte[] bytes;
                try (var stream = jar.getInputStream(entry)) { bytes = stream.readAllBytes(); }
                String raw = new String(bytes, StandardCharsets.ISO_8859_1);
                if (raw.contains("DebugProbe") || raw.contains("debug-point") || raw.contains("127.0.0.1:7777")) {
                    throw new IllegalStateException("原始输入含临时埋点：" + entry.getName());
                }
                byte[] protectedBytes = transformClass(bytes, loader);
                JarEntry target = new JarEntry(entry.getName());
                target.setTime(0);
                out.putNextEntry(target);
                out.write(protectedBytes);
                out.closeEntry();
            }
        }
        Files.move(temporary, output, StandardCopyOption.REPLACE_EXISTING);
    }

    /** 先加密常量，再重排安全范围内的方法，最后用实际依赖重新计算栈映射帧。 */
    byte[] transformClass(byte[] original, ClassLoader loader) throws Exception {
        ClassReader reader = new ClassReader(original);
        ClassNode type = new ClassNode();
        reader.accept(type, ClassReader.SKIP_FRAMES);
        count("classesTotal");
        if (type.name.startsWith("com/yiyiaddon/mixin/") || type.name.equals(ConstantEncryption.RUNTIME)
                || isMixin(type.visibleAnnotations) || isMixin(type.invisibleAnnotations)) {
            count("classesContractExcluded");
            return original;
        }
        // 新常量池独立生成，不能复用 reader 的原始常量池，否则已加密的明文仍留在产物里。
        Map<String, ConstantDynamic> literals = new HashMap<>();
        protectPrivateFields(type, literals);
        for (MethodNode method : type.methods) {
            encryptStrings(method, literals);
            if (method.instructions.size() == 0) continue;
            if (method.name.startsWith("<") || !method.tryCatchBlocks.isEmpty()
                    || (method.access & ACC_SYNCHRONIZED) != 0 || hasMonitor(method) || allocationCrossesBranch(method)) {
                count("flowContractExcludedMethods");
                continue;
            }
            if (method.instructions.size() > MAX_FLOW_INSTRUCTIONS
                    || reader.getItemCount() + literals.size() * 20 > 32_000) {
                count("flowSizeExcludedMethods");
                continue;
            }
            FlowObfuscation flow = new FlowObfuscation(random, encryption);
            if (flow.apply(method)) {
                count("flowMethods");
                counts.merge("encryptedDispatches", flow.dispatches, Integer::sum);
            }
        }
        for (MethodNode method : type.methods) {
            CodeSizeEvaluator size = new CodeSizeEvaluator(null);
            method.accept(size);
            if (size.getMaxSize() >= 65_536) throw new IllegalStateException("加固后方法超过 JVM 长度限制：" + type.name + "." + method.name);
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override protected ClassLoader getClassLoader() { return loader; }
        };
        type.accept(writer);
        count("classesProcessed");
        return writer.toByteArray();
    }

    /** 私有静态字符串迁入类初始化最前端；公开常量保留 ConstantValue 以维持外部编译契约。 */
    private void protectPrivateFields(ClassNode type, Map<String, ConstantDynamic> literals) throws GeneralSecurityException {
        InsnList assignments = new InsnList();
        for (FieldNode field : type.fields) {
            if (!(field.value instanceof String text)) continue;
            if ((field.access & (ACC_PRIVATE | ACC_STATIC | ACC_FINAL)) != (ACC_PRIVATE | ACC_STATIC | ACC_FINAL)) {
                count("publicOrPackageStringConstantsKept");
                continue;
            }
            if (text.length() > MAX_STRING_CHARS) { count("oversizeStringsKept"); continue; }
            field.value = null;
            assignments.add(new LdcInsnNode(encrypted(text, literals)));
            assignments.add(new FieldInsnNode(PUTSTATIC, type.name, field.name, field.desc));
            count("privateStringFieldsEncrypted");
        }
        if (assignments.size() == 0) return;
        MethodNode initializer = type.methods.stream().filter(m -> m.name.equals("<clinit>")).findFirst().orElse(null);
        if (initializer == null) {
            initializer = new MethodNode(ACC_STATIC, "<clinit>", "()V", null, null);
            initializer.instructions.add(new InsnNode(RETURN));
            type.methods.add(initializer);
        }
        initializer.instructions.insert(assignments);
    }

    /** 同时处理 LDC 字面量和 StringConcatFactory 拼接配方；不改注解和反射元数据。 */
    private void encryptStrings(MethodNode method, Map<String, ConstantDynamic> literals) throws GeneralSecurityException {
        for (AbstractInsnNode instruction : method.instructions) {
            if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof String text) {
                if (text.length() > MAX_STRING_CHARS) { count("oversizeStringsKept"); continue; }
                ldc.cst = encrypted(text, literals);
                count("stringLoadSitesEncrypted");
            }
            if (instruction instanceof InvokeDynamicInsnNode call
                    && call.bsm.getOwner().equals("java/lang/invoke/StringConcatFactory")) {
                for (int i = 0; i < call.bsmArgs.length; i++) {
                    if (call.bsmArgs[i] instanceof String text && text.length() <= MAX_STRING_CHARS) {
                        call.bsmArgs[i] = encrypted(text, literals);
                        count("concatArgumentsEncrypted");
                    }
                }
            }
        }
    }

    private ConstantDynamic encrypted(String text, Map<String, ConstantDynamic> literals) throws GeneralSecurityException {
        ConstantDynamic value = literals.get(text);
        if (value == null) {
            value = encryption.string(text);
            literals.put(text, value);
            count("uniqueStringConstantsEncrypted");
        }
        return value;
    }

    private static boolean isMixin(List<AnnotationNode> annotations) {
        return annotations != null && annotations.stream().anyMatch(a -> a.desc.equals("Lorg/spongepowered/asm/mixin/Mixin;"));
    }

    private static boolean hasMonitor(MethodNode method) {
        for (AbstractInsnNode node : method.instructions) {
            if (node.getOpcode() == MONITORENTER || node.getOpcode() == MONITOREXIT) return true;
        }
        return false;
    }

    /**
     * NEW 与对应构造调用之间的未初始化引用不能经过物理后向重排。
     * 例如 new Entry(flag ? a : b)；这里保留控制流，只加密字符串。
     * 这是最终包 -Xverify:all 发现的实际边界，不能靠关闭 JVM 校验绕过。
     */
    private static boolean allocationCrossesBranch(MethodNode method) {
        int pending = 0;
        for (AbstractInsnNode node : method.instructions) {
            if (node.getOpcode() == NEW) pending++;
            if (node instanceof MethodInsnNode call && call.getOpcode() == INVOKESPECIAL
                    && call.name.equals("<init>") && pending > 0) pending--;
            if (pending > 0 && (node instanceof JumpInsnNode || node instanceof TableSwitchInsnNode
                    || node instanceof LookupSwitchInsnNode)) return true;
        }
        return false;
    }

    private void count(String key) { counts.merge(key, 1, Integer::sum); }
}
