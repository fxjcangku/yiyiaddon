package com.yiyiaddon.feature.stardew.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 水位条通用结构探测：不依赖 namespace、目录名、字形名，只靠「端帽 + N 个重复格」的结构。
 *
 * <p><b>为什么需要它：</b>解压实测「晴海小镇9.7」包发现，同类 UI 在不同插件里的目录完全不同
 * （{@code customcrops/textures/font/bars}、{@code nameplates/textures/font/bubbles}、
 * {@code nong/textures/font/lore}…），全包 64 个 font json 里绝大多数不是水位条。
 * 因此「按 bars 目录认字形」只能作为强证据，换一套资源包就未必成立；真正跨服务器通用的是
 * <b>结构</b>：水位条 = 左端帽 + N 个格子 + 右端帽，端帽在同一行里各出现一次。</p>
 *
 * <p><b>两种格宽都支持：</b>有的服把每格配成一个字形（{@code full}/{@code empty}），
 * 有的把每格配成两个字（前缀 + 状态成对出现）。两种布局都能算出格数：
 * 前者 {@code 格数 = 去掉端帽后的字符数}，后者 {@code 格数 = 去掉端帽后的字符数 ÷ 2}。</p>
 *
 * <p><b>用真实水量反证：</b>容量不可能小于当前水量，因此 {@code 格数 < 当前水量} 的候选一律丢弃。
 * 两个候选都成立时取<b>较小</b>值——估小只会多补一轮（发包次数由每轮验证兜底），
 * 估大才会多发无意义的包，所以永远偏保守。</p>
 */
public final class StardewWaterBarProbe {

    private StardewWaterBarProbe() {
    }

    /**
     * 探测一行文本里的水位条格数。
     *
     * @param line         原始文本（可含 § 格式码与空白）
     * @param currentWater 已知当前水量；未知传 null（则不做反证，接受任何结构成立的结果）
     * @return 格数（= 容量）；不像水位条返回 null
     */
    public static Integer cellsIn(String line, Integer currentWater) {
        int[] cps = stripFormatting(line);
        int n = cps.length;
        // 至少 2 个端帽 + 2 格；端帽必须首尾各一次，否则视为普通文字 / 装饰线
        if (n < 4) return null;
        if (cps[0] == cps[n - 1]) return null;

        Map<Integer, Integer> freq = new HashMap<>();
        for (int cp : cps) freq.merge(cp, 1, Integer::sum);
        if (freq.size() < 2) return null;
        if (freq.getOrDefault(cps[0], 0) != 1 || freq.getOrDefault(cps[n - 1], 0) != 1) return null;

        int inner = n - 2;
        List<Integer> candidates = new ArrayList<>(2);
        if (inner >= 2) candidates.add(inner);
        if (inner >= 4 && inner % 2 == 0 && isPairedLayout(cps, 1, n - 1)) candidates.add(inner / 2);

        int best = 0;
        for (int cells : candidates) {
            if (currentWater != null && cells < currentWater) continue;
            if (best == 0 || cells < best) best = cells;
        }
        return best > 0 ? best : null;
    }

    /**
     * 是否「每格两个字」布局：中间段奇数偏移是全同前缀，偶数偏移由 ≤2 种状态字形组成。
     *
     * <p>纯同字形段（例如空壶整条都是空格字形）不算成对布局，否则会把格数砍半。</p>
     */
    private static boolean isPairedLayout(int[] cps, int from, int to) {
        if (to - from < 4) return false;
        int prefix = cps[from];
        Set<Integer> states = new HashSet<>();
        for (int i = from; i < to; i++) {
            if (((i - from) & 1) == 0) {
                if (cps[i] != prefix) return false;
            } else {
                states.add(cps[i]);
            }
        }
        return !states.isEmpty() && states.size() <= 2;
    }

    /** 去掉 § 颜色 / 格式码与全部空白，返回可见字符的 codepoint 序列。 */
    private static int[] stripFormatting(String line) {
        if (line == null || line.isEmpty()) return new int[0];
        StringBuilder clean = new StringBuilder(line.length());
        for (int i = 0; i < line.length(); ) {
            char ch = line.charAt(i);
            if (ch == '\u00a7') {           // § 后跟一个格式字符，整对丢弃
                i += i + 1 < line.length() ? 2 : 1;
                continue;
            }
            int cp = line.codePointAt(i);
            if (!Character.isWhitespace(cp)) clean.appendCodePoint(cp);
            i += Character.charCount(cp);
        }
        return clean.codePoints().toArray();
    }
}
