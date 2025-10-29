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
                default:
                    throw new RuntimeException("Unknown opcode: " + ins.op + " at ip=" + ip);
            }
        }
    }

    private void ensureStack(int n) {
        if (stack.size() < n) throw new RuntimeException("Stack underflow: need " + n);
    }
}
