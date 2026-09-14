package com.yiyiaddon.ui.navigation;

import com.yiyiaddon.ui.page.BasePage;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面路由：管理左侧导航的根页面，以及根页面之上的进入栈。
 *
 * <p>导航切换会清空进入栈；模块中心的三级跳转（分类 → 模块列表 → 模块页面）通过
 * {@link #open(BasePage)} 逐级压栈，由 {@link #back()} 或界面的返回按钮逐级退出。</p>
 */
public final class PageRouter {

    private final List<BasePage> roots = new ArrayList<>();
    private final List<BasePage> stack = new ArrayList<>();
    private int index;

    public void addRoot(BasePage page) {
        if (page != null) roots.add(page);
    }

    public List<BasePage> roots() {
        return roots;
    }

    public int rootCount() {
        return roots.size();
    }

    /** 当前选中的导航项下标。 */
    public int index() {
        return index;
    }

    /** 切换导航项并清空进入栈。 */
    public void select(int target) {
        if (target < 0 || target >= roots.size()) return;
        index = target;
        stack.clear();
    }

    /** 当前展示的页面：进入栈非空时取栈顶，否则取当前导航项的根页面。 */
    public BasePage current() {
        if (!stack.isEmpty()) return stack.get(stack.size() - 1);
        return roots.isEmpty() ? null : roots.get(index);
    }

    public boolean canGoBack() {
        return !stack.isEmpty();
    }

    /** 进入下一级页面。 */
    public void open(BasePage page) {
        if (page != null) stack.add(page);
    }

    /** 返回上一级；已在根页面时返回 false。 */
    public boolean back() {
        if (stack.isEmpty()) return false;
        stack.remove(stack.size() - 1);
        return true;
    }

    /** 回到底部并清空进入栈。 */
    public void reset() {
        stack.clear();
    }

    /** 替换指定导航项的根页面，用于界面重置后的页面重建。 */
    public void replaceRoot(int target, BasePage page) {
        if (page == null || target < 0 || target >= roots.size()) return;
        roots.set(target, page);
    }
}
