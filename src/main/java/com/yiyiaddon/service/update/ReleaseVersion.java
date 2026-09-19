package com.yiyiaddon.service.update;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 发布版本比较：兼容 v1.0、1.0.0 与 beta1 / beta.1；构建元数据不影响新旧顺序。 */
public record ReleaseVersion(List<BigInteger> numbers, String qualifier) implements Comparable<ReleaseVersion> {
    private static final Pattern FORMAT = Pattern.compile(
            "^[vV]?(\\d+\\.\\d+(?:\\.\\d+)?)(?:-([0-9A-Za-z]+(?:[.-][0-9A-Za-z]+)*))?(?:\\+[0-9A-Za-z.-]+)?$");
    private static final Pattern PART = Pattern.compile("[a-z]+|[0-9]+");

    /** 不认识的版本返回 null，避免把开发占位版本或任意标签当作更新。 */
    public static ReleaseVersion parse(String value) {
        if (value == null || value.length() > 80) return null;
        Matcher match = FORMAT.matcher(value.strip());
        if (!match.matches()) return null;
        return new ReleaseVersion(java.util.Arrays.stream(match.group(1).split("\\."))
                .map(BigInteger::new).toList(), match.group(2) == null ? "" : match.group(2).toLowerCase(Locale.ROOT));
    }

    @Override
    public int compareTo(ReleaseVersion other) {
        for (int i = 0; i < Math.max(numbers.size(), other.numbers.size()); i++) {
            int order = (i < numbers.size() ? numbers.get(i) : BigInteger.ZERO)
                    .compareTo(i < other.numbers.size() ? other.numbers.get(i) : BigInteger.ZERO);
            if (order != 0) return order;
        }
        // 同一数字版本下，正式版晚于全部预发布版；beta10 必须晚于 beta2。
        if (qualifier.isEmpty() || other.qualifier.isEmpty()) {
            return Boolean.compare(qualifier.isEmpty(), other.qualifier.isEmpty());
        }
        List<String> left = PART.matcher(qualifier).results().map(r -> r.group()).toList();
        List<String> right = PART.matcher(other.qualifier).results().map(r -> r.group()).toList();
        for (int i = 0; i < Math.min(left.size(), right.size()); i++) {
            String a = left.get(i), b = right.get(i);
            boolean an = Character.isDigit(a.charAt(0)), bn = Character.isDigit(b.charAt(0));
            int order = an && bn ? new BigInteger(a).compareTo(new BigInteger(b))
                    : an != bn ? (an ? -1 : 1) : a.compareTo(b);
            if (order != 0) return order;
        }
        return Integer.compare(left.size(), right.size());
    }
}
