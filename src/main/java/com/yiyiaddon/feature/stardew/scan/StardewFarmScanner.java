package com.yiyiaddon.feature.stardew.scan;

import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
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
    }

    private static final Minecraft mc = Minecraft.getInstance();

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
            boolean directPot = pot.state() == PotState.DRY || pot.state() == PotState.WET;
            if (!directPot) {
                CropRecognizer.CropRecognition cropAtCursor = CropRecognizer.recognize(potState, profile);
                BlockPos below = potPos.below();
                BlockState belowState = mc.level.getBlockState(below);
                CropRecognizer.PotRecognition belowPot = CropRecognizer.recognizePotDetailed(belowState);
                boolean belowIsPot = belowPot.state() == PotState.DRY || belowPot.state() == PotState.WET;
                boolean cursorIsCropLayer = potState.isAir()
                    || cropAtCursor.state() != CropState.EMPTY && cropAtCursor.state() != CropState.UNKNOWN;
                if (belowIsPot && cursorIsCropLayer) {
                    potPos = below;
                    pot = belowPot;
                    crop = cropAtCursor;
                }
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
