package com.yiyiaddon.dev.seedpoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试世界夹具：用固定种子建一个原版主世界，保证实验可重复。
 *
 * <p><b>为什么要自动建世界</b>：本次实验要求「同版本、同 Seed、多固定区块、可重复」。
 * 手工在世界管理界面里点一遍既不可重复也容易带错设置，所以这里直接走原版的世界创建入口
 * {@code WorldOpenFlows#createFreshLevel}（WorldOpenFlows.java:96-102，public），
 * 参数与原版「创建新世界」默认值一致：普通世界维度、生成建筑、非极限、允许指令
 * （允许指令只为开发期方便，与本实验结论无关）。</p>
 *
 * <p>每次运行都会先删掉同名存档目录再重建，因此「重复跑同一组参数」得到的是同一套真实生成结果，
 * 上一次实验留下的任何改动都不可能污染下一次。</p>
 */
public final class SeedPocWorldFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 实验专用存档名（纯 ASCII，避免路径问题）。 */
    public static final String LEVEL_ID = "seedpoc-known-seed";

    /** 上一次建世界时存档目录是否被确认「删除或本来就不存在」；false = 旧世界可能残留，实验不成立。 */
    private static volatile boolean lastFresh;

    /** 上一次建世界的新鲜度说明（报告证据）。 */
    private static volatile String lastFreshNote = "未记录（本次运行没有建过世界）";

    private SeedPocWorldFactory() {
    }

    /** 上一次建世界的新鲜度是否成立。 */
    public static boolean lastFreshOk() {
        return lastFresh;
    }

    /** 上一次建世界的新鲜度说明。 */
    public static String lastFreshNote() {
        return lastFreshNote;
    }

    /** 用指定种子新建测试世界并进入。必须在客户端线程调用。 */
    public static void createFreshWorld(Minecraft client, long seed) {
        boolean removed = deleteExistingWorld();
        if (!removed) {
            lastFresh = false;
            lastFreshNote = "建世界前存档目录清理失败（旧世界可能残留），本轮实验不成立";
        } else {
            lastFresh = true;
            lastFreshNote = "建世界前已删除上次实验存档并重建（全新 world / region / poi / entities）";
        }
        LevelSettings settings = new LevelSettings(
                LEVEL_ID,
                GameType.SURVIVAL,
                LevelSettings.DifficultySettings.DEFAULT,
                true,
                WorldDataConfiguration.DEFAULT);
        WorldOptions options = new WorldOptions(seed, true, false);
        LOGGER.info("{}：正在用种子 {} 新建测试世界 {}（原版普通世界设置）",
                SeedPocConstants.LOG_KEY, seed, LEVEL_ID);
        client.createWorldOpenFlows().createFreshLevel(
                LEVEL_ID, settings, options, WorldPresets::createNormalWorldDimensions, null);
    }

    /** 删掉上一次实验的存档目录，保证这次是干净生成；返回是否确认「已删除或本来就不存在」。 */
    private static boolean deleteExistingWorld() {
        Path worldDir = FabricLoader.getInstance().getGameDir().resolve("saves").resolve(LEVEL_ID);
        if (!Files.exists(worldDir)) {
            lastFreshNote = "建世界前存档目录不存在（首次运行）";
            return true;
        }
        try (Stream<Path> paths = Files.walk(worldDir)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(path);
            }
            LOGGER.info("{}：已删除上一次的实验存档 {}", SeedPocConstants.LOG_KEY, worldDir);
            return !Files.exists(worldDir);
        } catch (IOException error) {
            LOGGER.warn("{}：实验存档清理失败（若随后建世界失败，请手动删除 {}）",
                    SeedPocConstants.LOG_KEY, worldDir, error);
            return false;
        }
    }
}
