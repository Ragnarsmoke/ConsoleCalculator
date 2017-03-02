package consolecalculator.calculator;

import java.util.function.DoubleBinaryOperator;

/**
 * Arithmetics enum
 * 
 * @author Emil Bertilsson
 */
enum ArithmeticOperation {

    MULTIPLICATION('*', (a, b) -> a * b),
    DIVISION('/', (a, b) -> {
        if (a == 0 || b == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        return a / b;
    }),
    SUBTRACTION('-', (a, b) -> a - b),
    ADDITION('+', (a, b) -> a + b),
    POW('^', (a, b) -> Math.pow(a, b)),
    MODULUS('%', (a, b) -> a % b),
    NULL_OPERATION('\u0000', (a, b) -> Double.NaN);

    private char separator;
    private DoubleBinaryOperator operator;

    /**
     * Evaluates the arithmetic expression
     *
     * @param a A value
     * @param b B value
     * @return Result
     * @throws ArithmeticException Arithmetic error, divide by zero for example
     */
    public double eval(double a, double b) throws ArithmeticException {
        return operator.applyAsDouble(a, b);
    }

    /**
     * Gets the operation separator
     *
     * @return Separator character
     */
    public char getSeparator() {
        return separator;
    }

    ArithmeticOperation(char separator, DoubleBinaryOperator operator) {
        this.separator = separator;
        this.operator = operator;
    }

}
