package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.ModuleRow;
import io.github.humbleui.skija.Canvas;

/**
 * 紧凑型模块页基类：固定「顶部信息块 → 中间核心配置 → 底部状态信息」三段结构。
 *
 * <p>子类只负责把元素交给对应区块，纵向坐标、滚动偏移、命中与拖动分发全部由本类统一处理。
 * 三段顶部坐标只在 {@link #computeTops} 里推算一次，绘制、命中、拖动都读同一组结果，且区块间距
 * 规则与 {@link #getTotalHeight()} 完全一致，因此不存在绘制与命中的坐标漂移。</p>
 *
 * <p>内容总高按元素实时高度求和，折叠区展开到一半时滚动上限也随之变化；内容不足一屏时上层
 * 滚动条自动隐藏。</p>
 */
public abstract class CompactModulePage extends BasePage {

    /** 三个区块之间的垂直间距。 */
    protected static final float SECTION_GAP = 14f;
    /**
     * 区块内行与行之间的间距：就是模块中心的行距（{@link ModuleRow#ROW_GAP}）。
     *
     * <p>用户 2026-09-16 点进模块页后说「还有点击进去的时候 模块也要缩小 现在都不对称」——
     * 模块页的行高收到模块中心那一档（24）之后，行距也必须是同一个数，否则同一组行一会儿松一会儿紧。
     * 行距由 {@code CompactStack} 统一拿它排行，绘制、命中与
     * {@link #getTotalHeight()}（滚动上限）都靠这一份值，不存在三处各算一遍。</p>
     */
    protected static final float ROW_GAP = ModuleRow.ROW_GAP;

    private final CompactStack core = new CompactStack(ROW_GAP);
    private final CompactStack footer = new CompactStack(ROW_GAP);

    private CompactElement header;
    private float headerTop;
    private float coreTop;
    private float footerTop;

    /** 顶部信息块：模块名由屏幕头部给出，这里放状态、开关与快捷键。 */
    protected final void setHeader(CompactElement element) {
        this.header = element;
    }

    /** 中间核心配置：高频操作，一屏可见。 */
    protected final void addCore(CompactElement element) {
        core.add(element);
    }

    /** 底部状态信息：运行结果、统计与折叠的高级设置。 */
    protected final void addFooter(CompactElement element) {
        footer.add(element);
    }

    @Override
    public float getTotalHeight() {
        float headerHeight = headerHeight();
        float coreHeight = coreHeight();
        float footerHeight = footerHeight();
        float total = headerHeight;
        if (coreHeight > 0f) total += (headerHeight > 0f ? SECTION_GAP : 0f) + coreHeight;
        if (footerHeight > 0f) total += (coreHeight > 0f ? SECTION_GAP : 0f) + footerHeight;
        return total;
    }

    @Override
    public void update(float dt) {
        if (header != null) header.update(dt);
        core.update(dt);
        footer.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset,
                     float mouseX, float mouseY) {
        computeTops(y - scrollOffset);
        float viewportTop = y;
        float viewportBottom = y + contentH;
        if (header != null && headerTop + header.height() > viewportTop && headerTop < viewportBottom) {
            header.draw(canvas, x, headerTop, contentW, alpha, mouseX, mouseY);
        }
        core.draw(canvas, x, coreTop, contentW, alpha, viewportTop, viewportBottom, mouseX, mouseY);
        footer.draw(canvas, x, footerTop, contentW, alpha, viewportTop, viewportBottom, mouseX, mouseY);
    }

    @Override
    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset,
                           int button) {
        computeTops(contentY - scrollOffset);
        if (header != null && header.onClick(mx, my, contentX, headerTop, contentW, button)) {
            return true;
        }
        if (core.onClick(mx, my, contentX, coreTop, contentW, Float.MAX_VALUE, button)) {
            return true;
        }
        return footer.onClick(mx, my, contentX, footerTop, contentW, Float.MAX_VALUE, button);
    }

    @Override
    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset) {
        computeTops(contentY - scrollOffset);
        if (core.onDrag(mx, my, contentX, coreTop, contentW, Float.MAX_VALUE)) {
            return true;
        }
        return footer.onDrag(mx, my, contentX, footerTop, contentW, Float.MAX_VALUE);
    }

    @Override
    public void releaseDrag() {
        core.releaseDrag();
        footer.releaseDrag();
    }

    private float headerHeight() {
        return header == null ? 0f : header.height();
    }

    private float coreHeight() {
        return core.isEmpty() ? 0f : core.height();
    }

    private float footerHeight() {
        return footer.isEmpty() ? 0f : footer.height();
    }

    /** 推算三段顶部坐标；间距规则与 {@link #getTotalHeight()} 一致。 */
    private void computeTops(float top) {
        float headerHeight = headerHeight();
        float coreHeight = coreHeight();
        headerTop = top;
        float cursor = top + headerHeight;
        if (coreHeight > 0f) cursor += headerHeight > 0f ? SECTION_GAP : 0f;
        coreTop = cursor;
        cursor += coreHeight;
        if (footer.isEmpty()) {
            footerTop = cursor;
            return;
        }
        if (coreHeight > 0f) cursor += SECTION_GAP;
        footerTop = cursor;
    }
}
