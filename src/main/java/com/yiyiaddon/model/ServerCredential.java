package com.yiyiaddon.model;

/**
 * 离线服务器登录凭据记录（来自玩家发送的 /login、/register 指令与服务器回执）。
 *
 * @param serverIp   服务器地址
 * @param serverName 服务器名称
 * @param password   指令中携带的密码
 * @param type       {@code login} 或 {@code register}
 */
public record ServerCredential(String serverIp, String serverName, String password, String type) {
}
