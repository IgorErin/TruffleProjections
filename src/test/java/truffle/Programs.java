package truffle;

public class Programs {
    static final String fibRecursive = "(" +
                "define iter (" +
                    "lambda (counter first second) (" +
                        "if (= counter 0) " +
                            "(+ second) " +
                            "(iter (+ counter (- 1)) second (+ first second))" +
                        ")" +
                    ")" +
                ") " +
            "(define fib (lambda (x) (iter x 0 1)))" +
            " (fib 20)";
}
