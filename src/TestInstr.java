public class TestInstr {
    public static void main(String[] args) {
        Instruction i1 = new Instruction("PUSH", 5);
        Instruction i2 = new Instruction("ADD", null);
        System.out.println(i1);
        System.out.println(i2);
    }
}
