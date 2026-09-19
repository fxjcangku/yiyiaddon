import com.yiyiaddon.ui.component.VersionHeader;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Surface;
import java.nio.file.Files;
import java.nio.file.Path;

/** 用真实 Skija 控件离屏检查页头的深浅主题与命中，不冒充游戏内截图。 */
public final class VersionHeaderPreview {
    public static void main(String[] args) throws Exception {
        for (String theme : new String[]{"apple_dark", "white"}) {
            ClickGuiThemeManager.select(theme);
            var tc = ClickGuiThemeColors.current();
            try (var surface = Surface.makeRasterN32Premul(1100, 180)) {
                var canvas = surface.getCanvas();
                canvas.clear(ClickGuiThemeColors.withAlpha(tc.field, 1));
                canvas.scale(2, 2);
                MinecraftText.draw(canvas, "首页", 14, 38, 19, tc.primaryText, 1);
                MinecraftText.draw(canvas, "yiyiaddon 客户端控制中心", 14, 55, 11, tc.secondaryText, 1);
                int[] calls = new int[3];
                var header = new VersionHeader(() -> calls[0]++, () -> calls[1]++, () -> calls[2]++);
                header.draw(canvas, 263, 16, 1, -1, -1, 1, "v1.0-beta1 · 测试版", "已是最新版本");
                // 三个按钮各自的中心点应路由到对应回调；右键不得触发，页头文字不得触发。
                header.onClick(365, 48, 263, 16, 0);
                header.onClick(425, 48, 263, 16, 0);
                header.onClick(485, 48, 263, 16, 0);
                header.onClick(425, 48, 263, 16, 1);
                header.onClick(280, 20, 263, 16, 0);
                if (calls[0] != 1 || calls[1] != 1 || calls[2] != 1) throw new AssertionError("按钮命中错误");
                try (var image = surface.makeImageSnapshot(); var data = image.encodeToData()) {
                    Files.write(Path.of("build/update-header-" + theme + ".png"), data.getBytes());
                }
            }
        }
        System.out.println("深浅主题页头绘制及三个按钮命中检查通过");
    }
}
