package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;
import calculator.parser.Parser;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;

public class CalculatorInterpreter {
    private Parser parser;
    private IDictionary<String, AstNode> variables;
    private IDictionary<String, SpecialFunctionHandler> specialFunctions;
    private IDictionary<String, Integer> precedenceMap;

    public CalculatorInterpreter() {
        this.parser = new Parser();
        this.variables = new ArrayDictionary<>();
        this.specialFunctions = new ArrayDictionary<>();

        // Your functions
        this.specialFunctions.put("toDouble", ExpressionManipulator::toDouble);
        this.specialFunctions.put("plot", ExpressionManipulator::plot);

        // Internal functions
        this.specialFunctions.put("$simplify", Builtins::handleSimplifyWrapper);

        this.precedenceMap.put("^", 1);
        this.precedenceMap.put("negate", 2);
        this.precedenceMap.put("*", 3);
        this.precedenceMap.put("/", 3);
        this.precedenceMap.put("+", 4);
        this.precedenceMap.put("-", 4);
    }

    public String interpret(String input) {
        AstNode ast = this.parser.parse(input);
        AstNode normalizedAst = injectSimplify(this.prepareEnvironment(), ast);


        throw new NotYetImplementedException();
    }

    private Environment prepareEnvironment() {
        return new Environment(
                this.variables,
                System.out,
                this.specialFunctions);
    }

    public static AstNode injectSimplify(Environment env, AstNode node) {
        return wrapSimplifyFunc(injectSimplifyHelper(env, node));
    }

    public static AstNode injectSimplifyHelper(Environment env, AstNode node) {
        if (node.isNumber()) {
            return node;
        } else if (node.isVariable()) {
            return node;
        } else {
            IList<AstNode> newChildren = new DoubleLinkedList<>();
            for (AstNode oldChild : node.getChildren()) {
                newChildren.add(injectSimplifyHelper(env, oldChild));
            }

            if (env.getSpecialFunctions().containsKey(node.getName())) {
                for (int i = 0; i < newChildren.size(); i++) {
                    newChildren.set(i, wrapSimplifyFunc(newChildren.get(i)));
                }

                return wrapSimplifyFunc(new AstNode(node.getName(), newChildren));
            } else {
                return new AstNode(node.getName(), newChildren);
            }
        }
    }

    private static AstNode wrapSimplifyFunc(AstNode inner) {
        if (inner.isOperation() && inner.getName().equals("$simplify")) {
            return inner;
        } else {
            IList<AstNode> children = new DoubleLinkedList<>();
            children.add(inner);
            return new AstNode("$simplify", children);
        }
    }

    private String convertToString(AstNode node) {
        return this.convertToString(node, 0);
    }

    private String convertToString(AstNode node, int parentPrecedenceLevel) {
        if (node.isNumber()) {
            // TODO: handle decimals sanely
            return "" + node.getNumericValue();
        } else if (node.isVariable()) {
            return node.getName();
        } else {
            String name = node.getName();
            int currPrecedenceLevel = this.precedenceMap.containsKey(name) ? this.precedenceMap.get(name) : 0;

            IList<String> children = new DoubleLinkedList<>();
            for (AstNode child : node.getChildren()) {
                children.add(this.convertToString(child, currPrecedenceLevel));
            }

            String out;
            if ("-+*/^".contains(name)) {
                out = this.join(" " + name + " ", children);
            } else if ("negate".equals(name)) {
                out = "-" + children.get(0);
            } else {
                out = name + "(" + this.join(", ", children) + ")";
            }

            if (currPrecedenceLevel > parentPrecedenceLevel) {
                out = "(" + out + ")";
            }

            return out;
        }
    }

    private String join(String connector, IList<String> items) {
        String out = "";
        if (!items.isEmpty()) {
            Iterator<String> iter = items.iterator();
            out = iter.next();
            while (iter.hasNext()) {
                out += connector + iter.next();
            }
        }
        return out;
    }
}
