package consolecalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import consolecalculator.calculator.Calculator;

/**
 * Main application class
 * 
 * @author Emil Bertilsson
 */
public class Application {

    /**
     * Exit phrase
     */
    private static final String EXIT_PHRASE = "exit";

    /**
     * Runs the application instance
     */
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String inBuffer = null;

            do {
                try {
                    System.out.printf("Enter an equation or '%s' to exit: ", EXIT_PHRASE);
                    inBuffer = reader.readLine();

                    if (!inBuffer.equals(EXIT_PHRASE)) {
                        try {
                            System.out.println("Result: " + Calculator.evalInput(inBuffer));
                        } catch (ArithmeticException e) {
                            System.out.println("Arithmetic error: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println("Syntax error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (RuntimeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error! Could not read input!");
                }
                System.out.println();
            } while (!inBuffer.equals(EXIT_PHRASE));
        } catch (IOException e) {
            System.out.println("Input error!");
        }
    }

    /**
     * Main function
     *
     * @param args Runtime arguments
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.run(); 
    }

}
