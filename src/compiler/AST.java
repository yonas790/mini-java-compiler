package compiler;

import java.util.List;

// Base Node
abstract class Node {}

// Expressions
class NumberNode extends Node {
    public final int value;
    public NumberNode(int value) { this.value = value; }
}

class BinaryOpNode extends Node {
    public final Node left;
    public final Token op;
    public final Node right;
    public BinaryOpNode(Node left, Token op, Node right) {
        this.left = left; this.op = op; this.right = right;
    }
}

class VariableNode extends Node {
    public final String name;
    public VariableNode(String name) { this.name = name; }
}

// Statements
class AssignmentNode extends Node {
    public final String variable;
    public final Node expr;
    public AssignmentNode(String variable, Node expr) {
        this.variable = variable; this.expr = expr;
    }
}

class PrintNode extends Node {
    public final Node expr;
    public PrintNode(Node expr) { this.expr = expr; }
}
