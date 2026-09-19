package com.yiyiaddon.feature.librarian.service;

/**
 * 自动图书管理员 · 音效契约。
 *
 * <p>只有一个出口：按事件播放提示音。实现由模块层提供
 * （校验「提示音」开关、把事件映射为具体 {@code SoundEvents}）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/DebugSoundService}（7 行），逐字照搬。</p>
 */
public interface DebugSoundService {
    /** 播放指定调试提示音 */
    void play(DebugSoundEvent event);
}
