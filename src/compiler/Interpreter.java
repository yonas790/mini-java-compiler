package compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private final Map<String, Integer> symbolTable = new HashMap<>();

    public void execute(List<Node> nodes) {
        for (Node node : nodes) {
            executeNode(node);
        }
    }

    private void executeNode(Node node) {
        if (node instanceof AssignmentNode a) {
            int value = evaluate(a.expr);
            symbolTable.put(a.variable, value);
        } else if (node instanceof PrintNode p) {
            int value = evaluate(p.expr);
            System.out.println(value);
        }
    }

    private int evaluate(Node node) {
        if (node instanceof NumberNode n) return n.value;
        if (node instanceof VariableNode v) return symbolTable.getOrDefault(v.name, 0);
        if (node instanceof BinaryOpNode b) {
            int left = evaluate(b.left);
            int right = evaluate(b.right);
            return switch (b.op.type) {
                case PLUS -> left + right;
                case MINUS -> left - right;
                case MUL -> left * right;
                case DIV -> left / right;
                case MOD -> left % right;
                default -> 0;
            };
        }
        return 0;
    }
}
