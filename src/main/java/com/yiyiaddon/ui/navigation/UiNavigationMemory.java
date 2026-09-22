package com.yiyiaddon.ui.navigation;

import java.util.List;

/**
 * 界面导航记忆：记住上一次关闭面板时停留在哪里（左侧导航项 + 下钻栈），下次打开原样恢复。
 *
 * <p><b>为什么只记「可重建的标识」而不是页面对象本身：</b>页面持有宿主屏幕的引用（模块卡片要回调
 * 屏幕去打开模块窗口），把页面对象存下来会连同旧屏幕一起吊住，而且下次打开的新屏幕与旧页面
 * 对不上。这里只记 {@code <类型>:<分类 id>} 这样的短标识，重开面板时按注册表重新装配页面，
 * 页面内部状态一律新建——记忆的是「位置」，不是「上一份控件」。</p>
 *
 * <p>存活范围是本次客户端进程：关掉面板（Esc / 关闭按钮）再打开回到原处；重启客户端回到首页。
 * 这是界面会话状态，不写进配置文件。</p>
 *
 * <p><b>模块页也要记住</b>（用户 2026-09-22：「ui 没有记得我关闭时候的记忆，我在星露谷模块关闭的 ui，
 * 她又回到首页了」）：模块页是<b>独立屏幕</b>（{@code ModuleScreen}），不在左侧导航的路由栈里，
 * 只记导航项等于「在模块页里关掉整个界面」这一情形永远回不到那一页。所以这里再记一个
 * {@link #moduleId()}：进模块页时登记，<b>正常返回上级（返回键 / Esc）时抹掉</b>；
 * 用 G 关掉整个界面时保留，下次打开直接落回那一页。</p>
 */
public final class UiNavigationMemory {

    /** 分类自带页面的标识前缀：{@code page:<分类 id>} */
    public static final String TOKEN_PAGE = "page";

    private static int rootIndex;
    private static List<String> tokens = List.of();
    /** 上次关掉整个界面时停留的模块页（模块 id）；没在模块页里关过为 {@code null}。 */
    private static String moduleId;

    private UiNavigationMemory() {
    }

    /** 上次关掉整个界面时停留的模块页（模块 id），没有则 {@code null}。 */
    public static String moduleId() {
        return moduleId;
    }

    /** 进入某个模块页时登记（下次「关掉整个界面再打开」直接落回这一页）。 */
    public static void rememberModule(String id) {
        moduleId = id == null || id.isBlank() ? null : id;
    }

    /** 从模块页正常返回上级：这一页不再算「上次停留的位置」。 */
    public static void forgetModule() {
        moduleId = null;
    }

    /** 上次停留的左侧导航项下标。 */
    public static int rootIndex() {
        return rootIndex;
    }

    /** 上次停留的下钻栈（自底向上）；无下钻时为空表。 */
    public static List<String> tokens() {
        return tokens;
    }

    /** 关闭面板时登记当前位置。 */
    public static void remember(int index, List<String> stackTokens) {
        rootIndex = Math.max(0, index);
        tokens = stackTokens == null ? List.of() : List.copyOf(stackTokens);
    }

    /** 忘记位置，回到首页（「重置界面设置」用）。 */
    public static void clear() {
        rootIndex = 0;
        tokens = List.of();
        moduleId = null;
    }

    /** 拼一个「分类 id」标识；分类为空时返回 {@code null}。 */
    public static String token(String type, String categoryId) {
        if (type == null || categoryId == null || categoryId.isBlank()) return null;
        return type + ':' + categoryId;
    }

    /** 取标识里的分类 id；不是本类标识时返回 {@code null}。 */
    public static String categoryId(String token, String type) {
        if (token == null || type == null) return null;
        String prefix = type + ':';
        return token.startsWith(prefix) ? token.substring(prefix.length()) : null;
    }
}
