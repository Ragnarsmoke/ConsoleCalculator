package consolecalculator.calculator;

import java.util.function.Function;

/**
 * Functions enum
 * 
 * @author Emil Bertilsson
 */
enum FunctionOperation {

    SQUARE_ROOT(1, "sqrt", args -> {
        if (args[0] < 0) {
            throw new ArithmeticException("Negative square root");
        }

        return Math.sqrt(args[0]);
    }),
    FACTORIAL(1, "fact", args -> {
        if (args[0] < 0) {
            throw new ArithmeticException("Negative factorial");
        }

        double result = 1;

        for (int i = 1; i <= args[0]; i++) {
            result *= i;
        }

        return result;
    }),
    LOG_E(1, "ln", args -> Math.log(args[0])),
    LOG(1, "log", args -> args.length >= 2 ? Math.log10(args[0]) / Math.log10(args[1]) : Math.log10(args[0]));

    private int argc;
    private String index;
    private Function<double[], Double> operator;

    /**
     * Evaluates the function
     *
     * @param args Arguments
     * @throws ArithmeticException Arithmetic error, divide by zero for example
     * @throws IllegalArgumentException Too few arguments were passed
     */
    public double eval(double[] args) throws ArithmeticException, IllegalArgumentException {
        if (args.length < argc) {
            throw new IllegalArgumentException(String.format("Too few arguments (%d required)", argc));
        } else {
            return operator.apply(args);
        }
    }

    /**
     * Gets the index of the function
     *
     * @return Function index
     */
    public String getIndex() {
        return this.index;
    }

    FunctionOperation(int argc, String index, Function<double[], Double> operator) {
        this.argc = argc;
        this.index = index;
        this.operator = operator;
    }

}
