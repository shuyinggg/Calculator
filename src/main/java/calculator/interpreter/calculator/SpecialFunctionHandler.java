package calculator.interpreter.calculator;

import calculator.interpreter.AstNode;

public interface SpecialFunctionHandler {
    AstNode manipulate(Environment env, AstNode node);
}
