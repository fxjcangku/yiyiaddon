package com.yiyiaddon.model;

/**
 * 客户端连接侧的网络归属信息，用于后端判定正版/地理位置/疑似代理。
 *
 * @param ip          客户端公网 IP
 * @param countryCode ISO 3166-1 两位国家码
 * @param region      一级行政区名（英文原名，如 Guangdong / Oregon；探测源未给出时为 null；
 *                    界面按 RegionNames 译成中文，未收录时只显示国名，不显示英文原名）
 * @param isp         运营商名
 * @param asOrg       自治系统组织名
 * @param asn         自治系统号，未知为 -1
 * @param proxy       是否疑似代理
 * @param proxyType   代理类型（VPN / PROXY / TOR / MOBILE），无则为 null
 */
public record ClientNetworkInfo(String ip, String countryCode, String region, String isp, String asOrg, int asn,
                                boolean proxy, String proxyType) {

    /** 三路查询全部失败时的占位值。 */
    public static ClientNetworkInfo unknown() {
        return new ClientNetworkInfo(null, null, null, null, null, -1, false, null);
    }
}
