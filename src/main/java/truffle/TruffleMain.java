package truffle;

import org.graalvm.polyglot.Context;

public class TruffleMain {
    static public void main(String[] args) {
        try {
            Context context = Context.create("tf");

            context.eval("tf", "(println 4)");

        } catch (Exception e) {
            System.out.println("Execute exception: " + e.getMessage());
        }
    }
}
