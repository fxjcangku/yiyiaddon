package com.yiyiaddon.build;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * 基本块物理重排与加密常量驱动的跳转分派。
 * 保持原操作数栈及局部变量，不把它们装箱进共享状态数组，避免游戏热路径的大量分配。
 * 含异常表、监视器、构造器和过大方法由调用方排除，所有排除计入报告。
 */
final class FlowObfuscation implements Opcodes {
    private final SecureRandom random;
    private final ConstantEncryption encryption;
    int dispatches;

    FlowObfuscation(SecureRandom random, ConstantEncryption encryption) {
        this.random = random;
        this.encryption = encryption;
    }

    /** 切块后显式补全所有自然落空边，再打乱排列；入口固定跳回原始第一块。 */
    boolean apply(MethodNode method) throws GeneralSecurityException {
        Set<AbstractInsnNode> leaders = new HashSet<>();
        leaders.add(method.instructions.getFirst());
        for (AbstractInsnNode instruction : method.instructions) {
            if (instruction instanceof JumpInsnNode jump) leaders.add(jump.label);
            if (instruction instanceof TableSwitchInsnNode table) {
                leaders.add(table.dflt);
                leaders.addAll(table.labels);
            }
            if (instruction instanceof LookupSwitchInsnNode table) {
                leaders.add(table.dflt);
                leaders.addAll(table.labels);
            }
            if (endsBlock(instruction) && instruction.getNext() != null) leaders.add(instruction.getNext());
        }
        List<InsnList> blocks = new ArrayList<>();
        List<LabelNode> entries = new ArrayList<>();
        InsnList current = null;
        for (AbstractInsnNode instruction : method.instructions.toArray()) {
            if (leaders.contains(instruction)) {
                current = new InsnList();
                LabelNode entry = instruction instanceof LabelNode label ? label : new LabelNode();
                entries.add(entry);
                blocks.add(current);
                if (entry != instruction) current.add(entry);
            }
            method.instructions.remove(instruction);
            current.add(instruction);
        }
        if (blocks.size() < 3) {
            for (InsnList block : blocks) method.instructions.add(block);
            return false;
        }
        for (int i = 0; i < blocks.size(); i++) {
            InsnList block = blocks.get(i);
            AbstractInsnNode tail = executableTail(block);
            if (i + 1 < blocks.size() && fallsThrough(tail)) {
                block.add(new JumpInsnNode(GOTO, entries.get(i + 1)));
            }
            // 每条无条件边获得独立的加密分派键。默认路径不能到达，不修改业务条件。
            for (AbstractInsnNode instruction : block.toArray()) {
                if (instruction instanceof JumpInsnNode jump && jump.getOpcode() == GOTO) {
                    int key = random.nextInt();
                    LabelNode invalid = new LabelNode();
                    InsnList route = new InsnList();
                    route.add(new LdcInsnNode(encryption.integer(key)));
                    route.add(new LookupSwitchInsnNode(invalid, new int[]{key}, new LabelNode[]{jump.label}));
                    route.add(invalid);
                    route.add(new InsnNode(ACONST_NULL));
                    route.add(new InsnNode(ATHROW));
                    block.insertBefore(jump, route);
                    block.remove(jump);
                    dispatches++;
                }
            }
        }
        method.instructions.add(new JumpInsnNode(GOTO, entries.getFirst()));
        Collections.shuffle(blocks, random);
        for (InsnList block : blocks) method.instructions.add(block);
        // 原局部变量调试区间随物理顺序失效，发布流程本来就清理这些信息。
        method.localVariables = null;
        method.visibleLocalVariableAnnotations = null;
        method.invisibleLocalVariableAnnotations = null;
        return true;
    }

    private static AbstractInsnNode executableTail(InsnList block) {
        AbstractInsnNode node = block.getLast();
        while (node != null && node.getOpcode() < 0) node = node.getPrevious();
        return node;
    }

    private static boolean endsBlock(AbstractInsnNode node) {
        return node instanceof JumpInsnNode || node instanceof TableSwitchInsnNode
                || node instanceof LookupSwitchInsnNode || !fallsThrough(node);
    }

    private static boolean fallsThrough(AbstractInsnNode node) {
        if (node == null) return true;
        int opcode = node.getOpcode();
        return opcode != GOTO && opcode != ATHROW && opcode != TABLESWITCH && opcode != LOOKUPSWITCH
                && (opcode < IRETURN || opcode > RETURN);
    }
}
