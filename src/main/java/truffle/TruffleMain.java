package truffle;

import org.graalvm.polyglot.Context;

public class TruffleMain {
    static public void main(String[] args) {
        try {
            Context context = Context.create("tf");
            for (int i = 0; i < 20; i++) {
                context.eval("tf", "(define iter (lambda (counter first second) (if (= counter 0) (+ second) (iter (+ counter (- 1)) second (+ first second)))))\n" +
                        "\n" +
                        "(define fib (lambda (x) (iter x 0 1)))\n" +
                        "\n" +
                        "(fib 20)\n" +
                        "(fib 20)\n" +
                        "(fib 20)\n" +
                        "(fib 20)\n" +
                        "(fib 20)\n" +
                        "(fib 20)\n" +
                        "(fib 20)\n" +
                        "\n" +
                        "(define first (now))\n" +
                        "\n" +
                        "(fib 20)\n" +
                        "\n" +
                        "(define second (now))\n" +
                        "\n" +
                        "(println (+ second (- first)))");
            }

        } catch (Exception e) {
            System.out.println("Execute exception: " + e.getMessage());
        }
    }
}
