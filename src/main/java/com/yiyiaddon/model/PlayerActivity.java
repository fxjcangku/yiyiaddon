package com.yiyiaddon.model;

import com.google.gson.JsonObject;

/**
 * 玩家当前活动快照，对应后端 {@code player_activity} 字段组。
 */
public record PlayerActivity(double posX, double posY, double posZ, String dimension, float health,
                             int foodLevel, String gameMode, String currentActivity) {

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("pos_x", posX);
        json.addProperty("pos_y", posY);
        json.addProperty("pos_z", posZ);
        json.addProperty("dimension", dimension);
        json.addProperty("health", health);
        json.addProperty("food_level", foodLevel);
        json.addProperty("game_mode", gameMode);
        json.addProperty("current_activity", currentActivity);
        return json;
    }
}
