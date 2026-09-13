package com.yiyiaddon;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.integration.baritone.BaritoneTranslationToggle;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * yiyiaddon 主入口。
 *
 * <p>这是纯客户端 Mod：这里只做与界面无关的初始化（配置载入、日志、汉化开关探针），
 * 界面与输入相关初始化在 {@link YiyiAddonClient}。</p>
 */
public final class YiyiAddon implements ModInitializer {

    public static final String MOD_ID = "yiyiaddon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        AddonConfig.load();
        LOGGER.info("yiyiaddon initialised (baritone localisation: {})", BaritoneTranslationToggle.enabled() ? "on" : "off");
    }
}
