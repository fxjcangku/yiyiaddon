package com.yiyiaddon.feature.enchant.model;

/** 点位坐标（方块坐标）。挂机视角与铁砧朝向是点位级附加数据，由存储层单独承载。 */
public record EnchantPoint(int x, int y, int z) {
}
