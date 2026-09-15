package com.yiyiaddon.feature.stardew.scan;

import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 星露谷农场分帧扫描器。
 *
 * <p>在农田范围内逐格扫描种植盆，识别盆干湿与盆上方作物状态。每 tick 有扫描预算，
 * 避免 {@code BlockStateModelResolver}（读资源包文件）一次性解析大范围导致卡顿。</p>
 */
public final class StardewFarmScanner {

    /** 一次扫描命中的单格结果 */
    public record Cell(BlockPos potPos, PotState potState, String potKey, CropRecognizer.CropRecognition crop) {
        public BlockPos cropPos() {
            return potPos.above();
        }

        /** 是否属于可识别的种植盆（干/湿），或虽盆型未知但其上方确有作物 */
        public boolean usable() {
            return potState == PotState.DRY || potState == PotState.WET;
        }

        /**
         * 盆型组（普通 / 下界 / 末地）。
         *
         * <p>不额外存字段：盆型就写在 {@link #potKey} 的序号里（{@code dry_pot_2} → 下界盆），
         * 由 {@link PotGroup#ofPotKey} 现算，扫描结果与识别层因此不会各存一份而漂移。</p>
         */
        public PotGroup potGroup() {
            return PotGroup.ofPotKey(potKey);
        }
    }

    private static final Minecraft mc = Minecraft.getInstance();

    /**
     * 把任意一格归一到它所属的<b>种植盆那一格</b>：下方能确认为盆（干 / 湿）就取下方，否则原样返回。
     *
     * <p><b>为什么需要它：</b>盆上有作物时，准星射线打中的是<b>作物那一格</b>（盆上方一格），
     * 拿这一格当区域角点，区域框就会整体浮高一格 —— 与洒水器覆盖框共用同一个 Y 平面、互相重叠
     * （实机反馈「有作物的盆 ESP 会往上一格，跟洒水器渲染打架」）；空盆没有作物层，射线直接打中盆，
     * 所以只有有作物时才出问题。</p>
     *
     * <p><b>为什么只看下方：</b>「真盆下方永远不会是盆（盆只放在土地上）」是扫描归一用的同一条
     * 不变量，所以「下方是盆」时当前格必然不是盆；这样也不会踩到「作物名里含 pot 被误判成盆」的坑。</p>
     */
    public static BlockPos normalizeToPot(BlockPos pos) {
        if (pos == null || mc.level == null) return pos;
        BlockPos below = pos.below();
        CropRecognizer.PotRecognition pot = CropRecognizer.recognizePotDetailed(mc.level.getBlockState(below));
        return pot.state() == PotState.DRY || pot.state() == PotState.WET ? below : pos;
    }

    private BlockPos min;
    private BlockPos max;
    private BlockPos cursor;
    private boolean active;
    private final Set<BlockPos> emitted = new HashSet<>();

    /** 开始新范围扫描（游标回到起点） */
    public void begin(BlockPos min, BlockPos max) {
        this.min = min;
        this.max = max;
        this.cursor = new BlockPos(min.getX(), min.getY(), min.getZ());
        this.active = true;
        this.emitted.clear();
    }

    /** 停止扫描并清空范围 */
    public void reset() {
        this.min = null;
        this.max = null;
        this.cursor = null;
        this.active = false;
        this.emitted.clear();
    }

    /** 是否有范围可扫描 */
    public boolean bounded() {
        return active && min != null && max != null;
    }

    /** 农田最小角（含） */
    public BlockPos min() {
        return min;
    }

    /** 农田最大角（含） */
    public BlockPos max() {
        return max;
    }

    /** 单次是否已扫完一整轮 */
    public boolean complete() {
        return !active;
    }

    /**
     * 扫描一帧（预算内），返回本轮新发现的种植盆格。
     *
     * <p>扫完一整轮后自动关闭（active=false），由协调器重新 {@link #begin} 进入下一轮，
     * 保证「观察→决策→执行→再观察」的循环节律。</p>
     */
    public List<Cell> scanTick(StardewServerProfile profile, int budget) {
        List<Cell> found = new ArrayList<>();
        if (!bounded() || mc.level == null) return found;

        int scanned = 0;
        while (scanned < budget && cursor != null) {
            scanned++;
            BlockPos potPos = cursor;
            // 推进游标（Y → Z → X）
            advanceCursor();

            BlockState potState = mc.level.getBlockState(potPos);

            CropRecognizer.PotRecognition pot = CropRecognizer.recognizePotDetailed(potState);
            BlockState cropState = mc.level.getBlockState(potPos.above());
            CropRecognizer.CropRecognition crop = CropRecognizer.recognize(cropState, profile);

            // 玩家框选时可能点中绊线/自定义作物载体而不是其下方种植盆；若当前格本身
            // 能确认为作物且下方能确认为盆，统一归一回真实盆坐标，避免有菜时漏掉干盆。
            //
            // <b>这里不能先看「当前格是不是盆」再决定要不要归一：</b>作物名里含 "pot" 的
            // （sweet_potato_stage_N / potato_crate / teapot 等）会被判成「干盆」，一旦提前
            // 认定当前格就是盆，归一就被跳过 —— 结果是拿作物层那格当干盆去浇水：浇不到真盆、
            // 验证永远不是 WET，最后刷成「水壶缺水」，表现就是「已经滋润的盆被当成干枯」。
            CropRecognizer.CropRecognition cropAtCursor = CropRecognizer.recognize(potState, profile);
            BlockPos below = potPos.below();
            BlockState belowState = mc.level.getBlockState(below);
            CropRecognizer.PotRecognition belowPot = CropRecognizer.recognizePotDetailed(belowState);
            boolean belowIsPot = belowPot.state() == PotState.DRY || belowPot.state() == PotState.WET;
            boolean cursorIsCropLayer = potState.isAir()
                || cropAtCursor.state() != CropState.EMPTY && cropAtCursor.state() != CropState.UNKNOWN;
            // 真盆下方永远不会是盆（盆只放在土地上），所以命中「下方是盆」时当前格必然是作物层
            if (belowIsPot && cursorIsCropLayer) {
                potPos = below;
                pot = belowPot;
                crop = cropAtCursor;
            }

            // 盆识别成功（干/湿）→ 正常入列；盆型未知但其上方确有作物 → 回退按作物处理
            // （仅用于收割/清理，不浇水，因为浇水对象必须是被确认的盆）。
            boolean potOk = pot.state() == PotState.DRY || pot.state() == PotState.WET;
            boolean cropOk = crop.state() != CropState.EMPTY && crop.state() != CropState.UNKNOWN;
            if ((potOk || cropOk) && emitted.add(potPos)) {
                found.add(new Cell(potPos, pot.state(), pot.potKey(), crop));
            }
        }
        return found;
    }

    /** 游标按 Y→Z→X 顺序推进，越界后置 null（本轮结束） */
    private void advanceCursor() {
        if (cursor == null) return;
        int x = cursor.getX();
        int y = cursor.getY();
        int z = cursor.getZ();
        y++;
        if (y > max.getY()) {
            y = min.getY();
            z++;
            if (z > max.getZ()) {
                z = min.getZ();
                x++;
                if (x > max.getX()) {
                    cursor = null;
                    active = false;
                    return;
                }
            }
        }
        cursor = new BlockPos(x, y, z);
    }
}
