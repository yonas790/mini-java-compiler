import java.util.*;

public class StackVM {
    private final List<Instruction> code;
    private int ip = 0;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private boolean running = true;

    public StackVM(List<Instruction> code) {
        this.code = code;
    }

    public void run() {
        while (running) {
            if (ip < 0 || ip >= code.size()) throw new RuntimeException("IP out of bounds: " + ip);
            Instruction ins = code.get(ip);
            switch (ins.op) {
                case "PUSH":
                    stack.push(ins.operand);
                    ip++;
                    break;
                case "POP":
                    ensureStack(1); stack.pop(); ip++; break;
                case "ADD": {
                    ensureStack(2); int b = stack.pop(); int a = stack.pop(); stack.push(a+b); ip++; break;
                }
                case "PRINT":
                    ensureStack(1); System.out.println(stack.pop()); ip++; break;
                case "HALT":
                    running = false; break;
                case "JMP":
                    if (ins.operand == null) throw new RuntimeException("JMP requires operand");
                    ip = ins.operand;
                    break;
                case "JZ":
                    if (ins.operand == null) throw new RuntimeException("JZ requires operand");
                    ensureStack(1);
                    int v = stack.pop();
                    ip = (v == 0) ? ins.operand : ip + 1;
                    break;
                case "JNZ":
                    if (ins.operand == null) throw new RuntimeException("JNZ requires operand");
                    ensureStack(1);
                    int v2 = stack.pop();
                    ip = (v2 != 0) ? ins.operand : ip + 1;
                    break;
                case "CALL":
                    if (ins.operand == null) throw new RuntimeException("CALL requires target");
                    callStack.push(ip + 1);
                    ip = ins.operand;
                    break;
                case "RET":
                    if (callStack.isEmpty()) running = false;
                    else ip = callStack.pop();
                    break;
                default:
                    throw new RuntimeException("Unknown opcode: " + ins.op + " at ip=" + ip);
            }
        }
    }

    private void ensureStack(int n) {
        if (stack.size() < n) throw new RuntimeException("Stack underflow: need " + n);
    }
}
