package calculator.interpreter.calculator;

import calculator.gui.ImageDrawer;
import calculator.interpreter.AstNode;
import datastructures.interfaces.IDictionary;

import java.io.PrintStream;

/**
 * An 'Environment' object is a wrapper around every single item in
 * your 'environment' you might need.
 *
 * This exists mainly so we don't need to pass a million different
 * parameters into your manipulation functions.
 *
 * You should only need to use the following methods:
 *
 * - getVariables()
 * - getImageDrawer()
 *
 * You can ignore all other methods -- they're used internally by
 * the code you were provided.
 */
public class Environment {
    private IDictionary<String, AstNode> variables;
    private PrintStream output;
    private ImageDrawer imageDrawer;
    private IDictionary<String, SpecialFunctionHandler> customFunctions;
    private IDictionary<String, SpecialFunctionHandler> specialFunctions;
    private Interpreter interpreter;

    public Environment(IDictionary<String, AstNode> variables,
                       PrintStream output,
                       ImageDrawer imageDrawer,
                       IDictionary<String, SpecialFunctionHandler> customFunctions,
                       IDictionary<String, SpecialFunctionHandler> specialFunctions,
                       Interpreter interpreter) {
        this.variables = variables;
        this.output = output;
        this.imageDrawer = imageDrawer;
        this.customFunctions = customFunctions;
        this.specialFunctions = specialFunctions;
        this.interpreter = interpreter;
    }

    /**
     * Contains a map of every single currently defined variable.
     */
    public IDictionary<String, AstNode> getVariables() {
        return this.variables;
    }

    /**
     * Returns a class that contains a variety of useful methods for
     * drawing and plotting data.
     *
     * If the Calculator object is running outside of the GUI, this
     * method returns null.
     *
     * Implementation note: you may assume this method always returns
     * an actual ImageDrawer object when you're running your calculator
     * app, but will always return 'null' in your tests.
     */
    public ImageDrawer getImageDrawer() {
        return this.imageDrawer;
    }

    /**
     * A printstream to the output -- lets us control where we print
     * instead of having to always default to System.out.
     *
     * You can ignore this method.
     */
    public PrintStream getOutputStream() {
        return this.output;
    }

    /**
     * Returns all custom functions that manipulate an expression in some way.
     *
     * You should ignore this method.
     */
    public IDictionary<String, SpecialFunctionHandler> getCustomFunctions() {
        return this.customFunctions;
    }

    /**
     * Returns all functions that manipulate the flow of execution in a special way
     * somehow.
     *
     * You should ignore this method.
     */
    public IDictionary<String, SpecialFunctionHandler> getSpecialFunctions() {
        return this.specialFunctions;
    }

    /**
     * Returns the interpreter that is currently executing the AST.
     *
     * You should ignore this method, unless you are working on the
     * extra credit and are trying to add control flow to our calculator.
     */
    public Interpreter getInterpreter() {
        return this.interpreter;
    }
}
