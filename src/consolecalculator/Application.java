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

    private static final String FILE_INSTRUCTIONS = "instructions.txt";

    private static BufferedReader getReader(int reader) throws IOException {
        switch (reader) {
        case 1:
            return new BufferedReader(new InputStreamReader(System.in));
        case 2:
            return Files.newBufferedReader(Paths.get(FILE_INSTRUCTIONS));
        default:
            return null;
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = getReader(1)) {
            String input = null;

            do {
                try {
                    System.out.print("Skriv in en ekvation, 'exit', eller 'help' för mer info: ");
                    input = reader.readLine();

                    if (input.equals("help")) {
                        // TODO: Implement help command
                    } else if (!input.equals("exit")) {
                        try {
                            System.out.println("Svar: " + Calculator.evalInput(input));
                        } catch (ArithmeticException e) {
                            System.out.println("Aritmetiskt error: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println("Syntaxfel! " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Fel: " + e.getMessage());
                        } catch (RuntimeException e) {
                            System.out.println("Fel: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error! Kunde inte läsa input!");
                }

                System.out.println();
            } while (!input.equals("exit"));
        } catch (IOException e) {
            System.out.println("Error! Kunde inte läsa input fil!");
        }
    }

}
