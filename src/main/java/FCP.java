import Lexer.LexerForFCP;
import Lexer.Token;
import Parser.Parser;
import Parser.Printer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import Parser.Expression;

public class FCP {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        run("fefw");
        /*if (args.length > 1) {
            System.out.println("Too many arguments");
            System.exit(1);
        }
        else if (args.length == 1) {
            runFile(args[0]);
        }
        else {
            runREPL();
        }*/
    }

    private static void runFile(String name) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(name));

        if (hadError) {
            System.exit(1);
        }

        run(new String(bytes, Charset.defaultCharset()));
    }

    static void runREPL() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;) {
            System.out.println(">");
            String line = reader.readLine();

            if (line == null) {
                break;
            }

            hadError = false;

            run(line);
        }
    }

    static void run(String stringOfCode) {
        LexerForFCP lexer = new LexerForFCP(" 1 + 2 * 3 == 1 + 4 - 2");
        lexer.parseString();
        List<Token> tokens = lexer.listOfTokens;
        System.out.print("tokens: ");

        for (Token i : tokens) {
            System.out.print(i.name);
            System.out.print(" ");
        }
        Parser parser = new Parser(tokens);

        Expression exp  = parser.parse();
        Printer printer = new Printer();
        System.out.println(printer.print(exp));

    }

    public static void error(int string, String message) {
        report(string, "" ,message);
    }

    public static void report(int string, String where, String massage) {
        System.out.println("Error occurred in " + string + "line, in" + where + ": " + massage);

        boolean hadError = true;
    }
}
