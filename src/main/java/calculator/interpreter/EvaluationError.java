package calculator.interpreter;

public class EvaluationError extends RuntimeException {
    public EvaluationError() {
        super();
    }

    public EvaluationError(String message) {
        super(message);
    }

    public EvaluationError(String message, Throwable cause) {
        super(message, cause);
    }

    public EvaluationError(Throwable cause) {
        super(cause);
    }
}
