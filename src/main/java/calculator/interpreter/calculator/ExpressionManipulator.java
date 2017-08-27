package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;
import calculator.interpreter.EvaluationError;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import misc.exceptions.NotYetImplementedException;

/**
 * All of the static methods in this class are given the exact same parameters for
 * consistency. You can often ignore some of these parameters when implementing your
 * methods.
 *
 * Some of these methods should be recursive. You may want to consider using public-private
 * pairs in some cases.
 */
public class ExpressionManipulator {
    public static AstNode toDouble(Environment env, AstNode node) {
        // To help you get started, we've implemented this method for you.
        // You should fill in the TODOs in the 'toDoubleHelper' method.
        return new AstNode(toDoubleHelper(env.getVariables(), node));
    }

    private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        // There are three types of nodes, so we have three cases.
        if (node.isNumber()) {
            // TODO: your code here
            throw new NotYetImplementedException();
        } else if (node.isVariable()) {
            if (!variables.containsKey(node.getName())) {
                // If the expression contains an undefined variable, we give up.
                throw new EvaluationError("Undefined variable: " + node.getName());
            }
            // TODO: your code here
            throw new NotYetImplementedException();
        } else {
            String name = node.getName();

            // TODO: your code here

            if (name.equals("+")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("-")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("*")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("/")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("^")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("negate")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("sin")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else if (name.equals("cos")) {
                // TODO: your code here
                throw new NotYetImplementedException();
            } else {
                throw new EvaluationError("Unknown operation: " + name);
            }
        }
    }

    public static AstNode simplify(Environment env, AstNode node) {
        // Try writing this one on your own!
        throw new NotYetImplementedException();
    }

    public static AstNode plot(Environment env, AstNode node) {
        throw new NotYetImplementedException();
    }
}
