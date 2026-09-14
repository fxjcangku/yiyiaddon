package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.platform.ClientIdentity;

import java.time.Duration;

/**
 * 指令活动上报。
 *
 * <p>只上传功能名与分类，不携带完整指令参数、聊天内容或密码；后端按 30 秒窗口去重。</p>
 *
 * <p>接入方式：拦截客户端发送指令的位置（{@code ClientPacketListener.sendCommand}），
 * 把原始指令文本交给 {@link #onOutgoingCommand(String)}。</p>
 */
public final class CommandActivityService {

    private static final String CATEGORY_COMMAND = "指令";
    private static final Duration TIMEOUT = Duration.ofSeconds(4);

    private CommandActivityService() {
    }

    /** 处理一条客户端外发指令。 */
    public static void onOutgoingCommand(String commandLine) {
        if (commandLine == null || commandLine.isBlank()) return;
        String text = commandLine.trim();
        if (text.startsWith("/")) text = text.substring(1);
        if (text.isBlank()) return;

        int space = text.indexOf(' ');
        String commandName = space < 0 ? text : text.substring(0, space);
        if (commandName.isBlank()) return;

        report(commandName, CATEGORY_COMMAND);
    }

    /**
     * 上报一次功能使用。
     *
     * @param featureName 功能名（指令名或内部功能标识）
     * @param category    分类，如 指令 / 聊天 / 查询
     */
    public static void report(String featureName, String category) {
        String uuid = ClientIdentity.uuidString();
        String username = ClientIdentity.name();
        if (uuid == null || username == null || featureName == null || featureName.isBlank()) return;

        JsonObject body = new JsonObject();
        body.addProperty("uuid", uuid);
        body.addProperty("username", username);
        body.addProperty("command_name", featureName);
        body.addProperty("category", category == null || category.isBlank() ? CATEGORY_COMMAND : category);
        BackgroundTasks.run("yiyiaddon-command-activity",
                () -> HttpApi.post("/api/command-activity", body, TIMEOUT));
    }
}
