package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;

public interface SpecialFunctionHandler {
    public AstNode manipulate(Environment env, AstNode node);
}
