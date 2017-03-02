package consolecalculator.calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculator class
 * 
 * @author Emil Bertilsson
 */
public class Calculator {

    @SuppressWarnings("serial")
    private static HashMap<String, FunctionOperation> functions = new HashMap<String, FunctionOperation>() {

        {
            for (FunctionOperation func : FunctionOperation.values()) {
                put(func.getIndex(), func);
            }
        }
    };

    /**
     * Evaluates a simple expression (a ? b ? c ? d...)
     * 
     * @param in Input string
     * @return Evaluated result
     * @throws NumberFormatException Syntax error, could not parse input
     * @throws IllegalArgumentException
     *             Argument error, could not parse arguments
     */
    private static double simpleEval(String in) throws NumberFormatException {
        // TODO: Implement a queue-based system for computing expressions
        Pattern p;
        Matcher m;
        StringBuilder sb = new StringBuilder(in);
        boolean changed = false;
        double a, b;
        char separator;

        do {
            // Marks the changed value as false by default every run
            changed = false;

            for (ArithmeticOperation op : ArithmeticOperation.values()) {
                //separator = ArithmeticExpression.getSeparator(i);
                separator = op.getSeparator();

                // Creates a new pattern for each separator
                // Looks for the pattern a ? b
                p = Pattern
                        .compile(
                                // Matches first number
                                "(-?\\d+(?:\\.\\d+(?:E\\d+)?)?)("
                                        // Only adds escape characters if separator is special character
                                        + (!Character.isLetterOrDigit(separator) ? "\\" : "")
                                        // Matches separator
                                        + separator
                                        // Matches second number
                                        + ")(-?\\d+(?:\\.\\d+(?:E\\d+)?)?)");
                m = p.matcher(sb.toString());

                // Finds a single expression (a ? b)
                while (m.find()) {
                    // Gets the number values and separator from the expression
                    a = Double.parseDouble(m.group(1));
                    b = Double.parseDouble(m.group(3));
                    separator = m.group(2).charAt(0);

                    // Replaces the indexes of the string found with the evaluated value
                    sb.replace(m.start(), m.end(), new BigDecimal(
                            op.eval(a, b)).toPlainString());

                    // Tell the loop to keep looking
                    changed = true;

                    // Refreshes matcher to match new string
                    m = p.matcher(sb.toString());
                }
            }
        } while (changed);

        return Double.parseDouble(sb.toString());
    }

    /**
     * Evaluates expressions in function arguments
     *
     * @param contents String contents
     * @return Evaluated expressions
     * @throws NumberFormatException Syntax error, could not parse input
     * @throws IllegalArgumentException
     *             Argument error, could not parse arguments
     * @throws RuntimeException Runtime error
     */
    private static double[] argEval(String contents) throws NumberFormatException, IllegalArgumentException {
        String[] split = contents.split(",");
        double[] result = new double[split.length];

        for (int i = 0; i < split.length; i++) {
            result[i] = fullEval(split[i]);
        }

        return result;
    }

    /**
     * Evaluate parentheses and functions as well as expressions
     * 
     * @param input Input string
     * @return Evaluated result
     * @throws NumberFormatException Syntax error, could not parse input
     * @throws IllegalArgumentException
     *             Argument error, could not parse arguments
     * @throws RuntimeException Runtime error
     */
    private static double fullEval(String input)
            throws NumberFormatException, IllegalArgumentException, RuntimeException {
        input = "(" + input + ")";
        StringBuilder outBuilder = new StringBuilder(input);

        // Parentheses and function pattern
        Pattern p = Pattern.compile("(([a-zA-Z_]*)\\(([^\\(\\)]*)\\))+");
        Matcher m = p.matcher(outBuilder.toString());

        String func, contents, replacement;

        while (m.find()) {
            func = m.group(2);
            contents = m.group(3);

            // Checks if the selection is not a function
            if (func.isEmpty()) {
                replacement = Double.toString(simpleEval(contents));
            } else {
                // If a function is not found, throw an exception
                if (!functions.containsKey(func)) {
                    throw new RuntimeException(String.format("Function '%s' does not exist!", func));
                } else {
                    replacement = new BigDecimal(functions.get(func).eval(argEval(contents))).toPlainString();
                }
            }

            // Replaces the section with the evaluated value
            outBuilder.replace(m.start(), m.end(), replacement);
            // Refreshes matcher to match new string
            m = p.matcher(outBuilder.toString());
        }

        return Double.parseDouble(outBuilder.toString());
    }

    /**
     * Evaluates input
     * 
     * @param in Input string
     * @return Calculator expression
     * @throws ArithmeticException Arithmetic error, divide by zero for example
     * @throws NumberFormatException Syntax error, could not parse input
     * @throws IllegalArgumentException
     *             Variable error, could not parse variables
     * @throws RuntimeException Runtime error
     */
    public static double evalInput(String in)
            throws ArithmeticException, NumberFormatException, IllegalArgumentException {
        String formatted = in.replaceAll("\\s", "");

        return fullEval(formatted);
    }

}
