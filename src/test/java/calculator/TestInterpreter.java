package calculator;

import calculator.interpreter.calculator.Calculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestInterpreter {
    @Test
    public void testExample1BasicMath() {
        Calculator calc = new Calculator();
        assertEquals("17", calc.interpret("3 + 2 * 7"));
        assertEquals("x + y", calc.interpret("x + y"));
        assertEquals("1", calc.interpret("x := 1"));
        assertEquals("1 + y", calc.interpret("1 + y"));
        assertEquals("2", calc.interpret("y := 2"));
        assertEquals("3", calc.interpret("x + y"));
    }

    @Test
    public void testExample2VariableRedefinition() {
        Calculator calc = new Calculator();
        assertEquals("x + 3", calc.interpret("y := x + 3"));
        assertEquals("x + 3", calc.interpret("y"));
        assertEquals("4", calc.interpret("x := 4"));
        assertEquals("7", calc.interpret("y"));
        assertEquals("8", calc.interpret("x := 8"));
        assertEquals("11", calc.interpret("y"));
    }

    @Test
    public void testExample3SymbolicEvaluation() {
        Calculator calc = new Calculator();


        assertEquals(
                "x + 2 + 3 * sin(x) + 50 / 70",
                calc.interpret("y := x + 2 + 3 * sin(x) + (20 + 30)/70"));
        assertEquals(
                "x + 2 + 3 * sin(x) + 50 / 70",
                calc.interpret("y"));
        assertEquals(
                "4",
                calc.interpret("x := 4"));
        assertEquals(
                "6 + 3 * sin(4) + 50 / 70",
                calc.interpret("y"));
        assertEquals(
                "4.44387822836193",
                calc.interpret("toDouble(y)"));
    }
}
