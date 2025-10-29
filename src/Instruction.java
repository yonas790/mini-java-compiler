public class Instruction {
    public final String op;
    public final Integer operand; // nullable

    public Instruction(String op, Integer operand) {
        this.op = op;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return operand == null ? op : op + " " + operand;
    }
}