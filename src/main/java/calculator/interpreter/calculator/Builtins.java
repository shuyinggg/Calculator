package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

public class Builtins {
    public static AstNode handleSimplifyWrapper(Environment env, AstNode wrapper) {
        assertSignatureOk("$simplify", 1, wrapper);
        return ExpressionManipulator.simplify(env, wrapper.getChildren().get(0));
    }

    public static AstNode handleBlock(Environment env, AstNode wrapper) {
        assertSignatureOk("block", wrapper);
        AstNode out;
        for (AstNode child : wrapper.getChildren()) {

        }
    }

    private static void assertSignatureOk(String name, int numChildren, AstNode node) {
        boolean ok = node.isOperation() && node.getName().equals(name) && node.getChildren().size() == numChildren;
        if (!ok) {
            String msg = String.format(
                    "Input ('%s' w/ %d) does not match expected ('%s' w/ %d)",
                    node.getName(),
                    node.getChildren().size(),
                    name,
                    numChildren);

            throw new IllegalArgumentException(msg);
        }
    }

    private static void assertSignatureOk(String name, AstNode node) {
        boolean ok = node.isOperation() && node.getName().equals(name);
        if (!ok) {
            String msg = String.format(
                    "Input ('%s') does not match expected ('%s')",
                    node.getName(),
                    name);

            throw new IllegalArgumentException(msg);
        }
    }
}

