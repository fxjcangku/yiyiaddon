package com.yiyiaddon.feature.autofarm.model;

import com.yiyiaddon.platform.world.WorldContextFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * 一个已绑定的农场锚点：坐标 + 所属维度。
 *
 * 点位存储只存三个整数时不带维度信息，跨维度会把主世界的坐标当成下界的坐标用，
 * 后果是发包发到一堆空气上。所以这里改用字符串序列化，把维度 id 一起持久化。
 *
 * 序列化格式：{@code x,y,z,维度id}，例如 {@code 120,64,-88,minecraft:overworld}。
 * 空字符串代表未绑定。
 */
public record FarmSite(BlockPos pos, ResourceKey<Level> dimension) {

    /** 未绑定时的存储值 */
    public static final String UNBOUND = "";

    /** 序列化成设置里保存的字符串 */
    public String serialize() {
        return pos.getX() + "," + pos.getY() + "," + pos.getZ() + "," + dimension.identifier();
    }

    /** 反序列化，格式非法或空串返回 null */
    public static FarmSite parse(String raw) {
        if (raw == null || raw.isBlank()) return null;

        String[] parts = raw.split(",");
        if (parts.length != 4) return null;

        try {
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            int z = Integer.parseInt(parts[2].trim());

            Identifier id = Identifier.tryParse(parts[3].trim());
            if (id == null) return null;

            return new FarmSite(new BlockPos(x, y, z), ResourceKey.create(Registries.DIMENSION, id));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /** 以玩家当前所在维度构造锚点 */
    public static FarmSite here(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return null;
        return new FarmSite(pos, mc.level.dimension());
    }

    /** 该锚点是否位于玩家当前所在维度 */
    public boolean inCurrentDimension() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;
        return mc.level.dimension().equals(dimension);
    }

    /** 面向玩家的简短描述 */
    public String describe() {
        return describe("§f");
    }

    public String describe(String dimColor) {
        String dimName = WorldContextFormatter.dimensionSummary(dimension.identifier().toString());
        return "§7X§f" + pos.getX() + " §7Y§f" + pos.getY() + " §7Z§f" + pos.getZ() + " §8▸ " + dimColor + dimName + "§r";
    }
}
