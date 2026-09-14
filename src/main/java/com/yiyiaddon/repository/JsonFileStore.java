package com.yiyiaddon.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * JSON 文件存储通用层：统一「一记录一文件」目录的读写与事务语义。
 *
 * <p><b>为什么要有这一层：</b>历史实现把同一套「临时文件 + 原子替换 + 字节备份 + 失败回滚」
 * 逻辑在三个管理器里各复制了一份（合计约三百行），任一处修 bug 都要改三遍。本类把它们收敛为
 * 唯一实现。</p>
 *
 * <p><b>三条保证：</b></p>
 * <ol>
 *   <li><b>原子写</b>：先写同目录临时文件再替换目标，写入中断不会留下半个 JSON；</li>
 *   <li><b>可回滚</b>：整目录重写前先按字节备份全部旧 JSON，备份不完整则拒绝开始替换；</li>
 *   <li><b>不吞损坏数据</b>：损坏文件跳过加载但不删除，重新保存时按内存集合覆盖。</li>
 * </ol>
 *
 * <p>本类只做文件与 JSON 操作，不认识任何业务类型。</p>
 */
public final class JsonFileStore {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonFileStore() {
    }

    /** 待写入目录的一条记录：文件名（含后缀）+ 内容 */
    public record NamedJson(String fileName, JsonObject json) {
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  读取
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 列出目录下全部 {@code .json} 普通文件；目录不存在或不可读返回空列表 */
    public static List<Path> listJsonFiles(Path dir) {
        if (dir == null || !Files.isDirectory(dir)) return List.of();
        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".json"))
                .toList();
        } catch (Exception ignored) {
            return List.of();
        }
    }

    /** 读取目录下全部 JSON；单个文件损坏（解析失败）跳过，不阻塞整体加载 */
    public static List<JsonObject> readAll(Path dir) {
        List<JsonObject> result = new ArrayList<>();
        for (Path file : listJsonFiles(dir)) {
            JsonObject json = readJson(file);
            if (json != null) result.add(json);
        }
        return result;
    }

    /** 读取单个 JSON 文件；不存在或损坏返回 {@code null} */
    public static JsonObject readJson(Path file) {
        try {
            String raw = Files.readString(file, StandardCharsets.UTF_8);
            var root = JsonParser.parseString(raw);
            return root != null && root.isJsonObject() ? root.getAsJsonObject() : null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 文件最后修改时间（毫秒）；失败返回 0 */
    public static long lastModified(Path file) {
        try {
            return Files.getLastModifiedTime(file).toMillis();
        } catch (Exception ignored) {
            return 0L;
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  写入
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 生成不与现有文件冲突的路径：{@code base.json} → {@code base_2.json} → {@code base_3.json}。
     */
    public static Path uniquePath(Path dir, String base) {
        Path candidate = dir.resolve(base + ".json");
        int n = 2;
        while (Files.exists(candidate)) {
            candidate = dir.resolve(base + "_" + n + ".json");
            n++;
        }
        return candidate;
    }

    /**
     * 在本次批量写入中规划稳定文件名，避免依赖目录里已存在的旧文件产生无意义的 {@code _N}。
     */
    public static String plannedFileName(String base, Set<String> used) {
        String name = base + ".json";
        int n = 2;
        while (used.contains(name)) {
            name = base + "_" + n++ + ".json";
        }
        used.add(name);
        return name;
    }

    /** 原子写入单个 JSON 文件；成功返回 true */
    public static boolean writeAtomic(Path target, JsonObject json) {
        if (target == null || json == null) return false;
        Path temporary = target.resolveSibling(target.getFileName() + ".tmp-" + UUID.randomUUID());
        try {
            Files.createDirectories(target.getParent());
            Files.writeString(temporary, GSON.toJson(json), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            moveReplace(temporary, target);
            return true;
        } catch (Exception ignored) {
            deleteTree(temporary);
            return false;
        }
    }

    /**
     * 事务式整目录重写：把给定记录写入临时目录，再逐文件替换，最后清理不再需要的旧文件。
     *
     * <p>任一步失败即以字节备份回滚，保证内存集合与磁盘内容不会出现不可恢复的分叉
     * （例如「内存已删除、文件也被删光」）。</p>
     *
     * @param dir         目标目录
     * @param entries     本次期望的全部记录
     * @param tempPrefix  临时目录名前缀（便于排障时辨认来源）
     * @return 是否全部成功
     */
    public static boolean syncAll(Path dir, List<NamedJson> entries, String tempPrefix) {
        if (dir == null) return false;
        Map<Path, byte[]> backup = backupBytes(dir);
        // 无法完整读取旧文件时禁止开始替换，否则回滚无法恢复那份文件
        if (backup == null) return false;

        Set<String> used = new LinkedHashSet<>();
        List<NamedJson> planned = new ArrayList<>();
        for (NamedJson entry : entries) {
            planned.add(new NamedJson(plannedFileName(entry.fileName(), used), entry.json()));
        }

        Path temp = dir.resolveSibling(tempPrefix + "-" + UUID.randomUUID());
        Set<Path> targets = new LinkedHashSet<>();
        try {
            Files.createDirectories(temp);
            for (NamedJson entry : planned) {
                if (!writeAtomic(temp.resolve(entry.fileName()), entry.json())) {
                    throw new IOException("写入临时文件失败");
                }
                targets.add(dir.resolve(entry.fileName()));
            }
            Files.createDirectories(dir);
            try (Stream<Path> stream = Files.list(temp)) {
                for (Path staged : stream.toList()) {
                    moveReplace(staged, dir.resolve(staged.getFileName().toString()));
                }
            }
            for (Path old : backup.keySet()) {
                if (!targets.contains(old)) Files.deleteIfExists(old);
            }
            deleteTree(temp);
            return true;
        } catch (Exception ignored) {
            restoreBytes(dir, backup);
            deleteTree(temp);
            return false;
        }
    }

    /** 删除目录下全部 JSON；返回是否全部删除成功 */
    public static boolean deleteAllJson(Path dir) {
        if (dir == null || !Files.isDirectory(dir)) return true;
        boolean ok = true;
        for (Path file : listJsonFiles(dir)) {
            try {
                Files.deleteIfExists(file);
            } catch (Exception ignored) {
                ok = false;
            }
        }
        return ok;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  内部事务工具
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 优先原子替换；文件系统不支持时退回同文件系统普通替换 */
    public static void moveReplace(Path source, Path target) throws IOException {
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ignored) {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /** 读取目录下全部 JSON 的字节内容；任一文件不可读返回 {@code null}（表示不可安全替换） */
    private static Map<Path, byte[]> backupBytes(Path dir) {
        Map<Path, byte[]> result = new HashMap<>();
        for (Path file : listJsonFiles(dir)) {
            try {
                result.put(file, Files.readAllBytes(file));
            } catch (Exception ignored) {
                return null;
            }
        }
        return result;
    }

    /** 按字节备份恢复目录：删除备份中不存在的新增文件，再写回全部旧文件 */
    private static void restoreBytes(Path dir, Map<Path, byte[]> backup) {
        try {
            Files.createDirectories(dir);
        } catch (Exception ignored) {
            return;
        }
        for (Path file : listJsonFiles(dir)) {
            if (!backup.containsKey(file)) {
                try {
                    Files.deleteIfExists(file);
                } catch (Exception ignored) {
                    // 单个文件删不掉不影响其余恢复动作
                }
            }
        }
        for (Map.Entry<Path, byte[]> entry : backup.entrySet()) {
            try {
                Files.write(entry.getKey(), entry.getValue());
            } catch (Exception ignored) {
                // 同上
            }
        }
    }

    /** 递归删除目录；失败不影响已完成的主事务 */
    public static void deleteTree(Path dir) {
        if (dir == null || !Files.exists(dir)) return;
        try (Stream<Path> stream = Files.walk(dir)) {
            stream.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (Exception ignored) {
                    // 尽力清理
                }
            });
        } catch (Exception ignored) {
            // 尽力清理
        }
    }

    /** 目录字节备份（供需要自定义迁移流程的调用方复用） */
    public static Map<Path, byte[]> backupDirectory(Path dir) {
        return backupBytes(dir);
    }

    /** 目录按字节回滚（配合 {@link #backupDirectory(Path)} 使用） */
    public static void restoreDirectory(Path dir, Map<Path, byte[]> backup) {
        if (backup == null) return;
        restoreBytes(dir, backup);
    }

    /** 复制目录内容（用于迁移前的整体备份），返回是否全部复制成功 */
    public static boolean copyDirectory(Path from, Path to) {
        if (from == null || to == null || !Files.isDirectory(from)) return false;
        boolean ok = true;
        try {
            Files.createDirectories(to);
        } catch (Exception ignored) {
            return false;
        }
        for (Path file : listJsonFiles(from)) {
            try {
                Files.copy(file, to.resolve(file.getFileName().toString()),
                    StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Exception ignored) {
                ok = false;
            }
        }
        return ok;
    }

    /** 备用：按 {@code LinkedHashMap} 保序返回（供迁移流程按修改时间排序使用） */
    public static Map<Path, JsonObject> readAllOrdered(Path dir) {
        Map<Path, JsonObject> result = new LinkedHashMap<>();
        for (Path file : listJsonFiles(dir)) {
            JsonObject json = readJson(file);
            if (json != null) result.put(file, json);
        }
        return result;
    }
}
