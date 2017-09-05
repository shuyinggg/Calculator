package calculator;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import calculator.interpreter.calculator.Calculator;
import org.junit.Test;

public class TestInterpreter {
    @Test
    public void testExample1BasicMath() {
        Calculator calc = new Calculator();
        assertEquals("17", calc.evaluate("3 + 2 * 7"));
        assertEquals("x + y", calc.evaluate("x + y"));
        assertEquals("1", calc.evaluate("x := 1"));
        assertEquals("1 + y", calc.evaluate("1 + y"));
        assertEquals("2", calc.evaluate("y := 2"));
        assertEquals("3", calc.evaluate("x + y"));
    }

    @Test
    public void testExample2VariableRedefinition() {
        Calculator calc = new Calculator();
        assertEquals("x + 3", calc.evaluate("y := x + 3"));
        assertEquals("x + 3", calc.evaluate("y"));
        assertEquals("4", calc.evaluate("x := 4"));
        assertEquals("7", calc.evaluate("y"));
        assertEquals("8", calc.evaluate("x := 8"));
        assertEquals("11", calc.evaluate("y"));
    }

    @Test
    public void testExample3SymbolicEvaluation() {
        Calculator calc = new Calculator();

        assertEquals(
                "x + 2 + 3 * sin(x) + 50 / 70",
                calc.evaluate("y := x + 2 + 3 * sin(x) + (20 + 30)/70"));
        assertEquals(
                "x + 2 + 3 * sin(x) + 50 / 70",
                calc.evaluate("y"));
        assertEquals(
                "4",
                calc.evaluate("x := 4"));
        assertEquals(
                "6 + 3 * sin(4) + 50 / 70",
                calc.evaluate("y"));
        assertEquals(
                "4.44387822836193",
                calc.evaluate("toDouble(y)"));
    }
}
