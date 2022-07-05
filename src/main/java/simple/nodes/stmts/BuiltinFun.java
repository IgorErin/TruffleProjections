package simple.nodes.stmts;

import simple.types.Function;

import java.util.List;

public class BuiltinFun {
    static public Function plusFun = new Function() {
        @Override
        public Object execute(List<Object> args) {
            if (args.isEmpty()) {
                throw new RuntimeException("Sum not def for zero args");
            }

            int sum = 0;

            for (Object number : args) {
                sum += (int) number;
            }

            return sum;
        }
    };

    static public Function minusFun = new Function() {
        @Override
        public Object execute(List<Object> args) {
            if (args.isEmpty()) {
                throw new RuntimeException("subtraction not def for zero args");
            }

            int sum = (int) args.get(0);
            List<Object> subList = args.subList(1, args.size());

            for (Object number : subList) {
                sum -= (int) number;
            }

            return sum;
        }
    };
}
