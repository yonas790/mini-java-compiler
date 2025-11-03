package compiler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemanticAnalyzer {
    private final Set<String> declaredVariables = new HashSet<>();

    public void analyze(List<Node> nodes) {
        for (Node node : nodes) {
            if (node instanceof AssignmentNode a) {
                declaredVariables.add(a.variable);
            } else if (node instanceof PrintNode p) {
                checkVariableUsed(p.expr);
            }
        }
    }

    private void checkVariableUsed(Node node) {
        if (node instanceof VariableNode v) {
            if (!declaredVariables.contains(v.name)) {
                throw new RuntimeException("Variable used before declaration: " + v.name);
            }
        } else if (node instanceof BinaryOpNode b) {
            checkVariableUsed(b.left);
            checkVariableUsed(b.right);
        }
    }
}
