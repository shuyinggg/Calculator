package calculator.parser;

import calculator.interpreter.EvaluationError;

public class ParseError extends EvaluationError {
    public ParseError(String message) {
        super(message);
    }

    public ParseError(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }
}
