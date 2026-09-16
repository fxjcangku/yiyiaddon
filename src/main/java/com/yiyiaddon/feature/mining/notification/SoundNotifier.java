package com.yiyiaddon.feature.mining.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

/**
 * 音效通知系统
 * 
 * 在关键状态转换时播放音效提示用户
 * 使用原版音效，不需要额外资源包
 */
public class SoundNotifier {
    /** Minecraft 客户端实例 */
    private final Minecraft mc = Minecraft.getInstance();
    
    /** 是否启用音效通知 */
    private boolean enabled = true;
    
    /** 音量大小（0.0-1.0） */
    private float volume = 1.0f;
    
    /**
     * 设置是否启用音效
     * 
     * @param enabled true启用，false禁用
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * 设置音量
     * 
     * @param volume 音量值，自动限制在0.0-1.0范围内
     */
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    /**
     * 播放基础音效
     * 
     * @param pitch 音调（0.5=低沉，1.0=正常，1.5=清脆）
     */
    private void playSound(float pitch) {
        if (!enabled || mc.player == null || mc.level == null) return;
        mc.level.playLocalSound(
            mc.player.getX(),
            mc.player.getY(),
            mc.player.getZ(),
            SoundEvents.EXPERIENCE_ORB_PICKUP,
            SoundSource.PLAYERS,
            volume,
            pitch,
            false
        );
    }
    
    /**
     * 卸货完成提示
     * 播放清脆高音表示操作成功
     */
    public void notifyUnloadComplete() {
        playSound(1.5f);
    }
    
    /**
     * 食物不足警告
     * 播放中音提醒用户补充食物
     */
    public void notifyLowFood() {
        playSound(0.8f);
    }
    
    /**
     * 工具损坏警告
     * 播放低音提醒用户更换工具
     */
    public void notifyToolDamaged() {
        playSound(0.5f);
    }
    
    /**
     * 卡死检测警报
     * 播放铁砧音效引起注意
     */
    public void notifyStuck() {
        if (!enabled || mc.player == null || mc.level == null) return;
        mc.level.playLocalSound(
            mc.player.getX(),
            mc.player.getY(),
            mc.player.getZ(),
            SoundEvents.ANVIL_FALL,
            SoundSource.PLAYERS,
            volume,
            0.6f,
            false
        );
    }
    
    /**
     * 死亡事件通知
     * 播放凋零生成音效表示严重状况
     */
    public void notifyDeath() {
        if (!enabled || mc.player == null || mc.level == null) return;
        mc.level.playLocalSound(
            mc.player.getX(),
            mc.player.getY(),
            mc.player.getZ(),
            SoundEvents.WITHER_SPAWN,
            SoundSource.HOSTILE,
            volume * 0.5f,
            0.8f,
            false
        );
    }
    
    /**
     * 传送成功提示
     * 播放提示音确认传送已完成
     */
    public void notifyTeleportSuccess() {
        playSound(1.2f);
    }
    
    /**
     * 开始挖矿提示
     * 播放标准音调表示任务开始
     */
    public void notifyMiningStart() {
        playSound(1.0f);
    }
    
    /**
     * 工具耐久度低警告
     * 播放较低音调提醒用户及时修复
     */
    public void notifyLowDurability() {
        playSound(0.7f);
    }
}
