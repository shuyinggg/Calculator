package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;
import calculator.parser.Parser;
import datastructures.concrete.ArrayDictionary;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

import java.util.Iterator;

public class Calculator {
    private Parser parser;
    private Interpreter interpreter;
    private IDictionary<String, AstNode> variables;
    private IDictionary<String, SpecialFunctionHandler> customFunctions;
    private IDictionary<String, SpecialFunctionHandler> specialFunctions;
    private IDictionary<String, Integer> precedenceMap;

    private static int WEAKEST_PRECEDENCE = Integer.MAX_VALUE;

    public Calculator() {
        this.parser = new Parser();
        this.interpreter = new Interpreter();
        this.variables = new ArrayDictionary<>();
        this.customFunctions = new ArrayDictionary<>();
        this.specialFunctions = new ArrayDictionary<>();
        this.precedenceMap = new ArrayDictionary<>();

        // Your functions
        this.customFunctions.put("simplify", ExpressionManipulator::simplify);
        this.customFunctions.put("toDouble", ExpressionManipulator::toDouble);
        this.customFunctions.put("plot", ExpressionManipulator::plot);

        // Internal functions (that need to manipulate control flow or the environment somehow)
        this.specialFunctions.put("block", Builtins::handleBlock);
        this.specialFunctions.put("assign", Builtins::handleAssign);

        this.precedenceMap.put("^", 1);
        this.precedenceMap.put("negate", 2);
        this.precedenceMap.put("*", 3);
        this.precedenceMap.put("/", 3);
        this.precedenceMap.put("+", 4);
        this.precedenceMap.put("-", 4);
    }

    public String interpret(String input) {
        Environment env = this.prepareEnvironment();
        AstNode ast = this.parser.parse(input + "\n");
        AstNode normalizedAst = injectSimplify(env, ast);
        AstNode output = this.interpreter.evaluate(env, normalizedAst);
        return this.convertToString(output);
    }

    private Environment prepareEnvironment() {
        return new Environment(
                this.variables,
                System.out,
                this.customFunctions,
                this.specialFunctions,
                this.interpreter);
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
        if (inner.isOperation() && inner.getName().equals("simplify")) {
            return inner;
        } else {
            IList<AstNode> children = new DoubleLinkedList<>();
            children.add(inner);
            return new AstNode("simplify", children);
        }
    }

    private String convertToString(AstNode node) {
        return this.convertToString(node, WEAKEST_PRECEDENCE);
    }

    private String convertToString(AstNode node, int parentPrecedenceLevel) {
        if (node.isNumber()) {
            double val = node.getNumericValue();
            if (val == (long) val) {
                return String.format("%d", (long) val);
            } else {
                return String.format("%s", val);
            }
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
