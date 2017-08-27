package calculator;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import calculator.interpreter.logo.LogoInterpreter;
import org.junit.Test;

public class TestInterpreter {
    private String interpret(String input) {
        return new LogoInterpreter().evaluate(input);
    }

    @Test
    public void testEvalBasicMath() {
        assertEquals("5.0", this.interpret("toDouble(3 + 2)"));
        assertEquals("-5.0", this.interpret("toDouble(3 - 2 * 4)"));
        assertEquals("4.0", this.interpret("toDouble((3 - 2) * 4)"));
    }

    @Test
    public void testBasicVariableLookup() {
        LogoInterpreter interp = new LogoInterpreter();
        interp.evaluate("x := 3");
        interp.evaluate("y := x * 6");
        assertEquals("3.0 * 6.0", interp.evaluate("y"));
        assertEquals("18.0", interp.evaluate("toDouble(y)"));
    }
}
