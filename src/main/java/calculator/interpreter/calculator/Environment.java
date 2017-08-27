package calculator.interpreter.calculator;

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
 * - getPlot()
 *
 * You can ignore all other methods -- they're used internally by
 * the code you were provided.
 */
public class Environment {
    private IDictionary<String, AstNode> variables;
    private PrintStream output;
    private IDictionary<String, SpecialFunctionHandler> specialFunctions;

    public Environment(IDictionary<String, AstNode> variables,
                       PrintStream output,
                       IDictionary<String, SpecialFunctionHandler> specialFunctions) {
        this.variables = variables;
        this.output = output;
        this.specialFunctions = specialFunctions;
    }

    /**
     * Contains a map of every single currently defined variable.
     */
    public IDictionary<String, AstNode> getVariables() {
        return this.variables;
    }

    /**
     * A printstream to the output -- lets us control where we print
     * instead of having to always default to System.out
     */
    public PrintStream getOutputStream() {
        return this.output;
    }

    /**
     * Returns all functions that require special handling/have a custom
     * "definition" in the interpreter
     */
    public IDictionary<String, SpecialFunctionHandler> getSpecialFunctions() {
        return this.specialFunctions;
    }
}
