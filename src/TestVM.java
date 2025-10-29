import java.util.*;

public class TestVM {
    public static void main(String[] args) {
        List<Instruction> p = new ArrayList<>();
        p.add(new Instruction("PUSH", 5));
        p.add(new Instruction("PUSH", 3));
        p.add(new Instruction("ADD", null));
        p.add(new Instruction("PRINT", null));
        p.add(new Instruction("HALT", null));
        StackVM vm = new StackVM(p);
        vm.run();
    }
}
