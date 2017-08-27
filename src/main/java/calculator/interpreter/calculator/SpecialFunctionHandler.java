package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;
import datastructures.interfaces.IDictionary;

public interface SpecialFunctionHandler {
    public AstNode manipulate(Environment env, AstNode node);
}
