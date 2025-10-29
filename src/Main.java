import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Instruction> program = Assembler.assemble(Paths.get("programs/test1.asm"));
        System.out.println("Assembled " + program.size() + " instructions.");
        for (int i = 0; i < program.size(); i++) System.out.printf("%03d: %s%n", i, program.get(i));
        StackVM vm = new StackVM(program);
        vm.run();
    }
}
