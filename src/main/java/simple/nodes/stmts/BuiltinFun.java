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

            try {
                for (Object number : args) {
                    sum += (int) number;
                }
            } catch (Exception e) {
                System.out.println("inside plusFun, exception: " + e.getMessage());
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

    static public Function printFun = new Function() {
        @Override
        public Object execute(List<Object> args) {
            for (Object object : args) {
                System.out.print(object+ " ");
            }

            return null;
        }
    };

    static public Function printlnFun = new Function() {
        @Override
        public Object execute(List<Object> args) {
            for (Object object : args) {
                System.out.println(object+ " ");
            }

            return null;
        }
    };

    static public Function lessFun = new Function() {

        @Override
        public Object execute(List<Object> args) {
            return (int) args.get(0) < (int) args.get(1);
        }
    };

    static public Function lessOEFun = new Function() {

        @Override
        public Object execute(List<Object> args) {
            return (int) args.get(0) <= (int) args.get(1);
        }
    };

    static public Function greaterOEFun = new Function() {

        @Override
        public Object execute(List<Object> args) {
            return (int) args.get(0) >= (int) args.get(1);
        }
    };

    static public Function greaterFun = new Function() {

        @Override
        public Object execute(List<Object> args) {
            return (int) args.get(0) > (int) args.get(1);
        }
    };

    static public Function equalFun = new Function() {
        @Override
        public Object execute(List<Object> args) {
            return (int) args.get(0) == (int) args.get(1);
        }
    };
}
